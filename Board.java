package trabalho2;

import java.util.LinkedList;

public class Board {
	final static int WIN = 1;
	final static int DRAW = 0;
	final static int LOSE = -1;
	final static int LENGTH = 9;
	
	final char state[];
	int utility;
	final Board parent;
	LinkedList<Board> childList;
	
	final int depth;
	boolean maxTurn;
	
	Board() {
		this.utility = 2;
		this.parent = null;
		childList = new LinkedList<Board>();
		this.state = null;
		this.depth = 0;
		this.maxTurn = false;
	}
	Board(Board parent,char[] state,int depth) {
		this.utility = 2;
		this.parent = parent;
		childList = new LinkedList<Board>();
		this.state = state;
		this.depth = depth;
		this.maxTurn = false;
	}
	Board(Board parent,char[] state,int depth,boolean turn) {
		this.utility = 2;
		this.parent = parent;
		childList = new LinkedList<Board>();
		this.state = state;
		this.depth = depth;
		this.maxTurn = turn;
	}
	
	/**
	 * Returns true if it MAXs turn to play
	 * @return
	 */
	boolean isMaxTurn() {
		return this.maxTurn;
	}
	
	/**
	 * Returns if a given node is the root of
	 * the search tree;
	 * @return True if root; False if not;
	 */
	boolean isRoot() {
		return parent == null;
	}

	/**
	 * If the game board of the given node is full,or there is a <code>WIN</code>,<code>LOSE</code> or a <code>DRAW</code>,it is a terminal state
	 * @return <code>true</code> if Node is terminal;<code>false</code> if not;
	 */
	boolean isTerminal() {
		int line = checkLines('X','O');
		int column = checkColumns('X','O');
		int diagonal = checkDiagonal('X','O');
		
		if(line == 1 || line == -1)
			return true;
		else if(column == 1 || column == -1)
			return true;
		else if(diagonal == 1 || diagonal == -1)
			return true;
		else {
			int nSpaces = 0;
			for(int i = 0; i < Board.LENGTH; i++)
				if(this.state[i] == '-')
					nSpaces++;
			
			if(nSpaces == 0)
				return true;
			else 
				return false;
		}
	}
	
	/**
	 * Print the game state for a given Node;
	 */
	void printState() {
		for(int index = 0; index < Board.LENGTH; index++) {
			if(index == 2 || index == 5 || index == 8)
				System.out.println(state[index]);
			else
				System.out.print(state[index] + " ");
		}
	}
	
	/**
	 * Receives the 'symbol' of the computer player 
	 * and assigns an utility value to a node;
	 * 1 if Computer wins;
	 * -1 if Human player wins;
	 * 0 if it is a tie;
	 * @param MAXPLAYER
	 * @return utility value
	 */
	void assignUtilityValue(char MAXPLAYER) {
		char MINPLAYER;
		if(MAXPLAYER == 'X')
			MINPLAYER = 'O';
		else
			MINPLAYER = 'X';
		
		int line = checkLines(MAXPLAYER,MINPLAYER);
		int column = checkColumns(MAXPLAYER,MINPLAYER);
		int diagonal = checkDiagonal(MAXPLAYER,MINPLAYER);
		
		if(line == 1 || column == 1 || diagonal == 1)
			this.utility = WIN;
		else if(line == -1 || column == -1 || diagonal == -1)
			this.utility = LOSE;
		else
			this.utility = DRAW;
	}
	
	
	/**
	 * Check if there is a player about to win in a Line.
	 * @param MAXPLAYER
	 * @return 1 for computer; -1 for human player; 0 for tie;
	 */
	private int checkLines(char MAXPLAYER,char MINPLAYER) {
		int index = 0;
		if(state[index] == state[index + 1] && state[index] == state[index + 2]) {
			if(state[index] == MAXPLAYER)
				return 1;
			else if(state[index + 1] == MINPLAYER) 
				return -1;
		}		
		index = 3;
		if(state[index] == state[index + 1] && state[index] == state[index + 2]) {
			if(state[index] == MAXPLAYER)
				return 1;
			else if(state[index] == MINPLAYER) 
				return -1;
		}
		index = 6;
		if(state[index] == state[index + 1] && state[index] == state[index + 2]) {;
			if(state[index] == MAXPLAYER)
				return 1;
			else if(state[index] == MINPLAYER)
				return -1;
		}

		return 0;
	}
	
	/**
	 * Check if there is a player about to win in Diagonal.
	 * @param MAXPLAYER
	 * @return 1 for computer; -1 for human player; 0 for tie;
	 */
	private int checkDiagonal(char MAXPLAYER,char MINPLAYER) {
		if(state[0] == state[4] && state[0] == state[8]) {
			if(state[0] == MAXPLAYER)
				return 1;
			else if(state[0] == MINPLAYER)
				return -1;
		}
		else if(state[2] == state[4] && state[2] == state[6]) {
			if(state[2] == MAXPLAYER)
				return 1;
			else if(state[2] == MINPLAYER)
				return -1;
		}
		return 0;
	}
	
	
	/**
	 * Check if there is a player about to win in a Column.
	 * @param MAXPLAYER
	 * @return 1 for computer; -1 for human player; 0 for tie;
	 */
	private int checkColumns(char MAXPLAYER,char MINPLAYER) {
		int index = 0;
		if(state[index] == state[index + 3] && state[index] == state[index + 6]) {
			if(state[index] == MAXPLAYER)
				return 1;
			else if(state[index] == MINPLAYER)
				return -1;
		}
		index = 1;
		if(state[index] == state[index + 3] && state[index] == state[index + 6]) { 
			if(state[index] == MAXPLAYER)
				return 1;
			else if(state[index] == MINPLAYER) 
				return -1;
		}
		index = 2;
		if(state[index] == state[index + 3] && state[index] == state[index + 6]) { 
			if(state[index] == MAXPLAYER)
				return 1;
			else if(state[index] == MINPLAYER) 
				return -1;
		}		
		return 0;
	}
}
