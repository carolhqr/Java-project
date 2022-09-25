package itp265_final_project_qinruohu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This is the login program that will read from existing members list and write newly added members to the file
 * @author Carol Hu
 * ITP 265, 20201, Tea Section
 * Final Project
 * Email: qinruohu@usc.edu
 */
public class MemberLogin {
	private Map<String, Member> members; //map of email address to member objects
	private static final String MEMBER_FILE = "src/members.txt";
	private Helper helper;
	
	
	public MemberLogin() {
		members = new HashMap<String, Member>();
		helper = new Helper();
		readExistingMemberFromFile();
		
	}

	public Member createMember() {
		String email = helper.inputLine("Please enter your email address: ");
		Member m = createMember(email);
		writeMembersToFile();
		return m;
		
	}
	
	public Member createMember(String email) {
		// if the user is already in the system, do not want to create account
		if (members.containsKey(email)) {
			System.out.println("This account already exists in the system. Please login instead.");
			return null;
		}
		String name = helper.inputLine("Please enter your full name: ");
		String password = helper.inputLine("Please enter your password: ");
		
		String question = "";
		String answer = "";
		boolean setSecurityQuestion = helper.inputYesNoAsBoolean("Would you like to set a security question? (y/n)");
		if (setSecurityQuestion) {
			question = helper.inputLine("Please enter your question: ");
			answer = helper.inputLine("Please enter the answer to" + question);
		}
		Member m = new Member(name,email,password,question,answer);
		Member mfinal = upgradeMembership(m); //ask if the newly created member would like to upgrade membership
		return mfinal; 
	}
	
	private void readExistingMemberFromFile() {
		try(FileInputStream fis = new FileInputStream(MEMBER_FILE);
				Scanner scan = new Scanner(fis)){
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				Member member = parseLineToMember(line);
				members.put(member.getEmail(),member);
			}
		} catch (FileNotFoundException e) {
			System.err.print("File not found exception");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.print("IOException");
			e.printStackTrace();
		}
		
	}
	
	
	private Member parseLineToMember(String line) {
		Member m;
		Member mtemp;
		Scanner lineReader = new Scanner(line);
		lineReader.useDelimiter("/");
		String membership = lineReader.next();
		String email = lineReader.next();
		String name = lineReader.next();
		String password = lineReader.next();
		if (lineReader.hasNext()) {
			String question = lineReader.next();
			String answer = lineReader.next();
			mtemp = new Member(name,email,password,question,answer);
		}
		else {
			mtemp = new Member(name,email,password);
		}
		//check the type of membership
		if (membership.equalsIgnoreCase("paidmember")) { 
			m = new PaidMember(mtemp);
		}
		else {
			m = new DropInCustomer(mtemp);
		}
		lineReader.close();
		return m;
	}
	
	public String membersToString() {
		// just a way to visualize the map of users....
		// go through the map, get all the keys, and print all the User objects
		String s = "Map of All Users --> Email: User Object\n";
		for(String key: members.keySet()) { // for each key in the user map of keys....
			Member value = members.get(key); // get the object associated with the key
			s += "\t" + key + ": "+ value + "\n";
		}

		return s;
	}
	
	
	
	/**
	 * This method handles user signing in
	 * will check if the email has an account associated with it, if not, will ask for creating a new account
	 * @return
	 */
	public Member memberSignin() {
		Member m;
		String email = helper.inputLine("Please enter your email:");
		if(members.containsKey(email)) {//if the member exists in the members
			Member mtemp = members.get(email);//get the temporary member
			String password = helper.inputLine("Please enter your password:");//verify the password
			boolean pass = mtemp.verifyPassword(password);
			while (!pass) {
				System.out.println("Password incorrect. Please try again.");
				password = helper.inputLine("Please enter your password:");
				pass = mtemp.verifyPassword(password);
			}
			m = mtemp; //if the password is correct
			System.out.println("Welcome back "+m.getName()+"!");
		}
		else {
			System.out.println("Didn't find the member associated with that email.");//if the email could not be found;
			boolean createNew = helper.inputYesNoAsBoolean("Would you like to create a new account?(y/n)");
			if(createNew) {
				m = createMember(email);
				upgradeMembership(m);
			}
			else {
				m = null;
			}
		}
		return m;	
	}
	
	
	/**
	 * Every time a new user creates an account, ask if he/she would like to purchase an annual membership
	 * @param m
	 * @return a specific member type
	 */
	public Member upgradeMembership(Member m) {
		Member mfinal;
		boolean decision = helper.inputYesNoAsBoolean("Would you like to purchase an annual membership for $79.99? (y/n)\n"
				+ "By upgrading to our premium membership, you will have free access to group exercises and could also rent a locker.");
		if (decision) {
			mfinal = new PaidMember(m);
		}
		else {
			mfinal = new DropInCustomer(m);
		}
		members.put(mfinal.getEmail(), mfinal); //put the member into the map after specific member type is verified
		return mfinal;
	}
	
	
	/**
	 * This method will write all the members in the map to the file
	 */
	private void writeMembersToFile() {
		try (FileOutputStream fos = new FileOutputStream(MEMBER_FILE);
			PrintWriter pw = new PrintWriter(fos)) {
			// take each member in the map and write it to the file.
			for(String key: members.keySet()) {
				Member u = members.get(key);
				if(u.getClass().equals(PaidMember.class)){
					pw.print("PaidMember/");
				}
				else {
					pw.print("DropInCustomer/");
				}
				// print users email/name/password/q/a
				pw.println(u.toFileString());
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // automatically close resources at end
	}
	
	public void viewAllMembers() {
		System.out.println("Paid Members:");
		int i = 1;
		for(Member m: members.values()) {
			if (m.getClass().equals(PaidMember.class)) {
				System.out.println(i+". "+m.getName()+", email=" +m.getEmail());
				i += 1;
			}
			
		}
		System.out.println("\nDrop in Customers:");
		int k = 1;
		for(Member m: members.values()) {
			if(m.getClass().equals(DropInCustomer.class)) {
				System.out.println(k+". "+m.getName()+", email= "+m.getEmail());
				k += 1;
			}
		}
	}
}
