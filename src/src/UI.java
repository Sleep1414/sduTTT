import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Direction.Pos;

public class UI implements Component {
    private JFrame playWindow;
    private Map<String, JLabel> cellMap;
    private Mediator mediator;


    public UI() {
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

        ticTacToeBoard.setName(position.toString()); // Setzt den Namen basierend auf der Position

        //Überprüfen, ob der Name des Panels korrekt gesetzt wurde
        System.out.println("Setze Namen des Panels auf: " + position.toString());

        Pos[] positions = Pos.values();
        for (Pos pos : positions) {
            JLabel innerCell = createInnerCell(position, pos);
            ticTacToeBoard.add(innerCell);
        }

        // Überprüfe den Namen nach der Initialisierung
        System.out.println("Name des Panels nach Setzen: " + ticTacToeBoard.getName());
        return ticTacToeBoard;
    }

    private JLabel createInnerCell(Pos position, Pos pos) {
        JLabel innerCell = new JLabel();
        innerCell.setHorizontalAlignment(SwingConstants.CENTER);
        innerCell.setOpaque(true);
        innerCell.setBackground(Color.WHITE);
        innerCell.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        String actionCommand = position + "-" + pos;
        cellMap.put(actionCommand, innerCell);


        innerCell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!innerCell.getText().isEmpty()) {
                    //nichts passiert
                    return;
                }


                String panelName = ((JPanel) innerCell.getParent()).getName();
                System.out.println("JPanel Name: " + panelName);

                if (!whichplayerturn) {
                    System.out.println("Spieler 1 at: " + position + "-" + pos);
                    clearMarks();
                    nextplayerneedto(pos, position, panelName);
                    markpanel(nextmove);
                    innerCell.setText("<html><span style=\"color: red; font-size: 20px;\">x</span></html>");
                    currentPlayerL.setText("Am Zug: Spieler 2");
                    whichplayerturn = true;
                } else {
                    System.out.println("Spieler 2 at: " + position + "-" + pos);
                    clearMarks();
                    nextplayerneedto(pos, position, panelName);
                    markpanel(nextmove);
                    innerCell.setText("<html><span style=\"color: blue; font-size: 20px;\">o</span></html>");
                    currentPlayerL.setText("Am Zug: Spieler 1");
                    whichplayerturn = false;
                }
                innerCell.removeMouseListener(this);
            }
        });

        return innerCell;
    }

    public void markpanel(Pos nextmove) {
        switch(nextmove){
            case Pos.UPPERLEFT:
                marknotplayble("UPPERLEFT", Color.lightGray);
                break;
            case Pos.UPPERMID:
                marknotplayble("UPPERMID", Color.lightGray);
                break;
            case Pos.UPPERRIGHT:
                marknotplayble("UPPERRIGHT", Color.lightGray);
                break;
            case Pos.CENTERLEFT:
                marknotplayble("CENTERLEFT", Color.lightGray);
                break;
            case Pos.CENTERMID:
                marknotplayble("CENTERMID", Color.lightGray);
                break;
            case Pos.CENTERRIGHT:
                marknotplayble("CENTERRIGHT", Color.lightGray);
                break;
            case Pos.LOWERLEFT:
                marknotplayble("LOWERLEFT", Color.lightGray);
                break;
            case Pos.LOWERMID:
                marknotplayble("LOWERMID", Color.lightGray);
                break;
            case Pos.LOWERRIGHT:
                marknotplayble("LOWERRIGHT", Color.lightGray);
                break;
        }
    }

    private void marknotplayble(String panelName, Color color) {
        for (Map.Entry<String, JLabel> entry : cellMap.entrySet()) {
            String key = entry.getKey();
            JLabel label = entry.getValue();

            if (!key.startsWith(panelName)) {
                    label.setBackground(color);
            }
        }
    }

    public void markcell(String panelName, Color color) {
        for (Map.Entry<String, JLabel> entry : cellMap.entrySet()) {
            String key = entry.getKey();
            JLabel label = entry.getValue();

            if (key.startsWith(panelName)) {
                    label.setBackground(color);
                    label.setEnabled(false);
            }
        }
    }

    private void clearMarks() {
        for (Map.Entry<String, JLabel> entry : cellMap.entrySet()) {
            JLabel label = entry.getValue();
            label.setBackground(Color.WHITE);
        }
    }


    private void nextplayerneedto (Pos pos, Pos position, String name){
            lastmove = pos;
            nextmove = pos;
            System.out.println(nextmove);
        }

    @Override
    public void setMediator(Mediator mediator) {
       this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "UI";
    }
}

    
