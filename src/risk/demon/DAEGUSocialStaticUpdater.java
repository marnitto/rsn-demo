package risk.demon;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;

public class DAEGUSocialStaticUpdater {
	
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
    	
    	DateUtil du = new DateUtil();
    	
    	String today = du.getCurrentDate();
    	du.addDay(-1);
    	String yesterday = du.getDate(); 
    	
    	Log.crond("Migration START ...");    	    	
        try{
        	
        	dbconn1 = new DBconn(); //로컬디비
        	
        	dbconn1.getSubDirectConnection();
        	runTime1 =  System.currentTimeMillis();   
        	
        	////////////////////////////////////////////////
        	//	Blog
        	
        	// 전날 데이터가 없으면...
        	if(!checkYesterday(yesterday, "Blog")){
        		// 가장 최근 데이터 가지고 와서 없는 날 모두 입력.
        		insertSocialData(today,  "Blog");
        	}
        	
        	////////////////////////////////////////////////
        	//	Facebook
        	
        	// 전날 데이터가 없으면...
        	if(!checkYesterday(yesterday, "Facebook")){
        		// 가장 최근 데이터 가지고 와서 없는 날 모두 입력.
        		insertSocialData(today,  "Facebook");
        	}
        	
        	// 전날 데이터가 없으면...
        	if(!checkYesterday(yesterday, "Facebook2")){
        		// 가장 최근 데이터 가지고 와서 없는 날 모두 입력.
        		insertSocialData(today,  "Facebook2");
        	}
        	
        	////////////////////////////////////////////////
        	//	Tweet
        	
        	// 전날 데이터가 없으면...
        	if(!checkYesterday(yesterday, "Tweet")){
        		// 가장 최근 데이터 가지고 와서 없는 날 모두 입력.
        		insertSocialData(today,  "Tweet");
        	}        	
        	
        	// 전날 데이터가 없으면...
        	if(!checkYesterday(yesterday, "Tweet2")){
        		// 가장 최근 데이터 가지고 와서 없는 날 모두 입력.
        		insertSocialData(today,  "Tweet2");
        	}
        	
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
        Log.crond("Migration END ...");
    }
    
