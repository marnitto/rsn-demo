var scope = "";
var keyword = "";
var sDate = "";
var eDate = "";

/*	Select Box setting	*/
function initSetting(){
	var param = "section=0";
	$.ajax({      
        type:"POST",  
        url:"opinionDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$("#s_c2_1_sel_00, #s_c4_1_sel_00, #sel_keyword_00_00, #s_c6_1_sel_00, #s_c6_2_sel_00").html("");
        	$("#s_c2_1_sel_00, #sel_keyword_00_00, #s_c6_1_sel_00, #s_c6_2_sel_00").html("<option value='0' selected>전체</option>");
        	
        	for(var i=0; i<data.initData.infoDiv.length; i++){
        		var tmpData = data.initData.infoDiv[i];
        		var iccode = tmpData.iccode;
        		
        		$("#s_c2_1_sel_00").append("<option value=" + iccode + ">" + tmpData.icname + "</option>");
        	}
        	
        	for(var i=0; i<data.initData.source.length; i++){
        		var tmpData = data.initData.source[i];
        		var iccode = tmpData.iccode;
        		
        		if(iccode!="5" && iccode!="7" && iccode!="9"){	
        			$("#s_c4_1_sel_00").append("<option value=" + iccode + ">" + tmpData.icname + "</option>");
        		}
        		$("#sel_keyword_00_00").append("<option value=" + iccode + ">" + tmpData.icname + "</option>");
        		$("#s_c6_1_sel_00").append("<option value=" + iccode + ">" + tmpData.icname + "</option>");
        		$("#s_c6_2_sel_00").append("<option value=" + iccode + ">" + tmpData.icname + "</option>");
        	}
        }
	});	
}

/*	정보량 현황	*/
function section01(){
	var param = "section=1&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate;
	$.ajax({      
        type:"POST",  
        url:"opinionDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$(".ui_box_00").eq(0).find(".v0").fadeOut(80);
        	var arr = [];
        	var pos = {};
        	var neg = {};
        	var neu = {};
        	
        	pos["category"] = "긍정";
        	pos["column-1"] = data.sectionData1[0].pos;
        	arr[0] = pos;
        	
        	neg["category"] = "부정";
        	neg["column-1"] = data.sectionData1[0].neg;
        	arr[1] = neg;
        	
        	neu["category"] = "중립";
        	neu["column-1"] = data.sectionData1[0].neu;
        	arr[2] = neu;
        	
        	chart1.dataProvider = arr;
        	chart1.validateData();

        	$("#s_c1_1 > .content > .ui_volumes > .info_wrap > a").text(parseInt(data.sectionData1[0].cnt).lengthLimitComma(5, 0));        	
        	$("#s_c1_1 > .content > .ui_volumes > .info_wrap > a").attr("onclick", "popupOpen('../popup/rel_info.jsp', '" + param + "&codeList=1@2/9@1,2,3'); return false;");
        	
        	$("#s_c1_1 > .content > .ui_volumes > .info_wrap > .senti_infos > p:nth-child(1) > a").text(parseInt(data.sectionData1[0].pos).lengthLimitComma(5, 0));
        	$("#s_c1_1 > .content > .ui_volumes > .info_wrap > .senti_infos > p:nth-child(1) > a").attr("onclick", "popupOpen('../popup/rel_info.jsp', '" + param + "&codeList=1@2/9@1'); return false;");
        	
        	$("#s_c1_1 > .content > .ui_volumes > .info_wrap > .senti_infos > p:nth-child(2) > a").text(parseInt(data.sectionData1[0].neg).lengthLimitComma(5, 0));
        	$("#s_c1_1 > .content > .ui_volumes > .info_wrap > .senti_infos > p:nth-child(2) > a").attr("onclick", "popupOpen('../popup/rel_info.jsp', '" + param + "&codeList=1@2/9@2'); return false;");
        	
        	$("#s_c1_1 > .content > .ui_volumes > .info_wrap > .senti_infos > p:nth-child(3) > a").text(parseInt(data.sectionData1[0].neu).lengthLimitComma(5, 0));
        	$("#s_c1_1 > .content > .ui_volumes > .info_wrap > .senti_infos > p:nth-child(3) > a").attr("onclick", "popupOpen('../popup/rel_info.jsp', '" + param + "&codeList=1@2/9@3'); return false;");
        }
	});
}

