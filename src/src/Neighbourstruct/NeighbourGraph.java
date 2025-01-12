package Neighbourstruct;

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
    private final JLabel currentPlayerL;

    private final JFrame playWindow;

    public NeighbourGraph() {
        super(null, null, null);
        // window
        playWindow = new JFrame("Ultimate TicTacToe");
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
        backButton.addActionListener(_ -> {
            // Schließe das aktuelle Fenster
            playWindow.dispose();  // Schließt das Fenster
            System.out.println("Back-Button gedrückt: Fenster wird geschlossen.");
        });

        // Aktuellen Spieler anzeigen
        currentPlayerL = new JLabel("Es beginnt Spieler 1");
        currentPlayerL.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(currentPlayerL);


        //Mittlerer Button
        JButton middleButton = new JButton("restart Game");
        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        middlePanel.add(middleButton);

        middleButton.addActionListener(e -> {
            playWindow.dispose();
            System.out.println("Game wird neu gestartet");
            new NeighbourGraph();
        });

        // Hinzufügen der Panels zum TopBar
        topBar.add(leftPanel, BorderLayout.WEST);
        topBar.add(middlePanel, BorderLayout.CENTER);
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
        // Set neighbors for each Neighbourstruct.NeighbourField
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
        if (Objects.equals(field.getClass().getName(), "Neighbourstruct.NeighbourFieldField")) {
            this.check = evaluate(field);
            if(check != checkState.UNCHECKED) {
                onWin();}
        } else if (Objects.equals(field.getClass().getName(), "Neighbourstruct.NeighbourField")){
            clearMarks();
            Pos pos = field.getFieldFieldPosition();
            lastmove = pos;
            nextmove = pos;
            marknotplayble(nextmove);


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

    private void marksnextmove(Pos nextmove) {
        for (Map.Entry<Pos, NeighbourField> fieldfield : childField.entrySet()) {
            if (fieldfield.getValue().isChecked()|| fieldfield.getValue().getGraphPostion()==nextmove) {
                //System.out.println("clear nicht für markierte Zellen (x/o)");
                continue;
            }
            for (Pos position : Pos.values()) {
                NeighbourField field = fieldfield.getValue().getField(position);
                JLabel label = field.getInnerCell();

                // Wenn die Hintergrundfarbe rot (x) oder blau (o) ist, überspringe die Änderung


                // Ansonsten auf Weiß zurücksetzen
                label.setBackground(Color.WHITE);
            }
        }
    }

    private void marknotplayble(Pos nextmove) {

        // Prüfen, ob das Zielpanel (panelName) bereits markiert wurde
        boolean isPanelMarked = getField(nextmove).isChecked();

        // Wenn das Zielpanel markiert ist, alle Markierungen entfernen (freie Auswahl)
        if (isPanelMarked) {
            marksnextmove(nextmove); // Entfernt alle grauen Markierungen
            return;
        }

        // Standardlogik: Felder außerhalb des Zielpanels grau markieren
        for (Map.Entry<Pos, NeighbourField> fieldfield : childField.entrySet()) {


            for (Pos position : Pos.values()) {
                NeighbourField field = fieldfield.getValue().getField(position);
                JLabel label = field.getInnerCell();
                if (fieldfield.getValue().getGraphPostion() == nextmove) {
                    label.setBackground(Color.WHITE);

                }
                // Wenn die Zelle nicht zu dem Panel gehört und nicht mit x oder o markiert ist

                else if (!label.getBackground().equals(Color.RED) &&
                        !label.getBackground().equals(Color.BLUE)) {

                    label.setBackground(Color.lightGray); // Grau
                }

            }
        }
    }
    private void onWin(){
        JFrame winnerFrame = new JFrame("Spiel beendet!");
        winnerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        winnerFrame.setSize(400, 200);
        winnerFrame.setLayout(new BorderLayout(10, 10));

        JLabel winnerLabel = new JLabel("", SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 16));

        if (check == checkState.PLAYER1) {
            winnerLabel.setText("Der Gewinner ist: Spieler X (1)!");
            winnerLabel.setForeground(Color.BLUE);

        } else if(check == checkState.PLAYER2) {

            winnerLabel.setText("Der Gewinner ist: Spieler O (2)!");
            winnerLabel.setForeground(Color.red);

        }

        winnerFrame.add(winnerLabel, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton restartButton = new JButton("Neustarten");
        restartButton.addActionListener(e -> {
            winnerFrame.dispose(); // Gewinnerfenster schließen
            new NeighbourGraph(); // Neues Spiel starten
        });


        JButton closeButton = new JButton("Schließen");
        closeButton.addActionListener(e -> winnerFrame.dispose());


        buttonPanel.add(restartButton);
        buttonPanel.add(closeButton);


        winnerFrame.add(buttonPanel, BorderLayout.SOUTH);

        winnerFrame.setLocationRelativeTo(null);
        winnerFrame.setVisible(true);
    }

    @Override
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
