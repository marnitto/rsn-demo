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

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.util.ConfigUtil;
import risk.util.DateUtil;
import risk.util.Log;

public class DAEGUTopMigrationData{ 
	
	/**
     * MigrationData main 
     * @return
     */
	
	static String apiUrl = "http://api1.realsn.com:2025/json.php";
	static String siteNum = "6050,6051,6052,6057";
	static String name = "TopMigrationData";
	
    public static void main( String[] args )
    {
    	long diffSecond ;
    	long runTime1;
    	long runTime2;
    	int processNum = 0;
    	String transferNo = null;    	
    	String lastTransNum = null;
    	String tempNum = null;
    	int endNum[] = new int[2];
    	
    	ArrayList contentList = null;
    	ArrayList siteList = null;    
    	ArrayList keyword = null;
    	ArrayList exKeyword = null;
    	ArrayList AllExKeyword = null;
    	ConfigUtil cu = new ConfigUtil();
    	
    	Log.crond("PortalMigration START ...");    	    	
        try{
        	
        	DateUtil du = new DateUtil();
    		String today = du.getCurrentDate("yyyyMMdd");
    		
    		String top_date = "";
        	
        	dbconn1 = new DBconn();
        	dbconn1.getSubDirectConnection();
        	
        	// 최근 전송했던 번호
        	transferNo = cu.getConfig("SECTIONDATA_TOP");	    	
        	lastTransNum = getLastTransNum( transferNo );
        	Log.crond("TRANSFER NO :"+ lastTransNum);
        	
        	//설정 사이트 로딩(진솔)
        	//siteList = getSite();

        	//키워드 로딩
        	keyword = getKeyword(1);
        	exKeyword = getKeyword(2);
        	//AllExKeyword = getKeyword(3);  진솔 : 제거
        	
        	//전송된 번호 다음부터 	
        	tempNum = lastTransNum;
        	top_date = lastUpdateTime(transferNo);

        	while(true)
        	{ 
        		//인덱싱할 게시물 100건씩 로딩
        		contentList = new ArrayList();
        		contentList = getContents(top_date, tempNum, transferNo);
        		
        		if(contentList.size()>0){
        			runTime1 = System.currentTimeMillis();        			       			
        			endNum = executeIndexing(contentList, keyword, exKeyword);
        		 
        			if(endNum[0]>0){            		            	
		            	// TRANSFER 테이블에 마지막 전송값 입력
        				updateLastTransNum( transferNo, endNum );
		            	tempNum = String.valueOf(endNum[0]);
	            	}
        			
        			runTime2 = System.currentTimeMillis();
        			diffSecond = runTime2 - runTime1;        			
        			processNum ++;
        			
            	}else{
            		break;
            	}
        	}
        }catch(Exception ex) {
        	Log.crond(ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        }finally {
            if (dbconn1 != null) try { dbconn1.close(); } catch(SQLException ex) {}
        }
        Log.crond("Migration END ...");
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

			//System.out.println( sb.toString() );
			stmt = dbconn1.createStatement();
			rs = stmt.executeQuery( sb.toString() );

			if( rs.next() )
			{
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
     * 최근 기사 번호
     * @return
     * @author Lim Seung Chul
     */
    static String getMaxNum()
    {
        String result = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;       

		try{
            sb = new StringBuffer();
            sb.append(" SELECT T_SEQ FROM TOP ORDER BY T_SEQ DESC LIMIT 1 \n");

			//System.out.println( sb.toString() );
			stmt = dbconn1.createStatement();
			rs = stmt.executeQuery( sb.toString() );

			if( rs.next() )
			{
				result = rs.getString("T_SEQ");
			}

		} catch(SQLException ex) {
			Log.crond(ex.toString());
        } catch(Exception ex) {
        	Log.crond(ex.toString());
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

			//System.out.println( sb.toString() );
			stmt = dbconn1.createStatement();
			rs = stmt.executeQuery( sb.toString() );

			if( rs.next() )
			{
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

    static boolean updateLastTransNum( String transferNo, int lastTransNum[] )
    {
        boolean result = false;

        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;

		try{
            sb = new StringBuffer();
            sb.append(" UPDATE SYSTEM_TRANSFER SET ST_LAST_SEQ="+lastTransNum[0]+", ST_CRAWL_STAMP="+lastTransNum[1]+", ST_UPDATE_STAMP = UNIX_TIMESTAMP() WHERE ST_API_KEY='"+transferNo+"' \n");

			//System.out.println( sb.toString() );
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
    
    static String lastUpdateTime(String api_key){
    	String result = null;
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	stmt = dbconn1.createStatement();
        	sb = new StringBuffer();
            sb.append(" SELECT FROM_UNIXTIME(ST_CRAWL_STAMP,'%Y%m%d') AS UPDATE_DATE FROM SYSTEM_TRANSFER WHERE ST_API_KEY = '"+api_key+"'; 	\n");
            
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
    
    static ArrayList getContents(String date, String startNum, String acessToken)
    {       
    	JSONObject json = null;
    	String[][] result = null;
    	String returnData = ""; 
    	
        String param = "";
    	param += "api_key=" + acessToken;
    	param += "&max_seq=" + startNum;
    	param += "&search_day=" + date;
    	param += "&s_seq=" + siteNum;
    	param += "&list_count=" + "500";
    	
    	ArrayList arrContents = new ArrayList();
        int d_seq[]  = null;
		String d_title[]  = null;
		String d_contents[] = null;
		String d_site[] = null;
		String d_board[] = null;
		int d_stamp[] = null;
		String d_datetime[] = null;
		String d_url[] = null;
		int s_seq[] = null;
		int sb_seq[] = null;
		int if_seq[] = null;
        
        try{
        	Log.crond("[SEND]" + apiUrl+ "?" +  param);
        	returnData = GetHtmlPost(apiUrl, param);
        	Log.crond("[RECEIVE]");
        	
        	json = new JSONObject(returnData);
        	JSONArray jAr = new JSONArray();
        	JSONObject row_json = null;
        	
        	if(!json.isNull("data")){
        		jAr = json.getJSONArray("data");
        		
        		int idx = jAr.length();
        		d_seq = new int[idx];
    			d_title = new String[idx];
    			d_contents  = new String[idx];
    			d_site = new String[idx];
    			d_board = new String[idx];
    			d_stamp = new int[idx];
    			d_datetime = new String[idx];
    			d_url = new String[idx];			
    			s_seq = new int[idx];			
    			sb_seq = new int[idx];
    			if_seq = new int[idx];
        		
        		for(int i=0; i< idx; i++){
        			row_json = jAr.getJSONObject(i);
        			
        			if(!row_json.isNull("SECTION.DATA.Seq")){	
        				d_seq[i] = row_json.getInt("SECTION.DATA.Seq");
        			}
        			
        			if(!row_json.isNull("SECTION.DATA.Title")){	
        				if(row_json.getString("SECTION.DATA.Title").length()>256){
        					d_title[i] = row_json.getString("SECTION.DATA.Title").substring(0, 256);
        				}else{
        					d_title[i] = row_json.getString("SECTION.DATA.Title");
        				}
        				
        			}
    				
        			if(!row_json.isNull("SECTION.DATA.Content")){	
        				d_contents[i] = row_json.getString("SECTION.DATA.Content");
        			}
        			
        			if(!row_json.isNull("COMMON.SITE.Name")){	
        				if(row_json.getString("COMMON.SITE.Name").length()>48){
        					d_site[i]=row_json.getString("COMMON.SITE.Name").substring(0, 48);
        				}else{
        					d_site[i]=row_json.getString("COMMON.SITE.Name");
        				}
        			}
        			
        			if(!row_json.isNull("SECTION.BOARD.Name")){	
        				if(row_json.getString("SECTION.BOARD.Name").length()>48){
        					d_board[i]=row_json.getString("SECTION.BOARD.Name").substring(0, 48);
        				}else{
        					d_board[i]=row_json.getString("SECTION.BOARD.Name");
        				}
        			}
    				    				
    				if(!row_json.isNull("SECTION.DATA.CrawlStamp")){	
    					d_stamp[i] = row_json.getInt("SECTION.DATA.CrawlStamp");
        			}
    				
        			if(!row_json.isNull("SECTION.DATA.CrawlStamp")){	
        				d_datetime[i] = stampToDateTime(row_json.getString("SECTION.DATA.CrawlStamp"), "yyyy-MM-dd HH:mm:ss");
        			}
        			
        			if(!row_json.isNull("SECTION.DATA.Link")){	
        				if(row_json.getString("SECTION.DATA.Link").length()>256){
        					d_url[i]=row_json.getString("SECTION.DATA.Link").substring(0, 256);
        				}else{
        					d_url[i]=row_json.getString("SECTION.DATA.Link");
        				}
        			}		
    				
    				if(!row_json.isNull("COMMON.SITE.Seq")){	
    					s_seq[i] = row_json.getInt("COMMON.SITE.Seq");
        			}
    				 
    				if(!row_json.isNull("SECTION.BOARD.Seq")){	
    					sb_seq[i] = row_json.getInt("SECTION.BOARD.Seq");
        			}
        		}
        		
        		if(idx>0){
    				arrContents.add(d_seq);
    				arrContents.add(d_title);
    				arrContents.add(d_contents);
    				arrContents.add(d_site);
    				arrContents.add(d_board);
    				arrContents.add(d_stamp);
    				arrContents.add(d_datetime);
    				arrContents.add(d_url);
    				arrContents.add(s_seq);
    				arrContents.add(sb_seq);
    				arrContents.add(if_seq);		
    			}		
        		
        	}else if(!json.isNull("error")){
        		Log.writeExpt(name,json.getJSONObject("error").getString("message"));
        		System.exit(1);
        	}
        	
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
        } 
        
        return arrContents;
    }
    
    /**
     * 일반 및 제외 키워드를 로딩한다.
     * @param type     
     * @author Lim Seung Chul
     */
    static ArrayList getKeyword(int type)
    {       
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
    	ArrayList arrKeyword = new ArrayList();
        int count = 0;
        int xp[]  = null;
		int yp[]  = null;
        int zp[]  = null;
        int op[] = null;
        String value[] = null;

		try{
			
			sb = new StringBuffer();	            
            if(type == 1){
            	//SELECT COUNT(1) FROM IMS_TOP_KEYWORD WHERE T_TYPE=2
	            sb.append(" SELECT COUNT(*) FROM TOP_KEYWORD \n");
	            sb.append(" WHERE T_TYPE = 2    \n");
            }else if(type == 2){
	            sb.append(" SELECT COUNT(*) FROM TOP_KEYWORD \n");
	            sb.append(" WHERE T_TYPE >= 11  \n");	
            }else if(type == 3){
            	//sb.append(" SELECT COUNT(*) FROM EXCEPTION_KEYWORD \n"); 	          
            }
			
			stmt = dbconn1.createStatement();
			rs = stmt.executeQuery(sb.toString());
			if(rs.next()){
				count = rs.getInt(1);
			}
			if(count>0){
				xp = new int[count];
				yp = new int[count];
				zp = new int[count];
				op = new int[count];
				value = new String[count];
			}
			
			rs.close();
			stmt.close();
			rs = null;
			stmt = null;
            sb = new StringBuffer();            
            if(type == 1){
            	//SELECT T_XP,T_YP,T_ZP,T_OP,T_VALUE FROM TOP_KEYWORD WHERE T_TYPE=2 
            	sb.append(" SELECT T_XP, T_YP, T_ZP, T_OP, T_VALUE FROM TOP_KEYWORD \n");
            	sb.append(" WHERE T_TYPE = 2  \n");
            }else if(type == 2){
            	//SELECT T_XP, T_YP, T_ZP, T_VALUE FROM TOP_KEYWORD WHERE T_TYPE >= 11
            	sb.append(" SELECT T_XP, T_YP, T_ZP, T_OP, T_VALUE FROM TOP_KEYWORD \n");
            	sb.append(" WHERE T_TYPE >= 11  \n");	
            }else if(type == 3){
            	//sb.append(" SELECT EK_VALUE FROM EXCEPTION_KEYWORD \n");            	
            }
            
            count = 0;			
			stmt = dbconn1.createStatement();
			rs = stmt.executeQuery(sb.toString());
			

			while( rs.next() ){				
				xp[count]=rs.getInt("T_XP");
				yp[count]=rs.getInt("T_YP");
				zp[count]=rs.getInt("T_ZP");
				op[count]=rs.getInt("T_OP");
				value[count]=rs.getString("T_VALUE").toLowerCase();				
				count++;
			}
			
			if(count>0){
				arrKeyword.add(xp);
				arrKeyword.add(yp);
				arrKeyword.add(zp);
				arrKeyword.add(op);
				arrKeyword.add(value);
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
    	
    	return arrKeyword;
    }
    
    /**
     * SG_S_RELATION 테이블에 매핑된 사이트리스트를 가져온다.
     * @author Lim Seung Chul
     */
    static ArrayList getSite()
    {       
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
    	ArrayList arrSite = new ArrayList();
        int count = 0;
        int sg_seq[]  = null;
		int s_seq[]  = null;
		try{
			
			sb = new StringBuffer();			
            sb.append(" SELECT COUNT(*) FROM SG_S_RELATION \n");
           
			stmt = dbconn1.createStatement();
			rs = stmt.executeQuery(sb.toString());
			if(rs.next()){
				count = rs.getInt(1);
			}
			if(count>0){
				sg_seq = new int[count];
				s_seq = new int[count];
			}			
			
			rs.close();
			stmt.close();
			rs = null;
			stmt = null;
            sb = new StringBuffer();
            sb.append(" SELECT SG_SEQ, S_SEQ FROM SG_S_RELATION \n");
                       
            count = 0;			
			stmt = dbconn1.createStatement();
			rs = stmt.executeQuery(sb.toString());
			
			while( rs.next() ){				
				sg_seq[count]=rs.getInt("SG_SEQ");
				s_seq[count]=rs.getInt("S_SEQ");								
				count++;
			}
			if(count>0){
				arrSite.add(sg_seq);
				arrSite.add(s_seq);		
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
    	
    	return arrSite;
    }
    
    /**
     * 로컬 DB에 신규 게시물을 입력
     * @param articleBean
     * @return
     */
    static boolean compareCheck(ArrayList articleBean)
    {
	
		boolean result = false;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		String t_seq = null;
		
		try{
			
		    sb = new StringBuffer();            
		    sb.append("SELECT T_SEQ     \n");                
			sb.append("FROM TOP	                     \n"); 
			sb.append("WHERE T_URL = '"+(String)articleBean.get(7)+"'                \n");
			stmt = dbconn1.createStatement();
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				result = true;
				t_seq = rs.getString("T_SEQ");
			}			
			     
			sb = new StringBuffer();            
			sb.append("UPDATE TOP    \n");                
			sb.append("SET T_PRESENTTIME = '"+(String)articleBean.get(6)+"'                     \n"); 
			sb.append("WHERE T_SEQ = "+t_seq+"                \n");
			stmt = dbconn1.createStatement();              
			if(result){stmt.executeUpdate(sb.toString());}
	                               
		} catch(SQLException ex) {
			Log.crond(ex.toString());
			System.exit(1);
        } catch(Exception ex) {
        	Log.crond(ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}      
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}       
        }
        
        return result;
       
    }
    
    /**
     * 로컬 DB에 신규 게시물을 입력
     * @param articleBean
     * @return
     */
    static void insertTop( ArrayList articleBean)
    {
    
        PreparedStatement pstmt = null;
        PreparedStatement pstmt_data = null;
        StringBuffer sb = null;
        
		try{
			
            sb = new StringBuffer();            
            sb.append("INSERT INTO TOP(      \n");	      
            sb.append(" T_SEQ	             \n");
            sb.append(",T_SITE	             \n");
            sb.append(",T_BOARD	             \n");
            sb.append(",T_DATETIME	                     \n");
            sb.append(",T_TITLE                     \n");
            sb.append(",T_URL	                     \n");
            sb.append(",S_SEQ	                  \n");          
            sb.append(",SB_SEQ)	                  \n");
            sb.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?)		\n");             
           
            pstmt = dbconn1.createPStatement(sb.toString());          
           
            int paNum = 1;                         	
        	pstmt.clearParameters();
        	pstmt.setString(paNum++, (String)articleBean.get(0));
        	pstmt.setString(paNum++, (String)articleBean.get(3));
        	pstmt.setString(paNum++, (String)articleBean.get(4));
        	pstmt.setString(paNum++, (String)articleBean.get(6));
        	pstmt.setString(paNum++, (String)articleBean.get(1));
        	pstmt.setString(paNum++, (String)articleBean.get(7));
        	pstmt.setString(paNum++, (String)articleBean.get(8));
        	pstmt.setString(paNum++, (String)articleBean.get(9));
        	
        	pstmt.execute();
                   
		} catch(SQLException ex) {
			Log.crond(ex.toString());
			System.exit(1);
        } catch(Exception ex) {
        	Log.crond(ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (pstmt_data != null) try { pstmt_data.close(); } catch(SQLException ex) {}
        }
       
    }
    
    /**
     * 로컬 DB에 신규 게시물을 입력
     * @param articleBean
     * @return
     */
    static boolean insertIndex( ArrayList articleBean )
    {
    	boolean result = false;
        Statement stmt = null;
        StringBuffer sb = null;

        if( articleBean == null ) return result;
		try{
			//INSERT INTO TOP_IDX (T_SEQ, K_XP, K_YP, K_ZP) VALUES ()
            sb = new StringBuffer();
            sb.append(" INSERT INTO TOP_IDX (T_SEQ, K_XP, K_YP, K_ZP,ISSUE_CHECK ) \n");
            sb.append(" VALUES (  \n");
            sb.append(" 		'"+(String)articleBean.get(0)+"'\n");
            sb.append("       , '"+(String)articleBean.get(1)+"'\n");
            sb.append("       , '"+(String)articleBean.get(2)+"'\n");
            sb.append("       , '"+(String)articleBean.get(3)+"'\n");
            sb.append("       , 'N'\n");
            sb.append("        ) \n");
                 
			stmt = dbconn1.createStatement();
			if( stmt.executeUpdate( sb.toString() ) > 0 ) result = true;

		} catch(SQLException ex) {
			Log.crond(ex.toString());
        } catch(Exception ex) {
        	Log.crond(ex.toString());
        } finally {
            sb = null;
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
        }
        
        return result;
    }
    
    
    /**
     * 데이터를 Indexing 한다.(사이트검사,제외키워드 검사, 키워드 검사순 키워드검사는 near,구문 검색을 기본으로 한다.)
     * @param contentList    
     * @param site    
     * @param keyword    
     * @param exKeyword  
     * @author Lim Seung Chul
     */
    static int[] executeIndexing(ArrayList contentList, ArrayList keyword, ArrayList exKeyword)
    {    	
    	int endNum [] = new int[2];
    	boolean result = false;    	
    	boolean exResult = false;   
    	boolean dataChk = false;
    	
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        String regexpStr = "[',|@#$%\\^&*\\s\\[\\](){}‘’<>\"+-./:=?_`´~§±·]";
        
       //META 관련
        int d_seq[]  = null;
		String d_title[]  = null;
		String d_contents[] = null;
		String d_site[] = null;
		String d_board[] = null;
		int d_stamp[] = null;
		String d_datetime[] = null;
		String d_url[] = null;
		int s_seq[] = null;
		int sb_seq[] = null;
		int if_seq[] = null;
      
		//KEYWORD 관련
		//String allExValue[] = null;			
        int xp[]  = null;
		int yp[]  = null;
        int zp[]  = null;
        int op[] = null;
        String value[]  = null;        
        int exXp[] = null;
        int exYp[] = null;
        int exZp[] = null;
        int exOp[] = null;
        String exValue[] = null;        
                   
		try{
			ArrayList dataInfo = null;
			ArrayList idxInfo = null;
			String html = null;
			String sitegroupNum = null;
				
			//문서 배열
			if(contentList.size()>0){
				d_seq = (int[])contentList.get(0);	
				d_title = (String[])contentList.get(1);
				d_contents = (String[])contentList.get(2);				
				d_site = (String[])contentList.get(3);
				d_board = (String[])contentList.get(4);
				d_stamp = (int[])contentList.get(5);
				d_datetime = (String[])contentList.get(6);
				d_url = (String[])contentList.get(7);
				s_seq = (int[])contentList.get(8);
				sb_seq = (int[])contentList.get(9);
				if_seq = (int[])contentList.get(10);	
			}
			
			//사이트 정보 배열 진솔
			//if(site.size()>0){
			//	sg_seq = (int[])site.get(0);
			//	s_seq  = (int[])site.get(1);		       
			//}
			
			//키워드 정보 배열
			if(keyword.size()>0){
				xp = (int[])keyword.get(0);
				yp  = (int[])keyword.get(1);
		        zp  = (int[])keyword.get(2);
		        op = (int[])keyword.get(3);
		        value  = (String[])keyword.get(4);		        
			}
			
			//제외 키워드 정보 배열
			if(exKeyword.size()>0){
		        exXp = (int[])exKeyword.get(0);
		        exYp = (int[])exKeyword.get(1);
		        exZp = (int[])exKeyword.get(2);
		        exOp = (int[])exKeyword.get(3);
		        exValue = (String[])exKeyword.get(4);
			}
			
			//전체 제외 키워드 배열			
			//if(allExKeyword.size()>0){
			//	allExValue = (String[])allExKeyword.get(0);
			//}
			
			//boolean chkEx = false;
			
			for(int loofIndex=0; loofIndex<d_seq.length; loofIndex++)
			{

				dataChk = false;				
							
				//제목+내용 정규식 처리
				html = " "+d_title[loofIndex]+" "+d_contents[loofIndex]+" ";
				/**
				 * 2011.05.23 이경환
				 * 특수문자 정규식으로 공백처리 
				 */
				html = html.toLowerCase().replaceAll(regexpStr, " ").replaceAll("\\s{1,}"," ");

				dataInfo = new ArrayList();
				dataInfo.add(String.valueOf(d_seq[loofIndex]));
				dataInfo.add(String.valueOf(d_title[loofIndex]));
				dataInfo.add(String.valueOf(html));
				dataInfo.add(d_site[loofIndex]);
				dataInfo.add(d_board[loofIndex]);
				dataInfo.add(String.valueOf(d_stamp[loofIndex]));
				dataInfo.add(String.valueOf(d_datetime[loofIndex]));
				dataInfo.add(String.valueOf(d_url[loofIndex]));				
				dataInfo.add(String.valueOf(s_seq[loofIndex])); 
				dataInfo.add(String.valueOf(sb_seq[loofIndex]));					
				dataInfo.add(String.valueOf(if_seq[loofIndex]));	
				
				/**
				 * 2011.02.25 이경환 로직 수정
				 * 키워드 만큼 돌면서 제외 처리 제외 처리 걸리면 다음 키워드로 
				 */		
				
				if(keyword.size()>0){						
					for(int i=0;i<value.length;i++)
					{		

						exResult = true;
						if(exKeyword.size()>0){
							for(int j=0;j<exValue.length;j++)
							{
								if(xp[i]==exXp[j] && yp[i]==exYp[j] && zp[i]==exZp[j]){
									result = searchKeyword(html,exValue[j],1); //일반 인덱싱
									if(result){		
										exResult = false;
										Log.crond("ExKeyword Check ok : "+exXp[j]+" "+exYp[j]+" "+exZp[j]+" "+exValue[j]);
										break;
									}
								}
							}
						}else{
							exResult = true;
						}
						
						//제외 키워드가 걸리지 않으면 키워드 처리
						if(exResult){

							//옵션에 따른 처리 2011.02.28 1일반, 2인접, 3정확, 4고유
							result = searchKeyword(html, value[i], op[i]); //구문, Near검색
							//result = Good_Key(html,value[i],1); //일반 인덱싱
							
							idxInfo = new ArrayList();
							if(result){				
								if(!dataChk) dataChk = true;
									
								Log.crond("Keyword check ok : [ xp:"+xp[i]+"yp:"+yp[i]+"zp:"+zp[i]+"kvalue:"+value[i]+"] ");
								//Log.crond("입력 정보:[ MD_SEQ : "+md_seq[loofIndex]+ " MD_TITLE:"+md_title[loofIndex]+"]");
								idxInfo.add(String.valueOf(d_seq[loofIndex]));	
								idxInfo.add(String.valueOf(xp[i]));
								idxInfo.add(String.valueOf(yp[i]));
								idxInfo.add(String.valueOf(zp[i]));
								//arrTmp.add(sitegroupNum);
								insertIndex(idxInfo);
							}
						}
					}
				}			
				
				
				
				if(dataChk){
					if(!compareCheck(dataInfo)){
						insertTop(dataInfo);
					}
				}

				if(d_seq[loofIndex] == 0){
					break;
				}else{
					
					endNum[0] = d_seq[loofIndex];
					endNum[1] = d_stamp[loofIndex];
				}
			}
			
			
					
		}catch(Exception ex) {        	
        	Log.crond(ex.toString());
        	ex.printStackTrace();
        }
        return endNum;
    }
    
    
    	
    /**
	 * <p> key[0] 이 노출된 지점에서 앞뒤로 iNearByte(byte) 이내에 key[n]이 모두 존재 하는지 검사한다.</p>
	 * 
	 * @author Ryu Seung Wan
	 */
    static boolean nearSearch(String strHtml, String[] key, int iNearByte){  	
   	    	    	    	
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
						frontHtml = (tempIndex1 - iNearByte) > 0 ? 
								strHtml.substring(tempIndex1 - iNearByte, tempIndex1) : 
								strHtml.substring(0, tempIndex1); 
	
						
						// 키의 위치로 부터 뒷쪽으로 (키.length + iNearByte) 만큼 자른다.
						backHtml = strHtml.length() - (key[0].length() + iNearByte + 1) > tempIndex1 ? 
								strHtml.substring(tempIndex1, tempIndex1 + key[0].length() + iNearByte) : 
								strHtml.substring(tempIndex1, strHtml.length());
	    				
						
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
    
    /**
	 * <p> 구문 키워드를 찾아 키워드를 재배열한다.</p>
	 * 
	 * @author Lim Seung Chul
	 */
    static String[] searchGumunKey(String[] key)
    {
    	int indexCnt = 0;
		int firstWordIndex = 0;
		int endWordIndex = 0;
		int gumunCount = 0;
		
		String notGumnunKey = "";
		String gumnunKeyword = "";
		String[] tempKey = null;
		String[] lastKey = null;
	
		
		//구문 인덱스 범위를 찾는다.
		for(int i =0 ; i<key.length ; i++){
			notGumnunKey += notGumnunKey.equals("") ? key[i] : " "+ key[i];
			
			if(key[i].indexOf("\"")>-1)
			{
					if(indexCnt==0) firstWordIndex = i;					
					if(indexCnt>0) endWordIndex = i;	
					indexCnt++;
			}			
		}
		
		if(endWordIndex>0)
		{
			gumunCount = endWordIndex -	firstWordIndex;	
			
			for(int i = firstWordIndex ; i<gumunCount+1 ; i++){
				
				gumnunKeyword += gumnunKeyword.equals("") ? key[i] : " "+ key[i];	
			}
			
			//구문 제외한 키워드
			notGumnunKey = notGumnunKey.replaceAll(gumnunKeyword,"");
			notGumnunKey = notGumnunKey.replaceAll("  ", " ");
			
						
			//구문 제외한 키워드 배열
			if(notGumnunKey.equals(" "))tempKey = notGumnunKey.split(" ");			
			if(tempKey!=null)
			{
				lastKey = new String[tempKey.length+1];
				for(int i =0 ; i<tempKey.length ; i++)
				{					
					lastKey[i] = tempKey[i];
				}
				lastKey[tempKey.length] = gumnunKeyword;
		
			}else{
				lastKey = new String[1];
				lastKey[0] = gumnunKeyword;
			}
		}else{
			lastKey = key;
		}
		for(int i =0 ; i<lastKey.length ; i++){
			lastKey[i] = lastKey[i].replaceAll("\"","");
		}
		
		
    	return lastKey;
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
     * 옵션별 키워드 처리
     * @param html
     * @param word
     * @param op
     * @return
     */
    static boolean searchKeyword(String html, String word, int op){
		boolean result = false;
		
		String[] key = word.split(" ");
		String sKey = "";
		//일반
		if(op==1){
			for(int i=0; i<key.length; i++)
		     {
		     	sKey = " "+key[i];
		     	int check = html.indexOf(sKey);
		     	if ( check < 0 )
		         {
		     		return false;
		         }
		     }
		     return true;
		//인접	
		}else if(op==2){
			return nearSearch(html, key, 100);
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
}
