<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="	risk.issue.IssueMgr
					,java.util.ArrayList
					,risk.util.ParseRequest					
					,risk.issue.IssueTitleBean									
                 	,risk.util.DateUtil
                 	,risk.util.PageIndex
                 	,risk.util.StringUtil" %>
<%
	
	ParseRequest 	pr 		= new ParseRequest(request);
	//pr.printParams();
	IssueMgr 	issueMgr 	= new IssueMgr();	
	DateUtil 		du 		= new DateUtil();
	StringUtil		su		= new StringUtil();		
	IssueTitleBean iBean;	
	
	String i_seq = pr.getString("i_seq");
	String it_seq = pr.getString("it_seq");
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");	
	String schKey = pr.getString("schKey","");
		
	int nowpage = pr.getInt("nowpage");
	int rowCnt = 10;
	
	//주제 리스트를 불러온다.
	ArrayList issueTitleList = new ArrayList();	
	issueTitleList = issueMgr.getIssueTitleList(nowpage,rowCnt,i_seq,it_seq, "", sDateFrom, sDateTo,"");	
	IssueTitleBean itBean = new IssueTitleBean();
	int TotalCnt = issueMgr.getTotalIssueTitleCnt();
	
	int totalpage = 0;
	
	if (TotalCnt>0) {
		totalpage = TotalCnt / rowCnt;
		if ((TotalCnt % rowCnt) > 0 ) {
			totalpage++;
		}
	}
	
%>
<table width="780" border="0" cellspacing="0" cellpadding="0">
	<tr>
       <td width="15" height="33" style="padding: 3px 0px 0px 0px;"><img src="images/pop_ico02.gif" width="13" height="13" align="absmiddle"></td>
       <td width="255" style="padding: 5px 0px 0px 0px;"><strong>관련주제</strong></td>
       <td width="510" align="right" class="BIG_title02" style="padding: 2px 0px 0px 0px;"><img src="images/content_btn.gif" width="81" height="24" style="cursor:hand;" onclick="popTitleEditform('insert','');"></td>
     </tr>
</table>
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr>
  	<td width="47" align="center" background="images/issue_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"> <strong>No</strong></td>
    <td width="3" background="images/issue_table_bg01.gif"><img src="images/issue_table_bar.gif" width="3" height="32"></td>
    <td width="464" align="center" background="images/issue_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"> <strong>주제명</strong></td>
    <td width="3" background="images/issue_table_bg01.gif"><img src="images/issue_table_bar.gif" width="3" height="32"></td>    
    <td width="110" align="center" background="images/issue_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"><strong>등록일자</strong></td>
    <td width="3" background="images/issue_table_bg01.gif"><img src="images/issue_table_bar.gif" width="3" height="32"></td>
    <td width="60" align="center" background="images/issue_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"> <strong>삭제</strong></td>
    <td width="3" background="images/issue_table_bg01.gif"><img src="images/issue_table_bar.gif" width="3" height="32"></td>
    <td width="90" align="center" background="images/issue_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"> <strong>사용</strong></td>
  </tr>
<%
  for(int i=0; i < issueTitleList.size(); i++){
	  itBean = (IssueTitleBean)issueTitleList.get(i);
%>
  <tr height="25">
  	<td style="padding: 3px 0px 0px 0px;" align="center"><%=TotalCnt-(i+rowCnt*(nowpage-1))%></td>
    <td>&nbsp;</td>
    <td style="padding: 3px 0px 0px 10px;"><a><%=itBean.getIt_title()%></a><img src="images/modify_icon.gif" width="14" height="14" style="cursor:hand;" onclick="popTitleEditform('update','<%=itBean.getIt_seq()%>');" hspace="5" align="absmiddle"></td>
    <td>&nbsp;</td>    
    <td align="center" style="padding: 3px 0px 0px 0px;"><%=itBean.getIt_regdate()%></td>
    <td>&nbsp;</td>
    <td align="center" style="padding: 3px 0px 0px 0px;"><img src="images/x_icon.gif" width="14" height="14" style="cursor:hand;" value="" onclick="deleteIssueTitle('<%=itBean.getIt_seq()%>')"></td>
    <td>&nbsp;</td>
    <td align="center" style="padding: 3px 0px 0px 0px;"><a href="javascript:updateIssueTitleFlag('<%=itBean.getIt_seq()%>');"><%=itBean.getIt_useyn()%></a></td>
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
             <td align="center" class="t"><%= PageIndex.getPageIndex( nowpage, totalpage,2,"issue_title_list.jsp") %></td>
           </tr>
      	</table></td>
  	   </tr>
  	 </table></td>
  </tr>
</table>
  <!-- 페이징 -->

