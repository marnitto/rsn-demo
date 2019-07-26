<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" %>
<%@ include file="../../inc/sessioncheck.jsp" %>
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
		
	String mode = pr.getString("mode","INSERT");
	String siteName = pr.getString("siteName","");
	String sg_seqs = pr.getString("sg_seqs","");
	String as_seq        = pr.getString("as_seq","");
	String ab_seq = null;
	String nowpage = pr.getString("nowpage","");
	
	
	ArrayList alimiSetList = new ArrayList();
	List xpList = null;
	List sgList = null;
	List sdList = null;
	
	//AJAX  사용시 한글 처리
	URLDecoder ud = new URLDecoder();
	siteName = ud.decode(siteName,"UTF-8");
	siteName = siteName.trim();
	
	pr.printParams();
	if(mode.equals("UPDATE") && as_seq.length()>0)
	{
		ab_seq = asDao.getReceiverSeq(as_seq);
		alimiSetList = asDao.getAlimiSetList(1,0,as_seq,"Y");
		asBean = (AlimiSettingBean)alimiSetList.get(0);
	}
	
	SiteMng sitemng = new SiteMng();		
	if(!siteName.equals(""))sdList = sitemng.getList(siteName);		
%>

<%
	if(sdList!=null)
	{
%>
	

		<table width="100%" cellpadding=0 cellspacing=0 border="0"  bordercolor="black">			
			<tr>
				<td colspan="<%=sdList.size()%>" align="left" style="padding: 5px 0px 5px 0px;"><B>검색된 사이트  <%=sdList.size()%>건</B></td>
			</tr>
		</table>
		
		<div style="overflow-y:auto; width:370px; height:400px; border-top : 1px solid #999; border-left : 1px solid #999; border-bottom : 1px solid #999; border-right : 1px solid #999;">
		<table width="100%" cellpadding=0 cellspacing=0 border="1"  bordercolor="gray">
<%							
	
	
	for( int i=0 ; i < sdList.size() ; i++ ) {
			
		SiteBean sdInfo = (SiteBean)sdList.get(i);		
%>	
	   		<tr height=22>	
				<td style="padding: 0px 0px 0px 5px;">			  
			      	<a href="javascript:selectRightMove('<%=sdInfo.get_gsn()%>')"><%= sdInfo.get_name()%></a>
			    </td>
			</tr>
<%			  
	}
%>
  		</table>
  		</div>
<%
	}else{
%>
	

<%		
	}
%>