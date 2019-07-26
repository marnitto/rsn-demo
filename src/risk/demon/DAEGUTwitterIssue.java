package risk.demon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import risk.DBconn.DBconn;
import risk.util.Log;

public class DAEGUTwitterIssue {
	
	private static long runTime = 0;
	
	private static DBconn subdbconn = null;
	
	
	public static void main(String[] args) {
				
		Log.crond("IssueTwitter START ...");
		
		subdbconn = new DBconn();

		int result = 0;
		
		try{
			subdbconn.getSubDirectConnection();
			TwitterInsert();
			
			Log.crond("IssueTwitter END ...");
			
		}catch (Exception ex){	
			result = 0;
			Log.writeExpt("IssueTwitter","P:"+ex.getCause()+"  "+ex.getMessage());
			System.exit(1);
			
		}finally {
			try {if (subdbconn != null) {subdbconn.close();}} catch (SQLException ex) {}			
		}
	}
	
	public static void TwitterInsert(){
		
		try {
			
			ArrayList twitterList = getTwitterList();

			TwitterBean iBena = null;
			String html = "";
			String[] ar_html = null;
			
			
			String t_name = "";
			String t_id = "";
			String t_content = "";
			String t_rt = "";
			
			PioneerBean pbean = new PioneerBean();
			
			if(twitterList.size() > 0){
			
			for(int i =0; i<twitterList.size(); i++){
				iBena = (TwitterBean)twitterList.get(i);
				int result = TwitterDataInsert(iBena);
				
				if(result > 0){
					if(iBena.getT_is_rt().equals("R")){
						html = getHtmlHttpURL(iBena.getId_url().replaceAll("http://", "https://"));
						ar_html = html.split("\n");
						
						
						for(int j = 0; j < ar_html.length; j++){
							
							if(ar_html[j].indexOf("fullname js-action-profile-name show-popup-with-id") >= 0){
								
								t_name =  ar_html[j].split("with-id\">")[1];
								t_name =  t_name.split("</strong>")[0];
								
								if(t_name.indexOf("visuallyhidden") >= 0){
									t_name = t_name.split("<span class=\"visuallyhidden\">")[0];
								}
								
								if(t_name.indexOf("icon verified") >= 0){
									t_name = t_name.split("<span class=\"icon verified\">")[0];
								}
								
								
							}
							if(ar_html[j].indexOf("username js-action-profile-name") >= 0){
								
								t_id =  ar_html[j].split("<s>@</s><b>")[1];
								t_id =  t_id.split("</b></span>")[0];
							}
							if(ar_html[j].indexOf("js-tweet-text tweet-text") >= 0){
								 	
								t_content =  ar_html[j].split("tweet-text\">")[1];
								t_content = t_content.split("</p>")[0]; 
								t_content = TagClean(t_content);
								
							}
							if(ar_html[j].indexOf("data-activity-popup-title") >= 0){
								
								t_rt =  ar_html[j].split("popup-title=\"")[1];
								t_rt = t_rt.replaceAll("[^0-9]", "");
								
								//t_rt =  t_rt.split("번 리트윗됨")[0];
							}
						}
						
						pbean.setId_seq(iBena.getId_seq().trim());
						pbean.setP_name(t_name.trim());
						pbean.setP_user_id(t_id.trim());
						pbean.setP_title(t_content.trim());
						pbean.setP_rt_cnt(t_rt.trim());
						
						
						System.out.println("id_seq : " + pbean.getId_seq() + " / name : " + pbean.getP_name() + " / user id : " + pbean.getP_user_id() + " / rt_cnt : " + pbean.getP_rt_cnt());
						
						PioneerDataInsert(pbean);
						
					}
				}
				
			}
			}
			
		}catch (Exception ex){	
			Log.writeExpt("IssueTwitter","P:"+ex.getCause()+"  "+ex.getMessage());
		}
	}
	
