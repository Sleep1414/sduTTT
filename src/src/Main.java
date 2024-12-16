import Direction.Pos;

import java.awt.*;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
      //NeighbourField nei = new NeighbourField();
        //System.out.println(nei.isChecked());

        StartWindow startWindow = new StartWindow();
        startWindow.setVisible(true);

        //NeighbourGraph gamefield = new NeighbourGraph();

        //UI spielfeld1 = new UI(gamefield);
        ConcretMediator mediator = new ConcretMediator();
        NeighbourGraph gamefield = new NeighbourGraph();
        UI spielfeld1 = new UI();
        mediator.registerComponent(gamefield);
        mediator.registerComponent(spielfeld1);




        //mark: Player1 == true = Red
        //mark: Player2 == false = Blue


    }
}