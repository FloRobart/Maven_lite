@echo off

::======::
:: Main ::
::======::
SETLOCAL ENABLEDELAYEDEXPANSION
    ::-----------::
    :: Varaibles ::
    ::-----------::
    set "version=1.1.0"
    set "extensionValide=.java"
    set "nomFichierSortie=compile.list"
    set "encoding=UTF-8"
    set "dependencies="
    set "data="
    set "arguments="
    set /a "compilation=1"
    set /a "lancement=1"

    set "args=%*"
    set "ancienArg=a"


    ::-----------::
    :: Execution ::
    ::-----------::
    if "%args%"=="" (
        call :help 1 "Aucun argument donnée"
        exit /b 1
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

    :: Version ::
    if "%~1"=="-v" (
        echo %version%
        exit /b 0
    )

    if "%~1"=="--version" (
        echo Maven Lite Version %version%
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
    set "args=%args:"=%"
    set "args=%args:'=%"
    for %%a in (%args%) do (
        if "!ancienArg!"=="-s" (
            call :verifArguments %%a "Aucune source donnée pour l'option '-s'" && set "source=%%a" || exit /b 1
        ) else if "!ancienArg!"=="--source" (
            call :verifArguments %%a "Aucune source donnée pour l'option '--source'" && set "source=%%a" || exit /b 1
        ) else if "!ancienArg!"=="-o" (
            call :verifArguments %%a "Aucune sortie donnée pour l'option '-o'" && set "output=%%a" || exit /b 1
        ) else if "!ancienArg!"=="--output" (
            call :verifArguments %%a "Aucune sortie donnée pour l'option '--output'" && set "output=%%a" || exit /b 1
        ) else if "!ancienArg!"=="-cp" (
            call :verifArguments %%a "Aucun classpath donné pour l'option '-cp'" && set "classpath=%%a" || exit /b 1
        ) else if "!ancienArg!"=="--classpath" (
            call :verifArguments %%a "Aucun classpath donné pour l'option '--classpath'" && set "classpath=%%a" || exit /b 1
        ) else if "!ancienArg!"=="-d" (
            call :verifArguments %%a "Aucune dépendance donnée pour l'option '-d'" && set "dependency=%%a" || exit /b 1
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
            call :verifArguments %%a "Aucun fichier main donné pour l'option '-m'" && set "main=%%a" || exit /b 1
        ) else if "!ancienArg!"=="--main" (
            call :verifArguments %%a "Aucun fichier main donné pour l'option '--main'" && set "main=%%a" || exit /b 1
        ) else if "!ancienArg!"=="-dt" (
            call :verifArguments %%a "Aucun dossier de données donné pour l'option '-dt'" && set "data=%%a" || exit /b 1
        ) else if "!ancienArg!"=="--data" (
            call :verifArguments %%a "Aucun dossier de données donné pour l'option '--data'" && set "data=%%a" || exit /b 1
        ) else if "!ancienArg!"=="-arg" (
            call :verifArguments %%a "Aucun argument donné pour l'option '-arg'" && set "arguments=!arguments!"%%a" " || exit /b 1
        ) else if "!ancienArg!"=="--argument" (
            call :verifArguments %%a "Aucun argument donné pour l'option '--argument'" && set "arguments=!arguments!"%%a" " || exit /b 1
        ) else if "!ancienArg!"=="--cr" (
            call :createProjet %%a && exit /b 0 || exit /b 1
        ) else if "!ancienArg!"=="--create" (
            call :createProjet %%a && exit /b 0 || exit /b 1
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

    if "%compilation%" EQU "1" (
        if "%lancement%" EQU "1" (
            call :help 1 "Aucune action donnée"
        )
    )

    :: Compilation ::
    if "%compilation%"=="0" (
        :: Vérification du dossier source
        :verifSource
        if "%source:~-1%" == "\" set "source=%source:~0,-1%"
        dir "%source%\" > nul 2>&1 && (
            if "%source%"=="" (
                echo Le dossier source '%source%' n'existe pas
                call :demandeInfo source
                goto :verifSource
            ) else (
                goto :verifOutput
            )
        ) || (
            echo Le dossier source '%source%' n'existe pas
            call :demandeInfo source
            goto :verifSource
        )

        :: Vérification du dossier de sortie
        :verifOutput
        if "%output:~-1%" == "\" set "output=%output:~0,-1%"
        if "%output%"=="" (
            echo aucun dossier de sortie donnee
            call :demandeInfo output
            goto :verifOutput
        )

        :: Vérification du dossier de sortie
        dir "%output%" > nul 2>&1 && (
            if not exist "%output%\" (
                echo '%output%' n'est pas un dossier.
                exit /b 1
            )
        ) || (
            set /p "reponse=Le dossier de sortie '%output%' n'existe pas. Voulez-vous le créer ? (y/n) : "
            echo !reponse! | findstr /irc:"^y" >nul 2>&1 && (
                mkdir "%output%" >nul 2>&1 && (
                    echo Le dossier '%output%' a été créé
                ) || (
                    echo Erreur lors de la création du dossier '%output%'
                    exit /b 1
                )
            ) || (
                echo Le dossier '%output%' n'a pas été créé
                exit /b 0
            )
        )

        if "%output:~-1%" == "\" set "output=%output:~0,-1%"
        if "%output%"=="%source%" (
            echo Le dossier source doit être différent du dossier de sortie
            exit /b 1
        )

        :: Copie du dossier de données
        if not "%data%"=="" (
            if "%data:~-1%" == "\" (
                set "data=%data:~0,-1%"
            )

            if exist "!data!\" (
                for %%f in ("!data!") do set "dataLastFolder=%%~nxf"
                if not exist "%output%\!dataLastFolder!" mkdir %output%\!dataLastFolder!
                xcopy /E /Q /Y "!data!" "%output%\!dataLastFolder!" >nul 2>&1 || (
                    echo Erreur lors de la copie du dossier '!data!' dans le dossier '%output%'
                    exit /b 1
                )
            ) else (
                if exist "!data!" (
                    echo Le dossier de données '!data!' n'est pas un dossier
                    exit /b 1
                ) else (
                    echo Le dossier de données '!data!' n'existe pas
                    exit /b 1
                )
            )
        )

        if "%classpath%"=="" set "classpath=%output%"
        call :listerdependencies %dependency%

        :: Génération du fichier de compilation
        if exist "%nomFichierSortie%" del /Q /F "%nomFichierSortie%" >nul 2>&1
        call :genererCompileList
        if not exist "%nomFichierSortie%" (
            echo Erreur lors de la génération du fichier '%nomFichierSortie%'.
            exit /b 1
        )

        call :compilation || exit /b 1
        if exist "%nomFichierSortie%" del /Q /F "%nomFichierSortie%" >nul 2>&1
    )

    :: Lancement ::
    if "%lancement%"=="0" (
        set "classpath=%classpath%;%output%"
        if "%classpath%"==";" (
            call :help 1 "L'option -cp ou --classpath est obligatoire pour lancer le programme"
            exit /b 1
        )

        if "%main%"=="" (
            call :help 1 "L'option -m ou --main est obligatoire pour lancer le programme"
            exit /b 1
        )

        call :listerdependencies %dependency%
        call :lancement || exit /b 1
    )
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

    echo %~1 | findstr /irc:"^\-[a-z]" /c:"^\-\-[a-z][a-z]" >nul 2>&1 && (
        echo %~2
        exit /b 1
    )
