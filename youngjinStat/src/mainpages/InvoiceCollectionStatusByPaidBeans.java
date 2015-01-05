package mainpages;

import java.util.ArrayList;

public class InvoiceCollectionStatusByPaidBeans {
	private String date;
	private String tsp;
	private String invoiceDate;
	private String invoiceNo;
	private ArrayList<InvoiceCollectionStatusByPaidGblBeans> gblList;
	
	public InvoiceCollectionStatusByPaidBeans() {
		date="";
		tsp="";
		invoiceDate="";
		invoiceNo="";
		gblList = new ArrayList<InvoiceCollectionStatusByPaidGblBeans>();
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTsp() {
		return tsp;
	}
	public void setTsp(String tsp) {
		this.tsp = tsp;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public ArrayList<InvoiceCollectionStatusByPaidGblBeans> getGblList() {
		return gblList;
	}
	public void addGbl(InvoiceCollectionStatusByPaidGblBeans gbl){
		gblList.add(gbl);
	}
}
