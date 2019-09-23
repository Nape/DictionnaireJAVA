import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * Created by Nadir Pelletier
 * For : CORE_TP1
 * Date : 2019-06-04
 * Time : 15:55
 */
public class Recherche
{
    public static ObservableList filtre(String valeur, ListView listView)
    {
        ObservableList<String> observableListMotFiltre = FXCollections.observableArrayList();

        for (Object object : listView.getItems())
        {
            boolean match = true;
            String mot = (String) object;
            if (!mot.toUpperCase().contains(valeur))
            {
                match = false;
            }
            if (match)
            {
                observableListMotFiltre.add(mot);
            }
        }
        return observableListMotFiltre;
    }

}
