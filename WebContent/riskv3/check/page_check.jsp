<%@ page contentType = "text/html; charset=utf-8" %>
<%@ page import="risk.DBconn.DBconn,
			     java.sql.Statement
			     ,java.sql.ResultSet
			     ,java.sql.SQLException
			     "
%>   

<%!
	String getMdDate() {
		String result = "0";
	    DBconn dbconn = new DBconn();
	    Statement stmt = null;
	    ResultSet rs = null;
	    try {

	        
	        dbconn.getDBCPConnection();
	        
	        System.out.println("  select UNIX_TIMESTAMP(MAX(MD_DATE)) AS MD_DATE, MAX(MD_DATE) AS DATE2 from  META B    ");
	        
	        stmt = dbconn.createStatement();
	        rs = stmt.executeQuery("  select UNIX_TIMESTAMP(MAX(MD_DATE)) AS MD_DATE, MAX(MD_DATE) AS DATE2 from  META B     ");

	        if( rs.next() ) {
	        	System.out.println("** 페이지 체크 시간 Page check :"+rs.getString("DATE2"));
	            result =  rs.getString("MD_DATE");
	        }


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

%>
IndexStamp : <%=getMdDate()%>
