/*!
 *
 * @author: RSN R&D Team LHS(GUNI)
 *			h2dlhs@realsn.com
 *
 *
 **/

// -----  PROTOTYPE  ----- //
String.prototype.replaceAll = function( $rgExp, $replaceText ){			// 글자바꾸기
	var oStr = this;
	while (oStr.indexOf($rgExp) > -1)
	oStr = oStr.replace($rgExp, $replaceText);
	return oStr;
}

String.prototype.paramToJson = function() {
	var param = this;
    var hash;
    var result = {};
    var hashes = param.slice( param.indexOf( "?" ) + 1 ).split( "&" );
    for( var Loop1 = 0 ; Loop1 < hashes.length ; ++Loop1 ) {
        hash = hashes[ Loop1 ].split('=');
        result[ hash[ 0 ] ] = hash[ 1 ];
    }
    return result;
}

Number.prototype.addComma = function() {			// 3자리마다 콤마
    var txtNumber = '' + this;
    if (isNaN(txtNumber) || txtNumber == "") {
        alert("숫자만 입력 하세요");
        return;
    }
    else {
        var rxSplit = new RegExp('([0-9])([0-9][0-9][0-9][,.])');
        var arrNumber = txtNumber.split('.');
        arrNumber[0] += '.';
        do {
            arrNumber[0] = arrNumber[0].replace(rxSplit, '$1,$2');
        } while (rxSplit.test(arrNumber[0]));
 
        if (arrNumber.length > 1) {
            return arrNumber.join('');
        }
        else {
            return arrNumber[0].split('.')[0];
        }
    }
}
// -----  PROTOTYPE END  ----- //

var gnbIDX = "";
var headerFixed = $.cookie( "headerPin" );

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

	// 커스텀 셀렉트박스(체크박스 가지고 있는 셀렉트박스)
	$( ".dcp_c > button.select" ).each( function(){
		var tg = $( this );
		var list = $( this ).parent().find( "ul" );
		if( $( this ).css( "width" ) == "auto" ) $( this ).width( list.outerWidth() );
		$( this ).unbind( "click", hndl_c_sel ).click( hndl_c_sel );
		function hndl_c_sel( $e ){
			if( !list.hasClass( "active" ) ) {
				list.css( { "display" : "block" } ).addClass( "active", 200, "easeOutExpo" );
				$( document ).click( doc_click );
			} else {
				list.removeClass( "active", 200, "easeInExpo", function(){
					list.hide();
				});
				$( document ).unbind( "click", doc_click );
			}
			function doc_click( $e ){
				if( !list.hasClass( "active" ) ) return;
				var container = $(".dcp_c");
				if( !container.is( $e.target ) && container.has( $e.target ).length === 0 ) {
					hndl_c_sel();
					$( document ).unbind( "click", doc_click );
				}
			}
		}
		tg.parent().find( "input[type=checkbox]" ).unbind( "change", hndl_c_sel_btn ).change( hndl_c_sel_btn );
		hndl_c_sel_btn();
		function hndl_c_sel_btn(){
			var selStr = "";
			var selSpan = $( "<span />" );
			tg.parent().find( "input[type=checkbox]:checked + label > *:not(.chkbox_00)" ).each( function( $idx ){
				selStr += $( this ).html();
				if( $idx != ( tg.parent().find( "input[type=checkbox]:checked + label > *:not(.chkbox_00)" ).length -1 ) ) selStr += ", "
			});
			if( selStr == "" ) selStr = "선택";
			if( selStr.indexOf( "전체" ) >= 0 ) selStr = "전체";
			selSpan.html( selStr );
			tg.html( selSpan );
		}
	});
}

