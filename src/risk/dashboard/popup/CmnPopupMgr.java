package risk.dashboard.popup;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.util.Log;

public class CmnPopupMgr {
	DBconn conn = null;
	Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    StringBuilder sb = null;
    
    public JSONObject getPopupData(int nowPage, int rowCnt, String scope, String keyword, String sDate, String eDate, String codeList, String relationKeyword,String tier,String s_seq) {
    	int startNum = (nowPage-1)*rowCnt;   	
    	
    	String tmp[] = codeList.split("/");
 
    	conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONObject obj = null;
    	JSONArray arr = null; 
   	 	try {
			conn.getDBCPConnection();
			sb.append("## 공통 POPUP - COUNT 																																\n");
			sb.append("	SELECT																																					\n");
			
			// 연관키워드 팝업일 경우
			if(!"".equals(relationKeyword)){
				sb.append("	    COUNT(DISTINCT A.ID_SEQ) AS TOTAL																										\n");
			}else{
				sb.append("	    COUNT(A.ID_SEQ) AS TOTAL																													\n");
			}
			
			sb.append("	  FROM ISSUE_DATA A																																\n");
			
			// 연관키워드 팝업일 경우
			if(!"".equals(relationKeyword)){
				sb.append("	  , ISSUE_RELATION_KEYWORD BB																												\n");
			}
			
			if(!"".equals(tmp[0]) && tmp.length>0){
	    		for(int i=0; i<tmp.length; i++){
	    			sb.append("	  , ISSUE_DATA_CODE B" + i + " 																											\n");
	        	}
	    	}
			if(!"".equals(tier)){
				sb.append("	  , TIER_SITE TS																												\n");
			}
			
			sb.append(" WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59' 													\n");
			
			if(!"".equals(tier)){
				sb.append("	  AND A.S_SEQ = TS.TS_SEQ																																\n");
				sb.append("	  AND TS.TS_TYPE IN(" + tier + ")																										\n");
			}
			
			if(!"".equals(s_seq)){
				sb.append("	  AND A.S_SEQ = "+s_seq+"																																\n");
			}
			
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      																		\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    																	\n");
				    }else if("3".equals(scope)){
				    	sb.append("  AND CONCAT(A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'															\n"); 
				    }
		    }
			
			// 연관키워드 팝업일 경우
			if(!"".equals(relationKeyword)){
				sb.append("	  AND A.ID_SEQ = BB.ID_SEQ																																\n");
				sb.append("	  AND BB.PAT_SEQ IN(" + relationKeyword + ")																										\n");
			}			
					
			if(!"".equals(tmp[0]) && tmp.length>0){
	    		for(int i=0; i<tmp.length; i++){
	        		String tmp2[] = tmp[i].split("@");
	        		
	        		if(tmp2.length<2){
	        			sb.append("	 AND A.ID_SEQ = B" + i + ".ID_SEQ AND B" + i + ".IC_TYPE=" + tmp2[0] + "																\n");
	        		}else{
	        			sb.append("	 AND A.ID_SEQ = B" + i + ".ID_SEQ AND B" + i + ".IC_TYPE=" + tmp2[0] + " AND B" + i + ".IC_CODE IN(" + tmp2[1] + ")		\n");	
	        		}
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
			sb.append("## 공통 POPUP																										\n");
			sb.append("	SELECT																												\n");
			
			// 연관키워드 팝업일 경우
			if(!"".equals(relationKeyword)){
				sb.append("	    DISTINCT A.ID_SEQ	 AS ID_SEQ																			\n");
			}else{
				sb.append("	    A.ID_SEQ	 AS ID_SEQ																						\n");
			}
			
			sb.append("	    , A.ID_TITLE																										\n");
			sb.append("	    , A.S_SEQ																											\n");
			sb.append("	    , A.ID_URL																										\n");
			sb.append("	    ,FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 6) AS SOURCE                            						\n");
			sb.append("		  ,FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 9) AS SENTI		 											\n");
			sb.append("	    , DATE_FORMAT(A.MD_DATE, '%y/%m/%d') AS DATE													\n");
			sb.append("	  FROM ISSUE_DATA A																																\n");
			
			// 연관키워드 팝업일 경우
			if(!"".equals(relationKeyword)){
				sb.append("	  , ISSUE_RELATION_KEYWORD BB																												\n");
			}
						
			if(!"".equals(tmp[0]) && tmp.length>0){
	    		for(int i=0; i<tmp.length; i++){
	    			sb.append("	  , ISSUE_DATA_CODE B" + i + " 																											\n");
	        	}
	    	}
			
			if(!"".equals(tier)){
				sb.append("	  , TIER_SITE TS																												\n");
			}
			
			sb.append(" WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59' 													\n");
			
			if(!"".equals(tier)){
				sb.append("	  AND A.S_SEQ = TS.TS_SEQ																																\n");
				sb.append("	  AND TS.TS_TYPE IN(" + tier + ")																										\n");
			}
			
			if(!"".equals(s_seq)){
				sb.append("	  AND A.S_SEQ = "+s_seq+"																																\n");
			}
			
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      																		\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    																	\n");
				    }else if("3".equals(scope)){
				    	sb.append("  AND CONCAT(A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'															\n"); 
				    }
		    }
			
