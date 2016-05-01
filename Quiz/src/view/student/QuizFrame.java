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
	
	private static final int DEFAULT_WIDTH = 700;
	private static final int DEFAULT_HEIGHT = 350;
	
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
	
	public static QuizFrame getInstance(Student student){
		if(instance == null){
			Controller.loadQuestionsFromDB(); //Only when QuizFrame is created for the first time, questions are loaded from Database.
			instance = new QuizFrame(student);
			instance.initialize();
		}
		
		return instance;
	}

	private QuizFrame(Student student){
		setTitle("RED STAR BELGRADE!!!");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.student = student;
	}
	
	private void initialize() {
		initializeQuestions();
		initializuGUI();
	}
	
	private void initializeQuestions() {
		questions = new ArrayList<>();
		List<Question> tmp = new ArrayList<>();
		
		questions.clear();
		tmp.clear();
		
		tmp.addAll(Controller.getAllQuestions());
		
		int i = 0;
		while(i++ < 5){
			Random rand = new Random();
			int index = rand.nextInt(tmp.size());
			
			questions.add(tmp.get(index));
			
			tmp.remove(index);
		}
		tmp.clear();
	}
	
	public void restart(){
		instance = null;
	}
	
	private void initializuGUI() {
		/*
		 * Adjusting labels
		 */
		labelScore = new JLabel(student.getFirstName() + "'s score: ");
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
		
		rba1 = new JRadioButton(questions.get(0).getAnswers().get(0).getAnswerText());
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
		setResizable(false);
		pack();
	}
	
	public void nextQuestion(){
		if(questions.size() > 1){
			questions.remove(0);
			
			labelQuestionContent.setText(questions.get(0).getDescription());
			rba1.setText(questions.get(0).getAnswers().get(0).getAnswerText());
			rba2.setText(questions.get(0).getAnswers().get(1).getAnswerText());
			rba3.setText(questions.get(0).getAnswers().get(2).getAnswerText());
			rba4.setText(questions.get(0).getAnswers().get(3).getAnswerText());
			
			labelScore.setText(student.getFirstName() + "'s score: " + student.getResult());
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

	private void endOfExam() {
		buttonConfirm.setEnabled(false);
		rba1.setEnabled(false);
		rba2.setEnabled(false);
		rba3.setEnabled(false);
		rba4.setEnabled(false);
		labelQuestionContent.setText("The End...");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}
}
