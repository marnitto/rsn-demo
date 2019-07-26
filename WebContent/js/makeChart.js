function makeChart(){}

makeChart.gaugeChart = function(dataInfo, target){
	var chart = new Highcharts.Chart({
		
	    chart: {
	        renderTo: target,
	        type: 'gauge',
	        plotBorderWidth: 0
	    },
	    title: {
	        text: ''
	    },
	    pane: [{
	        startAngle: -90,
	        endAngle: 90,
	        background: null,
	        center: ['50%', '100%'],
	        size: 137
	    }],	    		        
	    tooltip: {
            formatter: function() {
                return '<b>부정률</b>'+
                    ' : '+ Highcharts.numberFormat(this.y, 1)+'%';
            }
        },
	    yAxis: [{
	        min: 0,
	        max: 100,
	        minorTickPosition: 'inside',
            minorTickLength: 3,
            tickLength: 5,
            tickPosition: 'inside',
	        labels: {
	        	rotation: 'auto',
	        	distance: 0,
	        	style: {
	        		fontSize: 9
	        	}
	        },
	        plotBands: [{
	        	from: 0,
	        	to: 33,
	        	color: {
                  radialGradient: { cx: 1, cy: 1, r: 1.9 },
                  stops: [
                      [0, '#9EC430'],
                      [1, '#ffffff'],
                      [2, '#9EC430']
                  ]
                },
	        	innerRadius: '60%',
	        	outerRadius: '100%'
	        },{
	        	from: 33,
	        	to: 66,
	        	color: {
                  radialGradient: { cx: 0.6, cy: 1.3, r: 1.9 },
                  stops: [
                      [0, '#DEB629'],
                      [1, '#ffffff'],
                      [2, '#DEB629']
                  ]
                },
	        	innerRadius: '60%',
	        	outerRadius: '100%'
	        },{
	        	from: 66,
	        	to: 100,
	        	color: {
                  radialGradient: { cx: 0.2, cy: 1, r: 1.9 },
                  stops: [
                      [0, '#D77C32'],
                      [1, '#ffffff'],
                      [2, '#D77C32']
                  ]
                },
	        	innerRadius: '60%',
	        	outerRadius: '100%'
	        }
                       ],
	        pane: 0,
	        title: {
	        	text: '',
	        	y: -40
	        }
	    }],
	    credits: {
			text: ""
		},
	    plotOptions: {
	    	gauge: {
	    		dataLabels: {
	    			enabled: false
	    		},
	    		dial: {
	    			radius: '80%'
	    		}
	    	}
	    },
	    	
	
	    series: [{
	    	name: '부정률',
	        data: [dataInfo],
	        yAxis: 0
	    }]
	
	});
};

