package mainpages;
import java.util.HashMap;


public class WorkVolumeStat1Beans {
	/******************[define parameter]************************/
	String[][] input = new String[23][20];
	String[][] cuftArr = new String[23][20];
	HashMap<String,String> hashMapColumn = new HashMap<String,String>();
	HashMap<String,String> hashMapRow = new HashMap<String,String>();
	String type="";
	/***************************************************/
	public WorkVolumeStat1Beans(){
		initArr(input);
		initArr(cuftArr);
		initHashmap(hashMapColumn,"column");
		initHashmap(hashMapRow,"");
	}
	public String[][] getCuftStr(){
		return cuftArr;
	}
	public String[][] getStr(){
		return input;
	}
	
	public void setWeightData(String area,String code,String jobValue,String weightValue){
		String column = hashMapColumn.get(area);
		int job;
		int weight;
		if(column!=null){
			job = Integer.parseInt(column)-1;
			weight = Integer.parseInt(column);
		}
		else{
			column="17";
			job = Integer.parseInt(column)-1;
			weight = Integer.parseInt(column);
		}
		String codeStr = hashMapRow.get(code);
		if(codeStr!=null){
			int row = Integer.parseInt(codeStr);
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
		}
		else{
			System.out.println("code null????");
		}
	}
	public void setNewDensity(String area,String code,String jobValue,String weightValue,String cuftValue){
		String column = hashMapColumn.get(area);
		int job;
		int weight;
		if(column!=null){
			job = Integer.parseInt(column)-1;
			weight = Integer.parseInt(column);
		}
		else{
			column="17";
			job = Integer.parseInt(column)-1;
			weight = Integer.parseInt(column);
		}
		String codeStr = hashMapRow.get(code);
		if(codeStr!=null){
			int row = Integer.parseInt(codeStr);
			int originJob;
			int originWeight;
			int originCuft=0;
			if(input[row][job].equals("-")){
				originJob = 0;
			}
			else{
				originJob = Integer.parseInt(input[row][job]);
			}
			if(input[row][weight].equals("-")){
				originWeight = 0;
				if(cuftArr[row][weight].equals("-")){
					originCuft=0;
				}
			}
			else{
				originWeight = Integer.parseInt(input[row][weight]);
				originCuft = Integer.parseInt(cuftArr[row][weight]);
			}
			originJob += Integer.parseInt(jobValue);
			originWeight+= Double.parseDouble(weightValue);
			originCuft +=Integer.parseInt(cuftValue);
			input[row][job] = originJob+"";
			input[row][weight] = originWeight+"";
			cuftArr[row][weight] = originCuft+"";
		}
		else{
			System.out.println("code null????");
		}
	}
	public void setDensityData(String area,String code,String jobValue,String densityValue){
		String column = hashMapColumn.get(area);
		System.out.println("AREA : "+area);
		System.out.println("COLUMN : "+column);
		if(column!= null){
		int job = Integer.parseInt(column)-1;
		int weight = Integer.parseInt(column);
		
		String codeStr = hashMapRow.get(code);
		
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
//		System.out.println("area : "+area +" code : "+code+" set - [job : "+jobValue +"][  weight :"+densityValue+" ]");
		}
		else{
			System.out.println("area Detection result null");
		}
	}
		
	/***************************************************/
	public void initHashmap(HashMap<String,String> hsm,String type){
		if(type.equals("column")){
			hsm.put("YS","1");
			hsm.put("OS","3");
			hsm.put("PYT","5");
			hsm.put("TDC","7");
			hsm.put("UJB","9");
			hsm.put("TG","11");
			hsm.put("BS","13");
			hsm.put("KS","15");
			hsm.put("OTHER","17");
		}
		else{
			hsm.put("in_3", "0");
			hsm.put("in_4", "0");
			hsm.put("in_5", "1");
			hsm.put("in_T", "2");
			hsm.put("in_others", "3");
			hsm.put("in_totalHHG", "4");
			hsm.put("in_7", "5");
			hsm.put("in_8", "6");
			hsm.put("in_J", "7");
			hsm.put("in_others", "8");
			hsm.put("in_totalUB", "9");
			hsm.put("in_totalIN", "10");
			/////////////////////////////////out_
			hsm.put("out_3", "11");
			hsm.put("out_4", "11");
			hsm.put("out_5", "12");
			hsm.put("out_T", "13");
			hsm.put("out_others", "14");
			hsm.put("out_totalHHG", "15");
			hsm.put("out_7", "16");
			hsm.put("out_8", "17");
			hsm.put("out_J", "18");
			hsm.put("out_others", "19");
			hsm.put("out_totalUB", "20");
			hsm.put("out_totalout", "21");
			hsm.put("total", "22");
		}
		
	}
	/***************************************************/
	public void initArr(String[][] list){
		for(int i=0;i<23;i++){
			for(int j=0;j<20;j++){
				list[i][j] = new String();
				list[i][j] = "-";
			}
		}
	}
	
}
