<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="risk.util.ParseRequest"%>
<%@page import="risk.util.StringUtil"%>
<%@page import="risk.util.DateUtil"%>
<%@page import="risk.search.MetaMgr"%>
<%@page import="risk.search.MetaBean"%>
<%@page import="risk.issue.IssueCodeMgr"%>
<%@page import="risk.issue.IssueCodeBean"%>
<%@page import="risk.issue.IssueMgr"%>
<%@page import="risk.issue.IssueDataBean"%>
<%@page import="risk.admin.info.InfoGroupMgr"%>
<%@page import="risk.admin.info.InfoGroupBean"%>
<%@include file="../inc/mobile_sessioncheck.jsp"%>
<%
    ParseRequest    pr = new ParseRequest(request);
pr.printParams();
	StringUtil su = new StringUtil();
	DateUtil du = new DateUtil();
	MetaMgr metaMgr = new MetaMgr();
	MetaBean metaBean = new MetaBean();
	IssueMgr issueMgr = new IssueMgr();
	IssueCodeMgr icm = new IssueCodeMgr();
	IssueDataBean idBean = new IssueDataBean();
	IssueCodeBean icBean = new IssueCodeBean();
	
	ArrayList arrIcBean = new ArrayList();
	
	icm.init(0);

	int ic_seq = 0;
	String mt_nos = pr.getString("mt_nos");
	String imgIndex = pr.getString("imgIndex");
	String mode = pr.getString("mode");
	
	if(mode.equals("insert")){
		metaBean = metaMgr.getMetaData(mt_nos);
		ic_seq = icm.getSiteMapCode(metaBean.getSg_seq());
	}else if(mode.equals("update")){
		idBean = issueMgr.getIssueDataBean(mt_nos);
	}
	
   	//정보그룹 관련
   	ArrayList igArr = new ArrayList();
   	InfoGroupMgr igMgr = new InfoGroupMgr();
   	InfoGroupBean igBean = new InfoGroupBean();
   	igArr = igMgr.getInfoGroup("Y");
   	
   	String selected = "";
