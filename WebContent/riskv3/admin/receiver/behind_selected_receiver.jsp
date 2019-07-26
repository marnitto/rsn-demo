<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" %>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="	java.util.ArrayList
					,java.net.*
                 	,risk.sms.AddressBookBean
					,risk.sms.AddressBookDao
					,risk.util.ParseRequest									
				 	,java.util.List
					" 
%>
<%		
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	AddressBookDao abDao = new AddressBookDao();
	AddressBookBean abBean = null;
	ArrayList receiverList = new ArrayList();
	
	//선택된 번호가 없을시 0으로 대체(0번 정보수신자는 없음)
	String selectedAbSeq = pr.getString("selectedAbSeq","0");	
	String ab_seq = pr.getString("ab_seq","");
	String ag_seq = pr.getString("ag_seq","");
	
	receiverList = abDao.getAddressList(selectedAbSeq,"","","","","",ag_seq);
	
%>

		
<%
	if(receiverList!=null)
	{
%>
							<table width="100%"  border="0" cellspacing="0" cellpadding="0" >
<%
	for( int i=0 ; i < receiverList.size() ; i++ ) {
			
		 abBean = (AddressBookBean)receiverList.get(i);
%>
							<tr>
								<td class="pop_mail_group_td"><%=abBean.getMab_name()%> <span style="font-weight:bold;color:#1886f7;"><%=abBean.getMab_dept()%></span></td>
								<td style="text-align:right;" class="pop_mail_group_td">SMS발송<input type="checkbox" name="smsChk" value="<%=abBean.getMab_mobile()%>" <%if(abBean.getMab_sms_chk().equals("1")){out.print("checked");}%>></td>
								<td style="text-align:right;" class="pop_mail_group_td"><img src="../../images/common/btn_cancel.gif" onclick="selectLeftMove('<%=abBean.getMab_seq()%>')" style="cursor:pointer"/></td>
							</tr>
<%			 
	 }
%>
							</table>
<%
	}else{
%>
	
<%		
	}
%>