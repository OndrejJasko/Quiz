package view.admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

import controller.Controller;
import domain.Answer;

/**
 * Class (Frame) for adding new Question.
 * Frame displays 5 text areas.
 * In first text area Administrator adds text of a question.
 * In second, adds text of the first possible Answer
 * In second, adds text of the second possible Answer
 * In third, adds text of the third possible Answer
 * In fourth, add text of the fourth possible Answer
 * 
 * @author Filip Stojkovic
 * @author Marko Stevankovic
 * @author Martin Veres
 */
public class QuestionFrame extends JFrame implements ActionListener {
	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 700;
	
	private JButton buttonClose;
	private JButton buttonAdd;
	
	private final ButtonGroup buttonGroup;
	
	private JTextArea textAreaQuestion;
	private JTextArea taAnswer1;
	private JTextArea taAnswer2;
	private JTextArea taAnswer3;
	private JTextArea taAnswer4;
	
	private JScrollPane scrollPane0;
	private JScrollPane scrollPane1;
	private JScrollPane scrollPane2;
	private JScrollPane scrollPane3;
	private JScrollPane scrollPane4;
	
	private JPanel panelCenter;
	private JPanel panelSouth;
	
	private JPanel panelQ;
	private JPanel panelA1;
	private JPanel panelA2;
	private JPanel panelA3;
	private JPanel panelA4;
	
	private JRadioButton rb1;
	private JRadioButton rb2;
	private JRadioButton rb3;
	private JRadioButton rb4;
	
	private JMenuBar menuBar;
	private JMenu menuHelpREADME;
	private JMenuItem itemReadMe;
	
