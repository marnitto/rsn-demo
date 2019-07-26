<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="	java.util.ArrayList
                 	,risk.sms.AddressBookDao
					,risk.sms.AddressBookBean
					,risk.util.ParseRequest
					,risk.admin.site.SitegroupBean
				 	,risk.admin.site.SiteMng
				 	,risk.search.userEnvMgr
				 	,risk.search.keywordInfo
				 	,risk.admin.classification.classificationMgr
				 	,risk.admin.classification.clfBean
				 	,java.util.List
					" %>
<%
	ParseRequest pr = new ParseRequest(request);
	AddressBookDao AddDao = new AddressBookDao();
	
	pr.printParams();

	int abSeq = pr.getInt("abSeq",0);
	
	String mode = pr.getString("mode","");
	String name = pr.getString("name","");
	String ag_seq = pr.getString("ag_seq","");
	String dept = pr.getString("dept","");
	String pos = pr.getString("pos","");
	String mobile = pr.getString("mobile","");
	String email = pr.getString("email","");	
	String issueCheck = pr.getString("issueCheck","");
	String reportD = pr.getString("reportD","");
	String reportW = pr.getString("reportW","");	
	String seqList = pr.getString("seqList","");
	String appChk = pr.getString("appChk","0");
	String smsChk = pr.getString("smsChk","0");
	pr.printParams();

	AddressBookBean addBean = new AddressBookBean();
	addBean.setMab_seq(abSeq);
	addBean.setMag_seq(ag_seq);	
	addBean.setMab_name(name);	
	addBean.setMab_dept(dept);
	addBean.setMab_mobile(mobile);
	addBean.setMab_pos(pos);
	addBean.setMab_mail(email);
	addBean.setMab_issue_receivechk(issueCheck);
	addBean.setMab_report_day_chk(reportD);
	addBean.setMab_report_week_chk(reportW);
	addBean.setMab_app_chk(appChk);
	addBean.setMab_sms_chk(smsChk);
	
	
	if( mode.equals("add") ) {
		AddDao.insertAddressBook(addBean);
	}else if( mode.equals("edit") ) {
		AddDao.updateAddressBook(addBean);
	}else if( mode.equals("del") ) {
		AddDao.deleteAddressBook( seqList );
	}
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
	location.href = 'receiver_list.jsp';
//-->
</SCRIPT>