package itp265_final_project_qinruohu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * This program will read from the fitness class files
 * @author Carol Hu
 * ITP 265, 20201, Tea Section
 * Final Project
 * Email: qinruohu@usc.edu
 */
public class FitnessCenter {
	private static ArrayList<Instructor> instructors;
	private static final String PERTRAINING_FILE = "src/PersonalTraining.txt";
	private static final String GROEXERCISE_FILE= "src/GroupExercise.txt";
	
	
	//the information of instructor will be hardcoded here
	public void setUpInstructor(){
		instructors = new ArrayList<>();
		Instructor i1 = new Instructor("Claire","claire@fitness.com","Yoga");
		Instructor i2 = new Instructor("Alex","alex@fitness.com","Dancing");
		Instructor i3 = new Instructor("Catherine","catherine@fitness.com","Yoga");
		Instructor i4 = new Instructor("Robert","robert@fitness.com","Weight Training");
		instructors.add(i1);
		instructors.add(i2);
		instructors.add(i3);
		instructors.add(i4);
		
	}
	
	public Map<ClassType, List<FitnessClass>> setUpFitnessClass(){
		
		setUpInstructor(); //create an arraylist of instructors;
		Map<ClassType, List<FitnessClass>> classMap = new HashMap<>(); //keys are ClassTypes
		//initialize the map with keys going to empty lists
		for(ClassType type: ClassType.values()) {
			List<FitnessClass> classList = new ArrayList<>();
			classMap.put(type,classList);
		}
		readFile(PERTRAINING_FILE,classMap); 
		readFile(GROEXERCISE_FILE,classMap);
		return classMap;
	}
	
	private static void readFile(String file, Map<ClassType, List<FitnessClass>> map) {
		try(FileInputStream fis = new FileInputStream(file); 
				Scanner sc = new Scanner(fis)){
			if(sc.hasNextLine()) {
				String header = sc.nextLine(); //skip the header
				while(sc.hasNextLine()) {
					String line = sc.nextLine(); 
					FitnessClass fc = parseStringIntoClass(line); // turns each line into a fitnessclass
					if(fc != null) {
						addClassToMap(fc,map);
					}
				}
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("File couldn't be found.");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		

	
	private static void addClassToMap(FitnessClass fc, Map<ClassType, List<FitnessClass>> map) {
		//add the class to the map of classes
		ClassType type = fc.getType();
		List<FitnessClass> classList = map.get(type);
		classList.add(fc);
		
	}


	private static FitnessClass parseStringIntoClass(String line) {
		FitnessClass fc = null;
		try {
			Scanner sc = new Scanner(line);
			sc.useDelimiter(",");
			String type = sc.next();
			String rest = sc.nextLine();
			if (type.equalsIgnoreCase("PersonalTraining")) {
				fc = parseTraining(rest);
			}
			else {
				fc = parseExercise(rest);
			}
			sc.close();
		}
		catch(Exception e){
			System.err.println("Error reading line: " + line + "\nerror; " + e);
		}

		return fc;
	}

	//this method will read the Personal Training file and create PersonalTraining objects
	private static PersonalTraining parseTraining(String restline) {
		PersonalTraining pt = null;
		try {
			Scanner sc = new Scanner(restline);
			sc.useDelimiter(",");
			String name = sc.next();
			String time = sc.next();
			String instructorName = sc.next();
			Instructor instructor = parseInstructor(instructorName);
			String description = sc.next();
			pt = new PersonalTraining(name,time,instructor, description);
		}
		catch(Exception e) {
			System.err.println("Error reading line: " + restline + "\nerror; " + e);
		}
		return pt;
	}
	
	private static GroupExercise parseExercise(String restline) {
		GroupExercise ge = null;
		try {
			Scanner sc = new Scanner(restline);
			sc.useDelimiter(",");
			String name = sc.next();
			String time = sc.next();
			String instructorName = sc.next();
			Instructor instructor = parseInstructor(instructorName);
			String level = sc.next();
			int size = sc.nextInt();
			Level difficulty = parseLevel(level);
			ge = new GroupExercise(name,time,instructor,difficulty,size);
		}
		catch(Exception e) {
			System.err.println("Error reading line: " + restline + "\nerror; " + e);
		}
	
		return ge;
	}

	//this method will match the string in the file with corresponding difficulty level in the enum class
	private static Level parseLevel(String level) {
		Level difficulty = Level.EASY;
		for(Level l: Level.values()) {
			if(l.toString().equalsIgnoreCase(level)){//compare each value in the enum class with the String passed in
				difficulty = l;
			}
		}
		return difficulty;
	}
	
	//this method will match the name of the instructor in the file with corresponding instructor object in the array list
	private static Instructor parseInstructor(String i) {
		Instructor instructor = instructors.get(0);
		for(Instructor ins: instructors) {
			String name = ins.getName();
			if(i.equals(name)) {
				instructor = ins;
			}
		}
		return instructor;
	}
	
	
	public ArrayList<Instructor> getInstructorList() {
		return instructors;
	}
	
	public String viewInstructor() {
		Collections.sort(instructors);
		String s ="";
		int i = 1;
		for (Instructor trainer: instructors) {
			s += i;
			s += ". ";
			s += trainer;
			s += "\n";
			i += 1;
		}
		return s;
	}

}
