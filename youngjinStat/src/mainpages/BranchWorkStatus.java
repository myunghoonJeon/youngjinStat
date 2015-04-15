package mainpages;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;

import WorkVolumeStat1.*;


public class BranchWorkStatus extends JFrame implements ActionListener{
////////////////////////////////////////////////////////////////
	int superWide = 1200;
	int superHeight = 650;
	SharedMethod sm = new SharedMethod();
////////////////////////////////////////////////////////////////
	CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
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
	public BranchWorkStatus(){
		super("branch work status (weight/density)");
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
			northUp.add(printBtn);
				printBtn.setPreferredSize(new Dimension(100,30));
		mainCenter.add("Center",bigCenter);
			bigCenter.setLayout(new BorderLayout());
			bigCenter.add("North",bcn);
				bcn.setLayout(new FlowLayout(FlowLayout.LEFT));
				bcn.setPreferredSize(new Dimension(0,25));
			bigCenter.add("Center",center);
			String[][] initStr = new String[25][20];
			for(int i=0;i<25;i++){
				for(int k=0;k<20;k++){
					initStr[i][k] = new String();
					initStr[i][k] = "";
				}
			}
			MultipleRowHeaderExample frame = new MultipleRowHeaderExample(initStr);
			JScrollPane js = frame.getWorkVolumeStat1Table();
			js.setPreferredSize(new Dimension(1150,530));
			center.add(js);
		super.add(jp);
	}
	public String[][] combine(String[][] ib,String[][] ob){
		String[][] result = new String[25][20];
		for(int i=0;i<25;i++){
			for(int j=0;j<20;j++){
				if(i<11){
					result[i][j] = ib[i][j];
				}
				else{
					result[i][j] = ob[i][j];
				}
			}
		}
		return result;
	}
	public void getResult(){
		center.removeAll();
		WorkVolumeStat1Beans WorkVolumeStat1BeansIn = new WorkVolumeStat1Beans();
		WorkVolumeStat1Beans WorkVolumeStat1BeansOut = new WorkVolumeStat1Beans();
		String[][] inboundStr = new String[25][20];
		String[][] outboundStr = new String[25][20];
		String[][] finalStr = new String[25][20];
		String[][] finalCuft = new String[25][20];
		String[][] tempArr = new String[25][20];
		String scac = scacCombo.getSelectedItem().toString();
		String area = areaCombo.getSelectedItem().toString();
		String hhgUb = hhgUbCombo.getSelectedItem().toString();
		String code = codeCombo.getSelectedItem().toString();
		String begin = startPeriod.getText();
		String end = endPeriod.getText();
		String type = typeCombo.getSelectedItem().toString();
		if(inoutCombo.getSelectedItem().toString().equals("ALL")){
			WorkVolumeStat1BeansIn = dao.getInboundWorkVolumeStat1(scac, area, hhgUb, code, begin, end,type);
			WorkVolumeStat1BeansOut = dao.getOutboundWorkVolumeStat1(scac, area, hhgUb, code, begin, end,type);
			System.out.println("COMBINE");
			finalStr = combine(WorkVolumeStat1BeansIn.getStr(),WorkVolumeStat1BeansOut.getStr());
			if(type.equals("DENSITY")){
				 finalCuft = combine(WorkVolumeStat1BeansIn.getCuftStr(),WorkVolumeStat1BeansOut.getCuftStr());
			}
		}
		else if(inoutCombo.getSelectedItem().toString().equals("IN")){
			WorkVolumeStat1BeansIn = dao.getInboundWorkVolumeStat1(scac, area, hhgUb, code, begin, end,type);
			finalStr = WorkVolumeStat1BeansIn.getStr();
			finalCuft = WorkVolumeStat1BeansIn.getCuftStr();
		}
		else if(inoutCombo.getSelectedItem().toString().equals("OUT")){
			WorkVolumeStat1BeansOut = dao.getOutboundWorkVolumeStat1(scac, area, hhgUb, code, begin, end,type);
			finalStr = WorkVolumeStat1BeansOut.getStr();
			finalCuft = WorkVolumeStat1BeansOut.getCuftStr();
		}
		System.out.println("???"+finalStr[0][19]);
		calcurationTotal(finalStr,"WEIGHT");
		System.out.println("???"+finalStr[0][19]);
		if(type.equals("DENSITY")){
			calcaurationTotalCuft(finalCuft);
			System.out.println("???"+finalStr[0][19]);
			System.out.println("CUFT CALCURATION COMPLETE");
			tempArr = calcurationDensity(finalStr, finalCuft);
			checkMoneyComma(tempArr,"DENSITY");
		}
		else{
			checkMoneyComma(finalStr,"WEIGHT");
			tempArr = finalStr;
		}
		MultipleRowHeaderExample frame = new MultipleRowHeaderExample(tempArr);
		JScrollPane js = frame.getWorkVolumeStat1Table();
		js.setPreferredSize(new Dimension(1150,530));
		center.add(js);
		validate();
	}
	public String[][] calcurationDensity(String[][] weight,String[][] cuft){
		String[][] result = new String[25][20];
		for(int i=0;i<25;i++){
			for(int j=0;j<20;j++){
				result[i][j]="-";
			}
		}
		double tempWeight=0;
		double tempCuft=0;
		double density;
		for(int i=0;i<25;i++){
			for(int j=0;j<20;j++){
				if(j%2==1){//weight case
					if(!weight[i][j].equals("-")&&!weight[i][j].equals("")&&weight[i][j]!=null&&!cuft[i][j].equals("-")&&!cuft[i][j].equals("")&&cuft[i][j]!=null){
						tempWeight = Double.parseDouble(weight[i][j]);
						tempCuft = Double.parseDouble(cuft[i][j]);
						if(tempWeight!=0&&tempCuft!=0){
							density = tempWeight/tempCuft;
							result[i][j] = density+"";
						}
						else{
							result[i][j] = "-";
						}
					}
					else{
						System.out.println("======================");
						System.out.println("[i] : "+i+" [j] : "+j);
						System.out.println("WEIGHT : "+weight[i][j]);
						System.out.println("CUFT : "+cuft[i][j]);
						System.out.println("======================");
						result[i][j] = "-";
					}
				}
			}
		}
//		for(int i=0;i<23;i++){
//			for(int j=0;j<20;j++){
//				System.out.print(result[i][j]);
//			}
//			System.out.println();
//		}
		return result;
	}
	public void checkMoneyComma(String[][] arr,String type){
		int flag=0;
		if(type.equals("WEIGHT")){
			flag = 1;
		}
		else if(type.equals("DENSITY")){
			flag = 2;
		}
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr[i].length;j++){
				if(!arr[i][j].equals("-") && arr[i][j]!=null){
					arr[i][j] = sm.getRoundValue(arr[i][j], flag);
				}
			}
		}
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
	public void calcaurationTotalCuft(String[][] arr){
		String[][] total = new String[25][20];
		String type="WEIGHT";
		for(int i=0;i<25;i++){
			int totalJob=0;
			int totalWeight = 0;
			for(int j=0;j<20;j++){
				total[i][j] = "";
					if(j%2==0 && j<18){
//						totalJob += checkWeightValue(arr[i][j]);
					}
					else if(j%2==1 && j<18){
						totalWeight += checkWeightValue(arr[i][j]);
					}
					if(j==18){
						if(totalJob==0){
							arr[i][j] = "-";
						}
						else{
							arr[i][j] = totalJob+"";
						}
					}
					if(j==19){
						arr[i][j] = totalWeight+"";
					}
					if(i>=0 && i<=4  ){
						pushArr(0, i, j, total, arr, type);
					}
					else if(i==5  ){//hhg total
						pushHhgUbTotal(i, j, total, arr, type);
					}
					else if(i>=6 && i<=9){
						pushArr(5, i, j, total, arr, type);
					}
					else if(i==10){//ub total
						pushHhgUbTotal(i, j, total, arr, type);
					}
					else if(i==11){//in total
						pushTotal(4, 9, i, j, total, arr, type);
					}
					else if(i>=12 && i<=16  ){
						pushArr(11, i, j, total, arr, type);
					}
					else if(i==17){//hhg total
						pushHhgUbTotal(i, j, total, arr, type);
					}
					else if(i>=16 && i<=21){
						pushArr(16, i, j, total, arr, type);
					}
					else if(i==22){//ub total
						pushHhgUbTotal(i, j, total, arr, type);
					}
					else if(i==23){//out total
						pushTotal(15, 20, i, j, total, arr, type);
					}
					else if(i==24){//all total
						pushTotal(10, 21, i, j, total, arr, type);
					}
			}//for (j)
		}//for(i)
	}
	public void calcurationTotal(String[][] arr,String type){
		String[][] total = new String[25][20];
		if(type.equals("WEIGHT")){
			for(int i=0;i<25;i++){
				int totalJob=0;
				int totalWeight = 0;
				for(int j=0;j<20;j++){
					total[i][j] = "";
						if(j%2==0 && j<18){
							totalJob += checkWeightValue(arr[i][j]);
						}
						else if(j%2==1 && j<18){
							totalWeight += checkWeightValue(arr[i][j]);
						}
						if(j==18){
							if(totalJob==0){
								arr[i][j] = "-";
							}
							else{
								arr[i][j] = totalJob+"";
							}
						}
						if(j==19){
							if(totalJob==0){
								arr[i][j] = "-";
							}
							else{
								arr[i][j] = totalWeight+"";
							}
						}
						if(i>=0 && i<=4  ){
							pushArr(0, i, j, total, arr, type);
						}
						else if(i==5  ){//hhg total
							pushHhgUbTotal(i, j, total, arr, type);
						}
						else if(i>=6 && i<=9){
							pushArr(6, i, j, total, arr, type);
						}
						else if(i==10){//ub total
							pushHhgUbTotal(i, j, total, arr, type);
						}
						else if(i==11){//in total
							pushTotal(5, 10, i, j, total, arr, type);
						}
						else if(i>=12 && i<=16  ){
							pushArr(12, i, j, total, arr, type);
						}
						else if(i==17){//hhg total
							pushHhgUbTotal(i, j, total, arr, type);
						}
						else if(i>=18 && i<=21){
							pushArr(18, i, j, total, arr, type);
						}
						else if(i==22){//ub total
							pushHhgUbTotal(i, j, total, arr, type);
						}
						else if(i==23){//out total
							pushTotal(17, 22, i, j, total, arr, type);
						}
						else if(i==24){//all total
							pushTotal(11, 23, i, j, total, arr, type);
						}
				}//for (j)
			}//for(i)
		}//if
		else if(type.equals("DENSITY")){}//if
		
	}//final method

	public void pushHhgUbTotal(int i, int j,String[][] total,String[][] arr,String type){
		if(total[i-1][j].equals("0")||total[i-1][j].equals("0.0")){
			arr[i][j] = "-";
		}
		else{
			if(j%2==0){
				if(total[i-1][j].contains(".")){
					int temp = (int)(Double.parseDouble(total[i-1][j]));
					arr[i][j] = temp+"";
				}
				else{
					arr[i][j] = total[i-1][j];
				}
			}
			else{
				arr[i][j] = total[i-1][j];
			}
			
		}
	}
	public void pushTotal(int x, int x2,int i,int j,String[][] total, String[][] arr,String type){
		if(type.equals("WEIGHT")){
			int a=checkWeightValue(arr[x][j]);
			int b=checkWeightValue(arr[x2][j]);
			int c = a+b;
			if(c!=0){
				arr[i][j] = c+"";
			}
			else{
				arr[i][j] = "-";
			}
		}
		else if(type.equals("DENSITY")){
			double a=checkDensityValue(arr[x][j]);
			double b=checkDensityValue(arr[x2][j]);
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
			if(i==x){
				int a=checkWeightValue(arr[i][j]);
				int b=checkWeightValue(total[i][j]);
				int c = a+b;
				total[i][j] += c+""; 
			}
			else{
				int a=checkWeightValue(arr[i][j]);
				int b=checkWeightValue(total[i-1][j]);
				int c = a+b;
				total[i][j] += c+""; 
			}
		}
		else if(type.equals("DENSITY")){
			if(i==x){
				double a=checkDensityValue(arr[i][j]);
				double b=checkDensityValue(total[i][j]);
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
			else{
				double a=checkDensityValue(arr[i][j]);
				double b=checkDensityValue(total[i-1][j]);
				double c = a+b;
				total[i][j] += c+""; 
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		ArrayList<GblBeans> list = new ArrayList<>();
		if(e.getSource() == searchBtn){
			getResult();
		}//if
		else if(e.getSource()==printBtn){
			PrintSolution ps = new PrintSolution();
			ps.print(this);
		}
	}//method
	
	
	public void setTableColumnSize(JTable table,int colNum, int size){
		int prefer = table.getColumnModel().getColumn(colNum).getPreferredWidth();
		table.getColumnModel().getColumn(colNum).setPreferredWidth(prefer+size);
	}
}
