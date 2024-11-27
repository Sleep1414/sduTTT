package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackButtonListener implements ActionListener {

//testing
    private JFrame tmp;

    public BackButtonListener(JFrame tmp) {
        this.tmp = tmp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println("Back Button ausgeführt");
        tmp.dispose();
    }
}
