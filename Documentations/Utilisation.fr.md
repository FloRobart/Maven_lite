# Utilisation de Maven Lite et description de toutes les fonctionnalités

## Table des matières

- [Utilisation de Maven Lite et description de toutes les fonctionnalités](#utilisation-de-maven-lite-et-description-de-toutes-les-fonctionnalités)
  - [Table des matières](#table-des-matières)
  - [Liste des options](#liste-des-options)
  - [Utilisation de base](#utilisation-de-base)
  - [Ligne de commande](#ligne-de-commande)
    - [Possibilités de la ligne de commande](#possibilités-de-la-ligne-de-commande)
  - [Fichier de configuration](#fichier-de-configuration)
    - [Possibilités du fichier de configuration](#possibilités-du-fichier-de-configuration)
  - [create](#create)
  - [mvc](#mvc)
  - [compilation](#compilation)
  - [launch](#launch)
  - [quiet](#quiet)
  - [verbose](#verbose)
  - [exclude](#exclude)
  - [compile jar](#compile-jar)
  - [launch jar](#launch-jar)
  - [source](#source)
  - [target](#target)
  - [classpath](#classpath)
  - [libraries](#libraries)
  - [arguments](#arguments)
  - [main](#main)
  - [encoding](#encoding)
  - [export](#export)
  - [maven](#maven)
  - [version](#version)
  - [help](#help)
  - [clear](#clear)
  - [Exemple, fonctionnaliés et limites](#exemple-fonctionnaliés-et-limites)

## Liste des options

- `--create` ou `-cr` : Créer un projet java.
- `--model-view-controller` ou `-mvc` : Créer un projet java avec l'arborescence d'un projet MVC.

## Utilisation de base

- placer vous dans le dossier de votre projet java et exécuter la commande suivante :

  ```sh
  mvnl [options] [arguments]
  ```

## Ligne de commande

La ligne de commande est la méthode classique pour utiliser Maven Lite bien qu'elle soit moins pratique que l'utilisation d'un fichier de configuration.

Vous pouvez mettre autant d'options que vous le souhaitez dans n'importe quel ordre.

### Possibilités de la ligne de commande

- Elle ne prend pas en charge les commentaires.
- Il est possible de mettre des options en utilisant le format `--option` ou `-o`. Vous pouvez mettre n'inporte quel option cité dans [la liste des options](#liste-des-options).
- Il est possible de passer des arguments avec des espaces à la class main de votre projet en utilisant des cotes simples plus des guillemets, par exemple `mvnl --args '"mon argument"'`.
- Il est possible de mettre plusieurs options sur la même ligne en les séparant par un espace, par exemple `mvnl --quiet --verbose` ou `mvnl -q -v`.
- Il est possible d'échapper les caractères spéciaux avec un antislash `\`, par exemple `--args '"exemp\"le'"`. Il est fortement recommandé d'utiliser le système côte simple + guillemets pour passer des arguments à la class main de votre projet avec l'option `-args` et `--arguments` pour éviter tous problèmes d'échappement.
  - Les caractères spéciaux dans la ligne de commande sont : `\`, `"`
    - `--args '"exemp\"le"'` devient `exemp"le`
    - `--args '"-exemple"'` devient `-exemple`
    - `--args '"--exemple"'` devient `--exemple`
    - `--args '"exemp\\le"'` devient `exemp\le`
    - `--args '"exemp\\\"le"'` devient `exemp\"le`.

## Fichier de configuration

L'utilisation d'un fichier de configuration se fait avec l'option `--file` ou `-f`.

Le fichier de configuration est un fichier unique à chaque projet qui permet de configurer les options que Maven Lite devra utiliser.

Le nom par défaut du fichier de configuration est `LPOM.conf` et doit être à la racine du projet. Il est possible de le renommer mais dans ce cas il faudra préciser son nom l'ors de l'utilisation de l'option, par exemple `mvnl -f monFichier`.

si vous voulez ajouté votre CLASSPATH système au CLASSPATH de Maven Lite dans le fichier de configuration, il faut utiliser le terme `CLASSPATH` en majuscule.

### Possibilités du fichier de configuration

- Il est possible de mettre des commentaires en utilisant le caractère `#` au début de la ligne.
- Il est possible de mettre des options en utilisant le même format que dans la ligne de commande, par exemple `--option` ou `-o`. Vous pouvez mettre n'inporte quel option cité dans [la liste des options](#liste-des-options).
- Il est possible de passé des arguments avec des espaces en utilisant des guillemets, par exemple `mvnl -args "mon argument"`.
- Il est possible de mettre plusieurs options sur la même ligne en les séparant par un espace, par exemple `mvnl --quiet --verbose` ou `mvnl -q -v`.
- Il est possible d'échapper les caractères spéciaux avec un antislash `\`, par exemple `--args exemp\"le`.
  - Les caractères spéciaux dans le fichier de configuration sont : `\`, `"` et `-` uniquement s'il sont au début de l'argumentet que ce dernier n'a pas de guillement. Par exemple :
    - `--args exemp\"le` devient `exemp"le`
    - `--args \-exemple` devient `-exemple`
    - `--args "--exemple"` devient `--exemple`
    - `--args \--exemple` devient `--exemple`
    - `--args exemp\\le` devient `exemp\le`
    - `--args exemp\\\"le` devient `exemp\"le`.
- Une option et ses arguments peuvent être sur plusieurs lignes, par exemple

  ```conf
  --args
  "mon argument

      sur plusieurs lignes"
  ```

- Un exemple type de fichier de configuration est le suivant :

  ```conf
  # Source du projet
  --source src/main/java

  # Dossier de sortie des fichiers compilés
  --target target

  # Liste des libraries à ajouter au classpath
  --libraries src/main/resources/lib

  # Affiche les commandes éxécutées
  --verbose

  # Ajoute le classpath système au classpath de Maven Lite
  --classpath CLASSPATH
  ```

## create

L'utilisation d'un fichier de configuration se fait avec l'option `--create` ou `-cr`.

Il est impossible de créer un projet avec un espace dans le nom.
Il est impossible de créer un projet avec un nom qui est déjà utilisé par un fichier ou un dossier.

Vous pouvez créer un projet dans le dossier courant grâce à la commande suivante :

```sh
mvnl --create ./
```

## mvc

L'utilisation de cette option se fait avec l'option `--model-view-controller` ou `-mvc`.

Permet de spécifier à l'option [create](#create) de créer l'arborescence d'un projet MVC.

## compilation

L'utilisation de cette option se fait avec l'option `--compilation` ou `-c` ou `--compile-launch` ou `-cl` ou `--launch-compile` ou `-lc`.

Cette option permet de compiler le projet.

## launch

L'utilisation de cette option se fait avec l'option `--launch` ou `-l` ou `--compile-launch` ou `-cl` ou `--launch-compile` ou `-lc`.

Cette option permet de lancer le projet.

## quiet

L'utilisation de cette option se fait avec l'option `--quiet` ou `-q`.

Cette option permet de supprimer l'affichage de java dans le terminal lors de l'exécution du projet.

## verbose

L'utilisation de cette option se fait avec l'option `--verbose` ou `-v`.

Cette option permet d'afficher les commandes Java exécutées par Maven Lite.

## exclude

L'utilisation de cette option se fait avec l'option `--exclude` ou `-ex`.

Cette option permet d'exclure des fichiers java et des dossiers de la [compilation](#compilation).

## compile jar

L'utilisation de cette option se fait avec l'option `--compile-jar` ou `-cj`.

Cette option permet de créer un fichier jar exécutable de votre projet. Le convention de nommage du fichier jar est la suivante : `<nom>-<M>.<m>.<b>.jar`.

- `<nom>` : Nom du projet.
- `<M>` : Numéro de la version majeur. Il commence à 1 et est incrémenté à chaque nouvelle version non compatible avec la précédente et avec de grosses modifications.
- `<m>` : Numéro de la version mineur. Il commence à 0 et est incrémenté à chaque nouvelle version compatible avec la précédente et avec de petites modifications et retourne à 0 à chaque changement de version majeur.
- `<b>` : Numéro de la version de build. Il commence à 0 et est incrémenté à chaque correction de bug et retourne à 0 à chaque changement de version mineur ou majeur.

## launch jar

L'utilisation de cette option se fait avec l'option `--launch-jar` ou `-lj`.

Cette option permet de lancer le fichier jar exécutable de votre projet.

## source

L'utilisation de cette option se fait avec l'option `--source` ou `-s`.

Cette option permet de spécifier le dossier contenant les fichiers java à compiler.

## target

L'utilisation de cette option se fait avec l'option `--target` ou `-t`.

Cette option permet de spécifier le dossier de sortie des fichiers compilés, le dossier d'entrée des fichiers à lancer, le dossier de sortie des fichiers jar ainsi que le dossier d'entrée des fichiers .class à mettre dans le jar.

Vous n'avez pas besoin de créer le dossier de sortie des fichiers compilés, Maven Lite le fera pour vous.

Vous n'avez pas besoin d'ajouter le dossier de sortie des fichiers compilés au classpath, Maven Lite le fera pour vous.

## classpath

L'utilisation de cette option se fait avec l'option `--classpath` ou `-cp`.

Cette option permet de spécifier le classpath à utiliser lors de la compilation et du lancement.

## libraries

L'utilisation de cette option se fait avec l'option `--libraries` ou `-lib`.

Cette option permet de spécifier le dossier contenant les fichiers jar utiliser par le programme. Tout les fichiers jar seront ajoutés au classpath lors de la compilation et du lancement.

Vous pouvez créer des sous-dossiers dans le dossier des librairies pour mieux organiser vos fichiers jar, Maven Lite les prendra en compte.

## arguments

L'utilisation de cette option se fait avec l'option `--arguments` ou `-args`.

Cette option permet de spécifier tous les arguments à passer à la classe principale.

Vous pouvez passer des arguments avec des espaces en utilisant des côtes simples plus des guillemets, par exemple `mvnl -args '"mon argument"'`.

Ces arguments seront passés à la classe principale dans l'ordre où ils sont écrits.

Exemple avec un fichier de configuration :

- Fichier de configuration

  ```conf
  --args "arg2"
  ```

- Ligne de commande à lancé

  ```sh
  mvnl -args '"arg1"' -f -args '"arg3"' '"arg4"'
  ```

- Arguments passés à la classe principale

  ```java
  arg1 arg2 arg3 arg4
  ```

## main

L'utilisation de cette option se fait avec l'option `--main` ou `-m`.

Cette option permet de spécifier la classe principale à lancer avec le package sous la forme `package.MainClass`. Cette option est utile uniquement si vous avez plusieurs classes main dans votre projet, si se n'est pas le cas, Maven Lite trouvera et utilisera automatiquement la classe main du projet.

## encoding

L'utilisation de cette option se fait avec l'option `--encoding` ou `-e`.

Cette option permet de spécifier l'encodage des fichiers java à compiler.

## export

L'utilisation de cette option se fait avec l'option `--export` ou `-exp`.

Cette option permet de créer un fichier jar exécutable permettant de lancer le projet sans avoir installer MavenLite.

## maven

L'utilisation de cette option se fait avec l'option `--maven` ou `-mvn`.

Cette option permet de convertir le projet en projet maven en créant un fichier pom.xml et en déplaçant les fichiers si nécessaire.

## version

L'utilisation de cette option se fait avec l'option `--version` ou `-V`.

Cette option permet d'afficher la version de Maven Lite ainsi que la localisation du fichier principale de Maven Lite, la version de java, le type de build, le runtime java utilisé, la langue utilisée par le système, la plateforme d'encodage utilisée par Maven Lite, le nom du système d'exploitation, la version du noyaux du système d'exploitation et l'architecture du système.

- Exemple

  ```sh
  Maven Lite 2.0.0 
  Maven Lite home : /etc/maven-lite/
  Java version : 17.0.9, vendor : Private Build, runtime : OpenJDK Runtime Environment
  Default locale : fr_FR, platform encoding : UTF-8
  OS name : "Linux", version : "6.5.0-14-generic", architecture : "amd64"
  ```

## help

L'utilisation de cette option se fait avec l'option `--help` ou `-h`.

Cette option permet d'afficher la liste des options ainsi que leur description, le nombre d'arguments qu'elles prennent et leur valeur par défaut si elle en ont une.

## clear

L'utilisation de cette option se fait avec l'option `--clear` ou `-cle`.

Cette option permet de supprimer les fichiers dans le dossier de sortie des fichiers compilés.

## Exemple, fonctionnaliés et limites
