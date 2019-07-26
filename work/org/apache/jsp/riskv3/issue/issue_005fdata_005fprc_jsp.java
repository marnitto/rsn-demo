package org.apache.jsp.riskv3.issue;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.issue.*;
import risk.mail.*;
import risk.sms.AddressBookDao;
import risk.sms.AddressBookBean;
import risk.util.*;
import risk.search.*;
import risk.admin.log.*;
import java.util.ArrayList;
import risk.sms.SmsSend;
import risk.admin.classification.classificationMgr;
import risk.util.ConfigUtil;

public final class issue_005fdata_005fprc_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/issue/../inc/sessioncheck.jsp");
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("                   \r\n");
//@ page contentType="text/html; charset=euc-kr"
      out.write("\r\n");
      out.write("\r\n");

    
	String SS_M_NO = (String)session.getAttribute("SS_M_NO")   == null ? "": (String)session.getAttribute("SS_M_NO")  ;
    String SS_M_ID = (String)session.getAttribute("SS_M_ID")   == null ? "": (String)session.getAttribute("SS_M_ID")  ;
    String SS_M_NAME = (String)session.getAttribute("SS_M_NAME") == null ? "": (String)session.getAttribute("SS_M_NAME");
    String SS_MG_NO = (String)session.getAttribute("SS_MG_NO")  == null ? "": (String)session.getAttribute("SS_MG_NO") ;
	String SS_TITLE = (String)session.getAttribute("SS_TITLE")  == null ? "": (String)session.getAttribute("SS_TITLE");
    String SS_URL =   (String)session.getAttribute("SS_URL")    == null ? "": (String)session.getAttribute("SS_URL") ;
    String SS_M_DEPT =   (String)session.getAttribute("SS_M_DEPT")    == null ? "": (String)session.getAttribute("SS_M_DEPT") ;    
    String SS_M_IP =   (String)session.getAttribute("SS_M_IP")    == null ? "": (String)session.getAttribute("SS_M_IP") ;    
    String SS_M_MAIL =   (String)session.getAttribute("SS_M_MAIL")    == null ? "": (String)session.getAttribute("SS_M_MAIL") ;    
    
	String SS_ML_URL = javax.servlet.http.HttpUtils.getRequestURL(request).toString();
	
	String SS_SEARCHDATE = (String)session.getAttribute("SS_SEARCHDATE")    == null ? "": (String)session.getAttribute("SS_SEARCHDATE") ;
	
	
    

	if ((SS_M_ID.equals("")) || !SS_M_IP.equals(request.getRemoteAddr()) ) {
		ConfigUtil cu = new ConfigUtil();
		out.print("<SCRIPT Language=JavaScript>");
		//out.print("window.setTimeout( \" top.document.location = "+cu.getConfig("URL")+"'riskv3/error/sessionerror.jsp' \") ");
		out.print("top.document.location = '"+cu.getConfig("URL")+"riskv3/error/sessionerror.jsp'");
        out.print("</SCRIPT>");
    }
	

      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
	

	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	DateUtil du = new DateUtil();
	//MailTool mail = new MailTool();
	GoogleSmtp gMail = new GoogleSmtp();
	GoogleSmtp_report gMail2 = new GoogleSmtp_report();
	StringUtil su = new StringUtil();
	IssueMgr iMgr = new IssueMgr();
	MetaMgr metaMgr = new MetaMgr();
	IssueReportMgr irMgr = new IssueReportMgr();
	LogMgr logMgr = new LogMgr();
	LogBean logBean = new LogBean();
	
	ArrayList metaList = new ArrayList();
	MetaBean mBean = null;
	IssueDataBean idBean = null;
	IssueCommentBean icBean = null;
	IssueReportBean irBean = null;	
	AddressBookDao abDao = new AddressBookDao();
	ArrayList arrABBean = null;
	AddressBookBean abBean = null;
	String typeCode= null;
	String reportHtml= null;
	String script_str = null;
	boolean sendResult = false;
	
	
	classificationMgr cMgr = new classificationMgr();
	String child = pr.getString("child");
	//프로세스모드
	String mode = pr.getString("mode");
	String md_seqs = pr.getString("md_seqs");
	String p_seq = pr.getString("p_seq","");
	
	//이슈정보 검색 조건 유지	
	String nowPage = pr.getString("nowPage");	
	
	//코드 정보
	typeCode = pr.getString("typeCode","");

	//메일 발송 관련
	String m_seq = SS_M_ID; 				              //등록자
	String sendMailUser = SS_M_MAIL;			          //보낸이
	String mailreceiver = pr.getString("mailreceiver");	  //보낼 수신자 번호
	
	//보고서 관련
	String ir_html = "";
	
	//hot keyword
	String keywordInfo = pr.getString("keywordInfo", "");
	
	//이슈리스트에서 체크된 관련정보를 긴급보고서로 메일 발송
	if( mode.equals("insert") ) {
				
		if(iMgr.IssueRegistCheck(pr.getString("md_pseq","0")) == 0){
			//이슈  데이터 등록 관련
			idBean = new IssueDataBean();
			idBean.setI_seq(pr.getString("i_seq","0"));
			idBean.setIt_seq(pr.getString("it_seq","0"));
			idBean.setMd_seq(pr.getString("md_seq",""));
			idBean.setMd_pseq(pr.getString("md_pseq", ""));
			idBean.setId_regdate(pr.getString("id_regdate",""));	
			idBean.setId_title(su.dbString(pr.getString("id_title","")));
			idBean.setId_url(su.dbString(pr.getString("id_url","")));
			idBean.setId_writter(SS_M_NAME);
			idBean.setId_content(su.dbString(pr.getString("id_content","")));
			idBean.setMd_site_name(pr.getString("md_site_name",""));
			idBean.setMd_site_menu(pr.getString("md_site_menu",""));
			idBean.setMd_same_ct(pr.getString("md_same_ct",""));
			idBean.setS_seq(pr.getString("s_seq",""));
			idBean.setSg_seq(pr.getString("sg_seq",""));
			idBean.setMd_date(pr.getString("md_date",""));
			idBean.setId_mailyn("N");
			idBean.setId_useyn("Y");
			idBean.setM_seq(SS_M_NO);
			idBean.setMd_type(pr.getString("md_type"));
			idBean.setKeywordInfo(keywordInfo);
			idBean.setH_seq(pr.getString("h_seq", ""));
			idBean.setId_mobile("N");
			idBean.setK_xp(pr.getString("k_xp"));
			idBean.setRelationkeys(su.dbString(pr.getString("keyNames", "")));
			//============================================================
			idBean.setUser_id(su.dbString(pr.getString("user_id")));
			idBean.setUser_nick(su.dbString(pr.getString("user_nick")));
			idBean.setBlog_visit_count(pr.getString("blog_visit_count"));
			idBean.setCafe_name(su.dbString(pr.getString("cafe_name")));
			idBean.setCafe_member_count(pr.getString("cafe_member_count"));
			//============================================================
	
			idBean.setD_seq(pr.getString("d_seq",""));
					
			if(iMgr.issueChk(idBean.getMd_seq()) == 0){
				//System.out.println("typeCode : "+typeCode);
				// 이슈정보 등록		
				idBean.setId_seq(iMgr.insertIssueData(idBean,icBean,typeCode,p_seq));  //이슈등록후 등록 번호 셋팅
				
				if(idBean.getId_seq() != null && !idBean.getId_seq().equals("")){
					// 자기사 이슈정보 등록		
					iMgr.insertIssueData_sub(idBean.getMd_seq(),icBean,typeCode, idBean ,p_seq);
					//태그등록
					/* 
					String tagCode = pr.getString("regTagCode", "");
					if(!tagCode.equals("")){
						iMgr.regTagCode(tagCode, idBean.getId_seq());
					} 
					*/
				}
				script_str = idBean.getMd_seq();
			}else{
				script_str = "a";
			}
		}else{
			//이미 부모키가 저장되어 있으므로 유사 데몬에서 저장 할 예정이기 때문에 수동 저장을 막는다.
			System.out.println("등록된 유사글이 있습니다.");
			script_str = "a";
		}
			        
					
	}else if(mode.equals("multi")) {
		String md_pseqs = metaMgr.Alter_mdSeq_mdPseq(md_seqs);
		
		if(iMgr.IssueRegistCheck(md_pseqs) == 0){
			if(!md_seqs.equals("")){
				metaList = metaMgr.getMetaDataList(md_seqs);
				
				for(int i=0;i<metaList.size();i++)
				{
					mBean = new MetaBean();
					mBean = (MetaBean)metaList.get(i);
					String newTypeCode = "";
					
					//이슈  데이터 등록 관련
					idBean = new IssueDataBean();
					idBean.setI_seq(pr.getString("i_seq","0"));
					idBean.setIt_seq(pr.getString("it_seq","0"));
					idBean.setMd_seq(mBean.getMd_seq());
					idBean.setMd_pseq(mBean.getMd_pseq());
					idBean.setId_writter(SS_M_NAME);
					idBean.setId_regdate(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));	
					idBean.setId_title(su.dbString(mBean.getMd_title()));
					idBean.setId_url(su.dbString(mBean.getMd_url()));
					idBean.setId_content(su.dbString(mBean.getHighrightHtml(1000,"black")));
					idBean.setMd_site_name(mBean.getMd_site_name());
					idBean.setMd_site_menu(mBean.getMd_site_menu());
					idBean.setS_seq(mBean.getS_seq());
					idBean.setSg_seq(mBean.getSg_seq());
					idBean.setRelationkeys(su.dbString(pr.getString("keyNames", "")));
					idBean.setD_seq(mBean.getD_seq());
					
					//============================================================
					idBean.setUser_id(su.dbString(pr.getString("user_id")));
					idBean.setUser_nick(su.dbString(pr.getString("user_nick")));
					idBean.setBlog_visit_count(pr.getString("blog_visit_count"));
					idBean.setCafe_name(su.dbString(pr.getString("cafe_name")));
					idBean.setCafe_member_count(pr.getString("cafe_member_count"));
					//============================================================
					
					//System.out.println(" 연관키워드 : "+  idBean.getRelationkeys());
					
					//멀티등록시 출처 자동 발췌
					String tempTypeCode = "";
					tempTypeCode = metaMgr.getSiteCode(mBean.getSg_seq());
					System.out.println("출처 : " + tempTypeCode);
					/* 
					*멀티 등록시 출처가 중복 저장되는 문제가 있어 newTypeCode 변수를 사용해 오류 수정
					*2014.12.30
					*/
					//if(typeCode.equals("")){
					//	typeCode = tempTypeCode;
					//}else{
					//	if(!tempTypeCode.equals("")){
					//		newTypeCode = typeCode +"@"+tempTypeCode;
					//		//System.out.println("newTypeCode : " + newTypeCode + "["+i+"]");
					//	}
					//}
					
					idBean.setMd_date(mBean.getFormatMd_date("yyyy-MM-dd HH:mm:ss"));		 
					idBean.setId_mailyn("N");
					idBean.setId_useyn("Y");
					idBean.setM_seq(SS_M_NO);
					idBean.setMd_type(mBean.getMd_type());
					idBean.setMd_same_ct(mBean.getMd_same_count());
					idBean.setH_seq(pr.getString("h_seq", ""));
					idBean.setId_seq(iMgr.insertIssueData(idBean,icBean,typeCode,p_seq));  //이슈등록후 등록 번호 셋팅
					
					if(idBean.getId_seq() != null && !idBean.getId_seq().equals("")){
						// 자기사 이슈정보 등록		
						iMgr.insertIssueData_sub(idBean.getMd_seq(),icBean, typeCode, idBean ,p_seq);
						//태그등록
						/* String tagCode = pr.getString("regTagCode", "");
						if(!tagCode.equals("")){
							iMgr.regTagCode(tagCode, idBean.getId_seq());
						} */
					}
					if("".equals(script_str)){
						script_str = mBean.getMd_seq();
					}else{
						script_str += "@"+mBean.getMd_seq();	
					}
					
					System.out.println(script_str);
				}
			}
		}
		//out.print(script_str);
	}else if(mode.equals("insert&mail")){
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
		idBean.setSg_seq(pr.getString("sg_seq",""));
		idBean.setMd_date(pr.getString("md_date",""));
		idBean.setId_mailyn("N");
		idBean.setId_useyn("Y");
		idBean.setM_seq(SS_M_NO);
		idBean.setMd_type(pr.getString("md_type"));
		idBean.setKeywordInfo(keywordInfo);
		idBean.setH_seq(pr.getString("h_seq", ""));
		idBean.setId_mobile("N");
		idBean.setK_xp(pr.getString("k_xp"));
		idBean.setRelationkeys(su.dbString(pr.getString("keyNames", "")));
		//============================================================
		idBean.setUser_id(su.dbString(pr.getString("user_id")));
		idBean.setUser_nick(su.dbString(pr.getString("user_nick")));
		idBean.setBlog_visit_count(pr.getString("blog_visit_count"));
		idBean.setCafe_name(su.dbString(pr.getString("cafe_name")));
		idBean.setCafe_member_count(pr.getString("cafe_member_count"));
		//============================================================
		
		if(iMgr.issueChk(idBean.getMd_seq()) == 0){
			
			// 이슈정보 등록
			idBean.setId_seq(iMgr.insertIssueData(idBean,icBean,typeCode,p_seq));  //이슈등록후 등록 번호 셋팅
			
			if(idBean.getId_seq() != null && !idBean.getId_seq().equals("")){
				// 자기사 이슈정보 등록		
				iMgr.insertIssueData_sub(idBean.getMd_seq(),icBean,typeCode, idBean,p_seq);
				//태그등록
				String tagCode = pr.getString("regTagCode", "");
				if(!tagCode.equals("")){
					iMgr.regTagCode(tagCode, idBean.getId_seq());
				}
			}
			
			//리포트 저장후 로그 저장
			irBean = new IssueReportBean();
			logBean = new LogBean();
			
			//메일 제목에 [애니써치] 타이틀 삭입 요청 2015-03-03
			String issueTitle = pr.getString("id_title","");
			irBean.setIr_title("[이슈보고서]"+issueTitle);
			ir_html = su.encodingRequestPageByGet(SS_URL+"riskv3/report/issue_report_form.jsp?id_seq="+idBean.getId_seq()+"&ir_type="+ReportTypeConstants.getEmergencyVal(),"UTF-8");
			irBean.setIr_html(su.dbString(ir_html));
			irBean.setIr_memo("");
			irBean.setIr_type(ReportTypeConstants.getEmergencyVal());
			irBean.setIr_mailyn("Y");
			irBean.setIr_regdate(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
			
			logBean.setKey(irMgr.insertReport(irBean)); //리포트 저장후 리포트 번호 셋팅
			logBean.setL_kinds(LogConstants.getReportKindsVal());
			logBean.setL_type(LogConstants.getMailTypeVal());
			logBean.setL_ip(request.getRemoteAddr());
			logBean.setM_seq(SS_M_NO);
			logBean.setL_seq(logMgr.insertLog(logBean)); //로그 저장후 로그 번호 셋팅
			
			//수신자 정보 
			arrABBean = new ArrayList();
			arrABBean = abDao.getAddressBook(mailreceiver);		
			
			//메일발송
			if(arrABBean.size()>0){			
				
				for( int i=0 ; i <arrABBean.size() ; i++ )
				{			
					abBean = (AddressBookBean)arrABBean.get(i);		
					sendMailUser = "daegu@buzzms.co.kr";
					if(sendMailUser.length()>0){
						sendResult = gMail2.sendmessage_report(abBean.getMab_mail(), sendMailUser, su.cutString(irBean.getIr_title(), 45, "..."), ir_html, true);	
					}else{
						sendResult = gMail2.sendmessage_report(abBean.getMab_mail(), su.cutString(irBean.getIr_title(), 45, "..."), ir_html, true);
					}						
				}
				//메일 수신자 로그
				logMgr.insertLogReceiver(logBean.getL_seq(),mailreceiver.split(","));
			}
			
			out.print(idBean.getMd_seq());
			
		}else{
			out.print("already");
		}
		
	}else if(mode.equals("mail")){
		
		//리포트 저장후 로그 저장
		irBean = new IssueReportBean();
		logBean = new LogBean();
		
		//irBean.setIr_title(pr.getString("ir_title"));
		irBean.setIr_title("[이슈보고서]"+su.dbString(pr.getString("ir_title")));
		ir_html = su.encodingRequestPageByGet(SS_URL+"riskv3/report/issue_report_form.jsp?id_seq="+pr.getString("id_seq")+"&ir_type="+ReportTypeConstants.getEmergencyVal(),"UTF-8");
		irBean.setIr_html(su.dbString(ir_html));
		irBean.setIr_memo("");
		irBean.setIr_type(ReportTypeConstants.getEmergencyVal());
		irBean.setIr_mailyn("Y");
		irBean.setIr_regdate(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));		
				
		logBean.setKey(irMgr.insertReport(irBean)); //리포트 저장후 리포트 번호 셋팅
		logBean.setL_kinds(LogConstants.getReportKindsVal());
		logBean.setL_type(LogConstants.getMailTypeVal());
		logBean.setL_ip(request.getRemoteAddr());
		logBean.setM_seq(SS_M_NO);
		logBean.setL_seq(logMgr.insertLog(logBean)); //로그 저장후 로그 번호 셋팅
		
		
		//수신자 정보 
		arrABBean = new ArrayList();
		arrABBean = abDao.getAddressBook(mailreceiver);		
		
		//메일발송
		if(arrABBean.size()>0){			
			
			for( int i=0 ; i <arrABBean.size() ; i++ )
			{			
				abBean = (AddressBookBean)arrABBean.get(i);
				sendMailUser = "daegu@buzzms.co.kr";
				if(sendMailUser.length()>0){
					sendResult = gMail.sendmessage(abBean.getMab_mail(), sendMailUser, su.cutString(irBean.getIr_title(), 45, "..."), ir_html, true);	
				}else{
					sendResult = gMail.sendmessage(abBean.getMab_mail(), su.cutString(irBean.getIr_title(), 45, "..."), ir_html, true);
				}					
			}
			//메일 수신자 로그
			logMgr.insertLogReceiver(logBean.getL_seq(),mailreceiver.split(","));
		}
		if(sendResult){
			//script_str = "alert('메일수신자에게 메일을 발송하였습니다.'); parent.window.close(); \n";
			script_str = "success";	
		}else{
			irBean.setIr_mailyn("F");
			irMgr.updateMailYN(irBean);
			//script_str = "alert('메일발송에 실패하였습니다.');\n";
			script_str = "fail";			
		}
		
	
	//관련 정보 수정폼에서 관련정보를 업데이트 후 메일 발송
	}else if( mode.equals("update&mail") ) {	
		
		//이슈  데이터 등록 관련
		idBean = new IssueDataBean();
		idBean.setId_seq(pr.getString("id_seq"));
		idBean.setI_seq(pr.getString("i_seq","0"));
		idBean.setIt_seq(pr.getString("it_seq","0"));
		idBean.setMd_seq(pr.getString("md_seq"));
		idBean.setId_regdate(pr.getString("id_regdate"));	
		idBean.setId_title(su.dbString(pr.getString("id_title")));
		idBean.setId_url(su.dbString(pr.getString("id_url")));
		idBean.setId_writter(SS_M_NAME);
		idBean.setId_content(su.dbString(pr.getString("id_content")));
		idBean.setMd_site_name(pr.getString("md_site_name"));
		idBean.setMd_site_menu(pr.getString("md_site_menu"));
		idBean.setS_seq(pr.getString("s_seq"));
		idBean.setSg_seq(pr.getString("sg_seq"));
		idBean.setMd_date(pr.getString("md_date"));	
		idBean.setId_useyn("Y");
		idBean.setMd_type(pr.getString("md_type"));
		idBean.setId_mailyn(pr.getString("id_mailyn"));
		idBean.setM_seq(SS_M_NO);
		idBean.setM_name(SS_M_NAME);
		idBean.setRelationkeys(su.dbString(pr.getString("keyNames", "")));
		//============================================================
		idBean.setUser_id(su.dbString(pr.getString("user_id")));
		idBean.setUser_nick(su.dbString(pr.getString("user_nick")));
		idBean.setBlog_visit_count(pr.getString("blog_visit_count"));
		idBean.setCafe_name(su.dbString(pr.getString("cafe_name")));
		idBean.setCafe_member_count(pr.getString("cafe_member_count"));
		//============================================================
		// 기자 등록 프로세스
		if(!pr.getString("name","").equals("") && pr.getString("typeCode7","").equals(""))
		{
			cMgr.InsertClf(7,0,pr.getString("name",""),SS_M_NO);
			typeCode += "@"+cMgr.getInsertTypeCode(7);
		}
		
		// 이슈정보 수정	
		iMgr.updateIssueData(idBean,icBean,typeCode, p_seq);
		
		//리포트 저장후 로그 저장
		irBean = new IssueReportBean();
		logBean = new LogBean();
		
		irBean.setIr_title(idBean.getId_title());
		ir_html = su.encodingRequestPageByGet(SS_URL+"riskv3/issue/issue_report_form.jsp?id_seq="+idBean.getId_seq()+"&ir_type="+ReportTypeConstants.getEmergencyVal(),"UTF-8");
		irBean.setIr_html(su.dbString(ir_html));
		irBean.setIr_memo("");
		irBean.setIr_type(ReportTypeConstants.getEmergencyVal());
		irBean.setIr_mailyn("Y");
		irBean.setIr_regdate(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));		
				
		logBean.setKey(irMgr.insertReport(irBean)); //리포트 저장후 리포트 번호 셋팅
		logBean.setL_kinds(LogConstants.getReportKindsVal());
		logBean.setL_type(LogConstants.getMailTypeVal());
		logBean.setL_ip(request.getRemoteAddr());
		logBean.setM_seq(SS_M_NO);
		logBean.setL_seq(logMgr.insertLog(logBean)); //로그 저장후 로그 번호 셋팅
		
		//수신자 정보 
		arrABBean = new ArrayList();
		arrABBean = abDao.getAddressBook(mailreceiver);		
		
		//메일발송
		if(arrABBean.size()>0){			
			
			for( int i=0 ; i <arrABBean.size() ; i++ )
			{			
				abBean = (AddressBookBean)arrABBean.get(i);							
				if(sendMailUser.length()>0){
					sendResult = gMail.sendmessage(abBean.getMab_mail(), sendMailUser, su.cutString(irBean.getIr_title(), 45, "..."), ir_html, true);	
				}else{
					sendResult = gMail.sendmessage(abBean.getMab_mail(), su.cutString(irBean.getIr_title(), 45, "..."), ir_html, true);
				}						
			}
			//메일 수신자 로그
			logMgr.insertLogReceiver(logBean.getL_seq(),mailreceiver.split(","));
		}
				
		if(sendResult){
			script_str = "alert('이슈정보가 수정되었습니다.\\n메일수신자에게 메일을 발송하였습니다.');  parent.document.location.reload();\n";
		}else{
			irBean.setIr_mailyn("F");
			irMgr.updateMailYN(irBean);
			script_str = "alert('이슈정보가 수정되었습니다.\\n메일발송에 실패하였습니다.');  parent.document.location.reload();\n";
		}
	
	//관련 정보 수정폼에서 관련정보를 업데이트
	}else if( mode.equals("update") ) {
		
		//이슈  데이터 등록 관련
		idBean = new IssueDataBean();
		idBean.setId_seq(pr.getString("id_seq"));
		idBean.setI_seq(pr.getString("i_seq","0"));
		idBean.setIt_seq(pr.getString("it_seq","0"));
		idBean.setMd_seq(pr.getString("md_seq"));
		idBean.setId_regdate(pr.getString("id_regdate"));	
		idBean.setId_title(su.dbString(pr.getString("id_title")));
		idBean.setId_url(su.dbString(pr.getString("id_url")));
		idBean.setId_writter(SS_M_NAME);
		idBean.setId_content(su.dbString(pr.getString("id_content")));
		idBean.setMd_site_name(pr.getString("md_site_name"));
		idBean.setMd_site_menu(pr.getString("md_site_menu"));
		idBean.setS_seq(pr.getString("s_seq"));
		idBean.setSg_seq(pr.getString("sg_seq"));
		idBean.setMd_date(pr.getString("md_date"));	
		idBean.setId_useyn("Y");
		idBean.setId_mailyn(pr.getString("id_mailyn"));
		idBean.setMd_type(pr.getString("md_type"));
		idBean.setM_seq(SS_M_NO);
		idBean.setKeywordInfo(keywordInfo);
		idBean.setH_seq(pr.getString("h_seq", ""));
		idBean.setRelationkeys(su.dbString(pr.getString("keyNames", "")));
		//============================================================
		idBean.setUser_id(su.dbString(pr.getString("user_id")));
		idBean.setUser_nick(su.dbString(pr.getString("user_nick")));
		idBean.setBlog_visit_count(pr.getString("blog_visit_count"));
		idBean.setCafe_name(su.dbString(pr.getString("cafe_name")));
		idBean.setCafe_member_count(pr.getString("cafe_member_count"));
		//============================================================
		idBean.setD_seq(pr.getString("d_seq",""));
		
		// 기사 등록 프로세스
		//if(!pr.getString("name","").equals("") && pr.getString("typeCode7","").equals(""))
		//{
		//	cMgr.InsertClf(7,0,pr.getString("name",""),SS_M_NO);
		//	typeCode += "@"+cMgr.getInsertTypeCode(7);
		//}
		
		// 이슈정보 수정
		boolean ret = iMgr.updateIssueData(idBean,icBean,typeCode,p_seq);  //이슈등록후 등록 번호 셋팅
		 
		if("Y".equals(child)){
			iMgr.updateChildIssueData(idBean,icBean,typeCode,p_seq);
		}
		
		//태그등록
		//String tagCode = pr.getString("regTagCode", "");
		//if(!tagCode.equals("")){
		//	iMgr.regTagCode(tagCode, idBean.getId_seq());
		//}
//		script_str = "alert('이슈정보가 수정되었습니다.'); \n  parent.opener.document.location='issue_data_list.jsp?nowPage="+nowPage+"' \n parent.close();";	
//		script_str = "alert('이슈정보가 수정되었습니다.'); \n  try{parent.opener.search();parent.close();}catch(e){parent.close();}";
		if(ret){
			script_str = "success";	
		}else{
			script_str = "";
		}
		
		
	}else if( mode.equals("update_multi") ) {//멀티 수정
		//금감원 멀티 수정 기능
		//String id_seqs = pr.getString("id_seqs");
		//
		//String chk_idSeqs = iMgr.getIssueData_MdSeqs(id_seqs);
		//
		//String chk_types = pr.getString("chk_types");
		//String chk_typeCode = pr.getString("typeCode");
		//String chk_p_seq = pr.getString("p_seq");
		//
		//boolean ret = false;
		//ret = iMgr.MultiUpdate(chk_idSeqs, chk_types, chk_typeCode, chk_p_seq);
		//
		//if(ret){
		//	script_str = "success";
		//}else{
		//	script_str = "fail";
		//}
		
		
		// 서울 시청 멀티 수정 기능
		String id_seqs = pr.getString("id_seqs");
		String chk_idSeqs = iMgr.getIssueData_MdSeqs(id_seqs);
		String chk_typeCode = pr.getString("typeCode");
		boolean ret = false;
		ret = iMgr.issueDataMod(chk_idSeqs, chk_typeCode, SS_M_NO);
		if(ret){
			script_str = "success";
		}else{
			script_str = "fail";
		}
	
	//이슈리스트에서 체크된 관련정보를 삭제
	}else if( mode.equals("delete") ) {
		
		boolean ret = false;
		ret = iMgr.deleteIssueData(pr.getString("check_no"));		
		//script_str = "alert('이슈정보가 삭제되었습니다.'); \n  parent.document.location='issue_data_list.jsp?nowPage="+nowPage+"'";
		
		System.out.println("ret - "+ret);
		
		if(ret){
			script_str = "success";
		}else{
			script_str = "fail";
		}
		
	}else if( mode.equals("sms") ) {
		Boolean send = false;
		String Mesage = pr.getString("ir_title","");
		SmsSend sms = new SmsSend();
		
		//수신자 정보 
		arrABBean = new ArrayList();
		arrABBean = abDao.getAddressBook(mailreceiver);
		
		//로그저장
		logBean = new LogBean();
		logBean.setKey("0"); //리포트 저장후 리포트 번호 셋팅
		logBean.setL_kinds(LogConstants.getIssueKindsVal());
		logBean.setL_type(LogConstants.getSmsTypeVal());
		logBean.setL_ip(request.getRemoteAddr());
		logBean.setM_seq(SS_M_NO);
		logBean.setL_seq(logMgr.insertLog(logBean)); //로그 저장후 로그 번호 셋팅
		//SMS발송
		if(arrABBean.size()>0){			
			
			for( int i=0 ; i <arrABBean.size() ; i++ )
			{			
				abBean = (AddressBookBean)arrABBean.get(i);
				send = sms.SendSMS(abBean.getMab_mobile(),Mesage);
				
				if(!send){
					break;
				}
			}
			
			//SMS 수신자 로그
			logMgr.insertLogReceiver(logBean.getL_seq(),mailreceiver.split(","));
		}
		if(send){
			//script_str = "parent.document.getElementById('sending').style.display = 'none'; alert('SMS수신자에게 SMS를 발송하였습니다.'); parent.window.close(); \n";
			script_str = "success";
		}else{
			//script_str = "parent.document.getElementById('sending').style.display = 'none'; alert('SMS발송에 실패하였습니다.');\n";
			script_str = "fail";
		}
	}
	

      out.write('\r');
      out.write('\n');
out.println(script_str.trim());
      out.write('\r');
      out.write('\n');
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else log(t.getMessage(), t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
