package mainpages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import Admin.Dialog;
import EachScacInvoiceCollection.GroupableColumnEachscacInvoice;

public class AdminPage extends JFrame implements ActionListener{
	CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
	////////////////////////////////////////////////////////////////
	int superWide = 900;
	int superHeight = 700;
	int ROW_LENGTH = dao.getScacList().size()+1;
	int COLUM_LENGTH = 6;
////////////////////////////////////////////////////////////////
	ArrayList<User> ua = new ArrayList<>();
	JButton addBtn = new JButton("[ ADD USER ]");
	JTextField newIdTF=new JTextField(15);
	JTextField newPwTF=new JTextField(15);
	JComboBox<String> newCombo;
////////////////////////////////////////////////////////////////
	JPanel center;
	JPanel information = new JPanel();
		JLabel informationLabel = new JLabel("　　ALL SCAC TOTAL INVOICE & COLLECTION STATUS (invoice base) 　　　　cut off date : ");
		JLabel cutOffDate = new JLabel("");
	JPanel mainCenter = new JPanel();
	JPanel north = new JPanel();
	JPanel jp = new JPanel();
	JPanel jpCenter = new JPanel();
	JPanel bigCenter = new JPanel();
	JPanel bcn = new JPanel();
	ArrayList<EachScacInvoiceCollectionBeans> esic = new ArrayList<>();
////////////////////////////////////////////////////////////////
	JPanel[] innerPanel;
	
	JTextField[] idtf;
	JTextField[] pwtf;
	
	JButton[] modifyBtn;
	JButton[] deleteBtn;
	
	JComboBox<String>[] levelCombo; 
	DefaultTableModel model;
	JScrollPane js= new JScrollPane();
//////////////////////////////////////////////////////////////	
	public AdminPage() {
		super("ADMIN PAGE");
		super.setVisible(true);
		super.setResizable(false);
		super.setSize(superWide,superHeight);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = super.getSize();
		int y = (int)(screen.height/2 - frm.height/2);
		int x = (int)(screen.width/2 - frm.width/2);
		super.setLocation(x,y);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initLayout();
		addActionListner();
	}
	
	public void addActionListner(){
		addBtn.addActionListener(this);
	}
	
