package visuals;

import javax.swing.*;
import java.awt.*;
import pos.Pos;

public class UI {

    public UI() {
        createplaywindow();
    }



    public void createplaywindow() {

        JLabel currentplayerL;

        //window
        JFrame playwindow = new JFrame("Ultimate TicTacToe");
        playwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playwindow.setSize(600, 600);
        playwindow.setLocationRelativeTo(null);
        playwindow.setBackground(Color.DARK_GRAY);

        //main layout
        JPanel mainpanel = new JPanel(new BorderLayout());

        //back button - obere Leiste
        JPanel topleiste = new JPanel(new BorderLayout());

        //button
        JButton backbutton = new JButton("Back");
        JPanel leftpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftpanel.add(backbutton);
        backbutton.addActionListener(new BackButtonListener(playwindow));

        //current player
        currentplayerL = new JLabel("Am Zug: Spieler ");
        currentplayerL.setFont(new Font("Arial", Font.BOLD,16));
        JPanel rightpanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightpanel.add(currentplayerL);

        //add
        topleiste.add(leftpanel, BorderLayout.WEST);
        topleiste.add(rightpanel, BorderLayout.EAST);

        //gameboard
        JPanel gameboard = new JPanel(new GridLayout(3,3));
        gameboard.setBackground(Color.PINK);

        // 9x9 groß
        Pos[] positions = Pos.values();
        for (int i = 0; i < positions.length; i++) {
            JPanel ticTacToeBoard = new JPanel(new GridLayout(3, 3));
            ticTacToeBoard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
            ticTacToeBoard.setBackground(Color.LIGHT_GRAY);

            // 9x9 klein
            for (int j = 0; j < positions.length; j++) {

                JButton innerCell = new JButton();
                innerCell.setPreferredSize(new Dimension(20, 20));


                String actionCommand = positions[i] + "-" + positions[j];
                innerCell.setActionCommand(actionCommand);


                innerCell.addActionListener(e -> {
                    String field = e.getActionCommand();
                    System.out.println("Gedrücktes Feld: " + field);
                });

                ticTacToeBoard.add(innerCell);

            }

            gameboard.add(ticTacToeBoard);
        }


        //adding
        mainpanel.add(topleiste,BorderLayout.NORTH);
        mainpanel.add(gameboard, BorderLayout.CENTER);

        playwindow.add(mainpanel);
        playwindow.setVisible(true);
    }
}