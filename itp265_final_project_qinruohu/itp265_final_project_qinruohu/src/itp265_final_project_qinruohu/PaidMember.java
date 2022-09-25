package itp265_final_project_qinruohu;

import java.util.ArrayList;

/**
 * This is the class which extends from Member
 * @author Carol Hu
 * ITP 265, 20201, Tea Section
 * Date/Assignment
 * Week #
 * Email: qinruohu@usc.edu
 */
public class PaidMember extends Member implements rentLocker{

	private ArrayList<FitnessClass> waitlist; //not utilized
	private static double membership_fee = 79.99;
	private static double locker_fee = 19.99;
	private boolean hasLocker;
	private static int lockerNum = 101; //members' locker number starts from 101
	
	public PaidMember(String name, String email, String password, String question, String answer) {
		super(name, email, password, question, answer, membership_fee); //billing includes the initial membership fee of $79.99
		this.hasLocker = false;
		waitlist = new ArrayList<FitnessClass>();
	}
	
	public PaidMember(Member m) {
		this(m.getName(),m.getEmail(),m.getPassword(),m.getQuestion(),m.getAnswer());

	}
	
	public void addToWaitlist(FitnessClass f) {
		waitlist.add(f);
	}
	
	public double getMembershipFee() {
		return membership_fee;
	}
	
	public double getRentalFee() {
		return locker_fee;
	}
	
	public boolean gethasLocker() {
		return hasLocker;
	}
	
	public void sethasLocker(boolean hasLocker) {
		this.hasLocker = hasLocker;
		
	}
	
	public String toString() {
		return "Paid Member: " + super.toString();
	}

	@Override
	public void rentLockers() {
		if (hasLocker == false) { //each member could only rent ONE locker
			int rentalFee = 20;
			System.out.println("Your locker number is " + lockerNum);
			super.addBilling(rentalFee);
			sethasLocker(true);
			lockerNum += 1;
		}
		else {
			System.out.println("You have already rent a locker");
		}
	}

}
