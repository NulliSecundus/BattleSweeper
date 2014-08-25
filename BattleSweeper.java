///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            BattleSweeper
// Files:            BattleSweeper.java Puzzle.java Player.java
// Semester:         CS302 Spring 2014
//
// Author:           Brendan Drackley
// Email:            bdrackley@wisc.edu
// CS Login:         drackley
// Lecturer's Name:  Nick Pappas
// Lab Section:      Lab 391
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Pair Partner:     Joanna Mohr
// Email:            jmohr8716@gmail.com
// CS Login:         mohr
// Lecturer's Name:  Nick Pappas
// Lab Section:      392
//
//                   STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR PARTNER
// Credits:          Senses Fail and other great bands
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is the main class of the program. It handles the main menu and 
 * basic game functionality.
 *
 * @author Brendan Drackley
 */
public class BattleSweeper {
	private int userChoice; //user's choice
	private int puzzleListIndex; //puzzle list index
	private int cellsRevealed; //number of cells revealed
	private int i; //game tile row
	private int j; //game tile column
	private int index; //index of a puzzle
	private boolean gameIsRunning; //current game while loop boolean
	private boolean mainMenuRunning; //main menu while loop boolean
	private boolean rowSelection; //row selection while loop boolean
	private boolean columnSelection; //column selection while loop boolean
	private boolean gameWon; //true if the game has been won
	private boolean proceed; //generic boolean to allow procession
	private char charToChange; //a character to be changed to
	private char revealedCell; //the character of a revealed cell
	private String userStringInput; //user's input string
	private String currentPlayerInfo; //current player info string
	private String rowColumnChoice; //the row/column choice info
	private String shipOrWater; //'s' or 'w'
	private String inputText; //text input string
	private String tempUserName; //temporary variable for user's name
	private String[] name; //String array for getting the puzzle name
	//list of players
	private ArrayList<Player> playerList = new ArrayList<Player>(); 
	//list of puzzles
	private ArrayList<Puzzle> puzzleList = new ArrayList<Puzzle>();
	//temporary list of puzzles
	private ArrayList<String> tempUserPuzzleList = new ArrayList<String>();
	private Scanner s = new Scanner(System.in); //System.in scanner (only one)
	private Puzzle currentPuzzle; //the currently being used puzzle
	
	
	/**
	 * BattleSweeper constructor, doesn't take any parameters. 
	 *
	 */
	public BattleSweeper(){
		userChoice = 0;
		puzzleListIndex = 0;
		gameIsRunning = false;
		mainMenuRunning = true;
		rowSelection = true;
		columnSelection = true;
		gameWon = false;
		userStringInput = "";
		currentPlayerInfo = "";
		rowColumnChoice = "";
		shipOrWater = "";
		charToChange = '?';
	}
	
	/**
	 * Shows the main menu and calls askUserChoice() to get the user's input
	 *
	 * @return void
	 */
	public void showMainMenu(){
		while(mainMenuRunning){
			System.out.print("=== BATTLESWEEPER MAIN MENU ===\n" +
					"\n" +
					"1. List player information\n" +
					"2. Load a new puzzle\n" +
					"3. Play a puzzle\n" +
					"4. Quit\n" +
					"\n");
			System.out.print("> ");
			askUserChoice();
		}
	}
	
	/**
	 * Uses scanner object to ask get user's choice, calls respondToUserChoice
	 * or writeUserInfoToFile accordingly.
	 * 
	 * @return void
	 */
	private void askUserChoice(){
		if(s.hasNextInt()){
			userChoice = s.nextInt();
			respondToUserChoice();
		}else{
			userStringInput = s.next();
			writeUserInfoToFile(userStringInput);
		}
	}
	
	/**
	 * Method that writes the user's info (puzzles won, initials) to a file.
	 *
	 * @return void
	 */
	private void writeUserInfoToFile(String fileName){
		String s;
		listPlayerInfo(2);
		Scanner infoScanner = new Scanner(currentPlayerInfo);
		File output = new File(fileName);
		try{
			PrintWriter pw = new PrintWriter(output);
			
			while(infoScanner.hasNextLine()){
				s = infoScanner.nextLine();
				pw.println(s);
			}
			
			pw.close();
		}catch(FileNotFoundException e){
			System.out.println("File was not found");
		}
		infoScanner.close();
	}
	
