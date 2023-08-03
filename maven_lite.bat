@echo off

::======::
:: Main ::
::======::
SETLOCAL ENABLEDELAYEDEXPANSION
    ::-----------::
    :: Varaibles ::
    ::-----------::
    set "extensionValide=.java"
    set "nomFichierSortie=compile.list"
    set "dependencies="
    set "encoding=UTF-8"
    set /a "compilation=1"
    set /a "lancement=1"

    set "args=%*"
    set "ancienArg=a"


    ::-----------::
    :: Execution ::
    ::-----------::
    if "%args%"=="" (
        call :help 1 "Aucun argument donnée"
    )

    :: Aide ::
    if "%~1"=="-h" (
        call :help 0
        exit /b 0
    )

    if "%~1"=="--help" (
        call :help 0
        exit /b 0
    )

    :: Fichier de configuration ::
    if "%~1"=="-f" (
        call :fileConfig %~2 %~1 || exit /b 1
    )

    if "%~1"=="--file" (
        call :fileConfig %~2 %~1 || exit /b 1
    )

    set "args=%args% -a"
    echo args in main : '%args%'
    for %%a in (%args%) do (
        if "!ancienArg!"=="-s" (
            call :verifArguments %%a "Aucune source donnée pour l'option '-s'" && set "source=%%a" || ( echo erreur source & exit /b 1 )
        ) else if "!ancienArg!"=="--source" (
            call :verifArguments %%a "Aucune source donnée pour l'option '--source'" && set "source=%%a" || exit /b 1
        ) else if "!ancienArg!"=="-o" (
            call :verifArguments %%a "Aucune sortie donnée pour l'option '-o'" && set "output=%%a" || ( echo erreur output & exit /b 1 )
        ) else if "!ancienArg!"=="--output" (
            call :verifArguments %%a "Aucune sortie donnée pour l'option '--output'" && set "output=%%a" || exit /b 1
        ) else if "!ancienArg!"=="-cp" (
            call :verifArguments %%a "Aucun classpath donné pour l'option '-cp'" && set "classpath=%%a" || ( echo erreur cp & exit /b 1 )
        ) else if "!ancienArg!"=="--classpath" (
            call :verifArguments %%a "Aucun classpath donné pour l'option '--classpath'" && set "classpath=%%a" || exit /b 1
        ) else if "!ancienArg!"=="-d" (
            call :verifArguments %%a "Aucune dépendance donnée pour l'option '-d'" && set "dependency=%%a" || ( echo erreur dependance & exit /b 1 )
        ) else if "!ancienArg!"=="--dependency" (
            call :verifArguments %%a "Aucune dépendance donnée pour l'option '--dependency'" && set "dependency=%%a" || exit /b 1
        ) else if "!ancienArg!"=="-n" (
            call :verifArguments %%a "Aucun nom donné pour l'option '-n'" && set "nomFichierSortie=%%a" || exit /b 1
        ) else if "!ancienArg!"=="--name" (
            call :verifArguments %%a "Aucun nom donné pour l'option '--name'" && set "nomFichierSortie=%%a" || exit /b 1
        ) else if "!ancienArg!"=="-e" (
            call :verifArguments %%a "Aucun encodage donné pour l'option '-e'" && set "encoding=%%a" || exit /b 1
        ) else if "!ancienArg!"=="--encoding" (
            call :verifArguments %%a "Aucun encodage donné pour l'option '--encoding'" && set "encoding=%%a" || exit /b 1
        ) else if "!ancienArg!"=="-m" (
            call :verifArguments %%a "Aucun fichier main donné pour l'option '-m'" && set "main=%%a" || ( echo erreur main & exit /b 1 )
        ) else if "!ancienArg!"=="--main" (
            call :verifArguments %%a "Aucun fichier main donné pour l'option '--main'" && set "main=%%a" || exit /b 1
        ) else if "!ancienArg!"=="-dt" (
            call :verifArguments %%a "Aucun dossier de données donné pour l'option '-dt'" && set "data=%%a" || ( echo erreur data & exit /b 1 )
        ) else if "!ancienArg!"=="--data" (
            call :verifArguments %%a "Aucun dossier de données donné pour l'option '--data'" && set "data=%%a" || exit /b 1
        ) else if "!ancienArg!"=="-c" (
            set "compilation=0"
        ) else if "!ancienArg!"=="--compilation" (
            set "compilation=0"
        ) else if "!ancienArg!"=="-l" (
            set "lancement=0"
        ) else if "!ancienArg!"=="--launch" (
            set "lancement=0"
        ) else if "!ancienArg!"=="-cl" (
            set "compilation=0"
            set "lancement=0"
        ) else if "!ancienArg!"=="--compile-launch" (
            set "compilation=0"
            set "lancement=0"
        ) else if "!ancienArg!"=="-lc" (
            set "compilation=0"
            set "lancement=0"
        ) else if "!ancienArg!"=="--launch-compile" (
            set "compilation=0"
            set "lancement=0"
        )

        set "ancienArg=%%a"
    )

    :: Compilation ::
    if "%compilation%"=="0" (
        echo Compilation
    )

    :: Lancement ::
    if "%lancement%"=="0" (
        echo Lancement
    )

    call :genererCompileList %source% && echo compile liste genere || ( echo erreur & exit /b 1 )