%>
<link rel="stylesheet" type="text/css" href="../../css/base.css" />
<%
if(mode.equals("insert")){
%>
<input name="md_seq" id="md_seq" type="hidden" value="<%=metaBean.getMd_seq()%>">
<input name="s_seq" id="s_seq" type="hidden" value="<%=metaBean.getS_seq()%>">
<input name="sg_seqs" id="sg_seqs" type="hidden" value="<%=metaBean.getSg_seq()%>">
<input name="md_date" id="md_date" type="hidden" value="<%=metaBean.getMd_date()%>">
<input name="md_site_menu" id="md_site_menu" type="hidden" value="<%=metaBean.getMd_site_menu()%>">
<input name="md_same_ct" id="md_same_ct" type="hidden" value="<%=metaBean.getMd_same_count()%>">
<input name="md_pseq" id="md_pseq" type="hidden" value="<%=metaBean.getMd_pseq()%>">
<%
}else if(mode.equals("update")){
%>
<input name="md_seq" id="md_seq" type="hidden" value="<%=idBean.getMd_seq()%>">
<input name="s_seq" id="s_seq" type="hidden" value="<%=idBean.getS_seq()%>">
<input name="sg_seqs" id="sg_seqs" type="hidden" value="<%=idBean.getSg_seq()%>">
<input name="md_date" id="md_date" type="hidden" value="<%=idBean.getMd_date()%>">
<input name="md_site_menu" id="md_site_menu" type="hidden" value="<%=idBean.getMd_site_menu()%>">
<input name="md_same_ct" id="md_same_ct" type="hidden" value="<%=idBean.getMd_same_ct()%>">
<input name="md_pseq" id="md_pseq" type="hidden" value="<%=idBean.getMd_pseq()%>">
<input type="hidden" name="id_seq" id="id_seq" value="<%=idBean.getId_seq()%>">
<%
}
%>
<table style="width:100%;" border="0" cellpadding="0" cellspacing="10" bgcolor="#ffffff">
	<tr>
		<td>
		<!-- 게시판 시작 -->
		<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td style="height:30px;"><span class="sub_tit">기본정보</span></td>
			</tr>
			<tr>
				<td>
				<table id="board_write" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc" style="table-layout: fixed;">
				<col width="30%"><col width="70%">
					<tr>
						<td bgcolor="#ffffff" style="padding:3px 0px 3px 3px"><span class="sub_tit1">제목</span></td>
						<td colspan="3" bgcolor="#ffffff" style="padding:3px 0px 3px 3px"><input style="width:90%;" type="text" class="textbox2" name="id_title" value="<%if(mode.equals("insert")){out.print(su.ChangeString(metaBean.getMd_title()));}else{out.print(su.ChangeString(idBean.getId_title()));}%>"></td>
					</tr>
					<tr>
						<td bgcolor="#ffffff" style="padding:3px 0px 3px 3px"><span class="sub_tit1">URL</span></td>
						<td colspan="3" bgcolor="#ffffff" style="padding:3px 0px 3px 3px"><input style="width:90%;" type="text" class="textbox2" name="id_url" value="<%if(mode.equals("insert")){out.print(metaBean.getMd_url());}else{out.print(idBean.getId_url());}%>"></td>
					</tr>
					<tr>
						<td bgcolor="#ffffff" style="padding:3px 0px 3px 3px"><span class="sub_tit1">사이트이름</span></td>
						<td colspan="3" bgcolor="#ffffff" style="padding:3px 0px 3px 3px"><input style="width:90%;" type="text" class="textbox2" name="md_site_name" value="<%if(mode.equals("insert")){out.print(metaBean.getMd_site_name());}else{out.print(idBean.getMd_site_name());}%>"></td>
					</tr>
					<tr>
						<td bgcolor="#ffffff" style="padding:3px 0px 3px 3px"><span class="sub_tit1">정보분류시간</span></td>
						<td colspan="3" bgcolor="#ffffff" style="padding:3px 0px 3px 3px"><input style="width:90%;" type="text" class="textbox2" name="id_regdate" value="<%if(mode.equals("insert")){out.print(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));}else{out.print(idBean.getFormatId_regdate("yyyy-MM-dd HH:mm:ss"));}%>" readonly="readonly"></td>
					</tr>
					<tr>
						<td bgcolor="#ffffff" style="padding:3px 0px 3px 3px"><span class="sub_tit1">정보종류</span></td>
						<td colspan="3" bgcolor="#ffffff" style="padding:3px 0px 3px 3px">
							<select name="md_type" id="md_type">
								<option value="1" <%if(mode.equals("insert")){if(metaBean.getMd_type().equals("1")){out.print("selected");}}else{if(idBean.getMd_type().equals("1")){out.print("selected");}}%>>언론</option>
								<option value="2" <%if(mode.equals("insert")){if(Integer.parseInt(metaBean.getMd_type()) > 1){out.print("selected");}}else{if(idBean.getMd_type().equals("2")){out.print("selected");}}%>>개인</option>
							</select>
						</td>
					</tr>
					<tr>
						<td bgcolor="#ffffff" style="padding:3px 0px 3px 3px"><span class="sub_tit1">주요내용</span></td>
						<td colspan="3" bgcolor="#ffffff" style="padding:3px 0px 3px 3px"><textarea style="width:90%;height:60px;" name="id_content"><%if(mode.equals("insert")){out.print(su.cutKey(su.ChangeString(metaBean.getMd_content()),"",300,""));}else{out.print(su.ChangeString(idBean.getId_content()));}%></textarea></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td style="padding-top:15px;height:45px;"><span class="sub_tit">정보분류 항목</span></td>
			</tr>
			<tr>
				<td>
				<table id="board_write" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
					<tr>
<%
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(4);
	icBean = new IssueCodeBean();
	icBean = (IssueCodeBean) arrIcBean.get(0);
%>                                 			
						<td width="30%" bgcolor="#ffffff" style="padding:3px 0px 3px 3px"><span class="sub_tit1"><%=icBean.getIc_name()%>*</span></td>
						<td bgcolor="#ffffff" style="padding:3px 0px 3px 3px">
<%
	if(mode.equals("insert")){
		selected = "4,1";		//DEFAULT 자사 선택
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),4);
	}
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){
			out.print("<input type='radio' name='typeCode4' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' checked>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}else{
			out.print("<input type='radio' name='typeCode4' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"'>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}
		if(i==6 && i!=0)out.print("<br>");
	}
%>	
						</td>
					</tr>
					<tr>
<%
arrIcBean = new ArrayList();
arrIcBean = icm.GetType(7);
icBean = new IssueCodeBean();
icBean = (IssueCodeBean) arrIcBean.get(0);
%>                                 		
						<td width="30%" bgcolor="#ffffff" style="padding:3px 0px 3px 3px"><span class="sub_tit1"><%=icBean.getIc_name()%>*</span></td>
						<td bgcolor="#ffffff" style="padding:3px 0px 3px 3px">
							<select name="typeCode7">
<%	
	if(mode.equals("insert")){
		selected = "";
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),7);
	}
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){
			out.print("<option value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' selected>" + icBean.getIc_name() + "</option>");
		}else{
			out.print("<option value='"+icBean.getIc_type()+","+icBean.getIc_code()+"'>" + icBean.getIc_name() + "</option>");
		}
	}
%>
							</select>
						</td>					
					</tr>
					<tr>
