package mainpages;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MainViewer extends JFrame implements ActionListener{
	/*************************[ LOGIN LEVEL ]*****************************/
	private int statLevel = 0;
	private JTextField idTextField;
	private JPasswordField passwordField;
	private JLabel loginInformation;
	
	private JButton loginBtn = new JButton("LOGIN");
	private JButton logoutBtn = new JButton("LOGOUT");
		
	private CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql(); 
	private userBean ub;
	
	private JPanel loginP;
	private JPanel jNorth = new JPanel();
	private JPanel jCenter = new JPanel();
	
	private JPanel levelCenterPanel;
	
	private String idTemp ="";
	private String pwTemp = "";
	
	/*************************[ LEVEL 1 ]*****************************/
	private JButton inventoryInputBtn = new JButton("[INVENTORY INPUT]");
	private JButton inventoryFilteringBtn = new JButton("[INVENTORY FILTERING]");
	private JButton inventoryStatBtn = new JButton("[INVENTORY STAT]");
	private JPanel level1P;
	
	/*************************[ LEVEL 2 ]*****************************/
	private JButton workVolumeFilteringBtn = new JButton("[WORK VOLUME FILTERING]");
	private JButton workVolumestat1Btn = new JButton("[WORK VOLUME STAT1]");
	private JButton workvolumeStat2Btn = new JButton("[WORK VOLUME STAT2]");
	private JPanel level2P;
	
	/*************************[ LEVEL 3 ]*****************************/
	private JPanel level3P;
	private JButton invoiceCollectionFilteringBtn = new JButton("[INVOICE & COLLECTION FILTERING]");
	private JButton invoiceCollectionStatBtn = new JButton("[INVOICE & COLLECTION STAT]");
	private JButton allScacTotalInvoiceCollectionStatusBtn = new JButton("[ALL SCAC TOTAL INVOICE & COLLECTIOIN STATUS]");
	private JButton eachScacInvoiceCollectionStatusBtn = new JButton("[EACH SCAC INVOICE & COLLECTION STATUS]");
//	private Jbutton 
	/*************************[ INVENTORY INPUT ]*****************************/
	private JPanel inventoryMainPanel;
	String add = "[ INPUT ]";
	/*********[ complimentary parameter ]***********/
	private JButton typeIIUsedBtn = new JButton("(used) Type II - A:191 cuft - "+add);
	private JButton typeIINewBtn = new JButton("(new) Type II - A:191 cuft - "+add);
	private JButton special120Btn = new JButton("Special - C:120 cuft - "+add);
	private JButton special90Btn = new JButton("Special - C:90 cuft - "+add);
	private JButton special55Btn = new JButton("Special - C:55 cuft - "+add);
	private JButton stansilBtn = new JButton("STANSIL - "+add);
	private JButton nailBtn  = new JButton("NAIL - "+add);
	private JButton cripBtn = new JButton("CRIP - "+add);
	private JButton compoundBtn = new JButton("COMPOUND - "+add);
	private JButton hbbox10Btn = new JButton("HB Box - 10.0 cuft -"+add);
	private JButton hbbox13Btn = new JButton("HB Box - 13.6 cuft -"+add);
	private JButton hbbox21Btn = new JButton("HB Box - 21.0 cuft -"+add);
	/*********[ compensation parameter ]***********/
	private JButton cottonbox2Btn = new JButton("Cotton Box - 2 cuft "+add);
	private JButton cottonbox4Btn = new JButton("Cotton Box - 4 cuft"+add);
	private JButton cottonbox6Btn = new JButton("Cotton Box - 6 cuft"+add);
	private JButton cardboardBtn = new JButton("Card board - "+add);
	private JButton paperBtn = new JButton("Paper(평면지) - "+add);
	private JButton tapeBtn = new JButton("Tape - "+add);
	private JButton insidepaperBtn = new JButton("Inside Paper(선화지) - "+add);
	private JButton aircapBtn = new JButton("Air Cap - "+add);
	private JButton wardrobeboxBtn = new JButton("Wardrobe Box - "+add);
	/*****************************************************************/

	MainViewer(){
		super("Youngjin Stat & Filtering");
		super.setVisible(true);
		super.setResizable(false);
		super.setSize(1000,700);
		super.setLayout(new BorderLayout());
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = super.getSize();
		int y = (int)(screen.height/2 - frm.height/2);
		int x = (int)(screen.width/2 - frm.width/2);
		super.setLocation(x,y);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		init();
		loginLayout();
		addActionListner();
	}
	
	public void invisibleButtonControl(int state){
		System.out.println(state);
		if(state == 1){
			inventoryFilteringBtn.setVisible(false);
			inventoryInputBtn.setVisible(false);
			inventoryStatBtn.setVisible(false);
		}
		else if(state == 2){
			
		}
		else if(state == 3){
			
		}
		else if(state == 4){
			
		}
		validate();
	}
	
	public void init(){
		loginBtn.setPreferredSize(new Dimension(70,20));
	}

	
	public void loginLayout(){
		validate();
		setStateLevel(0);
		jCenter.removeAll();
		jNorth.setVisible(true);
		jNorth.setLayout(new FlowLayout(FlowLayout.RIGHT));
		jNorth.add(logoutBtn);
		logoutBtn.setVisible(false);
		jNorth.setPreferredSize(new Dimension(0,40));
		jCenter.setVisible(true);
		super.add("North",jNorth);
		super.add("Center",jCenter);
			autoCreateBorderLayout(jCenter,330,330,150,300);
			loginArea(jCenter);
		validate();
	}
	
	public void loginArea(JPanel jp){
		idTextField = new JTextField(15);
		passwordField = new JPasswordField(15);
		loginInformation = new JLabel("");
		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new GridLayout(5,0));
		JPanel[] j =  new JPanel[5];
		for(int i=0;i<5;i++){
			j[i] = new JPanel();
			j[i].setLayout(new FlowLayout(FlowLayout.CENTER));
			loginPanel.add(j[i]);
		}
		JLabel id = new JLabel("ID");
		id.setPreferredSize(new Dimension(20,40));
//		JPanel pid = new JPanel();
//		pid.setPreferredSize(new Dimension(100,50));
		JLabel pw = new JLabel("PW");
		pw.setPreferredSize(new Dimension(20,40));
//		JPanel ppw = new JPanel();
//		ppw.setPreferredSize(new Dimension(100,50));
		JPanel t = new JPanel();
		t.setPreferredSize(new Dimension(120,40));
		j[0].add(loginInformation);
//		j[1].add(pid);
		j[1].add(id);
		j[1].add(idTextField);
//		j[2].add(ppw);
		j[2].add(pw);
		j[2].add(passwordField);
		j[3].add(t);
		j[3].add(loginBtn);
		j[4].add(new JPanel());
		jp.add("Center",loginPanel);
		loginPanel.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	public void setLoginText(String str){
		this.loginInformation.setText(str);
		validate();
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

	public void checkUser(){
		idTemp = idTextField.getText();
		pwTemp = passwordField.getText();
		int level;
		ub = dao.userAccessValidateCheck(idTemp, pwTemp);
		if(ub!=null){
			level = ub.getLevel();
			if(level == 1){
				level1Page();
			}
			else if(level == 2){
				level2Page();
			}
			else if(level == 3){
				level3Page();
			}
			else if(level == 4){
//				level4Page();
			}
		}
		else{
			loginInformation.setText("Wrong Input");
		}
	}
	
	public void logOut(){
//		invisibleButtonControl(getStateLevel());
		
		loginLayout();
		validate();
	}
	
	public void logoutBtnVisible(Boolean bool){
		logoutBtn.setVisible(bool);
	}
	
	public void level1PanelLayout(JPanel jp){
		levelCenterPanel = new JPanel();
		autoCreateBorderLayout(jp, 300, 300 , 100, 300);
		jp.add("Center",levelCenterPanel);
		levelCenterPanel.setLayout(new GridLayout(4,0,10,10));
		JLabel level1InformationLabel = new JLabel("[ SELECT MENU ]");
		levelCenterPanel.add(level1InformationLabel);
		inventoryInputBtn.setVisible(true);
		inventoryFilteringBtn.setVisible(true);
		inventoryStatBtn.setVisible(true);
		levelCenterPanel.add(inventoryInputBtn);
		levelCenterPanel.add(inventoryFilteringBtn);
		levelCenterPanel.add(inventoryStatBtn);
		validate();
	}
	public void level2PanelLayout(JPanel jp){
		levelCenterPanel = new JPanel();
		autoCreateBorderLayout(jp, 300, 300 , 100, 300);
		jp.add("Center",levelCenterPanel);
		levelCenterPanel.setLayout(new GridLayout(4,0,10,10));
		JLabel level1InformationLabel = new JLabel("[ SELECT MENU ]");
		levelCenterPanel.add(level1InformationLabel);
		levelCenterPanel.add(workVolumeFilteringBtn);
		levelCenterPanel.add(workVolumestat1Btn);
		levelCenterPanel.add(workvolumeStat2Btn);
		validate();
	}
	public void level3PanelLayout(JPanel jp){
		levelCenterPanel = new JPanel();
		autoCreateBorderLayout(jp, 300, 300 , 200, 200);
		jp.add("Center",levelCenterPanel);
		levelCenterPanel.setLayout(new GridLayout(6,0,10,10));
		JLabel level1InformationLabel = new JLabel("[ SELECT MENU ]");
		levelCenterPanel.add(level1InformationLabel);
		levelCenterPanel.add(invoiceCollectionFilteringBtn);
		levelCenterPanel.add(allScacTotalInvoiceCollectionStatusBtn);
		levelCenterPanel.add(eachScacInvoiceCollectionStatusBtn);
		validate();
	}
	public void setStateLevel(int i){
		this.statLevel = i;
	}
	public int getStateLevel(){
		return statLevel;
	}
	public void level1Page(){
		setStateLevel(1);
		level1P = new JPanel();
		level1PanelLayout(level1P);
		jCenter.removeAll();
		jCenter.add(level1P);
		logoutBtnVisible(true);
		validate();
	}
	
	public void level2Page(){
		setStateLevel(2);
		level2P = new JPanel();
		level2PanelLayout(level2P);
		jCenter.removeAll();
		jCenter.add(level2P);
		logoutBtnVisible(true);
		validate();
	}
	
	public void level3Page(){
		setStateLevel(3);
		level3P = new JPanel();
		level3PanelLayout(level3P);
		jCenter.removeAll();
		jCenter.add(level3P);
		logoutBtnVisible(true);
		validate();
	}
	
//	public void level4Page(){
//		setStateLevel(4);
//		level2P = new JPanel();
//		jCenter.removeAll();
//		jCenter.add(level2P);
//		logoutBtnVisible(true);
//		level2P.setBackground(Color.black);
//		validate();
//	}
	
	public void inventoryInputPage(){
		System.out.println("inventory Page");
		inventoryMainPanel = new JPanel();
		JPanel inventoryCenterPanel = new JPanel();
		autoCreateBorderLayout(inventoryMainPanel, 30, 30 , 0, 20);
		inventoryMainPanelLayout(inventoryCenterPanel);
		inventoryMainPanel.add("Center",inventoryCenterPanel);
		jCenter.removeAll();
		jCenter.add(inventoryMainPanel);
		logoutBtnVisible(true);
		validate();
	}
	public void inventoryMainPanelLayout(JPanel jp){
		jp.setLayout(new GridLayout(0,2));
		JPanel west = new JPanel();
		JPanel east = new JPanel();
		ivmpInnerPanel(west,13,1);
		ivmpInnerPanel(east,13,2);
		/****[grid top & bottom layout]***/
		jp.add(west);
		jp.add(east);
		
	}
	public void ivmpBtnSize(){
		typeIINewBtn.setPreferredSize(new Dimension(300,30));
		typeIIUsedBtn.setPreferredSize(new Dimension(300,30));
		special120Btn.setPreferredSize(new Dimension(300,30));
		special90Btn.setPreferredSize(new Dimension(300,30));
		special55Btn.setPreferredSize(new Dimension(300,30));
		stansilBtn.setPreferredSize(new Dimension(300,30));
		nailBtn.setPreferredSize(new Dimension(300,30));
		cripBtn.setPreferredSize(new Dimension(300,30));
		compoundBtn.setPreferredSize(new Dimension(300,30));
		hbbox10Btn.setPreferredSize(new Dimension(300,30));
		hbbox13Btn.setPreferredSize(new Dimension(300,30));
		hbbox21Btn.setPreferredSize(new Dimension(300,30));
		cottonbox2Btn.setPreferredSize(new Dimension(300,30));
		cottonbox4Btn.setPreferredSize(new Dimension(300,30));
		cottonbox6Btn.setPreferredSize(new Dimension(300,30));
		cardboardBtn.setPreferredSize(new Dimension(300,30));
		paperBtn.setPreferredSize(new Dimension(300,30));
		tapeBtn.setPreferredSize(new Dimension(300,30));
		insidepaperBtn.setPreferredSize(new Dimension(300,30));
		aircapBtn.setPreferredSize(new Dimension(300,30));
		wardrobeboxBtn.setPreferredSize(new Dimension(300,30));
	}
	public void ivmpInnerPanel(JPanel jp,int x,int flag){
		ivmpBtnSize();
		String title="";
		if(flag == 1){
			title = "==============[ COMPLIMENTARY ]==============";
		}
		else{
			title = "==============[ COMPENSATION ]==============";
		}
		jp.setLayout(new GridLayout(x,0,0,5));
		jp.setAlignmentX(CENTER_ALIGNMENT);
		JPanel []pl = new JPanel[x];
		JPanel []etc = new JPanel[30];
		for(int i=0;i<x;i++){
			pl[i] = new JPanel();
			jp.add(pl[i]);
		}
		for(int i=0;i<30;i++){
			etc[i] = new JPanel();
			setSize(etc[i], 100, 20);
			etc[i].setBackground(Color.orange);
		}
		
		pl[0].add(new JPanel().add(new JLabel(title)));
		if(flag == 1){
			pl[1].add(typeIINewBtn);
			pl[2].add(typeIIUsedBtn);
			pl[3].add(special120Btn);
			pl[4].add(special90Btn);
			pl[5].add(special55Btn);
			pl[6].add(stansilBtn);
			pl[7].add(nailBtn);
			pl[8].add(cripBtn);
			pl[9].add(compoundBtn);
			pl[10].add(hbbox10Btn);
			pl[11].add(hbbox13Btn);
			pl[12].add(hbbox21Btn);
		}
		else if(flag == 2){
			pl[1].add(cottonbox2Btn);
			pl[2].add(cottonbox4Btn);
			pl[3].add(cottonbox6Btn);
			pl[4].add(cardboardBtn);
			pl[5].add(paperBtn);
			pl[6].add(tapeBtn);
			pl[7].add(insidepaperBtn);
			pl[8].add(aircapBtn);
			pl[9].add(wardrobeboxBtn);
		}
//		pl[1].add(etc[count++]);	pl[1].add(etc[count++].add(new JLabel("CONTAINER"))); 
//		pl[2].add(etc[count++]);	pl[2].add(etc[count++].add(new JLabel("(NEW)  TYPE II")));	pl[2].add(etc[count++].add(new JLabel("A: 191 CUFT")));
//		pl[3].add(etc[count++]);	pl[3].add(etc[count++].add(new JLabel("(USED) TYPE II")));	pl[3].add(etc[count++].add(new JLabel("A: 191 CUFT")));
//		pl[4].add(etc[count++]);	pl[4].add(etc[count++].add(new JLabel("SPECIAL")));			pl[4].add(etc[count++].add(new JLabel("C: 120 CUFT")));
//		pl[5].add(etc[count++]);	pl[5].add(etc[count++]);									pl[5].add(etc[count++].add(new JLabel("D: 90 CUFT")));
//		pl[6].add(etc[count++]);	pl[6].add(etc[count++]);									pl[6].add(etc[count++].add(new JLabel("F: 55 CUFT")));
//		pl[7].add(etc[count++]);	pl[7].add(etc[count++].add(new JLabel("STANSIL")));			pl[7].add(etc[count++]);
//		pl[8].add(etc[count++]);	pl[8].add(etc[count++].add(new JLabel("NAIL")));			pl[8].add(etc[count++]);
//		pl[9].add(etc[count++]);	pl[9].add(etc[count++].add(new JLabel("CRIP")));			pl[9].add(etc[count++]);
//		pl[10].add(etc[count++]);	pl[10].add(etc[count++].add(new JLabel("COMPOUND")));		pl[10].add(etc[count++]);
	}
	
	public void setSize(JPanel p,int x, int y){
		p.setPreferredSize(new Dimension(x,y));
	}
	
	public void addActionListner(){
		loginBtn.addActionListener(this);
		logoutBtn.addActionListener(this);
		inventoryInputBtn.addActionListener(this);
		typeIINewBtn.addActionListener(this);
		typeIIUsedBtn.addActionListener(this);
		special120Btn.addActionListener(this);
		special55Btn.addActionListener(this);
		special90Btn.addActionListener(this);
		stansilBtn.addActionListener(this);
		nailBtn.addActionListener(this);
		cripBtn.addActionListener(this);
		compoundBtn.addActionListener(this);
		hbbox10Btn.addActionListener(this);
		hbbox13Btn.addActionListener(this);
		hbbox21Btn.addActionListener(this);
		cottonbox2Btn.addActionListener(this);
		inventoryFilteringBtn.addActionListener(this);
		inventoryStatBtn.addActionListener(this);
		workVolumeFilteringBtn.addActionListener(this);
		workvolumeStat2Btn.addActionListener(this);
		workVolumestat1Btn.addActionListener(this);
		invoiceCollectionFilteringBtn.addActionListener(this);
		invoiceCollectionStatBtn.addActionListener(this);
		eachScacInvoiceCollectionStatusBtn.addActionListener(this);
		allScacTotalInvoiceCollectionStatusBtn.addActionListener(this);
	}
	
	public void inputOpen(String item, String type){
		InventoryInputPopup ip = new InventoryInputPopup(item,type);
	}
	
	public void inventoryFilteringPage(){
		inventoryFilteringPage ivf = new inventoryFilteringPage();
	}
	
	public void inventoryStatPage(){ 
		InventoryStatPage is = new InventoryStatPage();
	}
	
	public void workVolumeFilteringPage(){
		WorkVolumeFilteringPage wvf = new WorkVolumeFilteringPage();
	}
	
	public void workVolumeStat1(){
		WorkVolumeStat1 ws1 = new WorkVolumeStat1();
	}
	public void workVolumeStat2(){
		WorkVolumeStat2 ws2 = new WorkVolumeStat2();
	}
	public void invoiceCollectionFiltering(){
		InvoiceCollectionFiltering icf = new InvoiceCollectionFiltering();
	}
	public void invoiceCollectionStat(){
		InvoiceCollectionStat ics = new InvoiceCollectionStat();
	}
	public void allScacTotalInvoiceCollection(){
		AllScacTotalInvoiceCollection atic = new AllScacTotalInvoiceCollection();
	}
	public void eachScacInvoiceCollection(){
		
	}
	public void actionPerformed(ActionEvent e) {
		/***********[loginPage Buttons]***********/
		if(e.getSource() == loginBtn){
			checkUser();
		}
		else if(e.getSource() == logoutBtn){
			logOut();
		}
		/***********[inventory INPUT Buttons]***********/
		else if(e.getSource() == inventoryInputBtn){
			inventoryInputPage();
		}
		else if(e.getSource() == typeIINewBtn){
			inputOpen("TYPE II (NEW)","complimentary");
		}
		else if(e.getSource() == typeIIUsedBtn){
			inputOpen("TYPE II (USED)","complimentary");
		}
		else if(e.getSource() == special120Btn){
			inputOpen("SPECIAL c: 120 CUFT","complimentary");
		}
		else if(e.getSource() == special55Btn){
			inputOpen("SEPCIAL f: 55 CUFT","complimentary");
		}
		else if(e.getSource() == special90Btn){
			inputOpen("SPECIAL d: 90 CUFT","complimentary");
		}
		else if(e.getSource() == nailBtn){
			inputOpen("NAIL","complimentary");
		}
		else if(e.getSource() == cripBtn){
			inputOpen("CRIP","complimentary");
		}
		else if(e.getSource() == compoundBtn){
			inputOpen("COMPOUND","complimentary");
		}
		else if(e.getSource() == hbbox10Btn){
			inputOpen("HB BOX 10.0 CUFT","complimentary");
		}
		else if(e.getSource() == hbbox13Btn){
			inputOpen("HB BOX 13.6 CUFT","complimentary");
		}
		else if(e.getSource() == hbbox21Btn){
			inputOpen("HB BOX 21.0 CUFT","complimentary");
		}
		else if(e.getSource() == cottonbox2Btn){
			inputOpen("COTTON 2 CUFT","compensation");
		}
		else if(e.getSource() == cottonbox4Btn){
			inputOpen("COTTON 4 CUFT","compensation");
		}
		else if(e.getSource() == cottonbox6Btn){
			inputOpen("COTTON 6 CUFT","compensation");
		}
		else if(e.getSource() == paperBtn){
			inputOpen("PAPER (평면지)","compensation");
		}
		else if(e.getSource() == tapeBtn){
			inputOpen("TAPE","compensation");
		}
		else if(e.getSource() == insidepaperBtn){
			inputOpen("INSIDE PAPER (선화지)","compensation");
		}
		else if(e.getSource() == aircapBtn){
			inputOpen("AIR CAP","compensation");
		}
		else if(e.getSource() == wardrobeboxBtn){
			inputOpen("WADROBE BOX","compensation");
		}
		/***********[inventory FILTERING , STAT Buttons]***********/
		else if(e.getSource() == inventoryFilteringBtn){
			inventoryFilteringPage();
		}
		else if(e.getSource() == inventoryStatBtn){
			inventoryStatPage();
		}
		/***********[work volume filtering Buttons]***********/
		else if(e.getSource() == workVolumeFilteringBtn){
			workVolumeFilteringPage();
		}
		else if(e.getSource() == workVolumestat1Btn){
			workVolumeStat1();
		}
		else if(e.getSource() == workvolumeStat2Btn){
			workVolumeStat2();
		}
		else if(e.getSource() == invoiceCollectionFilteringBtn){
			invoiceCollectionFiltering();
		}
		else if(e.getSource() == invoiceCollectionStatBtn){
			invoiceCollectionStat();
		}
		else if(e.getSource() == allScacTotalInvoiceCollectionStatusBtn){
			allScacTotalInvoiceCollection();
		}
		else if(e.getSource() == eachScacInvoiceCollectionStatusBtn){
			
		}
	}
	
}
