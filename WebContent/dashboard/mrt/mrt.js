var scope = "";
var keyword = "";
var sDate = "";
var eDate = "";
var issueCode = "";

/*	Select Box setting	*/
function initSetting(){
	var param = "section=0";
	$.ajax({      
        type:"POST",  
        url:"mrtDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$("#media_sel_00, #sel_keyword_00_00, #info_sel_00").html("");
        	$("#sel_keyword_00_00").append("<option value='0'>전체</option>");
        	$("#info_sel_00").append("<option value='0'>전체</option>");
        	for(var i=0; i<data.initData.length; i++){
        		var tmpData = data.initData[i];
        		var iccode = tmpData.iccode;
        		
        		if(iccode!="5" && iccode!="7" && iccode!="9"){	
        			$("#media_sel_00").append("<option value=" + iccode + ">" + tmpData.icname + "</option>");
        		}
        		
        		$("#sel_keyword_00_00").append("<option value=" + iccode + ">" + tmpData.icname + "</option>");
        		$("#info_sel_00").append("<option value=" + iccode + ">" + tmpData.icname + "</option>");
        	}
        }
	});	
}
 
/*	주요 시정 확산 목록	*/
function section01(nowPage, op){
	$(".ui_box_00").eq(0).find(".v0").fadeIn(80);
	var param = "section=1&nowPage=" + nowPage + "&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&op=" + op;
	$.ajax({      
        type:"POST",  
        url:"mrtDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	var html = "";
        	var length = data.sectionData1.data.length;
        	if(length>0){
        		for(var i=0; i<length; i++){
        			var tmpData = data.sectionData1.data[i];
        			html += "<tr>";
        			html += "<td class='title'><a href='#' data-code='" + tmpData.iccode + "' onclick='section01_select(this,"+tmpData.icflag+"); return false;' title='" + tmpData.issue + "'>" + tmpData.issue + "</a></td>";
        			
        			html += "<td><strong title='총정보량:" + parseInt(tmpData.cnt).lengthLimitComma(5, 0) + "'>" + parseInt(tmpData.cnt).lengthLimitComma(5, 0)
        			+ "</strong><span class='fs11'>(<span title='기사건수:" + parseInt(tmpData.cnt1).lengthLimitComma(5, 0) + "'>" + parseInt(tmpData.cnt1).lengthLimitComma(5, 0)
        			+ "</span>/<span title='온라인건수:" + parseInt(tmpData.cnt2).lengthLimitComma(5, 0) + "'>" + parseInt(tmpData.cnt2).lengthLimitComma(5, 0) + "</span>)</span></td>";
        			
        			html += "<td><div id='brd_chart_00_0" + i + "' data-code='" + tmpData.iccode + "' class='ui_chart' style='height:18px'></div></td>";
        			html += "<td><span class='ui_fc_blue' title='긍정 : " + tmpData.pos + "'>" + parseInt(tmpData.pos).lengthLimitComma(5, 0) + "</span>";
        			html += " / <span class='ui_fc_red' title='부정 : " + tmpData.neg + "'>" + parseInt(tmpData.neg).lengthLimitComma(5, 0) + "</span>";
        			html += " / <span class='ui_fc_green' title='중립 : " + tmpData.neu + "'>" + parseInt(tmpData.neu).lengthLimitComma(5, 0) + "</span></td>";
        			html += "<td><span data-code='" + tmpData.iccode + "' class='pie_chart'></span></td>";
        			html += "</tr>";
        		}
        		for(var i=0; i<(10-length); i++){
        			html += "<tr><td colspan='5'>&nbsp;</td></tr>";
        		}
        		$("#section01_contents > tbody").html(html);
        	
        		if($("#brd_chart_00_00").length>0){
        			chart_00_00 = AmCharts.makeChart("brd_chart_00_00", JSON.parse(JSON.stringify(sparkChartOption)));
        			chart_00_00.dataProvider = section01_1($("#brd_chart_00_00").data("code"));
        			chart_00_00.validateData();	
        		}
    			
        		if($("#brd_chart_00_01").length>0){
	    			chart_00_01 = AmCharts.makeChart("brd_chart_00_01", JSON.parse(JSON.stringify(sparkChartOption)));
	    			chart_00_01.dataProvider = section01_1($("#brd_chart_00_01").data("code"));
	    			chart_00_01.validateData();
        		}
        		
        		if($("#brd_chart_00_02").length>0){
	    			chart_00_02 = AmCharts.makeChart("brd_chart_00_02", JSON.parse(JSON.stringify(sparkChartOption)));
	    			chart_00_02.dataProvider = section01_1($("#brd_chart_00_02").data("code"));
	    			chart_00_02.validateData();
        		}
    			
        		if($("#brd_chart_00_03").length>0){
	    			chart_00_03 = AmCharts.makeChart("brd_chart_00_03", JSON.parse(JSON.stringify(sparkChartOption)));
	    			chart_00_03.dataProvider = section01_1($("#brd_chart_00_03").data("code"));
	    			chart_00_03.validateData();
        		}
    			
    			if($("#brd_chart_00_04").length>0){
	    			chart_00_04 = AmCharts.makeChart("brd_chart_00_04", JSON.parse(JSON.stringify(sparkChartOption)));
	    			chart_00_04.dataProvider = section01_1($("#brd_chart_00_04").data("code"));
	    			chart_00_04.validateData();
    			}
    			
    			if($("#brd_chart_00_05").length>0){
	    			chart_00_05 = AmCharts.makeChart("brd_chart_00_05", JSON.parse(JSON.stringify(sparkChartOption)));
	    			chart_00_05.dataProvider = section01_1($("#brd_chart_00_05").data("code"));
	    			chart_00_05.validateData();
    			}
    			
    			if($("#brd_chart_00_06").length>0){
	    			chart_00_06 = AmCharts.makeChart("brd_chart_00_06", JSON.parse(JSON.stringify(sparkChartOption)));
	    			chart_00_06.dataProvider = section01_1($("#brd_chart_00_06").data("code"));
	    			chart_00_06.validateData();
    			}
    			
    			if($("#brd_chart_00_07").length>0){
	    			chart_00_07 = AmCharts.makeChart("brd_chart_00_07", JSON.parse(JSON.stringify(sparkChartOption)));
	    			chart_00_07.dataProvider = section01_1($("#brd_chart_00_07").data("code"));
	    			chart_00_07.validateData();
    			}
    			
    			if($("#brd_chart_00_08").length>0){
	    			chart_00_08 = AmCharts.makeChart("brd_chart_00_08", JSON.parse(JSON.stringify(sparkChartOption)));
	    			chart_00_08.dataProvider = section01_1($("#brd_chart_00_08").data("code"));
	    			chart_00_08.validateData();
    			}
    			
    			if($("#brd_chart_00_09").length>0){
	    			chart_00_09 = AmCharts.makeChart("brd_chart_00_09", JSON.parse(JSON.stringify(sparkChartOption)));
	    			chart_00_09.dataProvider = section01_1($("#brd_chart_00_09").data("code"));
	    			chart_00_09.validateData();
    			}
    			
    			for(var i=0; i<length; i++){
    				var code = $("#section01_contents > tbody > tr").eq(i).find("td").eq(4).find("span").data("code");
    				var neg = section01_2(code);
    				
    				$("#section01_contents > tbody > tr").eq(i).find("td").eq(4).find("span").text(neg + "%");
    				$("#section01_contents > tbody > tr").eq(i).find("td").eq(4).find("span").attr("title", neg + "%");
    				
    				if(neg==0){
    					$("#section01_contents > tbody > tr").eq(i).find("td").eq(4).find("span").addClass("lv_0");
    				}else if(neg>=1 && neg<=9){
    					$("#section01_contents > tbody > tr").eq(i).find("td").eq(4).find("span").addClass("lv_1");
    				}else if(neg>=10 && neg<=19){
    					$("#section01_contents > tbody > tr").eq(i).find("td").eq(4).find("span").addClass("lv_2");
    				}else if(neg>=20 && neg<=29){
    					$("#section01_contents > tbody > tr").eq(i).find("td").eq(4).find("span").addClass("lv_3");
    				}else if(neg>=30 && neg<=39){
    					$("#section01_contents > tbody > tr").eq(i).find("td").eq(4).find("span").addClass("lv_4");
    				}else if(neg>=40 && neg<=49){
    					$("#section01_contents > tbody > tr").eq(i).find("td").eq(4).find("span").addClass("lv_5");
    				}else if(neg==50){
    					$("#section01_contents > tbody > tr").eq(i).find("td").eq(4).find("span").addClass("lv_6");
    				}else if(neg>=51 && neg<=59){
    					$("#section01_contents > tbody > tr").eq(i).find("td").eq(4).find("span").addClass("lv_7");
    				}else if(neg>=60 && neg<=69){
    					$("#section01_contents > tbody > tr").eq(i).find("td").eq(4).find("span").addClass("lv_8");
    				}else if(neg>=70 && neg<=79){
    					$("#section01_contents > tbody > tr").eq(i).find("td").eq(4).find("span").addClass("lv_9");
    				}else if(neg>=80 && neg<=89){
    					$("#section01_contents > tbody > tr").eq(i).find("td").eq(4).find("span").addClass("lv_10");
    				}else if(neg>=90 && neg<=99){
    					$("#section01_contents > tbody > tr").eq(i).find("td").eq(4).find("span").addClass("lv_11");
    				}else if(neg==100){
    					$("#section01_contents > tbody > tr").eq(i).find("td").eq(4).find("span").addClass("lv_12");
    				}
        		}	
        	}else{
        		html += "<tr><td colspan='5'>&nbsp;</td></tr><tr><td colspan='5'>&nbsp;</td></tr><tr><td colspan='5'>&nbsp;</td></tr>";
        		html += "<tr><td colspan='5'>&nbsp;</td></tr><tr><td colspan='5'>데이터가 없습니다</td></tr><tr><td colspan='5'>&nbsp;</td></tr>";
        		html += "<tr><td colspan='5'>&nbsp;</td></tr><tr><td colspan='5'>&nbsp;</td></tr><tr><td colspan='5'>&nbsp;</td></tr><tr><td colspan='5'>&nbsp;</td></tr>";
        		$("#section01_contents > tbody").html(html);
        		$("#hidden_section").css("display", "none");
        	}
        	paging(10, 10, data.sectionData1.total, nowPage, $("#section01_paging"), "section01Paging");

        	$(".ui_box_00").eq(0).find(".v0").fadeOut(80);
        	$("#section01_contents > tbody > tr > td").eq(0).find("a").click();
        }
	});	
}

