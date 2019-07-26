package risk.demon;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;

public class DAEGUStaticAnalysis{
	
    static DBconn localDBconn = null;
    static String msMaxNo = null;
    static String msMinNo = null;
    
	public static void main(String[] args) {
		
		long diffSecond ;
    	long runTime1;
    	long runTime2;
		DateUtil du = new DateUtil();
		String Date = null;
		int hour = 0;
		
		runTime1 =  System.currentTimeMillis();   
		Log.crond("ANALYSIS START ...");    	    	
        try{        	
        	localDBconn = new DBconn(); //로컬디비
        	localDBconn.getSubDirectConnection();
        	
        	Date = du.getCurrentDate("yyyy-MM-dd");
        	hour = Integer.parseInt(du.getCurrentDate("HH"));
        	System.out.println("hour:"+hour);       	        	
        	
        	if(hour==1){
        		excuteAnalysisConclusion(Date, -1); //새벽 1시에 전날 마감
        	}
        	excuteAnalysisConclusion(Date,0); //하루 임의로 돌릴때 사용
        	        	
        }catch(Exception ex){
        	Log.crond(ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        }finally{
        	 if (localDBconn != null) try { localDBconn.close(); } catch(SQLException ex) {}
        }
        
        Log.crond("ANALYSIS END ...");    	
	}
	
    static void getMaxMinNo( String psSDate, String psEdate) {
    		
    	ResultSet rs = null;
    	PreparedStatement pstmt = null;
        StringBuffer sb = null;
        DateUtil du = new DateUtil();
        String targetDate = null;
        
        try {      	        	
            sb = new StringBuffer();                                   
            sb.append("SELECT    \n");
            sb.append("	     ( \n");
            sb.append("		 	SELECT MD_SEQ \n");
            sb.append("		 	FROM META \n");
            sb.append("		 	WHERE MD_DATE >= '"+psSDate+" 00:00:00' \n");
            sb.append("		 	ORDER BY MD_DATE ASC \n"); 
            sb.append("		 	LIMIT 1 \n"); 
            sb.append("	    ) AS  MIN_NO, \n");          
            sb.append("		( \n");
            sb.append("		 	SELECT MD_SEQ \n");
            sb.append("		 	FROM META \n");	
            sb.append("		 	WHERE MD_DATE <= '"+psEdate+" 23:59:59' \n");
            sb.append("		 	ORDER BY MD_DATE DESC \n");
            sb.append("		 	LIMIT 1 \n"); 
            sb.append("	    ) AS  MAX_NO \n");        
            
            pstmt =localDBconn.createPStatement(sb.toString());
            rs = pstmt.executeQuery();

            if ( rs.next() ) {
                
                msMaxNo = rs.getString("MAX_NO");
                msMinNo = rs.getString("MIN_NO");
                System.out.println(" msMaxNo = "+ msMaxNo);
                System.out.println(" msMinNo = "+ msMinNo);
            }

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );
        } catch (Exception ex ) {
            Log.writeExpt(ex);
        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( pstmt  != null) pstmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }            
        }
    }
    
    static void excuteAnalysisConclusion(String date, int minusDay){
    	
    	PreparedStatement pstmt = null;
        StringBuffer sb = null;
        DateUtil du = new DateUtil();
        String targetDate = null;
        
        try{
        	
        	targetDate = du.addDay(date,minusDay);
        	
        	getMaxMinNo(targetDate, targetDate);
        	
        	sb = new StringBuffer();
            
            sb.append("  DELETE FROM STATIC_ANALYSIS WHERE MD_DATE BETWEEN '"+targetDate+" 00:00:00' AND '"+targetDate+" 23:59:59'	\n");
            System.out.println(sb.toString());                                                                                                                                   
			pstmt = localDBconn.createPStatement(sb.toString());
			pstmt.executeUpdate();
			pstmt.close();
        	
            sb = new StringBuffer();

            sb.append(" INSERT INTO STATIC_ANALYSIS(MD_DATE, SG_SEQ, K_XP, K_YP, K_ZP, CNT)	\n");
            sb.append(" SELECT MD_DATE, SG_SEQ, K_XP, K_YP, K_ZP,  COUNT(DISTINCT MD_SEQ) AS CNT	\n");                                                        
            sb.append(" FROM                                                                           \n");                                                                                
            sb.append(" (                                                                              \n");                                                                                  
            sb.append("  SELECT  M.MD_SEQ, K_XP, 0 AS K_YP, 0 AS K_ZP, MD_DATE, S.SG_SEQ    			\n");
            sb.append("  FROM                                                                 			\n");                                                                                             
            sb.append("         (SELECT * FROM META WHERE MD_SEQ BETWEEN "+msMinNo+" AND "+msMaxNo+" ) M,  \n");
            sb.append("         IDX I, SG_S_RELATION S													\n");
            sb.append("  WHERE M.MD_SEQ = I.MD_SEQ                                          			\n");
            sb.append("    AND M.S_SEQ = S.S_SEQ											   			\n");
            sb.append(" ) C																			\n");
            sb.append(" GROUP BY MD_DATE, SG_SEQ, K_XP, K_YP, K_ZP                                  	\n");                          
            sb.append(" ORDER BY MD_DATE, K_XP, K_YP, K_ZP      									   	\n");
            
            System.out.println(sb.toString());
			pstmt = localDBconn.createPStatement(sb.toString());
			pstmt.executeUpdate();
			
			sb = new StringBuffer();
            
            sb.append(" INSERT INTO STATIC_ANALYSIS(MD_DATE, SG_SEQ, K_XP, K_YP, K_ZP, CNT)	\n");
            sb.append(" SELECT MD_DATE, SG_SEQ, K_XP, K_YP, K_ZP,  COUNT(DISTINCT MD_SEQ) AS CNT	\n");                                                        
            sb.append(" FROM                                                                           \n");                                                                                
            sb.append(" (                                                                              \n");                                                                                  
            sb.append("  SELECT  M.MD_SEQ, K_XP, K_YP, 0 AS K_ZP, MD_DATE, S.SG_SEQ    			\n");
            sb.append("  FROM                                                                 			\n");                                                                                             
            sb.append("         (SELECT * FROM META WHERE MD_SEQ BETWEEN "+msMinNo+" AND "+msMaxNo+" ) M,  \n");
            sb.append("         IDX I, SG_S_RELATION S													\n");
            sb.append("  WHERE M.MD_SEQ = I.MD_SEQ                                          			\n");
            sb.append("    AND M.S_SEQ = S.S_SEQ											   			\n");
            sb.append(" ) C																			\n");
            sb.append(" GROUP BY MD_DATE, SG_SEQ, K_XP, K_YP, K_ZP                                  	\n");                          
            sb.append(" ORDER BY MD_DATE, K_XP, K_YP, K_ZP      									   	\n");
            
            System.out.println(sb.toString());
			pstmt = localDBconn.createPStatement(sb.toString());
			pstmt.executeUpdate();
			
			sb = new StringBuffer();
			 
            sb.append(" INSERT INTO STATIC_ANALYSIS(MD_DATE, SG_SEQ, K_XP, K_YP, K_ZP, CNT)	\n");
            sb.append(" SELECT MD_DATE, SG_SEQ, K_XP, K_YP, K_ZP,  COUNT(DISTINCT MD_SEQ) AS CNT	\n");                                                        
            sb.append(" FROM                                                                           \n");                                                                                
            sb.append(" (                                                                              \n");                                                                                  
            sb.append("  SELECT  M.MD_SEQ, K_XP, K_YP, K_ZP, MD_DATE, S.SG_SEQ    			\n");
            sb.append("  FROM                                                                 			\n");                                                                                             
            sb.append("         (SELECT * FROM META WHERE MD_SEQ BETWEEN "+msMinNo+" AND "+msMaxNo+" ) M,  \n");
            sb.append("         IDX I, SG_S_RELATION S													\n");
            sb.append("  WHERE M.MD_SEQ = I.MD_SEQ                                          			\n");
            sb.append("    AND M.S_SEQ = S.S_SEQ											   			\n");
            sb.append(" ) C																			\n");
            sb.append(" GROUP BY MD_DATE, SG_SEQ, K_XP, K_YP, K_ZP                                  	\n");                          
            sb.append(" ORDER BY MD_DATE, K_XP, K_YP, K_ZP      									   	\n");
            
            System.out.println(sb.toString());
			pstmt = localDBconn.createPStatement(sb.toString());
			pstmt.executeUpdate();

		 } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            sb = null;
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
        }
   }
}