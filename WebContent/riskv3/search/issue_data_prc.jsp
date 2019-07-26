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
	MailTool mail = new MailTool();
	StringUtil su = new StringUtil();
	IssueMgr iMgr = new IssueMgr();
	MetaMgr metaMgr = new MetaMgr();
	IssueReportMgr irMgr = new IssueReportMgr();
	LogMgr logMgr = new LogMgr();
	LogBean logBean  = new LogBean();
	AddressBookDao abDao = new AddressBookDao();
	GoogleSmtp_report gMail = new GoogleSmtp_report();
	
	ArrayList metaList = new ArrayList();
	MetaBean mBean = null;
	IssueDataBean idBean = null;
	IssueCommentBean icBean = null;
	IssueReportBean irBean = null;
	ArrayList arrABBean = null;
	AddressBookBean abBean = null;
	String typeCode= null;
	String reportHtml= null;
	String script_str = "";
	boolean sendResult = false;
	
	classificationMgr cMgr = new classificationMgr();
	
	//프로세스모드(save ,save&mail)
	String mode = pr.getString("mode");
	String md_seqs = pr.getString("md_seqs");
	
	//코드 정보
	typeCode = pr.getString("typeCode","");
	
	//hot keyword
	String keywordInfo = pr.getString("keywordInfo", "");
	
	//메일 내용
	String ir_html = "";
	//메일 발송 관련
	String m_seq = SS_M_ID; 				              //등록자
	String sendMailUser = SS_M_MAIL;			          //보낸이
	String mailreceiver = pr.getString("mailreceiver");	  //보낼 수신자 번호
	
	if( mode.equals("insert") ) {
				
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
		idBean.setRelationkeys(pr.getString("keyNames", ""));

		if(iMgr.issueChk(idBean.getMd_seq()) == 0){
			System.out.println("typeCode : "+typeCode);
			// 이슈정보 등록		
			idBean.setId_seq(iMgr.insertIssueData(idBean,icBean,typeCode));  //이슈등록후 등록 번호 셋팅
			
			if(idBean.getId_seq() != null && !idBean.getId_seq().equals("")){
				// 자기사 이슈정보 등록		
				iMgr.insertIssueData_sub(idBean.getMd_seq(),icBean,typeCode, idBean);
				//태그등록
				String tagCode = pr.getString("regTagCode", "");
				if(!tagCode.equals("")){
					iMgr.regTagCode(tagCode, idBean.getId_seq());
				}
			}
			out.print(idBean.getMd_seq());
		}else{
			out.print("already");
		}
			        
					
	}else if(mode.equals("multi")) {
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
				idBean.setId_regdate(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));	
				idBean.setId_title(su.dbString(mBean.getMd_title()));
				idBean.setId_url(su.dbString(mBean.getMd_url()));
				idBean.setId_content(su.dbString(mBean.getHighrightHtml(1000,"black")));
				idBean.setMd_site_name(mBean.getMd_site_name());
				idBean.setMd_site_menu(mBean.getMd_site_menu());
				idBean.setS_seq(mBean.getS_seq());
				idBean.setSg_seq(mBean.getSg_seq());
				idBean.setRelationkeys(pr.getString("keyNames", ""));
				//멀티등록시 출처 자동 발췌
				String tempTypeCode = "";
				tempTypeCode = metaMgr.getSiteCode(mBean.getSg_seq());
				System.out.println("출처 : " + tempTypeCode);
				/* 
				*멀티 등록시 출처가 중복 저장되는 문제가 있어 newTypeCode 변수를 사용해 오류 수정
				*2014.12.30
				*/
				if(typeCode.equals("")){
					typeCode = tempTypeCode;
				}else{
					if(!tempTypeCode.equals("")){
						newTypeCode = typeCode +"@"+tempTypeCode;
						System.out.println("newTypeCode : " + newTypeCode + "["+i+"]");
					}
				}
				idBean.setMd_date(mBean.getFormatMd_date("yyyy-MM-dd HH:mm:ss"));		 
				idBean.setId_mailyn("N");
				idBean.setId_useyn("Y");
				idBean.setM_seq(SS_M_NO);
				idBean.setMd_type(mBean.getMd_type());
				idBean.setMd_same_ct(mBean.getMd_same_count());
				idBean.setH_seq(pr.getString("h_seq", ""));
				idBean.setId_seq(iMgr.insertIssueData(idBean,icBean,newTypeCode));  //이슈등록후 등록 번호 셋팅
				
				if(idBean.getId_seq() != null && !idBean.getId_seq().equals("")){
					// 자기사 이슈정보 등록		
					iMgr.insertIssueData_sub(idBean.getMd_seq(),icBean, newTypeCode, idBean);
					//태그등록
					String tagCode = pr.getString("regTagCode", "");
					if(!tagCode.equals("")){
						iMgr.regTagCode(tagCode, idBean.getId_seq());
					}
				}
				
				script_str += "@"+mBean.getMd_seq();
				System.out.println(script_str);
			}
		}
		out.print(script_str);
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
		idBean.setRelationkeys(pr.getString("keyNames", ""));
		if(iMgr.issueChk(idBean.getMd_seq()) == 0){
			
			// 이슈정보 등록
			idBean.setId_seq(iMgr.insertIssueData(idBean,icBean,typeCode));  //이슈등록후 등록 번호 셋팅
			
			if(idBean.getId_seq() != null && !idBean.getId_seq().equals("")){
				// 자기사 이슈정보 등록		
				iMgr.insertIssueData_sub(idBean.getMd_seq(),icBean,typeCode, idBean);
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
						sendResult = gMail.sendmessage_report(abBean.getMab_mail(), sendMailUser, su.cutString(irBean.getIr_title(), 45, "..."), ir_html, true);	
					}else{
						sendResult = gMail.sendmessage_report(abBean.getMab_mail(), su.cutString(irBean.getIr_title(), 45, "..."), ir_html, true);
					}						
				}
				//메일 수신자 로그
				logMgr.insertLogReceiver(logBean.getL_seq(),mailreceiver.split(","));
			}
			
			out.print(idBean.getMd_seq());
			
		}else{
			out.print("already");
		}
		
	}
%>