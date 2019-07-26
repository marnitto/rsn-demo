package risk.admin.pressMng;

import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import risk.DBconn.DBconn;
import risk.util.Log;

public class pressMng {
	
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private StringBuffer sb = null;	
	
	int totalCnt = 0;
	
	public int getTotalCnt() {
		return totalCnt;
	}
	
	public ArrayList getPressDataList(int nowpage, int cnt, String sDate, String eDate, String sTime, String eTime, String searchKey)
    {
		ArrayList result = new ArrayList();
		ResultSet rs2    = null;
		Statement stmt2  = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			stmt2 = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT COUNT(*) AS CNT \n");
			sb.append("FROM PRESS_RELEASE_MNG     \n");
			sb.append("WHERE d_date BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"'\n");
			
			if(!searchKey.equals("")){
				String[] arStr = searchKey.split(" ");
				for(int i = 0; i < arStr.length; i ++){
					sb.append("AND d_title LIKE '%"+arStr[i]+"%' \n");
				}
			}

			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
				
			if(rs.next()){
				totalCnt = rs.getInt("CNT");
			}
			
			
			sb = new StringBuffer();
			sb.append("SELECT 															\n");
			sb.append("d_seq, d_site, d_board, date_format(d_date, '%Y-%m-%d') as d_date, d_title, d_url, d_keyword, d_tw_count, d_same_count, d_ana_yn 		\n");
			sb.append("FROM PRESS_RELEASE_MNG															\n");
			sb.append("WHERE d_date BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"'	\n");
			if(!searchKey.equals("")){
				String[] arStr = searchKey.split(" ");
				for(int i = 0; i < arStr.length; i ++){
					sb.append("AND d_title LIKE '%"+arStr[i]+"%' 								\n");
				}
			}
			sb.append("ORDER BY d_date DESC														\n");
			sb.append("LIMIT "+((nowpage-1) * cnt)+","+cnt+"									\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			Map map = new HashMap();
			while(rs.next()){
				map = new HashMap();
				map.put("d_seq", rs.getString("d_seq"));
				map.put("d_site", rs.getString("d_site"));
				map.put("d_board", rs.getString("d_board"));
				map.put("d_date", rs.getString("d_date"));
				map.put("d_title", rs.getString("d_title"));
				map.put("d_url", rs.getString("d_url"));
				map.put("encode_url",  URLEncoder.encode(rs.getString("d_url"), "UTF-8"));
				map.put("d_keyword", rs.getString("d_keyword"));
				map.put("d_tw_count", rs.getString("d_tw_count"));
				map.put("d_same_count", rs.getString("d_same_count"));
				map.put("d_ana_yn", rs.getString("d_ana_yn"));
				
				sb = new StringBuffer();
				sb.append(" SELECT ifnull(SUM(REPLY_COUNT),0) AS TOTAL                                                    \n");
			    sb.append(" FROM PRESS_RELEASE_DATA                                                             \n");
			    sb.append(" WHERE D_SEQ = "+rs.getString("d_seq")+"                                             \n");
				System.out.println(sb.toString());
				rs2 = stmt2.executeQuery( sb.toString() );
				if(rs2.next()){
					map.put("reply_count", rs2.getString("TOTAL"));
				}else{
					map.put("reply_count", "0");
				}
				result.add(map);
			}
			
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
        	if (rs2 != null) try { rs2.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (stmt2 != null) try { stmt2.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	
	public ArrayList getPressDataExcelList(String sDate, String eDate, String sTime, String eTime, String searchKey)
    {
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT 															\n");
			sb.append("A.d_seq, A.d_site, A.d_board, date_format(A.d_date, '%Y-%m-%d') as d_date, A.d_title, A.d_url, A.d_keyword, A.d_tw_count, A.d_same_count, A.d_ana_yn	\n"); 		
			sb.append(" ,ifnull(FN_PRESS_MEDIA_COUNT(A.D_SEQ),0) AS media_chk_cnt 					\n");
			sb.append(" ,ifnull((SELECT SUM(reply_count) FROM  PRESS_RELEASE_DATA WHERE D_SEQ = A.d_seq),0) AS reply_cnt	\n");
			sb.append(" ,(SELECT if(count(0) = 0 , 'N', 'Y') FROM  PRESS_RELEASE_DATA WHERE D_SEQ = A.d_seq AND d_top > 0) AS top	\n");
			sb.append(" ,(SELECT COUNT(0) AS CNT  FROM  PRESS_RELEASE_DATA WHERE D_SEQ = A.d_seq  AND d_type ='news' AND d_percent >= 66 ) AS same_per_1	\n");
			sb.append(" ,(SELECT COUNT(0) AS CNT  FROM  PRESS_RELEASE_DATA WHERE D_SEQ = A.d_seq  AND d_type ='news' AND d_percent <= 65 AND d_percent >= 31) AS same_per_2	\n");
			sb.append(" ,(SELECT COUNT(0) AS CNT  FROM  PRESS_RELEASE_DATA WHERE D_SEQ = A.d_seq  AND d_type ='news' AND d_percent <= 30 ) AS same_per_3	\n");
			sb.append("FROM PRESS_RELEASE_MNG A                                             \n");
			sb.append("WHERE A.d_date BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"'	\n");
			if(!searchKey.equals("")){
				String[] arStr = searchKey.split(" ");
				for(int i = 0; i < arStr.length; i ++){
					sb.append("AND A.d_title LIKE '%"+arStr[i]+"%' 								\n");
				}
			}
			sb.append("ORDER BY A.d_date DESC													\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			Map map = new HashMap();
			while(rs.next()){
				map = new HashMap();
				map.put("d_seq", rs.getString("d_seq"));
				map.put("d_site", rs.getString("d_site"));
				map.put("d_board", rs.getString("d_board"));
				map.put("d_date", rs.getString("d_date"));
				map.put("d_title", rs.getString("d_title"));
				map.put("d_url", rs.getString("d_url"));
				map.put("encode_url",  URLEncoder.encode(rs.getString("d_url"), "UTF-8"));
				map.put("d_keyword", rs.getString("d_keyword"));
				map.put("d_tw_count", rs.getString("d_tw_count"));
				map.put("d_same_count", rs.getString("d_same_count"));
				map.put("d_ana_yn", rs.getString("d_ana_yn"));
				map.put("media_chk_cnt", rs.getString("media_chk_cnt"));
				map.put("d_top", rs.getString("top"));
				map.put("reply_cnt", rs.getString("reply_cnt"));
				map.put("same_per_1", rs.getString("same_per_1"));
				map.put("same_per_2", rs.getString("same_per_2"));
				map.put("same_per_3", rs.getString("same_per_3"));
				
				double all_same_cnt = rs.getInt("d_same_count");
				double media_chk_cnt = rs.getInt("media_chk_cnt");
				
				double result_percent = 0;
				System.out.println(media_chk_cnt +"/"+all_same_cnt+"*100");
				if(media_chk_cnt > 0){
					result_percent = (media_chk_cnt/all_same_cnt)*100;
				}
				System.out.println("result_percent - "+result_percent);
				map.put("result_percent", Math.round(result_percent));
				
				int percent1 = rs.getInt("same_per_1");
				int percent2 = rs.getInt("same_per_2");
				int percent3 = rs.getInt("same_per_3");
				String same_grade = "";
				if(percent1 > percent2 && percent1 > percent3){
					same_grade="상";
				}
				if(percent2 > percent1 && percent2 > percent3){
					same_grade="중";
				}
				if(percent3 > percent1 && percent3 > percent2){
					same_grade="하";
				}
				
				map.put("same_grade", same_grade);
				result.add(map);
			}
			
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	
	public ArrayList getPressSameDataList(int nowpage, int cnt, String sDate, String eDate, String sTime, String eTime, String searchKey, int d_type, String tw_type, String d_Seq)
    {
		ArrayList result = new ArrayList();
		    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT COUNT(*) AS CNT \n");
			sb.append("FROM PRESS_RELEASE_DATA     \n");
			sb.append("WHERE d_date BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"'\n");
			sb.append("	AND d_seq = "+d_Seq+"\n");
			if(1==d_type){
				sb.append(" AND LOWER(d_type) = 'news'	\n");
			}else{
				if(!"".equals(tw_type)){
					sb.append(" AND LOWER(d_type) = LOWER('"+tw_type+"')	\n");
				}else{
					sb.append(" AND LOWER(d_type) != 'news'	\n");	
				}
			}
			if(!searchKey.equals("")){
				String[] arStr = searchKey.split(" ");
				for(int i = 0; i < arStr.length; i ++){
					sb.append("AND d_title LIKE '%"+arStr[i]+"%' \n");
				}
			}

			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
				
			if(rs.next()){
				totalCnt = rs.getInt("CNT");
			}
			
			
			sb = new StringBuffer();
			sb.append("SELECT 															\n");
			sb.append("d_seq, i_docid, if(d_type='news', convert(replace(i_docid,'DN',''),UNSIGNED), i_docid) AS p_docid, d_site, date_format(d_date, '%Y-%m-%d') as d_date, d_title, d_url, d_content, s_seq, d_percent, d_top, reply_count 		\n");
			sb.append("	, d_type , ifnull(d_board, '') as  d_board, ifnull(top_seqs,'') as top_seqs \n");
			sb.append("FROM PRESS_RELEASE_DATA															\n");
			sb.append("WHERE d_date BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"'	\n");
			sb.append("	AND d_seq = "+d_Seq+"\n");
			if(1==d_type){
				sb.append(" AND LOWER(d_type) = 'news'	\n");
			}else{
				if(!"".equals(tw_type)){
					sb.append(" AND LOWER(d_type) = LOWER('"+tw_type+"')	\n");
				}else{
					sb.append(" AND LOWER(d_type) != 'news'	\n");	
				}
			}
			
			if(!searchKey.equals("")){
				String[] arStr = searchKey.split(" ");
				for(int i = 0; i < arStr.length; i ++){
					sb.append("AND d_title LIKE '%"+arStr[i]+"%' 								\n");
				}
			}
			sb.append("ORDER BY d_date DESC														\n");
			sb.append("LIMIT "+((nowpage-1) * cnt)+","+cnt+"									\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			Map map = new HashMap();
			while(rs.next()){
				map = new HashMap();
				map.put("d_seq", rs.getString("d_seq"));
				map.put("i_docid", rs.getString("i_docid"));
				map.put("p_docid", rs.getString("p_docid"));
				map.put("d_site", rs.getString("d_site"));
				map.put("d_board", rs.getString("d_board"));
				map.put("d_date", rs.getString("d_date"));
				map.put("d_title", rs.getString("d_title"));
				map.put("d_url", rs.getString("d_url"));
				map.put("encode_url",  URLEncoder.encode(rs.getString("d_url"), "UTF-8"));
				map.put("d_content", rs.getString("d_content"));
				map.put("s_seq", rs.getString("s_seq"));
				map.put("d_percent", rs.getString("d_percent"));
				map.put("d_top", rs.getString("d_top"));
				map.put("top_seqs", rs.getString("top_seqs"));
				map.put("reply_count", rs.getString("reply_count"));
				map.put("d_type", rs.getString("d_type"));
				result.add(map);
			}
			
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	public int updateKeywords(String d_seq , String keywords){
		int result = 0;
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("UPDATE  PRESS_RELEASE_MNG \n");
			sb.append("SET d_keyword = '"+keywords+"' \n");
			sb.append("WHERE D_SEQ IN ("+d_seq+") \n");
			System.out.println(sb.toString());
			result = stmt.executeUpdate(sb.toString());
			
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
		
	}
}