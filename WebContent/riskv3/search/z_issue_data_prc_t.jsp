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
	IssueMgr_t iMgr = new IssueMgr_t();
	MetaMgr_t metaMgr = new MetaMgr_t();
	IssueReportMgr irMgr = new IssueReportMgr();
	LogMgr logMgr = new LogMgr();
	LogBean logBean  = new LogBean();
	AddressBookDao abDao = new AddressBookDao();
	
	ArrayList metaList = new ArrayList();
	MetaBean mBean = null;
	IssueDataBean idBean = null;
	IssueCommentBean icBean = null;
	IssueReportBean irBean = null;
	ArrayList arrABBean = null;
	AddressBookBean abBean = null;
	String typeCode= null;
	String reportHtml= null;
	String script_str = null;
	boolean sendResult = false;
	
	classificationMgr cMgr = new classificationMgr();
	
	//프로세스모드(save ,save&mail)
	String mode = pr.getString("mode");
	String md_seqs = pr.getString("md_seqs");
	
	//코드 정보
	typeCode = pr.getString("typeCode","");

	//메일 발송 관련
	String m_seq = SS_M_ID; 				              //등록자
	String sendMailUser = SS_M_MAIL;			          //보낸이
	String mailreceiver = pr.getString("mailreceiver");	  //보낼 수신자 번호
	
	//보고서 관련
	String ir_html = "";
	
	if( mode.equals("insert") ) {
		
		if(iMgr.IssueRegistCheck(pr.getString("md_pseq","0")) == 0){
			//이슈  데이터 등록 관련
			idBean = new IssueDataBean();
			idBean.setI_seq(pr.getString("i_seq","0"));
			idBean.setIt_seq(pr.getString("it_seq","0"));
			idBean.setMd_seq(pr.getString("md_seq",""));		
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
			idBean.setL_alpha(pr.getString("l_alpha"));
			idBean.setMd_pseq(pr.getString("md_pseq","0"));
			idBean.setId_reportyn(pr.getString("ra_report","Y"));
			//idBean.setK_xp(pr.getString("keyTypeXp"));
			//idBean.setK_yp(pr.getString("keyTypeYp"));
			idBean.setMedia_info(pr.getString("media_info"));
			idBean.setF_news(pr.getString("f_news"));
			
			String last = iMgr.insertIssueData(idBean,icBean,typeCode);
					
			// 이슈정보 등록		
			idBean.setId_seq(last);  //이슈등록후 등록 번호 셋팅
			
			if(!last.equals("") &&  last != null){
				// 자기사 이슈정보 등록		
				if(!idBean.getMd_site_menu().equals("SOLR") && !idBean.getSg_seq().equals("30")){
					iMgr.insertIssueData_sub(idBean.getMd_seq(),icBean,typeCode, idBean);
				}	
			}
			
			//script_str = "parent.document.getElementById('sending').style.display = 'none'; alert('이슈정보가 등록되었습니다.'); var obj = parent.opener.document.getElementById('issue_menu_icon"+idBean.getMd_seq()+"'); \n obj.src='../../images/search/btn_manage_on.gif';\n obj.title='이슈로 등록된 정보 입니다.';\n obj.onclick='';\n   parent.close();\n";
			script_str = "parent.document.getElementById('sending').style.display = 'none'; var obj = parent.opener.document.getElementById('issue_menu_icon"+idBean.getMd_seq()+"'); \n obj.src='../../images/search/btn_manage_on.gif';\n obj.title='이슈로 등록된 정보 입니다.';\n obj.onclick='';\n   parent.close();\n";
			//script_str = "alert('이슈정보가 등록되었습니다.'); parent.close(); parent.opener.document.location.reload();\n";
		}else{
			script_str = "parent.document.getElementById('sending').style.display = 'none'; alert('이슈로 등록되었거나 등록 예정인 정보 입니다.');\n";
		}
		
			        
					
	}if( mode.equals("simplesave") ) {
		
		//META 정보
		mBean = new MetaBean();
		mBean = metaMgr.getMetaData(pr.getString("md_seq"));
		
		//이슈  데이터 등록 관련
		idBean = new IssueDataBean();
		idBean.setI_seq(pr.getString("i_seq","0"));
		idBean.setIt_seq(pr.getString("it_seq","0"));
		idBean.setMd_seq(pr.getString("md_seq",""));
		idBean.setId_regdate(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));	
		idBean.setId_title(su.dbString(mBean.getMd_title()));
		idBean.setId_url(su.dbString(mBean.getMd_url()));
		idBean.setId_content("");
		idBean.setMd_site_name(mBean.getMd_site_name());
		idBean.setMd_site_menu(mBean.getMd_site_menu());
		idBean.setS_seq(mBean.getS_seq());
		idBean.setSg_seq(mBean.getSg_seq());
		idBean.setMd_date(mBean.getFormatMd_date("yyyy-MM-dd HH:mm:ss"));		 
		idBean.setId_mailyn("N");
		idBean.setId_useyn("Y");
		idBean.setM_seq(SS_M_NO);		
		idBean.setId_seq(iMgr.insertIssueData(idBean,icBean,typeCode));  //이슈등록후 등록 번호 셋팅
		
		script_str = "alert('이슈정보가 등록되었습니다.');\n var obj = parent.document.getElementById('issue_menu_icon"+mBean.getMd_seq()+"'); \n obj.src='../../images/search/btn_manage_on.gif';\n obj.title='이슈로 등록된 정보 입니다.';\n obj.onclick='';\n";
		//script_str = "alert('이슈정보가 등록되었습니다.');\n parent.document.location.reload();\n";
	} else if ( mode.equals("solrsave") ){
		
		SolrMgr solrMgr = new SolrMgr();
				
		String sg_seq = "0";
		String sg_name = "";
		String s_seq = "0";
		String s_name = "";
		
		String md_seq   = pr.getString("md_seq","0");
		String md_title = pr.getString("md_title", pr.getString("id_title",""));
		String md_url   = pr.getString("md_url", pr.getString("id_url",""));
		
		
		String[] arrSgroup = solrMgr.getSiteGroup(pr.getString("s_seq","0"));
		
		if(arrSgroup != null && arrSgroup.length > 0){
			sg_seq  = su.nvl(arrSgroup[0], "0");
			sg_name = arrSgroup[1];
			s_seq   = su.nvl(arrSgroup[2], "0") ;
			s_name  = arrSgroup[3];
		}
		
		if(s_name.equals("")){
			s_name = pr.getString("md_site_name","");
		}
		
		//이슈  데이터 등록 관련
		idBean = new IssueDataBean();
		idBean.setI_seq(pr.getString("i_seq"));
		idBean.setIt_seq(pr.getString("it_seq"));
		idBean.setMd_seq(md_seq);
		idBean.setId_regdate(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));	
		idBean.setId_title(su.dbString(md_title));
		idBean.setId_url(su.dbString(md_url));
		idBean.setId_content(pr.getString("id_content",""));
		idBean.setMd_site_name(s_name);
		idBean.setMd_site_menu("SOLR");
		idBean.setS_seq(s_seq);
		idBean.setSg_seq(sg_seq);
		idBean.setMd_date(du.getDate(pr.getString("md_date"), "yyyy-MM-dd HH:mm:ss"));		 
		idBean.setId_mailyn("N");
		idBean.setId_useyn("Y");
		idBean.setM_seq(SS_M_NO);		
		idBean.setId_seq(iMgr.insertIssueData(idBean,icBean,typeCode));  //이슈등록후 등록 번호 셋팅
		
		if((false == idBean.getId_seq().equals("") && Integer.parseInt(idBean.getId_seq()) > 0)){
			if(solrMgr.SetIssueSolr(idBean.getId_seq(), md_seq)){		
				//script_str = "alert('이슈정보가 등록되었습니다.');\n var obj = parent.document.getElementById('issueChkImg"+md_seq+"'); \n obj.src='images/search/yellow_star.gif';\n obj.title='이슈로 등록된 정보 입니다.';\n obj.onclick='';\n";
				script_str = "alert('이슈정보가 등록되었습니다.');\n parent.changeIssueIcon("+md_seq+");";
			}else{
				script_str = "alert('이슈등록실패'); parent.close();\n";
			}
		}else{
			script_str = "alert('이슈등록실패'); parent.close();\n";
		}
	
	}else if(mode.equals("insert&mail")){
		
		//META 정보
		mBean = new MetaBean();
		mBean = metaMgr.getMetaData(pr.getString("md_seq"));
		
		//이슈  데이터 등록 관련
		idBean = new IssueDataBean();
		idBean.setI_seq(pr.getString("i_seq"));
		idBean.setIt_seq(pr.getString("it_seq"));
		idBean.setMd_seq(pr.getString("md_seq",""));
		idBean.setId_regdate(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));	
		idBean.setId_title(su.dbString(mBean.getMd_title()));
		idBean.setId_url(su.dbString(mBean.getMd_url()));
		idBean.setId_content(su.dbString(pr.getString("id_content","")));
		idBean.setMd_site_name(mBean.getMd_site_name());
		idBean.setMd_site_menu(mBean.getMd_site_menu());
		idBean.setS_seq(mBean.getS_seq());
		idBean.setSg_seq(mBean.getSg_seq());
		idBean.setMd_date(mBean.getFormatMd_date("yyyy-MM-dd HH:mm:ss"));		 
		idBean.setId_mailyn("Y");
		idBean.setId_useyn("Y");
		idBean.setM_seq(SS_M_NO);
		idBean.setMd_type(pr.getString("md_type"));
		idBean.setId_seq(iMgr.insertIssueData(idBean,icBean,typeCode));  //이슈등록후 등록 번호 셋팅		
		
		
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
					sendResult = mail.sendMail(abBean.getMab_mail(), irBean.getIr_title(), ir_html, sendMailUser);	
				}else{
					sendResult = mail.sendMail(abBean.getMab_mail(), irBean.getIr_title(), ir_html);	
				}				
			}
		}		
		//메일 수신자 로그
		logMgr.insertLogReceiver(logBean.getL_seq(),mailreceiver.split(","));
		
		if(sendResult){
			script_str = "alert('이슈정보가 등록되었습니다.\\n메일수신자에게 메일을 발송하였습니다.');\n var obj = parent.opener.document.getElementById('issue_menu_icon"+mBean.getMd_seq()+"'); \n obj.src='../search/images/layer_edit_red.gif';\n obj.title='이슈로 등록된 정보 입니다.';\n obj.onclick='';\n parent.close();\n  ";
		}else{
			irBean.setIr_mailyn("F");
			irMgr.updateMailYN(irBean);
			script_str = "alert('이슈정보가 등록되었습니다.\\n메일발송에 실패하였습니다.');\n var obj = parent.opener.document.getElementById('issue_menu_icon"+mBean.getMd_seq()+"'); \n obj.src='../search/images/layer_edit_red.gif';\n obj.title='이슈로 등록된 정보 입니다.';\n obj.onclick='';\n parent.close();\n  ";
		}
