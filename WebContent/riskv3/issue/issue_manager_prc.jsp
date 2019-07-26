<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="	risk.issue.IssueMgr
					,java.util.ArrayList
					,risk.util.ParseRequest				
					,risk.issue.IssueBean
					,risk.issue.IssueTitleBean		
					,risk.issue.IssueMgr											
                 	,risk.util.DateUtil
                 	,risk.util.PageIndex
                 	,risk.util.StringUtil" %>
<%
	
	ParseRequest 	pr 		= new ParseRequest(request);	
	IssueMgr 	issueMgr 	= new IssueMgr();	
	DateUtil 		du 		= new DateUtil();
	StringUtil		su		= new StringUtil();	
	IssueBean iBean = new IssueBean();
	IssueTitleBean itBean = new IssueTitleBean();
	pr.printParams();
	boolean result = false;
	String resultScript = "";
	String mode = pr.getString("mode");
	String subMode = pr.getString("subMode");
	
	if(mode.equals("issue")){
		if(subMode.equals("insert")){
			iBean = new IssueBean();
			iBean.setI_title(pr.getString("i_title"));	
			iBean.setM_seq(SS_M_NO);
			iBean.setI_useyn("Y");
			if(issueMgr.insertIssue(iBean)){
				resultScript = "이슈를 등록하였습니다.";
			}
		}else if(subMode.equals("updateFlag")){
			iBean = new IssueBean();
			iBean.setI_seq(pr.getString("i_seq"));
			iBean.setI_title(pr.getString("i_title"));	
			iBean.setM_seq(SS_M_NO);			
			if(issueMgr.updateIssueFlag(iBean)){
				resultScript = "이슈의 상태를 변경하였습니다.";
			}
			
		}else if(subMode.equals("update")){
			iBean = new IssueBean();
			iBean.setI_seq(pr.getString("i_seq"));
			iBean.setI_title(pr.getString("i_title"));	
			iBean.setM_seq(SS_M_NO);
			iBean.setI_useyn("Y");
			if(issueMgr.updateIssue(iBean)){
				resultScript = "이슈를 수정하였습니다.";
			}
		}else if(subMode.equals("delete")){
			iBean = new IssueBean();
			iBean.setI_seq(pr.getString("i_seq"));			
			if(issueMgr.deleteIssue(iBean)){
				resultScript = "이슈를 삭제하였습니다.";
			}else{
				resultScript = "주제가 존재 합니다.";
			}
		}
	}if(mode.equals("issueTitle")){
		if(subMode.equals("insert")){
			itBean = new IssueTitleBean();
			itBean.setI_seq(pr.getString("i_seq"));
			itBean.setIt_title(pr.getString("it_title"));	
			itBean.setM_seq(SS_M_NO);
			itBean.setIt_useyn("Y");
			if(issueMgr.insertIssueTitle(itBean)){
				resultScript = "주제를 등록하였습니다.";
			}
		}else if(subMode.equals("update")){
			itBean = new IssueTitleBean();
			itBean.setIt_seq(pr.getString("it_seq"));			
			itBean.setIt_title(pr.getString("it_title"));	
			itBean.setM_seq(SS_M_NO);
			itBean.setIt_useyn("Y");
			if(issueMgr.updateIssueTitle(itBean)){
				resultScript = "주제를 수정하였습니다.";
			}
		}else if(subMode.equals("updateFlag")){
			itBean = new IssueTitleBean();
			itBean.setIt_seq(pr.getString("it_seq"));			
			itBean.setIt_title(pr.getString("it_title"));	
			itBean.setM_seq(SS_M_NO);
			if(issueMgr.updateIssueTitleFlag(itBean)){
				resultScript = "주제의 상태를 변경하였습니다..";
			}
		}else if(subMode.equals("delete")){
			itBean = new IssueTitleBean();
			itBean.setIt_seq(pr.getString("it_seq"));			
			if(issueMgr.deleteIssueTitle(itBean)){
				resultScript = "주제를 삭제하였습니다.";
			}
		}
	}
	
%>
<%=resultScript%>