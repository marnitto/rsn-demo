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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class DAEGUPortalAPI_Response {

	
	static DBconn conn = null;
	static StringBuffer sb = null;
	static PreparedStatement pstmt = null;
	static ResultSet rs = null;
	int cs_seq = 44;	//서울시청 고객사 번호
	int limitMin = 15; //15분 이상인 요청 데이터 삭제
	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void main(String args[]){
		DAEGUPortalAPI_Response spar = new DAEGUPortalAPI_Response();
		
		try {
			conn = new DBconn();
			conn.getSubDirectConnection();
			
			//0.API 테이블에서 요청이 'Y'인 데이터 중 하루 이상 지난 데이터 삭제하기
			spar.deleteApi();
			
			//1.API 테이블에서 요청이 'Y'인 데이터 조회
			List list = spar.getApiDataList();
			
			//2.조회 된 기사 LucyAPI 이용 댓글 업데이트
			ArrayList result = new ArrayList();
			if(list.size() > 0){
				String tmp[] = new String[2];
				for(int i = 0 ; i < list.size(); i++){
					tmp = new String[2];
					tmp = (String[])list.get(i);
					result = spar.getPortalReplyList(tmp[0], tmp[1].replaceAll("-", ""));
					
					Thread.sleep(2000);
					
					boolean chk = spar.updateSumaryPortal(result);
					
					if(chk){
						//3.업데이트 한 데이터중 요청시간(RES_SEQ)이 현재 시간 기준 15분이상인 경우 테이블에서 삭제
						spar.responesApi( result );		
					}
				}	
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
	
	
	public boolean updateSumaryPortal(ArrayList list){
		boolean result = false;
		StringBuffer sb = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Map map = null;
			for(int i=0; i<list.size(); i++){
				map = new HashMap();
				map = (HashMap)list.get(i);
				
				sb = new StringBuffer();
				sb.append(" UPDATE SUMMARY_PORTAL																\n"); 
				sb.append(" SET DOC_CNT = "+map.get("docCnt")+", POS_CNT = "+map.get("pos")+", NEG_CNT = "+map.get("neg")+", NEU_CNT = "+map.get("neu")+"      \n");
				sb.append(" WHERE D_SEQ = "+map.get("p_docid")+"                                                                \n");
				System.out.println(sb.toString());
				pstmt = conn.createPStatement(sb.toString());
			    if(pstmt.executeUpdate()> 0){
			    	result=true;
			    }
			}
			                                                                                    
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			try {if (rs != null) {rs.close();}} catch (SQLException ex) {}
			try {if (pstmt != null) {pstmt.close();}} catch (SQLException ex) {}
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
			//try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		
		return result;
	}
	
	
	public List getApiDataList(){
		List result = new ArrayList();;
		StringBuffer sb = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			sb = new StringBuffer();
			sb.append("SELECT D_SEQ, date_format(MD_DATE,'%Y-%m-%d') AS MD_DATE FROM SUMMARY_API_REQUEST WHERE API_YN = 'Y'	\n");
			System.out.println(sb.toString());
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			String tmp[] = new String[2];
			while(rs.next()){
				tmp = new String[2];
				tmp[0] = rs.getString("D_SEQ").toString();
				tmp[1] = rs.getString("MD_DATE").toString();
				result.add(tmp);
			}
			
		}catch (Exception ex){	
			Log.writeExpt("getApiDataList", ex.getMessage());
		}finally {
			try {if (rs != null) {rs.close();}} catch (SQLException ex) {}
			try {if (pstmt != null) {pstmt.close();}} catch (SQLException ex) {}		
		}
		
		return result;
	}
	
	
	public void deleteApi(){
		StringBuffer sb = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String doc_id = "";
		
		try{
			
			sb = new StringBuffer();
			sb.append(" SELECT D_SEQ FROM SUMMARY_API_REQUEST WHERE DATE_FORMAT(RES_DATE, '%Y-%m-%d') < DATE_FORMAT(NOW(), '%Y-%m-%d'); \n");
			System.out.println(sb.toString());
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				doc_id = rs.getString("D_SEQ");
				
				sb = new StringBuffer();
				sb.append(" DELETE FROM SUMMARY_API_REQUEST WHERE D_SEQ ="+doc_id+" \n");
				System.out.println(sb.toString());
				pstmt = conn.createPStatement(sb.toString());
				pstmt.executeUpdate();
			}
			
		}catch (Exception ex){	
			Log.writeExpt("responesApi", ex.getMessage());
		}finally {
			try {if (rs != null) {rs.close();}} catch (SQLException ex) {}
			try {if (pstmt != null) {pstmt.close();}} catch (SQLException ex) {}		
		}
		
	}
	
	public void responesApi(List list){
		StringBuffer sb = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Date edate = new Date();
		Date sdate = null;
		
		String api_Date = "";
		
		try{
			
			if(list.size() > 0){
				Map map =  null;
				for(int i=0; i < list.size(); i++){
					map = new HashMap();
					map = (HashMap)list.get(i);
					
					sb = new StringBuffer();
					sb.append(" SELECT DATE_FORMAT(RES_DATE, '%Y-%m-%d %T') AS RES_DATE FROM SUMMARY_API_REQUEST WHERE D_SEQ ="+map.get("p_docid")+" LIMIT 1 \n");
					System.out.println(sb.toString());
					pstmt = conn.createPStatement(sb.toString());
					rs = pstmt.executeQuery();
					if(rs.next()){
						api_Date = rs.getString("RES_DATE");
					}
					
					sdate = fmt.parse(api_Date) ;
					int min = (int) (((edate.getTime() - sdate.getTime()) / 1000) / 60);
					
					if(min > limitMin){
						sb = new StringBuffer();
						sb.append(" DELETE FROM SUMMARY_API_REQUEST WHERE D_SEQ ="+map.get("p_docid")+" \n");
						System.out.println(sb.toString());
						pstmt = conn.createPStatement(sb.toString());
						pstmt.executeUpdate();
					}
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
