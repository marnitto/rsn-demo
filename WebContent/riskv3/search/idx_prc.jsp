<%/*******************************************************
*  1. 분    류    명 : RSN
*  2. 업무 시스템 명 : 정보검색
*  3. 프로그램  개요 : 인덱스 삭제
*  4. 관 련  Table명 :
*  5. 작    성    자 :
*  6. 작    성    일 : 2006.4.13
*  7. 주  의  사  항 :
*  8. 변   경   자   :
*  9. 변 경  일 자   :
* 10. 변 경  사 유   :
********************************************************/%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList
                 ,risk.util.StringUtil
                 ,risk.util.ParseRequest
				 ,risk.util.DateUtil
				 ,risk.search.MetaMgr
				 ,risk.search.MetaBean
				 ,risk.search.userEnvInfo"
%>
<%@include file="../inc/sessioncheck.jsp" %>
<%
	//페이지에 사용할 변수 선언 부분
    StringUtil      su = new StringUtil();
    ParseRequest    pr = new ParseRequest(request);
    MetaMgr     smgr = new MetaMgr();
    DateUtil 	    du = new DateUtil();

    String nowpage = pr.getString("nowpage"); 
    String idxMode = pr.getString("idxMode");
    String md_seq = pr.getString("md_seq","");
    String md_seqs = pr.getString("SaveList");
    String st_name = pr.getString("st_name");
    String m_seq = SS_M_NO;
    String comment = "";
   	pr.printParams();        

    boolean bRtnValue1 = false;
    boolean bRtnValue = false;
    
    if(!md_seq.equals("")){
    	bRtnValue = smgr.idxProcess(idxMode,md_seq,m_seq,st_name);
    }else{
    	bRtnValue = smgr.idxProcess(idxMode,md_seqs,m_seq,st_name);
    }
    
    if(idxMode.equals("revert")){
    	comment = "복원 되었습니다";
    }else if(idxMode.equals("delAll")){
    	comment = "휴지통을 비웠습니다.";	
    }else{
	    if(bRtnValue){
			comment = "삭제 되었습니다";
		}else{
			comment = "삭제작업이 실패 하였습니다.";
		}  	
    }
%>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<script>
<!--
    //if ( '<%=bRtnValue%>' == 'true' ) {
        alert("<%=comment%>");
        //alert(top.bottomFrame.leftFrame.location);
  		//top.bottomFrame.leftFrame.location.reload();
  		var stname = '<%=st_name%>';
  		if(stname == ''){
  	  		parent.doSubmit();
  			//parent.document.location = "search_main_contents.jsp?nowpage=<%=nowpage%>";
  		}else{
  			parent.document.location = "search_main_exception.jsp?nowpage=<%=nowpage%>";
  		}
        
  	
    //} else {
    //    alert("삭제작업이 실패하였습니다.");
    //}
-->
</script>
</body>
</html>