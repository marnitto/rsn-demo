<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.sql.*
			,java.net.*
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
	pr.printParams();
	LoginManager lm = LoginManager.getInstance();
	LogMgr logMgr = new LogMgr();
	LogBean logBean = new LogBean();

	String FimUserID = pr.getString("FimUserID") ;
	String FimUserPasswd = pr.getString("FimUserPasswd");
	String saveid = pr.getString("SaveId") ;
	

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

	System.out.println("saveid:"+saveid);
	if (!saveid.equals("")) {		
		//==========  쿠키 저장 =============
		Cookie cookie = new Cookie("lifeRSNid", URLEncoder.encode(FimUserID,"utf-8"));
		cookie.setMaxAge(30 * 24 * 60 * 60); //30일간 저장
		response.addCookie(cookie);	
	}else {
		//==========  쿠키 삭제 =============
		Cookie cookie = new Cookie("lifeRSNid", "");
		cookie.setMaxAge(0); //쿠키삭제
		response.addCookie(cookie);
	}
	
	//사용자 조회 및  패스워드 확인
	member = (MemberBean) mdao.schID(FimUserID, FimUserPasswd);
	
	if(member == null){
		out.print("<SCRIPT Language='JavaScript'>		\n");
		out.print("alert('등록된 아이디가 아닙니다.');		\n");
		out.print("document.location = '/index.jsp';	\n");
		out.print("</SCRIPT>	\n");		
	}else{	
		
		if (member.getM_id().length()>0 && member.getM_id().equals(FimUserID) ) {
			if (!member.getM_pass().equals(FimUserPasswd)) {
				out.print("<SCRIPT Language='JavaScript'>		\n");
				out.print("alert('패스워드가 틀렸습니다.');			\n");
				out.print("document.location = '/index.jsp';	\n");
				out.print("</SCRIPT>							\n");	
			} else {					
				isLogin = true; // 로그인 성공
			}
		} 
		
		// 로그인 처리 프로세스 시
		if (isLogin) {		
			
		    // 중복로그인 방지 프로세스 시작
			if (lm.isUsing(member.getM_id())) {			
				lm.removeSession(member.getM_id());
				out.print("<SCRIPT Language='JavaScript'>");
				out.print("alert('동일 아이디 사용자가 존재합니다.\\n\\n기존 사용자는 로그아웃됩니다.');");
				out.print("document.location = '/login.jsp?FimUserID="+FimUserID+"&FimUserPasswd="+FimUserPasswd+"&SaveId="+saveid+"';\n");			
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
				session.setAttribute("SS_SEARCHDATE", "");
				session.setMaxInactiveInterval(24*60*60);	
				lm.setSession(session, member.getM_id());
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
					logBean.setL_kinds(LogConstants.loginKindsVal);
					logBean.setL_type(LogConstants.insertTypeVal);
					logBean.setL_ip(request.getRemoteAddr());
					logBean.setM_seq(SS_M_NO);
					
					//로그 저장
					logMgr.insertLog(logBean);
					
					session.setAttribute("ENV", uei);
					String[] top_menu = uei.getMg_menu().split(",");
					boolean chk = false;
					if(top_menu.length > 0){
						for(int i = 0; i < top_menu.length; i++){
							if(top_menu[i].equals("6")){
								chk = true;
							}
						}
					}
					if(chk){
						out.print("<SCRIPT Language='JavaScript'>");
						//out.print("document.location = 'riskv3/main.jsp';");
						if("23".equals(SS_MG_NO)){
							out.print("document.location = '/dashboard/mrt/mrt.jsp';");
						}else{
							out.print("document.location = '/dashboard/summary/summary.jsp';");	
						}
												
						out.print("</SCRIPT>");
					}else{
						out.print("<SCRIPT Language='JavaScript'>");
						out.print("document.location = 'riskv3/main.jsp';");
						out.print("</SCRIPT>");
					}
				}
			}
		}
	}

%>