	public static String getHtmlHttpURL(String strURL) {
		URL url;
		URLConnection conn;
		BufferedReader in = null;
		String line = null;
    	StringBuffer result = new StringBuffer();
		try {
			url = new URL(strURL);
			conn = url.openConnection();
			conn.setReadTimeout(10*1000);
			
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			
			Boolean ck_name = false;
			Boolean ck_id = false;
			Boolean ck_content = false;
			Boolean ck_rt = false;
			
			Boolean ck_start = false;
			
			while((line = in.readLine()) != null){
				
				
				if(line.indexOf("user-actions btn-group not-following") >= 0){
					ck_start = true;
				}
				
				if(!ck_start){continue;}
				
				if(ck_name == false && line.indexOf("fullname js-action-profile-name show-popup-with-id") >= 0){
					result.append( line+"\n");
					ck_name = true;
					
				}else if(ck_id == false && line.indexOf("username js-action-profile-name") >= 0){
					result.append( line+"\n");
					ck_id = true;
					
				}else if(ck_content == false && line.indexOf("js-tweet-text tweet-text") >= 0){
					result.append( line+"\n");
					ck_content = true;
					
				}else if(ck_rt == false && line.indexOf("data-activity-popup-title") >= 0){
					result.append( line+"\n");
					ck_rt = true;
					
				}
    		}
		} catch (SocketTimeoutException ex ) {
			Log.writeExpt("IssueTwitter","P:"+ex.getCause()+"  "+ex.getMessage());
			ex.printStackTrace();
		
		} catch (MalformedURLException ex) {
			Log.writeExpt("IssueTwitter","P:"+ex.getCause()+"  "+ex.getMessage());
			ex.printStackTrace();
		} catch (IOException ex) {
			Log.writeExpt("IssueTwitter","P:"+ex.getCause()+"  "+ex.getMessage());
			ex.printStackTrace();
		} finally {
			if(in != null){try {in.close();} catch (IOException e) {}}
		}
    	return result.toString();
	}
	
	public static int TwitterDataInsert(TwitterBean ibean)
	{
		
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("INSERT INTO ISSUE_TWITTER ( ID_SEQ			\n");
			sb.append("                          , T_TWEET_ID		\n");
			sb.append("                          , T_USER_ID		\n");
			sb.append("                          , T_IS_RT			\n");
			sb.append("                          , T_FOLLOWERS		\n");
			sb.append("                          , T_FOLLOWING		\n");
			sb.append("                          , T_TWEET			\n");
			sb.append("                          , T_SOURCE			\n");
			sb.append("                          , T_NAME			\n");
			sb.append("                          , T_PROFILE_IMAGE	\n");
			sb.append("                  )VALUES ( ?				\n");
			sb.append("                          , ?				\n");
			sb.append("                          , ?				\n");
			sb.append("                          , ?				\n");
			sb.append("                          , ?				\n");
			sb.append("                          , ?				\n");
			sb.append("                          , ?				\n");
			sb.append("                          , ?				\n");
			sb.append("                          , ?				\n");
			sb.append("                          , ?				\n");
			sb.append("                          )					\n");
			pstmt = subdbconn.createPStatement(sb.toString());
			
			
			int idx = 0;
			
			pstmt.setString(++idx, ibean.getId_seq());
			pstmt.setString(++idx, ibean.getT_tweet_id());
			pstmt.setString(++idx, ibean.getT_user_id());
			pstmt.setString(++idx, ibean.getT_is_rt());
			pstmt.setString(++idx, ibean.getT_followers());
			pstmt.setString(++idx, ibean.getT_following());
			pstmt.setString(++idx, ibean.getT_tweet());
			pstmt.setString(++idx, ibean.getT_source());
			pstmt.setString(++idx, ibean.getT_name());
			pstmt.setString(++idx, ibean.getT_profile_image());
			
			result = pstmt.executeUpdate();
			
			
		}catch (Exception ex){
			ex.printStackTrace();
			Log.writeExpt("IssueTwitter","P:"+ex.getCause()+"  "+ex.getMessage());
		}finally {
			try {if (pstmt != null) {pstmt.close();}} catch (SQLException ex) {}			
		}	
		
