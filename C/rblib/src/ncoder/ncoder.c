#include <stdio.h>
#include <string.h>
#include "ncode_hexa.h"
#include "ncoder.h"

int Ncode(NCODE_TYPE eNcodeType, void *pstArgs, int szArgs)
{
    if (eNcodeType == NCODE_TYPE_HEXA)
    {
        if (sizeof(NCODE_TYPE_HEXA_ARGS) != szArgs)
        {
            fprintf(stderr, "sizeof(NCODE_TYPE_HEXA_ARGS) != szArgs (%ld != %d)\n", sizeof(NCODE_TYPE_HEXA_ARGS), szArgs);
            return -1;
        }

        return ((NCODE_TYPE_HEXA_ARGS *)pstArgs)->isEncode == 1 ? Encode_Hexa(pstArgs) : Decode_Hexa(pstArgs);
    }
/*    else if (eNcodeType == NCODE_TYPE_BASE64)
    {
        if (sizeof(NCODE_TYPE_BASE64_ARGS) != szArgs)
        {
            fprintf(stderr, "sizeof(NCODE_TYPE_BASE64_ARGS) != szArgs (%ld != %d)\n", sizeof(NCODE_TYPE_BASE64_ARGS), szArgs);
            return -1;
        }

        return ((NCODE_TYPE_BASE64_ARGS *)pstArgs)->isEncode == 1 ? Encode_Base64(pstArgs) : Decode_Base64(pstArgs);
    }*/
    else if (0)
    {
        // Add new NCODE_TYPE here...
        return -1;
    }
    else
    {
        fprintf(stderr, "Unknown eNcodeType. (eNcodeType:%d)\n", eNcodeType);
        return -1;
    }
}

int main(int argc, char **argv)
{
    NCODE_TYPE_HEXA_ARGS stArgs;
    BYTE abInBuf[1024], abOutBuf[1024];
    int rtVal;

    memset(&stArgs, 0x00, sizeof(stArgs));
    memset(abInBuf, 0x00, sizeof(abInBuf));
    memset(abOutBuf, 0x00, sizeof(abOutBuf));
    rtVal = 0;

    memcpy(abInBuf, argv[2], strlen(argv[2]));

    stArgs.isEncode = (*argv[1] == 'e' ? 1 : 0);
    stArgs.isLowercase = 0;
    stArgs.pbInStream = abInBuf;
    stArgs.pbOutBuf = abOutBuf;
    stArgs.szInStream = strlen(argv[2]);
    stArgs.szOutBuf = sizeof(abOutBuf);

    fprintf(stdout, "isEncode:%d, pbInStream:%s, szInStream:%d\n", stArgs.isEncode, stArgs.pbInStream, stArgs.szInStream);

    if ((rtVal = Ncode(NCODE_TYPE_HEXA, (void *)&stArgs, sizeof(stArgs))) <= 0)
    {
        fprintf(stderr, "Ncode() error. (return:%d)\n", rtVal);
        return -1;
    }

    fprintf(stdout, "%s (rtVal:%d)\n", abOutBuf, rtVal);

    return 0;
}