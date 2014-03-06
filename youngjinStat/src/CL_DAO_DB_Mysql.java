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
	public ArrayList<String> getInOutList(){
		ArrayList<String> list = new ArrayList<>();
		list.add("OUT");
		list.add("IN");
		return list;
	}
	public ArrayList<String> getCodeList(){
		ArrayList<String> list = new ArrayList<>();
		list.add("ALL");
		try {
			connect();
			sql = "select * from stat_codelist";
			rs = stmt.executeQuery(sql);
			String result="";
			while(rs.next()){
				result = rs.getString("code");
				list.add(result);
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
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
	
	public ArrayList<InventoryStatBeans> getInventoryStat(String item,String area,String begin,String end){
		String sqlSupplied="";
		String sqlPurchase="";
		String sqlBeginSupplied="";
		String sqlBeginPurchase="";
		item = item.toUpperCase();
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
						sqlBeginPurchase = "select * from stat_purchase where date > date_format('"+beginDate+"','%y-%m-%d') and date < date_format('"+begin+"','%y-%m-%d')";
						sqlSupplied = "select * from stat_supplied where date > date_format('"+begin+"','%y-%m-%d') and date < date_format('"+end+"','%y-%m-%d')";
						sqlPurchase = "select * from stat_purchase where date > date_format('"+begin+"','%y-%m-%d') and date < date_format('"+end+"','%y-%m-%d')";
						System.out.println("sql beginPruchase : "+sqlBeginPurchase);
						System.out.println("sql purchase : "+sqlPurchase);
						System.out.println("sql supplied : "+sqlSupplied);
					}
				}
				else{// item all , area selected
					if(!begin.equals("") && !begin.equals("")){//input all date
						String beginDate = begin.substring(0, 4)+"0101";
						System.out.println("beginDate : "+beginDate);
						sqlBeginSupplied = "select * from stat_supplied where area='"+area+"' and date > date_format('"+beginDate+"','%y-%m-%d') and date < date_format('"+begin+"','%y-%m-%d')";
						sqlBeginPurchase = "select * from stat_purchase where area='"+area+"' and date > date_format('"+beginDate+"','%y-%m-%d') and date < date_format('"+begin+"','%y-%m-%d')";
						sqlSupplied = "select * from stat_supplied where area='"+area+"' and date > date_format('"+begin+"','%y-%m-%d') and date < date_format('"+end+"','%y-%m-%d')";
						sqlPurchase = "select * from stat_purchase where area='"+area+"' and date > date_format('"+begin+"','%y-%m-%d') and date < date_format('"+end+"','%y-%m-%d')";		
					}
					
				}
			}
			else{//selected ITEM
				if(area.equals("ALL")){//iteam selected , area all
					if(!begin.equals("") && !end.equals("")){//input all date
						String beginDate = begin.substring(0, 4)+"0101";
						System.out.println("beginDate : "+beginDate);
						sqlBeginSupplied = "select * from stat_supplied where item='"+item+"' and date > date_format('"+beginDate+"','%y-%m-%d') and date < date_format('"+begin+"','%y-%m-%d')";
						sqlBeginPurchase = "select * from stat_purchase where item='"+item+"' and date > date_format('"+beginDate+"','%y-%m-%d') and date < date_format('"+begin+"','%y-%m-%d')";
						sqlSupplied = "select * from stat_supplied where item='"+item+"' and date > date_format('"+begin+"','%y-%m-%d') and date < date_format('"+end+"','%y-%m-%d')";
						sqlPurchase = "select * from stat_purchase where item='"+item+"' and date > date_format('"+begin+"','%y-%m-%d') and date < date_format('"+end+"','%y-%m-%d')";
						System.out.println("sql beginPruchase : "+sqlBeginPurchase);
						System.out.println("sql purchase : "+sqlPurchase);
						System.out.println("sql supplied : "+sqlSupplied);
					}
				}
				else{// item selected , area selected
					if(!begin.equals("") && !begin.equals("")){//input all date
						String beginDate = begin.substring(0, 4)+"0101";
						System.out.println("beginDate : "+beginDate);
						sqlBeginSupplied = "select * from stat_supplied where item='"+item+"'and area='"+area+"' and date > date_format('"+beginDate+"','%y-%m-%d') and date < date_format('"+begin+"','%y-%m-%d')";
						sqlBeginPurchase = "select * from stat_purchase where item='"+item+"' and area='"+area+"' and date > date_format('"+beginDate+"','%y-%m-%d') and date < date_format('"+begin+"','%y-%m-%d')";
						sqlSupplied = "select * from stat_supplied where item='"+item+"'and area='"+area+"' and date > date_format('"+begin+"','%y-%m-%d') and date < date_format('"+end+"','%y-%m-%d')";
						sqlPurchase = "select * from stat_purchase where item='"+item+"'and area='"+area+"' and date > date_format('"+begin+"','%y-%m-%d') and date < date_format('"+end+"','%y-%m-%d')";		
					}
					
				}
			
				
			}
			
//			System.out.println(sql);
			rs = stmt.executeQuery(sqlBeginSupplied); //begin section up amounts //down quantity
			InventoryStatBeans isb=null;
			while(rs.next()){
				String itemName = rs.getString("item");
				System.out.println(itemName);
				int flag = checkItemExisting(itemName, list);
				if(flag!=-10){//existing
					int units = rs.getInt("units");
					int prise = rs.getInt("prise");
					int addAmounts = prise;
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
					int addAmounts = prise;
					int currentAmounts = addAmounts;
					isb.setItem(itemName);
					isb.setBeginningAmounts(currentAmounts);
					isb.setBeginningQuantity(-1*units);
					list.add(isb);
				}
			}
			System.out.println("????");
			System.out.println("sqlBeginPurchase : "+sqlBeginPurchase);
			rs = stmt.executeQuery(sqlBeginPurchase);//begin section down amounts // up quantity
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
			rs = stmt.executeQuery(sqlPurchase);//down amounts // up quantity
			while(rs.next()){
				String itemName = rs.getString("item");
				System.out.println(itemName);
				int flag = checkItemExisting(itemName, list);
				if(flag!=-10){//existing
					int units = rs.getInt("units");
					int prise = rs.getInt("prise");
					int addAmounts = prise;
					int currentUnits = list.get(flag).getIncreaseQuantity() + units;
					int currentAmounts = list.get(flag).getDecreaseQuantity()-addAmounts;
					list.get(flag).setIncreaseQuantity(currentUnits);
					list.get(flag).setDecreaseAmounts(currentAmounts);
				}
				else{//not existing
					isb = new InventoryStatBeans();
					System.out.println("add isb");
					int units = rs.getInt("units");
					int prise = rs.getInt("prise");
					int addAmounts = prise;
					int currentAmounts = addAmounts;
					isb.setItem(itemName);
					isb.setIncreaseQuantity(units);
					isb.setDecreaseAmounts(-1*currentAmounts);
					list.add(isb);
				}
			}
			rs = stmt.executeQuery(sqlSupplied); //decrease section up amounts //down quantity
			while(rs.next()){
				String itemName = rs.getString("item");
				System.out.println(itemName);
				int flag = checkItemExisting(itemName, list);
				if(flag!=-10){//existing
					int units = rs.getInt("units");
					int prise = rs.getInt("prise");
					int addAmounts = prise;
					int currentUnits = list.get(flag).getDecreaseQuantity() - units;
					int currentAmounts = list.get(flag).getIncreaseAmounts() + addAmounts;
					list.get(flag).setIncreaseAmounts(currentAmounts);
					list.get(flag).setDecreaseQuantity(currentUnits);
				}
				else{//not existing
					isb = new InventoryStatBeans();
					System.out.println("add isb");
					int units = rs.getInt("units");
					int prise = rs.getInt("prise");
					int addAmounts = prise;
					int currentAmounts = addAmounts;
					isb.setItem(itemName);
					isb.setIncreaseAmounts(currentAmounts);
					isb.setDecreaseQuantity(-1*units);
					list.add(isb);
				}
			}
			
			for(int i=0;i<list.size();i++){
				int endQuantity=0;
				int endAmounts=0;
				endQuantity += list.get(i).getBeginningQuantity()+list.get(i).getDecreaseQuantity()+list.get(i).getIncreaseQuantity();
				endAmounts += list.get(i).getBeginningAmounts()+list.get(i).getDecreaseAmounts()+list.get(i).getIncreaseAmounts();
				list.get(i).setEndingQuantity(endQuantity);
				list.get(i).setEndingAmounts(endAmounts);
				System.out.println("=============[DAO]=============");
				System.out.println("Beginning item Name : "+list.get(i).item);
				System.out.println("Beginning item Quantity : "+list.get(i).getBeginningQuantity());
				System.out.println("Beginning item Amounts : "+list.get(i).getBeginningAmounts());
				System.out.println("Increse Quantity : "+list.get(i).getIncreaseQuantity());
				System.out.println("Increse Amounts : "+list.get(i).getIncreaseAmounts());
				System.out.println("Decrease Quantity : "+list.get(i).getDecreaseQuantity());
				System.out.println("Decrease Amounts : "+list.get(i).getDecreaseAmounts());
				System.out.println("Ending Quantity : "+endQuantity);
				System.out.println("Ending Amounts : "+endAmounts);
				System.out.println();
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
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
	public ArrayList<String> getInventoryStatArea(){
		ArrayList<String> areaList = new ArrayList<String>();
		areaList.add("ALL");
		return areaList;
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
	
	public ArrayList<String> getScacListWork(){
		ArrayList<String> scacList = new ArrayList<String>();
		scacList.add("ALL");
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
	public ArrayList<GblBeans> getOutboundGblList(String scac,String inout,String code, String area, String begin,String end){
		ArrayList<GblBeans> list = new ArrayList<>();
		String gblno="";
			int whereflag=0;
			String sql="";
			String condition="";
			String table="";
//			System.out.println(scac);
			sql="select * from gbl";
			if(!scac.equals("ALL")){
				if(whereflag==0){
					condition+=" where scac='"+scac+"'";
					whereflag=1;
				}
				else{
					condition+=" and scac='"+scac+"'";
				}
			}
			if(!code.equals("ALL")){
				if(whereflag==0){
					condition+=" where code='"+code+"'";
					whereflag=1;
				}
				else{
					condition+=" and code='"+code+"'";
				}
			}
			if(!area.equals("ALL")){
				if(whereflag==0){
					condition+=" where area='"+area+"'";
				}
				else{
					condition+=" and area='"+area+"'";
				}
			}
			if(!begin.equals("") && !end.equals("")){
				if(whereflag==0){
					condition+=" where date > date_format('"+begin+"','%y-%m-%d') and date < date_format('"+end+"','%y-%m-%d')";
				}
				else{
					condition+=" and date > date_format('"+begin+"','%y-%m-%d') and date < date_format('"+end+"','%y-%m-%d')";
				}
			}
			sql+=condition;
			System.out.println(sql);
			try {
				connect();
				rs = stmt.executeQuery(sql);
				GblBeans gb = new GblBeans();
			
				while(rs.next()){
					list.add(gb);
					String tempCode=rs.getString("code");
					gblno = rs.getString("bl_no");
					gb.setPud(rs.getString("pud"));
					gb.setRdd(rs.getString("rdd"));
					gb.setScac(rs.getString("scac"));
					gb.setCode(tempCode);
					gb.setGblno(rs.getString("bl_no"));
					gb.setName(rs.getString("customer_name"));
					gb.setUsno(rs.getString("us_no"));
					String sql2 = "select * from weight_certificate_list where seq='"+rs.getString("seq")+"'";
	//				ResultSet rs2 = stmt.executeQuery(sql2);
					double density = 0;
	//				while(rs2.next()){//웨이서티피 참조
	//					int gross = Integer.parseInt(rs2.getString("all_gross"));
	//					int net = Integer.parseInt(rs2.getString("all_net"));
	//					int cuft = Integer.parseInt(rs2.getString("all_cuft"));
	//					gb.setPcs(rs2.getString("all_pcs"));
	//					gb.setGross(rs2.getString("all_gross"));
	//					gb.setNet(rs2.getString("all_net"));
	//					gb.setCuft(rs2.getString("all_cuft"));
	//					if(tempCode.equals("3")||tempCode.equals("4")||tempCode.equals("5")||tempCode.equals("t")||tempCode.equals("T")){
	//						java.text.DecimalFormat df = new java.text.DecimalFormat(",##0.00");
	//						density = net/cuft;
	//						String den = df.format(density);
	//						System.out.println("DEN : "+den);
	//						gb.setDensity(den);
	//					}
	//					else{
	//						java.text.DecimalFormat df = new java.text.DecimalFormat(",##0.00");
	//						density = gross/cuft;
	//						String den = df.format(density);
	//						System.out.println("DEN : "+den);
	//						gb.setDensity(den);
	//					}
	//					gb.setPcs(rs2.getString("all_pcs"));
	//				}
					list.add(gb);
				}//while end
			System.out.println("??");
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
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
