package Models;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyModel implements Model {

    private Statement stmt;
    private int levelMode;
    private String levelDifficulty;
    private Connection myConn;
    private String playerName1;
    private List<String> easyQueries;
    private List<String> mediumQueries;
    private List<String> hardQueries;
    private static MyModel instance = null;
    private String gameEndingTime;

    public static MyModel getInstance() {
        if (instance == null) {
            instance = new MyModel();
        }
        return instance;
    }

    /**
     * Constructor
     */
    private MyModel() {
        // Queries for the easy levels.
        this.easyQueries = new ArrayList<>();
        // Queries for the medium levels.
        this.mediumQueries = new ArrayList<>();
        // Queries for the hard levels.
        this.hardQueries = new ArrayList<>();
        this.levelDifficulty = "easy";
        this.levelMode = 0;
        this.playerName1 = "";
        initializeQueries();
    }

    /**
     * Initialize queries for all types of difficulty levels (easy, medium, hard).
     */
    public void initializeQueries() {
        // Create queries for the easy levels: (4 pairs)

        // artist - song
        String easyArtistSongQueries =  "With Artists_info As(\n"+
                "Select\n"+
                "ArtistName, Title,\n"+
                "Avg(Year) as Average_years, count(SongID) as Count_songs\n"+
                "From artists as AR\n"+
                "Inner join albums_artists AA\n"+
                "On AR.ArtistID=AA.ArtistID\n"+
                "Inner Join albums AL\n"+
                "On AA.AlbumID=AL.AlbumID\n"+
                "Inner join songs S\n"+
                "On AL.AlbumID=S.AlbumID\n"+
                "Group By AR.ArtistID)\n"+
                "\n" +
                "Select distinct ArtistName,Title From Artists_info \n" +
                "Where (Average_Years > 2004 or Count_songs>= 4) and length(Artists_info.ArtistName)<20 and length(Title)<20\n" +
                "ORDER BY RAND() LIMIT 4;";

        // song - album
        String easySongAlbumQueries =   "With Artists_info As(\n"+
                "Select\n"+
                "Title,AL.AlbumName,\n"+
                "Avg(Year) as Average_years, count(SongID) as Count_songs\n"+
                "From artists as AR\n"+
                "Inner join albums_artists AA\n"+
                "On AR.ArtistID=AA.ArtistID\n"+
                "Inner Join albums AL\n"+
                "On AA.AlbumID=AL.AlbumID\n"+
                "Inner join songs S\n"+
                "On AL.AlbumID=S.AlbumID\n"+
                "Group By AR.ArtistID)\n"+
                "\n" +
                "Select distinct Title, AlbumName From Artists_info\n" +
                "Where (Average_Years > 2004 or Count_songs >=4) and length(Title)<20 and length(AlbumName)<20" +
                " and Title not LIKE AlbumName\n" +
                "ORDER BY RAND() LIMIT 4;";

        // artist - album
        String easyArtistAlbumQueries = "With Artists_info As(\n"+
                "Select\n"+
                "ArtistName, AL.AlbumName,\n"+
                "Avg(Year) as Average_years, count(SongID) as Count_songs\n"+
                "From artists as AR\n"+
                "Inner join albums_artists AA\n"+
                "On AR.ArtistID=AA.ArtistID\n"+
                "Inner Join albums AL\n"+
                "On AA.AlbumID=AL.AlbumID\n"+
                "Inner join songs S\n"+
                "On AL.AlbumID=S.AlbumID\n"+
                "Group By AR.ArtistID)\n"+
                "\n" +
                "Select distinct ArtistName, AlbumName From Artists_info\n" +
                "Where (Average_Years > 2004 or Count_songs>= 4) and length(Artists_info.ArtistName)<20 " +
                "and length(AlbumName)<20\n" +
                "ORDER BY RAND() LIMIT 4;";

        this.easyQueries.add(easyArtistSongQueries);
        this.easyQueries.add(easySongAlbumQueries);
        this.easyQueries.add(easyArtistAlbumQueries);

        // Create queries for the medium levels: (5 pairs)

        // artist - song
        String mediumArtistSongQueries ="With Artists_info As(\n"+
                "Select\n"+
                "ArtistName, Title,\n"+
                "Avg(Year) as Average_years, count(SongID) as Count_songs\n"+
                "From artists as AR\n"+
                "Inner join albums_artists AA\n"+
                "On AR.ArtistID=AA.ArtistID\n"+
                "Inner Join albums AL\n"+
                "On AA.AlbumID=AL.AlbumID\n"+
                "Inner join songs S\n"+
                "On AL.AlbumID=S.AlbumID\n"+
                "Group By AR.ArtistID)\n"+
                "\n" +
                "Select distinct ArtistName, Title From Artists_info\n" +
                "Where (Average_Years < 2002 or Count_songs < 3) " +
                "and length(Artists_info.ArtistName)<20 and length(Title)<20\n" +
                "ORDER BY RAND() LIMIT 5;";

        // song - album
        String mediumSongAlbumQueries ="With Artists_info As(\n"+
                "Select\n"+
                "Title,AL.AlbumName,\n"+
                "Avg(Year) as Average_years, count(SongID) as Count_songs\n"+
                "From artists as AR\n"+
                "Inner join albums_artists AA\n"+
                "On AR.ArtistID=AA.ArtistID\n"+
                "Inner Join albums AL\n"+
                "On AA.AlbumID=AL.AlbumID\n"+
                "Inner join songs S\n"+
                "On AL.AlbumID=S.AlbumID\n"+
                "Group By AR.ArtistID)\n"+
                "\n" +
                "Select distinct  Title, AlbumName From Artists_info\n" +
                "Where (Average_Years < 2002 or Count_songs < 3) and length(Title)<20 and length(AlbumName)<20" +
                " and Title not LIKE AlbumName\n" +
                "ORDER BY RAND() LIMIT 5;";

        // artist - album
        String mediumArtistAlbumQueries = "With Artists_info As(\n"+
                "Select\n"+
                "ArtistName,AL.AlbumName,\n"+
                "Avg(Year) as Average_years, count(SongID) as Count_songs\n"+
                "From artists as AR\n"+
                "Inner join albums_artists AA\n"+
                "On AR.ArtistID=AA.ArtistID\n"+
                "Inner Join albums AL\n"+
                "On AA.AlbumID=AL.AlbumID\n"+
                "Inner join songs S\n"+
                "On AL.AlbumID=S.AlbumID\n"+
                "Group By AR.ArtistID)\n"+
                "\n" +
                "Select distinct ArtistName, AlbumName From Artists_info\n" +
                "Where (Average_Years < 2002 or Count_songs < 3) and length(Artists_info.ArtistName)<20 and length(AlbumName)<20\n" +
                "ORDER BY RAND() LIMIT 5;";
        this.mediumQueries.add(mediumArtistSongQueries);
        this.mediumQueries.add(mediumSongAlbumQueries);
        this.mediumQueries.add(mediumArtistAlbumQueries);

        // Create queries for the hard levels: (6 pairs)

        // artist - song
        String hardArtistSongQueries = "With Artists_info As(\n"+
                "Select\n"+
                "ArtistName, Title,\n"+
                "Avg(Year) as Average_years, count(SongID) as Count_songs\n"+
                "From artists as AR\n"+
                "Inner join albums_artists AA\n"+
                "On AR.ArtistID=AA.ArtistID\n"+
                "Inner Join albums AL\n"+
                "On AA.AlbumID=AL.AlbumID\n"+
                "Inner join songs S\n"+
                "On AL.AlbumID=S.AlbumID\n"+
                "Group By AR.ArtistID)\n"+
                "\n" +
                "Select distinct ArtistName, Title From Artists_info\n" +
                "Where (Average_Years < 2000 and Count_songs <= 2) and length(Artists_info.ArtistName)<20 and length(Title)<20\n" +
                "ORDER BY RAND() LIMIT 6;";

        // song - album
        String hardSongAlbumQueries = "With Artists_info As(\n"+
                "Select\n"+
                "Title,AL.AlbumName,\n"+
                "Avg(Year) as Average_years, count(SongID) as Count_songs\n"+
                "From artists as AR\n"+
                "Inner join albums_artists AA\n"+
                "On AR.ArtistID=AA.ArtistID\n"+
                "Inner Join albums AL\n"+
                "On AA.AlbumID=AL.AlbumID\n"+
                "Inner join songs S\n"+
                "On AL.AlbumID=S.AlbumID\n"+
                "Group By AR.ArtistID)\n"+
                "\n" +
                "Select distinct Title, AlbumName From Artists_info\n" +
                "Where (Average_Years < 2000 and Count_songs <= 2) and length(Title)<20 and length(AlbumName)<20" +
                " and Title not LIKE AlbumName\n" +
                "ORDER BY RAND() LIMIT 6;";

        // artist - album
        String hardArtistAlbumQueries = "With Artists_info As(\n"+
                "Select\n"+
                "ArtistName,AL.AlbumName,\n"+
                "Avg(Year) as Average_years, count(SongID) as Count_songs\n"+
                "From artists as AR\n"+
                "Inner join albums_artists AA\n"+
                "On AR.ArtistID=AA.ArtistID\n"+
                "Inner Join albums AL\n"+
                "On AA.AlbumID=AL.AlbumID\n"+
                "Inner join songs S\n"+
                "On AL.AlbumID=S.AlbumID\n"+
                "Group By AR.ArtistID)\n"+
                "\n" +
                "Select distinct ArtistName, AlbumName From Artists_info\n" +
                "Where (Average_Years < 2000 and Count_songs <= 2) and length(Artists_info.ArtistName)<20 and length(AlbumName)<20\n" +
                "ORDER BY RAND() LIMIT 6;";
        this.hardQueries.add(hardArtistSongQueries);
        this.hardQueries.add(hardSongAlbumQueries);
        this.hardQueries.add(hardArtistAlbumQueries);
    }

    /**
     * Set the level mode.
     * @param m - level mode (0 = artist - song, 1 = song - album, 2 = artist - album).
     */
    public void setLevelMode(int m) {
        this.levelMode = m;
    }

    /**
     * @return the level mode (0 = artist - song, 1 = song - album, 2 = artist - album).
     */
    public int getLevelMode() {
        return this.levelMode;
    }

    /**
     * Set the level difficulty.
     * @param d - level difficulty (easy, medium or hard).
     */
    public void setLevelDifficulty(String d) {
        this.levelDifficulty = d;
    }

    /**
     * @return the level difficulty.
     */
    public String getLevelDifficulty() {
        return this.levelDifficulty;
    }

    /**
     * Set the highscores table type.
     * @param type- level difficulty (easy, medium or hard).
     */
    public void setHighScoresType(String type) {
        HighScoresTable highScoresTable = HighScoresTable.getInstance();
        highScoresTable.setHighScoresType(type);
    }

    /**
     * @return the player name.
     */
    public String getPlayerName(){return this.playerName1;}

    /**
     * Set the player name.
     * @param name - player name.
     */
    public void setPlayerName(String name) {
        this.playerName1 = name;
    }

    /**
     * Connect to MySQL.
     */
    public void connect() {
        try {
            final String user = "team02";
            final String password = "dbms5";
            final String scheme = "db02";
            this.myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+scheme+"?allowPublicKeyRetrieval=true&useSSL=false", user, password);
            // create statement to execute queries
            this.stmt = this.myConn.createStatement();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the connection.
     */
    public void close() {
        try {
            this.stmt.close();
            this.myConn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create and return pairs of cards of the game.
     * @return pairs of cards of the game.
     */
    public List<Pair<String, String>> getCardsData() {
        List<Pair<String, String>> cards = new ArrayList<>();
        String query;
        ResultSet rs;
        switch (this.levelDifficulty) {
            case "easy": {
                query = this.easyQueries.get(this.levelMode);
                break;
            }
            case "medium": {
                query = this.mediumQueries.get(this.levelMode);
                break;
            }
            case "hard": {
                query = this.hardQueries.get(this.levelMode);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + levelDifficulty);
        }

        try {
            rs = this.stmt.executeQuery(query);
            while (rs.next()) {
                cards.add(getPairOfCards(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cards;
    }

    /**
     * Create and return pair of cards.
     * @param rs - raw in the table.
     * @return pair of cards.
     * @throws SQLException - throw SQL exception.
     */
    public Pair<String, String> getPairOfCards(ResultSet rs) throws SQLException {
        Pair<String, String> cardsPair;
        String card1, card2;

        switch (this.levelMode) {
            case 0:
                card1 = rs.getString("ArtistName");
                card2 = rs.getString("Title");
                break;
            case 1:
                card1 = rs.getString("Title");
                card2 = rs.getString("AlbumName");
                break;
            case 2:
                card1 = rs.getString("ArtistName");
                card2 = rs.getString("AlbumName");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + this.levelMode);
        }

        cardsPair = new Pair<>(card1, card2);
        return cardsPair;
    }

    /**
     * @return a list of players.
     */
    public List<Player> getHighScores() {
        HighScoresTable highScoresTable = HighScoresTable.getInstance();
        return highScoresTable.loadHighScores();
    }

    /**
     * @param endingTime - the ending time of the player.
     */
    public void setGameEndingTime(Integer endingTime) {
        this.gameEndingTime = endingTime.toString();
    }

    /**
     *  Add the player to the high scores table in a case he is one of the TOP-5 players.
     */
    public void addToHighScores() {
        String playerName = playerName1;
        HighScoresTable highScoresTable = HighScoresTable.getInstance();
        highScoresTable.addToHighScores(playerName, gameEndingTime);
    }
}