EXIT /B 0


::=========================::
:: Genère la compile liste ::
::=========================::
:: 1 = dossier source
:genererCompileList
    call :listerDossiers
    call :listerFichiers
goto :eof

:: Listes les dossiers ::
:listerDossiers
    FOR /f %%i IN ('dir "!source!" /b /ad') DO (
        dir "!source!" /b /a-d 2>nul >nul && ( call :listerFichiers )

        set "source=%source%\%%i"

        call :listerDossiers
    )
goto :eof

:: Listes les fichiers ::
:listerFichiers
    :: liste les fichiers dans le dossier courant ::
    FOR /f %%i IN ('dir "!source!" /b /a-d') DO (
        set "extension=%%~xi"
        IF "%%~xi" == "%extensionValide%" ( echo !source!\%%i >> %nomFichierSortie% )
    )

    IF "!extension!" == "%extensionValide%" ( echo.>> %nomFichierSortie% )
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


::====================================::
:: Creer larborescence du projet java ::
::====================================::
:: 1 = dossier source du projet
:createProjet
    set "projectFolder=%~1"

    if "%projectFolder%"=="" (
        set "projectFolder=."
    )

    :: if exist
    if not exist "%projectFolder%" (
        mkdir "%projectFolder%" >nul 2>&1 && (
            echo Le dossier '%projectFolder%' a été créé
        ) || (
            echo Erreur lors de la création du dossier '%projectFolder%'
            exit /b 1
        )
    )

    mkdir %projectFolder%\src >nul 2>&1 && (
        echo Le dossier '%projectFolder%\src' a été créé
    ) || (
        echo Erreur lors de la création du dossier '%projectFolder%\src'
        exit /b 1
    )

    mkdir %projectFolder%\bin >nul 2>&1 && (
        echo Le dossier '%projectFolder%\bin' a été créé
    ) || (
        echo Erreur lors de la création du dossier '%projectFolder%\bin'
        exit /b 1
    )

    mkdir %projectFolder%\data >nul 2>&1 && (
        echo Le dossier '%projectFolder%\data' a été créé
    ) || (
        echo Erreur lors de la création du dossier '%projectFolder%\data'
        exit /b 1
    )

    set "mainFile=%projectFolder%\src\Main.java"
    echo public class Main {>> %mainFile%
    echo     public static void main(String[] args) {>> %mainFile%
    echo         System.out.println("Le projet à été créé");>> %mainFile%
    echo     }>> %mainFile%
    echo }>> %mainFile%

    echo --source %projectFolder%\src>> config.txt
    echo --output %projectFolder%\bin>> config.txt
    echo --main Main>> config.txt
    echo --compile-launch >> config.txt
