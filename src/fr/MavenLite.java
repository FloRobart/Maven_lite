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
 * @author Floris Robart
 * @version 2.0.0
 * @since 1.0.0
 * @see <a href="https://florobart.github.io/mavenlite.github.io/">Maven Lite documentation</a>
 */
public class MavenLite 
{
    /*============*/
    /* Constantes */
    /*============*/
    /* Valeurs par défauts */
    private static final String PROJECT_NAME    = System.getProperty("user.dir").substring(System.getProperty("user.dir").lastIndexOf(File.separator)+1);
    private static final String AUTHOR          = "Floris Robart";
    private static final String VERSION         = "2.0.0";
    private static final String ENCODING        = "UTF-8";
    private static final String EXPORT          = "run.java";
    private static final String TARGET          = "target";
    private static final String CREATE          = "NewProject";
    private static final String FILE            = "LPOM";
    private static final String SOURCE          = "src" + File.separator + "main" + File.separator + "java";
    private static final String LIBRARIES       = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "lib";

    private static final String COMPILE_LIST_NAME = "compile.list";

    /* Couleurs et style */
    private static final String DEFAULT         = "\u001B[0m";
    private static final String GREEN_BOLD      = "\u001B[1;32m";
    private static final String FULL_GREEN_BOLD = "\u001B[38;5;46;1m";
    private static final String BOLD            = "\u001B[1m";
    private static final String RED_BOLD        = "\u001B[1;31m";
    private static final String BLUE_BOLD       = "\u001B[1;34m";
    private static final String YELLOW_BOLD     = "\u001B[1;33m";

    /* Messages */
    private static final String SUCCESS         = "[" + MavenLite.FULL_GREEN_BOLD + "SUCCES" + MavenLite.DEFAULT + "] ";
    private static final String INFO            = "[" + MavenLite.BLUE_BOLD + "INFO" + MavenLite.DEFAULT + "] ";
    private static final String WARNING         = "[" + MavenLite.YELLOW_BOLD + "AVERTISSEMENT" + MavenLite.DEFAULT + "] ";
    private static final String ERROR           = "[" + MavenLite.RED_BOLD + "ERREUR" + MavenLite.DEFAULT + "] ";


    /*===========*/
    /* Variables */
    /*===========*/
    /* Parse et exécution des options */
    private List<String[]> lstOptions;
    private Map<String, String> hmArgs;
    private int countArgs = 1;



    /*==============*/
    /* Constructeur */
    /*==============*/
    /**
     * Constructeur qui permet de lancer le programme.
     * @param args les arguments passés au programme
     */
    private MavenLite(String[] args)
    {
        this.lstOptions = this.initOptions();
        this.hmArgs = new HashMap<String, String>();

        /* Ajout des options obligatoire dans la hashmap */
        for (String[] opt : this.lstOptions)
            if (opt[6].equals("1"))
                this.hmArgs.put(opt[0], opt[5]);

        this.parseOptions(args);
        this.executeOption(this.lstOptions);
    }

    /**
     * Constructeur qui permet uniquement de charger la liste des options.
     * Elle est utilisé uniquement pour afficher une petite aide stylisé et pour désinstaller Maven Lite.
     */
    private MavenLite()
    {
        this.lstOptions = this.initOptions();
    }



