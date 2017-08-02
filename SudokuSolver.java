
import java.util.Scanner;

/**
 * @author Pak Ho
 * @Assumption The matrix input is one that can be solved one single way and has
 * a single answer. Obeys the normal Sudoku play rule.
 * Use 0 for unsolved spot.
 */

public class SudokuSolver {
    
    //Store the global variable that are likely require to be use through out the program
    private int[][] resultSudoku; //The resulted sudoku
    private boolean[][][] eachSpotPossibleNum; //Array to check whether a number is still possible in a certain spot
    private boolean solved; //State whether the sudoku is solved
    
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
        /*
        int[][] testSudoku1 = {
            {7,9,0,0,0,0,3,0,0},
            {0,0,0,0,0,6,9,0,0},
            {8,0,0,0,3,0,0,7,6},
            {0,0,0,0,0,5,0,0,2},
            {0,0,5,4,1,8,7,0,0},
            {4,0,0,7,0,0,0,0,0},
            {6,1,0,0,9,0,0,0,8},
            {0,0,2,3,0,0,0,0,0},
            {0,0,9,0,0,0,0,5,4}
        };
        
        SudokuSolver sudoku = new SudokuSolver(testSudoku1);
        
                Test sudoku puzzle
        */
        
        //sudoku.isValidSudokuPuzzle(); Test methods
        //sudoku.checkRowSame(0,0); Test methods
        //sudoku.checkColumnSame(0, 0); Test methods
        //sudoku.checkSquareGridSame(0, 0); Test methods
        //sudoku.isValidResult(); Test methods
        
