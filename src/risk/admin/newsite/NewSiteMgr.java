/**
 * 작성자 : 박정호
 * 작성일 : 2007.12.16
 * DESC : 	RSN의 MYSQL서버로 부터 신규 사이트 리스트를 LOCAL DB로 전송한다.
 * 			전송된 번호는 TRANSFER TABLE에 저장하고 
 * 			risk.properites 파일의 NewSiteTransferNo 변수에 작업된 번호를 저장하고 
 * 			다음 전송시 사용한다.
 * 			프로세스는 1일 1회 02~06시 사이에 실행한다.
 */

package risk.admin.newsite;

import java.sql.*;
import java.util.ArrayList;

import risk.DBconn.DBconn;
import risk.util.Log;
import risk.util.DateUtil;


public class NewSiteMgr {

    private static StringBuffer sb = new StringBuffer();
    
//  local db connection
    private static DBconn  dbConn  = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
	
// total record count of new_site by search keyword
    private int nsTotalCount = 0;

    /**
     * return new site total count
     * @return int total count
     */
    public int getNsTotalCount() {
    	return this.nsTotalCount;
    }
    
    /**
     * return new site list by keyword, page number
     * @param piPageNum : now page num
     * @param piPageCnt : list count per page
     * @param psSearchKey : keyword to search from new site title
     */
	public ArrayList getNewSiteList(int piPageNum, int piPageCnt, String psSearchKey, String piNsno) {
		
		ArrayList arrRtn = new ArrayList();
		NewSiteBean nsBean = null;
		
		// parameters initialize
		if (piPageNum<1) piPageNum = 1;
		if (piPageCnt<1) piPageCnt = 10;
		
		try {
			dbConn = new DBconn();
			dbConn.getDBCPConnection();

			stmt = dbConn.createStatement();
			
			// get total count by search keyword
			sb = new StringBuffer();
			sb.append("SELECT COUNT(0) AS CNT								\n");
			sb.append("FROM NEW_SITE 										\n");
			if (psSearchKey.length()>0) {
				sb.append("		WHERE NS_TITLE LIKE '%"+psSearchKey+"%'		\n");
			}
			rs = stmt.executeQuery( sb.toString() );
			// set total count
			if (rs.next()) {
				this.nsTotalCount = rs.getInt("CNT");
			}
			
			// get new site list by keyword, page number
			sb = new StringBuffer();
			sb.append("SELECT NS_NO, NS_NAME, NS_DATE, NS_URL, NS_TITLE, NS_SENDCHK, NS_SENDDATE, NS_SENDTIME FROM							\n");
			sb.append("		(SELECT 																	\n");
			sb.append("			NS_NO, NS_NAME, NS_DATE, NS_URL, NS_TITLE, NS_SENDCHK, NS_SENDDATE, NS_SENDTIME,								\n"); 
			sb.append("			((ROW_NUMBER() OVER(ORDER BY NS_NO DESC)-1)/"+piPageCnt+")+1 AS PAGE	\n");
			sb.append("		FROM NEW_SITE																\n");
			if(piNsno.length()>0){
				sb.append("		WHERE NS_NO IN ('"+piNsno+"')									\n");
			}
			if (psSearchKey.length()>0) {
				sb.append("		WHERE NS_TITLE LIKE '%"+psSearchKey+"%'									\n");
			}
			sb.append("		) AS A																		\n");
			sb.append("WHERE PAGE = "+piPageNum+"														\n");			
			
			//System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			
			while (rs.next())
			{
				// create new site bean and put data from result set
				nsBean = new NewSiteBean();
				
				nsBean.setNsNo(rs.getInt("NS_NO"));
				nsBean.setNsName(rs.getString("NS_NAME"));
				nsBean.setNsDate(rs.getString("NS_DATE"));
				nsBean.setNsURL(rs.getString("NS_URL"));
				nsBean.setNsTitle(rs.getString("NS_TITLE"));
				nsBean.setNsSendChk(rs.getString("NS_SENDCHK"));
				nsBean.setNsSendDate(rs.getString("NS_SENDDATE"));
				nsBean.setNsSendTime(rs.getString("NS_SENDTIME"));
				
				
				// add to return arraylist
				arrRtn.add( nsBean );
			}
			
			/*if(piNsno.length()>0){
				sb = new StringBuffer();
				sb.append("		UPDATE NEW_SITE									\n");
				sb.append("		SET NS_SENDCHK ('y')									\n");
				sb.append("		SET NS_SENDDATE ('y')									\n");
				sb.append("		SET NS_SENDTIME ('y')									\n");
				sb.append("		WHERE NS_NO IN ('"+piNsno+"')									\n");
				
			}*/
			
		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch (Exception ex) {
        	Log.writeExpt(ex);
		} finally {
            try { if( dbConn  != null) dbConn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
		return arrRtn;
	}
	
	/**
     * delete selected site 
     * @param delStr : selected delete list "1,2,3,4"
     * @return null
     */
	public void deleteNewSite(String psDelStr) {
		
		try {
			dbConn = new DBconn();
			dbConn.getDBCPConnection();

			stmt = dbConn.createStatement();
			
			// delete new sites
			sb = new StringBuffer();
			sb.append("DELETE FROM NEW_SITE WHERE NS_NO IN ("+psDelStr+")	\n");
			
			stmt.executeUpdate( sb.toString() );
			
		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch (Exception ex) {
        	Log.writeExpt(ex);
		} finally {
            try { if( dbConn  != null) dbConn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
		return;
	}
	
	public void updateNewSite(String psNsno, String psMseq) {

		DateUtil du = new DateUtil();
		String date = "";
		String time = "";
		date = du.getCurrentDate("yyyyMMDD");
		time = du.getCurrentDate("HHmm");

		try {
			dbConn = new DBconn();
			dbConn.getDBCPConnection();

			stmt = dbConn.createStatement();
			
			// delete new sites
			sb = new StringBuffer();
			sb.append("UPDATE NEW_SITE  	\n");
			sb.append("SET NS_SENDCHK = 'y' 	\n");
			sb.append(",NS_SENDDATE = '"+date+"' 	\n");
			sb.append(",NS_SENDTIME = '"+time+"' 	\n");
			if(psMseq.length()>0){
				sb.append(",M_SEQ = '"+psMseq+"' 	\n");
			}
			sb.append(" WHERE NS_NO IN ("+psNsno+")	\n");
			
			
			//System.out.println(sb.toString());
			stmt.executeUpdate( sb.toString() );
			
		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch (Exception ex) {
        	Log.writeExpt(ex);
		} finally {
            try { if( dbConn  != null) dbConn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
		return;
	}
}