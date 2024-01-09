@echo off
setlocal EnableDelayedExpansion

set "args="

REM Parcourir tous les arguments en ligne de commande
for %%i in (%*) do (
    REM Si l'argument commence par un backslash, on le double
    set "arg=%%i"
    if "!arg:~0,1!"=="\" (
        set "args=!args! ^"!arg!"^"
    ) else (
        set "args=!args! !arg!"
    )
)

echo java -cp "C:\Users\%USER_NAME%\.maven-lite\" MavenLite %args%

endlocal
exit /b


start java -cp "C:\Users\%USER_NAME%\.maven-lite\" MavenLite %args%

endlocal
