@echo off

REM Constantes
SET "SUCCES=[SUCCES]"
SET "ERROR=[ERREUR]"

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
        echo %ERROR% Erreur lors de la suppression du dossier '%bin%'.
        exit /b 1
    )
)

echo.
echo %SUCCES% Désinstallation de Maven Lite terminée avec succès.
pause
exit /b 0
