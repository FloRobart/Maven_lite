import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MavenLite is a program that allows you to compile and run a Java project.
 * @author Floris Robart
 * @translator Chat GPT-3.5
 * @version 2.0.0
 * @since 1.0.0
 * @see <a href="https://florobart.github.io/mavenlite.github.io/">Maven Lite documentation</a>
 */
public class MavenLite 
{
    /*============*/
    /* Constantes */
    /*============*/
    private static final String OS = System.getProperty("os.name").toLowerCase();
    /* Valeurs par défauts */
    private static final String PROJECT_NAME    = System.getProperty("user.dir").substring(System.getProperty("user.dir").lastIndexOf("/")+1);
    private static final String AUTHOR          = "Floris Robart";
    private static final String VERSION         = "2.0.0";
    private static final String ENCODING        = "UTF-8";
    private static final String EXPORT          = "run.java";
    private static final String TARGET          = "target";
    private static final String CREATE          = "NewProject";
    private static final String FILE            = "LPOM.conf";
    private static final String SOURCE          = "src" + "/" + "main"      + "/" + "java";
    private static final String RESOURCES       = "src" + "/" + "resources";
    private static final String LIBRARIES       = MavenLite.RESOURCES + "/" + "lib";
    private static final String TEST_UNITAIRE   = "src" + "/" + "test"      + "/" + "java";

    /* Couleurs et style */
    private static final String DEFAULT         = "\u001B[0m";
    private static final String GREEN_BOLD      = "\u001B[1;32m";
    private static final String FULL_GREEN_BOLD = "\u001B[38;5;46;1m";
    private static final String BOLD            = "\u001B[1m";
    private static final String RED_BOLD        = "\u001B[1;31m";
    private static final String BLUE_BOLD       = "\u001B[1;34m";
    private static final String YELLOW_BOLD     = "\u001B[1;33m";

    /* Messages */
    private static final String SUCCESS         = "[" + MavenLite.FULL_GREEN_BOLD + "SUCCESS" + MavenLite.DEFAULT + "] ";
    private static final String INFO            = "[" + MavenLite.BLUE_BOLD + "INFO" + MavenLite.DEFAULT + "] ";
    private static final String WARNING         = "[" + MavenLite.YELLOW_BOLD + "WARNING" + MavenLite.DEFAULT + "] ";
    private static final String ERROR           = "[" + MavenLite.RED_BOLD + "ERROR" + MavenLite.DEFAULT + "] ";

    /* Séparation des arguments */
    private static final String ARG_SEPARATOR     = "@;#ARGUMENT#;@";
    private static       String SPACE_REPLACEMENT;
    private static final String CP_SEPARATOR      = MavenLite.OS.contains("windows") ? ";" : ":";
    private static final String COMPILE_LIST_NAME = "compile.list";
    private static final String[] JUNIT_FILES     = new String[]{"junit-4.13.2.jar", "hamcrest-core-1.3.jar"};

    /*===========*/
    /* Variables */
    /*===========*/
    /* Parse et exécution des options */
    private List<String[]> lstOptions;
    private Map<String, String> hmArgs;
    private int countArgs = 1;



    /*=============*/
    /* Constructor */
    /*=============*/
    /**
     * Constructor that launches the program.
     * @param args the arguments passed to the program
     */
    private MavenLite(String[] args)
    {
        this.lstOptions = this.initOptions();
        this.hmArgs = new HashMap<String, String>();

        /* Adding mandatory options to the hashmap */
        for (String[] opt : this.lstOptions)
            if (opt[6].equals("1"))
                this.hmArgs.put(opt[0], opt[5]);

        this.parseOptions(args);
        this.executeOption(this.lstOptions);
    }

    /**
     * Constructor used only to load the list of options.
     * It is used solely for displaying a stylized help and uninstalling Maven Lite.
     */
    private MavenLite()
    {
        this.lstOptions = this.initOptions();
    }



