package risk.dashboard.summary;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONException;
import risk.json.JSONObject;
import risk.util.ConfigUtil;
import risk.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jdom.Element;
import org.jdom.JDOMException;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

public class SummaryMgr {
	DBconn conn = null;
	Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    StringBuilder sb = null;
    
    ConfigUtil cu = new ConfigUtil();
    String lucyKey = cu.getConfig("LUCY_API");
    
    private String p_docid = "";
	private int totalCnt = 0;
	private int replyTotalCnt = 0;
	
	private String apiHost = "";
	
	public int getPortalListCount(){
		return totalCnt;
	}
	
	public String getP_docides(){
		return p_docid;
	}
	
	public int getReplyTotalCnt(){
		return replyTotalCnt;
	}
	
	public JSONArray getOnlineTrendData(String scope, String keyword, String sDate, String eDate,String tier){
		conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONArray arr = null; 
   	 	try {
   	 		conn.getDBCPConnection();
			sb.append("	## 온라인 동향																								\n");
			sb.append("		 SELECT																									\n");
			sb.append("		      DATE_FORMAT(AA.CATEGORY_DATE, '%Y-%m-%d') AS MD_DATE						\n");
			sb.append("		      ,DATE_FORMAT(AA.CATEGORY_DATE, '%m/%d') AS DATE								\n");
			sb.append("		      ,IFNULL(BB.TOTAL, 0) AS TOTAL																\n");
			sb.append("		      ,IFNULL(BB.CNT1, 0) AS CNT1																	\n");
			//sb.append("		      ,IFNULL(BB.CNT2, 0) AS CNT2																	\n");
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
			sb.append("		          ,COUNT(A.ID_SEQ) AS TOTAL																						\n");
			sb.append("		          ,SUM(IF(B.IC_CODE=1, 1, 0)) AS CNT1																			\n");	// 분류체계-구분-서울특별시청
			//sb.append("		          ,SUM(IF(B.IC_CODE=2, 1, 0)) AS CNT2																			\n");	// 분류체계-구분-박원순 서울시장
			sb.append("		  FROM ISSUE_DATA A, ISSUE_DATA_CODE B							 													\n");
			if(!"".equals(tier)){
				sb.append(" 		,TIER_SITE TS \n");
			}
			
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
			
			if(!"".equals(tier)){
				sb.append("		  AND A.S_SEQ = TS.TS_SEQ																								\n");
				sb.append("		  AND TS.TS_TYPE IN ("+tier+")																							\n");
			}
			sb.append("		  AND A.ID_SEQ = B.ID_SEQ																									\n");
			sb.append("		  AND B.IC_TYPE = 1																											\n");
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
				obj.put("column-1", rs.getString("TOTAL"));
				obj.put("column-2", rs.getString("CNT1"));
				//obj.put("column-3", rs.getString("CNT2"));
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
	
	public JSONArray getChannelSentiData01(String scope, String keyword, String sDate, String eDate, String op,String tier){
    	conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONArray arr = null; 
   	 	try {
			conn.getDBCPConnection();
			sb.append("	## 주요 시정 채널 별 성향 ( 왼쪽차트 )																							\n");
			sb.append("	## 서울시장 채널 별 성향	( 왼쪽차트 )																							\n");
			sb.append("	  SELECT																						\n");
			sb.append("	      COUNT(0) AS CNT,																	\n");
			sb.append("	      IFNULL(SUM(IF(B.IC_CODE=1, 1, 0)), 0) AS POS,																\n");
			sb.append("	      IFNULL(SUM(IF(B.IC_CODE=2, 1, 0)), 0) AS NEG,																\n");
			sb.append("	      IFNULL(SUM(IF(B.IC_CODE=3, 1, 0)), 0) AS NEU																\n");
			sb.append("	    FROM ISSUE_DATA A, ISSUE_DATA_CODE B, ISSUE_DATA_CODE C, ISSUE_DATA_CODE D			\n");
			if(!"".equals(tier)){
				sb.append(" 	,TIER_SITE TS \n");
			}
			
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
			
			if(!"".equals(tier)){
				sb.append("		  AND A.S_SEQ = TS.TS_SEQ																								\n");
				sb.append("		  AND TS.TS_TYPE IN ("+tier+")																							\n");
			}
			sb.append("	      AND A.ID_SEQ = B.ID_SEQ																								\n");
			sb.append("	      AND B.IC_TYPE = 9																										\n");
			sb.append("	      AND A.ID_SEQ = C.ID_SEQ 																								\n");
			sb.append("	      AND C.IC_TYPE = 6																										\n");
			sb.append("	      AND A.ID_SEQ = D.ID_SEQ																								\n");
			sb.append("	      AND D.IC_TYPE = 1 AND D.IC_CODE = 1																			\n");
			// 02(section) - 주요 시정 채널 별 성향
			// 03(section) - 서울시장 채널 별 성향 
			//if("02".equals(op)){
			//	sb.append("	    AND D.IC_TYPE = 4																										\n");
			//}else if("03".equals(op)){
			//	sb.append("	    AND D.IC_TYPE = 1 AND D.IC_CODE = 2																			\n");
			//}
			
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
	
	public JSONArray getChannelSentiData02(String scope, String keyword, String sDate, String eDate, String op,String tier){	
		conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONArray arr = null; 
   	 	try {
   	 		conn.getDBCPConnection();
			sb.append("	## 주요 시정 채널 별 성향 ( 오른쪽차트 )																							\n");
			sb.append("	## 서울시장 채널 별 성향	( 오른쪽차트 )																							\n");
			sb.append("	SELECT AA.IC_CODE																									\n");
			sb.append("		        ,AA.IC_NAME																								\n");
			sb.append("		        ,IFNULL(BB.CNT, 0) AS TOTAL																			\n");
			sb.append("		        ,IFNULL(BB.POS, 0) AS POS																				\n");
			sb.append("		        ,IFNULL(BB.NEG, 0) AS NEG																				\n");
			sb.append("		        ,IFNULL(BB.NEU, 0) AS NEU																				\n");
			sb.append("	   FROM (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 6 AND IC_USEYN = 'Y' AND IC_CODE > 0 ORDER BY IC_ORDER ASC ) AA			\n");
			sb.append("	LEFT OUTER JOIN																																											\n");
			sb.append("	  ( SELECT B.IC_CODE																																										\n");
			//sb.append("	  ,(SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 6 AND IC_CODE = B.IC_CODE) AS IC_NAME																	\n");
			sb.append("	  , COUNT(B.IC_CODE) AS CNT 																																							\n");
			sb.append("	  ,SUM(IF(C.IC_CODE =1 , 1, 0)) AS POS																																				\n");
			sb.append("		,SUM(IF(C.IC_CODE =2 , 1, 0)) AS NEG																																				\n");
			sb.append("		,SUM(IF(C.IC_CODE =3 , 1, 0)) AS NEU																																				\n");
			sb.append("	FROM ISSUE_DATA A																																										\n");
			sb.append("	    INNER JOIN ISSUE_DATA_CODE B ON A.ID_SEQ=B.ID_SEQ AND B.IC_TYPE=6																								\n");
			sb.append("	    INNER JOIN ISSUE_DATA_CODE C ON A.ID_SEQ=C.ID_SEQ AND C.IC_TYPE=9																								\n");
			sb.append("	    INNER JOIN ISSUE_DATA_CODE D ON A.ID_SEQ=D.ID_SEQ AND D.IC_TYPE=1 AND D.IC_CODE=1																	\n");
			if(!"".equals(tier)){
				sb.append("	    INNER JOIN TIER_SITE TS ON A.S_SEQ=TS.TS_SEQ AND TS.TS_TYPE IN ("+tier+")																	\n");
			}
			// 02(section) - 주요 시정 채널 별 성향
			// 03(section) - 서울시장 채널 별 성향 
			//if("02".equals(op)){
			//	sb.append("	    INNER JOIN ISSUE_DATA_CODE D ON A.ID_SEQ=D.ID_SEQ AND D.IC_TYPE=4																							\n");
			//}else if("03".equals(op)){
			//	sb.append("	    INNER JOIN ISSUE_DATA_CODE D ON A.ID_SEQ=D.ID_SEQ AND D.IC_TYPE=1 AND D.IC_CODE=2																	\n");
			//} 
			
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
	
	public JSONObject getPotalTopList(String scope, String keyword, String sDate, String eDate, String tab, int nowPage){
		return getPotalTopList( scope,  keyword,  sDate,  eDate,  tab,  nowPage, false);
	}
	
	public JSONObject getPotalTopList(String scope, String keyword, String sDate, String eDate, String tab, int nowPage, boolean excelChk){
		conn = new DBconn();
    	
    	int startNum = 0;
    	startNum = (nowPage-1) * 5;
    	JSONObject result = new JSONObject();
    	JSONArray arr = null; 
   	 	try {
   	 		conn.getDBCPConnection();
   	 		if(!excelChk){
	   	 		sb = new StringBuilder();	
			 	sb.append("	## 포탈 TOP 노출 현황																						\n");
				sb.append("	SELECT                                                                    					     		\n");
				sb.append("	    COUNT(A.T_SEQ) AS CNT                                                     					   		\n");
				sb.append("	FROM TOP A, TOP_IDX B                                                  				        			\n");
				sb.append("	WHERE A.T_DATETIME BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'			\n");
				
				if(!"".equals(keyword)){
					sb.append("  AND A.T_TITLE LIKE '%" + keyword + "%'                      										\n");
			    }
				//if(!"0".equals(tab)){
				//	if("1".equals(tab)){
						sb.append(" AND B.K_XP = 1 AND B.K_ZP IN (1,4,9)                                              										\n");
				//	}else{
				//		sb.append(" AND B.K_XP = 1 AND B.K_YP IN (2,3)                                              										\n");	
				//	}
				//		
				//}
				sb.append("	AND A.T_SEQ = B.T_SEQ                                              										\n");
				sb.append("	AND B.ISSUE_CHECK='N'                                              										\n");
				pstmt = null;
				pstmt = conn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				System.out.println(sb.toString());
				if(rs.next()){
					result.put("totalCnt", rs.getInt("CNT"));
				}
   	 		}
			sb = new StringBuilder();	
			sb.append("	## 포탈 TOP 노출 현황																						\n");
			sb.append("	SELECT                                                                    					     				\n");
			sb.append("	    A.T_SEQ                                                               					   					\n");
			sb.append("	    ,A.T_SITE                                                             					  						\n");
			sb.append("	    ,A.S_SEQ                                                             					  						\n");
			sb.append("	    ,A.T_BOARD                                                            					    				\n");
			sb.append("	    ,A.T_TITLE                                                            					   					\n");
			sb.append("	    ,A.T_URL                                                              					   					\n");
			sb.append("	    ,date_format(A.T_DATETIME, '%m/%d %T') AS  T_DATETIME              							\n");
			sb.append("	    ,date_format(A.T_DATETIME, '%Y%m%d') AS  FULL_DATETIME            							\n");
			sb.append("	    ,date_format(A.T_PRESENTTIME, '%m/%d %T')  AS  T_PRESENTTIME       						\n");
			sb.append("	    ,(A.T_PRESENTTIME - A.T_DATETIME)                                  							        \n");
			sb.append("	FROM TOP A, TOP_IDX B                                                  				        			\n");
			sb.append("	WHERE A.T_DATETIME BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'			\n");
			
			if(!"".equals(keyword)){
				sb.append("  AND A.T_TITLE LIKE '%" + keyword + "%'                      										\n");
		    }
			//if(!"0".equals(tab)){
			//	if("1".equals(tab)){
					sb.append(" AND B.K_XP = 1 AND B.K_ZP IN (1,4,9)                                              										\n");
			//	}else{
			//		sb.append(" AND B.K_XP = 1 AND B.K_ZP IN (2,3)                                              										\n");	
			//	}	
			//}
			sb.append("	AND A.T_SEQ = B.T_SEQ                                              										\n");
			sb.append("	AND B.ISSUE_CHECK='N'                                              										\n");
			sb.append("	ORDER BY FULL_DATETIME DESC                                                        						\n");
			if(!excelChk){
				sb.append("	LIMIT "+startNum+", 5																					\n");	
			}

			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("sitecode", rs.getString("A.S_SEQ"));
				obj.put("sitename", rs.getString("A.T_SITE"));				
				obj.put("boardname", rs.getString("A.T_BOARD"));
				obj.put("title", rs.getString("A.T_TITLE"));
				obj.put("url", rs.getString("A.T_URL"));
				obj.put("date", rs.getString("T_DATETIME"));
				arr.put(obj);
			}
			
			result.put("list", arr);
			
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
	
	public JSONObject getSocialIssueList(int nowPage, int rowCnt, String scope, String keyword, String sDate, String eDate){
		int startNum = (nowPage-1)*rowCnt;
		
		conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONObject obj = null;
    	JSONArray arr = null;
    	
   	 	try {
   	 		conn.getDBCPConnection();		
   	 		
   	 		sb.append("##소셜 이슈 리스트 - COUNT																								\n");
   	 		sb.append("SELECT COUNT(A.MD_SEQ) AS TOTAL                                                                                    \n");
   	 		sb.append("  FROM META A, IDX B                                                        									 			\n");
   	 		if( !"".equals(keyword) && "3".equals(scope)){
   	 		sb.append(" , DATA C	\n");
   	 		}
   	 		sb.append(" WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'     				\n");
	 		sb.append("   AND A.MD_SEQ = B.MD_SEQ                                                    											\n");
	 		sb.append("   AND B.SG_SEQ NOT IN (42,45)        ##언론사,포탈                                      							\n");
	 		sb.append("   AND B.K_XP = 1                                                            													\n");
   	 		sb.append("   AND B.K_YP = 1                                                              													\n");
   	 		sb.append("   AND B.K_ZP IN (3,4)     # 1.대구광역시 2.대구시장                                           									\n");
   	 		sb.append("   AND A.MD_SEQ = A.MD_PSEQ   																						\n");
   	 		
	   	 	if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.MD_TITLE LIKE '%" + keyword + "%'                      											\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.MD_CONTENT LIKE '%" + keyword + "%'                    									\n");
				    }else if("3".equals(scope)){
				    	sb.append("	AND A.MD_SEQ = C.MD_SEQ \n");
				    	sb.append("  AND CONCAT_WS(' ', A.MD_TITLE, C.MD_CONTENT) LIKE '%" + keyword + "%'				\n"); 
				    }
		    }

			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			obj = new JSONObject();
			while(rs.next()){
				obj.put("total", rs.getString("TOTAL"));
			}
			
			sb = new StringBuilder();	
			sb.append(" ##소셜 이슈 리스트																										\n");
			sb.append(" SELECT                                                                       												\n");
			sb.append("        A.MD_TITLE                                                            											\n");
			sb.append("       ,A.MD_URL                                                              											\n");
			sb.append("       ,A.MD_SITE_NAME                                                        										\n");
			sb.append("       ,A.S_SEQ                                                       														\n");
			sb.append("       ,DATE_FORMAT(A.MD_DATE, '%m/%d') AS MD_DATE                         						\n");
			sb.append("       ,A.MD_SAME_COUNT                                                       										\n");
			sb.append("   FROM META A, IDX B                                                         										\n");
			if( !"".equals(keyword) && "3".equals(scope)){
	   	 		sb.append(" , DATA C	\n");
	   	 	}
			sb.append("  WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'  			\n");
			sb.append("    AND A.MD_SEQ = B.MD_SEQ                                                  									\n");
			sb.append("    AND B.SG_SEQ NOT IN (42,45)        ##언론사,포탈                                     					\n");
			sb.append("    AND B.K_XP = 1                                                          											\n");
			sb.append("    AND B.K_YP = 1                                                            											\n");
			sb.append("    AND B.K_ZP IN (3,4)    # 1.대구광역시 2.대구시장                                          							\n");
			sb.append("    AND A.MD_SEQ = A.MD_PSEQ                                                  								\n");
			
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.MD_TITLE LIKE '%" + keyword + "%'                      									\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.MD_CONTENT LIKE '%" + keyword + "%'                    							\n");
				    }else if("3".equals(scope)){
				    	sb.append("	AND A.MD_SEQ = C.MD_SEQ \n");
				    	sb.append("  AND CONCAT_WS(' ', A.MD_TITLE, C.MD_CONTENT) LIKE '%" + keyword + "%'		\n"); 
				    }
		    }
			
			sb.append("  ORDER BY A.MD_SAME_COUNT DESC, A.MD_DATE DESC												\n");	
			sb.append("  LIMIT " + startNum + "," + rowCnt + "																			\n");
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj2 = new JSONObject();
				obj2.put("sitecode", rs.getString("S_SEQ"));
				obj2.put("sitename", rs.getString("MD_SITE_NAME"));
				obj2.put("date", rs.getString("MD_DATE"));
				obj2.put("title", rs.getString("MD_TITLE"));
				obj2.put("url", rs.getString("MD_URL"));
				obj2.put("samecnt", rs.getString("MD_SAME_COUNT"));
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
	
	public JSONArray getSocialIssueList_excel(String scope, String keyword, String sDate, String eDate){
		conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONArray arr = null;
   	 	try {
   	 		conn.getDBCPConnection();			
	   	 	sb.append(" ##소셜 이슈 리스트																										\n");
			sb.append(" SELECT                                                                       												\n");
			sb.append("        A.MD_TITLE                                                            											\n");
			sb.append("       ,A.MD_URL                                                              											\n");
			sb.append("       ,A.MD_SITE_NAME                                                        										\n");
			sb.append("       ,A.S_SEQ                                                       														\n");
			sb.append("       ,DATE_FORMAT(A.MD_DATE, '%m/%d') AS MD_DATE                         						\n");
			sb.append("       ,A.MD_SAME_COUNT                                                       										\n");
			sb.append("   FROM META A, IDX B                                                         										\n");
			if( !"".equals(keyword) && "3".equals(scope)){
	   	 		sb.append(" , DATA C	\n");
	   	 	}
			sb.append("  WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'  			\n");
			sb.append("    AND A.MD_SEQ = B.MD_SEQ                                                  									\n");
			sb.append("    AND B.SG_SEQ NOT IN (42,45)        ##언론사,포탈                                     					\n");
			sb.append("   AND B.K_XP = 1                                                            													\n");
   	 		sb.append("   AND B.K_YP = 1                                                              													\n");
   	 		sb.append("   AND B.K_ZP IN (3,4)    # 1.대구광역시 2.대구시장                                           									\n");
			sb.append("    AND A.MD_SEQ = A.MD_PSEQ                                                  								\n");
			
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.MD_TITLE LIKE '%" + keyword + "%'                      									\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.MD_CONTENT LIKE '%" + keyword + "%'                    							\n");
				    }else if("3".equals(scope)){
				    	sb.append("	AND A.MD_SEQ = C.MD_SEQ \n");
				    	sb.append("  AND CONCAT_WS(' ', A.MD_TITLE, C.MD_CONTENT) LIKE '%" + keyword + "%'		\n"); 
				    }
		    }
		
			sb.append("  ORDER BY A.MD_SAME_COUNT DESC, A.MD_DATE DESC												\n");
			sb.append("  LIMIT 1000 																												\n");
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("sitecode", rs.getString("S_SEQ"));
				obj.put("sitename", rs.getString("MD_SITE_NAME"));
				obj.put("date", rs.getString("MD_DATE"));
				obj.put("title", rs.getString("MD_TITLE"));
				obj.put("url", rs.getString("MD_URL"));
				obj.put("samecnt", rs.getString("MD_SAME_COUNT"));
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
	
	/*public JSONObject getAlimiAppList(int nowPage, int rowCnt, String scope, String keyword, String sDate, String eDate){
		int startNum = (nowPage-1)*rowCnt;
		
		conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONObject obj = null;
    	JSONArray arr = null;
    	
   	 	try {
   	 		conn.getDBCPConnection();
			sb.append("	## 알리미앱글 목록 - COUNT										 										\n");
			sb.append("	    SELECT  COUNT(DISTINCT A.MT_NO ) AS TOTAL                               						\n");
			sb.append("	      FROM MALIMI_LOG A                                     									          	\n"); 
			sb.append("	     WHERE A.MT_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'     	\n");
			
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.MT_TITLE LIKE '%" + keyword + "%'                      							\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.MT_CONTENT LIKE '%" + keyword + "%'                    							\n");
				    }else if("3".equals(scope)){
				    	sb.append("  AND CONCAT_WS(' ', A.MT_TITLE, A.MT_CONTENT) LIKE '%" + keyword + "%'		\n"); 
				    }
		    }
			
			sb.append("	       AND MAL_TYPE = 1														                                \n");
			sb.append("	       AND AS_SEQ IN (1,2)														                        \n");
			sb.append("	    ORDER BY A.MAL_SEQ DESC 																			\n");

			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			obj = new JSONObject();
			while(rs.next()){
				obj.put("total", rs.getString("TOTAL"));
			}
			
			sb = new StringBuilder();
			sb.append(" ## 알리미앱글 목록 																							\n");
			sb.append("     SELECT  DISTINCT A.MT_NO                                                      						\n");
			sb.append("            ,A.S_SEQ                                                             									\n");
			sb.append("            ,A.AS_TITLE                                                                      						\n");
            sb.append("            ,A.SD_NAME                                                                       					\n");
			sb.append("            ,date_format(A.MT_DATE,'%Y-%m-%d') AS MT_DATE                             			\n");
			sb.append("            ,A.MT_TITLE                                                          									\n");
			sb.append("            ,A.MT_URL                                                           									\n");
			sb.append("       FROM MALIMI_LOG A                                                         							\n");
			sb.append("      WHERE A.MT_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'     		\n");
			
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.MT_TITLE LIKE '%" + keyword + "%'                      								\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.MT_CONTENT LIKE '%" + keyword + "%'                    								\n");
				    }else if("3".equals(scope)){
				    	sb.append("  AND CONCAT_WS(' ', A.MT_TITLE, A.MT_CONTENT) LIKE '%" + keyword + "%'			\n"); 
				    }
		    }
			
			sb.append("        AND MAL_TYPE = 1														  									\n");
			sb.append("	       AND AS_SEQ IN (1,2)														                        \n");
			sb.append(" ORDER BY A.MAL_SEQ DESC 																						\n");
			sb.append("  LIMIT " + startNum + "," + rowCnt + "																		\n");
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj2 = new JSONObject();
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
	
	public JSONArray getAlimiAppList_excel(String scope, String keyword, String sDate, String eDate){
		conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONArray arr = null;
   	 	try {
   	 		conn.getDBCPConnection();			
			sb.append(" ## 알리미앱글 목록 - 엑셀 다운로드																				\n");
			sb.append("     SELECT  DISTINCT A.MT_NO                                                      							\n");
			sb.append("            ,A.S_SEQ                                                             										\n");
			sb.append("            ,A.SD_NAME                                                             									\n");
			sb.append("            ,A.AS_TITLE                                                             									\n");
			sb.append("            ,date_format(A.MT_DATE,'%Y-%m-%d') AS MT_DATE                                             \n");
			sb.append("            ,A.MT_TITLE                                                          										\n");
			sb.append("            ,A.MT_URL                                                           										\n");
			sb.append("       FROM MALIMI_LOG A                                                         								\n");
			sb.append("      WHERE A.MT_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'     		\n");
			
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.MT_TITLE LIKE '%" + keyword + "%'                      								\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.MT_CONTENT LIKE '%" + keyword + "%'                    								\n");
				    }else if("3".equals(scope)){
				    	sb.append("  AND CONCAT_WS(' ', A.MT_TITLE, A.MT_CONTENT) LIKE '%" + keyword + "%'			\n"); 
				    }
		    }
			
			sb.append("        AND MAL_TYPE = 1														  									\n");
			sb.append(" ORDER BY A.MAL_SEQ DESC																						\n");
			sb.append("  LIMIT 1000 																											\n");
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("sitecode", rs.getString("S_SEQ"));
				obj.put("sitename", rs.getString("SD_NAME"));
				obj.put("date", rs.getString("MT_DATE"));
				obj.put("title", rs.getString("MT_TITLE"));
				obj.put("url", rs.getString("MT_URL"));
				obj.put("asTitle", rs.getString("AS_TITLE"));
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
	
	public String getLastUpdateIssue(String sDate, String sTime, String eDate, String eTime){
		String result = "";
		try {
			
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuilder();
			sb.append("   SELECT date_format(ID_REGDATE , '%m-%d %H') AS MD_DATE                   \n");
			sb.append("	    FROM ISSUE_DATA                                                     \n");
			sb.append("	   WHERE MD_DATE BETWEEN '"+sDate+" "+sTime+":00:00' AND '"+eDate+" "+eTime+":59:59'  \n");
			sb.append("	     AND M_SEQ IN (186,195,207)                                                   \n");
			sb.append("	ORDER BY MD_DATE DESC                                                   \n");
			sb.append("	   LIMIT 1                                                              \n");
			System.out.println(sb.toString());		
			rs = stmt.executeQuery(sb.toString());
			if(rs.next()){
				result = "<span>마지막 업데이트 시간 : <em>"+rs.getString("MD_DATE")+"</em>시 기준</span>";
			}
			                                                                                    
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
    	return result;
	}*/
	
	public JSONArray getSummaryPortalList_excel(String eDate, String scope, String keyword, String tab){
		JSONArray jarr = new JSONArray();
		try {
			
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuilder();
			sb.append(" ## 포탈 댓글 현황                                                                     \n");
			sb.append(" SELECT                                                                      \n");
			sb.append("         DISTINCT(A.D_SEQ) AS D_SEQ                                          \n");
	        sb.append("         ,A.MD_SITE_NAME                                                     \n");
	        sb.append("         ,A.MD_TITLE                                                         \n");
	        sb.append("         ,A.MD_URL                                                           \n");
	        sb.append("         ,A.S_SEQ                                                            \n");
	        sb.append("         ,date_format(A.MD_DATE, '%m/%d') AS MD_DATE                         \n");
	        sb.append("         ,A.DOC_CNT                                                          \n");
	        sb.append("         ,A.POS_CNT                                                          \n");
	        sb.append("         ,A.NEG_CNT                                                          \n");
	        sb.append("         ,A.NEU_CNT                                                          \n");
	        sb.append("         ,IFNULL(B.API_YN, 'B') AS API_REQUEST                               \n");
	        sb.append("         ,( SELECT COUNT(0) FROM SUMMARY_API_REQUEST ) AS API_CNT            \n");
			sb.append("   FROM SUMMARY_PORTAL A LEFT OUTER JOIN SUMMARY_API_REQUEST B ON A.D_SEQ = B.D_SEQ     \n");
			if(!"0".equals(tab)){
				sb.append("		INNER JOIN META C ON A.D_SEQ = C.D_SEQ	\n");
				sb.append("	    INNER JOIN IDX D ON C.MD_SEQ = D.MD_SEQ AND D.K_XP = 11 AND D.K_YP = "+tab+"	\n");	
			}
			sb.append("  WHERE A.DOC_CNT >= 0                                                       \n");
			sb.append("   AND A.MD_DATE BETWEEN '"+eDate+" 00:00:00' AND '"+eDate+" 23:59:59'       \n");
			if(!"".equals(keyword)){
				sb.append("  AND A.MD_TITLE LIKE '%" + keyword + "%'                       \n");
			}
			sb.append(" ORDER BY A.DOC_CNT DESC                                                     \n");
			System.out.println(sb.toString());		                                               
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("API_CNT", 			rs.getString("API_CNT"));
				obj.put("API_REQUEST", 		rs.getString("API_REQUEST"));
				obj.put("D_SEQ", 			rs.getString("D_SEQ"));
				obj.put("MD_SITE_NAME", 	rs.getString("MD_SITE_NAME"));
				obj.put("S_SEQ", 			rs.getString("S_SEQ"));
				obj.put("MD_TITLE", 		rs.getString("MD_TITLE"));
				obj.put("MD_URL", 			URLEncoder.encode(rs.getString("MD_URL").toString(), "utf-8"));
				obj.put("EX_URL", 			rs.getString("MD_URL").toString());
				obj.put("MD_DATE", 			rs.getString("MD_DATE"));
				obj.put("DOC_CNT", 			rs.getString("DOC_CNT"));
				obj.put("POS_CNT", 			rs.getString("POS_CNT"));
				obj.put("NEG_CNT", 			rs.getString("NEG_CNT"));
				obj.put("NEU_CNT", 			rs.getString("NEU_CNT"));
				jarr.put(obj);
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
   	 	return jarr;
		
	}
	
	
	public JSONObject getSummaryPortalList2(String eDate, int nowPage, String scope, String keyword, String tab){
		JSONObject result = new JSONObject();
		JSONArray jarr = new JSONArray();
		int startNumber =0;
		startNumber = (nowPage-1)*10;
		
		try {
			
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuilder();
			sb.append(" ## 포탈 댓글 현황   카운팅                                                                  \n");
			sb.append(" SELECT                                                                      \n");
			sb.append("         count(DISTINCT(A.D_SEQ)) AS CNT                                     \n");
			sb.append("   FROM SUMMARY_PORTAL A                                                     \n");
			if(!"0".equals(tab)){
				sb.append("INNER JOIN META C ON A.D_SEQ = C.D_SEQ	\n");
				sb.append("	    INNER JOIN IDX D ON C.MD_SEQ = D.MD_SEQ AND D.K_XP = 11 AND D.K_YP = "+tab+"	\n");	
			}
			sb.append("  WHERE A.DOC_CNT >= 0                                                        \n");
			sb.append("   AND A.MD_DATE BETWEEN '"+eDate+" 00:00:00' AND '"+eDate+" 23:59:59'       \n");
			if(!"".equals(keyword)){
				sb.append("  AND A.MD_TITLE LIKE '%" + keyword + "%'                       \n");
		    	 //if("1".equals(scope)){
				 //   	sb.append("  AND A.MD_TITLE LIKE '%" + keyword + "%'                       \n");
				 //}else if("2".equals(scope)){
				 //   	sb.append("  AND A.MD_CONTENT LIKE '%" + keyword + "%'                    											\n");
				 //}else if("3".equals(scope)){
				 //   	sb.append("  AND CONCAT_WS(' ', A.MD_TITLE, A.MD_CONTENT) LIKE '%" + keyword + "%'							\n"); 
				 //}
		    }
			
			sb.append(" ORDER BY A.DOC_CNT DESC                                                     \n");
			System.out.println(sb.toString());		                                               
			rs = stmt.executeQuery(sb.toString());
			if(rs.next()){
				result.put("total", rs.getInt("CNT"));
			}
			
			
			sb = new StringBuilder();
			sb.append(" ## 포탈 댓글 현황                                                                     \n");
			sb.append(" SELECT                                                                      \n");
			sb.append("         DISTINCT(A.D_SEQ) AS D_SEQ                                          \n");
	        sb.append("         ,A.MD_SITE_NAME                                                     \n");
	        sb.append("         ,A.MD_TITLE                                                         \n");
	        //서울시 시스템은 문제 없으나 대구시청 같은 경우 &amp;가 포함된 url처리가 안됨, was에서 설정하는게 있는듯
	        sb.append("         ,replace(A.MD_URL, '&amp;', '&') AS MD_URL                          \n");
	        sb.append("         ,A.S_SEQ                                                            \n");
	        sb.append("         ,date_format(A.MD_DATE, '%m/%d') AS MD_DATE                         \n");
	        sb.append("         ,A.DOC_CNT                                                          \n");
	        sb.append("         ,A.POS_CNT                                                          \n");
	        sb.append("         ,A.NEG_CNT                                                          \n");
	        sb.append("         ,A.NEU_CNT                                                          \n");
	        sb.append("         ,IFNULL(B.API_YN, 'B') AS API_REQUEST                               \n");
	        sb.append("         ,( SELECT COUNT(0) FROM SUMMARY_API_REQUEST ) AS API_CNT            \n");
			sb.append("   FROM SUMMARY_PORTAL A LEFT OUTER JOIN SUMMARY_API_REQUEST B ON A.D_SEQ = B.D_SEQ     \n");
			if(!"0".equals(tab)){
				sb.append("		INNER JOIN META C ON A.D_SEQ = C.D_SEQ	\n");
				sb.append("	    INNER JOIN IDX D ON C.MD_SEQ = D.MD_SEQ AND D.K_XP = 11 AND D.K_YP = "+tab+"	\n");	
			}
			sb.append("  WHERE A.DOC_CNT >= 0                                                       \n");
			sb.append("   AND A.MD_DATE BETWEEN '"+eDate+" 00:00:00' AND '"+eDate+" 23:59:59'       \n");
			if(!"".equals(keyword)){
				sb.append("  AND A.MD_TITLE LIKE '%" + keyword + "%'                       \n");
			}
			sb.append(" ORDER BY A.DOC_CNT DESC                                                     \n");
			sb.append(" LIMIT "+startNumber+", 10 													\n");
			System.out.println(sb.toString());		                                               
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("API_CNT", 			rs.getString("API_CNT"));
				obj.put("API_REQUEST", 		rs.getString("API_REQUEST"));
				obj.put("D_SEQ", 			rs.getString("D_SEQ"));
				obj.put("MD_SITE_NAME", 	rs.getString("MD_SITE_NAME"));
				obj.put("S_SEQ", 			rs.getString("S_SEQ"));
				obj.put("MD_TITLE", 		rs.getString("MD_TITLE"));
				obj.put("MD_URL", 			URLEncoder.encode(rs.getString("MD_URL").toString(), "utf-8"));
				obj.put("EX_URL", 			rs.getString("MD_URL").toString());
				obj.put("MD_DATE", 			rs.getString("MD_DATE"));
				obj.put("DOC_CNT", 			rs.getString("DOC_CNT"));
				obj.put("POS_CNT", 			rs.getString("POS_CNT"));
				obj.put("NEG_CNT", 			rs.getString("NEG_CNT"));
				obj.put("NEU_CNT", 			rs.getString("NEU_CNT"));
				jarr.put(obj);
			}
			result.put("list", jarr);
			
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
	
	
	public JSONObject getSummaryPortalList3(String eDate, int nowPage, String keyword, String tab, String user_id){
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		String url = apiHost+"cmd=replytrendlist"; 
		String params = "";
		
		String text_type = "";
		String i_or_text = "";
		
		try{
			JSONArray doc_arr = getReq_Dseq();
			ArrayList<String> k_arr = getKeywordList(tab);
			
			if(!"".equals(keyword)){
				text_type = "&lucy_keyword=";
				
				i_or_text = "((\""+keyword+"\") AND ";
				
				String or = "";
				if(k_arr.size() > 0){
					for (int i = 0; i < k_arr.size(); i++) {
						if(i==0){
							if(k_arr.size() == 1){
								or = "(\""+k_arr.get(i)+"\"))";
							}else{
								or = "(\""+k_arr.get(i)+"\"";
							}							
						}else{
							if(i==(k_arr.size()-1)){
								or += " OR \""+k_arr.get(i)+"\"))";
							}else{
								or += " OR \""+k_arr.get(i)+"\"";
							}
						}
					}
				}				
				i_or_text += or;				
			}else{
				
				String or = "";
				if(k_arr.size()>0){
					if(k_arr.size() == 1){
						text_type = "&general_keyword=";
						or = k_arr.get(0);
					}else{
						text_type = "&sentence_keyword=";						
						for (int i = 0; i < k_arr.size(); i++) {
							if("".equals(or)){
								or = k_arr.get(i);
							}else{
								or += ","+k_arr.get(i);
							}
						}	
					}					
				}
				i_or_text = or;				
			}			
					
			if(!"".equals(eDate)){
				params += "&i_sdate="+eDate.replaceAll("-", "")+"&i_edate="+eDate.replaceAll("-", "")+"";
			}
			params += "&systemkey="+lucyKey+"&userid="+user_id+"&row_limit=10&page_num="+nowPage+"&s_seq=2196 2199 3883"+text_type+URLEncoder.encode(i_or_text, "UTF-8");
			
			System.out.println(url+params);
			
			JSONObject root = rootReturn(url, params);
			
			jsonArray = root.getJSONArray("docs");			
			JSONArray arr = new JSONArray();			
			System.out.println(jsonArray);
			if(jsonArray.length() > 0){
				arr = new JSONArray();
				JSONObject jsonObject = null;
				JSONObject obj = new JSONObject();
				for(int i=0; i < jsonArray.length(); i++){
					jsonObject = (JSONObject)jsonArray.get(i);
					obj = new JSONObject();
					
					//obj.put("API_CNT", 			rs.getString("API_CNT"));
					
					if(doc_arr.length() > 0){
						for(int x=0 ; x < doc_arr.length() ; x++){
							JSONObject doc_obj = (JSONObject)doc_arr.get(x);
							if(jsonObject.getString("i_seq_org").equals(doc_obj.getString("D_SEQ"))){
								obj.put("API_REQUEST", 		"A");
							}else{
								obj.put("API_REQUEST", 		"B");
							}
						}
					}else{
						obj.put("API_REQUEST", 		"B");
					}
					obj.put("D_SEQ", 			jsonObject.getString("i_seq_org"));
					obj.put("MD_SITE_NAME", 	jsonObject.getString("i_boardname"));					
					obj.put("MD_TITLE", 		jsonObject.getString("i_title"));
					obj.put("MD_TITLE_REL", 	jsonObject.getString("i_title").replaceAll("'", ""));
					obj.put("MD_URL", 			URLEncoder.encode(jsonObject.getString("i_url").toString(), "utf-8"));
					obj.put("EX_URL", 			jsonObject.getString("i_url").toString());										
					obj.put("MD_DATE", 			getUnixTimeStamp(jsonObject.getLong("i_crawlstamp"), "MM/dd"));					
					obj.put("DOC_CNT", 			jsonObject.getString("total_cnt"));
					obj.put("POS_CNT", 			jsonObject.getString("positive_cnt"));
					obj.put("NEG_CNT", 			jsonObject.getString("negative_cnt"));
					obj.put("NEU_CNT", 			jsonObject.getString("neutrality_cnt"));
					
					arr.put(obj);
				}
			}
			
			System.out.println("total : "+root.getInt("numFound"));
			result.put("total",root.getInt("numFound"));
			result.put("list",arr);
			
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(conn != null)conn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		
		return result;
	}
	
	
	public JSONArray getSummaryPortalList3_Excel(String eDate, String keyword,String user_id){
		JSONArray jsonArray = new JSONArray();
		JSONArray arr = new JSONArray();	
		String url = apiHost+"cmd=replytrendlist"; 
		String params = "";
		
		String i_or_text = "";
		String text_type = "";  
		
		try{
			/*if(!"".equals(keyword)){
				text_type = "&lucy_keyword=";
				
				i_or_text = "((\""+keyword+"\")";
				
				i_or_text += " AND (\"서울시\" OR \"서울특별시\" OR \"구의역\" OR \"박원순\"))";
				
			}else{
				text_type = "&i_or_text=";
				i_or_text += "서울시 서울특별시 구의역 박원순";
			}*/
			
			ArrayList<String> k_arr = getKeywordList("");
			
			if(!"".equals(keyword)){
				text_type = "&lucy_keyword=";
				
				i_or_text = "((\""+keyword+"\") AND ";
				
				String or = "";
				if(k_arr.size() > 0){
					for (int i = 0; i < k_arr.size(); i++) {
						if(i==0){
							if(k_arr.size() == 1){
								or = "(\""+k_arr.get(i)+"\"))";
							}else{
								or = "(\""+k_arr.get(i)+"\"";
							}							
						}else{
							if(i==(k_arr.size()-1)){
								or += " OR \""+k_arr.get(i)+"\"))";
							}else{
								or += " OR \""+k_arr.get(i)+"\"";
							}
						}
					}
				}				
				i_or_text += or;				
			}else{
				
				String or = "";
				if(k_arr.size()>0){
					if(k_arr.size() == 1){
						text_type = "&general_keyword=";
						or = k_arr.get(0);
					}else{
						text_type = "&sentence_keyword=";
						for (int i = 0; i < k_arr.size(); i++) {
							if("".equals(or)){
								or = k_arr.get(i);
							}else{
								or += ","+k_arr.get(i);
							}
						}	
					}					
				}
				i_or_text = or;				
			}
			
					
			if(!"".equals(eDate)){
				params += "&i_sdate="+eDate.replaceAll("-", "")+"&i_edate="+eDate.replaceAll("-", "")+"";
			}
			params += "&systemkey="+lucyKey+"&userid="+user_id+"&row_limit=1000&page_num=1"+"&s_seq=2196 2199 3883"+text_type+URLEncoder.encode(i_or_text, "UTF-8");
			
			JSONObject root = rootReturn(url, params);
			
			jsonArray = root.getJSONArray("docs");			
			
			if(jsonArray.length() > 0){
				arr = new JSONArray();
				JSONObject jsonObject = null;
				JSONObject obj = new JSONObject();
				for(int i=0; i < jsonArray.length(); i++){
					jsonObject = (JSONObject)jsonArray.get(i);
					obj = new JSONObject();
					
					obj.put("D_SEQ", 			jsonObject.getString("i_seq_org"));
					obj.put("MD_SITE_NAME", 	jsonObject.getString("i_boardname"));					
					obj.put("MD_TITLE", 		jsonObject.getString("i_title"));
					obj.put("MD_TITLE_REL", 	jsonObject.getString("i_title").replaceAll("'", ""));
					obj.put("MD_URL", 			URLEncoder.encode(jsonObject.getString("i_url").toString(), "utf-8"));
					obj.put("EX_URL", 			jsonObject.getString("i_url").toString());										
					obj.put("MD_DATE", 			getUnixTimeStamp(jsonObject.getLong("i_crawlstamp"), "MM/dd"));					
					obj.put("DOC_CNT", 			jsonObject.getString("total_cnt"));
					obj.put("POS_CNT", 			jsonObject.getString("positive_cnt"));
					obj.put("NEG_CNT", 			jsonObject.getString("negative_cnt"));
					obj.put("NEU_CNT", 			jsonObject.getString("neutrality_cnt"));
					
					arr.put(obj);
				}
			}
			
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(conn != null)conn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		
		return arr;
	}
	
	public JSONArray getReq_Dseq(){
		conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONArray arr = null;
   	 	try {
   	 		conn.getDBCPConnection();
			sb.append(" SELECT                          \n");
			sb.append("   D_SEQ  						\n");
			sb.append(" FROM SUMMARY_API_REQUEST        \n");
			
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("D_SEQ", rs.getString("D_SEQ"));				
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
	
	public ArrayList<String> getKeywordList(String tab){
		ArrayList<String> arr = new ArrayList<String>();
		
		try{
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuilder();
			sb.append(" ## 포탈 댓글 현황 키워드 List                                   		\n");
			sb.append(" SELECT K_VALUE FROM KEYWORD WHERE K_XP = 11 AND K_YP = 1 AND K_ZP <> 0  \n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				arr.add(rs.getString("K_VALUE"));
			}
						
		}catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		}  finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
		
		return arr;
	}
	
	
	public ArrayList getSummaryPortalList(String eDate, int nowPage){
		ArrayList result = new ArrayList();
		HashMap map = new HashMap();
		int startNumber = 0 ;
		startNumber = (nowPage-1)*10;
		
		p_docid = "";
		
		try {
			
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuilder();
			sb.append(" SELECT                                                                    	\n");
			sb.append("         COUNT(DISTINCT(A.D_SEQ)) AS CNT                                     \n");
			sb.append("   FROM SUMMARY_PORTAL A                                                     \n");
			sb.append("  WHERE A.DOC_CNT > 0                                                        \n");
			sb.append("   AND A.MD_DATE BETWEEN '"+eDate+" 00:00:00' AND '"+eDate+" 23:59:59'      	\n");
			sb.append(" ORDER BY A.DOC_CNT DESC                                                     \n");
			System.out.println(sb.toString());		                                               
			rs = stmt.executeQuery(sb.toString());
			if(rs.next()){
				totalCnt = rs.getInt("CNT");
			}
			
			sb = new StringBuilder();
			sb.append(" SELECT                                                                    \n");
			sb.append("         DISTINCT(A.D_SEQ) AS D_SEQ                                         \n");
	        sb.append("         ,A.MD_SITE_NAME                                                    \n");
	        sb.append("         ,A.MD_TITLE                                                        \n");
	        sb.append("         ,A.MD_URL                                                          \n");
	        sb.append("         ,A.S_SEQ                                                          \n");
	        sb.append("        ,date_format(A.MD_DATE, '%m/%d') as MD_DATE                           \n");
	        sb.append("         ,A.DOC_CNT                                                          \n");
	        sb.append("         ,A.POS_CNT                                                          \n");
	        sb.append("         ,A.NEG_CNT                                                          \n");
	        sb.append("         ,A.NEU_CNT                                                          \n");
			sb.append("   FROM SUMMARY_PORTAL A                                                     \n");
			sb.append("  WHERE A.DOC_CNT > 0                                                         \n");
			sb.append("   AND A.MD_DATE BETWEEN '"+eDate+" 00:00:00' AND '"+eDate+" 23:59:59'      \n");
			sb.append(" ORDER BY A.DOC_CNT DESC                                                      \n");
			sb.append(" LIMIT "+startNumber+", 10 																	\n");
			System.out.println(sb.toString());		                                               
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				map = new HashMap();
				map.put("D_SEQ", rs.getString("D_SEQ"));
				map.put("MD_SITE_NAME", rs.getString("MD_SITE_NAME"));
				map.put("S_SEQ", rs.getString("S_SEQ"));
				map.put("MD_TITLE", rs.getString("MD_TITLE"));
				map.put("MD_URL", URLEncoder.encode(rs.getString("MD_URL").toString(), "utf-8"));
				map.put("EX_URL", rs.getString("MD_URL").toString());
				map.put("MD_DATE", rs.getString("MD_DATE"));
				map.put("DOC_CNT", rs.getString("DOC_CNT"));
				map.put("POS_CNT", rs.getString("POS_CNT"));
				map.put("NEG_CNT", rs.getString("NEG_CNT"));
				map.put("NEU_CNT", rs.getString("NEU_CNT"));
				result.add(map);
			}
			                                                                                    
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}

		return result;
	}
	
	public ArrayList getPortalList(String eDate){
		ArrayList result = new ArrayList();
		HashMap map = new HashMap();
		
		p_docid = "";
		
		try {
			
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuilder();
			sb.append("  SELECT																	   \n");
			sb.append("         DISTINCT(B.D_SEQ) AS D_SEQ                                         \n");
			sb.append("         ,A.MD_SEQ                                                          \n");
	        sb.append("         ,A.MD_SITE_NAME                                                    \n");
	        sb.append("         ,A.ID_TITLE                                                        \n");
	        sb.append("         ,A.ID_URL                                                          \n");
	        sb.append("         ,A.S_SEQ                                                          \n");
	        sb.append("        ,date_format(A.MD_DATE, '%m/%d') as MD_DATE                           \n");
			sb.append("    FROM ISSUE_DATA A , META B                             \n");
			sb.append("   WHERE A.MD_DATE BETWEEN '"+eDate+" 00:00:00' AND '"+eDate+" 23:59:59'  \n"); 
			sb.append("     AND A.S_SEQ IN (2196,2199,3883,4537)                                   \n");
			sb.append("     AND A.MD_SEQ = B.MD_SEQ                                                \n");
			//sb.append("     AND A.MD_SEQ = C.MD_SEQ                                                \n");
			//sb.append("     AND (CONCAT(A.ID_TITLE, A.ID_CONTENT) LIKE '%서울시%'              			\n");
			//sb.append("     OR CONCAT(A.ID_TITLE, A.ID_CONTENT) LIKE '%박원순%')              			\n");
			//sb.append("     AND C.K_XP > 2                                               			\n");
			System.out.println(sb.toString());		
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				map = new HashMap();
				map.put("MD_SEQ", rs.getString("MD_SEQ"));
				map.put("D_SEQ", rs.getString("D_SEQ"));
				map.put("MD_SITE_NAME", rs.getString("MD_SITE_NAME"));
				map.put("S_SEQ", rs.getString("S_SEQ"));
				map.put("MD_TITLE", rs.getString("ID_TITLE"));
				map.put("MD_URL", URLEncoder.encode(rs.getString("ID_URL").toString(), "utf-8"));
				map.put("EX_URL", rs.getString("ID_URL").toString());
				map.put("MD_DATE", rs.getString("MD_DATE"));
				result.add(map);
				
						
				if("".equals(p_docid)){
					p_docid = rs.getString("D_SEQ");
				}else{
					p_docid += ","+rs.getString("D_SEQ");
				}
			}
			                                                                                    
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}

		return result;
	}
	
	
	//Element root를 리턴해준다
	public JSONObject rootReturn(String queryUrl, String q)throws IOException ,JDOMException, JSONException{
		
			Element root=null;
			URL url = new URL(queryUrl);
			URLConnection conn;
			conn = url.openConnection();
			((HttpURLConnection)conn).setRequestMethod("POST");
			conn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		    wr.writeBytes(q);
		    //wr.write(q);
		    wr.flush();
			
		    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		    StringBuilder response = new StringBuilder();
		    
		    String lines = "";
		    while( (lines=rd.readLine()) != null){
		    	response.append(lines);
		    }
		    JSONObject jsonObj = new JSONObject(response.toString());
		    System.out.println("===========================   JSON convert =======================");
		    System.out.println(jsonObj.toString());
		    System.out.println("===========================   JSON convert ========================");
			return jsonObj;
	}
	
	public JSONArray getRelationKeyword(String p_docid, String p_date, String user_id){
		JSONArray jsonArray = new JSONArray(); 
		String url = apiHost+"cmd=replyRelationWord"; 
		String params = "";
		
		if(p_date.contains("-")){
			p_date = p_date.replaceAll("-", "");	
		}
		if(!"".equals(p_docid)){
			params += "&p_docid="+p_docid;
		}
		if(!"".equals(p_date)){
			params += "&p_date="+p_date;
		}
		params += "&systemkey="+lucyKey+"&userid="+user_id+"&groupByField=i_total_trendword&r_row_limit=30";
		
		System.out.println(url+params);
		
		try{
			JSONObject root = rootReturn(url, params);
			jsonArray = root.getJSONArray("docs");
			
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(conn != null)conn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		
		return jsonArray;
	}
	
	public ArrayList getReplyDataList(int nowpage, int rowCnt, String p_date, String doc_id, String r_trnd, String r_ralation_word, String user_id){
		ArrayList result = new ArrayList();
		String url = apiHost+"cmd=replySearchList"; 
		String params = "";
		
		try{
			
			if(p_date.contains("-")){
				p_date = p_date.replaceAll("-", "");	
			}
			if(!"".equals(doc_id)){
				params += "&p_docid="+doc_id;
			}
			if(!"".equals(p_date)){
				params += "&p_date="+p_date;
			}
			if(!"".equals(r_trnd)){
				params += "&r_trend="+r_trnd;
			}
			if(!"".equals(r_ralation_word)){
				params += "&r_relation_word="+ URLEncoder.encode(r_ralation_word, "UTF-8") ;	
			}
			params +="&systemkey="+lucyKey+"&userid="+user_id;
			params +="&r_page_num="+nowpage;
			params +="&r_row_limit="+rowCnt;
			System.out.println(url+params);
			
			JSONObject root = rootReturn(url, params);
			//JSONObject root = rootReturn(url, doc_id, p_date, nowpage, rowCnt);
			JSONArray jsonArray = root.getJSONArray("docs");
			replyTotalCnt = Integer.parseInt(root.getString("numFound"));
			
			if(jsonArray.length() > 0){
				JSONObject jsonObject = null;
				LinkedHashMap map = null;
				for(int i=0; i < jsonArray.length(); i++){
					map = new LinkedHashMap();
					jsonObject = (JSONObject)jsonArray.get(i);
					map.put("r_datetime", jsonObject.get("r_datetime"));
					map.put("r_content", jsonObject.get("r_content"));
					map.put("r_trend", jsonObject.get("r_trend"));
					result.add(map);
				}
			}
			
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(conn != null)conn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		
		
		return result;
	}
	
	public JSONArray getReplyDataExcelList(int rowCnt,String p_date, String doc_id, String r_trnd, String r_ralation_word, String user_id,boolean excelMode){
		JSONArray result = new JSONArray();
		String url = apiHost+"cmd=replySearchList"; 
		String params = "";
		
		try{
			
			if(p_date.contains("-")){
				p_date = p_date.replaceAll("-", "");	
			}
			if(!"".equals(doc_id)){
				params += "&p_docid="+doc_id;
			}
			if(!"".equals(p_date)){
				params += "&p_date="+p_date;
			}
			if(!"".equals(r_trnd)){
				params += "&r_trend="+r_trnd;
			}
			if(!"".equals(r_ralation_word)){
				params += "&r_relation_word="+ URLEncoder.encode(r_ralation_word, "UTF-8") ;	
			}
			int nowpage = 1;
			params +="&systemkey="+lucyKey+"&userid="+user_id;
			String param2 = "";  
			param2 +="&r_page_num="+nowpage;
			param2 +="&r_row_limit="+rowCnt;
			System.out.println(url+params+param2);
			
			JSONObject root = rootReturn(url, params+param2);
			//JSONObject root = rootReturn(url, doc_id, p_date, nowpage, rowCnt);
			JSONArray jsonArray = root.getJSONArray("docs");
			replyTotalCnt = Integer.parseInt(root.getString("numFound"));
			
			if(jsonArray.length() > 0){
				JSONObject jsonObject = null;
				
				for(int i=0; i < jsonArray.length(); i++){
					JSONObject object = new JSONObject();
					
					jsonObject = (JSONObject)jsonArray.get(i);
					
					object.put("r_datetime", jsonObject.get("r_datetime"));
					object.put("r_content", jsonObject.get("r_content"));
					object.put("r_interest_count", jsonObject.get("r_interest_count"));
					object.put("r_trend", jsonObject.get("r_trend"));
					object.put("p_sitename", jsonObject.get("p_sitename"));
					
					String Jdata = jsonObject.get("r_jsondata").toString();
					
					JSONObject obj1 = new JSONObject(Jdata);
					if(  !obj1.isNull("REPLY.DATA.BadCount") ){
						object.put("r_badcnt", obj1.get("REPLY.DATA.BadCount"));	
					}else{
						object.put("r_badcnt", "");
					}
					if(  !obj1.isNull("REPLY.DATA.WriterName") ){
						object.put("r_name", obj1.get("REPLY.DATA.WriterName"));	
					}else{
						object.put("r_name", "");
					}
					if(  !obj1.isNull("REPLY.DATA.WriteStamp") ){
						//object.put("r_writeDate", obj1.get("REPLY.DATA.WriteStamp"));
						java.util.Date time = new java.util.Date((long)obj1.getInt("REPLY.DATA.WriteStamp")*1000);
						SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String r_date = date.format(time);
						
						object.put("r_writeDate", r_date);
					}else{
						object.put("r_writeDate", "");
					}
					if(  !obj1.isNull("REPLY.DATA.WriterID") ){
						object.put("r_id", obj1.get("REPLY.DATA.WriterID"));	
					}else{
						object.put("r_id", "");
					}
					
					result.put(object);
				}
			}
			
			
			while(replyTotalCnt > 0){
				Thread.sleep(500);
				++nowpage;
				param2 = "";  
				param2 +="&r_page_num="+nowpage;
				param2 +="&r_row_limit="+rowCnt;
				System.out.println(url+params+param2);
				
				root = rootReturn(url, params+param2);
				//JSONObject root = rootReturn(url, doc_id, p_date, nowpage, rowCnt);
				jsonArray = root.getJSONArray("docs");
				replyTotalCnt = Integer.parseInt(root.getString("numFound"));
				
				if(jsonArray.length() > 0){
					JSONObject jsonObject = null;
					
					for(int i=0; i < jsonArray.length(); i++){
						JSONObject object = new JSONObject();
						
						jsonObject = (JSONObject)jsonArray.get(i);
						
						object.put("r_datetime", jsonObject.get("r_datetime"));
						object.put("r_content", jsonObject.get("r_content"));
						object.put("r_interest_count", jsonObject.get("r_interest_count"));
						object.put("r_trend", jsonObject.get("r_trend"));
						object.put("p_sitename", jsonObject.get("p_sitename"));
						
						String Jdata = jsonObject.get("r_jsondata").toString();
						
						JSONObject obj1 = new JSONObject(Jdata);
						if(  !obj1.isNull("REPLY.DATA.BadCount") ){
							object.put("r_badcnt", obj1.get("REPLY.DATA.BadCount"));	
						}else{
							object.put("r_badcnt", "");
						}
						if(  !obj1.isNull("REPLY.DATA.WriterName") ){
							object.put("r_name", obj1.get("REPLY.DATA.WriterName"));	
						}else{
							object.put("r_name", "");
						}
						if(  !obj1.isNull("REPLY.DATA.WriteStamp") ){
							//object.put("r_writeDate", obj1.get("REPLY.DATA.WriteStamp"));
							java.util.Date time = new java.util.Date((long)obj1.getInt("REPLY.DATA.WriteStamp")*1000);
							SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String r_date = date.format(time);
							
							object.put("r_writeDate", r_date);
							
						}else{
							object.put("r_writeDate", "");
						}
						if(  !obj1.isNull("REPLY.DATA.WriterID") ){
							object.put("r_id", obj1.get("REPLY.DATA.WriterID"));	
						}else{
							object.put("r_id", "");
						}
						
						
						result.put(object);
					}
				}
			}
			
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(conn != null)conn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		
		
		
		return result;
	}
	
	
	public ArrayList getPortalReplyList(String p_docid , String p_date){
		ArrayList result = new ArrayList();
		
		String url = apiHost+"cmd=replySortCnt"; 
		String params = "";
		
		if(!"".equals(p_docid)){
			params += "&p_docid="+p_docid;
		}
		if(!"".equals(p_date)){
			params += "&p_date="+p_date;
		}
		
		System.out.println(url+params);
		
		try{
			JSONObject root = rootReturn(url, params);
			
			JSONArray jsonArray = root.getJSONArray("docs");
			
			if(jsonArray.length() > 0){
				JSONObject jsonObject = null;
				LinkedHashMap map = null;
				for(int i=0; i < jsonArray.length(); i++){
					map = new LinkedHashMap();
					jsonObject = (JSONObject)jsonArray.get(i);
					map.put("docCnt", jsonObject.get("docCnt"));
					map.put("neg", jsonObject.get("nagative_cnt"));
					map.put("pos", jsonObject.get("positive_cnt"));
					map.put("neu", jsonObject.get("neutrality_cnt"));
					map.put("p_docid", jsonObject.get("p_docid"));
					
					result.add(map);
				}
			}
			
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(conn != null)conn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		
		return result;
	}

	public JSONArray getSummaryPortalListExcel(String eDate, String keyword){
		
		JSONArray result = new JSONArray();
		JSONObject obj = new JSONObject();
		try {
			
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuilder();
			sb.append(" ## 포탈 댓글 현황                                                                     \n");
			sb.append(" SELECT                                                                      \n");
			sb.append("         DISTINCT(A.D_SEQ) AS D_SEQ                                          \n");
	        sb.append("         ,A.MD_SITE_NAME                                                     \n");
	        sb.append("         ,A.MD_TITLE                                                         \n");
	        sb.append("         ,A.MD_URL                                                           \n");
	        sb.append("         ,A.S_SEQ                                                            \n");
	        sb.append("         ,date_format(A.MD_DATE, '%m/%d') as MD_DATE                         \n");
	        sb.append("         ,A.DOC_CNT                                                          \n");
	        sb.append("         ,A.POS_CNT                                                          \n");
	        sb.append("         ,A.NEG_CNT                                                          \n");
	        sb.append("         ,A.NEU_CNT                                                          \n");
	        sb.append("         ,IFNULL(B.API_YN, 'B') AS API_REQUEST                               \n");
	        sb.append("         ,( SELECT COUNT(0) FROM SUMMARY_API_REQUEST ) AS API_CNT            \n");
			sb.append("   FROM SUMMARY_PORTAL A LEFT OUTER JOIN SUMMARY_API_REQUEST B ON A.D_SEQ = B.D_SEQ     \n");
			sb.append("  WHERE A.DOC_CNT >= 0                                                       \n");
			sb.append("   AND A.MD_DATE BETWEEN '"+eDate+" 00:00:00' AND '"+eDate+" 23:59:59'       \n");
			if(!"".equals(keyword)){
				sb.append("  AND A.MD_TITLE LIKE '%" + keyword + "%'                       \n");
			}
			sb.append(" ORDER BY A.DOC_CNT DESC                                                     \n");
			System.out.println(sb.toString());		                                               
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				obj = new JSONObject();
				obj.put("API_CNT", 			rs.getString("API_CNT"));
				obj.put("API_REQUEST", 		rs.getString("API_REQUEST"));
				obj.put("D_SEQ", 			rs.getString("D_SEQ"));
				obj.put("MD_SITE_NAME", 	rs.getString("MD_SITE_NAME"));
				obj.put("S_SEQ", 			rs.getString("S_SEQ"));
				obj.put("MD_TITLE", 		rs.getString("MD_TITLE"));
				obj.put("MD_URL", 			URLEncoder.encode(rs.getString("MD_URL").toString(), "utf-8"));
				obj.put("EX_URL", 			rs.getString("MD_URL").toString());
				obj.put("MD_DATE", 			rs.getString("MD_DATE"));
				obj.put("DOC_CNT", 			rs.getString("DOC_CNT"));
				obj.put("POS_CNT", 			rs.getString("POS_CNT"));
				obj.put("NEG_CNT", 			rs.getString("NEG_CNT"));
				obj.put("NEU_CNT", 			rs.getString("NEU_CNT"));
				result.put(obj);
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
	
	public String portalreply_api(String p_docid){
		String result = "";
		int cnt = 0;
		try{
			
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuilder();
			sb.append(" ## 포탈 댓글 API 즉시 수집 요청                                                                     \n");
			sb.append(" SELECT COUNT(0) AS CNT FROM SUMMARY_API_REQUEST                 \n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			if(rs.next()){
				cnt = rs.getInt("CNT");
			}
			if(cnt < 10){
				sb = new StringBuilder();
				sb.append(" ## 포탈 댓글 API 즉시 수집 요청                                                                     \n");
				sb.append(" INSERT INTO SUMMARY_API_REQUEST  SELECT D_SEQ, MD_DATE, 'N', NOW(), NULL FROM META WHERE D_SEQ =  "+p_docid+" \n"); 
				System.out.println(sb.toString());
				if( stmt.executeUpdate(sb.toString()) > 0){
					result = "댓글 수집 요청을 완료 하였습니다.";
				}	
			}else{
				result = "최대 댓글 수집 요청 건수는 10건 입니다. 먼저 요청한 댓글 수집 건이 처리 된 이후 다시 요청하여주시기 바랍니다.";	
			}
			
			
		}catch(MySQLIntegrityConstraintViolationException e){
			result = "이미 댓글 수집 요청한 기사 입니다.";
			Log.writeExpt(e);
		}catch(SQLException e){
			result = "이미 댓글 수집 요청한 기사 입니다.";
			Log.writeExpt(e);
		}catch(Exception e){
			result = "댓글 수집 요청에 실패 했습니다. 잠시 후 다시 시도하시거나 관리자에게 문의하시기 바랍니다.";
			Log.writeExpt(e);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
		
		return result;
	}
	
	
	/**
	 * @param timeStamp
	 * @return
	 */
	public String getUnixTimeStamp(long timeStamp, String format) {
	    long unixTime = timeStamp * 1000;
	    Date date = new Date(unixTime);
	    
	    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
	    String retVal = dateFormat.format(date);
	    return retVal;
	}
	
	public void getLucySwitch(){
		String os = System.getProperty("os.name");
		if(os.contains("Win")){
			apiHost = "http://lucyapi.realsn.com/API?";
		}else{
			apiHost = "http://lucyapi1.realsn.com/API?";
		}
	}
	
}
