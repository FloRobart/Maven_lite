#!/bin/bash

#======#
# Main #
#======#
fichier="maven_lite.sh"
dossier="/home/$USER/.maven_lite"
bashrc="/home/$USER/.bashrc"

rm -fr $dossier && {
    sed -i '/# Maven Lite/,/# Fin Maven Lite/d' $bashrc && {
        source $bashrc; echo "Maven Lite à bien été supprimé."
    } || {
        echo "Erreur lors la suppression de l'alias de Maven Lite dans le fichier '$bashrc'."
    }
} || {
    echo "Erreur lors de la suppression du dossier '$dossier'."
}
