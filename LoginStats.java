//package project3;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Date;



/**This class is the runner class for this program. It contains the main method and handles all 
 * user input/ouput and reads the data file
 * 
 * @author Aarit Hundi
 * @version 10/31/2024
 */
public class LoginStats {
	

	/** This is where all user input/output is handled 
	 * 
	 */
	public static void main(String[] args) {

		//checks for input file in command line; code based on project 1 code
		if (args.length == 0 ) {  
            System.err.println("Usage Error: the program expects file name as an argument.\n");
            System.exit(1);
        }
		
		
		//verify that command line argument contains a name of an existing file; code based on project 1 code
        File inputFile = new File(args[0]);
        if (!inputFile.exists()) { //file is not available in the location
            System.err.println("Error: the file "+inputFile.getAbsolutePath()+" does not exist.\n");
            System.exit(1);
        }

        //opens file to read
        Scanner inRecords = null; 
		
        try {
          inRecords = new Scanner (inputFile) ; //creates scanner that will read given input file
        } catch (FileNotFoundException e) { //catches error if given input file cannot be read
            System.err.println("Error: the file "+inputFile.getAbsolutePath()+
                               " cannot be opened for reading.\n");
            System.exit(1);
        } 
        
        
        
        RecordList list = new RecordList();
        String line = null; 
        Scanner parseLine = null;
        
        //values to be parsed from input file
        int terminal = 0;
        Date time = null;
        String username = "";
        boolean login = true;
        Record record = null;
        long milliseconds = 0;
        
        //loop that parses input file and stores data into RecordList ArrayList; code is based on project 1 code
        while (inRecords.hasNextLine()) {
            try {
                line = inRecords.nextLine();
                parseLine = new Scanner(line); //initializes scanner for each line
                parseLine.useDelimiter(" "); //uses the space between data as a break
                
                //parses the data in file

                //first value in each line is stored in terminal
                terminal = parseLine.nextInt();

                //(+) terminal = login, (-) terminal = logout
                if (terminal>0){
                	login = true;}
                else{
                    terminal = -terminal; //if terminal is (-), make it (+)
                    login = false;}

                //second value in each line is stored in milliseconds
                milliseconds = parseLine.nextLong();
                time = new Date(milliseconds);

                //third value in each line is stored in username
                username = parseLine.next();
            }
            catch (NoSuchElementException ex ) {
                //caused by an incomplete or miss-formatted line in the input file
                System.err.println("Error in this line: " + line);
                continue;
            }
            
            //creates new record object fpr each line and adds it to list
            record = new Record(terminal, login, username, time);
            list.add(record);
              
        }
       

        //user I/O
        System.out.println("Welcome to Login Stats!"
                + "\nfirst USERNAME\t -\t retrieves first login session for the USER"
                + "\nlast USERNAME\t -\t retrieves last login session for the USER"
                + "\nall USERNAME\t - \t retrieves all sessions for the USER"
                + "\ntotal USERNAME\t - \t retrives total duration spent logged in for the USER"
                + "\nquit\t\t -\t terminates this program ");

        while(true){//allows program to provide an alternate command if an error arises
            Scanner reader = new Scanner(System.in);
            
            String input = reader.nextLine();
            
            

            if(input.equals("quit")){
                System.exit(1);
            }

            //split input into two strings
            String[] parts = input.split(" ");
            if (parts.length != 2){ //if two separate words are not entered
                System.out.println("This is not a valid query. Try again");
                continue;
            }
            String query = parts[0]; //represents "first" or "last"
            String user = parts[1]; // represents username
            if(parts.length != 2 || !parts[0].equals("first") && !parts[0].equals("last") && !parts[0].equals("all") && !parts[0].equals("total")){ //checks for valid input
                System.out.println("This is not a valid query. Try again");
                continue;
            }

            try{
                if(parts[0].equals("first")){ 
                    Session sessionF = list.getFirstSession(parts[1]);
                    System.out.println(sessionF);
                }
                else if(parts[0].equals("last")){
                    Session sessionL = list.getLastSession(user);
                    System.out.println(sessionL);
                }
                else if (parts[0].equals("all")){
                    SortedLinkedList<Session> sessionList = list.getAllSessions(user);
                    for (Session session : sessionList){
                        System.out.println(session);
                        System.out.println();
                    }
                }
                else if (parts[0].equals("total")){
                    long totalTime = list.getTotalTime(user);
                    long seconds = totalTime / 1000;
                    long days = seconds / (24 * 3600);
                    long hours = (seconds % (24 * 3600)) / 3600;
                    long minutes = (seconds % 3600) / 60;            
                    long secs = seconds % 60;

                    //format string display of duration, copied code from Session class
                    String dur = String.format("%dd %dh %dm %ds", days, hours, minutes, secs);
                    System.out.println(user + ", total duration " + dur);
                }
            }
            catch(NoSuchElementException e){//if user cannot be found in list
                System.out.println("No user matching " + user + " found. ");
            }
            System.out.println();
        }
        
	}
    
    

}