/*	주요 시정 확산 목록 - 추세 항목의 데이터를 가져온다	*/
function section01_1(issueCode){
	var param = "section=1_1&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&issueCode=" + issueCode;
	var result = {};
	$.ajax({   
        type:"POST",  
        url:"mrtDao.jsp",
        data:param,
        dataType:"json",
        async: false,
        success:function(data){
        	result = data.sectionData1_1;
        }	
	});
	return result;
}

/*	주요 시정 확산 목록 - 부정율 항목의 데이터를 가져온다		*/
function section01_2(issueCode){
	var param = "section=1_2&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&issueCode=" + issueCode;
	var result = "";
	$.ajax({   
        type:"POST",  
        url:"mrtDao.jsp",
        data:param,
        dataType:"json",
        async: false,
        success:function(data){
        	result = data.sectionData1_2[0].neg;
        }	
	});
	return result;
}

/*	주요 시정 - 주간 추이 현황	*/
function section02(){
	$(".ui_box_00").eq(2).find(".v0").fadeIn(80);   
	var param = "section=2&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&issueCode=" + issueCode;
	$.ajax({      
        type:"POST",  
        url:"mrtDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$(".ui_box_00").eq(2).find(".v0").fadeOut(80);        	
        	chart1.dataProvider = data.sectionData2;
        	chart1.validateData();	
        }
	});
}

