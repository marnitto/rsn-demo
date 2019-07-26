<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../riskv3/inc/sessioncheck.jsp" %>
<%@ page import="risk.util.ParseRequest
                 ,risk.util.DateUtil
                 ,java.util.*
                 ,risk.util.PageIndex
                 ,risk.util.StringUtil
                 ,risk.util.ConfigUtil
                 ,risk.dashboard.summary.SummaryMgr
                 ,java.net.*
                 "
%>

<%
	ParseRequest pr = new ParseRequest(request);
	DateUtil du = new DateUtil();
	StringUtil	su 	= new StringUtil();
	ConfigUtil cu = new ConfigUtil();
	SummaryMgr sMgr = new SummaryMgr();
	
	String p_date = pr.getString("p_date");
	String doc_id = pr.getString("doc_id");
	String r_trnd = pr.getString("r_trnd", "");
	String r_relation_word = pr.getString("r_relation_word", "");
	int totalCnt = pr.getInt("totalCnt",0);
	String excelParam = "";
	System.out.println("========================================");
	pr.printParams();
	
	int nowpage = Integer.parseInt( pr.getString("popPage","1") );
	int rowCnt = 10;
	int totalPage = 0;
	
	sMgr.getLucySwitch();
	
	ArrayList arData = new ArrayList();
	arData = sMgr.getReplyDataList(nowpage, rowCnt, p_date, doc_id, r_trnd, r_relation_word, SS_M_ID);
	excelParam = "&p_docid=" + doc_id + "&p_date=" + p_date + "&r_trend=" + r_trnd + "&r_relation_word=" + URLEncoder.encode(r_relation_word, "UTF-8");
	
	if(totalCnt == 0){
		totalCnt = sMgr.getReplyTotalCnt();
	}
	totalPage = totalCnt / rowCnt;
	if(totalCnt % rowCnt > 0){
		totalPage += 1;
	}
	
	String srtMsg = "총 건수 : "+su.digitFormat(totalCnt)+" 건, "+su.digitFormat(nowpage)+"/"+su.digitFormat(totalPage)+" pages";
