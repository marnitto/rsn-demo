<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="risk.issue.IssueDataBean
                ,risk.issue.IssueMgr
                ,java.util.ArrayList
                ,risk.util.ParseRequest
                ,risk.issue.IssueCodeMgr
                ,risk.issue.IssueCodeBean
                ,risk.issue.IssueDataBean
                ,risk.issue.IssueBean                
                ,risk.search.MetaMgr
                ,risk.search.userEnvMgr
                ,risk.search.userEnvInfo
                ,risk.util.StringUtil
                ,risk.util.DateUtil
                ,risk.util.PageIndex" %>
<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	IssueMgr issueMgr = new IssueMgr();
	IssueDataBean idBean= new IssueDataBean();
	
	ArrayList arrIdBean = new ArrayList();

	//보고서 날짜
	String ir_sdate = pr.getString("ir_sdate");
	String ir_edate = pr.getString("ir_edate");
	
	String ir_stime = pr.getString("ir_stime");
	String ir_etime = pr.getString("ir_etime");
	
	//시간값 자릿수에 따른 처리
	if(ir_stime.length()==1){
		ir_stime = "0"+ir_stime+":00:00";
	}else if(ir_stime.length()==2){
		ir_stime = ir_stime+":00:00";
	}
	
	if(ir_etime.length()==1){
		ir_etime = "0"+ir_etime+":00:00";
	}else if(ir_etime.length()==2){
		ir_etime = ir_etime+":00:00";
	}	
	
		
	arrIdBean = issueMgr.getIssueDataforTypeCode11(ir_sdate,ir_stime,ir_edate,ir_etime, "1", "1,2", "8");
%>

<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
<col width="5%"><col width="15%"><col width="*"><col width="10%"><col width="15%"><col width="10%">
	<tr>
		<th><input type="checkbox" name="checkall_isu" value="" onclick="checkAll('isu');"></th>
		<th>출처</th>
		<th>제목</th>
		<th>유사</th>
		<th>수집일시</th>
		<th>성향</th>
	</tr>
<%
		String sunghyang = "";
		if(arrIdBean.size() > 0){
			for(int i = 0; i < arrIdBean.size(); i++){
				idBean = (IssueDataBean)arrIdBean.get(i);
				if(idBean.getIc_code().equals("1")){
					sunghyang = "긍정";
				}else if(idBean.getIc_code().equals("2")){
					sunghyang = "중립";
				}else{
					sunghyang = "부정";
				}
%>
	<tr>
		<td><input type="checkbox" name="isuCheck" value="<%=idBean.getId_seq()%>" onclick=""></td>
		<td><p style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board01_category" title="<%=idBean.getMd_site_name()%>"><%=idBean.getMd_site_name()%></p></td>
		<td><p style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board_01_tit_01" title="<%=idBean.getId_title()%>"><a href="javascript:hrefPop('<%=idBean.getId_url()%>');"><%=idBean.getId_title()%></a></p></td>
		<td><%=idBean.getMd_same_ct()%></td>
		<td><%=idBean.getFormatMd_date("yyyy-MM-dd")%></td>
		<td><p class="tendency_0<%=idBean.getIc_code()%>"><%=sunghyang%></p></td>
	</tr>
<%
			}
		}else{%>
		<tr>
			<td colspan="6" style="text-align: center;">데이터가 없습니다.</td>
		</tr>
			
		<%}
%>
</table>
