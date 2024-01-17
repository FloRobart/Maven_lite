---
date: Septembre 2023
section: 1
title: Maven Lite
---

# NOM

mvnl - Gestionnaire de projet Java simple et léger inspiré du
gestionnaire de projet `Maven`.

# SYNOPSIS

mvnl \[OPTION\]\... \[ARGUMENT\]\...

# DESCRIPTION

Permet de compiler et lancer un projet Java en utilisant le minimum
d\'options et de manipulations. Facilite la compilation et le lancement
d\'un projet Java plus simplement que Maven.

L\'ordre des options n\'a pas d\'importance.

Si une option est utilisée plusieurs fois, seule la dernière est prise
en compte sauf pour les options qui prennent plusieurs arguments comme
\--arguments, \--classpath et \--exclude.

Nous vous conseillons vivement d'utiliser l'arborecence par défaut
générée par la commande `mvnl --create`. Cela vous permettra de
compiler et lancer votre projet Java sans avoir de fichier de
configuration ni d'options à spécifier dans la ligne de commande
à l'exception de l'option -c et -l.

# EXEMPLES

-   `mvnl -s src -t target -c -e UTF-8` \--\> compile le projet Java avec
    l\'encodage UTF-8 qui se trouve dans le dossier src et met les
    fichiers compilés dans le dossier target.

-   `mvnl -s src -t target -m package.Main -cl` \--\> compile et lance le
    projet Java qui se trouve dans le dossier src et met les fichiers
    compilés dans le dossier target, puis lance la classe package.Main.

-   `mvnl -s src -t target -m package.Main -lc -args "argument 1" "argument 2"`
    \--\> compile et lance le projet Java qui se trouve dans le dossier
    src et met les fichiers compilés dans le dossier target, puis lance la
    classe package.Main avec les arguments `argument 1` et `argument 2` dans
    l\'ordre de passage à la commande.

-   `mvnl -m package.Main -l -cp target lib` \--\> lance la classe
    package.Main en ajoutant le dossier target et le dossier lib au
    classpath du projet.

-   `mvnl -m Main -lib lib -l` \--\> lance la classe Main en ajoutant tous
    les fichiers JAR du dossier lib et ses sous dossier au classpath du projet.

-   `mvnl -f config.txt -cl` \--\> charge les options à partir du
    fichier config.txt. Contenu du fichier config.txt :
    `-s src -t target -m package.Main`. Compile et lance le projet Java qui
    se trouve dans le dossier src et met les fichiers compilés dans le
    dossier bin, puis lance la classe package.Main.

# COMPORTEMENT PAR DÉFAUT

Par défaut, si aucune option n\'est spécifiée, la commande `mvnl`
affiche une vue stylisée de l'affichage de la commande `mvnl --version`.

# OPTIONS

## Toutes les options

Usage : mvnl [options] [argument]

