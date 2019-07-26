package risk.search.solr;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;

import org.jdom.JDOMException;

import risk.json.JSONArray;
import risk.json.JSONException;
import risk.json.JSONObject;
import risk.search.solr.*;
import risk.util.ConfigUtil;
import risk.util.DateUtil;

public class SolrChart {
	
	static SolrSearch ssch = new SolrSearch();
	DateUtil du = new DateUtil();

	public static String queryUrl="";
	public static String q="";
	public static int hitCnt = 0;
	
	public String startDate = ""; 
	public String endDate = "";
	
	//String sgroup = "sgroup:(1 OR 2 OR 3 OR 4 OR 5) AND ";
	//String serverURL = "http://s.realsn.com/api";
	String serverURL = "http://lucyapi.realsn.com/SmisAPI";
	String serverPORT = "8983";
	
	//////////////////////////////API error Start
	
	//API key
	ConfigUtil cu = new ConfigUtil();
	String lucyKey = cu.getConfig("LUCY_API");
	//API error
	String errorCode ="";
	String errorMsg ="";
	String errorAlertMsg ="";
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorAlertMsg() {
		return errorAlertMsg;
	}

	public void setErrorAlertMsg(String errorAlertMsg) {
		this.errorAlertMsg = errorAlertMsg;
	}
	
	public JSONArray getChartXml(String keyword, String sdate, String edate, String searchDate, String sgroup, String userid) throws IOException, JDOMException, ParseException{
		JSONArray result = new JSONArray();
		JSONObject object = new JSONObject(); 
		ArrayList cntList = new ArrayList();
		
		String ecd_keyword = ssch.changeEncode(keyword, "utf-8");
		

		if(ecd_keyword.length() <= 0){
			ecd_keyword ="";
		}
		
		queryUrl = serverURL+"?type=Facet&language=KOR";
		q =  "&systemkey="+ lucyKey + "&userid="+ userid +"&q="+ ecd_keyword +"&"+sgroup+"&sdate="+sdate+"&edate="+edate+"&rows=0&returntype=xml";
		
		
		System.out.println("[queryUrl]" + queryUrl);
		System.out.println("[q]" + q);
		
		cntList = ssch.loadChartData(queryUrl, q);
		
		SubForm sf;
		
		int chkcnt=0;
		int cnt=0;
		int Avg=0;
		 
		String date = "";
		
		for(int i=0 ; i< cntList.size();i++){
			sf = (SubForm)cntList.get(i);
			
			if(i == 0){
				startDate = sf.getDate();
			}else if (i == cntList.size() -1){
				endDate = sf.getDate();
			}
			
			date = du.getDate(sf.getDate(), "MM/dd");
			
			object = new JSONObject(); 
			try {
				object.put("category", date);
				object.put("cnt", sf.getCnt());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			result.put(object);
		}
		
	
		return result;
	}
	
}
