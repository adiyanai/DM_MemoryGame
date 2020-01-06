package Controllers;

 import Models.CardModel;
 import Models.MyModel;
 import Models.Model;
 import Controllers.GameBoardController;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.MouseEvent;
 import java.awt.event.MouseListener;

public class CardController implements ActionListener, MouseListener {
    private CardModel model;
    private GameBoardController view;

    public CardController(CardModel model1) {
        this.view = view;
        model = model1;
    }

    public void setView(GameBoardController view) {
        this.view = view;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "New Game":
                model.setDefaultSettings();
                break;
            case "Exit":
                System.exit(0);
                break;
        }

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        int numOfCurCard = view.getNumOfCardsByCoord(e.getXOnScreen(), e.getYOnScreen());
        model.openCard(numOfCurCard);
        model.updateView();
    }

    public void mouseReleased(MouseEvent e) {
        switch (model.getCountOfOpenCards()) {
            case 1:
                //model.makeDelay();
                break;
            case 2:
                if (this.isOpenPairAreEqual()) {
                    model.destroyPair();
                    model.updateView();
                    model.checkWin();
                } else {
                    model.closePair();
                    model.updateView();
                }
                break;
        }

    }

    private boolean isOpenPairAreEqual() {
        return view.equals(model.getOpenCard(0), model.getOpenCard(1));
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
