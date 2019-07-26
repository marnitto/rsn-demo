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
System.out.println("=================SECTION2 PARAMS START=================");
pr.printParams();
System.out.println("=================SECTION2 PARAMS END=================");

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

SearchMgr sMgr = new SearchMgr();

String searchType = pr.getString("searchType", ""); // 검색 방식 (1:구문, 2:인접, 3:AND)
String andSearchKeyword = pr.getString("andSearchKeyword", ""); // AND 키워드
String orSearchKeyword = pr.getString("orSearchKeyword", ""); // OR 키워드
String exSearchKeyword = pr.getString("exSearchKeyword", ""); // 제외 키워드
// String searchKeywordLength = pr.getString("searchKeywordLength", "0"); // 글자수

Map resultMap = sMgr.selectSectionMap("datetrend", replaceSDate, replaceEDate, channel, searchType, searchKeyword, andSearchKeyword, orSearchKeyword, exSearchKeyword, "", 0, rowLimit, false);
List resultList = sMgr.selectSectionList("datetrend", replaceSDate, replaceEDate, channel, searchType, searchKeyword, andSearchKeyword, orSearchKeyword, exSearchKeyword, "", 0, rowLimit, false);
System.out.println("resultList.size()=> " + resultList.size());
%>
<%!

%>
                            <div class="fl">
                                <div id="chart_02" class="ui_chart"></div>
                                <script type="text/javascript">
                                    (function(){
                                        var chartOption = {
                                            "type": "pie",
                                            "balloonText": "<strong>[[category]]</strong><br>[[value]]건",
                                            "innerRadius": "50%",
                                            "labelRadius": -17,
                                            "labelText": "[[percents]]%",
                                            "pieY": 100,
                                            "pullOutRadius": "0%",
                                            "radius": "95",
                                            "startRadius": "0%",
                                            "colors": [
                                                "#5ba1e0",
                                                "#ea7070",
                                                "#aaaaaa"
                                            ],
                                            "marginBottom": 0,
                                            "marginTop": 0,
                                            "pullOutDuration": 0,
                                            "startDuration": 0,
                                            "titleField": "category",
                                            "valueField": "column-1",
                                            "addClassNames": true,
                                            "color": "#FFFFFF",
                                            "percentPrecision": 0,
                                            "allLabels": [],
                                            "balloon": {
                                                "color": "#FFFFFF",
                                                "fadeOutDuration": 0,
                                                "fillAlpha": 0.67,
                                                "fillColor": "#000000",
                                                "fixedPosition": false,
                                                "fontSize": 11,
                                                "horizontalPadding": 10,
                                                "pointerWidth": 5,
                                                "shadowAlpha": 0.3
                                            },
                                            "legend": {
                                                "enabled": true,
                                                "align": "center",
                                                "autoMargins": false,
                                                "equalWidths": false,
                                                "spacing": 15,
                                                "color": "#444444",
                                                "marginLeft": 0,
                                                "marginRight": 0,
                                                "markerLabelGap": 6,
                                                "markerSize": 11,
                                                "markerType": "circle",
                                                "valueText": "",
                                                "verticalGap": 2
                                            },
                                            "titles": [],
                                            "dataProvider": [
                                                {
                                                	"category": "긍정",
                                                	"senti": "1",
                                                    "column-1": <%=resultMap.get("pos_sum_cnt") %>
                                                },
                                                {
                                                    "category": "부정",
                                                    "senti": "2",
                                                    "column-1": <%=resultMap.get("neg_sum_cnt") %>
                                                },
                                                {
                                                    "category": "중립",
                                                    "senti": "3",
                                                    "column-1": <%=resultMap.get("neu_sum_cnt") %>
                                                }
                                            ]
                                        };
                                        var chart = AmCharts.makeChart("chart_02", chartOption);
                                        chart.addListener("clickSlice", function(event){
                                        	var params = "?";
                                        	var senti = event.dataItem.dataContext.senti;
                                        	
                                        	params += "sDate=<%=sDate %>" + "&eDate=<%=eDate %>" + "&trend=" + senti;
                                        	params += "&channel=" + "<%=channel %>";
                                        	params += "&searchType=" + "<%=searchType %>";
                                        	params += "&lucyKeyword=" + "<%=searchKeyword %>";
                                            params += "&andSearchKeyword=" + "<%=andSearchKeyword %>";
                                            params += "&orSearchKeyword=" + "<%=orSearchKeyword %>";
                                            params += "&exSearchKeyword=" + "<%=exSearchKeyword %>";
                                            params += "&notEncode=N";
<%--                                             params += "&lucyKeyword=" + "<%=URLEncoder.encode((String) searchKeyword, "UTF-8") %>"; --%>
<%--                                             params += "&andSearchKeyword=" + "<%=URLEncoder.encode((String) andSearchKeyword, "UTF-8") %>"; --%>
<%--                                             params += "&orSearchKeyword=" + "<%=URLEncoder.encode((String) orSearchKeyword, "UTF-8") %>"; --%>
<%--                                             params += "&exSearchKeyword=" + "<%=URLEncoder.encode((String) exSearchKeyword, "UTF-8") %>"; --%>
<%--                                             params += "&searchKeywordLength=" + "<%=searchKeywordLength %>"; --%>
                                            
                                            popupOpen( "../popup/rel_info_lucyAPI.jsp" + params );
                                        });
                                    })();
                                </script>
                            </div>
                            <div class="fr">
                                <div id="chart_03" class="ui_chart"></div>
                                <script type="text/javascript">
                                    (function(){
                                        var chartOption = {
                                            "type": "serial",
                                            "categoryField": "category",
                                            "columnWidth": 0.4,
                                            "marginBottom": 0,
                                            "marginTop": 10,
                                            "colors": [
                                                "#5ba1e0",
                                                "#ea7070",
                                                "#aaaaaa"
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
                                                    "title": "긍정",
                                                    "senti": "1",
                                                    "type": "column",
                                                    "valueField": "column-1"
                                                },
                                                {
                                                    "balloonText": "<strong>[[title]]</strong> : [[value]]",
                                                    "bulletBorderThickness": 0,
                                                    "bulletSize": 5,
                                                    "fillAlphas": 1,
                                                    "id": "AmGraph-2",
                                                    "markerType": "circle",
                                                    "title": "부정",
                                                    "senti": "2",
                                                    "type": "column",
                                                    "valueField": "column-2"
                                                },
                                                {
                                                    "balloonText": "<strong>[[title]]</strong> : [[value]]",
                                                    "bulletBorderThickness": 0,
                                                    "bulletSize": 5,
                                                    "fillAlphas": 1,
                                                    "id": "AmGraph-3",
                                                    "markerType": "circle",
                                                    "title": "중립",
                                                    "senti": "3",
                                                    "type": "column",
                                                    "valueField": "column-3"
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
    Map subMap = (Map) resultList.get(i);
%>
												{
												    "category": "<%=subMap.get("i_MMdd_date") %>",
												    "yyyymmddDate": "<%=subMap.get("i_yyyyMMdd_date") %>",
												    "column-1": <%=subMap.get("pos_cnt") %>,
												    "column-2": <%=subMap.get("nag_cnt") %>,
												    "column-3": <%=subMap.get("neu_cnt") %>
												}
<%
    if ((resultList.size() - i) > 0) out.print(",");
}
%>
                                            ]
                                        };
                                        var chart = AmCharts.makeChart("chart_03", chartOption);
                                        chart.addListener("clickGraphItem", function(event){
                                        	var yyyymmddDate = event.item.dataContext.yyyymmddDate;
                                        	var senti = event.graph.senti;
                                        	
                                        	var params = "?";
                                        	params += "sDate=" + yyyymmddDate + "&eDate=" + yyyymmddDate + "&trend=" + senti;
                                            params += "&channel=" + "<%=channel %>";
                                            params += "&searchType=" + "<%=searchType %>";
                                            params += "&lucyKeyword=" + "<%=searchKeyword %>";
                                            params += "&andSearchKeyword=" + "<%=andSearchKeyword %>";
                                            params += "&orSearchKeyword=" + "<%=orSearchKeyword %>";
                                            params += "&exSearchKeyword=" + "<%=exSearchKeyword %>";
                                            params += "&notEncode=N";
<%--                                             params += "&lucyKeyword=" + "<%=URLEncoder.encode((String) searchKeyword, "UTF-8") %>"; --%>
<%--                                             params += "&andSearchKeyword=" + "<%=URLEncoder.encode((String) andSearchKeyword, "UTF-8") %>"; --%>
<%--                                             params += "&orSearchKeyword=" + "<%=URLEncoder.encode((String) orSearchKeyword, "UTF-8") %>"; --%>
<%--                                             params += "&exSearchKeyword=" + "<%=URLEncoder.encode((String) exSearchKeyword, "UTF-8") %>"; --%>
<%--                                             params += "&searchKeywordLength=" + "<%=searchKeywordLength %>"; --%>

                                            popupOpen( "../popup/rel_info_lucyAPI.jsp" + params);
                                        });
                                    })();
                                </script>
                            </div>