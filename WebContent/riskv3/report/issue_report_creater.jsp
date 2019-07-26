<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="	risk.issue.IssueDataBean
					,risk.issue.IssueMgr
					,java.util.ArrayList
					,java.util.List
					,risk.util.ParseRequest
					,risk.admin.member.MemberGroupBean
					,risk.admin.member.MemberDao
					,risk.issue.IssueCodeMgr
					,risk.issue.IssueCodeBean
					,risk.search.MetaMgr
					,risk.search.userEnvMgr
                 	,risk.search.userEnvInfo
                 	,risk.util.DateUtil	
                 	,risk.sms.AddressBookDao
                 	,risk.sms.AddressBookGroupBean
                 	,risk.admin.membergroup.membergroupBean              
                 	,java.util.Iterator
                 	,risk.admin.keyword.KeywordMng
                 	,risk.admin.info.*
                 	,risk.admin.site.SiteBean
                 	,risk.admin.site.SiteMng
                 	,risk.admin.keyword.KeywordBean
                 	"%>
<%@page import="risk.admin.site.SitegroupBean"%>
<%
	DateUtil 		du 		= new DateUtil();
	ParseRequest pr = new ParseRequest(request);
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();	
	IssueCodeBean ICBean = null;
	MemberDao mDao = new MemberDao();
	AddressBookDao abDao = new AddressBookDao();
	AddressBookGroupBean abgBean = new AddressBookGroupBean();
	ArrayList allist = new ArrayList();
	
	pr.printParams();
	String sDate = null;		
	String eDate = null;
	String ir_type = null;
	String ir_title = null;
	
	//이슈 코드
	icMgr.init(0);
	
	ir_type = pr.getString("ir_type", "D1");
	boolean listViewChk = false;
	
	if(ir_type.equals("D1"))
	{
		ir_title = "주간 보고서 ("+du.getCurrentDate("yyyy.MM.dd")+")";		
		sDate    = du.addDay(du.getCurrentDate(),-1,"yyyy-MM-dd");
		eDate    = du.addDay(du.getCurrentDate(),0,"yyyy-MM-dd");		
		listViewChk = false;
	
	}else if(ir_type.equals("I")){
		ir_title = "주간 보고서 - 주요 이슈 포함("+du.getCurrentDate("MM/dd")+")";		
		sDate    = du.addDay(du.getCurrentDate(),-1,"yyyy-MM-dd");
		eDate    = du.addDay(du.getCurrentDate(),0,"yyyy-MM-dd");		
		listViewChk = true;
	}
	
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link rel="stylesheet" type="text/css" href="../../css/base.css" />
<style>
iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}
</style>
<link rel="stylesheet" type="text/css" href="<%=SS_URL%>css/design.css">
<script type="text/javascript" src="<%=SS_URL%>dashboard/asset/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="<%=SS_URL%>dashboard/asset/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=SS_URL%>dashboard/asset/js/design.js"></script>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/reportCalendar.js" type="text/javascript"></script>

