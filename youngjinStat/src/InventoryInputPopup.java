import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class InventoryInputPopup extends JFrame implements ActionListener{
	/********************************************/
	CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
//	String areaList[] = {"YS","OS","PYT","TDC","UJB","TG","BS","KS"};
//	String scacList[] = {"CRWV","FOFD","AALF","ABBV","PEFG","ISMQ","TCII","PPKC","SIFN","AERM","BOIF","APOF","DRBM","DYRI"};
	String item="";
	JComboBox areaCombo = new JComboBox(dao.getAreaList().toArray());
	JComboBox scacCombo = new JComboBox(dao.getScacList().toArray());
	JTextField yearTextfiled = new JTextField(4);
	JTextField monthTextfiled = new JTextField(2);
	JTextField dayTextfiled = new JTextField(2);
	InventoryInputAram ia;
	/********************************************/
	JButton purchaseBtn = new JButton("Purchase");
		
		JTextField purchaseScacTextfield = new JTextField(15);
		JTextField purchaseUnitCostTextfield = new JTextField(15);
		JTextField purchaseUnitsTextfield = new JTextField(15);
		JTextField purchasePriseTextfield = new JTextField(15);
		JTextField purchaseDateTextfield = new JTextField(15);
		JLabel purchaseLabel = new JLabel("        [ PURCHASE - INPUT ]       ");
		JLabel purchaseDateLabel = new JLabel("PURCHASE DATE(YYYYMMDD)");
		JLabel purchaseUnitCostLabel = new JLabel("PURCHASE UNIT COST");
		JLabel purchaseUnitsLabel = new JLabel("PURCHASE UNITS");
		JLabel purchasePriseLabel = new JLabel("PURCHASE PRISE");
		JLabel purchaseScacLabel = new JLabel("PURCHASE SCAC");
		JButton purchaseInputBtn = new JButton("INPUT");
	/********************************************/
	JButton suppliedBtn = new JButton("Supplied");
		JTextField suppliedScacTextfield = new JTextField(15);
		JTextField suppliedUnitsTextfield = new JTextField(15);
		JTextField suppliedAreaTextfield = new JTextField(15);
		JTextField suppliedPriseTextfield = new JTextField(15);
		
		JLabel suppliedLabel = new JLabel("[ SUPPLIED - INPUT ]");
		JLabel suppliedDateLabel = new JLabel("SUPPLIED DATE");
		JLabel suppliedAreaLabel = new JLabel("SUPPLIED AREA");
		JLabel suppliedScacLabel = new JLabel("SUPPLIED SCAC");
		JLabel suppliedUnitsLabel = new JLabel("SUPPLIED UNITS");
		JLabel suppliedPriseLabel = new JLabel("SUPPLIED PRISE");
		JButton suppliedInputBtn = new JButton("SUPPLIED INPUT");
	/********************************************/
	JPanel p;
	public void addactionListner(){
		purchaseBtn.addActionListener(this);
		suppliedBtn.addActionListener(this);
		purchaseInputBtn.addActionListener(this);
		suppliedInputBtn.addActionListener(this);
	}
	
	public InventoryInputPopup(String unit,String type) {
		super("");
		super.setVisible(true);
		super.setResizable(false);
		super.setSize(300,200);
		item = unit;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = super.getSize();
		int y = (int)(screen.height/2 - frm.height/2);
		int x = (int)(screen.width/2 - frm.width/2);
		super.setLocation(x,y);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addactionListner();
		setLayout();
	}
	
	public void setLayout(){
		p = new JPanel();
		JPanel center = new JPanel();
		autoCreateBorderLayout(p, 10, 10, 10, 30);
		super.add(p);
		centerLayout(center);
		p.add("Center",center);		
	}
	
	public void centerLayout(JPanel jp){
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		jp.setLayout(new GridLayout(2,0));
			jp.add(p1);
				p1.add(new JLabel("[ SELECT ]"));
			jp.add(p2);
				p2.setLayout(new GridLayout(0,2,10,10));
				p2.add(purchaseBtn);
				p2.add(suppliedBtn);
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
	
	public void purchase(){
		super.setSize(500,300);
		validate();
		p.removeAll();
		p.setLayout(new GridLayout(7,0));
		JPanel[] pl=new JPanel[7];
		getListPanel(pl, 7, 340,50);
		scacCombo.setPreferredSize(new Dimension(80,20));
		purchaseLabel.setText(item+" "+purchaseLabel.getText());
		pl[0].add(purchaseLabel);
		p.add(pl[0]);
		for(int i=1;i<7;i++){
			p.add(pl[i]);
			pl[i].setLayout(new GridLayout(1,2,20,20));
			JPanel jp1 = new JPanel();
			JPanel jp2 = new JPanel();
			if(i==1){
				jp1.add(purchaseDateLabel);
				jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
				jp2.add(yearTextfiled);
				jp2.add(new JLabel("year"));
				jp2.add(monthTextfiled);				
				jp2.add(new JLabel("month"));
				jp2.add(dayTextfiled);
				jp2.add(new JLabel("day"));
				pl[i].add(jp1);
				pl[i].add(jp2);
			}
			else if(i==2){
				jp1.add(purchaseUnitsLabel);
				jp2.add(purchaseUnitsTextfield);
				pl[i].add(jp1);
				pl[i].add(jp2);
			}
			else if(i==3){
				jp1.add(purchaseUnitCostLabel);
				jp2.add(purchaseUnitCostTextfield);
				pl[i].add(jp1);
				pl[i].add(jp2);
			}
			else if(i==4){
				jp1.add(purchasePriseLabel);
				jp2.add(purchasePriseTextfield);
				pl[i].add(jp1);
				pl[i].add(jp2);
			}
			else if(i==5){
				jp1.add(purchaseScacLabel);
				jp2.add(scacCombo);
				pl[i].add(jp1);
				pl[i].add(jp2);
			}
			else if(i==6){
				jp2.add(purchaseInputBtn);
				pl[i].add(jp1);
				pl[i].add(jp2);
			}
		}
		super.add(p);
		validate();
	}
	
	public void getListPanel(JPanel[] p,int count,int x, int y){
		for(int i=0;i<count;i++){
			p[i] = new JPanel();
		}
	}
	
	public void supplied(){
		super.setSize(500,300);
		validate();
		p.removeAll();
		p.setLayout(new GridLayout(7,0));
		JPanel[] pl=new JPanel[7];
		getListPanel(pl, 7, 340,50);
		areaCombo.setPreferredSize(new Dimension(80,20));
		scacCombo.setPreferredSize(new Dimension(80,20));
		suppliedLabel.setText(item+" "+suppliedLabel.getText());
		pl[0].add(suppliedLabel);
		p.add(pl[0]);
		for(int i=1;i<7;i++){
			p.add(pl[i]);
			pl[i].setLayout(new GridLayout(1,2,20,20));
			JPanel jp1 = new JPanel();
			JPanel jp2 = new JPanel();
			if(i==1){
				jp1.add(suppliedDateLabel);
				jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
				jp2.add(yearTextfiled);
				jp2.add(new JLabel("year"));
				jp2.add(monthTextfiled);				
				jp2.add(new JLabel("month"));
				jp2.add(dayTextfiled);
				jp2.add(new JLabel("day"));
				pl[i].add(jp1);
				pl[i].add(jp2);
			}
			else if(i==2){
				jp1.add(suppliedUnitsLabel);
				jp2.add(suppliedUnitsTextfield);
				pl[i].add(jp1);
				pl[i].add(jp2);
			}
			else if(i==3){
				jp1.add(suppliedPriseLabel);
				jp2.add(suppliedPriseTextfield);
				pl[i].add(jp1);
				pl[i].add(jp2);
			}
			else if(i==4){
				jp1.add(suppliedAreaLabel);
				jp2.add(areaCombo);
				pl[i].add(jp1);
				pl[i].add(jp2);
			}
			else if(i==5){
				jp1.add(suppliedScacLabel);
				jp2.add(scacCombo);
				pl[i].add(jp1);
				pl[i].add(jp2);
			}
			else if(i==6){
				jp2.add(suppliedInputBtn);
				pl[i].add(jp1);
				pl[i].add(jp2);
			}
		}
		super.add(p);
		validate();
	
	}
	
	public void insert(String type,String date){
		CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
		if(type.equals("purchase")){
			String units = purchaseUnitsTextfield.getText();
			String unitcost = purchaseUnitCostTextfield.getText();
			String prise = purchasePriseTextfield.getText();
			String scac = scacCombo.getSelectedItem().toString().toUpperCase();
			boolean bool = dao.insertPurchaseInput(item, date, units, unitcost, prise, scac, type);
			System.out.println(bool);
		}
		else if(type.equals("supplied")){
			String units = suppliedUnitsTextfield.getText();
			String area = areaCombo.getSelectedItem().toString();
			String prise = suppliedPriseTextfield.getText();
			String scac = scacCombo.getSelectedItem().toString();
			boolean bool = dao.insertSuppliedInput(item, date, units, area, prise, scac, type);
		}
		
//		String area = purchaseAreaCombo.getSelectedItem().toString();
//		if(units.equals("") || unitCost.equals("") || prise.equals("") || date.equals("") || area.equals("")){
//			System.out.println("????? shor");
//		}
//		else{
//			System.out.println("FROM : "+fromUnit+" SCAC : "+scac +" "+units+" "+unitCost+" "+prise+" "+date+" "+area);
//		}
	}
	
	public String checkDate(){
		String result;
		String year = yearTextfiled.getText();
		String day = dayTextfiled.getText();
		String month = monthTextfiled.getText();
		if(year.length() == 0 || month.length()==0||day.length()==0){
			result = "error";
		}
		else{
			if(Integer.parseInt(year)<=0 || Integer.parseInt(month)<=0 || Integer.parseInt(month)>12 || Integer.parseInt(day)<=0||Integer.parseInt(day)>31){
				result = "error";
			}
			else{
				if(year.length() == 1){
					year = "0"+year;
				}
				if(month.length() == 1){
					month = "0"+month;
				}
				if(day.length() == 1){
					day = "0"+day;
				}
				result = year+month+day;
			}//else
		}//else
		return result;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == purchaseBtn){
			System.out.println("purchase click");
			purchase();
		}
		
		else if(e.getSource() == suppliedBtn){
			System.out.println("supplied click");
			supplied();
		}
		
		else if(e.getSource() == purchaseInputBtn){
			System.out.println("purchaseInput");
			String result = checkDate();
			if(!result.equals("error")){
				insert("purchase",result);
				ia = new InventoryInputAram("INPUT SUCCESS");
				this.dispose();
			}
			else{
				ia = new InventoryInputAram("Input ERROR [DATE]");
			}
		}
		
		else if(e.getSource() == suppliedInputBtn){
			String result = checkDate();
			if(!result.equals("error")){//no error case
				insert("supplied",result);
				ia = new InventoryInputAram("INPUT SUCCESS");
				this.dispose();
			}
			else{
				ia = new InventoryInputAram("Input ERROR [DATE] ");
			}
		}
	}
	
}
