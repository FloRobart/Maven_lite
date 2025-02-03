#!/bin/bash

#======#
# Main #
#======#
# Constantes
SUCCES="[SUCCES]"
ERROR="[ERREUR]"

# Variables
MavenLiteFolder=".maven-lite"
bin="/home/etudiant/$USER/${MavenLiteFolder}"
etc="${bin}/etc"

binFileUn="mvnl"
binFileDeux="mvnl-uninstall"
classFile="MavenLite.class"
junitJarFileUn="junit-4.13.2.jar"
junitJarFileDeux="hamcrest-core-1.3.jar"
lang="lang"

# Création du dossier bin
if [ ! -d ${bin} ]; then
    mkdir ${bin} && { echo "${SUCCES} Création du dossier '${bin}' terminé avec succès."; } || { echo "${ERROR} Erreur lors de la création du dossier '${bin}'."; exit 1; }
fi

# Création du dossier etc
if [ ! -d ${etc} ]; then
    mkdir ${etc} && { echo "${SUCCES} Création du dossier '${etc}' terminé avec succès."; } || { echo "${ERROR} Erreur lors de la création du dossier '${etc}'."; exit 1; }
fi

# Déplacement des fichiers shell dans le dossier bin
cp -f ${binFileUn} ${bin} && { echo "${SUCCES} Copie du fichier '${binFileUn}' terminé avec succès."; } || { echo "${ERROR} Erreur lors du déplacement du fichier '${binFileUn}'."; exit 1; }
cp -f ${binFileDeux} ${bin} && { echo "${SUCCES} Copie du fichier '${binFileDeux}' terminé avec succès."; } || { echo "${ERROR} Erreur lors du déplacement du fichier '${binFileDeux}'."; exit 1; }

# Déplacement du fichier class dans etc
cp -f ${classFile} ${etc} && { echo "${SUCCES} Copie du fichier '${classFile}' terminé avec succès."; } || { echo "${ERROR} Erreur lors du déplacement du fichier '${classFile}'."; exit 1; }

# Déplacement des fichiers jar de junit dans etc
cp -f ${junitJarFileUn} ${etc} && { echo "${SUCCES} Copie du fichier '${junitJarFileUn}' terminé avec succès."; } || { echo "${ERROR} Erreur lors du déplacement du fichier '${junitJarFileUn}'."; exit 1; }
cp -f ${junitJarFileDeux} ${etc} && { echo "${SUCCES} Copie du fichier '${junitJarFileDeux}' terminé avec succès."; } || { echo "${ERROR} Erreur lors du déplacement du fichier '${junitJarFileDeux}'."; exit 1; }

# Déplacement des fichiers de lang dans etc
cp -fr ${$lang} ${etc} && { echo "${SUCCES} Copie du dossier '${lang}' terminé avec succès."; } || { echo "${ERROR} Erreur lors du déplacement du dossier '${lang}'."; exit 1; }

# Création d'une alias dans le fichier ~/.di_shrc_priv
echo -e "\n\n# Alias pour Maven Lite\nalias mvnl='${bin}/mvnl'\nalias mvnl-uninstall='${bin}/mvnl-uninstall'\n" >> /home/etudiant/$USER/.di_shrc_priv && { echo "${SUCCES} Création de l'alias 'mvnl' dans le fichier '~/.di_shrc_priv' terminé avec succès."; } || { echo "${ERROR} Erreur lors de la création de l'alias 'mvnl' dans le fichier '~/.di_shrc_priv'."; exit 1; }
source /home/etudiant/$USER/.di_shrc_priv

echo -e "\n${SUCCES} Installation terminée avec succès."
exit 0