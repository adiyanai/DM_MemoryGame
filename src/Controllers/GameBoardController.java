package Controllers;
import Views.GamePane;
import Models.MyModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.animation.*;
import javafx.util.Duration;

// GameBoardController is in charge of the board on which the game cards appear.
public class GameBoardController {
    private BorderPane pane = new BorderPane();
    private MyModel m;
    private Button btStop = new Button("Stop");
    private Timeline timer;
    private Integer seconds = 0;
    private Label timeLabel;
    private static Label levelLabel;
    private static Integer levelNumber = 0;
    private Label levelDifficultyLabel;
    private Integer numOfCouples;

    public GameBoardController(Stage window) {
        m = MyModel.getInstance();
        HBox infoBox = new HBox(20);
        infoBox.setAlignment(Pos.CENTER);
        levelLabel = new Label();
        increaseLevelNumber();
        levelDifficultyLabel = new Label();
        setLevelDifficulty();
        startTimer();
        HBox.setMargin(timeLabel, new Insets(20, 20, 20, 20));
        infoBox.getChildren().addAll(levelDifficultyLabel, levelLabel, timeLabel);
        runTheGame(window, infoBox);
    }

    // Start running the game.
    private void runTheGame(Stage window, HBox infoBox) {
        GamePane gamePane = new GamePane(this, pane, numOfCouples, window);
        pane.setTop(infoBox);
        pane.setCenter(gamePane);
        Scene s = new Scene(pane);
        window.setScene(s);
        window.show();
    }

    // Start running the time counter.
    private void startTimer() {
        timeLabel = new Label();
        timeLabel.setFont(Font.font("Cooper Black", 20));
        timeLabel.setText("Time Passed: " + seconds.toString());
        timer = new Timeline();
        timer.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(1), event -> {
            seconds++;
            timeLabel.setText("Time Passed: " + seconds.toString());
        });
        timer.getKeyFrames().add(frame);
        timer.playFromStart();
    }

    // Increase the number of the current level by 1.
    public void increaseLevelNumber() {
        levelNumber++;
        levelLabel.setFont(Font.font("Cooper Black", 20));
        levelLabel.setText("Level: " + levelNumber.toString());
    }

    /**
     * Check whether the game ended - if yes - perform the operations of the ending of the game.
     * Return true if the game ended, false otherwise.
     */
    public boolean isGameEnded()
    {
        if (levelNumber == 6) {
            m.setGameEndingTime(seconds);
            m.addToHighScores();
            levelNumber = 0;
            return true;
        } else {
            return false;
        }
    }

    // Set the difficulty of the current level.
    private void setLevelDifficulty() {
        levelDifficultyLabel.setFont(Font.font("Cooper Black", 20));
        String levelDifficulty =  m.getLevelDifficulty();
        levelDifficultyLabel.setText("Level Difficulty: " + levelDifficulty);
        switch (levelDifficulty) {
            case "easy": {
                numOfCouples = 4;
                break;
            }
            case "medium": {
                numOfCouples = 5;
                break;
            }
            case "hard": {
                numOfCouples = 6;
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + levelDifficulty);
        }
    }
}