/*	주요 시정 - 매체별 정보량, 매체별 성향 현황	*/
function section03(){
	$(".ui_box_00").eq(3).find(".v0").fadeIn(80);
	$(".ui_box_00").eq(4).find(".v0").fadeIn(80);
	var param = "section=3&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&issueCode=" + issueCode;
	$.ajax({      
        type:"POST",  
        url:"mrtDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$(".ui_box_00").eq(3).find(".v0").fadeOut(80);
        	$(".ui_box_00").eq(4).find(".v0").fadeOut(80);
        	
        	chart2.dataProvider = data.sectionData3;
        	chart3.dataProvider = data.sectionData3;
        	
        	chart2.validateData();
        	chart2.validateNow();
        	chart3.validateData();
        	chart3.validateNow();
        }
	});
}

/*	주요 시정 - 주요 매체 현황	*/
function section04(source, limit){
	$(".ui_box_00").eq(5).find(".v0").fadeIn(80);
	var param = "section=4&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&issueCode=" + issueCode + "&source=" + source + "&limit=" + limit;
	$.ajax({      
        type:"POST",  
        url:"mrtDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$(".ui_box_00").eq(5).find(".v0").fadeOut(80);
        	chart4.dataProvider = data.sectionData4;
        	chart4.validateData();	
        }
	});
}

