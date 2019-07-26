package risk.demon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.regex.*;

import risk.DBconn.*;
import risk.analysis.*;
import risk.analysis.vo.*;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.util.*;

public class DAEGUInstagramMigrationData{
	
	/**
     *  로컬 DB 컨넥션
     * @return
     */
    static DBconn dbconn1 = null;
    static String name = "DAEGUInstagramMigrationData";
    static String addName = "";
    
    static String apiUrl = "http://api.realsn.com:2025/json.php";
    
    static int instagram_list_count = 500;
    static String siteNum = "5026142";
    
    //자동분석 관련
  	static AutoAnalysis auto = new AutoAnalysis();
  	static HashMap<String, String> AutoIssueCode = null;
  	
    public static void main( String[] args ){
    	Log.crond(name, name + " START ...");
    	
    	try{
    		ConfigUtil cu = new ConfigUtil();
    		
    		dbconn1 = new DBconn(); //로컬디비
        	dbconn1.getSubDirectConnection();
        	
        	//키워드
        	String[][] keyword = getKeyword();
        	
        	//이슈코드
        	AutoIssueCode = getAutoIssueCode();
        	
        	//최근값 가져오기~
        	String dataKey = cu.getConfig("INSTAGRAMDATA");
        	String lastNum = getLastTransNum(dataKey);
        	String instagram_date = lastUpdateTime(dataKey);
        	
        	String regexpStr = "[',|@#$%\\^&*\\s\\[\\](){}‘’<>\"+-./:=?_`´~§±·]";
        	String[][] DBtable = null;
        	String hashTags = "";
        	String hashCodes = "";
        	int id_seq = 0;
        	
        	String type = "";
        	String d_seq = "";  
        	String d_stamp = "";  
        	String s_seq = "";  
        	String d_site = "";  
        	String d_board = "";  
        	String d_datetime = "";  
        	String d_title = "";  
        	String d_url = "";  
        	String sb_seq = "";
        	String d_content = "";
        	String if_seq = "";
        	String l_alpha = "";
        	String d_image = "";
        	String sg_seq = "";
        	
        	String media_info = "";
        	String dd_followercount = "";
        	String dd_imglink = "";
        	
        	String d_title_lower = "";
        	String d_content_lower = "";
        	String text = "";
        	
        	int result = 0;
        	
        	//쿼리 루프
        	while((DBtable = getData(instagram_date, lastNum, dataKey)).length > 0){
        		
        		//데이터 루프
        		for(int i = 0; i < DBtable.length; i++){

        			//공통
	            	type = DBtable[i][0];  
	            	d_seq = DBtable[i][1];  
	            	d_stamp = DBtable[i][2];  
	            	s_seq = DBtable[i][3];  
	            	d_site = DBtable[i][4];  
	            	d_board = DBtable[i][5];  
	            	d_datetime = DBtable[i][6];  
	            	d_title = DBtable[i][7];
	            	d_title_lower = DBtable[i][7].toLowerCase().replaceAll(regexpStr, " ").replaceAll("\\s{1,}"," ");
	            	d_url = DBtable[i][8];  
	            	sb_seq = DBtable[i][9];
	            	d_content = DBtable[i][10];
	            	d_content_lower = DBtable[i][10].toLowerCase().replaceAll(regexpStr, " ").replaceAll("\\s{1,}"," ");
	            	if_seq = DBtable[i][11];
	            	l_alpha = DBtable[i][12];
	            	d_image = DBtable[i][13];
	            	media_info = DBtable[i][14];
	            	dd_followercount = DBtable[i][15];
	            	dd_imglink = DBtable[i][16];
	            	sg_seq = "48";    	
        			
        			//키워드 체크
        			text = d_title_lower + " " + d_title_lower;  
        			result = 0;
        			for(int j = 0; j < keyword.length; j++){

        				if(searchKeyword(text, keyword[j][4], Integer.parseInt(keyword[j][3]), 30, 3)){
        					result = 1;
        					Log.writeExpt(name,"데이터 걸림[" + DBtable[i][2] + "]" + keyword[j][4]);
        					break;
        				}else{
        					result = 0;
        				}
        			}
        			if(result == 0){
        				continue;
        			}
        		
        			//해쉬태그 추출
        			hashTags = getHashTags(d_content);
        			
        			if(!hashTags.equals("")){
        				//해쉬태그 압축
            			hashTags = getGroupByStream(hashTags.replaceAll("'", "").replaceAll("\\\\", ""),",","S");
            			
            			// 2016.12.14
            			// 예외 해쉬코드 걸러내기
            			hashTags = getExceptionHashTagFilter(hashTags);
            			
            			if("".equals(hashTags)) continue;
            			
            			// 해쉬태그 사전 입력
            			Set<String> rSet = insertHashTag(hashTags);
            			
            			Iterator<String> it = rSet.iterator();
            			hashCodes = "";
            			StringBuilder sBuilder = new StringBuilder();
            			while(it.hasNext()){
            				if(sBuilder.length() > 0){
            					sBuilder.append(",");
            					sBuilder.append(it.next());
            				} else {
            					sBuilder.append(it.next());
            				}
            			}
            			hashCodes = sBuilder.toString();
            			
            			if(!"".equals(hashCodes)){
            				//이슈관련 데이터 저장
            				id_seq = PrcIssueData(0, "", d_site, d_board, if_seq, d_datetime, d_title_lower, d_url, s_seq, d_image
            						, l_alpha, sg_seq, d_content, media_info, d_seq, "", "", "", "", ""
            						, "", "", "", "", "", "", "0", ""
            						, "", "0", "0", dd_followercount, dd_imglink);
            				insertIssueHashTag(id_seq, hashCodes);
            			}
            			// 해쉬코드 조회
            			/*hashCodes = getIssueHashTag(hashTags);
            			
            			if(hashCodes.equals("")){
            				insertIssueHashTagWait(hashTags);
            			}else{
            				//이슈관련 데이터 저장
            				id_seq = PrcIssueData(0, "", d_site, d_board, if_seq, d_datetime, d_title_lower, d_url, s_seq, d_image
            						, l_alpha, sg_seq, d_content, media_info, d_seq, "", "", "", "", ""
            						, "", "", "", "", "", "", "0", ""
            						, "", "0", "0", dd_followercount, dd_imglink);

            				insertIssueHashTag(id_seq, hashCodes);
            			}*/
        			}
        			
        			Log.crond(name, "("+ (i+1) +"/"+ DBtable.length +")");
        		}
        		
        		//마지막값 저장
        		updateLastTransNum(dataKey, DBtable[DBtable.length-1][1]);
        		lastNum = DBtable[DBtable.length-1][1];
        		instagram_date = lastUpdateTime(dataKey);
        		Log.writeExpt(name,"마지막값 저장[" + lastNum + "]");
        	}
    	}catch(Exception ex) {
    		Log.writeExpt(name,ex.getMessage());
        	ex.printStackTrace();
        	System.exit(1);
        }finally {
            if (dbconn1 != null) try { dbconn1.close(); } catch(SQLException ex) {}
        }
    	Log.crond(name, name + " END ...");
    }
    
