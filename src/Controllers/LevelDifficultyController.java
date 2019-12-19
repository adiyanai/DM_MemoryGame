package Controllers;

import Models.MyModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class LevelDifficultyController {

    private MyModel m;

    public void startTheGame(ActionEvent event) {
        m = MyModel.getInstance();
        String difficulty = ((Button)event.getSource()).getId();
        m.setLevelDifficulty(difficulty);
    }
}
