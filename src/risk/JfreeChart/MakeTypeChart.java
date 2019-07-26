/**일일 보고서
 * @author 임승철 2010.11.22
 * @return chartPath 
 */
package risk.JfreeChart;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.CategoryTableXYDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;

import risk.issue.IssueCodeBean;
import risk.issue.IssueDataBean;
import risk.util.ConfigUtil;
import risk.util.DateUtil;
import risk.util.Log;

public class MakeTypeChart {
	
	//기본
	private GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, new Color(49, 129, 255),     0.0f, 0.0f, new Color(49, 129, 255));
	private GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, new Color(255, 68, 50), 0.0f, 0.0f, new Color(255, 68, 50));
	private GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, new Color(176, 219, 91),     0.0f, 0.0f, new Color(176, 219, 91));
//	private GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, new Color(75,172,237),     0.0f, 0.0f, new Color(75,172,237));
//	private GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, new Color(239,78,70), 0.0f, 0.0f, new Color(239,78,70));
//	private GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, new Color(207,235,97),     0.0f, 0.0f, new Color(207,235,97));
	private GradientPaint gp3 = new GradientPaint(0, 0, new Color(170, 119, 215),     1000, 0, new Color(170, 119, 215));
	private GradientPaint gp4 = new GradientPaint(0, 0, new Color(65,224,242),     1000, 0, new Color(65,224,242));
	private GradientPaint gp5 = new GradientPaint(0, 0, new Color(255,201,56),     1000, 0, new Color(255,201,56));
	
	
	
	//private GradientPaint gp6 = new GradientPaint(0, 0, new Color(14,123,155),     1000, 0, new Color(14,123,155));
	private GradientPaint gp6 = new GradientPaint(0, 0, new Color(201,111,15),     1000, 0, new Color(201,111,15));
	private GradientPaint gp7 = new GradientPaint(0, 0, new Color(146,209,92),     1000, 0, new Color(146,209,92));
	
	
	
	private GradientPaint gp8 = new GradientPaint(0, 0, new Color(111,200,252),     1000, 0, new Color(111,200,252)); 
	private GradientPaint gp9 = new GradientPaint(0, 0, new Color(252, 13, 111),     1000, 0, new Color(252, 13, 111)); 
	
	// 밝은 색상 정보
	GradientPaint[] gp_new = new GradientPaint[]{
//	 new GradientPaint(0.0f, 0.0f, new Color(147, 156, 242), 0.0f, 0.0f, new Color(255, 145, 9))
	 new GradientPaint(0.0f, 0.0f, new Color(147, 156, 242), 0.0f, 0.0f, new Color(147, 156, 242))
	,new GradientPaint(0.0f, 0.0f, new Color(255, 151, 151), 0.0f, 0.0f, new Color(	255, 96,96))
	,new GradientPaint(0.0f, 0.0f, new Color(126, 216, 107), 0.0f, 0.0f, new Color(	80 , 201, 54))
	,new GradientPaint(0.0f, 0.0f, new Color(252, 249, 137), 0.0f, 0.0f, new Color(	250, 245, 80))
	,new GradientPaint(0.0f, 0.0f, new Color(255, 47,  151), 0.0f, 0.0f, new Color(	159, 0, 80))
	,new GradientPaint(0.0f, 0.0f, new Color(222,165,  210), 0.0f, 0.0f, new Color(	204, 117, 187))
	,new GradientPaint(0.0f, 0.0f, new Color(255, 179, 85) , 0.0f, 0.0f, new Color(	255, 145, 9))
	,new GradientPaint(0.0f, 0.0f, new Color(123, 253, 100), 0.0f, 0.0f, new Color(	41 , 251, 4))
	,new GradientPaint(0.0f, 0.0f, new Color(255, 196, 196), 0.0f, 0.0f, new Color(	255, 153, 153))
	,new GradientPaint(0.0f, 0.0f, new Color(168, 81,  255), 0.0f, 0.0f, new Color(	128, 0,255))
	,new GradientPaint(0.0f, 0.0f, new Color(99 , 177, 177), 0.0f, 0.0f, new Color(	64 , 128, 128))
	,new GradientPaint(0.0f, 0.0f, new Color(164, 255, 255), 0.0f, 0.0f, new Color(	0  , 255, 255))
	,new GradientPaint(0.0f, 0.0f, new Color(219, 219, 0)  , 0.0f, 0.0f, new Color(	128, 128, 0))
	,new GradientPaint(0.0f, 0.0f, new Color(255, 157, 255), 0.0f, 0.0f, new Color(	255, 128, 255))
	,new GradientPaint(0.0f, 0.0f, new Color(167, 84 , 84) , 0.0f, 0.0f, new Color(	128, 64, 64))
	};
	
	
	//파이라벨 인터페이스 구현
	private MySectionLabelGenerator pieLabel = new MySectionLabelGenerator();
	
	//긍정,부정,중립 (2015) 삼성화재 
	private GradientPaint gpBlue = new GradientPaint(0.0f, 0.0f, new Color(79, 129, 189),     0.0f, 0.0f, new Color(79, 129, 189));	
	private GradientPaint gpRed = new GradientPaint(0.0f, 0.0f, new Color(192, 80, 77),     0.0f, 0.0f, new Color(192, 80, 77));
	private GradientPaint gpGreen = new GradientPaint(0.0f, 0.0f, new Color(155, 187, 89),     0.0f, 0.0f, new Color(155, 187, 89));
	private GradientPaint gpgray = new GradientPaint(0.0f, 0.0f, new Color(205, 205, 205),     0.0f, 0.0f, new Color(205, 205, 205));
	//마이크로 소프트 ppt 칼라 (2015) 삼성화재 
	private GradientPaint gpPpt1 = new GradientPaint(0.0f, 0.0f, new Color(69, 114, 167),     0.0f, 0.0f, new Color(69, 114, 167));
	private GradientPaint gpPpt2 = new GradientPaint(0.0f, 0.0f, new Color(170, 70, 67),     0.0f, 0.0f, new Color(170, 70, 67));
	private GradientPaint gpPpt3 = new GradientPaint(0.0f, 0.0f, new Color(137, 165, 78),     0.0f, 0.0f, new Color(137, 165, 78));
	private GradientPaint gpPpt4 = new GradientPaint(0.0f, 0.0f, new Color(113, 88, 143),     0.0f, 0.0f, new Color(113, 88, 143));
	private GradientPaint gpPpt5 = new GradientPaint(0.0f, 0.0f, new Color(65, 152, 175),     0.0f, 0.0f, new Color(65, 152, 175));
	private GradientPaint gpPpt6 = new GradientPaint(0.0f, 0.0f, new Color(129, 132, 61),     0.0f, 0.0f, new Color(129, 132, 61));
	private GradientPaint gpPpt7 = new GradientPaint(0.0f, 0.0f, new Color(147, 169, 207),     0.0f, 0.0f, new Color(147, 169, 207));
	
	// categoryType 1: 성향, 출처별  2:기타
	public DefaultPieDataset setPieDataset(int categoryType, ArrayList chartData)
	{
		DefaultPieDataset dataset = new DefaultPieDataset();	
		Iterator it = null;			
		HashMap chartDataHm = new HashMap();		
		
		
		if(categoryType ==1){
		
			if(chartData!=null)
			{
				it = chartData.iterator();
				while(it.hasNext())
				{
					chartDataHm = new HashMap();
					chartDataHm = (HashMap)it.next();
					 
					if((String)chartDataHm.get("CNT")!=null &&!((String)chartDataHm.get("CNT")).equals(""))dataset.setValue((String)chartDataHm.get("CATEGORY"),new Double((String)chartDataHm.get("CNT"))); 
					 
				}							
			}
		}else if(categoryType ==2){
			
		}
		
		return	dataset;	
	}
	
	// 삼성화재 전용 데이터 SET 2015년 - 주요이슈분석(bar)
	public DefaultCategoryDataset setBarDataSetForSamsungFire(ArrayList chartData){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();	
		Iterator it = null;
		HashMap chartDataHm = new HashMap();
		if(chartData!=null){
			it = chartData.iterator();
			while(it.hasNext()){
				chartDataHm = new HashMap();
				chartDataHm = (HashMap)it.next();
				if(chartDataHm.get("CNT")!=null && chartDataHm.get("NAME")!=null && chartDataHm.get("CATEGORY")!=null){
					dataset.addValue(new Double((String)chartDataHm.get("CNT")).doubleValue(),(String)chartDataHm.get("NAME"),(String)chartDataHm.get("CATEGORY"));
				}
			}
		}
		
		return	dataset;
	}
	// 삼성화재 전용 데이터 SET 2015년- 주요이슈분석(line)
	public DefaultCategoryDataset setLineDataSetForSamsungFire(ArrayList chartData){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();	
		Iterator it = null;
		HashMap chartDataHm = new HashMap();
		if(chartData!=null){
			it = chartData.iterator();
			while(it.hasNext()){
				chartDataHm = new HashMap();
				chartDataHm = (HashMap)it.next();
				if(chartDataHm.get("ECNT")!=null && chartDataHm.get("NAME2")!=null && chartDataHm.get("CATEGORY")!=null){
					dataset.addValue(new Double((String)chartDataHm.get("ECNT")).doubleValue(),(String)chartDataHm.get("NAME2"),(String)chartDataHm.get("CATEGORY"));
				}
			}
		}
		
		return	dataset;
	}
	
	// categoryType 1: 출처별 성향
	public DefaultCategoryDataset setStackBarDataSet(int categoryType, ArrayList chartData)
	{
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();	
		Iterator it = null;			
		HashMap chartDataHm = new HashMap();		
		
		
		if(categoryType ==1){
		
			if(chartData!=null)
			{
				it = chartData.iterator();				
				while(it.hasNext())
				{
					 chartDataHm = new HashMap();
					 chartDataHm = (HashMap)it.next();
					 if(chartDataHm.get("PCNT")!=null && chartDataHm.get("NAME1")!=null && chartDataHm.get("CATEGORY")!=null)dataset.addValue(new Double((String)chartDataHm.get("PCNT")).doubleValue(),(String)chartDataHm.get("NAME1"),(String)chartDataHm.get("CATEGORY"));
					 if(chartDataHm.get("NCNT")!=null && chartDataHm.get("NAME1")!=null && chartDataHm.get("CATEGORY")!=null)dataset.addValue(new Double((String)chartDataHm.get("NCNT")).doubleValue(),(String)chartDataHm.get("NAME2"),(String)chartDataHm.get("CATEGORY"));	
					 if(chartDataHm.get("ECNT")!=null && chartDataHm.get("NAME1")!=null && chartDataHm.get("CATEGORY")!=null)dataset.addValue(new Double((String)chartDataHm.get("ECNT")).doubleValue(),(String)chartDataHm.get("NAME3"),(String)chartDataHm.get("CATEGORY"));
				}				
			}
		}else if(categoryType ==2){
			if(chartData!=null)
			{
				it = chartData.iterator();				
				while(it.hasNext())
				{
					 chartDataHm = new HashMap();
					 chartDataHm = (HashMap)it.next();
					 if(chartDataHm.get("CNT")!=null && !chartDataHm.get("CNT").equals("0")) dataset.addValue(new Double((String)chartDataHm.get("CNT")).doubleValue(),(String)chartDataHm.get("NAME"),(String)chartDataHm.get("CATEGORY"));
					
				}				
			}
		}
		
		return	dataset;	
	}
	
	// categoryType 1: 날짜별 키워드,  2: 날짜별 출처
	public CategoryTableXYDataset setDateLineDataset(int categoryType, ArrayList chartData)
	{
		CategoryTableXYDataset dataset = new CategoryTableXYDataset();	
		Iterator it = null;			
		HashMap chartDataHm = new HashMap();
		DateUtil du = new DateUtil();
		org.jfree.data.time.Day day = null;
        org.jfree.data.time.Week week = null;
        org.jfree.data.time.Month month = null;
        org.jfree.data.time.Hour hour = null;
		
		
		if(categoryType ==1){			
		
			if(chartData!=null)
			{
				it = chartData.iterator();				
				while(it.hasNext())
				{
					 chartDataHm = new HashMap();
					 chartDataHm = (HashMap)it.next();
					 
					 day = new Day(du.getTime((String)chartDataHm.get("1")));							 					
					 if(chartDataHm.get("2")!=null && !chartDataHm.get("2").equals("0"))dataset.add(day.getFirstMillisecond(), Integer.parseInt((String)chartDataHm.get("2")), (String)chartDataHm.get("3"));
					 if(chartDataHm.get("4")!=null && !chartDataHm.get("4").equals("0"))dataset.add(day.getFirstMillisecond(), new Double((String)chartDataHm.get("4")).doubleValue(), (String)chartDataHm.get("5"));
					 if(chartDataHm.get("6")!=null && !chartDataHm.get("6").equals("0"))dataset.add(day.getFirstMillisecond(), new Double((String)chartDataHm.get("6")).doubleValue(), (String)chartDataHm.get("7"));
					 if(chartDataHm.get("8")!=null && !chartDataHm.get("8").equals("0"))dataset.add(day.getFirstMillisecond(), new Double((String)chartDataHm.get("8")).doubleValue(), (String)chartDataHm.get("9"));
					 if(chartDataHm.get("10")!=null && !chartDataHm.get("10").equals("0")) dataset.add(day.getFirstMillisecond(), new Double((String)chartDataHm.get("10")).doubleValue(), (String)chartDataHm.get("11"));
					 if(chartDataHm.get("12")!=null && !chartDataHm.get("12").equals("0")) dataset.add(day.getFirstMillisecond(), new Double((String)chartDataHm.get("12")).doubleValue(), (String)chartDataHm.get("13"));
					 if(chartDataHm.get("14")!=null && !chartDataHm.get("14").equals("0")) dataset.add(day.getFirstMillisecond(), new Double((String)chartDataHm.get("14")).doubleValue(), (String)chartDataHm.get("15"));
				}									
			}
			
		}else if(categoryType ==2){
			if(chartData!=null)
			{
				it = chartData.iterator();				
				while(it.hasNext())
				{
					 chartDataHm = new HashMap();
					 chartDataHm = (HashMap)it.next();
					 
					 day = new Day(du.getTime((String)chartDataHm.get("CATEGORY")));							 					
					 if(chartDataHm.get("CNT1")!=null && chartDataHm.get("NAME1")!=null)dataset.add(day.getFirstMillisecond(), Integer.parseInt((String)chartDataHm.get("CNT1")), (String)chartDataHm.get("NAME1"));
					 if(chartDataHm.get("CNT2")!=null && chartDataHm.get("NAME2")!=null)dataset.add(day.getFirstMillisecond(), Integer.parseInt((String)chartDataHm.get("CNT2")), (String)chartDataHm.get("NAME2"));
					 if(chartDataHm.get("CNT3")!=null && chartDataHm.get("NAME3")!=null)dataset.add(day.getFirstMillisecond(), Integer.parseInt((String)chartDataHm.get("CNT3")), (String)chartDataHm.get("NAME3"));
					 if(chartDataHm.get("CNT4")!=null && chartDataHm.get("NAME4")!=null)dataset.add(day.getFirstMillisecond(), Integer.parseInt((String)chartDataHm.get("CNT4")), (String)chartDataHm.get("NAME4"));
					 if(chartDataHm.get("CNT5")!=null && chartDataHm.get("NAME5")!=null)dataset.add(day.getFirstMillisecond(), Integer.parseInt((String)chartDataHm.get("CNT5")), (String)chartDataHm.get("NAME5"));
					 if(chartDataHm.get("CNT6")!=null && chartDataHm.get("NAME6")!=null)dataset.add(day.getFirstMillisecond(), Integer.parseInt((String)chartDataHm.get("CNT6")), (String)chartDataHm.get("NAME6"));
				}									
			}
		}else if(categoryType ==3){
			if(chartData!=null)
			{
				it = chartData.iterator();				
				while(it.hasNext())
				{
					 chartDataHm = new HashMap();
					 chartDataHm = (HashMap)it.next();
					 
					 day = new Day(du.getTime((String)chartDataHm.get("CATEGORY")));							 					
					 if(chartDataHm.get("CNT1")!=null)dataset.add(day.getFirstMillisecond(), Integer.parseInt((String)chartDataHm.get("CNT")), "");
					 
				}									
			}
		}
		
		return	dataset;	
	}
	
	private double tmpData1y = 0;
	private double tmpData2y = 0;
	
	//categoryType 1: 날짜별 우호도
	public CategoryTableXYDataset[] setDateLineBarDataset(int categoryType, ArrayList chartData)
	{
		CategoryTableXYDataset[] dataset = new CategoryTableXYDataset[2];
		CategoryTableXYDataset dataset1 = new CategoryTableXYDataset();
		CategoryTableXYDataset dataset2 = new CategoryTableXYDataset();
		
		Iterator it = null;			
		HashMap chartDataHm = new HashMap();
		DateUtil du = new DateUtil();
		org.jfree.data.time.Day day = null;
        org.jfree.data.time.Week week = null;
        org.jfree.data.time.Month month = null;
		
		
		if(categoryType ==1){				
			if(chartData!=null)
			{
				System.out.println("chartData:"+chartData.size());
				it = chartData.iterator();				
				while(it.hasNext())
				{
					 chartDataHm = new HashMap();
					 chartDataHm = (HashMap)it.next();
					 day = new Day(du.getTime((String)chartDataHm.get("CATEGORY")));
					
					 if(chartDataHm.get("TCNT")!=null && chartDataHm.get("NAME1")!=null)dataset1.add(day.getFirstMillisecond(), new Double((String)chartDataHm.get("TCNT")).doubleValue(), (String)chartDataHm.get("NAME1"));
					 if(chartDataHm.get("PCNT")!=null && chartDataHm.get("NAME2")!=null)dataset2.add(day.getFirstMillisecond(), new Double((String)chartDataHm.get("PCNT")).doubleValue(), (String)chartDataHm.get("NAME2"));
					 if(chartDataHm.get("NCNT")!=null && chartDataHm.get("NAME3")!=null)dataset2.add(day.getFirstMillisecond(), new Double((String)chartDataHm.get("NCNT")).doubleValue()*-1, (String)chartDataHm.get("NAME3"));
					
					 if(chartDataHm.get("TCNT")!=null)if(tmpData1y < new Double((String)chartDataHm.get("TCNT")).doubleValue())tmpData1y  = new Double((String)chartDataHm.get("TCNT")).doubleValue();
					 if(chartDataHm.get("PCNT")!=null)if(tmpData2y < new Double((String)chartDataHm.get("PCNT")).doubleValue())tmpData2y  = new Double((String)chartDataHm.get("PCNT")).doubleValue();
					 if(chartDataHm.get("NCNT")!=null)if(tmpData2y < new Double((String)chartDataHm.get("NCNT")).doubleValue())tmpData2y  = new Double((String)chartDataHm.get("NCNT")).doubleValue();
				}	
				dataset[0] = dataset1; 
				dataset[1] = dataset2;  
			}
		}else if(categoryType ==2){
			
		}else if(categoryType ==3){
			
		}
		
		return	dataset;	
	}
	
	
	//(Pie Chart)
	public String makePieChart(int categoryType, ArrayList chartData, String chartName, String filePath, String urlPath, int width, int height)
	{
		String pullPath = "";
		String fullChartName = "";
		String lastSiteName = "";
		DateUtil du = new DateUtil();
		HashMap chartDataHm = new HashMap();		
		double total = 0;
		int tempCnt = 0;						
		DefaultPieDataset dataset = new DefaultPieDataset();					
		Iterator it = null;			
		
		JFreeChart chart = null;
		
		try{
			fullChartName = chartName+du.getCurrentDate("yyyyMMddHHmmss")+".png";		
			
			
			//카테고리 타입별루 데이터셋 셋팅
			dataset = setPieDataset(categoryType,chartData);			
			
			//차트 객체 생성및 속성지정
			chart = ChartFactory.createPieChart("",dataset,true,true,false);										
			chart.getTitle().setFont(new java.awt.Font("돋움",0,11)); ////제목			 
			chart.getLegend().setItemFont(new java.awt.Font("맑은 고딕",0,11)); ////하단 라벨 설명
			chart.getLegend().setBorder(new BlockBorder(1,1,1,1,new Color(220,220,220))); ////차트 백그라운 색깔			
			chart.setBackgroundPaint(Color.WHITE);
			chart.setBorderPaint(new Color(225,225,225));
		    chart.setBorderStroke(new BasicStroke(2));
		 
		    
			
		    //PiePlot객체 생성 및 속성 지정
			PiePlot plot = (PiePlot)chart.getPlot();			
			plot.setLabelOutlinePaint(Color.WHITE);
			plot.setShadowXOffset(0.0);
			plot.setShadowYOffset(0.0);
			plot.setBackgroundAlpha(0);
			//plot.setSimpleLabels(true);
			plot.setLabelLinkStyle(PieLabelLinkStyle.QUAD_CURVE);
			plot.setLabelBackgroundPaint(Color.WHITE);
			plot.setOutlinePaint(Color.WHITE);
			plot.setLabelOutlinePaint(new Color(225,225,225));
			plot.setIgnoreZeroValues(true);					        
			plot.setSectionOutlinesVisible(true);	
			plot.setCircular(false);
			plot.setAutoPopulateSectionOutlinePaint(true);
			plot.setAutoPopulateSectionOutlineStroke(true);
			plot.setAutoPopulateSectionPaint(true);
		
			//plot.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);
			//plot.setExplodePercent(0, 0.3);
			//plot.setExplodePercent(1, 0.3);
			//plot.setExplodePercent(2, 0.3);
			//plot.setExplodePercent(3, 0.3);
			//plot.setExplodePercent(4, 0.3);
			//plot.setExplodePercent(5, 0.3);
			//plot.setExplodePercent(6, 0.3);
			//plot.setExplodePercent(7, 0.3);
			//plot.setExplodePercent(8, 0.3);
			//plot.setExplodePercent(9, 0.3);
			
			plot.setLabelFont(new java.awt.Font("LucidaBrightDemiBold",0,10));											
			plot.setSectionPaint(0,gp1);
			plot.setSectionPaint(1,gp0);
			plot.setSectionPaint(2,gp2);
			plot.setSectionPaint(3,gp3);
			plot.setSectionPaint(4,gp4);
			plot.setSectionPaint(5,gp5);
			plot.setSectionPaint(6,gp6);
			plot.setSectionPaint(7,gp7);
			plot.setSectionPaint(8,gp8);
			plot.setSectionPaint(9,gp9);	
			
			
			//파이라벨 인터페이스 구현 클래스 사용
			plot.setLabelGenerator(pieLabel);
			
			////차트생성
			//ChartRenderingInfo infoB = new ChartRenderingInfo(new StandardEntityCollection());
			if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+fullChartName),chart,width,height);
			pullPath = urlPath+fullChartName;			
			
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			
		}
		
		return pullPath;
	}

	
	public String make3DPieChart(int categoryType, ArrayList chartData, String chartName, String filePath, String urlPath, int width, int height){
		return make3DPieChart(categoryType, chartData, chartName, filePath, urlPath, width, height,"");
	}
	
	//(Pie Chart)
	public String make3DPieChart(int categoryType, ArrayList chartData, String chartName, String filePath, String urlPath, int width, int height, String right)
	{
		String pullPath = "";
		String fullChartName = "";
		String lastSiteName = "";
		DateUtil du = new DateUtil();
		HashMap chartDataHm = new HashMap();		
		double total = 0;
		int tempCnt = 0;						
		DefaultPieDataset dataset = new DefaultPieDataset();					
		Iterator it = null;			
		
		JFreeChart chart = null;
		
		try{
			fullChartName = chartName+du.getCurrentDate("yyyyMMddHHmmss")+".png";		
			
			//카테고리 타입별루 데이터셋 셋팅
			dataset = setPieDataset(categoryType,chartData);			
			
			
			//차트 객체 생성및 속성지정
			chart = ChartFactory.createPieChart3D("",dataset,true,true,false);										
			chart.getTitle().setFont(new java.awt.Font("돋움",0,11)); ////제목			 
			chart.getLegend().setItemFont(new java.awt.Font("맑은 고딕",0,11)); ////하단 라벨 설명
			chart.getLegend().setBorder(new BlockBorder(1,1,1,1,new Color(220,220,220))); ////차트 백그라운 색깔
			if(right.equals("Y")){
				chart.getLegend().setPosition(RectangleEdge.RIGHT);
			}
			chart.setBackgroundPaint(Color.WHITE);
			chart.setBorderPaint(new Color(225,225,225));
		    chart.setBorderStroke(new BasicStroke(2));		 
		    
			
		    //PiePlot객체 생성 및 속성 지정
		    PiePlot plot = (PiePlot)chart.getPlot();				
			plot.setLabelOutlinePaint(Color.WHITE);
			plot.setShadowXOffset(0.0);
			plot.setShadowYOffset(0.0);
			plot.setBackgroundAlpha(0);
			plot.setSectionOutlinesVisible(false);
			//plot.setSimpleLabels(true);
			plot.setLabelLinkStyle(PieLabelLinkStyle.QUAD_CURVE);
			plot.setLabelBackgroundPaint(Color.WHITE);
			plot.setOutlinePaint(Color.WHITE);
			plot.setLabelOutlinePaint(new Color(225,225,225));
			plot.setIgnoreZeroValues(true);					        
			plot.setSectionOutlinesVisible(true);	
			plot.setCircular(false);
			plot.setStartAngle(0.4);
			plot.setAutoPopulateSectionOutlinePaint(true);
			plot.setAutoPopulateSectionOutlineStroke(true);
			plot.setAutoPopulateSectionPaint(true);
			plot.setForegroundAlpha(0.5f);
			//plot.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);
			//plot.setExplodePercent(0, 0.3);
			//plot.setExplodePercent(1, 0.3);
			//plot.setExplodePercent(2, 0.3);
			//plot.setExplodePercent(3, 0.3);
			//plot.setExplodePercent(4, 0.3);
			//plot.setExplodePercent(5, 0.3);
			//plot.setExplodePercent(6, 0.3);
			//plot.setExplodePercent(7, 0.3);
			//plot.setExplodePercent(8, 0.3);
			//plot.setExplodePercent(9, 0.3);
			
			plot.setLabelFont(new java.awt.Font("LucidaBrightDemiBold",0,10));		
			/*
			plot.setSectionPaint(0,gp_new[0]);
			plot.setSectionPaint(1,gp_new[1]);
			plot.setSectionPaint(2,gp_new[2]);
			plot.setSectionPaint(3,gp_new[3]);
			plot.setSectionPaint(4,gp_new[4]);
			plot.setSectionPaint(5,gp_new[5]);
			plot.setSectionPaint(6,gp_new[6]);
			plot.setSectionPaint(7,gp_new[7]);
			plot.setSectionPaint(8,gp_new[8]);
			plot.setSectionPaint(9,gp_new[9]);	
			*/
			
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
			
			//파이라벨 인터페이스 구현 클래스 사용
			plot.setLabelGenerator(pieLabel);
			
			////차트생성
			//ChartRenderingInfo infoB = new ChartRenderingInfo(new StandardEntityCollection());
			if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+fullChartName),chart,width,height);
			pullPath = urlPath+fullChartName;			
			
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			
		}
		
		return pullPath;
	}
	
	//(Pie Chart)
	public String makeRingChart(int categoryType, ArrayList chartData, String chartName, String filePath, String urlPath, int width, int height)
	{
		String pullPath = "";
		String fullChartName = "";
		String lastSiteName = "";
		DateUtil du = new DateUtil();
		HashMap chartDataHm = new HashMap();		
		double total = 0;
		int tempCnt = 0;						
		DefaultPieDataset dataset = new DefaultPieDataset();					
		Iterator it = null;			
		
		JFreeChart chart = null;
		
		try{
			fullChartName = chartName+du.getCurrentDate("yyyyMMddHHmmss")+".png";		
			
			//카테고리 타입별루 데이터셋 셋팅
			dataset = setPieDataset(categoryType,chartData);	
			
			
			//차트 객체 생성및 속성지정
			chart = ChartFactory.createRingChart("",dataset,true,true,false);										
			chart.getTitle().setFont(new java.awt.Font("돋움",0,11)); ////제목			 
			chart.getLegend().setItemFont(new java.awt.Font("맑은 고딕",0,11)); ////하단 라벨 설명
			chart.getLegend().setBorder(new BlockBorder(1,1,1,1,new Color(220,220,220))); ////차트 백그라운 색깔			
			chart.setBackgroundPaint(Color.WHITE);
			chart.setBorderPaint(new Color(225,225,225));
		    chart.setBorderStroke(new BasicStroke(2));		 
		    
			
		    //PiePlot객체 생성 및 속성 지정
			RingPlot plot = (RingPlot)chart.getPlot();				
			plot.setSectionDepth(0.5);
			plot.setLabelOutlinePaint(Color.WHITE);
			plot.setShadowXOffset(0.0);
			plot.setShadowYOffset(0.0);
			plot.setBackgroundAlpha(0);
			plot.setSectionOutlinesVisible(false);
			//plot.setSimpleLabels(true);
			plot.setLabelLinkStyle(PieLabelLinkStyle.QUAD_CURVE);
			plot.setLabelBackgroundPaint(Color.WHITE);
			plot.setOutlinePaint(Color.WHITE);
			plot.setLabelOutlinePaint(new Color(225,225,225));
			plot.setIgnoreZeroValues(true);					        
			plot.setSectionOutlinesVisible(true);	
			plot.setCircular(false);
			plot.setAutoPopulateSectionOutlinePaint(true);
			plot.setAutoPopulateSectionOutlineStroke(true);
			plot.setAutoPopulateSectionPaint(true);
			//plot.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);
			//plot.setExplodePercent(0, 0.3);
			//plot.setExplodePercent(1, 0.3);
			//plot.setExplodePercent(2, 0.3);
			//plot.setExplodePercent(3, 0.3);
			//plot.setExplodePercent(4, 0.3);
			//plot.setExplodePercent(5, 0.3);
			//plot.setExplodePercent(6, 0.3);
			//plot.setExplodePercent(7, 0.3);
			//plot.setExplodePercent(8, 0.3);
			//plot.setExplodePercent(9, 0.3);
			
			plot.setLabelFont(new java.awt.Font("LucidaBrightDemiBold",0,10));											
			plot.setSectionPaint(0,gp1);
			plot.setSectionPaint(1,gp0);
			plot.setSectionPaint(2,gp2);
			plot.setSectionPaint(3,gp3);
			plot.setSectionPaint(4,gp4);
			plot.setSectionPaint(5,gp5);
			plot.setSectionPaint(6,gp6);
			plot.setSectionPaint(7,gp7);
			plot.setSectionPaint(8,gp8);
			plot.setSectionPaint(9,gp9);	
			
			
			//파이라벨 인터페이스 구현 클래스 사용
			plot.setLabelGenerator(pieLabel);
			
			////차트생성
			//ChartRenderingInfo infoB = new ChartRenderingInfo(new StandardEntityCollection());
			if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+fullChartName),chart,width,height);
			pullPath = urlPath+fullChartName;			
			
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			
		}
		
		return pullPath;
	}
	
	//(Line)
	public String makeLine(int categoryType, ArrayList chartData, String chartName, String filePath, String urlPath, int width, int height)
	{
		String pullPath = "";
		String fullChartName = "";
		String lastSiteName = "";
		HashMap chartDataHm = new HashMap();
		DateUtil du = new DateUtil();
		Iterator it = null;				
		String date;
		int term;
		long peorid;
						
		DateAxis xAxis = null; 
		NumberAxis yAxis = null;
		XYPlot plot = null;
		XYLineAndShapeRenderer renderer = null;
		JFreeChart chart = null;
		
		org.jfree.data.time.Day day = null;
        org.jfree.data.time.Week week = null;
        org.jfree.data.time.Month month = null;
		CategoryTableXYDataset dataset = new CategoryTableXYDataset();
		
		LegendTitle legenTitle = null;						
		
		try{			
			fullChartName = chartName+du.getCurrentDate("yyyyMMddHHmmss")+".png";				
			
			
			dataset = setDateLineDataset(categoryType,chartData);
			
			//라인 객체 생성및 속성지정
			renderer = new XYLineAndShapeRenderer();			
			renderer.setSeriesStroke(0,new BasicStroke(2)); //선라인
			renderer.setSeriesStroke(1,new BasicStroke(2));
			renderer.setSeriesStroke(2,new BasicStroke(2));
			renderer.setSeriesStroke(3,new BasicStroke(2));
			renderer.setSeriesStroke(4,new BasicStroke(2));
			renderer.setSeriesStroke(5,new BasicStroke(2));	
			renderer.setSeriesStroke(6,new BasicStroke(2));					
			renderer.setSeriesPaint(0,gp0,false); //선 색깔
			renderer.setSeriesPaint(1,gp1,false);
			renderer.setSeriesPaint(2,gp2,false);
			renderer.setSeriesPaint(3,gp3,false);
			renderer.setSeriesPaint(4,gp4,false);
			renderer.setSeriesPaint(5,gp5,false);
			renderer.setSeriesPaint(6,gp6,false);			
			renderer.setSeriesShapesVisible(0, false); //꼭지점 생성여부
			renderer.setSeriesShapesVisible(1, false);
			renderer.setSeriesShapesVisible(2, false);
			renderer.setSeriesShapesVisible(3, false);
			renderer.setSeriesShapesVisible(4, false);
			renderer.setSeriesShapesVisible(5, false);
			renderer.setSeriesShapesVisible(6, false);			
			
			
			
			//X축객체 생성 및 속성지정
			DateAxis xAsis = new DateAxis();		
			xAsis.setTickMarksVisible(true);
			xAsis.setTickLabelFont(new Font("맑은 고딕", 0, 11)) ;//X축 폰트 , 사이즈
			xAsis.setDateFormatOverride(new SimpleDateFormat("MM/dd"));	//X축 Date 출력방식 설정
			xAsis.setTickMarkOutsideLength(3); //X축 안쪽눈금 크기
//			xAsis.setLowerMargin(0.45);  //X축 좌 여백
//			xAsis.setUpperMargin(0.35);  //X축 우 여백						
			term = 1; //X축기간에 따른 출력 텀
			peorid = chartData.size();		   	
			if(peorid>15) 		term = 2;
		   	if(peorid>30)		term = 3;
		   	if(peorid>40)		term = 4;
		   	if(peorid>50)		term = 5;
		   	if(peorid>60)		term = 6;
		   	if(peorid>70)		term = 0;		   	
		   	if(term != 0){
		   		xAsis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,term));
		   	}
		   	if(peorid>0){
		    	xAsis.setFixedAutoRange((79200000*peorid)+(79200000*((long)term-1)));  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 )
			}		    		    
			
		    
		    //Y축 객체  생성 및 속성지정
			NumberAxis yAsis = new NumberAxis();			   	
			yAsis.setTickLabelFont(new Font("맑은 고딕", 0, 11)); //Y축 폰트 , 사이즈
			yAsis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); //Y축 소수점 없이 ..
		    //yAxis.setTickMarkInsideLength(3);
			yAsis.setTickMarkOutsideLength(3);
		    //yAxis.setTickMarksVisible(false);
			
			
			
			//XYPlot객체 생성 및 속성 지정
			plot = new XYPlot(dataset, xAsis, yAsis, renderer);
			plot.setOutlineVisible(false);
			//plot.setBackgroundAlpha(0);
			//plot.setBackgroundPaint(Color.WHITE); 
			plot.setRangeGridlinesVisible(false);
			plot.setDomainGridlinesVisible(false);
			//plot.setRangeGridlinePaint(Color.blue);
			//plot.setDomainGridlinePaint(Color.blue);
			plot.setNoDataMessage("No Data");			
			plot.setRangeZeroBaselineVisible(true);
						
			
			
			//차트 객체 생성 및 속성지정
		    chart = new JFreeChart(plot);
		    chart.setBackgroundPaint(Color.WHITE); //차트 백그라운 색깔
			chart.setBorderVisible(false); //차트 보더 보임 여부
			//chart.setBorderPaint(new Color(225,225,225));
			//chart.setBorderStroke(new BasicStroke(3));
						
			
			
			//차트 범례 속성 지정
		    legenTitle = chart.getLegend(); //범례 위치 조정
		    legenTitle.setItemFont(new java.awt.Font("맑은 고딕",0,11));
		    legenTitle.setBorder(new BlockBorder(1,1,1,1,new Color(200,200,200)));
			//legenTitle.setPosition(RectangleEdge.RIGHT);					//범례의 플롯 대비 위치
			//legenTitle.setVerticalAlignment(VerticalAlignment.CENTER);		//범례의 세로 위치		
		    
			
			//System.out.println("filePath:"+filePath);
			//차트 만들기
			if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+fullChartName),chart,width,height);
			pullPath = urlPath+fullChartName;
			
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			
		}
		return pullPath;
	}
	
	//(Bar)
	public String makeLineByTime(int categoryType, ArrayList chartData, String chartName, String filePath, String urlPath, int width, int height)
	{
		String pullPath = "";
		String fullChartName = "";
		String lastSiteName = "";
		HashMap chartDataHm = new HashMap();
		DateUtil du = new DateUtil();
		Iterator it = null;				
		String date;
		int term;
		long peorid;
		
		
		DateAxis xAxis = null; 
		NumberAxis yAxis = null;
		CategoryPlot plot = null;
		Plot plot2 = null;
		LineAndShapeRenderer renderer = null;
		JFreeChart chart = null;
		
		org.jfree.data.time.Day day = null;
        org.jfree.data.time.Week week = null;
        org.jfree.data.time.Month month = null;
        CategoryDataset dataset1 = new DefaultCategoryDataset();
        
        
        
		LegendTitle legenTitle = null;
		LegendTitle legenTitle2 = null;
		
		double tmpData1y = 0;
		double tmpData2y = 0;
		
				
		try{			
			fullChartName = chartName+du.getCurrentDate("yyyyMMddHHmmss")+".png";		
			
			dataset1 = setStackBarDataSet(categoryType,chartData);
			
			//X축객체 생성 및 속성지정
			CategoryAxis xAsis = new CategoryAxis();		
			xAsis.setTickMarksVisible(true);
			xAsis.setTickLabelFont(new Font("맑은 고딕", 0, 11)) ;//X축 폰트 , 사이즈
			xAsis.setTickMarkOutsideLength(3); //X축 안쪽눈금 크기			
//			xAsis.setLowerMargin(0.45);  //X축 좌 여백
//			xAsis.setUpperMargin(0.35);  //X축 우 여백
			
		    //Y축 객체  생성 및 속성지정
			NumberAxis yAsis = new NumberAxis();			   	
			yAsis.setTickLabelFont(new Font("맑은 고딕", 0, 11)); //Y축 폰트 , 사이즈
			yAsis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); //Y축 소수점 없이 ..
			yAsis.setTickMarkInsideLength(3);
			yAsis.setTickMarkOutsideLength(3);
			yAsis.setTickMarksVisible(false);
	
			//라인 객체 및 속성 지정
			renderer = new LineAndShapeRenderer();
			renderer.setSeriesStroke(0,new BasicStroke(2)); //선라인
			renderer.setSeriesStroke(1,new BasicStroke(2));
			renderer.setSeriesStroke(2,new BasicStroke(2));
			renderer.setSeriesStroke(3,new BasicStroke(2));
			renderer.setSeriesStroke(4,new BasicStroke(2));
			renderer.setSeriesStroke(5,new BasicStroke(2));	
			renderer.setSeriesStroke(6,new BasicStroke(2));					
			renderer.setSeriesPaint(0,gp0,false); //선 색깔
			renderer.setSeriesPaint(1,gp1,false);
			renderer.setSeriesPaint(2,gp2,false);
			renderer.setSeriesPaint(3,gp3,false);
			renderer.setSeriesPaint(4,gp4,false);
			renderer.setSeriesPaint(5,gp5,false);
			renderer.setSeriesPaint(6,gp6,false);			
			renderer.setSeriesShapesVisible(0, false); //꼭지점 생성여부
			renderer.setSeriesShapesVisible(1, false);
			renderer.setSeriesShapesVisible(2, false);
			renderer.setSeriesShapesVisible(3, false);
			renderer.setSeriesShapesVisible(4, false);
			renderer.setSeriesShapesVisible(5, false);
			renderer.setSeriesShapesVisible(6, false);	
			
			
			//XYPlot객체 생성 및 속성 지정
			plot = new CategoryPlot();		
			plot.setDataset(0, dataset1);		
			plot.setRenderer(0, renderer);			
			plot.setDomainAxis(0, xAsis);			
			plot.setRangeAxis(0, yAsis);		
			plot.mapDatasetToRangeAxis(0, 0);				
			
						
			plot.setOutlineVisible(true);
			//plot.setBackgroundAlpha(0);
			//plot.setBackgroundPaint(Color.WHITE); 
			plot.setRangeGridlinesVisible(false);
			plot.setDomainGridlinesVisible(false);
			//plot.setRangeGridlinePaint(Color.blue);
			//plot.setDomainGridlinePaint(Color.blue);
			plot.setNoDataMessage("No Data");			
			plot.setRangeZeroBaselineVisible(true);
			plot.setDomainGridlinesVisible(true);
			plot.setRangeGridlinesVisible(true);
			plot.setOrientation(PlotOrientation.VERTICAL);
			
						
			
			
			//차트 객체 생성 및 속성지정
		    chart = new JFreeChart(plot);
		    chart.setBackgroundPaint(Color.WHITE); //차트 백그라운 색깔
			chart.setBorderVisible(false); //차트 보더 보임 여부
		
			
			//chart.setBorderPaint(new Color(225,225,225));
			//chart.setBorderStroke(new BasicStroke(3));						
			
			
			//차트 범례 속성 지정
		    legenTitle = chart.getLegend(); //범례 위치 조정
		    legenTitle.setItemFont(new java.awt.Font("맑은 고딕",0,11));
		    legenTitle.setBorder(new BlockBorder(1,1,1,1,new Color(200,200,200)));
			//legenTitle.setPosition(RectangleEdge.RIGHT);					//범례의 플롯 대비 위치
			//legenTitle.setVerticalAlignment(VerticalAlignment.CENTER);		//범례의 세로 위치
		  		    
			
			
			//차트 만들기
			if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+fullChartName),chart,width,height);
			pullPath = urlPath+fullChartName;
			
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			
		}       
		return pullPath;
	}
	
	//(Bar)
	public String makeBar(int categoryType, ArrayList chartData, String chartName, String filePath, String urlPath, int width, int height)
	{
		String pullPath = "";
		String fullChartName = "";
		String lastSiteName = "";
		HashMap chartDataHm = new HashMap();
		DateUtil du = new DateUtil();
		Iterator it = null;				
		String date;
		int term;
		long peorid;
		
		
		DateAxis xAxis = null; 
		NumberAxis yAxis = null;
		CategoryPlot plot = null;
		Plot plot2 = null;
		XYLineAndShapeRenderer renderer = null;
		JFreeChart chart = null;
		
		org.jfree.data.time.Day day = null;
        org.jfree.data.time.Week week = null;
        org.jfree.data.time.Month month = null;
        DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
      
		LegendTitle legenTitle = null;
		LegendTitle legenTitle2 = null;
		
		double tmpData1y = 0;
		double tmpData2y = 0;
		
				
		try{			
			fullChartName = chartName+du.getCurrentDate("yyyyMMddHHmmss")+".png";		
			if(chartData!=null)
			{
				it = chartData.iterator();				
				while(it.hasNext())
				{
					 chartDataHm = new HashMap();
					 chartDataHm = (HashMap)it.next();
					 
					 if(categoryType == 1){
						 dataset1.addValue(new Double((String)chartDataHm.get("CNT")).doubleValue(),(String)chartDataHm.get("CATEGORY"), "");
					 }else{
						 dataset1.addValue(new Double((String)chartDataHm.get("PCNT")).doubleValue(),"긍정",(String)chartDataHm.get("CATEGORY"));
						 dataset1.addValue(new Double((String)chartDataHm.get("NCNT")).doubleValue(),"부정",(String)chartDataHm.get("CATEGORY"));
					 }
				}				
			}
													
			
			
			//X축객체 생성 및 속성지정
			CategoryAxis xAsis = new CategoryAxis();		
			xAsis.setTickMarksVisible(true);
			xAsis.setTickLabelFont(new Font("맑은 고딕", 0, 11)) ;//X축 폰트 , 사이즈
			xAsis.setTickMarkOutsideLength(3); //X축 안쪽눈금 크기			
//			xAsis.setLowerMargin(0.45);  //X축 좌 여백
//			xAsis.setUpperMargin(0.35);  //X축 우 여백
			
		    //Y축 객체  생성 및 속성지정
			NumberAxis yAsis = new NumberAxis();			   	
			yAsis.setTickLabelFont(new Font("맑은 고딕", 0, 11)); //Y축 폰트 , 사이즈
			yAsis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); //Y축 소수점 없이 ..
			yAsis.setTickMarkInsideLength(3);
			yAsis.setTickMarkOutsideLength(3);
			yAsis.setTickMarksVisible(false);
	
			//바 객체 생성 및 속성 지정
			BarRenderer  barRenderer = new BarRenderer();				
			barRenderer.setSeriesStroke(0,new BasicStroke(1)); //선두깨
			barRenderer.setSeriesStroke(1,new BasicStroke(1));
			barRenderer.setSeriesStroke(2,new BasicStroke(1));
			barRenderer.setSeriesStroke(3,new BasicStroke(1));
			barRenderer.setSeriesStroke(4,new BasicStroke(1));
			barRenderer.setSeriesStroke(5,new BasicStroke(1));	
			barRenderer.setSeriesStroke(6,new BasicStroke(1));					
			barRenderer.setSeriesPaint(0,gp0); //선 색깔
			barRenderer.setSeriesPaint(1,gp1);
			barRenderer.setSeriesPaint(2,gp2);
			barRenderer.setSeriesPaint(3,gp3);
			barRenderer.setSeriesPaint(4,gp4);
			barRenderer.setSeriesPaint(5,gp5);
			barRenderer.setSeriesPaint(6,gp6);
			barRenderer.setItemMargin(-0.35);
			barRenderer.setMaximumBarWidth(0.05);
			
			
			
			//XYPlot객체 생성 및 속성 지정
			plot = new CategoryPlot();		
			plot.setDataset(0, dataset1);		
			plot.setRenderer(0, barRenderer);			
			plot.setDomainAxis(0, xAsis);			
			plot.setRangeAxis(0, yAsis);		
			plot.mapDatasetToRangeAxis(0, 0);					
			
						
			plot.setOutlineVisible(true);
			//plot.setBackgroundAlpha(0);
			//plot.setBackgroundPaint(Color.WHITE); 
			plot.setRangeGridlinesVisible(false);
			plot.setDomainGridlinesVisible(false);
			//plot.setRangeGridlinePaint(Color.blue);
			//plot.setDomainGridlinePaint(Color.blue);
			plot.setNoDataMessage("No Data");			
			plot.setRangeZeroBaselineVisible(true);
			plot.setDomainGridlinesVisible(true);
			plot.setRangeGridlinesVisible(true);
			plot.setOrientation(PlotOrientation.VERTICAL);
			
						
			
			
			//차트 객체 생성 및 속성지정
		    chart = new JFreeChart(plot);
		    chart.setBackgroundPaint(Color.WHITE); //차트 백그라운 색깔
			chart.setBorderVisible(false); //차트 보더 보임 여부
		
			
			//chart.setBorderPaint(new Color(225,225,225));
			//chart.setBorderStroke(new BasicStroke(3));						
			
			
			//차트 범례 속성 지정
		    legenTitle = chart.getLegend(); //범례 위치 조정
		    legenTitle.setItemFont(new java.awt.Font("맑은 고딕",0,11));
		    legenTitle.setBorder(new BlockBorder(1,1,1,1,new Color(200,200,200)));
			//legenTitle.setPosition(RectangleEdge.RIGHT);					//범례의 플롯 대비 위치
			//legenTitle.setVerticalAlignment(VerticalAlignment.CENTER);		//범례의 세로 위치
		  		    
			
			
			//차트 만들기
			if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+fullChartName),chart,width,height);
			pullPath = urlPath+fullChartName;
			
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			
		}       
		return pullPath;
	}
	
	public String makeCategoryBar(int categoryType, ArrayList chartData, ArrayList codeList, String chartName, String filePath, String urlPath, int width, int height)
	{
		String pullPath = "";
		String fullChartName = "";
		String lastSiteName = "";
		HashMap chartDataHm = new HashMap();
		DateUtil du = new DateUtil();
		Iterator it = null;				
		String date;
		int term;
		long peorid;
		
		DateAxis xAxis = null; 
		NumberAxis yAxis = null;
		CategoryPlot plot = null;
		Plot plot2 = null;
		XYLineAndShapeRenderer renderer = null;
		JFreeChart chart = null;
		
		org.jfree.data.time.Day day = null;
		org.jfree.data.time.Week week = null;
		org.jfree.data.time.Month month = null;
		DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
		
		LegendTitle legenTitle = null;
		LegendTitle legenTitle2 = null;
		
		double tmpData1y = 0;
		double tmpData2y = 0;
		
		
		try{			
			fullChartName = chartName+du.getCurrentDate("yyyyMMddHHmmss")+".png";		
			if(categoryType == 1){
				for(int i = 0; i < chartData.size(); i++){
					IssueDataBean idb = (IssueDataBean)chartData.get(i);
					for(int j = 0; j < idb.getCnt().length; j++){
						IssueCodeBean icb = (IssueCodeBean)codeList.get((j+1));
						
						//dataset1.addValue(idb.getCnt()[j], (icb.getIc_name().equals("포탈TOP") ? "포탈기사" : icb.getIc_name()), du.getDate(idb.getMd_date(), "MM/dd"));
						
						
						dataset1.addValue(idb.getCnt()[j], icb.getIc_name(), du.getDate(idb.getMd_date(), "MM/dd"));
						
					}
				}
			}
			
			
			
			//X축객체 생성 및 속성지정
			CategoryAxis xAsis = new CategoryAxis();		
			xAsis.setTickMarksVisible(true);
			xAsis.setTickLabelFont(new Font("맑은 고딕", 0, 11)) ;//X축 폰트 , 사이즈
			xAsis.setTickMarkOutsideLength(3); //X축 안쪽눈금 크기			
//			xAsis.setLowerMargin(0.45);  //X축 좌 여백
//			xAsis.setUpperMargin(0.35);  //X축 우 여백
			
			//Y축 객체  생성 및 속성지정
			NumberAxis yAsis = new NumberAxis();			   	
			yAsis.setTickLabelFont(new Font("맑은 고딕", 0, 11)); //Y축 폰트 , 사이즈
			yAsis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); //Y축 소수점 없이 ..
			yAsis.setTickMarkInsideLength(3);
			yAsis.setTickMarkOutsideLength(3);
			yAsis.setTickMarksVisible(false);
			
			//바 객체 생성 및 속성 지정
			BarRenderer  barRenderer = new BarRenderer();		
			barRenderer.setSeriesStroke(0,new BasicStroke(2)); //선두깨
			barRenderer.setSeriesStroke(1,new BasicStroke(2));
			barRenderer.setSeriesStroke(2,new BasicStroke(2));
			barRenderer.setSeriesStroke(3,new BasicStroke(2));
			barRenderer.setSeriesStroke(4,new BasicStroke(2));
			barRenderer.setSeriesStroke(5,new BasicStroke(2));	
			barRenderer.setSeriesStroke(6,new BasicStroke(2));					
			barRenderer.setSeriesPaint(0,gp0); //선 색깔
			barRenderer.setSeriesPaint(1,gp1);
			barRenderer.setSeriesPaint(2,gp2);
			barRenderer.setSeriesPaint(3,gp3);
			barRenderer.setSeriesPaint(4,gp4);
			barRenderer.setSeriesPaint(5,gp5);
			barRenderer.setSeriesPaint(6,gp6);
			
			
			barRenderer.setShadowVisible(false);
			barRenderer.setDrawBarOutline(false);
			barRenderer.setBarPainter(new StandardBarPainter());
			barRenderer.setItemLabelsVisible(true);
			ItemLabelPosition ilp = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.TOP_CENTER);
			barRenderer.setBasePositiveItemLabelPosition(ilp);

			barRenderer.setBaseItemLabelFont(new Font("맑은고딕",0,9));
			barRenderer.setBaseItemLabelsVisible(true);
			barRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			
			
			
			//barRenderer.setItemMargin(-0.35);
			//barRenderer.setMaximumBarWidth(0.05);
			
			
			
			//XYPlot객체 생성 및 속성 지정
			plot = new CategoryPlot();		
			plot.setDataset(0, dataset1);		
			plot.setRenderer(0, barRenderer);			
			plot.setDomainAxis(0, xAsis);			
			plot.setRangeAxis(0, yAsis);		
			plot.mapDatasetToRangeAxis(0, 0);		
			
			
			
			plot.setOutlineVisible(true);
			//plot.setBackgroundAlpha(0);
			//plot.setBackgroundPaint(Color.WHITE); 
			plot.setRangeGridlinesVisible(false);
			plot.setDomainGridlinesVisible(false);
			//plot.setRangeGridlinePaint(Color.blue);
			//plot.setDomainGridlinePaint(Color.blue);
			plot.setNoDataMessage("No Data");			
			plot.setRangeZeroBaselineVisible(true);
			plot.setDomainGridlinesVisible(true);
			plot.setRangeGridlinesVisible(true);
			plot.setOrientation(PlotOrientation.VERTICAL);
			
			
			
			
			//차트 객체 생성 및 속성지정
			chart = new JFreeChart(plot);
			chart.setBackgroundPaint(Color.WHITE); //차트 백그라운 색깔
			chart.setBorderVisible(false); //차트 보더 보임 여부
			
			
			//chart.setBorderPaint(new Color(225,225,225));
			//chart.setBorderStroke(new BasicStroke(3));						
			
			
			//차트 범례 속성 지정
			legenTitle = chart.getLegend(); //범례 위치 조정
			legenTitle.setItemFont(new java.awt.Font("맑은 고딕",0,11));
			legenTitle.setBorder(new BlockBorder(1,1,1,1,new Color(200,200,200)));
			//legenTitle.setPosition(RectangleEdge.RIGHT);					//범례의 플롯 대비 위치
			//legenTitle.setVerticalAlignment(VerticalAlignment.CENTER);		//범례의 세로 위치
			
			
			
			//차트 만들기
			if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+fullChartName),chart,width,height);
			pullPath = urlPath+fullChartName;
			
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			
		}       
		return pullPath;
	}
	
	
	public String makeCategoryBar_2D(int categoryType, ArrayList chartData, ArrayList codeList, String chartName, String filePath, String urlPath, int width, int height)
	{
		String pullPath = "";
		String fullChartName = "";
		String lastSiteName = "";
		HashMap chartDataHm = new HashMap();
		DateUtil du = new DateUtil();
		Iterator it = null;				
		String date;
		int term;
		long peorid;
		
		
		DateAxis xAxis = null; 
		NumberAxis yAxis = null;
		CategoryPlot plot = null;
		Plot plot2 = null;
		BarRenderer renderer = null;
		JFreeChart chart = null;
		
		org.jfree.data.time.Day day = null;
		org.jfree.data.time.Week week = null;
		org.jfree.data.time.Month month = null;
		DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
		
		LegendTitle legenTitle = null;
		LegendTitle legenTitle2 = null;
		
		double tmpData1y = 0;
		double tmpData2y = 0;
		
		
		try{			
			fullChartName = chartName+du.getCurrentDate("yyyyMMddHHmmss")+".png";		
			if(categoryType == 1){
				for(int i = 0; i < chartData.size(); i++){
					IssueDataBean idb = (IssueDataBean)chartData.get(i);
					for(int j = 0; j < idb.getCnt().length; j++){
						IssueCodeBean icb = (IssueCodeBean)codeList.get((j+1));
						
						dataset1.addValue(idb.getCnt()[j], (icb.getIc_name().equals("포탈TOP") ? "포탈기사" : icb.getIc_name()), du.getDate(idb.getMd_date(), "MM/dd"));
					}
				}
			}
			
			
			
			
			chart = ChartFactory.createBarChart(
		            "",       // chart title
		            "",               // domain axis label
		            "",                  // range axis label
		            dataset1,                  // data
		            PlotOrientation.VERTICAL, // orientation
		            true,                    // include legend
		            false,                     // tooltips?
		            false                     // URLs?
		        );
			
			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			plot = chart.getCategoryPlot(); 
			plot.setNoDataMessage("No Data");
			renderer = (BarRenderer) plot.getRenderer();
			
			
			
			//범례 위치 조정
			legenTitle = chart.getLegend();
			legenTitle.setItemFont(new java.awt.Font("맑은고딕",1,11));
			legenTitle.setBorder(new BlockBorder(0,0,0,0,new Color(200,200,200)));
			//lt.setPosition(RectangleEdge.RIGHT);					//범례의 플롯 대비 위치
			//lt.setVerticalAlignment(VerticalAlignment.CENTER);		//범례의 세로 위치
			
			
		
			//차트 백그라운 색깔
			chart.setBackgroundPaint(Color.WHITE);
			chart.setBorderVisible(false);
			//chart.setBorderPaint(new Color(225,225,225));
			//chart.setBorderStroke(new BasicStroke(3));
			
			// 속성 및 라벨
			plot.setOutlineVisible(false);
			//plot.setBackgroundAlpha(0);
			
			//차트 Bg color
			plot.setBackgroundPaint(Color.WHITE); 
			plot.setRangeGridlinesVisible(false);
			plot.setDomainGridlinesVisible(false);
			
			//X축
			//plot.getDomainAxis().setLabelFont(new Font("맑은고딕",0,11));
			plot.getDomainAxis().setTickMarksVisible(true);
			plot.getDomainAxis().setTickLabelFont(new Font("맑은고딕",0,11));
			plot.getDomainAxis().setTickMarkOutsideLength(3);

			
			
			//Y축 		    
			plot.getRangeAxis().setTickLabelFont(new Font("맑은고딕",0,11));
			plot.getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			plot.getRangeAxis().setTickMarkInsideLength(3);
			plot.getRangeAxis().setTickMarkOutsideLength(3);
			plot.getRangeAxis().setTickMarksVisible(false);
		
		   	
		   	
		   	
		    //Y축 안쪽 눈금크기
		    //yAxis.setTickMarkInsideLength(3);
		    //yAxis.setTickMarkOutsideLength(3);
			
			//plot.setRangeZeroBaselineVisible(true);
			
		   	
		    //x축 폰트 , 사이즈
		    //xAxis.setTickLabelFont(new Font("gulim", Font.PLAIN, 10));
		    //xAxis.setTickMarksVisible(false);
		    
		    //x축 라벨 폰트,사이즈
		    //bar 간격
			
		    //좌우 여백
			//xAxis.setLowerMargin(0.45);  //좌
			//xAxis.setUpperMargin(0.35);  //우
			//Y축 소수점 없이 ..
		    
		    //Y축 안쪽 눈금크기
		    //yAxis.setTickMarkInsideLength(3);
		    //yAxis.setTickMarksVisible(false);
	
			
			renderer.setDrawBarOutline(false);
			
			
			renderer.setShadowVisible(false);
			renderer.setBarPainter(new StandardBarPainter());
			//renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setItemLabelsVisible(true);
			
			ItemLabelPosition ilp = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.TOP_CENTER);
			renderer.setBasePositiveItemLabelPosition(ilp);
			//renderer.setAutoPopulateSeriesStroke(false);
			renderer.setBaseItemLabelFont(new Font("맑은고딕",0,9));
		    renderer.setBaseItemLabelsVisible(true);
		    renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		    //renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}",NumberFormat.getNumberInstance() ));
		    
			//renderer.setAutoPopulateSeriesStroke(false);
			
			
			//bar 그림자
			//renderer.setShadowVisible(false);
			
			renderer.setSeriesPaint(0, gp0);
			renderer.setSeriesPaint(1, gp1);
			renderer.setSeriesPaint(2, gp2);
			renderer.setSeriesPaint(3, gp3);
			renderer.setSeriesPaint(4, gp4);
			renderer.setSeriesPaint(5, gp5);
			renderer.setSeriesPaint(6, gp6);
			
			
			
			
			//차트 만들기
			if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+fullChartName),chart,width,height);
			pullPath = urlPath+fullChartName;
			
			
			

			
			

			
			/*
			
			//X축객체 생성 및 속성지정
			CategoryAxis xAsis = new CategoryAxis();		
			xAsis.setTickMarksVisible(true);
			xAsis.setTickLabelFont(new Font("맑은 고딕", 0, 11)) ;//X축 폰트 , 사이즈
			xAsis.setTickMarkOutsideLength(3); //X축 안쪽눈금 크기			
//			xAsis.setLowerMargin(0.45);  //X축 좌 여백
//			xAsis.setUpperMargin(0.35);  //X축 우 여백
			
			//Y축 객체  생성 및 속성지정
			NumberAxis yAsis = new NumberAxis();			   	
			yAsis.setTickLabelFont(new Font("맑은 고딕", 0, 11)); //Y축 폰트 , 사이즈
			yAsis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); //Y축 소수점 없이 ..
			yAsis.setTickMarkInsideLength(3);
			yAsis.setTickMarkOutsideLength(3);
			yAsis.setTickMarksVisible(false);
			
			//바 객체 생성 및 속성 지정
			BarRenderer  barRenderer = new BarRenderer();		
			barRenderer.setSeriesStroke(0,new BasicStroke(2)); //선두깨
			barRenderer.setSeriesStroke(1,new BasicStroke(2));
			barRenderer.setSeriesStroke(2,new BasicStroke(2));
			barRenderer.setSeriesStroke(3,new BasicStroke(2));
			barRenderer.setSeriesStroke(4,new BasicStroke(2));
			barRenderer.setSeriesStroke(5,new BasicStroke(2));	
			barRenderer.setSeriesStroke(6,new BasicStroke(2));					
			barRenderer.setSeriesPaint(0,gp0); //선 색깔
			barRenderer.setSeriesPaint(1,gp1);
			barRenderer.setSeriesPaint(2,gp2);
			barRenderer.setSeriesPaint(3,gp3);
			barRenderer.setSeriesPaint(4,gp4);
			barRenderer.setSeriesPaint(5,gp5);
			barRenderer.setSeriesPaint(6,gp6);
			
			
			//barRenderer.setItemMargin(-0.35);
			//barRenderer.setMaximumBarWidth(0.05);
			
			
			
			//XYPlot객체 생성 및 속성 지정
			plot = new CategoryPlot();		
			plot.setDataset(0, dataset1);		
			plot.setRenderer(0, barRenderer);			
			plot.setDomainAxis(0, xAsis);			
			plot.setRangeAxis(0, yAsis);		
			plot.mapDatasetToRangeAxis(0, 0);		
			
			
			
			plot.setOutlineVisible(true);
			//plot.setBackgroundAlpha(0);
			//plot.setBackgroundPaint(Color.WHITE); 
			plot.setRangeGridlinesVisible(false);
			plot.setDomainGridlinesVisible(false);
			//plot.setRangeGridlinePaint(Color.blue);
			//plot.setDomainGridlinePaint(Color.blue);
			plot.setNoDataMessage("No Data");			
			plot.setRangeZeroBaselineVisible(true);
			plot.setDomainGridlinesVisible(true);
			plot.setRangeGridlinesVisible(true);
			plot.setOrientation(PlotOrientation.VERTICAL);
			
			
			
			
			//차트 객체 생성 및 속성지정
			chart = new JFreeChart(plot);
			chart.setBackgroundPaint(Color.WHITE); //차트 백그라운 색깔
			chart.setBorderVisible(false); //차트 보더 보임 여부
			
			
			//chart.setBorderPaint(new Color(225,225,225));
			//chart.setBorderStroke(new BasicStroke(3));						
			
			
			//차트 범례 속성 지정
			legenTitle = chart.getLegend(); //범례 위치 조정
			legenTitle.setItemFont(new java.awt.Font("맑은 고딕",0,11));
			legenTitle.setBorder(new BlockBorder(1,1,1,1,new Color(200,200,200)));
			//legenTitle.setPosition(RectangleEdge.RIGHT);					//범례의 플롯 대비 위치
			//legenTitle.setVerticalAlignment(VerticalAlignment.CENTER);		//범례의 세로 위치
			
			
			
			//차트 만들기
			if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+fullChartName),chart,width,height);
			pullPath = urlPath+fullChartName;
			*/
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			
		}       
		return pullPath;
	}
	
	
	public String makeStackBar(int categoryType, ArrayList chartData, String chartName, String filePath, String urlPath, int width, int height){
		return makeStackBar(categoryType, chartData, chartName, filePath, urlPath, width, height, "", "");
	}
	
	//StackBar
	public String makeStackBar(int categoryType, ArrayList chartData, String chartName, String filePath, String urlPath, int width, int height, String vertical, String percentage)
	{
		String pullPath = "";
		String fullChartName = "";
		String lastSiteName = "";
		HashMap chartDataHm = new HashMap();
		DateUtil du = new DateUtil();
		Iterator it = null;				
		String date;
		int term;
		long peorid;
		
		
		DateAxis xAxis = null; 
		NumberAxis yAxis = null;
		CategoryPlot plot = null;
		Plot plot2 = null;
		XYLineAndShapeRenderer renderer = null;
		JFreeChart chart = null;
		
		org.jfree.data.time.Day day = null;
        org.jfree.data.time.Week week = null;
        org.jfree.data.time.Month month = null;
        DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
        		
		
		LegendTitle legenTitle = null;
		LegendTitle legenTitle2 = null;
		
		double tmpData1y = 0;
		double tmpData2y = 0;
		
				
		try{			
			fullChartName = chartName+du.getCurrentDate("yyyyMMddHHmmss")+".png";		
			dataset1 = setStackBarDataSet(categoryType,chartData);						
			
			
			//CategoryDomainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.52359877559829882D));
			
			//X축객체 생성 및 속성지정
			CategoryAxis xAsis = new CategoryAxis();		
			xAsis.setTickMarksVisible(true);
			xAsis.setTickLabelFont(new Font("맑은 고딕", 0, 9)) ;//X축 폰트 , 사이즈
			xAsis.setTickMarkOutsideLength(3); //X축 안쪽눈금 크기
			//xAsis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.52359877559829882D));
			xAsis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(1.0));
//			xAsis.setLowerMargin(0.45);  //X축 좌 여백
//			xAsis.setUpperMargin(0.35);  //X축 우 여백
			
			

		    
		    //Y축 객체  생성 및 속성지정
			NumberAxis yAsis = new NumberAxis();			   	
			yAsis.setTickLabelFont(new Font("맑은 고딕", 0, 11)); //Y축 폰트 , 사이즈
			yAsis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); //Y축 소수점 없이 ..
			yAsis.setTickMarkInsideLength(3);
			yAsis.setTickMarkOutsideLength(3);
			yAsis.setTickMarksVisible(false);
	
			//바 객체 생성 및 속성 지정
			BarRenderer  barRenderer = new StackedBarRenderer();					
			barRenderer.setSeriesStroke(0,new BasicStroke(1)); //선두깨
			barRenderer.setSeriesStroke(1,new BasicStroke(1));
			barRenderer.setSeriesStroke(2,new BasicStroke(1));
			barRenderer.setSeriesStroke(3,new BasicStroke(1));
			barRenderer.setSeriesStroke(4,new BasicStroke(1));
			barRenderer.setSeriesStroke(5,new BasicStroke(1));	
			barRenderer.setSeriesStroke(6,new BasicStroke(1));					
			barRenderer.setSeriesPaint(0,gp0); //선 색깔
			barRenderer.setSeriesPaint(1,gp1);
			barRenderer.setSeriesPaint(2,gp2);
			barRenderer.setSeriesPaint(3,gp3);
			barRenderer.setSeriesPaint(4,gp4);
			barRenderer.setSeriesPaint(5,gp5);
			barRenderer.setSeriesPaint(6,gp6);
			barRenderer.setItemMargin(-0.35);
			barRenderer.setMaximumBarWidth(0.05);
			
		
			
			//XYPlot객체 생성 및 속성 지정
			plot = new CategoryPlot();		
			plot.setDataset(0, dataset1);		
			plot.setRenderer(0, barRenderer);			
			plot.setDomainAxis(0, xAsis);			
			plot.setRangeAxis(0, yAsis);		
			plot.mapDatasetToRangeAxis(0, 0);					
			
						
			plot.setOutlineVisible(false);
			//plot.setBackgroundAlpha(0);
			//plot.setBackgroundPaint(Color.WHITE); 
			plot.setRangeGridlinesVisible(false);
			plot.setDomainGridlinesVisible(false);
			//plot.setRangeGridlinePaint(Color.blue);
			//plot.setDomainGridlinePaint(Color.blue);
			plot.setNoDataMessage("No Data");			
			plot.setRangeZeroBaselineVisible(true);
			plot.setDomainGridlinesVisible(true);
			plot.setRangeGridlinesVisible(true);
			if(vertical.equals("")){
				plot.setOrientation(PlotOrientation.HORIZONTAL);
			}else{
				plot.setOrientation(PlotOrientation.VERTICAL);
			}
			

			if(!percentage.equals("")){
				NumberAxis numberaxis = (NumberAxis)plot.getRangeAxis();
		        numberaxis.setNumberFormatOverride(new DecimalFormat("0%"));
		        numberaxis.setTickLabelFont(new Font("굴림",java.awt.Font.PLAIN ,12));
				((StackedBarRenderer) barRenderer).setRenderAsPercentages(true);
				barRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{3}", NumberFormat.getIntegerInstance(), new DecimalFormat("0.0%")));
				barRenderer.setBaseItemLabelsVisible(true);
			}
			
			//차트 객체 생성 및 속성지정
		    chart = new JFreeChart(plot);
		    chart.setBackgroundPaint(Color.WHITE); //차트 백그라운 색깔
			chart.setBorderVisible(false); //차트 보더 보임 여부
			//chart.setBorderPaint(new Color(225,225,225));
			//chart.setBorderStroke(new BasicStroke(3));						
			
			
			//차트 범례 속성 지정
		    legenTitle = chart.getLegend(); //범례 위치 조정
		    legenTitle.setItemFont(new java.awt.Font("맑은 고딕",0,11));
		    legenTitle.setBorder(new BlockBorder(1,1,1,1,new Color(200,200,200)));
			//legenTitle.setPosition(RectangleEdge.RIGHT);					//범례의 플롯 대비 위치
			//legenTitle.setVerticalAlignment(VerticalAlignment.CENTER);		//범례의 세로 위치
		  		    
			
			
			//차트 만들기
			if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+fullChartName),chart,width,height);
			pullPath = urlPath+fullChartName;
			
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			
		}       
		return pullPath;
	}
	
	//(LineBar)
	public String makeLineBar(int categoryType, ArrayList chartData, String chartName, String filePath, String urlPath, int width, int height)
	{
		String pullPath = "";
		String fullChartName = "";
		String lastSiteName = "";
		HashMap chartDataHm = new HashMap();
		DateUtil du = new DateUtil();
		Iterator it = null;				
		String date;
		int term;
		long peorid;
		double tmpData1y = 0;
		double tmpData2y = 0;
		
		DateAxis xAxis = null; 
		NumberAxis yAxis = null;
		XYPlot plot = null;
		XYPlot plot2 = null;
		XYLineAndShapeRenderer renderer = null;
		JFreeChart chart = null;
		
		org.jfree.data.time.Day day = null;
        org.jfree.data.time.Week week = null;
        org.jfree.data.time.Month month = null;
		CategoryTableXYDataset dataset1 = new CategoryTableXYDataset();
		CategoryTableXYDataset dataset2 = new CategoryTableXYDataset();
		CategoryTableXYDataset[] dataset = null;
		
		LegendTitle legenTitle = null;
		LegendTitle legenTitle2 = null;
				
		try{			
			fullChartName = chartName+du.getCurrentDate("yyyyMMddHHmmss")+".png";		
			
			dataset = setDateLineBarDataset(categoryType,chartData);
			
			dataset1 = dataset[0];
			dataset2 = dataset[1];
			
			tmpData1y = this.tmpData1y;
			tmpData2y = this.tmpData2y;
									
															
			//라인 객체 생성및 속성지정
			renderer = new XYLineAndShapeRenderer();			
			renderer.setSeriesStroke(0,new BasicStroke(2)); //선두깨
			renderer.setSeriesStroke(1,new BasicStroke(2));
			renderer.setSeriesStroke(2,new BasicStroke(2));
			renderer.setSeriesStroke(3,new BasicStroke(2));
			renderer.setSeriesStroke(4,new BasicStroke(2));
			renderer.setSeriesStroke(5,new BasicStroke(2));	
			renderer.setSeriesStroke(6,new BasicStroke(2));					
			renderer.setSeriesPaint(0,gp2,false); //선 색깔
			renderer.setSeriesPaint(1,gp3,false);
			renderer.setSeriesPaint(2,gp4,false);
			renderer.setSeriesPaint(3,gp5,false);
			renderer.setSeriesPaint(4,gp6,false);
			renderer.setSeriesPaint(5,gp7,false);
			renderer.setSeriesPaint(6,gp8,false);			
			renderer.setSeriesShapesVisible(0, false); //꼭지점 생성여부
			renderer.setSeriesShapesVisible(1, false);
			renderer.setSeriesShapesVisible(2, false);
			renderer.setSeriesShapesVisible(3, false);
			renderer.setSeriesShapesVisible(4, false);
			renderer.setSeriesShapesVisible(5, false);
			renderer.setSeriesShapesVisible(6, false);			
			
			
			
			//X축객체 생성 및 속성지정
			DateAxis xAsis = new DateAxis();		
			xAsis.setTickMarksVisible(true);
			xAsis.setTickLabelFont(new Font("맑은 고딕", 0, 9)) ;//X축 폰트 , 사이즈
			xAsis.setDateFormatOverride(new SimpleDateFormat("MM월DD일"));	//X축 Date 출력방식 설정
			xAsis.setTickMarkOutsideLength(3); //X축 안쪽눈금 크기
//			xAsis.setLowerMargin(0.45);  //X축 좌 여백
//			xAsis.setUpperMargin(0.35);  //X축 우 여백					
			term = 1; //X축기간에 따른 출력 텀
			peorid = chartData.size();		   	
			if(peorid>15) 		term = 2;
		   	if(peorid>30)		term = 3;
		   	if(peorid>40)		term = 4;
		   	if(peorid>50)		term = 5;
		   	if(peorid>60)		term = 6;
		   	if(peorid>70)		term = 0;		   	
		   	if(term != 0){
		   		xAsis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,term));
		   	}			
		    if(peorid>0){
		    	xAsis.setFixedAutoRange((79200000*peorid)+(79200000*((long)term-1)));  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 )
			}
		    
		    //X축객체 생성 및 속성지정
			DateAxis xAsis2 = new DateAxis();		
			xAsis2.setTickMarksVisible(true);
			xAsis2.setTickLabelFont(new Font("맑은 고딕", 0, 11)) ;//X축 폰트 , 사이즈
			xAsis2.setDateFormatOverride(new SimpleDateFormat("MM월DD일"));	//X축 Date 출력방식 설정
			xAsis2.setTickMarkOutsideLength(3); //X축 안쪽눈금 크기
