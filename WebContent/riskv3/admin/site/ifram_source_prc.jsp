<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import="risk.util.*,
				 risk.admin.site.SiteMng
                 "
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
    ParseRequest    pr = new ParseRequest(request);

	String mode	= pr.getString("mode");
	String sgseq	= pr.getString("sgseq");

	String[] gsn;
	int i;
	StringBuffer sb = new StringBuffer();

	gsn = request.getParameterValues("gsn");

	for(i=0; i< gsn.length;i++)
	{
		if( i != 0) sb.append(",");
		sb.append(gsn[i]);
	}

	SiteMng sitemng = new SiteMng();

	if( mode.equals("ins") ) {
		
		sitemng.insertGSN(sgseq, sb.toString() );
		
	} else if( mode.equals("del") ) {
		sitemng.deleteGSN( sb.toString() );
	}

%>
<SCRIPT LANGUAGE="JavaScript">
<!--
	//parent.sc_sitemng.location.href = 'ifram_source.jsp';
	//parent.tg_sitemng.location.href = 'ifram_target.jsp';
	parent.source_form.submit();
	parent.target_form.submit();
//-->
</SCRIPT>