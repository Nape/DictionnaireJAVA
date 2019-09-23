import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Nadir Pelletier
 * For : CORE_TP1
 * Date : 2019-05-24
 * Time : 11:25
 */

/**
 * Implementation d'un dictionnaire.
 */
public class Dictionaire
{
    private Map<String,Mot> dictionaire;

    /**
     * Construit un TreeMap.
     */
    protected Dictionaire()
    {
        this.dictionaire = new TreeMap();
    }

    /**
     *
     * @return le dictionnaire.
     */
    public Map getDictionaire()
    {
        return dictionaire;
    }

    /**
     * Ajoute un mot.
     * @param stringMot le mot (String)
     * @param singleMot le mot (Mot)
     */
    public void putMot(String stringMot, Mot singleMot)
    {
        this.dictionaire.put(stringMot,singleMot);
    }

    /**
     * Suprime un mot.
     * @param stringMot le mot (String)
     */
    public void removeMot(String stringMot)
    {
        this.dictionaire.remove(stringMot);
    }

    /**
     * Retourne un mot a partir de sa cle dans la map.
     * @param stringMot un Mot (String)
     * @return un mot (Mot)
     */
    public Mot getMot(String stringMot)
    {
        return this.dictionaire.get(stringMot);
    }




}