(function(){

	$( document ).ready( function() {
		ui_reset();

		// GNB
		var nav_1d_idx = gnbIDX.substr( 0, 2 );
		var nav_2d_idx = gnbIDX.substr( 2, 2 );
		hndl_gnb();
		function hndl_gnb(){
			$( "nav > ul > li > a" ).each( function( $e ){
				if( $( this ).attr( "data-idx" ) == nav_1d_idx ) {
					$( this ).addClass( "active" );
					$( this ).parent().find( ".subnav a" ).each( function(){
						if( $( this ).attr( "data-idx" ) == nav_2d_idx ) {
							$( this ).addClass( "active" );
						}
					});
				}
			});
		}

		// 날짜 검색 설정
		var selDate_1;
		var selDate_2;
		$( ".ui_search_date" ).each( function(){
			$( ".ui_datepicker_input_range" ).each( function(){
				$( ".ui_datepicker_input_range .dp" ).datepicker({
					dateFormat: "yy-mm-dd",
					monthNames: [ "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" ],
					dayNamesMin: [ "Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" ]
				});

				$( "#searchs_dp_start" ).datepicker( "setDate", sel_sDate );
				$( "#searchs_dp_end" ).datepicker( "setDate", sel_eDate );
				$( "#searchs_dp_start" ).datepicker().each( date_limit );
				$( "#searchs_dp_start" ).datepicker().change( date_limit );
				$( "#searchs_dp_end" ).datepicker().each( date_limit );
				$( "#searchs_dp_end" ).datepicker().change( date_limit );

				var result_date = $( "#searchs_dp_start" ).val().split( "-" )[ 1 ] + "-" + $( "#searchs_dp_start" ).val().split( "-" )[ 2 ] + "~" + $( "#searchs_dp_end" ).val().split( "-" )[ 1 ] + "-" + $( "#searchs_dp_end" ).val().split( "-" )[ 2 ];
				$( "#searchs_date_range" ).val( result_date );

				$( "#searchs_date_range" ).click( function(){
					if( !$( this ).parent().find( ".calendars" ).is( ":visible" ) ){
						$( this ).addClass( "active" );
						$( this ).parent().find( ".calendars" ).fadeIn( 120 );
						selDate_1 = $( "#searchs_dp_start" ).val();
						selDate_2 = $( "#searchs_dp_end" ).val();
					} else {
						date_sel_cancel();
					}
				});
				$( ".ui_datepicker_input_range .btns button" ).click( function(){
					date_sel_success();
					if( selDate_1 == $( "#searchs_dp_start" ).val() && selDate_2 == $( "#searchs_dp_end" ).val() ) return false;
					$( "input[name=date_group]:checked" ).attr( "checked", false );
				});
			});
			function date_limit(){
				$( "#searchs_dp_start" ).datepicker("option", "maxDate", $( "#searchs_dp_end" ).val() );
				$( "#searchs_dp_end" ).datepicker("option", "minDate", $( "#searchs_dp_start" ).val() );
			}
			function date_sel_success(){
				$( "#searchs_date_range" ).removeClass( "active" );
				$( ".ui_datepicker_input_range .calendars" ).fadeOut( 120 );
				var result_date = $( "#searchs_dp_start" ).val().split( "-" )[ 1 ] + "-" + $( "#searchs_dp_start" ).val().split( "-" )[ 2 ] + "~" + $( "#searchs_dp_end" ).val().split( "-" )[ 1 ] + "-" + $( "#searchs_dp_end" ).val().split( "-" )[ 2 ];
				$( "#searchs_date_range" ).val( result_date );
			}
			function date_sel_cancel(){
				$( "#searchs_date_range" ).removeClass( "active" );
				$( ".ui_datepicker_input_range .calendars" ).fadeOut( 120, function(){
					$( "#searchs_dp_start" ).datepicker( "setDate", selDate_1 );
					$( "#searchs_dp_end" ).datepicker( "setDate", selDate_2 );
				});
			}
			$( ".ui_date_group" ).each( function(){
				$( "#group_01" ).val( ( ( new Date() ).getMonth() == 0 ) ? 12 : ( new Date() ).getMonth() );
				$( "#group_02" ).val( ( new Date() ).getMonth() + 1 );
				$( "#group_01 + label" ).html( $( "#group_01" ).val() + "월" );
				$( "#group_02 + label" ).html( $( "#group_02" ).val() + "월" );
				$( "input[name=date_group]" ).change( function(){
					$( "#searchs_dp_start" ).datepicker("option", "maxDate", null );
					$( "#searchs_dp_end" ).datepicker("option", "minDate", null );
					var year = new Date().getFullYear();
					var mon_1 = parseInt( $( "#group_01" ).val() );
					var mon_2 = parseInt( $( "#group_02" ).val() );
					var date_1;
					var date_2;
					$( "input[name=date_group]:checked" ).each( function(){
						var idx = $( this ).parent().index();
						switch( idx ){
							case 0:
								date1 = year + "-" + mon_1 + "-1";
								date2 = year + "-" + mon_1 + "-" + ( new Date( ( new Date( year, mon_1, 1 ) ) - 1 ) ).getDate();
								$( "#searchs_dp_start" ).datepicker( "setDate", date1 );
								$( "#searchs_dp_end" ).datepicker( "setDate", date2 );
								date_limit();
								break;
							case 1:
								date1 = year + "-" + mon_2 + "-1";
								date2 = year + "-" + mon_2 + "-" + ( new Date( ( new Date( year, mon_2, 1 ) ) - 1 ) ).getDate();
								$( "#searchs_dp_start" ).datepicker( "setDate", date1 );
								$( "#searchs_dp_end" ).datepicker( "setDate", date2 );
								date_limit();
								break;
							case 2:
								$( "#searchs_dp_start" ).datepicker( "setDate", -parseInt( $( "#group_03" ).val() ) );
								$( "#searchs_dp_end" ).datepicker( "setDate", new Date() );
								date_limit();
								break;
							case 3:
								$( "#searchs_dp_start" ).datepicker( "setDate", -parseInt( $( "#group_04" ).val() ) );
								$( "#searchs_dp_end" ).datepicker( "setDate", new Date() );
								date_limit();
								break;
							case 4:
								$( "#searchs_dp_start" ).datepicker( "setDate", new Date() );
								$( "#searchs_dp_end" ).datepicker( "setDate", new Date() );
								date_limit();
								break;
						}
						date_sel_success();
					});
				});
			});
		});

		// Header 고정
		$( ".btn_header_pin input" ).each( function(){
			$( this ).change( function(){
				if( this.checked ) $.cookie( "headerPin", "true", { expires : 99999999, path : "/" } );
				else $.cookie( "headerPin", "false", { expires : 99999999, path : "/" } );
				hndl_header();
			});
			
			(function(){
				if( $.cookie( "headerPin" ) == "true" ) $( ".btn_header_pin input" )[ 0 ].checked = true;
				else $( ".btn_header_pin input" )[ 0 ].checked = false;
				hndl_header();
			})();
			
			function hndl_header(){
				if( $.cookie( "headerPin" ) == "true" ) {
					$( "header" ).addClass( "fixed" );
					$( ".btn_header_pin .title" ).html( "고정해제" );
					$( ".btn_header_pin label" ).attr( "title", "메뉴 고정 해제" );
				} else {
					$( "header" ).removeClass( "fixed" );
					$( ".btn_header_pin .title" ).html( "메뉴고정" );
					$( ".btn_header_pin label" ).attr( "title", "메뉴 고정" );
				}
				hndl_navigator();
			};
		});

		// Side Navigator
		$( "#navigator" ).each( function(){
			$( this ).find( "a" ).each( function(){
				$( this ).click( function(){
					var hf = 0;
					if( $.cookie( "headerPin" ) == "true" ) hf = 110;
					var st = $( this ).attr( "href" );
					var posY = $( st ).offset().top - 20 - hf;
					$( "html, body" ).animate( { "scrollTop" : posY }, "600", "easeInOutExpo" );
					return false;
				});
			});
			hndl_navigator();
			$( window ).scroll( hndl_navigator );
		});
		function hndl_navigator() {
			var hf = 0;
			var sv = $( window ).scrollTop();
			//var basePosY = Math.round( $( window ).height() / 2 );
			var basePosY = 200;
			if ( sv >= 181 ) {
				$( "#content .ui_boxs_00" ).each( function( $idx ){
					if( $( this ).offset().top <= ( sv + basePosY ) ) {
						$( "#navigator li a" ).removeClass( "active" );
						$( "#navigator li" ).eq( $idx ).find( "a" ).addClass( "active" );
					} else if( sv == ( $( document ).height() - $( window ).height() ) ) {
						$( "#navigator li a" ).removeClass( "active" );
						$( "#navigator li" ).eq( $( "#navigator li" ).length - 1 ).find( "a" ).addClass( "active" );
					}
				});
			} else {
				$( "#navigator li a" ).removeClass( "active" );
				$( "#navigator li" ).eq( 0 ).find( "a" ).addClass( "active" );
			}
			if( $.cookie( "headerPin" ) == "false" ) {
				if( sv > 160 ) $( "#navigator" ).css( { "top" : 20 } );
				else $( "#navigator" ).css( { "top" : 181 - sv } );
			} else $( "#navigator" ).css( { "top" : 181 } );
		};

		// 정렬 폴딩
		$( ".ui_sort_fold" ).each( function(){
			var tg = $( this );
			var active = false;
			if( $( this ).find( ".btn_fold" ).hasClass( "active" ) ) active = true;
			$( this ).find( ".btn_fold" ).click( function(){
				active = !active;
				if( active ) {
					$( this ).addClass( "active" );
				} else {
					$( this ).removeClass( "active" );
				}
				hndl_sortFold();
			});
			hndl_sortFold();
			function hndl_sortFold(){
				if( active ){
					tg.find( ".sort_container" ).animate( { height : tg.find( ".sort_container > ul" ).outerHeight() }, 400, "easeInOutExpo" );
				} else {
					tg.find( ".sort_container" ).animate( { height : 22 }, 400, "easeInOutExpo" );
				}
			}
		});

		// 검색 설정 - 상세검색
		hndl_searchs_ds();
		$( ".searchs_container .btn_toggle" ).click( function(){
			if( !$( this ).hasClass( "active" ) ) $( this ).addClass( "active" );
			else $( this ).removeClass( "active" );
			hndl_searchs_ds();
		});
		function hndl_searchs_ds(){
			if( $( ".searchs_container .btn_toggle" ).hasClass( "active" ) ){
				$( ".searchs_container .fold_box" ).stop().animate( 
					{ "height" : $( ".searchs_container .fold_box .fold_container" ).outerHeight(), "padding" : "10px 0 0 0" },
				{
					duration : 300,
					easing : "easeInOutExpo",
					start : function(){
						//$( ".searchs_container" ).stop().animate( { "height" : $( ".searchs_container" ).height() + $( ".searchs_container .fold_box .fold_container" ).outerHeight(), "padding" : "14px 20px" }, 300, "easeInOutExpo" );
					}
				});
			} else {
				$( ".searchs_container .fold_box" ).stop().animate( 
					{ "height" : 0, "padding" : 0 },
				{
					duration : 300,
					easing : "easeInOutExpo",
					start : function(){
						//$( ".searchs_container" ).stop().animate( { "height" : $( ".searchs_container" ).height() - $( ".searchs_container .fold_box .fold_container" ).outerHeight(), "padding" : "14px 20px" }, 300, "easeInOutExpo" );
					}
				});
			}
		}

		// Bubble Tip
		$( ".ui_btn_02[ title='TIP' ]" ).each( function(){
			$( this ).bind( "mouseenter mouseleave focus blur", function( $e ){
				var tip = $( this ).next();
				if( $e.type == "mouseenter" || $e.type == "focus" ){
					var pos = {};
					pos.top = $( this ).position().top + $( this ).outerHeight() + 10;
					pos.left = $( this ).position().left - tip.outerWidth() + ( $( this ).outerWidth() / 2 ) + 21;
					if( tip.hasClass( "ui_bubble_tip" ) ) tip.css({ top : pos.top, left : pos.left }).fadeIn( 120 );
				} else {
					if( tip.hasClass( "ui_bubble_tip" ) ) tip.fadeOut( 120 );
				}
			});
		});

		// Scroll Top Header
		$( window ).scroll( hndl_scroll_header );
		function hndl_scroll_header( $e ){
			if( $( document ).scrollTop() >= 40 ) $( "header" ).addClass( "pos_fix" );
			else $( "header" ).removeClass( "pos_fix" );
			if( $( "header" ).hasClass( "pos_fix" ) ) $( "header" ).css( { "left" : -$( window ).scrollLeft() } );
			else $( "header" ).css( { "left" : 0 } );
		}

		// Layer Popup
		$( ".l_pop" ).bind( "click", function($e){
			var lnkUrl = $( this ).attr( "href" );
			popupOpen(lnkUrl);
			return false;
		});

		// 팝업 Disolve
		$( "#popup .bg" ).click( hndl_popupClose );

		$( window ).resize( function(){
			hndl_navigator();
			$( ".popup_item" ).each( function(){
				hndl_popupUpdate( $( this ) );
			});
		});
	});

})();

