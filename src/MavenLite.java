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
    /* Constantes et valeurs par défauts */
    private static final String AUTHOR = "Floris Robart";
    private static final String VERSION = "2.0.0";
    private static final String SOURCE = "src";
    private static final String CLASSPATH = "bin";
    private static final String ENCODING = "UTF-8";
    private static final String DATA = "data";
    private static final String FILE = "data/config-test.txt";
    private static final String EXPORT = "run.java";

    /* Variables */
    private List<String[]> lstOptions;
    private int countArgs = 1;



    /*==============*/
    /* Constructeur */
    /*==============*/
    /**
     * Coeur du programme
     */
    public MavenLite(String[] args)
    {
        this.lstOptions = this.initOptions();
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

        /*                    nom               court   long                nb argument  nb argument  valeur                utilisé  description     */
        /*                    0                 1       2                   3            4            5                     6        7               */
        lst.add(new String[] {"version"       , "-v"   , "--version"       , "0"        , "0"        , MavenLite.VERSION  , "0", "Afficher la version et quitter."});
        lst.add(new String[] {"create"        , "-cr"  , "--create"        , "0"        , "1"        , "."                , "0", "Créer l'arborescence du projet ainsi qu'un fichier de config par défaut. Si le dossier de sortie n'est pas spécifié, le dossier par défaut est le dossier courant."});
        lst.add(new String[] {"source"        , "-s"   , "--source"        , "1"        , "1"        , MavenLite.SOURCE   , "0", "Dossier racine du projet à compiler."});
        lst.add(new String[] {"output"        , "-o"   , "--output"        , "1"        , "1"        , null               , "0", "Dossier de sortie des fichiers compilés."});
        lst.add(new String[] {"classpath"     , "-cp"  , "--classpath"     , "1"        , "1"        , MavenLite.CLASSPATH, "0", "Liste des fichiers jar et du dossier de sortie des fichiers compilés (le même dossier que pour l'option -o) à ajouter au classpath lors de la compilation et du lancement. Les fichiers jar doivent être séparés par des ':'. La valeur par defaut du classpath est le dossier de sortie des fichiers compilés si l'option -o est utilisé, sinon le classpath sera le dossier courent."});
        lst.add(new String[] {"dependency"    , "-d"   , "--dependency"    , "1"        , "1"        , null               , "0", "Dossier contenant les fichiers jar utiliser par le programme. Tout les fichiers jar seront ajoutés au classpath lors de la compilation et du lancement."});
        lst.add(new String[] {"encoding"      , "-e"   , "--encoding"      , "1"        , "1"        , MavenLite.ENCODING , "0", "Permet de changer l'encodage des fichiers java à compiler. L'encodage par defaut est 'UTF-8'. Utilisable uniquement avec l'option -c."});
        lst.add(new String[] {"main"          , "-m"   , "--main"          , "1"        , "1"        , null               , "0", "Classe principale à lancer. Utilisable uniquement avec l'option -l."});
        lst.add(new String[] {"data"          , "-dt"  , "--data"          , "1"        , "1"        , MavenLite.DATA     , "0", "Dossier contenant les données du projet. Permet de copier le dossier de données dans le dossier de sortie. Utilisable uniquement avec l'option -c."});
        lst.add(new String[] {"arguments"     , "-args", "--arguments"     , "unlimited", "unlimited", null               , "0", "Arguments à passer à la classe principale. Si vous voulez passé un argument qui commencer par '-' parce qu'aucune erreur ne sera déclancher, il faut échapper le caractère '-' avec deux '\\' comme ceci : '-args \\\\-argument_pour_le_main'."});
        lst.add(new String[] {"argument"      , "-arg" , "--argument"      , "1"        , "1"        , null               , "0", "Argument unique"});
        lst.add(new String[] {"file"          , "-f"   , "--file"          , "0"        , "1"        , MavenLite.FILE     , "0", "Fichier de configuration. Permet de charger les options à partir d'un fichier de configuration, le séparateur sont l'espace et le retour à la ligne. Les options du fichier de configuration prédomine sur les options de la ligne de commande. L'option -f doit obligatoirement être la première option de la ligne de commande."});
        lst.add(new String[] {"compilation"   , "-c"   , "--compilation"   , "0"        , "0"        , null               , "0", "Compiler le projet."});
        lst.add(new String[] {"launch"        , "-l"   , "--launch"        , "0"        , "0"        , null               , "0", "Lancer le projet."});
        lst.add(new String[] {"compile-launch", "-cl"  , "--compile-launch", "0"        , "0"        , null               , "0", "Compiler et lancer le projet. (équivalent à -c -l)"});
        lst.add(new String[] {"launch-compile", "-lc"  , "--launch-compile", "0"        , "0"        , null               , "0", "Compiler et lancer le projet. (équivalent à -c -l)"});
        lst.add(new String[] {"help"          , "-h"   , "--help"          , "0"        , "0"        , null               , "0", "afficher l'aide et quitter."});
        lst.add(new String[] {"export"        , "-ex"  , "--export"        , "0"        , "1"        , MavenLite.EXPORT   , "0", "Exporter le projet dans un fichier jar. Le fichier jar sera exporté dans le dossier de sortie. Le nom du fichier jar est le nom du dossier de sortie."});
        lst.add(new String[] {"maven"         , "-mav" , "--maven"         , "0"        , "0"        , null               , "0", "Convertir le projet en projet maven."});

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
            for (String[] opt : this.lstOptions)
            {
                //System.out.println("'" + args[i] + "' == '" + opt[1] + "' || '" + args[i] + "' == '" + opt[2] + "' --> " + (args[i].equals(opt[1]) || args[i].equals(opt[2])));
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
                        String sArgs = "";
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


    public List<String[]> sortAndRemoveUnusedOption(List<String[]> lst)
    {
        List<String[]> lstSorted = new ArrayList<String[]>();

        for (int i = 1; i < lst.size(); i++)
            for (int j = 0; j < lst.size(); j++)
                if (lst.get(j)[6].equals(String.valueOf(i)))
                    lstSorted.add(lst.get(j));

        return lstSorted;
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
        for (String[] opt : lst)
        {
            if (opt[6].equals("0"))
                continue;

            switch (opt[0])
            {
                case "version":
                    this.version();
                    break;
                case "create":
                    this.create(opt[5]);
                    break;
                case "source":
                    this.source(opt[5]);
                    break;
                case "output":
                    this.output(opt[5]);
                    break;
                case "classpath":
                    this.classpath(opt[5]);
                    break;
                case "dependency":
                    this.dependency(opt[5]);
                    break;
                case "encoding":
                    this.encoding(opt[5]);
                    break;
                case "main":
                    this.main(opt[5]);
                    break;
                case "data":
                    this.data(opt[5]);
                    break;
                case "arguments":
                    this.arguments(opt[5]);
                    break;
                case "argument":
                    this.argument(opt[5]);
                    break;
                case "file":
                    this.file(opt[5]);
                    break;
                case "compilation":
                    this.compilation();
                    break;
                case "launch":
                    this.launch();
                    break;
                case "compile-launch":
                    this.compileLaunch();
                    break;
                case "launch-compile":
                    this.launchCompile();
                    break;
                case "help":
                    this.help();
                    break;
                case "export":
                    this.export(opt[5]);
                    break;
                case "maven":
                    this.maven();
                    break;
                default:
                    System.out.println("Option '" + opt[0] + "' inconnue.");
                    System.exit(0);
                    break;
            }
        }
    }


    /*=====================================*/
    /* Méthodes correspondants aux options */
    /*=====================================*/
    /**
     * Affiche la version du programme.
     */
    public void version()
    {
        System.out.println("Maven Lite version " + MavenLite.VERSION);
    }

    /**
     * Créer l'arborescence du projet ainsi qu'un fichier de config par défaut.
     */
    public void create(String pathOutput)
    {
        System.out.println("create : " + pathOutput);
    }

    /**
     * Permet de spécifier le dossier racine du projet à compiler et à lancer.
     */
    public void source(String pathSource)
    {
        System.out.println("source : " + pathSource);
    }

    /**
     * Permet de spécifier le dossier de sortie des fichiers compilés.
     */
    public void output(String pathOutput)
    {
        System.out.println("output : " + pathOutput);
    }

    /**
     * Permet de spécifier le classpath du projet lors de la compilation et du lancement.
     */
    public void classpath(String classpath)
    {
        System.out.println("classpath : " + classpath);
    }

    /**
     * Permet de spécifier le dossier contenant les fichiers jar utiliser par le programme.
     */
    public void dependency(String pathDependency)
    {
        System.out.println("dependency : " + pathDependency);
    }

    /**
     * Permet de changer l'encodage des fichiers java à compiler.
     */
    public void encoding(String encoding)
    {
        System.out.println("encoding : " + encoding);
    }

    /**
     * Permet de spécifier la classe principale à lancer.
     */
    public void main(String main)
    {
        System.out.println("main : " + main);
    }

    /**
     * Permet de spécifier le dossier contenant les données du projet.
     */
    public void data(String pathData)
    {
        System.out.println("data : " + pathData);
    }

    /**
     * Permet de passer plusieurs arguments à la fois à la classe principale.
     */
    public void arguments(String args)
    {
        System.out.println("arguments : " + args);
    }

    /**
     * Permet de passer un argument à la classe principale.
     */
    public void argument(String arg)
    {
        System.out.println("argument : " + arg);
    }

    /**
     * Utilise le fichier de configuration.
     */
    public void file(String pathFile)
    {
        System.out.println("file");
        try
        {
            Scanner sc = new Scanner(new File(pathFile));

            while (sc.hasNextLine())
                this.parseOptions(sc.nextLine().split(" "));

            sc.close();
        }
        catch (Exception e)
        {
            System.out.println("Erreur lors de la lecture du fichier '" + pathFile + "'.");
            System.exit(0);
        }

        this.lstOptions = this.sortAndRemoveUnusedOption(this.lstOptions);
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

    /**
     * Compile et lance le projet.
     */
    public void compileLaunch()
    {
        this.compilation();
        this.launch();
    }

    /**
     * Compile et lance le projet.
     */
    public void launchCompile()
    {
        this.compilation();
        this.launch();
    }

    /**
     * Affiche l'aide
     */
    public void help()
    {
        System.out.println("Aide");
    }

    /**
     * Exporte le projet dans un fichier jar.
     * Le fichier jar sera exporté dans le dossier de sortie.
     * Le nom du fichier jar est le nom du dossier de sortie.
     */
    public void export(String pathExport)
    {
        if (pathExport == null)
            System.out.println("Exportation du projet : null");
        else
            System.out.println("Exportation du projet : " + pathExport);
    }

    /**
     * Converti le projet en projet maven.
     */
    public void maven()
    {
        System.out.println("Conversion du projet en projet maven");
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
        for (String[] opt : this.lstOptions)
        {
            for (int i = 0; i < opt.length-1; i++)
                System.out.print(String.format("%-15s", opt[i]) + " | ");

            System.out.println();
        }
    }
}
