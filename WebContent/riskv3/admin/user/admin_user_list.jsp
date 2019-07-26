<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="UTF-8"%>
<%@ page import = "risk.admin.member.MemberDao" %>
<%@ page import = "risk.admin.member.MemberBean" %>
<%@ page import = "risk.util.ParseRequest" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import="risk.util.PageIndex"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	int totpage = 0;
	int nowpage = 1;
	int count = 0;
	String echomsg = "";
	String sch_mod = "";
	String sch_val = "";

	List memberlist = null;
	MemberDao dao = new MemberDao();
    ParseRequest    pr = new ParseRequest(request);

try{	
	if( request.getParameter("nowpage") != null ) nowpage = Integer.parseInt(request.getParameter("nowpage"));
	if( pr.getString("sch_mod") != null ) sch_mod = pr.getString("sch_mod");
	if( pr.getString("sch_val") != null ) sch_val = pr.getString("sch_val");
	
	if( sch_val.equals("") )
	memberlist = dao.getList( String.valueOf( nowpage ) );
	else if( sch_mod.equals("1") )
	memberlist = dao.getList( String.valueOf( nowpage ), "mg_name", sch_val );
	else if( sch_mod.equals("2") )
	memberlist = dao.getList( String.valueOf( nowpage ), "m_id", sch_val );
	else if( sch_mod.equals("3") )
	memberlist = dao.getList( String.valueOf( nowpage ), "m_name", sch_val );
	else if( sch_mod.equals("4") )
	memberlist = dao.getList( String.valueOf( nowpage ), "m_dept", sch_val );
	else if( sch_mod.equals("5") )
	memberlist = dao.getList( String.valueOf( nowpage ), "m_hp", sch_val );
	else if( sch_mod.equals("6") )
	memberlist = dao.getList( String.valueOf( nowpage ), "m_mail", sch_val );

}catch(Exception e){System.out.println("[admin_user_list] error1 : "+e);}
		
try{	
	count = dao.totalct;
	if( count > 0 ) {
		totpage = count/10;
		if( (count % 10) > 0 ) totpage++;
	}
}catch(Exception e){System.out.println("error2 : "+e);}

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<script type="text/JavaScript">
	function init(){
		search.sch_val.focus();
	}

    function pageClick( paramUrl ) {
		search.nowpage.value = paramUrl.substr(paramUrl.indexOf("=")+1, paramUrl.length);
		search.submit();
    }

	function sch_sub()
	{
		if( search.sch_val.value )
		{
			search.nowpage.value = 1;
			search.submit();
		} else {
			alert('검색조건이 없습니다.');
		}
	}

	var val = 0;
	function sel_all()
	{
		var form = document.getElementById('userlist');
		if( form.m_seq ) {
			if(val == 0){
				if(form.m_seq.length > 1){
					for(var i=0 ; i<form.m_seq.length ; i++ )
					{
						form.m_seq[i].checked = true;
					}
				} else {
					form.m_seq.checked = true;
				}
				val = 1;
			}else{
				if(form.m_seq.length > 1){
					for( i=0; i < form.m_seq.length; i++ ){
						form.m_seq[i].checked = false;
					}
				}else{
					form.m_seq.checked = false;
				}
				val = 0;
			}
		}
	}

	function multy_del()
	{
		var chk = false;
		if( userlist.m_seq.length ) {
			for(var i=0 ; i<userlist.m_seq.length ; i++ )
			{
				if( userlist.m_seq[i].checked == true ) {
					chk = true;
					break;
				}
			}
		} else {
			if( userlist.m_seq.checked == true ) chk = true;
		}

		if( chk ) {
			if( confirm('삭제하시겠습니까?') ) {
				userlist.submit();
			}
		} else {
			alert("삭제할 대상을 선택하여 주십시요.");
		}
	}
	
	// 각 메뉴의 마우스 오버시 이벤트
function mnu_over(obj) {
	obj.style.backgroundColor = "F3F3F3";


}

// 각 메뉴의 마우스 아웃시 이벤트
// 현재 선택된 메뉴면
function mnu_out(obj) {
	obj.style.backgroundColor = "#FFFFFF";

}
	
