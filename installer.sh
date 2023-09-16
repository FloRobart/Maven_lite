#!/bin/bash

fichierOrig="maven_lite.sh"
fichierDest="/usr/bin/mvnl"

manpageLangages=("fr" "en")


echo 'Installation de maven lite...'

chmod +x $fichierOrig || {
    echo "Erreur lors du changement des droits du fichier 'maven_lite.sh'."
    exit 1
}

sudo cp -f $fichierOrig $fichierDest || {
    echo "Erreur lors de la copie du fichier '$fichierOrig'."
    exit 1
}

echo 'Installation de maven lite terminée avec succès.'
echo 'Ajout des pages de manuel dans les différentes langues...'

for langage in "${manpageLangages[@]}"
do
    # pas sur que ça marche
    manpageOrig="manpages/${langage}/mvnl.1"
    manpageDest="/usr/local/man/${langage}/man1/"

    echo "manpageOrig=\'$manpageOrig\'"
    echo "manpageDest=\'$manpageDest\'"

    sudo cp -f $manpageOrig $manpageDest || {
        echo "Erreur lors de la copie du fichier '$manpageOrig'."
        exit 1
    }
done

echo 'Pages de manuel ajoutées avec succès.'
exit 0