package cs445.a3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
*	ASSIGNMENT 3 CS0445: DATA STRUCTURES
*
*	This program solves sudoku boards
*	using backtracking recursion to extend
*	and find the next possible value of
*	the last extension.
*
*	PROGRAM BY: THOMAS HOFFMAN (TGH19)
*/

public class Sudoku {

 	//Use ArrayList as an analogy to the ADT Stack
	static ArrayList<Integer> lastExtension = new ArrayList<Integer>();

	//Stores Test Boards to be used in each testing method
	static ArrayList<int[][]> testBoards = new ArrayList<int[][]>();

	public static void main(String[] args) throws InterruptedException{

		if (args[0].equals("-t")) {

			//testBoard that contains numbers out of the possible range of values
			int[][] testBoard0 = {{2,9,6,3,1,8,5,7,4},{5,8,4,9,7,2,6,1,3},{7,1,3,6,4,5,2,8,9},{6,99,99,8,9,7,3,4,1},
			{9,3,1,4,2,6,8,5,7},{4,99,8,5,3,1,9,2,6},{1,6,7,2,5,3,4,9,8},{8,5,9,7,6,4,1,3,2},{3,4,2,1,8,9,7,6,5}};
			testBoards.add(testBoard0);

			//Easy testBoard
			int[][] testBoard1 = {{0,0,2,6,8,0,3,4,7},{6,0,0,0,0,0,5,0,0},{4,0,9,0,0,0,0,8,0},{0,0,0,0,9,3,7,6,0},
			{0,5,0,1,0,6,0,9,0},{0,9,6,8,7,0,0,0,0},{0,8,0,0,0,0,9,0,3},{0,0,7,0,0,0,0,0,4},{3,6,5,0,1,4,2,0,0}};
			testBoards.add(testBoard1);

			//Medium testBoard
			int[][] testBoard2 = {{4,0,0,0,0,2,8,0,0},{0,0,0,0,9,8,0,0,1},{3,0,0,7,0,0,0,2,6},{0,4,9,5,0,3,0,0,8},
			{0,0,0,0,2,0,0,0,0},{6,0,0,8,0,1,9,3,0},{5,7,0,0,0,9,0,0,2},{9,0,0,1,6,0,0,0,0},{0,0,6,2,0,0,0,0,9}};
			testBoards.add(testBoard2);

			//Hard testBoard
			int[][] testBoard3 = {{0,1,0,0,0,0,0,7,0},{0,4,6,0,0,5,2,0,0},{0,8,0,1,0,0,0,0,3},{0,0,0,0,1,3,4,0,6},
			{0,0,0,0,7,0,0,0,0},{1,0,4,9,5,0,0,0,0},{3,0,0,0,0,8,0,4,0},{0,0,8,7,0,0,1,2,0},{0,7,0,0,0,0,0,9,0}};
			testBoards.add(testBoard3);

			//Evil testBoard
			int[][] testBoard4 = {{4,0,0,0,0,0,0,0,0},{0,0,0,6,0,2,3,8,0},{0,0,2,0,0,8,0,5,0},{2,0,0,0,0,0,5,4,0},
			{0,0,5,0,7,0,6,0,0},{0,3,9,0,0,0,0,0,1},{0,6,0,8,0,0,2,0,0},{0,7,4,9,0,6,0,0,0},{0,0,0,0,0,0,0,0,5}};
			testBoards.add(testBoard4);

			//All Zeros testBoard
			int[][] testBoard5 = {{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}};
			testBoards.add(testBoard5);

			//invalid testBoard
			int[][] testBoard6 = {{3,0,0,7,9,4,2,5,2},{2,0,0,5,6,0,1,9,7},{7,9,0,8,2,0,4,0,3},{0,2,0,0,7,8,0,4,1},
			{5,7,0,1,0,6,0,8,2},{1,4,8,3,0,2,9,7,6},{9,0,7,0,8,5,6,0,4},{4,5,2,6,0,7,8,3,9},{0,1,6,4,3,9,7,0,5}};
			testBoards.add(testBoard6);

			//All ones testBoard
			int[][] testBoard7 = {{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}};
			testBoards.add(testBoard7);

			//All twos testBoard
			int[][] testBoard8 = {{2,2,2,2,2,2,2,2,2},{2,2,2,2,2,2,2,2,2},{2,2,2,2,2,2,2,2,2},{2,2,2,2,2,2,2,2,2},
			{2,2,2,2,2,2,2,2,2},{2,2,2,2,2,2,2,2,2},{2,2,2,2,2,2,2,2,2},{2,2,2,2,2,2,2,2,2},{2,2,2,2,2,2,2,2,2}};
			testBoards.add(testBoard8);

			//All threes testBoard
			int[][] testBoard9 = {{3,3,3,3,3,3,3,3,3},{3,3,3,3,3,3,3,3,3},{3,3,3,3,3,3,3,3,3},{3,3,3,3,3,3,3,3,3},
			{3,3,3,3,3,3,3,3,3},{3,3,3,3,3,3,3,3,3},{3,3,3,3,3,3,3,3,3},{3,3,3,3,3,3,3,3,3},{3,3,3,3,3,3,3,3,3}};
			testBoards.add(testBoard9);

			//All fours testBoard
			int[][] testBoard10 = {{4,4,4,4,4,4,4,4,4},{4,4,4,4,4,4,4,4,4},{4,4,4,4,4,4,4,4,4},{4,4,4,4,4,4,4,4,4},
			{4,4,4,4,4,4,4,4,4},{4,4,4,4,4,4,4,4,4},{4,4,4,4,4,4,4,4,4},{4,4,4,4,4,4,4,4,4},{4,4,4,4,4,4,4,4,4}};
			testBoards.add(testBoard10);

			//All fives testBoard
			int[][] testBoard11 = {{5,5,5,5,5,5,5,5,5},{5,5,5,5,5,5,5,5,5},{5,5,5,5,5,5,5,5,5},{5,5,5,5,5,5,5,5,5},
			{5,5,5,5,5,5,5,5,5},{5,5,5,5,5,5,5,5,5},{5,5,5,5,5,5,5,5,5},{5,5,5,5,5,5,5,5,5},{5,5,5,5,5,5,5,5,5}};
			testBoards.add(testBoard11);

			//All sixes testBoard
			int[][] testBoard12 = {{6,6,6,6,6,6,6,6,6},{6,6,6,6,6,6,6,6,6},{6,6,6,6,6,6,6,6,6},{6,6,6,6,6,6,6,6,6},
			{6,6,6,6,6,6,6,6,6},{6,6,6,6,6,6,6,6,6},{6,6,6,6,6,6,6,6,6},{6,6,6,6,6,6,6,6,6},{6,6,6,6,6,6,6,6,6}};
			testBoards.add(testBoard12);

			//All eights testBoard
			int[][] testBoard13 = {{8,8,8,8,8,8,8,8,8},{8,8,8,8,8,8,8,8,8},{8,8,8,8,8,8,8,8,8},{8,8,8,8,8,8,8,8,8},
			{8,8,8,8,8,8,8,8,8},{8,8,8,8,8,8,8,8,8},{8,8,8,8,8,8,8,8,8},{8,8,8,8,8,8,8,8,8},{8,8,8,8,8,8,8,8,8}};
			testBoards.add(testBoard13);

			//All nines testBoard
			int[][] testBoard14 = {{9,9,9,9,9,9,9,9,9},{9,9,9,9,9,9,9,9,9},{9,9,9,9,9,9,9,9,9},{9,9,9,9,9,9,9,9,9},
			{9,9,9,9,9,9,9,9,9},{9,9,9,9,9,9,9,9,9},{9,9,9,9,9,9,9,9,9},{9,9,9,9,9,9,9,9,9},{9,9,9,9,9,9,9,9,9}};
			testBoards.add(testBoard14);

			//All sevens testBoard
			int[][] testBoard15 = {{7,7,7,7,7,7,7,7,7},{7,7,7,7,7,7,7,7,7},{7,7,7,7,7,7,7,7,7},{7,7,7,7,7,7,7,7,7},
			{7,7,7,7,7,7,7,7,7},{7,7,7,7,7,7,7,7,7},{7,7,7,7,7,7,7,7,7},{7,7,7,7,7,7,7,7,7},{7,7,7,7,7,7,7,7,7}};
			testBoards.add(testBoard15);

			//All numbers out of range testBoard
			int[][] testBoard16 = {{10,10,10,10,10,10,10,10,10},{10,10,10,10,10,10,10,10,10},{10,10,10,10,10,10,10,10,10},
			{10,10,10,10,10,10,10,10,10},{10,10,10,10,10,10,10,10,10},{10,10,10,10,10,10,10,10,10},{10,10,10,10,10,10,10,10,10},
			{10,10,10,10,10,10,10,10,10},{10,10,10,10,10,10,10,10,10}};
			testBoards.add(testBoard16);

			testIsFullSolution();
			testReject();
			testExtend();
			testNext();

			System.out.println("----------TESTING CONCLUDED----------");
		} else{
			int[][] board = readBoard(args[0]);
			System.out.println("\nOriginal Board:");
			printBoard(board);
			System.out.println("\nSolution:");
			printBoard(solve(board));
		}
	}

