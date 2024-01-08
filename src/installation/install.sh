#!/bin/sh

# Déplacement des fichiers shell dans le dossier /usr/local/bin
sudo mv maven-lite /usr/local/bin/
sudo mv maven-lite.sh /usr/local/bin/

# Déplacement du dossier maven-lite dans /etc
sudo mv maven-lite /etc/

# Déplacement des pages de manuel dans /usr/local
sudo mv man /usr/local/