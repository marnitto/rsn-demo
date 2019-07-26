<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="
                java.util.ArrayList
                ,risk.util.*
                ,risk.admin.tier.*                
                "
%>  
<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	TierSiteMgr tsMgr = new TierSiteMgr();
	TierSiteBean tsBean = new TierSiteBean();
	
	String mode = pr.getString("mode");
	StringBuffer script = null;

	
	tsBean.setTs_seq(pr.getString("ts_seq"));
	tsBean.setTs_name(pr.getString("ts_name"));
	tsBean.setTs_type(pr.getString("ts_type"));
	//tsBean.setTs_rank(pr.getString("ts_rank"));
		
	
	if(mode.equals("insert")){
		script = new StringBuffer();
		script.append("<script>");
		script.append("alert('저장하였습니다');");			
		script.append("</script>");
		
		tsMgr.insertTierSite(tsBean);
		
		out.print(script.toString());	
	}else if(mode.equals("update")){
		script = new StringBuffer();
		script.append("<script>");
		script.append("alert('수정하였습니다');");
		script.append("</script>");	
		
		tsMgr.updateTierSite(tsBean);
		
		out.print(script.toString());	
	}else if(mode.equals("delete")){
		script = new StringBuffer();
		script.append("<script>");
		script.append("alert('삭제하였습니다');");
		script.append("</script>");	
		
		tsMgr.deleteTierSite(tsBean);
	}
%>
