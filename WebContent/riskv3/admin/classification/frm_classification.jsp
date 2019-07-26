<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@include file="../../inc/sessioncheck.jsp" %>
<%@ page import="risk.admin.classification.classificationMgr,
                 risk.util.DateUtil,
                 risk.util.ParseRequest" 
%>
<%

    DateUtil        du = new DateUtil();
    ParseRequest    pr = new ParseRequest(request);
    
    classificationMgr cm = new classificationMgr();
	
	int itype = pr.getInt("itype",0);
	int icode = pr.getInt("icode",0);

	
	String sCurrentDate = du.getCurrentDate("yyyyMMdd");
	//System.out.println("sCurrentDate : " + sCurrentDate );
	
	String sDateFrom = pr.getString("sDateFrom",sCurrentDate );
	String sDateTo   = pr.getString("sDateFrom",sCurrentDate );
	
	//kg.setSelected(xp,yp,zp);
	
	//kg.setBaseTarget("parent.keyword_right");
	//kg.setBaseURL("admin_keyword_right.jsp?mod=");
	
	cm.setBaseTarget("parent.frm_detail");
	cm.setBaseURL("frm_classification_detail.jsp?mod=");
	cm.setSelected( itype, icode );
	
	String cmHtml   = cm.GetHtml( itype,icode );
	//String kgScript = kg.GetScript();
	//String kgStyle  = kg.GetStyle();
	
	//System.out.println("itype = "+itype);
	//System.out.println("icode = "+icode);
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>
body {
	scrollbar-face-color: #FFFFFF; 
	scrollbar-shadow-color:#B3B3B3; 
	scrollbar-highlight-color:#B3B3B3; 
	scrollbar-3dlight-color: #FFFFFF; 
	scrollbar-darkshadow-color: #EEEEEE; 
	scrollbar-track-color: #F6F6F6; 
	scrollbar-arrow-color: #8B9EA6;
}
.kgmenu { font-size:12px; height:18px; color:#333333; padding:3 3 1 3; cursor:hand; border:1px solid #FFFFFF; }
.kgmenu_cnt { FONT-SIZE: 10px; COLOR: navy; FONT-FAMILY: "tahoma"; padding:0 0 0 3 }
.kgmenu_selected { font-size:12px; height:18px; color:#333333; padding:3 3 1 3; border:1px solid #999999; background-color:#F3F3F3; cursor:hand;}
.kgmenu_over { font-size:12px; height:18px; color:#333333; padding:3 3 1 3; cursor:hand; border:1px solid #999999; }
.kgmenu_img { cursor:hand; }
</style>
<script type="text/javascript">		

function saveFlag(){
	alert(1);
	$("[name=iflag]").each(function(){
		$(this).val();
	});
}

	function toggleme(object, subo) {
		var obj = document.getElementById(object);	
		var subObj = eval("document.all."+subo);		
		var i = 0;										
		var subcnt = subObj.length;						
		if (obj.src.indexOf('ico03')>0) {				
			if (subObj)	{								
				obj.src = '../../../images/search/left_ico04.gif';		
				subObj.style.display='';				
			}											
		} else {											
			if (subObj)	{								
				obj.src = '../../../images/search/left_ico03.gif';		
				subObj.style.display='none';			
			}											
		}												
	}													
	function kg_over(obj) {							
		tmpspan.className=obj.className;				
		obj.className='kgmenu_over';					
	}													
	function kg_out(obj){								
		obj.className=tmpspan.className;				
	}													
	function kg_click(obj){							
		var prvObj = eval('document.all.'+document.all.kgmenu_id.value);	
		if (prvObj)										
		{												
			prvObj.className = 'kgmenu';				
		}												
		document.all.kgmenu_id.value = obj.id;			
		obj.className='kgmenu_selected';				
		tmpspan.className=obj.className;				
	}
	function clk_total()
	{
		parent.keyword_right.location.href = 'admin_keyword_right.jsp';
	}
</script>
</head>
<body bgcolor="#FFFFFF" leftmargin="15" topmargin="5">
<%=cmHtml%>
</body>
</html>
