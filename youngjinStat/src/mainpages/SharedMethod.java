package mainpages;

import java.text.DecimalFormat;

public class SharedMethod {
	//**********************[]****************************//
	String checkMinimumValue(String input,String type){
		String result="";
		if(input!=null && !input.equals("")){
			int val = Integer.parseInt(input);
			if(type.equals("HHG") || type.equals("hhg")){
				if(val<500){
					result="500";
				}
				else{
					result=input;
				}
			}
			else if(type.equals("UB") || type.equals("ub")){
				if(val<300){
					result="300";
				}
				else{
					result=input;
				}
			}
			return result;
		}
		
		else{
			System.out.println("NULL WEIGHT INPUT : "+input);
			return "0";
		}
	}
	//*************************************************************************//
	public String getRoundValue(String str,int flag){
		String result ="";
		int i;
		if(str!=null){
			if(flag==1){//int 
				if(str.equals("")||str==null){
					i=0;
				}
				else{
					i = Integer.parseInt(str);
				}
				result = new DecimalFormat("#,##0").format(i);
			}
			else if(flag==2) {
				Double d = Double.parseDouble(str);
		   		result = new DecimalFormat("#,##0.00").format(d);
			}
		}
   		return result;
   	}
	public String getWhereString(String input,int whereflag){
		String result="";
		if(whereflag==0){
			result=" where "+input;
			whereflag=1;
		}
		else if(whereflag==1){
			result=" and "+input;
			whereflag=1;
		}
		return result;
	}
	//*************************************************************************//
	public int whereflagChange(int flag){
		return 1;
	}
}
