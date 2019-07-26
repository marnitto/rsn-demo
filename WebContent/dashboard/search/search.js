var global_keyword01_1 = "";
var global_keyword01_2 = "";
var global_keyword01_3 = "";
var global_keyword01_4 = "";

var global_keyword02_1 = "";
var global_keyword02_2 = "";
var global_keyword02_3 = "";
var global_keyword02_4 = "";

var global_sDate = ""; 
var global_eDate = "";

var global_channel = "";

/*	실시간 검색  데이터	*/
function section01(){
	var param = "section=1";
	$.ajax({      
        type:"POST",  
        url:"searchDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$(".ui_box_00").find(".v0").eq(0).fadeOut(80);
        	$(".ui_box_00").find(".v0").eq(1).fadeOut(80);
        	
        	var html1 = "";
        	var html2 = "";
    		for(var i=0; i<10; i++){
    			var tmpData1 = data.sectionData1.naver[i];
    			var tmpData2 = data.sectionData1.daum[i];
    			html1 += "<tr><td>" + (i+1) + "</td><td><a href='#' onclick='$(\"#searchs_input_01_01\").val(\"" + tmpData1.keyword + "\"); $(\"#searchs_input_02_01\").val(\"\"); hndl_search();'><strong>" + tmpData1.keyword + "</strong></a></td></tr>";
    			html2 += "<tr><td>" + (i+1) + "</td><td><a href='#' onclick='$(\"#searchs_input_01_01\").val(\"" + tmpData2.keyword + "\"); $(\"#searchs_input_02_01\").val(\"\"); hndl_search();'><strong>" + tmpData2.keyword + "</strong></a></td></tr>";
    		}		
        	$("#section01_1_contents > tbody").html(html1);
        	$("#section01_2_contents > tbody").html(html2);
        }
	});	
}

/*	정보검색	*/
function section02(){
	var param = "section=2&" + getKeywordParam(0);
	$.ajax({      
        type:"POST",  
        url:"searchDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$(".ui_box_00").find(".v0").eq(2).fadeOut(80);
        	
        	chart1.colors = JSON.parse(JSON.stringify(chart1_colors));
        	chart1.graphs = JSON.parse(JSON.stringify(chart1_graphs));
        	
        	var firstKeywordLength = data.sectionData2.firstKeyword.length;
        	var secondKeywordLength = data.sectionData2.secondKeyword.length;
        	
        	if(firstKeywordLength==0){
        		chart1.colors.splice(0, 1);
        		chart1.graphs.splice(0, 1);
        		chart1.dataProvider = data.sectionData2.secondKeyword;
        		chart1.graphs[0].title = getKeyword(2);
        		
        	}else if(secondKeywordLength==0){
        		chart1.colors.splice(1, 1);
        		chart1.graphs.splice(1, 1);
        		chart1.dataProvider = data.sectionData2.firstKeyword;
        		chart1.graphs[0].title = getKeyword(1);
        		
        	}else{
        		for(var i=0; i<firstKeywordLength; i++){
            		data.sectionData2.firstKeyword[i]["column-2"] = data.sectionData2.secondKeyword[i]["column-2"];
            	}
        		chart1.dataProvider = data.sectionData2.firstKeyword;
        		chart1.graphs[0].title = getKeyword(1);
        		chart1.graphs[1].title = getKeyword(2);
        	}
        	chart1.validateData();
        }
	});	
}

/*	감성현황(정보 분포) */
function section03_1(position, params){
	var param = "section=3_1&" + params;	
	$.ajax({
        type:"POST",  
        url:"searchDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	if(position=="left"){
        		$(".ui_box_00").find(".v0").eq(3).fadeOut(80);
        		
	        	//왼쪽차트
	        	var total = data.sectionData3_1.leftData[0]["column-1"] + data.sectionData3_1.leftData[1]["column-1"] + data.sectionData3_1.leftData[2]["column-1"];
	        	chart2.allLabels[1].text = total.lengthLimitComma(5, 0);
	        	chart2.dataProvider = data.sectionData3_1.leftData;
	        	chart2.validateData();

	        	//오른쪽차트
	        	chart3.dataProvider = data.sectionData3_1.rightData;
	        	chart3.validateData();
	        	
        	}else if(position=="right"){
        		$(".ui_box_00").find(".v0").eq(4).fadeOut(80);
        		
	        	//왼쪽차트
	        	var total = data.sectionData3_1.leftData[0]["column-1"] + data.sectionData3_1.leftData[1]["column-1"] + data.sectionData3_1.leftData[2]["column-1"];	
	        	chart4.allLabels[1].text = total.lengthLimitComma(5, 0);
	        	chart4.dataProvider = data.sectionData3_1.leftData;
	        	chart4.validateData();
	        	
	        	//오른쪽차트
	        	chart5.dataProvider = data.sectionData3_1.rightData;
	        	chart5.validateData();
        	}
        }
	});	
}

