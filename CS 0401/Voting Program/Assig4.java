
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;

public class Assig4 extends JFrame{

  //Array of ballot objects
  public static ArrayList<Ballot> ballotArray = new ArrayList<Ballot>();

  //Array of voter objects
	public static ArrayList<Voter> voters = new ArrayList<Voter>();

  //optionsPanel and its components
	public static LoginButton login = new LoginButton();
	public static VoteButton vote = new VoteButton();
	public static JPanel optionsPanel = new JPanel();

  //main method
  public static void main(String[] args) throws IOException {
		new Assig4("ballots.txt");
	}

  public Assig4(String fileName) throws IOException {

    //numcols corresponds to the number of panels represented in the frame
    int numCols = ballotArray.size()+2;

    //will close program if window is closed
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //sets size of frame based off of columns
    this.setSize((300*numCols),300);

    //sets window location on startup
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension dim = tk.getScreenSize();

    int xPos = (dim.width / 2) - (this.getWidth() / 2);
    int yPos = (dim.height / 2) - (this.getHeight() / 2);

    this.setLocation(xPos, yPos);

    //window is not resizeable
    this.setResizable(false);

    //name of program
    this.setTitle("Voting Machine 1.0");

    //makes a new grid layout
    this.setLayout(new GridLayout(1,numCols));

    //importBallots method reads in ballots to program
    importBallots(fileName);

    //readVoters method reads in voters to program
    readVoters();

    //Adds each ballot object which is by default a panel to the frame
    for(int i = 0; i < ballotArray.size(); i++){
          this.add(ballotArray.get(i));
        }

    //login button and size
    login.setText("Login");
		login.setPreferredSize(new Dimension(100,30));

    //cast vote button and size
		vote.setText("Cast Vote");
		vote.setPreferredSize(new Dimension(100,30));

    //disable the vote button at start
		vote.setEnabled(false);

    //add buttons to the option panel
    optionsPanel.add(login);
    optionsPanel.add(vote);

    //add option panel to the window
    this.add(optionsPanel);

    //displays the frame to the user
		this.setVisible(true);


  }

  //import ballots takes file name of ballots and reads them in
  private void importBallots(String fileName) throws IOException {

    //finds ballot file
    File ballotFile = new File(fileName);

    //scans from ballot file
    Scanner scan = new Scanner(ballotFile);

    //reads numbers of ballots from file
    int numBallots = scan.nextInt();

    //number of lines to read corresponds to the number of ballots
    String [] readLine = new String[numBallots];

    //burn the rest of the line read from reading in the first integer
    scan.nextLine();

    //goes through the ballots
    for(int i = 0; i < numBallots; i++){

      //reads first line
      readLine[0] = scan.nextLine();

      //separates line by : into two string array
      String[] splitLine = readLine[0].split(":");

      //id is the 0 element
      String ballotID = splitLine[0];

      //title is the 1 element
      String ballotTitle = splitLine[1];

      //choices are split into an N element array
      String[] choices = splitLine[2].split(",");

      //initializes choices array
      ArrayList<String> ballotChoices = new ArrayList<String>();

      //adds each element of string array to the arraylist
      for(int j = 0; j < choices.length; j++){
        ballotChoices.add(choices[j]);
      }

       //reads the number of votes for each choice from file
      int[] votes = readBallot(ballotID, ballotChoices);

      //creates a new ballot
      Ballot ballot = new Ballot(numBallots, ballotID, ballotTitle, ballotChoices, votes);

      //adds the ballot to the ballot array
      ballotArray.add(ballot);
    }
  }

  //reads the individual ballot
  private int[] readBallot(String ballotID, ArrayList<String> ballotChoices) throws IOException {

    //appends .txt to the name of the ballot file
    String appendTxt = (ballotID + ".txt");

    //opens file
    File file = new File(appendTxt);

    //integer arra of the number of votes
    int[] votes = new int[ballotChoices.size()];

    //when the file exists, scans file
    if(file.exists()){
      Scanner scanBal = new Scanner(file);

      //sets string array = to the size of ballot choices
      String[] choicesLines = new String[ballotChoices.size()];

      //goes through each choice
      for(int i = 0; i < ballotChoices.size(); i++){

        //sets string array
        choicesLines[i] = scanBal.nextLine();

        //splits each array into two arrays by the colon
        String[] splitVote = choicesLines[i].split(":");

        //parses integer of the second element of the string array and sets it = to an integer
        int splitVoteInt = Integer.parseInt(splitVote[1]);

        //integer array is set with corresponding number of votes
        votes[i] = splitVoteInt;
      }
    }

    //makes a new file for the ballot if it doesnt already exist
    else{

      //print to file
      PrintWriter write = new PrintWriter(file);

      //goes through each ballot choice and sets votes = 0
			for (String choice : ballotChoices) {
				write.println(choice + ":0");
			}

      //close printwriter
			write.close();
    }

    //returns the integer array votes to the constructor of Assig4
    return votes;

  }

