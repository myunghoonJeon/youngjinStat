package mainpages;
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
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import AllScacTotalInvoiceCollectionStatusGBL.AllScacTotalInvoiceCollectionStatusGblBeans;



public class CL_DAO_DB_Mysql implements IT_DAO{
	/*************************************************************************/
	ErrorPopup ep;
	/*-------------------system parameter---------------------------------*/
	private String jdbc_driver = "com.mysql.jdbc.Driver";
	private String jdbc_url = "jdbc:mysql://119.192.215.138/youngjin";
	private Connection conn;
	private Statement stmt;
	private String sql="";
	private String sql2="";
	private userBean ub = null;
	private ResultSet rs = null; 
	/*-------------------normal parameter---------------------------------*/
	/*-------------------Shared Method---------------------------------*/
	SharedMethod sm = new SharedMethod();
	////////////////////////////////////////////////////////////////
	int ROW_LENGTH;
	int COLUMN_LENGTH;
	public CL_DAO_DB_Mysql() {
		ROW_LENGTH = getScacList().size()+1;
		COLUMN_LENGTH=6;
	}
	/*--------------------------------------------------------------------*/
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
			 
			stmt.executeUpdate(sql);
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			bool=false;
		}
		return bool;
	}
	
	public ArrayList<String> getDateList(){
		ArrayList<String> list = new ArrayList<>();
		list.add("INVOICED");
		list.add("COLLECTED");
		return list;
	}
	public ArrayList<String> getStatusList(){
		ArrayList<String> list = new ArrayList<>();
		list.add("ALL");
		list.add("UNCOLLECTED");
		list.add("COLLECTED");
		list.add("SHORTPAID");
		return list;
	}
	public ArrayList<String> getAllInOutList(){
		ArrayList<String> list = new ArrayList<>();
		list.add("ALL");
		list.add("OUT");
		list.add("IN");
		return list;
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
	public ArrayList<String> getWorkStat1TypeList(){
		ArrayList<String> list = new ArrayList<>();
		list.add("WEIGHT");
//		list.add("PERCENTAGE");
		list.add("DENSITY");
		return list;
	}
	public ArrayList<String> getHhgUbList(){
		ArrayList<String> list = new ArrayList<>();
		list.add("ALL");
		list.add("HHG");
		list.add("UB");
		return list;
	}
	public boolean insertSuppliedInput(String item,String date,String units,String area,String prise,String scac,String type){
		boolean bool=true;
		try {
			connect();
			sql = "insert into stat_supplied(item,date,units,area,prise,scac,type) values('"+item+"','"+date+"','"+units+"','"+area+"','"+prise+"','"+scac+"','"+type+"');";
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
					sql = "select * from stat_supplied where item='"+item+"' and date >= date_format('"+begin+"','%y-%m-%d') and date <= date_format('"+end+"','%y-%m-%d')";
					System.out.println("sql [correct input][all area]"+sql);
				}
				else{// no input date
					sql = "select * from stat_supplied where item='"+item+"'";
					System.out.println("[no input date] [all area]"+sql);
				}
			}
			else{
				if(!begin.equals("") && !begin.equals("")){//input all date
					sql = "select * from stat_supplied where item='"+item+"' and area='"+area+"' and date >= date_format('"+begin+"','%y-%m-%d') and date <= date_format('"+end+"','%y-%m-%d')";
					System.out.println("[correct input][part area]"+sql);
				}
				else{// no input date
					sql = "select * from stat_supplied where item='"+item+"' and area='"+area+"'";
					System.out.println("[no input date] [part area]"+sql);
				}
			}
//			 
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
//		System.out.println(begin+"===>"+end);
		ArrayList<InventoryStatBeans> list = new ArrayList<InventoryStatBeans>();
		try {
			connect();
			if(item.equals("ALL")){
				if(area.equals("ALL")){//iteam all , area all
					if(!begin.equals("") && !end.equals("")){//input all date
						String beginDate = begin.substring(0, 4)+"0101";
						System.out.println("beginDate : "+beginDate);
						sqlBeginSupplied = "select * from stat_supplied where date >= date_format('"+beginDate+"','%y-%m-%d') and date <= date_format('"+begin+"','%y-%m-%d')";
						sqlBeginPurchase = "select * from stat_purchase where date >= date_format('"+beginDate+"','%y-%m-%d') and date <= date_format('"+begin+"','%y-%m-%d')";
						sqlSupplied = "select * from stat_supplied where date >= date_format('"+begin+"','%y-%m-%d') and date <= date_format('"+end+"','%y-%m-%d')";
						sqlPurchase = "select * from stat_purchase where date >= date_format('"+begin+"','%y-%m-%d') and date <= date_format('"+end+"','%y-%m-%d')";
						System.out.println("sql beginPruchase : "+sqlBeginPurchase);
						System.out.println("sql purchase : "+sqlPurchase);
						System.out.println("sql supplied : "+sqlSupplied);
					}
				}
				else{// item all , area selected
					if(!begin.equals("") && !begin.equals("")){//input all date
						String beginDate = begin.substring(0, 4)+"0101";
						System.out.println("beginDate : "+beginDate);
						sqlBeginSupplied = "select * from stat_supplied where area='"+area+"' and date >= date_format('"+beginDate+"','%y-%m-%d') and date <= date_format('"+begin+"','%y-%m-%d')";
						sqlBeginPurchase = "select * from stat_purchase where area='"+area+"' and date >= date_format('"+beginDate+"','%y-%m-%d') and date <= date_format('"+begin+"','%y-%m-%d')";
						sqlSupplied = "select * from stat_supplied where area='"+area+"' and date >= date_format('"+begin+"','%y-%m-%d') and date <= date_format('"+end+"','%y-%m-%d')";
						sqlPurchase = "select * from stat_purchase where area='"+area+"' and date >= date_format('"+begin+"','%y-%m-%d') and date <= date_format('"+end+"','%y-%m-%d')";		
					}
					
				}
			}
			else{//selected ITEM
				if(area.equals("ALL")){//iteam selected , area all
					if(!begin.equals("") && !end.equals("")){//input all date
						String beginDate = begin.substring(0, 4)+"0101";
						System.out.println("beginDate : "+beginDate);
						sqlBeginSupplied = "select * from stat_supplied where item='"+item+"' and date >= date_format('"+beginDate+"','%y-%m-%d') and date <= date_format('"+begin+"','%y-%m-%d')";
						sqlBeginPurchase = "select * from stat_purchase where item='"+item+"' and date >= date_format('"+beginDate+"','%y-%m-%d') and date <= date_format('"+begin+"','%y-%m-%d')";
						sqlSupplied = "select * from stat_supplied where item='"+item+"' and date >= date_format('"+begin+"','%y-%m-%d') and date <= date_format('"+end+"','%y-%m-%d')";
						sqlPurchase = "select * from stat_purchase where item='"+item+"' and date >= date_format('"+begin+"','%y-%m-%d') and date <= date_format('"+end+"','%y-%m-%d')";
						System.out.println("sql beginPruchase : "+sqlBeginPurchase);
						System.out.println("sql purchase : "+sqlPurchase);
						System.out.println("sql supplied : "+sqlSupplied);
					}
				}
				else{// item selected , area selected
					if(!begin.equals("") && !begin.equals("")){//input all date
						String beginDate = begin.substring(0, 4)+"0101";
						System.out.println("beginDate : "+beginDate);
						sqlBeginSupplied = "select * from stat_supplied where item='"+item+"'and area='"+area+"' and date >= date_format('"+beginDate+"','%y-%m-%d') and date <= date_format('"+begin+"','%y-%m-%d')";
						sqlBeginPurchase = "select * from stat_purchase where item='"+item+"' and area='"+area+"' and date >= date_format('"+beginDate+"','%y-%m-%d') and date <= date_format('"+begin+"','%y-%m-%d')";
						sqlSupplied = "select * from stat_supplied where item='"+item+"'and area='"+area+"' and date >= date_format('"+begin+"','%y-%m-%d') and date <= date_format('"+end+"','%y-%m-%d')";
						sqlPurchase = "select * from stat_purchase where item='"+item+"'and area='"+area+"' and date >= date_format('"+begin+"','%y-%m-%d') and date <= date_format('"+end+"','%y-%m-%d')";		
					}
					
				}
			
				
			}
			
//			
			System.out.println(sqlBeginSupplied+"````````````````````````````````````");
			rs = stmt.executeQuery(sqlBeginSupplied); //begin section up amounts //down quantity
			InventoryStatBeans isb=null;
			while(rs.next()){
				String itemName = rs.getString("item");
//				System.out.println(itemName);
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
//					System.out.println("add isb");
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
//				System.out.println(itemName);
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
//					System.out.println("add isb");
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
//				System.out.println(itemName);
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
//					System.out.println("add isb");
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
					sql = "select * from stat_supplied where date >= date_format('"+begin+"','%y-%m-%d') and date <= date_format('"+end+"','%y-%m-%d')";
				}
			}
			else{
				if(begin.equals("") && end.equals("")){
					sql = "select * from stat_supplied where area='"+area+"'";
				}
				else{
					sql = "select * from stat_supplied where area='"+area+"'and date >= date_format('"+begin+"','%y-%m-%d') and date <= date_format('"+end+"','%y-%m-%d')";
				}
				
			}
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
			sql = "select tsp from invoice_rate group by tsp";
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				scacList.add(rs.getString("tsp"));
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
			sql = "select tsp from invoice_rate group by tsp";
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				scacList.add(rs.getString("tsp"));
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scacList;
	}
	
	public ArrayList<GblBeans> getWorkVolumeFilteringOutboundGblList(String scac,String inout,String code, String area, String begin,String end){
		ArrayList<GblBeans> list = new ArrayList<>();
		GblBeans gb;
		String gblno="";
			int whereflag=0;
			String sql="";
			String joinstr="";
			String condition="";
//			System.out.println(scac);
			sql="select * from gbl ";
			if(!scac.equals("ALL")){
				if(whereflag==0){
					condition+=" where gbl.scac='"+scac+"'";
					whereflag=1;
				}
				else{
					condition+=" and gbl.scac='"+scac+"'";
				}
			}
			if(!code.equals("ALL")){
				if(whereflag==0){
					condition+=" where gbl.code='"+code+"'";
					whereflag=1;
				}
				else{
					condition+=" and gbl.code='"+code+"'";
				}
			}
			if(!area.equals("ALL")){
				if(whereflag==0){
					condition+=" where gbl.area='"+area+"'";
				}
				else{
					condition+=" and gbl.area='"+area+"'";
				}
			}
			if(!begin.equals("") && !end.equals("")){
				if(whereflag==0){
					condition+=" where date(gbl.pud) >= date_format('"+begin+"','%y%m%d') and date(gbl.pud) <= date_format('"+end+"','%y%m%d')";
				}
				else{
					condition+=" and date(gbl.pud) >= date_format('"+begin+"','%y%m%d') and date(gbl.pud) <= date_format('"+end+"','%y%m%d')";
				}
			}
			sql+=condition;
			System.out.println("work filtering outbound SQL : "+sql);
			try {
				connect();
				rs = stmt.executeQuery(sql);
				int count=0;
				while(rs.next()){
					count++;
					gb = new GblBeans();
					String tempCode=rs.getString("code");
					gblno = rs.getString("no");
					Double density=0.0;
					gb.setPud(rs.getString("pud"));
					gb.setRdd(rs.getString("rdd"));
					gb.setScac(rs.getString("scac"));
					gb.setSeq(rs.getString("seq"));
					gb.setArea(rs.getString("area"));
					gb.setCode(tempCode);
					gb.setGblno(gblno);
					gb.setName(rs.getString("customer_name"));
					gb.setUsno(rs.getString("us_no"));
					gb.setGross(rs.getString("grossWeight"));
					gb.setNet(rs.getString("netWeight"));
					gb.setPcs(rs.getString("totalPcs"));
					gb.setCuft(getZeroValue(rs.getString("cuft")));
					if(gb.getCode().equals("3")||gb.getCode().equals("4")||gb.getCode().equals("5")||gb.getCode().equals("T")||gb.getCode().equals("t")){//HHG
							String tempValue = sm.checkMinimumValue(gb.getNet(), "HHG");
							gb.setNet(tempValue);
							if(gb.getCuft().equals("0")){
								System.out.println("CUFG IS ZERO");
								density=0.0;
							}
							else{
								density = Double.parseDouble(gb.getNet())/Double.parseDouble(gb.getCuft());
							}
							DecimalFormat df = new DecimalFormat("######0.00");
							gb.setDensity(df.format(density));
//						System.out.println(gb1.getDensity());
					}
					else if(gb.getCode().equals("7")||gb.getCode().equals("8")||gb.getCode().equals("j")||gb.getCode().equals("J")){//UB
						String tempValue = sm.checkMinimumValue(gb.getGross(), "UB");
						gb.setGross(tempValue);
						if(gb.getCuft().equals("0")){
							System.out.println("CUFT IS ZERO");
							density=0.0;
						}
						else{
							density = Double.parseDouble(gb.getGross())/Double.parseDouble(gb.getCuft());
						}
						DecimalFormat df = new DecimalFormat("######0.00");
						gb.setDensity(df.format(density));
//						System.out.println(gb1.getDensity());
					}
					list.add(gb);
				}//while end
				System.out.println("size : "+count);
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		for(int i=0;i<list.size();i++){
//			String seq = list.get(i).getSeq();
//			GblBeans gb1 = list.get(i);
//			try{
//				connect();
//				String sumGrossSql ="select sum(gross),sum(net),sum(cuft),count(gbl_seq) from weight_certificate where gbl_seq='"+seq+"'";
//				rs = stmt.executeQuery(sumGrossSql);
//				while(rs.next()){
//					String gross = rs.getString("sum(gross)");
//					String net = rs.getString("sum(net)");
//					String cuft = rs.getString("sum(cuft)");
//					String pcs = rs.getString("count(gbl_seq)");
//					String gblNo = list.get(i).getGblno();
//					
//					list.get(i).setGross(gross);
//					list.get(i).setCuft(cuft);
//					list.get(i).setNet(net);
//					list.get(i).setPcs(pcs);
//					double density = 0;
//					if(gblNo==null || gross == null || net== null || cuft==null || pcs == null){
//						System.out.println(" DETECTED NULL");
//						System.out.println("GBL no : "+gblNo);
//					}
////					System.out.println(list.get(i).getGblno() +" - "+gross+" - "+net+" - "+cuft+" - "+pcs);
//					else{
//						if(gb1.getCode().equals("3")||gb1.getCode().equals("4")||gb1.getCode().equals("5")||gb1.getCode().equals("T")||gb1.getCode().equals("t")){//HHG
//							String tempValue = sm.checkMinimumValue(gb1.getNet(), "HHG");
//							gb1.setNet(tempValue);
//							density = Double.parseDouble(gb1.getNet())/Double.parseDouble(gb1.getCuft());
//							DecimalFormat df = new DecimalFormat("######0.00");
//							list.get(i).setDensity(df.format(density));
//	//						System.out.println(gb1.getDensity());
//						}
//						else if(gb1.getCode().equals("7")||gb1.getCode().equals("8")||gb1.getCode().equals("j")||gb1.getCode().equals("J")){//UB
//							String tempValue = sm.checkMinimumValue(gb1.getGross(), "UB");
//							gb1.setNet(tempValue);
//							density = Double.parseDouble(gb1.getGross())/Double.parseDouble(gb1.getCuft());
//							DecimalFormat df = new DecimalFormat("######0.00");
//							list.get(i).setDensity(df.format(density));
//	//						System.out.println(gb1.getDensity());
//						}
//					}
//				}
//			}catch(Exception e){
//				
//			}
//		}
		return list;
	}
	public String getZeroValue(String input){
		if(input!=null){
			return input;
		}
		else{
			return "0";
		}
	}
	public ArrayList<GblBeans> getInboundGblList(String scac,String inout,String code, String area, String pudBegin,String pudEnd,String rddBegin,String rddEnd,String onhandBegin,String onhandEnd){
		ArrayList<GblBeans> list = new ArrayList<>();
		String gblno="";
			int whereflag=0;
			String sql="";
			String joinstr="";
			String condition="";
//			System.out.println(scac);
			sql="select * from gbl_ib ";
			if(!scac.equals("ALL")){
				if(whereflag==0){
					condition+=" where tsp='"+scac+"'";
					whereflag=1;
				}
				else{
					condition+=" and tsp='"+scac+"'";
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
					whereflag=1;
				}
				else{
					condition+=" and area='"+area+"'";
				}
			}
			if(!pudBegin.equals("") && !pudEnd.equals("")){
				if(whereflag==0){
					condition+=" where date(sitOut) >= date_format('"+pudBegin+"','%y-%m-%d')  and date(sitOut) <= date_format('"+pudEnd+"','%y-%m-%d')";
					whereflag=1;
				}
				else{
					condition+=" and date(sitOut) >= date_format('"+pudBegin+"','%y-%m-%d')  and date(sitOut) <= date_format('"+pudEnd+"','%y-%m-%d')";
				}
			}
			if(!rddBegin.equals("") && !rddEnd.equals("")){
				if(whereflag==0){
					condition+=" where date(rdd) >= date_format('"+rddBegin+"','%y-%m-%d')  and date(rdd) <= date_format('"+rddEnd+"','%y-%m-%d')";
					whereflag=1;
				}
				else{
					condition+=" and date(rdd) >= date_format('"+rddBegin+"','%y-%m-%d')  and date(rdd) <= date_format('"+rddEnd+"','%y-%m-%d')";
				}
			}
			if(!onhandBegin.equals("") && !onhandEnd.equals("")){
				if(whereflag==0){
					condition+=" where date(onHandDate) >= date_format('"+onhandBegin+"','%y-%m-%d')  and date(onHandDate) <= date_format('"+onhandEnd+"','%y-%m-%d')";
					whereflag=1;
				}
				else{
					condition+=" and date(onHandDate) >= date_format('"+onhandBegin+"','%y-%m-%d')  and date(onHandDate) <= date_format('"+onhandEnd+"','%y-%m-%d')";
				}
			}
			sql+=condition;
			System.out.println("work filtering inbound : "+sql);
			try {
				connect();
				rs = stmt.executeQuery(sql);
				double density=0.0;
				while(rs.next()){
					GblBeans gb = new GblBeans();
					String tempCode=rs.getString("code");
					gblno = rs.getString("gblNo");
					gb.setName(rs.getString("shipperName"));
					gb.setSitIn(rs.getString("sitIn"));
					gb.setPud(rs.getString("pud"));//
					gb.setRdd(rs.getString("rdd"));//
					gb.setArea(rs.getString("area"));//
					gb.setSitOut(rs.getString("sitOut"));
					gb.setSitNo(rs.getString("sitNo"));
					gb.setOnhand(rs.getString("onHandDate"));
					gb.setScac(rs.getString("tsp"));
					gb.setCode(tempCode);
					gb.setGblno(gblno);
					gb.setSeq(rs.getString("seq"));
//					list.add(gb);
					gb.setPcs(rs.getString("totalPcs"));
					gb.setCuft(getZeroValue(rs.getString("cuft")));
					gb.setGross(rs.getString("grossWeight"));
					gb.setNet(rs.getString("netWeight"));
					if(gb.getCode().equals("3")||gb.getCode().equals("4")||gb.getCode().equals("5")||gb.getCode().equals("T")||gb.getCode().equals("t")){//HHG
							String tempValue = sm.checkMinimumValue(gb.getNet(), "HHG");
							gb.setNet(tempValue);
							if(gb.getCuft().equals("0")||gb.getNet().equals("0")){
								System.out.println("net : "+gb.getNet());
								System.out.println("cuft : "+gb.getCuft());
								System.out.println("GBLNO : "+gblno);
								density=0.0;
							}
							else{
//								System.out.println("NET : "+gb.getNet()+" CUFT : "+gb.getCuft());
								density = Double.parseDouble(gb.getNet())/Double.parseDouble(gb.getCuft());
							}
							DecimalFormat df = new DecimalFormat("######0.00");
							gb.setDensity(df.format(density));
//						System.out.println(gb1.getDensity());
					}
					else if(gb.getCode().equals("7")||gb.getCode().equals("8")||gb.getCode().equals("j")||gb.getCode().equals("J")){//UB
						String tempValue = sm.checkMinimumValue(gb.getGross(), "UB");
						gb.setGross(tempValue);
						if(gb.getCuft().equals("0")||gb.getGross().equals("0")){
							System.out.println("gross : "+gb.getGross());
							System.out.println("cuft : "+gb.getCuft());
							System.out.println("GBLNO : "+gblno);
							density=0.0;
						}
						else{
							density = Double.parseDouble(gb.getGross())/Double.parseDouble(gb.getCuft());
							System.out.println("GROSS : "+gb.getGross()+" CUFT : "+gb.getCuft()+" DENSITY : "+density);
						}
						DecimalFormat df = new DecimalFormat("######0.00");
						gb.setDensity(df.format(density));
//						System.out.println(gb1.getDensity());
					}
					list.add(gb);
				}//while end
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
			System.out.println("SQL : "+sql);
			
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				if(rs.getString("id")!=null){
					System.out.println("[EXISTED USER INFORMATION]");
					flag+=1;
					ub.setId(rs.getString("id"));
					ub.setPw(rs.getString("pw"));
					ub.setLevel(rs.getInt("level"));
				}
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
	public String getFlagCheckMassage(int flag,String filter){
		String result="";
		if(flag==0){
			
			
		}
		else{
			
		}
		return result;
	}
	public ArrayList<AllScacTotalInvoiceCollectionStatusGblBeans> getOutboundEachScacUncollectedGbl(String scac,String code,String begin,String end){
		ArrayList<AllScacTotalInvoiceCollectionStatusGblBeans> list = new ArrayList<>();
		AllScacTotalInvoiceCollectionStatusGblBeans bean = new AllScacTotalInvoiceCollectionStatusGblBeans();
		String sql="select	invoice_gbl.gbl_no as gblNo, round(invoice_gbl.amount,2) as amount, invoice_list.start_date as start, invoice_list.end_date as end,"+
				   "invoice_gbl.name as name,gbl.code as code,gbl.scac as scac, round(invoice_gbl_collection.difference,2) as difference,"+
				   "invoice_gbl_collection.state as state, invoice_gbl_collection_flow.state as collectionState, round(invoice_gbl_collection_flow.amount,2) as collectionAmount,"+
				   "invoice_list.invoice_date as invoiceDate, DATEDIFF(DATE_FORMAT(SYSDATE(), '%Y%m%d'), DATE_FORMAT(CONVERT(invoice_list.invoice_date, DATE), '%Y%m%d')) as datediff"
				   + " from	invoice_list left join invoice_gbl on invoice_list.seq = invoice_gbl.invoice_list_seq"
				   + "	left join gbl on invoice_gbl.gbl_seq = gbl.seq"
				   + "	left join invoice_gbl_collection on invoice_gbl_collection.invoice_gbl_seq = invoice_gbl.seq"
				   + "	left join invoice_gbl_collection_flow on invoice_gbl_collection.seq = invoice_gbl_collection_flow.invoice_gbl_collection_seq"
				   + " where invoice_list.process = 'outbound'";
		if(!scac.equals("ALL")){
			sql+=" and gbl.scac='"+scac+"'";
		}
		if(!code.equals("ALL")){
			sql+=" and gbl.code='"+code+"'";
		}
		if(!begin.equals("") && !end.equals("")){
			sql+=" and date(invoice_list.invoice_date) >= date('"+begin+"') and date(invoice_list.invoice_date) <= date('"+end+"')";
		}
		System.out.println("outbound SQL1 : "+sql);
		try {
			System.out.println("[[SQL QUERRY EXECUTE]]");
			connect();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				String scac1 = rs.getString("scac");
				String gblNo = rs.getString("gblNo");
				String amount = rs.getString("amount");
				String name = rs.getString("name");
				String code1 = rs.getString("code");
				String difference = rs.getString("difference");
				String state1 = rs.getString("state");
				String collectionState = rs.getString("collectionState");
				String collectionAmount = rs.getString("collectionAmount");
				String dateDiff = rs.getString("datediff");
				String invoiceDate = rs.getString("invoiceDate");
				bean = new AllScacTotalInvoiceCollectionStatusGblBeans();
				double tempDiff = getDoubleValue(difference);
//				if(tempDiff <0){
//					System.out.println("@@@@HAPPEN?");
					tempDiff *= -1;
//				}
				bean.setGblNo(gblNo);
				bean.setScac(scac1);
				bean.setAmount(amount);
				bean.setName(name);
				bean.setInvoiceDate(invoiceDate);
				bean.setCode(code1);
				bean.setDifference(tempDiff+"");
				bean.setState(state1);
				bean.setDateDiff(dateDiff);
				bean.setProcess("outbound");
				if(collectionState==null || collectionState.equals("")){
//					System.out.println(gblNo+" : STATE NULL ADD uncollectedAmount : "+amount);
					bean.setDifference("0.0");
					bean.setUncollectedAmount(amount);
				}
				else if(collectionState.equals("ACCEPT")){
//					System.out.println(gblNo+" : ACCEPT AMOUNT : "+collectionAmount);
					bean.setAcceptAmount(collectionAmount);
				}
				else if(collectionState.equals("CLAIM")){
//					System.out.println(gblNo+" : CLAIM AMOUNT : "+collectionAmount);
					bean.setClaimeAmount(collectionAmount);
				}
				else if(collectionState.equals("DEPOSIT")){
//					System.out.println(gblNo+" : COLLECTED AMOUNT : "+collectionAmount);
					bean.setCollectionAmount(collectionAmount);
				}
				list.add(bean);
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<AllScacTotalInvoiceCollectionStatusGblBeans> getInboundEachScacUncollectedGbl(String scac,String code,String begin,String end){
		ArrayList<AllScacTotalInvoiceCollectionStatusGblBeans> list = new ArrayList<>();
		AllScacTotalInvoiceCollectionStatusGblBeans bean = new AllScacTotalInvoiceCollectionStatusGblBeans();
		String sql="select	invoice_gbl.gbl_no as gblNo, round(invoice_gbl.amount,2) as amount, invoice_list.start_date as start, invoice_list.end_date as end,"+
				   "invoice_gbl.name as name,gbl_ib.code as code,gbl_ib.tsp as scac, round(invoice_gbl_collection.difference,2) as difference,"+
				   "invoice_gbl_collection.state as state, invoice_gbl_collection_flow.state as collectionState, round(invoice_gbl_collection_flow.amount,2) as collectionAmount,"+
				   "invoice_list.invoice_date as invoiceDate,DATEDIFF(DATE_FORMAT(SYSDATE(), '%Y%m%d'), DATE_FORMAT(CONVERT(invoice_list.invoice_date, DATE), '%Y%m%d')) as datediff "
				   + " from	invoice_list left join invoice_gbl on invoice_list.seq = invoice_gbl.invoice_list_seq"
				   + "	left join gbl_ib on invoice_gbl.gbl_seq = gbl_ib.seq"
				   + "	left join invoice_gbl_collection on invoice_gbl_collection.invoice_gbl_seq = invoice_gbl.seq"
				   + "	left join invoice_gbl_collection_flow on invoice_gbl_collection.seq = invoice_gbl_collection_flow.invoice_gbl_collection_seq"
				   + " where invoice_list.process = 'inbound'";
		if(!scac.equals("ALL")){
			sql+=" and gbl_ib.tsp='"+scac+"'";
		}
		if(!code.equals("ALL")){
			sql+=" and gbl_ib.code='"+code+"'";
		}
		if(!begin.equals("") && !end.equals("")){
			sql+=" and date(invoice_list.invoice_date) >= date('"+begin+"') and date(invoice_list.invoice_date) <= date('"+end+"')";
		}
		System.out.println("inbound SQL1 : "+sql);
		try {
			connect();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				String scac1 = rs.getString("scac");
				String gblNo = rs.getString("gblNo");
				String amount = rs.getString("amount");
				String name = rs.getString("name");
				String code1 = rs.getString("code");
				String difference = rs.getString("difference");
				String state1 = rs.getString("state");
				String collectionState = rs.getString("collectionState");
				String collectionAmount = rs.getString("collectionAmount");
				String dateDiff = rs.getString("datediff");
				String invoiceDate = rs.getString("invoiceDate");
//				System.out.println("SCAC : "+scac1+" GBL NO : "+gblNo+" AMOUNT : "+amount+" NAME : "+name+" COLLECTION STATE : "+collectionState);
				bean = new AllScacTotalInvoiceCollectionStatusGblBeans();
				bean.setScac(scac1);
				bean.setGblNo(gblNo);
				bean.setAmount(amount);
				bean.setName(name);
				bean.setCode(code1);
				bean.setInvoiceDate(invoiceDate);
				double tempDiff = getDoubleValue(difference);
//				if(tempDiff <0){
					tempDiff *= -1;
//				}
				
				bean.setDateDiff(dateDiff);
				bean.setDifference(tempDiff+"");
				bean.setState(state1);
				bean.setProcess("inbound");
				if(collectionState==null || collectionState.equals("")){
//					System.out.println("STATE NULL ADD uncollectedAmount : "+amount);
					bean.setDifference("0.0");
					bean.setUncollectedAmount(amount);
				}
				else if(collectionState.equals("ACCEPT")){
//					System.out.println("ACCEPT AMOUNT : "+collectionAmount);
					bean.setAcceptAmount(collectionAmount);
				}
				else if(collectionState.equals("CLAIM")){
//					System.out.println("CLAIM AMOUNT : "+collectionAmount);
					bean.setClaimeAmount(collectionAmount);
				}
				else if(collectionState.equals("DEPOSIT")){
//					System.out.println("COLLECTED AMOUNT : "+collectionAmount);
					bean.setCollectionAmount(collectionAmount);
				}
				list.add(bean);
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<EachScacUncollectedBeans> getEachScacUncollected(String scac,String inOut,String code,String begin,String end){
		ArrayList<EachScacUncollectedBeans> list = new ArrayList<>();
		EachScacUncollectedBeans esub = new EachScacUncollectedBeans();		
		int whereFlag=0;
		HashMap<String,Integer> hm = new HashMap();
		System.out.println("SCAC : "+scac);
//		String sql = "select *,invoice_list.seq as invoiceSeq,DATEDIFF(DATE_FORMAT(SYSDATE(), '%Y%m%d'), DATE_FORMAT(CONVERT(invoice_list.invoice_date, DATE), '%Y%m%d')) as datediff from invoice_list left join invoice_collection on invoice_list.seq = invoice_collection.invoice_seq";
		String sql = "select invoice_list.invoice_no,round(invoice_list.amount,2) as amount,invoice_list.invoice_date,round(invoice_collection.net,2) as net ,invoice_list.seq,invoice_list.process,invoice_list.seq as invoiceSeq,DATEDIFF(DATE_FORMAT(SYSDATE(), '%Y%m%d'),DATE_FORMAT(CONVERT(invoice_list.invoice_date, DATE), '%Y%m%d')) as datediff from invoice_list left join invoice_collection on invoice_list.seq = invoice_collection.invoice_seq ";
		String condition="";
		if(!scac.equals("ALL")){
			if(whereFlag==0){
				whereFlag=1;
				condition +=" where invoice_list.tsp= '"+scac+"'";
			}
			else{
				condition += " and  invoice_list.tsp='"+scac+"'";
			}
		}
		if(!inOut.equals("ALL")){// in or out
			if(inOut.equals("IN")){
				if(whereFlag==0){
					whereFlag=1;
					condition += " where invoice_list.process='inbound'";
				}
				else{
					condition += " and invoice_list.process='inbound'";
				}
			}
			else if(inOut.equals("OUT")){
				if(whereFlag==0){
					whereFlag=1;
					condition += " where invoice_list.process='outbound'";
				}
				else{
					condition += " and invoice_list.process='outbound'";
				}
			}
		}
		if(!begin.equals("") && !end.equals("")){
			if(whereFlag==0){
				whereFlag =1;
				condition+=" where date(invoice_list.invoice_date) >= date_format('"+begin+"','%y%m%d') and date(invoice_list.invoice_date) <= date_format('"+end+"','%y%m%d')";
			}
			else{
				condition +=" and date(invoice_list.invoice_date) >= date_format('"+begin+"','%y%m%d') and date(invoice_list.invoice_date) <= date_format('"+end+"','%y%m%d')";
			}
		}
		sql+=condition;
//		String alpha = " group by invoice_list.seq ";
		System.out.println("1sql : "+sql);
		rs = getResultSet(sql);
		int index=0;
		try {
			while(rs.next()){
				hm.put(rs.getString("invoiceSeq"),index++);
				esub = new EachScacUncollectedBeans();
//				System.out.println("[[ HM HASMAP PUT : "+rs.getString("invoiceSeq")+" index : "+(index-1)+" ]]");
				esub.setInvoiceNo(rs.getString("invoice_no"));
				esub.setInvoicedAmounts(rs.getString("amount"));
				esub.setInvoiceDate(rs.getString("invoice_date"));
				esub.setCollectedAmounts(rs.getString("net"));
				esub.setInvoiceListSeq(rs.getString("invoiceSeq"));
				esub.setProcess(rs.getString("process"));
				esub.setDatediff(rs.getString("datediff"));
//				System.out.println("========[Basic Information]=======");
//				System.out.println("no : "+rs.getString("invoice_no"));
//				System.out.println("amount : "+rs.getString("amount"));
//				System.out.println("date : "+rs.getString("invoice_date"));
//				System.out.println("collected : "+rs.getString("net"));
//				System.out.println("invoiceSeq : "+rs.getString("invoiceSeq"));
//				System.out.println("date diff : "+rs.getString("datediff"));
//				System.out.println("==================================");
				list.add(esub);
			}
			try {
				disconnect();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//////////////////////////////////////////////////////////////2st querry start//////////////////////////////////////
		sql="select invoice_gbl.gbl_no as gblNo,gbl_ib.code as gblibcode,gbl.code as gblobcode,invoice_gbl.invoice_list_seq as list_seq,round(invoice_gbl.amount,2) as gblamount, "
				+ "round(invoice_gbl_collection.net,2) as net, round(invoice_gbl_collection.difference,2) as diff, invoice_gbl_collection.seq as gbl_collection_seq "
				+"from invoice_gbl left join invoice_gbl_collection on invoice_gbl.seq = invoice_gbl_collection.invoice_gbl_seq "
				+"left join gbl_ib on invoice_gbl.gbl_seq = gbl_ib.seq left join gbl on invoice_gbl.gbl_seq = gbl.seq";
		condition="";//init condition string;
		whereFlag=0;
//		if(!code.equals("ALL")){
//			if(!inOut.equals("ALL")){// in or out
//				if(inOut.equals("IN")){
//					if(whereFlag==0){
//						whereFlag=1;
//						condition += " where invoice_list.process='inbound'";
//					}
//					else{
//						condition += " and invoice_list.process='inbound'";
//					}
//				}
//				else if(inOut.equals("OUT")){
//					if(whereFlag==0){
//						whereFlag=1;
//						condition += " where invoice_list.process='outbound'";
//					}
//					else{
//						condition += " and invoice_list.process='outbound'";
//					}
//				}
//			}
//			else{
//				
//			}
//		}
		sql+=condition;
		System.out.println("2st sql : "+sql);
		rs = getResultSet(sql);
		index=0;
		System.out.println("==== WORMING UP ====");
//		for(int i=0;i<list.size();i++){
//			System.out.println(i+"st : "+list.get(i).getInvoiceNo());
//		}
		System.out.println("====================");
		try {
			while(rs.next()){
				if(hm.get(rs.getString("list_seq"))!=null){
//					System.out.println("[ 2st SQL execution result is not null ]");
					index = hm.get(rs.getString("list_seq"));
//					System.out.println("[ GET INDEX FOR LIST_SEQ : "+rs.getString("list_seq")+" - INDEX : "+index);
					double net = Double.parseDouble(checkAmountsNull(rs.getString("net")));
					double diff = Double.parseDouble(checkAmountsNull(rs.getString("diff")));
					if(diff!=0){
						if(diff>0){
							System.out.println("돈이 초과되서 들어온 상황 GBL no : "+rs.getString("gblNo"));
						}
						diff *= -1;
					}
					list.get(index).addGblQuantity();
					if(net==0 && diff==0){
						list.get(index).addUncollectedAmount(rs.getString("gblamount"));
//						System.out.println("=======================");
//						System.out.println("Invoice No : "+list.get(index).getInvoiceNo());
//						System.out.println("UN Collected : "+rs.getString("gblamount"));
//						System.out.println("=======================");
					}
					else if(diff!=0 && net!=0){
						list.get(index).addShortPaid(diff+"");
//						System.out.println("=======================");
//						System.out.println("Invoice No : "+list.get(index).getInvoiceNo());
//						System.out.println("Short Paid : "+diff);
//						System.out.println("=======================");
					}
					else if(diff!=0 && net==0){//안걷힌돈
						list.get(index).addUncollectedAmount(rs.getString("gblamount"));
//						System.out.println("?!");
//						System.out.println("diff : "+diff+" net : "+net+" no : "+list.get(index).getInvoiceNo());
//						System.out.println("=======================");
//						System.out.println("Invoice No : "+list.get(index).getInvoiceNo());
//						System.out.println("UN Collected : "+rs.getString("gblamount"));
//						System.out.println("=======================");
					}
					else{//완료 된돈
//						System.out.println("@?diff : "+diff+" net : "+net+" no : "+list.get(index).getInvoiceNo());
//						System.out.println("elseelseelseelse");
//						System.out.println("!!!!!!!!!!!!!!!!!!!!! GBL no : "+rs.getString("gblNo"));
//						System.out.println("diff : "+diff);
//						System.out.println("net : "+net);
					}
				}
			}
			try {
				disconnect();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		////////////////////////////////////////////////////////////3st querry start//////////////////////////////////////
		sql="select invoice_gbl.invoice_list_seq as listSeq, invoice_gbl_collection.seq as collection_seq, invoice_gbl_collection_flow.state as state, "+ 
				"invoice_gbl_collection_flow.amount as amount from invoice_gbl_collection left join invoice_gbl_collection_flow "+ 
				"on invoice_gbl_collection.seq = invoice_gbl_collection_flow.invoice_gbl_collection_seq "+
				"left join invoice_gbl on invoice_gbl.seq = invoice_gbl_collection.invoice_gbl_seq";
		System.out.println("3st sql : "+sql);
		condition="";//init condition string;
		rs = getResultSet(sql);
		index=0;
		try {
			while(rs.next()){
				if(hm.get(rs.getString("listSeq"))!=null){
//					System.out.println("[ 2st SQL execution result is not null ]");
					index = hm.get(rs.getString("listSeq"));
//					list.get(index).addGblQuantity();
					if(rs.getString("state")!=null){
						if(rs.getString("state").equals("ACCEPT")){
							double accept = Double.parseDouble(checkAmountsNull(rs.getString("amount")));
		//					list.get(index).addShortPaid((-1*accept)+"");
							list.get(index).addAcceptPaid(accept+"");
//							System.out.println("===================================");
//							System.out.println("Invoice No : "+list.get(index).getInvoiceNo());
//							System.out.println("Accepted Amount : "+accept);
//							System.out.println("===================================");
						}
						else if(rs.getString("state").equals("CLAIM")){
							double claim = Double.parseDouble(checkAmountsNull(rs.getString("amount")));
							list.get(index).addClaimPaid(claim+"");
		//					list.get(index).addShortPaid((-1*claim)+"");
//							System.out.println("===================================");
//							System.out.println("Invoice No : "+list.get(index).getInvoiceNo());
//							System.out.println("Claimed Amount : "+claim);
//							System.out.println("===================================");
						}
					}
				}
			}
			try {
				disconnect();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ResultSet getResultSet(String sql){
		ResultSet rs2 = null;
		try {
			connect();
			System.out.println("[ get ResultSet started ]");
			rs2 = stmt.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs2;
	}
	
//	public ArrayList<EachScacInvoiceCollectionBeans> getEachScacInvoiceCollection(String scac,String inOut,String code,String begin,String end){
//		ArrayList<EachScacInvoiceCollectionBeans> list = new ArrayList<>();
//		EachScacInvoiceCollectionBeans esic = new EachScacInvoiceCollectionBeans();		
//		int whereFlag=0;
//		HashMap<String,Integer> hm = new HashMap();
//		String sql = "select *,invoice_list.seq as invoiceSeq from invoice_list left join invoice_collection on invoice_list.seq = invoice_collection.invoice_seq";
//		String condition="";
//		if(!scac.equals("ALL")){
//			if(whereFlag==0){
//				whereFlag=1;
//				condition +=" where invoice_list.tsp= '"+scac+"'";
//			}
//			else{
//				condition += " and  invoice_list.tsp='"+scac+"'";
//			}
//		}
//		if(!inOut.equals("ALL")){// in or out
//			if(inOut.equals("IN")){
//				if(whereFlag==0){
//					whereFlag=1;
//					condition += " where invoice_list.process='inbound'";
//				}
//				else{
//					condition += " and invoice_list.process='inbound'";
//				}
//			}
//			else if(inOut.equals("OUT")){
//				if(whereFlag==0){
//					whereFlag=1;
//					condition += " where invoice_list.process='outbound'";
//				}
//				else{
//					condition += " and invoice_list.process='outbound'";
//				}
//			}
//		}
//		if(!begin.equals("")||!end.equals("")){
//			if(whereFlag==0){
//				whereFlag=1;
//				condition+=" where date(invoice_list.invoice_date) >= date('"+begin+"') and date(invoice_list.invoice_date) <= date('"+end+"')";
//			}
//			else{
//				condition+=" and date(invoice_list.invoice_date) >= date('"+begin+"') and date(invoice_list.invoice_date) <= date('"+end+"')";
//			}
//		}
//		sql+=condition;
//		System.out.println("sql : "+sql);
//		rs = getResultSet(sql);
//		int index=0;
//		try {
//			while(rs.next()){
//				hm.put(rs.getString("invoiceSeq"),index++);
//				esic = new EachScacInvoiceCollectionBeans();
//				esic.setInvoiceNo(rs.getString("invoice_no"));
//				esic.setInvoicedAmounts(rs.getString("amount"));
//				esic.setInvoiceDate(rs.getString("invoice_date"));
//				esic.setCollectedAmounts(rs.getString("net"));
//				esic.setInvoiceListSeq(rs.getString("invoiceSeq"));
//				esic.setProcess(rs.getString("process"));
//				if(rs.getString("state")==null){
//					esic.setStatus("PENDING");
//				}
//				else if(rs.getString("state").equals("COMPLETE")){
//					esic.setStatus("OK");
//				}
//				else{
//					esic.setStatus("PENDING");
//				}
//				System.out.println("========[Basic Information]=======");
//				System.out.println("no : "+rs.getString("invoice_no"));
//				System.out.println("amount : "+rs.getString("amount"));
//				System.out.println("date : "+rs.getString("invoice_date"));
//				System.out.println("collected : "+rs.getString("net"));
//				System.out.println("invoiceSeq : "+rs.getString("invoiceSeq"));
//				System.out.println("status : "+esic.getStatus());
//				System.out.println("=======================");
//				list.add(esic);
//			}
//			try {
//				disconnect();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//////////////////////////////////////////////////////////////2st querry start//////////////////////////////////////
//		sql="select gbl_ib.code as gblibcode,gbl.code as gblobcode,invoice_gbl.invoice_list_seq as list_seq,invoice_gbl.amount as gblamount, "
//				+ "invoice_gbl_collection.net as net, invoice_gbl_collection.difference as diff, invoice_gbl_collection.seq as gbl_collection_seq "
//				+"from invoice_gbl left join invoice_gbl_collection on invoice_gbl.seq = invoice_gbl_collection.invoice_gbl_seq "
//				+"left join gbl_ib on invoice_gbl.gbl_seq = gbl_ib.seq left join gbl on invoice_gbl.gbl_seq = gbl.seq";
//		condition="";//init condition string;
//		whereFlag=0;
////		if(!code.equals("ALL")){
////			if(!inOut.equals("ALL")){// in or out
////				if(inOut.equals("IN")){
////					if(whereFlag==0){
////						whereFlag=1;
////						condition += " where invoice_list.process='inbound'";
////					}
////					else{
////						condition += " and invoice_list.process='inbound'";
////					}
////				}
////				else if(inOut.equals("OUT")){
////					if(whereFlag==0){
////						whereFlag=1;
////						condition += " where invoice_list.process='outbound'";
////					}
////					else{
////						condition += " and invoice_list.process='outbound'";
////					}
////				}
////			}
////			else{
////				
////			}
////		}
//		sql+=condition;
//		System.out.println("2st sql : "+sql);
//		rs = getResultSet(sql);
//		index=0;
//		try {
//			while(rs.next()){
//				if(hm.get(rs.getString("list_seq"))!=null){
////					System.out.println("[ 2st SQL execution result is not null ]");
//					index = hm.get(rs.getString("list_seq"));
//					double ip = Double.parseDouble(checkAmountsNull(rs.getString("net")));
//					double diff = Double.parseDouble(checkAmountsNull(rs.getString("diff")));
//					diff *= -1;
//					list.get(index).addGblQuantity();
//					if(ip==0.0 && diff==0.0){
//						list.get(index).addUncollectedAmount(rs.getString("gblamount"));
//						System.out.println("=======================");
//						System.out.println("Invoice No : "+list.get(index).getInvoiceNo());
//						System.out.println("UN Collected : "+rs.getString("gblamount"));
//						System.out.println("=======================");
//					}
//					if(diff!=0.0){
//						list.get(index).addShortPaid(diff+"");
//						System.out.println("=======================");
//						System.out.println("Invoice No : "+list.get(index).getInvoiceNo());
//						System.out.println("Short Paid : "+diff);
//						System.out.println("=======================");
//					}
//				}
//			}
//			try {
//				disconnect();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		////////////////////////////////////////////////////////////3st querry start//////////////////////////////////////
//		sql="select invoice_gbl.invoice_list_seq as listSeq, invoice_gbl_collection.seq as collection_seq, invoice_gbl_collection_flow.state as state, "+ 
//				"invoice_gbl_collection_flow.amount as amount from invoice_gbl_collection left join invoice_gbl_collection_flow "+ 
//				"on invoice_gbl_collection.seq = invoice_gbl_collection_flow.invoice_gbl_collection_seq "+
//				"left join invoice_gbl on invoice_gbl.seq = invoice_gbl_collection.invoice_gbl_seq";
//		System.out.println("3st sql : "+sql);
//		condition="";//init condition string;
//		rs = getResultSet(sql);
//		index=0;
//		try {
//			while(rs.next()){
//				if(hm.get(rs.getString("listSeq"))!=null){
////					System.out.println("[ 2st SQL execution result is not null ]");
//					index = hm.get(rs.getString("listSeq"));
////					list.get(index).addGblQuantity();
//					if(rs.getString("state")!=null){
//						if(rs.getString("state").equals("ACCEPT")){
//							double accept = Double.parseDouble(checkAmountsNull(rs.getString("amount")));
//		//					list.get(index).addShortPaid((-1*accept)+"");
//							list.get(index).addAcceptPaid(accept+"");
//							System.out.println("=======================");
//							System.out.println("Invoice No : "+list.get(index).getInvoiceNo());
//							System.out.println("Accepted Amount : "+accept);
//							System.out.println("=======================");
//						}
//						else if(rs.getString("state").equals("CLAIM")){
//							double claim = Double.parseDouble(checkAmountsNull(rs.getString("amount")));
//							list.get(index).addClaimPaid(claim+"");
//		//					list.get(index).addShortPaid((-1*claim)+"");
//							System.out.println("=======================");
//							System.out.println("Invoice No : "+list.get(index).getInvoiceNo());
//							System.out.println("Claimed Amount : "+claim);
//							System.out.println("=======================");
//						}
//					}
//				}
//			}
//			try {
//				disconnect();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;
//	}
	public int getIndexOfInvoiceNo(ArrayList<EachScacInvoiceCollectionBeans> list,String invoiceNo){
		int resultIndex=-1;
		EachScacInvoiceCollectionBeans bean = new EachScacInvoiceCollectionBeans();
		for(int i=0;i<list.size();i++){
			bean = list.get(i);
			String temp = bean.getInvoiceNo();
			if(temp.equals(invoiceNo)){
				resultIndex = i;
			}
		}
		return resultIndex;
	}
	public ArrayList<EachScacInvoiceCollectionBeans> getEachScacInvoiceCollection(String scac,String inOut,String code,String begin,String end){

		ArrayList<EachScacInvoiceCollectionBeans> list = new ArrayList<>();
		EachScacInvoiceCollectionBeans esic;		
		int whereFlag=0;
		HashMap<String,Integer> hm = new HashMap();//invoiceNo,Quantity,InvoiceAmount;
		String sql = "select count(ig.seq) as gblCount, il.invoice_no, il.amount as invoiceAmount,  il.invoice_date as invoiceDate "+  
					"from invoice_list as il "+
					"left join invoice_gbl as ig on ig.invoice_list_seq = il.seq "+ 
					"where 1=1 ";
		String condition="";
		
		if(!scac.equals("ALL")){
			condition+="and il.tsp = '"+scac+"' ";
		}
		if(!inOut.equals("ALL")){// in or out
			if(inOut.equals("IN")){
				condition+="and il.process = 'inbound' ";
			}
			else if(inOut.equals("OUT")){
				condition+="and il.process = 'outbound' ";
			}
		}
		if(!begin.equals("")||!end.equals("")){
			condition+="and date(il.invoice_date) >= date('"+begin+"') and date(il.invoice_date) <= date('"+end+"') ";
		}
		condition+="group by il.invoice_no ";
		sql+=condition;
		condition="";
		System.out.println("1st sql : "+sql);
		rs = getResultSet(sql);
		int index=0;
		try {
			while(rs.next()){
				String gblCount = rs.getString("gblCount");
				String invoiceNo = rs.getString("invoice_no");
				String invoiceAmount = rs.getString("invoiceAmount");
				String invoiceDate = rs.getString("invoiceDate");
				index = getIndexOfInvoiceNo(list,invoiceNo);
				if(index==-1){
					esic = new EachScacInvoiceCollectionBeans();
					esic.setInvoiceNo(invoiceNo);
					esic.setInvoicedAmounts(invoiceAmount);
					esic.setGblQuantity(gblCount);
					esic.setInvoiceDate(invoiceDate);
					list.add(esic);
					System.out.println("CREATE NEW BEANS INVOICE NO : "+esic.getInvoiceNo()+" AMOUNT : "+esic.getInvoicedAmounts()+" GBLCOUNT : "+esic.getGblQuantity());
				}
				else{//already exist
					System.out.println("ALREADY?? this is cant not happen");
				}
			}
			try {
				disconnect();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//////////////////////////////////////////////////////////////2st querry start////////////////////////////////////// short Paid, collectedAmount
		sql="select il.invoice_no as invoiceNo,igc.state as state, "+
			"ig.amount as totalGblAmount ,igc.net as collectedAmount, igc.difference as shortpaid, il.process as process "+
			"from invoice_list as il "+
			"left join invoice_gbl as ig on ig.invoice_list_seq = il.seq "+ 
			"left join invoice_gbl_collection as igc on igc.invoice_gbl_seq = ig.seq "+
			"where 1=1 "+
			"and igc.net>0 ";
		condition+="and il.tsp = '"+scac+"' ";
		sql+=condition;
		condition="";
		System.out.println("2st sql : "+sql);
		rs = getResultSet(sql);
		index=0;
		try {
			while(rs.next()){
				String invoiceNo=rs.getString("invoiceNo");
				String shortPaid=rs.getString("shortpaid");
				String collectedAmount=rs.getString("collectedAmount");
				index = getIndexOfInvoiceNo(list, invoiceNo);
				double tempShortPaid = getDoubleValue(shortPaid);
				if(tempShortPaid<0){
					tempShortPaid*=-1;
				}
				double tempCollectedAmount = getDoubleValue(collectedAmount);
				if(index!=-1){
					list.get(index).addCollectedAmount(tempCollectedAmount+"");
					list.get(index).addShortPaid(tempShortPaid+"");
				}
				else{
					System.out.println("인덱스가 없다?? "+invoiceNo+" 여기서 문제생김");
				}
			}
			try {
				disconnect();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		////////////////////////////////////////////////////////////3st querry start////////////////////////////////////// accepted, claim
		sql="select il.invoice_no as invoiceNo, "+
			"igcf.state as state, igcf.amount as amount "+
			"from invoice_list as il  "+
			"left join invoice_gbl as ig on ig.invoice_list_seq = il.seq "+ 
			"left join invoice_gbl_collection as igc on igc.invoice_gbl_seq = ig.seq "+ 
			"left join invoice_gbl_collection_flow as igcf on igcf.invoice_gbl_collection_seq = igc.seq "+
			"where 1=1 "+
			"and igcf.state='ACCEPT' or igcf.state='CLAIM' ";
		condition+="and il.tsp = '"+scac+"' ";
		sql+=condition;
		System.out.println("3st sql : "+sql);
		condition="";//init condition string;
		rs = getResultSet(sql);
		index=0;
		try {
			while(rs.next()){
				String invoiceNo = rs.getString("invoiceNo");
				String state = rs.getString("state");
				String amount = rs.getString("amount");
				index = getIndexOfInvoiceNo(list, invoiceNo);
				if(index!=-1){
					if(state.equals("ACCEPT")){
						list.get(index).addAcceptPaid(amount);
					}
					else if(state.equals("CLAIM")){
						list.get(index).addClaimPaid(amount);
					}
				}
				else{
					System.out.println("인덱스가 없다?? "+invoiceNo+" 여기서 문제생김");
				}
			}
			try {
				disconnect();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		////////////////////////////////////////////////////////////4st querry start////////////////////////////////////// Uncollected
		sql="select il.invoice_no as invoiceNo,igc.state as state, "+
			"ig.amount as totalGblAmount ,igc.net as net, igc.difference as shortpaid, il.process as process "+
			"from invoice_list as il "+
			"left join invoice_gbl as ig on ig.invoice_list_seq = il.seq "+ 
			"left join invoice_gbl_collection as igc on igc.invoice_gbl_seq = ig.seq "+
			"where 1=1 ";
			condition+="and il.tsp = '"+scac+"' ";
			sql+=condition;
		System.out.println("4st sql : "+sql);
		condition="";//init condition string;
		rs = getResultSet(sql);
		index=0;
		try {
		while(rs.next()){
			String invoiceNo = rs.getString("invoiceNo");
			String state = rs.getString("state");
			String net = rs.getString("net");
			String amount = rs.getString("totalGblAmount");
			index = getIndexOfInvoiceNo(list, invoiceNo);
			if(index!=-1){
				if(state==null){
					list.get(index).addUncollectedAmount(amount);
				}
				else if(state.equals("COMPLETE")){
					
				}
				else if(state.equals("RESENT")){
					if(net.equals("0.0")||net == null||Double.parseDouble(net)==0.0){
						list.get(index).addUncollectedAmount(amount);
					}
				}
			}
			else{
			System.out.println("인덱스가 없다?? "+invoiceNo+" 여기서 문제생김");
			}
		}
		try {
		disconnect();
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return list;
	
	}
	public ArrayList<User> getUserList(){
		ArrayList<User> userList = new ArrayList<>();
		String sql="select * from stat_user";
		System.out.println("SQL : "+sql);
		try {
			connect();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				User userBean = new User();
				String id = rs.getString("id");
				String pw = rs.getString("pw");
				String level = rs.getString("level");
				userBean.setId(id);
				userBean.setPassword(pw);
				userBean.setLevel(level);
				userList.add(userBean);
				System.out.println("-----------------------------");
				System.out.println("id : "+id);
				System.out.println("pw : "+pw);
				System.out.println("level : "+level);
				System.out.println("-----------------------------");
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}
	
	public ArrayList<InvoiceFilteringBeans> getInvoiceCollectionFiltering(String scac,String inOut,String begin,String end, String status){
		ArrayList<InvoiceFilteringBeans> list = new ArrayList<>();
		InvoiceFilteringBeans ifb;
		int whereFlag=0;
		String condition="";
		String sql="select il.invoice_no as invoiceNo,il.tsp as tsp, il.amount as totalAmount,"+
				"ig.gbl_no as gblNo, sum(round(igcf.amount,2)) as sumAmount,count(igcf.seq),il.process as process, "+
				"il.invoice_date as invoiceDate "+
				"from invoice_list as il left join invoice_gbl as ig on il.seq = ig.invoice_list_seq "+
				"left join invoice_gbl_collection as igc on ig.seq = igc.invoice_gbl_seq "+
				"left join invoice_gbl_collection_flow as igcf on igc.seq = igcf.invoice_gbl_collection_seq "+
				"where 1=1 ";
		if(!scac.equals("ALL")){
				condition += " and  il.tsp='"+scac+"'";
		}
		if(!inOut.equals("ALL")){// in or out
			if(inOut.equals("IN")){
					condition += " and il.process='inbound'";
			}
			else if(inOut.equals("OUT")){
					condition += " and il.process='outbound'";
			}
		}
		if(!status.equals("ALL")){
			if(status.equals("UNCOLLECTED")){
				
			}
			else if(status.equals("COLLECTED")){
				
			}
		}
		if(!begin.equals("")&!end.equals("")){
			if( !begin.equals("") && !end.equals("")){
					condition+=" and date(il.invoice_date) >= date('"+begin+"') and date(il.invoice_date) <= date('"+end+"')";
			}
		}
		condition+="group by il.invoice_no";
		
//		if(date.equals("COLLECTED")){//??
//			if(!begin.equals("") && !end.equals("")){
//				if(whereFlag==0){
//					whereFlag=1;
//					condition+=" where date(invoice_list.write_date) >= date('"+begin+"') and date(invoice_list.write_date) <= date('"+end+"') and invoice_collection.state='COMPLETE'";
//				}
//				else{
//					condition+=" and date(write_date) >= date('"+begin+"') and date(write_date) <= date('"+end+"') and invoice_collection.state='COMPLETE'";
//				}
//			}
//			else{
//				if(whereFlag==0){
//					whereFlag=1;
//					condition+=" where invoice_collection.state='COMPLETE'";
//				}
//				else{
//					condition+=" and invoice_collection.state='COMPLETE'";
//				}
//			}
//			
//		}
		/***********************************************************************************/
//		if(!status.equals("ALL")){
//			if(status.equals("COLLECTED")){
//				condition+=" and invoice_collection.state='COMPLETE'";
//			}
//			if(status.equals("UNCOLLECTED")){
//				condition+=" and invoice_collection.state='RESENT' or invoice_collection.state is null";
//				
//			}
//		}
		/***********************************************************************************/
		sql+=condition;
		System.out.println("====================Invoice Collection Filtering querty 1st=================================");
		System.out.println(sql);
		System.out.println("============================================================================================");
		try {
			connect();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				ifb = new InvoiceFilteringBeans();
//				System.out.println("*****************************************************");
				ifb.setInvoiceNo(rs.getString("invoiceNo"));
				ifb.setInvoicedDate(rs.getString("invoiceDate"));
				ifb.setInvoicedAmounts(getDoubleRoundValue(rs.getString("totalAmount")));
				ifb.setNet(checkAmountsNull(getDoubleRoundValue(rs.getString("sumAmount"))));
				double diffTemp=Double.parseDouble(ifb.getInvoicedAmounts())-Double.parseDouble(ifb.getNet());
				if(diffTemp<0){
					System.out.println("이런일이 발생해?????");
					System.out.println("INVOIE NO : "+ifb.getInvoiceNo());
					diffTemp *= -1;
				}
				ifb.setUnCollectedAmounts(diffTemp+"");
				if(status.equals("UNCOLLECTED")&&ifb.getNet().equals("0.00")){
					System.out.println("UNCOLLECTED - INVOICE AMOUNT : "+ifb.getInvoicedAmounts()+" - NET : "+ifb.getNet());
					list.add(ifb);
				}
				else if(status.equals("COLLECTED")&& ifb.getNet().equals(ifb.getInvoicedAmounts())){
					System.out.println("COLLECTED - INVOICE AMOUNT : "+ifb.getInvoicedAmounts()+" - NET : "+ifb.getNet());
					list.add(ifb);
				}
				else if(status.equals("SHORTPAID") && !ifb.getNet().equals("0.00") && !ifb.getNet().equals(ifb.getInvoicedAmounts())){
					System.out.println("SHORT PAID - INVOICE AMOUNT : "+ifb.getInvoicedAmounts()+" - NET : "+ifb.getNet());
					list.add(ifb);
				}
				else if(status.equals("ALL")){
					System.out.println("ALL - INVOICE AMOUNT : "+ifb.getInvoicedAmounts()+" - NET : "+ifb.getNet());
					list.add(ifb);
				}
				
//				System.out.println("invoice no          : "+ifb.getInvoiceNo());
//				System.out.println("invoice date        : "+ifb.getInvoicedDate());
//				System.out.println("invoice amounts     : "+ifb.getInvoicedAmounts());
//				System.out.println("net amounts   : "+ifb.getNet());
//				System.out.println("uncollected amounts : "+ifb.getUnCollectedAmounts());
//				ifb.setUnCollectedAmounts(calcUncollectedAmounts(ifb.getInvoicedAmounts(),ifb.getCollectedAmounts()));
//				System.out.println("*****************************************************");
				
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		try {
//			connect();
//			rs = stmt.executeQuery(sql);
//			while(rs.next()){
//				ifb = new InvoiceFilteringBeans();
//				System.out.println("===========================================================================================================================");
//				ifb.setInvoiceNo(rs.getString("invoice_no"));
//				ifb.setInvoicedDate(getInvoiceFilteringWriteDate(rs.getString("Write_date")));
//				ifb.setInvoicedAmounts(rs.getString("amount"));
//				ifb.setCollectedAmounts(rs.getString("net"));
////				System.out.println("invoice no          : "+ifb.getInvoiceNo());
////				System.out.println("invoice date        : "+ifb.getInvoicedDate());
////				System.out.println("invoice amounts     : "+ifb.getInvoicedAmounts());
////				System.out.println("collected amounts   : "+ifb.getCollectedAmounts());
////				System.out.println("uncollected amounts : "+ifb.getUnCollectedAmounts());
//				ifb.setUnCollectedAmounts(calcUncollectedAmounts(ifb.getInvoicedAmounts(),ifb.getCollectedAmounts()));
//				System.out.println("===========================================================================================================================");
//				list.add(ifb);
//			}
//			disconnect();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		return list;
	}
	
	public String getDoubleRoundValue(String str){
		if(str==null){
			str="0.0";
		}
   		String result ="";
   		double d = Double.parseDouble(str);
   		result = new DecimalFormat("####0.00").format(d);
   		return result;
   	}
	
	public String checkAmountsNull(String str){
		String result;
		if(str==null){
			result="0.0";
		}
		else{
			result=str;
		}
		return result;
	}
	
	public String calcUncollectedAmounts(String total,String collected){
		String result="";
		if(total.equals("")){
			total="0.0";
		}
		if(collected.equals("")){
			collected="0.0";
		}
		double a = Double.parseDouble(total);
		double b = Double.parseDouble(collected);
		double c = a-b;
		result = c+"";
		return result;
	}
	public String getInvoiceFilteringWriteDate(String str){
		String result="";
		result = str.split(" ")[0];
		return result;
	}
	public WorkVolumeStat1Beans getOutboundWorkVolumeStat1(String scac,String area,String hhgUb,String code,String begin,String end,String type){
		String[][] str = new String[23][20];
		String sql="";
		String condition="";
		WorkVolumeStat1Beans wvs1 = new WorkVolumeStat1Beans();
		int whereflag=0;
		sql="select * from gbl";
		String querry="";
		if(!scac.equals("ALL")){
			querry="gbl.scac='"+scac+"'";
			condition+=sm.getWhereString(querry, whereflag);
			whereflag = sm.whereflagChange(whereflag);
		}
		if(!code.equals("ALL")){
				querry=" gbl.code='"+code+"'";
				condition+=sm.getWhereString(querry, whereflag);
				whereflag = sm.whereflagChange(whereflag);
		}
		if(!area.equals("ALL")){
				querry=" gbl.area='"+area+"'";
				condition+=sm.getWhereString(querry, whereflag);
				whereflag = sm.whereflagChange(whereflag);
		}
		if(!begin.equals("") && !end.equals("")){
				querry=" date(gbl.pud) >= date_format('"+begin+"','%y-%m-%d') and date(gbl.pud) <= date_format('"+end+"','%y-%m-%d')";
				condition+=sm.getWhereString(querry, whereflag);
				whereflag = sm.whereflagChange(whereflag);
		}
		if(!hhgUb.equals("")){
			if(hhgUb.equals("HHG")){
				querry=" (gbl.code = '3' or gbl.code = '4' or gbl.code = '5' or gbl.code='T' or gbl.code='t')";
				condition+=sm.getWhereString(querry, whereflag);
				whereflag = sm.whereflagChange(whereflag);
			}
			else if(hhgUb.equals("UB")){
				querry+=" (gbl.code = 'J' or gbl.code = '7' or gbl.code = '8' or gbl.code='j')";
				condition+=sm.getWhereString(querry, whereflag);
				whereflag = sm.whereflagChange(whereflag);
			}
		}
		sql+=condition;
		System.out.println("Work Stat1 outbound : "+sql);
		if(type.equals("WEIGHT")){
			try {
				connect();
				rs = stmt.executeQuery(sql);
				int count=0;
				while(rs.next()){
					count++;
					String tempWeight="";
					String code1 = rs.getString("code");
					String tempCode = "out_"+code1;
					String tempArea = rs.getString("area");
						if(code1.equals("3")||code1.equals("4")||code1.equals("5")||code1.equals("6")||code1.equals("T")||code1.equals("t")){
							 tempWeight = sm.checkMinimumValue(rs.getString("netWeight"), "HHG");
						}
						else if(code1.equals("J")||code1.equals("j")||code1.equals("7")||code1.equals("8")){
							 tempWeight = sm.checkMinimumValue(rs.getString("grossWeight"), "UB");
						}
						else{
							System.out.println("what code??");
							System.out.println("gblNo : "+rs.getString("no"));
						}
						
//					System.out.println("code : "+tempCode);
//					System.out.println("area : "+tempArea);
//					System.out.println("weight : "+tempWeight);
					if(code1!=null){
						if(tempWeight == null){
							tempWeight="0";
							wvs1.setWeightData(tempArea, tempCode, 1+"",tempWeight );
						}
						else{
							wvs1.setWeightData(tempArea, tempCode, 1+"",tempWeight );
						}
					}
//					else{
//						System.out.println("null !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//						System.out.println(tempCode);
//						System.out.println(tempArea);
//						System.out.println(tempWeight);
//						System.out.println(rs.getString("no"));
//					}
				}
				System.out.println("size : "+count);
				disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
			str = wvs1.getStr();
		}
		else if(type.equals("DENSITY")){
			try {
				connect();
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					String code1 = rs.getString("code");
					String tempCode = "out_"+rs.getString("code");
					String tempArea = rs.getString("area");
					String tempGross = rs.getString("grossWeight");
					String tempNet = rs.getString("netWeight");
					String tempCuft = rs.getString("cuft");
					String tempWeight="";
					String tempDensity = getDensity(tempGross, tempNet, tempCuft, rs.getString("code"));
					if(code1.equals("3")||code1.equals("4")||code1.equals("5")||code1.equals("T")||code1.equals("t")){
						 tempWeight = sm.checkMinimumValue(rs.getString("netWeight"), "HHG");
					}
					else if(code1.equals("J")||code1.equals("j")||code1.equals("7")||code1.equals("8")){
						 tempWeight = sm.checkMinimumValue(rs.getString("grossWeight"), "UB");
					}
					else{
						System.out.println("what code??");
						System.out.println("gblNo : "+rs.getString("no"));
					}
					wvs1.setNewDensity(tempArea, tempCode, "1", tempWeight, tempCuft);
				}
				disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
//			str = wvs1.getStr();
		}
		return wvs1;
	}
	public String[][] getAllScacTotalInvoiceCollectionStatusGbl(String begin,String end,String type,String code){
		String[][] str = new String[25][20];
		String gblno="";
//		int whereflag=0;
		String sql="";
		String joinstr="";
		String condition="";
//		System.out.println(scac);
		//INBOUND
		WorkVolumeStat1Beans wvs1 = new WorkVolumeStat1Beans();
		sql="select * from invoice_gbl,gbl_ib where invoice_gbl.gbl_seq = gbl_ib.seq";
		
		if(!begin.equals("") && !end.equals("")){
			condition+=" and date(gbl_ib.pud) >= date_format('"+begin+"','%y-%m-%d') and date(gbl_ib.pud) <= date_format('"+end+"','%y-%m-%d')";
		}
		
		if(!type.equals("")){
			if(type.equals("WEIGHT")){
				
			}
			else if(type.equals("DENSITY")){
				
			}
		}
		
		return str;
	}
	public WorkVolumeStat1Beans getInboundWorkVolumeStat1(String scac,String area,String hhgUb,String code,String begin,String end,String type){
		String[][] str = new String[25][20];
		String gblno="";
//		int whereflag=0;
		String sql="";
		String joinstr="";
		String condition="";
//		System.out.println(scac);
		//INBOUND
		WorkVolumeStat1Beans wvs1 = new WorkVolumeStat1Beans();
		sql="select * from gbl_ib";
		int whereflag=0;
		if(!scac.equals("ALL")){
			if(whereflag==0){
				condition+=" where gbl_ib.tsp='"+scac+"'";
				whereflag=1;
			}
			else{
				condition+=" and gbl_ib.tsp='"+scac+"'";
			}
			
		}
		if(!code.equals("ALL")){
			if(whereflag==0){
				condition+=" where gbl_ib.code='"+code+"'";
				whereflag=1;
			}
			else{
				condition+=" and gbl_ib.code='"+code+"'";
			}
				
		}
		if(!area.equals("ALL")){
			if(whereflag==0){
				condition+=" where gbl_ib.area='"+area+"'";
				whereflag=1;
			}
			else{
				condition+=" and gbl_ib.area='"+area+"'";
			}
			
		}
		if(!begin.equals("") && !end.equals("")){
			if(whereflag==0){
				condition+=" where date_format('"+begin+"','%y-%m-%d') <= date(gbl_ib.sitOut) and date(gbl_ib.sitOut) <= date_format('"+end+"','%y-%m-%d')";
				whereflag=1;
			}
			else{
				condition+=" and date_format('"+begin+"','%y-%m-%d') <= date(gbl_ib.sitOut) and date(gbl_ib.sitOut) <= date_format('"+end+"','%y-%m-%d')";
			}
			
		}
		if(!hhgUb.equals("")){
			if(hhgUb.equals("HHG")){
				if(whereflag==0){
					condition+=" wehere (gbl_ib.code = '3' or gbl_ib.code = '4' or gbl_ib.code = '5' or gbl_ib.code='T' or gbl_ib.code='t')";
					whereflag=1;
				}
				else{
					condition+=" and (gbl_ib.code = '3' or gbl_ib.code = '4' or gbl_ib.code = '5' or gbl_ib.code='T' or gbl_ib.code='t')";
				}
				
			}
			else if(hhgUb.equals("UB")){
				if(whereflag==0){
					condition+=" where (gbl_ib.code = 'J' or gbl_ib.code = '7' or gbl_ib.code = '8' or gbl_ib.code='j')";
					whereflag=1;
				}
				else{
					condition+=" and (gbl_ib.code = 'J' or gbl_ib.code = '7' or gbl_ib.code = '8' or gbl_ib.code='j')";
				}
				
			}
		}
		if(!type.equals("")){
			if(type.equals("WEIGHT")){
				sql+=condition;
				System.out.println("work volume inbound : "+sql);
				try {
					connect();
					rs = stmt.executeQuery(sql);
					while(rs.next()){
						String tempWeight="";
						String code1=rs.getString("code");
						String tempCode = "in_"+code1;
						String tempArea = rs.getString("area");
						String cuft = rs.getString("cuft");
						System.out.println("code : "+tempCode);
						System.out.println("area : "+tempArea);
						if(code1.equals("3")||code1.equals("4")||code1.equals("5")||code1.equals("T")||code1.equals("t")){
							 tempWeight = sm.checkMinimumValue(rs.getString("netWeight"), "HHG");
						}
						else if(code1.equals("J")||code1.equals("j")||code1.equals("7")||code1.equals("8")){
							 tempWeight = sm.checkMinimumValue(rs.getString("grossWeight"), "UB");
						}
						else{
							System.out.println("what code??");
							System.out.println("gblNo : "+rs.getString("gblNo"));
						}
						wvs1.setWeightData(tempArea, tempCode, 1+"",tempWeight );
					}
					disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
				str = wvs1.getStr();
			}
			else if(type.equals("DENSITY")){
				sql+=condition;
				System.out.println("work volume inbound : "+sql);
				try {
					connect();
					rs = stmt.executeQuery(sql);
					while(rs.next()){
						String code1 = rs.getString("code");
						String tempCode = "in_"+rs.getString("code");
						String tempArea = rs.getString("area");
						String tempWeight="";
//						System.out.println("code : "+tempCode);
//						System.out.println("area : "+tempArea);
						String gblNo = rs.getString("gblNo");
						String tempGross = rs.getString("grossWeight");
						String tempNet = rs.getString("netWeight");
						String tempCuft = rs.getString("cuft");
//						System.out.println("tempGross : "+tempGross+" tempNet : "+tempNet+" tempCuft : "+tempCuft);
//						String tempDensity = getDensity(tempGross, tempNet, tempCuft, rs.getString("code"));
						if(code1.equals("3")||code1.equals("4")||code1.equals("5")||code1.equals("T")||code1.equals("t")){
							 tempWeight = sm.checkMinimumValue(rs.getString("netWeight"), "HHG");
						}
						else if(code1.equals("J")||code1.equals("j")||code1.equals("7")||code1.equals("8")){
							 tempWeight = sm.checkMinimumValue(rs.getString("grossWeight"), "UB");
						}
						else{
							System.out.println("what code??");
							System.out.println("gblNo : "+rs.getString("gblNo"));
						}
						if(tempCuft.equals("")||tempCuft==null){
							ErrorPopup ep = new ErrorPopup("WORK STAT1 INBOUND ERROR", gblNo+" Cuft ERROR");
						}
						else{
							wvs1.setNewDensity(tempArea, tempCode, "1", tempWeight, tempCuft);
						}
					}
					disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
				str = wvs1.getStr();
			}
		}
		
		return wvs1;
	}
	public WorkVolumeStat2Beans getOutboundWorkVolumeStat2(String scac,String area,String hhgUb,String code,String begin,String end,String type){
		String[][] str = new String[ROW_LENGTH][22];
		String[][] cuftStr = new String[ROW_LENGTH][22];
		String[][] denStr = new String[ROW_LENGTH][22];
		String sql="";
		String condition="";
		String qr="";
		int whereflag=0;
		WorkVolumeStat2Beans wvs2 = new WorkVolumeStat2Beans();
		sql="select no,scac,code,area,pud,grossWeight,netWeight,cuft from gbl";
		if(!scac.equals("ALL")){
			qr = " gbl.scac='"+scac+"'";
			condition+=sm.getWhereString(qr, whereflag);
			whereflag = sm.whereflagChange(whereflag);
		}
		if(!code.equals("ALL")){
			qr = " gbl.code='"+code+"'";
			condition+=sm.getWhereString(qr, whereflag);
			whereflag = sm.whereflagChange(whereflag);
		}
		if(!area.equals("ALL")){
			qr = " gbl.area='"+area+"'";
			condition+=sm.getWhereString(qr, whereflag);
			whereflag = sm.whereflagChange(whereflag);
		}
		if(!begin.equals("") && !end.equals("")){
			qr = " date(gbl.pud) >= date_format('"+begin+"','%y-%m-%d') and date(gbl.pud) <= date_format('"+end+"','%y-%m-%d')";
			condition+=sm.getWhereString(qr, whereflag);
			whereflag = sm.whereflagChange(whereflag);
		}
		if(!hhgUb.equals("")){
			if(hhgUb.equals("HHG")){
				qr = " (gbl.code = '3' or gbl.code = '4' or gbl.code = '5' or gbl.code='T' or gbl.code='t')";
				condition+=sm.getWhereString(qr, whereflag);
				whereflag = sm.whereflagChange(whereflag);
			}
			else if(hhgUb.equals("UB")){
				qr = " (gbl.code = 'J' or gbl.code = '7' or gbl.code = '8' or gbl.code='j')";
				condition+=sm.getWhereString(qr, whereflag);
				whereflag = sm.whereflagChange(whereflag);
			}
		}
		
		sql+=condition;
		System.out.println("Workstat2 outbound : "+sql);
		
		if(type.equals("WEIGHT")){
			try {
				connect();
				rs = stmt.executeQuery(sql);
				int count=0;
				boolean errorflag=false;
				String tempErrorMsg="";
				String errorMessage="";
				while(rs.next()){
					count++;
					String code1 = rs.getString("code");
					if(code1 != null){
						
						String tempCode = code1;
						String tempArea = rs.getString("area");
						String tempNet=rs.getString("netWeight");
						String tempGross=rs.getString("grossWeight");
						String tempWeight="ok";
						String tempScac = rs.getString("scac"); 
						String tempGblNo = rs.getString("no");
						String tempCuft = rs.getString("cuft");
						 if(tempWeight!=null){
							if(tempCode.equals("3")||tempCode.equals("4")||tempCode.equals("5")||tempCode.equals("T")||tempCode.equals("t")||tempCode.equals("6")){
									tempWeight= sm.checkMinimumValue(tempNet, "HHG");
								}
							else if(tempCode.equals("J")||tempCode.equals("7")||tempCode.equals("8")||tempCode.equals("j")){
								tempWeight = sm.checkMinimumValue(tempGross, "UB");
							}
							else{
								System.out.println("???what code : "+tempCode);
								System.out.println("gbl no : "+tempGblNo);
							}
							if(tempCuft==null){
								tempCuft="0";
							}
							tempErrorMsg = wvs2.setWeightData(tempGblNo,tempCode,tempScac,1+"",tempWeight,tempCuft );
							if(!tempErrorMsg.equals("")){
								errorMessage+=tempErrorMsg+"\n";
								errorflag=true;
							}
						 }
					}
					else{
						System.out.println("null code : "+code1);
					}
				}
				System.out.println("size : "+count);
				if(errorflag==true){
					ep = new ErrorPopup("OUTBOUND ERROR LIST", errorMessage);
				}
				disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
			str = wvs2.getStr();
		}
		else if(type.equals("DENSITY")){
			try {
				connect();
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					String errorMessage="";
					String tempErrorMsg="1";
					boolean errorflag = false;
					if(rs.getString("code")!=null){
						String gblno = rs.getString("no");
						String tempCode = rs.getString("code").toUpperCase();
						String tempGross = rs.getString("grossWeight");
						String tempNet = rs.getString("netWeight");
						String tempScac = rs.getString("scac");
						String tempCuft = rs.getString("cuft");
//						String tempDensity = getDensity(tempGross, tempNet, tempCuft, tempCode);
						String tempDensity = "";
						String tempWeight="";
//						System.out.println("getOundBound-Density-code : "+tempCode+" gross : "+tempGross+" Net : "+tempNet+" SCAC : "+tempScac+" cuft : "+tempCuft);
						if(tempCode.equals("3")||tempCode.equals("4")||tempCode.equals("5")||tempCode.equals("T")||tempCode.equals("t")){
							tempWeight = sm.checkMinimumValue(rs.getString("netWeight"), "HHG");
							if(tempCuft==null){
								if(!tempErrorMsg.equals("")){
									errorMessage+="[ GBL NO : "+gblno+" ] [ CUFT ERROR ]"+"\n";
									errorflag = true;
								}
							}
							else{
								wvs2.setWeightData(gblno, tempCode, tempScac, 1+"", tempWeight,tempCuft);
							}
						}
						else if(tempCode.equals("7")||tempCode.equals("8")||tempCode.equals("J")||tempCode.equals("j")){
							tempWeight = sm.checkMinimumValue(rs.getString("grossWeight"), "UB");
							if(tempCuft==null){
								if(!tempErrorMsg.equals("")){
									errorMessage+="[ GBL NO : "+gblno+" ] [ CUFT ERROR ]"+"\n";
									errorflag = true;
								}
							}
							else{
								wvs2.setWeightData(gblno, tempCode, tempScac, 1+"", tempWeight,tempCuft);
							}
						}
						else{
							if(!tempErrorMsg.equals("")){
								errorMessage+="[ GBL NO : "+gblno+" ] [ CODE : "+tempCode+" ]"+"\n";
								errorflag = true;
							}
						}
						
					}
					if(errorflag==true){
						ep = new ErrorPopup("OUTBOUND ERROR LIST",errorMessage);
					}
				}
				disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
			str = wvs2.getStr();
			cuftStr = calcDenTotal(wvs2.getCuftStr());
//			wvs2.setCuftStr(cuftStr);
//			calcDensity(str,cuftStr);
//			for(int i=0;i<str.length;i++){
//				for(int j=0;j<str[i].length;j++){
//					System.out.print(str[i][j]+" ");
//				}
//				System.out.println();
//			}
//			for(int i=0;i<str.length;i++){
//				for(int j=0;j<str[i].length;j++){
//					System.out.print(cuftStr[i][j]+" ");
//				}
//				System.out.println();
//			}
		}
		
		return wvs2;
	}
	public String[][] getOutboundWorkVolumeStat2Cuft(String scac,String area,String hhgUb,String code,String begin,String end,String type){
		String[][] str = new String[ROW_LENGTH][20];
		String[][] cuftStr = new String[ROW_LENGTH][20];
		String[][] denStr = new String[ROW_LENGTH][20];
		String sql="";
		String condition="";
		String qr="";
		int whereflag=0;
		WorkVolumeStat2Beans wvs2 = new WorkVolumeStat2Beans();
		sql="select no,scac,code,area,pud,grossWeight,netWeight,cuft from gbl";
		if(!scac.equals("ALL")){
			qr = " gbl.scac='"+scac+"'";
			condition+=sm.getWhereString(qr, whereflag);
			whereflag = sm.whereflagChange(whereflag);
		}
		if(!code.equals("ALL")){
			qr = " gbl.code='"+code+"'";
			condition+=sm.getWhereString(qr, whereflag);
			whereflag = sm.whereflagChange(whereflag);
		}
		if(!area.equals("ALL")){
			qr = " gbl.area='"+area+"'";
			condition+=sm.getWhereString(qr, whereflag);
			whereflag = sm.whereflagChange(whereflag);
		}
		if(!begin.equals("") && !end.equals("")){
			qr = " date(gbl.pud) >= date_format('"+begin+"','%y-%m-%d') and date(gbl.pud) <= date_format('"+end+"','%y-%m-%d')";
			condition+=sm.getWhereString(qr, whereflag);
			whereflag = sm.whereflagChange(whereflag);
		}
		if(!hhgUb.equals("")){
			if(hhgUb.equals("HHG")){
				qr = " (gbl.code = '3' or gbl.code = '4' or gbl.code = '5' or gbl.code='T' or gbl.code='t')";
				condition+=sm.getWhereString(qr, whereflag);
				whereflag = sm.whereflagChange(whereflag);
			}
			else if(hhgUb.equals("UB")){
				qr = " (gbl.code = 'J' or gbl.code = '7' or gbl.code = '8' or gbl.code='j')";
				condition+=sm.getWhereString(qr, whereflag);
				whereflag = sm.whereflagChange(whereflag);
			}
		}
		
		sql+=condition;
		System.out.println("Workstat2 outbound : "+sql);
		if(type.equals("DENSITY")){
			try {
				connect();
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					String errorMessage="";
					String tempErrorMsg="1";
					boolean errorflag = false;
					if(rs.getString("code")!=null){
						String gblno = rs.getString("no");
						String tempCode = rs.getString("code").toUpperCase();
						String tempGross = rs.getString("grossWeight");
						String tempNet = rs.getString("netWeight");
						String tempScac = rs.getString("scac");
						String tempCuft = rs.getString("cuft");
//						String tempDensity = getDensity(tempGross, tempNet, tempCuft, tempCode);
						String tempDensity = "";
						String tempWeight="";
//						System.out.println("getOundBound-Density-code : "+tempCode+" gross : "+tempGross+" Net : "+tempNet+" SCAC : "+tempScac+" cuft : "+tempCuft);
						if(tempCode.equals("3")||tempCode.equals("4")||tempCode.equals("5")||tempCode.equals("T")||tempCode.equals("t")){
							tempWeight = sm.checkMinimumValue(rs.getString("netWeight"), "HHG");
							if(tempCuft==null){
							}
							else{
								wvs2.setWeightData(gblno, tempCode, tempScac, 1+"", tempWeight,tempCuft);
							}
						}
						else if(tempCode.equals("7")||tempCode.equals("8")||tempCode.equals("J")||tempCode.equals("j")){
							tempWeight = sm.checkMinimumValue(rs.getString("grossWeight"), "UB");
							if(tempCuft==null){
							}
							else{
								wvs2.setWeightData(gblno, tempCode, tempScac, 1+"", tempWeight,tempCuft);
							}
						}
						else{
						}
					}
					if(errorflag==true){
						ep = new ErrorPopup("OUTBOUND ERROR LIST",errorMessage);
					}
				}
				disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
			str = wvs2.getStr();
			cuftStr = wvs2.getCuftStr();
			calcDensity(str,cuftStr);
//			for(int i=0;i<str.length;i++){
//				for(int j=0;j<str[i].length;j++){
//					System.out.print(str[i][j]+" ");
//				}
//				System.out.println();
//			}
//			for(int i=0;i<str.length;i++){
//				for(int j=0;j<str[i].length;j++){
//					System.out.print(cuftStr[i][j]+" ");
//				}
//				System.out.println();
//			}
		}
		
		return str;
	}
	public String[][] getInboundWorkVolumeStat2Cuft(String scac,String area,String hhgUb,String code,String begin,String end,String type){
	String[][] str = new String[ROW_LENGTH][20];
	String[][] cuftStr = new String[ROW_LENGTH][20];
	String[][] denStr = new String[ROW_LENGTH][20];
	String gblno="";
	int whereflag=0;
	String sql="";
	String joinstr="";
	String condition="";
	String qr="";
//	System.out.println(scac);
	//INBOUND
	WorkVolumeStat2Beans wvs2 = new WorkVolumeStat2Beans();
	sql="select gblNo,tsp,code,area,pud,grossWeight,netWeight,cuft from gbl_ib";
	if(!scac.equals("ALL")){
		qr = " tsp = '"+scac+"'";
		condition += sm.getWhereString(qr, whereflag);
		whereflag = sm.whereflagChange(whereflag);
	}
	if(!code.equals("ALL")){
		qr = " code='"+code+"'";
		condition += sm.getWhereString(qr, whereflag);
		whereflag = sm.whereflagChange(whereflag);
	}
	if(!area.equals("ALL")){
		qr = " area='"+area+"'";
		condition+= sm.getWhereString(qr, whereflag);
		whereflag = sm.whereflagChange(whereflag);
	}
	if(!begin.equals("") && !end.equals("")){
		qr = " date(sitOut) >= date_format('"+begin+"','%y-%m-%d') and date(sitOut) <= date_format('"+end+"','%y-%m-%d')";
		condition+=sm.getWhereString(qr, whereflag);
		whereflag = sm.whereflagChange(whereflag);
	}
	if(!hhgUb.equals("")){
		if(hhgUb.equals("HHG")){
			qr = " (code = '3' or code = '4' or code = '5' or code='T' or code='t')";
			condition+= sm.getWhereString(qr, whereflag);
			whereflag = sm.whereflagChange(whereflag);
		}
		else if(hhgUb.equals("UB")){
			qr = " and (code = 'J' or code = '7' or code = '8' or code='j')";
			condition+= sm.getWhereString(qr, whereflag);
			whereflag = sm.whereflagChange(whereflag);
		}
	}
	if(!type.equals("")){
		if(type.equals("DENSITY")){
			String errorMessage="";
			String tempErrorMsg="1";
			boolean errorflag=false;
			sql+=condition;
			System.out.println("work volume inbound : "+sql);
			try {
				connect();
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					String tempGblNo = rs.getString("gblNo");
					String tempCode = rs.getString("code").toUpperCase();
					String tempArea = rs.getString("area");
					System.out.println("code : "+tempCode);
					System.out.println("area : "+tempArea);
					String tempGross = rs.getString("grossWeight");
					String tempNet = rs.getString("netWeight");
					String tempCuft = rs.getString("cuft");
					String tempScac = rs.getString("tsp");
					String tempWeight="";
//					String tempDensity = getDensity(tempGross, tempNet, tempCuft, rs.getString("code"));
					if(tempCode.equals("3")||tempCode.equals("4")||tempCode.equals("5")||tempCode.equals("T")||tempCode.equals("t")){
						tempWeight = sm.checkMinimumValue(rs.getString("netWeight"), "HHG");
						if(tempCuft==null){
							
						}
						else{
							wvs2.setWeightData(tempGblNo, tempCode, tempScac, 1+"", tempWeight,tempCuft);
						}
					}
					else if(tempCode.equals("7")||tempCode.equals("8")||tempCode.equals("J")||tempCode.equals("j")){
						tempWeight = sm.checkMinimumValue(rs.getString("grossWeight"), "UB");
						if(tempCuft==null){
							
						}
						else{
							wvs2.setWeightData(tempGblNo, tempCode, tempScac, 1+"", tempWeight,tempCuft);
						}
					}
					else{
						
					}
					
				}
				
				disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
			str = wvs2.getStr();
			cuftStr = wvs2.getCuftStr();
			cuftStr = calcDenTotal(cuftStr);
			System.out.println("SIZE : "+ROW_LENGTH);
			System.out.println("??? : "+str[0].length);
			str = cuftStr;
//			calcDensity(str,cuftStr);
//			for(int i=0;i<str.length;i++){
//				for(int j=0;j<str[i].length;j++){
//					System.out.print(str[i][j]+" ");
//				}
//				System.out.println();
//			}
			for(int i=0;i<str.length;i++){
				for(int j=0;j<str[i].length;j++){
					System.out.print(cuftStr[i][j]+" ");
				}
				System.out.println();
			}
		}
	}
	return str;
}
	public WorkVolumeStat2Beans getInboundWorkVolumeStat2(String scac,String area,String hhgUb,String code,String begin,String end,String type){
		String[][] str = new String[ROW_LENGTH][22];
		String[][] cuftStr = new String[ROW_LENGTH][22];
		String[][] denStr = new String[ROW_LENGTH][22];
		String gblno="";
		int whereflag=0;
		String sql="";
		String joinstr="";
		String condition="";
		String qr="";
//		System.out.println(scac);
		//INBOUND
		WorkVolumeStat2Beans wvs2 = new WorkVolumeStat2Beans();
		sql="select gblNo,tsp,code,area,pud,grossWeight,netWeight,cuft from gbl_ib";
		if(!scac.equals("ALL")){
			qr = " tsp = '"+scac+"'";
			condition += sm.getWhereString(qr, whereflag);
			whereflag = sm.whereflagChange(whereflag);
		}
		if(!code.equals("ALL")){
			qr = " code='"+code+"'";
			condition += sm.getWhereString(qr, whereflag);
			whereflag = sm.whereflagChange(whereflag);
		}
		if(!area.equals("ALL")){
			qr = " area='"+area+"'";
			condition+= sm.getWhereString(qr, whereflag);
			whereflag = sm.whereflagChange(whereflag);
		}
		if(!begin.equals("") && !end.equals("")){
			qr = " date(sitOut) >= date_format('"+begin+"','%y-%m-%d') and date(sitOut) <= date_format('"+end+"','%y-%m-%d')";
			condition+=sm.getWhereString(qr, whereflag);
			whereflag = sm.whereflagChange(whereflag);
		}
		if(!hhgUb.equals("")){
			if(hhgUb.equals("HHG")){
				qr = " (code = '3' or code = '4' or code = '5' or code='T' or code='t')";
				condition+= sm.getWhereString(qr, whereflag);
				whereflag = sm.whereflagChange(whereflag);
			}
			else if(hhgUb.equals("UB")){
				qr = " and (code = 'J' or code = '7' or code = '8' or code='j')";
				condition+= sm.getWhereString(qr, whereflag);
				whereflag = sm.whereflagChange(whereflag);
			}
		}
		if(!type.equals("")){
			if(type.equals("WEIGHT")){
				String errorMessage="";
				String tempErrorMsg="1";
				boolean errorflag = false;
				sql+=condition;
				System.out.println("Workstat2 inbound : "+sql);
				try {
					connect();
					rs = stmt.executeQuery(sql);
					while(rs.next()){
						String tempCode = rs.getString("code").toUpperCase();
						String tempArea = rs.getString("area");
						String tempScac = rs.getString("tsp");
						String tempGblNo = rs.getString("gblNo");
						String tempCuft = rs.getString("cuft");
						String tempWeight="";
						if(tempCode.equals("3")||tempCode.equals("4")||tempCode.equals("5")||tempCode.equals("T")||tempCode.equals("t")){
							tempWeight = sm.checkMinimumValue(rs.getString("netWeight"), "HHG");
						}
						else if(tempCode.equals("7")||tempCode.equals("8")||tempCode.equals("J")||tempCode.equals("j")){
							tempWeight = sm.checkMinimumValue(rs.getString("grossWeight"), "UB");
						}
						else{
							if(!tempErrorMsg.equals("")){
								errorMessage+="[ GBL NO : "+gblno+" ] [ CODE : "+tempCode+" ]"+"\n";
								errorflag = true;
							}
						}
						if(tempWeight!=null){
							tempErrorMsg = wvs2.setWeightData(tempGblNo,tempCode,tempScac, 1+"",tempWeight,tempCuft );
							if(!tempErrorMsg.equals("")){
								errorMessage+=tempErrorMsg+"\n";
								errorflag = true;
							}
						}
					}
					if(errorflag==true){
						ep = new ErrorPopup("INBOUND ERROR LIST",errorMessage);
					}
					disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
				str = wvs2.getStr();
				
				
			}
			else if(type.equals("DENSITY")){
				String errorMessage="";
				String tempErrorMsg="1";
				boolean errorflag=false;
				sql+=condition;
				System.out.println("work volume inbound : "+sql);
				try {
					connect();
					rs = stmt.executeQuery(sql);
					while(rs.next()){
						String tempGblNo = rs.getString("gblNo");
						String tempCode = rs.getString("code").toUpperCase();
						String tempArea = rs.getString("area");
						System.out.println("code : "+tempCode);
						System.out.println("area : "+tempArea);
						String tempGross = rs.getString("grossWeight");
						String tempNet = rs.getString("netWeight");
						String tempCuft = rs.getString("cuft");
						String tempScac = rs.getString("tsp");
						String tempWeight="";
//						String tempDensity = getDensity(tempGross, tempNet, tempCuft, rs.getString("code"));
						if(tempCode.equals("3")||tempCode.equals("4")||tempCode.equals("5")||tempCode.equals("T")||tempCode.equals("t")){
							tempWeight = sm.checkMinimumValue(rs.getString("netWeight"), "HHG");
							if(tempCuft==null){
								if(!tempErrorMsg.equals("")){
									errorMessage+="[ GBL NO : "+gblno+" ] [ CUFT ERROR ]"+"\n";
									errorflag = true;
								}
							}
							else{
								wvs2.setWeightData(tempGblNo, tempCode, tempScac, 1+"", tempWeight,tempCuft);
							}
						}
						else if(tempCode.equals("7")||tempCode.equals("8")||tempCode.equals("J")||tempCode.equals("j")){
							tempWeight = sm.checkMinimumValue(rs.getString("grossWeight"), "UB");
							if(tempCuft==null){
								if(!tempErrorMsg.equals("")){
									errorMessage+="[ GBL NO : "+gblno+" ] [ CUFT ERROR ]"+"\n";
									errorflag = true;
								}
							}
							else{
								wvs2.setWeightData(tempGblNo, tempCode, tempScac, 1+"", tempWeight,tempCuft);
							}
						}
						else{
							if(!tempErrorMsg.equals("")){
								errorMessage+="[ GBL NO : "+gblno+" ] [ CODE : "+tempCode+" ]"+"\n";
								errorflag = true;
							}
						}
						
					}
					if(errorflag==true){
						ep = new ErrorPopup("INBOUND ERROR LIST",errorMessage);
					}
					disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
				str = wvs2.getStr();
				cuftStr = wvs2.getCuftStr();
				cuftStr = calcDenTotal(cuftStr);
				wvs2.setCuftStr(cuftStr);
				System.out.println("SIZE : "+ROW_LENGTH);
				System.out.println("??? : "+str[0].length);
//				str = cuftStr;
//				calcDensity(str,cuftStr);
//				for(int i=0;i<str.length;i++){
//					for(int j=0;j<str[i].length;j++){
//						System.out.print(str[i][j]+" ");
//					}
//					System.out.println();
//				}
				for(int i=0;i<str.length;i++){
					for(int j=0;j<str[i].length;j++){
						System.out.print(cuftStr[i][j]+" ");
					}
					System.out.println();
				}
			}
		}
		
		return wvs2;
	}
	public String[][] calcDenTotal(String[][] input){
//		String[][] result = new String[ROW_LENGTH][22];
		for(int i=0;i<ROW_LENGTH;i++){
			int totalCuft=0;
			for(int j=0;j<22;j++){
				if(j==9){//hhg total
					input[i][j] = Integer.parseInt(input[i][1])+Integer.parseInt(input[i][3])+Integer.parseInt(input[i][5])+Integer.parseInt(input[i][7])+"";
				}
				else if(j== 19){//ub total
					input[i][j] = Integer.parseInt(input[i][11])+Integer.parseInt(input[i][13])+Integer.parseInt(input[i][15])+Integer.parseInt(input[i][17])+"";
				}
				else if(j==21){//total - job
					input[i][j] = Integer.parseInt(input[i][9])+Integer.parseInt(input[i][19])+"";
				}
			}//for (j)
		}//for(i)
		for(int i=0;i<22;i++){
			for(int j=0;j<18;j++){
				input[18][i] = Integer.parseInt(input[j][i])+Integer.parseInt(input[18][i])+"";
			}
		}
		return input;
	}
	public String[][] calcDensity(String[][] weight,String[][] cuft){
		System.out.println("CALCULATION DENSITY");
		String[][] result = new String[ROW_LENGTH][22];
		int w;
		int c;
		String d;
		DecimalFormat df = new DecimalFormat("####0.00");
		System.out.println("weight Length : "+weight.length);
		System.out.println("weight Length2 : "+weight[0].length);
		for(int i=0;i<weight.length;i++){
			for(int j=0;j<weight[i].length;j++){
				result[i][j]="-";
				if(j%2!=0){
					if(weight[i][j].equals("-") || cuft[i][j].equals("0")){
					}
					else{
						w = Integer.parseInt(weight[i][j]);
						c = Integer.parseInt(cuft[i][j]);
						d = df.format((double)w/(double)c);
						result[i][j] = d;
					}
				}
				System.out.print(result[i][j]+" ");
			}
			System.out.println();
		}
		return result;
	}
	
	public String getDensity(String gross,String net,String cuft, String code){
		String den="";
		DecimalFormat df = new DecimalFormat("####0.00");
		if(code.equals("3")||code.equals("4")||code.equals("5")||code.equals("T")){
			den = df.format(Double.parseDouble(net)/Double.parseDouble(cuft));
		}
		else if(code.equals("7")||code.equals("8")||code.equals("J")){
			den = df.format(Double.parseDouble(gross)/Double.parseDouble(cuft));
		}
		return den;
	}
	public int getIndexOfTsp(ArrayList<AllScacTotalInvoiceCollectionBeans> list,String tsp){
		int resultIndex=-1;
		AllScacTotalInvoiceCollectionBeans bean = new AllScacTotalInvoiceCollectionBeans();
		for(int i=0;i<list.size();i++){
			bean = list.get(i);
			String tempTsp = bean.getCarrier();
			if(tempTsp.equals(tsp)){
				resultIndex = i;
			}
		}
		return resultIndex;
	}
	public String[] getAllScacTotalInvoiceCollectionModelRow(AllScacTotalInvoiceCollectionBeans bean){
		String[] result=new String[6];
		result[0] = bean.getTotalAmount();
		result[1] = bean.getDepositAmount();
		result[2] = bean.getUnCollectedAmount();
		result[3] = bean.getAcceptAmount();
		result[4] = bean.getClaimeAmount();
		result[5] = bean.getNetCollectedAmount();
		return result;
	}
	public String[] getAllScacTotalInvoiceCollectionModelTotalRow(ArrayList<AllScacTotalInvoiceCollectionBeans> list){
		String[] result=new String[6];
		double to=0.0;
		double co=0.0;
		double un=0.0;
		double ac=0.0;
		double cl=0.0;
		double ne=0.0;
		for(int i=0;i<list.size();i++){
			to += getDoubleValue(list.get(i).getTotalAmount());
			co += getDoubleValue(list.get(i).getDepositAmount());
			un += getDoubleValue(list.get(i).getUnCollectedAmount());
			ac += getDoubleValue(list.get(i).getAcceptAmount());
			cl += getDoubleValue(list.get(i).getClaimeAmount());
			ne += getDoubleValue(list.get(i).getNetCollectedAmount());
		}
		result[0] = to+"";
		result[1] = co+"";
		result[2] = un+"";
		result[3] = ac+"";
		result[4] = cl+"";
		result[5] = ne+"";
		
		return result;
	}
	public String[][] getFinalModelRows(ArrayList<AllScacTotalInvoiceCollectionBeans> list){
   		String[][] finalStr = new String[getScacList().size()+1][6];
   		AllScacTotalInvoiceCollectionBeans bean = new AllScacTotalInvoiceCollectionBeans();
   		ArrayList<String> tspList = getScacList();
   		for(int i=0;i<getScacList().size()+1;i++){
   			for(int j=0;j<6;j++){
   				finalStr[i][j] = "-";
   			}
   		}
   		for(int i=0;i<list.size();i++){
   			bean = list.get(i);
   			int k = tspList.indexOf(bean.getCarrier());
   			if(k!=-1){
   				finalStr[k] = getAllScacTotalInvoiceCollectionModelRow(bean); 
   			}
   		}
   		finalStr[getScacList().size()] = getAllScacTotalInvoiceCollectionModelTotalRow(list);
   		return finalStr;
   	}
	public String[][] getAllScacTotalInvoice(String begin,String end){
		AllScacTotalInvoiceCollectionBeans bean;
		ArrayList<AllScacTotalInvoiceCollectionBeans> asticbList = new ArrayList<>();
		String[][] list = new String[ROW_LENGTH][COLUMN_LENGTH];
		AllScacTotalInvoiceBeans atib = new AllScacTotalInvoiceBeans();
		System.out.println("DATE begin : "+begin);
		System.out.println("date end : "+end);
		int whereFlag=0;
		String sql="select il.tsp as tsp, "+
					"il.process as process, "+
					"il.invoice_date as invoiceDate, "+ 
					"igcf.state as state, "+
					"sum(round(igcf.amount,2)) as collectedAmount "+ 
					"from invoice_list as il "+
					"left join invoice_gbl as ig on il.seq = ig.invoice_list_seq "+
					"left join invoice_gbl_collection as igc on ig.seq = igc.invoice_gbl_seq "+
					"left join invoice_gbl_collection_flow as igcf on igc.seq = igcf.invoice_gbl_collection_seq "+
					"where 1=1 "+
					"and igcf.state != 'null' ";
		String condition="";
		if(!begin.equals("") && !end.equals("")){
				condition = "and date(il.invoice_date) >= date_format('"+begin+"','%y-%m-%d') and date(il.invoice_date) <= date_format('"+end+"','%y-%m-%d') ";
		}
		condition+="group by il.tsp, igcf.state ";
		sql+=condition;
		System.out.println("sql1 : "+sql);
		try {
			connect();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
//				String invoicedAmounts = checkAmountsNull(rs.getString("amount"));
				String tsp = rs.getString("tsp");
				String state = rs.getString("state");
				String collectedAmount = rs.getString("collectedAmount");
				int index = getIndexOfTsp(asticbList, tsp);
				if(index!=-1){
					asticbList.get(index).setValue(state, collectedAmount);
					System.out.println("UPDATE LIST : "+tsp+" state : "+state+" amount : "+collectedAmount);
				}
				else if(index==-1){
					bean = new AllScacTotalInvoiceCollectionBeans();
					bean.setCarrier(tsp);
					bean.setValue(state, collectedAmount);
					asticbList.add(bean);
					System.out.println("CREATE LIST : "+tsp+" state : "+state+" amount : "+collectedAmount);
				}
				else{
					System.out.println("Error");
				}
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		whereFlag=0;
		String sql2="select il.tsp as tsp,sum(round(il.amount,2)) as totalAmount from invoice_list as il group by tsp;";
//		condition="";
//		if(!begin.equals("") && !end.equals("")){
//			condition = " and date(invoice_list.write_date) >= date_format('"+begin+"','%y-%m-%d') and date(invoice_list.write_date) <= date_format('"+end+"','%y-%m-%d') ";
//		}
//		sql2+=condition;
		System.out.println("sql2 : "+sql2);
		try {
			connect();
			rs = stmt.executeQuery(sql2);
			while(rs.next()){
				String tsp = rs.getString("tsp");
				String totalAmount = rs.getString("totalAmount");
				int index = getIndexOfTsp(asticbList, tsp);
				if(index!=-1){
					asticbList.get(index).setValue("TOTAL", totalAmount);
					System.out.println("UPDATE LIST : "+tsp+" state : TOTAL amount : "+totalAmount);
					asticbList.get(index).calcurateValue();
				}
				else if(index==-1){
					bean = new AllScacTotalInvoiceCollectionBeans();
					bean.setCarrier(tsp);
					bean.setValue("TOTAL", totalAmount);
					asticbList.add(bean);
					int index2 = asticbList.size()-1;
					asticbList.get(index2).calcurateValue();
					System.out.println("CREATE LIST : "+tsp+"state : Total  amount : "+totalAmount);
				}
//				String tsp = rs.getString("tsp");
//				String state = rs.getString("state");
//				String stateAmount = rs.getString("amount");
//				System.out.println("state : "+state);
//				System.out.println("amount : "+stateAmount);
//				if(state.equals("ACCEPT")){
//					atib.setValue(tsp, "0.0", "0.0", "0.0", stateAmount, "0.0", "0.0");
//				}
//				else if(state.equals("CLAIM")){
//					atib.setValue(tsp, "0.0", "0.0", "0.0", "0.0", stateAmount, "0.0");
//				}
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		for(int i=0;i<atib.getStr().length;i++){
//				for(int j=0;j<atib.getStr()[i].length;j++){
//					System.out.print(atib.getStr()[i][j]+" ");
//				}
//				System.out.println();
//		}
		String[][] result = getFinalModelRows(asticbList);
		return result;
	}
	public ArrayList<AllScacTotalInvoiceCollectionStatusGblBeans> getInboundAllScacTotalInvoiceCollcetionStatusGbl(String scac,String code,String begin,String end){
		ArrayList<AllScacTotalInvoiceCollectionStatusGblBeans> list = new ArrayList<>();
		AllScacTotalInvoiceCollectionStatusGblBeans bean = new AllScacTotalInvoiceCollectionStatusGblBeans();
		String sql="select	invoice_gbl.gbl_no as gblNo, invoice_gbl.amount as amount, invoice_list.start_date as start, invoice_list.end_date as end,"+
				   "invoice_gbl.name as name,gbl_ib.code as code,gbl_ib.tsp as scac, invoice_gbl_collection.difference as difference,"+
				   "invoice_gbl_collection.state as state, invoice_gbl_collection_flow.state as collectionState, invoice_gbl_collection_flow.amount collectionAmount,"+
				   "invoice_list.invoice_date as invoiceDate "
				   + "from	invoice_list left join invoice_gbl on invoice_list.seq = invoice_gbl.invoice_list_seq"
				   + "	left join gbl_ib on invoice_gbl.gbl_seq = gbl_ib.seq"
				   + "	left join invoice_gbl_collection on invoice_gbl_collection.invoice_gbl_seq = invoice_gbl.seq"
				   + "	left join invoice_gbl_collection_flow on invoice_gbl_collection.seq = invoice_gbl_collection_flow.invoice_gbl_collection_seq"
				   + " where invoice_list.process = 'inbound'";
		if(!scac.equals("ALL")){
			sql+=" and gbl_ib.tsp='"+scac+"'";
		}
		if(!code.equals("ALL")){
			sql+=" and gbl_ib.code='"+code+"'";
		}
		if(!begin.equals("") && !end.equals("")){
			sql+=" and date(invoice_list.invoice_date) >= date('"+begin+"') and date(invoice_list.invoice_date) <= date('"+end+"')";
		}
		System.out.println("SQL : "+sql);
		try {
			connect();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				String scac1 = rs.getString("scac");
				String gblNo = rs.getString("gblNo");
				String amount = rs.getString("amount");
				String name = rs.getString("name");
				String code1 = rs.getString("code");
				String difference = rs.getString("difference");
				double tempDiff = getDoubleValue(difference);
				if(tempDiff <0){
					System.out.println("tmepDiff@@@@@@@@@@@@@@@@@@@@@  : "+tempDiff);
					tempDiff *= -1;
				}
				if(tempDiff!=0){
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ : "+tempDiff);
				}
				String state1 = rs.getString("state");
				String collectionState = rs.getString("collectionState");
				String collectionAmount = rs.getString("collectionAmount");
//				System.out.println("SCAC : "+scac1+" GBL NO : "+gblNo+" AMOUNT : "+amount+" NAME : "+name+" COLLECTION STATE : "+collectionState);
				bean = new AllScacTotalInvoiceCollectionStatusGblBeans();
				bean.setScac(scac1);
				bean.setGblNo(gblNo);
				bean.setAmount(amount);
				bean.setName(name);
				bean.setCode(code1);
				bean.setDifference(tempDiff+"");
				bean.setState(state1);
				bean.setProcess("inbound");
				if(collectionState==null || collectionState.equals("")){
					System.out.println(bean.getGblNo()+ "STATE NULL ADD uncollectedAmount : "+amount);
					System.out.println("@@@@@@@@@@@@@@@@@@@@@1 : "+tempDiff);
					bean.setDifference("0.0");
					bean.setUncollectedAmount(amount);
				}
				else if(collectionState.equals("ACCEPT")){
					System.out.println("ACCEPT AMOUNT : "+collectionAmount);
					bean.setAcceptAmount(collectionAmount);
				}
				else if(collectionState.equals("CLAIM")){
					System.out.println("CLAIM AMOUNT : "+collectionAmount);
					bean.setClaimeAmount(collectionAmount);
				}
				else if(collectionState.equals("DEPOSIT")){
					System.out.println("COLLECTED AMOUNT : "+collectionAmount);
					bean.setCollectionAmount(collectionAmount);
				}
				list.add(bean);
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public double getDoubleValue(String a){
		double d;
		if(a ==null || a.equals("")){
			d = 0.0;
		}
		else{
			d = Double.parseDouble(a);
		}
		return d;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public ArrayList<EachScacAcceptedAmountStatusGblBean> getInboundAcceptedGbl(String scac, String begin, String end){
		ArrayList<EachScacAcceptedAmountStatusGblBean> list = new ArrayList<>();
		EachScacAcceptedAmountStatusGblBean bean = new EachScacAcceptedAmountStatusGblBean();
		String sql="select	invoice_gbl.gbl_no as gblNo, invoice_gbl.amount as amount,"+
				   "gbl_ib.code as code,gbl_ib.tsp as scac, invoice_gbl_collection.difference as difference,"+
				   "invoice_gbl_collection.state as state, invoice_gbl_collection_flow.state as collectionState, invoice_gbl_collection_flow.amount collectionAmount,"+
				   "invoice_list.invoice_date as invoiceDate,invoice_gbl_collection_flow.remark as reason "
				   + "from	invoice_list left join invoice_gbl on invoice_list.seq = invoice_gbl.invoice_list_seq"
				   + "	left join gbl_ib on invoice_gbl.gbl_seq = gbl_ib.seq"
				   + "	left join invoice_gbl_collection on invoice_gbl_collection.invoice_gbl_seq = invoice_gbl.seq"
				   + "	left join invoice_gbl_collection_flow on invoice_gbl_collection.seq = invoice_gbl_collection_flow.invoice_gbl_collection_seq"
				   + " where invoice_list.process = 'inbound'"
				   + " and invoice_gbl_collection_flow.state='ACCEPT'";
		if(!scac.equals("ALL")){
			sql+=" and gbl_ib.tsp='"+scac+"'";
		}
		if(!begin.equals("") && !end.equals("")){
			sql+=" and date(invoice_list.invoice_date) >= date('"+begin+"') and date(invoice_list.invoice_date) <= date('"+end+"')";
		}
		System.out.println("SQL IN : "+sql);
		try {
			connect();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				bean = new EachScacAcceptedAmountStatusGblBean();
				String gblNo = rs.getString("gblNo");
				String invoiceAmounts = rs.getString("amount");
				String acceptedAmounts = rs.getString("collectionAmount");
				String invoiceDate = rs.getString("invoiceDate");
				String reason = rs.getString("reason");
				String code = rs.getString("code");
				System.out.println("GBL : "+gblNo+" ACCEPTEAMOUNT : "+acceptedAmounts);
				bean.setInvoiceDate(invoiceDate);
				bean.setGblNo(gblNo);
				bean.setInvoiceAmounts(invoiceAmounts);
				bean.setAcceptedAmounts(acceptedAmounts);
				bean.setReason(reason);
				bean.setCode(code);
				list.add(bean);
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public ArrayList<EachScacAcceptedAmountStatusGblBean> getOutboundAcceptedGbl(String scac, String begin, String end){
		ArrayList<EachScacAcceptedAmountStatusGblBean> list = new ArrayList<>();
		EachScacAcceptedAmountStatusGblBean bean = new EachScacAcceptedAmountStatusGblBean();
		String sql="select	invoice_gbl.gbl_no as gblNo, invoice_gbl.amount as amount, invoice_list.start_date as start, invoice_list.end_date as end,"+
				   "invoice_gbl.name as name,gbl.code as code,gbl.scac as scac, invoice_gbl_collection.difference as difference,"+
				   "invoice_gbl_collection.state as state, invoice_gbl_collection_flow.state as collectionState, invoice_gbl_collection_flow.amount collectionAmount,"+
				   "invoice_list.invoice_date as invoiceDate,invoice_gbl_collection_flow.remark as reason "
				   + "from	invoice_list left join invoice_gbl on invoice_list.seq = invoice_gbl.invoice_list_seq"
				   + "	left join gbl on invoice_gbl.gbl_seq = gbl.seq"
				   + "	left join invoice_gbl_collection on invoice_gbl_collection.invoice_gbl_seq = invoice_gbl.seq"
				   + "	left join invoice_gbl_collection_flow on invoice_gbl_collection.seq = invoice_gbl_collection_flow.invoice_gbl_collection_seq"
				   + " where invoice_list.process = 'outbound'"
				   + " and invoice_gbl_collection_flow.state='ACCEPT'";
		if(!scac.equals("ALL")){
			sql+=" and gbl.scac='"+scac+"'";
		}
		if(!begin.equals("") && !end.equals("")){
			sql+=" and date(invoice_list.invoice_date) >= date('"+begin+"') and date(invoice_list.invoice_date) <= date('"+end+"')";
		}
		System.out.println("SQL OUT : "+sql);
		try {
			connect();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				bean = new EachScacAcceptedAmountStatusGblBean();
				String gblNo = rs.getString("gblNo");
				String invoiceAmounts = rs.getString("amount");
				String acceptedAmounts = rs.getString("collectionAmount");
				String invoiceDate = rs.getString("invoiceDate");
				String reason = rs.getString("reason");
				String code = rs.getString("code");
				System.out.println("GBL : "+gblNo+" ACCEPTEAMOUNT : "+acceptedAmounts);
				bean.setInvoiceDate(invoiceDate);
				bean.setGblNo(gblNo);
				bean.setInvoiceAmounts(invoiceAmounts);
				bean.setAcceptedAmounts(acceptedAmounts);
				bean.setReason(reason);
				bean.setCode(code);
				list.add(bean);
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public ArrayList<AllScacTotalInvoiceCollectionStatusGblBeans> getOutboundAllScacTotalInvoiceCollcetionStatusGbl(String scac,String code,String begin,String end){
		ArrayList<AllScacTotalInvoiceCollectionStatusGblBeans> list = new ArrayList<>();
		AllScacTotalInvoiceCollectionStatusGblBeans bean = new AllScacTotalInvoiceCollectionStatusGblBeans();
		String sql="select	invoice_gbl.gbl_no as gblNo, invoice_gbl.amount as amount, invoice_list.start_date as start, invoice_list.end_date as end,"+
				   "invoice_gbl.name as name,gbl.code as code,gbl.scac as scac, invoice_gbl_collection.difference as difference,"+
				   "invoice_gbl_collection.state as state, invoice_gbl_collection_flow.state as collectionState, invoice_gbl_collection_flow.amount collectionAmount,"+
				   "invoice_list.invoice_date as invoiceDate "
				   + "from	invoice_list left join invoice_gbl on invoice_list.seq = invoice_gbl.invoice_list_seq"
				   + "	left join gbl on invoice_gbl.gbl_seq = gbl.seq"
				   + "	left join invoice_gbl_collection on invoice_gbl_collection.invoice_gbl_seq = invoice_gbl.seq"
				   + "	left join invoice_gbl_collection_flow on invoice_gbl_collection.seq = invoice_gbl_collection_flow.invoice_gbl_collection_seq"
				   + " where invoice_list.process = 'outbound'";
		if(!scac.equals("ALL")){
			sql+=" and gbl.scac='"+scac+"'";
		}
		if(!code.equals("ALL")){
			sql+=" and gbl.code='"+code+"'";
		}
		if(!begin.equals("") && !end.equals("")){
			sql+=" and date(invoice_list.invoice_date) >= date('"+begin+"') and date(invoice_list.invoice_date) <= date('"+end+"')";
		}
		System.out.println("SQL : "+sql);
		try {
			connect();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				String scac1 = rs.getString("scac");
				String gblNo = rs.getString("gblNo");
				String amount = rs.getString("amount");
				String name = rs.getString("name");
				String code1 = rs.getString("code");
				String difference = rs.getString("difference");
				double tempDiff = getDoubleValue(difference);
				if(tempDiff <0){
					System.out.println("tmepDiff@@@@@@@@@@@@@@@@@@@@@  : "+tempDiff);
					tempDiff *= -1;
				}
				if(tempDiff!=0){
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ : "+tempDiff);
				}
				String state1 = rs.getString("state");
				String collectionState = rs.getString("collectionState");
				String collectionAmount = rs.getString("collectionAmount");
				bean = new AllScacTotalInvoiceCollectionStatusGblBeans();
				bean.setGblNo(gblNo);
				bean.setScac(scac1);
				bean.setAmount(amount);
				bean.setName(name);
				bean.setCode(code1);
				bean.setDifference(tempDiff+"");
				bean.setState(state1);
				bean.setProcess("outbound");
				
				if(collectionState==null || collectionState.equals("")){
					System.out.println("STATE NULL ADD uncollectedAmount : "+amount);
					bean.setDifference("0.0");
					bean.setUncollectedAmount(amount);
				}
				else if(collectionState.equals("ACCEPT")){
					System.out.println("ACCEPT AMOUNT : "+collectionAmount);
					bean.setAcceptAmount(collectionAmount);
				}
				else if(collectionState.equals("CLAIM")){
					System.out.println("CLAIM AMOUNT : "+collectionAmount);
					bean.setClaimeAmount(collectionAmount);
				}
				else if(collectionState.equals("DEPOSIT")){
					System.out.println("COLLECTED AMOUNT : "+collectionAmount);
					bean.setCollectionAmount(collectionAmount);
				}
				list.add(bean);
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<InvoiceCollectionStatusByPaidBeans> getInvoiceCollectionStatusByPaid(String begin,String end, String scac, String gbl_No){
		String sql="SELECT invoice_list.invoice_no as invoiceNo,invoice_list.invoice_date as invoiceDate, invoice_list.tsp as tsp, invoice_gbl.gbl_no as gblNo, invoice_gbl.amount as totalAmount, invoice_gbl_collection_flow.amount as inAmount,invoice_gbl_collection_flow.date as date "+
					"from invoice_list "+
					"left join invoice_gbl on invoice_list.seq = invoice_gbl.invoice_list_seq "+
					"left join invoice_gbl_collection on invoice_gbl.seq = invoice_gbl_collection.invoice_gbl_seq "+
					"left join invoice_gbl_collection_flow on invoice_gbl_collection.seq = invoice_gbl_collection_flow.invoice_gbl_collection_seq "+
					"where invoice_gbl_collection_flow.date != 'null' ";
		if(!begin.equals("") && !end.equals("")){
			sql+="and date >= date('"+begin+"') and date<=date('"+end+"') ";
		}
		if(!scac.equals("ALL")){
			sql+="and tsp = '"+scac+"' ";
		}
		if(!gbl_No.equals("")){
			sql+="and invoice_gbl.gbl_no ='"+gbl_No+"' ";
		}
		sql+="order by date";
		System.out.println(sql);
		ArrayList<InvoiceCollectionStatusByPaidBeans> invoiceList = new ArrayList<InvoiceCollectionStatusByPaidBeans>();
		InvoiceCollectionStatusByPaidBeans icsp;
		InvoiceCollectionStatusByPaidGblBeans icspg;
		ArrayList<String> checkList = new ArrayList<>();
		try {
			connect();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				String invoiceNo = rs.getString("invoiceNo");
				String tsp = rs.getString("tsp");
				String gblNo = rs.getString("gblNo");
				String totalAmount = rs.getString("inAmount");
				String date = rs.getString("date");
				String invoiceDate = rs.getString("invoiceDate");
				String paidAmount = rs.getString("inAmount");
				int index = checkList.indexOf(date+"@"+invoiceNo);
				if(index==-1){//there is no matching
					checkList.add(date+"@"+invoiceNo);
					icsp = new InvoiceCollectionStatusByPaidBeans();
					icspg = new InvoiceCollectionStatusByPaidGblBeans();
					icsp.setDate(date);
					icsp.setTsp(tsp);
					icsp.setInvoiceDate(invoiceDate);
					icsp.setInvoiceNo(invoiceNo);
					/*********gbl setting*********/
					icspg = getICSPG(gblNo, totalAmount, paidAmount);
					/*****************************/
					icsp.addGbl(icspg);
					invoiceList.add(icsp);
				}
				else{//already exist this matching
					icspg = new InvoiceCollectionStatusByPaidGblBeans();
					/*********gbl setting*********/
					icspg = getICSPG(gblNo, totalAmount, paidAmount);
					/*****************************/
					invoiceList.get(index).addGbl(icspg);
				}
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return invoiceList;
//		for(int i=0;i<invoiceList.size();i++){
//			System.out.println("------------------------------------");
//			System.out.println("INVOICE NO : "+invoiceList.get(i).getInvoiceNo()+" INVOICE DATE : "+invoiceList.get(i).getInvoiceDate()+" PAID DATE : "+invoiceList.get(i).getDate());
//			for(int j=0;j<invoiceList.get(i).getGblList().size();j++){
//				System.out.println("GBL NO : "+invoiceList.get(i).getGblList().get(j).getGblNo()+" TOTAL AMOUNT : "+invoiceList.get(i).getGblList().get(j).getAmount()+" Paid : "+invoiceList.get(i).getGblList().get(j).getPaidAmount());
//			}
//		}
	}
	public InvoiceCollectionStatusByPaidGblBeans getICSPG(String gblNo,String total,String paid){
		InvoiceCollectionStatusByPaidGblBeans icspg = new InvoiceCollectionStatusByPaidGblBeans();
		icspg.setAmount(total);
		icspg.setGblNo(gblNo);
		icspg.setPaidAmount(paid);
		return icspg;
	}
//	public Boolean inventoryInput(){
//		
//	}
	public void modifyAdmin(String originId,String originPw,String newId,String newPw,String newlevel){
		String sql="update stat_user set id='"+newId+"',pw='"+newPw+"', level='"+newlevel+"' where id='"+originId+"' and pw='"+originPw+"'";
		System.out.println(sql);
		try {
			connect();
			stmt.executeUpdate(sql);
			System.out.println("[[ MODIFY SUCCESS ]]");
		}catch(Exception e){
			
		}
	}
	
	public void deleteAdmin(String originId,String originPw){
		String sql="delete from stat_user where id='"+originId+"' and pw='"+originPw+"'";
		System.out.println(sql);
		try {
			connect();
			stmt.executeUpdate(sql);
			System.out.println("[[ DELETE SUCCESS ]]");
		}catch(Exception e){
			
		}
	}
	public void addUser(String id,String pw,String level){
		String sql="insert into stat_user (id,pw,level) values('"+id+"','"+pw+"','"+level+"')";
		System.out.println(sql);
		try {
			connect();
			stmt.executeUpdate(sql);
			System.out.println("[[ ADDUSER SUCCESS ]]");
		}catch(Exception e){
			
		}
	}
	
	
	
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