<%
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(17);
	icBean = new IssueCodeBean();
	icBean = (IssueCodeBean) arrIcBean.get(0);
%>                                 			
						<td width="30%" bgcolor="#ffffff" style="padding:3px 0px 3px 3px"><span class="sub_tit1"><%=icBean.getIc_name()%>*</span></td>
						<td bgcolor="#ffffff" style="padding:3px 0px 3px 3px">
<%	
	if(mode.equals("insert")){
		selected = "";
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),17);
	}
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){
			out.print("<input type='radio' name='typeCode17' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick=\"selectChk(this.value)\" checked>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}else{
			out.print("<input type='radio' name='typeCode17' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick=\"selectChk(this.value)\">" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}
		if(i==6 && i!=0)out.print("<br>");
	}
%>
						</td>
					</tr>
					<tr>
						<td colspan="2" bgcolor="#ffffff" style="display:<%if(mode.equals("update")){if(!selected.equals("17,1")){out.print("none");}}else{out.print("none");}%>" id="product"><table border="0" cellpadding="0" cellspacing="0">
							<tr>
<%
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(8);
	icBean = new IssueCodeBean();
	icBean = (IssueCodeBean) arrIcBean.get(0);
%>                                 		
						<td width="30%" bgcolor="#ffffff" style="padding:3px 0px 3px 3px;border-right: 1px solid cccccc"><span class="sub_tit1"><%=icBean.getIc_name()%>*</span></td>
						<td width="70%" bgcolor="#ffffff" style="padding:3px 0px 3px 3px">
<%
	if(mode.equals("insert")){
		selected = "";
		if(ic_seq > 0){
			selected = icm.getTypeCodeValByKey(arrIcBean , ic_seq);	    
		}
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),8);
	}
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){
			out.print("<input type='radio' name='typeCode8' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' checked>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}else{
			out.print("<input type='radio' name='typeCode8' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"'>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}
		if(i==6 && i!=0)out.print("<br>");
	}
%>	
						</td>
							</tr>
						</table></td>
					</tr>
					<tr>
<%
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(6);
	icBean = new IssueCodeBean();
	icBean = (IssueCodeBean) arrIcBean.get(0);
%> 
						<td width="30%" bgcolor="#ffffff" style="padding:3px 0px 3px 3px"><span class="sub_tit1"><%=icBean.getIc_name()%>*</span></td>
						<td bgcolor="#ffffff" style="padding:3px 0px 3px 3px">
<%	
	if(mode.equals("insert")){
		selected = "";
		if(ic_seq > 0){
			selected = icm.getTypeCodeValByKey(arrIcBean , ic_seq);	    
		}
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),6);
	}
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){
			out.print("<input type='radio' name='typeCode6' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' checked>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}else{
			out.print("<input type='radio' name='typeCode6' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"'>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}
		if(i==6 && i!=0)out.print("<br>");
	}
%>
						</td>
					</tr>
					<tr>
<%
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(9);
	icBean = new IssueCodeBean();
	icBean = (IssueCodeBean) arrIcBean.get(0);
%>                                 			
						<td width="30%" bgcolor="#ffffff" style="padding:3px 0px 3px 3px"><span class="sub_tit1"><%=icBean.getIc_name()%>*</span></td>
						<td bgcolor="#ffffff" style="padding:3px 0px 3px 3px">
<%	
	if(mode.equals("insert")){
		selected = "";
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),9);
	}
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){
			out.print("<input type='radio' name='typeCode9' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' checked>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}else{
			out.print("<input type='radio' name='typeCode9' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"'>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}
		if(i==6 && i!=0)out.print("<br>");
	}
%>
						</td>
					</tr>
					<tr>
<%
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(10);
	icBean = new IssueCodeBean();
	icBean = (IssueCodeBean) arrIcBean.get(0);
%>                                 			
						<td width="30%" bgcolor="#ffffff" style="padding:3px 0px 3px 3px"><span class="sub_tit1"><%=icBean.getIc_name()%>*</span></td>
						<td bgcolor="#ffffff" style="padding:3px 0px 3px 3px">
<%	
	if(mode.equals("insert")){
		selected = "";
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),10);
	}
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){
			out.print("<input type='radio' name='typeCode10' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' checked>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}else{
			out.print("<input type='radio' name='typeCode10' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"'>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}
		if(i==6 && i!=0)out.print("<br>");
	}
%>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		<!-- 게시판 끝 -->
		</td>
	</tr>
	<tr>
		<td style="text-align:center;"><img src="../../images/search/btn_save2.gif" onclick="save_issue('<%=mode%>');" style="cursor:pointer;"/>
		<img src="../../images/search/btn_cancel.gif" onclick="issue_cancel();" style="cursor:pointer;"/></td>
	</tr>
</table>