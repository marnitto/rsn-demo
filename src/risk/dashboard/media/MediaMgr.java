package risk.dashboard.media;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.util.Log;

public class MediaMgr {
	DBconn conn = null;
	Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    StringBuilder sb = null;
    
    
    public JSONArray getRealstateList(){
    	conn = new DBconn();
    	JSONArray result = new JSONArray();
    	
    	try {
    		conn.getDBCPConnection();
			sb = new StringBuilder();
			sb.append("	 ## 실국별 리스트																				\n");
			sb.append("	SELECT IC_NAME, IC_CODE FROM ISSUE_CODE WHERE IC_TYPE=5 AND IC_CODE>0 AND IC_USEYN='Y' 		\n");
			
			System.out.println(sb.toString());
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			JSONObject obj = null;
			
			while(rs.next()){
				obj = new JSONObject();
				obj.put("code", rs.getString("IC_CODE"));
				obj.put("name", rs.getString("IC_NAME"));
				result.put(obj);
			}
		} catch(SQLException ex) {
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
    
    public JSONArray getDvisionList(String code){
    	conn = new DBconn();
    	JSONArray result = new JSONArray();
    	
    	try {
    		conn.getDBCPConnection();
			sb = new StringBuilder();
			sb.append("	 ## 부서별 리스트								\n");
			sb.append("	SELECT IC_NAME, IC_CODE FROM ISSUE_CODE    	\n");
			sb.append(" WHERE IC_PTYPE=5 							\n");
			if(!"".equals(code)){
			sb.append("   AND IC_PCODE = "+code+" 					\n");
			}
			sb.append("   AND IC_CODE>0 AND IC_USEYN='Y'			\n");
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			JSONObject obj = null;
			
			while(rs.next()){
				obj = new JSONObject();
				obj.put("code", rs.getString("IC_CODE"));
				obj.put("name", rs.getString("IC_NAME"));
				result.put(obj);
			}
		} catch(SQLException ex) {
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
    
    public JSONArray getPublicityTrend(String sDate , String eDate,String scope, String keyword , String code){
    	conn = new DBconn();
    	JSONArray result = new JSONArray();
    	
    	try {
    		conn.getDBCPConnection();
    		
			sb = new StringBuilder();			
			sb.append("	##온라인 홍보매체 게재 현황 																					\n");
			sb.append("	SELECT																									\n");
			sb.append("			       DATE_FORMAT(AA.CATEGORY_DATE, '%Y-%m-%d') AS MD_DATE						 				\n");
			sb.append("			      ,DATE_FORMAT(AA.CATEGORY_DATE, '%m/%d') AS DATE								 			\n");
			sb.append("			      ,IFNULL(BB.CNT1, 0) AS CNT1																\n");
			sb.append("			 FROM																							\n");
			sb.append("			  (																								\n");
			sb.append("			  SELECT DATE_FORMAT(A.DATE, '%Y/%m/%d') AS CATEGORY_DATE						 				\n");
			sb.append("				FROM (                                                                                      \n");
			sb.append("				    SELECT '"+eDate+"' - INTERVAL (A.A + (10 * B.A) + (100 * C.A)) DAY AS DATE              \n");
			sb.append("				    FROM (SELECT 0 AS A UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS A        	 \n");
			sb.append("				    CROSS JOIN (SELECT 0 AS A UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS B 	 \n");
			sb.append("				    CROSS JOIN (SELECT 0 AS A UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS C 	 \n");
			sb.append("				) A                                                                            				\n");
			sb.append("				WHERE A.DATE BETWEEN '"+sDate+"' AND '"+eDate+"' 											\n");
			sb.append("			  )AA LEFT OUTER JOIN 																			\n");
			sb.append("			  ( 																							\n");
			sb.append("			  SELECT STRAIGHT_JOIN 																			\n");
			sb.append("			          DATE_FORMAT(A.MD_DATE, '%Y/%m/%d') AS MD_DATE		                    				\n");
			sb.append("	             	 ,COUNT(0) AS CNT1  																	\n");
			sb.append("			  FROM ISSUE_DATA A , ISSUE_DATA_CODE C, ISSUE_DATA_CODE B 										\n");
			sb.append("			  WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' 						\n");
			sb.append("  			AND A.ID_SEQ = C.ID_SEQ																		\n");
			sb.append("         	AND C.IC_TYPE = 6																			\n");
			sb.append("          	AND C.IC_CODE = 10																			\n");			
			sb.append("			  	AND A.ID_SEQ = B.ID_SEQ 																		\n");
			if(code.contains(",")){
				sb.append("			  	AND B.IC_TYPE = "+code.split(",")[0]+" 													\n");
				sb.append("			  	AND B.IC_CODE = "+code.split(",")[1]+"													\n");
			}else{
				sb.append("			  	AND B.IC_TYPE = "+code+"				 													\n");
			}			
			sb.append("			  GROUP BY DATE_FORMAT(A.MD_DATE, '%Y/%m/%d') ) BB ON AA.CATEGORY_DATE = BB.MD_DATE 			\n");
			sb.append("			  ORDER BY AA.CATEGORY_DATE ASC 																\n");
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			JSONObject obj = null;
			
			while(rs.next()){
				obj = new JSONObject();
				obj.put("column-1", rs.getString("CNT1"));
				obj.put("category", rs.getString("DATE"));
				obj.put("fulldate", rs.getString("MD_DATE"));
				
				result.put(obj);
			}
			
		} catch(SQLException ex) {
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
    
    public JSONArray getChannelInfo(String sDate , String eDate ,String scope,String keyword ,String code){
    	conn = new DBconn();
    	JSONArray result = new JSONArray();
    	
    	
    	try {
    		conn.getDBCPConnection();
			sb = new StringBuilder();
			
			sb.append(" SELECT  																						\n");
			sb.append("        AA.NAME 																					\n");
			sb.append("       ,AA.S_SEQ 																				\n");
			sb.append("       ,IFNULL(BB.CNT,0) AS CNT 																	\n");
			sb.append(" FROM  																							\n");
			sb.append(" ( 																								\n");
			sb.append(" SELECT  																						\n");
			sb.append("      '2300475' AS S_SEQ 																		\n");
			sb.append("     ,'블로그' AS NAME 																			\n");
			sb.append(" UNION ALL 																						\n");
			sb.append(" SELECT  																						\n");
			sb.append("     '2300476' AS S_SEQ 																			\n");
			sb.append("     ,'트위터' AS NAME 																			\n");
			sb.append(" UNION ALL 																						\n");
			sb.append(" SELECT  																						\n");
			sb.append("     '2300477' AS S_SEQ 																			\n");
			sb.append("     ,'유튜브' AS NAME 																			\n");
			sb.append(" UNION ALL 																						\n");
			sb.append(" SELECT  																						\n");
			sb.append("     '2300478' AS S_SEQ 																			\n");
			sb.append("     ,'인스타그램' AS NAME 																			\n");
			sb.append(" UNION ALL 																						\n");
			sb.append(" SELECT  																						\n");
			sb.append("     '2300598' AS S_SEQ 																			\n");
			sb.append("     ,'카카오스토리' AS NAME 																		\n");
			sb.append(" UNION ALL 																						\n");
			sb.append(" SELECT  																						\n");
			sb.append("     '2300599' AS S_SEQ 																			\n");
			sb.append("     ,'카페' AS NAME 																				\n");
			sb.append(" UNION ALL 																						\n");
			sb.append(" SELECT  																						\n");
			sb.append("     '2300479' AS S_SEQ 																			\n");
			sb.append("     ,'페이스북' AS NAME 																			\n");
			sb.append(" ) AA LEFT OUTER JOIN 																			\n");
			sb.append(" ( 																								\n");
			sb.append(" SELECT  																						\n");
			sb.append(" 			  A.S_SEQ                	 														\n");
			sb.append("        ,COUNT(0) AS CNT 																		\n");
			sb.append(" FROM ISSUE_DATA A , ISSUE_DATA_CODE C ,ISSUE_DATA_CODE B										\n");
			sb.append("	WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' 							\n");
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      						\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    						\n");
				    }else if("3".equals(scope)){
				    	
				    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'				\n"); 
				    }
		    }
			sb.append("   AND A.ID_SEQ = C.ID_SEQ 																		\n");
			sb.append("   AND C.IC_TYPE = 6 																			\n");
			sb.append("   AND C.IC_CODE = 10  																			\n");
			sb.append("	  AND A.ID_SEQ = B.ID_SEQ 																		\n");
			if(code.contains(",")){
				sb.append("			  	AND B.IC_TYPE = "+code.split(",")[0]+" 													\n");
				sb.append("			  	AND B.IC_CODE = "+code.split(",")[1]+"													\n");
			}else{
				sb.append("			  	AND B.IC_TYPE = "+code+"				 													\n");
			}	
			sb.append(" GROUP BY A.S_SEQ     																			\n");
			sb.append(" )BB ON AA.S_SEQ = BB.S_SEQ  																	\n");
			
			
			
			System.out.println(sb.toString());
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			JSONObject obj = null;
						
			while(rs.next()){
				obj = new JSONObject();
				obj.put("category",rs.getString("NAME"));
				obj.put("column_1",rs.getInt("CNT"));
				obj.put("s_seq",rs.getString("S_SEQ"));
				
				result.put(obj);
			}
			
			
		} catch(SQLException ex) {
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
    
    public JSONArray getRealstateGraph(String sDate , String eDate,String scope, String keyword , String code){
    	conn = new DBconn();
    	JSONArray result = new JSONArray();
    	
    	try {
    		conn.getDBCPConnection();
			sb = new StringBuilder();
			
			
			sb.append(" #실국별 게재현황 - 그래프 																				\n");
			sb.append(" SELECT 																							\n");
			sb.append("      AA.IC_NAME 																				\n");
			sb.append("     ,AA.IC_CODE 																				\n");
			sb.append("     ,IFNULL(BB.CNT,0) AS CNT 																	\n");
			sb.append(" FROM  																							\n");
			sb.append("  (SELECT 																						\n");
			sb.append("        IC_NAME 																					\n");
			sb.append("       ,IC_CODE 																					\n");
			sb.append("  FROM ISSUE_CODE  																				\n");
			sb.append("  WHERE IC_TYPE = 5  																			\n");			
			sb.append("    AND IC_CODE > 0      																		\n");
			sb.append("    AND IC_USEYN = 'Y' 																			\n");
			if(!"".equals(code)){
				sb.append("    AND IC_CODE = "+code+"																	\n");
			}			
			sb.append("  ) AA LEFT OUTER JOIN 																			\n");
			sb.append("  (SELECT 																						\n");
			sb.append("        B.IC_CODE 																				\n");
			sb.append(" 	    ,COUNT(0) AS CNT 																		\n");
			sb.append("  FROM ISSUE_DATA A , ISSUE_DATA_CODE C, ISSUE_DATA_CODE B  										\n");
			sb.append("	 WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' 							\n");
			sb.append("    AND A.ID_SEQ = C.ID_SEQ 																		\n");
			sb.append("    AND C.IC_TYPE = 6 																			\n");
			sb.append("    AND C.IC_CODE = 10         																	\n");
			sb.append("    AND A.ID_SEQ = B.ID_SEQ 																		\n");
			sb.append("    AND B.IC_TYPE = 5 																			\n");
			if(!"".equals(code)){
				sb.append("    AND B.IC_CODE = "+code+"																	\n");
			}
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      						\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    						\n");
				    }else if("3".equals(scope)){
				    	
				    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'				\n"); 
				    }
		    }
			sb.append(" GROUP BY B.IC_CODE ) BB ON AA.IC_CODE = BB.IC_CODE 												\n");
						
			
			System.out.println(sb.toString());
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			JSONObject obj = null;
						
			while(rs.next()){
				obj = new JSONObject();
				obj.put("category",rs.getString("IC_NAME"));
				obj.put("code",rs.getString("IC_CODE"));
				obj.put("column-1",rs.getString("CNT"));
				
				result.put(obj);
			}
			
		} catch(SQLException ex) {
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
    
    
    public JSONObject getRealstateInfo(String sDate , String eDate,String scope, String keyword , String code, int nowPage , int rowCnt){
    	conn = new DBconn();
    	JSONObject result = new JSONObject();
    	
    	int startNum = (nowPage-1)*rowCnt;
    	
    	try {
    		conn.getDBCPConnection();
			sb = new StringBuilder();
			
			sb.append(" #실국별 게재현황 - 관련정보 - COUNT																		\n");
			sb.append(" SELECT 																							\n");
			sb.append("        COUNT(0) AS CNT 																			\n");
			sb.append("  FROM ISSUE_DATA A , ISSUE_DATA_CODE C, ISSUE_DATA_CODE B  										\n");
			sb.append("	 WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' 							\n");
			sb.append("    AND A.ID_SEQ = C.ID_SEQ 																		\n");
			sb.append("    AND C.IC_TYPE = 6 																			\n");
			sb.append("    AND C.IC_CODE = 10         																	\n");
			sb.append("    AND A.ID_SEQ = B.ID_SEQ 																		\n");
			sb.append("    AND B.IC_TYPE = 5 																			\n");
			if(!"".equals(code)){
				sb.append("    AND B.IC_CODE = "+code+"																	\n");
			}						
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      						\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    						\n");
				    }else if("3".equals(scope)){
				    	
				    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'				\n"); 
				    }
		    }			
			
			System.out.println(sb.toString());
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
						
			if(rs.next()){
				result.put("total_cnt",rs.getInt("CNT"));
			}
			
			sb = new StringBuilder();
			
			sb.append(" #실국별 게재현황 - 관련정보 - COUNT																		\n");
			sb.append(" SELECT 																							\n");
			sb.append("  		DATE_FORMAT(A.MD_DATE, '%Y-%m-%d') AS MD_DATE											\n");
			sb.append("        ,A.MD_SITE_NAME																			\n");
			sb.append("        ,A.ID_URL																				\n");
			sb.append("        ,A.ID_TITLE																				\n");
			sb.append("        ,A.S_SEQ																					\n");
			sb.append("        ,FN_GET_ISSUE_CODE_NAME(A.ID_SEQ,5) AS TYPE5												\n");
			sb.append("        ,FN_GET_ISSUE_CODE_NAME(A.ID_SEQ,51) AS TYPE51											\n");
			sb.append("  FROM ISSUE_DATA A , ISSUE_DATA_CODE C, ISSUE_DATA_CODE B  										\n");
			sb.append("	 WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' 							\n");
			sb.append("    AND A.ID_SEQ = C.ID_SEQ 																		\n");
			sb.append("    AND C.IC_TYPE = 6 																			\n");
			sb.append("    AND C.IC_CODE = 10         																	\n");
			sb.append("    AND A.ID_SEQ = B.ID_SEQ 																		\n");
			sb.append("    AND B.IC_TYPE = 5 																			\n");
			if(!"".equals(code)){
				sb.append("    AND B.IC_CODE = "+code+"																	\n");
			}						
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      						\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    						\n");
				    }else if("3".equals(scope)){
				    	
				    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'				\n"); 
				    }
		    }
			sb.append("  ORDER BY MD_DATE DESC   																		\n");
			sb.append(" LIMIT " + startNum + "," + rowCnt + "															\n");
			
			System.out.println(sb.toString());
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			JSONObject obj = null;
			JSONArray  arr = new JSONArray();
			while(rs.next()){
				obj = new JSONObject();
				obj.put("date",rs.getString("MD_DATE"));
				obj.put("url",rs.getString("ID_URL"));
				obj.put("site_name",rs.getString("MD_SITE_NAME"));
				obj.put("s_seq",rs.getString("S_SEQ"));
				obj.put("title",rs.getString("ID_TITLE"));
				obj.put("type5",rs.getString("TYPE5"));
				obj.put("type51",rs.getString("TYPE51"));
				arr.put(obj); 
			}
			
			result.put("data",arr);
			
		} catch(SQLException ex) {
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
    
    public JSONArray getRealstateInfo_Excel(String sDate , String eDate,String scope, String keyword , String code){
    	conn = new DBconn();
    	JSONArray result = new JSONArray();
    	
    	try {
    		conn.getDBCPConnection();
			
			sb = new StringBuilder();
			
			sb.append(" #실국별 게재현황 - 관련정보 - 엑셀																		\n");
			sb.append(" SELECT 																							\n");
			sb.append("  		DATE_FORMAT(A.MD_DATE, '%Y-%m-%d') AS MD_DATE											\n");
			sb.append("        ,A.MD_SITE_NAME																			\n");
			sb.append("        ,A.ID_URL																				\n");
			sb.append("        ,A.ID_TITLE																				\n");
			sb.append("        ,A.S_SEQ																					\n");
			sb.append("        ,FN_GET_ISSUE_CODE_NAME(A.ID_SEQ,5) AS TYPE5												\n");
			sb.append("        ,FN_GET_ISSUE_CODE_NAME(A.ID_SEQ,51) AS TYPE51											\n");
			sb.append("  FROM ISSUE_DATA A , ISSUE_DATA_CODE C, ISSUE_DATA_CODE B  										\n");
			sb.append("	 WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' 							\n");
			sb.append("    AND A.ID_SEQ = C.ID_SEQ 																		\n");
			sb.append("    AND C.IC_TYPE = 6 																			\n");
			sb.append("    AND C.IC_CODE = 10         																	\n");
			sb.append("    AND A.ID_SEQ = B.ID_SEQ 																		\n");
			sb.append("    AND B.IC_TYPE = 5 																			\n");
			if(!"".equals(code)){
				sb.append("    AND B.IC_CODE = "+code+"																	\n");
			}						
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      						\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    						\n");
				    }else if("3".equals(scope)){
				    	
				    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'				\n"); 
				    }
		    }
			sb.append("  ORDER BY MD_DATE DESC   																		\n");
			
			System.out.println(sb.toString());
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			JSONObject obj = null;
			while(rs.next()){
				obj = new JSONObject();
				obj.put("date",rs.getString("MD_DATE"));
				obj.put("url",rs.getString("ID_URL"));
				obj.put("site_name",rs.getString("MD_SITE_NAME"));
				obj.put("s_seq",rs.getString("S_SEQ"));
				obj.put("title",rs.getString("ID_TITLE"));
				obj.put("type5",rs.getString("TYPE5"));
				obj.put("type51",rs.getString("TYPE51"));
				result.put(obj); 
			}
			
		} catch(SQLException ex) {
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
    
	
}
