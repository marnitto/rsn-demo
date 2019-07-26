/*!
 *
 * @author: RSN R&D Team LHS(GUNI)
 *			h2dlhs@realsn.com
 *
 *
 **/


// 오버되는 자릿수 K/M으로 대체 및 3자리마다 콤마(,) 추가
Number.prototype.lengthLimitComma = function( $limit, $type ) {				// $limit : 오버되는 자릿수, $type : 0일경우 K / 1일경우 K,M
	if( $limit == null || $limit == undefined || $limit == "undefined" ) $limit = 3;
	if( $type == null || $type == undefined || $type == "undefined" ) $type = 0;
	var result;
	var txtNumber = "" + this;
	if( txtNumber.length > $limit ) {
		if( $type == 1 ){
			if( txtNumber.length > 9 ) {
				result = this.lengthLimit( 6 ).addComma() + "M";
			} else if( txtNumber.length > 6 ) {
				result = this.lengthLimit( 3 ).addComma() + "K";
			}
		} else {
			result = this.lengthLimit( 3 ).addComma() + "K";
		}
	} else {
		result = this.addComma();
	}
	return result;
}

// 오버되는 자릿수 제한
Number.prototype.lengthLimit = function( $limit ) {							
	var txtNum = '' + this;
	var result = txtNum;
	if( txtNum.length > $limit ){
		result = txtNum.substr( 0, txtNum.length - $limit );
	}
	return parseInt( result );
}