	/**
	 * Method that consists of a switch statement, directs the program based
	 * on user's menu choice.
	 *
	 * @return void
	 */
	private void respondToUserChoice(){
		switch(userChoice){
		case(1): listPlayerInfo(1);
		break;
		case(2): loadNewPuzzle();
		break;
		case(3): playPuzzle();
		break;
		case(4): quit();
		break;
		default: userChoice = 0;
		}
	}

	/**
	 * Method with duel functionality. Either prints all user info in game
	 * (menu choice 1) or assigns it to a variable to be written to a file
	 * as per writeUserInfoToFile().
	 *
	 * @param option an integer that determines the functionality of this
	 * method.
	 * @return void
	 */
	private void listPlayerInfo(int option){
		String toPrint = "";
		
		for(int i = 0; i < playerList.size(); i++){
			toPrint += playerList.get(i).getPlayerName()+
					": "
					+playerList.get(i).puzzlesWonToString()
					+"\n";
		}
		
		switch(option){
		case(1):System.out.println("\n=== BATTLESWEEPER PLAYERS ===\n" +
				"\n"+toPrint);
			break;
		case(2):currentPlayerInfo = toPrint;
			break;
		}
		
		
		
	}

	/**
	 * Method that "reveals" certain tiles on the user grid, based on the 
	 * configuration file. Also makes a copy of the user grid, to be used later
	 *
	 * @param inputScanner precomposed scanner object to be used in method 
	 * functionality
	 * @return void
	 */
	private void revealedLocationsOnUserGrid(Scanner inputScanner){
		cellsRevealed = inputScanner.nextInt();
		
		for(int i = 0; i < cellsRevealed; i++){
			inputText = inputScanner.next();
			revealedCell = puzzleList.get(puzzleListIndex).
					getCharFromCompleteGrid(
							charToInt(inputText.charAt(0)), 
							charToInt(inputText.charAt(2)));
			puzzleList.get(puzzleListIndex).
					changeUserGridTile(
							revealedCell,
							charToInt(inputText.charAt(0)), 
							charToInt(inputText.charAt(2)) );
		}
		currentPuzzle.copyGrid(
				currentPuzzle.returnUserGrid(), 
				currentPuzzle.returnCopyOfUserGrid());
	}
	
	/**
	 * Method that reads the configuration file and creates the "complete" grid
	 * that the user is working towards. Calls revealedLocationsOnUserGrid to
	 * handle the user grid portion of the config file. If the newly loaded 
	 * puzzle is already on the list, checkIfPuzzleAlreadyExists (called at
	 * the end of this method) will remove it. 
	 *
	 * @return void
	 */
	private void loadNewPuzzle(){
		System.out.print("\n=== LOAD A PUZZLE ===\n" +
				"\nEnter a filename: ");
		
		userStringInput = s.next();
		
		File input = new File(userStringInput);
		
		
		try{
			name = userStringInput.split("[.]");
			puzzleList.add(new Puzzle(name[0]));
			currentPuzzle = puzzleList.get(puzzleListIndex);
			Scanner inputScanner = new Scanner(input);
			while(inputScanner.hasNextLine()){
				if(inputScanner.hasNextInt()){
					//Handles integer response
					revealedLocationsOnUserGrid(inputScanner);
					break;
				}else{
					inputText = inputScanner.nextLine();
					if(inputText.length() < 4){
						singleShip(inputText, puzzleListIndex);
					}else if(inputText.length() < 8){
						int comparison = charToInt(inputText.charAt(0)) - 
								charToInt(inputText.charAt(4));
						switch(comparison){
							case(0):doubleHorizontalShip(inputText, 
									puzzleListIndex);
							break;
							default:doubleVerticleShip(inputText, 
									puzzleListIndex);
						}
						
					}else{
						int comparison = charToInt(inputText.charAt(0)) - 
								charToInt(inputText.charAt(4));
						switch(comparison){
						case(0):tripleHorizontalShip(inputText, 
								puzzleListIndex);
						break;
						default:tripleVerticleShip(inputText, puzzleListIndex);
						}
						
					}
					
				}
			}
			checkIfPuzzleAlreadyExists();
			inputScanner.close();
			puzzleListIndex++;
			System.out.println();
		}catch(FileNotFoundException e){
			System.out.println("Error: file "+userStringInput+" not found\n");
			puzzleList.remove(puzzleListIndex);
		}
		
	}
	
