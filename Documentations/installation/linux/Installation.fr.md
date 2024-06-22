# Installation de Maven Lite sur d'autres distributions Linux ou sur MacOs

<a href="https://florobart.github.io/Maven_lite/Documentations/Installation.fr.html"><button type="button">Retour</button></a>

- See the [English documentation](./Installation.en.md)
- Voir la [documentation en PDF](./Installation.fr.pdf)

## Table des matières

- [Installation de Maven Lite sur d'autres distributions Linux ou sur MacOs](#installation-de-maven-lite-sur-dautres-distributions-linux-ou-sur-macos)
  - [Table des matières](#table-des-matières)
  - [Installation automatique de Maven Lite - Linux \& MacOs (recommandé)](#installation-automatique-de-maven-lite---linux--macos-recommandé)
  - [Installation manuelle de Maven Lite - Linux \& MacOs](#installation-manuelle-de-maven-lite---linux--macos)

## Installation automatique de Maven Lite - Linux & MacOs (recommandé)

- Téléchargez le [Fichier compressé de la version française](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.1.0/maven-lite_fr_2.0-1_linux-macos.zip) contenant les fichiers de l'applications.
- Décompressez le fichier compressé
- Éxecuter le script d'installation en éxécutant la commande suivante dans un terminal

  ```sh
  sudo ./installer.sh
  ```

- Vous pouvez supprimez le reste des fichiers inutilisés.

## Installation manuelle de Maven Lite - Linux & MacOs

- Téléchargez le [Fichier compressé de la version française](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.1.0/maven-lite_fr_2.0-1_linux-macos.zip) contenant les fichiers de l'applications.
- Décompressez le fichier compressé
- Créez le dossier `/usr/local/etc/maven-lite`

  ```sh
  sudo mkdir -p /usr/local/etc/maven-lite
  ```

- Déplacez les fichiers .class et .jar dans le dossier `/usr/local/etc/maven-lite`

  ```sh
  sudo mv hamcrest-core-1.3.jar /usr/local/etc/maven-lite/
  sudo mv junit-4.13.2.jar /usr/local/etc/maven-lite/
  sudo mv MavenLite.class /usr/local/etc/maven-lite/
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

- Déplacez le fichier `mvnl.1.gz` dans le dossier `/usr/local/man/fr/man1` pour avoir les pages de manuel

  ```sh
  sudo mv mvnl.1.gz /usr/local/man/fr/man1/
  ```

- Vous pouvez supprimez le reste des fichiers inutilisés.

****

<a href="https://florobart.github.io/Maven_lite/Documentations/Installation.fr.html"><button type="button">Retour</button></a>
