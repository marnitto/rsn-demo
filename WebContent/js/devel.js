
/**
 * 테이블의 모든 열을 검사하여 같은 항목끼리 병합
 */
$.fn.rowspan = function(colIdx, isStats, limit) {
    return this.each(function(){        
        var that;       
        $('tr', this).each(function(row) {        
            $('td',this).eq(colIdx).filter(':visible').each(function(col) {  
                  
            	if(colIdx < limit){
            		if ($(this).html() == $(that).html() && (!isStats || isStats && $(this).prev().html() == $(that).prev().html() ) ) {
            			rowspan = $(that).attr("rowspan") || 1;  
                        rowspan = Number(rowspan)+1;  
      
                        $(that).attr("rowspan",rowspan);  
                          
                        // do your action for the colspan cell here              
                        //$(this).hide();  
                          
                        $(this).remove();   
                        // do your action for the old cell here  
                          
                    } else {              
                        that = this;     
                    }            
                      
                    // set the that if not already set  
                    that = (that == null) ? this : that;     
            	}
                   
            });       
        });      
    });    
}; 

/**
 * 테이블의 모든 열을 검사하여 같은 항목끼리 병합 앞에꺼 비교하는 로직 제거 오직 단일 열만 검사
 */
$.fn.rowspan2 = function(colIdx, limit) {
    return this.each(function(){        
        var that;       
        $('tr', this).each(function(row) {        
            $('td',this).eq(colIdx).filter(':visible').each(function(col) {  
                  
            	if(colIdx < limit){
            		if ($(this).html() == $(that).html()) {
            			rowspan = $(that).attr("rowspan") || 1;  
                        rowspan = Number(rowspan)+1;  
      
                        $(that).attr("rowspan",rowspan);  
                          
                        // do your action for the colspan cell here              
                        $(this).hide();  
                          
                        //$(this).remove();   
                        // do your action for the old cell here  
                          
                    } else {              
                        that = this;     
                    }            
                      
                    // set the that if not already set  
                    that = (that == null) ? this : that;     
            	}
                   
            });       
        });      
    });    
}; 

/**
 * 테이블의 모든 행을 검사하여 같은 항목끼리 병합
 */
$.fn.colspan = function(rowIdx) {  
    return this.each(function(){  
          
 var that;  
  $('tr', this).filter(":eq("+rowIdx+")").each(function(row) {  
  $(this).find('td').filter(':visible').each(function(col) {  
      if ($(this).html() == $(that).html()) {  
        colspan = $(that).attr("colSpan");  
        if (colspan == undefined) {  
          $(that).attr("colSpan",1);  
          colspan = $(that).attr("colSpan");  
        }  
        colspan = Number(colspan)+1;  
        $(that).attr("colSpan",colspan);  
        $(this).hide(); // .remove();  
      } else {  
        that = this;  
      }  
      that = (that == null) ? this : that; // set the that if not already set  
  });  
 });  
  
 });  
} 


/**
 * 천단위 마다 콤마
 * @param x : 숫자
 * @returns
 */
function Commas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

