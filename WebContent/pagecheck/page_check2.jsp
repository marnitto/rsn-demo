<%@ page contentType = "text/html; charset=utf-8" %>
<%@ page import="risk.util.DateUtil
			     ,java.util.Calendar
			     "
%>  

<%!
String getMdDate() {
	String result = "0";
	
	
	Calendar cal = Calendar.getInstance();
	result = Long.toString(cal.getTimeInMillis());
    
    if(result.length()>10){
    	result = result.substring(0,10);
    }
	
	return result;
}

%>
IndexStamp : <%=getMdDate()%>