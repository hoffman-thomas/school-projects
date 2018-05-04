
public class Selector {

	private static void swap(Word[] array, int i, int j){
		if(i == j) return;

		Word temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	public static Word[] select(Word[] array, int k){
		// Problem #1
		// Fill in this method with an O(n*k) time algorithm
		// that returns the k largest elements of array in
		// order from largest to smallest.
		// Note: This should return an array with k elements.

		Word[] result = new Word[k];

		for(int i = 0; i < array.length; i++){
			for(int j = 0; j < result.length; j++){
				if(result[j] == null){
					result[j] = array[i];
					break;

				}else if(array[i].compareTo(result[j]) == 1){
					Word[] temp = result.clone();
					result[j] = array[i];

					for(int l = j; l < result.length - 1; l++){
						result[l+1] = temp[l];
					}
					break;
				}
			}
		}
		return result;
	}
}
