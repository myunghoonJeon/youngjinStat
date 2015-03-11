import java.util.ArrayList;

import javax.swing.JTable;


public class SharedPrint {
	String title="";
	ArrayList<String> namesArr=new ArrayList<>();
	ArrayList<JTable> tablesArr=new ArrayList<>();
	JTable printTable= new JTable();
	/**
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	public JTable getPrintTable() {
		return printTable;
	}
	public void setPrintTable(JTable printTable) {
		this.printTable = printTable;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<String> getNamesArr() {
		return namesArr;
	}
	public void setNamesArr(ArrayList<String> namesArr) {
		this.namesArr = namesArr;
	}
	public ArrayList<JTable> getTablesArr() {
		return tablesArr;
	}
	public void setTablesArr(ArrayList<JTable> tablesArr) {
		this.tablesArr = tablesArr;
	}
	public void addName(String name){
		namesArr.add(name);
	}
	public void addTable(JTable table){
		tablesArr.add(table);
	}
}
