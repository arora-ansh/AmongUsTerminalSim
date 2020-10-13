import java.util.InputMismatchException;
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws InputMismatchException {
        int x = 1;
        Scanner in = new Scanner(System.in);
        do{
            new Game();
            System.out.println("\nDo you want to play again? (1-Y 2-N) ");
            x = in.nextInt();
        } while(x==1);
    }
}