    static String getHashTags(String str) {
		String result = "";
		String regexpStr = "(#[^ #\"]+)";
		Pattern p = Pattern.compile(regexpStr); 
		Matcher m = p.matcher(str);
		while (m.find()) {
			if(result.equals("")){
				result = m.group().trim();
			}else{
				result += "," + m.group().trim();
			}
		}
		return result;
	}
    
    //데이터 압축
    //option : S일경우 따옴표 붙이고, I일경우 안붙힌다. 
    static String getGroupByStream(String keyCodes, String strChar, String option){
    	
    	String result = "";
    	HashMap<String, Integer> hKey = getGroupBy(keyCodes,strChar);
    	Set<String> keys = hKey.keySet();
		Iterator<String> it = keys.iterator();
    	
		result = "";
		while(it.hasNext()){
			if(result.equals("")){
				if(option.equals("S")){
					result = "'" + it.next() + "'";	
				}else{
					result = it.next();
				}
			}else{
				if(option.equals("S")){
					result += strChar + "'" + it.next() + "'";
				}else{
					result += strChar + it.next();
				}
			}
		}
    	return result;
    }
    
    static HashMap<String, Integer> getGroupBy(String keyCodes, String strChar){
    	HashMap<String, Integer> result= new HashMap<String, Integer>();
    	String[] arKeyCode = keyCodes.split(strChar);
    	
    	for(int i = 0; i < arKeyCode.length; i++){
    		if(result.containsKey(arKeyCode[i])){
    			result.put(arKeyCode[i], result.get(arKeyCode[i]) + 1);
    		}else{
    			result.put(arKeyCode[i], 1);
    		}
    	}
    	return result;
    }

    static String getIssueHashTag(String hashTags){
    	
    	String result = "";
    	Statement stmt = null;
    	StringBuffer sb = null;
    	ResultSet rs = null;
    	
    	try{
    		stmt = dbconn1.createStatement();
    		sb = new StringBuffer();
			sb.append("SELECT HC_CODE FROM HASHTAG_CODE WHERE BINARY HC_NAME IN ("+hashTags+")\n");
			//System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
    		
			while(rs.next()){
				if(result.equals("")){
					result = rs.getString("HC_CODE");
				}else{
					result += "," + rs.getString("HC_CODE");
				}
			}	
    	}catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
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
     * 2016-12-18
     * Jeong Cheon Woong
     * 입력된 해시태그 중에서 HASHTAG_CODE테이블에 존재하지 않는 태그들을 입력해주고 
     * 입력된 해시태그들에 대한 HC_CODE를 구해온다.
     * 
     * @param hashTags 필터링 된 해쉬태그
     * @return Set HC_CODE
     *
     */
    static Set<String> insertHashTag(String hashTags){
    	
    	Set<String> nameSet = new HashSet<String>();
    	Set<String> insertSet = new HashSet<String>();
    	Set<String> codeSet = new HashSet<String>();
    	DateUtil du = new DateUtil();
    	
    	PreparedStatement pstmt = null;
    	Statement stmt = null;
    	StringBuffer sb = null;
    	ResultSet rs = null;
    	
    	try{
	    	// 오리지널 값 구하기
			String[] originTags = hashTags.replaceAll("'", "").split(",");
			
			stmt = dbconn1.createStatement();
			sb = new StringBuffer();
			sb.append("SELECT HC_CODE, HC_NAME FROM HASHTAG_CODE WHERE BINARY HC_NAME IN ("+hashTags+")	\n");
			rs = stmt.executeQuery(sb.toString());
			
			// 현재 사전에 있는 해쉬코드 코드값과 태그명을 구해온다.
			while(rs.next()){
				codeSet.add(rs.getString("HC_CODE"));
				nameSet.add(rs.getString("HC_NAME")); 
			}
			rs = null;
    		sb = null;
    		
			// 들어온 데이터 중에 사전에 없는 태그명 만을 추출한다.
    		for(String str : originTags){
    			
    			if(!nameSet.contains(str)){
    				insertSet.add(str);
    			}
    		}
			
    		// 해쉬태그 사전에 넣기
    		sb = new StringBuffer();
			sb.append(" INSERT INTO HASHTAG_CODE (HC_NAME, HC_USEYN, HC_DATE) VALUES (?, ?, ?)	\n");
    		pstmt = dbconn1.createPStatement(sb.toString());
    		int idx = 0;
    		String cur_date = du.getCurrentDate("yyyy-MM-dd").replaceAll("-", "");
    		
    		Iterator<String> it = insertSet.iterator();
    		while(it.hasNext()){
    			pstmt.setString(1, it.next());
    			pstmt.setString(2, "Y");
    			pstmt.setString(3, cur_date);
    			pstmt.addBatch();
    			pstmt.clearParameters();
    			
    			if( (idx % 1000) == 0){ // 1000건 마다 배치 실행
    				pstmt.executeBatch();
    				pstmt.clearBatch();
    			}
    			idx++;
    		}
    		pstmt.executeBatch();
    		
    		// 인서트한 HC_CODE 값 추출
    		rs = pstmt.getGeneratedKeys();
    		
    		while(rs.next()){
    			codeSet.add(rs.getString(1));
    		}
    		
    	}catch(SQLException ex){
    		Log.writeExpt(name,pstmt.toString());
    		Log.writeExpt(name,ex.toString());
    		ex.printStackTrace();
    		System.exit(1);
    	}catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
        }
    	return codeSet;
    }
 
