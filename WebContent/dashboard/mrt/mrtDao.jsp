<%@page import="risk.json.JSONObject"%>
<%@page import="risk.dashboard.mrt.MrtMgr"%>
<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% 
ParseRequest pr = new ParseRequest(request);
pr.printParams();

JSONObject object = new JSONObject();
MrtMgr mMgr = new MrtMgr();

String scope = pr.getString("scope");
String keyword = pr.getString("keyword");
String sDate = pr.getString("sDate");
String eDate = pr.getString("eDate");
String issueCode = pr.getString("issueCode");
String source = pr.getString("source");
String limit = pr.getString("limit");
String senti = pr.getString("senti");

String op = pr.getString("op", "1");
int nowPage = pr.getInt("nowPage");


if("0".equals(pr.getString("section"))){
	object.put("initData", mMgr.getMediaList());
}else if("1".equals(pr.getString("section"))){
	object.put("sectionData1", mMgr.getMainIssueData(nowPage, 10, scope, keyword, sDate, eDate, op));
}else if("1_1".equals(pr.getString("section"))){
	object.put("sectionData1_1", mMgr.getMainIssueData_trend(scope, keyword, sDate, eDate, issueCode));
}else if("1_2".equals(pr.getString("section"))){
	object.put("sectionData1_2", mMgr.getMainIssueData_neg(scope, keyword, sDate, eDate, issueCode));
}else if("2".equals(pr.getString("section"))){
	object.put("sectionData2", mMgr.getWeekData(scope, keyword, sDate, eDate, issueCode));
}else if("3".equals(pr.getString("section"))){
	object.put("sectionData3", mMgr.getMediaData(scope, keyword, sDate, eDate, issueCode));
}else if("4".equals(pr.getString("section"))){
	object.put("sectionData4", mMgr.getMainMediaData(scope, keyword, sDate, eDate, issueCode, source, limit));
}else if("5".equals(pr.getString("section"))){
	object.put("sectionData5", mMgr.getRelationKeywordData(scope, keyword, sDate, eDate, issueCode, source, limit, senti));
}else if("6".equals(pr.getString("section"))){
	JSONObject object2 = new JSONObject();
	object2.put("pos", mMgr.getRelationKeywordSentiData(scope, keyword, sDate, eDate, issueCode, "1"));
	object2.put("neg", mMgr.getRelationKeywordSentiData(scope, keyword, sDate, eDate, issueCode, "2"));
	object2.put("neu", mMgr.getRelationKeywordSentiData(scope, keyword, sDate, eDate, issueCode, "3"));
	object.put("sectionData6", object2);
}else if("7".equals(pr.getString("section"))){
	object.put("sectionData7", mMgr.getRelaltionInfoList(nowPage, 10, scope, keyword, sDate, eDate, issueCode, source));
}
out.println(object);

%>