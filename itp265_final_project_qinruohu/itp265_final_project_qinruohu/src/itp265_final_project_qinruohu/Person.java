package itp265_final_project_qinruohu;

/**
 * This is the base class of all type of users
 * @author Carol Hu
 * ITP 265, 20201, Tea Section
 * Final Project
 * Email: qinruohu@usc.edu
 */
public class Person implements Comparable<Person>{
	
	private String name;
	private String email;
	
	public Person(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User " + name + ", email=" + email;
	}
	
	public final int compareTo(Person p) {
		int number;
		if (this.equals(p)) {
			return 0;}
		//People are ordered according to their name
		number = this.name.compareTo(p.name);
		if(number == 0) {//if name is the same, compare time
			number = this.email.compareTo(p.email);
		}
		return number;
	}
}
