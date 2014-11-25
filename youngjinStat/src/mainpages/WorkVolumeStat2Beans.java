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
	String[][] cuftInput = new String[rowSize][COLUM_LENGTH];
	HashMap<String,String> hashMapColumn = new HashMap<String,String>();
	HashMap<String,String> hashMapRow = new HashMap<String,String>();
	String type="";
	/***************************************************/
	public WorkVolumeStat2Beans() {
		initArr(input);
		initArr2(cuftInput);
		hashMapColumn = initHashmap("column");
		hashMapRow = initHashmap("row");
	}
	
	public String[][] getStr(){
		return input;
	}
	public void setCuftStr(String[][] cuftStr){
		cuftInput = cuftStr;
	}
	public String[][] getCuftStr(){
		return cuftInput;
	}
	
	public String setWeightData(String gblno,String code,String scac,String jobValue,String weightValue,String cuft){
		String column = hashMapColumn.get(code);
		int job = Integer.parseInt(column)-1;
		int weight = Integer.parseInt(column);
		String scacStr = hashMapRow.get(scac);
		if(scacStr!=null){// not scac and not cuft
			int row = Integer.parseInt(scacStr);
			int originJob;
			int originWeight;
			
			int tempCuft = Integer.parseInt(cuftInput[row][weight]);
			tempCuft+=Integer.parseInt(cuft);
			cuftInput[row][weight] = tempCuft+"";
			
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
	
	public void setDensityData(String scac,String code,String jobValue,String densityValue,String cuft){
		String column = hashMapColumn.get(code);
		int job = Integer.parseInt(column)-1;
		int weight = Integer.parseInt(column);
		String codeStr = hashMapRow.get(scac);
		int row = Integer.parseInt(codeStr);
		int originCuft;
		int tempCuft;
		originCuft = Integer.parseInt(cuftInput[row][weight]);
		tempCuft = Integer.parseInt(cuft);
		cuftInput[row][weight] = originCuft+tempCuft+"";
		
		System.out.println("scac : "+scac +" code : "+code+" set - [  cuft :"+tempCuft+" ]");
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
	public void initArr2(String[][] list){
		for(int i=0;i<rowSize;i++){
			for(int j=0;j<COLUM_LENGTH;j++){
				list[i][j] = new String();
				list[i][j] = "0";
			}
		}
	}
}
