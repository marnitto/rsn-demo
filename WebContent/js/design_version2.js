/*!
 *
 * @author: RSN R&D Team LHS(GUNI)
 *			h2dlhs@realsn.com
 *
 *
 **/


// IE8인 경우 체크
var ie8Chk = isIE_ver8();
function isIE_ver8() { 
	if ( window.navigator.userAgent.search("MSIE 8")>-1) return true;
	return false;
}

function ui_reset(){
	// Design SelectBox 셋팅
	$( ".dcp > select + label" ).each(function() {
		$( this ).html( $( this ).parent().find("select > option:selected").html() );
	});
	$( ".dcp > select" ).bind( "change", function($e){
		$( this ).next().html( $( this ).find("> option:selected").html() );
	});

	// Design CheckBox - 전체선택 기능
	$( ".dcp > input[type='checkbox']" ).unbind( "change" ).change(function($e){
		if( $( this ).hasClass( "boardAllChecker" ) ) {
			if( this.checked ) {
				$( ".dcp > input[name='" + this.name + "']:not(.boardAllChecker)" ).not( "[disabled]" ).each(function(){
					this.checked = true;
				});
			} else {
				$( ".dcp > input[name='" + this.name + "']:not(.boardAllChecker)" ).not( "[disabled]" ).each(function(){
					this.checked = false;
				});
			}
		} else {
			var allChker = true;
			$( ".dcp > input[name='" + this.name + "']:not(.boardAllChecker)" ).not( "[disabled]" ).each(function(){
				if( !this.checked ) allChker = false;
			});
			if( allChker ) {
				$( ".dcp > input[name='" + this.name + "'].boardAllChecker" ).not( "[disabled]" ).prop( "checked", true );
			} else {
				$( ".dcp > input[name='" + this.name + "'].boardAllChecker" ).not( "[disabled]" ).prop( "checked", false );
			}
		}
	});
}

(function(){

	$( document ).ready( function() {
		ui_reset();

		// Date picker 설정
		if( $( ".ui_datepicker_input" ).length > 0 ) {
			$( ".ui_datepicker_input" ).datepicker({
				dateFormat: "yy-mm-dd",
				monthNames: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
				dayNamesMin: ['일','월','화','수','목','금','토']
			});
			$( ".input_date_first.ui_datepicker_input" ).datepicker().change(function(){
				var tempDate = $( this ).datepicker("getDate");
				var tempDate2 = $( this ).datepicker("getDate");
				tempDate2.setDate( tempDate2.getDate() + 30 );
				$( this ).parent().find( ".date_month" ).html( ( tempDate.getMonth() + 1 ) + "월" );
				$( this ).parent().find( ".date_date" ).html( "" + tempDate.getDate() );
				$( this ).parent().parent().find( ".input_date_last.ui_datepicker_input" ).datepicker("option", "minDate", $( this ).val());
				$( this ).parent().parent().find( ".input_date_last.ui_datepicker_input" ).datepicker("option", "maxDate", tempDate2);
			});
			$( ".input_date_last.ui_datepicker_input" ).datepicker().change(function(){
				var tempDate = $( this ).datepicker("getDate");
				$( this ).parent().find( ".date_month" ).html( ( tempDate.getMonth() + 1 ) + "월" );
				$( this ).parent().find( ".date_date" ).html( "" + tempDate.getDate() );
			});
		}

	});

})();