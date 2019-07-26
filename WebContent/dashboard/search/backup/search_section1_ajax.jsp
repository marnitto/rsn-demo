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
System.out.println("=================SECTION1 PARAMS START=================");
pr.printParams();
System.out.println("=================SECTION1 PARAMS END=================");

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
List resultList = sMgr.selectSectionList("datetrend", replaceSDate, replaceEDate, channel, searchType, searchKeyword, andSearchKeyword, orSearchKeyword, exSearchKeyword, "", 0, rowLimit, false);
System.out.println("resultList.size()=> " + resultList.size());
%>
<%!

%>
                            <div id="chart_01" class="ui_chart <%if (resultList.size() == 0) { %>no_data<%} %>" style="height:300px"></div>
                            <script type="text/javascript">
                                (function(){
                                    var chartOption = {
                                        "type": "serial",
                                        "categoryField": "category",
                                        "columnWidth": 0.4,
                                        "marginBottom": 0,
                                        "marginTop": 10,
                                        "colors": [
                                            "#fdd035",
                                            "#7e0000"
                                        ],
                                        "addClassNames": true,
                                        "pathToImages": "../asset/img/amchart/",
                                        "percentPrecision": 0,
                                        "categoryAxis": {
                                            "gridPosition": "start",
                                            "twoLineMode": true,
                                            "axisColor": "#D8D8D8",
                                            "color": "#444444",
                                            "gridAlpha": 0,
                                            "tickLength": 0
                                        },
                                        "chartCursor": {
                                            "enabled": true,
                                            "cursorColor": "#000000"
                                        },
                                        "trendLines": [],
                                        "graphs": [
                                            {
                                                "balloonText": "<strong>[[title]]</strong> : [[value]]",
                                                "bulletBorderThickness": 0,
                                                "bulletSize": 5,
                                                "fillAlphas": 1,
                                                "id": "AmGraph-1",
                                                "markerType": "circle",
                                                "title": "정보량",
                                                "type": "column",
                                                "valueField": "column-1"
                                            },
                                            {
                                                "balloonText": "<strong>[[title]]</strong> : [[value]]%",
                                                "bulletBorderThickness": 0,
                                                "bulletSize": 5,
                                                "dashLength": 4,
                                                "id": "AmGraph-2",
                                                "lineThickness": 5,
                                                "title": "부정률",
                                                "valueAxis": "ValueAxis-2",
                                                "valueField": "column-2"
                                            }
                                        ],
                                        "guides": [],
                                        "valueAxes": [
                                            {
                                                "id": "ValueAxis-1",
                                                "stackType": "regular",
                                                "axisThickness": 0,
                                                "color": "#c0c0c0",
                                                "dashLength": 2,
                                                "gridAlpha": 1,
                                                "gridColor": "#D8D8D8",
                                                "tickLength": 0,
                                                "title": ""
                                            },
                                            {
                                                "id": "ValueAxis-2",
                                                "maximum": 100,
                                                "position": "right",
                                                "stackType": "regular",
                                                "unit": "%",
                                                "autoGridCount": false,
                                                "axisThickness": 0,
                                                "color": "#c0c0c0",
                                                "dashLength": 2,
                                                "gridAlpha": 1,
                                                "gridColor": "#D8D8D8",
                                                "gridCount": 2,
                                                "gridThickness": 0,
                                                "tickLength": 0,
                                                "title": ""
                                            }
                                        ],
                                        "allLabels": [],
                                        "balloon": {
                                            "color": "#FFFFFF",
                                            "fadeOutDuration": 0,
                                            "fillAlpha": 0.67,
                                            "fillColor": "#000000",
                                            "fontSize": 11,
                                            "horizontalPadding": 10,
                                            "pointerWidth": 5,
                                            "shadowAlpha": 0.3
                                        },
                                        "legend": {
                                            "enabled": true,
                                            "align": "center",
                                            "autoMargins": false,
                                            "color": "#444444",
                                            "equalWidths": false,
                                            "marginLeft": 0,
                                            "marginRight": 0,
                                            "markerLabelGap": 6,
                                            "markerSize": 11,
                                            "markerType": "circle",
                                            "spacing": 15,
                                            "useGraphSettings": true,
                                            "valueText": "",
                                            "verticalGap": 2
                                        },
                                        "titles": [],
                                        "dataProvider": [
<%
for (int i=0; i<resultList.size(); i++) {
	Map resultMap = (Map) resultList.get(i);
%>
											{
											    "category": "<%=resultMap.get("i_MMdd_date") %>",
											    "yyyymmddDate": "<%=resultMap.get("i_yyyyMMdd_date") %>",
											    "column-1": <%=resultMap.get("cnt") %>,
											    "column-2": <%=resultMap.get("neg_rate") %>
											}
<%
	if ((resultList.size() - i) > 0) out.print(",");
}
%>
                                        ]
                                    };
                                    var chart = AmCharts.makeChart("chart_01", chartOption);
                                    chart.addListener("clickGraphItem", function(event){
                                        var yyyymmddDate = event.item.dataContext.yyyymmddDate;
                                        
                                    	var params = "?";
                                        params += "sDate=" + yyyymmddDate;
                                        params += "&eDate=" + yyyymmddDate;
                                        params += "&channel=" + "<%=channel %>";
                                        params += "&searchType=" + "<%=searchType %>";
                                        params += "&notEncode=N";
                                        params += "&lucyKeyword=" + "<%=(String) searchKeyword %>";
                                        params += "&andSearchKeyword=" + "<%=(String) andSearchKeyword %>";
                                        params += "&orSearchKeyword=" + "<%=(String) orSearchKeyword %>";
                                        params += "&exSearchKeyword=" + "<%=(String) exSearchKeyword %>";
<%--                                         params += "&lucyKeyword=" + "<%=URLEncoder.encode((String) searchKeyword, "UTF-8") %>"; --%>
<%--                                         params += "&andSearchKeyword=" + "<%=URLEncoder.encode((String) andSearchKeyword, "UTF-8") %>"; --%>
<%--                                         params += "&orSearchKeyword=" + "<%=URLEncoder.encode((String) orSearchKeyword, "UTF-8") %>"; --%>
<%--                                         params += "&exSearchKeyword=" + "<%=URLEncoder.encode((String) exSearchKeyword, "UTF-8") %>"; --%>
<%--                                         params += "&searchKeywordLength=" + "<%=searchKeywordLength %>"; --%>
                                        
                                        popupOpen( "../popup/rel_info_lucyAPI.jsp" + params );
                                    });
                                })();
                            </script>