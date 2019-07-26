<%@ page contentType = "text/html; charset=euc-kr" %>
<%@ page import="risk.mobile.PcAlimiMgr,
				 risk.util.Log,
			     java.util.ArrayList"
%>    
<%
	PcAlimiMgr PcMgr = new PcAlimiMgr();
	//ArrayList arrMember = mDao.getAMList();
	//MemberBean mBean = null;
	
	String ip = request.getRemoteAddr();
	//System.out.println(ip);
	String script = "";
	boolean ipChk = PcMgr.checkIP(ip);
	
	//if(arrMember!=null){
	//	for(int i=0; i<arrMember.size(); i++){
	//		mBean = (MemberBean)arrMember.get(i);
				//out.println(mBean.getMip()+"<br>");
	//		if(mBean.getMip().trim().equals(ip)){
	//			ipChk = true;
	//			Log.writeExpt("PC_ALIMI_LOG","IP Check Success NAME:"+mBean.getMname()+" IP:"+ip);
	//		}
	//	}
		if(ipChk){
				out.println("success");
				Log.writeExpt("PC_ALIMI_LOG","IP Check Success IP:"+ip);
		}else{
			Log.writeExpt("PC_ALIMI_LOG","IP Check Faile IP:"+ip);
			out.println("failure");
		}
	//}else{
	//	out.println("failure");
		
	//}
%>

<%//=script%>