	/**
	 * Goes over list of puzzles and removes duplicates.
	 *
	 * @return void
	 */
	private void checkIfPuzzleAlreadyExists(){
		for(int i = 0; i < puzzleList.size() - 1; i++){
			if(currentPuzzle.getPuzzleName().equalsIgnoreCase(
					puzzleList.get(i).getPuzzleName())){
				puzzleList.remove(i);
				puzzleListIndex--;
			}
		}
	}
	
	/**
	 * Handles the single ship portion of the complete grid
	 *
	 * @param inputText info on location of ship
	 * @param index index of current puzzle in puzzle list
	 * @return void
	 */
	private void singleShip(String inputText, int index){
		int i = charToInt(inputText.charAt(0));
		int j = charToInt(inputText.charAt(2));
		puzzleList.get(index).changeCompleteGridTile('o', i, j);
	}
	
	/**
	 * Handles the double ship portion of the complete grid
	 *
	 * @param inputText info on location of ship
	 * @param index index of current puzzle in puzzle list
	 * @return void
	 */
	private void doubleHorizontalShip(String inputText, int index){
		puzzleList.get(index).changeCompleteGridTile('<', charToInt(
				inputText.charAt(0)), charToInt(inputText.charAt(2)));
		puzzleList.get(index).changeCompleteGridTile('>', charToInt(
				inputText.charAt(4)), charToInt(inputText.charAt(6)));
	}
	
	/**
	 * Handles the double ship portion of the complete grid
	 *
	 * @param inputText info on location of ship
	 * @param index index of current puzzle in puzzle list
	 * @return void
	 */
	private void doubleVerticleShip(String inputText, int index){
		puzzleList.get(index).changeCompleteGridTile('^', charToInt(
				inputText.charAt(0)), charToInt(inputText.charAt(2)));
		puzzleList.get(index).changeCompleteGridTile('v', charToInt(
				inputText.charAt(4)), charToInt(inputText.charAt(6)));
	}
	
	/**
	 * Handles the triple ship portion of the complete grid
	 *
	 * @param inputText info on location of ship
	 * @param index index of current puzzle in puzzle list
	 * @return void
	 */
	private void tripleHorizontalShip(String inputText, int index){
		puzzleList.get(index).changeCompleteGridTile('<', charToInt(
				inputText.charAt(0)), charToInt(inputText.charAt(2)));
		puzzleList.get(index).changeCompleteGridTile('+', charToInt(
				inputText.charAt(4)), charToInt(inputText.charAt(6)));
		puzzleList.get(index).changeCompleteGridTile('>', charToInt(
				inputText.charAt(8)), charToInt(inputText.charAt(10)));
	}
	
	/**
	 * Handles the triple ship portion of the complete grid
	 *
	 * @param inputText info on location of ship
	 * @param index index of current puzzle in puzzle list
	 * @return void
	 */
	private void tripleVerticleShip(String inputText, int index){
		puzzleList.get(index).changeCompleteGridTile('^', charToInt(
				inputText.charAt(0)), charToInt(inputText.charAt(2)));
		puzzleList.get(index).changeCompleteGridTile('+', charToInt(
				inputText.charAt(4)), charToInt(inputText.charAt(6)));
		puzzleList.get(index).changeCompleteGridTile('v', charToInt(
				inputText.charAt(8)), charToInt(inputText.charAt(10)));
	}
	
	/**
	 * Converts 'char a' to the corresponding grid-integer
	 *
	 * @param a character that is to be converted
	 * @return int
	 */
	private int charToInt(char a){
		switch(a){
		case('a'):return 0;
		case('b'):return 1;
		case('c'):return 2;
		case('d'):return 3;
		case('e'):return 4;
		case('f'):return 5;
		case('g'):return 0;
		case('h'):return 1;
		case('i'):return 2;
		case('j'):return 3;
		case('k'):return 4;
		case('l'):return 5;
		default:return -1;
		}
	}
	
