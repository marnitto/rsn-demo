<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.ArrayList,
 				 risk.admin.aekeyword.ExceptionKeywordBean,
                 risk.admin.aekeyword.ExceptionKeywordMgr,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	ExceptionKeywordMgr ekMgr = new ExceptionKeywordMgr();
	ExceptionKeywordBean eKeywordBean = null;
	pr.printParams();
	
	String ekSeq= pr.getString("ekSeq");	
	String mode = pr.getString("mode");
	
	ArrayList ar_k_seq_L = null;
	ArrayList ar_k_seq_R = null;
	ArrayList ar_s_seq_L = null;
	ArrayList ar_s_seq_R = null;
		
	
	
	eKeywordBean = ekMgr.getKeyword(ekSeq);
	/*
	ar_k_seq_L =  ekMgr.getAllKeyword(eKeywordBean.getK_seqs(), ExceptionKeywordBean.Type.LEFT);
	ar_k_seq_R =  ekMgr.getAllKeyword(eKeywordBean.getK_seqs(), ExceptionKeywordBean.Type.RIGHT);
	ar_s_seq_L =  ekMgr.getAllSite(eKeywordBean.getS_seqs(), ExceptionKeywordBean.Type.LEFT);
	ar_s_seq_R =  ekMgr.getAllSite(eKeywordBean.getS_seqs(), ExceptionKeywordBean.Type.RIGHT);
	*/
	

%>

<html>
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
<style>
<!--	
	.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}

-->
	</style>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script language="javascript">
<!--

	$(document).ready(pageInit);

	function pageInit()
	{
		showDataList('keyword','left');
		showDataList('keyword','right');
		showDataList('site','left');
		showDataList('site','right');
	}

	
	function showDataList(mode,type){

		
		data = '';
		if(mode == 'keyword'){
			if(type == 'left'){
				data = '?mode=' + mode + '&type=' + type;
				ajax.post('inc_selected_list.jsp'+ data,'editForm','div_kl');
				
			}else if(type == 'right'){
				data = '?mode=' + mode + '&type=' + type;
				ajax.post('inc_selected_list.jsp'+ data,'editForm','div_kr');
			}
		}else if(mode == 'site'){
			if(type == 'left'){
				data = '?mode=' + mode + '&type=' + type;
				ajax.post('inc_selected_list.jsp'+ data,'editForm','div_sl');
			}else if(type == 'right'){
				data = '?mode=' + mode + '&type=' + type;
				ajax.post('inc_selected_list.jsp'+ data,'editForm','div_sr');
			}
		}
		
	}
	

	function updateKeyword(mode, type2)
	{
		var f = document.editForm;

		var target = '';
		if(mode == 'keyword'){
			if(type2 == 'add'){
				if(f.k_seq_L.value == ''){
					alert('��������Ʈ�� �����ϼ���.'); return;
				}else{
					target = f.k_seq_L.value; 		
				}								
			}else if(type2 == 'del'){
				if(f.k_seq_R.value == ''){
					alert('��������Ʈ�� �����ϼ���.'); return;
				}else{
					target = f.k_seq_R.value; 		
				}
			}
		}else if(mode == 'site'){
			if(type2 == 'add'){
				if(f.s_seq_L.value == ''){
					alert('��������Ʈ�� �����ϼ���.'); return;
				}else{
					target = f.s_seq_L.value; 		
				}
			}else if(type2 == 'del'){
				if(f.s_seq_R.value == ''){
					alert('��������Ʈ�� �����ϼ���.'); return;
				}else{
					target = f.s_seq_R.value; 		
				}
			}
		}

		var data = '?mode=' + mode + '&type2=' + type2 + "&targetSeq=" + target; 
		
		f.target='processFrm';
		f.action='aekeyword_prc.jsp' + data;
		f.submit();
	}

	
