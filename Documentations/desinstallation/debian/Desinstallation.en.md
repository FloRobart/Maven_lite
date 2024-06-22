# Maven Lite Uninstallation on Debian and its Derivatives

<a href="https://florobart.github.io/Maven_lite/Documentations/Installation.en.html"><button type="button">Retour</button></a>

- Voir la [documentation Française](./Desinstallation.fr.md)
- See the [PDF documentation](./Desinstallation.en.pdf)

## Table of Contents

- [Maven Lite Uninstallation on Debian and its Derivatives](#maven-lite-uninstallation-on-debian-and-its-derivatives)
  - [Table of Contents](#table-of-contents)
  - [Automatic Uninstallation of Maven Lite - Debian (Recommended)](#automatic-uninstallation-of-maven-lite---debian-recommended)
  - [Manual Uninstallation of Maven Lite - Debian](#manual-uninstallation-of-maven-lite---debian)

## Automatic Uninstallation of Maven Lite - Debian (Recommended)

- First method
  - Execute the following command

    ```sh
    mvnl-uninstall
    ```

- Second method
  - Execute the following command

    ```sh
    sudo dpkg -r mvnl
    ```

## Manual Uninstallation of Maven Lite - Debian

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
  sudo rm /usr/local/man/fr/man1/mvnl.1.gz /usr/local/man/en/man1/mvnl.1.gz
  ```

****

<a href="https://florobart.github.io/Maven_lite/Documentations/Installation.en.html"><button type="button">Retour</button></a>
