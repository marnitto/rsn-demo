<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "risk.admin.member.MemberDao" %>
<%@ page import = "risk.admin.member.MemberBean" %>
<%@ page import = "risk.util.ParseRequest" %>
<%@page import="risk.util.DateUtil"%>

<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	int i;

	String mode = null;
	String m_id = null;
	String m_pass = null;
	String m_name = null;
	String m_dept = null;
	String m_dpos = null;
	String m_email = null;
	String m_tel = null;
	String m_hp = null;
	String m_seq = null;
	String m_ip = null;
	String m_mg = null;
	String m_sms = null;
	String m_date = null;
	int type =0;
	String script = "";
	String[] multy_m_seq = null;

	StringBuffer sb = new StringBuffer();
	DateUtil du = new DateUtil();

	MemberDao dao = new MemberDao();
	MemberBean member = new MemberBean();
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();


	if( pr.getString("mode") != null ) mode = pr.getString("mode");
	//if( request.getParameter("mode") != null ) mode = request.getParameter("mode");
	

	if( mode != null )
	{
		if( mode.equals("ins") ) {													
			if( pr.getString("m_id") != null ) m_id = pr.getString("m_id");
				if(!dao.checkId(m_id)) {		
					if( pr.getString("m_pass") != null ) m_pass = pr.getString("m_pass");
					if( pr.getString("m_name") != null ) m_name = pr.getString("m_name");
					if( pr.getString("m_dept") != null ) m_dept = pr.getString("m_dept");
					if( pr.getString("m_dpos") != null ) m_dpos = pr.getString("m_dpos");
					if( pr.getString("m_email") != null ) m_email = pr.getString("m_email");
					if( pr.getString("m_tel") != null ) m_tel = pr.getString("m_tel");
					if( pr.getString("m_hp") != null ) m_hp = pr.getString("m_hp","");
					if( pr.getString("m_mg") != null ) m_mg = pr.getString("m_mg");		 										
					member.setM_id(m_id); 
					member.setM_pass(m_pass);
					member.setM_name(m_name);
					member.setM_dept(m_dept);
					member.setM_position(m_dpos);
					member.setM_mail(m_email);
					member.setM_tel(m_tel);
					member.setM_hp(m_hp);					
					member.setMg_seq(m_mg);
					member.setM_regdate(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
					dao.insertMember(member);			
				}else {				
					%>
					<SCRIPT>
					alert('중복되는 id가 있습니다! ');
					history.go(-1);
					//location.href = 'admin_user_list.jsp';
					</SCRIPT>
					<%	
				}
		} else if( mode.equals("edit") ) {
			if( request.getParameter("m_seq") != null ) m_seq = request.getParameter("m_seq");
			if( pr.getString("m_id") != null ) m_id = pr.getString("m_id");
			if( pr.getString("m_pass") != null ) m_pass = pr.getString("m_pass");
			if( pr.getString("m_name") != null ) m_name = pr.getString("m_name");
			if( pr.getString("m_dept") != null ) m_dept = pr.getString("m_dept");
			if( pr.getString("m_dpos") != null ) m_dpos = pr.getString("m_dpos");
			if( pr.getString("m_email") != null ) m_email = pr.getString("m_email");
			if( pr.getString("m_tel") != null ) m_tel = pr.getString("m_tel");
			if( pr.getString("m_hp") != null ) m_hp = pr.getString("m_hp","");
			if( pr.getString("m_mg") != null ) m_mg = pr.getString("m_mg");
			if( pr.getString("m_date") != null ) m_date = pr.getString("m_date");
			
			
			
			System.out.println("m_id:"+m_id);
			System.out.println("m_name:"+m_name);
			
			MemberBean mb = new MemberBean();
			member.setM_seq(m_seq);
			member.setM_id(m_id);
			member.setM_pass(m_pass);
			member.setM_name(m_name);
			member.setM_dept(m_dept);
			member.setM_position(m_dpos);
			member.setM_mail(m_email);
			member.setM_tel(m_tel);
			member.setM_hp(m_hp);
		    member.setMg_seq(m_mg);
		    member.setM_regdate(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
			dao.updateMember(member);
			
			if( pr.getString("from").equals("login") ){
				
				%>
				<SCRIPT>
				location.href = '../../logout.jsp';
			</SCRIPT>
				
				
				<%
			}
		
			
		}else if( mode.equals("del") ) {

			if( request.getParameter("m_seq") != null ) multy_m_seq = request.getParameterValues("m_seq");

			for(i=0; i< multy_m_seq.length;i++)
			{
				if( i != 0) sb.append(",");
				sb.append(multy_m_seq[i]);
			}

			dao.deleteMember(sb.toString());
		
		}
	}	
	

%>
<SCRIPT>
	location.href = 'admin_user_list.jsp';
</SCRIPT>