

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.EmptyStackException;

/**
 * This class uses two stacks to evaluate an infix arithmetic expression from an
 * InputStream.
 */
public class InfixExpressionEvaluator {
	
	// Tokenizer to break up our input into tokens
	StreamTokenizer tokenizer;

	//Array of strings to keep track of the expression after each token is processed
	ArrayList<String> expression = new ArrayList<String>();;
	
	//Final value for result of expression
	double result = 0;

	// Stacks for operators (for converting to postfix) and operands (for evaluating)
	StackInterface<Character> operators;
	StackInterface<Double> operands;

	//Stack of brackets used for checking their syntax
	StackInterface<Character> brackets;


	/**
	 * Initializes the solver to read an infix expression from input.
	 */
	public InfixExpressionEvaluator(InputStream input) {
		
		// Initialize the tokenizer to read from the given InputStream
		tokenizer = new StreamTokenizer(new BufferedReader(
				new InputStreamReader(input)));

		// Declare that - and / are regular characters (ignore their regex meaning)
		tokenizer.ordinaryChar('-');
		tokenizer.ordinaryChar('/');

		// Allow the tokenizer to recognize end-of-line
		tokenizer.eolIsSignificant(true);

		// Initialize the stacks
		operators = new ArrayStack<Character>();
		operands = new ArrayStack<Double>();
		brackets = new ArrayStack<Character>();
	}
	
	/**
	 * Takes in values for the lasToken processed and the current token being processed and ensures no syntax error
	 * @param lastToken
	 * @param thisToken
	 */
	public void checkExpression(String lastToken, String thisToken) {

		if(lastToken.equals("") && isOperator(thisToken)) //can not start expression with an operator
			throw new ExpressionError("Expression may not start with an Operator");

		if((lastToken.equals("(") && isOperator(thisToken))) //can not follow an open bracket by operator
			throw new ExpressionError("Can not have: ("+thisToken);
		
		if((lastToken.equals("[") && isOperator(thisToken))) //can not follow an open bracket by operator
			throw new ExpressionError("Can not have: ["+thisToken);

		if(isOperand(lastToken) && isOperand(thisToken)) //cant have 2 operands in a row
			throw new ExpressionError("Can not have two operands in a row");

		if(isOperator(lastToken) && isOperator(thisToken)) //cant have 2 operators in a row
			throw new ExpressionError("Can not have two operators in a row");
		
		if(isOperator(lastToken) && (thisToken.equals(")") || thisToken.equals("]")))
			throw new ExpressionError("Can not open bracket after an operator");

	}

	/**
	 * Keeps track of brackets in the expression using a stack and ensuring that they nest properly
	 * @param bracket
	 */
	public void checkBrackets(char bracket){

		if(!brackets.isEmpty()){ //cant close one type of brackets with the other
			if(brackets.peek() == '(' && bracket == ']')
				throw new ExpressionError("Bracket Mismatch");
			if(brackets.peek() == '[' && bracket == ')')
				throw new ExpressionError("Bracket Mismatch");


		}else if(brackets.isEmpty()){ //Cant start an expression with a closed brakcet
			if(bracket == ']' || bracket == ')')
				throw new ExpressionError("Bracket Mismatch");
		}
		brackets.push(bracket);
	}

	/**
	 * Checks to see if the string passed to it is an operand (as defined by the program an operand may not be +-/*()[]or ^
	 * @param op
	 * Returns true if the string is not an operand
	 * @return
	 */
	public boolean isOperand(String op){

		//If the string is not equal to an operand, return true
		if(!(op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("^") || op.equals("(") || op.equals(")") || op.equals("]") || op.equals("[") )){
			return true;			
		}
		return false;
	}

	/**
	 * Checks to see if the string passed into it is an operator. In order to be an operator, the string must equal +-*divide or ^
	 * @param op
	 * returns true if it is an operator, returns false if it is not
	 * @return
	 */
	public boolean isOperator(String op){

		//check to see if the operator is one of these characers
		if(op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("^")){
			return true;			
		}
		return false;
	}


