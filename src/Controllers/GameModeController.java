package Controllers;

import Models.MyModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;

/**
 * GameModeController is in charge of the screen that asks the user to choose the musical relation according to which
 * the game board will be built.
 */
public class GameModeController {

    private MyModel m;

    public void levelDifficultyPage(ActionEvent event) throws IOException {
        m = MyModel.getInstance();
        String mode = ((Button)event.getSource()).getId();
        switch (mode) {
            case "artistSong": {
                m.setLevelMode(0);
                break;
            }
            case "songAlbum": {
                m.setLevelMode(1);
                break;
            }
            case "artistAlbum": {
                m.setLevelMode(2);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + mode);
        }
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent gameModePage = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Views/LevelDifficultyView.fxml")));
        Scene scene = new Scene(gameModePage);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("StyleSheet.css")).toExternalForm());
        window.setScene(scene);
    }

    public void pressBack(ActionEvent event) throws IOException {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent mainView = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Views/PlayerNameView.fxml")));
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
