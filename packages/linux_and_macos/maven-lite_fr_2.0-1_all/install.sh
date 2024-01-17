#!/bin/bash

#======#
# Main #
#======#
# Constantes
SUCCES="[SUCCES]"
ERROR="[ERREUR]"

# Variables
MavenLiteFolder="maven-lite"
bin="/usr/local/bin"
etc="/usr/local/etc/${MavenLiteFolder}"
man="/usr/local/man/fr/man1"

binFileUn="mvnl"
binFileDeux="mvnl-uninstall"
classFile="MavenLite.class"
junitJarFileUn="junit-4.13.2.jar"
junitJarFileDeux="hamcrest-core-1.3.jar"
manFile="mvnl.1.gz"

# Création du dossier /etc/maven-lite
if [ ! -d ${etc} ]; then
    sudo mkdir ${etc} && { echo "${SUCCES} Création du dossier '${etc}' terminé avec succès."; } || { echo "${ERROR} Erreur lors de la création du dossier '${etc}'."; exit 1; }
fi

# Déplacement des fichiers shell dans le dossier /usr/local/bin
sudo cp -f ${binFileUn} ${bin} && { echo "${SUCCES} Copie du fichier '${binFileUn}' terminé avec succès."; } || { echo "${ERROR} Erreur lors du déplacement du fichier '${binFileUn}'."; exit 1; }
sudo cp -f ${binFileDeux} ${bin} && { echo "${SUCCES} Copie du fichier '${binFileDeux}' terminé avec succès."; } || { echo "${ERROR} Erreur lors du déplacement du fichier '${binFileDeux}'."; exit 1; }

# Déplacement du fichier class dans /etc/maven-lite
sudo cp -f ${classFile} ${etc} && { echo "${SUCCES} Copie du fichier '${classFile}' terminé avec succès."; } || { echo "${ERROR} Erreur lors du déplacement du fichier '${classFile}'."; exit 1; }

# Déplacement des fichiers jar de junit dans /etc/maven-lite
sudo cp -f ${junitJarFileUn} ${etc} && { echo "${SUCCES} Copie du fichier '${junitJarFileUn}' terminé avec succès."; } || { echo "${ERROR} Erreur lors du déplacement du fichier '${junitJarFileUn}'."; exit 1; }
sudo cp -f ${junitJarFileDeux} ${etc} && { echo "${SUCCES} Copie du fichier '${junitJarFileDeux}' terminé avec succès."; } || { echo "${ERROR} Erreur lors du déplacement du fichier '${junitJarFileDeux}'."; exit 1; }

# Déplacement des pages de manuel dans /usr/local
sudo cp -f ${manFile} ${man} && { echo "${SUCCES} Copie des pages de manuel terminé avec succès."; } || { echo "${ERROR} Erreur lors du déplacement des pages de manuel."; exit 1; }

echo -e "\n${SUCCES} Installation terminée avec succès."
exit 0