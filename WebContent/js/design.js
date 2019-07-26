/*!
 *
 * @author: RSN R&D Team LHS(GUNI) 20141224
 *			h2dlhs@realsn.com
 *
 *
 *
 * 예약어 : popupOn, popupClose
 *
 **/

var dateChange = function(){};

/*
if( typeof WebFont !== 'undefined' ) {
	WebFont.load({
		custom: {
			families: ['Nanum Gothic'],
			urls: ['http://fonts.googleapis.com/earlyaccess/nanumgothic.css']
		}
	});
}
*/


(function($) {

	var frContentfirstLoadChk = false;
	var selGnbIdx_1d = -999;
	var selGnbIdx_2d = -999;
	var selGnbIdx_3d = -999;

	var setGnbIdx_1d = function($value) {
		selGnbIdx_1d = $value;
		hndl_nav_1d();
	};
	var setGnbIdx_2d = function($value) {
		selGnbIdx_2d = $value;
		hndl_nav_2d();
	};
	var setGnbIdx_3d = function($value) {
		selGnbIdx_3d = $value;
		hndl_nav_3d();
	};

	$( document ).ready( function() {
		// 팝업
		$( "#popup" ).fadeOut(0);
		$( "#popup > .bg" ).click(popupClose);

		// Datepicker 셋팅
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
				var sDate = $( this ).val();
				var eDate = $( this ).parent().parent().find( ".input_date_last.ui_datepicker_input" ).datepicker().val();
				if( dateChange ) dateChange($(this),sDate,eDate);
			});
			$( ".input_date_last.ui_datepicker_input" ).datepicker().change(function(){
				var tempDate = $( this ).datepicker("getDate");
				$( this ).parent().find( ".date_month" ).html( ( tempDate.getMonth() + 1 ) + "월" );
				$( this ).parent().find( ".date_date" ).html( "" + tempDate.getDate() );
				var sDate = $( this ).parent().parent().find( ".input_date_first.ui_datepicker_input" ).datepicker().val();
				var eDate = $( this ).val();
				if( dateChange ) dateChange($(this),sDate,eDate);
			});

			// 대시보드 상단 검색 데이트 피커
			$( "#input_date_first_top" ).datepicker().change(function(){
				var tempDate = $( this ).datepicker("getDate");
				tempDate.setDate( tempDate.getDate() + 7 );
				$( "#input_date_last_top" ).datepicker("option", "minDate", $( this ).datepicker("getDate"));
				$( "#input_date_last_top" ).datepicker("option", "maxDate", tempDate);
			});
			if( $( "#input_date_first_top" ).datepicker("getDate") ) $( "#input_date_first_top" ).datepicker().trigger("change");
		}

		// Design CheckBox - 전체선택 기능
		$( ".dcp > input[type='checkbox']" ).change(function($e){
			if( $( this ).hasClass( "boardAllChecker" ) ) {
				if( this.checked ) {
					$( ".dcp > input[name='" + this.name + "']:not(.boardAllChecker)" ).each(function(){
						this.checked = true;
					});
				} else {
					$( ".dcp > input[name='" + this.name + "']:not(.boardAllChecker)" ).each(function(){
						this.checked = false;
					});
				}
			} else {
				var allChker = true;
				$( ".dcp > input[name='" + this.name + "']:not(.boardAllChecker)" ).each(function(){
					if( !this.checked ) allChker = false;
				});
				if( allChker ) {
					$( ".dcp > input[name='" + this.name + "'].boardAllChecker" ).prop( "checked", true );
				} else {
					$( ".dcp > input[name='" + this.name + "'].boardAllChecker" ).prop( "checked", false );
				}
			}
		});

		// 아이프레임 - 컨텐츠 로드
		$( ".ui_con_frame_00" ).each(function() {
			if( !frContentfirstLoadChk ) $( this ).contents().get(0).location.href = $( this ).attr( "src" );
			$( ".ui_con_frame_00" ).load( evt_frameLoadCom );
			function evt_frameLoadCom($e){
				firstLoadChk = true;
				hndl_navIdx();
				hndl_contentAutoHgt();
			}
		});
		function hndl_contentAutoHgt() {
			$( ".ui_con_frame_00" ).height( "auto" );
			var contengHgt = $( ".ui_con_frame_00" ).contents().find( "body" )[0].scrollHeight + 50;
			$( ".ui_con_frame_00" ).height( contengHgt );
		}

		// Nav IDX
		function hndl_navIdx(){
			if( $( "nav" ).length > 0 ) {
				$( "nav > ul > li > a" ).each( function(){
					if( String( $( ".ui_con_frame_00" ).contents().get(0).location.href ).indexOf( $(this).attr( "href" ) ) >= 0 ) {
						setGnbIdx_1d( parseInt( $( this ).attr( "data-role-idx" ) ) );
						setGnbIdx_2d( 0 );
						setGnbIdx_3d( 0 );
					}
				});
				$( "nav > ul > li > .gnb_sub > ul > li > a" ).each( function(){
					if( String( $( ".ui_con_frame_00" ).contents().get(0).location.href ).indexOf( $(this).attr( "href" ) ) >= 0 ) {
						setGnbIdx_1d( parseInt( $( this ).parent().parent().parent().parent().find("> a" ).attr( "data-role-idx" ) ) );
						setGnbIdx_2d( parseInt( $( this ).attr( "data-role-idx" ) ) );
						setGnbIdx_3d( 0 );
					}
				});
			}
		}

		// GNB
		if( $( "nav" ).length > 0 ) {
			$( "nav > ul > li > .gnb_sub" ).each(function(){
				var navLen = 6;
				var ulCnt = 0;
				$( this ).find( "> ul > li > a" ).each(function($idx) {
					var idx = $( this ).attr( "data-role-idx" );
					if( idx != 0 && idx % navLen == 0 ) {
						var tag_ul = "<ul />";
						$( this ).parent().parent().parent().append( tag_ul );
						ulCnt++;
					}
					$( this ).parent().parent().parent().find( "> ul" ).eq( ulCnt ).append( $( this ).parent() );
				});
				$( this ).hover(function($e){
					if( $e.type == "mouseenter" ){
						$( "nav > ul > li > a[data-role-idx=" + $( this ).parent().find( "> a" ).attr( "data-role-idx" ) + "]" ).addClass( "active" );
						hndl_gnbSub( $( this ).parent().find( "> a" ).attr( "data-role-idx" ) );
					} else {
						$( "nav > ul > li > a" ).removeClass( "active" );
						$( "nav > ul > li > a[data-role-idx=" + selGnbIdx_1d + "]" ).addClass( "active" );
						hndl_gnbSub( null );
					}
				});
			});

			$( "nav > ul > li > a" ).each(function($idx){
				$( this ).hover(function($e){
					if( $e.type == "mouseenter" ){
						$( this ).addClass( "active" );
						hndl_gnbSub( $( this ).attr( "data-role-idx" ) );
					} else {
						$( this ).removeClass( "active" );
						$( "nav > ul > li > a[data-role-idx=" + selGnbIdx_1d + "]" ).addClass( "active" );
						hndl_gnbSub( null );
					}
				});
				$( this ).click(function($e){
					setGnbIdx_1d( parseInt( $( this ).attr( "data-role-idx" ) ) );
					setGnbIdx_2d( 0 );
					setGnbIdx_3d( 0 );
					hndl_gnbSub(null);
				});
			});
			function hndl_gnbSub($idx) {
				$( "nav > ul > li" ).each( function() {
					var idx = parseInt( $( this ).find( "> a" ).attr( "data-role-idx" ) );
					if( $idx == idx ) {
						$( this ).find( "> .gnb_sub" ).stop().css("display", "block").animate( {"margin-top" : 0, "opacity" : 1}, 120, "easeInOutQuad" );
					} else {
						$( this ).find( "> .gnb_sub" ).stop().animate( {"margin-top" : -15, "opacity" : 0}, 120, "easeInOutQuad", function(){
							$( this ).css( "display", "none" );
						});
					}
				});
			}

			$( "nav > ul > li > .gnb_sub > ul > li > a" ).each(function($idx){
				$( this ).click(function($e){
					var idx = parseInt( $( this ).attr( "data-role-idx" ) );
					setGnbIdx_1d( $( this ).parent().parent().parent().parent().find( "> a" ).attr( "data-role-idx" ) );
					setGnbIdx_2d( idx );
					setGnbIdx_3d( 0 );
					hndl_gnbSub(null);
				});
			});

			$( ".ui_btn_setting" ).click(function($e){
				setGnbIdx_1d( parseInt( $( this ).attr( "data-role-idx" ) ) );
				setGnbIdx_2d( 0 );
				setGnbIdx_3d( 0 );
			});
		}

		// SNB
		if( $( "#aside" ).length > 0 ) {
			$( "#aside > .snb > .snb_box > ul > li > a" ).click(function($e) {
				setGnbIdx_2d( parseInt( $( this ).attr( "data-role-idx" ) ) );
				setGnbIdx_3d( 0 );
			});
			$( "#aside > .snb > .snb_box > ul > li > ul > li > a" ).click(function($e) {
				setGnbIdx_2d( parseInt( $( this ).parent().parent().parent().find( "> a" ).attr( "data-role-idx" ) ) );
				setGnbIdx_3d( parseInt( $( this ).attr( "data-role-idx" ) ) );
			});
		}

		// Tab Content
		if( $( ".ui_tab_00" ).length > 0 ) {
			$( ".ui_tab_00 > ul > li > a" ).each( function($idx){
				if( $( this ).hasClass("active") ) hndl_tabContents($idx);
				$( this ).click( function($e){
					$e.preventDefault();
					hndl_tabContents($idx);
				});
			});
		}
		function hndl_tabContents($idx) {
			$( ".ui_tab_00 > ul > li > a" ).removeClass( "active" );
			$( ".ui_tab_00 > ul > li > a" ).eq($idx).addClass( "active" );
			$( ".ui_tab_00 > ul > li > .tab_content" ).hide();
			$( ".ui_tab_00 > ul > li > .tab_content" ).eq($idx).show();
		}

		// 검색창 Toggle
		$( ".ui_searchbox_toggle .btn_toggle" ).each( function(){
			var hgt;
			if( !$( this ).hasClass("active") ) {
				hgt = $( ".ui_searchbox_00 .c_wrap" ).outerHeight();
				$( ".ui_searchbox_00 .c_sort" ).css( { "top" : -$( ".ui_searchbox_00 .c_sort" ).outerHeight() } );
			} else {
				hgt = $( ".ui_searchbox_00 .c_wrap" ).outerHeight() + $( ".ui_searchbox_00 .c_sort" ).outerHeight() + 10;
				$( ".ui_searchbox_00 .c_sort" ).css( { "top" : $( ".ui_searchbox_00 .c_wrap" ).outerHeight() + 5 } );
			}
			$( ".ui_searchbox_00" ).css( { "height" : hgt } );

			$( this ).click( function($e){
				if( parseInt( $( ".ui_searchbox_00 .c_sort" ).css( "top" ) ) > 0 ) {
					$( this ).removeClass( "active" );
					hgt = $( ".ui_searchbox_00 .c_wrap" ).outerHeight();
					$( ".ui_searchbox_00 .c_sort" ).animate( { "top" : -$( ".ui_searchbox_00 .c_sort" ).outerHeight() }, { duration : 300, easing : "easeInOutExpo", step : hndl_frContentAutoHgt } );
				} else {
					$( this ).addClass( "active" );
					hgt = $( ".ui_searchbox_00 .c_wrap" ).outerHeight() + $( ".ui_searchbox_00 .c_sort" ).outerHeight() + 10;
					$( ".ui_searchbox_00 .c_sort" ).animate( { "top" : $( ".ui_searchbox_00 .c_wrap" ).outerHeight() + 5 }, { duration : 300, easing : "easeInOutExpo", step : hndl_frContentAutoHgt } );
				}
				$( ".ui_searchbox_00" ).animate( { "height" : hgt }, 300, "easeInOutExpo" );
			});

			function hndl_frContentAutoHgt() {
				var bodyDoc = $( $( window )[ 0 ].parent.document ).find( ".ui_con_frame_00" );
				var contentHgt = bodyDoc.contents().find( "body > *" ).outerHeight();
				bodyDoc.height( contentHgt );
				
			}
		});

		hndl_asideFrameHgt();
	});

	function hndl_nav_1d() {
		$( "nav > ul > li > a" ).removeClass( "active" );
		$( "nav > ul > li > a" ).each( function(){
			if( parseInt( $( this ).attr( "data-role-idx" ) ) == selGnbIdx_1d ) $( this ).addClass( "active" );
		});
		$( "#aside > .snb" ).hide();
		$( "#aside > .snb" ).each( function(){
			if( parseInt( $( this ).attr( "data-role-idx" ) ) == selGnbIdx_1d ) $( this ).show();
		});
	}
	function hndl_nav_2d() {
		$( "nav > ul > li > .gnb_sub > ul > li > a" ).removeClass( "active" );
		$( "nav > ul > li > a[data-role-idx=" + selGnbIdx_1d + "]" ).parent().find( "> .gnb_sub > ul > li > a" ).each( function(){
			if( parseInt( $( this ).attr( "data-role-idx" ) ) == selGnbIdx_2d ) $( this ).addClass( "active" );
		});
		$( "#aside > .snb > .snb_box > ul > li > a" ).removeClass( "active" );

		$( "#aside > .snb[data-role-idx=" + selGnbIdx_1d + "]" ).find( "> .snb_box > ul > li > a" ).each( function(){
			if( parseInt( $( this ).attr( "data-role-idx" ) ) == selGnbIdx_2d ) $( this ).addClass( "active" );
		});

		// 정보검색 Iframe 컨트롤
		if( selGnbIdx_1d == 1 ) {
			hndl_asideFrameHgt();
		}
	}
	function hndl_nav_3d() {
		$( "#aside > .snb > .snb_box > ul > li > ul > li > a" ).removeClass( "active" );
		$( "#aside > .snb[data-role-idx=" + selGnbIdx_1d + "]" ).find( "> .snb_box > ul > li > a[data-role-idx=" + selGnbIdx_2d + "]" ).parent().find( "> ul > li > a" ).each( function(){
			if( parseInt( $( this ).attr( "data-role-idx" ) ) == selGnbIdx_3d ) $( this ).addClass( "active" );
		});
	}
	
	function hndl_asideFrameHgt() {
		$( "#aside .ui_treeviewer" ).each(function(){
			var tg = $( this ).parent();
			var hgt = $( window ).height() - ( tg.find( "h2" ).outerHeight() + ( tg.find(".snb_box li a" ).outerHeight() * tg.find( ".snb_box li a" ).length ) ) - $( "#header" ).outerHeight();
			$( this ).height( hgt );

			/*
			var tg = $( this ).parent().parent().parent().parent();
			var hgt = $( window ).height() - ( tg.find( "h2" ).outerHeight() + ( tg.find("li a" ).outerHeight() * tg.find( "li a" ).length ) ) - $( "#header" ).outerHeight() - 300;

			if( $( this ).parent().find( "a" ).hasClass( "active" ) ) {
				$( this ).show();
				$( this ).height( hgt );
			} else {
				$( this ).hide();
				$( this ).height(0);
			}
			*/
		});
	}

	$( window ).resize( evt_resize );
	function evt_resize($e) {
		hndl_asideFrameHgt();
	}


	
})(jQuery);

