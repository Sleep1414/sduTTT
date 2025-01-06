

import Direction.Pos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public  class NeighbourField {
    protected Map<Pos, NeighbourField> neighbours;
    protected ArrayList <Subscriber> subscribers;



    enum checkState{ PLAYER1, PLAYER2, UNCHECKED}
    protected checkState check = checkState.UNCHECKED ;


    public NeighbourField getField(Pos direction){return null;}


    void notifySubscriber() {
        for (Subscriber subscriber : subscribers ) {
                subscriber.update(this);
        }
    }


    NeighbourField(NeighbourFieldField parent){
        subscribers = new ArrayList<>();
        subscribers.add(parent);
        neighbours = new HashMap<>();

    }

    void putNeighbour(Pos orientation, NeighbourField field) {
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
    public void addSubscriber(Subscriber sub){
        subscribers.add(sub);
    }
}
