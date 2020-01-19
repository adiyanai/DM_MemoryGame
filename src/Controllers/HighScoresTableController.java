package Controllers;

import Models.MyModel;
import Models.Player;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HighScoresTableController implements Initializable {

    public Button exit;

    public GridPane highScoresPage;

    private MyModel m;

    private List<Player> highScores = null;

    @FXML
    Button prevWindowButton;

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
        Parent mainView = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Views/HighScoresMenuView.fxml")));
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
