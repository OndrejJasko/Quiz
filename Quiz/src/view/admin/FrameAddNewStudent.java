package view.admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.Controller;

/**
 * Class (Frame) which is used for adding new Student.
 * 
 * @author Filip Stojkovic
 * @author Marko Stevankovic
 * @author Martin Veres
 */
public class FrameAddNewStudent extends JFrame implements ActionListener{
	
	public static final int DEFAULT_WIDTH = 300;
	public static final int DEFAULT_HEIGHT = 250;
	
	private static FrameAddNewStudent instance;
	
	private JPanel panelIndex;
	private JPanel panelEnrollmentYear;
	private JPanel panelFirstName;
	private JPanel panelLastName;
	private JPanel panelButtons;
	
	private JMenuBar menuBar;
	private JMenu menuHelp;
	private JMenuItem itemHelp;
	
	private JButton buttonAdd;
	private JButton buttonCancel;
	
	private JLabel labelIndex;
	private JLabel labelEnrollmentYear;
	private JLabel labelFirstName;
	private JLabel labelLastName;
	
	private JTextField tfIndex;
	private JTextField tfEnrollmentYear;
	private JTextField tfFirstName;
	private JTextField tfLastName;
	
	/**
	 * Global point of access for FrameAddNewStudent instance
	 * @return instance, {@link FrameAddNewStudent}
	 */
	public static FrameAddNewStudent getInstance(){
		if(instance == null){
			instance = new FrameAddNewStudent();
		}
		return instance;
	}
	/**
	 * Making the constructor private so that this class cannot be instantiated
	 */
	private FrameAddNewStudent(){
		setTitle("Add new student...");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLayout(new GridLayout(5, 1));
		
		/*
		 * Adjusting menu bar
		 */
		menuBar = new JMenuBar();
		menuHelp = new JMenu("Help");
		itemHelp = new JMenuItem("Help");
		itemHelp.addActionListener(this);
		
		menuHelp.add(itemHelp);
		
		menuBar.add(menuHelp);
		
		setJMenuBar(menuBar);
		
		/*
		 * Adjusting panel for retrieving index number 
		 */
		panelIndex = new JPanel(new FlowLayout(FlowLayout.LEFT));
		labelIndex = new JLabel("Index number: ");
		tfIndex = new JTextField(4);
		panelIndex.add(labelIndex);
		panelIndex.add(tfIndex);
		
		/*
		 * Adjusting panel for retrieving the year of enrollment at the Faculty
		 */
		panelEnrollmentYear = new JPanel(new FlowLayout(FlowLayout.LEFT));
		labelEnrollmentYear = new JLabel("Enrollment year: ");
		tfEnrollmentYear = new JTextField(4);
		panelEnrollmentYear.add(labelEnrollmentYear);
		panelEnrollmentYear.add(tfEnrollmentYear);
		
		/*
		 * Adjusting panel for retrieving information about user's first name
		 */
		panelFirstName = new JPanel(new FlowLayout(FlowLayout.LEFT));
		labelFirstName = new JLabel("First name: ");
		tfFirstName = new JTextField(15);
		panelFirstName.add(labelFirstName);
		panelFirstName.add(tfFirstName);
		
		/*
		 * Adjusting panel for retrieving information about user's last name
		 */
		panelLastName = new JPanel(new FlowLayout(FlowLayout.LEFT));
		labelLastName = new JLabel("Last name: ");
		tfLastName = new JTextField(15);
		panelLastName.add(labelLastName);
		panelLastName.add(tfLastName);
		
		/*
		 * Adjusting panel with 3 buttons
		 */
		panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonAdd = new JButton("Add");
		buttonCancel = new JButton("Cancel");
		buttonAdd.addActionListener(this);
		buttonCancel.addActionListener(this);
		
		panelButtons.add(buttonAdd);
		panelButtons.add(buttonCancel);
		
		add(panelIndex);
		add(panelEnrollmentYear);
		add(panelFirstName);
		add(panelLastName);
		add(panelButtons);
	}

	/**
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * Override of actionPerformed method
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == buttonAdd){
			Controller.addNewStudent(tfIndex.getText(), tfEnrollmentYear.getText(), tfFirstName.getText(), tfLastName.getText());
			Controller.refreshFieldsFrameAddNewStudent();
		}else if(source == buttonCancel){
			Controller.disposeFrame(this);
		}else if(source == itemHelp){
			Controller.showAddNewStudentFrameHelpDialog();
		}
	}
	
	/**
	 * Get method for private attribute tfIndex
	 * @return tfIndex, {@link TextField}
	 */
	public JTextField getTfIndex() {
		return tfIndex;
	}
	
	/**
	 * Get method for private attribute tfEnrollmentYear
	 * @return tfEnrollmentYear, {@link TextField}
	 */
	public JTextField getTfEnrollmentYear() {
		return tfEnrollmentYear;
	}
	
	/**
	 * Get method for private attribute tfFirstName
	 * @return tfFirstName, {@link TextField}
	 */
	public JTextField getTfFirstName() {
		return tfFirstName;
	}
	
	/**
	 * Get method for private attribute tfLastName
	 * @return tfLastName, {@link TextField}
	 */
	public JTextField getTfLastName() {
		return tfLastName;
	}
}
