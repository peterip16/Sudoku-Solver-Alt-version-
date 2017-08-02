/**
 * @author Pak Ho
 * @Assumption The matrix input is one that can be solved one single way and has
 * a single answer. Obeys the normal Sudoku play rule.
 * Use 0 for unsolved spot.
 */

public class SudokuSolver {
    
    //Store the global variable that are likely require to be use through out the program
    private int[][] resultSudoku; // The resulted sudoku
    private boolean[][][] eachSpotPossibleNum; // Array to check whether a number is still possible in a certain spot
    private boolean solved; // State whether the sudoku is solved
    
    /*
    Create a SudokuSolver object for use with main. Initialize all the global variables.
    */
    public SudokuSolver(){
        
        resultSudoku = new int[9][9];   //Make sure the array that store the result sudoku are initalized
        
        for(int i = 0; i < 9; i++){    //Initialize the resultSudoku with values for now
            
            for(int n = 0; n < 9; n++){
                
                resultSudoku[i][n] = 0;
            }
        }
        
        initEachSpotPossibleNum();  //Initialize the matrix to keep track of all possible number in a position
        solved = false; //State the current sudoku is not solved
    }
    
    /*
    Create a SudokuSolver object for use.
    @param sudokuPuzzle The sudoku that are meant to be solve.
    */
    public SudokuSolver(int[][] sudokuPuzzle){
       
        resultSudoku = sudokuPuzzle;    //Grab the sudoku that was inputed 
        initEachSpotPossibleNum();      //Initialize the matrix to keep track of all possible number in a position
        solved = false; //State the current sudoku is not solved
    }
    
    /*
    The main method where the execution of the Java program begins.
    Initate the global variables result Sudoku with the given sudoku puzzle.
    Initiate the each SpotPossibleNum matrix with all true value since at the 
    beginning in a blank sudoku papers, anything is possible in any spot.
    @param args Currently doesn't take in values
    */
    public static void main(String args[]){
        
        SudokuSolver sudoku = new SudokuSolver();   //Create a new sudokuSolver object
        
        sudoku.inputSudokuPuzzle();     //Ask user to input their sudoku puzzle
        
        //sudoku.isValidSudokuPuzzle(); Test methods
        //sudoku.checkRowSame(0,0); Test methods
        //sudoku.checkColumnSame(0, 0); Test methods
        //sudoku.checkSquareGridSame(0, 0); Test methods
        //sudoku.isValidResult(); Test methods
        
        if(!sudoku.isValidSudokuPuzzle()){  //Check if the sudoku is a valid sudoku puzzle  
            
        }
    }
    
    /*
    Initialize the eachSpotPossibleNum array allowing for future checking of possible number in each sudoku spot
    */
    public void initEachSpotPossibleNum(){
        
        eachSpotPossibleNum = new boolean[9][9][10];    //Make sure the array that store all possible values are initalized
        
        for(int i = 0; i < 9; i++){    //Triple nested loop to initialize the aechSpotPossibleNum array
            
            for(int n = 0; n < 9; n++){
                
                for(int t = 0; t < 10; t++){
                    
                    eachSpotPossibleNum[i][n][t] = true;    //Initialize that at this particular spot it could be this value
                }
            }
        }
    }
    
    public void inputSudokuPuzzle(){
        System.out.println("Please input the sudoku row by row. No need to separate the values with anything.");
        System.out.println("After you are done with a single row press Enter to input next row.");
        System.out.println("Please type in 0 for empty spot.");
    }
    
    /*
    Check if the sudokupuzzles that was given is a valid sudoku
    @return Whether the sudokupuzzle that was given is a valid sudoku puzzle
    */
    public boolean isValidSudokuPuzzle(){
        
        for(int i = 0; i < 9; i++){      //Double nested loop to go through all values and see if values are allowed in the sudoku
            
            for(int n = 0; n < 9; n++){             
                
                if(resultSudoku[i][n] > 9 || resultSudoku[i][n] < 0){       //Condition to see if the value at a specific position is valid
                    
                    System.out.println("This is not a proper sudoku puzzle. Some values are not proper values in the sudoku. You need to use 0's for placeholder.");
                    return false;
                }
            }
        }
        
        System.out.println("This is a sudoku puzzle with valid values or with 0's as placeholder.");
        return true;
    }
    
