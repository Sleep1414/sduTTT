import pos.Pos;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NeighbourFieldField extends NeighbourField implements Subscriber {
    private HashMap <Pos,NeighbourField> firstOrderField;

    NeighbourFieldField(NeighbourFieldField parent) {
        super(parent);
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
