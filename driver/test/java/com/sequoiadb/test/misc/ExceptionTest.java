package com.sequoiadb.test.misc;

import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.sequoiadb.base.CollectionSpace;
import com.sequoiadb.base.DBCollection;
import com.sequoiadb.base.DBCursor;
import com.sequoiadb.base.Sequoiadb;
import com.sequoiadb.exception.BaseException;
import com.sequoiadb.exception.SDBError;
import com.sequoiadb.net.ConfigOptions;
import com.sequoiadb.test.common.*;


public class ExceptionTest {
	
	private static Sequoiadb sdb ;
	private static CollectionSpace cs ;
	private static DBCollection cl ;
	private static DBCursor cursor ;
	
	@BeforeClass
	public static void setConnBeforeClass() throws Exception{
		sdb = new Sequoiadb(Constants.COOR_NODE_CONN,"","");
	}
	
	@AfterClass
	public static void DropConnAfterClass() throws Exception {
		sdb.disconnect();
	}
	
	@Before
	public void setUp() throws Exception {
		if(sdb.isCollectionSpaceExist(Constants.TEST_CS_NAME_1)){
			sdb.dropCollectionSpace(Constants.TEST_CS_NAME_1);
		}
	    cs = sdb.createCollectionSpace(Constants.TEST_CS_NAME_1);
		BSONObject conf = new BasicBSONObject();
		conf.put("ReplSize", 0);
		cl = cs.createCollection(Constants.TEST_CL_NAME_1, conf);
	}

	@After
	public void tearDown() throws Exception {
		sdb.dropCollectionSpace(Constants.TEST_CS_NAME_1);
	}
	
	@Test
	public void TestEquals() {
		Assert.assertEquals(true, SDBError.SDB_INVALIDARG.equals(SDBError.SDB_INVALIDARG));
		Assert.assertEquals(false, SDBError.SDB_SYS.equals(SDBError.SDB_INVALIDARG));
	}
	
	@Test
	public void TestDeprecatedExceptionAPI() {
		try {
			ConfigOptions opt = new ConfigOptions();
			opt.setMaxAutoConnectRetryTime(0);
			opt.setConnectTimeout(100);
			Sequoiadb db = new Sequoiadb("123","","", opt);
			Assert.fail();
		} catch(BaseException e) {
			System.out.println("case 1's result:");
			System.out.println("error type: " + e.getErrorType());
			System.out.println("error code: " + e.getErrorCode());
			System.out.println("error desc: " + e.getMessage());
			System.out.println("error stack: ");
			e.printStackTrace();
			Assert.assertEquals(SDBError.SDB_NETWORK.getErrorType(), e.getErrorType());
			Assert.assertEquals(SDBError.SDB_NETWORK.getErrorCode(), e.getErrorCode());
			Assert.assertEquals("SDB_NETWORK(-15): Network error", e.getMessage());
		}
		

	}
	
	@Test
	public void TestFormalExceptionAPI() {
		try {
			try {
				ConfigOptions opt = new ConfigOptions();
				opt.setMaxAutoConnectRetryTime(0);
				opt.setConnectTimeout(100);
				Sequoiadb db = new Sequoiadb("123","","", opt);
				Assert.fail();
			} catch(BaseException e) {
				throw new BaseException(SDBError.SDB_SYS, "this is a test message", e);
			}
		} catch(BaseException e) {
			System.out.println("result of TestFormalExceptionAPI is:");
			System.out.println("error type: " + e.getErrorType());
			System.out.println("error code: " + e.getErrorCode());
			System.out.println("error desc: " + e.getMessage());
			System.out.println("error stack: ");
			e.printStackTrace();
			Assert.assertEquals(SDBError.SDB_SYS.getErrorType(), e.getErrorType());
			Assert.assertEquals(SDBError.SDB_SYS.getErrorCode(), e.getErrorCode());
			Assert.assertEquals("SDB_SYS(-10): System error, detail: this is a test message", e.getMessage());
		}
		
	}


}
