package mainpages;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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

public class WorkVolumeFilteringPage extends JFrame implements ActionListener{
	CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
	JComboBox itemsCombo = new JComboBox(dao.getInventoryFilteringItemsList().toArray());
	JComboBox areaCombo = new JComboBox(dao.getAreaList2().toArray());
	JComboBox scacCombo = new JComboBox(dao.getScacListWork().toArray());
	JComboBox inoutCombo = new JComboBox(dao.getInOutList().toArray());
	JComboBox codeCombo = new JComboBox(dao.getCodeList().toArray());
	JButton searchBtn = new JButton("SEARCH");
	
	
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
	public WorkVolumeFilteringPage() {
		super("");
		super.setVisible(true);
		super.setResizable(false);
		super.setSize(1000,750);
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
		mainCenter.add("Center",bigCenter);
			bigCenter.setLayout(new BorderLayout());
			bigCenter.add("North",bcn);
			bigCenter.add("Center",center);
			bcn.setLayout(new FlowLayout(FlowLayout.LEFT));
				bcn.add(bigCenterItems);
				bcn.add(bigCenterArea);
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
				north.add(new JLabel("PUD : "));
				north.add(pudStartPeriod);
				north.add(new JLabel("~"));
				north.add(pudEndPeriod);
				north.add(term);
//				term.setPreferredSize(new Dimension(200,30));
				north.add(new JLabel("RDD : "));
				north.add(rddStartPeriod);
				north.add(new JLabel("~"));
				north.add(rddEndPeriod);
				north.add(new JLabel("ON HAND : "));
				north.add(onhandStartPeriod);
				north.add(new JLabel("~"));
				north.add(onhandEndPeriod);
				north.add(searchBtn);
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
				JTable table = new JTable(model);
				dtcr.setHorizontalAlignment(SwingConstants.CENTER);
				TableColumnModel tcm = table.getColumnModel();
				table.setRowSorter(new TableRowSorter(model));
				table.setPreferredScrollableViewportSize(new Dimension(940,500));
//				table.setFont(new Font( "" , Font.PLAIN, 11 ));
//		        table.getTableHeader().setFont( new Font( "" , Font.PLAIN, 11 ));
				JScrollPane scrollpane = new JScrollPane(table);
				scrollpane.setPreferredSize(new Dimension(950,550));
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
							, list.get(i).getUsno(),list.get(i).getArea(),list.get(i).getPcs(),list.get(i).getGross(),list.get(i).getNet(),list.get(i).getCuft(), list.get(i).getDensity()};
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
				String[] totalRow = {"TOTAL","","","",totalCount+"건","","","",totalPcs+"",totalGross+"",totalNet+"",totalCuft+"",""};
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
				scrollpane.setPreferredSize(new Dimension(950,550));
				center.add(scrollpane);
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
			String colName[] = {"PUD","RDD","SCAC","CODE","GBL NO","NAME","AREA","PCS","GROSS","NET","CUFT","DENSITY","ON HAND","SIT IN","SIT OUT","SIT NO"};
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			DefaultTableModel model = new DefaultTableModel(colName,0);
			JTable table = new JTable(model);
			
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumnModel tcm = table.getColumnModel();
			table.setRowSorter(new TableRowSorter(model));
            table.setFont(new Font( "" , Font.PLAIN, 11 ));
            table.getTableHeader().setFont( new Font( "" , Font.PLAIN, 11 ));
			JScrollPane scrollpane = new JScrollPane(table);
			scrollpane.setPreferredSize(new Dimension(950,550));
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
				String[] insertRow = {list.get(i).getPud(),list.get(i).getRdd(),list.get(i).getScac(),list.get(i).getCode(),list.get(i).getGblno(),list.get(i).getName()
						, list.get(i).getUsno(),list.get(i).getArea(),list.get(i).getPcs(),list.get(i).getGross(),list.get(i).getNet(),list.get(i).getCuft(), list.get(i).getDensity()};
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
			
			String[] totalRow = {"TOTAL","","","",totalCount+"건","","","",totalPcs+"",totalGross+"",totalNet+"",totalCuft+""};
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
			validate();
		}
		}//if
		
	}//method
	
	
	public void setTableColumnSize(JTable table,int colNum, int size){
		int prefer = table.getColumnModel().getColumn(colNum).getPreferredWidth();
		table.getColumnModel().getColumn(colNum).setPreferredWidth(prefer+size);
	}
}
