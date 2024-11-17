//package project3;
import java.util.Date;


/** 
 * 
 * 
 * This class represents the individual records from the input log file
 * @throws IllegalArgumentException if constructor is called with invalid value for terminal 
 * 
 * @author Aarit Hundi
 * @version 10/31/2024
 */
public class Record implements Comparable<Record>{
	
	private int terminal;
	private boolean login;
	private String username;
	private Date time;
	
	/**
	 * constructor that creates a Record object
	 * @param terminal, integer terminal value of record, needs to be positive
	 * @param login, boolean that represents if a Record object represents a login(true) or a logout(false)
	 * @param username, String name of user
	 * @param time, Date object that represents the date/time at which user logged in/out 
	 */
	public Record(int terminal, boolean login, String username, Date time) {
		if (terminal<=0){
			throw new IllegalArgumentException("terminal cannot be negative");
		}

		this.terminal=terminal;
		this.login = login;
		this.username = username;
		this.time = time;
	}
	
	
	/** returns the terminal value for this record 
	 * 
	 * @return integer terminal value, only positive integers 
	 * 
	 */
	public int getTerminal() {
		return terminal;
	}
	
	/** Returns boolean for whether this Record represents a login; 
	 * true = login record; false = logout record 
	 * 
	 * @return boolean true if Record represents a log in
	 */
	public boolean isLogin() {
		return login;
	}
	
	/**Returns boolean for whether this Record represents a logout;
	 * true = logout record; false = login record
	 * 
	 * @return boolean true if Record represents a log out
	 */
	public boolean isLogout() {
		return !login;
	}
	
	/**returns the username of this Record
	 * 
	 * @return String username of this Record's user
	 */
	public String getUsername() {
		return username;
	}
	
	/** returns date object "time" that represents the Date/time of the Record
	 * 
	 * @return Date time of this user's login
	 */
	public Date getTime() {
		return time;
	}

	/**compares two record objects by their respective login/logout times
	* @return 1 if first record's time is greater than the other record's time, 
	* -1 if first record's time is less than the other record's time, 0 if the
	* times are the same
	* @param other, record that is being compared to
	*/
	@Override
	public int compareTo(Record other){
		//checks if parameter is null
		if (other == null){
			throw new NullPointerException("parameter cannot be null");
		}
		//checks if parameter is an instance of Record
		if(!(other instanceof Record)){
			throw new ClassCastException("parameter is not an instance of Session class");
		}

		//this record goes after "other" (A>B)
		if(this.getTime().getTime() > other.getTime().getTime()){
			return 1; 
		}

		//this record goes before "other" (A<B)
		else if(this.getTime().getTime() < other.getTime().getTime()){
			return -1;
		}
		
		//records are equal (A=B)
		else{
			return 0;
		}
	}

	/** checks if two Record objects are equal by comparing terminal number, username, login, and time
	* @return true if records are equal, false if not
	* @param Object obj being compared to
	*/
	@Override 
	public boolean equals(Object obj){
		//if both refer to same object
		if (this == obj){
			return true;
		}
		//check if obj is not null and if it is an instance of Record
		if (obj == null || !(obj instanceof Record)){
			return false;
		}
		Record other = (Record)obj;
		if (this.terminal == other.terminal && this.username.equals(other.username) && this.login == other.login && this.time.equals(other.time)){
			return true;}
		return false;
	}

	
	public String toString(){
		return this.getUsername() + " " + this.getTime().getTime();
	}
}
