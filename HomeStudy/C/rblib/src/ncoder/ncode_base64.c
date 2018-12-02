#include "ncode_base64.h"

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
 *  < Base64 6bit binary > 010010(S)110100(0)111101(9)010010(S)010001(R)010100(U)0001/00(E)(=)
 *
 *  < Result > "S09SRUE="
 *
 */

static const char *P_BASE64_PADDING = "=";
static const char *P_BASE64_SYMBOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
static const BYTE A_BASE64_REVERSE_TABLE[256] =
{
    255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 
    255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,  62, 255, 255, 255,  63,  52,  53,  54,  55,  56,  57,  58,  59,  60,  61, 255, 255, 255, 255, 255, 255, 
    255,   0,   1,   2,   3,   4,   5,   6,   7,   8,   9,  10,  11,  12,  13,  14,  15,  16,  17,  18,  19,  20,  21,  22,  23,  24,  25, 255, 255, 255, 255, 255, 
    255,  10,  11,  12,  13,  14,  15, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 
    255,  26,  27,  28,  29,  30,  31,  32,  33,  34,  35,  36,  37,  38,  39,  40,  41,  42,  43,  44,  45,  46,  47,  48,  49,  50,  51, 255, 255, 255, 255, 255, 
    255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 
    255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 
    255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255
};

static int CheckCommonArgs(NCODE_TYPE_BASE64_ARGS *pstArgs)
{
    if (pstArgs == NULL)
    {
        fprintf(stderr, "pstArgs is NULL\n");
        return -1;
    }

    if (pstArgs->pbInStream == NULL || pstArgs->szInStream < 1)
    {
        fprintf(stderr, "pbInStream is NULL || szInStream < 1 (szInStream:%d)\n", pstArgs->szInStream);
        return -1;
    }

    if (pstArgs->pbOutBuf == NULL || pstArgs->szOutBuf < 1)
    {
        fprintf(stderr, "pbOutBuf is NULL || szOutBuf < 1 (szOutBuf:%d)\n", pstArgs->szOutBuf);
        return -1;
    }

    return 0;
}

static int CheckEncodeArgs(NCODE_TYPE_BASE64_ARGS *pstArgs)
{                                                               // [ szInStream -> szOutReq ]
    int szOutReq = (((pstArgs->szInStream + 2) / 3) * 4;        // [1 -> 4], [4 -> 8], [7 -> 12], ... 
                                                                // [2 -> 4], [5 -> 8], [8 -> 12],
    if (pstArgs->szOutBuf < szOutReq)                           // [3 -> 4], [6 -> 8], [9 -> 12],
    {
        fprintf(stderr, "szOutBuf < szOutReq (%d < %d)\n", pstArgs->szOutBuf, szOutReq);
        return -1;
    }

    return -1;
}

static int CheckDecodeArgs(NCODE_TYPE_BASE64_ARGS *pstArgs)
{                                                               // [ szInStream -> szOutReq ]
    int szOutReq = ((pstArgs->szInStream * 3) / 4) - ;    // [4(xx==) -> 1], [8(xxxxyy==) -> 4], ...
                                                                // [4(xxx=) -> 2], [8(xxxxyyy=) -> 5],
    if (pstArgs->szOutBuf < szOutReq)                           // [4(xxxx) -> 3], [8(xxxxyyyy) -> 6],
    {
        fprintf(stderr, "szOutBuf < szOutReq (%d < %d)\n", pstArgs->szOutBuf, szOutReq);
        return -1;
    }

    if (pstArgs->szInStream % 4 != 0)
    {
        fprintf(stderr, "szInStream %% 4 != 0 (szInStream:%d)\n", pstArgs->szInStream);
        return -1;
    }

    return -1;
}


int Encode_Base64(NCODE_TYPE_BASE64_ARGS *pstArgs)
{

}

int Decode_Base64(NCODE_TYPE_BASE64_ARGS *pstArgs)
{

}