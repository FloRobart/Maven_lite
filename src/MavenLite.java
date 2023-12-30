//!/usr/bin/env java

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
    /*===================================*/
    /* Constantes et valeurs par défauts */
    /*===================================*/
    private static final String AUTHOR          = "Floris Robart";
    private static final String VERSION         = "2.0.0";
    private static final String ENCODING        = "UTF-8";
    private static final String EXPORT          = "run.java";
    private static final String OUTPUT          = "bin"; // TODO : L'output doit être modifier quand les options -cr ou -s sont utilisées
    private static final String CREATE          = "data/create";
    private static final String FILE            = "config";
    private static final String SOURCE          = "src" + File.separator + "main" + File.separator + "java";
    private static final String LIBRARIES       = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "lib";
    private static final String MAIN            = "App";

    private static final String COMPILE_LIST_NAME = "compile.list";

    /* Couleurs et style */
    private static final String DEFAULT         = "\u001B[0m";
    private static final String GREEN_BOLD      = "\u001B[1;32m";
    private static final String GREEN           = "\u001B[32m";
    private static final String BOLD            = "\u001B[1m";
    private static final String RED             = "\u001B[31m";
    private static final String RED_BOLD        = "\u001B[1;31m";
    private static final String BLUE            = "\u001B[34m";
    private static final String BLUE_BOLD       = "\u001B[1;34m";
    private static final String YELLOW          = "\u001B[33m";
    private static final String YELLOW_BOLD     = "\u001B[1;33m";
    private static final String ORANGE          = "\u001B[38;5;208m";
    private static final String ORANGE_BOLD     = "\u001B[38;5;208;1m";
    private static final String FULL_GREEN_BOLD = "\u001B[38;5;46;1m";

    /* Messages */
    private static final String SUCCESS         = "[" + MavenLite.FULL_GREEN_BOLD + "SUCCESS" + MavenLite.DEFAULT + "] ";
    private static final String INFO            = "[" + MavenLite.BLUE_BOLD + "INFO" + MavenLite.DEFAULT + "] ";
    private static final String WARNING         = "[" + MavenLite.YELLOW_BOLD + "WARNING" + MavenLite.DEFAULT + "] ";
    private static final String ERROR           = "[" + MavenLite.RED_BOLD + "ERROR" + MavenLite.DEFAULT + "] ";


    /*===========*/
    /* Variables */
    /*===========*/
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
     * @param args les arguments passés au programme
     */
    public MavenLite(String[] args)
    {
        this.lstOptions = this.initOptions();
        this.hmArgs = new HashMap<String, String>();
        for (String[] tab : this.lstOptions)
            if (tab[5] != null)
                this.hmArgs.put(tab[0], tab[5]);

        this.parseOptions(args);

        this.lstOptions = this.sortList(this.lstOptions);
        this.executeOption(this.lstOptions);
    }

    /**
     * Constructeur qui permet uniquement de charger la liste des options.
     */
    public MavenLite()
    {
        this.lstOptions = this.initOptions();
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
     *   <li>5 : valeur par défaut de l'option</li>
     *   <li>6 : numéro d'ordonnancement de l'option</li>
     *   <li>7 : description de l'option</li>
     * </ul>
     * @return la liste des options possible
     */
    public List<String[]> initOptions()
    {
        List<String[]> lst = new ArrayList<String[]>();

        /*                    nom               court    long                       nb argument  nb argument  valeur                utilisé    description       */
        /*                    0                 1        2                          3            4            5                     6          7                 */
        lst.add(new String[] {"file"          , "-f"   , "--file"                 , "0"        , "1"        , MavenLite.FILE      , "0", "Fichier de configuration. Permet de charger les options à partir d'un fichier de configuration, le séparateur sont l'espace et le retour à la ligne. Les options du fichier de configuration prédomine sur les options de la ligne de commande. L'option -f doit obligatoirement être la première option de la ligne de commande."});
        /*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*/
        /* Tous se qui est au dessus de cette ligne ne doit pas être déplacer, leur ordre est important */
        lst.add(new String[] {"create"        , "-cr"  , "--create"               , "0"        , "1"        , MavenLite.CREATE    , "0", "Créer l'arborescence du projet ainsi qu'un fichier de config par défaut. Si le dossier de sortie n'est pas spécifié, le dossier par défaut est le dossier courant."});
        lst.add(new String[] {"compilation"   , "-c"   , "--compilation"          , "0"        , "0"        , null                , "0", "Compiler le projet."});
        lst.add(new String[] {"launch"        , "-l"   , "--launch"               , "0"        , "0"        , null                , "0", "Lancer le projet."});
        lst.add(new String[] {"compile-launch", "-cl"  , "--compile-launch"       , "0"        , "0"        , null                , "0", "Compiler et lancer le projet. (équivalent à -c -l)"});
        lst.add(new String[] {"launch-compile", "-lc"  , "--launch-compile"       , "0"        , "0"        , null                , "0", "Compiler et lancer le projet. (équivalent à -c -l)"});
        /*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*/
        /* Tous se qui est au dessus de cette ligne ne doit pas être déplacer en dehors des commentaires qui les entours mais peux être interchanger entre eux */
        lst.add(new String[] {"maven"         , "-mvn" , "--maven"                , "0"        , "0"        , null                , "0", "Convertir le projet en projet maven."});
        lst.add(new String[] {"export"        , "-ex"  , "--export"               , "0"        , "1"        , MavenLite.EXPORT    , "0", "Exporter le projet dans un fichier jar. Le fichier jar sera exporté dans le dossier de sortie. Le nom du fichier jar est le nom du dossier de sortie."});
        lst.add(new String[] {"source"        , "-s"   , "--source"               , "1"        , "1"        , MavenLite.SOURCE    , "0", "Dossier racine du projet à compiler."});
        lst.add(new String[] {"output"        , "-o"   , "--output"               , "1"        , "1"        , MavenLite.OUTPUT    , "0", "Dossier de sortie des fichiers compilés."});
        lst.add(new String[] {"classpath"     , "-cp"  , "--classpath"            , "1"        , "1"        , null                , "0", "Liste des fichiers jar et du dossier de sortie des fichiers compilés (le même dossier que pour l'option -o) à ajouter au classpath lors de la compilation et du lancement. Les fichiers jar doivent être séparés par des ':'. La valeur par defaut du classpath est le dossier de sortie des fichiers compilés si l'option -o est utilisé, sinon le classpath sera le dossier courent."});
        lst.add(new String[] {"libraries"     , "-lib" , "--libraries"            , "0"        , "1"        , MavenLite.LIBRARIES , "0", "Dossier contenant les fichiers jar utiliser par le programme. Tout les fichiers jar seront ajoutés au classpath lors de la compilation et du lancement."});
        lst.add(new String[] {"encoding"      , "-e"   , "--encoding"             , "1"        , "1"        , MavenLite.ENCODING  , "0", "Permet de changer l'encodage des fichiers java à compiler. L'encodage par defaut est 'UTF-8'. Utilisable uniquement avec l'option -c."});
        lst.add(new String[] {"main"          , "-m"   , "--main"                 , "1"        , "1"        , MavenLite.MAIN      , "0", "Classe principale à lancer. Utilisable uniquement avec l'option -l."});
        lst.add(new String[] {"argument"      , "-arg" , "--argument"             , "1"        , "1"        , null                , "0", "Argument unique"});
        lst.add(new String[] {"arguments"     , "-args", "--arguments"            , "unlimited", "unlimited", null                , "0", "Arguments à passer à la classe principale. Si vous voulez passé un argument qui commencer par '-' parce qu'aucune erreur ne sera déclancher, il faut échapper le caractère '-' avec deux '\\' comme ceci : '-args \\\\-argument_pour_le_main'."});
        lst.add(new String[] {"version"       , "-v"   , "--version"              , "0"        , "0"        , MavenLite.VERSION   , "0", "Afficher la version et quitter."});
        lst.add(new String[] {"help"          , "-h"   , "--help"                 , "0"        , "0"        , null                , "0", "afficher l'aide et quitter."});
        lst.add(new String[] {"mvc"           , "-mvc" , "--model-view-controller", "0"        , "0"        , null                , "0", "Permet de créer l'arborescence d'un projet MVC."});
        /* Pour ajouter une option, il faut ajouter un tableau de String dans la liste lstOptions et ajouter l'option dans le switch de la méthode executeOption(). Il peut être nécessaire d'ajouter une méthode pour l'option. */
        lst.add(new String[] {"test"          , "-t"   , "--test"                 , "0"        , "0"        , null                , "0", "Permet de tester des méhodes."});

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
                        String sArgs = this.hmArgs.get(opt[0]) == null ? opt[5] : "";

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



    /*===================*/
    /* Méthodes générale */
    /*===================*/
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
                            if (sLine.replaceAll(" ", "").contains("public static void main(String[]".replaceAll(" ", "")))
                            {
                                /* Récupération du package */
                                Scanner sc2 = new Scanner(file);
                                while (sc2.hasNextLine())
                                {
                                    String sLine2 = sc2.nextLine().replaceAll("^[\\u0009\\u0020]*", "");
                                    if (sLine2.contains("package"))
                                    {
                                        sMain = sLine2.split(" ")[1].replace(";", "") + "." + file.getName().replace(".java", "");
                                        break;
                                    }
                                }
                                sc2.close();
                                break;
                            }
                        }
                        sc.close();
                    }
                    catch (Exception e)
                    {
                        System.out.println(MavenLite.ERROR + "La lecture du fichier '" + file.getName() + "' a échoué.");
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
            System.out.println(MavenLite.ERROR + "Le fichier '" + f.getName() + "' n'existe pas ou n'est pas un dossier.");
            System.exit(0);
        }

        return sMain;
    }


    /**
     * Génère la liste des fichiers à compiler
     * @param source le dossier à parcourir
     * @return la liste des fichiers à compiler
     */
    public String genererCompileList(File source)
    {
        StringBuilder sCompileList = new StringBuilder();
    
        if (source.exists() && source.isDirectory())
        {
            File[] lstFiles = source.listFiles();
            if (lstFiles != null)
            {
                for (File file : lstFiles)
                {
                    if (file.isFile() && file.getName().endsWith(".java"))
                    {
                        sCompileList.append(file.getPath()).append("\n");
                    }
                    else if (file.isDirectory())
                    {
                        sCompileList.append(this.genererCompileList(file));
                    }
                }
            }
        }
        else
        {
            if (source.isFile())
                System.out.println(MavenLite.ERROR + "Le fichier '" + source + "' n'est pas un dossier !");
            else
                System.out.println(MavenLite.ERROR + "Le dossier '" + source + "' n'existe pas !");

            System.exit(0);
        }

        return sCompileList.toString();
    }


    /**
     * Permet d'éxecuter une commande
     * @param commande la commande à exécuter
     */
    public void executCommande(String commande)
    {
        ProcessBuilder processBuilder = new ProcessBuilder();
        String shell;
        String shellOption;

        if (System.getProperty("os.name").toLowerCase().startsWith("windows"))
        {
            shell = "cmd.exe";
            shellOption = "/c";
        }
        else
        {
            shell = "bash";
            shellOption = "-c";
        }

        /* Préparation de la commande */
        processBuilder.command(shell, shellOption, commande);

        try
        {
            /* Exécution de la commande */
            Process        process = processBuilder.start();
            StringBuilder  output  = new StringBuilder();
            BufferedReader reader  = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null)
                output.append(line + "\n");

            int exitCode = process.waitFor();
            if (exitCode == 0)
            {
                System.out.println(output);
                System.out.println(MavenLite.SUCCESS + "La commande '" + commande + "' à réussi.");
            }
            else
            {
                System.out.println(MavenLite.ERROR + "La commande '" + commande + "' à échoué.");
                System.exit(1);
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }


    /**
     * Permet de supprimer le fichier contenant la liste des fichiers à compiler
     */
    public void removeCompilList()
    {
        File f = new File(MavenLite.COMPILE_LIST_NAME);
        if (f.exists())
            f.delete();
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
        String[] create = null;
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
                    this.version("");
                    break;
                case "create":
                    lst.remove(i--);
                    this.hmArgs.put(opt[0], opt[5]);
                    create = opt;
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
                case "libraries":
                    lst.remove(i--);
                    this.hmArgs.put(opt[0], this.libraries(opt));
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
                case "mvc":
                    lst.remove(i--);
                    this.hmArgs.put(opt[0], "true");
                    break;
                case "test":
                    lst.remove(i--);
                    this.test();
                    break;
                default:
                    System.out.println(MavenLite.ERROR + "l'exécution de l'option '" + opt[0] + "' à échoué.");
                    break;
            }
        }

        if (create != null)
        {
            if (this.hmArgs.get("source") != null)
                this.hmArgs.put("source", create[5] + File.separator + this.hmArgs.get("source"));
            
            if (this.hmArgs.get("output") != null)
                this.hmArgs.put("output", create[5] + File.separator + this.hmArgs.get("output"));

            this.create(create);
        }
        else
            this.hmArgs.put("create", null);

        if (this.compil)
            this.compilation();

        if (this.launch)
            this.launch();
    }


    /*=====================================*/
    /* Méthodes correspondants aux options */
    /*=====================================*/
    /**
     * Affiche la version, le nom de l'auteur et des informations sur l'environnement d'exécution.
     * @param phraseAuteur la phrase à afficher après la version
     */
    public void version(String phraseAuteur)
    {
        String version = "";

        version += MavenLite.BOLD + "Maven Lite " + MavenLite.VERSION + MavenLite.DEFAULT + " " + MavenLite.GREEN_BOLD + phraseAuteur + MavenLite.DEFAULT + "\n";
        version += "Maven Lite home : " + new File(".").getAbsolutePath().substring(0, new File(".").getAbsolutePath().length()-2) + "\n";
        version += "Java version : " + System.getProperty("java.version") + ", vendor : " + System.getProperty("java.vendor") + ", runtime : " + System.getProperty("java.runtime.name") + "\n";
        version += "Default locale : " + System.getProperty("user.language") + "_" + System.getProperty("user.country") + ", platform encoding : " + System.getProperty("file.encoding") + "\n";
        version += "OS name : \"" + System.getProperty("os.name") + "\", version : \"" + System.getProperty("os.version") + "\", architecture : \"" + System.getProperty("os.arch") + "\"";
    
        System.out.println(version);
    }

    /**
     * Permet d'afficher l'aide à partir de la liste des options.
     * @param lst la liste des options
     */
    public void help(List<String[]> lst)
    {
        int optLength = 30;
        int descLength = 50;
        String help = "\nUsage : mvnl [options] [argument]\n\n";
        help += "Options :\n";

        for (String[] s : lst)
        {
            help += String.format("  %-" + (optLength-2) + "s", (String.format("%-5s, ", s[1]) + s[2]));

            /* Écriture de la description */
            String sDesc = s[7];
            int currentLineLength = 0;
            for (String word : sDesc.split(" "))
            {
                if ((currentLineLength + word.length()) > descLength)
                {
                    help += "\n" + String.format("%-" + optLength + "s", "");
                    currentLineLength = 0;
                }

                help += word + " ";
                currentLineLength = currentLineLength + word.length();
            }

            if (s[3].equals(s[4]))
                help += String.format("\n%-" + optLength + "s", "") + "Nombre de paramètre : " + MavenLite.BOLD + s[3] + MavenLite.DEFAULT + ".";
            else
                help += String.format("\n%-" + optLength + "s", "") + "Nombre de paramètre : de " + MavenLite.BOLD + s[3] + MavenLite.DEFAULT + " à " + MavenLite.BOLD + s[4] + MavenLite.DEFAULT + ".";
            
            if (s[5] != null)
                help += String.format("\n%-" + optLength + "s", "") + "Valeur par défaut   : " + MavenLite.BOLD + s[5] + MavenLite.DEFAULT + ".";

            help += "\n\n";
        }

        System.out.println(help);
    }    

    /**
     * Permet de spécifier le dossier contenant les fichiers jar utiliser par le programme.
     * Tout les fichiers jar seront ajoutés au classpath lors de la compilation et du lancement.
     * @param opt Ligne de la liste des options correspondant à l'option -lib (--libraries)
     * @return la liste des fichiers jar depuis la racine du projet à ajouter au classpath sous la forme d'une chaine de caractère séparé par des ':'
     */
    public String libraries(String[] opt)
    {
        String libraries = "";

        File f = new File(opt[5]);
        if (f.exists() && f.isDirectory())
        {
            File[] lstFiles = f.listFiles();
            for (File file : lstFiles)
            {
                if (file.isFile())
                {
                    if (file.getName().endsWith(".jar"))
                        libraries += file.getPath() + ":";
                }
                else if (file.isDirectory())
                {
                    libraries += this.libraries(new String[] {"", "", "", "0", "1", file.getPath() + "/"});
                }
            }
        }
        else
        {
            System.out.println("Le dossier '" + opt[5] + "' n'existe pas ou n'est pas un dossier.");
            System.exit(0);
        }

        return libraries;
    }

    /**
     * Permet de charger les options à partir d'un fichier de configuration.
     * Le séparateur sont l'espace et le retour à la ligne.
     * Les options de la ligne de commande prédomine sur les options sur celle du fichier de configuration.
     * @param opt Ligne de liste des options correspondant à l'option -f
     */
    public void file(String[] opt)
    {
        try
        {
            Scanner sc = new Scanner(new File(opt[5]));

            while (sc.hasNextLine())
            {
                String line = sc.nextLine().replaceAll("^[\\u0009\\u0020]*", ""); // Supprime les espaces et les tabulations au début de la ligne
                if (!line.startsWith("#") && !line.equals(""))
                    this.parseOptions(line.split(" "));
            }

            sc.close();
        }
        catch (Exception e)
        {
            System.out.println("Erreur lors de la lecture du fichier '" + opt[5] + "'.");
            System.exit(0);
        }
    }

    /**
     * Créer l'arborescence du projet ainsi qu'un fichier de config par défaut.
     * @param opt Ligne de la liste des options correspondant à l'option -cr
     */
    public void create(String[] opt)
    {
        /*
        ProjectName
        ├── config
        └── src
            ├── main
            │   └── java
            │       └── App.java
            └── resources
                └── lib
         */

        List<String> lstFoldersProject = new ArrayList<String>();
        List<String> lstFilesProject = new ArrayList<String>();

        /* Création de la liste des dossiers du projet */
        lstFoldersProject.add(opt[5]);
        lstFoldersProject.add(opt[5] + File.separator + MavenLite.SOURCE);
        lstFoldersProject.add(opt[5] + File.separator + MavenLite.OUTPUT);
        lstFoldersProject.add(opt[5] + File.separator + MavenLite.LIBRARIES);

        if (this.hmArgs.get("mvc") == null)
        {
            /* Création de la liste des fichiers du projet */
            lstFilesProject.add(opt[5] + File.separator + MavenLite.SOURCE + File.separator + MavenLite.MAIN + ".java");
        }
        else
        {
            /* Création de la liste des dossiers du projet */
            lstFoldersProject.add(lstFoldersProject.get(1) + File.separator + "controller");
            lstFoldersProject.add(lstFoldersProject.get(1) + File.separator + "model");
            lstFoldersProject.add(lstFoldersProject.get(1) + File.separator + "view");

            /* Création de la liste des fichiers du projet */
            lstFilesProject.add(opt[5] + File.separator + MavenLite.SOURCE + File.separator + "controller" + File.separator + MavenLite.MAIN + ".java");
        }

        /* Création de la liste des fichiers du projet */
        lstFilesProject.add(opt[5] + File.separator + MavenLite.FILE);
    

        /* Création de l'arborescence */
        for (String folder : lstFoldersProject)
        {
            File f = new File(folder);
            if (!f.exists())
                f.mkdirs();
        }

        /* Création des fichiers */
        for (String file : lstFilesProject)
        {
            File f = new File(file);
            if (!f.exists())
            {
                try
                {
                    f.createNewFile();
                }
                catch (IOException e)
                {
                    System.out.println(MavenLite.ERROR + "La création du fichier '" + f.getName() + "' a échoué.");
                    System.exit(0);
                }
            }
        }

        /* Écriture dans le fichier App.java */
        try
        {
            StringBuilder sApp = new StringBuilder();
            if (this.hmArgs.get("mvc") != null)
                sApp.append("package controller;\n\n");

            sApp.append("/**\n");
            sApp.append(" * Hello world!\n");
            sApp.append(" * @version 1.0.0\n");
            sApp.append(" * @autor " + System.getProperty("user.name") + "\n");
            sApp.append(" */\n");
            sApp.append("public class App\n");
            sApp.append("{\n");
            sApp.append("    public static void main(String[] args)\n");
            sApp.append("    {\n");
            sApp.append("        System.out.println(\"Hello World!\");\n");
            sApp.append("    }\n");
            sApp.append("}");

            FileWriter fw = new FileWriter(new File(lstFilesProject.get(0)));
            fw.write(sApp.toString());
            fw.close();
        }
        catch (Exception e)
        {
            System.out.println(MavenLite.ERROR + "L'écriture dans le fichier '" + lstFilesProject.get(0) + "' à échoué.");
            System.exit(0);
        }


        /* Écriture dans le fichier de configuration */
        try
        {
            String sConfig = "";
            sConfig += "# Source du projet\n";
            sConfig += "--source " + MavenLite.SOURCE + "\n";
            sConfig += "\n";
            sConfig += "# Dossier de sortie des fichiers compilés\n";
            sConfig += "--output " + MavenLite.OUTPUT + "\n";
            sConfig += "\n";
            sConfig += "# Liste des libraries à ajouter au classpath\n";
            sConfig += "--libraries " + MavenLite.LIBRARIES + "\n";
            sConfig += "\n";
            sConfig += "# Compiler et lancer le projet grâce à la commande 'mvnl -f -cl'\n";

            FileWriter fw = new FileWriter(new File(lstFilesProject.get(1)));
            fw.write(sConfig);
            fw.close();
        }
        catch (Exception e)
        {
            System.out.println(MavenLite.ERROR + "L'écriture dans le fichier '" + lstFilesProject.get(1) + "' à échoué.");
            System.exit(0);
        }
    }

    /**
     * Exporte le projet dans un fichier jar.
     * Le fichier jar sera exporté dans le dossier de sortie.
     * Le nom du fichier jar est le nom du dossier de sortie.
     * @param opt Ligne de la liste des options correspondant à l'option -ex
     */
    public void export(String[] opt)
    {
        System.out.println("Exportation du projet : " + opt[5]);
        // TODO : Exporter le projet dans un fichier .class
    }

    /**
     * Converti le projet en projet maven.
     */
    public void maven()
    {
        System.out.println("Conversion du projet en projet maven");
        // TODO : Convertir le projet en projet maven
    }


    /**
     * Compile le projet.
     */
    public void compilation()
    {
        /* Variables */
        StringBuilder command = new StringBuilder();
        String rootProject;

        if (this.hmArgs.get("create") != null)
        {
            rootProject = this.hmArgs.get("create") + File.separator + this.hmArgs.get("source");
            this.hmArgs.put("output", this.hmArgs.get("create") + File.separator + this.hmArgs.get("output"));
        }
        else
            rootProject = new File(this.hmArgs.get("source")).getAbsolutePath();

        try
        {
            FileWriter fw = new FileWriter(new File(MavenLite.COMPILE_LIST_NAME));
            fw.write(this.genererCompileList(new File(rootProject)));
            fw.close();
        }
        catch (Exception e)
        {
            System.out.println(MavenLite.ERROR + "L'écriture dans le fichier '" + MavenLite.COMPILE_LIST_NAME + "' à échoué.");
            System.exit(0);
        }

        this.hmArgs.put("main", this.getMainClassName(new File(rootProject)));
        
        /* Compilation */
        System.out.println("Compilation du projet : " + rootProject);
        System.out.println("output : " + this.hmArgs.get("output"));
        System.out.println("Classpath : " + this.hmArgs.get("classpath"));
        System.out.println("Encoding : " + this.hmArgs.get("encoding"));


        command.append("javac -d ");
        command.append(this.hmArgs.get("output"));
        command.append(" -cp ");
        command.append(this.hmArgs.get("output"));
        command.append(":");
        command.append(this.hmArgs.get("classpath"));
        command.append(" -encoding ");
        command.append(this.hmArgs.get("encoding"));
        command.append(" @");
        command.append(MavenLite.COMPILE_LIST_NAME);
        this.executCommande(command.toString());
    }

    /**
     * Lance le projet.
     */
    public void launch()
    {
        System.out.println("Lancement du projet");
    }

    /**
     * Permets d'afficher le nom du programme en ascii art, la version, l'auteur et une phrase d'aide.
     */
    private void defaultAffichage()
    {
        String helpStyle = "\n";

        /* Écriture de 'MVNL' en ascii art */
        helpStyle += "        __  ___                          __    _ __      \n";
        helpStyle += "       /  |/  /___ __   _____  ____     / /   (_) /____   \n";
        helpStyle += "      / /|_/ / __ `/ | / / _ \\/ __ \\   / /   / / __/ _ \\  \n";
        helpStyle += "     / /  / / /_/ /| |/ /  __/ / / /  / /___/ / /_/  __/  \n";
        helpStyle += "    /_/  /_/\\__,_/ |___/\\___/_/ /_/  /_____/_/\\__/\\___/   \n\n";



        System.out.println(helpStyle);
        this.version("Par " + MavenLite.AUTHOR);
        System.out.println("\n    " + MavenLite.BOLD + "Utiliser la commande '" + MavenLite.DEFAULT + MavenLite.GREEN_BOLD + "mvnl -h" + MavenLite.DEFAULT + MavenLite.BOLD + "' ou '" + MavenLite.DEFAULT + MavenLite.GREEN_BOLD + "mvnl --help" + MavenLite.DEFAULT + MavenLite.BOLD + "' pour afficher l'aide." + MavenLite.DEFAULT + "\n");
    }



    /*======*/
    /* Main */
    /*======*/
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            new MavenLite().defaultAffichage();
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
    private void printLstTab(List<String[]> lst)
    {
        for (String[] opt : lst)
        {
            for (int i = 0; i < opt.length-1; i++)
                System.out.print(String.format("%-15s", opt[i]) + " | ");

            System.out.println();
        }
    }

    /**
     * Pour le débogage uniquement
     */
    private void test()
    {
        System.out.println("Test");
    }
}

