package com.SourceCode;

class Printer {
    private final int max = 3;
    private final char[][] location = {{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', '9'}};
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
        char[][] view = view_location == 1 ? location : symbol_to_print;
        for (int i = 0; i < max; i++) {
            System.out.print("\n   ");
            for (int j = 0; j < max; j++) {
                switch(view[i][j]) {
                    case ' ':
                        printWithAnsi(' ', false, false);
                        break;
                    case 'x':
                        printWithAnsi('x', false, false);
                        break;
                    case 'o':
                        printWithAnsi('o', false, false);
                        break;
                    default :
                        printWithAnsi(view[i][j], false, false);
                        break;
                }
                if(j<2)
                    System.out.print("|");
            }
            if(i<2)
                System.out.print("\n   - - - - - -");
        }
        System.out.println();
    }
    public void printFinalGrid(String line, int idx_i, int idx_j) {
        for(int i=0; i<max; i++) {
            System.out.print("\n   ");
            for(int j=0; j<max; j++) {
                switch(line) {
                    case "horizontal":
                        printWithAnsi(symbol_to_print[i][j], i == idx_i, true);
                        break;
                    case "vertical":
                        printWithAnsi(symbol_to_print[i][j], j == idx_j, true);
                        break;
                    case "diagonal_lr":
                        printWithAnsi(symbol_to_print[i][j], i == j, true);
                        break;
                    case "diagonal_rl":
                        printWithAnsi(symbol_to_print[i][j], i + j == 2, true);
                        break;
                }
                if(j<2)
                    System.out.print("|");
            }
            if(i<2)
                System.out.print("\n   - - - - - -");
        }
        System.out.println();
    }
    public void printFinalGrid(String line1, String line2, int idx_i, int idx_j) {
        for(int i=0; i<max; i++) {
            System.out.print("\n   ");
            for(int j=0; j<max; j++) {
                switch(line1) {
                    case "horizontal":
                        switch (line2) {
                            case "vertical":
                                printWithAnsi(symbol_to_print[i][j], i == idx_i || j == idx_j, true);
                                break;
                            case "diagonal_lr":
                                printWithAnsi(symbol_to_print[i][j], i == idx_i || i == j, true);
                                break;
                            case "diagonal_rl":
                                printWithAnsi(symbol_to_print[i][j], i == idx_i || i + j == 2, true);
                                break;
                        }
                        break;
                    case "vertical":
                        switch (line2) {
                            case "diagonal_lr":
                                printWithAnsi(symbol_to_print[i][j], j == idx_j || i == j, true);
                                break;
                            case "diagonal_rl":
                                printWithAnsi(symbol_to_print[i][j], j == idx_j || i + j == 2, true);
                                break;
                        }
                    case "diagonal_lr":
                        if (line2.equals("diagonal_rl"))
                            printWithAnsi(symbol_to_print[i][j], i == j || i + j == 2, true);
                }
                if(j<2)
                    System.out.print("|");
            }
            if(i<2)
                System.out.print("\n   - - - - - -");
        }
        System.out.println();
    }
}
