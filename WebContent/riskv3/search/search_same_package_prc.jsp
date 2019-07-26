
<%@page import="java.util.ArrayList"%><%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="risk.util.*
				 ,risk.search.* 
				 ,risk.issue.IssueMgr  
				 ,risk.issue.IssueDataBean
                 "
%>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	String md_seqs = pr.getString("md_seqs","");
	String nowpage = pr.getString("nowpage");
	MetaMgr mgr = new MetaMgr(); 
	
	int check = 0;
	Boolean result = false;
	
	
	
	if(!md_seqs.equals("")){
		int cnt = md_seqs.split(",").length;
		check =  mgr.getPackGubun(md_seqs);

		if(check == 0){
			//이슈등록 안된것끼리 묶을때~	
			String pseqs = mgr.getSamePseq(md_seqs);
			if(!pseqs.equals("")){
				mgr.UpdateSamePseq(pseqs);
				mgr.DeleteSamePseq(pseqs);
				
				result = true;
			}
			
			
		}else if(check == cnt){
			//이슈등록 된것끼리 묶을때~	
			String pseqs = mgr.getSamePseq(md_seqs);
			if(!pseqs.equals("")){
				mgr.UpdateSamePseq(pseqs);
				mgr.UpdateSameIssuePseq(pseqs);
				mgr.DeleteSamePseq(pseqs);
				
				result = true;
			}
		}else{
			//이슈등록 된것과 안된것 석였을때~
			String yes_md_seq = mgr.getIssueSeq(md_seqs, "YES");
			String yes_pseqs = mgr.getSamePseq(yes_md_seq);
			
			String no_md_seq = mgr.getIssueSeq(md_seqs, "NO");
			String no_pseqs = mgr.getSamePseq(no_md_seq);
			
			//이슈등록 안된것 끼리 먼저 합치기
			if(!no_pseqs.equals("") &&  no_pseqs.split(",").length > 1){
				mgr.UpdateSamePseq(no_pseqs);
				mgr.DeleteSamePseq(no_pseqs);
			}
			
			//합쳐진 데이터 이슈등록
			IssueMgr iMgr = new IssueMgr();
			IssueDataBean bean = iMgr.getIssueDataInfo(no_pseqs.split(",")[0], yes_pseqs.split(",")[0]);
			String typeCode = iMgr.getTypeCode(yes_pseqs.split(",")[0]);
			String last = iMgr.insertIssueData(bean,null,typeCode,"");
			bean.setId_seq(last);  //이슈등록후 등록 번호 셋팅
			iMgr.insertIssueData_sub(bean.getMd_seq(),null,typeCode, bean, "");
			
			//이슈 등록된것끼리 합치기~
			String sumSeq = "";
			if(yes_pseqs.equals("")){
				sumSeq = no_pseqs.split(",")[0];
			}else{
				sumSeq = yes_pseqs + "," + no_pseqs.split(",")[0];
			}
			
			mgr.UpdateSamePseq(sumSeq);
			mgr.UpdateSameIssuePseq(sumSeq);
			mgr.DeleteSamePseq(sumSeq);
			
			result = true;
			
		}
		
	}
	
%>

<script>
<!--
	var result = '<%=result == true ? 1 : 0%>';

	

    if(result == 1){        
    	alert('유사묶기 완료하였습니다.');
  		top.bottomFrame.leftFrame.location.reload();
        parent.document.location = "search_main_contents.jsp?nowpage=<%=nowpage%>";
    }else{
    	alert('유사묶기 실패하였습니다.');
    }
    
-->
</script>