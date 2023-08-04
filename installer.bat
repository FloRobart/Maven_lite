@echo off

set "pathToInstallFolder=C:\Program Files\Maven_Lite"
set "nomFichier=maven_lite.bat"

if not exist "%pathToInstallFolder%" (
    mkdir "%pathToInstallFolder%" || ( echo Erreur lors de la création du dossier '%pathToInstallFolder%'. & exit /b 1 )
)

xcopy /s /y /q "%nomFichier%" "%pathToInstallFolder%" || ( echo Erreur lors de la copie du fichier '%nomFichier%' dans le dossier '%pathToInstallFolder%'. & exit /b 1 )

setx path "%PATH%;%pathToInstallFolder%\%nomFichier%" || ( echo Erreur lors de l'ajout du dossier '%pathToInstallFolder%' dans la variable d'environnement 'PATH'. & exit /b 1 )

echo Installation terminée avec succès.
