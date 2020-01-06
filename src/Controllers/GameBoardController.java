package Controllers;

import Models.Card;
import Models.CardModel;
import Views.SwingViewOfCards;
import com.sun.istack.internal.NotNull;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameBoardController {
    private SwingViewOfCards arrayOfCards[];
    private JFrame mainFrame;
    private Container mainConteinerPane;
    private ImageIcon cardIcon[]; // 0-6 front side of the card; 7 back side
    private CardModel model;
    private CardController controller;
    JPanel panel;
    ImageIcon backIcon;

    public GameBoardController(CardModel model, CardController controller) {
        this.controller = controller;
        this.model = model;
//      main window
        this.mainFrame = new JFrame("Matching Game");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setSize(800, 900);
        this.mainConteinerPane = this.mainFrame.getContentPane();
        this.mainConteinerPane.setLayout(new BoxLayout(this.mainConteinerPane, BoxLayout.PAGE_AXIS));
        //Menu Bar
        JMenuBar menuBar = new JMenuBar();
        this.mainFrame.setJMenuBar(menuBar);
        //Game Menu
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);
        menuBar.add(gameMenu);
        //creation generic submenu creater
        newMenuItem("New Game", gameMenu, controller);
        newMenuItem("Exit", gameMenu, controller);
        //About menu
        JMenu aboutMenu = new JMenu("Help");
        menuBar.add(aboutMenu);
        newMenuItem("Rules", aboutMenu, controller);
        newMenuItem("Developers", aboutMenu, controller);
        //loading cards
        this.cardIcon = loadCardIcons(model.getCountOfCards() / 2);
        arrayOfCards = new SwingViewOfCards[model.getCountOfCards()];
        panel = new JPanel(new GridLayout(3, 4));
        backIcon = this.cardIcon[6];
    }

    private ImageIcon[] loadCardIcons(int countOfIcons) {
        ImageIcon icon[] = new ImageIcon[countOfIcons + 1];
        for (int i = 0; i < countOfIcons + 1; i++) {
            String fileName = "D:/education/Java/FindAPairGame/card" + i + ".jpg";
            icon[i] = new ImageIcon(fileName);
        }
        return icon;
    }

    private void makeCards(boolean newGame) {
        if (newGame) {
            this.panel = new JPanel(new GridLayout(3, 4));
            int lenghtOfCardsToAdd = model.getCountOfCards();
            int cardsToAdd[] = new int[lenghtOfCardsToAdd];
            for (int i = 0; i < lenghtOfCardsToAdd / 2; i++) {
                cardsToAdd[2 * i] = i;
                cardsToAdd[2 * i + 1] = i;
            }
            randomizeCardArray(cardsToAdd);
            for (int i = 0; i < cardsToAdd.length; i++) {
                arrayOfCards[i] = new SwingViewOfCards(cardIcon[cardsToAdd[i]], backIcon, i, controller);
                this.panel.add(arrayOfCards[i]);
            }
        } else {
            for (int i = 0; i < arrayOfCards.length; i++) {
                this.mainConteinerPane.remove(arrayOfCards[i]);
                if (model.getCard(i).isFaceUp()) {
                    arrayOfCards[i].setIcon(arrayOfCards[i].getFaceIcon());
                    arrayOfCards[i].setFaceUp(true);
                } else {
                    arrayOfCards[i].setIcon(backIcon);
                    arrayOfCards[i].setFaceUp(false);
                }
                this.panel.add(arrayOfCards[i]);
            }
        }

        this.mainConteinerPane.add(panel);
    }

    public void update() {
        this.makeCards(false);
    }

    private void randomizeCardArray(int[] arr) {
        Random randomizer = new Random();
        for (int i = 0; i < arr.length; i++) {
            int d = randomizer.nextInt(arr.length);
            int s = arr[d];
            arr[d] = arr[i];
            arr[i] = s;
        }
    }

    private void newMenuItem(String string, JMenu menu, CardController listener) {
        JMenuItem newItem = new JMenuItem(string);
        newItem.setActionCommand(string);
        newItem.addActionListener(listener);
        menu.add(newItem);
    }

    public void newGame() {
        this.mainConteinerPane.removeAll();
        //make new cardSet visible
        this.makeCards(true);
        this.mainConteinerPane.add(panel);
        //show main window
        this.mainFrame.setVisible(true);
    }

    private void drawCardArray() {
        for (int i = 0; i < this.arrayOfCards.length; i++) {
            this.panel.add(arrayOfCards[i]);
        }
    }

    public int getNumOfCardsByCoord(int x, int y) {
        int line = y / arrayOfCards[0].getFaceIcon().getIconHeight();
        int columns = x / arrayOfCards[0].getFaceIcon().getIconWidth();
        if ((line < 3)) {
            return (line - 1) * 4 + columns;
        } else {
            return (line - 2) * 4 + columns;
        }
    }

    public boolean equals(@NotNull Card card1, @NotNull Card card2) {
        int num1 = card1.getNum();
        int num2 = card2.getNum();

        return arrayOfCards[num1].getFaceIcon().equals(arrayOfCards[num2].getFaceIcon());
    }
}