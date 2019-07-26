<%@ page import=
			"risk.util.ParseRequest
			,risk.mobile.MobileMgr
			,risk.util.Log
			"%>
			
<%
	MobileMgr mMar = new MobileMgr();	

	//15555215554
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	String tel = "";
	String result = "";
	
	tel = pr.getString("tel","");
	if(mMar.UserAccessCheck(tel)){
		result = "1";
	}else{
		result = "0";	
	}
	Log.writeExpt("MOBILE_APP_LOG","TEL:"+tel+"/result:"+result);

%>
<%=result%>