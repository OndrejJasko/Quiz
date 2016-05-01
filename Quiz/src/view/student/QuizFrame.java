package view.student;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class QuizFrame extends JFrame implements ActionListener{
	
	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 300;
	
	private static QuizFrame instance;
	
	private List<Question> questions;
	
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
	public static QuizFrame getInstance(Student student){
		if(instance == null){
			Controller.loadQuestionsFromDB();
			instance = new QuizFrame(student);
			instance.initialize();
		}
		
		return instance;
	}

	/**
	 * private constructor which takes one student as an input parameter
	 * and sets Title and Size properties.
	 * 
	 * @param student, student who is taking exam, playing quiz
	 */
	private QuizFrame(Student student){
		setTitle("RED STAR BELGRADE!!!");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.student = student;
	}
	
	private void initialize() {
		initializeQuestions();
		initializuGUI();
	}
	
	/**
	 * Initializing questions into local list tmp.
	 * Five questions are choosen randomly.
	 * Student than answers to those 5 questions.
	 */
	private void initializeQuestions() {
		questions = new ArrayList<>();
		List<Question> tmp = new ArrayList<>();
		
		questions.clear();
		tmp.clear();
		
		tmp.addAll(Controller.getAllQuestions());
		
		int i = 0;
		while(i++ < 10){
			Random rand = new Random();
			int index = rand.nextInt(tmp.size());
			
			questions.add(tmp.get(index));
			
			tmp.remove(index);
		}
		tmp.clear();
	}
	
	/**
	 * instance resets to its default.
	 */
	public void restart(){
		instance = null;
	}
	
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
	
	/**
	 * If students has answered to a question, application moves on to the next question.
	 * If there are any questions left, student continues to answer.
	 * If there are no questions left, student cannot use this application any more.
	 */
	public void nextQuestion(){
		if(questions.size() > 1){
			questions.remove(0);
			
			labelQuestionContent.setText(questions.get(0).getDescription());
			rba1.setText(questions.get(0).getAnswers().get(0).getAnswerText());
			rba2.setText(questions.get(0).getAnswers().get(1).getAnswerText());
			rba3.setText(questions.get(0).getAnswers().get(2).getAnswerText());
			rba4.setText(questions.get(0).getAnswers().get(3).getAnswerText());
			
			labelScore.setText(student.getFirstName().toUpperCase() + "'s score: " + student.getResult());
		}
		else{
			Controller.updateResultOfStudentToDB(student);
			JOptionPane.showMessageDialog(
					null,
					"Exam is over!\n" +
					"You have scored: " + 
					student.getResult() + " points");
			endOfExam();
		}
	}

	/**
	 * If student has answered to all questions (5 questions),
	 * he cannot use this application any more.
	 */
	private void endOfExam() {
		buttonConfirm.setEnabled(false);
		rba1.setEnabled(false);
		rba2.setEnabled(false);
		rba3.setEnabled(false);
		rba4.setEnabled(false);
		labelScore.setText(student.getFirstName().toUpperCase() + "'s score: " + student.getResult());
		labelQuestionContent.setText("The End...");
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		
		if(source == buttonConfirm){
			performAction();
		}
	}

	/**
	 * After selecting answer student presses button.
	 * If selected answer is correct, 
	 * student's result is increased and application moves on to next question.
	 * If selected answer is incorrect,
	 * student's result is decreased and app moves on to next question
	 */
	private void performAction() {
		
		boolean correct = isCorrectlyAnswered();
		
		if(correct){
			student.increaseResult();
			nextQuestion();
		} else{
			student.decreaseResult();
			nextQuestion();
		}
	}

	/**
	 * Method checking whether selected answer is correnct or not
	 * 
	 * @return boolean, returns true if answer is correct and false if it is not
	 */
	private boolean isCorrectlyAnswered() {
		if(rba1.isSelected() && questions.get(0).getAnswers().get(0).isCorrect()){
			return true;
		} else if(rba2.isSelected() && questions.get(0).getAnswers().get(1).isCorrect()){
			return true;
		} else if(rba3.isSelected() && questions.get(0).getAnswers().get(2).isCorrect()){
			return true;
		} else if(rba4.isSelected() && questions.get(0).getAnswers().get(3).isCorrect()){
			return true;
		}
		return false;
	}
}
