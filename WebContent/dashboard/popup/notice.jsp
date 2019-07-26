<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<title>오리온 - 공지사항</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="shortcut icon" href="../asset/img/favicon.ico">
<link rel="stylesheet" type="text/css" href="../asset/css/normalize.css">
<link rel="stylesheet" type="text/css" href="../asset/css/design.css">
<script type="text/javascript" src="../asset/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="../asset/js/jquery-ui.min.js"></script>
<!--[if lte IE 8]>
	<script type="text/javascript" src="../asset/js/ie_chk.js"></script>
<![endif]-->
</head>

<body class="r_pop">
	<!-- Popup -->
	<div class="popup_wrap">
		<h1><img src="../asset/img/popup_notice/h1_logo.gif" alt="공지사항"></h1>
		<div class="container">
			<section>
				<h2><span><strong><span class="ui_bullet_02">-</span> 총 1659건</strong>, 1/166 pages</span></h2>
				<div class="title_rc">
					<a href="./write.jsp" class="ui_btn_02 ui_shadow_01" title="글쓰기"><span>글쓰기</span></a>
				</div>
				<div class="content">
					<div class="ui_board_list_00">
						<table>
						<caption>목록</caption>
						<colgroup>
						<col>
						<col style="width:120px">
						<col style="width:80px">
						</colgroup>
						<thead>
						<tr>
						<th scope="col"><span>제목</span></th>
						<th scope="col"><span>글쓴이</span></th>
						<th scope="col"><span>날짜</span></th>
						</tr>
						</thead>
						<tbody>
						<tr>
						<td class="al"><a href="view.jsp">코웨이 정수기 더 늦기 전에코웨이 정수기 더 늦기 전에코웨이 정수기 더 늦기 전에코웨이 정수기 더 늦기 전에코웨이 정수기 더 늦기 전에!</a><span class="ui_bullet_file" title="첨부파일">첨부파일</span></td>
						<td><span>손석희</span></td>
						<td><span>2016-12-12</span></td>
						</tr>
						<tr>
						<td class="al"><a href="view.jsp">코웨이 정수기 더 늦기 전에!</a><span class="ui_bullet_new" title="신규등록글">NEW</span></td>
						<td><span>손석희</span></td>
						<td><span>2016-12-12</span></td>
						</tr>
						<tr><td colspan="3"></td></tr>
						<tr><td colspan="3"></td></tr>
						<tr><td colspan="3"></td></tr>
						<tr><td colspan="3"></td></tr>
						<tr><td colspan="3"></td></tr>
						<tr><td colspan="3"></td></tr>
						<tr><td colspan="3"></td></tr>
						<tr><td colspan="3"></td></tr>
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
					<div class="search_wrap">
						<label for="search_input"><input type="text" id="search_input" class="ui_input_00"></label>
						<button type="button" class="ui_btn_02 ui_shadow_01"><span class="icon search">-</span><span>검색</span></button>
					</div>

					<!-- Loader -->
					<div class="ui_loader v0"><span class="loader">Load</span></div>
					<!-- // Loader -->
				</div>
			</section>
		</div>
	</div>
	<!-- // Popup -->
</body>
</html>