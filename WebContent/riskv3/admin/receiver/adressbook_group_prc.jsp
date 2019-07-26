<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="risk.util.*
				 ,risk.sms.AddressBookDao
				 ,risk.sms.AddressBookGroupBean				 
                 "
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
    ParseRequest    pr = new ParseRequest(request);
	AddressBookDao abdDao = new AddressBookDao();
	
	String mode = pr.getString("mode","");
	String abSeq = pr.getString("abSeq","");
	String ag_seq = pr.getString("ag_seq","");
	String ag_name = pr.getString("ag_name","");
	
	AddressBookGroupBean abgBean = new AddressBookGroupBean();
	abgBean.setAg_seq(ag_seq);
	abgBean.setAg_name(ag_name);
	
	if(mode.equals("ins"))
	{
		if(ag_name.length()>0)
		{
			abdDao.insertAdressBookGroup(abgBean);
		}
	}else if(mode.equals("del")){
		if(ag_seq.length()>0)
		{
			abdDao.deleteAdressBookGroup(abgBean);
		}
	}
	pr.printParams();
	
	if(mode.equals("ins"))
	{
		
%>
<script language="Javascript">
	alert('그룹이 추가 되었습니다.');
	opener.location.reload();
	self.close();
</script>
<%
	}else if(mode.equals("del")){ 
		
%>
<script language="Javascript">
	alert('그룹이 삭제 되었습니다.');
	self.location.href='receiver_detail.jsp?mode=edit&abSeq=<%=abSeq%>';
</script>
<%
	}
%>