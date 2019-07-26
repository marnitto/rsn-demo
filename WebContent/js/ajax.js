//********************************
//  ajax
//
//  작성자 : 임승철
//  작성일 : 2010.09.28
//********************************


function ajax(){};

ajax.version = "version 1.0";

/**
* 기능: ajax를 GET방식으로 요청한다.
* @param url: 호출 페이지
* @param param: 넘길 인자배열값
* @param divId: reponse 받을 객체
*/

ajax.get = function(url, param, objId){
	var targetUrl = url;	
	targetUrl = targetUrl+'?'+ param.join('&');
	$.get(targetUrl,
			function(data){								
				$("#"+objId).html(data);											
			});
}

/**
* 기능: ajax를 POST방식으로 요청한다.
* @param url: 호출 페이지
* @param formId: 넘길 폼 Id
* @param divId: reponse 받을 객체
*/
ajax.post = function(url, formId, objId){	
	var targetUrl = url;	
	var param = $("#"+formId).serialize();
	$.post(targetUrl,param,
			function(data){				
				$("#"+objId).html(data);										
			});
}

/**
* 기능: ajax를 POST방식으로 요청한다.
* @param url: 호출 페이지
* @param formId: 넘길 폼 Id
* @param divId: reponse 받을 객체
*/
ajax.post2 = function(url, formId, objId, loadingBarName){	
	var targetUrl = url;	
	var param = $("#"+formId).serialize();
	
	$.ajax({
	type : "POST"
	,async : true
	,url: targetUrl
	,timeout: 30000
	,data : param
	,dataType : 'text'
	,async: true
	,success : function(data){
				//alert(data);
				$("#"+objId).html(data);					
			  }
	,beforeSend : function(){				
				$("#"+objId).html('<img src="../../images/common/'+loadingBarName+'">');
			  }
	});			
}

ajax.post3 = function(url, formId, objId, loadingBarName){	
	var targetUrl = url;	
	var param = $("#"+formId).serialize();
	
	$.ajax({
	type : "POST"
	,async : true
	,url: targetUrl
	,timeout: 30000
	,data : param
	,dataType : 'text'
	,async: true
	,success : function(data){
				//alert(data);
				$("#"+objId).html(data);					
			  }
	,beforeSend : function(){				
				$("#"+objId).html('<img src="'+loadingBarName+'">');
			  }
	});			
}

/**
* 기능: ajax로 insert,update,delete한다.
* @param url: 호출 페이지
* @param formId: 넘길 폼 Id
* @param divId: reponse 받을 객체
*/

ajax.update = function(url, formId){
	var targetUrl = url;	
	var param = $("#"+formId).serialize();
	$.post(targetUrl,param,
			function(data){				
				alert($.trim(data));										
			});
}