//		if(sendResult){
//			script_str = "alert('이슈정보가 등록되었습니다.\\n메일수신자에게 메일을 발송합니다.');\n parent.opener.document.location.reload();\n parent.close();\n  ";
//		}else{
//			irBean.setIr_mailyn("F");
//			irMgr.updateMailYN(irBean);
//			script_str = "alert('이슈정보가 등록되었습니다.\\n메일발송에 실패하였습니다.');\n parent.opener.document.location.reload();\n parent.close();\n  ";
//		}
	}if( mode.equals("update") ) {
				
		//이슈  데이터 등록 관련
		idBean = new IssueDataBean();
		idBean.setId_seq(pr.getString("id_seq"));
		idBean.setI_seq(pr.getString("i_seq","0"));
		idBean.setIt_seq(pr.getString("it_seq","0"));
		idBean.setMd_seq(pr.getString("md_seq"));
		idBean.setId_regdate(pr.getString("id_regdate"));	
		idBean.setId_title(su.dbString(pr.getString("id_title")));
		idBean.setId_url(su.dbString(pr.getString("id_url")));
		idBean.setId_writter(pr.getString("name",""));
		idBean.setId_content(su.dbString(pr.getString("id_content")));
		idBean.setMd_site_name(pr.getString("md_site_name"));
		idBean.setMd_site_menu(pr.getString("md_site_menu"));
		idBean.setS_seq(pr.getString("s_seq"));
		idBean.setSg_seq(pr.getString("sg_seq"));
		idBean.setMd_date(pr.getString("md_date"));	
		idBean.setId_useyn("Y");
		idBean.setId_mailyn(pr.getString("id_mailyn"));
		idBean.setM_seq(SS_M_NO);
		
			
		// 이슈정보 수정	
		iMgr.updateIssueData(idBean,icBean,typeCode);  //이슈등록후 등록 번호 셋팅		
		script_str = "alert('이슈정보가 수정되었습니다.'); parent.close();\n";			         
					
	}if( mode.equals("update&mail") ) {
	
		//이슈  데이터 등록 관련
		idBean = new IssueDataBean();
		idBean.setId_seq(pr.getString("id_seq"));
		idBean.setI_seq(pr.getString("i_seq","0"));
		idBean.setIt_seq(pr.getString("it_seq","0"));
		idBean.setMd_seq(pr.getString("md_seq"));
		idBean.setId_regdate(pr.getString("id_regdate"));	
		idBean.setId_title(su.dbString(pr.getString("id_title")));
		idBean.setId_url(su.dbString(pr.getString("id_url")));
		idBean.setId_writter(pr.getString("name",""));
		idBean.setId_content(su.dbString(pr.getString("id_content")));
		idBean.setMd_site_name(pr.getString("md_site_name"));
		idBean.setMd_site_menu(pr.getString("md_site_menu"));
		idBean.setS_seq(pr.getString("s_seq"));
		idBean.setSg_seq(pr.getString("sg_seq"));
		idBean.setMd_date(pr.getString("md_date"));	
		idBean.setId_useyn("Y");
		idBean.setId_mailyn(pr.getString("id_mailyn"));
		idBean.setM_seq(SS_M_NO);
		
		
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
						sendResult = mail.sendMail(abBean.getMab_mail(), irBean.getIr_title(), ir_html, sendMailUser);	
					}else{
						sendResult = mail.sendMail(abBean.getMab_mail(), irBean.getIr_title(), ir_html);	
					}					
			}
		}
		//메일 수신자 로그
		logMgr.insertLogReceiver(logBean.getL_seq(),mailreceiver.split(","));
		
		if(sendResult){
			script_str = "alert('이슈정보가 수정되었습니다.\\n메일수신자에게 메일을 발송하였습니다.');  parent.document.location.reload();\n";
		}else{
			irBean.setIr_mailyn("F");
			irMgr.updateMailYN(irBean);
			script_str = "alert('이슈정보가 수정되었습니다.\\n메일발송에 실패하였습니다.');\n parent.document.location.reload();\n  ";
		}
		
