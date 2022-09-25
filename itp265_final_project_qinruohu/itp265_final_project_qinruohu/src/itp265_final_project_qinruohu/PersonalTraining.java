package itp265_final_project_qinruohu;

/**
 * The PersonalTraining is a subclass of FitnessClass
 * @author Carol Hu
 * ITP 265, 20201, Tea Section
 * Date/Assignment
 * Week #
 * Email: qinruohu@usc.edu
 */
public class PersonalTraining extends FitnessClass {
	private String description;
	private Member participant;
	
	public PersonalTraining(String name, String time, Instructor instructor, String description) {
		super(name,time,instructor,false); //Personal Training is not included in membership
		this.description = description;
		this.participant = null;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Member getParticipant() {
		return participant;
	}
	
	public ClassType getType() {
		return ClassType.PersonalTraining;
	}
	
	@Override
	public boolean addParticipant(Member m) {
		boolean added = false;
		if(participant==null){
			this.participant = m;
			added = true;
		}
		return added;
	}
	
	@Override
	public boolean removeParticipant(Member m) {
		boolean isRemoved = false;
		if (!(participant == null)) {
			this.participant = null;
			isRemoved = true;
		}
		return isRemoved;
	}
	
	public String toString() {
		return super.toString()+ "\n   Description: "+description;
	}
	

	@Override
	public double calculateCost(Member m) {
		//if the customer is a paid member, he/she will enjoy a 80% discount
		//if the customer is a dropp-in customer, he/she will pay the full price of $59.99 each class
		double cost = 59.99;
		if(m.getClass() == PaidMember.class) {
			cost = 0.8*cost;
		}
		return cost;
	}

	@Override
	protected void viewParticipant() {
		System.out.println("The current participant is"+participant);
		
	}



}
