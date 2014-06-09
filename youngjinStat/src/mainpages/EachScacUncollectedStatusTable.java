package mainpages;
package EachScacUncollectedStatus;

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


public class EachScacUncollectedStatusTable {
	
	public JScrollPane tableLayout(JScrollPane js,ArrayList<EachScacInvoiceCollectionBeans> esic){
		
		js.removeAll();
		GroupableColumnEachscacUncollectedStatus ge = new GroupableColumnEachscacUncollectedStatus();
		String colName[] = ge.getJobWeight();
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		model = new DefaultTableModel(colName,0);
		String code = "";
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
		String status="";
		center.removeAll();
		for(int i=0;i<esic.size();i++){
			code =codeCombo.getSelectedItem().toString();
			date=esic.get(i).getInvoiceDate();
			invoiceNo = esic.get(i).getInvoiceNo();
			invoiceAmounts = esic.get(i).getInvoicedAmounts();
			collectedAmounts = esic.get(i).getCollectedAmounts();
			uncollectedAmounts = esic.get(i).getUncollectedAmounts();
			shortPaid = esic.get(i).getShortpaidAmounts();
			accepted = esic.get(i).getAcceptedAmounts();
			claimed = esic.get(i).getClaimedAmounts();
			net = getDoubleValue(collectedAmounts)+getDoubleValue(accepted)-getDoubleValue(shortPaid)+"";
			quantity = esic.get(i).getGblQuantity()+"";
			status=esic.get(i).getStatus();
			if(code.equals("ALL")){
				System.out.println("[NET : "+net+"]");
				String[] input = {date,invoiceNo,quantity,invoiceAmounts,collectedAmounts,uncollectedAmounts,shortPaid,accepted,claimed,net,status};
				model.addRow(input);
			}
			else{
				if(esic.get(i).getCode().equals(code)){// to operation when discover same code
					System.out.println("[NET : "+net+"]");
					String[] input = {date,invoiceNo,quantity,invoiceAmounts,collectedAmounts,uncollectedAmounts,shortPaid,accepted,claimed,net,status};
					model.addRow(input);
				}
			}
		}
		JTable table = new JTable(model);
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		table.setRowSorter(new TableRowSorter(model));
		js = new JScrollPane(table);
		js.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		for(int i=0;i<colName.length;i++){
			tcm.getColumn(i).setCellRenderer(dtcr);
		}
		setTableColumnWidth(tcm);
		js.setPreferredSize(new Dimension(950,400));
		center.add(js);
		validate();
		return js;
	}
}
