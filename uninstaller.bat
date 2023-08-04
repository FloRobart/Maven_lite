@echo off

SETLOCAL ENABLEDELAYEDEXPANSION
set "pathToInstallFolder=C:\Program Files\Maven_Lite"
set "secondePath="

if exist "%pathToInstallFolder%" (
    rmdir /Q /S "%pathToInstallFolder%" || ( echo Erreur lors de la suppression du dossier '%pathToInstallFolder%'. & exit /b 1 )
)

for %%i in ("%PATH:;=" "%") do (
    set "var=%%i"
    set "var=!var:"=!"

    if not "!var!" == "%pathToInstallFolder%" (
        if "!secondePath!" == "" (
            set "secondePath=!var!"
        ) else (
            set "secondePath=!secondePath!;!var!"
        )
    )
)

setx path "%secondePath%" >nul || ( echo Erreur lors de la mdification de la variable d'environnement 'PATH' & exit /b 1 )

echo Désinstallation terminée avec succès.

ENDLOCAL
exit /b 0
