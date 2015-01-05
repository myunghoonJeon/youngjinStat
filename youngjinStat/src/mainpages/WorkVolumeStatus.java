package mainpages;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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

public class WorkVolumeStatus extends JFrame implements ActionListener{
	
	JTable table;
	JTable printTable = new JTable();
	DefaultTableModel model;
	CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
	String title="";
	
	
	JComboBox itemsCombo = new JComboBox(dao.getInventoryFilteringItemsList().toArray());
	JComboBox areaCombo = new JComboBox(dao.getAreaList2().toArray());
	JComboBox scacCombo = new JComboBox(dao.getScacListWork().toArray());
	JComboBox inoutCombo = new JComboBox(dao.getInOutList().toArray());
	JComboBox codeCombo = new JComboBox(dao.getCodeList().toArray());
	JButton searchBtn = new JButton("SEARCH");

	JButton printBtn = new JButton("PRINT");

	
	JButton outboundBtn = new JButton("OUTBOUND");
	JButton inboundBtn = new JButton("INBOUND");
	
	JLabel itemsLabel = new JLabel("Items :");
	JLabel areaLabel = new JLabel("　　AREA :");
	JLabel periodLabel = new JLabel("　　PUD Period(YYYYMMDD) :");
	JLabel bigCenterItems = new JLabel("");
	JLabel bigCenterArea = new JLabel("");

	JTextField pudStartPeriod =  new JTextField("",8);
	JTextField pudEndPeriod = new JTextField("",8);
	JTextField rddStartPeriod = new JTextField("",8);
	JTextField rddEndPeriod = new JTextField("",8);
	JTextField onhandStartPeriod = new JTextField("",8);
	JTextField onhandEndPeriod = new JTextField("",8);
	
	JPanel center;
	////////////////////////////////////////////////////////////////	
	JPanel mainCenter = new JPanel();
	JPanel north = new JPanel();
	JPanel jp = new JPanel();
	JPanel jpCenter = new JPanel();
	JPanel bigCenter = new JPanel();
	JPanel bcn = new JPanel();
	////////////////////////////////////////////////////////////////
	JPanel southMessagePanel = new JPanel();
	JLabel southMessageLabel = new JLabel("");
	////////////////////////////////////////////////////////////////
	
