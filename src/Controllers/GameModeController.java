package Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public class GameModeController {

    public void levelDifficultyPage(ActionEvent event) throws IOException {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent gameModePage = FXMLLoader.load(getClass().getClassLoader().getResource("Views/LevelDifficultyView.fxml"));
        Scene scene = new Scene(gameModePage);
        window.setScene(scene);
    }
}
