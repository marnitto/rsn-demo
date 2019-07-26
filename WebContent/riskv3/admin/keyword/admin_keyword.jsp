<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<%=SS_URL%>css/base.css" type="text/css">

<script type="text/JavaScript">
<!--
	function add_key()
	{
		frm = keyword_right.keygroup;
		frm.mode.value = 'add';
		var url = "pop_keyword_add.jsp?xp="+frm.xp.value+"&yp="+frm.yp.value+"&zp="+frm.zp.value;
		window.open(url, "pop_keyword", "width=400,height=400");
	}

	function del_key()
	{
		if( confirm("하위분류 및 키워드까지 삭제됩니다.\n삭제하시겠습니까?") )
		{
			keyword_right.del_kg();
		}
	}

	function up_key()
	{
		keyword_right.up_kg();
	}

	function down_key()
	{
		keyword_right.down_kg();
	}
	
	function keySave()
	{
		document.frm_keyword_get.location = 'keyword_download_excel.jsp';
	}

	function openLayer()
	{
		document.getElementById("floater").style.display='';
	}

	function closeLayer()
	{
		document.getElementById("floater").style.display='none';
	}

//-->
</script>
</head>
<body>
<div id="floater" style="width:328px;height:168px; top:485px; left=490px; cellspacing:0; cellpadding:0; position: absolute;border:black 0px solid; display:none;">  
<img src="../../../images/admin/keyword/layer_img.gif" style="cursor:hand" onclick="closeLayer();"></img>
</div>
<table style="width:850px;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
			<table width="820" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="15px"></td>
					<td class="tit_bg" style="height:37px;padding-top:0px;">
					<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td><img src="../../../images/admin/keyword/tit_icon.gif" /><img src="../../../images/admin/keyword/tit_0505.gif" /></td>
							<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="navi_home">HOME</td>
									<td class="navi_arrow">관리자</td>
									<td class="navi_arrow2">키워드관리</td>
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
      <table width="770" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="right" style="padding-left:10px"><table width="750" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="35" align="left">수집된 정보의 필터링 및 분류에 사용될 키워드 그룹 및 키워드를 관리합니다.</td>
            </tr>
             <tr>
              <td height="10">&nbsp;</td>
            </tr>
          </table>
            <table width="750" border="0" cellspacing="0" cellpadding="0">
              <tr>
			    <td align="left">&nbsp;</td>
                <td width="220" align="left" valign="top"><table width="220"  border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="25" class="textBbig" >
                    	<table width="100%" border="0">
                    		<tr>
                    			<td align="left"><img src="../../../images/admin/keyword/admin_ico01.gif" width="12" height="10" hspace="2"><strong>키워드그룹</strong></td>
                    			<td align="right"><img src="../../../images/admin/keyword/excel_save.gif" width="94" height="24" hspace="2" onclick="keySave();" style="cursor:hand;"></td>
                    		</tr>
                    	</table>
                    </td>
                  </tr>
                  <tr>
                    <td><iframe name="keyword_left" id="keyword_left" src="admin_keyword_left.jsp" width="240" height="300" scrolling="auto" frameborder="0" style="border:1px solid; border-color:#CCCCCC"></iframe></td>
                  </tr>
                </table>
                  <table width="240" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="../../../images/admin/keyword/form_add.gif" vspace="5" OnKeyDown="Javascript:if (event.keyCode == 13) { add_key();}" onclick="add_key();" style="cursor:hand;"><img src="../../../images/admin/keyword/form_del.gif" hspace="5" vspace="5" onclick="del_key();" style="cursor:hand;"></td>
                      <td align="right"><img src="../../../images/admin/keyword/up_btn.gif" width="18" height="18" onclick="up_key();" style="cursor:hand;"><img src="../../../images/admin/keyword/down_btn.gif" width="18" height="18" hspace="5" onclick="down_key();" style="cursor:hand;"></td>
                    </tr>
                  </table></td>
                <td width="30" align="left">&nbsp;</td>
                <td width="335" align="left" valign="top"><table width="335"  border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="25" align="left" class="textBbig" ><img src="../../../images/admin/keyword/admin_ico01.gif" width="12" height="10" hspace="2"><strong>세부정보</strong></td>
                  </tr>
                  <tr>
                    <td><iframe name="keyword_right" id="keyword_right" src="admin_keyword_right.jsp" width="335" height="300" scrolling="auto" frameborder="0" style="border:1px solid; border-color:#CCCCCC"></iframe></td>
                  </tr>
                  <tr>
		          	<td height="10" align="right">&nbsp;</td>
		          </tr>
                  <tr>
		          	<td height="10" align="right"><img src="../../../images/admin/keyword/banner_01.gif" style="cursor:hand" onclick="openLayer();"></td>
		          </tr>
                </table>                  
                  <table width="335" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td align="right"><!--<img src="../../../images/admin/keyword/btn_save.gif" width="48" height="21" vspace="5">--></td>
                    </tr>
                  </table></td>
                <td align="left">&nbsp;</td>
              </tr>
            </table>            </td>
        </tr>
      </table>
		</td>
	</tr>
</table>
<iframe name="frm_keyword_get" id="frm_keyword_get" src="" width="0" height="0" frameborder="0"></iframe>
</body>
</html>