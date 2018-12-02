/*******************************************************************************
*
* FILE:         KISA_SEED.h
*
* DESCRIPTION:  header file for KISA_SEED.c
*
*******************************************************************************/

#ifndef _SEED_H
#define _SEED_H


/******************************* Include files ********************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "KISA/KISA_typedef.h"

/******************************* Type Definitions *****************************/

// If your machine uses different data types, you must change them to proper ones.
// #ifndef TYPE_DEFINITION
//     #define TYPE_DEFINITION
//     //#if defined(__alpha)
//         typedef unsigned int        DWORD;		// unsigned 4-byte data type
//         typedef unsigned short      WORD;		// unsigned 2-byte data type
//     //#else
//     //    typedef unsigned long int   DWORD;		// unsigned 4-byte data type
//     //    typedef unsigned short int  WORD;		// unsigned 2-byte data type
//     //#endif
//     typedef unsigned char           BYTE;		// unsigned 1-byte data type
// #endif

/**************************** Constant Definitions ****************************/

#define NoRounds         16						// the number of rounds
#define NoRoundKeys      (NoRounds*2)			// the number of round-keys
#define SeedBlockSize    16    					// block length in bytes
#define SeedBlockLen     128   					// block length in bits


/******************************** Common Macros *******************************/

// macroses for left or right rotations
#if defined(_MSC_VER)
    #define ROTL(x, n)     (_lrotl((x), (n)))		// left rotation
    #define ROTR(x, n)     (_lrotr((x), (n)))		// right rotation
#else
    #define ROTL(x, n)     (((x) << (n)) | ((x) >> (32-(n))))		// left rotation
    #define ROTR(x, n)     (((x) >> (n)) | ((x) << (32-(n))))		// right rotation
#endif

// macroses for converting endianess
#define EndianChange(dwS)                       \
    ( (ROTL((dwS),  8) & (DWORD)0x00ff00ff) |   \
      (ROTL((dwS), 24) & (DWORD)0xff00ff00) )


/*************************** Function Declarations ****************************/

void SEED_Encrypt(		/* encryption function */
		BYTE *pbData, 				// [in,out]	data to be encrypted
		DWORD *pdwRoundKey			// [in]			round keys for encryption
		);
    
void SEED_Decrypt(		/* decryption function */
		BYTE *pbData, 				// [in,out]	data to be decrypted
		DWORD *pdwRoundKey			// [in]			round keys for decryption
		);
    
void SEED_KeySchedKey(		/* key scheduling function */
		DWORD *pdwRoundKey, 		// [out]		round keys for encryption or decryption
		BYTE *pbUserKey				// [in]			secret user key 
		);

/*************************** END OF FILE **************************************/
#endif