    /*==========================================*/
    /* Méthodes pour la préparation des options */
    /*==========================================*/
    /**
     * Initialise la liste des options.
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
     * @return la liste des options utilisables
     */
    private List<String[]> initOptions()
    {
        List<String[]> lst = new ArrayList<String[]>();

        /*                             nom                  court    long                       nb argument  nb argument  valeur                  utilisé    description       */
        /*                             0                    1        2                          3            4            5                       6          7                 */
        /* 0  */ lst.add(new String[] {"file"             , "-f"   , "--file"                 , "0"        , "1"        , MavenLite.FILE        , "0", "Permet de charger les options à partir d'un fichier de configuration. En savoir plus : "});
        /* 1  */ lst.add(new String[] {"create"           , "-cr"  , "--create"               , "0"        , "1"        , MavenLite.CREATE      , "0", "Créer l'arborescence du projet ainsi qu'un fichier de config par défaut."});
        /* 2  */ lst.add(new String[] {"mvc"              , "-mvc" , "--model-view-controller", "0"        , "0"        , null                  , "0", "Permet de spécifier à l'option '" + lst.get(1)[2] + "' de créer l'arborescence d'un projet MVC."});
        /* 3  */ lst.add(new String[] {"compilation"      , "-c"   , "--compilation"          , "0"        , "0"        , null                  , "0", "Compile le projet."});
        /* 4  */ lst.add(new String[] {"launch"           , "-l"   , "--launch"               , "0"        , "0"        , null                  , "0", "Lance le projet."});
        /* 5  */ lst.add(new String[] {"compile-launch"   , "-cl"  , "--compile-launch"       , "0"        , "0"        , null                  , "0", "Compile et lance le projet. (équivalent à -c -l)"});
        /* 6  */ lst.add(new String[] {"launch-compile"   , "-lc"  , "--launch-compile"       , "0"        , "0"        , null                  , "0", "Compile et lance le projet. (équivalent à -c -l)"});
        /* 7  */ lst.add(new String[] {"quiet"            , "-q"   , "--quiet"                , "0"        , "0"        , null                  , "0", "Permet de supprimer l'affichage de java dans le terminal lors de l'exécution du projet."});
        /* 8  */ lst.add(new String[] {"verbose"          , "-v"   , "--verbose"              , "0"        , "0"        , null                  , "0", "Permet d'afficher les commandes exécutées."});
        /* 9  */ lst.add(new String[] {"exclude"          , "-ex"  , "--exclude"              , "unlimited", "unlimited", null                  , "0", "Permet d'exclure des fichiers java et des dossiers de la compilation. Si vous voulez passé un argument qui commencer par '-' échapper le caractère '-' avec deux '\\' comme ceci : '-ex \\\\-fichier'."});
        /* 10 */ lst.add(new String[] {"jar"              , "-j"   , "--jar"                  , "1"        , "1"        , null                  , "0", "Permet de créer un fichier jar exécutable permettant de lancer le projet sans avoir installer MavenLite."});
        /*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*/
        /* Tous se qui est au dessus de cette ligne ne doit pas être déplacer, leur ordre est important */

        /* 11 */ lst.add(new String[] {"source"           , "-s"   , "--source"               , "1"        , "1"        , MavenLite.SOURCE      , "1", "Dossier contenant les fichiers java à compiler."});
        /* 12 */ lst.add(new String[] {"target"           , "-t"   , "--target"               , "1"        , "1"        , MavenLite.TARGET      , "1", "Dossier de sortie des fichiers compilés. Ce dossier sera créer si il n'existe pas et sera automatiquement ajouter au classpath lors de la compilation et du lancement."});
        /* 13 */ lst.add(new String[] {"classpath"        , "-cp"  , "--classpath"            , "1"        , "1"        , null                  , "0", "Permet de spécifier le classpath à utiliser lors de la compilation et du lancement. Si vous voulez ajouter plusieurs éléments au classpath, il faut les séparer par des ':'."});
        /* 14 */ lst.add(new String[] {"libraries"        , "-lib" , "--libraries"            , "0"        , "1"        , MavenLite.LIBRARIES   , "0", "Dossier contenant les fichiers jar utiliser par le programme. Tout les fichiers jar seront ajoutés au classpath lors de la compilation et du lancement."});
        /* 15 */ lst.add(new String[] {"arguments"        , "-args", "--arguments"            , "unlimited", "unlimited", null                  , "0", "Tous les arguments à passer à la classe principale. Si vous voulez passé un argument qui commencer par '-' échapper le caractère '-' avec deux '\\' comme ceci : '-args \\\\-argument_pour_le_main'."});
        /* 16 */ lst.add(new String[] {"main"             , "-m"   , "--main"                 , "1"        , "1"        , null                  , "0", "Classe principale à lancer. Si vous voulez lancer une classe qui se trouve dans un package, il faut spécifier le package avec le nom de la classe comme ceci : 'package.nom.MainClass'"});
        /* 17 */ lst.add(new String[] {"encoding"         , "-e"   , "--encoding"             , "1"        , "1"        , MavenLite.ENCODING    , "1", "Permet de changer l'encodage des fichiers java à compiler."});
        /* 18 */ lst.add(new String[] {"export"           , "-exp" , "--export"               , "0"        , "1"        , MavenLite.EXPORT      , "0", "Permet de créer un fichier jar exécutable permettant de lancer le projet sans avoir installer MavenLite."});
        /* 19 */ lst.add(new String[] {"maven"            , "-mvn" , "--maven"                , "0"        , "0"        , null                  , "0", "Convertie le projet en projet maven en créant un fichier pom.xml et en déplaçant les fichiers si nécessaire."});
        /* 20 */ lst.add(new String[] {"version"          , "-V"   , "--version"              , "0"        , "0"        , MavenLite.VERSION     , "0", "Affiche la version."});
        /* 21 */ lst.add(new String[] {"help"             , "-h"   , "--help"                 , "0"        , "0"        , null                  , "0", "Affiche l'aide et quitte."});
        /* 22 */ lst.add(new String[] {"clear"            , "-cle" , "--clear"                , "0"        , "0"        , null                  , "0", "Permet de supprimer les fichiers dans le dossier de sortie des fichiers compilés."});
        /* Pour ajouter une option, il faut ajouter un tableau de String dans la liste ci-dessus. Si l'option doit executé du code ajouter la au switch dans la méthode executeOption() et créer une méthode pour l'exécution de l'option. */

        return lst;
    }

