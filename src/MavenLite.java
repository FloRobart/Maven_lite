//!/usr/bin/env java

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * MavenLite est un programme qui permet de compiler et de lancer un projet java
 * sans avoir à utiliser Maven.
 * @author Floris Robart
 * @version 2.0.0
 * @since 1.0.0
 * @see <a href="https://github.com/FloRobart/Maven_lite/blob/master/maven_lite.sh">maven_lite.sh</a>
 */
public class MavenLite 
{
    /*-----------------------------------*/
    /* Constantes et valeurs par défauts */
    /*-----------------------------------*/
    private static final String AUTHOR          = "Floris Robart";
    private static final String MAVEN_LITE_HOME = "/usr/share/maven_lite ?";
    private static final String VERSION         = "2.0.0";
    private static final String SOURCE          = "src";
    private static final String CLASSPATH       = "bin";
    private static final String ENCODING        = "UTF-8";
    private static final String FILE            = "data/config-test.txt";
    private static final String EXPORT          = "run.java";
    private static final String DEPENDENCY      = "lib";
    private static final String CREATE          = ".";
    private static final String OUTPUT          = "bin";

    /*-----------*/
    /* Variables */
    /*-----------*/
    /* Parse et exécution des options */
    private List<String[]> lstOptions;
    private Map<String, String> hmArgs;
    private int countArgs = 1;

    /* Compilation et lancement */
    private boolean compil = false;
    private boolean launch = false;



    /*==============*/
    /* Constructeur */
    /*==============*/
    /**
     * Coeur du programme
     */
    public MavenLite(String[] args)
    {
        this.lstOptions = this.initOptions();
        this.hmArgs = new HashMap<String, String>();
        this.parseOptions(args);

        this.lstOptions = this.sortList(this.lstOptions);
        this.executeOption(this.lstOptions);
    }



