package risk.dashboard.search;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONException;
import risk.json.JSONObject;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class SearchMgr {
	
	private DateUtil du = new DateUtil();
	private StringUtil su = new StringUtil();
	
	DBconn conn = null;
	Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    StringBuilder sb = null;
    
    
    public JSONArray getLucyData(String name, String cmd, String i_sdate, String i_edate
    		, String i_and_text, String i_exact_text, String i_or_text, String i_exclude_text, String i_sourcetype,String i_remove_rt, int rowCnt, int nowPage, String userid) {
    	JSONArray resultArray = new JSONArray();
    	
    	String url = "http://lucyapi.realsn.com/API?systemkey=54&userid=" + userid + "&cmd=" + cmd;
    	StringBuilder param = new StringBuilder();
    	
    	try {
    		param.append("&i_sdate=" + i_sdate + "&i_edate=" + i_edate);
    		
    		if(!"".equals(i_and_text))		{	param.append("&general_keyword=" + URLEncoder.encode(i_and_text, "UTF-8"));				}
        	if(!"".equals(i_exact_text))		{	param.append("&sentence_keyword=" + URLEncoder.encode(i_exact_text, "UTF-8"));			}
        	if(!"".equals(i_or_text))			{	param.append("&adjacent_keyword=" + URLEncoder.encode(i_or_text, "UTF-8"));					}
        	if(!"".equals(i_exclude_text))	{	param.append("&exclude_keyword=" + URLEncoder.encode(i_exclude_text, "UTF-8"));	}
        	if(!"".equals(i_sourcetype))		{	param.append("&i_sourcetype=" + URLEncoder.encode(i_sourcetype, "UTF-8"));		} 
        	if("Y".equals(i_remove_rt)) {	param.append("&i_remove_rt=Y");	}else{param.append("&i_remove_rt=N");}
        	
        	if("datetrend".equals(cmd) || "channelTrend".equals(cmd) || "datechannel".equals(cmd)){
        		param.append("&row_limit=31&page_num=0&sort_field_first=i_crawlstamp&sort_type_first=desc");
        	}else if("relationword".equals(cmd)){
        		param.append("&row_limit=" + rowCnt + "&page_num=0&sort_field_first=i_crawlstamp&sort_type_first=desc");
        	}else if("searchpersonal".equals(cmd)){
        		param.append("&row_limit=" + rowCnt + "&page_num=" + nowPage + "&fieldList=i_title,i_url,i_sitename,s_seq,i_docid,i_crawlstamp&sort_field_first=i_crawlstamp&sort_type_first=desc");
        	}else if("twsearchpersonal".equals(cmd)){
        		param.append("&row_limit=" + rowCnt + "&page_num=" + nowPage + "&fieldList=i_title,i_url,i_sitename,i_similar_count,i_docid,i_crawlstamp&i_twitter_sort_field=rt&i_twitter_sort=desc");
        	}
        	
        	String returnData = StringUtil.GetLucyAPIPost(url, param.toString());
        	System.out.println(" [ " + name + " ] " + url + param.toString());
        	
        	JSONObject object = new JSONObject(returnData);
        	
        	if("datetrend".equals(cmd) || "channelTrend".equals(cmd) || "datechannel".equals(cmd)){
        		resultArray = object.getJSONArray("arrDate");
        	}else if("relationword".equals(cmd)){
        		resultArray = object.getJSONArray("arrWord");
        	}else if("searchpersonal".equals(cmd)|| "twsearchpersonal".equals(cmd)){
        		JSONArray searchArray = new JSONArray();
        		searchArray = object.getJSONArray("docs");
        		resultArray.put(0, object.getString("numFound"));
        		resultArray.put(1, searchArray);
        	}
        
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return resultArray;
    }
    
    /*	실시간 검색 데이터	*/
    public JSONArray getRealTimeList(String siteBoardCode) {
    	conn = new DBconn();
    	sb = new StringBuilder();
    	
    	JSONArray arr = null; 
   	 	try {
			conn.getDBCPConnection();

			sb.append("	## 실시간 검색 데이터 																																																\n");
			sb.append("	 (SELECT SD_TITLE , SD_DATE, SD_RANK*1 AS SD_RANK FROM RANK_DATA WHERE SB_SEQ = " + siteBoardCode + " AND SD_RANK = 1 ORDER BY SD_DATE DESC LIMIT 1)		\n");
			sb.append("	 UNION ALL																																																				\n");
			sb.append("	 (SELECT SD_TITLE , SD_DATE, SD_RANK*1 AS SD_RANK FROM RANK_DATA WHERE SB_SEQ = " + siteBoardCode + " AND SD_RANK = 2 ORDER BY SD_DATE DESC LIMIT 1)		\n");
			sb.append("	 UNION ALL																																																				\n");
			sb.append("	 (SELECT SD_TITLE , SD_DATE, SD_RANK*1 AS SD_RANK FROM RANK_DATA WHERE SB_SEQ = " + siteBoardCode + " AND SD_RANK = 3 ORDER BY SD_DATE DESC LIMIT 1)		\n");
			sb.append("	 UNION ALL																																																				\n");
			sb.append("	 (SELECT SD_TITLE , SD_DATE, SD_RANK*1 AS SD_RANK FROM RANK_DATA WHERE SB_SEQ = " + siteBoardCode + " AND SD_RANK = 4 ORDER BY SD_DATE DESC LIMIT 1)		\n");
			sb.append("	 UNION ALL																																																				\n");
			sb.append("	 (SELECT SD_TITLE , SD_DATE, SD_RANK*1 AS SD_RANK FROM RANK_DATA WHERE SB_SEQ = " + siteBoardCode + " AND SD_RANK = 5 ORDER BY SD_DATE DESC LIMIT 1)		\n");
			sb.append("	 UNION ALL																																																				\n");
			sb.append("	 (SELECT SD_TITLE , SD_DATE, SD_RANK*1 AS SD_RANK FROM RANK_DATA WHERE SB_SEQ = " + siteBoardCode + " AND SD_RANK = 6 ORDER BY SD_DATE DESC LIMIT 1)		\n");
			sb.append("	 UNION ALL																																																				\n");
			sb.append("	 (SELECT SD_TITLE , SD_DATE, SD_RANK*1 AS SD_RANK FROM RANK_DATA WHERE SB_SEQ = " + siteBoardCode + " AND SD_RANK = 7 ORDER BY SD_DATE DESC LIMIT 1)		\n");
			sb.append("	 UNION ALL																																																				\n");
			sb.append("	 (SELECT SD_TITLE , SD_DATE, SD_RANK*1 AS SD_RANK FROM RANK_DATA WHERE SB_SEQ = " + siteBoardCode + " AND SD_RANK = 8 ORDER BY SD_DATE DESC LIMIT 1)		\n");
			sb.append("	 UNION ALL																																																				\n");
			sb.append("	 (SELECT SD_TITLE , SD_DATE, SD_RANK*1 AS SD_RANK FROM RANK_DATA WHERE SB_SEQ = " + siteBoardCode + " AND SD_RANK = 9 ORDER BY SD_DATE DESC LIMIT 1)		\n");
			sb.append("	 UNION ALL																																																				\n");
			sb.append("	 (SELECT SD_TITLE , SD_DATE, SD_RANK*1 AS SD_RANK FROM RANK_DATA WHERE SB_SEQ = " + siteBoardCode + " AND SD_RANK = 10 ORDER BY SD_DATE DESC LIMIT 1)		\n");
			
			pstmt = null;
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			//System.out.println(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("rank", rs.getString("SD_RANK"));
				obj.put("keyword", rs.getString("SD_TITLE"));
				arr.put(obj);
			}
			
   	 	}catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
   	 	return arr;
    }
    
    /*	정보검색	*/
    public JSONArray getInfoData(String i_sdate, String i_edate, String i_and_text, String i_exact_text, String i_or_text, String i_exclude_text, String i_sourcetype,String i_remove_rt, String keywordOp, String userid) {
    	JSONArray result = new JSONArray();
    	JSONArray arrayLucyData = new JSONArray();
    	
    	try{
	    		if("".equals(i_and_text) && "".equals(i_exact_text) && "".equals(i_or_text)) return result;
	    		arrayLucyData = getLucyData("정보검색", "datetrend", i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, 0, 0, userid);
    		
    		for(int i=0; i<arrayLucyData.length(); i++){
    			JSONObject tmpObject = arrayLucyData.getJSONObject(i);
    			
    			JSONObject resultObject = new JSONObject();
    			resultObject.put("category", du.getDate(tmpObject.getString("date"), "MM/dd"));
    			
    			if("1".equals(keywordOp)){
    				resultObject.put("column-1", tmpObject.get("cnt"));
    			}else if("2".equals(keywordOp)){
    				resultObject.put("column-2", tmpObject.get("cnt"));
    			}else if("3".equals(keywordOp)){
    				resultObject.put("column-3", tmpObject.get("cnt"));
    			}
    			
    			resultObject.put("fulldate", du.getDate(tmpObject.getString("date"), "yyyy-MM-dd"));
    			result.put(resultObject);
    		}
    	} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    
    /*	감성현황(정보 분포) - 왼쪽차트	*/
    public JSONArray getInfoDistribution_left(String i_sdate, String i_edate, String i_and_text, String i_exact_text, String i_or_text, String i_exclude_text, String i_sourcetype,String i_remove_rt, String userid) {
    	JSONArray result = new JSONArray();
    	JSONArray arrayLucyData = new JSONArray();
    	
    	try{
	    		if("".equals(i_and_text) && "".equals(i_exact_text) && "".equals(i_or_text)) return result;
	    		arrayLucyData = getLucyData("감성현황(정보 분포) - 왼쪽차트", "datetrend", i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, 0, 0, userid);
	    	
	    		int posCnt = 0;
	    		int negCnt = 0;
	    		int neuCnt = 0;
	    		
	    		for(int i=0; i<arrayLucyData.length(); i++){
	    			JSONObject tmpObject = arrayLucyData.getJSONObject(i);
	    			posCnt += Integer.parseInt(tmpObject.getString("positive_cnt"));
	    			negCnt += Integer.parseInt(tmpObject.getString("nagative_cnt"));
	    			neuCnt += Integer.parseInt(tmpObject.getString("neutrality_cnt"));
	    		}
	    		
	    		JSONObject resultObject = new JSONObject();
	    		resultObject.put("category", "긍정");
	    		resultObject.put("column-1", posCnt);
	    		result.put(resultObject);
	    		
	    		resultObject = new JSONObject();
	    		resultObject.put("category", "부정");
	    		resultObject.put("column-1", negCnt);
	    		result.put(resultObject);
	    		
	    		resultObject = new JSONObject();
	    		resultObject.put("category", "중립");
	    		resultObject.put("column-1", neuCnt);
	    		result.put(resultObject);
	    		
    	} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println( result.toString() );
    	return result;
    }
    
    /*	감성현황(정보 분포) - 오른쪽차트	*/
    public JSONArray getInfoDistribution_right(String i_sdate, String i_edate, String i_and_text, String i_exact_text, String i_or_text, String i_exclude_text, String i_sourcetype,String i_remove_rt, String userid) {
    	JSONArray result = new JSONArray();
    	JSONArray arrayLucyData = new JSONArray();
    	
    	try{
	    		if("".equals(i_and_text) && "".equals(i_exact_text) && "".equals(i_or_text)) return result;
	    		arrayLucyData = getLucyData("감성현황(정보 분포) - 오른쪽차트", "channelTrend", i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, 0, 0, userid);
	    	
	    		String [] channel = i_sourcetype.split(" ");
	    		
	    		for(int i=0; i<arrayLucyData.length(); i++){
	    			JSONObject tmpObject = arrayLucyData.getJSONObject(i);
	    			for(int j=0; j<channel.length; j++){
		    			if(channel[j].equals(tmpObject.get("channel"))){
			    			JSONObject resultObject = new JSONObject();
			    			resultObject.put("channel", tmpObject.getString("channel"));
			    			resultObject.put("category", su.getChannelName(tmpObject.getString("channel")));
			    			resultObject.put("column-1", tmpObject.get("positive_cnt"));
			    			resultObject.put("column-2", tmpObject.get("nagative_cnt"));
			    			resultObject.put("column-3", tmpObject.get("neutrality_cnt"));
			    			result.put(resultObject);
		    			}
		    		}
	    		}
	    		
    	} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    
    /*	감성현황(감성 추이)	*/
    public JSONArray getSentiTrend(String i_sdate, String i_edate, String i_and_text, String i_exact_text, String i_or_text, String i_exclude_text, String i_sourcetype,String i_remove_rt, String userid){
    	JSONArray result = new JSONArray();
    	JSONArray arrayLucyData = new JSONArray();
    	
    	try{
	    		if("".equals(i_and_text) && "".equals(i_exact_text) && "".equals(i_or_text)) return result;
	    		arrayLucyData = getLucyData("감성현황(감성 추이)", "datetrend", i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, 0, 0, userid);
    		
    		for(int i=0; i<arrayLucyData.length(); i++){
    			JSONObject tmpObject = arrayLucyData.getJSONObject(i);
    			
    			JSONObject resultObject = new JSONObject();
    			resultObject.put("category", du.getDate(tmpObject.getString("date"), "MM/dd"));
    			resultObject.put("column-1", tmpObject.get("positive_cnt"));
    			resultObject.put("column-2", tmpObject.get("nagative_cnt"));
    			resultObject.put("column-3", tmpObject.get("neutrality_cnt"));
    			
    			resultObject.put("fulldate", du.getDate(tmpObject.getString("date"), "yyyy-MM-dd"));
    			result.put(resultObject);
    		}
    	} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    
    /*	감성현황(채널별 추이)	*/
    public JSONArray getChannelTrend(String i_sdate, String i_edate, String i_and_text, String i_exact_text, String i_or_text, String i_exclude_text, String i_sourcetype,String i_remove_rt, String userid){
    	JSONArray result = new JSONArray();
    	JSONArray arrayLucyData = new JSONArray();
    	
    	try{
	    		if("".equals(i_and_text) && "".equals(i_exact_text) && "".equals(i_or_text)) return result;
	    		arrayLucyData = getLucyData("감성현황(채널별 추이)", "datechannel", i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, 0, 0, userid);
    		
    		for(int i=0; i<arrayLucyData.length(); i++){
    			JSONObject tmpObject = arrayLucyData.getJSONObject(i);
    			
    			JSONObject resultObject = new JSONObject();
    			resultObject.put("category", du.getDate(tmpObject.getString("date"), "MM/dd"));
    			resultObject.put("TW", tmpObject.get("tw_cnt"));		//트위터
    			resultObject.put("FA", tmpObject.get("fp_cnt"));		//페이스북
    			resultObject.put("BL", tmpObject.get("bl_cnt"));		//블로그
    			resultObject.put("DC", tmpObject.get("dc_cnt"));		//커뮤니티
    			resultObject.put("CF", tmpObject.get("cf_cnt"));		//카페
    			resultObject.put("DN", tmpObject.get("dn_cnt"));		//언론
    			resultObject.put("PR", tmpObject.get("pr_cnt"));		//포탈댓글
    			
    			resultObject.put("fulldate", du.getDate(tmpObject.getString("date"), "yyyy-MM-dd"));
    			result.put(resultObject);
    		}
    	} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    
    /*	연관키워드	*/
    public JSONArray getRelationKeywordData(String i_sdate, String i_edate, String i_and_text, String i_exact_text, String i_or_text, String i_exclude_text, String i_sourcetype,String i_remove_rt, int limit, String userid) {
    	JSONArray result = new JSONArray();
    	JSONArray arrayLucyData = new JSONArray();
    	
    	try{
	    		if("".equals(i_and_text) && "".equals(i_exact_text) && "".equals(i_or_text)) return result;
	    		arrayLucyData = getLucyData("연관키워드", "relationword", i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, limit, 0, userid);
	    	
	    		for(int i=0; i<arrayLucyData.length(); i++){
	    			JSONObject tmpObject = arrayLucyData.getJSONObject(i);
	    			
	    			JSONObject resultObject = new JSONObject();
	    			resultObject.put("word", tmpObject.get("word_nm"));
	    			resultObject.put("cnt", tmpObject.get("cnt"));
	    			result.put(resultObject);
	    		}
	    		
    	} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    
    /*	관련정보	*/
    public JSONObject getRelationInfoDataForTw(String i_sdate, String i_edate
    		, String i_and_text, String i_exact_text, String i_or_text, String i_exclude_text, String i_sourcetype,String i_remove_rt, int rowCnt, int nowPage, String userid) {
    	JSONObject result = new JSONObject();
    	JSONArray arrayLucyData = new JSONArray();
    	
    	try{
	    		if("".equals(i_and_text) && "".equals(i_exact_text) && "".equals(i_or_text)) return result;
	    		arrayLucyData = getLucyData("관련정보", "twsearchpersonal", i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, rowCnt, nowPage, userid);
	    		
	    		result.put("total", arrayLucyData.getString(0));

	    		JSONArray tmpArray = new JSONArray();
	    		for(int i=0; i<arrayLucyData.getJSONArray(1).length(); i++){
	    			JSONObject tmpObject = arrayLucyData.getJSONArray(1).getJSONObject(i);
	    			
	    			JSONObject resultObject = new JSONObject();
	    			resultObject.put("sitename", tmpObject.get("i_sitename"));
	    			//resultObject.put("sitecode", tmpObject.get("s_seq"));
	    			resultObject.put("title", tmpObject.get("i_title"));
	    			resultObject.put("url", tmpObject.get("i_url"));
	    			resultObject.put("samecnt", tmpObject.get("i_similar_count"));
	    			resultObject.put("date", getUnixTimeStamp(tmpObject.getLong("i_crawlstamp"), "yy/MM/dd"));
	    			tmpArray.put(resultObject);
	    		}
	    		result.put("data", tmpArray);
	    		
    	} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    
    
    /*	관련정보	*/
    public JSONObject getRelationInfoData(String i_sdate, String i_edate
    		, String i_and_text, String i_exact_text, String i_or_text, String i_exclude_text, String i_sourcetype,String i_remove_rt, int rowCnt, int nowPage, String userid) {
    	JSONObject result = new JSONObject();
    	JSONArray arrayLucyData = new JSONArray();
    	
    	try{
	    		if("".equals(i_and_text) && "".equals(i_exact_text) && "".equals(i_or_text)) return result;
	    		arrayLucyData = getLucyData("관련정보", "searchpersonal", i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, rowCnt, nowPage, userid);
	    		
	    		result.put("total", arrayLucyData.getString(0));

	    		JSONArray tmpArray = new JSONArray();
	    		for(int i=0; i<arrayLucyData.getJSONArray(1).length(); i++){
	    			JSONObject tmpObject = arrayLucyData.getJSONArray(1).getJSONObject(i);
	    			
	    			JSONObject resultObject = new JSONObject();
	    			resultObject.put("sitename", tmpObject.get("i_sitename"));
	    			resultObject.put("sitecode", tmpObject.get("s_seq"));
	    			resultObject.put("title", tmpObject.get("i_title"));
	    			resultObject.put("url", tmpObject.get("i_url"));
	    			resultObject.put("date", getUnixTimeStamp(tmpObject.getLong("i_crawlstamp"), "yy/MM/dd"));
	    			tmpArray.put(resultObject);
	    		}
	    		result.put("data", tmpArray);
	    		
    	} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    
    /*	관련정보	*/
    public JSONObject getRelationInfoData_excel(String i_sdate, String i_edate
    		, String i_and_text, String i_exact_text, String i_or_text, String i_exclude_text, String i_sourcetype,String i_remove_rt, int rowCnt, int nowPage, String userid) {
    	JSONObject result = new JSONObject();
    	JSONArray arrayLucyData = new JSONArray();
    	JSONArray tmpArray = new JSONArray();
    	int execel_limit_count = 3000;
    	int jarryCnt = 0;
    	try{
	    		if("".equals(i_and_text) && "".equals(i_exact_text) && "".equals(i_or_text)) return result;
	    		
	    		arrayLucyData = getLucyData("관련정보", "searchpersonal", i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, rowCnt, nowPage, userid);
	    		jarryCnt = arrayLucyData.getJSONArray(1).length();
	    		for(int i=0; i<jarryCnt; i++){
	    			JSONObject tmpObject = arrayLucyData.getJSONArray(1).getJSONObject(i);
	    			
	    			JSONObject resultObject = new JSONObject();
	    			
	    			if(!tmpObject.isNull("i_sitename")){
	    				resultObject.put("sitename", tmpObject.get("i_sitename"));	
	    			}else{
	    				resultObject.put("sitename", "");
	    			}
	    			
	    			if(!tmpObject.isNull("s_seq")){
	    				resultObject.put("sitecode", tmpObject.get("s_seq"));
	    			}else{
	    				resultObject.put("sitecode", "");
	    			}
	    			
	    			if(!tmpObject.isNull("s_seq")){
	    				resultObject.put("sitecode", tmpObject.get("s_seq"));	
	    			}else{
	    				resultObject.put("sitecode", "");
	    			}
	    			
	    			if(!tmpObject.isNull("i_title")){
	    				resultObject.put("title", tmpObject.get("i_title"));	
	    			}else{
	    				resultObject.put("title", "");
	    			}
	    			
	    			if(!tmpObject.isNull("i_url")){
	    				resultObject.put("url", tmpObject.get("i_url"));	
	    			}else{
	    				resultObject.put("url", "");
	    			}
	    			
	    			if(!tmpObject.isNull("i_crawlstamp")){
	    				resultObject.put("date", getUnixTimeStamp(tmpObject.getLong("i_crawlstamp"), "yy/MM/dd"));	
	    			}else{
	    				resultObject.put("date", "");
	    			}
	    			
	    			tmpArray.put(resultObject);
	    		}
	    		result.put("data", tmpArray);
	    		
	    	//	execel_limit_count = execel_limit_count-jarryCnt;
	    	//	
	    	//	while( (jarryCnt == rowCnt) && (jarryCnt <= execel_limit_count) ){
	    	//		//
			//		//try {
			//		//	Thread.sleep(500);
			//		//} catch (InterruptedException e) {
			//		//	// TODO Auto-generated catch block
			//		//	e.printStackTrace();
			//		//}
			//		//
	    	//		if(nowPage > 30){
	    	//			break;
	    	//		}
			//		
	    	//		nowPage++;
	    	//		arrayLucyData = getLucyData("관련정보", "search", i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype, rowCnt, nowPage, userid);
	    	//		jarryCnt = arrayLucyData.getJSONArray(1).length(); 
		    //		for(int i=0; i<arrayLucyData.getJSONArray(1).length(); i++){
		    //			JSONObject tmpObject = arrayLucyData.getJSONArray(1).getJSONObject(i);
		    //			
		    //			JSONObject resultObject = new JSONObject();
		    //			resultObject.put("sitename", tmpObject.get("i_sitename"));
		    //			resultObject.put("sitecode", tmpObject.get("s_seq"));
		    //			resultObject.put("title", tmpObject.get("i_title"));
		    //			resultObject.put("url", tmpObject.get("i_url"));
		    //			resultObject.put("date", getUnixTimeStamp(tmpObject.getLong("i_crawlstamp"), "yy/MM/dd"));
		    //			tmpArray.put(resultObject);
		    //		}
		    //		result.put("data", tmpArray);
		    //		
		    //		execel_limit_count = execel_limit_count-jarryCnt;
		    //		
	    	//	}
    	//} catch (InterruptedException e) {
    	//	e.printStackTrace();	
    	} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    
    /**
	 * @param timeStamp
	 * @return
	 */
	public String getUnixTimeStamp(long timeStamp, String format) {
	    long unixTime = timeStamp * 1000;
	    Date date = new Date(unixTime);
	    
	    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
	    String retVal = dateFormat.format(date);
	    return retVal;
	}
}