// Page Title
function setPageTitle( $val ){
	document.title = $val;
}

// 게시판 상세보기
function hndl_similars( $type ){
	$( ".ui_board_list_02 .btn_similar" ).each( function(){
		if( $type == 0 ){
			$( this ).addClass( "active" );
		} else {
			$( this ).removeClass( "active" );
		}
		hndl_similar_item( this );
	});
}
function hndl_similar( $tg ){
	if( !$( $tg ).hasClass( "active" ) ) $( $tg ).addClass( "active" );
	else $( $tg ).removeClass( "active" );
	hndl_similar_item( $tg );
}
function hndl_similar_item( $tg ){
	var tg = $( $tg ).parent().parent().next().find( ".similar_container" );
	if( $( $tg ).hasClass( "active" ) ){
		$( $tg ).parent().parent().addClass( "similar_active" );
		tg.find( "> *" ).show();
		tg.animate( { height : tg.find( "> *" ).outerHeight() }, 300, "easeInOutExpo" );
	} else {
		tg.animate( { height : 0 }, 300, "easeInOutExpo", function(){
			$( $tg ).parent().parent().removeClass( "similar_active" );
			tg.find( "> *" ).hide();
		});
	}
}

function popupOpen( $lnk, $data ){
	var lnk = $lnk.split( "?" )[ 0 ];
	var param;
	if( $lnk.split( "?" ).length > 1 ) param = $lnk.split( "?" )[ 1 ].paramToJson();
	if( $data ) param = $data;
	var popup;
	var popupLnkSameChk = false;
	var popupAjax = $.ajax({
		type : "POST", 
		url : lnk,
		data : param,
		timeout : 30000,
		cache : false,
		beforeSend : function(){
			$( ".popup_item" ).each( function(){
				if( $( this ).attr( "data-lnk" ) == lnk ) {
					popupLnkSameChk = true;
					popup = $( this );
				}
			});
			if( popupLnkSameChk ) {
				popup.find( ".ui_loader.v0" ).fadeIn( 80 );
			} else {
				popup = $( "<div class='popup_item' data-lnk='" + lnk + "'><div class='bg'></div><div class='container'><div class='ui_loader v0' style='display:block'><span class='loader'>Load</span></div></div></div>" );
				$( "body" ).append( popup );
				$( popup ).find( ".bg" ).click( function(){
					popupAjax.abort();
					hndl_popupClose( popup );
				});
				hndl_popupOpen( popup );
			}
		},
		success : function( $result ){
			$( popup ).find( ".container" ).html( $( $result ).find( ".container" ).html() );
			$( popup ).find( ".header .close" ).click( function(){
				hndl_popupClose( popup );
			});
			hndl_popupUpdate( popup );
			pop_ui_reset( popup );
		},
		error : function($err){
			hndl_popupClose( popup );
			if( $err.statusText != "abort" ) messageBox( $err.status + "<br>url : " + $lnk, "Ajax Error", 0, 1 );
		}
	});
}
function hndl_popupOpen( $tg ){			// 팝업 열기
	var tg = $( $tg ).find( ".container" );
	$( $tg ).stop().fadeIn( 200 );
	tg.css( "top", "53%" ).show();
	tg.css( "top", "53%" ).stop().animate( { "top" : "50%" }, 200, "easeOutQuad" );
	hndl_popupUpdate( $tg );
}
function hndl_popupClose( $tg ){		// 팝업 닫기
	var tg = $tg;
	if( !tg ) tg = $( this ).parents( ".popup_item" );
	var tgContainer = tg.find( ".container" );
	tgContainer.stop().animate( { "top" : "47%" }, 200, "easeInQuad" );
	tg.stop().fadeOut( 200, function(){
		tg.remove();
	});
	return false;
}
function hndl_popupUpdate( $tg ){
	var tg = $( $tg ).find( ".container" );
	//if( $( ".ui_mid_navigator" ).is( ":visible" ) ) {
	if( $( document ).width() > 767 ) {
		tg.css( { left : "0", "margin-left" : -tg.outerWidth() / 2 });
		tg.css( { top : "50%", "margin-top" : -tg.outerHeight() / 2, left : "50%", "margin-left" : -tg.outerWidth() / 2 });
	} else {
		tg.css( { top : 0, "margin-top" : 0, left : 0, "margin-left" : 0 });
	}
}
function pop_ui_reset( $tg ){
	var popup = $( $tg );
	// Design SelectBox 셋팅
	popup.find( ".dcp > select + label" ).each(function() {
		$( this ).html( $( this ).parent().find("select > option:selected").html() );
	});
	popup.find( ".dcp > select" ).bind( "change", function($e){
		$( this ).next().html( $( this ).find("> option:selected").html() );
	});

	// Date picker 설정
	if( popup.find( ".ui_datepicker_input" ).length > 0 ) {
		popup.find( ".ui_datepicker_input" ).datepicker({
			dateFormat: "yy-mm-dd",
			monthNames: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
			dayNamesMin: ['일','월','화','수','목','금','토']
		});
		popup.find( ".input_date_first.ui_datepicker_input" ).datepicker().each( first_dp_change );
		popup.find( ".input_date_first.ui_datepicker_input" ).datepicker().change( first_dp_change );
		popup.find( ".input_date_last.ui_datepicker_input" ).datepicker().each( last_dp_change );
		popup.find( ".input_date_last.ui_datepicker_input" ).datepicker().change( last_dp_change );
		function first_dp_change(){
			var tempDate = $( this ).datepicker("getDate");
			var tempDate2 = $( this ).datepicker("getDate");
			tempDate2.setDate( tempDate2.getDate() + 30 );
			$( this ).parent().find( ".date_month" ).html( ( tempDate.getMonth() + 1 ) + "월" );
			$( this ).parent().find( ".date_date" ).html( "" + tempDate.getDate() );
			$( this ).parent().parent().find( ".input_date_last.ui_datepicker_input" ).datepicker("option", "minDate", $( this ).val());
			$( this ).parent().parent().find( ".input_date_last.ui_datepicker_input" ).datepicker("option", "maxDate", tempDate2);
		}
		function last_dp_change(){
			var tempDate = $( this ).datepicker("getDate");
			$( this ).parent().find( ".date_month" ).html( ( tempDate.getMonth() + 1 ) + "월" );
			$( this ).parent().find( ".date_date" ).html( "" + tempDate.getDate() );
		}
	}

	// Layer Popup
	popup.find( ".l_pop" ).bind( "click", function($e){
		var lnkUrl = $( this ).attr( "href" );
		popupOpen(lnkUrl);
		return false;
	});
}

