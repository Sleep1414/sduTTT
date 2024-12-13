import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import Direction.Pos;

public class UI {
    private JFrame playWindow;
    private NeighbourGraph gameField;
    private Map<String, JButton> cellMap;

    public UI(NeighbourGraph gamefield) {
        this.gameField = gamefield;
        this.cellMap = new HashMap<>();
        createPlayWindow();
    }

    //true == player1
    //false == player2
    boolean whichplayerturn = true;
    JLabel currentPlayerL;

    private void createPlayWindow() {
        // window
        playWindow = createMainWindow();

        // main layout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // top bar
        JPanel topBar = createTopBar(playWindow);

        // game board
        JPanel gameBoard = createGameBoard();

        // adding components to the main panel
        mainPanel.add(topBar, BorderLayout.NORTH);
        mainPanel.add(gameBoard, BorderLayout.CENTER);

        //final
        playWindow.add(mainPanel);
        playWindow.setVisible(true);
    }

    private JFrame createMainWindow() {
        JFrame playWindow = new JFrame("Ultimate TicTacToe");
        playWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playWindow.setSize(600, 600);
        playWindow.setLocationRelativeTo(null);
        playWindow.setBackground(Color.DARK_GRAY);
        return playWindow;
    }

    private JPanel createTopBar(JFrame playWindow) {
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
        currentPlayerL= new JLabel("Es beginnt Spieler 1");
        currentPlayerL.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(currentPlayerL);

        // Hinzufügen der Panels zum TopBar
        topBar.add(leftPanel, BorderLayout.WEST);
        topBar.add(rightPanel, BorderLayout.EAST);
        return topBar;
    }


    private JPanel createGameBoard() {
        JPanel gameBoard = new JPanel(new GridLayout(3, 3));
        gameBoard.setBackground(Color.PINK);

        Pos[] positions = Pos.values();
        for (Pos position : positions) {
            JPanel ticTacToeBoard = createTicTacToeBoard(position);
            gameBoard.add(ticTacToeBoard);
        }
        return gameBoard;
    }

    private JPanel createTicTacToeBoard(Pos position) {
        JPanel ticTacToeBoard = new JPanel(new GridLayout(3, 3));
        ticTacToeBoard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        ticTacToeBoard.setBackground(Color.LIGHT_GRAY);

        Pos[] positions = Pos.values();
        for (Pos pos : positions) {
            JButton innerCell = createInnerCell(position, pos);
            ticTacToeBoard.add(innerCell);
        }
        return ticTacToeBoard;
    }

    private JButton createInnerCell(Pos position, Pos pos) {
        JButton innerCell = new JButton();
        innerCell.setPreferredSize(new Dimension(20, 20));

        String actionCommand = position + "-" + pos;
        cellMap.put(actionCommand, innerCell);


        innerCell.setActionCommand(actionCommand);
        innerCell.addActionListener(e -> {
            String field = e.getActionCommand();
            System.out.println("Gedrücktes Feld: " + field);

            if(whichplayerturn == false){
                System.out.println("Spieler 1 ist am Zug");
                currentPlayerL.setText("Am Zug: Spieler 1");
                whichplayerturn = true;
            }else{
                System.out.println("Spieler 2 ist am Zug");
                currentPlayerL.setText("Am Zug: Spieler 2");
                whichplayerturn = false;
            }

            innerCell.setBackground(Color.YELLOW);
            innerCell.setEnabled(false);
        });
        return innerCell;
    }

    public void markField(Pos outerPosition, Pos innerPosition, boolean whichplayer) {
        String fieldKey = outerPosition + "-" + innerPosition;

        JButton button = cellMap.get(fieldKey);
        if (button != null && button.isEnabled()) {
            button.setOpaque(true);  // Stelle sicher, dass der Button die Farbe anzeigt
            button.setBorderPainted(false); // Deaktiviere den Standardrahmen

            // Setze die Hintergrundfarbe basierend auf dem Spieler
            if (whichplayer) {
                button.setBackground(Color.RED);  // Farbe für Spieler 1
            } else {
                button.setBackground(Color.BLUE); // Farbe für Spieler 2
            }

            button.setEnabled(false);  // Deaktiviere den Button
        } else {
            System.out.println("Feld " + fieldKey + " existiert nicht oder ist bereits markiert.");
        }
    }



    public void markLargeField(Pos outerPosition, boolean player1win) {
        Color backgroundColor = player1win ? Color.RED : Color.BLUE;

        for (Pos innerPosition : Pos.values()) {
            String fieldKey = outerPosition + "-" + innerPosition;

            JButton button = cellMap.get(fieldKey);
            if (button != null && button.isEnabled()) {
                button.setOpaque(true);
                button.setBorderPainted(false);
                button.setBackground(backgroundColor);
                button.setEnabled(false);
            }
        }
    }
}