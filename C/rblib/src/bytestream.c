#include "bytestream.h"
#include <stdio.h>
#include <string.h>
int CountStreamInStream(BYTE *pbIn, int szIn, BYTE *pbFind, int szFind)
{
    int i, j, iLimit, jFound, rtCnt;

    if (pbIn == NULL || szIn < 1)
    {
        fprintf(stderr, "pbIn == NULL || szIn < 1 (pbIn:0x%p, szIn:%d)\n", pbIn, szIn);
        return -1;
    }

    if (pbFind == NULL || szFind < 1)
    {
        fprintf(stderr, "pbIn == NULL || szIn < 1 (pbIn:0x%p, szIn:%d)\n", pbIn, szIn);
        return -1;
    }

    if (szIn < szFind)
    {
        fprintf(stderr, "szIn < szFind (%d < %d)\n", szIn, szFind);
        return -1;
    }

    iLimit = szIn - szFind;
    jFound = 1;
    rtCnt = 0;

    for (i = 0; i < )

    return rtCnt;
}

int main()
{
    BYTE *pbIn = "abcdeefghijkeelmnopeeeqrstuvwxeeeeyz";
    BYTE *pbFind = "ee";
    int result = CountStreamInStream(pbIn, strlen(pbIn), pbFind, strlen(pbFind));

    fprintf(stdout, "result:%d\n", result);
    return 0;
}