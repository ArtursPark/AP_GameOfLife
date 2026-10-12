@echo off
setlocal
set "GOL_BASH="

if exist "%ProgramFiles%\Git\bin\bash.exe" set "GOL_BASH=%ProgramFiles%\Git\bin\bash.exe"
if not defined GOL_BASH if exist "%LocalAppData%\Programs\Git\bin\bash.exe" set "GOL_BASH=%LocalAppData%\Programs\Git\bin\bash.exe"
if not defined GOL_BASH for /f "delims=" %%G in ('where git.exe 2^>nul') do if exist "%%~dpG..\bin\bash.exe" set "GOL_BASH=%%~dpG..\bin\bash.exe"

if not defined GOL_BASH (
    echo Git Bash is required. Install Git for Windows or run run.sh from a Bash terminal. >&2
    exit /b 1
)

"%GOL_BASH%" "%~dp0run.sh" %*
exit /b %ERRORLEVEL%