    /*
    Return if the sudoku puzzle is solved.
    @return Whether the sudoku puzzle is solved
    */
    public boolean isSolved(){
        
        if(!solved){    //If currently not solved
            
            solved = checkSolvedState();    //Check if the result sudoku is solved
        }
        
        return solved;
    }
    
    /*
    Return whether the state of sudoku is solved and the result is valid
    @return A boolean value indicating whether the sudoku is solved
    */
    private boolean checkSolvedState(){
 
        return isValidSudokuPuzzle() && isValidResult();    //Return if the result sudoku is a valid sudoku and the result is valid
    }
    
    /*
    Method to check whether the result matrix is a valid sudoku
    @return Whether the result sudoku is valid
    */
    public boolean isValidResult(){
        
        boolean result = true;
        
        for(int i = 0; i < 9 && result; i++){ //Double nested loop to go through all position in the sudoku
         
            for(int n = 0; n < 9 && result; n++){
                
                if(!(checkRowSame(i, n) && checkColumnSame(i, n) && checkSquareGridSame(i, n))){ //Check if the values at this position violated any of the rules
                    
                    System.out.println("Violated one of the sudoku rules.");
                    result = false;
                }
                
                if(resultSudoku[i][n] > 9 || resultSudoku[i][n] < 1){ //Check if the sudoku position has invalid values
                    
                    System.out.println("Value in row " + (i+1) + " column " + (n+1 + " has an invalid value in sudoku."));
                    result = false;
                }
                
            }
        }
        return result;
    }
    
    /*
    Check whether there are values in the same row that are the same against the specific position
    @param row The row which the current position is on
    @param column The column which the current position is on
    @return Whether there are values in the same row that are the same
    */
    public boolean checkRowSame(int row, int column){
        
        int val = resultSudoku[row][column]; //Grab the values in current position
        
        for(int i = 0; i < 9; i++){ //Traverse through the entire row
            
            if(resultSudoku[row][i] == val && i != column){ //Check if there's a value in the row that is current position
                
                System.out.println("Value in column " + (column+1) + " and in column " + (i+1) + " in row " + (row+1) + " is the same."); //Error message if there's a problem
                return true;
            }
        }
        
        return false;
    }
    
    /*
    Check whether there are values in the same column that are the same against the specific position
    @param row The row which the current position is on
    @param column The column which the current position is on
    @return Whether there are values in the same row that are the same
    */
    public boolean checkColumnSame(int row, int column){
        
        int val = resultSudoku[row][column]; //Grab the values in current position
        
        for(int i = 0; i < 9; i++){ //Traverse through the entire column
            
            if(resultSudoku[i][column] == val && i != row){ //Check if there's a value in the column that is current position
                
                System.out.println("Value in row " + (row+1) + " and in row " + (i+1) + " in column " + (column+1) + " is the same."); //Error message if there's a problem
                return true;
            }
        }
        
        return false;
    }
    
    /*
    Check whether there are values in the same 3x3 grid that are the same against the specific position
    @param row The row which the current position is on
    @param column The column which the current position is on
    @return Whether there are values in the same 3x3 grid that are the same
    */
    public boolean checkSquareGridSame(int row, int column){
        
        int val = resultSudoku[row][column]; //Grab the values in current position
        int gridRow = row/3; //Find which grid row it is on
        int gridColumn = column/3; //Find which grid column this is on
        
        for(int i = 3*gridRow; i < 3*gridRow + 3; i++){ //Traverse through this 3x3 grid's row
            
            for(int n = 3*gridColumn; n < 3*gridRow + 3; n++){ //Traverse through this 3x3 grid's column
                
                if(val == resultSudoku[row][column] && (i!= row || n != column)){ //Check if there are values that are the same in this grid
                    
                    System.out.println("Something in row " + (row+1) + " column " + (column+1) + " is the same as row " + (i+1) + " column " + (n+1) + "."); //Error message if there's a problem
                    return true;
                }
            }
        }
        
        return false;
    }
}
 