	/**
	 * Prints list of puzzles and asks user to choose one
	 *
	 * @return void
	 */
	private void playPuzzle(){
		System.out.println("\n=== BATTLESWEEPER PUZZLES ===\n");
		int num = 0;
		for(int i = 0; i < puzzleList.size(); i++){
			num++;
			System.out.println(num+". "+puzzleList.get(i).getPuzzleName());
		}
		num++;
		System.out.print(num+". Go Back\n" +
				"\n> ");

		if(s.hasNextInt()){
			userChoice = s.nextInt();
			try{
				currentPuzzle = puzzleList.get(userChoice - 1);
			}catch(IndexOutOfBoundsException e){
				
			}
			
		}
		if(userChoice == num){
			return;
		}else{
			gameIsRunning = true;
			playAGame(userChoice - 1);
		}
		
	}
	
	/**
	 * While loop with method calls that constitutes a single game
	 *
	 * @param puzzleIndex index of current puzzle from puzzle list
	 * @return void
	 */
	private void playAGame(int puzzleIndex){
		while(gameIsRunning){
			printGame(puzzleIndex);
			makeMove(puzzleIndex);
			if(!gameIsRunning){
				return;
			}
			checkIfGameIsOver();
		}
	}
	
	/**
	 * Literally checks if the game is over. Calls checkIfWonOrLost
	 *
	 * @return void
	 */
	private void checkIfGameIsOver(){
		gameIsRunning = false;
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				if(currentPuzzle.getCharFromUserGrid(i, j) == '?'){
					gameIsRunning = true;
				}
			}
		}
		if(!gameIsRunning){
			checkIfWonOrLost();
		}
	}
	
	/**
	 * Does exactly what you would expect it to. Also calls endGameCleanUp to
	 * revert puzzles back to the way they were for another play through
	 *
	 * @return void
	 */
	private void checkIfWonOrLost(){
		gameWon = currentPuzzle.compareUserGridToCompleteGrid();
		
		
		if(gameWon){
			gameWonCondition();
		}else{
			System.out.print("\n"+currentPuzzle.
					gridToString(currentPuzzle.returnUserGrid()));
			System.out.println("Oh no...\n");
		}
		currentPuzzle.endGameCleanUp();
	}
	
	/**
	 * If game is won, takes user's initials (if they don't already have them
	 * saved in game) and adds the puzzle name to their list of solved puzzles
	 * (if it isn't there already)
	 *
	 * @return void
	 */
	private void gameWonCondition(){
		
		System.out.print("\n"+currentPuzzle.gridToString(
				currentPuzzle.returnCompleteGrid()));
		
		System.out.print("Congratulations!\n" +
				"Enter your initials: ");
		if(s.hasNext()){
			tempUserName = s.next();
			tempUserName = tempUserName.toUpperCase();
			if(tempUserName.length() > 3){
				tempUserName = tempUserName.substring(0, 3);
			}
			tempUserPuzzleList.add(currentPuzzle.getPuzzleName());
			proceed = true;
			for(int i = 0; i < playerList.size(); i++){
				if(playerList.get(i).getPlayerName().equalsIgnoreCase(
						tempUserName)){
					proceed = false;
					index = i;
				}
			}
			if(proceed){
				playerList.add(new Player(tempUserName, tempUserPuzzleList));
			}else if(!playerList.get(index).puzzlesWonToString().contains(
					currentPuzzle.getPuzzleName())){
				
				playerList.get(index).addPuzzleToPuzzleWon(
						currentPuzzle.getPuzzleName());
			}
			tempUserPuzzleList.remove(0);
		}
		
	}
	
	/**
	 * prints solution puzzle
	 *
	 * @return void
	 */
	private void printSolution(){
		System.out.print(currentPuzzle.gridToString(
				currentPuzzle.returnCompleteGrid()));
	}
	
	/**
	 * prints user's puzzle
	 *
	 * @return void
	 */
	private void printGame(int puzzleIndex){
		System.out.print(currentPuzzle.gridToString(
				currentPuzzle.returnUserGrid()));
	}
	
	/**
	 * Gets user's input to make a move
	 *
	 * @return void
	 */
	private void getMoveToMake(){
		rowSelection = true;
		columnSelection = true;
		while(rowSelection || columnSelection){
			rowColumnChoice = s.next();
			rowColumnChoice.toLowerCase();
			if(rowColumnChoice.length() < 3){
				shipOrWater = rowColumnChoice;
				return;
			}
			switch(rowColumnChoice.charAt(0)){
			case('a'):rowSelection = false;
			case('b'):rowSelection = false;
			case('c'):rowSelection = false;
			case('d'):rowSelection = false;
			case('e'):rowSelection = false;
			case('f'):rowSelection = false;
				break;
			default:rowSelection = true;
			}
			
			switch(rowColumnChoice.charAt(2)){
			case('g'):columnSelection = false;
			case('h'):columnSelection = false;
			case('i'):columnSelection = false;
			case('j'):columnSelection = false;
			case('k'):columnSelection = false;
			case('l'):columnSelection = false;
				break;
			default:columnSelection = true;
			}
			
			i = charToInt(rowColumnChoice.charAt(0));
			j = charToInt(rowColumnChoice.charAt(2));
			
			if(currentPuzzle.getCharFromUserGrid(i, j) != '?'){
				System.out.println("That location is fixed!\n");
				rowSelection = true;
				columnSelection = false;
				System.out.print("Row,column or (q)uit: ");
			}
		}
	}

	/**
	 * Handles the option to quit in game
	 *
	 * @return void
	 */
	private void quitInGame(){
		gameIsRunning = false;
		
		printSolution();
		
		System.out.println("Quitter.\n");
	}
	
	/**
	 * Makes the appropriate move based on user input.
	 * Kind of a pain in the ass to code.
	 *
	 * @param puzzleIndex index of the puzzle currently being played
	 * @return void
	 */
	private void makeMove(int puzzleIndex){
		System.out.print("Row,column or (q)uit: ");
		if(s.hasNext()){
			
			getMoveToMake();
			
			if(rowColumnChoice.length() > 2){
				System.out.print("\n(s)hip or (w)ater? ");
				shipOrWater = s.next();
				shipOrWater.toLowerCase();
			}
			
			charToChange = '?';
			switch(shipOrWater){
			case("s"):charToChange = 'S';
				break;
			case("w"):charToChange = '.';
				break;
			case("q"):quitInGame();
				return;
			}
			
			if(charToChange == '.' && 
					currentPuzzle.getCharFromUserGrid(i, j) == '?'){
				
				currentPuzzle.changeUserGridTile(charToChange, i, j);
				
			}else if(charToChange == 'S' && 
					currentPuzzle.getCharFromUserGrid(i, j) == '?'){
				
				currentPuzzle.changeUserGridTile(charToChange, i, j);
				
			}else{
				System.out.println("Oh no...");
				gameIsRunning = false;
			}
		}
	}
	
	/**
	 * Main menu option to quit. Saves file with user info written on it.
	 *
	 * @return void
	 */
	private void quit(){
		System.out.print("\n=== BEFORE YOU GO! ===\n" +
				"\n" +
				"Enter player history filename: ");
		askUserChoice();
		mainMenuRunning = false;
	}
	
	/**
	 * Main method, creates a new game of BattleSweeper
	 *
	 * @return void
	 */
	public static void main(String[] args) {
		
		BattleSweeper newGame = new BattleSweeper();
		newGame.showMainMenu();
		
		
//		try{
//			File input = new File("player_input.txt");
//			Scanner fileScanner = new Scanner(input);
//			String temp;
//			String tempName;
//			ArrayList<String> tempPuzzles = new ArrayList<String>();
//			
//			while(fileScanner.hasNextLine()){
//				temp = fileScanner.nextLine();
//				Scanner tempScanner = new Scanner(temp);
//				tempName = tempScanner.next();
//				while(tempScanner.hasNext()){
//					tempPuzzles.add(tempScanner.next());
//				}
//				newGame.playerList.add(new Player(tempName, tempPuzzles));
//			}
//		}catch(FileNotFoundException e){
//			
//		}
		
		
		
	}

}
