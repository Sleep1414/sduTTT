import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import Direction.Pos;

public class UI {
    private JFrame playWindow;
    private NeighbourGraph gameField;
    private Map<String, JLabel> cellMap;


    public UI(NeighbourGraph gamefield) {
        this.gameField = gamefield;
        this.cellMap = new HashMap<>();
        createPlayWindow();
    }

    //true == player1
    //false == player2
    boolean whichplayerturn = true;
    JLabel currentPlayerL;
    Pos lastmove;
    Pos nextmove;


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
            JLabel innerCell = createInnerCell(position, pos);
            ticTacToeBoard.add(innerCell);
        }
        return ticTacToeBoard;
    }


    private JLabel createInnerCell(Pos position, Pos pos) {
        JLabel innerCell = new JLabel();
        innerCell.setHorizontalAlignment(SwingConstants.CENTER);
        innerCell.setOpaque(true);
        innerCell.setBackground(Color.WHITE); // Hintergrundfarbe
        innerCell.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Rahmen

        String actionCommand = position + "-" + pos;
        cellMap.put(actionCommand, innerCell);


        //funktion wo geschaut wird wo man hinplacen darf

        innerCell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!innerCell.getText().isEmpty()) {
                    // Feld bereits belegt, keine Aktion
                    return;
                }

                if (!whichplayerturn) {
                    System.out.println("Spieler 1 at: " + position+"-"+pos);
                    nextplayerneedto(pos, position);
                    // Spieler 1
                    innerCell.setText("<html><span style=\"color: red; font-size: 20px;\">x</span></html>");
                    //System.out.println("Spieler 1 ist am Zug");
                    currentPlayerL.setText("Am Zug: Spieler 2");
                    whichplayerturn = true;

                } else {
                    System.out.println("Spieler 2 at: " + position+"-"+pos);
                    nextplayerneedto(pos, position);
                    // Spieler 2
                    innerCell.setText("<html><span style=\"color: blue; font-size: 20px;\">o</span></html>");
                    //System.out.println("Spieler 2 ist am Zug");
                    currentPlayerL.setText("Am Zug: Spieler 1");
                    whichplayerturn = false;

                }

                // Label nach dem Setzen des Texts nicht mehr klickbar machen
                innerCell.removeMouseListener(this);
            }
        });

        return innerCell;
    }

    private void nextplayerneedto(Pos pos, Pos position) {
        //pos = großes feld
        //position = kleines feld
        lastmove = pos;
        switch(lastmove) {
            case position.UPPERLEFT:
                nextmove = Pos.UPPERLEFT;
                marknextfield(nextmove);
                System.out.println(nextmove);
                break;
            case position.UPPERMID:
                System.out.println(nextmove = Pos.UPPERMID);
                nextmove = Pos.UPPERMID;
                break;
            case position.UPPERRIGHT:
                nextmove = Pos.UPPERRIGHT;
                System.out.println(nextmove);
                break;
            case position.CENTERLEFT:
                nextmove = Pos.UPPERRIGHT;
                System.out.println(nextmove);
                break;
            case position.CENTERMID:
                nextmove = Pos.CENTERMID;
                System.out.println(nextmove);
                break;
            case position.CENTERRIGHT:
                System.out.println(nextmove = Pos.CENTERRIGHT);
                nextmove = Pos.CENTERRIGHT;
                break;
            case position.LOWERLEFT:
                nextmove = Pos.LOWERLEFT;
                System.out.println(nextmove);
                break;
            case position.LOWERMID:
                nextmove = Pos.LOWERMID;
                System.out.println(nextmove);
                break;
            case position.LOWERRIGHT:
                nextmove = Pos.LOWERRIGHT;
                System.out.println(nextmove);
                break;
        }
    }

    private void marknextfield(Pos nextmove) {



    }

}