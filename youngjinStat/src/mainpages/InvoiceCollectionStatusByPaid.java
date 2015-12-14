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

public class InvoiceCollectionStatusByPaid extends JFrame implements ActionListener {
	////////////////////////////////////////////////////////////////
	JTable printTable=new JTable();
	JTable printTable2 = new JTable();
	String title="";
	ArrayList<JTable> printArr = new ArrayList<>();
	ArrayList<String> nameArr = new ArrayList<>();
	ArrayList<InvoiceCollectionStatusByPaidBeans> list;
	////////////////////////////////////////////////////////////////
	ArrayList<InvoiceCollectionStatusByPaidBeans> ifb = new ArrayList<>();
	////////////////////////////////////////////////////////////////
	int superWide = 1200;
	int superHeight = 700;
	
	// //////////////////////////////////////////////////////////////
	CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
//	JComboBox areaCombo = new JComboBox(dao.getAreaList2().toArray());
	JComboBox scacCombo = new JComboBox(dao.getScacListWork().toArray());
//	JComboBox inoutCombo = new JComboBox(dao.getAllInOutList().toArray());
//	JComboBox codeCombo = new JComboBox(dao.getCodeList().toArray());
//	JComboBox hhgUbCombo = new JComboBox(dao.getHhgUbList().toArray());
//	JComboBox dateCombo = new JComboBox(dao.getDateList().toArray());
//	JComboBox statusCombo = new JComboBox(dao.getStatusList().toArray());
	JButton searchBtn = new JButton("SEARCH");
	JButton printBtn = new JButton("PRINT");
	JTextField startPeriod = new JTextField("", 8);
	JTextField endPeriod = new JTextField("", 8);
	JTextField gblNo = new JTextField("", 12);
	JTextField invoiceNo = new JTextField("",15);
	JPanel center;
	JPanel bottom;
	// //////////////////////////////////////////////////////////////
	JPanel mainCenter = new JPanel();
	JPanel north = new JPanel();
	JPanel jp = new JPanel();
	JPanel jpCenter = new JPanel();
	JPanel bigCenter = new JPanel();
	JPanel bcn = new JPanel();
	
	JLabel informationLabel = new JLabel("[ INVOICE & COLLECTION STATUS (PAID BASED) ]                    cut offdate : ");
	JLabel cutoffLabel = new JLabel();
	// //////////////////////////////////////////////////////////////
	public InvoiceCollectionStatusByPaid() {
		super(" INVOICE COLLECTION ( PAID ) ");
		super.setVisible(true);
		super.setResizable(false);
		super.setSize(superWide, superHeight);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = super.getSize();
		int y = (int) (screen.height / 2 - frm.height / 2);
		int x = (int) (screen.width / 2 - frm.width / 2);
		super.setLocation(x, y);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initLayout();
		addActionListner();
	}

	public void addActionListner() {
		searchBtn.addActionListener(this);
		printBtn.addActionListener(this);
	}

	public void autoCreateBorderLayout(JPanel a, int wx, int ex, int ny, int sy) {
		a.setLayout(new BorderLayout());
		JPanel[] j = new JPanel[4];
		Dimension d = null;
		for (int i = 0; i < 4; i++) {
			j[i] = new JPanel();
			if (i == 0) {
				d = new Dimension(wx, 0);
			}
			if (i == 1) {
				d = new Dimension(ex, 0);
			}
			if (i == 2) {
				d = new Dimension(0, ny);
			}
			if (i == 3) {
				d = new Dimension(0, sy);
			}
			j[i].setPreferredSize(d);
		}
		a.add("North", j[2]);
		a.add("West", j[0]);
		a.add("East", j[1]);
//		a.add("South", j[3]);
		validate();
	}

