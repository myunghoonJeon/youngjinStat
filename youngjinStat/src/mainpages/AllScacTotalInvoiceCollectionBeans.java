package mainpages;

import java.text.DecimalFormat;

public class AllScacTotalInvoiceCollectionBeans {
	private String carrier="";
	private String totalAmount="0";
	private String DepositAmount="0";
	private String AcceptAmount="0";
	private String ClaimeAmount="0";
	private String unCollectedAmount="0";
	private String netCollectedAmount="0";
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
	public void setValue(String totalAmount,String state,String collect,String minus){
		if(state==null){
//			System.out.println("이거되??");
			double unOrigin = getRoundValue(getUnCollectedAmount());
			unOrigin+=getRoundValue(totalAmount);
			setUnCollectedAmount(unOrigin+"");
		}
		else{
			if(state.equals("ACCEPT")){
				double origin = getRoundValue(getAcceptAmount());
				origin+=getRoundValue(collect);
				setAcceptAmount(origin+"");
				double unOrigin = getRoundValue(getUnCollectedAmount());
				if(getRoundValue(collect)<0){
					
				}
				else{
					unOrigin-=getRoundValue(collect);
				}
				setUnCollectedAmount(unOrigin+"");
			}
			else if(state.equals("DEPOSIT")){
				double origin = getRoundValue(getDepositAmount());
				origin+=getRoundValue(collect);
				setDepositAmount(origin+"");
				if(getRoundValue(minus)>0){
					double unOrigin = getRoundValue(getUnCollectedAmount());
					unOrigin+=getRoundValue(minus);
					setUnCollectedAmount(unOrigin+"");
				}
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
		
	}
	public void calcurateValue(){
		double net;
		double unCollectedAmount = getRoundValue(getUnCollectedAmount());
		double total = getRoundValue(getTotalAmount());
		double deposit = getRoundValue(getDepositAmount());
		double accept = getRoundValue(getAcceptAmount());
		double claime = getRoundValue(getClaimeAmount());
		
		if(deposit==0 && unCollectedAmount==0){
			System.out.println("이건??? : "+getCarrier());
			setUnCollectedAmount(totalAmount);
			unCollectedAmount =total;
		}
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
