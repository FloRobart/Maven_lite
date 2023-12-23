import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class MavenLite 
{
    private Map<String, String> hmArgs;
    private List<String[]> lstOptions;


    /**
     * Enregistre les arguments passés au programme
     */
    public MavenLite(String[] args)
    {
        this.genererLst();
        this.lstOptions = this.initOptions();
        this.hmArgs = new HashMap<String, String>();

        this.parseCommandLine(args);
    }

    /**
     * Parse les arguments passés au programme
     * @param args les arguments à parser 
     */
    public void parseCommandLine(String[] args)
    {
        
    }

    /**
     * Initialise la liste des options
     * @return la liste des options
     */
    public List<String[]> initOptions()
    {
        List<String[]> lst = new ArrayList<String[]>();

        /*                    nom               court   long                description */
        lst.add(new String[] {"version"       , "-v"  , "--version"       , "Afficher la version et quitter."});
        lst.add(new String[] {"create"        , "-cr" , "--create"        , "Créer l'arborescence du projet ainsi qu'un fichier de config par défaut. Si le dossier de sortie n'est pas spécifié, le dossier par défaut est le dossier courant."});
        lst.add(new String[] {"source"        , "-s"  , "--source"        , "Dossier racine du projet à compiler."});
        lst.add(new String[] {"output"        , "-o"  , "--output"        , "Dossier de sortie des fichiers compilés."});
        lst.add(new String[] {"classpath"     , "-cp" , "--classpath"     , "Liste des fichiers jar et du dossier de sortie des fichiers compilés (le même dossier que pour l'option -o) à ajouter au classpath lors de la compilation et du lancement. Les fichiers jar doivent être séparés par des ':'. La valeur par defaut du classpath est le dossier de sortie des fichiers compilés si l'option -o est utilisé, sinon le classpath sera le dossier courent."});
        lst.add(new String[] {"dependency"    , "-d"  , "--dependency"    , "Dossier contenant les fichiers jar utiliser par le programme. Tout les fichiers jar seront ajoutés au classpath lors de la compilation et du lancement."});
        lst.add(new String[] {"name"          , "-n"  , "--name"          , "Permet de changer le nom du fichier contenant les chemins des fichiers java à compiler. Le nom par defaut est 'compile.list'. Utilisable uniquement avec l'option -c."});
        lst.add(new String[] {"encoding"      , "-e"  , "--encoding"      , "Permet de changer l'encodage des fichiers java à compiler. L'encodage par defaut est 'UTF-8'. Utilisable uniquement avec l'option -c."});
        lst.add(new String[] {"main"          , "-m"  , "--main"          , "Classe principale à lancer. Utilisable uniquement avec l'option -l."});
        lst.add(new String[] {"data"          , "-dt" , "--data"          , "Dossier contenant les données du projet. Permet de copier le dossier de données dans le dossier de sortie. Utilisable uniquement avec l'option -c."});
        lst.add(new String[] {"arguments"     , "-arg", "--arguments"     , "Arguments à passer à la classe principale. Un argument par option, c'est à dire que si vous voulez passer deux arguments il faudra utiliser deux fois l'option -arg. Lordre des arguments passé à la classe principale est le même que l'ordre de passage à la commande. Les arguments de la ligne de commande sont pris en compte avant les arguments du fichier de configuration. Les arguments ne peuvent pas contenir d'espace sans peine de bug. Utilisable uniquement avec l'option -l."});
        lst.add(new String[] {"file"          , "-f"  , "--file"          , "Fichier de configuration. Permet de charger les options à partir d'un fichier de configuration, le séparateur sont l'espace et le retour à la ligne. Les options du fichier de configuration prédomine sur les options de la ligne de commande. L'option -f doit obligatoirement être la première option de la ligne de commande."});
        lst.add(new String[] {"compilation"   , "-c"  , "--compilation"   , "Compiler le projet."});
        lst.add(new String[] {"launch"        , "-l"  , "--launch"        , "Lancer le projet."});
        lst.add(new String[] {"compile-launch", "-cl" , "--compile-launch", "Compiler et lancer le projet. (équivalent à -c -l)"});
        lst.add(new String[] {"launch-compile", "-lc" , "--launch-compile", "Compiler et lancer le projet. (équivalent à -c -l)"});
        lst.add(new String[] {"help"          , "-h"  , "--help"          , "afficher l'aide et quitter."});

        return lst;
    }


    /**
     * Génère la liste des options à partir d'un fichier texte
     */
    public void genererLst()
    {
        String sRet = "";
        String fileOrig = "data/options.data";
        String fileDest = "data/lstOptions.data";
        int[] sFormat = new int[] {16, 6, 18, 1};
        List<String[]> lst = new ArrayList<String[]>();

        /* Pour chaque ligne du fichier data/options.data */
        try
        {
            Scanner scFile = new Scanner(new File(fileOrig));
            while (scFile.hasNextLine())
                lst.add(scFile.nextLine().split(";"));

            scFile.close();
        }
        catch (Exception e)
        {
            System.out.println("Erreur lors de la lecture du fichier data/options.data");
            System.exit(0);
        }

        for (String[] tab : lst)
        {
            int i = 0;
            sRet += "lst.add(new String[] {";
            for (String str : tab)
            {
                str = "\"" + str + "\"";
                sRet += String.format("%-" + sFormat[i++] + "s", str) + ", ";
            }

            sRet = sRet.substring(0, sRet.length() - 2) + "});\n";
        }

        File output = new File(fileDest);
        try
        {
            java.io.FileWriter fw = new java.io.FileWriter(output);
            fw.write(sRet);
            fw.close();
        }
        catch (Exception e)
        {
            System.out.println("Erreur lors de l'écriture du fichier data/options.data");
            System.exit(0);
        }
    }


    public static void main(String[] args)
    {
        if (args.length < -1)
        {
            System.out.println("Help");
            System.exit(0);
        }

        new MavenLite(args);
    }
}