/*	감성현황(감성 추이) */
function section03_2(position, params){
	var param = "section=3_2&" + params;	
	$.ajax({
        type:"POST",  
        url:"searchDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	if(position=="top"){
        		$(".ui_box_00").find(".v0").eq(5).fadeOut(80);
        		chart6.dataProvider = data.sectionData3_2;
            	chart6.validateData();
            	
        	}else if(position=="bottom"){
        		$(".ui_box_00").find(".v0").eq(6).fadeOut(80);
        		chart7.dataProvider = data.sectionData3_2;
            	chart7.validateData();
        	}
        }
	});	
}

/*	감성현황(채널별 추이) */
function section03_3(position, params){
	var param = "section=3_3&" + params;	
	$.ajax({
        type:"POST",  
        url:"searchDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	var tmpChart89_graphs = JSON.parse(JSON.stringify(chart89_graphs));
        	var tmpChart89_colors = JSON.parse(JSON.stringify(chart89_colors));
        	var channel = global_channel.split(" ");
        	
    		var i = 0;
    		while(i<tmpChart89_graphs.length){
    			var chk = false;
    			for(var j=0; j<channel.length; j++){
            		if(tmpChart89_graphs[i].valueField==channel[j]) chk = true;
        		}
    			if(chk==false){
    				tmpChart89_colors.splice(i, 1);
    				tmpChart89_graphs.splice(i, 1);
    				i = 0;
    				continue;
    			}
    			i++;
    		}
    		
        	if(position=="top"){
        		$(".ui_box_00").find(".v0").eq(7).fadeOut(80);
        		chart8.colors = JSON.parse(JSON.stringify(tmpChart89_colors));
        		chart8.graphs = JSON.parse(JSON.stringify(tmpChart89_graphs));
        		chart8.dataProvider = data.sectionData3_3;
            	chart8.validateData();
            	
        	}else if(position=="bottom"){
        		$(".ui_box_00").find(".v0").eq(8).fadeOut(80);
        		chart9.colors = JSON.parse(JSON.stringify(tmpChart89_colors));
        		chart9.graphs = JSON.parse(JSON.stringify(tmpChart89_graphs));
        		chart9.dataProvider = data.sectionData3_3;
            	chart9.validateData();
        	}
        }
	});	
}

/*	연관키워드 */
function section04(position, op, limit){
	$("#tag_cloud_01").addClass("no_data");
	$("#tag_cloud_02").addClass("no_data");
	
	var param = "";
	var encodeParam = "";
	if(op=="1"){
		param = "section=4&" + getKeywordParam(1) + "&limit=" + limit;
		encodeParam = "i_sdate=" + global_sDate.replace(/-/gi, "") + "&i_edate=" + global_eDate.replace(/-/gi, "") + "&i_sourcetype=" + global_channel
		+ "&i_and_text=" + encodeURI(global_keyword01_1, "UTF-8") + "&i_exact_text=" + encodeURI(global_keyword01_2, "UTF-8")
		+ "&i_or_text=" + encodeURI(global_keyword01_3, "UTF-8") + "&i_exclude_text=" + encodeURI(global_keyword01_4, "UTF-8") + "&limit=" + limit;
	}else if(op=="2"){
		param = "section=4&" + getKeywordParam(2) + "&limit=" + limit;
		encodeParam = "i_sdate=" + global_sDate.replace(/-/gi, "") + "&i_edate=" + global_eDate.replace(/-/gi, "") + "&i_sourcetype=" + global_channel
		+ "&i_and_text=" + encodeURI(global_keyword02_1, "UTF-8") + "&i_exact_text=" + encodeURI(global_keyword02_2, "UTF-8")
		+ "&i_or_text=" + encodeURI(global_keyword02_3, "UTF-8") + "&i_exclude_text=" + encodeURI(global_keyword02_4, "UTF-8") + "&limit=" + limit;
	}
	
	$.ajax({      
        type:"POST",  
        url:"searchDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	if(position=="left"){
        		$(".ui_box_00").find(".v0").eq(9).fadeOut(80);
        		$(".ui_box_00").find(".v0").eq(11).fadeOut(80);
            	var length = data.sectionData4.length;
            	if(length>0){
            		$("#tag_cloud_01").removeClass("no_data");
            		var words = [];
            		var weight = 35;
            		for(var i=0; i<length; i++){
            			var tmpObj = {};
            			tmpObj.text = data.sectionData4[i].word;
            			tmpObj.weight = weight;
            			tmpObj.link = "javascript:popupOpen('../popup/rel_info_lucy.jsp','" + encodeParam + "&I_relation_word=" + encodeURI(data.sectionData4[i].word, "UTF-8") + "')";
            			words[i] = tmpObj;
            			weight = weight - 1;
            		}
            		$('#tag_cloud_01').jQCloud('destroy');
            		$("#tag_cloud_01").jQCloud(words, {
        				autoResize: true
        			});
            	}else{
            		$("#tag_cloud_01").addClass("no_data");
            	}
            }else if(position=="right"){
            	$(".ui_box_00").find(".v0").eq(10).fadeOut(80);
            	$(".ui_box_00").find(".v0").eq(11).fadeOut(80);
	        	var length = data.sectionData4.length;
	        	if(length>0){
	        		$("#tag_cloud_02").removeClass("no_data");
	        		var words = [];
	        		var weight = 35;
	        		for(var i=0; i<length; i++){
	        			var tmpObj = {};
	        			tmpObj.text = data.sectionData4[i].word;
	        			tmpObj.weight = weight;
	        			tmpObj.link = "javascript:popupOpen('../popup/rel_info_lucy.jsp','" + encodeParam + "&I_relation_word=" + encodeURI(data.sectionData4[i].word, "UTF-8") + "')";
	        			words[i] = tmpObj;
	        			weight = weight - 1;
	        		}
	        		$('#tag_cloud_02').jQCloud('destroy');
	        		$("#tag_cloud_02").jQCloud(words, {
	    				autoResize: true
	    			});
	        	}else{
	        		$("#tag_cloud_02").addClass("no_data");
	        	}
            }
        }
   });
}