// 미니 팝업 컨트롤러
function m_popupOpen( $tg, $lnk, $data ){
	var popup = $( "<div class='m_pop_item'><div class='ui_loader v0' style='display:block'><span class='loader'>Load</span></div></div>" );
	var lnk = $lnk.split( "?" )[ 0 ];
	var param;
	if( $lnk.split( "?" ).length > 1 ) param = $lnk.split( "?" )[ 1 ].paramToJson();
	if( $data ) param = $data;
	$.ajax({
		type : "POST", 
		url : lnk,
		data : param,
		cache : false,
		beforeSend : function(){
			$( "#container" ).append( popup );
			hndl_m_popupOpen( popup );
			hndl_m_popupUpdate( $( $tg ), popup );
		},
		success : function( $result ){
			popup.html( $( $result ).find( ".container" ).html() );
			hndl_m_popupUpdate( $( $tg ), popup );
		},
		error : function($err){
			messageBox( $err.status + "<br>url : " + $lnk, "Ajax Error", 0, 1 );
		}
	});
}
function hndl_m_popupOpen( $tg ){
	$tg.hide()
	$tg.fadeIn( 200 );
}
function hndl_m_popupClose( $tg ){
	var popup = $( $tg ).parent().parent().parent();
	popup.stop().fadeOut( 200, function(){
		popup.remove();
	});
}
function hndl_m_popupUpdate( $tg, $popup ){
	var pos = {};
	pos.top = ( $tg.offset().top - $( "#container" ).offset().top ) - $popup.outerHeight() - 5;
	pos.left = ( $tg.offset().left - $( "#container" ).offset().left ) - ( $popup.outerWidth() / 2) + ( $tg.width() / 2 );
	//if( pos.top < ( 0 + parseInt( $( "#container" ).css( "padding-top" ) ) ) ) pos.top = ( 0 + parseInt( $( "#container" ).css( "padding-top" ) ) );
	if( pos.top < 0 ) pos.top = parseInt( $tg.offset().top ) + parseInt( $tg.outerHeight() ) + 5 - $( "#container" ).offset().top;
	if( pos.left < 0 ) pos.left = 0;
	if( ( pos.left + $popup.outerWidth() ) > $( "#container" ).width() ) pos.left = $( "#container" ).width() - $popup.outerWidth();
	$popup.css( { top : pos.top, left : pos.left } );
}

