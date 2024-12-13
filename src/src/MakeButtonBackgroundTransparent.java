import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class MakeButtonBackgroundTransparent {

    public static void makeButtonTransparent(JButton button) {
        button.setOpaque(false); // Entfernt den festen Hintergrund
        button.setContentAreaFilled(false); // Entfernt die Füllung
        button.setBorderPainted(false); // Optional: Entfernt den Rand (falls nicht gewünscht)
        button.setBackground(new Color(0, 0, 0, 0)); // Setzt eine vollständig transparente Farbe
        button.setForeground(button.getForeground()); // Beibehaltung der Textfarbe
    }

}