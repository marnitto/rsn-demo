<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Excel Download Form & IFrame -->
<div class="excel_prc_container">
	<form name="proceeExcel" id="proceeExcel" method="post" action="excel_down_prc.jsp" target="_blank">
		<input type="hidden" id="dataToDisplay" name="dataToDisplay"/>
	</form>
	<iFrame id="processFrm" name ="processFrm" width="0" height="0" title="excel data frame"></iFrame>
</div>
<!-- // Excel Download Form & IFrame -->

<!-- Message Box -->
<section id="msg_box">
	<div class="bg"></div>
	<div class="box ui_shadow_00"></div>
</section>
<!-- // Message Box -->