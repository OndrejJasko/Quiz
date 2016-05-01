package domain;

import java.util.Calendar;
import java.util.Date;

public class Student implements Comparable<Student>{
	private int indexNumber;
	private int enrollmentYear;
	private String firstName;
	private String lastName;
	private String password;
	private int result;
	
	public Student(int indexNumber, int enrollmentYear ,String firstName, String lastName, String password, int result){
		this.setIndexNumber(indexNumber);
		this.setEnrollmentYear(enrollmentYear);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setPassword(password);
		this.setResult(result);
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if(firstName == null)
			throw new NullPointerException("First name cannot be null value...");
		
		if("".equals(firstName.trim()))
			throw new RuntimeException("First name cannot be an empty String....");
		
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if(lastName == null)
			throw new NullPointerException("Last name cannot be null value...");
		
		if("".equals(lastName.trim()))
			throw new RuntimeException("Last name cannot be an empty String....");
		
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if(password == null)
			throw new NullPointerException("Password cannot be null value...");
		
		if("".equals(password.trim()))
			throw new RuntimeException("Password cannot be an empty String....");
		
		this.password = password;
	}

	public String getIndex() {
		return enrollmentYear + "/" + indexNumber;
	}

	public int getIndexNumber() {
		return indexNumber;
	}

	public void setIndexNumber(int indexNumber) {
		if(indexNumber < 0)
			throw new RuntimeException("Index number has to be a positive integer!");
		
		this.indexNumber = indexNumber;
	}

	public int getEnrollmentYear() {
		return enrollmentYear;
	}

	public void setEnrollmentYear(int enrollmentYear) {
		if(enrollmentYear < 2000 || enrollmentYear > Calendar.getInstance().get(Calendar.YEAR))
			throw new RuntimeException("Invalid value for enrollmentYear attribute!");
		
		this.enrollmentYear = enrollmentYear;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
	
	public void increaseResult(){
		this.setResult(getResult() + 1);
	}
	
	public void decreaseResult(){
		this.setResult(getResult() - 1);
	}

	@Override
	public String toString() {
		return this.getLastName()
				+ "."
				+ this.getFirstName()
				+ "."
				+ this.getIndex();
	}
	
	public String toStringResult(){
		return  getIndex() + 
				" " +
				getLastName() + 
				" " + 
				getFirstName() + 
				": " + 
				getResult() + 
				" points";
	}

	@Override
	public int compareTo(Student student) {
		if(getResult() > student.getResult())
			return 1;
		
		else if(getResult() < student.getResult())
			return -1;
		
		return 0;
	}
}
