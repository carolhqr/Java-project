package itp265_final_project_qinruohu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This is the main system which allows for user interaction to schedule classes
 * @author Carol Hu
 * ITP 265, 20201, Tea Section
 * Final Project
 * Email: qinruohu@usc.edu
 */
public class CarolFitnessStudio {
	private FitnessCenter FitnessCenter;
	private Map<ClassType, List<FitnessClass>> classMap;
	private MemberLogin login;
	private Helper helper;
	private Member currentMember; //reference to the current member loggged in 
	
	
	/**
	 * The constructor will set up the classMap by reading from the class list file
	 * Will also set up login system which reads from a file of existing member information
	 */
	public CarolFitnessStudio() {
		FitnessCenter = new FitnessCenter();
		classMap = FitnessCenter.setUpFitnessClass(); 
		helper = new Helper();
		login = new MemberLogin(); 
		
	}
	
	
	public void run() {
		System.out.println("Welcome to Carol's Fitness Studio!");
		boolean isDone = false;
		while(!isDone) {
			int menuOption = helper.inputInt("Press 1 for Customer Menu and 2 for Manager Menu", 1, 2);
			if (menuOption == 1) {
				runCustomerMenu();
			}
			else{
				runManagerMenu();
			}
			isDone = helper.inputYesNoAsBoolean("Do you want to leave the system?(y/n)");
		}
		System.out.println("Thanks for using the reservation system. Goodbye!");
	}
	

	/**
	 * The operator menu is used for managing instructors and members
	 */
	private void runManagerMenu() {
		int choice = helper.inputInt(ManagerMenu.getMenuOptions(),0,ManagerMenu.values().length-1);
		ManagerMenu option = ManagerMenu.values()[choice];
		while(option != ManagerMenu.QUIT) {
			switch(option) {
			case VIEW_INSTRUCTORS:
				viewInstructors();
				break;
			case VIEW_MEMBERS:
				viewMembers();
				break;
			case RENT_LOCKER:
				rentLockerForInstructors();
				break;
			case ADD_CLASS:
				addNewClass();
				break;
			case QUIT:
				System.out.println("Goodbye");
				break;
			}
			helper.inputLine("Hit enter to continue");
			choice = helper.inputInt(ManagerMenu.getMenuOptions(), 0, ManagerMenu.values().length-1);
			option = ManagerMenu.values()[choice];
		}
		
	}

	
	/**
	 * The operator menu is used to interact with users
	 */
	private void runCustomerMenu() {
		int choice = helper.inputInt(CustomerMenu.getMenuOptions(), 0, CustomerMenu.values().length-1);//get the choice from the user
		CustomerMenu option = CustomerMenu.values()[choice]; //get the corresponding menu option
		while(option != CustomerMenu.QUIT) {
			switch(option) {
				case LOG_IN:
					memberLogin();
					break;
				case LOG_OUT:
					memberLogout();
					break;
				case VIEW_CLASS:
					printClassByCategory();
					break;
				case ADD_CLASS:
					addClass();
					break;
				case DROP_CLASS:
					removeClass();
					break;
				case VIEW_MYCLASS:
					viewMyClass();
					break;
				case VIEW_BILLING:
					viewMyBill();
					break;
				case RENT_A_LOCKER:
					rentLockerForMembers();
					break;
				case QUIT:
					quit();
					break;
			}
			helper.inputLine("Hit enter to continue");
			choice = helper.inputInt(CustomerMenu.getMenuOptions(), 0, CustomerMenu.values().length-1);
			option = CustomerMenu.values()[choice];
		}
	}





	/**
	 * This method allows the logged in member to view his/her bill, also ask if the user want to view details
	 */
	private void viewMyBill() {
		if (checkCurrentMember()) {
			calcualteBill();
			String s = ("Your total billing is ");
			s += String.format("$%.2f", currentMember.getBilling());
			System.out.println(s);
			boolean viewDetail = helper.inputYesNoAsBoolean("Do you want to view details of your bill?(y/n)");
			if(viewDetail) {
				viewDetailedBilling();
			}
		}
	}
	
