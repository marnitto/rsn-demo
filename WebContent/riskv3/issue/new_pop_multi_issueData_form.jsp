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
	List arrAddGroupBean = new ArrayList();  //수신자그룹 어레이
	
	hotkeywordMgr hm = new hotkeywordMgr();
	hotkeywordBean hb = new hotkeywordBean();
	
	AddressBookGroupBean addGroupBean = new AddressBookGroupBean();	
	IssueDataBean idBean = new IssueDataBean();
	IssueCodeBean icBean = new IssueCodeBean();
	MetaBean metaBean = new MetaBean();			
			
	String selected = null;
	String mode = pr.getString("mode", "update_multi");
	String issue_m_seq = pr.getString("issue_m_seq");
	
	/* String md_seqs = pr.getString("md_seqs");
	String nowPage = pr.getString("nowPage");
	String subMode = pr.getString("subMode");
	String md_seq = pr.getString("md_seq"); */
	int ic_seq = 0;
	
	//분류체계코드 (0:분류항목 포함, 1:분류항목 제외)
	icm.init(0);
	
	//SMS대그룹  HOT ISSUE중그룹에 속하는 키워드
	ArrayList keywordInfo = new ArrayList();
	
	String k_xp = "";
	
	//이슈데이터 등록 관련
	IssueMgr isMgr = new IssueMgr();
	IssueBean isBean = new IssueBean();	   	   	
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	
	//get할려는 IC_TYPE값을 num에 세팅한다.
	int num = 0;
	String checked ="";
	String checkedValue="";
	 
	ArrayList pressList = new ArrayList();
	pressList = isMgr.getPressList();
		
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
	.ui_input_table > table > tbody > tr > th span{padding-left:6px;background:url(../../images/common/icon_dot.gif) no-repeat left 4px}
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
<link href="<%=SS_URL%>css/jquery.autocomplete.css" rel="stylesheet"type="text/css" />
<script src="<%=SS_URL%>js/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/common.js"  type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/jquery.autocomplete.js" type="text/javascript"></script>
<script type="text/javascript">

<%-- var key_seq = <%=key_seq%>;
var streamKey = '<%=streamKey%>'; --%>

//death2인 코드 값들을 불러 온다.
//type : 부모 속성의 타입값 - IC_PTYPE
//code : 부모 속성의 코드값 - IC_PCODE
//name : 부모 속성의 이름 - IC_NAME 
function getTypeCodeDeath2(type, code, name){
	var icPtype = type;
	var icPcode = code;
	var icPName = name;
	$("#tr_icType"+icPtype).css("display","");
	$("#label_"+icPtype+"1").html(icPName+"<input type='checkbox' name='chk_mod_type' value='31' />");
	
	var icType1 = "H";
	var checked= "";
	
	$.ajax({
		type:'POST',
		url:"getJsonData.jsp",
		dataType:'json',
		data:{ic_Ptype:icPtype, ic_Pcode:icPcode, flag: "2death", icType1:icType1},
		success:function(data){
			var innerSpan = "";
			
			$.each(data.list, function(index){
				var num = data.list[index].icType;
				if(data.list.length==1){
					checked = "checked";
					$("[name=focus_icType"+num+"]").val(data.list[index].icType+","+data.list[index].icCode);
				}else{
					checked = "";
				}	
				innerSpan += "<span class='comp'><input id='input_radio_01_"+num+"_"+index+"' class='validation_"+num+"' name='icType"+num+"' onclick='comboCheck(this);' value='"+data.list[index].icType+","+data.list[index].icCode+"' type='radio' "+checked+" />"
				+"<label for='input_radio_01_"+num+"_"+index+"'>"+data.list[index].icName+"</label></span>"
			});
			$("#td_icType"+icPtype).html(innerSpan);
		}
		,error:function(){}
	
	});
}

//유효성 검사 선택된 값이 있으면 true를 리턴, 없으면 false를 리턴
function validation_check(id){
	var target = "validation_"+id;
	var chk = "";
	
	$("."+target).each(function(index){
		if($(this).is(":checked")){chk="true";}
	});
	
	if(chk.length > 1){return true;
	}else{return false;}	
}