    /*==========================================*/
    /* Méthodes pour la préparation des options */
    /*==========================================*/
    /**
     * Initializes the list of options.
     * Options are in the form of a String array.
     * <ul>
     *   <li>0: option name</li>
     *   <li>1: short name of the option</li>
     *   <li>2: long name of the option</li>
     *   <li>3: minimum number of arguments for the option</li>
     *   <li>4: maximum number of arguments for the option</li>
     *   <li>5: default value of the option</li>
     *   <li>6: order number of the option</li>
     *   <li>7: description of the option</li>
     * </ul>
     * @return the list of usable options
     */
    private List<String[]> initOptions() {
        List<String[]> lst = new ArrayList<String[]>();

        /*                             name                  short    long                       min arguments  max arguments  default value                  order      description       */
        /*                             0                     1        2                          3              4              5                             6          7                 */
        /* 0  */ lst.add(new String[] {"file"             , "-f"   , "--file"                 , "0"        , "1"        , MavenLite.FILE     , "0", "Load options from a configuration file. Learn more: "});
        /* 1  */ lst.add(new String[] {"create"           , "-cr"  , "--create"               , "0"        , "1"        , MavenLite.CREATE   , "0", "Create project structure and a default configuration file."});
        /* 2  */ lst.add(new String[] {"mvc"              , "-mvc" , "--model-view-controller", "0"        , "0"        , null               , "0", "Specify for the '" + lst.get(1)[2] + "' option to create an MVC project structure."});
        /* 3  */ lst.add(new String[] {"compilation"      , "-c"   , "--compilation"          , "0"        , "0"        , null               , "0", "Compile the project."});
        /* 4  */ lst.add(new String[] {"launch"           , "-l"   , "--launch"               , "0"        , "0"        , null               , "0", "Launch the project."});
        /* 5  */ lst.add(new String[] {"compile-launch"   , "-cl"  , "--compile-launch"       , "0"        , "0"        , null               , "0", "Compile and launch the project. (equivalent to -c -l)"});
        /* 6  */ lst.add(new String[] {"launch-compile"   , "-lc"  , "--launch-compile"       , "0"        , "0"        , null               , "0", "Compile and launch the project. (equivalent to -c -l)"});
        /* 7  */ lst.add(new String[] {"quiet"            , "-q"   , "--quiet"                , "0"        , "0"        , null               , "0", "Suppress java output in the terminal during project execution."});
        /* 8  */ lst.add(new String[] {"verbose"          , "-v"   , "--verbose"              , "0"        , "0"        , null               , "0", "Display executed commands."});
        /* 9  */ lst.add(new String[] {"exclude"          , "-ex"  , "--exclude"              , "unlimited", "unlimited", null               , "0", "Exclude java files and directories from compilation. If you want to pass an argument starting with '-', escape the '-' character with two '\\' like this: '-ex \\\\-file'."});
        /* 10 */ lst.add(new String[] {"compile-jar"      , "-cj"  , "--compile-jar"          , "0"        , "1"        , null               , "0", "Create a jar file for your project. You can specify the name of the jar file to create. If you don't specify a name, the jar file will be named after the project."});
        /* 11 */ lst.add(new String[] {"launch-jar"       , "-lj"  , "--launch-jar"           , "0"        , "1"        , null               , "0", "Launch an executable jar file. You can specify the name of the jar file to launch. If you don't specify a name, the jar file will be named after the project."});
        /* 12 */ lst.add(new String[] {"integrate-test"   , "-it"  , "--integrate-test"       , "0"        , "0"        , null               , "0", "Integrate unit tests into the project."});
        /*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*/
        /* Everything above this line should not be moved, their order is important */

        /* 13 */ lst.add(new String[] {"source"           , "-s"   , "--source"               , "1"        , "1"        , MavenLite.SOURCE   , "1", "Folder containing java files to compile."});
        /* 14 */ lst.add(new String[] {"target"           , "-t"   , "--target"               , "1"        , "1"        , MavenLite.TARGET   , "1", "Output folder for compiled files. This folder will be created if it does not exist and will be automatically added to the classpath during compilation and execution."});
        /* 15 */ lst.add(new String[] {"resources"        , "-r"   , "--resources"            , "1"        , "1"        , MavenLite.RESOURCES, "1", "Folder containing resource files to copy into the output folder for compiled files when creating a jar file."});
        /* 15 */ lst.add(new String[] {"classpath"        , "-cp"  , "--classpath"            , "unlimited", "unlimited", null               , "0", "Specify the classpath to use during compilation and execution. If you want to add multiple elements to the classpath, separate them with ':'."});
        /* 16 */ lst.add(new String[] {"libraries"        , "-lib" , "--libraries"            , "0"        , "1"        , MavenLite.LIBRARIES, "0", "Folder containing jar files used by the program. All jar files will be added to the classpath during compilation and execution."});
        /* 17 */ lst.add(new String[] {"arguments"        , "-args", "--arguments"            , "unlimited", "unlimited", null               , "0", "All arguments to pass to the main class. If you want to pass an argument starting with '-', escape the '-' character with two '\\' like this: '-args \\\\-argument_for_main'."});
        /* 18 */ lst.add(new String[] {"main"             , "-m"   , "--main"                 , "1"        , "1"        , null               , "0", "Main class to launch. If you want to launch a class in a package, specify the package with the class name like this: 'package.name.MainClass'"});
        /* 19 */ lst.add(new String[] {"encoding"         , "-e"   , "--encoding"             , "1"        , "1"        , MavenLite.ENCODING , "1", "Change the encoding of the java files to be compiled."});
        /* 20 */ lst.add(new String[] {"export"           , "-exp" , "--export"               , "0"        , "1"        , MavenLite.EXPORT   , "0", "Create an executable jar file to launch the project without installing MavenLite."});
        /* 21 */ lst.add(new String[] {"maven"            , "-mvn" , "--maven"                , "0"        , "0"        , null               , "0", "Convert the project to a Maven project by creating a pom.xml file and moving files if necessary."});
        /* 22 */ lst.add(new String[] {"version"          , "-V"   , "--version"              , "0"        , "0"        , MavenLite.VERSION  , "0", "Display the version."});
        /* 23 */ lst.add(new String[] {"help"             , "-h"   , "--help"                 , "0"        , "0"        , null               , "0", "Display help and exit."});
        /* 24 */ lst.add(new String[] {"clear"            , "-clr" , "--clear"                , "0"        , "0"        , null               , "0", "Delete files in the output folder for compiled files."});
        /* 25 */ //lst.add(new String[] {"add-compile-option", "-aco" , "--add-compile-option"   , "unlimited"    , "unlimited"    , null                          , "0", "Add an option to java during compilation. Caution, no verification is done on the option, so be careful about what you add."});
        /* 26 */ //lst.add(new String[] {"add-launch-option" , "-alo" , "--add-launch-option"    , "unlimited"    , "unlimited"    , null                          , "0", "Add an option to java during execution. Caution, no verification is done on the option, so be careful about what you add."});
        /* To add an option, add a String array to the list above. If the option needs to execute code, add it to the switch in the executeOption() method and create a method for executing the option. */

        return lst;
    }

    /**
     * Parses the arguments passed to the program.
     * If an option is passed multiple times, only the last one will be considered, except for options that can have an unlimited number of arguments.
     * @param args the arguments to parse
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

            /* Toute les autres options */
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
                            System.out.println(MavenLite.WARNING + "The option '" + MavenLite.YELLOW_BOLD + args[i] + MavenLite.DEFAULT + "' requires an argument.");

                            if (i + 1 < args.length && args[i + 1].startsWith("-"))
                                System.out.println(MavenLite.WARNING + "If the argument starts with the '-' character, please escape the '-' character with two '\\' like this: '" + MavenLite.YELLOW_BOLD + args[i] + " \\\\" + args[i + 1] + MavenLite.DEFAULT + "'");