	/**
	 * This method will print out the detailed information of the member's bill
	 */
	public void viewDetailedBilling() {
		//get the list of registered class associated with the member
		ArrayList<FitnessClass> registeredClass = currentMember.getRegisteredClass();
		String s = "Here is your detailed billing:\n";
		//if the current member is a paid member, he/she will have membership fee and might have locker rental fee
		if(currentMember.getClass().equals(PaidMember.class)) {
			PaidMember pm = (PaidMember)currentMember;
			s += String.format("$%.2f: ",pm.getMembershipFee());
			s += "Membership Fee\n";
			if (pm.gethasLocker()) {
				s += String.format("$%.2f: ", pm.getRentalFee());
				s += "Locker Rental Fee\n";
			}
		}
		//iterate through the list of class and get the cost of each item
		for(FitnessClass fc: registeredClass) {
			double cost = fc.calculateCost(currentMember); 
			s += String.format("$%.2f: ", cost);
			s += fc.getName();
			s += "\n";	
		}
		System.out.println(s);
	}
		

	/**
	 * This method will calculate the total billing for the current customer
	 */
	private void calcualteBill() {
		ArrayList<FitnessClass> registeredClass = currentMember.getRegisteredClass();
		//iterate through the registered class list of current member and calculate the cost
		for (FitnessClass fc: registeredClass) {
			double cost = fc.calculateCost(currentMember);
			currentMember.addBilling(cost);
		}
	}

	/**
	 * This method will log out the current member
	 */
	private void memberLogout() {
		if (currentMember != null) {
			boolean confirm = helper.inputYesNoAsBoolean("Are you sure to log out?(y/n)");
			if (confirm) {
				System.out.println("Logged "+ currentMember.getName() +" out of the system");
				currentMember = null;
			}
		}
		else {
			System.out.println("Attempt failed. There is no current member logged in.");
		}
		
		
	}

	/**
	 * This method will display the class current member has registered
	 */
	private boolean viewMyClass() {
		boolean hasClass = true;
		if(checkCurrentMember()) {
			if(!currentMember.getRegisteredClass().isEmpty()){
				String s = currentMember.viewClass();
				System.out.println(s);
			}
			else {
				System.out.println("You haven't registered for any class yet.");
				hasClass = false;
			}
		}
		return hasClass;
	}

	/**
	 * This method handles scheduling classes
	 * 
	 */
	private void addClass() {
		if (checkCurrentMember()) {
			displayCurrentMember();
			System.out.println("What type of classes are you interested in scheduling today?");
			FitnessClass fc = chooseClass();
			boolean add = fc.addParticipant(currentMember);
			if (add) {
				System.out.println("You have successfully scheduled the class.");
				currentMember.addClass(fc);
			}
		}
		
	}
	
	/**
	 * This method handles dropping classes
	 * 
	 */
	private void removeClass() {
		if (checkCurrentMember()) {
			displayCurrentMember();
			if(viewMyClass()){//check if the current member has registered for any class
				FitnessClass fc = chooseClass(currentMember);
				boolean remove = fc.removeParticipant(currentMember); //return whether the member is removed from the class list
				if (remove) {
					System.out.println("You have cancelled the class.");
					currentMember.dropClass(fc);
				}
			}	
		}
	}
	
	
	
	/**
	 * This method will print out all classes offered in a friendly way
	 */
	private void printClassByCategory() {
		boolean hasCurrentMember = checkCurrentMember();
		if(hasCurrentMember) {
			for(ClassType key: classMap.keySet()) {
				System.out.println(key+":");
				List<FitnessClass> classList = classMap.get(key);
				int number = 1;
				Collections.sort(classList);
				for(FitnessClass fc: classList) {
					System.out.println(number + ": "+fc);
					number+=1;
					System.out.println();
				}
				System.out.println();
			}
		}
	}