makeChart.lineChart1 = function(target, category, data, date_data){
	var y = Number(category.substring(0,4));
	var m = Number(category.substring(5,7));
	var d = Number(category.substring(8,10));
	var h = Number(category.substring(11,13));
	var arr_data = new Array;
	for(var i = 0; i < data.split(',').length; i++){
		//arr_data[i] = Number(data.split(',')[i]);
		arr_data[i] = {y: Number(data.split(',')[i]), drilldown: {data: date_data.split(',')[i]}};
	}
    var chart;
    chart = new Highcharts.Chart({
        chart: {
            renderTo: target,
            type: 'spline'
        },
        title: {
            text: '',
            x: -20 //center
        },
        subtitle: {
            text: '',
            x: -20
        },
        xAxis: {
        	type: 'datetime'
        },
        yAxis: {
        	min: 0,
            title: {
                text: ''
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        plotOptions: {
            spline: {
                lineWidth: 4,
                states: {
                    hover: {
                        lineWidth: 5
                    }
                },
                marker: {
                    enabled: true,
                    states: {
                        hover: {
                            enabled: true,
                            symbol: 'circle',
                            radius: 5,
                            lineWidth: 1
                        }
                    }
                },
                pointInterval: 3600000, // one hour
                pointStart: Date.UTC(y, m-1, d, h, 0, 0)
            },
            series: {
                cursor: 'pointer',
                point: {
                    events: {
                        click: function() {
                            var drilldown = this.drilldown;
                            if (drilldown) { // drill down
                                chartLink(drilldown.data);
                            }
                        }
                    }
                },
                marker: {
                    lineWidth: 1
                }
            }

        },
        tooltip: {
            formatter: function() {
                    return this.y;
            }
        },
        legend: {
        	enabled: false,
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'top',
            x: -10,
            y: 100,
            borderWidth: 0
        },
	    credits: {
			text: ""
		},
        series: [{
            color:'#696969',
            data: arr_data
        }]
    });
};

makeChart.lineChart2 = function(target, category, data, date_data){
	var y = Number(category.substring(0,4));
	var m = Number(category.substring(5,7));
	var d = Number(category.substring(8,10));
	//alert(y+'\n'+m+'\n'+d);
	var arr_data = new Array;
	for(var i = 0; i < data.split(',').length; i++){
		//arr_data[i] = Number(data.split(',')[i]);
		arr_data[i] = {y: Number(data.split(',')[i]), drilldown: {data: date_data.split(',')[i]}};
	}
	
	var chart;
	chart = new Highcharts.Chart({
		chart: {
		renderTo: target,
		type: 'spline'
	},
	title: {
		text: '',
		x: -20 //center
	},
	subtitle: {
		text: '',
		x: -20
	},
	xAxis: {
		type: 'datetime'
	},
	yAxis: {
		min: 0,
		title: {
		text: ''
	},
	plotLines: [{
		value: 0,
		width: 1,
		color: '#808080'
	}]
	},
    plotOptions: {
        spline: {
            lineWidth: 4,
            states: {
                hover: {
                    lineWidth: 5
                }
            },
            marker: {
                enabled: true,
                states: {
                    hover: {
                        enabled: true,
                        symbol: 'circle',
                        radius: 5,
                        lineWidth: 1
                    }
                }
            },
            pointInterval: 24 * 3600 * 1000,
            pointStart: Date.UTC(y, m-1, d, 0, 0, 0)
        },
        series: {
            cursor: 'pointer',
            point: {
                events: {
                    click: function() {
                        var drilldown = this.drilldown;
                        if (drilldown) { // drill down
                            chartLink(drilldown.data);
                        }
                    }
                }
            },
            marker: {
                lineWidth: 1
            }
        }
    },
	tooltip: {
		formatter: function() {
			return this.y;
		}
	},
	legend: {
		enabled: false,
		layout: 'vertical',
		align: 'right',
		verticalAlign: 'top',
		x: -10,
		y: 100,
		borderWidth: 0
	},
	credits: {
		text: ""
	},
	series: [{
		color:'#696969',
		data: arr_data
	}]
	});
};

makeChart.categoryBarChart = function(target, category, categoryCode, p_data, n_data){
	var pos_data = new Array;
	for(var i = 0; i < p_data.length; i++){
		pos_data[i] = {y:Number(p_data[i]), drilldown: {data: categoryCode[i], data1: 'P'}};
	}
	var neg_data = new Array;
	for(var i = 0; i < n_data.length; i++){
		neg_data[i] = {y:Number(n_data[i]), drilldown: {data: categoryCode[i], data1: 'N'}};
	}
	    var chart;
        chart = new Highcharts.Chart({
            chart: {
                renderTo: target,
                type: 'column'
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                categories: category,
                labels: {
    	        	style: {
    	        		fontSize: 6
    	        	}
    	        }
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                }
            },
            legend: {
            	enabled: false,
                layout: 'vertical',
                backgroundColor: '#FFFFFF',
                align: 'left',
                verticalAlign: 'top',
                x: 100,
                y: 70,
                floating: true,
                shadow: true
            },
            tooltip: {
                formatter: function() {
                    return ''+
                        this.series.name +': '+ this.y;
                }
            },
    	    credits: {
    			text: ""
    		},
            plotOptions: {
                column: {
	    			cursor: 'pointer',
	                point: {
	                    events: {
	                        click: function() {
	                            var drilldown = this.drilldown;
	                            if (drilldown) { // drill down
	                                chartLink1(drilldown.data, drilldown.data1);
	                            }
	                        }
	                    }
	                },
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            },
                series: [{
                name: '긍정',
                data: pos_data
    
            }, {
                name: '부정',
                data: neg_data
    
            }]
        });
};

makeChart.columnChart = function(target, category, seriesname, data1, data2, data3, data4){
	var dataA = new Array;
	for(var i = 0; i < data1.split(',').length ; i++){
		dataA[i] = Number(data1.split(',')[i]);
	}
	
	var dataB = new Array;
	for(var i = 0; i < data2.split(',').length ; i++){
		dataB[i] = Number(data2.split(',')[i]);
	}
	
	var dataC = new Array;
	for(var i = 0; i < data3.split(',').length ; i++){
		dataC[i] = Number(data3.split(',')[i]);
	}
	
	var dataD = new Array;
	for(var i = 0; i < data4.split(',').length ; i++){
		dataD[i] = Number(data4.split(',')[i]);
	}	
	
	
		var chart;
	    chart = new Highcharts.Chart({
	        chart: {
	    	renderTo: target,
	        type: 'column',
	        backgroundColor: '#f0f0f0',
	        width: 540,
	        height: 143
	    },
	    title: {
	        text: '',
	        y: -40
	    },
	    subtitle: {
	        text: '',
	        y: -40
	    },
	    xAxis: {
	        categories: category,
	        labels:{
	    		y : 23
	    	}
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: '',
	            x : -20
	        }
	    },
	    credits: {
	        enabled: false
	    },
	    legend: {
	    	y:220
	    },
        tooltip: {
//            headerFormat: '<span style="font-size:10px">{point.key}</span><table width="90" border="0">',
//            pointFormat: '<tr><td style="color:{series.color};padding:0"><strong>{series.name}: </strong></td>' +
//                		 '<td style="padding:0"><strong>{point.y}</strong></td></tr>',
//            footerFormat: '</table>',
//            shared: true,
//            useHTML: true
        },
        colors: [
                 '#5383bf', 
                 '#cb8b8a', 
                 '#c49cd2', 
                 '#a3b480'
              ],
	  
	    
	    plotOptions: { 
	    	column: { 
	    		dataLabels: { 
	    			enabled: true, 
	    			formatter: function() { 
        				//if(this.y != 0){
        					return this.y;
        				//}
	    			} 
	    		},
	        	borderWidth: 0,
	        	shadow: false
	    	}, 
	    	series: {
	    		groupPadding: 0.125,
	    		cursor: 'pointer',
	    		point: {
		    		events: {
		    			click: function() {
	    					clickColumnChart(this.series.name, this.category);
		    			}
		    		}
		    	}
	    	}
	    },
	    
	    series: [{
	        name: seriesname[0],
	        data: dataA
	    }, {
	        name: seriesname[1],
	        data: dataB
	    }, {
	        name: seriesname[2],
	        data: dataC
	    }, {
	        name: seriesname[3],
	        data: dataD
	    }]
    });
};


makeChart.columnChart5 = function(target, category, seriesname, data1, data2){
	var dataA = new Array;
	for(var i = 0; i < data1.split(',').length ; i++){
		dataA[i] = Number(data1.split(',')[i]);
	}
	
	var dataB = new Array;
	for(var i = 0; i < data2.split(',').length ; i++){
		dataB[i] = Number(data2.split(',')[i]);
	}
	
	
		var chart;
	    chart = new Highcharts.Chart({
	        chart: {
	    	renderTo: target,
	        type: 'column',
	        backgroundColor: '#f0f0f0',
	        width: 410,
	        height: 170
	    },
	    title: {
	        text: '',
	        y: -40
	    },
	    subtitle: {
	        text: '',
	        y: -40
	    },
	    xAxis: {
	        categories: category
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: '',
	            x : -20
	        }
	    },
	    credits: {
	        enabled: false
	    },
	    legend: {
	    	y:220
	    },
        tooltip: {
//            headerFormat: '<span style="font-size:10px">{point.key}</span><table width="90" border="0">',
//            pointFormat: '<tr><td style="color:{series.color};padding:0"><strong>{series.name}: </strong></td>' +
//                		 '<td style="padding:0"><strong>{point.y}</strong></td></tr>',
//            footerFormat: '</table>',
//            shared: true,
//            useHTML: true
        },
        colors: [
                 '#5383bf', 
                 '#cb8b8a', 
                 '#c49cd2', 
                 '#a3b480'
              ],
	  
	    
	    plotOptions: { 
	    	column: { 
	    		dataLabels: { 
	    			enabled: true, 
	    			formatter: function() { 
        				//if(this.y != 0){
        					return this.y;
        				//}
	    			} 
	    		},
	        	borderWidth: 0,
	        	shadow: false
	    	}, 
	    	series: {
	    		groupPadding: 0.125
	    	}
	    },
	    
	    series: [{
	        name: '이슈',
	        data: dataA
	    }, {
	        name: '이슈',
	        data: dataB
	    }]
    });
};
	    