/*	관련정보	*/
function section05(nowPage, params){
	$(".ui_box_00").find(".v0").eq(12).fadeIn(80);
	var param = "section=5&" + params + "&nowPage=" + nowPage;	
	$.ajax({      
        type:"POST",  
        url:"searchDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$(".ui_box_00").find(".v0").eq(12).fadeOut(80);
        	$("#section05_contents").find(".no_data").removeClass("no_data");
        	
        	var totalPage = data.sectionData5.total / 10;
        	totalPage = Math.ceil(totalPage);
        	
        	$("#s_c6_1 > .title_rc > span").html("<strong><span class='ui_bullet_02'>-</span>총 " + parseInt(data.sectionData5.total).addComma() + "건</strong>, " + parseInt(nowPage).addComma() + "/" + parseInt(totalPage).addComma() + " pages");
        
        	var html = "";
        	var length = data.sectionData5.data.length;
        	if(length>0){
        		for(var i=0; i<length; i++){
        			var tmpData = data.sectionData5.data[i];
        			html += "<tr>";
        			html += "<td><strong title='" + tmpData.sitename + "'>" + tmpData.sitename + "</strong></td>";
        			
        			var sitecode = tmpData.sitecode;
        			var caffeImg = "";
        			if(sitecode=="3555" || sitecode=="4943"){
        				caffeImg = "<a href=\'javascript:portalSearch(\"" + sitecode + "\", \"" + urlTitle(tmpData.title) + "\")\' class=\'ui_bullet_cafe\'>Cafe</a>";
        			}
        			
        			html += "<td class='title'>" + caffeImg + "<a href='http://hub.buzzms.co.kr?url=" + encodeURIComponent(tmpData.url) + "' target='_blank' title='" + attrTitle(tmpData.title) + "' >" + tmpData.title + "</a></td>";
        			html += "<td>" + tmpData.date + "</td>";
        			html += "</tr>";   			
        		}
        		for(var i=0; i<(10-length); i++){
        			html += "<tr><td colspan='3'>&nbsp;</td></tr>";
        		}
        	}else{
        		html += "<tr><td colspan='3' class='no_data' style='height:330px'></td></tr>";
        	}
        	$("#section05_contents > tbody").html(html);
        	
        	paging(10, 10, data.sectionData5.total, nowPage, $("#section05_paging"), "section05Paging");
        }
	});	
}

function section05Paging(nowPage){
	var op = $("input[name=ri_radio]:checked").val();
	if(op==undefined){
		if(getKeyword(2)==""){			 	// 검색키워드1 만 입력한 경우 
			section05(nowPage, getKeywordParam(1));
		}else if(getKeyword(1)==""){		// 검색키워드2 만 입력한 경우 
			section05(nowPage, getKeywordParam(2));
		}
	}else{
		section05(nowPage, getKeywordParam(op));
	}
}

