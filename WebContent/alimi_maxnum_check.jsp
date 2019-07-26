<%@ page import=
			"risk.util.ParseRequest
			,risk.mobile.MobileMgr
			"%>		
<%
	MobileMgr mMar = new MobileMgr();	

	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	String tel = "";
	long old_max_seq;
	String result = "";
	String gubun = (new Character((char)29)).toString();
	
	
	tel = pr.getString("tel","");
	old_max_seq = pr.getInt("mal_seq", 0);
	
	long[] arrResult = mMar.DataMaxNumCheck(tel, old_max_seq);
	
	if(arrResult.length != 2)
	{
		result = "0"+ gubun +"0";
	}
	else
	{
		result = Long.toString(arrResult[0]) + gubun + Long.toString(arrResult[1]);
	}
%>
<%=result%>