<%@page import="risk.json.JSONObject"%>
<%@page import="risk.json.JSONArray"%>
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
	
	AddressBookGroupBean addGroupBean = new AddressBookGroupBean();	
	IssueDataBean idBean = new IssueDataBean();
	IssueCodeBean icBean = new IssueCodeBean();
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
	int issue_seq = 0;
	
	String k_xp = "";
	String km_TypeCode = "";
	
	String pseqVal = "";
	//모드에 따른 분기
	if(mode.equals("insert")){
		metaBean = metaMgr.getMetaData(md_seq);
		ic_seq = icm.getSiteMapCode(metaBean.getSg_seq());  //사이트 이슈 코드 맵핑 정보 코드 시퀀스로 가져옴
		k_xp = metaMgr.getKeywordXP(md_seq);
		km_TypeCode = metaMgr.getKeyword_Map(md_seq);
		JSONArray sb_seq = metaMgr.getSb_seq_Map(); 
		JSONObject obj = null;
		for(int i=0 ; i<sb_seq.length() ; i++){
			obj = new JSONObject();
			obj = (JSONObject)sb_seq.get(i);
			if(obj.getString("SB_SEQ").equals(metaBean.getSb_seq())){
				km_TypeCode = "3,"+obj.getString("TYPE3")+"@"+"5,"+obj.getString("TYPE5")+"@"+"31,"+obj.getString("TYPE31")+"@"+"51,"+obj.getString("TYPE51");
			}
		}
		
		
	}else if(mode.equals("update")){
		//이슈 정보
		idBean = issueMgr.getIssueDataBean(md_seq);
		//연관 키워드 정보
	    relationKey = issueMgr.getRelationKey(idBean.getId_seq());
	    key_seq =  relationKey.size();
	    
	    pseqVal = issueMgr.getPressIssueData(idBean.getId_seq());
		
	}
	ArrayList arrCodeList = new ArrayList();
	arrCodeList = idBean.getArrCodeList();
	
	//이슈데이터 등록 관련
	IssueMgr isMgr = new IssueMgr();
	IssueBean isBean = new IssueBean();	   	   	
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	
	//수신자 그룹
	arrAddGroupBean = abDao.getAdressBookGroup();
	Iterator it = arrAddGroupBean.iterator();
	
	//get할려는 IC_TYPE값을 num에 세팅한다.
	int num = 0;
	String selectedValue="";
	
	//자동롤링용 연관키워드 한줄로 만들기
	  relationKeyAll = issueMgr.getRelationKey();
	  String streamKey = "";
	  for(int i =0; i < relationKeyAll.size(); i++){
	    if(streamKey.equals("")){
	      streamKey = (String)relationKeyAll.get(i);
	    }else{
	      streamKey += "," + (String)relationKeyAll.get(i);
	    }
	  }
	  
	  ArrayList IssueArrayList = null;
	  
	  String issueKey = "";
	  IssueArrayList = icm.getCodeList("4","","DESC");
	  for(int i =0; i < IssueArrayList.size(); i++){
		  Map map = new HashMap();
	      map = (HashMap)IssueArrayList.get(i);
	      if(issueKey.equals("")){	    	
	    	issueKey = map.get("IC_CODE")+"."+map.get("IC_NAME");
	      }else{
	    	issueKey += "," +map.get("IC_CODE")+"."+map.get("IC_NAME");
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
<script type="text/javascript" src="<%=SS_URL%>js/jquery.multi_selector.js"></script>
<script type="text/javascript" src="<%=SS_URL%>js/design.js"></script>
<script src="<%=SS_URL%>js/spin.js" type="text/javascript"></script>
<script type="text/javascript">
var key_seq = <%=key_seq%>;
var streamKey = '<%=streamKey%>'; 
var issue_seq = <%=issue_seq%>;
var issueKey = '<%=issueKey%>';
var km_TypeCode = '<%=km_TypeCode%>';


//death2인 코드 값들을 불러 온다.
//type : 부모 속성의 타입값 - IC_PTYPE
//code : 부모 속성의 코드값 - IC_PCODE
//name : 부모 속성의 이름 - IC_NAME 
function getTypeCodeDeath2(type, code, name){
	var icPtype = type;
	var icPcode = code;
	var icPName = name;
	
	$("#tr_icType"+icPtype+"1").css("display","");
	
	var icon = "";
	
	if(type == "5"){
		icon = "&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>";		
		icPName = "부서";
	}else if(type == "3"){
		icon = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>";
		icPName = "소분류";
	}
	
	if(type == "5" || type == "3"){
		$("#label_"+icPtype+"1").html(icon+icPName+"<strong>*</strong>");	
	}else{
		$("#label_"+icPtype+"1").html(icon+icPName+"<strong></strong>");
	}
	
	var checked= ""; 
	$.ajax({
		type:'POST',
		url:"getJsonData.jsp",
		dataType:'json',
		data:{ic_Ptype:icPtype, ic_Pcode:icPcode, flag: "2death"},
		success:function(data){
			var innerSpan = "";
			var num = "";
			$.each(data.list, function(index){

				num = data.list[index].icType;
				
				if(data.list.length==1){
					checked = "checked";
					$("[name=focus_icType"+num+"]").val(data.list[index].icType+","+data.list[index].icCode);
				}else{
					checked = "";
				}
				
				if(num == "51"){
					innerSpan += "<span class='comp'><input id='input_radio_01_"+num+"_"+index+"' class='validation_"+num+"' name='icType"+num+"' onclick='comboCheckManual(this);' value='"+data.list[index].icType+","+data.list[index].icCode+"' type='radio' "+checked+" />"
					+"<label for='input_radio_01_"+num+"_"+index+"'>"+data.list[index].icName+"</label></span>"
				}else{
					innerSpan += "<span class='comp'><input id='input_radio_01_"+num+"_"+index+"' class='validation_"+num+"' name='icType"+num+"' onclick='comboCheck(this);' value='"+data.list[index].icType+","+data.list[index].icCode+"' type='radio' "+checked+" />"
					+"<label for='input_radio_01_"+num+"_"+index+"'>"+data.list[index].icName+"</label></span>"	
				}				
				
			});
			innerSpan += "<input type='hidden' name='focus_icType"+num+"' value='' />";
			$("#td_icType"+icPtype+"1").html(innerSpan);
			
			if(icPtype == "5"){
				if($("[name=icType51]").length == 1){
					$("[name=icType51]").click();
				}
			}
			
		}
		,error:function(){}
	
	});
}

function getMapingTypeCodeDeath2(type, code, two_type , two_code , name){
	var icPtype = type;
	var icPcode = code;
	var icPName = name;
	
	$("#tr_icType"+icPtype+"1").css("display","");
	$("#label_"+icPtype+"1").html(icPName+"<strong>*</strong>");
	
	var icon = "";
	
	if(type == "5"){
		icon = "&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>";		
		icPName = "부서";
	}else if(type == "3"){
		icon = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>";
		icPName = "소분류";
	}
	
	if(type == "5" || type == "3"){
		$("#label_"+icPtype+"1").html(icon+icPName+"<strong>*</strong>");	
	}else{
		$("#label_"+icPtype+"1").html(icon+icPName+"<strong></strong>");
	}
	
	var checked= ""; 
	$.ajax({
		type:'POST',
		url:"getJsonData.jsp",
		dataType:'json',
		data:{ic_Ptype:icPtype, ic_Pcode:icPcode, flag: "2death"},
		success:function(data){
			var innerSpan = "";
			var num = "";
			$.each(data.list, function(index){

				num = data.list[index].icType;
				
				if(data.list[index].icCode == two_code){
					checked = "checked";
				}else{
					checked = "";
				}
				
				innerSpan += "<span class='comp'><input id='input_radio_01_"+num+"_"+index+"' class='validation_"+num+"' name='icType"+num+"' onclick='comboCheckManual(this);' value='"+data.list[index].icType+","+data.list[index].icCode+"' type='radio' "+checked+" />"
				+"<label for='input_radio_01_"+num+"_"+index+"'>"+data.list[index].icName+"</label></span>"
				
			});
			innerSpan += "<input type='hidden' name='focus_icType"+num+"' value='' />";
			$("#td_icType"+icPtype+"1").html(innerSpan);
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
	var radio_id = null;
	//선택박스 type값 세팅
	var select_id = null;
	//유효성 검사
	
	radio_id = [1,2,5,51,3,31,9,6,11,21];
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


var regChk = false;//이미 등록중인지 확인하기위한 값
//이슈관리 저장 함수
function save_issue(mode, event){
	
	//유효성 검사
	var check;
	//유효성 검사
	check = [1,5,51,3,31,9,6];
	
	var targetClass = "";
	for(var i = 0; i < check.length; i++){
		if(!validation_check(check[i])){
			var label = $("#label_"+check[i]).text();
			alert(label.trim()+"을(를) 선택하세요.");
			return;
		}
	}
	
	var pseq = $("[name=typeCode10_pseq] option:selected").val();
	if(pseq != ''){
		$("#p_seq").val(pseq);	
	}
	$("#p_seq").val(pseq);
	
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
     
     /* if(!names || names == '' || names.length < 1) {
       alert("연관 키워드를 입력해 주세요.");
       $("#txt_relationKey").focus();
       
       return;
     } */
     
     $("[name=keyNames]").val(names);
     
    }
	
	//타입별 값 세팅
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
					if(mode == "insert"){
						if(data.indexOf('a') > 0){
							alert("유사가 이미 등록 되었습니다. 자동 등록 될 예정입니다.");
							window.close();
						}else{
							alert("이슈가 등록 되었습니다.");
							//$(opener.document).find("#issue_menu_icon"+data.trim()).attr("src", "../../images/search/btn_manage_on.gif");
							//$(opener.document).find("#issue_menu_icon"+data.trim());
							//imgId = "#issue_menu_icon" + data.trim();
							imgId = "#issue_menu_icon" + $("[name=md_seq]").val();
							$(imgId, opener.document).attr("src", "../../images/search/btn_manage_on.gif");
							window.close();
						}	
					}else if(mode == "insert&mail"){
						alert("이슈 등록 및 메일이 발송 되었습니다.");
						window.close();
					}else if(mode == "update"){
						alert("이슈가 수정 되었습니다.");
						window.close();
					}else if(mode == "mail"){
						alert("메일이 발송되었습니다.");
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


//체크해제
function comboCheckType2(obj,icType,icCode,icName){
		var icPtype = icType;
		var f = document.fSend;
		focusObj = eval('f.focus_'+ obj.name);
		
		if(focusObj.value == obj.value){
			obj.checked = false;
			focusObj.value = '';
			
			$("#tr_icType"+icPtype+"1").css("display", "");
			$("[name=icType"+icPtype+"1]").attr("checked", false);
			$("[name=focus_icType"+icPtype+"1]").val('');
			
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

function comboCheckManual(obj){
	var type5 = $("[name=icType5]:checked").val().split(",")[1];
	var type51 = $("[name=icType51]:checked").val().split(",")[1];
	
	var f = document.fSend;
	
	if(type51 != '77'){
	$.ajax({
		type:'POST',
		url:"getJsonData.jsp",
		dataType:'json',
		data:{"type5":type5, "type51":type51, flag: "manual"},
		success:function(data){
			
			if(data != null){
				var type3 = "input_radio_01_03_0"+data.type3;
				
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
				}, 100);
			}else{
				$("[name=icType3]").attr("checked",false);
				$("[name=icType31]").attr("checked",false);
			}
			
			
			if(f.mode.value == 'insert'){
				$("#tr_icType3").css("display","");
				$("#tr_icType31").css("display","");
			}
			
		}
		,error:function(){}
	
	});
	
	}else{
		$("[name=icType3]").attr("checked",false);
		$("[name=icType31]").attr("checked",false);
	}
	
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

function settingPseq(){
	var pseq = $("[name=typeCode10_pseq] option:selected").val();
	if(pseq != ''){
		$("#p_seq").val(pseq);	
	}
}

$(function(){
	
	var f = document.fSend;
	
	if(km_TypeCode != ""){
		var type3 = $("[name=icType3]:checked").val();
		var type31 = $("[name=icType31]:checked").val();
		var type5 = $("[name=icType5]:checked").val();
		var type51 = $("[name=icType51]:checked").val();
		
		if(type3 != "" && type31 != ""){
			getMapingTypeCodeDeath2(type3.split(",")[0], type3.split(",")[1], type31.split(",")[0] , type31.split(",")[1] , "소분류");
		}
		
		if(type5 != "" && type51 != ""){
			getMapingTypeCodeDeath2(type5.split(",")[0], type5.split(",")[1], type51.split(",")[0] , type51.split(",")[1] , "부서");
		}
		
	}else{
		if(f.mode.value == 'insert'){
			$("#tr_icType3").css("display","none");
			$("#tr_icType31").css("display","none");
		}
	}
		
	
	$('#txt_issueKey').autocomplete(issueKey.split(','), {matchContains: true});
	
	$('#txt_relationKey').autocomplete(streamKey.split(','));
	
	$("[name=icType1]").click();
	
	/* $("[name=icType10]").click(function(){
		
		if( $("[name=focus_icType10]").val() == "10,1"){
			$("[name=typeCode10_pseq]").css("display", '');
			var pseq = $("[name=typeCode10_pseq] option:selected").val();
			if(pseq != ''){
				$("#p_seq").val(pseq);	
			}
		}else{
			$("[name=typeCode10_pseq]").css("display", 'none');
			$("#p_seq").val('');
		}
	}); */
	
	/* var mode = $("[name=mode]").val();
	if(mode != "update"){
		var type = [10,12];
		var order = [1,1];
		for(var i=0; i < type.length; i++){
			$("[name=icType"+type[i]+"]").eq(order[i]).prop("checked", true);
			
			$("[name=focus_icType"+type[i]+"]").val( $("[name=icType"+type[i]+"]").eq(order[i]).val() );
		}
	} */
	
});
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
<%if(mode.equals("insert")){ %>
<input name="md_seq" id="md_seq" type="hidden" value="<%=metaBean.getMd_seq()%>"><!-- 기사번호 -->
<input name="s_seq" id="s_seq" type="hidden" value="<%=metaBean.getS_seq()%>"><!-- 사이트번호 -->
<input name="sg_seq" id="sg_seq" type="hidden" value="<%=metaBean.getSg_seq()%>"><!-- 사이트 그룹 -->
<input name="md_date" id="md_date" type="hidden" value="<%=metaBean.getMd_date()%>"><!-- 수십 시간-->
<input name="md_site_menu" id="md_site_menu" type="hidden" value="<%=metaBean.getMd_same_count()%>"><!-- 사이트 메뉴 -->
<input name="md_same_ct" id="md_same_ct" type="hidden" value="<%=metaBean.getMd_same_count()%>"><!-- 유사개수 -->
<input name="typeCode" id="typeCode" type="hidden" value=""><!-- 타입코드 -->
<input name="mailreceiver" id="mailreceiver" type="hidden" value=""><!--선택된 메일 수신자 -->
<input name="selItseq" id="selItseq" type="hidden" value="">
<input name="md_pseq" id="md_pseq" type="hidden" value="<%=metaBean.getMd_pseq()%>">
<input name="k_xp" id="k_xp" type="hidden" value="<%=k_xp%>">
<input name="user_id" type="hidden" value="<%=metaBean.getUser_id() %>" />
<input name="user_nick" type="hidden" value="<%=metaBean.getUser_nick() %>" />

<input name="blog_visit_count" type="hidden" value="<%=metaBean.getBlog_visit_count() %>" />
<input name="cafe_name" type="hidden" value="<%=metaBean.getCafe_name() %>" />
<input name="cafe_member_count" type="hidden" value="<%=metaBean.getCafe_member_count() %>" />

<input name="d_seq" type="hidden" value="<%=metaBean.getD_seq() %>" /><!-- 문서번호 -->

<%}else if(mode.equals("update")){%>
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

<input name="d_seq" type="hidden" value="<%=idBean.getD_seq() %>" /><!-- 문서번호 -->

<%} %>


<%-- <div class="wrap">
    <div class="ori_txts">
        <div class="in_con">
<%
String contents ="";
if ("insert".equals(mode)) {
	contents = metaBean.getMd_content().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
} else if ("update".equals(mode)) {
	contents = idBean.getId_content().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
}
contents = contents.replaceAll("<div", "");
%>
	<%=contents %>
        </div>
    </div> --%>
    
<%if(!"multi".equals(mode)){ %>
<!-- <div class="wrap" style="width: 50%"> -->

	<%
	if( SS_MG_NO.equals("3") ||  SS_MG_NO.equals("9") || SS_MG_NO.equals("4")){
	
	%>
    <div class="ori_txts" style="float:left;width:35%;position: fixed;">
        	<div class="in_con">
			<%
		//	String contents ="";
		//	if ("insert".equals(mode)) {
		//		contents = metaBean.getMd_content().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		//	} else if ("update".equals(mode)) {
		//		contents = idBean.getId_content().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		//	}
		//	contents = contents.replaceAll("<div", "");
			%>
			<%
			if("insert".equals(mode)){
				out.println(metaBean.changeKeyColor("blue"));				 
			}else{
				String contents = "";
				contents = idBean.getId_content().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
				contents = contents.replaceAll("<div", "");
				out.println(contents);
			}
			%>
			</div>
			</div>
			<div class="wrap_pop" style="float:right;width:65%;">
	<%
	}else{%>
			<div class="wrap_pop" >
	<%
	}
	 
}else{%>	<div class="wrap_pop" >
	<%
}
%>    
    
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
		<th><span>수집원명</span></th>
		<td><input id="ui_input_00_04" name="md_menu_name" type="text" style="width:70%" value="<%if(mode.equals("insert")){out.print(metaBean.getMd_site_menu());}else{out.print(idBean.getMd_site_menu());}%>"><label for="ui_input_00_04" class="invisible">수집원명 입력</label></td>
		</tr>
		<tr>
		<th><span>정보분류시간</span></th>
		<td><input id="ui_input_00_03" name="id_regdate" type="text" style="width:70%" value="<%if(mode.equals("insert")){out.print(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));}else{out.print(idBean.getFormatId_regdate("yyyy-MM-dd HH:mm:ss"));}%>" readonly="readonly"><label for="ui_input_00_03" class="invisible">정보분류시간 입력</label></td>
		<th><span>정보종류</span></th>
		<td >
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
		<%
		int[] codeArry = {1,2,21,5,51,3,31,9,6};	//전체 이슈코드
		int[] twoDepthCode = {2,3,5};			//2depth 코드의 부모 코드
		int[] hiddenCode;
		if("insert".equals(mode)){
			hiddenCode = new int[]{21,31,51};			//기본 숨김 설정 코드	
		}else{
			hiddenCode = new int[]{21,3,31,51};			//기본 숨김 설정 코드
		}
		
		
		for(int codeNum = 0; codeNum < codeArry.length; codeNum++){
			num = codeArry[codeNum];
			arrIcBean = new ArrayList();
			arrIcBean = icm.GetType(num);
			icBean = (IssueCodeBean)arrIcBean.get(0);
			String styleDisplay = "";
			String cobocheck = "";
			if("insert".equals(mode)){
				for(int number : hiddenCode){
					if(number == num){
						styleDisplay ="none;";	
						break;
					}else{
						styleDisplay ="";
					}
				}
			}
			
			String star="<strong>*</strong>";
			//필수 체크 해제
			if(num == 2 || num == 21){
				star="";
			}
			
			String title = icBean.getIc_name();
			int indx = 1;
			if(num==21){
				title = "정보구분 상세";
				indx = 0;
			}else if(num==31){
				title = "소분류";
				indx = 0;
			}else if(num==51){
				title = "부서";
				indx = 0;
			}
			
			String icon = "";
			if(num == 3){
				icon = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>";
			}
			
			if("update".equals(mode)){
				if(num == 51){
					icon = "&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>";
				}else if(num == 31){
					icon = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>";
				}
			}
			
			
		%>
		<tr id="tr_icType<%=num%>" style="display: <%=styleDisplay%>" >
		
		<th><span id="label_<%=num%>"><%=icon%><%=title%><%=star%></span></th><!-- 필수 속성인 경우 strong 태그 * 삽입 -->
		<td id="td_icType<%=num%>" style="height:24px;line-height:24px">
		<%
			for (int i = indx; i < arrIcBean.size(); i++) {
				int i_ = i;
				icBean = (IssueCodeBean) arrIcBean.get(i);
				for(int number : twoDepthCode){
					if(number == num){
						cobocheck ="comboCheckType2(this,"+icBean.getIc_type()+","+icBean.getIc_code()+",'"+icBean.getIc_name()+"');";
						break;
					}else{
						if(num==31){
							cobocheck = "comboCheckManual(this)";
						}else{
							cobocheck ="comboCheck(this);";	
						}						
					}
				}
				
				checkedValue = "";
				selected="";
				/**
				*	출처 자동 선택 시작
				**/
				 
				if("insert".equals(mode) && codeArry[codeNum] == 6 || codeArry[codeNum] == 7){
					
					if(codeArry[codeNum] == 6){
						if(metaBean.getMd_type().equals("1")){	//정보류가 언론인 경우 무조건 언론으로 찍히도록
							if(icBean.getIc_code() == 1){
								selected = "checked=checked";
								checkedValue = icBean.getIc_type()+","+icBean.getIc_code();
							}else{
								checked = "";
							}
						}else{
							if(ic_seq > 0){
								selected = icm.getTypeCodeValByKey(arrIcBean , ic_seq);
							}
							if(selected.equals( icBean.getIc_type()+","+icBean.getIc_code() )){
								selected = "checked=checked";
								checkedValue = icBean.getIc_type()+","+icBean.getIc_code();
							}else{
								checked = "";
							}
						}
					}
				}
				/**
				*	출처 자동 선택 끝
				**/
				
				if(num==3 || num==31 || num==5 || num==51){
					i_ = i_*100;
				}
				
				if("update".equals(mode)){
					//System.out.println( icBean.getIc_type()+","+icBean.getIc_code() );
					checkedValue = icm.getTypeCodeVal(idBean.getArrCodeList(),num);
					if(checkedValue.equals(icBean.getIc_type()+","+icBean.getIc_code())){
						selected = "checked=checked";
						selectedValue = icBean.getIc_type()+","+icBean.getIc_code();
						//icPcode = icBean.getIc_code();
					}else{
						selected = "";
					}
				}
				
				if("update".equals(mode) && (num==21 || num==31 || num == 51)){				
					String chk = "";
					if(num==21){
						chk = icm.getTypeCodeVal(idBean.getArrCodeList(), 2);	
					}else if(num==31){
						chk = icm.getTypeCodeVal(idBean.getArrCodeList(), 3);
					}else if(num==51){
						chk = icm.getTypeCodeVal(idBean.getArrCodeList(), 5);
					}
					
					if(chk.equals(icBean.getIc_ptype()+","+icBean.getIc_pcode())){
		%>
					<span class="comp" <%if(num==10){%> style="line-height:24px" <%} %>>
					<input id="input_radio_01_0<%=num%>_0<%=i_%>" class="validation_<%=num%>" name="icType<%=num%>" style="top:7px" value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' type="radio" onclick="<%=cobocheck%>" <%=selected%> />
					<label for="input_radio_01_0<%=num%>_0<%=i_%>"><%=icBean.getIc_name()%></label>
					</span>
		<%
					}
				}else{					
					if(!"".equals(km_TypeCode)){
					if(codeArry[codeNum] == 3 || codeArry[codeNum] == 31 || codeArry[codeNum] == 5 || codeArry[codeNum] == 51){
						String arr_kMap[] = km_TypeCode.split("@");
						String arr_TypeCode[] = null;
						for(int k=0 ; k<arr_kMap.length ; k++){
							arr_TypeCode = arr_kMap[k].split(",");
							if(icBean.getIc_type() == Integer.parseInt(arr_TypeCode[0]) && icBean.getIc_code() == Integer.parseInt(arr_TypeCode[1])){
								selected = "checked=checked";
							}
						}
					}
					}
					
		%>
				<span class="comp" <%if(num==10){%> style="line-height:24px" <%} %>>
				<input id="input_radio_01_0<%=num%>_0<%=i_%>" class="validation_<%=num%>" name="icType<%=num%>" style="top:7px" value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' type="radio" onclick="<%=cobocheck%>" <%=selected%> />
				<label for="input_radio_01_0<%=num%>_0<%=i_%>"><%=icBean.getIc_name()%></label>
				</span>
		<%
				}
			}
		%>
		<%-- <%if(num == 10){ 
		String none = "none";
		if("update".equals(mode) && "10,1".equals(selectedValue)){
			none = "";
		}
		%>
				<select name="typeCode10_pseq" style="display: <%=none%>;margin-left: 10px;height: 24px;line-height:normal" onchange="settingPseq();" >
				<option value="" selected="selected" >선택하세요</option>
				<%
					Map pMap = null;
					for(int p=0; p < pressList.size(); p++){
						pMap = new HashMap();
						pMap = (HashMap)pressList.get(p);
						String slted = "";
						if(pseqVal.equals(pMap.get("P_SEQ").toString())){
							slted = "selected";
						}
						%>
						<option value="<%=pMap.get("P_SEQ")%>" <%=slted%>> <%=pMap.get("P_TITLE")%> </option>
						<%
					}
				%>
				</select>
				<%} %> --%>
			<input type='hidden' name='focus_icType<%=num%>' value="<%=selectedValue%>" />
		</td>
		</tr>
		<%}%>
		<tr>
		<th><span id="label_4">주요이슈</span></th>
		<td>
<%
	num = 4;
	//arrIcBean = new ArrayList();
	//arrIcBean = icm.GetType(num);
%>		
			<select id="input_select_01_<%=num%>" class="validation_<%=num%>" name="icType<%=num%>" style="min-width:180px">
				<option value="">선택하세요</option>
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
<tr>
		<th><span id="label_7">보도자료</span></th>
		<td>
<%
	num = 7;
	//arrIcBean = new ArrayList();
	//arrIcBean = icm.GetType(num);
%>		
			<select id="input_select_01_<%=num%>" class="validation_<%=num%>" name="icType<%=num%>" style="min-width:180px">
				<option value="">선택하세요</option>
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
			</select><label for="input_select_01_<%=num%>" class="invisible">보도자료</label>
		</td>
		</tr>		
		
		<!-- <tr>
			<th><span>주요이슈</span></th>
			<td>
				<input name="txt_issueKey" id="txt_issueKey" type="text" class="h24" style="width:200px" /><label for="txt_issueKey" class="invisible">주요이슈</label>
			</td>
		</tr> -->
		
		<!-- 연관 키워드 -->
		<tr id="tr_relationKey" style="display:<%if(mode.equals("update_multi")){out.print("none");}%>">
			<th><span id="label_keyword">연관키워드</span></th>
			<td>
				<input id="txt_relationKey" type="text" class="h24" style="width:200px" onkeypress="javascript:if(event.keyCode == 13){addKeyword();}"><label for="txt_relationKey" class="invisible">연관 키워드 입력</label>
				<button id="btn_key_add" type="button" class="btn_add" onclick="addKeyword();"></button>
				<ul class="keyword_list" id="tb_keywordList">
				<%
                  if(mode.equals("update")) {
                    for(int i =0; i < relationKey.size(); i++) {
                      out.println("<li id='td_keyword_" + (i + 1) +"'><div class='item'><span>" + (String)relationKey.get(i) + "</span><button type='button' onclick=\"delKeyword('"+ (i+1) +"');\" class='btn_del' title='키워드 삭제'>삭제</button></div></li>");
                    }
                  }
                %></ul>
			
			<!-- 	<input style="width:150px;" type="text" class="textbox2" name="txt_relationKey" id="txt_relationKey" value="" onkeypress="javascript:if(event.keyCode == 13){addKeyword();}">
				<img src="/images/search/plus_btn.gif" onclick="addKeyword();" style="vertical-align: bottom;cursor: pointer;">
				<div id="tb_keywordList">	
				
                </div> -->
			</td>
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