Options :
  -f   , \--file               Permet de charger les options à partir d'un fichier de
                              configuration. En savoir plus :
                              Nombre de d'arguments : de 0 à 1.
                              Valeur par défaut     : LPOM.conf.

  -cr  , \--create             Créer l'arborescence du projet ainsi qu'un fichier de
                              config par défaut.
                              Nombre de d'arguments : de 0 à 1.
                              Valeur par défaut     : NewProject.

  -mvc , \--model-view-controllerPermet de spécifier à l'option '--create' de créer
                              l'arborescence d'un projet MVC.
                              Nombre de d'argument  : 0.

  -c   , \--compilation        Compile le projet.
                              Nombre de d'argument  : 0.

  -l   , \--launch             Lance le projet.
                              Nombre de d'argument  : 0.

  -cl  , \--compile-launch     Compile et lance le projet. (équivalent à -c -l)
                              Nombre de d'argument  : 0.

  -lc  , \--launch-compile     Compile et lance le projet. (équivalent à -c -l)
                              Nombre de d'argument  : 0.

  -q   , \--quiet              Permet de supprimer l'affichage de java dans le terminal
                              lors de l'exécution du projet.
                              Nombre de d'argument  : 0.

  -v   , \--verbose            Permet d'afficher les commandes exécutées.
                              Nombre de d'argument  : 0.

  -ex  , \--exclude            Permet d'exclure des fichiers java et des dossiers de la
                              compilation. Si vous voulez passé un argument qui
                              commencer par '-' échapper le caractère '-' avec deux '\'
                              comme ceci : '-ex \\-fichier'.
                              Nombre de d'argument  : unlimited.

  -cj  , \--compile-jar        Permet de créer un fichier jar de votre projet. Vous pouvez
                              spécifier le nom du fichier jar à créer. Si vous ne spécifiez
                              pas de nom, le nom du fichier jar sera le nom du projet.
                              Nombre de d'arguments : de 0 à 1.

  -lj  , \--launch-jar         Permet de lancer un fichier jar exécutable. Vous pouvez
                              spécifier le nom du fichier jar à lancer. Si vous ne
                              spécifiez pas de nom, le nom du fichier jar sera le nom du
                              projet.
                              Nombre de d'arguments : de 0 à 1.

  -it  , \--integrate-test     Permet d'intégrer les tests unitaires au projet.
                              Nombre de d'argument  : 0.

  -s   , \--source             Dossier contenant les fichiers java à compiler.
                              Nombre de d'argument  : 1.
                              Valeur par défaut     : src/main/java.

  -t   , \--target             Dossier de sortie des fichiers compilés. Ce dossier sera
                              créer si il n'existe pas et sera automatiquement ajouter au
                              classpath lors de la compilation et du lancement.
                              Nombre de d'argument  : 1.
                              Valeur par défaut     : target.

  -r   , \--resources          Dossier contenant les fichiers ressources à copier dans le
                              dossier de sortie des fichiers compilés dans le cas de la
                              création d'un fichier jar.
                              Nombre de d'argument  : 1.
                              Valeur par défaut     : src/resources.

  -cp  , \--classpath          Permet de spécifier le classpath à utiliser lors de la
                              compilation et du lancement. Si vous voulez ajouter
                              plusieurs éléments au classpath, il faut les séparer par
                              des ':'.
                              Nombre de d'argument  : unlimited.

  -lib , \--libraries          Dossier contenant les fichiers jar utiliser par le
                              programme. Tout les fichiers jar seront ajoutés au
                              classpath lors de la compilation et du lancement.
                              Nombre de d'arguments : de 0 à 1.
                              Valeur par défaut     : src/resources/lib.

  -args, \--arguments          Tous les arguments à passer à la classe principale. Si vous
                              voulez passé un argument qui commencer par '-' échapper le
                              caractère '-' avec deux '\' comme ceci : '-args
                              \\-argument_pour_le_main'.
                              Nombre de d'argument  : unlimited.

  -m   , \--main               Classe principale à lancer. Si vous voulez lancer une
                              classe qui se trouve dans un package, il faut spécifier le
                              package avec le nom de la classe comme ceci :
                              'package.nom.MainClass'
                              Nombre de d'argument  : 1.

  -e   , \--encoding           Permet de changer l'encodage des fichiers java à compiler.
                              Nombre de d'argument  : 1.
                              Valeur par défaut     : UTF-8.

  -exp , \--export             Permet de créer un fichier jar exécutable permettant de
                              lancer le projet sans avoir installer MavenLite.
                              Nombre de d'arguments : de 0 à 1.
                              Valeur par défaut     : run.java.

  -mvn , \--maven              Convertie le projet en projet maven en créant un fichier
                              pom.xml et en déplaçant les fichiers si nécessaire.
                              Nombre de d'argument  : 0.

  -V   , \--version            Affiche la version.
                              Nombre de d'argument  : 0.
                              Valeur par défaut     : 2.0.0.

  -h   , \--help               Affiche l'aide et quitte.
                              Nombre de d'argument  : 0.

  -clr , \--clear              Permet de supprimer les fichiers dans le dossier de sortie
                              des fichiers compilés.
                              Nombre de d'argument  : 0.

# CODES DE RETOUR

0 : Tout s\'est bien passé.

1 : Une erreur est survenue.

# FICHIERS

Maven Lite est constitué de 6 fichiers :

-   `'mvnl'`, le fichier principal qui se situe dans le dossier
    `'/usr/local/bin/'`.

-   `'mvnl-uninstall'`, le fichier de désinstallation qui se situe dans
    le dossier `'/usr/local/bin/'`.

-   `'MavenLite.class'`, le fichier de la classe MavenLite qui se situe
    dans le dossier `'/usr/local/etc/maven-lite'`.

-   `'hamcrest-core-1.3.jar'`, le fichier JAR de la librairie
    Hamcrest-Core qui se situe dans le dossier `'/usr/local/etc/maven-lite'`.

-   `'junit-4.13.1.jar'`, le fichier JAR de la librairie JUnit qui se
    situe dans le dossier `'/usr/local/etc/maven-lite'`.

-   `'mvnl.1.gz'`, le fichier d\'aide contenant la page de manuel
    affichée avec la commande `'man mvnl'` qui se situe dans le dossier
    `'/usr/local/man/fr/man1/'` pour la version française et dans le
    dossier `'/usr/local/man/en/man1/'` pour la version anglaise.

# BOGUES

Il y a un seul bogue connu qui concerne les arguments avec des guillemets
antislashs, etc.

# AUTEUR

Écrit par Robart Floris.

# RAPPORT DE BOGUES

Reporter les bogues par mail à l\'adresse \<florisrobart.pro@gmail.com\>
en précisant quel est le bogue, comment puis-je le reproduire et qu\'il
concerne Maven Lite ainsi que la version utilisé.
