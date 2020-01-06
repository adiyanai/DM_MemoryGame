package Models;

import Controllers.GameBoardController;
import java.util.ArrayList;
import java.util.List;

public class CardModel {
    private Card cardArray[];
    private List<Card> turnedCards;
    private GameBoardController view;
    boolean isWin;

    public CardModel(int countOfCards) {
        this.cardArray = new Card[countOfCards];
        this.turnedCards = new ArrayList<>(2);
        for (int i = 0; i < cardArray.length; i++) {
            cardArray[i] = new Card(false, i);
        }
        isWin = false;
    }

    public void setView(GameBoardController view) {
        this.view = view;
    }

    public void setAllToDown() {
        for (Card curCard : cardArray) {
            curCard.setFaceUp(false);
        }
    }

    public void setDefaultSettings() {
        setAllToDown();
        turnedCards.clear();
        isWin = false;
        view.newGame();
        view.update();
    }

    public Card getCard(int numOfCard) {
        return cardArray[numOfCard];
    }

    public int getCountOfCards() {
        return cardArray.length;
    }

    public int getCountOfOpenCards() {
        return turnedCards.size();
    }

    public void openCard(int num) {
        turnedCards.add(cardArray[num]);
        cardArray[cardArray[num].getNum()].setFaceUp(true);
        isWin = false;
    }

    public void updateView() {
        view.update();
    }

    public void closePair() {
        cardArray[turnedCards.get(0).getNum()].setFaceUp(false);
        cardArray[turnedCards.get(1).getNum()].setFaceUp(false);
        turnedCards.clear();
        view.update();
        isWin = false;
    }

    public void destroyPair() {
        cardArray[turnedCards.get(0).getNum()].setFaceUp(true);
        cardArray[turnedCards.get(1).getNum()].setFaceUp(true);
        turnedCards.clear();
        view.update();
    }

    public void checkWin() {
        int k = 0;
        for (int i = 0; i < cardArray.length; i++) {
            if (cardArray[i].isFaceUp()) k++;
            if (k == cardArray.length) {
                isWin = true;
                System.out.println("You are Winner!");
            }
        }
    }

    public void makeDelay() {
        try {
            Thread.sleep(1000);
        } catch (Throwable ex) {
        }
    }

    public Card getOpenCard(int index) {
        return turnedCards.get(index);
    }
}