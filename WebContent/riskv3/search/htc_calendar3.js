//---------------------------------------------------------------------- 
// HTC Calendar // ver. 0.2 (2004. 3. 12) // Copyright 2004, JSGUIDE.NET // http://jsguide.net // 
// HTC Calendar의 배포권은 JSGUIDE.NET에서 허용한 곳에만 있습니다. // 본 저작권 명시부분을 삭제하거나 변조하면 안됩니다. 저작권 표기가 // 지켜지는 한도 내에서 자유롭게 사용할 수 있습니다. HTC Calendar의 // 사용으로 인한 어떠한 사고나 문제에 대해서 JSGUIDE.NET은 책임을 질 // 의무가 없습니다. 사용 중 코드의 수정을 할 수는 있지만, // 수정된 코드의 재배포는 JSGUIDE.NET 이외의 곳에서 금지합니다. 
//---------------------------------------------------------------------- 

<public:component>
<public:attach event="ondocumentready" handler="init"/>
<public:attach event="onchange" handler="calUpdate"/>

<script language="javascript">
var currDate, oDropBtn, oDropImg,  ctrlFrame, ctrlLayer, objYear, objMonth, currCell, ShowStat = "close";
var objDateCells = new Array();

//
// Utility methods
//

function padZeros(num, size) {
	var str = num.toString();
	var numZeros = size - str.length;
	for (var i=0; i<numZeros; i++) str="0"+str;
	return str;
}
function getRealOffsetTop(o) { return o ? o.offsetTop + getRealOffsetTop(o.offsetParent) : 0; }
function getRealOffsetLeft(o) { return o ? o.offsetLeft + getRealOffsetLeft(o.offsetParent) : 0; }

//
// internal methods
//

function calAdjust() {
	var year = currDate.getFullYear(); var month = currDate.getMonth();
	objYear.innerText = year; objMonth.innerText = month+1;
	
	var dateFirst = new Date(year, month, 1);   var startCellNum = dateFirst.getDay();
	var dateLast = new Date(year, month+1, 0);  var endCellNum = startCellNum + dateLast.getDate() - 1;


	for (var i=0; i<42; i++) {
		objDateCells[i].innerText = (i>=startCellNum && i<=endCellNum) ? i-startCellNum+1 : "";
/*
		switch (i%7) {
			case 0: objDateCells[i].style.color="red"; break;
			case 6: objDateCells[i].style.color="blue"; break;
			default: objDateCells[i].style.color="black";
		}
*/
	}

	if (currCell) { currCell.style.backgroundColor="white"; }
	currCell = objDateCells[currDate.getDate() + startCellNum - 1];
	currCell.style.backgroundColor="#BAD4D4";
	
	ctrlFrame.style.width = ctrlFrame.scrollWidth;
	ctrlFrame.style.height = ctrlFrame.scrollHeight;

}

function setNextMonth() { currDate.setMonth(currDate.getMonth() + 1); calAdjust(); }
function setPrevMonth() { currDate.setMonth(currDate.getMonth() - 1); calAdjust(); }

function setNextYear() {
	currDate.setYear(currDate.getFullYear() + 1);
	calAdjust();
}
function setPrevYear() {
	currDate.setYear(currDate.getFullYear() - 1);
	calAdjust();
}

function dateCellOnClick() {
    
    if (this.innerText=="") return;
	if (currCell) { currCell.style.backgroundColor="white";  }
	currDate.setDate(parseInt(this.innerText));
	currCell=this; 
	currCell.style.backgroundColor="#BAD4D4";
	this.click();
	value=getDateStr();
	calToggle();
}
function dateCellOnDblClick() { this.click(); value=getDateStr(); calToggle(); }

function getDateStr() {
	return currDate.getFullYear()+"-"+padZeros(currDate.getMonth()+1,2)+"-"+padZeros(currDate.getDate(),2);
}

function calHide() { ctrlFrame.style.visibility="hidden"; }
function calShow() { ctrlFrame.style.visibility="visible"; }


//function calToggle() {
//	switch (oDropBtn.value) {
//		case "�޷�": calShow(); oDropBtn.value="�ݱ�"; break;
//		case "�ݱ�": calHide(); oDropBtn.value="�޷�"; break;
//	}
//}

