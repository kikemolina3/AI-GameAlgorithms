import org.omg.PortableInterceptor.INACTIVE;

import java.io.Serializable;
import java.util.*;

public class Board implements Cloneable {
    public static final int WIDTH = 8;

    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RESET = "\u001B[0m";

    Square[][] sq;
    int n_blacks;
    int n_whites;
    boolean finished;

    // Constructor by default -> set board at begin state
    public Board() {
        Square[][] sq = new Square[WIDTH][WIDTH];

        for (int i = 0; i < WIDTH; i++)
            for (int j = 0; j < WIDTH; j++)
                sq[i][j] = new Square(Square.FREE);

        sq[0][1] = new Square(Square.BLACK);
        sq[0][3] = new Square(Square.BLACK);
        sq[0][5] = new Square(Square.BLACK);
        sq[0][7] = new Square(Square.BLACK);
        sq[1][0] = new Square(Square.BLACK);
        sq[1][2] = new Square(Square.BLACK);
        sq[1][4] = new Square(Square.BLACK);
        sq[1][6] = new Square(Square.BLACK);
        sq[2][1] = new Square(Square.BLACK);
        sq[2][3] = new Square(Square.BLACK);
        sq[2][5] = new Square(Square.BLACK);
        sq[2][7] = new Square(Square.BLACK);

//        sq[3][0] = new Square(Square.FREE);
//        sq[3][2] = new Square(Square.WHITE);
//        sq[3][4] = new Square(Square.WHITE);
//        sq[3][6] = new Square(Square.WHITE);

        sq[5][0] = new Square(Square.WHITE);
        sq[5][2] = new Square(Square.WHITE);
        sq[5][4] = new Square(Square.WHITE);
        sq[5][6] = new Square(Square.WHITE);
        sq[6][1] = new Square(Square.WHITE);
        sq[6][3] = new Square(Square.WHITE);
        sq[6][5] = new Square(Square.WHITE);
        sq[6][7] = new Square(Square.WHITE);
        sq[7][0] = new Square(Square.WHITE);
        sq[7][2] = new Square(Square.WHITE);
        sq[7][4] = new Square(Square.WHITE);
        sq[7][6] = new Square(Square.WHITE);

        this.n_blacks = 12;
        this.n_whites = 12;
        this.sq = sq;
    }

    // Replication of board
    public Board(Board b){
        Square[][] sq = new Square[WIDTH][WIDTH];
        for (int i = 0; i < WIDTH; i++)
            for (int j = 0; j < WIDTH; j++)
                sq[i][j] = new Square(b.sq[i][j].state);
        this.sq = sq;
        this.n_whites = b.n_whites;
        this.n_blacks = b.n_blacks;
    }

