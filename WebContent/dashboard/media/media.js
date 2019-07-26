var scope = "";
var keyword = "";
var sDate = "";
var eDate = "";
var url = "mediaDao.jsp";

function getDvisionList(pcode,$target){
	
	$.ajax({      
	    type:"POST",  
	    url:"mediaDao.jsp?section=list",
	    data:"pcode="+pcode,
	    dataType:"json",
	    success:function(data){
	    	var html = "";
	    	if(data.length > 0){
	    		$($target).closest("div").find("label").text("전체");
	    		if(pcode == ''){
	    			html += "<option value='51' selected>전체</option>";
	    		}else{
	    			html += "<option value='5,"+pcode+"' selected>전체</option>";
	    		}	    			  
	    		for(var i=0 ; i<data.length ; i++){
	    			html += "<option value='51,"+data[i].code+"'>"+data[i].name+"</option>";	    			
	    		}
	    	}
	    	
	    	$($target).html(html);
	    }
	});
}

//온라인동향
function section01(pcode){	
	var param = "section=section01&sDate="+sDate+"&eDate="+eDate+"&scope="+scope+"&keyword="+keyword+"&pcode="+pcode;
	
	$.ajax({      
	    type:"POST",  
	    url:url,
	    data:param,
	    dataType:"json",
	    success:function(data){
	    	$(".ui_box_00").eq(0).find(".v0").fadeOut(80);
	    	if(data.length > 0){
	    		chart1.graphs[0].title = $(".select_00").eq(1).text();
	    		chart1.dataProvider = data;				
				chart1.validateData();
	    	} 	
	    }
	});
}

//채널별 성향
function section02(pcode){	
	
	var param = "section=section02&sDate="+sDate+"&eDate="+eDate+"&scope="+scope+"&keyword="+keyword+"&pcode="+pcode;
	
	$.ajax({      
	    type:"POST",  
	    url:url,
	    data:param,
	    dataType:"json",
	    success:function(data){
	    	$(".ui_box_00").eq(1).find(".v0").fadeOut(80);
	    	
	    	var total_cnt = 0;
	    	for(var i=0; i<data.length ; i++){
	    		total_cnt += data[i].column_1;
	    	}
	    	
	    	chart2.allLabels[1].text = total_cnt.addComma();
	    	chart2.dataProvider = data;				
			chart2.validateData();
			
			chart3.dataProvider = data;				
			chart3.validateData();
	    	
	    }
	});
}

//실국별 게재현황
function section03_graph(){
	
	var pcode = $("#s4_c_sel03").val();
	
	var param = "section=section03_graph&sDate="+sDate+"&eDate="+eDate+"&scope="+scope+"&keyword="+keyword+"&pcode="+pcode;
	
	$.ajax({      
	    type:"POST",  
	    url:url,
	    data:param,
	    dataType:"json",
	    success:function(data){
	    	chart4.dataProvider = data;				
			chart4.validateData();
	    }
	});	
}

function section03_info(nowPage){
	var pcode = $("#s4_c_sel03").val();
	
	var param = "section=section03_info&sDate="+sDate+"&eDate="+eDate+"&scope="+scope+"&keyword="+keyword+"&pcode="+pcode+"&nowPage="+nowPage;
	
	$.ajax({      
	    type:"POST",  
	    url:url,
	    data:param,
	    dataType:"json",
	    success:function(data){
			
			if(data.data.length > 0){
				var html = "";
				var tmpData;
				for(var i=0 ; i<data.data.length ; i++){
					tmpData = data.data[i];
					
					var caffeImg = "";
        			if(tmpData.s_seq=="3555" || tmpData.s_seq=="4943"){
        				caffeImg = "<a href=\'javascript:portalSearch(\"" + tmpData.s_seq + "\", \"" + urlTitle(tmpData.title) + "\")\' class=\'ui_bullet_cafe\'>Cafe</a>";
        			}
					html += "<tr>";
			    	html += "<td>"+tmpData.date+"</td>";
			    	html += "<td><span title='"+tmpData.site_name+"'>"+tmpData.site_name+"</span></td>";
			    	html += "<td class='title'>" + caffeImg + "<a href='http://hub.buzzms.co.kr?url=" + encodeURIComponent(tmpData.url) + "' target='_blank' title='" + attrTitle(tmpData.title) + "' >" + tmpData.title + "</a></td>";
			    	html += "<td><span title='"+tmpData.type5+"'>"+tmpData.type5+"</span></td>";
			    	html += "<td><span title='"+tmpData.type51+"'>"+tmpData.type51+"</span></td>";
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
	section03_info(nowPage);
}

function excelDownload(section, title){
	 
	var url = "mediaExcelDao.jsp";	
	var param = "section=" + section + "&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&title=" + title;
	
	if(section=="1"){
		param += "&pcode="+$("#s_sel04 option:selected").val();
	}else if(section=="2"){
		param += "&pcode="+$("#s_sel06 option:selected").val();
	}else if(section=="3"){
		param += "&pcode="+$("#s4_c_sel03 option:selected").val();
	}else if(section=="4"){
		param += "&pcode="+$("#s4_c_sel03 option:selected").val();
	}
	
	
	getExcel(event, url, param, title);
}
