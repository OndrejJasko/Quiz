package model;

import java.util.ArrayList;
import java.util.List;

import domain.Student;

/**
 * Class which contains list of students and methods used for manipulation with that list.
 * 
 * @author Marko Stevankovic
 * @author Martin Veres
 * @author Filip Stojkovic
 */
public class CollectionOfStudents {
	private static CollectionOfStudents instance;
	private List<Student> students;
	
	private CollectionOfStudents(){
		students = new ArrayList<>();
	}
	
	public static CollectionOfStudents getInstance(){
		if(instance == null)
			instance = new CollectionOfStudents();
		return instance;
	}
	
	public void addStudent(Student student){
		if(!students.contains(student))
			students.add(student);
	}
	
	public List<Student> getListOfStudents(){
		return students;
	}
}
