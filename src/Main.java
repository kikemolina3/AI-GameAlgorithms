import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {

        Board b = new Board();
        Algorithm a1 = null;
        Algorithm a2 = null;
        Heuristic h1 = null;
        Heuristic h2 = null;

        System.out.println("Select mode: [1-\"Human VS PC\", 2-\"PC1 VS PC2\"]");
        Scanner sc = new Scanner(System.in);
        int select_mode = sc.nextInt();
        if (select_mode == 1)
            System.out.println("You selected 'Human VS PC' mode. Specify: [Algorithm] [Heuristic] [Depth]");
        if (select_mode == 2)
            System.out.println("You selected 'BLACKS(PC1) VS WHITES(PC2)' mode. Specify: [Algorithm_BLACKS] [Heuristic_BLACKS] [Depth_BLACKS] [Algorithm_WHITES] [Heuristic_WHITES] [Depth_WHITES]");
        System.out.println("Tip:\n\tAlgorithm: [1-MiniMax, 2-AlphaBeta]\n\tHeuristic: [1-Tokens' number, 2-Queens' number, 3-Lateral strategy]\n\tLevels' depth: [num]");
        int a_black = sc.nextInt();
        int h_black = sc.nextInt();
        int levels_black = sc.nextInt();
        switch (a_black) {
            case 1:
                a1 = new MiniMax();
                break;
            case 2:
                a1 = new AlphaBeta();
                break;
        }
        switch (h_black) {
            case 1:
                h1 = new H1();
                break;
            case 2:
                h1 = new H2();
                break;
            case 3:
                h1 = new H3();
                break;
        }
        a1.setDepth(levels_black);
        if (select_mode == 2) {
            int a_white = sc.nextInt();
            int h_white = sc.nextInt();
            int levels_white = sc.nextInt();
            switch (a_white) {
                case 1:
                    a2 = new MiniMax();
                    break;
                case 2:
                    a2 = new AlphaBeta();
                    break;
            }
            switch (h_white) {
                case 1:
                    h2 = new H1();
                    break;
                case 2:
                    h2 = new H2();
                    break;
                case 3:
                    h2 = new H3();
                    break;
            }
            a2.setDepth(levels_white);
        }
        long acumulate_times1 = 0, acumulate_times2 = 0;
        int iterations1 = 0, iterations2 = 0;
        while (true) {
            if (!b.anyMove()) {
                System.out.println("End of the game");
                int result = b.calcWinner(Square.BLACK);
                if (result == Integer.MIN_VALUE / 2)
                    System.out.println("BLACK wins!");
                if (result == Integer.MAX_VALUE / 2)
                    System.out.println("WHITE win!");
                if (result == 0)
                    System.out.println("It's a draw!");
                System.out.println("Execution time average BLACKS: " + acumulate_times1 / iterations1 + " ms");
                if (select_mode == 2)
                    System.out.println("Execution time average WHITES: " + acumulate_times2 / iterations2 + " ms");
                return;
            }
            if (b.canMove(Square.BLACK)) {
                long start_time = System.currentTimeMillis();
                ValueBoard vb = a1.execute(b, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, h1, Square.BLACK);
                long lapsus = System.currentTimeMillis() - start_time;
                System.out.println("Execution time: " + lapsus + " ms");
                if(iterations1<5) {
                    acumulate_times1 += lapsus;
                    iterations1++;
                }
                b = vb.b;
            } else {
                System.out.println("BLACKS can't move.");
            }
            System.out.println(b);
            if (b.canMove(Square.WHITE)) {
                if (select_mode == 2) {
                    long start_time = System.currentTimeMillis();
                    ValueBoard vb = a2.execute(b, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, h2, Square.WHITE);
                    long lapsus = System.currentTimeMillis() - start_time;
                    System.out.println("Execution time: " + lapsus + " ms");
                    if(iterations1<5) {
                        acumulate_times2 += lapsus;
                        iterations2++;
                    }
                    b = vb.b;
                }
                if (select_mode == 1) {
                    Board user_movement = null;
                    do {
                        System.out.println("Your turn! Choose: [row] [column] [1-left, 2-right]");
                        int f = sc.nextInt() - 1;
                        int c = sc.nextInt() - 1;
                        int move = sc.nextInt();
                        switch (move) {
                            case 1:
                                user_movement = b.userMoveLeft(f, c);
                                break;
                            case 2:
                                user_movement = b.userMoveRight(f, c);
                                break;
                            default:
                        }
                    } while (user_movement == null);
                    b = user_movement;
                }
            } else {
                System.out.println("WHITES can't move.");
            }
            System.out.println(b);
        }
    }
}
