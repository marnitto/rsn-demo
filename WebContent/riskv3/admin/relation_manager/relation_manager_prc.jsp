<%@page import="risk.admin.relation_manager.RelationManagerMgr"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="risk.util.ParseRequest"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	String rk_seq = pr.getString("rk_seq");
	String rk_name = pr.getString("rk_name");
	String mode = pr.getString("mode");
	
	RelationManagerMgr rmMgr = new RelationManagerMgr();
	
	if(mode.equals("sum")){
		String merge = rmMgr.relationkeywordMerge(rk_name, rk_seq);
		if(merge.equals("success")){
			%>
			<script type="text/javascript">
					alert("합치기 성공!");
					opener.Search();
					window.close();
					opener.window.close();
			</script>
			<%
		} else{
			%>
			<script type="text/javascript">
					alert("합치기 실패! 다시 시도해 주세요.");
					window.close();
			</script>
			<%
		}
	}
	else if(mode.equals("insert")){
		if(rmMgr.insertKeyword(rk_name)){
			%>
			<script type="text/javascript">
					alert("저장하였습니다.");
					window.opener.location.reload();
					window.close();
			</script>
			<%
		}
	}
	else if(mode.equals("update")){
		if(rmMgr.updateKeyword(rk_seq, rk_name)){
			%>
			<script type="text/javascript">
					alert("수정하였습니다.");
					window.opener.location.reload();
					window.close();
			</script>
			<%
		}
	}
	else if(mode.equals("delete")){
		if(rmMgr.deleteKeyword(rk_seq)){
			%>
			<script type="text/javascript">
					parent.location.reload();
			</script>
			<%
		}
	}
%>