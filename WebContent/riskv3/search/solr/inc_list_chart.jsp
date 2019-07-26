<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList
                 ,risk.util.ParseRequest
                 ,risk.search.solr.SolrSearch"
%>
<%@include file="../../inc/sessioncheck.jsp" %>
<%@page import="java.net.URLDecoder"%>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	//String reSearchKey = new String(request.getParameter("reSearchKey").getBytes("iso-8859-1"), "UTF-8");
	String reSearchKey = pr.getString("reSearchKey", "");
	String xml = "";	
	String startDate  = pr.getString("startDate", "");
	String endDate = pr.getString("endDate", "");
	String sgroup = pr.getString("sgroup", "");
	String searchDate = pr.getString("searchDate");
	String type=pr.getString("type");
	SolrSearch solr = new SolrSearch();
	
	
	System.out.println("#########################################");
	System.out.println(reSearchKey);
	System.out.println("#########################################");
	
	
	if(sgroup.length() > 0){
		sgroup += " ";
	}
	
	//차트 XML가져오기
	xml = solr.getChartXml(reSearchKey, startDate, endDate, searchDate, sgroup, SS_M_ID);
	
	System.out.println("#########################################");
	System.out.println("##차트XML 가져오기 완료##");
	System.out.println("#########################################");
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<link rel="stylesheet" href="css/basic.css" type="text/css">
<style type="text/css">
.selectBox1 {behavior:url('./selectbox.htc'); }
.line_change01 {  border:1px solid #FFFFFF; background-color:#FFFFFF;}
.line_change02{  border:1px solid #E4E4E4; background-color:#F7F7F7;}
</style>
<script language="JavaScript" src="chart/js/FusionCharts.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--

function responseXml()
{
	var f = document.fSolr;
	var ChartXml = f.xml.value;
	
	if(ChartXml != ""){
		var chart = new FusionCharts("chart/charts/Column2D.swf", "ChartId", "800", "230");
		chart.setDataXML(ChartXml);
		chart.render("chartdiv");
		 
	}else{	
		var chart2 = document.getElementById("chartdiv");//첫로딩이미지
		chart2.innerHTML = "<img id=\"loadingBar\" src=\"images/search/no_data.gif\" width=\"115\" height=\"92\">";
	}
}

function chartView(check){
	var f = document.fSolr;
	var Obj = document.getElementById("chartview");
	 
	if(check){
		Obj.style.position = '';
		Obj.style.display = '';
		f.grfCheck.value = 1;	
	}else{
		Obj.style.position = 'absolute';
		Obj.style.display = 'none';
		f.grfCheck.value = 0;	
	}
}   



//-->
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >

<!-- 기사 분류 메뉴  -->

<form name="fSolr" action ="search_main_contents_solr.jsp" method="post">
<input type="hidden" name="nowpage" value ="1">
<input type="hidden" name="xml" value="<%=xml%>">
<table width="800" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td style="padding:0px 0px 0px 0px" align="left" valign="top">
		<table width="800" border="0" cellspacing="0" cellpadding="0">
        	<tr>
        		<td width="800" valign="top">
        		<div id="chartBox" >
				<table width="800" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<div id="chartview" >
							<table width="800" height="205" border="0" cellspacing="0" cellpadding="0">
				               	<tr>
				               		<td></td>
				              	</tr>
				               	<tr>
				               		<td align="center" style="padding:0px 0px 0px 0px">
				               			<div id="chartdiv" align="center">
				               			
				               			</div>
				               		</td>
				              		</tr>
				               	<tr>
				               		<td></td>
				              	</tr>
				               </table>
				              </div>
				        </td>
					</tr>
					<tr>
						<td></td>
					</tr>
				</table>
				</div>	
        		</td>

       		</tr>
       	</table></td>
	</tr>
</table>
</form>
</body>
</html>
<script>
responseXml();
</script>