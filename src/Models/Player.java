package Models;

import javafx.beans.property.SimpleStringProperty;

public class Player {

    private SimpleStringProperty playerName;
    private SimpleStringProperty playerTime;

    /**
     * Constructor.
     * @param name - the name of the player.
     * @param time - the time taken to finish the game.
     */
    public Player(String name, String time)
    {
        playerName = new SimpleStringProperty(name);
        playerTime = new SimpleStringProperty(time);
    }

    /**
     * @return playerName.
     */
    public String getName()
    {
        return playerName.get();
    }

    /**
     * @return playerTime.
     */
    public String getTime()
    {
        return playerTime.get();
    }

}