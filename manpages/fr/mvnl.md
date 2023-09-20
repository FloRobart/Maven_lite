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

Toutes les options prennent un seul argument sauf -c et -l qui n\'en
prennent aucun.

L\'ordre des options n\'a pas d\'importance sauf pour l\'option \--file
(-f) et \--help (-h) qui doit être la première option de la ligne de
commande s\'ils sont utilisés. Les guillemets doubles ne sont pas
obligatoires.

Les options qui sont utilisées avec -c alors qu\'elles sont utilisables
uniquement avec -l sont ignorées et inversement. Les options qui sont
utilisables avec -c ou -l sont utilisables avec -cl et -lc.

Les arguments obligatoires pour les options longues le sont aussi pour
les options courtes. Par exemple, -s et \--source sont équivalents.

# EXEMPLES

-   `mvnl -s src -o bin -c -e UTF-8` \--\> compile le projet Java avec
    l\'encodage UTF-8 qui se trouve dans le dossier src et met les
    fichiers compilés dans le dossier bin.

-   `mvnl -s src -o bin -m package.Main -cl` \--\> compile et lance le
    projet Java qui se trouve dans le dossier src et met les fichiers
    compilés dans le dossier bin, puis lance la classe package.Main.

-   `mvnl -s src -o bin -m package.Main -lc -arg argument_1 -arg argument_2`
    \--\> compile et lance le projet Java qui se trouve dans le dossier
    src et met les fichiers compilés dans le dossier bin, puis lance la
    classe package.Main avec les arguments argument_1 et argument_2 dans
    l\'ordre de passage à la commande.

-   `mvnl -m package.Main -l -cp bin;lib` \--\> lance la classe
    package.Main en ajoutant le dossier bin et le dossier lib au
    classpath du projet. Attention, sous Linux, le séparateur de chemin
    est \':\' et non

-   `mvnl -m Main -d lib -l` \--\> lance la classe Main en ajoutant tous
    les fichiers JAR du dossier lib au classpath du projet.

-   `mvnl -s src -o bin -m package.Main -cl -dt data` \--\> compile le
    projet Java qui se trouve dans le dossier src et met les fichiers
    compilés dans le dossier bin, puis lance la classe package.Main et
    copie le dossier data dans le dossier bin.

-   `mvnl -f config.txt -cl` \--\> charge les options à partir du
    fichier config.txt. Contenu du fichier config.txt :
    `-s src -o bin -m package.Main`. Compile et lance le projet Java qui
    se trouve dans le dossier src et met les fichiers compilés dans le
    dossier bin, puis lance la classe package.Main.

# COMPORTEMENT PAR DÉFAUT

Par défaut, si aucune option n\'est spécifiée, la commande `mvnl`
affiche la page d\'aide qui est affichée avec l\'option `-h` ou
`--help`. Cette page d\'aide est différente et plus simple que la page
de manuel qui est affichée avec la commande `man mvnl`.

# OPTIONS

## Toutes les options

-v , \--version Affiche la version et quitter.

-s , \--source Dossier racine du projet à compiler.

-o , \--output Dossier de sortie des fichiers compilés.

-cp , \--classpath Liste des fichiers JAR à ajouter au classpath lors de
la compilation et du lancement. Les fichiers JAR doivent être séparés
par des \':\'. La valeur par défaut du classpath est le dossier de
sortie des fichiers compilés si l\'option -o est utilisée, sinon le
programme vous demandera de spécifier le classpath.

-d , \--dependency Dossier contenant les fichiers JAR utilisés par le
programme. Tous les fichiers JAR présent à la racine de ce dossier
seront ajoutés au classpath lors de la compilation et du lancement.

-n , \--name Permet de changer le nom du fichier contenant les chemins
des fichiers Java à compiler. Le nom par défaut est \`compile.list\'.
Utilisable uniquement avec l\'option -c.

-e , \--encoding Permet de changer l\'encodage des fichiers Java à
compiler. L\'encodage par défaut est \`UTF-8\'. Utilisable uniquement
avec l\'option -c.

-m , \--main Classe principale à lancer. Utilisable uniquement avec
l\'option -l.

-dt , \--data Dossier contenant les données du projet. Permet de copier
le dossier de données dans le dossier de sortie. Utilisable uniquement
avec l\'option -c.

-arg , \--arguments Arguments à passer à la classe principale. Un
argument par option, c\'est-à-dire que si vous voulez passer deux
arguments il faudra utiliser deux fois l\'option -arg. L\'ordre des
arguments passés à la classe principale est le même que l\'ordre de
passage à la commande. Les arguments de la ligne de commande sont pris
en compte avant les arguments du fichier de configuration. Les arguments
ne peuvent pas contenir d\'espace sous peine de bug. Utilisable
uniquement avec l\'option -l.

-f , \--file Fichier de configuration. Permet de charger les options à
partir d\'un fichier de configuration, les séparateurs sont l\'espace et
le retour à la ligne. Les options du fichier de configuration
prédominent sur les options de la ligne de commande. L\'option -f doit
obligatoirement être la première option de la ligne de commande.

-c , \--compilation Compile le projet.

-l , \--launch Lance le projet.

-cl , \--compile-launch Compile puis lance le projet. (équivalent à -c
-l)

-lc , \--launch-compile Compile puis lance le projet. (équivalent à -l
-c)

-h , \--help Affiche l\'aide et quitter.

## Les options obligatoires pour la compilation sont :

-s , \--source Dossier racine du projet à compiler.

-o , \--output Dossier de sortie des fichiers compilés.

-c , \--compilation Compile le projet.

## Les options obligatoires pour le lancement sont :

-m , \--main Classe principale à lancer.

-l , \--launch Lance le projet.

-cp , \--classpath Voir l\'option -cp dans la liste des options
ci-dessus.

## Les options obligatoires pour la compilation et le lancement sont :

-s , \--source Dossier racine du projet à compiler.

-o , \--output Dossier de sortie des fichiers compilés.

-m , \--main Classe principale à lancer.

-cl , \--compile-launch Compile et lance le projet. (équivalent à -c -l)

# CODES DE RETOUR

0 : Tout s\'est bien passé.

1 : Une erreur est survenue.

# FICHIERS

Maven Lite est constitué uniquement de 2 fichiers.

-   `'mvnl'`, le fichier principal qui se situe dans le dossier
    \'/usr/bin/\'.

-   `'mvnl.1.gz'`, le fichier d\'aide contenant la page de manuel
    affichée avec la commande `man mvnl` qui se situe dans le dossier
    `'/usr/local/man/fr/man1/'`.

# BOGUES

Il y a un seul bogue connu qui concerne les arguments avec des espaces,
que ce soit en ligne de commande ou dans le fichier de configuration. Il
ne faut donc pas mettre d\'espaces dans les arguments.

# AUTEUR

Écrit par Robart Floris.

# RAPPORT DE BOGUES

Reporter les bogues par mail à l\'adresse \<florisrobart.pro@gmail.com\>
en précisant quel est le bogue, comment puis-je le reproduire et qu\'il
concerne Maven Lite.
