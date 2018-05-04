package cs445.a5;
import java.util.Iterator;
import java.util.NoSuchElementException;
import StackAndQueuePackage.*; // Needed by tree iterators

public class TernaryTree<T extends Comparable<? super T>> implements TernaryTreeInterface<T>{

	//DATA MEMBERS
	private TernaryNode<T> root;

	//CONSTRUCTORS
	//initializes an empty tree default constructor
	public TernaryTree(){
		root = null;
	}
	//initializes a tree whose root node contains root data
	public TernaryTree(T rootData){
		root = new TernaryNode<>(rootData);
	}
	//initializes a tree whose root node contains rootData and whose child subtrees are leftTree, middleTree, and rightTree, respectively.
	public TernaryTree(T rootData, TernaryTree<T> leftTree, TernaryTree<T> middleTree, TernaryTree<T> rightTree){
		privateSetTree(rootData, leftTree, middleTree, rightTree);
	}

	//FUNCTIONS FROM TREE INTERFACE
	@Override
	public T getRootData() {
		if (isEmpty()) {
			throw new EmptyTreeException();
		} else {
			return root.getData();
		}
	}
	@Override
	public int getHeight() {
		return root.getHeight();
	}
	@Override
	public int getNumberOfNodes() {
		return root.getNumberOfNodes();
	}
	@Override
	public boolean isEmpty() {
		return (root == null);
	}
	@Override
	public void clear() {
		root = null;
	}

	//ITERATORS
	@Override
	public Iterator<T> getLevelOrderIterator() {
		return new LevelOrderIterator();
	}
	private class LevelOrderIterator implements Iterator<T> {
		private QueueInterface<TernaryNode<T>> nodeQueue;

		public LevelOrderIterator() {
			nodeQueue = new LinkedQueue<>();
			if (root != null) {
				nodeQueue.enqueue(root);
			}
		}

		@Override
		public boolean hasNext() {
			return !nodeQueue.isEmpty();
		}

		@Override
		public T next() {

			TernaryNode<T> nextNode;

			if (hasNext()) {
				nextNode = nodeQueue.dequeue();
				TernaryNode<T> leftChild = nextNode.getLeftChild();
				TernaryNode<T> middleChild = nextNode.getMiddleChild();
				TernaryNode<T> rightChild = nextNode.getRightChild();

				// Add to queue in order of recursive calls
				if (leftChild != null) {
					nodeQueue.enqueue(leftChild);
				}
				if (middleChild != null){
					nodeQueue.enqueue(middleChild);
				}
				if (rightChild != null) {
					nodeQueue.enqueue(rightChild);
				}

			} else {
				throw new NoSuchElementException();
			}
			return nextNode.getData();
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	@Override
	public Iterator<T> getPreorderIterator() {
		return new PreorderIterator();
	}
	private class PreorderIterator implements Iterator<T> {
		private StackInterface<TernaryNode<T>> nodeStack;

		public PreorderIterator() {
			nodeStack = new LinkedStack<>();
			if (root != null) {
				nodeStack.push(root);
			}
		}

		@Override
		public boolean hasNext() {
			return !nodeStack.isEmpty();
		}

		@Override
		public T next() {
			TernaryNode<T> nextNode;

			if (hasNext()) {
				nextNode = nodeStack.pop();
				TernaryNode<T> leftChild = nextNode.getLeftChild();
				TernaryNode<T> middleChild = nextNode.getMiddleChild();
				TernaryNode<T> rightChild = nextNode.getRightChild();

				// Push into stack in reverse order of recursive calls
				if (rightChild != null)
					nodeStack.push(rightChild);

				if (middleChild != null)
					nodeStack.push(middleChild);

				if (leftChild != null)
					nodeStack.push(leftChild);

			} else {
				throw new NoSuchElementException();
			}

			return nextNode.getData();
		}

		public void remove(){
			throw new UnsupportedOperationException();
		}

	}
	@Override
	public Iterator<T> getPostorderIterator() {
		return new PostorderIterator();
	}
	private class PostorderIterator implements Iterator<T> {
		private StackInterface<TernaryNode<T>> nodeStack;
		private TernaryNode<T> currentNode;
		public PostorderIterator() {
			nodeStack = new LinkedStack<>();
			currentNode = root;
		}
		@Override
		public boolean hasNext() {
			return !nodeStack.isEmpty() || (currentNode != null);
		}
		@Override
		public T next() {
			TernaryNode<T> leftChild, nextNode = null;

			// Find leftmost leaf
			while(currentNode != null){
				nodeStack.push(currentNode);
				leftChild = currentNode.getLeftChild();
				currentNode = leftChild;
			}

			if(!nodeStack.isEmpty()){
				nextNode = nodeStack.pop();

				TernaryNode<T> parent = null;
				if(!nodeStack.isEmpty()){
					parent = nodeStack.peek();
					if(parent.getLeftChild() == nextNode){
						currentNode = parent.getMiddleChild();
					} else if(parent.getMiddleChild() == nextNode){
						currentNode = parent.getRightChild();
					} else {
						currentNode = null;
					}
				} else {
					currentNode = null;
				}
			} else {
				throw new NoSuchElementException();
			}

			return nextNode.getData();
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	@Override
	public Iterator<T> getInorderIterator() throws UnsupportedOperationException {
		// Ternary tree does not support in order traversal because after traversing to the middle node, there would be a redundant traversal through the parent node to reach the right node.
		throw new UnsupportedOperationException();
	}



	//SET TREE METHODS
	@Override
	public void setTree(T rootData) {
		root = new TernaryNode<>(rootData);
	}
	@Override
	public void setTree(T rootData, TernaryTreeInterface<T> leftTree, TernaryTreeInterface<T> middleTree, TernaryTreeInterface<T> rightTree) {
		privateSetTree(rootData, (TernaryTree<T>)leftTree, (TernaryTree<T>)middleTree, (TernaryTree<T>)rightTree);
	}
	private void privateSetTree(T rootData, TernaryTreeInterface<T> leftTree, TernaryTreeInterface<T> middleTree, TernaryTreeInterface<T> rightTree){
		root = new TernaryNode<>(rootData);



		if((leftTree != null) && !leftTree.isEmpty())
			root.setLeftChild(new TernaryNode<>(leftTree.getRootData()));

		if((middleTree != null) && !middleTree.isEmpty()){
			if(leftTree != middleTree && rightTree != middleTree){
				root.setMiddleChild(new TernaryNode<>(middleTree.getRootData()));
			} else {
				root.setMiddleChild(new TernaryNode<>(middleTree.getRootData()).copy());
			}
		}

		if((rightTree != null) && !rightTree.isEmpty()){
			if(leftTree != rightTree && rightTree != middleTree){
				root.setRightChild(new TernaryNode<>(rightTree.getRootData()));
			} else {
				root.setRightChild(new TernaryNode<>(rightTree.getRootData()).copy());
			}
		}

		if ((leftTree != null) && (leftTree != this)) {
			leftTree.clear();
		}

		if ((middleTree != null) && (middleTree != this)) {
			middleTree.clear();
		}

		if ((rightTree != null) && (rightTree != this)) {
			rightTree.clear();
		}

	}
}
