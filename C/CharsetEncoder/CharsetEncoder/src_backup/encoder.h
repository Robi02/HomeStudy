#ifndef __ENCODER_H__
#define __ENCODER_H__

extern const char *G_CHARSET[];
extern const char *G_OPTIONS[];

extern const int ENC_CS_HEXA;
extern const int ENC_CS_BASE64;
extern const int ENC_OP_ENCODE;
extern const int ENC_OP_DECODE;

int CheckCharset(char *pCharset);
int CheckOptions(char *pOptions);
int CheckString(char *pString);
int GetCharsetIdx(char *pCharset);
int GetOptionsIdx(char *pOptions);
int Encode(char *pCharset, char *pOptions, char *pInString, char *pOutString, int szOutStrBuf);

static int (*SelectEncodingFunc(int charsetIdx, int optionsIdx))(char*, char*, int);

#endif