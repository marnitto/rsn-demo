<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="	java.util.ArrayList
                 	,risk.mobile.AlimiSettingBean
					,risk.mobile.AlimiSettingDao
					,risk.util.ParseRequest
					,risk.admin.site.SitegroupBean
				 	,risk.admin.site.SiteMng
				 	,risk.search.userEnvMgr
				 	,risk.search.keywordInfo
				 	,risk.admin.classification.classificationMgr
				 	,risk.admin.classification.clfBean
				 	,java.util.List
				 	,risk.search.userEnvMgr
                    ,risk.search.userEnvInfo
					" 
%>
<% 
	ParseRequest pr = new ParseRequest(request);
	AlimiSettingDao asDao = new AlimiSettingDao();
	AlimiSettingBean asBean = null;	
		
	
	String mode = pr.getString("mode","INSERT");
	String as_seq        = pr.getString("as_seq","");
	String ab_seq = null;
	String nowpage = pr.getString("nowpage","");
	
	String styleDisplay1 = "";
	String styleDisplay2 = "";
	String styleDisplay3 = "";
	String styleDisplay4 = "";
	
	
	ArrayList alimiSetList = new ArrayList();
	List xpList = null;
	List sgList = null;
	
	String[] smsTime = null;
	String smsTimeCheck1 =null;
	String smsTimeCheck2 =null;
	String smsTimeCheck3 =null;
	String smsTimeCheck4 =null;
	String smsTimeCheck5 =null;
	
	String[] mtType = null;
	String mtTypeCheck1 = null;
	String mtTypeCheck2 = null;
	
	//pr.printParams();
	String[] sdGsnList = null;
	String[] exSeqList = null;
	int sdGsnCnt = 0;
	int exSeqCnt = 0;
	
	if(mode.equals("UPDATE") && as_seq.length()>0)
	{
		ab_seq = asDao.getReceiverSeq(as_seq);
		//System.out.println("ab_seq:"+ab_seq);
		alimiSetList = asDao.getAlimiSetList(1,0,as_seq,"Y");
		asBean = (AlimiSettingBean)alimiSetList.get(0);
		
				
		if(asBean.getSd_gsns()!=null && !asBean.getSd_gsns().trim().equals(""))
		{
			sdGsnList = asBean.getSd_gsns().split(",");
			sdGsnCnt = sdGsnList.length;
		}
		
		if(asBean.getAs_infotype().equals("1"))
		{
			if(asBean.getAs_type().equals("1"))
			{
				styleDisplay1 = "";
				styleDisplay2 = "none";
				styleDisplay3 = "none";
				styleDisplay4 = "none";
				
			}else{
				styleDisplay1 = "none";
				styleDisplay2 = "";
				styleDisplay3 = "none";
				styleDisplay4 = "none";
			}
		}else{
			if(asBean.getAs_type().equals("1"))
			{
				styleDisplay1 = "none";
				styleDisplay2 = "none";
				styleDisplay3 = "";
				styleDisplay4 = "none";
				
			}else{
				styleDisplay1 = "none";
				styleDisplay2 = "none";
				styleDisplay3 = "none";
				styleDisplay4 = "";
			}
		}		
		
		if(asBean.getAs_sms_time()!=null && !asBean.getAs_sms_time().equals(""))
		{
			smsTime = asBean.getAs_sms_time().split(",");
			
  			if(smsTime!=null)
  			{
  				for(int i=0;i<smsTime.length;i++)
  				{
  					if(smsTime[i].equals("1"))smsTimeCheck1="checked";
  					if(smsTime[i].equals("2"))smsTimeCheck2="checked";
  					if(smsTime[i].equals("3"))smsTimeCheck3="checked";
  					if(smsTime[i].equals("4"))smsTimeCheck4="checked";
  					if(smsTime[i].equals("5"))smsTimeCheck5="checked";
  				}
  			}
		}
		
		if(asBean.getMt_types()!=null && !asBean.getMt_types().equals(""))
		{
			mtType = asBean.getMt_types().split(",");
			
  			if(mtType!=null)
  			{
  				for(int i=0;i<mtType.length;i++)
  				{
  					if(mtType[i].equals("1"))mtTypeCheck1="checked";
  					if(mtType[i].equals("2"))mtTypeCheck2="checked";  					
  				}
  			}
		}
		
	}
	
	userEnvMgr uemng = new userEnvMgr();
	userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");
	xpList = uemng.getKeywordGroup(uei.getMg_xp());	
	
	SiteMng sitemng = new SiteMng();
	sgList = sitemng.getSGList();
	
	System.out.println(mode.equals("UPDATE") +" - mode ");
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=SS_URL%>css/base.css" />
<style type="text/css">
.sameOp{height: 20px;box-sizing: content-box;color: rgb(33, 33, 33);font-size: 11px;line-height: 18px;padding: 0px 0px 0px 5px;margin: 0px 4px;border-width: 1px;border-style: solid;border-color: rgb(199, 199, 199);border-image: initial;}
</style>

