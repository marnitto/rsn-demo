<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="	
                 	risk.mobile.AlimiSettingDao
					,risk.mobile.AlimiReceiverBean
					,risk.util.ParseRequest			 	
				" 
%>
<%	
	ParseRequest	pr = new ParseRequest(request);	
	AlimiSettingDao asDao = new AlimiSettingDao();
	AlimiReceiverBean asBean = new AlimiReceiverBean();
	
	//pr.printParams();
	String ab_seq = "";
	String ab_name = pr.getString("ab_name","");
	String ab_dept = pr.getString("ab_dept","");
	String ab_pos = pr.getString("ab_pos","");
	String ab_mobile = pr.getString("ab_mobile","");
	String ab_email = pr.getString("ab_email","");
	
	asBean.setAb_name(ab_name);
	asBean.setAb_dept(ab_dept);
	asBean.setAb_pos(ab_pos);
	asBean.setAb_mobile(ab_mobile);
	asBean.setAb_mail(ab_email);
	
	
	ab_seq = asDao.insertAddressBook(asBean);	
%>
<script language="javascript">
<!--	
	var existAb_seq = opener.document.ifr_receiver.existAb_seq.value;	
	var ab_seq ;
	
	if(existAb_seq =='')
	{	
		ab_seq = <%=ab_seq%>;
	}else{
		ab_seq = existAb_seq +','+ <%=ab_seq%>;
	}
	
	opener.location = 'ifrm_receiver_list.jsp?ab_seq='+ab_seq;
	//opener.location.href.reload;
	window.close();
//-->
</script>