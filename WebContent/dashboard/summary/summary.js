var scope = "";
var keyword = "";
var tier = "";
var sDate = "";
var eDate = "";
var DOC_ID = "";
var url = "summaryDao.jsp";

/*	온라인 동향	*/
function section01(){
	var param = "section=1&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate+"&tier="+tier;
	$.ajax({      
        type:"POST",  
        url:"summaryDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$(".ui_box_00").eq(0).find(".v0").fadeOut(80);
        	chart1.dataProvider = data.sectionData1;
        	chart1.validateData();
        }
	});
}

/*	주요 시정 채널 별 성향	*/
function section02(){
	var param = "section=2&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate+"&tier="+tier;
	$.ajax({      
        type:"POST",  
        url:"summaryDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$(".ui_box_00").eq(1).find(".v0").fadeOut(80);
        	var arr = [];
        	var pos = {};
        	var neg = {};
        	var neu = {};
        	
        	pos["category"] = "긍정";
        	pos["column-1"] = data.sectionData2.data1[0].pos;
        	arr[0] = pos;
        	
        	neg["category"] = "부정";
        	neg["column-1"] = data.sectionData2.data1[0].neg;
        	arr[1] = neg;
        	
        	neu["category"] = "중립";
        	neu["column-1"] = data.sectionData2.data1[0].neu;
        	arr[2] = neu;
        	
        	chart2.allLabels[1].text = parseInt(data.sectionData2.data1[0].cnt).lengthLimitComma(5, 0);
        	chart2.dataProvider = arr;
        	chart2.validateData();
        	
        	chart3.dataProvider = data.sectionData2.data2;
        	chart3.validateData();
        }
	});
}

/*	포탈 TOP 노출 현황	*/
function section04(nowPage){
	//var tab = $("[name=s_c6_1_radio]:checked").val();
	var tab = 1;
	
	var param = "section=4&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&tab=" + tab;
	$.ajax({      
        type:"POST",  
        url:"summaryDao.jsp?nowPage="+nowPage,
        data:param,
        dataType:"json",
        success:function(data){
        	$(".ui_box_00").eq(2).find(".v0").fadeOut(80);
        	var html = "";
        	var totalCnt = data.sectionData4.totalCnt;
        	var length = data.sectionData4.list.length;
        	if(length>0){
        		$("#section04_contents > tbody").empty();
        		for(var i=0; i<length; i++){
        			var tmpData = data.sectionData4.list[i];
        			html += "<tr>";
        			html += "<td><span title='" + tmpData.sitename + "'>" + tmpData.sitename + "</span></td>";
        			html += "<td><span title='" + tmpData.boardname + "'>" + tmpData.boardname + "</span></td>";
        			var sitecode = tmpData.sitecode;
        			var caffeImg = "";
        			if(sitecode=="3555" || sitecode=="4943"){
        				caffeImg = "<a href=\'javascript:portalSearch(\"" + sitecode + "\", \"" + urlTitle(tmpData.title) + "\")\' class=\'ui_bullet_cafe\'>Cafe</a>";
        			}
        			html += "<td class='title'>" + caffeImg + "<a href='http://hub.buzzms.co.kr?url=" + encodeURIComponent(tmpData.url) + "' target='_blank' title='" + attrTitle(tmpData.title) + "' >" + tmpData.title + "</a></td>";
        			html += "<td>" + tmpData.date + "</td>";
        			html += "</tr>";   			
        		}
        		for(var i=0; i<(5-length); i++){
        			html += "<tr><td colspan='4'>&nbsp;</td></tr>";
        		}
        	}else{
        		html += "<tr><td colspan='4'>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr>";
        		html += "<tr><td colspan='4'>데이터가 없습니다</td></tr>";
        		html += "<tr><td colspan='4'>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr>";
        	}
        	$("#section04_contents > tbody").html(html);
        	
        	paging(10, 5, totalCnt, nowPage, $("#top_paging"), "section04");
        }
	});
}

