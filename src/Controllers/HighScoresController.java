package Controllers;

import Models.HighScores;
import Models.MyModel;
import Models.Player;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;


public class HighScoresController implements Initializable {

    private MyModel m;

    private List<Player> highScores = null;

    @FXML
    private Button prevWindowButton;

    @FXML
    TableView<Player> highScoresTable;

    @FXML
    TableColumn<Player, String> nameColumn;

    @FXML
    TableColumn<Player, String> timeColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        m = MyModel.getInstance();
        highScores = m.getHighScores();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        timeColumn.setCellValueFactory(new PropertyValueFactory<>("Time"));

        ObservableList<Player> data = FXCollections.observableArrayList(highScores);

        highScoresTable.setItems(data);

    }


    public void findenig()
    {

    }

    public void pressBack(ActionEvent event) throws IOException {
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
