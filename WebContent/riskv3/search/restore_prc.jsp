<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList
                 ,risk.util.StringUtil
                 ,risk.util.ParseRequest
				 ,risk.util.DateUtil
				 ,risk.search.MetaMgr
				 ,risk.search.MetaBean
				 ,risk.search.userEnvInfo
				 ,java.net.URLEncoder
				 "
%>
<%@include file="../inc/sessioncheck.jsp" %>
<%
	//페이지에 사용할 변수 선언 부분
    StringUtil      su = new StringUtil();
    ParseRequest    pr = new ParseRequest(request);
    MetaMgr     smgr = new MetaMgr();
    DateUtil 	    du = new DateUtil();
    String m_seq = SS_M_NO;
    
    pr.printParams();
    
  	//넘기기 파라미터
    String nowpage = pr.getString("nowpage");
    String sKeyword = URLEncoder.encode(pr.getString("sKeyword"),"utf-8");
    String xp = pr.getString("xp");
    String yp = pr.getString("yp");
    String zp = pr.getString("zp");
    String type = pr.getString("type");
    String sg_seq = URLEncoder.encode(pr.getString("sg_seq"),"utf-8");
    String sDateFrom = URLEncoder.encode(pr.getString("sDateFrom"),"utf-8");
    String sDateTo = URLEncoder.encode(pr.getString("sDateTo"),"utf-8");
    String stime = pr.getString("stime");
    String etime = pr.getString("etime");
    String searchmode = URLEncoder.encode(pr.getString("searchmode"),"utf-8");
    
    String parm = "nowpage=" + nowpage
    			+ "&sKeyword=" + sKeyword
    			+ "&xp=" + xp
    			+ "&yp=" + yp
    			+ "&zp=" + zp
    			+ "&type=" + type
    			+ "&sg_seq=" + sg_seq
    			+ "&sDateFrom=" + sDateFrom
    			+ "&sDateTo=" + sDateTo
    			+ "&stime=" + stime
    			+ "&etime=" + etime
    			+ "&searchmode=" + searchmode;    
    
   	
   	System.out.println(parm);
   	
   	
   	String mode = pr.getString("mode");
   	
   	if(mode.equals("insert")){
   		
   		String md_seq = pr.getString("md_seq","");
   		smgr.restoreProcess(md_seq);
   		
   	}else if(mode.equals("multi")){
   		
   		String md_seqs = pr.getString("md_seqs","");
   		String[] ar_md_seq = md_seqs.split(",");
   		for(int i =0; i < ar_md_seq.length; i++){
   			smgr.restoreProcess(ar_md_seq[i]);	
   		}
   	}
   	
%>


<%@page import="javax.sound.sampled.AudioFormat.Encoding"%><html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<script>
<!--
    
	parent.document.location = "search_main_exception.jsp?<%=parm%>";
    
-->
</script>
</body>
</html>