//-->
</script>
</head>
<body>
<form id="editForm" name="editForm" method="post">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="ekSeq" value="<%=ekSeq%>">

 
 <table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td id="pop_head"><font color="white" style="border:white; font-weight:bolder; font-size:15px">&nbsp;&nbsp;����Ű�������</font>
			<span><a href="javascript:close();"><img src="../../../images/admin/aekeyword/pop_tit_close.gif"></span>
		</div>		</td>
	</tr>
	<tr>
		<td>
		<!-- �Խ��� ���� -->
		<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
			<tr>
			  <td style="height:30px;"><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#E0E0E0">
                <tr>
                  <td bgcolor="#F6F6F6" style="padding:10px 10px 10px 10px"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="20"><img src="../../../images/admin/aekeyword/arrow.gif" width="6" height="7"><span class="blue_text"><strong>����Ű���� : <%=eKeywordBean.getEkValue()%></strong></span></td>
                    </tr>
                    <tr>
                      <td height="20"><img src="../../../images/admin/aekeyword/arrow.gif" width="6" height="7">����� : <%=eKeywordBean.getEkFwriter()%></td>
                    </tr>
                    <tr>
                      <td height="20"><img src="../../../images/admin/aekeyword/arrow.gif" width="6" height="7">�Է����� : <%=eKeywordBean.getEkDate()%></td>
                    </tr>
                  </table></td>
                </tr>
              </table></td>
		  </tr>
			<tr>
			  <td height="5"></td>
		  </tr>
			<tr>
				<td style="height:40px;"><span class="sub_tit">���� ��� Ű���� �߰�</span></td>
			</tr>
			<tr>
				<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="47%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="../../../images/admin/aekeyword/admin_ico03.gif" width="10" height="11"> ��ü Ű���� </td>
                      </tr>
                      <tr>
                        <td height="5"></td>
                      </tr>
                      <tr>
                        <td>
                        	<div id="div_kl">
                        	</div>
                        </td>
                      </tr>
                    </table></td>
                    <td width="6%" align="center"><img src="../../../images/admin/aekeyword/btn_left.gif" style="cursor: pointer;" onclick="updateKeyword('keyword','del');"><br>
                    <img src="../../../images/admin/aekeyword/btn_right.gif" width="18" height="18" vspace="5" style="cursor: pointer;" onclick="updateKeyword('keyword','add');"></td>
                    <td width="47%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="../../../images/admin/aekeyword/admin_ico03.gif" width="10" height="11"> ���� ��� Ű���� </td>
                      </tr>
                      <tr>
                        <td height="5"></td>
                      </tr>
                      <tr>
                        <td>
                        	<div id="div_kr">
                        	</div>
                        </td>
                      </tr>
                    </table></td>
                  </tr>
                </table></td>
			</tr>
			<tr>
				<td style="padding-top:15px;height:55px;"><span class="sub_tit">���� ��� ����Ʈ �߰�</span></td>
			</tr>
			<tr>
				<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="47%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><img src="../../../images/admin/aekeyword/admin_ico03.gif" width="10" height="11"> ��ü ����Ʈ</td>
                        </tr>
                        <tr>
                          <td height="5"></td>
                        </tr>
                        <tr>
                          <td>
                          	<div id="div_sl">
                        	</div>
                          </td>
                        </tr>
                    </table></td>
                    <td width="6%" align="center"><img src="../../../images/admin/aekeyword/btn_left.gif" width="18" height="18" style="cursor: pointer;" onclick="updateKeyword('site','del');"><br>
                        <img src="../../../images/admin/aekeyword/btn_right.gif" width="18" height="18" vspace="5" style="cursor: pointer;" onclick="updateKeyword('site','add');"></td>
                    <td width="47%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><img src="../../../images/admin/aekeyword/admin_ico03.gif" width="10" height="11"> ���� ��� ����Ʈ</td>
                        </tr>
                        <tr>
                          <td height="5"></td>
                        </tr>
                        <tr>
                          <td>
                          	<div id="div_sr">
                        	</div>
                          </td>
                        </tr>
                    </table></td>
                  </tr>
                </table></td>
			</tr>
		</table>
		<!-- �Խ��� �� -->		</td>
	</tr>
	
	<!---------------->
</table>
</form>
<iframe name="processFrm" height="0" border="0" style="display: none;"></iframe>
</body>
</html>