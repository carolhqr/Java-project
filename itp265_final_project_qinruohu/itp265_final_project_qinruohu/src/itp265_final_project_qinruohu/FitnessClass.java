package itp265_final_project_qinruohu;


/**
 * This is the abstract FitnessClass
 * @author Carol Hu
 * ITP 265, 20201, Tea Section
 * Final Project
 * Email: qinruohu@usc.edu
 */
public abstract class FitnessClass implements Comparable<FitnessClass> {
	private String name;
	private String time;
	private Instructor instructor;
	
	private boolean isincludedMembership; //indicate whether the class is included in membership
	
	public FitnessClass(String name, String time, Instructor instructor, boolean isincludedMembership) {
		this.name = name;
		this.time = time;
		this.instructor = instructor;
		this.isincludedMembership = isincludedMembership;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public boolean isIsincludedMembership() {
		return isincludedMembership;
	}

	public void setIsincludedMembership(boolean isincludedMembership) {
		this.isincludedMembership = isincludedMembership;
	}
	
	//these are the abstract methods which the subclasses of fitnessClass will implement
	public abstract double calculateCost(Member m); 
	//if the class is included in membership, the cost is 0 for paid member
	//if the class is not included in membership, paid member got a 80% discount
	
	protected abstract ClassType getType(); 
	
	protected abstract boolean addParticipant(Member m);
	
	protected abstract boolean removeParticipant(Member m);
	
	protected abstract void viewParticipant();
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((instructor == null) ? 0 : instructor.hashCode());
		result = prime * result + (isincludedMembership ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}
	
	public final int compareTo(FitnessClass f) {
		int number;
		if (this.equals(f)) {
			return 0;}
		//fitnessClass are ordered according to their name
		number = this.name.compareTo(f.name);
		if(number == 0) {//if name is the same, compare time
			number = this.time.compareTo(f.time);
		}
		return number;
		
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FitnessClass other = (FitnessClass) obj;
		if (instructor == null) {
			if (other.instructor != null)
				return false;
		} else if (!instructor.equals(other.instructor))
			return false;
		if (isincludedMembership != other.isincludedMembership)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}
	
	public String toString() {
		String s= "Name: "+this.name;
		if (isincludedMembership) {
			s += " (Included in membership)";
		}
		else {
			s += " (Not included in membership)";
		}
		s+= "\n   Time: "+this.time+"\n   Instructor: "+this.instructor.getName();
		return s;
	}
	
}
