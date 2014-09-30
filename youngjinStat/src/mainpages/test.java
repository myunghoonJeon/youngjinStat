package mainpages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import WorkVolumeStat1.MultipleRowHeaderExample;

public class test extends JFrame{
//	CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
	JComboBox areaCombo = new JComboBox<String>();
	JComboBox scacCombo = new JComboBox<String>();
	JComboBox inoutCombo = new JComboBox<String>();
	JComboBox codeCombo = new JComboBox<String>();
	JComboBox hhgUbCombo = new JComboBox<String>();
	JComboBox typeCombo = new JComboBox<String>();
	JButton searchBtn = new JButton("SEARCH");
	
	JTextField startPeriod =  new JTextField("",6);
	JTextField endPeriod = new JTextField("",6);

	JPanel center;
////////////////////////////////////////////////////////////////	
	JPanel mainCenter = new JPanel();
	JPanel north = new JPanel();
	JPanel jp = new JPanel();
	JPanel jpCenter = new JPanel();
	JPanel bigCenter = new JPanel();
	JPanel bcn = new JPanel();
	
	public test(){
		super("work volume stat1");
		super.setVisible(true);
		super.setResizable(false);
		super.setSize(1200,800);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = super.getSize();
		int y = (int)(screen.height/2 - frm.height/2);
		int x = (int)(screen.width/2 - frm.width/2);
		super.setLocation(x,y);
		initLayout();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
			north.setLayout(new GridLayout(1,0));
			JPanel northUp = new JPanel();
			north.add(northUp);
			northUp.setLayout(new FlowLayout(FlowLayout.LEFT));
			northUp.add(new JLabel("SCAC:"));
			northUp.add(scacCombo);
			scacCombo.setPreferredSize(new Dimension(80,30));
			scacCombo.setMaximumRowCount(20);
			northUp.add(new JLabel("AREA:"));
			northUp.add(areaCombo);
			areaCombo.setPreferredSize(new Dimension(80,30));
			northUp.add(new JLabel("IN/OUT:"));
			northUp.add(inoutCombo);
			northUp.add(new JLabel("HHG/UB:"));
			northUp.add(hhgUbCombo);
			northUp.add(new JLabel("CODE:"));
			northUp.add(codeCombo);
			areaCombo.setMaximumRowCount(15);
			northUp.add(new JLabel("TYPE:"));
			northUp.add(typeCombo);
			northUp.add(new JLabel("Period:"));
			northUp.add(startPeriod);
			northUp.add(new JLabel("~"));
			northUp.add(endPeriod);
			northUp.add(searchBtn);
			searchBtn.setPreferredSize(new Dimension(90,30));
		mainCenter.add("Center",bigCenter);
			bigCenter.setLayout(new BorderLayout());
			bigCenter.add("North",bcn);
				bcn.setLayout(new FlowLayout(FlowLayout.LEFT));
				bcn.setPreferredSize(new Dimension(0,25));
			bigCenter.add("Center",center);
			String[][] initStr = new String[23][20];
			for(int i=0;i<23;i++){
				for(int k=0;k<20;k++){
					initStr[i][k] = new String();
					initStr[i][k] = "";
				}
			}
//			MultipleRowHeaderExample frame = new MultipleRowHeaderExample(initStr);
//			JScrollPane js = frame.getWorkVolumeStat1Table();
//			js.setPreferredSize(new Dimension(1150,530));
//			center.add(js);
		super.add(jp);
	}
	
	public static void main(String[] args) {
		test t = new test();
	}
}
