package risk.admin.alimi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

import risk.DBconn.DBconn;
import risk.util.Log;

public class AlimiLogMgr {
	
	DBconn dbconn   = null;
    Statement stmt  = null;
    ResultSet rs    = null;
    StringBuffer sb = null;
    
    int FullCnt = 0;
    public int getFullCnt() {
		return FullCnt;
	}


	public ArrayList getAlimiLogList(int nowpage, int rowCnt, String sDate, String eDate, String mal_type, String searchKey, String as_type, String as_seq) {
    	ArrayList result = new ArrayList();
    	AlimiLogSuperBean bean = new AlimiLogSuperBean();
    	AlimiLogSuperBean.AlimiLogList childBean = null;
    	
        try {

        	
        	dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();
            
        	sb = new StringBuffer();
        	sb.append("SELECT COUNT(*) AS CNT FROM (SELECT DISTINCT MT_NO, SEND_MESSAGE, MT_URL, MT_DATE, AS_SEQ, AS_TITLE, MAL_TYPE,  MAL_SEND_DATE , AS_TYPE FROM MALIMI_LOG WHERE MT_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'	\n");
        	if(!as_seq.equals("")){
        		sb.append("        AND AS_SEQ = "+as_seq+"														\n");
        	}
        	if(!as_type.equals("")){
        		sb.append("        AND AS_TYPE = "+as_type+"														\n");
        	}
        	if(!mal_type.equals("")){
        		sb.append("        AND MAL_TYPE = "+mal_type+"														\n");
        	}
            if(!searchKey.equals("")){
        		sb.append(" AND SEND_MESSAGE LIKE '%"+searchKey+"%'													\n");
        	}
            sb.append("          GROUP BY AS_TYPE, MT_NO 													  			\n");
        	sb.append("   )A 																																	\n");
        	
        	
        	System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());        
        	
            if(rs.next()){
            	FullCnt = rs.getInt("CNT");
            }
            
            int index = (nowpage-1) * rowCnt;
            
            sb = new StringBuffer();
            sb.append("SELECT A.*																		  			\n");
            sb.append("     , (SELECT COUNT(*) FROM ALIMI_RECEIVER WHERE AS_SEQ = A.AS_SEQ) AS AS_CNT	  			\n");
            sb.append("  FROM (																			  			\n");
            sb.append("         SELECT DISTINCT MT_NO, SEND_MESSAGE, MT_URL, MT_DATE, AS_SEQ, AS_TITLE, MAL_TYPE,  MAL_SEND_DATE , AS_TYPE	\n");  
            sb.append("           FROM MALIMI_LOG 														  			\n");
            sb.append("          WHERE MT_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'	  			\n");
            if(!as_seq.equals("")){
        		sb.append("        AND AS_SEQ = "+as_seq+"														\n");
        	}
            if(!as_type.equals("")){
        		sb.append("        AND AS_TYPE = "+as_type+"														\n");
        	}
            if(!mal_type.equals("")){
        		sb.append("        AND MAL_TYPE = "+mal_type+"														\n");
        	}
            if(!searchKey.equals("")){
        		sb.append(" AND SEND_MESSAGE LIKE '%"+searchKey+"%'													\n");
        	}
            sb.append("          GROUP BY AS_TYPE, MT_NO 													  		\n");
            sb.append("          ORDER BY MAL_SEQ DESC 													  			\n");
            sb.append("          LIMIT  "+index+" , "+rowCnt+"											  			\n");
            sb.append("       )A 																		  			\n"); 
            
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());            

