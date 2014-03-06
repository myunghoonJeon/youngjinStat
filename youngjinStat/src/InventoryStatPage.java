import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

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

public class InventoryStatPage extends JFrame implements ActionListener{
	CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
	JComboBox itemsCombo = new JComboBox(dao.getInventoryStatItemList().toArray());
	JComboBox areaCombo = new JComboBox(dao.getInventoryStatArea().toArray());
	JComboBox scacCombo = new JComboBox(dao.getScacList().toArray());
	
	JButton searchBtn = new JButton("SEARCH");
	
	JLabel itemsLabel = new JLabel("Items :");
	JLabel areaLabel = new JLabel("　　AREA :");
	JLabel periodLabel = new JLabel("　　Period(YYYYMMDD) :");
	JLabel between = new JLabel("~");
	JLabel bigCenterItems = new JLabel("");
	JLabel bigCenterArea = new JLabel("");
	
	JTextField startPeriod =  new JTextField(8);
	JTextField endPeriod = new JTextField(8);
	
	JPanel center;
	
	int num = 25;
	int num2 = 5;
	JPanel[] jp = new JPanel[num];
	JPanel[][] jp2 = new JPanel[num][num2];
	JPanel tp[] = new JPanel[num];
	JPanel bq[] = new JPanel[num];
	JPanel ba[] = new JPanel[num];
	JPanel iq[] = new JPanel[num];
	JPanel ia[] = new JPanel[num];
	JPanel dq[] = new JPanel[num];
	JPanel da[] = new JPanel[num];
	JPanel eq[] = new JPanel[num];
	JPanel ea[] = new JPanel[num];
	JLabel tl[] = new JLabel[num];
	JLabel bql[] = new JLabel[num];
	JLabel bal[] = new JLabel[num];
	JLabel iql[] = new JLabel[num];
	JLabel ial[] = new JLabel[num];
	JLabel dql[] = new JLabel[num];
	JLabel dal[] = new JLabel[num];
	JLabel eql[] = new JLabel[num];
	JLabel eal[] = new JLabel[num];		

	JLabel items = new JLabel("ITEMS");
	JLabel beginning = new JLabel("BEGINNING");
	JLabel increase = new JLabel("INCREASE");
	JLabel decrease = new JLabel("DECREASE");
	JLabel ending = new JLabel("ENDING");
			
	
	public InventoryStatPage() {
		super("");
		super.setVisible(true);
		super.setResizable(false);
		super.setSize(900,700);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = super.getSize();
		int y = (int)(screen.height/2 - frm.height/2);
		int x = (int)(screen.width/2 - frm.width/2);
		super.setLocation(x,y);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initLayout();
		statPage();
		addActionListner();
	}
	
	public void addActionListner(){
		searchBtn.addActionListener(this);
	}
	
