package mainpages;

/**
 * @author jmh
 *
 */
/**
 * @author jmh
 *
 */
public class InvoiceFilteringBeans {
	String invoiceNo="";
	String invoicedDate="";
	String invoicedAmounts="";
	String collectedAmounts="0.0";
	String unCollectedAmounts="";
	String net="0.0";
	
	public String getNet() {
		return net;
	}
	public void setNet(String net) {
		this.net = net;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getInvoicedDate() {
		return invoicedDate;
	}
	public void setInvoicedDate(String invoicedDate) {
		this.invoicedDate = invoicedDate;
	}
	public String getInvoicedAmounts() {
		return invoicedAmounts;
	}
	public void setInvoicedAmounts(String invoicedAmounts) {
		this.invoicedAmounts = invoicedAmounts;
		this.unCollectedAmounts = invoicedAmounts;
	}
	public String getCollectedAmounts() {
		return collectedAmounts;
	}
	public void setCollectedAmounts(String collectedAmounts) {
		this.collectedAmounts = collectedAmounts;
	}
	public String getUnCollectedAmounts() {
		return unCollectedAmounts;
	}
	public void setUnCollectedAmounts(String unCollectedAmounts) {
		this.unCollectedAmounts = unCollectedAmounts;
	}
	
}