function hndl_m_popup2Open( $tg ){			// 미니 팝업 열기
	var tg = $( "#m_popup .popup." + $tg );
	$( "#m_popup" ).stop().fadeIn( 200 );
	tg.show();
	tg.css( "top", "53%" ).stop().animate( { "top" : "50%" }, 200, "easeOutQuad" );
	hndl_m_popup2Update();
}
function hndl_m_popup2Close(){					// 팝업 닫기
	$( "#m_popup .popup" ).stop().animate( { "top" : "47%" }, 200, "easeInQuad" );
	$( "#m_popup" ).stop().fadeOut( 200, function(){
		$( "#m_popup .popup" ).hide();
	});
}
function hndl_m_popup2Update(){
	var tg = $( "#m_popup .popup" );
	if( $( document ).width() > 767 ) {
		tg.css( { left : "0", "margin-left" : -tg.outerWidth() / 2 });
		tg.css( { top : "50%", "margin-top" : -tg.outerHeight() / 2, left : "50%", "margin-left" : -tg.outerWidth() / 2 });
	} else {
		tg.css( { top : 0, "margin-top" : 0, left : 0, "margin-left" : 0 });
	}
}

// MessageBox
var messageBoxStack = [];
var arrAlertType = [];
	arrAlertType[ 1 ] = $( "<div class='icons'><span class='icon_error'></span></div>" );
	arrAlertType[ 2 ] = $( "<div class='icons'><span class='icon_warning'></span></div>" );
	arrAlertType[ 3 ] = $( "<div class='icons'><span class='icon_info'></span></div>" );
	arrAlertType[ 4 ] = $( "<div class='icons'><span class='icon_question'></span></div>" );