	public WorkVolumeStatus() {
		super("work volume status");
		super.setVisible(true);
		super.setResizable(false);
		super.setSize(1200,750);
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
		outboundBtn.addActionListener(this);
		inboundBtn.addActionListener(this);
		inoutCombo.addActionListener(this);
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
		autoCreateBorderLayout(jp, 20, 20, 10, 10);
		jp.add("Center",mainCenter);
		mainCenter.setLayout(new BorderLayout());
		center = new JPanel();
		bcn.setPreferredSize(new Dimension(0,25));
		bcn.setBackground(Color.white);
		mainCenter.add("North",north);
			north.setPreferredSize(new Dimension(0,40));
			north.setLayout(new FlowLayout(FlowLayout.LEFT));
			north.add(new JLabel("SCAC : "));
			north.add(scacCombo);
			scacCombo.setMaximumRowCount(20);
			north.add(new JLabel("IN/OUT : "));
			north.add(inoutCombo);
			north.add(new JLabel("CODE : "));
			north.add(codeCombo);
			north.add(new JLabel("AREA : "));
			north.add(areaCombo);
			areaCombo.setMaximumRowCount(15);
			north.add(new JLabel("PUD : "));
			north.add(pudStartPeriod);
			north.add(new JLabel("~"));
			north.add(pudEndPeriod);
			north.add(new JLabel("　　"));
			north.add(searchBtn);
			searchBtn.setPreferredSize(new Dimension(90,30));
			north.add(printBtn);
			printBtn.setPreferredSize(new Dimension(90,30));
			
		mainCenter.add("Center",bigCenter);
			bigCenter.setLayout(new BorderLayout());
			bigCenter.add("North",bcn);
			bigCenter.add("Center",center);
			bcn.setLayout(new FlowLayout(FlowLayout.LEFT));
				bcn.add(bigCenterItems);
				bcn.add(bigCenterArea);
				bcn.add(southMessageLabel);
			center.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
			
				
		super.add(jp);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ArrayList<GblBeans> list = new ArrayList<>();
		if(e.getSource() == inoutCombo){
			if(inoutCombo.getSelectedItem().equals("OUT")){//outbound
				north.removeAll();
				center.removeAll();
				center.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
				north.setPreferredSize(new Dimension(0,40));
				north.setLayout(new FlowLayout(FlowLayout.LEFT));
				north.add(new JLabel("SCAC : "));
				north.add(scacCombo);
				scacCombo.setMaximumRowCount(20);
				north.add(new JLabel("IN/OUT : "));
				north.add(inoutCombo);
				north.add(new JLabel("CODE : "));
				north.add(codeCombo);
				north.add(new JLabel("AREA : "));
				north.add(areaCombo);
				areaCombo.setMaximumRowCount(15);
				north.add(new JLabel("PUD : "));
				north.add(pudStartPeriod);
				north.add(new JLabel("~"));
				north.add(pudEndPeriod);
				north.add(new JLabel("　　"));
				north.add(searchBtn);
				north.add(printBtn);
				validate();
			}
			else{//inbound
				JPanel term = new JPanel();
				north.removeAll();
				center.removeAll();
				center.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
				north.setPreferredSize(new Dimension(0,70));
				north.setLayout(new FlowLayout(FlowLayout.LEFT));
				north.add(new JLabel("SCAC : "));
				north.add(scacCombo);
				scacCombo.setMaximumRowCount(20);
				north.add(new JLabel("IN/OUT : "));
				north.add(inoutCombo);
				north.add(new JLabel("CODE : "));
				north.add(codeCombo);
				north.add(new JLabel("AREA : "));
				north.add(areaCombo);
				areaCombo.setMaximumRowCount(15);
				north.add(new JLabel("DEL : "));
				north.add(pudStartPeriod);
				north.add(new JLabel("~"));
				north.add(pudEndPeriod);
				
				term.setPreferredSize(new Dimension(200,30));
				north.add(new JLabel("RDD : "));
				north.add(rddStartPeriod);
				north.add(new JLabel("~"));
				north.add(rddEndPeriod);
				north.add(term);
				north.add(new JLabel("ON HAND : "));
				north.add(onhandStartPeriod);
				north.add(new JLabel("~"));
				north.add(onhandEndPeriod);
				north.add(searchBtn);
				north.add(printBtn);
				validate();
			}
		}
		if(e.getSource() == searchBtn){
			if(inoutCombo.getSelectedItem().equals("OUT")){
				center.removeAll();
				center.setBorder(null);
				String scac = scacCombo.getSelectedItem().toString();
				String inout = inoutCombo.getSelectedItem().toString();
				String code = codeCombo.getSelectedItem().toString();
				String area = areaCombo.getSelectedItem().toString();
				String begin = pudStartPeriod.getText();
				String end = pudEndPeriod.getText();
				String colName[] = {"PUD","RDD","SCAC","CODE","GBL NO","NAME","US NO","AREA","PCS","GROSS","NET","CUFT","DENSITY"};
				DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
				DefaultTableModel model = new DefaultTableModel(colName,0);
				table = new JTable(model);
				dtcr.setHorizontalAlignment(SwingConstants.CENTER);
				table.setRowSorter(new TableRowSorter<DefaultTableModel>(model));
	            table.setFont(new Font( "" , Font.PLAIN, 10 ));
	            table.getTableHeader().setFont( new Font( "" , Font.PLAIN, 10 ));
				TableColumnModel tcm = table.getColumnModel();
//				table.setPreferredScrollableViewportSize(new Dimension(940,500));
				JScrollPane scrollpane = new JScrollPane(table);
				scrollpane.setPreferredSize(new Dimension(1100,550));
				scrollpane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
				
				for(int i=0;i<colName.length;i++){
					tcm.getColumn(i).setCellRenderer(dtcr);
				}
				
				list = dao.getWorkVolumeFilteringOutboundGblList(scac, inout, code, area, begin, end);
				int totalCount=0;
				int totalPcs=0;
				int totalGross=0;
				int totalNet=0;
				int totalCuft=0;
				for(int i=0;i<list.size();i++){
					String[] insertRow = {list.get(i).getPud(),list.get(i).getRdd(),list.get(i).getScac(),list.get(i).getCode(),list.get(i).getGblno(),list.get(i).getName()
							, list.get(i).getUsno(),list.get(i).getArea(),getRoundValue("pcs",list.get(i).getPcs(),1),getRoundValue("gross",list.get(i).getGross(),1),getRoundValue("net",list.get(i).getNet(),1),
							getRoundValue("cuft",list.get(i).getCuft(),1), getRoundValue("density",list.get(i).getDensity(),2)};
					totalCount++;
					try{
						if(list.get(i).getPcs()!=null || !list.get(i).getPcs().equals("")){
							totalPcs+=Integer.parseInt(list.get(i).getPcs());
						}
						if(list.get(i).getGross()!=null || !list.get(i).getGross().equals("")){
							totalGross+=Integer.parseInt(list.get(i).getGross());
						}
						if(list.get(i).getNet()!=null || !list.get(i).getNet().equals("")){
							totalNet+=Integer.parseInt(list.get(i).getNet());
						}
						if(list.get(i).getCuft()!=null || !list.get(i).getCuft().equals("")){
							totalCuft+=Integer.parseInt(list.get(i).getCuft());
						}
					}catch(Exception ee){
						
					}
					model.addRow(insertRow);
				}
				String[] totalRow = {"TOTAL","","","",getRoundValue("",totalCount+"",1)+"건","","","",getRoundValue("",totalPcs+"",1)+"",getRoundValue("",totalGross+"",1),getRoundValue("",totalNet+"",1),getRoundValue("",totalCuft+"",1),""};
				model.addRow(totalRow);
				setTableColumnSize(table, 2,-15);
				setTableColumnSize(table, 3,-25);
				setTableColumnSize(table, 4,30);
				setTableColumnSize(table, 5,65);
				setTableColumnSize(table, 6,-25);
				setTableColumnSize(table, 7,-15);
				setTableColumnSize(table, 8,-35);
				setTableColumnSize(table, 9,-15);
				setTableColumnSize(table, 10,-25);
				setTableColumnSize(table, 11,-25);
				setTableColumnSize(table, 12,-15);
				center.add(scrollpane);
				southMessageLabel.setText("");
				title="-Work Volume Status- [SCAC : "+scacCombo.getSelectedItem()+"] [PROCESS : "+inoutCombo.getSelectedItem()+"] [CODE : "+codeCombo.getSelectedItem()+"] [AREA : "+areaCombo.getSelectedItem()+"] [PUD : "+pudStartPeriod.getText()+" ~ "+pudEndPeriod.getText()+"]";
				validate();
			}//out if
		else if(inoutCombo.getSelectedItem().equals("IN")){
			center.removeAll();
			center.setBorder(null);
			String scac = scacCombo.getSelectedItem().toString();
			String inout = inoutCombo.getSelectedItem().toString();
			String code = codeCombo.getSelectedItem().toString();
			String area = areaCombo.getSelectedItem().toString();
			String pudBegin = pudStartPeriod.getText();
			String pudEnd = pudEndPeriod.getText();
			String rddBegin = rddStartPeriod.getText();
			String rddEnd = rddEndPeriod.getText();
			String onhandBegin = onhandStartPeriod.getText();
			String onhandEnd = onhandEndPeriod.getText();
			String colName[] = {"PUD","RDD","SCAC","CODE","GBL NO","NAME","AREA","PCS","GROSS","NET","CUFT","DEN","ON HAND","SIT IN","SIT OUT","SIT NO"};
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			model = new DefaultTableModel(colName,0);
			table = new JTable(model);
			table.setRowSorter(new TableRowSorter<DefaultTableModel>(model));
            table.setFont(new Font( "" , Font.PLAIN, 10 ));
            table.getTableHeader().setFont( new Font( "" , Font.PLAIN, 10 ));
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumnModel tcm = table.getColumnModel();
			JScrollPane scrollpane = new JScrollPane(table);
			scrollpane.setPreferredSize(new Dimension(1100,550));
			scrollpane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
			for(int i=0;i<colName.length;i++){
				tcm.getColumn(i).setCellRenderer(dtcr);
			}
			list = dao.getInboundGblList(scac, inout, code, area, pudBegin, pudEnd,rddBegin,rddEnd,onhandBegin,onhandEnd);
//			int totalCount=0;
//			int totalPcs=0;
//			int totalGross=0;
//			int totalNet=0;
//			int totalCuft=0;
//			for(int i=0;i<list.size();i++){
//				String[] insertRow = {list.get(i).getPud(),list.get(i).getRdd(),list.get(i).getScac(),list.get(i).getCode(),list.get(i).getGblno(),list.get(i).getName()
//						,list.get(i).getArea(),list.get(i).getPcs(),list.get(i).getGross(),list.get(i).getNet(),list.get(i).getCuft(), list.get(i).getDensity()
//						,list.get(i).getOnhand(),list.get(i).getSitIn(),list.get(i).getSitOut(),list.get(i).getSitNo()};
//				totalCount++;
//				totalPcs+=Integer.parseInt(list.get(i).getPcs());
//				totalGross+=Integer.parseInt(list.get(i).getGross());
//				totalNet+=Integer.parseInt(list.get(i).getNet());
//				totalCuft+=Integer.parseInt(list.get(i).getCuft());
//				model.addRow(insertRow);
//		}
			int totalCount=0;
			int totalPcs=0;
			int totalGross=0;
			int totalNet=0;
			int totalCuft=0;
			for(int i=0;i<list.size();i++){
				String[] insertRow = {
						list.get(i).getPud(),list.get(i).getRdd(),list.get(i).getScac(),list.get(i).getCode(),list.get(i).getGblno(),list.get(i).getName()
						, list.get(i).getArea(), getRoundValue("PCS",list.get(i).getPcs(),1),getRoundValue("g",list.get(i).getGross(),1),getRoundValue("n",list.get(i).getNet(),1),getRoundValue("c",list.get(i).getCuft(),1), 
						getRoundValue("d",list.get(i).getDensity(),2),
						list.get(i).getOnhand(),list.get(i).getSitIn(),list.get(i).getSitOut(),list.get(i).getSitNo()
						};
				totalCount++;
				try{
					if(list.get(i).getPcs()!=null || !list.get(i).getPcs().equals("")){
						totalPcs+=Integer.parseInt(list.get(i).getPcs());
					}
					
					if(list.get(i).getGross()!=null || !list.get(i).getGross().equals("")){
						totalGross+=Integer.parseInt(list.get(i).getGross());
					}
					if(list.get(i).getNet()!=null || !list.get(i).getNet().equals("")){
						totalNet+=Integer.parseInt(list.get(i).getNet());
					}
					if(list.get(i).getCuft()!=null || !list.get(i).getCuft().equals("")){
						totalCuft+=Integer.parseInt(list.get(i).getCuft());
					}
				}catch(Exception ee){
					
				}
				model.addRow(insertRow);
			}
			
			String[] totalRow = {"TOTAL","","",totalCount+"건","","","",totalPcs+"",totalGross+"",totalNet+"",totalCuft+""};
			model.addRow(totalRow);
			setTableColumnSize(table, 2,-35);
			setTableColumnSize(table, 3,-40);//code
			setTableColumnSize(table, 4,30);//GBLNO
			setTableColumnSize(table, 5,65);
			setTableColumnSize(table, 6,-35);
			setTableColumnSize(table, 7,-40);
			setTableColumnSize(table, 8,-25);
			setTableColumnSize(table, 9,-30);//net
			setTableColumnSize(table, 10,-30);
			setTableColumnSize(table, 11,-30);
			setTableColumnSize(table, 12,-15);
			center.add(scrollpane);
			title="-Work Volume Status- [SCAC : "+scacCombo.getSelectedItem()+"] [PROCESS : "+inoutCombo.getSelectedItem()+"] [CODE : "+codeCombo.getSelectedItem()+"]"
					+ " [AREA : "+areaCombo.getSelectedItem()+"] [RDD : "+rddStartPeriod.getText()+"~"+rddEndPeriod.getText()+"] [DEL : "+pudStartPeriod.getText()+"~"+pudEndPeriod.getText()+"][ONHAND : "+onhandEndPeriod.getText()+"~"+onhandEndPeriod.getText()+"]"; 	
			System.out.println(title);
			validate();
		}
			printTable = table;
			System.out.println("mine : "+table.getRowCount());
		}//if
		else if(e.getSource() == printBtn){
//			PrintSolution ps = new PrintSolution();
//			ps.print(this);
			ArrayList<JTable> tableArr = new ArrayList<>();
			ArrayList<String> headers = new ArrayList<>();
			headers.add("");
			System.out.println(title);
			tableArr.add(printTable);
//			PrintSolution ps = new PrintSolution();
			PrintSolution.print(title,tableArr,headers);
		}
		
	}//method
	public Double getDoubleValue(String str){
		return Double.parseDouble(str);
	}
	public String getRoundValue(String type,String str,int flag){
		String result ="";
//		System.out.println("tyep : "+type+"str : ["+str+"]");
		if(str!=null){
			if(flag==1){//int
				int i=0;
				if(str.equals("")){
					i=0;
				}
				else{
				 i = Integer.parseInt(str);
				}
				result = new DecimalFormat("#,##0").format(i);
			}
			else if(flag==2) {
				Double d=0.0;
//				System.out.println("str : "+str);
				if(!str.contains(".")){
					if(str.equals("")){
						str="0.0";
					}
					else{
						str+=".0";
					}
				}
				d = Double.parseDouble(str);
		   		result = new DecimalFormat("#,##0.00").format(d);
			}
		}
		else{
			System.out.println("type : "+type);
			System.out.println("NULL??????");
		}
   		return result;
   	}
	
	public void setTableColumnSize(JTable table,int colNum, int size){
		int prefer = table.getColumnModel().getColumn(colNum).getPreferredWidth();
		table.getColumnModel().getColumn(colNum).setPreferredWidth(prefer+size);
	}
}
