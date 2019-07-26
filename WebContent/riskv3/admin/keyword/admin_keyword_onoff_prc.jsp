
<%@page import="risk.search.GetKGMenu"%><%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="risk.util.ParseRequest				
				,risk.search.userEnvInfo
				,risk.util.DateUtil
				,java.util.ArrayList
				,risk.search.siteGroupInfo
                ,risk.search.siteDataInfo
                ,risk.admin.membergroup.membergroupMng
                ,risk.admin.membergroup.membergroupBean
                ,java.net.URLEncoder"%>
<%@page import="risk.admin.keyword.KeywordMng"%>
<%@include file="../../inc/sessioncheck.jsp"%>
<%		
		ParseRequest pr = new ParseRequest(request);
		KeywordMng kMng = new KeywordMng();
		int OnOffMode = pr.getInt("onoffmode");
		int xp = pr.getInt("xp");
		int yp = pr.getInt("yp");
		int zp = pr.getInt("zp");		
		int result = kMng.keywordOnOff(xp,yp,zp,OnOffMode);
		String Script = "?xp="+xp+"&yp="+yp+"&zp="+zp+"";
		System.out.println("result="+result);

if(result>=1){
%>
<script language="javascript" type="text/javascript">
	document.location.href = 'admin_keyword_left.jsp<%=Script%>';
</script>
<%
}else{
%>
<script language="javascript" type="text/javascript">
	alert('저장에 실패하였습니다.');
	history.back();
</script>
<%
}

%>