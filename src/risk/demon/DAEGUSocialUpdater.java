package risk.demon;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;

public class DAEGUSocialUpdater {
	
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
        	//	Facebook
        	
        	// 오늘 데이터가 없으면...
        	if(!checkYesterday(today, "Facebook")){
        		// 가장 최근 데이터 가지고 와서 입력.
        		insertSocialData(today,  "Facebook");
        	}
        	
        	// 오늘 데이터가 없으면...
        	if(!checkYesterday(today, "Facebook2")){
        		// 가장 최근 데이터 가지고 와서 입력.
        		insertSocialData(today,  "Facebook2");
        	}
        	
        	////////////////////////////////////////////////
        	//	Tweet
        	
        	// 오늘 데이터가 없으면...
        	if(!checkYesterday(today, "Tweet")){
        		// 가장 최근 데이터 가지고 와서 입력.
        		insertSocialData(today,  "Tweet");
        	}        	
        	
        	// 오늘 데이터가 없으면...
        	if(!checkYesterday(today, "Tweet2")){
        		// 가장 최근 데이터 가지고 와서 입력.
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
			if(type.equals("Facebook")){
				sb.append(" SELECT * FROM SOCIAL_FB WHERE SSF_DATE = '"+yesterday+"' ORDER BY SSF_TIME DESC LIMIT 1	\n");
			}else if(type.equals("Facebook2")){
				sb.append(" SELECT * FROM SOCIAL_FB2 WHERE SSF_DATE = '"+yesterday+"' ORDER BY SSF_TIME DESC LIMIT 1	\n");	
			}else if(type.equals("Tweet")){
				sb.append(" SELECT * FROM SOCIAL_TW WHERE SST_DATE = '"+yesterday+"' ORDER BY SST_TIME DESC LIMIT 1	\n");
			}else if(type.equals("Tweet2")){
				sb.append(" SELECT * FROM SOCIAL_TW2 WHERE SST_DATE = '"+yesterday+"' ORDER BY SST_TIME DESC LIMIT 1	\n");
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
        String data4 = "";
        String data5 = "";
        
        DateUtil du = new DateUtil();
        
		try{
			
			sb = new StringBuffer();
			if(type.equals("Facebook")){
				sb.append(" SELECT * FROM SOCIAL_FB WHERE SSF_DATE < '"+today+"' ORDER BY SSF_DATE DESC , SSF_TIME DESC LIMIT 1	\n");
			}else if(type.equals("Facebook2")){
				sb.append(" SELECT * FROM SOCIAL_FB2 WHERE SSF_DATE < '"+today+"' ORDER BY SSF_DATE DESC , SSF_TIME DESC LIMIT 1	\n");
			}else if(type.equals("Tweet")){
				sb.append(" SELECT * FROM SOCIAL_TW WHERE SST_DATE < '"+today+"' ORDER BY SST_DATE DESC , SST_TIME DESC LIMIT 1	\n");
			}else if(type.equals("Tweet2")){
				sb.append(" SELECT * FROM SOCIAL_TW2 WHERE SST_DATE < '"+today+"' ORDER BY SST_DATE DESC , SST_TIME DESC LIMIT 1	\n");
			}
            
			System.out.println(sb.toString());
			stmt = dbconn1.createStatement();
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next()){
				if(type.equals("Facebook")){
					date = rs.getString("SSF_DATE");
					data1 = rs.getString("SSF_FAN_CNT");
					data2 = rs.getString("SSF_FRD_CNT");
					data3 = rs.getString("SSF_TIME");
					data4 = rs.getString("SSF_STANDARD_DATE");
					data5 = rs.getString("SSF_STANDARD_TIME");
				}else if(type.equals("Facebook2")){
					date = rs.getString("SSF_DATE");
					data1 = rs.getString("SSF_FAN_CNT");
					data2 = rs.getString("SSF_FRD_CNT");
					data3 = rs.getString("SSF_TIME");
					data4 = rs.getString("SSF_STANDARD_DATE");
					data5 = rs.getString("SSF_STANDARD_TIME");
				}else if(type.equals("Tweet")){
					date = rs.getString("SST_DATE");
					data1 = rs.getString("SST_FOLLOWER");
					data2 = rs.getString("SST_FOLLOWING");
					data3 = rs.getString("SST_TIME");
					data4 = rs.getString("SST_STANDARD_DATE");
					data5 = rs.getString("SST_STANDARD_TIME");
				}else if(type.equals("Tweet2")){
					date = rs.getString("SST_DATE");
					data1 = rs.getString("SST_FOLLOWER");
					data2 = rs.getString("SST_FOLLOWING");
					data3 = rs.getString("SST_TIME");
					data4 = rs.getString("SST_STANDARD_DATE");
					data5 = rs.getString("SST_STANDARD_TIME");
				}
			}
			
			
			
			if(!date.equals("")){
				int timeData[] = new int[]{8,13,17}; 
				for(int i =0; i < timeData.length; i++){
					if(type.equals("Facebook")){
						sb = new StringBuffer();
						sb.append(" INSERT INTO SOCIAL_FB (SSF_DATE, SSF_FAN_CNT, SSF_FRD_CNT, SSF_TIME, SSF_STANDARD_DATE, SSF_STANDARD_TIME)	\n"); 
						sb.append(" VALUES ('"+today+"', "+data1+", "+data2+", "+timeData[i]+", '"+data4+"', "+data5+")	\n");
						System.out.println(sb.toString());
						stmt = dbconn1.createStatement();
						stmt.executeUpdate(sb.toString());
					}else if(type.equals("Facebook2")){
						sb = new StringBuffer();
						sb.append(" INSERT INTO SOCIAL_FB2 (SSF_DATE, SSF_FAN_CNT, SSF_FRD_CNT, SSF_TIME, SSF_STANDARD_DATE, SSF_STANDARD_TIME)	\n"); 
						sb.append(" VALUES ('"+today+"', "+data1+", "+data2+", "+timeData[i]+", '"+data4+"', "+data5+")	\n");
						System.out.println(sb.toString());
						stmt = dbconn1.createStatement();
						stmt.executeUpdate(sb.toString());
					}else if(type.equals("Tweet")){
						sb = new StringBuffer();
						sb.append(" INSERT INTO SOCIAL_TW (SST_DATE, SST_FOLLOWER, SST_FOLLOWING, SST_TIME, SST_STANDARD_DATE, SST_STANDARD_TIME)	\n"); 
						sb.append(" VALUES ('"+today+"', "+data1+", "+data2+", "+timeData[i]+", '"+data4+"', "+data5+")	\n");
						System.out.println(sb.toString());
						stmt = dbconn1.createStatement();
						stmt.executeUpdate(sb.toString());
					}else if(type.equals("Tweet2")){
						sb = new StringBuffer();
						sb.append(" INSERT INTO SOCIAL_TW2 (SST_DATE, SST_FOLLOWER, SST_FOLLOWING, SST_TIME, SST_STANDARD_DATE, SST_STANDARD_TIME)	\n"); 
						sb.append(" VALUES ('"+today+"', "+data1+", "+data2+", "+timeData[i]+", '"+data4+"', "+data5+")	\n");
						System.out.println(sb.toString());
						stmt = dbconn1.createStatement();
						stmt.executeUpdate(sb.toString());
					}
					
				}
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