<script src="<%=SS_URL%>js/jquery.js"  type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js"  type="text/javascript"></script>
<script src="<%=SS_URL%>js/common.js"  type="text/javascript"></script>
<script language="javascript">

	//$('document').ready(initMain);
	function initMain()
	{
		
		$("[name=as_type]").each(function(){
			if ( $(this).attr("checked") ){
				if ( $(this).val() == 4 ){
					$(".ex_top").css("display","none");		
				}else{
					changeTopKeywordGroup();
				}
			}
		});
	}

	function checkAsTypeValue(){
		var f =  document.alimi_detail;
		var asType;
		for( var i = 0; i<f.as_type.length; i++)
		{
			if(f.as_type[i].checked)
			{			
				asType =f.as_type[i].value;			
			}
		}
		return asType;
	}
	
	function checkAsInfoTypeValue(){
		var f =  document.alimi_detail;
		var asInfoType;
	
		asInfoType = f.as_infotype.value;
	
	
		for( var i = 0; i<f.as_infotype.length; i++)
		{
			if(f.as_infotype[i].checked)
			{			
				asInfoType =f.as_infotype[i].value;			
			}
		}
		
		return asInfoType;
	}
	
	function checkedIntervalValue(name)
	{	
		var obj = eval('document.alimi_detail.'+name);
		var intervalValue='';
		if(obj.length)
		{
			for(var i =0 ; i<obj.length; i++)
			{
				if(obj[i].checked)
				{				
					intervalValue = obj[i].value;
				}
			}
		}else{
			if(obj.checked)
			{			
				intervalValue = obj.value;
			}
		}	
		return intervalValue;
	}
	
	function checkAsIntervalValue(){
		var f =  document.alimi_detail;
		var asType = checkAsTypeValue();
		var asInfoType = checkAsInfoTypeValue();
		var asInterval;
		
		if(asInfoType=='1')
		{
			if(asType==1)
			{
				asInterval = checkedIntervalValue('as_interval1');
			}else{
				asInterval = checkedIntervalValue('as_interval2');
			}
		}else{
			if(asType=='1')
			{
				asInterval = checkedIntervalValue('as_interval3');			
			}else{
				asInterval = checkedIntervalValue('as_interval4');
			}		
		}	
		return asInterval;
	}
	
	function checkedSmsTimeValue(name)
	{	
		var obj = eval('document.alimi_detail.'+name);
		var asSmsTime = '';
		//alert(obj.length);
		if(obj.length)
		{
			for(var i =0 ; i<obj.length; i++)
			{
				if(obj[i].checked)
				{
					if(asSmsTime=='')
					{				
						asSmsTime = obj[i].value;
					}else{
						asSmsTime +=',' + obj[i].value;
					}
				}
			}
		}else{
			if(obj.checked)
			{			
				asSmsTime = obj.value;
			}
		}	
		return asSmsTime;
	}
	
	function checkSmsTimelValue(){
		var f =  document.alimi_detail;
		var asType = checkAsTypeValue();
		var asInfoType = checkAsInfoTypeValue();
		var asSmsTime;
		
		if(asInfoType=='1')
		{
			if(asType==1)
			{
				asSmsTime = '0';			 
			}else{
				asSmsTime = checkedSmsTimeValue('as_sms_time1');
			}
		}else{
			if(asType=='1')
			{
				asSmsTime = '0';	
			}else{
				asSmsTime = checkedSmsTimeValue('as_sms_time2');
			}		
		}	
		return asSmsTime;
	}
	
	function checkAllKxp(chk) {
		var o=document.all.k_xp;
		for(i=0; i<o.length; i++) {
			o(i).checked = chk;
		}
	}
	
	function checkXpValue(){
		var f =  document.alimi_detail;
		var check = false;
		f.k_xps.value='';
		if(f.k_xp.length){
			for( var i = 0; i<f.k_xp.length; i++)
			{
					
				if(f.k_xp[i].checked)
				{		
					f.k_xps.value =f.k_xp[i].value;							
				}
			}
		}else{
			f.k_xps.value = f.k_xp.value;
		}
		if(f.k_xps.value.length>0)check =true;
		return check;
			
	}

	function checkYpValue(){
		var f =  document.alimi_detail;
		var check = false;
		f.k_yps.value='';
		if(f.k_yp){
			if(f.k_yp.length){
				for( var i = 0; i<f.k_yp.length; i++)
				{
						
					if(f.k_yp[i].checked)
					{			
						if(f.k_yps.value=='')
						{
							f.k_yps.value =f.k_yp[i].value;
						}else{
							f.k_yps.value = f.k_yps.value+','+ f.k_yp[i].value;
						}			
					}
				}
			}else{
				f.k_yps.value = f.k_yp.value;
			}
		}
		//alert(f.k_yps.value);
		if(f.k_yps.value.length>0)check =true;
		return check;
			
	}
	
	function checkAllSgSeq(chk) {
		/* var o=document.all.sg_seq;
		for(i=0; i<o.length; i++) {
			o(i).checked = chk;
		} */
		if($("[name=sg_seqALL]").is(":checked")){
			$("[name=sg_seq]").attr("checked", "checked");
		}else{
			$("[name=sg_seq]").attr("checked", "");
		}
	}
	function checkSgSeqValue(){
		var f =  document.alimi_detail;
		var check = false;
		f.sg_seqs.value = '';
		for( var i = 0; i<f.sg_seq.length; i++)
		{
			if(f.sg_seq[i].checked)
			{
				if(f.sg_seqs.value=='')
				{
					f.sg_seqs.value =f.sg_seq[i].value;
				}else{
					f.sg_seqs.value = f.sg_seqs.value+','+ f.sg_seq[i].value;
				}
			}
		}
		if(f.sg_seqs.value.length>0)check =true;
		return check;	
	}
	
	function checkMtTypeValue(){
		var f =  document.alimi_detail;
		var check = false;
		f.mt_types.value = '';
		for( var i = 0; i<f.mt_type.length; i++)
		{
			if(f.mt_type[i].checked)
			{			
				if(f.mt_types.value=='')
				{
					f.mt_types.value =f.mt_type[i].value;
				}else{
					f.mt_types.value = f.mt_types.value+','+ f.mt_type[i].value;
				}			
			}
		}
		if(f.mt_types.value.length>0)check =true;
		return check;
	}
	
	function checkAbAeqValue(){
		var f =  frmReceiver.document.ifr_receiver;
		var pf = document.alimi_detail;
	
		pf.ab_seqs.value = '';
	
		if(f.chkNum)
		{
			if(f.chkNum.length)
			{
				for( var i = 0; i<f.chkNum.length; i++)
				{
					if(f.chkNum[i].checked)
					{
						if(pf.ab_seqs.value=='')
						{
							pf.ab_seqs.value =f.chkNum[i].value;
						}else{
							pf.ab_seqs.value = pf.ab_seqs.value+','+ f.chkNum[i].value;
						}
					}
				}
			}else{
				if( f.chkNum.checked)
				{
					pf.ab_seqs.value = f.chkNum.value;
				}
			}	
		}
		//alert(pf.ab_seqs.value);
	}
	
	function asTypeCheck(objValue)   // 발송매체 구분 (objValue) - 1: 이메일 3: R-rimi 발송 4: 포탈TOP 발송 6: 포탈댓글전용알리미
	{
		var f = document.alimi_detail;
		var asType = objValue;
		var asInfoType = checkAsInfoTypeValue();	// 1: 일반수집
		
		if(asInfoType==1)
		{

			$("#intervalFilter3").css("display", "none");
			$("#intervalFilter4").css("display", "none");
			$(".ex_top").css("display", "");
			
			if(asType==1) //이메일
			{
				$("#sender_mail").css("display", "");	//발신자 메일
				
				$("#intervalFilter1").css("display", "");
				$("#intervalFilter2").css("display", "none");
				
				$("#smsTimeFilter1").css("display", "none");
				$("#smsTimeFilter2").css("display", "none");
				document.getElementById("tr_later").style.display='none';
				document.getElementById("tr_monitor_use").style.display='none';

				$("#as_same").css("display", "none");
				
			}else if(asType==3){ //R-rimi 발송
				$("#sender_mail").css("display", "none");	//발신자 메일
				
				$("#intervalFilter1").css("display", "none");
				$("#intervalFilter2").css("display", "");
				
				$("#smsTimeFilter1").css("display", "");
				$("#smsTimeFilter2").css("display", "none");
				document.getElementById("tr_later").style.display='';
				document.getElementById("tr_monitor_use").style.display='';
				
				$("#as_same").css("display", "");
				var op = $(".sameOp").val();
				if(op=="1"){
					$("#as_same_text").text("%");
				}else if(op=="2"){
					$("#as_same_text").text("건");
				}
				
			}else if(asType==4){ //포탈TOP 발송
				$("#sender_mail").css("display", "none");	//발신자 메일
				$(".ex_top").css("display", "none");	//키워드 그룹, 사이트 그룹, 정보유형
				
				$("#intervalFilter1").css("display", "none");
				$("#intervalFilter2").css("display", "");
				
				$("#smsTimeFilter1").css("display", "");
				$("#smsTimeFilter2").css("display", "none");
				document.getElementById("tr_later").style.display='';
				document.getElementById("tr_monitor_use").style.display='';
				
				$("#as_same").css("display", "none");
				
			}else if(asType==6){ //포탈댓글전용알리미
				$("#sender_mail").css("display", "none");	//발신자 메일
				$(".ex_top").css("display", "none");	//키워드 그룹, 사이트 그룹, 정보유형
				
				$("#intervalFilter1").css("display", "none");
				$("#intervalFilter2").css("display", "");
				
				$("#smsTimeFilter1").css("display", "");
				$("#smsTimeFilter2").css("display", "none");

				document.getElementById("tr_later").style.display='';
				document.getElementById("tr_monitor_use").style.display='';
				
				$("#as_same").css("display", "");
				var op = $(".sameOp").val();
				if(op=="1"){
					$("#as_same_text").text("%");
				}else if(op=="2"){
					$("#as_same_text").text("건");
				}
			}
				
		}else{
			
			$("#intervalFilter3").css("display", "");
			$("#intervalFilter4").css("display", "");
			if(asType==1)
			{
					
				$("#intervalFilter1").css("display", "none");
				$("#intervalFilter2").css("display", "");
				$("#intervalFilter3").css("display", "");
				$("#intervalFilter4").css("display", "none");
				$("#smsTimeFilter1").css("display", "none");
				$("#smsTimeFilter2").css("display", "none");
			}else{
				
				$("#intervalFilter1").css("display", "");
				$("#intervalFilter2").css("display", "none");
				$("#intervalFilter3").css("display", "none");
				$("#intervalFilter4").css("display", "");
				$("#smsTimeFilter1").css("display", "none");
				$("#smsTimeFilter2").css("display", "");
			}
		}
	}
	
	function asInfoTypeCheck(objValue)
	{
		var f = document.alimi_detail;
		var asType = checkAsTypeValue();
		var asInfoType = objValue;	
		if(asInfoType==1)
		{
			document.getElementById("smsFilter").style.display='none';		
			document.getElementById("mailFilter").style.display='';		
			if(asType==1)
			{
				document.getElementById("intervalFilter1").style.display='';
				document.getElementById("intervalFilter2").style.display='none';
				document.getElementById("intervalFilter3").style.display='none';
				document.getElementById("intervalFilter4").style.display='none';
				document.getElementById("smsTimeFilter1").style.display='none';	
				document.getElementById("smsTimeFilter2").style.display='none';
				document.getElementById("tr_later").style.display='none';
				document.getElementById("tr_monitor_use").style.display='none';
				
			}else{
				document.getElementById("intervalFilter1").style.display='none';
				document.getElementById("intervalFilter2").style.display='';
				document.getElementById("intervalFilter3").style.display='none';
				document.getElementById("intervalFilter4").style.display='none';
				document.getElementById("smsTimeFilter1").style.display='';	
				document.getElementById("smsTimeFilter2").style.display='none';
				document.getElementById("tr_later").style.display='';
				document.getElementById("tr_monitor_use").style.display='';
			}
			//document.getElementById("smsTimeFilter").style.display ='';
			
		}else{
			document.getElementById("mailFilter").style.display='none';	
			document.getElementById("smsFilter").style.display='';
			if(asType==1)
			{
				document.getElementById("intervalFilter1").style.display='none';
				document.getElementById("intervalFilter2").style.display='none';
				document.getElementById("intervalFilter3").style.display='';
				document.getElementById("intervalFilter4").style.display='none';
				document.getElementById("smsTimeFilter1").style.display='none';
				document.getElementById("smsTimeFilter2").style.display='none';
			}else{
				document.getElementById("intervalFilter1").style.display='none';
				document.getElementById("intervalFilter2").style.display='none';
				document.getElementById("intervalFilter3").style.display='none';
				document.getElementById("intervalFilter4").style.display='';
				document.getElementById("smsTimeFilter1").style.display='none';
				document.getElementById("smsTimeFilter2").style.display='';
			}
			//document.getElementById("smsTimeFilter").style.display ='';
			
		}
	}
	
	//발송체크되면 발송관련  tr 보여줌
	function monitorView(m){
		
		if(m.value=="Y"){
			document.getElementById("tr_monitor_cnt_info").style.display='';
			document.getElementById("tr_monitor_inspector").style.display='';
			document.getElementById("as_monitor_repeat_m00").value="";
			document.getElementById("as_monitor_max_m00").value="";
			document.getElementById("as_monitor_inspector00").value="";
		}else if(m.value=="N"){
			document.getElementById("tr_monitor_cnt_info").style.display='none';
			document.getElementById("tr_monitor_inspector").style.display='none';
			document.getElementById("as_monitor_repeat_m00").value="";
			document.getElementById("as_monitor_max_m00").value="";
			document.getElementById("as_monitor_inspector00").value="";
		}else{
			alert("오류발생");
		}
	}
	