	/* isFullSolution, a method that accepts a partial solution
	 * and returns true if it is a complete, valid solution.*/
	static boolean isFullSolution(int[][] board){

		//COMPLETENESS: checks to see if the board is complete (meaning there are no zeros)
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(board[i][j] <= 0 || board[i][j] > 9)
					return false;
			}
		}

		//VALIDITY: will reject the board if it is incorrect
		return !(reject(board));
	}

	/* reject, a method that accepts a partial solution and returns
	 * true if it should be rejected because it can never be extended
	 * into a complete solution.*/
	static boolean reject(int[][] board) {

		//reject if there are duplicate numbers in a box, row, or column
		for(int row = 0; row < 9; row++){

			//iterate through columns
			for(int col = 0; col < 9; col++){
				int element = board[row][col];

				if(element > 9 || element < 0)
					return true;

				//Compare element to others in the same row if it is non zero
				if(element != 0){
					for(int compare = 0; compare < 9; compare++){
						if(board[row][compare] >= 1 && board[row][compare] <= 9 ){ //VALID RANGE FOR THE VALUE WE COMPARE ELEMENT TO
							if(element == board[row][compare] && compare != col){
								return true;
							}
						}
						if(board[compare][col] <= 9 && board[compare][col] >= 1){	//VALID RANGE FOR THE VALUE WE COMPARE ELEMENT TO
							if(element == board[compare][col] && compare != row){
								return true;
							}
						}
					}
				}
			}
		}

		//Check Boxes for duplicates
		for(int startRow = 0; startRow < 9; startRow += 3){
			for(int startCol = 0; startCol < 9; startCol += 3){
				ArrayList<Integer> box = new ArrayList<Integer>();
				for(int boxRow = startRow; boxRow < startRow + 3; boxRow++){
					for(int boxCol = startCol; boxCol < startCol + 3; boxCol++){
						box.add(board[boxRow][boxCol]);
					}
				}
				for(int i = 0; i < 9; i++){
					int compare = box.get(i);
					for(int j = i; j < 9; j++){
						if(compare == box.get(j) && i != j && box.get(j) >=1 && box.get(j) <= 9){
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/* extend, a method that accepts a partial solution and returns another
	 * partial solution that includes one additional choice added on. This
	 * method will return null if there are no more choices to add to the
	 * solution. It should also be sure to make a new partial solution, rather
	 * than modifying the original object (recall that the runtime stack can only
	 * contain references, not objects themselves!).*/
	static int[][] extend(int[][] board) {
		int[][] partial = board;
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(partial[i][j] == 0){
					for(int k = 1; k < 10; k++){

						//try this value
						partial[i][j] = k;

						//if the partial solution generated is valid.
						if(reject(partial) == false){

							//the last extension of the board is saved
							lastExtension.add(i);
							lastExtension.add(j);

							//return the first possible assignment of that element
							return partial;
						}
						partial[i][j] = 0;
					}
					return null;
				}
			}
		}
		return null;
	}

	/* next, a method that accepts a partial solution and returns another
	 * partial solution in which the most recent choice to be added has
	 * been changed to its next option. This method will return null if
	 * there are no more options for the most recent choice that was made.
	 */
	static int[][] next(int[][] board) {
		int[][] partial = board;

		//The index of the last extended element is the one to be changed
		int i = lastExtension.get(lastExtension.size() - 2);
		int j = lastExtension.get(lastExtension.size() - 1);

		//preserve the previous value of the lastValue so that it does not go to the same value
		int lastValue = partial[i][j];
		partial[i][j] = 0;

		//check for numbers after the previously extended value
		for(int k = lastValue + 1; k < 10; k++){
			partial[i][j] = k;

			//if the partial solution generated is valid.
			if(reject(partial) == false && 0 != board[i][j]){

				//return the assignment of that element
				return partial;
			}
			partial[i][j] = 0;
		}

		//if we cant find a next solution, we need to go back to try a new solution. decrement the last indicies
		//in a sense, this "pops" the last two elements of the arraylist stack
		lastExtension.remove(lastExtension.size() - 2);
		lastExtension.remove(lastExtension.size() - 1);
		return null;
	}

	/* testIsFullSolution,a method that generates partial solutions and
	 * ensures that the isFullSolution method correctly determines whether
	 * each is a complete solution.`
	 */
	static void testIsFullSolution() throws InterruptedException{
		System.out.println("\n----------TESTING IS FULL SOLUTION METHOD----------");
		Thread.sleep(300);
		for(int i = 0; i < testBoards.size(); i++){

			System.out.println("\nTest Board ["+i+"]: ");

			printBoard(testBoards.get(i));

			if(isFullSolution(testBoards.get(i))){
				System.out.println("Board IS a valid solution");
			}else{
				System.out.println("Board IS NOT a valid solution");
			}
			Thread.sleep(300);
		}
	}

	/* testReject, a method that generates partial solutions and ensures
	 * that the reject method correctly determines whether each should
	 * be rejected.
	 */
	static void testReject()throws InterruptedException{
		System.out.println("\n----------TESTING REJECT METHOD----------");
		Thread.sleep(300);
		for(int i = 0; i < testBoards.size(); i++){
			System.out.println("\nTest Board ["+i+"]: ");
			printBoard(testBoards.get(i));

			if(reject(testBoards.get(i))){
				System.out.println("REJECT! - Board CANNOT be Extended into a valid solution ");
			}else{
				System.out.println("DO NOT REJECT! - Board CAN be extended into valid solution");
			}
			Thread.sleep(300);
		}
	}

	/* testExtend, a method that generates partial solutions and ensures
	 * that the extend method correctly extends each with the correct
	 * next choice.
	 */
	static void testExtend()throws InterruptedException{
		System.out.println("\n----------TESTING EXTEND METHOD----------");
		Thread.sleep(300);
		for(int i = 0; i < testBoards.size(); i++){
			System.out.println("\nTest Board ["+i+"]: ");
			printBoard(testBoards.get(i));

			int[][] extendedBoard = extend(testBoards.get(i));
			if(extendedBoard == null){
				System.out.println("COULD NOT EXTEND - The Board has NO more choices to extend upon");
			}else{
				System.out.println("\nBoard ["+i+"] was successfully Extended into the following:");
				printBoard(extendedBoard);
				Thread.sleep(300);
			}
			Thread.sleep(300);
		}
	}

	/* testNext, a method that generates partial solutions and ensures
	 * that the, in each, next method correctly changes the most recent
	 * choice that was added to its next option.
	 */
	static void testNext()throws InterruptedException{
		System.out.println("\n----------TESTING NEXT METHOD---------");
		Thread.sleep(300);
		for(int i = 0; i < testBoards.size(); i++){

			//Generates a random index to decide which the last index extended was
			Random rn = new Random();

			int j = rn.nextInt(8 - 0 + 1) + 0;
			int k = rn.nextInt(8 - 0 + 1) + 0;

			lastExtension.add(j);
			lastExtension.add(k);

			System.out.println("\nTest Board ["+i+"]: ");
			System.out.println("The last element to be extended was: ["+j+"]["+k+"]");
			printBoard(testBoards.get(i));
			Thread.sleep(300);

			int[][] nextBoard = next(testBoards.get(i));

			if(nextBoard == null){
				System.out.println("The next method could not find any new value for element ["+j+"]["+k+"]");
			}else{
				System.out.println("\nElement ["+j+"]["+k+"]\'s next value was found in the following board");
				printBoard(nextBoard);
				Thread.sleep(300);
			}
		}
	}

	static void printBoard(int[][] board) {
		if (board == null) {
			System.out.println("Sorry, There are no possible solutions to this board");
			return;
		}
		for (int i = 0; i < 9; i++) {
			if (i == 3 || i == 6) {
				System.out.println("----+-----+----");
			}
			for (int j = 0; j < 9; j++) {
				if (j == 2 || j == 5) {
					System.out.print(board[i][j] + " | ");
				} else {
					System.out.print(board[i][j]);
				}
			}
			System.out.print("\n");
		}
	}

	static int[][] readBoard(String filename) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
		} catch (IOException e) {
			return null;
		}
		int[][] board = new int[9][9];
		int val = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				try {
					val = Integer.parseInt(Character.toString(lines.get(i).charAt(j)));
				} catch (Exception e) {
					val = 0;
				}
				board[i][j] = val;
			}
		}
		return board;
	}

	static int[][] solve(int[][] board) {
		if (reject(board)) return null;
		if (isFullSolution(board)) return board;
		int[][] attempt = extend(board);
		while (attempt != null) {
			int[][] solution = solve(attempt);
			if (solution != null) return solution;
			attempt = next(attempt);
		}
		return null;
	}
}
