# Désinstallation de Maven Lite sur d'autres distributions Linux ou sur MacOs

<a href="https://florobart.github.io/Maven_lite/Documentations/Installation.fr.html"><button type="button">Retour</button></a>

- See the [English documentation](./Desinstallation.en.md)
- Voir la [documentation en PDF](./Desinstallation.fr.pdf)

## Table des matières

- [Désinstallation de Maven Lite sur d'autres distributions Linux ou sur MacOs](#désinstallation-de-maven-lite-sur-dautres-distributions-linux-ou-sur-macos)
  - [Table des matières](#table-des-matières)
  - [Désinstallation automatique de Maven Lite - Linux \& MacOs (recommandé)](#désinstallation-automatique-de-maven-lite---linux--macos-recommandé)
  - [Désinstallation manuelle de Maven Lite - Linux \& MacOs](#désinstallation-manuelle-de-maven-lite---linux--macos)

## Désinstallation automatique de Maven Lite - Linux & MacOs (recommandé)

- Éxécutez la commande suivante

  ```sh
  mvnl-uninstall
  ```

## Désinstallation manuelle de Maven Lite - Linux & MacOs

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
  sudo rm /usr/local/share/man/fr/man1/mvnl.1.gz /usr/local/share/man/en/man1/mvnl.1.gz
  ```

****

<a href="https://florobart.github.io/Maven_lite/Documentations/Installation.fr.html"><button type="button">Retour</button></a>