<script>
	var list_view_chk = '<%=listViewChk%>';
	var tempDateTime = 0;

	function loadList()
	{		
		hideSetDate(checkIrTypeValue());
		ilu.sendRequest();
	}

	function checkIrTypeValue(){
		
		var irType;
		
		$("[name=radioChange]").each(function(){
			if($(this).is(":checked")){
				irType = $(this).val(); 	
			}
		});
		
		$("[name=ir_type]").val(irType);
		
		return irType;
	}

	function java_all_trim(a) {
        for (; a.indexOf(' ') != -1 ;) {
          a = a.replace(' ','');
        }
        return a;
	}

	function AddDate( day ) {

        var newdate = new Date();
		var resultDateTime;

		if(tempDateTime==0)
		{
			tempDateTime = newdate.getTime();
		}  
		
              
		resultDateTime = tempDateTime + ( day * 24 * 60 * 60 * 1000);      
        newdate.setTime(resultDateTime);
		
        last_ndate = newdate.toLocaleString();

        last_date = java_all_trim(last_ndate);
        last_year = last_date.substr(0,4);
        last_month = last_date.substr(5,2);
        last_mon = last_month.replace('월','');

        if(last_mon < 10) {

            last_m = 0+last_mon;
            last_day = last_date.substr(7,2);
            last_da = last_day.replace('일','');

            if(last_da < 10) {
                last_d = 0+last_da;
            }else{
                last_d = last_da;
            }

        }else{
            last_m = last_mon;

            last_day = last_date.substr(8,2);
            last_da = last_day.replace("일","");

            if(last_da < 10) {
                last_d = 0+last_da;
            }else{
                last_d = last_da;
            }

        }

        last_time = last_year + '-' + last_m + '-' + last_d;

        return last_time;
        
        
    }

	function changeType(ir_type)
	{
		var f = document.fSend;
			
		if(ir_type=='D1')
		{
			f.ir_type.value = 'D1';
			f.ir_title.value='주간보고서(<%=du.getCurrentDate("MM/dd")%>)';
			$("#tr_issue_date").css("display", "none");
			
		}else if(ir_type=='I'){
			
			f.ir_type.value = 'I';
			f.ir_title.value='주간 보고서 - 주요 이슈 포함(<%=du.getCurrentDate("MM/dd")%>)';
			
			$("#tr_issue_date").css("display", "");
		}
		issueListload();
	}
	

	//관련 정보 리스트 관련	
	$(document).ready(issueListload);

	function issueListload()
	{
		ajax.post2('aj_ceoDataList.jsp','fSend','ceoDataList','ajax-loader2.gif');	
		ajax.post2('aj_productDataList.jsp','fSend','productDataList','ajax-loader2.gif');
		ajax.post2('aj_issueDataList.jsp','fSend','issueDataList','ajax-loader2.gif');
	}

	//Url 링크
 	var chkPop = 1;
 	
	function hrefPop(url){
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop','');
		chkPop++;
	}

	//체크박스
    function checkAll(chk) {
		if(chk == "ceo"){
			if($("[name=checkall_ceo]").is(":checked")){
				$("[name=ceoCheck]").attr("checked", true);
			}else{
				$("[name=ceoCheck]").attr("checked", false);
			}
		}else if(chk == "pro"){
			if($("[name=checkall_pro]").is(":checked")){
				$("[name=proCheck]").attr("checked", true);
			}else{
				$("[name=proCheck]").attr("checked", false);
			}
		}else{
			if($("[name=checkall_isu]").is(":checked")){
				$("[name=isuCheck]").attr("checked", true);
			}else{
				$("[name=isuCheck]").attr("checked", false);
			}
		}
 	}

 	////////////////////////////////////////AJAX 수신자 설정///////////////////////////////////////
	
	////////////////////수신자 리스트 //////////////////////
	//$(document).ready(pageInit);

	function pageInit()
	{
		loadList1();
		loadList2();
	}
	
	function loadList1()
	{	
		ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_receiver.jsp','report_receiver','receiverList');		
	}
	
	function findList1()
	{
		var f = document.report_receiver;
		var f2 = document.report_selectedReceiver;
		
		f.selectedAbSeq.value = f2.selectedAbSeq.value;		
		ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_receiver.jsp','report_receiver','receiverList');			
	}	
	/////////////////////////////////////////////////////	
	///////////////////선택된 수신자 리스트/////////////////						
	function selectedListCheck(ab_seq)
	{
		var f = document.report_selectedReceiver;
		var check = true;
		var list = new Array();
		list = f.selectedAbSeq.value.split(',');

		for(var i =0; i<list.length; i++)
		{
			if(list[i]==ab_seq)
			{
				check = false;
				break;
			}
		}
		return check;
	}	
	
	function selectRightMove(ab_seq)
	{
		var f = document.report_selectedReceiver;
	
		if(!selectedListCheck(ab_seq)){alert('이미 선택된 수신자 입니다.');	return;}	
		
		if(f.selectedAbSeq.value!='')
		{
			f.selectedAbSeq.value += ","+ ab_seq;
		}else{
			f.selectedAbSeq.value = ab_seq;
		}
		findList1(); 		
		findList2();
	}
	
	function addReceiver() 
	{
	 	window.open('<%=SS_URL%>riskv3/admin/receiver/receiver_detail.jsp?mode=add','adduser', 'width=800,height=500,scrollbars=no');
	}


	function editReceiver(ab_seq) 
	{
	 	window.open('<%=SS_URL%>riskv3/admin/receiver/receiver_detail.jsp?mode=edit&abSeq='+ab_seq,'adduser', 'width=800,height=1000,scrollbars=no');
	}

	function delReceiver(ab_seq) 
	{
		if(window.confirm("삭제하시겠습니까?"))
		{
	 		window.open('<%=SS_URL%>riskv3/admin/receiver/receiver_prc.jsp?mode=del&seqList='+ab_seq,'adduser', 'width=800,height=500,scrollbars=no');
		}else{
			return;
		}
	}

	function delAbSeq(ab_seq)
	{
		var f = document.report_selectedReceiver;
		var list = new Array();
		list = f.selectedAbSeq.value.split(',');

		f.selectedAbSeq.value = '';
		for(var i =0; i<list.length; i++)
		{
			if(list[i]!=ab_seq)
			{				
				if(f.selectedAbSeq.value!='')
				{
					f.selectedAbSeq.value += ","+ list[i];
				}else{
					f.selectedAbSeq.value = list[i];
				} 
			}
		}
	}

	function selectLeftMove(ab_seq)
	{
		var f = document.report_selectedReceiver;
		delAbSeq(ab_seq);
		findList1();		
		findList2();
	}	

	function loadList2()
	{	
		ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_selected_receiver.jsp','report_selectedReceiver','selectedList');		
	}
	
	function findList2()
	{		
		ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_selected_receiver.jsp','report_selectedReceiver','selectedList');					
	}				
	//////////////////////////////////////////////////////

	function chkIssueRisk(no){
		var f = document.fSend;
		var risk1='';
		var obj = eval('document.all.issue_0'+no+'_risk');
		//var obj = document.getElementById('issue_0'+no+'_risk');
		if(obj){
			if(obj.length){
				for( var i = 0; i<obj.length; i++)
				{
					if(obj[i].checked == true )
					{			
						if(risk1==''){
							risk1 = obj[i].value;			
						}else{
							risk1 += ','+obj[i].value;
						}			
					}
				}
			}else{
				risk1 = obj.value;
			}
		}
		return risk1;
	}

	function preview(reType)
	{	
		document.fSend.reportType.value = reType;
		var sdate = $("#issue_sDate").val();
		var edate = $("#issue_eDate").val();
		
		var sDateArray = sdate.split("-"); 
		var sdateObj = new Date(sDateArray[0], Number(sDateArray[1])-1, sDateArray[2]);
		
		var eDateArray = edate.split("-"); 
		var edateObj = new Date(eDateArray[0], Number(eDateArray[1])-1, eDateArray[2]);
		
		var betweenDay =  ((edateObj.getTime()  - sdateObj.getTime())/1000/60/60/24)+1;
		
		if(betweenDay > 14){
			alert("주요이슈 기간은 최대 14일 조회 가능 합니다. 다시 입력해주세요.");
			return;
		}else if(betweenDay < 0){
			alert("주요이슈 기간을 잘 못 입력하셨습니다. 다시 입력해주세요.");
			return;
		}
		
		var f = document.fSend;
		var ir_type = checkIrTypeValue();		
		//checkSetting();
		//setCodeName();
		var id_seqs = "";
		$("[name=ceoCheck]:checked").each(function(){
			if(id_seqs ==""){
				id_seqs = $(this).val();
			}else{
				id_seqs += ","+$(this).val();
			}						
		});
		$("[name=id_seqs_ceo]").val(id_seqs);
		id_seqs = "";
		$("[name=proCheck]:checked").each(function(){
			if(id_seqs ==""){
				id_seqs = $(this).val();
			}else{
				id_seqs += ","+$(this).val();	
			}						
		});
		$("[name=id_seqs_pro]").val(id_seqs);
		id_seqs = "";
		
		$("[name=isuCheck]:checked").each(function(){
			if(id_seqs ==""){
				id_seqs = $(this).val();
			}else{
				id_seqs += ","+$(this).val();	
			}						
		});
		$("[name=id_seqs_isu]").val(id_seqs);
		id_seqs = "";
		window.open('about:blank', 'reportpagefornamo', 'width=860,height=850,scrollbars=yes,status=no,resizable=no');		
		//f.mode.value = 'view';
		f.ir_type.valvalue = ir_type;
		f.action = 'pop_report_editform.jsp';
		f.target = 'reportpagefornamo';
		f.method= 'post';
		f.submit();
	}
	
	function check_re(target){
		if(target =="1"){
			$("#d_radio").attr("checked", true);
			$("#i_radio").attr("checked", false);
		}else{
			$("#d_radio").attr("checked", false);
			$("#i_radio").attr("checked", true);
		}
	}

