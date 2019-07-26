<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<title>팝업컨텐츠</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="../asset/css/normalize.css">
<link rel="stylesheet" type="text/css" href="../asset/css/design.css">
</head>

<body>
	<!-- Popup -->
	<div id="popup" style="display:block">
		<h2 class="invisible">팝업 컨텐츠</h2>
		<div class="bg"></div>
		<div class="container ui_shadow">
			<section style="width:700px">
				<div class="header">
					<h3>관련정보</h3>
					<a href="#" class="close" onclick="hndl_popupClose();return false;">팝업닫기</a>
				</div>
				<div class="content">
					<div class="ui_board_list_00">
						<div class="ui_board_header f_clear">
							<div class="fr">
								<button type="button" class="ui_btn_02 ui_shadow_01" title="등록" onclick="hndl_m_popup2Open('issue_detail_mngr')"><span class="icon plus">등록</span></button>
							</div>
						</div>
						<table>
						<caption>연관키워드 목록</caption>
						<colgroup>
						<col style="width:120px">
						<col style="width:120px">
						<col>
						</colgroup>
						<thead>
						<tr>
						<th scope="col"><span>날짜</span></th>
						<th scope="col"><span>구분</span></th>
						<th scope="col"><span>내용</span></th>
						</tr>
						</thead>
						<tbody>
						<tr>
						<td>2016-01-01</td>
						<td>구분</td>
						<td class="al"><a href="#" target="_blank">코웨이 정수기 더 늦기 전에!</a></td>
						</tr>
						<tr>
						<td>2016-01-01</td>
						<td>구분</td>
						<td class="al"><a href="#" target="_blank">코웨이 정수기 더 늦기 전에!</a></td>
						</tr>
						<tr>
						<td>2016-01-01</td>
						<td>구분</td>
						<td class="al"><a href="#" target="_blank">코웨이 정수기 더 늦기 전에!</a></td>
						</tr>
						<tr>
						<td>2016-01-01</td>
						<td>구분</td>
						<td class="al"><a href="#" target="_blank">코웨이 정수기 더 늦기 전에!</a></td>
						</tr>
						<tr>
						<td>2016-01-01</td>
						<td>구분</td>
						<td class="al"><a href="#" target="_blank">코웨이 정수기 더 늦기 전에!</a></td>
						</tr>
						<tr>
						<td>2016-01-01</td>
						<td>구분</td>
						<td class="al"><a href="#" target="_blank">코웨이 정수기 더 늦기 전에!</a></td>
						</tr>
						<tr>
						<td>2016-01-01</td>
						<td>구분</td>
						<td class="al"><a href="#" target="_blank">코웨이 정수기 더 늦기 전에!</a></td>
						</tr>
						<tr>
						<td>2016-01-01</td>
						<td>구분</td>
						<td class="al"><a href="#" target="_blank">코웨이 정수기 더 늦기 전에!</a></td>
						</tr>
						<tr>
						<td>2016-01-01</td>
						<td>구분</td>
						<td class="al"><a href="#" target="_blank">코웨이 정수기 더 늦기 전에!</a></td>
						</tr>
						<tr>
						<td>2016-01-01</td>
						<td>구분</td>
						<td class="al"><a href="#" target="_blank">코웨이 정수기 더 늦기 전에!</a></td>
						</tr>
						</tbody>
						</table>
					</div>
					<div class="paginate">
						<a href="#" class="page_prev disabled">이전페이지</a>		<!-- 버튼 활성화시 disabled 클래스 제거 -->
						<a href="#" class="active" onclick="popupOpen( '../popup/rel_info.jsp' );return false;">1</a>
						<a href="#" onclick="popupOpen( '../popup/rel_info.jsp' );return false;">2</a>
						<a href="#">3</a>
						<a href="#">4</a>
						<a href="#">5</a>
						<a href="#">6</a>
						<a href="#">7</a>
						<a href="#">8</a>
						<a href="#">9</a>
						<a href="#">10</a>
						<a href="#" class="page_next">다음페이지</a>
					</div>

					<!-- Mini Popup -->
					<div id="m_popup">
						<div class="bg"></div>
						<section class="popup issue_detail_mngr">
							<h4>이슈 상세관리</h4>
							<button type="button" class="btn_close" onclick="hndl_m_popup2Close()"><span class="icon">닫기</span></button>
							<div class="content">
								<div>
									<div class="dcp">
										<select id="m_pop_01_sel_01" class="ui_hidden" style="width:200px">
											<option selected>장소선택</option>
										</select><label for="m_pop_01_sel_01" class="select_00"></label>
									</div>
									<div class="dcp"><input type="text" id="m_pop_01_dp_01" class="ui_datepicker_input vt" value="2015-07-01"><label for="m_pop_01_dp_01" class="invisible">날짜선택</label></div>
								</div>
								<div class="p_t_05">
									<input id="m_pop_01_input_01" type="text" class="ui_input_00" style="width:400px"><label for="m_pop_01_input_01" class="invisible">상세내용 입력</label>
								</div>
								<div class="btns">
									<button class="ui_btn_03"><span>저장</span></button>
									<button class="ui_btn_03" onclick="hndl_m_popup2Close()"><span>취소</span></button>
								</div>
							</div>
						</section>
					</div>
					<!-- //Mini Popup -->

					<!-- Loader -->
					<div class="ui_loader v0"><span class="loader">Load</span></div>
					<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
					<!-- // Loader -->
				</div>
			</section>
		</div>
	</div>
	<!-- // Popup -->
</body>
</html>