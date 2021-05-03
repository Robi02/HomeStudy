#include <stdio.h>
#include <string.h>
#include "encoder.h"
#include "main.h"

static char *g_pPwd, *g_pCharset, *g_pOptions, *g_pString;

int main(int argc, char **argv)
{
	char outStrBuf[1024];
	int nextOffset, szInStr, rtVal;
	
	memset(outStrBuf, 0, sizeof(outStrBuf));
	nextOffset = rtVal = 0;
	
	if ( (rtVal = CheckArgc(argc, argv)) != 0 )
	{
		return rtVal;
	}
	
	szInStr = strlen(g_pString);
	
	while ( 1 )
	{
		if ( (rtVal = Encode(g_pCharset, g_pOptions, &g_pString[nextOffset], outStrBuf, sizeof(outStrBuf))) < 0 )
		{
			fprintf(stderr, "Error: Encode() error. (g_pCharset: %s, g_pOptions: %s, g_pString[%d]: %s, szoutStrBuf: %ld)\n",
					g_pCharset, g_pOptions, nextOffset, &g_pString[nextOffset], sizeof(outStrBuf));
			return rtVal;
		}
		
		nextOffset = szInStr - rtVal;
		fprintf(stdout, "%s", outStrBuf);
		
		if ( nextOffset < szInStr )
		{
			continue;
		}
		
		break;
	}
	
	return 0;
}

static int CheckArgc(int argc, char **argv)
{
	if ( (argc != 4) || (strcmp(argv[1], "--help") == 0) || (strcmp(argv[1], "--h") == 0) )
	{
		fprintf(stderr, "\n"												);
		PrintUsage();
		fprintf(stderr, "\n"												);
		PrintCharset();
		fprintf(stderr, "\n"												);
		PrintOptions();
		fprintf(stderr, "\n"												);
		return -1;
	}
	
	g_pPwd     = argv[0];
	g_pCharset = argv[1];
	g_pOptions = argv[2];
	g_pString  = argv[3];
	
	if ( CheckCharset(g_pCharset) != 0 )
	{
		fprintf(stderr, "\n");
		fprintf(stderr, "# Encode: charset error\n");
		fprintf(stderr, "\n");
		PrintCharset();
		fprintf(stderr, "\n");
		return -1;
	}
	
	if ( CheckOptions(g_pOptions) != 0 )
	{
		fprintf(stderr, "\n");
		fprintf(stderr, "# Encode: options error\n");
		fprintf(stderr, "\n");
		PrintOptions();
		fprintf(stderr, "\n");
		return -1;
	}
	
	if ( CheckString(g_pString) != 0 )
	{
		fprintf(stderr, "\n");
		fprintf(stderr, "# Encode: string error\n");
		fprintf(stderr, "\n");
		PrintUsage();
		fprintf(stderr, "\n");
		return -1;
	}
	
	return 0;
}

static void PrintUsage()
{
	fprintf(stderr, "# Encode usage: encode [-charset] [-options] [string]\n");
}

static void PrintCharset()
{
	fprintf(stderr, " [charset]\n"													);
	fprintf(stderr, "  1. hex : use '0~F' hexa, total '16'characters.\n"			);
	fprintf(stderr, "  2. base64 : use '[0-9][A-Z][a-z]+/' total '64'characters.\n"	);
}

static void PrintOptions()
{
	fprintf(stderr, " [options]\n"													);
	fprintf(stderr, "  1. e : encode [string] to [-charset].\n"						);
	fprintf(stderr, "  2. d : decode origin string from [-charset] of [string].\n"	);
}