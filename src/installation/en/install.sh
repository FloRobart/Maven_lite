#!/bin/bash

# This file is part of the Maven Lite project
# Copyright (C) 2024 Floris Robart <florisrobart.pro@gmail.com>

#======#
# Main #
#======#
# Constantes
SUCCES="[SUCCESS]"
ERROR="[ERROR]"

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

# Creation of the /etc/maven-lite directory
if [ ! -d ${etc} ]; then
    sudo mkdir ${etc} && { echo "${SUCCESS} Creation of the '${etc}' directory completed successfully."; } || { echo "${ERROR} Error creating the '${etc}' directory."; exit 1; }
fi

# Moving shell files to the /usr/local/bin directory
sudo cp -f ${binFileUn} ${bin} && { echo "${SUCCESS} Copying the '${binFileUn}' file completed successfully."; } || { echo "${ERROR} Error moving the '${binFileUn}' file."; exit 1; }
sudo cp -f ${binFileDeux} ${bin} && { echo "${SUCCESS} Copying the '${binFileDeux}' file completed successfully."; } || { echo "${ERROR} Error moving the '${binFileDeux}' file."; exit 1; }

# Moving the class file to /etc/maven-lite
sudo cp -f ${classFile} ${etc} && { echo "${SUCCESS} Copying the '${classFile}' file completed successfully."; } || { echo "${ERROR} Error moving the '${classFile}' file."; exit 1; }

# Moving junit jar files to /etc/maven-lite
sudo cp -f ${junitJarFileUn} ${etc} && { echo "${SUCCESS} Copying the '${junitJarFileUn}' file completed successfully."; } || { echo "${ERROR} Error moving the '${junitJarFileUn}' file."; exit 1; }
sudo cp -f ${junitJarFileDeux} ${etc} && { echo "${SUCCESS} Copying the '${junitJarFileDeux}' file completed successfully."; } || { echo "${ERROR} Error moving the '${junitJarFileDeux}' file."; exit 1; }

# Moving manual pages to /usr/local
sudo cp -f ${manFile} ${man} && { echo "${SUCCESS} Copying manual pages completed successfully."; } || { echo "${ERROR} Error moving manual pages."; exit 1; }

echo -e "\n${SUCCESS} Installation completed successfully."
exit 0