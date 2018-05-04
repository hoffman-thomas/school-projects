package A1;

public class Set<T> implements SetInterface {

	private T[] contents;
	private int size;

	public Set(int capacity){
		size = 0;
		contents = (T[]) new Object[capacity];
	}

	public Set(){
		size = 0;
		contents = (T[]) new Object[1000];
	}

	@Override
	public int getCurrentSize() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		if(size == 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean add(Object newEntry) throws IllegalArgumentException {
	//REPLACE SETFULLEXCEPTION BEFORE SUBMITTING
		T entry = (T) newEntry;

		if(entry == null)
			throw new IllegalArgumentException();

		boolean isDuplicate = false;

		//Check for duplicate entry
		for(int j = 0; j < contents.length; j++){
			if(entry == contents[j]){
				isDuplicate = true;
				break;
			}
		}

		if(isDuplicate == false){

			//If there is room in the set
			if(size < contents.length){		
				contents[size] = entry;

				//If the set is full
			}else if(size >= contents.length){

				//the following line is for fixed array implementation
				//throw new SetFullException();

				//Copy contents
				T[] contentsCopy = contents;
				contents = (T[]) new Object[size+1];

				//replace the bigger copy with old elements
				for(int i = 0; i < size; i++){
					contents[i] = contentsCopy[i];
				}
				//set the last element to 
				contents[size] = entry;
			}
			size++;
			return true;
		}else{
			return false;
		}

	}

	@Override
	public boolean remove(Object entry) throws IllegalArgumentException {
		
		if(entry == null)
			throw new IllegalArgumentException();
		
		boolean didRemove = false;

		for(int i = 0; i < size; i++){
			if(entry == contents[i]){
				didRemove = true;
				if(size > 1){
					contents[i] = contents[size-1];
					contents[size-1] = null;
					size--;
					break;
				}else if(size == 1){
					contents[size] = null;
					size--;
				}
			}
		}
		return didRemove;
	}

	@Override
	public T remove() {
		
		System.out.println(size);
		
		if(size == 0){
			return null;
		}else if(size >= 1){
			T removed = (T) contents[size-1];
			contents[size-1] = null;
			size--;
			return removed;
		}
		return null;
	}

	@Override
	public void clear() {
		int capacity = contents.length;

		if(size == 0 || contents == null){
			//There isn't anything to clear
		}else if(size >= 1 && contents != null){
			contents = (T[]) new Object[capacity];
		}
	}

	@Override
	public boolean contains(Object entry) throws IllegalArgumentException {
		
		if(entry == null)
			throw new IllegalArgumentException();
		
		if(this.isEmpty() == true)
			return false;
		
		for(int i = 0; i < contents.length; i++){
			if(contents[i] == entry){
				return true;
			}
		}
		return false;
	}

	@Override
	public Object[] toArray() {

		Object copyArray[] = new Object[size];
		
		for(int i = 0; i < size; i++){
			copyArray[i] = contents[i];
		}
		return copyArray;
	}

}
