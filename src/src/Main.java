import Direction.Pos;
import visuals.UI;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        UI spielfeld1 = new UI();

        NeighbourGraph gamefield = new NeighbourGraph();
        gamefield.getField(Pos.CENTERLEFT).getField(Pos.CENTERLEFT).check(1);
        gamefield.getField(Pos.CENTERLEFT).getField(Pos.UPPERLEFT).check(1);
        gamefield.getField(Pos.CENTERLEFT).getField(Pos.LOWERLEFT).check(1);
        System.out.println( gamefield.getField(Pos.CENTERLEFT).getCheck());



    }
}