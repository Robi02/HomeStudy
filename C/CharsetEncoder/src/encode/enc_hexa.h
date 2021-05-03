#ifndef __ENC_HEXA_H__
#define __ENC_HEXA_H__

const static char *HEXA_SYMBOL;

int EncHexa(char *pInStr, char *pOutStr, int szOutStr);
int DecHexa(char *pInStr, char *pOutStr, int szOutStr);

static char CombineHalfByte(unsigned char inLeft4Bit, unsigned char inRight4Bit);

#endif