//		if(sendResult){
//			script_str = "alert('이슈정보가 수정되었습니다.\\n메일수신자에게 메일을 발송합니다.');  parent.document.location.reload();\n";
//		}else{
//			irBean.setIr_mailyn("F");
//			irMgr.updateMailYN(irBean);
//			script_str = "alert('이슈정보가 수정되었습니다.\\n메일발송에 실패하였습니다.');\n parent.document.location.reload();\n  ";
//		}
	}else if(mode.equals("multi")) {
		
		String md_pseqs = metaMgr.Alter_mdSeq_mdPseq(md_seqs);
		
		if(iMgr.IssueRegistCheck(md_pseqs) == 0){
			if(!md_seqs.equals("")){
				metaList = metaMgr.getMetaDataList(md_seqs);
				
				for(int i=0;i<metaList.size();i++)
				{
					mBean = new MetaBean();
					mBean = (MetaBean)metaList.get(i);
					
					//이슈  데이터 등록 관련
					idBean = new IssueDataBean();
					idBean.setI_seq(pr.getString("i_seq","0"));
					idBean.setIt_seq(pr.getString("it_seq","0"));
					idBean.setMd_seq(mBean.getMd_seq());
					idBean.setId_regdate(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));	
					idBean.setId_title(su.dbString(mBean.getMd_title()));
					idBean.setId_url(su.dbString(mBean.getMd_url()));
					idBean.setId_content(su.dbString(mBean.getHighrightHtml(1000,"black")));
					idBean.setMd_site_name(mBean.getMd_site_name());
					idBean.setMd_site_menu(mBean.getMd_site_menu());
					idBean.setS_seq(mBean.getS_seq());
					idBean.setSg_seq(mBean.getSg_seq());
					idBean.setMd_date(mBean.getFormatMd_date("yyyy-MM-dd HH:mm:ss"));		 
					idBean.setId_mailyn("N");
					idBean.setId_useyn("Y");
					idBean.setM_seq(SS_M_NO);
					idBean.setMd_type(mBean.getMd_type());
					idBean.setMd_same_ct(mBean.getMd_same_count());
					idBean.setL_alpha(mBean.getL_alpha());
					idBean.setMd_pseq(mBean.getMd_pseq());
					idBean.setId_reportyn(pr.getString("ra_report","Y"));
					//idBean.setK_xp(pr.getString("keyTypeXp"));
					//idBean.setK_yp(pr.getString("keyTypeYp"));
					idBean.setMedia_info(pr.getString("media_info"));
					idBean.setF_news(pr.getString("f_news",""));
					
					String last = iMgr.insertIssueData(idBean,icBean,typeCode);
					
					idBean.setId_seq(last);  //이슈등록후 등록 번호 셋팅	
					
					if(!last.equals("") &&  last != null){
						iMgr.insertIssueData_sub(idBean.getMd_seq(),icBean,typeCode,idBean);	
					}
					
					
					script_str += "\n var obj"+i+" = parent.opener.document.getElementById('issue_menu_icon"+mBean.getMd_seq()+"'); \n obj"+i+".src='../../images/search/btn_manage_on.gif';\n obj"+i+".title='이슈로 등록된 정보 입니다.';\n obj"+i+".onclick='';\n";
				}
				
				script_str += "alert('이슈정보가 등록되었습니다.'); parent.close();"	;
			}
		}else{
			script_str = "alert('이슈로 등록되었거나 등록 예정인 정보가 있습니다.');\n";
		}
		
		
		
	}else if(mode.equals("samemulti")) {
		if(!md_seqs.equals("")){
			metaList = metaMgr.getMetaDataList(md_seqs);
			
			for(int i=0;i<metaList.size();i++)
			{
				mBean = new MetaBean();
				mBean = (MetaBean)metaList.get(i);
				
				//이슈  데이터 등록 관련
				idBean = new IssueDataBean();
				idBean.setI_seq(pr.getString("i_seq","0"));
				idBean.setIt_seq(pr.getString("it_seq","0"));
				idBean.setMd_seq(mBean.getMd_seq());
				idBean.setId_regdate(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));	
				idBean.setId_title(su.dbString(mBean.getMd_title()));
				idBean.setId_url(su.dbString(mBean.getMd_url()));
				idBean.setId_content(su.dbString(mBean.getHighrightHtml(1000,"black")));
				idBean.setMd_site_name(mBean.getMd_site_name());
				idBean.setMd_site_menu(mBean.getMd_site_menu());
				idBean.setS_seq(mBean.getS_seq());
				idBean.setSg_seq(mBean.getSg_seq());
				idBean.setMd_date(mBean.getFormatMd_date("yyyy-MM-dd HH:mm:ss"));		 
				idBean.setId_mailyn("N");
				idBean.setId_useyn("Y");
				idBean.setM_seq(SS_M_NO);
				idBean.setMd_type(mBean.getMd_type());
				idBean.setMd_same_ct(mBean.getMd_same_count());
				idBean.setL_alpha(mBean.getL_alpha()); 
				idBean.setId_seq(iMgr.insertIssueData(idBean,icBean,typeCode));  //이슈등록후 등록 번호 셋팅
				
				
				script_str += "\n var obj"+i+" = parent.opener.document.getElementById('issue_menu_icon"+mBean.getMd_seq()+"'); \n obj"+i+".src='../../images/search/btn_manage_on.gif';\n obj"+i+".title='이슈로 등록된 정보 입니다.';\n obj"+i+".onclick='';\n";
			}
			
			script_str += "alert('이슈정보가 등록되었습니다.'); parent.close();"	;
		}
	}
	
%>

<script language="JavaScript" type="text/JavaScript">
<%=script_str%>
</script>