var arrAlertBtns = [];
	arrAlertBtns[ 0 ] = $( "<button type='button' class='ui_shadow_00' data-value='0'><span>확인</span></button>" );
	arrAlertBtns[ 1 ] = $( "<button type='button' class='ui_shadow_00' data-value='1'><span>취소</span></button>" );
	arrAlertBtns[ 2 ] = $( "<button type='button' class='ui_shadow_00' data-value='2'><span>예</span></button>" );
	arrAlertBtns[ 3 ] = $( "<button type='button' class='ui_shadow_00' data-value='3'><span>아니요</span></button>" );
function messageBox( $txt, $title, $btnType, $mType, $func ) {
	var stackData = {};
	stackData.txt = $txt;
	stackData.title = $title;
	stackData.btnType = $btnType;
	stackData.mType = $mType;
	stackData.func = $func;
	messageBoxStack.push( stackData );
	if( !$( "#msg_box" ).is( ":visible" ) ) openMessageBox();
}
function openMessageBox(){
	var $txt = messageBoxStack[ 0 ].txt;
	var $title = messageBoxStack[ 0 ].title;
	var $btnType = messageBoxStack[ 0 ].btnType;
	var $mType = messageBoxStack[ 0 ].mType;
	var $func = messageBoxStack[ 0 ].func;

	$( "#msg_box .box" ).html("");
	$( "#msg_box .box" ).hide();
	if( $title && $title != "" ) $( "#msg_box .box" ).append( "<h2>" + $title + "</h2>" );
	if( $mType && $mType != 0 ) $( "#msg_box .box" ).append( arrAlertType[ $mType ] );
	if( $txt != "" ){
		if( !$mType || $mType == 0 ) $( "#msg_box .box" ).append( "<div class='txts alone'><span>" + String($txt).replaceAll("\n", "<br>") + "</span></div>" );
		else $( "#msg_box .box" ).append( "<div class='txts'><span>" + $txt + "</span></div>" );
	}
	$( "#msg_box .box" ).append( "<div class='btns'></div>" );
	if( !$btnType ) $btnType = 0;
	switch( $btnType ){
		case 0 :
			$( "#msg_box .box .btns" ).append( arrAlertBtns[ 0 ] );
			break;
		case 1 :
			$( "#msg_box .box .btns" ).append( arrAlertBtns[ 0 ] );
			$( "#msg_box .box .btns" ).append( arrAlertBtns[ 1 ] );
			break;
		case 2 :
			$( "#msg_box .box .btns" ).append( arrAlertBtns[ 2 ] );
			$( "#msg_box .box .btns" ).append( arrAlertBtns[ 3 ] );
			break;
		case 3 :
			$( "#msg_box .box .btns" ).append( arrAlertBtns[ 2 ] );
			$( "#msg_box .box .btns" ).append( arrAlertBtns[ 3 ] );
			$( "#msg_box .box .btns" ).append( arrAlertBtns[ 1 ] );
			break;
	}
	$( "#msg_box .box .btns > *" ).click( function(){
		if( $func ) $func( $( this ).attr( "data-value" ) );
		closeMessageBox();
	});
	$( "#msg_box" ).fadeIn( 200, function(){
		$( "#msg_box .box" ).css( "top", "53%" ).show().animate( { "top" : "50%" }, 200, "easeOutQuad", function(){
			if( messageBoxStack.length > 0 ) messageBoxStack.splice( 0, 1 );
			$( "#msg_box .box .btns > *" ).eq( 0 ).focus();
		});
		$( "#msg_box .box" ).css( { "margin-top" : -$( "#msg_box .box" ).outerHeight() / 2, "margin-left" : -$( "#msg_box .box" ).outerWidth() / 2 });
	});
}
function closeMessageBox(){
	if( messageBoxStack.length > 0 ) {
		openMessageBox();
		return;
	}
	$( "#msg_box .box" ).animate( { "top" : "47%" }, 200, "easeInQuad" );
	$( "#msg_box" ).fadeOut( 200, function(){
		$( "#msg_box .box .btns > *" ).unbind( "click" );
		$( "#msg_box .box" ).children().remove();
	});
}
function alert( $txt ){
	messageBox( $txt );
}


