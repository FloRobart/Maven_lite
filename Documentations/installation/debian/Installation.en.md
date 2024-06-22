# Maven Lite Installation on Debian and its Derivatives

<a href="https://florobart.github.io/Maven_lite/Documentations/Installation.en.html"><button type="button">Retour</button></a>

- Voir la [documentation Française](./Installation.fr.md)
- See the [PDF documentation](./Installation.en.pdf)

## Table of Contents

- [Maven Lite Installation on Debian and its Derivatives](#maven-lite-installation-on-debian-and-its-derivatives)
  - [Table of Contents](#table-of-contents)
  - [Automatic Installation of Maven Lite - Debian (Recommended)](#automatic-installation-of-maven-lite---debian-recommended)
  - [Manual Installation of Maven Lite - Debian](#manual-installation-of-maven-lite---debian)

## Automatic Installation of Maven Lite - Debian (Recommended)

- Download the [Debian file for the French version](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.1.0/maven-lite_fr_2.0-1_all.deb)
- Execute the Debian file

  ```sh
  sudo dpkg -i maven-lite_fr_2.0-1_all.deb
  ```

- Once the installation is complete, you can delete the file `maven-lite_<language>_2.0-1_all.deb` if you wish.

## Manual Installation of Maven Lite - Debian

- Download the [Compressed file of the French version](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.1.0/maven-lite_fr_2.0-1_linux-macos.zip) containing the application files.
- Unzip the compressed file.
- Create the folder `/usr/local/etc/maven-lite`

  ```sh
  sudo mkdir -p /usr/local/etc/maven-lite
  ```

- Move the .class and .jar files to the `/usr/local/etc/maven-lite` folder

  ```sh
  sudo mv hamcrest-core-1.3.jar /usr/local/etc/maven-lite/
  sudo mv junit-4.13.2.jar /usr/local/etc/maven-lite/
  sudo mv MavenLite.class /usr/local/etc/maven-lite/
  ```

- Move the shell files to the `/usr/local/bin` folder

  ```sh
  sudo mv mvnl /usr/local/bin/
  sudo mv mvnl-uninstall /usr/local/bin/
  ```

- Add execution rights to the `mvnl` and `mvnl-uninstall` files

  ```sh
  sudo chmod +x /usr/local/bin/mvnl*
  ```

- Move the `mvnl.1.gz` file to the `/usr/local/man/fr/man1` folder to have manual pages

  ```sh
  sudo mv mvnl.1.gz /usr/local/man/fr/man1/
  ```

- You can delete the remaining unused files.

****

<a href="https://florobart.github.io/Maven_lite/Documentations/Installation.en.html"><button type="button">Retour</button></a>
