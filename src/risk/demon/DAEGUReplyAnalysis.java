package risk.demon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;

public class DAEGUReplyAnalysis {
	
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
    	DateUtil du = new DateUtil();
    	
    	String today = du.getDate("yyyy-MM-dd");
    	
    	du.addDay(-2);
    	String yesterday = du.getDate("yyyy-MM-dd"); 
    	du.addHour(-1);
		String eTime = du.getDate("yyyy-MM-dd HH").substring(11, 13);
    	String lastUpdateTime = "";
    	
    	Log.crond("FSSReplyAnalysis START ...");    	    	
        try{
        	
        	dbconn1 = new DBconn(); //로컬디비
        	dbconn1.getSubDirectConnection();
        	
        	int total = 0;
        	int cnt = 0;
        	String doc_id = "";
        	
        	total = SeqTotalCnt(today, yesterday);
        	lastUpdateTime = selectUpdateTime();
        	String day = lastUpdateTime.substring(0, 10).replaceAll("-", "");
        	String hour = lastUpdateTime.substring(11, lastUpdateTime.length());
        	
        	while(cnt < total){
        		ArrayList portalReplyList = null;
        		
        		// 3일동안 이슈등록된 SEQ 목록
        		String d_seq = selectSeqsList(today, yesterday, cnt);
        		d_seq = d_seq.substring(0, d_seq.length()-1);
        		
        		System.out.println("d_seq ::: " + d_seq);
        		
        		//댓글이 존재하는 SEQ만 추출
        		doc_id = getPortalSeqsList(day, hour, d_seq);
        		
        		System.out.println("doc_id ::: " + doc_id);
        		
        		//댓글목록 가져오기
        		if(!"".equals(doc_id)){
        			portalReplyList = getPortalReplyList(day, hour, doc_id, eTime);
        		}
        		
        		//댓글리스트 INSERT
        		if(portalReplyList != null){
        			insertReplyList(portalReplyList);
        		}
        		        		
        		cnt += 100;
        		Thread.sleep(1000);
        	}
        	
        	if(cnt != 0){
        		insertUpdateTime();
        	}
        	
        }catch(Exception ex) {
        	Log.crond(ex.toString());
        	ex.printStackTrace();
        	//System.exit(1);
        }finally {
            if (dbconn1 != null) try { dbconn1.close(); } catch(SQLException ex) {}
        }
        Log.crond("FSSReplyAnalysis END ...");
    }
    
    static String selectUpdateTime(){
    	String result = "";
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        try{
        	
        	sb = new StringBuffer();
        	sb.append("SELECT DATE_FORMAT(LAST_UPDATETIME, '%Y-%m-%d %H') AS UPDATETIME	\n");
        	sb.append(" FROM REPLY_ANALYSIS_UPDATETIME	\n");
        	sb.append("WHERE R_SEQ = 1	\n");
        	
        	System.out.println(sb.toString());
			stmt = dbconn1.createStatement();
			rs = stmt.executeQuery(sb.toString());
			
			if( rs.next() ){
				result = rs.getString("UPDATETIME");
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
    
    static int SeqTotalCnt(String today, String yesterday){
    	int result = 0;
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        try{
        	
        	sb = new StringBuffer();
        	sb.append("SELECT COUNT(DISTINCT(A.D_SEQ)) AS TOT\n");
        	sb.append(" FROM META A, IDX B\n");
        	sb.append("WHERE A.MD_DATE BETWEEN '"+yesterday+" 00:00:00' AND '"+today+" 23:59:59'\n");
        	sb.append("   AND A.S_SEQ IN (2196,2199,3883,4537)\n");
        	sb.append("   AND A.MD_SEQ = B.MD_SEQ\n");
        	sb.append("   AND B.I_STATUS != 'T'\n");
        	sb.append("   AND ((B.K_XP IN (1) AND B.K_YP IN (1) AND B.K_ZP IN (1,2)) OR (B.K_XP IN (1) AND B.K_YP IN (4) AND B.K_ZP IN (1)) OR (B.K_XP IN (1) AND B.K_YP IN (2) AND B.K_ZP IN (4)))\n");
        	sb.append("   AND (A.D_SEQ != '' OR A.D_SEQ IS NOT NULL)\n");
        	sb.append("   AND B.ISSUE_CHECK ='Y'\n");
        	
        	System.out.println(sb.toString());
			stmt = dbconn1.createStatement();
			rs = stmt.executeQuery(sb.toString());
			
			if( rs.next() ){
				result = rs.getInt("TOT");
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
    
    static String selectSeqsList(String today, String yesterday, int cnt){
    	String result = "";
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        try{
        	
        	sb = new StringBuffer();
        	sb.append("SELECT DISTINCT(A.D_SEQ) AS D_SEQ\n");
        	sb.append(" FROM META A, IDX B\n");
        	sb.append("WHERE A.MD_DATE BETWEEN '"+yesterday+" 00:00:00' AND '"+today+" 23:59:59'\n");
        	sb.append("   AND A.S_SEQ IN (2196,2199,3883,4537)\n");
        	sb.append("   AND A.MD_SEQ = B.MD_SEQ\n");
        	sb.append("   AND B.I_STATUS != 'T'\n");
        	sb.append("   AND ((B.K_XP IN (1) AND B.K_YP IN (1) AND B.K_ZP IN (1,2)) OR (B.K_XP IN (1) AND B.K_YP IN (4) AND B.K_ZP IN (1)) OR (B.K_XP IN (1) AND B.K_YP IN (2) AND B.K_ZP IN (4)))\n");
        	sb.append("   AND (A.D_SEQ != '' OR A.D_SEQ IS NOT NULL)\n");
        	sb.append("   AND B.ISSUE_CHECK ='Y'\n");
        	sb.append("LIMIT "+cnt+", 100\n");
        	
        	System.out.println(sb.toString());
			stmt = dbconn1.createStatement();
			rs = stmt.executeQuery(sb.toString());
			
			while( rs.next() ){
				result += rs.getString("D_SEQ") + ",";
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
    public static String getPortalSeqsList(String day, String hour, String doc_id){
    	String result = "";
    	try {
    		String queryUrl = "http://lucyapi.realsn.com/API?"; //api 주소
    		String q = ""
					+ "cmd=replyExcelSort" //날짜별 댓글카운트, 긍부정카운트, 높은순으로 나열
					+ "&p_docid="+doc_id  // 원본문서번호 리스트(하루치)
					+ "&r_row_limit=100" //문서번호 갯수로 출력제한
					+ "&p_date="+day //검색 기준 날짜
					+ "&r_sdate="+day  //시작날짜
					+ "&r_edate="+day //종료날짜
					;
    		
    		System.out.println(q);
    		URL url = new URL(queryUrl);
			URLConnection conn;
			conn = url.openConnection();
			((HttpURLConnection)conn).setRequestMethod("POST");
			conn.setDoOutput(true);
			
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(q);
			wr.flush();
			String line = "";
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while((line = rd.readLine()) != null){
				JSONObject obj = new JSONObject(line);
				//JSONArray docsInfoArray = (JSONArray) obj.get("docs");
				
				if(!obj.isNull("docs")){
					JSONArray docsInfoArray = (JSONArray) obj.get("docs");
					for(int i=0; i<docsInfoArray.length(); i++){
						JSONObject json = (JSONObject) docsInfoArray.get(i);
						// 일자 | 매체명 | 문서번호 | 댓글수 | 긍정수 | 부정수 | 중립수 | 제목 | 링크
						//System.out.println(json.get("sitename")+"|"+json.get("p_docid")+"|"+json.get("docCnt")+"|"+json.get("positive_cnt")+"|"+json.get("nagative_cnt")+"|"+json.get("neutrality_cnt")+"|"+json.get("title")+"|"+json.get("url"));
						result += json.getString("p_docid");
						if(i < docsInfoArray.length()-1){
							result += ",";
						}
					}
				}
			}
    		
    	}catch(Exception e){
    		e.printStackTrace();
			Log.writeExpt(e);
		}
    	return result;
    }
    
    public static ArrayList getPortalReplyList(String day, String hour, String doc_id, String eTime){
		ArrayList result = new ArrayList();
		String queryUrl = "http://lucyapi.realsn.com/API?"; //api 주소
		String params = "";
		
		String[] docList = doc_id.split(",");
		
		try{
			for(int k=0; k<docList.length; k++){
				String p_docid = docList[k];
				int page_num = 1;
				pageNum :
				while(true){
					if(!"".equals(p_docid)){
						params = "cmd=replyExcelList"	//댓글리스트출력
								+ "&p_docid="+p_docid	//원본문서 하나
								+ "&r_row_limit=100"	//페이지당 댓글수
								+ "&r_page_num="+page_num	//페이지처리
								+ "&p_date="+day	//날짜리스트
								+ "&r_sdate="+day	//시작날짜
								+ "&r_edate="+day	//종료날짜
								+ "&r_stime="+hour	//시작시간
								+ "&r_etime="+eTime	//종료시간
								;
					}
					
					System.out.println(params);
				
					URL url = new URL(queryUrl);
					URLConnection conn;
					conn = url.openConnection();
					((HttpURLConnection)conn).setRequestMethod("POST");
					conn.setDoOutput(true);
					
					OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
					wr.write(params);
					wr.flush();
					String line = "";
					String tmp_test = "";
					BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
					/*System.out.println("============================================");
					System.out.println("p_docid ::: " + p_docid + "numCount ::: " + rd.readLine());
					System.out.println("============================================");*/
					while((line = rd.readLine()) != null){
						JSONObject obj = new JSONObject(line);
						JSONArray docsInfoArray = (JSONArray) obj.get("docs");
						String[] tmp = null;
						for(int i=0; i<docsInfoArray.length(); i++){
							tmp = new String[11];
							JSONObject json = (JSONObject) docsInfoArray.get(i);
							
							tmp[0] = p_docid;
							tmp[1] = json.get("r_sdate").toString();
							tmp[2] = json.get("p_title").toString().replaceAll("\'", "\\\\'");
							tmp[3] = json.get("p_sitename").toString();
							tmp[4] = json.get("p_url").toString();
							tmp[5] = json.get("r_content").toString().replaceAll("\\\\n", " ").replaceAll("\\\\r", " ").replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("|", "").replaceAll("\\\\{1,}", "").replaceAll("\"","").replaceAll("\'", "\\\\'");
							tmp[6] = json.get("r_trend").toString();
							tmp[7] = json.get("r_trendword_pos").toString();
							tmp[8] = json.get("r_trendword_neg").toString();
							tmp[9] = json.get("r_relation_word").toString();
							tmp[10] = json.get("r_datetime").toString();
							
							result.add(tmp);
							tmp_test = json.get("r_sdate").toString(); 
							//System.out.println(json.get("r_sdate")+"|"+json.get("p_title")+"|"+json.get("p_sitename")+"|"+doc_id+"|"+json.get("p_url")+"|"+json.get("r_content").toString().replaceAll("\\\\n", " ").replaceAll("\\\\r", " ").replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("|", "")+"|"+trend+"|"+json.get("r_trendword_pos")+"|"+json.get("r_trendword_neg")+"|"+json.get("r_relation_word"));
						}
					}
					if(!"".equals(tmp_test)){
						page_num++;
					}else{
						break pageNum;
					}
				}
			}
			
		}catch(Exception e){
			Log.writeExpt(e);
		}
		return result;
	}
    
    static void insertReplyList(ArrayList replyList) {

		String[] result = null;

		Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;

		try {
			stmt = dbconn1.createStatement();
			for(int i=0; i<replyList.size(); i++){
				sb = new StringBuffer();
				sb.append("INSERT INTO REPLY_ANALYSIS 											\n");
				sb.append("(r_docid, r_sdate, p_title, p_sitename, p_url, r_content, r_trend, r_trendword_pos, r_trendword_neg, r_relation_word, r_datetime, m_trend) VALUES 		\n");
				result = (String[])replyList.get(i);
				sb.append("("+result[0]+", "+result[1]+", '"+result[2]+"', '"+result[3]+"', '"+result[4]+"', '"+result[5]+"', "+result[6]+", '"+result[7]+"', '"+result[8]+"', '"+result[9]+"', '"+result[10] +"', 2)	\n");
				/*if(i < replyList.size()-1){
					sb.append(",");
				}else{
					sb.append(";");
				}*/
				stmt.addBatch(sb.toString());
				System.out.println(sb.toString());
			}
			stmt.executeBatch();
			
			//System.out.println(sb.toString());
			//stmt.executeUpdate();

		} catch (SQLException ex) {
			//ex.printStackTrace();
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			//ex.printStackTrace();
			Log.writeExpt(ex);
		} finally {
			sb = null;
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
			if (stmt != null)try {stmt.close();} catch (SQLException ex) {}
		}
	}
    
    static void insertUpdateTime() {

		Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;

		try {
			stmt = dbconn1.createStatement();
			sb = new StringBuffer();
			sb.append("UPDATE REPLY_ANALYSIS_UPDATETIME							\n");
			sb.append("SET last_updatetime = DATE_FORMAT(NOW(), '%y-%m-%d %H')	\n");
			sb.append("WHERE R_SEQ = 1											\n");
			
			System.out.println(sb.toString());
			stmt = dbconn1.createStatement();
			stmt.executeUpdate(sb.toString());

		} catch (SQLException ex) {
			//ex.printStackTrace();
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			//ex.printStackTrace();
			Log.writeExpt(ex);
		} finally {
			sb = null;
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
			if (stmt != null)try {stmt.close();} catch (SQLException ex) {}
		}
	}
    
    
}