    // Deep cloning
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Board(this);
    }

    // Return a list with possible movements
    public LinkedList<Board> generateBoards(int turn) throws CloneNotSupportedException {
        LinkedList<Board> moves = new LinkedList<Board>();
        int way = 0;
        if (turn == Square.BLACK) way = 1;
        if (turn == Square.WHITE) way = -1;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (this.sq[i][j].state == turn) {
                    Board successor;
                    successor = moveRight(this, way, i, j);
                    if(successor != null) moves.add(successor);
                    successor = moveLeft(this, way, i, j);
                    if(successor != null) moves.add(successor);
                    successor = eatRight(this, way, i, j);
                    if(successor != null) moves.add(successor);
                    successor = eatLeft(this, way, i, j);
                    if(successor != null) moves.add(successor);
                }
            }
        }
        return moves;
    }

    // Posible operators
    public Board moveRight(Board b, int way, int f, int c) throws CloneNotSupportedException {
        try{
            if (b.sq[f+way][c+1].state==Square.FREE){
                Board cloned = (Board)b.clone();
                int aux;
                aux = b.sq[f][c].state;
                cloned.sq[f][c].state = Square.FREE;
                cloned.sq[f+way][c+1].state = aux;
                return cloned;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            return null;
        }
        return null;
    }
    public Board moveLeft(Board b, int way, int f, int c) throws CloneNotSupportedException {
        try{
            if (b.sq[f+way][c-1].state==Square.FREE){
                Board cloned = (Board)b.clone();
                int aux;
                aux = b.sq[f][c].state;
                cloned.sq[f][c].state = Square.FREE;
                cloned.sq[f+way][c-1].state = aux;
                return cloned;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            return null;
        }
        return null;
    }
    public Board eatRight(Board b, int way, int f, int c) throws CloneNotSupportedException{
        int enemy = 0;
        if (way == 1) enemy = Square.WHITE;
        if (way == -1) enemy = Square.BLACK;
        try{
            if(b.sq[f+way*2][c+2].state == Square.FREE && b.sq[f+way][c+1].state == enemy){
                Board cloned = (Board)b.clone();
                cloned.sq[f+way*2][c+2].state = b.sq[f][c].state;
                cloned.sq[f][c].state = Square.FREE;
                cloned.sq[f+way][c+1].state = Square.FREE;
                if (enemy == Square.BLACK) cloned.n_blacks--;
                if (enemy == Square.WHITE) cloned.n_whites--;
                return cloned;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            return null;
        }
        return null;
    }
    public Board eatLeft(Board b, int way, int f, int c) throws CloneNotSupportedException{
        int enemy = 0;
        if (way == 1) enemy = Square.WHITE;
        if (way == -1) enemy = Square.BLACK;
        try{
            if(b.sq[f+way*2][c-2].state == Square.FREE && b.sq[f+way][c-1].state == enemy){
                Board cloned = (Board)b.clone();
                cloned.sq[f+way*2][c-2].state = b.sq[f][c].state;
                cloned.sq[f][c].state = Square.FREE;
                cloned.sq[f+way][c-1].state = Square.FREE;
                if (enemy == Square.BLACK) cloned.n_blacks--;
                if (enemy == Square.WHITE) cloned.n_whites--;
                return cloned;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            return null;
        }
        return null;
    }

    public int calcWinner(int turn){
        int black_row = WIDTH - 1;
        int white_row = 0;
        int black_queens;
        int white_queens;
        for(int k = 0; k < WIDTH; k++){
            black_queens = 0;
            white_queens = 0;
            for (int i = 0; i < WIDTH; i++) {
                if (this.sq[black_row][i].state == Square.BLACK)
                    black_queens++;
                if (this.sq[white_row][i].state == Square.WHITE)
                    white_queens++;
            }
            if (black_queens > white_queens)
                return (turn == Square.BLACK)? Integer.MIN_VALUE/2 : Integer.MAX_VALUE/2;
            else if (black_queens < white_queens)
                return (turn == Square.BLACK)? Integer.MAX_VALUE/2 : Integer.MIN_VALUE/2;
            else {
                black_row--;
                white_row++;
            }
        }
        return 0;
    }

    public Board userMoveRight(int f, int c){
        try {
            if(this.sq[f][c].state != Square.WHITE)
                return null;
            if (this.sq[f - 1][c + 1].state == Square.WHITE) {
                return null;
            } else {
                if(this.sq[f - 1][c + 1].state == Square.FREE){
                    this.sq[f][c].state = Square.FREE;
                    this.sq[f - 1][c + 1].state = Square.WHITE;
                    return this;
                }
                else if(this.sq[f - 1][c + 1].state == Square.BLACK && this.sq[f - 2][c + 2].state == Square.FREE){
                    this.sq[f][c].state = Square.FREE;
                    this.sq[f - 1][c + 1].state = Square.FREE;
                    this.sq[f - 2][c + 2].state = Square.WHITE;
                    this.n_blacks--;
                    return this;
                }
                else
                    return null;
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
    }
    public Board userMoveLeft(int f, int c){
        try {
            if(this.sq[f][c].state != Square.WHITE)
                return null;
            if (this.sq[f - 1][c - 1].state == Square.WHITE) {
                return null;
            } else {
                if(this.sq[f - 1][c - 1].state == Square.FREE){
                    this.sq[f][c].state = Square.FREE;
                    this.sq[f - 1][c - 1].state = Square.WHITE;
                    return this;
                }
                else if(this.sq[f - 1][c - 1].state == Square.BLACK && this.sq[f - 2][c - 2].state == Square.FREE){
                    this.sq[f][c].state = Square.FREE;
                    this.sq[f - 1][c - 1].state = Square.FREE;
                    this.sq[f - 2][c - 2].state = Square.WHITE;
                    this.n_blacks--;
                    return this;
                }
                else
                    return null;
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
    }

    public boolean canMove(int turn) throws CloneNotSupportedException {
        LinkedList<Board> moves = this.generateBoards(turn);
        if(moves.isEmpty())
            return false;
        else
            return true;
    }

    public boolean anyMove() throws CloneNotSupportedException {
        LinkedList<Board> moves = this.generateBoards(Square.BLACK);
        moves.addAll(this.generateBoards(Square.WHITE));
        if(moves.isEmpty())
            return false;
        return true;
    }

//    public

    @Override
    public String toString() {
        char column_info = '1';
        String r = "  1 2 3 4 5 6 7 8\n";
        for (int i = 0; i < WIDTH; i++) {
            r += column_info + " ";
            for (int j = 0; j < WIDTH; j++) {
                r += ANSI_BLACK;
                switch(this.sq[i][j].state){
                    case 0:
                        r += (((i+j) % 2 == 0)? ANSI_BLACK_BACKGROUND : ANSI_WHITE_BACKGROUND) + " ";
                        break;
                    case 1:
                        r += ANSI_RED_BACKGROUND + "X";
                        break;
                    case 2:
                        r += ANSI_GREEN_BACKGROUND + "O";
                        break;
                }
                r += " " + ANSI_RESET;
            }
            r += "\n";
            column_info++;
        }
        return r + "Blacks: " + n_blacks + "\tWhites: " + n_whites;
    }
}
