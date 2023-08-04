@echo off

set "pathToInstallFolder=C:\Program Files\Maven_Lite"
set "nomFichierSource=mvnl.bat"
set "nomFichierDestination=mvnl.bat"

if not exist "%pathToInstallFolder%" (
    mkdir "%pathToInstallFolder%" || ( echo Erreur lors de la création du dossier '%pathToInstallFolder%'. & exit /b 1 )
)

copy /s /y /q "%nomFichierSource%" "%pathToInstallFolder%\%nomFichierDestination%" || ( echo Erreur lors de la copie du fichier '%nomFichierSource%' dans le dossier '%pathToInstallFolder%'. & exit /b 1 )

echo Installation terminée avec succès.
exit /b 0
setx path "%PATH%;%pathToInstallFolder%" || ( echo Erreur lors de l'ajout du dossier '%pathToInstallFolder%' dans la variable d'environnement 'PATH'. & exit /b 1 )

