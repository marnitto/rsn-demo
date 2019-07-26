<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
				 risk.admin.info.*,
                 risk.util.*" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	ParseRequest    pr = new ParseRequest(request);
	DateUtil du = new DateUtil();
	InfoGroupMgr igMgr = new InfoGroupMgr();
	InfoGroupBean igBean = new InfoGroupBean();
	pr.printParams();
	
	String checkIds = pr.getString("checkIds");
	String mode = pr.getString("mode");
	igBean.setI_seq(pr.getString("i_seq"));
	igBean.setI_nm(pr.getString("i_nm"));
	igBean.setI_regdate(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
	igBean.setI_useyn(pr.getString("i_useyn"));
	igBean.setM_seq(SS_M_NO);
	

	if(mode.equals("insert")){
		if(igMgr.insertInfoGroup(igBean))
		{
%>
			 <script type="text/javascript">
			 	alert('저장하였습니다.');
			 	opener.loadList();
			 	window.close();
			 </script>
<%
		}
	}else if(mode.equals("update")){		
		
		
		if(igMgr.updateInfoGroup(igBean))
		{	%>
			 <script type="text/javascript">
			 	alert('저장하였습니다.');
			 	opener.loadList();
			 	window.close();	
			 </script>
<%
		}

	}else if(mode.equals("delete")){
		if(igMgr.DeleteInfoGroup(checkIds))
		{
%>
			 <script type="text/javascript">
			 	alert('삭제하였습니다.');
			 	parent.loadList();
			 	window.close();	
			 </script>

<%
		}
	}else if(mode.equals("infoinsert")){
		if(igMgr.insertInfoGroup(igBean))
		{
%>
			 <script type="text/javascript">
			 	alert('저장하였습니다.');
			 	opener.select();
			 	window.close();
			 </script>

<%
		}
	}
%>