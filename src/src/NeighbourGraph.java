import Direction.Pos;

import java.util.HashMap;
import java.util.Map;

public class NeighbourGraph extends NeighbourFieldField {


    NeighbourGraph() {
        super(null);
        // Assuming the parent is not used or handled elsewhere.

        // Initialize the grid (3x3 Tic-Tac-Toe)
        childField = new HashMap<>();

        // Create NeighbourField objects for each position
        NeighbourField upperLeft = new NeighbourFieldField(this);
        NeighbourField upperMid = new NeighbourFieldField(this);
        NeighbourField upperRight = new NeighbourFieldField(this);
        NeighbourField centerLeft = new NeighbourFieldField(this);
        NeighbourField centerMid = new NeighbourFieldField(this);
        NeighbourField centerRight = new NeighbourFieldField(this);
        NeighbourField lowerLeft = new NeighbourFieldField(this);
        NeighbourField lowerMid = new NeighbourFieldField(this);
        NeighbourField lowerRight = new NeighbourFieldField(this);

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



}
