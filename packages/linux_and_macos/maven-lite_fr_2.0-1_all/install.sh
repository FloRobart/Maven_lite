#!/bin/bash

#======#
# Main #
#======#
# Constantes
SUCCES="[SUCCES]"
ERROR="[ERREUR]"

# Variables
bin="/usr/local/bin"
etc="/etc"
man="/usr/local/man/fr/man1"

binFileUn="mvnl"
binFileDeux="mvnl-uninstall"
etcFile="maven-lite"
manFile="mvnl.1.gz"

# Déplacement des fichiers shell dans le dossier /usr/local/bin
mv ${binFileUn} ${bin} || { echo "${ERROR} Erreur lors du déplacement du fichier 'mvnl'."; exit 1; }
mv ${binFileDeux} ${bin} || { echo "${ERROR} Erreur lors du déplacement du fichier 'mvnl-uninstall'."; exit 1; }

# Déplacement du dossier maven-lite dans /etc
mv ${etcFile} ${etc} || { echo "${ERROR} Erreur lors du déplacement du dossier 'maven-lite'."; exit 1; }

# Déplacement des pages de manuel dans /usr/local
mv ${manFile} ${man} || { echo "${ERROR} Erreur lors du déplacement des pages de manuel."; exit 1; }

echo "${SUCCES} Installation terminée avec succès."
exit 0