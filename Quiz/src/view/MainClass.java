package view;

import javax.swing.JFrame;

import database_connection.MySQLConnection;

/**
 * @author Martin Veres
 * @author Filip Stojkovic
 * @author Marko Stevankovic
 * 
 * Main class, class from which program starts by displaying login frame
 */
public class MainClass {

	public static void main(String[] args) {
		LoginFrame login = new LoginFrame();
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.setVisible(true);
		login.setLocationRelativeTo(null);
	}
	
}
