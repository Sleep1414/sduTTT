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

                ConcretMediator mediator = new ConcretMediator();
                NeighbourGraph gamefield = new NeighbourGraph();
                UI spielfeld1 = new UI();
                mediator.registerComponent(gamefield);
                mediator.registerComponent(spielfeld1);


                //testmarkierung
                spielfeld1.markcell(String.valueOf(Pos.UPPERLEFT), Color.blue);
                spielfeld1.markcell(String.valueOf(Pos.UPPERRIGHT), Color.red);

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






