
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

public class WorkVolumeFilteringPage extends JFrame implements ActionListener{
	CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
	JComboBox itemsCombo = new JComboBox(dao.getInventoryFilteringItemsList().toArray());
	JComboBox areaCombo = new JComboBox(dao.getAreaList2().toArray());
	JComboBox scacCombo = new JComboBox(dao.getScacListWork().toArray());
	JComboBox inoutCombo = new JComboBox(dao.getInOutList().toArray());
	JComboBox codeCombo = new JComboBox(dao.getCodeList().toArray());
	JButton outboundSearchBtn = new JButton("SEARCH");
	JButton inboundSearchBtn = new JButton("SEARCH");
	
	JButton outboundBtn = new JButton("OUTBOUND");
	JButton inboundBtn = new JButton("INBOUND");
	
	JLabel itemsLabel = new JLabel("Items :");
	JLabel areaLabel = new JLabel("　　AREA :");
	JLabel periodLabel = new JLabel("　　PUD Period(YYYYMMDD) :");
	JLabel between = new JLabel("~");
	JLabel bigCenterItems = new JLabel("");
	JLabel bigCenterArea = new JLabel("");

	JTextField startPeriod =  new JTextField("",8);
	JTextField endPeriod = new JTextField("",8);
	
	JPanel center;
	
	JPanel mainCenter = new JPanel();
	
	public WorkVolumeFilteringPage() {
		super("");
		super.setVisible(true);
		super.setResizable(false);
		super.setSize(900,650);
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
		outboundSearchBtn.addActionListener(this);
		outboundBtn.addActionListener(this);
		inboundBtn.addActionListener(this);
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
		JPanel jp = new JPanel();
		JPanel jpCenter = new JPanel();
		autoCreateBorderLayout(jp, 20, 20, 10, 10);
		jp.add("Center",mainCenter);
		autoCreateBorderLayout(mainCenter, 300, 300,200, 250);
		mainCenter.add("Center",jpCenter);
		jpCenter.setLayout(new GridLayout(2,1,20,30));
		jpCenter.add(outboundBtn);
		jpCenter.add(inboundBtn);
		super.add(jp);
	}
	
	public void outboundLayout(JPanel mainCenter){
		mainCenter.setLayout(new BorderLayout());
		JPanel north = new JPanel();
		center = new JPanel();
		JPanel bigCenter = new JPanel();
		JPanel bcn = new JPanel();
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
			north.add(new JLabel("PUD PERIOD : "));
			north.add(startPeriod);
			north.add(between);
			north.add(endPeriod);
			north.add(new JLabel("　　"));
			north.add(outboundSearchBtn);
		mainCenter.add("Center",bigCenter);
			bigCenter.setLayout(new BorderLayout());
			bigCenter.add("North",bcn);
			bigCenter.add("Center",center);
			bcn.setLayout(new FlowLayout(FlowLayout.LEFT));
				bcn.add(bigCenterItems);
				bcn.add(bigCenterArea);
			center.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
			validate();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		ArrayList<GblBeans> list = new ArrayList<>();
		
		if(e.getSource() == outboundBtn){
			
		}
		
		if(e.getSource() == inboundBtn){
			
		}
		
		if(e.getSource() == outboundSearchBtn){
			String scac = scacCombo.getSelectedItem().toString();
			String inout = inoutCombo.getSelectedItem().toString();
			String code = codeCombo.getSelectedItem().toString();
			String area = areaCombo.getSelectedItem().toString();
			String begin = startPeriod.getText();
			String end = endPeriod.getText();
			if(inout.equals("OUT")){
				String colName[] = {"PUD","RDD","SCAC","CODE","GBL NO","NAME","US NO","AREA","PCS","GROSS","NET","CUFT","DENSITY"};
				DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
				DefaultTableModel model = new DefaultTableModel(colName,0);
				JTable table = new JTable(model);
				dtcr.setHorizontalAlignment(SwingConstants.CENTER);
				TableColumnModel tcm = table.getColumnModel();
				table.setRowSorter(new TableRowSorter(model));
				JScrollPane scrollpane = new JScrollPane(table);
				scrollpane.setPreferredSize(new Dimension(840,500));
				for(int i=0;i<colName.length;i++){
					tcm.getColumn(i).setCellRenderer(dtcr);
				}
				list = dao.getOutboundGblList(scac, inout, code, area, begin, end);
				int totalCount=0;
				int totalPcs=0;
				int totalGross=0;
				int totalNet=0;
				int totalCuft=0;
				for(int i=0;i<list.size();i++){
					String[] insertRow = {list.get(i).getPud(),list.get(i).getRdd(),list.get(i).getScac(),list.get(i).getCode(),list.get(i).getGblno(),list.get(i).getName()
							, list.get(i).getUsno(),list.get(i).getArea(),list.get(i).getPcs(),list.get(i).getGross(),list.get(i).getNet(),list.get(i).getCuft(), list.get(i).getDensity()};
					totalCount++;
					totalPcs+=Integer.parseInt(list.get(i).getPcs());
					totalGross+=Integer.parseInt(list.get(i).getGross());
					totalNet+=Integer.parseInt(list.get(i).getNet());
					totalCuft+=Integer.parseInt(list.get(i).getCuft());
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
				center.add(scrollpane);
				validate();
			}
		}
		
	}
	public void setTableColumnSize(JTable table,int colNum, int size){
		int prefer = table.getColumnModel().getColumn(colNum).getPreferredWidth();
		table.getColumnModel().getColumn(colNum).setPreferredWidth(prefer+size);
	}
}
