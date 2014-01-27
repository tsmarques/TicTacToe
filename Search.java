package trabalho2;

import java.util.LinkedList;
import java.util.LinkedList;

public class Search {
	Board root;
	LinkedList<Board> terminalStates;
	LinkedList<Board> list;
	char MAXPLAYER;
	char MINPLAYER;
	
	Search(Board root,char MAXPLAYER) {
		this.root = root;
		terminalStates = new LinkedList<Board>();
		list = new LinkedList<Board>();
		this.MAXPLAYER = MAXPLAYER;
		if(this.MAXPLAYER == 'X')
			MINPLAYER = 'O';
		else
			MINPLAYER = 'X';
	}
	
	
	/**
	 * Expand a node through all the possible configurations,
	 * and add them to the tree list.
	 * @param node
	 */
	public void expandNode(Board node) {
		for(int index = 0; index < Board.LENGTH;index++) {
			char newState[] = node.state.clone();
			if(newState[index] == '-') {
				if(node.isMaxTurn())
					newState[index] = MAXPLAYER;
				else
					newState[index] = MINPLAYER;
				
				Board newNode = new Board(node,newState,node.depth + 1,!node.isMaxTurn());
				node.childList.add(newNode);
				if(newNode.isTerminal()) {
					newNode.assignUtilityValue(MAXPLAYER);
					terminalStates.addFirst(newNode);
				}
				else
					list.addFirst(newNode);
			}
		}	
	}
	
	/**
	 * Generates the search tree 
	 * and returns the best node to follow
	 * @return
	 */	
	public Board getMinMaxState() {
		list.addFirst(root);
		int maxVal = maxVal = getMax(root);
		for(Board child : root.childList) {
			if(child.utility == maxVal)
				return child;
			}
		return null;
	}
	
	/**
	 * Function used in minimax to get
	 * the maximum value from the minimum value of <code>MINPLAYER</code>
	 * @param node
	 * @return minVal
	 */
	private int getMax(Board node) {
		if(node.isTerminal())
			return node.utility;
		int maxVal = Integer.MIN_VALUE;
		expandNode(node);
		for(Board child : node.childList) {
			int maxMinVal = getMin(child);
			if(maxMinVal > maxVal) {
				maxVal = maxMinVal;
				node.utility = maxMinVal;
			}
		}
		return maxVal;
	}
	
	/**
	 * Function used in minimax to get
	 * the minimum value from the maximum value of <code>MAXPLAYER</code>
	 * @param node
	 * @return minVal
	 */
	private int getMin(Board node) {
		if(node.isTerminal())
			return node.utility;
		int minVal = Integer.MAX_VALUE;
		expandNode(node);
		for(Board child : node.childList) {
			int minMaxVal = getMax(child);
			if(minMaxVal < minVal) {
				minVal = minMaxVal;
				node.utility = minMaxVal;
			}
		}
		return minVal;
	}
	
	//ALGORITHMS FOR ALPHA-BETA PRUNNING
	/**
	 * Alpha-Beta Prunning
	 * @return next move from <code>MAXPLAYER</code>
	 */
	public Board getMinMaxStateAB() {
		list.addFirst(root);
		int maxVal = maxVal = getMaxAB(root, Integer.MIN_VALUE,Integer.MAX_VALUE);
		for(Board child : root.childList) {
			if(child.utility == maxVal)
				return child;
			}
		return null;
	}
	
	/**
	 * Function used in Alpha-Beta Pruning to get
	 * the maximum value from the minimum value of <code>MINPLAYER</code>
	 * @param node
	 * @return minVal
	 */
	private int getMaxAB(Board node, int alpha,int beta) {
		if(node.isTerminal())
			return node.utility;
		int maxVal = Integer.MIN_VALUE;
		expandNode(node);
		for(Board child : node.childList) {
			int minVal = getMinAB(child,alpha,beta);
			if(minVal > maxVal) {
				maxVal = minVal;
				node.utility = maxVal;
			}
			if(minVal >= beta)
				return minVal;
			if(alpha < minVal)
				alpha = minVal;
		}
		return maxVal;
	}
	
	/**
	 * Function used in Alpha-Beta Pruning to get
	 * the minimum value from the maximum value of <code>MAXPLAYER</code>
	 * @param node
	 * @return minVal
	 */
	private int getMinAB(Board node, int alpha, int beta) {
		if(node.isTerminal())
			return node.utility;
		int minVal = Integer.MAX_VALUE;
		expandNode(node);
		for(Board child : node.childList) {
			int maxVal = getMaxAB(child,alpha,beta);
			if(maxVal < minVal) {
				minVal = maxVal;
				node.utility = minVal;
			}
			if(minVal <= alpha)
				return minVal;
			if(minVal < beta)
				beta = minVal;
		}
		return minVal;		
	}
}
