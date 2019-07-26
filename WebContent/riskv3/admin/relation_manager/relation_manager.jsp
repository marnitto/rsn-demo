<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
				 risk.admin.relation_manager.RelationManagerBean,
				 risk.admin.relation_manager.RelationManagerMgr,
				 risk.util.ParseRequest,
                 java.net.*"
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	RelationManagerMgr rmMgr = new RelationManagerMgr();
	
	String searchKey = pr.getString("searchKey", "");	
	String kor_bt = pr.getString("kor_bt", "");
	String eng_bt = pr.getString("eng_bt", "");
	String num_bt = pr.getString("num_bt", "");
	
	String kor_temp[] = new String[]{"ㄱ","ㄴ","ㄷ","ㄹ","ㅁ","ㅂ","ㅅ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};
	String eng_temp[] = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	String num_temp[] = new String[]{"0","1","2","3","4","5","6","7","8","9"};
	
	ArrayList keywordArrList = new ArrayList();
	keywordArrList = rmMgr.getRelationKeywordList(searchKey, kor_bt, eng_bt, num_bt);	
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>RIS-K</title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<link rel="stylesheet" href="<%=SS_URL%>/css/common.css" type="text/css">
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<style type="text/css">
		.btn_tk{
		display:inline-block; *display:inline; *zoom:1; height:19px;
		border:1px solid #DDDDDD; border-radius:2px; background:#eaeaea;
		ling-height:16px !important; outline:none;
		cursor:pointer;
		}
		.btn_tk:hover, .btn_tk.active{border:1px solid #585858; background:#767676; color:#ffffff;}
		iframe{display:none;}
</style>
<script language="javascript">



	//단어 검색시
	function Search(){
		$("#searchKey").val( $("[name=searchWord]").val() );
		$("#managerForm").attr("action", "relation_manager.jsp");
		$("#managerForm").submit();
	}
	
	//한글 검색시
	function keywordSearch_kor(thisVal){
		$("#kor_bt").val(thisVal);
		$("#eng_bt").val("");
		$("#num_bt").val("");
		$("#searchKey").val("");
		$("#managerForm").attr("action", "relation_manager.jsp");
		$("#managerForm").submit();
	}
	
	//영어 검색시
	function keywordSearch_eng(thisVal){
		$("#kor_bt").val("");
		$("#eng_bt").val(thisVal);
		$("#num_bt").val("");
		$("#searchKey").val("");
		$("#managerForm").attr("action", "relation_manager.jsp");
		$("#managerForm").submit();
	}
	
	//숫자 검색시
	function keywordSearch_num(thisVal){
		$("#kor_bt").val("");
		$("#eng_bt").val("");
		$("#num_bt").val(thisVal);
		$("#searchKey").val("");
		$("#managerForm").attr("action", "relation_manager.jsp");
		$("#managerForm").submit();
	}	

	//버튼 팝업
	function popupEdit(id,mode)
	{				
		var f = document.editFrm;		
		f.rk_name.value = id;
		f.mode.value = mode;
		
		var sel_length = $("#relation_select option:selected").length;
				
		if(mode == 'sum'){
			if(sel_length == 0){
				alert("연관키워드가 선택되지 않았습니다.");
				return;
			}else if(sel_length == 1){
				alert("연관키워드는 2개이상 선택되어야 합치기 가능 합니다.");
				return;
			}else{
				var str = "";
				$("#relation_select option:selected").each(function () {
					if(str == ""){
						str += $(this).text();
					}else{
						str += "," + $(this).text();
					}
				});
				
				$("[name=rk_name]").val(str);
				
				str = "";
				$("#relation_select option:selected").each(function () {
					if(str == ""){
						str += $(this).val();
					}else{
						str += "," + $(this).val();
					}
				});
				$("[name=rk_seq]").val(str);				
				
			}
			popup.openByPost('editFrm', 'relation_manager_edit.jsp', 400, 170, false, false, false, 'trendPop');
		}		
		
		else if(mode=='insert'){
			popup.openByPost('editFrm','relation_manager_edit.jsp',400,170,false,false,false,'trendPop');
		}
		else if(mode=='update'){
			if(sel_length == 0){
				alert("연관키워드가 선택되지 않았습니다.");
				return;
			}
			else if(sel_length == 1){
				$("[name=rk_seq]").val($("#relation_select option:selected").val());
				$("[name=rk_name]").val($("#relation_select option:selected").text());
				
				popup.openByPost('editFrm','relation_manager_edit.jsp',400,170,false,false,false,'trendPop');
			}
			else{
				alert("연관키워드가 복수 선택되었습니다.");
				return;
			}
		}
		else if(mode=='delete'){
			if(sel_length == 0 ){
				alert("연관키워드가 선택되지 않았습니다.");
				return;
			}
			else{
				var str = "";
				$("#relation_select option:selected").each(function () {
					if(str == ""){
						str += $(this).text();
					}else{
						str += "," + $(this).text();
					}
				});
				
				$("[name=rk_name]").val(str);
				
				str = "";
				$("#relation_select option:selected").each(function () {
					if(str == ""){
						str += $(this).val();
					}else{
						str += "," + $(this).val();
					}
				});
				$("[name=rk_seq]").val(str);
				
				var i = window.confirm("삭제하시겠습니까?")
				
				if(i){								
					f.action = 'relation_manager_prc.jsp';
					f.target = 'iframePrc';
					f.submit();					
				}
			}
		}		
	}		

</script>
</head>
<body style="margin-left: 15px">
<iframe name="iframePrc" style="width:0;height:0;"></iframe>
<form name="managerForm" id="managerForm" method="post" onsubmit="return false;">
<input type="hidden" name="kor_bt" id="kor_bt">
<input type="hidden" name="eng_bt" id="eng_bt">
<input type="hidden" name="num_bt" id="num_bt">
<input type="hidden" name="searchKey" id="searchKey">
</form>
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:67px;padding-top:20px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/alimi/tit_icon.gif" /><img src="../../../images/admin/relation_manager/tit_0803.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">연관키워드 관리</td>
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

			<!-- 검색 시작 -->
			<tr>
				<td class="search_box">
				<table id="search_box" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th>검색 단어</th>
						<td style="vertical-align:middle">
						<%if(searchKey == "") { %>
						<input style="width:500px; vertical-align:middle" class="textbox" type="text" name="searchWord" onkeypress="Javascript:if (event.keyCode == 13) { Search(); }" style="vertical-align:middle"><img src="../../../images/admin/relation_manager/btn_search.gif" style="vertical-align:middle;cursor:pointer" onclick="Search();"/></td>
						<% } else { %>						
						<input style="width:500px; vertical-align:middle" class="textbox" type="text" name="searchWord" value="<%=request.getParameter("searchKey") %>" onkeypress="Javascript:if (event.keyCode == 13) { Search(); }" style="vertical-align:middle"><img src="../../../images/admin/relation_manager/btn_search.gif" style="vertical-align:middle;cursor:pointer" onclick="Search();"/></td>
						<% } %>
					</tr>
					<tr>
						<th>한글</th>
						<td style="vertical-align:middle; height:24px;">
						<%for(int i=0; i<kor_temp.length; i++){ %>
						<input type="button" class="btn_tk <%if (kor_bt.equals(kor_temp[i]))out.print("active"); %>" value="<%=kor_temp[i]%>" onclick="keywordSearch_kor('<%=kor_temp[i]%>');" style="width:23px; text-align:center; padding-left:4px;"	/>
						<% } %>
						</td>
					</tr>
					<tr>
						<th>영어</th>
						<td style="vertical-align:middle; height:24px;">
						<%for(int i=0; i<eng_temp.length; i++) { %>
						<input type="button" class="btn_tk <%if (eng_bt.equals(eng_temp[i]))out.print("active"); %>" value="<%=eng_temp[i]%>" onclick="keywordSearch_eng('<%=eng_temp[i]%>');" style="width:23px; padding-left:6px;" />
						<% } %>
						</td>
					</tr>
					<tr>
						<th>숫자</th>
						<td style="vertical-align:middle; height:24px;">
						<%for(int i=0; i<num_temp.length; i++) { %>
						<input type="button" class="btn_tk <%if (num_bt.equals(num_temp[i]))out.print("active"); %>" value="<%=num_temp[i]%>" onclick="keywordSearch_num('<%=num_temp[i]%>');" style="width:23px;" />
						<% } %>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 검색 끝 -->
			<tr>
				<td style="height:40px;">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/relation_manager/btn_merge.gif" style="cursor:pointer" onclick="popupEdit('','sum');"/></td><%-- 합치기 --%>
						<td><img src="../../../images/admin/relation_manager/btn_add.gif" style="cursor:pointer" onclick="popupEdit('','insert');"/></td><%-- 추가 --%>
						<td><img src="../../../images/admin/relation_manager/btn_modify.gif" style="cursor:pointer" onclick="popupEdit('','update');"/></td><%-- 수정 --%>
						<td><img src="../../../images/admin/relation_manager/btn_delete.gif" style="cursor:pointer" onclick="popupEdit('','delete');"/></td><%-- 삭제 --%>
					</tr>
				</table>				
				</td>
			</tr>
			<!-- 게시판 시작 -->
			<tr>
				<td>
				<form name="editFrm" id="editFrm" method="post" onsubmit="return false;">
				<input type="hidden" name="mode" id="mode">
				<input type="hidden" name="rk_seq">
				<input type="hidden" name="rk_name">				
				
				<select multiple name="relation_select" id="relation_select" style="display:block; width:100%; height:400px; padding:5px; border:1px solid #CFCFCF; border-top:2px solid #7CA5DD; outline:none; cursor:pointer;">
				<% if(keywordArrList.size() > 0) {
					HashMap map = new HashMap();
					for(int i=0; i<keywordArrList.size(); i++){
						map = (HashMap)keywordArrList.get(i);
				%>
				<option value="<%=map.get("rk_seq") %>"><%=map.get("rk_name") %></option>
				<%
					}
				}
				%>
				</select>
				</form>
				</td>
			</tr>
			<!-- 게시판 끝 -->
		</table>
		</td>
	</tr>
</table>
</body>
</html>