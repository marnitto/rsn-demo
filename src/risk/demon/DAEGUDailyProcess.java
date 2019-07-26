package risk.demon;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import risk.DBconn.DBconn;
import risk.util.Log;

public class DAEGUDailyProcess {
	
	public static void main( String[] args )
	{
	  try{
			subdbconn = new DBconn();
		  	maindbconn = new DBconn();
		  	subdbconn.getSubDirectConnection();
        	maindbconn.getMainDirectConnection();        	
        	
        	Log.crond("DailyProcess START ...");    	 
        	
        	//전일  MIN(md_seq),MAX(md_seq) 저장
        	//insertMaxMinNO(1);        	
        	
        	//META, DATA, IDX 삭제
        	delMdSeq(30);
                    	
        	//EXCEPTION_META, EXCEPTION_DATA, EXCEPTION_IDX 삭제
        	delExMdSeq(7);
        	
        	//실시간 검색어
        	delRankDataThreeDayAgo(30);
        	
        	Log.crond("DailyProcess END ...");    	 
        	
	  }catch (SQLException ex) {
      	Log.writeExpt("CRONLOG",ex);
      } catch (Exception ex) {
          Log.writeExpt("CRONLOG",ex);
      } finally {
      	try { if( subdbconn  != null) subdbconn.close(); } catch(SQLException ex) { Log.writeExpt("CRONLOG",ex); }
      	try { if( maindbconn  != null) maindbconn.close(); } catch(SQLException ex) { Log.writeExpt("CRONLOG",ex); }
      }
	}
	
	static DBconn subdbconn = null;
	static DBconn maindbconn = null; 
	
	/**
     * 현재 날짜 리턴 (년,월,일)
     * @param minusDate
     * @return
     */
	static String getCurrDate()
	{
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
		
		return sdf.format(new java.util.Date()); 
	}
	
	/**
     * 현재 날짜 리턴 (년,월,일)
     * @param minusDate
     * @return
     */
	static String getCurrDateTime()
	{
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
		return sdf.format(new java.util.Date()); 
	}
	
