package AllScacTotalInvoiceCollectionStatusGBL;
import TableOption.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.tools.DiagnosticCollector;

// Steve Webb 16/09/04 swebb99_uk@hotmail.com

public class AllScacTotalInvoiceGroupableColumn extends JFrame {
	JTable table = new JTable( /*dm, new GroupableTableColumnModel()*/);

    AllScacTotalInvoiceGroupableColumn(String [][]val) {
        String[] jobWeight = {"Quantity","invoiced Amount","Collected","Uncollected","Short Paid","Accepted","Claimed","Net"};
        DefaultTableModel dm = new DefaultTableModel();
        
        dm.setDataVector(val,jobWeight);
            // Setup table
            table.setColumnModel(new GroupableTableColumnModel());
            table.setTableHeader(new GroupableTableHeader((GroupableTableColumnModel)table.getColumnModel()));
            table.setModel(dm);
//            GroupableTableColumnModel cm = (GroupableTableColumnModel)table.getColumnModel();
//            ColumnGroup ys = new ColumnGroup("YS");
//            ColumnGroup os = new ColumnGroup("OS");
//            ColumnGroup pyt = new ColumnGroup("PYT");
//            ColumnGroup tdc = new ColumnGroup("TDC");
//            ColumnGroup ujb = new ColumnGroup("UJB");
//            ColumnGroup tg = new ColumnGroup("TG");
//            ColumnGroup bs = new ColumnGroup("BS");
//            ColumnGroup ks = new ColumnGroup("KS");
//            ColumnGroup other = new ColumnGroup("OTHER");
//            ColumnGroup total = new ColumnGroup("TOTAL");
//            GroupableTableHeader header = (GroupableTableHeader)table.getTableHeader();
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