//선택된 코드 값들을 insert하기 위한 값으로 세팅
function settingTypeCode(){
	//라디오 버튼 type값 세팅
	var radio_id = [2,3,31,4,9,10,12,14];
	//선택박스 type값 세팅
	var select_id = [11];
	var form = document.fSend;

	form.typeCode.value = '';
	
	for(var i=0; i < radio_id.length; i++){
		$("[name=icType"+radio_id[i]+"]:radio").each(function(){
			if($(this).is(":checked")){
				form.typeCode.value += form.typeCode.value=='' ? $(this).val() : '@'+$(this).val();
			}
		});
	}
	
	var selectedVal;
	for(var i=0; i < select_id.length; i++){
		selectedVal =  $("[name=icType"+select_id[i]+"] option:selected").val();
		if(selectedVal != ""){
			form.typeCode.value +='@'+selectedVal;
		}
	}
	
	return form.typeCode.value;
}

var regChk = false;//이미 등록중인지 확인하기위한 값
//이슈관리 저장 함수
function save_issue(mode, event){
	
	var chk_type = "";
	var chk_types = "";
	$("[name=chk_mod_type]").each(function(){
		if ( $(this).prop("checked") ) {
			chk_type = $(this).val();
			
			if($("[name=focus_icType"+chk_type+"]").val() == ''){
				var title = $("#label_"+chk_type).text();
				alert(title+"을(를) 선택해 주세요.");
				return;
			}else{
				if(chk_types == ''){ chk_types = $(this).val();
				}else{ chk_types += ","+$(this).val(); }
			}
		}
	});
	
	var typeCode = settingTypeCode();

	//mode에 따라 form submit
	var f = document.fSend;
	
	if(!regChk){
		//regChk = true;
		f.typeCode.value = typeCode;	
		f.mode.value = mode;
		f.chk_types.value = chk_types;
		//f.target='if_samelist';
		//f.action='issue_data_prc.jsp';
        //f.submit();
        
        /* 
        * ajax 방법으로 이슈등록 프로세스 변경
        * 2014.12.31
        */
        var formData = $("#fSend").serialize();
        var imgId = ""; 
		$.ajax({
			type : "POST"
			,url: "../issue/issue_data_prc.jsp"
			,timeout: 30000
			,data : formData
			,dataType : 'text'
			,async: true
			,success : function(data){
						if(data != ""){
							alert("이슈가 수정 되었습니다.");
							window.close();
						}else{
							alert("이슈 수정에 실패 했습니다.");
						}
					  }
			,beforeSend : function(){}
			});
	}else{
		alert('등록중입니다.. 잠시만 기다려주세요.');
	}
}

//체크해제
function comboCheckType2(obj,icType,icCode,icName){

		var f = document.fSend;
		focusObj = eval('f.focus_'+ obj.name);

		if(focusObj.value == obj.value){
			obj.checked = false;
			focusObj.value = '';
			if("icType2" == obj.name){
				$("#tr_icType2").css("display", "none");
				$("[name=icType21]").attr("checked", false);
			}else if("icType3" == obj.value){
				$("#tr_icType3").css("display", "none");
				$("[name=icType31]").attr("checked", false);
			}
		}else{
			getTypeCodeDeath2(icType,icCode,icName);
			focusObj.value = obj.value;
		}
	}

//체크해제
function comboCheck(obj){

	var f = document.fSend;
	focusObj = eval('f.focus_'+ obj.name);

	if(focusObj.value == obj.value){
		obj.checked = false;
		focusObj.value = '';
	}else{
		focusObj.value = obj.value;
	}
}

function settingPseq(){
	var pseq = $("[name=typeCode10_pseq] option:selected").val();
	$("#p_seq").val(pseq);
}

