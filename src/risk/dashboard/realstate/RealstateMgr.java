package risk.dashboard.realstate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.util.Log;

public class RealstateMgr {
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
			sb.append("	 ## 부서별 리스트																									\n");
			sb.append("	SELECT IC_NAME, IC_CODE FROM ISSUE_CODE WHERE IC_PTYPE=5 AND IC_PCODE = "+code+" AND IC_CODE>0 AND IC_USEYN='Y' 	\n");
			
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
    
    public JSONArray getOnlineTrend(String sDate , String eDate,String scope, String keyword , String code){
    	conn = new DBconn();
    	JSONArray result = new JSONArray();
    	
    	String [] GroupCode = code.split(",");
    	
    	
    	try {
    		conn.getDBCPConnection();
			sb = new StringBuilder();
			
			sb.append("	 ## 온라인 동향 																			\n");
			sb.append("	 SELECT																					\n");
			sb.append("			      DATE_FORMAT(AA.CATEGORY_DATE, '%Y-%m-%d') AS MD_DATE						\n");
			sb.append("			      ,DATE_FORMAT(AA.CATEGORY_DATE, '%m/%d') AS DATE							\n");
			for(int i=0 ; i<GroupCode.length ; i++){
				sb.append("			      ,CONCAT('"+GroupCode[i].split("@@")[1]+"','@#',IFNULL(BB.CNT"+(i+1)+", 0)) AS CNT"+(i+1)+"							\n");
			}
			
			sb.append("	 FROM																					\n");
			sb.append("	 (																						\n");
			sb.append("	 SELECT DATE_FORMAT(A.DATE, '%Y/%m/%d') AS CATEGORY_DATE						 		\n");
			sb.append("	 FROM (                                                                                 \n");
			sb.append("				    SELECT '"+eDate+"' - INTERVAL (A.A + (10 * B.A) + (100 * C.A)) DAY AS DATE                                                                                                                                  																 \n");
			sb.append("				    FROM (SELECT 0 AS A UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS A        	 \n");
			sb.append("				    CROSS JOIN (SELECT 0 AS A UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS B 	 \n");
			sb.append("				    CROSS JOIN (SELECT 0 AS A UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS C 	 \n");
			sb.append("	 ) A                                                                            		\n");
			sb.append("	 WHERE A.DATE BETWEEN '"+sDate+"' AND '"+eDate+"' 										\n");
			sb.append("	 )AA LEFT OUTER JOIN 																	\n");
			sb.append("	 ( 																						\n");
			sb.append("	 SELECT STRAIGHT_JOIN 																	\n");
			sb.append("			       DATE_FORMAT(A.MD_DATE, '%Y/%m/%d') AS MD_DATE 							\n");
			for(int i=0 ; i<GroupCode.length ; i++){
				sb.append("			      ,SUM(IF(B.IC_CODE="+GroupCode[i].split("@@")[1]+", 1, 0)) AS CNT"+(i+1)+"				\n");
			}			
			sb.append("	 FROM ISSUE_DATA A, ISSUE_DATA_CODE B 							  						\n");
			sb.append("	 WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' 					\n");
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      												\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    											\n");
				    }else if("3".equals(scope)){
				    	
				    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'							\n"); 
				    }
		    }
			sb.append("	   AND A.ID_SEQ = B.ID_SEQ 																\n");
			sb.append("	   AND B.IC_TYPE = 51 																	\n");
			//sb.append("	   AND B.IC_CODE IN ("+code+") 															\n");
			sb.append("	 GROUP BY DATE_FORMAT(A.MD_DATE, '%Y/%m/%d') ) BB ON AA.CATEGORY_DATE = BB.MD_DATE 		\n");
			sb.append("	 ORDER BY AA.CATEGORY_DATE ASC 															\n");
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			JSONObject obj = null;
			
