<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="risk.issue.IssueCodeMgr
				,risk.issue.IssueCodeBean	
				,risk.issue.IssueMgr
				,risk.issue.IssueTitleBean
				,risk.util.ParseRequest
				,risk.util.StringUtil
				,java.util.ArrayList				
				"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%

	//이슈데이터 등록 관련
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	StringUtil su = new StringUtil();
	IssueMgr isMgr = new IssueMgr();
	IssueTitleBean itBean = new IssueTitleBean();
	ArrayList arrItBean = new ArrayList();
	arrItBean = isMgr.getIssueTitleList(0,0,pr.getString("i_seq"),"","","","","Y");
	String selected = "";
%>
            			
<select name="it_seq" class="selectbox1" setDisplayCount="5" setImage="<%=SS_URL%>images/issue/selet_arrow.gif" setColor="#696969,#ffffff,#696969,#F4F4F4,#C8CACA,#C8CACA">
	<option value="">선택하세요</option>
<%							
			if(arrItBean!=null && arrItBean.size()>0){
				for (int j = 0; j < arrItBean.size(); j++) {
					selected = "";
					itBean = (IssueTitleBean)arrItBean.get(j);
					if(itBean.getIt_seq().equals(pr.getString("selItseq"))){
						selected = "selected";
					}
%>
	<option value="<%=itBean.getIt_seq()%>" title="<%=itBean.getIt_title()%>" <%=selected%>><%=su.cutString(itBean.getIt_title(), 25,"")%></option>
<%
				}
			}
%>
</select>

