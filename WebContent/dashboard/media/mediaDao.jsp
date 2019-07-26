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
                 ,risk.dashboard.media.MediaMgr
                 "
%>
<%
ParseRequest pr = new ParseRequest(request);
pr.printParams();
DateUtil du = new DateUtil();
StringUtil su = new StringUtil();

MediaMgr mMgr = new MediaMgr();

String section = pr.getString("section");
String sDate = pr.getString("sDate");
String eDate = pr.getString("eDate");
String scope = pr.getString("scope");
String keyword = pr.getString("keyword");

if("list".equals(section)){
	String pcode = pr.getString("pcode");
	JSONArray rList = mMgr.getDvisionList(pcode);
	
	out.print(rList);
}else if("section01".equals(section)){
	String pcode = pr.getString("pcode");
	JSONArray rList = mMgr.getPublicityTrend(sDate,eDate,scope,keyword,pcode);
	
	out.print(rList);
}else if("section02".equals(section)){
	String pcode = pr.getString("pcode");
	JSONArray rList = mMgr.getChannelInfo(sDate,eDate,scope,keyword,pcode);
	
	out.print(rList);
}else if("section03_graph".equals(section)){
	String pcode = pr.getString("pcode");
	
	JSONArray rList = mMgr.getRealstateGraph(sDate, eDate, scope, keyword, pcode);
	
	out.print(rList);
}else if("section03_info".equals(section)){
	JSONObject result = new JSONObject();
	
	String pcode = pr.getString("pcode");
	int nowPage = pr.getInt("nowPage" , 1);
	
	result = mMgr.getRealstateInfo(sDate, eDate, scope, keyword, pcode,nowPage,10);
	
	out.print(result);
}

%>