package mainpages;

public class InvoiceCollectionStatusByPaidGblBeans {
	private String gblNo;
	private String amount;
	private String paidAmount;
	
	public InvoiceCollectionStatusByPaidGblBeans() {
		// TODO Auto-generated constructor stub
		gblNo="";
		amount="";
		paidAmount="";
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
