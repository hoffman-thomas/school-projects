

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;

class Ballot extends JPanel{

  //Class variable
  private static int numBallots;

  //instance variable
  private String ballotID;
  private String ballotTitle;
  public ArrayList<String> ballotChoices = new ArrayList<String>();
  public ArrayList<JButton> buttonArray = new ArrayList<JButton>();
  private int[] votes;
  private JLabel header;


  public Ballot(int _numBallots, String _ballotID, String _ballotTitle, ArrayList<String> _ballotChoices, int[] _votes){

    //construct variables
    numBallots = _numBallots;
    ballotID = _ballotID;
    ballotTitle = _ballotTitle;
    ballotChoices = _ballotChoices;
    votes = _votes;

    header = new JLabel(_ballotTitle);
    this.add(header);

    //goes through each ballot
    for(int i = 0; i < _ballotChoices.size(); i++){

      //initializes button
      JButton button = new JButton(_ballotChoices.get(i));

      //adds ButtonListener to the button
      button.addActionListener(new ButtonListener());

      //disables button, but button is stil shown
      button.setEnabled(false);

      // sets size of buttons
      button.setPreferredSize(new Dimension(120,30));

      //adds button to the buttonArray for future reference
      buttonArray.add(button);

      //adds button to JPanel
      this.add(button);
    }

    //sets the panel visible to the frame
    this.setVisible(true);

  }

  //returns the number of votes
  public int getNumVotes(int x){
    return votes[x];
  }

  //increment the number of votes
  public void incVote(int x){
    votes[x] = votes[x] + 1;
  }

  //return the ballot ID
  public String getBallotId(){
    return ballotID;
  }

  //ButtonListener
  class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {

			// gets the buttons information
			JButton source = (JButton) e.getSource();
			String buttonText = source.getText();
			int numVotes = ballotChoices.size();

      // goes through each button
			for (JButton j: buttonArray) {

        //when button is pressed
				if (j.getText().equals(buttonText)) {

          //sets text color to red
					j.setForeground(Color.RED);
				}

        //when button wasnt pressed
        else {

          //sets text color to black
					j.setForeground(Color.BLACK);

				}
			}
		}
  }
}
