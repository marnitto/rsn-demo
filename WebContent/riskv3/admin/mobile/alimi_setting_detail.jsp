<%@ page contentType = "text/html; charset=euc-kr"  pageEncoding="EUC-KR"%>
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
					" 
%>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
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
	//일반 키워드 그룹
	List xpList = null;
	
	//포털 키워드 그룹
	List xpList2 = null;
	
	List sgList = null;
	
//	String[] smsTime = null;
//	String smsTimeCheck1 =null;
//	String smsTimeCheck2 =null;
//	String smsTimeCheck3 =null;
	
	String[] mtType = null;
	String mtTypeCheck1 = null;
	String mtTypeCheck2 = null;
	
	//pr.printParams();
	String[] sdGsnList = null;
	
	String smsDay = "";
	int smsStime = -1;
	int smsEtime = -1;
	int sameCnt = 0;
	
	int sdGsnCnt = 0;
	
	if(mode.equals("UPDATE") && as_seq.length()>0)
	{
		ab_seq = asDao.getReceiverSeq(as_seq);
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
//		smsTimeCheck1 ="";
//		smsTimeCheck2 ="";
//		smsTimeCheck3 ="";
		
		
		if(asBean.getAs_sms_day()!=null && !asBean.getAs_sms_day().trim().equals("")){
			smsDay = asBean.getAs_sms_day();
		}
		if(asBean.getAs_sms_stime()!=null && !asBean.getAs_sms_stime().trim().equals("")){
			smsStime = Integer.parseInt(asBean.getAs_sms_stime());
		}
		if(asBean.getAs_sms_stime()!=null && !asBean.getAs_sms_stime().trim().equals("")){
			smsEtime = Integer.parseInt(asBean.getAs_sms_etime());
		}
			sameCnt = asBean.getAs_same_cnt();
		
		
//		if(asBean.getAs_sms_time()!=null && !asBean.getAs_sms_time().equals(""))
//		{
//			smsTime = asBean.getAs_sms_time().split(",");
//			
// 			if(smsTime!=null)
//			{
// 				for(int i=0;i<smsTime.length;i++)
//  				{
//  					if(smsTime[i].equals("1"))smsTimeCheck1="checked";
//  					if(smsTime[i].equals("2"))smsTimeCheck2="checked";
//  					if(smsTime[i].equals("3"))smsTimeCheck3="checked";
//  				}
//  			}
//		}
		
		mtTypeCheck1 = "";
		mtTypeCheck2 = "";
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
	xpList = uemng.getKeywordGroup();
	
	//pKeywordMng pkMng = new pKeywordMng();
	//xpList2 = pkMng.getKgroupListX();
	
	
	SiteMng sitemng = new SiteMng();
	sgList = sitemng.getSGList();
	
	
%>
<html>
<head>
<title><%=SS_TITLE %></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="../css/basic.css" type="text/css">

<SCRIPT TYPE="text/javascript" src="/js/dojo.js"></SCRIPT>
<SCRIPT TYPE="text/javascript" src="/js/prototype-1.6.0.3.js"></SCRIPT>
<SCRIPT TYPE="text/javascript" src="/js/json2.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript">

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
	
	var asInterval;

	asInterval = checkedIntervalValue('as_interval1');
	
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
		if(asType==1 || asType==6)
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
function checkAllMtType(chk) {
	var o=document.all.mt_type;
	for(i=0; i<o.length; i++) {
		o(i).checked = chk;
	}
}
function checkKxpValue(){
	var f =  document.alimi_detail;
	var check = false;
	f.k_xps.value='';
	for( var i = 0; i<f.k_xp.length; i++)
	{
			
		if(f.k_xp[i].checked)
		{			
			if(f.k_xps.value=='')
			{
				f.k_xps.value =f.k_xp[i].value;
			}else{
				f.k_xps.value = f.k_xps.value+','+ f.k_xp[i].value;
			}			
		}
	}
	if(f.k_xps.value.length>0)check =true;
	return check;
		
}

function checkAllPKxp(chk) {
	var o=document.all.pk_xp;
	var f =  document.alimi_detail;
	if(f.pk_xp.length)
	{
		for(i=0; i<o.length; i++) {
			o(i).checked = chk;
		}
	}else{
		o.checked = chk;
	}
}
function checkPKxpValue(){
	var f =  document.alimi_detail;
	var check = false;
	f.k_xps.value='';
	if(f.pk_xp.length)
	{
		for( var i = 0; i<f.pk_xp.length; i++)
		{
				
			if(f.pk_xp[i].checked)
			{			
				if(f.k_xps.value=='')
				{
					f.k_xps.value =f.pk_xp[i].value;
				}else{
					f.k_xps.value = f.pk_xps.value+','+ f.pk_xp[i].value;
				}			
			}
		}
	}else{
		f.k_xps.value =f.pk_xp.value;
	}
	if(f.k_xps.value.length>0)check =true;
	return check;
		
}

function checkAllSgSeq(chk) {
	var o=document.all.sg_seq;
	for(i=0; i<o.length; i++) {
		o(i).checked = chk;
	}
}
	
function checkAllDay1(chk) {
	var o=document.all.as_sms_day1;
	for(i=0; i<o.length; i++) {
		o(i).checked = chk;
	}	
}

function checkDay1() {
	var o=document.all.as_sms_day1;
	var chk = false;
	var j=0;
		for(i=0; i<o.length; i++) {
			if(o(i).checked == true){ j++;}
			else{j--;}
		}
		if(j==o.length){ chk = true;}
		else{chk = false;}
	
	document.all.as_sms_dayAll1.checked=chk;	
}
function checkDay2() {
	var o=document.all.as_sms_day2;
	var chk = false;
	var j=0;
		for(i=0; i<o.length; i++) {
			if(o(i).checked == true){ j++;}
			else{j--;}
		}
		if(j==o.length){ chk = true;}
		else{chk = false;}
	
	document.all.as_sms_dayAll2.checked=chk;	
}

function checkAllDay2(chk) {
	var o=document.all.as_sms_day2;
	for(i=0; i<o.length; i++) {
		o(i).checked = chk;
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
}

function checkSmsDayValue1(){
	var f =  document.alimi_detail;
	var check = false;
	f.as_smsday.value='';
	for( var i = 0; i<f.as_sms_day1.length; i++)
	{
			
		if(f.as_sms_day1[i].checked)
		{			
			if(f.as_smsday.value=='')
			{
				f.as_smsday.value =f.as_sms_day1[i].value;
			}else{
				f.as_smsday.value = f.as_smsday.value+','+ f.as_sms_day1[i].value;
			}			
		}
	}
	if(f.as_smsday.value.length>0)check =true;
	return check;
		
}

function checkSmsDayValue2(){
	var f =  document.alimi_detail;
	var check = false;
	f.as_smsday.value='';
	for( var i = 0; i<f.as_sms_day2.length; i++)
	{
			
		if(f.as_sms_day2[i].checked)
		{			
			if(f.as_smsday.value=='')
			{
				f.as_smsday.value =f.as_sms_day2[i].value;
			}else{
				f.as_smsday.value = f.as_smsday.value+','+ f.as_sms_day2[i].value;
			}			
		}
	}
	if(f.as_smsday.value.length>0)check =true;
	return check;
		
}

function asTypeCheck(objValue)
{
	var f = document.alimi_detail;
	var asType = objValue;

	if(asType == "4" || asType == "5"){
		document.getElementById("sendType").style.display='none';
		document.getElementById("infoFilter").style.display='none';
		document.getElementById("infoFilter2").style.display='none';
		f.as_infotype[1].disabled = false;
		
	}else{
		document.getElementById("infoFilter").style.display='';
		document.getElementById("infoFilter2").style.display='';
		document.getElementById("sendType").style.display='';
		var asInfoType = checkAsInfoTypeValue();
	
		f.as_infotype[1].disabled = false;
		if(asInfoType==1) //일반수집
		{
			document.getElementById("intervalFilter3").style.display='none';
			document.getElementById("intervalFilter4").style.display='none';
			if(asType==1 || asType==6)//메일
			{
				document.getElementById("intervalFilter1").style.display='';
				document.getElementById("intervalFilter2").style.display='none';
				document.getElementById("smsTimeFilter1").style.display='none';
				document.getElementById("smsTimeFilter2").style.display='none';
				document.getElementById("smsSameFilter").style.display='none';			
				document.getElementById("smsSameFilter1").style.display='none';			
			}else{//SMS, MMS
				if(asType==7 || asType==8){
					document.getElementById("intervalFilter1").style.display='';
					document.getElementById("intervalFilter2").style.display='none';
					f.as_interval1[0].disabled = true;
					f.as_infotype[0].checked = true;
					f.as_infotype[1].disabled = true;
					
				}else{
					f.as_interval1[1].checked = true;
					f.as_interval1[0].disabled = false;
					document.getElementById("intervalFilter1").style.display='none';
					document.getElementById("intervalFilter2").style.display='';
				}
				document.getElementById("smsTimeFilter1").style.display='';	
				document.getElementById("smsTimeFilter2").style.display='none';
				document.getElementById("smsSameFilter1").style.display='';				
			}
			if(asType==3 ){
				f.as_infotype[0].checked = true;
				f.as_infotype[1].disabled = true;
				asInfoTypeCheck(1);
			}
			
			if(asType==7 || asType==8 ){
				document.getElementById("sameLimit").innerHTML = '유사 증가';
				document.getElementById("sameLimit2").innerHTML = '';
				document.getElementById("sendInterval").innerHTML = '유사 간격';
			}else{
				document.getElementById("sendInterval").innerHTML = '발송 간격';
				document.getElementById("sameLimit").innerHTML = '유사 제한';
				document.getElementById("sameLimit2").innerHTML = '제한';
			}
				
		}else{
			document.getElementById("intervalFilter3").style.display='';
			document.getElementById("intervalFilter4").style.display='';		
			if(asType==1 || asType==6)
			{
				document.getElementById("intervalFilter1").style.display='none';
				document.getElementById("intervalFilter2").style.display='';
				document.getElementById("intervalFilter3").style.display='';
				document.getElementById("intervalFilter4").style.display='none';
				document.getElementById("smsTimeFilter1").style.display='none';
				document.getElementById("smsTimeFilter2").style.display='none';
				document.getElementById("smsSameFilter").style.display='none';		
			}else{
				document.getElementById("intervalFilter1").style.display='';
				document.getElementById("intervalFilter2").style.display='none';
				document.getElementById("intervalFilter3").style.display='none';
				document.getElementById("intervalFilter4").style.display='';
				document.getElementById("smsTimeFilter1").style.display='none';
				document.getElementById("smsTimeFilter2").style.display='';	
				document.getElementById("smsSameFilter").style.display='';
			}
			if(asType==3){
				f.as_infotype[0].checked = true;
				f.as_infotype[1].disabled = true;
				asInfoTypeCheck(1);
			}
		}
	}
}

function asInfoTypeCheck(objValue)
{
	var f = document.alimi_detail;
	var asType = checkAsTypeValue();
	var asInfoType = objValue;	
	if(asInfoType==1)  //일반 수집
	{
		document.getElementById("smsFilter").style.display='none';		
		document.getElementById("mailFilter").style.display='';		
		if(asType==1 || asType==6) //메일
		{
			document.getElementById("intervalFilter1").style.display='';
			document.getElementById("intervalFilter2").style.display='none';
			document.getElementById("intervalFilter3").style.display='none';
			document.getElementById("intervalFilter4").style.display='none';
			document.getElementById("smsTimeFilter1").style.display='none';	
			document.getElementById("smsTimeFilter2").style.display='none';
			document.getElementById("smsSameFilter1").style.display='none';
			
		}else{ // SMS, MMS
			document.getElementById("intervalFilter1").style.display='none';
			document.getElementById("intervalFilter2").style.display='';
			document.getElementById("intervalFilter3").style.display='none';
			document.getElementById("intervalFilter4").style.display='none';
			document.getElementById("smsTimeFilter1").style.display='';	
			document.getElementById("smsTimeFilter2").style.display='none';	
			document.getElementById("smsSameFilter1").style.display='';	
		}
		//document.getElementById("smsTimeFilter").style.display ='';
		
	}else{ //포털 메인
		document.getElementById("mailFilter").style.display='none';	
		document.getElementById("smsFilter").style.display='';
		if(asType==1 || asType==6)
		{
			document.getElementById("intervalFilter1").style.display='none';
			document.getElementById("intervalFilter2").style.display='none';
			document.getElementById("intervalFilter3").style.display='';
			document.getElementById("intervalFilter4").style.display='none';
			document.getElementById("smsTimeFilter1").style.display='none';
			document.getElementById("smsTimeFilter2").style.display='none';
			document.getElementById("smsSameFilter").style.display='none';
		}else{
			document.getElementById("intervalFilter1").style.display='none';
			document.getElementById("intervalFilter2").style.display='none';
			document.getElementById("intervalFilter3").style.display='none';
			document.getElementById("intervalFilter4").style.display='';
			document.getElementById("smsTimeFilter1").style.display='none';
			document.getElementById("smsTimeFilter2").style.display='';
			document.getElementById("smsSameFilter").style.display='';
		}
		//document.getElementById("smsTimeFilter").style.display ='';
		
	}
}


function save()
{
	var f = document.alimi_detail;
	var asInfoType = "1"; //일반수집전용
	var checkValue = false;
	var asType = checkAsTypeValue();
	
			
	f.as_interval.value=checkAsIntervalValue();		
	if(f.as_title.value.length==0){alert('제목을 입력하여 주십시오.'); f.as_title.focus(); return;}
	
	checkValue=checkKxpValue();
	if(checkValue==false){alert('키워드 그룹을 체크하여주십시오.'); return;}

	checkValue=checkSgSeqValue();
	if(checkValue==false && f.sd_gsns.value.length==0){alert('하나의 사이트 그룹이나 사이트를 지정해주십시오.'); return;}		

	checkValue=checkMtTypeValue();
	if(checkValue==false){alert('정보유형을 그룹을 체크하여주십시오.'); return;}

	checkAbAeqValue();
	//2009-07-08 포털 키워드 변경으로 삭제
	//f.as_sms_key.value='';
	//f.as_sms_exkey.value='';

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

function popSiteList()
{
	var f = document.alimi_detail;
	if(f.sd_gsns.value.length>0)
	{
		window.open("pop_sitelist.jsp?selectedSdGsn="+f.sd_gsns.value, "siteList", "width=850,height=600,scrollbars=no");
	}else{
		window.open("pop_sitelist.jsp", "siteList", "width=850,height=600,scrollbars=no");
	}	
}
		

</SCRIPT>
</head>
<body leftmargin="10" topmargin="10" marginwidth="0" marginheight="0" onload="">
<form name="alimi_detail" method="post">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="nowpage" value="<%=nowpage%>">
<input type="hidden" name="as_seq" value="<%=as_seq%>">
<input type="hidden" name="ab_seqs">
<input type="hidden" name="as_sms_time">
<input type="hidden" name="as_sms_stime">
<input type="hidden" name="as_sms_etime">
<input type="hidden" name="as_smsday">
<input type="hidden" name="as_interval">
<input type="hidden" name="k_xps">
<input type="hidden" name="as_sitetype">
<input type="hidden" name="sg_seqs">
<input type="hidden" name="mt_types">
<input type="hidden" name="as_same_cnt">


<table width="780" border="0" cellspacing="0" cellpadding="0">

  <tr>
    <td background="../images2/top_title_bg.gif"><img src="../images/admin_title_alimi.gif" width="157" height="42"></td>
    <td align="right" background="../images2/top_title_bg.gif" class="menu_gray" style="padding: 0px 10px 0px 0px;">수신할 대상 및 수신조건을 관리합니다.</td>
  </tr>
  <tr>
    <td colspan="2"><img src="../images2/brank.gif" width="1" height="15"></td>
  </tr>
</table>
<%if(mode.equals("INSERT")){ %>
<input type="hidden" name="sd_gsns">    
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding: 0px 0px 0px 10px;">

	    <table width="750" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="25" colspan="2"><table width="600" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td width="15"><img src="../images2/ico_won_blue.gif" width="11" height="11"></td>
	              <td class="BIG_title02" style="padding: 2px 0px 0px 0px;"><strong>기본 설정</strong></td>
	            </tr>
	        </table></td>
	      </tr>	      
	      <tr>
	        <td colspan="2" bgcolor="#9CBBE5"><img src="../images2/brank.gif" width="1" height="2"></td>
	      </tr>
	      <tr>
	        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>제목</strong></td>
		  	<td width="630" style="padding: 5px 0px 5px 10px;"><input name="as_title" type="text" class="txtbox" size="50"></td>
	      </tr>
	      <tr>
	        <td colspan="2" bgcolor="#D3D3D3"><img src="../images2/brank.gif" width="1" height="1"></td>
	      </tr>	      
	      <tr>
	        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>발송매체</strong></td>
		  	<td width="630" style="padding: 5px 0px 5px 10px;">
		  	<input name="as_type" type="radio" onclick="asTypeCheck(this.value);" value="6" checked>모바일앱

		  	</td>
	      </tr>
	      <tr>
	        <td colspan="2" bgcolor="#D3D3D3"><img src="../images2/brank.gif" width="1" height="1"></td>
	      </tr>
	   
	      <tr>
	        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>정보 발송 여부</strong></td>
		  	<td width="630" style="padding: 5px 0px 5px 10px;"><input name="as_chk" type="radio" value="1" checked>발송 <input name="as_chk" type="radio" value="2">발송 중지<td>
	      </tr>
	      <tr>
	        <td colspan="2" bgcolor="#D3D3D3"><img src="../images2/brank.gif" width="1" height="1"></td>
	      </tr>
	      <tr id="sendType">
	        <td width="120" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>발송조건</strong></td>	        
	        <td>
	      	  <table id="mailFilter" width="630" border="0" cellspacing="1" cellpadding="0">     
			      
			      <tr>
			        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>키워드그룹</strong></td>
			        <td width="510" style="padding: 5px 0px 3px 10px;">
					
						<table width="510" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="510" height="25">
									<input type="checkbox" name="k_xpALL" onclick="checkAllKxp(this.checked);"> <B>전체</B>
						    </td>
			
							</tr>
							<tr>
								<td width="510" height="25">
									<table width=100% cellpadding=0 cellspacing=0 border="0">
			          
							 <%
									int intUubun = 3;												
									for( int i=0 ; i < xpList.size() ; i++ ) {
										keywordInfo KGset = (keywordInfo)xpList.get(i);
										System.out.println("KGset.getK_Xp:"+KGset.getK_Xp());
										
										if(i == 0) out.println("<tr height=22>");
			
										if(i>0)
											if((i%intUubun)==0) out.println("<tr height=22>");
										
									%>	<td width="<%=600/intUubun%>">
										<input type="checkbox" name="k_xp" value="<%=KGset.getK_Xp()%>"><%=KGset.getK_Value()%>
										</td>
									<%
			
										if((i%intUubun)==(intUubun-1)) out.println("</tr>");
									
									}
			
									if((xpList.size() % intUubun) > 0){
										out.println("<td colspan='"+ xpList.size() % intUubun+"'></td></tr>");
									}
							%>		
									</table>
								</td>
							</tr>
						</table>
					</td>
				 </tr>
				 
				  <tr>
			        <td colspan="2"  bgcolor="#D3D3D3"><img src="../images2/brank.gif" width="1" height="1"></td>
			      </tr>		
		
			      <tr>
			        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>사이트 그룹</strong></td>
			        <td width="510" style="padding: 5px 0px 3px 10px;">
					
					
						<table width="510" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="510" height="25">
									<input type="checkbox" name="sg_seqALL"  value="0" onclick="checkAllSgSeq(this.checked);"><B>전체</B>
								</td>
							</tr>
							<tr>
								<td width="510" height="25">
									<table width=100% cellpadding=0 cellspacing=0 border="0">
							  <%							
			
								intUubun = 3;
								for( int i=0 ; i < sgList.size() ; i++ ) {
									SitegroupBean SGinfo = (SitegroupBean)sgList.get(i);
			
									if(i == 0) out.println("<tr height=22>");
			
										if(i>0)
											if((i%intUubun)==0) out.println("<tr height=22>");
										
								%>		
										<td width="<%=600/intUubun%>">
									      <input type="checkbox" name="sg_seq" value="<%=SGinfo.get_seq()%>"><%= SGinfo.get_name()%>
									    </td>
							   <%				
									if((i%intUubun)==(intUubun-1)) out.println("</tr>");				  
								}
			
								if((sgList.size() % intUubun) > 0){
									out.println("<td colspan='"+ sgList.size() % intUubun+"'></td></tr>");
								}
			
							   %>
							  		</table>
								</td>
							</tr>
						</table></td>						
			      </tr>
				  <tr>
			        <td colspan="2"  bgcolor="#D3D3D3"><img src="../images2/brank.gif" width="1" height="1"></td>
			      </tr>		
		
			      <tr>
			        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>정보유형</strong></td>
			        <td width="510" style="padding: 5px 0px 5px 10px;"><table width="500" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			             <tr>
				            <td height="20">				    
				              <input type="checkbox" name="mt_type_all" value="0" onclick="checkAllMtType(this.checked);"><B>전체</B>&nbsp;&nbsp;       			            
				              <input type="checkbox" name="mt_type" value="1">기사&nbsp;&nbsp;
				      		  <input type="checkbox" name="mt_type" value="2">게시물
				      	    </td>		      		
			          </tr>
			        </table>
			     </td>
			   </tr>
		       
		       
		       <tr>
		         <td colspan="2"  bgcolor="#D3D3D3"><img src="../images2/brank.gif" width="1" height="1"></td>
		       </tr>		       
			   <tr>
		        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong><div id="sendInterval">발송간격</div></strong></td>
			  	<td  width="510" style="padding: 5px 0px 5px 10px;">
			  		<table id="intervalFilter1" width="510" border="0" cellspacing="0" cellpadding="0">
				  		<tr><td>
				  		<input type="radio" name="as_interval1" value="0" checked>건별&nbsp;&nbsp;
				  		<input type="radio" name="as_interval1" value="30">30분&nbsp;&nbsp;
				  		<input type="radio" name="as_interval1" value="60">1시간&nbsp;&nbsp;
				  		<input type="radio" name="as_interval1" value="180">3시간&nbsp;&nbsp;	
				  		<input type="radio" name="as_interval1" value="300">5시간&nbsp;&nbsp;	
				  		</td></tr>
			  		</table>
			  		<table id="intervalFilter2" width="510" border="0" cellspacing="0" cellpadding="0" style="display:none">
				  		<tr><td>
				  		<input type="radio" name="as_interval2" value="0" checked>건별&nbsp;&nbsp;	
				  		</td></tr>
			  		</table>		  		
			  	</td>			  	
		       </tr>
		       
		            
      		</table>     		
      	</td>
      </tr>
      <tr>
        <td colspan="2" bgcolor="#9CBBE5"><img src="../images2/brank.gif" width="1" height="2"></td>
      </tr>      
    </table>
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<iframe name="frmReceiver" src="ifrm_receiver_list.jsp" frameborder="0" scrolling="no" width="810" height="250"></iframe>

<%
	}else if(mode.equals("UPDATE")){
	if(asBean!=null)
	{
		
%>

<input type="hidden" name="sd_gsns" value="<%if(asBean.getSd_gsns()!=null){out.println(asBean.getSd_gsns());}%>"> 
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding: 0px 0px 0px 10px;">
    
	    <table width="750" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="25" colspan="2"><table width="600" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td width="15"><img src="../images2/ico_won_blue.gif" width="11" height="11"></td>
	              <td class="BIG_title02" style="padding: 2px 0px 0px 0px;"><strong>기본 설정</strong></td>
	            </tr>
	        </table></td>
	      </tr>	     
	      <tr>
	        <td colspan="2" bgcolor="#9CBBE5"><img src="../images2/brank.gif" width="1" height="2"></td>
	      </tr>
	      <tr>
	        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>제목</strong></td>
		  	<td width="630" style="padding: 5px 0px 5px 10px;"><input name="as_title" type="text" class="txtbox" size="50" value="<%=asBean.getAs_title() %>"></td>
	      </tr>
	      <tr>
	        <td colspan="2" bgcolor="#D3D3D3"><img src="../images2/brank.gif" width="1" height="1"></td>
	      </tr>
	      <tr>
	        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>발송매체</strong></td>
		  	<td width="630" style="padding: 5px 0px 5px 10px;">
		  	<input name="as_type" type="radio" value="6" <%if(asBean.getAs_type().equals("6")){out.println("checked");}%> onclick="asTypeCheck(this.value);">모바일앱
		  	</td>
	      </tr>
	      <tr>
	        <td colspan="2" bgcolor="#D3D3D3"><img src="../images2/brank.gif" width="1" height="1"></td>
	      </tr>      
	      <tr>
	        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>정보 발송 여부</strong></td>
		  	<td width="510" style="padding: 5px 0px 5px 10px;"><input name="as_chk" type="radio" value="1" <%if(asBean.getAs_chk().equals("1")){out.println("checked");} %>>발송 <input name="as_chk" type="radio" value="2" <%if(asBean.getAs_chk().equals("2")){out.println("checked");} %>>발송 중지</td>
	      </tr>
	      <tr>
	        <td colspan="2" bgcolor="#D3D3D3"><img src="../images2/brank.gif" width="1" height="1"></td>
	      </tr>
	      <tr id="sendType">
	        <td width="120" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>발송조건</strong></td>
	        
	        <td>
	      	  <table id="mailFilter" <%if(asBean.getAs_infotype().equals("2")){out.println("style=\"display='none'\"");}%> width="630" border="0" cellspacing="1" cellpadding="0">     
			      
			      <tr>
			        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>키워드그룹</strong></td>
			        <td width="510" style="padding: 5px 0px 5px 10px;">
					
						<table width="510" border="0" cellspacing="0" cellpadding="0" >
							<tr>
								<td width="510" height="25">
									<input type="checkbox" name="k_xpALL" onclick="checkAllKxp(this.checked);"> <B>전체</B></td>
			
							</tr>
							<tr>
								<td width="510" height="25">
									<table width=100% cellpadding=0 cellspacing=0 border="0">
			          
							  <%
									int intUubun = 3;
							  		String[] xp = null;
							  		if(asBean.getKg_xps()!=null && asBean.getAs_infotype().equals("1"))
							  		{
								  		xp = asBean.getKg_xps().split(",");						
										for( int i=0 ; i < xpList.size() ; i++ ) {
											keywordInfo KGset = (keywordInfo)xpList.get(i);
				
											if(i == 0) out.println("<tr height=22>");
				
											if(i>0)
												if((i%intUubun)==0) out.println("<tr height=22>");
										
									%>	<td width="<%=600/intUubun%>">
										<input type="checkbox" name="k_xp" value="<%=KGset.getK_Xp()%>" 
											<%for(int j=0; j < xp.length; j++){
												if( xp[j].equals(KGset.getK_Xp())){
													out.println("checked");
												}
											}
											%>><%=KGset.getK_Value()%></td><%
			
											if((i%intUubun)==(intUubun-1)) out.println("</tr>");
									
										}
			
										if((xpList.size() % intUubun) > 0){
											out.println("<td colspan='"+ xpList.size() % intUubun+"'></td></tr>");
										}
							  		}else{
							  			for( int i=0 ; i < xpList.size() ; i++ ) {
											keywordInfo KGset = (keywordInfo)xpList.get(i);
											System.out.println("KGset.getK_Xp:"+KGset.getK_Xp());
											
											if(i == 0) out.println("<tr height=22>");
				
											if(i>0)
												if((i%intUubun)==0) out.println("<tr height=22>");
											
										%>	<td width="<%=600/intUubun%>">
											<input type="checkbox" name="k_xp" value="<%=KGset.getK_Xp()%>"><%=KGset.getK_Value()%>
											</td>
										<%
				
											if((i%intUubun)==(intUubun-1)) out.println("</tr>");
										
										}
				
										if((xpList.size() % intUubun) > 0){
											out.println("<td colspan='"+ xpList.size() % intUubun+"'></td></tr>");
										}
							  		}
							%>		
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				 
				  <tr>
			        <td colspan="2"  bgcolor="#D3D3D3"><img src="../images2/brank.gif" width="1" height="1"></td>
			      </tr>		
		
			      <tr>
			        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>사이트 그룹</strong></td>
			        <td width="510" style="padding: 5px 0px 3px 10px;">
					
					
						<table width="510" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="510" height="25">
									<input type="checkbox" name="sg_seqALL" value ="0" onclick="checkAllSgSeq(this.checked);"><B>전체</B>
								</td>
							</tr>
							<tr>
								<td width="510" height="25">
									<table width=100% cellpadding=0 cellspacing=0 border="0">
							  <%
							  	String[] site = null;
							  	if(asBean.getSg_seqs() !=null && asBean.getAs_infotype().equals("1"))
							  	{
							  		site= asBean.getSg_seqs().split(",");
			
									intUubun = 3;
									for( int i=0 ; i < sgList.size() ; i++ ) {
										SitegroupBean SGinfo = (SitegroupBean)sgList.get(i);
				
										if(i == 0) out.println("<tr height=22>");
				
											if(i>0)
												if((i%intUubun)==0) out.println("<tr height=22>");
										
									%>	<td width="<%=600/intUubun%>"><input type="checkbox" name="sg_seq" value="<%=SGinfo.get_seq()%>" 
									<%
										  for( int j=0; j < site.length; j++){
											  if(site[j].equals(Integer.toString(SGinfo.get_seq()))){ 
												  out.println("checked");
											  }else{out.println("");}
										  }					  
								  
								  %>><%= SGinfo.get_name()%></td><%				
										if((i%intUubun)==(intUubun-1)) out.println("</tr>");				  
									}
			
									if((sgList.size() % intUubun) > 0){
										out.println("<td colspan='"+ sgList.size() % intUubun+"'></td></tr>");
									}
							  	}else{
							  		intUubun = 3;
									for( int i=0 ; i < sgList.size() ; i++ ) {
										SitegroupBean SGinfo = (SitegroupBean)sgList.get(i);
				
										if(i == 0) out.println("<tr height=22>");
				
											if(i>0)
												if((i%intUubun)==0) out.println("<tr height=22>");
											
									%>		
											<td width="<%=600/intUubun%>">
										      <input type="checkbox" name="sg_seq" value="<%=SGinfo.get_seq()%>"><%= SGinfo.get_name()%>
										    </td>
								   <%				
										if((i%intUubun)==(intUubun-1)) out.println("</tr>");				  
									}
				
									if((sgList.size() % intUubun) > 0){
										out.println("<td colspan='"+ sgList.size() % intUubun+"'></td></tr>");
							  		}
							  	}
							  %>
							  		</table>
								</td>
							</tr>
						</table></td>
			      </tr>

			      <tr>
			        <td colspan="2"  bgcolor="#D3D3D3"><img src="../images2/brank.gif" width="1" height="1"></td>
			      </tr>
			      <tr>
			        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong>정보유형</strong></td>
			        <td width="510" style="padding: 5px 0px 5px 10px;"><table width="500" border="0" cellspacing="0" cellpadding="0">
			          <!--<tr>
			            <td height="20"><input type="checkbox" name="mtType" value="">
			      전체게시물</td>
			          </tr>
			          -->			        
			          <tr>
			            <td height="20">
			              <input type="checkbox" name="mt_type_all" value="0" onclick="checkAllMtType(this.checked);"><B>전체</B>&nbsp;&nbsp; 
		            	  <input type="checkbox" name="mt_type" value="1" <%=mtTypeCheck1%>>기사&nbsp;&nbsp;
			      		  <input type="checkbox" name="mt_type" value="2" <%=mtTypeCheck2%>>게시물		       
			      	  	</td>
			      		
			          </tr>
			        </table>			        
			     </td>
			   </tr>


		       
		       <tr>
		         <td colspan="2"  bgcolor="#D3D3D3"><img src="../images2/brank.gif" width="1" height="1"></td>
		       </tr>			       	
			   <tr>
		        <td width="120" height="30" align="center" bgcolor="#F0F0F0" class="table_top" style="padding: 4px 0px 0px 0px;"><strong><div id="sendInterval">발송간격</div></strong></td>
			  	<td width="510" style="padding: 5px 0px 5px 10px;">		  	
				  	<table id="intervalFilter1" width="510" border="0" cellspacing="0" cellpadding="0">
				  		<tr><td>
				  		<input type="radio" name="as_interval1" <%if(asBean.getAs_interval().equals("0")){out.println("checked");} %> value="0" checked>건별&nbsp;&nbsp;
				  		<input type="radio" name="as_interval1" <%if(asBean.getAs_interval().equals("30")){out.println("checked");} %> value="30">30분&nbsp;&nbsp;
				  		<input type="radio" name="as_interval1" <%if(asBean.getAs_interval().equals("60")){out.println("checked");} %> value="60">1시간&nbsp;&nbsp;
				  		<input type="radio" name="as_interval1" <%if(asBean.getAs_interval().equals("180")){out.println("checked");} %> value="180">3시간&nbsp;&nbsp;	
				  		<input type="radio" name="as_interval1" <%if(asBean.getAs_interval().equals("300")){out.println("checked");} %> value="300">5시간&nbsp;&nbsp;	
				  		</td></tr>
		  		 	</table>
	  		 	</td>			  	
		       </tr>
      		</table>
      	</td>
      </tr>
      <tr>
        <td colspan="2" bgcolor="#9CBBE5"><img src="../images2/brank.gif" width="1" height="2"></td>
      </tr>      
    </table>
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>

</table>
<script>
asTypeCheck(<%=asBean.getAs_type()%>);
</script>
<iframe name="frmReceiver" src="ifrm_receiver_list.jsp?ab_seq=<%=ab_seq%>" frameborder="0" scrolling="no" width="810" height="222"></iframe>

<%
	}
} 
%>
<table width="780" border="0" cellspacing="0" cellpadding="0">
	<tr height="40" align="center">
	  <td valign="middle"><img src="../images2/btn_save.gif" width="55" height="24" hspace="5" onclick="save();" style="cursor:hand;"><img src="../images2/btn_cancel.gif" width="55" height="24" hspace="5" onclick="goList();" style="cursor:hand;"></td>
	</tr>
</table>
</form>
</body>
</html>