	public void initLayout(){
		JPanel jp = new JPanel();
		autoCreateBorderLayout(jp, 20, 20, 10, 10);
		JPanel mainCenter = new JPanel();
		jp.add("Center",mainCenter);
		mainCenter.setLayout(new BorderLayout());
		JPanel north = new JPanel();
		center = new JPanel();
		
		JPanel bigCenter = new JPanel();
		JPanel bcn = new JPanel();
		bcn.setPreferredSize(new Dimension(0,25));
		bcn.setBackground(Color.white);
		mainCenter.add("North",north);
			north.setPreferredSize(new Dimension(0,40));
			north.setLayout(new FlowLayout(FlowLayout.LEFT));
			north.add(itemsLabel);
			north.add(itemsCombo);
				itemsCombo.setMaximumRowCount(25);
			north.add(areaLabel);
			north.add(areaCombo);
				areaCombo.setMaximumRowCount(15);
			north.add(periodLabel);
			north.add(startPeriod);
			north.add(between);
			north.add(endPeriod);
			north.add(new JLabel("　　　　"));
			north.add(searchBtn);
		mainCenter.add("Center",bigCenter);
			bigCenter.setLayout(new BorderLayout());
			bigCenter.add("North",bcn);
			bigCenter.add("Center",center);
			bcn.setLayout(new FlowLayout(FlowLayout.LEFT));
				bcn.add(bigCenterItems);
				bcn.add(bigCenterArea);
			center.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
			super.add(jp);
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
	
	public void statPage(){
		center.removeAll();
		validate();
		items = new JLabel("ITEMS");
		items.setHorizontalAlignment(JLabel.CENTER);
		beginning = new JLabel("BEGINNING");
		beginning.setHorizontalAlignment(JLabel.CENTER);
		increase = new JLabel("INCREASE");
		increase.setHorizontalAlignment(JLabel.CENTER);
		decrease = new JLabel("DECREASE");
		decrease.setHorizontalAlignment(JLabel.CENTER);
		ending = new JLabel("ENDING");
		ending.setHorizontalAlignment(JLabel.CENTER);
		center.setLayout(new GridLayout(num,1,2,0));
		JPanel[] jp = new JPanel[num];
		JPanel[][] jp2 = new JPanel[num][num2];
		for(int i=0;i<num;i++){
			jp[i] = new JPanel();
			jp[i].setLayout(new GridLayout(1,5,5,2));
			tp[i] = new JPanel();
			tl[i] = new JLabel("");
				tp[i].add(tl[i]);
			bq[i] = new JPanel();
			ba[i] = new JPanel();
			iq[i] = new JPanel();
			ia[i] = new JPanel();
			dq[i] = new JPanel();
			da[i] = new JPanel();
			eq[i] = new JPanel();
			ea[i] = new JPanel();
			if(i==1){
				bql[i] = new JLabel("QUANTITY");
				bal[i] = new JLabel("AMOUNTS");
				iql[i] = new JLabel("QUANTITY");
				ial[i] = new JLabel("AMOUNTS");
				dql[i] = new JLabel("QUANTITY");
				dal[i] = new JLabel("AMOUNTS");
				eql[i] = new JLabel("QUANTITY");
				eal[i] = new JLabel("AMOUNTS");
			}
			else if(i==2||i==15){
				bql[i] = new JLabel("");
				bal[i] = new JLabel("");
				iql[i] = new JLabel("");
				ial[i] = new JLabel("");
				dql[i] = new JLabel("");
				dal[i] = new JLabel("");
				eql[i] = new JLabel("");
				eal[i] = new JLabel("");
			}
			else{

				bql[i] = new JLabel(" ");
				bal[i] = new JLabel(" ");
				iql[i] = new JLabel(" ");
				ial[i] = new JLabel(" ");
				dql[i] = new JLabel(" ");
				dal[i] = new JLabel(" ");
				eql[i] = new JLabel(" ");
				eal[i] = new JLabel(" ");
			}
			
			bq[i].add(bql[i]);
			ba[i].add(bal[i]);
			iq[i].add(iql[i]);
			ia[i].add(ial[i]);
			dq[i].add(dql[i]);
			da[i].add(dal[i]);
			eq[i].add(eql[i]);
			ea[i].add(eal[i]);
			if(i==2 || i==15){
				tl[i].setHorizontalAlignment(JLabel.CENTER);
			}
			else{
				tl[i].setHorizontalAlignment(JLabel.LEFT);
			}
			bql[i].setHorizontalAlignment(JLabel.CENTER);
			bql[i].setVerticalAlignment(JLabel.CENTER);
			
			bal[i].setHorizontalAlignment(JLabel.CENTER);
			bal[i].setVerticalAlignment(JLabel.CENTER);
			
			iql[i].setHorizontalAlignment(JLabel.CENTER);
			iql[i].setVerticalAlignment(JLabel.CENTER);
			
			ial[i].setHorizontalAlignment(JLabel.CENTER);
			ial[i].setVerticalAlignment(JLabel.CENTER);
			
			dql[i].setHorizontalAlignment(JLabel.CENTER);
			dql[i].setVerticalAlignment(JLabel.CENTER);
			
			dal[i].setHorizontalAlignment(JLabel.CENTER);
			dal[i].setVerticalAlignment(JLabel.CENTER);
			
			eql[i].setHorizontalAlignment(JLabel.CENTER);
			eql[i].setVerticalAlignment(JLabel.CENTER);
			
			eal[i].setHorizontalAlignment(JLabel.CENTER);
			eal[i].setVerticalAlignment(JLabel.CENTER);
			
			center.add(jp[i]);
			if(i==0){
				jp[0].add(items);jp[0].add(beginning);jp[0].add(increase);jp[0].add(decrease);jp[0].add(ending);
			}
			else{//i가 1이상
				for(int k=0;k<num2;k++){
					jp2[i][k] = new JPanel();
					jp[i].add(jp2[i][k]);
					if(k==0){
						jp2[i][k].setLayout(new GridLayout());
						jp2[i][k].add(tp[i]);
					}
					if(k==0 && i==1){
						jp2[i][k].setLayout(new GridLayout(1,1,5,0));
					}
					else{
						if(k>0 && i>0){
							jp2[i][k].setLayout(new GridLayout(1,2,0,0));
							if(i>1){
								bq[i].setBorder(BorderFactory.createLineBorder(Color.black));
								ba[i].setBorder(BorderFactory.createLineBorder(Color.black));
								iq[i].setBorder(BorderFactory.createLineBorder(Color.black));
								ia[i].setBorder(BorderFactory.createLineBorder(Color.black));
								dq[i].setBorder(BorderFactory.createLineBorder(Color.black));
								da[i].setBorder(BorderFactory.createLineBorder(Color.black));
								eq[i].setBorder(BorderFactory.createLineBorder(Color.black));
								ea[i].setBorder(BorderFactory.createLineBorder(Color.black));
							}
							
							if(k==1){
								jp2[i][k].add(bq[i]);
								jp2[i][k].add(ba[i]);
							}
							else if(k==2){
								jp2[i][k].add(iq[i]);
								jp2[i][k].add(ia[i]);
							}
							else if(k==3){
								jp2[i][k].add(dq[i]);
								jp2[i][k].add(da[i]);
							}
							else if(k==4){
								jp2[i][k].add(eq[i]);
								jp2[i][k].add(ea[i]);
							}
						}
						
					}
				}
			}
		}
		
		tl[2].setText("[ Complimentary ]");
		tl[3].setText("Type II (NEW)");
		tl[4].setText("Type II (USED)");
		tl[5].setText("Special C: 120 cuft");
		tl[6].setText("Special D: 90 cuft");
		tl[7].setText("Special F: 55 cuft");
		tl[8].setText("Stansil");
		tl[9].setText("Nail");
		tl[10].setText("Crip");
		tl[11].setText("Compound");
		tl[12].setText("HB Box 10.0 cuft");
		tl[13].setText("HB Box 13.6 cuft");
		tl[14].setText("HB Box 21.0 cuft");
		tl[15].setText("[ Compensation ]");
		tl[16].setText("Cotton Box 2 cuft");
		tl[17].setText("Cotton Box 4 cuft");
		tl[18].setText("Cotton Box 6 cuft");
		tl[19].setText("Card board");
		tl[20].setText("Paper (평면지)");
		tl[21].setText("Tape");
		tl[22].setText("Inside paper (선화지)");
		tl[23].setText("Air cap");
		tl[24].setText("Wardrobe box");
		
//		for(int i=0;i<values;i++){
//			bq[i] = new JPanel();
//			ba[i] = new JPanel();
//			iq[i] = new JPanel();
//			ia[i] = new JPanel();
//			dq[i] = new JPanel();
//			da[i] = new JPanel();
//			eq[i] = new JPanel();
//			ea[i] = new JPanel();
//			if(i==0||i==12){
//				bql[i] = new JLabel("------");
//				bal[i] = new JLabel("------");
//				iql[i] = new JLabel("------");
//				ial[i] = new JLabel("------");
//				dql[i] = new JLabel("------");
//				dal[i] = new JLabel("------");
//				eql[i] = new JLabel("------");
//				eal[i] = new JLabel("------");
//			}
//			else{
//				bql[i] = new JLabel("");
//				bal[i] = new JLabel("");
//				iql[i] = new JLabel("");
//				ial[i] = new JLabel("");
//				dql[i] = new JLabel("");
//				dal[i] = new JLabel("");
//				eql[i] = new JLabel("");
//				eal[i] = new JLabel("");
//			}
//			bql[i].setHorizontalAlignment(JLabel.CENTER);
//			bal[i].setHorizontalAlignment(JLabel.CENTER);
//			iql[i].setHorizontalAlignment(JLabel.CENTER);
//			ial[i].setHorizontalAlignment(JLabel.CENTER);
//			dql[i].setHorizontalAlignment(JLabel.CENTER);
//			dal[i].setHorizontalAlignment(JLabel.CENTER);
//			eql[i].setHorizontalAlignment(JLabel.CENTER);
//			eal[i].setHorizontalAlignment(JLabel.CENTER);
//		}
		
		
			
//		center.setLayout(new GridLayout(1,5));
		///////////////////////////////////////////////////////////////////		
//		String colName[] = {"ITEMS"};
//
//		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
//		DefaultTableModel model = new DefaultTableModel(colName,0);
//		JTable table = new JTable(model);
//		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
//		TableColumnModel tcm = table.getColumnModel();
//		tcm.getColumn(0).setCellRenderer(dtcr);
//		JScrollPane scrollpane = new JScrollPane(table);
//		ArrayList<String> resultList = new ArrayList<>();
//		String item=itemsCombo.getSelectedItem().toString();
//		String begin=startPeriod.getText();
//		String end=endPeriod.getText();
//		String area = areaCombo.getSelectedItem().toString();
//		autoCreateBorderLayout(center,200, 200, 68, 60);
//		String[] values = {" ","COMPLIMENTARY","Type II (NEW)","Type II (USED)","Special C: 120 cuft","Special D: 90  cuft","Special F: 55  cuft","Stansil","Nail","Crip",
//				"Compound","HB Box 10.0 cuft","HB Box 13.6 cuft"," HB Box 21.0 cuft",//COMPLIMENTARY ITEM LIST
//				"COMPENSATION","Cotton Box 2 cuft","Cotton Box 4 cuft","Cotton Box 6 cuft","Cart Board","Paper (평면지)","Tape","Inside Paper (선화지)","Air cap","Wardrobe box"};
//				//COMPENSATION ITEM LIST
//		for(int i=0;i<values.length;i++){
//			String[] input = new String[1];
//			input[0] = values[i];
//			model.addRow(input);
//		}
//		for(int i=0;i<4;i++){
//			Vector<String> input = new Vector<String>();
//			
//		}
//		center.add(scrollpane); //////////////////////////////////////////////// 1 add
//		validate();
//		resultList = dao.getInventoryStatItems(item, area, begin, end);
		
		///////////////////////////////////////////////////////////////////
		validate();
	}
	
	public void showResult(){
		for(int i=2;i<tl.length;i++){
			bql[i].setText("");
			bal[i].setText("");
			iql[i].setText("");
			ial[i].setText("");
			dql[i].setText("");
			dal[i].setText("");
			eql[i].setText("");
			eal[i].setText("");
		}
		String items = itemsCombo.getSelectedItem().toString();
		ArrayList<InventoryStatBeans> is = new ArrayList<>();
		String start = startPeriod.getText();
		String end = endPeriod.getText();
		String area = areaCombo.getSelectedItem().toString();
		is = dao.getInventoryStat(items,area,start,end);
		for(int j=0;j<is.size();j++){
			InventoryStatBeans isb = is.get(j);
			for(int i=0;i<tl.length;i++){
				if(isb.getItem().toUpperCase().equals(tl[i].getText().toUpperCase())){
					System.out.println("tl : "+tl[i].getText());
					System.out.println("isb : "+isb.getItem());
					bql[i].setText(isb.getBeginningQuantity()+"");
					bal[i].setText(isb.getBeginningAmounts()+"");
					iql[i].setText(isb.getIncreaseQuantity()+"");
					ial[i].setText(isb.getIncreaseAmounts()+"");
					dql[i].setText(isb.getDecreaseQuantity()+"");
					dal[i].setText(isb.getDecreaseAmounts()+"");
					eql[i].setText(isb.getEndingQuantity()+"");
					eal[i].setText(isb.getEndingAmounts()+"");
				}
				else{
					bq[i].setBackground(Color.DARK_GRAY);
					ba[i].setBackground(Color.DARK_GRAY);
					iq[i].setBackground(Color.DARK_GRAY);
					ia[i].setBackground(Color.DARK_GRAY);
					dq[i].setBackground(Color.DARK_GRAY);
					da[i].setBackground(Color.DARK_GRAY);
					eq[i].setBackground(Color.DARK_GRAY);
					ea[i].setBackground(Color.DARK_GRAY);
				}
			}
		}
		validate();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		bigCenterItems.setText("ITEMS : "+itemsCombo.getSelectedItem().toString()+"　　");
		bigCenterArea.setText("AREA : "+areaCombo.getSelectedItem().toString()+"　　");
		validate();
		if(e.getSource() == searchBtn){
			showResult();
		}
		
	}
	
}
