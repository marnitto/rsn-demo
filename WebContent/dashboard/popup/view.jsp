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
				<h2><span><strong><span class="ui_bullet_02">-</span> View</strong></span></h2>
				<div class="title_rc">
					<a href="./write.jsp" class="ui_btn_02 ui_shadow_01" title="수정"><span>수정</span></a>
					<a href="#" class="ui_btn_02 ui_shadow_01" title="삭제"><span>삭제</span></a>
				</div>
				<div class="content">
					<div class="ui_board_view_00">
						<div class="titles">
							<span class="title">제목 테스트제목 테스트제목 테스트제목 테스트제목 테스트제목 테스트제목 테스트제목 테스트제목 테스트제목 테스트제목 테스트</span>
							<div class="infos">
								<span class="writer">홍길동</span>
								<span class="date">2016-01-01</span>
							</div>
						</div>
						<div class="txts">
							내용입력<br>
						</div>
						<div class="files">
							<strong><span class="ui_bullet_file" title="첨부파일">첨부파일</span>첨부파일</strong>
							<span class="file_item">파일이름.jpg</span>
						</div>
					</div>
					<div class="btns">
						<a href="./notice.jsp" class="ui_btn_02 ui_shadow_01"><span>목록</span></a>
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