package mainpages;
import java.util.ArrayList;
import java.util.HashMap;


public class WorkVolumeStat2Beans {
	/******************[define parameter]************************/
	int COLUM_LENGTH = 22;
	CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
	ArrayList<String> scacList = dao.getScacList();
	int rowSize = scacList.size()+1;
	String[][] input = new String[rowSize][COLUM_LENGTH];
	HashMap<String,String> hashMapColumn = new HashMap<String,String>();
	HashMap<String,String> hashMapRow = new HashMap<String,String>();
	String type="";
	/***************************************************/
	public WorkVolumeStat2Beans() {
		initArr(input);
		hashMapColumn = initHashmap("column");
		hashMapRow = initHashmap("row");
	}
	
	public String[][] getStr(){
		return input;
	}
	
	public String setWeightData(String gblno,String code,String scac,String jobValue,String weightValue){
		String column = hashMapColumn.get(code);
		int job = Integer.parseInt(column)-1;
		int weight = Integer.parseInt(column);
		String scacStr = hashMapRow.get(scac);
		if(scacStr!=null){
			int row = Integer.parseInt(scacStr);
			int originJob;
			int originWeight;
			if(input[row][job].equals("-")){
				originJob = 0;
			}
			else{
				originJob = Integer.parseInt(input[row][job]);
			}
			if(input[row][weight].equals("-")){
				originWeight = 0;
			}
			else{
				originWeight = Integer.parseInt(input[row][weight]);
			}
			originJob += Integer.parseInt(jobValue);
			originWeight+= Double.parseDouble(weightValue);
			
			input[row][job] = originJob+"";
			input[row][weight] = originWeight+"";
	//		System.out.println("job : "+input[row][job]+" weight : "+input[row][weight]);
//			System.out.println("scac : "+scac +" code : "+code+" set - [job : "+jobValue +"][  weight :"+weightValue+" ]");
		}
		else{
			return "[ GBL NO : "+gblno+" ] [ SCAC : "+scac+" ]";
		}
		return "";
	}
	
	public void setDensityData(String scac,String code,String jobValue,String densityValue){
		String column = hashMapColumn.get(code);
		int job = Integer.parseInt(column)-1;
		int weight = Integer.parseInt(column);
		
		String codeStr = hashMapRow.get(scac);
		
		int row = Integer.parseInt(codeStr);
		int originJob;
		double originDensity;
		if(input[row][job].equals("-")){
			originJob = 0;
		}
		else{
			originJob = Integer.parseInt(input[row][job]);
		}
		if(input[row][weight].equals("-")){
			originDensity = 0.0;
		}
		else{
			originDensity = Double.parseDouble(input[row][weight]);
		}
		originJob += Integer.parseInt(jobValue);
		originDensity+= Double.parseDouble(densityValue);
		
		input[row][job] = originJob+"";
		input[row][weight] = originDensity+"";
		System.out.println("scac : "+scac +" code : "+code+" set - [job : "+jobValue +"][  weight :"+densityValue+" ]");
	}
		
	/***************************************************/
	public HashMap<String, String> initHashmap(String type){
		HashMap<String,String> hsm = new HashMap<>();
		if(type.equals("column")){
			hsm.put("3","1");
			hsm.put("4","1");
			hsm.put("5","3");
			hsm.put("T","5");
			hsm.put("7","11");
			hsm.put("8","13");
			hsm.put("J","15");
		}
		else{
			String scac="";
			for(int i=0;i<scacList.size();i++){
				scac = scacList.get(i);
				System.out.println(scac);
				hsm.put(scac, i+"");
			}
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
	
}
