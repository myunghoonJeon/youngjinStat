/*
 * Copyright (c) 2011, RTOS Laboratory in Kyonggi University
 * All rights reserved.
 * ----------------------------------------------------------
 * ������������ : 11/03/30
 * �ӱ�ȣ�� ���� ���������Ǿ���ϴ�.
 * DAO Patten�� ���� �������̽� �ۼ�.
 * �������̽����� ��� �� �޼ҵ�� ����.
 * ----------------------------------------------------------
*/


public interface IT_DAO {
	public void connect() throws Exception;
	public void disconnect() throws Exception;
//	public int maxMessageNumber(String board) throws Exception;
//	public void writeMessage(String board,CL_DataBean data) throws Exception;
//	public ArrayList<CL_DataBean> readMessage(String board,int lowNumber,int highNumber) throws Exception;
}
