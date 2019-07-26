<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="risk.issue.IssueDataBean
                ,risk.issue.IssueMgr
                ,java.util.*
                ,risk.util.ParseRequest
                ,risk.issue.IssueCodeMgr
                ,risk.issue.IssueCodeBean
                ,risk.issue.IssueBean                
                ,risk.search.MetaMgr
                ,risk.search.userEnvMgr
                ,risk.search.userEnvInfo
                ,risk.util.StringUtil
                ,risk.util.DateUtil
                ,risk.util.PageIndex
                ,risk.admin.info.*
                ,java.net.URLDecoder" %>
<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	IssueMgr issueMgr = new IssueMgr();
	MetaMgr smgr = new MetaMgr();
	ArrayList arrIcBean = null;
	ArrayList arrIdBean = null;
	ArrayList arrIdcBean =  null;
	
	ArrayList arrSiteGroup = null;
	
	int chk_cnt = 0;
	int nowpage = 1;	
	int pageCnt = 20;
	int totalCnt = 0;
	int totalPage = 0;	
	int sameCnt = 0;
	
	String searchKey = URLDecoder.decode(pr.getString("encodingSearchKey",""),"UTF-8");
	
	String i_seq = pr.getString("i_seq","");
	String it_seq = pr.getString("it_seq","");
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	String id_mobile = pr.getString("id_mobile");
	String searchType = pr.getString("searchType", "1");
	
	
	String ir_stime = pr.getString("ir_stime","8");
	String ir_etime = pr.getString("ir_etime","18");
	
	String pseqVal = pr.getString("pseqVal", "");
	
	
	String typeCode = pr.getString("typeCode");
	String m_seq = pr.getString("m_seq",SS_M_NO);
	String issue_m_seq = pr.getString("issue_m_seq","");
	
	String check_no = pr.getString("check_no","");
	String srtMsg = null;
	String language = pr.getString("language");
	String parents = pr.getString("parents");
	
	//매체관리 
    String tiers = pr.getString("tiers", "");
    String temp[] = null;
    if(!tiers.equals("")){
    	temp = tiers.split(","); 
    }
    String tier1 = "";
    String tier2 = "";
    String tier3 = "";
    String tier4 = "";
    String tier5 = "";
    //if(temp.length > 0 && temp != null){
    if(temp != null){    	
    	for(int i =0; i < temp.length; i++){
        	if(temp[i].equals("1")){
        		tier1 = "checked=checked";
        	}else if(temp[i].equals("2")){
        		tier2 = "checked=checked";
        	}else if(temp[i].equals("3")){
        		tier3 = "checked=checked";
        	}else if(temp[i].equals("4")){
        		tier4 = "checked=checked";
        	}else if(temp[i].equals("5")){
        		tier5 = "checked=checked";
        	}
        }
    }
	//IssueCodeMgr 인스턴스 생성
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	IssueCodeBean icBean = null;	
	icMgr.init(0);		
	
	//검색날짜 설정 : 기본 7일간 검색한다.
	String sCurrDate    = du.getCurrentDate();
	if (sDateTo.equals("")) sDateTo = du.getCurrentDate();
	if (sDateFrom.equals("")) {
		du.addDay(-1);
		sDateFrom = du.getDate();
	}	
	
	//체크 박스 유지
	String[] check_cnt = null;
	String tmp = "";
	if(!check_no.equals("")){
		check_cnt 	= check_no.split(",");
		for(int i=0; i<check_cnt.length; i++){
			if(check_cnt[i].length()>1){
				chk_cnt++;
				if(tmp.equals("")){
					tmp = check_cnt[i];
				}else{
					tmp += ","+check_cnt[i];
				}
			}
		}
	}
	
	//관련정보 리스트
	IssueDataBean idBean = null;
	//if( request.getParameter("nowPage") != null ) nowPage = Integer.parseInt(request.getParameter("nowPage"));
	nowpage = pr.getInt("nowpage",1);
	arrIdBean = issueMgr.getIssueDataList_groupBy(nowpage,pageCnt,"",i_seq,it_seq,"",searchKey,"2",sDateFrom,ir_stime+":00:00",sDateTo,ir_etime+":59:59",typeCode,"","Y", language,"","","", id_mobile, searchType, pseqVal,tiers, issue_m_seq);	
	totalCnt =  issueMgr.getTotalIssueDataCnt();
	//sameCnt = issueMgr.getTotalSameDataCnt();
	totalPage = totalCnt / pageCnt;
	if(totalCnt % pageCnt > 0){
		totalPage += 1;
	}
	
	
	
	//출처별 카운터 수 
	ArrayList arrSourceCount = new ArrayList();
	arrSourceCount = issueMgr.getSourceCnt();
	String strCnt = "";
	String arrCnt [] = new String[6];
	int totalDataCnt = 0;
	if(arrSourceCount.size() > 0){
		for(int i =0; i < arrSourceCount.size(); i++ ){
			arrCnt = (String[])arrSourceCount.get(i);
			strCnt += arrCnt[0]+": <strong>"+arrCnt[1]+"</strong> 건";
			totalDataCnt += Integer.parseInt(arrCnt[1]);
			if(i != arrSourceCount.size()-1){
				strCnt +=" / ";
			}
		}
	}
	
	
	
	
	srtMsg = " <b> 총 건수 : "+su.addComma(totalCnt+"")+" 건 (유사포함 : "+su.addComma(totalDataCnt+"")+" 건)  </b> "+nowpage+"/"+totalPage+" pages";
	
	//이슈 리스트를 불러온다.
	ArrayList arrIssueBean = new ArrayList();
	IssueBean isBean = new IssueBean();
	arrIssueBean = issueMgr.getIssueList(0,0, "", "", "", "","Y");
	
	//페이징 처리
	totalPage = 0;	
	if (totalCnt>0) {
		totalPage = totalCnt / pageCnt;
		if ((totalCnt % pageCnt) > 0 ) {
			totalPage++;
		}
	}
	
	// 사이트 그룹 정렬관련
	arrSiteGroup = issueMgr.getSiteGroup_order();
	String SiteGroups = "";
	for(int i =0; i < arrSiteGroup.size(); i++){
		if(SiteGroups.equals("")){
			SiteGroups = "[" + ((String[])arrSiteGroup.get(i))[1] + "]"; 
		}else{
			SiteGroups += " " + "[" + ((String[])arrSiteGroup.get(i))[1] + "]";
		}
	}
	
   	//정보그룹 관련
   	ArrayList igArr = new ArrayList();
   	InfoGroupMgr igMgr = new InfoGroupMgr();
   	InfoGroupBean igBean = new InfoGroupBean();
   	igArr = igMgr.getInfoGroup("Y");
   	
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link rel="stylesheet" type="text/css" href="../../css/base.css" />
<style type="text/css">
iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}
</style>
<link rel="stylesheet" type="text/css" href="<%=SS_URL%>css/design.css">
<script type="text/javascript" src="<%=SS_URL%>js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="<%=SS_URL%>js/jquery-ui-1.9.2.custom.js"></script>
<script type="text/javascript" src="<%=SS_URL%>js/design.js"></script>
<%-- <script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script> --%>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/Calendar.js" type="text/javascript"></script>
<%-- <script src="<%=SS_URL%>js/Export2Excel.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/FileSaver.min.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/tableexport-2.1.min.js" type="text/javascript"></script> --%>
<script type="text/JavaScript">
	$(document).ready(init);

	//검색 텍스트 활성화
	function init(){
		document.fSend.searchKey.focus();
	}

	//Url 링크
 	var chkPop = 1;
	function hrefPop(url){
		//window.open(url,'hrefPop'+chkPop,'');
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop','');
		chkPop++;
	}

	//페이징
	function pageClick( paramUrl ) {	
       	var f = document.fSend; 
       	
        f.action = 'issue_data_list.jsp' + paramUrl;
        f.target='';
        f.submit();	        
	}

    // 전체 선택 및 해제
    function checkAll(chk) {
    	
    	if($("[name=checkall]").is(":checked")){
    		$("[name=check]").attr("checked", true);
    	}else{
    		$("[name=check]").attr("checked", false);
    	}
 	}

    // 각 메뉴의 마우스 오버시 이벤트
	function mnu_over(obj) {
		obj.style.backgroundColor = "F3F3F3";
	}

	// 각 메뉴의 마우스 아웃시 이벤트
	// 현재 선택된 메뉴면
	function mnu_out(obj) {
		obj.style.backgroundColor = "#FFFFFF";
	}
	
	function getIssue(obj) {
	
		var mt_no2="";
 		
		if(obj.checked==true){
			if(document.fSend.check_no.value==""){
				document.fSend.check_no.value = obj.value;
			}else{
				document.fSend.check_no.value += ","+obj.value;
			}
		}else{
			mt_no2 = document.fSend.check_no.value;
			if(mt_no2.length<10){
				document.fSend.check_no.value =	mt_no2.replace(obj.value,"");
			}else{
				document.fSend.check_no.value =	mt_no2.replace(","+obj.value,"");	
			}
		}
	}

 	//타입코드 셋팅
	function settingTypeCode()
	{
		var form = document.fSend;

		form.typeCode.value = '';

		for(var i=0;i<form.typeCode1.length;i++){
			if(form.typeCode1[i].selected)
			{
				if(form.typeCode1[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode1[i].value : '@'+form.typeCode1[i].value ;
				}
			}	
		}
		for(var  i=0;i<form.typeCode2.length;i++){
			if(form.typeCode2[i].selected)
			{
				if(form.typeCode2[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode2[i].value : '@'+form.typeCode2[i].value ;
				}
			}	
		}
		for(var i=0;i<form.typeCode21.length;i++){
			if(form.typeCode21[i].selected)
			{
				if(form.typeCode21[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode21[i].value : '@'+form.typeCode21[i].value ;
				}
			}	
		}
		for(var i=0;i<form.typeCode3.length;i++){
			if(form.typeCode3[i].selected)
			{
				if(form.typeCode3[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode3[i].value : '@'+form.typeCode3[i].value ;
				}
			}	
		}
		for(var i=0;i<form.typeCode31.length;i++){
			if(form.typeCode31[i].selected)
			{
				if(form.typeCode31[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode31[i].value : '@'+form.typeCode31[i].value ;
				}
			}	
		}
		 for(var i=0;i<form.typeCode4.length;i++){
			if(form.typeCode4[i].selected)
			{
				if(form.typeCode4[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode4[i].value : '@'+form.typeCode4[i].value ;
				}
			}	
		}
		
		 for(var  i=0;i<form.typeCode5.length;i++){
				if(form.typeCode5[i].selected)
				{
					if(form.typeCode5[i].value!=''){
						form.typeCode.value += form.typeCode.value=='' ? form.typeCode5[i].value : '@'+form.typeCode5[i].value ;
					}
				}	
			}
			for(var i=0;i<form.typeCode51.length;i++){
				if(form.typeCode51[i].selected)
				{
					if(form.typeCode51[i].value!=''){
						form.typeCode.value += form.typeCode.value=='' ? form.typeCode51[i].value : '@'+form.typeCode51[i].value ;
					}
				}	
			}
		 /*
		for(var i=0;i<form.typeCode5.length;i++){
			if(form.typeCode5[i].selected)
			{
				if(form.typeCode5[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode5[i].value : '@'+form.typeCode5[i].value ;
				}
			}	
		} */
		for(var i=0;i<form.typeCode6.length;i++){
			if(form.typeCode6[i].selected)
			{
				if(form.typeCode6[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode6[i].value : '@'+form.typeCode6[i].value ;
				}
			}	
		}
		
		for(var i=0;i<form.tier.length;i++){
			if(form.tier[i].checked)
			{
				form.tiers.value += form.tiers.value=='' ? form.tier[i].value : ','+form.tier[i].value ;
			}	
		}
		
		 for(var i=0;i<form.typeCode7.length;i++){
			if(form.typeCode7[i].selected)
			{
				if(form.typeCode7[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode7[i].value : '@'+form.typeCode7[i].value ;
				}
			}	
		}
		 /*
		//추가분
		for(var i=0;i<form.typeCode8.length;i++){
			if(form.typeCode8[i].selected)
			{
				if(form.typeCode8[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode8[i].value : '@'+form.typeCode8[i].value ;
				}
			}	
		} */
		for(var i=0;i<form.typeCode9.length;i++){
			if(form.typeCode9[i].selected)
			{
				if(form.typeCode9[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode9[i].value : '@'+form.typeCode9[i].value ;
				}
			}	
		}
	/* 	for(var i=0;i<form.typeCode10.length;i++){
			if(form.typeCode10[i].selected)
			{
				if(form.typeCode10[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode10[i].value : '@'+form.typeCode10[i].value ;
				}
			}	
		}
		for(var i=0;i<form.typeCode11.length;i++){
			if(form.typeCode11[i].selected)
			{
				if(form.typeCode11[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode11[i].value : '@'+form.typeCode11[i].value ;
				}
			}	
		}
		for(var i=0;i<form.typeCode12.length;i++){
			if(form.typeCode12[i].selected)
			{
				if(form.typeCode12[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode12[i].value : '@'+form.typeCode12[i].value ;
				}
			}	
		}
		for(var i=0;i<form.typeCode14.length;i++){
			if(form.typeCode14[i].selected)
			{
				if(form.typeCode14[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode14[i].value : '@'+form.typeCode14[i].value ;
				}
			}	
		} 
		for(var i=0;i<form.typeCode31.length;i++){
			if(form.typeCode31[i].selected)
			{
				if(form.typeCode31[i].value!=''){
					form.typeCode.value += form.typeCode.value=='' ? form.typeCode31[i].value : '@'+form.typeCode31[i].value ;
				}
			}	
		} */
		
		
		form.issue_m_seq.value = $("#issueWriter option:selected").val(); 
	}

	//셀렉트 박스 이슈로 이슈 타이틀 변경
    function changeIssueTitle()
	{    	
		ajax.post('selectbox_issue_title.jsp','fSend','td_it_title');
	}	
 	
 	//검색을 기본검색에서 상세검색으로 보여준다.
 	function openDetail() {
		var f = document.fSend;
        var detail = document.getElementById("Detail");
        var open = document.getElementById("open");
        detail.style.display = '';
        open.style.display = 'none';             
    }

	//검색을 상세검색에서 기본검색으로 보여준다.
    function closeDetail() {
		var f = document.fSend;
        var detail = document.getElementById("Detail");
        var open = document.getElementById("open");	
        var close = document.getElementById("close");
        detail.style.display = 'none';        
        open.style.display = '';  
    }

    //검색
 	function search(page){ 		
 		var f = document.fSend;		
		//타입코드 셋팅
		settingTypeCode();
		f.encodingSearchKey.value = encodeURIComponent(f.searchKey.value);
		
		if(page != null && page !=''){
			f.nowpage.value = page;
		}else{
			f.nowpage.value = '1';
		}
 		f.action = 'issue_data_list.jsp';
 		f.target = '';
 		f.submit();
 	}    
    
      
    function deleteIssueData(){
 		var chkedseq = '';
 		$("[name=check]").each(function(){
			if(this.checked){
				if(this.value != ''){
					if(chkedseq == ''){
						chkedseq = this.value;
					}else{
						chkedseq += ','+this.value;
					}
				}
			}
		});
 		
		var f = document.fSend;
		if(chkedseq == '') {
			alert('선택된 정보가 없습니다.'); return;
		}else{
			//if(confirm(cnt+'건의 이슈를 삭제합니다.')) {
			if(confirm('이슈를 삭제합니다.')) {
				//f.mode.value = 'delete';
				//f.encodingSearchKey.value = encodeURIComponent(f.searchKey.value);				
				//f.check_no.value = chkedseq;
				//f.action="issue_data_prc.jsp";
				//f.target='processFrm';
				//f.submit();
				
				$("[name=mode]").val('delete');
				$("[name=check_no]").val(chkedseq);
				
				$.ajax({
		 			type: 'POST',
		 			url: 'issue_data_prc.jsp',
		 			dataType: 'text',
		 			data: $("#fSend").serialize(),
		 			success:function(data){
		 				
		 				alert("삭제 되었습니다.");
	 					$("[name=mode]").val('');
	 					$("[name=check_no]").val('');
	 					$("#btn_search").click();
	 					//$("#fSend").submit();
		 				
		 				//var result = data.trim();
		 				//if(result == "success"){
		 				//	alert("삭제 되었습니다.");
		 				//	$("[name=mode]").val('');
		 				//	$("[name=check_no]").val('');
		 				//	$("#fSend").submit();
		 				//}else{
		 				//	alert("삭제에 실패했습니다. 다시 시도해주세요. 문제가 계속 될 경우 담당자에게 연락주시기 바랍니다.");
		 				//	//$("#fSend").submit();
		 				//}
		 			}
		 			,error:function(){
		 				alert("삭제에 실패했습니다. 다시 시도해주세요. 문제가 계속 될 경우 담당자에게 연락주시기 바랍니다.");
		 			}
		 		});
				
			}
		}
 	}
 	
 	function goMailTo() {
 		
 		var o = $("[name=check]");
 		var chkedseq='';
 		var cnt = <%=chk_cnt%>;
 		var check_no = "";
 		check_no = '<%=check_no%>';
 		
 		for(i=0; i<o.length; i++) {
 			if(o[i].checked == true){
	 			if(o[i].value!=""){
	 				if(chkedseq == ''){
	 					chkedseq = o[i].value;
	 				}else{
	 					chkedseq = chkedseq+','+o[i].value;
	 				}
	 				cnt++;
	 			}
 			}
 		}
 		var f = document.fSend;
				if(chkedseq==""){
					chkedseq = check_no;
				}else{
					if(check_no!=""){
						chkedseq += ","+check_no;
					}
				}
		if(chkedseq == '') {
			alert('선택된 정보가 없습니다.'); return;
		}
		else {
			if(confirm(cnt+'건의 이슈를 발송합니다.')) {
				f.mode.value = 'mail';
				f.check_no.value = chkedseq;
				f.ir_type.value = 'E';
				window.open("about:blank",'goMailTo', 'width=850,height=700,scrollbars=yes');
	       		f.action = 'pop_urgent_mail.jsp';
	       		//f.action = 'pop_emergency_report_form.jsp';
	       		f.target='goMailTo';
				f.submit();
			}
		}
 	}
 	
 	function goSmsTo(idseq){
 		var f = document.fSend;
 		f.mode.value = 'sms';
		f.check_no.value = idseq;
		f.ir_type.value = 'E';
		window.open("about:blank",'goSmsTo', 'width=850,height=700,scrollbars=yes');
   		f.action = 'pop_urgent_sms.jsp';
   		f.target='goSmsTo';
		f.submit();
 	}
 	
 	function goExcelTo(kind){
 		var o=document.getElementsByName('tt');
 		var chkedseq='';
 		var cnt = 0;
 		
 		for(i=0; i<o.length; i++) {
 			if(o[i].checked == true){
	 			if(o[i].value!=""){
	 				if(chkedseq == ''){
	 					chkedseq = o[i].value;
	 				}else{
	 					chkedseq = chkedseq+','+o[i].value;
	 				}
	 				cnt++;
	 			}
 			}
 		}
 		var f = document.fSend;
 		
		f.check_no.value = chkedseq;
		f.encodingSearchKey.value = encodeURIComponent(f.searchKey.value);
		
		f.action = 'issue_data_excel.jsp?kind='+kind;
		f.target = 'processFrm';
		f.submit();
		f.action = 'issue_list.jsp';
		f.target = 'processFrm';
 	}
 	
 	function excelForPOI(){
 		
 		var o=document.getElementsByName('tt');
 		var chkedseq='';
 		var cnt = 0;
 		
 		for(i=0; i<o.length; i++) {
 			if(o[i].checked == true){
	 			if(o[i].value!=""){
	 				if(chkedseq == ''){
	 					chkedseq = o[i].value;
	 				}else{
	 					chkedseq = chkedseq+','+o[i].value;
	 				}
	 				cnt++;
	 			}
 			}
 		}
 		var f = document.fSend;
 		
		f.check_no.value = chkedseq;
		f.encodingSearchKey.value = encodeURIComponent(f.searchKey.value);
		
		//f.action = '/ExcelView';
		f.action = 'getExcelData.jsp';
		f.target = 'processFrm';
		f.submit();
		//f.action = 'issue_list.jsp';
		//f.target = 'processFrm';
 		
 		//$.ajax({
 		//	type:'POST',
 		//	url:"getExcelData.jsp",
 		//	dataType:'text',
 		//	data:$("#fSend").serialize(),
 		//	success:function(data){
 		//		console.log(data);
 		//		$("#excelView").append(data);
 		//		
 		//		$("#exportTable").tableExport({type:'csv',escape:'false'});
 		//	}
 		//	,error:function(){
 		//		alert("다시 선택해주세요");
 		//	}
 		//});
 		
 	}

 	//관심정보 변경 폼
 	function PopIssueDataForm(md_seq, child){
 		var f = document.fSend; 
 		f.md_seq.value = md_seq;
 		f.encodingSearchKey.value = encodeURIComponent(f.searchKey.value);
 		f.mode.value = 'update';
 		f.child.value = child;
 		
 		//popup.openByPost('fSend','pop_issueData_form_checkBox.jsp',1380,860,false,true,false,'send_issue');
 		popup.openByPost('fSend','new_pop_issueData_form.jsp',1380,860,false,true,false,'send_issue');
 	
 	}

 	var pre_SameList = '';
 	var pre_ic_seq = '';
 	function getSourceData(md_pseq, ic_seq, ic_name){

 		var f = document.fSend; 
 	 	
 		var sameLayer = document.getElementById('SameList_' + md_pseq);

 		if(pre_SameList == sameLayer && pre_ic_seq == ic_seq && pre_SameList.style.display==''){
     		pre_SameList.style.display = 'none';
     		return;
     	}

 		if(pre_SameList){
     		pre_SameList.style.display = 'none';
     	}

 		

 		pre_SameList = sameLayer;
 		pre_ic_seq = ic_seq;
     	sameLayer.innerHTML = '로딩중...';
     	if_samelist.location.href = "inc_same_list.jsp?md_pseq=" + md_pseq 
     	                                           + "&ic_seq=" + ic_seq 
     	                                           + "&ic_name=" + encodeURI(ic_name) 
     	                                           + "&sDateFrom=" + f.sDateFrom.value
     	                                           + "&sDateTo=" + f.sDateTo.value
     	                                           + "&ir_stime=" + f.ir_stime.value
     	                                           + "&ir_etime=" + f.ir_etime.value;
     	sameLayer.style.display = '';
 	}
 	function fillSameList(no){
     	var ly = document.getElementById('SameList_'+no);    	
 	    ly.innerHTML = if_samelist.zzFilter.innerHTML;
     }
 	
 	function getOption(now, target){
 		
 		var icType1 = "";
 		var code = $("[name="+now+"] option:selected").val();
 		
 		if(code == ""){
 			return;
 		}
 		
 		var temp = code.split(",");
 		
 		var option = "<option value=''>선택하세요</option>";
 		$.ajax({
 			type:'POST',
 			url:"getJsonData.jsp",
 			dataType:'json',
 			data:{ic_Ptype:temp[0], ic_Pcode:temp[1], flag: "2death", icType1:icType1},
 			success:function(data){
 				$.each(data.list, function(index){
 					option += "<option value='"+data.list[index].icType+","+data.list[index].icCode+"'>"+data.list[index].icName+"</option>";
 				});
 				$("[name="+target+"]").empty().append(option);
 			}
 			,error:function(){
 				alert("다시 선택해주세요");
 			}
 		});
 	}
 	
 	function getOptionType2(){
 		
 		var valueis = $("[name=typeCode1] option:selected").val();
 		
 		if(valueis == ""){
 			return;
 		}
 		
		var option = "<option value=''>선택하세요</option>";
		$.ajax({
 			type:'POST',
 			url:"getJsonData.jsp",
 			dataType:'json',
 			data:{flag: "0death", icType1:icType1},
 			success:function(data){
 				$.each(data.list, function(index){
 					option += "<option value='"+data.list[index].icType+","+data.list[index].icCode+"'>"+data.list[index].icName+"</option>";
 				});
 				
 				$("[name=typeCode2]").empty().append(option);
 			}
 			,error:function(){
 				alert("다시 선택해주세요");
 			}
 		});
 	}
 	
 	//멀티 수정
 	function muti_updateIssueData(mode){
 		var idSeqs = "";
 		$("[name=check]").each(function(){
 			if( $(this).prop("checked") ){
 				if(idSeqs == ""){
 					idSeqs = $(this).val();
 				}else{
 					idSeqs += ","+$(this).val();	
 				}
 			}
 		});
 		
 		document.fSend.mode.value = mode;
 		$("[name=issue_m_seq]").val(idSeqs);
 		if(document.fSend.issue_m_seq.value==''){alert('정보를 선택해주세요.'); return;}
 		popup.openByPost('fSend','pop_multi_issueData_form_.jsp',650,450,false,true,false,'send_issue');
 	}
 	
 	var chkOriginal = 1;
	function portalSearch(s_seq, md_title){
		if(s_seq == '3555'){
			//네이버까페
			//url = "http://section.cafe.naver.com/CombinationSearch.nhn?query=" + md_title;
			url = "https://section.cafe.naver.com/cafe-home/search/articles?query=\""+md_title+"\"";		
			//window.open(url,'hrefPop'+chkOriginal,'');
			window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
		}else if(s_seq == '4943'){
			//다음까페
			url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + md_title;
			//window.open(url,'hrefPop'+chkOriginal,'');
			window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
		}

		chkOriginal ++;
	}
	
	function checkTiers(){
		var legnth = 0;
		$("[name=tier]").each(function(){
			if($(this).attr("checked")){
				legnth += 1;
			}
			
		});	
	
		if(legnth == 4 ){
			$("[name=tierAll]").attr("checked", true);
		}else{
			$("[name=tierAll]").attr("checked", false);
		}
	}
	
</script>
</head>
<body>
<form name="fSend" id="fSend" action="" method="post">
<input type="hidden" name="mode" value="">
<input type="hidden" name="encodingSearchKey" value="<%=searchKey%>">
<input type="hidden" name="nowpage" value="<%=nowpage %>">
<input type="hidden" name="md_seq" value="">
<input type="hidden" name="typeCode" value="<%=typeCode %>">
<input type="hidden" name="id_seq" value="">
<input type="hidden" name="ir_type" value="">
<input type="hidden" name="check_no" value="<%=check_no%>">
<input type="hidden" name="selItseq" value="<%=it_seq%>">
<input type="hidden" name="child" >
<input type="hidden" name="issue_m_seq" value="">
<input name="tiers" type="hidden">
<table style="height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top" style="width:auto">
		<table align="center" style="width:820px;margin-left:20px" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:37px;padding-top:0px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="al"><img src="../../images/issue/tit_icon.gif" /><img src="../../images/issue/tit_0201.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">이슈관리</td>
								<td class="navi_arrow2">관련정보</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 타이틀 끝 -->
			<!-- 검색 시작 -->
			<tr>
				<td>
				<!-- 검색 -->
			<div class="article">
				<div class=" ui_searchbox_00">
					<div class="c_wrap">
						<div class="ui_row_00">
							<span class="title"><!-- <span class="icon">-</span>검색단어</span> -->
							<select name="searchType">
								<option value="1" <%if(searchType.equals("1"))out.print("selected");%>>제목</option>
								<option value="2" <%if(searchType.equals("2"))out.print("selected");%>>제목+내용</option></select>
							</span>
							<span class="txts">
								<input name="searchKey" value="<%=searchKey%>" onkeydown="Javascript:if(event.keyCode == 13){search();}" id="input_search_char" type="text" class="ui_input_02" style="width:160px"><label for="input_search_char" class="invisible">검색어 입력</label>
							</span>
							<span class="title m_l_20"><span class="icon">-</span>검색기간</span>
							<span class="txts">
								<input id="sDateFrom" name="sDateFrom" value="<%=sDateFrom%>" type="text" class="ui_datepicker_input input_date_first" style="width:70px" readonly><label for="sDateFrom">날짜입력</label
								><select id="ir_stime" name="ir_stime" class="ui_select_04" style="margin-right:0">
								<% 
								String sBasics  = ir_stime.equals("") ? "8" :  ir_stime;
			                	String eBasics  = ir_etime.equals("") ? "18" :  ir_etime;
								for(int i=0; i< 24; i++){
								
								%>
								<option value="<%=i%>" <%if(sBasics.equals(i+"")){out.print("selected");} %>><%=i%>시</option>
								<%} %>
								</select><label for="ir_stime"></label
								> ~ <input id="sDateTo" name="sDateTo" value="<%=sDateTo%>" type="text" class="ui_datepicker_input input_date_last" style="width:70px" readonly><label for="sDateTo">날짜입력</label
								><select id="ir_etime" name="ir_etime" class="ui_select_04">
								<% 
								for(int i=0; i< 24; i++){
								
								%>
								<option value="<%=i%>" <%if(eBasics.equals(i+"")){out.print("selected");} %>><%=i%>시</option>
								<% 
								}	
								%>
								</select><label for="ir_etime"></label
								><button class="ui_btn_02" id="btn_search" onclick="search();return false;"><span class="icon search_01">-</span>검색</button>
							</span>
						</div>
					</div>
					<div class="c_sort">
						<div class="ui_row_00">
							<%
								String selected = "";
								String codeTypeName = "";
								arrIcBean = icMgr.GetType(1);
								codeTypeName = icMgr.GetCodeName(arrIcBean,0);
							%>						
							<span class="title wid_70px"><span class="icon">-</span><%=codeTypeName%></span>
							<span class="txts wid_120px">
								<select id="typeCode1" name="typeCode1" class="ui_select_04">
									<option value="">선택하세요</option>
							<%
								for(int i = 1; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
							%>
									<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=selected%>><%=icBean.getIc_name()%></option>
							<%}%>								
								</select><label for="typeCode1" class="invisible"><%=codeTypeName%> 선택</label>
							</span>
						<!-- </div>
						<div class="ui_row_00"> -->
						<%
							selected = "";
							codeTypeName = "";
							arrIcBean = icMgr.GetType(2);
							codeTypeName = icMgr.GetCodeName(arrIcBean, 0);
						%>
							<span class="title wid_70px"><span class="icon">-</span><%=codeTypeName%></span>
							<span class="txts">
								<select id="typeCode2" name="typeCode2" class="ui_select_04" onchange="getOption('typeCode2','typeCode21')">
								<option value="">선택하세요</option>
								<%
								for(int i = 1; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
								%>
									
									<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=selected%>><%=icBean.getIc_name()%></option>
								<%}%>
								</select><label for="typeCode2" class="invisible"><%=codeTypeName%>1 선택</label>
								<%
									selected = "";
									codeTypeName = "";
									arrIcBean = icMgr.GetType(21);
									codeTypeName = icMgr.GetCodeName(arrIcBean, 0);
								%>
								<select id="typeCode21" name="typeCode21" class="ui_select_04">
								<option value="">선택하세요</option>
								<%
								for(int i = 0; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
								%>
									
									<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=selected%>><%=icBean.getIc_name()%></option>
								<%}%>
								</select><label for="typeCode21" class="invisible"><%=codeTypeName%>2 선택</label>
							</span>
						</div>
						<div class="ui_row_00">
						<%-- 	<%
									selected = "";
									codeTypeName = "";
									arrIcBean = icMgr.GetType(3);
									codeTypeName = icMgr.GetCodeName(arrIcBean, 0);
						%>
							<span class="title wid_70px"><span class="icon">-</span><%=codeTypeName%></span>
							<span class="txts wid_120px">
								<select id="typeCode3" name="typeCode3" class="ui_select_04">
								<option value="">선택하세요</option>
								<%
								for(int i = 1; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
								%>
									
									<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=selected%>><%=icBean.getIc_name()%></option>
								<%}%>
								</select><label for="typeCode3" class="invisible"><%=codeTypeName%> 선택</label>
							</span> --%>
						<%
									selected = "";
									codeTypeName = "";
									arrIcBean = icMgr.GetType(9);
									codeTypeName = icMgr.GetCodeName(arrIcBean, 0);
						%>
							<span class="title wid_70px"><span class="icon">-</span><%=codeTypeName%></span>
							<span class="txts wid_120px">
								<select id="typeCode9" name="typeCode9" class="ui_select_04">
								<option value="">선택하세요</option>
								<%
								for(int i = 0; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(!"0".equals(icBean.getIc_code()+"") ){
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
								%>
									
									<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=selected%>><%=icBean.getIc_name()%></option>
								<%}}%>
								</select><label for="typeCode9" class="invisible"> 선택</label>
							</span>
							<%
									selected = "";
									codeTypeName = "";
									arrIcBean = icMgr.GetType(6);
									codeTypeName = icMgr.GetCodeName(arrIcBean, 0);
						%>
							<span class="title wid_70px"><span class="icon">-</span><%=codeTypeName%></span>
							<span class="txts wid_120px">
								<select id="typeCode6" name="typeCode6" class="ui_select_04">
								<option value="">선택하세요</option>
								<%
								for(int i = 0; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(!"0".equals(icBean.getIc_code()+"") ){
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
								%>
									
									<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=selected%>><%=icBean.getIc_name()%></option>
								<%}}%>
								</select><label for="typeCode6" class="invisible"> 선택</label>
							</span>
							
							
					</div>
					<div class="ui_row_00">
					
					
							<%
							selected = "";
							codeTypeName = "";
							arrIcBean = icMgr.GetType(3);
							codeTypeName = icMgr.GetCodeName(arrIcBean, 0);
						%>
							<span class="title wid_70px"><span class="icon">-</span><%=codeTypeName%></span>
							<span class="txts">
								<select id="typeCode3" name="typeCode3" class="ui_select_04" onchange="getOption('typeCode3','typeCode31')">
								<option value="">선택하세요</option>
								<%
								for(int i = 1; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
								%>
									
									<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=selected%>><%=icBean.getIc_name()%></option>
								<%}%>
								</select><label for="typeCode3" class="invisible"><%=codeTypeName%>1 선택</label>
								<%
									selected = "";
									codeTypeName = "";
									arrIcBean = icMgr.GetType(31);
									codeTypeName = icMgr.GetCodeName(arrIcBean, 0);
								%>
								<select id="typeCode31" name="typeCode31" class="ui_select_04">
								<option value="">선택하세요</option>
								<%
								for(int i = 0; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
								%>
									
									<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=selected%>><%=icBean.getIc_name()%></option>
								<%}%>
								</select><label for="typeCode31" class="invisible"><%=codeTypeName%>2 선택</label>
							</span>
					</div>
					<div class="ui_row_00">
						
							
							<%
							selected = "";
							codeTypeName = "";
							arrIcBean = icMgr.GetType(5);
							codeTypeName = icMgr.GetCodeName(arrIcBean, 0);
						%>
							<span class="title wid_70px"><span class="icon">-</span><%=codeTypeName%></span>
							<span class="txts">
								<select id="typeCode5" name="typeCode5" class="ui_select_04" onchange="getOption('typeCode5','typeCode51')">
								<option value="">선택하세요</option>
								<%
								for(int i = 1; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
								%>
									
									<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=selected%>><%=icBean.getIc_name()%></option>
								<%}%>
								</select><label for="typeCode5" class="invisible"><%=codeTypeName%>1 선택</label>
								<%
									selected = "";
									codeTypeName = "";
									arrIcBean = icMgr.GetType(51);
									codeTypeName = icMgr.GetCodeName(arrIcBean, 0);
								%>
								<select id="typeCode51" name="typeCode51" class="ui_select_04">
								<option value="">선택하세요</option>
								<%
								for(int i = 0; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
								%>
									
									<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=selected%>><%=icBean.getIc_name()%></option>
								<%}%>
								</select><label for="typeCode51" class="invisible"><%=codeTypeName%>2 선택</label>
							</span>
							
					
					</div>
					<div class="ui_row_00">
								<%
									selected = "";
									codeTypeName = "";
									arrIcBean = icMgr.GetType(4);
									codeTypeName = icMgr.GetCodeName(arrIcBean, 0);
						%>
							<span class="title wid_70px"><span class="icon">-</span><%=codeTypeName%></span>
							<span class="txts wid_120px">
								<select id="typeCode4" name="typeCode4" class="ui_select_04">
								<option value="">선택하세요</option>
								<%
								for(int i = 1; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
								%>
									
									<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=selected%>><%=icBean.getIc_name()%></option>
								<%}%>
								</select><label for="typeCode4" class="invisible"><%=codeTypeName%> 선택</label>
							</span>
							
							<%
									selected = "";
									codeTypeName = "";
									arrIcBean = icMgr.GetType(7);
									codeTypeName = icMgr.GetCodeName(arrIcBean, 0);
						%>
							<span class="title wid_70px"><span class="icon">-</span><%=codeTypeName%></span>
							<span class="txts wid_120px">
								<select id="typeCode7" name="typeCode7" class="ui_select_07">
								<option value="">선택하세요</option>
								<%
								for(int i = 1; i < arrIcBean.size(); i++){
									icBean = (IssueCodeBean) arrIcBean.get(i);
									if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
									else selected = "";
								%>
									
									<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=selected%>><%=icBean.getIc_name()%></option>
								<%}%>
								</select><label for="typeCode7" class="invisible"><%=codeTypeName%> 선택</label>
							</span>

							<span class="title wid_70px"><span class="icon">-</span>등록자</span>
							<span class="txts wid_120px">
								<select id="issueWriter" name="issueWriter" class="ui_select_04">
								<option value="">선택하세요</option>
								<%
								List listMember = issueMgr.getMember("2,3");
								for(int i = 0; i < listMember.size(); i++){
									String mem[] = (String[])listMember.get(i);
									if(issue_m_seq.equals(mem[0])) selected = "selected";
									else selected = "";
								%>
									<option value="<%=mem[0]%>" <%=selected%>><%=mem[1]%></option>
								<%}%>
								</select><label for="issueWriter" class="invisible"><%=codeTypeName%> 선택</label>
							</span>
						</div>	
						<div class="ui_row_00">
								<span class="title"><span class="icon">-</span>매체관리</span>
								<span class="txts">
									<div class="dcp m_r_10"><input name="tier" type="checkbox" id="ui_input_chk_02_00" class="boardAllChecker" value=""><label for="ui_input_chk_02_00"><span class="chkbox_00"></span><strong>Tier 전체</strong></label></div>
									<div class="dcp m_r_10"><input name="tier" type="checkbox" onclick="checkTiers();" <%=tier1 %> id="ui_input_chk_02_01" value="1"><label for="ui_input_chk_02_01"><span class="chkbox_00"></span>Tiering 1</label></div>
									<div class="dcp m_r_10"><input name="tier" type="checkbox" onclick="checkTiers();" <%=tier2 %> id="ui_input_chk_02_02" value="2"><label for="ui_input_chk_02_02"><span class="chkbox_00"></span>Tiering 2</label></div>
									<div class="dcp m_r_10"><input name="tier" type="checkbox" onclick="checkTiers();" <%=tier3 %> id="ui_input_chk_02_03" value="3"><label for="ui_input_chk_02_03"><span class="chkbox_00"></span>Tiering 3</label></div>
									<div class="dcp m_r_10"><input name="tier" type="checkbox" onclick="checkTiers();" <%=tier4 %> id="ui_input_chk_02_04" value="4"><label for="ui_input_chk_02_04"><span class="chkbox_00"></span>Tiering 4</label></div>
									<div class="dcp m_r_10"><input name="tier" type="checkbox" onclick="checkTiers();" <%=tier5 %> id="ui_input_chk_02_05" value="5"><label for="ui_input_chk_02_05"><span class="chkbox_00"></span>Tiering 5</label></div>
								</span>
						</div>					
					</div>
				</div>
				<div class="ui_searchbox_toggle">
					<a href="#" class="btn_toggle active">검색조건 열기/닫기</a>
				</div>
			</div>
		<!-- // 검색 -->
				</td>
			</tr>
			<!-- 검색 끝 -->
			<tr>
				<td style="height:40px;">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="100%" align="left" style="padding-right: 10px; padding-top: 5px"><img src="../../images/search/icon_search_bullet.gif" style="vertical-align: middle;"><%=srtMsg%></td>
					</tr>
					<tr>
						<td width="100%" align="left" style="padding-right: 10px; padding-top: 5px"><img src="../../images/search/icon_search_bullet.gif" style="vertical-align: middle;"> 
						<strong>출처별</strong> 검색결과: <%=strCnt%></td>
					</tr>					
				</table>								
				</td>
			</tr>
			<tr>
				<td style="height:40px;">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../images/issue/btn_allselect.gif" onclick="checkAll(!document.fSend.checkall.checked);" style="cursor:pointer;"/></td>
						<td><img src="../../images/issue/btn_mailsend.gif" onclick="goMailTo();" style="cursor:pointer;"/></td>
						<!-- <td><img src="../../images/issue/btn_exelsave.gif" onclick="goExcelTo('group');" style="cursor:pointer;"/></td> -->
						<td><img src="../../images/issue/btn_allexelsave.gif" onclick="goExcelTo('all');" style="cursor:pointer;"/></td>
						<!-- <td><img src="../../images/issue/btn_allexelsave.gif" onclick="excelForPOI();" style="cursor:pointer;"/></td> -->
						<td><img src="../../images/issue/btn_del.gif" onclick="deleteIssueData();" style="cursor:pointer;"/></td>
						<td><img src="../../images/issue/btn_multi.gif" onclick="muti_updateIssueData('update_multi');" style="cursor:pointer;"/></td>
						<td width="100%" align="right" style="padding-right: 10px; padding-top: 15px"></td>
					</tr>
				</table>								
				</td>
			</tr>
			<!-- 게시판 시작 -->
			<tr>
				<td>
				<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
				<col width="5%"><col width="22%"><col width="*"><col width="5%"><!-- <col width="5%"> --><col width="12%"><col width="10%"><col width="5%">
					<tr>
						<th><input type="checkbox" name="checkall" id="tt" onclick="checkAll(this.checked);" value=""></th>
						<th>출처</th>
						<th>제목</th>
						<th></th>
						<!-- <th></th> -->
						<th>유사</th>
						<th>수집일시</th>
						<th>성향</th>
					</tr>
<%
		String sunghyang = "";
		String update_writer = "";	//수정완료된 글
		if(arrIdBean.size() > 0){
			for(int i = 0; i < arrIdBean.size(); i++){
				idBean = (IssueDataBean)arrIdBean.get(i);
				arrIdcBean = (ArrayList) idBean.getArrCodeList();
				
				//성향
				sunghyang = icMgr.GetCodeNameType(arrIdcBean,9);
				update_writer = idBean.getId_writter();
				//유사 타이틀 만들기~~
				String strSites = "";
				String strSitesValue = "";
				String[] ar_SiteCnt = idBean.getSite_group_same_ct().split(",");
				String siteCheck = "";
				String child = "";
				
				int sameCt = 0;
				if(arrSiteGroup.size() == ar_SiteCnt.length){
				 	for(int j = 0; j < arrSiteGroup.size(); j++){
				 		siteCheck = ar_SiteCnt[j];
				 		sameCt += Integer.parseInt(siteCheck); 
				 		if(strSites.equals("")){
				 			strSites = ((String[])arrSiteGroup.get(j))[1] + " : " +  ar_SiteCnt[j];
				 			if(ar_SiteCnt[j].equals("0")){
				 				strSitesValue = ar_SiteCnt[j];
				 			}else{
				 				strSitesValue = "<a href=\"javascript:getSourceData('"+ idBean.getMd_pseq() +"','"+((String[])arrSiteGroup.get(j))[0]+"','"+ ((String[])arrSiteGroup.get(j))[1] +"')\">"+ siteCheck + "</a>";
				 			}
				 		}else{
				 			strSites += ", " + ((String[])arrSiteGroup.get(j))[1] + " : " +  ar_SiteCnt[j];
				 			if(ar_SiteCnt[j].equals("0")){
				 				strSitesValue += "," + ar_SiteCnt[j];
				 			}else{
				 				strSitesValue += "," + "<a href=\"javascript:getSourceData('"+ idBean.getMd_pseq() +"','"+((String[])arrSiteGroup.get(j))[0]+"','"+ ((String[])arrSiteGroup.get(j))[1] +"')\">" + siteCheck +"</a>";
				 			}
				 		}
				 	}
				}
				if(sameCt > 0){
					child = "Y";
				}else{
					child = "N";
				}
				//TR 색갈변경하기
				String titleColor = "";
				if( !"".equals(update_writer) && update_writer != null && !"null".equals(update_writer) ){ 
					titleColor = "";
				}else{
					titleColor = "";
				}
				
				String star = "";
				//카페 일경우~	        	
	        	if(idBean.getS_seq().equals("3555") || idBean.getS_seq().equals("4943")){
	        		star = "<img src='../../images/search/yellow_star.gif' style='cursor:pointer' onclick=portalSearch('"+idBean.getS_seq()+"','"+java.net.URLEncoder.encode(idBean.getId_title(),"utf-8")+"')>";
	        	}
				
%>
					<tr style="background-color: <%=titleColor%>">
						<td><input name="check" type="checkbox" value="<%=idBean.getId_seq()%>" onclick="getIssue(this);" <%if(check_cnt!=null){for(int j=0; j<check_cnt.length; j++){if(check_cnt[j].equals(idBean.getMd_seq())){out.print("checked");}}}%>></td>
						<td style="text-align: left;"><span style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board_01_tit_0<%=idBean.getMd_type()%>" title="<%=idBean.getMd_site_name()%>"><%=idBean.getMd_site_name()%></span></td>
						<td style="text-align: left;"><div style="width:320px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" title="<%=idBean.getId_title()%>"><%=star%><a href="javascript:PopIssueDataForm('<%=idBean.getMd_seq()%>','<%=child%>');"><%=idBean.getId_title()%></a></div></td>
						<td><a onClick="hrefPop('<%=idBean.getId_url()%>');" href="javascript:void(0);" ><img src="../../images/issue/ico_original.gif" align="absmiddle" /></a></td>
						<%-- <td><a href="javascript:goSmsTo('<%=idBean.getId_seq()%>');" ><img src="../../images/issue/ico_sms.gif" align="absmiddle" /></a></td> --%>
						<td title="<%=strSites%>"><%=strSitesValue%></td>
						<td><%=idBean.getFormatMd_date("yyyy-MM-dd")%></td>
						<td><p class="tendency_0<%if(sunghyang.equals("긍정")){out.print("1");}else if(sunghyang.equals("중립")){out.print("2");}else{out.print("3");}%>"><%=sunghyang%></p></td>
					</tr>
					<tr>
						<td class="same" colspan="6">
							<div id="SameList_<%=idBean.getMd_pseq()%>" style="display:none;"></div>
						</td>
					</tr>
<%
			}
		}
%>
				</table>
				</td>
			</tr>
			<!-- 게시판 끝 -->
			<tr>
				<td class="al" style="height:40px">
				<img src="../../images/issue/btn_allselect.gif" onclick="checkAll(!document.fSend.checkall.checked);" style="cursor:pointer;"/>
				<img src="../../images/issue/btn_mailsend.gif" onclick="goMailTo();" style="cursor:pointer;"/>
				<!-- <img src="../../images/issue/btn_exelsave.gif" onclick="goExcelTo('group');" style="cursor:pointer;"/> -->
				<img src="../../images/issue/btn_allexelsave.gif" onclick="goExcelTo('all');" style="cursor:pointer;"/>
				<img src="../../images/issue/btn_del.gif" onclick="deleteIssueData();" style="cursor:pointer;"/>
				</td>
			</tr>
			<!-- 페이징 -->
			<tr>
				<td style="padding-top:10px">
				<table id="paging" border="0" cellpadding="0" cellspacing="2" style="margin:0 auto">
					<tr><%=PageIndex.getPageIndex(nowpage, totalPage,"","" )%></tr>
				</table>
				</td>
			</tr>
			<!-- 페이징 -->
		</table>
		</td>
	</tr>
</table>

</form>
<div id="excelView" style="display: none;"></div>

<%-- 달력 테이블 --%>
<table width="162" border="0" cellspacing="0" cellpadding="0" id="calendar_conclass" style="position:absolute;display:none;">
	<tr><td><img src="../../images/calendar/menu_bg_004.gif" width="162" height="2"></td></tr>
	<tr>
		<td align="center" background="../../images/calendar/menu_bg_005.gif"><table width="148" border="0" cellspacing="0" cellpadding="0">
			<tr><td height="6"></td></tr>
			<tr><td id="calendar_calclass"></td></tr>
			<tr><td height="5"></td></tr>
		</table></td>
	</tr>
	<tr><td><img src="../../images/calendar/menu_bg_006.gif" width="162" height="2"></td></tr>
</table>
<iframe id="processFrm" name ="processFrm" width="0" height="0" style="display: none;" ></iframe>
<iframe id="if_samelist" name="if_samelist" width="0" height="0" src="about:blank" style="display: none;"></iframe>
</body>
</html>