			while(rs.next()){
				obj = new JSONObject();
				obj.put("full_date", rs.getString("MD_DATE"));
				obj.put("category", rs.getString("DATE"));
				obj.put("fulldate", rs.getString("MD_DATE"));				
				for(int i=0 ; i<GroupCode.length ; i++){
					obj.put("CNT_"+(i+1)+"",rs.getString("CNT"+(i+1)+"").split("@#")[1]);
				}
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
    
    public JSONObject getChannelInfo(String sDate , String eDate ,String scope,String keyword ,String code){
    	conn = new DBconn();
    	JSONObject result = new JSONObject();
    	JSONObject obj = null;
    	
    	try {
    		conn.getDBCPConnection();
			sb = new StringBuilder();
			
			sb.append(" ##채널 별 성향 (왼쪽) 														\n");
			sb.append(" SELECT   																\n");
			sb.append("       SUM(IF(B.IC_CODE=1, 1, 0)) AS POS 								\n");
			sb.append("      ,SUM(IF(B.IC_CODE=2, 1, 0)) AS NEG 								\n");
			sb.append("      ,SUM(IF(B.IC_CODE=3, 1, 0)) AS NEU 								\n");
			sb.append("      ,COUNT(0) AS TOTAL 												\n");
			sb.append(" FROM ISSUE_DATA A , ISSUE_DATA_CODE B , ISSUE_DATA_CODE C				\n");
			sb.append("	WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' 	\n");
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      												\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    											\n");
				    }else if("3".equals(scope)){
				    	
				    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'							\n"); 
				    }
		    }
			sb.append("   AND A.ID_SEQ = B.ID_SEQ 												\n");
			sb.append("   AND B.IC_TYPE = 9 													\n");
			sb.append("   AND A.ID_SEQ = C.ID_SEQ 												\n");
			sb.append("   AND C.IC_TYPE = "+code.split(",")[0]+" 											\n");
			sb.append("   AND C.IC_CODE = "+code.split(",")[1]+" 											\n");
			
			System.out.println(sb.toString());
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				obj = new JSONObject();
				obj.put("pos",rs.getString("POS"));
				obj.put("neg",rs.getString("NEG"));
				obj.put("neu",rs.getString("NEU"));
				obj.put("total",rs.getString("TOTAL"));
			}
			
			result.put("pie",obj);			
			
