var dateFormat = 'yyyy.mm.dd';

var calendar_monthnames = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']; 
var calendar_daynames = ['일', '월', '화', '수', '목', '금', '토']; 

var curMonth; 
var curYear; 
var curDay; 
var curDate;
var toDay;

var imageUrl;
var firstDayIndex;
var searchMode;

var date = new Date();
today_month = date.getMonth() + 1;
today_year = date.getFullYear();
today_date = date.getDate();
toDay = calendar_format_date(today_date, today_month, today_year);


function calendar_get_el(id) {
	return document.getElementById(id);
}

function calendar_getleft(el) {
	var tmp = el.offsetLeft;
	el = el.offsetParent;
	while(el) {
		tmp += el.offsetLeft;
		el = el.offsetParent;
	}
	return tmp;
}
function calendar_gettop(el) {
	var tmp = el.offsetTop;
	el = el.offsetParent;
	while(el) {
		tmp += el.offsetTop;
		el = el.offsetParent;
	}
	return tmp;
}

var calendar_oe;
var calendar_ce;

var calendar_ob = ''; 
function calendar_ob_clean() {
	calendar_ob = '';
}
function calendar_ob_flush() {
	calendar_oe.innerHTML = calendar_ob;
	calendar_ob_clean();
	carDate = '';
}
function calendar_echo(t) {
	calendar_ob += t;
}

var calendar_element; 

function calendar_template_main_above(t) {
	return '<iframe class="hide"></iframe><table width="210" border="0" align="center" cellpadding="0" cellspacing="0">'
	+'<tr>'
	+'<table width="210" border="0" align="center" cellpadding="0" cellspacing="0">'
	+'<tr>'
	+'<td width="6"><img src="' + imageUrl + 'cal_bg_01.gif" width="6" height="26" /></td>'
	+'<td background="' + imageUrl + 'cal_bg_03.gif"><table width="180" border="0" align="center" cellpadding="0" cellspacing="0">'
	+'	<tr>'
	+'		<td width="10"><img src="' + imageUrl + 'cal_arrow_02.gif" width="4" height="8" style="cursor: pointer" onclick="calendar_pm();"/></td>'
	+'		<td align="center" valign="bottom" class="white_b"><strong>' + t + '</strong></td>'
	+'		<td width="10" align="right"><img src="' + imageUrl + 'cal_arrow_01.gif" width="4" height="8" style="cursor: pointer" onclick="calendar_nm();"/></td>'
	+'	</tr>'
	+'</table></td>'
	+'<td width="6"><img src="' + imageUrl + 'cal_bg_02.gif" width="6" height="26" /></td>'
	+'</tr>'
	//+'<tr>'
	//+'<td height="7"></td>'
	//+'</tr>'
	+'</table>'
	+'</tr>'
	+'<tr>'
	+'<td><table width="210" border="0" cellpadding="0" cellspacing="1" bgcolor="E1E1E1">'
	+'<tr align="center" bgcolor="C9C9C9">';
}

function calendar_template_day_row(t, cName) {
	return '<td width="30" height="24" class="'+cName+'"><strong>'+t+'</strong></td>';
}

function calendar_template_new_week() {
	return '</tr><tr align="center" bgcolor="#FFFFFF">';
}

function calendar_template_blank_cell() {
	return '<td height="24"></td>';
}

function calendar_template_day(d, m, y, className) {
	return '<td height="24" class="'+className+'" style="cursor:pointer" onclick="calendar_onclick(' + d + ',' + m + ',' + y + ')" '
	+ 'onmouseover="calendar_sell_over(this)" onmouseout="calendar_sell_out(this)">' + d + '</td>';
}

function calendar_template_day_curdate(d, m, y, className) {
	return '<td height="24" class="'+className+'" style="background-color: #c8d9ea;cursor:pointer" onclick="calendar_onclick(' + d + ',' + m + ',' + y + ')">' + d + '</td>';
}