    static String getExceptionHashTagFilter(String hashTags){
    	
    	Set<String> hashSet = new HashSet<String>();
    	StringBuilder sBuilder = new StringBuilder();
    	Statement stmt = null;
    	StringBuffer sb = null;
    	ResultSet rs = null;
    	
    	try{
    		// 오리지널 값 구하기
    		String[] originTags = hashTags.replaceAll("'", "").split(",");
    		
    		stmt = dbconn1.createStatement();
    		sb = new StringBuffer();
			sb.append("SELECT HC_NAME FROM HASHTAG_EXCEPTION_CODE WHERE BINARY HC_NAME IN ("+hashTags+")\n");
			//System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
    		
			// 예외 해쉬태그 값 구하기
			while(rs.next()){
				hashSet.add(rs.getString("HC_NAME"));
			}
			
			// 예외 해쉬태그 걸러내기(+빈 값도 제거)
			for(String str : originTags){
				str = str.trim();
				
				if(!(str.length() > 31)){	// 30글자 이상 제거
					if(!hashSet.contains(str) && !"".equals(str)){
						
						if(sBuilder.length() > 0){
							sBuilder.append(",");
							sBuilder.append("'"+str+"'");
						} else {
							sBuilder.append("'"+str+"'");
						}
					}	
				}
			}
    	
    	}catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
        }
    	return sBuilder.toString();
    }
    
    static void insertIssueHashTag(int id_seq, String hashCodes){
    
    	PreparedStatement pstmt = null;
    	StringBuffer sb = null;
    	ResultSet rs = null;
    	
    	try{

    		sb = new StringBuffer();
    		sb.append("INSERT INTO ISSUE_INSTA_HASHTAG (ID_SEQ, HC_CODE) VALUES ("+id_seq+", ?);\n");
    		pstmt = dbconn1.createPStatement(sb.toString());
    		
    		String[] arHashcode = hashCodes.split(",");
    		
			for(int i =0; i < arHashcode.length; i++){
				pstmt.clearParameters();
    			pstmt.setString(1, arHashcode[i]);
    			pstmt.executeUpdate();
			}
    	}catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
        }
    }
    
    static void insertIssueHashTagWait(String hashNames){
        
    	PreparedStatement pstmt = null;
    	StringBuffer sb = null;
    	ResultSet rs = null;
    	
    	try{
    		sb = new StringBuffer();
    		sb.append("INSERT INTO ISSUE_INSTA_HASHTAG_WAIT (HW_NAME) VALUES (?);\n");
    		pstmt = dbconn1.createPStatement(sb.toString());
    		
    		String[] arHashTag = hashNames.split(",");
    		
			for(int i =0; i < arHashTag.length; i++){
				if(arHashTag[i].length() > 120){
    				arHashTag[i] = arHashTag[i].substring(0, 120);
    			}
				pstmt.clearParameters();
    			pstmt.setString(1, arHashTag[i].replaceAll("'", ""));
    			pstmt.executeUpdate();
			}
    	
    	}catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
        }
    }
    
	static String[][] getData(String date, String startNum, String acessToken){
		
	    	JSONObject json = null;
	    	String[][] result = null;
	    	String returnData = ""; 
	    	
	        String param = "";
	    	param += "api_key=" + acessToken;
	    	param += "&max_seq=" + startNum;
	    	param += "&search_day=" + date;
	    	param += "&s_seq=" + siteNum;
	    	param += "&list_count="+instagram_list_count;
	        
	        try{
	        	
	        	Log.crond("[SEND]" + apiUrl+ "?" +  param);
	        	returnData = GetHtmlPost(apiUrl, param);
	        	Log.crond("[RECEIVE]");
	        	
	        	json = new JSONObject(returnData);
	        	JSONArray jAr = new JSONArray();
	        	JSONObject row_json = null;
	        	      	
	        	if(!json.isNull("data")){
	        		jAr = json.getJSONArray("data");
	        		
	        		result = new String[jAr.length()][17];
	        		
	        		int idx = jAr.length();
	        		for(int i=0; i< jAr.length(); i++){
	        			row_json = jAr.getJSONObject(i);
	        			
	        			idx--;
	        			
	        			for(int j = 0; j < 17; j++){
	        				result[idx][j] = "";
	        			}
	   		
	        			result[idx][0] = "DATA";
	        			
	        			if(!row_json.isNull("MAIN.DATA.Seq")){ //게시글번호
	        				result[idx][1] = row_json.getString("MAIN.DATA.Seq");
	        			}
	        			if(!row_json.isNull("MAIN.DATA.CrawlStamp")){ //수집일시
	        				result[idx][2] = row_json.getString("MAIN.DATA.CrawlStamp");
	        			}
	        			if(!row_json.isNull("COMMON.SITE.Seq")){ //사이트번호
	        				result[idx][3] = row_json.getString("COMMON.SITE.Seq");
	        			}
	        			if(!row_json.isNull("COMMON.SITE.Name")){ //사이트명
	        				result[idx][4] = row_json.getString("COMMON.SITE.Name");
	        			}
	        			if(!row_json.isNull("MAIN.BOARD.Name")){	 //보드명
	        				result[idx][5] = row_json.getString("MAIN.BOARD.Name");
	        			}
	        			if(!row_json.isNull("MAIN.DATA.CrawlStamp")){ //수집일시
	        				result[idx][6] = stampToDateTime(row_json.getString("MAIN.DATA.CrawlStamp"), "yyyy-MM-dd HH:mm:ss");
	        			}
	        			if(!row_json.isNull("MAIN.DATA.Title")){	 //게시글제목
	        				result[idx][7] = row_json.getString("MAIN.DATA.Title");
	        			}
	        			if(!row_json.isNull("MAIN.DATA.Link")){	//게시글링크
	        				result[idx][8] = row_json.getString("MAIN.DATA.Link");
	        			}
	        			if(!row_json.isNull("MAIN.BOARD.Seq")){ //보드번호
	        				result[idx][9] = row_json.getString("MAIN.BOARD.Seq");
	        			}
	        			if(!row_json.isNull("MAIN.DATA.Content")){ //게시글본문
	        				result[idx][10] = row_json.getString("MAIN.DATA.Content");
	        			}
	        			if(!row_json.isNull("MAIN.DATA.InfoType")){ //정보유형 (1:기사 2:게시)
	        				result[idx][11] = row_json.getString("MAIN.DATA.InfoType");
	        			}
	        			if(!row_json.isNull("MAIN.DATA.Lang3")){ //언어코드
	        				result[idx][12] = row_json.getString("MAIN.DATA.Lang3");
	        			}
	        			if(!row_json.isNull("MAIN.DATA.ImgIs")){ //이미지포함여부
	        				result[idx][13] = row_json.getString("MAIN.DATA.ImgIs");
	        			}
	        			if(!row_json.isNull("MAIN.DATA.WriterName")){ //NICK
	        				result[idx][14] = row_json.getString("MAIN.DATA.WriterName");
	        			}
	        			if(!row_json.isNull("MAIN.DATA.WriterFollowerCount")){ //FOLLOWERCOUNT
	        				result[idx][15] = row_json.getString("MAIN.DATA.WriterFollowerCount");
	        			}
	        			if(!row_json.isNull("MAIN.DATA.ImgLink")){ //IMGLINK
	        				result[idx][16] = row_json.getString("MAIN.DATA.ImgLink");
	        			}
	        		}
	        	}else if(!json.isNull("error")){
	        		Log.writeExpt(name,json.getJSONObject("error").getString("message"));
	        		System.exit(1);
	        	}else{
	        		result = new String[0][16];
	        	}
	        	
	        } catch(Exception ex) {
	        	Log.writeExpt(name,ex.toString());			
	        	ex.printStackTrace();
				System.exit(1);
	        } 
	        
	        return result;
	    }
    
    static String[][] getKeyword(){
    	
    	String[][] result = null;
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	stmt = dbconn1.createStatement();
        	sb = new StringBuffer();
        	sb.append("SELECT K_XP			\n");
        	sb.append("     , K_YP			\n");
        	sb.append("     , K_ZP			\n");
        	sb.append("     , K_OP			\n");
        	sb.append("     , K_VALUE		\n");
        	sb.append("  FROM KEYWORD K		\n");
        	sb.append(" WHERE K_TYPE = 2	\n");
        	sb.append("   AND K_XP = 14		\n");
        	
        	
        	System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
        	
			rs.last();
			result = new String[rs.getRow()][7];  
			rs.beforeFirst();
			
			int row_idx = 0;
			int column_idx = 0;
			
			while(rs.next()){
				column_idx = 0;				
				result[row_idx][column_idx++] =  rs.getString("K_XP");
				result[row_idx][column_idx++] =  rs.getString("K_YP");
				result[row_idx][column_idx++] =  rs.getString("K_ZP");
				result[row_idx][column_idx++] =  rs.getString("K_OP");
				result[row_idx][column_idx++] =  rs.getString("K_VALUE").toLowerCase().trim();
				
				row_idx++;
			}
			
            
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    //최근 INDEXING한 번호 
    static String getLastTransNum( String transferNo ){
    	
    	String result = "0";
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	stmt = dbconn1.createStatement();
        	
        	sb = new StringBuffer();
        	sb.append(" SELECT ST_LAST_SEQ AS LASTNO FROM SYSTEM_TRANSFER WHERE ST_API_KEY = '"+transferNo+"' \n");
        	
            System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){		
				result = rs.getString("LASTNO");
			}
			
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    /**
     * 옵션별 키워드 처리
     * @param html
     * @param word
     * @param op
     * @return
     */
    static boolean searchKeyword(String html, String word, int op){
    	return searchKeyword(html, word, op, 0, 3);
    }
	static boolean searchKeyword(String html, String word, int op, int near_len, int near_option){
		boolean result = false;
		
		String[] key = word.split(" ");
		String sKey = "";
		//일반
		if(op==1){
			for(int i=0; i<key.length; i++)
		     {
		     	sKey = ""+key[i];
		     	int check = html.indexOf(sKey);
		     	if ( check < 0 )
		         {
		     		return false;
		         }
		     }
		     return true;
		//인접	
		}else if(op==2){
			return nearSearch(html, key, near_len, near_option);
		//구문
		}else if(op==3){
			if(html.indexOf(word)>-1){
				return true;
			}else{
				return false;
			}
		//고유명사	
		}else if(op==4){
			if(KorCheck(word)){
				sKey = " "+word;
			} else{
				sKey = " "+word+" ";
			}
	    
	     	int check = html.indexOf(sKey);
	     	if ( check < 0 )
	         {
	     		return false;
	         }
	     	
		     return true;
		//고유명사2	
		}else if(op==5){
			return englishPropernoun(html, key);
			
		}
		
		return result;
	}
	
	/**
	 * <p> key[0] 이 노출된 지점에서 앞뒤로 iNearByte(byte) 이내에 key[n]이 모두 존재 하는지 검사한다.</p>
	 * 
	 * @author Ryu Seung Wan
	 * option = 1:앞쪽으로만 검색, 2:뒤쪽으로만 검색, 3:앞뒤로 검색  
	 * 
	 */
    
    static boolean nearSearch(String strHtml, String[] key, int iNearByte, int option){  	
   	    	    	    	
    	int tempIndex1 = 0;
    	int lastIndex = 0;
    	int nextIndex = 1;
    	boolean bFindKey = false;
    	String[] tempKey = null;
    	String searchHtml = "";
    	String frontHtml = "";
    	String backHtml = "";
    	
    	//key = searchGumunKey(key);
    	
    	//대소문자 처리
    	/*for(int i=0;i<key.length;i++){
			key[i] = key[i].toLowerCase();			
		}*/
    	
    	tempIndex1 = nextIndexOf(strHtml, key[0], nextIndex);
		lastIndex = lastIndexOf(strHtml, key[0]);
		
		//복수 키워드
		if(key.length>1)
		{
			if(tempIndex1 != -1){
				
				tempKey = new String[key.length-1];
				
				for(int j=1;j<key.length;j++){					
					tempKey[j-1] = key[j];
				}
				
				findKeyLoop:while (tempIndex1 <= lastIndex){
					tempIndex1 = nextIndexOf(strHtml, key[0], nextIndex);
					++nextIndex;
					if(tempIndex1 != -1){
						
						// 키의 위치가 첫글짜 이면 (tempIndex1 == 0) 앞쪽은 자르지 않는다.
	
						// 키의 위치로 부터 앞쪽으로 iNearByte 만큼 자른다.
						
						if(option == 1 || option == 3){
							frontHtml = (tempIndex1 - iNearByte) > 0 ? 
									strHtml.substring(tempIndex1 - iNearByte, tempIndex1) : 
									strHtml.substring(0, tempIndex1);
						}else{
							frontHtml = "";
						}
						 
						if(option == 2 || option == 3){
							// 키의 위치로 부터 뒷쪽으로 (키.length + iNearByte) 만큼 자른다.
							backHtml = strHtml.length() - (key[0].length() + iNearByte + 1) > tempIndex1 ? 
									strHtml.substring(tempIndex1, tempIndex1 + key[0].length() + iNearByte) : 
									strHtml.substring(tempIndex1, strHtml.length());
						}else{
							backHtml = "";
						}
						
						
						searchHtml = frontHtml + backHtml;
											
						bFindKey = indexOfAll(searchHtml, tempKey);					
	    				
	    				if(bFindKey) break findKeyLoop;
	    				
					}else{
						break findKeyLoop;
					}				
				}
			}
		//단일 키워드
		}else{	
			key[0] = " "+key[0];					 
			
			int check = strHtml.indexOf(key[0]);
			if ( check >=0 )
			{
				//System.out.println("key[0]:"+key[0]);
				bFindKey  =  true;
			}		    		
		}
    	return bFindKey;
    }
    
    
    
    
    static int nextIndexOf(String str, String searchStr, int nextIndex) {
        if (str == null || searchStr == null || nextIndex <= 0) {
            return -1;
        }
        if (searchStr.length() == 0) {
            return 0;
        }
        int found = 0;
        int index = -1;
        do {
            index = str.indexOf(searchStr, index + 1);
            if (index < 0) {
                return index;
            }
            found++;
        } while (found < nextIndex);
        return index;
    }
    
    static int lastIndexOf(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }
        return str.lastIndexOf(searchStr);
    }
    
    static boolean indexOfAll(String str, String[] searchStrs) {
        if ((str == null) || (searchStrs == null)) {
            return false;
        }
        int sz = searchStrs.length;
        int tmp = 0;
        boolean btmp = true;

        for (int i = 0; i < sz; i++) {
            String search = searchStrs[i];
            if (search == null) {
            	btmp = false;
                continue;
            }
            tmp = str.indexOf(search);
            if (tmp == -1) {
                btmp = false;
                continue;
            }
        }
        return btmp;
    }
    
    /**
     * 키워드의 키워드 앞에 공백,특수문자가 와야함.
     * 키워드의 키워드 뒤에 공백,특수문자, 한글이 와야함.
     * @param html
     * @param key
     * @return
     */
    static boolean englishPropernoun(String html, String[] key ){
    	boolean result = true;
    	
    	boolean s_char = false;
    	boolean e_char = false;
    	
    	int keyIdx  = 0;
    	String alphabet = "";
    	//String alphabet = "[a-zA-Z0-9]"; 숫자포함
    	for(int i = 0; i < key.length; i++){
    		
    		
    		//키워드가 있는지 검색
    		keyIdx  = html.indexOf(key[i]);
    		if(keyIdx<0){
    			return false;
    		}
    		
    		//앞글자 확인
    		s_char = false;
    		if(keyIdx == 0){
    			s_char = true;
    		}else{
    			//공백통과
    			if(html.subSequence(keyIdx-1, keyIdx).equals(" ")){
    				s_char = true;
    			}
    		}
    		
    		//뒷글자 확인
    		e_char = false;
    		if((keyIdx+key[i].length()+1)> html.length()){
    			e_char = true;
    		}else{
    			//공백통과
    			if(html.subSequence(keyIdx+key[i].length(), (keyIdx+key[i].length()+1)).equals(" ")){
    				e_char = true;
    			}else{
    				//한글통과
    				int c = html.subSequence(keyIdx+key[i].length(), (keyIdx+key[i].length()+1)).charAt(0);
    				if(c > 0xac00 && c < 0xd7a3){
    					e_char = true;
    				}
    			}
    		}
    		
    		if(!(s_char && e_char)){
    			return false;
    		}
    		
    	}
    	
    	return result;
    }
    
    //psValue 한글이 한글자라도 있으면 TRUE 반환
   	static boolean KorCheck(String psValue){ 
   		for(int i=0; i<psValue.length(); i++){
   		    int c=psValue.charAt(i);		
   		    if(c > 0xac00 && c < 0xd7a3){
   		    	return true;
   		    }
   		}
   		return false;
   	}
   	
   	static int PrcIssueData(int newNum
				            , String typeCodes
				            , String d_site
				            , String d_board
				            , String if_seq
				            , String d_datetime
				            , String d_title
				            , String d_url
				            , String s_seq
				            , String d_image
				            , String l_alpha
				            , String sg_seq
				            , String d_content
				            , String media_info
				            , String d_seq
				            , String riskKey
				            , String tweet_id
				            , String user_id
				            , String is_rt
				            , String followers
				            , String following
				            , String tweet
				            , String source
				            , String tname
				            , String profile_image
				            , String Retweet_ID
				            , String dd_visitcount
				            , String dd_sitelink
				            , String dd_siteName
				            , String dd_membercount
				            , String dd_doccount
				            , String dd_followercount
				            , String dd_imglink
   			){
   		
   		int id_seq = insertIssueData(newNum, d_site, d_board, if_seq, d_datetime, d_title, d_url, "0", newNum, s_seq, d_image, l_alpha, sg_seq, d_content, media_info, d_seq, riskKey);
    	
		//자동분석 모듈 Start		            			
		String p_typecode = "";
		boolean sentiChk = false;
		ArrayList<ResultForm> arResultForm = null;
		int option = 0;
		ResultForm rf = null;
		HashMap<String, Integer> arRelationKey = null;
		
		//인스타그램
    	String hashTags = "";
    	
		//typeCodes = IdxTypeCodes(idxInfo);
		
		SuccessForm sf = auto.AutoAnalysis(d_title + " " + d_content);
		
		//자동분석 성공여부
		if(sf.isSuccess_type()){
			if(!sf.getResultIc_type().trim().equals("")){
				//System.out.println("분석타입 : " + sf.getResultIc_type());
    			
    			String[] arrIcType = sf.getResultIc_type().split(",");
    			for(String icType : arrIcType){
    				p_typecode = "";
    				arResultForm = sf.getResultList().get(Integer.parseInt(icType));
    				
    				//연관키워드 찾기
    				if(arResultForm.size() > 0){
    					option = arResultForm.get(0).getIc_auto_option();
    					
    					if(option == 0){
    						option = 1;
    					}
    				}else{
    					option = 1;
    				}
    				
    				if(option == 1 || option == 3){
    					if(icType.equals("9")){
    						sentiChk = true;
        					int positive = 0;
        					int negative = 0;
        					double trd_score = 0;
        					//성향분석
        					for(int i = 0; i < arResultForm.size(); i++){
        						rf = arResultForm.get(i);
        						if(rf.getIc_code() == 1){
        							positive = rf.getCount();
        						}else if(rf.getIc_code() == 2){
        							negative = rf.getCount();
        						}	
        					}
        						
        					if(positive > 0 || negative > 0){
        						trd_score = (double)positive / ((double)positive + negative) * 100;
        						
        						if((int)Math.round(trd_score) > 60  ){
            						p_typecode = "9,1";
            					}else if((int)Math.round(trd_score) <= 40  ){
            						p_typecode = "9,2";
            					}else{
            						p_typecode = "9,3";
            					}
        					}else{
        						p_typecode = "9,3";
        					}
        				
        				}else{
    
        					//일반분석
        					for(int i = 0; i < arResultForm.size(); i++){
            					rf = arResultForm.get(i);
            					
            					/*
            					if(i == 0){
            						f_rf = new ResultForm();
            						f_rf = rf;
            					}else{
            						if(f_rf.getCount() < rf.getCount()){
            							f_rf = rf;
            						}else if(f_rf.getCount() == rf.getCount()){
            							
            							if(f_rf.getWeighted_point() < rf.getWeighted_point()){
            								f_rf = rf;
            							}
            						}
            					}
            					*/
            					
            					if(p_typecode.equals("")){			                        						
            						p_typecode = AutoIssueCode.get(rf.getIc_type()+","+rf.getIc_code());
            					}else{
            						p_typecode += "@" + AutoIssueCode.get(rf.getIc_type()+","+rf.getIc_code());
            					}
            				}
        				}
    					
        				if(!p_typecode.equals("")){
        					//중복제거
        					p_typecode = getGroupByStream(p_typecode,"@","I");
        					
        					if(typeCodes.equals("")){
            					typeCodes = p_typecode;
            				}else{
            					typeCodes += "@" + p_typecode;
            				}
        				}

    				}

    				if(option == 2 || option == 3){
    					for(int i = 0; i < arResultForm.size(); i++){
    						rf = arResultForm.get(i);   					
    						//System.out.println("연관키워드코드 : " + rf.getPat_seq());
    						
    						arRelationKey = getGroupBy(rf.getPat_seq(),",");	
    						//System.out.println("연관키워드코드 카운트 : " + arRelationKey.size());
    						
    						//연관키워드 저장
    						insertRelationKeyword(id_seq, rf.getIc_type(), rf.getIc_code(), arRelationKey);
    					}
    				}
    				
    			} 
			}
		}
		if(!sentiChk){
			if(typeCodes.equals("")){
				typeCodes = "9,3";
			}else{
				typeCodes += "@9,3";	
			}
		}
		
		if(typeCodes.equals("")){
			insertIssueDataCode(id_seq, "30,2@33,2@34,2");
		}else{
			insertIssueDataCode(id_seq, typeCodes + "@30,1@33,1@34,2");
		}
	
		insertIssueGlobal(id_seq, dd_visitcount, dd_sitelink, dd_siteName, dd_membercount, dd_doccount, dd_followercount, dd_imglink);
		
		return id_seq;
   	}
   	
   	static int insertIssueData( int newNum
            , String md_site_name
            , String md_menu_name
            , String md_type
            , String md_date
            , String md_title
            , String md_url
            , String md_name_count
            , int md_pseq
            , String s_seq
            , String md_img
            , String l_alpha
            , String sg_seq
            , String md_content
            , String media_info
            , String d_seq
            , String riskKey
            ){
   		int result = 0;
    	PreparedStatement pstmt = null;
        StringBuffer sb = null;
    	int contentIdx = 0;
    	ResultSet rs = null;
    	
        try{
			
            sb = new StringBuffer();
            sb.append("INSERT INTO ISSUE_INSTA_DATA (      			\n");	      
            
            sb.append("	ID_TYPE		\n");
            sb.append("	,MD_SEQ			\n");
            sb.append("	,ID_TITLE		\n");
            sb.append("	,ID_URL			\n");
            sb.append("	,SG_SEQ			\n");
            sb.append("	,S_SEQ			\n");
            sb.append("	,MD_SITE_NAME	\n");
            sb.append("	,MD_SITE_MENU	\n");
            sb.append("	,MD_DATE		\n");
            sb.append("	,ID_CONTENT		\n");
            sb.append("	,ID_REGDATE		\n");
            sb.append("	,ID_USEYN		\n");
            sb.append("	,M_SEQ			\n");
            sb.append("	,MD_SAME_CT		\n");
            sb.append("	,MD_TYPE		\n");
            sb.append("	,MD_PSEQ		\n");
            sb.append("	,ID_REPORTYN	\n");
            sb.append("	,MEDIA_INFO		\n");
            sb.append("	,L_ALPHA		\n");
            sb.append("	,D_SEQ			\n");
            sb.append("	,ID_KEYWORD			\n");
            sb.append(")	     		\n");
            sb.append(" VALUES (?,?,?,?,?,?,?,?,?,?,NOW(),?,?,?,?,?,?,?,?,?,?)		\n");
            
                 
			//System.out.println( sb.toString() );
			pstmt = dbconn1.createPStatement(sb.toString());
			
			int paNum = 1;
			pstmt.clearParameters();
			pstmt.setInt(paNum++, 1);//ID_TYPE
			pstmt.setInt(paNum++, newNum); //MD_SEQ
			pstmt.setString(paNum++, md_title); //ID_TITLE
			pstmt.setString(paNum++, md_url); //ID_URL
			pstmt.setString(paNum++, sg_seq); //SG_SEQ
			pstmt.setString(paNum++, s_seq); //S_SEQ
			pstmt.setString(paNum++, md_site_name); //MD_SITE_NAME
			if(md_menu_name != null || !"".equals(md_menu_name)){
				if(md_menu_name.length() > 64){
					md_menu_name = md_menu_name.substring(0, 60);	
				}
			}
			pstmt.setString(paNum++, md_menu_name); //MD_SITE_MENU
			pstmt.setString(paNum++, md_date); //MD_DATE
			//pstmt.setString(paNum++, "SYSTEM"); //ID_WRITTER
			contentIdx = paNum++;
			pstmt.setString(contentIdx, md_content); //ID_CONTENT
			pstmt.setString(paNum++, "Y"); //ID_USEYN
			pstmt.setInt(paNum++, 1); //M_SEQ
			pstmt.setInt(paNum++, 0); //MD_SAME_CT
			pstmt.setString(paNum++, md_type); //MD_TYPE
			pstmt.setInt(paNum++, newNum); //MD_PSEQ
			pstmt.setString(paNum++, "Y"); //ID_REPORTYN
			
			if(media_info.length() > 120){
				media_info = media_info.substring(0,120); 
			}
			
			pstmt.setString(paNum++, media_info); //MEDIA_INFO
			pstmt.setString(paNum++, l_alpha); //L_ALPHA
			pstmt.setString(paNum++, d_seq); //D_SEQ
			pstmt.setString(paNum++, riskKey); //ID_KEYWORD
		
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys(); 
			if(rs.next()){
				result = rs.getInt(1);
			}
			
        }catch(SQLException ex) {
        	
			if(ex.getErrorCode() == 1366){

				try{
		        	StringBuffer reCon = new StringBuffer();
		        	String content = md_content;
		        	char c = 0;
		        	
		        	for(int i =0; i < content.length(); i++){
		        		c = content.charAt(i);
		        		if (false == (0xD800 <= c && c <= 0xDBFF || 0xDC00 <= c && c <= 0xDFFF) )
		        		{
		        			reCon.append(c);
		        		}
		        	}	
		        	pstmt.setString(contentIdx, reCon.toString());
		        	pstmt.executeUpdate();
		        	
		        	rs = pstmt.getGeneratedKeys(); 
					if(rs.next()){
						result = rs.getInt(1);
					}
		        
				}catch (Exception e) {
					Log.writeExpt(name,ex.toString());
					System.exit(1);
				}
			}else{
				Log.writeExpt(name,ex.toString() + " : errorCode("+ex.getErrorCode()+")" );
				ex.printStackTrace();
				System.exit(1);
			}	
		} catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
        }
        
    	return result;
   		
   		
   		
   	}
   	
   	static HashMap<String, String> getAutoIssueCode(){
    	
    	HashMap<String, String> map = new HashMap<String, String>();
        
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;       
        
		try{
			stmt = dbconn1.createStatement();
			
            sb = new StringBuffer();
            sb.append("SELECT IC_TYPE															\n");
            sb.append("     , IC_CODE															\n");
            sb.append("     , FN_P_TYPECODE(IC_TYPE, IC_CODE, IC_PTYPE, IC_PCODE) AS TYPECODE	\n");
            sb.append("  FROM ISSUE_CODE 														\n");
            sb.append(" WHERE IC_AUTO_USE = 'Y'													\n"); 
            sb.append("   AND IC_USEYN = 'Y' 													\n");
            sb.append("   AND IC_CODE > 0														\n");
            
			System.out.println( sb.toString() );
			rs = stmt.executeQuery( sb.toString() );
			
			while( rs.next() )
			{
				map.put( rs.getString("IC_TYPE") + "," +  rs.getString("IC_CODE")
					   , rs.getString("TYPECODE")
						);
			}

		} catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
        }
        
        return map;
    }
   	
   	static boolean insertRelationKeyword(int idSeq, int icType, int icCode, HashMap<String, Integer> hKey){
    	boolean result = false;
    	PreparedStatement pstmt = null;
        StringBuffer sb = null;
        int paNum = 1;
		try{
			
            sb = new StringBuffer();
            sb.append("INSERT INTO ISSUE_INSTA_RELATION_KEYWORD (ID_SEQ, IC_TYPE, IC_CODE, PAT_SEQ, PAT_CNT)\n"); 
            sb.append("                            VALUES (?, ?, ?, ?, ?)\n");
                 
			//System.out.println( sb.toString() );
			pstmt = dbconn1.createPStatement(sb.toString());
			
			Set<String> keys = hKey.keySet();
			Iterator<String> it = keys.iterator();
			
			
			String name = "";
			while(it.hasNext()){
				paNum = 1;
				
				name = it.next();
				pstmt.clearParameters();
				pstmt.setInt(paNum++, idSeq);
				pstmt.setInt(paNum++, icType);
				pstmt.setInt(paNum++, icCode);
				pstmt.setInt(paNum++, Integer.parseInt(name));
				pstmt.setInt(paNum++, hKey.get(name));
				pstmt.execute();
			}
			
			result = true;

		}catch(SQLException ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
			
        }catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
        }
        
        return result;
    }
   	
   	static boolean insertIssueDataCode(int id_seq, String typeCode){
    	boolean result = false;
    	
    	PreparedStatement pstmt = null;
        StringBuffer sb = null;
    	ResultSet rs = null;
    	
    	String[] arTmp = null;
    	String[] arSubTmp = null;
    	
    	
    	try{
    		
    		sb = new StringBuffer();
			sb.append("INSERT INTO ISSUE_INSTA_DATA_CODE (ID_SEQ, IC_TYPE, IC_CODE) VALUES (?, ?, ?) \n");
    		pstmt = dbconn1.createPStatement(sb.toString());
    		
    		arTmp = typeCode.split("@");
    		
    		System.out.println(typeCode);
    		
    		for(int i =0; i < arTmp.length; i++){
    			arSubTmp = arTmp[i].split(",");
    			pstmt.clearParameters();
    			pstmt.setInt(1, id_seq);
    			pstmt.setInt(2, Integer.parseInt(arSubTmp[0]));
    			pstmt.setInt(3, Integer.parseInt(arSubTmp[1]));
    			pstmt.executeUpdate();
    		}
    		
    		result = true;
    		
    	}catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
        }
    	
    	
    	return result;
    }
   	
   	static boolean insertIssueGlobal( int id_seq
									, String g_visit_count
									, String g_site_link
									, String g_site_name
									, String g_member_count
									, String g_doc_count
									, String g_followercount
									, String g_imglink
									)
	{
   		boolean result = false;
    	
    	PreparedStatement pstmt = null;
        StringBuffer sb = null;
    	ResultSet rs = null;
    	
    	try{
    		
    		sb = new StringBuffer();
			sb.append("INSERT INTO ISSUE_INSTA_GLOBAL (ID_SEQ, G_VISIT_COUNT, G_SITE_LINK, G_SITE_NAME, G_MEMBER_COUNT, G_DOC_COUNT, G_FOLLOWERCOUNT, G_IMGLINK)		\n");
			sb.append("                  VALUES (?, ?, ?, ?, ?, ?, ?, ?)									\n");
			
    		pstmt = dbconn1.createPStatement(sb.toString());
			pstmt.clearParameters();
			int idx =0;
			pstmt.setInt(++idx, id_seq);
			pstmt.setLong(++idx, Long.parseLong(g_visit_count));
			pstmt.setString(++idx, g_site_link);
			pstmt.setString(++idx, g_site_name);
			pstmt.setInt(++idx, Integer.parseInt(g_member_count));
			pstmt.setInt(++idx, Integer.parseInt(g_doc_count));
			pstmt.setInt(++idx, Integer.parseInt(g_followercount));
			pstmt.setString(++idx, g_imglink);
			
			pstmt.executeUpdate();
    		result = true;
    	}catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
        }
    	
    	return result;
	}
   	
   	static String lastUpdateTime(String api_key){
    	String result = null;
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	stmt = dbconn1.createStatement();
        	
        	sb = new StringBuffer();
            sb.append(" SELECT FROM_UNIXTIME(ST_UPDATE_STAMP,'%Y%m%d') AS UPDATE_DATE FROM SYSTEM_TRANSFER WHERE ST_API_KEY = '"+api_key+"' 	\n");
            
            System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next()){
				result = rs.getString("UPDATE_DATE");
			}
            
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
   	
   	static boolean updateLastTransNum( String transferNo, String lastTransNum )
    {
        boolean result = false;

        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;

		try{
            sb = new StringBuffer();
            sb.append(" UPDATE SYSTEM_TRANSFER SET ST_LAST_SEQ="+lastTransNum+" , ST_UPDATE_STAMP = UNIX_TIMESTAMP() WHERE ST_API_KEY = '"+transferNo+"' \n");

			System.out.println( sb.toString() );
			stmt = dbconn1.createStatement();
			if( stmt.executeUpdate( sb.toString() ) > 0 ) result = true;

		} catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
   	
   	public static String stampToDateTime(String stamp, String format) {
    	long unixSeconds = Long.parseLong(stamp);
    	Date date = new Date(unixSeconds*1000L); 
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
    	//sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
    	String formattedDate = sdf.format(date);    	
    	return formattedDate;
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

