# Installation de Maven Lite

## Installation des prérequis

### Installation de bash

#### Installer bash sur Debian et dérivés

- Éxécutez la commande suivante

  ```sh
  sudo apt install bash
  ```

#### Installer Bash sur d'autre distributions Linux ou MacOs

- Allez sur le [site de GNU](https://www.gnu.org/software/bash/)

### Installation de java

#### Debian et dérivés

- Éxécutez la commande suivante

  ```sh
  sudo apt install openjdk-17-jdk openjdk-17-jre
  ```

#### Autres distributions Linux

- Allez sur le [site d'Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- Téléchargez l'installeur de java 17 pour votre distribution Linux ou votre version de MacOs. Se guide d'installation ne traite pas l'installation avec un fichier compressé.
- Une fois l'installeur téléchargé, éxécutez le.
- Suivez les instructions de l'installeur.
- Une fois l'installation terminée, vous pouvez supprimer l'installeur si vous le souhaitez.

#### Windows



## Installation et désinstallation

### Debian et dérivés

#### Installation automatique - Debian

- Téléchargez le [fichier debian](https://github.com/FloRobart/mavenlite.github.io/releases) dans la langue de votre choix dans la section 'Releases' du dépôt Github
  - [Téléchargez le fichier de la version française.](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.0.0/maven-lite_french_2.0-1_all.deb)
  - [Téléchargez le fichier de la version anglaise.](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.0.0/maven-lite_english_2.0-1_all.deb)
- Éxécutez le fichier debian

  ```sh
  sudo dpkg -i maven-lite_fr_2.0-1_all.deb
  ```

- Une fois l'installation terminée, vous pouvez supprimer le fichier `maven-lite_<langue>_2.0-1_all.deb` si vous le souhaitez.

#### Installation manuelle - Debian

- Téléchargez le [Fichier compressé](https://github.com/FloRobart/mavenlite.github.io/releases) contenant les fichiers de l'applications dans la langue de votre choix dans la section 'Releases' du dépôt Github
  - [Téléchargez le fichier de la version française.](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.0.0/maven-lite_french_2.0-1_all.zip)
  - [Téléchargez le fichier de la version anglaise.](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.0.0/maven-lite_english_2.0-1_all.zip)
- Décompressez le fichier compressé
- Déplacez le dossier `maven-lite` dans le dossier `/etc`

  ```sh
  sudo mv maven-lite /etc/
  ```

- Déplacez les fichiers shell dans le dossier `/usr/local/bin`

  ```sh
  sudo mv mvnl /usr/local/bin/
  sudo mv mvnl-uninstall /usr/local/bin/
  ```

- Ajoutez les droits d'exécution au fichier `mvnl` et `mvnl-uninstall`

  ```sh
  sudo chmod +x /usr/local/bin/mvnl*
  ```

- Déplacez le dossier `man` dans le dossier `/usr/local/` pour avoir les pages de manuel

  ```sh
  sudo mv man /usr/local/
  ```

- Vous pouvez supprimez le reste des fichiers inutilisés.

#### Désinstallation automatique - Debian

- Éxécutez la commande suivante

  ```sh
  mvnl-uninstall
  ```

#### Désinstallation manuelle - Debian

- Supprimez le dossier `/etc/maven-lite`

  ```sh
  sudo rm -r /etc/maven-lite
  ```

- Supprimez les fichiers shell

  ```sh
  sudo rm /usr/local/bin/mvnl /usr/local/bin/mvnl-uninstall
  ```

- Supprimez les pages de manuel

  ```sh
  sudo rm /usr/local/man/fr/man1/mvnl.1.gz /usr/local/man/en/man1/mvnl.1.gz
  ```

### Autres distributions Linux

#### Installation automatique - Linux

- Téléchargez le [Fichier compressé](https://github.com/FloRobart/mavenlite.github.io/releases) contenant les fichiers de l'applications dans la langue de votre choix dans la section 'Releases' du dépôt Github
  - [Téléchargez le fichier de la version française.](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.0.0/maven-lite_french_2.0-1_all.zip)
  - [Téléchargez le fichier de la version anglaise.](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.0.0/maven-lite_english_2.0-1_all.zip)
- Décompressez le fichier compressé
- Éxecuter le script d'installation

  ```sh
  sudo ./installer.sh
  ```

- Vous pouvez supprimez le reste des fichiers inutilisés.

#### Installation manuelle - Linux

- Téléchargez le [Fichier compressé](https://github.com/FloRobart/mavenlite.github.io/releases) contenant les fichiers de l'applications dans la langue de votre choix dans la section 'Releases' du dépôt Github
  - [Téléchargez le fichier de la version française.](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.0.0/maven-lite_french_2.0-1_all.zip)
  - [Téléchargez le fichier de la version anglaise.](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.0.0/maven-lite_english_2.0-1_all.zip)
- Décompressez le fichier compressé
- Déplacez le dossier `maven-lite` dans le dossier `/etc`

  ```sh
  sudo mv maven-lite /etc/
  ```

- Ajoutez les droits d'exécution au fichier `mvnl` et `mvnl-uninstall`

  ```sh
  sudo chmod +x /usr/local/bin/mvnl*
  ```

- Déplacez le dossier `man` dans le dossier `/usr/local/share` pour avoir les pages de manuel

  ```sh
  sudo mv man /usr/local/share/
  ```

- Vous pouvez supprimez le reste des fichiers inutilisés.

#### Désinstallation automatique - Linux

- Éxécutez la commande suivante

  ```sh
  mvnl-uninstall
  ```

#### Désinstallation manuelle - Linux

- Supprimez le dossier `/etc/maven-lite`

  ```sh
  sudo rm -r /etc/maven-lite
  ```

- Supprimez les fichiers shell

  ```sh
  sudo rm /usr/local/bin/mvnl /usr/local/bin/mvnl-uninstall
  ```

- Supprimez les pages de manuel

  ```sh
  sudo rm /usr/local/share/man/fr/man1/mvnl.1.gz /usr/local/share/man/en/man1/mvnl.1.gz
  ```

### Windows

#### Installation automatique - Windows

- Téléchargez le [Fichier compressé](https://github.com/FloRobart/mavenlite.github.io/releases) contenant les fichiers de l'applications dans la langue de votre choix dans la section 'Releases' du dépôt Github
  - [Téléchargez le fichier de la version française.](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.0.0/maven-lite_french_2.0-1_win.zip)
  - [Téléchargez le fichier de la version anglaise.](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.0.0/maven-lite_english_2.0-1_win.zip)
- Décompressez le fichier compressé
- Éxecuter le script d'installation

  ```bat
  ./installer.bat
  ```

- Vous pouvez supprimez le reste des fichiers inutilisés.

#### Installation manuelle - Windows

- Téléchargez le [Fichier compressé](https://github.com/FloRobart/mavenlite.github.io/releases) contenant les fichiers de l'applications dans la langue de votre choix dans la section 'Releases' du dépôt Github
  - [Téléchargez le fichier de la version française.](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.0.0/maven-lite_french_2.0-1_win.zip)
  - [Téléchargez le fichier de la version anglaise.](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.0.0/maven-lite_english_2.0-1_win.zip)
- Décompressez le fichier compressé

#### Désinstallation automatique - Windows

#### Désinstallation manuelle - Windows

### MacOs

#### Installation automatique - MacOs

- Téléchargez le [Fichier compressé](https://github.com/FloRobart/mavenlite.github.io/releases) contenant les fichiers de l'applications dans la langue de votre choix dans la section 'Releases' du dépôt Github
  - [Téléchargez le fichier de la version française.](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.0.0/maven-lite_french_2.0-1_all.zip)
  - [Téléchargez le fichier de la version anglaise.](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.0.0/maven-lite_english_2.0-1_all.zip)
- Décompressez le fichier compressé
- Éxecuter le script d'installation

  ```sh
  sudo ./installer.sh
  ```

- Vous pouvez supprimez le reste des fichiers inutilisés.

#### Installation manuelle - MacOs

- Téléchargez le [Fichier compressé](https://github.com/FloRobart/mavenlite.github.io/releases) contenant les fichiers de l'applications dans la langue de votre choix dans la section 'Releases' du dépôt Github
  - [Téléchargez le fichier de la version française.](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.0.0/maven-lite_french_2.0-1_all.zip)
  - [Téléchargez le fichier de la version anglaise.](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.0.0/maven-lite_english_2.0-1_all.zip)
- Décompressez le fichier compressé
- Déplacez le dossier `maven-lite` dans le dossier `/etc`

  ```sh
  sudo mv maven-lite /etc/
  ```

- Ajoutez les droits d'exécution au fichier `mvnl` et `mvnl-uninstall`

  ```sh
  sudo chmod +x /usr/local/bin/mvnl*
  ```

- Déplacez le dossier `man` dans le dossier `/usr/local/share` pour avoir les pages de manuel

  ```sh
  sudo mv man /usr/local/share/
  ```

- Vous pouvez supprimez le reste des fichiers inutilisés.

#### Désinstallation automatique - MacOs

- Éxécutez la commande suivante

  ```sh
  mvnl-uninstall
  ```

#### Désinstallation manuelle - MacOs

- Supprimez le dossier `/etc/maven-lite`

  ```sh
  sudo rm -r /etc/maven-lite
  ```

- Supprimez les fichiers shell

  ```sh
  sudo rm /usr/local/bin/mvnl /usr/local/bin/mvnl-uninstall
  ```

- Supprimez les pages de manuel

  ```sh
  sudo rm /usr/local/share/man/fr/man1/mvnl.1.gz /usr/local/share/man/en/man1/mvnl.1.gz
  ```

## Liste des options

- Courte, Longue
  - Description
  - Nombre d'arguments
  - Valeur par défaut
- -f   , --file
  - Permet de charger les options à partir d'un fichier de configuration. En savoir plus :
  - Nombre de d'arguments : de `0` à `1`.
  - Valeur par défaut     : `LPOM.conf`.
- -cr  , --create
  - Créer l'arborescence du projet ainsi qu'un fichier de config par défaut.
  - Nombre de d'arguments : de `0` à `1`.
  - Valeur par défaut     : `NewProject`.
- -mvc , --model-view-controller
  - Permet de spécifier à l'option '--create' de créer l'arborescence d'un projet MVC.
  - Nombre de d'argument  : `0`.
- -c   , --compilation
  - Compile le projet.
  - Nombre de d'argument  : `0`.
- -l   , --launch
  - Lance le projet.
  - Nombre de d'argument  : `0`.
- -cl  , --compile-launch
  - Compile et lance le projet. (équivalent à -c -l)
  - Nombre de d'argument  : `0`.
- -lc  , --launch-compile
  - Compile et lance le projet. (équivalent à -c -l)
  - Nombre de d'argument  : `0`.
- -q   , --quiet
  - Permet de supprimer l'affichage de java dans le terminal lors de l'exécution du projet.
  - Nombre de d'argument  : `0`.
- -v   , --verbose
  - Permet d'afficher les commandes exécutées.
  - Nombre de d'argument  : `0`.
- -ex  , --exclude
  - Permet d'exclure des fichiers java et des dossiers de la compilation. Si vous voulez passé un argument qui commencer par '-' échapper le caractère '-' avec deux '\' comme ceci : '-ex  \\-fichier'.
  - Nombre de d'argument  : `unlimited`.
- -s   , --source
  - Dossier contenant les fichiers java à compiler.
  - Nombre de d'argument  : `1`.
  - Valeur par défaut     : `src/main/java`.
- -t   , --target
  - Dossier de sortie des fichiers compilés. Ce dossier sera créer si il n'existe pas et sera automatiquement ajouter au classpath lors de la compilation et du lancement.
  - Nombre de d'argument  : `1`.
  - Valeur par défaut     : `target`.
- -cp  , --classpath
  - Permet de spécifier le classpath à utiliser lors de la compilation et du lancement. Si vous voulez ajouter plusieurs éléments au classpath, il faut les séparer par des ':'.
  - Nombre de d'argument  : `1`.
- -lib , --libraries
  - Dossier contenant les fichiers jar utiliser par le programme. Tout les fichiers jar seront ajoutés au classpath lors de la compilation et du lancement.
  - Nombre de d'arguments : de `0` à `1`.
  - Valeur par défaut     : `src/main/resources/lib`.
- -args, --arguments
  - Tous les arguments à passer à la classe principale. Si vous voulez passé un argument qui commencer par '-' échapper le caractère '-' avec deux '\' comme ceci : '-args  \\-argument_pour_le_main'.
  - Nombre de d'argument  : `unlimited`.
- -m   , --main
  - Classe principale à lancer. Si vous voulez lancer une classe qui se trouve dans un package, il faut spécifier le package avec le nom de la classe comme ceci : 'package.nom.MainClass'
  - Nombre de d'argument  : `1`.
- -e   , --encoding
  - Permet de changer l'encodage des fichiers java à compiler.
  - Nombre de d'argument  : `1`.
  - Valeur par défaut     : `UTF-8`.
- -exp , --export
  - Permet de créer un fichier jar exécutable permettant de lancer le projet sans avoir installer MavenLite.
  - Nombre de d'arguments : de `0` à `1`.
  - Valeur par défaut     : `run.java`.
- -mvn , --maven
  - Convertie le projet en projet maven en créant un fichier pom.xml et en déplaçant les fichiers si nécessaire.
  - Nombre de d'argument  : `0`.
- -V   , --version
  - Affiche la version.
  - Nombre de d'argument  : `0`.
  - Valeur par défaut     : `2.0.0`.
- -h   , --help
  - Affiche l'aide et quitte.
  - Nombre de d'argument  : `0`.
- -cle , --clear
  - Permet de supprimer les fichiers dans le dossier de sortie des fichiers compilés.
  - Nombre de d'argument  : `0`.
