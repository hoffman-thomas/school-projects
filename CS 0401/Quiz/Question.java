
import java.util.*;
import java.io.*;

public class Question{

  //initializes the variables for use in object
  private String question;
  private ArrayList<String> answers;
  private int numAnswer;
  private int correctAnswer;
  private int attempts;
  private int successes;
  private int userAnswer;

  //construct the obj
  public Question( String _question, ArrayList<String> _answers, int _correctAnswer, int _attempts, int _successes, int _numAnswer){

    question = _question;
    answers = _answers;
    correctAnswer = _correctAnswer;
    attempts = _attempts;
    successes = _successes;
    numAnswer = _numAnswer;
  }

  // Getters
  public String getQuestion(){
    return question;
  }

  public int getNumAnswer(){
    return numAnswer;
  }

  public ArrayList<String> getAnswer(){
    return answers;
  }

  public int getCorrectAnswer(){
    return correctAnswer;
  }

  public int getAttempts(){
    return attempts;
  }

  public int getSuccesses(){
    return successes;
  }

  //Calculates PCT correct each time accesed to provide current data
  public double getPctCorrect(){
    double pctCorrect = (double) successes/attempts;
    pctCorrect = pctCorrect *100;
    return pctCorrect;
  }

  public int getUserAnswer(){
    return userAnswer;
  }

  //Setters (mutators)
  public void setUserAnswer(int _userAnswer){
    userAnswer = _userAnswer;
  }
  public void incSuccesses(){
    successes = successes + 1;
  }
  public void incAttempts(){
    attempts = attempts + 1;
  }

}
