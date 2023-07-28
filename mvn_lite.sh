#!/bin/bash

#======#
# Aide #
#======#
# $1 = status de sortie
function help()
{
    echo 'help'
    # voir mkdir --help pour créer l'aide
    exit $1
}

#================================================================#
# Demande à l'utilisateur de rentrer les informations manquantes #
#================================================================#
function demandeInfo()
{
    if [ "$1" = "source" ]
    then
        read -p "Veuillez entrez le dossier racine à partir du quel la compile.list vas être générer : " source
    else
        if [ "$1" = "output" ]
        then
            read -p "Veuillez entrez le dossier de output dans lequel vont être les fichier .class : " output
        fi
    fi
}


#=======================================#
# Vérifie si les arguments sont valides #
#=======================================#
# $1 = argument à vérifier, $2 = phrase à afficher si l'argument est invalide
function verifArguments()
{
    [[ ${1} =~ ^-{1,2}[a-z]+$ ]] && { echo "$2"; help 1; }
    [[ ${1} =~ ^$ ]] && { echo "$2"; help 1; }

    return 0
}

#=========================#
# Genère la compile liste #
#=========================#
# $1 = dossier source
function genererCompileList()
{
    for file in `ls -1 $1`    # permet de parcourir chaque éléments d'un répertoire
    do
        [[ -f $1"/"$file ]] && { [[ "${file##*.}" = "$extensionValide" ]] && echo $1"/"$file >> "$nomFichierSortie"; }
        [[ -d $1"/"$file ]] && { genererCompileList $1"/"$file; }
    done

    return 0
}

#=========================#
# Liste les fichiers .jar #
#=========================#
function listerdependencies()
{
    for file in `ls -1 $1`    # permet de parcourir chaque éléments d'un répertoire
    do
        [[ -f $1"/"$file ]] && { [[ "${file##*.}" = "jar" ]] && dependencies+="$1/$file:"; }
        [[ -d $1"/"$file ]] && { listerdependencies $1"/"$file; }
    done

    return 0
}


#============================#
# Compile les fichiers .java #
#============================#

function compilation()
{
    echo 'Compilation...'
    javac -cp "$classpath:$dependencies" -encoding $encoding -d "$output" @$nomFichierSortie && { echo 'Fin de la compilation.'; } || { echo "Erreur lors de la compilation."; help 1; }
}

