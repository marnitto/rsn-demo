package risk.dashboard.popup;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONException;
import risk.json.JSONObject;
import risk.util.StringUtil;

public class CmnPopupLucyMgr {
	
	DBconn conn = null;
	Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    StringBuilder sb = null;
    
    
    public JSONArray getLucyPopupData(String name, String cmd, String i_sdate, String i_edate
    		, String general_keyword, String sentence_keyword, String adjacent_keyword, String exclude_keyword, String i_sourcetype, String i_trend, String i_relation_word, int rowCnt, int nowPage, String userid) {
    	JSONArray resultArray = new JSONArray();
    	
    	String url = "http://lucyapi.realsn.com/API?systemkey=54&userid=" + userid + "&cmd=" + cmd;
    	StringBuilder param = new StringBuilder();
    	
    	try {
    		param.append("&i_sdate=" + i_sdate + "&i_edate=" + i_edate);
    		
    		if(!"".equals(general_keyword))				{	param.append("&general_keyword=" + URLEncoder.encode(general_keyword, "UTF-8"));								}
        	if(!"".equals(sentence_keyword))				{	param.append("&sentence_keyword=" + URLEncoder.encode(sentence_keyword, "UTF-8"));							}
        	if(!"".equals(adjacent_keyword))					{	param.append("&adjacent_keyword=" + URLEncoder.encode(adjacent_keyword, "UTF-8"));									}
        	if(!"".equals(exclude_keyword))			{	param.append("&exclude_keyword=" + URLEncoder.encode(exclude_keyword, "UTF-8"));						}
        	if(!"".equals(i_sourcetype))				{	param.append("&i_sourcetype=" + URLEncoder.encode(i_sourcetype, "UTF-8"));							}
        	if(!"".equals(i_trend))					{	param.append("&i_trend=" + URLEncoder.encode(i_trend, "UTF-8"));										}
        	if(!"".equals(i_relation_word))			{	param.append("&i_relation_word=" + URLEncoder.encode(i_relation_word, "UTF-8"));					}
        	
        	if("datetrend".equals(cmd) || "channelTrend".equals(cmd)){
        		param.append("&row_limit=31&page_num=0&sort_field_first=i_crawlstamp&sort_type_first=desc");
        	}else if("relationword".equals(cmd)){
        		param.append("&row_limit=" + rowCnt + "&page_num=0&sort_field_first=i_crawlstamp&sort_type_first=desc");
        	}else if("search".equals(cmd)){
        		param.append("&row_limit=" + rowCnt + "&page_num=" + nowPage + "&sort_field_first=i_crawlstamp&sort_type_first=desc");
        	}
        	
        	String returnData = StringUtil.GetLucyAPIPost(url, param.toString());
        	System.out.println(" [ " + name + " ] " + url + param.toString());
        	
        	JSONObject object = new JSONObject(returnData);
        	
        	if("datetrend".equals(cmd) || "channelTrend".equals(cmd)){
        		resultArray = object.getJSONArray("arrDate");
        	}else if("relationword".equals(cmd)){
        		resultArray = object.getJSONArray("arrWord");
        	}else if("search".equals(cmd)){
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

    /*	관련정보	*/
    public JSONObject getPopupData(String i_sdate, String i_edate
    		, String general_keyword, String sentence_keyword, String adjacent_keyword, String exclude_keyword, String i_sourcetype, String i_trend, String I_relation_word, int rowCnt, int nowPage, String userid) {
    	JSONObject result = new JSONObject();
    	JSONArray arrayLucyData = new JSONArray();
    	
    	try{
	    		if("".equals(general_keyword) && "".equals(sentence_keyword) && "".equals(adjacent_keyword)) return result;
	    		arrayLucyData = getLucyPopupData("Lucy Popup Data", "search", i_sdate, i_edate, general_keyword, sentence_keyword, adjacent_keyword, exclude_keyword, i_sourcetype, i_trend, I_relation_word, rowCnt, nowPage, userid);
	    		
	    		result.put("total", arrayLucyData.getString(0));

	    		JSONArray tmpArray = new JSONArray();
	    		for(int i=0; i<arrayLucyData.getJSONArray(1).length(); i++){
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
	    			
	    			resultObject.put("title", tmpObject.get("i_title"));
	    			resultObject.put("url", tmpObject.get("i_url"));
	    			resultObject.put("senti", tmpObject.get("i_trend"));	
	    			resultObject.put("i_sourcetype", tmpObject.get("i_sourcetype"));
	    			resultObject.put("i_content", tmpObject.get("i_content"));
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
