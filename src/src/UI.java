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

        // finalize and display the window
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

        // back button and panel
        JButton backButton = new JButton("Back");
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(backButton);


        // current player label
        JLabel currentPlayerL = new JLabel("Am Zug: Spieler ");
        currentPlayerL.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(currentPlayerL);

        // assemble top bar
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
        // Button in die Map hinzufügen
        cellMap.put(actionCommand, innerCell);


        innerCell.addActionListener(e -> {
            String field = e.getActionCommand();
            System.out.println("Gedrücktes Feld: " + field);

            innerCell.setBackground(Color.yellow);
            innerCell.setEnabled(false);

        });

        return innerCell;
    }

    public void markField(Pos outerPosition, Pos innerPosition) {
        // Kombiniere die Positionen zu einem Schlüssel
        String fieldKey = outerPosition + "-" + innerPosition;

        // Hole den Button aus der Map
        JButton button = cellMap.get(fieldKey);
        if (button != null && button.isEnabled()) {
            button.setBackground(Color.YELLOW); // Markiere das Feld
            button.setEnabled(false);          // Deaktiviere den Button
        } else {
            System.out.println("Feld " + fieldKey + " existiert nicht oder ist bereits markiert.");
        }
    }


}