makeChart.stackedBarChart = function(target, category, seriesname, data1, data2, data3, data4){
	
	var dataA = new Array;
	for(var i = 0; i < data1.split(',').length ; i++){
		dataA[i] = Number(data1.split(',')[i]);
	}
	
	var dataB = new Array;
	for(var i = 0; i < data2.split(',').length ; i++){
		dataB[i] = Number(data2.split(',')[i]);
	}
	
	var dataC = new Array;
	for(var i = 0; i < data3.split(',').length ; i++){
		dataC[i] = Number(data3.split(',')[i]);
	}
	
	var dataD = new Array;
	for(var i = 0; i < data4.split(',').length ; i++){
		dataD[i] = Number(data4.split(',')[i]);
	}
	
	var chart;
    chart = new Highcharts.Chart({
    	chart: {
    	renderTo: target,
        type: 'bar',
        backgroundColor: '#f0f0f0',
        borderRadius: 0,
        width: 170,
        height: 170
    },
    title: {
        text: '',
        y:-40	
    },
    xAxis: {
        categories: category
    },
    yAxis: {
        min: 0,
        title: {
            text: '',
            y:-30
        }
    },
    legend: {
    	y:220
    },
    plotOptions: {
        series: {
            stacking: 'normal'
        },
        bar: {
        	borderWidth: 0,
        	shadow: false/*,
            dataLabels: { 
    			enabled: true, 
    			formatter: function() { 
        			if(this.y != 0)
        				return this.y;
    			},
    			color: '#ffffff' 
    		}
    		*/
        },
		groupPadding: 0.1

    },
    colors: [
             '#5383bf', 
             '#cb8b8a', 
             '#c49cd2', 
             '#a3b480'
          ],
    credits: {
        enabled: false
    },
    series: [{
        name: seriesname[0],
        data: dataA
    }, {
        name: seriesname[1],
        data: dataB
    }, {
        name: seriesname[2],
        data: dataC
    }, {
        name: seriesname[3],
        data: dataD
    }]
});	    
};

