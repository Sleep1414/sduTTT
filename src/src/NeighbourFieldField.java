import Direction.Pos;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NeighbourFieldField extends NeighbourField implements Subscriber {
    HashMap <Pos,NeighbourField> childField;

    NeighbourFieldField(NeighbourFieldField parent) {
        super(parent);
         // Assuming the parent is not used or handled elsewhere.

        // Initialize the grid (3x3 Tic-Tac-Toe)
        childField = new HashMap<>();

        // Create NeighbourField objects for each position
        NeighbourField upperLeft = new NeighbourField(this);
        NeighbourField upperMid = new NeighbourField(this);
        NeighbourField upperRight = new NeighbourField(this);
        NeighbourField centerLeft = new NeighbourField(this);
        NeighbourField centerMid = new NeighbourField(this);
        NeighbourField centerRight = new NeighbourField(this);
        NeighbourField lowerLeft = new NeighbourField(this);
        NeighbourField lowerMid = new NeighbourField(this);
        NeighbourField lowerRight = new NeighbourField(this);

        // Put these fields in the map
        childField.put(Pos.UPPERLEFT, upperLeft);
        childField.put(Pos.UPPERMID, upperMid);
        childField.put(Pos.UPPERRIGHT, upperRight);
        childField.put(Pos.CENTERLEFT, centerLeft);
        childField.put(Pos.CENTERMID, centerMid);
        childField.put(Pos.CENTERRIGHT, centerRight);
        childField.put(Pos.LOWERLEFT, lowerLeft);
        childField.put(Pos.LOWERMID, lowerMid);
        childField.put(Pos.LOWERRIGHT, lowerRight);

        // Set neighbors for each NeighbourField
        setNeighbours(upperLeft, upperMid, centerLeft, null, null, null, lowerLeft, lowerMid, lowerRight);
        setNeighbours(upperMid, upperLeft, centerMid, upperRight, null, null, lowerMid, lowerLeft, lowerRight);
        setNeighbours(upperRight, upperMid, centerRight, null, null, null, lowerRight, lowerMid, lowerLeft);

        setNeighbours(centerLeft, upperLeft, centerMid, centerRight, null, null, lowerLeft, lowerMid, lowerRight);
        setNeighbours(centerMid, upperMid, centerLeft, centerRight, null, null, lowerMid, lowerLeft, lowerRight);
        setNeighbours(centerRight, upperRight, centerMid, centerLeft, null, null, lowerRight, lowerMid, lowerLeft);

        setNeighbours(lowerLeft, lowerMid, centerLeft, null, null, null, upperLeft, upperMid, upperRight);
        setNeighbours(lowerMid, lowerLeft, centerMid, lowerRight, null, null, upperMid, upperLeft, upperRight);
        setNeighbours(lowerRight, lowerMid, centerRight, null, null, null, upperRight, upperMid, upperLeft);
    }

    // Helper method to set neighbors for a NeighbourField
    private void setNeighbours(NeighbourField field, NeighbourField upper, NeighbourField mid, NeighbourField right,
                               NeighbourField left, NeighbourField bottom, NeighbourField lower, NeighbourField lowerMid,
                               NeighbourField lowerRight) {
        field.putNeighbour(Pos.UPPERLEFT, upper);
        field.putNeighbour(Pos.UPPERMID, mid);
        field.putNeighbour(Pos.UPPERRIGHT, right);
        field.putNeighbour(Pos.CENTERLEFT, left);
        field.putNeighbour(Pos.CENTERMID, field);
        field.putNeighbour(Pos.CENTERRIGHT, right);
        field.putNeighbour(Pos.LOWERLEFT, lower);
        field.putNeighbour(Pos.LOWERMID, lowerMid);
        field.putNeighbour(Pos.LOWERRIGHT, lowerRight);

    }

    private checkState evaluate(NeighbourField newSetField){
        if( !newSetField.isChecked()){
            return checkState.UNCHECKED;
        }
        Iterator<Map.Entry<Pos, NeighbourField>> fields = newSetField.getNeighbours().entrySet().iterator();

        for (int i = 0 ; i < 3; i++) {
           var nextfield = fields.next();
           var state = evaluate(nextfield.getValue(), nextfield.getValue().getCheck(), nextfield.getKey(),  0,newSetField);
           if( checkState.UNCHECKED != state){
               return state;
           }
        }
        return checkState.UNCHECKED;
        }

    private checkState evaluate(NeighbourField field, checkState previousState, Pos dir, int count,NeighbourField previous){
        if(previousState == field.getCheck() && count > 0){
            return previousState;
        }
        if (!field.isChecked()){
            return checkState.UNCHECKED;
        }
        if(field.getCheck() != previousState){
            return checkState.UNCHECKED;
        }
        if(!field.getNeighbours().containsKey(dir)){
            return switch (dir) {
                case UPPERLEFT ->
                        evaluate(previous.neighbours.get(Pos.LOWERRIGHT), field.getCheck(), Pos.LOWERRIGHT, count++, field);
                case UPPERMID ->
                        evaluate(previous.neighbours.get(Pos.LOWERMID), field.getCheck(), Pos.LOWERMID, count++, field);
                case UPPERRIGHT ->
                        evaluate(previous.neighbours.get(Pos.LOWERLEFT), field.getCheck(), Pos.LOWERLEFT, count++, field);
                case CENTERLEFT ->
                        evaluate(previous.neighbours.get(Pos.CENTERRIGHT), field.getCheck(), Pos.CENTERRIGHT, count++, field);
                case CENTERRIGHT ->
                        evaluate(previous.neighbours.get(Pos.CENTERLEFT), field.getCheck(), Pos.CENTERLEFT, count++, field);
                case LOWERLEFT ->
                        evaluate(previous.neighbours.get(Pos.UPPERRIGHT), field.getCheck(), Pos.UPPERRIGHT, count++, field);
                case LOWERMID ->
                        evaluate(previous.neighbours.get(Pos.UPPERMID), field.getCheck(), Pos.UPPERMID, count++, field);
                case LOWERRIGHT ->
                        evaluate(previous.neighbours.get(Pos.UPPERLEFT), field.getCheck(), Pos.UPPERLEFT, count++, field);
                default -> throw new IllegalArgumentException("Unknown direction: " + this);
            };

        }
        return evaluate(field.neighbours.get(dir), previousState, dir, count++,field);

    }

    @Override
    public void update(NeighbourField field) {
          this.check = evaluate(field);
    }
}
