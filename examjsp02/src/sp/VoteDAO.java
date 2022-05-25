package sp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VoteDAO {
//	private ConnUtil pool;
//	Connection conn = null;
//	PreparedStatement pstmt = null;
//	private static VoteDAO instance = null;
//
//	private VoteDAO() {
//		pool = ConnUtil.getInstance();
//	} // 생성자
//		// 생성자를 private 으로 선언했으므로 useBean으로 생성 불가
//
//	public static VoteDAO getInstance() {
//		if (instance == null) {
//			synchronized (VoteDAO.class) {
//				instance = new VoteDAO();
//			}
//		}
//		return instance;
//
//	}

	public void InsertForm(String sub) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ConnectionPool pool = ConnectionPool.getInstance();
		try {
			conn = pool.getConnection();
			String sql = "insert into title(item) values(?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sub);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					pool.releaseConnection(conn);
				} catch (SQLException e) {
				}
		}
	}

	public List<VoteDTO> selectAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ConnectionPool pool = ConnectionPool.getInstance();

		List<VoteDTO> ls = new ArrayList<>();
		ResultSet rs = null;
		try {
			conn = pool.getConnection();
			String sql = "SELECT * FROM TITLE";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				VoteDTO tmp = new VoteDTO(rs.getString(1), rs.getInt(2));
				ls.add(tmp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					pool.releaseConnection(conn);
				} catch (SQLException e) {
			}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
			}
		}
		return (ls.size() == 0) ? null : ls;
	}
	public void countPlus(String sub) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ConnectionPool pool = ConnectionPool.getInstance();
		try {
			conn = pool.getConnection();
			String sql = "update title set count=count+1 where item=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sub);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					pool.releaseConnection(conn);
				} catch (SQLException e) {
				}
		}
	}
	public void itemdelete(String movie) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ConnectionPool pool = ConnectionPool.getInstance();
		try {
			conn = pool.getConnection();
			String sql = "delete from title where item = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, movie);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					pool.releaseConnection(conn);
				} catch (SQLException e) {
				}
		}
	}
}
/*
 * private int getMaxNum() { return 0; }
 * 
 * //Max Item Count: 아이템 중에 가장 높은 투표값 //select max(count) from tblPollitem where
 * listNum=? public int getMaxcount(int listNum) { Connection conn = null;
 * PreparedStatement pstmt = null; ResultSet rs = null; String sql = null; int
 * maxCnt = 0; try { conn = ConnUtil.getConnection(); sql =
 * "select max(count) from MaxCount where listNum=?"; pstmt =
 * conn.prepareStatement(sql); if(listNum==0) listNum=getMaxNum();
 * pstmt.setInt(1, listNum); rs = pstmt.executeQuery(); if(rs.next())
 * maxCnt=rs.getInt(1); } catch (Exception e) { e.printStackTrace(); } finally {
 * if(rs != null) try {rs.close();}catch(SQLException e) {} if(pstmt != null)try
 * {pstmt.close();}catch(SQLException e) {} if(conn != null)try
 * {conn.close();}catch(SQLException e) {} } } return MaxCount; } // Poll
 * Insert(설문작성) public boolean InsertForm() { Connection conn = null;
 * PreparedStatement pstmt = null; ResultSet rs = null; String sql = null;
 * boolean flag = false; try { conn = ConnUtil.getConnection(); sql =
 * "insert tblPollList(question,  " + "values(?,?,?,now(),?)"; pstmt =
 * conn.prepareStatement(sql); pstmt.setString(1, getQuestion());
 * pstmt.setString(2, vlBean.getSdate()); pstmt.setString(3, vlBean.getEdate());
 * pstmt.setInt(4, vlBean.getType()); //1:복수, 0:단일 int cnt =
 * pstmt.executeUpdate(); if(cnt==1) { //save sql =
 * "insert tblPollItem values(?,?,?,0)"; pstmt = conn.prepareStatement(sql);
 * String item[] = viBean.getItem(); int listNum = getMaxNum(); int j = 0; for
 * (int i = 0; i < item.length; i++) { //space
 * if(item[i]==null||item[i].trim().equals("")) break; pstmt.setInt(1, listNum);
 * pstmt.setInt(2, i); pstmt.setString(3, item[i]); j = pstmt.executeUpdate();
 * }//--for if(j==1) flag=true; } } catch (Exception e) { e.printStackTrace(); }
 * finally { if(rs != null) try {rs.close();}catch(SQLException e) {} if(pstmt
 * != null)try {pstmt.close();}catch(SQLException e) {} if(conn != null)try
 * {conn.close();}catch(SQLException e) {} } } return flag; }
 * 
 * // Sum Count (총 투표한 수) public int SumCount(int listNum) { Connection conn =
 * null; PreparedStatement pstmt = null; ResultSet rs = null; String sql = null;
 * int sum = 0; try { conn = ConnUtil.getConnection(); sql =
 * "select sum(count) from tblPollItem where listnum=?"; pstmt =
 * conn.prepareStatement(sql); if(listNum==0) listNum = getMaxNum(); //가장 최신의 설문
 * pstmt.setInt(1, listNum); rs = pstmt.executeQuery(); if(rs.next()) sum =
 * rs.getInt(1); } catch (Exception e) { e.printStackTrace(); } finally { if(rs
 * != null) try {rs.close();}catch(SQLException e) {} if(pstmt != null) try
 * {pstmt.close();}catch(SQLException e) {} if(conn != null) try
 * {conn.close();}catch(SQLException e){} } return sum; }
 * 
 * // Vote(투표실행) public boolean Vote(int listNum, String itemNum[]) { Connection
 * conn = null; PreparedStatement pstmt = null; ResultSet rs = null; String sql
 * = null; boolean flag = false; try { conn = ConnUtil.getConnection(); sql =
 * "update Vote set count=count+1" + "where listnum=? and itemnum=?"; //2가지 조건
 * pstmt = conn.prepareStatement(sql); if(listNum==0) { listNum = getMaxNum(); }
 * for (int i = 0; i < itemNum.length; i++) { pstmt.setInt(1, listNum);
 * pstmt.setInt(2, Integer.parseInt(itemNum[i])); if(pstmt.executeUpdate()==1) {
 * flag = true; } } } catch (Exception e) { e.printStackTrace(); } finally {
 * if(rs != null) try {rs.close();}catch(SQLException e) {} if(pstmt != null)
 * try {pstmt.close();}catch(SQLException e) {} if(conn != null) try
 * {conn.close();}catch(SQLException e){} } return flag; }
 * 
 * // VoteResult (투표결과) public getVoteResult(int listNum) { Connection conn =
 * null; PreparedStatement pstmt = null; ResultSet rs = null; String sql = null;
 * try { conn = ConnUtil.getConnection(); sql =
 * "select item, count from tblPollItem where listnum=?"; pstmt =
 * conn.prepareStatement(sql); if(listNum==0) { listNum = getMaxNum(); }
 * pstmt.setInt(1, listNum); rs = pstmt.executeQuery(); while(rs.next()) {
 * VoteResult vr = new VoteResult(); String item[] = new String[1];
 * item[0]=rs.getString("item"); vr.setItem(item);
 * vr.setCount(rs.getInt("count")); vlist.addElement(vr); } } catch (Exception
 * e) { e.printStackTrace(); } finally { if(rs != null) try
 * {rs.close();}catch(SQLException e) {} if(pstmt != null) try
 * {pstmt.close();}catch(SQLException e) {} if(conn != null) try
 * {conn.close();}catch(SQLException e){} } return vlist; } }
 */
