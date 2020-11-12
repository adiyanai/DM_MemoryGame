package Controllers;

import Models.MyModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.Objects;

// PlayerNameController is in charge of the screen that asks the user to enter his name.
public class PlayerNameController {

    public Button continueButton1;

    private MyModel m;

    @FXML
    private TextField playerName;

    @FXML
    private GridPane PlayerNamePane;

    @FXML
    private Button exitButton;

    @FXML
    private Button continueButton;

    @FXML
    private Button backButton;


    public void enterYourName(ActionEvent event)throws IOException{
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent gameModePage = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Views/GameModeView.fxml")));
        Scene scene = new Scene(gameModePage);
        playerName.setText("Enter your Name");
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("StyleSheet.css")).toExternalForm());
        window.setScene(scene);
        window.show();
    }

    public void pressContinue(ActionEvent event) throws IOException{
        m = MyModel.getInstance();
        m.setPlayerName(playerName.getText());
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent gameModePage = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Views/GameModeView.fxml")));
        Scene scene = new Scene(gameModePage);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("StyleSheet.css")).toExternalForm());
        window.setScene(scene);
    }

    public void pressBack(ActionEvent event) throws IOException {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent mainView = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Views/MainView.fxml")));
        Scene scene = new Scene(mainView);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("StyleSheet.css")).toExternalForm());
        window.setScene(scene);
    }


    public void pressExit(ActionEvent event) {
        MyModel.getInstance().close();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
