#!/bin/bash

#======#
# Main #
#======#
fichier="/usr/bin/mvnl"

sudo rm $fichier || {
    echo "Erreur lors de la suppression du fichier '$fichier'."
    exit 1
}

echo 'Désinstallation terminée avec succès.'
