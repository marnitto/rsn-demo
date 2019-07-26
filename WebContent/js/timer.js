
   //새로고침 관련 자바스크립트 By  윤석준
    /****************************************************/
    /* 새로고침 이후 지난 시간을 표출하기 위한 스크립트 */
    /****************************************************/
    var refreshinterval=0;

    var startRefreshTime;
    var nowtime;
    var reloadseconds=0;
    var secondssinceloaded=0;


    var currentsec=0;
    var currentmin=0;
    var currentmil=0;
    var refreshing=false;
    var refreshTimer;


    function starttime() {
            startRefreshTime=new Date();
            startRefreshTime=startRefreshTime.getTime();
            countdown();
    }

    function countdown() {
        nowtime= new Date();
        nowtime=nowtime.getTime();
        secondssinceloaded= (nowtime - startRefreshTime) /1000;
        reloadseconds = Math.round(refreshinterval - secondssinceloaded);

        if (refreshing) {

            if (refreshinterval >= secondssinceloaded) {
                refreshTimer=setTimeout("countdown()",1000);
            } else {
                clearTimeout(refreshTimer);
                doSubmit();

            }

            currentsec+=1;
            if (currentsec==60){
                currentsec=0;
                currentmin+=1;
            }
            Strsec=""+currentsec;
            Strmin=""+currentmin;
            Strmil=""+currentmil;

            if (Strsec.length!=2){
                Strsec="0"+currentsec;
            }
            if (Strmin.length!=2){
                Strmin="0"+currentmin;
            }
            watch.innerHTML = "<strong>" + Strmin + ":" + Strsec + "초전</strong> 정보입니다." ;
        }
    }

    function setTimer( time ) {


        if ( time == "" ) {
            refreshing = false;
        } else {
            refreshing = true;
        }

        if ( refreshing == true ) {

            document.fSend.timer.value = "N";
            document.fSend.interval.value = "";

            clearTimeout(refreshTimer);
            currentsec=0;
            currentmin=0;
            currentmil=0;
            Strsec="00";
            Strmin="00";
            Strmil="00";


            refreshinterval = time * 60 ;
            document.fSend.timer.value = "Y";
            document.fSend.interval.value = time;
            starttime();

        } else {

            document.fSend.timer.value = "N";
            document.fSend.interval.value = time;

            clearTimeout(refreshTimer);

            refreshing=false;
            currentsec=0;
            currentmin=0;
            currentmil=0;
            Strsec="00";
            Strmin="00";
            Strmil="00";
            watch.innerHTML = "<strong>새로고침중지중.</strong>" ;
        }
    }

    /****************************************************/
