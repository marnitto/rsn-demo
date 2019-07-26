<%@ page contentType = "text/html; charset=utf-8" %>
<%@ page import="risk.DBconn.DBconn,
			     java.sql.Statement
			     ,java.sql.ResultSet
			     ,java.sql.SQLException
			     ,java.util.Calendar
			     "
%>   
<%@page import="risk.util.DateUtil"%>
<%!
	String getLockTime() {
		DateUtil du = new DateUtil();
	
		String result = "";
	    DBconn dbconn = new DBconn();
	    Statement stmt = null;
	    ResultSet rs = null;
	    try {
	        dbconn.getDBCPConnection();
	        
	        stmt = dbconn.createStatement();
	        rs = stmt.executeQuery("show processlist");

	        int eTime = 0;
	        while( rs.next() ) {
	        	if(rs.getString("Info") != null){
	        		if(rs.getInt("Time") > eTime){
	        			eTime = rs.getInt("Time");
	        		}
	        	}
	        }
	        
	        result = minusTimeToTimeStamp(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"), eTime);
	    } catch (SQLException ex ) {
	        ex.printStackTrace();

	    } catch (Exception ex ) {
	    	ex.printStackTrace();

	    } finally {
	        try { if( rs  != null) rs.close(); } catch(SQLException ex) { ex.printStackTrace(); }
	        try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { ex.printStackTrace(); }
	        try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { ex.printStackTrace(); }
	    }
	    return result;
	}

	String minusTimeToTimeStamp(String time, int eTimeSec){
		
		int year = Integer.parseInt(time.split(" ")[0].split("-")[0]);
		int month = Integer.parseInt(time.split(" ")[0].split("-")[1])-1;
		int day = Integer.parseInt(time.split(" ")[0].split("-")[2]);
		
		int hour = Integer.parseInt(time.split(" ")[1].split(":")[0]);
		int minute = Integer.parseInt(time.split(" ")[1].split(":")[1]);
		int second = Integer.parseInt(time.split(" ")[1].split(":")[2]);
		
		int minusDay = 0;
		int minusHour = 0;
		int minusMinute = 0;
		int minusSecond = 0;
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day, hour, minute, second);
		
		cal.add(Calendar.SECOND, (-1)*eTimeSec);
		
		//return sdf.format(cal.getTime());
		return Long.toString(cal.getTimeInMillis()/1000);
	}

%>
IndexStamp : <%=getLockTime()%>