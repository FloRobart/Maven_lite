@echo off

REM this file is part of Maven_Lite
REM Copyright (C) 2024 Floris Robart florobart.github@gmail.com

REM Constantes
SET "SUCCES=[SUCCESS]"
SET "ERROR=[ERROR]"

REM Variables
SET "mavenLiteFolder=maven-lite"
SET "bin=C:\Program Files\%mavenLiteFolder%"
SET "etc=%bin%\etc"

SET "binFileUn=mvnl.bat"
SET "binFileDeux=mvnl-uninstall.bat"
SET "classFile=MavenLite.class"
SET "junitJarFileUn=junit-4.13.2.jar"
SET "junitJarFileDeux=hamcrest-core-1.3.jar"
SET "lang=lang"

SET "courantDir=%~dp0"

REM Creating the C:\Program Files\Maven_Lite directory
IF NOT EXIST "%bin%" (
    MKDIR "%bin%" && (
        echo "%SUCCESS% Directory '%bin%' created successfully."
    ) || (
        echo "%ERROR% Error creating the directory '%bin%'."
        call :exitPersonal
        exit /b 1
    )
)

REM Creating the C:\Program Files\Maven_Lite\etc directory
IF NOT EXIST "%etc%" (
    MKDIR "%etc%" && (
        echo "%SUCCESS% Directory '%etc%' created successfully."
    ) || (
        echo "%ERROR% Error creating the directory '%etc%'."
        call :exitPersonal
        exit /b 1
    )
)

REM Moving shell files to the bin directory
COPY /Y "%currentDir%\%binFileUn%" "%bin%" && ( echo "%SUCCESS% File '%binFileUn%' copied successfully." ) || ( echo "%ERROR% Error copying the file '%binFileUn%'." & call :exitPersonal & exit /b 1 )
COPY /Y "%currentDir%\%binFileDeux%" "%bin%" && ( echo "%SUCCESS% File '%binFileDeux%' copied successfully." ) || ( echo "%ERROR% Error copying the file '%binFileDeux%'." & call :exitPersonal & exit /b 1 )

REM Moving the class file to the etc directory
COPY /Y "%currentDir%\%classFile%" "%etc%" && ( echo "%SUCCESS% File '%classFile%' copied successfully." ) || ( echo "%ERROR% Error copying the file '%classFile%'." & call :exitPersonal & exit /b 1 )

REM Moving jar files to the etc directory
COPY /Y "%currentDir%\%junitJarFileUn%" "%etc%" && ( echo "%SUCCESS% File '%junitJarFileUn%' copied successfully." ) || ( echo "%ERROR% Error copying the file '%junitJarFileUn%'." & call :exitPersonal & exit /b 1 )
COPY /Y "%currentDir%\%junitJarFileDeux%" "%etc%" && ( echo "%SUCCESS% File '%junitJarFileDeux%' copied successfully." ) || ( echo "%ERROR% Error copying the file '%junitJarFileDeux%'." & call :exitPersonal & exit /b 1 )

REM Moving the lang directory to the etc directory
COPY /Y "%currentDir%\%lang%" "%etc%" && ( echo "%SUCCESS% Directory '%lang%' copied successfully." ) || ( echo "%ERROR% Error copying the directory '%lang%'." & call :exitPersonal & exit /b 1 )

echo.
echo "[INFO] Add the '%bin%' directory to the system 'Path' environment variable."
echo "%SUCCESS% Installation completed successfully."
pause
exit /b 0

REM Pause and display a help message
:exitPersonal
    echo.
    echo Try restarting the program as an administrator.
    echo.
    pause
goto :EOF
