@echo off
rem ===========================================================
rem [ CMD Setting ]
setlocal
rem ===========================================================
rem [ Load batches ]
call paths.bat
rem ===========================================================
rem [ Compile and build .exe ]
cd %SRC_DIR%
gcc -o %EXE_OUT_PATH%/%EXE_NAME% ^
%C01% ^
%NLC01% %NLC02% %NLC03% %NLC04% %NLC05% %NLC06% %NLC07% %NLC08% ^
%CLC01% ^
-I %SRC_DIR% -I %INC_DIR_NCRYPT_HOME%
cd %BIN_DIR%
rem ===========================================================
echo "[%EXE_NAME%] 컴파일 수행 완료. (Code:%ERRORLEVEL%)"
rem ===========================================================
rem [ CMD End setting ]
endlocal
rem ===========================================================