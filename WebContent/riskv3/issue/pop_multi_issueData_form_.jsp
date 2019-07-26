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
	
	IssueDataBean idBean = new IssueDataBean();
	IssueCodeBean icBean = new IssueCodeBean();
	MetaBean metaBean = new MetaBean();			
			
	String selected = null;
	String mode = pr.getString("mode", "update_multi");
	String issue_m_seq = pr.getString("issue_m_seq");
	
	int ic_seq = 0;
	
	//분류체계코드 (0:분류항목 포함, 1:분류항목 제외)
	icm.init(0);
	
	
	//이슈데이터 등록 관련
	IssueMgr isMgr = new IssueMgr();
	IssueBean isBean = new IssueBean();	   	   	
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	
	//get할려는 IC_TYPE값을 num에 세팅한다.
	int num = 0;
	String checked ="";
	String checkedValue="";

		
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


function getTypeCodeDeath2(type, code, name){
	var icPtype = type;
	var icPcode = code;
	var icPName = name;
	$("#tr_icType"+icPtype).css("display","");
	
	var icon = "";
	
	if(type == "5"){
		icon = "&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>";		
		icPName = "부서";
	}else if(type == "3"){
		icon = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>";
		icPName = "소분류";
	}
	
	$("#label_"+icPtype+"1").html(icon+icPName);
	
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
				/* innerSpan += "<span class='comp'><input id='input_radio_01_"+num+"_"+index+"' class='validation_"+num+"' name='icType"+num+"' onclick='comboCheck(this);' value='"+data.list[index].icType+","+data.list[index].icCode+"' type='radio' "+checked+" />"
				+"<label for='input_radio_01_"+num+"_"+index+"'>"+data.list[index].icName+"</label></span>" */
				
				if(num == "51"){
					innerSpan += "<span class='comp'><input id='input_radio_01_"+num+"_"+index+"' class='validation_"+num+"' name='icType"+num+"' onclick='comboCheckManual(this);' value='"+data.list[index].icType+","+data.list[index].icCode+"' type='radio' "+checked+" />"
					+"<label for='input_radio_01_"+num+"_"+index+"'>"+data.list[index].icName+"</label></span>"
				}else{				
					innerSpan += "<span class='comp'><input id='input_radio_01_"+num+"_"+index+"' class='validation_"+num+"' name='icType"+num+"' onclick='comboCheck(this);' value='"+data.list[index].icType+","+data.list[index].icCode+"' type='radio' "+checked+" />"
					+"<label for='input_radio_01_"+num+"_"+index+"'>"+data.list[index].icName+"</label></span>"
				}
				
			});
			$("#td_icType"+icPtype).html(innerSpan);

			if(icPtype == "5"){
				if($("[name=icType51]").length == 1){
					$("[name=icType51]").click();
				}
			}
			
		}
		,error:function(){}
	
	});
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


function comboCheckManual(){
	var type5 = $("[name=icType5]:checked").val().split(",")[1];
	var type51 = $("[name=icType51]:checked").val().split(",")[1];
	
	
	if(type51 != '77'){
		$.ajax({
			type:'POST',
			url:"getJsonData.jsp",
			dataType:'json',
			data:{"type5":type5, "type51":type51, flag: "manual"},
			success:function(data){
				
				var type3 = "input_radio_01_03_0"+data.type3;
				
				$("#label_3").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>대분류");
				
				$("#"+type3).click();
				
				setTimeout(function() {
					if(typeof data.type31 != "undefined"){				
						var type31 = "31,"+data.type31;					
						$("[name=icType31]").each(function(){						
							if(this.value == type31){							
								this.checked = true;
							}
						});				
					}				 
				}, 50);
				
			}
			,error:function(){}
		
		});
	}else{
		$("[name=icType3]").attr("checked",false);
		$("[name=icType31]").attr("checked",false);
	}
	
}


