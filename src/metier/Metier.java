package metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Metier
{
    private Map<String, List<String>> hmArgs;
    private List<Map<String, String>> lstOptions;


    /**
     * Enregistre les arguments passés au programme
     */
    public Metier(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Help");
            System.exit(0);
        }

        parseCommandLine(args);
    }


    public List<Map<String, String>> initOptions()
    {
        List<Map<String, String>> lstOpts = new ArrayList<Map<String, String>>();

        // Pour chaque ligne du fichier data/options.data
        


        return lstOpts;
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
                System.out.println("option : '" + s + "'");
            }
            else
            {
                System.out.println("argument : '" + s + "'");
            }
        }
    }
}