makeChart.stackedBar2Chart = function(target, category, seriesname, data1, data2, data3, data4, type){
	
	var dataA = new Array;
	for(var i = 0; i < data1.split(',').length ; i++){
		dataA[i] = Number(data1.split(',')[i]);
	}
	
	var dataB = new Array;
	for(var i = 0; i < data2.split(',').length ; i++){
		dataB[i] = Number(data2.split(',')[i]);
	}
	
	var dataC = new Array;
	for(var i = 0; i < data3.split(',').length ; i++){
		dataC[i] = Number(data3.split(',')[i]);
	}
	
	var dataD = new Array;
	for(var i = 0; i < data4.split(',').length ; i++){
		dataD[i] = Number(data4.split(',')[i]);
	}
	
	var chart;
    chart = new Highcharts.Chart({
    	chart: {
    	renderTo: target,
        type: 'bar',
        backgroundColor: '#f0f0f0',
        borderRadius: 0,
        width: 500,
        height: 150
    },
    title: {
        text: '',
        y:-40	
    },
    xAxis: {
        categories: category,
        labels: {
    		style: {
    			fontSize: '11px'
    		}
    	}
    },
    yAxis: {
        min: 0,
        title: {
            text: '',
            y:-30
        }
    },
    legend: {
    	y:220
    },
    plotOptions: {
        series: {
            stacking: 'normal',
            groupPadding: 0.125,
    		cursor: 'pointer',
    		point: {
	    		events: {
	    			click: function() {
    					clickColumnChart(this.series.name, this.category, type);
	    			}
	    		}
	    	}
        },
        bar: {
        	borderWidth: 0,
        	shadow: false,
        	/*
            dataLabels: { 
    			enabled: true, 
    			formatter: function() { 
        			if(this.y != 0)
        				return this.y;
    			},
    			color: '#ffffff'
    		},
    		*/
    		cursor: 'pointer',
    		groupPadding: 0.1
        }
    },
    colors: [
             '#5383bf', 
             '#cb8b8a', 
             '#c49cd2', 
             '#a3b480'
          ],
    credits: {
        enabled: false
    },
    series: [{
        name: seriesname[0],
        data: dataA
    }, {
        name: seriesname[1],
        data: dataB
    }, {
        name: seriesname[2],
        data: dataC
    }, {
        name: seriesname[3],
        data: dataD
    }]
});	    
};

