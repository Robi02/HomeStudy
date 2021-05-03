#include <stdio.h>
#include <string.h>
#include "enc_hexa.h"

/* [ HEXA Encoding ]
 *
 * 1. Synopsis
 *
 *  < Binary 8bit >            < Char >   < Decimal >   < Hexa >
 *  [0][0][1][1][1][0][1][0] =    :     =     58      =    3A
 *
 *  > 1byte = 8bit / 8bit = 2hex base / 1byte = 2hex base
 *  > BaseSymbol : 0123456789ABCDEF
 *
 * 2. Example
 *
 *  < Input Str > "KOREA"
 *
 *  < Binary > 01001011(K) 01001111(O) 01010010(R) 01000101(E) 01000001(A)
 *
 *  < Hexa > 4(0100)B(1011) 4(0100)F(1111) 5(0101)2(0010) 4(0100)5(0101) 4(0100)1(0001)
 *
 *  < Result > "4B4F524541"
 *
 */

const static char *HEXA_SYMBOL = "0123456789ABCDEF";

int EncHexa(char *pInStr, char *pOutStr, int szOutStr)
{
	unsigned char oneByte, left4Bit, right4Bit;
	int inStrIdx, outStrIdx, szRemainStr;
	
	memset(pOutStr, 0, szOutStr);
	szRemainStr = strlen(pInStr);

	if ( --szOutStr < 2 )
	{
		return -1;
	}
	
	for ( inStrIdx = outStrIdx = 0; inStrIdx < szRemainStr; ++inStrIdx )
	{
		if ( outStrIdx + 2 > szOutStr )
		{
			break;
		}
		
		oneByte   = pInStr[inStrIdx];
		left4Bit  = oneByte >> 4;
		right4Bit = oneByte &= 0x0f;
		
		pOutStr[outStrIdx++] = HEXA_SYMBOL[left4Bit];
		pOutStr[outStrIdx++] = HEXA_SYMBOL[right4Bit];
	}

	return szRemainStr - inStrIdx;
}

int DecHexa(char *pInStr, char *pOutStr, int szOutStr)
{
	unsigned char left4Bit, right4Bit;
	int inStrIdx, outStrIdx, szRemainStr;
	
	if ( --szOutStr < 1 )
	{
		return -1;
	}
	
	memset(pOutStr, 0, szOutStr);
	szRemainStr = strlen(pInStr);
	
	for ( inStrIdx = outStrIdx = 0; inStrIdx + 1 < szRemainStr; )
	{
		if ( outStrIdx + 1 > szOutStr )
		{
			break;
		}
		
		left4Bit  = pInStr[inStrIdx++];
		right4Bit = pInStr[inStrIdx++];
		
		pOutStr[outStrIdx++] = CombineHalfByte(left4Bit, right4Bit);
	}
	
	return szRemainStr - inStrIdx;
}

static char CombineHalfByte(unsigned char inLeft4Bit, unsigned char inRight4Bit)
{
	unsigned char *pByte, left4Bit, right4Bit;
	char oneByte;
	int i;
	
	left4Bit  = inLeft4Bit;
	right4Bit = inRight4Bit;
	
	for ( i = 0 ; i < 2; ++i )
	{
		/* 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70  < ASCII >
		    0,  1,  2,  3,  4,  5,  6,  7,  8,  9,  A,  B,  C,  D,  E,  F  < HEXA  > */
		
		pByte = (i == 0 ? &left4Bit : &right4Bit);
		
		if ( *pByte > 47 && *pByte < 58 )
		{
			*pByte -= 48; /* Char '0' mapped to decimal 0  */
		}
		else
		{
			*pByte -= 55; /* Char 'A' mapped to decimal 10 */
		}
	}

	oneByte = 0x00;
	oneByte |= left4Bit << 4;
	oneByte |= right4Bit;
	
	return oneByte;
}