import java.util.Arrays;

public class Main {

	public static void main(String[] args){
		Word[] wordArray = WordReader.loadWords();

		// Testing Selection Approach
		Word[] mostFrequent1 = Selector.select(wordArray, 500);
		System.out.println("mostFrequent1: " + Arrays.toString(mostFrequent1));

		// Testing Heap-based Approach
		Heap heap = new Heap(wordArray);
		Word[] mostFrequent2 = heap.select(500);
		System.out.println("mostFrequent2: " + Arrays.toString(mostFrequent2));
	}

}
