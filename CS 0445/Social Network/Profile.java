package A1;

public class Profile implements ProfileInterface {

	private String name;
	private String about;
	private Set<ProfileInterface> followers;

	public Profile(){
		if(name == null)
			name = "";

		if(about == null)
			about = "";

		if(followers == null)
			followers = new Set();
	}
	public Profile(String _name, String _about){
		name = _name;
		about = _about;
		followers = new Set();
	}

	@Override
	public void setName(String newName) throws IllegalArgumentException {
		if(newName == null)
			throw new IllegalArgumentException();

		name = newName;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setAbout(String newAbout) throws IllegalArgumentException {
		if(newAbout == null)
			throw new IllegalArgumentException();

		about = newAbout;
	}

	@Override
	public String getAbout() {
		return about;
	}

	@Override
	public boolean follow(ProfileInterface other) {
		if(other == null)
			return false;

		if(other == (ProfileInterface)this)
			return false;

		followers.add(other);
		return true;
	}

	@Override
	public boolean unfollow(ProfileInterface other) {
		if(other == null)
			return false;

		if(followers.remove(other) == true){
			return true;	
		}else{
			return false;
		}
	}

	@Override
	public ProfileInterface[] following(int howMany) {


		if(followers.getCurrentSize() < howMany)
			howMany = followers.getCurrentSize();

		Object[] previewObject = followers.toArray();

		ProfileInterface[] preview = new ProfileInterface[howMany];

		for(int i = 0; i < howMany; i++){
			ProfileInterface pro = (ProfileInterface) previewObject[i];
			preview[i] = pro;
		}

		return preview;
	}

	@Override
	public ProfileInterface recommend() {

		for(int i = 0; i < followers.getCurrentSize(); i++){
			ProfileInterface[] usersFollowed = following(i+1);

			ProfileInterface[] usersFollowedbyFollower = usersFollowed[i].following(10000);

			for(int j = 0; j < usersFollowedbyFollower.length; j++ ){

				if((followers.contains(usersFollowedbyFollower[j]) == false) && ((ProfileInterface)this != usersFollowedbyFollower[j])){
					//System.out.println(usersFollowedbyFollower[j].getName());
					return usersFollowedbyFollower[j];
				}
			}
		}
		//System.out.println("Sorry, there is nobody to recommend");
		return null;
	}


}
