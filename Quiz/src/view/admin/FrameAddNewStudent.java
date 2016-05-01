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
	
	public FrameAddNewStudent(){
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
			refreshFields();
		}else if(source == buttonCancel){
			disposeFrameAddNewStudent();
		}else if(source == itemHelp){
			Controller.showAddNewStudentFrameHelpDialog();
		}
	}

	/**
	 * Confirm dialog which asks Administrator 
	 * whether he is sure that he wants to dispose/quit this dialog
	 */
	private void disposeFrameAddNewStudent() {
		int option = JOptionPane.showConfirmDialog(
				null, 
				"Close this window?", 
				"Exit", 
				JOptionPane.YES_NO_OPTION);
		
		if(option == JOptionPane.YES_OPTION)
			this.dispose();
	}

	/**
	 * Method which removes content of all text filds in the frame
	 */
	private void refreshFields() {
		tfIndex.setText(null);
		tfEnrollmentYear.setText(null);
		tfFirstName.setText(null);
		tfLastName.setText(null);
	}
}