</script>
</head>
<body>
<form name="fSend" id="fSend" method="post">
<!-- 주간보고서 - 보고서 유형 값 세팅 -->
<input name="ir_type" id="ir_type" value="" type="hidden" />
<input name="id_seqs_ceo" id="id_seqs_ceo" value="" type="hidden" /><!-- 자사/CEO 관련  -->
<input name="id_seqs_pro" id="id_seqs_pro" value="" type="hidden" /><!-- 상품 관련  -->
<input name="id_seqs_isu" id="id_seqs_isu" value="" type="hidden" /><!-- 주요이슈 관련  -->
<input name="reportType" type="hidden">
<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top" style="width:auto">
		<table align="center" style="width:820px;margin-left:20px" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:37px;padding-top:0px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="al"><img src="../../images/report/tit_icon.gif" /><img src="../../images/report/tit_0301.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">보고서관리</td>
								<td class="navi_arrow2">보고서작성</td>
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
				<td class="al"><span class="sub_tit2">보고서 설정</span></td>
			</tr>
			<tr>
				<!-- <td class="search_box"> -->
				<td>
				<%-- <table id="search_box"  style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th width="124">보고서 유형</th>
						<td>
						<table style="color:#2f5065;" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td style="padding-right:10px;"><input type="radio" value="D1" name="radioChange" id="d_radio" onclick="changeType('D1');" <%if(ir_type.equals("D1")){out.print("checked");} %>>일일 보고서</td>
								<td style="padding-right:10px;"><input type="radio" value="I" name="radioChange" id="i_radio" onclick="changeType('I');" <%if(ir_type.equals("I")){out.print("checked");} %>>일일 보고서(주요 이슈 포함)</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr><td style="height:10px;"></td></tr>
					<tr>
						<th>보고서 제목</th>
						<td><input style="width:460px;" class="textbox" type="text" name="ir_title" value="<%=ir_title%>"></td>
					</tr>
					<tr><td style="height:10px;"></td></tr>
					<tr>
						<th>기 간</th>
						<td>
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td><input style="width:90px;" class="textbox" type="text" name="ir_sdate" id="ir_sdate" value="<%=sDate%>"></td>
								<td><img src="../../images/report/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('ir_sdate'))"/></td>
								<td>
								<select name="ir_stime" onchange="issueListload();">
								<%for(int i=0; i<24; i++){ 
									if(i==8){
										out.print("<option value="+i+" selected>"+i+"시</option>");
									}else{
										if(i < 10){
											out.print("<option value=0"+i+">"+i+"시</option>");
										}else{
											out.print("<option value="+i+">"+i+"시</option>");	
										}
								}} %>
								</select>
								</td>
								<td>~</td>
								<td><input style="width:90px;" class="textbox" type="text" name="ir_edate" id="ir_edate" value="<%=eDate%>"></td>
								<td><img src="../../images/report/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('ir_edate'))"/></td>
								<td>
								<select name="ir_etime" onchange="issueListload();">
								<%for(int i=0; i<24; i++){ 
									if(i==8){
										out.print("<option value="+i+" selected>"+i+"시</option>");
									}else{
										if(i < 10){
											out.print("<option value=0"+i+">"+i+"시</option>");
										}else{
											out.print("<option value="+i+">"+i+"시</option>");	
										}
								}} %>
								</select>
								</td>
								<td style="padding-left:10px;">※ 수집시간 기준으로 작성됩니다.</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr><td style="height:10px;"></td></tr>
					
					<tr id="tr_issue_date" style="display: none;">
						<th>주요이슈 기간</th>
						<td>
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td><input style="width:90px;" class="textbox" type="text" name="issue_sDate" id="issue_sDate" value="<%=sDate%>"></td>
								<td><img src="../../images/report/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('issue_sDate'))"/></td>
								<td>~</td>
								<td><input style="width:90px;" class="textbox" type="text" name="issue_eDate" id="issue_eDate" value="<%=eDate%>"></td>
								<td><img src="../../images/report/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('issue_eDate'))"/></td>
								<td style="padding-left:10px;">※ 최대 조회가능 기간은 14일 입니다.</td>
							</tr>
						</table>
						</td>
					</tr>
					
				</table> --%>
				<!-- 검색 -->
		<div class="article">
			<div class=" ui_searchbox_00">
				<div class="c_sort visible">
					<div class="ui_row_00">
						<span class="title wid_100px"><span class="icon">-</span>보고서 유형</span>
						<span class="txts">
							<div class="dcp m_r_10"><input type="radio" value="D1" name="radioChange" id="d_radio" onclick="changeType('D1');" <%if(ir_type.equals("D1")){out.print("checked");}%> ><label for="d_radio"><span class="radio_00"></span>주간 보고서</label></div>
							<div class="dcp"><input type="radio" value="I" name="radioChange" id=i_radio onclick="changeType('I');" <%if(ir_type.equals("I")){out.print("checked");}%> ></span><label for="i_radio"><span class="radio_00"></span>주간 보고서(주요 이슈 포함)</label></div>
						</span>
					</div>
					<div class="ui_row_00">
						<span class="title wid_100px"><span class="icon">-</span>보고서 제목</span>
						<span class="txts">
							<input type="text" id="input_title_00" class="ui_input_02" name="ir_title" value="<%=ir_title%>"><label for="input_title_00" class="invisible">보고서 제목</label>
						</span>
					</div>
					<div class="ui_row_00">
						<span class="title wid_100px"><span class="icon">-</span>보고서 기간</span>
						<span class="txts">
							<input onchange="issueListload();" name="ir_sdate" id="ir_sdate" value="<%=sDate%>" type="text" class="ui_datepicker_input input_date_first" readonly><label for="ir_sdate">날짜입력</label>
							<select name="ir_stime" class="ui_select_04" onchange="issueListload();">
								<%for(int i=0; i<24; i++){ 
									if(i==8){
										out.print("<option value="+i+" selected>"+i+"시</option>");
									}else{
										if(i < 10){
											out.print("<option value=0"+i+">"+i+"시</option>");
										}else{
											out.print("<option value="+i+">"+i+"시</option>");	
										}
								}} %>
								</select>
							~
							<input onchange="issueListload();" name="ir_edate" id="ir_edate" value="<%=eDate%>" type="text" class="ui_datepicker_input input_date_last" readonly><label for="ir_edate">날짜입력</label>
							<select name="ir_etime" class="ui_select_04" onchange="issueListload();">
								<%for(int i=0; i<24; i++){ 
									if(i==8){
										out.print("<option value="+i+" selected>"+i+"시</option>");
									}else{
										if(i < 10){
											out.print("<option value=0"+i+">"+i+"시</option>");
										}else{
											out.print("<option value="+i+">"+i+"시</option>");	
										}
								}} %>
								</select>
							<span class="fs11">※ 수집시간 기준으로 작성됩니다.</span>
						</span>
					</div>
					<div id="issue_date_row" class="ui_row_00 last-child">
						<span class="title wid_100px"><span class="icon">-</span>주요이슈 기간</span>
						<span class="txts">
							<input name="issue_sDate" id="issue_sDate" value="<%=sDate%>" type="text" class="ui_datepicker_input input_date_first" readonly><label for="issue_sDate">날짜입력</label>
							~
							<input name="issue_eDate" id="issue_eDate" value="<%=eDate%>" type="text" class="ui_datepicker_input input_date_last" readonly><label for="issue_eDate">날짜입력</label>
							<span class="fs11">※ 최대 조회가능 기간은 14일 입니다.</span>
						</span>
					</div>
				</div>
				<script type="text/javascript">
					report_change($( "input[name='radioChange']" ).val());		// 최초에 한번 실행(불필요시 삭제 하고 사용)
					$( "input[name='radioChange']" ).change(function($e){
						report_change( this.value );
					});
					function report_change($value){
						if($value == "I") {
							$( "#issue_date_row" ).prev().removeClass( "last-child" );
							$( "#issue_date_row" ).show();
						} else {
							$( "#issue_date_row" ).prev().addClass( "last-child" );
							$( "#issue_date_row" ).hide();
						}
					}
				</script>
			</div>
			<!-- <div class="ui_searchbox_toggle">
				<a href="#" class="btn_toggle active invisible">검색조건 열기/닫기</a>
			</div> -->
		<!-- // 검색 -->
				</td>
			</tr>
			<!-- 검색 끝 -->

			<tr>
				<td class="al" style="height:50px;padding-top:20px;"><span class="sub_tit2">자사/CEO 관련</span></td>
			</tr>
			<tr>
				<td id="ceoDataList"></td>
			</tr>
			<tr>
				<td height="20"></td>
			</tr>
			<tr>
				<td class="al" style="height:50px;padding-top:20px;"><span class="sub_tit2">상품 관련</span></td>
			</tr>
			<tr>
				<td id="productDataList"></td>
			</tr>
			<tr>
				<td height="20"></td>
			</tr>
			<tr>
				<td class="al" style="height:50px;padding-top:20px;"><span class="sub_tit2">온라인 관련 정보</span></td>
			</tr>
			<tr>
				<td id="issueDataList"></td>
			</tr>
			<tr>
				<td style="height:40px;" align="center">
				<img src="../../images/report/btn_report_write.gif" onclick="preview(1);" style="cursor:pointer;"/>
				<img src="../../images/report/btn_report_write2.gif" onclick="preview(2);" style="cursor:pointer;"/>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
<%-- 달력 테이블 --%>
<table width="162" border="0" cellspacing="0" cellpadding="0" id="calendar_conclass" style="position:absolute;display:none;">
	<tr><td><img src="../../images/calendar/menu_bg_004.gif" width="162" height="2"></td></tr>
	<tr><td align="center" background="../../images/calendar/menu_bg_005.gif"><table width="148" border="0" cellspacing="0" cellpadding="0">
			<tr><td height="6"></td></tr>
			<tr><td id="calendar_calclass"></td></tr>
			<tr><td height="5"></td></tr>
		</table></td>
	</tr>
	<tr><td><img src="../../images/calendar/menu_bg_006.gif" width="162" height="2"></td></tr>
</table>
</body>
</html>
