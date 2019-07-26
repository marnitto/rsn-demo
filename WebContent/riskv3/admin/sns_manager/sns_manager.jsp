<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../inc/sessioncheck.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.util.DateUtil"%>
<%@ page import="risk.util.StringUtil"%>
<%@ page import="risk.admin.sns_manager.snsManagerMgr"%>
<%@ page import="risk.dashboard.po.snsMgr"%>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();

	DateUtil du = new DateUtil();
	snsManagerMgr im = new snsManagerMgr();
	StringUtil su = new StringUtil();
	
	snsMgr sMgr= new snsMgr();

	//검색날짜 설정 : 전일 대비 결과를 위해 조회날짜와 그 전일 날짜를 세팅한다.
	String cDate = du.getCurrentDate();
	String currentDate = pr.getString("sDate", du.getCurrentDate());
	String s_type = pr.getString("s_type","B");
	du.setDate(currentDate);
	du.addDay(-1);
	String startDate = du.getDate();
	
	int nowpage_B = pr.getInt("nowpage_B", 1);
	int nowpage_F = pr.getInt("nowpage_F", 1);
	int nowpage_T = pr.getInt("nowpage_T", 1);
	int nowpage_K = pr.getInt("nowpage_K", 1);

	int rowCnt = 10;
	int totalPage_B = 0;
	int totalPage_F = 0;
	int totalPage_T = 0;
	int totalPage_K = 0;
	
	int totalCnt_B = im.getSnsManagerInfoCount(currentDate, "B");
	ArrayList list = im.getSnsManagerInfo(nowpage_B,rowCnt,currentDate, "B");
	totalPage_B = totalCnt_B / rowCnt;
	if(totalCnt_B % rowCnt > 0){totalPage_B += 1;}
	
	int totalCnt_F = im.getSnsManagerInfoCount(currentDate, "F");
	ArrayList list1 = im.getSnsManagerInfo(nowpage_F,rowCnt,currentDate, "F");
	totalPage_F = totalCnt_F / rowCnt;
	if(totalCnt_F % rowCnt > 0){totalPage_F += 1;}
	
	
	int totalCnt_T = im.getSnsManagerInfoCount(currentDate, "T");
	ArrayList list2 = im.getSnsManagerInfo(nowpage_T,rowCnt,currentDate, "T");
	totalPage_T = totalCnt_T / rowCnt;
	if(totalCnt_T % rowCnt > 0){totalPage_T += 1;}
	
	
	int totalCnt_K = im.getSnsManagerInfoCount(currentDate, "K");
	ArrayList list3 = im.getSnsManagerInfo(nowpage_K,rowCnt,currentDate, "K");
	totalPage_K = totalCnt_K / rowCnt;
	if(totalCnt_K % rowCnt > 0){totalPage_K += 1;}
	
	
	String active = "";
	String active1 = "";
	String active2 = "";
	String active3 = "";
	if(s_type.equals("B")){
		active = "active";
	}else if(s_type.equals("F")){
		active1 = "active";
	}else if(s_type.equals("T")){
		active2 = "active";
	}else{
		active3 = "active";
	}

