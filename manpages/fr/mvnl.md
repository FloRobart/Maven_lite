# NOM

mvnl - Gestionnaire de projet Java simple et léger inspiré du gestionnaire de projet `Maven`.

# SYNOPSIS

mvnl [OPTION]... [ARGUMENT]...

# DESCRIPTION

Permet de compiler et lancer un projet java en utilisant le minimum d'options et de manipulation.
Facilite la compilation et le lancement d'un projet java plus simplement que maven.

Toutes les options prennent un seul argument sauf -c et -l qui n'en prennent aucun.

L'ordre des options n'a pas d'importance sauf pour l'option -f qui doit être la première option de la ligne de commande.
les guillements double ne sont pas obligatoires, sauf si l'argument contient un espace

Les options qui sont utilisé avec -c alors qu'elles sont utilisable uniquement avec -l sont ignorées et inversement.
Les options qui sont utilisable avec -c ou -l sont utilisable avec -cl et -lc.

Les arguments obligatoires pour les options longues le sont aussi pour les options courtes

# EXEMPLES

- `mvnl -s src -o bin -c -e UTF-8` --> compile le projet java avec l'encodage UTF-8 qui se trouve dans le dossier src et met les fichiers compilés dans le dossier bin.
- `mvnl -s src -o bin -m package.Main -cl` --> compile et lance le projet java qui se trouve dans le dossier src et met les fichiers compilés dans le dossier bin, puis lance la classe package.Main.
- `mvnl -s src -o bin -m package.Main -lc -arg argument_1 -arg argument_2` --> compile et lance le projet java qui se trouve dans le dossier src et met les fichiers compilés dans le dossier bin, puis lance la classe package.Main avec les arguments argument_1 et argument_2 dans l'ordre de passage à la commande.
- `mvnl -m package.Main -l -cp bin:lib` --> lance la classe package.Main en ajoutant le dossier bin et le dossier lib au classpath du projet.
- `mvnl -m Main -d lib -l` --> lance la classe Main en ajoutant tout les fichiers jar du dossier lib au classpath du projet.
- `mvnl -s src -o bin -m package.Main -cl -dt data` --> compile le projet java qui se trouve dans le dossier src et met les fichiers compilés dans le dossier bin, puis lance la classe package.Main et copie le dossier data dans le dossier bin.
- `mvnl -f config.txt -cl` --> charge les options à partir du fichier config.txt. Content du fichier config.txt : `-s src -o bin -m package.Main`. compile et lance le projet java qui se trouve dans le dossier src et met les fichiers compilés dans le dossier bin, puis lance la classe package.Main.

# COMPORTEMENT PAR DÉFAUT

Par défaut si aucune option n'est spécifié, la commande `mvnl` affiche la page d'aide qui est affiché avec l'option `-h` ou `--help`. Cette page d'aide est différente et plus simpe de la page de manuel qui est affiché avec la commande `man mvnl`.

# OPTIONS

## Toutes les options

-v  , --version         Afficher la version et quitter.

-s  , --source          Dossier racine du projet à compiler.

-o  , --output          Dossier de sortie des fichiers compilés.

-cp , --classpath       Liste des fichiers jar et du dossier de sortie des fichiers compilés (le même dossier que pour l'option -o) à ajouter au classpath lors de la compilation et du lancement. Les fichiers jar doivent être séparés par des ':'. La valeur par defaut du classpath est le dossier de sortie des fichiers compilés si l'option -o est utilisé, sinon le classpath sera le dossier courent.

-d  , --dependency      Dossier contenant les fichiers jar utiliser par le programme. Tout les fichiers jar seront ajoutés au classpath lors de la compilation et du lancement.

-n  , --name            Permet de changer le nom du fichier contenant les chemins des fichiers java à compiler. Le nom par defaut est 'compile.list'. Utilisable uniquement avec l'option -c.

-e  , --encoding        Permet de changer l'encodage des fichiers java à compiler L'encodage par defaut est 'UTF-8'. Utilisable uniquement avec l'option -c.

-m  , --main            Classe principale à lancer. Utilisable uniquement avec l'option -l.

-dt , --data            Dossier contenant les données du projet. Permet de copier le dossier de données dans le dossier de sortie. Utilisable uniquement avec l'option -c.

-arg, --arguments       Arguments à passer à la classe principale. Un argument par option, c'est à dire que si vous voulez passer deux arguments il faudra utiliser deux fois l'option -arg. Lordre des arguments passé à la classe principale est le même que l'ordre de passage à la commande. Les arguments de la ligne de commande sont pris en compte avant les arguments du fichier de configuration. Les arguments ne peuvent pas contenir d'espace sans peine de bug. Utilisable uniquement avec l'option -l.

-f  , --file            Fichier de configuration. Permet de charger les options à partir d'un fichier de configuration, le séparateur sont l'espace et le retour à la ligne. Les options du fichier de configuration prédomine sur les options de la ligne de commande. L'option -f doit obligatoirement être la première option de la ligne de commande.

-c  , --compilation     Compiler le projet.

-l  , --launch          Lancer le projet.

-cl , --compile-launch  Compiler et lancer le projet. (équivalent à -c -l)

-lc , --launch-compile  Compiler et lancer le projet. (équivalent à -c -l)

-h  , --help            afficher l'aide et quitter.

## Les options obligatoires pour la compilation sont :

-s, --source       Dossier racine du projet à compiler.

-o, --output       Dossier de sortie des fichiers compilés.

-c, --compilation  Compiler le projet.

## Les options obligatoires pour le lancement sont :

-m , --main       Classe principale à lancer.

-l , --launch     Lancer le projet.

-cp, --classpath  Voir l'option -cp dans listes des options.

## Les options obligatoires pour la compilation et le lancement sont :

-s , --source          Dossier racine du projet à compiler.

-o , --output          Dossier de sortie des fichiers compilés.

-m , --main            Classe principale à lancer.

-cl, --compile-launch  Compiler et lancer le projet. (équivalent à -c -l)

# CODES DE RETOUR

0 : Tout c'est bien passé.

1 : Une erreur est survenue.

# FICHIERS

Maven Lite est constitué d'uniquement 2 fichiers.

- `mvnl`, le fichier principal qui se situe dans le dossier `/usr/bin`.
- `mvnl.1.gz`, le fichier d'aide contenant la page de manuel afficher avec la commande `man mvnl` qui se situe dans le dossier `/usr/local/man/fr/man1`

# BOGUES

Il y un seul bogue connu qui concerne les arguments avec des espaces, que se sois en ligne de commande ou dans le fichier de configuration. Il ne faut donc pas mettre d'espaces dans les arguments.

# AUTHEUR

Écrit par Robart Floris.

# RAPPORT DE BOGUES

Reporter les bugs par mail à l'adresse <florisrobart.pro@gmail.com> en précisant le nom du programme concerné.