    static boolean checkYesterday(String yesterday, String type) {
	
    	boolean returnVal = false;
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
		try{
			
			sb = new StringBuffer();
			if(type.equals("Blog")){
				sb.append(" SELECT * FROM STATIC_SOCIAL_BLOG WHERE SSB_DATE = '"+yesterday+"'	\n");	
			}else if(type.equals("Facebook")){
				sb.append(" SELECT * FROM STATIC_SOCIAL_FB WHERE SSF_DATE = '"+yesterday+"'	\n");
			}else if(type.equals("Facebook2")){
				sb.append(" SELECT * FROM STATIC_SOCIAL_FB2 WHERE SSF_DATE = '"+yesterday+"'	\n");	
			}else if(type.equals("Tweet")){
				sb.append(" SELECT * FROM STATIC_SOCIAL_TWEET WHERE SST_DATE = '"+yesterday+"'	\n");
			}else if(type.equals("Tweet2")){
				sb.append(" SELECT * FROM STATIC_SOCIAL_TWEET2 WHERE SST_DATE = '"+yesterday+"'	\n");
			}
			
            
			System.out.println(sb.toString());
			stmt = dbconn1.createStatement();
			rs = stmt.executeQuery(sb.toString());
			
			if( rs.next() ){		
				returnVal = true;
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
    	
    	return returnVal;
	}
    
    static void insertSocialData(String today, String type){
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        String date = "";
        String data1 = "";
        String data2 = "";
        String data3 = "";
        
        DateUtil du = new DateUtil();
        du.setDate(today);
        du.addDay(-1);
        String yesterday = du.getDate(); 
        
		try{
			
			sb = new StringBuffer();
			if(type.equals("Blog")){
				sb.append(" SELECT * FROM STATIC_SOCIAL_BLOG WHERE SSB_DATE < '"+yesterday+"' ORDER BY SSB_DATE DESC LIMIT 1	\n");	
			}else if(type.equals("Facebook")){
				sb.append(" SELECT * FROM STATIC_SOCIAL_FB WHERE SSF_DATE < '"+yesterday+"' ORDER BY SSF_DATE DESC LIMIT 1	\n");
			}else if(type.equals("Facebook2")){
				sb.append(" SELECT * FROM STATIC_SOCIAL_FB2 WHERE SSF_DATE < '"+yesterday+"' ORDER BY SSF_DATE DESC LIMIT 1	\n");
			}else if(type.equals("Tweet")){
				sb.append(" SELECT * FROM STATIC_SOCIAL_TWEET WHERE SST_DATE < '"+yesterday+"' ORDER BY SST_DATE DESC LIMIT 1	\n");
			}else if(type.equals("Tweet2")){
				sb.append(" SELECT * FROM STATIC_SOCIAL_TWEET2 WHERE SST_DATE < '"+yesterday+"' ORDER BY SST_DATE DESC LIMIT 1	\n");
			}
            
			System.out.println(sb.toString());
			stmt = dbconn1.createStatement();
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next()){
				if(type.equals("Blog")){
					date = rs.getString("SSB_DATE"); 
					data1 = rs.getString("SSB_DAILY_HIT");
					data2 = rs.getString("SSB_ALL_HIT");
					data3 = rs.getString("SSB_SEARCHKEY");
				}else if(type.equals("Facebook")){
					date = rs.getString("SSF_DATE");
					data1 = rs.getString("SSF_FAN_CNT");
					data2 = rs.getString("SSF_FRD_CNT");
				}else if(type.equals("Facebook2")){
					date = rs.getString("SSF_DATE");
					data1 = rs.getString("SSF_FAN_CNT");
					data2 = rs.getString("SSF_FRD_CNT");	
				}else if(type.equals("Tweet")){
					date = rs.getString("SST_DATE");
					data1 = rs.getString("SST_FOLLOWER");
					data2 = rs.getString("SST_FOLLOWING");	
				}else if(type.equals("Tweet2")){
					date = rs.getString("SST_DATE");
					data1 = rs.getString("SST_FOLLOWER");
					data2 = rs.getString("SST_FOLLOWING");	
				}
			}
			
			du.setDate(date);
			du.addDay(1);
			while(!du.getDate().equals(today)){
				sb = new StringBuffer();
				if(type.equals("Blog")){
					sb.append(" INSERT INTO STATIC_SOCIAL_BLOG (SSB_DATE, SSB_DAILY_HIT, SSB_ALL_HIT, SSB_SEARCHKEY)	\n"); 
					sb.append(" VALUES ('"+du.getDate()+"', "+data1+", "+data2+", '"+data3+"')	\n");
				}else if(type.equals("Facebook")){
					sb.append(" INSERT INTO STATIC_SOCIAL_FB (SSF_DATE, SSF_FAN_CNT, SSF_FRD_CNT)	\n"); 
					sb.append(" VALUES ('"+du.getDate()+"', "+data1+", "+data2+")	\n");
				}else if(type.equals("Facebook2")){
					sb.append(" INSERT INTO STATIC_SOCIAL_FB2 (SSF_DATE, SSF_FAN_CNT, SSF_FRD_CNT)	\n"); 
					sb.append(" VALUES ('"+du.getDate()+"', "+data1+", "+data2+")	\n");	
				}else if(type.equals("Tweet")){
					sb.append(" INSERT INTO STATIC_SOCIAL_TWEET (SST_DATE, SST_FOLLOWER, SST_FOLLOWING)	\n"); 
					sb.append(" VALUES ('"+du.getDate()+"', "+data1+", "+data2+")	\n");
				}else if(type.equals("Tweet2")){
					sb.append(" INSERT INTO STATIC_SOCIAL_TWEET2 (SST_DATE, SST_FOLLOWER, SST_FOLLOWING)	\n"); 
					sb.append(" VALUES ('"+du.getDate()+"', "+data1+", "+data2+")	\n");	
				}
	            
				System.out.println(sb.toString());
				stmt = dbconn1.createStatement();
				stmt.executeUpdate(sb.toString());
				
				du.addDay(1);
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
    }
}
