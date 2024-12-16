import Direction.Pos;

import javax.swing.*;

public class ConcretMediator implements Mediator{

    private NeighbourGraph gamegraph;
    private UI gamefield;



    @Override
    public void notifygraph(JLabel innercell, Pos position, Pos pos) {

    }

    @Override
    public void registerComponent(Component component) {
            switch (component.getName()){
                case "NeighbourGraph":
                    this.gamegraph = (NeighbourGraph) component;
                    break;
                case "UI":
                    this.gamefield = (UI) component;
                    break;
                default:
                    break;

            }

    }
}
