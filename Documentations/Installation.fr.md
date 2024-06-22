# Installation et désinstallation de Maven Lite et de ses dépendances

- See the [English documentation](./Installation.en.md)
- Voir la [documentation en PDF](./pdf/Installation.fr.pdf)

## Table des matières

- [Installation et désinstallation de Maven Lite et de ses dépendances](#installation-et-désinstallation-de-maven-lite-et-de-ses-dépendances)
  - [Table des matières](#table-des-matières)
  - [Prérequis avant l'installation de Maven Lite](#prérequis-avant-linstallation-de-maven-lite)
    - [Prérequis pour Toutes les distributions Linux et MacOs](#prérequis-pour-toutes-les-distributions-linux-et-macos)
    - [Prérequis pour Windows](#prérequis-pour-windows)
  - [Installation des prérequis](#installation-des-prérequis)
    - [Installation de bash](#installation-de-bash)
      - [Installer bash sur Debian et ses dérivés](#installer-bash-sur-debian-et-ses-dérivés)
      - [Installer Bash sur d'autre distributions Linux ou sur MacOs](#installer-bash-sur-dautre-distributions-linux-ou-sur-macos)
    - [Installation de java](#installation-de-java)
      - [Installation de java sur Debian et ses dérivés](#installation-de-java-sur-debian-et-ses-dérivés)
      - [Installation de java sur d'autre distributions Linux ou sur MacOs](#installation-de-java-sur-dautre-distributions-linux-ou-sur-macos)
      - [Installation de java sur Windows](#installation-de-java-sur-windows)
  - [Installation de Maven Lite](#installation-de-maven-lite)
  - [Désinstallation de Maven Lite](#désinstallation-de-maven-lite)

**Si vous voulez désinstaller Maven Lite, vous pouvez [passer directement à la désinstallation de Maven Lite](#désinstallation-de-maven-lite).**

## Prérequis avant l'installation de Maven Lite

### Prérequis pour Toutes les distributions Linux et MacOs

- [Bash](#installation-de-bash)
- [Java 17 ou supérieur](#installation-de-java)

### Prérequis pour Windows

- [Java 17 ou supérieur](#installation-de-java-sur-windows)

## Installation des prérequis

**Si vous avez déjà installé les prérequis, vous pouvez [passer directement à l'installation de Maven Lite](#installation-de-maven-lite).**

### Installation de bash

#### Installer bash sur Debian et ses dérivés

- Éxécutez la commande suivante

  ```sh
  sudo apt install bash
  ```

#### Installer Bash sur d'autre distributions Linux ou sur MacOs

- Téléchargez le fichier d'installation de bash en éxécutant la commande suivante

  ```sh
  /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
  ```

- Éxécutez la commande suivante pour ajouter le dossier d'installation de bash au zprofile

  ```sh
  echo 'eval "$(/opt/homebrew/bin/brew shellenv)"' >> ~/.zprofile
  ```

- Éxécutez la commande suivante pour installer bash

  ```sh
  brew install bash
  ```

- Vérifiez que bash est bien installé en éxécutant la commande suivante

  ```sh
  bash --version
  ```

### Installation de java

#### Installation de java sur Debian et ses dérivés

- Éxécutez la commande suivante

  ```sh
  sudo apt install openjdk-17-jdk openjdk-17-jre
  ```

#### Installation de java sur d'autre distributions Linux ou sur MacOs

- Allez sur le [site officiel d'Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- Téléchargez l'installateur de java 17 pour votre distribution Linux ou votre version de MacOs. Se petit guide d'installation de java ne traite pas de l'installation avec un fichier compressé.
- Une fois l'installateur téléchargé, éxécutez le.
- Suivez les instructions de l'installateur.
- Une fois l'installation terminée, vous pouvez supprimer l'installateur si vous le souhaitez.

#### Installation de java sur Windows

- Téléchargez le fichier d'installation de java 17 pour Windows sur le site officiel d'Oracle en cliquant [ici](https://download.oracle.com/java/17/archive/jdk-17.0.9_windows-x64_bin.exe).
- Une fois l'installateur '`jdk-17.0.9_windows-x64_bin.exe`' téléchargé, éxécutez le en double cliquant dessus.
- Suivez les instructions de l'installateur.
- Une fois l'installation terminée, vous pouvez supprimer l'installateur si vous le souhaitez.

## Installation de Maven Lite

**Suivez les instructions correspondant à votre système d'exploitation.**

- [IUT Le Havre](./installation/iut_le_havre/Installation.fr.md)
- [Debian et ses dérivés](./installation/debian/Installation.fr.md)
- [Linux et MacOs](./installation/linux/Installation.fr.md)
- [Windows](./installation/windows/Installation.fr.md)

## Désinstallation de Maven Lite

**Suivez les instructions correspondant à votre système d'exploitation.**

- [IUT Le Havre](./desinstallation/iut_le_havre/Desinstallation.fr.md)
- [Debian et ses dérivés](./desinstallation/debian/Desinstallation.fr.md)
- [Linux et MacOs](./desinstallation/linux/Desinstallation.fr.md)
- [Windows](./desinstallation/windows/Desinstallation.fr.md)

****

<a href="https://florobart.github.io/Maven_lite/Documentations/README.fr.html"><button type="button">Retour</button></a>
