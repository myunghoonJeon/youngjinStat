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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import EachScacInvoiceCollection.GroupableColumnEachscacInvoice;

public class AdminPage extends JFrame implements ActionListener{
	CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
	////////////////////////////////////////////////////////////////
	int superWide = 1000;
	int superHeight = 700;
	int ROW_LENGTH = dao.getScacList().size()+1;
	int COLUM_LENGTH = 6;
////////////////////////////////////////////////////////////////
	
	JButton addBtn = new JButton("[ ADD USER ]");
	
////////////////////////////////////////////////////////////////
	JPanel center;
	JPanel information = new JPanel();
		JLabel informationLabel = new JLabel("　　ALL SCAC TOTAL INVOICE & COLLECTION STATUS (invoice base) 　　　　cut off date : ");
		JLabel cutOffDate = new JLabel("");
	JPanel mainCenter = new JPanel();
	JPanel north = new JPanel();
	JPanel jp = new JPanel();
	JPanel jpCenter = new JPanel();
	JPanel bigCenter = new JPanel();
	JPanel bcn = new JPanel();
	ArrayList<EachScacInvoiceCollectionBeans> esic = new ArrayList<>();
////////////////////////////////////////////////////////////////
	JPanel[] innerPanel;
	JTextField[] idtf;
	JTextField[] pwtf;
	JButton[] modifyBtn;
	JButton[] deleteBtn;
	DefaultTableModel model;
	JScrollPane js= new JScrollPane();
//////////////////////////////////////////////////////////////	
	public AdminPage() {
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
	public ArrayList<User> getUserList(){
		System.out.println("GET UER LIST");
		ArrayList<User> userArr = new ArrayList<>();
		userArr = dao.getUserList();
		return userArr;
	}
	public void initLayout(){
		autoCreateBorderLayout(jp, 20, 20, 10, 10);
		jp.add("Center",mainCenter);
		autoCreateBorderLayout(mainCenter, 200, 200, 50, 50);
		mainCenter.add("Center",bigCenter);
			bigCenter.setBackground(Color.black);
			JScrollPane jsp = new JScrollPane();
			jsp = getUserListPanel();
			jsp.setPreferredSize(new Dimension(500,500));
			jsp.setBackground(Color.yellow);
			bigCenter.add(jsp);
			super.add(jp);
			validate();
/////////////////////////////////////////////////////////////////////////////			
	}
	public JScrollPane getUserListPanel(){
		JScrollPane jsp = new JScrollPane();
		JPanel jp = new JPanel();
		JPanel jpCenter = new JPanel();
		JPanel jpSouth = new JPanel();
		ArrayList<User> ua = new ArrayList<>();
		ua = getUserList();
		innerPanel = new JPanel[ua.size()];
		idtf = new JTextField[ua.size()];
		pwtf = new JTextField[ua.size()];
		modifyBtn = new JButton[ua.size()];
		deleteBtn = new JButton[ua.size()];
						
		jp.setLayout(new BorderLayout());
		jp.add("Center",jpCenter);
			jpCenter.setLayout(new GridLayout(ua.size(),6));
			jpCenter.setBackground(Color.red);
			System.out.println("UA SIZE : "+ua.size());
//			for(int i=0;i<ua.size();i++){
//				innerPanel[i] = new JPanel();
//				innerPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT));
//				idtf[i] = new JTextField(15);
//				pwtf[i] = new JTextField(15);
//				modifyBtn[i] = new JButton("MODIFY");
//				deleteBtn[i] = new JButton("DELETE");
//				innerPanel[i].add(new JLabel("ID"));
//				innerPanel[i].add(idtf[i]);
//				innerPanel[i].add(new JLabel("PASSWORD"));
//				innerPanel[i].add(pwtf[i]);
//				innerPanel[i].add(modifyBtn[i]);
//				innerPanel[i].add(deleteBtn[i]);
//				modifyBtn[i].addActionListener(this);
//				deleteBtn[i].addActionListener(this);
//				jpCenter.add(innerPanel[i]);
//			}
			jpCenter.add(new JLabel("aaa"));
		jp.add("South",jpSouth);
		jsp.add(jp);
		
		return jsp;
	}

	
	
	public void actionPerformed(ActionEvent e) {
			for(int i=0;i<innerPanel.length;i++){
				if(e.getSource() == modifyBtn[i]){
					System.out.println("modify : "+i);
				}
				if(e.getSource() == deleteBtn[i]){
					System.out.println("delte : "+i);
				}
			}
	}//method
	
	public void setTableColumnSize(JTable table,int colNum, int size){
		int prefer = table.getColumnModel().getColumn(colNum).getPreferredWidth();
		table.getColumnModel().getColumn(colNum).setPreferredWidth(prefer+size);
	}
}
