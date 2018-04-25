//////////////////////////////////////////////////////////
// Sudoku Solver Program
// Name: SudokuSolver.java
// Authors: Rafael Antonio Martinez Salguero, Brian Mickel
// Date 04/14/2018
// Purpose: Sudoku Solver
// Time to finish: 13 hours
//////////////////////////////////////////////////////////

import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;




public class SudokuSolver {
    //this should be the board with hint placed in it
    public static int[][] initialBoard;

    public static int[][] solvedBoard;

    //Main function that solves sudoku
    public static boolean numberPlacerBacktracker(int[][] board){
        for(int y = 0; y < 9; y++) { //for each column
            for (int x = 0; x < 9; x++) { //for each row
                if (board[y][x] == 0) { //make sure its not a hint, every value that is not a zero should be a hint
                    for (int val = 1; val <= 9; val++){
                        if (checkValid(board, x, y, val)){
                            board[y][x] = val;
                            if (numberPlacerBacktracker(board)){
                                solvedBoard = board;
                                return true;
                            } else {
                                board[y][x] = 0;
                            }
                        }
                    }
                    solvedBoard = board;
                    return false;
                }
            }
        }
        solvedBoard = board;
        return true;
    }


    //checks for violations in x axis
    public static boolean checkRow(int[][] board, int x, int y, int value){
        for (int row = 0; row < 9; row++) {
            if (board[y][row] == value) {
                return false;
            }
        }
        return true;
    }

    //checks for violations in y axis
    public static boolean checkColumn(int[][] board, int x, int y, int value){
        for (int col = 0; col < 9; col++) {
            if (board[col][x] == value) {
                return false;
            }
        }
        return true;
    }

    //checks for violations in nonets
    public static boolean checkNonet(int[][] board, int x, int y, int value){
        int nonetColCenterCell = 3 * (y/3) + 1;
        int nonetRowCenterCell = 3 * (x/3) + 1;
        int[] checkColRange = {nonetColCenterCell - 1, nonetColCenterCell, nonetColCenterCell + 1};
        int[] checkRowRange = {nonetRowCenterCell - 1, nonetRowCenterCell, nonetRowCenterCell + 1};

        for (int col : checkColRange){
            for (int row : checkRowRange){
                if (board[col][row] == value){
                    return false;
                }
            }
        }
        return true;
        
    }

    //places numbers on board that cannot be changed, these are the hints
    public static int[][] hintPlacer(int[][] board){
        Random generator = new Random();
        //difficulty meter, the less iterations the harder it gets.
        //make sure there's at least 17 hints if not puzzle is unsolvable
        int genX;
        int genY;
        int value;
        for(int i = 0; i < 38; i++){
            genX = generator.nextInt(9);
            genY = generator.nextInt(9);
            value = generator.nextInt(9) + 1;
            if (checkValid(board, genX, genY, value)){
                board[genY][genX] = value;
            } else {
                board[genY][genX] = 0;
            }
        }
        return board;
    }


    //checks for all violations of row, column, nonet, if none are found then place integer in cell
    //input board, x and y values to give you specific cells, and value to input in case no violations are found
    //returns true if all are valid (row & col & nonet)
    public static boolean checkValid(int[][] board, int x, int y, int value){
        return (checkColumn(board, x, y, value) && checkRow(board, x, y, value) && checkNonet(board, x, y, value)); // returns true if all are valid

    }


    //prints board
    public static void boardPrinter(int[][] board){
        for(int y = 0; y < 9; y++) {
            if(y % 3 == 0 && y != 0) {
                System.out.println(" -------------------------------");
            }
            for(int x = 0; x < 9; x++) {
                if(x % 3 == 0 && x != 0) {
                    System.out.print(" | ");
                }
                System.out.print(" " + board[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println("\n________________________________\n");

    }

    //checks if puzzle has a unique solution
    public static boolean noSolutionChecker(int[][] board){
        boolean check = false;
        for (int y = 0; y < 9; y++){
            for (int x = 0; x < 9; x++){
                if (board[y][x] == 0){
                    check = true;
                    break;
                }
            }
        }
        if (check){
            return true;
        }
        return false;
    }

    //if no solution is found print unsolved instead of solved
    public static void solvedChecker(boolean checker){
        if (checker){
            System.out.println("       NO UNIQUE SOLUTION");
            System.out.println("       ------------------");
        } else {
            System.out.println("             Solved");
            System.out.println("             ------");
        }
    }


    public static void main(String[] args) {
        int[][] board = new int[9][9]; //creates board

        boolean checker = false;  //checks if puzzle has unique solution or not

        board = hintPlacer(board); //places hints on board
        System.out.println();
        System.out.println("             Pending");
        System.out.println("             -------");
        boardPrinter(board);//prints board with hints

        numberPlacerBacktracker(board); //solves Sudoku puzzle
        checker = noSolutionChecker(solvedBoard);

        solvedChecker(checker); //lets user know if Sudoku has a unique solution or not
        boardPrinter(solvedBoard); //prints solved Sudoku

    }
}
