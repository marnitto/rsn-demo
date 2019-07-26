<%@ page contentType = "text/html; charset=utf-8" %>
<%@ page import="risk.DBconn.DBconn,
			     java.sql.Statement
			     ,java.sql.ResultSet
			     ,java.sql.SQLException
			     "
%>   

<%!
	String getMdDate() {
		String result = "123123";
	    DBconn dbconn = new DBconn();
	    Statement stmt = null;
	    ResultSet rs = null;
	    try {

	        
	        dbconn.getDBCPConnection();
	        
	        System.out.println("SELECT UNIX_TIMESTAMP(A2.MD_DATE) AS MD_DATE, A2.MD_DATE AS DATE2 FROM IDX A1, META A2 WHERE A1.MD_SEQ = A2.MD_SEQ ORDER BY A2.MD_SEQ DESC LIMIT 1   ");
	        
	        stmt = dbconn.createStatement();
	        rs = stmt.executeQuery("SELECT UNIX_TIMESTAMP(A2.MD_DATE) AS MD_DATE, A2.MD_DATE AS DATE2 FROM IDX A1, META A2 WHERE A1.MD_SEQ = A2.MD_SEQ ORDER BY A2.MD_SEQ DESC LIMIT 1     ");

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