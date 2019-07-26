<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ include file="../inc/sessioncheck.jsp" %>    
<%@ page import="	java.util.ArrayList
					,risk.util.ParseRequest
					,risk.util.ConfigUtil
					,risk.util.StringUtil
					,risk.util.DateUtil 
					,risk.statistics.StatisticsBean
					,risk.statistics.StatisticsMgr
					,risk.admin.site.SiteBean
                 	" %>

<%
	DateUtil   du = new DateUtil();
	ParseRequest  pr = new ParseRequest(request);
	StatisticsMgr staticMgr = new StatisticsMgr();
	StringUtil su = new StringUtil();
	ArrayList arrStatic02 = new ArrayList();
	pr.printParams();
	
	
	String sDateFrom ="";
	String sDateTo ="";
	String sGroup = "";
	
	sDateFrom = pr.getString("sDateFrom");
	sDateTo = pr.getString("sDateTo");
	sGroup = pr.getString("sGroup");
		
	if(sDateFrom.length()<1){
		sDateFrom = du.getCurrentDate("yyyy-MM-dd");
		sDateTo = sDateFrom;
	}
	
	System.out.println("sGroup:"+sGroup);
	if(sGroup.length()>0){
		//arrStatic02 = staticMgr.getStatistics03(sDateFrom,sDateTo,sGroup);
	}

%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="../css/basic.css" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--
	sDateFrom = '<%=sDateFrom%>';
	sDateTo = '<%=sDateTo%>';
	
	function GoStatics(){
		SSend.action='static_site_collect.jsp';
		SSend.submit();
	}

	function pop_sitecollect_view( sDateFrom, sDateTo,type,sd_gsn ){
		var f = document.SSend;
		var view = window.open("pop_sitecollect_view.jsp?&sDateFrom="+sDateFrom+"&sDateTo="+sDateTo+"&searchmode="+type+"&sd_gsn="+sd_gsn,'pop_sitecollect_view', 'width=820,height=790,scrollbars=yes');
	}
	
//-->
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="SSend" action="" method="post">
  <table width="750" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td colspan="2" bgcolor="#7CA5DD"><img src="images2/brank.gif" width="1" height="2"></td>
  </tr>
  <tr>
    <td bgcolor="#CFCFCF" width="100%">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
      <tr>
        <td width="308" height="28" align="center" background="images2/statis_table_bg01.gif" bgcolor="#FFFFFF" style="padding: 3px 0px 0px 0px;"><strong>사이트명</strong></td>
        <td width="147" align="center" background="images2/statis_table_bg01.gif" bgcolor="#FFFFFF" style="padding: 3px 0px 0px 0px;"><strong>관심정보건수*</strong></td>
        <td width="148" align="center" background="images2/statis_table_bg01.gif" bgcolor="#FFFFFF" style="padding: 3px 0px 0px 0px;"><strong>전체수집건수</strong></td>
        <td width="142" align="center" background="images2/statis_table_bg01.gif" bgcolor="#FFFFFF" style="padding: 3px 0px 0px 0px;"><strong>관심정보비율</strong></td>
      </tr>
    </table></td>
  </tr>
  <tr>
   <td>
   <div id="aa" style="width:735;height:181;overflow-y:auto;">
   <table width="734" border="0" cellspacing="0" cellpadding="0">
<%
	int[] arrInt2 = new int[2];
	//String[] arrString;
	String avg2 = "";
	String Stotal2 = "";
	double total2 = 0;
	StatisticsBean staBean2;
	
	if(arrStatic02.size()>0)
	{
		for(int i=0; i<arrStatic02.size(); i++){
			staBean2 = (StatisticsBean)arrStatic02.get(i);	
			
			arrInt2[0] += staBean2.getFavor_ct();
			arrInt2[1] += staBean2.getTotal_ct();
			
			total2 += staBean2.getAvg();
			String[] arrString2 = (Double.toString(staBean2.getAvg())).split("[.]");
			
			avg2 = arrString2[0]+"."+arrString2[1].substring(0,1);
	%>
            <tr>
              <td width="310" height="25" align="center" style="padding: 3px 0px 0px 0px;"><a href="<%=staBean2.getS_url()%>" target="_blank"><u><nobr><%=staBean2.getS_name()%></nobr></u></a></td>
              <%if(staBean2.getSite_ct()==0){%>
              <td width="148" align="center" background="images2/collect_tline01.gif" style="padding: 3px 0px 0px 0px;"><%=su.digitFormat(staBean2.getFavor_ct())%></td>
              <%}else{%>
              <td width="148" align="center" background="images2/collect_tline01.gif" style="padding: 3px 0px 0px 0px;"><u><%=su.digitFormat(staBean2.getFavor_ct())%></u></td>
              <%}%>
              <%if(staBean2.getTotal_ct()==0){%>
              <td width="149" align="center" background="images2/collect_tline01.gif" style="padding: 3px 0px 0px 0px;"><%=su.digitFormat(staBean2.getTotal_ct())%></td>
              <%}else{%>
              <td width="149" align="center" background="images2/collect_tline01.gif" style="padding: 3px 0px 0px 0px;"><u><%=su.digitFormat(staBean2.getTotal_ct())%></u></td>
              <%}%>
              <td width="127" align="center" background="images2/collect_tline01.gif" style="padding: 3px 0px 0px 0px;"><%=avg2%>%</td>
            </tr>
	        <tr>
	          <td colspan="7" bgcolor="#CFCFCF"><img src="images2/brank.gif" width="1" height="1"></td>
	        </tr>
<%
		}
	}
		if(arrInt2[0]!=0){
			total2 = (Double.parseDouble(Integer.toString(arrInt2[0]))/arrInt2[1])*100;
		}
		String[] arrString2 = (Double.toString(total2)).split("[.]");
		Stotal2 = arrString2[0]+"."+arrString2[1].substring(0,1);
	%>        
            
            <tr>
              <td width=310 height="25" align="center" background="images2/statis_table_bg01.gif" bgcolor="#7CA5DD" style="padding: 3px 0px 0px 0px;"><b>계</b></td>
              <td width="148" align="center" background="images2/statis_table_bg01.gif" bgcolor="#7CA5DD" style="padding: 3px 0px 0px 0px;"><b><%=su.digitFormat(arrInt2[0])%></b></td>
              <td width="149" align="center" background="images2/statis_table_bg01.gif" bgcolor="#7CA5DD" style="padding: 3px 0px 0px 0px;"><b><%=su.digitFormat(arrInt2[1])%></b></td>
              <td width="127" align="center" background="images2/statis_table_bg01.gif" bgcolor="#7CA5DD" style="padding: 3px 0px 0px 0px;"><b><%=Stotal2%>%</b></td>
            </tr>
          </table>
          </div>
          </td>
          </tr>
          </table>
          </form>
</body>
</html>
