package risk.dashboard.lucyapi;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import risk.util.DateUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LucyAPI{
	
	public static final String LUCY_URL = "http://lucyapi.realsn.com/API";
	
	public static final String KEYWORD_CNT = LUCY_URL+"?cmd=keywordCount";
	public static final String DATE_CNT = LUCY_URL+"?cmd=dateCnt";
	
	public static final String LIST = LUCY_URL+"?cmd=search";
	
	private DateUtil du = new DateUtil();
	
	public static void main(String[] args) {
		
		try {
			
//			String query1 = (new LucyAPI()).getQuery(KEYWORD_CNT, "20160831", "20160831", "DN DC BL CF FA TW", "먹스타그램");
//
//			String query2 = (new LucyAPI()).getQuery(DATE_CNT, "20160831", "20160906", "DN DC BL CF FA TW", "먹스타그램");
//			
//			Map rMap1 = (new LucyAPI()).callLucy(KEYWORD_CNT, query1);
//			Map rMap2 = (new LucyAPI()).callLucy(DATE_CNT, query2);
//			
//			System.out.println(rMap1);
//			System.out.println(rMap2);
			
			String query3 = (new LucyAPI()).getQuery(LIST, "20161006", "20161006", "DN DC BL CF FA TW", "먹스타그램");
			Map rMap3 = (new LucyAPI()).callLucy(LIST, query3,"1","10", false);
			
			String total_cnt = String.valueOf( rMap3.get("TOTAL_CNT") );
			List items = (List)rMap3.get("ITEMS");
			
			System.out.println( "total_cnt : " + total_cnt );
					
			for(int i=0;i<items.size();i++){
				Map item = (Map)items.get(i);
				String sitename = String.valueOf( item.get("SITENAME") );
				String title = String.valueOf( item.get("TITLE") );
				String s_seq = String.valueOf( item.get("S_SEQ") );
				String content = String.valueOf( item.get("CONTENT") );
				String crawltime = String.valueOf( item.get("CRAWLTIME") );
				String url = String.valueOf( item.get("URL") );
				
				System.out.println(" ======================================= "
						+ "\n" + " sitename : " + sitename 
						+ "\n" + " title : " + title 
						+ "\n" + " s_seq : " + s_seq 
						+ "\n" + " content : " + content 
						+ "\n" + " crawltime : " + crawltime 
						+ "\n" + " url : " + url 
						+ "\n" + " ======================================= ");
				
				//System.out.println("rMap3 >>>>>>>>>>>> " + rMap3);
			}
			
//			System.out.println("rMap3 >>>>>>>>>>>> " + rMap3);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 설명: getQuery
	 * 로직:
	 * 1. 
	 * @param url
	 * @param sDate
	 * @param eDate
	 * @param source_type DN DC BL CF FA TW
	 * @param search_txt
	 * @return
	 * @throws Exception
	 */
	public String getQuery(String url, String sDate, String eDate, String source_type, String search_txt ) throws Exception{
		
		return getQuery(url, sDate, eDate, source_type, search_txt, "", "", "");
	}
	
	public String getQuery(String url, String sDate, String eDate, String source_type, String search_txt, String sort, String type) throws Exception{
		return getQuery(url, sDate, eDate, source_type, search_txt, sort, type, "");
	}
	
	/**
	 * 설명: getQuery
	 * 로직:
	 * 1. 
	 * @param url
	 * @param sDate
	 * @param eDate
	 * @param source_type DN DC BL CF FA TW
	 * @param search_txt
	 * @return
	 * @throws Exception
	 */
	public String getQuery(String url, String sDate, String eDate, String source_type, String search_txt, String sort, String type, String trend) throws Exception{
		String query = "&";
		
		query += "i_sdate="+sDate+"&i_edate="+eDate+"&i_sourcetype="+source_type+"&i_searchtxt="+search_txt;
		
		if (!"".equals(trend)) {
			query += "&i_trend="+trend;
		}
		//String sort_first="i_crawlstamp";
		//String type_first="desc";
		
		//String sort_second="";
		if(!"".equals(sort) && !"".equals(type))
			query +="&sort_field_first="+sort+"&sort_type_first="+type;
		
		if(KEYWORD_CNT.equals(url)){
			
		}else
			if(DATE_CNT.equals(url)){
				
				long diff = du.DateDiff("yyyyMMdd", eDate, sDate );
				
				query += "&row_limit="+(diff+1);
			}
		
		return query;
	}
//	
//	/**
//	 * 설명: getQuery
//	 * 로직:
//	 * 1. 
//	 * @param url
//	 * @param sDate
//	 * @param eDate
//	 * @param source_type DN DC BL CF FA TW
//	 * @param search_txt
//	 * @return
//	 * @throws Exception
//	 */
//	public String getQuery(String url, String sDate, String eDate, String source_type, String search_txt, String page_num, String row_limit ) throws Exception{
//		
//		String query = "&";
//		
//		query += "i_sdate="+sDate+"&i_edate="+eDate+"&i_sourcetype="+source_type+"&i_searchtxt="+search_txt+"&page_num="+page_num+"&row_limit="+row_limit; 
//		
//		return query;
//	} 
	
	public Map parseJson(String url, String line) throws JSONException{
		
		Map rMap = new HashMap();
		
		JSONObject root = new JSONObject(line);
		//System.out.println("|XXXX| jsonObj-root : " + root );
		
		if(KEYWORD_CNT.equals(url)){
			
			if( !root.isNull("totalCnt")) rMap.put("TOTAL_CNT", root.getInt("totalCnt"));
			
			if( !root.isNull("numFound")) rMap.put("NUM_FOUND", root.getInt("numFound"));
			
			JSONArray arrKeyword = null;
			if( !root.isNull("arrKeyword")) arrKeyword = root.getJSONArray("arrKeyword");
			
			List rList = new ArrayList();
			for(int i=0;i<arrKeyword.length();i++){
				JSONObject arrKeyword_o = arrKeyword.getJSONObject(i);
				
				Map m = new HashMap();
				m.put("WORD", arrKeyword_o.getString("word"));
				m.put("CNT", arrKeyword_o.getInt("cnt"));
				
				rList.add(m);
			}
			
			rMap.put("ITEMS", rList);
			
		}else
			if(DATE_CNT.equals(url)){
				
				if( !root.isNull("totalCnt")) rMap.put("TOTAL_CNT", root.getInt("totalCnt"));
				
				if( !root.isNull("numFound")) rMap.put("NUM_FOUND", root.getInt("numFound"));
				
				JSONArray arrDate = null;
				if( !root.isNull("arrDate")) arrDate = root.getJSONArray("arrDate");
				
				List rList = new ArrayList();
				for(int i=0;i<arrDate.length();i++){
					JSONObject arrKeyword_o = arrDate.getJSONObject(i);
					
					Map m = new HashMap();
					m.put("DATE", arrKeyword_o.getString("date"));
					m.put("CNT", arrKeyword_o.getInt("cnt"));
					
					rList.add(m);
				}
				
				rMap.put("ITEMS", rList);
				
				
			}else
				if(LIST.equals(url)){
					
					if( !root.isNull("numFound")) rMap.put("TOTAL_CNT", root.getInt("numFound"));
					
					//if( !root.isNull("numFound")) rMap.put("NUM_FOUND", root.getInt("numFound"));
					
					JSONArray docs = null;
					if( !root.isNull("docs")) docs = root.getJSONArray("docs");
					
					List rList = new ArrayList();
					for(int i=0;i<docs.length();i++){
						JSONObject doc_o = docs.getJSONObject(i);
						
						Map m = new HashMap();
						
						m.put("SITENAME", doc_o.getString("i_sitename"));
						m.put("TITLE", doc_o.getString("i_title"));
						m.put("S_SEQ", doc_o.getString("s_seq"));
						m.put("CONTENT", doc_o.getString("i_content"));
						
						//m.put("i_crawlstamp", doc_o.getString("i_crawlstamp"));
						//m.put("CRAWLTIME", du.getDateByTimestamp(doc_o.getString("i_crawlstamp")) );
						m.put("CRAWLTIME", du.timestamp2Date(doc_o.getString("i_crawlstamp"), null) );
						
						m.put("URL", doc_o.getString("i_url"));
						
						rList.add(m);
					}
					
					rMap.put("ITEMS", rList);
					
					
				}
		
		
		return rMap;
		
	}
	
	public Map callLucy(String p_url, String param, String page_num, String row_limit, boolean log) throws IOException, JSONException{
		
		Map result = new HashMap();
		
		URL url = new URL(p_url);
		
		URLConnection conn = null;
		conn = url.openConnection();
		
		((HttpURLConnection)conn).setRequestMethod("POST");
		conn.setDoOutput(true);
	    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	    wr.write(param+"&page_num="+page_num+"&row_limit="+row_limit);
	    wr.flush();
		
	    BufferedReader reader = null;
	    reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	    
	    String line = reader.readLine();
	    
		//while(line != null){
		if(!line.equals("") && !line.equals(" ")){
			//System.out.println("line >>> " + line);
			
			System.out.println(" XXX CALL XXX : " + p_url + param+"&page_num="+page_num+"&row_limit="+row_limit );
			if(log) System.out.println(" XXX RETURN XXX : " + line );
			
			result = this.parseJson(p_url, line);
			
			if(log) System.out.println(" XXX PARSED XXX : " + result );
			
		}
		//}
		
		return result;
		
	}
	
	/**
	 * 설명: callLucy
	 * 로직:
	 * 1. 
	 * @param p_url
	 * @param param
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public Map callLucy(String p_url, String param) throws IOException, JSONException{
		
		Map result = new HashMap();
		
		URL url = new URL(p_url);
		
		URLConnection conn = null;
		conn = url.openConnection();
		
		((HttpURLConnection)conn).setRequestMethod("POST");
		conn.setDoOutput(true);
	    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	    wr.write(param);
	    wr.flush();
		
	    BufferedReader reader = null;
	    reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	    
	    String line = reader.readLine();
	    
		//while(line != null){
		if(!line.equals("") && !line.equals(" ")){
			//System.out.println("line >>> " + line);
			
			System.out.println(" XXX CALL XXX : " + p_url + param );
			System.out.println(" XXX RETURN XXX : " + line );
			
			result = this.parseJson(p_url, line);
			
			System.out.println(" XXX PARSED XXX : " + result );
			
		}
		//}
		
		return result;
		
	}
	
	
	/**
	 * 설명: Element root를 리턴해준다
	 * 로직:
	 * 1. 
	 * @param queryUrl www.lucy........cmd=cmdName
	 * @param q &param=paramName etc
	 * @return
	 * @throws IOException
	 * @throws JDOMException
	 * @throws JSONException
	 */
	public JSONObject rootReturn(String queryUrl, String q)throws IOException ,JDOMException, JSONException{
		System.out.println("# query q : "+queryUrl+q);
		Element root=null;
		URL url = new URL(queryUrl);
		URLConnection conn;
		conn = url.openConnection();
		((HttpURLConnection)conn).setRequestMethod("POST");
		conn.setDoOutput(true);
	    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	    wr.write(q);
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