makeChart.stackedBarChartTotal = function(target, totalCntData){
	var totalCntArr = new Array;
	for(var i = 0; i < totalCntData.split(',').length ; i++){
		totalCntArr[i] = Number(totalCntData.split(',')[i]);
	}
	
	
	var chart;
    chart = new Highcharts.Chart({
    	chart: {
    	renderTo: target,
        type: 'column',
        backgroundColor: '#f0f0f0',
        borderRadius: 0,
        width: 130,
        height: 170
    },
    title: {
        text: '',
        y:-40	
    },
    xAxis: {
        categories: ['total']
    },
    yAxis: {
        min: 0,
        title: {
            text: '',
            y:-30
        }
    },
    legend: {
    	y:220
    },
    plotOptions: {
    	column: {
	        stacking: 'normal',
	        dataLabels: {
	            enabled: true,
	            color: '#FFFFFF'
	    	},
		    borderWidth: 0,
			shadow: false
	    },
    	groupPadding: 0.1

    },
    colors: [
             '#5383bf', 
             '#cb8b8a', 
             '#c49cd2', 
             '#a3b480'
          ],
    credits: {
        enabled: false
    },
    series: [{
        name: 'riskA',
        data : [totalCntArr[0]]
    },{
        name: 'riskB',
        data : [totalCntArr[1]]
    },{
        name: 'riskC',
        data: [totalCntArr[2]]
    },{
        name: '법률/정부',
        data: [totalCntArr[3]]
    },{
        name: '지적재산',
        data : [totalCntArr[4]]
    }]
});	    
};

