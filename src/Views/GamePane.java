package Views;

import Controllers.GameBoardController;
import Models.MyModel;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GamePane extends GridPane {
    private MyModel m = MyModel.getInstance();
    private GameBoardController gameBoardController;
    private Stage gameWindow;
    private String[][] board;
    private Card[][] cards;
    boolean first = true;
    private int numberOfCouples;
    private int rowsNumber;
    private int columnsNumber;
    int i1;
    int j1;
    int i2;
    int j2;
    int finishedCounter = 0;
    private Random rand;
    private List<Pair<String, String>> cardsPairs;
    private List<String> listOfAllCards;
    private int currentBackCard;
    BorderPane pane;

    public GamePane(GameBoardController boardController, BorderPane gamePane, int numOfCouples, Stage window) {
        gameBoardController = boardController;
        gameWindow = window;
        pane = gamePane;
        setStyle("-fx-background-color: antiquewhite");
        setAlignment(Pos.CENTER);
        setPadding(new Insets(3, 3, 3, 3));
        setHgap(10);
        setVgap(10);
        currentBackCard = 0;
        numberOfCouples = numOfCouples;
        rowsNumber = 0;
        columnsNumber = 0;
        if (numberOfCouples == 4) {
            rowsNumber = 2;
            columnsNumber = 4;
        }
        else if (numberOfCouples == 5) {
            rowsNumber = 2;
            columnsNumber = 5;
        } else {
            rowsNumber = 3;
            columnsNumber = 4;
        }
        rand = new Random();
        setLevelCards();

    }

    private void setLevelCards() {
        finishedCounter = 0;
        board = new String[columnsNumber][rowsNumber];
        cards = new Card[columnsNumber][rowsNumber];
        cardsPairs = m.getCardsData();
        listOfAllCards = new ArrayList<>();
        String key;
        String value;
        for (Pair<String, String> stringStringPair : cardsPairs) {
            key = stringStringPair.getKey();
            listOfAllCards.add(key);
            value = stringStringPair.getValue();
            listOfAllCards.add(value);
        }
        String card;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                card = listOfAllCards.get(rand.nextInt(listOfAllCards.size()));
                listOfAllCards.remove(card);
                board[i][j] = String.copyValueOf(card.toCharArray());
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                add(cards[i][j] = new Card(i, j, currentBackCard), i, j);
                currentBackCard++;
                currentBackCard = currentBackCard % 6;
                this.setAlignment(Pos.CENTER);
            }
        }
    }

    private boolean isLevelEnded() {
        return finishedCounter == numberOfCouples;
    }

    private boolean isGameEnded() {
        return gameBoardController.isGameEnded();
    }


    public class Card extends StackPane {
        private boolean flipped;
        private int i;
        private int j;
        private String imgPath;
        private boolean done = false;
        private ImageView imageView;

        public Card(int i, int j, int cardBack) {
            this.i = i;
            this.j = j;
            switch (cardBack){
                case 0: {
                    imgPath = "/backCard1.png";
                    break;
                }
                case 1: {
                    imgPath = "/backCard2.png";
                    break;
                }
                case 2: {
                    imgPath = "/backCard3.png";
                    break;
                }
                case 3: {
                    imgPath = "/backCard4.png";
                    break;
                }
                case 4: {
                    imgPath = "/backCard5.png";
                    break;
                }
                case 5: {
                    imgPath = "/backCard6.png";
                    break;
                }
            }
            imageView = new ImageView(new Image(imgPath));
            this.setPrefSize(100, 100);
            this.setOnMouseClicked(e -> {
                try {
                    handleMouseClick();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            if (!flipped) {
                setFaceDown();
            } else {
                setFaceUp();
            }
        }

        private void setFaceDown() {
            this.getChildren().clear();
            this.getChildren().add(imageView);
        }

        private void setFaceUp() {
            HBox h = new HBox();
            if(done) {
                h.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                h.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                h.setStyle("-fx-border-color: skyblue; -fx-border-width: 4px;");
            }
            Label l = new Label();
            l.setTextFill(Color.BLACK);
            l.setFont(Font.font("Aclonica", FontWeight.BOLD, 14));
            l.setTextAlignment(TextAlignment.CENTER);
            StringBuilder text = new StringBuilder(board[i][j]);
            if (text.toString().contains("(")) {
                text = new StringBuilder(text.substring(0, text.indexOf("(")));
            }
            if (text.toString().contains("[")) {
                text = new StringBuilder(text.substring(0, text.indexOf("[")));
            }
            String[] words = text.toString().split(" ");
            if (words.length > 3) {
                text = new StringBuilder(words[0] + " " + words[1] + " " + words[2] + "\n");
                for(int k = 3; k < words.length; k++) {
                    text.append(words[k]);
                    text.append(" ");
                }
            }
            l.setText(text.toString());
            h.getChildren().addAll(l);
            this.getChildren().add(h);
            h.setAlignment(Pos.CENTER);
        }

        private void handleMouseClick() throws IOException {
            if (first) {
                if (!flipped) {
                    if (!cards[i1][j1].flipped)
                        cards[i1][j1].setFaceDown();
                    if (!cards[i2][j2].flipped)
                        cards[i2][j2].setFaceDown();
                    cards[getI()][getJ()].flipped = true;
                    i1 = getI();
                    j1 = getJ();
                    setFaceUp();
                    first = false;
                }
            } else {
                if (!flipped) {
                    i2 = getI();
                    j2 = getJ();
                    cards[i2][j2].flipped = true;
                    setFaceUp();
                    if (match(i1, j1, i2, j2)) {
                        cards[i1][j1].done = true;
                        done = true;
                        cards[i1][j1].setFaceUp();
                        setFaceUp();
                        finishedCounter++;
                        if (isLevelEnded()) {
                            gameBoardController.increaseLevelNumber();
                            if (isGameEnded()) {
                                Parent highScoresPage = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Views/WinningView.fxml")));
                                Scene scene = new Scene(highScoresPage);
                                scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("StyleSheet.css")).toExternalForm());
                                gameWindow.setScene(scene);
                            }
                            for (int i = 0; i < board.length; i++) {
                                for (int j = 0; j < board[i].length; j++) {
                                    cards[i][j].setFaceDown();
                                }
                            }
                            setLevelCards();
                        }

                    } else {
                        cards[i1][j1].flipped = false;
                        cards[i2][j2].flipped = false;
                    }
                    first = true;
                    cards[i1][j1].setStyle("-fx-border-color: white; -fx-border-width: 0px");
                    this.setStyle("-fx-border-color: white; -fx-border-width: 0px");
                }
            }
        }

        private boolean match(int i1, int j1, int i2, int j2) {
            for (Pair<String, String> stringStringPair : cardsPairs) {
                if (stringStringPair.getKey().intern().equals(board[i1][j1].intern()) &&
                        stringStringPair.getValue().intern().equals(board[i2][j2].intern()) ||
                        stringStringPair.getKey().intern().equals(board[i2][j2].intern()) &&
                                stringStringPair.getValue().intern().equals(board[i1][j1].intern())) {
                    return true;
                }
            }
            return false;
        }

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
        }

    }
}