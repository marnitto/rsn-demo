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
import java.util.List;
import java.util.Map;

import org.jdom.Element;
import org.jdom.JDOMException;

import risk.DBconn.DBconn;
import risk.analysis.SeoulUtil;
import risk.json.JSONArray;
import risk.json.JSONException;
import risk.json.JSONObject;
import risk.util.ConfigUtil;
import risk.util.DateUtil;
import risk.util.Log;

public class DAEGUPressDataSameAna{
	
	static String apiKey = "";
	static String userId = "system";
    private StringBuffer sb = null;
    private Statement stmt = null;
    private ResultSet rs    = null;
	private PreparedStatement pstmt = null;
	private String portal_naver_seq = "499,500,504,505,506,507,508,509,2306715,2306711";	//포탈 초기면 SB_SEQ 
	private String portal_daum_seq = "451,450,452,2306747,455";	//포탈 초기면 SB_SEQ
	
	/**
     *  로컬 DB 컨넥션
     * @return
     */
	static DBconn dbconn = null;
	
	/**
     * MigrationData main 
     * @return
     */
    public static void main( String[] args )
    {

    	DAEGUPressDataSameAna s_pds = new DAEGUPressDataSameAna();
    	ConfigUtil cu = new ConfigUtil();
    	
    	Log.crond("DAEGUPressDataSameAna START ...");    
    	
        try{
        	DateUtil du = new DateUtil();
        	
        	du.addDay(-1);
        	String eDate = du.getDate();
        	
        	du.addDay(-3);
        	String sDate = du.getDate();
        	
        	sDate = "2017-07-10";
        	
        	System.out.println(sDate);
        	
        	du.addDay(sDate, -3);
        	String sDate_portal = du.getDate();
        	du.addDay(sDate, 3);
        	String eDate_portal = du.getDate();
        	
        	System.out.println(sDate_portal);
        	System.out.println(eDate_portal);
        	
        	dbconn = new DBconn();
        	dbconn.getSubDirectConnection();
        	
        	apiKey = cu.getConfig("LUCY_API");	    	
        	
        	List list = null;
	        	//유사율 검사할 데이터 가져오기
	        	list = s_pds.getPressRealseData(sDate, 1);
	        	//유사율 검사
	        	s_pds.checkSamePercent(list);
	        	
	        	list =  null;
	        	//댓글 수 분석할 데이터 가져오기
	        	list = s_pds.getPressRealseData(sDate, 2);
	        	//댓글 수 검사
	        	s_pds.updateReplyCount(list);
	        	
	        	list = null;
	        	/**
	        	 * 보도자료 뉴스를 조회 한 후 섹션데이터에서 비교
	        	 */
	        	//보도자료 가져오기
	        	list =  s_pds.getPressRealseData(sDate, 2);
	        	//섹션 뉴스 데이터랑 비교 하기
	        	s_pds.updateTopyn(list, sDate_portal, eDate_portal, 2);
        	
        	//list = null;
        	/**
        	 * 섹션데이터를 조회 한 후 보도자료의 뉴스 기사와 비교
        	 */
        	//섹션 뉴스 가져오기
        	//list =  s_pds.getPortalNews(sDate);
        	//보도자료 데이터랑 비교 하기
        	//s_pds.updateTopyn(list, sDate_portal, eDate_portal, 1);
        	
        	
    		
        }catch(Exception ex) {
        	Log.crond(ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        }finally {
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        
        Log.crond("DAEGUPressDataSameAna END ...");
    }
    
    
    public void checkSamePercent(List list){
    	
    	System.out.println("IN same percent check...");
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
    	
        SeoulUtil s_util = new SeoulUtil();
    	
    	String M_title = "";
    	String M_contetnt = "";
    	
    	String S_title = "";
    	String S_contetnt = "";
    	
    	int percent = 0;
    	try{
    	
	    	if(list.size() > 0){
	    		System.out.println("list.size() - "+list.size());
	    		Map map = new HashMap();
	    		for(int i=0; i < list.size(); i++){
	    			map = new HashMap();
	    			map = (HashMap)list.get(i);
	    			
	    			System.out.println("map.isEmpty() - "+map.isEmpty());
	    			
	    			if( map.get("m_title") != null ){
	    				M_title = map.get("m_title").toString();	
	    			}
	    			
	    			if( map.get("m_content") != null ){
	    				M_contetnt = map.get("m_content").toString();	
	    			}
	    			
	    			if( map.get("d_title") != null ){
	    				S_title = map.get("d_title").toString();	
	    			}
	    			
	    			
	    			if( map.get("d_content") != null ){
	    				S_contetnt = map.get("d_content").toString();	
	    			}
	    			
	    			percent = s_util.textWordCompare(M_title+" "+M_contetnt, S_title+" "+S_contetnt);
	    			
	    			System.out.println("id : "+map.get("i_docid").toString()+",   유사율 : "+percent);
	    			
	    			try{
	    				sb = new StringBuffer();
	    				sb.append("UPDATE PRESS_RELEASE_DATA													   \n");
	    				sb.append("SET d_percent = "+percent+", d_ana_yn = 'Y'                                     \n");
	    				sb.append("WHERE i_docid = '"+map.get("i_docid").toString()+"'                             \n");
	    				System.out.println(sb.toString());
	         			stmt = dbconn.createStatement();
	         			stmt.executeUpdate( sb.toString() );
	    				
	    			} catch(SQLException ex) {
	         			Log.crond(ex.toString());
	         		}
	    			
	    		}	//for end
	    	}	//if end
	    	
    	}catch(Exception ex) {
          	Log.crond(ex.toString());
          	ex.printStackTrace();
        	System.exit(1);
         } finally {
          	sb = null;
              if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
              if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
         }
    	
    }
    
    
    public void updateTopyn(List list , String sDate, String eDate, int type){
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
    	if(list.size() > 0){
    		Map map = null;
    		String title = "";
    		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
    		
    		for(int i=0; i < list.size(); i++){
    			map = new HashMap();
    			map = (HashMap)list.get(i);
    			
    	        String i_docid = "";
    	        String s_seq = map.get("s_seq").toString();
    	        
    			try{
    				if( type == 1){
    					
    					title = map.get("d_title").toString().replaceAll("'", "");
    	    			title = title.replaceAll(match, "");
    	    			String tmp[] = title.split(" ");
    					
    					sb = new StringBuffer();
	    	            sb.append(" SELECT D_SEQ, I_DOCID													\n");
	    	            sb.append("   FROM PRESS_RELEASE_DATA                                       \n");
	    	            sb.append("  WHERE D_TYPE = 'news'   										\n");
	    	            sb.append("    AND D_DATE  BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'       \n");
	    	            for(int k=0; k < tmp.length; k++){
	    	            	sb.append("    AND CONCAT_WS(' ' , D_TITLE, D_CONTENT) like '%"+tmp[k]+"%'   \n");		
	    	            }
	    	            //sb.append("    AND CONCAT_WS(' ' , D_TITLE, D_CONTENT) like '%"+title+"%'   \n");
	    	            System.out.println(sb.toString());
	    				stmt = dbconn.createStatement();
	    				rs = stmt.executeQuery( sb.toString() );
	    				
	    				while(rs.next()){
	    					if(!"".equals(i_docid)){
	    						i_docid += ","+rs.getString("I_DOCID");
	    					}else{
	    						i_docid = rs.getString("I_DOCID");
	    					}
	    				}
	    				
	    				if( !"".equals(i_docid) ){
	    					sb = new StringBuffer();
	    					sb.append("UPDATE PRESS_RELEASE_DATA			  \n"); 
	    					sb.append("SET  d_top = 1   , d_ana_yn = 'Y'       				  \n");
	    					sb.append("WHERE i_docid in ( '"+i_docid+"' )          \n");
	    					stmt = dbconn.createStatement();
	    					stmt.executeUpdate(sb.toString());
	    				}
	    				
    				}else{
    					
    					title = map.get("d_title").toString().replaceAll("'", "");
    	    			//title = title.replaceAll(match, "");
    	    			//title = title.trim();
    	    			//String tmp[] = title.split(" ");
    	    			if(title.length() > 20){
    	    				title = title.substring(0, 20);	
    	    			}
    	    			//
    	    			//title = title.trim();
    					
    					sb = new StringBuffer();
	    	            sb.append(" SELECT SB_SEQ							  				  \n");
	    	            sb.append("   FROM SECTION_NEWS                                       \n");
	    	            sb.append("  WHERE D_DATETIME  BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'   										\n");
	    	            //for(int k=0; k < tmp.length; k++){
	    	            //	sb.append("    AND D_TITLE like '%"+tmp[k]+"%'   \n");		
	    	            //}
	    	            sb.append("    AND D_TITLE like '%"+title+"%'   \n");
	    	            
	    	            if("2196".equals(s_seq) ){
	    	            	sb.append("  AND S_SEQ = 6050  		\n");
	    	            	sb.append("  AND SB_SEQ IN ("+portal_naver_seq+")	\n");
	    	            }else if( "2199".equals(s_seq )){
	    	            	sb.append("  AND S_SEQ = 6051  		\n");
	    	            	sb.append("  AND SB_SEQ IN ("+portal_daum_seq+")	\n");
	    	            }
	    	            
	    	            System.out.println(sb.toString());
	    	            pstmt = dbconn.createPStatement(sb.toString());
	        			rs = pstmt.executeQuery();
	    				int cnt = 0;
	    				String top_seqs = ""; 
	    				while(rs.next()){
	    					if("".equals(top_seqs)){
	    						top_seqs = rs.getString("SB_SEQ");	
	    					}else{
	    						top_seqs += ","+rs.getString("SB_SEQ");
	    					}
	    					cnt++;
	    				}
	    				
	    				if( cnt > 0){
	    					sb = new StringBuffer();
	    					sb.append("UPDATE PRESS_RELEASE_DATA			  \n"); 
	    					sb.append("SET  d_top = 1   , d_ana_yn = 'Y',   top_seqs = '"+top_seqs+"'    \n");
	    					sb.append("WHERE i_docid in ( '"+map.get("i_docid").toString()+"' )          \n");
	    					stmt = dbconn.createStatement();
	    					stmt.executeUpdate(sb.toString());
	    				}
    				}
    				

    			} catch(SQLException ex) {
    				Log.crond(ex.toString());
    				ex.printStackTrace();
    	        } catch(Exception ex) {
    	        	Log.crond(ex.toString());
    	        } finally {
    	        	sb = null;
    	            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
    	            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
    	            if (pstmt != null) try { pstmt.close(); pstmt = null;} catch(SQLException ex) {}
    	        }
    		}
    	}
    }
    
    public List getPortalNews(String sDate){
    	List result = new ArrayList();
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;

		try{
            sb = new StringBuffer();
            sb.append(" SELECT D_SEQ, D_TITLE, D_DATETIME	\n");
            sb.append("   FROM SECTION_NEWS                                       \n");
            sb.append("  WHERE DATE_FORMAT(D_DATETIME,'%Y-%m-%d') = '"+sDate+"'   \n");
            sb.append("  GROUP BY D_TITLE                                    \n");
            sb.append("  ORDER BY D_SEQ ASC                                    \n");
            
			stmt = dbconn.createStatement();
			rs = stmt.executeQuery( sb.toString() );
			Map map = null;
			while(rs.next()){
				map = new HashMap();
				map.put("d_seq", rs.getString("D_SEQ"));
				map.put("d_title", rs.getString("D_TITLE"));
				map.put("d_date", rs.getString("D_DATETIME"));
				result.add(map);
			}
			
			System.out.println("조회된 데이터 : "+result.size());

		} catch(SQLException ex) {
			Log.crond(ex.toString());
        } catch(Exception ex) {
        	Log.crond(ex.toString());
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    
    public List getPressRealseData(String sDate, int type){
    	List result = new ArrayList();
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;

		try{
            sb = new StringBuffer();
            sb.append(" SELECT  A.D_SEQ , A.I_DOCID, convert(replace(A.I_DOCID,'DN',''),UNSIGNED) AS DOCID, A.D_TITLE, A.D_URL, A.D_CONTENT 	\n");
            sb.append(" 	  , DATE_FORMAT(A.D_DATE,'%Y-%m-%d') as D_DATE, B.D_TITLE AS MAIN_TITLE, B.D_CONTENT AS MAIN_CONTENT	\n");
            sb.append("		  , A.S_SEQ	\n");
            sb.append(" FROM PRESS_RELEASE_DATA A, PRESS_RELEASE_MNG B                                                                                                              \n");
            if(type == 1){
            	sb.append(" WHERE 1=1                                                                                                                   \n");
            }else{
            	sb.append(" WHERE LOWER(A.D_TYPE) = 'news'                                                                                                                   \n");	
            }
            sb.append("  AND DATE_FORMAT(A.D_DATE,'%Y-%m-%d') = '"+sDate+"'                                                                                     	\n");
            sb.append("  AND A.D_SEQ = B.D_SEQ                                                                                                                  \n");
            System.out.println(sb.toString());
			stmt = dbconn.createStatement();
			rs = stmt.executeQuery( sb.toString() );
			Map map = null;
			while(rs.next()){
				map = new HashMap();
				map.put("d_seq", rs.getString("D_SEQ"));
				map.put("s_seq", rs.getString("S_SEQ"));
				map.put("i_docid", rs.getString("I_DOCID"));
				map.put("docid", rs.getString("DOCID"));
				map.put("d_title", rs.getString("D_TITLE"));
				map.put("d_content", rs.getString("D_CONTENT"));
				map.put("d_date", rs.getString("D_DATE"));
				map.put("m_title", rs.getString("MAIN_TITLE"));
				map.put("m_content", rs.getString("MAIN_CONTENT"));
				result.add(map);
			}
			
			System.out.println("조회된 데이터 : "+result.size());

		} catch(SQLException ex) {
			Log.crond(ex.toString());
        } catch(Exception ex) {
        	Log.crond(ex.toString());
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    
    public void updateReplyCount(List list){
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
    	
    	try{
    	
    		int idx = 1;
    		String p_docids = "";
    		String p_date = "";
	    	if(list.size() > 0){
	    		Map map = new HashMap();
	    		for(int i=0; i < list.size(); i++){
	    			map = new HashMap();
	    			map = (HashMap)list.get(i);
	    			
	    			p_docids += ","+map.get("docid"); 
	    			p_date = map.get("d_date").toString();
	    			
	    			if( idx == 100){
	    				
	    				updatePressData( p_docids, p_date);
	    				Thread.sleep(1000);
	    				p_docids = "";
	    				idx = 1;
	    			}
	    			
	    			idx++;
	    		}	//for end
	    		
	    		updatePressData( p_docids, p_date);
	    		Thread.sleep(1000);
	    		
	    	}	//if end
	    	
    	}catch(Exception ex) {
          	Log.crond(ex.toString());
         }
    }
    
    public void updatePressData(String p_docids, String p_date){
    	
    	p_docids = p_docids.substring(1, p_docids.length());
		List updateList = getReplyCount( p_docids , p_date.replaceAll("-", "") );
		
		try{
			
			if(updateList.size() > 0){
				Map map2 = null;
				for(int j=0; j < updateList.size(); j++){
					map2 = (HashMap)updateList.get(j);
					try{
	    				sb = new StringBuffer();
	    				sb.append("UPDATE PRESS_RELEASE_DATA													   \n");
	    				sb.append("SET reply_count = "+map2.get("docCnt")+"                                     \n");
	    				sb.append("	,r_pos_count = "+map2.get("pos")+"	\n");
	    				sb.append("	,r_neg_count = "+map2.get("neg")+"	\n");
	    				sb.append("	,r_neu_count = "+map2.get("neu")+"	\n");
	    				sb.append("	,d_ana_yn = 'R' 					\n");
	    				sb.append("WHERE d_type = 'news'        \n");
	    				sb.append("	AND convert(replace(i_docid,'DN',''),UNSIGNED) = "+map2.get("p_docid").toString()+" \n");
	    				System.out.println(sb.toString());
	         			stmt = dbconn.createStatement();
	         			stmt.executeUpdate( sb.toString() );
	    				
	    			} catch(SQLException ex) {
	         			Log.crond(ex.toString());
	         		}
				}
			}
			
		}catch(Exception ex) {
          	Log.crond(ex.toString());
         } finally {
          	sb = null;
              if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
              if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
         } 
    }
    
    
    public ArrayList getReplyCount(String p_docid , String p_date){
		ArrayList result = new ArrayList();
		
		String url = "http://lucyapi.realsn.com/API?cmd=replySortCnt&systemkey="+apiKey+"&userid=system"; 
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
			}else{
				System.out.println("데이터 결과가 없습니다.");
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
