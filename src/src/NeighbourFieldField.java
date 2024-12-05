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
        if(this.getClass() != NeighbourFieldField.class){
            return;
        }

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
        setNeighbours(upperLeft,null,null,null,null,upperMid,null,centerLeft,centerMid);
        setNeighbours(upperMid,null,null,null,upperLeft,upperRight,null,centerMid,null);
        setNeighbours(upperRight,null,null,null,upperMid,null,centerMid,centerRight,null );

        setNeighbours(centerLeft,null, upperLeft,null,null,centerMid,null,lowerLeft,null);
        setNeighbours(centerMid, upperLeft, upperMid,upperRight,centerLeft,centerRight,lowerLeft,lowerMid,lowerRight);
        setNeighbours(centerRight, null,upperRight,null,centerMid,null,null,lowerRight,null);

        setNeighbours(lowerLeft, null, centerLeft,centerMid,null,lowerMid,null,null,null);
        setNeighbours(lowerMid, null,centerMid,null,lowerLeft, lowerRight,null,null,null);
        setNeighbours(lowerRight, centerMid,centerRight,null,lowerMid,null,null,null,null);
    }

    // Helper method to set neighbors for a NeighbourField
    protected void setNeighbours(NeighbourField field, NeighbourField upperLeft, NeighbourField upperMid, NeighbourField upperRight, NeighbourField left,
                               NeighbourField right, NeighbourField lowerLeft, NeighbourField lowerMid,
                               NeighbourField lowerRight) {

        if (upperLeft != null) { field.putNeighbour(Pos.UPPERLEFT, upperLeft);}
        if (upperMid != null) {field.putNeighbour(Pos.UPPERMID, upperMid);}
        if (upperRight != null) {field.putNeighbour(Pos.UPPERRIGHT, upperRight);}
        if (right != null) {field.putNeighbour(Pos.CENTERRIGHT, right);}
        if (lowerRight != null) {field.putNeighbour(Pos.LOWERRIGHT, lowerRight);}
        if (lowerMid != null) {field.putNeighbour(Pos.LOWERMID, lowerMid);}
        if (lowerLeft != null) {field.putNeighbour(Pos.LOWERLEFT, lowerLeft);}
        if (left != null) {field.putNeighbour(Pos.CENTERLEFT, left);}


    }

    private checkState evaluate(NeighbourField newSetField){
        if( !newSetField.isChecked()){
            return checkState.UNCHECKED;
        }
        Iterator<Map.Entry<Pos, NeighbourField>> fields = newSetField.getNeighbours().entrySet().iterator();

        for (int i = 0 ; i < 3; i++) {
            var nextfield = fields.next();
           if(nextfield.getValue() == null){
               i--;
               continue;
           }
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
        if (!field.isChecked() || count > 0){
            return checkState.UNCHECKED;
        }
        if(field.getCheck() != previousState){
            return checkState.UNCHECKED;
        }
        if(!field.getNeighbours().containsKey(dir)){
            return switch (dir) {
                case UPPERLEFT ->
                        evaluate(previous.neighbours.get(Pos.LOWERRIGHT), field.getCheck(), Pos.LOWERRIGHT, count + 1, field);
                case UPPERMID ->
                        evaluate(previous.neighbours.get(Pos.LOWERMID), field.getCheck(), Pos.LOWERMID, count + 1, field);
                case UPPERRIGHT ->
                        evaluate(previous.neighbours.get(Pos.LOWERLEFT), field.getCheck(), Pos.LOWERLEFT, count + 1, field);
                case CENTERLEFT ->
                        evaluate(previous.neighbours.get(Pos.CENTERRIGHT), field.getCheck(), Pos.CENTERRIGHT, count + 1, field);
                case CENTERRIGHT ->
                        evaluate(previous.neighbours.get(Pos.CENTERLEFT), field.getCheck(), Pos.CENTERLEFT, count + 1, field);
                case LOWERLEFT ->
                        evaluate(previous.neighbours.get(Pos.UPPERRIGHT), field.getCheck(), Pos.UPPERRIGHT, count + 1, field);
                case LOWERMID ->
                        evaluate(previous.neighbours.get(Pos.UPPERMID), field.getCheck(), Pos.UPPERMID, count + 1, field);
                case LOWERRIGHT ->
                        evaluate(previous.neighbours.get(Pos.UPPERLEFT), field.getCheck(), Pos.UPPERLEFT, count + 1, field);
                default -> throw new IllegalArgumentException("Unknown direction: " + this);
            };

        }
        return evaluate(field.neighbours.get(dir), previousState, dir, count + 1 ,field);

    }
    @Override
    public NeighbourField getField(Pos direction){
        return childField.get(direction);
    }

    @Override
    public void update(NeighbourField field) {
          this.check = evaluate(field);
    }
}
