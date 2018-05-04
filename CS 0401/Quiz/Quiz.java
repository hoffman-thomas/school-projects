//Thomas Hoffman
//Assignment 3 - CS 0401
//March 29, 2016

import java.util.*;
import java.io.*;

public class Quiz {

    public static void main(String[] args) throws IOException {

      //Creates Array to hold questions on quiz
      ArrayList<Question> questions = new ArrayList<Question>();
      System.out.printf("\n-----------QuizMachine-----------\n");

      //Prompts user for the file name
      Scanner keyboard = new Scanner(System.in);
      System.out.printf("\nEnter the quiz file: ");
      String fileName = keyboard.nextLine();

      // creates new file object
      File userFile = new File(fileName);
      boolean exists = userFile.exists();

      //When it doesnt exist, it prompts user to enter a correct quiz file
      while (exists == false) {

          //Giver user error that their file is not found
          System.out.printf("Enter the quiz file: ");
          String name = keyboard.nextLine();
          fileName = name;

          //checks each iteration to see if the newly entered file exists
          userFile = new File(fileName);
          exists = userFile.exists();
      }

      //Open File
      File quiz = new File(fileName);

      //Scan From Quiz File
      Scanner readQuiz = new Scanner(quiz);

      String questionRead = readQuiz.nextLine();
      int numAnswer = readQuiz.nextInt();

      // makes  array list strings which include the answers to each question
      ArrayList<String> answers = new ArrayList<String>();
      for(int i = 0; i <= numAnswer; i++){
        answers.add(readQuiz.nextLine());
      }

      int correct = readQuiz.nextInt();
      int attempt = readQuiz.nextInt();
      int success = readQuiz.nextInt();

      // create new question object and as it to questions array list
      Question question = new Question(questionRead, answers, correct, attempt, success, numAnswer);
      questions.add(question);

      // read the file until  text stops
      while(readQuiz.hasNext()){

        // burn the  line in order to read the proper line
        readQuiz.nextLine();

        // stores question as variable questionread
        questionRead = readQuiz.nextLine();

        // stores the number of answers as variable num answer
        numAnswer = readQuiz.nextInt();

        answers = new ArrayList<String>();
        for(int i = 0; i <= numAnswer; i++){
          answers.add(readQuiz.nextLine());
        }

        //  reads the correct answer, Number of attempts, and successes
        correct = readQuiz.nextInt();
        attempt = readQuiz.nextInt();
        success = readQuiz.nextInt();

        // makes a new question object and then adds it to the questions array list
        question = new Question(questionRead, answers, correct, attempt, success, numAnswer);
        questions.add(question);

      }

      // closest file reader
      readQuiz.close();

      System.out.printf("\n-----------Begin Quiz------------\n");

      // initialize his variables for the number of local wins, Tries, and  integer locations of the hardest question and easiest question In the questions  array list
      int hardQuestion = 0, easyQuestion = 0;
      double wins = 0, tries = 0;

      // makes an array of double values which  Will store the Percentage correct of each answer
      double [] questionDiff2 = new double[questions.size()];

      // makes a clone arraylist of doubles in order to allow me to acces arraylist functions later on
      ArrayList<Double> questionDiff = new ArrayList<Double>(questions.size());

      // for loop which  prompts user with question and asks for their answer
      for(int j = 0; j < questions.size(); j++){
        System.out.print("\nQuestion "+(j+1)+"\n" + questions.get(j).getQuestion()+"\n");
        printAnswers(questions.get(j));

        System.out.printf("Enter Response:");

        // ensures that user inputs an integer
        while(!keyboard.hasNextInt()) {
            System.out.printf("Enter Response:");
            keyboard.next();
        }

        int _userAnswer = keyboard.nextInt();

        // ensures that user input an integer within the range of possible answers
        while(_userAnswer < 0 || _userAnswer > questions.get(j).getNumAnswer()){
          System.out.printf("Enter Response:");

          while(!keyboard.hasNextInt()) {
              System.out.printf("Enter Response:");
              keyboard.next();
          }
          _userAnswer = keyboard.nextInt();
        }

        // sets the users answer in the object
        questions.get(j).setUserAnswer(_userAnswer);

        // increments the attempt  variable for the object
        questions.get(j).incAttempts();

        //  increment tries each time the user answers a question
        tries = tries + 1;

      }

      System.out.printf("\n-------------Results-------------\n");

      // for Loop Which runs through each question
      for(int j = 0; j < questions.size(); j++){

        // accesses the question correct answer and user response from the question object
        System.out.print("\nQuestion "+(j+1)+": " + questions.get(j).getQuestion()+"\n");
        System.out.print("Correct Answer: " + questions.get(j).getCorrectAnswer()+"\n");
        System.out.print("User Response: " + questions.get(j).getUserAnswer()+"\n");

        // if the stored variable for the Users answer is equal to incorrect answer stored within The object then the program will indicate correct answer
        if(questions.get(j).getUserAnswer() == questions.get(j).getCorrectAnswer()){
          System.out.print("Good Job! You Are Correct!\n");

          // increments the successes variable for the object
          questions.get(j).incSuccesses();

          // increment wins each time user gets answer correct 6
          wins = wins + 1;
        }
      }

      System.out.printf("\n-------------Overall-------------\n");

      // displays the number of correct answers the user has made
      System.out.printf("\n\tCorrect: %.0f", wins);

      // displays the amount of incorrect answers these guys made by subtracting wins from all attempts
      System.out.printf("\n\tWrong: %.0f",(tries-wins));

      // prints the percent correct score of each quiz
      System.out.printf("\n\tScore: %.2f", ((double)(wins/tries)*100));
      System.out.print(" %\n");

      System.out.println("\n------Cumulative Statistics-----\n");


      for(int j = 0; j < questions.size(); j++){

        // initialize us to double variables called temps success and temp attempt. Temp success accesses the successes and Temp Attemp accesses the attemps FROM THE OBJECT
        double tempSuc = questions.get(j).getSuccesses(), tempAttempt = questions.get(j).getAttempts();

        //adds the question difficulty to array list
        questionDiff.add(((tempSuc/tempAttempt)*100));

        //adds the question difficulty to array
        questionDiff2[j] = (tempSuc/tempAttempt)*100;

        //Prints the question, the number of correct answers, the number of incorrect responses, and the percent correct
        System.out.print("Question "+(j+1)+": "+ questions.get(j).getQuestion()+"\nCorrect: "+questions.get(j).getSuccesses()+" \tIncorrect: "+(questions.get(j).getAttempts()-questions.get(j).getSuccesses())+" \t ");
        System.out.printf(" Percent Correct: %.2f",questionDiff.get(j));
        System.out.print(" %\n\n");

      }

      //finds the maximum and minimums of the arraylist questionDiff and reports them to respective variables
      double easy = Collections.max(questionDiff);
      double hard = Collections.min(questionDiff);

      //finds which element corresponds to the easiest and hardest question
      for(int j = 0; j < questions.size(); j++){
        if(easy == questions.get(j).getPctCorrect()){
          easyQuestion = j;
        }
        if(hard == questions.get(j).getPctCorrect()){
          hardQuestion = j;
        }
      }

      //when all questions have the same scores there is an exception that all questions are equally difficult
      if(hard == easy){
        System.out.println("Questions are equally ranked in difficulty\n\n");

      }

      //displays the easiest and hardest questions
      else if(hard != easy){
        System.out.println("Easiest Question: "+ questions.get(easyQuestion).getQuestion());
        System.out.printf("Percent Correctly Answered : %.2f", easy);
        System.out.print(" %\n\n");
        System.out.println("Hardest Question: "+ questions.get(hardQuestion).getQuestion());
        System.out.printf("Percent Correctly Answered : %.2f", hard);
        System.out.print(" %\n\n");
      }

      //writes the updated file to the same input file
      PrintWriter writer = new PrintWriter(fileName, "UTF-8");

      //loops through each question object
      for(int j = 0; j < questions.size(); j++){

        //writes data of each question object within the questions arraylist in the same format as the previous file
        writer.println(questions.get(j).getQuestion());
        writer.print(questions.get(j).getNumAnswer());

        ArrayList<String> ansWrite = questions.get(j).getAnswer();

        for(int i = 0; i <= questions.get(j).getNumAnswer(); i++){
          writer.println(ansWrite.get(i));
        }

        writer.println(questions.get(j).getCorrectAnswer());
        writer.println(questions.get(j).getAttempts());
        writer.println(questions.get(j).getSuccesses());
      }
      writer.close();

      System.out.println("-------------Goodbye------------\n");

    }

    //method created to print the possible answers to the user
    public static void printAnswers(Question question){

        ArrayList<String> pullAnswers = question.getAnswer();

        for(int i=1; i < pullAnswers.size(); i++){
          System.out.println("["+(i)+"] "+ pullAnswers.get(i));
        }
      }
    }
