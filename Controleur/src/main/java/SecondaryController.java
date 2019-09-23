import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * Created by Nadir Pelletier
 * For : CORE_TP1
 * Date : 2019-05-23
 * Time : 17:19
 */
public class SecondaryController implements Initializable
{


    private Stage stage;
    @FXML
    private CheckBox checkBoxFiltreDate;

    @FXML
    private ChoiceBox<String> ChoiceBoxExpression;

    @FXML
    private ChoiceBox<String> ChoiceBoxAvantLe;

    @FXML
    private DatePicker datePickerFiltre;

    @FXML
    private CheckBox CheckBoxImagePresente;

    @FXML
    private Button btnAnnulerFiltre;

    @FXML
    private Button btnAppliquerFiltre;

    private Map<String,String> filtreParam;

    void activerFiltreDate()
    {
        if(datePickerFiltre.getValue() != null)
        {
            if (!datePickerFiltre.getValue().isAfter(LocalDate.now()))
            {
                LocalDate dateSelectione = datePickerFiltre.getValue();

                this.filtreParam.put("dateSelectionne", dateSelectione.toString());
            }
        }
    }


    void activerFiltreImagePresente()
    {
        if (CheckBoxImagePresente.isSelected())
        {
            this.filtreParam.put("imagePresente", "1");
        }
    }

    @FXML
    void annulerFiltre()
    {
        this.filtreParam = null;
        this.CheckBoxImagePresente.setSelected(false);
        this.checkBoxFiltreDate.setSelected(false);
        this.datePickerFiltre.setValue(null);

//        this.stage.close();
    }

    @FXML
    void appliquerFiltre(ActionEvent event)
    {
        this.filtreParam = new HashMap<>();

        activerFiltreDate();
        activerFiltreImagePresente();
    }

    protected void setStage(Stage stage)
    {
        this.stage = stage;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ChoiceBoxAvantLe.setItems(FXCollections.observableArrayList("Avant le"));
        ChoiceBoxExpression.setItems(FXCollections.observableArrayList("Mot saisi"));
        btnAppliquerFiltre.setDisable(true);

        checkBoxFiltreDate.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
            {
                if (checkBoxFiltreDate.isSelected())
                {
                    btnAppliquerFiltre.setDisable(false);
                }
                else if (CheckBoxImagePresente.isSelected()&& !checkBoxFiltreDate.isSelected())
                {
                    btnAppliquerFiltre.setDisable(false);
                }
                else
                {
                    btnAppliquerFiltre.setDisable(true);
                }

            }
        });

        CheckBoxImagePresente.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
            {
                if (CheckBoxImagePresente.isSelected())
                {
                    btnAppliquerFiltre.setDisable(false);
                }
                else if (!CheckBoxImagePresente.isSelected()&& checkBoxFiltreDate.isSelected())
                {
                    btnAppliquerFiltre.setDisable(false);
                }
                else
                    {
                        btnAppliquerFiltre.setDisable(true);
                    }
            }
        });
    }

    protected Map getFiltreParam()
    {
        return this.filtreParam;
    }
}


