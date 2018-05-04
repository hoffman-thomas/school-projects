
public class ChainingHashMap{
	Node[] array;
	int size;

	public ChainingHashMap(int size){
		this.size = size;
		array = new Node[size];
	}

	public Integer get(Word key) {
		// Problem #1A
		// Fill in this method to get the value corresponding
		// with the key. Note: if the key is not found, then
		// return null.
		int index = key.hashCode()%this.size;

		Node node = array[index];

		while(node != null){

			if(node.word.value.equals(key.value))
				return node.frequency;
				node = node.next;
		}
		return null;
	}

	public void put(Word key, Integer value) {
		// Problem #1B
		// Fill in this method to insert a new key-value pair into
		// the map or update the existing key-value pair if the
		// key is already in the map.
		int index = key.hashCode() % this.size;

		if(array[index] == null){
			array[index] = new Node(key, value, null);
			return;

		}else if(array[index] != null){
			Node temp = array[index];
			array[index] = new Node(key, value, temp);
			return;
		}
	}

	public Integer remove(Word key) {
		// Problem #1C
		// Fill in this method to remove a key-value pair from the
		// map and return the corresponding value. If the key is not
		// found, then return null.
		int index = key.hashCode() % this.size;

		Node node = array[index];

		while(node != null){

			if(node.word.value.equals(key.value)){

					if(node.next != null){
						int freq = node.frequency;
						node.frequency = node.next.frequency;
						node.word = node.next.word;
						node.next = node.next.next;
						return freq;

					}else{
						int freq = node.frequency;
						node = null;
						return freq;
					}
			}
			node = node.next;
		}
		return null;
	}

	// This method returns the total size of the underlying array.
	// In other words, it returns the total number of linkedlists.
	public int getSize(){
		return size;
	}

	// This method counts how many keys are stored at the given array index.
	// In other words, it computes the size of the linkedlist at the given index.
	public int countCollisions(int index){
		if(index < 0 || index >= size) return -1;

		int count = 0;
		Node temp = array[index];
		while(temp != null){
			temp = temp.next;
			count++;
		}
		return count;
	}
}