/*	주간 정보량 추이	*/
function section02(){
	var param = "section=2&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate;
	$.ajax({      
        type:"POST",  
        url:"opinionDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$(".ui_box_00").eq(1).find(".v0").fadeOut(80);
        	chart2.dataProvider = data.sectionData2;
        	chart2.validateData();
        }
	});
}

/*	정보구분 별 정보량	*/
function section03(pCode){
	$(".ui_box_00").eq(2).find(".v0").fadeIn(80);
	var param = "section=3&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&pCode=" + pCode;
	$.ajax({      
        type:"POST",  
        url:"opinionDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$(".ui_box_00").eq(2).find(".v0").fadeOut(80);
        	chart3.dataProvider = data.sectionData3;
        	chart3.validateData();
        }
	});
}

/*	주요이슈 별 정보량	*/
function section04(){
	var param = "section=4&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate;
	$.ajax({      
        type:"POST",  
        url:"opinionDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$(".ui_box_00").eq(3).find(".v0").fadeOut(80);
        	chart4.dataProvider = data.sectionData4;
        	chart4.validateData();
        }
	});
}

/*	매체 별 정보량, 매체 별 성향 현황	*/
function section05(){
	var param = "section=5&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate;
	$.ajax({      
        type:"POST",  
        url:"opinionDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$(".ui_box_00").eq(4).find(".v0").fadeOut(80);
        	$(".ui_box_00").eq(5).find(".v0").fadeOut(80);
        	
        	chart5_1.dataProvider = data.sectionData5;
        	chart5_1.validateData();
        	chart5_1.validateNow();
        	
        	chart5_2.dataProvider = data.sectionData5;
        	chart5_2.validateData();
        	chart5_2.validateNow();
        }
	});
}

/*	주요 매체 현황	*/
function section06(source, limit){
	$(".ui_box_00").eq(6).find(".v0").fadeIn(80);
	var param = "section=6&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&source=" + source + "&limit=" + limit;
	$.ajax({      
        type:"POST",  
        url:"opinionDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$(".ui_box_00").eq(6).find(".v0").fadeOut(80);
        	chart6.dataProvider = data.sectionData6;
        	chart6.validateData();
        }
	});
}

/*	주요 연관키워드		*/
function section07(source, limit, senti){
	$("#s_c5_1").find(".v0").fadeIn(80);
	var param = "section=7&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&source=" + source + "&limit=" + limit + "&senti=" + senti;
	$.ajax({      
        type:"POST",  
        url:"opinionDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$("#s_c5_1").find(".v0").fadeOut(80);
        	$("#spider_00").html("");
        	
        	var length = data.sectionData7.length;
        	if(length>0){
        		var total1 = 0;
        		var total2 = 0;
        		var pos = 0;
        		var neg = 0; 
        		var neu = 0;
        		
        		// tmpData.cnt : 연관키워드 중복 o, tmpData.keycnt : 연관키워드 중복 x
        		for(var i=0; i<length; i++){
        			var tmpData = data.sectionData7[i];
        			total1 = total1 + parseInt(tmpData.cnt);
        			total2 = total2 + parseInt(tmpData.keycnt);
        			pos = pos + parseInt(tmpData.pos);
        			neg = neg + parseInt(tmpData.neg);
        			neu = neu + parseInt(tmpData.neu);
        		}
        		var html = "<div class='line_area'></div><div class='all''><a href='#' onclick='return false;' style='cursor:default;'><strong>전체</strong><span>(총 " + parseInt(total2).lengthLimitComma(5, 0) +"건)</span><div class='f_clear'><span style='width:" + percent(total1, pos) + "%'>긍정</span><span style='width:" + percent(total1, neg) + "%'>부정</span><span style='width:" + percent(total1, neu) + "%'>중립</span></div></a></div>";
        		for(var i=0; i<length; i++){
        			var tmpData = data.sectionData7[i];       	
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
					
					//1@2 (분류체계-구분-박원순 서울시장)
					var codeList = "1@2/6@" + source + "/9@" + senti;
					var param = "scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&codeList=" + codeList + "&relationKeyword=" + patCode;
					popupOpen( "../popup/rel_info.jsp", param);
					return false;
				});
        	}
        }
	});
}

