<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="	java.util.ArrayList
                 	,risk.sms.AddressBookDao
					,risk.sms.AddressBookBean
					,risk.sms.AddressBookGroupBean
					,risk.util.ParseRequest
					,risk.admin.site.SitegroupBean
				 	,risk.admin.site.SiteMng
				 	,risk.search.userEnvMgr
				 	,risk.search.keywordInfo
				 	,risk.admin.classification.classificationMgr
				 	,risk.admin.classification.clfBean
				 	,java.util.*
					" %>
<%
	ParseRequest pr = new ParseRequest(request);
	AddressBookDao AddDao = new AddressBookDao();
	AddressBookBean AddBean;	
	pr.printParams();
	
	int abSeq        = pr.getInt("abSeq",0);
	String mode = pr.getString("mode","");
	
	List KGlist = null;
	List sglist = null;
	
	userEnvMgr uemng = new userEnvMgr();
	KGlist = uemng.getKeywordGroup();
	SiteMng sitemng = new SiteMng();
	sglist = sitemng.getSGList();
	List AbGroupList = new ArrayList();
	
	AbGroupList = AddDao.getAdressBookGroup();
	AddressBookGroupBean abgBean = null;
	Iterator it = null;
	if(AbGroupList!=null)
	{
		it = AbGroupList.iterator();
	}
	
	String abName = "";
	String abDept = "";
	String ag_seq = "";
	String abPos = "";
	String abMobile = "";
	String abMail = "";
	String abK_xp = "";
	String abSg_seq = "";
	String abMt_type = "";
	String abIssue_dept = "";
	String abSms_receiverchk = "";
	String abIssue_receivechk = "";
	String abReport_daychk = "";
	String abReport_weekchk = "";
	String abApp_chk = "";
	String smsCheck = "";
	
	String reportDayCheck = "0";
	String reportWeekCheck = "0";
	String ab_sms_limit="";
	
	if( abSeq > 0 && mode.equals("edit")) {
		AddBean = AddDao.Getaddress(abSeq);
		
		
		
		abName = AddBean.getMab_name();
		abDept = AddBean.getMab_dept();
		ag_seq = AddBean.getMag_seq();
		abPos = AddBean.getMab_pos();
		abMobile = AddBean.getMab_mobile();
		abMail = AddBean.getMab_mail();
		smsCheck = AddBean.getMab_sms_receivechk();
		abK_xp = AddBean.getMk_xp();
		abSg_seq = AddBean.getMsg_seq();
		abMt_type = AddBean.getMmt_type();
		abIssue_dept = AddBean.getMab_issue_dept();
		abSms_receiverchk = AddBean.getMab_sms_receivechk();
		abIssue_receivechk = AddBean.getMab_issue_receivechk();
		abReport_daychk = AddBean.getMab_report_day_chk();
		abReport_weekchk = AddBean.getMab_report_week_chk();
		ab_sms_limit = AddBean.getMab_sms_limit();
		abApp_chk = AddBean.getMab_app_chk();
		smsCheck = AddBean.getMab_sms_chk();
		
		
		if( abIssue_receivechk.equals("1") ) {
			abIssue_receivechk = " checked";
		}
		if( abReport_daychk.equals("1") ) {
			reportDayCheck = " checked";
		}
		if( abReport_weekchk.equals("1") ) {
			reportWeekCheck = " checked";
		}		
	}
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<script language="javascript">
<!--
	
	// sms 수신방법 value를 세팅
	function setSms(){		
	
		var f = document.fSend;
		var o = document.fData.smsTemp;
		var smsMethod = '';
 		
 		for(i=0; i<o.length; i++) {
 			if(o(i).checked == true){
	 			if(o(i).value!=""){
	 				if(smsMethod == ''){
	 					smsMethod = o(i).value;
	 				}else{
	 					smsMethod = smsMethod+','+o(i).value;
	 				}
	 			}
 			}
 		}
 		if(smsMethod == ''){
 			smsMethod = '0';
 		}
		f.sms.value = smsMethod;
	}
	
	// sms를 수신할 키워드 그룹을 세팅
	function setKeyGroup(){
		
		var f = document.fSend;
		var o = document.fData.mg_kgTemp;
		var kgGroup = '';
 		
 		for(i=0; i<o.length; i++) {
 			if(o(i).checked == true){
	 			if(o(i).value!=""){
	 				if(kgGroup == ''){
	 					kgGroup = o(i).value;
	 				}else{
	 					kgGroup = kgGroup+','+o(i).value;
	 				}
	 			}
 			}
 		}
 		if(kgGroup == ''){
 			kgGroup = '0';
 		}
		f.kg_xp.value = kgGroup;
	}
	
	// sms를 수신할 사이트 그룹을 세팅
	function setSiteGroup(){
		
		var f = document.fSend;
		var o = document.fData.mg_siteTemp;
		var siteGroup = '';
 		
 		for(i=0; i<o.length; i++) {
 			if(o(i).checked == true){
	 			if(o(i).value!=""){
	 				if(siteGroup == ''){
	 					siteGroup = o(i).value;
	 				}else{
	 					siteGroup = siteGroup+','+o(i).value;
	 				}
	 			}
 			}
 		}
 		if(siteGroup == ''){
 			siteGroup = '0';
 		}
 		
		f.sg_seq.value = siteGroup;
	}
	
	// sms를 수신할  정보 유형을 세팅
	function setMtType(){
		
		var f = document.fSend;
		var o = document.fData.mtTypeTemp;
		var mtType = '';
 		
 		for(i=0; i<o.length; i++) {
 			if(o(i).checked == true){
	 			if(o(i).value!=""){
	 				if(mtType == ''){
	 					mtType = o(i).value;
	 				}else{
	 					mtType = mtType+','+o(i).value;
	 				}
	 			}
 			}
 		}
 		if(mtType == ''){
 			mtType = '0';
 		}
		f.mType.value = mtType;
	}
	
	// 보고서를 수신할 부서 정보를 세팅
	function setReportDept(){
		
		var f = document.fSend;
		var o = document.fData.ic_codeTemp;
		var Ic_code = '';
 		
 		for(i=0; i<o.length; i++) {
 			if(o(i).checked == true){
	 			if(o(i).value!=""){
	 				if(Ic_code == ''){
	 					Ic_code = o(i).value;
	 				}else{
	 					Ic_code = Ic_code+','+o(i).value;
	 				}
	 			}
 			}
 		}
 		if(Ic_code == ''){
 			Ic_code = '0';
 		}
		f.ic_code.value = Ic_code;
	}
	
	function setSmsLimit(){
		
		var f = document.fSend;
		var o = document.fData.sms_limitTemp;
		var sms_limit = '';
 		
 		for(i=0; i<o.length; i++) {
 			if(o(i).checked == true){
	 			if(o(i).value!=""){
	 				if(sms_limit == ''){
	 					sms_limit = o(i).value;
	 				}else{
	 					sms_limit = sms_limit+','+o(i).value;
	 				}
	 			}
 			}
 		}
 		if(sms_limit == ''){
 			sms_limit = '';
 		}
		f.sms_limit.value = sms_limit;
	}
	
	function addReceiver(){
		var f = document.fSend;
		var frm2 = document.fData;
		if( frm2.name.value ) {
			if( frm2.dept.value ) {
				if( frm2.mobile.value ) {
					if( frm2.email.value ) {
						f.name.value = frm2.name.value;
						f.dept.value = frm2.dept.value;
						f.pos.value = frm2.pos.value;
						f.mobile.value = frm2.mobile.value;
						f.email.value = frm2.email.value;
						f.ag_seq.value = frm2.ag_seq.value;
					
						if(frm2.issue_check.checked == true){
							f.issueCheck.value = '1';
						}else{
							f.issueCheck.value = frm2.issue_check.value;
						}
						if(frm2.reportDTemp.checked == true){
							f.reportD.value = '1';
						}else{
							f.reportD.value = frm2.reportDTemp.value;
						}
						if(frm2.reportWTemp.checked == true){
							f.reportW.value = '1';
						}else{
							f.reportW.value = frm2.reportWTemp.value;
						}

							if(frm2.smsChk.checked){
								f.smsChk.value = 1;
							}else{
								f.smsChk.value = 0;
							}
						

								f.target = '';
								f.action = 'receiver_prc.jsp';
								f.submit();
							
						
						}else {
						alert('Email 주소를  입력 하세요');
					}
				}else {
					alert('휴대폰 번호를  입력 하세요');
				}
			}else {
				alert('부서를  입력 하세요');
			}
		}else {
			alert('성명을 입력 하세요');
		}
	
	}
	
	function cancel()
	{
		location.href = 'receiver_list.jsp';
	}
	
	function checkAllsms(chk) {
 		var o=document.all.smsTemp;
 		for(i=0; i<o.length; i++) {
 			o(i).checked = chk;
 		}
 	}
 	function checkAllsms_limit(chk) {
 		var o=document.all.sms_limitTemp;
 		for(i=0; i<o.length; i++) {
 			o(i).checked = chk;
 		}
 	}
 	function checkAllmg_kg(chk) {
 		var o=document.all.mg_kgTemp;
 		for(i=0; i<o.length; i++) {
 			o(i).checked = chk;
 		}
 	}
 	function checkAllmg_site(chk) {
 		var o=document.all.mg_siteTemp;
 		for(i=0; i<o.length; i++) {
 			o(i).checked = chk;
 		}
 	}
 	
 	function checkAllmtType(chk) {
 		var o=document.all.mtTypeTemp;
 		for(i=0; i<o.length; i++) {
 			o(i).checked = chk;
 		}
 	}
 	function checkAllic_code(chk) {
 		var o=document.all.ic_codeTemp;
 		for(i=0; i<o.length; i++) {
 			o(i).checked = chk;
 		}
 	}
 	
 	function smsCheck(){
		var o=document.all.smsTemp;
		var j=0;
		for(i=0; i<o.length; i++) {
			if(o(i).checked == true){
			j++;
			}
		}
		if(j>=o.length){
		document.all.smsAll.checked = true;
		}
 	}function sms_limitCheck(){
		var o=document.all.sms_limitTemp;
		var j=0;
		for(i=0; i<o.length; i++) {
			if(o(i).checked == true){
			j++;
			}
		}
		if(j>=o.length){
		document.all.sms_limitAll.checked = true;
		}
 	}
 	
 	function mg_kgCheck(){
		var o=document.all.mg_kgTemp;
		var j=0;
		for(i=0; i<o.length; i++) {
			if(o(i).checked == true){
			j++;
			}
		}
		if(j>=o.length){
		document.all.mg_kgAll.checked = true;
		}
 	}
 	function mg_siteCheck(){
 		var o=document.all.mg_siteTemp;
		var j=0;
		for(i=0; i<o.length; i++) {
			if(o(i).checked == true){
			j++;
			}
		}
		if(j>=o.length){
		document.all.mg_siteAll.checked = true;
		}	
 	}
 	function mtTypeCheck(){
 		var o=document.all.mtTypeTemp;
		var j=0;
		for(i=0; i<o.length; i++) {
			if(o(i).checked == true){
			j++;
			}
		}
		if(j>=o.length){
		document.all.mtTypeAll.checked = true;
		}	
 	}
 	function ic_codeCheck(){
 		var o=document.all.ic_codeTemp;
		var j=0;
		for(i=0; i<o.length; i++) {
			if(o(i).checked == true){
			j++;
			}
		}
		//alert(j+'olength'+o.length);
		if(j>=o.length){
		document.all.ic_codeAll.checked = true;
		}	
 	}
 	function addGroup()
 	{
 		window.open('pop_adressbook_group.jsp', 'pop_sitegroup', "width=400,height=146,scrollbars=no");
 	}

 	function delGroup()
 	{
 	 	var f = document.fSend;
 		var f2 = document.fData;		

		if(window.confirm('해당그룹을 삭제하시겠습니까?'))
		{
 		
			for(var i=0; i<f2.ag_seq.length ;i++)
			{
				if(f2.ag_seq[i].selected)
				{
					f.ag_seq.value = f2.ag_seq[i].value;
					break;
				}
			}
			 		
	 		f.mode.value='del';
	 		f.target=''; 		
	 		f.action='adressbook_group_prc.jsp';
	 		f.submit(); 
		}else{
			return;
		}
 		
 	} 	
