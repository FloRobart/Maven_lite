# Maven Lite Uninstallation on Other Linux Distributions or MacOS

<a href="https://florobart.github.io/Maven_lite/Documentations/Installation.en.html"><button type="button">Retour</button></a>

- Voir la [documentation Française](./Desinstallation.fr.md)
- See the [PDF documentation](./Desinstallation.en.pdf)

## Table of Contents

- [Maven Lite Uninstallation on Other Linux Distributions or MacOS](#maven-lite-uninstallation-on-other-linux-distributions-or-macos)
  - [Table of Contents](#table-of-contents)
  - [Automatic Uninstallation of Maven Lite - Linux \& MacOS (Recommended)](#automatic-uninstallation-of-maven-lite---linux--macos-recommended)
  - [Manual Uninstallation of Maven Lite - Linux \& MacOS](#manual-uninstallation-of-maven-lite---linux--macos)

## Automatic Uninstallation of Maven Lite - Linux & MacOS (Recommended)

- Execute the following command

  ```sh
  mvnl-uninstall
  ```

## Manual Uninstallation of Maven Lite - Linux & MacOS

- Delete the `/usr/local/etc/maven-lite` folder

  ```sh
  sudo rm -r /usr/local/etc/maven-lite
  ```

- Delete the shell files

  ```sh
  sudo rm /usr/local/bin/mvnl /usr/local/bin/mvnl-uninstall
  ```

- Delete the manual pages

  ```sh
  sudo rm /usr/local/share/man/fr/man1/mvnl.1.gz /usr/local/share/man/en/man1/mvnl.1.gz
  ```

****

<a href="https://florobart.github.io/Maven_lite/Documentations/Installation.en.html"><button type="button">Retour</button></a>