  //Reads in voter objects to the program
  public void readVoters() throws IOException {

    //gets file
    File voterFile = new File("voters.txt");

    //new scanner of file
    Scanner voterScan = new Scanner(voterFile);

    //loops through while voter has a line
    while (voterScan.hasNextLine()) {

      //splits the line by colon
      String[] split = voterScan.nextLine().split(":");

      //new voter object is created with the ID, name, and Vote Status
      Voter user = new Voter(split[0], split[1], Boolean.parseBoolean(split[2]));

      //adds object to voter array
      voters.add(user);
		}
  }

  //Safe file update feature
  public static void fileUpdateSafetyNet(ArrayList<Ballot> ballotArray, ArrayList<Voter> voters) throws IOException {

    //status of whether or not the voters were saved properly
    boolean votersSaveSuccess = saveVoter(voters);

    //status of whether or not the ballots were saved properly
		boolean ballotSaveSuccess = saveBallot(ballotArray);

    //the voter and ballot file was temperarily updated succesfully
		if (votersSaveSuccess && ballotSaveSuccess) {

      //permenantly saves the files
			safeSave(ballotArray);
		}

    //the files were not temporarily updated correctly
    else {

      //error message
			String message = "Congrats! You broke the program";

      //display error message
			int selection = JOptionPane.showConfirmDialog(null, message, "GG", JOptionPane.OK_OPTION);

      //quit program when user selects ok
			if (selection  == JOptionPane.OK_OPTION) {
				System.exit(0);
			}
		}
  }

  //Save ballot makes backup file of ballot
  private static boolean saveBallot(ArrayList<Ballot> ballotArray) throws IOException {

    try{

      //goes through each ballot
      for(int i = 0; i < ballotArray.size(); i++){

        //sets ballotID
        String ballotID = ballotArray.get(i).getBallotId();

        //uses ballot ID in the new backup file for ballot
        File ballotFile = new File("backup"+ballotID+".txt");

        //opens new printwriter
        PrintWriter write = new PrintWriter(ballotFile);

        //goes through each candidate
        for(int j = 0; j < ballotArray.get(i).ballotChoices.size(); j++){

          //writes ballot file with the ballot candidate with the votes for each
          String line = ballotArray.get(i).ballotChoices.get(j)+":"+ballotArray.get(i).getNumVotes(j);
          write.println(line);
        }

        //closes the writer
        write.close();
       }
     }

    //catches IOException and returns false exit status
    catch (IOException except){
      return false;
    }

    //If no exception is caught, then it returns true as a successful exit status
    return true;
  }

  //saves voter files
  private static boolean saveVoter(ArrayList<Voter> voters) throws IOException {
    try{
      //new backup file for voters
      File voterFile = new File("backupVoters.txt");

      //write to the backup file
      PrintWriter write = new PrintWriter(voterFile);

      //goes through each voter file
      for(int i = 0; i < voters.size(); i++){

        //writes voters ID, their Name, and their vote status
        write.println( voters.get(i).getId() + ":" + voters.get(i).name + ":" + voters.get(i).getVoterStatus());
      }

      //Closes the print writer
      write.close();
    }

    //returns false if exception is caught
    catch (IOException except) {
      return false;
    }

    //returns true if successful exit status
    return true;
  }

  //updates the old files with the backup files
  private static void safeSave(ArrayList<Ballot> ballotArray) {

    //reads in and deletes voters.txt
    File oldVoter = new File ("voters.txt");
    oldVoter.delete();

    //reads in the backup
    File votersBackup = new File("backupVoters.txt");

    //makes a new file
    File newVoter = new File("voters.txt");

    //renames the backup file to voters.txt
    votersBackup.renameTo(newVoter);


    //Ballots for loop
    for(int i = 0; i < ballotArray.size(); i++){

      //gets each ballot ID
      String ballotID = ballotArray.get(i).getBallotId();

      //gets old ballot ID
      File oldBallot = new File(ballotID + ".txt");

      //deletes old ballot file
      oldBallot.delete();

      //reads the backup file for the Ballot
      File ballotBackup = new File("backup" + ballotID + ".txt");

      //makes new ballot file
      File newBallot = new File(ballotID + ".txt");

      //renames backup file to the proper file name 
      ballotBackup.renameTo(newBallot);
    }
  }
}