function calToggle() {
	switch (ShowStat) {
		case "close" : calShow(); ShowStat = "open" ; break;
		case "open"  : calHide(); ShowStat = "close"; break;
	}
}


function calUpdate() {
	currDate = (this.value != "") ?
		new Date(this.value.substr(0,4),this.value.substr(5,2)-1,this.value.substr(8,2)) : new Date();
	if (isNaN(currDate)) { this.value=""; currDate=new Date(); }
	calAdjust();
}

function init() {
	currDate = (this.value != "") ?
	  new Date(this.value.substr(0,4),this.value.substr(5,2)-1,this.value.substr(8,2)) : new Date();
	if (isNaN(currDate)) { this.value=""; currDate=new Date(); }

	//oDropBtn = document.createElement("<input type=button value='�޷�' style='font-size:12px;width:28px;height:20px'>");

	oSpace   = document.createElement("<span'></span>");
	oDropImg = document.createElement("<img src='images/con_data.gif' width='13' height='11' align='absmiddle' hspace='5' style='cursor:hand;'>");

	//oDropBtn.onfocus = oDropBtn.blur;
	//oDropBtn.onclick = calToggle;

	oDropImg.onfocus = oDropImg.blur;
	oDropImg.onclick = calToggle;


	//insertAdjacentElement("afterEnd",oDropBtn);
	insertAdjacentElement("afterEnd",oSpace);
	insertAdjacentElement("afterEnd",oDropImg);

// import�� html ������ iframe��ü�� ���Ѵ�.
	var ifrmId = "htccalifrm" + Math.floor(Math.random() * 1000000);
	ctrlFrame = document.createElement("<IFRAME id=" + ifrmId + " FRAMEBORDER=0></IFRAME>");
	with (ctrlFrame.style) {
		width="21px"; height="20px"; position="absolute"; zIndex="9"; visibility="hidden";
		top=getRealOffsetTop(this)+20; left=getRealOffsetLeft(this);
	}
	insertAdjacentElement("afterEnd", ctrlFrame);
	var ifrmDoc = ctrlFrame.document.frames(ifrmId).document;

// ��� iframe ��ü�� html�� ���Ѵ�.
	ifrmDoc.open("text/html","replace");

	ifrmDoc.writeln("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
	ifrmDoc.writeln("<html>");
	ifrmDoc.writeln("<head>");
	ifrmDoc.writeln("<title>Calendar</title>");
	ifrmDoc.writeln("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">");
	ifrmDoc.writeln("<link href=\"./css/calendar.css\" rel=\"stylesheet\" type=\"text/css\">");
	ifrmDoc.writeln("</head>");
	ifrmDoc.writeln("<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">");
	ifrmDoc.writeln("</body>");
	ifrmDoc.writeln("</html>");
	
	ifrmDoc.close();

// �������� �޷��� ����
	ctrlLayer = ifrmDoc.createElement("DIV");
	with (ctrlLayer.style) {
		border="1px solid #999999"; 
	}

// table �߰�	
	var oTableMain = ifrmDoc.createElement("<table width='165' height='188' border='0' cellspacing='0' cellpadding='0' class='bg_light'>");
	ctrlLayer.appendChild(oTableMain);

	var oTable, row, cell, span, img, nbsp;
	var oTable2, oTbody2, row2; // ��� ����� ��ä��
// tbody�߰� 
	var oTbody = ifrmDoc.createElement("TBODY"); 
	oTableMain.appendChild(oTbody);

  // tr �߰�
	row = ifrmDoc.createElement("<tr>");
	oTbody.appendChild(row);
    // td �߰�
	cell = ifrmDoc.createElement("<td align='center'>");
	row.appendChild(cell);

// table �߰�	
	oTable = ifrmDoc.createElement("<table width='154' height='177' border='0' cellpadding='0' cellspacing='1' class='calendar_bg_line'>");
	cell.appendChild(oTable);
// tbody�߰� 
	oTbody = ifrmDoc.createElement("TBODY"); 
	oTable.appendChild(oTbody);
  // tr �߰�
	row = ifrmDoc.createElement("<tr>");
	oTbody.appendChild(row);
    // td �߰�
	cell = ifrmDoc.createElement("<td align='center' class='bg_white'>");
	row.appendChild(cell);

// table �߰�	
	oTable2 = ifrmDoc.createElement("<table width='152' height='157' border='0' cellpadding='0' cellspacing='0'>");
	cell.appendChild(oTable2);
// tbody�߰� 
	oTbody2 = ifrmDoc.createElement("TBODY"); 
	oTable2.appendChild(oTbody2);
  // tr �߰�
	row2 = ifrmDoc.createElement("<tr>");
	oTbody2.appendChild(row2);
    // td �߰�
	cell = ifrmDoc.createElement("<td>");
	row2.appendChild(cell);
	nbsp = ifrmDoc.createTextNode(" ");
	cell.appendChild(nbsp);
    // td �߰�
	cell = ifrmDoc.createElement("<td height='24' colspan='7' align='center' valign='top'>");
	row2.appendChild(cell);

// table �߰�	
	oTable = ifrmDoc.createElement("<table width='147' height='24' border='0' cellpadding='0' cellspacing='1' class='calendar_bg_line'>");
	cell.appendChild(oTable);
// tbody�߰� 
	var oTbody = ifrmDoc.createElement("TBODY"); 
	oTable.appendChild(oTbody);
  // tr �߰�
	row = ifrmDoc.createElement("<tr class='calendar_title_bg'>");
	oTbody.appendChild(row);
    // td �߰� : ��⼱�ù�ư
	cell = ifrmDoc.createElement("<td width='20' align='center' class='calendar_num'>");
	row.appendChild(cell);
	img = ifrmDoc.createElement("<img src='images/datepicker_Last.gif' alt='Last Year'>");
	cell.appendChild(img);
	cell.onmouseup = setPrevYear;
	cell.onselectstart = function(){return false;}
    // td �߰� : ����ù�ư
	cell = ifrmDoc.createElement("<td width='20' align='center' class='calendar_title'>");
	row.appendChild(cell);
	img = ifrmDoc.createElement("<img src='images/datepicker_prv.gif' alt='Last Month' border='0'>");
	cell.appendChild(img);
	cell.onmouseup = setPrevMonth;
	cell.onselectstart = function(){return false;}
    // td �߰� : ��, �� ǥ��â
	cell = ifrmDoc.createElement("<td width='70' align='center' class='calendar_title'>");
	row.appendChild(cell);
	objYear = ifrmDoc.createElement("SPAN");
	cell.appendChild(objYear);
	nbsp = ifrmDoc.createTextNode(". ");
	cell.appendChild(nbsp);
	objMonth = ifrmDoc.createElement("SPAN");
	cell.appendChild(objMonth);
    // td �߰� : �����޼��ù�ư
	cell = ifrmDoc.createElement("<td width='20' align='center' class='calendar_num'>");
	row.appendChild(cell);
	img = ifrmDoc.createElement("<img src='images/datepicker_next.gif' alt='Next Month' border='0'>");
	cell.appendChild(img);
	cell.onmouseup = setNextMonth;
	cell.onselectstart = function(){return false;}
    // td �߰� : ���⼱�ù�ư
	cell = ifrmDoc.createElement("<td width='20' align='center' class='calendar_title'>");
	row.appendChild(cell);
	img = ifrmDoc.createElement("<img src='images/datepicker_nextyear.gif' alt='Next Year'>");
	cell.appendChild(img);
	cell.onmouseup = setNextYear;
	cell.onselectstart = function(){return false;}
//======================================	
    // td �߰�
	cell = ifrmDoc.createElement("<td>");
	row2.appendChild(cell);
	nbsp = ifrmDoc.createTextNode(" ");
	cell.appendChild(nbsp);
               
  // tr �߰�
	row = ifrmDoc.createElement("<tr align='center'>");
	oTbody2.appendChild(row);
    // td �߰�
	cell = ifrmDoc.createElement("<td>");
	row.appendChild(cell);
	nbsp = ifrmDoc.createTextNode(" ");
	cell.appendChild(nbsp);
    // td �߰�
	cell = ifrmDoc.createElement("<td class='calendar_week_sun'>");
	cell.innerText="S";
	row.appendChild(cell);
    // td �߰�
	cell = ifrmDoc.createElement("<td class='calendar_week'>");
	cell.innerText="M";
	row.appendChild(cell);
    // td �߰�
	cell = ifrmDoc.createElement("<td class='calendar_week'>");
	cell.innerText="T";
	row.appendChild(cell);
    // td �߰�
	cell = ifrmDoc.createElement("<td class='calendar_week'>");
	cell.innerText="W";
	row.appendChild(cell);
    // td �߰�
	cell = ifrmDoc.createElement("<td class='calendar_week'>");
	cell.innerText="T";
	row.appendChild(cell);
    // td �߰�
	cell = ifrmDoc.createElement("<td class='calendar_week'>");
	cell.innerText="T";
	row.appendChild(cell);
    // td �߰�
	cell = ifrmDoc.createElement("<td class='calendar_week_sat'>");
	cell.innerText="S";
	row.appendChild(cell);
    // td �߰�
	cell = ifrmDoc.createElement("<td>");
	row.appendChild(cell);
	nbsp = ifrmDoc.createTextNode(" ");
	cell.appendChild(nbsp);

  // tr �߰�
	row = ifrmDoc.createElement("<tr>");
	oTbody2.appendChild(row);
    // td �߰�
	cell = ifrmDoc.createElement("<td>");
	row.appendChild(cell);
	img = ifrmDoc.createElement("<img src='images/blank.gif' width='1' height='1'>");
	cell.appendChild(img);
    // td �߰�
	cell = ifrmDoc.createElement("<td height='1' colspan='7' class='calendar_bg_line'>");
	row.appendChild(cell);
	img = ifrmDoc.createElement("<img src='images/blank.gif' width='1' height='1'>");
	cell.appendChild(img);
    // td �߰�
	cell = ifrmDoc.createElement("<td>");
	row.appendChild(cell);
	img = ifrmDoc.createElement("<img src='images/blank.gif' width='1' height='1'>");
	cell.appendChild(img);
  // tr �߰�
	row = ifrmDoc.createElement("<tr align='center'>");
	oTbody2.appendChild(row);
    // td �߰�
	cell = ifrmDoc.createElement("<td>");
	row.appendChild(cell);
    // td �߰�
	cell = ifrmDoc.createElement("<td height='3' >");
	row.appendChild(cell);
    // td �߰�
	cell = ifrmDoc.createElement("<td>");
	row.appendChild(cell);
    // td �߰�
	cell = ifrmDoc.createElement("<td>");
	row.appendChild(cell);
    // td �߰�
	cell = ifrmDoc.createElement("<td>");
	row.appendChild(cell);
    // td �߰�
	cell = ifrmDoc.createElement("<td>");
	row.appendChild(cell);
    // td �߰�
	cell = ifrmDoc.createElement("<td>");
	row.appendChild(cell);
    // td �߰�
	cell = ifrmDoc.createElement("<td>");
	row.appendChild(cell);
    // td �߰�
	cell = ifrmDoc.createElement("<td>");
	row.appendChild(cell);



	var thisClassName = "";
	for (var i=0; i<6; i++) {
  // tr �߰�
		row = ifrmDoc.createElement("<tr align='center'>");
		oTbody2.appendChild(row);
    // td �߰�
		cell = ifrmDoc.createElement("<td>");
		row.appendChild(cell);
		nbsp = ifrmDoc.createTextNode(" ");
		cell.appendChild(nbsp);		

		for (var j=0; j<7; j++) {
			if (j==0) thisClassName="calendar_sun";
			else if (j==6) thisClassName="calendar_sat";
			else thisClassName="calendar_num";
    // td �߰�	
			objDateCells[i*7+j]= cell = ifrmDoc.createElement("<td height='20' class='"+thisClassName+"' onmouseover=\"this.style.border='1px solid #BAD4D4'\"onmouseout=\"this.style.border='1px solid #FFFFFF'\">");
			row.appendChild(cell);
			cell.onclick = dateCellOnClick; 
			cell.ondblclick = dateCellOnDblClick;
			cell.onselectstart = function(){return false;}
		}
    // td �߰�
		cell = ifrmDoc.createElement("<td>");
		row.appendChild(cell);
		nbsp = ifrmDoc.createTextNode(" ");
		cell.appendChild(nbsp);		
	}
	ifrmDoc.appendChild(ctrlLayer);
	calAdjust();
}
</script>

</public:component>
