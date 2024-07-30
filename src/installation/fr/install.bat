@echo off

REM Ce fichier fait partie du projet Maven Lite
REM Copyright (C) 2024 Floris Robart florobart.github@gmail.com

REM Constantes
SET "SUCCES=[SUCCES]"
SET "ERROR=[ERREUR]"

REM Variables
SET "mavenLiteFolder=maven-lite"
SET "bin=C:\Program Files\%mavenLiteFolder%"
SET "etc=%bin%\etc"

SET "binFileUn=mvnl.bat"
SET "binFileDeux=mvnl-uninstall.bat"
SET "classFile=MavenLite.class"
SET "junitJarFileUn=junit-4.13.2.jar"
SET "junitJarFileDeux=hamcrest-core-1.3.jar"

SET "courantDir=%~dp0"

REM Création du dossier C:\Program Files\Maven_Lite
IF NOT EXIST "%bin%" (
    MKDIR "%bin%" && (
        echo "%SUCCES% Dossier '%bin%' créé avec succès."
    ) || (
        echo "%ERROR% Erreur lors de la création du dossier '%bin%'."
        call :exitPerso
        exit /b 1
    )
)

REM Création du dossier C:\Program Files\Maven_Lite\etc
IF NOT EXIST "%etc%" (
    MKDIR "%etc%" && (
        echo "%SUCCES% Dossier '%etc%' créé avec succès."
    ) || (
        echo "%ERROR% Erreur lors de la création du dossier '%etc%'."
        call :exitPerso
        exit /b 1
    )
)

REM Déplacement des fichiers shell dans le dossier bin
COPY /Y "%courantDir%\%binFileUn%" "%bin%" && ( echo "%SUCCES% Fichier '%binFileUn%' copie avec succès." ) || ( echo "%ERROR% Erreur lors de la copie du fichier '%binFileUn%'." & call :exitPerso & exit /b 1 )
COPY /Y "%courantDir%\%binFileDeux%" "%bin%" && ( echo "%SUCCES% Fichier '%binFileDeux%' copie avec succès." ) || ( echo "%ERROR% Erreur lors de la copie du fichier '%binFileDeux%'." & call :exitPerso & exit /b 1 )

REM Déplacement du fichier class dans le dossier etc
COPY /Y "%courantDir%\%classFile%" "%etc%" && ( echo "%SUCCES% Fichier '%classFile%' copie avec succès." ) || ( echo "%ERROR% Erreur lors de la copie du fichier '%classFile%'." & call :exitPerso & exit /b 1 )

REM Déplacement des fichiers jar dans le dossier etc
COPY /Y "%courantDir%\%junitJarFileUn%" "%etc%" && ( echo "%SUCCES% Fichier '%junitJarFileUn%' copie avec succès." ) || ( echo "%ERROR% Erreur lors de la copie du fichier '%junitJarFileUn%'." & call :exitPerso & exit /b 1 )
COPY /Y "%courantDir%\%junitJarFileDeux%" "%etc%" && ( echo "%SUCCES% Fichier '%junitJarFileDeux%' copie avec succès." ) || ( echo "%ERROR% Erreur lors de la copie du fichier '%junitJarFileDeux%'." & call :exitPerso & exit /b 1 )

echo.
echo "[INFO] Ajoutez le dossier '%bin%' dans la variable d'environnement système 'Path'."
echo "%SUCCES% Installation terminée avec succès."
pause
exit /b 0

REM Fait une pause et afficher un message d'aide
:exitPerso
    echo.
    echo Essayer de relancer le programme en tant qu'administrateur.
    echo.
    pause
goto :EOF