	/**
	 * A type of runtime exception thrown when the given expression is found to
	 * be invalid
	 */
	class ExpressionError extends RuntimeException {
		ExpressionError(String msg) {
			super(msg);
		}
	}

	/**
	 * A type of runtime exception given when dividing by zero
	 * @author treyhoffman
	 *
	 */
	class InvalidOperationException extends RuntimeException {
		InvalidOperationException(String string){
			//print string passed to the exception
			System.out.print(string);
		}
	}

	/**
	 * Creates an InfixExpressionEvaluator object to read from System.in, then
	 * evaluates its input and prints the result.
	 * @throws InvalidOperationException 
	 * @throws ExpressionError 
	 */
	public static void main(String[] args) throws ExpressionError, InvalidOperationException {
		InfixExpressionEvaluator solver = new InfixExpressionEvaluator(System.in);
		Double value = solver.evaluate();
		if (value != null) {
			System.out.println(value);
		}
	}

	/**
	 * Evaluates the expression parsed by the tokenizer and returns the
	 * resulting value.
	 * @throws InvalidOperationException 
	 */
	public Double evaluate() throws ExpressionError, InvalidOperationException {

		// Get the first token. If an IO exception occurs, replace it with a runtime exception, causing an immediate crash. 
		try {
			tokenizer.nextToken();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		//Keep track of the last token to check for expression errors
		String lastToken = "";

		// Continue processing tokens until we find end-of-line
		while (tokenizer.ttype != StreamTokenizer.TT_EOL) {

			// Consider possible token types
			switch (tokenizer.ttype) {
			case StreamTokenizer.TT_NUMBER:
				// If the token is a number, process it as a double-valued operand
				lastToken = Double.toString((double)tokenizer.nval);  //the last token is set to the current token
				processOperand((double)tokenizer.nval);//Token is processed
				break;

			case '+':
			case '-':
			case '*':
			case '/':
			case '^':
				// If the token is any of the above characters, process it is an operator
				checkExpression(lastToken, Character.toString((char)tokenizer.ttype)); //checks the expression with the last token
				lastToken = Character.toString((char)tokenizer.ttype); //if no error occurs, the last token is set to the current token
				processOperator((char)tokenizer.ttype); //Token is processed
				break;
			case '(':
			case '[':
				// If the token is open bracket, process it as such. Forms of bracket are interchangeable but must nest properly.
				checkExpression(lastToken, Character.toString((char)tokenizer.ttype));//checks the expression with the last token
				lastToken = Character.toString((char)tokenizer.ttype);//if no error occurs, the last token is set to the current token
				checkBrackets((char)tokenizer.ttype); //ensure that the brackets have no issues
				processOpenBracket((char)tokenizer.ttype); //process bracket
				break;
			case ')':
			case ']':
				// If the token is close bracket, process it as such. Forms of bracket are interchangeable but must nest properly.
				checkExpression(lastToken, Character.toString((char)tokenizer.ttype));//checks the expression with the last token
				lastToken = Character.toString((char)tokenizer.ttype);//if no error occurs, the last token is set to the current token
				checkBrackets((char)tokenizer.ttype); //ensure that the brackets have no issues
				processCloseBracket((char)tokenizer.ttype); //process bracket
				break;
			case StreamTokenizer.TT_WORD:
				// If the token is a "word", throw an expression error
				throw new ExpressionError("Unrecognized token: " + tokenizer.sval);
			default:
				// If the token is any other type or value, throw an expression error
				throw new ExpressionError("Unrecognized token: " + String.valueOf((char)tokenizer.ttype));
			}

			// Read the next token, again converting any potential IO exception
			try {
				tokenizer.nextToken();
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		}

		// Almost done now, but we may have to process remaining operators in the operators stack
		processRemainingOperators();

		// Return the result of the evaluations. Only set if expression is valid
		return result;
	}

	/**
	 * Processes an operand.
	 */
	void processOperand(double operand) {
		operands.push(operand);
	}

	
	/**
	 * Processes an operator.
	 * @throws InvalidOperationException 
	 */
	void processOperator(char operator) throws InvalidOperationException {

		int opStackPrec = 0, opPrec = 1;

		//gets precedence of the stack and the operator being passed in 
		if(operators.isEmpty() == false){
			opStackPrec = getOperatorPrecedence(operators.peek());
			opPrec = getOperatorPrecedence(operator);

			if(opStackPrec >= opPrec){ //pop the operator and two operands, then push their computed value onto the operand stack
				operands.push((eval(operators.pop(), operands.pop(), operands.pop())));
				operators.push(operator);
			}else if (opStackPrec < opPrec){
				operators.push(operator);
			}

		}else if(operators.isEmpty() == true){
			operators.push(operator);
		}
	}

	
	/**
	 * Evaluates an expression of operand 2 and 1 b using operation Operator. returns the value of that operation
	 * @param operator
	 * @param operand1
	 * @param operand2
	 * @return
	 * @throws InvalidOperationException
	 */
	double eval(Character operator, Double operand1, Double operand2) throws InvalidOperationException {
		switch(operator){
		case '+':
			return (operand2 + operand1); //Addition
		case '-': 
			return (operand2 - operand1); //Subtraction
		case '*': 
			return (operand2 * operand1); //Multiplication
		case '/':
			if(operand1 == 0){ //DIV0 error
				throw new InvalidOperationException("Can not divide by zero");
			}
			return (operand2 / operand1);
		case '^':
			return Math.pow(operand2,operand1);
		}
		return 0.0;
	}
	
	/**
	 * takes in the operator and returns the corresponding precedence
	 * @param operator
	 * @return
	 */

	int getOperatorPrecedence(Character operator) {

		int operatorPrecedence = 0;

		if(operator == '+' || operator == '-')
			operatorPrecedence = 1;

		else if(operator == '*' || operator == '/')
			operatorPrecedence = 2;

		else if(operator == '^')
			operatorPrecedence = 3;

		return operatorPrecedence;
	}

	
	/**
	 * Processes an open bracket.
	 */
	void processOpenBracket(char openBracket) {
		//always push an open bracket
		operators.push(openBracket);
	}


	/**
	 * Processes a close bracket.
	 * @throws InvalidOperationException 
	 */
	void processCloseBracket(char closeBracket) throws InvalidOperationException {
		
		if(closeBracket == ')'){ 
			while(operators.peek() != '('){ //checks to make sure it doesnt hit the beginning of a new expression
				operands.push((eval(operators.pop(), operands.pop(), operands.pop()))); //pop the values and compute operation when close bracket is encountered
			}
		}else if(closeBracket == ']'){ //same operation for brackets instead of parenthesis
			while(operators.peek() != '['){
				operands.push((eval(operators.pop(), operands.pop(), operands.pop())));
			}
		}
		operators.pop(); //finally, pop the last open bracket
	}
	

	/**
	 * Processes any remaining operators leftover on the operators stack
	 * @throws InvalidOperationException 
	 */
	void processRemainingOperators() throws InvalidOperationException {
		//initialize variables to pop later on and the operation by which they are evaluated
		double op1 = 0.0;
		double op2 = 0.0;
		Character operation = ' ';

		//while operators has something in it
		while(!operators.isEmpty() ){
			if(operators.peek() == '(' || operators.peek() == '[') //if the top of the stack has an open bracket, there was an expression error
				throw new ExpressionError("Brackets must close");
			
			try{ 
				operation = operators.pop();
				op1 = operands.pop();
				op2 = operands.pop();	//if either of these pops gives an empty stack expression, then there was a missing operand to compute the operation
				operands.push(eval(operation,op1,op2)); 
			}catch(EmptyStackException e){
				throw new ExpressionError("Missing operand to compute operation:"+op1+""+operation+"???");
			}
		}
		
		result = operands.pop();

		/*
		try{
			operands.pop();
		}catch(EmptyStackException e){
			return;
		}
		throw new ExpressionError("Can not place multiple operands in sequence");
		 */
	}
}

