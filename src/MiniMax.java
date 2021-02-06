import java.util.LinkedList;
public class MiniMax extends Algorithm{

//    int depth;
int depth;
    public void setDepth(int d){
        this.depth = d;
    }
    @Override
    public ValueBoard execute(Board b, int level, int alfa, int beta, Heuristic h, int turn) throws CloneNotSupportedException {
        int role = (turn == Square.BLACK)? turn+level%2 : turn - level%2;
        LinkedList<Board> succesors = b.generateBoards(role);
        ValueBoard result = null;
        Board node_to_return = null;
        int value;
        if (succesors.isEmpty()){
            return new ValueBoard(b.calcWinner(turn), null);
        }
        else{
            if (level == depth)
                return new ValueBoard(h.calc(b, turn), null);
            else {
                node_to_return = null;
                if (level % 2 == 0)
                    value = Integer.MIN_VALUE/2;
                else
                    value = Integer.MAX_VALUE/2;
                while (!succesors.isEmpty()) {
                    Board f = succesors.remove();
                    result = this.execute(f, level + 1, 0, 0,  h, turn);
                    if (level % 2 == 0) {
                        if (result.heuristic >= value) {
                            value = result.heuristic;
                            node_to_return = f;
                        }
                    } else {
                        if (result.heuristic <= value) {
                            value = result.heuristic;
                            node_to_return = f;
                        }
                    }
                }
                return new ValueBoard(value, node_to_return);
            }
        }
    }

}
