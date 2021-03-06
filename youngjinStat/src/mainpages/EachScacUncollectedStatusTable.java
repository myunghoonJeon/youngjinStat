package mainpages;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import TableOption.GroupableTableColumnModel;
import TableOption.GroupableTableHeader;
import EachScacUncollectedStatus.GroupableColumnEachscacUncollectedStatus;


public class EachScacUncollectedStatusTable {
	JTable table = new JTable( /*dm, new GroupableTableColumnModel()*/);
	int COLUM_LENGTH = 10;
	Object[][] obj = new Object[1][1];
	String[] jobWeight = new String[COLUM_LENGTH];
    public EachScacUncollectedStatusTable(ArrayList<EachScacUncollectedBeans> esic,String end,String begin) {
        
        for(int i=0;i<COLUM_LENGTH;i++){
        	jobWeight[i] = new String();
       }
        
       obj[0][0]="";
       jobWeight[0] = "Invoice Date";
       jobWeight[1] = "Invoice No";
       jobWeight[2] = "Quantity (GBL)";
       jobWeight[3] = "Invoice Amounts";
       jobWeight[4] = "Collected Amounts";
       jobWeight[5] = "Uncollected Amounts";
       jobWeight[6] = "Short paid";
       jobWeight[7] = "Accepted";
       jobWeight[8] = "Claimed";
       jobWeight[9] = "Net uncollected";
       
        
       DefaultTableModel dm = new DefaultTableModel();
       
       for(int i=0;i<esic.size();i++){
    	    String date ="";
    	    String invoiceNo="";
    	    String quantity="";
    	    String invoiceAmounts="";
	   		String collectedAmounts="";
	   		String uncollectedAmounts="";
	   		String shortPaid="";
	   		String accepted="";
	   		String claimed="";
	   		String net="";
	   		date = esic.get(i).getInvoiceDate();
			invoiceNo = esic.get(i).getInvoiceNo();
			invoiceAmounts = esic.get(i).getInvoicedAmounts();
			collectedAmounts = esic.get(i).getCollectedAmounts();
			uncollectedAmounts = esic.get(i).getUncollectedAmounts();
			shortPaid = esic.get(i).getShortpaidAmounts();
			accepted = esic.get(i).getAcceptedAmounts();
			claimed = esic.get(i).getClaimedAmounts();
			net = getDoubleValue(invoiceAmounts)-getDoubleValue(collectedAmounts)-getDoubleValue(accepted)+"";
			quantity = esic.get(i).getGblQuantity()+"";
    	   int diff = Integer.parseInt(esic.get(i).getDatediff());
    	   int b = Integer.parseInt(begin);
    	   int e = Integer.parseInt(end);
    	   if(b<diff && diff<e){
    		   dm.addRow(new String[]{date,invoiceNo,quantity,invoiceAmounts,collectedAmounts,uncollectedAmounts,shortPaid,accepted,claimed,net});
    	   }
       }
       
       
       dm.setDataVector(obj, jobWeight);
            // Setup table
            table.setColumnModel(new GroupableTableColumnModel());
            table.setTableHeader(new GroupableTableHeader((GroupableTableColumnModel)table.getColumnModel()));
            table.setModel(dm);
            GroupableTableColumnModel cm = (GroupableTableColumnModel)table.getColumnModel();
//            cm.getColumn(19).setWidth(100);
//            ColumnGroup title = null;
//            ColumnGroup ys = new ColumnGroup("3,4");
//            ys.add(cm.getColumn(0));
//            ys.add(cm.getColumn(1));
//            ColumnGroup os = new ColumnGroup("6");
//            os.add(cm.getColumn(2));
//            os.add(cm.getColumn(3));
//            ColumnGroup pyt = new ColumnGroup("T");
//            pyt.add(cm.getColumn(4));
//            pyt.add(cm.getColumn(5));
//            ColumnGroup tdc = new ColumnGroup("HHG other");
//            tdc.add(cm.getColumn(6));
//            tdc.add(cm.getColumn(7));
//            ColumnGroup ujb = new ColumnGroup("HHG TOTAL");
//            ujb.add(cm.getColumn(8));
//            ujb.add(cm.getColumn(9));
//            ColumnGroup tg = new ColumnGroup("7");
//            tg.add(cm.getColumn(10));
//            tg.add(cm.getColumn(11));
//            ColumnGroup bs = new ColumnGroup("8");
//            bs.add(cm.getColumn(12));
//            bs.add(cm.getColumn(13));
//            ColumnGroup ks = new ColumnGroup("J");
//            ks.add(cm.getColumn(14));
//            ks.add(cm.getColumn(15));
//            ColumnGroup other = new ColumnGroup("UB OTHER");
//            other.add(cm.getColumn(16));
//            other.add(cm.getColumn(17));
//            ColumnGroup ubTotal = new ColumnGroup("UB TOTAL");
//            ubTotal.add(cm.getColumn(18));
//            ubTotal.add(cm.getColumn(19));
//            ColumnGroup inTotal = new ColumnGroup("IN TOTAL");
//            inTotal.add(cm.getColumn(20));
//            inTotal.add(cm.getColumn(21));
            GroupableTableHeader header = (GroupableTableHeader)table.getTableHeader();
//            cm.addColumnGroup(title);
//            cm.addColumnGroup(ys);
//            cm.addColumnGroup(os);
//            cm.addColumnGroup(pyt);
//            cm.addColumnGroup(tdc);
//            cm.addColumnGroup(ujb);
//            cm.addColumnGroup(tg);
//            cm.addColumnGroup(bs);
//            cm.addColumnGroup(ks);
//            cm.addColumnGroup(other);
//            cm.addColumnGroup(total);
            
            // Finish off gui
    }
    public double getDoubleValue(String str){
		double d=0.0;
		d = Double.parseDouble(str);
		return d;
	}
    public JTable getWorkvolumeTableColumnHeader(){
    	return this.table;
    }
    public String[] getJobWeight(){
    	return this.jobWeight;
    }
}
