//********************************
//  formutil
//
//  작성자 : 임승철
//  작성일 : 2010.09.28
//********************************


function formutil(){};

formutil.version = "version 1.0";

var $jq = jQuery.noConflict();

/**
* 기능: input값을 체크
* @param formId: 폼아이디
* input 태그 속성이 required가존재할때만 값을체크하고 해당 field명으로 메세지를 리턴..
*/

formutil.inputCheck(formId)
{
	var cursor  = null;
	var fields = '';	
		
	$jq('input:not(.required)').each(function(){
		if($jq.trim($jq(this).val())=='')
		{															
			$jq(this).css('background-color', '#ff7');
			//$jq(this).parent().find('span.error-message').remove();			
			//alert($jq(this).attr('field')+'를 입력해주세요.');				
			//$jq('<span></span>').addClass('error-message').text($jq(this).attr('field')+'를 입력해주세요.').appendTo($jq(this).parent());				
			fields += fields == '' ? $jq(this).attr('field') : ', '+$jq(this).attr('field');
			if(cursor==null)
			{
				cursor = $jq(this);
			}					
		}
	});	
		
	if(cursor==null)
	{
		return true;
	}else{
		alert(fields+'를 입력해주세요.');
		cursor.focus();
		return false;
	}	
}

