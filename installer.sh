#!/bin/bash

fichierOrig="maven_lite.sh"
fichierDest="/bin/mvnl"

chmod +x $fichierOrig || {
    echo "Erreur lors du changement des droits du fichier 'maven_lite.sh'."
    exit 1
}

sudo cp -f $fichierOrig $fichierDest || {
    echo "Erreur lors de la copie du fichier '$fichierOrig'."
    exit 1
}

echo 'Installation terminée avec succès.'