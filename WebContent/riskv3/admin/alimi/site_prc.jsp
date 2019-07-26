<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="	java.util.ArrayList
					,java.net.*
                 	,risk.mobile.AlimiSettingBean
					,risk.mobile.AlimiSettingDao
					,risk.util.ParseRequest
					,risk.admin.site.SitegroupBean
					,risk.admin.site.SiteBean
				 	,risk.admin.site.SiteMng
				 	,risk.search.userEnvMgr
				 	,risk.search.keywordInfo
				 	,risk.admin.classification.classificationMgr
				 	,risk.admin.classification.clfBean
				 	,java.util.List
					" 					
%>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	AlimiSettingDao asDao = new AlimiSettingDao();
	AlimiSettingBean asBean = null;	

	String selectedSdGsn = pr.getString("selectedSdGsn","");
	String mode = pr.getString("mode","");
	String as_seq  = pr.getString("as_seq","");
	
	asDao.updateSiteList(as_seq, selectedSdGsn);	
%>