    /*==========================================*/
    /* Méthodes pour la préparation des options */
    /*==========================================*/
    /**
     * Initialise la liste des options.
     * <br />
     * Les options sont sous la forme d'un tableau de String.
     * <ul>
     *   <li>0 : nom de l'option</li>
     *   <li>1 : nom court de l'option</li>
     *   <li>2 : nom long de l'option</li>
     *   <li>3 : nombre d'argument de l'option minimum</li>
     *   <li>4 : nombre d'argument de l'option maximum</li>
     *   <li>5 : description de l'option</li>
     * </ul>
     * @return la liste des options possible
     */
    public List<String[]> initOptions()
    {
        List<String[]> lst = new ArrayList<String[]>();

        /*                    nom               court    long                nb argument  nb argument  valeur                utilisé    description       */
        /*                    0                 1        2                   3            4            5                     6          7                 */
        lst.add(new String[] {"file"          , "-f"   , "--file"          , "0"        , "1"        , MavenLite.FILE      , "0", "Fichier de configuration. Permet de charger les options à partir d'un fichier de configuration, le séparateur sont l'espace et le retour à la ligne. Les options du fichier de configuration prédomine sur les options de la ligne de commande. L'option -f doit obligatoirement être la première option de la ligne de commande."});
        /* Tous se qui est au dessus de cette ligne ne doit pas être déplacer, leur ordre est important */
        lst.add(new String[] {"compilation"   , "-c"   , "--compilation"   , "0"        , "0"        , null                , "0", "Compiler le projet."});
        lst.add(new String[] {"compile-launch", "-cl"  , "--compile-launch", "0"        , "0"        , null                , "0", "Compiler et lancer le projet. (équivalent à -c -l)"});
        lst.add(new String[] {"launch-compile", "-lc"  , "--launch-compile", "0"        , "0"        , null                , "0", "Compiler et lancer le projet. (équivalent à -c -l)"});
        lst.add(new String[] {"launch"        , "-l"   , "--launch"        , "0"        , "0"        , null                , "0", "Lancer le projet."});
        lst.add(new String[] {"maven"         , "-mvn" , "--maven"         , "0"        , "0"        , null                , "0", "Convertir le projet en projet maven."});
        lst.add(new String[] {"export"        , "-ex"  , "--export"        , "0"        , "1"        , MavenLite.EXPORT    , "0", "Exporter le projet dans un fichier jar. Le fichier jar sera exporté dans le dossier de sortie. Le nom du fichier jar est le nom du dossier de sortie."});
        lst.add(new String[] {"create"        , "-cr"  , "--create"        , "0"        , "1"        , MavenLite.CREATE    , "0", "Créer l'arborescence du projet ainsi qu'un fichier de config par défaut. Si le dossier de sortie n'est pas spécifié, le dossier par défaut est le dossier courant."});
        lst.add(new String[] {"source"        , "-s"   , "--source"        , "1"        , "1"        , MavenLite.SOURCE    , "0", "Dossier racine du projet à compiler."});
        lst.add(new String[] {"output"        , "-o"   , "--output"        , "1"        , "1"        , MavenLite.OUTPUT    , "0", "Dossier de sortie des fichiers compilés."});
        lst.add(new String[] {"classpath"     , "-cp"  , "--classpath"     , "1"        , "1"        , MavenLite.CLASSPATH , "0", "Liste des fichiers jar et du dossier de sortie des fichiers compilés (le même dossier que pour l'option -o) à ajouter au classpath lors de la compilation et du lancement. Les fichiers jar doivent être séparés par des ':'. La valeur par defaut du classpath est le dossier de sortie des fichiers compilés si l'option -o est utilisé, sinon le classpath sera le dossier courent."});
        lst.add(new String[] {"dependency"    , "-d"   , "--dependency"    , "1"        , "1"        , MavenLite.DEPENDENCY, "0", "Dossier contenant les fichiers jar utiliser par le programme. Tout les fichiers jar seront ajoutés au classpath lors de la compilation et du lancement."});
        lst.add(new String[] {"encoding"      , "-e"   , "--encoding"      , "1"        , "1"        , MavenLite.ENCODING  , "0", "Permet de changer l'encodage des fichiers java à compiler. L'encodage par defaut est 'UTF-8'. Utilisable uniquement avec l'option -c."});
        lst.add(new String[] {"main"          , "-m"   , "--main"          , "1"        , "1"        , null                , "0", "Classe principale à lancer. Utilisable uniquement avec l'option -l."});
        lst.add(new String[] {"argument"      , "-arg" , "--argument"      , "1"        , "1"        , null                , "0", "Argument unique"});
        lst.add(new String[] {"arguments"     , "-args", "--arguments"     , "unlimited", "unlimited", null                , "0", "Arguments à passer à la classe principale. Si vous voulez passé un argument qui commencer par '-' parce qu'aucune erreur ne sera déclancher, il faut échapper le caractère '-' avec deux '\\' comme ceci : '-args \\\\-argument_pour_le_main'."});
        lst.add(new String[] {"version"       , "-v"   , "--version"       , "0"        , "0"        , MavenLite.VERSION   , "0", "Afficher la version et quitter."});
        lst.add(new String[] {"help"          , "-h"   , "--help"          , "0"        , "0"        , null                , "0", "afficher l'aide et quitter."});
        /* Pour ajouter une option, il faut ajouter un tableau de String dans la liste lstOptions et ajouter l'option dans le switch de la méthode executeOption(). Il peut être nécessaire d'ajouter une méthode pour l'option. */

        return lst;
    }


