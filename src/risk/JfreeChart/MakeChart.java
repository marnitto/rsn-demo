package risk.JfreeChart;



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StackedXYBarRenderer;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Week;
import org.jfree.data.xy.CategoryTableXYDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.VerticalAlignment;

//import risk.issue.IssueStaticBean;
import risk.issue.IssueStaticBean;
import risk.util.ConfigUtil;
import risk.util.DateUtil;
import risk.util.StringUtil;


public class MakeChart {

	
	public MakeChart() {
		//가비지 콜렉터 호출
		System.gc();
	}
	
	ConfigUtil cu = new ConfigUtil();
	
	//String filePath = cu.getConfig("CHARTPATH")+"statistics/chartimg/twitter/";
	//String chartURL = cu.getConfig("URL")+"riskv3/statistics/chartimg/twitter/";
	
	//변경 : 진솔
	String filePath = cu.getConfig("CHARTPATH")+"twitter/";
	String chartURL = cu.getConfig("URL")+"chartimg/twitter/";
	
	/*
	 * 153,51,0
	 * 204,0,0
	 * 137,165,78
	 * 113,88,143
	 * 204,153,255
	 * 219,132,61
	 * 147,169,207
	 * 209,147,146
	 * 
	 * 209,207,146
	 * 0,140,153
	 * 153,0,104
	 * 61,153,0
	 * */
	GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, new Color(80,88,243),     0.0f, 0.0f, new Color(80,88,243));
	GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, new Color(247,0,123), 0.0f, 0.0f, new Color(247,0,123));
	GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, new Color(255,255,0),     0.0f, 0.0f, new Color(255,255,0));
	GradientPaint gp3 = new GradientPaint(0.0f, 0.0f, new Color(0,128,128), 0.0f, 0.0f, new Color(0,128,128));

	GradientPaint gp4 = new GradientPaint(0.0f, 0.0f, new Color(255,128,0),     0.0f, 0.0f, new Color(255,128,0));
	GradientPaint gp5 = new GradientPaint(0.0f, 0.0f, new Color(129,234,123),     0.0f, 0.0f, new Color(129,234,123));
	GradientPaint gp6 = new GradientPaint(0.0f, 0.0f, new Color(97,210,235),     0.0f, 0.0f, new Color(97,210,235));
	GradientPaint gp7 = new GradientPaint(0.0f, 0.0f, new Color(207,160,207),     0.0f, 0.0f, new Color(207,160,207));

	GradientPaint gp8 = new GradientPaint(0.0f, 0.0f, new Color(208,205,214),     0.0f, 0.0f, new Color(208,205,214));
	GradientPaint gp9 = new GradientPaint(0.0f, 0.0f, new Color(241,200,131),     0.0f, 0.0f, new Color(241,200,131));
	GradientPaint gp10 = new GradientPaint(0.0f, 0.0f, new Color(126,147,226),     0.0f, 0.0f, new Color(126,147,226));
	GradientPaint gp11 = new GradientPaint(0.0f, 0.0f, new Color(193,242,79),     0.0f, 0.0f, new Color(193,242,79));
	
	/* 
	GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, new Color(153,51,0), 0.0f, 0.0f, new Color(153,51,0));
	GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, new Color(204,0,0),     0.0f, 0.0f, new Color(204,0,0));
	GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, new Color(137,165,78),     0.0f, 0.0f, new Color(137,165,78));
	GradientPaint gp3 = new GradientPaint(0.0f, 0.0f, new Color(113,88,143), 0.0f, 0.0f, new Color(113,88,143));
	
	GradientPaint gp4 = new GradientPaint(0.0f, 0.0f, new Color(204,153,255),     0.0f, 0.0f, new Color(204,153,255));
	GradientPaint gp5 = new GradientPaint(0.0f, 0.0f, new Color(219,132,61),     0.0f, 0.0f, new Color(219,132,61));
	GradientPaint gp6 = new GradientPaint(0.0f, 0.0f, new Color(147,169,207),     0.0f, 0.0f, new Color(147,169,207));
	GradientPaint gp7 = new GradientPaint(0.0f, 0.0f, new Color(209,147,146),     0.0f, 0.0f, new Color(209,147,146));
	
	GradientPaint gp8 = new GradientPaint(0.0f, 0.0f, new Color(209,207,146),     0.0f, 0.0f, new Color(209,207,146));
	GradientPaint gp9 = new GradientPaint(0.0f, 0.0f, new Color(0,140,153),     0.0f, 0.0f, new Color(0,140,153));
	GradientPaint gp10 = new GradientPaint(0.0f, 0.0f, new Color(153,0,104),     0.0f, 0.0f, new Color(153,0,104));
	GradientPaint gp11 = new GradientPaint(0.0f, 0.0f, new Color(61,153,0),     0.0f, 0.0f, new Color(61,153,0));
	*/
	
	
	/*GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, new Color(42,168,235), 0.0f, 0.0f, new Color(42,168,235));
	GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, new Color(248,87,34),     0.0f, 0.0f, new Color(248,87,34));
	GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, new Color(227,198,31),     0.0f, 0.0f, new Color(227,198,31));
	GradientPaint gp3 = new GradientPaint(0.0f, 0.0f, new Color(051,102,255), 0.0f, 0.0f, new Color(051,102,255));
	GradientPaint gp4 = new GradientPaint(0.0f, 0.0f, new Color(45,163,193),     0.0f, 0.0f, new Color(45,163,193));
	GradientPaint gp5 = new GradientPaint(0.0f, 0.0f, new Color(92,155,209),     0.0f, 0.0f, new Color(92,155,209));
	GradientPaint gp6 = new GradientPaint(0.0f, 0.0f, new Color(146,209,92),     0.0f, 0.0f, new Color(146,209,92));
	GradientPaint gp7 = new GradientPaint(0.0f, 0.0f, new Color(92,209,205),     0.0f, 0.0f, new Color(92,209,205));
	GradientPaint gp8 = new GradientPaint(0.0f, 0.0f, new Color(209,188,92),     0.0f, 0.0f, new Color(209,188,92));
	GradientPaint gp9 = new GradientPaint(0.0f, 0.0f, new Color(150,149,149),     0.0f, 0.0f, new Color(150,149,149));
	GradientPaint gp10 = new GradientPaint(0.0f, 0.0f, new Color(102,051,153),     0.0f, 0.0f, new Color(102,051,153));
	GradientPaint gp11 = new GradientPaint(0.0f, 0.0f, new Color(255,153,000),     0.0f, 0.0f, new Color(255,153,000));*/
	
	
	//2가지코드로 통계구할경우  데이터셋
	public DefaultCategoryDataset SetValue2(ArrayList arrLabel) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		try {
			for (int i = 0;i<arrLabel.size();i++) {
				IssueStaticBean isBean = new IssueStaticBean();
				isBean = (IssueStaticBean)arrLabel.get(i);
								
				//System.out.println(isBean.getCodeName1()+isBean.getCodeName2()+isBean.getCodeValue());
				dataset.addValue(isBean.getCodeValue(), isBean.getCodeName1(), isBean.getCodeName2());
				
			}
			
		} catch(Exception ex) {
			System.out.println(ex.getMessage()+"mkchart");
		}
		return dataset;
	}
	/**
	 * @param arrLabel
	 * @param arrSeries
	 * @param arrValue
	 */
	public int getVal(ArrayList arrValue, String xVal, int yVal) {
		ChartBean cb = null;
		int rtnVal = 0;
		for (int i=0; i<arrValue.size(); i++) {
			cb = (ChartBean) arrValue.get(i);
			if (cb.getMsxVal().equals(xVal) && cb.getMsyVal() == yVal) {
				rtnVal = cb.getMiVal();
				break;
			}
		}
		return rtnVal;
	}
	
	/**
	 * 일별 데이터 변화를 표시하는 그래프의 DATASET을 반환한다.
	 * @param sDate 시작일 "YYYY-MM-DD"
	 * @param eDate 종료일 "YYYY-MM-DD"
	 * @param paList 데이터 세트 ARRAYLIST
	 * @return
	 */
		public TimeSeriesCollection setChartDataKeywordCount( String sDate, String eDate, ArrayList paList, String unit ) {
			TimeSeries series = null;
			
			DateUtil du = new DateUtil();
			StringUtil su = new StringUtil();
			ArrayList arrData = null;
			GetChartData GCdata = new GetChartData();
			String Input_sDate = "";
			String Input_eDate = "";
			
			if(unit.equals("w")){
				Input_sDate = sDate.replaceAll("-","");
				Input_eDate = eDate.replaceAll("-","");
				arrData = GCdata.getXvalDate(Input_sDate, Input_eDate, unit, "yyyyww");
			}else if(unit.equals("m")){
				Input_sDate = sDate.replaceAll("-","");
				Input_eDate = eDate.replaceAll("-","");
				arrData = GCdata.getXvalDate(Input_sDate, Input_eDate, unit, "yyyyMM");
			}else if(unit.equals("d")){
				Input_sDate = sDate.replaceAll("-","");
				Input_eDate = eDate.replaceAll("-","");
				arrData = GCdata.getXvalDate(Input_sDate, Input_eDate, unit, "yyyyMMdd");
			}else{
				arrData = GCdata.getXvalDate(Input_sDate, Input_eDate, unit, "yyyyMMdd");
			}
	        TimeSeriesCollection dataset = new TimeSeriesCollection();
	        
	        Day day = null;
	        Week week = null;
	        Month month = null;
	        //java.util.Calendar cal = null;
	        
	        try {
	        String tmpName = "";
	        ArrayList arrChartData = new ArrayList(); // 전체 chart data의 집합
	        ArrayList arrSeriesData = new ArrayList(); // 각 계열(series) date의 집합
	        ArrayList arrName = new ArrayList(); // 각 계열(series) date의 집합
	        
	        for( int j=0; j<paList.size(); j++ ) {
	        	ChartBean CBean = (ChartBean)paList.get(j);
	        	if (!tmpName.equals(CBean.getData_name())) {	// title이 변경되면 새로운 계열의 시작이다.
	        		if (arrSeriesData.size() > 0) {  			// 계열  data가 있다면 전체 data집합에 추가한다.
	        			arrChartData.add(arrSeriesData);
	        		}
	        		tmpName = CBean.getData_name();				// 비교대상 title변경
	        		arrName.add(tmpName);
	        		//System.out.println("tmpname="+tmpName);
	    			arrSeriesData = new ArrayList();			// series data초기화
	        	}
	        	arrSeriesData.add(CBean);						
			}
	        arrChartData.add(arrSeriesData);					// 마지막 series data추가
	        
	        int sd = 0;
	        
	        for (int i=0; i<arrChartData.size(); i++) {
	        	arrSeriesData = (ArrayList)arrChartData.get(i);
	        	if (arrSeriesData.size()>0) {
	        		if(unit.equals("w")){
	        		//	series = new TimeSeries((String)arrName.get(i), org.jfree.data.time.Week.class);
	        			series = new TimeSeries((String)arrName.get(i));
	        		}else if(unit.equals("m")){
	        		//	series = new TimeSeries((String)arrName.get(i), org.jfree.data.time.Month.class);
	        			series = new TimeSeries((String)arrName.get(i));
	        		}else if(unit.equals("d")){
	        		//	series = new TimeSeries((String)arrName.get(i), org.jfree.data.time.Day.class);
	        			series = new TimeSeries((String)arrName.get(i));
	        		}
	        		
		        	sd=0;
		        	for(int j=0; j<arrData.size(); j++) {
		        		ChartBean CBean = (ChartBean)arrSeriesData.get(sd);
		        		String date = (String)arrData.get(j);
		        		//cal = java.util.Calendar.getInstance();
		        		if(unit.equals("w")){
		        			week = new Week(Integer.parseInt(date.substring(4, 6)), Integer.parseInt(date.substring(0, 4)));
		        		}else if(unit.equals("m")){
		        			month = new Month(Integer.parseInt(date.substring(4, 6)), Integer.parseInt(date.substring(0, 4)));
		        		}else if(unit.equals("d")){
		        			day = new Day(Integer.parseInt(date.substring(6,8)), Integer.parseInt(date.substring(4, 6)), Integer.parseInt(date.substring(0, 4)));
		        		}
						if(date.equals(CBean.getData_date())){
							if(unit.equals("w")) {
								series.add(week,Double.parseDouble(CBean.getData_count2()));
							}else if(unit.equals("m")) {
								series.add(month,Double.parseDouble(CBean.getData_count2()));
							}else if(unit.equals("d")) {
								series.add(day,Double.parseDouble(CBean.getData_count2()));
							}
							if (arrSeriesData.size()>sd+1) {
								sd++;
							}
						}
						else
						{
							if(unit.equals("w")){
								series.add(week,(double)0);
							}else if(unit.equals("m")){
								series.add(month,(double)0);
							}else if(unit.equals("d")){
								series.add(day,(double)0);
								
							}
						}
		        	}
		        	dataset.addSeries(series);
	        	}
	        }
	      
				
			} catch(Exception ex) {
				System.out.println(" ================ MakeChart setChartDataIssue2 ERROR ==================");
				ex.printStackTrace();
				System.out.println("MakeChart :    "+ex+"    "+ex.getMessage());
			}finally{
				series = null; day = null; week = null; month = null;
			}
			return dataset;
		}
		
		/**
		 * 일별 데이터 변화를 표시하는 그래프의 DATASET을 반환한다.
		 * @param sDate 시작일 "YYYY-MM-DD"
		 * @param eDate 종료일 "YYYY-MM-DD"
		 * @param paList 데이터 세트 ARRAYLIST
		 * @return
		 */
		public TimeSeriesCollection setChartDataDateCount( String sDate, String eDate, ArrayList paList, String unit ) {
			TimeSeries series = null;
			
			DateUtil du = new DateUtil();
			StringUtil su = new StringUtil();
			ArrayList arrData = null;
			GetChartData GCdata = new GetChartData();
			String Input_sDate = "";
			String Input_eDate = "";
			String title = "";
			
			if(paList!=null && paList.size()>0){
				ChartBean cBean = (ChartBean)paList.get(0);
				if(!cBean.getData_name().equals("")){
					title = cBean.getData_name();
				}else{
					title = "일별 변화";
				}
			}
			
			if(unit.equals("w")){
				Input_sDate = sDate.replaceAll("-","");
				Input_eDate = eDate.replaceAll("-","");
				arrData = GCdata.getXvalDate(Input_sDate, Input_eDate, unit, "yyyyww");
			}else if(unit.equals("m")){
				Input_sDate = sDate.replaceAll("-","");
				Input_eDate = eDate.replaceAll("-","");
				arrData = GCdata.getXvalDate(Input_sDate, Input_eDate, unit, "yyyyMM");
			}else if(unit.equals("d")){
				Input_sDate = sDate.replaceAll("-","");
				Input_eDate = eDate.replaceAll("-","");
				arrData = GCdata.getXvalDate(Input_sDate, Input_eDate, unit, "yyyyMMdd");
			}else{
				arrData = GCdata.getXvalDate(Input_sDate, Input_eDate, unit, "yyyyMMdd");
			}
			TimeSeriesCollection dataset = new TimeSeriesCollection();
			Day day = null;
			Week week = null;
			Month month = null;
			try {
				
				
				series = new TimeSeries(title);
				
				for(int j=0; j<arrData.size(); j++) {
					String date = (String)arrData.get(j);
					boolean chkDate = false;
					
					for (int i=0; i<paList.size(); i++) {
					ChartBean cBean = (ChartBean)paList.get(i);
					
					
						if(unit.equals("w")){
							week = new Week(Integer.parseInt(date.substring(4, 6)), Integer.parseInt(date.substring(0, 4)));
						}else if(unit.equals("m")){
							month = new Month(Integer.parseInt(date.substring(4, 6)), Integer.parseInt(date.substring(0, 4)));
						}else if(unit.equals("d")){
							day = new Day(Integer.parseInt(date.substring(6,8)), Integer.parseInt(date.substring(4, 6)), Integer.parseInt(date.substring(0, 4)));
						}
					
					//System.out.println("day:"+day);
					
						if(date.equals(cBean.getData_date())){
							if(unit.equals("w")) {
								series.add(week,Double.parseDouble(cBean.getData_count()));
							}else if(unit.equals("m")) {
								series.add(month,Double.parseDouble(cBean.getData_count()));
							}else if(unit.equals("d")) {
								series.add(day,Double.parseDouble(cBean.getData_count()));
							}
							chkDate = true;
						}
					}
					if(!chkDate){
						if(unit.equals("w")){
							series.add(week,(double)0);
						}else if(unit.equals("m")){
							series.add(month,(double)0);
						}else if(unit.equals("d")){
							series.add(day,(double)0);
							
						}
					}
				}
					
						
			dataset.addSeries(series);
				
				
			} catch(Exception ex) {
				System.out.println(" ================ MakeChart setChartDataIssue2 ERROR ==================");
				ex.printStackTrace();
				System.out.println("MakeChart :    "+ex+"    "+ex.getMessage());
			}finally{
				series = null; day = null; week = null; month = null;
			}
			return dataset;
		}
		
		/**
		 * 일별 데이터 변화를 표시하는 그래프의 DATASET을 반환한다.
		 * @param sDate 시작일 "YYYY-MM-DD"
		 * @param eDate 종료일 "YYYY-MM-DD"
		 * @param paList 데이터 세트 ARRAYLIST
		 * @return
		 */
		public TimeSeriesCollection setChartDataDateCountStack( String sDate, String eDate, ArrayList paList, String unit, int baseCnt ) {
			TimeSeries series = null;

			DateUtil du = new DateUtil();
			StringUtil su = new StringUtil();
			ArrayList arrData = null;
			GetChartData GCdata = new GetChartData();
			String Input_sDate = "";
			String Input_eDate = "";
			String title = "";

			if(paList!=null && paList.size()>0){

				ChartBean cBean = (ChartBean)paList.get(0);

				if(!cBean.getData_name().equals("")){
					title = cBean.getData_name();
				}else{
					title = "일별 변화";
				}
			}

			if(unit.equals("w")){
				Input_sDate = sDate.replaceAll("-","");
				Input_eDate = eDate.replaceAll("-","");
				arrData = GCdata.getXvalDate(Input_sDate, Input_eDate, unit, "yyyyww");
			}else if(unit.equals("m")){
				Input_sDate = sDate.replaceAll("-","");
				Input_eDate = eDate.replaceAll("-","");
				arrData = GCdata.getXvalDate(Input_sDate, Input_eDate, unit, "yyyyMM");
			}else if(unit.equals("d")){
				Input_sDate = sDate.replaceAll("-","");
				Input_eDate = eDate.replaceAll("-","");
				arrData = GCdata.getXvalDate(Input_sDate, Input_eDate, unit, "yyyyMMdd");
			}else{
				arrData = GCdata.getXvalDate(Input_sDate, Input_eDate, unit, "yyyyMMdd");
			}
			TimeSeriesCollection dataset = new TimeSeriesCollection();
			Day day = null;
			Week week = null;
			Month month = null;
			double defaultCnt = (double)baseCnt;

			try {

				
				series = new TimeSeries(title);
				
				for(int j=0; j<arrData.size(); j++) {
					String date = (String)arrData.get(j);
					boolean chkDate = false;
					
					if(unit.equals("w")){
						week = new Week(Integer.parseInt(date.substring(4, 6)), Integer.parseInt(date.substring(0, 4)));
					}else if(unit.equals("m")){
						month = new Month(Integer.parseInt(date.substring(4, 6)), Integer.parseInt(date.substring(0, 4)));
					}else if(unit.equals("d")){
						day = new Day(Integer.parseInt(date.substring(6,8)), Integer.parseInt(date.substring(4, 6)), Integer.parseInt(date.substring(0, 4)));
					}

					for (int i=0; i<paList.size(); i++) {
						ChartBean cBean = (ChartBean)paList.get(i);
	
						if(date.equals(cBean.getData_date())){
							defaultCnt = Double.parseDouble(cBean.getData_count());
							if(unit.equals("w")) {
								series.add(week,defaultCnt);
							}else if(unit.equals("m")) {
								series.add(month,defaultCnt);
							}else if(unit.equals("d")) {
								//System.out.println("day:"+day+"/cBean.getData_count():"+defaultCnt);
								
								series.add(day,defaultCnt);
							}
							chkDate = true;
						}
					}

					if(!chkDate){
						if(unit.equals("w")){
							series.add(week,(double)defaultCnt);
						}else if(unit.equals("m")){
							series.add(month,(double)defaultCnt);
						}else if(unit.equals("d")){
							series.add(day,(double)defaultCnt);
							
						}
					}
				}

				
				dataset.addSeries(series);
				
				
			} catch(Exception ex) {
				System.out.println(" ================ MakeChart setChartDataIssue2 ERROR ==================");
				ex.printStackTrace();
				System.out.println("MakeChart :    "+ex+"    "+ex.getMessage());
			}finally{
				series = null; day = null; week = null; month = null;
			}
			return dataset;
		}
		
		
		// 트위터용 라인차트를 만들어 chart url 반환
		public String getKeywordLineChartTwitter(String sDate, String eDate, ArrayList psArrayData, String chartName, int width, int height){
			String result = "";
			DateUtil du = new DateUtil();
			JFreeChart chart = null;
			TimeSeriesCollection dataset = null;
			XYPlot plot = null;
			NumberAxis yAxis = null;
		    XYLineAndShapeRenderer renderer = null;
		    XYItemRenderer renderer1 = null;
			ChartRenderingInfo info2 = null;
			DateAxis xAxis = null;
			
			try{
				//데이터 셋 생성
				dataset = new TimeSeriesCollection();
				
				dataset = setChartDataKeywordCount(sDate.replaceAll("-", ""), eDate.replaceAll("-", ""), psArrayData,"d");
				chart = ChartFactory.createTimeSeriesChart("","", "", dataset, true, true, false);
				
				plot = chart.getXYPlot();
				plot.setDomainCrosshairVisible(true);
				plot.setRangeCrosshairVisible(true);
			    xAxis = (DateAxis)plot.getDomainAxis();
			    
			    //X축 Date 출력방식 설정.
			   	xAxis.setDateFormatOverride(new SimpleDateFormat("MM/dd"));
			   	
			   //날짜값에 따른 출력 텀
			   	int term = 1;
			   	long dateTerm = du.DateDiff(eDate, sDate);
			   	if(dateTerm>20)		   		term = 2;
			   	if(dateTerm>30)		   		term = 3;
			   	if(dateTerm>40)		   		term = 4;
			   	if(dateTerm>50)		   		term = 5;
			   	if(dateTerm>60)		   		term = 6;
			   	if(dateTerm>70)		   		term = 0;
			   	
			   	if(term != 0){
			   		xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,term));
			   	}
			   	
			   	//xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,1));
			   	
			   	//Legend Font
				chart.getLegend().setItemFont(new Font("맑은 고딕", 0, 11));
				chart.getLegend().setBorder(new BlockBorder(1,1,1,1,new Color(200,200,200)));
				chart.setBackgroundPaint(Color.WHITE);
				
				//x축 라벨
			   	//xAxis.setLabel("월/일");
			    	
			    //x축 폰트 , 사이즈
			    //xAxis.setTickLabelFont(new Font("gulim", Font.PLAIN, 10));
			    xAxis.setTickLabelFont(new Font("맑은 고딕", 0, 11));

			    //x축 안쪽눈금 크기
			    xAxis.setTickMarkInsideLength(3);
			    
			    //x축 라벨 폰트,사이즈
			    xAxis.setLabelFont(new Font("맑은 고딕", 0, 11));

			    // x축 범위 
			    //xAxis.setAutoRange(true); 
			    //xAxis.setFixedAutoRange(554400000.0);  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 ) 
			  	
			    //그래프 간격
				//xAxis.setLowerMargin(0.50);
			    
			   	//차트 Bg color
				plot.setBackgroundPaint(Color.WHITE); 
				//plot.setRangeGridlinePaint(Color.blue);
				//plot.setDomainGridlinePaint(Color.blue);
				
				//Y축 소수점 없이 ..
			   	yAxis = (NumberAxis) plot.getRangeAxis();
			    yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			    
			    //Y축 안쪽 눈금크기
			    yAxis.setTickMarkInsideLength(3);
			    
				 // 그래프 라인 색 속성
				 /*renderer = new XYLineAndShapeRenderer();
				 renderer.setSeriesShapesVisible(0, true);
				 renderer.setSeriesShapesVisible(1, true);
				 renderer.setSeriesShapesVisible(2, true);
				 renderer.setSeriesShapesVisible(3, true);
				 plot.setRenderer(renderer);*/
				 
				 renderer1 = plot.getRenderer();
				 
				 renderer1.setSeriesPaint(0, gp0);
				 renderer1.setSeriesPaint(1, gp1);
				 renderer1.setSeriesPaint(2, gp2);
				 renderer1.setSeriesPaint(3, gp3);
				 renderer1.setSeriesPaint(4, gp4);
				 renderer1.setSeriesPaint(5, gp5);
				 renderer1.setSeriesPaint(6, gp6);
				 
				 
				 // 그래프 라인 두께
				renderer1.setSeriesStroke(0,new BasicStroke(3));
				renderer1.setSeriesStroke(1,new BasicStroke(3));
				renderer1.setSeriesStroke(2,new BasicStroke(3));
				renderer1.setSeriesStroke(3,new BasicStroke(3));
				renderer1.setSeriesStroke(4,new BasicStroke(3));
				renderer1.setSeriesStroke(5,new BasicStroke(3));
				renderer1.setSeriesStroke(6,new BasicStroke(3));
			    
				 
				info2 = new ChartRenderingInfo(new StandardEntityCollection());
				if(!new File(filePath).isDirectory() ) new File(filePath).mkdirs(); 
				ChartUtilities.saveChartAsPNG(new File(filePath+chartName),chart,width,height,info2);
				result = chartURL+chartName;
				
				
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				chart = null; dataset = null; plot = null; info2 = null; renderer1 = null; renderer = null; yAxis = null; xAxis = null;
			}
			
			return result;
		}
		
		// 트위터용 라인차트를 만들어 chart url 반환
		public String getDailyLineChartTwitter(String sDate, String eDate, ArrayList psArrayData, String chartName, int width, int height){
			String result = "";
			DateUtil du = new DateUtil();
			JFreeChart chart = null;
			TimeSeriesCollection dataset = null;
			org.jfree.chart.plot.XYPlot plot = null;
			NumberAxis yAxis = null;
			XYItemRenderer renderer1 = null;
			ChartRenderingInfo info2 = null;
			DateAxis xAxis = null;
			try{
				//데이터 셋 생성
				dataset = new TimeSeriesCollection();
				
				dataset = setChartDataDateCount(sDate.replaceAll("-", ""), eDate.replaceAll("-", ""), psArrayData,"d");
				chart = ChartFactory.createTimeSeriesChart("","", "", dataset, true, true, false);
				
				plot = chart.getXYPlot();
				//plot.setDomainCrosshairVisible(true);
				//plot.setRangeCrosshairVisible(true);
				plot.setOutlineVisible(true);
				
				xAxis = (DateAxis)plot.getDomainAxis();
				
				//X축 Date 출력방식 설정.
				xAxis.setDateFormatOverride(new SimpleDateFormat("MM/dd"));
				
				//날짜값에 따른 출력 텀
			   	int term = 1;
			   	long dateTerm = du.DateDiff(eDate, sDate);
			   	if(dateTerm>20)		   		term = 2;
			   	if(dateTerm>30)		   		term = 3;
			   	if(dateTerm>40)		   		term = 4;
			   	if(dateTerm>50)		   		term = 5;
			   	if(dateTerm>60)		   		term = 6;
			   	if(dateTerm>70)		   		term = 0;
			   	
			   	if(term != 0){
			   		xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,term));
			   	}
			   	
				//xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,1));
				
				//Legend Font
				chart.getLegend().setItemFont(new Font("맑은 고딕", 0, 11));
				chart.getLegend().setBorder(new BlockBorder(1,1,1,1,new Color(200,200,200)));
				//chart.setBackgroundPaint(new Color(246,246,246));
				
				//x축 라벨
				//xAxis.setLabel("월/일");
				
				//x축 폰트 , 사이즈
				//xAxis.setTickLabelFont(new Font("gulim", Font.PLAIN, 10));
				xAxis.setTickLabelFont(new Font("맑은 고딕", 0, 11));
				
				//x축 안쪽눈금 크기
				//xAxis.setTickMarkInsideLength(3);
				//xAxis.setTickMarkOutsideLength(3);
				//x축 라벨 폰트,사이즈
				xAxis.setLabelFont(new Font("맑은 고딕", 0, 11));
				
				// x축 범위 
				//xAxis.setAutoRange(true); 
				//xAxis.setFixedAutoRange(554400000.0);  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 ) 
				
				//그래프 간격
				//xAxis.setLowerMargin(0.50);
				
				//차트 Bg color
				plot.setBackgroundPaint(Color.WHITE); 
				//plot.setRangeGridlinePaint(Color.blue);
				//plot.setDomainGridlinePaint(Color.blue);
				
				//Y축 소수점 없이 ..
				yAxis = (NumberAxis) plot.getRangeAxis();
				//yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
				
				//Y축 안쪽 눈금크기
				//yAxis.setTickMarkInsideLength(3);
				//yAxis.setTickMarkOutsideLength(3);
				
				xAxis.setAxisLineVisible(true);
				yAxis.setAxisLineVisible(true);
				
				//Marker
				/*XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
				renderer.setSeriesShapesVisible(0, true);
				renderer.setSeriesShapesVisible(1, true);
				renderer.setSeriesShapesVisible(2, true);
				renderer.setSeriesShapesVisible(3, true);
				renderer.setSeriesShapesVisible(4, true);
				renderer.setSeriesShapesVisible(5, true);
				renderer.setSeriesShapesVisible(6, true);
				
				plot.setRenderer(renderer);*/
				
				
				
				// 그래프 라인 색 속성
				renderer1 = plot.getRenderer();
				renderer1.setSeriesPaint(0, gp0);
				renderer1.setSeriesPaint(1, gp1);
				renderer1.setSeriesPaint(2, gp2);
				renderer1.setSeriesPaint(3, gp3);
				renderer1.setSeriesPaint(4, gp4);
				renderer1.setSeriesPaint(5, gp5);
				renderer1.setSeriesPaint(6, gp6);
				
				
				// 그래프 라인 두께
				renderer1.setSeriesStroke(0,new BasicStroke(3));
				renderer1.setSeriesStroke(1,new BasicStroke(3));
				renderer1.setSeriesStroke(2,new BasicStroke(3));
				renderer1.setSeriesStroke(3,new BasicStroke(3));
				renderer1.setSeriesStroke(4,new BasicStroke(3));
				renderer1.setSeriesStroke(5,new BasicStroke(3));
				renderer1.setSeriesStroke(6,new BasicStroke(3));
				
				
				//info2 = new ChartRenderingInfo(new StandardEntityCollection());
				if(!new File(filePath).isDirectory() ) new File(filePath).mkdirs(); 
				ChartUtilities.saveChartAsPNG(new File(filePath+chartName),chart,width,height,info2);
				result = chartURL+chartName;
				
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			finally{
				chart = null; dataset = null; plot = null; info2 = null; renderer1 = null; yAxis = null; xAxis = null;
			}
			
			return result;
		}
		
		// 트위터용 라인차트를 만들어 chart url 반환
		public String getDailyLineChartTwitter2(String sDate, String eDate, ArrayList psArrayData, String chartName, int width, int height){
			String result = "";
			DateUtil du = new DateUtil();
			JFreeChart chart = null;
			TimeSeriesCollection dataset = null;
			XYPlot plot = null;
			NumberAxis yAxis = null;
			DateAxis xAxis = null;
			XYLineAndShapeRenderer renderer = null;
			XYItemRenderer renderer1 = null;
			ChartRenderingInfo info2 = null;
			try{
				//데이터 셋 생성
				dataset = new TimeSeriesCollection();
				
				dataset = setChartDataDateCount(sDate.replaceAll("-", ""), eDate.replaceAll("-", ""), psArrayData,"d");
				chart = ChartFactory.createTimeSeriesChart("","", "", dataset, true, true, false);
				
				plot = chart.getXYPlot();
				plot.setDomainCrosshairVisible(true);
				plot.setRangeCrosshairVisible(true);
				xAxis = (DateAxis)plot.getDomainAxis();
				
				//X축 Date 출력방식 설정.
				xAxis.setDateFormatOverride(new SimpleDateFormat("MM/dd"));
				
				//날짜값에 따른 출력 텀
				int term = 1;
				long dateTerm = du.DateDiff(eDate, sDate);
				if(dateTerm>20)		   		term = 2;
				if(dateTerm>30)		   		term = 3;
				if(dateTerm>40)		   		term = 4;
				if(dateTerm>50)		   		term = 5;
				if(dateTerm>60)		   		term = 6;
				if(dateTerm>70)		   		term = 0;
				
				if(term != 0){
					xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,term));
				}
				
				//xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,1));
				
				//Legend Font
				chart.getLegend().setItemFont(new Font("맑은 고딕", 0, 11));
				chart.getLegend().setBorder(new BlockBorder(1,1,1,1,new Color(200,200,200)));
				chart.setBackgroundPaint(Color.WHITE);
				
				//x축 라벨
				//xAxis.setLabel("월/일");
				
				//x축 폰트 , 사이즈
				//xAxis.setTickLabelFont(new Font("gulim", Font.PLAIN, 10));
				xAxis.setTickLabelFont(new Font("맑은 고딕", 0, 11));
				
				//x축 안쪽눈금 크기
				xAxis.setTickMarkInsideLength(3);
				
				//x축 라벨 폰트,사이즈
				xAxis.setLabelFont(new Font("맑은 고딕", 0, 11));
				
				// x축 범위 
				//xAxis.setAutoRange(true); 
				//xAxis.setFixedAutoRange(554400000.0);  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 ) 
				
				//그래프 간격
				//xAxis.setLowerMargin(0.50);
				
				//차트 Bg color
				plot.setBackgroundPaint(Color.WHITE); 
				//plot.setRangeGridlinePaint(Color.blue);
				//plot.setDomainGridlinePaint(Color.blue);
				
				//Y축 소수점 없이 ..
				yAxis = (NumberAxis) plot.getRangeAxis();
				yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
				
				//Y축 안쪽 눈금크기
				yAxis.setTickMarkInsideLength(3);
				
				
				/*// 그래프 라인 색 속성
				renderer = new XYLineAndShapeRenderer();
				renderer.setSeriesShapesVisible(0, true);
				renderer.setSeriesShapesVisible(1, true);
				renderer.setSeriesShapesVisible(2, true);
				renderer.setSeriesShapesVisible(3, true);
				renderer.setSeriesShapesVisible(4, true);
				renderer.setSeriesShapesVisible(5, true);
				renderer.setSeriesShapesVisible(6, true);
				
				plot.setRenderer(renderer);*/
				
				
				
				renderer1 = plot.getRenderer();
				renderer1.setSeriesPaint(0, gp1);
				renderer1.setSeriesPaint(1, gp0);
				renderer1.setSeriesPaint(2, gp3);
				renderer1.setSeriesPaint(3, gp2);
				renderer1.setSeriesPaint(4, gp4);
				renderer1.setSeriesPaint(5, gp5);
				renderer1.setSeriesPaint(6, gp6);
				
				
				// 그래프 라인 두께
				renderer1.setSeriesStroke(0,new BasicStroke(3));
				renderer1.setSeriesStroke(1,new BasicStroke(3));
				renderer1.setSeriesStroke(2,new BasicStroke(3));
				renderer1.setSeriesStroke(3,new BasicStroke(3));
				renderer1.setSeriesStroke(4,new BasicStroke(3));
				renderer1.setSeriesStroke(5,new BasicStroke(3));
				renderer1.setSeriesStroke(6,new BasicStroke(3));
				
				
				info2 = new ChartRenderingInfo(new StandardEntityCollection());
				if(!new File(filePath).isDirectory() ) new File(filePath).mkdirs(); 
				ChartUtilities.saveChartAsPNG(new File(filePath+chartName),chart,width,height,info2);
				result = chartURL+chartName;
				
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			finally{
				chart = null; dataset = null; plot = null; info2 = null; renderer1 = null; yAxis = null; renderer = null; xAxis = null;
			}
			
			return result;
		}
		
		
		// 파이 만들어 chart url 반환
		public String getPieChart(ArrayList psArrayData, String form, String chartName, int width, int height){
			String result = "";
			JFreeChart chart = null;
			
			DefaultPieDataset dataset = null;
			org.jfree.chart.plot.PiePlot plot = null;
			LegendTitle lt = null;
			ChartRenderingInfo info2 = null;
			try{
				//데이터 셋 생성
				dataset = new DefaultPieDataset();
				
				String[] tmpData = null;
				double pos = 0;
				double neg = 0;
				double neu = 0;
				if(form.equals("tot")){
					
					for(int i=0; i<psArrayData.size(); i++){
						tmpData = null;
						tmpData = (String[])psArrayData.get(i);
						
						if(tmpData[2]!=null && !tmpData[2].equals("0")){
							pos += Double.parseDouble(tmpData[2]);
							//dataset.setValue(tmpData[1], Double.parseDouble(tmpData[2]));
						}
						if(tmpData[3]!=null && !tmpData[3].equals("0")){
							neg += Double.parseDouble(tmpData[3]);
							//dataset.setValue(tmpData[1], Double.parseDouble(tmpData[3]));
						}
						if(tmpData[4]!=null && !tmpData[4].equals("0")){
							neu += Double.parseDouble(tmpData[4]);
							//dataset.setValue(tmpData[1], Double.parseDouble(tmpData[4]));
						}
					}
					if(pos>0) dataset.setValue("긍정", pos);
					if(neg>0) dataset.setValue("부정", neg);
					if(neu>0) dataset.setValue("중립", neu);
				}else if(form.equals("pos")){
					psArrayData = sortArray(psArrayData, 2, "DESC");
					for(int i=0; i<psArrayData.size(); i++){
						tmpData = null;
						tmpData = (String[])psArrayData.get(i);
						
						//if(tmpData[2]!=null && !tmpData[2].equals("0") && !tmpData[2].equals("1"))
						if(tmpData[2]!=null && !tmpData[2].equals("0"))
							dataset.setValue(tmpData[0], Double.parseDouble(tmpData[2]));
						
						if(psArrayData.size()>5){
							if(i==4)
								break;
						}
					}
				}else if(form.equals("neg")){
					psArrayData = sortArray(psArrayData, 3, "DESC");
					for(int i=0; i<psArrayData.size(); i++){
						tmpData = null;
						tmpData = (String[])psArrayData.get(i);
						
						//if(tmpData[3]!=null && !tmpData[3].equals("0") && !tmpData[3].equals("1")){
						if(tmpData[3]!=null && !tmpData[3].equals("0")){
							dataset.setValue(tmpData[0], Double.parseDouble(tmpData[3]));
						//System.out.println("neg : "+tmpData[0]+"/"+tmpData[3]);
						}
						if(psArrayData.size()>5){
							if(i==4)
								break;
						}
					}
					
				}
				
				chart = ChartFactory.createPieChart("",dataset,true,true,false);		
				
				
				//범례 위치 조정
				lt = chart.getLegend();
				lt.setItemFont(new java.awt.Font("맑은 고딕",0,11));
				lt.setBorder(new BlockBorder(1,1,1,1,new Color(200,200,200)));
				lt.setPosition(RectangleEdge.RIGHT);					//범례의 플롯 대비 위치
				lt.setVerticalAlignment(VerticalAlignment.CENTER);		//범례의 세로 위치
				
			    //차트 백그라운 색깔
				chart.setBackgroundPaint(Color.WHITE);
				chart.setBorderVisible(false);
				chart.setBorderPaint(new Color(225,225,225));
				chart.setBorderStroke(new BasicStroke(3));

				//파이 속성 및 라벨
				plot = (PiePlot)chart.getPlot();	
				
				plot.setBackgroundAlpha(0);
				
				plot.setNoDataMessage("No Data");
				
				//plot.setSimpleLabels(true);				
				plot.setLabelBackgroundPaint(Color.WHITE);
				plot.setLabelOutlinePaint(Color.WHITE);
				plot.setLabelOutlinePaint(new Color(225,225,225));
				plot.setLabelLinksVisible(true);
				//plot.setLabelLinkMargin(1);
				plot.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);
				//plot.setShadowXOffset(0.0);
				//plot.setShadowYOffset(0.0);
				plot.setOutlinePaint(Color.WHITE);
				//라벨 퍼센트 표시
				//{0}: 범례, {1}: 카운트, {2} 비율%
				plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
						"{2}({1})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
				));
				
				//plot.setIgnoreZeroValues(true);					        
				plot.setSectionOutlinesVisible(true);	
				//plot.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);
				
				plot.setLabelFont(new java.awt.Font("돋움",0,11));
				
				
				if(form.equals("tot")){
					if(pos==0){ 
						gp0 = new GradientPaint(0.0f, 0.0f, new Color(192,80,77),     0.0f, 0.0f, new Color(192,80,77));
						gp1 = new GradientPaint(0.0f, 0.0f, new Color(79,129,189), 0.0f, 0.0f, new Color(79,129,189));
					}else{
						gp0 = new GradientPaint(0.0f, 0.0f, new Color(79,129,189), 0.0f, 0.0f, new Color(79,129,189));
						gp1 = new GradientPaint(0.0f, 0.0f, new Color(192,80,77),     0.0f, 0.0f, new Color(192,80,77));
					}
					
				}else if(form.equals("pos")){
					gp0 = new GradientPaint(0.0f, 0.0f, new Color(153,51,0), 0.0f, 0.0f, new Color(153,51,0));
					gp1 = new GradientPaint(0.0f, 0.0f, new Color(79,129,189), 0.0f, 0.0f, new Color(79,129,189));
					//gp1 = new GradientPaint(0.0f, 0.0f, new Color(204,0,0),     0.0f, 0.0f, new Color(204,0,0));
					
				}else if(form.equals("neg")){
					gp0 = new GradientPaint(0.0f, 0.0f, new Color(153,51,0), 0.0f, 0.0f, new Color(153,51,0));
					gp1 = new GradientPaint(0.0f, 0.0f, new Color(79,129,189), 0.0f, 0.0f, new Color(79,129,189));
					//gp1 = new GradientPaint(0.0f, 0.0f, new Color(204,0,0),     0.0f, 0.0f, new Color(204,0,0));
					
				}
				
				
				plot.setSectionPaint(0,gp0);
				plot.setSectionPaint(1,gp1);
				plot.setSectionPaint(2,gp2);
				plot.setSectionPaint(3,gp3);
				plot.setSectionPaint(4,gp4);
				plot.setSectionPaint(5,gp5);
				plot.setSectionPaint(6,gp6);
				plot.setSectionPaint(7,gp7);
				plot.setSectionPaint(8,gp8);
				plot.setSectionPaint(9,gp9);
				plot.setSectionPaint(10,gp10);
				plot.setSectionPaint(11,gp11);
				
				
				info2 = new ChartRenderingInfo(new StandardEntityCollection());
				
				if(!new File(filePath).isDirectory() ) new File(filePath).mkdirs(); 
				ChartUtilities.saveChartAsPNG(new File(filePath+chartName),chart,width,height,info2);
				result = chartURL+chartName;
				
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			finally{
				chart = null; dataset = null; plot = null; lt = null; info2 = null;
			}
			
			return result;
		}
		
		
		// stack 바차트를 만들어 chart url 반환
		public String getXYStackBarChart(ArrayList psArrayData, ArrayList arrDate, String chartName, int width, int height){
			String result = "";
			JFreeChart chart = null;
			CategoryTableXYDataset dataset = new CategoryTableXYDataset();
			XYPlot plot = null;
			LegendTitle lt = null;
			DateAxis xAxis = null;
			NumberAxis yAxis = null;
			ChartRenderingInfo info2 = null;
			StackedXYBarRenderer renderer = null;
			try{
				//데이터 셋 생성
				dataset = new CategoryTableXYDataset();
				
				String[] tmp = null;
				String date = "";
				double[] cnt = null; 
				Day day = null;
				for(int j=0; j<arrDate.size(); j++){
					date = "";
					date = (String)arrDate.get(j);
					cnt = new double[4];
					for(int k=0; k<cnt.length; k++){
						cnt[k] = 0;
					}
					day = new Day(Integer.parseInt(date.substring(6,8)), Integer.parseInt(date.substring(4, 6)), Integer.parseInt(date.substring(0, 4)));
					//System.out.println("date:"+date);
					for(int i=0; i<psArrayData.size(); i++){
						tmp = (String[])psArrayData.get(i); 
						if(date.equals(tmp[1]) && tmp[0].equals("1") && !tmp[3].equals("0")){
							cnt[0] += Double.parseDouble(tmp[3]); 
						}else if(date.equals(tmp[1]) && tmp[0].equals("1") && !tmp[4].equals("0")){
							cnt[1] += Double.parseDouble("-"+tmp[4]); 
						}else if(date.equals(tmp[1]) && tmp[0].equals("2") && !tmp[3].equals("0")){
							cnt[2] += Double.parseDouble(tmp[3]); 
						}else if(date.equals(tmp[1]) && tmp[0].equals("2") && !tmp[4].equals("0")){
							cnt[3] += Double.parseDouble("-"+tmp[4]); 
						}
						dataset.add(day.getFirstMillisecond(),cnt[0],"긍정기사");
						dataset.add(day.getFirstMillisecond(),cnt[2],"긍정게시");
						dataset.add(day.getFirstMillisecond(),cnt[1],"부정기사");
						dataset.add(day.getFirstMillisecond(),cnt[3],"부정게시");
						
					}
					
				}
				
				renderer = new StackedXYBarRenderer(0.0);
			      
				
				plot = new XYPlot(dataset,new NumberAxis(""), new NumberAxis(""),renderer);
			    chart = new JFreeChart(plot);
				//chart = ChartFactory.createStackedBarChart("","", "", dataset, PlotOrientation.VERTICAL, true, false, false);		
				
				
				//범례 위치 조정
				lt = chart.getLegend();
				lt.setItemFont(new java.awt.Font("맑은 고딕",0,11));
				lt.setBorder(new BlockBorder(1,1,1,1,new Color(200,200,200)));
				//lt.setPosition(RectangleEdge.RIGHT);					//범례의 플롯 대비 위치
				//lt.setVerticalAlignment(VerticalAlignment.CENTER);		//범례의 세로 위치
				
				//차트 백그라운 색깔
				chart.setBackgroundPaint(Color.WHITE);
				chart.setBorderVisible(false);
				//chart.setBorderPaint(new Color(225,225,225));
				//chart.setBorderStroke(new BasicStroke(3));
				
				// 속성 및 라벨
				plot = (XYPlot) chart.getPlot();					
				plot.setOutlineVisible(false);
				//plot.setBackgroundAlpha(0);
				
				//차트 Bg color
				//plot.setBackgroundPaint(Color.WHITE); 
				plot.setRangeGridlinesVisible(false);
				plot.setDomainGridlinesVisible(false);
				//plot.setRangeGridlinePaint(Color.blue);
				//plot.setDomainGridlinePaint(Color.blue);
				
				
				plot.setDomainAxis(new DateAxis(""));
								
				plot.setNoDataMessage("No Data");
				
				plot.setRangeZeroBaselineVisible(true);
				
				xAxis = (DateAxis)plot.getDomainAxis();
			    xAxis.setTickMarksVisible(true);
			    //X축 Date 출력방식 설정.
			   	xAxis.setDateFormatOverride(new SimpleDateFormat("MM/dd"));
			   	
			   //날짜값에 따른 출력 텀
			   	int term = 1;
			   	long dateTerm = arrDate.size();
			   	if(dateTerm>20)		   		term = 2;
			   	if(dateTerm>30)		   		term = 3;
			   	if(dateTerm>40)		   		term = 4;
			   	if(dateTerm>50)		   		term = 5;
			   	if(dateTerm>60)		   		term = 6;
			   	if(dateTerm>70)		   		term = 0;
			   	
			   	if(term != 0){
			   		xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,term));
			   	}
			   	  
				//xAxis.setTickLabelInsets(new RectangleInsets(1.0, 1.0, 1.0, 1.0));
				
				//xAxis.setAutoRange(true);
				
				//x축 라벨
			   	//xAxis.setLabel("월/일");
			    	
			    //x축 폰트 , 사이즈
			    //xAxis.setTickLabelFont(new Font("gulim", Font.PLAIN, 10));
			    xAxis.setTickLabelFont(new Font("맑은 고딕", 0, 11));

			    //x축 안쪽눈금 크기
			    xAxis.setTickMarkOutsideLength(3);
			    //xAxis.setTickMarksVisible(false);
			    
			    //x축 라벨 폰트,사이즈
			    xAxis.setLabelFont(new Font("맑은 고딕", 0, 11));
			    
			    //bar 간격
				renderer.setMargin(0.5);
				
			    //좌우 여백
				//xAxis.setLowerMargin(0.45);  //좌
				//xAxis.setUpperMargin(0.35);  //우
				
			    // x축 범위 
			    //xAxis.setAutoRange(true); 
				if(dateTerm>10){
					xAxis.setFixedAutoRange((79200000*dateTerm)+(79200000));  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 )
				}
			  	
				//Y축 소수점 없이 ..
			   	yAxis = (NumberAxis) plot.getRangeAxis();
			   	yAxis.setTickLabelFont(new Font("맑은 고딕", 0, 11));
			    yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			    
			    //Y축 안쪽 눈금크기
			    //yAxis.setTickMarkInsideLength(3);
			    yAxis.setTickMarkOutsideLength(3);
			    //yAxis.setTickMarksVisible(false);
				
				
			    GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, new Color(55,96,146),     0.0f, 0.0f, new Color(55,96,146));
				GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, new Color(85,142,213), 0.0f, 0.0f, new Color(85,142,213));
				GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, new Color(204,0,0),     0.0f, 0.0f, new Color(204,0,0));
				GradientPaint gp3 = new GradientPaint(0.0f, 0.0f, new Color(240,132,124), 0.0f, 0.0f, new Color(240,132,124));
				
				renderer = (StackedXYBarRenderer)plot.getRenderer();
				//Standard 
				renderer.setBarPainter(new StandardXYBarPainter());
			    //stack outline
				renderer.setDrawBarOutline(false);
				//shadow
			    renderer.setShadowVisible(false);
			      
				//renderer.setAutoPopulateSeriesStroke(false);
				
				
				//bar 그림자
				//renderer.setShadowVisible(false);
				
				renderer.setSeriesPaint(0, gp0);
				renderer.setSeriesPaint(1, gp1);
				renderer.setSeriesPaint(2, gp2);
				renderer.setSeriesPaint(3, gp3);
				
				
				
				
				//info2 = new ChartRenderingInfo(new StandardEntityCollection());
				
				if(!new File(filePath).isDirectory() ) new File(filePath).mkdirs(); 
				ChartUtilities.saveChartAsPNG(new File(filePath+chartName),chart,width,height,info2);
				result = chartURL+chartName;
				
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			finally{
				chart = null; dataset = null; plot = null; lt = null; xAxis = null; info2 = null; renderer = null;
			}
			
			return result;
		}
		
		// 이슈/채널별 라인차트를 만들어 chart url 반환
		public String getLineChartChanel(String sDate, String eDate, ArrayList psArrayData, String chartName, int width, int height){
			String result = "";
			DateUtil du = new DateUtil();
			JFreeChart chart = null;
			TimeSeriesCollection dataset = null;
			XYPlot plot = null;
			ChartRenderingInfo info2 = null;
			DateAxis xAxis = null;
			NumberAxis yAxis = null;
			XYItemRenderer renderer1 = null;
			try{
				//데이터 셋 생성
				dataset = new TimeSeriesCollection();
				
				dataset = setChartDataKeywordCount(sDate.replaceAll("-", ""), eDate.replaceAll("-", ""), psArrayData,"d");
				chart = ChartFactory.createTimeSeriesChart("","", "", dataset, true, true, false);
				
				//Legend Font
				chart.getLegend().setItemFont(new Font("맑은 고딕", 0, 11));
				chart.getLegend().setBorder(new BlockBorder(1,1,1,1,new Color(200,200,200)));
				//chart.setBackgroundImageAlpha((float)0.1);
				
				
				plot = chart.getXYPlot();
				plot.setOutlineVisible(true);
				
				//plot.setDomainCrosshairVisible(true);
				//plot.setRangeCrosshairVisible(true);
				
				//차트 Bg color
				plot.setBackgroundPaint(Color.WHITE); 
				//plot.setRangeGridlinePaint(Color.blue);
				//plot.setDomainGridlinePaint(Color.blue);
				
			    xAxis = (DateAxis)plot.getDomainAxis();
			    
			    xAxis.setTickMarksVisible(true);
			    //X축 Date 출력방식 설정.
			   	xAxis.setDateFormatOverride(new java.text.SimpleDateFormat("MM/dd"));
			   	
			   //날짜값에 따른 출력 텀
			   	int term = 1;
			   	long dateTerm = du.DateDiff(eDate, sDate);
			   	if(dateTerm>20)		   		term = 2;
			   	if(dateTerm>30)		   		term = 3;
			   	if(dateTerm>40)		   		term = 4;
			   	if(dateTerm>50)		   		term = 5;
			   	if(dateTerm>60)		   		term = 6;
			   	if(dateTerm>70)		   		term = 0;
			   	
			   	if(term != 0){
			   		xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,term));
			   	}
			   	
			   	//xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,1));
			   	
				
				//x축 라벨
			   	//xAxis.setLabel("월/일");
			    
			   	//xAxis.setTickLabelInsets(new RectangleInsets(1.0, 1.0, 1.0, 1.0));
			   	
			    //x축 폰트 , 사이즈
			    //xAxis.setTickLabelFont(new Font("gulim", Font.PLAIN, 10));
			    xAxis.setTickLabelFont(new Font("맑은 고딕", 0, 11));
			    
			    //x축 안쪽눈금 크기
			    //xAxis.setTickMarkInsideLength(3);
			    
			    //x축 라벨 폰트,사이즈
			    xAxis.setLabelFont(new Font("맑은 고딕", 0, 11));

			    // x축 범위 
			    //xAxis.setAutoRange(true); 
			    //xAxis.setFixedAutoRange(554400000.0);  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 ) 
			  	
			    //그래프 간격
				//xAxis.setLowerMargin(0.50);
			    
				
				//Y축 소수점 없이 ..
			   	yAxis = (NumberAxis) plot.getRangeAxis();
			   	
			    yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			    
			    //Y축 안쪽 눈금크기
			    //yAxis.setTickMarkInsideLength(3);
			    
			    //xy축 라인
			    //xAxis.setAxisLineVisible(true);
				yAxis.setAxisLineVisible(true);
				
				xAxis.setTickMarkOutsideLength(3);
				yAxis.setTickMarkOutsideLength(3);
				
				//Marker
				/*XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
				renderer.setSeriesShapesVisible(0, true);
				renderer.setSeriesShapesVisible(1, true);
				renderer.setSeriesShapesVisible(2, true);
				renderer.setSeriesShapesVisible(3, true);
				renderer.setSeriesShapesVisible(4, true);
				renderer.setSeriesShapesVisible(5, true);
				renderer.setSeriesShapesVisible(6, true);
				renderer.setSeriesShapesVisible(7, true);
				renderer.setSeriesShapesVisible(8, true);
				renderer.setSeriesShapesVisible(9, true);
				renderer.setSeriesShapesVisible(10, true);
				
				plot.setRenderer(renderer);*/
				/*
				 * 72,124,186
				 * 189,72,68
				 * 146,181,74
				 * 129,101,163
				 * 65,170,197
				 * */
				
				GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, new Color(72,124,186), 0.0f, 0.0f, new Color(72,124,186));
				GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, new Color(189,72,68),     0.0f, 0.0f, new Color(189,72,68));
				GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, new Color(146,181,74),     0.0f, 0.0f, new Color(146,181,74));
				GradientPaint gp3 = new GradientPaint(0.0f, 0.0f, new Color(129,101,163), 0.0f, 0.0f, new Color(129,101,163));
				
				GradientPaint gp4 = new GradientPaint(0.0f, 0.0f, new Color(65,170,197),     0.0f, 0.0f, new Color(65,170,197));
				
				
			    // 그래프 라인 색 속성
				renderer1 = plot.getRenderer();
				
				
				renderer1.setSeriesPaint(0, gp0);
				renderer1.setSeriesPaint(1, gp1);
				renderer1.setSeriesPaint(2, gp2);
				renderer1.setSeriesPaint(3, gp3);
				renderer1.setSeriesPaint(4, gp4);
				renderer1.setSeriesPaint(5, gp5);
				renderer1.setSeriesPaint(6, gp6);
				renderer1.setSeriesPaint(7, gp7);
				renderer1.setSeriesPaint(8, gp8);
				renderer1.setSeriesPaint(9, gp9);
				renderer1.setSeriesPaint(10, gp10);
				
				
				// 그래프 라인 두께
				renderer1.setSeriesStroke(0,new BasicStroke(3));
				renderer1.setSeriesStroke(1,new BasicStroke(3));
				renderer1.setSeriesStroke(2,new BasicStroke(3));
				renderer1.setSeriesStroke(3,new BasicStroke(3));
				renderer1.setSeriesStroke(4,new BasicStroke(3));
				renderer1.setSeriesStroke(5,new BasicStroke(3));
				renderer1.setSeriesStroke(6,new BasicStroke(3));
				renderer1.setSeriesStroke(7,new BasicStroke(3));
				renderer1.setSeriesStroke(8,new BasicStroke(3));
				renderer1.setSeriesStroke(9,new BasicStroke(3));
				renderer1.setSeriesStroke(10,new BasicStroke(3));
			    
				 
				//info2 = new ChartRenderingInfo(new StandardEntityCollection());
				
				if(!new File(filePath).isDirectory() ) new File(filePath).mkdirs(); 
				ChartUtilities.saveChartAsPNG(new File(filePath+chartName),chart,width,height,info2);
				result = chartURL+chartName;
				
				
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				chart = null; dataset = null; plot = null; xAxis = null; info2 = null; yAxis = null; renderer1 = null;
			}
			
			return result;
		}
		
		/**
		 * 어레이를 받아 인덱스 데이터로 정렬 한다.
		 * @param psArrayData  String[]
		 * @param idx  String[idx]
		 * @param order ASC DESC
		 * @return ArrayList
		 */
		
		public ArrayList sortArray(ArrayList psArrayData, int idx, String order){
			int i = 0, j = 0, compare = 0;
			
			for (i = 0; i < psArrayData.size()-1; i++){
				compare = i;
                for (j = i+1; j < psArrayData.size(); j++){
                	if(order==null || order.equals("ASC") || order.equals("")){
	                    if (Integer.parseInt(((String[])psArrayData.get(compare))[idx]) > Integer.parseInt(((String[])psArrayData.get(j))[idx])){
	                    	compare = j;
	                    }
                	}else{
                		if (Integer.parseInt(((String[])psArrayData.get(compare))[idx]) < Integer.parseInt(((String[])psArrayData.get(j))[idx])){
                			compare = j;
                		}
                	}
                }
               //System.out.println("compare:"+compare+" /i:"+i);
               
                String[] temp = (String[])psArrayData.get(i);
                
                psArrayData.set(i, psArrayData.get(compare));
                psArrayData.set(compare, temp);
	         }
		
			return psArrayData;
		}
		
		
		// 트위터용 라인차트를 만들어 chart url 반환
		public String getDailyLineChartTwitter3(String sDate, 
												String eDate, 
												ArrayList psArrayData, 
												String chartName, 
												int width, 
												int height
												,String gTitle
												,int baseCnt
												,int yminCnt
												,int ymaxCnt
												,int tick
												,int tick_cnt
												){
			String result = "";
			DateUtil du = new DateUtil();
			JFreeChart chart = null;
			TimeSeriesCollection dataset = null;
			XYPlot plot = null;
			NumberAxis yAxis = null;
			DateAxis xAxis = null;
			XYLineAndShapeRenderer renderer = null;
			XYItemRenderer renderer1 = null;
			ChartRenderingInfo info2 = null;
			try{

				//데이터 셋 생성
				dataset = new TimeSeriesCollection();

				//LegacyTheme 적용
				//ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
				//BarRenderer.setDefaultBarPainter(new StandardBarPainter());
				
				dataset = setChartDataDateCountStack(sDate.replaceAll("-", ""), eDate.replaceAll("-", ""), psArrayData,"d", baseCnt);
				chart = ChartFactory.createTimeSeriesChart("","", "", dataset, true, true, false);

				plot = chart.getXYPlot();
				plot.setDomainCrosshairVisible(true);
				plot.setRangeCrosshairVisible(true);
				xAxis = (DateAxis)plot.getDomainAxis();
				
				//X축 Date 출력방식 설정.
				xAxis.setDateFormatOverride(new SimpleDateFormat("MM/dd"));
				
				//날짜값에 따른 출력 텀
				int term = 1;
				long dateTerm = du.DateDiff(eDate, sDate);
				if(dateTerm>20)		   		term = 2;
				if(dateTerm>30)		   		term = 3;
				if(dateTerm>40)		   		term = 4;
				if(dateTerm>50)		   		term = 5;
				if(dateTerm>60)		   		term = 6;
				if(dateTerm>70)		   		term = 0;
				
				if(term != 0){
					xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,term));
				}
				
				//xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,1));
				
				chart.setTitle(gTitle);
				chart.getTitle().setFont(new Font("맑은 고딕", 0, 13));
				
				//Legend Font
				chart.getLegend().setItemFont(new Font("맑은 고딕", 0, 11));
				chart.getLegend().setBorder(new BlockBorder(1,1,1,1,new Color(200,200,200)));
				chart.setBackgroundPaint(Color.WHITE);
				
				//x축 폰트 , 사이즈
				//xAxis.setTickLabelFont(new Font("gulim", Font.PLAIN, 10));
				xAxis.setTickLabelFont(new Font("맑은 고딕", 0, 11));
				
				//x축 안쪽눈금 크기
				xAxis.setTickMarkInsideLength(3);
				
				//좌우 여백
				xAxis.setLowerMargin(0.05);  //좌
				xAxis.setUpperMargin(0.05);  //우
				
				// x축 범위 
				//xAxis.setAutoRange(true); 
				//xAxis.setFixedAutoRange(554400000.0);  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 ) 
				
				//그래프 간격
				//xAxis.setLowerMargin(0.50);
				
				//차트 Bg color
				plot.setBackgroundPaint(Color.WHITE); 
				//plot.setRangeGridlinePaint(Color.blue);
				//plot.setDomainGridlinePaint(Color.blue);
				
				//Y축 소수점 없이 ..
				yAxis = (NumberAxis) plot.getRangeAxis();
				//yAxis.setNumberFormatOverride(NumberFormat.getCurrencyInstance());
				yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
				
				//y축 라벨 폰트,사이즈
				//yAxis.setLabelFont(new Font("맑은 고딕", 0, 9));
				yAxis.setTickLabelFont(new Font("맑은 고딕", 0, 10));
				//Y축 안쪽 눈금크기
				yAxis.setTickMarkInsideLength(3);
				
				//x축 라벨
				//xAxis.setLabel("일자");
				//y축 라벨
				//yAxis.setLabel("트위터 누적수량");
				//x축 라벨 폰트,사이즈
				//xAxis.setLabelFont(new Font("맑은 고딕", 0, 11));
				//y축 라벨 폰트,사이즈
				//yAxis.setLabelFont(new Font("맑은 고딕", 0, 11));
				
				//xAxis.setLabelInsets(new RectangleInsets(0.0, 0.0, 0.0, 360.0));
				//yAxis.setLabelAngle(9.0);
				

				//Y축 범위 설정
				if(ymaxCnt>0){
					yAxis.setRange(yminCnt, ymaxCnt);
				}
				
				renderer = new XYLineAndShapeRenderer();
				if(tick>0){
					// 그래프  tick
					renderer.setSeriesShapesVisible(0, true);
					renderer.setSeriesShapesVisible(1, true);
					renderer.setSeriesShapesVisible(2, true);
					renderer.setUseFillPaint(true);
					renderer.setDrawOutlines(false);
					renderer.setFillPaint(Color.red);
					
				}else{
					renderer.setSeriesShapesVisible(0, false);
					renderer.setSeriesShapesVisible(1, false);
					renderer.setSeriesShapesVisible(2, false);
				}
				// 포인트 라벨 
				if(tick_cnt>0){
					//StandardXYItemLabelGenerator xyItem = new StandardXYItemLabelGenerator("{0}{1}{2}",xAxis.getDateFormatOverride(), NumberFormat.getCurrencyInstance());
					StandardXYItemLabelGenerator xyItem = new StandardXYItemLabelGenerator();
					System.out.println("xyItem.getFormatString(): "+xyItem.getFormatString());
					
					renderer.setSeriesItemLabelsVisible(0, true);
					renderer.setSeriesItemLabelGenerator(0, xyItem);
					renderer.setSeriesItemLabelGenerator(1, xyItem);
					renderer.setSeriesItemLabelGenerator(2, xyItem);
					//renderer.setLegendItemLabelGenerator(new  StandardXYSeriesLabelGenerator("{0} {1}"));
				}

				plot.setRenderer(renderer);
				
				/*LineAndShapeRenderer ShapeRenderer = (LineAndShapeRenderer)plot.getRenderer();
				ShapeRenderer.setShapesVisible(true);
				ShapeRenderer.setDrawOutlines(false);
				ShapeRenderer.setUseFillPaint(true);
				ShapeRenderer.setFillPaint(Color.red);*/
				
				//라벨 퍼센트 표시
				//{0}: 범례, {1}: 카운트, {2} 비율%
				
				
				renderer1 = plot.getRenderer();
				
				//255,187,0
				GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, new Color(255,187,0), 0.0f, 0.0f, new Color(255,187,0));
				
				renderer1.setSeriesPaint(0, gp1);
				renderer1.setSeriesPaint(1, gp0);
				renderer1.setSeriesPaint(2, gp3);
				
				
				// 그래프 라인 두께
				renderer1.setSeriesStroke(0,new BasicStroke(3));
				renderer1.setSeriesStroke(1,new BasicStroke(3));
				renderer1.setSeriesStroke(2,new BasicStroke(3));
				

				info2 = new ChartRenderingInfo(new StandardEntityCollection());
				if(!new File(filePath).isDirectory() ) new File(filePath).mkdirs(); 
				ChartUtilities.saveChartAsPNG(new File(filePath+chartName),chart,width,height,info2);
				result = chartURL+chartName;
				
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			finally{
				chart = null; dataset = null; plot = null; info2 = null; renderer1 = null; yAxis = null; renderer = null; xAxis = null;
			}
			
			return result;
		}
		
		// 트위터용 라인차트를 만들어 chart url 반환
		public String getDailyBarChartTwitter3(String sDate, 
												String eDate, 
												ArrayList psArrayData, 
												String chartName, 
												int width, 
												int height
												,String gTitle
												,int baseCnt
												,int yminCnt
												,int ymaxCnt
												,int tick
												,int tick_cnt
												,ArrayList arrDate
										){
			String result = "";
			JFreeChart chart = null;
			CategoryTableXYDataset dataset = null;
			XYPlot plot = null;
			LegendTitle lt = null;
			DateAxis xAxis = null;
			NumberAxis yAxis = null;
			ChartRenderingInfo info2 = null;
			StackedXYBarRenderer renderer = null;
			try{
				//데이터 셋 생성
				dataset = new CategoryTableXYDataset();
				
				
				String date = "";
				Day day = null;
				for(int j=0; j<arrDate.size(); j++){
					date = "";
					date = (String)arrDate.get(j);
					day = new Day(Integer.parseInt(date.substring(6,8)), Integer.parseInt(date.substring(4, 6)), Integer.parseInt(date.substring(0, 4)));
					ChartBean cInfo = null;
					
					for(int i=0; i<psArrayData.size(); i++){
						cInfo = (ChartBean)psArrayData.get(i); 
						
						if(date.equals(cInfo.getData_date())){
							dataset.add(day.getFirstMillisecond(),Double.parseDouble(cInfo.getData_count()),cInfo.getData_name());
							
						}
					}
				}
				
				renderer = new StackedXYBarRenderer(0.0);
			      
				
				plot = new XYPlot(dataset, new NumberAxis(""), new NumberAxis(""), renderer);
				
			    chart = new JFreeChart(plot);
			    
			  //LegacyTheme 적용
				//ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
				//BarRenderer.setDefaultBarPainter(new StandardBarPainter());
			    
				//chart = ChartFactory.createStackedBarChart("","", "", dataset, PlotOrientation.VERTICAL, true, false, false);		
				
			    chart.setTitle(gTitle);
				chart.getTitle().setFont(new Font("맑은 고딕", 0, 13));
				
				//범례 위치 조정
				lt = chart.getLegend();
				lt.setItemFont(new java.awt.Font("맑은 고딕",0,11));
				lt.setBorder(new BlockBorder(1,1,1,1,new Color(200,200,200)));
				//lt.setPosition(RectangleEdge.RIGHT);					//범례의 플롯 대비 위치
				//lt.setVerticalAlignment(VerticalAlignment.CENTER);		//범례의 세로 위치
				
				//차트 백그라운 색깔
				chart.setBackgroundPaint(Color.WHITE);
				chart.setBorderVisible(false);
				//chart.setBorderPaint(new Color(225,225,225));
				//chart.setBorderStroke(new BasicStroke(3));
				
				// 속성 및 라벨
				plot = (XYPlot) chart.getPlot();					
				plot.setOutlineVisible(false);
				//plot.setBackgroundAlpha(0);
				
				//차트 Bg color
				//plot.setBackgroundPaint(Color.WHITE); 
				plot.setRangeGridlinesVisible(false);
				plot.setDomainGridlinesVisible(false);
				//plot.setRangeGridlinePaint(Color.blue);
				//plot.setDomainGridlinePaint(Color.blue);
				
				
				plot.setDomainAxis(new DateAxis(""));
								
				plot.setNoDataMessage("No Data");
				
				plot.setRangeZeroBaselineVisible(true);
				
				xAxis = (DateAxis)plot.getDomainAxis();
			    xAxis.setTickMarksVisible(true);
			    //X축 Date 출력방식 설정.
			   	xAxis.setDateFormatOverride(new SimpleDateFormat("MM/dd"));
			   	
			   //날짜값에 따른 출력 텀
			   	int term = 1;
			   	long dateTerm = arrDate.size();
			   	if(dateTerm>20)		   		term = 2;
			   	if(dateTerm>30)		   		term = 3;
			   	if(dateTerm>40)		   		term = 4;
			   	if(dateTerm>50)		   		term = 5;
			   	if(dateTerm>60)		   		term = 6;
			   	if(dateTerm>70)		   		term = 0;
			   	
			   	if(term != 0){
			   		xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,term));
			   	}
			   	  
				//xAxis.setTickLabelInsets(new RectangleInsets(1.0, 1.0, 1.0, 1.0));
				
				//xAxis.setAutoRange(true);
				
				//x축 라벨
			   	//xAxis.setLabel("월/일");
			    	
			    //x축 폰트 , 사이즈
			    //xAxis.setTickLabelFont(new Font("gulim", Font.PLAIN, 10));
			    xAxis.setTickLabelFont(new Font("맑은 고딕", 0, 11));

			    //x축 안쪽눈금 크기
			    xAxis.setTickMarkOutsideLength(3);
			    //xAxis.setTickMarksVisible(false);
			    
			    //x축 라벨 폰트,사이즈
			    xAxis.setLabelFont(new Font("맑은 고딕", 0, 11));
			    
			    //bar 간격
				renderer.setMargin(0.5);
				
			    //좌우 여백
				//xAxis.setLowerMargin(0.45);  //좌
				//xAxis.setUpperMargin(0.35);  //우
				
			    // x축 범위 
			    //xAxis.setAutoRange(true);
				xAxis.setFixedAutoRange((79200000*dateTerm)+(79200000*0.5));  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 )
				if(dateTerm>5)	xAxis.setFixedAutoRange((79200000*dateTerm)+(79200000*1));  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 )
				if(dateTerm>15)	xAxis.setFixedAutoRange((79200000*dateTerm)+(79200000*1.5));  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 )
				if(dateTerm>20)	xAxis.setFixedAutoRange((79200000*dateTerm)+(79200000*2));  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 )
				if(dateTerm>25)	xAxis.setFixedAutoRange((79200000*dateTerm)+(79200000*2.5));  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 )
			   	if(dateTerm>30)	xAxis.setFixedAutoRange((79200000*dateTerm)+(79200000*3));  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 )
			   	if(dateTerm>35)	xAxis.setFixedAutoRange((79200000*dateTerm)+(79200000*3.5));  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 )
			   	if(dateTerm>40)	xAxis.setFixedAutoRange((79200000*dateTerm)+(79200000*4));  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 )
			   	if(dateTerm>45)	xAxis.setFixedAutoRange((79200000*dateTerm)+(79200000*4.5));  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 )
			   	if(dateTerm>50)	xAxis.setFixedAutoRange((79200000*dateTerm)+(79200000*5));  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 )
			   	if(dateTerm>55)	xAxis.setFixedAutoRange((79200000*dateTerm)+(79200000*5.5));  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 )
			   	if(dateTerm>60)	xAxis.setFixedAutoRange((79200000*dateTerm)+(79200000*6));  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 )
			   	if(dateTerm>65)	xAxis.setFixedAutoRange((79200000*dateTerm)+(79200000*6.5));  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 )
			   	if(dateTerm>70)	xAxis.setFixedAutoRange((79200000*dateTerm)+(79200000*7));  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 )
			  
				//Y축 소수점 없이 ..
			   	yAxis = (NumberAxis) plot.getRangeAxis();
			   	yAxis.setTickLabelFont(new Font("맑은 고딕", 0, 11));
			    yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			    
			    //Y축 안쪽 눈금크기
			    //yAxis.setTickMarkInsideLength(3);
			    yAxis.setTickMarkOutsideLength(3);
			    //yAxis.setTickMarksVisible(false);
				
				
			    GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, new Color(55,96,146),     0.0f, 0.0f, new Color(55,96,146));
				GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, new Color(85,142,213), 0.0f, 0.0f, new Color(85,142,213));
				GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, new Color(204,0,0),     0.0f, 0.0f, new Color(204,0,0));
				GradientPaint gp3 = new GradientPaint(0.0f, 0.0f, new Color(240,132,124), 0.0f, 0.0f, new Color(240,132,124));
				
				renderer = (StackedXYBarRenderer)plot.getRenderer();
				//Standard 
				renderer.setBarPainter(new StandardXYBarPainter());
			    //stack outline
				renderer.setDrawBarOutline(false);
				//shadow
			    renderer.setShadowVisible(false);
			      
				//renderer.setAutoPopulateSeriesStroke(false);
				
				
				//bar 그림자
				//renderer.setShadowVisible(false);
				
				renderer.setSeriesPaint(0, gp0);
				renderer.setSeriesPaint(1, gp1);
				renderer.setSeriesPaint(2, gp2);
				renderer.setSeriesPaint(3, gp3);
				
				
				
				
				//info2 = new ChartRenderingInfo(new StandardEntityCollection());
				
				if(!new File(filePath).isDirectory() ) new File(filePath).mkdirs(); 
				ChartUtilities.saveChartAsPNG(new File(filePath+chartName),chart,width,height,info2);
				result = chartURL+chartName;
				
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			finally{
				chart = null; dataset = null; plot = null; lt = null; xAxis = null; info2 = null; renderer = null;
			}
			
			return result;
		}
		
		// 트위터용 라인차트를 만들어 chart url 반환
		public String getCompareLineChartTwitter(String sDate, 
												String eDate, 
												ArrayList psArrayData, 
												String chartName, 
												int width, 
												int height
												,String gTitle
												,int baseCnt
												,int yminCnt
												,int ymaxCnt
												,int tick
												,int tick_cnt
												,String seriesName
												){
			String result = "";
			DateUtil du = new DateUtil();
			JFreeChart chart = null;
			TimeSeriesCollection dataset = null;
			XYPlot plot = null;
			NumberAxis yAxis = null;
			DateAxis xAxis = null;
			XYLineAndShapeRenderer renderer = null;
			XYItemRenderer renderer1 = null;
			ChartRenderingInfo info2 = null;
			try{
				//데이터 셋 생성
				dataset = new TimeSeriesCollection();
				
				//LegacyTheme 적용
				//ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
				//BarRenderer.setDefaultBarPainter(new StandardBarPainter());
				
				dataset = setChartDataDateCount(sDate.replaceAll("-", ""), eDate.replaceAll("-", ""), psArrayData,"d", seriesName, baseCnt);
				chart = ChartFactory.createTimeSeriesChart("","", "", dataset, true, true, false);
				
				plot = chart.getXYPlot();
				plot.setDomainCrosshairVisible(true);
				plot.setRangeCrosshairVisible(true);
				xAxis = (DateAxis)plot.getDomainAxis();
				
				//X축 Date 출력방식 설정.
				xAxis.setDateFormatOverride(new SimpleDateFormat("MM/dd"));
				
				//날짜값에 따른 출력 텀
				int term = 1;
				long dateTerm = du.DateDiff(eDate, sDate);
				if(dateTerm>20)		   		term = 2;
				if(dateTerm>30)		   		term = 3;
				if(dateTerm>40)		   		term = 4;
				if(dateTerm>50)		   		term = 5;
				if(dateTerm>60)		   		term = 6;
				if(dateTerm>70)		   		term = 0;
				
				if(term != 0){
					xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,term));
				}
				
				//xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,1));
				
				chart.setTitle(gTitle);
				chart.getTitle().setFont(new Font("맑은 고딕", 0, 13));
				
				//Legend Font
				chart.getLegend().setItemFont(new Font("맑은 고딕", 0, 11));
				chart.getLegend().setBorder(new BlockBorder(1,1,1,1,new Color(200,200,200)));
				chart.setBackgroundPaint(Color.WHITE);
				
				//x축 폰트 , 사이즈
				//xAxis.setTickLabelFont(new Font("gulim", Font.PLAIN, 10));
				xAxis.setTickLabelFont(new Font("맑은 고딕", 0, 11));
				
				//x축 안쪽눈금 크기
				xAxis.setTickMarkInsideLength(3);
				
				//좌우 여백
				xAxis.setLowerMargin(0.05);  //좌
				xAxis.setUpperMargin(0.05);  //우
				
				// x축 범위 
				//xAxis.setAutoRange(true); 
				//xAxis.setFixedAutoRange(554400000.0);  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 ) 
				
				//그래프 간격
				//xAxis.setLowerMargin(0.50);
				
				//차트 Bg color
				plot.setBackgroundPaint(Color.WHITE); 
				//plot.setRangeGridlinePaint(Color.blue);
				//plot.setDomainGridlinePaint(Color.blue);
				
				//Y축 소수점 없이 ..
				yAxis = (NumberAxis) plot.getRangeAxis();
				//yAxis.setNumberFormatOverride(NumberFormat.getCurrencyInstance());
				yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
				
				//y축 라벨 폰트,사이즈
				//yAxis.setLabelFont(new Font("맑은 고딕", 0, 9));
				yAxis.setTickLabelFont(new Font("맑은 고딕", 0, 10));
				//Y축 안쪽 눈금크기
				yAxis.setTickMarkInsideLength(3);
				
				//x축 라벨
				//xAxis.setLabel("일자");
				//y축 라벨
				//yAxis.setLabel("트위터 누적수량");
				//x축 라벨 폰트,사이즈
				//xAxis.setLabelFont(new Font("맑은 고딕", 0, 11));
				//y축 라벨 폰트,사이즈
				//yAxis.setLabelFont(new Font("맑은 고딕", 0, 11));
				
				//xAxis.setLabelInsets(new RectangleInsets(0.0, 0.0, 0.0, 360.0));
				//yAxis.setLabelAngle(9.0);
				
				
				//Y축 범위 설정
				if(ymaxCnt>0){
					yAxis.setRange(yminCnt, ymaxCnt);
				}
				
				renderer = new XYLineAndShapeRenderer();
				if(tick>0){
					// 그래프  tick
					renderer.setSeriesShapesVisible(0, true);
					renderer.setSeriesShapesVisible(1, true);
					renderer.setSeriesShapesVisible(2, true);
					renderer.setSeriesShapesVisible(3, true);
					renderer.setSeriesShapesVisible(4, true);
					renderer.setSeriesShapesVisible(5, true);
					renderer.setSeriesShapesVisible(6, true);
					renderer.setUseFillPaint(true);
					renderer.setDrawOutlines(false);
					renderer.setFillPaint(Color.red);
					
				}else{
					renderer.setSeriesShapesVisible(0, false);
					renderer.setSeriesShapesVisible(1, false);
					renderer.setSeriesShapesVisible(2, false);
					renderer.setSeriesShapesVisible(3, false);
					renderer.setSeriesShapesVisible(4, false);
					renderer.setSeriesShapesVisible(5, false);
					renderer.setSeriesShapesVisible(6, false);
				}
				// 포인트 라벨 
				if(tick_cnt>0){
					//StandardXYItemLabelGenerator xyItem = new StandardXYItemLabelGenerator("{0}{1}{2}",xAxis.getDateFormatOverride(), NumberFormat.getCurrencyInstance());
					StandardXYItemLabelGenerator xyItem = new StandardXYItemLabelGenerator();
					System.out.println("xyItem.getFormatString(): "+xyItem.getFormatString());
					
					renderer.setSeriesItemLabelsVisible(0, true);
					renderer.setSeriesItemLabelsVisible(1, true);
					renderer.setSeriesItemLabelsVisible(2, true);
					renderer.setSeriesItemLabelsVisible(3, true);
					renderer.setSeriesItemLabelsVisible(4, true);
					renderer.setSeriesItemLabelsVisible(5, true);
					renderer.setSeriesItemLabelGenerator(0, xyItem);
					renderer.setSeriesItemLabelGenerator(1, xyItem);
					renderer.setSeriesItemLabelGenerator(2, xyItem);
					renderer.setSeriesItemLabelGenerator(3, xyItem);
					renderer.setSeriesItemLabelGenerator(4, xyItem);
					renderer.setSeriesItemLabelGenerator(6, xyItem);
					//renderer.setLegendItemLabelGenerator(new  StandardXYSeriesLabelGenerator("{0} {1}"));
				}

				plot.setRenderer(renderer);
				
				/*LineAndShapeRenderer ShapeRenderer = (LineAndShapeRenderer)plot.getRenderer();
				ShapeRenderer.setShapesVisible(true);
				ShapeRenderer.setDrawOutlines(false);
				ShapeRenderer.setUseFillPaint(true);
				ShapeRenderer.setFillPaint(Color.red);*/
				
				//라벨 퍼센트 표시
				//{0}: 범례, {1}: 카운트, {2} 비율%
				
				
				renderer1 = plot.getRenderer();
				
				//255,187,0
				GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, new Color(255,187,0), 0.0f, 0.0f, new Color(255,187,0));
				
				renderer1.setSeriesPaint(0, gp1);
				renderer1.setSeriesPaint(1, gp0);
				renderer1.setSeriesPaint(2, gp3);
				
				
				// 그래프 라인 두께
				renderer1.setSeriesStroke(0,new BasicStroke(3));
				renderer1.setSeriesStroke(1,new BasicStroke(3));
				renderer1.setSeriesStroke(2,new BasicStroke(3));
				
				
				info2 = new ChartRenderingInfo(new StandardEntityCollection());
				if(!new File(filePath).isDirectory() ) new File(filePath).mkdirs(); 
				ChartUtilities.saveChartAsPNG(new File(filePath+chartName),chart,width,height,info2);
				result = chartURL+chartName;
				
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			finally{
				chart = null; dataset = null; plot = null; info2 = null; renderer1 = null; yAxis = null; renderer = null; xAxis = null;
			}
			
			return result;
		}
		
		/**
		 * 일별 데이터 변화를 표시하는 그래프의 DATASET을 반환한다.
		 * @param sDate 시작일 "YYYY-MM-DD"
		 * @param eDate 종료일 "YYYY-MM-DD"
		 * @param paList 데이터 세트 ARRAYLIST
		 * @return
		 */
		public TimeSeriesCollection setChartDataDateCount( String sDate, String eDate, ArrayList paList, String unit, String seriesName, int baseCnt  ) {
			TimeSeries series = null;
			
			DateUtil du = new DateUtil();
			StringUtil su = new StringUtil();
			ArrayList arrData = null;
			GetChartData GCdata = new GetChartData();
			String Input_sDate = "";
			String Input_eDate = "";
			String title = "";
			
			/*if(paList!=null && paList.size()>0){
				ChartBean cBean = (ChartBean)paList.get(0);
				if(!cBean.getData_name().equals("")){
					title = cBean.getData_name();
				}else{
					title = "일별 변화";
				}
			}*/
			
			if(unit.equals("w")){
				Input_sDate = sDate.replaceAll("-","");
				Input_eDate = eDate.replaceAll("-","");
				arrData = GCdata.getXvalDate(Input_sDate, Input_eDate, unit, "yyyyww");
			}else if(unit.equals("m")){
				Input_sDate = sDate.replaceAll("-","");
				Input_eDate = eDate.replaceAll("-","");
				arrData = GCdata.getXvalDate(Input_sDate, Input_eDate, unit, "yyyyMM");
			}else if(unit.equals("d")){
				Input_sDate = sDate.replaceAll("-","");
				Input_eDate = eDate.replaceAll("-","");
				arrData = GCdata.getXvalDate(Input_sDate, Input_eDate, unit, "yyyyMMdd");
			}else{
				arrData = GCdata.getXvalDate(Input_sDate, Input_eDate, unit, "yyyyMMdd");
			}
			TimeSeriesCollection dataset = new TimeSeriesCollection();
			Day day = null;
			Week week = null;
			Month month = null;
			double defaultCnt = (double)baseCnt;
			try {
				
				String[] arrSeries = seriesName.split("@"); 
				
				for (int k = 0; k < arrSeries.length; k++) {
				//System.out.println("arrSeries["+k+"]"+arrSeries[k]);
					series = new TimeSeries(arrSeries[k]);
					
					for(int j=0; j<arrData.size(); j++) {
						String date = (String)arrData.get(j);
						boolean chkDate = false;
						
						if(unit.equals("w")){
							week = new Week(Integer.parseInt(date.substring(4, 6)), Integer.parseInt(date.substring(0, 4)));
						}else if(unit.equals("m")){
							month = new Month(Integer.parseInt(date.substring(4, 6)), Integer.parseInt(date.substring(0, 4)));
						}else if(unit.equals("d")){
							day = new Day(Integer.parseInt(date.substring(6,8)), Integer.parseInt(date.substring(4, 6)), Integer.parseInt(date.substring(0, 4)));
						}
						
						for (int i=0; i<paList.size(); i++) {
						ChartBean cBean = (ChartBean)paList.get(i);
						
						
					/*	if(date.equals(cBean.getData_date())){
							System.out.println("11date:"+date+"/"+cBean.getData_date()+"/"+arrSeries[k]+"/cBean.getData_name()1"+cBean.getData_name());
							if(arrSeries[k].equals(cBean.getData_name().trim())){
								System.out.println("22date:"+date+"/"+cBean.getData_date()+"/"+arrSeries[k]+"/cBean.getData_name()1"+cBean.getData_name());
								
							}
						}*/
						if(date.equals(cBean.getData_date()) && arrSeries[k].equals(cBean.getData_name())){
							//System.out.println("cBean.getData_name()2"+cBean.getData_name());
						
						
							defaultCnt = Double.parseDouble(cBean.getData_count());
								if(unit.equals("w")) {
									series.add(week,defaultCnt);
								}else if(unit.equals("m")) {
									series.add(month,defaultCnt);
								}else if(unit.equals("d")) {
									//System.out.println("day:"+day+"/cBean.getData_count():"+cBean.getData_count());
									series.add(day,defaultCnt);
								}
								chkDate = true;
							}
						}
						if(!chkDate){
							//누적 그래프
							if(unit.equals("w")){
								series.add(week,(double)defaultCnt);
							}else if(unit.equals("m")){
								series.add(month,(double)defaultCnt);
							}else if(unit.equals("d")){
								series.add(day,(double)defaultCnt);
								
							}
						}
					}
					dataset.addSeries(series);
				}
				
			} catch(Exception ex) {
				System.out.println(" ================ MakeChart setChartDataIssue2 ERROR ==================");
				ex.printStackTrace();
				System.out.println("MakeChart :    "+ex+"    "+ex.getMessage());
			}finally{
				series = null; day = null; week = null; month = null;
			}
			return dataset;
		}

}
















