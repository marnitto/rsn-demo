<%@page import="risk.dashboard.opinion.OpinionMgr"%>
<%@page import="risk.json.JSONObject"%>
<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% 

ParseRequest pr = new ParseRequest(request);
pr.printParams();

JSONObject object = new JSONObject();
OpinionMgr opinionMgr = new OpinionMgr();

String section = pr.getString("section");
String scope = pr.getString("scope");
String keyword = pr.getString("keyword");
String sDate = pr.getString("sDate");
String eDate = pr.getString("eDate");
String pCode = pr.getString("pCode");
String source = pr.getString("source");
String limit = pr.getString("limit");
String senti = pr.getString("senti");

int nowPage = pr.getInt("nowPage");

if("0".equals(section)){
	JSONObject object2 = new JSONObject();
	object2.put("infoDiv", opinionMgr.getIssueCodeList("2"));
	object2.put("source", opinionMgr.getIssueCodeList("6"));
	object.put("initData", object2);
}else if("1".equals(pr.getString("section"))){
	object.put("sectionData1", opinionMgr.getinfoCountData(scope, keyword, sDate, eDate));
}else if("2".equals(pr.getString("section"))){
	object.put("sectionData2", opinionMgr.getWeekData(scope, keyword, sDate, eDate));
}else if("3".equals(pr.getString("section"))){
	object.put("sectionData3", opinionMgr.getInfoDivCountData(scope, keyword, sDate, eDate, pCode));
}else if("4".equals(pr.getString("section"))){
	object.put("sectionData4", opinionMgr.getMainIssueCountData(scope, keyword, sDate, eDate));
}else if("5".equals(pr.getString("section"))){
	object.put("sectionData5", opinionMgr.getMediaData(scope, keyword, sDate, eDate));
}else if("6".equals(pr.getString("section"))){
	object.put("sectionData6", opinionMgr.getMainMediaData(scope, keyword, sDate, eDate, source, limit));
}else if("7".equals(pr.getString("section"))){
	object.put("sectionData7", opinionMgr.getRelationKeywordData(scope, keyword, sDate, eDate, source, limit, senti));
	
}else if("8".equals(pr.getString("section"))){
	JSONObject object2 = new JSONObject();
	object2.put("pos", opinionMgr.getRelationKeywordSentiData(scope, keyword, sDate, eDate, "1"));
	object2.put("neg", opinionMgr.getRelationKeywordSentiData(scope, keyword, sDate, eDate, "2"));
	object2.put("neu", opinionMgr.getRelationKeywordSentiData(scope, keyword, sDate, eDate, "3"));
	object.put("sectionData8", object2);
}else if("9_1".equals(pr.getString("section"))){
	object.put("sectionData9_1", opinionMgr.getRelaltionInfoList(nowPage, 10, scope, keyword, sDate, eDate, "1", source));
}else if("9_2".equals(pr.getString("section"))){
	object.put("sectionData9_2", opinionMgr.getRelaltionInfoList(nowPage, 10, scope, keyword, sDate, eDate, "2", source));
}
out.println(object);

%>




