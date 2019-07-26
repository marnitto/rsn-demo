package risk.demon;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import risk.DBconn.DBconn;
import risk.util.Log;

public class DAEGUMailLogProcess {
	
	static DBconn subdbconn = null;
	
	public static void main(String args[]) {
	
		try{
			subdbconn = new DBconn();
		  	subdbconn.getSubDirectConnection();
        	        	
        	Log.crond("MailLogProcess START ...");
        	
        	//약 3개월 로그만 저장하도록...(31x3)
        	String mal_seq = "";
        	mal_seq = getMailLogSeq(93);
        	
        	//mail log(r-rimi,sms) 삭제
        	if( !"".equals(mal_seq) ){
        		deleteMailLog(mal_seq);	
        	}else{
        		Log.crond("No Log Data ...");
        	}
                    	
        	Log.crond("MailLogProcess END ...");    	 
        	
	  }catch (SQLException ex) {
      	Log.writeExpt("CRONLOG",ex);
      } catch (Exception ex) {
          Log.writeExpt("CRONLOG",ex);
      } finally {
      	try { if( subdbconn  != null) subdbconn.close(); } catch(SQLException ex) { Log.writeExpt("CRONLOG",ex); }
      }
	}
	
	
	static String getCurrDate()
	{
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
		
		return sdf.format(new java.util.Date()); 
	}
	
	
	public static String getMailLogSeq(int day){
		String result = "";
		
		Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
		
		try{
			sb = new StringBuffer();       
			sb.append(" SELECT MAL_SEQ FROM MALIMI_LOG \n");
			sb.append(" WHERE MT_DATE < TIMESTAMPADD(DAY, -"+day+", '"+getCurrDate()+" 00:00:00') \n");
			sb.append(" ORDER BY MT_DATE DESC LIMIT 1 \n");
			stmt = subdbconn.createStatement();			
			rs = stmt.executeQuery(sb.toString());
			if(rs.next())
			{
				result = rs.getString("MAL_SEQ");				
			}
		}catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
        }
		
		return result;
	}
	
	
	public static void deleteMailLog(String malSeq){
		Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	sb = new StringBuffer();       
            sb.append(" DELETE FROM MALIMI_LOG  \n");
            sb.append(" WHERE MAL_SEQ < "+malSeq+" \n");
			stmt = subdbconn.createStatement();			
			stmt.executeUpdate( sb.toString());
			
			Log.crond("Delete Log Data : "+malSeq);
			
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
        }
		
	}

}
