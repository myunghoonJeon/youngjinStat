package mainpages;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @author jmh
 *
 */
public class EachScacInvoiceCollectionBeans {
	
	String invoiceDate;
	String gblNo;
	String gblQuantity;
	String invoicedAmounts;
	String collectedAmounts;
	String uncollectedAmounts;
	String shortpaidAmounts;
	String acceptedAmounts;
	String claimedAmounts;
	String netAmounts;
	String status;
	String gblSeq;
	String invoiceNo;
	String code;
	String process;

	public EachScacInvoiceCollectionBeans() {
		invoiceDate="";
		gblNo="";
		gblQuantity="0";
		invoicedAmounts="0.0";
		collectedAmounts="0.0";
		uncollectedAmounts="0.0";
		shortpaidAmounts="0.0";
		acceptedAmounts="0.0";
		claimedAmounts="0.0";
		netAmounts="0.0";
		status="";
		gblSeq="";
		invoiceNo="";
		code="";
		process="";
	}
	
	public String getGblQuantity() {
		return gblQuantity;
	}

	public void setGblQuantity(String gblQuantity) {
		this.gblQuantity = gblQuantity;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}
	public void showInvoiceNo(){
//		System.out.println("[ Invoice No : "+getInvoiceNo()+" ]");
	}
	public void addUncollectedAmount(String money){
		showInvoiceNo();
		money = checkAmountsNull(money);
		System.out.println("[ Set Uncollected Amounts : "+money+" ]");
		double temp = Double.parseDouble(checkAmountsNull(getUncollectedAmounts()));
		System.out.println(temp+" + "+Double.parseDouble(checkAmountsNull(money)));
		temp+=Double.parseDouble(checkAmountsNull(money));
		setUncollectedAmounts(checkAmountsNull(temp+""));
		System.out.println("[ Set Final Uncollected Amounts : "+temp+" ]");
	}
//	public void addGblQuantity(){
//		gblQuantity++;
//	}
	public void addCollectedAmount(String money){
		money = checkAmountsNull(money);
		System.out.println("[ Input Collected Amounts : "+money+" ]");
		double temp = Double.parseDouble(checkAmountsNull(getCollectedAmounts()));
		System.out.println(temp+" + "+Double.parseDouble(checkAmountsNull(money)));
		temp+=Double.parseDouble(checkAmountsNull(money));
		setCollectedAmounts(checkAmountsNull(temp+""));
		System.out.println("[ Set Final Collected Amounts : "+temp+" ]");
	}
	public void addShortPaid(String money){
		showInvoiceNo();
		money = checkAmountsNull(money);
		System.out.println("[ Input ShortPaid Amounts : "+money+" ]");
		double temp = Double.parseDouble(checkAmountsNull(getShortpaidAmounts()));
		System.out.println(temp+" + "+Double.parseDouble(checkAmountsNull(money)));
		temp+=Double.parseDouble(checkAmountsNull(money));
		setShortpaidAmounts(checkAmountsNull(temp+""));
		System.out.println("[ Set Final ShortPaid Amounts : "+temp+" ]");
	}
	public void addAcceptPaid(String money){
		showInvoiceNo();
		money = checkAmountsNull(money);
		System.out.println("[ Input AcceptPaid Amounts : "+money+" ]");
		double temp = Double.parseDouble(checkAmountsNull(getAcceptedAmounts()));
		System.out.println(temp+" + "+Double.parseDouble(checkAmountsNull(money)));
		temp+=Double.parseDouble(checkAmountsNull(money));
		setAcceptedAmounts(checkAmountsNull(temp+""));
		System.out.println("[ Set Final AcceptPaid Amounts : "+temp+" ]");
	}
	public void addClaimPaid(String money){
		showInvoiceNo();
		money = checkAmountsNull(money);
		System.out.println("[ Input ClaimPaid Amounts : "+money+" ]");
		double temp = Double.parseDouble(checkAmountsNull(getClaimedAmounts()));
		System.out.println(temp+" + "+Double.parseDouble(checkAmountsNull(money)));
		temp+=Double.parseDouble(checkAmountsNull(money));
		setClaimedAmounts(checkAmountsNull(temp+""));
		System.out.println("[ Set Final ClaimedPaid Amounts : "+temp+" ]");
	}
	public String checkAmountsNull(String str){
		String result;
		if(str.equals("")){
			result="0.0";
		}
		else{
	   		result = new DecimalFormat("###0.00").format(Double.parseDouble(str));
		}
		return result;
	}
	public String getGblSeq() {
		return gblSeq;
	}
	public void setGblSeq(String gblSeq) {
		this.gblSeq = gblSeq;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getInvoiceListSeq() {
		return invoiceListSeq;
	}
	public void setInvoiceListSeq(String invoiceListSeq) {
		this.invoiceListSeq = invoiceListSeq;
	}
	public String getInvoiceGblSeq() {
		return invoiceGblSeq;
	}
	public void setInvoiceGblSeq(String invoiceGblSeq) {
		this.invoiceGblSeq = invoiceGblSeq;
	}
	String invoiceListSeq;
	String invoiceGblSeq;
	String invoiceGblCollectionSeq;
		
	public String getInvoiceGblCollectionSeq() {
		return invoiceGblCollectionSeq;
	}
	public void setInvoiceGblCollectionSeq(String invoiceGblCollectionSeq) {
		this.invoiceGblCollectionSeq = invoiceGblCollectionSeq;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getGblNo() {
		return gblNo;
	}
	public void setGblNo(String gblNo) {
		this.gblNo = gblNo;
	}
//	public int getGblQuantity() {
//		return gblQuantity;
//	}
//	public void setGblQuantity(String gblQuantity) {
//		this.gblQuantity = gblQuantity;
//	}
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
	public String getUncollectedAmounts() {
		return uncollectedAmounts;
	}
	public void setUncollectedAmounts(String uncollectedAmounts) {
		this.uncollectedAmounts = uncollectedAmounts;
	}
	public String getShortpaidAmounts() {
		return shortpaidAmounts;
	}
	public void setShortpaidAmounts(String shorpaidAmounts) {
		this.shortpaidAmounts = shorpaidAmounts;
	}
	public String getAcceptedAmounts() {
		return acceptedAmounts;
	}
	public void setAcceptedAmounts(String acceptedAmounts) {
		this.acceptedAmounts = acceptedAmounts;
	}
	public String getClaimedAmounts() {
		return claimedAmounts;
	}
	public void setClaimedAmounts(String claimedAmounts) {
		this.claimedAmounts = claimedAmounts;
	}
	public String getNetAmounts() {
		return netAmounts;
	}
	public void setNetAmounts(String netAmounts) {
		this.netAmounts = netAmounts;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
