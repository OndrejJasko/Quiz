package view.admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.Controller;
import model.CollectionOfStudents;
import view.admin.table_model.StudentTableModel;

/**
 * Class (Frame) which displays students' results in a table
 * 
 * @author Martin Veres
 * @author Filip Stojkovic
 * @author Marko Stevankovic
 */
public class FrameCheckResults extends JFrame implements ActionListener{

	public static final int DEFAULT_WIDTH = 500;
	public static final int DEFAULT_HEIGHT = 500;
	
	private JPanel panelSouth;
	private JPanel panelCenter;
	
	private JTable table;
	private JScrollPane scrollPane;
	
	private JButton buttonOK;
	
	public FrameCheckResults() {
		setTitle("Scores/Resultes");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLayout(new BorderLayout());
		
		/*
		 * Adjusting central panel
		 */
		panelCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		Controller.loadStudentsFromDB();
		
		table = new JTable(new StudentTableModel(CollectionOfStudents.getInstance().getListOfStudents()));
		
		scrollPane = new JScrollPane(table);

		table.setFillsViewportHeight(true);
		
		panelCenter.add(scrollPane);
		
		/*
		 * Adjusting south panel
		 */
		panelSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		buttonOK = new JButton("OK");
		buttonOK.addActionListener(this);
		
		panelSouth.add(buttonOK);
		
		add(panelCenter, BorderLayout.CENTER);
		add(panelSouth, BorderLayout.SOUTH);
	}
	
	/**
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == buttonOK){
			closeWindow();
		}
	}

	/**
	 * Confirm dialog which asks Administrator 
	 * whether he is sure that he wants to dispose/quit this dialog
	 */
	private void closeWindow() {
		int option = JOptionPane.showConfirmDialog(
				null, 
				"Close this window?", 
				"Exit", 
				JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION)
			this.dispose();
	}
}
