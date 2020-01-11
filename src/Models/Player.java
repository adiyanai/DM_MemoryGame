package Models;

import javafx.beans.property.SimpleStringProperty;

public class Player {

    private SimpleStringProperty playerName;
    private SimpleStringProperty playerTime;

    public Player(String name, String time)
    {
        playerName = new SimpleStringProperty(name);
        playerTime = new SimpleStringProperty(time);
    }

    public String getName()
    {
        return playerName.get();
    }

    public String getTime()
    {
        return playerTime.get();
    }


}
