/*!
 *
 * @author: GUNI, h2dlhs@realsn.com
 **/


(function ($) {

	var cloudClass = function( $el, $options ) {

		this.init = function () {
			$el.jQWCloud({
				words: $options.words,
				minFont: $options.minFont ? $options.minFont : 12,
				maxFont: $options.maxFont ? $options.maxFont : 60,
				fontOffset: 4,
				verticalEnabled: false,
				padding_left: 2,
				word_click: function(){
					$( this ).siblings().removeClass( "active" );
					//$( this ).addClass( "active" );

					if( $options.clickFunc ) $options.clickFunc();
				},
				afterCloudRender: function(){
					var left;
					var right;
					var top;
					var bottom;
					$el.find( "> *" ).each( function(){
						if( left ) {
							left = left > $( this ).position().left ? $( this ).position().left : left;
						} else {
							left = $( this ).position().left;
						}
						if( right ) {
							right = right < $( this ).position().left + $( this ).outerWidth() ? $( this ).position().left + $( this ).outerWidth() : right;
						} else {
							right = $( this ).position().left + $( this ).outerWidth();
						}
						if( top ) {
							top = top > $( this ).position().top ? $( this ).position().top : top;
						} else {
							top = $( this ).position().top;
						}
						if( bottom ) {
							bottom = bottom < $( this ).position().top + $( this ).outerHeight() ? $( this ).position().top + $( this ).outerHeight() : bottom;
						} else {
							bottom = $( this ).position().top + $( this ).outerHeight();
						}
					});

					var r_wid = right - left;
					var r_hgt = bottom - top;
					var scale = ( $el.outerWidth() / r_wid ) - 0.05;

					if( ( r_hgt * scale ) > $el.outerHeight() ) {
						scale = ( $el.outerHeight() / r_hgt ) - 0.1;
					}

					$el.css( "transform", "scale(" + scale + ")" );
				}
			});
		}

		// **********		Out Method		************************************************************************************************ //
		// Active Disable
		this.activeDisable = function(){
			$el.find( "> *" ).removeClass( "active" );
		}
	};

	$.fn.jqw_cloud = function ( $options ) {
		return this.each(function() {
			var cloud = new cloudClass( $( this ), $options );
			$.data( this, 'jqw_cloud', cloud );
			$( document ).ready( cloud.init );
		});
	}
})(jQuery);