	/*
	 * public empty constructor
	 */
	public QuestionFrame() {
		setTitle("Add new question");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLayout(new BorderLayout());
		
		/*
		 * Adjusting menu bar
		 */
		menuBar = new JMenuBar();
		menuHelpREADME = new JMenu("Help/Readme");
		
		itemReadMe = new JMenuItem("ReadMe");
		itemReadMe.addActionListener(this);
		
		menuHelpREADME.add(itemReadMe);
		menuBar.add(menuHelpREADME);
		
		setJMenuBar(menuBar);
		/*
		 * Defining panels
		 */
		panelCenter = new JPanel(new GridLayout(5, 1));
		panelSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		buttonGroup = new ButtonGroup();
		
		/*
		 * Adjusting question pane
		 */
		textAreaQuestion = new JTextArea();
		textAreaQuestion.setPreferredSize(new Dimension(DEFAULT_WIDTH - 40, 50));
		textAreaQuestion.setEditable(true);
		textAreaQuestion.setLineWrap(true);
		textAreaQuestion.setWrapStyleWord(true);
		
		scrollPane0 = new JScrollPane(textAreaQuestion);
		
		Border border0 = BorderFactory.createEtchedBorder();
		Border titlet0 = BorderFactory.createTitledBorder(border0, "Question");
		
		panelQ = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelQ.setBorder(titlet0);
		panelQ.add(scrollPane0);		
		
		panelCenter.add(panelQ);
		
		/*
		 * Adjusting Answer1 panel
		 */
		
		taAnswer1 = new JTextArea();
		taAnswer1.setPreferredSize(new Dimension(DEFAULT_WIDTH - 40, 50));
		taAnswer1.setEditable(true);
		taAnswer1.setLineWrap(true);
		taAnswer1.setWrapStyleWord(true);
		
		scrollPane1 = new JScrollPane(taAnswer1);
		
		Border border1 = BorderFactory.createEtchedBorder();
		Border titlet1 = BorderFactory.createTitledBorder(border1, "Answer 1");
		
		rb1 = new JRadioButton("is Answer1 correct?");
		buttonGroup.add(rb1);
		rb1.setSelected(true);
		
		panelA1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelA1.setBorder(titlet1);
		panelA1.add(scrollPane1);
		panelA1.add(rb1);
		
		panelCenter.add(panelA1);
		
		/*
		 * Adjusting Answer2 panel
		 */
		
		taAnswer2 = new JTextArea();
		taAnswer2.setPreferredSize(new Dimension(DEFAULT_WIDTH - 40, 50));
		taAnswer2.setEditable(true);
		taAnswer2.setLineWrap(true);
		taAnswer2.setWrapStyleWord(true);
		
		scrollPane2 = new JScrollPane(taAnswer2);
		
		Border border2 = BorderFactory.createEtchedBorder();
		Border titlet2 = BorderFactory.createTitledBorder(border2, "Answer 2");
		
		rb2 = new JRadioButton("is Answer2 correct?");
		buttonGroup.add(rb2);
		
		panelA2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelA2.setBorder(titlet2);
		panelA2.add(scrollPane2);		
		panelA2.add(rb2);
		
		panelCenter.add(panelA2);
		
		/*
		 * Adjusting Answer3 panel
		 */
		
		taAnswer3 = new JTextArea();
		taAnswer3.setPreferredSize(new Dimension(DEFAULT_WIDTH - 40, 50));
		taAnswer3.setEditable(true);
		taAnswer3.setLineWrap(true);
		taAnswer3.setWrapStyleWord(true);
		
		scrollPane3 = new JScrollPane(taAnswer3);
		
		Border border3 = BorderFactory.createEtchedBorder();
		Border titlet3 = BorderFactory.createTitledBorder(border3, "Answer 3");
		
		rb3 = new JRadioButton("is Answer3 correct?");
		buttonGroup.add(rb3);
		
		panelA3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelA3.setBorder(titlet3);
		panelA3.add(scrollPane3);
		panelA3.add(rb3);
		
		panelCenter.add(panelA3);
		
		/*
		 * Adjusting Answer4 panel
		 */
		
		taAnswer4 = new JTextArea();
		taAnswer4.setPreferredSize(new Dimension(DEFAULT_WIDTH - 40, 50));
		taAnswer4.setEditable(true);
		taAnswer4.setLineWrap(true);
		taAnswer4.setWrapStyleWord(true);
		
		scrollPane4 = new JScrollPane(taAnswer4);
		
		Border border4 = BorderFactory.createEtchedBorder();
		Border titlet4 = BorderFactory.createTitledBorder(border1, "Answer 4");
		
		rb4 = new JRadioButton("is Answer4 correct?");
		buttonGroup.add(rb4);
		
		panelA4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelA4.setBorder(titlet4);
		panelA4.add(scrollPane4);
		panelA4.add(rb4);
		
		panelCenter.add(panelA4);
		
		/*
		 * Adjusting south panel
		 */
		panelSouth.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT / 10 - 20));
		
		buttonAdd = new JButton("Add question");
		buttonAdd.addActionListener(this);
		buttonClose = new JButton("Close");
		buttonClose.addActionListener(this);
		
		panelSouth.add(buttonAdd);
		panelSouth.add(buttonClose);
		
		/*
		 * Adding panels to frame
		 */
		add(panelSouth, BorderLayout.SOUTH);
		add(panelCenter, BorderLayout.CENTER);
	}

	/**
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == buttonClose){
			disposeQuestionFrame();
		} else if(source == buttonAdd){
			saveQuestion();
		} else if(source == itemReadMe){
			showReadMeDialog();
		}
	}

	/**
	 * Message dilalog which informs Administrator about actions he can perform on current frame
	 */
	private void showReadMeDialog() {
		JOptionPane.showMessageDialog(
				null,
			    "Add  - Creates new question, based on content of text areas and jradiobuttons\n"
			    + "\tand adds newly created question to global list of questions.\n"
			    + "Question panel - Add question text to textArea surrounded with border titled 'Question'\n"
			    + "Answer panels - Add answer text to textAreas surrounded with border titled 'Answer #'\n"
			    + "\tIf answer# is correct, select appropriate jradiobutton\n");
	}

	/**
	 * Method that saves question to global list of questions
	 */
	private void saveQuestion() {
		Answer a1 = getAnswer(taAnswer1, rb1);
		Answer a2 = getAnswer(taAnswer2, rb2);
		Answer a3 = getAnswer(taAnswer3, rb3);
		Answer a4 = getAnswer(taAnswer4, rb4);
		
		String questionText = textAreaQuestion.getText().trim();
		
		Controller.addNewQuestion(questionText, a1, a2, a3, a4);
		
		refreshFields();
	}

	/**
	 * Confirm dialog which asks Administrator 
	 * whether he is sure that he wants to dispose/quit this dialog
	 */
	private void disposeQuestionFrame() {
		int option = JOptionPane.showConfirmDialog(
				null,
				"Leave Question frame?",
				"Exit",
				JOptionPane.YES_NO_OPTION);
		
		if(option == JOptionPane.YES_OPTION)
			this.dispose();
	}

	/**
	 * Methods which removes contents of all text areas in the frame
	 */
	private void refreshFields() {
		textAreaQuestion.setText(null);
		taAnswer1.setText(null);
		taAnswer2.setText(null);
		taAnswer3.setText(null);
		taAnswer4.setText(null);
	}

	/**
	 * Method which generates Answer
	 * based on contents of the appropriate text area
	 * and appropriate radio button
	 * 
	 * @param textArea, text area from which Answer text is read
	 * 
	 * @param rb, radio button representing whether Answer is correct or not
	 * 
	 *  @return Answer, returns answer based on content of the input parameters
	 */
	private Answer getAnswer(JTextArea textArea, JRadioButton rb) {
		
		try{
			String answerText = textArea.getText().trim();
			boolean isCorrect = rb.isSelected();
			
			return new Answer(answerText, isCorrect);
			
		}catch(Exception exc){
			Controller.showExceptionErrorPane(exc);
		}
		
		return null;
	}
}
