public class ValueBoard {
    int heuristic;
    Board b;

    public ValueBoard(int i, Board b){
        this.heuristic = i;
        this.b = b;
    }
}
