package Models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HighScoresTable {

    private static HighScoresTable instance = null;
    private static String easyTable = "EasyModeHighScores.txt";
    private static String mediumTable = "MediumModeHighScores.txt";
    private static String hardTable = "HardModeHighScores.txt";
    private static String tableType;


    public static HighScoresTable getInstance() {
        if (instance == null) {
            instance = new HighScoresTable();
        }
        return instance;
    }

    /**
     * set the high scores table type
     * @param type- level difficulty (easy, medium or hard)
     */
    public void setHighScoresType(String type) {
        tableType = type;
    }

    public static List<Player> loadHighScores() {
        List<Player> highScores = new ArrayList<>();
        switch (tableType) {
            case "easy": {
                tableType = easyTable;
                break;
            }
            case "medium": {
                tableType = mediumTable;
                break;
            }
            case "hard": {
                tableType = hardTable;
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + tableType);
        }

        try {
            URL path = HighScoresTable.class.getResource(tableType);
            File f = new File(path.getFile());
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitLine = line.split(",");
                highScores.add(new Player(splitLine[0], splitLine[1]));
            }
            reader.close();
            return highScores;
        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", tableType);
            e.printStackTrace();
            return null;
        }
    }

    //TODO: implementation
    public static void addToHighScores(String playerName, String gameEndingTime) {
        String levelDifficulty = MyModel.getInstance().getLevelDifficulty();
        String fileName = "";
        switch (levelDifficulty) {
            case "easy": {
                fileName = easyTable;
                break;
            }
            case "medium": {
                fileName = mediumTable;
                break;
            }
            case "hard": {
                fileName = hardTable;
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + tableType);
        }
    }
}