$(function(){
	/* $('#txt_relationKey').autocomplete(streamKey.split(',')); */
	
	$("[name=icType10]").click(function(){
		
		if( $("[name=focus_icType10]").val() == "10,1"){
			$("[name=typeCode10_pseq]").css("display", '');
			var pseq = $("[name=typeCode10_pseq] option:selected").val();
			$("#p_seq").val(pseq);
		}else{
			$("[name=typeCode10_pseq]").css("display", 'none');
			$("#p_seq").val('');
		}
	});
	
	
	$("[name=typeCode10_pseq]").change(function(){
		var pseq = $("[name=typeCode10_pseq] option:selected").val();
		$("#p_seq").val(pseq);
	});
	
});
</script>
</head>
<body>
<form name="fSend" id="fSend" action="issue_data_prc.jsp" method="post" onsubmit="return false;">
<input name="mode" id="mode" type="hidden" value="">
<input name="id_seqs" id="id_seqs" type="hidden" value="<%=issue_m_seq%>">
<input type="hidden" name="typeCode" value="">
<input type="hidden" name="chk_types" value="">
<input type="hidden" id="p_seq" name="p_seq" value="">
<div class="wrap_pop">
	<h2>이슈 멀티 수정</h2>
	<a href="javascript:window.close();" style="position:absolute;top:12px;right:15px"><img src="/images/search/pop_tit_close.gif" alt="창닫기"></a>
	<div class="ui_input_table">
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
		<th><span id="label_2">회사<input type="checkbox" name="chk_mod_type" value="2" /> </span></th><!-- 필수 속성인 경우 strong 태그 삽입 -->
		<td>
		<input type="radio" name="icType1" value="1,1" style="display: none;" checked="checked" />
<%
	num = 2;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	String cssNone = "";   
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
%>
			<span class="comp hongboPart" id="typeCode2_<%=i%>" >
			<input id="input_radio_01_0<%=num%>_0<%=i%>" class="validation_<%=num%>" name="icType<%=num%>" value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' type="radio" onclick="comboCheckType2(this,'<%=icBean.getIc_type()%>','<%=icBean.getIc_code()%>','<%=icBean.getIc_name()%>');" <%=checked%> />
			<label for="input_radio_01_0<%=num%>_0<%=i%>"><%=icBean.getIc_name()%></label>
			</span>	
	
<%}%>
			<input type='hidden' name='focus_icType<%=num%>' value="" />
		</td>
		</tr>
		
		<tr>
		<th><span id="label_9">성향<input type="checkbox" name="chk_mod_type" value="9" /></span></th>
		<td>
<%
	num = 9;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
%>  
			<span class="comp">
			<input id="input_radio_01_0<%=num%>_0<%=i%>" class="validation_<%=num%>" name="icType<%=num%>" value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' onclick="comboCheck(this);" type="radio">
			<label for="input_radio_01_0<%=num%>_0<%=i%>"><%=icBean.getIc_name()%></label>
			</span>
<%}%>
			<input type='hidden' name='focus_icType<%=num%>' value="" />
		</td>
		</tr>
		
		<tr class="soboNone">
		<th><span id="label_3">주제그룹<input type="checkbox" name="chk_mod_type" value="3" /></span></th>
		<td>
<%
	num = 3;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
%>  
			<span class="comp">
			<input id="input_radio_01_0<%=num%>_0<%=i%>" class="validation_<%=num%>" name="icType<%=num%>" value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' type="radio" onclick="comboCheckType2(this,'<%=icBean.getIc_type()%>','<%=icBean.getIc_code()%>','<%=icBean.getIc_name()%>');" />
			<label for="input_radio_01_0<%=num%>_0<%=i%>"><%=icBean.getIc_name()%></label>
			</span>
<%}%>
			<input type='hidden' name='focus_icType<%=num%>' value="" />
		</td>
		</tr>
		<!--  2death 정보속성 값 -->
		<tr style="display: none;" id="tr_icType<%=num%>">
		<th><span id="label_31"><input type="checkbox" name="chk_mod_type" value="31" /></span><input type='hidden' name='focus_icType31' value="" /></th>
		<td id="td_icType<%=num%>"></td>
		</tr>		
		<!--  2death 정보속성 값 -->
		
<tr>
		<th><span id="label_4">금융과제<input type="checkbox" name="chk_mod_type" value="4" /></span></th>
		<td>
<%
	num = 4;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
%>  
			<span class="comp">
			<input id="input_radio_01_0<%=num%>_0<%=i%>" class="validation_<%=num%>" name="icType<%=num%>" value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' onclick="comboCheck(this);" type="radio">
			<label for="input_radio_01_0<%=num%>_0<%=i%>"><%=icBean.getIc_name()%></label>
			</span>
