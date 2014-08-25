/**
 * This class handles data and methods pertaining to each puzzle. This includes
 * the grids the puzzles use and the methods that manipulate the grids, as well
 * as the name of the puzzle.
 */
public class Puzzle {
	private char[][] completeGrid = new char[6][6]; //the complete grid
	private char[][] copyOfCompleteGrid = new char[6][6]; //a copy of the complete grid, for testing purposes
	private char[][] userGrid = new char[6][6]; //the user grid
	private char[][] copyOfUserGrid = new char[6][6]; //a copy of the user grid, for testing purposes
	private char[][] copyOfUserGridForTestingPurposes = new char[6][6]; //a copy of the user grid, also for testing purposes (separate)
	private String puzzleName; //the name of the puzzle
	
	/**
	 * Puzzle constructor
	 *
	 */
	public Puzzle(String name){
		this.fillGrid();
		this.puzzleName = name;
	}
	
	/**
	 * Gets the name of the puzzle
	 *
	 * @return String
	 */
	public String getPuzzleName(){
		return puzzleName;
	}
	
	/**
	 * Converts the complete grid to a string
	 * 
	 * @return String
	 */
	public String completeGridToString(){
		String toReturn = "";
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 5; j++){
				toReturn += completeGrid[i][j]+" ";
			}
			toReturn += completeGrid[i][5]+"\n";
		}
		return toReturn;
	}
	
	/**
	 * Converts any formated grid to a string with proper game formating
	 *
	 * @param grid a 2D array to be converted
	 * @return String
	 */
	public String gridToString(char[][] grid){
		String toReturn = "";
		
		toReturn += "    " + 
			this.checkColumn(0) +
			" " +
			this.checkColumn(1) +
			" " +
			this.checkColumn(2) +
			" " +
			this.checkColumn(3) +
			" " +
			this.checkColumn(4) +
			" " +
			this.checkColumn(5) + 
			"\n\n";
		
		for(int i = 0; i < 6; i++){
			toReturn += (char) (97 + i);
			toReturn += "   ";
			for(int j = 0; j < 5; j++){
				toReturn += grid[i][j]+" ";
			}
			toReturn += grid[i][5];
			toReturn += "   " + this.checkRow(i) +"\n";
		}
		toReturn += "\n    g h i j k l\n\n";
		
		return toReturn;
	}
	
	/**
	 * Returns the number of ship pieces in a given column
	 *
	 * @param column an integer corresponding to the column to be checked
	 * @return int
	 */
	private int checkColumn(int column){
		int toReturn = 0;
		for(int i = 0; i < 6; i++){
			if(completeGrid[i][column] == '<' || 
					completeGrid[i][column] == '+' || 
					completeGrid[i][column] == '>' || 
					completeGrid[i][column] == '^' || 
					completeGrid[i][column] == 'v' ||
					completeGrid[i][column] == 'o')
			{
				toReturn++;
			}
		}
		return toReturn;
	}
	
	/**
	 * Returns the number of ship pieces in a given row
	 *
	 * @param row an integer corresponding to a row to be checked
	 * @return int
	 */
	private int checkRow(int row){
		int toReturn = 0;
		for(int i = 0; i < 6; i++){
			if(completeGrid[row][i] == '<' || 
					completeGrid[row][i] == '+' || 
					completeGrid[row][i] == '>' || 
					completeGrid[row][i] == '^' || 
					completeGrid[row][i] == 'v' ||
					completeGrid[row][i] == 'o')
			{
				toReturn++;
			}
		}
		return toReturn;
	}
	
	/**
	 * Changes a single tile from the user grid
	 *
	 * @param i,j the grid location of the piece to be changed
	 * @param a character to be changed to 
	 * @return void
	 */
	public void changeUserGridTile(char a, int i, int j){
		userGrid[i][j] = a;
	}
	
	/**
	 * Changes a single tile from the complete grid
	 *
	 * @param i,j the grid location of the piece to be changed
	 * @param a character to be changed to 
	 * @return void
	 */
	public void changeCompleteGridTile(char a, int i, int j){
		completeGrid[i][j] = a;
	}
	
	/**
	 * Gets a single tile from the complete grid
	 *
	 * @param i,j the grid location of the piece to be changed
	 * @return void
	 */
	public char getCharFromCompleteGrid(int i, int j){
		return completeGrid[i][j];
	}
	
	/**
	 * Gets a single tile from the user grid
	 *
	 * @param i,j the grid location of the piece to be changed
	 * @return void
	 */
	public char getCharFromUserGrid(int i, int j){
		return userGrid[i][j];
	}
	
	/**
	 * Returns the complete grid
	 *
	 * @return char[][]
	 */
	public char[][] returnCompleteGrid(){
		return completeGrid;
	}
	
	/**
	 * Returns the user's grid
	 *
	 * @return char[][]
	 */
	public char[][] returnUserGrid(){
		return userGrid;
	}
	
	/**
	 * Returns a copy of the user's grid
	 *
	 * @return char[][]
	 */
	public char[][] returnCopyOfUserGrid(){
		return copyOfUserGrid;
	}
	
	/**
	 * Copies one grid onto another
	 *
	 * @param gridToCopy the grid to be copied
	 * @param copiedGrid the grid to be copied to
	 * @return void
	 */
	public void copyGrid(char[][] gridToCopy, char[][] copiedGrid){
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				copiedGrid[i][j] = gridToCopy[i][j];
			}
		}
	}
	
	/**
	 * Changes every ship character to an 'S'
	 *
	 * @param grid the grid to manipulate
	 * @return void
	 */
	private void changeShipCharactersToS(char[][] grid){
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				if(grid[i][j] == '<' 
						|| grid[i][j] == '>' 
						|| grid[i][j] == '+' 
						|| grid[i][j] == '^' 
						|| grid[i][j] == 'v'
						|| grid[i][j] == 'o'){
					
					grid[i][j] = 'S';
				}
			}
		}
	}
	
	/**
	 * Returns true if userGrid is the same as completeGrid
	 *
	 * @return boolean
	 */
	public boolean compareUserGridToCompleteGrid(){
		copyGrid(userGrid, copyOfUserGridForTestingPurposes);
		copyGrid(completeGrid, copyOfCompleteGrid);
		changeShipCharactersToS(copyOfCompleteGrid);
		changeShipCharactersToS(copyOfUserGridForTestingPurposes);
		boolean gridsTheSame = true;
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				if(copyOfUserGridForTestingPurposes[i][j] != 
						copyOfCompleteGrid[i][j]){
					userGrid[i][j] = 'E';
					gridsTheSame = false;
				}
			}
		}
		return gridsTheSame;
	}
	
	/**
	 * Returns all grids to start format
	 *
	 * @return void
	 */
	public void endGameCleanUp(){
		copyGrid(this.copyOfUserGrid, this.userGrid);
		copyGrid(this.copyOfUserGrid, this.copyOfUserGridForTestingPurposes);
	}
	
	/**
	 * Fills the grids with whatever values they need to start with
	 * @return void
	 */
	private void fillGrid(){
		
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				completeGrid[i][j] = '.';
			}
		}
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				userGrid[i][j] = '?';
			}
		}
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				copyOfUserGrid[i][j] = '?';
			}
		}
	}
}
