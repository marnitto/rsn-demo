<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ include file="../inc/sessioncheck.jsp" %> 
<%@ page import="	risk.issue.IssueDataBean
					,risk.issue.IssueMgr
					,risk.util.ParseRequest
                 	,risk.search.userEnvInfo
                 	,risk.util.DateUtil
                 	,risk.util.StringUtil" %>
<%
userEnvInfo env = (userEnvInfo)session.getAttribute("ENV");
ParseRequest pr = new ParseRequest(request);
IssueMgr im = new IssueMgr();
DateUtil	 du = new DateUtil();

pr.printParams();
String ir_seq = pr.getString("ir_seq");
String past_ir_title = pr.getString("past_ir_title");
String ir_title = pr.getString("ir_title");
String ir_type = pr.getString("ir_type");
String StrScript = "";

System.out.println(ir_seq);
System.out.println(ir_title);
System.out.println(past_ir_title);

im.reportNameUpdate(ir_seq, ir_title);

	StrScript = "opener.parent.top.bottomFrame.contentsFrame.document.location.href='issue_report_list.jsp?ir_type="+ir_type+"';\n"
	+"parent.window.close();";

%>
<script>
	<%=StrScript%>
</script>
