// Excel Download - 기존 HTML방식
function getExcel( $e, $url, $data ){
	var e = $e ? $e : window.event;
	var tg = ( e.currentTarget ) ? $( e.currentTarget ) : $( e.srcElement );
	var loader;
	tg.parents().each( function(){
		if( this.tagName == "SECTION" ) {
			loader = $( this ).find( ".ui_loader.v1" );
			return false;
		}
	});
	var dataParams = $data;
	if( String( typeof( dataParams ) ).toLowerCase() == "string" && dataParams.indexOf( "{" ) > -1 ) dataParams = $.parseJSON( dataParams );
	$.ajax({ type : "POST"
		,url : $url
		,timeout : 1000 * 60 * 5
		,dataType : "text"
		,data : dataParams
		,success : function( data ){
			var frm_excel = document.proceeExcel;
			frm_excel.target = 'processFrm';
			frm_excel.action = '../common/excel_down_prc.jsp';
			frm_excel.dataToDisplay.value = data;
			frm_excel.submit();
		}
		,beforeSend : function(){
			loader.fadeIn(80);
		}
		,complete : function(){
			loader.fadeOut(80);
		}
		,error : function( $err ){
			messageBox( $err.status + "<br>url : " + $url, "Ajax Error", 0, 1 );
		}
	});
}


// Excel Download - POI방식
function getExcel_POI( $e, $url, $data ){
	var e = $e ? $e : window.event;
	var tg = ( e.currentTarget ) ? $( e.currentTarget ) : $( e.srcElement );
	var loader;
	tg.parents().each( function(){
		if( this.tagName == "SECTION" ) {
			loader = $( this ).find( ".ui_loader.v1" );
			return false;
		}
	});
	var dataParams = $data;
	if( String( typeof( dataParams ) ).toLowerCase() == "string" && dataParams.indexOf( "{" ) > -1 ) dataParams = $.parseJSON( dataParams );
	$.ajax({ type : "POST"
		,url : $url
		,timeout : 30000
		,dataType : "text"
		,data : dataParams
		,success : function( $url ){
			$( "#processFrm" ).attr( "src", decodeURIComponent( $url ) );
		}
		,beforeSend : function(){
			loader.fadeIn(80);
		}
		,complete : function(){
			loader.fadeOut(80);
		}
		,error : function( $err ){
			messageBox( $err.status + "<br>url : " + $url, "Ajax Error", 0, 1 );
		}
	});
}