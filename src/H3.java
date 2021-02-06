public class H3 extends Heuristic{
    @Override
    public int calc(Board b, int turn) {
        int count = 0;
        for(int i=0; i<Board.WIDTH; i++){
            if(b.sq[i][0].state == turn)
                count++;
            if(b.sq[i][Board.WIDTH-1].state == turn)
                count++;
        }
        return count;
    }
}
