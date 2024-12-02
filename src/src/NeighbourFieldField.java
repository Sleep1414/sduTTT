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
           evaluate(nextfield.getValue(), nextfield.getValue().getCheck(), nextfield.getKey(),1);

        }

        }

    private checkState evaluate(NeighbourField field, checkState previousState, Pos dir, int count){ return null;

    }

    @Override
    public void update(NeighbourField field) {
          this.check = evaluate(field);
    }
}
