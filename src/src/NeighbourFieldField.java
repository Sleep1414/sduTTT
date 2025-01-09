import Direction.Pos;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NeighbourFieldField extends NeighbourField implements Subscriber {
    HashMap<Pos, NeighbourField> childField;
    int highestPossibleChecknumber = 3;
    private JPanel ticTacToeBoard;

    NeighbourFieldField(Subscriber parent, Pos graphPosition, JPanel gameBoard) {
        super(parent, graphPosition);
        // Assuming the parent is not used or handled elsewhere.

        // Initialize the grid (3x3 Tic-Tac-Toe)
        childField = new HashMap<>();
        if (this.getClass() != NeighbourFieldField.class) {
            return;
        }

        this.ticTacToeBoard = new JPanel(new GridLayout(3, 3));
        ticTacToeBoard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        ticTacToeBoard.setBackground(Color.LIGHT_GRAY);

        ticTacToeBoard.setName(graphPosition.toString());

        gameBoard.add(ticTacToeBoard);

        Pos[] positions = Pos.values();
        for (Pos position : positions) {
            childField.put(position, new NeighbourField(this, position, graphPostion,ticTacToeBoard));
        }
        // Set neighbors for each NeighbourField
        setNeighbours(
                childField.get(Pos.UPPERLEFT), null, null, null, null, childField.get(Pos.UPPERMID), null, childField.get(Pos.CENTERLEFT), childField.get(Pos.CENTERMID)
        );
        setNeighbours(
                childField.get(Pos.UPPERMID), null, null, null, childField.get(Pos.UPPERLEFT), childField.get(Pos.UPPERRIGHT), null, childField.get(Pos.CENTERMID), null
        );
        setNeighbours(
                childField.get(Pos.UPPERRIGHT), null, null, null, childField.get(Pos.UPPERMID), null, childField.get(Pos.CENTERMID), childField.get(Pos.CENTERRIGHT), null
        );

        setNeighbours(
                childField.get(Pos.CENTERLEFT), null, childField.get(Pos.UPPERLEFT), null, null, childField.get(Pos.CENTERMID), null, childField.get(Pos.LOWERLEFT), null
        );
        setNeighbours(
                childField.get(Pos.CENTERMID), childField.get(Pos.UPPERLEFT), childField.get(Pos.UPPERMID), childField.get(Pos.UPPERRIGHT),
                childField.get(Pos.CENTERLEFT), childField.get(Pos.CENTERRIGHT), childField.get(Pos.LOWERLEFT), childField.get(Pos.LOWERMID), childField.get(Pos.LOWERRIGHT)
        );
        setNeighbours(
                childField.get(Pos.CENTERRIGHT), null, childField.get(Pos.UPPERRIGHT), null, childField.get(Pos.CENTERMID), null, null, childField.get(Pos.LOWERRIGHT), null
        );

        setNeighbours(
                childField.get(Pos.LOWERLEFT), null, childField.get(Pos.CENTERLEFT), childField.get(Pos.CENTERMID), null, childField.get(Pos.LOWERMID), null, null, null
        );
        setNeighbours(
                childField.get(Pos.LOWERMID), null, childField.get(Pos.CENTERMID), null, childField.get(Pos.LOWERLEFT), childField.get(Pos.LOWERRIGHT), null, null, null
        );
        setNeighbours(
                childField.get(Pos.LOWERRIGHT), childField.get(Pos.CENTERMID), childField.get(Pos.CENTERRIGHT), null, childField.get(Pos.LOWERMID), null, null, null, null
        );

    }

    // Helper method to set neighbors for a NeighbourField
    protected void setNeighbours(NeighbourField field, NeighbourField upperLeft, NeighbourField upperMid, NeighbourField upperRight, NeighbourField left,
                                 NeighbourField right, NeighbourField lowerLeft, NeighbourField lowerMid,
                                 NeighbourField lowerRight) {

        if (upperLeft != null) {
            field.putNeighbour(Pos.UPPERLEFT, upperLeft);
        }
        if (upperMid != null) {
            field.putNeighbour(Pos.UPPERMID, upperMid);
        }
        if (upperRight != null) {
            field.putNeighbour(Pos.UPPERRIGHT, upperRight);
        }
        if (right != null) {
            field.putNeighbour(Pos.CENTERRIGHT, right);
        }
        if (lowerRight != null) {
            field.putNeighbour(Pos.LOWERRIGHT, lowerRight);
        }
        if (lowerMid != null) {
            field.putNeighbour(Pos.LOWERMID, lowerMid);
        }
        if (lowerLeft != null) {
            field.putNeighbour(Pos.LOWERLEFT, lowerLeft);
        }
        if (left != null) {
            field.putNeighbour(Pos.CENTERLEFT, left);
        }


    }

    checkState evaluate(NeighbourField newSetField) {
        if (!newSetField.isChecked()) {
            return checkState.UNCHECKED;
        }
        Iterator<Map.Entry<Pos, NeighbourField>> fields = newSetField.getNeighbours().entrySet().iterator();

        for (int i = 0; i < highestPossibleChecknumber; i++) {
            var nextfield = fields.next();
            if (nextfield.getValue() == null) {
                i--;
                continue;
            }
            var state = evaluate(nextfield.getValue(), nextfield.getValue().getCheck(), nextfield.getKey(), 0, newSetField);
            if (checkState.UNCHECKED != state) {
                return state;
            }
        }
        return checkState.UNCHECKED;
    }

    public JPanel getTicTacToeBoard() {
        return ticTacToeBoard;
    }

    private checkState evaluate(NeighbourField field, checkState previousState, Pos dir, int count, NeighbourField previous) {
        if (previousState == field.getCheck() && count > 0) {
            return previousState;
        }
        if (!field.isChecked() || count > 0) {
            return checkState.UNCHECKED;
        }
        if (field.getCheck() != previousState) {
            return checkState.UNCHECKED;
        }
        if (!field.getNeighbours().containsKey(dir)) {
            return switch (dir) {
                case UPPERLEFT ->
                        evaluate(previous.neighbours.get(Pos.LOWERRIGHT), field.getCheck(), Pos.LOWERRIGHT, count + 1, field);
                case UPPERMID ->
                        evaluate(previous.neighbours.get(Pos.LOWERMID), field.getCheck(), Pos.LOWERMID, count + 1, field);
                case UPPERRIGHT ->
                        evaluate(previous.neighbours.get(Pos.LOWERLEFT), field.getCheck(), Pos.LOWERLEFT, count + 1, field);
                case CENTERLEFT ->
                        evaluate(previous.neighbours.get(Pos.CENTERRIGHT), field.getCheck(), Pos.CENTERRIGHT, count + 1, field);
                case CENTERRIGHT ->
                        evaluate(previous.neighbours.get(Pos.CENTERLEFT), field.getCheck(), Pos.CENTERLEFT, count + 1, field);
                case LOWERLEFT ->
                        evaluate(previous.neighbours.get(Pos.UPPERRIGHT), field.getCheck(), Pos.UPPERRIGHT, count + 1, field);
                case LOWERMID ->
                        evaluate(previous.neighbours.get(Pos.UPPERMID), field.getCheck(), Pos.UPPERMID, count + 1, field);
                case LOWERRIGHT ->
                        evaluate(previous.neighbours.get(Pos.UPPERLEFT), field.getCheck(), Pos.UPPERLEFT, count + 1, field);
                default -> throw new IllegalArgumentException("Unknown direction: " + this);
            };

        }
        return evaluate(field.neighbours.get(dir), previousState, dir, count + 1, field);

    }
    //großes feld markieren für test
    protected void markcell( int player) {

        for (Pos position : Pos.values()) {
            NeighbourField field = getField(position);
            JLabel label = field.getInnerCell();

            // Setze die Hintergrundfarbe je nach Spielerfarbe
            if (player == 2) {
                label.setBackground(Color.RED); // Farbe für Spieler 1 (x)
            } else if (player == 1) {
                label.setBackground(Color.BLUE); // Farbe für Spieler 2 (o)
            }
            label.setEnabled(false); // Zelle deaktivieren

        }
    }

    @Override
    public NeighbourField getField(Pos direction) {
        return childField.get(direction);
    }

    @Override
    public void update(NeighbourField field) {
        checkState evalcheck = evaluate(field);
        if (evalcheck == checkState.PLAYER1) {
            check(1);
            markcell(1);
        } else if (evalcheck == checkState.PLAYER2) {
            check(2);
            markcell(2);
        }

        parent.update(field);

    }

    @Override
    public void updateplayerturn() {
        parent.updateplayerturn();
    }

    @Override
    public boolean getplayerturn() {
        return parent.getplayerturn();
    }

}



