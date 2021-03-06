package EachScacInvoiceCollection;
/* (swing1.1) (swing#1356,#1454) */
//package jp.gr.java_conf.tame.swing.examples;
import TableOption.*;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import mainpages.CL_DAO_DB_Mysql;
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
public class MultipleRowHeaderEachscacInvoice extends JFrame {
  Object[][] data;
  Object[] column;
  JTable table;
  MultiSpanCellTable fixedTable;
  JPanel test = new JPanel();
  JPanel north = new JPanel();
  JScrollPane scroll = null;
  String[][] val;
  CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
  int INBOUND_COLUMN_SIZE=6;
    
  public MultipleRowHeaderEachscacInvoice() {
   data = new Object[1][1];
    column = new Object[]{""};
    AttributiveCellTableModel fixedModel = new AttributiveCellTableModel(data, column) {
      public boolean CellEditable(int row, int col) { 
        return false; 
      }
    };
    CellSpan cellAtt =(CellSpan)fixedModel.getCellAttribute();
    fixedTable = new MultiSpanCellTable( fixedModel );
//    fixedTable.setBorder(BorderFactory.createLineBorder(Color.darkGray));2	2	
    /*---------------------------column set---------------------------------*/
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    fixedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    
   
    
    
    fixedTable.setRowHeight(19);
    Border lowBorder = BorderFactory.createBevelBorder(BevelBorder.LOWERED ,Color.LIGHT_GRAY,Color.lightGray);
    Border raisedBorder = BorderFactory.createBevelBorder(BevelBorder.LOWERED ,Color.gray,Color.gray);
    fixedTable.setBorder(raisedBorder);
    table.getTableHeader().setBackground(Color.LIGHT_GRAY);
    fixedTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
    fixedTable.setDefaultRenderer(Object.class, new RowHeaderRenderer(fixedTable));
    fixedTable.setGridColor(table.getTableHeader().getBackground());
    /**************************[ SET COLUMN ROW WIDTH ]*****************************/
    fixedTable.getColumnModel().getColumn(0).setPreferredWidth(50);//in/out width
//    fixedTable.getColumnModel().getColumn(1).setPreferredWidth(40);//in/out width
//    fixedTable.getColumnModel().getColumn(21).setPreferredWidth(50);//in/out width
    /*******************************************************************************/
    scroll = new JScrollPane( table );
    JViewport viewport = new JViewport();
    viewport.setView(fixedTable);
    viewport.setPreferredSize(fixedTable.getPreferredSize());
    scroll.setRowHeaderView(viewport);
  }

  public JScrollPane getAllScacTotalInvoiceTable(){
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
