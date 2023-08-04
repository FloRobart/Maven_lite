@echo off

set "pathToInstallFolder=C:\Program Files\Maven_Lite"
set "nomFichier=mvnl.bat"

if not exist "%pathToInstallFolder%" (
    mkdir "%pathToInstallFolder%" || ( echo Erreur lors de la création du dossier '%pathToInstallFolder%'. & exit /b 1 )
)

copy /s /y /q "%nomFichier%" "%pathToInstallFolder%\%nomFichier%" || ( echo Erreur lors de la copie du fichier '%nomFichier%' dans le dossier '%pathToInstallFolder%'. & exit /b 1 )

echo Installation terminée avec succès.
exit /b 0
setx path "%PATH%;%pathToInstallFolder%" || ( echo Erreur lors de l'ajout du dossier '%pathToInstallFolder%' dans la variable d'environnement 'PATH'. & exit /b 1 )

