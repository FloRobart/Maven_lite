@echo off

REM Constantes
SET "SUCCES=[SUCCES]"
SET "ERROR=[ERREUR]"

REM Variables
SET "mavenLiteFolder=maven-lite"
SET "bin=C:\Program Files\%mavenLiteFolder%"
SET "etc=C:\Program Files\%mavenLiteFolder%\etc"

SET "binFileUn=mvnl"
SET "binFileDeux=mvnl-uninstall"
SET "classFile=MavenLite.class"
SET "junitJarFileUn=junit-4.13.2.jar"
SET "junitJarFileDeux=hamcrest-core-1.3.jar"

REM Création du dossier C:\Program Files\Maven_Lite
IF NOT EXIST "%bin%\%mavenLiteFolder%" (
    MKDIR "%bin%\%mavenLiteFolder%" || (
        echo "%ERROR% Erreur lors de la création du dossier '%bin%\%mavenLiteFolder%'."
        exit /b 1
    )
)

REM Déplacement des fichiers shell dans le dossier bin
MOVE "%binFileUn%" "%bin%" || (echo "%ERROR% Erreur lors du déplacement du fichier '%binFileUn%'." & exit /b 1)
MOVE "%binFileDeux%" "%bin%" || (echo "%ERROR% Erreur lors du déplacement du fichier '%binFileDeux%'." & exit /b 1)

REM Déplacement du fichier class dans le dossier etc
MOVE "%classFile%" "%etc%" || (echo "%ERROR% Erreur lors du déplacement du fichier '%classFile%'." & exit /b 1)

REM Déplacement des fichiers jar dans le dossier etc
MOVE "%junitJarFileUn%" "%etc%" || (echo "%ERROR% Erreur lors du déplacement du fichier '%junitJarFileUn%'." & exit /b 1)
MOVE "%junitJarFileDeux%" "%etc%" || (echo "%ERROR% Erreur lors du déplacement du fichier '%junitJarFileDeux%'." & exit /b 1)

echo "%SUCCES% Installation terminée avec succès."
pause
exit /b 0
