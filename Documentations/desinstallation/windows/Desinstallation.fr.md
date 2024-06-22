# Désinstallation de Maven Lite sur Windows

<a href="https://florobart.github.io/Maven_lite/Documentations/Installation.fr.html"><button type="button">Retour</button></a>

- See the [English documentation](./Desinstallation.en.md)
- Voir la [documentation en PDF](./Desinstallation.fr.pdf)

## Table des matières

- [Désinstallation de Maven Lite sur Windows](#désinstallation-de-maven-lite-sur-windows)
  - [Table des matières](#table-des-matières)
  - [Désinstallation automatique de Maven Lite - Windows (recommandé)](#désinstallation-automatique-de-maven-lite---windows-recommandé)
  - [Désinstallation manuelle de Maven Lite - Windows](#désinstallation-manuelle-de-maven-lite---windows)

## Désinstallation automatique de Maven Lite - Windows (recommandé)

- Éxécutez la commande suivante dans un terminal powershell en tant qu'administrateur

  ```powershell
  mvnl-uninstall
  ```

## Désinstallation manuelle de Maven Lite - Windows

- Supprimez le dossier `C:\Program Files\maven-lite` en éxécutant la commande suivante dans un terminal powershell

  ```powershell
  rmdir 'C:\Program Files\maven-lite'
  ```

- Confirmer la suppression en tapant `O`
- Supprimez le dossier `C:\Program Files\maven-lite\` de la variable d'environnement système `PATH`
  - Si vous ne savez pas comment faire suivez les étapes suivantes ou suivez [ce tutoriel](https://wiki.gamedevalliance.fr/contribuer/path/)
    - Ouvrez le menu démarrer
    - Tapez `variables d'environnement` et cliquez sur `Modifier les variables d'environnement système`
    - Cliquez sur `Variables d'environnement...`
    - Sélectionnez la variable système `Path` et cliquez sur `Modifier...`
    - Cliquez sur `C:\Program Files\maven-lite` et cliquez sur `Supprimer`
    - Cliquez sur `OK`
    - Cliquez sur `OK`
    - Cliquez sur `OK`

****

<a href="https://florobart.github.io/Maven_lite/Documentations/Installation.fr.html"><button type="button">Retour</button></a>
