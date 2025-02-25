import Neighbourstruct.NeighbourGraph;

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
        instructionArea.setText("""
            ULTIMATE TIC TAC TOE
            
            Anleitung:
            Bist du bereit für die ultimative Herausforderung?
            Ultimate Tic Tac Toe hebt das klassische Tic Tac Toe auf ein völlig neues Level!

            Statt nur ein simples 3x3-Gitter zu bespielen, kämpfst du hier auf einem gigantischen Spielfeld aus 9 verbundenen kleinen Tic Tac Toe-Spielfeldern. Dein Zug entscheidet nicht nur über deine Strategie, sondern bestimmt auch, wo dein Gegner als Nächstes ziehen muss.

            Wirst du clever planen, Felder erobern und deinen Gegner austricksen? Oder wird deine nächste Entscheidung ihm den Vorteil verschaffen? Jede Runde ist ein spannender Mix aus Taktik, Vorausdenken und überraschenden Wendungen!

            Der Clou: Du spielst nicht nur, um kleine Spielfelder zu gewinnen – du musst sie so strategisch erobern, dass du auch im großen Gitter 3 in einer Reihe schaffst.

            Ultimate Tic Tac Toe – einfache Regeln, aber unendliche Möglichkeiten. Fordere Freunde heraus und erlebe, wie aus einem simplen Spiel ein packendes Duell wird!
            """);
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