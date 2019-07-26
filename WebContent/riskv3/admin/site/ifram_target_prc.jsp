<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import="risk.util.*,
				 risk.admin.site.SiteMng
                 "
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
try{
    ParseRequest    pr = new ParseRequest(request);

	String mode	= pr.getString("mode");
	String sgseq = pr.getString("sgseq");
	String sgval = pr.getString("sgval");

	

	SiteMng sitemng = new SiteMng();

	if( mode.equals("ins") ) {
		sitemng.SGinsert(sgval);
	} else if( mode.equals("edit") ) {
		sitemng.SGupdate(Integer.parseInt(sgseq), sgval);
	} else if( mode.equals("del") ) {
		sitemng.SGdelete(Integer.parseInt(sgseq));
	}
}catch(Exception e){System.out.println("error ifram_target_prc line 26 : "+e);}
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
	//location.href = 'ifram_target.jsp';
	parent.source_form.submit();
	parent.target_form.submit();
//-->
</SCRIPT>