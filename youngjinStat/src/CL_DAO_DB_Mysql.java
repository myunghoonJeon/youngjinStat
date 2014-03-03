/**
 * Copyright (c) 2012, RTOS Laboratory in Kyonggi University
 * All rights reserved.
 * ----------------------------------------------------------
 * ������������ : 12/09/26
 * �ӱ�ȣ�� ���� ���������Ǿ���ϴ�.
 * IT_DAO �������̽��� �޼ҵ� ����.
 * ----------------------------------------------------------
*/


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;



public class CL_DAO_DB_Mysql implements IT_DAO{
	private String jdbc_driver = "com.mysql.jdbc.Driver";
	private String jdbc_url = "jdbc:mysql://203.249.22.70:3306/youngjin";
	private Connection conn;
	private Statement stmt;
	private String sql="";
	private String sql2="";
	private userBean ub = null;
	private ResultSet rs = null;
	public void connect() throws Exception{
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(jdbc_url, "root", "root");
			stmt = conn.createStatement();
		} catch (Exception e) {
			throw new Exception("DB Error(connect) : "+e.toString());
		}
	}
	
	public void disconnect() throws Exception{
		try{
			stmt.close();
			conn.close();
		} catch (Exception e){
			throw new Exception("DB Error(disconnect) : "+e.toString());
		}
	}
	
	public boolean insertPurchaseInput(String item,String date,String units,String unitcost,String prise,String scac,String type){
		boolean bool=true;
		try {
			connect();
			sql = "insert into stat_purchase(item,date,units,unitcost,prise,scac,type) values('"+item+"','"+date+"','"+units+"','"+unitcost+"','"+prise+"','"+scac+"','"+type+"');";
			System.out.println(sql);
			stmt.executeUpdate(sql);
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			bool=false;
		}
		return bool;
	}
	
	public boolean insertSuppliedInput(String item,String date,String units,String area,String prise,String scac,String type){
		boolean bool=true;
		try {
			connect();
			sql = "insert into stat_supplied(item,date,units,area,prise,scac,type) values('"+item+"','"+date+"','"+units+"','"+area+"','"+prise+"','"+scac+"','"+type+"');";
			System.out.println(sql);
			stmt.executeUpdate(sql);
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			bool=false;
		}
		return bool;
	}
	public ArrayList<String> getInventorySuppliedOneItems(String item,String area,String begin,String end){
		
		ArrayList<String> list = new ArrayList<String>();
		try {
			connect();
			if(area.equals("ALL")){
				if(!begin.equals("") && !begin.equals("")){//input all date
					sql = "select * from stat_supplied where item='"+item+"' and date > date_format('"+begin+"','%y-%m-%d') and date < date_format('"+end+"','%y-%m-%d')";
					System.out.println("sql [correct input][all area]"+sql);
				}
				else{// no input date
					sql = "select * from stat_supplied where item='"+item+"'";
					System.out.println("[no input date] [all area]"+sql);
				}
			}
			else{
				if(!begin.equals("") && !begin.equals("")){//input all date
					sql = "select * from stat_supplied where item='"+item+"' and area='"+area+"' and date > date_format('"+begin+"','%y-%m-%d') and date < date_format('"+end+"','%y-%m-%d')";
					System.out.println("[correct input][part area]"+sql);
				}
				else{// no input date
					sql = "select * from stat_supplied where item='"+item+"' and area='"+area+"'";
					System.out.println("[no input date] [part area]"+sql);
				}
			}
//			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			String result="";
			while(rs.next()){
				result="";
				result += rs.getString("date")+"@";
				result += rs.getString("units");
				list.add(result);
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<String> getInventoryStat(String item,String area,String begin,String end){
		String sqlSupplied="";
		String sqlPurchase="";
		String sqlBeginSupplied="";
		String sqlBeginPurchase="";
		System.out.println(begin+"===>"+end);
		ArrayList<InventoryStatBeans> list = new ArrayList<InventoryStatBeans>();
		try {
			connect();
			if(item.equals("ALL")){
				if(area.equals("ALL")){//iteam all , area all
					if(!begin.equals("") && !end.equals("")){//input all date
						String beginDate = begin.substring(0, 4)+"0101";
						System.out.println("beginDate : "+beginDate);
						sqlBeginSupplied = "select * from stat_supplied where date > date_format('"+beginDate+"','%y-%m-%d') and date < date_format('"+begin+"','%y-%m-%d')";
						System.out.println(sqlBeginSupplied);
						sqlBeginPurchase = "select * from stat_purchase where date > date_format('"+beginDate+"','%y-%m-%d') and date < date_format('"+begin+"','%y-%m-%d')";
						sqlSupplied = "select * from stat_supplied where date > date_format('"+begin+"','%y-%m-%d') and date < date_format('"+end+"','%y-%m-%d')";
						sqlPurchase = "select * from stat_purchase where date > date_format('"+begin+"','%y-%m-%d') and date < date_format('"+end+"','%y-%m-%d')";
						System.out.println(sqlBeginPurchase);
					}
				}
				else{// item all , area selected
					if(!begin.equals("") && !begin.equals("")){//input all date
						String beginDate = begin.substring(0, 4)+"0101";
						System.out.println("beginDate : "+beginDate);
						sqlBeginSupplied = "select * from stat_supplied where area='"+area+"'date > date_format('"+beginDate+"','%y-%m-%d') and date < date_format('"+begin+"','%y-%m-%d')";
						System.out.println(sqlBeginSupplied);
						sqlBeginPurchase = "select * from stat_purchase where area='"+area+"'date > date_format('"+beginDate+"','%y-%m-%d') and date < date_format('"+begin+"','%y-%m-%d')";
						sqlSupplied = "select * from stat_supplied where area='"+area+"'date > date_format('"+begin+"','%y-%m-%d') and date < date_format('"+end+"','%y-%m-%d')";
						sqlPurchase = "select * from stat_purchase where area='"+area+"'date > date_format('"+begin+"','%y-%m-%d') and date < date_format('"+end+"','%y-%m-%d')";		
					}
					
				}
			}
			
//			System.out.println(sql);
			rs = stmt.executeQuery(sqlBeginSupplied); //up amounts //down quantity
			InventoryStatBeans isb=null;
			while(rs.next()){
				String itemName = rs.getString("item");
				System.out.println(itemName);
				int flag = checkItemExisting(itemName, list);
				if(flag!=-10){//existing
					int units = rs.getInt("units");
					int prise = rs.getInt("prise");
					int addAmounts = units*prise;
					int currentUnits = list.get(flag).getBeginningQuantity() - units;
					int currentAmounts = list.get(flag).getBeginningAmounts()+addAmounts;
					list.get(flag).setBeginningAmounts(currentAmounts);
					list.get(flag).setBeginningQuantity(currentUnits);
				}
				else{//not existing
					isb = new InventoryStatBeans();
					System.out.println("add isb");
					int units = rs.getInt("units");
					int prise = rs.getInt("prise");
					int addAmounts = units*prise;
					int currentAmounts = addAmounts;
					isb.setItem(itemName);
					isb.setBeginningAmounts(currentAmounts);
					isb.setBeginningQuantity(-1*units);
					list.add(isb);
				}
			}
			rs = stmt.executeQuery(sqlBeginPurchase);//down amounts // up quantity
					while(rs.next()){
						String itemName = rs.getString("item");
						System.out.println(itemName);
						int flag = checkItemExisting(itemName, list);
						if(flag!=-10){//existing
							int units = rs.getInt("units");
							int prise = rs.getInt("prise");
							int addAmounts = prise;
							int currentUnits = list.get(flag).getBeginningQuantity() + units;
							int currentAmounts = list.get(flag).getBeginningAmounts()-addAmounts;
							list.get(flag).setBeginningAmounts(currentAmounts);
							list.get(flag).setBeginningQuantity(currentUnits);
						}
						else{//not existing
							isb = new InventoryStatBeans();
							System.out.println("add isb");
							int units = rs.getInt("units");
							int prise = rs.getInt("prise");
							int addAmounts = prise;
							int currentAmounts = addAmounts;
							isb.setItem(itemName);
							isb.setBeginningAmounts(-1*currentAmounts);
							isb.setBeginningQuantity(units);
							list.add(isb);
						}
					}		
			for(int i=0;i<list.size();i++){
				System.out.println("item Name : "+list.get(i).item);
				System.out.println("item Quantity : "+list.get(i).beginningQuantity);
				System.out.println("item Amounts : "+list.get(i).beginningAmounts);
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public int checkItemExisting(String item,ArrayList<InventoryStatBeans> isb){
		int index = -10;
		for(int i=0;i<isb.size();i++){
			if(isb.get(i).getItem().equals(item)){
				index = i;
			}
		}
		return index;
	}
	
	public ArrayList<String> getInventoryStatItemList(){

		ArrayList<String> list = new ArrayList<String>();
		list.add("ALL");
		try {
			connect();
			sql = "select * from stat_items";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				list.add(rs.getString("name"));
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	
	}
	public ArrayList<String> getInventoryFilteringItemsList(){
		ArrayList<String> list = new ArrayList<String>();
		list.add("ALL");
		list.add("COMPLIMENTARY");
		list.add("COMPENSATION");
		try {
			connect();
			sql = "select * from stat_items";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				list.add(rs.getString("name"));
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<String> getAreaList2(){
		ArrayList<String> areaList = new ArrayList<String>();
		areaList.add("ALL");
		try {
			connect();
			sql = "select * from stat_area";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				areaList.add(rs.getString("name"));
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return areaList;
	}
	
	public ArrayList<String> getInventorySuppliedAllItem(String area,String begin,String end){
		ArrayList<String> result=new ArrayList<>();
		String in = "";
		try {
			connect();
			if(area.equals("ALL")){
				if(begin.equals("") && end.equals("")){
					sql = "select * from stat_supplied";
				}
				else{
					sql = "select * from stat_supplied where date > date_format('"+begin+"','%y-%m-%d') and date < date_format('"+end+"','%y-%m-%d')";
				}
			}
			else{
				if(begin.equals("") && end.equals("")){
					sql = "select * from stat_supplied where area='"+area+"'";
				}
				else{
					sql = "select * from stat_supplied where area='"+area+"'and date > date_format('"+begin+"','%y-%m-%d') and date < date_format('"+end+"','%y-%m-%d')";
				}
				
			}
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				in = rs.getString("item")+"@"+rs.getString("units");
				result.add(in);
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<String> getAreaList(){
		ArrayList<String> areaList = new ArrayList<String>();
		try {
			connect();
			sql = "select * from stat_area";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				areaList.add(rs.getString("name"));
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return areaList;
	}
	
	public ArrayList<String> getScacList(){
		ArrayList<String> scacList = new ArrayList<String>();
		try {
			connect();
			sql = "select * from stat_scac";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				scacList.add(rs.getString("name"));
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scacList;
	}
	public userBean userAccessValidateCheck(String id,String pw){
		ub = new userBean();
		int flag=0;
		try {
			connect();
			sql = "select * from stat_user where id='"+id+"' and pw = '"+pw+"'";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				flag+=1;
				System.out.println("exited data");
				ub.setId(rs.getString("id"));
				ub.setPw(rs.getString("pw"));
				ub.setLevel(rs.getInt("level"));
			}
			disconnect();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(flag!= 0){
			return ub;
		}
		else{
			return null;
		}
	}
	
//	public Boolean inventoryInput(){
//		
//	}
	
	/**
	public void writeMessage(String board,CL_DataBean data) throws Exception{
		String sql,columnString,valueString;
		columnString = "sn,content,reportingdate,remotedeviceid,remotehost";
		String encodeSn=null,encodeContent=null;
				
//		encodeSn = new String(data.getStudentNumber().getBytes());
//		encodeContent = new String(data.getContent().getBytes());
//		try {
//			encodeSn = URLEncoder.encode(encodeSn,"UTF-8");
//			encodeContent = URLEncoder.encode(encodeContent,"UTF-8");
//		} catch (UnsupportedEncodingException e1) {}
		
		try {
			encodeSn = URLEncoder.encode(data.getStudentNumber(),"8859_1");
			encodeContent = URLEncoder.encode(data.getContent(),"8859_1");
		} catch (UnsupportedEncodingException e1) {}
		
		valueString = "'"+encodeSn+"','"+encodeContent+"','"+data.getDate()+"','"+data.getRemoteDeviceID()+"','"+data.getRemotehost()+"'";
		board = board.toUpperCase();
		sql = "insert into "+board+"("+columnString+") values("+valueString+")";
		columnString = columnString.toUpperCase();
		try {
			connect();
			stmt.executeUpdate(sql);
			disconnect();
		} catch (Exception e) {
			throw new Exception("DB Error(writeMessage) : "+e.toString());
		}
	}
	public ArrayList<CL_DataBean> readMessage(String board,int lowNumber, int highNumber) throws Exception{
		String sql,columnString,orderString;
		columnString = "no,sn,content,reportingdate,remotedeviceid";
		orderString = " order by no desc";
		sql = "select "+columnString+" from "+ board +" where no>="+lowNumber+" and no<="+highNumber;
		sql += orderString; // ���� 
		sql = sql.toUpperCase();
		ArrayList<CL_DataBean> list = new ArrayList<CL_DataBean>();
		try {
			connect();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				CL_DataBean data = new CL_DataBean();
				data.setData(rs.getInt("NO"), rs.getString("SN"), rs.getString("CONTENT"),rs.getString("REPORTINGDATE"),rs.getString("REMOTEDEVICEID"));
				list.add(data);
			}
			rs.close();
			disconnect();
		} catch (Exception e) {
			throw new Exception("DB Error(readMessage) : "+e.toString());
		}
		return list;
	}
	public int maxMessageNumber(String board) throws Exception{
		String sql,columnString;
		columnString = "max(no)";
		sql = "select "+columnString+" from "+ board;
		sql = sql.toUpperCase();
		
		int max = 0;
		try {
			connect();
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			max = rs.getInt("MAX(NO)");
			rs.close();
			disconnect();
		} catch (Exception e) {
			throw new Exception("DB Error(maxMessageNumber) : "+e.toString());
		}
		return max;
	}
	**/
}