			// 연관키워드 팝업일 경우
			if(!"".equals(relationKeyword)){
				sb.append("	  AND A.ID_SEQ = BB.ID_SEQ																																\n");
				sb.append("	  AND BB.PAT_SEQ IN(" + relationKeyword + ")																										\n");
			}	
			
			if(!"".equals(tmp[0]) && tmp.length>0){
	    		for(int i=0; i<tmp.length; i++){
	        		String tmp2[] = tmp[i].split("@");
	        		if(tmp2.length<2){
	        			sb.append("	 AND A.ID_SEQ = B" + i + ".ID_SEQ AND B" + i + ".IC_TYPE=" + tmp2[0] + "																\n");
	        		}else{
	        			sb.append("	 AND A.ID_SEQ = B" + i + ".ID_SEQ AND B" + i + ".IC_TYPE=" + tmp2[0] + " AND B" + i + ".IC_CODE IN(" + tmp2[1] + ")		\n");	
	        		}
	        	}
	    	}
			sb.append("  ORDER BY A.MD_DATE DESC				\n");
			sb.append("  LIMIT " + startNum + "," + rowCnt + "				\n");
		    
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj2 = new JSONObject();
				obj2.put("idcode", rs.getString("ID_SEQ"));
				obj2.put("title", rs.getString("A.ID_TITLE"));
				obj2.put("sitecode", rs.getString("A.S_SEQ"));
				obj2.put("url", rs.getString("A.ID_URL"));
				obj2.put("source", rs.getString("SOURCE"));
				obj2.put("senti", rs.getString("SENTI"));
				obj2.put("date", rs.getString("DATE"));
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
    
    /*	주요 매체 현황(주요시정 모니터링 메뉴) 팝업
     *  source : 매체(1:언론, 2:트위터, 3:블로그, 4:카페)
     * */
    public JSONObject getPopupData2(int nowPage, int rowCnt, String scope, String keyword, String sDate, String eDate, String codeList, String source, String category) {
    	int startNum = (nowPage-1)*rowCnt;   	
    	
    	String tmp[] = codeList.split("/");
 
    	conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONObject obj = null;
    	JSONArray arr = null; 
   	 	try {
			conn.getDBCPConnection();
			sb.append("## 주요 매체 현황(주요시정 모니터링) POPUP - COUNT 																							\n");
			sb.append("	SELECT																																					\n");
			sb.append("	    COUNT(A.ID_SEQ) AS TOTAL																														\n");
			sb.append("	  FROM ISSUE_DATA A																																\n");
			
			if(!"".equals(tmp[0]) && tmp.length>0){
	    		for(int i=0; i<tmp.length; i++){
	    			sb.append("	  , ISSUE_DATA_CODE B" + i + " 																											\n");
	        	}
	    	}
			
			if("2".equals(source)){
				sb.append("	  , ISSUE_TWITTER E																																\n");
			}
			
			sb.append(" WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59' 													\n");
			
			if("1".equals(source)){
				sb.append("	 AND A.S_SEQ = " + category + "																												\n");
			}
			
			if("3".equals(source) || "4".equals(source)){
				sb.append("		AND A.CAFE_NAME='" + category + "'																										\n");
			}
			
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      																		\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    																	\n");
				    }else if("3".equals(scope)){
				    	sb.append("  AND CONCAT(A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'															\n"); 
				    }
		    }			
					
