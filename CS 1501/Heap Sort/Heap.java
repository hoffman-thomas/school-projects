import java.util.*;

public class Heap {

	public Word[] data = null;

	public Heap(Word[] array){
		data = buildHeap(array);
	}

	public Word[] buildHeap(Word[] array){
		// Problem #2
		// Fill in this method with an O(n) time algorithm
		// that builds an n element complete binary heap.
		// Note: You are allowed to add and modify fields
	  // and helper methods.

		for(int i = ((array.length-3)/2); i >= 0; i--)
			array = sortHeap(array, i);

		return array;
	}

	//accepts a array and
	public Word[] sortHeap(Word[] array, int index){

		Word currentNode = array[index]; 		//current node to compare with
		int lcIndex = leftChild(index); 		//index of left child
		int rcIndex = rightChild(index); 		//index of right child
		int heapSize = array.length; 				//number of nodes
		int maxIndex = 0; 									//index of the max value
		Word max = null; 										//maximum of the heap

		//if left child is bigger than parent
		if(heapSize >= lcIndex && array[lcIndex].compareTo(currentNode) >= 1){
			max = array[lcIndex];
			maxIndex = lcIndex;

		//parent is larger than the left child
		}else{
			max = currentNode;
			maxIndex = index;
		}

		//if right child is bigger than parent
		if(heapSize >= rcIndex && array[rcIndex].compareTo(max) >= 1){
			max = array[rcIndex];
			maxIndex = rcIndex;
		}

		//if the max is the parent, return the array, it satisfies the heap condition
		if(maxIndex == index){
			return array;

		//determine which child to swap with if the tree does not satisfy heap condition
		}else{
			Word temp = array[index];
			array[index] = max;

			//swap left child with parent
			if(maxIndex == lcIndex){
				array[lcIndex] = temp;

			//swap right child with parent
			}else if(maxIndex == rcIndex){
				array[rcIndex] = temp;
			}

			//recursively check to see if the new array at the shifted position satisfies the heap condition
			return sortHeap(array, maxIndex);
		}
	}

	//accepts the index position of a child and returns the value for its parent
	public int parent(int index){
		return (index-1)/2;
	}

	//accepts the index position of a parent and returns the value for its left child
	public int leftChild(int index){
		return 2*index + 1;
	}

	//accepts the index position of a parent and returns the value for its left child
	public int rightChild(int index){
		return 2*index + 2;
	}

	public Word removeMax(){
		// Problem #3
		// Fill in this method with an O(log(n)) time algorithm
		// that removes the root element, restores the heap
		// structure, and finally returns the removed root element.

		//the max value is at the root
		Word max = data[0];

		//swap root with the last node in bin heap
		data[0] = data[data.length-1];

		//make a copy of data to remove the end node
		data = Arrays.copyOf(data, data.length-1);

		//sort this heap at array index 0 in order to sink the node to its proper position
		data = sortHeap(data, 0);

		//return the max value
		return max;

	}

	public Word[] select(int k){
		Word[] result = new Word[k];
		for(int i = 0; i < k; i++){
			result[i] = this.removeMax();
		}
		return result;
	}
}
