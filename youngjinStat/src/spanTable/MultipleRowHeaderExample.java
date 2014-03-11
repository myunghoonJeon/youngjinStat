package spanTable;
/* (swing1.1) (swing#1356,#1454) */
//package jp.gr.java_conf.tame.swing.examples;

import java.awt.Color;
import java.awt.Component;

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
  String[][] val = new String[23][20];
  public MultipleRowHeaderExample(String[][] resultStr) {
    val = resultStr;
    data =  new Object[][]{
        {"IN","HHG","3,4"},
        {""  ,"","5"},
        {""  ,"","T"},
        {""  ,"","other"},
        {""  ,"total","(HHG)"},
        {""  ,"UB","7"},
        {""  ,"","8"},
        {""  ,"","J"},
        {""  ,"","others"},
        {""  ,"total","(UB)"},
        {""  ,"total","(IN)"},
        //////////////////////
        {"OUT","HHG","3,4"},
        {""   ,"","5"},
        {""   ,"","T"},
        {""   ,"","other"},
        {""   ,"total","(HHG)"},
        {""   ,"UB","7"},
        {""   ,"","8"},
        {""   ,"","J"},
        {""   ,"","others"},
        {""   ,"total","(UB)"},
        {""   ,"total","(OUT)"},
        {"TOTAL","",""},
       };
    
    column = new Object[]{"","",""};
    AttributiveCellTableModel fixedModel = new AttributiveCellTableModel(data, column) {
      public boolean CellEditable(int row, int col) { 
        return false; 
      }
    };
    
    CellSpan cellAtt =(CellSpan)fixedModel.getCellAttribute();
    
    cellAtt.combine(new int[] {0,1,2,3,4,5,6,7,8,9,10,11}    ,new int[] {0});
    cellAtt.combine(new int[] {0,1,2,3 }, new int[]{1});
    cellAtt.combine(new int[] {5,6,7,8 }, new int[]{1});
    cellAtt.combine(new int[] {12,13,14,15,16,17,18,19,20,21}    ,new int[] {0});
    cellAtt.combine(new int[] {22 }, new int[]{0,1,2});
    fixedTable = new MultiSpanCellTable( fixedModel );
//    fixedTable.setBorder(BorderFactory.createLineBorder(Color.darkGray));2	2	
    GroupableColumnExample ge = new GroupableColumnExample(val);
    table = ge.getWorkvolumeTableColumnHeader();
//    table.setBorder(BorderFactory.createLineBorder(Color.darkGray));
    fixedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
    dtcr.setHorizontalAlignment(SwingConstants.CENTER);
    for(int i=0;i<20;i++){
    	table.getColumnModel().getColumn(i).setCellRenderer(dtcr);
    }
    table.setRowHeight(18);
    fixedTable.setRowHeight(17);
    
    Border lowBorder = BorderFactory.createBevelBorder(BevelBorder.LOWERED ,Color.LIGHT_GRAY,Color.lightGray);
    Border raisedBorder = BorderFactory.createBevelBorder(BevelBorder.LOWERED ,Color.gray,Color.gray);
    fixedTable.setBorder(raisedBorder);
    table.getTableHeader().setBackground(Color.LIGHT_GRAY);
    fixedTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
    fixedTable.setDefaultRenderer(Object.class, new RowHeaderRenderer(fixedTable));
    fixedTable.setGridColor(table.getTableHeader().getBackground());
    /**************************[ SET COLUMN ROW WIDTH ]*****************************/
    fixedTable.getColumnModel().getColumn(0).setPreferredWidth(30);//in/out width
    fixedTable.getColumnModel().getColumn(1).setPreferredWidth(40);//in/out width
    fixedTable.getColumnModel().getColumn(2).setPreferredWidth(50);//in/out width
    /*******************************************************************************/
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