/*	소셜미디어 이슈 리스트	*/
function section06(nowPage){
	$(".ui_box_00").eq(6).find(".v0").fadeIn(80);
	var param = "section=6&nowPage=" + nowPage + "&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate;
	$.ajax({      
        type:"POST",  
        url:"summaryDao.jsp",
        data:param,
        dataType:"json",
        success:function(data){
        	$(".ui_box_00").eq(6).find(".v0").fadeOut(80);
        	var html = "";
        	var length = data.sectionData6.data.length;
        	if(length>0){
        		for(var i=0; i<length; i++){
        			var tmpData = data.sectionData6.data[i];
        			html += "<tr>";
        			html += "<td><span title='" + tmpData.sitename + "'>" + tmpData.sitename + "</span></td>";
        			var sitecode = tmpData.sitecode;
        			var caffeImg = "";
        			if(sitecode=="3555" || sitecode=="4943"){
        				caffeImg = "<a href=\'javascript:portalSearch(\"" + sitecode + "\", \"" + urlTitle(tmpData.title) + "\")\' class=\'ui_bullet_cafe\'>Cafe</a>";
        			}
        			html += "<td class='title'>" + caffeImg + "<a href='http://hub.buzzms.co.kr?url=" + encodeURIComponent(tmpData.url) + "' target='_blank' title='" + attrTitle(tmpData.title) + "' >" + tmpData.title + "</a></td>";
        			html += "<td><span>" + parseInt(tmpData.samecnt).addComma() + "</span></td>";
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
        	$("#section06_contents > tbody").html(html);
        	
        	paging(10, 10, data.sectionData6.total, nowPage, $("#section06_paging"), "section06Paging");
        }
	});
}

function section06Paging(nowPage){
	section06(nowPage);
}

function excelDownload(section, title){
	
	var tab ="";
	var portal_date = "";
	if(section == "4"){
		tab ="1";
	}else if(section == "5_1") {
		tab ="1";
		portal_date = $("#c_15_dp_00").val();
	}
	 
	var url = "summaryExcelDao.jsp";
	var param = "section=" + section + "&scope=" + scope + "&keyword=" + keyword + "&sDate=" + sDate + "&eDate=" + eDate + "&title=" + title+"&docid="+DOC_ID+"&tab="+tab+"&portal_date="+portal_date+"&tier="+tier;
	
	if(section == "5_2"){
		param += "&pos_cnt="+chart6.chartData[0].value+"&neg_cnt="+chart6.chartData[1].value+"&neu_cnt="+chart6.chartData[2].value;
	}
	
	getExcel(event, url, param, title);
}


var summary = {
		
		search : function(){
			
		},
		
		//포탈 댓글 검색
		portalSearch : function( nowpage ){
			$(".ui_box_00").eq(4).find(".v0").fadeIn(80);
			$(".ui_box_00").eq(5).find(".v0").fadeIn(80);
			var type = 4;
			var portal_date = $("#c_15_dp_00").val();
			//var tab = $("[name=s_c6_2_radio]:checked").val();
			var tab = 1;
			
			$.post(url, {portal_date:portal_date, type:type, nowpage:nowpage, scope:scope, keyword:keyword, tab:tab}, function(data){
				$("#portal_reply_loader").fadeOut(80);

				var jsonObj = JSON.parse(data);
				var dataSize = jsonObj.section4.list.length;
				var html = "";

				var pos = 0;
				var neg = 0;
				var neu = 0;
				var pdoc_id = "";
				var title = "";
				
				if(dataSize > 0){
					$(jsonObj.section4.list).each(function(index){
						var $this = jsonObj.section4.list[index];
						
						if(index == 0){//리스트의 첫번째 항목의 긍정,부정,중립 수치를 저장
							pos = $this.POS_CNT;		//긍정
							neg = $this.NEG_CNT;     //부정
							neu = $this.NEU_CNT;     //증립
							pdoc_id = $this.D_SEQ;	//원문번호
							DOC_ID = $this.D_SEQ;	//원문번호
							title = "["+$this.MD_SITE_NAME+"] "+$this.MD_TITLE
						}
						var tt = "";
						if(index == 0){
							tt = "\'["+$this.MD_SITE_NAME+"] "+$this.MD_TITLE.replace(/"/gi, "&#34;").replaceAll("'", "")+"\'";
							html += '<tr class="active">';	
						}else{
							tt = "\'["+$this.MD_SITE_NAME+"] "+$this.MD_TITLE.replace(/"/gi, "&#34;").replaceAll("'", "")+"\'";
							html += '<tr class="" >';
						}
						var parammeters =  "'"+$this.D_SEQ+"',"+"'"+portal_date.replace(/-/gi, '')+"',"+"'"+$this.DOC_CNT+"',1,'',''";

						var tl = $this.MD_TITLE.replace(/"/gi, "&#34;");
						html += '<td onclick="summary.getDetailInfo(this,\''+$this.D_SEQ+'\','+$this.POS_CNT+','+$this.NEG_CNT+','+$this.NEU_CNT+','+tt+');" style="cursor:pointer;"><span>'+$this.MD_SITE_NAME+'</span></td>';
						if( $this.API_REQUEST == "B"){
							html += '<td class="r_btn_cell"><a href="javascript:summary.getReplyPopUp('+parammeters+');">'+$this.DOC_CNT+'</a> (<span class="ui_fc_blue" title="긍정 :'+$this.POS_CNT+'">'+$this.POS_CNT+'</span>/<span class="ui_fc_red" title="부정 : '+$this.NEG_CNT+'">'+$this.NEG_CNT+'</span>/<span class="ui_fc_green" title="중립 : '+$this.NEU_CNT+'">'+$this.NEU_CNT+'</span>)<button type="button" onclick="summary.portalreply_api(this, '+$this.D_SEQ+');" class="ui_btn_02 small" title="갱신"><span class="icon restore">댓글갱신</span></button><div class="ui_loader v0 mini"><span class="loader">Load</span></div></td>';							
						}else{
							html += '<td class="r_btn_cell"><a href="javascript:summary.getReplyPopUp('+parammeters+');">'+$this.DOC_CNT+'</a> (<span class="ui_fc_blue" title="긍정 :'+$this.POS_CNT+'">'+$this.POS_CNT+'</span>/<span class="ui_fc_red" title="부정 : '+$this.NEG_CNT+'">'+$this.NEG_CNT+'</span>/<span class="ui_fc_green" title="중립 : '+$this.NEU_CNT+'">'+$this.NEU_CNT+'</span>)<button type="button" onclick="summary.portalreply_api(this, '+$this.D_SEQ+');" class="ui_btn_02 small" disabled title="갱신"><span class="icon restore">댓글갱신</span></button><div class="ui_loader v0 mini"><span class="loader">Load</span></div></td>';
						}
						
						html += '<td class="title"><a href="http://hub.buzzms.co.kr?url='+$this.MD_URL+'" target="_blank" title="'+tl+'" >'+$this.MD_TITLE+'</a></td>';
						html += '<td>'+$this.MD_DATE+'</td>';
						html += '</tr>';
					});
					
					if(dataSize < 10){
						for(var i=dataSize; i < 10; i++){
							html += '<tr><td td colspan="4"> </td></tr>';
						}
					}
				}
				
				$("#portal_tbody").empty().append(html);
				
				paging(10, 10, jsonObj.section4.total, nowpage, $("#section04_paging"), "summary.section04Paging");
				
				//기사 댓글 분석 - 제목 변경
				summary.titleChange(title);
				
				//기사 댓글 분석- 파이 차트 변경
				summary.chartDraw(pos, neg, neu);
				
				//기사 댓글 분석- 연관 클라우드 변경
				summary.networkChartForKeyword(pdoc_id);
				
			});
		},
		
		//포탈댓글 페이징
		section04Paging : function(nowPage){
			$(".ui_box_00").eq(4).find(".v0").fadeIn(80);
			summary.portalSearch(nowPage); 
		},
		
		//기사 댓글 분석 - 연관어 클라우드
		networkChartForKeyword : function(p_docid){
			
			WordCloud();
			
			$(".ui_box_00").eq(5).find(".v0").fadeIn(80);
			var type = 5;
			var totalCnt = 0;
			var portal_date = $("#c_15_dp_00").val();	//포탈 댓글 검색 날짜
			$.post(url, {p_docid:p_docid, portal_date:portal_date, type:type}, function(data){
				$(".ui_box_00").eq(5).find(".v0").fadeOut(80);				
				var jsonObj = JSON.parse(data);
				var length = jsonObj.section5.length;
            	if(length>0){
            		
            		$(".ui_jqw_cloud").empty();
            		
            		$(".ui_jqw_cloud").append("<div id='tag_cloud_02_04_01' class='wrap'></div>")
            		
            		$("#tag_cloud_02_04_01").removeClass("no_data");
            		
            		var words = [];
            		var weight = 33000;
            		for(var i=0; i<length; i++){
            			var tmpObj = {};
            			tmpObj.word = jsonObj.section5[i].word_nm;
            			tmpObj.weight = weight;
            			tmpObj.doc_id = DOC_ID;
            			tmpObj.date = portal_date;
            			tmpObj.pop_word_name = jsonObj.section5[i].word_nm;            			
            			
            			if(jsonObj.section5[i].attribute == "긍정" ){
            				tmpObj.color = "#5ba1e0";            					
            			}else if(jsonObj.section5[i].attribute == "부정" ){
            				tmpObj.color = "#ea7070";
            			}else{
            				tmpObj.color = "#888888";
            			}
            			words[i] = tmpObj;
            			
            			if(weight == 0){
            				weight = 0;
            			}else{
            				weight = weight - 3000;
            			}
            		}
            		
            		var opt = {words:words};
            		$("#tag_cloud_02_04_01").css('transform','');
            		$('#tag_cloud_02_04_01').jqw_cloud(opt);
            		
            		$("#tag_cloud_02_04_01").click(function(e){
            			
            			var chk = words[$(e.target).index()];
            			
            			summary.getReplyPopUp(chk.doc_id,chk.date,0,1,'',chk.pop_word_name);
            		});
            		
            	}else{
            		$("#tag_cloud_02_04_01").css('transform','');
            		$("#tag_cloud_02_04_01").addClass("no_data");
            	}
				
				/*var sentiCode = "";
				var html ="";
				html += "<div class=\"line_area\"></div>";
				var jsonObj = JSON.parse(data);
				var dataSize = jsonObj.section5.length;
				if(dataSize > 0){
					$(jsonObj.section5).each(function(index){
						
						var senti = jsonObj.section5[index].attribute;
						var setiClassName = "";
						
						if(senti == "긍정"){
							setiClassName = "postive";
							sentiCode ="1";
						}else if(senti == "부정"){
							setiClassName = "negative";
							sentiCode ="2";
						}else{
							setiClassName = "neutral";
							sentiCode ="3";
						}
						
						totalCnt += jsonObj.section5[index].cnt;
						
						html += "<div class=\"item\ "+setiClassName+" \"><a href=\"#\" onclick=\"summary.getReplyPopUp("+DOC_ID+",'"+portal_date+"',0,1,'','"+jsonObj.section5[index].word_nm+"');return false;\"><strong>"+jsonObj.section5[index].word_nm+"</strong><span class=\"dv\">"+"총 "+jsonObj.section5[index].cnt+"건"+"</span></a></div>";
					});
				}
				
				html += "<div class=\"all\"><a href=\"#\" onclick=\"return false;\"><strong class=\"v2\">대구 광역시</strong><span class=\"dv\">총 "+totalCnt+"건</span></a></div>";
				
				$("#spider_00").empty().html(html);
				$( "#spider_00" ).ui_spider();*/
				
				
				
			});
		},
		
		//댓글 팝업창
		getReplyPopUp : function(p_docid, p_date, totalCnt, popPage, r_trnd, r_relation_word){
			popupOpen( "../popup/pop_portal_reply.jsp?p_date="+p_date+"&doc_id="+p_docid+"&totalCnt="+totalCnt+"&popPage="+popPage+"&r_trnd="+r_trnd+"&r_relation_word="+r_relation_word);
		},
		
		//댓글 분석 이벤트
		getDetailInfo : function($this, p_docid , pos, neg, neu, title){
			DOC_ID = p_docid;
			
			$($this).parent().parent().find("tr").removeClass("active");
			$($this).parent().addClass("active");
			
			summary.titleChange(title);
			
			$(".ui_box_00").eq(3).find(".v0").fadeIn(80);
			summary.chartDraw(pos, neg, neu);
			
			//$(".ui_box_00").eq(6).find(".v0").fadeIn(80);
			summary.networkChartForKeyword(p_docid);
		},
		
		//기사댓글 분석 - 파이차트
		chartDraw : function(pos, neg, neu){
			$(".ui_box_00").eq(3).find(".v0").fadeOut(80);
			
			var jObject = [{"category":"긍정",column1:pos},{"category":"부정",column1:neg},{"category":"중립",column1:neu}];
			chart6.allLabels[1].text = parseInt(Number(pos)+Number(neg)+Number(neu)).lengthLimitComma(5, 0);
			chart6.dataProvider = jObject;
			chart6.validateData();
		},
		
		//기사댓글 분석 - 기사 타이틀
		titleChange : function(title){
			$("#article_reply_ana").empty().text(title);	
		},
		
		//댓글 즉시 수집 요청 API
		portalreply_api : function($target , p_docid){
			
			$($target).parent().find("div.ui_loader").fadeIn(80);
			
			$.ajax({      
		        type:"POST",  
		        url:"summaryDao.jsp",
		        data:{type:6 , p_docid:p_docid},
		        dataType:"json",
		        success:function(data){
		        	$($target).parent().find("div.ui_loader").fadeOut(80);
		        	alert( data.msg );
		        	$($target).attr("disabled","disabled");
		        }
			});
		}
		
}


String.prototype.replaceAll = function(org, dest) {
    return this.split(org).join(dest);
}