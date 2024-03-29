import java.util.Scanner;
import java.util.NoSuchElementException;

class TicTacToe {
    private int max = 3;
    private final char[][] symbol = new char[max][max];
    private int chance = 0;
    private boolean takeLocationFromPlayer(String player, char sign) {
        Printer print = new Printer();
        Scanner input = new Scanner(System.in);
        int location, idx_i, idx_j;
        while(true) {
            System.out.print("\n" + player + " (" + sign + ") : ");
            try {
                location = input.nextInt() - 1;
                if(location < 0 || location >= max*max)
                    throw new NoSuchElementException();
            } catch (Exception e) {
                System.out.println("Invalid location, try again...");
                input.nextLine();
                continue;
            }
            idx_i = (location/max) % max; idx_j = location % max;
            if(symbol[idx_i][idx_j] != '\u0000') {
                System.out.println("Already occupied...");
                continue;
            }
            break;
        }
        symbol[idx_i][idx_j] = sign;
        if(chance >= 5)
            return evaluate(player, idx_i, idx_j);
        else {
            print.symbol_to_print = symbol;
            print.printGrid(0);
            return false;
        }
    }
    private boolean evaluate(String player, int idx_i, int idx_j) {
        Printer print = new Printer();
        print.symbol_to_print = symbol;
        String[] won_by = {"horizontal", "vertical", "diagonal_lr", "diagonal_rl"};
        boolean[] match = new boolean[won_by.length];
        for(int i=0; i< won_by.length; i++) {
            match[i] = true;
            switch(i) {
                case 0: {
                    for(int j=0; j<max-1; j++)
                        match[i] = match[i] && (symbol[idx_i][j] == symbol[idx_i][j+1]);
                } break;
                case 1: {
                    for(int j=0; j<max-1; j++)
                        match[i] = match[i] && (symbol[j][idx_j] == symbol[j+1][idx_j]);
                } break;
                case 2: {
                    if(idx_i - idx_j != 0) {
                        match[i] = false;
                        break;
                    }
                    match[i] = match[i] && (symbol[1][1] != '\u0000');
                    for(int j=0; j<max-1; j++)
                        match[i] = match[i] && (symbol[j][j] == symbol[j+1][j+1]);
                } break;
                case 3: {
                    if(idx_i + idx_j != 2) {
                        match[i] = false;
                        break;
                    }
                    match[i] = match[i] && (symbol[1][1] != '\u0000');
                    for(int j=0; j<max-1; j++)
                        match[i] = match[i] && (symbol[j][(max-1)-j] == symbol[j+1][((max-1)-j)-1]);
                } break;
            }
        }
        for(int i=0; i<match.length-1; i++) {
            for(int j=i+1; j<match.length; j++) {
                if(match[i] && match[j]) {
                    print.printFinalGrid(won_by[i], won_by[j], idx_i, idx_j);
                    System.out.println("\n" + player + " won!\n");
                    return true;
                }
            }
        }
        for(int i=0; i<match.length; i++) {
            if (match[i]) {
                print.printFinalGrid(won_by[i], idx_i, idx_j);
                System.out.println("\n" + player + " won!\n");
                return true;
            }
        }
        print.printGrid(0);
        return false;
    }
    public void startGame() {
        if(chance == 0) {
            Printer print = new Printer();
            System.out.println("Grid location: ");
            print.printGrid(1);
            chance++;
            startGame();
        } else {
            boolean result;
            String[] player = {"Player - 1", "Player - 2"};
            char[] sign = {'x', 'o'};
            while(chance <= 9) {
                result = takeLocationFromPlayer(player[(chance-1) % 2], sign[(chance-1) % 2]);
                if(result) break;
                else if(chance == 9) System.out.println("\nIt's a draw!\n");
                chance++;
            }
        }
    }
}

class Printer {
    private final int max = 3;
    char[][] symbol_to_print = new char[max][max];
    private void printWithAnsi(char content, boolean in_line, boolean last_print) {
        String blue_bold_bright = "\u001B[1;96m";
        String red_bold_bright = "\u001B[1;91m";
        String black_bright = "\u001B[0;90m";
        String reset_ansi = "\u001B[0m";
        if(content == '\u0000') // default char value
            System.out.print("   ");
        else if((!in_line) && last_print)
            System.out.print(black_bright + " " + content + " " + reset_ansi);
        else if (content == 'x')
            System.out.print(blue_bold_bright + " " + content + " " + reset_ansi);
        else if(content == 'o')
            System.out.print(red_bold_bright + " " + content + " " + reset_ansi);
        else
            System.out.print(" " + content + " ");
    }
    public void printGrid(int view_location) {
        char[][] location = {{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', '9'}};
        char[][] view = view_location == 1 ? location : symbol_to_print;
        for (int i = 0; i < max; i++) {
            System.out.print("\n   ");
            for (int j = 0; j < max; j++) {
                printWithAnsi(view[i][j], false, false);
                if(j<2)
                    System.out.print("|");
            }
            if(i<2)
                System.out.print("\n   - - - - - -");
        }
        System.out.println();
    }
    public void printFinalGrid(String line, int idx_i, int idx_j) {
        String[] lines = {"horizontal", "vertical", "diagonal_lr", "diagonal_rl"};
        int[] diff_inlines = {1, 3, 4, 2};
        int difference = 0;
        for(int i=0; i<lines.length; i++) {
            if(lines[i].equals(line)) {
                difference = diff_inlines[i];
                break;
            }
        }
        int location = (idx_i * max) + idx_j, new_location;
        for(int i=0; i<max; i++) {
            System.out.print("\n   ");
            for(int j=0; j<max; j++) {
                new_location = (i * max) + j;
                boolean condition = ((new_location - location) % difference) == 0;
                // due to multiple occurrence of same difference,
                // we have to go for another fixed condition for horizontal and diagonal_rl lines
                if(difference == 1) // horizontal -> row is fixed
                    condition = condition && (i == idx_i);
                else if(difference == 2) // diagonal_rl -> row + column = 2
                    condition = condition && (i + j == 2);
                printWithAnsi(symbol_to_print[i][j], condition, true);
                if(condition)
                    location = new_location;
                if(j<2)
                    System.out.print("|");
            }
            if(i<2)
                System.out.print("\n   - - - - - -");
        }
        System.out.println();
    }
    public void printFinalGrid(String line1, String line2, int idx_i, int idx_j) {
        String[] lines = {"horizontal", "vertical", "diagonal_lr", "diagonal_rl"};
        int[] diff_inlines = {1, 3, 4, 2};
        int location = (idx_i * max) + idx_j;
        int difference1 = 0, difference2 = 0;
        for(int i=0; i<max; i++) {
            if(lines[i].equals(line1))
                difference1 = diff_inlines[i];
            else if(lines[i].equals(line2))
                difference2 = diff_inlines[i];
            if(difference1 != 0 && difference2 != 0)
                break;
        }
        for(int i=0; i<max; i++) {
            System.out.print("\n   ");
            for (int j = 0; j < max; j++) {
                boolean condition1 = (((i * max) + j) - location) % difference1 == 0;
                boolean condition2 = (((i * max) + j) - location) % difference2 == 0;
                printWithAnsi(symbol_to_print[i][j], condition1 || condition2, true);
                if(j<2)
                    System.out.print("|");
            }
            if(i<2)
                System.out.print("\n   - - - - - -");
        }
        System.out.println();
    }
}

public class Main {
    public static void main(String[] args) {
        TicTacToe play = new TicTacToe();
        play.startGame();
    }
}