			sb = new StringBuilder();
			sb.append(" ##채널 별 성향 (오른쪽) 																			\n");
			sb.append(" SELECT AA.IC_CODE																			\n");
			sb.append(" 		        ,AA.IC_NAME																	\n");
			sb.append(" 		        ,IFNULL(BB.CNT, 0) AS TOTAL													\n");
			sb.append(" 		        ,IFNULL(BB.POS, 0) AS POS													\n");
			sb.append(" 		        ,IFNULL(BB.NEG, 0) AS NEG													\n");
			sb.append(" 		        ,IFNULL(BB.NEU, 0) AS NEU													\n");
			sb.append(" 		        ,BB.IC_CODE AS CODE															\n");
			sb.append(" 	   FROM (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 6 AND IC_USEYN = 'Y' AND IC_CODE > 0 ORDER BY IC_ORDER ASC ) AA			 \n");
			sb.append(" 	LEFT OUTER JOIN																			\n");
			sb.append(" 	  ( SELECT B.IC_CODE																	\n");
			sb.append(" 	  , COUNT(B.IC_CODE) AS CNT 															\n");
			sb.append(" 	  ,SUM(IF(C.IC_CODE =1 , 1, 0)) AS POS													\n");
			sb.append(" 		,SUM(IF(C.IC_CODE =2 , 1, 0)) AS NEG												\n");
			sb.append(" 		,SUM(IF(C.IC_CODE =3 , 1, 0)) AS NEU												\n");
			sb.append(" 	FROM ISSUE_DATA A																		\n");
			sb.append(" 	    INNER JOIN ISSUE_DATA_CODE B ON A.ID_SEQ=B.ID_SEQ AND B.IC_TYPE=6					\n");
			sb.append(" 	    INNER JOIN ISSUE_DATA_CODE C ON A.ID_SEQ=C.ID_SEQ AND C.IC_TYPE=9					\n");
			sb.append(" 	    INNER JOIN ISSUE_DATA_CODE D ON A.ID_SEQ=D.ID_SEQ AND D.IC_TYPE="+code.split(",")[0]+"			\n");
			sb.append("                   AND D.IC_CODE = "+code.split(",")[1]+" 												\n");
			sb.append("		WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' 					\n");
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      												\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    											\n");
				    }else if("3".equals(scope)){
				    	
				    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'							\n"); 
				    }
		    }
			sb.append(" 	GROUP BY B.IC_CODE ) BB ON AA.IC_CODE = BB.IC_CODE 										\n");
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			obj = null;
			JSONArray arr = new JSONArray();
			
			while(rs.next()){
				obj = new JSONObject();
				obj.put("category",rs.getString("IC_NAME"));
				obj.put("total_cnt",rs.getString("TOTAL"));
				obj.put("column-1",rs.getString("POS"));
				obj.put("column-2",rs.getString("NEG"));
				obj.put("column-3",rs.getString("NEU"));
				obj.put("code",rs.getString("CODE"));
				
				arr.put(obj);
			}
			
			result.put("graph",arr);
			
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
    
    public JSONObject getRelInfo(String sDate , String eDate,String scope,String keyword , String senti , String code , int nowPage , int rowCnt ){
    	return getRelInfo(sDate, eDate, scope, keyword, senti, code, nowPage, rowCnt,"");
    }
    
    public JSONObject getRelInfo(String sDate , String eDate,String scope,String keyword , String senti , String code , int nowPage , int rowCnt ,String excel){
    	conn = new DBconn();
    	JSONObject result = new JSONObject();
    	
    	int startNum = (nowPage-1)*rowCnt;
    	
    	try {
    		conn.getDBCPConnection();
			sb = new StringBuilder();
			sb.append("	## 관련정보 - COUNT														\n");
			sb.append(" SELECT 																	\n");
			sb.append("       COUNT(0) AS TOTAL_CNT  											\n");
			sb.append(" FROM ISSUE_DATA A , ISSUE_DATA_CODE B , ISSUE_DATA_CODE C 				\n");
			sb.append("	WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' 	\n");
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      												\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    											\n");
				    }else if("3".equals(scope)){
				    	
				    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'							\n"); 
				    }
		    }
			sb.append("   AND A.ID_SEQ = B.ID_SEQ  												\n");
			sb.append("   AND B.IC_TYPE = "+code.split(",")[0]+"								\n");
			sb.append("   AND B.IC_CODE = "+code.split(",")[1]+"								\n");
			sb.append("   AND A.ID_SEQ = C.ID_SEQ 												\n");
			sb.append("   AND C.IC_TYPE = 9 													\n");
			if(!"".equals(senti)){
				sb.append("   AND C.IC_CODE = "+senti+" 										\n");
			}
			
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			if(rs.next()){
				result.put("total_cnt", rs.getString("TOTAL_CNT"));
			}
			
			sb = new StringBuilder();
			sb.append("	## 관련정보 - LIST															\n");
			sb.append(" SELECT 																	\n");
			sb.append("       A.ID_SEQ   														\n");
			sb.append("      ,DATE_FORMAT(A.MD_DATE, '%y/%m/%d') AS MD_DATE  					\n");
			sb.append("      ,A.ID_TITLE  														\n");
			sb.append("      ,A.MD_SITE_NAME  													\n");
			sb.append("      ,C.IC_CODE  														\n");
			sb.append("      ,A.S_SEQ	  														\n");
			sb.append("      ,A.ID_URL	  														\n");
			sb.append("      ,FN_GET_ISSUE_CODE_NAME(A.ID_SEQ,51) AS NAME						\n");
			sb.append(" FROM ISSUE_DATA A , ISSUE_DATA_CODE B , ISSUE_DATA_CODE C 				\n");
			sb.append("	WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' 	\n");
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      												\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    											\n");
				    }else if("3".equals(scope)){
				    	
				    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'							\n"); 
				    }
		    }
			sb.append("   AND A.ID_SEQ = B.ID_SEQ  												\n");
			sb.append("   AND B.IC_TYPE = "+code.split(",")[0]+"								\n");
			sb.append("   AND B.IC_CODE = "+code.split(",")[1]+"								\n");
			sb.append("   AND A.ID_SEQ = C.ID_SEQ 												\n");
			sb.append("   AND C.IC_TYPE = 9 													\n");
			if(!"".equals(senti)){
				sb.append("   AND C.IC_CODE = "+senti+" 										\n");
			}
			sb.append("	ORDER BY MD_DATE DESC													\n");
			if(!"true".equals(excel)){
			sb.append(" LIMIT " + startNum + "," + rowCnt + "									\n");
			}
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			JSONObject obj = null;
			JSONArray arr = new JSONArray();
			
			while(rs.next()){
				obj = new JSONObject();
				obj.put("id_seq", rs.getString("ID_SEQ"));
				obj.put("date", rs.getString("MD_DATE"));
				obj.put("title", rs.getString("ID_TITLE"));
				obj.put("site_name", rs.getString("MD_SITE_NAME"));
				obj.put("senti", rs.getString("IC_CODE"));
				obj.put("s_seq", rs.getString("S_SEQ"));
				obj.put("url", rs.getString("ID_URL"));
				obj.put("name", rs.getString("NAME"));
				
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

}