			if(!"".equals(tmp[0]) && tmp.length>0){
	    		for(int i=0; i<tmp.length; i++){
	        		String tmp2[] = tmp[i].split("@");
	        		if(tmp2.length<2){
	        			sb.append("	 AND A.ID_SEQ = B" + i + ".ID_SEQ AND B" + i + ".IC_TYPE=" + tmp2[0] + "																\n");
	        		}else{
	        			sb.append("	 AND A.ID_SEQ = B" + i + ".ID_SEQ AND B" + i + ".IC_TYPE=" + tmp2[0] + " AND B" + i + ".IC_CODE IN(" + tmp2[1] + ")		\n");	
	        		}
	        	}
	    	}
			
			if("2".equals(source)){
				sb.append("	 AND A.ID_SEQ = E.ID_SEQ AND E.T_USER_ID='" + category + "'																					\n");
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
			sb.append("## 주요 매체 현황(주요시정 모니터링) POPUP																	\n");
			sb.append("	SELECT																												\n");
			sb.append("	    A.ID_SEQ	 AS ID_SEQ																							\n");
			sb.append("	    , A.ID_TITLE																										\n");
			sb.append("	    , A.S_SEQ																											\n");
			sb.append("	    , A.ID_URL																										\n");
			sb.append("	    ,FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 6) AS SOURCE                            						\n");
			sb.append("		  ,FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 9) AS SENTI		 											\n");
			sb.append("	    , DATE_FORMAT(A.MD_DATE, '%y/%m/%d') AS DATE													\n");
			sb.append("	  FROM ISSUE_DATA A																																\n");			
						
			if(!"".equals(tmp[0]) && tmp.length>0){
	    		for(int i=0; i<tmp.length; i++){
	    			sb.append("	  , ISSUE_DATA_CODE B" + i + " 																											\n");
	        	}
	    	}
			
			if("2".equals(source)){
				sb.append("	  , ISSUE_TWITTER E																																\n");
			}
			
			sb.append(" WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59' 													\n");
			
			if("1".equals(source)){
				sb.append("	 AND A.S_SEQ = " + category + "																												\n");
			}
			
			if("3".equals(source) || "4".equals(source)){
				sb.append("		AND A.CAFE_NAME='" + category + "'																										\n");
			}
			
			if(!"".equals(keyword)){
		    	 if("1".equals(scope)){
				    	sb.append("  AND A.ID_TITLE LIKE '%" + keyword + "%'                      																		\n");
				    }else if("2".equals(scope)){
				    	sb.append("  AND A.ID_CONTENT LIKE '%" + keyword + "%'                    																	\n");
				    }else if("3".equals(scope)){
				    	sb.append("  AND CONCAT(A.ID_TITLE, A.ID_CONTENT) LIKE '%" + keyword + "%'															\n"); 
				    }
		    }
			
			if(!"".equals(tmp[0]) && tmp.length>0){
	    		for(int i=0; i<tmp.length; i++){
	        		String tmp2[] = tmp[i].split("@");
	        		if(tmp2.length<2){
	        			sb.append("	 AND A.ID_SEQ = B" + i + ".ID_SEQ AND B" + i + ".IC_TYPE=" + tmp2[0] + "																\n");
	        		}else{
	        			sb.append("	 AND A.ID_SEQ = B" + i + ".ID_SEQ AND B" + i + ".IC_TYPE=" + tmp2[0] + " AND B" + i + ".IC_CODE IN(" + tmp2[1] + ")		\n");	
	        		}
	        	}
	    	}
			
			if("2".equals(source)){
				sb.append("	 AND A.ID_SEQ = E.ID_SEQ AND E.T_USER_ID='" + category + "'																					\n");
			}	
			sb.append("  ORDER BY A.MD_DATE DESC							\n");
			sb.append("  LIMIT " + startNum + "," + rowCnt + "				\n");
		    
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj2 = new JSONObject();
				obj2.put("idcode", rs.getString("ID_SEQ"));
				obj2.put("title", rs.getString("A.ID_TITLE"));
				obj2.put("sitecode", rs.getString("A.S_SEQ"));
				obj2.put("url", rs.getString("A.ID_URL"));
				obj2.put("source", rs.getString("SOURCE"));
				obj2.put("senti", rs.getString("SENTI"));
				obj2.put("date", rs.getString("DATE"));
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
}
