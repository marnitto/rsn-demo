
    var f = document.fSend;
	var typeChk = 0;
	var typeStr = "";
	var currDate = new Date();
	var dateStr = "" + currDate.getYear() + "-";

    function chkDate(obj) {				// 날짜입력양식에 대한 수치 검증
        str = removeDash(obj.value);
		len=str.length;

		stat="true";
		if(len < 8){
			alert("8자리의 숫자로 입력하십시오");

			obj.value = sCurrDate;
			if(obj.name == "txtDateFrom"){
				document.fSend.txtDateFrom.select();
			}else if(obj.name == "txtDateTo"){
				document.fSend.txtDateTo.select();
			}

			return;
		}

		var i, chr, point=0;
		for (i=0;i<len;i++){
			chr=str.substring(i,i+1);
			if (chr=="."){
				point+=1;
				continue;
			}
			if (chr < "0" || chr > "9") {
				stat="x";
				break;
			}
		}

		chkDateVal = removeDash(str);
		if(check_date(chkDateVal.substring(0,4), chkDateVal.substring(4,6), chkDateVal.substring(6,8)) != 0){
			alert("올바른 날자가 아닙니다.");
			obj.value = sCurrDate;
			if(obj.name == "txtDateFrom"){
				document.fSend.txtDateFrom.select();
			}else if(obj.name == "txtDateTo"){
				document.fSend.txtDateTo.select();
			}

			return;
		}

		if ((stat=="true") && (point<=1)){
			dFilter(event.keyCode, obj, '####-##-##');
			return true;
		}else{
			alert('숫자만 입력하십시오.');
			obj.value = sCurrDate;
			if(obj.name == "txtDateFrom"){
				document.fSend.txtDateFrom.select();
			}else if(obj.name == "txtDateTo"){
				document.fSend.txtDateTo.select();
			}
		}

	}

    //	str의 Dash(-)를 제거한 문자열을 반환한다.
	function removeDash(str){
		returnStr = "";
	for(i=0 ; i < str.length ; i++){
			if(str.charAt(i) != "-"){
				returnStr += str.charAt(i);
			}
		}
		return returnStr;
	}


	function check_date(year, month, day) {			//유효날자체크 0이 정상
		var dat_option = year % 4;
		year = parseInt(year, 10);
		month = parseInt(month, 10);
		day = parseInt(day, 10);

		if (isNaN(year) == true) { return 1; }
		if (year < 1970) { return 1; }

		if (isNaN(month) == true) { return 2; }
		if (isNaN(day) == true) { return 3; }
		if (day < 1 || day > 31) { return 3; }

		if (month == 2) {
		if (((dat_option == 0) && (day > 29)) || ((dat_option != 0) && (day > 28)) ) { return 3; }
		} else if ((month == 4) ||
		(month == 6) ||
		(month == 9) ||
		(month == 11)) {
		if (day > 30) { return 3; }
		} else if ((month == 1) ||
		(month == 3) ||
		(month == 5) ||
		(month == 7) ||
		(month == 8) ||
		(month == 10) ||
		(month == 12)) {
		if (day > 31) { return 3; }
		} else {
		return 2;
		}

		return 0;
	}


    // 시간을 설정 하세요
        var limit="0:10" // 1분 30초

        if (document.images){
            var parselimit=limit.split(":")
            parselimit=parselimit[0]*60+parselimit[1]*1
        }

        function begintimer(){
            if (!document.images)
            return
            if (parselimit==1)
            window.location="http://www.yahoo.co.kr"
            else{
            parselimit-=1
            curmin=Math.floor(parselimit/60)
            cursec=parselimit%60
            if (curmin!=0)
            curtime=curmin+" 분 "+cursec+" 초 가 남았습니다"
            else
            curtime=cursec+" 초가 남았습니다"
            window.status=curtime
            setTimeout("begintimer()",1000)
        }
    }

    var dFilterStep

    function dFilterStrip(dFilterTemp, dFilterMask) {
    	dFilterMask = replace(dFilterMask,'#','');
    	for (dFilterStep = 0; dFilterStep < dFilterMask.length++; dFilterStep++) {
    			dFilterTemp = replace(dFilterTemp,dFilterMask.substring(dFilterStep,dFilterStep+1),'');
    	}
    	return dFilterTemp;
    }

    function dFilterMax(dFilterMask) {
    	dFilterTemp = dFilterMask;
    	for (dFilterStep = 0; dFilterStep < (dFilterMask.length+1); dFilterStep++) {
    		if (dFilterMask.charAt(dFilterStep)!='#') {
    			dFilterTemp = replace(dFilterTemp,dFilterMask.charAt(dFilterStep),'');
    		}
    	}
    	return dFilterTemp.length;
    }

    function dFilter(key, textbox, dFilterMask) {
    	dFilterNum = dFilterStrip(textbox.value, dFilterMask);

    	if (key==9) {
    		return true;
    	} else
    	if (key==8&&dFilterNum.length!=0) {
    		dFilterNum = dFilterNum.substring(0,dFilterNum.length-1);
    	} else
    	if ( ((key>47&&key<58)||(key>95&&key<106)) && dFilterNum.length<dFilterMax(dFilterMask) ) {
    		dFilterNum=dFilterNum+String.fromCharCode(key);
    	}

    	var dFilterFinal='';
    	for (dFilterStep = 0; dFilterStep < dFilterMask.length; dFilterStep++) {
    		if (dFilterMask.charAt(dFilterStep)=='#') {
    			if (dFilterNum.length!=0) {
    				dFilterFinal = dFilterFinal + dFilterNum.charAt(0);
    				dFilterNum = dFilterNum.substring(1,dFilterNum.length);
    			} else {
    				dFilterFinal = dFilterFinal + "";
    			}
    		} else
    		if (dFilterMask.charAt(dFilterStep)!='#') {
    			dFilterFinal = dFilterFinal + dFilterMask.charAt(dFilterStep);
    		}
    	}
    	textbox.value = dFilterFinal;
    	return false;
    }

    function replace(fullString,text,by) {
    	var strLength = fullString.length, txtLength = text.length;
    	if ((strLength == 0) || (txtLength == 0)) return fullString;

    	var i = fullString.indexOf(text);
    	if ((!i) && (text != fullString.substring(0,txtLength))) return fullString;
    	if (i == -1) return fullString;

    	var newstr = fullString.substring(0,i) + by;

    	if (i+txtLength < strLength)
    	newstr += replace(fullString.substring(i+txtLength,strLength),text,by);

    	return newstr;
    }
