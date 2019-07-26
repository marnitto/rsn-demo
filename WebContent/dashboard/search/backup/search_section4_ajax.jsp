<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="risk.util.ParseRequest" %>
<%@ page import="risk.util.StringUtil" %>
<%@ page import="risk.util.DateUtil" %>
<%@ page import="risk.dashboard.search.SearchMgr" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.net.URLEncoder" %>
<%
ParseRequest pr = new ParseRequest(request);
System.out.println("=================SECTION4 PARAMS START=================");
pr.printParams();
System.out.println("=================SECTION4 PARAMS END=================");

DateUtil du = new DateUtil();
StringUtil su = new StringUtil();

/*시간셋팅****************************************************************/
String eDate = pr.getString("eDate", du.getCurrentDate("yyyy-MM-dd"));
String sDate = pr.getString("sDate", du.addDay_v2(eDate, -6));
String sTime = pr.getString("sTime", "0");
String eTime = pr.getString("eTime", "24");
String setSTime = sTime.equals("24") ? "23:59:59" : (sTime.length() == 2 ? sTime+":00:00" : "0"+sTime+":00:00");
String setETime = eTime.equals("24") ? "23:59:59" : (eTime.length() == 2 ? eTime+":00:00" : "0"+eTime+":00:00");

String[] agoDate = du.getDayBefore("yyyy-MM-dd", sDate, eDate);

String replaceSDate = sDate.replaceAll("-", "");
String replaceEDate = eDate.replaceAll("-", "");
String agoSdate = agoDate[0].replaceAll("-", "");
String agoEdate = agoDate[1].replaceAll("-", "");
/*************************************************************************/

String channel = pr.getString("channelChkVal", "");
String searchKeyword = pr.getString("searchKeyword", "");
String pIdx = pr.getString("pIdx", "1");
int intPIdx = Integer.valueOf(pIdx);
int rowLimit = 10;

String searchType = pr.getString("searchType", ""); // 검색 방식 (1:구문, 2:인접, 3:AND)
String andSearchKeyword = pr.getString("andSearchKeyword", ""); // AND 키워드
String orSearchKeyword = pr.getString("orSearchKeyword", ""); // OR 키워드
String exSearchKeyword = pr.getString("exSearchKeyword", ""); // 제외 키워드
// String searchKeywordLength = pr.getString("searchKeywordLength", "0"); // 글자수

SearchMgr sMgr = new SearchMgr();
List resultList = sMgr.selectSectionList("search", replaceSDate, replaceEDate, channel, searchType, searchKeyword, andSearchKeyword, orSearchKeyword, exSearchKeyword, "", intPIdx, rowLimit, true);
System.out.println("resultList.size()=> " + resultList.size());
%>
<%!

%>
                            <div class="ui_board_list_00">
                                <table>
                                <caption>관련정보 목록</caption>
                                <colgroup>
                                <col style="width:150px">
                                <col>
                                <col style="width:110px">
                                </colgroup>
                                <thead>
                                <tr>
                                <th scope="col"><span>출처</span></th>
                                <th scope="col"><span>메세지</span></th>
                                <th scope="col"><span>수집시간</span></th>
                                </tr>
                                </thead>
                                <tbody>
<%
int rows = 0;
int numFound = 0;
int totalPageCnt = 0;
int upCnt = 0;
if (resultList.size() < 1) {
%>
                                <tr>
                                <td colspan="3" class="no_data" style="height:330px"></td>
                                </tr>
<%
} else {
%>
<%
for (int i=0; i<resultList.size(); i++) {
    Map resultMap = (Map) resultList.get(i);
    rows = (Integer) resultMap.get("rows");
    numFound = (Integer) resultMap.get("numFound");
%>
                                <tr>
                                <td><strong><%=resultMap.get("i_sitename") %></strong></td>
                                <td class="title"><a href="http://hub.buzzms.co.kr?url=<%=java.net.URLEncoder.encode(resultMap.get("i_url").toString(), "UTF-8")%>" target="_blank"><%=resultMap.get("i_title") %></a></td>
                                <td><%=resultMap.get("i_MMdd_date") %></td>
                                </tr>
<%
}
if ((numFound % rowLimit) > 0) upCnt = 1; else upCnt = 0;
totalPageCnt = (numFound / rowLimit) + upCnt;
%>
                                </tbody>
                                </table>
                            </div>
                            
<%
	String result = "<strong><span class='ui_bullet_02'>-</span> 총 " + su.digitFormat(numFound, "###,###") + "건</strong>, "+su.digitFormat(pIdx, "###,###")+"/"+su.digitFormat(totalPageCnt, "###,###")+" pages";
%>

<script type="text/javascript">
	$("#pageVal").html("<%=result %>");
</script>
								
                            <div class="paginate">
<%
int startNo = 0;
startNo = ((((intPIdx - 1) / rowLimit)) * 10);
for (int i=1; i<=totalPageCnt; i++) {
%>
<%
	if (i == 1) {
%>
                                <a style="cursor: pointer;" onclick="javascript:movePage(event, 4, '<%=((i+startNo)-1) %>');return false;" class="page_prev <%if ((i+startNo) == 1) { %> disabled <%} %>">이전페이지</a>        <!-- 버튼 활성화시 disabled 클래스 제거 -->
<%
	}
%>
                                <a style="cursor: pointer;" onclick="javascript:movePage(event, 4, '<%=(i+startNo) %>');return false;" <%if ((i+startNo) == intPIdx) { %> class="active" <%} %>><%=(i+startNo) %></a>
<%
	if (i == 10) {
%>
                                <a href="#" style="cursor: pointer;" onclick="javascript:movePage(event, 4, '<%=((i+startNo)+1) %>');return false;" class="page_next ">다음페이지</a>        <!-- 버튼 활성화시 disabled 클래스 제거 -->
<%
    }
%>
<%
	if (i == 10) break;
}
%>
<%
}
%>
                            </div>