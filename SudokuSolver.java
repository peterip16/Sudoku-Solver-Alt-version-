
import java.util.Arrays;

/**
 * @author Pak Ho
 * @Assumption The matrix input is one that can be solved one single way and has
 * a single answer. Obeys the normal Sudoku play rule 
 */

public class SudokuSolver {
    
    //Store the global variable that are likely require to be use through out the program
    private int[][] resultSudoku; //The resulted sudoku
    private boolean[][][] eachSpotPossibleNum; //Array to check whether a number is still possible in a certain spot
    
    public SudokuSolver(){
        
        resultSudoku = new int[9][9];   // Make sure the array that store the result sudoku are initalized
        
        for(int i = 0; i <= 9; i++){
            for(int n = 0; n <= 9; n++){
                resultSudoku[i][n] = 0;
            }
        }
        
        initEachSpotPossibleNum();  //
    }
    
    public SudokuSolver(int[][] sudokuPuzzle){
        
        resultSudoku = sudokuPuzzle;
        initEachSpotPossibleNum();
        
    }
    
    /*
    The main method where the execution of the Java program begins.
    Initate the global variables result Sudoku with the given sudoku puzzle.
    Initiate the each SpotPossibleNum matrix with all true value since at the 
    beginning in a blank sudoku papers, anything is possible in any spot.
    @param args Currently doesn't take in values
    */
    public static void main(String args[]){
        
    }
    
    public void initEachSpotPossibleNum(){
        
        eachSpotPossibleNum = new boolean[9][9][10];    // Make sure the array that store all possible values are initalized
        
        for(int i = 0; i <= 9; i++){    // Triple nested loop to initialize the aechSpotPossibleNum array
            for(int n = 0; n <= 9; n++){
                for(int t = 0; t <= 10; t++){
                    eachSpotPossibleNum[i][n][t] = true;
                }
            }
        }
    }
    
    public static void inputSudokuPuzzle(){
        
    }
    
    public static boolean checkValidSudokuPuzzle(){
        
        for(int i = 0; i <=9; i++){         
            for(int n = 0; n <=9; n++){             
                
                if(resultSudoku[i][n] > 9 || resultSudoku[i][n] < 0){
                    
                    System.out.println("This is not a proper sudoku puzzle. Some values are not proper values in the sudoku");
                    
                    return false;
                            
                }
            }
        }
        
        return true;
    }
    
}