    /**
     * Parse les arguments passés au programme.
     * Si une option est passée plusieurs fois, seule la dernière sera prise en compte.
     * @param args les arguments à parser 
     */
    public void parseOptions(String[] args)
    {
        for (int i = 0; i < args.length; i++)
        {
            boolean bFound = false;

            /* Si l'option est "-f" ou "--file. Ne fonctionne qu'une seul fois */
            if ((args[i].equals(this.lstOptions.get(0)[1]) || args[i].equals(this.lstOptions.get(0)[2])))
            {
                if (!this.lstOptions.get(0)[6].equals("0"))
                {
                    if ((i+1 < args.length) && !args[i+1].startsWith("-"))
                        i++;

                    continue;
                }

                if ((i+1 < args.length) && !args[i+1].startsWith("-"))
                    this.lstOptions.get(0)[5] = args[++i];

                this.lstOptions.get(0)[6] = String.valueOf(1);
                this.file(this.lstOptions.get(0));

                this.lstOptions.remove(0);
                bFound = true;
            }

            for (String[] opt : this.lstOptions.subList(1, this.lstOptions.size()))
            {
                if (bFound) break;

                if (args[i].equals(opt[1]) || args[i].equals(opt[2]))
                {
                    if (opt[3].equals("0"))
                    {
                        /* Parse les options qui n'ont sois pas sois un seul argument */
                        if (opt[4].equals("1") && (i+1 < args.length) && !args[i+1].startsWith("-"))
                            opt[5] = args[++i];
                    }
                    else if (opt[3].equals("1"))
                    {
                        /* Parse les options qui ont obligatoirement un seul argument */
                        if (i+1 < args.length && !args[i+1].startsWith("-"))
                            opt[5] = args[++i].replace("\\", "");
                        else
                        {
                            System.out.println("L'option '" + args[i] + "' nécessite un argument.");

                            if (i+1 < args.length && args[i+1].startsWith("-"))
                                System.out.println("Veuillez échapé le caractère '-' avec deux '\\' comme ceci : '-arg \\\\" + args[i+1] + "'");

                            System.exit(0);
                        }
                    }
                    else if (opt[3].equals("unlimited"))
                    {
                        /* Parse les options qui peuvent avoir un nombre illimité d'argument */
                        String sArgs;
                        if (opt[5] == null)
                            sArgs = "";
                        else
                            sArgs = opt[5];

                        while (i + 1 < args.length && !args[i + 1].startsWith("-"))
                            sArgs += "\"" + args[++i].replace("\\", "") + "\";";

                        opt[5] = sArgs;
                    }
                    else
                    {
                        /* Parse les options qui ont un nombre d'argument défini autre que 0 et 1 */
                        /* Pour l'instant ce code n'est pas utilisé */
                        String sArgs = "";
                        for (int j = 0; j < Integer.parseInt(opt[3]) && i+1 < args.length; j++)
                            sArgs += "\"" + args[++i].replace("\\", "") + "\";";

                        opt[5] = sArgs;

                        for (int j = i-Integer.parseInt(opt[3]); j < i+Integer.parseInt(opt[3]) && i+1 < args.length; j++)
                            args = this.removeTabElement(args, i);
                    }

                    opt[6] = String.valueOf(this.countArgs);
                    bFound = true;
                    break;
                }
            }

            if (!bFound)
            {
                System.out.println("Option '" + args[i] + "' inconnue !");
                System.exit(0);
            }
            else
            {
                this.countArgs++;
            }
        }
    }


    /**
     * Trie la liste des options dans l'ordre de passage à la ligne de commande et supprime les options non utilisées
     * @param lst la liste des options à trier
     * @return la liste des options triées
     */
    public List<String[]> sortList(List<String[]> lst)
    {
        List<String[]> lstSorted = new ArrayList<String[]>(lst.size());

        for (int i = 1; i < lst.size(); i++)
            for (int j = 0; j < lst.size(); j++)
                if (lst.get(j)[6].equals(String.valueOf(i)))
                    lstSorted.add(lst.get(j));

        for (int i = 0; i < lst.size(); i++)
            if (lst.get(i)[6].equals("0"))
                lstSorted.add(lst.get(i));

        return lstSorted;
    }


    /**
     * Trie la liste des options dans l'ordre de passage à la ligne de commande et supprime les options non utilisées
     * @param lst
     * @return
     */
    public List<String[]> removeUnusedOption(List<String[]> lst)
    {
        List<String[]> lstSorted = new ArrayList<String[]>();

        for (String[] opt : lst)
            if (!opt[6].equals("0"))
                lstSorted.add(opt);

        return lstSorted;
    }


    /**
     * Supprime un élément d'un tableau
     * @param tab le tableau à modifier
     * @param index l'index de l'élément à supprimer
     * @return le tableau modifié
     */
    public String[] removeTabElement(String[] tab, int index)
    {
        String[] newTab = new String[tab.length-1];

        for (int i = 0; i < tab.length; i++)
            if (i < index)
                newTab[i] = tab[i];
            else if (i > index)
                newTab[i-1] = tab[i];
        
        return newTab;
    }


