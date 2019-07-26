<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="	java.util.ArrayList
                 	,risk.admin.pressgroup.PressgroupDataBean
                 	,risk.admin.pressgroup.PressgroupDataMgr
					,risk.util.ParseRequest
					" %>
<%
	ParseRequest pr = new ParseRequest(request);
	PressgroupDataMgr pMgr = new PressgroupDataMgr();
	


	pr.printParams();

	String mode = pr.getString("mode");
	
	PressgroupDataBean pBean = new PressgroupDataBean();
	pBean.setPg_seq(pr.getString("target_seq"));
	pBean.setPg_title(pr.getString("title",""));
	pBean.setPg_content(pr.getString("content",""));
	pBean.setPg_date(pr.getString("date",""));
	pBean.setPg_regdate(pr.getString("regDate",""));
	pBean.setPg_dept(pr.getString("p_dept"));
	
	
	if( mode.equals("add") ) {
		pMgr.InsertPressgroupData(pBean);
	}else if( mode.equals("update") ) {
		pMgr.UpdatePressgroupData(pBean);		
	}else if( mode.equals("delete") ) {
		
		String[] pg_seqs = pr.getStringArr("pg_seq");
		
		for(int i = 0; i < pg_seqs.length; i++){
			pMgr.DeletePressgroupData(pg_seqs[i]);
		}
	}
%>
<SCRIPT LANGUAGE="JavaScript">
	parent.location.href = 'pressgroup_data_list.jsp';	                 
</SCRIPT>