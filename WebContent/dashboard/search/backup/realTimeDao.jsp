<%@page import="risk.util.ParseRequest"%>
<%@page import="risk.json.JSONObject"%>
<%@page import="risk.dashboard.search.RealTimeMgr"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%

JSONObject object = new JSONObject();
JSONObject object2 = new JSONObject();

RealTimeMgr realTimeMgr = new RealTimeMgr();

String naverBoardCode = "2305157";
String daumBoardCode = "2305159";

object2.put("naver", realTimeMgr.getRealTimeList(naverBoardCode));
object2.put("daum", realTimeMgr.getRealTimeList(daumBoardCode));
object.put("realTimeData", object2);

out.println(object);

%>