import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;




public class inventoryFilteringPage extends JFrame implements ActionListener{
	CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
	JComboBox itemsCombo = new JComboBox(dao.getInventoryFilteringItemsList().toArray());
	JComboBox areaCombo = new JComboBox(dao.getAreaList2().toArray());
	JComboBox scacCombo = new JComboBox(dao.getScacList().toArray());
	
	JButton searchBtn = new JButton("SEARCH");
	
	JLabel itemsLabel = new JLabel("Items :");
	JLabel areaLabel = new JLabel("　　AREA :");
	JLabel periodLabel = new JLabel("　　Period(YYYYMMDD) :");
	JLabel between = new JLabel("~");
	JLabel bigCenterItems = new JLabel("");
	JLabel bigCenterArea = new JLabel("");
	
	JTextField startPeriod =  new JTextField("",8);
	JTextField endPeriod = new JTextField("",8);
	
	JPanel center;
	
	public inventoryFilteringPage() {
		super("");
		super.setVisible(true);
		super.setResizable(false);
		super.setSize(900,650);
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

	public void allItemPage(String type){
		center.removeAll();
		JPanel west = new JPanel();
		JPanel east = new JPanel();
		west.setLayout(new GridLayout(14,1));
		JPanel[] inGrid = new JPanel[14];
		for(int i=0;i<14;i++){
			inGrid[i] = new JPanel();
			west.add(inGrid[i]);
			inGrid[i].setLayout(new FlowLayout(FlowLayout.CENTER));
		}
		JPanel[] wp = new JPanel[14];
		JPanel[] ep = new JPanel[14];
		JLabel[] wl = new JLabel[14];
		JLabel[] el = new JLabel[14];
		int wlCount=0;
		int elCount=0;
		for(int i=0;i<14;i++){
			wp[i] = new JPanel();
			wp[i].setPreferredSize(new Dimension(200,30));
			ep[i] = new JPanel();
			ep[i].setPreferredSize(new Dimension(100,30));
				wl[i] = new JLabel("");
				if(i!=0){
					wl[i].setHorizontalAlignment(JLabel.LEFT);
					ep[i].setBackground(Color.LIGHT_GRAY);
					wp[i].setBackground(Color.LIGHT_GRAY);
				}
				else{
					wl[0].setHorizontalAlignment(JLabel.CENTER);
				}
				wl[i].setPreferredSize(new Dimension(200,25));
				el[i] = new JLabel("");
				el[i].setPreferredSize(new Dimension(100,25));
				el[i].setHorizontalAlignment(JLabel.CENTER);
				el[i].setBackground(Color.white);
			inGrid[i].add(wp[i]);
			inGrid[i].add(ep[i]);
			
			
			wp[i].add(wl[wlCount++]);
			
			ep[i].add(el[elCount++]);
				
		}
		
		wl[0].setBorder(BorderFactory.createLineBorder(Color.black));
		el[0].setBorder(BorderFactory.createLineBorder(Color.black));
		wl[0].setText("ITEMS");							el[0].setText("QUANTITY");
		wl[1].setText("                  Complimentary (무상)"); el[1].setText("");
		wl[2].setText("                  Type II (NEW)");		el[2].setText("0");
		wl[3].setText("                  Type II (USED)");		el[3].setText("0");
		wl[4].setText("                  Special C: 120 cuft");	el[4].setText("0");
		wl[5].setText("                  Special D: 90  cuft");	el[5].setText("0");
		wl[6].setText("                  Special F: 55  cuft");	el[6].setText("0");
		wl[7].setText("                  Stansil");				el[7].setText("0");
		wl[8].setText("                  Nail");				el[8].setText("0");
		wl[9].setText("                  Crip");				el[9].setText("0");
		wl[10].setText("                  Compound");			el[10].setText("0");
		wl[11].setText("                  HB Box 10.0 cuft");	el[11].setText("0");
		wl[12].setText("                  HB Box 13.6 cuft");	el[12].setText("0");
		wl[13].setText("                  HB Box 21.0 cuft");	el[13].setText("0");


		//////////////////////////////////////////////////////////////////////////////////
		JPanel east2 = new JPanel();
		east2.setLayout(new GridLayout(14,1));
		JPanel[] inGrid2 = new JPanel[14];
		for(int i=0;i<14;i++){
			inGrid2[i] = new JPanel();
			east2.add(inGrid2[i]);
			inGrid2[i].setLayout(new FlowLayout(FlowLayout.CENTER));
		}
		JPanel[] wp2 = new JPanel[14];
		JPanel[] ep2 = new JPanel[14];
		JLabel[] wl2 = new JLabel[14];
		JLabel[] el2 = new JLabel[14];
		int wlCount2=0;
		int elCount2=0;
		for(int i=0;i<14;i++){
			wp2[i] = new JPanel();
			wp2[i].setPreferredSize(new Dimension(200,30));
			ep2[i] = new JPanel();
			ep2[i].setPreferredSize(new Dimension(100,30));
				wl2[i] = new JLabel("");
				if(i!=0){
					wl2[i].setHorizontalAlignment(JLabel.LEFT);
					if(i<11){
						wp2[i].setBackground(Color.LIGHT_GRAY);
						ep2[i].setBackground(Color.LIGHT_GRAY);
					}
				}
				else{
					wl2[0].setHorizontalAlignment(JLabel.CENTER);
				}
				wl2[i].setPreferredSize(new Dimension(200,25));
				el2[i] = new JLabel("");
				el2[i].setPreferredSize(new Dimension(100,25));
				el2[i].setHorizontalAlignment(JLabel.CENTER);
				el2[i].setBackground(Color.white);
			inGrid2[i].add(wp2[i]);
			inGrid2[i].add(ep2[i]);

			wp2[i].add(wl2[wlCount2++]);
			ep2[i].add(el2[elCount2++]);
				
		}
		
		wl2[0].setBorder(BorderFactory.createLineBorder(Color.black));
		el2[0].setBorder(BorderFactory.createLineBorder(Color.black));
		wl2[0].setText("ITEMS");							el2[0].setText("QUANTITY");
		wl2[1].setText("                  Compensation (유상)");		el2[1].setText("");
		wl2[2].setText("                  Cotton Box 2 cuft");		el2[2].setText("0");
		wl2[3].setText("                  Cotton Box 4 cuft");		el2[3].setText("0");
		wl2[4].setText("                  Cotton Box 6 cuft");		el2[4].setText("0");
		wl2[5].setText("                  Cart Board");				el2[5].setText("0");
		wl2[6].setText("                  Paper (평면지)");			el2[6].setText("0");
		wl2[7].setText("                  Tape");					el2[7].setText("0");
		wl2[8].setText("                  Inside Paper (선화지)");		el2[8].setText("0");
		wl2[9].setText("                  Air cap");				el2[9].setText("0");
		wl2[10].setText("                 Wardrobe box");			el2[10].setText("0");
		wl2[11].setText("　　　");				//el2[11].setText("");
		wl2[12].setText("　　　");				//el2[12].setText("");
		wl2[13].setText("　　　");				//el2[13].setText("");
		
		String area = areaCombo.getSelectedItem().toString();
		ArrayList<String> itemList = new ArrayList<>();
		String item="";
		int unitCount=0;
		int tempCount=0;
		String test = wl[3].getText().toUpperCase().trim();
		System.out.println(test);
		String begin = startPeriod.getText();
		String end = endPeriod.getText();
			itemList = dao.getInventorySuppliedAllItem(area,begin,end);
			for(int i=0;i<itemList.size();i++){
				tempCount=0;
				item = itemList.get(i).split("@")[0];
				unitCount = Integer.parseInt(itemList.get(i).split("@")[1]);
				for(int k=0;k<14;k++){
					if(item.equals(wl[k].getText().toUpperCase().trim())){
						tempCount = Integer.parseInt(el[k].getText());
						tempCount += unitCount;
						el[k].setText(tempCount+"");
					}
					if(item.equals(wl2[k].getText().toUpperCase().trim())){
						tempCount = Integer.parseInt(el2[k].getText());
						tempCount += unitCount;
						el2[k].setText(tempCount+"");
					}
				}
			}
			if(type.equals("ALL")){
				center.setLayout(new GridLayout(1,2));
				center.add(west);
				center.add(east2);
				validate();
			}
			else if(type.equals("COMPLIMENTARY")){
				center.setLayout(new GridLayout(1,1));
				center.add(west);
				validate();
			}
			else if(type.equals("COMPENSATION")){
				center.setLayout(new GridLayout(1,1));
				center.add(east2);
				validate();
			}
		validate();
	}
	public void oneItemPage(){
		center.removeAll();
		String colName[] = {"DATE","QUANTITY"};
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		DefaultTableModel model = new DefaultTableModel(colName,0);
		JTable table = new JTable(model);
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setCellRenderer(dtcr);
		tcm.getColumn(1).setCellRenderer(dtcr);
		table.setRowSorter(new TableRowSorter(model));
		JScrollPane scrollpane = new JScrollPane(table);
		autoCreateBorderLayout(center,200, 200, 30, 30);
		center.add("Center",scrollpane);
		ArrayList<String> resultList = new ArrayList<>();
		String item=itemsCombo.getSelectedItem().toString();
		String begin=startPeriod.getText();
		String end=endPeriod.getText();
		String area = areaCombo.getSelectedItem().toString();
		resultList = dao.getInventorySuppliedOneItems(item, area, begin, end);
		String date;
		String units;
		int totalCount=resultList.size();
		for(int i=0;i<totalCount;i++){
			date = resultList.get(i).split("@")[0];
			units = resultList.get(i).split("@")[1];
			String[] values = {date,units};
			model.addRow(values);
		}

//		
//		for(int i=0;i<totalCount;i++){
//			date="";
//			units="";
//			
//			wp[i] = new JPanel();
//			wp[i].setPreferredSize(new Dimension(200,30));
//				wl[i] = new JLabel();
//				wl[i].setPreferredSize(new Dimension(95,25));
//				wl[i].setHorizontalAlignment(JLabel.CENTER);
//				wl[i].setBackground(Color.LIGHT_GRAY);
//			ep[i] = new JPanel();
//			ep[i].setPreferredSize(new Dimension(100,30));
//				el[i] = new JLabel();
//				el[i].setPreferredSize(new Dimension(95,25));
//				el[i].setHorizontalAlignment(JLabel.CENTER);
//				el[i].setBackground(Color.LIGHT_GRAY);
//			
//			wp[i].add(wl[i]);
//			ep[i].add(el[i]);
//			
//			inGrid[i].add(wp[i]);
//			inGrid[i].add(ep[i]);
//			

//			
//			if(i==0){
//				wl[i].setText("DATE");
//					wl[i].setBorder(BorderFactory.createLineBorder(Color.black));
//				el[i].setText("QUANTITY");
//					el[i].setBorder(BorderFactory.createLineBorder(Color.black));
//			}
//			else{
//				wl[i].setText(date);
//				el[i].setText(units);
//			}
//		}
//		center.setLayout(new GridLayout(1,1));
//		center.add(west);
		validate();
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == searchBtn){
			bigCenterItems.setText("ITEMS : "+itemsCombo.getSelectedItem().toString()+"　　");
			bigCenterArea.setText("AREA : "+areaCombo.getSelectedItem().toString()+"　　");
			validate();
			String type = itemsCombo.getSelectedItem().toString();
			if(type.equals("ALL")||type.equals("COMPLIMENTARY")||type.equals("COMPENSATION")){
				allItemPage(type);
			}
			else{
				oneItemPage();
			}
		}
	}
}
