<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.util.DateUtil"%>
<%@ page import="risk.util.StringUtil"%>
<%@ page import="risk.admin.info.*"%>
<%@ page import="risk.admin.keyword.KeywordBean"%>
<%@ page import="risk.admin.hotkeyword.hotkeywordMgr" %>
<%@ page import="risk.admin.hotkeyword.hotkeywordBean" %>
<%@ page import="risk.search.MetaBean"%>
<%@ page import="risk.search.MetaMgr"%>
<%@ page import="risk.search.solr.*"%>
<%@ page import="risk.issue.IssueCodeMgr"%>
<%@ page import="risk.issue.IssueCodeBean"%>
<%@ page import="risk.issue.IssueBean"%>	
<%@ page import="risk.issue.IssueMgr"%>
<%@ page import="risk.sms.AddressBookDao"%>
<%@ page import="risk.sms.AddressBookBean"%>
<%@ page import="risk.sms.AddressBookGroupBean"%>
<%@ page import="risk.issue.IssueDataBean"%>
<%@ page import="risk.issue.IssueDataCodeBean"%>
<%@include file="../inc/sessioncheck.jsp"%>
<%
	ParseRequest pr = new ParseRequest(request);		
	DateUtil	 du = new DateUtil();
	StringUtil		su = new StringUtil();
	pr.printParams();
	
	MetaMgr  metaMgr = new MetaMgr();
	IssueCodeMgr 	icm = new IssueCodeMgr();		
	IssueMgr issueMgr = new IssueMgr();
	AddressBookDao abDao = new AddressBookDao();
	
	ArrayList arrIcBean = new ArrayList();	 //분류코드 어레이
	
	AddressBookGroupBean addGroupBean = new AddressBookGroupBean();	
	IssueDataBean idBean = new IssueDataBean();
	IssueCodeBean icBean = new IssueCodeBean();
	IssueDataCodeBean idcBean = new IssueDataCodeBean();
	MetaBean metaBean = new MetaBean();			
			
	String checked = null;
	String checkedValue = null;
	String selected = null;
	String mode = pr.getString("mode");
	String nowPage = pr.getString("nowPage");
	String subMode = pr.getString("subMode");
	String md_seq = pr.getString("md_seq");
	String child = pr.getString("child");
	int ic_seq = 0;
	
	//분류체계코드 (0:분류항목 포함, 1:분류항목 제외)
	icm.init(0);
	
	//연관키워드 관련 
	ArrayList relationKey = null;
	ArrayList relationKeyAll = null;
	int key_seq = 0;

	List dataCodeList = null;
	//모드에 따른 분기
	if(mode.equals("update")){
		//이슈 정보
		idBean = issueMgr.getIssueData(md_seq);
		dataCodeList = issueMgr.getIssueDataCode(idBean.getId_seq());
		//연관 키워드 수동 입력 정보
	    relationKey = issueMgr.getRelationKey(idBean.getId_seq());
	    key_seq =  relationKey.size();
	    //사용 안함
	    //pseqVal = issueMgr.getPressIssueData(idBean.getId_seq());
	}
	ArrayList arrCodeList = new ArrayList();
	arrCodeList = idBean.getArrCodeList();
	
	//이슈데이터 등록 관련
	IssueMgr isMgr = new IssueMgr();
	IssueBean isBean = new IssueBean();	   	   	
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	
	//get할려는 IC_TYPE값을 num에 세팅한다.
	int num = 0;
	String selectedValue="";
	
	//자동롤링용 연관키워드 한줄로 만들기
  	//relationKeyAll = issueMgr.getRelationKey();
	relationKeyAll = new ArrayList();
	String streamKey = "";
	if(relationKeyAll.size() > 0){
	  for(int i =0; i < relationKeyAll.size(); i++){
	    if(streamKey.equals("")){
	      streamKey = (String)relationKeyAll.get(i);
	    }else{
	      streamKey += "," + (String)relationKeyAll.get(i);
	    }
	  }
	}

	/* ArrayList pressList = new ArrayList();
	pressList = isMgr.getPressList(); */
	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title></title>
