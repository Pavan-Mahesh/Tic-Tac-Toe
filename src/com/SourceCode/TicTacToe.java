package com.SourceCode;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class TicTacToe {
    private final int max = 3;
    private final char[][] symbol = new char[max][max];
    private final String[] player = new String[2];
    private final char[] sign = new char[2];
    private int chance = 0;
    private void setPlayers() {
        Scanner input = new Scanner(System.in);
        System.out.println("Select a mode: ");
        System.out.println("1. Player-1 vs Player-2 \n2. Player vs Computer");
        int choice;
        while(true) {
            try {
                System.out.print("Choice: ");
                choice = input.nextInt();
                if (choice < 1 || choice > 2)
                    throw new NoSuchElementException();
                break;
            } catch (Exception e) {
                System.out.println("Invalid choice, try again...");
                input.nextLine();
            }
        }
        System.out.println();
        if(choice == 1) {
            player[0] = "Player-1"; player[1] = "Player-2";
            sign[0] = 'x'; sign[1] = 'o';
        } else {
            player[0] = "Player"; player[1] = "Computer";
            setSign();
        }
    }
    private void setSign() {
        Scanner input = new Scanner(System.in);
        System.out.println("Select your sign: ");
        System.out.println("1. X \n2. O");
        int choice;
        while(true) {
            try {
                System.out.print("Choice: ");
                choice = input.nextInt();
                if (choice < 1 || choice > 2)
                    throw new NoSuchElementException();
                break;
            } catch (Exception e) {
                System.out.println("Invalid choice, try again...");
                input.nextLine();
            }
        }
        System.out.println();
        if(choice == 1) {
            sign[0] = 'x'; sign[1] = 'o';
        } else {
            sign[0] = 'o'; sign[1] = 'x';
        }
    }
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
        if(chance >= max)
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
        match[0] = (symbol[idx_i][0] == symbol[idx_i][1]) && (symbol[idx_i][0] == symbol[idx_i][2]);
        match[1] = (symbol[0][idx_j] == symbol[1][idx_j]) && (symbol[0][idx_j] == symbol[2][idx_j]);
        match[2] = (symbol[1][1] != '\u0000') && (symbol[0][0] == symbol[1][1]) && (symbol[0][0] == symbol[2][2]);
        match[3] = (symbol[1][1] != '\u0000') && (symbol[0][2] == symbol[1][1]) && (symbol[0][2] == symbol[2][0]);
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
            setPlayers();
            Printer print = new Printer();
            System.out.println("Grid location: ");
            print.printGrid(1);
            chance++;
            startGame();
        } else {
            boolean result;
            while(chance <= 9) {
                result = takeLocationFromPlayer(player[(chance-1) % 2], sign[(chance-1) % 2]);
                if(result) break;
                else if(chance == 9) System.out.println("\nIt's a draw!\n");
                chance++;
            }
        }
    }
}