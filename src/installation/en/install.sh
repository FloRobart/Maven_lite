#!/bin/sh

# Déplacement des fichiers shell dans le dossier /usr/local/bin
sudo mv mvnl /usr/local/bin || { echo 'Error occurred while moving the \'mvnl\' file.'; exit 1; }
sudo mv mvnl-uninstall /usr/local/bin/ || { echo 'Error occurred while moving the \'mvnl-uninstall\' file.'; exit 1; }

# Déplacement du dossier maven-lite dans /etc
sudo mv maven-lite /etc || { echo 'Error occurred while moving the \'maven-lite\' folder.'; exit 1; }

# Déplacement des pages de manuel dans /usr/local
sudo mv man /usr/local/ || { echo 'Error occurred while moving the manual pages.'; exit 1; }

echo 'Installation completed successfully.'
exit 0