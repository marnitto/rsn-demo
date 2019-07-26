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
	AddressBookDao abDao = new AddressBookDao();
	AddressBookBean abBean = null;
	ArrayList receiverList = new ArrayList();
	
	String selectedAbSeq = pr.getString("selectedAbSeq","");
	String ab_seq = pr.getString("ab_seq","");
	String ag_seq = pr.getString("ag_seq","");
	String ab_name = pr.getString("ab_name","");
	String ab_dept = pr.getString("ab_dept","");
	String ab_issue_receivechk = pr.getString("ab_issue_receivechk","");
	String ab_report_day_chk = pr.getString("ab_report_day_chk","");
	String ab_report_week_chk = pr.getString("ab_report_week_chk","");
	
	receiverList = abDao.getAddressList(ab_seq,ab_name,ab_dept,ab_issue_receivechk,ab_report_day_chk,ab_report_week_chk,ag_seq,selectedAbSeq);
	pr.printParams();	
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
													<td style="text-align:right;" class="pop_mail_group_td"><img src="../../images/common/btn_select.gif" onclick="selectRightMove('<%=abBean.getMab_seq()%>')" style="cursor:pointer"/></td>
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
