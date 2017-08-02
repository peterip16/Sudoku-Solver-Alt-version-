
import java.util.Arrays;

/**
 * @author Pak Ho
 * @Assumption The matrix input is one that can be solved one single way and has
 * a single answer. Obeys the normal Sudoku play rule 
 */

public class SudokuSolver {
    
    //Store the global variable that are likely require to be use through out the program
    private static int[][] resultSudoku; //The resulted sudoku
    private static boolean[][][] eachSpotPossibleNum; //Array to check whether a number is still possible in a certain spot
    
    public SudokuSolver(int[][] sudokuPuzzle){
        resultSudoku = sudokuPuzzle;
        
        eachSpotPossibleNum = new boolean[9][9][10];    // Make sure the array that store all possible values are initalized
        Arrays.fill(eachSpotPossibleNum, true);     // Fill the array that store all possible with true since all values at a blank sudoku paper are valid anywhere
    }
    
    /*
    The main method where the execution of the Java program begins.
    Initate the global variables result Sudoku with the given sudoku puzzle.
    Initiate the each SpotPossibleNum matrix with all true value since at the 
    beginning in a blank sudoku papers, anything is possible in any spot.
    @param args Currently doesn't take in values
    */
    public static void main(String args[]){
        
        eachSpotPossibleNum = new boolean[9][9][10];    // Make sure the array that store all possible values are initalized
        Arrays.fill(eachSpotPossibleNum, true);     // Fill the array that store all possible with true since all values at a blank sudoku paper are valid anywhere
        
        resultSudoku = new int[9][9];   // Make sure the array that store the result sudoku are initalized
        Arrays.fill(resultSudoku, 0);   // Fill the array that store result matrix with 0 since 
        
        inputSudokuPuzzle();
        
        if(checkValidSudokuPuzzle()){
            
        }
    }
    
    public static void inputSudokuPuzzle(){
        
    }
    
    public static boolean checkValidSudokuPuzzle(){
        
        for(int i = 0; i <=9; i++){         
            for(int n = 0; n <=9; n++){             
                
                if(resultSudoku[i][n] > 9 || resultSudoku[i][n] < 0){
                    
                    System.out.println("This is not a proper sudoku puzzle. Some values are not proper values in the sudoku");
                            
                }
            }
        }
        
        return true;
    }
    
}
