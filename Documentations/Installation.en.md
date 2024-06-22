# Installation and Uninstallation of Maven Lite and its Dependencies

- Voir la [documentation Française](./Installation.fr.md)
- See the [PDF documentation](./pdf/Installation.en.pdf)

## Table of Contents

- [Installation and Uninstallation of Maven Lite and its Dependencies](#installation-and-uninstallation-of-maven-lite-and-its-dependencies)
  - [Table of Contents](#table-of-contents)
  - [Prerequisites before Installing Maven Lite](#prerequisites-before-installing-maven-lite)
    - [Prerequisites for All Linux Distributions and MacOS](#prerequisites-for-all-linux-distributions-and-macos)
    - [Prerequisites for Windows](#prerequisites-for-windows)
  - [Installing Prerequisites](#installing-prerequisites)
    - [Installing Bash](#installing-bash)
      - [Installing Bash on Debian and its Derivatives](#installing-bash-on-debian-and-its-derivatives)
      - [Installing Bash on other Linux Distributions or MacOS](#installing-bash-on-other-linux-distributions-or-macos)
    - [Installing Java](#installing-java)
      - [Installing Java on Debian and its Derivatives](#installing-java-on-debian-and-its-derivatives)
      - [Installing Java on other Linux Distributions or MacOS](#installing-java-on-other-linux-distributions-or-macos)
      - [Installing Java on Windows](#installing-java-on-windows)
  - [Maven Lite Installation](#maven-lite-installation)
  - [Maven Lite Uninstallation](#maven-lite-uninstallation)

**If you want to uninstall Maven Lite, you can [skip directly to the uninstallation of Maven Lite](#maven-lite-uninstallation).**

## Prerequisites before Installing Maven Lite

### Prerequisites for All Linux Distributions and MacOS

- [Bash](#installing-bash)
- [Java 17 or higher](#installing-java)

### Prerequisites for Windows

- [Java 17 or higher](#installing-java-on-windows)

## Installing Prerequisites

**If you have already installed the prerequisites, you can [skip directly to the installation of Maven Lite](#maven-lite-installation).**

### Installing Bash

#### Installing Bash on Debian and its Derivatives

- Run the following command

  ```sh
  sudo apt install bash
  ```

#### Installing Bash on other Linux Distributions or MacOS

- Download the bash installation file by running the following command

  ```sh
  /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
  ```

- Run the following command to add the bash installation directory to zprofile

  ```sh
  echo 'eval "$(/opt/homebrew/bin/brew shellenv)"' >> ~/.zprofile
  ```

- Run the following command to install bash

  ```sh
  brew install bash
  ```

- Verify that bash is installed by running the following command

  ```sh
  bash --version
  ```

### Installing Java

#### Installing Java on Debian and its Derivatives

- Run the following command

  ```sh
  sudo apt install openjdk-17-jdk openjdk-17-jre
  ```

#### Installing Java on other Linux Distributions or MacOS

- Go to the [official Oracle website](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- Download the java 17 installer for your Linux distribution or MacOS version. This guide doesn't cover installation with a compressed file.
- Once the installer is downloaded, run it.
- Follow the instructions of the installer.
- Once the installation is complete, you can delete the installer if you wish.

#### Installing Java on Windows

- Download the java 17 installer file for Windows from the official Oracle website by clicking [here](https://download.oracle.com/java/17/archive/jdk-17.0.9_windows-x64_bin.exe).
- Once the '`jdk-17.0.9_windows-x64_bin.exe`' installer is downloaded, double-click to run it.
- Follow the instructions of the installer.
- Once the installation is complete, you can delete the installer if you wish.

## Maven Lite Installation

**Follow the instructions corresponding to your operating system.**

- [IUT Le Havre](./installation/iut_le_havre/Installation.en.md)
- [Debian and its derivatives](./installation/debian/Installation.en.md)
- [Linux and MacOS](./installation/linux/Installation.en.md)
- [Windows](./installation/windows/Installation.en.md)

## Maven Lite Uninstallation

**Follow the instructions corresponding to your operating system.**

- [IUT Le Havre](./desinstallation/iut_le_havre/Desinstallation.en.md)
- [Debian and its derivatives](./desinstallation/debian/Desinstallation.en.md)
- [Linux and MacOS](./desinstallation/linux/Desinstallation.en.md)
- [Windows](./desinstallation/windows/Desinstallation.en.md)

****

<a href="https://florobart.github.io/Maven_lite/Documentations/README.en.html"><button type="button">Retour</button></a>