    /**
     * Permet de détecter automatiquement le fichier main à lancer
     * @param f le dossier à parcourir
     * @return le nom de la classe principale
     */
    public String getMainClassName(File f)
    {
        String sMain = "";

        if (f.exists() && f.isDirectory())
        {
            File[] lstFiles = f.listFiles();
            for (File file : lstFiles)
            {
                if (file.isFile() && file.getName().endsWith(".java"))
                {
                    try
                    {
                        Scanner sc = new Scanner(file);
                        while (sc.hasNextLine())
                        {
                            String sLine = sc.nextLine();
                            if (sLine.contains("public static void main"))
                            {
                                sMain = file.getName().substring(0, file.getName().length() - 5);
                                break;
                            }
                        }
                        sc.close();
                    }
                    catch (Exception e)
                    {
                        System.out.println("Erreur lors de la lecture du fichier '" + file.getName() + "'.");
                        System.exit(0);
                    }
                }
                else if (file.isDirectory())
                {
                    sMain = this.getMainClassName(file);
                }
            }
        }
        else
        {
            System.out.println("Le dossier '" + "opt[5]" + "' n'existe pas ou n'est pas un dossier.");
            System.exit(0);
        }

        return sMain;
    }



    /*=======================================*/
    /* Méthodes pour l'exécution des options */
    /*=======================================*/
    /**
     * Execute les options passées en paramètre
     * @param map les options à exécuter avec leurs arguments associés
     */
    public void executeOption(List<String[]> lst)
    {
        lst = this.removeUnusedOption(lst);
        for (int i = 0; i < lst.size(); i++)
        {
            String[] opt = lst.get(i);
            if (opt[6].equals("0"))
                continue;

            switch (opt[0])
            {
                case "version":
                    lst.remove(i--);
                    this.version();
                    break;
                case "create":
                    lst.remove(i--);
                    this.create(opt);
                    break;
                case "source":
                    lst.remove(i--);
                    this.hmArgs.put(opt[0], opt[5]);
                    break;
                case "output":
                    lst.remove(i--);
                    this.hmArgs.put(opt[0], opt[5]);
                    break;
                case "classpath":
                    lst.remove(i--);
                    this.hmArgs.put(opt[0], opt[5]);
                    break;
                case "dependency":
                    lst.remove(i--);
                    this.dependency(opt);
                    break;
                case "encoding":
                    lst.remove(i--);
                    this.hmArgs.put(opt[0], opt[5]);
                    break;
                case "main":
                    lst.remove(i--);
                    this.hmArgs.put(opt[0], opt[5]);
                    break;
                case "arguments":
                    lst.remove(i--);
                    this.hmArgs.put(opt[0], opt[5]);
                    break;
                case "argument":
                    lst.remove(i--);
                    this.hmArgs.put(opt[0], opt[5]);
                    break;
                case "compilation":
                    lst.remove(i--);
                    this.compil = true;
                    break;
                case "launch":
                    lst.remove(i--);
                    this.launch = true;
                    break;
                case "compile-launch":
                    lst.remove(i--);
                    this.compil = true;
                    this.launch = true;
                    break;
                case "launch-compile":
                    lst.remove(i--);
                    this.compil = true;
                    this.launch = true;
                    break;
                case "help":
                    lst.remove(i--);
                    this.help(this.lstOptions);
                    break;
                case "export":
                    lst.remove(i--);
                    this.export(opt);
                    break;
                case "maven":
                    lst.remove(i--);
                    this.maven();
                    break;
                default:
                    System.out.println("Erreur lors de l'exécution de l'option '" + opt[0] + "'.");
                    break;
            }
        }

        if (this.compil)
            this.compilation();

        if (this.launch)
            this.launch();
    }


