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

import EachScacInvoiceCollection.GroupableColumnEachscacInvoice;

public class EachScacInvoiceCollection extends JFrame implements ActionListener{
	CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
	////////////////////////////////////////////////////////////////
	int superWide = 1000;
	int superHeight = 700;
	int ROW_LENGTH = dao.getScacList().size()+1;
	int COLUM_LENGTH = 6;
////////////////////////////////////////////////////////////////
	JComboBox hhgUbCombo = new JComboBox(dao.getHhgUbList().toArray());
	JComboBox typeCombo = new JComboBox(dao.getWorkStat1TypeList().toArray());
	JTextField beginPeriod = new JTextField("",8);
	JTextField endPeriod = new JTextField("",8);
	JButton searchBtn = new JButton("SEARCH");
	JComboBox scacCombo = new JComboBox(dao.getScacList().toArray());
	JComboBox inoutCombo = new JComboBox(dao.getAllInOutList().toArray());
	JComboBox codeCombo = new JComboBox(dao.getCodeList().toArray());
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
	DefaultTableModel model;
	JScrollPane js= new JScrollPane();
//////////////////////////////////////////////////////////////	
	public EachScacInvoiceCollection() {
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
		autoCreateBorderLayout(jp, 20, 20, 10, 10);
		jp.add("Center",mainCenter);
		mainCenter.setLayout(new BorderLayout());
		center = new JPanel();
		bcn.setBackground(Color.white);
		mainCenter.add("North",north);
			north.setPreferredSize(new Dimension(0,50));
			north.setLayout(new BorderLayout());
			JPanel northUp = new JPanel();
			north.add("North",northUp);
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
		///////////////////////////////////////////////////////
		mainCenter.add("Center",bigCenter);
			bigCenter.setLayout(new BorderLayout());
			bigCenter.add("North",bcn);
				bcn.setLayout(new FlowLayout(FlowLayout.LEFT));
				bcn.setPreferredSize(new Dimension(0,35));
				bcn.add(information);
				information.setLayout(new FlowLayout(FlowLayout.LEFT));
				information.add(informationLabel);
				information.add(cutOffDate);
			bigCenter.add("Center",center);
			js = tableLayout(js,esic);
			center.add(js);
			super.add(jp);
			validate();
/////////////////////////////////////////////////////////////////////////////			
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
	public JScrollPane tableLayout(JScrollPane js,ArrayList<EachScacInvoiceCollectionBeans> esic){
		js.removeAll();
		GroupableColumnEachscacInvoice ge = new GroupableColumnEachscacInvoice();
		String colName[] = ge.getJobWeight();
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		model = new DefaultTableModel(colName,0);
		String code = "";
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
		String status="";
		int tq=0;
		double ta=0;
		double tc=0;
		double tu=0;
		double ts=0;
		double tac=0;
		double tcl=0;
		double tnu=0;
		center.removeAll();
		for(int i=0;i<esic.size();i++){
			code =codeCombo.getSelectedItem().toString();
			date=esic.get(i).getInvoiceDate();
			invoiceNo = esic.get(i).getInvoiceNo();
			invoiceAmounts = esic.get(i).getInvoicedAmounts();
			collectedAmounts = esic.get(i).getCollectedAmounts();
			uncollectedAmounts = esic.get(i).getUncollectedAmounts();
			shortPaid = esic.get(i).getShortpaidAmounts();
			accepted = esic.get(i).getAcceptedAmounts();
			claimed = esic.get(i).getClaimedAmounts();
			net = getDoubleValue(collectedAmounts)+getDoubleValue(accepted)-getDoubleValue(shortPaid)+"";
			quantity = esic.get(i).getGblQuantity()+"";
			status=esic.get(i).getStatus();
			if(code.equals("ALL")){
				System.out.println("[NET : "+net+"]");
				String[] input = {date,invoiceNo,quantity,getRoundValue(getDoubleValue(invoiceAmounts)),getRoundValue(getDoubleValue(collectedAmounts)),
						getRoundValue(getDoubleValue(uncollectedAmounts)),getRoundValue(getDoubleValue(shortPaid)),getRoundValue(getDoubleValue(accepted)),getRoundValue(getDoubleValue(claimed)),getRoundValue(getDoubleValue(net)),status};
				model.addRow(input);
			}
			else{
				if(esic.get(i).getCode().equals(code)){// to operation when discover same code
					System.out.println("[NET : "+net+"]");
					String[] input = {date,invoiceNo,quantity,getRoundValue(getDoubleValue(invoiceAmounts)),getRoundValue(getDoubleValue(collectedAmounts)),
							getRoundValue(getDoubleValue(uncollectedAmounts)),getRoundValue(getDoubleValue(shortPaid)),getRoundValue(getDoubleValue(accepted)),
							getRoundValue(getDoubleValue(claimed)),getRoundValue(getDoubleValue(net)),status};
					model.addRow(input);
				}
			}
			tq+=Integer.parseInt(quantity);
			ta+=getDoubleValue(invoiceAmounts);
			tc+=getDoubleValue(collectedAmounts);
			tu+=getDoubleValue(uncollectedAmounts);
			ts+=getDoubleValue(shortPaid);
			tac+=getDoubleValue(accepted);
			tcl+=getDoubleValue(claimed);
			tnu+=getDoubleValue(net);
		}
		model.addRow(new String[]{"TOTAL","",tq+"",getRoundValue(ta),getRoundValue(tc),getRoundValue(tu),getRoundValue(ts),getRoundValue(tac),getRoundValue(tcl),getRoundValue(tnu)});
		JTable table = new JTable(model);
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		table.setRowSorter(new TableRowSorter(model));
		js = new JScrollPane(table);
		js.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		for(int i=0;i<colName.length;i++){
			tcm.getColumn(i).setCellRenderer(dtcr);
		}
		setTableColumnWidth(tcm);
		js.setPreferredSize(new Dimension(950,550));
		center.add(js);
		validate();
		return js;
	}
	   	public String getRoundValue(double d){
	   		String result ="";
	   		result = new DecimalFormat("#,##0.00").format(d);
	   		return result;
	   	}
	public double getDoubleValue(String str){
		double d=0.0;
		if(str==null){
			str="0.0";
		}
		d = Double.parseDouble(str);
		return d;
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
		esic.clear();
		esic = dao.getEachScacInvoiceCollection(scac, inOut, code, begin, end);
		tableLayout(js,esic);
		validate();
	}
	
	public void actionPerformed(ActionEvent e) {
		ArrayList<GblBeans> list = new ArrayList<>();
		if(e.getSource() == searchBtn){
			cutOffDate.setText(endPeriod.getText());
			validate();
			getResult();
			validate();
		}//if
		
	}//method
	
	public void setTableColumnSize(JTable table,int colNum, int size){
		int prefer = table.getColumnModel().getColumn(colNum).getPreferredWidth();
		table.getColumnModel().getColumn(colNum).setPreferredWidth(prefer+size);
	}
}