//			xAsis.setLowerMargin(0.45);  //X축 좌 여백
//			xAsis.setUpperMargin(0.35);  //X축 우 여백					
			term = 1; //X축기간에 따른 출력 텀
			peorid = chartData.size();		   	
			if(peorid>15) 		term = 2;
		   	if(peorid>30)		term = 3;
		   	if(peorid>40)		term = 4;
		   	if(peorid>50)		term = 5;
		   	if(peorid>60)		term = 6;
		   	if(peorid>70)		term = 0;		   	
		   	if(term != 0){
		   		xAsis2.setTickUnit(new DateTickUnit(DateTickUnit.DAY,term));
		   	}			
		    if(peorid>0){
		    	xAsis2.setFixedAutoRange((79200000*peorid)+(79200000*((long)term-1)));  // 단위-밀리세컨드 //1시간 범위(3300000.0)1일(79200000.0) 1주일(554400000.0) (시간축 )
			}
			
		    
		    //Y축 객체  생성 및 속성지정
			NumberAxis yAsis = new NumberAxis();			   	
			yAsis.setTickLabelFont(new Font("맑은 고딕", 0, 11)); //Y축 폰트 , 사이즈
			yAsis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); //Y축 소수점 없이 ..
			yAsis.setTickMarkInsideLength(3);
			yAsis.setTickMarkOutsideLength(3);
			yAsis.setTickMarksVisible(false);
			yAsis.setVisible(true);
			yAsis.setAutoRange(true);
			
			NumberAxis yAsis2 = new NumberAxis();			   	
			yAsis2.setTickLabelFont(new Font("맑은 고딕", 0, 11)); //Y축 폰트 , 사이즈
			yAsis2.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); //Y축 소수점 없이 ..
			yAsis2.setTickMarkInsideLength(3);
			yAsis2.setTickMarkOutsideLength(3);
			yAsis2.setTickMarksVisible(false);		
			yAsis2.setVisible(true);
			yAsis2.setAutoRange(true);
			
			//바 객체 생성 및 속성 지정
			XYBarRenderer barRenderer = new XYBarRenderer();					
			barRenderer.setSeriesStroke(0,new BasicStroke(1)); //선두깨
			barRenderer.setSeriesStroke(1,new BasicStroke(1));
			barRenderer.setSeriesStroke(2,new BasicStroke(1));
			barRenderer.setSeriesStroke(3,new BasicStroke(1));
			barRenderer.setSeriesStroke(4,new BasicStroke(1));
			barRenderer.setSeriesStroke(5,new BasicStroke(1));	
			barRenderer.setSeriesStroke(6,new BasicStroke(1));					
			barRenderer.setSeriesPaint(0,gp0,false); //선 색깔
			barRenderer.setSeriesPaint(1,gp1,false);
			barRenderer.setSeriesPaint(2,gp2,false);
			barRenderer.setSeriesPaint(3,gp3,false);
			barRenderer.setSeriesPaint(4,gp4,false);
			barRenderer.setSeriesPaint(5,gp5,false);
			barRenderer.setSeriesPaint(6,gp6,false);
			barRenderer.setShadowVisible(false); //그림자 효과 여부
			barRenderer.setMargin(0.7);
			
			
			//XYPlot객체 생성 및 속성 지정
			plot = new XYPlot();		
			plot.setDataset(0, dataset1);
			plot.setDataset(1, dataset2);
			plot.setRenderer(0, renderer);
			plot.setRenderer(1, barRenderer);			
			plot.setDomainAxis(0, xAsis);			
			//plot.setDomainAxis(1, xAsis2);			
			plot.setRangeAxis(0, yAsis);
			plot.setRangeAxis(1, yAsis2);
		
			
			plot.mapDatasetToRangeAxis(0, 0);
			plot.mapDatasetToRangeAxis(1, 1);
						
			plot.getRangeAxis(0).setRange(-tmpData1y,tmpData1y);
			plot.getRangeAxis(1).setRange(-tmpData2y, tmpData2y);
						
			plot.setOutlineVisible(true);
			//plot.setBackgroundAlpha(0);
			//plot.setBackgroundPaint(Color.WHITE); 
			plot.setRangeGridlinesVisible(false);
			plot.setDomainGridlinesVisible(false);
			//plot.setRangeGridlinePaint(Color.blue);
			//plot.setDomainGridlinePaint(Color.blue);
			plot.setNoDataMessage("No Data");			
			plot.setRangeZeroBaselineVisible(true);		
			plot.setDomainGridlinesVisible(true);
			plot.setRangeGridlinesVisible(true);
			
			//차트 객체 생성 및 속성지정
		    chart = new JFreeChart(plot);
		   
		    chart.setBackgroundPaint(Color.WHITE); //차트 백그라운 색깔
			chart.setBorderVisible(false); //차트 보더 보임 여부
			//chart.setBorderPaint(new Color(225,225,225));
			//chart.setBorderStroke(new BasicStroke(3));						
			
			
			//차트 범례 속성 지정
		    legenTitle = chart.getLegend(); //범례 위치 조정
		    legenTitle.setItemFont(new java.awt.Font("맑은 고딕",0,11));
		    legenTitle.setBorder(new BlockBorder(1,1,1,1,new Color(200,200,200)));
			//legenTitle.setPosition(RectangleEdge.RIGHT);					//범례의 플롯 대비 위치
			//legenTitle.setVerticalAlignment(VerticalAlignment.CENTER);		//범례의 세로 위치
		  		    
			
			
			//차트 만들기
			if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+fullChartName),chart,width,height);
			pullPath = urlPath+fullChartName;
			
			
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			
		}       
		return pullPath;
	}
	
	
	//StackBar
	public String makeStackBar_2015(int categoryType, ArrayList chartData, String chartName, String filePath, String urlPath, int width, int height, String vertical, String percentage)
		{
			String pullPath = "";
			String fullChartName = "";
			String lastSiteName = "";
			HashMap chartDataHm = new HashMap();
			DateUtil du = new DateUtil();
			Iterator it = null;				
			String date;
			int term;
			long peorid;
			
			
			DateAxis xAxis = null; 
			NumberAxis yAxis = null;
			CategoryPlot plot = null;
			Plot plot2 = null;
			XYLineAndShapeRenderer renderer = null;
			JFreeChart chart = null;
			
			org.jfree.data.time.Day day = null;
	        org.jfree.data.time.Week week = null;
	        org.jfree.data.time.Month month = null;
	        DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
	        		
			
			LegendTitle legenTitle = null;
			LegendTitle legenTitle2 = null;
			
			double tmpData1y = 0;
			double tmpData2y = 0;
			
					
			try{			
				fullChartName = chartName+du.getCurrentDate("yyyyMMddHHmmss")+".png";		
				//데이터 set  주요이슈분석 Bar 
				//dataset1 = setStackBarDataSet(categoryType,chartData);						
				dataset1 = setBarDataSetForSamsungFire(chartData);
				
				
				//CategoryDomainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.52359877559829882D));
				
				//X축객체 생성 및 속성지정
				CategoryAxis xAsis = new CategoryAxis();		
				xAsis.setTickMarksVisible(true);
				xAsis.setTickLabelFont(new Font("맑은 고딕", 0, 7)) ;//X축 폰트 , 사이즈
				xAsis.setTickMarkOutsideLength(3); //X축 안쪽눈금 크기
				//xAsis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.52359877559829882D));
				//xAsis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(1.0));
//				xAsis.setLowerMargin(0.45);  //X축 좌 여백
//				xAsis.setUpperMargin(0.35);  //X축 우 여백
				
				

			    
			    //Y축 객체  생성 및 속성지정
				NumberAxis yAsis = new NumberAxis();			   	
				yAsis.setTickLabelFont(new Font("맑은 고딕", 0, 11)); //Y축 폰트 , 사이즈
				yAsis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); //Y축 소수점 없이 ..
				yAsis.setTickMarkInsideLength(3);
				yAsis.setTickMarkOutsideLength(3);
				yAsis.setTickMarksVisible(false);
		
				//바 객체 생성 및 속성 지정
				BarRenderer  barRenderer = new StackedBarRenderer();
				//BarRenderer  barRenderer = new BarRenderer();
				barRenderer.setSeriesStroke(0,new BasicStroke(1)); //선두깨
				//barRenderer.setSeriesStroke(1,new BasicStroke(1));
				//barRenderer.setSeriesStroke(2,new BasicStroke(1));
				//barRenderer.setSeriesStroke(3,new BasicStroke(1));
				//barRenderer.setSeriesStroke(4,new BasicStroke(1));
				//barRenderer.setSeriesStroke(5,new BasicStroke(1));	
				//barRenderer.setSeriesStroke(6,new BasicStroke(1));
			
				
				barRenderer.setSeriesPaint(0,gpgray); //선 색깔
				//barRenderer.setSeriesPaint(1,gpRed);
				//barRenderer.setSeriesPaint(2,gpGreen);
				//barRenderer.setSeriesPaint(3,gpPpt4);
				//barRenderer.setSeriesPaint(4,gpPpt5);
				//barRenderer.setSeriesPaint(5,gpPpt6);
				//barRenderer.setSeriesPaint(6,gpPpt7);
				barRenderer.setItemMargin(-0.35);
				barRenderer.setMaximumBarWidth(0.06);
				
				barRenderer.setBaseItemLabelsVisible(true);
				barRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				barRenderer.setBarPainter(new StandardBarPainter());
				
				//barRenderer.setMaximumBarWidth(0.1);
				//barRenderer.setBarPainter(new StandardBarPainter());
				
				//XYPlot객체 생성 및 속성 지정
				
				plot = new CategoryPlot();
				
				plot.setDataset(0, dataset1);		
				plot.setRenderer(0, barRenderer);			
				plot.setDomainAxis(0, xAsis);			
				plot.setRangeAxis(0, yAsis);		
				plot.mapDatasetToRangeAxis(0, 0);					
				
				
							
				plot.setOutlineVisible(false);
				//plot.setBackgroundAlpha(0);
				//plot.setBackgroundPaint(Color.WHITE); 
				plot.setRangeGridlinesVisible(false);
				plot.setDomainGridlinesVisible(false);
				//plot.setRangeGridlinePaint(Color.blue);
				//plot.setDomainGridlinePaint(Color.blue);
				plot.setNoDataMessage("No Data");			
				plot.setRangeZeroBaselineVisible(true);
				plot.setDomainGridlinesVisible(true);
				plot.setRangeGridlinesVisible(true);
				if(vertical.equals("")){
					//plot.setOrientation(PlotOrientation.HORIZONTAL);
					plot.setOrientation(PlotOrientation.VERTICAL);
				}else{
					plot.setOrientation(PlotOrientation.VERTICAL);
				}
				
				 /*if(!percentage.equals("")){
				NumberAxis numberaxis = (NumberAxis)plot.getRangeAxis();
		        numberaxis.setNumberFormatOverride(new DecimalFormat("0%"));
		        numberaxis.setTickLabelFont(new Font("굴림",java.awt.Font.PLAIN ,12));
				((StackedBarRenderer) barRenderer).setRenderAsPercentages(true);
				barRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{3}", NumberFormat.getIntegerInstance(), new DecimalFormat("0.0%")));
				barRenderer.setBaseItemLabelsVisible(true);
				}*/

				//라인차트 추가하기~~
				// now create the second dataset and renderer...
		        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
		        //dataset2 = setStackBarDataSet(3,chartData);
		        dataset2 = setLineDataSetForSamsungFire(chartData);
		        final CategoryItemRenderer renderer2 = new LineAndShapeRenderer();
		        
		        renderer2.setSeriesStroke(0,new BasicStroke(2)); //선두깨
		        //renderer2.setSeriesPaint(0,gpblack); //선 색깔
		        renderer2.setBaseItemLabelsVisible(true);
		        renderer2.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		        
		        ItemLabelPosition ilp = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.BASELINE_LEFT);
				renderer2.setBasePositiveItemLabelPosition(ilp);
		        
		        plot.setDataset(1, dataset2);
		        plot.setRenderer(1, renderer2);

		        // create the third dataset and renderer...
		        final NumberAxis rangeAxis2 = new NumberAxis();
		        rangeAxis2.setRange(0.0, 100.0);
		        //rangeAxis2.setTickUnit(new NumberTickUnit(10));
		        //rangeAxis2.setTickUnit(new NumberTickUnit(.1, new DecimalFormat("0%")));
		        //rangeAxis2.setNumberFormatOverride(NumberFormat.getPercentInstance());
		        
		        rangeAxis2.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		        rangeAxis2.setAutoRangeIncludesZero(true);
		        
		        plot.setRangeAxis(1, rangeAxis2);
		        plot.mapDatasetToRangeAxis(1,1);
		        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
				
				
				//차트 객체 생성 및 속성지정
			    chart = new JFreeChart(plot);
			    chart.setBackgroundPaint(Color.WHITE); //차트 백그라운 색깔
				chart.setBorderVisible(false); //차트 보더 보임 여부
				//chart.setBorderPaint(new Color(225,225,225));
				//chart.setBorderStroke(new BasicStroke(3));						
				
				
				//차트 범례 속성 지정
			    legenTitle = chart.getLegend(); //범례 위치 조정
			    legenTitle.setItemFont(new java.awt.Font("맑은 고딕",0,11));
			    legenTitle.setBorder(new BlockBorder(1,1,1,1,new Color(200,200,200)));
				//legenTitle.setPosition(RectangleEdge.RIGHT);					//범례의 플롯 대비 위치
				//legenTitle.setVerticalAlignment(VerticalAlignment.CENTER);		//범례의 세로 위치
				
				
				//차트 만들기
				if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
				ChartUtilities.saveChartAsPNG(new File(filePath+fullChartName),chart,width,height);
				pullPath = urlPath+fullChartName;
				
			}catch(Exception e){
				Log.writeExpt(e);
			}finally{
				
			}       
			return pullPath;
		}
	
	public String createChartCS(ArrayList issueTrendCnt, ArrayList productList, String chartName, String filePath, String urlPath, int width, int height){
		String chartPath = "";
		DateUtil du = new DateUtil();
		ConfigUtil cu = new ConfigUtil();
		String createTime = du.getCurrentDate("yyyyMMddHHmmss");
		
		
		
		

		String fullChartName = chartName+du.getCurrentDate("yyyyMMddHHmmss")+".png";	
		
		try{
			JFreeChart jfreechart = ChartFactory.createXYLineChart("", "", "", createDatasetCS(issueTrendCnt, productList), PlotOrientation.VERTICAL, true, true, false);
			jfreechart.getLegend().setItemFont(new java.awt.Font("맑은 고딕",0,11));
			jfreechart.getLegend().setBorder(new BlockBorder(1,1,1,1,new Color(200,200,200)));
			//jfreechart.getLegend().setHeight(20);
			jfreechart.setBackgroundPaint(Color.WHITE); //차트 백그라운 색깔
			jfreechart.setBorderVisible(false); //차트 보더 보임 여부
		
			DateAxis dateaxis = new DateAxis();
			dateaxis.setTickMarkPosition(DateTickMarkPosition.START);
			dateaxis.setTickLabelFont(new Font("맑은 고딕", 0, 10));
			dateaxis.setDateFormatOverride(new SimpleDateFormat("MM/dd"));
			dateaxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 1));
			
			NumberAxis numberaxis = new NumberAxis();
			numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			
			XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer();
			xylineandshaperenderer.setSeriesPaint(0, gp0);
			xylineandshaperenderer.setSeriesPaint(1, gp1);
			xylineandshaperenderer.setSeriesPaint(2, gp2);
			xylineandshaperenderer.setSeriesPaint(3, gp3);
			xylineandshaperenderer.setSeriesPaint(4, gp4);
			xylineandshaperenderer.setSeriesPaint(5, gp5);
			
			
			xylineandshaperenderer.setSeriesStroke(0,new BasicStroke(2));
			xylineandshaperenderer.setSeriesStroke(1,new BasicStroke(2));
			xylineandshaperenderer.setSeriesStroke(2,new BasicStroke(2));
			xylineandshaperenderer.setSeriesStroke(3,new BasicStroke(2));
			xylineandshaperenderer.setSeriesStroke(4,new BasicStroke(2));
			xylineandshaperenderer.setSeriesStroke(5,new BasicStroke(2));
			
			
			
			
			XYPlot xyplot = (XYPlot)jfreechart.getPlot();
			xyplot.setDomainCrosshairVisible(true);
			xyplot.setRangeCrosshairVisible(true);
			xyplot.setDomainCrosshairPaint(Color.BLACK);
			xyplot.setRangeCrosshairPaint(Color.BLACK);
			xyplot.setDomainGridlinePaint(Color.BLACK);
			xyplot.setRangeGridlinePaint(Color.BLACK);
			
			xyplot.setRangeAxis(numberaxis);
			xyplot.setDomainAxis(dateaxis);
			xyplot.setRenderer(xylineandshaperenderer);
			xyplot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
			xyplot.setBackgroundPaint(Color.WHITE);
			
			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			
			
			
			if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+fullChartName), jfreechart, width, height, info);
			chartPath = urlPath+fullChartName;
			
		}catch(Exception e){
			
		}
		return chartPath;
	}
	
	public IntervalXYDataset createDatasetCS(ArrayList issueTrendCnt, ArrayList productList){
    	TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
    	TimeSeries timeseries = null;
    	int year = 0;
    	int month = 0;
    	int day = 0;
    	if(productList.size() > 0){
    		for(int i = 1; i < productList.size(); i++){
    			IssueCodeBean icb = (IssueCodeBean)productList.get(i);
    			timeseries = new TimeSeries(icb.getIc_name());
    			year = 0;
    			month = 0;
    			day = 0;
    			for(int j = 0; j < issueTrendCnt.size(); j++){
    				IssueDataBean idb = (IssueDataBean)issueTrendCnt.get(j);
    				year = Integer.parseInt(idb.getMd_date().substring(0, 4));
    				month = Integer.parseInt(idb.getMd_date().substring(4, 6));
    				day = Integer.parseInt(idb.getMd_date().substring(6, 8));
    				timeseries.add(new Day(day, month, year), idb.getCnt()[i-1]);
    			}
    			timeseriescollection.addSeries(timeseries);
    		}
    	}
    	return timeseriescollection;
    }
}
