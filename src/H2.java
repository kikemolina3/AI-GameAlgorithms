public class H2 extends Heuristic {
    @Override
    public int calc(Board b, int turn) {
        int black_queen_sum = 0, white_queen_sum = 0;
        for (int i = 0; i < Board.WIDTH; i++) {
            if(b.sq[0][i].state == Square.WHITE)
                white_queen_sum ++;
            if(b.sq[Board.WIDTH-1][i].state == Square.BLACK)
                black_queen_sum ++;
        }
        if(turn == Square.BLACK)
            return black_queen_sum - white_queen_sum;
        if(turn == Square.WHITE)
            return white_queen_sum - black_queen_sum;
        return 0;
    }
}
