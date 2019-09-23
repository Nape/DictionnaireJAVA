import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Created by Nadir Pelletier
 * For : CORE_TP1
 * Date : 2019-05-23
 * Time : 17:08
 */

public class App extends Application
{
    private BorderPane root;
    private Scene scene;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("primary.fxml"));
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