/*	주요 시정 - 주요 연관키워드	*/
function section05(source, limit, senti){
	$(".ui_box_00").eq(6).find(".v0").fadeIn(80);  
	var param = "section=5&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&issueCode=" + issueCode + "&source=" + source + "&limit=" + limit + "&senti=" + senti;
	$.ajax({      
        type:"POST",  
        url:"mrtDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$(".ui_box_00").eq(6).find(".v0").fadeOut(80);      	
        	$("#spider_00").html("");
        	
        	var length = data.sectionData5.length;
        	if(length>0){
        		var total1 = 0;
        		var total2 = 0;
        		var pos = 0;
        		var neg = 0; 
        		var neu = 0;
        		
        		// tmpData.cnt : 연관키워드 중복 o, tmpData.keycnt : 연관키워드 중복 x
        		for(var i=0; i<length; i++){
        			var tmpData = data.sectionData5[i];
        			total1 = total1 + parseInt(tmpData.cnt);
        			total2 = total2 + parseInt(tmpData.keycnt);
        			pos = pos + parseInt(tmpData.pos);
        			neg = neg + parseInt(tmpData.neg);
        			neu = neu + parseInt(tmpData.neu);
        		}
        		var html = "<div class='line_area'></div><div class='all''><a href='#' onclick='return false;' style='cursor:default;'><strong>전체</strong><span>(총 " + parseInt(total2).lengthLimitComma(5, 0) +"건)</span><div class='f_clear'><span style='width:" + percent(total1, pos) + "%'>긍정</span><span style='width:" + percent(total1, neg) + "%'>부정</span><span style='width:" + percent(total1, neu) + "%'>중립</span></div></a></div>";
        		for(var i=0; i<length; i++){
        			var tmpData = data.sectionData5[i];       	
        			var total = parseInt(tmpData.pos) + parseInt(tmpData.neg) + parseInt(tmpData.neu);
		        	html += "<div class='item'><a href='#' id=" + tmpData.patcode + "><strong>" + tmpData.wordname + "</strong><span>(총 " + parseInt(tmpData.keycnt).lengthLimitComma(5, 0) + "건)</span><div class='f_clear'><span style='width:" + percent(total, tmpData.pos) + "%'>긍정</span><span style='width:" + percent(total, tmpData.neg) + "%'>부정</span><span style='width:" + percent(total, tmpData.neu) + "%'>중립</span></div></a></div>";		        
        		}
				$("#spider_00").html(html);
				$("#spider_00").ui_spider();
				
				$("#spider_00 a").not("#spider_00 a:eq(0)").off("click");
				$("#spider_00 a").not("#spider_00 a:eq(0)").click(function(e){					
					var patCode = $(this).attr("id");
					var source = "";
					var senti = "";

					if($("#sel_keyword_00_00").val()=="0"){
						$("#sel_keyword_00_00 > option").not("#sel_keyword_00_00 > option:eq(0)").each(function(){
							source += $(this).val() + ",";
						});
						source = source.replace(/,$/, "");
					}else{
						source = $("#sel_keyword_00_00").val();
					}
					
					if($("#sel_keyword_00_02").val()=="0"){
						$("#sel_keyword_00_02 > option").not("#sel_keyword_00_02 > option:eq(0)").each(function(){
							senti += $(this).val() + ",";
						});
						senti = senti.replace(/,$/, "");
					}else{
						senti = $("#sel_keyword_00_02").val();
					}
					
					var codeList = "4@" + issueCode + "/6@" + source + "/9@" + senti;
					var param = "scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&codeList=" + codeList + "&relationKeyword=" + patCode;
					popupOpen( "../popup/rel_info.jsp", param);
					return false;
				});
        	}
        }
	});
}