            while ( rs.next() ) {
            	childBean = bean.new AlimiLogList();
            	childBean.setMt_no(rs.getString("MT_NO"));
            	childBean.setSend_message(rs.getString("SEND_MESSAGE"));
            	childBean.setMt_date(rs.getString("MT_DATE"));
            	childBean.setAs_seq(rs.getString("AS_SEQ"));
            	childBean.setAs_title(rs.getString("AS_TITLE"));
            	childBean.setAs_cnt(rs.getString("AS_CNT"));
            	childBean.setMal_type(rs.getString("MAL_TYPE"));
            	childBean.setMt_url(rs.getString("MT_URL"));
            	childBean.setMal_send_date(rs.getString("MAL_SEND_DATE"));
            	childBean.setAs_type(rs.getString("AS_TYPE"));
            	childBean.setAs_seq(rs.getString("AS_SEQ"));
            	result.add(childBean);
            }

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );
        } catch (Exception ex ) {
            Log.writeExpt(ex);
        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return result;
    }
	
	public ArrayList getAlimiLogList_Excel(String sDate, String eDate, String mal_type, String searchKey, String as_type, String as_seq) {
    	ArrayList result = new ArrayList();
    	AlimiLogSuperBean bean = new AlimiLogSuperBean();
    	AlimiLogSuperBean.AlimiLogList childBean = null;
    	
        try {

        	
        	dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();
            
            sb = new StringBuffer();
            sb.append("SELECT A.*																		  			\n");
            sb.append("     , (SELECT COUNT(*) FROM ALIMI_RECEIVER WHERE AS_SEQ = A.AS_SEQ) AS AS_CNT	  			\n");
            sb.append("  FROM (																			  			\n");
            sb.append("         SELECT DISTINCT MT_NO, SEND_MESSAGE, MT_URL, MT_DATE, AS_SEQ, AS_TITLE, MAL_TYPE,  MAL_SEND_DATE , AS_TYPE	\n");  
            sb.append("           FROM MALIMI_LOG 														  			\n");
            sb.append("          WHERE MT_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'	  			\n");
            if(!as_seq.equals("")){
        		sb.append("        AND AS_SEQ = "+as_seq+"														\n");
        	}
            if(!as_type.equals("")){
        		sb.append("        AND AS_TYPE = "+as_type+"														\n");
        	}
            if(!mal_type.equals("")){
        		sb.append("        AND MAL_TYPE = "+mal_type+"														\n");
        	}
            if(!searchKey.equals("")){
        		sb.append(" AND SEND_MESSAGE LIKE '%"+searchKey+"%'													\n");
        	}
            sb.append("          GROUP BY AS_TYPE, MT_NO 													  		\n");
            sb.append("          ORDER BY MAL_SEQ DESC 													  			\n");
            sb.append("       )A 																		  			\n"); 
            
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());            

            while ( rs.next() ) {
            	childBean = bean.new AlimiLogList();
            	childBean.setMt_no(rs.getString("MT_NO"));
            	childBean.setSend_message(rs.getString("SEND_MESSAGE"));
            	childBean.setMt_date(rs.getString("MT_DATE"));
            	childBean.setAs_seq(rs.getString("AS_SEQ"));
            	childBean.setAs_title(rs.getString("AS_TITLE"));
            	childBean.setAs_cnt(rs.getString("AS_CNT"));
            	childBean.setMal_type(rs.getString("MAL_TYPE"));
            	childBean.setMt_url(rs.getString("MT_URL"));
            	childBean.setMal_send_date(rs.getString("MAL_SEND_DATE"));
            	childBean.setAs_type(rs.getString("AS_TYPE"));
            	childBean.setAs_seq(rs.getString("AS_SEQ"));
            	result.add(childBean);
            }

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );
        } catch (Exception ex ) {
            Log.writeExpt(ex);
        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return result;
    }
	
	public ArrayList getSnsCntList(String sDate, String eDate){
		
		ArrayList list = new ArrayList();
		String tmp[] = null;
		 try {

	        	
	        	dbconn = new DBconn();
	            dbconn.getDBCPConnection();
	            stmt = dbconn.createStatement();
	            
	        	sb = new StringBuffer();
	        	sb.append("(SELECT '"+sDate+"~"+eDate+"' AS DATE,  A.AB_NAME, B.AB_DEPT, COUNT(0) AS CNT     \n");
	        	sb.append("		FROM MALIMI_LOG A, ADDRESS_BOOK B                                                              \n");
	        	sb.append("		WHERE A.AB_MOBILE = B.AB_MOBILE 	 \n");
	        	sb.append("		AND A.MAL_SEND_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'	                                                             \n");
	        	sb.append("		AND A.AS_TYPE = 2	                                                             \n");
	        	sb.append("		GROUP BY A.AB_NAME                                                             \n");
	        	sb.append("		ORDER BY AB_NAME ASC)															\n");
	        	sb.append("	UNION ALL															\n");
	        	sb.append("(SELECT DATE_FORMAT(MAL_SEND_DATE, '%Y-%m-%d') AS DATE, A.AB_NAME, B.AB_DEPT, COUNT(0) AS CNT   \n");
	        	sb.append("FROM MALIMI_LOG A, ADDRESS_BOOK B \n");
	        	sb.append("WHERE A.AB_MOBILE = B.AB_MOBILE	\n");
	        	sb.append("AND A.MAL_SEND_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'	\n");
	        	sb.append("AND A.AS_TYPE = 2	\n");
	        	sb.append("GROUP BY date_format(A.MAL_SEND_DATE , '%Y-%m-%d'), A.AB_NAME \n");
	        	sb.append("ORDER BY DATE ASC, AB_NAME ASC) \n");
	            System.out.println(sb.toString());
	            rs = stmt.executeQuery(sb.toString());            
	            while ( rs.next() ) {
	            	tmp = new String[4];
	            	tmp[0] = rs.getString("DATE");
	            	tmp[1] = rs.getString("AB_NAME");
	            	tmp[2] = rs.getString("AB_DEPT");
	            	tmp[3] = rs.getString("CNT");
	            	list.add(tmp);
	            }

	        } catch (SQLException ex ) {
	            Log.writeExpt(ex, sb.toString() );
	        } catch (Exception ex ) {
	            Log.writeExpt(ex);
	        } finally {
	            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	        }
		return list;
	}
    
}
