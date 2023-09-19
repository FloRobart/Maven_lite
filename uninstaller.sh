#!/bin/bash

#======#
# Main #
#======#
fichier="/usr/bin/mvnl"
manpageLangages=("fr" "en")

echo 'Désinstallation de maven lite...'

sudo rm -f $fichier && echo "le fichier ${fichier} a été supprimé" || {
    echo "Erreur lors de la suppression du fichier '$fichier'."
    exit 1
}

echo 'Désinstallation de maven lite terminée avec succès.'
echo 'Suppression des pages de manuel dans les différentes langues...'

for langage in "${manpageLangages[@]}"
do
    manpageFile="/usr/local/man/${langage}/man1/mvnl.1.gz"

    if [ -f ${manpageFile} ]
    then
        sudo rm -f ${manpageFile} && echo "le fichier ${manpageFile} a été supprimé" || {
            echo "Erreur lors de la suppression du fichier '${manpageFile}'."
            exit 1
        }
    fi
done
echo 'Pages de manuel supprimé avec succès.'
exit 0