/*	주요 시정 - 연관키워드 현황	*/
function section06(){
	$(".ui_box_00").eq(7).find(".v0").fadeIn(80);
	var param = "section=6&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&issueCode=" + issueCode;
	$.ajax({      
        type:"POST",  
        url:"mrtDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$(".ui_box_00").eq(7).find(".v0").fadeOut(80);
        	
        	var html = "";
        	var length1 = data.sectionData6.pos.length;
			var length2 = data.sectionData6.neg.length;
			var length3 = data.sectionData6.neu.length;

        	if(length1>0 || length2>0 || length3>0){
        		for(var i=0; i<10; i++){
        			var tmpData1 = data.sectionData6.pos[i];
        			var tmpData2 = data.sectionData6.neg[i];
        			var tmpData3 = data.sectionData6.neu[i];
        			param = "scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate;
        			
        			html += "<tr>";
        			html += "<th scope='row'>" + (i+1) + "</th>";
        			
        			if(i<length1){
        				html += "<td><a href='#' onclick='popupOpen(\"../popup/rel_info.jsp\", \"" + param + "&codeList=4@" + issueCode + "/9@1" + "&relationKeyword=" + tmpData1.patcode + "\"); return false;' class='ui_fc_blue' title='" + tmpData1.wordname + "(" + parseInt(tmpData1.cnt).lengthLimitComma(5, 0) + ")'>" + tmpData1.wordname + "(" + parseInt(tmpData1.cnt).lengthLimitComma(5, 0) + ")</a></td>";
        			}else{
        				html += "<td>&nbsp;</td>";
        			}
        			
        			if(i<length2){
        				html += "<td><a href='#' onclick='popupOpen(\"../popup/rel_info.jsp\", \"" + param + "&codeList=4@" + issueCode + "/9@2" + "&relationKeyword=" + tmpData2.patcode + "\"); return false;' class='ui_fc_red' onclick='' title='" + tmpData2.wordname + "(" + parseInt(tmpData2.cnt).lengthLimitComma(5, 0) + ")'>" + tmpData2.wordname + "(" + parseInt(tmpData2.cnt).lengthLimitComma(5, 0) + ")</a></td>";	
        			}else{
        				html += "<td>&nbsp;</td>";
        			}
        			
        			if(i<length3){
        				html += "<td><a href='#' onclick='popupOpen(\"../popup/rel_info.jsp\", \"" + param + "&codeList=4@" + issueCode + "/9@3" + "&relationKeyword=" + tmpData3.patcode + "\"); return false;' class='ui_fc_green' onclick='' title='" + tmpData3.wordname + "(" + parseInt(tmpData3.cnt).lengthLimitComma(5, 0) + ")'>" + tmpData3.wordname + "(" + parseInt(tmpData3.cnt).lengthLimitComma(5, 0) + ")</a></td>";	
        			}else{
        				html += "<td>&nbsp;</td>";
        			}
        			
        			html += "</tr>";
        		}
        	}else{
        		html += "<tr><td colspan='4'>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr>";
        		html += "<tr><td colspan='4'>&nbsp;</td></tr><tr><td colspan='4'>데이터가 없습니다</td></tr><tr><td colspan='4'>&nbsp;</td></tr>";
        		html += "<tr><td colspan='4'>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr>";
        	}
        	$("#section06_contents > tbody").html(html);
        }
	});
}

