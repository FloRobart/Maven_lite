# Maven Lite Installation on Windows

<a href="https://florobart.github.io/Maven_lite/Documentations/Installation.en.html"><button type="button">Retour</button></a>

- Voir la [documentation Française](./Installation.fr.md)
- See the [PDF documentation](./Installation.en.pdf)

## Table of Contents

- [Maven Lite Installation on Windows](#maven-lite-installation-on-windows)
  - [Table of Contents](#table-of-contents)
  - [Automatic Installation of Maven Lite - Windows (Recommended)](#automatic-installation-of-maven-lite---windows-recommended)
  - [Manual Installation of Maven Lite - Windows](#manual-installation-of-maven-lite---windows)

## Automatic Installation of Maven Lite - Windows (Recommended)

- Download the [Compressed file of the French version](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.1.0/maven-lite_fr_2.0-1_win.zip) containing the application files.
- Unzip the compressed file by right-clicking on the file and clicking on `Extract all...`
  ![Unzip Image 1](./img/windows_unzip_1.png)
- Confirm the extraction to the folder of your choice by clicking on `Extract`
  ![Unzip Image 2](./img/windows_unzip_2.png)
- Run the `installer.bat` installation script by right-clicking on it and selecting `Run as administrator`
  ![Script Execution Image](./img/windows_execute_script.png)
- You should see a command window open and display text similar to this
  ![Script Execution Result Image](./img/windows_execution_result.png)
- Press any key to close the command window
- Add the `C:\Program Files\maven-lite` folder to the system `PATH` environment variable
  - If you don't know how to do this, execute the command below or follow these steps or follow [this tutorial](https://wiki.gamedevalliance.fr/contribuer/path/)
    - Open the Start menu
    - Type `environment variables` and click on `Edit the system environment variables`
    - Click on `Environment Variables...`
    - Select the system variable `Path` and click on `Edit...`
    - Click on `New`
    - Type `C:\Program Files\maven-lite` and click on `OK`
    - Click on `OK`
    - Click on `OK`
  - Execute the following command in a PowerShell terminal as an administrator

  ```powershell
  SETX PATH "%PATH%;C:\Program Files\maven-lite"
  ```

- You can delete the remaining unused files as well as the compressed file.

## Manual Installation of Maven Lite - Windows

- Download the [Compressed file of the French version](https://github.com/FloRobart/mavenlite.github.io/releases/download/v2.1.0/maven-lite_fr_2.0-1_win.zip) containing the application files.
- Unzip the compressed file by right-clicking on the file and clicking on `Extract all...`
  ![Unzip Image 1](./img/windows_unzip_1.png)
- Confirm the extraction to the folder of your choice by clicking on `Extract`
  ![Unzip Image 2](./img/windows_unzip_2.png)
- Create the `C:\Program Files\maven-lite` folder

  ```powershell
  mkdir 'C:\Program Files\maven-lite'
  ```

- Create the `C:\Program Files\maven-lite\etc` folder

  ```powershell
  mkdir 'C:\Program Files\maven-lite\etc'
  ```

- Move the .class and .jar files to the `C:\Program Files\maven-lite\etc` folder

  ```powershell
  MOVE hamcrest-core-1.3.jar 'C:\Program Files\maven-lite\etc'
  MOVE junit-4.13.2.jar 'C:\Program Files\maven-lite\etc'
  MOVE MavenLite.class 'C:\Program Files\maven-lite\etc'
  ```

- Move the Batch files to the `C:\Program Files\maven-lite` folder

  ```powershell
  MOVE mvnl.bat 'C:\Program Files\maven-lite'
  MOVE mvnl-uninstall.bat 'C:\Program Files\maven-lite'
  ```

- Add the `C:\Program Files\maven-lite` folder to the system `PATH` environment variable
  - If you don't know how to do this, follow these steps or follow [this tutorial](https://wiki.gamedevalliance.fr/contribuer/path/)
    - Open the Start menu
    - Type `environment variables` and click on `Edit the system environment variables`
    - Click on `Environment Variables...`
    - Select the system variable `Path` and click on `Edit...`
    - Click on `C:\Program Files\maven-lite` and click on `Remove`
    - Click on `OK`
    - Click on `OK`
    - Click on `OK`
  - Execute the following command in a PowerShell terminal as an administrator

  ```powershell
  SETX PATH "%PATH%;C:\Program Files\maven-lite"
  ```

- Delete the remaining unused files as well as the compressed file.

****

<a href="https://florobart.github.io/Maven_lite/Documentations/Installation.en.html"><button type="button">Retour</button></a>
