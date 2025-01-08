import Direction.Pos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartWindow extends JFrame {
    public StartWindow() { //Fenstereinstellungen
        setTitle("TicTacToe - Start");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        getContentPane().setBackground(Color.GREEN);


        JTextArea instructionArea = new JTextArea(); //Spielanleitung
        instructionArea.setText("Willkommen zum TicTacToe Spiel - Anleitung folgt");
        instructionArea.setEditable(false);
        instructionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionArea.setWrapStyleWord(true);
        instructionArea.setLineWrap(true);
        instructionArea.setBackground(Color.GREEN);
        instructionArea.setBackground((Color.WHITE));

        JScrollPane scrollPane = new JScrollPane(instructionArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(Color.GREEN);

        JButton startButton = new JButton("Spiel starten");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                NeighbourGraph spielfeld1 = new NeighbourGraph();


                //testmarkierung
                spielfeld1.markcell(Pos.UPPERLEFT, 1);
                spielfeld1.markcell(Pos.UPPERRIGHT, 2);

                dispose();
            }
        });

        JButton closeButton = new JButton("Beenden");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);

            }
        });

        buttonPanel.add(startButton);
        buttonPanel.add(closeButton);

        add(buttonPanel, BorderLayout.SOUTH);

    }

}






