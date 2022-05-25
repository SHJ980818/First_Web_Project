package sp;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;

public class ConnUtil {

	private static DataSource ds = null;
	{
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/myOracle");
		} catch (Exception e) {
			System.err.println("Connection 실패");
		}
	}

	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	private static ConnUtil instance = null;

	public static ConnUtil getInstance() {
		synchronized (ConnUtil.class) { // 동기화 처리
			if (instance == null) {
				instance = new ConnUtil();
			}
		}
		return instance;
	}

//	   }
//	
//	   private DataSource ds;
//	   
//	   private ConnUtil() {
//	      try{
//	           Context init = (Context) new InitialContext();
//	           ds = (DataSource)init.lookup("java:comp/env/jdbc/myOracle");
//	       } catch(Exception e){
//	           System.out.println("Exception : " + e);
//	       }
//	   }
//	   
//	   public static ConnUtil getInstance() {
//	      synchronized(ConnUtil.class) { //동기화 처리
//	         if(instance == null) {
//	            instance = new ConnUtil();
//	         }
//	      }
//	      return instance;
//	   }
//	   public Connection getConnection() throws SQLException {
//	      return ds.getConnection();
//	   }

}