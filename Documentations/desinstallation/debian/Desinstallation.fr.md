# Désinstallation de Maven Lite sur Debian et ses dérivés

<a href="https://florobart.github.io/Maven_lite/Documentations/Installation.fr.html"><button type="button">Retour</button></a>

- See the [English documentation](./Desinstallation.en.md)
- Voir la [documentation en PDF](./Desinstallation.fr.pdf)

## Table des matières

- [Désinstallation de Maven Lite sur Debian et ses dérivés](#désinstallation-de-maven-lite-sur-debian-et-ses-dérivés)
  - [Table des matières](#table-des-matières)
  - [Désinstallation automatique de Maven Lite - Debian (recommandé)](#désinstallation-automatique-de-maven-lite---debian-recommandé)
  - [Désinstallation manuelle de Maven Lite - Debian](#désinstallation-manuelle-de-maven-lite---debian)

## Désinstallation automatique de Maven Lite - Debian (recommandé)

- Première méthode
  - Éxécutez la commande suivante

    ```sh
    mvnl-uninstall
    ```

- Deuxième méthode
  - Éxécutez la commande suivante

    ```sh
    sudo dpkg -r mvnl
    ```

## Désinstallation manuelle de Maven Lite - Debian

- Supprimez le dossier `/usr/local/etc/maven-lite`

  ```sh
  sudo rm -r /usr/local/etc/maven-lite
  ```

- Supprimez les fichiers shell

  ```sh
  sudo rm /usr/local/bin/mvnl /usr/local/bin/mvnl-uninstall
  ```

- Supprimez les pages de manuel

  ```sh
  sudo rm /usr/local/man/fr/man1/mvnl.1.gz /usr/local/man/en/man1/mvnl.1.gz
  ```

****

<a href="https://florobart.github.io/Maven_lite/Documentations/Installation.fr.html"><button type="button">Retour</button></a>
