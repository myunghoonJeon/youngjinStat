package mainpages;

import java.text.DecimalFormat;

public class AllScacTotalInvoiceCollectionBeans {
	private String carrier="";
	private String totalAmount="";
	private String DepositAmount="";
	private String AcceptAmount="";
	private String ClaimeAmount="";
	private String unCollectedAmount="";
	private String netCollectedAmount="";
	public String getUnCollectedAmount() {
		return unCollectedAmount;
	}
	public void setUnCollectedAmount(String unCollectedAmount) {
		this.unCollectedAmount = unCollectedAmount;
	}
	public String getNetCollectedAmount() {
		return netCollectedAmount;
	}
	public void setNetCollectedAmount(String netCollectedAmount) {
		this.netCollectedAmount = netCollectedAmount;
	}
	public String getClaimeAmount() {
		return ClaimeAmount;
	}
	public void setClaimeAmount(String claimeAmount) {
		ClaimeAmount = claimeAmount;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getDepositAmount() {
		return DepositAmount;
	}
	public void setDepositAmount(String depositAmount) {
		DepositAmount = depositAmount;
	}
	public String getAcceptAmount() {
		return AcceptAmount;
	}
	public void setAcceptAmount(String acceptAmount) {
		AcceptAmount = acceptAmount;
	}
	public void setValue(String state,String collect){
		if(state.equals("ACCEPT")){
			double origin = getRoundValue(getAcceptAmount());
			origin+=getRoundValue(collect);
			setAcceptAmount(origin+"");
		}
		else if(state.equals("DEPOSIT")){
			double origin = getRoundValue(getDepositAmount());
			origin+=getRoundValue(collect);
			setDepositAmount(origin+"");
		}
		else if(state.equals("CLAIME")){
			double origin = getRoundValue(getClaimeAmount());
			origin+=getRoundValue(collect);
			setClaimeAmount(origin+"");
		}
		else if(state.equals("TOTAL")){
			double origin = getRoundValue(getTotalAmount());
			origin+=getRoundValue(collect);
			setTotalAmount(origin+"");
		}
	}
	public void calcurateValue(){
		double net;
		double unCollectedAmount;
		double total = getRoundValue(getTotalAmount());
		double deposit = getRoundValue(getDepositAmount());
		double accept = getRoundValue(getAcceptAmount());
		double claime = getRoundValue(getClaimeAmount());
		unCollectedAmount = total - deposit;
		setUnCollectedAmount(unCollectedAmount+"");
		net = total-unCollectedAmount - accept - claime;
		setNetCollectedAmount(net+"");
//		net = total-deposit-accept-claime;
		 
//		setNetCollectedAmount();
	}
	public Double getRoundValue(String str){
		String result ="";
		if(str==null || str.equals("")){
			return 0.0;
		}
		else{
		Double d = Double.parseDouble(str);
   			return d;
		}
   	}
}
