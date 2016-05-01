package view.admin.table_model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import domain.Student;
import model.CollectionOfStudents;
/**
 * Class which extends AbstractTableModel and is used for configuring
 * table in FrameCheckResults class
 * 
 * @author Marko Stevankovic
 * @author Martin Veres
 * @author Filip Stojkovic
 */
public class StudentTableModel extends AbstractTableModel{
	String[] columnNames = {"Last name", "First name", "Index", "Score"};
	private List<Student> students;
	
	/**
	 * public constructor
	 * @param students, representing list of students that should be added to the table
	 */
	public StudentTableModel(List<Student> students) {
		this.students = students;
	}
	
	/**
	 * (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return 4;
	}

	/**
	 * (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return students.size();
	}

	/**
	 * (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Student student = students.get(rowIndex);
		
		switch(columnIndex){
			case 0: return student.getLastName();
			case 1: return student.getFirstName();
			case 2: return student.getIndex();
			case 3: return student.getResult();
			default: return "N/A";
		}
	}
	
	/**
	 * (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int columnIndex) {
		switch(columnIndex){
		case 0: return "Last name";
		case 1: return "First name";
		case 2: return "Index";
		case 3: return "Score";
		default: return "N/A";
		}
	}
	
	/**
	 * Method updating/refresing content of a table
	 */
	public void updateTable(){
		fireTableDataChanged();
	}
}
