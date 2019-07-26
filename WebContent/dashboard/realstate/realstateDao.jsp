<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../riskv3/inc/sessioncheck.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "java.util.Enumeration" %>
<%@ page import = "java.util.Map" %>
<%@ page import="risk.json.*"%>
<%@ page import="risk.util.ParseRequest
                 ,risk.util.DateUtil
                 ,java.util.*
                 ,java.net.URLEncoder
                 ,risk.util.PageIndex
                 ,risk.util.StringUtil
                 ,risk.util.ConfigUtil
                 ,risk.dashboard.realstate.RealstateMgr
                 "
%>
<%
ParseRequest pr = new ParseRequest(request);
pr.printParams();
DateUtil du = new DateUtil();
StringUtil su = new StringUtil();

RealstateMgr rMgr = new RealstateMgr();

String section = pr.getString("section");
String sDate = pr.getString("sDate");
String eDate = pr.getString("eDate");
String scope = pr.getString("scope");
String keyword = pr.getString("keyword");

if("list1".equals(section)){

	JSONArray rList = rMgr.getRealstateList();
	
	out.print(rList);
}else if("list2".equals(section)){
	
	String pcode = pr.getString("pcode");
	
	JSONArray rList = rMgr.getDvisionList(pcode);	
	out.print(rList);
}else if("section01".equals(section)){
	String type51 = pr.getString("type51");
	JSONArray rList = rMgr.getOnlineTrend(sDate,eDate,scope,keyword,type51);	
	out.print(rList);
}else if("section02".equals(section)){
	String code = pr.getString("code");
	JSONObject object = rMgr.getChannelInfo(sDate,eDate,scope,keyword,code);	
	out.print(object);
}else if("section03".equals(section)){
	String senti = pr.getString("senti");	
	String code = pr.getString("code");
	int nowPage = pr.getInt("nowPage" , 1);
	JSONObject object = rMgr.getRelInfo(sDate,eDate,scope,keyword,senti,code,nowPage,10);	
	out.print(object);
}

%>