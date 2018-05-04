public class Voter {
	private String voterID;
	public String name;
	private boolean didVote;
	private boolean isCurrentVoter;


	//constructor
	public Voter(String _voterID, String _name, boolean _didVote) {
		voterID = _voterID;
		name = _name;
		didVote = _didVote;

		//sets current voter status to false by default
		isCurrentVoter = false;

	}

	//getters
	//gets voter ID
	public String getId() {
		return voterID;
	}

	//gets wether if not the voter voted
	public boolean getVoterStatus() {
		return didVote;
	}

	//sets voter status
	public void setVoterStatus(Boolean bool) {
		didVote = bool;
	}

	//sets voter status
	public void setCurrentVoter(boolean bool) {
		isCurrentVoter = bool;
	}

	//gets whether or not the voter is
	public boolean getCurrentVoter() {
		return isCurrentVoter;
	}
}
