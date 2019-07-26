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
import java.util.ArrayList;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.util.ConfigUtil;
import risk.util.DateUtil;
import risk.util.Log;

public class DAEGUTransSite {
	
	 /**
     *  로컬 DB 컨넥션
     * @return
     */
    static DBconn dbconn1 = null;
    
    /**
     * MigrationData main 
     * @return
     */
    public static void main( String[] args )
    {
    	long diffSecond ;
    	long runTime1;
    	long runTime2;
    	int processNum = 0;
    	
    	Log.crond("TransSite START ...");    	    	
        try{
        	
        	dbconn1 = new DBconn(); //로컬디비
        	
        	dbconn1.getSubDirectConnection();
        	runTime1 =  System.currentTimeMillis();   
        	
        	ConfigUtil cu = new ConfigUtil();
        	String siteKey = cu.getConfig("SITEDATA");
        	
        	if(siteKey == null){
        		Log.crond(" LOST TRANS KEY.... \n PLEASE CHECK SYSTEM_TRANS TABLE.");
        		System.exit(0);
        	}
        	
        	ArrayList<String[]> siteData = getSiteMaster(siteKey);
        	insertSite(siteData);
			//updateRelation();
			
        	runTime2 = System.currentTimeMillis();
        	diffSecond = runTime2 - runTime1;        	
        	
        	Log.crond("processNum:"+processNum);
        	Log.crond("time:"+diffSecond/1000L/60);
     
        	
        }catch(Exception ex) {
        	Log.crond(ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        }finally {
            if (dbconn1 != null) try { dbconn1.close(); } catch(SQLException ex) {}
        }
        Log.crond("TransSite END ...");
    }
    
    static void insertSite(ArrayList<String[]> site)
    {       
    	PreparedStatement pstmt = null;
        Statement stmt = null;
        StringBuffer sb = null;
        
        try{
			// SITE비운다.
			sb = new StringBuffer();
			sb.append("TRUNCATE TABLE SITE \n");
			stmt = dbconn1.createStatement();
			stmt.executeUpdate(sb.toString());

			// 전부입력.
			sb = new StringBuffer();
			sb.append(" INSERT INTO SITE (S_SEQ, S_NAME, S_URL, S_ACTIVE, S_CHANNEL, S_LANG, S_COUNTY, S_CODE1, S_TAG) 	\n");
			sb.append(" VALUES (?,?,?,?,?,   ?,?,?,?) 		\n");

			// System.out.println( sb.toString() );
			pstmt = dbconn1.createPStatement(sb.toString());
			String[] data = null;
			for (int i = 0; i < site.size(); i++) {
				data = site.get(i);

				pstmt.clearParameters();
				for (int j=0 ; j<data.length ; j++) {
					pstmt.setString(j+1, data[j]);	
				}
				
				pstmt.execute();
			}                
                    
        } catch(SQLException ex) {
        	Log.crond(ex.toString());               
        } catch(Exception ex) {
        	Log.crond(ex.toString());
        } finally {
        	sb = null;
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }     
     }
    
    static void updateRelation()
    {       
            Statement stmt = null;
            StringBuffer sb = null;
            
            try{
               // SITE에 없는 사이트는 SG_S_RELATION에서 삭제한다.
               sb = new StringBuffer();
               sb.append(" DELETE FROM SG_S_RELATION WHERE S_SEQ NOT IN (SELECT S_SEQ FROM SITE) AND S_SEQ <> 10464				  \n");
               stmt = dbconn1.createStatement();                           
               stmt.executeUpdate(sb.toString());
               
               // SG_S_RELATION의 S_NAME을 SITE에서 가져와 업데이트한다.
               sb = new StringBuffer();
               sb.append(" UPDATE SITE A, SG_S_RELATION B SET B.S_NAME = A.S_NAME WHERE A.S_SEQ= B.S_SEQ AND A.S_NAME <> B.S_NAME    \n");
               stmt = dbconn1.createStatement();                           
               stmt.executeUpdate(sb.toString());                     
                        
            } catch(SQLException ex) {
            	Log.crond(ex.toString());               
            } catch(Exception ex) {
            	Log.crond(ex.toString());
            } finally {
            	sb = null;
                if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
            }     
     }
    
    static ArrayList<String[]> getSiteMaster(String acessToken){
    	
    	JSONObject json = null;
    	String[][] result = null;
    	String returnData = ""; 
    	
    	ArrayList<String[]> resultArr = new ArrayList<String[]>();
    	
    	DateUtil du = new DateUtil();
    	String today = du.getCurrentDate("yyyyMMdd");
    	
    	long startNum = 0;
    	
    	
    	String apiUrl = "http://api.realsn.com:2025/json.php";
    	
        String param = "";
    	
    	String[] parsingKey = {
    			"COMMON.SITE.Seq",
    			"COMMON.SITE.Name",
    			"COMMON.SITE.Link",
    			"COMMON.SITE.Active",
    			"COMMON.SITE.Channel",
    			"COMMON.SITE.Lang3",
    			"COMMON.SITE.Country3",
    			//"COMMON.SITE.StateCode", 필요없음.
    			"COMMON.SITE.Category",	
    			"COMMON.SITE.Tag"    			
    	};
        
        try{
        	while(true){
        		param = "";
            	param += "api_key=" + acessToken;
            	param += "&max_seq=" + startNum;
            	param += "&search_day=" + today;
            	param += "&list_count=500";
            	
            	Log.crond("[SEND]" + apiUrl+ "?" +  param);
            	returnData = GetHtmlPost(apiUrl, param);
            	Log.crond("[RECEIVE]");
            	
            	json = new JSONObject(returnData);
            	JSONArray jAr = new JSONArray();
            	JSONObject row_json = null;
            	
            	if(!json.isNull("data")){
            		jAr = json.getJSONArray("data");
            		
            		if(jAr == null || jAr.length() <= 0){
            			break;
            		}
            		
            		result = new String[jAr.length()][parsingKey.length];
            		
            		for(int i=0; i< jAr.length(); i++){
            			row_json = jAr.getJSONObject(i);
            			
            			for (int j=0 ; j<parsingKey.length ; j++) {
    						if(!row_json.isNull(parsingKey[j])){
    							result[i][j] = row_json.getString(parsingKey[j]);
    						}else{
    							result[i][j] = "";	
    						}
    					}
            		}
            	}else if(!json.isNull("error")){
            		System.out.println(json.getJSONObject("error").getString("message"));
            		System.exit(1);
            	}else{
            		result = new String[0][16];
            	}
            	
            	for (int i=0 ; i<result.length; i++) {
            		resultArr.add(result[i]);
            		
            		startNum = Long.parseLong(result[i][0]);
				}
        	}
        	
        } catch(Exception ex) {
        	ex.printStackTrace();
			System.exit(1);
        } 
        
        return resultArr;
    }
    
    static String getTransKey(String channel){
    	
    	String result = null;
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	stmt = dbconn1.createStatement();
        	
        	sb = new StringBuffer();
            sb.append(" SELECT ST_API_KEY FROM SYSTEM_TRANSFER WHERE ST_CHANNEL = '"+channel+"' 	\n");
            
            System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next()){
				result = rs.getString("ST_API_KEY");
			}
            
        } catch(Exception ex) {
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
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
}
