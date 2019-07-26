/*!
 *
 * @author: GUNI, h2dlhs@realsn.com
 **/
 
 
(function ($) {

	var spiderClass = function( $el, $options ) {

		
		var containerW;
		var containerH;
		var positions;
		var keepCnt;
		var $items;
		var $lineArea;

		var bla;


		this.init = function () {
			$el.width( $el.parent().width() );
			$el.height( $el.parent().height() );
			containerW = $el.outerWidth();
			containerH = $el.outerHeight();

			$lineArea = $el.find( ".line_area" );
			$lineArea.width( containerW );
			$lineArea.height( containerH );

			reset();
		}

		function reset(){
			$items = $el.find( "> .item" ).not( ".all, .line_area" );

			build();
			buildLines();
			$el.find( "> *" ).fadeIn(200);
		}

		// **********		Build		************************************************************************************************ //
		function build(){
			var coords_1st = {
				x : ( containerW - $el.find( "> .all" ).outerWidth(true) ) / 2,
				y : ( containerH - $el.find( "> .all" ).outerHeight(true) ) / 2,
				w : $el.find( "> .all" ).outerWidth(true),
				h : $el.find( "> .all" ).outerHeight(true)
			};
			$el.find( "> .all" ).css( { top : coords_1st.y, left : coords_1st.x } );
			keepCnt = 0;
			positions = [];
			positions[ 0 ] = coords_1st;

			$items.each( function($idx){
				var coords = {
					w: $(this).outerWidth(true),
					h: $(this).outerHeight(true)
				};
				var success = false;
				var timeOutCnt = 0;
				while (!success) {

					var angle = Math.random() * Math.PI * 2;

					if( timeOutCnt < 50 ) {
						coords.x = ( Math.cos(angle) * ( $el.outerWidth() - 100 ) / 2 ) + coords_1st.x + 10;
						coords.y = ( Math.sin(angle) * ( $el.outerHeight() - 50 ) / 2 ) + coords_1st.y;
					} else {
						coords.x = ( Math.cos(angle) * 100 ) + coords_1st.x;
						coords.y = ( Math.sin(angle) * 70 ) + coords_1st.y;
					}


					var success = true;
					$.each(positions, function(){
						if (
							coords.x <= (this.x + this.w * 0.95) &&
							(coords.x + coords.w * 0.95) >= this.x &&
							coords.y <= (this.y + this.h * 0.95) &&
							(coords.y + coords.h * 0.95) >= this.y
						) {
							success = false;
						}
					});

					timeOutCnt++;
					if( timeOutCnt > 100 ) {
						if( keepCnt == 0 ) {
							coords.x = containerW - coords.w;
							coords.y = -12;
						} else if( keepCnt == 1 ) {
							coords.x = containerW - coords.w;
							coords.y =  containerH - coords.h + 12;
						} else if( keepCnt == 2 ) {
							coords.x = 0;
							coords.y =  containerH - coords.h + 12;
						} else {
							coords.x = 0;
							coords.y = -12;
						}
						keepCnt++;
						success = false;
						break;
					}

				}

				if( coords.x < 0 ) coords.x = 0;
				if( ( coords.x + $( this ).outerWidth() ) > containerW ) coords.x = containerW - $( this ).outerWidth();

				positions.push(coords);
				$(this).css({
					top: coords.y + 'px',
					left: coords.x + 'px'
				});
			});
		}

		function buildLines() {
			var line;
			$items.each(function(){
				var cx = containerW / 2;
				var cy = containerH / 2;
				var tx = parseInt( $( this ).css( "left" ) ) + parseInt( $( this ).outerWidth() / 2 );
				var ty = parseInt( $( this ).css( "top" ) ) + parseInt( $( this ).outerHeight() / 2 );
				var lineObj = $( "<div />" );
				$lineArea.append( lineObj );
				$.line({ x : cx, y : cy }, { x : tx, y : ty }, { elem : lineObj });

				//lineDraw( cx, cy, tx, ty );
			});
		}

		function lineDraw( x1, y1, x2, y2 ){
			if(y1 < y2){
				var pom = y1;
				y1 = y2;
				y2 = pom;
				pom = x1;
				x1 = x2;
				x2 = pom;
			}

			var a = Math.abs(x1-x2);
			var b = Math.abs(y1-y2);
			var c;
			var sx = (x1+x2)/2 ;
			var sy = (y1+y2)/2 ;
			var width = Math.sqrt(a*a + b*b ) ;
			var x = sx - width/2;
			var y = sy;

			a = width / 2;

			c = Math.abs(sx-x);

			b = Math.sqrt(Math.abs(x1-x)*Math.abs(x1-x)+Math.abs(y1-y)*Math.abs(y1-y) );

			var cosb = (b*b - a*a - c*c) / (2*a*c);
			var rad = Math.acos(cosb);
			var deg = (rad*180)/Math.PI;

			var bla = $( "<div />" );
			var rad2 = deg * ( Math.PI * 2 / 360 );
			var costheta = Math.cos(rad2);
			var sintheta = Math.sin(rad2);
			var styles = "position:absolute;top:"+y+"px;left:"+x+"px;width:"+width+"px;height:1px;background:#cbcbcb;transform:rotate("+deg+"deg);-ms-transform:rotate("+deg+"deg);-moz-transform:rotate("+deg+"deg);-webkit-transform:rotate("+deg+"deg);-o-transform:rotate("+deg+"deg);transform-origin:50% 50%;-moz-transform-origin:50% 50%;-webkit-transform-origin:50% 50%;-o-transform-origin:50% 50%;-ms-transform-origin:50% 50%;zoom:1;";
			styles += "-ms-filter:\"progid:DXImageTransform.Microsoft.Matrix(M11=" + costheta + ", M12=" + (-sintheta) + ", M21=" + sintheta +", M22=" + costheta +", SizingMethod='auto expand')\"";
			bla.attr( "style", styles );
			$lineArea.append(bla);

			if( ie8Chk ) {
				bla.css( "margin-top", -Math.abs( y1 - y2 ) / 2 );
				//bla.css( "left", parseInt( bla.position().left ) + 58 );
			}

			$( "#debug" ).append( "\r\n" + bla.width() );
			$( "#debug" ).append( "\r\n" + Math.abs( x1 - x2 ) );

			//bla.css( { "-ms-filter" : "progid:DXImageTransform.Microsoft.Matrix(M11=" + costheta + ", M12=" + (-sintheta) + ", M21=" + sintheta +", M22=" + costheta +",sizingMethod='auto expand')" } );

			//bla.css( { "-ms-filter" : "progid:DXImageTransform.Microsoft.Matrix(M11=1.4488887394336025, M12=-0.388228567653781, M21=0.388228567653781, M22=1.4488887394336025, SizingMethod='auto expand')" } );
		}




		// **********		Hndler		************************************************************************************************ //





		// **********		Event		************************************************************************************************ //





		// **********		Out Method		************************************************************************************************ //
		/*this.reLoad = function( $value ){
			$options.jsonDataUrl = $value;
			dataLoad();
		}*/



	};

	$.fn.ui_spider = function ( $options ) {
		return this.each(function() {
			var spider = new spiderClass( $( this ), $options );
			$.data( this, 'ui_spider', spider );
			$( document ).ready( spider.init );
		});
	}
})(jQuery);
