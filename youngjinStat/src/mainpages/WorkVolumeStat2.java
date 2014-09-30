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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import WorkVolumeStat2Table.MultipleRowHeaderWorkStat2;


public class WorkVolumeStat2 extends JFrame implements ActionListener{
	CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
	SharedMethod sm = new SharedMethod();
	////////////////////////////////////////////////////////////////
	int superWide = 1200;
	int superHeight = 650;
	int ROW_LENGTH = dao.getScacList().size()+1;
	int COLUM_LENGTH = 22;
////////////////////////////////////////////////////////////////
	
	JComboBox areaCombo = new JComboBox(dao.getAreaList2().toArray());
	JComboBox scacCombo = new JComboBox(dao.getScacListWork().toArray());
	JComboBox inoutCombo = new JComboBox(dao.getAllInOutList().toArray());
	JComboBox codeCombo = new JComboBox(dao.getCodeList().toArray());
	JComboBox hhgUbCombo = new JComboBox(dao.getHhgUbList().toArray());
	JComboBox typeCombo = new JComboBox(dao.getWorkStat1TypeList().toArray());
	JButton searchBtn = new JButton("SEARCH");
	JButton printBtn = new JButton("PRINT");
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
	////////////////////////////////////////////////////////////////
	String[][] initStr;
	public WorkVolumeStat2(){
		super("work volume stat2");
		super.setVisible(true);
		super.setResizable(false);
		super.setSize(superWide,superHeight);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = super.getSize();
		int y = (int)(screen.height/2 - frm.height/2);
		int x = (int)(screen.width/2 - frm.width/2);
		super.setLocation(x,y);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initStr = new String[dao.getScacList().size()+1][COLUM_LENGTH];
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
		autoCreateBorderLayout(jp, 20, 20, 10, 10);
		jp.add("Center",mainCenter);
		mainCenter.setLayout(new BorderLayout());
		center = new JPanel();
		bcn.setBackground(Color.white);
		mainCenter.add("North",north);
			north.setPreferredSize(new Dimension(0,60));
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
			JScrollPane js= new JScrollPane();
			js = tableLayout(initStr,inoutCombo.getSelectedItem().toString());
			center.add(js);
			super.add(jp);
			validate();
	}
	public JScrollPane initStringArr(String[][] str){
		JScrollPane js = new JScrollPane();
		MultipleRowHeaderWorkStat2 frame = new MultipleRowHeaderWorkStat2(str,inoutCombo.getSelectedItem().toString());
		js = frame.getWorkVolumeStat2Table();
		js.setPreferredSize(new Dimension(1150,480));
		js.setAlignmentY(CENTER_ALIGNMENT);
		return js;
	}
	
	public JScrollPane tableLayout(String[][] str,String type){
		JScrollPane js;
		MultipleRowHeaderWorkStat2 frame = new MultipleRowHeaderWorkStat2(str,inoutCombo.getSelectedItem().toString());
		js = frame.getWorkVolumeStat2Table();
		js.setPreferredSize(new Dimension(1150,480));
		js.setAlignmentY(CENTER_ALIGNMENT);
		return js;
	}
	
	
	
	public String[][] combine(String[][] ib,String[][] ob,String type){
		String[][] result = new String[ROW_LENGTH][COLUM_LENGTH];
		if(type.equals("WEIGHT")){
			for(int i=0;i<ROW_LENGTH;i++){
				for(int j=0;j<COLUM_LENGTH;j++){
					result[i][j] = new String();
					result[i][j] = checkWeightValue(ib[i][j])+checkWeightValue(ob[i][j])+"";
					if(result[i][j].equals("0")){
						result[i][j]="-";
					}
				}
			}
		}
		else if(type.equals("DENSITY")){
			for(int i=0;i<ROW_LENGTH;i++){
				for(int j=0;j<COLUM_LENGTH;j++){
					if(j%2==0){
						result[i][j] = new String();
						result[i][j] = checkWeightValue(ib[i][j])+checkWeightValue(ob[i][j])+"";
						if(result[i][j].equals("0")){
							result[i][j]="-";
						}
					}
					else{
						result[i][j] = new String();
						result[i][j] = checkDensityValue(ib[i][j])+checkDensityValue(ob[i][j])+"";
						if(result[i][j].equals("0.0")){
							result[i][j]="-";
						}
					}
					
				}
			}
		}
		
		return result;
	}
	
