import Neighbourstruct.NeighbourGraph;

import javax.swing.*;
import java.awt.*;

public class GewinnerScreen {

    public GewinnerScreen(boolean gewinner) {
        JFrame winnerFrame = new JFrame("Spiel beendet!");
        winnerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        winnerFrame.setSize(400, 200);
        winnerFrame.setLayout(new BorderLayout(10, 10));

        String winnerMessage;
        if (gewinner) {
            winnerMessage = "Der Gewinner ist: Spieler X (1)!";
        } else {
            winnerMessage = "Der Gewinner ist: Spieler O (2)!";
        }

        JLabel winnerLabel = new JLabel(winnerMessage, SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        if(gewinner){ winnerLabel.setForeground(Color.BLUE);
        }else{ winnerLabel.setForeground(Color.red); }
        winnerFrame.add(winnerLabel, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton restartButton = new JButton("Neustarten");
        restartButton.addActionListener(e -> {
            winnerFrame.dispose(); // Gewinnerfenster schließen
            NeighbourGraph spielfeld1 = new NeighbourGraph(); // Neues Spiel starten
        });


        JButton closeButton = new JButton("Schließen");
        closeButton.addActionListener(e -> winnerFrame.dispose());


        buttonPanel.add(restartButton);
        buttonPanel.add(closeButton);


        winnerFrame.add(buttonPanel, BorderLayout.SOUTH);

        winnerFrame.setLocationRelativeTo(null);
        winnerFrame.setVisible(true);
    }


}
