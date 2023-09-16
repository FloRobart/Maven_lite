NOM
        mvnl - Gestionnaire de projet Java simple et léger inspiré du gestionnaire de projet `Maven`.
SYNOPSIS
        mvnl [OPTION]... [ARGUMENT]...
DESCRIPTION
        Permet de compiler et lancer un projet java en utilisant le minimum d'options et de manipulation.
        Facilite la compilation et le lancement d'un projet java plus simplement que maven.

        Toutes les options prennent un seul argument sauf -c et -l qui n'en prennent aucun.

        L'ordre des options n'a pas d'importance sauf pour l'option -f qui doit être la première option de la ligne de commande.
        les guillements double ne sont pas obligatoires, sauf si l'argument contient un espace

        Les options qui sont utilisé avec -c alors qu'elles sont utilisable uniquement avec -l sont ignorées et inversement.
        Les options qui sont utilisable avec -c ou -l sont utilisable avec -cl et -lc.

        Les arguments obligatoires pour les options longues le sont aussi pour les options courtes

EXEMPLES
        Quelques exemples d'usage courant.
COMPORTEMENT PAR DÉFAUT
        Par défaut si aucune option n'est spécifié, la commande `mvnl` affiche la page d'aide qui est affiché avec l'option `-h` ou `--help`. Cette page d'aide est différente et plus simpe de la page de manuel qui est affiché avec la commande `man mvnl`.
OPTIONS
        Une description des options de ligne de commande.
    Options générales (modifiables)
        Une description des options générale de ligne de commande générales.
    Options spécifiques (modifiables)
        Une description des options spécifiques de ligne de commande.
CODES DE RETOUR
        0 : Tout c'est bien passé.
        1 : Une erreur est survenue.
FICHIERS
        Maven Lite est constitué d'uniquement 2 fichiers.
        - `mvnl`, le fichier principal qui se situe dans le dossier `/usr/bin`.
        - `mvnl.1.gz`, le fichier d'aide contenant la page de manuel afficher avec la commande `man mvnl` qui se situe dans le dossier `/usr/local/man/fr/man1`
BOGUES
        Il y un seul bogue connu qui concerne les arguments avec des espaces, que se sois en ligne de commande ou dans le fichier de configuration.
        Il ne faut donc pas mettre d'espaces dans les arguments.
AUTHEUR
        Écrit par Robart Floris.
RAPPORT DE BOGUES
        Reporter les bugs par mail à l'adresse <florisrobart.pro@gmail.com> en précisant le nom du programme concerné.