	public void getResult(){
		center.removeAll();
		String[][] inboundStr = new String[ROW_LENGTH][COLUM_LENGTH];
		String[][] outboundStr = new String[ROW_LENGTH][COLUM_LENGTH];
		String[][] finalStr = new String[ROW_LENGTH][COLUM_LENGTH];
		String scac = scacCombo.getSelectedItem().toString();
		String area = areaCombo.getSelectedItem().toString();
		String hhgUb = hhgUbCombo.getSelectedItem().toString();
		String code = codeCombo.getSelectedItem().toString();
		String begin = startPeriod.getText();
		String end = endPeriod.getText();
		String type = typeCombo.getSelectedItem().toString();
		if(inoutCombo.getSelectedItem().toString().equals("ALL")){
			inboundStr = dao.getInboundWorkVolumeStat2(scac, area, hhgUb, code, begin, end,type);
			outboundStr = dao.getOutboundWorkVolumeStat2(scac, area, hhgUb, code, begin, end,type);
			finalStr = combine(inboundStr,outboundStr,type);
		}
		if(inoutCombo.getSelectedItem().toString().equals("IN")){
			inboundStr = dao.getInboundWorkVolumeStat2(scac, area, hhgUb, code, begin, end,type);
			finalStr = inboundStr;
		}
		else if(inoutCombo.getSelectedItem().toString().equals("OUT")){
			outboundStr = dao.getOutboundWorkVolumeStat2(scac, area, hhgUb, code, begin, end,type);
			finalStr = outboundStr;
		}
		calcuration2Total(finalStr,type);
		for(int i=0;i<finalStr.length;i++){
			for(int j=0;j<finalStr[i].length;j++){
				if(!finalStr[i][j].equals("-")){
					if(typeCombo.getSelectedItem().equals("WEIGHT")){
						finalStr[i][j] = sm.getRoundValue(finalStr[i][j], 1);
					}
					else{
						finalStr[i][j] = sm.getRoundValue(finalStr[i][j], 2);
					}
				}
			}
		}
//		MultipleRowHeaderWorkStat2 frame = new MultipleRowHeaderWorkStat2(finalStr,inoutCombo.getSelectedItem().toString());
//		JScrollPane js = frame.getWorkVolumeStat1Table();
//		js.setPreferredSize(new Dimension(950,530));
		JScrollPane js = tableLayout(finalStr,type);
		center.add(js);
		validate();
	}
	public int checkWeightValue(String value){
		int result=0;
		if(!value.equals("") && !value.equals("-")){
			result = Integer.parseInt(value);
		}
		else{
			result = 0;
		}
		return result;
	}
	public double checkDensityValue(String value){
		Double result = 0.0;
		if(!value.equals("") && !value.equals("-")){
			DecimalFormat df = new DecimalFormat("#####0.00");
			result = Double.parseDouble(value);
			String test = df.format(result);
			result = Double.parseDouble(test);
		}
		else{
			result = 0.0;
		}
		return result;
	}
	public void totalCalcuration(String[][] arr,String type){
		System.out.println("[ total calcuration method executed ]");
		if(type.equals("WEIGHT")){
			for(int i=0;i<COLUM_LENGTH;i++){
				int temp=0;
				for(int j=0;j<ROW_LENGTH;j++){
					temp += checkWeightValue(arr[j][i]);
				}
				arr[ROW_LENGTH-1][i] = temp+"";
			}
		}
		else if(type.equals("DENSITY")){
			for(int i=0;i<COLUM_LENGTH;i++){
				int intTemp=0;
				double doubleTemp=0.0;
				for(int j=0;j<ROW_LENGTH;j++){
					if(i%2==0){//job
						intTemp += checkWeightValue(arr[j][i]);
					}
					else{
						doubleTemp += checkDensityValue(arr[j][i]);
					}
				}
				if(i%2==0){//job
					arr[ROW_LENGTH-1][i] = intTemp+"";
				}
				else{
					arr[ROW_LENGTH-1][i] = doubleTemp+"";
				}
			}
		}
	}
	public void calcuration2Total(String[][] arr,String type){
		String[][] total = new String[ROW_LENGTH][COLUM_LENGTH];
		if(type.equals("WEIGHT")){
			for(int i=0;i<ROW_LENGTH;i++){
				int totalJob=0;
				int totalWeight = 0;
				for(int j=0;j<COLUM_LENGTH;j++){
					total[i][j] = new String();
					if(j==8 || j==9){//hhg total
						pushHhgUbTotal(i, j, total, arr, type);
					}
					else if(j==18 || j== 19){//ub total
						pushHhgUbTotal(i, j, total, arr, type);
					}
					else if(j==20){//total - job
						pushTotal(8, 18, i, j, total, arr, type);
					}
					else if(j==21){// total - weight
						pushTotal(9, 19, i, j, total, arr, type);
					}
					else if(j==0||j==1 || j==10 ||j==11){//total - normal
						pushArr(j,i, j, total, arr, type);
					}
					else{
						pushArr(-1,i, j, total, arr, type);
					}
				}//for (j)
			}//for(i)
			totalCalcuration(arr, "WEIGHT");
		}//if
		
		else if(type.equals("DENSITY")){
			for(int i=0;i<ROW_LENGTH;i++){
				int totalJob=0;
				double totalWeight = 0.0;
				for(int j=0;j<COLUM_LENGTH;j++){
					total[i][j] = new String();
					if(j==8 || j==9){//hhg total
						pushHhgUbTotal(i, j, total, arr, type);
					}
					else if(j==18 || j== 19){//ub total
						pushHhgUbTotal(i, j, total, arr, type);
					}
					else if(j==20){//total - job
						pushTotal(8, 18, i, j, total, arr, type);
					}
					else if(j==21){// total - weight
						pushTotal(9, 19, i, j, total, arr, type);
					}
					else if(j==0||j==1 || j==10 ||j==11){//total - normal
						pushArr(j,i, j, total, arr, type);
					}
					else{
						pushArr(-1,i, j, total, arr, type);
					}
				}//for (j)
			}//for(i)
			totalCalcuration(arr, "DENSITY");
		}//else if
	}//final method
	public void pushHhgUbTotal(int i, int j,String[][] total,String[][] arr,String type){
		if(j>=2){
			if(total[i][j-2].equals("0")||total[i][j-2].equals("0.0")){
				arr[i][j] = "-";
			}
			else{
				if(j%2==0){
					if(total[i][j-2].contains(".")){
						int temp = (int)(Double.parseDouble(total[i][j-2]));
						arr[i][j] = temp+"";
					}
					else{
						arr[i][j] = total[i][j-2];
					}
				}
				else{
					arr[i][j] = total[i][j-2];
				}
				
			}
		}
	}
	public void pushTotal(int x, int x2,int i,int j,String[][] total, String[][] arr,String type){
		if(type.equals("WEIGHT")){
			int a=checkWeightValue(arr[i][x]);
			int b=checkWeightValue(arr[i][x2]);
			int c = a+b;
			if(c!=0){
				arr[i][j] = c+"";
			}
			else{
				arr[i][j] = "-";
			}
		}
		else if(type.equals("DENSITY")){
			double a=checkDensityValue(arr[i][x]);
			double b=checkDensityValue(arr[i][x2]);
			double c = a+b;
			if(c!=0){
				if(j%2==0){
					arr[i][j] = Integer.parseInt((int)c+"")+"";
				}
				else{
					DecimalFormat df = new DecimalFormat("#####0.00");
					String test = df.format(c);
					String temp = Double.parseDouble(test)+"";
					arr[i][j] = temp;
				}
			}
			else{
				arr[i][j] = "-";
			}
		}
	}
	