<style type="text/css">
	*{margin:0;padding:0;font-family:돋움,Dotum,AppleGothic,Sans-serif,Tahoma;-webkit-text-size-adjust:none}
	header,footer,section,article,aside,nav,hgroup,details,menu,figure,figcaption{display:block}
	html{width:100%;height:100%;padding:0;margin:0;border:0}
	body{width:100%;height:100%;color:#666666;font-size:12px;line-height:15px}
	img,fieldset,iframe{border:0}
	
	.wrap{padding-left:350px}
	.ori_txts{position:fixed;top:0;left:0;bottom:0;width:350px;padding:20px;border:1px solid #d5d5d5;box-sizing:border-box;;overflow-y:auto;vertical-align:top}
	.ori_txts > .in_con{font-size:11px;line-height:18px}
	
	/* #wrap_pop{width:100%} */
	h2{height:20px;padding:13px 0 10px 10px;background:url(../../images/common/pop_tit_bg.gif);color:#fff;font-size:14px}
	h3{padding:10px 0 10px 15px;background:url(../../images/common/icon_subtit.gif) no-repeat left 11px;text-align:left;color:#363636;font-size:12px}
	table{border-collapse:separate;*border-collapse:collapse;border-spacing:0;table-layout:fixed}
	input[type=text]{padding:2px 5px;border:1px solid #d3d3d3}
	input[type=text].h24{height:22px;padding:0 5px;vertical-align:top}
	select{padding:2px 5px;border:1px solid #d3d3d3}
	textarea{display:block;padding:2px 5px;margin:0;border:1px solid #d3d3d3;resize:none}
	.invisible{position:absolute;width:0;height:0;font-size:0;overflow:hidden;visibility:hidden}
	.ui_input_table{padding:10px}
	.ui_input_table > table > caption{display:none}
	.ui_input_table > table{width:100%}
	.ui_input_table > table > tbody > tr:first-child > th,.ui_input_table > table > tbody > tr:first-child > td{border-top:2px solid #d5d5d5}
	.ui_input_table > table > tbody > tr > th,.ui_input_table > table > tbody > tr > td{height:24px;padding:5px 0;border-bottom:1px dotted #cccccc;text-align:left}
	.ui_input_table > table > tbody > tr > th{background:#f7f7f7;color:#555555;font-weight:bold;text-indent:15px}
	.ui_input_table > table > tbody > tr > th span{padding-left:6px;background:url(../../images/common/icon_dot.gif) no-repeat left 8px}
	.ui_input_table > table > tbody > tr > th span strong{padding-left:6px;color:#519eff}
	.ui_input_table > table > tbody > tr > td{padding-left:10px}
	div.botBtns{padding:15px 0;text-align:center}
	span.comp{display:inline-block;padding-right:10px}
	span.comp input{position:relative;top:2px;border:none}
	span.comp input + label{padding-left:5px}
	span.comp:last-child{padding-right:0}
	.btn_add{width:39px;height:24px;border:none;background:url(../../images/admin/member_group/btn_add.gif) no-repeat 0 0;vertical-align:top;cursor:pointer;outline:none}
	.keyword_list{padding:10px 0 5px}
	.keyword_list:empty{display:none;padding:0}
	.keyword_list:after{display:block;clear:both;content:''}
	.keyword_list li{float:left}
	.keyword_list .item{padding:5px;margin-right:8px;border:1px solid #efefef;border-radius:2px;background:#f9f9f9;line-height:1}
	.keyword_list .item:hover{border:1px solid #c7c7c7}
	.keyword_list .item .btn_del{width:13px;height:13px;margin:1px 0 0 6px;border:none;background:url(../../images/search/delete_btn_01.gif) no-repeat 0 0;text-align:left;text-indent:-9999px;cursor:pointer;outline:none;vertical-align:top}
</style>
<link rel="stylesheet" type="text/css" href="<%=SS_URL%>css/design.css">
<link href="<%=SS_URL%>css/jquery.autocomplete.css" rel="stylesheet"type="text/css" />
<script src="<%=SS_URL%>js/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/common.js"  type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/jquery.autocomplete.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=SS_URL%>js/design.js"></script>
<script src="<%=SS_URL%>js/spin.js" type="text/javascript"></script>
<script type="text/javascript">
var key_seq = <%=key_seq%>;
var streamKey = '<%=streamKey%>';

var regChk = false;
function save_issue(mode){
	
	
	 //연관키워드
    if(document.getElementById("tr_relationKey").style.display != 'none'){
     var obj_td = null;
     var names = '';
     var html = '';
     var nameIdx = -1;
     
     $("#tb_keywordList").find("li").each(function(){
    	 html = $(this).find("div").find("span").text();
    	if(names == '') {
    		names =	html.trim();
    	}else{
    		names += ',' + html.trim();
    	}
     });
     
     $("[name=keyNames]").val(names);
     
    }
	 
	
	//선택된 값 세팅
	var typeCode = settingTypeCode();
	
	//mode에 따라 form submit
	var f = document.fSend;
	if(!regChk){
		regChk = true;
		f.typeCode.value = typeCode;
		f.mode.value = mode;
		
        var formData = $("[name=fSend]").serialize();
        
		$.ajax({
			type : "POST"
			,url: "issue_data_prc.jsp"
			,timeout: 30000
			,data : formData
			,dataType : 'text'
			,async: true
			,success : function(data){
				
				if(data != ""){
					alert("이슈가 수정 되었습니다.");
					window.close();
				}
				
			  }
			,beforeSend : function(){}			
			});	
	}else{
		alert('등록중입니다.. 잠시만 기다려주세요.');
	}
	
	
}

//유효성 검사 선택된 값이 있으면 true를 리턴, 없으면 false를 리턴
function validation_check(id){
	var target = "icType"+id;
	var chk = 0;
	
	$("[name="+target+"]").each(function(index){
		if($(this).prop("checked")){
			chk++;
		}
	});
	
	if(chk == 1){
		return true;
	}else{
		return false;
	}	
}



function settingTypeCode(){
	//라디오 버튼 type값 세팅
	var checked_id = null;
	//선택박스 type값 세팅
	var select_id = null;
	//유효성 검사
	
	checked_id = [1,2,3,9,6,21];
	select_id = [4];
	
	
	var form = document.fSend;

	form.typeCode.value = '';
	if(checked_id != null){
		for(var i=0; i < checked_id.length; i++){
			$("[name=icType"+checked_id[i]+"]:checkbox").each(function(){
				if($(this).prop("checked")){
					form.typeCode.value += form.typeCode.value=='' ? $(this).val() : '@'+$(this).val();
				}
			});
		}
	
	}
		
	var selectedVal;
	if(select_id != null){
		for(var i=0; i < select_id.length; i++){
			selectedVal =  $("[name=icType"+select_id[i]+"] option:selected").val();		
			form.typeCode.value += form.typeCode.value=='' ? selectedVal : '@'+selectedVal;
		}	
	}
	
	
	return form.typeCode.value;
}



//연관키워드 추가
function addKeyword() {
var f = document.fSend;
var keyword = f.txt_relationKey.value.trim();

if(keyword != ''){
  key_seq++;
  
  var AddHtml = "<li id='td_keyword_" + key_seq +"'><div class='item'><span>" + keyword + "</span><button type='button' onclick=\"delKeyword('"+ key_seq +"');\" class='btn_del' title='키워드 삭제'>삭제</button></div></li>";
  $("#tb_keywordList").append(AddHtml);
  
}

f.txt_relationKey.value = '';
}
//연관키워드 삭제
function delKeyword(idx){
	
	$("#td_keyword_"+idx).remove();
}


</script>
</head>
<body>
<form name="fSend" id="fSend" action="issue_data_prc.jsp" method="post" onsubmit="return false;">
<input name="mode" id="mode" type="hidden" value="<%=mode%>"><!-- 모드 -->
<input name="subMode" id="subMode" type="hidden" value=<%=subMode%>>
<input type="hidden" name="nowPage" value="<%=nowPage %>">
<input type="hidden" name="tagGroupCode" id="tagGroupCode">
<input type="hidden" name="regTagCode" id="regTagCode">
<input type="hidden" id="keyNames" name="keyNames" value="">
<input type="hidden" id="p_seq" name="p_seq" value="">
<%if(mode.equals("update")){%>
<input name="id_seq" id="id_seq" type="hidden" value="<%=idBean.getId_seq()%>"><!-- 기사번호 -->
<input name="md_seq" id="md_seq" type="hidden" value="<%=idBean.getMd_seq()%>"><!-- 기사번호 -->
<input name="s_seq" id="s_seq" type="hidden" value="<%=idBean.getS_seq()%>"><!-- 사이트번호 -->
<input name="sg_seq" id="sg_seq" type="hidden" value="<%=idBean.getSg_seq()%>"><!-- 사이트 그룹 -->
<input name="md_date" id="md_date" type="hidden" value="<%=idBean.getMd_date()%>"><!-- 수십 시간-->
<input name="md_site_menu" id="md_site_menu" type="hidden" value="<%=idBean.getMd_site_menu()%>"><!-- 사이트 메뉴 -->
<input name="id_mailyn" id="id_mailyn" type="hidden" value="<%=idBean.getId_mailyn()%>"><!-- 사이트 메뉴 -->
<input name="typeCode" id="typeCode" type="hidden" value=""><!-- 타입코드 -->
<input name="mailreceiver" id="mailreceiver" type="hidden" value=""><!--선택된 메일 수신자 -->
<input name="selItseq" id="selItseq" type="hidden" value="<%=idBean.getIt_seq()%>"><!--선택된 it_seq -->
<input name="child" id="child" type="hidden" value="<%=child%>" />
<input name="user_id" type="hidden" value="<%=idBean.getUser_id() %>" />
<input name="user_nick" type="hidden" value="<%=idBean.getUser_nick() %>" />
<input name="blog_visit_count" type="hidden" value="<%=idBean.getBlog_visit_count() %>" />
<input name="cafe_name" type="hidden" value="<%=idBean.getCafe_name() %>" />
<input name="cafe_member_count" type="hidden" value="<%=idBean.getCafe_member_count() %>" />
<%} %>


<div class="wrap">

    <div class="ori_txts">
        <div class="in_con">
<%
String contents ="";
if ("update".equals(mode)) {
	contents = idBean.getId_content().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
}
	contents = contents.replaceAll("<div", "");
%>
	<%=contents %>
        </div>
    </div>
<div class="wrap_pop">
	<h2>이슈등록</h2>
	<a href="javascript:window.close();" style="position:absolute;top:12px;right:15px"><img src="/images/search/pop_tit_close.gif" alt="창닫기"></a>
	<div class="ui_input_table">
		<table summary="기본정보">
		<caption>기본정보</caption>
		<colgroup>
		<col style="width:130px">
		<col>
		<col style="width:130px">
		<col>
		</colgroup>
		<tbody>
		<tr>
		<th><span>제목</span></th>
		<td colspan="3"><input id="ui_input_00_00" name="id_title" type="text" style="width:90%" value="<%if(mode.equals("insert")){out.print(su.ChangeString(metaBean.getMd_title()));}else{out.print(su.ChangeString(idBean.getId_title()));}%>"><label for="ui_input_00_00" class="invisible">제목 입력</label></td>
		</tr>
		<tr>
		<th><span>URL</span></th>
		<td colspan="3"><input id="ui_input_00_01" name="id_url" type="text" style="width:90%" value="<%if(mode.equals("insert")){out.print(metaBean.getMd_url());}else{out.print(idBean.getId_url());}%>"><label for="ui_input_00_01" class="invisible">URL 입력</label></td>
		</tr>
		<tr>
		<th><span>사이트이름</span></th>
		<td><input id="ui_input_00_02" name="md_site_name" type="text" style="width:70%" value="<%if(mode.equals("insert")){out.print(metaBean.getMd_site_name());}else{out.print(idBean.getMd_site_name());}%>"><label for="ui_input_00_02" class="invisible">사이트이름 입력</label></td>
		<th><span>정보분류시간</span></th>
		<td><input id="ui_input_00_03" name="id_regdate" type="text" style="width:70%" value="<%if(mode.equals("insert")){out.print(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));}else{out.print(idBean.getFormatId_regdate("yyyy-MM-dd HH:mm:ss"));}%>" readonly="readonly"><label for="ui_input_00_03" class="invisible">정보분류시간 입력</label></td>
		</tr>
		<tr>
		<th><span>정보종류</span></th>
		<td colspan="3">
			<select id="ui_select_00_00" name="md_type" style="width:100px">
			<option value="1" <%if(mode.equals("insert")){if(metaBean.getMd_type().equals("1")){out.print("selected");}}else{if(idBean.getMd_type().equals("1")){out.print("selected");}}%>>언론</option>
			<option value="2" <%if(mode.equals("insert")){if(Integer.parseInt(metaBean.getMd_type()) > 1){out.print("selected");}}else{if(idBean.getMd_type().equals("2")){out.print("selected");}}%>>개인</option>
			</select><label for="ui_select_00_00" class="invisible">정보종류 선택</label>
		</td>
		</tr>
		<tr>
		<th><span>정보내용</span></th>
		<td colspan="3"><textarea id="ui_textarea_00_00"name="id_content" style="width:90%;height:100px"><%if(mode.equals("insert")){out.print(su.cutKey(su.ChangeString(metaBean.getMd_content()),"",300,""));}else{out.print(su.ChangeString(idBean.getId_content()));}%></textarea><label for="ui_textarea_00_00" class="invisible">정보내용 입력</label></td>
		</tr>
		</tbody>
		</table>
	</div>
	<div class="ui_input_table">
		<h3>정보분류 항목</h3>
		<table summary="정보분류 항목">
		<caption>정보분류 항목</caption>
		<colgroup>
		<col style="width:130px">
		<col>
		</colgroup>
		<tbody>
		
		<tr>
		<th><span id="label_1">구분<strong>*</strong></span></th><!-- 필수 속성인 경우 strong 태그 삽입 -->
		<td>
<%		
	num = 1;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	String cssNone = "";   
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(dataCodeList.size() > 0){
			Map codeMap = null;
			String typeCode = ""; 
			for (int d=0; d < dataCodeList.size(); d++) {
				codeMap = new HashMap();
				codeMap = (HashMap) dataCodeList.get(d);
				typeCode = codeMap.get("IC_TYPE").toString()+","+codeMap.get("IC_CODE").toString();
				if ( typeCode.equals( icBean.getIc_type()+","+icBean.getIc_code() ) ) {
					checked = "checked";
					break;
				}else{
					checked = "";
				}
			}
		}
		
%>
			<span class="comp">
			<input id="input_radio_01_0<%=num%>_0<%=i%>" class="validation_<%=num%>" name="icType<%=num%>" value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' type="checkbox" <%=checked%> />
			<label for="input_radio_01_0<%=num%>_0<%=i%>"><%=icBean.getIc_name()%></label>
			</span>	
	
<%}%>
		</td>
		</tr>
<tr>
		<th><span id="label_2">정보구분<strong>*</strong></span></th><!-- 필수 속성인 경우 strong 태그 삽입 -->
		<td>
<%		
	num = 2;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	cssNone = "";   
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		checked = "";
		if(dataCodeList.size() > 0){
			Map codeMap = null;
			String typeCode = ""; 
			for (int d=0; d < dataCodeList.size(); d++) {
				codeMap = new HashMap();
				codeMap = (HashMap) dataCodeList.get(d);
				typeCode = codeMap.get("IC_TYPE").toString()+","+codeMap.get("IC_CODE").toString();
				if ( typeCode.equals( icBean.getIc_type()+","+icBean.getIc_code() ) ) {
					checked = "checked";
					break;
				}else{
					checked = "";
				}
			}
		}
%>
			<span class="comp">
			<input id="input_radio_01_0<%=num%>_0<%=i%>" class="validation_<%=num%>" name="icType<%=num%>" value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' type="checkbox" <%=checked%> />
			<label for="input_radio_01_0<%=num%>_0<%=i%>"><%=icBean.getIc_name()%></label>
			</span>	
	
<%}%>
		</td>
		</tr>	
<tr>
		<th><span id="label_21">정보구분 상세<strong>*</strong></span></th><!-- 필수 속성인 경우 strong 태그 삽입 -->
		<td>
<%		
	num = 21;
	arrIcBean = new ArrayList();
	arrIcBean = icm.getCodeList("21", "");
	String pcode = "";
	cssNone = "";   
	Map code = null;
	for (int i = 0; i < arrIcBean.size(); i++) {
		code = new HashMap();
		code = (HashMap) arrIcBean.get(i);
		
		if(i != 0){
			if(!pcode.equals(code.get("IC_PCODE").toString())){
				out.print("<br>");
			}
		}
			
		
		checked = "";
		if(dataCodeList.size() > 0){
			Map codeMap = null;
			String typeCode = ""; 
			for (int d=0; d < dataCodeList.size(); d++) {
				codeMap = new HashMap();
				codeMap = (HashMap) dataCodeList.get(d);
				typeCode = codeMap.get("IC_TYPE").toString()+","+codeMap.get("IC_CODE").toString();
				if ( typeCode.equals( code.get("IC_TYPE")+","+code.get("IC_CODE") ) ) {
					checked = "checked";
					break;
				}else{
					checked = "";
				}
			}
		}
%>
			<span class="comp">
			<input id="input_radio_01_0<%=num%>_0<%=i%>" class="validation_<%=num%>" name="icType<%=num%>" value='<%=code.get("IC_TYPE")%>,<%=code.get("IC_CODE")%>' type="checkbox" <%=checked%> />
			<label for="input_radio_01_0<%=num%>_0<%=i%>"><%=code.get("IC_NAME")%></label>
			</span>	
<%
			pcode = code.get("IC_PCODE").toString();
	}%>
		</td>
		</tr>				
<tr>
		<th><span id="label_3">민원유형</span></th><!-- 필수 속성인 경우 strong 태그 삽입 -->
		<td>
<%		
	num = 3;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	cssNone = "";   
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		checked = "";
		if(dataCodeList.size() > 0){
			Map codeMap = null;
			String typeCode = ""; 
			for (int d=0; d < dataCodeList.size(); d++) {
				codeMap = new HashMap();
				codeMap = (HashMap) dataCodeList.get(d);
				typeCode = codeMap.get("IC_TYPE").toString()+","+codeMap.get("IC_CODE").toString();
				if ( typeCode.equals( icBean.getIc_type()+","+icBean.getIc_code() ) ) {
					checked = "checked";
					break;
				}else{
					checked = "";
				}
			}
		}
%>
			<span class="comp">
			<input id="input_radio_01_0<%=num%>_0<%=i%>" class="validation_<%=num%>" name="icType<%=num%>" value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' type="checkbox"  <%=checked%> />
			<label for="input_radio_01_0<%=num%>_0<%=i%>"><%=icBean.getIc_name()%></label>
			</span>	
	
<%}%>
		</td>
		</tr>
		
						<tr>
		<th><span id="label_9">성향</span></th><!-- 필수 속성인 경우 strong 태그 삽입 -->
		<td>
<%		
	num = 9;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	cssNone = "";   
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		checked = "";
		if(dataCodeList.size() > 0){
			Map codeMap = null;
			String typeCode = ""; 
			for (int d=0; d < dataCodeList.size(); d++) {
				codeMap = new HashMap();
				codeMap = (HashMap) dataCodeList.get(d);
				typeCode = codeMap.get("IC_TYPE").toString()+","+codeMap.get("IC_CODE").toString();
				if ( typeCode.equals( icBean.getIc_type()+","+icBean.getIc_code() ) ) {
					checked = "checked";
					break;
				}else{
					checked = "";
				}
			}
		}
%>
			<span class="comp">
			<input id="input_radio_01_0<%=num%>_0<%=i%>" class="validation_<%=num%>" name="icType<%=num%>" value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' type="checkbox"  <%=checked%> />
			<label for="input_radio_01_0<%=num%>_0<%=i%>"><%=icBean.getIc_name()%></label>
			</span>	
	
<%}%>
		</td>
		</tr>	
<tr>
		<th><span id="label_3">출처</span></th><!-- 필수 속성인 경우 strong 태그 삽입 -->
		<td>
<%		
	num = 6;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	cssNone = "";   
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		checked = "";
		if(dataCodeList.size() > 0){
			Map codeMap = null;
			String typeCode = ""; 
			for (int d=0; d < dataCodeList.size(); d++) {
				codeMap = new HashMap();
				codeMap = (HashMap) dataCodeList.get(d);
				typeCode = codeMap.get("IC_TYPE").toString()+","+codeMap.get("IC_CODE").toString();
				if ( typeCode.equals( icBean.getIc_type()+","+icBean.getIc_code() ) ) {
					checked = "checked";
					break;
				}else{
					checked = "";
				}
			}
		}
%>
			<span class="comp">
			<input id="input_radio_01_0<%=num%>_0<%=i%>" class="validation_<%=num%>" name="icType<%=num%>" value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' type="checkbox"  <%=checked%> />
			<label for="input_radio_01_0<%=num%>_0<%=i%>"><%=icBean.getIc_name()%></label>
			</span>	
	
<%}%>

		</td>
		</tr>					
		<tr>
		<th><span id="label_4">주요이슈</span></th>
		<td>
<%
	num = 4;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
%>		
			<select id="input_select_01_<%=num%>" class="validation_<%=num%>" name="icType<%=num%>" style="min-width:180px">
				<option value="">선택하세요</option>
<%
		List type4List = icm.getCodeList("4","","DESC");
		
		HashMap map = new HashMap();
		for (int i = 0; i < type4List.size(); i++) {
			map = (HashMap) type4List.get(i);
			
			if(dataCodeList.size() > 0){
				Map codeMap = null;
				String typeCode = ""; 
				for (int d=0; d < dataCodeList.size(); d++) {
					codeMap = new HashMap();
					codeMap = (HashMap) dataCodeList.get(d);
					typeCode = codeMap.get("IC_TYPE").toString()+","+codeMap.get("IC_CODE").toString();
					if ( typeCode.equals( map.get("IC_TYPE")+","+map.get("IC_CODE") ) ) {
						selected = "selected";
						break;
					}else{
						selected = "";
					}
				}
			}
%>  			
			<option value='<%=map.get("IC_TYPE")%>,<%=map.get("IC_CODE")%>' <%=selected%> ><%=map.get("IC_NAME")%></option>
<%}%>			
			</select><label for="input_select_01_<%=num%>" class="invisible">주요이슈</label>
			<ul class="keyword_list" id="tb_issueList">
<%
			checked = "";
			if( dataCodeList.size() > 0 ) {
				Map codeMap = null;
				String typeCode = ""; 
				for (int d=0; d < dataCodeList.size(); d++) {
					codeMap = new HashMap();
					codeMap = (HashMap) dataCodeList.get(d);
					if ( "4".equals( codeMap.get("IC_TYPE").toString() ) ) {
						checked = "checked";
%>
				<li id="li_<%=codeMap.get("IC_CODE")%>">
					<div class="item"><span><%=codeMap.get("IC_NAME")%></span></div>
				</li>
<%
					}
				}
			}
%>
			</ul>
		</td>
		</tr>
				<!-- 연관 키워드 -->
		<tr id="tr_relationKey" style="display:<%if(mode.equals("update_multi")){out.print("none");}%>">
			<th><span id="label_keyword">연관키워드</span></th>
			<td>
				<input id="txt_relationKey" type="text" class="h24" style="width:200px" onkeypress="javascript:if(event.keyCode == 13){addKeyword();}"><label for="txt_relationKey" class="invisible">연관 키워드 입력</label>
				<button id="btn_key_add" type="button" class="btn_add" onclick="addKeyword();"></button>
				<ul class="keyword_list" id="tb_keywordList">
				<%
				if(relationKey != null) {
                    for(int i =0; i < relationKey.size(); i++) {
                      out.println("<li id='td_keyword_" + (i + 1) +"'><div class='item'><span>" + (String)relationKey.get(i) + "</span><button type='button' onclick=\"delKeyword('"+ (i+1) +"');\" class='btn_del' title='키워드 삭제'>삭제</button></div></li>");
                    }
                  }
                %></ul>
		</tr>
		<!-- 연관 키워드 END -->
		</tbody>
		</table>
	</div>
	<div class="botBtns">
		<a href="javascript:save_issue('<%=mode%>');"><img src="/images/admin/member/btn_save2.gif" alt="저장"></a>
		<a href="javascript:window.close();"><img src="/images/admin/member/btn_cancel.gif" alt="취소"></a>
	</div>
</div>
</div>
</form>
</body>
</html>