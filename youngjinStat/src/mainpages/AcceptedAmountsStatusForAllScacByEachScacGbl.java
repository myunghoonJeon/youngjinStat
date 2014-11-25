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

import AllScacTotalInvoiceCollectionStatusGBL.AllScacTotalInvoiceCollectionStatusGblBeans;
import EachScacUncollectedStatus.GroupableColumnEachscacUncollectedStatus;

public class AcceptedAmountsStatusForAllScacByEachScacGbl extends JFrame implements ActionListener{

	GroupableColumnEachscacUncollectedStatus geus;
	CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
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
	JPanel code34=new JPanel();
	JPanel code34c=new JPanel();
	JPanel code5=new JPanel();
	JPanel code5c=new JPanel();
	JPanel codeT=new JPanel();
	JPanel codeTc=new JPanel();
	JPanel code7 = new JPanel();
	JPanel code7c=new JPanel();
	JPanel code8 = new JPanel();
	JPanel code8c=new JPanel();
	JPanel codeJ = new JPanel();
	JPanel codeJc=new JPanel();
	JPanel information = new JPanel();
		JLabel informationLabel = new JLabel("ACCEPTED AMOUNTS STATUS FOR ALL SCAC & BY EACH SCAC (GBL BASE) ");
		JLabel scacLabel= new JLabel(" SCAC : ");
		JLabel scacValueLabel = new JLabel("[ ALL ]");
		JLabel cutOffDateLabel = new JLabel("cut off date : ");
		JLabel cutOffDateValueLabel = new JLabel("[ ALL ]");
		JLabel process = new JLabel("PROCESS : ");
		JLabel processLabel = new JLabel("[ ALL ]");
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
	
////////////////////////////////////////////////////////////////
	DefaultTableModel model;
	JScrollPane js= new JScrollPane();
//////////////////////////////////////////////////////////////	
	ArrayList<EachScacAcceptedAmountStatusGblBean> esubOut = new ArrayList<>();
	ArrayList<EachScacAcceptedAmountStatusGblBean> tempEsub = new ArrayList<>();
	ArrayList<EachScacAcceptedAmountStatusGblBean> esubIn = new ArrayList<>();
//////////////////////////////////////////////////////////////	
	public AcceptedAmountsStatusForAllScacByEachScacGbl(){
		super("accepted amounts status for all scac & by each scac (GBL base)");
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
				northUp.add(new JLabel("PERIOD:"));
				northUp.add(beginPeriod);
				northUp.add(new JLabel("~"));
				northUp.add(endPeriod);
				northUp.add(searchBtn);
				searchBtn.setPreferredSize(new Dimension(90,30));
				northUp.add(printBtn);
				printBtn.setPreferredSize(new Dimension(90,30));
		
		
			north.add("South",northDown);
				northDown.setLayout(new FlowLayout(FlowLayout.LEFT));
				northDown.setPreferredSize(new Dimension(1100,25));
//				northDown.setBackground(Color.yellow);
				northDown.add(information);
				information.setLayout(new FlowLayout(FlowLayout.LEFT));
				information.add(informationLabel);
				information.add(process);
				information.add(processLabel);
				information.add(scacLabel);
				information.add(scacValueLabel);
				information.add(cutOffDateLabel);
				information.add(cutOffDateValueLabel);
				
		///////////////////////////////////////////////////////
		mainCenter.add("Center",bigCenter);
			bigCenter.setLayout(new BorderLayout());
			bigCenter.add("North",bcn);
				bcn.setLayout(new FlowLayout(FlowLayout.LEFT));
				bcn.setPreferredSize(new Dimension(1100,1));
			bigCenter.add("Center",center);
				center.setLayout(new BorderLayout());
					center.add("Center",innerCenter);
						innerCenter.setPreferredSize(new Dimension(1100,700));
						innerCenter.setBackground(Color.black);
						innerCenter.setLayout(new GridLayout(6,1));
						innerCenter.add(code34);
							code34.setLayout(new BorderLayout());
							code34.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
							code34.add("North",new JLabel("  CODE 3,4"));
							code34.add("Center",code34c);
								code34c.add(getTable(esubOut));
						innerCenter.add(code5);
							code5.setLayout(new BorderLayout());
							code5.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
							code5.add("North",new JLabel("  CODE 5"));
							code5.add("Center",code5c);
								code5c.add(getTable(esubOut));
						innerCenter.add(codeT);
							codeT.setLayout(new BorderLayout());
							codeT.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
							codeT.add("North",new JLabel("  CODE T"));
							codeT.add("Center",codeTc);
								codeTc.add(getTable(esubOut));
						innerCenter.add(code7);
							code7.setLayout(new BorderLayout());
							code7.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
							code7.add("North",new JLabel("  CODE 7"));
							code7.add("Center",code7c);
								code7c.add(getTable(esubOut));
						innerCenter.add(code8);
							code8.setLayout(new BorderLayout());
							code8.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
							code8.add("North",new JLabel("  CODE 8"));
							code8.add("Center",code8c);
								code8c.add(getTable(esubOut));
						innerCenter.add(codeJ);
							codeJ.setLayout(new BorderLayout());
							codeJ.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
							codeJ.add("North",new JLabel("  CODE J"));
							codeJ.add("Center",codeJc);
								codeJc.add(getTable(esubOut));
			super.add(jp);
			validate();
	}
	
