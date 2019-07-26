package risk.demon;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;

public class DAEGUSummaryPortalData_v2 {
	
	static DBconn dbconn = null;
	static String name = "DAEGUSummaryPortalData_v2";
    private StringBuffer sb = null;
    private Statement stmt = null;
    private ResultSet rs    = null;
	private PreparedStatement pstmt = null;
	
	static String p_docid = "";
	
	static String last_update_seq = "";
	
	public static void main(String args[]){
		
		Log.crond(name,"DAEGUSummaryPortalData_v2 START ...");
    	
    	try{
    		
    		dbconn = new DBconn(); //로컬디비
    		dbconn.getSubDirectConnection();
			
    		DateUtil du = new DateUtil();
    		String today = du.getCurrentDate("yyyy-MM-dd");
    		DAEGUSummaryPortalData_v2 ssp = new DAEGUSummaryPortalData_v2();
    		ssp.setPortalData(today);
    		
    	}catch(Exception ex) {
    		Log.writeExpt(name, ex.getMessage());
        	ex.printStackTrace();
        	if (dbconn != null) try { dbconn.close(); } catch(SQLException e) {}
        	System.exit(1);
        }finally {
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
    	
    	Log.crond(name,"DAEGUSummaryPortalData_v2 END ...");
	    
	}
	

	public void setPortalData(String today){
		try {
			
			//dbconn = new DBconn();
			//dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			sb.append(" INSERT INTO SUMMARY_PORTAL                                                                                                      \n");
			sb.append("  SELECT A.D_SEQ , A.MD_SITE_NAME, A.MD_TITLE, A.MD_URL, A.MD_DATE, 0, 0, 0, 0, A.S_SEQ                                          \n");
			sb.append("  FROM META A                                                                                                                    \n");
			sb.append("  WHERE A.S_SEQ IN (2196,2199,3883,4537)                                                                                         \n");
			sb.append("    AND A.MD_DATE BETWEEN '"+today+" 00:00:00' AND '"+today+" 23:59:59'                                                        \n");
			sb.append("    AND A.D_SEQ NOT IN (SELECT D_SEQ FROM SUMMARY_PORTAL WHERE MD_DATE BETWEEN '"+today+" 00:00:00' AND '"+today+" 23:59:59');	\n"); 
		    System.out.println(sb.toString());
		    stmt.executeUpdate(sb.toString());
				
		} catch(SQLException ex) {
			Log.crond(name, ex.getMessage());
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.crond(name, ex.getMessage());
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    //if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		
	}
}
