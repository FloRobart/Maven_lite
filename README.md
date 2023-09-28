# Maven Lite

- Version : 1.1.0
- La version 2.0 est en préparation sous le nom de Java Builder

## Compatibilité

- Windows 10 (Testé sur windows 10 22H2)
- Windows 11 (Testé sur windows 11 22H2)
- Linux possèdant `bash` (Testé sur Ubuntu 22.04 LTS et Debian 12)
- MacOS Mojave 10.14.6 et inférieur (Non testé)

## Description

Maven Lite est un système de gestion de projet java qui permet de compiler et lancer des projets java très simplement et rapidement.

Le but est de pouvoir compiler et/ou lancer un projet java en une seule commande, comme maven mais en plus simple.

Maven Lite est plus simple que Maven car il ne gère pas les dépendances hors local, les plugins, les phases de build, etc. Il est donc adapté pour les projets simples qui ne nécessitent pas de gérer des dépendances externes. Pour des projets plus complexes, il est conseillé d'utiliser Maven.

Maven lite permet de compiler le projet dans un dossier target, gèrer les dépendances locales (fichier jar) et éxecuter le projet.

L'intéret principale de maven lite dans la gestion des dépendances est de fournir au script un dossier qui contient toutes les dépendances, le script ajoutera automatiquement les dépendances dans le classpath.

Les fichiers d'installation permettent d'installer Maven Lite sur votre ordinateur et de profiter de la commande 'mvnl'. Cela vous évitera de devoir copier le fichier maven_lite.XX dans chaque projet.

Il est possible que cette première version de Maven Lite contienne des bugs. Si vous en trouvez, n'hésitez pas à les signaler.

La version 1.1.0 prend en charge uniquement les arguments Java sans espace.

## Prérequis

- Disposer des droits administrateurs si vous voulez utiliser les installations automatiques.

Si vous ne disposez pas des droits administrateurs, vous pouvez installer maven lite manuellement en suivant les instructions de la partie 'Installation manuelle'.

## Utilisation

- placer vous dans le dossier de votre projet java et exécuter la commande suivante :

  ```sh
  mvnl [options] [arguments]
  ```

### Exemple, fonctionnaliés et limites

Vous avez un projet java dans le dossier 'C:\Users\user\Documents\MonProjetJava'
Tout le code source se trouve dans le dossier 'src' et les fichiers .class doivent être compilés dans le dossier 'bin'. Votre projet contient des dépendances sous forme de fichier jar qui se trouve dans le dossier 'lib'.

Pour compiler le projet, vous devez ouvrir un terminal, vous placer dans le dossier 'C:\Users\user\Documents\MonProjetJava' puis exécuter la commande suivante :

```sh
mvnl -s src -o bin -d lib -c
```

Maintenant que votre projet est compilé, vous voulez lancer la classe principal 'Main.java' dans le package 'com.exemple'. Pour cela, vous devez exécuter la commande suivante :

```sh
mvnl -cp bin -d lib -m com.exemple.Main -l
```

Il est possible de combiner les deux commandes en une seule :

```sh
mvnl -s src -o bin -d lib -cp bin -m com.exemple.Main -cl
```

Comme vous pouvez le voir, ça fait une commande assez longue à taper. C'est pour cela que vous pouvez créer un fichier de configuration, par exemple 'config.txt' puis mettre les arguments dedans.

Le fichier de configuration doit contenir les options et argument de la commande. Soit une option avec son argument par ligne, soit plusieurs options et arguments sur la même ligne séparé par un espace.

Les options du fichier de configuration prévalent sur les options de la ligne de commande, il n'est donc pas possible de modifier une option dans la ligne de commande si elle est déjà présente dans le fichier de configuration. Cependant, il est possible de mettre un partie des options dans le fichier de configuration et le reste dans la ligne de commande.

Exemple de fichier de configuration :

```txt
-s src -o bin
-d lib -cp bin -m com.exemple.Main
```

- Une fois que vous avez créé le fichier de configuration, vous pouvez exécuter la commande suivante pour compiler et lancez le projet :

  ```sh
  mvnl -f config.txt -cl
  ```

Je vous recommande de ne pas mettre les options '-c' et '-l' dans le fichier de configuration car vous n'avez pas besoin de compiler et lancer le projet à chaque fois. Cela vous permettra de les mettres dans la commande quand vous en aurez besoin. Malgré tout, vous pouvez les mettre dans le fichier de configuration si vous le souhaitez.

- Pour compiler le projet, vous pouvez exécuter la commande suivante :

  ```sh
  mvnl -f config.txt -c
  ```

