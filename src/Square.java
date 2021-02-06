public class Square implements Cloneable{
    int state;
    public static final int FREE = 0;
    public static final int BLACK = 1;
    public static final int WHITE = 2;

    public Square(int state){
        this.state = state;
    }

    @Override
    public String toString() {
        return Integer.toString(state);
    }
}