/*	연관키워드 현황		*/
function section08(){
	var param = "section=8&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate;
	$.ajax({      
        type:"POST",  
        url:"opinionDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$("#s_c5_2").find(".v0").fadeOut(80);
        	
        	var html = "";
			
			var length1 = JSON.parse(data.sectionData8.pos).length;
			var length2 = JSON.parse(data.sectionData8.neg).length;
			var length3 = JSON.parse(data.sectionData8.neu).length;

        	if(length1>0 || length2>0 || length3>0){
        		for(var i=0; i<10; i++){
        			var tmpData1 = JSON.parse(data.sectionData8.pos)[i];
        			var tmpData2 = JSON.parse(data.sectionData8.neg)[i];
        			var tmpData3 = JSON.parse(data.sectionData8.neu)[i];
        			param = "scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate;
        			
        			html += "<tr>";
        			html += "<th scope='row'>" + (i+1) + "</th>";
        			
        			if(i<length1){
        				html += "<td><a href='#' onclick='popupOpen(\"../popup/rel_info.jsp\", \"" + param + "&codeList=1@2/9@1" + "&relationKeyword=" + tmpData1.patcode + "\"); return false;' class='ui_fc_blue' title='" + tmpData1.wordname + "(" + parseInt(tmpData1.cnt).lengthLimitComma(5, 0) + ")'>" + tmpData1.wordname + "(" + parseInt(tmpData1.cnt).lengthLimitComma(5, 0) + ")</a></td>";
        			}else{
        				html += "<td>&nbsp;</td>";
        			}
        			
        			if(i<length2){
        				html += "<td><a href='#' onclick='popupOpen(\"../popup/rel_info.jsp\", \"" + param + "&codeList=1@2/9@2" + "&relationKeyword=" + tmpData2.patcode + "\"); return false;' class='ui_fc_red' title='" + tmpData2.wordname + "(" + parseInt(tmpData2.cnt).lengthLimitComma(5, 0) + ")'>" + tmpData2.wordname + "(" + parseInt(tmpData2.cnt).lengthLimitComma(5, 0) + ")</a></td>";	
        			}else{
        				html += "<td>&nbsp;</td>";
        			}
        			
        			if(i<length3){
        				html += "<td><a href='#' onclick='popupOpen(\"../popup/rel_info.jsp\", \"" + param + "&codeList=1@2/9@3" + "&relationKeyword=" + tmpData3.patcode + "\"); return false;' class='ui_fc_green' title='" + tmpData3.wordname + "(" + parseInt(tmpData3.cnt).lengthLimitComma(5, 0) + ")'>" + tmpData3.wordname + "(" + parseInt(tmpData3.cnt).lengthLimitComma(5, 0) + ")</a></td>";	
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
        	$("#section08_contents > tbody").html(html);
        }
	});
}

/*	긍정 관련 정보	*/
function section09_1(nowPage, source){
	$("#s_c6_1").find(".v0").fadeIn(80);
	var param = "section=9_1&nowPage=" + nowPage + "&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&source=" + source;
	$.ajax({      
        type:"POST",  
        url:"opinionDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$("#s_c6_1").find(".v0").fadeOut(80);
        	var html = "";
        	var length = data.sectionData9_1.data.length;
        	if(length>0){
        		for(var i=0; i<length; i++){
        			var tmpData = data.sectionData9_1.data[i];
        			html += "<tr>";
        			html += "<td><span title='" + tmpData.sitename + "'>" + tmpData.sitename + "</span></td>";
        			
        			var sitecode = tmpData.sitecode;
        			var caffeImg = "";
        			if(sitecode=="3555" || sitecode=="4943"){
        				caffeImg = "<a href=\'javascript:portalSearch(\"" + sitecode + "\", \"" + urlTitle(tmpData.title) + "\")\' class=\'ui_bullet_cafe\'>Cafe</a>";
        			}
        			
        			
        			html += "<td class='title'>" + caffeImg + "<a href='http://hub.buzzms.co.kr?url=" + encodeURIComponent(tmpData.url) + "' target='_blank' title='" + attrTitle(tmpData.title) + "' >" + tmpData.title + "</a></td>";
        			html += "<td><span title='" + tmpData.samecnt + "'>" + tmpData.samecnt + "</span></td>";
        			html += "<td>" + tmpData.date + "</td>";
        			html += "</tr>";   			
        		}
        		for(var i=0; i<(10-length); i++){
        			html += "<tr><td colspan='4'>&nbsp;</td></tr>";
        		}
        	}else{
        		html += "<tr><td colspan='4'>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr>";
        		html += "<tr><td colspan='4'>&nbsp;</td></tr><tr><td colspan='4'>데이터가 없습니다</td></tr><tr><td colspan='4'>&nbsp;</td></tr>";
        		html += "<tr><td colspan='4'>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr>";
        	}
        	$("#section09_1_contents > tbody").html(html);
        	       
        	paging(10, 10, data.sectionData9_1.total, nowPage, $("#section09_1_paging"), "section09_1Paging");
        }
	});
}

/*	부정 관련 정보	*/
function section09_2(nowPage, source){
	$("#s_c6_2").find(".v0").fadeIn(80);
	var param = "section=9_2&nowPage=" + nowPage + "&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&source=" + source;
	$.ajax({      
        type:"POST",  
        url:"opinionDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$("#s_c6_2").find(".v0").fadeOut(80);
        	var html = "";
        	var length = data.sectionData9_2.data.length;
        	if(length>0){
        		for(var i=0; i<length; i++){
        			var tmpData = data.sectionData9_2.data[i];
        			html += "<tr>";
        			html += "<td><span title='" + tmpData.sitename + "'>" + tmpData.sitename + "</span></td>";
        			
        			var sitecode = tmpData.sitecode;
        			var caffeImg = "";
        			if(sitecode=="3555" || sitecode=="4943"){
        				caffeImg = "<a href=\'javascript:portalSearch(\"" + sitecode + "\", \"" + urlTitle(tmpData.title) + "\")\' class=\'ui_bullet_cafe\'>Cafe</a>";
        			}
        			html += "<td class='title'>" + caffeImg + "<a href='http://hub.buzzms.co.kr?url=" + encodeURIComponent(tmpData.url) + "' target='_blank' title='" + attrTitle(tmpData.title) + "' >" + tmpData.title + "</a></td>";
        			html += "<td><span title='" + tmpData.samecnt + "'>" + tmpData.samecnt + "</span></td>";
        			html += "<td>" + tmpData.date + "</td>";
        			html += "</tr>";   			
        		}
        		for(var i=0; i<(10-length); i++){
        			html += "<tr><td colspan='4'>&nbsp;</td></tr>";
        		}
        	}else{
        		html += "<tr><td colspan='4'>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr>";
        		html += "<tr><td colspan='4'>&nbsp;</td></tr><tr><td colspan='4'>데이터가 없습니다</td></tr><tr><td colspan='4'>&nbsp;</td></tr>";
        		html += "<tr><td colspan='4'>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr>";
        	}
        	$("#section09_2_contents > tbody").html(html);
        	       
        	paging(10, 10, data.sectionData9_2.total, nowPage, $("#section09_2_paging"), "section09_2Paging");
        }
	});
}

function section09_1Paging(nowPage){
	section09_1(nowPage, $("#s_c6_1_sel_00").val());
}

function section09_2Paging(nowPage){
	section09_2(nowPage, $("#s_c6_2_sel_00").val());
}

function excelDownload(section, title){
	var url = "opinionExcelDao.jsp";
	var param = "section=" + section + "&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&title=" + title;
	
	if(section=="3"){
		param += "&pCode=" + $("#s_c2_1_sel_00").val();
	}else if(section=="6"){
		param += "&source=" + $("#s_c4_1_sel_00").val() + "&limit=" + $("#s_c4_1_sel_01").val();
	}else if(section=="7"){
		param += "&source=" + $("#sel_keyword_00_00").val() + "&limit=" + $("#sel_keyword_00_01").val() + "&senti=" + $("#sel_keyword_00_02").val();
	}else if(section=="9_1"){
		param += "&source=" + $("#s_c6_1_sel_00").val();
	}else if(section=="9_2"){
		param += "&source=" + $("#s_c6_2_sel_00").val();
	}
	
	getExcel(event, url, param, title);
}


