import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Nadir Pelletier
 * For : CORE_TP1
 * Date : 2019-05-23
 * Time : 17:12
 */
public class PrimaryController implements Initializable
{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField textFieldRecherche;

    @FXML
    private TextArea textAreaMot;

    @FXML
    private TextArea textAreaDefinition;

    @FXML
    private TextArea textAreaDate;

    @FXML
    private ImageView imageViewMot;

    @FXML
    private CheckBox checkBoxExpression;

    @FXML
    private Hyperlink hyperFiltre;

    @FXML
    private ListView<String> listViewMots;

    @FXML
    private Button btnAjouterMot;

    @FXML
    private Button btnSuprimerMot;

    private static ObservableList<String> observableListMots = FXCollections.observableArrayList();

    private Map<String,String> filtreParam;


    /**
     * Ajoute ou Modifie un mot.
     * @param event
     */
    @FXML
    void ajouterMot(ActionEvent event)
    {
        String motAjoute = textAreaMot.getText();
        String motSelectione = listViewMots.getSelectionModel().getSelectedItem();
        int indexMotAjout = listViewMots.getSelectionModel().getSelectedIndex();
        if (indexMotAjout != -1)
        {
            //Si le nom du mot à ete changé.
            if (!FabriqueMotSingle.getInstance().getMot(motSelectione).getMot().matches(motAjoute))
            {
                if (!FabriqueMotSingle.getInstance().getDictionaire().containsKey(motAjoute))
                {
                    renomerMot(FabriqueMotSingle.getInstance().getMot(motSelectione), motAjoute);

                    listViewMots.getItems().set(indexMotAjout, motAjoute);
                    FabriqueMotSingle.getInstance().getMot(motAjoute).ajouterDateModification();
                    textAreaDate.appendText("\nDate de modification: " + FabriqueMotSingle.getInstance().getMot(motAjoute).getDateModification());

                } else
                {
                    textAreaMot.setText(motSelectione);
                }
            }
        }

        if (!listViewMots.getItems().contains(textFieldRecherche.getText()) && !textFieldRecherche.getText().isEmpty())
        {
            if (!FabriqueMotSingle.getInstance().getDictionaire().containsKey(textFieldRecherche.getText()))
            {
                String nouveauNomMot = textFieldRecherche.getText();
                Mot nouveauMot = new Mot();
                nouveauMot.setMot(nouveauNomMot);
                FabriqueMotSingle.getInstance().getDictionaire().put(nouveauMot.getMot(), nouveauMot);
                initListView();
                listViewMots.refresh();
                textFieldRecherche.setText(nouveauMot.getMot());
                listViewMots.getSelectionModel().select(nouveauMot.getMot());
                textAreaMot.setText(nouveauMot.getMot());
            }
        }
        //Si le mot n'existe pas deja.
        else if (!FabriqueMotSingle.getInstance().getDictionaire().containsKey(motAjoute) && !motAjoute.isEmpty())
        {
            //Ajoute une definition au mot.
            if (compteMot(textAreaDefinition.getText()) <= FabriqueMotSingle.getInstance().getMotMax() && !textAreaDefinition.getText().isEmpty())
            {
                FabriqueMotSingle.getInstance().getMot(motSelectione).setDefinition(textAreaDefinition.getText());
                FabriqueMotSingle.getInstance().getMot(motSelectione).ajouterDateModification();
                textAreaDate.appendText("\nDate de modification: " + FabriqueMotSingle.getInstance().getMot(motSelectione).getDateModification());
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Maximum 20 mots, votre phrase à: " + compteMot(textAreaDefinition.getText()));
                alert.showAndWait();
                alert.getButtonTypes().setAll(ButtonType.CLOSE);
                textAreaDefinition.clear();
            }

            //Si le nom du mot à ete changé.
            if (!FabriqueMotSingle.getInstance().getMot(motSelectione).getMot().matches(motAjoute))
            {
                renomerMot(FabriqueMotSingle.getInstance().getMot(motSelectione), motAjoute);
            }
        }
        else
            {
                System.out.println(compteMot(textAreaDefinition.getText()));
                System.out.println(FabriqueMotSingle.getInstance().getMotMax());
                if (compteMot(textAreaDefinition.getText()) <= FabriqueMotSingle.getInstance().getMotMax())
                {
                    if (!textAreaDefinition.getText().isEmpty())
                    {
                        FabriqueMotSingle.getInstance().getMot(motSelectione).setDefinition(textAreaDefinition.getText());
                        FabriqueMotSingle.getInstance().getMot(motSelectione).ajouterDateModification();
                        textAreaDate.appendText("\nDate de modification: " + FabriqueMotSingle.getInstance().getMot(motSelectione).getDateModification());
                    }
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Maximum 20 mots, votre phrase à: " + compteMot(textAreaDefinition.getText()));
                    alert.showAndWait();
                    alert.getButtonTypes().setAll(ButtonType.CLOSE);
                    textAreaDefinition.clear();
                }
            }
        listViewMots.refresh();

    }

