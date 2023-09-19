#!/bin/bash

fichierOrig="maven_lite.sh"
fichierDest="/usr/bin/mvnl"

manpageLangages=("fr" "en")


echo 'Installation de maven lite...'

chmod +x $fichierOrig || {
    echo "Erreur lors du changement des droits du fichier 'maven_lite.sh'."
    exit 1
}

sudo cp -f $fichierOrig $fichierDest && echo "Le fichier $fichierDest à été copié." || {
    echo "Erreur lors de la copie du fichier '$fichierOrig'."
    exit 1
}

echo 'Installation de maven lite terminée avec succès.'
echo 'Ajout des pages de manuel dans les différentes langues...'

for langage in "${manpageLangages[@]}"
do
    manpageOrig="manpages/${langage}/mvnl.1.gz"
    manpageDest="/usr/local/man/${langage}/man1/"

    if [ ! -d ${manpageDest} ]
    then
        sudo mkdir -p ${manpageDest} && echo "le dossier ${manpageDest} a été créé." || {
            echo "Erreur lors de création du dossier ${manpageDest}."
            exit 1
        }
    fi

    sudo cp -f $manpageOrig $manpageDest && echo "Le fichier ${manpageDest}/mvnl.1.gz à été copié." || {
        echo "Erreur lors de la copie du fichier '${manpageOrig}/mvnl.1.gz'."
        exit 1
    }
done

echo 'Pages de manuel ajoutées avec succès.'
exit 0