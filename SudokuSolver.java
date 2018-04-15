//////////////////////////////////////////////////////////
// Sudoku Solver Program
// Name: SudokuSolver.java
// Author: Rafael Antonio Martinez Salguero
// Date 04/14/2018
// Purpose: Sudoku Solver
//////////////////////////////////////////////////////////

//https://en.wikipedia.org/wiki/Sudoku_solving_algorithms <-- Use this for future generations of this project
import java.util.Collections;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class SudokuSolver {

    public static int[][] initialBoard;
    public static int[][] solvedBoard;

    //if numberPlacer finds a violation backtracker will increase previous changeable number
    // by one.
    public static void backtracker(){

    }


    //Main function that solves sudoku
    public static boolean numberPlacerBacktracker(int[][] board){
        initialBoard = board;
        //Random generator = new Random();
        for(int y = 0; y < 9; y++) { //for each column
            for (int x = 0; x < 9; x++) { //for each row
                if ( board[y][x] == 0) { //make sure its not a hint
                    for (int val=1; val<=9; val++){
                        if (checkValid(board, y, x, val)){
                            board[y][x] = val;
                            if (numberPlacerBacktracker(board)){
                                solvedBoard = board;
                                return true;
                            } else {
                                board[y][x] = 0;
                            }
                        }
                    }
                    return false;

                }
            }
        }
        solvedBoard = board;
        return true;

    }

    //checks for violations in x axis
    public static boolean checkColumn(int[][] board, int x, int y, int value){
        for (int row = 0; row < 9; row++) {
            if (board[y][row] == value) {
                return false;
            }
        }
        return true;
    }

    

    //checks for violations in y axis
    public static boolean checkRow(int[][] board, int x, int y, int value){
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
    public static void hintPlacer(int[][] board){
        Random generator = new Random();
        //difficulty meter, the less iterations the harder it gets.
        //make sure there's at least 17 hints if not puzzle is unsolvable
        for(int i = 0; i < 35; i++){
            board[generator.nextInt(9)][generator.nextInt(9)] = generator.nextInt(9) + 1;
        }
    }

    //makes sure hints are never repeated in column, row, and nonet
    public static void hintVerifier(int[][] board){
        int rowSum = IntStream.of(board[0]).sum();
        int curr = 0;
        int prev = 0;
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {

                if(board[x][y] != 0){
                    prev = board[x][y];
                }
            }
        }




    }

    //checks for all violations of row, column, nonet, if none are found then place integer in cell
    //input board, x and y values to give you specific cells, and value to input in case no violations are found
    //returns true if all are valid (row & col & nonet)
    public static boolean checkValid(int[][] board, int x, int y, int value){
        return (checkColumn(board, x, y, value) & checkRow(board, x, y, value) & checkNonet(board, x, y, value)); // returns true if all are valid

    }


    //prints board
    public static void boardPrinter(int[][] board){
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                System.out.print("|" + "_" + board[y][x] + "_" + "|");
            }
            System.out.println();
        }

    }


    public static void main(String[] args) {
        //creates board
        int[][] board = new int[9][9];

        hintPlacer(board);
        hintVerifier(board);
        boardPrinter(board);


        int[][] mockBoard={{9,0,3,1,7,4,2,5,8},
                            {1,7,8,3,2,5,6,4,9},
                            {2,5,4,6,8,9,7,3,1},
                            {8,2,1,4,3,7,5,9,6},
                            {4,9,6,8,5,2,3,1,7},
                            {7,3,5,9,6,1,8,0,4},
                            {5,0,9,7,1,3,4,6,2},
                            {3,1,7,2,4,6,9,8,5},
                            {6,4,2,5,9,8,1,7,3}};
//        {{9,6,3,1,7,4,2,5,8},
//        {1,7,8,3,2,5,6,4,9},
//        {2,5,4,6,8,9,7,3,1},
//        {8,2,1,4,3,7,5,9,6},
//        {4,9,6,8,5,2,3,1,7},
//        {7,3,5,9,6,1,8,2,4},
//        {5,8,9,7,1,3,4,6,2},
//        {3,1,7,2,4,6,9,8,5},
//        {6,4,2,5,9,8,1,7,3}};
        System.out.println("Hold up");
        boardPrinter(mockBoard);
        numberPlacerBacktracker(mockBoard);
        boardPrinter(initialBoard);
        System.out.println("Solved");
        boardPrinter(solvedBoard);
    }
}