//-->
</script>
</head>
<body>
<form name="fSend" action="receiver_prc.jsp" method="post">
<input type="hidden" name="abSeq" value="<%=abSeq%>">
<input type="hidden" name="ag_seq" value="">
<input type="hidden" name="name" value="">
<input type="hidden" name="dept" value="">
<input type="hidden" name="pos" value="">
<input type="hidden" name="mobile" value="">
<input type="hidden" name="email" value="">
<input type="hidden" name="sms" value="">
<input type="hidden" name="issueCheck" value="">
<input type="hidden" name="appChk" value="">
<input type="hidden" name="reportD" value="">
<input type="hidden" name="reportW" value="">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="smsChk" value="">
</form>
<form name="fData" action="" method="post">
<input type="hidden" name="issue_check" value="1">
<input type="hidden" name="reportDTemp" value="1">
<input type="hidden" name="reportWTemp" value="1">
<table style="width:850px;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table align="center" style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:37px;padding-top:0px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/receiver/tit_icon.gif" /><img src="../../../images/admin/receiver/tit_0506.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">정보수신자관리</td>
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
				<table id="board_write" border="0" cellspacing="0">
					<tr>
						<th><span class="board_write_tit">성명</span></th>
						<td><input style="width:50%;" type="text" class="textbox2" name="name" value="<%=abName%>"></td>
					</tr>
					<tr>
						<th><span class="board_write_tit">수신자그룹</span></th>
						<td>
						<ul>
							<li style="padding-top:3px;padding-left:3px;">
            				<select name="ag_seq">
