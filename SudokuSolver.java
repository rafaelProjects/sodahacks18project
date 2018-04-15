//////////////////////////////////////////////////////////
// Sudoku Solver Program
// Name: SudokuSolver.java
// Authors: Rafael Antonio Martinez Salguero, Brian Mickel
// Date 04/14/2018
// Purpose: Sudoku Solver
// Time to finish: 13 hours 56 mins.
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

    //===================================================================
    //Brian "Up"
    //Rafael "Down"
    //===================================================================
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




    public static void main(String[] args) {
        //creates board
        int[][] board = new int[9][9];

        board = hintPlacer(board); //places hints on board
        System.out.println();
        System.out.println("             Pending");
        System.out.println("             -------");
        boardPrinter(board);//prints board with hints

        // ------ UI -------- //
        int[][] boardUI = board.clone();//board that user interacts with
        int[][] boardCopy = board;
        Timer t = new Timer(); //Timer starts for users to be challenged
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                //Once user runs out of time to answer return answer of solved sudoku board
                numberPlacerBacktracker(boardCopy);
                System.out.println("             Solved");
                System.out.println("             ------");
                boardPrinter(solvedBoard);
                t.cancel();
                t.purge();
                //System.exit(0);
            }
        }, 60000*3 /*<--3 mins*/);
        Scanner s = new Scanner(System.in);
        for (int i = 0; i < 81; i++) {
            System.out.println("Enter guess for cell");
            System.out.println("pls enter x coordinate:");
            int x = s.nextInt();
            System.out.println("pls enter y coordinate:");
            int y = s.nextInt();
            System.out.println("pls enter cell value that you want");
            int value = s.nextInt();
            boardUI[y][x] = value;
            boardPrinter(boardUI);
        }

        /*//this prints the answered sudoku puzzle
        numberPlacerBacktracker(board); //solves sudoku puzzle
        System.out.println("             Solved");
        System.out.println("             ------");
        boardPrinter(solvedBoard); //prints solved sudoku*/

    }
}
