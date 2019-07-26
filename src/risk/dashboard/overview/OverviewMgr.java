package risk.dashboard.overview;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.jdom.Element;
import org.jdom.JDOMException;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONException;
import risk.json.JSONObject;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class OverviewMgr {

	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private PreparedStatement pstmt = null;
	private StringBuffer sb = null;
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	
	private String p_docid = "";
	private int totalCnt = 0;
	private int replyTotalCnt = 0;
	
	public int getPortalListCount(){
		return totalCnt;
	}
	
	public String getP_docides(){
		return p_docid;
	}
	
	public int getReplyTotalCnt(){
		return replyTotalCnt;
	}
	
	public String getLastUpdateIssue(String sDate, String sTime, String eDate, String eTime){
		String result = "";
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
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
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
    	return result;
	}
	
	
	public JSONObject getSummaryPortalList2(String eDate, int nowPage, String scope, String keyword){
		JSONObject result = new JSONObject();
		JSONArray jarr = new JSONArray();
		int startNumber =0;
		startNumber = (nowPage-1)*10;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			sb.append(" SELECT                                                                      \n");
			sb.append("         count(DISTINCT(A.D_SEQ)) AS CNT                                     \n");
			sb.append("   FROM SUMMARY_PORTAL A                                                     \n");
			sb.append("  WHERE A.DOC_CNT > 0                                                        \n");
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
			
			
			sb = new StringBuffer();
			sb.append(" SELECT                                                                      \n");
			sb.append("         DISTINCT(A.D_SEQ) AS D_SEQ                                          \n");
	        sb.append("         ,A.MD_SITE_NAME                                                     \n");
	        sb.append("         ,A.MD_TITLE                                                         \n");
	        sb.append("         ,A.MD_URL                                                           \n");
	        sb.append("         ,A.S_SEQ                                                            \n");
	        sb.append("        ,date_format(A.MD_DATE, '%m/%d') as MD_DATE                          \n");
	        sb.append("         ,A.DOC_CNT                                                          \n");
	        sb.append("         ,A.POS_CNT                                                          \n");
	        sb.append("         ,A.NEG_CNT                                                          \n");
	        sb.append("         ,A.NEU_CNT                                                          \n");
			sb.append("   FROM SUMMARY_PORTAL A                                                     \n");
			sb.append("  WHERE A.DOC_CNT > 0                                                        \n");
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
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
   	 	return result;
		
	}
	
	public ArrayList getSummaryPortalList(String eDate, int nowPage){
		ArrayList result = new ArrayList();
		HashMap map = new HashMap();
		int startNumber = 0 ;
		startNumber = (nowPage-1)*10;
		
		p_docid = "";
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
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
			
			sb = new StringBuffer();
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
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}

		return result;
	}
	
	public ArrayList getPortalList(String eDate){
		ArrayList result = new ArrayList();
		HashMap map = new HashMap();
		
		p_docid = "";
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
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
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
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
		    StringBuffer response = new StringBuffer();
		    
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
	
	public JSONArray getRelationKeyword(String p_docid, String p_date){
		JSONArray jsonArray = new JSONArray(); 
		String url = "http://lucyapi.realsn.com/API?cmd=replyRelationWord"; 
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
		params += "&groupByField=i_total_trendword";
		
		System.out.println(url+params);
		
		try{
			JSONObject root = rootReturn(url, params);
			jsonArray = root.getJSONArray("docs");
			
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		
		return jsonArray;
	}
	
	public ArrayList getReplyDataList(int nowpage, int rowCnt, String p_date, String doc_id, String r_trnd, String r_ralation_word){
		ArrayList result = new ArrayList();
		String url = "http://lucyapi.realsn.com/API?cmd=replySearchList"; 
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
			try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		
		
		return result;
	}
	
	
	public ArrayList getPortalReplyList(String p_docid , String p_date){
		ArrayList result = new ArrayList();
		
		String url = "http://lucyapi.realsn.com/API?cmd=replySortCnt"; 
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
			try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		
		return result;
	}
}
