import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class Main 
{
    public void test(String[] args)
    {
        System.out.println("length : " + args.length);
        if (args.length < 1)
        {
            System.out.println("Help");
            System.exit(0);
        }

        if (args.length > 0)
            System.out.println(args[0]);

        Arguments arguments = new Arguments(args);

        try (InputStream in = getClass().getResourceAsStream("/jlc.xml");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            System.out.println("result scanner : " + reader.readLine());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
    }


    public static void main( String[] args )
    {
        new Main().test(args);
    }
}
