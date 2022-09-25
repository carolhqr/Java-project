package itp265_final_project_qinruohu;

/**
 * This is the Customer Menu enum class
 * @author Carol Hu
 * ITP 265, 20201, Tea Section
 * Final Project
 * Email: qinruohu@usc.edu
 */
public enum CustomerMenu {
	LOG_IN("Sign in or create account"),
	LOG_OUT("Log out of account"),
	VIEW_CLASS("View all classes"),
	ADD_CLASS("Schedule a class"),
	DROP_CLASS("Cancel a class reservation"),
	VIEW_MYCLASS("View my class schedule"),
	VIEW_BILLING("View my current billing"),
	RENT_A_LOCKER("Rent a locker"),
	QUIT("Quit");
	
	private String option;
	
	private CustomerMenu(String option) {
		this.option = option;
	}
	
	public String DisplayOption() {
		return this.option;
	}
	
		
	public static String getMenuOptions() {
		String s = "****** Customer Menu ******";
		for(CustomerMenu m: CustomerMenu.values()) {
			s+= "\n" + m.ordinal() + ": " +m.DisplayOption();
		}
		return s;
	}
}
