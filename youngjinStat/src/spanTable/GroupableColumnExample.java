package spanTable;

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

public class GroupableColumnExample extends JFrame {
	JTable table = new JTable( /*dm, new GroupableTableColumnModel()*/);
    GroupableColumnExample() {
        
        DefaultTableModel dm = new DefaultTableModel();
        dm.setDataVector(new Object[][]{
            {"119","Finbar", "John","Saunders","ja","ko","zh"},
            {"911","Roger", "Peter","Melly","en","fr","pt"}},
            new Object[]{"SNo.","First", "1","2","Native","2","3"});
            
            // Setup table
            
            table.setColumnModel(new GroupableTableColumnModel());
            table.setTableHeader(new GroupableTableHeader((GroupableTableColumnModel)table.getColumnModel()));
            table.setModel(dm);
            
            // Setup Column Groups
            GroupableTableColumnModel cm = (GroupableTableColumnModel)table.getColumnModel();
            
            ColumnGroup g_name = new ColumnGroup( "Name");
            g_name.add(cm.getColumn(1));
            ColumnGroup g_token = new ColumnGroup("Second");
            g_name.add(g_token);
            g_token.add(cm.getColumn(2));
            g_token.add(cm.getColumn(3));
            ColumnGroup g_lang = new ColumnGroup("Language");
            g_lang.add(cm.getColumn(4));
            ColumnGroup g_other = new ColumnGroup("Others");
            g_other.add(cm.getColumn(5));
            g_other.add(cm.getColumn(6));
            g_lang.add(g_other);
            GroupableTableHeader header = (GroupableTableHeader)table.getTableHeader();
            cm.addColumnGroup(g_name);
            cm.addColumnGroup(g_lang);
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