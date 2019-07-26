<%@page contentType="text/html;charset=utf-8" %>
<%@page import="java.util.*"%>
<%@page import="java.util.regex.PatternSyntaxException"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.awt.*"%>
<%@page import="java.awt.Image"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="javax.swing.ImageIcon"%>
<%@page import="java.io.File"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="org.apache.commons.fileupload.FileUploadException"%>
<%@page import="org.apache.commons.fileupload.FileUploadBase"%>

<%@page import="risk.crosseditor.crosseditor"%>

<%@include file="Util.jsp"%>
<%@include file="SecurityTool.jsp"%>
<%
	String encType = "utf-8"; 
	
	if(detectXSSEx(request.getParameter("licenseCheck")) != null){
		if(detectXSSEx(request.getParameter("licenseCheck")).toLowerCase().equals("true")){
			response.getWriter().println(InetAddress.getLocalHost().getHostAddress());
			return;
		}
	}
	
	int maxSize = Integer.parseInt(detectXSSEx(request.getParameter("imageSizeLimit")));
	String defaultUPath = detectXSSEx(request.getParameter("defaultUPath"));
	String imageUPath = detectXSSEx(request.getParameter("imageUPath"));
	String imageUPathHost = "http://" + request.getHeader("host");
	String imagePhysicalPath = "";
%>
<%@include file="ImagePath.jsp"%>
<%
	crosseditor co = new crosseditor();
	co.coress(request, response, getServletConfig().getServletContext(), maxSize, defaultUPath, imageUPath, imageUPathHost, imagePhysicalPath);
%>
