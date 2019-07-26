var scope = "";
var keyword = "";
var sDate = "";
var eDate = "";
var type51 = "";
var url = "realstateDao.jsp";


function getRealstateList(){	
	$.ajax({      
	    type:"POST",  
	    url:"realstateDao.jsp?section=list1",
	    dataType:"json",
	    success:function(data){
	    	var html = "";
	    	if(data.length > 0){
	    		var checked = "";
	    		for(var i=0 ; i<data.length ; i++){
	    			html += "<li>";
	    			if(i==0){
	    				html += "<input type='radio' name='realstate01' id='tab_li_0"+i+"' value='"+data[i].code+"' checked>";
	    			}else{
	    				html += "<input type='radio' name='realstate01' id='tab_li_0"+i+"' value='"+data[i].code+"'>";
	    			}	    			
	    			html += "<div class='bg'></div>";
	    			html += "<span>"+data[i].name+"</span>";
	    			html += "<div><label for='tab_li_0"+i+"'></label></div>";
	    			html += "</li>";
	    		}
	    	}
	    	
	    	$("#realstateList").html(html);
	    }
	});
}

function getDvisionList(pcode){	
	$.ajax({      
	    type:"POST",  
	    url:"realstateDao.jsp?section=list2",
	    data:"pcode="+pcode,
	    dataType:"json",
	    success:function(data){
	    	var html = "";
	    	if(data.length > 0){
	    		var checked = "";
	    		type51 = "";
	    		html += "<option value='5,"+pcode+"' selected>전체</option>";	  
	    		for(var i=0 ; i<data.length ; i++){
	    			html += "<option value='51,"+data[i].code+"'>"+data[i].name+"</option>";
	    			
	    			if(type51 == ""){
	    				type51 = data[i].name+"@@"+data[i].code;
	    			}else{
	    				type51 += ","+data[i].name+"@@"+data[i].code;
	    			}
	    		}
	    	}
	    	
	    	$("#s_sel01").html(html);
	    	$("#s_sel03").html(html);	    	
	    }
	});
}

//온라인동향
function section01(){	
	var param = "section=section01&sDate="+sDate+"&eDate="+eDate+"&scope="+scope+"&keyword="+keyword+"&type51="+type51;
	
	$.ajax({      
	    type:"POST",  
	    url:"realstateDao.jsp",
	    data:param,
	    dataType:"json",
	    success:function(data){
	    	$(".ui_box_00").eq(0).find(".v0").fadeOut(80);
	    	
	    	if(data.length > 0){
	    		type_arr = type51.split(",");
	    		var graph_arr = [];
	    		var data_arr = [];
	    		for(var i=0 ; i<type_arr.length ; i++){
	    			var graph_obj = {};
	    			graph_obj.balloonText = "<strong>[[title]]</strong> : [[value]]";
	    			graph_obj.bullet = "round";
	    			graph_obj.bulletBorderThickness = 0;
	    			graph_obj.bulletSize = 7;
	    			graph_obj.id = "AmGraph-"+(i+1);
	    			graph_obj.lineThickness = 2;
	    			graph_obj.title = ""+type_arr[i].split("@@")[0]+"";
	    			graph_obj.valueField = "CNT_"+(i+1);
	    			graph_obj.code = ""+type_arr[i].split("@@")[1]+"";
	    			graph_arr.push(graph_obj);
	    		}	    		
	    		chart1.dataProvider = data;
				chart1.graphs = graph_arr;
				chart1.validateData();
	    	}else{
	    		
	    	}    	
	    }
	});
}

//채널별 성향
function section02(){	
	
	var code = $("#s_sel01 option:selected").val();
	
	var param = "section=section02&sDate="+sDate+"&eDate="+eDate+"&scope="+scope+"&keyword="+keyword+"&code="+code;
	
	$.ajax({      
	    type:"POST",  
	    url:"realstateDao.jsp",
	    data:param,
	    dataType:"json",
	    success:function(data){
	    	$(".ui_box_00").eq(1).find(".v0").fadeOut(80);
	    	var arr = [];
        	var pos = {};
        	var neg = {};
        	var neu = {};
        	
        	pos["category"] = "긍정";
        	pos["code"] = "1";
        	pos["column-1"] = data.pie.pos;
        	arr[0] = pos;
        	
        	neg["category"] = "부정";
        	neg["code"] = "2";
        	neg["column-1"] = data.pie.neg;
        	arr[1] = neg;
        	
        	neu["category"] = "중립";
        	neu["code"] = "3";
        	neu["column-1"] = data.pie.neu;
        	arr[2] = neu;
        	
        	chart2.allLabels[1].text = parseInt(data.pie.total).lengthLimitComma(5, 0);
        	chart2.dataProvider = arr;
        	chart2.validateData();
        	
        	chart3.dataProvider = data.graph;
        	chart3.validateData();
	    	
	    }
	});
}

