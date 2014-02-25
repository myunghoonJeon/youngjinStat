import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SizeSequence;


public class InventoryInputPopup extends JFrame implements ActionListener{
	/********************************************/
	String areaList[] = {"YS","OS","PYT","TDC","UJB","TG","BS","KS"};
	String fromUnit="";
	/********************************************/
	JButton purchaseBtn = new JButton("Purchase");
		JComboBox<String[]> purchaseAreaCombo;
		JTextField purchaseScacTextfield = new JTextField(15);
		JTextField purchaseUnitCostTextfield = new JTextField(15);
		JTextField purchaseUnitsTextfield = new JTextField(15);
		JTextField purchasePriseTextfield = new JTextField(15);
		JTextField purchaseDateTextfield = new JTextField(15);
		JLabel purchaseLabel = new JLabel("        [ PURCHASE - INPUT ]       ");
		JLabel purchaseDateLabel = new JLabel("PURCHASE DATE(YYMMDD");
		JLabel purchaseUnitCostLabel = new JLabel("PURCHASE UNIT COST");
		JLabel purchaseUnitsLabel = new JLabel("PURCHASE UNITS");
		JLabel purchasePriseLabel = new JLabel("PURCHASE PRISE");
		JButton purchaseInputBtn = new JButton("INPUT");
	/********************************************/
	JButton suppliedBtn = new JButton("Supplied");
		JTextField suppliedScacTextfield = new JTextField(15);
		JTextField suppliedUnitsTextfield = new JTextField(15);
		JTextField suppliedAreaTextfield = new JTextField(15);
		JTextField suppliedPriseTextfield = new JTextField(15);
		JTextField suppliedDateTextfield = new JTextField(15);
		JLabel suppliedLabel = new JLabel("[ SUPPLIED - INPUT ]");
		JLabel suppliedDateLabel = new JLabel("SUPPLIED DATE");
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
	}
	
	public InventoryInputPopup(String unit) {
		super("");
		super.setVisible(true);
		super.setResizable(false);
		super.setSize(300,200);
		fromUnit = unit;
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
		super.setSize(400,300);
		validate();
		p.removeAll();
		p.setLayout(new GridLayout(6,0));
		JPanel[] pl = new JPanel[6];
		getListPanel(pl, 6, 300,50);
		purchaseAreaCombo = new JComboBox(areaList);
		purchaseAreaCombo.setPreferredSize(new Dimension(80,20));
		pl[0].add(purchaseLabel);
		p.add(pl[0]);
		for(int i=1;i<6;i++){
			p.add(pl[i]);
			pl[i].setLayout(new GridLayout(1,2,20,20));
			JPanel jp1 = new JPanel();
			JPanel jp2 = new JPanel();
			if(i==1){
				jp1.add(purchaseDateLabel);
				jp2.add(purchaseDateTextfield);
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
		
	}
	
	public void insert(){
		CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
//		JTextField purchaseScacTextfield = new JTextField(15);
//		JTextField purchaseUnitCostTextfield = new JTextField(15);
//		JTextField purchaseUnitsTextfield = new JTextField(15);
//		JTextField purchasePriseTextfield = new JTextField(15);
//		JTextField purchaseDateTextfield = new JTextField(15);
		String scac = purchaseScacTextfield.getText();
		String units = purchaseUnitsTextfield.getText();
		String unitCost = purchaseUnitCostTextfield.getText();
		String prise = purchasePriseTextfield.getText();
		String date = purchaseDateTextfield.getText();
		String area = purchaseAreaCombo.getSelectedItem().toString();
		if(units.equals("") || unitCost.equals("") || prise.equals("") || date.equals("") || area.equals("")){
			System.out.println("????? shor");
		}
		else{
			System.out.println("FROM : "+fromUnit+" SCAC : "+scac +" "+units+" "+unitCost+" "+prise+" "+date+" "+area);
		}
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
			insert();
		}
	}
	
}
