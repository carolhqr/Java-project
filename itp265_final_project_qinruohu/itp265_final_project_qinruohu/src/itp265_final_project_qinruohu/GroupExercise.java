package itp265_final_project_qinruohu;

import java.util.ArrayList;

/**
 * The GroupExercise is another subclass of FitnessClass
 * @author Carol Hu
 * ITP 265, 20201, Tea Section
 * Final Project
 * Email: qinruohu@usc.edu
 */
public class GroupExercise extends FitnessClass{
	private Level difficulty;
	private int size; // this is the size of each class
	private ArrayList<Member> participantList;
	
	public GroupExercise(String name, String time, Instructor instructor, Level difficulty, int size) {
		super(name,time,instructor,true);//group exercise is included in membership
		this.difficulty = difficulty;
		this.size = size;
		participantList = new ArrayList<Member>(size);
		
	}

	public Level getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Level difficulty) {
		this.difficulty = difficulty;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public ClassType getType() {
		return ClassType.GroupExercise;
	}
	
	public String toString() {
		return super.toString()+ "\n   Difficulty: "+difficulty+"\n   Size: "+size;
	}
	
	//this method will add participant to the class
	@Override
	public boolean addParticipant(Member m) {
		boolean added = false;
		if(!(participantList.contains(m))) { //check if the member has already registered for this class
			if(participantList.size() < size) {
				participantList.add(m);
				added = true;
			}
			else {
				System.out.println("Sorry this class is full. Please check for other classes");
			}
		}
		else {
			System.out.println("Sorry, but you have already registered for this class.");
		}
		return added;
	}
	
	@Override
	public boolean removeParticipant(Member m) {
		boolean removed = false;
		if(participantList.contains(m)) {//if the member is on the list
			participantList.remove(m);
			removed = true;
		}
		else {
			System.out.println("Attempt failed. You haven't reserved for this class.");
		}
		return removed;
	}
	
	public void viewParticipant() {
		System.out.println("Here are the participants for this class:");
		for (Member m: participantList) {
			System.out.println(m);
		}
	}
	
	
	@Override
	public double calculateCost(Member m) {
		//if the person is a member, he/she could take the class for free
		//if the person is not a member, he/she will have to pay $19.99 for each class
		double cost = 19.99;
		if(m.getClass() == PaidMember.class) {
			cost = 0;
		}
		return cost;
	}
	
	
	
	

}
