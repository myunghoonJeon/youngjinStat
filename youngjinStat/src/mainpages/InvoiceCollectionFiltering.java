package mainpages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.plaf.metal.MetalBorders.TableHeaderBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import WorkVolumeStat1Table.MultipleRowHeaderExample;

public class InvoiceCollectionFiltering extends JFrame implements ActionListener{

	// //////////////////////////////////////////////////////////////
	int superWide = 900;
	int superHeight = 650;

	// //////////////////////////////////////////////////////////////
	CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
	JComboBox areaCombo = new JComboBox(dao.getAreaList2().toArray());
	JComboBox scacCombo = new JComboBox(dao.getScacListWork().toArray());
	JComboBox inoutCombo = new JComboBox(dao.getAllInOutList().toArray());
	JComboBox codeCombo = new JComboBox(dao.getCodeList().toArray());
	JComboBox hhgUbCombo = new JComboBox(dao.getHhgUbList().toArray());
	JComboBox dateCombo = new JComboBox(dao.getDateList().toArray());
	JComboBox statusCombo = new JComboBox(dao.getStatusList().toArray());
	JButton searchBtn = new JButton("SEARCH");

	JTextField startPeriod = new JTextField("", 6);
	JTextField endPeriod = new JTextField("", 6);

	JPanel center;
	// //////////////////////////////////////////////////////////////
	JPanel mainCenter = new JPanel();
	JPanel north = new JPanel();
	JPanel jp = new JPanel();
	JPanel jpCenter = new JPanel();
	JPanel bigCenter = new JPanel();
	JPanel bcn = new JPanel();

	// //////////////////////////////////////////////////////////////
	public InvoiceCollectionFiltering() {
		super("");
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
		a.add("South", j[3]);
		validate();
	}

	public void initLayout() {
		autoCreateBorderLayout(jp, 20, 20, 10, 10);
		jp.add("Center", mainCenter);
		mainCenter.setLayout(new BorderLayout());
		center = new JPanel();
		bcn.setBackground(Color.white);
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
		northUp.add(new JLabel("IN/OUT:"));
		northUp.add(inoutCombo);
		northUp.add(new JLabel("CODE:"));
		northUp.add(codeCombo);
		northUp.add(new JLabel("DATE:"));
		northUp.add(dateCombo);
		northUp.add(new JLabel("Period:"));
		northUp.add(startPeriod);
		northUp.add(new JLabel("~"));
		northUp.add(endPeriod);
		northUp.add(new JLabel("STATUS"));
		northUp.add(statusCombo);
		northUp.add(searchBtn);
		searchBtn.setPreferredSize(new Dimension(90, 30));
		mainCenter.add("Center", bigCenter);
		bigCenter.setLayout(new BorderLayout());
		bigCenter.add("North", bcn);
		bcn.setLayout(new FlowLayout(FlowLayout.LEFT));
		bcn.setPreferredSize(new Dimension(0, 25));
		bigCenter.add("Center",center);
		beginLayout(center);
		super.add(jp);
	}
	
	public void beginLayout(JPanel jp){
		autoCreateBorderLayout(jp, 10, 10, 30, 30);
		JPanel main = new JPanel();
		jp.add("Center",main);
		JScrollPane js = new JScrollPane();
		ArrayList<String[]> arr = new ArrayList<>();
		js = getTable(arr);
		main.add(js);
		validate();
	}
	
	public JScrollPane getTable(ArrayList<String[]> in){
		String colName[] = {"INVOICE NO","INVOICE DATE","INVOICED AMOUNTS","COLLECTED AMOUNTS","UNCOLLECTED AMOUNTS"};
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		DefaultTableModel model = new DefaultTableModel(colName,20);
		JTable table = new JTable(model);
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(800,430));
		table.setRowHeight(18);
		scrollpane.setBorder(BorderFactory.createEmptyBorder());
		table.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		table.getTableHeader().setBackground(Color.LIGHT_GRAY);
		for(int i=0;i<colName.length;i++){
			tcm.getColumn(i).setCellRenderer(dtcr);
		}
		for(int i=0;i<in.size();i++){
			String[] str = in.get(i);
			model.addRow(str);
		}
		
		validate();
		return scrollpane;
	}
	
	public void setFilteringInformation(){
		String scac = scacCombo.getSelectedItem().toString();
		String inout = inoutCombo.getSelectedItem().toString();
		String code = codeCombo.getSelectedItem().toString();
		String date = dateCombo.getSelectedItem().toString();
		String begin = startPeriod.getText();
		String end = endPeriod.getText();
		String status = statusCombo.getSelectedItem().toString();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==searchBtn){
			setFilteringInformation();
		}
		
	}
	
}
