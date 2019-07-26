<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.sql.*
			,risk.search.userEnvMgr
			,risk.search.userEnvInfo
			,risk.admin.member.MemberDao
			,risk.admin.member.MemberBean
			,risk.util.*
			,risk.DBconn.DBconn
			,risk.admin.log.LogMgr
			,risk.admin.log.LogBean
			,risk.admin.log.LogConstants"%>
<%
	ParseRequest pr = new ParseRequest(request);
	LoginManager lm = LoginManager.getInstance();
	LogMgr logMgr = new LogMgr();
	LogBean logBean = new LogBean();
	pr.printParams();
	String FimUserID = pr.getString("m_id") != null ? pr.getString("m_id") : "";
	String FimUserPasswd = pr.getString("m_pass") != null ? pr.getString("m_pass") : "";
	//String saveid = pr.getString("SaveId") != null ? pr.getString("SaveId") : "";
	
	
	String SS_M_NO = "";
	String SS_MG_NO = "";

	DBconn dbconn = null;
	ResultSet rs1 = null;
	ResultSet rs2 = null;
	Statement stmt1 = null;
	Statement stmt2 = null;
	String str_sql1 = "";
	String str_sql2 = "";

	boolean isLogin = false;
	
	ConfigUtil cu = new ConfigUtil();
	MemberDao mdao = new MemberDao();
	MemberBean member = null;

	/*
	if (!saveid.equals("")) {
		//==========  쿠키 저장 =============
		Cookie cookie = new Cookie("lifeRSNid", FimUserID);
		cookie.setMaxAge(30 * 24 * 60 * 60); //30일간 저장
		response.addCookie(cookie);
	}else {
		//==========  쿠키 삭제 =============
		Cookie cookie = new Cookie("lifeRSNid", "");
		cookie.setMaxAge(0); //쿠키삭제
		response.addCookie(cookie);
	}
	
	Cookie[] cookies = request.getCookies(); //쿠키를 가져옴 		
	 
	 if(cookies != null){			
		for(int i = 0;i<cookies.length;i++){			
			System.out.println("cookies["+i+"] length:"+cookies.length);				
			System.out.println("cookies["+i+"] name:"+cookies[i].getName());				
			System.out.println("cookies["+i+"] value:"+cookies[i].getValue());
			System.out.println("cookies["+i+"] Age:"+cookies[i].getMaxAge());
			System.out.println("cookies["+i+"] Path:"+cookies[i].getPath());
			System.out.println("cookies["+i+"] Domain:"+cookies[i].getDomain());						
		}	 	
	}
	*/
	
	//사용자 조회 및  패스워드 확인
	member = (MemberBean) mdao.schID(FimUserID, FimUserPasswd);
	
	if(member == null){
		out.print("<SCRIPT Language='JavaScript'>		\n");
		out.print("alert('등록된 아이디가 아닙니다.');		\n");
		out.print("document.location = './index.jsp';	\n");
		out.print("</SCRIPT>	\n");		
	}else{	
		
		if (member.getM_id().length()>0 && member.getM_id().equals(FimUserID) ) {
			if (!member.getM_pass().equals(FimUserPasswd)) {
				out.print("<SCRIPT Language='JavaScript'>		\n");
				out.print("alert('패스워드가 틀렸습니다.');			\n");
				out.print("document.location = './index.jsp';	\n");
				out.print("</SCRIPT>							\n");	
			} else {					
				isLogin = true; // 로그인 성공
			}
		} 
		
		// 로그인 처리 프로세스 시
		if (isLogin) {		
			
		    // 중복로그인 방지 프로세스 시작
			if (lm.isUsing(member.getM_id()+ "_m")) {			
				lm.removeSession(member.getM_id()+ "_m");
				out.print("<SCRIPT Language='JavaScript'>");
				out.print("alert('동일 아이디 사용자가 존재합니다.\\n\\n기존 사용자는 로그아웃됩니다.');");
				out.print("document.location = './m_login.jsp?m_id="+FimUserID+"&m_pass="+FimUserPasswd+"';\n");			
				out.print("</SCRIPT>");		
			}else{
				session = request.getSession();			
				session.setAttribute("SS_M_NO", member.getM_seq());
				session.setAttribute("SS_M_ID", member.getM_id());
				session.setAttribute("SS_M_NAME", member.getM_name());
				session.setAttribute("SS_MG_NO", member.getMg_seq());
				session.setAttribute("SS_M_DEPT", member.getM_dept());
				session.setAttribute("SS_M_IP", request.getRemoteAddr());
				session.setAttribute("SS_M_MAIL", member.getM_mail());
				session.setAttribute("SS_TITLE", cu.getConfig("TITLE"));
				session.setAttribute("SS_URL", cu.getConfig("URL"));
				session.setMaxInactiveInterval(24*60*60);	
				lm.setSession(session, member.getM_id()+ "_m");
				lm.printloginUsers();
				
				//사용자 기본 환경을 조회하여 세션에 저장한다.
				SS_M_NO = member.getM_seq();
				SS_MG_NO = member.getMg_seq();
				userEnvMgr uem = new userEnvMgr();
				userEnvInfo uei = uem.getUserEnv(SS_M_NO, SS_MG_NO);
				
				
				//사용자 기본 환경 조회 실패시 처리
				if (uei == null) {
					out.print("<SCRIPT Language='JavaScript'>");
					out.print("document.location = './riskv3/error/sessionerror.html';");
					out.print("</SCRIPT>");
				} else {
					logBean = new LogBean();
					logBean.setKey("0");
					logBean.setL_kinds(LogConstants.mobileLoginKindsVal);
					logBean.setL_type(LogConstants.insertTypeVal);
					logBean.setL_ip(request.getRemoteAddr());
					logBean.setM_seq(SS_M_NO);
					
					//로그 저장
					logMgr.insertLog(logBean);
					
					session.setAttribute("ENV", uei);
					out.print("<SCRIPT Language='JavaScript'>");
					//out.print("document.location = 'riskv3/mobile_web/mobile_dashboard.jsp';");
					out.print("document.location = 'riskv3/mobile_web/mobile_content_list.jsp';");
					out.print("</SCRIPT>");
				}
			}
		}
	}

%>
