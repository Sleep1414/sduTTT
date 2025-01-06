

import Direction.Pos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NeighbourField {
    protected Map<Pos, NeighbourField> neighbours;
    protected ArrayList<Subscriber> subscribers;
    protected Pos fieldFieldPosition;
    Pos graphPostion;


    enum checkState {PLAYER1, PLAYER2, UNCHECKED}

    protected checkState check = checkState.UNCHECKED;

    NeighbourField(Subscriber parent, Pos fieldFieldPosition, Pos graphPostion) {
        subscribers = new ArrayList<>();
        subscribers.add(parent);
        neighbours = new HashMap<>();
        this.graphPostion = graphPostion;
        this.fieldFieldPosition = fieldFieldPosition;

    }

    NeighbourField(Subscriber parent, Pos graphPostion) {
        subscribers = new ArrayList<>();
        subscribers.add(parent);
        this.graphPostion = graphPostion;
        neighbours = new HashMap<>();

    }

    public Pos getGraphPostion() {
        return graphPostion;
    }

    public NeighbourField getField(Pos direction) {
        return null;
    }

    void notifySubscriber() {
        for (Subscriber subscriber : subscribers) {
            subscriber.update(this);
        }
    }


    void putNeighbour(Pos orientation, NeighbourField field) {
        neighbours.put(orientation, field);
    }

    public void check(int playerID) {
        if (playerID == 1) {
            check = checkState.PLAYER1;
        } else if (playerID == 2) {
            check = checkState.PLAYER2;
        }

        notifySubscriber();


    }

    public void uncheck() {
        check = checkState.UNCHECKED;
    }

    public boolean isChecked() {
        return check != checkState.UNCHECKED;
    }

    public checkState getCheck() {
        return check;
    }

    public Map<Pos, NeighbourField> getNeighbours() {
        return neighbours;
    }

    public void addSubscriber(Subscriber sub) {
        subscribers.add(sub);
    }
}
