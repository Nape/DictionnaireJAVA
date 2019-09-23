import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Nadir Pelletier
 * For : CORE_TP1
 * Date : 2019-05-30
 * Time : 13:34
 */

/**
 * Classe Factory qui Construit un Dictionnaire à partir d'un fichier de mot.
 */
public class FabriqueMotSingle extends Dictionaire
{

    private static FabriqueMotSingle instanceUnique;
    private Properties properties = new Properties();
    private int motMax;

    /**
     * Construit un Dictionaire de de mot.
     */
    private FabriqueMotSingle()
    {
        lirePropriete();
        this.motMax = Integer.parseInt(properties.getProperty("max.mot"));
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(new File("/Users/Nadir/Desktop/Prog-Analyste/Session 4/Taveaux Pratiques/Programmation Orientée Objet IV/CORE_TP1/Vue/src/main/resources/liste.de.mots.francais.frgut.utf8.txt" ));
//            scanner = new Scanner(new File(String.valueOf(this.getClass().getResource("liste.de.mots.francais.frgut.utf8.txt"))));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        while (scanner.hasNext())
        {
            String mot = scanner.next();

            Mot nouveauMot = new Mot();
            nouveauMot.setMot(mot);
            this.getDictionaire().put(nouveauMot.getMot(),nouveauMot);
        }
        scanner.close();
    }

    /**
     *
     * @return l'instance du Dictionnaire.
     */
    public static synchronized FabriqueMotSingle getInstance()
    {
        if (instanceUnique == null)
        {
            instanceUnique = new FabriqueMotSingle();
            // instanceUnique = new Dictionnaire();
        }

        return instanceUnique;
    }

    /**
     * Lit les propriétés du fichier xml.
     */
    private void lirePropriete()
    {
        InputStream inputStream = this.getClass().getResourceAsStream("propriete.xml");

        try
        {
            properties.loadFromXML(inputStream);
            inputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public int getMotMax()
    {
        return this.motMax;
    }
}
