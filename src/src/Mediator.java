import Direction.Pos;

import javax.swing.*;

public interface Mediator {
   void notifygraph(JLabel innercell, Pos position, Pos pos);
   void registerComponent(Component component);
}
