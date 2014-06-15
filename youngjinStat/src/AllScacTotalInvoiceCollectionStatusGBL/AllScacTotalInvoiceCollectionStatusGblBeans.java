package AllScacTotalInvoiceCollectionStatusGBL;
import java.util.HashMap;


public class AllScacTotalInvoiceCollectionStatusGblBeans {
	private String gblNo;
	private String amount;
	private String start;
	private String end;
	private String name;
	private String code;
	private String difference;
	private String state;
	private String acceptAmount;
	private String claimeAmount;
	private String collectionAmount;
	private String scac;
	private String uncollectedAmount;
	private String process;
	private String shortPaid;
	
	public AllScacTotalInvoiceCollectionStatusGblBeans(){
		gblNo="";
		amount="0.0";
		start="";
		end="";
		name="";
		code="";
		difference="0.0";
		state="";
		acceptAmount="0.0";
		claimeAmount="0.0";
		collectionAmount="0.0";
		scac="";
		process="";
		shortPaid="0.0";
	}
	
	public String getShortPaid() {
		return shortPaid;
	}

	public void setShortPaid(String shortPaid) {
		this.shortPaid = shortPaid;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getUncollectedAmount() {
		return uncollectedAmount;
	}

	public void setUncollectedAmount(String uncollectedAmount) {
		this.uncollectedAmount = uncollectedAmount;
	}
	public String getScac() {
		return scac;
	}

	public void setScac(String scac) {
		this.scac = scac;
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
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDifference() {
		return difference;
	}
	public void setDifference(String difference) {
		this.difference = difference;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAcceptAmount() {
		return acceptAmount;
	}
	public void setAcceptAmount(String acceptAmount) {
		this.acceptAmount = acceptAmount;
	}
	public String getClaimeAmount() {
		return claimeAmount;
	}
	public void setClaimeAmount(String claimeAmount) {
		this.claimeAmount = claimeAmount;
	}
	public String getCollectionAmount() {
		return collectionAmount;
	}
	public void setCollectionAmount(String collectionAmount) {
		this.collectionAmount = collectionAmount;
	}
	
}
