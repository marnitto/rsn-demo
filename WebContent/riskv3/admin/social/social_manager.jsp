<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../inc/sessioncheck.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.util.DateUtil"%>
<%@ page import="risk.util.StringUtil"%>
<%@ page import="risk.issue.IssueMgr"%>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();

	DateUtil du = new DateUtil();
	IssueMgr im = new IssueMgr();
	StringUtil su = new StringUtil();

	//검색날짜 설정 : 전일 대비 결과를 위해 조회날짜와 그 전일 날짜를 세팅한다.
	String cDate = du.getCurrentDate();
	String currentDate = pr.getString("sDate", du.getCurrentDate());
	du.setDate(currentDate);
	du.addDay(-1);
	String startDate = du.getDate();

	HashMap facebook = null;
	HashMap twitter = null;
	HashMap kakao = null;
	HashMap blog = null;

	blog = im.getSocialData(currentDate, "B");
	facebook = im.getSocialData(currentDate, "F");
	twitter = im.getSocialData(currentDate, "T");
	kakao = im.getSocialData(currentDate, "K");
	

%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=SS_URL%>css/design.css">
<script type="text/javascript" src="<%=SS_URL%>dashboard/asset/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="<%=SS_URL%>dashboard/asset/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=SS_URL%>js/design.js"></script>
<!--[if (gte IE 6)&(lte IE 8)]>
	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<script type="text/javascript" src="../asset/js/selectivizr-min.js"></script>
<![endif]-->
<script type="text/javascript">
	function socialMod(pflag, ptarget){
		var flag = pflag;
		var target = ptarget;
		
		$("."+flag+"_time_"+target).removeAttr('disabled');
		$("."+flag+"_input_"+target).removeAttr('readonly');
		$("#"+pflag+"_modBtn_"+ptarget).css('display','none');
		$("#"+pflag+"_savBtn_"+ptarget).css('display','');
	}
	
	function socialSave(pflag, ptarget){
		var date = $("[name="+pflag+"_date_"+ptarget+"]").val();
		var time = $("."+pflag+"_time_"+ptarget+" option:selected").val();
		var data1 = $("[name="+pflag+"_data1_"+ptarget+"]").val();
		var data2 = $("[name="+pflag+"_data2_"+ptarget+"]").val();
		
		data1 = data1.replace("," , "");
		data2 = data2.replace("," , "");
		
		$("[name=type]").val(pflag);
		$("[name=date]").val(date);
		$("[name=time]").val(time);
		$("[name=data1]").val(data1);
		$("[name=data2]").val(data2);
		$("#frm").attr("action", "social_manager_prc.jsp");
		$("#frm").submit();
	}
	
	function getSearch(){
		var sDate = $("#sDate").val();
		location.href="social_manager.jsp?sDate="+sDate;
	}
	
	function save(){
		
		var pflag = $("ul > li > a.active").attr("id");
		
		$("[name=type]").val(pflag);
		$("[name=date]").val($("[name="+pflag+"_insert_date]").val());
		$("[name=time]").val($("[name="+pflag+"_insert_time]").val());
		$("[name=data1]").val($("[name="+pflag+"_insert_data1]").val());
		$("[name=data2]").val($("[name="+pflag+"_insert_data2]").val());
		$("[name=data3]").val($("[name="+pflag+"_insert_data3]").val());
		$("#frm").attr("action", "social_manager_prc.jsp");
		$("#frm").submit();
		
	}
	
	function del(el){
		var type = $(el).parent("div").data("flag");
		var date = $(el).parent("div").find("table > tbody > tr > td:nth-child(1)").text();

		var param = "op=delete&type=" + type + "&date=" + date;
		
		if(date!="-"){
			$.ajax({      
		        type:"POST",  
		        url:"social_manager_prc.jsp",
		        data:param,
		        dataType:"text",
		        success:function(){
		        	var $tbody = $(el).parent("div").find("table > tbody > tr");
		        	$tbody.find("td:nth-child(1)").text("-");
		        	$tbody.find("td:nth-child(2)").css("height", "20px").text("-시");
		        	$tbody.find("td:nth-child(3)").text("0");
		        	$tbody.find("td:nth-child(4)").html("<span class='ui_icon_up'></span><span class='ui_icon_up_data'>0</span>");
		        	$tbody.find("td:nth-child(5)").text("0");
		        	$tbody.find("td:nth-child(6)").html("<span class='ui_icon_up'></span><span class='ui_icon_up_data'>0</span>");
		        	$tbody.find("td:nth-child(7)").text("0");
		        	$tbody.find("td:nth-child(8)").html("<span class='ui_icon_up'></span><span class='ui_icon_up_data'>0</span>");
		        }
			});
		}else{
			alert("삭제할 데이터가 없습니다.");
		}
	}
	
