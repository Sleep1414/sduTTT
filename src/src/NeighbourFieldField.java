import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class NeighbourFieldField extends NeighbourField implements Subscriber {
    private HashMap <Pos,NeighbourField> firstOrderField;

    NeighbourFieldField(NeighbourFieldField parent) {
        super(parent);
    }

    private checkState evaluate(NeighbourField newSetField){
        if( !newSetField.isChecked()){
            return checkState.UNCHECKED;
        }
        AtomicInteger i = new AtomicInteger();
         newSetField.getNeighbours().forEach((position,newfield)-> {if (i.get() < 4){evaluate(newfield, newSetField.getCheck() ,position,0 );}; i.getAndIncrement(); });
    }
    private checkState evaluate(NeighbourField field, checkState previousState, Pos dir, int count){

    }

    @Override
    public void update(NeighbourField field) {
          this.check = evaluate(field);
    }
}
