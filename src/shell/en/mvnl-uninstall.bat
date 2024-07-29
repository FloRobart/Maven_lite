@echo off

REM This file is part of Maven Lite.
REM Copyright (C) 2024 Floris Robart florisrobart.pro@gmail.com

REM Constantes
SET "SUCCES=[SUCCESS]"
SET "ERROR=[ERROR]"

REM Variables
SET "mavenLiteFolder=maven-lite"
SET "bin=C:\Program Files\%mavenLiteFolder%"

REM demander confirmation
call powershell mvnl uninstall_from_mvnl-uninstall

IF %ERRORLEVEL% GTR 0 (
    exit /b 0
)

REM Suppression du dossier bin et de tout son contenu
IF EXIST "%bin%" (
    RMDIR /S /Q "%bin%" || (
        echo %ERROR% Error occurred during the deletion of the '%bin%' folder.
        call :exitPerso
        exit /b 1
    )
)

echo.
echo %SUCCES% Maven Lite uninstallation completed successfully.
pause
exit /b 0

REM Fait une pause et afficher un message d'aide
:exitPerso
    echo.
    echo Try relaunching the program as an administrator.
    echo.
    pause
goto :EOF