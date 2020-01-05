package Models;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HighScores {

    private static HighScores instance = null;
    private static String highScoresFileName = "highScores.txt";

    public static HighScores getInstance() {
        if (instance == null) {
            instance = new HighScores();
        }
        return instance;
    }

    public static List<Player> loadHighScores() {
        List<Player> highScores = new ArrayList<>();
        try
        {
            URL path = HighScores.class.getResource(highScoresFileName);
            File f = new File(path.getFile());
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line;
            while ((line = reader.readLine()) != null)
            {
                String [] splitLine = line.split(",");
                highScores.add(new Player(splitLine[0], splitLine[1]));
            }
            reader.close();
            return highScores;
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", highScoresFileName);
            e.printStackTrace();
            return null;
        }
    }
}
