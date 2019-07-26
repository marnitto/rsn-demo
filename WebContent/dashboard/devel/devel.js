
//카페 글일경우 제목 검색 링크로 연결
var chkOriginal = 1;
function portalSearch(s_seq, md_title){
	if(s_seq == '3555'){ //네이버카페
		//url = "http://section.cafe.naver.com/CombinationSearch.nhn?query="+md_title ;
		url = "https://section.cafe.naver.com/cafe-home/search/articles?query=\""+md_title+"\"";		
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
		
	}else if(s_seq == '4943'){ //다음카페
		url = "http://search.daum.net/search?w=cafe&nil_search=btn&DA=NTB&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + encodeURIComponent(md_title);
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
	}
	chkOriginal ++;
}

// URL에 문서제목을 넘기기 위해 작은따옴표와 큰따옴표를 제거
function urlTitle(title){
	var result = title.replace(/"/gi, "");
	result = result.replace(/'/gi, "");
	result = result.replace(/&#34;/gi, "");
	result = result.replace(/&#39;/gi, "");
	return result;
}

// 작은따옴표와 큰따옴표를 &#34; &#39; 로 치환
function attrTitle(title){
	var result = title.replace(/"/gi, "&#34;");
	result = result.replace(/'/gi, "&#39;");
	return result;
}

//엑셀파일 삭제
function deleteExcelFile(file){
	setTimeout(function(){
		var frm_excel = document.proceeExcel;
		frm_excel.target = 'processFrm';
		frm_excel.action = '../common/excel_delete_prc.jsp';
		$(frm_excel).find("[name=filePath]").remove();
		$(frm_excel).append("<input type='hidden' name='filePath' value='"+file+"' >");
		frm_excel.submit();
	},1000);
}

function paging(pageCnt, rowCnt, totalCnt, nowPage, $element, method){
	var totalPage = totalCnt / rowCnt;
	totalPage = Math.ceil(totalPage);

	var firstPage = nowPage - ((nowPage-1) % pageCnt);
	var lastPage = (firstPage + (pageCnt - 1) > totalPage) ? totalPage : firstPage + (pageCnt - 1);
	
	var html = "<a href='#' class='page_first'>처음 페이지</a> <a href='#' class='page_prev'>이전페이지</a>";
	
	if(totalCnt=="0"){
		html += "<a href='#' class='active'>1</a>";
	}else{
		for(var i=firstPage; i<=lastPage; i++){
			html += "<a href='#' id='" + i + "'>" + i + "</a>";
		}
	}
	html += "<a href='#' class='page_next'>다음페이지</a> <a href='#' class='page_last'>마지막 페이지</a>";
		        	
	$element.html(html);	        	

	if(firstPage==1){
		$element.find(".page_prev").addClass("disabled");
	}else{
		$element.find(".page_prev").attr("id", firstPage-1);
	}
	
	if(nowPage == 1){
		$element.find(".page_first").addClass("disabled");
	}else{
		$element.find(".page_first").attr("id", 1);
	}
	
	if(lastPage==totalPage){
		$element.find(".page_next").addClass("disabled");
	}else{
		$element.find(".page_next").attr("id", lastPage+1);		
	}
	
	if(nowPage == totalPage){
		$element.find(".page_last").addClass("disabled");
	}else{
		$element.find(".page_last").attr("id", totalPage);
	}
	
	$element.find("#" + nowPage).addClass("active");
	
	$element.find("a").off("click");
	$element.find("a").click(function(){
		eval(method + "(" + $(this).attr("id") + ");");
		return false;
	});	
}

// 두 날짜의 차이를 계산
function getDateDiff(sDate, eDate){
	var fromDateArry = sDate.split('-');
	var toDateArry = eDate.split('-');
	
	var startDate = new Date(fromDateArry[0], Number(fromDateArry[1])-1, fromDateArry[2]);
	var endDate = new Date(toDateArry[0], Number(toDateArry[1])-1, toDateArry[2]);
	var betweenDay = (endDate.getTime() - startDate.getTime())/1000/60/60/24;
	
	return betweenDay;
}

function getBeforeDay(date, beforeday){
	var beforedays = new Date(Date.parse(date) - beforeday * 1000 * 60 * 60 * 24);
	return beforedays.getFullYear() + "-" + (beforedays.getMonth()+1<10 ? "0" + (beforedays.getMonth()+1) : beforedays.getMonth()+1) + "-"
			+ (beforedays.getDate()<10 ? "0" + beforedays.getDate() : beforedays.getDate());
}

function getAfterDay(date, afterday){
	var afterDays = new Date(Date.parse(date) + afterday * 1000 * 60 * 60 * 24);
	return afterDays.getFullYear() + "-" + (afterDays.getMonth()+1<10 ? "0" + (afterDays.getMonth()+1) : afterDays.getMonth()+1) + "-"
			+ (afterDays.getDate()<10 ? "0" + afterDays.getDate() : afterDays.getDate());
}


function getToday(){
	var today = new Date();
	return today.getFullYear() + "-" + (today.getMonth()+1<10 ? "0" + (today.getMonth()+1) : today.getMonth()+1) + "-"
			+ (today.getDate()<10 ? "0" + today.getDate() : today.getDate());
}

function percent(totalCount, count){
	return count/totalCount*100;
}

function senti(op, sentiCode){
	if(op==0){
		if(sentiCode == "1"){
			return "긍정";
		}else if(sentiCode == "2"){
			return "부정";
		}else if(sentiCode == "3"){
			return "중립";
		}
	}else if(op==1){
		if(sentiCode == "1"){
			return "ui_fc_blue";
		}else if(sentiCode == "2"){
			return "ui_fc_red";
		}else if(sentiCode == "3"){
			return "ui_fc_green";
		}
	}	
} 