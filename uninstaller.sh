#!/bin/bash

#======#
# Main #
#======#
fichier="/bin/mvnl"

sudo rm $fichier || {
    echo "Erreur lors de la suppression du fichier '$fichier'."
    exit 1
}

echo 'Désinstallation terminée avec succès.'
