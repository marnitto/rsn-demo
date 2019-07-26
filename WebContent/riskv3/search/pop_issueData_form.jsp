<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/sessioncheck.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
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
<%@ page import="risk.issue.IssueDataBean
				,risk.admin.member.MemberBean
                ,risk.admin.member.MemberDao
                ,risk.sms.AddressBookDao
               	,risk.sms.AddressBookGroupBean
               	,risk.admin.membergroup.membergroupBean"%>
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
	
	hotkeywordMgr hm = new hotkeywordMgr();
	hotkeywordBean hb = new hotkeywordBean();
	
	IssueDataBean idBean = new IssueDataBean();
	IssueCodeBean icBean = new IssueCodeBean();
	MetaBean metaBean = new MetaBean();			

	//연관키워드 관련 
	ArrayList relationKey = null;
	ArrayList relationKeyAll = null;
	int key_seq = 0;
	
	String selected = null;
	String mode = pr.getString("mode");
	String nowPage = pr.getString("nowPage");
	String subMode = pr.getString("subMode");
	String md_seq = pr.getString("md_seq");
	int ic_seq = 0;
	
	//분류체계코드 (0:분류항목 포함, 1:분류항목 제외)
	icm.init(0);
	
	//SMS대그룹  HOT ISSUE중그룹에 속하는 키워드
	ArrayList keywordInfo = new ArrayList();
	
	String k_xp = "";
	
	//모드에 따른 분기
	if(mode.equals("insert")){
		//메타 정보
		if(subMode.equals("solr")){
			metaBean.setMd_seq(pr.getString("md_seq"));
			metaBean.setS_seq(pr.getString("s_seq"));
			metaBean.setSg_seq("0");
			metaBean.setMd_type(pr.getString("md_type","0"));
			metaBean.setMd_same_count(pr.getString("md_same_ct","0"));
			metaBean.setMd_site_name(pr.getString("md_site_name"));
			metaBean.setMd_site_menu("SOLR");
			metaBean.setMd_date(du.getDate(pr.getString("md_date"), "yyyy-MM-dd HH:mm:ss"));
			metaBean.setMd_pseq(pr.getString("md_seq"));
			metaBean.setMd_title(su.dbString(pr.getString("md_title")));
			metaBean.setMd_url(su.dbString(pr.getString("md_url")));
			metaBean.setMd_content(pr.getString("md_content"));			

		 }else{
			 
			metaBean = metaMgr.getMetaData(md_seq);
			ic_seq = icm.getSiteMapCode(metaBean.getSg_seq());  //사이트 이슈 코드 맵핑 정보 코드 시퀀스로 가져옴
			keywordInfo = metaMgr.getKeywordInfo(md_seq);
			k_xp = metaMgr.getKeywordXP(md_seq);
		}
	}else if(mode.equals("update")){
		//이슈 정보
		idBean = issueMgr.getIssueDataBean(md_seq);
		//연관 키워드 정보
	    //relationKey = issueMgr.getRelationKey(idBean.getId_seq());
	    //key_seq =  relationKey.size();
		
	}
	
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
	
	//이슈데이터 등록 관련
	IssueMgr isMgr = new IssueMgr();
	IssueBean isBean = new IssueBean();	   	   	
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	
	
	//get할려는 IC_TYPE값을 num에 세팅한다.
	int num = 0;
	String checked ="";
	String checkedValue="";
	
	//시스템 멤버 그룹
	AddressBookGroupBean abgBean = new AddressBookGroupBean();
	List abgGroupList = new ArrayList();
	abgGroupList = abDao.getAdressBookGroup();
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>이슈등록</title>
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
<link rel="stylesheet" type="text/css" href="<%=SS_URL%>css/design.css">
<link href="<%=SS_URL%>css/jquery.autocomplete.css" rel="stylesheet"type="text/css" />
<script src="<%=SS_URL%>js/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/common.js"  type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/jquery.autocomplete.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=SS_URL%>dashboard/asset/js/jquery.multi_selector.js"></script>
<script type="text/javascript" src="<%=SS_URL%>dashboard/asset/js/design.js"></script>
<script src="<%=SS_URL%>js/spin.js" type="text/javascript"></script>
<script type="text/javascript">
var key_seq = <%=key_seq%>;
var streamKey = '<%=streamKey%>';