                            System.exit(0);
                        }
                    }
                    /* Parse les options qui peuvent avoir un nombre illimité d'argument */
                    else if (opt[3].equals("unlimited"))
                    {
                        StringBuilder sArgs = new StringBuilder();
                        sArgs.append(this.hmArgs.get(opt[0]) == null ? "" : this.hmArgs.get(opt[0]));

                        while (i + 1 < args.length && !args[i + 1].startsWith("-"))
                            sArgs.append(MavenLite.ARG_SEPARATOR).append(args[++i]);

                        this.hmArgs.put(opt[0], sArgs.toString().replace(MavenLite.SPACE_REPLACEMENT, " "));
                    }
                    /* Parse les options qui ont un nombre d'argument défini autre que 0, 1 et unlimited */
                    /* Pour l'instant ce code n'est pas utilisé */
                    else
                    {
                        StringBuilder sArgs = new StringBuilder();
                        sArgs.append(this.hmArgs.get(opt[0]) == null ? "" : this.hmArgs.get(opt[0]));

                        for (int j = 0; j < Integer.parseInt(opt[3]) && i+1 < args.length && !args[i + 1].startsWith("-"); j++)
                            sArgs.append(args[++i]).append(MavenLite.ARG_SEPARATOR);

                        this.hmArgs.put(opt[0], sArgs.toString().substring(0, sArgs.length()-MavenLite.ARG_SEPARATOR.length()));
                    }

                    opt[6] = String.valueOf(this.countArgs);
                    bFound = true;
                    break;
                }
            }

            if (!bFound)
            {
                System.out.println(MavenLite.ERROR + "Unknown option '" + MavenLite.RED_BOLD + args[i] + MavenLite.DEFAULT + "'.");
                System.exit(1);
            }
            else
            {
                this.countArgs++;
            }
        }
    }

    /**
     * Removes unused options from the list passed as a parameter (which must be a copy of the list of options).
     * @param lst the list from which to remove unused options
     * @return the list without unused options
     */
    private List<String[]> removeUnusedOption(List<String[]> lst)
    {
        List<String[]> lstSorted = new ArrayList<String[]>();

        for (String[] opt : lst)
            if (!opt[6].equals("0"))
                lstSorted.add(opt);

        return lstSorted;
    }



    /*===================================================================*/
    /* Méthodes générale utilisée par les options ou les autres méthodes */
    /*===================================================================*/
    /**
     * Executes a command in the terminal (bash / cmd).
     * @param command the command to execute
     * @return the return code of the command. 0 if the command executed successfully, 1 otherwise.
     */
    private int executCommande(String commande)
    {
        ProcessBuilder processBuilder = new ProcessBuilder();
        String shell;
        String shellOption;

        if (MavenLite.OS.contains("windows"))
        {
            shell = "powershell";
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
            BufferedReader readerError  = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = reader.readLine()) != null)
                if (this.hmArgs.get(this.lstOptions.get(7)[0]) == null)
                    System.out.println(line);
            
                    

            int rCode = process.waitFor();
            if (rCode != 0)
            {
                while ((line = readerError.readLine()) != null)
                    System.out.println(MavenLite.ERROR + line);
            }

            return rCode;
        }
        catch (Exception e)
        {
            System.out.println(MavenLite.ERROR + "The execution of the command '" + MavenLite.RED_BOLD + shell + " " + shellOption + " " + commande + MavenLite.DEFAULT + "' has failed.");
            System.exit(1);
        }

        return 1;
    }

    /* Main */
    /**
     * Automatically detects the Main class to launch.
     * @param directory the directory to traverse (Java project source)
     * @return the name with the package of the Main class to launch. Format: "package.name.MainClass"
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
                                if (line.replace(" ", "").contains("public static void main(String[]".replace(" ", "")))
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
                            System.out.println(MavenLite.ERROR + "Reading the file '" + MavenLite.RED_BOLD + file.getName() + MavenLite.DEFAULT + "' has failed.");
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
                System.out.println(MavenLite.ERROR + "The file '" + MavenLite.RED_BOLD + directory.getName() + MavenLite.DEFAULT + "' is not a directory.");
            else
                System.out.println(MavenLite.ERROR + "The directory '" + MavenLite.RED_BOLD + directory.getName() + MavenLite.DEFAULT + "' does not exist.");

            System.exit(1);
        }

        return mainClass;
    }

    /**
     * Retrieves the package name from a Java file.
     * @param javaFile the Java file to analyze
     * @return the package name in the format "package.name.". If the file does not contain a package, an empty string is returned.
     */
    private String getPackageName(File javaFile)
    {
        String packageName = "";

        try
        {
            Scanner scanner = new Scanner(javaFile);
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine().replace("^[\\u0009\\u0020]*", "");
                if (line.contains("package"))
                {
                    packageName = line.split(" ")[1].replace(";", ".");
                    break;
                }
                else if (!line.replace("^[\\u0009\\u0020]*", "").equals(""))
                {
                    break;
                }
            }
            scanner.close();
        }
        catch (IOException e)
        {
            System.out.println(MavenLite.ERROR + "Reading the file '" + MavenLite.RED_BOLD + javaFile.getName() + MavenLite.DEFAULT + "' has failed.");
            System.exit(1);
        }

        return packageName;
    }

    /* Compilation and JAR Compilation */
    /**
     * Generates the list of files to compile.
     * @param source the directory to traverse (Java project source)
     * @return the list of files to compile separated by line breaks (\n)
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
                System.out.println(MavenLite.ERROR + "The file '" + MavenLite.RED_BOLD + source.getName() + MavenLite.DEFAULT + "' is not a directory.");
            else
                System.out.println(MavenLite.ERROR + "The directory '" + MavenLite.RED_BOLD + source.getName() + MavenLite.DEFAULT + "' does not exist.");

            System.exit(1);
        }

        String exclude = this.hmArgs.get(this.lstOptions.get(9)[0]);
        if (exclude != null)
            for (String ex : exclude.replace("\\", "").split(MavenLite.ARG_SEPARATOR))
                for (String line : sCompileList.toString().split("\n"))
                    if (line.contains(ex))
                        sCompileList = new StringBuilder(sCompileList.toString().replace(line + "\n", ""));

        return sCompileList.toString();
    }

    /**
     * Safely deletes a file or folder.
     * @param f the file or folder to delete
     */
    private void removeFile(String name)
    {
        File f = new File(name);
        if (f.exists())
            f.delete();
    }

    /**
     * Copies a file tree.
     * @param source the folder to copy
     * @param target the destination folder
     */
    private boolean copyArborescence(File source, File target)
    {
        if (source.exists() && source.isDirectory())
        {
            /* Création du dossier de destination */
            if (!new File(target.getPath() + source.getName()).exists())
                target.mkdirs();

            /* Création de tout les dossier de l'arborscence */
            File[] lstFiles = source.listFiles();
            if (lstFiles != null)
            {
                for (File file : lstFiles)
                    if (file.isDirectory())
                        new File(target.getPath() + "/" + source.getName() + "/" + file.getName()).mkdirs();
            }


            /* Copie des fichiers */
            if (lstFiles != null)
            {
                for (File file : lstFiles)
                {
                    if (file.isFile())
                    {
                        try
                        {
                            Files.copy(file.toPath(), new File(target.getPath() + "/" + source.getName() + "/" + file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                        }
                        catch (IOException e)
                        {
                            System.out.println(MavenLite.ERROR + "Copying the file '" + MavenLite.RED_BOLD + file.getName() + MavenLite.DEFAULT + "' has failed.");
                            return false;
                        }
                    }
                    else if (file.isDirectory())
                    {
                        this.copyArborescence(file, new File(target.getPath() + "/" + source.getName() + "/"));
                    }
                }
            }
        }
        else
        {
            if (source.exists())
                System.out.println(MavenLite.ERROR + "The file '" + MavenLite.RED_BOLD + source.getName() + MavenLite.DEFAULT + "' is not a directory.");
            else
                System.out.println(MavenLite.ERROR + "The directory '" + MavenLite.RED_BOLD + source.getName() + MavenLite.DEFAULT + "' does not exist.");

            return false;
        }

        return true;
    }

    /* Uninstallation */
    /**
     * Asks the user if they are sure they want to uninstall Maven Lite.
     * @return 0 if the user confirms the uninstallation, 1 if there is an error, 2 if the user cancels the uninstallation
     */
    private int confirmeUninstall()
    {
        System.out.print(MavenLite.WARNING + MavenLite.RED_BOLD + "WARNING" + MavenLite.DEFAULT + ", you are about to uninstall Maven Lite. Are you sure you want to continue? (y/n): ");
        Scanner sc = new Scanner(System.in);
        String reponse = sc.nextLine().toLowerCase();
        sc.close();

        if (!reponse.matches("^[yY]([eE][sS])?$"))
        {
            System.out.println(MavenLite.INFO + "Uninstallation canceled.");
            return 2;
        }

        return 0;
    }

    /**
     * Deletes a file.
     * @param filePath the path of the file to delete
     * @return true if the file was deleted, false otherwise
     */
    private static boolean deleteFile(String filePath)
    {
        File file = new File(filePath);
        return file.delete();
    }

    /* Clear */
    /**
     * Deletes all folders and files in a directory.
     * @param directory the directory to delete
     * @return true if the directory was deleted, false otherwise
     */
    private boolean deleteDirectory(File directory)
    {
        if (directory.exists() && directory.isDirectory())
        {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile())
                        file.delete();
                    else if (file.isDirectory())
                        this.deleteDirectory(file);
                }
            }

            return directory.delete();
        }
        else
        {
            if (directory.exists())
                System.out.println(MavenLite.ERROR + "The file '" + MavenLite.RED_BOLD + directory.getName() + MavenLite.DEFAULT + "' is not a directory.");
            else
                System.out.println(MavenLite.ERROR + "The directory '" + MavenLite.RED_BOLD + directory.getName() + MavenLite.DEFAULT + "' does not exist.");

            return false;
        }
    }

    /* Autres */


    /*=======================================*/
    /* Méthodes pour l'exécution des options */
    /*=======================================*/
    /**
     * Executes the options passed as parameters if they need to be executed.
     * @param lst the list of options to execute
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
                case "help":
                    System.out.println(this.help(this.lstOptions));
                    System.exit(0);
                    break;
                case "clear":
                    this.clear(new File(this.hmArgs.get("target")));
                    break;
                case "maven":
                    // System.exit(this.maven());
                    this.maven();
                    break;
                case "integrate-test":
                    this.integrateTest();
                    break;
                case "export":
                    this.export();
                    break;
                case "libraries":
                    this.hmArgs.put(opt[0], this.libraries(this.hmArgs.get(opt[0])));
                    break;
                case "classpath":
                    this.hmArgs.put(opt[0], this.classpath(this.hmArgs.get(opt[0]).substring(MavenLite.ARG_SEPARATOR.length())));
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

        /* Compilation du fichier jar */
        if (!this.lstOptions.get(10)[6].equals("0"))
            this.compileJar();

        /* Lancement */
        if (!this.lstOptions.get(4)[6].equals("0") || !this.lstOptions.get(5)[6].equals("0") || !this.lstOptions.get(6)[6].equals("0"))
            this.launch();
        
        /* Lancement du fichier jar */
        if (!this.lstOptions.get(11)[6].equals("0"))
            this.launchJar();
    }



    /*=====================================*/
    /* Méthodes correspondants aux options */
    /*=====================================*/
    /**
     * Displays the version, author's name, and information about the runtime environment.
     * @param authorPhrase the phrase to display after the version
     */
    private void version(String phraseAuteur)
    {
        String version = "";

        String mvnlHome = MavenLite.class.getClassLoader().getResource("MavenLite.class").toString();

        version += MavenLite.BOLD + "Maven Lite " + MavenLite.VERSION + MavenLite.DEFAULT + " " + MavenLite.GREEN_BOLD + phraseAuteur + MavenLite.DEFAULT + "\n";
        version += "Maven Lite home : " + mvnlHome.substring(5, mvnlHome.length()-"MavenLite.class".length()) + "\n";
        version += "Java version : " + System.getProperty("java.version") + ", vendor : " + System.getProperty("java.vendor") + ", runtime : " + System.getProperty("java.runtime.name") + "\n";
        version += "Default locale : " + System.getProperty("user.language") + "_" + System.getProperty("user.country") + ", platform encoding : " + System.getProperty("file.encoding") + "\n";
        version += "OS name : \"" + System.getProperty("os.name") + "\", version : \"" + System.getProperty("os.version") + "\", architecture : \"" + System.getProperty("os.arch") + "\"";
    
        System.out.println(version);
    }

    /**
     * Displays help based on the list of options.
     * @param lst the list of options
     * @return help as a formatted string
     */
    private String help(List<String[]> lst)
    {
        int optLength = 30;
        int descLength = 50;
        StringBuilder help = new StringBuilder();
        help.append("\nUsage: mvnl [options] [argument]\n\nOptions:\n");

        for (String[] s : lst)
        {
            help.append(String.format("  %-" + (optLength-2) + "s", (String.format("%-5s, ", s[1]) + s[2])));

            /* Écriture de la description */
            String sDesc = s[7];
            int currentLineLength = 0;
            for (String word : sDesc.split(" "))
            {
                if ((currentLineLength + word.length()) > descLength)
                {
                    help.append("\n" + String.format("%-" + optLength + "s", ""));
                    currentLineLength = 0;
                }

                help.append(word + " ");
                currentLineLength = currentLineLength + word.length();
            }

            if (s[3].equals(s[4]))
                help.append(String.format("\n%-" + optLength + "s", "") + "Number of arguments  : " + MavenLite.BOLD + s[3] + MavenLite.DEFAULT + ".");
            else
                help.append(String.format("\n%-" + optLength + "s", "") + "Number of arguments  : from " + MavenLite.BOLD + s[3] + MavenLite.DEFAULT + " to " + MavenLite.BOLD + s[4] + MavenLite.DEFAULT + ".");

            if (s[5] != null)
                help.append(String.format("\n%-" + optLength + "s", "") + "Default value        : " + MavenLite.BOLD + s[5] + MavenLite.DEFAULT + ".");

            help.append("\n\n");
            }

            help.append(MavenLite.BLUE_BOLD + "Complete documentation: <https://florobart.github.io/mavenlite.github.io>\n" + MavenLite.DEFAULT);

        return help.toString();
    }    

    /**
     * Formats the string passed as a parameter into the Java classpath form corresponding to the OS.
     * @param classpath the string to format
     * @return the formatted string. If the string is null, null is returned.
     */
    private String classpath(String classpath)
    {
        if (classpath == null)
            return null;

        return classpath.replace(MavenLite.ARG_SEPARATOR, MavenLite.CP_SEPARATOR).replace("$CLASSPATH", System.getProperty("java.class.path"));
    }

    /**
     * Specifies the folder containing the JAR files used by the program.
     * All JAR files will be added to the classpath during compilation and execution.
     * @param fileName the name of the folder containing the JAR files
     * @return the list of JAR files from the project root to add to the classpath as a string separated by ':'
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
                        libraries.append(file.getPath()).append(MavenLite.CP_SEPARATOR);
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
                System.out.println(MavenLite.ERROR + "The file '" + MavenLite.RED_BOLD + fileName + MavenLite.DEFAULT + "' is not a folder.");
            else
                System.out.println(MavenLite.ERROR + "The folder '" + MavenLite.RED_BOLD + fileName + MavenLite.DEFAULT + "' does not exist.");
            
            System.exit(1);
        }

        return libraries.toString() == "" ? null : libraries.toString().substring(0, libraries.length()-1);
    }

    /**
     * Deletes the compiled files in the output folder for compiled files.
     * @param f the folder to traverse
     */
    private void clear(File f)
    {
        if (f.exists() && f.isDirectory())
        {
            File[] lstFiles = f.listFiles();
            if (lstFiles != null)
            {
                for (File file : lstFiles)
                {
                    if (file.isFile())
                        file.delete();
                    else if (file.isDirectory())
                        if (!this.deleteDirectory(file))
                        {
                            System.out.println(MavenLite.ERROR + "Deleting the folder '" + MavenLite.RED_BOLD + file.getName() + MavenLite.DEFAULT + "' has failed.");
                            System.exit(1);
                        }
                }

                System.out.println(MavenLite.SUCCESS + "The folder '" + MavenLite.GREEN_BOLD + f.getName() + MavenLite.DEFAULT + "' has been emptied.");
            }
            else
            {
            if (f.exists())
                System.out.println(MavenLite.ERROR + "The file '" + MavenLite.RED_BOLD + f.getName() + MavenLite.DEFAULT + "' is not a folder.");
            else
                System.out.println(MavenLite.ERROR + "The folder '" + MavenLite.RED_BOLD + f.getName() + MavenLite.DEFAULT + "' does not exist.");
            }

            System.exit(1);
        }

    }

    /**
     * Loads options from a configuration file.
     * The separators are space and newline characters.
     * Command line options take precedence over options in the configuration file.
     * @param fileName the name of the configuration file
     */
    private void file(String fileName)
    {
        try
        {
            StringBuilder sFile = new StringBuilder();
            Scanner sc = new Scanner(new File(fileName));

            while (sc.hasNextLine())
            {
                String line = sc.nextLine().replace("^[\\u0009\\u0020]*", ""); /* Removes spaces and tabs at the beginning of the line */
                if (!line.startsWith("#") && !line.equals(""))
                {
                    /* Removal of spaces in strings */
                    Pattern p = Pattern.compile("\"(?:\\\\\"|[^\"])*\""); /* Matches all words between quotes, ignoring quotes preceded by the character "\" */
                    Matcher m = p.matcher(line);
                    while (m.find())
                    {
                        line = line.replace(m.group(0), m.group(0).replace(" ", MavenLite.SPACE_REPLACEMENT));
                        
                        /* Removal of quotes at the beginning and end */
                        line = line.replaceFirst("\"", "");
                        line = line.substring(0, line.length()-1);
                    }

                    sFile.append(line).append(" ");
                }
            }

            sc.close();

            this.parseOptions(sFile.toString().split(" "));
        }
        catch (Exception e)
        {
            System.out.println(MavenLite.ERROR + "Reading the file '" + MavenLite.RED_BOLD + fileName + MavenLite.DEFAULT + "' has failed.");
            System.exit(1);
        }
    }

    /**
     * Creates the entire structure, including files, for unit testing.
     */
    private void integrateTest()
    {
        System.out.println(MavenLite.INFO + "Unit test integration is coming soon...");
        // TODO : Create the structure for unit tests
    }


    /**
     * Creates the project structure along with a default configuration file.
     * @param projectName the name of the project
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
            ├── resources
            │   └── lib
            └── test
                └── java
                    └── AppTest.java
        */

        if (new File(projectName).exists() && !projectName.equals("./"))
        {
            System.out.println(MavenLite.ERROR + "The folder '" + MavenLite.RED_BOLD + projectName + MavenLite.DEFAULT + "' already exists.");
            System.exit(1);
        }

        List<String> lstFoldersProject = new ArrayList<String>();
        List<String> lstFilesProject = new ArrayList<String>();
        String main = "HelloWorld";

        /* Création de la liste des dossiers du projet */
        /* 0 */ lstFoldersProject.add(projectName);
        /* 1 */ lstFoldersProject.add(projectName + "/" + MavenLite.SOURCE);
        /* 2 */ lstFoldersProject.add(projectName + "/" + MavenLite.TARGET);
        /* 3 */ lstFoldersProject.add(projectName + "/" + MavenLite.LIBRARIES);

        /* Création de la liste des fichiers du projet */
        /* 0 */ lstFilesProject.add(projectName + "/" + MavenLite.FILE);

        /* Si l'option MVC est passé en paramètre */
        if (this.hmArgs.get(this.lstOptions.get(2)[0]) == null)
        {
            /* Création de la liste des fichiers du projet */
            /* 1 */ lstFilesProject.add(lstFoldersProject.get(1) + "/" + main + ".java");

            /* Création de la liste des fichiers de test du projet */
            if (this.hmArgs.get(this.lstOptions.get(12)[0]) != null)
            {
                /* 4 */ lstFoldersProject.add(projectName + "/" + MavenLite.TEST_UNITAIRE);
                /* 2 */ lstFilesProject.add(lstFoldersProject.get(4) + "/" + main + "Test.java");
            }
        }
        else
        {
            /* Création de la liste des dossier de test du projet */
            if (this.hmArgs.get(this.lstOptions.get(12)[0]) != null)
            {
                /* 4 */ lstFoldersProject.add(projectName + "/" + MavenLite.TEST_UNITAIRE);
                /* 5 */ lstFoldersProject.add(lstFoldersProject.get(4) + "/" + "controller");
                /* 6 */ lstFoldersProject.add(lstFoldersProject.get(4) + "/" + "model");
                /* 7 */ lstFoldersProject.add(lstFoldersProject.get(4) + "/" + "view");
            }

            /* Création de la liste des dossiers du projet */
            /* 8  */ lstFoldersProject.add(lstFoldersProject.get(1) + "/" + "controller");
            /* 9  */ lstFoldersProject.add(lstFoldersProject.get(1) + "/" + "model");
            /* 10 */ lstFoldersProject.add(lstFoldersProject.get(1) + "/" + "view");

            /* Création de la liste des fichiers du projet */
            main = "Controller";
            /* 1 */ lstFilesProject.add(lstFoldersProject.get(1) + "/controller/" + main + ".java");

            /* Création de la liste des fichiers de test du projet */
            if (this.hmArgs.get(this.lstOptions.get(12)[0]) != null)
                /* 2 */ lstFilesProject.add(lstFoldersProject.get(5) + "/" + main + "Test.java");
        }


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
                    System.out.println(MavenLite.ERROR + "Creating the file '" + MavenLite.RED_BOLD + f.getName() + MavenLite.DEFAULT + "' has failed.");
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

            FileWriter fw = new FileWriter(new File(lstFilesProject.get(1)));
            fw.write(sApp.toString());
            fw.close();
        }
        catch (Exception e)
        {
            System.out.println(MavenLite.ERROR + "Writing to the file '" + MavenLite.RED_BOLD + lstFilesProject.get(1) + MavenLite.DEFAULT + "' has failed.");
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

            FileWriter fw = new FileWriter(new File(lstFilesProject.get(0)));
            fw.write(sConfig);
            fw.close();
        }
        catch (Exception e)
        {
            System.out.println(MavenLite.ERROR + "Writing to the file '" + MavenLite.RED_BOLD + lstFilesProject.get(0) + MavenLite.DEFAULT + "' has failed.");
            System.exit(1);
        }

        /* Écriture dans le fichier de test */
        if (this.hmArgs.get(this.lstOptions.get(12)[0]) != null)
        {
            /* Copie des fichiers jar pour les tests unitaires */
            for (String s : MavenLite.JUNIT_FILES)
            {
                File source = new File(MavenLite.class.getClassLoader().getResource(s).getPath()); // MavenLite.class
                File dest = new File(lstFoldersProject.get(3) + "/" + s);

                try
                {
                    Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                catch (IOException e)
                {
                    System.out.println(MavenLite.ERROR + "Copying the file '" + MavenLite.RED_BOLD + s + MavenLite.DEFAULT + "' has failed.");
                    System.exit(1);
                }
            }

            // TODO : Écrire dans le fichier de test
        }

        System.out.println("\n" + MavenLite.SUCCESS + "The project '" + MavenLite.GREEN_BOLD + projectName + MavenLite.DEFAULT + "' has been successfully created.\n");
        System.out.println(MavenLite.INFO    + "To " + MavenLite.BLUE_BOLD + "compile" + MavenLite.DEFAULT + " the project, run the command: '" + MavenLite.BLUE_BOLD + "mvnl -c" + MavenLite.DEFAULT + "'.");
        System.out.println(MavenLite.INFO    + "To " + MavenLite.BLUE_BOLD + "run" + MavenLite.DEFAULT + " the project, run the command: '" + MavenLite.BLUE_BOLD + "mvnl -l" + MavenLite.DEFAULT + "'.");
        System.out.println(MavenLite.INFO    + "To " + MavenLite.BLUE_BOLD + "compile and run" + MavenLite.DEFAULT + " the project using the configuration file data, run the command: '" + MavenLite.BLUE_BOLD + "mvnl -f -cl" + MavenLite.DEFAULT + "'.\n");
    }

    /**
     * Compile the projet.
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
            System.out.println(MavenLite.ERROR + "Writing to the file '" + MavenLite.RED_BOLD + MavenLite.COMPILE_LIST_NAME + MavenLite.DEFAULT + "' has failed.");
            if (this.hmArgs.get(this.lstOptions.get(8)[0]) == null)
                this.removeFile(MavenLite.COMPILE_LIST_NAME);
            System.exit(1);
        }

        /* Compilation */
        command.append("javac -d ").append(this.hmArgs.get("target"));

        if (this.hmArgs.get("libraries") != null)
            command.append(" -cp ").append(this.hmArgs.get("libraries"));
        if (this.hmArgs.get("classpath") != null)
            command.append(MavenLite.CP_SEPARATOR).append(this.hmArgs.get("classpath"));

        if (this.hmArgs.get("add-comile-option") != null)
            command.append(" ").append(this.hmArgs.get("add-comile-option").replace(MavenLite.ARG_SEPARATOR, " ").replace("\\", ""));

        command.append(" -encoding ").append(this.hmArgs.get("encoding"));
        command.append(" '@").append(MavenLite.COMPILE_LIST_NAME).append("'");

        if (this.hmArgs.get(this.lstOptions.get(8)[0]) != null)
            System.out.println(MavenLite.INFO + MavenLite.BLUE_BOLD + command.toString() + MavenLite.DEFAULT);

        System.out.println(MavenLite.INFO + "Compiling the project '" + MavenLite.BLUE_BOLD + MavenLite.PROJECT_NAME + MavenLite.DEFAULT + "'...");
        if (this.executCommande(command.toString()) != 0)
        {
            System.out.println(MavenLite.ERROR + "Compiling the project '" + MavenLite.RED_BOLD + MavenLite.PROJECT_NAME + MavenLite.DEFAULT + "' has failed.");
            if (this.hmArgs.get(this.lstOptions.get(8)[0]) == null)
                this.removeFile(MavenLite.COMPILE_LIST_NAME);
            System.exit(1);
        }
        
        System.out.println(MavenLite.SUCCESS + "Compiling the project '" + MavenLite.GREEN_BOLD + MavenLite.PROJECT_NAME + MavenLite.DEFAULT + "' completed successfully.");
        if (this.hmArgs.get(this.lstOptions.get(8)[0]) == null)
            this.removeFile(MavenLite.COMPILE_LIST_NAME);
    }

    /**
     * Launch the projet.
     */
    private void launch()
    {
        /* Variables */
        StringBuilder command = new StringBuilder();

        /* Lancement */
        command.append("java -cp ");
        command.append(this.hmArgs.get("target"));

        if (this.hmArgs.get("libraries") != null)
            command.append(MavenLite.CP_SEPARATOR).append(this.hmArgs.get("libraries"));
        if (this.hmArgs.get("classpath") != null)
            command.append(MavenLite.CP_SEPARATOR).append(this.hmArgs.get("classpath"));

        if (this.hmArgs.get("add-launch-option") != null)
            command.append(" ").append(this.hmArgs.get("add-launch-option"));

        if (this.hmArgs.get("main") == null)
            this.hmArgs.put("main", this.getMainClassName(new File(this.hmArgs.get("source"))));

        if (this.hmArgs.get("main").isBlank())
        {
            System.out.println(MavenLite.ERROR + "Unable to find the main class of the project. You can specify the main class with the option '" + MavenLite.RED_BOLD + "--main <className>" + MavenLite.DEFAULT + "'.");
            System.exit(1);
        }

        command.append(" ").append(this.hmArgs.get("main"));

        if (this.hmArgs.get("arguments") != null)
        {
            this.hmArgs.put("arguments", this.hmArgs.get("arguments").substring(MavenLite.ARG_SEPARATOR.length()));
            if (MavenLite.OS.contains("windows"))
                this.hmArgs.put("arguments", this.hmArgs.get("arguments").replace("\\\"", "\\\"\\\""));

            for (String s : this.hmArgs.get("arguments").split(MavenLite.ARG_SEPARATOR))
                if (MavenLite.OS.contains("windows"))
                    command.append(" \'").append(s).append("\'");
                else
                    command.append(" \"").append(s).append("\"");
        }

        if (this.hmArgs.get(this.lstOptions.get(8)[0]) != null)
            System.out.println(MavenLite.INFO + MavenLite.BLUE_BOLD + command.toString() + MavenLite.DEFAULT);

        System.out.println(MavenLite.INFO + "Launching the project '" + MavenLite.BLUE_BOLD + MavenLite.PROJECT_NAME + MavenLite.DEFAULT + "'...");
        if (this.executCommande(command.toString()) != 0)
        {
            System.out.println(MavenLite.ERROR + "Launching the project '" + MavenLite.RED_BOLD + MavenLite.PROJECT_NAME + MavenLite.DEFAULT + "' has failed.");
            System.exit(1);
        }
        
        System.out.println(MavenLite.SUCCESS + "Launching the project '" + MavenLite.GREEN_BOLD + MavenLite.PROJECT_NAME + MavenLite.DEFAULT + "' completed successfully.");            
    }

    /**
     * Creates a JAR file from the compiled files.
     */
    private void compileJar()
    {
        System.out.println(MavenLite.INFO + "Jar compilation will be available in " + MavenLite.BLUE_BOLD + "version 2.1.0" + MavenLite.BLUE_BOLD + "...");
        System.exit(0);

        /* Variables */
        StringBuilder command = new StringBuilder();
        String main = this.hmArgs.get("main") == null ? this.getMainClassName(new File(this.hmArgs.get("source"))) : this.hmArgs.get("main");
        File manifest = new File(this.hmArgs.get("target") + "/" + "manifest.txt");

        /* Copying data files */
        if (this.hmArgs.get("resources") != null)
        {
            if (this.copyArborescence(new File(this.hmArgs.get("resources")), new File(this.hmArgs.get("target"))))
            {
                System.out.println(MavenLite.SUCCESS + "Copying the folder '" + MavenLite.GREEN_BOLD + this.hmArgs.get("resources") + MavenLite.DEFAULT + "' to the folder '" + MavenLite.GREEN_BOLD + this.hmArgs.get("target") + MavenLite.DEFAULT + "' completed successfully.");
            }
            else
            {
                System.out.println(MavenLite.ERROR + "Copying the folder '" + MavenLite.RED_BOLD + this.hmArgs.get("resources") + MavenLite.DEFAULT + "' to the folder '" + MavenLite.RED_BOLD + this.hmArgs.get("target") + MavenLite.DEFAULT + "' has failed.");
                System.exit(1);
            }
        }

        /* Décompression des fichiers jar */
        // Fait par copilot
        if (this.hmArgs.get("libraries") != null)
        {
            String[] lstLibraries = this.hmArgs.get("libraries").split(MavenLite.CP_SEPARATOR);
            System.out.println("Libraries : " + this.hmArgs.get("libraries"));
            for (String s : lstLibraries)
            {
                if (s.endsWith(".jar"))
                {
                    File source = new File(s);
                    File dest = new File(this.hmArgs.get("target") + "/" + source.getName());

                    System.out.println("source : " + source.getPath()); // debug
                    System.out.println("dest   : " + dest.getPath()); // debug

                    try
                    {
                        System.out.println(MavenLite.INFO + "Copying the file '" + MavenLite.BLUE_BOLD + s + MavenLite.DEFAULT + "' to the folder '" + MavenLite.BLUE_BOLD + dest.getPath() + MavenLite.DEFAULT + "'..."); // debug
                        Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                    catch (IOException e)
                    {
                        System.out.println(MavenLite.ERROR + "Copying the file '" + MavenLite.RED_BOLD + s + MavenLite.DEFAULT + "' has failed.");
                        System.exit(1);
                    }

                    command = new StringBuilder();
                    command.append("jar xvf ").append(dest.getPath()).append(" -C ").append(this.hmArgs.get("target")).append("/").append(MavenLite.LIBRARIES).append(" .");
                    if (this.hmArgs.get(this.lstOptions.get(8)[0]) != null)
                        System.out.println(MavenLite.INFO + MavenLite.BLUE_BOLD + command.toString() + MavenLite.DEFAULT);

                    System.out.println(MavenLite.INFO + "Decompressing the file '" + MavenLite.BLUE_BOLD + dest.getName() + MavenLite.DEFAULT + "'...");
                    if (this.executCommande(command.toString()) != 0)
                    {
                        System.out.println(MavenLite.ERROR + "Decompressing the file '" + MavenLite.RED_BOLD + dest.getName() + MavenLite.DEFAULT + "' has failed.");
                        System.exit(1);
                    }
                    
                    System.out.println(MavenLite.SUCCESS + "Decompressing the file '" + MavenLite.GREEN_BOLD + dest.getName() + MavenLite.DEFAULT + "' completed successfully.");
                    //this.removeFile(dest.getPath());                        
                }
            }
        }

        System.exit(0);

        /* Création du manifest */
        if (!manifest.exists())
        {
            try
            {
                FileWriter fw = new FileWriter(manifest);
                fw.write("Manifest-Version: 1.0\n");
                fw.write("Author: " + System.getProperty("user.name") + "\n");
                fw.write("Main-Class: " + main + "\n");
                fw.close();
            }
            catch (Exception e)
            {
                System.out.println(MavenLite.ERROR + "Writing to the file '" + MavenLite.RED_BOLD + this.hmArgs.get("target") + "/" + "manifest.txt" + MavenLite.DEFAULT + "' has failed.");
                if (this.hmArgs.get(this.lstOptions.get(8)[0]) == null)
                    this.removeFile(manifest.getPath());
                System.exit(1);
            }
        }

        /* Création du fichier jar */
        command = new StringBuilder();
        command.append("jar cvfe ");
        command.append(this.hmArgs.get("target")).append("/").append(MavenLite.PROJECT_NAME).append(".jar ");
        command.append(main).append(" -C ").append(this.hmArgs.get("target")).append(" .");

        if (this.hmArgs.get(this.lstOptions.get(8)[0]) != null)
            System.out.println(MavenLite.INFO + MavenLite.BLUE_BOLD + command.toString() + MavenLite.DEFAULT);

        System.out.println(MavenLite.INFO + "Creating the JAR file '" + MavenLite.BLUE_BOLD + MavenLite.PROJECT_NAME + ".jar" + MavenLite.DEFAULT + "'...");
        if (this.executCommande(command.toString()) != 0)
        {
            System.out.println(MavenLite.ERROR + "Creating the JAR file '" + MavenLite.RED_BOLD + MavenLite.PROJECT_NAME + ".jar" + MavenLite.DEFAULT + "' has failed.");
            if (this.hmArgs.get(this.lstOptions.get(8)[0]) == null)
                this.removeFile(manifest.getPath());
            System.exit(1);
        }
        
        System.out.println(MavenLite.SUCCESS + "Creating the JAR file '" + MavenLite.GREEN_BOLD + MavenLite.PROJECT_NAME + ".jar" + MavenLite.DEFAULT + "' completed successfully.");
        if (this.hmArgs.get(this.lstOptions.get(8)[0]) == null)
            this.removeFile(manifest.getPath());
    }

    /**
     * Launch JAR file
     */
    private void launchJar()
    {
        /* Variables */
        StringBuilder command = new StringBuilder();
        String jarName = this.hmArgs.get(this.lstOptions.get(11)[0]) == null ? MavenLite.PROJECT_NAME + "/" + this.hmArgs.get("target") : this.hmArgs.get(this.lstOptions.get(11)[0]);

        /* Lancement */
        command.append("java -jar ");
        command.append(jarName);

        if (!command.toString().endsWith(".jar"))
            command.append(".jar");

        if (this.hmArgs.get("arguments") != null)
            for (String s : this.hmArgs.get("arguments").split(MavenLite.ARG_SEPARATOR))
                command.append(" \"").append(s).append("\"");

        if (this.hmArgs.get(this.lstOptions.get(8)[0]) != null)
            System.out.println(MavenLite.INFO + MavenLite.BLUE_BOLD + command.toString() + MavenLite.DEFAULT);

        System.out.println(MavenLite.INFO + "Launching the JAR file '" + MavenLite.BLUE_BOLD + MavenLite.PROJECT_NAME + ".jar" + MavenLite.DEFAULT + "'...");
        if (this.executCommande(command.toString()) != 0) {
            System.out.println(MavenLite.ERROR + "Launching the JAR file '" + MavenLite.RED_BOLD + MavenLite.PROJECT_NAME + ".jar" + MavenLite.DEFAULT + "' failed.");
            System.exit(1);
        }
        
        System.out.println(MavenLite.SUCCESS + "Launching the JAR file '" + MavenLite.GREEN_BOLD + MavenLite.PROJECT_NAME + ".jar" + MavenLite.DEFAULT + "' completed successfully.");
    }

    /**
     * Exports the project to a JAR file.
     * The JAR file will be exported to the output directory.
     */
    private void export()
    {
        System.out.println(MavenLite.INFO + "Export feature coming soon...");
        // TODO : Implement exporting the project to a .class file
    }

    /**
     * Converts the project to a Maven project.
     * @return 0 if the conversion is successful, 1 otherwise
     */
    private int maven()
    {
        System.out.println(MavenLite.INFO + "Maven feature coming soon...");
        // TODO : Implement converting the project to a Maven project

        return 0;
    }


    /**
     * Uninstalls the manual pages of Maven Lite.
     * @return 0 if uninstallation is successful, 1 if an error occurs, 2 if the user cancels the uninstallation
     */
    private int uninstallManPage()
    {
        // Variables
        int exitCode = this.confirmeUninstall();
        if (exitCode != 0)
            return exitCode;
    
        String[] manPageLanguages = {"fr", "en"};
    
        System.out.println(MavenLite.INFO + "Uninstalling Maven Lite...");
    
        // Removing manual pages
        System.out.println(MavenLite.INFO + "Removing manual pages in different languages...");
        for (String language : manPageLanguages) {
            String manPageFile = "/usr/local/man/" + language + "/man1/mvnl.1.gz";
            if (new File(manPageFile).exists()) {
                if (!deleteFile(manPageFile)) {
                    System.out.println(MavenLite.ERROR + "Error while deleting file '" + MavenLite.RED_BOLD + manPageFile + MavenLite.DEFAULT + "'.");
                    return 1;
                } else
                    System.out.println(MavenLite.INFO + "File '" + MavenLite.BLUE_BOLD + manPageFile + MavenLite.DEFAULT + "'' deleted successfully.");
            }
        }
        System.out.println(MavenLite.SUCCESS + "Manual pages deleted successfully.");
    
        return 0;
    }
    

    /**
     * Displays the program name in ASCII art, along with the version, author, and a help phrase.
     */
    private void defaultAffichage()
    {
        String helpStyle = "\n";

        /* Écriture de 'Maven Lite' en ascii art */
        helpStyle += "        __  ___                          __    _ __      \n";
        helpStyle += "       /  |/  /___ __   _____  ____     / /   (_) /____   \n";
        helpStyle += "      / /|_/ / __ `/ | / / _ \\/ __ \\   / /   / / __/ _ \\  \n";
        helpStyle += "     / /  / / /_/ /| |/ /  __/ / / /  / /___/ / /_/  __/  \n";
        helpStyle += "    /_/  /_/\\__,_/ |___/\\___/_/ /_/  /_____/_/\\__/\\___/   \n\n";



        System.out.println(helpStyle);
        this.version("By " + MavenLite.AUTHOR);
        System.out.println("\n    " + MavenLite.BOLD + "Use the command '" + MavenLite.DEFAULT + MavenLite.GREEN_BOLD + "mvnl -h" + MavenLite.DEFAULT + MavenLite.BOLD + "' or '" + MavenLite.DEFAULT + MavenLite.GREEN_BOLD + "mvnl --help" + MavenLite.DEFAULT + MavenLite.BOLD + "' to display the help." + MavenLite.DEFAULT + "\n");
    }



    /*======*/
    /* Main */
    /*======*/
    public static void main(String[] args)
    {
        if (args.length <= 1)
        {
            new MavenLite().defaultAffichage();
            System.exit(0);
        }
        else if (args.length == 1 && args[0].equals("uninstall_from_mvnl-uninstall"))
        {
            if (MavenLite.OS.contains("windows"))
                System.exit(new MavenLite().confirmeUninstall());
            else
                System.exit(new MavenLite().uninstallManPage());
        }

        String[] args2 = new String[args.length-1];
        for (int i = 1; i < args.length; i++)
            args2[i-1] = args[i];

        MavenLite.SPACE_REPLACEMENT = args[0];

        new MavenLite(args2);
    }
}