#====================#
# Lance le programme #
#====================#
function lancement()
{
    echo 'Lancement du programme...'
    echo "'java -cp \"$classpath:$dependencies\" $main'"
    java -cp "$classpath:$dependencies" $main && { echo 'Fin de l'\''execution.'; } || { echo "Erreur lors du lancement du programme."; help 1; }
}


#======#
# Main #
#======#
#-----------#
# Varaibles #
#-----------#
extensionValide="java"
nomFichierSortie="compile.list"
classpath="."
dependencies=""
encoding="UTF-8"
compilation=1
lancement=1
args=("$@")
ancienArg="a"


#-----------#
# Execution #
#-----------#
if [ $1 = "-h" ] || [ $1 = "--help" ]
then
    help 0
fi

if [ $1 = "-f" ] || [ $1 = "--file" ]
then
    [[ -z $2 ]] && { echo "Aucun fichier de configuration donnée pour l'option '-f' ou '--file'"; help 1; }
    ls $2 > /dev/null 2>&1 || { echo "Le fichier de configuration '$2' n'existe pas"; help 1; }
    [[ ! -f $2 ]] && { echo "Le fichier de configuration '$2' n'est pas un fichier"; help 1; }

    for line in `cat $2`
    do
        args+=("$line")
    done
fi

unset args[0]
unset args[1]
args+=("-a")
for arg in "${args[@]}"
do
    case "${ancienArg}" in
        "-s") verifArguments $arg "Aucune source donnée pour l'option '-s'" && source=$arg ;;
        "--source") verifArguments $arg "Aucune source donnée pour l'option '--source'" && source=$arg ;;

        "-o") verifArguments $arg "Aucune sortie donnée pour l'option '-o'" && output=$arg ;;
        "--output") verifArguments $arg "Aucune sortie donnée pour l'option '--output'" && output=$arg ;;

        "-cp") verifArguments $arg "Aucun classpath donnée pour l'option '-cp'" && classpath=$arg ;;
        "--classpath") verifArguments $arg "Aucun classpath donnée pour l'option '--classpath'" && classpath=$arg ;;

        "-d") verifArguments $arg "Aucune dépendance donnée pour l'option '-d'" && dependency=$arg ;;
        "--dependency") verifArguments $arg "Aucune dépendance donnée pour l'option '--dependency'" && dependency=$arg ;;

        "-n") verifArguments $arg "Aucun nom donnée pour l'option '-n'" && nomFichierSortie=$arg ;;
        "--name") verifArguments $arg "Aucun nom donnée pour l'option '--name'" && nomFichierSortie=$arg ;;

        "-e") verifArguments $arg "Aucun encodage donnée pour l'option '-e'" && encoding=$arg ;;
        "--encoding") verifArguments $arg "Aucun encodage donnée pour l'option '--encoding'" && encoding=$arg ;;

        "-m") verifArguments $arg "Aucun fichier main donnée pour l'option '-m'" && main=$arg ;;
        "--main") verifArguments $arg "Aucun fichier main donnée pour l'option '--main'" && main=$arg ;;

        "-dt") verifArguments $arg "Aucun dossier de données donnée pour l'option '-dt'" && data=$arg ;;
        "--data") verifArguments $arg "Aucun dossier de données donnée pour l'option '--data'" && data=$arg ;;

        "-c") compilation=0 ;;
        "--compilation") compilation=0 ;;

        "-l") lancement=0 ;;
        "--launch") lancement=0 ;;

        "-cl") compilation=0 ; lancement=0 ;;
        "--compile-launch") compilation=0 ; lancement=0 ;;
        "-lc") compilation=0 ; lancement=0 ;;
        "--launch-compile") compilation=0 ; lancement=0 ;;
    esac

    ancienArg=$arg
done

echo "source : '$source'"
echo "output : '$output'"
echo "classpath : '$classpath'"
echo "dependency : '$dependency'"
echo "nomFichierSortie : '$nomFichierSortie'"
echo "encoding : '$encoding'"
echo "compilation : '$compilation'"
echo "lancement : '$lancement'"
echo "main : '$main'"
echo "data : '$data'"

classpath=$( echo "$classpath" | sed -e 's/\"//g' )

if [ $compilation -eq 0 ]
then
    # Vérification du dossier source
    until ls $source > /dev/null 2>&1 && ! [[ -z $source ]]
    do
        echo "Le dossier source '$source' n'existe pas"
        demandeInfo "source"
    done

    # Vérification du dossier de sortie
    while [[ -z $output ]]
    do
        echo 'aucun dossier de sortie donnée'
        demandeInfo "output"
    done

    # Vérification du dossier de sortie
    ls $output > /dev/null 2>&1 || {
        read -p "Le dossier de sortie '$output' n'existe pas. Voulez-vous le créer ? (y/n) : " reponse
        [[ ${reponse} =~ ^[yY]([eE][sS])?$ ]] && {
            mkdir -p $output && echo "Le dossier '$output' a été créé" || { echo "Erreur lors de la création du dossier '$output'"; help 1; }
        } || exit 0
    }

    # Copie du dossier de données
    if [ ! -z $data ]
    then
        [[ -d $data ]] && { cp -fr "$data" "$output"; } || { echo "Le dossier de données '$data' n'existe pas"; help 1; }
    fi

    [[ ! -z $dependency ]] && listerdependencies $dependency

    echo -n > $nomFichierSortie
    genererCompileList $source; ls $nomFichierSortie > /dev/null 2>&1 || {
        echo "Erreur lors de la génération du fichier '$nomFichierSortie'."
        help 1
    }

    compilation
fi


[[ $lancement -eq 0 ]] && { lancement; }