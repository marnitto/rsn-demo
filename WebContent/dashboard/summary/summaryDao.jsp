<%@page import="risk.dashboard.summary.SummaryMgr"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../riskv3/inc/sessioncheck.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "java.util.Enumeration" %>
<%@ page import = "java.util.Map" %>
<%@ page import="risk.json.JSONObject"%>
<%@ page import="risk.util.ParseRequest
                 ,risk.util.DateUtil
                 ,java.util.*
                 ,java.net.URLEncoder
                 ,risk.util.PageIndex
                 ,risk.util.StringUtil
                 ,risk.util.ConfigUtil
                 ,risk.dashboard.overview.OverviewMgr
                 "
%>
<%
ParseRequest pr = new ParseRequest(request);
DateUtil du = new DateUtil();
StringUtil su = new StringUtil();

//------------------------------------------------------------------------------

SummaryMgr sMgr = new SummaryMgr();

String section = pr.getString("section");
String scope = pr.getString("scope");
String keyword = pr.getString("keyword");
String sDate = pr.getString("sDate");
String eDate = pr.getString("eDate");
int nowPage = pr.getInt("nowPage" , 1);
JSONObject object = new JSONObject();
String tab = pr.getString("tab", "");	//탭 선택 값
String tier = pr.getString("tier", "");	 
//------------------------------------------------------------------------------

if("1".equals(section)){
	object.put("sectionData1", sMgr.getOnlineTrendData(scope, keyword, sDate, eDate,tier));
}else if("2".equals(section)){
	JSONObject object2 = new JSONObject();
	object2.put("data1", sMgr.getChannelSentiData01(scope, keyword, sDate, eDate, "02",tier));
	object2.put("data2", sMgr.getChannelSentiData02(scope, keyword, sDate, eDate, "02",tier));
	object.put("sectionData2", object2);
}else if("3".equals(section)){
	JSONObject object2 = new JSONObject();
	object2.put("data1", sMgr.getChannelSentiData01(scope, keyword, sDate, eDate, "03",tier));
	object2.put("data2", sMgr.getChannelSentiData02(scope, keyword, sDate, eDate, "03",tier));
	object.put("sectionData3", object2);
}else if("4".equals(section)){
	object.put("sectionData4", sMgr.getPotalTopList(scope, keyword, sDate, eDate, tab, nowPage));
}else if("6".equals(section)){
	object.put("sectionData6", sMgr.getSocialIssueList(nowPage, 10, scope, keyword, sDate, eDate));
}


//------------------------------------------------------------------------------
//포탈 댓글 관련 parameters
String portal_date = pr.getString("portal_date", "");
String searchKey = pr.getString("searchKey" , "");
String p_docid = pr.getString("p_docid" , "");
int type = pr.getInt("type");
int nowpage = pr.getInt("nowpage",1);

//------------------------------------------------------------------------------

sMgr.getLucySwitch();

if(type == 4){
	
	//object.put("section4", sMgr.getSummaryPortalList2(portal_date,nowpage,scope,keyword,tab));
	
	object.put("section4", sMgr.getSummaryPortalList3(portal_date,nowpage,keyword,"",SS_M_ID));
	
}else if(type == 5){
	
	object.put("section5", sMgr.getRelationKeyword(p_docid, portal_date, SS_M_ID) );
	
}else if(type == 6){

	object.put("msg", sMgr.portalreply_api(p_docid) );
}


out.println(object);


%>