		return result;
	}
	
	public static int PioneerDataInsert(PioneerBean pbean)
	{
		
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("INSERT INTO ISSUE_TWITTER_PIONEER ( ID_SEQ		\n");
			sb.append("                                  , P_USER_ID	\n");
			sb.append("                                  , P_NAME		\n");
			sb.append("                                  , P_TITLE		\n");
			sb.append("                                  , P_RT_CNT		\n");
			sb.append("                         ) VALUES ( ?			\n");
			sb.append("                                  , ?			\n");
			sb.append("                                  , ?			\n");
			sb.append("                                  , ?			\n");
			sb.append("                                  , ?			\n");
			sb.append("                                  )				\n");
			pstmt = subdbconn.createPStatement(sb.toString());
			
			int idx = 0;
			
			pstmt.setString(++idx, pbean.getId_seq());
			pstmt.setString(++idx, pbean.getP_user_id());
			pstmt.setString(++idx, pbean.getP_name());
			pstmt.setString(++idx, pbean.getP_title());
			pstmt.setString(++idx, pbean.getP_rt_cnt());
			
			pstmt.executeUpdate();
			
			
		}catch (Exception ex){	
			Log.writeExpt("IssueTwitter","P:"+ex.getCause()+"  "+ex.getMessage());
		}finally {
			try {if (pstmt != null) {pstmt.close();}} catch (SQLException ex) {}			
		}	
		
		return result;
	}
	
	
	public static ArrayList getTwitterList(){
		StringBuffer sb = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList result = new ArrayList();
		
		try {    
			stmt = subdbconn.createStatement();	
			
			// 트위터 정보 가져오기
			sb = new StringBuffer();
			sb.append("SELECT B.ID_SEQ															\n");
			sb.append("     , B.ID_URL															\n");                
			sb.append("     , A.T_TWEET_ID														\n");
			sb.append("     , A.T_USER_ID														\n");
			sb.append("     , A.T_IS_RT															\n");
			sb.append("     , A.T_FOLLOWERS														\n");
			sb.append("     , A.T_FOLLOWING														\n");
			sb.append("     , A.T_TWEET															\n");
			sb.append("     , A.T_SOURCE														\n");
			sb.append("     , A.T_NAME															\n");
			sb.append("     , A.T_PROFILE_IMAGE													\n");   
			sb.append("  FROM TWEET A															\n");
			sb.append("     , ISSUE_DATA B														\n");
			sb.append(" WHERE A.MD_SEQ = B.MD_SEQ												\n");
			sb.append("   AND B.MD_DATE >= DATE_ADD( NOW(), INTERVAL -1 DAY)					\n");
			sb.append("   AND NOT EXISTS (SELECT 1 FROM ISSUE_TWITTER WHERE ID_SEQ = B.ID_SEQ)	\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			TwitterBean ibean = null;
			
			while(rs.next()){
				ibean = new TwitterBean();
				ibean.setId_seq(rs.getString("ID_SEQ"));
				ibean.setId_url(rs.getString("ID_URL"));
				ibean.setT_tweet_id(rs.getString("T_TWEET_ID"));
				ibean.setT_user_id(rs.getString("T_USER_ID"));
				ibean.setT_is_rt(rs.getString("T_IS_RT"));
				ibean.setT_followers(rs.getString("T_FOLLOWERS"));
				ibean.setT_following(rs.getString("T_FOLLOWING"));
				ibean.setT_tweet(rs.getString("T_TWEET"));
				ibean.setT_source(rs.getString("T_SOURCE"));
				ibean.setT_name(rs.getString("T_NAME"));
				ibean.setT_profile_image(rs.getString("T_PROFILE_IMAGE"));
				
				result.add(ibean);
			}
			
		}catch (Exception ex){	
			Log.writeExpt("IssueTwitter","P:"+ex.getCause()+"  "+ex.getMessage());
		}finally {
			try {if (rs != null) {rs.close();}} catch (SQLException ex) {}
			try {if (stmt != null) {stmt.close();}} catch (SQLException ex) {}			
			try {if (pstmt != null) {pstmt.close();}} catch (SQLException ex) {}		
		}
		
		return result;
		
	}
	
	
	public static class TwitterBean {
		
		String id_seq;
		String id_url;
		String t_tweet_id;
		String t_user_id;
		String t_is_rt;
		String t_followers;
		String t_following;
		String t_tweet;
		String t_source;
		String t_name;
		String t_profile_image;
		
		public String getId_seq() {
			return id_seq;
		}
		public void setId_seq(String id_seq) {
			this.id_seq = id_seq;
		}
		public String getId_url() {
			return id_url;
		}
		public void setId_url(String id_url) {
			this.id_url = id_url;
		}
		public String getT_tweet_id() {
			return t_tweet_id;
		}
		public void setT_tweet_id(String t_tweet_id) {
			this.t_tweet_id = t_tweet_id;
		}
		public String getT_user_id() {
			return t_user_id;
		}
		public void setT_user_id(String t_user_id) {
			this.t_user_id = t_user_id;
		}
		public String getT_is_rt() {
			return t_is_rt;
		}
		public void setT_is_rt(String t_is_rt) {
			this.t_is_rt = t_is_rt;
		}
		public String getT_followers() {
			return t_followers;
		}
		public void setT_followers(String t_followers) {
			this.t_followers = t_followers;
		}
		public String getT_following() {
			return t_following;
		}
		public void setT_following(String t_following) {
			this.t_following = t_following;
		}
		public String getT_tweet() {
			return t_tweet;
		}
		public void setT_tweet(String t_tweet) {
			this.t_tweet = t_tweet;
		}
		public String getT_source() {
			return t_source;
		}
		public void setT_source(String t_source) {
			this.t_source = t_source;
		}
		public String getT_name() {
			return t_name;
		}
		public void setT_name(String t_name) {
			this.t_name = t_name;
		}
		public String getT_profile_image() {
			return t_profile_image;
		}
		public void setT_profile_image(String t_profile_image) {
			this.t_profile_image = t_profile_image;
		}      
		
	}
	
	public static class PioneerBean {
		
		String id_seq = "";
		String p_user_id = "";
		String p_name = "";
		String p_title = "";
		String p_rt_cnt = "";
		
		public String getId_seq() {
			return id_seq;
		}
		public void setId_seq(String id_seq) {
			this.id_seq = id_seq;
		}
		public String getP_user_id() {
			return p_user_id;
		}
		public void setP_user_id(String p_user_id) {
			this.p_user_id = p_user_id;
		}
		public String getP_name() {
			return p_name;
		}
		public void setP_name(String p_name) {
			this.p_name = p_name;
		}
		public String getP_title() {
			return p_title;
		}
		public void setP_title(String p_title) {
			this.p_title = p_title;
		}
		public String getP_rt_cnt() {
			return p_rt_cnt;
		}
		public void setP_rt_cnt(String p_rt_cnt) {
			this.p_rt_cnt = p_rt_cnt;
		}
	}
	
	public static String TagClean(String s)
	 {
	  if (s == null)
	  {
	   return null;
	  }
	  Matcher m;
	  
	  m = Patterns.SCRIPTS.matcher(s);
	  s = m.replaceAll("");
	  m = Patterns.STYLE.matcher(s);
	  s = m.replaceAll("");
	  m = Patterns.TAGS.matcher(s);
	  s = m.replaceAll("");
	  m = Patterns.ENTITY_REFS.matcher(s);
	  s = m.replaceAll("");
	  m = Patterns.WHITESPACE.matcher(s);
	  s = m.replaceAll(" ");
	  
	  return s;
	 }

	public static interface Patterns
	{
	 // javascript tags and everything in between
	 public static final Pattern SCRIPTS = Pattern.compile(
	   "<(no)?script[^>]*>.*?</(no)?script>",
	   Pattern.DOTALL);
	 public static final Pattern STYLE = Pattern.compile(
	   "<style[^>]*>.*</style>",
	   Pattern.DOTALL);
	 // HTML/XML tags
	 public static final Pattern TAGS = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");
	 public static final Pattern nTAGS = Pattern.compile("<\\w+\\s+[^<]*\\s*>");
	 // entity references
	 public static final Pattern ENTITY_REFS = Pattern.compile("&[^;]+;");
	 // repeated whitespace
	 public static final Pattern WHITESPACE = Pattern.compile("\\s\\s+");
	 
	}
}


