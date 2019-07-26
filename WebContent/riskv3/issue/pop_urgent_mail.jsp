<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../riskv3/inc/sessioncheck.jsp" %>
<%@ page import="risk.issue.IssueMgr
				 ,risk.issue.IssueDataBean
				 ,risk.issue.IssueCodeBean
				 ,risk.issue.IssueCodeMgr
				 ,risk.issue.IssueCommentBean
				 ,risk.util.StringUtil
                 ,risk.util.DateUtil
                 ,risk.util.ParseRequest
                 ,risk.admin.member.MemberBean
                 ,risk.admin.member.MemberDao
                 ,risk.sms.AddressBookDao
               	 ,risk.sms.AddressBookGroupBean
               	 ,risk.admin.membergroup.membergroupBean              
               	 ,java.util.Iterator
               	 ,java.util.List                    
                 ,java.util.ArrayList"                  
%>
<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	IssueMgr issueMgr = new IssueMgr();
	ArrayList arrIcBean = null;
	ArrayList arrIdBean = null;
	ArrayList arrIdcBean =  null;
	
	int chk_cnt = 0;
	int nowPage = 0;	
	int pageCnt = 0;
	int totalCnt = 0;
	int totalPage = 0;	
	
	String searchKey = pr.getString("searchKey","");
	String i_seq = pr.getString("i_seq","");
	String it_seq = pr.getString("it_seq","");
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	String typeCode = pr.getString("typeCode");
	String m_seq = pr.getString("m_seq",SS_M_NO);
	String check_no = pr.getString("check_no","");
	String srtMsg = null;
	
	//IssueCodeMgr 인스턴스 생성
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	IssueCodeBean icBean = null;	
	icMgr.init(0);		
		
	//관련정보 리스트
	IssueDataBean idBean = null;
	arrIdBean = issueMgr.getIssueDataList(nowPage,pageCnt,check_no,i_seq,it_seq,"","","1",sDateFrom,"",sDateTo,"",typeCode,"","Y","","Y","","");
	
	//시스템 멤버 그룹 
	AddressBookDao abDao = new AddressBookDao();
	AddressBookGroupBean abgBean = new AddressBookGroupBean();
	List abgGroupList = new ArrayList();
	abgGroupList = abDao.getAdressBookGroup();
	//Iterator it = abgGroupList.iterator();
	
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
<script type="text/javascript" src="<%=SS_URL%>dashboard/asset/js/jquery.multi_selector.js"></script>
<script type="text/javascript" src="<%=SS_URL%>dashboard/asset/js/design.js"></script>
<script type="text/javascript" src="<%=SS_URL%>js/ajax.js"></script>
<!--[if (gte IE 6)&(lte IE 8)]>
	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<script type="text/javascript" src="../asset/js/selectivizr-min.js"></script>
<![endif]-->
<script type="text/javascript">
$(function(){
	getMailReceiverList();	//메일 수신자 전체 목록 가져오기
	loading();
});

function getMailReceiverList(){
	var param = $("#frm").serialize();
	$.post("aj_getMailReceiverList.jsp"
			,param
			,function(data){
				$("#ui_multi_selector_00").html(data);
				selectDataMove_Append();
				selectDataMove_Remove();
			});
}

//선택된 수신자 메일 보내기
function sendTomail(){
	var abSeq = "";
	$("#ui_multi_selector_01 > li").not(".disable").each(function(){
		if(abSeq == ""){
			abSeq += $(this).attr("user_data_value");	
		}else{
			abSeq += ","+$(this).attr("user_data_value");
		}
	});
	
	if(abSeq == ""){
		alert("메일 수신 대상자를 선택해주세요.");
		return;
	}else{
		
		$("[name=mailreceiver]").val(abSeq);
		var formData = $("#frm").serialize();
		$.ajax({
			type : "POST"
			,url: "issue_data_prc.jsp"
			,timeout: 30000
			,data : formData
			,dataType : 'text'
			,async: true
			,success : function(data){
						if(data == "fail" ){
							alert("메일 발송에 실패하였습니다.");
							window.close();
						}else{
							alert("메일을 발송하였습니다.");
							window.close();
						}
					
					  }
			,beforeSend : function(){
						$("#sending").css("display", "");
					}			
			});	
	}
	
}

</script>
</head>
<body>
	<div id="popup_wrap">
		<h1 class="invisible">금융감독원</h1>
		<h2>이슈 메일 발송</h2>
		<a href="#" class="close">닫기</a>
	<form name="frm" id="frm" method="post">
	<input type="hidden" name="mode" value="mail"><!-- 메일발송 모드 -->
	<input type="hidden" name="ir_type" value="E"><!-- 긴급보고서 타입-->
	<input type="hidden" name="id_seq" value="<%=check_no%>"> <!-- 기사 -->
	<input type="hidden" name="mailreceiver" value=""><!-- 메일 대상자 -->
	<input type="hidden" name="asSeq" /><!-- 메일수신자 그룹 -->
		<!-- 메일 발송 목록 -->
		<div class="article m_t_10">
			<h3><span class="icon">-</span>메일 발송 이슈</h3>
			<div class="ui_table_02">
				<table summary="메일 발송 목록">
				<caption>메일 발송 목록</caption>
				<colgroup>
					<col style="width:150px">
					<col>
					<col style="width:120px">
					<col style="width:70px">
				</colgroup>
				<thead>
					<tr>
						<th><span>출처</span></th>
						<th><span>제목</span></th>
						<th><span>수집일시</span></th>
						<th><span>성향</span></th>
					</tr>
				</thead>
				<tbody>
				<%
				if( arrIdBean.size() > 0 ){
					String sunghyang = "";
					for(int i =0; i < arrIdBean.size(); i++){	
						idBean = (IssueDataBean)arrIdBean.get(i);
						arrIdcBean = (ArrayList) idBean.getArrCodeList();
						sunghyang = icMgr.GetCodeNameType(arrIdcBean,9);
				%>
					<tr>
						<td><%=idBean.getMd_site_name()%></td>
						<td class="nowrap al"><input type="hidden" name="ir_title" value="<%=idBean.getId_title()%>" /><%=idBean.getId_title()%></td>
						<td><%=idBean.getFormatMd_date("yyyy-MM-dd")%></td>
						<td><%=sunghyang%></td>
					</tr>
				<%
					}
				}
				%>
				</tbody>
				</table>
			</div>
		</div>
</form>		
		<!-- // 메일 발송 목록 -->

		<!-- 메일 발송 대상 -->
		<div class="article m_t_20">
			<h3><span class="icon">-</span>메일 발송 설정</h3>
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
							<h4>메일 수신자 그룹</h4>
							<div class="floatbox grouping_box">
								<div class="floatbox_left"><h5><span class="icon">-</span>그룹선택</h5></div>
								<div class="floatbox_right">
									<select id="input_select_grouping_00" class="ui_select_03" name="groupId">
										<option value="all">전체</option>
<%
	//while(it.hasNext()){
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
								<ul id="ui_multi_selector_00" class="ui_select_multi_00" style="height:200px">
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
							<h4>메일 대상자 그룹</h4>
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
								<ul id="ui_multi_selector_01" class="ui_select_multi_00" style="height:200px">
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
		</div>
		<!-- // 메일 발송 대상 -->
		<div class="bot_btns">
			<button class="ui_btn_05 ui_shadow_00" onclick="sendTomail();"><span class="icon mail">-</span>발송하기</button>
		</div>
	</div>
	<img id="sending" src="../../images/issue/sending.gif" style="position: absolute;left:50%;top:50%;margin:-65px 0 0 -100px;display:none;" >
</body>
</html>
