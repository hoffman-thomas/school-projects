import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class LoginButton extends JButton {

    public LoginButton() {
        this.addActionListener(new LoginListener());
    }

    class LoginListener implements ActionListener {

        private JTextField _voterIDField = new JTextField();
        private JLabel _idLabel = new JLabel("Voter ID:");
        private Object[] message = {_idLabel, _voterIDField};

        //login button was pressed
        public void actionPerformed(ActionEvent e) {
            _idLabel.setHorizontalAlignment(SwingConstants.CENTER);

            //allows user to enter voter id
            int selection = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
            if (selection == JOptionPane.OK_OPTION) {

                //user entered an id
                userEnteredAnID(selection);
            }
        }

        //user entered an id
        private void userEnteredAnID(int selection) {
            String voterID = _voterIDField.getText();

            //checks to see if the user exists
            String voterName = checkIfUserExists(voterID);

            //if the user does not exist
            while (voterName.equals("")) {


                String alert = "Voter ID: " + voterID + " not a valid ID";

                //Invalid ID
                JOptionPane.showMessageDialog(null, alert, "Invalid ID", JOptionPane.OK_OPTION);

                //resets the textfield
                _voterIDField.setText("");

                //reprompts the user to enter an id
                selection = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
                voterID = _voterIDField.getText();

                // user hits okay button
                if (selection == JOptionPane.OK_OPTION) {

                    // checks if the user id exists
                    voterName = checkIfUserExists(voterID);
                }
                else {

                    break;
                }
            }

            if (!voterName.equalsIgnoreCase("")) {

                // checks to make sure the voter didnt vote already
                boolean didTheVoterVote = didVote(voterID);

                // voter did not vote yet
                if (!didTheVoterVote) {

                    // user id does exist
                    voterWasValid(voterName);
                }

                // voter already voted
                else {
                    JOptionPane.showMessageDialog(null, "Vote already logged", "Repeat Voter", JOptionPane.OK_OPTION);

                    //resets text field
                    _voterIDField.setText("");
                }
            }
        }

        // checks if the voter exists as an object
        private String checkIfUserExists(String voterID) {

            //gets the voters array from Assig4
            ArrayList<Voter> voters = Assig4.voters;

            //initializes the name string
            String voterName = "";

            //goes through each voter
            for (Voter v : voters) {

                //if the input id matches a valid voter
                if (v.getId().equals(voterID)) {

                    //set this voter as the current voter
                    v.setCurrentVoter(true);

                    //get the voters name
                    voterName = v.name;
                    break;
                }
            }

            //return the string containing the name of the voter
            return voterName;
        }

        //checks if the voter already voted
        private boolean didVote(String voterID) {

            //gets voter object array from main program
            ArrayList<Voter> voters = Assig4.voters;

            //for each voter
            for (Voter v : voters) {

                //find the id of each voter in the array and compare it to the iput ID from user
                if (v.getId().equals(voterID)) {

                    //returns whether or not the voter has already polled
                    return v.getVoterStatus();
                }
            }
            return false;
        }



        //voter has been verified as existant and has not voted before
        private void voterWasValid(String voterName) {
            _voterIDField.setText("");

            //Login success
            JOptionPane.showMessageDialog(null, voterName + ", please vote", "Welcome!", JOptionPane.OK_OPTION);

            //gets ballot array from Assig4
            ArrayList<Ballot> ballots = Assig4.ballotArray;

            //goes through each ballot
            for (Ballot b : ballots) {

                //goes through each button for that ballot
                for (JButton j : b.buttonArray) {

                    //enables the vote buttons
                    j.setEnabled(true);
                }
            }

            //enables vote button
            Assig4.vote.setEnabled(true);

            //disables login button 
            Assig4.login.setEnabled(false);
        }
    }
}
