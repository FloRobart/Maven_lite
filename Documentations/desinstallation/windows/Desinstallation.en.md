# Maven Lite Uninstallation on Windows

<a href="https://florobart.github.io/Maven_lite/Documentations/Installation.en.html"><button type="button">Retour</button></a>

- Voir la [documentation Française](./Desinstallation.fr.md)
- See the [PDF documentation](./Desinstallation.en.pdf)

## Table of Contents

- [Maven Lite Uninstallation on Windows](#maven-lite-uninstallation-on-windows)
  - [Table of Contents](#table-of-contents)
  - [Automatic Uninstallation of Maven Lite - Windows (Recommended)](#automatic-uninstallation-of-maven-lite---windows-recommended)
  - [Manual Uninstallation of Maven Lite - Windows](#manual-uninstallation-of-maven-lite---windows)

## Automatic Uninstallation of Maven Lite - Windows (Recommended)

- Execute the following command in a PowerShell terminal as an administrator

  ```powershell
  mvnl-uninstall
  ```

## Manual Uninstallation of Maven Lite - Windows

- Delete the `C:\Program Files\maven-lite` folder by executing the following command in a PowerShell terminal

  ```powershell
  rmdir 'C:\Program Files\maven-lite'
  ```

- Confirm the deletion by typing `Y`
- Remove the `C:\Program Files\maven-lite\` folder from the system `PATH` environment variable
  - If you don't know how to do this, follow these steps or follow [this tutorial](https://wiki.gamedevalliance.fr/contribuer/path/)
    - Open the Start menu
    - Type `environment variables` and click on `Edit the system environment variables`
    - Click on `Environment Variables...`
    - Select the system variable `Path` and click on `Edit...`
    - Click on `C:\Program Files\maven-lite` and click on `Remove`
    - Click on `OK`
    - Click on `OK`
    - Click on `OK`

****

<a href="https://florobart.github.io/Maven_lite/Documentations/Installation.en.html"><button type="button">Retour</button></a>
