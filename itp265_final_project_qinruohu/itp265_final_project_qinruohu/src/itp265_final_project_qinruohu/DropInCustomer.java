package itp265_final_project_qinruohu;

/**
 * Description of code
 * @author Carol Hu
 * ITP 265, 20201, Tea Section
 * Final Project
 * Email: qinruohu@usc.edu
 */
public class DropInCustomer extends Member{

	
	public DropInCustomer(String name, String email, String password, String question, String answer) {
		super(name,email,password,question,answer);
	}
	
	public DropInCustomer(Member m) {
		this(m.getName(),m.getEmail(),m.getPassword(),m.getQuestion(),m.getAnswer());
	}
	
	public String toString() {
		return "Drop in Customer: " + super.toString();
	}
		
	

}