    /**
     * Modifie la clé du mot si (son nom), si le nom du mot est changé.
     * @param aChanger
     * @param nomMot
     */
    private void renomerMot(Mot aChanger,String nomMot)
    {
        Mot motTemp = aChanger;
        FabriqueMotSingle.getInstance().removeMot(aChanger.getMot());
        motTemp.setMot(nomMot);

        FabriqueMotSingle.getInstance().putMot(motTemp.getMot(), motTemp);
    }

    /**
     * Supprime un mot.
     * @param event
     */
    @FXML
    void supprimerMot(ActionEvent event)
    {
        String motSuprime = listViewMots.getSelectionModel().getSelectedItem();
        observableListMots.remove(motSuprime);
        FabriqueMotSingle.getInstance().removeMot(motSuprime);
        listViewMots.getItems().remove(motSuprime);
        listViewMots.refresh();

        reinitializeView();
    }

    /**
     * Affiche la deuxième fenetre (Filtre)
     * @param event
     */
    @FXML
    void fenetreFiltre(ActionEvent event)
    {
        FXMLLoader fxmlLoaderSecond = new FXMLLoader(this.getClass().getResource("secondary.fxml"));
        Stage stage = new Stage();
        VBox fenetreFiltre;
        try
        {
            fenetreFiltre = (VBox) fxmlLoaderSecond.load();
            Scene scene = new Scene(fenetreFiltre);
            stage.setScene(scene);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        SecondaryController secondController = fxmlLoaderSecond.getController();
        secondController.setStage(stage);
        stage.show();
        stage.setOnCloseRequest(event1 ->
        {
            if (secondController.getFiltreParam() != null )
            {
                filtreParam = secondController.getFiltreParam();

                if (filtreParam != null && filtreParam.size() != 0)
                {
                    filtreListView();
                    stage.close();
                }
            }
            else
                {
                    stage.close();
                    filtreParam = null;
                    initListView();
                }
        });
    }

    /**
     * Initialise la fenetre principale au lancement de l'application
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        initListView();
        initTextField();
        imageDragDrop(imageViewMot);
    }

    /**
     *  Initialise la liste de mot de la fenetre principale à partir d'un dictionaire de mot.
     */
    private void initListView()
    {
        observableListMots.addAll(FabriqueMotSingle.getInstance().getDictionaire().keySet());
        listViewMots.setItems(observableListMots);


        listViewMots.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
        {
            public void changed(ObservableValue<? extends String> ov, String old_val, String new_val)
            {
                imageViewMot.setImage(null);
                //Obtien le mot selectioné dans ListView.
                String motSelectione = listViewMots.getSelectionModel().getSelectedItem();

                //Affiche les mot et la definition;
                textAreaMot.setText(FabriqueMotSingle.getInstance().getMot(motSelectione).getMot());
                textAreaDefinition.setText(FabriqueMotSingle.getInstance().getMot(motSelectione).getDefinition());
                textAreaDate.setText("Date de saisie: "+FabriqueMotSingle.getInstance().getMot(motSelectione).getDateSaisie().toString());
                if (FabriqueMotSingle.getInstance().getMot(motSelectione).getDateModification()!=null)
                {
                    textAreaDate.appendText("\nDate de modification: " + FabriqueMotSingle.getInstance().getMot(motSelectione).getDateModification().toString());
                }
                if (!FabriqueMotSingle.getInstance().getMot(motSelectione).getNomImage().isEmpty())
                {
                    imageViewMot.setImage(new Image(FabriqueMotSingle.getInstance().getMot(motSelectione).getNomImage()));
                }
                listViewMots.refresh();
            }
        });
    }


