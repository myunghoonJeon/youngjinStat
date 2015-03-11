package AllScacTotalInvoiceCollectionStatusGBL;
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
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import mainpages.CL_DAO_DB_Mysql;
import mainpages.GblBeans;
import mainpages.PrintSolution;


public class TotalInvoiceCollectionStatusForAllScacByEachScac extends JFrame implements ActionListener{
////////////////////////////////////////////////////////////////
	HashMap<String, String> inboundMap = new HashMap<>();
	HashMap<String, String> outboundMap = new HashMap<>();
////////////////////////////////////////////////////////////////
	int superWide = 1200;
	int superHeight = 650;
	
////////////////////////////////////////////////////////////////
	CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
//	JComboBox areaCombo = new JComboBox(dao.getAreaList2().toArray());
	JComboBox scacCombo = new JComboBox(dao.getScacListWork().toArray());
	JComboBox inoutCombo = new JComboBox(dao.getAllInOutList().toArray());
	JComboBox codeCombo = new JComboBox(dao.getCodeList().toArray());
//	JComboBox hhgUbCombo = new JComboBox(dao.getHhgUbList().toArray());
//	JComboBox typeCombo = new JComboBox(dao.getWorkStat1TypeList().toArray());
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
	JLabel informationLabel = new JLabel("TOTAL (IN/OUT) INVOICE & COLLECTION STATUS FOR ALL SCAC & BY EACH SCAC (GBL BASE) cut off date : ");
	JLabel cutoffDate = new JLabel("");
	////////////////////////////////////////////////////////////////
	public TotalInvoiceCollectionStatusForAllScacByEachScac(){
		super("total (in/out) invoice & collection status for all scac & by each scac (GBL base)");
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
		initHash();
		addActionListner();
	}
	
