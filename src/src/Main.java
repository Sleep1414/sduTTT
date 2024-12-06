import Direction.Pos;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
      //NeighbourField nei = new NeighbourField();
        //System.out.println(nei.isChecked());



        NeighbourGraph gamefield = new NeighbourGraph();

        UI spielfeld1 = new UI(gamefield);

        spielfeld1.markField(Pos.CENTERMID,Pos.CENTERRIGHT);


        //System.out.println( gamefield.getField(Pos.CENTERLEFT));

    }
}