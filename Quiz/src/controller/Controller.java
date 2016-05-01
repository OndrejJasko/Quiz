package controller;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mysql.jdbc.integration.c3p0.MysqlConnectionTester;

import database_connection.MySQLConnection;
import domain.Administrator;
import domain.Answer;
import domain.Question;
import domain.Student;
import model.CollectionOfQuestions;
import model.CollectionOfStudents;
import view.admin.FrameAddNewAdministrator;
import view.admin.FrameAddNewStudent;
import view.admin.FrameAdministrator;
import view.admin.FrameCheckResults;
import view.admin.QuestionFrame;
import view.student.QuizFrame;

/**
 * @author Martin Veres
 * @author Filip Stojkovic
 * @author Marko Stevankovic
 * 
 * Class which represents work of Controller, idea from MVC pattern 
 */
public class Controller {
	
	/**
	 * Method which performs login operation
	 * 
	 * @param username, representing username of the user
	 * @param pass, representing password of the user
	 */
	public static void loginIn(String username, char[] pass){
		MySQLConnection dao = new MySQLConnection();
		
		String[] tmp = null;
		String lastName = "";
		String firstName = "";
		
		try{
			tmp = username.split("\\.");
			lastName = tmp[0];
			firstName = tmp[1];
		} catch(Exception exc){
			JOptionPane.showMessageDialog(
					null, 
					"Username is INCORRECT!\n"
					+ "For admin: lastName.firstName \n"
					+ "For students: lastName.firstName.index\n",
					"ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
		
		String password = new String(pass);
		String index = "";
		int indexNumber = 0;
		int enrollmentYear = 0;
		
		try{
			index = tmp[2];
			String[] tmp1 = index.split("/");
			indexNumber = Integer.parseInt(tmp1[1]);
			enrollmentYear = Integer.parseInt(tmp1[0]);
		}catch(ArrayIndexOutOfBoundsException exc){
			index = null;
		}
		
		if(index == null){
			try{
				Administrator administrator = new Administrator(firstName, lastName, password);
				if(dao.isAdmin(administrator)){
					Controller.showAdministratorFrame(administrator.getFirstName());
				}else{
					errorInSigningIn();
				}
			} catch(Exception exc){
				Controller.showExceptionErrorPane(exc);
			}
		}
		else{
			try{
				Student student = new Student(indexNumber, enrollmentYear, firstName, lastName, password, 0 );
				if(dao.isStudent(student)){
					Controller.showQuizFrame(student);
				}else{
					errorInSigningIn();
				}
			} catch(Exception exc){
				Controller.showExceptionErrorPane(exc);
			}
		}
	}

	/**
	 * If user has not entered required information properly, error dialog shows.
	 * Or if user does not exist in database.
	 */
	private static void errorInSigningIn() {
		JOptionPane.showMessageDialog(
				null,
				"User NOT found.\nTry again!\nCheck your spelling\n",
				"ERROR",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * If the administrator login was succesfull,
	 * administrator form is displayed to Administrator
	 */
	public static void showAdministratorFrame(String firstName) {
		FrameAdministrator adminFrame = new FrameAdministrator(firstName);
		adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		adminFrame.setLocationRelativeTo(null);
		adminFrame.setVisible(true);
	}

	/**
	 * If the student login was succesfull, 
	 * QuizForm is displayed to Student
	 */
	private static void showQuizFrame(Student student) {
		QuizFrame quizFrame = QuizFrame.getInstance(student);
		quizFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		quizFrame.setLocationRelativeTo(null);
		quizFrame.setVisible(true);
	}

	/**
	 * Method for displaying to Aministrator new form, which Aministrator can use
	 * to add new student to a student list 
	 */
	public static void showAddNewStudentFrame() {
		FrameAddNewStudent addStudentFrame = new FrameAddNewStudent();
		addStudentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addStudentFrame.setLocationRelativeTo(null);
		addStudentFrame.setVisible(true);
	}

	/**
	 * Method for displaying help dialog, which informs Administrator how to add new student
	 */
	public static void showAddNewStudentFrameHelpDialog() {
		JOptionPane.showMessageDialog(null,
			    "Fields index number and enrollment year\nshould represent Student's index.\n"
			    + "For example, in index '1/2014',\nnumber '1' represents index number\n"
			    + "and number '2014' represents enrollment year");
	}
	
	/**
	 * Method for appending text in Administrator's frame text area
	 * 
	 * @param message, representing text that should be appended to the text area
	 */
	public static void appendTextToAministratorsFrameTextArea(String message){
		FrameAdministrator.textArea.append(message + "\n");
	}

	/**
	 * Method which adds new Student to the list of students.
	 * 
	 * @param indexNumber, student's number of index
	 * @param enrollmentYear, representing the year when student enrolled at the faculty
	 * @param firstName, representing first name of the student
	 * @param lastName, representing the last name of the student
	 */
	public static void addNewStudent(String indexNumber, String enrollmentYear, String firstName, String lastName) {
		CollectionOfStudents instance = CollectionOfStudents.getInstance();
		
		try{
			String password = enrollmentYear + "/" + indexNumber + "_" + "JAVA";
		
			Student student = new Student(Integer.parseInt(indexNumber), Integer.parseInt(enrollmentYear),firstName, lastName, password, 0);
		
			instance.addStudent(student);
		
			appendTextToAministratorsFrameTextArea(student.toString());
		} catch(Exception exc){
			showExceptionErrorPane(exc);
		}
	}

	/**
	 * Method displaying frame which allows Administrator to add new Question.
	 */
	public static void showAddNewQuestionFrame() {
		QuestionFrame qframe = new QuestionFrame();
		qframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		qframe.setVisible(true);
		qframe.setLocationRelativeTo(null);
	}

	/**
	 * Method which adds Question loaded from QuestionFrame to the global list of questions
	 * 
	 * @param questionText, representing content of question
	 * @param a1, representing possible answer to the question
	 * @param a2, representing possible answer to the question
	 * @param a3, representing possible answer to the question
	 * @param a4, representing possible answer to the question
	 */
	public static void addNewQuestion(String questionText, Answer a1, Answer a2, Answer a3, Answer a4) {
		try{
			CollectionOfQuestions instance = CollectionOfQuestions.getInstance();
			
			instance.addQuestion(new Question(questionText, a1, a2, a3, a4));
		}catch(Exception exc){
			showExceptionErrorPane(exc);
		}
	}

	/**
	 * Method which loads students from database and adds the to the list of students
	 * This method first removes all the items contained in the list of students,
	 * and only then loads new students to the very same list.
	 */
	public static void loadStudentsFromDB() {
		CollectionOfStudents instance = CollectionOfStudents.getInstance();
		MySQLConnection dao = new MySQLConnection();
		
		instance.getListOfStudents().clear();
		
		instance.getListOfStudents().addAll(dao.loadStudentsFromDB());
	}
	
	/**
	 * Method which loads questions from database
	 * and adds them to the global list of questions.
	 * This method first removes all the items contained in the list of questions,
	 * and only then loads new questions to the very same list.
	 */
	public static void loadQuestionsFromDB(){
		CollectionOfQuestions instance = CollectionOfQuestions.getInstance();
		MySQLConnection dao = new MySQLConnection();
		
		instance.getAllQuestions().clear();
		
		instance.getAllQuestions().addAll(dao.loadQuestionsFromDB());
	}

	/**
	 * Method displaying frame which shows the scores of all students
	 */
	public static void showResultsFrame() {
		int option = JOptionPane.showConfirmDialog(
				null,
				"If you want to see the results of your students,\n"
				+ " their results will be loaded from a database,\n"
				+ "but the data about students you have added and not saved in database\n"
				+ "will be lost.\nTherefore, first save all changes to database.\n"
				+ "Are you SURE you want to CONTINUE?",
				"Exit",
				JOptionPane.YES_NO_OPTION
				);
		
		if(option == JOptionPane.YES_OPTION){
			FrameCheckResults frameResults = new FrameCheckResults();
			frameResults.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frameResults.setVisible(true);
			frameResults.setLocationRelativeTo(null);
		}
	}

	/**
	 * Method which saves global list of students to database
	 */
	public static void saveStudentsToDB() 
	{
		int option = JOptionPane.showConfirmDialog(
				null, 
				"Are you SURE you want to save\n"
				+ "list of Students to Database?", 
				"Saveing students to DB", 
				JOptionPane.YES_NO_OPTION);
		
		if(option == JOptionPane.YES_OPTION)
			saveStudentsToDatabase();
	}

	/**
	 * Method which came as a result of refactoring
	 * "saveStudentsToDB()" method.
	 */
	private static void saveStudentsToDatabase() {
		MySQLConnection dao = new MySQLConnection();
		CollectionOfStudents instance = CollectionOfStudents.getInstance();
		
		dao.saveStudentsToDatabase(instance.getListOfStudents());
	}

	/**
	 * Method which first checks whether
	 * Administrator really wants to save global list of questions to dabase or not.
	 * If Administrator wants to proceed, 
	 * method saves global list of question to dabase
	 * by invoking appropriate method from the MySQLConnection class
	 */
	public static void saveQuestionsToDB() {
		int option = JOptionPane.showConfirmDialog(
				null, 
				"Are you SURE you want to save\n"
				+ "list of Questions to Database?", 
				"Saving questions to DB", 
				JOptionPane.YES_NO_OPTION);
		
		if(option == JOptionPane.YES_OPTION)
			saveQuestionsToDatabase();
	}

	/**
	 * Method which came as a result of refactoring
	 * "saveQuestionsToDB()" method.
	 */
	private static void saveQuestionsToDatabase() {
		MySQLConnection dao = new MySQLConnection();
		CollectionOfQuestions instance = CollectionOfQuestions.getInstance();
		
		dao.saveQuestionsToDatabase(instance.getAllQuestions());
	}

	/**
	 * Method which displays Error dialog when SQLException occurs
	 * 
	 * @param exc, representing thrown SQLException exception
	 */
	public static void showSQLExceptionErrorPane(SQLException exc) {
		JOptionPane.showMessageDialog(
				null, 
				exc.getMessage(),
				"ERROR",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Method which displays Error dialog when Exception occurs
	 * 
	 * @param exc, representing thrown Exception
	 */
	public static void showExceptionErrorPane(Exception exc) {
		JOptionPane.showMessageDialog(
				null, 
				exc.getMessage(),
				"ERROR",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * @return List<Question>, returns global list of questions
	 */
	public static List<Question> getAllQuestions() {
		CollectionOfQuestions instance = CollectionOfQuestions.getInstance();
		return instance.getAllQuestions();
	}

	/**
	 * Method that saves student's newest result into database
	 * 
	 * @param student, user/student whose result should be updated in database,
	 * 				because that student just set an exam.
	 */
	public static void updateResultOfStudentToDB(Student student) {
		MySQLConnection dao = new MySQLConnection();
		
		dao.updateResultOfStudentToDB(student);
	}

	/**
	 * Dialog showing informations about authors
	 */
	public static void showAboutDialog() {
		JOptionPane.showMessageDialog(
				null,
				"Authors:\nFilip Stojkovic\nMartin Veres\nMarko Stevankovic\n",
				"Authors info",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Dialog showing basic information about logging in
	 */
	public static void showLogInReadMeDialog() {
		JOptionPane.showMessageDialog(
				null,
				"Authors:\nusername: authorFirstName.authorLastName\n"
				+ "password is their own password.\nIf you want to test authors account\n"
				+ "username: admin.admin\npassword: 1234\n\n"
				+ "Students:\nusername: lastName.firstName.index\n"
				+ "password: index_JAVA\nIf you want to test students account\n"
				+ "username: stevankovic.marko.2014/8\npassword: 2014/8_JAVA",
				"Authors info",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Method for displaying help dialog,
	 * which informs Administrator how to add new Administrator
	 */
	public static void showAddNewAdministratorFrameHelpDialog() {
			JOptionPane.showMessageDialog(
					null,
					"When all text fields are populated with appropriate data, "
					+ "new Administrator will be created and after pressing button Add,"
					+ "inserted into database.",
					"Info",
					JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Method which adds new Administrator to database.
	 * 
	 * @param firstName, representing first name of the Administrator
	 * @param lastName, representing the last name of the Administrator
	 * @param password, representing password of the Administrator
 	 */
	public static void addNewAdministrator(String firstName, String lastName, String password) {
		MySQLConnection dao = new MySQLConnection();
		Administrator administrator = null;
		
		try{
			administrator = new Administrator(firstName, lastName, password);
			dao.saveAdministratorToDatabase(administrator);
		}catch(Exception exc){
			Controller.showExceptionErrorPane(exc);
		}
	}

	/**
	 * Displaying AddNewAdministratorFrame,
	 * frame from which Administrator can create new Administrator
	 */
	public static void showAddNewAdministratorFrame() {
		FrameAddNewAdministrator newAdminFrame = new FrameAddNewAdministrator();
		newAdminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		newAdminFrame.setVisible(true);
		newAdminFrame.setLocationRelativeTo(null);
	}
}
