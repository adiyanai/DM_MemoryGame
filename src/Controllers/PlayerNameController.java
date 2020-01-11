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

public class PlayerNameController {

    @FXML
    private TextField playerName;

    @FXML
    private GridPane PlayerName;

    @FXML
    private Button exitButton;

    @FXML
    private Button continueButton;

    public void enterYourName(ActionEvent event)throws IOException{
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent gameModePage = FXMLLoader.load(getClass().getClassLoader().getResource("Views/GameModeView.fxml"));
        Scene scene = new Scene(gameModePage);
        playerName.setText("Enter your Name");
        scene.getStylesheets().add(getClass().getClassLoader().getResource("StyleSheet.css").toExternalForm());
        window.setScene(scene);
        window.show();
    }

    public void pressContinue(ActionEvent event) throws IOException {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent gameModePage = FXMLLoader.load(getClass().getClassLoader().getResource("Views/GameModeView.fxml"));
        Scene scene = new Scene(gameModePage);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("StyleSheet.css").toExternalForm());
        window.setScene(scene);
    }

    public void pressExit(ActionEvent event) {
        MyModel.getInstance().close();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
