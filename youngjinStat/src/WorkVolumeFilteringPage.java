
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
	
	JLabel itemsLabel = new JLabel("Items :");
	JLabel areaLabel = new JLabel("　　AREA :");
	JLabel periodLabel = new JLabel("　　PUD Period(YYYYMMDD) :");
	JLabel between = new JLabel("~");
	JLabel bigCenterItems = new JLabel("");
	JLabel bigCenterArea = new JLabel("");
	
	JTextField startPeriod =  new JTextField("",8);
	JTextField endPeriod = new JTextField("",8);
	
	JPanel center;
	
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
		JPanel jp = new JPanel();
		autoCreateBorderLayout(jp, 20, 20, 10, 10);
		JPanel mainCenter = new JPanel();
		jp.add("Center",mainCenter);
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
		if(e.getSource() == searchBtn){
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
				for(int i=0;i<colName.length;i++){
					tcm.getColumn(i).setCellRenderer(dtcr);
				}
				list = dao.getOutboundGblList(scac, inout, code, area, begin, end);
				for(int i=0;i<list.size();i++){
					System.out.println("list : "+list.get(i).getGblno());
				}
			}
			else if(inout.equals("IN")){
				
			}
		}
		
	}
}
