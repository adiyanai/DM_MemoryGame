import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import Models.MyModel;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;

public class Main extends Application {
    Stage window;
    MyModel m;

    public final int screenWidth = 600;
    public final int screenHeight = 400;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Connect the database
        this.m = MyModel.getInstance();
        this.m.connect();
        /*List<Pair<String, String>> list = m.getCardsData();
        for (Pair<String, String> stringStringPair : list) {
            System.out.println(stringStringPair.toString());
        }*/
        this.window = primaryStage;
        this.window.setTitle("Memory Game");
        // main page
        Parent root = FXMLLoader.load(getClass().getResource("Views/MainView.fxml"));
        Scene scene = new Scene(root, screenWidth, screenHeight);
        this.window.setMinWidth(scene.getWidth());
        this.window.setMinHeight(scene.getHeight());
        this.window.setResizable(false);
        this.window.setScene(scene);
        this.window.show();

        // כנראה ליצור מסמך של הנראות של העמוד הראשי שלנו כדאי לראות אצל בת דודה שלי מה יש במסמך הזה
        //scene.getStylesheets().add(getClass().getClassLoader().getResource("StyleSheet.css").toExternalForm());
    }

    @Override
    public void stop() throws Exception {
        this.m.close();
        super.stop();
    }
}
