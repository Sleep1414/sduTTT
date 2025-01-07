import Direction.Pos;

public class NeighbourGraph extends NeighbourFieldField {


    NeighbourGraph(Subscriber parent) {
        super(parent, null);
        // Assuming the parent is not used or handled elsewhere.

        // Create NeighbourField objects for each position
        NeighbourFieldField upperLeft = new NeighbourFieldField(this, Pos.LOWERLEFT);
        NeighbourFieldField upperMid = new NeighbourFieldField(this, Pos.LOWERMID);
        NeighbourFieldField upperRight = new NeighbourFieldField(this, Pos.LOWERRIGHT);
        NeighbourFieldField centerLeft = new NeighbourFieldField(this, Pos.CENTERLEFT);
        NeighbourFieldField centerMid = new NeighbourFieldField(this, Pos.CENTERMID);
        NeighbourFieldField centerRight = new NeighbourFieldField(this, Pos.CENTERRIGHT);
        NeighbourFieldField lowerLeft = new NeighbourFieldField(this, Pos.LOWERLEFT);
        NeighbourFieldField lowerMid = new NeighbourFieldField(this, Pos.LOWERMID);
        NeighbourFieldField lowerRight = new NeighbourFieldField(this, Pos.LOWERRIGHT);

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
        setNeighbours(upperLeft, null, null, null, null, upperMid, null, centerLeft, centerMid);
        setNeighbours(upperMid, null, null, null, upperLeft, upperRight, null, centerMid, null);
        setNeighbours(upperRight, null, null, null, upperMid, null, centerMid, centerRight, null);

        setNeighbours(centerLeft, null, upperLeft, null, null, centerMid, null, lowerLeft, null);
        setNeighbours(centerMid, upperLeft, upperMid, upperRight, centerLeft, centerRight, lowerLeft, lowerMid, lowerRight);
        setNeighbours(centerRight, null, upperRight, null, centerMid, null, null, lowerRight, null);

        setNeighbours(lowerLeft, null, centerLeft, centerMid, null, lowerMid, null, null, null);
        setNeighbours(lowerMid, null, centerMid, null, lowerLeft, lowerRight, null, null, null);
        setNeighbours(lowerRight, centerMid, centerRight, null, lowerMid, null, null, null, null);
    }

    @Override
    public void update(NeighbourField field) {
        this.check = evaluate(field);
        notifyfromlesserField(field);
    }

    void notifyfromlesserField(NeighbourField field) {
        for (Subscriber subscriber : subscribers) {
            subscriber.update(field);
        }
    }

}
