package Controllers;

import Models.MyModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// WinningController is in charge of the winning screen.
public class WinningController {

    public void pressHighScores(ActionEvent event) throws IOException {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent highScoresPage = FXMLLoader.load(getClass().getClassLoader().getResource("Views/HighScoresMenuView.fxml"));
        Scene scene = new Scene(highScoresPage);
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