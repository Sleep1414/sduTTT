import javax.swing.*;
import java.awt.*;

public class UI {

   public UI() {
       createplaywindow();
    }


    public void createplaywindow() {
        //window
        JFrame playwindow = new JFrame("Ultimate TicTacToe");
        playwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playwindow.setSize(600, 600);
        playwindow.setLocationRelativeTo(null);
        playwindow.setBackground(Color.DARK_GRAY);

        //main layout
        JPanel mainpanel = new JPanel(new BorderLayout());

        //back button - obere Leiste
        JPanel leiste = new JPanel(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backbutton = new JButton("back");

        backbutton.addActionListener(new BackButtonListener(playwindow));

        top.add(backbutton);
        leiste.add(top, BorderLayout.NORTH);

        //gameboard
        JPanel game = new JPanel();
        game.setLayout(new BorderLayout());
        game.setBackground(Color.PINK);


        //adding
        mainpanel.add(leiste, BorderLayout.NORTH);
        mainpanel.add(game, BorderLayout.CENTER);

        playwindow.add(mainpanel);
        playwindow.setVisible(true);
    }
}
