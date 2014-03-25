package mainpages;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class AllScacTotalInvoiceBeans {
	/******************[define parameter]************************/
	int COLUM_LENGTH = 6;
	CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
	ArrayList<String> scacList = dao.getScacList();
	int rowSize = scacList.size()+1;
	String[][] input = new String[rowSize][COLUM_LENGTH];
	HashMap<String,String> hashMapColumn = new HashMap<String,String>();
	HashMap<String,String> hashMapRow = new HashMap<String,String>();
	String type="";
	/************************************************************/

	public AllScacTotalInvoiceBeans() {
		initArr(input);
		hashMapRow = initHashmap("row");
	}
	
	public String[][] getStr(){
		return input;
	}
	
//	public void setWeightData(String code,String scac,String jobValue,String weightValue){
//		String column = hashMapColumn.get(code);
//		int job = Integer.parseInt(column)-1;
//		int weight = Integer.parseInt(column);
//		String scacStr = hashMapRow.get(scac);
//		int row = Integer.parseInt(scacStr);
//		
//		int originJob;
//		int originWeight;
//		if(input[row][job].equals("-")){
//			originJob = 0;
//		}
//		else{
//			originJob = Integer.parseInt(input[row][job]);
//		}
//		if(input[row][weight].equals("-")){
//			originWeight = 0;
//		}
//		else{
//			originWeight = Integer.parseInt(input[row][weight]);
//		}
//		originJob += Integer.parseInt(jobValue);
//		originWeight+= Double.parseDouble(weightValue);
//		
//		input[row][job] = originJob+"";
//		input[row][weight] = originWeight+"";
////		System.out.println("job : "+input[row][job]+" weight : "+input[row][weight]);
//		System.out.println("scac : "+scac +" code : "+code+" set - [job : "+jobValue +"][  weight :"+weightValue+" ]");
//	}
//	
//	public void setDensityData(String scac,String code,String jobValue,String densityValue){
//		String column = hashMapColumn.get(code);
//		int job = Integer.parseInt(column)-1;
//		int weight = Integer.parseInt(column);
//		
//		String codeStr = hashMapRow.get(scac);
//		
//		int row = Integer.parseInt(codeStr);
//		int originJob;
//		double originDensity;
//		if(input[row][job].equals("-")){
//			originJob = 0;
//		}
//		else{
//			originJob = Integer.parseInt(input[row][job]);
//		}
//		if(input[row][weight].equals("-")){
//			originDensity = 0.0;
//		}
//		else{
//			originDensity = Double.parseDouble(input[row][weight]);
//		}
//		originJob += Integer.parseInt(jobValue);
//		originDensity+= Double.parseDouble(densityValue);
//		
//		input[row][job] = originJob+"";
//		input[row][weight] = originDensity+"";
//		System.out.println("scac : "+scac +" code : "+code+" set - [job : "+jobValue +"][  weight :"+densityValue+" ]");
//	}
	
	public void setValue(String row,String invoiced,String collected,String unCollected,String accepted,String claimed,String net){
		String rowColumn = hashMapRow.get(row);
		int r = Integer.parseInt(rowColumn);
		double[] dlist;
		ArrayList<String> arr = new ArrayList<>();
		arr.add(invoiced);
		arr.add(collected);
		arr.add(unCollected);
		arr.add(accepted);
		arr.add(claimed);
		arr.add(net);
		dlist = getDoubleValue(arr);
		calcuration(input,dlist,r);
	}
	
	public void calcuration(String[][]s ,double[] d,int row){
		double temp;
		DecimalFormat df = new DecimalFormat("####0.00");
		for(int i=0;i<6;i++){
			if(s[row][i].equals("-")){
				s[row][i] = "0.0";
			}
			temp = Double.parseDouble(s[row][i])+d[i];
			s[row][i] = df.format(temp)+"";
			
		}
	}
	
	public double[] getDoubleValue(ArrayList<String> arr){
		double[] d = new double[6];
		String temp="";
		for(int i=0;i<arr.size();i++){
			if(arr.get(i).equals("")){
				temp = "0.0";
			}
			System.out.println("temp : "+arr.get(i));
			d[i] = Double.parseDouble(arr.get(i));
		}
		return d;
	}
	
	public int getIntValue(String str){
		int i;
		i = Integer.parseInt(str);
		return i;
	}
	/***************************************************/
	public HashMap<String, String> initHashmap(String type){
		HashMap<String,String> hsm = new HashMap<>();
			String scac="";
			for(int i=0;i<scacList.size();i++){
				scac = scacList.get(i);
				hsm.put(scac, i+"");
			}
		return hsm;
	}
	/***************************************************/
	public void initArr(String[][] list){
		for(int i=0;i<rowSize;i++){
			for(int j=0;j<COLUM_LENGTH;j++){
				list[i][j] = new String();
				list[i][j] = "-";
			}
		}
	}
	/***************************************************/
}