	public void pushArr(int x,int i,int j,String[][] total,String[][] arr,String type){
		if(type.equals("WEIGHT")){
			if(j!=x){
				int a=checkWeightValue(arr[i][j]);
				int b=checkWeightValue(total[i][j-2]);
				int c = a+b;
				total[i][j] += c+"";
			}
			else{
				int a = checkWeightValue(arr[i][j]);
				total[i][j] += a+"";
			}
		}
		else if(type.equals("DENSITY")){
//			if(i==x){
//				double a=checkDensityValue(arr[i][j]);
//				double b=checkDensityValue(total[i][j]);
//				double c = a+b;
//				if(j%2==0){
//					total[i][j] += c+"";
//					int temp = (int)(Double.parseDouble(total[i][j]));
//					total[i][j] = temp+"";							
//				}
//				else{
//					total[i][j] += c+"";
//				}
//			}
			if(j!=x){
				double a=checkDensityValue(arr[i][j]);
				double b=checkDensityValue(total[i][j-2]);
				double c = a+b;
				if(j%2==0){
					total[i][j] += c+"";
					int temp = (int)(Double.parseDouble(total[i][j]));
					total[i][j] = temp+"";							
				}
				else{
					total[i][j] += c+"";
				}
			}
			else{//begin column
				double a=checkDensityValue(arr[i][j]);
//				double b=checkDensityValue(total[i][j]);
				double c = a;
				if(j%2==0){
					total[i][j] += c+"";
					int temp = (int)(Double.parseDouble(total[i][j]));
					total[i][j] = temp+"";							
				}
				else{
					total[i][j] += c+"";
				}
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		ArrayList<GblBeans> list = new ArrayList<>();
		if(e.getSource() == searchBtn){
			getResult();
		}//if
		else if(e.getSource() == printBtn){
			PrintSolution ps = new PrintSolution();
			ps.print(this);
		}
		
	}//method
	
	public void setTableColumnSize(JTable table,int colNum, int size){
		int prefer = table.getColumnModel().getColumn(colNum).getPreferredWidth();
		table.getColumnModel().getColumn(colNum).setPreferredWidth(prefer+size);
	}
}