function calendar_template_main_below() {
	
	return '</tr>'
    + '</table>'
    +'</td>'
    +'</tr>'
    +'<tr>'
    +'<td width="148" align="center"><table width="148" border="0" cellpadding="0" cellspacing="0">'
	+'<tr>'
	+'<td height="7"></td>'
	+'</tr>'
    +'<tr>'
    +'<td align="center" style="cursor:pointer" onclick="calendar_set_doday();">Today</td>'
    +'<td align="center" style="cursor:pointer" onclick="calendar_close();">Close</td>'
    +'</tr>'
    +'</table></td>'
    +'</tr>'
    +'</table>';
}

function calendar_draw_calendar(m, y) {
	
	
	calendar_ob_clean();
	calendar_echo (calendar_template_main_above(y + '.' + calendar_monthnames[m - 1]));
	for (i = 0; i < 7; i ++) {
		if(i == 0){
			calendar_echo (calendar_template_day_row(calendar_daynames[i],'calendar_red'));
		}else if(i > 0 && i < 6){
			calendar_echo (calendar_template_day_row(calendar_daynames[i],'calendar_white'));
		}else{
			calendar_echo (calendar_template_day_row(calendar_daynames[i],'calendar_blue'));
		}
	}

	var calendar_dc_date = new Date();
	//calendar_dc_date.setDate(1);
	//calendar_dc_date.setMonth((m-1),1);
	calendar_dc_date.setFullYear(y,(m-1),1);
	
	if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
		days = 31;
	} else if (m == 4 || m == 6 || m == 9 || m == 11) {
		days = 30;
	} else {
		days = (y % 4 == 0) ? 29 : 28;
	}
	var first_day = calendar_dc_date.getDay();
	var first_loop = 1;

	calendar_echo (calendar_template_new_week());
	if (first_day != 0) {
		for(i = 0; i < first_day; i++){
			calendar_echo (calendar_template_blank_cell());
		}
	}
	var j = first_day;
	firstDayIndex = first_day;
	var className="";
	var bgColor = "";
	for (i = 0; i < days; i ++) {
		if (j == 0 && !first_loop) {
			calendar_echo (calendar_template_new_week());
		}
		
		if (j==0) className="calendar_red";
		else if (j==6) className="calendar_blue";
		else className="calendar_gray";
		
		if(curDate == calendar_format_date(i + 1, m, y)){
			calendar_echo (calendar_template_day_curdate(i + 1, m, y, className));
		}else{
			calendar_echo (calendar_template_day(i + 1, m, y, className));
		}
		
		first_loop = 0;
		j ++;
		j %= 7;
	}

	if(first_day != 0){
		if((first_day+days) > 35){
			for(i = 0; i < (42-(first_day+days)); i++){
				calendar_echo (calendar_template_blank_cell());
			}
		}else if((first_day+days) < 35){
			for(i = 0; i < (35-(first_day+days)); i++){
				calendar_echo (calendar_template_blank_cell());
			}
		}
	}else{
		if(days > 28){
			for(i = 0; i < (35-days); i++){
				calendar_echo (calendar_template_blank_cell());
			}
		}
	}
	
	//calendar_echo (calendar_template_main_below());
	calendar_ob_flush();

	//calendar_ce.scrollIntoView();
}

function view_calendar(t, imgUrl, mode){
	imageUrl = imgUrl;
	searchMode = mode;
	
	if(calendar_ce && calendar_ce.style.display==''){
		calendar_close();
	}else{
		calendar_sh(t);
	}
}

