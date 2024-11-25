import java.util.HashMap;

public  class NeighbourField {
    private HashMap<Pos, NeighbourField> Neighbours;



    enum checkState{ PLAYER1, PLAYER2, UNCHECKED}
    private checkState check;


    NeighbourField(){
        this.check = checkState.UNCHECKED;
    }

    public void addNeighbour(Pos orientation, NeighbourField field) {
        Neighbours.put(orientation, field);
    }

    public void check(int playerID){
        if (playerID == 1){check = checkState.PLAYER1;}
        else if (playerID == 2) { check =checkState.PLAYER2;}


    }
    public void uncheck(){
        check = checkState.UNCHECKED;
    }
    public boolean isChecked() {
        return check != checkState.UNCHECKED;
    }
    public Integer checkedby(){
            if(check == checkState.PLAYER1){return 1;}
            else if (check == checkState.PLAYER2) { return 2;}
        return null;
    }
}
