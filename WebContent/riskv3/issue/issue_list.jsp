<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="	risk.issue.IssueDataBean
					,risk.search.MetaMgr
					,risk.issue.IssueMgr
					,java.util.ArrayList
					,risk.util.ParseRequest
					,risk.issue.IssueCodeMgr
					,risk.issue.IssueCodeBean
					,risk.issue.IssueBean					
					,risk.search.userEnvMgr
                 	,risk.search.userEnvInfo
                 	,risk.util.DateUtil
                 	,risk.util.PageIndex
                 	,risk.util.StringUtil" %>
<%
	
	ParseRequest 	pr 		= new ParseRequest(request);
	IssueMgr 	issueMgr 	= new IssueMgr();
	MetaMgr    	smgr 	= new MetaMgr();
	DateUtil 		du 		= new DateUtil();
	StringUtil		su		= new StringUtil();		
	IssueBean iBean;
	//pr.printParams();
	
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");	
	String schKey = pr.getString("schKey","");
		
	int nowpage = pr.getInt("nowpage");
	int rowCnt = 10;

	
	
	//이슈 리스트를 불러온다.
	ArrayList issueList = new ArrayList();
	issueList = issueMgr.getIssueList(nowpage,rowCnt, "", schKey, sDateFrom, sDateTo,"");
	int TotalCnt = issueMgr.getTotalIssueCnt();
	
	//페이징 처리
	int totalpage = 0;
	
	if (TotalCnt>0) {
		totalpage = TotalCnt / rowCnt;
		if ((TotalCnt % rowCnt) > 0 ) {
			totalpage++;
		}
	}
%>
<html>
<head>
<title><%=SS_TITLE %></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="css/basic.css" type="text/css">
<body>
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr>
  	<td width="47" align="center" background="images/issue_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"> <strong>No</strong></td>
    <td width="3" background="images/issue_table_bg01.gif"><img src="images/issue_table_bar.gif" width="3" height="32"></td>
    <td width="310" align="center" background="images/issue_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"> <strong>이슈명</strong></td>
    <td width="3" background="images/issue_table_bg01.gif"><img src="images/issue_table_bar.gif" width="3" height="32"></td>
    <td width="74" align="center" background="images/issue_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"> <strong>관련주제</strong> </td>
    <td width="3" background="images/issue_table_bg01.gif"><img src="images/issue_table_bar.gif" width="3" height="32"></td>
    <td width="74" align="center" background="images/issue_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"> <strong>등록자</strong></td>
    <td width="3" background="images/issue_table_bg01.gif"><img src="images/issue_table_bar.gif" width="3" height="32"></td>
    <td width="110" align="center" background="images/issue_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"><strong>등록일자</strong></td>
    <td width="3" background="images/issue_table_bg01.gif"><img src="images/issue_table_bar.gif" width="3" height="32"></td>
    <td width="60" align="center" background="images/issue_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"> <strong>삭제</strong></td>
    <td width="3" background="images/issue_table_bg01.gif"><img src="images/issue_table_bar.gif" width="3" height="32"></td>
    <td width="90" align="center" background="images/issue_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"> <strong>사용</strong></td>
  </tr>
<%
  for(int i=0; i < issueList.size(); i++){
	  iBean = (IssueBean)issueList.get(i);
%>
  <tr height="25">
  	<td style="padding: 3px 0px 0px 0px;" align="center"><%=TotalCnt-(i+rowCnt*(nowpage-1))%></td>
    <td>&nbsp;</td>
    <td style="padding: 3px 0px 0px 10px;"><a href="javascript:clickIssue('<%=iBean.getI_seq()%>');"><%=iBean.getI_title()%></a><img src="images/modify_icon.gif" width="14" height="14" style="cursor:hand;" onclick="popIssueEditform('<%=iBean.getI_seq()%>');" hspace="5" align="absmiddle"></td>
    <td>&nbsp;</td>
    <td align="center" style="padding: 3px 0px 0px 0px;"><%=iBean.getI_count()%></td>
    <td>&nbsp;</td>
    <td align="center" style="padding: 3px 0px 0px 0px;"><%=iBean.getM_name()%></td>
    <td>&nbsp;</td>
    <td align="center" style="padding: 3px 0px 0px 0px;"><%=iBean.getI_regdate()%></td>
    <td>&nbsp;</td>
    <td align="center" style="padding: 3px 0px 0px 0px;"><img src="images/x_icon.gif" width="14" height="14" style="cursor:hand;" onclick="deleteIssue('<%=iBean.getI_seq()%>');"></td>
    <td>&nbsp;</td>
    <td align="center" style="padding: 3px 0px 0px 0px;"><a href="javascript:updateIssueFlag('<%=iBean.getI_seq()%>');"><%=iBean.getI_useyn()%></a></td>
  </tr>
  <tr>
    <td colspan="13" bgcolor="#E9E9E9"><img src="images/brank.gif" width="1" height="1"></td>
  </tr>
  
<%} %>    
  <tr>
    <td>&nbsp;</td>
  </tr>
  <!-- 페이징 -->
  <tr height="27" valign="middle">
     <td colspan="13" bgcolor="#F2F2F2"><table width="100%" border="0" cellspacing="3" cellpadding="3">
       <tr>
         <td height="25" align="center" bgcolor="#FFFFFF"><table width="250" border="0" cellspacing="0" cellpadding="0">
           <tr>
             <td align="center" class="t"><%= PageIndex.getPageIndex( nowpage, totalpage,1,"issue_manager_prc.jsp") %></td>
           </tr>
      	</table></td>
  	   </tr>
  	 </table></td>
  </tr> 
  <!-- 페이징 -->
</table>
</body>
</html>
