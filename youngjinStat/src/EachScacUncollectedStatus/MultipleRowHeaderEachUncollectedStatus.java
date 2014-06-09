package EachScacUncollectedStatus;
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
import mainpages.EachScacInvoiceCollectionBeans;
import mainpages.EachScacUncollectedBeans;
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
public class MultipleRowHeaderEachUncollectedStatus extends JFrame {
  Object[][] data;
  Object[] column;
  JTable table;
  MultiSpanCellTable fixedTable;
  JPanel test = new JPanel();
  JPanel north = new JPanel();
  JScrollPane scroll = null;
  CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
  int INBOUND_COLUMN_SIZE=10;
    
  public MultipleRowHeaderEachUncollectedStatus(ArrayList<EachScacUncollectedBeans> esicArr,String rowColumn,String end,String begin) {
    int dataSize = 1;
    data = new Object[dataSize][1];
    data[0][0] = rowColumn;
    column = new Object[]{""};
    AttributiveCellTableModel fixedModel = new AttributiveCellTableModel(data, column) {
      public boolean CellEditable(int row, int col) { 
        return false; 
      }
    };
    CellSpan cellAtt =(CellSpan)fixedModel.getCellAttribute();
    for(int i=0;i<dataSize;i++){
    	cellAtt.combine(new int[] {i}, new int[] {0});
    }
    fixedTable = new MultiSpanCellTable( fixedModel );
//    fixedTable.setBorder(BorderFactory.createLineBorder(Color.darkGray));2	2	
    /*---------------------------column set---------------------------------*/
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    GroupableColumnEachscacUncollectedStatus ge = new GroupableColumnEachscacUncollectedStatus(esicArr,end,begin);
   	table = ge.getWorkvolumeTableColumnHeader();
    table.setBorder(BorderFactory.createLineBorder(Color.darkGray));
    fixedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
    dtcr.setHorizontalAlignment(SwingConstants.CENTER);
    
    for(int i=0;i<INBOUND_COLUMN_SIZE;i++){
	   	table.getColumnModel().getColumn(i).setCellRenderer(dtcr);
	}
    
    table.setRowHeight(20);
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

  public JScrollPane getEachScacUncollectedStatusTable(){
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