        if(sudoku.isValidValueSudokuPuzzle()){  //Check if the sudoku is a valid sudoku puzzle  
            
            sudoku.printSudoku();
            int unsolvedSpotCount = sudoku.solvePuzzle();
            sudoku.printSudoku();
            
            System.out.println("There are " + unsolvedSpotCount + " spot not solved.");
        }
    }
    
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
    Initialize the eachSpotPossibleNum array allowing for future checking of possible number in each sudoku spot
    */
    public void initEachSpotPossibleNum(){
        
        eachSpotPossibleNum = new boolean[9][9][9];    //Make sure the array that store all possible values are initalized
        
        for(int i = 0; i < 9; i++){    //Triple nested loop to initialize the aechSpotPossibleNum array
            
            for(int n = 0; n < 9; n++){
                
                for(int t = 0; t < 9; t++){
                    
                    eachSpotPossibleNum[i][n][t] = true;    //Initialize that at this particular spot it could be this value
                }
            }
        }
    }
    
    /*
    Ask the user to input the sudoku puzzle they want to be solved
    */
    public void inputSudokuPuzzle(){
        
        for(int i = 0; i < 9; i++){ //Double nested loop so users must go through all 81 position
            
            for(int n = 0; n < 9; n++){
                
                boolean correctInput = false; //Set a variable to state whether the user has inputed a correct input for the current position
                System.out.println("Please enter the number in row " + (i+1) + " column " + (n+1) + " in the sudoku. Enter 0 if there's no value at that position yet.");
                
                while(!correctInput){ //Do this if there are no correctInput yet
                    
                    Scanner in = new Scanner(System.in); //Create and store the input of the user
                    String input = in.nextLine();
                    correctInput = true;
                    
                    if(!(input.length() == 1)){ //Check whether the input is only a single character and if not, the input is not any values that will fit on a normal sudoku
                        
                        correctInput = false;
                        System.out.println("Your input is not a single character.");
                    } else {
                        
                        for(int t = 0; t < input.length(); t++){ //Loop to check whether there's a non-character in the loop
                            
                            if(!Character.isDigit(input.charAt(t))){ //Check to see if the character at a certain position is a digit
                                
                                correctInput = false;
                                System.out.println("The value you inputted is not a number.");
                            }
                    }
                    }
                    
                    if(correctInput){ //If the input by the user is correct, parse and input into the current sudoku
                        
                        int val = Integer.parseInt(input);
                        resultSudoku[i][n] = val;
                    }
                }
            }
        }
    }
    
    /*
    Check if the sudokupuzzles that was given is a valid sudoku
    @return Whether the sudokupuzzle that was given is a valid sudoku puzzle
    */
    public boolean isValidValueSudokuPuzzle(){
        
        for(int i = 0; i < 9; i++){      //Double nested loop to go through all values and see if values are allowed in the sudoku
            
            for(int n = 0; n < 9; n++){             
                
                if(resultSudoku[i][n] > 9 || resultSudoku[i][n] < 0){       //Condition to see if the value at a specific position is valid
                    
                    System.out.println("This sudoku puzzle has invalid values. You need to use 0's for placeholder.");
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
 
        return isValidValueSudokuPuzzle() && isValidResult();    //Return if the result sudoku is a valid sudoku and the result is valid
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
    public boolean checkRowSame(int row, int col){
        
        int val = resultSudoku[row][col]; //Grab the values in current position
        
        for(int i = 0; i < 9; i++){ //Traverse through the entire row
            
            if(resultSudoku[row][i] == val && i != col){ //Check if there's a value in the row that is current position
                
                System.out.println("Value in column " + (col+1) + " and in column " + (i+1) + " in row " + (row+1) + " is the same."); //Error message if there's a problem
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
    public boolean checkColumnSame(int row, int col){
        
        int val = resultSudoku[row][col]; //Grab the values in current position
        
        for(int i = 0; i < 9; i++){ //Traverse through the entire column
            
            if(resultSudoku[i][col] == val && i != row){ //Check if there's a value in the column that is current position
                
                System.out.println("Value in row " + (row+1) + " and in row " + (i+1) + " in column " + (col+1) + " is the same."); //Error message if there's a problem
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
    public boolean checkSquareGridSame(int row, int col){
        
        int val = resultSudoku[row][col]; //Grab the values in current position
        int gridRow = row/3; //Find which grid row it is on
        int gridCol = col/3; //Find which grid column this is on
        
        for(int i = 3*gridRow; i < 3*gridRow + 3; i++){ //Traverse through this 3x3 grid's row
            
            for(int n = 3*gridCol; n < 3*gridCol + 3; n++){ //Traverse through this 3x3 grid's column
                
                if(val == resultSudoku[row][col] && (i!= row || n != col)){ //Check if there are values that are the same in this grid
                    
                    System.out.println("Something in row " + (row+1) + " column " + (col+1) + " is the same as row " + (i+1) + " column " + (n+1) + "."); //Error message if there's a problem
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /*
    A method to print the current form of the sudoku
    */
    public void printSudoku(){
        
        System.out.println("The current sudoku is: ");
        
        for(int i = 0; i < 9; i++){ //Double nested loop to go through all 81 position in the sudoku
            
            System.out.print("| "); //Add | at the beginning of every row
            
            for(int n = 0; n < 9; n++){
                
                System.out.print(resultSudoku[i][n] + " ");
                
                if(n%3 == 2){
                    System.out.print("| "); //Add | after every 3 numbers to help separate the printed form into tinier grids
                }
            }
            
            System.out.println();
        }
    }
    
    /*
    This method is for the puzzle is start trying to solve the puzzle on its own using the more human method.
    First eliminate the possible values on all spots from default puzzle.
    The proceed to fill it those spots that only has one posible values.
    Using those spots, eliminate possible values in the same row, column and 3x3 grid.
    Find the next possible values until there's nothing left.
    Check the amount of spots left that are empty and return it.
    @return The number of spots that this method weren't able to solve.
    */
    public int solvePuzzle(){
        
        removeAllKnownImpossibleVal(); //Remove all known impossible values at each spot first
        searchAndFill(); //Find a random spot that can be solved first and start from there
        
        int numberUnsolvedSpot = 0; //Variables to return how many spots cannot be solved
        
        for(int i = 0; i < 9; i++){ //Nested loop to go through all spaces to determine how many spots are unsolved
            
            for( int n = 0; n < 9; n++){
                
                if(resultSudoku[i][n] == 0){
                    
                    numberUnsolvedSpot++;
                }
            }
        }
        return numberUnsolvedSpot;
    }
    
    /*
    The method to remove all values that are impossible in all the unfilled spots
    */
    public void removeAllKnownImpossibleVal(){
        
        for(int i = 0; i < 9; i++){ //Nested loop to run through all spot to eliminate everything in the sudoku
            
            for(int n = 0; n < 9; n++){
                
                if(resultSudoku[i][n] != 0) { //If there's a value in the current position, remove the probability in the same spot, row, column and 3x3 grid 
                    
                    removeAllPossibleInSpot(i, n);
                    removeAllPossibleInRow(i, n);
                    removeAllPossibleInCol(i, n);
                    removeAllPossibleInGrid(i, n);
                }
            }
        }
    }
    
    /*
    If a spot is filled in, it cannot be filled in for anything else.
    Eliminate all possible values this spot can be.
    */
    public void removeAllPossibleInSpot(int row, int col){
        
        for(int i = 0; i < 9; i++){ //Made sure all probability in this spot is false
            
            eachSpotPossibleNum[row][col][i] = false;
        }
    }
    
    /*
    If a spot is filled in, it cannot have the same value in the same row.
    Eliminate all possible values this spot can be in the row.
    */
    public void removeAllPossibleInRow(int row, int col){
        
        int val = resultSudoku[row][col]; //Get the value in the spot and convert it to the position
        val = val - 1;
        
        for(int i = 0; i < 9; i++){ //Run a loop to remove all chance of this being in same row
            
            /*
            if(i == 8 && col == 3 && val == 5){
                System.out.println("Problem coordinate is: " + row + " " + col);
            }
            Debug code
            */ 
            
            eachSpotPossibleNum[row][i][val] = false;
        }
    }
    
    /*
    If a spot is filled in, it cannot have the same value in the same column.
    Eliminate all possible values this spot can be in the column.
    */
    public void removeAllPossibleInCol(int row, int col){
        
        int val = resultSudoku[row][col]; //Get the value in the spot and convert it to the position
        val = val - 1;
        
        for(int i = 0; i < 9; i++){ ////Run a loop to remove all chance of this being in same column
            
            /*
            if(row == 8 && i == 3 && val == 5){
                System.out.println("Problem coordinate is: " + row + " " + col);
            }
            debug code
            */
            
            eachSpotPossibleNum[i][col][val] = false;
        }
    }
    
    /*
    If a spot is filled in, it cannot have the same value in the same 3x3 grid.
    Eliminate all possible values this spot can be in the 3x3 grid.
    */
    public void removeAllPossibleInGrid(int row, int col){
        
        int gridRow = row/3; //Find which grid row it is on
        int gridCol = col/3; //Find which grid column this is on
        int val = resultSudoku[row][col]; //Get the value in the spot and convert it to the position
        val = val - 1;

        for(int i = 3*gridRow; i < 3*gridRow + 3; i++){ //Traverse through this 3x3 grid's row
            
            for(int n = 3*gridCol; n < 3*gridCol + 3; n++){ //Traverse through this 3x3 grid's column
                
                eachSpotPossibleNum[i][n][val] = false;
            }
        }
    }
    
    /*
    Search for any position in the grid that can only have one possible number and fill it.
    Afterward remove all possibility in same row, column and 3x3 grid and continue filling it.
    */
    public void searchAndFill(){
        
        boolean found = true; //Variables in case the need to go back and recheck previous position
        
        while(found){ //While things were still changing
            found = false;
            
            for(int i = 0; i < 9; i++){ //Nested loop to check all position to see if there's any 

                for(int n = 0; n < 9; n++){

                    int possibleCount = 0; //Variables to count how many possible numbers it could be
                    
                    /*
                    if(i == 1 && n == 4){
                        boolean temp = true;
                    }
                    debug code
                    */

                    for(int t = 0; t < 9; t++){ //Run through all of the values to count

                        if(eachSpotPossibleNum[i][n][t] == true){
                            possibleCount++;
                        }
                    }

                    if(possibleCount == 1){ //This spot can only have one single values. Fill in the spot
                        fillInSpot(i, n);
                        found = true;
                    } 
                }
            }
            
            if(!found) found = findTheOnlyOneInGrid();
        }
    }
    
    /*
    This methods fills in the spot and remove all the posbility in the current row and column and 3x3 grid.
    */
    public void fillInSpot(int row, int col){
        
        for(int i = 0; i < 9; i++){ //Find the only possible values that can be in this spot
            
            if(eachSpotPossibleNum[row][col][i]){ //Once the only value that can be in this spot is found
                
                resultSudoku[row][col] = i+1; //Fill in the value into this spot
                eachSpotPossibleNum[row][col][i] = false; //Eliminate the possibilty in this spot
                System.out.println("Row " + (row+1) + " column " + (col+1) + " has been filled in with " + (i+1) + ".");
                
                fillAllPossibleInRow(row, col, i); //Remove all posibility with the same value from the same row
                fillAllPossibleInCol(row, col, i); //Remove all posibility with the same value from the same column
                fillAllPossibleInGrid(row, col, i); //Remove all posibility with the same value from the same 3x3 grid
            }
        }
    }
    
    /*
    This methods fills in the spot and remove all the posbility in the current row with the same values.
    Also check to see if after removal there are spots that can only be one possible values and fill it in.
    */
    public void fillAllPossibleInRow(int row, int col, int val){
        
        for(int i = 0; i < 9; i++){ // A loop to run through all spots in the same row
            
            /*
            if(i == 8 && col == 3 && val == 5){
                System.out.println("Problem coordinate is: " + row + " " + col);
            }
            debug code
            */
            
            eachSpotPossibleNum[row][i][val] = false;
            int possibleValCount = 0;
        
            for(int n = 0; n < 9; n++){ //Loop to check if there's any spot in the same row that has only one possbility
                
                if(eachSpotPossibleNum[row][i][n]){
                    
                    possibleValCount++;
                }
            }
            
            if(possibleValCount == 1){ //In this specific spot, only one possible value can be there so fill it in
                
                fillInSpot(row, i);
            }
        }
    }
    
    /*
    This methods fills in the spot and remove all the posbility in the current column with the same values.
    Also check to see if after removal there are spots that can only be one possible values and fill it in.
    */
    public void fillAllPossibleInCol(int row, int col, int val){
        
        for(int i = 0; i < 9; i++){
            
            /*
            if(row == 8 && i == 3 && val == 5){
                System.out.println("Problem coordinate is: " + row + " " + col);
            }
            *Debug Code
                    */
            
            eachSpotPossibleNum[i][col][val] = false;
            int possibleValCount = 0;
        
            for(int n = 0; n < 9; n++){ //Loop to check if there's any spot in the same column that has only one possbility
                
                if(eachSpotPossibleNum[i][col][n]){
                    
                    possibleValCount++;
                }
            }
            
            if(possibleValCount == 1){ //In this specific spot, only one possible value can be there so fill it in
                
                fillInSpot(i, col);
            }
        }
    }
    
    /*
    This methods fills in the spot and remove all the posbility in the current 3x3 grid with the same values.
    Also check to see if after removal there are spots that can only be one possible values and fill it in.
    */
    public void fillAllPossibleInGrid(int row, int col, int val){
        
        int gridRow = row/3; //Find which grid row it is on
        int gridCol = col/3; //Find which grid column this is on  
        
        for(int i = 3*gridRow; i < 3*gridRow + 3; i++){ //Traverse through this 3x3 grid's row
            
            for(int n = 3*gridCol; n < 3*gridCol + 3; n++){ //Traverse through this 3x3 grid's column
                
                /*
                if(i == 8 && n == 3 && val == 5){
                    System.out.println("Problem coordinate is: " + row + " " + col);
                }
                debug code
                */
                
                eachSpotPossibleNum[i][n][val] = false;
                int possibleValCount = 0;
                
                for(int t = 0; t < 9; t++){ //Check how many possibility of values in this spot
                    
                    if(eachSpotPossibleNum[i][n][t]){
                        
                        possibleValCount++;
                    }
                }
                
                if(possibleValCount == 1){ //In this specific spot, only one possible value can be there so fill it in
                
                    fillInSpot(i, n);
                }
            }
        }
    }
    
    /*
    Find the only spot in a particular 3x3 grid out of the entire grid that can only have 1 number.
    @return Whether anything was found using this method
    */
    public boolean findTheOnlyOneInGrid(){
        
        System.out.println("Finding the special one.");
        
        boolean found = false; //Variable to return to see if there's any such spot found
        
        for(int gridRow = 0; gridRow < 3; gridRow++){ //Nested loop to check each of the 3x3 grid in the sudoku
            
            for(int gridCol = 0; gridCol < 3; gridCol++){
                
                int[] possibleValueOccures = new int[10]; //A integer array to hold all amount of occurence possible
                
                for(int row = 0; row < 3; row++){ //Nested loop to run through each 3x3 grid
                    
                    for(int col = 0; col < 3; col++){
                        
                        for(int val = 0; val < 9; val++)
                            
                            if(eachSpotPossibleNum[3*gridRow+row][3*gridCol+col][val]){ //If a value is possible in this position, add to the amount of possible occurence
                                
                                possibleValueOccures[val]++;
                        }
                    }
                }
                
                for(int val = 0; val < 9; val++){
                    
                    if(possibleValueOccures[val] == 1){
                        
                        found = true;
                        
                        for(int row = 0; row < 3; row++){
                            
                            for(int col = 0; col < 3; col++){
                                
                                if(eachSpotPossibleNum[3*gridRow+row][3*gridCol+col][val]){
                                    
                                    for(int i = 0; i < 9; i++){
                                        
                                        if(i != val) {
                                            eachSpotPossibleNum[3*gridRow+row][3*gridCol+col][i] = false;
                                        }
                                    }
                                    
                                    fillInSpot(3*gridRow+row, 3*gridCol+col);
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return found;
    }
}
 