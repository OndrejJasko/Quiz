package database_connection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;

import configuration.DatabaseConfiguration;
import controller.Controller;
import domain.Administrator;
import domain.Answer;
import domain.Question;
import domain.Student;

/**
 * @author Martin Veres
 * @author Filip Stojkovic
 * @author Marko Stevankovic
 * 
 * Class in which are defined all methods used in program for work with database
 */
public class MySQLConnection {
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	
	String url = DatabaseConfiguration.getInstance().getURL();
	String user = DatabaseConfiguration.getInstance().getUsername();
	String password = DatabaseConfiguration.getInstance().getPassword();
	
	/**
	 * Method which check whether user is Administrator or nor
	 * 
	 * @param administrator, user who is trying to log in
	 * 
	 * @return boolean, returns true if such users exists in administrator table and
	 * 					returns false if not.
	 * 
	 * @throws Exception, when Driver is not properly loaded
	 * 
	 * @throws SQLException, an exception that provides information
	 * 						 on a database access error or other errors.
	 */
	public boolean isAdmin(Administrator administrator){
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception exc){
			Controller.showExceptionErrorPane(exc);
		}
		
		try{
			connection = DriverManager.getConnection(url, user, password);
						
			String sql = "SELECT * FROM administrator "
						+ "WHERE first_name = ? "
						+ "AND last_name = ? "
						+ "AND password = ?";
			
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, administrator.getFirstName());
			preparedStatement.setString(2, administrator.getLastName());
			preparedStatement.setString(3, administrator.getPassword());
			
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next())
				return true;
			
		}catch(SQLException exc){
			Controller.showSQLExceptionErrorPane(exc);
		}finally{
			if(resultSet != null){
				try {
					resultSet.close();
				} catch (SQLException exc) {
					Controller.showSQLExceptionErrorPane(exc);
				}
			}
			if(preparedStatement != null){
				try {
					preparedStatement.close();
				} catch (SQLException exc) {
					Controller.showSQLExceptionErrorPane(exc);
				}
			}
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException exc) {
					Controller.showSQLExceptionErrorPane(exc);
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Method which loads all students from database into
	 * global list of Students, model.CollectionOfStudents.getInstance().getListOfStudent()
	 * 
	 * @return List<Student>
	 * 
	 * @throws Exception, when Driver is not properly loaded
	 * 
	 * @throws SQLException, an exception that provides information
	 * 						 on a database access error or other errors.
	 */
	public List<Student> loadStudentsFromDB(){
		List<Student> students = null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception exc){
			Controller.showExceptionErrorPane(exc);
		}
		
		try{
			connection = DriverManager.getConnection(url, user, password);
			String sql = "SELECT * FROM student ORDER BY result DESC";
			preparedStatement = connection.prepareStatement(sql);
			
			resultSet = preparedStatement.executeQuery();
			
			students = new ArrayList<>();
			
			while(resultSet.next()){
				int indexNumber = resultSet.getInt("index_number");
				int enrollmentYear = resultSet.getInt("enrollment_year");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				int score = resultSet.getInt("result");
				String password = resultSet.getString("password");
				
				Student student = new Student(indexNumber, enrollmentYear ,firstName, lastName, password, score);
				
				students.add(student);
			}
			
		}catch(SQLException exc){
			exc.printStackTrace();
		}finally{
			if(resultSet != null){
				try {
					resultSet.close();
				} catch (SQLException exc) {
					Controller.showSQLExceptionErrorPane(exc);
				}
			}
			
			if(preparedStatement != null){
				try {
					preparedStatement.close();
				} catch (SQLException exc) {
					Controller.showSQLExceptionErrorPane(exc);
				}
			}
			
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException exc) {
					Controller.showSQLExceptionErrorPane(exc);
				}
			}
		}
		
		return students;
	}
	
	/**
	 * Method which saves List of students (global list of students)
	 * into database.
	 * 
	 * @param students
	 * 				List<Student>
	 * 
	 * @throws Exception, when Driver is not properly loaded
	 * 
	 * @throws SQLException, an exception that provides information
	 * 						 on a database access error or other errors.
	 */
	public void saveStudentsToDatabase(List<Student> students){
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception exc){
			Controller.showExceptionErrorPane(exc);
		}
		
		try{
			connection = DriverManager.getConnection(url, user, password);
			String sql = "INSERT into student VALUES(?, ?, ?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(sql);
						
			for(Student student : students){
				preparedStatement.setInt(1, student.getIndexNumber());
				preparedStatement.setString(2, student.getFirstName());
				preparedStatement.setString(3, student.getLastName());
				preparedStatement.setInt(4, student.getEnrollmentYear());
				preparedStatement.setString(5, student.getPassword());
				preparedStatement.setInt(6,  student.getResult());
				
				preparedStatement.executeUpdate();
			}
			
			JOptionPane.showMessageDialog(null, "Students sucessfully saved to Database!");
			
		}catch(SQLException exc){
			Controller.showSQLExceptionErrorPane(exc);
		}finally{
			if(preparedStatement != null){
				try {
					preparedStatement.close();
				} catch (SQLException exc) {
					Controller.showSQLExceptionErrorPane(exc);
				}
			}
			
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException exc) {
					Controller.showSQLExceptionErrorPane(exc);
				}
			}
		}
	}
	
	/**
	 * Method which saves List of questions (global list of questions)
	 * into database.
	 * 
	 * @param questions
	 * 				List<Question>
	 * 
	 * @throws Exception, when Driver is not properly loaded
	 * 
	 * @throws SQLException, an exception that provides information
	 * 						 on a database access error or other errors.
	 */
	public void saveQuestionsToDatabase(List<Question> questions){
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception exc){
			Controller.showExceptionErrorPane(exc);
		}
		
		try{
			connection = DriverManager.getConnection(url, user, password);
			String sql = "INSERT INTO question VALUES(null, ?)";
			
			String sql1 = "INSERT INTO answer VALUES(null, ?, ?, (SELECT id_question FROM question WHERE description = ?))";
			
			preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			PreparedStatement preparedStatement1 = connection.prepareStatement(sql1, PreparedStatement.RETURN_GENERATED_KEYS);
					
			for(Question question : questions){
				preparedStatement.setString(1, question.getDescription());
				preparedStatement.executeUpdate();
				for(Answer answer : question.getAnswers()){
					preparedStatement1.setString(1, answer.getAnswerText());
					preparedStatement1.setBoolean(2, answer.isCorrect());
					preparedStatement1.setString(3, question.getDescription());
					preparedStatement1.executeUpdate();
				}
			}
			
			preparedStatement1.close();
			
			JOptionPane.showMessageDialog(null, "Questions sucessfully saved to Database!");
			
		}catch(SQLException exc){
			Controller.showSQLExceptionErrorPane(exc);
		}finally{
			if(preparedStatement != null){
				try {
					preparedStatement.close();
				} catch (SQLException exc) {
					Controller.showSQLExceptionErrorPane(exc);
				}
			}
			
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException exc) {
					Controller.showSQLExceptionErrorPane(exc);
				}
			}
		}
	}

	/**
	 * Method which loads all questions from database into the
	 * global list of questions, model.CollectionOfQuestions.getInstance().getAllQuestions()
	 * 
	 * @return List<Questions>
	 * 
	 * @throws Exception, when Driver is not properly loaded
	 * 
	 * @throws SQLException, an exception that provides information
	 * 						 on a database access error or other errors.
	 */
	public List<Question> loadQuestionsFromDB(){
		
		List<Question> questions = null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception exc){
			Controller.showExceptionErrorPane(exc);
		}
		
		try{
			connection = DriverManager.getConnection(url, user, password);
			
			String sql = "SELECT * FROM question";
			String getAnswerSQL = "SELECT * FROM answer WHERE id_question=?";
			
			PreparedStatement innerPreparedStatement = null;
			ResultSet innerResultSet = null;
			
			Answer[] answers = new Answer[4];
			int counter;
					
			preparedStatement = connection.prepareStatement(sql);
			innerPreparedStatement = connection.prepareStatement(getAnswerSQL);
			
			resultSet = preparedStatement.executeQuery();
			
			questions = new ArrayList<>();
			
			while(resultSet.next()){
				
				int id_question = resultSet.getInt("id_question");
				String desc = resultSet.getString("description");
				
				counter = 0;
				
				innerPreparedStatement.setInt(1, id_question);
				innerResultSet = innerPreparedStatement.executeQuery();
				
				while(innerResultSet.next()){
					
					String answerText = innerResultSet.getString("answer_text");
					boolean isCorrect = innerResultSet.getBoolean("is_correct");
					
					 answers[counter++] = new Answer(answerText, isCorrect);
				}
				
				Question question = new Question(desc, answers[0], answers[1], answers[2], answers[3]);
				
				questions.add(question);
			}
			
			innerResultSet.close();
			innerPreparedStatement.close();
			
		}catch(SQLException exc){
			exc.printStackTrace();
		}finally{
			if(resultSet != null){
				try {
					resultSet.close();
				} catch (SQLException exc) {
					Controller.showSQLExceptionErrorPane(exc);
				}
			}
			
			if(preparedStatement != null){
				try {
					preparedStatement.close();
				} catch (SQLException exc) {
					Controller.showSQLExceptionErrorPane(exc);
				}
			}
			
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException exc) {
					Controller.showSQLExceptionErrorPane(exc);
				}
			}
		}
		
		return questions;
	}
	
	/**
	 * Method which check whether user is Administrator or nor
	 *
	 * @param student, user who is trying to log in
	 *
	 * @return boolean, returns true if such users exists in administrator table and
	 * 					returns false if not.
	 * 
	 * @throws Exception, when Driver is not properly loaded
	 * 
	 * @throws SQLException, an exception that provides information
	 * 						 on a database access error or other errors.
	 */
	public boolean isStudent(Student student){
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception exc){
			Controller.showExceptionErrorPane(exc);
		}
		
		try{
			connection = DriverManager.getConnection(url, user, password);
						
			String sql = "SELECT * FROM student"
					+ " WHERE index_number = ?"
					+ " AND enrollment_year = ?"
					+ " AND password = ?";
			
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, student.getIndexNumber());
			preparedStatement.setInt(2, student.getEnrollmentYear());
			preparedStatement.setString(3, student.getPassword());
			
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next())
				return true;
			
		}catch(SQLException exc){
			Controller.showSQLExceptionErrorPane(exc);
		}finally{
			if(resultSet != null){
				try {
					resultSet.close();
				} catch (SQLException exc) {
					Controller.showSQLExceptionErrorPane(exc);
				}
			}
			if(preparedStatement != null){
				try {
					preparedStatement.close();
				} catch (SQLException exc) {
					Controller.showSQLExceptionErrorPane(exc);
				}
			}
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException exc) {
					Controller.showSQLExceptionErrorPane(exc);
				}
			}
		}
		
		return false;
	}

	/**
	 * Method that updates student's newest result
	 * 
	 * @param student, student who has taken an exam
	 */
	public void updateResultOfStudentToDB(Student student) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception exc){
			Controller.showExceptionErrorPane(exc);
		}
		
		try{
			connection = DriverManager.getConnection(url, user, password);
						
			String sql = "UPDATE student"
					+ " SET result = ?"
					+ " WHERE index_number = ?"
					+ " AND enrollment_year = ?";
			
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, student.getResult());
			preparedStatement.setInt(2, student.getIndexNumber());
			preparedStatement.setInt(3, student.getEnrollmentYear());
			
			int numberOfAffectedRows = preparedStatement.executeUpdate();
			
			JOptionPane.showMessageDialog(
					null,
					"Score/Result successfully updated to new score!\n"
					+ "Number of students affected by this change: "
					+ numberOfAffectedRows + "\n");
				
		}catch(SQLException exc){
			Controller.showSQLExceptionErrorPane(exc);
		}finally{
			if(preparedStatement != null){
				try {
					preparedStatement.close();
				} catch (SQLException exc) {
					Controller.showSQLExceptionErrorPane(exc);
				}
			}
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException exc) {
					Controller.showSQLExceptionErrorPane(exc);
				}
			}
		}
	}
}