/*	검색 파라미터를 반환	*/
function getKeywordParam(op){
	var param = "";
	if(op=="0"){
		param = "i_sdate=" + global_sDate.replace(/-/gi, "") + "&i_edate=" + global_eDate.replace(/-/gi, "") + "&i_sourcetype=" + global_channel
			+ "&i_and_text=" + global_keyword01_1 + "&i_exact_text=" + global_keyword01_2 + "&i_or_text=" + global_keyword01_3 + "&i_exclude_text=" + global_keyword01_4
			+ "&i_and_text2=" + global_keyword02_1 + "&i_exact_text2=" + global_keyword02_2 + "&i_or_text2=" + global_keyword02_3 + "&i_exclude_text2=" + global_keyword02_4;
	}else if(op=="1"){
		param = "i_sdate=" + global_sDate.replace(/-/gi, "") + "&i_edate=" + global_eDate.replace(/-/gi, "") + "&i_sourcetype=" + global_channel
		+ "&i_and_text=" + global_keyword01_1 + "&i_exact_text=" + global_keyword01_2 + "&i_or_text=" + global_keyword01_3 + "&i_exclude_text=" + global_keyword01_4;
	}else if(op=="2"){
		param = "i_sdate=" + global_sDate.replace(/-/gi, "") + "&i_edate=" + global_eDate.replace(/-/gi, "") + "&i_sourcetype=" + global_channel
		+ "&i_and_text=" + global_keyword02_1 + "&i_exact_text=" + global_keyword02_2 + "&i_or_text=" + global_keyword02_3 + "&i_exclude_text=" + global_keyword02_4;
	}
	return param;
}

/*	네개의 키워드 중 화면에 표시 할 대표 키워드를 반환	*/
function getKeyword(op){
	var keyword = "";
	if(op=="1"){
		if(global_keyword01_1!=""){
			keyword = global_keyword01_1;
		}else if(global_keyword01_2!=""){
			keyword = global_keyword01_2;
		}else if(global_keyword01_3!=""){
			keyword = global_keyword01_3;
		}
	}else if(op=="2"){
		if(global_keyword02_1!=""){
			keyword = global_keyword02_1;
		}else if(global_keyword02_2!=""){
			keyword = global_keyword02_2;
		}else if(global_keyword02_3!=""){
			keyword = global_keyword02_3;
		}
	}
	return keyword;
}

function excelDownload(section, title){
	var keyword1 = getKeyword(1);
	var keyword2 = getKeyword(2);
	
	var url = "searchExcelDao.jsp";
	var param = getKeywordParam(0) + "&title=" + title;
	
	if(section=="2"){
		param += "&section=" + section + "&keyword1=" + keyword1 + "&keyword2=" + keyword2;
		
	}else if(section=="3_s1_1"){	
		if(getKeyword(1)==""){
			param += "&section=3_s1_2&keyword=" + keyword2;
		}else{
			param += "&section=" + section + "&keyword=" + keyword1;
		}
		
	}else if(section=="3_s1_2"){
		param += "&section=" + section + "&keyword=" + keyword2;
		
	}else if(section=="3_s2_1"){	
		if(getKeyword(1)==""){
			param += "&section=3_s2_2&keyword=" + keyword2;
		}else{
			param += "&section=" + section + "&keyword=" + keyword1;
		}
		
	}else if(section=="3_s2_2"){
		param += "&section=" + section + "&keyword=" + keyword2;
		
	}else if(section=="3_s3_1"){	
		if(getKeyword(1)==""){
			param += "&section=3_s3_2&keyword=" + keyword2;
		}else{
			param += "&section=" + section + "&keyword=" + keyword1;
		}
		
	}else if(section=="3_s3_2"){
		param += "&section=" + section + "&keyword=" + keyword2;
		
	}else if(section=="4_1"){	
		if(getKeyword(1)==""){
			param += "&section=4_2&keyword=" + keyword2 + "&limit=100";
		}else if(getKeyword(2)==""){
			param += "&section=4_1&keyword=" + keyword1 + "&limit=100";
		}else{
			param += "&section=" + section + "&keyword=" + keyword1 + "&limit=30";
		}
		
	}else if(section=="4_2"){
		param += "&section=" + section + "&keyword=" + keyword2 + "&limit=30";
	
	}else if(section=="5"){
		var op = $("input[name=ri_radio]:checked").val();
		if(op!=undefined){
			if(op=="1"){
				param += "&section=5_1&keyword=" + keyword1;
			}else if(op=="2"){
				param += "&section=5_2&keyword=" + keyword2;
			}
		}else{
			if(getKeyword(1)==""){	
				param += "&section=5_2&keyword=" + keyword2;
			}else if(getKeyword(2)==""){
				param += "&section=5_1&keyword=" + keyword1;
			}
		}
		
	}
	getExcel(event, url, param, title);
}

