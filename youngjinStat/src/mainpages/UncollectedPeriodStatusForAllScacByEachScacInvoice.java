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

import EachScacUncollectedStatus.GroupableColumnEachscacUncollectedStatus;

public class UncollectedPeriodStatusForAllScacByEachScacInvoice extends JFrame implements ActionListener {
	GroupableColumnEachscacUncollectedStatus geus;
	CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
	SharedPrint sp = new SharedPrint();
	JTable printTable1=new JTable();
	JTable printTable2=new JTable();
	JTable printTable3=new JTable();
	JTable printTable4=new JTable();
	JTable printTable5=new JTable();
	String title="";
	ArrayList<JTable> printArr = new ArrayList<>();
	ArrayList<String> nameArr = new ArrayList<>();
	////////////////////////////////////////////////////////////////
	int superWide = 1200;
	int superHeight = 800;
	int ROW_LENGTH = dao.getScacList().size()+1;
	int COLUM_LENGTH = 6;
	
////////////////////////////////////////////////////////////////
	JComboBox hhgUbCombo = new JComboBox(dao.getHhgUbList().toArray());
	JComboBox typeCombo = new JComboBox(dao.getWorkStat1TypeList().toArray());
	JTextField beginPeriod = new JTextField("",8);
	JTextField endPeriod = new JTextField("",8);
	JButton searchBtn = new JButton("SEARCH");
	JButton printBtn = new JButton("PRINT");

	JComboBox scacCombo = new JComboBox(dao.getScacListWork().toArray());
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
		JLabel informationLabel = new JLabel("UNCOLLECTED PERIOD STATUS FOR ALL SCAC & BY EACH SCAC  (INVOICE BASE)　　cut off date : ");
		JLabel cutOffDate = new JLabel("");
	JPanel mainCenter = new JPanel();
	JPanel north = new JPanel();
	JPanel jp = new JPanel();
	JPanel jpCenter = new JPanel();
	JPanel bigCenter = new JPanel();
	JPanel bcn = new JPanel();
	JPanel selectMenuP = new JPanel();
	JPanel totalPanel = new JPanel();
	JPanel totalPanelw = new JPanel();
	JPanel totalPanelc = new JPanel();
	
