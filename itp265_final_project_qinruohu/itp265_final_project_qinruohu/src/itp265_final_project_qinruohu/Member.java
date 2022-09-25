package itp265_final_project_qinruohu;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This is the Member class which extends from Person
 * @author Carol Hu
 * ITP 265, 20201, Tea Section
 * Final project
 * Email: qinruohu@usc.edu
 */
public class Member extends Person{
	
	private String password;
	private String question;
	private String answer;
	private ArrayList<FitnessClass> registeredClass;
	private double billing;
	
	public Member(String name, String email, String password, String question, String answer, double billing) {
		super(name,email);
		this.password = password;
		this.question = question;
		this.answer = answer;
		this.billing = billing;
		registeredClass = new ArrayList<FitnessClass>();
	}
	
	public Member(String name, String email, String password, String question, String answer) {
		this(name, email, password,question,answer,0.0);
	}
	
	public Member(String name, String email, String password) {
		super(name, email);
		this.password = password;
		this.question = "";
		this.answer = "";
		
	}
	
	public boolean verifyPassword(String pword) {
		return password.equals(pword);
			
	}
	
	public String toFileString() {
		String fileLine = getEmail() + "/" + getName() + "/" + password;
		if(!question.isEmpty()) {
			fileLine += "/" + question + "/" + answer;
		}
		return fileLine;
	}
	
	public boolean changePassword(String oldPassWord, String newPassWord) {
		boolean changed = false;
		if(verifyPassword(oldPassWord)) {
			this.password = newPassWord;
			changed = true;
		}
		return changed;	
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}
	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String getPassword() {
		return password;
	}
	
	
	public double getBilling() {
		return billing;
	}

	public void setBilling(double billing) {
		this.billing = billing;
	}
	
	public void addBilling(double cost) {
		this.billing += cost;
	}

	public ArrayList<FitnessClass> getRegisteredClass(){
		return registeredClass;
	}
	
	public void addClass(FitnessClass f) {
		registeredClass.add(f);
	}
	
	public void dropClass(FitnessClass f) {
		registeredClass.remove(f);
	}
	
	public String viewClass() {
		Collections.sort(registeredClass);
		int i = 1;
		String s = "You have scheduled the following class(es):\n";
		for (FitnessClass fc: registeredClass) {
			s += i;
			s += ". " ;
			s += fc;
			s += "\n";
			i+=1;
		}
		return s;
	}
	
	
	public int compareTo(Member m) {
		int number;
		if (this.equals(m)) {
			return 0;}
		//customers are ordered according to their name first
		number = this.getName().compareTo(m.getName());
		if(number == 0) {//if name is the same, compare email
			number = this.getEmail().compareTo(m.getEmail());
		}
		return number;
	}
	
	@Override
	public String toString() {
		String s = super.toString();
		if(!question.isEmpty()) {
			s += ", question=" + question + ", answer=" + answer;
		}
		return s;
	}
	
	

}
