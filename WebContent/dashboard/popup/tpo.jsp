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
			<section style="width:500px">
				<div class="header">
					<h3>해변 TPO</h3>
					<a href="#" class="close" onclick="hndl_m_popupClose( this );return false;">팝업닫기</a>
				</div>
				<div class="content">
					<div class="ui_board_list_01">
						<table>
						<caption>관련정보 목록</caption>
						<colgroup>
						<col style="width:45px">
						<col>
						<col style="width:50px">
						<col>
						<col style="width:50px">
						<col>
						<col style="width:50px">
						</colgroup>
						<thead>
						<tr>
						<th scope="col" rowspan="2"><span>No.</span></th>
						<th scope="col" colspan="2"><span>시간</span></th>
						<th scope="col" colspan="2"><span>대상</span></th>
						<th scope="col" colspan="2"><span>상황</span></th>
						</tr>
						<tr>
						<th scope="col"><span>키워드</span></th>
						<th scope="col"><span>수량</span></th>
						<th scope="col"><span>키워드</span></th>
						<th scope="col"><span>수량</span></th>
						<th scope="col"><span>키워드</span></th>
						<th scope="col"><span>수량</span></th>
						</tr>
						</thead>
						<tbody>
						<tr>
						<td>1</td>
						<td><strong>키워드</strong></td>
						<td><a href="#" onclick="popupOpen( '../popup/rel_info.jsp', 'a=1' );return false;">99,999</a></td>
						<td><strong>키워드</strong></td>
						<td>99,999</td>
						<td><strong>키워드</strong></td>
						<td>99,999</td>
						</tr>
						<tr>
						<td>2</td>
						<td><strong>키워드</strong></td>
						<td>99,999</td>
						<td><strong>키워드</strong></td>
						<td>99,999</td>
						<td><strong>키워드</strong></td>
						<td>99,999</td>
						</tr>
						<tr>
						<td>3</td>
						<td><strong>키워드</strong></td>
						<td>99,999</td>
						<td><strong>키워드</strong></td>
						<td>99,999</td>
						<td><strong>키워드</strong></td>
						<td>99,999</td>
						</tr>
						<tr>
						<td>4</td>
						<td><strong>키워드</strong></td>
						<td>99,999</td>
						<td><strong>키워드</strong></td>
						<td>99,999</td>
						<td><strong>키워드</strong></td>
						<td>99,999</td>
						</tr>
						<tr>
						<td>5</td>
						<td>-</td>
						<td></td>
						<td>-</td>
						<td></td>
						<td>-</td>
						<td></td>
						</tr>
						</tbody>
						</table>
					</div>
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