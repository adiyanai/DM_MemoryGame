import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Models.MyModel;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    Stage window;
    MyModel m;

    public final int screenWidth = 590;
    public final int screenHeight = 390;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Connect the database
        this.m = MyModel.getInstance();
        this.m.connect();
        this.window = primaryStage;
        this.window.setTitle("Perfect Matching");
        // main page
        Parent root = FXMLLoader.load(getClass().getResource("Views/MainView.fxml"));
        Scene scene = new Scene(root, screenWidth, screenHeight);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("StyleSheet.css")).toExternalForm());
        this.window.setMinWidth(scene.getWidth());
        this.window.setMinHeight(scene.getHeight());
        this.window.setResizable(false);
        this.window.setScene(scene);
        this.window.show();
    }

    @Override
    public void stop() throws Exception {
        this.m.close();
        super.stop();
    }
}
