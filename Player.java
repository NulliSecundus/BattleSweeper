import java.util.ArrayList;

/**
 * This class contains data and methods that deal with each player. It is 
 * relatively straight-forward, and holds the player's name and a list of
 * puzzles that they have won, as well as methods that manipulate these items.
 */
public class Player {
	private String playerName; //The player's name
	private ArrayList<String> puzzlesWon = new ArrayList<String>(); //a list of puzzles they have won
	
	/**
	 * Player constructor
	 *
	 */
	public Player(){
		playerName = null;
	}
	
	/**
	 * Player constructor (overloaded)
	 *
	 */
	public Player(String playerName){
		this.playerName = playerName;
	}
	
	/**
	 * Player constructor (overloaded)
	 *
	 */
	public Player(String playerName, ArrayList<String> puzzlesWon){
		this.playerName = playerName;
		for(int i = 0; i < puzzlesWon.size(); i++){
			this.addPuzzleToPuzzleWon(puzzlesWon.get(i));
		}
	}
	
	/**
	 * Returns the player's name
	 * 
	 * @return String
	 */
	public String getPlayerName(){
		return playerName;
	}
	
	/**
	 * Sets the player's name
	 *
	 * @param playerName the player's name
	 * @return void
	 */
	public void setPlayerName(String playerName){
		this.playerName = playerName;
	}
	
	/**
	 * Converts the list of puzzles the user has won to a string
	 * @return String
	 */
	public String puzzlesWonToString(){
		String toReturn = "";
		for(int j = 0; j < puzzlesWon.size(); j++){
			toReturn += puzzlesWon.get(j)+" ";
		}
		
		return toReturn;
	}
	
	/**
	 * Adds a puzzle to the list of puzzles the player has won
	 *
	 * @param puzzle the puzzle to add
	 * @return void
	 */
	public void addPuzzleToPuzzleWon(String puzzle){
		puzzlesWon.add(puzzle);
	}
}
