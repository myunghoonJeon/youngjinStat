package mainpages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import EachScacInvoiceCollection.GroupableColumnEachscacInvoice;
import EachScacUncollectedStatus.MultipleRowHeaderEachUncollectedStatus;

public class EachScacUncollectedStatus extends JFrame implements ActionListener {

	CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
	////////////////////////////////////////////////////////////////
	int superWide = 1000;
	int superHeight = 750;
	int ROW_LENGTH = dao.getScacList().size()+1;
	int COLUM_LENGTH = 6;
////////////////////////////////////////////////////////////////
	JComboBox hhgUbCombo = new JComboBox(dao.getHhgUbList().toArray());
	JComboBox typeCombo = new JComboBox(dao.getWorkStat1TypeList().toArray());
	JTextField beginPeriod = new JTextField("20140201",8);
	JTextField endPeriod = new JTextField("20140831",8);
	JButton searchBtn = new JButton("SEARCH");
	JComboBox scacCombo = new JComboBox(dao.getScacList().toArray());
	JComboBox inoutCombo = new JComboBox(dao.getAllInOutList().toArray());
	JComboBox codeCombo = new JComboBox(dao.getCodeList().toArray());
////////////////////////////////////////////////////////////////
	JButton b15 = new JButton("P <= 15 days");
	JButton b1530 = new JButton("15 days < P <= 30 days");
	JButton b3045 = new JButton("30 days < P <= 45 days");
	JButton b45 = new JButton("45 days < P");
////////////////////////////////////////////////////////////////
	JTable table15 = new JTable();
	JTable table1530 = new JTable();
	JTable table3045 = new JTable();
	JTable table45 = new JTable();
////////////////////////////////////////////////////////////////
	JPanel center;
	JPanel innerCenter = new JPanel();
	JPanel p15=new JPanel();
	JPanel p15w=new JPanel();
	JPanel p15Table = new JPanel();
	JPanel p15Total = new JPanel();
	JPanel p1530=new JPanel();
	JPanel p1530w=new JPanel();
	JPanel p1530Table = new JPanel();
	JPanel p1530Total = new JPanel();
	JPanel p3045=new JPanel();
	JPanel p3045w=new JPanel();
	JPanel p3045Table = new JPanel();
	JPanel p3045Total = new JPanel();
	JPanel p45 = new JPanel();
	JPanel p45w = new JPanel();
	JPanel p45Table = new JPanel();
	JPanel p45Total = new JPanel();
	JPanel ptotal=new JPanel();
	JPanel information = new JPanel();
		JLabel informationLabel = new JLabel("　　ALL SCAC TOTAL INVOICE & COLLECTION STATUS (invoice base) 　　　　cut off date : ");
		JLabel cutOffDate = new JLabel("");
	JPanel mainCenter = new JPanel();
	JPanel north = new JPanel();
	JPanel jp = new JPanel();
	JPanel jpCenter = new JPanel();
	JPanel bigCenter = new JPanel();
	JPanel bcn = new JPanel();
	JPanel selectMenuP = new JPanel();
	ArrayList<EachScacUncollectedBeans> esub = new ArrayList<>();
	ArrayList<EachScacUncollectedBeans> esub1530 = new ArrayList<>();
	ArrayList<EachScacUncollectedBeans> esub3045 = new ArrayList<>();
	ArrayList<EachScacUncollectedBeans> esub45 = new ArrayList<>();
////////////////////////////////////////////////////////////////
	DefaultTableModel model;
	JScrollPane js= new JScrollPane();
//////////////////////////////////////////////////////////////	
	public EachScacUncollectedStatus(){
		super("");
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
		searchBtn.addActionListener(this);
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
	
	public void initLayout(){
		jp.add("Center",mainCenter);
		mainCenter.setLayout(new BorderLayout());
		center = new JPanel();
		bcn.setBackground(Color.white);
		mainCenter.add("North",north);
			north.setPreferredSize(new Dimension(0,70));
			north.setLayout(new GridLayout(2,0));
			JPanel northUp = new JPanel();
			JPanel northDown = new JPanel();
			north.add(northUp);
				northUp.setLayout(new FlowLayout(FlowLayout.LEFT));
				northUp.add(new JLabel("SCAC:"));
				northUp.add(scacCombo);
				northUp.add(new JLabel("IN/OUT"));
				northUp.add(inoutCombo);
				northUp.add(new JLabel("CODE"));
				northUp.add(codeCombo);
				northUp.add(new JLabel("PERIOD:"));
				northUp.add(beginPeriod);
				northUp.add(new JLabel("~"));
				northUp.add(endPeriod);
				northUp.add(searchBtn);
				searchBtn.setPreferredSize(new Dimension(90,30));
			north.add(northDown);
				northDown.setLayout(new FlowLayout(FlowLayout.LEFT));
				northDown.add(information);
				information.setLayout(new FlowLayout(FlowLayout.LEFT));
				information.add(informationLabel);
				information.add(cutOffDate);
		///////////////////////////////////////////////////////
		mainCenter.add("Center",bigCenter);
			bigCenter.setLayout(new BorderLayout());
			bigCenter.add("North",bcn);
				bcn.setLayout(new FlowLayout(FlowLayout.LEFT));
				bcn.setPreferredSize(new Dimension(1000,25));
			bigCenter.add("Center",center);
			autoCreateBorderLayout(center, 50, 50, 0, 0);
			center.add("Center",innerCenter);
			innerCenter.setPreferredSize(new Dimension(900,600));
			innerCenter.setLayout(new GridLayout(4,1));
			innerCenter.add(p15);
				p15.setLayout(new BorderLayout());
					p15.add("West",p15w);
					p15w.setPreferredSize(new Dimension(100,0));
					p15w.add(new JLabel("p<=15"));
					p15.add("Center",p15Table);
					p15Table.setBackground(Color.black);
					p15.add("South",p15Total);
					p15Total.setBackground(Color.red);
			innerCenter.add(p1530);
				p1530.setLayout(new BorderLayout());
					p1530.add("West",p1530w);
					p1530w.add(new JLabel("15<p<=30"));
					p1530w.setPreferredSize(new Dimension(100,0));
					p1530.add("Center",p1530Table);
					p1530Table.setBackground(Color.black);
					p1530.add("South",p1530Total);
					p1530Total.setBackground(Color.red);
			innerCenter.add(p3045);
				p3045.setLayout(new BorderLayout());
					p3045.add("West",p3045w);
					p3045w.add(new JLabel("30<p<=45"));
					p3045w.setPreferredSize(new Dimension(100,0));
					p3045.add("Center",p3045Table);
					p3045Table.setBackground(Color.black);
					p3045.add("South",p3045Total);
					p3045Total.setBackground(Color.red);
			innerCenter.add(p45);
				p45.setLayout(new BorderLayout());
					p45.add("West",p45w);
					p45w.add(new JLabel("45<p"));
					p45w.setPreferredSize(new Dimension(100,0));
					p45.add("Center",p45Table);
					p45Table.setBackground(Color.black);
					p45.add("South",p45Total);
					p45Total.setBackground(Color.red);
//			center.add(comp);
//			ArrayList<EachScacUncollectedBeans> test = new ArrayList<>();
//			setLayoutEachPanel(p15);
//			setLayoutEachPanel(p1530);
//			setLayoutEachPanel(p3045);
//			setLayoutEachPanel(p45up);
//			JScrollPane js15 = tableLayout(test,"p>=15","-1","15");
//			js15.setPreferredSize(new Dimension(980,100));
//			p15w.setBackground(Color.gray);
//			p15w.add(new JLabel("p>=15"));
//			p15.add("West",p15w);
//			p15.add("Center",js15);
//			center.add(p15);
			super.add(jp);
			validate();
	}
	public void setLayoutEachPanel(JPanel jp){
		jp.setLayout(new BorderLayout());
	}
	
//	public JScrollPane initStringArr(String[][] str){
//		JScrollPane js = new JScrollPane();
//		MultipleRowHeaderAllscacInvoice frame = new MultipleRowHeaderAllscacInvoice(str);
//		js = frame.getAllScacTotalInvoiceTable();
//		js.setPreferredSize(new Dimension(950,480));
//		js.setAlignmentY(CENTER_ALIGNMENT);
//		return js;
//	}
//////////////////////////////////////////[ table column layout ]//////////////////////////////////////////////////////////////			
	public JScrollPane tableLayout(ArrayList<EachScacUncollectedBeans> esic,String title,String start,String finish){
		JScrollPane jsp = new JScrollPane();
		MultipleRowHeaderEachUncollectedStatus mreu = new MultipleRowHeaderEachUncollectedStatus(esic,title,start,finish);
		jsp = mreu.getEachScacUncollectedStatusTable();
		center.removeAll();
		return jsp;
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public void setTableColumnWidth(TableColumnModel tcm){
		tcm.getColumn(2).setPreferredWidth(100);
		tcm.getColumn(3).setPreferredWidth(100);
		tcm.getColumn(4).setPreferredWidth(110);
		tcm.getColumn(5).setPreferredWidth(120);
		tcm.getColumn(6).setPreferredWidth(60);
		tcm.getColumn(7).setPreferredWidth(60);
		tcm.getColumn(8).setPreferredWidth(60);
		tcm.getColumn(9).setPreferredWidth(60);
	}
	
	public void getResult(){
		String scac = scacCombo.getSelectedItem().toString();
		String code = codeCombo.getSelectedItem().toString();
		String begin = beginPeriod.getText();
		String end = endPeriod.getText();
		String type = typeCombo.getSelectedItem().toString();
		String inOut = inoutCombo.getSelectedItem().toString();
//		clearEsub();
		esub = dao.getEachScacUncollected(scac, inOut, code, begin, end);
		
//		tableLayout(js,esub);
		validate();
	}
//	public void clearEsub(){
//		esub15.clear();
//		esub1530.clear();
//		esub3045.clear();
//		esub45.clear();
//	}
	public void actionPerformed(ActionEvent e) {
		ArrayList<GblBeans> list = new ArrayList<>();
		if(e.getSource() == searchBtn){
			cutOffDate.setText(endPeriod.getText());
			validate();
			getResult();
			validate();
		}//if
		if(e.getSource() == b15){
			cutOffDate.setText(endPeriod.getText());
			validate();
		}
		if(e.getSource() == b1530){
			cutOffDate.setText(endPeriod.getText());
			validate();
		}
		if(e.getSource() == b3045){
			cutOffDate.setText(endPeriod.getText());
			validate();
		}
		if(e.getSource() == b45){
			cutOffDate.setText(endPeriod.getText());
			validate();
		}
		
	}//method
	
	public void setTableColumnSize(JTable table,int colNum, int size){
		int prefer = table.getColumnModel().getColumn(colNum).getPreferredWidth();
		table.getColumnModel().getColumn(colNum).setPreferredWidth(prefer+size);
	}


}
