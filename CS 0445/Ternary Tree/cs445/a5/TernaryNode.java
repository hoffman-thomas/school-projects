package cs445.a5;

public class TernaryNode<T> {
	private T data;
	private TernaryNode<T> leftChild;   // Reference to Left Child
	private TernaryNode<T> middleChild; // Reference to Middle Child
	private TernaryNode<T> rightChild;  // Reference to Right Child

	TernaryNode(){
		this(null); // Call next constructor
	}
	TernaryNode(T dataPortion){
		this(dataPortion, null, null, null); // Call next constructor
	}
	TernaryNode(T dataPortion, TernaryNode<T> newLeftChild, TernaryNode<T> newRightChild, TernaryNode<T> newMiddleChild){
		data = dataPortion;
		leftChild = newLeftChild;
		middleChild = newMiddleChild;
		rightChild = newRightChild;

	}

	public int getNumberOfNodes() {
		int leftNumber = 0;
		int middleNumber = 0;
		int rightNumber = 0;

		if(leftChild != null){
			leftNumber = leftChild.getNumberOfNodes();
		}

		if(middleChild != null){
			middleNumber = middleChild.getNumberOfNodes();
		}

		if(rightChild != null){
			rightNumber = rightChild.getNumberOfNodes();
		}

		return 1 + leftNumber + middleNumber + rightNumber;
	}
	public int getHeight() {
		return getHeight(this);
	}
	private int getHeight(TernaryNode<T> node){
		int height = 0;

		if(node != null){
			height = 1 + Math.max(getHeight(node.getLeftChild()),				 
					Math.max(getHeight(node.getMiddleChild()), 
							getHeight(node.getRightChild()))
					);
		}
		return height;
	}
	public TernaryNode<T> getRightChild() {
		return rightChild;
	}
	public TernaryNode<T> getMiddleChild() {
		return middleChild;
	}
	public TernaryNode<T> getLeftChild() {
		return leftChild;
	}
	public T getData() {
		return data;
	}

	public void setData(T newData){
		data = newData;
	}
	public void setLeftChild(TernaryNode<T> newLeftChild){
		leftChild = newLeftChild;
	}
	public void setMiddleChild(TernaryNode<T> newMiddleChild){
		middleChild = newMiddleChild;
	}
	public void setRightChild(TernaryNode<T> newRightChild){
		rightChild = newRightChild;
	}

	public boolean hasLeftChild(TernaryNode<T> newLeftChild){
		return leftChild != null;
	}
	public boolean hasMiddleChild(TernaryNode<T> newMiddleChild){
		return middleChild != null;
	}
	public boolean hasRightChild(TernaryNode<T> newRightChild){
		return rightChild != null;
	}

	public boolean isLeaf() {
		return (leftChild == null) && (rightChild == null) && (middleChild == null);
	}
    public TernaryNode<T> copy() {
    	TernaryNode<T> newRoot = new TernaryNode<>(data);

        if (leftChild != null)
            newRoot.setLeftChild(leftChild.copy());
        
        if (middleChild != null) 
            newRoot.setMiddleChild(middleChild.copy());
        
        if (rightChild != null) 
            newRoot.setRightChild(rightChild.copy());

        return newRoot;
    }	
}
