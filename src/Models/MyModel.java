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
     * constructor
     */
    private MyModel() {
        // queries for the easy levels
        this.easyQueries = new ArrayList<>();
        // queries for the medium levels
        this.mediumQueries = new ArrayList<>();
        // queries for the hard levels
        this.hardQueries = new ArrayList<>();
        this.levelDifficulty = "easy";
        this.levelMode = 0;
        this.playerName1 = "";
        initializeQueries();
    }

    /**
     * initialize queries to all the levels types (easy, medium, hard)
     */
    public void initializeQueries() {
        // create queries for the easy levels: (4 pairs)
        // artist - song
        String easyArtistSongQueries = "With Artists_info As(\n" +
                "Select \n" +
                "ArtistName, AR.ArtistID, Title, Year,\n" +
                "Avg(Year) as Average_years\n" +
                "From artists as AR\n" +
                "Inner join albums_artists AA\n" +
                "On AR.ArtistID=AA.ArtistID\n" +
                "Inner Join albums AL\n" +
                "On AA.AlbumID=AL.AlbumID\n" +
                "Inner join songs S\n" +
                "On AL.AlbumID=S.AlbumID\n" +
                "Group By AR.ArtistID,ArtistName),\n" +
                "\n" +
                "Artists_info2 As(\n" +
                "Select \n" +
                "ArtistName, AR.ArtistID,\n" +
                "count(SongID) as Count_songs\n" +
                "From artists as AR\n" +
                "Inner join albums_artists AA\n" +
                "On AR.ArtistID=AA.ArtistID\n" +
                "Inner Join albums AL\n" +
                "On AA.AlbumID=AL.AlbumID\n" +
                "Inner join songs S\n" +
                "On AL.AlbumID=S.AlbumID\n" +
                "Group By AR.ArtistID,ArtistName)\n" +
                "\n" +
                "Select distinct Artists_info.ArtistName,Title From Artists_info inner join Artists_info2\n" +
                "on Artists_info.ArtistID = Artists_info2.ArtistID\n" +
                "Where (Average_Years > 2004 or Count_songs>= 4) and length(Artists_info.ArtistName)<20 and length(Title)<20\n" +
                "ORDER BY RAND() LIMIT 4;";
        // song - album
        String easySongAlbumQueries = "With Artists_info As(\n" +
                "Select \n" +
                "ArtistName, AR.ArtistID, Title, Year,AL.AlbumName,\n" +
                "Avg(Year) as Average_years\n" +
                "From artists as AR\n" +
                "Inner join albums_artists AA\n" +
                "On AR.ArtistID=AA.ArtistID\n" +
                "Inner Join albums AL\n" +
                "On AA.AlbumID=AL.AlbumID\n" +
                "Inner join songs S\n" +
                "On AL.AlbumID=S.AlbumID\n" +
                "Group By AR.ArtistID,ArtistName),\n" +
                "\n" +
                "Artists_info2 As(\n" +
                "Select \n" +
                "ArtistName, AR.ArtistID,\n" +
                "count(SongID) as Count_songs\n" +
                "From artists as AR\n" +
                "Inner join albums_artists AA\n" +
                "On AR.ArtistID=AA.ArtistID\n" +
                "Inner Join albums AL\n" +
                "On AA.AlbumID=AL.AlbumID\n" +
                "Inner join songs S\n" +
                "On AL.AlbumID=S.AlbumID\n" +
                "Group By AR.ArtistID,ArtistName)\n" +
                "\n" +
                "Select distinct Title, AlbumName From Artists_info inner join Artists_info2\n" +
                "on Artists_info.ArtistID = Artists_info2.ArtistID\n" +
                "Where (Average_Years > 2004 or Count_songs >=4) and length(Title)<20 and length(AlbumName)<20" +
                " and Title not LIKE AlbumName\n" +
                "ORDER BY RAND() LIMIT 4;";
        // artist - album
        String easyArtistAlbumQueries = "With Artists_info As(\n" +
                "Select \n" +
                "ArtistName, AR.ArtistID, Title, Year,AL.AlbumName,\n" +
                "Avg(Year) as Average_years\n" +
                "From artists as AR\n" +
                "Inner join albums_artists AA\n" +
                "On AR.ArtistID=AA.ArtistID\n" +
                "Inner Join albums AL\n" +
                "On AA.AlbumID=AL.AlbumID\n" +
                "Inner join songs S\n" +
                "On AL.AlbumID=S.AlbumID\n" +
                "Group By AR.ArtistID,ArtistName),\n" +
                "\n" +
                "Artists_info2 As(\n" +
                "Select \n" +
                "ArtistName, AR.ArtistID,\n" +
                "count(SongID) as Count_songs\n" +
                "From artists as AR\n" +
                "Inner join albums_artists AA\n" +
                "On AR.ArtistID=AA.ArtistID\n" +
                "Inner Join albums AL\n" +
                "On AA.AlbumID=AL.AlbumID\n" +
                "Inner join songs S\n" +
                "On AL.AlbumID=S.AlbumID\n" +
                "Group By AR.ArtistID,ArtistName)\n" +
                "\n" +
                "Select distinct Artists_info.ArtistName, AlbumName From Artists_info inner join Artists_info2\n" +
                "on Artists_info.ArtistID = Artists_info2.ArtistID\n" +
                "Where (Average_Years > 2004 or Count_songs>= 4) and length(Artists_info.ArtistName)<20 and length(AlbumName)<20\n" +
                "ORDER BY RAND() LIMIT 4;";
        this.easyQueries.add(easyArtistSongQueries);
        this.easyQueries.add(easySongAlbumQueries);
        this.easyQueries.add(easyArtistAlbumQueries);

        // create queries for the medium levels: (5 pairs)
        // artist - song
        String mediumArtistSongQueries = "With Artists_info As(\n" +
                "Select \n" +
                "ArtistName, AR.ArtistID, Title, Year,AL.AlbumName,\n" +
                "Avg(Year) as Average_years\n" +
                "From artists as AR\n" +
                "Inner join albums_artists AA\n" +
                "On AR.ArtistID=AA.ArtistID\n" +
                "Inner Join albums AL\n" +
                "On AA.AlbumID=AL.AlbumID\n" +
                "Inner join songs S\n" +
                "On AL.AlbumID=S.AlbumID\n" +
                "Group By AR.ArtistID,ArtistName),\n" +
                "\n" +
                "Artists_info2 As(\n" +
                "Select \n" +
                "ArtistName, AR.ArtistID,\n" +
                "count(SongID) as Count_songs\n" +
                "From artists as AR\n" +
                "Inner join albums_artists AA\n" +
                "On AR.ArtistID=AA.ArtistID\n" +
                "Inner Join albums AL\n" +
                "On AA.AlbumID=AL.AlbumID\n" +
                "Inner join songs S\n" +
                "On AL.AlbumID=S.AlbumID\n" +
                "Group By AR.ArtistID,ArtistName)\n" +
                "\n" +
                "Select distinct Artists_info.ArtistName, Title From Artists_info inner join Artists_info2\n" +
                "on Artists_info.ArtistID = Artists_info2.ArtistID\n" +
                "Where (Average_Years < 2002 or Count_songs < 3) " +
                "and length(Artists_info.ArtistName)<20 and length(Title)<20\n" +
                "ORDER BY RAND() LIMIT 5;";
        // song - album
        String mediumSongAlbumQueries = "With Artists_info As(\n" +
                "Select \n" +
                "ArtistName, AR.ArtistID, Title, Year,AL.AlbumName,\n" +
                "Avg(Year) as Average_years\n" +
                "From artists as AR\n" +
                "Inner join albums_artists AA\n" +
                "On AR.ArtistID=AA.ArtistID\n" +
                "Inner Join albums AL\n" +
                "On AA.AlbumID=AL.AlbumID\n" +
                "Inner join songs S\n" +
                "On AL.AlbumID=S.AlbumID\n" +
                "Group By AR.ArtistID,ArtistName),\n" +
                "\n" +
                "Artists_info2 As(\n" +
                "Select \n" +
                "ArtistName, AR.ArtistID,\n" +
                "count(SongID) as Count_songs\n" +
                "From artists as AR\n" +
                "Inner join albums_artists AA\n" +
                "On AR.ArtistID=AA.ArtistID\n" +
                "Inner Join albums AL\n" +
                "On AA.AlbumID=AL.AlbumID\n" +
                "Inner join songs S\n" +
                "On AL.AlbumID=S.AlbumID\n" +
                "Group By AR.ArtistID,ArtistName)\n" +
                "\n" +
                "Select distinct  Title, AlbumName From Artists_info inner join Artists_info2\n" +
                "on Artists_info.ArtistID = Artists_info2.ArtistID\n" +
                "Where (Average_Years < 2002 or Count_songs < 3) and length(Title)<20" +
                " and Title not LIKE AlbumName\n" +
                "ORDER BY RAND() LIMIT 5;";
        // artist - album
        String mediumArtistAlbumQueries = "With Artists_info As(\n" +
                "Select \n" +
                "ArtistName, AR.ArtistID, Title, Year,AL.AlbumName,\n" +
                "Avg(Year) as Average_years\n" +
                "From artists as AR\n" +
                "Inner join albums_artists AA\n" +
                "On AR.ArtistID=AA.ArtistID\n" +
                "Inner Join albums AL\n" +
                "On AA.AlbumID=AL.AlbumID\n" +
                "Inner join songs S\n" +
                "On AL.AlbumID=S.AlbumID\n" +
                "Group By AR.ArtistID,ArtistName),\n" +
                "\n" +
                "Artists_info2 As(\n" +
                "Select \n" +
                "ArtistName, AR.ArtistID,\n" +
                "count(SongID) as Count_songs\n" +
                "From artists as AR\n" +
                "Inner join albums_artists AA\n" +
                "On AR.ArtistID=AA.ArtistID\n" +
                "Inner Join albums AL\n" +
                "On AA.AlbumID=AL.AlbumID\n" +
                "Inner join songs S\n" +
                "On AL.AlbumID=S.AlbumID\n" +
                "Group By AR.ArtistID,ArtistName)\n" +
                "\n" +
                "Select distinct  Artists_info.ArtistName, AlbumName From Artists_info inner join Artists_info2\n" +
                "on Artists_info.ArtistID = Artists_info2.ArtistID\n" +
                "Where (Average_Years < 2002 or Count_songs < 3) and length(Artists_info.ArtistName)<20 and length(AlbumName)<20\n" +
                "ORDER BY RAND() LIMIT 5;";
        this.mediumQueries.add(mediumArtistSongQueries);
        this.mediumQueries.add(mediumSongAlbumQueries);
        this.mediumQueries.add(mediumArtistAlbumQueries);


        // create queries for the hard levels: (6 pairs)
        // artist - song
        String hardArtistSongQueries = "With Artists_info As(\n" +
                "Select \n" +
                "ArtistName, AR.ArtistID, Title, Year,AL.AlbumName,\n" +
                "Avg(Year) as Average_years\n" +
                "From artists as AR\n" +
                "Inner join albums_artists AA\n" +
                "On AR.ArtistID=AA.ArtistID\n" +
                "Inner Join albums AL\n" +
                "On AA.AlbumID=AL.AlbumID\n" +
                "Inner join songs S\n" +
                "On AL.AlbumID=S.AlbumID\n" +
                "Group By AR.ArtistID,ArtistName),\n" +
                "\n" +
                "Artists_info2 As(\n" +
                "Select \n" +
                "ArtistName, AR.ArtistID,\n" +
                "count(SongID) as Count_songs\n" +
                "From artists as AR\n" +
                "Inner join albums_artists AA\n" +
                "On AR.ArtistID=AA.ArtistID\n" +
                "Inner Join albums AL\n" +
                "On AA.AlbumID=AL.AlbumID\n" +
                "Inner join songs S\n" +
                "On AL.AlbumID=S.AlbumID\n" +
                "Group By AR.ArtistID,ArtistName)\n" +
                "\n" +
                "Select distinct  Artists_info.ArtistName, Title From Artists_info inner join Artists_info2\n" +
                "on Artists_info.ArtistID = Artists_info2.ArtistID\n" +
                "Where (Average_Years < 2000 and Count_songs <= 2) and length(Artists_info.ArtistName)<20 and length(Title)<20\n" +
                "ORDER BY RAND() LIMIT 6;";
        // song - album
        String hardSongAlbumQueries = "With Artists_info As(\n" +
                "Select \n" +
                "ArtistName, AR.ArtistID, Title, Year,AL.AlbumName,\n" +
                "Avg(Year) as Average_years\n" +
                "From artists as AR\n" +
                "Inner join albums_artists AA\n" +
                "On AR.ArtistID=AA.ArtistID\n" +
                "Inner Join albums AL\n" +
                "On AA.AlbumID=AL.AlbumID\n" +
                "Inner join songs S\n" +
                "On AL.AlbumID=S.AlbumID\n" +
                "Group By AR.ArtistID,ArtistName),\n" +
                "\n" +
                "Artists_info2 As(\n" +
                "Select \n" +
                "ArtistName, AR.ArtistID,\n" +
                "count(SongID) as Count_songs\n" +
                "From artists as AR\n" +
                "Inner join albums_artists AA\n" +
                "On AR.ArtistID=AA.ArtistID\n" +
                "Inner Join albums AL\n" +
                "On AA.AlbumID=AL.AlbumID\n" +
                "Inner join songs S\n" +
                "On AL.AlbumID=S.AlbumID\n" +
                "Group By AR.ArtistID,ArtistName)\n" +
                "\n" +
                "Select distinct Title, AlbumName From Artists_info inner join Artists_info2\n" +
                "on Artists_info.ArtistID = Artists_info2.ArtistID\n" +
                "Where (Average_Years < 2000 and Count_songs <= 2) and length(Title)<20 and length(AlbumName)<20" +
                " and Title not LIKE AlbumName\n" +
                "ORDER BY RAND() LIMIT 6;";
        // artist - album
        String hardArtistAlbumQueries = "With Artists_info As(\n" +
                "Select \n" +
                "ArtistName, AR.ArtistID, Title, Year,AL.AlbumName,\n" +
                "Avg(Year) as Average_years\n" +
                "From artists as AR\n" +
                "Inner join albums_artists AA\n" +
                "On AR.ArtistID=AA.ArtistID\n" +
                "Inner Join albums AL\n" +
                "On AA.AlbumID=AL.AlbumID\n" +
                "Inner join songs S\n" +
                "On AL.AlbumID=S.AlbumID\n" +
                "Group By AR.ArtistID,ArtistName),\n" +
                "\n" +
                "Artists_info2 As(\n" +
                "Select \n" +
                "ArtistName, AR.ArtistID,\n" +
                "count(SongID) as Count_songs\n" +
                "From artists as AR\n" +
                "Inner join albums_artists AA\n" +
                "On AR.ArtistID=AA.ArtistID\n" +
                "Inner Join albums AL\n" +
                "On AA.AlbumID=AL.AlbumID\n" +
                "Inner join songs S\n" +
                "On AL.AlbumID=S.AlbumID\n" +
                "Group By AR.ArtistID,ArtistName)\n" +
                "\n" +
                "Select distinct  Artists_info.ArtistName, AlbumName From Artists_info inner join Artists_info2\n" +
                "on Artists_info.ArtistID = Artists_info2.ArtistID\n" +
                "Where (Average_Years < 2000 and Count_songs <= 2) and length(Artists_info.ArtistName)<20 and length(AlbumName)<20\n" +
                "ORDER BY RAND() LIMIT 6;";
        this.hardQueries.add(hardArtistSongQueries);
        this.hardQueries.add(hardSongAlbumQueries);
        this.hardQueries.add(hardArtistAlbumQueries);
    }

    /**
     * set the level mode
     * @param m - level mode (0 = artist - song, 1 = song - album, 2 = artist - album)
     */
    public void setLevelMode(int m) {
        this.levelMode = m;
    }

    /**
     * @return the level mode (0 = artist - song, 1 = song - album, 2 = artist - album)
     */
    public int getLevelMode() {
        return this.levelMode;
    }

    /**
     * set the level difficulty
     * @param d - level difficulty (easy, medium or hard)
     */
    public void setLevelDifficulty(String d) {
        this.levelDifficulty = d;
    }

    /**
     * @return the level difficulty
     */
    public String getLevelDifficulty() {
        return this.levelDifficulty;
    }

    /**
     * set the highscores table type
     * @param type- level difficulty (easy, medium or hard)
     */
    public void setHighScoresType(String type) {
        HighScoresTable.setHighScoresType(type);
    }

    /**
     * @return the player name
     */
    public String getPlayerName(){return this.playerName1;}

    /**
     * set the player name
     * @param name - player name
     */
    public void setPlayerName(String name) {
        this.playerName1 = name;
    }

    /**
     * connect to mySQL
     */
    public void connect() {
        try {
            final String user = "root";
            final String password = "123456";
            this.myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/millionsong?allowPublicKeyRetrieval=true&useSSL=false", user, password);
            // create statement to execute queries
            this.stmt = this.myConn.createStatement();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * close connection
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
     * Create and return pairs of cards of the game
     * @return pairs of cards of the game
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
     * Create and return pair of cards
     * @param rs - raw in the table
     * @return pair of cards
     * @throws SQLException - throw sql exception
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

    public List<Player> getHighScores() {
        HighScoresTable.getInstance();
        return HighScoresTable.loadHighScores();
    }

    public void setGameEndingTime(Integer endingTime) {
        this.gameEndingTime = endingTime.toString();
    }

    public void addToHighScores() {
        String playerName = playerName1;
        HighScoresTable.getInstance();
        HighScoresTable.addToHighScores(playerName, gameEndingTime);
    }
}