	ArrayList<EachScacUncollectedBeans> esub15 = new ArrayList<>();
	ArrayList<EachScacUncollectedBeans> esub1530 = new ArrayList<>();
	ArrayList<EachScacUncollectedBeans> esub3045 = new ArrayList<>();
	ArrayList<EachScacUncollectedBeans> esub45 = new ArrayList<>();
////////////////////////////////////////////////////////////////
	DefaultTableModel model;
	JScrollPane js= new JScrollPane();
//////////////////////////////////////////////////////////////	
	double tQ=0.0;
	double tIa=0.0;
	double tC=0.0;
	double tUc=0.0;
	double tS=0.0;
	double tA=0.0;
	double tCl=0.0;
	double tNu=0.0;
	double gtQ=0.0;
	double gtIa=0.0;
	double gtC=0.0;
	double gtUc=0.0;
	double gtS=0.0;
	double gtA=0.0;
	double gtCl=0.0;
	double gtNu=0.0;
//////////////////////////////////////////////////////////////	
	public UncollectedPeriodStatusForAllScacByEachScacInvoice(){
		super("uncollected period status for all scac & by each scac (invoice base)");
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
		printBtn.addActionListener(this);
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
//		mainCenter.setBackground(Color.red);
		center = new JPanel();
		bcn.setBackground(Color.white);
		mainCenter.add("North",north);
			north.setPreferredSize(new Dimension(0,60));
//			north.setBackground(Color.red);
			north.setLayout(new BorderLayout());
			JPanel northUp = new JPanel();
			JPanel northDown = new JPanel();
			north.add("Center",northUp);
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
				northUp.add(printBtn);
				printBtn.setPreferredSize(new Dimension(90,30));
				searchBtn.setPreferredSize(new Dimension(90,30));
			north.add("South",northDown);
				northDown.setLayout(new FlowLayout(FlowLayout.LEFT));
				northDown.setPreferredSize(new Dimension(1000,25));
//				northDown.setBackground(Color.yellow);
				northDown.add(information);
				information.setLayout(new FlowLayout(FlowLayout.LEFT));
				information.add(informationLabel);
				information.add(cutOffDate);
		///////////////////////////////////////////////////////
		mainCenter.add("Center",bigCenter);
			bigCenter.setLayout(new BorderLayout());
			bigCenter.add("North",bcn);
				bcn.setLayout(new FlowLayout(FlowLayout.LEFT));
				bcn.setPreferredSize(new Dimension(1000,1));
			bigCenter.add("Center",center);
				center.setLayout(new BorderLayout());
					center.add("Center",innerCenter);
						innerCenter.setPreferredSize(new Dimension(1100,650));
						innerCenter.setBackground(Color.black);
						innerCenter.setLayout(new GridLayout(4,1));
						innerCenter.add(p15);
							p15.setLayout(new BorderLayout());
							p15.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
								p15.add("West",p15w);
								p15w.setPreferredSize(new Dimension(70,0));
								p15w.add(new JLabel("p<=15"));
								p15.add("Center",p15Table);
			//					p15Table.setBackground(Color.black);
			//					p15.add("South",p15Total);
			//					p15Total.setBackground(Color.red);
						innerCenter.add(p1530);
							p1530.setLayout(new BorderLayout());
							p1530.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
								p1530.add("West",p1530w);
								p1530w.add(new JLabel("15<p<=30"));
								p1530w.setPreferredSize(new Dimension(70,0));
								p1530.add("Center",p1530Table);
			//					p1530Table.setBackground(Color.black);
			//					p1530.add("South",p1530Total);
			//					p1530Total.setBackground(Color.red);
						innerCenter.add(p3045);
							p3045.setLayout(new BorderLayout());
							p3045.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
								p3045.add("West",p3045w);
								p3045w.add(new JLabel("30<p<=45"));
								p3045w.setPreferredSize(new Dimension(70,0));
								p3045.add("Center",p3045Table);
			//					p3045Table.setBackground(Color.black);
			//					p3045.add("South",p3045Total);
			//					p3045Total.setBackground(Color.red);
						innerCenter.add(p45);
							p45.setLayout(new BorderLayout());
							p45.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
								p45.add("West",p45w);
								p45w.add(new JLabel("45<p"));
								p45w.setPreferredSize(new Dimension(70,0));
								p45.add("Center",p45Table);
					center.add("South",totalPanel);
						totalPanel.setLayout(new BorderLayout());
						totalPanel.add("West",totalPanelw);
							totalPanelw.setPreferredSize(new Dimension(70,0));
							totalPanelw.add(new JLabel("Total"));
						totalPanel.add("Center",totalPanelc);
							
//					p45Table.setBackground(Color.black);
//					p45.add("South",p45Total);
//					p45Total.setBackground(Color.red);
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
			initTable();
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
	public void initTotalValue(){
		System.out.println("INIT TOTAL VALUE");
		 tQ=0.0;
		 tIa=0.0;
		 tC=0.0;
		 tUc=0.0;
		 tS=0.0;
		 tA=0.0;
		 tCl=0.0;
		 tNu=0.0;
//		 gtQ=0.0;
//		 gtIa=0.0;
//		 gtC=0.0;
//		 gtUc=0.0;
//		 gtS=0.0;
//		 gtA=0.0;
//		 gtCl=0.0;
//		 gtNu=0.0;
	}
	public void initGtotalValue(){
		 gtQ=0.0;
		 gtIa=0.0;
		 gtC=0.0;
		 gtUc=0.0;
		 gtS=0.0;
		 gtA=0.0;
		 gtCl=0.0;
		 gtNu=0.0;
	}
	public JScrollPane getJscrollPane(ArrayList<EachScacUncollectedBeans> esic,String criteria,int checksum){
		JScrollPane jsp = new JScrollPane();
		String colName[] = {"Date","Invoice No","Quantity(GBL)","Invoiced Amount","Collected","Uncollected","Short Paid","Accepted","Claimed","Net Uncollected"};
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		DefaultTableModel model = new DefaultTableModel(colName,0);
		JTable table = new JTable(model);
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		table.setRowSorter(new TableRowSorter(model));
//		table.setFont(new Font( "" , Font.PLAIN, 11 ));
//        table.getTableHeader().setFont( new Font( "" , Font.PLAIN, 11 ));
		JScrollPane scrollpane = new JScrollPane(table);
		
		if(criteria.equals("total")){
			table.setPreferredScrollableViewportSize(new Dimension(1150,45));
			scrollpane.setPreferredSize(new Dimension(1100,45));
		}
		else{
			table.setPreferredScrollableViewportSize(new Dimension(1150,150));
			scrollpane.setPreferredSize(new Dimension(1100,150));	
		}
		
		scrollpane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		initTotalValue(); // init Total Parameter
		int flag = 0;
		double testValue=0;
		for(int i=0;i<esic.size();i++){
			testValue += Double.parseDouble(esic.get(i).getInvoicedAmounts());
    	    String date ="";
    	    String invoiceNo="";
    	    String quantity="";
    	    String invoiceAmounts="";
	   		String collectedAmounts="";
	   		String uncollectedAmounts="";
	   		String shortPaid="";
	   		String accepted="";
	   		String claimed="";
	   		String net="";
	   		date = esic.get(i).getInvoiceDate();
			invoiceNo = esic.get(i).getInvoiceNo();
			invoiceAmounts = esic.get(i).getInvoicedAmounts();
			collectedAmounts = esic.get(i).getCollectedAmounts();
			uncollectedAmounts = esic.get(i).getUncollectedAmounts();
			shortPaid = esic.get(i).getShortpaidAmounts();
			accepted = esic.get(i).getAcceptedAmounts();
			claimed = esic.get(i).getClaimedAmounts();
			net = (getDoubleValue(invoiceAmounts)-getDoubleValue(collectedAmounts)-getDoubleValue(accepted))+"";
			quantity = esic.get(i).getGblQuantity()+"";
			if(esic.get(i).getDatediff()!=null){
    	   int diff = Integer.parseInt(esic.get(i).getDatediff());
    	   
    	   if(criteria.equals("15")){
    		   if(diff<=15){
    			   flag ++;
//    			   System.out.println("[[[[ CRITERIA  : "+criteria+" DETECTED -- INVOICE NO : : "+invoiceNo+" ]]]]]");
    			   model.addRow(new String[]{date,invoiceNo,quantity,getRoundValue(getDoubleValue(invoiceAmounts)),
    					   getRoundValue(getDoubleValue(collectedAmounts)),getRoundValue(getDoubleValue(uncollectedAmounts)),getRoundValue(getDoubleValue(shortPaid)),getRoundValue(getDoubleValue(accepted)),getRoundValue(getDoubleValue(claimed)),getRoundValue(getDoubleValue(net))});
    			   calcTotal(quantity,invoiceAmounts,collectedAmounts,uncollectedAmounts,shortPaid,accepted,claimed,net);
        	   }
    	   }
    	   else if(criteria.equals("1530")){
    		   if(diff>15 && diff <= 30){
//    			   System.out.println("[[[[ CRITERIA  : "+criteria+" DETECTED -- INVOICE NO : : "+invoiceNo+" ]]]]]");
    			   flag ++;
    			   model.addRow(new String[]{date,invoiceNo,quantity,getRoundValue(getDoubleValue(invoiceAmounts)),
    					   getRoundValue(getDoubleValue(collectedAmounts)),getRoundValue(getDoubleValue(uncollectedAmounts)),getRoundValue(getDoubleValue(shortPaid)),getRoundValue(getDoubleValue(accepted)),getRoundValue(getDoubleValue(claimed)),getRoundValue(getDoubleValue(net))});
    			   calcTotal(quantity,invoiceAmounts,collectedAmounts,uncollectedAmounts,shortPaid,accepted,claimed,net);
    		   }
    	   }
    	   else if(criteria.equals("3045")){
    		   
    		   if(diff>30 && diff<=45){
//    			   System.out.println("[[[[ CRITERIA  : "+criteria+" DETECTED -- INVOICE NO : : "+invoiceNo+" ]]]]]");
    			   flag ++;
    			   model.addRow(new String[]{date,invoiceNo,quantity,getRoundValue(getDoubleValue(invoiceAmounts)),
    					   getRoundValue(getDoubleValue(collectedAmounts)),getRoundValue(getDoubleValue(uncollectedAmounts)),getRoundValue(getDoubleValue(shortPaid)),getRoundValue(getDoubleValue(accepted)),getRoundValue(getDoubleValue(claimed)),getRoundValue(getDoubleValue(net))});
    			   calcTotal(quantity,invoiceAmounts,collectedAmounts,uncollectedAmounts,shortPaid,accepted,claimed,net);
    		   }
    	   }
    	   else if(criteria.equals("45")){
    		   if(diff>45){
//    			   System.out.println("[[[[ CRITERIA  : "+criteria+" DETECTED -- INVOICE NO : : "+invoiceNo+" ]]]]]");
    			   flag ++;
    			   model.addRow(new String[]{date,invoiceNo,quantity,getRoundValue(getDoubleValue(invoiceAmounts)),
    					   getRoundValue(getDoubleValue(collectedAmounts)),getRoundValue(getDoubleValue(uncollectedAmounts)),getRoundValue(getDoubleValue(shortPaid)),getRoundValue(getDoubleValue(accepted)),getRoundValue(getDoubleValue(claimed)),getRoundValue(getDoubleValue(net))});
    			   calcTotal(quantity,invoiceAmounts,collectedAmounts,uncollectedAmounts,shortPaid,accepted,claimed,net);
    		   }
    	   }
    	   
       }
			else{
				System.out.println("DATE DIFF NULL !! : "+invoiceNo);
			}
		}//for
//		System.out.println("TEST VALUE : "+testValue);
		if(flag !=0){
			model.addRow(new String[]{"Total","",(int)tQ+"",getRoundValue(tIa),getRoundValue(tC),getRoundValue(tUc),getRoundValue(tS),getRoundValue(tA),getRoundValue(tCl),getRoundValue(tNu)});
		}
		if(criteria.equals("total")){
//			System.out.println("[[[[ CRITERIA  : "+criteria+" DETECTED ]]]]]");
//			System.out.println("<<<<<<<<<<<<< total Click >>>>?>>");
//			System.out.println("totalQuantity : "+gtQ+" "+gtIa);
			String totalQuantity = (int)gtQ+"";
			model.addRow(new String[]{"","",totalQuantity+"",getRoundValue(gtIa),
				   getRoundValue(gtC),getRoundValue(gtUc),getRoundValue(gtS),getRoundValue(gtA),getRoundValue(gtCl),getRoundValue(gtNu)});
		}
		else{
			gtQ += tQ;
			gtIa += tIa;
			gtC += tC;
			gtUc += tUc;
			gtS += tS;
			gtA += tA;
			gtCl += tCl;
			gtNu += tNu;
//			System.out.println("LAST GTIA : "+gtIa);
		}
		if(criteria.equals("15") && checksum==1){
			printTable1 = table;
			printArr.add(printTable1);
			sp.addTable(table);
			nameArr.add("P <= 15");
		}
		else if(criteria.equals("1530") && checksum==1){
			printTable2 = table;
			printArr.add(printTable2);
			sp.addTable(table);
			nameArr.add("15 < p <=30");
		}
		else if(criteria.equals("3045") && checksum==1){
			printTable3 = table;
			printArr.add(printTable3);
			sp.addTable(table);
			nameArr.add("30 < p <=45");
		}
		else if(criteria.equals("45") && checksum==1){
			printTable4 = table;
			printArr.add(printTable4);
			sp.addTable(table);
			nameArr.add("45 < p");
		}
		else if(criteria.equals("total") && checksum==1){
			printTable5 = table;
			printArr.add(printTable5);
			sp.addTable(table);
			nameArr.add("total");
		}
		else{
			System.out.println("??????");
		}
		return scrollpane;
	}
	/////////////////////////////////////////////////////
	   public void calcTotal(String quantity,String invoiceAmounts,String collectedAmounts,String uncollectedAmounts,String shortPaid,String accepted,String claimed,String net){
		   tQ += getDoubleValue(quantity);
		   tIa += getDoubleValue(invoiceAmounts);
		   tC += getDoubleValue(collectedAmounts);
		   tUc += getDoubleValue(uncollectedAmounts);
		   tS += getDoubleValue(shortPaid);
		   tA += getDoubleValue(accepted);
		   tCl += getDoubleValue(claimed);
		   tNu += getDoubleValue(net);
//		   System.out.println("last TIA : "+tIa);
	   }
	   public double getDoubleValue(String str){
			double d=0.0;
			if(str==null || str.equals("")){
				d=0.0;
			}
			else{
				d = Double.parseDouble(str);
			}
			return d;
		}
	   	public String getRoundValue(double d){
	   		String result ="";
	   		result = new DecimalFormat("#,##0.00").format(d);
	   		return result;
	   	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public void setTableColumnWidth(TableColumnModel tcm){
//		tcm.getColumn(2).setPreferredWidth(100);
//		tcm.getColumn(3).setPreferredWidth(100);
//		tcm.getColumn(4).setPreferredWidth(110);
//		tcm.getColumn(5).setPreferredWidth(120);
//		tcm.getColumn(6).setPreferredWidth(60);
//		tcm.getColumn(7).setPreferredWidth(60);
//		tcm.getColumn(8).setPreferredWidth(60);
//		tcm.getColumn(9).setPreferredWidth(60);
	}
	
	public void initTable(){
		String scac = scacCombo.getSelectedItem().toString();
		String code = codeCombo.getSelectedItem().toString();
		String begin = beginPeriod.getText();
		String end = endPeriod.getText();
		String type = typeCombo.getSelectedItem().toString();
		String inOut = inoutCombo.getSelectedItem().toString();
//		clearEsub();
		esub15 = dao.getEachScacUncollected("init", inOut, code, begin, end);
		p15Table.removeAll();
		p1530Table.removeAll();
		p3045Table.removeAll();
		p45Table.removeAll();
//		JScrollPane table1515 = tableLayout(esub15, "15table", "15");
		System.out.println("DETECTED SIZE : "+esub15.size());
//		GroupableColumnEachscacUncollectedStatus ge = new GroupableColumnEachscacUncollectedStatus(esub15, "15");
//		table1515.setPreferredSize(new Dimension(800,100));
//		System.out.println("table15 reset");
		p15Table.add(getJscrollPane(esub15,"15",0));
		p1530Table.add(getJscrollPane(esub15,"1530",0));
		p3045Table.add(getJscrollPane(esub15,"3045",0));
		p45Table.add(getJscrollPane(esub15,"45",0));
		totalPanelc.add(getJscrollPane(esub15, "total",0));
//		p15Table.add(table1515);
//		tableLayout(js,esub);
		
		validate();
	}
	public void getResult(){
		initTotalValue();
		initGtotalValue();
		String scac = scacCombo.getSelectedItem().toString();
		String code = codeCombo.getSelectedItem().toString();
		String begin = beginPeriod.getText();
		String end = endPeriod.getText();
		String type = typeCombo.getSelectedItem().toString();
		String inOut = inoutCombo.getSelectedItem().toString();
//		clearEsub();
		esub15 = dao.getEachScacUncollected(scac, inOut, code, begin, end);
		p15Table.removeAll();
		p1530Table.removeAll();
		p3045Table.removeAll();
		p45Table.removeAll();
		totalPanelc.removeAll();
//		JScrollPane table1515 = tableLayout(esub15, "15table", "15");
		System.out.println("DETECTED SIZE : "+esub15.size());
//		GroupableColumnEachscacUncollectedStatus ge = new GroupableColumnEachscacUncollectedStatus(esub15, "15");
//		table1515.setPreferredSize(new Dimension(800,100));
//		System.out.println("table15 reset");
		p15Table.add(getJscrollPane(esub15,"15",1));
		p1530Table.add(getJscrollPane(esub15,"1530",1));
		p3045Table.add(getJscrollPane(esub15,"3045",1));
		p45Table.add(getJscrollPane(esub15,"45",1));
		totalPanelc.add(getJscrollPane(esub15,"total",1));
//		p15Table.add(table1515);
//		tableLayout(js,esub);
		
		validate();
	}
	public void clearEsub(){
		esub15.clear();
		esub1530.clear();
		esub3045.clear();
		esub45.clear();
	}
	public void actionPerformed(ActionEvent e) {
		ArrayList<GblBeans> list = new ArrayList<>();
		if(e.getSource() == searchBtn){
			printArr.clear();
			nameArr.clear();
			cutOffDate.setText(endPeriod.getText());
			if(inoutCombo.getSelectedItem().equals("IN")){
				informationLabel.setText("EACH SCAC INBOUND UNCOLLECTION STATUS (invoice base) 　　　　cut off date : ");
			}
			else if(inoutCombo.getSelectedItem().equals("OUT")){
				informationLabel.setText("EACH SCAC OUTBOUND UNCOLLECTION STATUS (invoice base) 　　　　cut off date : ");
			}

			else{
				informationLabel.setText("EACH SCAC TOTAL UNCOLLECTION STATUS (invoice base) 　　　　cut off date : ");
			}
			validate();
			getResult();
			validate();
		}//if
		else if(e.getSource() == printBtn){
			System.out.println("print CLICK");
			title = "UNCOLLECTED PERIOD STATUS FOR ALL SCAC & BY EACH SCAC(INVOICE BASE) [SCAC : "+scacCombo.getSelectedItem()+"][PROCESS : "+inoutCombo.getSelectedItem()+"][CODE:"+codeCombo.getSelectedItem()+"][PERIOD:"+beginPeriod.getText()+"~"+endPeriod.getText()+"]";
			PrintSolution.print(title,printArr,nameArr);
		}
	}//method
	
	public void setTableColumnSize(JTable table,int colNum, int size){
		int prefer = table.getColumnModel().getColumn(colNum).getPreferredWidth();
		table.getColumnModel().getColumn(colNum).setPreferredWidth(prefer+size);
	}


}
