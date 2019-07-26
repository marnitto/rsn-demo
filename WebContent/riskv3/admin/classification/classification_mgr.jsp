<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@include file="../../inc/sessioncheck.jsp" %>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
</head>
<body leftmargin="10" topmargin="10" marginwidth="0" marginheight="0">
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top" style="padding-left:20px">
			<table width="820" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="tit_bg" style="height:37px;padding-top:0px;">
					<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td><img src="../../../images/admin/classification/tit_icon.gif" /><img src="../../../images/admin/classification/tit_0507.gif" /></td>
							<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="navi_home">HOME</td>
									<td class="navi_arrow">관리자</td>
									<td class="navi_arrow2">보고서 분류체계 관리</td>
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
			</table>
			<table width="780" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td style="padding: 0px 0px 0px 10px;"><table width="740" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td width="300" valign="top"><table width="300" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td height="25"><img src="../../../images/admin/classification/ico_won_blue02.gif" width="16" height="14" align="absmiddle"><strong>분류체계</strong></td>
			          </tr>
			          <tr>
			            <td><table cellSpacing=1 cellPadding=2 width=300 bgColor=#cccccc border=0>
			              <tbody>
			                <tr bgColor=#d5d5d5>
			                  <td style="padding-right: 0px; padding-left: 10px; padding-bottom: 5px;padding-top: 5px" Align=top bgColor=#ffffff colSpan=5>
			                    <iframe id=frm_menu style="width: 285px; height: 300px" border=0  name=frm_menu src="frm_classification.jsp"  frameBorder=0></iframe>
			                  </td>
			               </tr>
			              </tbody>
			            </table></td>
			          </tr>
			        </table></td>
			        <td width="60">&nbsp;</td>
			        <td width="380" valign="top"><table width="380" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td height="25"><img src="../../../images/admin/classification/ico_won_blue02.gif" width="16" height="14" align="absmiddle"><strong>분류항목</strong></td>
			          </tr>
			          <tr>
			            <td>
			           	<table cellSpacing=1 cellPadding=2 width=300 bgColor=#cccccc border=0>
			              <tbody>
			                <tr bgColor=#d5d5d5>
			                  <td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px;padding-top: 0px" align=top bgColor=#ffffff colSpan=5>
			                    <iframe id="frm_detail" name="frm_detail" style="width: 396px; height: 310px" scrolling="auto" src="frm_classification_detail.jsp"  frameBorder="0"></iframe>
			                  </td>
			               </tr>
			              </tbody>
			            </table>
			           </td>
			          </tr>
			        </table></td>
			      </tr>
			    </table>
			      <table width="740" border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td height="75">※ 분류체계는 보고서등과 연동되므로 추가/삭제 할수 없습니다.<br>
			            ※ 각 분류체계의 하위 분류항목만 추가/삭제 할 수 있습니다.<br>
			          ※ 분류항목을 변경하여도 기존에 발송된 보고서에는 영향을 미치지 않습니다.</td>
			        </tr>
			      </table>
			    </td>
			  </tr>
			  <tr>
			    <td>&nbsp;</td>
			  </tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>