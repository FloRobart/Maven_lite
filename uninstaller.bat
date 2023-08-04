@echo off

set "pathToInstallFolder=C:\Program Files\Maven_Lite"
set "nomFichierSource=maven_lite.bat"
set "nomFichierDestination=mvnl.bat"

if exist "%pathToInstallFolder%" (
    rmdir /Q /S "%pathToInstallFolder%" || ( echo Erreur lors de la suppression du dossier '%pathToInstallFolder%'. & exit /b 1 )
)

for /f "delims=" %%i in ('echo %PATH% ^| findstr /c:"%pathToInstallFolder%"') do (
    echo i = '%%i'
)

echo Désinstallation terminée avec succès.
exit /b 0