    /**
     * Initialise la barre de recherche et ajoute un ecouteur
     * qui met a jour la liste selon les caractère entrés.
     */
    private void initTextField()
    {
        //ajoute un listener sur le textField qui appelle recherche.
        textFieldRecherche.textProperty().addListener(new ChangeListener()
        {
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                recherche((String) oldValue, (String) newValue);
            }
        });
    }

    private void recherche(String oldValue, String newValue)
    {
        if (oldValue != null && (newValue.length() < oldValue.length()) || newValue == "")
        {
            listViewMots.setItems(observableListMots);
        }
        else if (newValue.length() > oldValue.length())
        {
            String valeur = newValue.toUpperCase();
            listViewMots.setItems(Recherche.filtre(valeur,listViewMots));
        }
        if (!newValue.isEmpty() && newValue.length() < oldValue.length() && oldValue.contains(newValue))
        {
            String valeur = newValue.toUpperCase();
            listViewMots.setItems(Recherche.filtre(valeur,listViewMots));
        }

    }

    /**
     * Effectue le tri nécessaire selon les filtres choisi dans la fenetre filtre.
     */
    private void filtreListView()
    {
        ObservableList<String> observableListMotFiltre = FXCollections.observableArrayList();
        ArrayList<Mot> tempMotsDate = null;
        ArrayList<Mot> tempMotsImg = null;
        LocalDate date;
        int img;

        if (filtreParam.containsKey("dateSelectionne"))
        {
            date = LocalDate.parse(filtreParam.get("dateSelectionne"));
            tempMotsDate = filtreDate(date);
        }
        if (filtreParam.containsKey("imagePresente"))
        {
            img = Integer.parseInt(filtreParam.get("imagePresente"));
            tempMotsImg = filtreImg(img);
        }

        if (tempMotsDate != null && tempMotsImg != null)
        {
            tempMotsDate.retainAll(tempMotsImg);

            for (Mot mot:tempMotsDate)
            {
                observableListMotFiltre.add(mot.getMot());
            }
            listViewMots.setItems(observableListMotFiltre);
        }
        else if (tempMotsDate == null && tempMotsImg != null)
        {
            for (Mot mot:tempMotsImg)
            {
                observableListMotFiltre.add(mot.getMot());
            }
            listViewMots.setItems(observableListMotFiltre);
        }
        else if (tempMotsDate != null && tempMotsImg == null)
        {
            for (Mot mot:tempMotsDate)
            {
                observableListMotFiltre.add(mot.getMot());
            }
            listViewMots.setItems(observableListMotFiltre);
        }
        else
            {
                listViewMots.setItems(observableListMots);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("AUCUN MOTS NE CORRESPOND A VOTRE FILTRE");
                alert.showAndWait();
            }
    }

    /**
     * Construit un array de mots a partir du dictionnaire selon la date.
     * @param date date du filtre
     * @return Array de mots
     */
    private ArrayList<Mot> filtreDate(LocalDate date)
    {
        ArrayList<Mot> tempMots = new ArrayList<>();
        Iterator<Map.Entry<String, Mot>> iterator = FabriqueMotSingle.getInstance().getDictionaire().entrySet().iterator();
        while (iterator.hasNext())
        {
            Map.Entry<String,Mot> stringMotEntry = iterator.next();
            if (stringMotEntry.getValue().getDateModification() != null)
            {
                if (!stringMotEntry.getValue().getDateModification().isAfter(date))
                {
                    tempMots.add(stringMotEntry.getValue());
                }
            }
        }
        return tempMots;
    }

    /**
     * Construit un array de mots a partir du dictionnaire selon image présente.
     * @param img image présente.
     * @return Array de mots.
     */
    private ArrayList<Mot> filtreImg(int img)
    {
        ArrayList<Mot> tempMots = new ArrayList<>();
        Iterator<Map.Entry<String, Mot>> iterator = FabriqueMotSingle.getInstance().getDictionaire().entrySet().iterator();
        while (iterator.hasNext())
        {
            Map.Entry<String,Mot> stringMotEntry = iterator.next();
            if (!stringMotEntry.getValue().getNomImage().isEmpty())
            {
                tempMots.add(stringMotEntry.getValue());
            }
        }
        return tempMots;
    }


    /**
     * Initialise le DRAG n' Drop sur un image View.
     * L'orsque qu'une image est ajouté, on enregistre sont path, pour affichage futur.
     * @param imageViewcible ImageView de la fenetre principale.
     */
    private void imageDragDrop(final ImageView imageViewcible)
    {
        imageViewcible.setOnDragOver((DragEvent event) ->
        {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasFiles())
            {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });
        imageViewcible.setOnDragDropped((DragEvent e) ->
        {
            Dragboard dragboard = e.getDragboard();

            if (dragboard.hasFiles())
            {
                for (File file : dragboard.getFiles())
                {
                    String absolutePath = null;
                    try
                    {
                        absolutePath = file.toURI().toURL().toString();
                    } catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                    Image dbimage = new Image(absolutePath);
                    imageViewMot.setImage(dbimage);
                    //Ajoute l'url de l'image au Mot.
                    String motAjouteImage = listViewMots.getSelectionModel().getSelectedItem();
                    FabriqueMotSingle.getInstance().getMot(motAjouteImage).setNomImage(absolutePath);
                }
                e.setDropCompleted(true);
            }
            else
            {
                e.setDropCompleted(false);
            }
            e.consume();
        });
    }

    /**
     * Efface tout les champ modifiable de la vue.
     */
    private void reinitializeView()
    {
        textFieldRecherche.clear();
        textAreaDefinition.clear();
        textAreaDate.clear();
        textAreaMot.clear();
    }


    /**
     * Quitte le programme.
     * @param event
     */
    @FXML
    public void fermer(ActionEvent event)
    {
        Platform.exit();
        System.exit(0);
    }

    public int compteMot(String phrase)
    {
        StringTokenizer tokens = new StringTokenizer(phrase);
        return tokens.countTokens();
    }

}