ENDLOCAL
EXIT /B 0


::==========================::
:: Fichier de configuration ::
::==========================::
:: 1 = nom du fichier de configuration, 2 = option utilisé
:fileConfig
    if "%~1"=="" (
        call :help 1 "Aucun fichier de configuration donnée pour l'option '%~2'"
    )

    if not exist "%~1" (
        echo Le fichier de configuration '%~1' n'existe pas
        exit /b 1
    )

    if "%~x1"=="" (
        echo Le fichier de configuration '%~1' n'est pas un fichier
        exit /b 1
    )

    REM Lire chaque ligne du fichier spécifié ^(argument 2^)
    for /f "usebackq tokens=* delims=" %%i in ("%~1") do (

        REM Supprimer les guillemets doubles de chaque ligne
        set "line=%%i"
        set "line=!line:"=!"
        set "line=!line:'=!"

        REM Ajouter la ligne modifiée au tableau args
        set "args=!args! !line!"
    )
EXIT /B 0






::================================================================::
:: Demande à l'utilisateur de rentrer les informations manquantes ::
::================================================================::
:demandeInfo
    if "%~1"=="source" (
        set /p "source=Veuillez entrer le dossier racine à partir duquel le fichier compile.list va être généré : "
    ) else (
        if "%~1"=="output" (
            set /p "output=Veuillez entrer le dossier de sortie dans lequel les fichiers .class seront placés : "
        )
    )
goto :eof


::=======================================::
:: Vérifie si les arguments sont valides ::
::=======================================::
:: 1 = argument à vérifier, 2 = phrase à afficher si l'argument est invalide
:verifArguments
    if "%~1"=="" (
        echo %~2
        exit /b 1
    )

    echo %~1 | findstr /irc:"^\-[a-z]" /c:"^\-\-[a-z][a-z]" && (
        echo %~2
        exit /b 1
    )
EXIT /B 0


::=========================::
:: Genère la compile liste ::
::=========================::
:: 1 = dossier source
:genererCompileList
    for %%F in (%~1\*) do (
        if exist "%%F" (
            echo dossier ? '%%~aF'
            if "%%~aF"=="d" (
                call :genererCompileList "%%F"
            ) else if "%%~aF"=="-a-" (
                for %%X in ("%%F") do (
                    if "%%~xX"=="%extensionValide%" (
                        echo fichier : '%%~X'
                        echo %%~F>>"%nomFichierSortie%"
                    )
                )
            )
        )
    )
goto :eof


::=========================::
:: Liste les fichiers .jar ::
::=========================::
:: 1 = dossier source
:listerdependencies
    if not "%dependency%"=="" (
        for %%F in (%~1\*) do (
            if exist "%%F" (
                if "%%~xF"==".jar" (
                    set "dependencies=!dependencies!%%~F;"
                )
            )
        )
    )
goto :eof


::============================::
:: Compile les fichiers .java ::
::============================::
:compilation
    echo Compilation...
    call javac -cp "%classpath%;%dependencies%" -encoding %encoding% -d "%output%" "@%nomFichierSortie%" && echo Fin de la compilation. || ( echo Erreur lors de la compilation. & exit 1 )
goto :eof


::====================::
:: Lance le programme ::
::====================::
:lancement
    echo Lancement du programme...
    call java -cp "%classpath%;%dependencies%" %main% && echo Fin de l'execution. || ( echo Erreur lors du lancement du programme. & exit 1 )
goto :eof




