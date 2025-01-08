import Direction.Pos;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Objects;

public class NeighbourGraph extends NeighbourFieldField {

    //true == player1
    //false == player2
    boolean whichplayerturn = true;
    Pos lastmove;
    Pos nextmove;
    private JLabel currentPlayerL;

    NeighbourGraph() {
        super(null, null, null);
        // window
        JFrame playWindow = new JFrame("Ultimate TicTacToe");
        playWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playWindow.setSize(600, 600);
        playWindow.setLocationRelativeTo(null);
        playWindow.setBackground(Color.DARK_GRAY);

        // main layout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // top bar
        JPanel topBar = new JPanel(new BorderLayout());

        // Back Button und Panel
        JButton backButton = new JButton("Back");
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(backButton);

        // ActionListener für den Back-Button hinzufügen
        backButton.addActionListener(e -> {
            // Schließe das aktuelle Fenster
            playWindow.dispose();  // Schließt das Fenster
            System.out.println("Back-Button gedrückt: Fenster wird geschlossen.");
        });

        // Aktuellen Spieler anzeigen
        currentPlayerL = new JLabel("Es beginnt Spieler 1");
        currentPlayerL.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(currentPlayerL);

        // Hinzufügen der Panels zum TopBar
        topBar.add(leftPanel, BorderLayout.WEST);
        topBar.add(rightPanel, BorderLayout.EAST);

        // game board
        JPanel gameBoard = new JPanel(new GridLayout(3, 3));
        gameBoard.setBackground(Color.PINK);

        // adding components to the main panel
        mainPanel.add(topBar, BorderLayout.NORTH);
        mainPanel.add(gameBoard, BorderLayout.CENTER);

        //final
        playWindow.add(mainPanel);
        playWindow.setVisible(true);
        // Assuming the parent is not used or handled elsewhere.

        Pos[] positions = Pos.values();
        for (Pos position : positions) {
            childField.put(position, new NeighbourFieldField(this, position, gameBoard));
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

    @Override
    public void update(NeighbourField field) {
        if (Objects.equals(field.toString(), "NeighbourFieldField")) {
            this.check = evaluate(field);
        } else {
            clearMarks();
            Pos pos = field.getFieldFieldPosition();
            lastmove = pos;
            nextmove = pos;
            marknotplayble(nextmove, Color.lightGray);


        }
    }

    private void clearMarks() {
        for (Map.Entry<Pos, NeighbourField> fieldfield : childField.entrySet()) {
            for (Pos position : Pos.values()) {
                NeighbourField field = fieldfield.getValue().getField(position);
                JLabel label = field.getInnerCell();

                // Wenn die Hintergrundfarbe rot (x) oder blau (o) ist, überspringe die Änderung
                if (label.getBackground().equals(Color.RED) || label.getBackground().equals(Color.BLUE)) {
                    //System.out.println("clear nicht für markierte Zellen (x/o)");
                    continue;
                }

                // Ansonsten auf Weiß zurücksetzen
                label.setBackground(Color.WHITE);
            }
        }
    }

    private void marknotplayble(Pos nextmove, Color color) {

        // Prüfen, ob das Zielpanel (panelName) bereits markiert wurde
        boolean isPanelMarked = getField(nextmove).isChecked();

        // Wenn das Zielpanel markiert ist, alle Markierungen entfernen (freie Auswahl)
        if (isPanelMarked) {
            clearMarks(); // Entfernt alle grauen Markierungen
            return;
        }

        // Standardlogik: Felder außerhalb des Zielpanels grau markieren
        for (Map.Entry<Pos, NeighbourField> fieldfield : childField.entrySet()) {
            if (fieldfield.getValue().getGraphPostion() == nextmove) {
                continue;
            }

            for (Pos position : Pos.values()) {
                NeighbourField field = fieldfield.getValue().getField(position);
                JLabel label = field.getInnerCell();

                // Wenn die Zelle nicht zu dem Panel gehört und nicht mit x oder o markiert ist

                if (!label.getBackground().equals(Color.RED) &&
                        !label.getBackground().equals(Color.BLUE)) {
                    label.setBackground(color); // Grau
                }

            }
        }
    }
    //großes feld markieren für test
    public void markcell(Pos pos, int player) {

            for (Pos position : Pos.values()) {
                NeighbourField field = getField(pos).getField(position);
                JLabel label = field.getInnerCell();

                // Setze die Hintergrundfarbe je nach Spielerfarbe
                if (player == 2) {
                    label.setBackground(Color.RED); // Farbe für Spieler 1 (x)
                } else if (player == 1) {
                    label.setBackground(Color.BLUE); // Farbe für Spieler 2 (o)
                }
                label.setEnabled(false); // Zelle deaktivieren

        }
    }    @Override
    public void updateplayerturn() {
        whichplayerturn = !whichplayerturn;
        if (whichplayerturn){
            currentPlayerL.setText("Am Zug: Spieler 1");
        }else {
            currentPlayerL.setText("Am Zug: Spieler 2");
        }

    }
    @Override
    public boolean getplayerturn() {
        return whichplayerturn;
    }

}
