rem ===========================================================
rem [ PATH ]
set BIN_DIR=%cd%
set PRJ_DIR=%BIN_DIR%/..
set SRC_DIR=%PRJ_DIR%/src
set RES_DIR=%PRJ_DIR%/res
set INC_DIR_NCRYPT_HOME=%SRC_DIR%/ncrypt
set INC_DIR_NCRYPT_KISA=%INC_DIR_NCRYPT_HOME%/KISA
set INC_DIR_NCRYPT_ARIA=%INC_DIR_NCRYPT_KISA%/ARIA
set INC_DIR_NCRYPT_HIGHT=%INC_DIR_NCRYPT_KISA%/HIGHT
set INC_DIR_NCRYPT_SEED=%INC_DIR_NCRYPT_KISA%/SEED
rem ===========================================================
rem [ OUTPUT PATH ]
set EXE_OUT_PATH=%BIN_DIR%
set EXE_NAME=ncrypt.exe
rem ===========================================================
rem [ COMMON_CODE ]
set C01=ncryptor_main.c
rem [ NCRYPT_LIB_CODE ]
set NLC01=%INC_DIR_NCRYPT_HOME%/ncrypt.c
set NLC02=%INC_DIR_NCRYPT_HOME%/ncrypt_common.c
set NLC03=%INC_DIR_NCRYPT_ARIA%/ncrypt_aria.c
set NLC04=%INC_DIR_NCRYPT_ARIA%/KISA_ARIA.c
set NLC05=%INC_DIR_NCRYPT_HIGHT%/ncrypt_hight.c
set NLC06=%INC_DIR_NCRYPT_HIGHT%/KISA_HIGHT.c
set NLC07=%INC_DIR_NCRYPT_SEED%/ncrypt_seed.c
set NLC08=%INC_DIR_NCRYPT_SEED%/KISA_SEED.c
rem [ COMMON_LIB_CODE ]
set CLC01=rbmath.c
rem ===========================================================