// 	숫자만입력받기함수들 시작
	function onlyNumber(event,type){
		event = event || window.event;
		var keyID = (event.which) ? event.which : event.keyCode;
		if(type=="1"){
			if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) {return;}
			else{return false;}
		}	
		if(type=="2"){
			if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 || keyID ==188){return;}
			else{return false;}
		}
	}
	function removeChar(event,type) {
		event = event || window.event;
		var keyID = (event.which) ? event.which : event.keyCode;
		if(type=="1"){
			if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ){return;}	
			else{event.target.value = event.target.value.replace(/[^0-9,]/g, "");}
		}
		if(type=="2"){
			if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 || keyID ==188){return;}
			else{event.target.value = event.target.value.replace(/[^0-9,]/g, "");}
		}
	}
	//	숫자만입력받기함수들 종료
	
	//추가부분 As_later_send
	function checkAsLaterSendValue(){
		var obj = eval('document.alimi_detail.as_later_send1');
		var as_later_send='';
		if(obj.length)
		{
			for(var i =0 ; i<obj.length; i++)
			{
				if(obj[i].checked)
				{				
					as_later_send = obj[i].value;
				}
			}
		}else{}
		
		return as_later_send;
	}
	
	function save()
	{
		var f = document.alimi_detail;
		var asInfoType = checkAsInfoTypeValue();
		var checkValue = false;
		//alert(asType);
		if(asInfoType==1)
		{
			f.as_sms_time.value=checkSmsTimelValue();				
			f.as_interval.value=checkAsIntervalValue();	
			f.as_later_send.value =checkAsLaterSendValue();
			if(f.as_title.value.length==0){alert('제목을 입력하여 주십시오.'); f.as_title.focus(); return;}
			
			
			var checked_asType = "";
			$("[name=as_type]").each(function(){
				if( $(this).attr("checked") ){
					checked_asType = $(this).val();
				}
			});
			
			console.log(checked_asType);
			
			if(checked_asType != "4" && checked_asType != "6"){  // 포탈TOP, 포탈댓글전용 제외
			checkValue=checkXpValue();
			checkValue=checkYpValue();
			if(checkValue==false){alert('키워드 그룹을 체크하여주십시오.'); return;}
			checkValue=checkSgSeqValue();
			if(checkValue==false && f.sd_gsns.value.length==0){alert('하나의 사이트 그룹이나 사이트를 지정해주십시오.'); return;}		
			checkValue=checkMtTypeValue();
			if(checkValue==false){alert('정보유형을 그룹을 체크하여주십시오.'); return;}
			
			}
			
			//정보수신자 체크
			checkAbAeqValue();
			//f.as_sms_key.value='';
			//f.as_sms_exkey.value='';
			
			if($('input:radio[name=as_monitor_use1]:checked').val()=="Y"){
				if($('#as_monitor_max_m00').val().length==0){alert('미발송대기시간항목을 입력하여 주십시오.'); $('#as_monitor_max_m00').focus(); return;}
				if($('#as_monitor_repeat_m00').val().length==0){alert('미발송대기시간항목을 입력하여 주십시오.'); $('#as_monitor_repeat_m00').focus(); return;}
				if($('#as_monitor_inspector00').val().length==0){alert('수신자를 작성해주세요.'); $('#as_monitor_inspector00').focus(); return;}
				
				f.as_monitor_use.value	= "Y";
				f.as_monitor_max_m.value = $('#as_monitor_max_m00').val();
				f.as_monitor_repeat_m.value	= $('#as_monitor_repeat_m00').val();
				f.as_monitor_inspector.value = $('#as_monitor_inspector00').val();
			}else{
				f.as_monitor_use.value	= "N";
				f.as_monitor_max_m.value = '0';
				f.as_monitor_repeat_m.value	= '0';
				f.as_monitor_inspector.value = '';
			}
									
		}else{
			
			if(f.as_title.value.length==0){alert('제목을 입력하여 주십시오.'); f.as_title.focus(); return;}			
			f.as_sms_time.value=checkSmsTimelValue();		
			f.as_interval.value=checkAsIntervalValue();
			checkAbAeqValue();	
			
		}	
		
		f.target = '';
		f.action ='alimi_setting_prc.jsp';
		f.submit();
		
	}
	
	function goList()
	{
		var f = document.alimi_detail;
		f.target='';
		f.action = 'alimi_setting_list.jsp';
		f.submit();
	}
	
	function popSiteList(as_seq)
	{
		var f = document.alimi_detail;
		if(f.sd_gsns.value.length>0)
		{
			window.open("pop_sitelist.jsp?as_seq="+as_seq+"&selectedSdGsn="+f.sd_gsns.value+"&mode="+f.mode.value, "siteList", "width=850,height=600,scrollbars=no");
		}else{
			window.open("pop_sitelist.jsp", "siteList", "width=850,height=600,scrollbars=no");
		}	
	}
	
	
	function changeTopKeywordGroup()
	{
	    ajax.post('sub_keywordgroup_radio.jsp','alimi_detail','subKewyrodGroupView');
	}


	function ApproveDecimal(keyCode){

		var keyChar;
		
		if(keyCode){
			keyChar = keyCode = String.fromCharCode(keyCode);
		}else{
			return true;
		}

		if(("0123456789").indexOf(keyChar) > -1){		
			event.returnValue = true; 	
		}else{
			event.returnValue = false;
		}
	}

