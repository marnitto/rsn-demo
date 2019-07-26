package risk.dashboard.app;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.util.Log;

public class AppMgr {
	DBconn conn = null;
	Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    StringBuilder sb = null;
    
    public JSONArray getAlimi(){
    	conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONArray arr = null; 
   	 	try {
			conn.getDBCPConnection();
			sb.append("	 ## 알리미 리스트																		\n");
			sb.append("	 SELECT AS_TITLE, AS_SEQ FROM ALIMI_SETTING								\n");
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("alimiSeq", rs.getString("AS_SEQ"));
				obj.put("alimiTitle", rs.getString("AS_TITLE"));
				arr.put(obj);
			}
			
   	 	}catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
   	 	return arr;
    }
    
    public int getReloadTime(String userSeq){
    	conn = new DBconn();
    	sb = new StringBuilder();
    	
    	int result = 0; 
   	 	try {
			conn.getDBCPConnection();
			sb.append("	 ## 사용자의 RELOAD TIME																	\n");
			sb.append("	SELECT ST_RELOAD_TIME FROM SETTING WHERE M_SEQ = " + userSeq + "		\n");
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			while(rs.next()){
				result = rs.getInt("ST_RELOAD_TIME");
			}
			
   	 	}catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
   	 	return result;
    }
	
	public JSONObject getAlimiAppList(int nowPage, int rowCnt, String scope, String keyword, String sDate, String eDate, String alimiSeq, String readYn, String importantYn, String userSeq, String excelYn){
		int startNum = (nowPage-1)*rowCnt;
		conn = new DBconn();
    	
    	JSONObject obj = new JSONObject();
    	JSONArray arr = null;
    	
   	 	try {
   	 		conn.getDBCPConnection();
   	 		
   	 		if("N".equals(excelYn)){
	   	 		sb = new StringBuilder();
				sb.append("	## 알리미앱글 목록 - COUNT										 																																												\n");
				sb.append("	 SELECT  COUNT(*) AS TOTAL 																																																						\n");
				sb.append("	 FROM (																																																													\n");
				sb.append("	     SELECT  A.MT_NO  																																																								\n");
				sb.append("	            , IFNULL(( SELECT AM_TYPE  FROM ALIMI_MONITOR WHERE AM_SEQ = A.AS_SEQ AND AM_NO = A.MT_NO AND AM_M_SEQ = " + userSeq + " AND AM_TYPE = 1  ), 0) AS `READ`					\n");
				sb.append("	            , IFNULL(( SELECT AM_TYPE  FROM ALIMI_MONITOR WHERE AM_SEQ = A.AS_SEQ AND AM_NO = A.MT_NO AND AM_M_SEQ = " + userSeq + " AND AM_TYPE = 2  ), 0) AS `IMPORTANT`     	\n");
				sb.append("	       			FROM MALIMI_LOG A                                           																																										\n");
				sb.append("	      WHERE A.MT_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'    																																			\n");
				
				if(!"".equals(keyword)){
			    	 if("1".equals(scope)){
					    	sb.append("  AND A.MT_TITLE LIKE '%" + keyword + "%'                      											\n");
					    }else if("2".equals(scope)){
					    	sb.append("  AND A.MT_CONTENT LIKE '%" + keyword + "%'                    											\n");
					    }else if("3".equals(scope)){
					    	sb.append("  AND CONCAT_WS(' ', A.MT_TITLE, A.MT_CONTENT) LIKE '%" + keyword + "%'						\n"); 
					    }
			    }
				
				sb.append("	        AND A.MAL_TYPE = 1																											\n");
				
				if(!"".equals(alimiSeq)){
					sb.append("     AND A.AS_SEQ = " + alimiSeq + "																																						\n");
				}
				
				sb.append("		GROUP BY A.AS_SEQ, A.MT_NO													                      							\n");
				sb.append("	 ) A																																		\n");
				sb.append("	 WHERE 1=1																																\n");
				
				if("Y".equals(readYn)){
					sb.append("	AND A.READ != 0																															\n");
				}else if("N".equals(readYn)){
					sb.append("	AND A.READ = 0																															\n");
				}
				
				if("Y".equals(importantYn)){
					sb.append("	AND A.IMPORTANT != 0																															\n");
				}else if("N".equals(importantYn)){
					sb.append("	AND A.IMPORTANT = 0																															\n");
				}
				
				pstmt = null;
				pstmt = conn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				System.out.println(sb.toString());
			
				while(rs.next()){
					obj.put("total", rs.getString("TOTAL"));
				}
   	 		}
			
			sb = new StringBuilder();
			sb.append(" ## 알리미앱글 목록 																																																								\n");
			sb.append("  SELECT  A.*																																																											\n");
			sb.append("  FROM (																																																												\n");
			sb.append("      SELECT  A.MT_NO 																																																							\n");
			sb.append("             , IFNULL(( SELECT AM_TYPE  FROM ALIMI_MONITOR WHERE AM_SEQ = A.AS_SEQ AND AM_NO = A.MT_NO AND AM_M_SEQ = " + userSeq + " AND AM_TYPE = 1  ), 0) AS `READ`				\n");
			sb.append("             , IFNULL(( SELECT AM_TYPE  FROM ALIMI_MONITOR WHERE AM_SEQ = A.AS_SEQ AND AM_NO = A.MT_NO AND AM_M_SEQ = " + userSeq + " AND AM_TYPE = 2  ), 0) AS `IMPORTANT`	\n");
			sb.append("             ,A.AS_SEQ                                                             																																									\n");
			sb.append("             ,A.S_SEQ                                                             																																										\n");
			sb.append("             ,A.AS_TITLE                                                                     																				 							\n");
			sb.append("             ,A.SD_NAME                                                                      																				 						\n");
			sb.append("             ,date_format(A.MT_DATE,'%Y-%m-%d') AS MT_DATE                             																								\n");
			sb.append("             ,A.MT_TITLE                                                          																													\n");
			sb.append("             ,A.MT_URL                                                           																														\n");
			sb.append("        			FROM MALIMI_LOG A                                           																													\n");
			sb.append("       WHERE A.MT_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'     																						\n");
			
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.MT_TITLE LIKE '%" + keyword + "%'                      											\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.MT_CONTENT LIKE '%" + keyword + "%'                    											\n");
				    }else if("3".equals(scope)){
				    	sb.append("  AND CONCAT_WS(' ', A.MT_TITLE, A.MT_CONTENT) LIKE '%" + keyword + "%'						\n"); 
				    }
		    }
			
			sb.append("         AND A.MAL_TYPE = 1																																										\n");
			
			if(!"".equals(alimiSeq)){
				sb.append("     AND A.AS_SEQ = " + alimiSeq + "																																						\n");
			}
			
			sb.append(" 	GROUP BY A.AS_SEQ, A.MT_NO													            																				          			\n");
			sb.append("  ORDER BY A.MAL_SEQ DESC 																																									\n");
			sb.append("  ) A																																																	\n");
			sb.append("  WHERE 1=1																																															\n");
			
			if("Y".equals(readYn)){
				sb.append("	AND A.READ != 0																															\n");
			}else if("N".equals(readYn)){
				sb.append("	AND A.READ = 0																															\n");
			}
			
			if("Y".equals(importantYn)){
				sb.append("	AND A.IMPORTANT != 0																															\n");
			}else if("N".equals(importantYn)){
				sb.append("	AND A.IMPORTANT = 0																															\n");
			}
			
			if("N".equals(excelYn)){
				sb.append("  LIMIT " + startNum + "," + rowCnt + "																													\n");
			}else{
				sb.append("  LIMIT 1000																																						\n");
			}
			                                                                                                                                                 						
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj2 = new JSONObject();
				obj2.put("docId", rs.getString("MT_NO"));
				obj2.put("alimiSeq", rs.getString("AS_SEQ"));
				obj2.put("readYn", rs.getString("READ"));
				obj2.put("importantYn", rs.getString("IMPORTANT"));
				obj2.put("sitecode", rs.getString("S_SEQ"));
				obj2.put("sitename", rs.getString("SD_NAME"));
				obj2.put("date", rs.getString("MT_DATE"));
				obj2.put("title", rs.getString("MT_TITLE"));
				obj2.put("url", rs.getString("MT_URL"));
				obj2.put("asTitle", rs.getString("AS_TITLE"));
				arr.put(obj2);
			}
			obj.put("data", arr);
			
   	 	}catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
   	 	return obj;
    }
	
	public void insertAlimiAppMonitor(String alimiSeq, String docId, String op, String userSeq){
		conn = new DBconn();
    	sb = new StringBuilder();

   	 	try {
   	 		conn.getDBCPConnection();
			sb.append(" INSERT INTO ALIMI_MONITOR (AM_SEQ, AM_NO, AM_TYPE, AM_M_SEQ) VALUES (" + alimiSeq + ", " + docId + ", " + op + ", " + userSeq +")				\n");
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			pstmt.executeUpdate();
			System.out.println(sb.toString());
		
   	 	}catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
    }
	
	public void deleteAlimiAppMonitor(String alimiSeq, String docId, String op, String userSeq){
		conn = new DBconn();
    	sb = new StringBuilder();
    	
   	 	try {
   	 		conn.getDBCPConnection();
			sb.append(" DELETE FROM ALIMI_MONITOR WHERE AM_SEQ = " + alimiSeq + " AND AM_NO = " + docId + " AND AM_TYPE = " + op + " AND AM_M_SEQ = " + userSeq + "	\n");
			 
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			pstmt.executeUpdate();
			System.out.println(sb.toString());
		
   	 	}catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
    }
	
	public void updateAllAlimiAppMonitor(String alimiSeq, String docId, String op, String userSeq){
		conn = new DBconn();
    	sb = new StringBuilder();
    	
   	 	try {
   	 		conn.getDBCPConnection();
			sb.append(" DELETE FROM ALIMI_MONITOR WHERE AM_SEQ = " + alimiSeq + " AND AM_NO = " + docId + " AND AM_TYPE = " + op + " AND AM_M_SEQ = " + userSeq + "	\n");
			 
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			pstmt.executeUpdate();
			System.out.println(sb.toString());
			
			pstmt.close();
			pstmt = null;
			sb = null;
			
			sb = new StringBuilder();
			sb.append(" INSERT INTO ALIMI_MONITOR																							\n");
			sb.append(" (AM_SEQ, AM_TYPE, AM_NO, AM_M_SEQ) 																			\n");
			sb.append(" VALUES (" + alimiSeq + ", " + op + ", " + docId + ", " + userSeq + ")											\n");
			 
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			pstmt.executeUpdate();
			System.out.println(sb.toString());
		
			
   	 	}catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
    }
}
	
	
	