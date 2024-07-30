@echo off

REM Ce fichier fait partie de Maven Lite.
REM Copyright (C) 2024 Floris Robart florobart.github@gmail.com

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
        call :exitPerso
        exit /b 1
    )
)

echo.
echo %SUCCES% Désinstallation de Maven Lite terminée avec succès.
pause
exit /b 0

REM Fait une pause et afficher un message d'aide
:exitPerso
    echo.
    echo Essayer de relancer le programme en tant qu'administrateur.
    echo.
    pause
goto :EOF