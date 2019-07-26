<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import = "risk.issue.*
                   ,risk.mail.*
                   ,risk.sms.AddressBookDao
                   ,risk.sms.AddressBookBean
                   ,risk.util.*
                   ,risk.search.*
                   ,risk.search.solr.*
                   ,risk.admin.log.*
                   ,java.util.ArrayList
                   ,risk.admin.classification.classificationMgr"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%
	
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	DateUtil du = new DateUtil();
	StringUtil su = new StringUtil();
	IssueMgr iMgr = new IssueMgr();
	
	IssueDataBean idBean = null;
	IssueCommentBean icBean = null;
	String typeCode= null;
	String script_str = null;
	
	String mode = pr.getString("mode");
	String md_seqs = pr.getString("md_seqs");
	
	//코드 정보
	typeCode = pr.getString("typeCode","");

	//hot keyword
	String keywordInfo = pr.getString("keywordInfo", "");
	
	//이슈  데이터 등록 관련
	idBean = new IssueDataBean();
	idBean.setI_seq(pr.getString("i_seq","0"));
	idBean.setIt_seq(pr.getString("it_seq","0"));
	idBean.setMd_seq(pr.getString("md_seq",""));
	idBean.setMd_pseq(pr.getString("md_pseq", ""));
	idBean.setId_regdate(pr.getString("id_regdate",""));	
	idBean.setId_title(su.dbString(pr.getString("id_title","")));
	idBean.setId_url(su.dbString(pr.getString("id_url","")));
	idBean.setId_writter(pr.getString("name",""));
	idBean.setId_content(su.dbString(pr.getString("id_content","")));
	idBean.setMd_site_name(pr.getString("md_site_name",""));
	idBean.setMd_site_menu(pr.getString("md_site_menu",""));
	idBean.setMd_same_ct(pr.getString("md_same_ct",""));
	idBean.setS_seq(pr.getString("s_seq",""));
	idBean.setSg_seq(pr.getString("sg_seqs",""));
	idBean.setMd_date(pr.getString("md_date",""));
	idBean.setId_mailyn("N");
	idBean.setId_useyn("Y");
	idBean.setM_seq(SS_M_NO);
	idBean.setMd_type(pr.getString("md_type"));
	idBean.setKeywordInfo(keywordInfo);
	idBean.setH_seq(pr.getString("h_seq", ""));
	idBean.setId_mobile("Y");
	
	if( mode.equals("insert") ) {
				
		// 이슈정보 등록		
		idBean.setId_seq(iMgr.insertIssueData(idBean,icBean,typeCode));  //이슈등록후 등록 번호 셋팅
		
		if(idBean.getId_seq() != null && !idBean.getId_seq().equals("")){
			// 자기사 이슈정보 등록		
			iMgr.insertIssueData_sub(idBean.getMd_seq(),icBean,typeCode, idBean);
		}
		
		script_str = "alert('이슈정보가 등록되었습니다.'); var obj = parent.document.getElementById('issueChkImg"+idBean.getMd_seq()+"'); \n obj.src='../../images/mobile/btn_03.gif';\n obj.title='이슈로 등록된 정보 입니다.';\n obj.onclick='';\n   parent.issue_cancel();\n";
					
	}else if(mode.equals("update")){
		idBean.setId_seq(pr.getString("id_seq"));
		
		// 이슈정보 수정	
		iMgr.updateIssueData(idBean,icBean,typeCode);  //이슈등록후 등록 번호 셋팅
		
		iMgr.updateChildIssueData(idBean,icBean,typeCode);
		
		script_str = "alert('이슈정보가 수정되었습니다.');parent.issue_cancel();";	
	}
%>
<script>
	<%out.print(script_str);%>
</script>