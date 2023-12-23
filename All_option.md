resources='src/main/resources'

-v , --version Afficher la version et quitter.

-s , --source Dossier racine du projet à compiler. par défaut le dossier 'src/main/java', s'il existe, sinon le dossier courant.

-t , --target Dossier de sortie des fichiers compilés, par défaut 'target'.

-cp  ,  --classpath  Liste  des  fichiers JAR et du dossier de sortie des fichiers compilés (le même dossier que pour l'option -o) à
ajouter au classpath lors de la compilation et du lancement. Les fichiers JAR doivent être séparés par des ':'. La valeur par défaut
du classpath est le dossier de sortie des fichiers compilés si l'option -o est utilisée, sinon le classpath sera le dossier courant.

-d  ,  --dependency  Dossier contenant les fichiers JAR utilisés par le programme. Tous les fichiers JAR seront ajoutés au classpath
lors de la compilation et du lancement. Par défaut le dossier '${resources}/lib' s'il existe, sinon aucun fichier JAR ne sera ajouté au classpath.

-e  , --encoding Permet de changer l'encodage des fichiers Java à compiler. L'encodage par défaut est `UTF-8'. Utilisable uniquement
avec l'option -c.

-dt , --data Dossier contenant les données du projet. Permet de copier le dossier de données dans le dossier de  sortie.  Utilisable
uniquement avec l'option -c. par default le dossier '${resources}/*'

-arg  ,  --arguments  Arguments à passer à la classe principale. Un argument par option, c'est-à-dire que si vous voulez passer deux
arguments il faudra utiliser deux fois l'option -arg. L'ordre des arguments passés à la classe principale est le même que l'ordre de
passage  à  la  commande. Les arguments de la ligne de commande sont pris en compte avant les arguments du fichier de configuration.
Les arguments ne peuvent pas contenir d'espace sous peine de bug. Utilisable uniquement avec l'option -l.

-f , --file Fichier de configuration. Permet de charger les options à partir d'un fichier de  configuration,  les  séparateurs  sont
l'espace et le retour à la ligne. Les options du fichier de configuration prédominent sur les options de la ligne de commande. L'op‐
tion -f doit obligatoirement être la première option de la ligne de commande.

-c , --compilation Compiler le projet.

-l , --launch Lancer le projet.

-cl , --compile-launch Compiler et lancer le projet. (équivalent à -c -l)

-lc , --launch-compile Compiler et lancer le projet. (équivalent à -c -l)

-h , --help Afficher l'aide et quitter.