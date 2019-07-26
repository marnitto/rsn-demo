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
System.out.println("=================SECTION3 PARAMS START=================");
pr.printParams();
System.out.println("=================SECTION3 PARAMS END=================");

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
String encodeSearchKeyword = URLEncoder.encode((String) searchKeyword, "UTF-8");
String pIdx = pr.getString("pIdx", "1");
int intPIdx = Integer.valueOf(pIdx);
int rowLimit = 100;

String searchType = pr.getString("searchType", ""); // 검색 방식 (1:구문, 2:인접, 3:AND)
String andSearchKeyword = pr.getString("andSearchKeyword", ""); // AND 키워드
String orSearchKeyword = pr.getString("orSearchKeyword", ""); // OR 키워드
String exSearchKeyword = pr.getString("exSearchKeyword", ""); // 제외 키워드
// String searchKeywordLength = pr.getString("searchKeywordLength", "0"); // 글자수

SearchMgr sMgr = new SearchMgr();
List resultList = sMgr.selectSectionList("relationword", replaceSDate, replaceEDate, channel, searchType, searchKeyword, andSearchKeyword, orSearchKeyword, exSearchKeyword, "", 0, rowLimit, true);
System.out.println("resultList.size()=> " + resultList.size());
%>
<%!

%>
<%
String words = "[";
for (int i=0; i<resultList.size(); i++) {
    Map resultMap = (Map) resultList.get(i);
    
    String params = "";
    params += "sDate=" + sDate + "&eDate=" + eDate;
    params += "&channel=" + channel.replaceAll(" ", "");
    params += "&searchType=" + searchType;
    params += "&encodeSearchKeyword=" + encodeSearchKeyword;
    params += "&andSearchKeyword=" + andSearchKeyword;
    params += "&orSearchKeyword=" + orSearchKeyword;
    params += "&word_nm=" + URLEncoder.encode((String) resultMap.get("word_nm"), "UTF-8");
    params += "&exSearchKeyword=" + exSearchKeyword;
    params += "&notEncode=Y";
//     params += "&lucyKeyword=" + URLEncoder.encode((String) searchKeyword, "UTF-8");
//     params += "&andSearchKeyword=" + URLEncoder.encode((String) andSearchKeyword, "UTF-8");
//     params += "&orSearchKeyword=" + URLEncoder.encode((String) orSearchKeyword, "UTF-8");
//     params += "&exSearchKeyword=" + URLEncoder.encode((String) exSearchKeyword, "UTF-8");
//     params += "&searchKeywordLength=" + searchKeywordLength;
    
    words += "{";
    words += "\"text\": \""+resultMap.get("word_nm")+"\", \"weight\": "+(resultList.size() - i)+", \"color\": \"#005cb7\", ";
    words += "\"link\": \"javascript:popupOpen('../popup/rel_info_lucyAPI.jsp?"+ params +"');\"";
    words += "}";
    
    if ((resultList.size() - (i+1)) > 0) words += ",";
}
words += "]";
%>
<%=words %>