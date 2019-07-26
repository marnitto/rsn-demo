<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import = "risk.issue.*
                   ,risk.mail.*
                   ,risk.sms.AddressBookDao
                   ,risk.sms.AddressBookBean
                   ,risk.util.*
                   ,risk.search.*
                   ,risk.namo.*
                   ,risk.admin.log.*
                   ,java.util.ArrayList
                   ,java.io.File"%>
                   
<%@ include file="../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	//pr.printParams();
	DateUtil du = new DateUtil();
	ConfigUtil cu = new ConfigUtil();
	MailTool mail = new MailTool();
	GoogleSmtp gMail = new GoogleSmtp();
	StringUtil su = new StringUtil();
	IssueMgr iMgr = new IssueMgr();
	MetaMgr metaMgr = new MetaMgr();
	IssueReportMgr irMgr = new IssueReportMgr();
	LogMgr logMgr = new LogMgr();
	LogBean logBean = new LogBean();
	NamoMime mime = new NamoMime();
	
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
	
	String[] arrDelseq = null;	
	String mode = pr.getString("mode");
	
	String smsreceiver = pr.getString("smsreceiver","");
	
	
	
	//메일 발송 관련
	String m_seq = SS_M_ID; 				              //등록자
	String sendMailUser = SS_M_MAIL;			          //보낸이
	String mailreceiver = pr.getString("mailreceiver");	  //보낼 수신자 번호
	
	//리포트 관련
	String ir_type = pr.getString("ir_type");
	String ir_title = pr.getString("ir_title");
	String ir_html = pr.getString("ir_html");
	String ir_seq  = pr.getString("ir_seq");
	
	//이슈 리포트 삭제
	if(mode.equals("delete"))
	{
		//삭제번호어레이
		arrDelseq = pr.getString("delseq").split(",");
		
		//리포트 삭제	
		irMgr.deleteReport(pr.getString("delseq"));			
		
		if(arrDelseq.length>0)
		{
			//로그 저장
			logBean = new LogBean();		
			logBean.setL_kinds(LogConstants.getReportKindsVal());
			logBean.setL_type(LogConstants.getDeleteTypeVal());
			logBean.setL_ip(request.getRemoteAddr());
			logBean.setM_seq(SS_M_NO);
			for(int i=0; i<arrDelseq.length; i++)
			{
				logBean.setKey(arrDelseq[i]);
				logMgr.insertLog(logBean);
			}		
		}
	
		script_str = "alert('리포트를 삭제하였습니다.'); \n"
		   			 +"parent.document.location='issue_report_list.jsp?ir_type="+pr.getString("ir_type")+"'";
	//이슈 리포트 저장
	}else if(mode.equals("insert")){
		
		//Namo 2011.02.09  임승철		
		String namoDataPath = cu.getConfig("NAMOPATH");
	    String namoDataUrl = cu.getConfig("NAMOURL");
	    String upsDir = "namoImages/report/"+ir_type+"/y" + du.getDate("yyyy") + "/" + du.getDate("MMdd") + "/"+ du.getDate("HHmmss");
	    
	 	// 저장할 절대경로
	    namoDataPath = namoDataPath + upsDir;	    
	 	// 저장할 상대경로
	    namoDataUrl = namoDataUrl + upsDir;
	    //System.out.println("namoDataPath:"+namoDataPath);
	    //System.out.println("namoDataUrl:"+namoDataUrl);
	    
	    //저장 경로 디텍토리가 없으면 생성해주고 나모 저장경로 셋팅
	    if(!new File(namoDataPath).isDirectory()) new File(namoDataPath).mkdirs();
	    
	    if(pr.getString("reportType").equals("1")){

		    if(pr.getString("IEuse").equals("Y")){
		    	mime.setSaveURL(namoDataUrl);
			    mime.setSavePath(namoDataPath);
			    
			    mime.decode(ir_html); // MIME 디코딩   		   
			    mime.saveFile();
			    
			    ir_html = mime.getBodyContent("UTF-8");
		    }
	    }
		//리포트 저장후 로그 저장
		irBean = new IssueReportBean();
		logBean = new LogBean();						
		
		irBean.setIr_title(ir_title);
		//irBean.setIr_html(su.dbString(ir_html));//나모 HTML 셋팅
		irBean.setIr_html(su.dbString(ir_html).replaceAll("\n","").replaceAll((new Character((char)13)).toString(),"").replaceAll((new Character((char)9)).toString(),"").replaceAll((new Character((char)11)).toString(),""));//나모 HTML 셋팅
		
		irBean.setIr_memo("");
		irBean.setIr_type(ir_type);
		irBean.setIr_mailyn("N");
		irBean.setIr_regdate(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));		
				
		logBean.setKey(irMgr.insertReport(irBean)); //리포트 저장후 리포트 번호 셋팅
		logBean.setL_kinds(LogConstants.getReportKindsVal());
		logBean.setL_type(LogConstants.getInsertTypeVal());
		logBean.setL_ip(request.getRemoteAddr());
		logBean.setM_seq(SS_M_NO);
		logBean.setL_seq(logMgr.insertLog(logBean)); //로그 저장후 로그 번호 셋팅		
		
		script_str = "alert('보고서를 저장하였습니다.');  parent.close();\n";
				
	}else if(mode.equals("update")){
		
		//Namo 2011.02.09  임승철		
		String namoDataPath = cu.getConfig("NAMOPATH");
	    String namoDataUrl = cu.getConfig("NAMOURL");
	    String upsDir = "namoImages/report/"+ir_type+"/y" + du.getDate("yyyy") + "/" + du.getDate("MMdd") + "/"+ du.getDate("HHmmss");
	    
	 	// 저장할 절대경로
	    namoDataPath = namoDataPath + upsDir;	    
	 	// 저장할 상대경로
	    namoDataUrl = namoDataUrl + upsDir;
	 	
	 	//저장 경로 디텍토리가 없으면 생성해주고 나모 저장경로 셋팅
	    if(!new File(namoDataPath).isDirectory()) new File(namoDataPath).mkdirs();
	    
	    if(pr.getString("reportType").equals("1")){

		    if(pr.getString("IEuse").equals("Y")){
		    	mime.setSaveURL(namoDataUrl);
			    mime.setSavePath(namoDataPath);
			    
			    mime.decode(ir_html); // MIME 디코딩   		   
			    mime.saveFile();
			    
			    ir_html = mime.getBodyContent("UTF-8");
		    }
	    }   
	    if(irMgr.UpdateReport(ir_seq, su.dbString(ir_html))){
	    	script_str = "alert('보고서를 수정하였습니다.');  parent.close();\n";
	    }
	
	}else if(mode.equals("mail")){
					
		irBean = new IssueReportBean();
		logBean = new LogBean();
		
		irBean = irMgr.getReportBean(pr.getString("ir_seq")); //리포트 정보 
		
		logBean.setKey(pr.getString("ir_seq")); //리포트 번호 셋팅
		logBean.setL_kinds(LogConstants.getReportKindsVal());
		logBean.setL_type(LogConstants.getMailTypeVal());
		logBean.setL_ip(request.getRemoteAddr());
		logBean.setM_seq(SS_M_NO);
		logBean.setL_seq(logMgr.insertLog(logBean)); //로그 저장후 로그 번호 셋팅		
	
		//수신자 정보 
		arrABBean = new ArrayList();
		arrABBean = abDao.getAddressBook(mailreceiver);
		
		String receipt = cu.getConfig("URL") + "riskv3/report/report_receipt.jsp"; 
		
		
		//메일발송
		if(arrABBean.size()>0){			
			for( int i=0 ; i <arrABBean.size() ; i++ )
			{			
				abBean = (AddressBookBean)arrABBean.get(i);							
				sendMailUser = "daegu@buzzms.co.kr";
				if(sendMailUser.length()>0){
					sendResult = gMail.sendmessage(abBean.getMab_mail(), sendMailUser, su.cutString(irBean.getIr_title(), 45, "..."), irBean.getIr_html().replaceAll("report_receipt", receipt + "?info=" + logBean.getL_seq() + "," + abBean.getMab_seq()), true);	
				}else{
					sendResult = gMail.sendmessage(abBean.getMab_mail(), su.cutString(irBean.getIr_title(), 45, "..."),  irBean.getIr_html().replaceAll("report_receipt", receipt + "?info=" + logBean.getL_seq() + "," + abBean.getMab_seq()), true);
				}					
			}
			//메일 수신자 로그
			logMgr.insertLogReceiver(logBean.getL_seq(),mailreceiver.split(","));
		}
		
		//SMS발송
		SmsSend ss = new SmsSend();
		String[] ar_sms_receiver = smsreceiver.split(",");
		String phone = "";
		String msg = "";
		for(int i =0; i < ar_sms_receiver.length; i++){
			phone = ar_sms_receiver[i].replaceAll("-","").replaceAll(" ","");
			ss.SendSMS(phone,su.cutString(irBean.getIr_title(), 40, "..."));
			
		}
		
		if(sendResult){
			irBean.setIr_mailyn("Y");
			irMgr.updateMailYN(irBean);
			script_str = "parent.document.getElementById('sending').style.display = 'none'; alert('메일수신자에게 메일을 발송합니다.');  parent.document.location.reload();\n";
		}else{
			irBean.setIr_mailyn("F");
			irMgr.updateMailYN(irBean);
			script_str = "parent.document.getElementById('sending').style.display = 'none'; alert('메일 발송에 실패하셨습니다.');  parent.document.location.reload();\n";
		}
	}
%>

<%@page import="risk.sms.SmsSend"%><script language="JavaScript" type="text/JavaScript">
<%=script_str%>
</script>