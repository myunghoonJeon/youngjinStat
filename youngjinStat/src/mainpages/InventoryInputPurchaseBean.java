package mainpages;
import javax.swing.JTextField;


public class InventoryInputPurchaseBean {
//	JTextField purchaseScacTextfield = new JTextField(15);
//	JTextField purchaseUnitCostTextfield = new JTextField(15);
//	JTextField purchaseUnitsTextfield = new JTextField(15);
//	JTextField purchasePriseTextfield = new JTextField(15);
//	JTextField purchaseDateTextfield = new JTextField(15);
	String scac = "";
	String unitcost="";
	String units="";
	String prise="";
	String data="";
	
	public String getScac() {
		return scac;
	}
	public void setScac(String scac) {
		this.scac = scac;
	}
	public String getUnitcost() {
		return unitcost;
	}
	public void setUnitcost(String unitcost) {
		this.unitcost = unitcost;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public String getPrise() {
		return prise;
	}
	public void setPrise(String prise) {
		this.prise = prise;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
