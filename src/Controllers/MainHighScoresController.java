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

// MainHighScoresController is in charge of the screen of the high scores that can be reached from the main menu.
public class MainHighScoresController {

    private MyModel m;

    public void highScoresPage(ActionEvent event) throws IOException {
        m = MyModel.getInstance();
        String mode = ((Button)event.getSource()).getId();
        m.setHighScoresType(mode);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent gameModePage = FXMLLoader.load(getClass().getClassLoader().getResource("Views/HighScoresTableView.fxml"));
        Scene scene = new Scene(gameModePage);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("StyleSheet.css").toExternalForm());
        window.setScene(scene);
    }

    public void pressMainMenu(ActionEvent event) throws IOException {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent mainView = FXMLLoader.load(getClass().getClassLoader().getResource("Views/MainView.fxml"));
        Scene scene = new Scene(mainView);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("StyleSheet.css").toExternalForm());
        window.setScene(scene);
    }

    public void pressExit(ActionEvent event) {
        MyModel.getInstance().close();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