var regChk = false;//이미 등록중인지 확인하기위한 값
function save_issue(mode){
	
	//타입별 값 세팅
	var typeCode = settingTypeCode();
	
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
					if(mode == "update_multi"){
						alert("이슈가 수정 되었습니다.");
						window.close();
					}
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
	var target = "validation_"+id;
	var chk = "";
	
	$("."+target).each(function(index){
		if($(this).is(":checked")){chk="true";}
	});
	
	if(chk.length > 1){return true;
	}else{return false;}	
}

//선택된 코드 값들을 수정하기 위한 값으로 세팅
function settingTypeCode(){
	//라디오 버튼 type값 세팅
	var radio_id = null;
	//선택박스 type값 세팅
	var select_id = null;
	//유효성 검사
	
	radio_id = [1,2,3,9,21,31,5,51];
	select_id = [4,7];
	
	var form = document.fSend;

	form.typeCode.value = '';
	if(radio_id != null){
		for(var i=0; i < radio_id.length; i++){
			$("[name=icType"+radio_id[i]+"]:radio").each(function(){
				if($(this).is(":checked")){
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
		<th><span id="label_1">구분</span></th><!-- 필수 속성인 경우 strong 태그 삽입 -->
		<td>
<%
	num = 1;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	String cssNone = "";   
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
%>
			<span class="comp" id="typeCode1_<%=i%>" >
			<input id="input_radio_01_0<%=num%>_0<%=i%>" class="validation_<%=num%>" name="icType<%=num%>" value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' type="radio" onclick="comboCheckType2(this,'<%=icBean.getIc_type()%>','<%=icBean.getIc_code()%>','<%=icBean.getIc_name()%>');" <%=checked%> />
			<label for="input_radio_01_0<%=num%>_0<%=i%>"><%=icBean.getIc_name()%></label>
			</span>	
	
<%}%>
			<input type='hidden' name='focus_icType<%=num%>' value="" />
		</td>
		</tr>
		
		<tr>
		<th><span id="label_2">정보구분</span></th><!-- 필수 속성인 경우 strong 태그 삽입 -->
		<td>
<%
	num = 2;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	cssNone = "";   
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
%>
			<span class="comp" id="typeCode2_<%=i%>" >
			<input id="input_radio_01_0<%=num%>_0<%=i%>" class="validation_<%=num%>" name="icType<%=num%>" value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' type="radio" onclick="comboCheckType2(this,'<%=icBean.getIc_type()%>','<%=icBean.getIc_code()%>','<%=icBean.getIc_name()%>');" <%=checked%> />
			<label for="input_radio_01_0<%=num%>_0<%=i%>"><%=icBean.getIc_name()%></label>
			</span>	
	
<%}%>
			<input type='hidden' name='focus_icType<%=num%>' value="" />
		</td>
		</tr>
		
		<!--  2death 정보속성 값 -->
		<tr style="display: none;" id="tr_icType<%=num%>">
		<th><span id="label_21"></span><input type='hidden' name='focus_icType21' value="" /></th>
		<td id="td_icType<%=num%>"></td>
		</tr>		
		<!--  2death 정보속성 값 -->
		
		<tr>
		<th><span id="label_5">실국</span></th><!-- 필수 속성인 경우 strong 태그 삽입 -->
		<td>
<%
	num = 5;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	cssNone = "";   
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		int i_ = i*100;
%>
			<span class="comp" id="typeCode5_<%=i_%>" >
			<input id="input_radio_01_0<%=num%>_0<%=i_%>" class="validation_<%=num%>" name="icType<%=num%>" value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' type="radio" onclick="comboCheckType2(this,'<%=icBean.getIc_type()%>','<%=icBean.getIc_code()%>','<%=icBean.getIc_name()%>');" <%=checked%> />
			<label for="input_radio_01_0<%=num%>_0<%=i_%>"><%=icBean.getIc_name()%></label>
			</span>	
	
<%}%>
			<input type='hidden' name='focus_icType<%=num%>' value="" />
		</td>
		</tr>
		
		<!--  2death 정보속성 값 -->
		<tr style="display: none;" id="tr_icType<%=num%>">
		<th><span id="label_51"></span><input type='hidden' name='focus_icType51' value="" /></th>
		<td id="td_icType<%=num%>"></td>
		</tr>		
		<!--  2death 정보속성 값 -->
		
		
		<tr>
		<th><span id="label_3">대분류</span></th><!-- 필수 속성인 경우 strong 태그 삽입 -->
		<td>
<%
	num = 3;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	cssNone = "";   
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		int i_ = i*100;
%>
			<span class="comp" id="typeCode3_<%=i_%>" >
			<input id="input_radio_01_0<%=num%>_0<%=i_%>" class="validation_<%=num%>" name="icType<%=num%>" value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' type="radio" onclick="comboCheckType2(this,'<%=icBean.getIc_type()%>','<%=icBean.getIc_code()%>','<%=icBean.getIc_name()%>');" <%=checked%> />
			<label for="input_radio_01_0<%=num%>_0<%=i_%>"><%=icBean.getIc_name()%></label>
			</span>	
	
<%}%>
			<input type='hidden' name='focus_icType<%=num%>' value="" />
		</td>
		</tr>
		
		<!--  2death 정보속성 값 -->
		<tr style="display: none;" id="tr_icType<%=num%>">
		<th><span id="label_31"></span><input type='hidden' name='focus_icType31' value="" /></th>
		<td id="td_icType<%=num%>"></td>
		</tr>		
		<!--  2death 정보속성 값 -->
		
		<tr>
		<th><span id="label_9">성향</span></th>
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
		
		<%-- <tr>
		<th><span id="label_7">보도자료</span></th>
		<td>
<%
	num = 7;
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
		 --%>
		
<%
	num = 4;
%>		
		<tr>
		<th><span id="label_4">주요이슈</span></th>
		<td>
			<select id="input_select_01_<%=num%>" class="validation_<%=num%>" name="icType<%=num%>" style="min-width:180px">
			<option value='' >선택하세요</option>
<%

	List type4List = icm.getCodeList("4","","DESC");
	HashMap map = new HashMap();
	for (int i = 0; i < type4List.size(); i++) {
		map = (HashMap) type4List.get(i);
		if(!"0".equals(map.get("IC_CODE")+"") ){
		checkedValue = "";
		selected="";
		if("update".equals(mode)){
			checkedValue = icm.getTypeCodeVal(idBean.getArrCodeList(),4); //selectbox 
			if(checkedValue.equals(map.get("IC_TYPE")+","+map.get("IC_CODE"))){
				selected = "selected=selected";
			}else{
				selected = "";
			}
		}
%>  			
			<option value='<%=map.get("IC_TYPE")%>,<%=map.get("IC_CODE")%>' <%=selected%> ><%=map.get("IC_NAME")%></option>
<%}}%>			
			</select><label for="input_select_01_<%=num%>" class="invisible">주요이슈</label>
		</td>
		</tr>
		
<%
	num = 7;
%>		
		<tr>
		<th><span id="label_7">보도자료</span></th>
		<td>
			<select id="input_select_01_<%=num%>" class="validation_<%=num%>" name="icType<%=num%>" style="min-width:180px">
			<option value='' >선택하세요</option>
<%

	List type7List = icm.getCodeList("7","","DESC");
	map = new HashMap();
	for (int i = 0; i < type7List.size(); i++) {
		map = (HashMap) type7List.get(i);
		if(!"0".equals(map.get("IC_CODE")+"") ){
		checkedValue = "";
		selected="";
		if("update".equals(mode)){
			checkedValue = icm.getTypeCodeVal(idBean.getArrCodeList(),7); //selectbox 
			if(checkedValue.equals(map.get("IC_TYPE")+","+map.get("IC_CODE"))){
				selected = "selected=selected";
			}else{
				selected = "";
			}
		}
%>  			
			<option value='<%=map.get("IC_TYPE")%>,<%=map.get("IC_CODE")%>' <%=selected%> >(<%=map.get("IC_REGDATE")%>) - <%=map.get("IC_NAME")%></option>
<%}}%>			
			</select><label for="input_select_01_<%=num%>" class="invisible">주요이슈</label>
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