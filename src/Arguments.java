import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Objet qui vas stocker les arguments passés au programme
 */
public class Arguments
{
    private static String FILE_CONFIG = "jlc.ini";

    Map<String, String> mapArgs;


    /**
     * Constructeur par défaut
     */
    public Arguments()
    {
        this.mapArgs = new HashMap<>();
    }


    /**
     * Constructeur avec initialisation des arguments de la ligne de commande
     * @param args les arguments passés au programme
     */
    public Arguments(String[] args)
    {
        this.mapArgs = new HashMap<>();
        this.parseCommandLine(args);
    }

    /**
     * Parse les arguments passés au programme
     * @param args les arguments à parser 
     */
    public void parseCommandLine(String[] args)
    {
        for (String s : args)
        {
            if (s.matches("^[-]{1,2}[a-zA-Z\\u00C0-\\u017F]+$"))
            {
                System.out.println(s);
            }
        }
    }

    /**
     * Parse le fichier de config du projet
     * @param file 
     */
    public void parseFileConfig(File file)
    {

    }
}
