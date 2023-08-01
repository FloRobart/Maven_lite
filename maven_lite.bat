@echo off

::======::
:: Main ::
::======::
SETLOCAL ENABLEDELAYEDEXPANSION
    ::-----------::
    :: Varaibles ::
    ::-----------::
    set "extensionValide=java"
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

    if "%~1"=="-h" (
        call :help 0
        exit /b 0
    )

    if "%~1"=="--help" (
        call :help 0
    )


    if "%~1"=="-f" (
        if "%~2"=="" (
            call :help 1 "Aucun fichier de configuration donnée pour l'option '-f' ou '--file'"
        )

        if not exist "%~2" (
            echo Le fichier de configuration '%~2' n'existe pas
            exit /b 1
        )

        if "%~x2"=="" (
            echo Le fichier de configuration '%~2' n'est pas un fichier
            exit /b 1
        )

        REM Lire chaque ligne du fichier spécifié ^(argument 2^)
        set /a "argscount=0"
        for /f "usebackq tokens=* delims= " %%i in ("%~2") do (

            REM Supprimer les guillemets doubles de chaque ligne
            set "line=%%i"
            set "line=!line:"=!"

            REM Ajouter la ligne modifiée au tableau args
            set args[!argscount!]=!line!
            set /a "argscount+=1"
        )

        set "args[!argscount!]=-a"

        REM Afficher le contenu du tableau args (facultatif)
        for /l %%i in (0,1,!argscount!) do (
            echo args de %%i : '!args[%%i]!'
        )
    )

ENDLOCAL
EXIT /B 0


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
    echo                          Les fichiers jar doivent être séparés par des ':'.
    echo                          La valeur par defaut du classpath est le dossier de
    echo                          sortie des fichiers compilés si l'option -o est utilisé.
    echo.
    echo   -d , --dependency      Dossier contenant les fichiers jar utiliser
    echo                          par le programme. Tout les fichiers jar seront
    echo                          ajoutés au classpath lors de la compilation
    echo                          et du lancement.
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
goto :eof


::================================================================::
:: Demande à l'utilisateur de rentrer les informations manquantes ::
::================================================================::
:demandeInfo

goto :eof


::=======================================::
:: Vérifie si les arguments sont valides ::
::=======================================::
:: 1 = argument à vérifier, 2 = phrase à afficher si l'argument est invalide
:verifArguments

goto :eof


::=========================::
:: Genère la compile liste ::
::=========================::
:: 1 = dossier source
:genererCompileList

goto :eof


::=========================::
:: Liste les fichiers .jar ::
::=========================::
:listerdependencies

goto :eof


::============================::
:: Compile les fichiers .java ::
::============================::
:compilation

goto :eof


::====================::
:: Lance le programme ::
::====================::
:lancement

goto :eof
