package Neighbourstruct;

import Direction.*;
import org.w3c.dom.ranges.Range;


import javax.swing.*;
import java.awt.*;
import java.awt.font.NumericShaper;
import java.util.*;


public class NeighbourFieldField extends NeighbourField implements Subscriber {
    HashMap<Pos, NeighbourField> childField;
    int highestPossibleChecknumber = 4;

    NeighbourFieldField(Subscriber parent, Pos graphPosition, JPanel gameBoard) {
        super(parent, graphPosition);
        // Assuming the parent is not used or handled elsewhere.

        // Initialize the grid (3x3 Tic-Tac-Toe)
        childField = new HashMap<>();
        if (this.getClass() != NeighbourFieldField.class) {
            return;
        }

        JPanel ticTacToeBoard = new JPanel(new GridLayout(3, 3));
        ticTacToeBoard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        ticTacToeBoard.setBackground(Color.LIGHT_GRAY);

        ticTacToeBoard.setName(graphPosition.toString());

        gameBoard.add(ticTacToeBoard);

        Column[] columns = Column.values();
        Row[] rows = Row.values();
        for (Column column : columns) {
            for (Row row : rows) {
                Pos position = new Pos(column,row);
                NeighbourField field = new NeighbourField(this, position, graphPostion, ticTacToeBoard);
                childField.put(position,field);
            }
        }
        childField.forEach( (k,v) -> {setNeighbours(v);});



    }

    // Helper method to set neighbors for a Neighbourstruct.NeighbourField
    protected void setNeighbours(NeighbourField field) {
        Pos coords = getFieldFieldPosition();
        int column = coords.column.ordinal();
        int row = coords.row.ordinal();
        int addedNumericValue = column + row;
        boolean setDiagonal = true;
        if(!(addedNumericValue % 2 == 0)){
            setDiagonal= false;
        }
        for ( int x = -1 ; x <= 1; x++) {
            for (int y = 0 ; y < Column.values().length; y++) {
                int neighbourcolumn = column+x;
                int neighbourrow= row+x;

                if( neighbourcolumn<=2 && neighbourcolumn >=0 && neighbourrow<=2 && neighbourrow >=0  ) {
                    Pos neighbourDir = new Pos(Column.values()[column + x], Row.values()[row - y]);

                    field.putNeighbour(neighbourDir, childField.get(neighbourDir));
                }
            }
        }
    }

    checkState evaluate(NeighbourField newSetField) {
        if (!newSetField.isChecked()) {
            return checkState.UNCHECKED;
        }
        Iterator<Map.Entry<Pos, NeighbourField>> fields = newSetField.getNeighbours().entrySet().iterator();

        for (int i = 0; i < highestPossibleChecknumber; i++) {
            try {
            var nextfield = fields.next();
            if (nextfield.getValue() == null) {
                i--;
                continue;
            }
            var state = evaluate(nextfield.getValue(), nextfield.getValue().getCheck(), nextfield.getKey(), 0, newSetField);
            if (checkState.UNCHECKED != state) {
                return state;
            }
            } catch (NoSuchElementException a){return checkState.UNCHECKED;}
        }
        return checkState.UNCHECKED;
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