goto :eof


::============================::
:: Compile les fichiers .java ::
::============================::
:compilation
    echo Compilation...
    call javac -cp "%classpath%;%dependencies%" -encoding %encoding% -d "%output%" "@%nomFichierSortie%" && ( echo Fin de la compilation. & exit /b 0 ) || ( echo Erreur lors de la compilation. & exit /b 1 )
goto :eof


::====================::
:: Lance le programme ::
::====================::
:lancement
    echo Lancement du programme...
    call java -cp "%classpath%;%dependencies%" %main% %arguments% && ( echo Fin de l'execution. & exit /b 0 ) || ( echo Erreur lors du lancement du programme. & exit /b 1 )
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
    echo L'ordre des options n'a pas d'importance sauf pour l'option -f, -h et -v
    echo qui doit être la première option de la ligne de commande.
    echo les guillements double ne sont pas obligatoires, sauf si l'argument contient un espace
    echo.
    echo Les options qui sont utilisé avec -c alors qu'elles sont utilisable
    echo uniquement avec -l sont ignorées et inversement.
    echo.
    echo Les options qui sont utilisable avec -c ou -l sont utilisable avec -cl et -lc.
    echo.
    echo Les arguments obligatoires pour les options longues le sont aussi pour les options courtes :
    echo   -v , --version         Affiche la version du script, actuellement en version %version%.
    echo.
    echo   -cr , --create          Creer l'arborescence du projet ainsi qu'un
    echo                           fichier de config par défaut. Si le dossier
    echo                           de sortie n'est pas spécifié, le dossier
    echo                           par défaut est le dossier courant.
    echo.
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
    echo   -arg, --arguments      Arguments à passer à la classe principale.
    echo                          Un argument par option, c'est à dire que si vous
    echo                          voulez passer deux arguments il faudra utiliser
    echo                          deux fois l'option -arg. Lordre des arguments passé
    echo                          à la classe principale est le même que l'ordre de
    echo                          passage à la commande. Les arguments de la ligne de
    echo                          commande sont pris en compte avant les arguments du
    echo                          fichier de configuration. Les arguments ne peuvent
    echo                          pas contenir d'espace sans peine de bug.
    echo                          Utilisable uniquement avec l'option -l.
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
    echo Exemple d'utiliation : 'mvnl -s src -o ./bin -m "controleur.Main" -cl'
    echo                        Cette ligne de commande va compiler et lancer le projet
    echo                        java contenu dans le dossier 'src' et lancer la classe
    echo                        'controleur.Main' avec le classpath './bin'.
    echo                        Tout les fichiers compilés seront dans le dossier 'bin'.


    if not "%~2"=="" (
        echo.
        echo.
        echo %~2
        echo.
    )


exit /b %~1
