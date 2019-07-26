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
				<h2><span><strong><span class="ui_bullet_02">-</span> Write</strong></span></h2>
				<div class="content">
					<div class="ui_board_write_00">
					<form action="#">
						<fieldset>
							<label class="invisible" for="input_title">제목</label><input type="text" id="input_title" class="ui_input_00 ui_wid100p" placeholder="제목">
						</fieldset>
						<fieldset>
							<label class="invisible" for="input_txts">내용</label><textarea id="input_txts" class="ui_textarea_00"></textarea>
						</fieldset>
						<fieldset>
							<div class="input_file"><label for="input_file"><span class="ui_input_00 txts">파일을 선택하세요</span><span class="ui_btn_02 ui_shadow_01"><span>찾아보기</span></span></label><input type="file" id="input_file"></div>
							<script type="text/javascript">
								$( "#input_file" ).each( function(){
									var tg = $( this );
									fileChange();
									$( this ).change( fileChange );
									function fileChange(){
										if( tg.prop( 'files' )[ 0 ] ) {
											$( ".input_file .ui_input_00.txts" ).text( tg.prop( 'files' )[ 0 ].name );
										}
									}
								});
							</script>
						</fieldset>
					</div>
					<div class="btns">
						<button type="button" class="ui_btn_02 ui_shadow_01"><span>취소</span></button>
						<button type="button" class="ui_btn_02 ui_shadow_01"><span>저장</span></button>
					</div>
					</form>

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