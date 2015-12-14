package mainpages;

public class InvoiceCollectionStatusByPaidGblBeans {
	public String getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}
	private String gblNo;
	private String amount;
	private String paidAmount;
	private String paidDate;
	
	public InvoiceCollectionStatusByPaidGblBeans() {
		// TODO Auto-generated constructor stub
		gblNo="";
		amount="";
		paidAmount="";
		paidDate="";
	}
	
	public String getGblNo() {
		return gblNo;
	}
	public void setGblNo(String gblNo) {
		this.gblNo = gblNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}
	
}
