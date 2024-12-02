import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public  class NeighbourField  {
    Map<Pos, NeighbourField> neighbours;
    ArrayList <Subscriber> subscribers;

    enum checkState{ PLAYER1, PLAYER2, UNCHECKED}
    checkState check;


    public void notifySubscriber() {
        for (Subscriber subscriber : subscribers ) {
                subscriber.update(this);
        }
    }


    NeighbourField(NeighbourFieldField parent){
        this.check = checkState.UNCHECKED;
        subscribers = new ArrayList<Subscriber>();
        subscribers.add(parent);
        neighbours = new HashMap<Pos,NeighbourField>();

    }

    public void putNeighbour(Pos orientation, NeighbourField field) {
        neighbours.put(orientation, field);
    }

    public void check(int playerID){
        if (playerID == 1){check = checkState.PLAYER1;}
        else if (playerID == 2) { check =checkState.PLAYER2;}

        notifySubscriber();


    }
    public void uncheck(){
        check = checkState.UNCHECKED;
    }
    public boolean isChecked() {
        return check != checkState.UNCHECKED;
    }

    public checkState getCheck() {
        return check;
    }

    public Map<Pos,NeighbourField> getNeighbours() {
        return neighbours;
    }
}