/*	주요 시정 - 관련정보 보기		*/
function section07(nowPage, source){
	$(".ui_box_00").eq(8).find(".v0").fadeIn(80);
	var param = "section=7&nowPage=" + nowPage + "&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate
				+ "&issueCode=" + issueCode + "&source=" + source;
	$.ajax({      
        type:"POST",  
        url:"mrtDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$(".ui_box_00").eq(8).find(".v0").fadeOut(80);
        	var html = "";
        	var length = data.sectionData7.data.length;
        	if(length>0){
        		for(var i=0; i<length; i++){
        			var tmpData = data.sectionData7.data[i];
        			html += "<tr>";
        			html += "<td>" + tmpData.date + "</td>";
        			
        			var sitecode = tmpData.sitecode;
        			var caffeImg = "";
        			
        			if(sitecode=="3555" || sitecode=="4943"){
        				caffeImg = "<a href=\'javascript:portalSearch(\"" + sitecode + "\", \"" + urlTitle(tmpData.title) + "\")\' class=\'ui_bullet_cafe\'>Cafe</a>";
        			}
        			html += "<td class='title'>" + caffeImg + "<a href='http://hub.buzzms.co.kr?url=" + encodeURIComponent(tmpData.url) + "' target='_blank' title='" + attrTitle(tmpData.title) + "' >" + tmpData.title + "</a></td>";
        			
        			html += "<td><span title='" + tmpData.sitename + "'>" + tmpData.sitename + "</span></td>";
        			html += "<td><span class='" + senti(1, tmpData.senti) + "'>" + senti(0, tmpData.senti) + "</span></td>";
        			html += "<td><span title='" + tmpData.issue + "'>" + tmpData.issue + "</span></td>";
        			html += "</tr>";   			
        		}
        		for(var i=0; i<(10-length); i++){
        			html += "<tr><td colspan='5'>&nbsp;</td></tr>";
        		}
        	}else{
        		html += "<tr><td colspan='5'>&nbsp;</td></tr><tr><td colspan='5'>&nbsp;</td></tr><tr><td colspan='5'>&nbsp;</td></tr>";
        		html += "<tr><td colspan='5'>&nbsp;</td></tr><tr><td colspan='5'>데이터가 없습니다</td></tr><tr><td colspan='5'>&nbsp;</td></tr>";
        		html += "<tr><td colspan='5'>&nbsp;</td></tr><tr><td colspan='5'>&nbsp;</td></tr><tr><td colspan='5'>&nbsp;</td></tr><tr><td colspan='5'>&nbsp;</td></tr>";
        	}
        	$("#section07_contents > tbody").html(html);
        	       
        	paging(10, 10, data.sectionData7.total, nowPage, $("#section07_paging"), "section07Paging");
        }
	});
}

function section01Paging(nowPage){
	//section01(nowPage, $("input[name=s_c6_1_radio]:checked").val());
	section01(nowPage, 1);
}

function section07Paging(nowPage){
	section07(nowPage, $("#info_sel_00").val()); 
}

/*	주요 시정 확산 목록의 주요이슈를 선택했을 경우 */
function section01_select(ths, icflag){
	$("#hidden_section").css("display", "block");
	$("#section01_contents > tbody > tr").removeClass("active");
	$(ths).parent().parent().addClass("active");	
	
	issueCode = $(ths).data("code");
	$("#section02_subTitle").text($(ths).text());
	var source = 0;
	if(icflag == 1){
		source = 1;
		$("#media_sel_00").val("1");
		$("#media_sel_00 ~ label").text("언론");
	}else{
		source = 2;
		$("#media_sel_00").val("2");
		$("#media_sel_00 ~ label").text("트위터");
	}
	$("#media_sel_01").val("0");
	$("#media_sel_01 ~ label").text("1위 ~ 10위");	
	
	$("#sel_keyword_00_00").val("0");
	$("#sel_keyword_00_00 ~ label").text("전체");
	$("#sel_keyword_00_01").val("10");
	$("#sel_keyword_00_01 ~ label").text("10개");
	$("#sel_keyword_00_02").val("0");
	$("#sel_keyword_00_02 ~ label").text("전체");
	
	$("#info_sel_00").val("0");
	$("#info_sel_00 ~ label").text("전체");
	
	//$(".ui_box_00").find(".v0").fadeIn(80);
	
	initSetting();
	section02();
	section03();
	section04(source, 0);
	section05(0, 10, 0);
	section06();
	section07(1, 0);
}

function excelDownload(section, title){
	var url = "mrtExcelDao.jsp";
	var param = "section=" + section + "&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&issueCode=" + issueCode + "&title=" + title;
	
	if(section=="4"){
		param += "&source=" + $("#media_sel_00").val() + "&limit=" + $("#media_sel_01").val();
	}else if(section=="5"){
		param += "&source=" + $("#sel_keyword_00_00").val() + "&limit=" + $("#sel_keyword_00_01").val() + "&senti=" + $("#sel_keyword_00_02").val();
	}else if(section=="7"){
		param += "&source=" + $("#info_sel_00").val();
	}
	getExcel(event, url, param, title);
	
}