	//this method handles member login
	private void memberLogin() {
		if(currentMember == null) {
			String choice = helper.inputWord("Would you like to sign in or create a new account? (signin/create)","signin", "create");
			if (choice.equalsIgnoreCase("signin")) {
				currentMember = login.memberSignin();			
			}
			else {
				currentMember = login.createMember();
			}
		}
		else {
			System.out.println("Please log out first to sign in to another account.");
		}
	}

	
	//This method will display classes of specific type and ask the user to choose
	public FitnessClass chooseClass(){
		ClassType type;
		int i = 1;
		int choice = helper.inputInt("Enter 1 for Group Class or 2 for Personal Training", 1, 2);
		if(choice==1) {
			type = ClassType.GroupExercise;
		}
		else {
			type = ClassType.PersonalTraining;
		}
		List<FitnessClass> list = classMap.get(type); //get the list the user chooses		
		
		for(FitnessClass fc: list) {
			System.out.println(i+". "+fc);
			i += 1;
		}
		int num = helper.inputInt("Which class would you like to choose?", 1, list.size());
		return list.get(num-1);
	}
	
	public FitnessClass chooseClass(Member m) {
		ArrayList<FitnessClass> list = m.getRegisteredClass();
		int choice = helper.inputInt("Which class would you like to cancel today?");
		FitnessClass fc = list.get(choice-1);
		return fc;
	}
	
	public void displayCurrentMember(){
		System.out.println("Current member logged in is: "+currentMember.getName());
		
	}
	
	public boolean checkCurrentMember() {
		boolean hasCurrentMember = false;
		if (currentMember == null) {
			System.out.println("Please log in first.");
		}
		else {
			hasCurrentMember = true;
		}
		return hasCurrentMember;
	}
		
	

	/**
	 * This method handles renting lockers for paid members
	 */
	private void rentLockerForMembers() {
		boolean hasCurrentMember = checkCurrentMember();
		if(hasCurrentMember) {
			if (currentMember.getClass().equals(PaidMember.class)) { //only paid member could rent a locker
				PaidMember pm = (PaidMember)currentMember;
				boolean confirm = helper.inputYesNoAsBoolean("Do you want to rent a locker for $" + pm.getRentalFee()+"?(y/n)");
				if (confirm) {
					pm.rentLockers();
				}
			}
			else {
				System.out.println("Sorry, but only paid members could rent a locker");
			}
		}
		
	}

	
	/**
	 * This method handles renting a locker for specific instructor
	 */
	private void rentLockerForInstructors() {
		Instructor i = chooseInstructor();
		i.rentLockers();
	}

	/**
	 * This method allows the manager to choose an instructor
	 */
	private Instructor chooseInstructor() {
		ArrayList<Instructor> list = FitnessCenter.getInstructorList(); //get the instructor list
		String s = "Please choose an instructor: ";
		int i = 1;
		for (Instructor in: list) {
			s += "\n";
			s += i;
			s += ". ";
			s += in.getName();
			i += 1;
		}
		int choice = helper.inputInt(s, 1, list.size());
		Instructor in = list.get(choice-1);
		return in;
	}

	private void viewMembers() {
		System.out.println("Here is the information of all members at Carol's Fitness:");
		login.viewAllMembers();
	}


	private void viewInstructors() {
		System.out.println("Here is the information of all instructors at Carol's Fitness:");
		System.out.println(FitnessCenter.viewInstructor());
	}
	
	private void addNewClass() {
		System.out.println("This method will allow managers to manually add new classes to the schedule.");
	}


	private void quit() {
		if (!(currentMember == null)){ //check if the member has logged out
			currentMember = null;
		}
		System.out.println("Goodbye.");
	}
		
	public static void main(String[] args) {
		CarolFitnessStudio studio = new CarolFitnessStudio();
		studio.run();
	}
	
}
