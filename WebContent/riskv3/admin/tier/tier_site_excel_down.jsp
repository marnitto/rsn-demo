<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="risk.admin.tier.*,   
                 risk.util.DateUtil,
                 risk.util.ParseRequest,
                 java.util.ArrayList" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%

    DateUtil        du = new DateUtil();
    ParseRequest    pr = new ParseRequest(request);
    
    String ts_types = pr.getString("ts_types");
    TierSiteMgr tr = new TierSiteMgr();
	
	response.setContentType("application/vnd.ms-excel; charset=EUC-KR") ;
    response.setHeader("Content-Disposition", "attachment;filename=tier_"+ du.getCurrentDate("yyyyMMdd") +".xls");
    response.setHeader("Content-Description", "JSP Generated Data");
	
    TierSiteBean tsBean = new TierSiteBean();
    ArrayList arr = new ArrayList();
    arr = tr.getTierSiteList(0,0,"",ts_types);
    if(arr.size()>0)
	{

%>
<table style="width:700px; table-layout:fixed;" border="1" cellspacing="1" >
	    <caption></caption>
		<colgroup>
			<col style="width:100px">
			<col style="width:100px">
			<col style="width:200px">
		</colgroup>
		<thead>
			<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->
			<%
				int colspan = 3;
			%>			
			<tr>
			<th scope="col"><span>순번</span></th>
			<th scope="col"><span> Tiering</span></th>
			<th scope="col"><span> 사이트명</span></th>
			</tr>
		</thead>
	    <tbody>
	    
	    <%
			for(int i =0; i<arr.size(); i++)
			{
				tsBean = new TierSiteBean();
				tsBean = (TierSiteBean)arr.get(i);
		%>
			<tr>		
			  <td align="center"bgcolor="#FFFFFF"><%=i+1%></td>	  
              <td align="center"bgcolor="#FFFFFF" class="table_top"><%=tsBean.getTs_type() %></td>
              <td align="center" bgcolor="#FFFFFF" class="table_top"><%=tsBean.getTs_name()%></td>
            </tr>                    
<%
			}
%>	
	    </tbody>
	</table>
<%} %>
		