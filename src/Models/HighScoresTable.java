package Models;

import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class HighScoresTable {

    private static HighScoresTable instance = null;
    private String easyTable = "EasyModeHighScores.txt";
    private String mediumTable = "MediumModeHighScores.txt";
    private String hardTable = "HardModeHighScores.txt";
    private String tableType;


    public static HighScoresTable getInstance() {
        if (instance == null) {
            instance = new HighScoresTable();
        }
        return instance;
    }

    /**
     * constructor
     */
    private HighScoresTable() {

    }

        /**
         * set the high scores table type
         * @param type- level difficulty (easy, medium or hard)
         */
    public void setHighScoresType(String type) {
        tableType = type;
    }

    public List<Player> loadHighScores() {
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
            File f = new File(tableType);
            if(!f.exists()) {
                boolean b = f.createNewFile();
                return highScores; }
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
    public void addToHighScores(String playerName, String gameEndingTime) {
        String levelDifficulty = MyModel.getInstance().getLevelDifficulty();
        setHighScoresType(levelDifficulty);
        List<Player> highScores = loadHighScores();
        if (highScores!=null) {
            try {
                File f = new File(tableType);
                BufferedWriter writer = new BufferedWriter(new FileWriter(f));
                Player p = new Player(playerName, gameEndingTime);
                highScores.add(p);
                highScores.sort(Comparator.comparingInt(object -> Integer.parseInt(object.getTime())));
                int stop = 5;
                for(int i=0; i< highScores.size() ; i++) {
                    if (i == stop) {
                        break;
                    }
                    StringJoiner sj = new StringJoiner(",");
                    sj.add(highScores.get(i).getName());
                    sj.add(highScores.get(i).getTime());
                    writer.write(sj.toString());
                    writer.newLine();
                }
                writer.close();
            }
            catch (Exception e) {
                System.out.println("failed");
            }
        }

    }
}
