package risk.demon;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONException;
import risk.json.JSONObject;
import risk.util.ConfigUtil;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class DAEGUPressDataAna{
	
	static String apiKey = "";
	static String userId = "system";
	
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

    	ConfigUtil cu = new ConfigUtil();
    	
    	Log.crond("DAEGUPressDataAna START ...");    
    	
        try{
        	DateUtil du = new DateUtil();
        	
        	//du.addDay(-1);
        	//String eDate = du.getDate();
        	
        	du.addDay(-4);
        	String sDate = du.getDate();
        	
//			수동으로 돌리려는 날짜를 입력한다.        	
        	sDate = "2017-07-10";
    		
        	dbconn = new DBconn();
        	dbconn.getSubDirectConnection();
        	
        	apiKey = cu.getConfig("LUCY_API");	    	
        	
        	//4일전 보도자료 중 주요 키워드가 등록된 건 조회
        	List list = getPressRealseMng(sDate);
        	
        	String s_seq [] = new String[]{"2196 2199","10464"};
        	String sourceType[] = new String[]{"DN","TW"};
        	String d_seq = "";
        	String d_keyword = "";
        	
        	//해당 문서건에 대한 주요 키워드로 유사건수를 lucy api 검색을 통해 조회
        	Map map = null;
        	if(list.size() > 0){
        		for(int i=0; i < list.size(); i++){
        			map = new HashMap();
        			map = (HashMap)list.get(i);
        			d_seq = map.get("d_seq").toString();
        			d_keyword = map.get("d_keyword").toString();
        			getLucyData( d_seq,  d_keyword, sDate,  s_seq[0], sourceType[0] );
        			Thread.sleep(1000);
        			getLucyData( d_seq,  d_keyword, sDate,  s_seq[1], sourceType[1] );
        			Thread.sleep(1000);
        			
        			updateData(d_seq);
        		}
        	}
        	
    		
        }catch(Exception ex) {
        	Log.crond(ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        }finally {
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        
        Log.crond("DAEGUPressData END ...");
    }
    
    public static List getPressRealseMng(String sDate){
    	List result = new ArrayList();
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;

		try{
            sb = new StringBuffer();
            sb.append(" SELECT SEQ, D_SEQ, D_SITE, D_BOARD, D_DATE, D_TITLE, D_URL, D_CONTENT, D_KEYWORD, S_SEQ, SB_SEQ, D_TW_COUNT, D_SAME_COUNT, D_ANA_YN  \n");
            sb.append("   FROM PRESS_RELEASE_MNG \n");
            sb.append("  WHERE DATE_FORMAT(D_DATE, '%Y-%m-%d') = '"+sDate+"' \n");
            sb.append("    AND D_KEYWORD is not null AND D_KEYWORD != '' \n");
            System.out.println(sb.toString());
			stmt = dbconn.createStatement();
			rs = stmt.executeQuery( sb.toString() );
			Map map = null;
			while(rs.next()){
				map = new HashMap();
				map.put("d_seq", rs.getString("D_SEQ"));  
				map.put("s_seq", rs.getString("S_SEQ"));
				map.put("d_keyword", rs.getString("D_KEYWORD"));
				result.add(map);
			}

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
    
    public static void getLucyData(String d_seq, String keyword, String sDate,  String s_seq, String sourcetype){
    	DateUtil dateUtil = new DateUtil();
    	JSONArray resultArray = new JSONArray();
    	int row_limit = 100;
    	
    	try {
    		
    		String fromData;
			fromData = dateUtil.addDay(sDate, -3);
        	fromData = fromData.replaceAll("-", "");
        	
        	String toData = dateUtil.addDay(sDate, 3);
        	toData = toData.replaceAll("-", "");
	    	
	    	String url = "http://lucyapi.realsn.com/API?systemkey=" + apiKey + "&userid=" + userId + "&cmd=search";
	    	
	    	StringBuilder param = new StringBuilder();
	    	
	    	param.append("&i_sdate=" + fromData + "&i_edate=" + toData);
	   		param.append("&i_and_text="+URLEncoder.encode(keyword.toString(),"UTF-8"));
	   		param.append("&s_seq="+s_seq);
	   		param.append("&i_sourcetype="+sourcetype);
	   		param.append("&sort_field_first=i_crawlstamp&sort_type_first=desc&row_limit="+row_limit);
	   		
	   		String url_params = param.toString(); 
	   		
	   		String returnData = StringUtil.GetLucyAPIPost(url, url_params);
	    	System.out.println(" [ API CALL ] " + url + url_params.toString());
	    	
	    	JSONObject object = new JSONObject(returnData);
	    	JSONArray searchArray = new JSONArray();
	    	if(  !object.isNull("docs") ){
	    		searchArray = object.getJSONArray("docs");
	    		insertSearchData(d_seq, searchArray, sourcetype);
	    	}
	    	
	    	int rowCount = Integer.parseInt( object.getString("numFound"));
	    	
	    	int idx = 2;
	    	boolean chk = true;
	    	if ( rowCount >= row_limit  ){
	    		while(chk){
	    		
	    			returnData = StringUtil.GetLucyAPIPost(url, url_params+"&page_num="+idx);
	    			object = new JSONObject(returnData);
	    			searchArray = new JSONArray();
	    			searchArray = object.getJSONArray("docs");
	        		insertSearchData(d_seq, searchArray, sourcetype);
	        		
	        		rowCount = Integer.parseInt( object.getString("numFound"));
	        		if(rowCount < row_limit){
	        			chk = false;
	        		}
	        		idx ++;
	    		}
	    	}
	    	
    	} catch (ParseException e) {
    		e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	
    }
    
    public static void insertSearchData(String d_seq, JSONArray searchArray, String sourcetype){
    	 Statement stmt = null;
         ResultSet rs = null;
         StringBuffer sb = null;
         PreparedStatement pstmt = null;
         
         try{
         
	         if(searchArray.length() > 0){
	        	 JSONObject object = null;
	        	 for(int i=0; i < searchArray.length(); i++){
	        		 object = new JSONObject();
	        		 
	        		 try{
	        			 
	        		 	object = searchArray.getJSONObject(i);
	                 	sb = new StringBuffer();
	                 	sb.append(" INSERT INTO PRESS_RELEASE_DATA	  \n");
	                 	sb.append(" (D_SEQ                            \n");
	                 	sb.append(" , I_DOCID                         \n");
	                 	sb.append(" , D_SITE                          \n");
	                 	sb.append(" , D_BOARD                          \n");
	                 	sb.append(" , D_DATE                          \n");
	                 	sb.append(" , D_TITLE                         \n");
	                 	sb.append(" , D_URL                           \n");
	                 	sb.append(" , D_CONTENT                       \n");
	                 	sb.append(" , S_SEQ                           \n");
	                 	sb.append(" , D_PERCENT                       \n");
	                 	sb.append(" , D_TOP                           \n");
	                 	sb.append(" , REPLY_COUNT                     \n");
	                 	sb.append(" , D_TYPE)                         \n");
	                 	sb.append(" VALUES ("+d_seq+",?,?,?,?,?,?,?,?,?,?,?,?)  \n");
	                 	//sb.append(" VALUES ("+d_seq+"               \n");
	                 	//sb.append(" , '"+object.getString("i_docid")+"'                     \n");
	                 	//sb.append(" , '"+object.getString("i_sitename")+"'                  \n");
	                 	//sb.append(" , '"+getUnixTimeStamp(object.getLong("i_crawlstamp"), "yy/MM/dd")+"'                  \n");
	                 	//sb.append(" , '"+object.getString("i_title")+"'                     \n");
	                 	//sb.append(" , '"+object.getString("i_url")+"'                       \n");
	                 	//sb.append(" , '"+object.getString("i_content")+"'                   \n");
	                 	//sb.append(" , "+object.getString("s_seq")+"                         \n");
	                 	//sb.append(" , 0                     								\n");
	                 	//sb.append(" , 0                         							\n");
	                 	//sb.append(" , 0                   									\n");
	                 	//sb.append(" , '"+object.getString("i_doctype")+"')                  \n");
	                 	System.out.println(sb.toString());
	                 	pstmt = dbconn.createPStatement(sb.toString());
	                 	int paNum = 1;
	                 	pstmt.clearParameters();
	        			pstmt.setString(paNum++, object.getString("i_docid"));
	        			pstmt.setString(paNum++, object.getString("i_sitename"));
	        			pstmt.setString(paNum++, object.getString("i_boardname"));
	        			pstmt.setString(paNum++, getUnixTimeStamp(object.getLong("i_crawlstamp"), "yy/MM/dd"));
	        			pstmt.setString(paNum++, object.getString("i_title"));
	        			pstmt.setString(paNum++, object.getString("i_url"));
	        			pstmt.setString(paNum++, object.getString("i_content"));
	        			pstmt.setInt(paNum++, object.getInt("s_seq"));
	        			pstmt.setInt(paNum++, 0);
	        			pstmt.setInt(paNum++, 0);
	        			pstmt.setInt(paNum++, 0);
	        			pstmt.setString(paNum++, object.getString("i_doctype"));
	        			pstmt.execute();
	         			//stmt = dbconn.createStatement();
	         			//stmt.executeUpdate( sb.toString() );
	
	         		} catch(SQLException ex) {
	         			Log.crond(ex.toString());
	         		}
	        	 }
	        	 
	        	 //sb = new StringBuffer();
	        	 //sb.append("UPDATE PRESS_RELEASE_MNG															\n");
	        	 //if("TW".equals(sourcetype)){
	        	//	 sb.append("SET d_tw_count = "+searchArray.length()+", d_ana_yn = 'Y'     \n");	 
	        	 //}else{
	        	//	 sb.append("SET d_same_count = "+searchArray.length()+", d_ana_yn = 'Y'     \n");
	        	 //}
	        	 //sb.append("WHERE d_seq= "+d_seq+"                                                              \n");
	        	 //System.out.println(sb.toString());
      			 //stmt = dbconn.createStatement();
      			 //stmt.executeUpdate( sb.toString() );
	         }                                                                                                  
         
         }catch(Exception ex) {
          	Log.crond(ex.toString());
         } finally {
          	sb = null;
              if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
              if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
         }
    }
    
    
    public static void updateData(String d_seq){
   	 Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        PreparedStatement pstmt = null;
        
        String news_count = "";
        String tw_count = "";
        
        try{
	        	 
	        sb = new StringBuffer();
	        sb.append("SELECT COUNT(0) AS CNT FROM PRESS_RELEASE_DATA WHERE D_SEQ = "+d_seq+" AND D_TYPE = 'news'		\n");
	        System.out.println(sb.toString());
     		stmt = dbconn.createStatement();
     		rs = stmt.executeQuery(sb.toString());
     		if(rs.next()){
     			news_count = rs.getString("CNT");
     		}
     		
     		sb = new StringBuffer();
	        sb.append("SELECT COUNT(0) AS CNT FROM PRESS_RELEASE_DATA WHERE D_SEQ = "+d_seq+" AND D_TYPE != 'news'		\n");
	        System.out.println(sb.toString());
     		stmt = dbconn.createStatement();
     		rs = stmt.executeQuery(sb.toString());
     		if(rs.next()){
     			tw_count = rs.getString("CNT");
     		}
	        
     		sb = new StringBuffer();
	        sb.append("UPDATE PRESS_RELEASE_MNG															\n");
	        sb.append("SET d_tw_count = "+tw_count+", d_ana_yn = 'Y'     \n");	 
	        sb.append("WHERE d_seq= "+d_seq+"                                                              \n");
	        System.out.println(sb.toString());
     		stmt = dbconn.createStatement();
     		stmt.executeUpdate( sb.toString() );
     		
     		sb = new StringBuffer();
	        sb.append("UPDATE PRESS_RELEASE_MNG															\n");
	        sb.append("SET d_same_count = "+news_count+", d_ana_yn = 'Y'     \n");
	        sb.append("WHERE d_seq= "+d_seq+"                                                              \n");
	        System.out.println(sb.toString());
     		stmt = dbconn.createStatement();
     		stmt.executeUpdate( sb.toString() );
        
        }catch(Exception ex) {
         	Log.crond(ex.toString());
        } finally {
         	sb = null;
             if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
             if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
   }
    
    
    public static String getUnixTimeStamp(long timeStamp, String format) {
	    long unixTime = timeStamp * 1000;
	    Date date = new Date(unixTime);
	    
	    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
	    String retVal = dateFormat.format(date);
	    return retVal;
	}
}
