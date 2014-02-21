

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
	
	private JPanel level1CenterPanel;
	
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
	
	/*****************************************************************/

	MainViewer(){
		super("Youngjin Stat & Filtering");
		super.setVisible(true);
		super.setResizable(false);
		super.setSize(1000,800);
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
	public void addActionListner(){
		loginBtn.addActionListener(this);
		logoutBtn.addActionListener(this);
	}
	
	public void loginLayout(){
		validate();
		setStateLevel(0);
		jCenter.removeAll();
		jNorth.setVisible(true);
		jNorth.setLayout(new FlowLayout(FlowLayout.RIGHT));
		jNorth.add(logoutBtn);
		logoutBtn.setVisible(false);
		jNorth.setPreferredSize(new Dimension(0,100));
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
		id.setPreferredSize(new Dimension(20,50));
//		JPanel pid = new JPanel();
//		pid.setPreferredSize(new Dimension(100,50));
		JLabel pw = new JLabel("PW");
		pw.setPreferredSize(new Dimension(20,50));
//		JPanel ppw = new JPanel();
//		ppw.setPreferredSize(new Dimension(100,50));
		JPanel t = new JPanel();
		t.setPreferredSize(new Dimension(120,50));
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
		level1CenterPanel = new JPanel();
		autoCreateBorderLayout(jp, 300, 300 , 100, 300);
		jp.add("Center",level1CenterPanel);
		level1CenterPanel.setLayout(new GridLayout(4,0,10,10));
		JLabel level1InformationLabel = new JLabel("[ SELECT MENU ]");
		level1CenterPanel.add(level1InformationLabel);
		inventoryInputBtn.setVisible(true);
		inventoryFilteringBtn.setVisible(true);
		inventoryStatBtn.setVisible(true);
		level1CenterPanel.add(inventoryInputBtn);
		level1CenterPanel.add(inventoryFilteringBtn);
		level1CenterPanel.add(inventoryStatBtn);
		validate();
	}
	public void level2PanelLayout(JPanel jp){
		level1CenterPanel = new JPanel();
		autoCreateBorderLayout(jp, 300, 300 , 100, 300);
		jp.add("Center",level1CenterPanel);
		level1CenterPanel.setLayout(new GridLayout(4,0,10,10));
		JLabel level1InformationLabel = new JLabel("[ SELECT MENU ]");
		level1CenterPanel.add(level1InformationLabel);
		level1CenterPanel.add(workVolumeFilteringBtn);
		level1CenterPanel.add(workVolumestat1Btn);
		level1CenterPanel.add(workvolumeStat2Btn);
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
		jCenter.removeAll();
		jCenter.add(level2P);
		logoutBtnVisible(true);
		level3P.setBackground(Color.red);
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
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == loginBtn){
			checkUser();
		}
		else if(e.getSource() == logoutBtn){
			logOut();
		}
		else if(e.getSource() == inventoryFilteringBtn){
			
		}
		else if(e.getSource() == inventoryInputBtn){
			
		}
		else if(e.getSource() == inventoryStatBtn){
			
		}
		
	}
	
}
