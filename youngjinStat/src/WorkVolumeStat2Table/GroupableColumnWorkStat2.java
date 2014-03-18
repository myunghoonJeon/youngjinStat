package WorkVolumeStat2Table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.tools.DiagnosticCollector;

// Steve Webb 16/09/04 swebb99_uk@hotmail.com

public class GroupableColumnWorkStat2 extends JFrame {
	JTable table = new JTable( /*dm, new GroupableTableColumnModel()*/);
	int COLUM_LENGTH = 22;
    GroupableColumnWorkStat2(String [][]val,String type) {
        String[] jobWeight = new String[COLUM_LENGTH];
        for(int i=0;i<COLUM_LENGTH;i++){
        	jobWeight[i] = new String();
        	if(i%2==0){
        		jobWeight[i] = "job";
        	}
        	else{
        		jobWeight[i] = "weight";
        	}
        }

       DefaultTableModel dm = new DefaultTableModel();
       dm.setDataVector(val,jobWeight);
            // Setup table
            table.setColumnModel(new GroupableTableColumnModel());
            table.setTableHeader(new GroupableTableHeader((GroupableTableColumnModel)table.getColumnModel()));
            table.setModel(dm);
            GroupableTableColumnModel cm = (GroupableTableColumnModel)table.getColumnModel();
            cm.getColumn(19).setWidth(100);
            ColumnGroup title = null;
            if(type.equals("IN")){
            	title = new ColumnGroup("INBOUND");
            }
            else if(type.equals("OUT")){
            	title = new ColumnGroup("OUTBOUND");
            }
            else if(type.equals("ALL")){
            	title = new ColumnGroup("OUTBOUND / INBOUND");
            }
            else{
            	title = new ColumnGroup(" ");
            }
            ColumnGroup ys = new ColumnGroup("3,4");
            ys.add(cm.getColumn(0));
            ys.add(cm.getColumn(1));
            ColumnGroup os = new ColumnGroup("6");
            os.add(cm.getColumn(2));
            os.add(cm.getColumn(3));
            ColumnGroup pyt = new ColumnGroup("T");
            pyt.add(cm.getColumn(4));
            pyt.add(cm.getColumn(5));
            ColumnGroup tdc = new ColumnGroup("HHG other");
            tdc.add(cm.getColumn(6));
            tdc.add(cm.getColumn(7));
            ColumnGroup ujb = new ColumnGroup("HHG TOTAL");
            ujb.add(cm.getColumn(8));
            ujb.add(cm.getColumn(9));
            ColumnGroup tg = new ColumnGroup("7");
            tg.add(cm.getColumn(10));
            tg.add(cm.getColumn(11));
            ColumnGroup bs = new ColumnGroup("8");
            bs.add(cm.getColumn(12));
            bs.add(cm.getColumn(13));
            ColumnGroup ks = new ColumnGroup("J");
            ks.add(cm.getColumn(14));
            ks.add(cm.getColumn(15));
            ColumnGroup other = new ColumnGroup("UB OTHER");
            other.add(cm.getColumn(16));
            other.add(cm.getColumn(17));
            ColumnGroup ubTotal = new ColumnGroup("UB TOTAL");
            ubTotal.add(cm.getColumn(18));
            ubTotal.add(cm.getColumn(19));
            ColumnGroup inTotal = new ColumnGroup("IN TOTAL");
            inTotal.add(cm.getColumn(20));
            inTotal.add(cm.getColumn(21));
            GroupableTableHeader header = (GroupableTableHeader)table.getTableHeader();
            title.add(ys);
            title.add(os);
            title.add(pyt);
            title.add(tdc);
            title.add(ujb);
            title.add(tg);
            title.add(bs);
            title.add(ks);
            title.add(other);
            title.add(ubTotal);
            title.add(inTotal);
            cm.addColumnGroup(title);
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
    
    public JTable getWorkvolumeTableColumnHeader(){
    	return this.table;
    }
}


/**
 * Demo renderer just to prove they can be used.
 */
class GroupableTableCellRenderer extends DefaultTableCellRenderer {
    /**
     *
     * @param table
     * @param value
     * @param selected
     * @param focused
     * @param row
     * @param column
     * @return
     */
    public Component getTableCellRendererComponent(JTable table, Object value,
    boolean selected, boolean focused, int row, int column) {
        JTableHeader header = table.getTableHeader();
        if (header != null) {
            setForeground(Color.WHITE);
            setBackground(Color.RED);
        }
        setHorizontalAlignment(SwingConstants.CENTER);
        setText(value != null ? value.toString() : " ");
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        return this;
    }
}