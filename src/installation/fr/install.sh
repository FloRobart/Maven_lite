#!/bin/sh

# Déplacement des fichiers shell dans le dossier /usr/local/bin
mv mvnl /usr/local/bin || { echo "Erreur lors du déplacement du fichier 'mvnl'."; exit 1; }
mv mvnl-uninstall /usr/local/bin/ || { echo "Erreur lors du déplacement du fichier 'mvnl-uninstall'."; exit 1; }

# Déplacement du dossier maven-lite dans /etc
mv maven-lite /etc || { echo "Erreur lors du déplacement du dossier 'maven-lite'."; exit 1; }

# Déplacement des pages de manuel dans /usr/local
mv man /usr/local/man/fr/man1 || { echo "Erreur lors du déplacement des pages de manuel."; exit 1; }

echo 'Installation terminée avec succès.'
exit 0