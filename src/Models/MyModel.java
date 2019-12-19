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
    private List<String> easyQueries;
    private List<String> mediumQueries;
    private List<String> hardQueries;
    private static MyModel instance = null;

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
        initializeQueries();
    }

    /**
     * initialize queries to all the levels types (easy, medium, hard)
     */
    public void initializeQueries() {
        // create queries for the easy levels: (4 pairs)
        // artist - song
        String easyArtistSongQueries = "SELECT DISTINCT ArtistName, Title FROM millionsong.artists, millionsong.albums, millionsong.albums_artists, millionsong.songs " +
                "WHERE albums.AlbumID = albums_artists.AlbumID AND artists.ArtistID = albums_artists.ArtistID AND albums.AlbumID = songs.AlbumID " +
                "ORDER BY RAND() LIMIT 4;";
        // song - album
        String easySongAlbumQueries = "SELECT DISTINCT Title, AlbumName FROM millionsong.songs, millionsong.albums " +
                "WHERE songs.AlbumID = albums.AlbumID " +
                "ORDER BY RAND() LIMIT 4;";
        // artist - album
        String easyArtistAlbumQueries = "SELECT DISTINCT ArtistName, AlbumName FROM millionsong.artists, millionsong.albums, millionsong.albums_artists " +
                "WHERE albums.AlbumID = albums_artists.AlbumID AND artists.ArtistID = albums_artists.ArtistID " +
                "ORDER BY RAND() LIMIT 4;";
        // song - year/decade
        this.easyQueries.add(easyArtistSongQueries);
        this.easyQueries.add(easySongAlbumQueries);
        this.easyQueries.add(easyArtistAlbumQueries);


        // create queries for the medium levels: (6 pairs)
        // artist - song
        String mediumArtistSongQueries = "SELECT DISTINCT ArtistName, Title FROM millionsong.artists, millionsong.albums, millionsong.albums_artists, millionsong.songs " +
                "WHERE albums.AlbumID = albums_artists.AlbumID AND artists.ArtistID = albums_artists.ArtistID AND albums.AlbumID = songs.AlbumID " +
                "ORDER BY RAND() LIMIT 6;";
        // song - album
        String mediumSongAlbumQueries = "SELECT DISTINCT Title, AlbumName FROM millionsong.songs, millionsong.albums " +
                "WHERE songs.AlbumID = albums.AlbumID " +
                "ORDER BY RAND() LIMIT 6;";
        // artist - album
        String mediumArtistAlbumQueries = "SELECT DISTINCT ArtistName, AlbumName FROM millionsong.artists, millionsong.albums, millionsong.albums_artists " +
                "WHERE albums.AlbumID = albums_artists.AlbumID AND artists.ArtistID = albums_artists.ArtistID " +
                "ORDER BY RAND() LIMIT 6;";
        this.mediumQueries.add(mediumArtistSongQueries);
        this.mediumQueries.add(mediumSongAlbumQueries);
        this.mediumQueries.add(mediumArtistAlbumQueries);


        // create queries for the hard levels: (8 pairs)
        // artist - song
        String hardArtistSongQueries = "SELECT DISTINCT ArtistName, Title FROM millionsong.artists, millionsong.albums, millionsong.albums_artists, millionsong.songs " +
                "WHERE albums.AlbumID = albums_artists.AlbumID AND artists.ArtistID = albums_artists.ArtistID AND albums.AlbumID = songs.AlbumID " +
                "ORDER BY RAND() LIMIT 8;";
        // song - album
        String hardSongAlbumQueries = "SELECT DISTINCT Title, AlbumName FROM millionsong.songs, millionsong.albums " +
                "WHERE songs.AlbumID = albums.AlbumID " +
                "ORDER BY RAND() LIMIT 8;";
        // artist - album
        String hardArtistAlbumQueries = "SELECT DISTINCT ArtistName, AlbumName FROM millionsong.artists, millionsong.albums, millionsong.albums_artists " +
                "WHERE albums.AlbumID = albums_artists.AlbumID AND artists.ArtistID = albums_artists.ArtistID " +
                "ORDER BY RAND() LIMIT 8;";
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
     * set the level difficulty
     * @param d - level difficulty (easy, medium or hard)
     */
    public void setLevelDifficulty(String d) {
        this.levelDifficulty = d;
    }

    /**
     * connect to mySQL
     */
    public void connect() {
        System.out.println("Database connecting...");
        try {
            final String user = "root";
            final String password = "ay28ed99";
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
        System.out.println("Database closing...");
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
}