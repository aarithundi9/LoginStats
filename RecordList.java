//package project3;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * This class stores a this of all existing records in an Sorted Linked List of Records. It contains methods to retrieve a user's 
 * first session, last session, all sessions, and total duration logged in.  
 * @author Aarit Hundi
 * @version 10/9/2024
 */
public class RecordList extends SortedLinkedList<Record> {
	
	/**default constructor
	 *
	 *
	 */
	public RecordList() {
		
	}
	
	/**construct and return the first login session for the specified user; if there are multiple login sessions, 
	 * the first one is the one with the earliest login time
	 * 
	 * @param String user, user who's firstSession will be returned
	 * @return first Session of the specified user
	 * @throws NoSuchElementException if specified user does not match any records on thiss
	 * @throws IllegalArgumentException if method is called with an illegal argument (null or empty string)
	 */
	public Session getFirstSession(String user) {
		if (user == null || user.isEmpty()){ //check for invalid input
			throw new IllegalArgumentException("user is null or empty");
		}

		Record login = null;
		Record logout = null;
		for (int i =0; i<this.size(); i++){ //iterate through record list
			if(this.get(i).getUsername().equals(user) && this.get(i).isLogin()){ //finds first login record
				login = this.get(i); 
				for (int j = i+1; j<this.size(); j++){ //if first login record found, look for logout
					if (this.get(j).getUsername().equals(user) && this.get(j).isLogout() && this.get(j).getTerminal() == (this.get(i).getTerminal())){//check if same user, terminal #, and if record is a logout
							logout = this.get(j); 
							return new Session(login, logout);
					}
					
				}
				//if no logout session is found
				return new Session(login, null);
			 }
	
		}	
		
		//if no login session is found for the user
		throw new NoSuchElementException("specified user was not found in the list");
	}	

		
	
	
	/**construct and return the last login session for the specified user; if there are multiple logout sessions, 
	 * the last one is the one with the latest login time 
	 * 
	 * @param String user, user who's last Session will be returned
	 * @return last Session of the specified user
	 * @throws NoSuchElementException if specified user does not match any records on thiss
	 * @throws IllegalArgumentException if method is called with an illegal argument (null or empty string)
	 */
	public Session getLastSession(String user) {
		if (user == null || user.isEmpty()){ //check for invalid input
			throw new IllegalArgumentException("user is null or empty");
		}

		Record login = null;
		for(int i = this.size()-1; i>=0; i--){ //iterate from back of list
			if(this.get(i).getUsername().equals(user) && this.get(i).isLogin()){//find last login record
				login = this.get(i);
				for(int j = i+1; j<this.size(); j++){ //start at position of last login and search for corresponding logout
					if (this.get(j).getUsername().equals(user) && this.get(j).isLogout()){ //check for = usernames & logout
						if (this.get(j).getTerminal() == (login.getTerminal())){ //check for = terminals
							return new Session(login, this.get(j));
						}
					}
				}
				//if no logout record is found (active session)
				return new Session(login, null);
			}
		}
		//if no login session found for user
		throw new NoSuchElementException("specified user was not found in the list");
	}


	/** returns the total amount of time (in milliseconds) that the user was logged on to the system. 
	*If the user is logged in to two or more terminals at the same time, the time counts double for 
	*part or the entirety of the duration of the sessions. Active sessions do not contribute to total duration
	*
	* @param String user, who's total time will be returned
	* @return total time spent logged in for "user"
	* @throws NoSuchElementException if user is not found in the list
	* @throws IllegalArgumentException if user is null or empty
	*/
	public long getTotalTime(String user){
		//check for null or empty
		if (user == null || user.isEmpty()){
			throw new IllegalArgumentException("user cannot be null or empty");
		}

		long total = 0;
		boolean userFound = false; 

		//start with first person on Record list
		for(int i = 0; i<this.size(); i++){
			Record login = this.get(i);

			//check if record is a login
			if (login.getUsername().equals(user) && login.isLogin()){
				userFound = true;
				Record logout = null;
				
				
				for(int j = i+1; j<size(); j++){
					logout = this.get(j);
					if(logout.getUsername().equals(user) && logout.getTerminal() == login.getTerminal() && logout.isLogout()){
						Session session = new Session(login, logout);
						total += session.getDuration();
					}
				}

			}
			
		}
		//no login record matches the specified user
		if (!userFound)
			throw new NoSuchElementException("user " + user + " was not found in list");
		return total;
	}

	/**returns the list of all login sessions for the specified user ordered from earliest to latest login time
	*
	* @param String user, who's sessions will be returned
	* @return list of sessions for specified user
	* @throws NoSuchElementException if user is not found in the list
	* @throws IllegalArgumentException if user is null or empty
	*/
	public SortedLinkedList<Session> getAllSessions(String user){
		//check for null/empty
		if (user == null || user.isEmpty()){
			throw new IllegalArgumentException("user cannot be null or empty");
		}

		SortedLinkedList<Session> sessionList = new SortedLinkedList<>();

		Record login = null;
		boolean userFound = false;

		//iterate "i" through all of specified user's login records
		for(int i = 0; i<this.size(); i++){
			login = this.get(i);

			//check if login is a login record and usernames match
			if(login.isLogin() && login.getUsername().equals(user)){
				userFound = true;
				Record logout = null;
				boolean logoutFound = false;
				
				//search for a corresponding logout record
				for(int j = i+1; j<this.size(); j++){
					logout = this.get(j);
					
					//check for logout, equal terminal numbers, and equal username to login
					if(logout.isLogout() && logout.getTerminal() == login.getTerminal() && logout.getUsername().equals(login.getUsername())){
						Session session = new Session(login, logout);
						sessionList.add(session);
						logoutFound = true;
					}
				}
				//if no logout session is found
				if(!logoutFound){
					Session session = new Session(login, null);
					sessionList.add(session);
				}
			}
		}

		if(!userFound){
			throw new NoSuchElementException("user " + user + " not found in list");
		}
		return sessionList;
	}

}


