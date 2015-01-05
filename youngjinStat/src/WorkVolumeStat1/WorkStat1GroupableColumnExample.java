package WorkVolumeStat1;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import TableOption.ColumnGroup;
import TableOption.GroupableTableColumnModel;
import TableOption.GroupableTableHeader;

public class WorkStat1GroupableColumnExample extends JFrame {
	JTable table = new JTable( /*dm, new GroupableTableColumnModel()*/);
	String[][] test= new String[25][20];
//			new String[][]{
//            {"119","Finbar", "John","Saunders","ja","ko","zh"},
//            {"911","Roger", "Peter","Melly","en","fr","pt"},
//            {"911","Roger", "Peter","Melly","en","fr","pt"},
//            {"911","Roger", "Peter","Melly","en","fr","pt"},
//            {"911","Roger", "Peter","Melly","en","fr","pt"},
//            {"911","Roger", "Peter","Melly","en","fr","pt"},
//            {"911","Roger", "Peter","Melly","en","fr","pt"},
//            {"911","Roger", "Peter","Melly","en","fr","pt"},
//            {"911","Roger", "Peter","Melly","en","fr","pt"},
//            {"911","Roger", "Peter","Melly","en","fr","pt"},
//            {"911","Roger", "Peter","Melly","en","fr","pt"},
//            {"911","Roger", "Peter","Melly","en","fr","pt"}
//	};
	
	
	
	WorkStat1GroupableColumnExample(String [][]val) {
    	System.out.println("val example : "+val[2][2]);
        String[] jobWeight = new String[20];
        for(int i=0;i<20;i++){
        	jobWeight[i] = new String();
        	if(i%2==0){
        		jobWeight[i] = "job";
        	}
        	else{
        		jobWeight[i] = "weight";
        	}
        	
        }
       test = val;
        DefaultTableModel dm = new DefaultTableModel();
        
        dm.setDataVector(val,jobWeight);
            // Setup table
            table.setColumnModel(new GroupableTableColumnModel());
            table.setTableHeader(new GroupableTableHeader((GroupableTableColumnModel)table.getColumnModel()));
            table.setModel(dm);
            GroupableTableColumnModel cm = (GroupableTableColumnModel)table.getColumnModel();
            ColumnGroup ys = new ColumnGroup("YS");
            ys.add(cm.getColumn(0));
            ys.add(cm.getColumn(1));
            ColumnGroup os = new ColumnGroup("OS");
            os.add(cm.getColumn(2));
            os.add(cm.getColumn(3));
            ColumnGroup pyt = new ColumnGroup("PYT");
            pyt.add(cm.getColumn(4));
            pyt.add(cm.getColumn(5));
            ColumnGroup tdc = new ColumnGroup("TDC");
            tdc.add(cm.getColumn(6));
            tdc.add(cm.getColumn(7));
            ColumnGroup ujb = new ColumnGroup("UJB");
            ujb.add(cm.getColumn(8));
            ujb.add(cm.getColumn(9));
            ColumnGroup tg = new ColumnGroup("TG");
            tg.add(cm.getColumn(10));
            tg.add(cm.getColumn(11));
            ColumnGroup bs = new ColumnGroup("BS");
            bs.add(cm.getColumn(12));
            bs.add(cm.getColumn(13));
            ColumnGroup ks = new ColumnGroup("KS");
            ks.add(cm.getColumn(14));
            ks.add(cm.getColumn(15));
            ColumnGroup other = new ColumnGroup("OTHER");
            other.add(cm.getColumn(16));
            other.add(cm.getColumn(17));
            ColumnGroup total = new ColumnGroup("TOTAL");
            total.add(cm.getColumn(18));
            total.add(cm.getColumn(19));
            GroupableTableHeader header = (GroupableTableHeader)table.getTableHeader();
            cm.addColumnGroup(ys);
            cm.addColumnGroup(os);
            cm.addColumnGroup(pyt);
            cm.addColumnGroup(tdc);
            cm.addColumnGroup(ujb);
            cm.addColumnGroup(tg);
            cm.addColumnGroup(bs);
            cm.addColumnGroup(ks);
            cm.addColumnGroup(other);
            cm.addColumnGroup(total);
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