import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Models.MyModel;
import javafx.util.Pair;
import java.util.List;

public class Main extends Application {
    public final int screenWidth = 600;
    public final int screenHeight = 600;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Connect the database
        MyModel m = MyModel.getInstance();
        m.connect();
        List<Pair<String, String>> list = m.getCardsData("easy", 0);
        for (Pair<String, String> stringStringPair : list) {
            System.out.println(stringStringPair.toString());
        }
        //כל מה שפה מתחת זה דברים שראיתי שבת דודה שלי עשתה נצטרך ליצור קבצים בהתאם כמובן ולשנות שמות אם צריך (שיניתי כבר חלק)

        // need to create main page (create Views/mainView.fxml)
        //Parent mainView;
        //Scene scene = new Scene(mainView, screenWidth, screenHeight);
        // כנראה ליצור מסמך של הנראות של העמוד הראשי שלנו כדאי לראות אצל בת דודה שלי מה יש במסמך הזה
        //scene.getStylesheets().add(getClass().getClassLoader().getResource("StyleSheet.css").toExternalForm());
        //primaryStage.setResizable(false);
        //primaryStage.setTitle("Memory Game");
        //primaryStage.setMinWidth(scene.getWidth());
        //primaryStage.setMinHeight(scene.getHeight());
        //primaryStage.setScene(scene);
        //primaryStage.show();
    }
}