<%}%>
			<input type='hidden' name='focus_icType<%=num%>' value="" />
		</td>
		</tr>		
		
		<tr class="soboNone">
		<th><span id="label_10">보도자료<input type="checkbox" name="chk_mod_type" value="10" /></span></th>
		<td>
<%
	num = 10;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
%>  
			<span class="comp">
			<input id="input_radio_01_0<%=num%>_0<%=i%>" class="validation_<%=num%>" name="icType<%=num%>" value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' onclick="comboCheck(this);" type="radio">
			<label for="input_radio_01_0<%=num%>_0<%=i%>"><%=icBean.getIc_name()%></label>
			</span>
<%}%>
<%if(num == 10){ 
		String none = "none";
	
		%>
				<select name="typeCode10_pseq" style="display: <%=none%>;margin-left: 10px;height: 24px;" onchange="settingPseq();">
				<%
					Map pMap = null;
					for(int p=0; p < pressList.size(); p++){
						pMap = new HashMap();
						pMap = (HashMap)pressList.get(p);
						%>
						<option value="<%=pMap.get("P_SEQ")%>"> <%=pMap.get("P_TITLE")%> </option>
						<%
					}
				%>
				</select>
				<%} %>
			<input type='hidden' name='focus_icType<%=num%>' value="" />			
		</td>
		</tr>
		<tr class="soboNone">
		<th><span id="label_12">지면<input type="checkbox" name="chk_mod_type" value="12" /></span></th>
		<td>
<%
	num = 12;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
%>  
			<span class="comp">
			<input id="input_radio_01_0<%=num%>_0<%=i%>" class="validation_<%=num%>" name="icType<%=num%>" value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' onclick="comboCheck(this);" type="radio">
			<label for="input_radio_01_0<%=num%>_0<%=i%>"><%=icBean.getIc_name()%></label>
			</span>
<%}%>
			<input type='hidden' name='focus_icType<%=num%>' value="" />			
		</td>
		</tr>
		<tr class="soboNone">
		<th><span id="label_11">주요이슈<input type="checkbox" name="chk_mod_type" value="11" /></span></th>
		<td>
<%
	num = 11;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
%>		
			<select id="input_select_01_<%=num%>" class="validation_<%=num%>" name="icType<%=num%>" style="min-width:180px">
			<option value='' >선택하세요</option>
<%
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
%>  			
			<option value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' ><%=icBean.getIc_name()%></option>
<%}%>			
			</select><label for="input_select_01_<%=num%>" class="invisible">주요이슈</label>
		</td>
		</tr>
		<tr class="soboNone">
		<th><span id="label_14">금융꿀팁<input type="checkbox" name="chk_mod_type" value="14" /></span></th>
		<td>
<%
	num = 14;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
%>  
			<span class="comp">
			<input id="input_radio_01_0<%=num%>_0<%=i%>" class="validation_<%=num%>" name="icType<%=num%>" value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' onclick="comboCheck(this);" type="radio">
			<label for="input_radio_01_0<%=num%>_0<%=i%>"><%=icBean.getIc_name()%></label>
			</span>
<%}%>
			<input type='hidden' name='focus_icType<%=num%>' value="" />			
		</td>
		</tr>		
		<!-- 연관 키워드 -->
		<!-- <tr id="tr_relationKey">
			<th><span id="label_keyword">연관키워드</span></th>
			<td>
				
				<input id="txt_relationKey" type="text" class="h24" style="width:200px" onkeypress="javascript:if(event.keyCode == 13){addKeyword();}"><label for="txt_relationKey" class="invisible">연관 키워드 입력</label>
				<button id="btn_key_add" type="button" class="btn_add" onclick="addKeyword();"></button>
				<ul class="keyword_list" id="tb_keywordList"></ul>
			</td>
		</tr> -->
		<!-- 연관 키워드 END -->
		
		</tbody>
		</table>
	</div>
	<div class="botBtns">
		<a href="javascript:save_issue('<%=mode%>');"><img src="/images/admin/member/btn_save2.gif" alt="저장"></a>
		<a href="javascript:window.close();"><img src="/images/admin/member/btn_cancel.gif" alt="취소"></a>
	</div>
</div>
</form>
</body>
</html>