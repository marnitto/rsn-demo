<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList
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
	String agSeq = pr.getString("ag_seq", "");
		
	receiverList = abDao.getInfoReceiverList(agSeq);
	pr.printParams();	
	if(receiverList.size() > 0){
		for(int i =0; i < receiverList.size(); i++ ){
			abBean = (AddressBookBean)receiverList.get(i);	
%>
			<li data-role-value="<%=abBean.getMag_seq()%>" user_data_value="<%=abBean.getMab_seq()%>">
				<span><%=abBean.getMab_name()%> <strong class=""><%=abBean.getMab_dept()%></strong></span>
				<%-- <div class="absolute_r sms">
					<input id="input_sms_chk_0<%=i%>" type="checkbox" class="ui_chkbox_0<%=i%>"><label for="input_sms_chk_0<%=i%>">SMS발송</label>
				</div> --%>
			</li>
<%		}
	}	
%>
