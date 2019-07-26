<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "risk.util.*" %>
<%

POIExcelAdd poiAdd = new POIExcelAdd();

String filePath = request.getParameter("filePath");
filePath = filePath.trim();
System.out.println("filePath - "+filePath);
poiAdd.deleteExcelFile(filePath);

%>