function section03(nowPage){
	
	var code = $("#s_sel03 option:selected").val();
	var senti = $("#s_sel02 option:selected").val();
	
	var param = "section=section03&sDate="+sDate+"&eDate="+eDate+"&scope="+scope+"&keyword="+keyword+"&senti="+senti+"&code="+code+"&nowPage="+nowPage;
	
	$.ajax({      
	    type:"POST",  
	    url:"realstateDao.jsp",
	    data:param,
	    dataType:"json",
	    success:function(data){
	    	$(".ui_box_00").eq(2).find(".v0").fadeOut(80);
	    	if(data.data.length > 0){
	    		var html = "";
	    		
	    		for(var i=0;i<data.data.length;i++){
	    			var tmpData = data.data[i];
        			var caffeImg = "";
        			var senti_name = "";
        			if(tmpData.s_seq=="3555" || tmpData.s_seq=="4943"){
        				caffeImg = "<a href=\'javascript:portalSearch(\"" + tmpData.s_seq + "\", \"" + urlTitle(tmpData.title) + "\")\' class=\'ui_bullet_cafe\'>Cafe</a>";
        			}
        			if(tmpData.senti == "1"){
        				senti_name = "긍정";	
        			}else if(tmpData.senti == "2"){
        				senti_name = "부정";
        			}else{
        				senti_name = "중립";
        			}
	    			
	    			html += "<tr>";
	    			html += "<td>"+tmpData.date+"</td>";	    			
	    			html += "<td class='title'>" + caffeImg + "<a href='http://hub.buzzms.co.kr?url=" + encodeURIComponent(tmpData.url) + "' target='_blank' title='" + attrTitle(tmpData.title) + "' >" + tmpData.title + "</a></td>";
	    			html += "<td><span title='"+tmpData.site_name+"'>"+tmpData.site_name+"</span></td>";
	    			html += "<td><span>"+senti_name+"</span></td>";
	    			html += "<td><span title='"+tmpData.name+"'>"+tmpData.name+"</span></td>";
	    			html += "</tr>";
	    			
	    		}
	    		for(var i=0; i<(10-data.data.length); i++){
        			html += "<tr><td colspan='5'>&nbsp;</td></tr>";
        		}
	    	}else{
	    		html += "<tr><td colspan='5'>&nbsp;</td></tr><tr><td colspan='5'>&nbsp;</td></tr><tr><td colspan='5'>&nbsp;</td></tr>";
        		html += "<tr><td colspan='5'>&nbsp;</td></tr><tr><td colspan='5'>데이터가 없습니다</td></tr><tr><td colspan='5'>&nbsp;</td></tr>";
        		html += "<tr><td colspan='5'>&nbsp;</td></tr><tr><td colspan='5'>&nbsp;</td></tr><tr><td colspan='5'>&nbsp;</td></tr><tr><td colspan='5'>&nbsp;</td></tr>";
	    	}   
	    	$("#section03_list").empty().html(html);
	    	paging(10, 10, data.total_cnt, nowPage, $("#section03_Paging"), "section03_Paging");
	    }
	});
	
}

function section03_Paging(nowPage){
	section03(nowPage);
}

function excelDownload(section, title){
			 
	var url = "realstateExcelDao.jsp";	
	var param = "section=" + section + "&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&title=" + title;
	if(section=="1"){
		param += "&type51="+type51;
	}else if(section=="2"){
		param += "&code="+$("#s_sel06 option:selected").val();
	}else{
		param += "&senti="+$("#s_sel02 option:selected").val()+"&code="+$("#s_sel03 option:selected").val();
	}
	
	getExcel(event, url, param, title);
}

