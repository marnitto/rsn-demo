<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import = "risk.issue.*
                   ,risk.util.*
                   ,risk.search.*
                   ,risk.admin.log.*
                   ,java.util.ArrayList"
%>                   
<%@ include file="../inc/sessioncheck.jsp" %>
<%	

	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	DateUtil du = new DateUtil();
	StringUtil su = new StringUtil();
	IssueMgr iMgr = new IssueMgr();
	
	String typeCode= null;
	String reportHtml= null;
	String script_str = "";
	
	//프로세스모드
	String mode = pr.getString("mode");
	String r_seq = pr.getString("r_seq","");
	String r_trend = pr.getString("trend","");
	
	//이슈정보 검색 조건 유지	
	String nowPage = pr.getString("nowPage");
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	
	if( mode.equals("update_all") ) {
		iMgr.updateTrend(r_seq, r_trend);
		script_str = "alert('수동성향이 수정되었습니다.'); \n  parent.document.location='issue_reply_mngr.jsp?nowPage="+nowPage+"&sDateFrom="+sDateFrom+"&sDateTo="+sDateTo+"'";
		%>
		<script type="text/javascript">
		<%=script_str%>
		</script>
		<%
	}else if(mode.equals("update_one")){
		iMgr.updateTrend(r_seq, r_trend);
	}
	
%>
<%out.println(script_str.trim());%>