#!/bin/bash

#======#
# Aide #
#======#
function help()
{
    echo 'help'
}

function demandeInfo()
{
    if [ "$1" = "source" ]
    then
        read -p "Veuillez entrez le dossier racine à partir du quel la compile.list vas être générer : " source
    else
        if [ "$1" = "destination" ]
        then
            read -p "Veuillez entrez le dossier de destination dans lequel vont être les fichier .class : " destination
        fi
    fi
}

function verifFolder()
{
    ls $1 > /dev/null 2>&1 && echo 0 || echo 1
}


#======#
# Main #
#======#
cpt=1
ancienArg="a"
for arg in "$@"
do
    if [ $ancienArg = "-s" ] || [ $ancienArg = "--source" ]
    then
        source=$arg
    else
        if [ $ancienArg = "-d" ] || [ $ancienArg = "--destiation" ]
        then
            destination=$arg
        else
            if [ $ancienArg = "-cp" ] || [ $ancienArg = "--classpath" ]
            then
                
            else
                if [ $arg = "-h" ] || [ $arg = "--help" ]
                then
                    help
                    exit 0
                fi
            fi
        fi
    fi

    ancienArg=$arg
done

[[ -z $source ]] && demandeInfo "source"
[[ -z $destination ]] && demandeInfo "destination"

while [ $(verifFolder $source) -eq 1 ]
do
    echo "Le dossier '$source' n'existe pas"
    demandeInfo "source"
done
while [ $(verifFolder $destination) -eq 1 ]
do
    echo "Le dossier '$destination' n'existe pas"
    demandeInfo "source"
done

echo 'Compilation...'