- Pour lancer le projet, vous pouvez exécuter la commande suivante :

  ```sh
  mvnl -f config.txt -l
  ```

Les options inutiles comme l'option -s pour lancer le projet seront ignorées, donc pas de problème si vous les laissez dans le fichier de configuration.

L'ordre des options n'a pas d'importance sauf les options -f, -h et -v qui doivent être la première option si elles sont utilisées.

## Installation automatique

### Windows

- Ouvrir un terminal en tant qu'administrateur
- Se placer dans le dossier de votre choix, par exemple 'C:\Users\user\Documents'
- Clonez le dépôt git :

  ```sh
  git clone https://github.com/FloRobart/Maven_lite.git
  ```

- Se placer dans le dossier 'Maven_lite'

  ```sh
  cd Maven_lite
  ```

- Exécutez le script d'installation :

  ```sh
  .\installer.bat
  ```

- Une fois l'installation terminée, vous pouvez supprimer le dossier 'Maven_lite' si vous le souhaitez.

### Linux

- Ouvrir un terminal
- Se placer dans le dossier de votre choix, par exemple '/home/user/Documents'
- Clonez le dépôt git :

  ```sh
  git clone https://github.com/FloRobart/Maven_lite.git
  ```

- Se placer dans le dossier 'Maven_lite'

  ```sh
  cd Maven_lite
  ```

- Donnez les droits d'exécution au script d'installation :

  ```sh
  chmod +x installer.sh
  ```

- Exécutez le script d'installation :

  ```sh
  ./installer.sh
  ```

- Une fois l'installation terminée, vous pouvez supprimer le dossier 'Maven_lite' si vous le souhaitez.

## Installation manuelle

Si vous utilisez les mêmes chemins d'installation que dans les exemples, vous pourrez utiliser les scripts de désinstallation automatique pour désinstaller maven lite.

### Windows

- copier le fichier 'maven_lite.bat' dans le dossier de votre choix, par exemple 'C:\Program Files\Maven_Lite'
- renommez le fichier 'maven_lite.bat' en 'mvnl.bat'
- Ajoutez le dossier 'C:\Program Files\Maven_lite' dans la variable d'environnement utilisateur 'PATH' sois grace à l'interface graphique, sois en exécutant la commande suivante :

  ```bat
  setx path "%PATH%;C:\Program Files\Maven_Lite"
  ```

### Linux Avec les droits administrateurs

- Ajoutez les droits d'exécution au fichier 'maven_lite.sh' :

  ```sh
  chmod +x /home/$USER/.maven_lite/maven_lite.sh
  ```

- copier le fichier 'maven_lite.sh' dans le dossier `/bin`

  ```sh
  sudo cp maven_lite.sh /bin/maven_lite.sh
  ```

- renommez le fichier 'maven_lite.sh' en 'mvnl'

  ```sh
  sudo mv /bin/maven_lite.sh /bin/mvnl
  ```

### Linux Sans les droits administrateurs

- copier le fichier 'maven_lite.sh' dans le dossier de votre choix, par exemple '/home/$USER/.maven_lite'

  ```sh
  cp maven_lite.sh /home/$USER/.maven_lite/maven_lite.sh
  ```

- Ajoutez les droits d'exécution au fichier 'maven_lite.sh' :

  ```sh
  chmod +x /home/$USER/.maven_lite/maven_lite.sh
  ```

- Ajoutez une alias dans le fichier de configuration de votre shell, par exemple dans le fichier '.bashrc' :

  ```sh
  # Maven Lite
  alias mvnl='bash /home/$USER/.maven_lite/maven_lite.sh'
  # Fin Maven Lite
  ```

- Assurez-vous de bien mettre les commentaires comme dans l'exemple ci-dessus pour pouvoir désinstaller Maven Lite facilement en cas de besoins ou pour éviter les problèmes en cas d'éxecution du script d'installation.

- Rechargez votre fichier de configuration de votre shell, par exemple en exécutant la commande suivante :

  ```sh
  source /home/$USER/.bashrc
  ```

## Désinstallation automatique

Pour désinstaller Maven Lite, il suffit de suivre les instructions d'installation automatique mais en exécutant le script de désinstallation à la place du script d'installation.

## Désinstallation manuelle

### Windows

- Supprimer le dossier d'installation de maven lite, par exemple le dossier 'C:\Program Files\Maven_Lite'
- Supprimer ce même dossier, par exemple 'C:\Program Files\Maven_lite' de la variable d'environnement utilisateur 'PATH' grace à l'interface graphique

### Linux

- Supprimer le fichier `/bin/mvnl`

  ```sh
  sudo rm /bin/mvnl
  ```