function calendar_sh(t) {

	calendar_oe = calendar_get_el('calendar_calclass');
	calendar_ce = calendar_get_el('calendar_conclass');
	
	calendar_element = t;

	curDate = calendar_element.value.split(' ')[0];
	
	if(curDate == 'undefined' || curDate == ''){
		curDate = toDay;
	}

	yIndex = dateFormat.indexOf("yyyy", 0);
	mIndex = dateFormat.indexOf("mm", 0);
	dIndex = dateFormat.indexOf("dd", 0);
	
	curYear = parseInt(curDate.substring(yIndex, yIndex+4));
	//curMonth = parseInt(curDate.substring(mIndex, mIndex+2));
	curMonth = Number(curDate.substring(mIndex, mIndex+2));
	curDay   = parseInt(curDate.substring(dIndex, dIndex+2));
	
	calendar_draw_calendar(curMonth, curYear);
	calendar_ce.style.display = '';
	the_left = calendar_getleft(t);
	the_top = calendar_gettop(t) + t.offsetHeight;
	calendar_ce.style.left = the_left + 'px';
	calendar_ce.style.top = the_top + 'px';
	
}

function calendar_close() {
	calendar_ce.style.display = 'none';
}

function calendar_set_doday() {
	calendar_close();
	if (typeof(calendar_element.value) != 'undefined') {
		calendar_element.value = toDay;
	} else if (typeof(calendar_element.innerHTML) != 'undefined') {
		calendar_element.innerHTML = toDay;
	} else {
		alert (toDay);
	}
}

function calendar_nm() {
	curMonth ++;
	if (curMonth > 12) {
		curMonth = 1; 
		curYear++;
	}
	calendar_draw_calendar(curMonth, curYear);
}

function calendar_pm() {
	curMonth = curMonth - 1;
	if (curMonth < 1) {
		curMonth = 12; 
		curYear = curYear - 1; 
	}
	calendar_draw_calendar(curMonth, curYear);
}

function calendar_ny() {
	curYear++;
	calendar_draw_calendar(curMonth, curYear);
}

function calendar_py() {
	curYear = curYear - 1; 
	calendar_draw_calendar(curMonth, curYear);
}


function addZero(number)
{
	return ((number < 10) ? '0' : '') + number;
}

function calendar_format_date(d, m, y) {
	vMonth = addZero(m);
	vDay = addZero(d);
	vYear=y;
	return dateFormat.replace(/dd/g, vDay).replace(/mm/g, vMonth).replace(/y{1,4}/g, vYear);
}

function calendar_onclick(d, m, y) {
	var tmpDay = d;
	while(tmpDay >= 7){
		tmpDay = tmpDay-7;
	}
	
	tmpDay = firstDayIndex+(tmpDay-1);
	if(tmpDay >= 7){
		tmpDay = tmpDay - 7;
	}
	
	var dayOfWeek = calendar_daynames[tmpDay];
	
	calendar_close();
	if (typeof(calendar_element.value) != 'undefined') {
		if(searchMode == 'emergency'){
			calendar_element.value = calendar_format_date(d, m, y)+"";
		}else{
			calendar_element.value = calendar_format_date(d, m, y)+" "+dayOfWeek+"요일";	
		}
		
	} else if (typeof(calendar_element.innerHTML) != 'undefined') {
		
		calendar_element.innerHTML = calendar_format_date(d, m, y)+" "+dayOfWeek+"요일";
	} else {
		alert (calendar_format_date(d, m, y));
	}
	
	if(m < 10){m='0'+m;}
	if(d < 10){d='0'+d;}
	if(searchMode == 'todayCondition'){
		getRelationKeyword(y+'-'+m+'-'+d, 1);
		showTimeArea(searchMode, y+'-'+m+'-'+d);
	}else if(searchMode == 'majorIssue'){
		getMajorIssue(y+'-'+m+'-'+d, 1);
		showTimeArea(searchMode, y+'-'+m+'-'+d);
	}else if(searchMode == 'chanelIssue'){
		getChanelIssueChart(y+'-'+m+'-'+d, 'social', 1);
		getChanelIssueChart(y+'-'+m+'-'+d, 'media', 1);
		showTimeArea(searchMode, y+'-'+m+'-'+d);
	}else if(searchMode == 'chanelCondition'){
		getSocialChanelCondition(y+'-'+m+'-'+d);
	}
}

function calendar_sell_over(el) {
	el.style.backgroundColor = "#c8d9ea";
}

function calendar_sell_out(el) {
	el.style.backgroundColor = "#FFFFFF";
}