/*!
 *
 * @author: GUNI, h2dlhs@realsn.com
 **/
 
 
(function ($) {
	

     var selectorClass = function( $el, $options ) {
		
		
		var $items;
		var key_shift = false;
		var key_ctrl = false;
		var clickItem;
		var firstIdx;

		var arrAllItems = [];
		var arrSelectItems = [];
		var arrSelectIndex = [];
		var grouping = new Array();

		
		
		this.init = function () {
			$( document ).keydown( evt_keyDown );
			$( document ).keyup( evt_keyUp );

			builds();
			if( $options == undefined ) $options = {};

			// 정렬값이 있을경우 정렬
			if( $options.sort == "index" ) sort_index();
			else if( $options.sort == "txt" ) sort_txts();
        }
		
		
		// **********		Build		************************************************************************************************ //
		function builds(){
			arrAllItems = $el.find( "> li" );
			$items = $el.find( "> li > span" );
			$items.unbind( "click", evt_click );
			$items.click( evt_click );
		}
		
		
		
		
		
		// **********		Hndler		************************************************************************************************ //
		function hndl_itemSelect(){
			$items.parent().removeClass("selected");
			for( var Loop1 = 0 ; Loop1 < arrSelectIndex.length ; ++Loop1 ) {
				$items.eq(arrSelectIndex[ Loop1 ]).parent().addClass("selected");
			}
		}

		function itemPush( $idx ){
			if( $.inArray($idx, arrSelectIndex) == -1 ) arrSelectIndex.push( $idx );
		}
		
		
		
		
		
		// **********		Event		************************************************************************************************ //
		function evt_click( $e ) {
			if( !$( this ).parent().hasClass("selected") ) {
				if( key_ctrl ) {
					firstIdx = $( this ).parent().index();
					itemPush( $( this ).parent().index() );
				} else if( key_shift ) {
					if( arrSelectIndex.length == 0 ) firstIdx = $( this ).parent().index();
					var gap = $( this ).parent().index() - firstIdx;
					arrSelectIndex = [];
					for( var Loop1 = 0; Loop1 < Math.abs(gap); ++Loop1 ) {
						if( gap > 0 ) itemPush( firstIdx + Loop1 );
						else itemPush( firstIdx - Loop1 );
					}
					itemPush( $( this ).parent().index() );
				} else {
					firstIdx = $( this ).parent().index();
					arrSelectIndex = [];
					itemPush( $( this ).parent().index() );
				}
			} else {
				if( key_ctrl ) {
					firstIdx = $( this ).parent().index();
					arrSelectIndex.splice( $.inArray($( this ).parent().index(), arrSelectIndex), 1 );
				} else if( key_shift ) {
					var gap = $( this ).parent().index() - firstIdx;
					arrSelectIndex = [];
					for( var Loop1 = 0; Loop1 < Math.abs(gap); ++Loop1 ) {
						if( gap > 0 ) itemPush( firstIdx + Loop1 );
						else itemPush( firstIdx - Loop1 );
					}
					itemPush( $( this ).parent().index() );
				} else {
					firstIdx = $( this ).parent().index();
					arrSelectIndex = [];
					itemPush( $( this ).parent().index() );
				}
			}

			hndl_itemSelect();
		}
		function evt_keyDown( $e ) {
			if( $e.keyCode == 17 || $e.keyCode == 25 ) {
				if( key_ctrl ) return false;
				key_ctrl = true;
			}
			if( $e.keyCode == 16 ) {
				if( key_shift ) return false;
				key_shift = true;
			}
		}
		function evt_keyUp( $e ) {
			if( $e.keyCode == 17 || $e.keyCode == 25 ) key_ctrl = false;
			if( $e.keyCode == 16 ) key_shift = false;
		}

		function sort_index(){
			arrAllItems = $el.find( "> li" ).clone();
			arrAllItems.sort(function (a, b) {
				if (parseInt($( a ).attr("r_date")) > parseInt($( b ).attr("r_date"))) return -1;
				if (parseInt($( a ).attr("r_date")) < parseInt($( b ).attr("r_date"))) return 1;
				return 0;
			});
			$el.html("");
			for(var Loop1 = 0 ; Loop1 < arrAllItems.length ; ++Loop1 ) {
				$el.append( arrAllItems[ Loop1 ] );
			}
			hndl_arrIndexReset();
			builds();
		}
		function sort_txts(){
			var Loop1;
			arrAllItems = $el.find( "> li" ).clone();
			arrAllItems.sort(function (a, b) {
				if ($( a ).find("> span").html() > $( b ).find("> span").html()) return 1;
				if ($( a ).find("> span").html() < $( b ).find("> span").html()) return -1;
				return 0;
			});
			$el.html("");
			for( Loop1 = 0 ; Loop1 < arrAllItems.length ; ++Loop1 ) {
				$el.append( arrAllItems[ Loop1 ] );
			}
			hndl_arrIndexReset();
			builds();
		}
		function sort_group($attr, $idx){
			grouping[ $attr ] = $idx;
			var Loop1;
			var Loop2;
			var visibleChk = false;
			var test= [];
			for( var Loop1 = 0 ; Loop1 < $el.find( "> li" ).length ; ++Loop1 ) {
				$el.find( "> li" ).eq( Loop1 ).removeClass( "disable" );
				visibleChk = true;
				for (var attrName in grouping) {
					if( grouping[ attrName ] != "all" && grouping[ attrName ] != $el.find( "> li" ).eq( Loop1 ).attr( attrName ) ) {
						visibleChk = false;
					}
				}
				if( !visibleChk ) $el.find( "> li" ).eq( Loop1 ).addClass( "disable" );
			}
		}

		function hndl_arrIndexReset(){
			arrSelectIndex = [];
			for(var Loop1 = 0 ; Loop1 < $el.find( "> li.selected" ).length ; ++Loop1 ) {
				arrSelectIndex[ Loop1 ] = $el.find( "> li.selected" ).eq( Loop1 ).index();
			}
		}
		
		
		
		
		// **********		Outside Method		************************************************************************************************ //
		//  리셋(버튼 재정의)
		this.reset = function() {
			builds();
		}

		// 아이템 추가
		this.appendItems = function( $items ) {
			for( var Loop1 = 0 ; Loop1 < $items.length ; ++Loop1 ) {
				$items[ Loop1 ].removeClass( "selected" );
				$el.append( $items[ Loop1 ] );
			}
			builds();
		}

		// 선택된 아이템 삭제
		this.removeItems = function() {
			$el.find( "> li.selected" ).remove();
			arrSelectIndex = [];
			builds();
		}

		// 선택된 아이템 jQuery셀렉터 형태로 - Getter
		this.getSelectedItems = function() {
			arrSelectItems = [];
			for( var Loop1 = 0 ; Loop1 < $el.find( "> li.selected" ).length ; ++Loop1 ) {
				arrSelectItems[ Loop1 ] = $el.find( "> li.selected" ).eq( Loop1 ).clone();
			}
			return arrSelectItems;
		}

		// 선택된 아이템 Index값 배열로 - Getter
		this.getSelectedIndex = function() {
			return arrSelectIndex;
		}

		// 아이템 Index값으로 정렬
		this.sortIndex = function() {
			sort_index();
		}
		// 아이템 글자순으로 정렬
		this.sortTxts = function() {
			sort_txts();
		}

		// 아이템 그룹핑( 속성이름, 속성값 )
		this.sortGroup = function($attr, $idx){
			sort_group($attr, $idx);
		}

		// 선택된 아이템 위로 이동( 한칸씩 이동함 )
		this.moveUp = function(){
			var items = $el.find( "> li.selected" );
			if( items.length <= 0 ) {
				alert( "선택된 아이템이 없습니다." );
				return;
			}
			var firstIdx = items.eq( 0 ).index() - 1;
			if( firstIdx < 0 ) {
				firstIdx = 0;
				return;
			}
			$el.find( "> li" ).eq( firstIdx ).before( items );
			builds();
		}
		// 선택된 아이템 아래로 이동( 한칸씩 이동함 )
		this.moveDn = function(){
			var items = $el.find( "> li.selected" );
			if( items.length <= 0 ) {
				alert( "선택된 아이템이 없습니다." );
				return;
			}
			var lastIdx = items.eq( items.length - 1 ).index() + 1;
			if( lastIdx > $el.find( "> li" ).length - 1 ) {
				lastIdx = $el.find( "> li" ).length - 1;
				return;
			}
			$el.find( "> li" ).eq( lastIdx ).after( items );
			builds();
		}
    };
	
	$.fn.multi_selector = function ( $options ) {
		return this.each(function() {
			var selector = new selectorClass( $( this ), $options );
			$.data( this, 'multi_selector', selector );
			selector.init();
		});
    }
	

})(jQuery);