	public void initLayout() {
		autoCreateBorderLayout(jp, 20, 20, 10, 10);
		jp.add("Center", mainCenter);
		mainCenter.setLayout(new BorderLayout());
		center = new JPanel();
//		bcn.setBackground(Color.white);
		mainCenter.add("North", north);
		north.setPreferredSize(new Dimension(0, 40));
		north.setLayout(new GridLayout(1, 0));
		JPanel northUp = new JPanel();
		north.add(northUp);
		northUp.setLayout(new FlowLayout(FlowLayout.LEFT));
		northUp.add(new JLabel("SCAC:"));
		northUp.add(scacCombo);
		scacCombo.setPreferredSize(new Dimension(70, 30));
		scacCombo.setMaximumRowCount(20);
//		northUp.add(new JLabel("IN/OUT:"));
//		northUp.add(inoutCombo);
//		northUp.add(new JLabel("CODE:"));
//		northUp.add(codeCombo);
//		northUp.add(new JLabel("DATE:"));
//		northUp.add(dateCombo);
//		northUp.add(new JLabel("STATUS"));
//		northUp.add(statusCombo);
		northUp.add(new JLabel("Period:"));
		northUp.add(startPeriod);
		northUp.add(new JLabel("~"));
		northUp.add(endPeriod);
		northUp.add(new JLabel("GBL NO:"));
		northUp.add(gblNo);
		northUp.add(new JLabel("INVOICE NO : "));
		northUp.add(invoiceNo);
		northUp.add(searchBtn);
		northUp.add(printBtn);
		searchBtn.setPreferredSize(new Dimension(90, 30));
		printBtn.setPreferredSize(new Dimension(90, 30));
		mainCenter.add("Center", bigCenter);
		bigCenter.setLayout(new BorderLayout());
		bigCenter.add("North", bcn);
		bcn.setLayout(new FlowLayout(FlowLayout.LEFT));
		bcn.add(informationLabel);
		bcn.add(cutoffLabel);
		bcn.setPreferredSize(new Dimension(0, 25));
		bigCenter.add("Center",center);
		centerLayout(center,1,ifb);
		super.add(jp);
	}
	
