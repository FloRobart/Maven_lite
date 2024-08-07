@echo off
setlocal enabledelayedexpansion

REM This file is part of Maven Lite.
REM Copyright (C) 2024 Floris Robart florobart.github@gmail.com

REM Remplacement de l'espace par le caractère spécifique
SET "SPACE_REPLACEMENT=#;#SPACE#;#"
SET "args="

REM Boucle à travers les arguments
for %%i in (%*) do (
    SET "arg=%%i"
    SET "arg=!arg: =%SPACE_REPLACEMENT%!"
    SET "args=!args! '!arg!'"
)

REM Exécution de la commande Java
call powershell java -cp 'C:\Program Files\maven-lite\etc' MavenLite '%SPACE_REPLACEMENT%' %args%

endlocal