makeChart.pieChart = function(target, data){
	var dataSet = data.split(',');
	var chart;
    chart = new Highcharts.Chart({
    	chart: {
    	renderTo: target,
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        width: 120,
        height: 131,
        backgroundColor: '#f0f0f0'
	    },
	    title: {
	        text: '',
	        y:-20
	    },
	    tooltip: {
		    enabled:true,
		    formatter: function() { 
    			return "<span style=\"color:"+this.point.color+"\">"+this.point.name+":</span> <strong>"+this.y+"</strong>";
	    	}
	    },
	    plotOptions: {
	        pie: {
		    	borderWidth: 0,
	        	shadow: false,
	            allowPointSelect: true,
	            cursor: 'pointer',
	            dataLabels: {
	    	 		distance: -15,
	                enabled: true,
	                formatter: function() { 
        			if(this.y != 0)
        				return this.y;
	    			},
	    			color: '#ffffff'
	            },
	            showInLegend: true,
	            size: 100
	        }
	    
	    },
	    credits: {
	        enabled: false
	    },
	    legend: {
	    	y:220
	    },
	    colors: [
	             '#80a8da', 
	             '#ca8482', 
	             '#a38fba', 
	             '#899f59', 
	             '#a8a8a8'
	          ],
	    series: [{
	        type: 'pie',
	        data: [
	            ['발화',   Number(dataSet[0])],
	            ['발열',   Number(dataSet[1])],
	            ['폭발',   Number(dataSet[2])],
	            ['스웰링', Number(dataSet[3])],
	            ['기타',   Number(dataSet[4])]
	        ]
	    }]
    });	    
};

makeChart.stackedColumn = function(target, category, data1, data2, year){
	
	var dataA = new Array;
	for(var i = 0; i < data1.length ; i++){
		dataA[i] = Number(data1[i]);
	}

	var dataB = new Array;
	for(var i = 0; i < data2.length ; i++){
		dataB[i] = Number(data2[i]);
	}
	
	var chart;
    chart = new Highcharts.Chart({
    	chart: {
	        type: 'column',
	        renderTo: target
	    },
	    title: {
	        text: ''
	    },
	    xAxis: {
	        categories: category
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: ''
	        },
	        stackLabels: {
	            enabled: true,
	            style: {
	                fontWeight: 'bold',
	                color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
	            }
	        }
	    },
	    credits: {
	        enabled: false
	    },
	    legend: {
	    	y:220
	    },
	    tooltip: {
	        formatter: function() {
	            return '<b>'+ this.x +'</b><br/>'+
	                this.series.name +': '+ this.y +'<br/>'+
	                'Total: '+ this.point.stackTotal;
	        }
	    },
	    plotOptions: {
	        column: {
	            stacking: 'normal',
	            borderWidth: 0,
	        	shadow: false,
	        	cursor: 'pointer',
	    		point: {
		    		events: {
		    			click: function() {
	            			var date = year[this.x]+this.category;
	            			getWeeklyIssueList(date, this.series.name);
		    			}
		    		}
		    	}
	        }
	    },
	    colors: [
	             '#ca8482', 
	             '#80a8da'
	          ],
	    series: [{
	        name: '부정',
	        data: dataB
	    }, {
	        name: '긍정',
	        data: dataA
	    }]
    });	    
};