	/**
     * DAILY_MAXNINNO 테이블에 하루전의 MAX(MD_SEQ), MIN(MD_SEQ) 값을 입력
     * @param minusDate
     * @return
     */
    static boolean insertMaxMinNO( int minusDate )
    {
        boolean result = false;

        Statement stmt = null;
        StringBuffer sb = null;

		try{
            sb = new StringBuffer();
            
            sb.append(" INSERT INTO DAILY_MAXNINNO(DM_DATE, MINNO, MAXNO) \n");
            sb.append(" SELECT \n"); 
            sb.append(" 	TIMESTAMPADD(DAY, -"+minusDate+", '"+getCurrDate()+" 23:59:59') DM_DATE \n");
            sb.append(" 	,(SELECT MD_SEQ FROM META WHERE MD_DATE BETWEEN TIMESTAMPADD(DAY, -"+minusDate+", '"+getCurrDate()+" 00:00:00') AND TIMESTAMPADD(DAY, -"+minusDate+", '"+getCurrDate()+" 23:59:59') ORDER BY MD_DATE ASC LIMIT 1) MINNO \n"); 
            sb.append(" 	,(SELECT MD_SEQ FROM META WHERE MD_DATE BETWEEN TIMESTAMPADD(DAY, -"+minusDate+", '"+getCurrDate()+" 00:00:00') AND TIMESTAMPADD(DAY, -"+minusDate+", '"+getCurrDate()+" 23:59:59') ORDER BY MD_DATE DESC LIMIT 1) MAXNO \n");
           
			stmt = subdbconn.createStatement();
			if( stmt.executeUpdate( sb.toString() ) > 0 ) result = true;

		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            sb = null;
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
        }
        
        return result;
    }
         
       
    /**
     * META, DATA, IDX 삭제 (주의 ASP_MAIN, ASP 데이터베이스로 나뉜다.)
     * @param minusDate
     * @return
     */
    static boolean delMdSeq(int minusDate)
    {
        boolean result = false;

        Statement stmt = null;
        ResultSet rs = null;
        Statement stmt2 = null;
        StringBuffer sb = null;
        int maxNum = 0;

		try{
			sb = new StringBuffer();       
			sb.append(" SELECT MD_SEQ AS MAXNUM FROM META \n");
			sb.append(" WHERE MD_DATE < TIMESTAMPADD(DAY, -"+minusDate+", '"+getCurrDate()+" 00:00:00') \n");
			sb.append(" ORDER BY MD_DATE DESC LIMIT 1 \n");
			stmt = subdbconn.createStatement();			
			rs = stmt.executeQuery(sb.toString());
			if(rs.next())
			{
				maxNum = rs.getInt("MAXNUM");				
			}
			
			Log.crond("maxNum:"+maxNum);   
						
			if(maxNum>0){
	            sb = new StringBuffer();       
	            sb.append(" DELETE FROM META  \n");
	            sb.append(" WHERE MD_SEQ < "+maxNum+" \n");
				stmt = subdbconn.createStatement();			
				stmt.executeUpdate( sb.toString());
				
				sb = new StringBuffer();
				sb.append(" DELETE FROM DATA  \n");
		        sb.append(" WHERE MD_SEQ < "+maxNum+" \n");
		        stmt = subdbconn.createStatement();
				stmt.executeUpdate( sb.toString());		
				
				sb = new StringBuffer();
				sb.append(" DELETE FROM IDX  \n");			
				sb.append(" WHERE MD_SEQ < "+maxNum+" \n");		
				stmt2 =  subdbconn.createStatement();
				stmt2.executeUpdate( sb.toString());
				
				sb = new StringBuffer();
				sb.append(" DELETE FROM MEMBER_COMFIRM  \n");			
				sb.append(" WHERE MD_SEQ < "+maxNum+" \n");		
				stmt2 =  subdbconn.createStatement();
				stmt2.executeUpdate( sb.toString());
				
				sb = new StringBuffer();
				sb.append(" DELETE FROM TWEET  \n");			
				sb.append(" WHERE MD_SEQ < "+maxNum+" \n");		
				stmt2 =  subdbconn.createStatement();
				stmt2.executeUpdate( sb.toString());
				
				sb = new StringBuffer();
				sb.append(" DELETE FROM FACEBOOK_DATA  \n");			
				sb.append(" WHERE FD_SEQ < "+maxNum+" \n");		
				stmt2 =  subdbconn.createStatement();
				stmt2.executeUpdate( sb.toString());
				
				sb = new StringBuffer();
				sb.append(" DELETE FROM MAP_META_SEQ  \n");			
				sb.append(" WHERE MD_SEQ < "+maxNum+" \n");		
				stmt2 =  subdbconn.createStatement();
				stmt2.executeUpdate( sb.toString());
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (stmt2 != null) try { stmt2.close(); } catch(SQLException ex) {}
        }
        
        return result;
    }
    
    static boolean delExMdSeq(int minusDate)
    {
        boolean result = false;

        Statement stmt = null;
        ResultSet rs = null;
        Statement stmt2 = null;
        StringBuffer sb = null;
        int maxNum = 0;

		try{
			sb = new StringBuffer();       
			sb.append(" SELECT MD_SEQ AS MAXNUM FROM EXCEPTION_META \n");
			sb.append(" WHERE MD_DATE < TIMESTAMPADD(DAY, -"+minusDate+", '"+getCurrDate()+" 00:00:00') \n");
			sb.append(" ORDER BY MD_DATE DESC LIMIT 1 \n");
			stmt = subdbconn.createStatement();			
			rs = stmt.executeQuery(sb.toString());
			if(rs.next())
			{
				maxNum = rs.getInt("MAXNUM");				
			}
			
			Log.crond("maxNum:"+maxNum);   
						
			if(maxNum>0){
	            sb = new StringBuffer();       
	            sb.append(" DELETE FROM EXCEPTION_META  \n");
	            sb.append(" WHERE MD_SEQ < "+maxNum+" \n");
				stmt = subdbconn.createStatement();			
				stmt.executeUpdate( sb.toString());
				
				sb = new StringBuffer();
				sb.append(" DELETE FROM EXCEPTION_DATA  \n");
		        sb.append(" WHERE MD_SEQ < "+maxNum+" \n");
		        stmt = subdbconn.createStatement();
				stmt.executeUpdate( sb.toString());		
				
				sb = new StringBuffer();
				sb.append(" DELETE FROM EXCEPTION_IDX  \n");			
				sb.append(" WHERE MD_SEQ < "+maxNum+" \n");		
				stmt2 =  subdbconn.createStatement();
				stmt2.executeUpdate( sb.toString());
				
				sb = new StringBuffer();
				sb.append(" DELETE FROM EXCEPTION_TWEET  \n");			
				sb.append(" WHERE MD_SEQ < "+maxNum+" \n");		
				stmt2 =  subdbconn.createStatement();
				stmt2.executeUpdate( sb.toString());
				
				sb = new StringBuffer();
				sb.append(" DELETE FROM EXCEPTION_FACEBOOK_DATA  \n");			
				sb.append(" WHERE FD_SEQ < "+maxNum+" \n");		
				stmt2 =  subdbconn.createStatement();
				stmt2.executeUpdate( sb.toString());
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (stmt2 != null) try { stmt2.close(); } catch(SQLException ex) {}
        }
        
        return result;
    }
    
    
    static int delRankDataThreeDayAgo(int minusDate )
    {
        int result = 0;

        Statement stmt = null;
        StringBuffer sb = null;

		try{
            sb = new StringBuffer();
            
            
            sb.append("DELETE FROM RANK_DATA WHERE SD_DATE < TIMESTAMPADD(DAY,-"+minusDate+",'"+getCurrDate()+" 00:00:00')\n");
            
			stmt = subdbconn.createStatement();
			result += stmt.executeUpdate( sb.toString() );
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            sb = null;
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
        }
        
        return result;
    }
}
