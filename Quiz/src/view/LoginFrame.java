package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.Controller;

/**
 * @author Martin Veres
 * @author Filip Stojkovic
 * @author Marko Stevankovic
 * 
 * Class which displays login form
 */
public class LoginFrame extends JFrame implements ActionListener{
	
	public static final int DEFAULT_WIDTH = 300;
	public static final int DEFAULT_HEIGHT = 180;
	
	private JPanel panelUsername;
	private JPanel panelPassword;
	private JPanel panelButtons;
	
	private JLabel labelUsername;
	private JLabel labelPassword;
	
	private JTextField textFieldUsername;
	private JPasswordField passwordField;
	
	private JButton buttonLogin;
	private JButton buttonCancel;
	
	private JMenuBar menuBar;
	private JMenu menuHelp;
	private JMenuItem menuItemAbout;
	private JMenuItem menuItemReadMe;
	
	/**
	 * An empty constructor of the class LoginFrame
	 */
	public LoginFrame(){
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setTitle("USER LOG IN");
		setLayout(new GridLayout(3, 1));
		
		menuBar = new JMenuBar();
		menuHelp = new JMenu("Help");
		menuItemAbout = new JMenuItem("About");
		menuItemReadMe = new JMenuItem("READ ME");
		
		menuItemAbout.addActionListener(this);
		menuItemReadMe.addActionListener(this);
		
		menuHelp.add(menuItemReadMe);
		menuHelp.add(menuItemAbout);
		menuBar.add(menuHelp);
		
		setJMenuBar(menuBar);
		
		panelUsername = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		labelUsername = new JLabel("Username: ");
		textFieldUsername = new JTextField(15);
		
		panelUsername.add(labelUsername);
		panelUsername.add(textFieldUsername);
		
		panelPassword = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		labelPassword = new JLabel("Password: ");
		passwordField = new JPasswordField(15);
		
		panelPassword.add(labelPassword);
		panelPassword.add(passwordField);
		
		panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		buttonLogin = new JButton("LogIN");
		buttonCancel = new JButton("Cancel");
		
		buttonLogin.addActionListener(this);
		buttonCancel.addActionListener(this);
		
		panelButtons.add(buttonLogin);
		panelButtons.add(buttonCancel);
		
		add(panelUsername);
		add(panelPassword);
		add(panelButtons);
	}

	/**
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == buttonLogin){
			Controller.loginIn(textFieldUsername.getText().trim(), passwordField.getPassword());
		} else if(source == buttonCancel){
			disposeLoginFrame();
		} else if(source == menuItemAbout){
			Controller.showAboutDialog();
		} else if(source == menuItemReadMe){
			Controller.showLogInReadMeDialog();
		}
	}
	
	/**
	 * Method which displays confirmation dialog,
	 * whether users wants to quit application before loging in or not.
	 */
	private void disposeLoginFrame() {
		int option = JOptionPane.showConfirmDialog(
				null,
				"Quit application?",
				"Quiting",
				JOptionPane.YES_NO_OPTION);
		
		if(option == JOptionPane.YES_OPTION)
			this.dispose();
	}
}
