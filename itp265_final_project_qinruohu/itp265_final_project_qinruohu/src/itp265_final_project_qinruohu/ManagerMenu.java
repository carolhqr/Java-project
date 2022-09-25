package itp265_final_project_qinruohu;

/**
 * This is the enum class of operator menu
 * @author Carol Hu
 * ITP 265, 20201, Tea Section
 * Final Project
 * Email: qinruohu@usc.edu
 */
public enum ManagerMenu {
	VIEW_INSTRUCTORS("View all instructors"),
	VIEW_MEMBERS("View all members"),
	RENT_LOCKER("Rent lockers for instructors"),
	ADD_CLASS("Add class"),
	QUIT("Quit");
	
	private String option;
	
	private ManagerMenu(String option) {
		this.option = option; 
	}
	
	public String DisplayOption() {
		return this.option;
	}
	
	public static String getMenuOptions() {
		String s = "****** Manager Menu ******";
		for(ManagerMenu o: ManagerMenu.values()) {
			s+= "\n" + o.ordinal() + ": " +o.DisplayOption();
		}
		return s;
	}
	
}
