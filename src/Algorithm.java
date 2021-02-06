public abstract class Algorithm {
    public abstract ValueBoard execute(Board b, int level, int alfa, int beta, Heuristic h, int turn) throws CloneNotSupportedException;
    public abstract void setDepth(int d);
}