    /**
     * Parse les arguments passés au programme.
     * Si une option est passée plusieurs fois, seule la dernière sera prise en compte. sauf pour les options qui peuvent avoir un nombre illimité d'argument.
     * @param args les arguments à parser
     */
    private void parseOptions(String[] args)
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
                    this.hmArgs.put(this.lstOptions.get(0)[0], args[++i]);
                else
                    this.hmArgs.put(this.lstOptions.get(0)[0], this.lstOptions.get(0)[5]);

                this.lstOptions.get(0)[6] = String.valueOf(1);
                this.file(this.hmArgs.get(this.lstOptions.get(0)[0]));

                bFound = true;
            }

            for (String[] opt : this.lstOptions)
            {
                if (bFound) break;

                if (args[i].equals(opt[1]) || args[i].equals(opt[2]))
                {
                    /* Parse les options qui ont sois 0 sois 1 seul argument */
                    if (opt[3].equals("0"))
                    {
                        if (opt[4].equals("1") && (i+1 < args.length) && !args[i+1].startsWith("-"))
                            this.hmArgs.put(opt[0], args[++i]);
                        else if (opt[4].equals("0") && opt[5] == null)
                            this.hmArgs.put(opt[0], "true");
                    }
                    /* Parse les options qui ont obligatoirement 1 seul argument */
                    else if (opt[3].equals("1"))
                    {
                        if (i+1 < args.length && !args[i+1].startsWith("-"))
                            this.hmArgs.put(opt[0], args[++i].replace("\\", ""));
                        else
                        {
                            System.out.println(MavenLite.WARNING + "L'option '" + MavenLite.YELLOW_BOLD + args[i] + MavenLite.DEFAULT + "' nécessite un argument.");

                            if (i+1 < args.length && args[i+1].startsWith("-"))
                                System.out.println(MavenLite.WARNING + "Si l'argument commence par le caractère '-' veuillez échapé le caractère '-' avec deux '\\' comme ceci : '" + MavenLite.YELLOW_BOLD + args[i] + " \\\\" + args[i+1] + MavenLite.DEFAULT + "'");

                            System.exit(0);
                        }
                    }
                    /* Parse les options qui peuvent avoir un nombre illimité d'argument */
                    else if (opt[3].equals("unlimited"))
                    {
                        StringBuilder sArgs = new StringBuilder();
                        sArgs.append(this.hmArgs.get(opt[0]) == null ? "" : this.hmArgs.get(opt[0]));

                        while (i + 1 < args.length && !args[i + 1].startsWith("-"))
                            sArgs.append(args[++i]).append("\";\"");

                        this.hmArgs.put(opt[0], sArgs.toString());
                    }
                    /* Parse les options qui ont un nombre d'argument défini autre que 0 et 1 */
                    /* Pour l'instant ce code n'est pas utilisé */
                    else
                    {
                        StringBuilder sArgs = new StringBuilder();
                        sArgs.append(this.hmArgs.get(opt[0]) == null ? "" : this.hmArgs.get(opt[0]));

                        for (int j = 0; j < Integer.parseInt(opt[3]) && i+1 < args.length && !args[i + 1].startsWith("-"); j++)
                            sArgs.append(args[++i]).append("\";\"");

                        this.hmArgs.put(opt[0], sArgs.toString());
                    }

                    opt[6] = String.valueOf(this.countArgs);
                    bFound = true;
                    break;
                }
            }

            if (!bFound)
            {
                System.out.println(MavenLite.ERROR + "Option '" + MavenLite.RED_BOLD + args[i] + MavenLite.DEFAULT + "' inconnue.");
                System.exit(1);
            }
            else
            {
                this.countArgs++;
            }
        }
    }

    /**
     * Supprime les options inutilisées de la liste passée en paramètre (qui doit être une copie de la liste des options)
     * @param lst la liste dans laquelle supprimer les options inutilisées
     * @return la liste sans les options inutilisées
     */
    private List<String[]> removeUnusedOption(List<String[]> lst)
    {
        List<String[]> lstSorted = new ArrayList<String[]>();

        for (String[] opt : lst)
            if (!opt[6].equals("0"))
                lstSorted.add(opt);

        return lstSorted;
    }



    /*===================*/
    /* Méthodes générale */
    /*===================*/
    /**
     * Permet de détecter automatiquement le Main à lancer
     * @param directory le dossier à parcourir (source du projet java)
     * @return le nom avec le package de la classe principale à lancer. Format : "package.name.MainClass"
     */
    private String getMainClassName(File directory)
    {
        String mainClass = "";

        if (directory.exists() && directory.isDirectory())
        {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".java"))
                    {
                        try
                        {
                            Scanner scanner = new Scanner(file);
                            while (scanner.hasNextLine())
                            {
                                String line = scanner.nextLine();
                                if (line.replaceAll(" ", "").contains("public static void main(String[]".replaceAll(" ", "")))
                                {
                                    /* Récupération du package */
                                    mainClass = this.getPackageName(file) + file.getName().replace(".java", "");
                                    break;
                                }
                            }
                            scanner.close();
                        }
                        catch (IOException e)
                        {
                            System.out.println(MavenLite.ERROR + "La lecture du fichier '" + MavenLite.RED_BOLD + file.getName() + MavenLite.DEFAULT + "' a échoué.");
                            System.exit(1);
                        }
                    }
                    else if (file.isDirectory())
                    {
                        String subdirectoryMainClass = getMainClassName(file);
                        if (!subdirectoryMainClass.isEmpty())
                        {
                            mainClass = subdirectoryMainClass;
                            break;
                        }
                    }
                }
            }
        }
        else
        {
            if (directory.exists())
                System.out.println(MavenLite.ERROR + "Le fichier '" + MavenLite.RED_BOLD + directory.getName() + MavenLite.DEFAULT + "' n'est pas un dossier.");
            else
                System.out.println(MavenLite.ERROR + "Le dossier '" + MavenLite.RED_BOLD + directory.getName() + MavenLite.DEFAULT + "' n'existe pas.");

            System.exit(1);
        }

        return mainClass;
    }

    /**
     * Permet de récupérer le nom du package d'un fichier java
     * @param javaFile le fichier java à analyser
     * @return le nom du package au format "package.name.". Si le fichier ne contient pas de package, une chaine de caractère vide est retournée.
     */
    private String getPackageName(File javaFile)
    {
        String packageName = "";

        try
        {
            Scanner scanner = new Scanner(javaFile);
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine().replaceAll("^[\\u0009\\u0020]*", "");
                if (line.contains("package"))
                {
                    packageName = line.split(" ")[1].replace(";", ".");
                    break;
                }
                else if (!line.replaceAll("^[\\u0009\\u0020]*", "").equals(""))
                {
                    break;
                }
            }
            scanner.close();
        }
        catch (IOException e)
        {
            System.out.println(MavenLite.ERROR + "La lecture du fichier '" + MavenLite.RED_BOLD + javaFile.getName() + MavenLite.DEFAULT + "' a échoué.");
            System.exit(1);
        }

        return packageName;
    }

    /**
     * Génère la liste des fichiers à compiler
     * @param source le dossier à parcourir (source du projet java)
     * @return la liste des fichiers à compiler séparé par des retours à la ligne (\n)
     */
    private String genererCompileList(File source)
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
            if (source.exists())
                System.out.println(MavenLite.ERROR + "Le fichier '" + MavenLite.RED_BOLD + source + MavenLite.DEFAULT + "' n'est pas un dossier.");
            else
                System.out.println(MavenLite.ERROR + "Le dossier '" + MavenLite.RED_BOLD + source + MavenLite.DEFAULT + "' n'existe pas.");

            System.exit(1);
        }

        String exclude = this.hmArgs.get(this.lstOptions.get(9)[0]);
        if (exclude != null)
            for (String ex : exclude.replace("\\", "").split("\";\""))
                for (String line : sCompileList.toString().split("\n"))
                    if (line.contains(ex))
                        sCompileList = new StringBuilder(sCompileList.toString().replace(line + "\n", ""));

        return sCompileList.toString();
    }

    /**
     * Permet d'éxecuter une commande dans le terminal (bash / cmd)
     * @param commande la commande à exécuter
     * @return le code de retour de la commande. 0 si la commande s'est bien exécutée, 1 sinon.
     */
    private int executCommande(String commande)
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
            BufferedReader reader  = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null)
                if (this.hmArgs.get(this.lstOptions.get(7)[0]) == null)
                    System.out.println(line);

            return process.waitFor();
        }
        catch (Exception e)
        {
            System.out.println(MavenLite.ERROR + "L'exécution de la commande '" + MavenLite.RED_BOLD + shell + " " + shellOption + " " + commande + MavenLite.DEFAULT + "' a échoué.");
            System.exit(1);
        }

        return 1;
    }

    /**
     * Permet de supprimer le fichier contenant la liste des fichiers à compiler
     */
    private void removeCompilList()
    {
        File f = new File(MavenLite.COMPILE_LIST_NAME);
        if (f.exists())
            f.delete();
    }



    /*=======================================*/
    /* Méthodes pour l'exécution des options */
    /*=======================================*/
    /**
     * Execute les options passées en paramètre si elles ont besoin d'être exécutées.
     * @param lst la liste des options à exécuter
     */
    private void executeOption(List<String[]> lst)
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
                    this.version("");
                    break;
                case "libraries":
                    this.hmArgs.put(opt[0], this.libraries(opt[5]));
                    break;
                case "help":
                    System.out.println(this.help(this.lstOptions));
                    System.exit(0);
                    break;
                case "export":
                    this.export();
                    break;
                case "maven":
                    // System.exit(this.maven());
                    this.maven();
                    break;
                case "clear":
                    this.clear(new File(this.hmArgs.get("target")));
                    break;
                case "uninstall":
                    this.uninstall();
                    break;
            }

            lst.remove(i--);
        }

        /* Création de l'arborescence */
        if (!this.lstOptions.get(1)[6].equals("0")) {
            String projectName = this.hmArgs.get(this.lstOptions.get(1)[0]) == null ? MavenLite.CREATE : this.hmArgs.get(this.lstOptions.get(1)[0]);
            this.create(projectName);
            System.exit(0);
        }

        /* Compilation */
        if (!this.lstOptions.get(3)[6].equals("0") || !this.lstOptions.get(5)[6].equals("0") || !this.lstOptions.get(6)[6].equals("0"))
            this.compilation();
        
        /* Création du fichier jar */
        if (!this.lstOptions.get(10)[6].equals("0"))
            this.jar();

        /* Lancement */
        if (!this.lstOptions.get(4)[6].equals("0") || !this.lstOptions.get(5)[6].equals("0") || !this.lstOptions.get(6)[6].equals("0"))
            this.launch();
    }



    /*=====================================*/
    /* Méthodes correspondants aux options */
    /*=====================================*/
    /**
     * Affiche la version, le nom de l'auteur et des informations sur l'environnement d'exécution.
     * @param phraseAuteur la phrase à afficher après la version
     */
    private void version(String phraseAuteur)
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
     * @return l'aide sous la forme d'une chaine de caractère formaté
     */
    private String help(List<String[]> lst)
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
                help += String.format("\n%-" + optLength + "s", "") + "Nombre de d'argument  : " + MavenLite.BOLD + s[3] + MavenLite.DEFAULT + ".";
            else
                help += String.format("\n%-" + optLength + "s", "") + "Nombre de d'arguments : de " + MavenLite.BOLD + s[3] + MavenLite.DEFAULT + " à " + MavenLite.BOLD + s[4] + MavenLite.DEFAULT + ".";
            
            if (s[5] != null)
                help += String.format("\n%-" + optLength + "s", "") + "Valeur par défaut     : " + MavenLite.BOLD + s[5] + MavenLite.DEFAULT + ".";

            help += "\n\n";
        }

        return help;
    }    

    /**
     * Permet de spécifier le dossier contenant les fichiers jar utiliser par le programme.
     * Tout les fichiers jar seront ajoutés au classpath lors de la compilation et du lancement.
     * @param fileName le nom du dossier contenant les fichiers jar
     * @return la liste des fichiers jar depuis la racine du projet à ajouter au classpath sous la forme d'une chaine de caractère séparé par des ':'
     */
    private String libraries(String fileName)
    {
        StringBuilder libraries = new StringBuilder();

        File f = new File(fileName);
        if (f.exists() && f.isDirectory())
        {
            File[] lstFiles = f.listFiles();
            for (File file : lstFiles)
            {
                if (file.isFile())
                {
                    if (file.getName().endsWith(".jar"))
                        libraries.append(file.getPath()).append(":");
                }
                else if (file.isDirectory())
                {
                    libraries.append(this.libraries(file.getPath()));
                }
            }
        }
        else
        {
            if (f.exists())
                System.out.println(MavenLite.ERROR + "Le fichier '" + MavenLite.RED_BOLD + fileName + MavenLite.DEFAULT + "' n'est pas un dossier.");
            else
                System.out.println(MavenLite.ERROR + "Le dossier '" + MavenLite.RED_BOLD + fileName + MavenLite.DEFAULT + "' n'existe pas.");

            System.exit(1);
        }

        return libraries.toString() == "" ? null : libraries.toString().substring(0, libraries.length()-1);
    }

    /**
     * Permet de supprimer les fichiers compilés dans le dossier de sortie des fichiers compilés.
     * @param f le dossier à parcourir
     */
    private void clear(File f)
    {
        if (f.exists() && f.isDirectory())
        {
            File[] lstFiles = f.listFiles();
            for (File file : lstFiles)
                file.delete();
        }
        else
        {
            if (f.exists())
                System.out.println(MavenLite.ERROR + "Le fichier '" + MavenLite.RED_BOLD + f.getName() + MavenLite.DEFAULT + "' n'est pas un dossier.");
            else
                System.out.println(MavenLite.ERROR + "Le dossier '" + MavenLite.RED_BOLD + f.getName() + MavenLite.DEFAULT + "' n'existe pas.");

            System.exit(1);
        }
    }

    /**
     * Permet de charger les options à partir d'un fichier de configuration.
     * Le séparateur sont l'espace et le retour à la ligne.
     * Les options de la ligne de commande prédomine sur les options sur celle du fichier de configuration.
     * @param fileName le nom du fichier de configuration
     */
    private void file(String fileName)
    {
        try
        {
            StringBuilder sFile = new StringBuilder();
            Scanner sc = new Scanner(new File(fileName));

            while (sc.hasNextLine())
            {
                String line = sc.nextLine().replaceAll("^[\\u0009\\u0020]*", ""); // Supprime les espaces et les tabulations au début de la ligne
                if (!line.startsWith("#") && !line.equals(""))
                    sFile.append(line).append(" ");
            }

            sc.close();

            this.parseOptions(sFile.toString().split(" "));
        }
        catch (Exception e)
        {
            System.out.println(MavenLite.ERROR + "La lecture du fichier '" + MavenLite.RED_BOLD + fileName + MavenLite.DEFAULT + "' a échoué.");
            System.exit(1);
        }
    }

    /**
     * Créer l'arborescence du projet ainsi qu'un fichier de config par défaut.
     * @param projectName le nom du projet
     */
    private void create(String projectName)
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
        String main = "HelloWorld";

        /* Création de la liste des dossiers du projet */
        lstFoldersProject.add(projectName);
        lstFoldersProject.add(projectName + File.separator + MavenLite.SOURCE);
        lstFoldersProject.add(projectName + File.separator + MavenLite.TARGET);
        lstFoldersProject.add(projectName + File.separator + MavenLite.LIBRARIES);

        if (this.hmArgs.get(this.lstOptions.get(2)[0]) == null)
        {
            /* Création de la liste des fichiers du projet */
            lstFilesProject.add(projectName + File.separator + MavenLite.SOURCE + File.separator + main + ".java");
        }
        else
        {
            /* Création de la liste des dossiers du projet */
            lstFoldersProject.add(projectName + File.separator + "controller");
            lstFoldersProject.add(projectName + File.separator + "model");
            lstFoldersProject.add(projectName + File.separator + "view");

            /* Création de la liste des fichiers du projet */
            main = "Controller";
            lstFilesProject.add(projectName + File.separator + MavenLite.SOURCE + File.separator + "controller" + File.separator + main + ".java");
        }

        /* Création de la liste des fichiers du projet */
        lstFilesProject.add(projectName + File.separator + MavenLite.FILE);


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
                    System.out.println(MavenLite.ERROR + "La création du fichier '" + MavenLite.RED_BOLD + f.getName() + MavenLite.DEFAULT + "' a échoué.");
                    System.exit(1);
                }
            }
        }

        /* Écriture dans le fichier App.java */
        try
        {
            StringBuilder sApp = new StringBuilder();
            if (this.hmArgs.get(this.lstOptions.get(2)[0]) != null)
                sApp.append("package controller;\n\n");

            sApp.append("/**\n");
            sApp.append(" * Hello world!\n");
            sApp.append(" * @version 1.0.0\n");
            sApp.append(" * @autor " + System.getProperty("user.name") + "\n");
            sApp.append(" */\n");
            sApp.append("public class " + main + "\n");
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
            System.out.println(MavenLite.ERROR + "L'écriture dans le fichier '" + MavenLite.RED_BOLD + lstFilesProject.get(0) + MavenLite.DEFAULT + "' à échoué.");
            System.exit(1);
        }


        /* Écriture dans le fichier de configuration */
        try
        {
            String sConfig = "";
            sConfig += "# Source du projet\n";
            sConfig += "--source " + MavenLite.SOURCE + "\n";
            sConfig += "\n";
            sConfig += "# Dossier de sortie des fichiers compilés\n";
            sConfig += "--target " + MavenLite.TARGET + "\n";
            sConfig += "\n";
            sConfig += "# Liste des libraries à ajouter au classpath\n";
            sConfig += "--libraries " + MavenLite.LIBRARIES + "\n";
            sConfig += "\n";
            sConfig += "# Compiler et lancer le projet grâce à la commande 'mvnl -cl'\n";

            FileWriter fw = new FileWriter(new File(lstFilesProject.get(1)));
            fw.write(sConfig);
            fw.close();
        }
        catch (Exception e)
        {
            System.out.println(MavenLite.ERROR + "L'écriture dans le fichier '" + MavenLite.RED_BOLD + lstFilesProject.get(1) + MavenLite.DEFAULT + "' à échoué.");
            System.exit(1);
        }

        System.out.println("\n" + MavenLite.SUCCESS + "Le projet '" + MavenLite.GREEN_BOLD + projectName + MavenLite.DEFAULT + "' a été créé avec succès.\n");
        System.out.println(MavenLite.INFO    + "Pour " + MavenLite.BLUE_BOLD + "compiler" + MavenLite.DEFAULT + " le projet, exécuter la commande : '" + MavenLite.BLUE_BOLD + "mvnl -c" + MavenLite.DEFAULT + "'.");
        System.out.println(MavenLite.INFO    + "Pour " + MavenLite.BLUE_BOLD + "lancer  " + MavenLite.DEFAULT + " le projet, exécuter la commande : '" + MavenLite.BLUE_BOLD + "mvnl -l" + MavenLite.DEFAULT + "'.");
        System.out.println(MavenLite.INFO    + "Pour " + MavenLite.BLUE_BOLD + "compiler et lancer" + MavenLite.DEFAULT + " le projet à partir des données du fichier de configuration, exécuter la commande : '" + MavenLite.BLUE_BOLD + "mvnl -f -cl" + MavenLite.DEFAULT + "'.\n");
    }

    /**
     * Compile le projet.
     */
    private void compilation()
    {
        /* Variables */
        StringBuilder command = new StringBuilder();

        try
        {
            FileWriter fw = new FileWriter(new File(MavenLite.COMPILE_LIST_NAME));
            fw.write(this.genererCompileList(new File(this.hmArgs.get("source"))));
            fw.close();
        }
        catch (Exception e)
        {
            System.out.println(MavenLite.ERROR + "L'écriture dans le fichier '" + MavenLite.RED_BOLD + MavenLite.COMPILE_LIST_NAME + MavenLite.DEFAULT + "' à échoué.");
            System.exit(1);
        }

        /* Compilation */
        command.append("javac");
        command.append(" -d ").append(this.hmArgs.get("target"));

        if (this.hmArgs.get("libraries") != null)
            command.append(" -cp ").append(this.hmArgs.get("libraries"));
        if (this.hmArgs.get("classpath") != null)
            command.append(":").append(this.hmArgs.get("classpath"));

        command.append(" -encoding ");
        command.append(this.hmArgs.get("encoding"));
        command.append(" @");
        command.append(MavenLite.COMPILE_LIST_NAME);

        if (this.hmArgs.get(this.lstOptions.get(8)[0]) != null)
            System.out.println(MavenLite.INFO + MavenLite.BLUE_BOLD + command.toString() + MavenLite.DEFAULT);

        System.out.println(MavenLite.INFO + "Compilation du projet '" + MavenLite.BLUE_BOLD + MavenLite.PROJECT_NAME + MavenLite.DEFAULT + "'...");
        if (this.executCommande(command.toString()) != 0)
        {
            System.out.println(MavenLite.ERROR + "La compilation du projet '" + MavenLite.RED_BOLD + MavenLite.PROJECT_NAME + MavenLite.DEFAULT + "' à échoué.");
            this.removeCompilList();
            System.exit(1);
        }
        
        System.out.println(MavenLite.SUCCESS + "Compilation du projet '" + MavenLite.GREEN_BOLD + MavenLite.PROJECT_NAME + MavenLite.DEFAULT + "' terminé avec succès.");
        this.removeCompilList();
    }

    /**
     * Lance le projet.
     */
    private void launch()
    {
        /* Variables */
        StringBuilder command = new StringBuilder();

        /* Lancement */
        command.append("java -cp ");
        command.append(this.hmArgs.get("target"));

        if (this.hmArgs.get("libraries") != null)
            command.append(":").append(this.hmArgs.get("libraries"));
        if (this.hmArgs.get("classpath") != null)
            command.append(":").append(this.hmArgs.get("classpath"));

        if (this.hmArgs.get("main") == null)
            this.hmArgs.put("main", this.getMainClassName(new File(this.hmArgs.get("source"))));

        command.append(" ").append(this.hmArgs.get("main"));

        if (this.hmArgs.get("arguments") != null)
            for (String s : this.hmArgs.get("arguments").split("\";\""))
                command.append(" \"").append(s).append("\"");

        if (this.hmArgs.get(this.lstOptions.get(8)[0]) != null)
            System.out.println(MavenLite.INFO + MavenLite.BLUE_BOLD + command.toString() + MavenLite.DEFAULT);

        System.out.println(MavenLite.INFO + "Lancement du projet '" + MavenLite.BLUE_BOLD + MavenLite.PROJECT_NAME + MavenLite.DEFAULT + "'...");
        if (this.executCommande(command.toString()) != 0)
        {
            System.out.println(MavenLite.ERROR + "Le lancement du projet '" + MavenLite.RED_BOLD + MavenLite.PROJECT_NAME + MavenLite.DEFAULT + "' à échoué.");
            this.removeCompilList();
            System.exit(1);
        }
        
        System.out.println(MavenLite.SUCCESS + "Lancement du projet '" + MavenLite.GREEN_BOLD + MavenLite.PROJECT_NAME + MavenLite.DEFAULT + "' terminé avec succès.");
    }

    /**
     * Créer un fichier jar à partir des fichiers compilés.
     */
    private void jar()
    {
        /* Variables */
        StringBuilder command = new StringBuilder();
        String main = this.hmArgs.get("main") == null ? this.getMainClassName(new File(this.hmArgs.get("source"))) : this.hmArgs.get("main");

        /* Création du manifest */
        try
        {
            FileWriter fw = new FileWriter(new File(this.hmArgs.get("target") + File.separator + "manifest.txt"));
            fw.write("Manifest-Version: 1.0\n");
            fw.write("Author: " + System.getProperty("user.name") + "\n");
            fw.write("Main-Class: " + main + "\n");
            fw.close();
        }
        catch (Exception e)
        {
            System.out.println(MavenLite.ERROR + "L'écriture dans le fichier '" + MavenLite.RED_BOLD + this.hmArgs.get("target") + File.separator + "manifest.txt" + MavenLite.DEFAULT + "' à échoué.");
            System.exit(1);
        }

        /* Création du fichier jar */
        command.append("jar cvfe ");
        command.append(this.hmArgs.get("target")).append(File.separator).append(MavenLite.PROJECT_NAME).append(".jar ");
        command.append(main).append(" -C ").append(this.hmArgs.get("target")).append(" .");

        if (this.hmArgs.get(this.lstOptions.get(8)[0]) != null)
            System.out.println(MavenLite.INFO + MavenLite.BLUE_BOLD + command.toString() + MavenLite.DEFAULT);

        System.out.println(MavenLite.INFO + "Création du fichier jar '" + MavenLite.BLUE_BOLD + MavenLite.PROJECT_NAME + ".jar" + MavenLite.DEFAULT + "'...");
        if (this.executCommande(command.toString()) != 0)
        {
            System.out.println(MavenLite.ERROR + "La création du fichier jar '" + MavenLite.RED_BOLD + MavenLite.PROJECT_NAME + ".jar" + MavenLite.DEFAULT + "' à échoué.");
            this.removeCompilList();
            System.exit(1);
        }
        
        System.out.println(MavenLite.SUCCESS + "Création du fichier jar '" + MavenLite.GREEN_BOLD + MavenLite.PROJECT_NAME + ".jar" + MavenLite.DEFAULT + "' terminé avec succès.");
    }

    /**
     * Exporte le projet dans un fichier jar.
     * Le fichier jar sera exporté dans le dossier de sortie.
     */
    private void export()
    {
        System.out.println("Export arrive bientôt...");
        // TODO : Exporter le projet dans un fichier .class
    }

    /**
     * Converti le projet en projet maven.
     * @return 0 si la conversion à réussi, 1 sinon
     */
    private int maven()
    {
        System.out.println("Maven arrive bientôt...");
        // TODO : Convertir le projet en projet maven

        return 0;
    }

    /**
     * Permet de désinstaller les pages de manuel de Maven Lite.
     * @return 0 si la désinstallation à réussi, 1 si une erreur est survenue, 2 si l'utilisateur à annulé la désinstallation
     */
    private int uninstall()
    {
        // Variables
        String[] manPageLangages = {"fr", "en"};

        System.out.print(MavenLite.WARNING + MavenLite.RED_BOLD + "ATTENTION" + MavenLite.DEFAULT + ", vous êtes sur le point de désinstaller Maven Lite. Êtes-vous sûr de vouloir continuer ? (y/n) : ");
        Scanner sc = new Scanner(System.in);
        String reponse = sc.nextLine().toLowerCase();
        sc.close();

        if (!reponse.matches("^[yY]([eE][sS])?$"))
        {
            System.out.println(MavenLite.INFO + "Désinstallation annulée.");
            return 2;
        }

        System.out.println(MavenLite.INFO + "Désinstallation de Maven Lite...");

        // Suppression des pages de manuel
        System.out.println(MavenLite.INFO + "Suppression des pages de manuel dans les différentes langues...");
        for (String langage : manPageLangages)
        {
            String manPageFile = "/usr/local/man/" + langage + "/man1/mvnl.1.gz";
            if (!deleteFile(manPageFile))
            {
                System.out.println(MavenLite.ERROR + "Erreur lors de la suppression du fichier '" + MavenLite.RED_BOLD + manPageFile + MavenLite.DEFAULT + "'.");
                return 1;
            }
            else
                System.out.println(MavenLite.INFO + "Le fichier '" + MavenLite.BLUE_BOLD + manPageFile + MavenLite.DEFAULT + "'' a été supprimé avec succès.");
        }
        System.out.println(MavenLite.SUCCESS + "Pages de manuel supprimées avec succès.");

        return 0;
    }

    /**
     * Supprime un fichier
     * @param filePath le chemin du fichier à supprimer
     * @return true si le fichier a été supprimé, false sinon
     */
    private static boolean deleteFile(String filePath)
    {
        File file = new File(filePath);
        return file.delete();
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
        else if (args.length == 1 && args[0].equals("uninstall_from_mvnl-uninstall"))
        {
            System.exit(new MavenLite().uninstall());
        }

        new MavenLite(args);
    }
}
