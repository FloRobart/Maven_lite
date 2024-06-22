# Maven Lite Installation on Other Linux Distributions or MacOS

<a href="https://florobart.github.io/Maven_lite/Documentations/Installation.en.html"><button type="button">Retour</button></a>

- Voir la [documentation Française](./Installation.fr.md)
- See the [PDF documentation](./Installation.en.pdf)

## Table of Contents

- [Maven Lite Installation on Other Linux Distributions or MacOS](#maven-lite-installation-on-other-linux-distributions-or-macos)
  - [Table of Contents](#table-of-contents)
  - [Automatic Installation of Maven Lite - Linux \& MacOS (Recommended)](#automatic-installation-of-maven-lite---linux--macos-recommended)
  - [Manual Installation of Maven Lite - Linux \& MacOS](#manual-installation-of-maven-lite---linux--macos)

## Automatic Installation of Maven Lite - Linux & MacOS (Recommended)

- Download the [Compressed file of the French version](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.1.0/maven-lite_fr_2.0-1_linux-macos.zip) containing the application files.
- Unzip the compressed file.
- Run the installation script by executing the following command in a terminal

  ```sh
  sudo ./installer.sh
  ```

- You can delete the remaining unused files.

## Manual Installation of Maven Lite - Linux & MacOS

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
