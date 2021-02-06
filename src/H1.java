public class H1 extends Heuristic{
    @Override
    public int calc(Board b, int turn) {
        if(turn == Square.BLACK)
            return b.n_blacks - b.n_whites;
        if(turn == Square.WHITE)
            return b.n_whites - b.n_blacks;
        return 0;
    }
}
