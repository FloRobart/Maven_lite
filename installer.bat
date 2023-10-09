@echo off

setlocal EnableDelayedExpansion
    set "pathToInstallFolder=C:\Program Files\Maven_Lite"
    set "nomFichierSource=maven_lite.bat"
    set "nomFichierDestination=mvnl.bat"

    xcopy /s /y /q ".\%nomFichierSource%" "%pathToInstallFolder%\" >nul || ( echo Erreur lors de la copie du fichier '%nomFichierSource%' dans le dossier '%pathToInstallFolder%'. & exit /b 1 )

    if exist "%pathToInstallFolder%\%nomFichierDestination%" del /q /f "%pathToInstallFolder%\%nomFichierDestination%"

    rename "%pathToInstallFolder%\%nomFichierSource%" "%nomFichierDestination%" >nul || ( echo Erreur lors du renomage du fichier '%nomFichierSource%'. & exit /b 1 )

    echo %PATH% | findstr /c:"%pathToInstallFolder%" >nul 2>nul || (
        setx path "%PATH%;%pathToInstallFolder%" >nul || ( echo Erreur lors de l'ajout du dossier '%pathToInstallFolder%' dans la variable d'environnement 'PATH'. & exit /b 1 )
    )

    echo Installation terminée avec succès.
endlocal

exit /b 0