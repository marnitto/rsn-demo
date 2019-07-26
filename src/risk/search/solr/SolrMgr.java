/**
========================================================
주 시 스 템 : RSN
서브 시스템 :
프로그램 ID : siteDataInfo
프로그램 명 : 전체검색 이슈관련 데이터 class
프로그램개요 : 
작 성 자 : 양진솔
작 성 일 : 2011. 04. 21
========================================================
수정자/수정일:
수정사유/내역:
========================================================
*/
package risk.search.solr;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import risk.DBconn.DBconn;
import risk.util.Log;


public class SolrMgr {

    DBconn dbconn   = null;
    Statement stmt  = null;
    ResultSet rs    = null;

    StringBuffer sb = null;    
    String sQuery   = "";
    String BookMarkQuery = null;
    
    public String[] getSiteGroup(String sSeq){
    	
    	String[] arrResult = new String[4];
    	
    	try {	
    		sb = new StringBuffer();
    		sb.append("SELECT IFNULL(MAX(B.SG_SEQ),0)   AS SG_SEQ   \n");
    		sb.append("     , IFNULL(MAX(C.SG_NAME),'') AS SG_NAME  \n");
    		sb.append("	    , IFNULL(MAX(B.S_SEQ),0)    AS S_SEQ    \n");
    		sb.append("	    , IFNULL(MAX(B.S_NAME),'')  AS S_NAME   \n");
    		sb.append("  FROM SITE          A                       \n");
    		sb.append("     , SG_S_RELATION B                       \n");
    		sb.append("	    , SITE_GROUP    C                       \n");
    		sb.append(" WHERE A.S_SEQ    = B.S_SEQ                  \n");
    		sb.append("   AND B.SG_SEQ   = C.SG_SEQ                 \n");
    		sb.append("   AND A.S_ACTIVE = '1'                      \n");
    		sb.append("   AND B.S_SEQ    = "+ sSeq +"               \n");
    		
    		System.out.println(sb.toString());
    		
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		
    		stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            
            if(rs.next()){
            	
            	for(int i = 0; i < arrResult.length; i++){
            		arrResult[i] = rs.getString(i+1);
            	}
            }
            
            sb = null;
    		
    	} catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
    	return arrResult;
    }
    
    public boolean SetIssueSolr(String idSeq, String docid){
    	
    	boolean result = false;
    	
    	try {	
    		sb = new StringBuffer();
    		
    		sb.append("INSERT INTO ISSUE_SOLR ( ID_SEQ          \n");
    		sb.append("                       , DOCID       )   \n");
    		sb.append("				   VALUES ( "+ idSeq +"     \n");
    		sb.append("				          , "+ docid +" )   \n");
    		
    		System.out.println(sb.toString());
    		
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		
    		stmt = dbconn.createStatement();
            
            if(stmt.executeUpdate(sb.toString()) > 0){
            	result = true;
            }
 
            sb = null;
    		
    	} catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
    	return result;
    }
    
    
    public ArrayList getIssueSolr(String date){
    	
    	ArrayList arrResult = new ArrayList();
    	
    	try {	
    		sb = new StringBuffer();
    			
    		sb.append("SELECT B.DOCID                                                       \n");
    		sb.append("  FROM ISSUE_DATA A                                                  \n");
    		sb.append("	    , ISSUE_SOLR B                                                  \n");
    		sb.append("	WHERE A.ID_SEQ = B.ID_SEQ                                           \n");
    		sb.append("	  AND A.MD_DATE BETWEEN '"+date+"000000' AND '"+date+"235959'       \n");
    		
    		System.out.println(sb.toString());
    		
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		
    		stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            
            while(rs.next()){
        		arrResult.add(rs.getString("DOCID"));
            }
            sb = null;
    		
    	} catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
    	return arrResult;
    }

}