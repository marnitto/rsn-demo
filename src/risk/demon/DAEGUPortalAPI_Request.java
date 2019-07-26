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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;
import org.jdom.JDOMException;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONException;
import risk.json.JSONObject;
import risk.util.Log;

public class DAEGUPortalAPI_Request {

	
	static DBconn conn = null;
	static StringBuffer sb = null;
	static PreparedStatement pstmt = null;
	static ResultSet rs = null;
	int cs_seq = 44;	//서울시청 고객사 번호
	
	
	public static void main(String args[]){
		DAEGUPortalAPI_Request spar = new DAEGUPortalAPI_Request();
		
		try {
			conn = new DBconn();
			conn.getSubDirectConnection();
			
			
			//1.API 요펑 테이블에서 요청할 기사가 있는지 조회
			String d_seqs = spar.getApiDataList();
			
			//2.조회 된 기사 API 요청
			List list = new ArrayList();
			if(!"".equals(d_seqs)){
				list = spar.requestApi(d_seqs);	
			}
			
			//3.요청한 기사 댓글요청 flag값 Y로 변경, 요청 시간 변경
			if(list.size() > 0){
				spar.responesApi( list );	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {if (conn != null) {conn.close();}} catch (SQLException ex) {}
		}
		
	}
	
	
	public String getApiDataList(){
		String result = "";
		StringBuffer sb = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			sb = new StringBuffer();
			sb.append("SELECT D_SEQ FROM SUMMARY_API_REQUEST WHERE API_YN = 'N'	\n");
			System.out.println(sb.toString());
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				if("".equals(result)){
					result = rs.getString("D_SEQ").toString(); 
				}else{
					result += ","+rs.getString("D_SEQ").toString();
				}
			}
			
		}catch (Exception ex){	
			Log.writeExpt("getApiDataList", ex.getMessage());
		}finally {
			try {if (rs != null) {rs.close();}} catch (SQLException ex) {}
			try {if (pstmt != null) {pstmt.close();}} catch (SQLException ex) {}		
		}
		
		return result;
	}
	
	public void responesApi(List list){
		StringBuffer sb = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			
			if(list.size() > 0){
				Map map =  null;
				for(int i=0; i < list.size(); i++){
					map = new HashMap();
					map = (HashMap)list.get(i);
					boolean chk = (Boolean) map.get("msg");
					sb = new StringBuffer();
					sb.append("UPDATE SUMMARY_API_REQUEST                  \n");
					if(chk){
						sb.append("   SET API_YN = 'Y', RES_DATE = NOW()   \n");	
					}else{
						sb.append("   SET API_YN = 'N', RES_DATE = NOW()   \n");
					}
					sb.append(" WHERE D_SEQ = "+map.get("d_seq")+"         \n");
					System.out.println(sb.toString());
					pstmt = conn.createPStatement(sb.toString());
					pstmt.executeUpdate();
					
				}
			}
			
		}catch (Exception ex){	
			Log.writeExpt("responesApi", ex.getMessage());
		}finally {
			try {if (rs != null) {rs.close();}} catch (SQLException ex) {}
			try {if (pstmt != null) {pstmt.close();}} catch (SQLException ex) {}		
		}
		
	}
	
	
	public List requestApi(String  d_seqs){
		List result = new ArrayList();
		StringBuffer sb = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String url = "http://portalreply_api.realsn.com/api.php?&cs_seq="+cs_seq; 
		String params = "";
		params += "&d_seq="+d_seqs;
		
		System.out.println(url+params);
		
		try{
			JSONObject root = rootReturn(url, params);
			
			if(root.isNull("error")){
			
				JSONArray jsonArray = root.getJSONArray("data");
				
				if(jsonArray.length() > 0){
					JSONObject jsonObject = null;
					LinkedHashMap map = null;
					for(int i=0; i < jsonArray.length(); i++){
						map = new LinkedHashMap();
						jsonObject = (JSONObject)jsonArray.get(i);
						map.put("d_seq", jsonObject.get("d_seq"));
						map.put("msg", jsonObject.get("result"));
						result.add(map);
					}
				}
				
			}else{
				Log.crond("error - 비정상요청 !! 고객사 번호 또는 문서번호를 확인해주세요.");
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