	public void centerLayout(JPanel jp,int flag,ArrayList<InvoiceCollectionStatusByPaidBeans> tt){
		autoCreateBorderLayout(jp, 10, 10, 10, 10);
		JScrollPane js = new JScrollPane();
		JScrollPane js2 = new JScrollPane();
		js = getTable(tt,flag).get(0);
		js2 = getTable(tt,flag).get(1);
//		js.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
//		js2.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
		JPanel temp = new JPanel();
		jp.add("Center",temp);
		temp.setLayout(new BorderLayout(0,20));
		temp.add("Center",js);
		temp.add("South",js2);
		validate();
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
   	public String getRoundValue(String s){
   		String result ="";
   		double d = Double.parseDouble(s);
   		result = new DecimalFormat("#,##0.00").format(d);
   		return result;
   	}
   	public int checkIndexPaidGbl(ArrayList<PaidGblCheckBean> arr,String gblNo){
   		int index=-1;
   		for(int i=0;i<arr.size();i++){
   			if(arr.get(i).getGblNo().equals(gblNo)){
   				return i;
   			}
   		}
   		return index;
   	}
	public ArrayList<JScrollPane> getTable(ArrayList<InvoiceCollectionStatusByPaidBeans> arr,int flag){
//		InvoiceCollectionStatusByPaid icsp = new InvoiceCollectionStatusByPaid();
		InvoiceCollectionStatusByPaidBeans paidBean = new InvoiceCollectionStatusByPaidBeans();
		InvoiceCollectionStatusByPaidGblBeans gBean = new InvoiceCollectionStatusByPaidGblBeans();
		double ta=0;
		double tpa=0;
		double td=0;
	
		String colName[] = {"PAID DATE","SCAC","INVOICE DATE","INVOICE NO","INVOICE AMOUNT","GBL NO","GBL AMOUNT","PAID AMOUNT","DIFFERENCE"};
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		DefaultTableModel model;
		String colName2[] = {"TOTAL INVOICE AMOUNT","TOTAL PAID AMOUNT","DIFFERENCE"};
		DefaultTableModel model2;
		if(arr.size()==0){
			model = new DefaultTableModel(colName,25);
			model2 = new DefaultTableModel(colName2,1);
		}
		else{
			model = new DefaultTableModel(colName,0);
			model2 = new DefaultTableModel(colName2,0);
//			for(int i=0;i<arr.size();i++){
//				InvoiceFilteringBeans ifb = arr.get(i);
//				String tempAmounts = getRoundValue(getDoubleValue(ifb.getInvoicedAmounts()+""));
//				String tempNet= getRoundValue(getDoubleValue(ifb.getNet()+""));
//				String tempUncollected = getRoundValue(getDoubleValue(ifb.getUnCollectedAmounts()+""));
//				String[] row = {ifb.getInvoiceNo(),ifb.getInvoicedDate(),tempAmounts,tempNet,tempUncollected};
//				
//				model.addRow(row);
//				ta+=getDoubleValue(ifb.getInvoicedAmounts());
//				tc+=getDoubleValue(ifb.getNet());
//				tuc+=getDoubleValue(ifb.getUnCollectedAmounts());
//			}
			String same = "\"";
			String paidSame = "";
			PaidGblCheckBean pgcb;
			for(int i=0;i<arr.size();i++){
				ArrayList<PaidGblCheckBean> checkArr = new ArrayList<PaidGblCheckBean>();
				String tempGbl="";
				Double tempMoney=0.0; 
				paidBean = arr.get(i);
				tempGbl = paidBean.getGblList().get(0).getGblNo();
				Double totalGblMoney=getDoubleValue(paidBean.getInvoiceAmount());
				Double totalPaidMoney=0.0;
				Double totalDiffMoney = 0.0;
				Double fullMoney=Double.parseDouble(paidBean.getGblList().get(0).getAmount());
				Double paidMoney=Double.parseDouble(paidBean.getGblList().get(0).getPaidAmount());
				Double statusMoney=getDoubleValue(paidMoney+"")-getDoubleValue(fullMoney+"");
				Double tm=0.0;
//				totalDiffMoney += statusMoney;
				totalPaidMoney += paidMoney;
				String paidDate = paidBean.getGblList().get(0).getPaidDate();
				String paidDate2="";
				if(checkIndexPaidGbl(checkArr, tempGbl)==-1){
					pgcb = new PaidGblCheckBean();
					pgcb.setGblNo(tempGbl);
					pgcb.setMoney(statusMoney+"");
					System.out.println(statusMoney+" GBL : "+tempGbl);
					checkArr.add(pgcb);
				}
//				totalGblMoney += fullMoney;
				if(getRoundValue(statusMoney+"").equals("-0.00")){
					statusMoney = 0.0;
				}
				
				tempMoney = statusMoney;
				String[] row = {paidBean.getGblList().get(0).getPaidDate(),paidBean.getTsp(),paidBean.getInvoiceDate(),paidBean.getInvoiceNo(),paidBean.getInvoiceAmount(),paidBean.getGblList().get(0).getGblNo(),
						getRoundValue(paidBean.getGblList().get(0).getAmount()),getRoundValue(paidBean.getGblList().get(0).getPaidAmount()),getRoundValue(statusMoney+"")};
				model.addRow(row);
				for(int j=1;j<paidBean.getGblList().size();j++){
					gBean = paidBean.getGblList().get(j);
					tempGbl = gBean.getGblNo();
					fullMoney = getDoubleValue(gBean.getAmount());
					paidMoney = getDoubleValue(gBean.getPaidAmount());
					statusMoney = paidMoney-fullMoney;
					int indexPaid = checkIndexPaidGbl(checkArr, tempGbl);
					if(indexPaid == -1){
						pgcb = new PaidGblCheckBean();
						pgcb.setGblNo(tempGbl);
						System.out.println(statusMoney+" GBL : "+tempGbl);
						pgcb.setMoney(statusMoney+"");
						checkArr.add(pgcb);
					}
					else{
						tm = getDoubleValue(checkArr.get(indexPaid).getMoney());
						tm += paidMoney;
						statusMoney = tm;
						System.out.println(statusMoney+" GBL : "+tempGbl);
						checkArr.get(indexPaid).setMoney(tm+"");
					}
					tempMoney = statusMoney;
//					totalDiffMoney += statusMoney;
					totalPaidMoney += paidMoney;
					paidDate2 = gBean.getPaidDate();
					
					if(paidDate.equals(paidDate2)){
						 paidSame ="\"";
					}
					else{
						paidSame = paidDate2;
						paidDate = paidDate2;
					}
//					totalGblMoney += fullMoney;
					if(getRoundValue(statusMoney+"").equals("-0.00")){
						statusMoney = 0.0;
					}
					if(getRoundValue(tempMoney+"").equals("-0.00")){
						tempMoney = 0.0;
					}					
					String[] gblRow ={paidSame,same,same,same,same,gBean.getGblNo(),getRoundValue(gBean.getAmount()),getRoundValue(gBean.getPaidAmount()),getRoundValue(tempMoney+"")};
					model.addRow(gblRow);
				}
				String mamuri = "──────────";
				totalDiffMoney = getDoubleValue(totalPaidMoney+"")-getDoubleValue(totalGblMoney+"");
				if(getRoundValue(totalDiffMoney+"").equals("-0.00")){
					totalDiffMoney = 0.00;
				}
				
				String[] totalRow = {mamuri,mamuri,mamuri,mamuri,mamuri,"[　TOTAL　]",getRoundValue(totalGblMoney+""),getRoundValue(totalPaidMoney+""),getRoundValue(totalDiffMoney+"")};
				model.addRow(totalRow);
				
				ta+=getDoubleValue(paidBean.getInvoiceAmount());
				tpa+=totalPaidMoney;
				td+=totalDiffMoney;
				
			}
			String[] tr = {getRoundValue(ta+""),getRoundValue(tpa+""),getRoundValue(td+"")};
			model2.addRow(tr);
		}
		JTable table = new JTable(model);
		JTable table2 = new JTable(model2);
//		table.setRowSorter(new TableRowSorter<DefaultTableModel>(model));
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		TableColumnModel tcm2 = table2.getColumnModel();
		JScrollPane scrollpane = new JScrollPane(table);
		JScrollPane scrollpane2 = new JScrollPane(table2);
		scrollpane.setPreferredSize(new Dimension(800,450));
		scrollpane2.setPreferredSize(new Dimension(800,70));
		table.setRowHeight(20);
		
		scrollpane.setBorder(BorderFactory.createEmptyBorder());
		scrollpane2.setBorder(BorderFactory.createEmptyBorder());
		table.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		table.getTableHeader().setBackground(Color.LIGHT_GRAY);
		table2.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		table2.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		table2.getTableHeader().setBackground(Color.LIGHT_GRAY);
		for(int i=0;i<colName.length;i++){
			tcm.getColumn(i).setCellRenderer(dtcr);
			if(i==0){
				tcm2.getColumn(i).setCellRenderer(dtcr);
			}
		}
		if(flag==1){
			printArr.clear();
			nameArr.clear();
			printTable = table;
			printTable2 = table2;
			printArr.add(printTable);
			printArr.add(printTable2);
			System.out.println(printTable.getRowCount());
			nameArr.add("");
		}
		ArrayList<JScrollPane> scrollpaneArr = new ArrayList<>();
		scrollpaneArr.add(scrollpane);
		scrollpaneArr.add(scrollpane2);
		validate();
		return scrollpaneArr;
	}
	
	public void setFilteringInformation(){
		ArrayList<InvoiceFilteringBeans> arr = new ArrayList<>();
		String scac = scacCombo.getSelectedItem().toString();
		String begin = startPeriod.getText();
		String end = endPeriod.getText();
		String gbl = gblNo.getText();
		String invoice = invoiceNo.getText();
		list = dao.getInvoiceCollectionStatusByPaid(begin,end,scac,gbl,invoice);
		center.removeAll();
//		autoCreateBorderLayout(center, 10, 10, 30, 30);
//		JScrollPane upPane = getTable(list,1).get(0);
//		JScrollPane downPane = getTable(list,1).get(1);
		centerLayout(center,1, list);
//		center.add("Center",upPane);
//		center.add("South",downPane);
		validate();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==searchBtn){
			cutoffLabel.setText(endPeriod.getText());
			title = "INVOICE COLLECTION (PAID) [SCAC : "+scacCombo.getSelectedItem()+"][PERIOD:"+startPeriod.getText()+"~"+endPeriod.getText()+"]";
			setFilteringInformation();
		}
		else if(e.getSource() == printBtn){
			PrintSolution.print(title,printArr,nameArr);
		}
	}
	

}
