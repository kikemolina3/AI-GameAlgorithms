import java.util.LinkedList;

public class AlphaBeta extends Algorithm{

//    int depth;
int depth;
    public void setDepth(int d){
        this.depth = d;
    }
    @Override
    public ValueBoard execute(Board b, int level, int alfa, int beta, Heuristic h, int turn) throws CloneNotSupportedException {
        int role = (turn == Square.BLACK) ? turn + level % 2 : turn - level % 2;
        LinkedList<Board> succesors = b.generateBoards(role);
        ValueBoard result = null;
        Board node_to_return = null;
        if (succesors.isEmpty()) {
            return new ValueBoard(b.calcWinner(turn), null);
        }
        if (level == depth)
            return new ValueBoard(h.calc(b, turn), null);
        else {
            node_to_return = null;
            while (!succesors.isEmpty() && alfa<beta) {//OK
                Board f = succesors.remove();
                result = this.execute(f, level + 1, alfa, beta, h, turn);
                if (level % 2 == 0) {
                    if (result.heuristic > alfa) {
                        alfa = result.heuristic;
                        node_to_return = f;
                    }
                } else {
                    if (result.heuristic < beta) {
                        beta = result.heuristic;
                        node_to_return = f;
                    }
                }
//                if (alfa >= beta) break;
            }
            if (level % 2 == 0)
                return new ValueBoard(alfa, node_to_return);
            else
                return new ValueBoard(beta, node_to_return);
        }

    }

}