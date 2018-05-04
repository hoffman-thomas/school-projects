import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class VoteButton extends JButton {

	public VoteButton() {

		this.addActionListener(new VoteListener());

	}

	class VoteListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {

			// checks if voter made a valid vote
			Boolean isVoteValid = checkForValidChoices();

			if (!isVoteValid) {

				 // vote is invalid
				JOptionPane.showMessageDialog(null, "Please select all ballot fields", "Invalid Vote", JOptionPane.OK_OPTION);
			} else {

				 // vote is valid, prompt user for confirmation
				int selection  = JOptionPane.showConfirmDialog(null, "There is no returning! Please ensure your votes are correct", "Vote Confirmation", JOptionPane.OK_CANCEL_OPTION);
				if (selection == JOptionPane.OK_OPTION) {

					// user hits ok
					JOptionPane.showMessageDialog(null, "Thank You, Goodbye", "Thank you", JOptionPane.OK_OPTION);

					// updates the ballot and voter information
					ballotVoterUpdate();
				}
				try {
					//reads in the ballotArray and voters array in order to pass into the save file
					ArrayList<Ballot> ballotArray= Assig4.ballotArray;
					ArrayList<Voter> voters = Assig4.voters;

					Assig4.fileUpdateSafetyNet(ballotArray, voters);
				}

				// if IOException is caught it prints an error to the terminal
				catch (IOException except) {
					System.out.println("File Update Error");
				}
			}
		}
	}

	//checks if the vote is valid
	private boolean checkForValidChoices() {
		ArrayList<Ballot> ballots = Assig4.ballotArray;

		// number of ballots the voter voted for
		int counter = 0;

		// Goes through each ballot
		for (Ballot b : ballots) {

			// Goes through each button
			for (int i = 0; i < b.buttonArray.size(); i++) {
				JButton j = b.buttonArray.get(i);

				// increments when button is red
				if (j.getForeground() == Color.RED) {
					counter++;
				}
			}
		}

		//Vote will only be valid if all buttons are red, meaning they were selected
		if (counter == ballots.size()) {
			return true;
		}
		//if vote is invalid return false
		return false;
	}

	//updates ballot info and voter info
	private void ballotVoterUpdate() {
		ArrayList<Ballot> ballots = Assig4.ballotArray;
		ArrayList<Voter> voters = Assig4.voters;

		//goes through each ballot
		for (Ballot b : ballots) {

			//for loop through buttons
			for (int i = 0; i < b.buttonArray.size(); i++) {

				//gets button
				JButton j = b.buttonArray.get(i);

				//sets button to disabled
				j.setEnabled(false);

				//button selected if red
				if (j.getForeground() == Color.RED) {

					//gets buttons text
					String buttonText = j.getText();

					//current number of votes for that choice
					int voteCount = b.getNumVotes(i);

					//increments the votes for that choice
					b.incVote(i);
				}

				//buttons back to black
				j.setForeground(Color.BLACK);

			}
		}

		//goes through each voter
		for (Voter v : voters) {
			//voterID
			if (v.getCurrentVoter()) {

				//remove this voter from being the current voter
				v.setCurrentVoter(false);

				//logs that this voter has already voted
				v.setVoterStatus(true);
			}
		}

		//reset the page
		Assig4.login.setEnabled(true);
		Assig4.vote.setEnabled(false);


	}




}