// Preview
var preview;
var preview_tg;
function previewOn( $tg, $container ){
	preview_tg = $( $tg );
	var idx = new Date().getTime();
	var pos = {};
	$( $tg ).attr( "data-idx", "preview_" + idx );
	preview = $( "<div id='preview_" + idx + "' class='preview_box'><span class='arrow'></span>" + $( $tg ).attr( "data-txts" ) + "</div>" );
	$( "body" ).append( preview );

	if( !$container ) $container = $( "#container" );

	$( document ).mousemove( evt_previewMove );

	pos.x = $( $tg ).offset().left - 50;
	pos.y = parseInt( $( $tg ).offset().top ) - parseInt( preview.outerHeight() ) - 10;

	if( pos.x < $container.offset().left ) pos.x = $container.offset().left;
	if( ( pos.x + preview.outerWidth() ) > ( $container.offset().left + $container.outerWidth() ) ) pos.x = ( $container.offset().left + $container.outerWidth() ) - ( preview.outerWidth() );

	if( pos.y < $( document ).scrollTop() ) {
		pos.y = $( $tg ).offset().top + $( $tg ).outerHeight() + 10;
		preview.find( ".arrow" ).addClass( "up" );
	}
	if( pos.y + preview.outerHeight() > $( window ).height() ) {
		pos.y = parseInt( $( $tg ).offset().top ) - parseInt( preview.outerHeight() ) - 10;
		preview.find( ".arrow" ).removeClass( "up" );
	}

	preview.css( { top : pos.y, left : pos.x } );
	preview.fadeIn( 80 );
}
function previewOff( $tg ){
	if( $tg ) var idx = $( $tg ).attr( "data-idx" );
	if( preview ) preview.remove();
	preview = null;
	preview_tg = null;
	$( document ).unbind( "mousemove", evt_previewMove );
}
function evt_previewMove( $e ){
	if( $( $e.target ).attr( "data-idx" ) != preview_tg.attr( "data-idx" ) ){
		previewOff( preview_tg );
		return false;
	}
	var posX = $e.clientX - preview.offset().left - 6;
	if( preview && posX > ( preview.outerWidth() - 15 ) ) posX = preview.outerWidth() - 15;
	preview.find( ".arrow" ).css( { left : posX } );
}