</script>
</head>
<body>
<table style="width:850px;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table align="center" style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:37px;padding-top:0px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/member/tit_icon.gif" /><img src="../../../images/admin/member/tit_0501.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">사용자관리</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td height="15"></td>
			</tr>
			<!-- 타이틀 끝 -->
			<tr>
				<td style="height:40px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width:76px;"><img src="../../../images/admin/member/btn_allselect.gif" onclick="sel_all();" style="cursor:pointer"/></td>
						<td><img src="../../../images/admin/member/btn_del.gif" onclick="multy_del();" style="cursor:pointer;"/></td>
						<td style="width:88px;"><a href="admin_user_add.jsp"><img src="../../../images/admin/member/btn_useradd.gif" /></a></td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 게시판 시작 -->
			<tr>
				<td>
				<form name="userlist" id="userlist" action="admin_user_prc.jsp" method="post">
				<input name="mode" type="hidden" value="del">
				<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
				<col width="5%"><col width="10%"><col width="10%"><col width="10%"><col width="15%"><col width="15%"><col width="35%">
					<tr>
						<th>선택</th>
						<th>사용자그룹</th>
						<th>아이디</th>
						<th>이름</th>
						<th>부서</th>
						<th>핸드폰</th>
						<th> E-Mail</th>
					</tr>
<%
	if(memberlist != null && memberlist.size() > 0){
		for(int i = 0; i < memberlist.size(); i++) {
			MemberBean memberinfo = (MemberBean)memberlist.get(i);
%>
					<tr>
						<td><input type="checkbox" id="m_seq" name="m_seq" value="<%=memberinfo.getM_seq()%>"></td>
						<td><%=memberinfo.getMg_name()%></td>
						<td><a href="#" onclick="location.href='admin_user_view.jsp?id=<%=memberinfo.getM_id()%>'"><strong><%=memberinfo.getM_id()%></strong></a></td>
						<td><%=memberinfo.getM_name()%></td>
						<td><%=memberinfo.getM_dept()%></td>
						<td><span class="phone"><%=memberinfo.getM_hp()%></span></td>
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;"><span class="mail"><%=memberinfo.getM_mail()%></span></td>
					</tr>
<%}}else{%>
					<tr>
						<td colspan="7" align="center" style="font-weight:bold;height:40px">조건에 맞는 데이터가 없습니다.</td>
					</tr>
<%}%>
				</table>
				</form>
				</td>
			</tr>
			<!-- 게시판 끝 -->
			<tr>
				<td style="height:40px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width:128px;">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td style="width:76px;"><img src="../../../images/admin/member/btn_allselect.gif" onclick="sel_all();" style="cursor:pointer;"/></td>
								<td><img src="../../../images/admin/member/btn_del.gif" onclick="multy_del();" style="cursor:pointer;"/></td>
							</tr>
						</table>
						</td>
						<td align="right"><a href="admin_user_add.jsp"><img src="../../../images/admin/member/btn_useradd.gif" /></a></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td>
				<form name="search" action="admin_user_list.jsp" method="post">
				<input type="hidden" name="nowpage" value="<%=nowpage%>">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td align="center" style="vertical-align:middle">
							<select name="sch_mod" style="vertical-align:middle">
								<option value="3" <% if( sch_mod.equals("3") ) out.print("selected");%>>이름</option>
								<option value="1" <% if( sch_mod.equals("1") ) out.print("selected");%>>사용자그룹</option>
								<option value="2" <% if( sch_mod.equals("2") ) out.print("selected");%>>아이디</option>
								<option value="4" <% if( sch_mod.equals("4") ) out.print("selected");%>>부서</option>
								<option value="5" <% if( sch_mod.equals("5") ) out.print("selected");%>>핸드폰</option>
								<option value="6" <% if( sch_mod.equals("6") ) out.print("selected");%>>E-mail</option>
							</select>
							<input class="txtbox" align="middle" type="text" style="vertical-align:middle" name="sch_val" value="<%=sch_val%>"  onkeydown="javascript:if (event.keycode == 13) { sch_sub();}">
							<img src="../../../images/admin/member/search_btn.gif" width="41" height="19" hspace="5" onclick="sch_sub();" style="cursor:pointer;vertical-align:middle">
						</td>
					</tr>
				</table>
				</form>
				</td>
			</tr>
			<tr>
				<td>
				<table style="padding-top:10px;" border="0" cellpadding="0" cellspacing="1" align="center">
					<tr>
						<td>
							<table id="paging" border="0" cellpadding="0" cellspacing="2">
								<tr>
									<%=PageIndex.getPageIndex(nowpage, totpage,"","" )%>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>