package com.SourceCode;

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
                // so we have to go for another fixed condition for horizontal and diagonal_rl lines
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
