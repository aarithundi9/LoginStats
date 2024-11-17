//package project3;
import java.util.Date;

/** This class represents a single login session for a user. It uses matching login and logout records to create Session Objects. 
 * 
 * @author Aarit Hundi
 * @version 10/31/2024
 */
public class Session implements Comparable<Session>{
	
	private Record login;
	private Record logout;

	/**
	 * constructs Session object using login/logout Record objects; login and logout have to have the same terminal number
	 * to represent a single session; 
	 * @param Record login object; cannot be null
	 * @param Record logout object; can be null (if user is still logged in)
	 * @throws IllegalArgumentException if constructor has invalid parameters, 
	 */
	public Session(Record login, Record logout) throws IllegalArgumentException{
		//check if login is null
		if (login == null) {
			throw new IllegalArgumentException("login record cannot be null");
		}
		//if user has logged out, check for matching terminal #'s, matching usernames, and if logout time > login time
		if (logout!=null){
			if (login.getTerminal()!=logout.getTerminal() || !login.getUsername().equals(logout.getUsername()) || login.getTime().getTime()>logout.getTime().getTime() || login.isLogin() == logout.isLogin()){
				throw new IllegalArgumentException("login/logout terminals or usernames do not match, or login time is greater than logout time");
			}
		}

		this.login = login;
		this.logout = logout;
	}
	
	/** return terminal value of this session
	 * 
	 * @return integer terminal value for this session
	 */
	public int getTerminal() {
		return login.getTerminal();
	}
	
	/** returns login time
	 *  
	 * @return Date login time for this session
	 */
	public Date getLoginTime() {
		return login.getTime();
	}
	
	/** returns logout time
	 * 
	 * @return Date logout time for this session
	 */
	public Date getLogoutTime() {
		if (logout == null)
			return null;
		return logout.getTime();
	}

	/**returns username of user in this session
	 * 
	 * @return the String username of the user in this session
	 */
	public String getUsername() {
		return login.getUsername();
	}
	
	/** returns duration value for this session; uses getTime() method inherited from Date class
	 * 
	 * @return long duration (milliseconds between login and logout time) or -1 if session
	 * is still active (user has not logged out)
	 */
	public long getDuration() {
		if (logout == null)
			return -1;
		return logout.getTime().getTime()-login.getTime().getTime(); //getTime() method called from Date class
	}

	 /** Returns a string representation of the Session in the format:
     * USERNAME, terminal TERMINAL, duration DURATION
	   logged in: LOGIN_TIME
	   logged out: LOGOUT_TIME
	   this method overrides the toString method of the Object class
     * 
     * @return String representation of this Session in the format above
     */
    public String toString() {
		
		//creating the display for duration
		long duration = getDuration();
		long seconds = duration / 1000;
        long days = seconds / (24 * 3600);
        long hours = (seconds % (24 * 3600)) / 3600;
        long minutes = (seconds % 3600) / 60;            
		long secs = seconds % 60;

		//format string display of duration
		String dur = String.format("%d days, %d hours, %d minutes, %d seconds", days, hours, minutes, secs);

    	//if user has logged out 
		if(logout != null){
        return login.getUsername() + ", terminal " + login.getTerminal() + ", duration " + dur + 
        		"\nlogged in: " + getLoginTime().toString() + 
        		"\nlogged out: " + getLogoutTime().toString();} //used toString() from Date class
        
        //if user is logged in currently
		else{
        return this.getUsername() + ", terminal " + this.getTerminal() + ", duration active session"  + 
        		"\nlogged in: " + getLoginTime().toString() + 
        		"\nlogged out: still logged in";}

		
    }

	/**compares two session objects by their respective login times
	* @return 1 if first session's login time is greater than the other login time, 
	* -1 if first session's login time is less than the other login time, 0 if the login
	* times are the same
	* @param other, session that is being compared to
	*/
	@Override
	public int compareTo(Session other){
		if (other == null){
			return 1;
		}
		
		//if "this" goes after other
		if(this.getLoginTime().getTime() > other.getLoginTime().getTime()){
			return 1;
		}

		//if "this" goes before other
		if(this.getLoginTime().getTime() < other.getLoginTime().getTime()){
			return -1;
		}

		//if they are equal
		else {
			return 0;
		}		
	}

	/**checks if two Session objects are equal based on if they have the same login/logout {@link Record
	*@return boolean true if Sessions are equal, false if not
	*@param o Object that is being comapared to
	*/
	@Override
	public boolean equals(Object obj){
		//if both objects have the same reference
		if (this == obj){
			return true;
		}
		//if o is null or not an instance of session class
		if (obj == null || !(obj instanceof Session) ){
			System.out.println(0);
			return false;
		}

		Session other = (Session)obj;
		
		//if both sessions are active (no logouts)
		if(this.logout == null && other.logout == null){
			if(this.login.equals(other.login)){
				return true;
			}
			return false;
		}

		//if only one session has a logout
		if(this.logout == null || other.logout == null){
			return false;
		}

		//regular comparison
		if (this.login.equals(other.login) && this.logout.equals(other.logout)){
			return true;
		}

		return false;
	}
}
