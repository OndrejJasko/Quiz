package view.student;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;
import javax.swing.border.Border;

import controller.Controller;
import domain.Question;
import domain.Student;

/**
 * Class representing QuizFrame where Student is given 10 questions on which he should answer
 * 
 * @author Marko Stevankovic
 * @author Filip Stojkovic
 * @author Martin Veres
 */
public class QuizFrame extends JFrame implements ActionListener {
	
	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 300;
	
	private static QuizFrame instance;
	
	private List<Question> questions = new ArrayList<>();
	
	private Student student;
	
	private JPanel panel;
	private JPanel panelNorth;
	private JPanel panelSouth;
	
	private JPanel panelCenter;
	private JPanel panelCenterNorth;
	private JPanel panelCenterCenter;
	private JPanel panelCenterSouth;
	
	private JPanel panelAnswer1;
	private JPanel panelAnswer2;
	private JPanel panelAnswer3;
	private JPanel panelAnswer4;
	
	private JLabel labelScore;
	private JLabel labelQuestionContent;
	
	private JButton buttonConfirm;
	private ButtonGroup buttonGroup;
	private JRadioButton rba1;
	private JRadioButton rba2;
	private JRadioButton rba3;
	private JRadioButton rba4;
	
	/**
	 * Method returning instance of QuizFrame class.
	 * Idea from singleton pattern.
	 * 
	 * @param student, user who has logged in, in order to take an exam
	 * @return QuizFrame instance
	 */
	public static QuizFrame getInstance(){
		if(instance == null){
			Controller.loadQuestionsFromDB();
			instance = new QuizFrame();
			instance.initialize();
		}
		return instance;
	}

	/**
	 * Private constructor which takes one student as an input parameter
	 * and sets Title and Size properties.
	 * 
	 * @param student, student who is taking exam, playing quiz
	 */
	private QuizFrame(){
		setTitle("RED STAR BELGRADE!!!");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.student = Controller.getActiveStudent();
	}
	
	private void initialize() {
		Controller.initializeQuestions();
		initializuGUI();
	}
	
	/**
	 * instance resets to its default.
	 */
	public void restart(){
		instance = null;
	}
	
	/**
	 * Private method initializing GUI components
	 */
	private void initializuGUI() {
		/*
		 * Adjusting labels
		 */
		labelScore = new JLabel(student.getFirstName().toUpperCase() + "'s score: ");
		Font fontScore = new Font("Verdana", Font.BOLD, 12);
		labelScore.setFont(fontScore);
		
		labelQuestionContent = new JLabel(questions.get(0).getDescription());
		Font fontQuestion = new Font("Verdana", Font.BOLD, 12);
		labelQuestionContent.setFont(fontQuestion);
		
		/*
		 * panels
		 */
		panel = new JPanel(new BorderLayout());
		panelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		Border borderCenterNorth = BorderFactory.createEtchedBorder();
		Border titledCenterNorth = BorderFactory.createTitledBorder(borderCenterNorth, "Question");
		
		Border borderCenterCenter = BorderFactory.createEtchedBorder();
		Border titledCenterCenter = BorderFactory.createTitledBorder(borderCenterCenter, "Answers");
		
		Border border = BorderFactory.createEtchedBorder();
		Border titled= BorderFactory.createTitledBorder(border, "Student: " + student.toString());
		
		panelCenter = new JPanel(new BorderLayout());
		panelCenterNorth = new JPanel();
		panelCenterCenter = new JPanel(new GridLayout(2,2));
		panelCenterSouth = new JPanel();
		
		panelCenterNorth.setBorder(titledCenterNorth);
		panelCenterNorth.setPreferredSize(new Dimension(DEFAULT_WIDTH - 20, 70));
		panelCenterCenter.setBorder(titledCenterCenter);
		panel.setBorder(titled);
		
		panelAnswer1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelAnswer2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelAnswer3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelAnswer4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		
		/*
		 * Adjusting buttons
		 */
		buttonConfirm = new JButton("Confirm answer");
		buttonConfirm.addActionListener(this);
		
		rba1 = new JRadioButton("<html>" + questions.get(0).getAnswers().get(0).getAnswerText()+ "</html>");
		rba1.setHorizontalTextPosition(SwingConstants.LEFT);
		rba2 = new JRadioButton(questions.get(0).getAnswers().get(1).getAnswerText());
		// rba2.setHorizontalTextPosition(SwingConstants.LEFT);
		rba3 = new JRadioButton(questions.get(0).getAnswers().get(2).getAnswerText());
		rba3.setHorizontalTextPosition(SwingConstants.LEFT);
		rba4 = new JRadioButton(questions.get(0).getAnswers().get(3).getAnswerText());
		// rba4.setHorizontalTextPosition(SwingConstants.LEFT);
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(rba1);
		buttonGroup.add(rba2);
		buttonGroup.add(rba3);
		buttonGroup.add(rba4);
		
		/*
		 * Adding components to panels
		 */
		
		panelNorth.add(labelScore);
		
		panelCenterNorth.add(labelQuestionContent);
		
		panelCenterCenter.add(panelAnswer1);
		panelCenterCenter.add(panelAnswer2);
		panelCenterCenter.add(panelAnswer3);
		panelCenterCenter.add(panelAnswer4);
		
		panelCenterSouth.add(buttonConfirm);
		
		panelCenter.add(panelCenterNorth, BorderLayout.NORTH);
		panelCenter.add(panelCenterCenter, BorderLayout.CENTER);
		panelCenter.add(panelCenterSouth, BorderLayout.SOUTH);
		
		panelAnswer1.add(rba1);
		panelAnswer2.add(rba2);
		panelAnswer3.add(rba3);
		panelAnswer4.add(rba4);
		
		panel.add(panelNorth, BorderLayout.NORTH);
		panel.add(panelCenter, BorderLayout.CENTER);
		
		add(panel);
		// setResizable(false);
		pack();
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		
		if(source == buttonConfirm){
			Controller.ButtonConfirmPerformAction();
		}
	}

	/**
	 * Get method for private attribute getRba1
	 * @return rba1, {@link JRadioButton}
	 */
	public JRadioButton getRba1() {
		return rba1;
	}
	
	/**
	 * Get method for private attribute getRba2
	 * @return rba2, {@link JRadioButton}
	 */
	public JRadioButton getRba2() {
		return rba2;
	}
	
	/**
	 * Get method for private attribute getRba3
	 * @return rba3, {@link JRadioButton}
	 */
	public JRadioButton getRba3() {
		return rba3;
	}
	
	/**
	 * Get method for private attribute getRba4
	 * @return rba4, {@link JRadioButton}
	 */
	public JRadioButton getRba4() {
		return rba4;
	}
	
	/**
	 * Get method for private attribute labelQuestionContent
	 * @return labelQuestionContent, {@link JLabel}
	 */
	public JLabel getLabelQuestionContent() {
		return labelQuestionContent;
	}
	
	/**
	 * Get method for private attribute labelScore
	 * @return labelScore, {@link JLabel}
	 */
	public JLabel getLabelScore() {
		return labelScore;
	}
	
	/**
	 * Get method for private attribute buttonConfirm
	 * @return buttonConfirm, {@link JButton}
	 */
	public JButton getButtonConfirm() {
		return buttonConfirm;
	}
	
	/**
	 * Get method for private atribute Student
	 * @return student, {@link Student}
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * Get method for private attribute questions
	 * @return questions
	 */
	public List<Question> getQuestions() {
		return questions;
	}
}