</script>
</head>
<body>
<form id="alimi_detail" name="alimi_detail" method="post">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="nowpage" value="<%=nowpage%>">
<input type="hidden" name="as_seq" value="<%=as_seq%>">
<input type="hidden" name="ab_seqs">
<input type="hidden" name="as_sms_time">
<input type="hidden" name="as_interval">
<input type="hidden" name="k_xps">
<input type="hidden" name="k_yps">
<input type="hidden" name="as_sitetype">
<input type="hidden" name="sg_seqs">
<input type="hidden" name="mt_types">
<input type="hidden" name="as_later_send">
<input type="hidden" name="as_monitor_use">
<input type="hidden" name="as_monitor_max_m">
<input type="hidden" name="as_monitor_repeat_m">
<input type="hidden" name="as_monitor_inspector">
<%
	if(mode.equals("INSERT")){
%>
<input type="hidden" name="sd_gsns">
<input name="as_infotype" type="hidden" value="1"/>
<script type="text/javascript">
	$(document).ready(function(){
		$("#sender_mail").css("display", "");
		$("#as_same").css("display", "none");
		
		$(".sameOp").change(function(){
			var op = $(this).val();
			if(op=="1"){
				$("#as_same_text").text("%");
			}else if(op=="2"){
				$("#as_same_text").text("건");
			}
		});
	});
</script>
<table style="width:850px" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table align="center" style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:37px;padding-top:0px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/alimi/tit_icon.gif" /><img src="../../../images/admin/alimi/tit_0509.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">알리미 설정관리</td>
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
			<!-- 게시판 시작 -->
			<tr>
				<td>
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="height:30px;"><span class="sub_tit">기본 설정</span></td>
					</tr>
					<tr>
						<td>
						<table id="board_write" border="0" cellspacing="0">
							<tr>
								<th><span class="board_write_tit">제목</span></th>
								<td><input style="width:80%;" name="as_title" type="text" class="textbox2"></td>
							</tr>
							<tr>
								<th><span class="board_write_tit">발송매체</span></th>
								<td>
								<ul>
									<li style="padding-right:20px;"><input name="as_type" type="radio" value="1" onclick="asTypeCheck(this.value);" checked>이메일</li>
									<!-- <li style="padding-right:20px;"><input name="as_type" type="radio" value="2" onclick="asTypeCheck(this.value);">긴급 SMS 발송</li> -->
									<li style="padding-right:20px;"><input name="as_type" type="radio" value="3" onclick="asTypeCheck(this.value);">R-rimi 발송</li>
									<li style="padding-right:20px;"><input name="as_type" type="radio" value="4" onclick="asTypeCheck(this.value);">포탈TOP 발송</li>
									<!-- <li style="padding-right:20px;"><input name="as_type" type="radio" value="5" onclick="asTypeCheck(this.value);">트위터 전용 R-rimi 발송</li> -->
									<li style="padding-right:20px;"><input name="as_type" type="radio" value="6" onclick="asTypeCheck(this.value);">포탈댓글전용알리미</li>
								</ul>		
								</td>
							</tr>
							<!-- <tr style="display: none;">
								<th><span class="board_write_tit">발송정보</span></th>
								<td>
								<ul>
									<li style="padding-right:20px;"><input name="as_infotype" type="radio" value="1" onclick="asInfoTypeCheck(this.value)" checked>일반수집</li>
								</ul>		
								</td>
							</tr> -->
							<tr>
								<th><span class="board_write_tit">정보 발송 여부</span></th>
								<td>
								<ul>
									<li style="padding-right:20px;"><input id="input_radio_00" name="as_chk" type="radio" value="1" checked /><label for="input_radio_00">발송</label></li>
									<li style="padding-right:20px;"><input id="input_radio_01" name="as_chk" type="radio" value="2"><label for="input_radio_01">발송중지</label></li>
								</ul>		
								</td>
							</tr>
							<tr id="sender_mail">
								<th><span class="board_write_tit">발신자 메일</span></th>
								<td>
									<select name="sendMail" >
										<option value="">선택하세요</option>
										<%
										String tmp[] = new String[3];
										ArrayList mail = new ArrayList(); 
										mail = asDao.getSendMailSetting();
										if(mail.size() > 0 ){
											for(int i=0; i <mail.size(); i++){
												tmp = (String[])mail.get(i);
										%>
											<option value="<%=tmp[0]%>"><%=tmp[2]%></option>
										<%	}
										}
										%>
									</select>
								</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
						<td style="padding-top:15px;height:45px;"><span class="sub_tit">발송 조건</span></td>
					</tr>
					<tr>
						<td>
						<table id="board_write" border="0" cellspacing="0">
							<tr class="ex_top">
								<th><span class="board_write_tit">키워드그룹</span></th>
								<td>
<%	for( int i=0 ; i < xpList.size() ; i++ ) {
		keywordInfo KGset = (keywordInfo)xpList.get(i);	%>
									<div style="width:150px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;float:left" title="<%=KGset.getK_Value()%>"><input type="radio" name="k_xp" value="<%=KGset.getK_Xp()%>" onclick="changeTopKeywordGroup();"><%=KGset.getK_Value()%></div>
<%	}	%>
								</td>
							</tr>
							<tr id="subKewyrodGroupView">
							</tr>
							<tr  class="ex_top">
								<th><span class="board_write_tit">사이트 그룹</span></th>
								<td>
									<input type="checkbox" name="sg_seqALL"  value="0" onclick="checkAllSgSeq(this.checked);">전체
<%	for( int i=0 ; i < sgList.size() ; i++ ) {
		SitegroupBean SGinfo = (SitegroupBean)sgList.get(i);%>
									<input type="checkbox" name="sg_seq" value="<%=SGinfo.get_seq()%>"><%= SGinfo.get_name()%>
<%	} %>
								</td>
							</tr>
							<tr class="ex_top">
								<th><span class="board_write_tit">사이트 리스트</span></th>
								<td class="normal"><table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td class="normal"><img src="../../../images/admin/alimi/btn_alrimiadd.gif" onclick="popSiteList('<%=as_seq%>');" style="cursor:pointer;"/></td>
										<td class="normal" id="siteListCnt"></td>
									</tr>
								</table></td>
							</tr>
							<tr class="ex_top">
								<th><span class="board_write_tit">정보유형</span></th>
								<td>
								<ul>
									<li style="padding-right:20px;"><input type="checkbox" name="mt_type" value="1">기사</li>
									<li><input type="checkbox" name="mt_type" value="2">게시물</li>
								</ul> 
								</td>
							</tr>
							<tr id="smsTimeFilter1" style="display:none">
								<th><span class="board_write_tit">발송시간</span></th>
								<td>
									<input name="as_sms_time1" type="checkbox" value="1" checked>평일 근무시간 ( 06:00 ~ 22:00 )<br>
									<input name="as_sms_time1" type="checkbox" value="2">평일 근무외시간  ( 22:00 ~ 명일 06:00 )<br>
									<input name="as_sms_time1" type="checkbox" value="3">주말 근무시간1 ( 06:00 ~ 24:00 )<br>
									<input name="as_sms_time1" type="checkbox" value="4">주말 근무시간2 ( 09:00 ~ 21:00 )<br>
									<input name="as_sms_time1" type="checkbox" value="5">주말 근무외시간 ( 22:00 ~ 명일 06:00 )<br>
								</td>
							</tr>
							<tr>
								<th><span class="board_write_tit">발송간격</span></th>
								<td>
								<ul id="intervalFilter1">
									<li style="padding-right:20px;"><input type="radio" name="as_interval1" value="0" checked>건별</li>
									<li style="padding-right:20px;"><input type="radio" name="as_interval1" value="30">30분</li>
									<li style="padding-right:20px;"><input type="radio" name="as_interval1" value="60">1시간</li>
									<li style="padding-right:20px;"><input type="radio" name="as_interval1" value="180">3시간</li>
								</ul> 
								<ul id="intervalFilter2" style="display:none">
									<li style="padding-right:20px;"><input type="radio" name="as_interval2" value="0" checked>건별</li>
								</ul> 
								</td>
							</tr>
							<tr id="as_same">
								<th><span class="board_write_tit"><select name="as_same_op" class="sameOp"><option value="1">유사율</option><option value="2">유사건수</option></select></span></th>
								<td>
									<input type="text" maxlength="3" size="3" name="as_same_value" value="0" onkeypress="ApproveDecimal(window.event.keyCode)" style="text-align: right">
									<span id="as_same_text">%</span>
								</td>
							</tr>
							<tr id="tr_later" style="display:none">
								<th><span class="board_write_tit">시간외 알람</span></th>
								<td>
									<ul>
										<li style="padding-right:20px;"><input name="as_later_send1" type="radio" value="Y">몰아서 받기</li>
										<li style="padding-right:20px;"><input name="as_later_send1" type="radio" value="N" checked>받지않기</li>
									</ul>		
								</td>
							</tr>
							<tr id="tr_monitor_use" style="display:none">
								<th><span class="board_write_tit">감시SMS발송</span></th>
									<td>
										<ul>
											<li style="padding-right:20px;"><input name="as_monitor_use1" type="radio" value="Y" onclick="monitorView(this)">발송</li>
											<li style="padding-right:20px;"><input name="as_monitor_use1" type="radio" value="N" onclick="monitorView(this)" checked >미발송</li>
										</ul>		
									</td>
							</tr>
							<tr id="tr_monitor_cnt_info" style="display: none;">
								<th><span class="board_write_tit">미발송대기시간</span></th>
									<td>
										<input type="text" style="text-align: right;" maxlength="4" size="5" id="as_monitor_max_m00" onkeydown="return onlyNumber(event,1)" onkeyup="removeChar(event,1)">분 이상부터
										<input type="text" style="text-align: right;" maxlength="4" size="5" id="as_monitor_repeat_m00"  onkeydown="return onlyNumber(event,1)" onkeyup="removeChar(event,1)">분 마다발송
									</td>
							</tr>
							<tr id="tr_monitor_inspector" style="display: none;">
								<th><span class="board_write_tit">감시SMS수신자</span></th>
									<td>
										<input style="width:60%;" type="text" style="text-align: right;" id="as_monitor_inspector00">
										<div style="margin-top: 5px; margin-bottom: 1px; font-size: 90%" > * 다수일 경우 세미콜론(;)처리 ex) 010-1234-4567;010-4567-8912 </div>
									</td>
							</tr>
							
							<!-- <tr>
								<th><span class="board_write_tit">제목 포함 필터</span></th>
								<td>
									<input name="as_sms_key" type="text" class="txtbox" size="50" value="">
								</td>
							</tr>
							<tr>
								<th><span class="board_write_tit">제목 제외 필터</span></th>
								<td>
									<input name="as_sms_exkey" type="text" class="txtbox" size="50" value="">
								</td>
							</tr> -->
							
						</table>
						
