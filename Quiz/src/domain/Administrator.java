package domain;

public class Administrator {
	private String firstName;
	private String lastName;
	private String password;
	
	public Administrator(String firstName, String lastName, String password){
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setPassword(password);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if(firstName == null)
			throw new RuntimeException("First name cannot be null value...");
		
		if("".equals(firstName.trim()))
			throw new RuntimeException("First name cannot be an empty String....");
		
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if(lastName == null)
			throw new RuntimeException("Last name cannot be null value...");
		
		if("".equals(lastName.trim()))
			throw new RuntimeException("Last name cannot be an empty String....");
		
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if(password == null)
			throw new RuntimeException("Password cannot be null value...");
		
		if("".equals(password.trim()))
			throw new RuntimeException("Password cannot be an empty String....");
		
		this.password = password;
	}

	@Override
	public String toString() {
		return this.getFirstName().toLowerCase().trim() +
				"." + 
				this.getLastName().toLowerCase().trim();
	}	
}
