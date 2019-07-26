package risk.demon;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jdom.Element;
import org.jdom.JDOMException;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONException;
import risk.json.JSONObject;
import risk.util.DateUtil;
import risk.util.Log;

public class DAEGUSummaryPortalData {
	
	static DBconn dbconn = null;
	static String name = "DAEGUSummaryPortalData";
    private StringBuffer sb = null;
    private Statement stmt = null;
    private ResultSet rs    = null;
	private PreparedStatement pstmt = null;
	
	static String p_docid = "";
	static String last_update_seq = "";
	
	public static void main(String args[]){
		
		Log.crond(name,"DAEGUSummaryPortalData START ...");
    	
    	try{
    		
    		dbconn = new DBconn(); //로컬디비
    		dbconn.getSubDirectConnection();
			
    		DateUtil du = new DateUtil();
    		String today = du.getCurrentDate("yyyyMMdd");
    		String timeFormat = du.getCurrentTime();
    		DAEGUSummaryPortalData ssp = new DAEGUSummaryPortalData();
    		boolean chk = true;
    		
    		String type [] = new String[2];
    		type[0] = "1";
    		type[1] = "2";
    		
    		if(type.length > 0){
    			for(int index=0; index < type.length; index++){
    				
    				if(type[index].equals("1")){
    					
    					while(chk){
        	    			
        	    			String last_dseq = ssp.getLastDocSeq(type[index]); 
        	    			
        	    			ArrayList list = ssp.getPortalList(last_dseq);    		
        	        		ArrayList replyCntList = new ArrayList();
        	        		
        	        		if(list.size() > 0){
        	        			
        	        			//ssp.deleteSumaryPortal(p_docid);
        	        			
        	        			ssp.insertSumaryPortal(list);
        	        			
        	        			replyCntList = ssp.getPortalReplyList(p_docid,today);
        	        			if(replyCntList.size() > 0){
        	        				ssp.updateSumaryPortal(replyCntList);	
        	        			}
        	        			
        	        			ssp.updateLastSeq( last_update_seq , "1" );
        	        			Thread.sleep(10000);
        	        		}else{
        	        			chk = false;
        	        		}
        	        		
        	    		}
    					
    				}else if(type[index].equals("2")){
    					chk = true;
    					while(chk){
        	    			
        	    			String last_dseq = ssp.getLastDocSeq(type[index]); 
        	    			
        	    			ArrayList list = ssp.getPortalList2(last_dseq);    		
        	        		ArrayList replyCntList = new ArrayList();
        	        		
        	        		if(list.size() > 0){
        	        			
        	        			//ssp.deleteSumaryPortal(p_docid);
        	        			
        	        			ssp.insertSumaryPortal(list);
        	        			
        	        			replyCntList = ssp.getPortalReplyList(p_docid,today);
        	        			
        	        			ssp.updateSumaryPortal(replyCntList);
        	        			
        	        			ssp.updateLastSeq( last_update_seq , "2" );
        	        			Thread.sleep(10000);
        	        		}else{
        	        			chk = false;
        	        		}
        	        		
        	    		}
    					
    				}
    			}
    		}
    		
    		
    		
    		
    		
    	}catch(Exception ex) {
    		Log.writeExpt(name,ex.getMessage());
        	ex.printStackTrace();
        	if (dbconn != null) try { dbconn.close(); } catch(SQLException e) {}
        	System.exit(1);
        }finally {
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
    	
    	Log.crond(name,"DAEGUSummaryPortalData END ...");
	    
	}
	
	public void updateLastSeq(String doc_seq, String type){
		
		try {
			
			//dbconn = new DBconn();
			//dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			sb.append(" UPDATE SUMMARY_LAST_UPDATE  \n");
			sb.append("    SET  D_SEQ = "+doc_seq+", REG_DATE = now() \n");
			sb.append("  WHERE  SEQ = "+type+" \n");
		    System.out.println(sb.toString());
		    stmt.executeUpdate(sb.toString());
				
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    //if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		
	}
	
	public String getLastDocSeq(String type){
		String result = "";
		try {
			
			//dbconn = new DBconn();
			//dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			sb.append(" SELECT D_SEQ FROM  SUMMARY_LAST_UPDATE WHERE SEQ = "+type+" LIMIT 1 \n");
		    System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
		    if(rs.next()){
		    	result = rs.getString("D_SEQ");	
		    }
				
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    //if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		
		return result;
	}
	
	
	public void deleteSumaryPortal(String doc_id){
		try {
			
			//dbconn = new DBconn();
			//dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			Map map = null;
			sb = new StringBuffer();
			sb.append(" DELETE FROM SUMMARY_PORTAL WHERE D_SEQ IN ("+doc_id+") \n");
		    System.out.println(sb.toString());		
		    stmt.executeUpdate(sb.toString());
			                                                                                    
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    //if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	}
	
	
	public void insertSumaryPortal(ArrayList list){
		
		try {
			
			//dbconn = new DBconn();
			//dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			Map map = null;
			for(int i=0; i<list.size(); i++){
				map = new HashMap();
				map = (HashMap)list.get(i);
				
				sb = new StringBuffer();
				sb.append(" INSERT INTO SUMMARY_PORTAL (D_SEQ, MD_SITE_NAME, S_SEQ, MD_TITLE, MD_URL, MD_DATE)    \n");
			    sb.append("   VALUES ("+map.get("D_SEQ")+", '"+map.get("MD_SITE_NAME")+"', "+map.get("S_SEQ")+", '"+map.get("MD_TITLE").toString().replaceAll("'","")+"', '"+map.get("MD_URL")+"', '"+map.get("MD_DATE")+"') \n");
			    System.out.println(sb.toString());		
			    stmt.executeUpdate(sb.toString());
			}
			                                                                                    
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    //if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	}
	
	public void updateSumaryPortal(ArrayList list){
		
		try {
			
			//dbconn = new DBconn();
			//dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			Map map = null;
			for(int i=0; i<list.size(); i++){
				map = new HashMap();
				map = (HashMap)list.get(i);
				
				sb = new StringBuffer();
				sb.append(" UPDATE SUMMARY_PORTAL																\n"); 
				sb.append(" SET DOC_CNT = "+map.get("docCnt")+", POS_CNT = "+map.get("pos")+", NEG_CNT = "+map.get("neg")+", NEU_CNT = "+map.get("neu")+"      \n");
				sb.append(" WHERE D_SEQ = "+map.get("p_docid")+"                                                                \n");
			    System.out.println(sb.toString());		
			    stmt.executeUpdate(sb.toString());
			    
			    
			}
			                                                                                    
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    //if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	}
	
	
	
	public ArrayList getPortalList(String doc_id){
		ArrayList result = new ArrayList();
		HashMap map = new HashMap();
		
		try {
			
			//dbconn = new DBconn();
			//dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT DISTINCT(A.D_SEQ) AS D_SEQ, A.MD_SITE_NAME, A.S_SEQ, A.MD_TITLE, A.MD_URL, A.MD_DATE		\n");
			sb.append("  FROM META A , IDX B                                                    \n");
			sb.append(" WHERE S_SEQ IN (2196,2199,3883,4537)                                    \n");
			sb.append("   AND A.D_SEQ > "+doc_id+"                                              \n");
			//sb.append("   AND MD_DATE BETWEEN '"+eDate+" 00:00:00' AND '"+eDate+" 23:59:59'   \n");
			sb.append("   AND A.MD_SEQ = B.MD_SEQ                                               \n");
			sb.append("   AND B.K_XP = 11                                                       \n");
			sb.append("   ORDER BY A.MD_DATE ASC                                                \n");
			sb.append("  LIMIT 20                                                \n");
			System.out.println(sb.toString());		
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				map = new HashMap();
				map.put("D_SEQ", rs.getString("D_SEQ"));
				map.put("MD_SITE_NAME", rs.getString("MD_SITE_NAME"));
				map.put("S_SEQ", rs.getString("S_SEQ"));
				map.put("MD_TITLE", rs.getString("MD_TITLE"));
				map.put("MD_URL", rs.getString("MD_URL").toString());
				map.put("MD_DATE", rs.getString("MD_DATE"));
				result.add(map);
				p_docid = "";
				if("".equals(p_docid)){
					p_docid = rs.getString("D_SEQ");
				}else{
					p_docid += ","+rs.getString("D_SEQ");
				}
				
				last_update_seq = rs.getString("D_SEQ");
			}
			                                                                                    
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    //if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}

		return result;
	}
	
	public ArrayList getPortalList2(String doc_id){
		ArrayList result = new ArrayList();
		HashMap map = new HashMap();
		
		try {
			
			//dbconn = new DBconn();
			//dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			
			sb.append("SELECT DISTINCT(A.D_SEQ) AS D_SEQ, A.MD_SITE_NAME, A.S_SEQ, A.MD_TITLE, A.MD_URL, A.MD_DATE	\n");
			sb.append("  FROM META A , IDX B , ISSUE_DATA C                                                         \n");
			sb.append(" WHERE A.S_SEQ IN (2196,2199,3883,4537)                                                      \n");
			sb.append("   AND A.MD_SEQ = B.MD_SEQ                                                                   \n");
			sb.append("   AND A.MD_SEQ = C.MD_SEQ                                                                   \n");
			sb.append("   AND B.K_XP != 14                                                                          \n");
			sb.append("   AND A.D_SEQ > "+doc_id+"                                              \n");
			sb.append("ORDER BY A.D_SEQ ASC                                                                         \n");
			sb.append("  LIMIT 20                                                \n");
			System.out.println(sb.toString());		
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				map = new HashMap();
				map.put("D_SEQ", rs.getString("D_SEQ"));
				map.put("MD_SITE_NAME", rs.getString("MD_SITE_NAME"));
				map.put("S_SEQ", rs.getString("S_SEQ"));
				map.put("MD_TITLE", rs.getString("MD_TITLE"));
				map.put("MD_URL", rs.getString("MD_URL").toString());
				map.put("MD_DATE", rs.getString("MD_DATE"));
				result.add(map);
				
				if("".equals(p_docid)){
					p_docid = rs.getString("D_SEQ");
				}else{
					p_docid += ","+rs.getString("D_SEQ");
				}
				
				last_update_seq = rs.getString("D_SEQ");
			}
			                                                                                    
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    //if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}

		return result;
	}
	
	
	
	public ArrayList getPortalReplyList(String p_docid , String p_date){
		ArrayList result = new ArrayList();
		
		String url = "http://lucyapi.realsn.com/API?cmd=replySortCnt&systemkey=54&userid=system3"; 
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
			JSONArray jsonArray = new JSONArray();
			if(!root.isNull("docs")){
				jsonArray = root.getJSONArray("docs");	
			}
			
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
			//try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
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
	

}