	public void autoCreateBorderLayout(JPanel a,int wx, int ex, int ny, int sy){
		a.setLayout(new BorderLayout());
		JPanel[] j =  new JPanel[4];
		Dimension d=null;
		for(int i=0;i<4;i++){
			j[i] = new JPanel();
			if(i == 0){
				d = new Dimension(wx,0);
			}
			if(i == 1){
				d = new Dimension(ex,0);
			}
			if(i==2){
				d = new Dimension(0,ny);
			}
			if(i==3){
				d = new Dimension(0,sy);
			}
			j[i].setPreferredSize(d);
		}
		a.add("North",j[2]);
		a.add("West",j[0]);
		a.add("East",j[1]);
		a.add("South",j[3]);
		validate();
	}
	public ArrayList<User> getUserList(){
		System.out.println("GET UER LIST");
		ArrayList<User> userArr = new ArrayList<>();
		userArr = dao.getUserList();
		return userArr;
	}
	public void initLayout(){
		autoCreateBorderLayout(jp, 20, 20, 10, 10);
		jp.add("Center",mainCenter);
		autoCreateBorderLayout(mainCenter, 10, 10, 50, 50);
		mainCenter.add("Center",bigCenter);
			JPanel jsp = new JPanel();
			getUserListPanel(jsp);
			jsp.setPreferredSize(new Dimension(800,ua.size()*60));
			bigCenter.add(jsp);
			super.add(jp);
			validate();
/////////////////////////////////////////////////////////////////////////////			
	}
	public void getUserListPanel(JPanel p){
		p.setAlignmentX(CENTER_ALIGNMENT);
		JPanel jp = new JPanel();
		JPanel jpCenter = new JPanel();
		JPanel jpSouth = new JPanel();
		jpSouth.setPreferredSize(new Dimension(800,60));
		ua = getUserList();
		innerPanel = new JPanel[ua.size()];
		idtf = new JTextField[ua.size()];
		pwtf = new JTextField[ua.size()];
		modifyBtn = new JButton[ua.size()];
		deleteBtn = new JButton[ua.size()];
		levelCombo = new JComboBox[ua.size()];
//		jp.setPreferredSize(new Dimension(800,500));
//		jp.setBackground(Color.red);
		jp.setLayout(new BorderLayout());
		jp.add("Center",jpCenter);
			jpCenter.setLayout(new GridLayout(ua.size(),1));
			System.out.println("UA SIZE : "+ua.size());
			for(int i=0;i<ua.size();i++){
				innerPanel[i] = new JPanel();
				innerPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT));
				idtf[i] = new JTextField(ua.get(i).getId(),15);
				pwtf[i] = new JTextField(ua.get(i).getPassword(),15);
				modifyBtn[i] = new JButton("MODIFY");
				deleteBtn[i] = new JButton("DELETE");
				levelCombo[i] = getLevelComboBox(ua.get(i).getLevel());
				///////////////////////////////////
				innerPanel[i].add(new JLabel("ID"));
				innerPanel[i].add(idtf[i]);
				innerPanel[i].add(new JLabel("PASSWORD"));
				innerPanel[i].add(pwtf[i]);
				innerPanel[i].add(levelCombo[i]);
				innerPanel[i].add(modifyBtn[i]);
				innerPanel[i].add(deleteBtn[i]);
				modifyBtn[i].addActionListener(this);
				deleteBtn[i].addActionListener(this);
				jpCenter.add(innerPanel[i]);
			}
		jp.add("South",jpSouth);
			jpSouth.setLayout(new FlowLayout(FlowLayout.LEFT));
			jpSouth.add(new JLabel("NEW ID "));
			jpSouth.add(newIdTF);
			jpSouth.add(new JLabel("PASSWORD "));
			jpSouth.add(newPwTF);
			newCombo = getLevelComboBox("1");
			jpSouth.add(newCombo);
			jpSouth.add(addBtn);
		p.add(jp);
		validate();
	}
	public JComboBox<String> getLevelComboBox(String level){
		ArrayList<String> levelArr = new ArrayList<>();
		for(int i=1;i<7;i++){
			levelArr.add(i+"");
		}
		JComboBox<String> jcb = new JComboBox(levelArr.toArray());
		int levelIndex = levelArr.indexOf(level);
		jcb.setSelectedIndex(levelIndex);
		return jcb;
	}
	public void modify(int index){
		String newId = idtf[index].getText();
		String newPw = pwtf[index].getText();
		String originId = ua.get(index).getId();
		String originPw = ua.get(index).getPassword();
		String newLevel = levelCombo[index].getSelectedItem().toString();
		System.out.println("=======================");
		System.out.println("ORIGIN ID : "+originId);
		System.out.println("NEW ID : "+newId);
		System.out.println("NEW LEVEL : "+newLevel);
		System.out.println("=======================");
		dao.modifyAdmin(originId,originPw,newId,newPw,newLevel);
	}
	public void delete(int index){
		String newId = idtf[index].getText();
		String newPw = pwtf[index].getText();
		String originId = ua.get(index).getId();
		String originPw = ua.get(index).getPassword();
		String newLevel = levelCombo[index].getSelectedItem().toString();
		System.out.println("=======================");
		System.out.println("ID : "+originId);
		System.out.println("=======================");
		dao.deleteAdmin(originId,originPw);
	}
	public void addUser(){
		String newId = newIdTF.getText();
		String newPw = newPwTF.getText();
		String newLevel = newCombo.getSelectedItem().toString();
		ua = getUserList();
		int flag=0;
		for(int i=0;i<ua.size();i++){
			if(ua.get(i).getId().equals(newId)){
				flag=1;
				break;
			}
		}
		if(flag==0){
			dao.addUser(newId,newPw,newLevel);
			Dialog d = new Dialog("[ADD USER] COMPLETE");
		}
		else{
			Dialog d = new Dialog("[ DUPLICATION ] - ID");
		}
		
	}
	public void initPanel(){
		jp.removeAll();
		mainCenter.removeAll();
		bigCenter.removeAll();
		newIdTF.setText("");
		newPwTF.setText("");
		validate();
	}
	public void actionPerformed(ActionEvent e) {
			if(e.getSource()==addBtn){
					addUser();
					initPanel();
					validate();
					initLayout();
					validate();
			}
			else{
				for(int i=0;i<innerPanel.length;i++){
					if(e.getSource() == modifyBtn[i]){
						System.out.println("[[ modify method call index : "+i+" ]]");
						modify(i);
						initPanel();
						validate();
						initLayout();
						validate();
						Dialog d = new Dialog("[MODIFY] COMPLETE");
					}
					if(e.getSource() == deleteBtn[i]){
						System.out.println("[[ delete method call index : "+i+" ]]");
						delete(i);
						initPanel();
						validate();
						initLayout();
						validate();
						Dialog d = new Dialog("[DELETE] COMPLETE");
					}
				}
			}
			
	}//method
	
	public void setTableColumnSize(JTable table,int colNum, int size){
		int prefer = table.getColumnModel().getColumn(colNum).getPreferredWidth();
		table.getColumnModel().getColumn(colNum).setPreferredWidth(prefer+size);
	}
}
