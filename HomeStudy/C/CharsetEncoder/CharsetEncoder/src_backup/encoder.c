#include <stdio.h>
#include <string.h>
#include "enc_hexa.h"
#include "enc_base64.h"
#include "encoder.h"

const char *G_CHARSET[] = { "-hexa", "-base64" };
const char *G_OPTIONS[] = { "-e", "-d" };

const int ENC_CS_HEXA = 0;
const int ENC_CS_BASE64 = 1;
const int ENC_OP_ENCODE = 0;
const int ENC_OP_DECODE = 1;

int CheckCharset(char *pCharset)
{
	int rtVal;
	
	rtVal = GetCharsetIdx(pCharset);
	
	return ( (rtVal > -1) ? 0 : -1 );
}

int CheckOptions(char *pOptions)
{
	int rtVal;
	
	rtVal = GetOptionsIdx(pOptions);
	
	return ( (rtVal > -1) ? 0 : -1 );
}

int CheckString(char *pInString)
{
	if ( pInString == NULL || (strlen(pInString) <= 0) )
	{
		return -1;
	}
	
	return 0;
}

int GetCharsetIdx(char *pCharset)
{
	int i, charsetCnt;
	
	charsetCnt = sizeof(G_CHARSET) / sizeof(char*);
	
	if ( pCharset == NULL )
	{
		return -1;
	}
	
	for (i = 0; i < charsetCnt; ++i)
	{
		if ( strcmp(pCharset, G_CHARSET[i]) == 0 )
		{
			return i;
		}
	}
	
	return -1;
}

int GetOptionsIdx(char *pOptions)
{
	int i, optionsCnt;
	
	optionsCnt = sizeof(G_OPTIONS) / sizeof(char*);
	
	if ( pOptions == NULL )
	{
		return -1;
	}
	
	for (i = 0; i < optionsCnt; ++i)
	{
		if ( strcmp(pOptions, G_OPTIONS[i]) == 0 )
		{
			return i;
		}
	}
	
	return -1;
}

int Encode(char *pCharset, char *pOptions, char *pInStr, char *pOutStr, int szOutStrBuf)
{
	int charsetIdx, optionsIdx, rtVal;
	int (*pEncoderFunc)(char*, char*, int);
	
	if ( (CheckCharset(pCharset) != 0) || (CheckOptions(pOptions) != 0) || (CheckString(pInStr) != 0) )
	{
		fprintf(stderr, "%s() Error: Check pCharset|Options|String. \
						(pCharset=%s, pOptions=%s, pInStr=%s)\n", __FUNCTION__, pCharset, pOptions, pInStr);
		return -1;
	}
	
	if ( (charsetIdx = GetCharsetIdx(pCharset)) < 0 )
	{
		fprintf(stderr, "%s() Error: Check pCharset|G_CHARSET. (pCharset=%s)\n", __FUNCTION__, pCharset);
		return -1;
	}
	
	if ( (optionsIdx = GetOptionsIdx(pOptions)) < 0 )
	{
		fprintf(stderr, "%s() Error: Check pOptions|G_OPTIONS. (pOptions=%s)\n", __FUNCTION__, pOptions);
		return -1;
	}
	
	if ( (pEncoderFunc = SelectEncodingFunc(charsetIdx, optionsIdx)) == NULL )
	{
		fprintf(stderr, "%s() Error: Cannot found correct En/Decoder function. \
						(pCharset=%s, pOptions=%s)\n", pCharset, pOptions);
		return -1;
	}
	
	if ( (rtVal = pEncoderFunc(pInStr, pOutStr, szOutStrBuf)) < 0 )
	{
		fprintf(stderr, "%s() Error: pEncoderFunc() error. (returns:%d)\n", __FUNCTION__, rtVal);
		return -1;
	}
	
	return rtVal;
}

static int (*SelectEncodingFunc(int charsetIdx, int optionsIdx))(char*, char*, int)
{
	if ( charsetIdx == ENC_CS_HEXA )
	{
		return ( (optionsIdx == ENC_OP_ENCODE) ? EncHexa : DecHexa );
	}
	else if ( charsetIdx == ENC_CS_BASE64)
	{
		return ( (optionsIdx == ENC_OP_ENCODE) ? EncBase64 : DecBase64 );
	}
	
	return NULL;
}