%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title></title>
<style type="text/css">
.paginate{position:relative;padding:20px 0 0;text-align:center;letter-spacing:-4px}
.paginate > a:not(:first-child){display:inline-block;*display:inline;*zoom:1;height:16px;padding:0 8px;border-right:1px solid #e2e2e2;color:#7d7d7d;line-height:14px;letter-spacing:0}
.paginate > a:nth-child(2){border-left:1px solid #cccccc}
.paginate > a:hover{color:#007bbb}
.paginate > a.active{color:#007bbb;font-weight:bold}
.paginate > a:last-child{border-right:none}
.ui_board_list_00.sns_nb + .paginate > a:hover{color:#2db400}
.ui_board_list_00.sns_nb + .paginate > a.active{color:#2db400}
.ui_board_list_00.sns_fb + .paginate > a:hover{color:#3a569d}
.ui_board_list_00.sns_fb + .paginate > a.active{color:#3a569d}
.ui_board_list_00.sns_twt + .paginate > a:hover{color:#52c3f1}
.ui_board_list_00.sns_twt + .paginate > a.active{color:#52c3f1}
.ui_board_list_00.sns_ks + .paginate > a:hover{color:#ef4d30}
.ui_board_list_00.sns_ks + .paginate > a.active{color:#ef4d30}
.paginate a.page_prev{display:inline-block;*display:inline;*zoom:1;width:20px;height:16px;padding:0;background:url(../../../img/ui_board.png) no-repeat 8px 4px;text-indent:-9999px;vertical-align:top}
.paginate a.page_next{display:inline-block;*display:inline;*zoom:1;width:20px;height:16px;padding:0;background:url(../../../img/ui_board.png) no-repeat -6px 4px;text-indent:-9999px;vertical-align:top}
.paginate a.page_prev:hover{background-position:8px -14px}
.paginate a.page_next:hover{background-position:-6px -14px}
</style>
<link rel="stylesheet" type="text/css" href="<%=SS_URL%>css/design.css">
<script type="text/javascript" src="<%=SS_URL%>dashboard/asset/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="<%=SS_URL%>dashboard/asset/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=SS_URL%>js/design.js"></script>
<!--[if (gte IE 6)&(lte IE 8)]>
	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<script type="text/javascript" src="../asset/js/selectivizr-min.js"></script>
<![endif]-->
<script type="text/javascript">
	function save(mode){
		$("[name=mode]").val(mode);
		$("#frm").submit();
	}
	
	function updateSetting(s_seq,s_date,s_content,s_cnt1,s_cnt2,s_type){
		$("[name=s_date]").val(s_date);
		$("[name=s_content]").val(s_content);
		$("[name=s_cnt1]").val(s_cnt1);
		$("[name=s_cnt2]").val(s_cnt2);
		$("[name=s_type]").val(s_type);
		$("[name=s_seq]").val(s_seq);
	}
	
	function getSearch(){
		$("#searchFrm").submit();
	}
	
	function del(){
		
		var id = $(".ui_tab_00").find(".li_tab_00").find(".tab").closest(".active").attr("id");
		
		var seqs = "";
		
		$("[name=chk_"+id+"]").each(function(){
			if( $(this).prop("checked") ){
				if(seqs == ""){
					seqs = $(this).val();
				}else{
					seqs += ","+$(this).val();	
				}
			}
		});
		
		$("#frm").find("[name=mode]").val("del");
		$("#frm").find("[name=s_seq]").val(seqs);
		$("#frm").submit();
		
	}
	
	function pageClick_B(nowpage){
		
		$("#searchFrm").find("[name=s_type]").val("B");
		$("#searchFrm").find("[name=nowpage_B]").val(nowpage);
		
		$("#searchFrm").submit();
		
	}
	function pageClick_F(nowpage){
		$("#searchFrm").find("[name=s_type]").val("F");
		$("#searchFrm").find("[name=nowpage_F]").val(nowpage);
		
		$("#searchFrm").submit();		
	}
	function pageClick_T(nowpage){
		$("#searchFrm").find("[name=s_type]").val("T");
		$("#searchFrm").find("[name=nowpage_T]").val(nowpage);
		
		$("#searchFrm").submit();		
	}
	function pageClick_K(nowpage){
		$("#searchFrm").find("[name=s_type]").val("K");
		$("#searchFrm").find("[name=nowpage_K]").val(nowpage);
		
		$("#searchFrm").submit();		
	}
</script>
</head>
<body>
	<div id="fr_content">
		<!-- 페이지 타이틀 -->
		<h3><span class="icon">-</span><img src="<%=SS_URL%>img/h3_admin_sns_stat.gif" alt=""></h3>
		<!-- // 페이지 타이틀 -->

		<!-- Locator -->
		<div class="ui_locator">
			<span class="home"></span>HOME<span class="arrow">></span>관리자<span class="arrow">></span><strong>SNS정보 추이 현황 관리</strong>
		</div>
		<!-- // Locator -->

		<!-- 검색 -->
		<form name="searchFrm" id="searchFrm" action="sns_manager.jsp" method="post">
		<input type="hidden" name="nowpage_B" value="1" />
		<input type="hidden" name="nowpage_F" value="1" />
		<input type="hidden" name="nowpage_T" value="1" />
		<input type="hidden" name="nowpage_K" value="1" />
		<input type="hidden" name="s_type" value="" />
		
		<div class="article ui_searchbox_00">
			<div class="c_wrap">
				<div class="ui_row_00">
					<span class="title"><span class="icon">-</span>검색기간</span>
					<span class="txts">
						<input id="sDate" name="sDate" type="text" class="ui_datepicker_input input_date_first" value="<%=currentDate%>" readonly><label for="sDate">날짜입력</label>
						<button class="ui_btn_02" onclick="getSearch();">검색</button>
					</span>
				</div>
			</div>
		</div>
		</form>
		<!-- // 검색 -->

		<!-- 입력 -->
		<div class="article m_t_30" style="height: 440px;">
			<div class="ui_tab_00">
				<ul>
				<li class="li_tab_00">
						<a href="#" class="tab <%=active%>" id="B">블로그</a>
						<div class="tab_content board_list_00">
							<table summary="페이스북" style="border-top:2px solid #5b799f">
							<caption>페이스북</caption>
							<colgroup>
								<col style="width:60px">
								<col style="width:110px">
								<col>
								<col style="width:90px">
								<col style="width:90px">
							</colgroup>
							<thead>
							<tr>
							<th scope="col"><input type="checkbox" name="all_chk_B"/></th>
							<th scope="col">날짜</th>
							<th scope="col">내용</th>
							<th scope="col">확산도</th>
							<th scope="col">반응도</th>
							</tr>
							</thead>
							<tbody>
							<%if(list.size() > 0){
								Map map = null;
								for(int i=0; i < list.size(); i++){
									map = new HashMap();
									map = (HashMap)list.get(i);
							%>
							<tr>
							<td><input type="checkbox" name="chk_B" value="<%=map.get("S_SEQ")%>" /></td>
							<td><%=map.get("S_DATE")%></td>
							<td class="al" style="cursor: pointer;" 
							onclick="updateSetting('<%=map.get("S_SEQ")%>','<%=map.get("S_DATE")%>','<%=map.get("S_CONTENT")%>','<%=map.get("S_CNT1")%>','<%=map.get("S_CNT2")%>','<%=map.get("S_TYPE") %>');"
							><%=map.get("S_CONTENT")%></td>
							<td><%=map.get("S_CNT1")%></td>
							<td><%=map.get("S_CNT2")%></td>
							</tr>
							<%}}else{%>
								<tr>
								<td colspan="5"> 데이터가 없습니다. </td>
								</tr>
							<%}
							%>
							</tbody>
							</table>
							<%
							out.print( sMgr.paginate(totalPage_B, nowpage_B, "_B") );
							%>
						</div>
						
					</li>
					<li class="li_tab_00">
						<a href="#" class="tab <%=active1%>" id="F">페이스북</a>
						<div class="tab_content board_list_00">
							<table summary="페이스북" style="border-top:2px solid #5b799f">
							<caption>페이스북</caption>
							<colgroup>
								<col style="width:60px">
								<col style="width:110px">
								<col>
								<col style="width:90px">
								<col style="width:90px">
							</colgroup>
							<thead>
							<tr>
							<th scope="col"><input type="checkbox" name="all_chk_F"/></th>
							<th scope="col">날짜</th>
							<th scope="col">내용</th>
							<th scope="col">확산도</th>
							<th scope="col">반응도</th>
							</tr>
							</thead>
							<tbody>
							<%if(list1.size() > 0){
								Map map = null;
								for(int i=0; i < list1.size(); i++){
									map = new HashMap();
									map = (HashMap)list1.get(i);
							%>
							<tr>
							<td><input type="checkbox" name="chk_F" value="<%=map.get("S_SEQ")%>" /></td>
							<td><%=map.get("S_DATE")%></td>
							<td class="al" style="cursor: pointer;" 
							onclick="updateSetting('<%=map.get("S_SEQ")%>','<%=map.get("S_DATE")%>','<%=map.get("S_CONTENT")%>','<%=map.get("S_CNT1")%>','<%=map.get("S_CNT2")%>','<%=map.get("S_TYPE") %>');"
							><%=map.get("S_CONTENT")%></td>
							<td><%=map.get("S_CNT1")%></td>
							<td><%=map.get("S_CNT2")%></td>
							</tr>
							<%}}else{%>
							<tr>
							<td colspan="5"> 데이터가 없습니다. </td>
							</tr>
							<%}
							%>
							</tbody>
							</table>
							<%
							out.print( sMgr.paginate(totalPage_F, nowpage_F, "_F") );
						%>
						</div>
						
					</li>
					<li class="li_tab_00">
						<a href="#" class="tab <%=active2%>" id="T">트위터</a>
						<div class="tab_content board_list_00">
							<table summary="페이스북" style="border-top:2px solid #5b799f">
							<caption>페이스북</caption>
							<colgroup>
								<col style="width:60px">
								<col style="width:110px">
								<col>
								<col style="width:90px">
								<col style="width:90px">
							</colgroup>
							<thead>
							<tr>
							<th scope="col"><input type="checkbox" name="all_chk_T"/></th>
							<th scope="col">날짜</th>
							<th scope="col">내용</th>
							<th scope="col">확산도</th>
							<th scope="col">반응도</th>
							</tr>
							</thead>
							<tbody>
							<%if(list2.size() > 0){
								Map map = null;
								for(int i=0; i < list2.size(); i++){
									map = new HashMap();
									map = (HashMap)list2.get(i);
							%>
							<tr>
							<td><input type="checkbox" name="chk_T" value="<%=map.get("S_SEQ")%>" /></td>
							<td><%=map.get("S_DATE")%></td>
							<td class="al" style="cursor: pointer;" 
							onclick="updateSetting('<%=map.get("S_SEQ")%>','<%=map.get("S_DATE")%>','<%=map.get("S_CONTENT")%>','<%=map.get("S_CNT1")%>','<%=map.get("S_CNT2")%>','<%=map.get("S_TYPE") %>');"
							><%=map.get("S_CONTENT")%></td>
							<td><%=map.get("S_CNT1")%></td>
							<td><%=map.get("S_CNT2")%></td>
							</tr>
							<%}} else{%>
								<tr>
								<td colspan="5"> 데이터가 없습니다. </td>
								</tr>
							<%}
							%>
							</tbody>
							</table>
							<%
							out.print( sMgr.paginate(totalPage_T, nowpage_T, "_T") );
						%>
						</div>
						
					</li>
					<li class="li_tab_00">
						<a href="#" class="tab <%=active3%>" id="K">카카오톡</a>
						<div class="tab_content board_list_00">
							<table summary="페이스북" style="border-top:2px solid #5b799f">
							<caption>페이스북</caption>
							<colgroup>
								<col style="width:60px">
								<col style="width:110px">
								<col>
								<col style="width:90px">
								<col style="width:90px">
							</colgroup>
							<thead>
							<tr>
							<th scope="col"><input type="checkbox" name="all_chk_K"/></th>
							<th scope="col">날짜</th>
							<th scope="col">내용</th>
							<th scope="col">확산도</th>
							<th scope="col">반응도</th>
							</tr>
							</thead>
							<tbody>
							<%if(list3.size() > 0){
								Map map = null;
								for(int i=0; i < list3.size(); i++){
									map = new HashMap();
									map = (HashMap)list3.get(i);
							%>
							<tr>
							<td><input type="checkbox" name="chk_K" value="<%=map.get("S_SEQ")%>" /></td>
							<td><%=map.get("S_DATE")%></td>
							<td class="al" style="cursor: pointer;" 
							onclick="updateSetting('<%=map.get("S_SEQ")%>','<%=map.get("S_DATE")%>','<%=map.get("S_CONTENT")%>','<%=map.get("S_CNT1")%>','<%=map.get("S_CNT2")%>','<%=map.get("S_TYPE") %>');"
							><%=map.get("S_CONTENT")%></td>
							<td><%=map.get("S_CNT1")%></td>
							<td><%=map.get("S_CNT2")%></td>
							</tr>
							<%}}else{%>
							<tr>
							<td colspan="5"> 데이터가 없습니다. </td>
							</tr>
						<%}
						%>
							</tbody>
							</table>
							<%
							out.print( sMgr.paginate(totalPage_K, nowpage_K, "_K") );
						%>
						</div>
						
					</li>
				</ul>
			</div>
		</div>
		<!-- <div class="ar p_r_10">
				<a href="javascript:del();" class="ui_btn_04 ui_shadow_00">삭제</a>
		</div> -->
		<!-- // 입력 -->

		<!-- 페이스북 -->
		<form method="post" action="sns_manager_prc.jsp" name="frm" id="frm" target="prcFrame">
		<input type="hidden" name="mode" value=""/>
		<input type="hidden" name="s_seq" value=""/>
		<div class="article ui_table_00 m_t_30">
			<h4><span class="icon">-</span>SNS정보추이 입력</h4>
			<table>
			<caption>정보추이 입력</caption>
			<colgroup>
				<col style="width:18%">
				<col >
				<col style="width:18%">
				<col >
				<!-- <col style="width:10%"> -->
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">SNS종류</th>
					<td class="al">
						<select id="sel_sns_type" name="s_type">
							<option value="B">블로그</option>
							<option value="F">페이스북</option>
							<option value="T">트위터</option>
							<option value="K">카카오톡</option>
						</select>
					</td>
					<th scope="row">날짜</th>
					<td class="al"><input id="s_date" name="s_date" type="text" class="ui_datepicker_input input_date_first" value="<%=currentDate%>" readonly><label for="inputDate">날짜입력</label></td>
				</tr>
				<tr>
					<th scope="row">내용</th>
					<td colspan="3" class="al"><input id="sns_input_00" name="s_content" type="text" class="ui_input_01" style="width:100%"><label for="sns_input_00" class="invisible">내용입력</label></td>
				</tr>
				<tr>
					<th scope="row">확산도</th>
					<td class="al"><input id="sns_input_01" type="text" name="s_cnt1" class="ui_input_01" style="width:100%"><label for="sns_input_01" class="invisible">확산도 입력</label></td>
					<th scope="row">반응도</th>
					<td class="al"><input id="sns_input_02" type="text" name="s_cnt2" class="ui_input_01" style="width:100%"><label for="sns_input_02" class="invisible">반응도 입력</label></td>
				</tr>
			</tbody>
			</table>
			<br/>
			<div class="ar p_r_10">
				<a href="javascript:del();" class="ui_btn_04 ui_shadow_00">삭제</a>
				<a href="javascript:save('update');" class="ui_btn_04 ui_shadow_00">수정</a>
				<a href="javascript:save('insert');" class="ui_btn_04 ui_shadow_00">저장</a>
			</div>
		</div>
		</form>
		<!-- // 기업 페이스북 -->

	</div>
<iframe id="prcFrame" name="prcFrame" width="0" height="0"></iframe>	
</body>
</html>
