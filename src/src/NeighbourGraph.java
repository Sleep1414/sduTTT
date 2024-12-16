import Direction.Pos;

import java.util.HashMap;
import java.util.Map;

public class NeighbourGraph extends NeighbourFieldField implements Component{

    protected Mediator mediator;

    NeighbourGraph() {
        super(null);
        // Assuming the parent is not used or handled elsewhere.

        // Create NeighbourField objects for each position
        NeighbourFieldField upperLeft = new NeighbourFieldField(this);
        NeighbourFieldField upperMid = new NeighbourFieldField(this);
        NeighbourFieldField upperRight = new NeighbourFieldField(this);
        NeighbourFieldField centerLeft = new NeighbourFieldField(this);
        NeighbourFieldField centerMid = new NeighbourFieldField(this);
        NeighbourFieldField centerRight = new NeighbourFieldField(this);
        NeighbourFieldField lowerLeft = new NeighbourFieldField(this);
        NeighbourFieldField lowerMid = new NeighbourFieldField(this);
        NeighbourFieldField lowerRight = new NeighbourFieldField(this);

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


    }


    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "NeighbourGraph";
    }
}
