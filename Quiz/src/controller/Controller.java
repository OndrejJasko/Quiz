package controller;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import com.mysql.jdbc.integration.c3p0.MysqlConnectionTester;

import database_connection.MySQLConnection;
import domain.Administrator;
import domain.Answer;
import domain.Question;
import domain.Student;
import model.CollectionOfQuestions;
import model.CollectionOfStudents;
import view.LoginFrame;
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
		FrameAddNewStudent addStudentFrame = FrameAddNewStudent.getInstance();
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
	 * Method for removing any text from Administrator's frame text area
	 */
	public static void ClearAministratorsFrameTextArea(){
		FrameAdministrator.textArea.setText(null);
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
		QuestionFrame qframe = QuestionFrame.getInstance();
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
		
		instance.getListOfStudents().clear();
		
		Controller.ClearAministratorsFrameTextArea();
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
		
		instance.getAllQuestions().clear();
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
		FrameAddNewAdministrator newAdminFrame = FrameAddNewAdministrator.getInstance();
		newAdminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		newAdminFrame.setVisible(true);
		newAdminFrame.setLocationRelativeTo(null);
	}

	/**
	 * Confirm dialog which asks Administrator 
	 * whether he is sure that he wants to dispose/quit dialog
	 * 
	 * @param frame, frame that is closing
	 */
	public static void disposeFrame(JFrame frame) {
		int option = JOptionPane.showConfirmDialog(
				frame,
				"Are you sure you want to close/dispose this window?",
				"Closing/Disposing",
				JOptionPane.YES_NO_OPTION);
		
		if(option == JOptionPane.YES_OPTION)
			frame.dispose();
	}

	/**
	 * Message dilalog which informs Administrator about actions he can perform
	 */
	public static void showReadMeDialogFrameAdministrator() {
		JOptionPane.showMessageDialog(
				null,
			    "Add student - Displays frame from which Administrator can add new student\n"
			    + "Add question - Displays frame from which Administrator can add new question\n"
			    + "Check results - Displays frame which displays scores of all students\n"
			    + "Save students - Saves recently added students to database\n"
			    + "Save question - Saves recently added questions to database\n"
			    + "LogOut/Cancel - User logs out, disposes Aministrator frame\n");
	}

	/**
	 * Method which removes content of all text filds in frame FrameAddNewAdministrator
	 */
	public static void refreshFieldsFrameAddNewAdministrator() {
		FrameAddNewAdministrator instance = FrameAddNewAdministrator.getInstance();
		instance.getTfFirstName().setText(null);
		instance.getTfLastName().setText(null);
		instance.getTfPassword().setText(null);
	}

	/**
	 * Method which displays confirmation dialog,
	 * whether users wants to quit application before loging in or not.
	 */
	public static void disposeLoginFrame(LoginFrame loginFrame) {
		int option = JOptionPane.showConfirmDialog(
				loginFrame,
				"Quit application?",
				"Quiting",
				JOptionPane.YES_NO_OPTION);
		
		if(option == JOptionPane.YES_OPTION)
			loginFrame.dispose();
	}

	/**
	 * Confirm dialog which asks Administrator 
	 * whether he is sure that he wants to log out from current account
	 * 
	 * @param frameAdministrator, frame that is closing
	 */
	public static void disposeAdministratorFrame(FrameAdministrator frameAdministrator) {
		int option = JOptionPane.showConfirmDialog(
				frameAdministrator,
				"Are you sure you want to log out?",
				"LOG OUT",
				JOptionPane.YES_NO_OPTION);
		
		if(option == JOptionPane.YES_OPTION)
			frameAdministrator.dispose();
	}

	/**
	 * Method which removes content of all text filds
	 * in frame FrameAddNewStudent
	 */
	public static void refreshFieldsFrameAddNewStudent() {
		FrameAddNewStudent instance = FrameAddNewStudent.getInstance();
		instance.getTfIndex().setText(null);
		instance.getTfEnrollmentYear().setText(null);
		instance.getTfFirstName().setText(null);
		instance.getTfLastName().setText(null);
	}

	/**
	 * Message dilalog which informs Administrator about actions he can perform on current frame
	 */
	public static void showReadMeDialogQuestionFrame(QuestionFrame qFrame) {
			JOptionPane.showMessageDialog(
					qFrame,
				    "Add  - Creates new question, based on content of text areas and jradiobuttons\n"
				    + "\tand adds newly created question to global list of questions.\n"
				    + "Question panel - Add question text to textArea surrounded with border titled 'Question'\n"
				    + "Answer panels - Add answer text to textAreas surrounded with border titled 'Answer #'\n"
				    + "\tIf answer# is correct, select appropriate jradiobutton\n");
	}

	/**
	 * Method which removes content of all text areas in the frame.
	 */
	public static void refreshFieldsQuestionFrame() {
		QuestionFrame instance = QuestionFrame.getInstance();
		instance.getTextAreaQuestion().setText(null);
		instance.getTaAnswer1().setText(null);
		instance.getTaAnswer2().setText(null);
		instance.getTaAnswer3().setText(null);
		instance.getTaAnswer4().setText(null);
		instance.getRb1().setSelected(true);
	}

	/**
	 * Method which generates Answer
	 * based on contents of the appropriate text area
	 * and appropriate radio button
	 * 
	 * @param textArea, text area from which Answer text is read
	 * 
	 * @param radioButton, radio button showing whether Answer is correct or not
	 * 
	 *  @return Answer, returns answer based on content of the input parameters
	 */
	public static Answer getAnswer(JTextArea textArea, JRadioButton radioButton) {
		try{
			String answerText = textArea.getText().trim();
			boolean isCorrect = radioButton.isSelected();
			
			return new Answer(answerText, isCorrect);
			
		}catch(Exception exc){
			Controller.showExceptionErrorPane(exc);
		}
		return null;
	}

	/**
	 * Method that saves question to global list of questions
	 */
	public static void saveQuestion() {
		QuestionFrame instance = QuestionFrame.getInstance();
		Answer a1 = Controller.getAnswer(instance.getTaAnswer1(), instance.getRb1());
		Answer a2 = Controller.getAnswer(instance.getTaAnswer2(), instance.getRb2());
		Answer a3 = Controller.getAnswer(instance.getTaAnswer3(), instance.getRb3());
		Answer a4 = Controller.getAnswer(instance.getTaAnswer4(), instance.getRb4());
		
		String questionText = instance.getTextAreaQuestion().getText().trim();
		
		Controller.addNewQuestion(questionText, a1, a2, a3, a4);
	}
}
