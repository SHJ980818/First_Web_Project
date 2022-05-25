package sp;

import java.sql.*;
import java.util.ArrayList;

public class ConnectionPool {
	private ArrayList<Connection> freed; // 사용 전 Connection
	private ArrayList<Connection> used; // 사용 중 Connection
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "c##tester";
	private String password = "1234";
	private int initCons = 10; // 초기 Connection 수
	private int maxCons = 20; // 최대 Connection 수
	private int numCons = 0; // 전체 Connection 수
	private static ConnectionPool cp;

	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static ConnectionPool getInstance() {
		try {
			if (cp == null) {
				synchronized (ConnectionPool.class) {
					cp = new ConnectionPool();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cp;
	}

	private ConnectionPool() throws SQLException {
		freed = new ArrayList<Connection>(initCons);
		used = new ArrayList<Connection>(initCons);
		// initCons 의 수만큼 Connection생성
		while (numCons < initCons) {
			addConnection();
		}
	}

	private void addConnection() throws SQLException {
		freed.add(getNewConnection());
	}

	private Connection getNewConnection() throws SQLException {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("About to connect to " + conn);
		numCons++;
		return conn;
	}

	// 요청이 왔을 때 freed에 있는 Connection을 used로 옮기는 기능
	public synchronized Connection getConnection() throws SQLException {
		if (freed.isEmpty()) {
			while (numCons < maxCons) {
				addConnection();
			}
		}
		Connection _conn = freed.get(freed.size() - 1);
		freed.remove(_conn);
		used.add(_conn);
		return _conn;
	}

	// 요청 처리가 끝나면 used의 Connection객체를 freed로 옮기는 기능
	public synchronized void releaseConnection(Connection _conn) throws SQLException {
		boolean flag = false;
		if (used.contains(_conn)) {
			used.remove(_conn);
			numCons--;
			flag = true;
		} else {
			throw new SQLException("ConnectionPool에 없습니다. ");
		}
		try {
			if (flag) {
				freed.add(_conn);
				numCons++;
			} else {
				_conn.close();
			}
		} catch (SQLException e) {
			try {
				_conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	// 모든 Connection 삭제
	public void closeAll() {
		// 사용 중인 모든 Connection객체 삭제
		for (int i = 0; i < used.size(); i++) {
			Connection _conn = used.get(i);
			used.remove(i--);
			try {
				_conn.close();
			} catch (SQLException e) {
				try {
					_conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		// 사용 전인 모든 Connection객체 삭세
		for (int i = 0; i < freed.size(); i++) {
			Connection _conn = freed.get(i);
			freed.remove(i--);
			try {
				_conn.close();
			} catch (SQLException e) {
				try {
					_conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public int getNumCons() {
		return numCons;
	}
}