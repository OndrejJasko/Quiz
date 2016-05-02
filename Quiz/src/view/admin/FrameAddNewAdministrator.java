package view.admin;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Controller;

/**
 * Class(Frame) whose purpose is to collect data about new Administrator
 * 
 * @author Marko Stevankovic
 * @author Filip Stojkovic
 * @author Martin Veres
 */
public class FrameAddNewAdministrator extends JFrame implements ActionListener{
	
	public static final int DEFAULT_WIDTH = 300;
	public static final int DEFAULT_HEIGHT = 250;
	
	private static FrameAddNewAdministrator instance;

	private JPanel panelFirstName;
	private JPanel panelLastName;
	private JPanel panelPassword;
	private JPanel panelButtons;
	
	private JMenuBar menuBar;
	private JMenu menuHelp;
	private JMenuItem itemHelp;
	
	private JButton buttonAdd;
	private JButton buttonCancel;
	
	private JLabel labelFirstName;
	private JLabel labelPassword;
	private JLabel labelLastName;
	
	private JTextField tfFirstName;
	private JTextField tfPassword;
	private JTextField tfLastName;
	
	/**
	 * getInstance() method, idea from Singleton pattern
	 * @return {@link FrameAddNewAdministrator}
	 */
	public static FrameAddNewAdministrator getInstance(){
		if(instance == null){
			instance = new FrameAddNewAdministrator();
		}
		return instance;
	}
	
	/**
	 * Private empty constructor
	 */
	private FrameAddNewAdministrator(){
		setTitle("Add new administrator...");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLayout(new GridLayout(4, 1));
		
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
		 * Adjusting panel for retrieving information about administrator's first name
		 */
		panelFirstName = new JPanel(new FlowLayout(FlowLayout.LEFT));
		labelFirstName = new JLabel("First name: ");
		tfFirstName = new JTextField(15);
		panelFirstName.add(labelFirstName);
		panelFirstName.add(tfFirstName);
		
		/*
		 * Adjusting panel for retrieving information about administrator's last name
		 */
		panelLastName = new JPanel(new FlowLayout(FlowLayout.LEFT));
		labelLastName = new JLabel("Last name: ");
		tfLastName = new JTextField(15);
		panelLastName.add(labelLastName);
		panelLastName.add(tfLastName);

		/*
		 * Adjusting panel for retrieving Administrators password
		 */
		panelPassword = new JPanel(new FlowLayout(FlowLayout.LEFT));
		labelPassword = new JLabel("Password: ");
		tfPassword = new JTextField(15);
		panelPassword.add(labelPassword);
		panelPassword.add(tfPassword);
		
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
		
		add(panelFirstName);
		add(panelLastName);
		add(panelPassword);
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
			Controller.addNewAdministrator(tfFirstName.getText(), tfLastName.getText(), tfPassword.getText());
			Controller.refreshFieldsFrameAddNewAdministrator();
		}else if(source == buttonCancel){
			Controller.disposeFrame(this);
			// disposeFrameAddNewAdministrator();
		}else if(source == itemHelp){
			Controller.showAddNewAdministratorFrameHelpDialog();
		}
	}
	
	/**
	 * Get method for tfFirstName attribute
	 * @return tfFirstName
	 */
	public JTextField getTfFirstName() {
		return tfFirstName;
	}
	
	/**
	 * Get method for tfLastName attribute
	 * @return tfLastName
	 */
	public JTextField getTfLastName() {
		return tfLastName;
	}
	
	/**
	 * Get method for tfPassword attribute
	 * @return tfPassword
	 */
	public JTextField getTfPassword() {
		return tfPassword;
	}
}
