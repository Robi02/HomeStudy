#ifndef __ENC_BASE64_H__
#define __ENC_BASE64_H__

const static char *BASE64_SYMBOL;

int EncBase64(char *pInStr, char *pOutStr, int szOutStr);
int DecBase64(char *pInStr, char *pOutStr, int szOutStr);

#endif