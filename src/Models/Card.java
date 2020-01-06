package Models;

public class Card {
    private Boolean faceUp = false;
    private int num;


    public Card(Boolean isFaceUp, int numOfCard) {
        this.faceUp = isFaceUp;
        this.num = numOfCard;
    }

    public Card() {}

    public boolean isFaceUp() {
        return this.faceUp;
    }

    public void setFaceUp(boolean value) {
        faceUp = value;
    }

    public int getNum() {
        return this.num;
    }

    public void setWinFlag() {
        this.num = -2;
    }
}