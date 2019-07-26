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
    String md_seq = pr.getString("md_seq");
    String md_pseq = pr.getString("md_pseq");
    String sKeyword = pr.getString("sKeyword", "");
    
    //String idxMode = pr.getString("idxMode");
    //String md_seq = pr.getString("md_seq","");
    //String md_seqs = pr.getString("SaveList");
    
    
    String m_seq = SS_M_NO;
    String comment = "";
   	pr.printParams();        

    String StrScript = "";
    
    //System.out.println("md_seq, md_pseq : "+md_seq+" : "+md_pseq);
    smgr.alterList(md_seq, md_pseq);
%>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<script>
<!--

        parent.document.location = "search_main_contents.jsp?nowpage=<%=nowpage%>&sKeyword=<%=sKeyword%>";
  	

-->
</script>
</body>
</html>