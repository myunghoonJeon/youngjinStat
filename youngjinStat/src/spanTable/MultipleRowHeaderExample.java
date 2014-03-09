package spanTable;
/* (swing1.1) (swing#1356,#1454) */
//package jp.gr.java_conf.tame.swing.examples;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;

  
/*   ----------------------------------------------
 *  |         SNo.        |
 *   ----------------------------------------------
 *  |          |     1    |
 *  |   Name   |-----------------------------------
 *  |          |     2    |
 *   ----------------------------------------------
 *  |          |     1    |
 *  |          |-----------------------------------
 *  | Language |     2    |
 *  |          |-----------------------------------
 *  |          |     3    |
 *   ----------------------------------------------
 */

/**
 * @version 1.0 03/06/99
 */
public class MultipleRowHeaderExample extends JFrame {
  Object[][] data;
  Object[] column;
  JTable table;
  MultiSpanCellTable fixedTable;
  JPanel test = new JPanel();
  JPanel north = new JPanel();
  JScrollPane scroll = null;
  public MultipleRowHeaderExample() {
    
    data =  new Object[][]{
        {"inbound"    ,""},
        {"Name"    ,"1"},
        {""        ,"2"},
        {"Language","1"},
        {""        ,"2"},
        {""        ,"3"},
        {""        ,"","3"}
        };
    
    column = new Object[]{"","",""};
    AttributiveCellTableModel fixedModel = new AttributiveCellTableModel(data, column) {
      public boolean CellEditable(int row, int col) { 
        return false; 
      }
    };
    
    CellSpan cellAtt =(CellSpan)fixedModel.getCellAttribute();
    cellAtt.combine(new int[] {0}    ,new int[] {0,1,2});
    cellAtt.combine(new int[] {1,2}  ,new int[] {0});
    cellAtt.combine(new int[] {5,6},new int[] {1});
    cellAtt.combine(new int[] {3,4,5,6},new int[] {0});
    fixedTable = new MultiSpanCellTable( fixedModel );
    GroupableColumnExample ge = new GroupableColumnExample();
    table = ge.getWorkvolumeTableColumnHeader();
    fixedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    fixedTable.setDefaultRenderer(Object.class, new RowHeaderRenderer(fixedTable));
    fixedTable.setGridColor(table.getTableHeader().getBackground());
    
    fixedTable.getColumnModel().getColumn(0).setPreferredWidth(100);
    
    scroll = new JScrollPane( table );
    JViewport viewport = new JViewport();
    viewport.setView(fixedTable);
    viewport.setPreferredSize(fixedTable.getPreferredSize());
    scroll.setRowHeaderView(viewport);
    
  }

  public JScrollPane getWorkVolumeStat1Table(){
	  return scroll;
  }

  
  class RowHeaderRenderer extends JLabel implements TableCellRenderer {  
    RowHeaderRenderer(JTable table) {
      JTableHeader header = table.getTableHeader();
      setOpaque(true);
      setBorder(UIManager.getBorder("TableHeader.cellBorder"));
      setHorizontalAlignment(CENTER);
      setForeground(header.getForeground());
      setBackground(header.getBackground());
      setFont(header.getFont());
    }
  
    public Component getTableCellRendererComponent(JTable table, Object value,
                          boolean isSelected, boolean hasFocus, int row, int column) {
      setText((value == null) ? "" : value.toString());
      return this;
    }
  }
  
}
