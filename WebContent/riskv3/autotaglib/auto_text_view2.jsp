<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="risk.taglib.AutoTagDao"%>
<%@page import="risk.taglib.AutoTagBean"%>
<%@page import="risk.util.ParseRequest"%>
<%@page import="risk.util.StringUtil"%>
<%
	ParseRequest pr  = new ParseRequest(request);
	pr.printParams();
	StringUtil su = new StringUtil();
	String queryId = pr.getString("queryId");
	String name = pr.getString("name");
	
	AutoTagDao atDao = new AutoTagDao();
	AutoTagBean atBean = new AutoTagBean();	
	ArrayList arr = new ArrayList();
	
	if(!name.equals("")){
		arr = atDao.findByName(queryId,name);
	}
	
	String className = "";
	String spanKey = "";
	
	if(arr.size()>0){
%>
<ul>	
<%
		for(int i =0;i <arr.size(); i++)
			{
		atBean = new AutoTagBean();
		atBean = (AutoTagBean)arr.get(i);	
		//검색 키워드 강조
		spanKey =su.cutKey(atBean.getName(),name,1000,"#FDCDBC");
		
		className = "";
		if(i==0) className = "class=\"selected\"";
%>
	<li <%=className%> onmouseover="autoText2.mouseOver(<%=i%>);" onmousedown="autoText2.mouseDown(<%=i%>);"><input type="hidden" name="cd" id="cd" value="<%=atBean.getValue()+","+atBean.getValue2()%>"/><%=spanKey%></li>
<%
		}
%>
</ul>
<%
	}
%>