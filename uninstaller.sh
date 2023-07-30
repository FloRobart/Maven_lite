#!/bin/bash

#======#
# Main #
#======#
fichier="maven_lite.sh"
dossier="/home/$USER/.maven_lite"
bashrc="/home/$USER/.bashrc"

rm -fr $dossier

sed -i '/# Maven Lite/,/# Fin Maven Lite/d' $bashrc