    /*=====================================*/
    /* Méthodes correspondants aux options */
    /*=====================================*/
    /**
     * Affiche la version du programme.
     */
    public void version()
    {
        System.out.println("\u001B[1mMaven Lite " + MavenLite.VERSION + "\u001B[0m");
        System.out.println("Maven Lite home : " + MavenLite.MAVEN_LITE_HOME);
        System.out.println("Java version : " + System.getProperty("java.version") + ", vendor : " + System.getProperty("java.vendor") + ", runtime : " + System.getProperty("java.runtime.name"));
        System.out.println("Default locale : " + System.getProperty("user.language") + "_" + System.getProperty("user.country") + ", platform encoding : " + System.getProperty("file.encoding"));
        System.out.println("OS name : \"" + System.getProperty("os.name") + "\", version : \"" + System.getProperty("os.version") + "\", architecture : \"" + System.getProperty("os.arch") + "\"");
    }

    /**
     * Créer l'arborescence du projet ainsi qu'un fichier de config par défaut.
     */
    public void create(String[] opt)
    {
        System.out.println("create : " + opt[5]);
    }

    /**
     * Permet de spécifier le dossier contenant les fichiers jar utiliser par le programme.
     */
    public void dependency(String[] opt)
    {
        String dependency = "";

        File f = new File(opt[5]);
        if (f.exists() && f.isDirectory())
        {
            File[] lstFiles = f.listFiles();
            for (File file : lstFiles)
                if (file.isFile() && file.getName().endsWith(".jar"))
                    dependency += file.getName() + ":";
        }
        else
        {
            System.out.println("Le dossier '" + opt[5] + "' n'existe pas ou n'est pas un dossier.");
            System.exit(0);
        }

        if (dependency.length() > 0)
            dependency = dependency.substring(0, dependency.length() - 1);
        
        this.hmArgs.put("dependency", dependency);
    }

    /**
     * Utilise le fichier de configuration.
     */
    public void file(String[] opt)
    {
        System.out.println("file : " + opt[5]);
        try
        {
            Scanner sc = new Scanner(new File(opt[5]));

            while (sc.hasNextLine())
                this.parseOptions(sc.nextLine().split(" "));

            sc.close();
        }
        catch (Exception e)
        {
            System.out.println("Erreur lors de la lecture du fichier '" + opt[5] + "'.");
            System.exit(0);
        }
    }

    /**
     * Affiche l'aide
     */
    public void help(List<String[]> lst)
    {
        int optLength = 30;
        int descLength = 50;
        String help = "Usage : mvnl [options] [argument]\n\n";
        help += "Options :\n";

        for (String[] s : lst)
        {
            help += String.format("%-" + optLength + "s", (String.format("%-5s, ", s[1]) + s[2]));

            /* Écriture de la description */
            int currentLineLength = 0;
            for (String word : s[7].split(" "))
            {
                if ((currentLineLength + word.length()) > descLength)
                {
                    help += "\n" + String.format("%-" + optLength + "s", "");
                    currentLineLength = 0;
                }
                
                help += word + " ";
                currentLineLength = currentLineLength + word.length();
            }

            help += "\n\n";
        }

        System.out.println(help);
    }

    /**
     * Exporte le projet dans un fichier jar.
     * Le fichier jar sera exporté dans le dossier de sortie.
     * Le nom du fichier jar est le nom du dossier de sortie.
     */
    public void export(String[] opt)
    {
        System.out.println("Exportation du projet : " + opt[5]);
    }

    /**
     * Converti le projet en projet maven.
     */
    public void maven()
    {
        System.out.println("Conversion du projet en projet maven");
    }


    /**
     * Compile le projet.
     */
    public void compilation()
    {
        System.out.println("Compilation du projet");
    }

    /**
     * Lance le projet.
     */
    public void launch()
    {
        System.out.println("Lancement du projet");
    }



    /*======*/
    /* Main */
    /*======*/
    public static void main(String[] args)
    {
        if (args.length < -1)
        {
            System.out.println("Help");
            System.exit(0);
        }

        new MavenLite(args);
    }



    /*======================*/
    /* Méthodes de débogage */
    /*======================*/
    /**
     * Pour le débogage uniquement
     * Affiche les options et leurs arguments associés
     */
    public void printLstTab(List<String[]> lst)
    {
        for (String[] opt : lst)
        {
            for (int i = 0; i < opt.length-1; i++)
                System.out.print(String.format("%-15s", opt[i]) + " | ");

            System.out.println();
        }
    }
}