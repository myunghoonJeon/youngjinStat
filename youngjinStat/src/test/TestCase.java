package test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import mainpages.CL_DAO_DB_Mysql;
import mainpages.userBean;

import org.junit.Test;

public class TestCase {
	private CL_DAO_DB_Mysql dao = new CL_DAO_DB_Mysql();
	private userBean testUserBean = new userBean();
	
	@Test
	public void test() {
		testUserBean = dao.userAccessValidateCheck("1", "1");
		assertThat(null,is(not(testUserBean)) );
//		ArrayList<String> areaList = dao.getAreaList();
//		assertThat(areaList.size(), is(5));
	}

}
