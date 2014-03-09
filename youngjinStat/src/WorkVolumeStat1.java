import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;

import spanTable.*;


public class WorkVolumeStat1 extends JFrame implements ActionListener{
////////////////////////////////////////////////////////////////
	int superWide = 1000;
	int superHeight = 650;
	
////////////////////////////////////////////////////////////////
	CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
	JComboBox areaCombo = new JComboBox(dao.getAreaList2().toArray());
	JComboBox scacCombo = new JComboBox(dao.getScacListWork().toArray());
	JComboBox inoutCombo = new JComboBox(dao.getAllInOutList().toArray());
	JComboBox codeCombo = new JComboBox(dao.getCodeList().toArray());
	JComboBox hhgUbCombo = new JComboBox(dao.getHhgUbList().toArray());
	JButton searchBtn = new JButton("SEARCH");
	
	JTextField startPeriod =  new JTextField("",8);
	JTextField endPeriod = new JTextField("",8);

	JPanel center;
////////////////////////////////////////////////////////////////	
	JPanel mainCenter = new JPanel();
	JPanel north = new JPanel();
	JPanel jp = new JPanel();
	JPanel jpCenter = new JPanel();
	JPanel bigCenter = new JPanel();
	JPanel bcn = new JPanel();
	////////////////////////////////////////////////////////////////
	public WorkVolumeStat1(){
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
			north.setPreferredSize(new Dimension(0,40));
			north.setLayout(new FlowLayout(FlowLayout.LEFT));
			north.add(new JLabel("SCAC : "));
			north.add(scacCombo);
			scacCombo.setMaximumRowCount(20);
			north.add(new JLabel("AREA : "));
			north.add(areaCombo);
			north.add(new JLabel("IN/OUT : "));
			north.add(inoutCombo);
			north.add(new JLabel("HHG/UB : "));
			north.add(hhgUbCombo);
			north.add(new JLabel("CODE : "));
			north.add(codeCombo);
			areaCombo.setMaximumRowCount(15);
			north.add(new JLabel("Period : "));
			north.add(startPeriod);
			north.add(new JLabel("~"));
			north.add(endPeriod);
			north.add(new JLabel("　　"));
			north.add(searchBtn);
		mainCenter.add("Center",bigCenter);
			bigCenter.setLayout(new BorderLayout());
			bigCenter.add("North",bcn);
				bcn.setLayout(new FlowLayout(FlowLayout.LEFT));
				bcn.setPreferredSize(new Dimension(0,25));
			bigCenter.add("Center",center);
			center.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
			MultipleRowHeaderExample frame = new MultipleRowHeaderExample();
			JScrollPane js = frame.getWorkVolumeStat1Table();
			js.setPreferredSize(new Dimension(950,500));
			center.add(js);
		super.add(jp);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ArrayList<GblBeans> list = new ArrayList<>();
		if(e.getSource() == searchBtn){
			
		}//if
		
	}//method
	
	
	public void setTableColumnSize(JTable table,int colNum, int size){
		int prefer = table.getColumnModel().getColumn(colNum).getPreferredWidth();
		table.getColumnModel().getColumn(colNum).setPreferredWidth(prefer+size);
	}
}
