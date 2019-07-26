package risk.dashboard.opinion;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.util.Log;
import risk.dashboard.ehcache.EhCacheUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OpinionMgr {
	private EhCacheUtil ehCacheUtil;
	DBconn conn = null;
	Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    StringBuilder sb = null;
    
    //public OpinionMgr(){
    //	ehCacheUtil = new EhCacheUtil();
    //}
    
    public JSONArray getIssueCodeList(String code){
    	conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONArray arr = null; 
   	 	try {
			conn.getDBCPConnection();
			sb.append("	 ## 이슈 코드 리스트																															\n");
			sb.append("	SELECT IC_NAME, IC_CODE FROM ISSUE_CODE WHERE IC_TYPE=" + code + " AND IC_CODE>0 AND IC_USEYN='Y'		\n");
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("iccode", rs.getString("IC_CODE"));
				obj.put("icname", rs.getString("IC_NAME"));
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
    
    public JSONArray getinfoCountData(String scope, String keyword, String sDate, String eDate){
    	conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONArray arr = null; 
   	 	try {
			conn.getDBCPConnection();
			sb.append("	  ## 정보량 현황																			\n");
			sb.append("	  SELECT																						\n");
			sb.append("	      COUNT(0) AS CNT,																	\n");
			sb.append("	      IFNULL(SUM(IF(B.IC_CODE=1, 1, 0)), 0) AS POS,												\n");
			sb.append("	      IFNULL(SUM(IF(B.IC_CODE=2, 1, 0)), 0) AS NEG,												\n");
			sb.append("	      IFNULL(SUM(IF(B.IC_CODE=3, 1, 0)), 0) AS NEU												\n");
			sb.append("	    FROM ISSUE_DATA A, ISSUE_DATA_CODE B, ISSUE_DATA_CODE C			\n");
			
			sb.append("	    WHERE A.MD_DATE BETWEEN '"  + sDate + " 00:00:00' AND '" + eDate + " 23:59:59' 						\n");
			
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      												\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    											\n");
				    }else if("3".equals(scope)){
				    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'							\n"); 
				    }
		    }
			
			sb.append("	      AND A.ID_SEQ = B.ID_SEQ																								\n");
			sb.append("	      AND B.IC_TYPE = 9																										\n");
			sb.append("	      AND A.ID_SEQ = C.ID_SEQ 																								\n");
			sb.append("	      AND C.IC_TYPE = 1 AND C.IC_CODE = 2  ## 분류체계-구분-박원순 서울시장									\n");
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("cnt", rs.getString("CNT"));
				obj.put("pos", rs.getString("POS"));
				obj.put("neg", rs.getString("NEG"));
				obj.put("neu", rs.getString("NEU"));
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
    
    public JSONArray getWeekData(String scope, String keyword, String sDate, String eDate){
    	conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONArray arr = null; 
   	 	try {
			conn.getDBCPConnection();
			sb.append("	## 주간 정보량 추이																						\n");
			sb.append("		 SELECT																									\n");
			sb.append("		      DATE_FORMAT(AA.CATEGORY_DATE, '%Y-%m-%d') AS MD_DATE						\n");
			sb.append("		      ,DATE_FORMAT(AA.CATEGORY_DATE, '%m/%d') AS DATE								\n");
			sb.append("		      ,IFNULL(BB.POS, 0) AS POS																		\n");
			sb.append("		      ,IFNULL(BB.NEG, 0) AS NEG																		\n");
			sb.append("		      ,IFNULL(BB.NEU, 0) AS NEU																		\n");
			sb.append("		      ,IFNULL(BB.TOTAL, 0) AS TOTAL																\n");
			sb.append("		 FROM																									\n");
			sb.append("		  (																											\n");
			sb.append("		  SELECT DATE_FORMAT(A.DATE, '%Y/%m/%d') AS CATEGORY_DATE						\n");
			sb.append("			FROM (                                                                                                                                                                                                         																						\n");
			sb.append("			    SELECT '" + eDate + "' - INTERVAL (A.A + (10 * B.A) + (100 * C.A)) DAY AS DATE                                                                                                                                  																\n");
			sb.append("			    FROM (SELECT 0 AS A UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS A        	\n");
			sb.append("			    CROSS JOIN (SELECT 0 AS A UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS B 	\n");
			sb.append("			    CROSS JOIN (SELECT 0 AS A UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS C 	\n");
			sb.append("			) A                                                                            							               			\n");
			sb.append("			WHERE A.DATE BETWEEN '" + sDate + "' AND '" + eDate + "'                    								\n");
			sb.append("		  )AA LEFT OUTER JOIN																										\n");
			sb.append("		  (																																	\n");
			sb.append("		  SELECT STRAIGHT_JOIN																										\n");
			sb.append("		          DATE_FORMAT(A.MD_DATE, '%Y/%m/%d') AS MD_DATE														\n");
			sb.append("		          ,IFNULL(SUM(IF(B.IC_CODE = 1, 1, 0)),0) AS POS																\n");
			sb.append("		          ,IFNULL(SUM(IF(B.IC_CODE = 2, 1, 0)),0) AS NEG																\n");
			sb.append("		          ,IFNULL(SUM(IF(B.IC_CODE = 3, 1, 0)),0) AS NEU																\n");
			sb.append("		          ,COUNT(A.ID_SEQ) AS TOTAL																						\n");
			sb.append("		  FROM ISSUE_DATA A, ISSUE_DATA_CODE B, ISSUE_DATA_CODE C 													\n");
			sb.append("		  WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59' 							\n");
			
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      												\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    											\n");
				    }else if("3".equals(scope)){
				    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'							\n"); 
				    }
		    }
			
			sb.append("		  AND A.ID_SEQ = B.ID_SEQ																									\n");
			sb.append("		  AND B.IC_TYPE = 9																											\n");
			sb.append("		  AND A.ID_SEQ = C.ID_SEQ																									\n");
			sb.append("		  AND C.IC_TYPE = 1 AND C.IC_CODE = 2		## 분류체계-구분-박원순 서울시장									\n");
			sb.append("		  GROUP BY DATE_FORMAT(A.MD_DATE, '%Y/%m/%d') ) BB ON AA.CATEGORY_DATE = BB.MD_DATE		\n");
			sb.append("		  ORDER BY AA.CATEGORY_DATE ASC																						\n");
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("date", rs.getString("MD_DATE"));
				obj.put("category", rs.getString("DATE"));
				obj.put("column-1", rs.getString("POS"));
				obj.put("column-2", rs.getString("NEG"));
				obj.put("column-3", rs.getString("NEU"));
				obj.put("column-4", rs.getString("TOTAL"));
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
    
    public JSONArray getInfoDivCountData(String scope, String keyword, String sDate, String eDate, String pCode){	
    	conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONArray arr = null; 
   	 	try {
			conn.getDBCPConnection();
			sb.append("	##  정보구분 별 정보량																																\n");
			if( "0".equals(pCode) ){
				sb.append("	   SELECT																																					\n");
				sb.append("	   		B.IC_CODE																																		\n");
				sb.append("	        ,(SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 2 AND IC_CODE = B.IC_CODE) AS IC_NAME						\n");
				sb.append("	        ,COUNT(A.ID_SEQ) AS CNT																													\n");
				sb.append("	        ,SUM(IF(C.IC_CODE = 1 , 1,0)) AS POS				\n");
				sb.append("	        ,SUM(IF(C.IC_CODE = 2 , 1,0)) AS NEG				\n");
				sb.append("	        ,SUM(IF(C.IC_CODE = 3 , 1,0)) AS NEU				\n");
				sb.append("	   FROM ISSUE_DATA A										\n");
				sb.append("	        INNER JOIN ISSUE_DATA_CODE B ON A.ID_SEQ = B.ID_SEQ AND B.IC_TYPE = 2  			\n");
				sb.append("	        INNER JOIN ISSUE_DATA_CODE C ON A.ID_SEQ = C.ID_SEQ AND C.IC_TYPE = 9									\n");
				sb.append("	        INNER JOIN ISSUE_DATA_CODE E ON A.ID_SEQ = E.ID_SEQ AND E.IC_TYPE = 1 AND E.IC_CODE = 2  ## 분류체계-구분-박원순 서울시장					\n");
				sb.append("	   WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'  															\n");
				if(!"".equals(keyword)){
			    	 if("1".equals(scope)){
					    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      												\n");
					    }else if("2".equals(scope)){
					    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    											\n");
					    }else if("3".equals(scope)){
					    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'							\n"); 
					    }
			    }
				
				sb.append("	  GROUP BY B.IC_CODE																					\n");
				sb.append("	  ORDER BY B.IC_CODE ASC																					\n");
			}else{
				
				sb.append("	   SELECT																																					\n");
				sb.append("	   		D.IC_CODE																																		\n");
				sb.append("	        ,(SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 21 AND IC_CODE = D.IC_CODE) AS IC_NAME						\n");
				sb.append("	        ,COUNT(A.ID_SEQ) AS CNT																													\n");
				sb.append("	        ,SUM(IF(C.IC_CODE = 1 , 1,0)) AS POS				\n");
				sb.append("	        ,SUM(IF(C.IC_CODE = 2 , 1,0)) AS NEG				\n");
				sb.append("	        ,SUM(IF(C.IC_CODE = 3 , 1,0)) AS NEU				\n");
				sb.append("	   FROM ISSUE_DATA A										\n");
				sb.append("	        INNER JOIN ISSUE_DATA_CODE B ON A.ID_SEQ = B.ID_SEQ AND B.IC_TYPE = 2  AND B.IC_CODE = " + pCode + "			\n");
				sb.append("	        INNER JOIN ISSUE_DATA_CODE C ON A.ID_SEQ = C.ID_SEQ AND C.IC_TYPE = 9									\n");
				sb.append("	        INNER JOIN ISSUE_DATA_CODE D ON A.ID_SEQ = D.ID_SEQ AND D.IC_TYPE = 21 AND D.IC_CODE IN ( SELECT IC_CODE FROM ISSUE_CODE WHERE IC_TYPE = 21 AND IC_PTYPE = 2 AND IC_PCODE = " + pCode + " )		\n");
				sb.append("	        INNER JOIN ISSUE_DATA_CODE E ON A.ID_SEQ = E.ID_SEQ AND E.IC_TYPE = 1 AND E.IC_CODE = 2  ## 분류체계-구분-박원순 서울시장																															\n");
				sb.append("	   WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'  																																															\n");
				if(!"".equals(keyword)){
			    	 if("1".equals(scope)){
					    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      												\n");
					    }else if("2".equals(scope)){
					    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    											\n");
					    }else if("3".equals(scope)){
					    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'							\n"); 
					    }
			    }
				
				sb.append("	  GROUP BY D.IC_CODE																					\n");
				sb.append("	  ORDER BY D.IC_CODE ASC																					\n");
			}
			
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("iccode", rs.getString("IC_CODE"));
				obj.put("category", rs.getString("IC_NAME"));
				obj.put("cnt", rs.getString("CNT"));
				obj.put("column-1", rs.getString("POS"));
				obj.put("column-2", rs.getString("NEG"));
				obj.put("column-3", rs.getString("NEU"));
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
    
    public JSONArray getMainIssueCountData(String scope, String keyword, String sDate, String eDate){	
    	conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONArray arr = null; 
   	 	try {
			conn.getDBCPConnection();
			sb.append("	## 주요이슈 별 정보량																																\n");
			sb.append("		   SELECT																																			\n");
			sb.append("		   		C.IC_CODE																																\n");
			sb.append("		        ,(SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 4 AND IC_CODE = C.IC_CODE  AND IC_USEYN='Y') AS IC_NAME					\n");
			sb.append("		        ,COUNT(A.ID_SEQ) AS CNT																											\n");
			sb.append("		        , SUM(IF(B.IC_CODE=1, 1, 0)) AS POS																								\n");
			sb.append("	         	, SUM(IF(B.IC_CODE=2, 1, 0)) AS NEG																								\n");
			sb.append("	          	, SUM(IF(B.IC_CODE=3, 1, 0)) AS NEU  																								\n");
			sb.append("		   FROM ISSUE_DATA A																														\n");
			sb.append("		        INNER JOIN ISSUE_DATA_CODE B ON A.ID_SEQ = B.ID_SEQ AND B.IC_TYPE = 9											\n");
			sb.append("		        INNER JOIN ISSUE_DATA_CODE C ON A.ID_SEQ = C.ID_SEQ AND C.IC_TYPE = 4 AND C.IC_CODE NOT IN (19)											\n");
			sb.append("		        INNER JOIN ISSUE_CODE C2 ON C.IC_TYPE = C2.IC_TYPE AND C.IC_CODE = C2.IC_CODE AND C2.IC_USEYN='Y'               \n");
			sb.append("		        INNER JOIN ISSUE_DATA_CODE D ON A.ID_SEQ = D.ID_SEQ AND D.IC_TYPE = 1 AND D.IC_CODE = 2  ## 분류체계-구분-박원순 서울시장			\n");
			sb.append("		   WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'  																				\n");
			
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      																											\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    																										\n");
				    }else if("3".equals(scope)){
				    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'																						\n"); 
				    }
		    }
			
			sb.append("		  GROUP BY C.IC_CODE																																								\n");
			sb.append("		  ORDER BY CNT DESC																																								\n");
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("iccode", rs.getString("C.IC_CODE"));
				obj.put("category", rs.getString("IC_NAME"));
				obj.put("cnt", rs.getString("CNT"));
				obj.put("column-1", rs.getString("POS"));
				obj.put("column-2", rs.getString("NEG"));
				obj.put("column-3", rs.getString("NEU"));
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
    
    public JSONArray getMediaData(String scope, String keyword, String sDate, String eDate){	
    	conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONArray arr = null; 
   	 	try {
			conn.getDBCPConnection();
			sb.append("	## 매체 별 정보량 (검색조건) 																						\n");
			sb.append("	## 매체 별 성향 현황 (검색조건)																						\n");
			sb.append("	SELECT AA.IC_CODE																									\n");
			sb.append("		        ,AA.IC_NAME																								\n");
			sb.append("		        ,IFNULL(BB.CNT, 0) AS TOTAL																			\n");
			sb.append("		        ,IFNULL(BB.POS, 0) AS POS																				\n");
			sb.append("		        ,IFNULL(BB.NEG, 0) AS NEG																				\n");
			sb.append("		        ,IFNULL(BB.NEU, 0) AS NEU																				\n");
			sb.append("	   FROM (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 6 AND IC_USEYN = 'Y' AND IC_CODE > 0 ORDER BY IC_ORDER ASC ) AA			\n");
			sb.append("	LEFT OUTER JOIN																																											\n");
			sb.append("	  ( SELECT B.IC_CODE																																										\n");
			sb.append("	  ,(SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 6 AND IC_CODE = B.IC_CODE) AS IC_NAME																	\n");
			sb.append("	  , COUNT(B.IC_CODE) AS CNT 																																							\n");
			sb.append("	  ,SUM(IF(C.IC_CODE =1 , 1, 0)) AS POS																																				\n");
			sb.append("		,SUM(IF(C.IC_CODE =2 , 1, 0)) AS NEG																																				\n");
			sb.append("		,SUM(IF(C.IC_CODE =3 , 1, 0)) AS NEU																																				\n");
			sb.append("	FROM ISSUE_DATA A																																										\n");
			sb.append("	    INNER JOIN ISSUE_DATA_CODE B ON A.ID_SEQ=B.ID_SEQ AND B.IC_TYPE=6																								\n");
			sb.append("	    INNER JOIN ISSUE_DATA_CODE C ON A.ID_SEQ=C.ID_SEQ AND C.IC_TYPE=9																								\n");
			sb.append("	    INNER JOIN ISSUE_DATA_CODE D ON A.ID_SEQ=D.ID_SEQ AND D.IC_TYPE=1 AND D.IC_CODE=2	## 분류체계-구분-박원순 서울시장							\n");
			sb.append("	WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'  																						\n");

			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      												\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    											\n");
				    }else if("3".equals(scope)){
				    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'							\n"); 
				    }
		    }
			
			sb.append("	GROUP BY B.IC_CODE ) BB ON AA.IC_CODE = BB.IC_CODE\n");
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("iccode", rs.getString("AA.IC_CODE"));
				obj.put("category", rs.getString("AA.IC_NAME"));
				obj.put("column-1", rs.getString("TOTAL"));
				obj.put("column-2", rs.getString("POS"));
				obj.put("column-3", rs.getString("NEG"));
				obj.put("column-4", rs.getString("NEU"));
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
    
    public JSONArray getMainMediaData(String scope, String keyword, String sDate, String eDate, String source, String limit) {
    	conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONArray arr = null; 
   	 	try {
			conn.getDBCPConnection();			
			if("1".equals(source)){
				
				sb.append(" ## 주요 매체 현황 - 언론																														\n");
				sb.append("  SELECT 																																			\n");
				sb.append("          A.S_SEQ																																	\n");
				sb.append("          ,D.CIRCULATION																														\n");
				sb.append("          ,A.MD_SITE_NAME AS NAME																					 					\n");
				sb.append("          ,B.IC_CODE			 																													\n");
				sb.append("          ,SUM(IF(C.IC_CODE = 1 , 1, 0)) AS POS																							\n");
				sb.append("          ,SUM(IF(C.IC_CODE = 2 , 1, 0)) AS NEG																							\n");
				sb.append("          ,SUM(IF(C.IC_CODE = 3 , 1, 0)) AS NEU    																						\n");
				sb.append("          ,COUNT(0) AS CNT																													\n");
				sb.append("   FROM ISSUE_DATA A, ISSUE_DATA_CODE B, ISSUE_DATA_CODE C, PRESS_CIRCULATION D, ISSUE_DATA_CODE E			\n");
				sb.append("   WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'    										\n");
				
				if(!"".equals(keyword)){
			    	 if("1".equals(scope)){
					    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      																\n");
					    }else if("2".equals(scope)){
					    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    															\n");
					    }else if("3".equals(scope)){
					    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'											\n"); 
					    }
			    }
				sb.append("     AND A.MD_TYPE = 1																														\n");
				sb.append("     AND A.ID_SEQ = B.ID_SEQ																													\n");
				sb.append("     AND B.IC_TYPE = 6																															\n");
				sb.append("     AND B.IC_CODE = 1																														\n");
				sb.append("     AND A.ID_SEQ = C.ID_SEQ																												\n");
				sb.append("     AND C.IC_TYPE = 9  																														\n");
				sb.append("     AND A.S_SEQ = D.S_SEQ																													\n");
				sb.append("     AND A.ID_SEQ = E.ID_SEQ AND E.IC_TYPE=1 AND E.IC_CODE=2	## 분류체계-구분-박원순 서울시장						\n");
				sb.append("   GROUP BY A.S_SEQ																															\n");
				sb.append("   ORDER BY D.CIRCULATION DESC																											\n");
				
			}if("2".equals(source)){

				sb.append("  ## 주요 매체현황 - 트위터																											\n");
				sb.append("   SELECT 																																	\n");
				sb.append("          D.T_USER_ID AS NAME																										\n");
				sb.append("          ,A.MD_SITE_NAME  																											\n");
				sb.append("          ,B.IC_CODE			 																											\n");
				sb.append("          ,SUM(IF(C.IC_CODE = 1 , 1, 0)) AS POS																					\n");
				sb.append("          ,SUM(IF(C.IC_CODE = 2 , 1, 0)) AS NEG																					\n");
				sb.append("          ,SUM(IF(C.IC_CODE = 3 , 1, 0)) AS NEU																					\n");
				sb.append("          ,D.T_FOLLOWERS * COUNT(0) AS CNT																						\n");
				sb.append("   FROM ISSUE_DATA A, ISSUE_DATA_CODE B, ISSUE_DATA_CODE C, ISSUE_TWITTER D, ISSUE_DATA_CODE E		\n");
				sb.append("   WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'    								\n");
				
				if(!"".equals(keyword)){
			    	 if("1".equals(scope)){
					    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      														\n");
					    }else if("2".equals(scope)){
					    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    													\n");
					    }else if("3".equals(scope)){
					    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'									\n"); 
					    }
			    }
				
				sb.append("     AND A.ID_SEQ = B.ID_SEQ																											\n");
				sb.append("     AND B.IC_TYPE = 6																													\n");
				sb.append("     AND B.IC_CODE = 2																												\n");
				sb.append("     AND A.ID_SEQ = C.ID_SEQ																										\n");
				sb.append("     AND C.IC_TYPE = 9  																												\n");
				sb.append("     AND A.ID_SEQ = D.ID_SEQ																										\n");
				sb.append("     AND A.ID_SEQ = E.ID_SEQ AND E.IC_TYPE=1 AND E.IC_CODE=2	## 분류체계-구분-박원순 서울시장				\n");
				sb.append("   GROUP BY D.T_USER_ID																												\n");
				sb.append("   ORDER BY CNT DESC 																												\n");

			}else if("3".equals(source) || "4".equals(source)){
				
				if("3".equals(source)){
					sb.append(" ## 주요 매체 현황 - 블로그(3)																									\n");
				}else if("4".equals(source)){
					sb.append(" ## 주요 매체 현황 - 카페(4)																										\n");
				}
				
				sb.append("	SELECT 																																	\n");
				sb.append("		        A.CAFE_NAME AS NAME																									\n");
				sb.append("		        ,A.MD_SITE_NAME																											\n");
				sb.append("	        	,B.IC_CODE			 																										\n");
				sb.append("		        ,SUM(IF(C.IC_CODE = 1 , 1, 0)) AS POS																					\n");
				sb.append("		        ,SUM(IF(C.IC_CODE = 2 , 1, 0)) AS NEG																					\n");
				sb.append("		        ,SUM(IF(C.IC_CODE = 3 , 1, 0)) AS NEU																					\n");
				sb.append("		        ,MAX(A.BLOG_VISIT_COUNT)*1 AS CNT																				\n");
				sb.append("		 FROM ISSUE_DATA A, ISSUE_DATA_CODE B, ISSUE_DATA_CODE C, ISSUE_DATA_CODE E							\n");
				sb.append("		 WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'  								\n");
				
				if(!"".equals(keyword)){
			    	 if("1".equals(scope)){
					    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      														\n");
					    }else if("2".equals(scope)){
					    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    													\n");
					    }else if("3".equals(scope)){
					    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'									\n"); 
					    }
			    }
				
				sb.append("		   AND A.ID_SEQ = B.ID_SEQ																										\n");
				sb.append("		   AND B.IC_TYPE = 6																												\n");
				sb.append("		   AND B.IC_CODE = " + source + "																								\n");
				sb.append("		   AND A.ID_SEQ = C.ID_SEQ																										\n");
				sb.append("		   AND C.IC_TYPE = 9    																											\n");
				sb.append("     	   AND A.ID_SEQ = E.ID_SEQ AND E.IC_TYPE=1 AND E.IC_CODE=2	## 분류체계-구분-박원순 서울시장			\n");
				sb.append("		   AND A.CAFE_NAME IS NOT NULL																							\n");
				sb.append("		   AND A.CAFE_NAME != 'null'																									\n");
				sb.append("		 GROUP BY A.CAFE_NAME																										\n");
				sb.append("		 ORDER BY CNT DESC																												\n");
				
			}
			
			if("0".equals(limit)){
				sb.append("	 LIMIT 0, 10																																\n");
			}else if("1".equals(limit)){
				sb.append("	 LIMIT 10, 10																															\n");
			}
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("category", rs.getString("NAME"));
				
				if("1".equals(source)){
					obj.put("site", rs.getString("A.S_SEQ"));
				}else{
					obj.put("site", rs.getString("A.MD_SITE_NAME"));
				}
				
				obj.put("source", rs.getString("B.IC_CODE"));
				obj.put("column-1", rs.getString("POS"));
				obj.put("column-2", rs.getString("NEG"));
				obj.put("column-3", rs.getString("NEU"));
				obj.put("cnt", rs.getString("CNT"));
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
    
    public JSONArray getRelationKeywordData(String scope, String keyword, String sDate, String eDate, String source, String limit, String senti) {
    	conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONArray arr = new JSONArray();
    	//arr =  (JSONArray)ehCacheUtil.getWidget("getRelationKeywordData"+scope+keyword+sDate+eDate+source+limit+senti);
    	//if (arr == null || "".equals(arr)) {
    	
    	
    	
   	 	try {
   	 		
			conn.getDBCPConnection();
			sb.append(" ## 주요 연관키워드	 - 01																								\n");
			sb.append("	 	SELECT                                                                                                         \n");
			sb.append("	 	      BB.RK_SEQ                                                                                               \n");
			sb.append("	 	      ,(SELECT RK_NAME FROM RELATION_KEYWORD_R WHERE RK_SEQ = BB.RK_SEQ ) AS WORD_NAME      	           \n");
			sb.append("	 	      ,COUNT(0) AS CNT ,COUNT(DISTINCT(AA.ID_SEQ)) AS KEY_CNT                                                                                         \n");
			sb.append("	 	      ,SUM(IF(AA.IC_CODE = 1, 1, 0)) AS POS                                                                    \n");
		    sb.append("	 	      ,SUM(IF(AA.IC_CODE = 2, 1, 0)) AS NEG                                                                    \n");
		    sb.append("	 	      ,SUM(IF(AA.IC_CODE = 3, 1, 0)) AS NEU                                                                    \n");
			sb.append("	 	FROM                                                                                                           \n");
			sb.append("	 	(                                                                                                              \n");
			sb.append("	 	SELECT                                                                                                         \n");
			sb.append("	 	        A.ID_SEQ ,D.IC_CODE                                                                                    \n");
			sb.append("	 	FROM ISSUE_DATA A INNER JOIN ISSUE_DATA_CODE B ON A.ID_SEQ = B.ID_SEQ AND B.IC_TYPE = 1 AND B.IC_CODE = 2      \n");
			sb.append("	 	                  INNER JOIN ISSUE_DATA_CODE C ON A.ID_SEQ = C.ID_SEQ AND C.IC_TYPE = 6     \n");
			if(!"0".equals(source) ){
			sb.append("						  AND C.IC_CODE IN (" + source + ")											\n");
			}
			sb.append("	 	                  INNER JOIN ISSUE_DATA_CODE D ON A.ID_SEQ = D.ID_SEQ AND D.IC_TYPE = 9     \n");
			if(!"0".equals(senti) ){
			sb.append("						  AND D.IC_CODE IN (" + senti + ")											\n");
			}
			sb.append("	 	WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'               \n");
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      						\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    						\n");
				    }else if("3".equals(scope)){
				    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'				\n"); 
				    }
		    }
			sb.append("	 	) AA INNER JOIN RELATION_KEYWORD_MAP BB ON AA.ID_SEQ = BB.ID_SEQ                          \n");
			sb.append("	 	GROUP BY BB.RK_SEQ                                                                         \n");
			sb.append("	 	ORDER BY CNT DESC                                                                           \n");
			sb.append("	 LIMIT " + limit + "																			\n");						
			
			System.out.println(sb.toString());
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("patcode", rs.getString("PAT_SEQ"));
				obj.put("wordname", rs.getString("WORD_NAME"));
				obj.put("cnt", rs.getString("CNT"));
				obj.put("keycnt", rs.getString("KEY_CNT"));
				obj.put("pos", rs.getString("POS"));
				obj.put("neu", rs.getString("NEU"));
				obj.put("neg", rs.getString("NEG"));
				arr.put(obj);
			}
			System.out.println( arr.toString() );
   	 	}catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        
   	 	//ehCacheUtil.addWidget("getRelationKeywordData"+scope+keyword+sDate+eDate+source+limit+senti, arr);
    	//}
   	 	return arr;
    }
    
    public JSONArray getRelationKeywordSentiData(String scope, String keyword, String sDate, String eDate, String senti) {
    	conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONArray arr = null; 
   	 	try {
			conn.getDBCPConnection();
			//sb.append(" ## 연관키워드 현황																															\n");
			//sb.append("	SELECT                                                                                           												\n");
			//sb.append("	           B.PAT_SEQ                                                                               											\n");
			//sb.append("	          ,(SELECT WORD_NM FROM ANA_TOPIC_DICTIONARY WHERE PAT_SEQ = B.PAT_SEQ ) AS WORD_NAME      	\n");
			//sb.append("	          ,COUNT(DISTINCT A.ID_SEQ) AS CNT                                                                							\n");
			//sb.append("	    FROM ISSUE_DATA A, ISSUE_RELATION_KEYWORD B, ISSUE_DATA_CODE C, ISSUE_DATA_CODE D 					\n");
			//sb.append("	   WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'             						\n");
            //
			//if(!"".equals(keyword)){
		    //	 if("1".equals(scope)){
			//	    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      															\n");
			//	    }else if("2".equals(scope)){
			//	    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    														\n");
			//	    }else if("3".equals(scope)){
			//	    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'										\n"); 
			//	    }
		    //}
			//
			//sb.append("	     AND A.ID_SEQ = B.ID_SEQ                                                                      										\n");
			//sb.append("	     AND A.ID_SEQ = C.ID_SEQ                                                                       									\n");
			//sb.append("	     AND C.IC_TYPE = 9                                                                       		 										\n");
			//sb.append("	     AND C.IC_CODE = " + senti + "																										\n");
			//sb.append("	     AND A.ID_SEQ = D.ID_SEQ AND D.IC_TYPE=1 AND D.IC_CODE=2	## 분류체계-구분-박원순 서울시장				\n");
			//sb.append("	   GROUP BY B.PAT_SEQ                                                                            										\n");
			//sb.append("	   ORDER BY CNT DESC                                                                             										\n");
			//sb.append("	   LIMIT 10																																	\n");
			sb.append("   SELECT                                                                                                            \n");
			sb.append("         BB.RK_SEQ                                                                                                  \n");
			sb.append("         ,(SELECT RK_NAME FROM RELATION_KEYWORD_R WHERE RK_SEQ = BB.RK_SEQ ) AS WORD_NAME      	                \n");
			sb.append("         ,COUNT(0) AS CNT                                                                                            \n");
			sb.append("   FROM                                                                                                              \n");
			sb.append("   (                                                                                                                 \n");
			sb.append("   SELECT                                                                                                            \n");
			sb.append("           A.ID_SEQ                                                                                                  \n");
			sb.append("   FROM ISSUE_DATA A INNER JOIN ISSUE_DATA_CODE B ON A.ID_SEQ = B.ID_SEQ AND B.IC_TYPE = 9 AND B.IC_CODE = " + senti + "       \n");
			sb.append("   WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'                                           \n");
			if(!"".equals(keyword)){
				 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      												\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    											\n");
				    }else if("3".equals(scope)){
				    	sb.append("  AND CONCAT(A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'									\n"); 
				    }
			}
			sb.append("   ) AA INNER JOIN RELATION_KEYWORD_MAP BB ON AA.ID_SEQ = BB.ID_SEQ                                                \n");
			sb.append("   GROUP BY BB.RK_SEQ                                                                                               \n");
			sb.append("   ORDER BY CNT DESC                                                                                                 \n");
			sb.append("   LIMIT 10                                                                                                          \n");
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("patcode", rs.getString("RK_SEQ"));
				obj.put("wordname", rs.getString("WORD_NAME"));
				obj.put("cnt", rs.getString("CNT"));
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
    
    public JSONObject getRelaltionInfoList(int nowPage, int rowCnt, String scope, String keyword, String sDate, String eDate, String senti, String source) {
    	int startNum = (nowPage-1)*rowCnt;
    	
    	conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONObject obj = null;
    	JSONArray arr = null; 
   	 	try {
			conn.getDBCPConnection();
			sb.append("	## 관련정보 - COUNT																														\n");
			sb.append("	SELECT COUNT(DISTINCT A.MD_PSEQ) AS TOTAL																					\n");
			sb.append("	     FROM ISSUE_DATA A, ISSUE_DATA_CODE B, ISSUE_DATA_CODE D, ISSUE_DATA_CODE E 								\n");
			sb.append("	 WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59' 										\n");
			
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      															\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    														\n");
				    }else if("3".equals(scope)){
				    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'										\n"); 
				    }
		    }
			
			sb.append("	     AND A.ID_SEQ = B.ID_SEQ																											\n");
			sb.append("	     AND B.IC_TYPE = 9																													\n");
			sb.append("	     AND B.IC_CODE = " + senti + "																										\n");
			sb.append("	     AND A.ID_SEQ = D.ID_SEQ																											\n");
			sb.append("	     AND D.IC_TYPE = 6																													\n");
			
			if(!"0".equals(source)){
				sb.append(" AND D.IC_CODE IN (" + source + ")  																								\n");	
			}
		
			sb.append("	     AND A.ID_SEQ = E.ID_SEQ AND E.IC_TYPE=1 AND E.IC_CODE=2	## 분류체계-구분-박원순 서울시장					\n");

			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			obj = new JSONObject();
			while(rs.next()){
				obj.put("total", rs.getString("TOTAL"));
			}
			
			sb = new StringBuilder();
			sb.append(" ## 관련정보																																\n");
			sb.append("	SELECT A.ID_SEQ																														\n");
			sb.append("	         ,DATE_FORMAT(A.MD_DATE, '%y/%m/%d') AS MD_DATE																\n");
			sb.append("	         ,A.ID_TITLE																														\n");
			sb.append("	         ,A.ID_URL																														\n");
			sb.append("	         ,A.S_SEQ																															\n");
			sb.append("	         ,A.MD_SITE_NAME																												\n");
			sb.append("	         ,A.MD_SAME_CT																												\n");
			sb.append("	 FROM ISSUE_DATA A, ISSUE_DATA_CODE B, ISSUE_DATA_CODE D, ISSUE_DATA_CODE E 								\n");
			sb.append("	       WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59' 							\n");
			
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      														\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    													\n");
				    }else if("3".equals(scope)){
				    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'									\n"); 
				    }
		    }
			
			sb.append("	         AND A.ID_SEQ = B.ID_SEQ																									\n");
			sb.append("	         AND B.IC_TYPE = 9																											\n");
			sb.append("	         AND B.IC_CODE = " + senti + "																								\n");
			sb.append("	         AND A.ID_SEQ = D.ID_SEQ																									\n");
			sb.append("	         AND D.IC_TYPE = 6																											\n");
			
			if(!"0".equals(source)){
				sb.append(" AND D.IC_CODE IN (" + source + ")  																							\n");	
			}
			
			sb.append("	         AND A.ID_SEQ = E.ID_SEQ AND E.IC_TYPE=1 AND E.IC_CODE=2	## 분류체계-구분-박원순 서울시장			\n");
			sb.append(" 		GROUP BY A.MD_PSEQ																											\n");
			sb.append(" 		ORDER BY A.MD_DATE DESC																									\n");
			sb.append("  LIMIT " + startNum + "," + rowCnt + "																							\n");
		    
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj2 = new JSONObject();
				obj2.put("idcode", rs.getString("A.ID_SEQ"));
				obj2.put("date", rs.getString("MD_DATE"));
				obj2.put("title", rs.getString("A.ID_TITLE"));
				obj2.put("url", rs.getString("A.ID_URL"));
				obj2.put("sitecode", rs.getString("A.S_SEQ"));
				obj2.put("sitename", rs.getString("A.MD_SITE_NAME"));
				obj2.put("samecnt", rs.getString("A.MD_SAME_CT"));
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
    
    public JSONArray getRelaltionInfoList_excel(String scope, String keyword, String sDate, String eDate, String senti, String source) {
    	conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONArray arr = null; 
   	 	try {
			conn.getDBCPConnection();
			sb.append(" ## 관련정보 - 엑셀 다운로드																											\n");
			sb.append("	SELECT A.ID_SEQ																														\n");
			sb.append("	         ,DATE_FORMAT(A.MD_DATE, '%y/%m/%d') AS MD_DATE																\n");
			sb.append("	         ,A.ID_TITLE																														\n");
			sb.append("	         ,A.ID_URL																														\n");
			sb.append("	         ,A.S_SEQ																															\n");
			sb.append("	         ,A.MD_SITE_NAME																												\n");
			sb.append("	         ,A.MD_SAME_CT																												\n");
			sb.append("	 FROM ISSUE_DATA A, ISSUE_DATA_CODE B, ISSUE_DATA_CODE D, ISSUE_DATA_CODE E 								\n");
			sb.append("	       WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59' 							\n");
			sb.append("	         AND A.ID_SEQ = E.ID_SEQ AND E.IC_TYPE=1 AND E.IC_CODE=2	## 분류체계-구분-박원순 서울시장			\n");
			
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      														\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    													\n");
				    }else if("3".equals(scope)){
				    	sb.append("  AND CONCAT_WS(' ', A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'									\n"); 
				    }
		    }
			
			sb.append("	         AND A.ID_SEQ = B.ID_SEQ																									\n");
			sb.append("	         AND B.IC_TYPE = 9																											\n");
			sb.append("	         AND B.IC_CODE = " + senti + "																								\n");
			sb.append("	         AND A.ID_SEQ = D.ID_SEQ																									\n");
			sb.append("	         AND D.IC_TYPE = 6																											\n");
			
			if(!"0".equals(source)){
				sb.append(" AND D.IC_CODE IN (" + source + ")  																							\n");	
			}
			sb.append(" 		ORDER BY MD_DATE DESC																									\n");
			sb.append("  LIMIT 1000																																\n");
		    
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("idcode", rs.getString("A.ID_SEQ"));
				obj.put("date", rs.getString("MD_DATE"));
				obj.put("title", rs.getString("A.ID_TITLE"));
				obj.put("url", rs.getString("A.ID_URL"));
				obj.put("sitecode", rs.getString("A.S_SEQ"));
				obj.put("sitename", rs.getString("A.MD_SITE_NAME"));
				obj.put("samecnt", rs.getString("A.MD_SAME_CT"));
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
}
