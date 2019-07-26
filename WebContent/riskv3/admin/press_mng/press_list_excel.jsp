<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp"%>
<%@ page import="	java.util.*
                 	,risk.admin.pressMng.pressMng
					,risk.util.*
					"%>
<%
	String seqList = "";
	ParseRequest pr = new ParseRequest(request);
	DateUtil du = new DateUtil();
	pressMng pMgr = new pressMng();
	pr.printParams();

	int rowCnt = 99999999;
	int iNowPage = pr.getInt("nowpage", 1);

	String searchKey = pr.getString("searchKey", "");
	int d_type = pr.getInt("d_type");
	String tw_type = pr.getString("tw_type", "");
	String d_seq = pr.getString("d_seq", "");

	/*****시간설정*****/
	//일자
	String sDate = pr.getString("sDate",
			du.addDay_v2(du.getCurrentDate(), -7));
	//String eDate = pr.getString("eDate", du.getCurrentDate());
	String wDate = pr.getString("wDate", sDate);
	
	String eDate = du.addDay_v2(wDate, 4);

	//시간
	String sTime = pr.getString("sTime", "00");
	String eTime = pr.getString("eTime", "24");

	String sTimeSet = sTime + ":00:00";
	String eTimeSet = "";
	if (eTime.equals("24")) {
		eTimeSet = "23:59:59";
	} else {
		eTimeSet = eTime + ":00:00";
	}
	/*****************/
	ArrayList arList = pMgr.getPressSameDataList(iNowPage, rowCnt,
			sDate, eDate, sTimeSet, eTimeSet, searchKey, d_type, tw_type, d_seq);

	
	response.setContentType("application/vnd.ms-excel; charset=EUC-KR") ;
    response.setHeader("Content-Disposition", "attachment;filename=press_"+ du.getCurrentDate("yyyyMMdd") +".xls");
    response.setHeader("Content-Description", "JSP Generated Data");
  
%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style>
<!--
body {
	scrollbar-face-color: #FFFFFF; 
	scrollbar-shadow-color:#B3B3B3; 
	scrollbar-highlight-color:#B3B3B3; 
	scrollbar-3dlight-color: #FFFFFF; 
	scrollbar-darkshadow-color: #EEEEEE; 
	scrollbar-track-color: #F6F6F6; 
	scrollbar-arrow-color: #8B9EA6;
}
-->
</style>
<body bgcolor="#FFFFFF" leftmargin="15" topmargin="5">

<table style="width:2000px; table-layout:fixed; text-align: center;" border="1" cellspacing="1" >
	<colgroup>
		<col width="200">
		<col width="200">
		<col width="200">
		<col width="200">
		<col width="200">
		<col width="200">
		<col width="200">
		<col width="200">
		<col width="200">
		<col width="200">
	</colgroup>
		<tr>
			<th>문서번호</th>
			<th>매체명</th>
			<th>제목</th>
			<th>url</th>
			<th>수집일자</th>
			<th>유사율</th>										
			<th>댓글 수</th>
			<th>TOP노출</th>
			<th>포털초기면 노출 섹션</th>
			<th>구분</th>
		</tr>
		
		<%
		Map map = null;
		String siteNm = "";
		if(	arList.size() > 0	){
			for(int i=0; i < arList.size(); i++){
				map = new HashMap();
				map = (HashMap)arList.get(i);
				if(d_type == 1){
					siteNm = map.get("d_board").toString();
				}else{
					siteNm = map.get("d_site").toString();
				}
				
				String top_site_name = "";
				String tmp[] = map.get("top_seqs").toString().split(",");
				
				if(tmp.length > 0){
					for(int s=0; s<tmp.length; s++){
						if (!"".equals(tmp[s]) ){
							switch( Integer.parseInt(tmp[s]) ){
							
							case  450 :  top_site_name += ",헤드라인(d)"; break;
							case  451 :  top_site_name += ",주요이슈(d)"; break;
							case  452 :  top_site_name += ",오늘의사진(d)"; break;
							case  455 :  top_site_name += ",많이본뉴스(d)"; break;
							case  2306747 :  top_site_name += ",댓글많은 뉴스(d)"; break;
							
							case  499 :  top_site_name += ",이 시각 주요뉴스(n)"; break;
							case  500 :  top_site_name += ",오늘의 핫뉴스(n)"; break;
							case  504 :  top_site_name += ",사회(n)"; break;
							case  505 :  top_site_name += ",정치(n)"; break;
							case  506 :  top_site_name += ",경제(n)"; break;
							case  507 :  top_site_name += ",IT/과학(n)"; break;
							case  508 :  top_site_name += ",생활/문화(n)"; break;
							case  509 :  top_site_name += ",세계(n)"; break;
							case  2306715 :  top_site_name += ",가장 많이본 뉴스(n)"; break;
							case  2306711 :  top_site_name += ",신문 헤드라인(n)"; break;
							default : top_site_name += "";
						}
						}
					}
				}
				
				if(!"".equals(top_site_name)){
					top_site_name = top_site_name.substring(1, top_site_name.length());
				}
				
				String yn="";
				if( "1".equals(map.get("d_top").toString()) ){
					yn="Y";	
				}else{
					yn="N";
				}
				
		%>
		
		<tr>
			<td style="text-align:center;" ><%=map.get("i_docid")%></td>
			<td style="text-align:center;" ><%=siteNm%></td>	
			<td style="text-align:left;" ><%=map.get("d_title")%></td>
			<td style="text-align:left;" ><%=map.get("d_url")%></td>
			<td style="text-align:center;" ><%=map.get("d_date")%></td>	
			<td style="text-align:center;" ><%=map.get("d_percent")%>%</td>
			<td style="text-align:center;" ><%=map.get("reply_count")%></td>
			<td style="text-align:center;" ><%=yn%></td>
			<td style="text-align:center;" ><%=top_site_name%></td>
			<td style="text-align:center;" ><%=map.get("d_type")%></td>
		</tr>
		<%
			}
		}
		%>
</table>
</body>
</html>