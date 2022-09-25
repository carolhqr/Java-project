package itp265_final_project_qinruohu;

import java.util.ArrayList;

/**
 * This is the instructor class
 * @author Carol Hu
 * ITP 265, 20201, Tea Section
 * Final project
 * Email: qinruohu@usc.edu
 */
public class Instructor extends Person implements rentLocker{
	private String specialty;
	private ArrayList<FitnessClass> instructedClass;
	private static int lockerNum = 1; //instructors' locker number start from 1
	private boolean hasLocker;
	

	public Instructor(String name, String email, String specialty) {
		super(name,email);
		this.specialty = specialty;
		this.hasLocker = false;
		this.instructedClass = new ArrayList<FitnessClass>();
	}
	
	

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public ArrayList<FitnessClass> getInstructedClass() {
		return instructedClass;
	}
	
	public boolean gethasLocker() {
		return hasLocker;
	}
	
	public void sethasLocker(boolean hasLocker) {
		this.hasLocker = hasLocker;
		
	}

	public void setInstructedClass(ArrayList<FitnessClass> instructedClass) {
		this.instructedClass = instructedClass;
	}
	
	
	public int compareTo(Instructor i) {
		int number;
		if (this.equals(i)) {
			return 0;}
		//instructors are ordered according to their name
		number = this.getName().compareTo(i.getName());
		if(number == 0) {//if name is the same, compare specialty
			number = this.getSpecialty().compareTo(i.getSpecialty());
		}
		return number;
	}
	
	@Override
	public String toString() {
		return "Instructor " + super.getName() + ", email=" + super.getEmail() +", specialty="+specialty;
	}
	
	@Override
	public void rentLockers() {
		if (hasLocker == false) {
			System.out.println("Attempt approved.");
			System.out.println(super.getName()+"'s locker number is " + lockerNum);
			sethasLocker(true);
			lockerNum += 1;
		}
		else {
			System.out.println(super.getName()+" already has a locker");
			
		}
	}

}
