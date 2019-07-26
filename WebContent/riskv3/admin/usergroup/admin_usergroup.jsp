<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<script language="JavaScript" type="text/JavaScript">
<!--
	function ins_mg() {
		var url = "pop_usergroup.jsp?mode=ins";
		window.open(url, "pop_usergroup", "width=400,height=150");
	}
	
	function edit_mg() {
			frm = usergroup_left.membergroup;
			if( !frm.mgseq.value ) {
				alert('사용자그룹을 선택하십시요.');
			} else {
				var url = "pop_usergroup.jsp?mode=edit&mgseq="+frm.mgseq.value;
				window.open(url, "pop_usergroup", "width=400,height=150");
			}
	}
	
	function del_mg() {
		if( confirm('사용자그룹을 삭제하시겠습니까?') )
		usergroup_left.del();
	}
	
	function alert_msg() {
		alert("사용자그룹에 속한 사용자가 있습니다.");
	}
//-->
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
						<td><img src="../../../images/admin/member_group/tit_icon.gif" /><img src="../../../images/admin/member_group/tit_0502.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">사용자그룹/권한관리</td>
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
			<!-- 게시판 시작 -->
			<tr>
				<td align="center">
				<table style="height:350px;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width:340px;">
						<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td style="height:30px;"><span class="sub_tit">사용자그룹</span></td>
							</tr>
							<tr>
								<td>
									<iframe id="usergroup_left" name="usergroup_left" src="ifram_usergroup_left.jsp" width="340" height="300" scrolling="auto" frameborder="0"></iframe>
								</td>
							</tr>
							<tr>
								<td style="height:30px;"><img src="../../../images/admin/member_group/btn_add.gif" onclick="ins_mg();" style="cursor:pointer;"/><img src="../../../images/admin/member_group/btn_modify.gif" onclick="edit_mg();" style="cursor:pointer;"/><img src="../../../images/admin/member_group/btn_del2.gif" onclick="del_mg();" style="cursor:pointer;"/></td>
							</tr>
						</table>
						</td>
						<td style="width:30px;"></td>
						<td style="width:340px;">
						<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td style="height:30px;"><span class="sub_tit">그룹별 권한설정</span></td>
							</tr>
							<tr>
								<td>
									<iframe id="usergroup_right" name="usergroup_right" src="ifram_usergroup_right.jsp" width="340" height="300" scrolling="auto" frameborder="0"></iframe>
								</td>
							</tr>
							<tr>
								<td style="height:30px;"><img src="../../../images/admin/member_group/btn_save.gif" onclick="usergroup_right.curr_save();" style="cursor:pointer;"/></td>
							</tr>
						</table>
						</td>
					</tr>
				</table>

				</td>
			</tr>
			<!-- 게시판 끝 -->
		</table>
		</td>
	</tr>
</table>
</body>
</html>