</script>
</head>
<body>
<form id="frm" name="frm" target="prcFrame">
<input type="hidden" name="insert_date" value="<%=cDate%>" >
<input type="hidden" name="type" />
<input type="hidden" name="date" />
<input type="hidden" name="time" />
<input type="hidden" name="data1" />
<input type="hidden" name="data2" />
<input type="hidden" name="data3" />
</form>
	<div id="fr_content">
		<!-- 페이지 타이틀 -->
		<h3><span class="icon">-</span><img src="<%=SS_URL%>img/h3_admin_social_stat.gif" alt=""></h3>
		<!-- // 페이지 타이틀 -->

		<!-- Locator -->
		<div class="ui_locator">
			<span class="home"></span>HOME<span class="arrow">></span>관리자<span class="arrow">></span><strong>소셜 통계 관리</strong>
		</div>
		<!-- // Locator -->

		<!-- 검색 -->
		<div class="article ui_searchbox_00">
			<div class="c_wrap">
				<div class="ui_row_00">
					<span class="title"><span class="icon">-</span>검색기간</span>
					<span class="txts">
						<input id="sDate" type="text" class="ui_datepicker_input input_date_first" value="<%=currentDate%>" readonly><label for="sDate">날짜입력</label>
						<button class="ui_btn_02" onclick="getSearch();">검색</button>
					</span>
				</div>
			</div>
		</div>
		<!-- // 검색 -->

		<!-- 입력 -->
		<div class="article m_t_30" style="display:;">
			<div class="ui_tab_00">
				<ul>
					<li>
						<a href="#" class="tab active" id="B">블로그</a>
						<div class="tab_content ui_table_01">
							<table summary="블로그">
							<caption>블로그</caption>
							<colgroup>
								<col style="width:110px">
								<col>
								<col style="width:110px">
								<col>
								<col style="width:110px">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th>날짜</th>
									<td><input type="text" name="B_insert_date" value="<%=cDate%>"></td>
									<th>기준시간</th>
									<td colspan="3" style="text-align: left;">
										<select id="insert_time_03" name="B_insert_time" class="ui_select_02" style="width:190px">
											<%for(int t=1; t < 25; t++) {%>
												<%if(blog != null){ %>
													<option value="<%=t%>" <%if(blog.get("TIME").toString().equals(t+"")){out.print("selected");} %> > <%=t%>시</option>
												<%}else{ %>
													<option value="<%=t%>"> <%=t%>시</option>
												<%} %>
											<%} %>
										</select><label for="insert_time_03" class="invisible">기준시간 선택</label>
									</td>
									
								</tr>
								<tr>
									<th>팬수</th>
									<td><input id="ui_input_bl_00" type="text" class="ui_input_00" name="B_insert_data1" value="0" ><label for="ui_input_bl_00" class="invisible">팔로잉 입력</label></td>
									<th>확산도</th>
									<td><input id="ui_input_bl_01" type="text" class="ui_input_00" name="B_insert_data2" value="0" ><label for="ui_input_bl_01" class="invisible">팬수 입력</label></td>
									<th>반응도</th>
									<td><input id="ui_input_bl_02" type="text" class="ui_input_00" name="B_insert_data3" value="0" ><label for="ui_input_bl_02" class="invisible">팬수 입력</label></td>
								</tr>
							</tbody>
							</table>
						</div>
					</li>
					<li>
						<a href="#" class="tab" id="F">페이스북</a>
						<div class="tab_content ui_table_01">
							<table summary="페이스북">
							<caption>페이스북</caption>
							<colgroup>
								<col style="width:110px">
								<col>
								<col style="width:110px">
								<col>
								<col style="width:110px">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th>날짜</th>
									<td><input type="text" name="F_insert_date" value="<%=cDate%>"></td>
									<th>기준시간</th>
									<td colspan="3" style="text-align: left;">
										<select id="insert_time_00" name="F_insert_time" class="ui_select_02" style="width:190px;">
											<%for(int t=1; t < 25; t++) {%>
												<%if(facebook != null){ %>
													<option value="<%=t%>" <%if(facebook.get("TIME").toString().equals(t+"")){out.print("selected");} %> > <%=t%>시</option>
												<%}else{ %>
													<option value="<%=t%>"> <%=t%>시</option>
												<%} %>
											<%} %>
										</select><label for="insert_time_00" class="invisible">기준시간 선택</label>
									</td>
								</tr>
								<tr>
									<th>팬수</th>
									<td><input id="ui_input_fb_00" type="text" class="ui_input_00" name="F_insert_data1" value="0" ><label for="ui_input_fb_00" class="invisible">페이스북</label></td>
									<th>확산도</th>
									<td><input id="ui_input_fb_01" type="text" class="ui_input_00" name="F_insert_data2" value="0" ><label for="ui_input_fb_01" class="invisible">페이스북</label></td>
									<th>반응도</th>
									<td><input id="ui_input_fb_02" type="text" class="ui_input_00" name="F_insert_data3" value="0" ><label for="ui_input_fb_02" class="invisible">페이스북</label></td>
								</tr>
							</tbody>
							</table>
						</div>
					</li>
					<li>
						<a href="#" class="tab" id="T">트위터</a>
						<div class="tab_content ui_table_01">
							<table summary="트위터">
							<caption>트위터</caption>
							<colgroup>
								<col style="width:110px">
								<col>
								<col style="width:110px">
								<col>
								<col style="width:110px">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th>날짜</th>
									<td><input type="text" name="T_insert_date" value="<%=cDate%>"></td>
									<th>기준시간</th>
									<td colspan="3" style="text-align: left;">
										<select id="insert_time_01" name="T_insert_time" class="ui_select_02" style="width:190px">
											<%for(int t=1; t < 25; t++) {%>
												<%if(twitter != null){ %>
													<option value="<%=t%>" <%if(twitter.get("TIME").toString().equals(t+"")){out.print("selected");} %> > <%=t%>시</option>
												<%}else{ %>
													<option value="<%=t%>"> <%=t%>시</option>
												<%} %>
											<%} %>
										</select><label for="insert_time_01" class="invisible">기준시간 선택</label>
									</td>									
									
								</tr>
								<tr>
									<th>팬수</th>
									<td><input id="ui_input_tw_00" type="text" class="ui_input_00" name="T_insert_data1" value="0"><label for="ui_input_tw_00" class="invisible">트위터</label></td>
									<th>확산도</th>
									<td><input id="ui_input_tw_01" type="text" class="ui_input_00" name="T_insert_data2" value="0"><label for="ui_input_tw_01" class="invisible">트위터</label></td>
									<th>반응도</th>
									<td><input id="ui_input_tw_02" type="text" class="ui_input_00" name="T_insert_data3" value="0"><label for="ui_input_tw_02" class="invisible">트위터</label></td>
								</tr>
							</tbody>
							</table>
						</div>
					</li>
					<li>
						<a href="#" class="tab" id="K">카카오톡</a>
						<div class="tab_content ui_table_01">
							<table summary="카카오톡">
							<caption>카카오톡</caption>
							<colgroup>
								<col style="width:110px">
								<col>
								<col style="width:110px">
								<col>
								<col style="width:110px">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th>날짜</th>
									<td><input type="text" name="K_insert_date" value="<%=cDate%>"></td>
									<th>기준시간</th>
									<td colspan="3" style="text-align: left;">
										<select id="insert_time_02" name="K_insert_time" class="ui_select_02" style="width:190px">
											<%for(int t=1; t < 25; t++) {%>
												<%if(kakao != null){ %>
													<option value="<%=t%>" <%if(kakao.get("TIME").toString().equals(t+"")){out.print("selected");} %> > <%=t%>시</option>
												<%}else{ %>
													<option value="<%=t%>"> <%=t%>시</option>
												<%} %>
											<%} %>
										</select><label for="insert_time_02" class="invisible">기준시간 선택</label>
									</td>
								</tr>
								<tr>
									<th>팬수</th>
									<td><input id="ui_input_ka_00" type="text" class="ui_input_00" name="K_insert_data1" value="0"><label for="ui_input_ka_00" class="invisible">팔로잉 입력</label></td>
									<th>확산도</th>
									<td><input id="ui_input_ka_01" type="text" class="ui_input_00" name="K_insert_data2" value="0"><label for="ui_input_ka_01" class="invisible">팬수 입력</label></td>
									<th>반응도</th>
									<td><input id="ui_input_ka_02" type="text" class="ui_input_00" name="K_insert_data3" value="0"><label for="ui_input_ka_02" class="invisible">팬수 입력</label></td>
								</tr>
							</tbody>
							</table>
						</div>
					</li>
					
				</ul>
			</div>
			<div class="ar p_r_10">
				<a href="javascript:save();" class="ui_btn_04 ui_shadow_00"><span class="icon confirm">-</span>저장</a>
			</div>
		</div>
		<!-- // 입력 -->
		
		<!-- 블로그 -->
		<div data-flag="B" class="article ui_table_00 m_t_30">
			<h4 style="display:inline"><span class="icon">-</span>블로그</h4>
			<a href="#" class="ui_btn_04 ui_shadow_00" onclick="del(this);" style="float:right"><span class="icon confirm">-</span>삭제</a>
			<table summary="블로그">
			<caption>블로그</caption>
			<colgroup>
				<col style="width:12%">
				<col style="width:12%">
				<col >
				<col >
				<col >
				<col >
				<col >
				<col >
				<!-- <col style="width:10%"> -->
			</colgroup>
			<thead>
				<tr>
					<th scope="col">날짜</th>
					<th scope="col">기준시간</th>
					<th scope="col" colspan="2">팬수</th>
					<th scope="col" colspan="2">확산도</th>
					<th scope="col" colspan="2">반응도</th>
					<!-- <th scope="col">처리</th> -->
				</tr>
			</thead>
			<tbody>
			<%
			if(blog != null){
				String checked = "";
				String classStr1[] = {"",""};
				String classStr2[] = {"",""};
				String classStr3[] = {"",""};
				String DIF_SM_CNT1 = "";
				String DIF_SM_CNT2 = "";
				String DIF_SM_CNT3 = "";
				boolean chk = false;
				
				for(int i = 0; i < 1; i++){
					
					chk = (blog.get("DIF_SM_CNT1").toString()).startsWith("-");
					if(chk){
						DIF_SM_CNT1 = blog.get("DIF_SM_CNT1").toString().replaceAll("-", "");
						classStr1[0] = "ui_icon_dn"; classStr1[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT1 = blog.get("DIF_SM_CNT1").toString();
						classStr1[0] = "ui_icon_up"; classStr1[1] = "ui_icon_up_data";
					}
					
					chk = blog.get("DIF_SM_CNT2").toString().startsWith("-");
					if(chk){
						DIF_SM_CNT2 = blog.get("DIF_SM_CNT2").toString().replaceAll("-", "");
						classStr2[0] = "ui_icon_dn"; classStr2[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT2 = blog.get("DIF_SM_CNT2").toString();
						classStr2[0] = "ui_icon_up"; classStr2[1] = "ui_icon_up_data";
					}
					
					chk = blog.get("DIF_SM_CNT3").toString().startsWith("-");
					if(chk){
						DIF_SM_CNT3 = blog.get("DIF_SM_CNT3").toString().replaceAll("-", "");
						classStr3[0] = "ui_icon_dn"; classStr3[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT3 = blog.get("DIF_SM_CNT3").toString();
						classStr3[0] = "ui_icon_up"; classStr3[1] = "ui_icon_up_data";
					}
			%>
			
				<tr>
					<td><%=blog.get("DATE")%><input type="hidden" name="B_date_<%=blog.get("TIME")%>" value="<%=blog.get("DATE")%>" /></td>
					<td>
						<select id="input_select_time_00_0<%=i%>" class="ui_select_02 B_time_<%=blog.get("TIME")%>" style="width:70px" disabled="disabled">
							<%for(int t=1; t < 25; t++) {%>
							<option value="<%=t%>" <%if(blog.get("TIME").toString().equals(t+"")){out.print("selected");} %> > <%=t%>시</option>
							<%} %>
						</select><label for="input_select_time_00_0<%=i%>" class="invisible">기준시간</label><span><%=blog.get("TIME")%>시</span>
					</td>
					<td><input name="B_data1_<%=blog.get("TIME")%>" id="ui_input_0<%=i%>_0<%=i%>" type="text" class="ui_input_00 B_input_<%=blog.get("TIME")%>" value="<%=blog.get("SM_CNT1")%>" readonly><label for="ui_input_0<%=i%>_0<%=i%>" class="invisible">팬수</label></td>
					<td><span class="<%=classStr1[0]%>"> </span><span class="<%=classStr1[1]%>"><%=blog.get("DIF_SM_CNT1")%></span></td>
					<td><input name="B_data2_<%=blog.get("TIME")%>" id="ui_input_0<%=i+1%>_0<%=i%>" type="text" class="ui_input_00 B_input_<%=blog.get("TIME")%>" value="<%=blog.get("SM_CNT2")%>" readonly><label for="ui_input_0<%=i+1%>_0<%=i%>" class="invisible">확산도</label></td>
					<td><span class="<%=classStr2[0]%>"> </span><span class="<%=classStr2[1]%>"><%=blog.get("DIF_SM_CNT2")%></span></td>
					<td><input name="B_data2_<%=blog.get("TIME")%>" id="ui_input_0<%=i+1%>_0<%=i%>" type="text" class="ui_input_00 B_input_<%=blog.get("TIME")%>" value="<%=blog.get("SM_CNT3")%>" readonly><label for="ui_input_0<%=i+1%>_0<%=i%>" class="invisible">반응도</label></td>
					<td><span class="<%=classStr3[0]%>"> </span><span class="<%=classStr3[1]%>"><%=blog.get("DIF_SM_CNT3")%></span></td>
					<%-- <td><a id="B_modBtn_<%=blog.get("TIME")%>" href="javascript:socialMod('B','<%=blog.get("TIME")%>');" class="ui_btn_04 ui_shadow_00">수정</a><a id="B_savBtn_<%=blog.get("TIME")%>" style="display:none;" href="javascript:socialSave('F','<%=blog.get("TIME")%>');" class="ui_btn_04 ui_shadow_00">저장</a></td> --%>
				</tr>
			
		<%	}
		}else{%>
		<tr height="25">
			<td align="center" bgcolor="#FFFFFF" colspan="8">데이터가 없습니다.</td>
		</tr>
		<%}
		%>
			</tbody>
			</table>
		</div>
		<!-- 블로그 -->		

		<!-- 페이스북 -->
		<div data-flag="F" class="article ui_table_00 m_t_30">
			<h4 style="display:inline"><span class="icon">-</span>페이스북</h4>
			<a href="#" class="ui_btn_04 ui_shadow_00" onclick="del(this);" style="float:right"><span class="icon confirm">-</span>삭제</a>
			<table summary="페이스북">
			<caption>페이스북</caption>
			<colgroup>
				<col style="width:12%">
				<col style="width:12%">
				<col >
				<col >
				<col >
				<col >
				<col >
				<col >
				<!-- <col style="width:10%"> -->
			</colgroup>
			<thead>
				<tr>
					<th scope="col">날짜</th>
					<th scope="col">기준시간</th>
					<th scope="col" colspan="2">팬수</th>
					<th scope="col" colspan="2">확산도</th>
					<th scope="col" colspan="2">반응도</th>
					<!-- <th scope="col">처리</th> -->
				</tr>
			</thead>
			<tbody>
			<%
			if(facebook != null){
				String checked = "";
				String classStr1[] = {"",""};
				String classStr2[] = {"",""};
				String classStr3[] = {"",""};
				String DIF_SM_CNT1 = "";
				String DIF_SM_CNT2 = "";
				String DIF_SM_CNT3 = "";
				boolean chk = false;
				
				for(int i = 0; i < 1; i++){
					
					chk = (facebook.get("DIF_SM_CNT1").toString()).startsWith("-");
					if(chk){
						DIF_SM_CNT1 = facebook.get("DIF_SM_CNT1").toString().replaceAll("-", "");
						classStr1[0] = "ui_icon_dn"; classStr1[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT1 = facebook.get("DIF_SM_CNT1").toString();
						classStr1[0] = "ui_icon_up"; classStr1[1] = "ui_icon_up_data";
					}
					
					chk = facebook.get("DIF_SM_CNT2").toString().startsWith("-");
					if(chk){
						DIF_SM_CNT2 = facebook.get("DIF_SM_CNT2").toString().replaceAll("-", "");
						classStr2[0] = "ui_icon_dn"; classStr2[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT2 = facebook.get("DIF_SM_CNT2").toString();
						classStr2[0] = "ui_icon_up"; classStr2[1] = "ui_icon_up_data";
					}
					
					chk = facebook.get("DIF_SM_CNT3").toString().startsWith("-");
					if(chk){
						DIF_SM_CNT3 = facebook.get("DIF_SM_CNT3").toString().replaceAll("-", "");
						classStr3[0] = "ui_icon_dn"; classStr3[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT3 = facebook.get("DIF_SM_CNT3").toString();
						classStr3[0] = "ui_icon_up"; classStr3[1] = "ui_icon_up_data";
					}
			%>
			
				<tr>
					<td><%=facebook.get("DATE")%><input type="hidden" name="F_date_<%=facebook.get("TIME")%>" value="<%=facebook.get("DATE")%>" /></td>
					<td>
						<select id="input_select_time_00_0<%=i%>" class="ui_select_02 F_time_<%=facebook.get("TIME")%>" style="width:70px" disabled="disabled">
							<%for(int t=1; t < 25; t++) {%>
							<option value="<%=t%>" <%if(facebook.get("TIME").toString().equals(t+"")){out.print("selected");} %> > <%=t%>시</option>
							<%} %>
						</select><label for="input_select_time_00_0<%=i%>" class="invisible">기준시간</label><span><%=facebook.get("TIME")%>시</span>
					</td>
					<td><input name="F_data1_<%=facebook.get("TIME")%>" id="ui_input_0<%=i%>_0<%=i%>" type="text" class="ui_input_00 F_input_<%=facebook.get("TIME")%>" value="<%=facebook.get("SM_CNT1")%>" readonly><label for="ui_input_0<%=i%>_0<%=i%>" class="invisible">팬수</label></td>
					<td><span class="<%=classStr1[0]%>"> </span><span class="<%=classStr1[1]%>"><%=facebook.get("DIF_SM_CNT1")%></span></td>
					<td><input name="F_data2_<%=facebook.get("TIME")%>" id="ui_input_0<%=i+1%>_0<%=i%>" type="text" class="ui_input_00 F_input_<%=facebook.get("TIME")%>" value="<%=facebook.get("SM_CNT2")%>" readonly><label for="ui_input_0<%=i+1%>_0<%=i%>" class="invisible">확산도</label></td>
					<td><span class="<%=classStr2[0]%>"> </span><span class="<%=classStr2[1]%>"><%=facebook.get("DIF_SM_CNT2")%></span></td>
					<td><input name="F_data2_<%=facebook.get("TIME")%>" id="ui_input_0<%=i+1%>_0<%=i%>" type="text" class="ui_input_00 F_input_<%=facebook.get("TIME")%>" value="<%=facebook.get("SM_CNT3")%>" readonly><label for="ui_input_0<%=i+1%>_0<%=i%>" class="invisible">반응도</label></td>
					<td><span class="<%=classStr3[0]%>"> </span><span class="<%=classStr3[1]%>"><%=facebook.get("DIF_SM_CNT3")%></span></td>
					<%-- <td><a id="F_modBtn_<%=facebook.get("TIME")%>" href="javascript:socialMod('F','<%=facebook.get("TIME")%>');" class="ui_btn_04 ui_shadow_00">수정</a><a id="F_savBtn_<%=facebook.get("TIME")%>" style="display:none;" href="javascript:socialSave('F','<%=facebook.get("TIME")%>');" class="ui_btn_04 ui_shadow_00">저장</a></td> --%>
				</tr>
			
		<%	}
		}else{%>
		<tr height="25">
			<td align="center" bgcolor="#FFFFFF" colspan="8">데이터가 없습니다.</td>
		</tr>
		<%}
		%>
			</tbody>
			</table>
		</div>
		<!-- // 기업 페이스북 -->

		<!-- 트위터 -->
		<div data-flag="T" class="article ui_table_00 m_t_30">
			<h4 style="display:inline"><span class="icon">-</span>트위터</h4>
			<a href="#" class="ui_btn_04 ui_shadow_00" onclick="del(this);" style="float:right"><span class="icon confirm">-</span>삭제</a>
			<table summary="트위터">
			<caption>트위터</caption>
			<colgroup>
				<col style="width:12%">
				<col style="width:12%">
				<col >
				<col >
				<col >
				<col >
				<col >
				<col >
				<!-- <col style="width:10%"> -->
			</colgroup>
			<thead>
				<tr>
					<th scope="col">날짜</th>
					<th scope="col">기준시간</th>
					<th scope="col" colspan="2">팬수</th>
					<th scope="col" colspan="2">확산도</th>
					<th scope="col" colspan="2">반응도</th>
					<!-- <th scope="col">처리</th> -->
				</tr>
			</thead>
			<tbody>
			<%
			if(twitter != null){
				String checked = "";
				String classStr1[] = {"",""};
				String classStr2[] = {"",""};
				String classStr3[] = {"",""};
				String DIF_SM_CNT1 = "";
				String DIF_SM_CNT2 = "";
				String DIF_SM_CNT3 = "";
				boolean chk = false;
				
				for(int i = 0; i < 1; i++){
					
					chk = (twitter.get("DIF_SM_CNT1").toString()).startsWith("-");
					if(chk){
						DIF_SM_CNT1 = twitter.get("DIF_SM_CNT1").toString().replaceAll("-", "");
						classStr1[0] = "ui_icon_dn"; classStr1[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT1 = twitter.get("DIF_SM_CNT1").toString();
						classStr1[0] = "ui_icon_up"; classStr1[1] = "ui_icon_up_data";
					}
					
					chk = twitter.get("DIF_SM_CNT2").toString().startsWith("-");
					if(chk){
						DIF_SM_CNT2 = twitter.get("DIF_SM_CNT2").toString().replaceAll("-", "");
						classStr2[0] = "ui_icon_dn"; classStr2[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT2 = twitter.get("DIF_SM_CNT2").toString();
						classStr2[0] = "ui_icon_up"; classStr2[1] = "ui_icon_up_data";
					}
					
					chk = twitter.get("DIF_SM_CNT3").toString().startsWith("-");
					if(chk){
						DIF_SM_CNT3 = twitter.get("DIF_SM_CNT3").toString().replaceAll("-", "");
						classStr3[0] = "ui_icon_dn"; classStr3[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT3 = twitter.get("DIF_SM_CNT3").toString();
						classStr3[0] = "ui_icon_up"; classStr3[1] = "ui_icon_up_data";
					}
			%>
			
				<tr>
					<td><%=twitter.get("DATE")%><input type="hidden" name="T_date_<%=twitter.get("TIME")%>" value="<%=twitter.get("DATE")%>" /></td>
					<td>
						<select id="input_select_time_00_0<%=i%>" class="ui_select_02 T_time_<%=twitter.get("TIME")%>" style="width:70px" disabled="disabled">
							<%for(int t=1; t < 25; t++) {%>
							<option value="<%=t%>" <%if(twitter.get("TIME").toString().equals(t+"")){out.print("selected");} %> > <%=t%>시</option>
							<%} %>
						</select><label for="input_select_time_00_0<%=i%>" class="invisible">기준시간</label><span><%=twitter.get("TIME")%>시</span>
					</td>
					<td><input name="T_data1_<%=twitter.get("TIME")%>" id="ui_input_0<%=i%>_0<%=i%>" type="text" class="ui_input_00 T_input_<%=twitter.get("TIME")%>" value="<%=twitter.get("SM_CNT1")%>" readonly><label for="ui_input_0<%=i%>_0<%=i%>" class="invisible">팬수</label></td>
					<td><span class="<%=classStr1[0]%>"> </span><span class="<%=classStr1[1]%>"><%=twitter.get("DIF_SM_CNT1")%></span></td>
					<td><input name="T_data2_<%=twitter.get("TIME")%>" id="ui_input_0<%=i+1%>_0<%=i%>" type="text" class="ui_input_00 T_input_<%=twitter.get("TIME")%>" value="<%=twitter.get("SM_CNT2")%>" readonly><label for="ui_input_0<%=i+1%>_0<%=i%>" class="invisible">확산도</label></td>
					<td><span class="<%=classStr2[0]%>"> </span><span class="<%=classStr2[1]%>"><%=twitter.get("DIF_SM_CNT2")%></span></td>
					<td><input name="T_data2_<%=twitter.get("TIME")%>" id="ui_input_0<%=i+1%>_0<%=i%>" type="text" class="ui_input_00 T_input_<%=twitter.get("TIME")%>" value="<%=twitter.get("SM_CNT3")%>" readonly><label for="ui_input_0<%=i+1%>_0<%=i%>" class="invisible">반응도</label></td>
					<td><span class="<%=classStr3[0]%>"> </span><span class="<%=classStr3[1]%>"><%=twitter.get("DIF_SM_CNT3")%></span></td>
					<%-- <td><a id="T_modBtn_<%=twitter.get("TIME")%>" href="javascript:socialMod('T','<%=twitter.get("TIME")%>');" class="ui_btn_04 ui_shadow_00">수정</a><a id="T_savBtn_<%=twitter.get("TIME")%>" style="display:none;" href="javascript:socialSave('T','<%=twitter.get("TIME")%>');" class="ui_btn_04 ui_shadow_00">저장</a></td> --%>
				</tr>
			
		<%	}
		}else{%>
		<tr height="25">
			<td align="center" bgcolor="#FFFFFF" colspan="8">데이터가 없습니다.</td>
		</tr>
		<%}
		%>
			</tbody>
			</table>
		</div>
		<!-- 트위터 -->

		<!-- 카카오 -->
		<div data-flag="K" class="article ui_table_00 m_t_30">
			<h4 style="display:inline"><span class="icon">-</span>카카오톡</h4>
			<a href="#" class="ui_btn_04 ui_shadow_00" onclick="del(this);" style="float:right"><span class="icon confirm">-</span>삭제</a>
			<table summary="카카오톡">
			<caption>카카오톡</caption>
			<colgroup>
				<col style="width:12%">
				<col style="width:12%">
				<col >
				<col >
				<col >
				<col >
				<col >
				<col >
				<!-- <col style="width:10%"> -->
			</colgroup>
			<thead>
				<tr>
					<th scope="col">날짜</th>
					<th scope="col">기준시간</th>
					<th scope="col" colspan="2">팬수</th>
					<th scope="col" colspan="2">확산도</th>
					<th scope="col" colspan="2">반응도</th>
					<!-- <th scope="col">처리</th> -->
				</tr>
			</thead>
			<tbody>
			<%
			if(kakao != null){
				String checked = "";
				String classStr1[] = {"",""};
				String classStr2[] = {"",""};
				String classStr3[] = {"",""};
				String DIF_SM_CNT1 = "";
				String DIF_SM_CNT2 = "";
				String DIF_SM_CNT3 = "";
				boolean chk = false;
				
				for(int i = 0; i < 1; i++){
					
					chk = (kakao.get("DIF_SM_CNT1").toString()).startsWith("-");
					if(chk){
						DIF_SM_CNT1 = kakao.get("DIF_SM_CNT1").toString().replaceAll("-", "");
						classStr1[0] = "ui_icon_dn"; classStr1[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT1 = kakao.get("DIF_SM_CNT1").toString();
						classStr1[0] = "ui_icon_up"; classStr1[1] = "ui_icon_up_data";
					}
					
					chk = kakao.get("DIF_SM_CNT2").toString().startsWith("-");
					if(chk){
						DIF_SM_CNT2 = kakao.get("DIF_SM_CNT2").toString().replaceAll("-", "");
						classStr2[0] = "ui_icon_dn"; classStr2[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT2 = kakao.get("DIF_SM_CNT2").toString();
						classStr2[0] = "ui_icon_up"; classStr2[1] = "ui_icon_up_data";
					}
					
					chk = kakao.get("DIF_SM_CNT3").toString().startsWith("-");
					if(chk){
						DIF_SM_CNT3 = kakao.get("DIF_SM_CNT3").toString().replaceAll("-", "");
						classStr3[0] = "ui_icon_dn"; classStr3[1] = "ui_icon_dn_data";
					}else{
						DIF_SM_CNT3 = kakao.get("DIF_SM_CNT3").toString();
						classStr3[0] = "ui_icon_up"; classStr3[1] = "ui_icon_up_data";
					}
			%>
			
				<tr>
					<td><%=kakao.get("DATE")%><input type="hidden" name="K_date_<%=kakao.get("TIME")%>" value="<%=kakao.get("DATE")%>" /></td>
					<td>
						<select id="input_select_time_00_0<%=i%>" class="ui_select_02 K_time_<%=kakao.get("TIME")%>" style="width:70px" disabled="disabled">
							<%for(int t=1; t < 25; t++) {%>
							<option value="<%=t%>" <%if(kakao.get("TIME").toString().equals(t+"")){out.print("selected");} %> > <%=t%>시</option>
							<%} %>
						</select><label for="input_select_time_00_0<%=i%>" class="invisible">기준시간</label><span><%=kakao.get("TIME")%>시</span>
					</td>
					<td><input name="K_data1_<%=kakao.get("TIME")%>" id="ui_input_0<%=i%>_0<%=i%>" type="text" class="ui_input_00 K_input_<%=kakao.get("TIME")%>" value="<%=kakao.get("SM_CNT1")%>" readonly><label for="ui_input_0<%=i%>_0<%=i%>" class="invisible">팬수</label></td>
					<td><span class="<%=classStr1[0]%>"> </span><span class="<%=classStr1[1]%>"><%=kakao.get("DIF_SM_CNT1")%></span></td>
					<td><input name="K_data2_<%=kakao.get("TIME")%>" id="ui_input_0<%=i+1%>_0<%=i%>" type="text" class="ui_input_00 K_input_<%=kakao.get("TIME")%>" value="<%=kakao.get("SM_CNT2")%>" readonly><label for="ui_input_0<%=i+1%>_0<%=i%>" class="invisible">확산도</label></td>
					<td><span class="<%=classStr2[0]%>"> </span><span class="<%=classStr2[1]%>"><%=kakao.get("DIF_SM_CNT2")%></span></td>
					<td><input name="K_data2_<%=kakao.get("TIME")%>" id="ui_input_0<%=i+1%>_0<%=i%>" type="text" class="ui_input_00 K_input_<%=kakao.get("TIME")%>" value="<%=kakao.get("SM_CNT3")%>" readonly><label for="ui_input_0<%=i+1%>_0<%=i%>" class="invisible">반응도</label></td>
					<td><span class="<%=classStr3[0]%>"> </span><span class="<%=classStr3[1]%>"><%=kakao.get("DIF_SM_CNT3")%></span></td>
					<%-- <td><a id="K_modBtn_<%=kakao.get("TIME")%>" href="javascript:socialMod('K','<%=kakao.get("TIME")%>');" class="ui_btn_04 ui_shadow_00">수정</a><a id="K_savBtn_<%=kakao.get("TIME")%>" style="display:none;" href="javascript:socialSave('K','<%=kakao.get("TIME")%>');" class="ui_btn_04 ui_shadow_00">저장</a></td> --%>
				</tr>
			
		<%	}
		}else{%>
		<tr height="25">
			<td align="center" bgcolor="#FFFFFF" colspan="8">데이터가 없습니다.</td>
		</tr>
		<%}
		%>
			</tbody>
			</table>
		</div>
		<!-- 카카오 -->


	</div>
<iframe id="prcFrame" name="prcFrame" width="0" height="0"></iframe>	
</body>
</html>
