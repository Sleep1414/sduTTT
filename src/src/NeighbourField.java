

import Direction.Pos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NeighbourField {
    protected Map<Pos, NeighbourField> neighbours;
    protected Subscriber parent;
    private Pos  fieldFieldPosition;
    private JLabel innerCell;
    Pos graphPostion;


    enum checkState {PLAYER1, PLAYER2, UNCHECKED}

    protected checkState check = checkState.UNCHECKED;

    NeighbourField(Subscriber parent, Pos fieldFieldPosition, Pos graphPostion, JPanel ticTacToeBoard) {
        this.parent = parent;
        neighbours = new HashMap<>();
        this.graphPostion = graphPostion;
        this.fieldFieldPosition = fieldFieldPosition;

        this.innerCell = new JLabel();
        innerCell.setHorizontalAlignment(SwingConstants.CENTER);
        innerCell.setOpaque(true);
        innerCell.setBackground(Color.WHITE);
        innerCell.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        innerCell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!innerCell.getText().isEmpty()) {
                    //nichts passiert
                    return;
                }


                String panelName = innerCell.getParent().getName();
                System.out.println("JPanel Name: " + panelName);
                if (innerCell.getBackground() == Color.lightGray) {
                    System.out.println("player set wrong");
                } else if (!parent.getplayerturn()) {
                    check(2);
                    innerCell.setText("<html><span style=\"color: red; font-size: 20px;\">x</span></html>");
                    parent.updateplayerturn();
                    innerCell.removeMouseListener(this);
                } else {
                    check(1);
                    innerCell.setText("<html><span style=\"color: blue; font-size: 20px;\">o</span></html>");
                    parent.updateplayerturn();
                    innerCell.removeMouseListener(this);
                }

            }
        });

        ticTacToeBoard.add(innerCell);

    }

    NeighbourField(Subscriber parent, Pos graphPostion) {
        this.parent = parent;
        this.graphPostion = graphPostion;
        neighbours = new HashMap<>();

    }

    public Pos getGraphPostion() {
        return graphPostion;
    }

    public NeighbourField getField(Pos direction) {
        return null;
    }

    void notifySubscriber() {
        parent.update(this);

    }


    void putNeighbour(Pos orientation, NeighbourField field) {
        neighbours.put(orientation, field);
    }

    public void check(int playerID) {
        if (playerID == 1) {
            check = checkState.PLAYER1;
        } else if (playerID == 2) {
            check = checkState.PLAYER2;
        }

        notifySubscriber();


    }

    public void uncheck() {
        check = checkState.UNCHECKED;
    }

    public boolean isChecked() {
        return check != checkState.UNCHECKED;
    }

    public checkState getCheck() {
        return check;
    }

    public Subscriber getParent() {
        return parent;
    }

    public Map<Pos, NeighbourField> getNeighbours() {
        return neighbours;
    }

    public JLabel getInnerCell() {
        return innerCell;
    }

    public Pos getFieldFieldPosition() {
        return fieldFieldPosition;
    }
}
