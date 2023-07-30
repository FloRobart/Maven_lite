#!/bin/bash

function copie() {
    cp -f $fichier $dossier || {
        echo "Erreur lors de la copie du fichier 'maven_lite.sh'."
        exit 1
    }
}

function addCommand() {
    {
        echo >> $bashrc
        echo "# Maven Lite" >> $bashrc
        echo "alias mvnl='bash $dossier/$fichier'" >> $bashrc
        echo "# Fin Maven Lite" >> $bashrc
        echo >> $bashrc
    } && {
        echo "Installation terminée."
        echo "Pour utiliser maven lite, tapez 'mvnl --help' dans un terminal."
        return 0
    } || {
        echo "Erreur lors de l'ajout de la commande 'mvnl'."
        exit 1
    }
}


#======#
# Main #
#======#
fichier="maven_lite.sh"
dossier="/home/$USER/.maven_lite"
bashrc="/home/$USER/.bashrc"

chmod +x $fichier || {
    echo "Erreur lors du changement des droits du fichier 'maven_lite.sh'."
    exit 1
}

ls $dossier >/dev/null 2>&1 && {
    copie
} || {
    mkdir $dossier && {
        copie
    } || {
        echo "Erreur lors de la création du dossier '$dossier'."
        exit 1
    }
}

addCommand
source $bashrc