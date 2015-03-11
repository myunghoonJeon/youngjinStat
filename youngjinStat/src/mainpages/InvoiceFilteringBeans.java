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
	String invoicedAmounts="0.0";
	String collectedAmounts="0.0";
	String unCollectedAmounts="0.0";
	String net="0.0";
	String state="";
	public String getState() {
		return state;
	}
	public void setState(String state) {
		if(state==null){
			this.state="";
		}
		this.state = state;
	}
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