	public void initHash(){
		/*inboundMap.put("3", "0");
		inboundMap.put("4", "0");
		inboundMap.put("5", "1");
		inboundMap.put("T", "2");
		inboundMap.put("otherHhg", "3");
		inboundMap.put("total", "4");
		inboundMap.put("7", "5");
		inboundMap.put("8", "6");
		inboundMap.put("J", "7");
		inboundMap.put("otherUb", "8");
		inboundMap.put("totalUb", "9");
		inboundMap.put("totalIn", "10");
		////////////////////////////////
		outboundMap.put("3", "11");
		outboundMap.put("4", "11");
		outboundMap.put("5", "12");
		outboundMap.put("T", "13");
		outboundMap.put("otherHhg", "14");
		outboundMap.put("totalHhg", "15");
		outboundMap.put("7", "16");
		outboundMap.put("8", "17");
		outboundMap.put("J", "18");
		outboundMap.put("otherUb", "19");
		outboundMap.put("totalUb", "20");
		outboundMap.put("totalOut", "21");
		outboundMap.put("total", "22");
		*/
		inboundMap.put("3", "0");
		inboundMap.put("4", "0");
		inboundMap.put("5", "1");
		inboundMap.put("6", "2");
		inboundMap.put("T", "3");
		inboundMap.put("otherHhg", "4");
		inboundMap.put("total", "5");
		inboundMap.put("7", "6");
		inboundMap.put("8", "7");
		inboundMap.put("J", "8");
		inboundMap.put("otherUb", "9");
		inboundMap.put("totalUb", "10");
		inboundMap.put("totalIn", "11");
		////////////////////////////////
		outboundMap.put("3", "12");
		outboundMap.put("4", "12");
		outboundMap.put("5", "13");
		outboundMap.put("6", "14");
		outboundMap.put("T", "15");
		outboundMap.put("otherHhg", "16");
		outboundMap.put("totalHhg", "17");
		outboundMap.put("7", "18");
		outboundMap.put("8", "19");
		outboundMap.put("J", "20");
		outboundMap.put("otherUb", "21");
		outboundMap.put("totalUb", "22");
		outboundMap.put("totalOut", "23");
		outboundMap.put("total", "24");
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
			northUp.add(new JLabel("SCAC "));
			northUp.add(scacCombo);
			northUp.add(new JLabel("IN/OUT "));
			northUp.add(inoutCombo);
			northUp.add(new JLabel("CODE "));
			northUp.add(codeCombo);
			northUp.add(new JLabel("Period:"));
			northUp.add(startPeriod);
			northUp.add(new JLabel("~"));
			northUp.add(endPeriod);
			northUp.add(searchBtn);
			searchBtn.setPreferredSize(new Dimension(90,30));
			northUp.add(printBtn);
			printBtn.setPreferredSize(new Dimension(90,30));
	
	
		mainCenter.add("Center",bigCenter);
			bigCenter.setLayout(new BorderLayout());
			bigCenter.add("North",bcn);
				bcn.setLayout(new FlowLayout(FlowLayout.LEFT));
				bcn.setPreferredSize(new Dimension(0,25));
				bcn.add(informationLabel);
				bcn.add(cutoffDate);
			bigCenter.add("Center",center);
			String[][] initStr = new String[23][7];
			for(int i=0;i<23;i++){
				for(int k=0;k<7;k++){
					initStr[i][k] = new String();
					initStr[i][k] = "";
				}
			}
			AllScacTotalInvoiceMultipleRowHeader frame = new AllScacTotalInvoiceMultipleRowHeader(initStr);
			JScrollPane js = frame.getWorkVolumeStat1Table();
			js.setPreferredSize(new Dimension(1100,530));
			center.add(js);
		super.add(jp);
	}
	public void getResult(){
		initHash();
		ArrayList<AllScacTotalInvoiceCollectionStatusGblBeans> inboundList;
		ArrayList<AllScacTotalInvoiceCollectionStatusGblBeans> outboundList;
		ArrayList<AllScacTotalInvoiceCollectionStatusGblBeans> combineList;
		center.removeAll();
//		String[][] inboundStr = new String[23][20];
//		String[][] outboundStr = new String[23][20];
		String[][] finalStr = new String[25][8];
		String scac = scacCombo.getSelectedItem().toString();
//		String area = areaCombo.getSelectedItem().toString();
//		String hhgUb = hhgUbCombo.getSelectedItem().toString();
		String code = codeCombo.getSelectedItem().toString();
		String begin = startPeriod.getText();
		String end = endPeriod.getText();
//		String type = typeCombo.getSelectedItem().toString();
		if(inoutCombo.getSelectedItem().toString().equals("ALL")){
//			inboundStr = dao.getInboundWorkVolumeStat1(scac, area, hhgUb, code, begin, end,type);
//			outboundStr = dao.getOutboundWorkVolumeStat1(scac, area, hhgUb, code, begin, end,type);
//			finalStr = combine(inboundStr,outboundStr);
//			System.out.println("[[[ ALL CASE scac : "+scac+" code : "+code+" begin : "+begin+" end : "+end+" ]]]]");
			inboundList = dao.getInboundAllScacTotalInvoiceCollcetionStatusGbl(scac, code, begin, end);
//			System.out.println("[[[ COMPLETE GET INBOUND DATA ]]]");
			String[][] inboundStr = getStringInput(inboundList, "inbound");
//			System.out.println("[[[ COMPLETE GET INBOUND STRING ARRAY ]]]");
			outboundList = dao.getOutboundAllScacTotalInvoiceCollcetionStatusGbl(scac, code, begin, end);
//			System.out.println("[[[ COMPLETE GET OUTBOUND DATA ]]]");
			String[][] outboundStr = getStringInput(outboundList, "outbound");
//			System.out.println("[[[ COMPLETE GET OUTBOUND STRING ARRAY ]]]");
			finalStr = combineStrList(inboundStr, outboundStr);
		}
		else if(inoutCombo.getSelectedItem().toString().equals("IN")){
//			System.out.println("[[[ INBOUND CASE scac : "+scac+" code : "+code+" begin : "+begin+" end : "+end+" ]]]");
			inboundList = dao.getInboundAllScacTotalInvoiceCollcetionStatusGbl(scac, code, begin, end);
//			System.out.println("[[[ COMPLETE GET DATA ]]]");
			String[][] inboundStr = getStringInput(inboundList, "inbound");
			String[][] outboundStr = getStringArr(25, 8);
			finalStr = combineStrList(inboundStr,outboundStr);
//			inboundStr = dao.getInboundWorkVolumeStat1(scac, area, hhgUb, code, begin, end,type);
//			finalStr = inboundStr;
		}
		else if(inoutCombo.getSelectedItem().toString().equals("OUT")){
//			System.out.println("[[[ OUTBOUND CASE scac : "+scac+" code : "+code+" begin : "+begin+" end : "+end+" ]]]");
			outboundList = dao.getOutboundAllScacTotalInvoiceCollcetionStatusGbl(scac, code, begin, end);
//			System.out.println("[[[ COMPLETE GET DATA ]]]");
			String[][] outboundStr = getStringInput(outboundList, "outbound");
			String[][] inboundStr = getStringArr(23, 8);
			finalStr = combineStrList(inboundStr,outboundStr);
//			outboundStr = dao.getOutboundWorkVolumeStat1(scac, area, hhgUb, code, begin, end,type);
//			finalStr = outboundStr;
		}
		finalStr = arrangementStrList(finalStr);
		AllScacTotalInvoiceMultipleRowHeader frame = new AllScacTotalInvoiceMultipleRowHeader(finalStr);
		JScrollPane js = frame.getWorkVolumeStat1Table();
		js.setPreferredSize(new Dimension(1100,530));
		center.add(js);
		validate();
	}
	public String[][] getStringArr(int x, int y){
		String[][] arr = new String[x][y];
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++){
				arr[i][j] = "0";
			}
		}
		return arr;
	}
	
	public String[][] combineStrList(String[][] a,String[][] b){
		String[][] result= new String[25][8];
		for(int i=0;i<25;i++){
			for(int k=0;k<8;k++){
				if(i>=0 && i<=10){
					result[i][k] = a[i][k];
				}
				else{
					result[i][k] = b[i][k];
				}
			}
		}
		String[] total = {"0","0","0","0","0","0","0","0"};
		for(int i=0;i<5;i++){
			for(int k=0;k<8;k++){
				total[k] = getDoubleValue(a[i][k])+getDoubleValue(total[k])+"";
			}
		}
		result[5] = copyArray(total);
		initTotal(total);
		for(int i=6;i<10;i++){
			for(int k=0;k<8;k++){
				total[k] = getDoubleValue(a[i][k])+getDoubleValue(total[k])+"";
			}
		}
		result[10] = copyArray(total);
		initTotal(total);
		
		for(int i=12;i<17;i++){
			for(int k=0;k<8;k++){
				total[k] = getDoubleValue(b[i][k])+getDoubleValue(total[k])+"";
			}
		}
		result[17]=copyArray(total);
		initTotal(total);
		
		for(int i=18;i<21;i++){
			for(int k=0;k<8;k++){
				total[k] = getDoubleValue(b[i][k])+getDoubleValue(total[k])+"";
			}
		}
		result[22]=copyArray(total);
		initTotal(total);
		for(int k=0;k<8;k++){
			result[11][k] = getDoubleValue(result[5][k])+getDoubleValue(result[10][k])+"";
			result[23][k] = getDoubleValue(result[17][k])+getDoubleValue(result[22][k])+"";
			result[24][k] = getDoubleValue(result[11][k])+getDoubleValue(result[23][k])+"";
		}
		
		return result;
	}
	public void initTotal(String[] a){
		for(int i=0;i<8;i++){
			a[i]="0";
		}
	}
	public String[] copyArray(String[] b){
		String[] k= {"0","0","0","0","0","0","0","0"};
		for(int i=0;i<8;i++){
			k[i] = b[i];
		}
		return k;
	}
	public String[][] arrangementStrList(String[][] strList){
//		System.out.println(strList.length);
		for(int i=0;i<25;i++){
			for(int k=0;k<8;k++){
//				strList[i][k].equals("0")||strList[i][k].equals(".00")||strList[i][k].equals("0.0")
//				||strList[i][k].equals(".0")||strList[i][k].equals(".000")
				try{
						if(Double.parseDouble(strList[i][k]+"")==0){
							strList[i][k] = "0";
						}
						else{
							if(k==0){
								strList[i][k] = (int)Double.parseDouble(strList[i][k])+"";
							}
							else{
								strList[i][k] = new DecimalFormat("###,##0.00").format(Double.parseDouble(strList[i][k]+""));
							}
						}
				}catch(NumberFormatException e){
					System.out.println("i : "+i+" - k : "+k+" - value : "+strList[i][k]);
				}
			}
		}
		return strList;
	}
	public ArrayList<AllScacTotalInvoiceCollectionStatusGblBeans> combineInput(ArrayList<AllScacTotalInvoiceCollectionStatusGblBeans> list){
		ArrayList<AllScacTotalInvoiceCollectionStatusGblBeans> result = new ArrayList<>();
		ArrayList<String> idList = new ArrayList<>();
		for(int i=0;i<list.size();i++){
			if(idList.contains(list.get(i).getGblNo())){
				int index = idList.indexOf(list.get(i).getGblNo());
				
			}
			else{
				idList.add(list.get(i).getGblNo());
				result.add(list.get(i));
			}
		}
		return result;
	}
	
	public String[][] getStringInput(ArrayList<AllScacTotalInvoiceCollectionStatusGblBeans> list,String process){
		ArrayList<String> idList = new ArrayList<>();
		String[][] strList = new String[25][8];
		for(int i=0;i<25;i++){
			for(int k=0;k<8;k++){
				strList[i][k] = "0";
			}
		}
		String tempCode="";
		int index=-1;
		HashMap<String, String> map = new HashMap<>();
		if(process.equals("inbound")){
			map = inboundMap;
		}
		else if(process.equals("outbound")){
			map = outboundMap;
		}
		AllScacTotalInvoiceCollectionStatusGblBeans tempBean = new AllScacTotalInvoiceCollectionStatusGblBeans();
		
		for(int i=0;i<list.size();i++){
			tempBean = list.get(i);
			tempCode = tempBean.getCode();
			int flag=0;
			if(idList.contains(tempBean.getGblNo())){
//				System.out.println("EXIST GBL NO : "+tempBean.getGblNo());
				flag=1;
			}
			else{
//				System.out.println("NOT EXIST GBL NO : "+tempBean.getGblNo());
				idList.add(tempBean.getGblNo());
				flag=0;
			}
//			try{
//				System.out.println("CODE : "+tempCode);
				if(map.get(tempCode)==null){
					if(process.equals("inbound")){
						index = 4;
					}
					else{
						index = 16;
					}
				}
				else{
					index = Integer.parseInt(map.get(tempCode));
				}
//			}
//			catch(Exception e){
//				System.out.println("ERROR : "+e.getStackTrace()+" , CODE : "+tempCode);
//				continue;
//			}
			//////////////////////////////////[0] quantity
			double origin = getDoubleValue(strList[index][0]);
			if(flag == 0){
//				System.out.println("[[ Quantity - index : "+index+" code : "+tempCode +" value : "+origin+" ]]");
				origin++;
//				System.out.println("[[ After count : "+origin+" ]]");
				int tempQuantity = (int)origin;
				strList[index][0] = tempQuantity+"";
			}
			//////////////////////////////////[1] invoiced Amount
			origin = getDoubleValue(strList[index][1]);
			if(flag==0){
//				System.out.println("[[ Invoiced Amount - index : "+index+" code : "+tempCode +" value : "+origin+" ]]");
				origin += getDoubleValue(tempBean.getAmount());
				strList[index][1] = new DecimalFormat("######.00").format(origin);
			}
			//////////////////////////////////[2] collected
			origin = getDoubleValue(strList[index][2]);
//			System.out.println("[[ Collected Amount - index : "+index+" code : "+tempCode +" value : "+origin+" ]]");
			origin += getDoubleValue(tempBean.getCollectionAmount());
			strList[index][2] = origin+"";
			//////////////////////////////////[3] Uncollected
			if(flag==0){
				origin = getDoubleValue(strList[index][3]);
//				System.out.println("[[ Uncollected Amount - index : "+index+" code : "+tempCode +" value : "+origin+" ]]");
				origin += getDoubleValue(tempBean.getUncollectedAmount());
				strList[index][3] = new DecimalFormat("######.00").format(origin);
			}
			//////////////////////////////////[4] ShortPaid
			origin = getDoubleValue(strList[index][4]);
			if(flag == 0){//포함되어있지 않으면 더해라
//				System.out.println("[[ ShortPaid Amount - index : "+index+" code : "+tempCode +" value : "+origin+" GBL NO : "+tempBean.getGblNo()+" ]]");
				origin += getDoubleValue(tempBean.getDifference());
//				System.out.println(strList[index][4]);
//				System.out.println("tempBean.difference() : "+tempBean.getDifference());
//				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ : "+origin);
				strList[index][4] = new DecimalFormat("######.00").format(origin);
			}
			//////////////////////////////////[5] Accepted
			origin = getDoubleValue(strList[index][5]);
//			System.out.println("[[ Accepted Amount - index : "+index+" code : "+tempCode +" value : "+origin+" ]]");
			origin += getDoubleValue(tempBean.getAcceptAmount());
			strList[index][5] = new DecimalFormat("######.00").format(origin);
			//////////////////////////////////[6] Claimed
			origin = getDoubleValue(strList[index][6]);
//			System.out.println("[[ Claimed Amount - index : "+index+" code : "+tempCode +" value : "+origin+" ]]");
			origin += getDoubleValue(tempBean.getClaimeAmount());
			strList[index][6] = new DecimalFormat("######.00").format(origin);
			//////////////////////////////////[7] Net
			origin = getDoubleValue(strList[index][7]);
//			System.out.println("[[ Net Amount - index : "+index+" code : "+tempCode +" value : "+origin+" ]]");
			double tempAmount = getDoubleValue(strList[index][1]);
			double tempUncollected = getDoubleValue(strList[index][3]);
			double tempShortPaid = getDoubleValue(strList[index][4]);
			double tempAccepted = getDoubleValue(strList[index][5]);
			double tempClaimed = getDoubleValue(strList[index][6]);
			double tempNet = tempAmount - tempUncollected - tempShortPaid - tempAccepted - tempClaimed;
//			System.out.println("[[ Change NET : "+tempNet+"]]");
			strList[index][7] =  new DecimalFormat("######.00").format(getDoubleValue(tempNet+""));
//			double
		}
		return strList;
	}
	
	public double getDoubleValue(String a){
		if( a == null || a.equals("")){
			a = "0.0";
		}
		return Double.parseDouble(a);
	}

	public void actionPerformed(ActionEvent e) {
		ArrayList<GblBeans> list = new ArrayList<>();
		if(e.getSource() == searchBtn){
			cutoffDate.setText(endPeriod.getText());
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
