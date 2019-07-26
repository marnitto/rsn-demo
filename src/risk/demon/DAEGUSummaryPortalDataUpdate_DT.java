package risk.demon;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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

public class DAEGUSummaryPortalDataUpdate_DT {
	
	static DBconn dbconn = null;
	static String name = "DAEGUSummaryPortalDataUpdate_DT";
    private StringBuffer sb = null;
    private Statement stmt = null;
    private ResultSet rs    = null;
	private PreparedStatement pstmt = null;
	
	static String p_docid = "";
	static String last_update_seq = "";
	
	static String input_date = "2017-08-31";	//(ex): 2017-02-15
	static String input_dseq = "3396274381";	//(ex): 2955598223
	
	public static void main(String args[]){
		
		Log.crond(name,"DAEGUSummaryPortalDataUpdate_DT START ...");
    	
    	try{
    		
    		dbconn = new DBconn(); //로컬디비
    		dbconn.getSubDirectConnection();
			
    		DateUtil du = new DateUtil();
    		String today = du.getCurrentDate("yyyy-MM-dd");
    		String timeFormat = du.getCurrentTime();
    		DAEGUSummaryPortalDataUpdate_DT ssp = new DAEGUSummaryPortalDataUpdate_DT();
    		boolean chk = true;
    		
    		String dateArr[] = new String[1];
    		
    		dateArr[0] = input_date;
    		
    		for(int index =0; index < dateArr.length; index++){
    			chk = true;    			
    			int startNumber = 0;
    			String doc_id = "";
    			while(chk){
    				
    				doc_id =  ssp.getportalDocId(startNumber, dateArr[index], dateArr[index]);
    				
    				if( !"".equals(doc_id) ){
    					
    					ArrayList result = ssp.getPortalReplyList(doc_id, dateArr[index].replaceAll("-", ""));
    					
    					ssp.updateSumaryPortal(result);
    					Thread.sleep(1000);
    				}else{
    					chk = false;
    				}
    				startNumber = startNumber + 100;
    			}
    		}
    		
    	}catch(Exception ex) {
    		Log.writeExpt(name,ex.getMessage());
        	ex.printStackTrace();
        	System.exit(1);
        }finally {
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
    	
    	Log.crond(name,"DAEGUSummaryPortalDataUpdate_DT END ...");
	    
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
	
	
	
	public String getportalDocId(int startNumber, String fromDate, String toDate){
		String result = "";
		
		try{
			stmt = dbconn.createStatement();
				
			sb = new StringBuffer();
			sb.append("SELECT D_SEQ                                                                         \n");
			sb.append("  FROM SUMMARY_PORTAL                                                                \n");
			sb.append(" WHERE 1=1 #MD_DATE BETWEEN '"+fromDate+" 00:00:00' AND '"+toDate+" 23:59:59'             \n");
			sb.append("   AND D_SEQ = "+input_dseq+"                                                        \n");
			sb.append(" ORDER BY D_SEQ ASC                                                                  \n");
			sb.append(" LIMIT "+startNumber+", 100                                                          \n");
		    System.out.println(sb.toString());		                                                        
		    rs = stmt.executeQuery(sb.toString());
		    while(rs.next()){
		    	if("".equals(result)){
		    		result = rs.getString("D_SEQ");
		    	}else{
		    		result += ","+rs.getString("D_SEQ");	
		    	}
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
		
		String url = "http://lucyapi.realsn.com/API?cmd=replySortCnt&systemkey=54&userid=system"; 
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
			
			if(root.isNull("errorMessage")){
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
				
			}else{
				Log.crond("원문 번호가 잘못 입력되었거나, 해당 기사의 댓글이 없습니다. - "+params);
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
