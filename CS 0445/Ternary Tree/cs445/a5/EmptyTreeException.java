package cs445.a5;

/**
 * A class of runtime exceptions thrown by methods to indicate that a tree is
 * empty.
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @author William C. Garrison
 * @version 4.5
 */
public class EmptyTreeException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmptyTreeException() {
        this(null);
    }

    public EmptyTreeException(String message) {
        super(message);
    }
}

