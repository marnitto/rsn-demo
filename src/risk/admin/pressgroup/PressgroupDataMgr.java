package risk.admin.pressgroup;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class PressgroupDataMgr {
	
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private PreparedStatement pstmt = null;
	private StringBuffer sb = null;	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	
	int totalCnt = 0;
	public int getTotalCnt() {
		return totalCnt;
	}
	
	

	public ArrayList getPressgroupDataList(int nowpage, int cnt, String sDate, String eDate, String sTime, String eTime, String searchKey)
    {
		ArrayList result = new ArrayList();
		    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT COUNT(*) AS CNT \n");
			sb.append("FROM PRESS_RELEASE     \n");
			sb.append("WHERE P_DATE BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"'\n");
			
			if(!searchKey.equals("")){
				String[] arStr = searchKey.split(" ");
				for(int i = 0; i < arStr.length; i ++){
					sb.append("AND P_TITLE LIKE '%"+arStr[i]+"%' \n");
				}
			}

			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
				
			if(rs.next()){
				totalCnt = rs.getInt("CNT");
			}
			
			
			sb = new StringBuffer();
			sb.append("SELECT P_SEQ															\n");
			sb.append(", P_TITLE																\n");
			sb.append(", P_DATE																\n");
			sb.append(", P_DEPT																\n");
			sb.append(", P_REGDATE																\n");
			sb.append("FROM PRESS_RELEASE															\n");
			sb.append("WHERE P_DATE BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"'	\n");
			if(!searchKey.equals("")){
				String[] arStr = searchKey.split(" ");
				for(int i = 0; i < arStr.length; i ++){
					sb.append("AND P_TITLE LIKE '%"+arStr[i]+"%' 								\n");
				}
			}
			sb.append("ORDER BY P_SEQ DESC														\n");
			sb.append("LIMIT "+((nowpage-1) * cnt)+","+cnt+"									\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			
			PressgroupDataBean bean = null;
			while(rs.next()){
				bean = new PressgroupDataBean();
				bean.setPg_seq(rs.getString("P_SEQ"));
				bean.setPg_title(rs.getString("P_TITLE"));
				bean.setPg_date(rs.getString("P_DATE"));
				bean.setPg_dept(rs.getString("P_DEPT"));
				bean.setPg_regdate(rs.getString("P_REGDATE"));
				result.add(bean);
			}
			
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	public ArrayList getPressgroupDataList_Simple(String sDate, String eDate, String sTime, String eTime)
    {
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT P_SEQ															\n");								
			sb.append("     , P_TITLE															\n");							
			sb.append("  FROM PRESS_RELEASE														\n");						
			sb.append(" WHERE P_DATE BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"'	\n");	
			sb.append(" ORDER BY P_SEQ DESC													\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
		
			PressgroupDataBean iBean = null;
			while(rs.next()){
				iBean = new PressgroupDataBean();
				iBean.setPg_seq(rs.getString("P_SEQ"));
				iBean.setPg_title(rs.getString("P_TITLE"));
				result.add(iBean);
			}
			
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	
	public PressgroupDataBean getPressgroupDataDetail(String pg_seq)
    {
		PressgroupDataBean result = new PressgroupDataBean();
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			
			sb = new StringBuffer();
			sb.append("SELECT P_SEQ				\n");
			sb.append("     , P_TITLE				\n");
			sb.append("     , P_CONTENTS			\n");
			sb.append("     , P_DATE				\n");
			sb.append("     , P_REGDATE			\n");
			sb.append("  FROM PRESS_RELEASE			\n");
			sb.append(" WHERE P_SEQ = "+pg_seq+"	\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			
			while(rs.next()){
				result.setPg_seq(rs.getString("P_SEQ"));
				result.setPg_title(rs.getString("P_TITLE"));
				result.setPg_content(rs.getString("P_CONTENTS"));
				result.setPg_date(rs.getString("P_DATE"));
				result.setPg_regdate(rs.getString("P_REGDATE"));
			}
			
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	
	public ArrayList InsertPressgroupData(PressgroupDataBean bean)
    {
		ArrayList result = new ArrayList();
		    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			
			
			sb = new StringBuffer();
			sb.append("INSERT INTO PRESS_RELEASE (				\n");
			sb.append("	 P_TITLE							\n");
			sb.append(", P_DEPT							\n");
			sb.append(", P_CONTENTS							\n");
			sb.append(", P_DATE							\n");
			sb.append(", P_REGDATE) VALUES (?,?,?,?,NOW())	\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			pstmt.clearParameters();
			int idx = 0;
			pstmt.setString(++idx, bean.getPg_title());
			pstmt.setString(++idx, bean.getPg_dept());
			pstmt.setString(++idx, bean.getPg_content());
			pstmt.setString(++idx, bean.getPg_date());
			pstmt.executeUpdate();
			
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	public ArrayList UpdatePressgroupData(PressgroupDataBean bean)
    {
		ArrayList result = new ArrayList();
		    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("UPDATE PRESS_RELEASE 	\n");
			sb.append("SET P_TITLE = ?		\n");
			sb.append(", P_DEPT = ?		\n");
			sb.append(", P_CONTENTS = ?		\n");
			sb.append(", P_DATE = ?		\n");
			sb.append(", P_REGDATE = NOW()	\n");
			sb.append("WHERE P_SEQ = "+ bean.getPg_seq() +"		\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			pstmt.clearParameters();
			int idx = 0;
			pstmt.setString(++idx, bean.getPg_title());
			pstmt.setString(++idx, bean.getPg_dept());
			pstmt.setString(++idx, bean.getPg_content());
			pstmt.setString(++idx, bean.getPg_date());
			pstmt.executeUpdate();
			
			
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	public ArrayList DeletePressgroupData(String pg_seq)
    {
		ArrayList result = new ArrayList();
		    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("DELETE FROM PRESS_RELEASE WHERE P_SEQ = "+pg_seq+"\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			pstmt.executeUpdate();
			
			
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
}