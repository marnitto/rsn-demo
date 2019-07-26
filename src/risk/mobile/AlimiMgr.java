/**
========================================================
주 시 스 템 : RSN
서브 시스템 :
프로그램 ID : siteDataInfo
프로그램 명 : 사이트그룹 데이터 class
프로그램개요 : 사이트그룹 Data Beans
작 성 자 : 윤석준
작 성 일 : 2006. 04. 11
========================================================
수정자/수정일:
수정사유/내역:
========================================================
*/
package risk.mobile;
/// SSL

import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

//import risk.util.StringUtil;
import risk.DBconn.DBconn;
import risk.util.Log;

import risk.mobile.AlimiBean;

public class AlimiMgr {

    DBconn dbconn   = null;
    Statement stmt  = null;
    ResultSet rs    = null;
    

    StringBuffer sb = null;
    
    public int totalCount = 0;
    public int pageCount = 0;


    
    public ArrayList getAlimiLog( String pTel, String pMalType, int nowPage, int pPagSize)
    {
    	ArrayList arrResult = new ArrayList();
    	StringBuffer sb = null;
    
    	try{
    		
    		dbconn = new DBconn();
	        dbconn.getDBCPConnection();
	        stmt = dbconn.createStatement();

    		sb = new StringBuffer();
    		
	        /*
    		sb.append("SELECT A.MAL_SEQ   \n");
    		sb.append(", A.SD_NAME        \n");
    		sb.append(", A.SD_MENU        \n");
    		sb.append(", A.MT_TITLE       \n");
    		sb.append("sb.append(", A.MT_DATE \n");
    		sb.append(", A.MT_URL \n");
			sb.append(", A.MT_NO \n");
			sb.append(", A.MAL_SEND_DATE \n");
			sb.append("FROM (SELECT * FROM MALIMI_LOG WHERE MAL_SEND_DATE = curdate()) A \n");
			sb.append("WHERE A.MAL_TYPE = '6' \n");
			sb.append("AND REPLACE(AB_MOBILE, '-','')= '01063962287' \n");
	        */
    		
    		sb.append("SELECT COUNT(*) AS COUNT                                  \n");
            sb.append("  FROM MALIMI_LOG A USE INDEX(IDX_MAILIMI_LOG_SEND_DATE)  \n");
	        sb.append(" WHERE  A.MAL_TYPE = '"+pMalType+"'                        \n");
		    sb.append("   AND REPLACE(AB_MOBILE, '-','') = '"+pTel+"'            \n");
		    //sb.append("   LIMIT "+(nowPage-1)*pPagSize+", "+pPagSize+"           \n");
		    
		    Log.writeExpt("MOBILE_ALIMI_SEND_LOG", sb.toString());
		    
		    rs = stmt.executeQuery(sb.toString());
		    if(rs.next()){
		    	totalCount = rs.getInt("COUNT");
		    	pageCount = totalCount / pPagSize;
		    }
		    
		    rs.close();
	        rs = null;
	        sb = null;
	        sb = new StringBuffer();
    		
    		sb.append("SELECT A.MAL_SEQ       AS MAL_SEQ                                       \n");
			sb.append("	    , A.SD_NAME       AS SD_NAME                                       \n");
			sb.append("     , A.SD_MENU       AS SD_MENU                                       \n");
			sb.append("     , A.MT_TITLE      AS MT_TITLE                                      \n");
			sb.append("     , A.MT_DATE       AS MT_DATE                                       \n");
			sb.append("     , A.MT_URL        AS MT_URL                                        \n");
			sb.append("     , A.MT_NO         AS MT_NO                                         \n");
			sb.append("     , A.MAL_SEND_DATE AS MAL_SEND_DATE                                 \n");
			sb.append("     , A.MT_CONTENT    AS MT_CONTENT                                    \n");
            sb.append("  FROM MALIMI_LOG A USE INDEX(IDX_MAILIMI_LOG_SEND_DATE)\n");
	        sb.append(" WHERE A.MAL_TYPE = '"+pMalType+"'                      \n");
		    sb.append("   AND REPLACE(AB_MOBILE, '-','') = '"+pTel+"'          \n");
		    sb.append("   ORDER BY MT_DATE DESC          \n");
		    sb.append("   LIMIT "+(nowPage-1)*pPagSize+", "+pPagSize+"           \n");
		    	
	        
	        Log.writeExpt("MOBILE_ALIMI_SEND_LOG", sb.toString());
	        
	        rs = stmt.executeQuery(sb.toString());
	        
	        while(rs.next()){
	        	
	        	AlimiBean aBean = new AlimiBean(); 
	        	
	        	aBean.setMalSeq(rs.getString("MAL_SEQ"));
	        	aBean.setSdName(rs.getString("SD_NAME"));
	        	aBean.setSdMenu(rs.getString("SD_MENU"));
	        	aBean.setMtTitle(rs.getString("MT_TITLE"));
	        	aBean.setMtDate(rs.getString("MT_DATE"));
	        	aBean.setMtUrl(rs.getString("MT_URL"));
	        	aBean.setMtNo(rs.getString("MT_NO"));
	        	aBean.setMalSendDate(rs.getString("MAL_SEND_DATE"));
	        	aBean.setMtContent(rs.getString("MT_CONTENT"));

	        	arrResult.add(aBean);
	        }
	        
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        
        return arrResult;
    }
}