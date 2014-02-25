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



public class CL_DAO_DB_Mysql implements IT_DAO{
	private String jdbc_driver = "com.mysql.jdbc.Driver";
	private String jdbc_url = "jdbc:mysql://203.249.22.70:3306/youngjin";
	private Connection conn;
	private Statement stmt;
	private String sql="";
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
