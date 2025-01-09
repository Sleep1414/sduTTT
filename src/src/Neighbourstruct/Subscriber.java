package Neighbourstruct;

public interface Subscriber {
    void update(NeighbourField field);
    void updateplayerturn();
    boolean getplayerturn();
}