	public JScrollPane getTable(ArrayList<EachScacAcceptedAmountStatusGblBean> bean){
		JScrollPane jsp = new JScrollPane();
		String colName[] = {"Invoice Date","GBL No","Invoiced Amounts","Accepted Amounts","Accepted(%)","Reason for Accepted Amounts"};
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		DefaultTableModel model = new DefaultTableModel(colName,0);
		JTable table = new JTable(model);
		double totalInvoiceAmount=0;
		double totalAcceptedAmount=0;
		int gblCount=0;
		for(int i=0;i<bean.size();i++){
			String invoiceDate = bean.get(i).getInvoiceDate();
			String gblNo = bean.get(i).getGblNo();
			String invoiceAmounts = bean.get(i).getInvoiceAmounts();
			String acceptedAmounts = bean.get(i).getAcceptedAmounts();
			String accepted = getDoubleValue(acceptedAmounts)/getDoubleValue(invoiceAmounts)*100+"";
			String reason = bean.get(i).getReason();
			model.addRow(new String[]{invoiceDate,gblNo,getRoundValue(getDoubleValue(invoiceAmounts)),getRoundValue(getDoubleValue(acceptedAmounts)),getRoundValue(getDoubleValue(accepted))+"%",reason});
			totalInvoiceAmount += getDoubleValue(invoiceAmounts);
			totalAcceptedAmount += getDoubleValue(acceptedAmounts);
		}
		gblCount = bean.size();
		model.addRow(new String[]{"TOTAL",gblCount+"",getRoundValue(totalInvoiceAmount),getRoundValue(totalAcceptedAmount),"",""});
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(1100,90));
//		tcm.getColumn(0).setPreferredWidth(15);
//		tcm.getColumn(1).setPreferredWidth(15);
//		tcm.getColumn(2).setPreferredWidth(15);
//		tcm.getColumn(3).setPreferredWidth(15);
//		tcm.getColumn(4).setPreferredWidth(15);
//		tcm.getColumn(5).setPreferredWidth(200);
		return scrollpane;
	}
	 public double getDoubleValue(String str){
			double d=0.0;
			System.out.println("GETDOUBLEVALUE : "+str);
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
	public void getResult(){
		System.out.println("[[ GET RESULT ]]");
		String scac = scacCombo.getSelectedItem().toString();
		String begin = beginPeriod.getText();
		String end = endPeriod.getText();
		String inOut = inoutCombo.getSelectedItem().toString();
		if(inOut.equals("ALL")){
			System.out.println("-------[[ ALL CASE ]]-------");
			esubOut = dao.getOutboundAcceptedGbl(scac, begin, end);
			System.out.println("[[GET OUTBOUND DATE]]");
			//////////////////////////////////////////////////////////////////////////
			esubIn = dao.getInboundAcceptedGbl(scac, begin, end);
			System.out.println("[[GET INBOUND DATE]]");
			/////////////////////////////////////////////////////////////////////
			esubOut.addAll(esubIn);//combine list
			setTables(esubOut);
		}
		else if(inOut.equals("OUT")){
			System.out.println("[[ IN CASE ]]");
			esubOut = dao.getOutboundAcceptedGbl(scac, begin, end);
			System.out.println("[[GET OUTBOUND DATE]]");
			setTables(esubOut);
		}
		else if(inOut.equals("IN")){
			System.out.println("[[ OUT CASE ]]");
			esubIn = dao.getInboundAcceptedGbl(scac, begin, end);
			System.out.println("[[GET INBOUND DATE]]");
			setTables(esubIn);
		}
	}
	public void setTables(ArrayList<EachScacAcceptedAmountStatusGblBean> bean){
		code34c.removeAll();
		code5c.removeAll();
		codeTc.removeAll();
		code7c.removeAll();
		code8c.removeAll();
		codeJc.removeAll();
		ArrayList<EachScacAcceptedAmountStatusGblBean> bean34 = new ArrayList<>();
		ArrayList<EachScacAcceptedAmountStatusGblBean> bean5 = new ArrayList<>();
		ArrayList<EachScacAcceptedAmountStatusGblBean> beanT = new ArrayList<>();
		ArrayList<EachScacAcceptedAmountStatusGblBean> bean7 = new ArrayList<>();
		ArrayList<EachScacAcceptedAmountStatusGblBean> bean8 = new ArrayList<>();
		ArrayList<EachScacAcceptedAmountStatusGblBean> beanJ = new ArrayList<>();
		EachScacAcceptedAmountStatusGblBean tempBean = new EachScacAcceptedAmountStatusGblBean();
		for(int i=0;i<bean.size();i++){
			tempBean = bean.get(i);
			String code = tempBean.getCode();
			if(code.equals("3")||code.equals("4")){
				bean34.add(tempBean);
			}
			else if(code.equals("5")){
				bean5.add(tempBean);
			}
			else if(code.equals("T")){
				beanT.add(tempBean);
			}
			else if(code.equals("7")){
				bean7.add(tempBean);
			}
			else if(code.equals("8")){
				bean8.add(tempBean);
			}
			else if(code.equals("J")){
				beanJ.add(tempBean);
			}
		}
		code34c.add(getTable(bean34));
		code5c.add(getTable(bean5));
		codeTc.add(getTable(beanT));
		code7c.add(getTable(bean7));
		code8c.add(getTable(bean8));
		codeJc.add(getTable(beanJ));
		validate();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == searchBtn){
			if(!scacCombo.getSelectedItem().equals("ALL")){
				scacValueLabel.setText("[ "+scacCombo.getSelectedItem().toString()+" ]");
				validate();
			}
			else{
				scacValueLabel.setText("[ ALL ]");
				validate();
			}
			if(!inoutCombo.getSelectedItem().equals("ALL")){
				if(inoutCombo.getSelectedItem().equals("OUT")){
					processLabel.setText("[ OUTBOUND ]");
				}
				else{
					processLabel.setText("[ INBOUND ]");
				}
				validate();
			}
			else{
				processLabel.setText("[ ALL ]");
				validate();
			}
			if(!beginPeriod.getText().equals("") && !endPeriod.getText().equals("")){
				cutOffDateValueLabel.setText(endPeriod.getText());
				validate();
			}
			else{
				cutOffDateValueLabel.setText("[ ALL ]");
				validate();
			}
			getResult();
			validate();
		}
		else if(e.getSource() == printBtn){
			PrintSolution ps = new PrintSolution();
			ps.print(this);
		}
	}
}