::======::
:: Aide ::
::======::
:: 1 = status de sortie, 2 = message optionnel
:help
    echo Utilisation : mvn_lite.sh ^[options^] ^[arguments^]
    echo Permet de compiler et lancer un projet java en utilisant
    echo le minimum d'options et de manipulation.
    echo Facilite la compilation et le lancement d'un projet java
    echo plus simplement que maven.
    echo.
    echo Toutes les options prennent un seul argument sauf -c et -l qui n'en prennent aucun.
    echo.
    echo L'ordre des options n'a pas d'importance sauf pour l'option -f
    echo qui doit être la première option de la ligne de commande.
    echo les guillements double ne sont pas obligatoires, sauf si l'argument contient un espace
    echo.
    echo Les options qui sont utilisé avec -c alors qu'elles sont utilisable
    echo uniquement avec -l sont ignorées et inversement.
    echo.
    echo Les options qui sont utilisable avec -c ou -l sont utilisable avec -cl et -lc.
    echo.
    echo Les arguments obligatoires pour les options longues le sont aussi pour les options courtes :
    echo   -s , --source          Dossier racine du projet à compiler.
    echo.
    echo   -o , --output          Dossier de sortie des fichiers compilés.
    echo.
    echo   -cp, --classpath       Liste des fichiers jar et du dossier de sortie
    echo                          des fichiers compilés ^(le même dossier que pour
    echo                          l'option -o^) à ajouter au classpath lors de la
    echo                          compilation et du lancement.
    echo                          Les fichiers jar doivent être séparés par des ';'.
    echo                          La valeur par defaut du classpath est le dossier de
    echo                          sortie des fichiers compilés si l'option -o est utilisé,
    echo                          sinon le classpath sera le dossier courent.
    echo.
    echo   -d , --dependency      Dossier contenant les fichiers jar utiliser
    echo                          par le programme. Tout les fichiers jar seront
    echo                          ajoutés au classpath lors de la compilation
    echo                          et du lancement. Tout les fichiers jar doivent être
    echo                          placés dans le même dossier.
    echo.
    echo   -n , --name            Permet de changer le nom du fichier contenant les
    echo                          chemins des fichiers java à compiler. Le nom par
    echo                          defaut est 'compile.list'.
    echo                          Utilisable uniquement avec l'option -c.
    echo.
    echo   -e , --encoding        Permet de changer l'encodage des fichiers java
    echo                          à compiler. L'encodage par defaut est 'UTF-8'.
    echo                          Utilisable uniquement avec l'option -c.
    echo.
    echo   -m , --main            Classe principale à lancer. Utilisable
    echo                          uniquement avec l'option -l.
    echo.
    echo   -dt, --data            Dossier contenant les données du projet.
    echo                          Permet de copier le dossier de données
    echo                          dans le dossier de sortie. Utilisable
    echo                          uniquement avec l'option -c.
    echo.
    echo   -f , --file            Fichier de configuration. Permet de charger
    echo                          les options à partir d'un fichier de configuration,
    echo                          le séparateur sont l'espace et le retour à la ligne.
    echo                          Les options du fichier de configuration prédomine
    echo                          sur les options de la ligne de commande.
    echo                          L'option -f doit obligatoirement être la première
    echo                          option de la ligne de commande.
    echo.
    echo.
    echo   -c , --compilation     Compiler le projet.
    echo.
    echo   -l , --launch          Lancer le projet.
    echo.
    echo   -cl, --compile-launch  Compiler et lancer le projet. ^(équivalent à -c -l^)
    echo.
    echo   -lc, --launch-compile  Compiler et lancer le projet. ^(équivalent à -c -l^)
    echo.
    echo.
    echo   -h , --help            afficher l'aide et quitter.
    echo.
    echo.
    echo Les options minimum obligatoires pour la compilation sont :
    echo   -s, --source       Dossier racine du projet à compiler.
    echo   -o, --output       Dossier de sortie des fichiers compilés.
    echo   -c, --compilation  Compiler le projet.
    echo.
    echo.
    echo Les options minimum obligatoires pour le lancement sont :
    echo   -m , --main       Classe principale à lancer.
    echo   -l , --launch     Lancer le projet.
    echo   -cp, --classpath  Voir l'option -cp dans la listes des options.
    echo.
    echo.
    echo Les options minimum obligatoires pour la compilation et le lancement sont :
    echo   -s , --source          Dossier racine du projet à compiler.
    echo   -o , --output          Dossier de sortie des fichiers compilés.
    echo   -m , --main            Classe principale à lancer.
    echo   -cl, --compile-launch  Compiler et lancer le projet. ^(équivalent à -c -l^)
    echo.
    echo.
    echo.
    echo Exemple d'utiliation : './mvn_lite.sh -s src -o ./bin -m "controleur.Main" -cl'
    echo                        Cette ligne de commande va compiler et lancer le projet
    echo                        java contenu dans le dossier 'src' et lancer la classe
    echo                        'controleur.Main' avec le classpath './bin'.
    echo                        Tout les fichiers compilés seront dans le dossier 'bin'.


    if not "%~2"=="" (
        echo.
        echo.
        echo "%~2"
        echo.
    )


exit /b %~1