%>
	<!-- Popup -->
	<div id="popup">
		<h2 class="invisible">팝업 컨텐츠</h2>
		<div class="bg"></div>
		<div class="container ui_shadow">
			<section style="width:700px">
				<div class="header">
					<h3>관련정보</h3>
					<a href="#" class="close" onclick="hndl_popupClose();return false;">팝업닫기</a>
				</div>
				<div class="content">
					<div class="ui_board_list_00">
						<div class="ui_board_header f_clear">
							<div class="fl"><strong><!-- <span class="ui_bullet_01">-</span> -->총 <%=su.digitFormat(totalCnt) %>건</strong>, <%=su.digitFormat(nowpage)+"/"+su.digitFormat(totalPage) %> pages</div>
							<div class="fr">
								<button type="button" class="ui_btn_02 ui_shadow_01" title="Excel Download" onclick="getExcel_POI(event, '../popup/excel/potal_reply_ExcelDao.jsp', '<%=excelParam%>', deleteExcelFile); return false;"><span class="icon excel">Excel Download</span></button>
								<!-- <button type="button" class="ui_btn_02 ui_shadow_01" title="Raw Data Download"><span class="icon dn">Raw Data Download</span></button> -->
							</div>
						</div>
						<table>
						<caption>관련정보 목록</caption>
						<colgroup>
						<col style="width:120px">
						<!-- <col style="width:100px"> -->
						<col>
						<col style="width:70px">
						</colgroup>
						<thead>
						<tr>
						<th scope="col"><span>날짜</span></th>
						<th scope="col"><span>내용</span></th>
						<th scope="col"><span>성향</span></th>
						</tr>
						</thead>
						<tbody>
						
							<%
   		
   	%>
   	
   	<%if( arData.size() > 0 ){ 
   		Map map = null;
   	%>
   	
   	<%
   		for(int i = 0; i < arData.size(); i++){
   			map = new HashMap();
   			map = (HashMap)arData.get(i);
   			String senti = "";
   			if( "1".equals( map.get("r_trend").toString()) ){
   				senti = "긍정";
   			}else if( "2".equals( map.get("r_trend").toString()) ){
   				senti = "부정";
   			}else if( "3".equals( map.get("r_trend").toString()) ){
   				senti = "중립";
   			}
   			
   	%>
							<tr>
							<td><%=map.get("r_datetime")%></td>
							<%-- <td><%=map.get("MD_SITE_NAME")%></td> --%>
							<td class="title"
							><%-- <a href="http://hub.buzzms.co.kr?url=<%=map.get("ID_URL")%>" target="_blank" > --%><%=map.get("r_content")%><!-- </a> --></td>
							<td><%=senti%></td>
							</tr>
	<%
	   		}
   	
   	if(arData.size() < 10){
		for(int k=arData.size(); k < 10; k++){
			%>
			<tr><td></td><td></td><td></td></tr>
			<%
		}
	}
		%>
		<%}else{ %>
							<tr>
								<td colspan="3"> 검색된 자료가 없습니다. </td>
							</tr>
		
		<%} %>		
						</tbody>
						</table>
					</div>
				<%
	/*** 페이징 처리 ***/
	String pageHtml = "<div class=\"paginate\">";
    
	int lastPage = totalPage;
	String strNowPage = Integer.toString(nowpage - 1); 
	String display = "";
	String AndDis = "";
	
	int ptPage = Integer.parseInt(strNowPage.substring(strNowPage.length() - 1 , strNowPage.length()));

	int startPage = (nowpage - 1) - ptPage;         // 0 : 10 : 20 ...       
	int endPage   = (nowpage - 1) + (10 - ptPage);  //10 : 20 : 30 ...
	if(endPage > lastPage) endPage = lastPage;
	
	//왼쪽화살표
	String href = "";
	page = "";
	if(startPage <= 0){
		display = "disabled";
		//href = "#";
	}else{
		href = "href=\"javascript:summary.getReplyPopUp('"+doc_id+"','"+p_date+"','"+totalCnt+"',"+startPage+",'"+r_trnd+"','"+r_relation_word+"')\"";
	}
	
	if(nowpage == 1){
		AndDis = "disabled";
	}else{
		page = "href=\"javascript:summary.getReplyPopUp('"+doc_id+"','"+p_date+"','"+totalCnt+"',"+1+",'"+r_trnd+"','"+r_relation_word+"')\"";
	}
	
	pageHtml += "<a "+page+" class=\"page_first "+AndDis+"\">첫 페이지</a>";
	pageHtml += "<a "+href+" class=\"page_prev "+display+"\">이전페이지</a>";
	
	if(startPage < endPage){
		//숫자
		do{
			startPage++;
			if(startPage == nowpage){
				pageHtml += "<a class=\"active\">"+ startPage +"</a>"; //강조
				
			}else{
				pageHtml += "<a href=\"javascript:summary.getReplyPopUp('"+doc_id+"','"+p_date+"','"+totalCnt+"',"+startPage+",'"+r_trnd+"','"+r_relation_word+"')\">"+startPage+"</a>"; //기본
				
			}
			
		}while(startPage != endPage);
	}
	
	//오른쪽화살표
	display = "";
	href = "";
	page = "";
	if(endPage >= lastPage){
		display = "disabled";
		//href = "#";
	}else{
		href = "href=\"javascript:summary.getReplyPopUp('"+doc_id+"','"+p_date+"','"+totalCnt+"',"+(endPage + 1)+",'"+r_trnd+"','"+r_relation_word+"')\"";
		page = "href=\"javascript:summary.getReplyPopUp('"+doc_id+"','"+p_date+"','"+totalCnt+"',"+totalPage+",'"+r_trnd+"','"+r_relation_word+"')\"";
	}
	pageHtml += "<a "+href+" class=\"page_next "+display+"\">다음페이지</a> ";
	pageHtml += "<a "+page+" class=\"page_last "+display+"\">다음페이지</a> ";
	
	pageHtml += "</div>";
	out.println(pageHtml);
%>						
					<!-- Loader -->
					<div class="ui_loader v0"><span class="loader">Load</span></div>
					<div class="ui_loader v1"><span class="loader"></span><p>saving...</p></div>
					<!-- // Loader -->
				</div>
			</section>
		</div>
	</div>
	<!-- // Popup -->
