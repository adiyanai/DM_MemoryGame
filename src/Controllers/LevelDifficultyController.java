package Controllers;

import Models.MyModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LevelDifficultyController {

    private MyModel m;

    public void startTheGame(ActionEvent event) {
        m = MyModel.getInstance();
        String difficulty = ((Button)event.getSource()).getId();
        m.setLevelDifficulty(difficulty);
        // game board page
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        new GameBoardController(window);
    }

    public void pressBack(ActionEvent event) throws IOException {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent mainView = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Views/GameModeView.fxml")));
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
