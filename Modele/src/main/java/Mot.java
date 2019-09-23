import java.time.LocalDate;

/**
 * Created by Nadir Pelletier
 * For : CORE_TP1
 * Date : 2019-05-30
 * Time : 13:20
 */

/**
 * Implematation d'un mot
 */
public class Mot
{

    private String mot;
    private String definition;
    private String nomImage;
    private LocalDate dateSaisie;
    private LocalDate dateModification;

    /**
     * Construit un mot.
     */
    protected Mot()
    {
        this.mot = new String();
        this.definition = new String();
        this.nomImage = new String();
        this.dateSaisie = LocalDate.now();

    }

    public String getMot()
    {
        return mot;
    }

    public void setMot(String mot)
    {
        this.mot = mot;
    }

    public LocalDate getDateSaisie()
    {
        return dateSaisie;
    }
    public LocalDate getDateModification()
    {
        return dateModification;
    }
    public String getDefinition()
    {
        return definition;
    }

    public void setDefinition(String definition)
    {
        this.definition = definition;
    }

    public String getNomImage()
    {
        return nomImage;
    }

    public void setNomImage(String nomImage)
    {
        this.nomImage = nomImage;
    }

    public void ajouterDateModification()
    {
        this.dateModification = LocalDate.now();
    }


}
