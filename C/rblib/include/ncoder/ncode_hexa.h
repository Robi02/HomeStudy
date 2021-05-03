#ifndef __NCODE_HEXA_H__
#define __NCODE_HEXA_H__

#include "common_typedef.h"

typedef struct
{
    int     isEncode;       // 1 is encoding, others are decoding.
    int     isLowercase;    // When 1, Output stream is lowercase letter. (1:[a-f] <-> [A-F])
    BYTE    *pbInStream;    // Input stream to en/decoding.
    int     szInStream;     // Byte size of input stream.
    BYTE    *pbOutBuf;      // Output stream buffer.
    int     szOutBuf;       // Byte size of output buffer.
    
} NCODE_TYPE_HEXA_ARGS;

static int CheckCommonArgs(NCODE_TYPE_HEXA_ARGS *pstArgs);
static int CheckEncodeArgs(NCODE_TYPE_HEXA_ARGS *pstArgs);
static int CheckDecodeArgs(NCODE_TYPE_HEXA_ARGS *pstArgs);

int Encode_Hexa(NCODE_TYPE_HEXA_ARGS *pstArgs); // return: Output size of encoding result.
int Decode_Hexa(NCODE_TYPE_HEXA_ARGS *pstArgs); // return: Output size of decoding result.

#endif