makeChart.pieChart2 = function(target, category, seq, data, date){
	
	var dataSet = [];
	if(category){
		for(i=0;i<category.length;i++){
			dataSet.push([category[i], parseInt(data[i])]);
		}
	}
	var chart;
    chart = new Highcharts.Chart({
    	chart: {
    	renderTo: target,
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        width: 137,
        height: 115,
        backgroundColor: '#f0f0f0',
        margin: [0, 0, 0, 0]
	    },
	    title: {
	        text: '',
	        y:-20
	    },
	    tooltip: {
		    enabled:true,
		    formatter: function() { 
    			return "<span style=\"color:"+this.point.color+"\">"+this.point.name+":</span> <strong>"+this.y+"</strong>";
	    	}
	    },
	    plotOptions: {
	        pie: {
		    	borderWidth: 0,
	        	shadow: false,
	            allowPointSelect: false,
	            cursor: 'pointer',
	            dataLabels: {
	    	 		distance: -15,
	                enabled: true,
	                formatter: function() { 
        			if(this.y != 0)
        				return this.y;
	    			},
	    			color: '#ffffff'
	            },
	            showInLegend: true,
	            size: 100,
	    		point: {
		    		events: {
		    			click: function() {
	            			getChanelIssueList(date, seq[this.x]);
		    			}
		    		}
		    	}
	        }
	    
	    },
	    credits: {
	        enabled: false
	    },
	    legend: {
	    	y:300
	    },
	    colors: [
	             '#80a8da', 
	             '#ca8482', 
	             '#a38fba', 
	             '#899f59', 
	             '#a8a8a8',
	             '#db843e'
	          ],
	    series: [{
	        type: 'pie',
	        data: dataSet
	    }]
    });	    
};
//'ariskChart', category, k_yp, data1, k_xp, eDate, sDate, timeArea
makeChart.pieChartCompliance = function(target, category, k_yp, data, k_xp, eDate, sDate, timeArea){
	var dataSet = [];
	if(category){
		for(i=0;i<category.length;i++){
			dataSet.push([category[i], parseInt(data[i])]);
		}
	}
	var chart;
    chart = new Highcharts.Chart({
    	chart: {
    	renderTo: target,
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        width: 175,
        height: 135,
        backgroundColor: '#f0f0f0',
        margin: [0, 0, 0, 0]
	    },
	    title: {
	        text: '',
	        y:-20
	    },
	    	    
	    tooltip: {
	    	enabled:true,
	    	//headerFormat: "<span style=\"color:"+this.point.color+"\">"+this.point.name+":</span> <strong>"+this.y+"</strong>",
		    //pointFormat: "<span style=\"color:"+this.point.color+"\">"+this.point.name+":</span> <strong>"+this.y+"</strong>"
		    formatter: function() { 
    			return "<span style=\"color:"+this.point.color+"\">"+this.point.name+":</span> <strong>"+this.y+"</strong>";
	    	}
	    },
	    plotOptions: {
	        pie: {
		    	borderWidth: 0,
	        	shadow: false,
	            allowPointSelect: false,
	            cursor: 'pointer',
	            dataLabels: {
	    	 		distance: 8,
	                enabled: true,
	                inside: true,
	                softConnector:false,
	                formatter: function() { 
        			if(this.y != 0)
        				return "<span style=\"color:"+this.point.color+"\">"+this.y+"</span>";
        				//return this.y;
        				//format: '<b>{dataSet}</b>:'
	    			
	    			}
	    			
	            },
	            showInLegend: false,
	            size: 85,
	    		point: {
		    		events: {
		    			click: function() {
	            	//eDate, k_xp, k_yp, timeArea, sDate
	            			getKeyConditionList(eDate, k_xp, k_yp[this.x], timeArea, sDate);
		    			}
		    		}
		    	}
	        }
	    
	    },
	    credits: {
	        enabled: false
	    },
	    colors: [
	             '#80a8da', 
	             '#ca8482', 
	             '#a38fba', 
	             '#899f59', 
	             '#a8a8a8',
	             '#db843e'
	          ],
	    series: [{
	        type: 'pie',
	        data: dataSet
	        
	    }]
	   
    });	    
};

//테스트용 파이차트
makeChart.pieChartTest = function(target, category, k_yp, data, k_xp, eDate, sDate, timeArea){
	var dataSet = [];
	if(category){
		for(i=0;i<category.length;i++){
			dataSet.push([category[i], parseInt(data[i])]);
		}
	}
	var chart;
    chart = new Highcharts.Chart({
    	chart: {
    	renderTo: target,
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        width: 300,
        height: 150,
        backgroundColor: '#f0f0f0',
        margin: [0, 0, 0, 0]
	    },
	    title: {
            text: 'Browser market shares at a specific website, 2010'
        },
        tooltip: {
    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: false,
                cursor: 'pointer',
                dataLabels: {
        			distance:10,
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                    
                },
                point:{
                    events:{
                        click:function(){
                             alert(this.x);
                        }
                    }
                }
            }
        },
        series: [{
            type: 'pie',
            name: 'Browser share',
            data: [
                ['Firefox',   45.0],
                ['IE',       26.8],
                {
                    name: 'Chrome',
                    y: 12.8,
                    sliced: false,
                    selected: false
                },
                ['Safari',    8.5],
                ['Opera',     6.2],
                ['Others',   0.7]
            ]
        }]
	   
    });	    
};

