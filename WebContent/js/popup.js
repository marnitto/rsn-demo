//********************************
//  popup
//  작성자 : 임승철
//  작성일 : 2010.05.18
//********************************


function popup(){};

popup.version = "version 1.0";


/**
* window.open을 post방식으로 요청한다.
* resizeable,scrollbars,status
* Ex) OpenWindow('/test.jsp',500,400);
* Ex) OpenWindow('/test.jsp',500,400,'no','no');
* @param theForm(Object) FORM OBJECT
* @param url(String)  url
* @param intWidth(int)  width
* @param intHeight(int)  height
* @param resizeable(String)  window options
* @param scrollbars(String)  window options
* @param status(String)   window options
* @return
*/
popup.openByPost = function(theForm, url, intWidth, intHeight,
          resizeable,scrollbars,status, target) {
 
 //alert(1);
 var posX = (screen.availWidth/2)-(intWidth/2);
 var posY = (screen.availHeight/2)-(intHeight/2);
 var winoption = "";

 if(resizeable) resizeable = 'yes'; else resizable = 'no';
 if(scrollbars) scrollbars = 'yes'; else scrollbars = 'no';
 if(status) status = 'yes'; else status = 'no';
 if(!target) target = 'postWindow';

 winoption = "toolbar=no,location=no,directories=no,width="+intWidth;
 winoption = winoption + ",height="+intHeight+",left="+posX+",top="+posY;
 winoption = winoption + ",resizable="+resizeable+",scrollbars="+scrollbars;
 winoption = winoption + ",status=" + status;

 var win = window.open('', target, winoption);
  
 form = document.getElementById(theForm);
 form.action = url;  
 form.target = target;
 form.submit();
 win.focus(); 
};


popup.openByUrl = function(url, intWidth, intHeight,
          resizeable,scrollbars,status, target) {
 
 //alert(1);
 var posX = (screen.availWidth/2)-(intWidth/2);
 var posY = (screen.availHeight/2)-(intHeight/2);
 var winoption = "";

 if(resizeable) resizeable = 'yes'; else resizable = 'no';
 if(scrollbars) scrollbars = 'yes'; else scrollbars = 'no';
 if(status) status = 'yes'; else status = 'no';
 if(!target) target = 'postWindow';

 winoption = "toolbar=no,location=no,directories=no,width="+intWidth;
 winoption = winoption + ",height="+intHeight+",left="+posX+",top="+posY;
 winoption = winoption + ",resizable="+resizeable+",scrollbars="+scrollbars;
 winoption = winoption + ",status=" + status;

 var win = window.open(url, target, winoption);

};

popup.openByUrl2 = function(url, intWidth, intHeight,
        resizeable,scrollbars,status, target) {

//alert(1);
var posX = (screen.availWidth/2)-(intWidth/2);
var posY = (screen.availHeight/2)-(intHeight/2);
var winoption = "";

if(resizeable) resizeable = 'yes'; else resizable = 'no';
if(scrollbars) scrollbars = 'yes'; else scrollbars = 'no';
if(status) status = 'yes'; else status = 'no';
if(!target) target = 'postWindow';

winoption = "toolbar=yes,location=yes,directories=yes,width="+intWidth;
winoption = winoption + ",height="+intHeight+",left="+posX+",top="+posY;
winoption = winoption + ",resizable="+resizeable+",scrollbars="+scrollbars;
winoption = winoption + ",status=" + status;

var win = window.open(url, target, winoption);

};