//death2인 코드 값들을 불러 온다.
//type : 부모 속성의 타입값 - IC_PTYPE
//code : 부모 속성의 코드값 - IC_PCODE
//name : 부모 속성의 이름 - IC_NAME 
function getTypeCodeDeath2(type, code, name){
	var icPtype = type;
	var icPcode = code;
	var icPName = name;
	$("#tr_icType"+icPtype).css("display","");
	$("#label_"+icPtype+"1").html(icPName+"<strong>*</strong>");
	
	var icType1 = "";
	$("[name=icType1]").each(function(){
		if($(this).is(":checked")){
			if($(this).val() == "1,2"){//소보운영파트
				icType1 = "S";
			}else{//홍보운영파트
				icType1 = "H";
			}
		}
	});
	 
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
				if(icType1 == "H"){
					if(data.list.length==1){
						checked = "checked";
						$("[name=focus_icType"+num+"]").val(data.list[index].icType+","+data.list[index].icCode);
					}else{
						checked = "";
					}	
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
	var flagVal = $("[name=icType1]:checked").val();
	var radio_id;
	//유효성 검사
	radio_id = [2,3,6,9,10,31];
	
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
	
	//var flagVal = $("[name=icType1]:checked").val();
	var check;
	//유효성 검사
	check = [2,3,6,9,31];
	
	var targetClass = "";
	for(var i = 0; i < check.length; i++){
		if(!validation_check(check[i])){
			var label = $("#label_"+check[i]).text();
			alert(label+"을(를) 선택하세요.");
			return;
		}
	}
	
	  // 연관키워드
    if(document.getElementById("tr_relationKey").style.display != 'none'){
     var obj_td = null;
     var names = '';
     var html = '';
     var nameIdx = -1;
     
     //for(var i = 1; i <= key_seq; i++){
     //  
     //  obj_td = document.getElementById('td_keyword_' + i);
     //  if(obj_td){
     //
     //    html = obj_td.innerHTML;
     //    nameIdx = html.indexOf('&',0); 
     //    html = html.substring(0,nameIdx);
     //    
     //    if(names == '') {
     //      names = html.trim();
     //    } else {
     //      names += ',' + html.trim();
     //    }
     //  }
     //}
     
     $("#tb_keywordList").find("li").each(function(){
    	 html = $(this).find("div").find("span").text();
    	if(names == '') {
    		names =	html.trim();
    	}else{
    		names += ',' + html.trim();
    	}
     });
     
     if(!names || names == '' || names.length < 1) {
       alert("연관 키워드를 입력해 주세요.");
       $("#txt_relationKey").focus();
       
       return;
     }
     $("[name=keyNames]").val(names);
   }
	
	//메일 발송일 경우 수신자 메일 체크해서 mailreceiver에 ,로 구분해서 저장
	if(mode == "insert&mail" ){
		var abSeq = "";
		$("#ui_multi_selector_01 > li").not(".disable").each(function(){
			if(abSeq == ""){
				abSeq += $(this).attr("user_data_value");	
			}else{
				abSeq += ","+$(this).attr("user_data_value");
			}
		});
		$("[name=mailreceiver]").val(abSeq);
	}
	
	//타입별 값 세팅
	var typeCode = settingTypeCode();
	//mode에 따라 form submit
	var f = document.fSend;
	if(!regChk){
		regChk = true;
		f.typeCode.value = typeCode;
		if(f.subMode.value=='solr'){
			f.mode.value='solrsave';
		}else{
			f.mode.value = mode;
		}
		/*
		f.target='if_samelist';
		f.action='issue_data_prc.jsp';
        f.submit(); */
        var formData = $("[name=fSend]").serialize();
        var imgId = ""; 
		$.ajax({
			type : "POST"
			,url: "issue_data_prc.jsp"
			,timeout: 30000
			,data : formData
			,dataType : 'text'
			,async: true
			,success : function(data){
				$("#load_spin").hide();
						if(data != ""){
							if(data == "already"){
								alert("이미등록된 정보 입니다.\n새로고침으로 확인 하시기 바랍니다.");
								window.close();
							}else{
								if(mode == "insert"){
									alert("이슈가 등록 되었습니다.");	
								}else{
									alert("이슈 등록 및 메일이 발송 되었습니다.");
								}
								imgId = "#issue_menu_icon" + data.trim();
								$(imgId, opener.document).attr("src", "../../images/search/btn_manage_on.gif");
								window.close();
							}
						}
					  }
			,beforeSend : function(){
				$("#load_spin").show();
			}			
			});	
	}else{
		alert('등록중입니다.. 잠시만 기다려주세요.');
	}
}

//체크해제
function comboCheckType2(obj,icType,icCode,icName){

		var f = document.fSend;
		$("#tr_icType12").css("display", "none");
		focusObj = eval('f.focus_'+ obj.name);
		
		if(focusObj.value == obj.value){
			obj.checked = false;
			focusObj.value = '';
			if("icType3" == obj.name){
				
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

//메일 수신자 목록
function getMailReceiverList(){
	var param = $("#frm").serialize();
	$.post("../issue/aj_getMailReceiverList.jsp"
			,param
			,function(data){
				$("#ui_multi_selector_00").html(data);
				selectDataMove_Append();
				selectDataMove_Remove();
			});
}

//연관키워드 추가
function addKeyword() {
    var f = document.fSend;
    var keyword = f.txt_relationKey.value.trim();

    if(keyword != ''){
      key_seq++;
      
      //var AddHtml = keyword + "&nbsp;<img src=\"/images/search/delete_btn_01.gif\" style=\"vertical-align: middle\" onclick=\"delKeyword('"+ key_seq +"');\">&nbsp;";
      var AddHtml = "<li id='td_keyword_" + key_seq +"'><div class='item'><span>" + keyword + "</span><button type='button' onclick=\"delKeyword('"+ key_seq +"');\" class='btn_del' title='키워드 삭제'>삭제</button></div></li>"
      //var table = document.getElementById('tb_keywordList'); 
      //var row = table.rows[0];
      //var col = row.insertCell(-1);
      //
      //col.id = 'td_keyword_'+ key_seq;
      //col.innerHTML = AddHtml;  
      $("#tb_keywordList").append(AddHtml);
      
    }

    f.txt_relationKey.value = '';
  }
//연관키워드 삭제
  function delKeyword(idx){
	
	  $("#td_keyword_"+idx).remove();

    //var obj = document.getElementById('td_keyword_' + idx);
    //
    //if(obj){
    //  var table = document.getElementById('tb_keywordList');
    //  table.rows[0].deleteCell(obj.cellIndex);
    //}

  }

$(function(){
	$('#txt_relationKey').autocomplete(streamKey.split(','));
	//getMailReceiverList();	//메일 수신자 전체 목록 가져오기
	
	var target = document.getElementById('load_spin');
	var spinner = new Spinner().spin(target);
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
<input name="mailreceiver" id="mailreceiver" type="hidden" value="" />
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
<%} %>

<div class="wrap">
    <div class="ori_txts">
        <div class="in_con">
<%
if ("insert".equals(mode)) {
%>
    <%=metaBean.getMd_content() %>
<%
} else if ("update".equals(mode)) {
%>  
    <%=idBean.getId_content() %>
<%
}
%>
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
		<th><span id="label_2">회사<strong>*</strong></span></th><!-- 필수 속성인 경우 strong 태그 삽입 -->
		<td>
		<input type='radio' name='icType1' value="1,1" checked="checked" style="display: none;"/>
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
		<th><span id="label_9">성향<strong>*</strong></span></th>
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
		
		<tr>
		<th><span id="label_6">출처<strong>*</strong></span></th>
		<td>
<%
	num = 6;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	selected = "";
	if(ic_seq > 0){
		selected = icm.getTypeCodeValByKey(arrIcBean , ic_seq);
	}
	/*
	*	정보종류가 언론인 경우 출처를 언론으로 자동으로 찍히게 함 
	*/
	if(metaBean.getMd_type().equals("99999")){
		
		for (int i = 1; i < arrIcBean.size(); i++) {
			icBean = (IssueCodeBean) arrIcBean.get(i);
			if(icBean.getIc_code() == 1){
				checked = "checked";
				checkedValue = icBean.getIc_type()+","+icBean.getIc_code();
			}else{
				checked = "";
			}
%>
		<span class="comp">
			<input id="input_radio_01_0<%=num%>_0<%=i%>" class="validation_<%=num%>" name="icType<%=num%>" value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' onclick="comboCheck(this);" type="radio" <%=checked%>>
			<label for="input_radio_01_0<%=num%>_0<%=i%>"><%=icBean.getIc_name()%></label>
		</span>
<%		}
		/*
		*	정보종류가 개인인 경우 출처를 확인해서 자동으로 찍히게 함 
		*/	
	}else{
		for (int i = 1; i < arrIcBean.size(); i++) {
			icBean = (IssueCodeBean) arrIcBean.get(i);
			//if(metaBean.getMd_type().equals("2")){
				if(selected.equals( icBean.getIc_type()+","+icBean.getIc_code() )){
					checked = "checked";
					checkedValue = icBean.getIc_type()+","+icBean.getIc_code();
				}else{
					checked = "";
				}	
			//}
%>  
			<span class="comp">
			<input id="input_radio_01_0<%=num%>_0<%=i%>" class="validation_<%=num%>" name="icType<%=num%>" value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' onclick="comboCheck(this);" type="radio" <%=checked%>>
			<label for="input_radio_01_0<%=num%>_0<%=i%>"><%=icBean.getIc_name()%></label>
			</span>
<%}}%>
			<input type='hidden' name='focus_icType<%=num%>' value="<%=checkedValue%>" />
		</td>
		</tr>
		
		<tr class="soboNone">
		<th><span id="label_3">주제그룹<strong>*</strong></span></th>
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
		<!--  2death 주제그룹 값 -->
		<tr style="display: none;" id="tr_icType<%=num%>">
		<th><span id="label_31"><strong>*</strong></span><input type='hidden' name='focus_icType31' value="" /></th>
		<td id="td_icType<%=num%>"></td>
		</tr>		
		<!--  2death 주제그룹 값 -->
		
		
		
		<tr class="soboNone">
		<th><span id="label_10">보도자료</span></th>
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
			<input type='hidden' name='focus_icType<%=num%>' value=">" />			
		</td>
		</tr>
		<tr class="soboNone">
		<th><span id="label_11">주요이슈</span></th>
		<td>
<%
	num = 11;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
%>		
			<select id="input_select_01_<%=num%>" class="validation_<%=num%>" name="icType<%=num%>" style="min-width:180px">
			<option value='' >선택하세요</option>
<%
	System.out.println( "주요이슈 사이즈 : "+arrIcBean.size() );
	for (int i = 0; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(icBean.getIc_code() != 0){
%>  			
			<option value='<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>' ><%=icBean.getIc_name()%></option>
<%}}%>			
			</select><label for="input_select_01_<%=num%>" class="invisible">주요이슈</label>
		</td>
		</tr>
		
		<!-- 연관 키워드 -->
		<tr id="tr_relationKey">
			<th><span id="label_keyword">연관키워드<strong>*</strong></span></th>
			<td>
				
				<input id="txt_relationKey" type="text" class="h24" style="width:200px" onkeypress="javascript:if(event.keyCode == 13){addKeyword();}"><label for="txt_relationKey" class="invisible">연관 키워드 입력</label>
				<button id="btn_key_add" type="button" class="btn_add" onclick="addKeyword();"></button>
				<ul class="keyword_list" id="tb_keywordList"></ul>
			</td>
		</tr>
		<!-- 연관 키워드 END -->
		
        
		
		<tr class="soboOn" style="display:none;">
			<th><span id="label_12">메일발송</span></th>
			<td>
				<table class="ui_multiselector_box_00" summary="메일 수신자/대상자 설정">
			<caption>메일 수신자/대상자 설정</caption>
			<colgroup>
				<col>
				<col style="width:60px">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<td>
						<div class="ui_box_00">
							<div class="floatbox grouping_box">
								<div class="floatbox_left"><h5><span class="icon">-</span>그룹선택</h5></div>
								<div class="floatbox_right">
									<select id="input_select_grouping_00" class="ui_select_03" name="groupId">
										<option value="all">전체</option>
<%
		for(int i =0; i < abgGroupList.size(); i++){
		abgBean = new AddressBookGroupBean();
		abgBean = (AddressBookGroupBean)abgGroupList.get(i);
%>
										<option value="<%=abgBean.getAg_seq()%>"><%=abgBean.getAg_name()%></option>
<%
	}
%>										
									</select><label for="input_select_grouping_00" class="invisible">라벨</label>
								</div>
							</div>
							<div class="m_s_box">
								<ul id="ui_multi_selector_00" class="ui_select_multi_00" style="height:100px">
									<!-- 메일 수신자 그룹 -->
								</ul>
							</div>
							<div class="infos"><span class="icon">-</span>Ctrl, Shift + 마우스 클릭으로 복수 선택이 가능합니다.</div>
						</div>
					</td>
					<td>
						<a href="javascript:selectDataMove_Append()" class="btn_left">추가</a>
						<a href="javascript:selectDataMove_Remove()" class="btn_right">삭제</a>
					</td>
					<td>
						<div class="ui_box_00">
							<div class="floatbox grouping_box">
								<div class="floatbox_left"><h5><span class="icon">-</span>그룹선택</h5></div>
								<div class="floatbox_right">
									<select id="input_select_grouping_01" class="ui_select_03">
										<option value="all">전체</option>
<%
for(int i =0; i < abgGroupList.size(); i++){
	abgBean = new AddressBookGroupBean();
	abgBean = (AddressBookGroupBean)abgGroupList.get(i);
%>
										<option value="<%=abgBean.getAg_seq()%>"><%=abgBean.getAg_name()%></option>
<%
	}
%>
									</select><label for="input_select_grouping_01" class="invisible">라벨</label>
								</div>
							</div>
							<div class="m_s_box">
								<ul id="ui_multi_selector_01" class="ui_select_multi_00" style="height:100px">
								<!-- 메일 대상자 그룹 -->
								</ul>
							</div>
							<div class="infos"><span class="icon">-</span>Ctrl, Shift + 마우스 클릭으로 복수 선택이 가능합니다.</div>
						</div>
					</td>
				</tr>
			</tbody>
			</table>
			<script type="text/javascript">
				if( $( ".ui_select_multi_00" ).length > 0 ) $( ".ui_select_multi_00" ).multi_selector({});
				$( "#input_select_grouping_00" ).change(function(){
					$( "#ui_multi_selector_00" ).data( "multi_selector" ).sortGroup( "data-role-value", this.value );	// Group으로 정렬할 경우에 사용 - ( 커스텀 속성명, 속성값 )2가지 파라미터 전달
				});
				$( "#input_select_grouping_01" ).change(function(){
					$( "#ui_multi_selector_01" ).data( "multi_selector" ).sortGroup( "data-role-value", this.value );	// Group으로 정렬할 경우에 사용 - ( 커스텀 속성명, 속성값 )2가지 파라미터 전달
				});

				function selectDataMove_Append(){
					var datas = $( "#ui_multi_selector_00" ).data( "multi_selector" ).getSelectedItems();
					$( "#ui_multi_selector_00" ).data( "multi_selector" ).removeItems();
					$( "#ui_multi_selector_01" ).data( "multi_selector" ).appendItems(datas);
				}
				function selectDataMove_Remove(){
					var datas = $( "#ui_multi_selector_01" ).data( "multi_selector" ).getSelectedItems();
					$( "#ui_multi_selector_01" ).data( "multi_selector" ).removeItems();
					$( "#ui_multi_selector_00" ).data( "multi_selector" ).appendItems(datas);
					$( "#ui_multi_selector_00" ).data( "multi_selector" ).sortTxts();
					$( "#ui_multi_selector_00 input[type=checkbox]" ).prop( "checked", false );
				}
			</script>
			</td>
		</tr>
		</tbody>
		</table>
	</div>
	<div class="botBtns">
		<a href="javascript:save_issue('insert&mail');" style="display:none;" class="soboOn" ><img src="/images/search/btn_savemail.gif" alt="저장&메일발송"></a>
		<a href="javascript:save_issue('<%=mode%>');"><img src="/images/admin/member/btn_save2.gif" alt="저장"></a>
		<a href="javascript:window.close();"><img src="/images/admin/member/btn_cancel.gif" alt="취소"></a>
	</div>
</div>
</div>
<div id="load_spin" class="ui_loader_00" style="display:none;">로딩중...</div>
</form>
</body>
</html>