<!-- <table id="smsFilter" style="display:none" width="630" border="0" cellspacing="1" cellpadding="0">     
	<tr>
		<td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>제목 포함 필터</strong></td>
		<td width="510" style="padding: 5px 0px 5px 10px;"><input name="as_sms_key" type="text" class="txtbox" size="50"></td>
	</tr>
	<tr>
		<td colspan="2"  bgcolor="#D3D3D3" width="1" height="1"></td>
	</tr>
	<tr>
		<td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>제목 제외 필터</strong></td>
		<td width="510" style="padding: 5px 0px 5px 10px;"><input name="as_sms_exkey" type="text" class="txtbox" size="50"></td>
	</tr>
	<tr>
		<td colspan="2"  bgcolor="#D3D3D3" width="1" height="1"></td>
	</tr>		      
	<tr id="smsTimeFilter2" style="display:none">			      
		<td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>발송 시간</strong></td>
		<td width="510" style="padding: 5px 0px 5px 10px;">
			<input name="as_sms_time2" type="checkbox" value="1" checked>평일 근무시간 ( 06:00 ~ 22:00 )<br>
			<input name="as_sms_time2" type="checkbox" value="2">평일 근무외시간  ( 22:00 ~ 명일 06:00 )<br>
			<input name="as_sms_time2" type="checkbox" value="3">주말 근무시간1 ( 06:00 ~ 24:00 )<br>
			<input name="as_sms_time2" type="checkbox" value="4">주말 근무시간2 ( 09:00 ~ 21:00 )<br>
			<input name="as_sms_time2" type="checkbox" value="4">주말 근무외시간 ( 22:00 ~ 명일 06:00 )
		</td>				  	
	</tr>
	<tr>
		<td colspan="2"  bgcolor="#D3D3D3" width="1" height="1"></td>
	</tr>		       
	<tr>
		<td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>발송간격</strong></td>
		<td width="510" style="padding: 5px 0px 5px 10px;">		  		 	
		<table id="intervalFilter3" width="510" border="0" cellspacing="0" cellpadding="0" style="display:none">
			<tr>
				<td>
					<input type="radio" name="as_interval3" value="0" checked>건별&nbsp;&nbsp;
				  	<input type="radio" name="as_interval3" value="30">30분&nbsp;&nbsp;
				  	<input type="radio" name="as_interval3" value="60">1시간&nbsp;&nbsp;
				  	<input type="radio" name="as_interval3" value="180">3시간&nbsp;&nbsp;	
				</td>
			</tr>
		</table>
		<table id="intervalFilter4" width="510" border="0" cellspacing="0" cellpadding="0" style="display:none">
			<tr>
				<td>
					<input type="radio" name="as_interval4" value="0" checked>건별&nbsp;&nbsp;	
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>  -->
						</td>
					</tr>
				</table>
				<iframe name="frmReceiver" src="ifrm_receiver_list.jsp" frameborder="0" scrolling="no" width="820" height="200"></iframe>
				</td>
			</tr>
			<!-- 게시판 끝 -->
			<tr>
				<td style="height:40px;" align="center"><img src="../../../images/admin/alimi/btn_save2.gif" onclick="save();" style="cursor:pointer;"/><img src="../../../images/admin/alimi/btn_cancel.gif" onclick="goList();" style="cursor:pointer;"/></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<%
	}else if(mode.equals("UPDATE")){
		if(asBean!=null){
%>
<input type="hidden" name="sd_gsns" value="<%=asBean.getSd_gsns()%>">
<input name="as_infotype" type="hidden" value="1"/>
<input type="hidden" name="as_last_num" value="<%=asBean.getAs_last_num()%>">
<input type="hidden" name="as_same_op" value="<%=asBean.getAs_same_op()%>">
<input type="hidden" id="as_same_value" name="as_same_value" value="">

<script language="javascript">
	$('document').ready(function(){
		initMain();
		
		var asType = "<%=asBean.getAs_type()%>";
		var asSameOp = "<%=asBean.getAs_same_op()%>";
		$("input[name='as_type']").not("input[value='" + asType + "']").parent().css("display", "none");
		$(".ex_top").css("display", "");
		
		if(asType=="1"){
			$("#sender_mail").css("display", "");
			$("#as_same").css("display", "none");
			
		}else if(asType=="3" || asType=="6"){
			$("#sender_mail").css("display", "none");
			$("#as_same").css("display", "");
			
			if(asType=="6"){
				$(".ex_top").css("display", "none");
				$("#subKewyrodGroupView").css("display", "none");	
			}
			
			if(asSameOp=="1"){
				$("#as_same > th > span > label").text("유사율");
				$("#as_same > td > label").text("<%=asBean.getAs_same_percent()%> %");
				
				$("#as_same_value").val("<%=asBean.getAs_same_percent()%>");
			}else if(asSameOp=="2"){
				$("#as_same > th > span > label").text("유사건수");
				$("#as_same > td > label").text("<%=asBean.getAs_same_cnt()%> 건");
				
				$("#as_same_value").val("<%=asBean.getAs_same_cnt()%>");
			}
			
		}else if(asType=="4"){
			$("#sender_mail").css("display", "none");
			$("#as_same").css("display", "none");
			$(".ex_top").css("display", "none");
			
		}
	});
</script>
<table style="width:850px;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table align="center" style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:37px;padding-top:0px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/alimi/tit_icon.gif" /><img src="../../../images/admin/alimi/tit_0509.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">알리미 설정관리</td>
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
			<!-- 게시판 시작 -->
			<tr>
				<td>
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="height:30px;"><span class="sub_tit">기본 설정</span></td>
					</tr>
					<tr>
						<td>
						<table id="board_write" border="0" cellspacing="0">
							<tr>
								<th><span class="board_write_tit">제목</span></th>
								<td><input style="width:80%;" name="as_title" type="text" class="textbox2" value="<%=asBean.getAs_title() %>"></td>
							</tr>
							<tr>
								<th><span class="board_write_tit">발송매체</span></th>
								<td>
								<ul>
									<li style="padding-right:20px;"><input name="as_type" type="radio" value="1" onclick="asTypeCheck(this.value);" <%if(asBean.getAs_type().equals("1")){out.println("checked");}%>>이메일</li>
									<%-- <li style="padding-right:20px;"><input name="as_type" type="radio" value="2" onclick="asTypeCheck(this.value);" <%if(asBean.getAs_type().equals("2")){out.println("checked");}%>>긴급 SMS 발송</li> --%>
									<li style="padding-right:20px;"><input name="as_type" type="radio" value="3" onclick="asTypeCheck(this.value);" <%if(asBean.getAs_type().equals("3")){out.println("checked");}%>>R-rimi 발송</li>
									<li style="padding-right:20px;"><input name="as_type" type="radio" value="4" onclick="asTypeCheck(this.value);"<%if(asBean.getAs_type().equals("4")){out.println("checked");}%>>포탈TOP 발송</li>
									<%-- <li style="padding-right:20px;"><input name="as_type" type="radio" value="5" onclick="asTypeCheck(this.value);"<%if(asBean.getAs_type().equals("5")){out.println("checked");}%>>트위터 전용 R-rimi 발송</li> --%>
									<li style="padding-right:20px;"><input name="as_type" type="radio" value="6" onclick="asTypeCheck(this.value);"<%if(asBean.getAs_type().equals("6")){out.println("checked");}%>>포탈댓글전용알리미</li>
								</ul>		
								</td>
							</tr>
							<%-- <tr>
								<th><span class="board_write_tit">발송정보</span></th>
								<td>
								<ul>
									<li style="padding-right:20px;"><input name="as_infotype" type="hidden" value="1" onclick="asInfoTypeCheck(this.value)" <%if(asBean.getAs_infotype().equals("1")){out.println("checked");}%>>일반수집</li>
								</ul>		
								</td>
							</tr> --%>
							<tr>
								<th><span class="board_write_tit">정보 발송 여부</span></th>
								<td>
								<ul>
									<li style="padding-right:20px;"><input name="as_chk" type="radio" value="1" <%if(asBean.getAs_chk().equals("1")){out.println("checked");} %>>발송</li>
									<li style="padding-right:20px;"><input name="as_chk" type="radio" value="2" <%if(asBean.getAs_chk().equals("2")){out.println("checked");} %>>발송중지</li>
								</ul>		
								</td>
							</tr>
							<tr id="sender_mail" class="ex_top">
								<th><span class="board_write_tit">발신자 메일</span></th>
								<td>
									<select name="sendMail" >
										<option value="">선택하세요</option>
										<%
										String tmp[] = new String[3];
										ArrayList mail = new ArrayList(); 
										mail = asDao.getSendMailSetting();
										if(mail.size() > 0 ){
											for(int i=0; i <mail.size(); i++){
												tmp = (String[])mail.get(i);
										%>
											 <option value="<%=tmp[0]%>"<%if(tmp[1].equals(asBean.getAs_seq())){out.println("selected");} %>><%=tmp[2]%></option>
										<%	}
										}
										%>
									</select>
								</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
						<td style="padding-top:15px;height:45px;"><span class="sub_tit">발송 조건</span></td>
					</tr>
					<tr>
						<td>
						<table id="board_write" border="0" cellspacing="0">
							<tr class="ex_top">
								<th><span class="board_write_tit">키워드그룹</span></th>
								<td>
<%
	String[] xp = asBean.getKg_xps().split(",");
	for( int i=0 ; i < xpList.size() ; i++ ) {
		keywordInfo KGset = (keywordInfo)xpList.get(i);
%>
									<div style="width:150px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;float:left" title="<%=KGset.getK_Value()%>"><input type="radio" name="k_xp" value="<%=KGset.getK_Xp()%>" onclick="changeTopKeywordGroup();" <%for(int j=0; j < xp.length; j++){if( xp[j].equals(KGset.getK_Xp())){out.println("checked");}}%>><%=KGset.getK_Value()%></div>
<%	}	%>

								</td>
							</tr>
							<tr id="subKewyrodGroupView">
							</tr>
							<tr class="ex_top">
								<th><span class="board_write_tit">사이트 그룹</span></th>
								<td>
							      <input type="checkbox" name="sg_seqALL"  value="0" onclick="checkAllSgSeq(this.checked);">전체
<%
	String[] site = asBean.getSg_seqs().split(",");
	for( int i=0 ; i < sgList.size() ; i++ ) {
		SitegroupBean SGinfo = (SitegroupBean)sgList.get(i);
%>		
							      <input type="checkbox" name="sg_seq" value="<%=SGinfo.get_seq()%>" <%for( int j=0; j < site.length; j++){if(site[j].equals(Integer.toString(SGinfo.get_seq()))){out.println("checked");}else{out.println("");}}%>><%= SGinfo.get_name()%>
<%	}	%>
								</td>
							</tr>
							<tr class="ex_top">
								<th><span class="board_write_tit">사이트 리스트</span></th>
								<td class="normal" ><img src="../../../images/admin/alimi/btn_alrimiadd.gif" onclick="popSiteList('<%=as_seq%>');" style="cursor:pointer;"/>
								<span id="siteListCnt" style="vertical-align: middle;"><%=sdGsnCnt+"개의 사이트가 저장 되었습니다."%></span>
								</td>
							</tr>
							<tr class="ex_top">
								<th><span class="board_write_tit">정보유형</span></th>
								<td>
								<ul>
									<li style="padding-right:20px;"><input type="checkbox" name="mt_type" value="1" <%=mtTypeCheck1%>>기사</li>
									<li><input type="checkbox" name="mt_type" value="2" <%=mtTypeCheck2%>>게시물</li>
								</ul> 
								</td>
							</tr>
							<tr id="smsTimeFilter1" style="display:<%if(asBean.getAs_type().equals("1")){out.println("none");}else{out.println("");}%>">
								<th><span class="board_write_tit">발송시간</span></th>
								<td>
									<input name="as_sms_time1" type="checkbox" value="1" <%=smsTimeCheck1%>>평일 근무시간 ( 06:00 ~ 22:00 )<br>
									<input name="as_sms_time1" type="checkbox" value="2" <%=smsTimeCheck2%>>평일 근무외시간  ( 22:00 ~ 명일 06:00 )<br>
									<input name="as_sms_time1" type="checkbox" value="3" <%=smsTimeCheck3%>>주말 근무시간1 ( 06:00 ~ 24:00 )<br>
									<input name="as_sms_time1" type="checkbox" value="4" <%=smsTimeCheck4%>>주말 근무시간2 ( 09:00 ~ 21:00 )<br>
									<input name="as_sms_time1" type="checkbox" value="5" <%=smsTimeCheck5%>>주말 근무외시간 ( 22:00 ~ 명일 06:00 )<br>
								</td>
							</tr>
							<tr>
								<th><span class="board_write_tit">발송간격</span></th>
								<td>
								<ul id="intervalFilter1" style="display:<%=styleDisplay1%>">
									<li style="padding-right:20px;"><input type="radio" name="as_interval1" value="0" <%if(asBean.getAs_interval().equals("0")){out.println("checked");} %>>건별</li>
									<li style="padding-right:20px;"><input type="radio" name="as_interval1" value="30" <%if(asBean.getAs_interval().equals("30")){out.println("checked");} %>>30분</li>
									<li style="padding-right:20px;"><input type="radio" name="as_interval1" value="60" <%if(asBean.getAs_interval().equals("60")){out.println("checked");} %>>1시간</li>
									<li style="padding-right:20px;"><input type="radio" name="as_interval1" value="180" <%if(asBean.getAs_interval().equals("180")){out.println("checked");} %>>3시간</li>
								</ul> 
								<ul id="intervalFilter2" style="display:<%=styleDisplay2%>">
									<li style="padding-right:20px;"><input type="radio" name="as_interval2" value="0" checked>건별</li>
								</ul> 
								</td>
							</tr>
							<tr id="as_same">
								<th><span class="board_write_tit"><label></label></span></th>
								<td>
									<label></label>
								</td>
							</tr>
							<tr id="tr_later" style="display: <%if(asBean.getAs_type().equals("1")){out.println("none");}%>;">
								<th><span class="board_write_tit">시간외 알람</span></th>
								<td>
								<ul>
									<li style="padding-right:20px;"><input name="as_later_send1" type="radio" value="Y" <%if(asBean.getAs_later_send().equals("Y")){out.println("checked");} %>>몰아서받기</li>
									<li style="padding-right:20px;"><input name="as_later_send1" type="radio" value="N" <%if(asBean.getAs_later_send().equals("N")){out.println("checked");} %>>받지않기</li>
								</ul>
								</td>
							</tr>
							<tr id="tr_monitor_use" style="display: <%if(asBean.getAs_type().equals("1")){out.println("none");}%>;">
								<th><span class="board_write_tit">감시SMS발송</span></th>
								<td>
								<ul>
									<li style="padding-right:20px;"><input name="as_monitor_use1" type="radio" value="Y" onclick="monitorView(this)" <%if(asBean.getAs_monitor_use().equals("Y")){out.println("checked");} %>checked>발송</li>
									<li style="padding-right:20px;"><input name="as_monitor_use1" type="radio" value="N" onclick="monitorView(this)" <%if(asBean.getAs_monitor_use().equals("N")){out.println("checked");} %>>미발송</li>
								</ul>		
								</td>
							</tr>
							<tr id="tr_monitor_cnt_info" style="display: <%if(!asBean.getAs_monitor_use().equals("Y")){out.println("none");} %>;">
								<th><span class="board_write_tit">미발송대기시간</span></th>
								<td>
									<input type="text" style="text-align: right;" maxlength="4" size="5" id="as_monitor_max_m00" onkeydown="return onlyNumber(event,1)" onkeyup="removeChar(event,1)" value="<%=asBean.getAs_monitor_max_m() %>">분 이상부터
									<input type="text" style="text-align: right;" maxlength="4" size="5" id="as_monitor_repeat_m00"  onkeydown="return onlyNumber(event,1)" onkeyup="removeChar(event,1)" value="<%=asBean.getAs_monitor_repeat_m() %>">분 마다발송
								</td>
							</tr>
							<tr id="tr_monitor_inspector" style="display: <%if(!asBean.getAs_monitor_use().equals("Y")){out.println("none");} %>;">
								<th><span class="board_write_tit">감시SMS수신자</span></th>
								<td>
									<input style="width:60%;" type="text" style="text-align: right;" id="as_monitor_inspector00" value="<%=asBean.getAs_monitor_inspector()%>">
									<div style="margin-top: 5px; margin-bottom: 1px; font-size: 90%" > * 다수일 경우 세미콜론(;)처리 ex) 010-1234-4567;010-4567-8912</div>		
								</td>
							</tr>
							
							<%-- <tr>
								<th><span class="board_write_tit">제목 포함 필터</span></th>
								<td>
									<input name="as_sms_key" type="text" class="txtbox" size="50" value="<%=asBean.getAs_sms_key()%>">
								</td>
							</tr>
							<tr>
								<th><span class="board_write_tit">제목 제외 필터</span></th>
								<td>
									<input name="as_sms_exkey" type="text" class="txtbox" size="50" value="<%=asBean.getAs_sms_exkey()%>">
								</td>
							</tr> --%>
							
						</table>
<%-- 
<table id="smsFilter" <%if(asBean.getAs_infotype().equals("1")){out.println("style=\"display='none'\"");}%> width="630" border="0" cellspacing="1" cellpadding="0">     
	<tr>
		<td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>제목 포함 필터</strong></td>
		<td width="510" style="padding: 5px 0px 5px 10px;"></td>
	</tr>
	<tr>
		<td colspan="2"  bgcolor="#D3D3D3" width="1" height="1"></td>
	</tr>
	<tr>
		<td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>제목 제외 필터</strong></td>
		<td width="510" style="padding: 5px 0px 5px 10px;"><input name="as_sms_exkey" type="text" class="txtbox" size="50" value="<%=asBean.getAs_sms_exkey()%>"></td>
	</tr>
	<tr>
		<td colspan="2"  bgcolor="#D3D3D3" width="1" height="1"></td>
	</tr>
	<tr id="smsTimeFilter2" style="display:<%if(asBean.getAs_type().equals("2")){out.println("");}else{out.println("none");}%>">			      
		<td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>발송 시간</strong></td>
		<td width="510" style="padding: 5px 0px 5px 10px;">
			<input name="as_sms_time2" type="checkbox" value="1" <%=smsTimeCheck1%>>평일 근무시간 ( 06:00 ~ 22:00 )<br>
			<input name="as_sms_time2" type="checkbox" value="2" <%=smsTimeCheck2%>>평일 근무외시간  ( 22:00 ~ 명일 06:00 )<br>
			<input name="as_sms_time2" type="checkbox" value="3" <%=smsTimeCheck3%>>주말 근무시간1 ( 06:00 ~ 24:00 )<br>
			<input name="as_sms_time2" type="checkbox" value="4" <%=smsTimeCheck4%>>주말 근무시간2 ( 09:00 ~ 21:00 )<br>
			<input name="as_sms_time2" type="checkbox" value="5" <%=smsTimeCheck5%>>주말 근무외시간 ( 22:00 ~ 명일 06:00 )  		
		</td>
	</tr>
	<tr>
		<td colspan="2"  bgcolor="#D3D3D3" width="1" height="1"></td>
	</tr>		       
	<tr>
		<td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>발송간격</strong></td>
		<td width="510" style="padding: 5px 0px 5px 10px;">		  		 	
		<table id="intervalFilter3" width="510" border="0" cellspacing="0" cellpadding="0" style="display:<%=styleDisplay3%>">
			<tr>
				<td>
					<input type="radio" name="as_interval3" value="0"  <%if(asBean.getAs_interval().equals("0")){out.println("checked");} %>>건별&nbsp;&nbsp;
			  		<input type="radio" name="as_interval3" value="30" <%if(asBean.getAs_interval().equals("30")){out.println("checked");} %>>30분&nbsp;&nbsp;
				  	<input type="radio" name="as_interval3" value="60" <%if(asBean.getAs_interval().equals("60")){out.println("checked");} %>>1시간&nbsp;&nbsp;
				  	<input type="radio" name="as_interval3" value="180" <%if(asBean.getAs_interval().equals("180")){out.println("checked");} %>>3시간&nbsp;&nbsp;		
				</td>
			</tr>
		</table>
		<table id="intervalFilter4" width="510" border="0" cellspacing="0" cellpadding="0" style="display:<%=styleDisplay4%>">
			<tr>
				<td>
					<input type="radio" name="as_interval4" value="0" checked>건별&nbsp;&nbsp;	
				</td>
			</tr>
		</table>		  		 			  		 	
		</td>
	</tr>
</table> --%>
						</td>
					</tr>
				</table>
				<iframe name="frmReceiver" src="ifrm_receiver_list.jsp?ab_seq=<%=ab_seq%>" frameborder="0" scrolling="no" width="820" height="200"></iframe>
				</td>
			</tr>
			<!-- 게시판 끝 -->
			<tr>
				<td style="height:40px;" align="center"><img src="../../../images/admin/alimi/btn_save2.gif" onclick="save();" style="cursor:pointer;"/><img src="../../../images/admin/alimi/btn_cancel.gif" onclick="goList();" style="cursor:pointer;"/></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<%
		}
	} 
%>
</form>
</body>
</html>