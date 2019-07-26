<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.issue.IssueCodeMgr"%>
<%@ page import="risk.issue.IssueCodeBean"%>
<%@ page import="risk.issue.IssueBean"%>
<%@ page import="risk.issue.IssueMgr"%>
<%@ page import="risk.issue.IssueDataBean"%>	
<%@ page import="risk.admin.info.*"%>
<%
		ParseRequest pr = new ParseRequest(request);		
		pr.printParams();
		
		IssueCodeMgr 	icm = new IssueCodeMgr();		
		IssueMgr issueMgr = new IssueMgr();
		
		IssueDataBean idBean = new IssueDataBean();
				
		String selected = null;
		String mode = pr.getString("mode");
		System.out.println("mode : "+mode);
		String nowPage = pr.getString("nowPage");
		String subMode = pr.getString("subMode");
		String md_seq = pr.getString("md_seq");
		int ic_seq = 0;
		
		//분류체계코드 (0:분류항목 포함, 1:분류항목 제외)
		icm.init(0);
		
		//SMS대그룹  HOT ISSUE중그룹에 속하는 키워드
		ArrayList keywordInfo = new ArrayList();
		
		String k_xp = "";
		
		//모드에 따른 분기
		if(mode.equals("insert")){
			//메타 정보
			if(subMode.equals("solr")){

			 }else{
				 
			}
		}else if(mode.equals("update")){
			//이슈 정보
			idBean = issueMgr.getIssueDataBean(md_seq);
			
		}
		
		//이슈데이터 등록 관련
	   	IssueMgr isMgr = new IssueMgr();
	   	IssueBean isBean = new IssueBean();	   	   	
	   	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	   	
	   	//정보그룹 관련
	   	ArrayList igArr = new ArrayList();
	   	InfoGroupMgr igMgr = new InfoGroupMgr();
	   	InfoGroupBean igBean = new InfoGroupBean();
	   	igArr = igMgr.getInfoGroup("Y");	   	
%>
<table>
<select name="i_seq" id="i_seq" onchange="show_checkbox(this.value)">
<option value="">전체</option>
<%
	String infoChecked = "";
	boolean infoChk = false;
	for (int i = 0; i < igArr.size(); i++) {
		igBean = new InfoGroupBean();
		igBean = (InfoGroupBean) igArr.get(i);
		infoChecked = "";
		if(mode.equals("insert")){
			out.print("<option value='"+igBean.getI_seq()+"'>" + igBean.getI_nm()+ "&nbsp;&nbsp;&nbsp;");
		}else{
			if(idBean.getI_seq().equals(igBean.getI_seq())) infoChecked ="selected";infoChk = true;
			out.print("<option value='"+igBean.getI_seq()+"' "+infoChecked+">" + igBean.getI_nm()+ "&nbsp;&nbsp;&nbsp;");
		}
		
	}
%>	
</select>
</table>
