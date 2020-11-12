package Controllers;

import Models.MyModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

// MainController is in charge of the screen of the main menu.
public class MainController {

    @FXML
    private Pane topPane;

    @FXML
    private Button highScoresButton;

    @FXML
    private Button startButton;

    @FXML
    private GridPane mainPage;

    @FXML
    private Button exitButton;

    @FXML
    private Button xButton;

    public void pressStart(ActionEvent event) throws IOException {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent playerNamePage = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Views/PlayerNameView.fxml")));
        Scene scene = new Scene(playerNamePage);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("StyleSheet.css")).toExternalForm());
        window.setScene(scene);
    }

    public void pressHighScores(ActionEvent event) throws IOException {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent highScoresPage = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Views/HighScoresMenuView.fxml")));
        Scene scene = new Scene(highScoresPage);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("StyleSheet.css")).toExternalForm());
        window.setScene(scene);
    }

    public void pressExit(ActionEvent event) {
        MyModel.getInstance().close();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

}
