package trabalho2;
import java.util.*;	

public class TicTacToe {
	static Scanner input = new Scanner(System.in);

	/**
	 * Get the next position the human player(min player) wants to play in;
	 * @param MINPLAYER
	 * @param game
	 * @return new game board(char);
	 */
	static char[] getPlayerMove(char MINPLAYER,char[] game) {
		int move = 0;
		System.out.print("A tua jogada: ");
		
		move = input.nextInt();
		System.out.println();
		
		if(move >= 0  && move < 9) {
			if(game[move] != '-') {
				System.out.println("Jogada não permitida: Espaço já se encontra preenchido, jogue noutra posição!");
				return getPlayerMove(MINPLAYER,game);
			}
			else {
				game[move] = MINPLAYER;
				return game;
			}
		}
		else { 
			System.out.println(move + ": Não é uma posição válida,volta a tentar.");
			return getPlayerMove(MINPLAYER,game);
		}
	}
	
	/**
	 * Get symbol that humam player wants to play with
	 * @return
	 */
	static private int getPlayerSymbol() {
		System.out.println("______________________________\nEscolha um símbolo para jogar:\n1.X\n2.O\n______________________________");
		int minPlayer = input.nextInt();
		System.out.println(minPlayer);
		if((minPlayer < 1 || minPlayer > 2) || minPlayer == 0) {
			System.out.println("###########################################Não escolheu uma opção válida,volte a tentar###########################################");
			
			return getPlayerSymbol();
		}
		else
			return minPlayer;
	}
	/**
	 * Prints final game messages;
	 * @param utility
	 */
	static private void gameEnded(int utility) {
		System.out.println("############\nFIM DO JOGO");
		
		if(utility == 1)
			System.out.println("__Perdeste__\n############");
		else if(utility == 0)
			System.out.println("__Empataram!__\n############");
	}
	
	/**
	 * Minimax Algorithm
	 * @param MINPLAYER
	 * @param game
	 */
	static private void miniMax(char MINPLAYER,char[] game) {
		char MAXPLAYER;
		
		if(MINPLAYER == 'X')
			MAXPLAYER = 'O';
		else
			MAXPLAYER = 'X';
				
		char[] newGame = getPlayerMove(MINPLAYER, game);

		Board root = new Board(null,newGame,0,true);
		if(root.isTerminal()) { // the last move is from the human player
			root.assignUtilityValue(MAXPLAYER);
			int utility = root.utility;
			root.printState();
			
			gameEnded(utility);
		}
		else { // if node isn't terminal
			Search tree = new Search(root,MAXPLAYER);
			Board terminalNode;
			terminalNode = tree.getMinMaxState();

			System.out.println("Jogada do computador: ");
			terminalNode.printState();
			
			if(terminalNode.isTerminal()) { // the last move is from the computer
				terminalNode.assignUtilityValue(MAXPLAYER);
				int utility = terminalNode.utility;
				
				gameEnded(utility);
			}
			else //if node is not terminal
				miniMax(MINPLAYER,terminalNode.state);
		}
	}
	
	/**
	 * Alpha Beta pruning Algorithm
	 * @param MINPLAYER
	 * @param game
	 */
	static void AlphaBetaPruning(char MINPLAYER,char[] game) {
		char MAXPLAYER;
		
		if(MINPLAYER == 'X')
			MAXPLAYER = 'O';
		else
			MAXPLAYER = 'X';
				
		char[] newGame = getPlayerMove(MINPLAYER, game);

		Board root = new Board(null,newGame,0,true);
		if(root.isTerminal()) { // the last move is from the human player
			root.assignUtilityValue(MAXPLAYER);
			int utility = root.utility;
			root.printState();
			
			gameEnded(utility);
		}
		else { // if node isn't terminal
			Search tree = new Search(root,MAXPLAYER);
			Board terminalNode;
			terminalNode = tree.getMinMaxStateAB();

			System.out.println("Jogada do computador: ");
			terminalNode.printState();
			
			if(terminalNode.isTerminal()) { // the last move is from the computer
				terminalNode.assignUtilityValue(MAXPLAYER);
				int utility = terminalNode.utility;
				
				gameEnded(utility);
			}
			else //if node is not terminal
				AlphaBetaPruning(MINPLAYER,terminalNode.state);
		}
	}
	
	public static void main(String[] Args) {
		int mb = 1024*1024;
		System.out.println("Escolha o algoritmo a usar:\n1.MiniMax\n2.AlphaBeta");
		int algorithm = input.nextInt();
		
		System.out.println("#########################\nBem Vindo ao jogo do galo\n#########################");
		int minPlayer = getPlayerSymbol();
		char MINPLAYER;
		if(minPlayer == 1)
			MINPLAYER = 'X';
		else
			MINPLAYER = 'O';
		System.out.println("Para jogar basta escrever a posição na qual quer jogar,");
		System.out.println("sendo que as posições são como um vector em programação,ou seja: \n0 1 2\n3 4 5\n6 7 8");
		System.out.println("És o 1º a jogar");
		
		char[] rootState = new char[Board.LENGTH];
		for(int i = 0; i < Board.LENGTH; i++)
			rootState[i] = '-';
		
		if(algorithm == 1) {
			Runtime runtime = Runtime.getRuntime();
			miniMax(MINPLAYER,rootState);
			System.out.println("Memória utilizada na 1ª árvore expandida: " + (runtime.totalMemory() - runtime.freeMemory())/mb + "mb");
		}
		else if(algorithm == 2) {
			Runtime runtime = Runtime.getRuntime();
			AlphaBetaPruning(MINPLAYER, rootState);
			System.out.println();
			System.out.println();
			System.out.println("Memória utilizada na 1ª árvore expandida: " + (runtime.totalMemory() - runtime.freeMemory())/mb + "mb");
		}
	}
}
