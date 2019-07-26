/*!
 *
 * @author: GUNI, h2dlhs@realsn.com
 **/
 
 
(function ($) {

	var rollerClass = function( $el, $options ) {

		
		var itemWid;
		var viewItemLen;
		var moveGap;

		var prevBtn;
		var nextBtn;
		var items;

		var isAnimationing = false;


		this.init = function () {
			viewItemLen = $options.viewItemLen;
			moveGap = $options.moveGap;

			items = $el.find( ".list > *" );
			if( items.length == 0 ){
				$el.find( ".container" ).addClass( "no_data" );
			}
			prevBtn = $options.prevBtn;
			nextBtn = $options.nextBtn;

			build();
		}

		

		// **********		Build		************************************************************************************************ //
		function build(){
			itemWid = items.eq( 0 ).outerWidth();

			if( items.length > viewItemLen && items.length < ( ( viewItemLen * 3 ) + 2 ) ){
				var dummyItems = [];
				var cnt = 0;
				var len = ( items.length * 2 );
				while( len < ( viewItemLen * 3 ) ) {
					if( len > ( viewItemLen * 3 ) ) break;
					len += items.length;
				}
				for( var Loop1 = 0 ; Loop1 < len ; ++Loop1 ){
					var item = items.eq( cnt ).clone();
					item.find( "input" ).attr( "id", item.find( "input" ).attr( "id" ) + "_" + Loop1 );
					item.find( "label" ).attr( "for", item.find( "input" ).attr( "id" ) );
					dummyItems[ Loop1 ] = item;

					cnt++;
					if( cnt >= items.length ) cnt = 0;
				}
				for( var Loop1 = 0 ; Loop1 < dummyItems.length ; ++Loop1 ){
					$el.find( ".list" ).append( dummyItems[ Loop1 ] );
					items = $el.find( ".list > *" );
				}
			}

			items.each( function( $idx ) {
				$( this ).attr( "data-idx", $idx );
				$( this ).css( { position : "absolute", top : 0, left : parseInt( $( this ).attr( "data-idx" ) ) * itemWid } );
				if( $( this ).attr( "data-idx" ) >= ( ( viewItemLen * 2 ) + 1 )  ) {
					$( this ).attr( "data-idx", Math.abs( $( this ).attr( "data-idx" ) ) - ( items.length )  );
					$( this ).css( { left : parseInt( $( this ).attr( "data-idx" ) ) * itemWid } );
				}
			});

			if( items.length > viewItemLen ){
				prevBtn.click( evt_prev_click );
				nextBtn.click( evt_next_click );
			} else {
				prevBtn.addClass( "disabled" );
				nextBtn.addClass( "disabled" );
			}

			if( $options.complete ) $options.complete();
		}





		// **********		Hndler		************************************************************************************************ //
		function hndl_items(){
			items.each( function( $idx ){
				isAnimationing = true;
				$( this ).stop().animate( { left : parseInt( $( this ).attr( "data-idx" ) ) * itemWid }, "400", "easeInOutExpo", function(){
					if( $( this ).attr( "data-idx" ) <= -( viewItemLen + 1 ) ) {
						$( this ).attr( "data-idx", items.length - Math.abs( $( this ).attr( "data-idx" ) ) );
						$( this ).css( { left : parseInt( $( this ).attr( "data-idx" ) ) * itemWid } );
					}
					if( $( this ).attr( "data-idx" ) >= ( ( viewItemLen * 2 ) + 1 )  ) {
						$( this ).attr( "data-idx", Math.abs( $( this ).attr( "data-idx" ) ) - ( items.length )  );
						$( this ).css( { left : parseInt( $( this ).attr( "data-idx" ) ) * itemWid } );
					}
					isAnimationing = false;
				});
			});
		}




		// **********		Event		************************************************************************************************ //
		function evt_prev_click( $e ){
			if( isAnimationing ) return false;
			for( var Loop1 = 0 ; Loop1 < moveGap ; ++Loop1 ){
				items.each( function( $idx ){
					$( this ).attr( "data-idx", parseInt( $( this ).attr( "data-idx" ) ) + 1 );
				});
				hndl_items();
			}
			return false;
		}
		function evt_next_click( $e ){
			if( isAnimationing ) return false;
			for( var Loop1 = 0 ; Loop1 < moveGap ; ++Loop1 ){
				items.each( function( $idx ){
					$( this ).attr( "data-idx", parseInt( $( this ).attr( "data-idx" ) ) - 1 );
				});
				hndl_items();
			}
			return false;
		}




		// **********		Out Method		************************************************************************************************ //
		/*this.reLoad = function( $value ){
			$options.jsonDataUrl = $value;
			dataLoad();
		}*/



	};

	$.fn.ui_roller = function ( $options ) {
		return this.each(function() {
			var roller = new rollerClass( $( this ), $options );
			$.data( this, 'ui_roller', roller );
			$( document ).ready( roller.init );
		});
	}
})(jQuery);
