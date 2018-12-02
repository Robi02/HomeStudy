#include <stdio.h>
#include <string.h>
#include "rbmath.h"
#include "enc_base64.h"

/* [ BASE64 Encoding ]
 *
 * 1. Synopsis
 *
 * < 3Char >           < Binary 24bit >                  < Base64 6bit binary >		   < Base64 >
 *   "Man"      [01001101][01100001][01101110]		[010011][010110][000101][101110]     "TWFu"
 *
 *  > Make group each 6bits for 4 base64 char and padding '=' for empty space.
 *  > BaseSymbol: ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/
 *
 * 2. Example
 *
 *  < Input Str > "KOREA"
 *
 *  < Binary > 01001011(K) 01001111(O) 01010010(R) 01000101(E) 01000001(A)
 *
 *  < Base64 6bit binary > 010010(S)110100(0)111101(9)010010(S)010001(R)010100(U)0001[00](E)(=)
 *
 *  < Result > "S09SRUE="
 *
 */
 
const static char *BASE64_SYMBOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

int EncBase64(char *pInStr, char *pOutStr, int szOutStr)
{
	unsigned char buf3Byte[3], buf6Bit[4];	/* InStr: buf3Byte[4], OutStr: buf6Bit[3] */
	int inStrIdx, outStrIdx, outStrLen, accumOutStrLen, bufStrLen, szRemainStr;
	
	memset(pOutStr, 0, szOutStr);
	szRemainStr = strlen(pInStr);

	if ( --szOutStr < 4 )
	{
		fprintf(stderr, "%s() Error: Output buffer size must be over 5byte! (CurSize:%d)\n", __FUNCTION__, szOutStr + 1);
		return -1;
	}
	
	for ( inStrIdx = outStrIdx = accumOutStrLen = 0; inStrIdx < szRemainStr; inStrIdx += bufStrLen )
	{
		bufStrLen = min(szRemainStr - inStrIdx, 3);
		outStrLen =  bufStrLen + 1;
		accumOutStrLen += outStrLen;
		memset(buf6Bit, 0, 4);
		memset(buf3Byte, 0, 3);
		memcpy(buf3Byte, &pInStr[inStrIdx], bufStrLen);

		if ( accumOutStrLen > szOutStr )
		{
			break;
		}

		buf6Bit[0] |= (buf3Byte[0] & 0xfc) >> 2;		 							/* xxxxxx00 00000000 00000000 */
		buf6Bit[1] |= ((buf3Byte[0] & 0x03) << 4) | ((buf3Byte[1] & 0xf0) >> 4);	/* 000000xx xxxx0000 00000000 */
		buf6Bit[2] |= ((buf3Byte[1] & 0x0f) << 2) | ((buf3Byte[2] & 0xc0) >> 6);	/* 00000000 0000xxxx xx000000 */
		buf6Bit[3] |= (buf3Byte[2] & 0x3f);											/* 00000000 00000000 00xxxxxx */

		memset(&pOutStr[outStrIdx + outStrLen], '=', 3 - bufStrLen);
		
		for ( ; outStrIdx < accumOutStrLen; ++outStrIdx )
		{
			pOutStr[outStrIdx] = BASE64_SYMBOL[buf6Bit[outStrIdx % 4]];
		}
	}
	
	return szRemainStr - inStrIdx;
}

int DecBase64(char *pInStr, char *pOutStr, int szOutStr)
{
	unsigned char base64Str[4];
	unsigned int buf4Byte;
	int i, inStrIdx, outStrIdx, accumOutStrLen, szRemainStr;

	memset(pOutStr, 0, szOutStr);
	szRemainStr = strlen(pInStr);
	
	if ( --szOutStr < 3 )
	{
		fprintf(stderr, "%s() Error: Output buffer size must be over 4byte! (CurSize:%d)\n", __FUNCTION__, szOutStr + 1);
		return -1;
	}

	if ( szRemainStr % 4 != 0 )
	{
		fprintf(stderr, "%s() Error: Base64 encoded string size is must be multiple of 4! (InStrSize:%d)\n",
				__FUNCTION__, szRemainStr);
		return -3;
	}

	for ( inStrIdx = outStrIdx = accumOutStrLen = 0; inStrIdx < szRemainStr; inStrIdx += 4 )
	{
		accumOutStrLen += 3;
		buf4Byte = 0;
		memset(base64Str, 0, 4);
		memcpy(base64Str, &pInStr[inStrIdx], 4);

		if ( accumOutStrLen > szOutStr )
		{
			break;
		}

		for ( i = 0; i < 4; ++i )
		{
			buf4Byte <<= 6;

			if ( base64Str[i] > 64 && base64Str[i] < 91 )
			{
				base64Str[i] -= 65; /* Char 'A-Z' is 0-25th index of base64  */
			}
			else if ( base64Str[i] > 96 && base64Str[i] < 123 )
			{
				base64Str[i] -= 71; /* Char 'a-z' is 26-51th index of base64 */
			}
			else if ( base64Str[i] > 47 && base64Str[i] < 58 )
			{
				base64Str[i] += 4;  /* Char '0-9' is 52-61th index of base64 */
			}
			else if ( base64Str[i] == '+' )
			{
				base64Str[i] = 62; /* Char '+' is 62th index of base64 */
			}
			else if ( base64Str[i] == '/' )
			{
				base64Str[i] = 63; /* Char '/' is 63th index of base64 */
			}
			else if ( base64Str[i] == '=' )
			{
				base64Str[i] = 255; /* Char '=' is the count of empty char for padding string */
				continue;
			}
			else
			{	/* Undefined symbol error */
				fprintf(stderr, "%s() Error: Undefined symbol '%c' in input string!\n", __FUNCTION__, base64Str[i]);
				return -2;
			}

			buf4Byte |= base64Str[i]; /* 00000000wwwwwwxxxxxxyyyyyyzzzzzz */
		}

		for ( ; outStrIdx < accumOutStrLen; ++outStrIdx ) /* wwwwwwxx xxxxyyyy yyzzzzzz */
		{
			buf4Byte <<= 8;
			pOutStr[outStrIdx] = buf4Byte >> 24; 
		}
	}

	return szRemainStr - inStrIdx;
}