<%
	if(it!=null){
		String selected = "";
		while(it.hasNext()){
			selected = "";
			abgBean = new AddressBookGroupBean();
			abgBean = (AddressBookGroupBean)it.next();
			if(ag_seq.equals(abgBean.getAg_seq())){
				selected="selected";
			}
%>        	
								<option value="<%=abgBean.getAg_seq()%>" <%=selected%>><%=abgBean.getAg_name()%></option>
<%
		}
	}
%>
							</select>
							</li>
							<li><img src="../../../images/admin/receiver/btn_groupadd.gif" style="cursor:pointer;" onclick="addGroup();"/></li>
							<li><img src="../../../images/admin/receiver/btn_groupdel.gif" style="cursor:pointer;" onclick="delGroup();"/></li>
						</ul>
						</td>
					</tr>
					<tr>
						<th><span class="board_write_tit">부서</span></th>
						<td><input style="width:50%;" type="text" class="textbox2" name="dept" value="<%=abDept%>"></td>
					</tr>
					<tr>
						<th><span class="board_write_tit">직급</span></th>
						<td><input style="width:50%;" type="text" class="textbox2" name="pos" value="<%=abPos%>"></td>
					</tr>
					<tr>
						<th><span class="board_write_tit">핸드폰 번호</span></th>
						<td><input style="width:50%;" type="text" class="textbox2" name="mobile" value="<%=abMobile%>">&nbsp;&nbsp;SMS발송<input type="checkbox" class="textbox2" name="smsChk" <%if(smsCheck.equals("1")){out.print("checked");}%>></td>
					</tr>
					<tr>
						<th><span class="board_write_tit">이메일 주소</span></th>
						<td><input style="width:50%;" type="text" class="textbox2" name="email" value="<%=abMail%>"></td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 게시판 끝 -->
			<tr>
				<td style="height:40px;" align="center"><img src="../../../images/admin/receiver/btn_save2.gif" onclick="addReceiver();" style="cursor:pointer;"/><img src="../../../images/admin/receiver/btn_cancel.gif" onclick="cancel();" style="cursor:pointer;"/></td>
			</tr>
		</table>
		</td>

	</tr>
</table>
</form>
</body>
</html>
