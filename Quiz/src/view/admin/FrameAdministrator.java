package view.admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

import controller.Controller;

/**
 * Class which represents Administrator frame.
 * On Administrator frame, Administrator can choose his actions.
 * Administrator can add new student, add new question, save student/question to database,
 * check results of all students or can just log out and quit the application.
 * 
 * @author Marko Stevankovic
 * @author Filip Stojkovic
 * @author Martin Veres
 */
public class FrameAdministrator extends JFrame implements ActionListener{
	
	public static final int DEFAULT_WIDTH = 465;
	public static final int DEFAULT_HEIGHT = 400;
	
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenu menuHelp;
	private JMenuItem itemAddStudent;
	private JMenuItem itemAddQuestion;
	private JMenuItem itemLogOut;
	private JMenuItem itemCheckResults;
	private JMenuItem itemSaveStudents;
	private JMenuItem itemSaveQuestions;
	private JMenuItem itemAddNewAdmin;
	private JMenuItem itemHelp;
	
	private JScrollPane scrollPane;
	
	public static JTextArea textArea;
	
	private JButton buttonAddStudent;
	private JButton buttonAddQuestion;
	private JButton buttonLogOut;
	private JButton buttonCheckResults;
	private JButton buttonSaveStudentsToDB;
	private JButton buttonSaveQuestionsToDB;
	private JButton buttonAddNewAdmin;
	
	private JPanel panelEast;
	private JPanel panelCenter;
	
	public FrameAdministrator(String firstName){
		
		setTitle("Hello " + firstName + "! Choose what you are going to do next :)");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLayout(new BorderLayout());
		
		/*
		 * Adjusting menu bar
		 */
		menuBar = new JMenuBar();
		menuFile = new JMenu("File");
		menuHelp = new JMenu("Help");
		
		itemAddStudent = new JMenuItem("Add student");
		itemAddQuestion = new JMenuItem("Add question");
		itemCheckResults = new JMenuItem("Check results");
		itemSaveStudents = new JMenuItem("Save students");
		itemSaveQuestions = new JMenuItem("Save questions");
		itemAddNewAdmin = new JMenuItem("Add administrator");
		itemLogOut = new JMenuItem("LogOut/Exit");
		itemHelp = new JMenuItem("READ ME");
		
		itemAddStudent.addActionListener(this);
		itemAddQuestion.addActionListener(this);
		itemCheckResults.addActionListener(this);
		itemSaveStudents.addActionListener(this);
		itemSaveQuestions.addActionListener(this);
		itemAddNewAdmin.addActionListener(this);
		itemLogOut.addActionListener(this);
		itemHelp.addActionListener(this);
		
		menuFile.add(itemAddStudent);
		menuFile.add(itemAddQuestion);
		menuFile.add(itemCheckResults);
		menuFile.add(itemSaveStudents);
		menuFile.add(itemSaveQuestions);
		menuFile.add(itemAddNewAdmin);
		menuFile.add(itemLogOut);
		
		menuHelp.add(itemHelp);
		
		menuBar.add(menuFile);
		menuBar.add(menuHelp);
		
		setJMenuBar(menuBar);
		
		/*
		 * Adjusting panel position in the center of the layout
		 */
		Border border = BorderFactory.createEtchedBorder();
		Border titled = BorderFactory.createTitledBorder(border, "Added students");
		
		panelCenter = new JPanel();
		panelCenter.setBorder(titled);
		
		textArea = new JTextArea();
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		scrollPane.setPreferredSize(new Dimension(DEFAULT_WIDTH - 200, DEFAULT_HEIGHT - 100));
		
		panelCenter.add(scrollPane);
	
		/*
		 * Adjusting panel position in the south part of the layout
		 */
		panelEast = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelEast.setPreferredSize(new Dimension(150, 400));
		
		buttonAddStudent = new JButton("Add student");
		buttonAddQuestion = new JButton("Add question");
		buttonCheckResults = new JButton("Check results");
		buttonSaveStudentsToDB = new JButton("Save students");
		buttonSaveQuestionsToDB = new JButton("Save questions");
		buttonAddNewAdmin = new JButton("Add admin");
		buttonLogOut = new JButton("LogOut/Cancel");
		
		buttonAddStudent.addActionListener(this);
		buttonAddQuestion.addActionListener(this);
		buttonCheckResults.addActionListener(this);
		buttonSaveStudentsToDB.addActionListener(this);
		buttonSaveQuestionsToDB.addActionListener(this);
		buttonAddNewAdmin.addActionListener(this);
		buttonLogOut.addActionListener(this);
		
		panelEast.add(buttonAddStudent);
		panelEast.add(buttonAddQuestion);
		panelEast.add(buttonCheckResults);
		panelEast.add(buttonSaveStudentsToDB);
		panelEast.add(buttonSaveQuestionsToDB);
		panelEast.add(buttonAddNewAdmin);
		panelEast.add(buttonLogOut);
		
		add(panelCenter, BorderLayout.CENTER);
		add(panelEast, BorderLayout.EAST);
	}
	
	/**
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == itemAddStudent || source == buttonAddStudent){
			Controller.showAddNewStudentFrame();
		} else if(source == itemAddQuestion || source == buttonAddQuestion){
			Controller.showAddNewQuestionFrame();
		} else if(source == itemCheckResults || source == buttonCheckResults){
			Controller.showResultsFrame();
		} else if(source == itemLogOut || source == buttonLogOut){
			disposeAdministratorFrame();
		} else if(source == itemSaveStudents || source == buttonSaveStudentsToDB){
			Controller.saveStudentsToDB();
		} else if(source == itemSaveQuestions || source == buttonSaveQuestionsToDB){
			Controller.saveQuestionsToDB();
		} else if(source == itemHelp){
			showReadMeDialog();
		} else if(source == itemAddNewAdmin || source == buttonAddNewAdmin){
			Controller.showAddNewAdministratorFrame();
		}
	}

	/**
	 * Confirm dialog which asks Administrator 
	 * whether he is sure that he wants to dispose/quit this dialog
	 */
	private void disposeAdministratorFrame() {
		int option = JOptionPane.showConfirmDialog(
				null,
				"Are you sure you want to log out?",
				"LogOUT",
				JOptionPane.YES_NO_OPTION);
		
		if(option == JOptionPane.YES_OPTION)
			this.dispose();
	}

	/**
	 * Message dilalog which informs Administrator about actions he can perform
	 */
	private void showReadMeDialog() {
		JOptionPane.showMessageDialog(
				null,
			    "Add student - Displays frame from which Administrator can add new student\n"
			    + "Add question - Displays frame from which Administrator can add new question\n"
			    + "Check results - Displays frame which displays scores of all students\n"
			    + "Save students - Saves recently added students to database\n"
			    + "Save question - Saves recently added questions to database\n"
			    + "LogOut/Cancel - User logs out, disposes Aministrator frame\n");
	}
}