// 팝업 
function popupOn( $value ){
	$( "#popup > .bg" ).css( { "display" : "block" } );
	$( "." + $value ).css( { "display" : "block" } );
	$( "#popup" ).fadeIn( 200 );
	$( "." + $value ).css( { "margin-left" : -$( "." + $value ).outerWidth() / 2, "margin-top" : -$( "." + $value ).outerHeight() / 2 } );
	$( "." + $value ).css( {"top" : ( $( window ).height() / 2 ) + $( document ).scrollTop() + 30} );
	$( "." + $value ).animate( {"top" : $( "." + $value ).position().top - 30}, 200, "easeOutQuad" );
}
function popupClose(){
	$( "#popup" ).fadeOut( 200, function() {
		$( "#popup > div" ).each(function(){
			$( this ).css( { "display" : "none" } );
		});
	});
	$( "#popup > div" ).each(function(){
		$( this ).animate( {"top" : $( this ).position().top - 30}, 200, "easeInQuad" );
	});
}

function debug_log($value) {
	if( $( "#debugbox" ).length > 0 ) $( "#debugbox" ).append( "- " + $value + "<br>" );
	alert( $value );
}

function msgbox_on($str){
	var tg;
	if( $( this.document ).find( "#ui_msgbox" )[ 0 ] ) tg = $( this.document ).find( "#ui_msgbox" );
	else if( $( this.parent.document ).find( "#ui_msgbox" )[ 0 ] ) tg = $( this.parent.document ).find( "#ui_msgbox" );
	else if( $( this.parent.parent.document ).find( "#ui_msgbox" )[ 0 ] ) tg = $( this.parent.parent.document ).find( "#ui_msgbox" );
	if( tg ) {
		tg.find( ".strbox" ).html( $str );
		tg.fadeIn( 100 );
		tg.find( ".msgbox" ).css({ "margin-top" : -tg.find( ".msgbox" ).outerHeight() / 2, "margin-left" : -tg.find( ".msgbox" ).outerWidth() / 2 });
	}
}
function msgbox_close(){
	var tg;
	if( $( this.document ).find( "#ui_msgbox" )[ 0 ] ) tg = $( this.document ).find( "#ui_msgbox" );
	else if( $( this.parent.document ).find( "#ui_msgbox" )[ 0 ] ) tg = $( this.parent.document ).find( "#ui_msgbox" );
	else if( $( this.parent.parent.document ).find( "#ui_msgbox" )[ 0 ] ) tg = $( this.parent.parent.document ).find( "#ui_msgbox" );
	if( tg ) {
		tg.fadeOut( 50 );
	}
}