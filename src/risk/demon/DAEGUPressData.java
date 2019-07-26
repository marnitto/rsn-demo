package risk.demon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.util.ConfigUtil;
import risk.util.DateUtil;
import risk.util.Log;

public class DAEGUPressData{ 
	
	/**
     * MigrationData main 
     * @return
     */
	
	static String apiUrl = "http://api.realsn.com:2025/json.php";
	static String siteNum = "2300342"; 		/* 	2300111 : 네이버, 2300112 : 다음	*/
	static String name = "PressData";
	static String lastUpdateSeq = "";
	static String transferKey = null;
	
	// 예) http://api.realsn.com:2025/json.php?api_key=624bbead691dbc6d&max_seq=0&search_day=20170106&s_seq=2300111,2300112&list_count=100
	
    public static void main( String[] args )
    {
    	long diffSecond;
    	long runTime1;
    	long runTime2;
    	int processNum = 0;
    	    	
    	String lastTransNum = null;
    	String tempNum = null;
    	String endNum = null;
    	
    	ConfigUtil cu = new ConfigUtil();
    	ArrayList contentList = null;	
    	
    	Log.crond("DAEGUPressData START ...");    
    	
        try{
        	DateUtil du = new DateUtil();
        	du.addDay(-1);
        	String today = du.getDate().replaceAll("-","");

        	
//			수동으로 돌리려는 날짜를 입력한다.        	
//        	today = "20170729";
        	//System.out.println(today);
    		
        	dbconn1 = new DBconn();
        	dbconn1.getSubDirectConnection();
        	
        	transferKey = cu.getConfig("SECTIONDATA_PRESSDATA");	    	
        	lastTransNum = getLastTransNum( transferKey );
        	
        	Log.crond("TRANSFER NO :"+ lastTransNum);        	
        	
        	tempNum = lastTransNum;

    		contentList = new ArrayList();
    		contentList = getContents(today, tempNum, transferKey);
    		
    		if(contentList.size()>0){
    			//runTime1 = System.currentTimeMillis();        			       			
    			int count = insertPressData(contentList);
    			
    			updateLastTransNum(transferKey, lastUpdateSeq);
    			
    			//runTime2 = System.currentTimeMillis();
    			//diffSecond = runTime2 - runTime1;        			
    			//processNum ++;
    			
        	}
    		
        }catch(Exception ex) {
        	Log.crond(ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        }finally {
            if (dbconn1 != null) try { dbconn1.close(); } catch(SQLException ex) {}
        }
        
        Log.crond("DAEGUPressData END ...");
    }
    
    /**
     *  로컬 DB 컨넥션
     * @return
     */
    static DBconn dbconn1 = null;
    
    /**
     * 최대 번호  가져오기
     * @return
     * @author Lim Seung Chul
     */
    static int getNewNum()
    {
        int result = 0;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;       

		try{
            sb = new StringBuffer();
            sb.append(" SELECT IFNULL(MAX(T_SEQ),1) AS T_SEQ FROM TOP \n");

			stmt = dbconn1.createStatement();
			rs = stmt.executeQuery( sb.toString() );

			if(rs.next()){
				result = rs.getInt("T_SEQ");
			}

		} catch(SQLException ex) {
			Log.crond(ex.toString());
			System.exit(1);
        } catch(Exception ex) {
        	Log.crond(ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
        }
        return result;
    }
    
    /**
     * 최근 INDEXING한 번호 
     * @param transferNo
     * @author Lim Seung Chul
     */
    static String getLastTransNum( String transferNo )
    {
    	String result = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;

		try{
            sb = new StringBuffer();
            sb.append(" SELECT ST_LAST_SEQ FROM SYSTEM_TRANSFER WHERE ST_API_KEY='"+transferNo+"' \n");

			stmt = dbconn1.createStatement();
			rs = stmt.executeQuery( sb.toString() );

			if(rs.next()){
				result = rs.getString("ST_LAST_SEQ");
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

    static boolean updateLastTransNum(String transferNo, String lastTransNum)
    {
        boolean result = false;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;

		try{
            sb = new StringBuffer();
            sb.append(" UPDATE SYSTEM_TRANSFER SET ST_LAST_SEQ=" + lastTransNum + ", ST_UPDATE_STAMP = UNIX_TIMESTAMP() WHERE ST_API_KEY='" + transferNo + "' \n");

			stmt = dbconn1.createStatement();
			if( stmt.executeUpdate( sb.toString() ) > 0 ) result = true;

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
    
    static ArrayList getContents(String date, String startNum, String acessToken)
    {       
    	JSONObject json = null;
    	ArrayList result = null;
    	String returnData = ""; 
    	
        String param = "";
    	param += "api_key=" + acessToken;
    	param += "&max_seq=" + startNum;
    	param += "&search_day=" + date;
    	param += "&s_seq=" + siteNum;
    	param += "&list_count=500";
        
        try{
        	Log.crond("[SEND]" + apiUrl+ "?" +  param);
        	returnData = GetHtmlPost(apiUrl, param);
        	Log.crond("[RECEIVE]");
        	
        	json = new JSONObject(returnData);
        	JSONArray jAr = new JSONArray();
        	JSONObject object = null;
        	HashMap<String, String> map = null;
        	
        	object = (JSONObject) json.get("status");
        	
        	int list_count = 0;
        	
        	if( !object.isNull("list_count")){
        		list_count = object.getInt("list_count");
        	}

        	result = new ArrayList();
        	
        	if(list_count > 0){
        		if(!json.isNull("data")){
            		jAr = json.getJSONArray("data");
            		
            		for(int i=0; i<jAr.length(); i++){
            			object = jAr.getJSONObject(i);
            			map = new HashMap<String, String>();
            			
            			if(!object.isNull("SECTION.DATA.Seq")){
            				map.put("seq", object.getString("SECTION.DATA.Seq"));
            				map.put("d_seq", object.getString("SECTION.DATA.Seq"));
            			}
            			
            			if(!object.isNull("SECTION.DATA.Title")){ 
            				map.put("title", object.getString("SECTION.DATA.Title"));
            			}
            			
            			if(!object.isNull("SECTION.DATA.Link")){
            				
            				map.put("link", JsonToUrl(object.getString("SECTION.DATA.Link")));
            			}
            			
            			if(!object.isNull("SECTION.DATA.Content")){
            				map.put("content", object.getString("SECTION.DATA.Content"));
            			}
            			
            			
            			if(!object.isNull("SECTION.DATA.WriteTime")){
            				
            				map.put("date", object.getString("SECTION.DATA.WriteTime"));
            				
            			}else{
            				
            				if(!object.isNull("SECTION.DATA.CrawlStamp")){
                				map.put("date", stampToDateTime(object.getString("SECTION.DATA.CrawlStamp"), "yyyy-MM-dd HH:mm:ss"));
                			}	
            			}
            			
            			
            			if(!object.isNull("COMMON.SITE.Name")){
            				map.put("sitename", object.getString("COMMON.SITE.Name"));
            			}
            			
            			if(!object.isNull("SECTION.BOARD.Name")){
            				map.put("boardname", object.getString("SECTION.BOARD.Name"));
            			}
            			
            			if(!object.isNull("COMMON.SITE.Seq")){
            				map.put("siteseq", object.getString("COMMON.SITE.Seq"));
            			}
            			
            			if(!object.isNull("SECTION.BOARD.Seq")){
            				map.put("boardseq", object.getString("SECTION.BOARD.Seq"));
            			}
            			result.add(map);
            			
            		}
            	}else if(!json.isNull("error")){
            		Log.writeExpt(name, json.getJSONObject("error").getString("message"));
            	}
        		
        	}else{
        		Log.crond("조회 된 데이터가 없습니다.");
        	}
        	
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
        } 
        return result;
    }
    
    /**
     * 로컬 DB에 신규 게시물을 입력
     */
    static int insertPressData(ArrayList realTimeList)
    {
        PreparedStatement pstmt = null;
        PreparedStatement pstmt_data = null;
        StringBuffer sb = null;
        int count = 0;
        
        try{
        	
        	HashMap<String, String> map = null;
	        for(int i=0; i<realTimeList.size(); i++){
	        	map = (HashMap)realTimeList.get(i);
					try{
			            sb = new StringBuffer();            
			            sb.append("	INSERT INTO PRESS_RELEASE_MNG																		\n");    
			            sb.append("	(SEQ, D_SEQ, D_SITE, D_BOARD, D_DATE, D_TITLE, D_URL, D_CONTENT, D_KEYWORD, S_SEQ, SB_SEQ, D_TW_COUNT, D_SAME_COUNT, D_ANA_YN) 		\n");
			            sb.append("	VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)																		\n");
			           
			            pstmt = dbconn1.createPStatement(sb.toString());          
			            
		            	int index = 1;
		            	pstmt.clearParameters();
		            	
		            	pstmt.setString(index++, map.get("seq"));
		            	pstmt.setString(index++, map.get("d_seq"));
		            	pstmt.setString(index++, map.get("sitename"));
		            	pstmt.setString(index++, map.get("boardname"));
		            	pstmt.setString(index++, map.get("date"));
		            	pstmt.setString(index++, map.get("title"));
		            	pstmt.setString(index++, map.get("link"));
		            	pstmt.setString(index++, map.get("content"));
		            	pstmt.setString(index++, "");
		            	pstmt.setString(index++, map.get("siteseq"));
		            	pstmt.setString(index++, map.get("boardseq"));
		            	pstmt.setString(index++, "0");
		            	pstmt.setString(index++, "0");
		            	pstmt.setString(index++, "N");
		            	count += pstmt.executeUpdate();
		            	
		            	lastUpdateSeq = map.get("d_seq").toString();
			            
					} catch(SQLException ex) {
						System.out.println("Duplicate error ignore");
			        }
	        }
        }catch(Exception ex) {
        	updateLastTransNum(transferKey, lastUpdateSeq);
        	Log.crond(ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (pstmt_data != null) try { pstmt_data.close(); } catch(SQLException ex) {}
        }
        
		return count;
    }
    
    public static String GetHtmlPost(String sUrl, String param) {
		StringBuffer html = new StringBuffer();
		try {
			String line = null;
			URL aURL = new URL(sUrl);
			HttpURLConnection urlCon = (HttpURLConnection) aURL.openConnection();
			urlCon.setRequestMethod("POST");

			urlCon.setDoInput(true);
			urlCon.setDoOutput(true);
			OutputStream out = urlCon.getOutputStream();
			out.write(param.getBytes());
			out.flush();

			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(urlCon.getInputStream(),"utf-8"));
			// BufferedReader br = new BufferedReader(new
			// InputStreamReader(aURL.openStream()));
			while ((line = br.readLine()) != null) {
				html.append(line);
			}

			aURL = null;
			urlCon = null;
			br.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Log.writeExpt("CRONLOG", "MalformedURLException : " + e);
			// 프로세스 종료
			// System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			Log.writeExpt("CRONLOG", "IOException :" + e);
			// 프로세스 종료
			// System.exit(1);
		}
		return html.toString();
	}
    
    public static String stampToDateTime(String stamp, String format) {
    	long unixSeconds = Long.parseLong(stamp);
    	Date date = new Date(unixSeconds*1000L); 
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
    	//sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
    	String formattedDate = sdf.format(date);    	
    	return formattedDate;
    }
    
    public static String JsonToUrl(String url){
    	
    	String result = "http://spp.daegu.go.kr/main/news/news_report.jsp#view/";
    	
    	if(url.indexOf(".json") > 0){
    		
    		result = result+url.substring(url.indexOf("_")+1, url.indexOf(".json"));
    		
    	}else{
    		
    		result = url;
    	}
    	
    	return result;
    }
}
