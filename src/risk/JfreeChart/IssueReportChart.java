/**일일 보고서
 * @author 임승철 2009.09.22
 * @return chartPath 
 */
package risk.JfreeChart;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.VerticalAlignment;
import org.jfree.util.Rotation;

import risk.util.DateUtil;
import risk.util.Log;

public class IssueReportChart {
	
	private GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, new Color(55,124,195), 0.0f, 0.0f, new Color(55,124,195));
	private GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, new Color(220,157,67), 0.0f, 0.0f, new Color(220,157,67));
	private GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, new Color(153,184,45),     0.0f, 0.0f, new Color(153,184,45));
	private GradientPaint gp3 = new GradientPaint(0.0f, 0.0f, new Color(156,125,192),     0.0f, 0.0f, new Color(156,125,192));
	private GradientPaint gp4 = new GradientPaint(0.0f, 0.0f, new Color(45,163,193),     0.0f, 0.0f, new Color(45,163,193));
	private GradientPaint gp5 = new GradientPaint(0.0f, 0.0f, new Color(92,155,209),     0.0f, 0.0f, new Color(92,155,209));
	private GradientPaint gp6 = new GradientPaint(0.0f, 0.0f, new Color(146,209,92),     0.0f, 0.0f, new Color(146,209,92));
	private GradientPaint gp7 = new GradientPaint(0.0f, 0.0f, new Color(111,200,252),     0.0f, 0.0f, new Color(111,200,252));
	private GradientPaint gp8 = new GradientPaint(0.0f, 0.0f, new Color(252, 111, 111),     0.0f, 0.0f, new Color(252, 111, 111));
	private GradientPaint gp9 = new GradientPaint(0.0f, 0.0f, new Color(155,155,155),     0.0f, 0.0f, new Color(155,155,155));
	
	//인터페이스 구현
	private MySectionLabelGenerator msg = new MySectionLabelGenerator();
	
	//1.상대적 관심도 (Bar Chart)
	public String getDailyChartA(ArrayList chartData, String it_seq, String filePath, String urlPath)
	{
		String pullPath = "";
		String chartName = "";
		String lastSiteName = "";
		HashMap chartDataHm = new HashMap();
		Iterator it = null;
		JFreeChart chartA = null;		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();       
		String series1 = "누적평균";
        String series2 = "현주제";
        float cnt = 0;
		float maxCnt = 0;
       		
		try{			
			chartName = "PieChartA"+it_seq+".png";		
			if(chartData!=null)
			{
				it = chartData.iterator();				
				while(it.hasNext())
				{
					 chartDataHm = new HashMap();
					 chartDataHm = (HashMap)it.next();
					 //System.out.println((String)BData.get("1"));
					 //System.out.println((String)BData.get("2"));
					 cnt = Float.parseFloat((String)chartDataHm.get("1"));
					 maxCnt  = Float.parseFloat((String)chartDataHm.get("2"));
					 
					 System.out.println("cnt:"+cnt);
					 System.out.println("maxCnt:"+maxCnt);
					 dataset.addValue(new Double((String)chartDataHm.get("2")), series1, "");
					 dataset.addValue(new Double((String)chartDataHm.get("1")), series2, "");
				}
				
			}
			
			GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, new Color(220,40,0), 0.0f, 0.0f, new Color(220,40,0));
			
			
			//한글처리...
			StandardChartTheme chartTheme = new StandardChartTheme("맑은 고딕");
			chartTheme.setExtraLargeFont(new Font("맑은 고딕",0,11));
			chartTheme.setLargeFont(new Font("맑은 고딕",0,11));
			chartTheme.setRegularFont(new Font("맑은 고딕",0,11));
			ChartFactory.setChartTheme(chartTheme);
			
			chartA = ChartFactory.createBarChart(
	            "",       // chart title
	            "관심도",               // domain axis label
	            "",                  // range axis label
	            dataset,                  // data
	            PlotOrientation.VERTICAL, // orientation
	            true,                    // include legend
	            false,                     // tooltips?
	            false                     // URLs?
	        );
	
	        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
			
					
			
	        // set the background color for the chart...
			chartA.setBackgroundPaint(Color.white);
			
			// legend box
	        chartA.getLegend().setItemFont(new java.awt.Font("맑은 고딕",0,11));		        //chartA.getLegend().setHorizontalAlignment(HorizontalAlignment.RIGHT);
	        chartA.getLegend().setHorizontalAlignment(HorizontalAlignment.CENTER);
	        chartA.getLegend().setVerticalAlignment(VerticalAlignment.CENTER);
	        chartA.getLegend().setBorder(new BlockBorder(1,1,1,1,new Color(200,200,200)));
	
	        // get a reference to the plot for further customisation...
	        final CategoryPlot plotA = chartA.getCategoryPlot();		        
	        plotA.setBackgroundPaint(Color.white);
	        plotA.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
	        plotA.mapDatasetToRangeAxis(1, 1);
	        plotA.setRangeGridlinePaint(Color.lightGray);
	        plotA.setOutlinePaint(Color.white);
	      
	      	        
	        /*
	        final IntervalMarker target = new IntervalMarker(4.5, 7.5);
	        target.setLabel("Target Range");
	        target.setLabelFont(new Font("맑은 고딕",0,8));
	        target.setLabelAnchor(RectangleAnchor.LEFT);
	        target.setLabelTextAnchor(TextAnchor.CENTER_LEFT);
	        target.setPaint(new Color(222, 222, 255, 128));
	        plotA.addRangeMarker(target, Layer.BACKGROUND);
	        */
	        
	        // set the range axis to display integers only...
	        final NumberAxis rangeAxis = (NumberAxis) plotA.getRangeAxis();
	        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	
	        // disable bar outlines...
	        final BarRenderer renderer = (BarRenderer) plotA.getRenderer();
	        renderer.setDrawBarOutline(false);
	        renderer.setAutoPopulateSeriesPaint(false);	        
	        renderer.setShadowVisible(false);
	        renderer.setSeriesPaint(0, new Color(220,157,67),false);
	        renderer.setItemMargin(0);
	        renderer.setMaximumBarWidth(0.42);
	        
	        // set up gradient paints for series...
	      
	        renderer.setSeriesPaint(0, gp0);
	        renderer.setSeriesPaint(1, gp1);
	        
	        
	        //renderer.setLabelGenerator(new BarChartDemo7.LabelGenerator());
	        /*
	        renderer.setItemLabelsVisible(true);
	        final ItemLabelPosition p = new ItemLabelPosition(
	            ItemLabelAnchor.INSIDE12, TextAnchor.CENTER_RIGHT, 
	            TextAnchor.CENTER_RIGHT, -Math.PI / 2.0
	        );
	        renderer.setPositiveItemLabelPosition(p);
	
	        final ItemLabelPosition p2 = new ItemLabelPosition(
	            ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER_LEFT, 
	            TextAnchor.CENTER_LEFT, -Math.PI / 2.0
	        );
	        renderer.setPositiveItemLabelPositionFallback(p2);
	        */
	        final CategoryAxis domainAxis = plotA.getDomainAxis();
	        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
	        
	        ////차트생성
			//ChartRenderingInfo infoB = new ChartRenderingInfo(new StandardEntityCollection());
			if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+chartName),chartA,206,189);
			pullPath = urlPath+chartName;
			
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			
		}
       
		return pullPath;
	}
	
	//2.출처별 관심도(Pie Chart)
	public String getDailyChartB(ArrayList chartData, String it_seq, String filePath, String urlPath )
	{
		String pullPath = "";
		String chartName = "";
		String lastSiteName = "";
		HashMap chartDataHm = new HashMap();		
		double total = 0;
		int tempCnt = 0;						
		DefaultPieDataset dataset = new DefaultPieDataset();					
		Iterator it = null;		
		JFreeChart chartB = null;
		
		try{
			chartName = "PieChartB"+it_seq+".png";		
			if(chartData!=null)
			{
				it = chartData.iterator();
				while(it.hasNext())
				{
					chartDataHm = new HashMap();
					chartDataHm = (HashMap)it.next();
					 //System.out.println((String)BData.get("1"));
					 //System.out.println((String)BData.get("2"));
					 
					 //사이트그룹  병합 (정당/공공기관/기타 >> 기타그룹) 하드 코딩 ㅡㅡ;
					 if(!((String)chartDataHm.get("3")).equals("5") && !((String)chartDataHm.get("3")).equals("6")&& !((String)chartDataHm.get("3")).equals("7"))
					 {
					 	//datasetB.setValue((String)BData.get("1")+" "+(String)BData.get("2")+"건",new Double((String)BData.get("2")));
						 dataset.setValue((String)chartDataHm.get("1"),new Double((String)chartDataHm.get("2")));
					 }else{
						tempCnt += Integer.parseInt((String)chartDataHm.get("2"));
					 }
					 if(!it.hasNext())
					 {
					 	lastSiteName = (String)chartDataHm.get("1");
					 	dataset.setValue(lastSiteName,new Double(tempCnt));
					 }
					 total += Double.parseDouble((String)chartDataHm.get("2"));
				}							
			}
			//datasetB.sortByValues(SortOrder.DESCENDING);
			chartB = ChartFactory.createPieChart("",dataset,true,true,false);						
			////////////차트 셋팅	///////////	
			
			////제목						
			chartB.getTitle().setFont(new java.awt.Font("돋움",0,11));				
			////하단 라벨 설명 
			chartB.getLegend().setItemFont(new java.awt.Font("맑은 고딕",0,11));
			chartB.getLegend().setBorder(new BlockBorder(1,1,1,1,new Color(220,220,220)));
			////차트 백그라운 색깔
			chartB.setBackgroundPaint(Color.WHITE);
			chartB.setBorderPaint(new Color(225,225,225));
		    chartB.setBorderStroke(new BasicStroke(2));
		    
			
			////파이 속성 및 라벨
			final PiePlot plotB = (PiePlot)chartB.getPlot();			
			plotB.setLabelOutlinePaint(Color.WHITE);
			plotB.setShadowXOffset(0.0);
			plotB.setShadowYOffset(0.0);
			plotB.setBackgroundAlpha(0);
			//plotB.setSimpleLabels(true);
			plotB.setLabelLinkStyle(PieLabelLinkStyle.QUAD_CURVE);
			plotB.setLabelBackgroundPaint(Color.WHITE);
			plotB.setOutlinePaint(Color.WHITE);
			plotB.setLabelOutlinePaint(new Color(225,225,225));
			//plotB.setIgnoreZeroValues(true);					        
			//plotB.setSectionOutlinesVisible(true);	
			//plotB.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);
			plotB.setLabelFont(new java.awt.Font("LucidaBrightDemiBold",0,8));
					
			
			
				
			plotB.setSectionPaint(0,gp1);
			plotB.setSectionPaint(1,gp0);
			plotB.setSectionPaint(2,gp2);
			plotB.setSectionPaint(3,gp3);
			plotB.setSectionPaint(4,gp4);
			plotB.setSectionPaint(5,gp5);
			plotB.setSectionPaint(6,gp6);
			plotB.setSectionPaint(7,gp7);
			plotB.setSectionPaint(8,gp8);
			plotB.setSectionPaint(9,gp9);
			
			
			//인터페이스 구현 클래스
			plotB.setLabelGenerator(msg);
			
			////차트생성
			//ChartRenderingInfo infoB = new ChartRenderingInfo(new StandardEntityCollection());
			if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+chartName),chartB,206,189);
			pullPath = urlPath+chartName;			
			
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			
		}
		
		return pullPath;
	}
	
	//3.우호도 (Pie Chart)
	public String getDailyChartC(ArrayList chartData, String it_seq, String filePath, String urlPath)
	{
		String pullPath = "";
		String chartName = "";
		HashMap chartDataHm = new HashMap();
		JFreeChart chartC = null;		
		DefaultPieDataset dataset = new DefaultPieDataset();		
		Iterator it = null;
		
		try{
			
			it = null;
			chartName = "PieChartC"+it_seq+".png";
		
			if(chartData!=null)
			{
				it = chartData.iterator();
				while(it.hasNext())
				{
					 chartDataHm = new HashMap();
					 chartDataHm = (HashMap)it.next();
					 //System.out.println((String)CData.get("1"));
					 //System.out.println((String)CData.get("2"));
					 
					 //datasetC.setValue((String)CData.get("1")+" "+(String)CData.get("2")+"건",new Double((String)CData.get("2")));
					 dataset.setValue((String)chartDataHm.get("1"),new Double((String)chartDataHm.get("2")));
					 
				}
			}
			//datasetC.sortByValues(SortOrder.DESCENDING);
			chartC = ChartFactory.createPieChart("",dataset,true,false,false);						
			////////////차트 셋팅	///////////
			////제목
			chartC.getTitle().setFont(new java.awt.Font("돋움",0,11));
			////Legend Font
			chartC.getLegend().setItemFont(new java.awt.Font("맑은 고딕",0,11));
			chartC.getLegend().setBorder(new BlockBorder(1,1,1,1,new Color(220,220,220)));
			////차트 백그라운 색깔
			chartC.setBackgroundPaint(Color.WHITE);
			chartC.setBorderPaint(new Color(225,225,225));
		    chartC.setBorderStroke(new BasicStroke(2));
		    
			////파이 속성 및 라벨
			PiePlot plotC = (PiePlot)chartC.getPlot();
			plotC.setLabelOutlinePaint(Color.WHITE);						
			plotC.setShadowXOffset(0.0);
			plotC.setShadowYOffset(0.0);
			plotC.setBackgroundAlpha(0);
			//plotC.setSimpleLabels(true);
			plotC.setLabelLinkStyle(PieLabelLinkStyle.QUAD_CURVE);
			plotC.setLabelBackgroundPaint(Color.WHITE);
			plotC.setOutlinePaint(Color.WHITE);
			plotC.setLabelOutlinePaint(new Color(225,225,225));
			
			//plotC.setIgnoreZeroValues(true);
			//plotC.setSectionOutlinesVisible(true);
			//plotC.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);						
			plotC.setLabelFont(new java.awt.Font("돋움",0,11));
			plotC.setSectionPaint(0,gp1);
			plotC.setSectionPaint(1,gp0);
			plotC.setSectionPaint(2,gp2);
			plotC.setSectionPaint(3,gp3);
			plotC.setSectionPaint(4,gp4);
			plotC.setSectionPaint(5,gp5);
			plotC.setSectionPaint(6,gp6);
			plotC.setSectionPaint(7,gp7);
			plotC.setSectionPaint(8,gp8);
			plotC.setSectionPaint(9,gp9);
			
			
			//인터페이스 구현 클래스
			plotC.setLabelGenerator(msg);
									
			////차트생성
			//ChartRenderingInfo infoC = new ChartRenderingInfo(new StandardEntityCollection());
			if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+chartName),chartC,206,189);
			pullPath = urlPath+chartName;
		
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			
		}
		
		return pullPath;
	}
	
	//4.전체 % (Bar Chart)
	public String getDailyBarChart(String[] Count, String filePath, String urlPath)
	{
		String pullPath = "";
		String chartName = "";
		JFreeChart chartC = null;		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		DateUtil du = new DateUtil();
		String[] title = "기타사항,당사언급,주요이슈".split(",");
		try{
			
			chartName = "BarChart1"+du.getCurrentDate("yyyyMMddHHmmssss")+".png";
			
			for (int i = 0; i < Count.length; i++) {
				dataset.addValue(Double.parseDouble(Count[i]), title[i], "");
			}
			
			
			//datasetC.sortByValues(SortOrder.DESCENDING);
			chartC = ChartFactory.createStackedBarChart3D("","", "", dataset, PlotOrientation.HORIZONTAL, true, false, false);
			//chartC = ChartFactory.createPieChart("",dataset,true,false,false);						
			////////////차트 셋팅	///////////
			////제목
			chartC.getTitle().setFont(new java.awt.Font("돋움",0,12));
			////Legend Font
			chartC.getLegend().setItemFont(new java.awt.Font("맑은 고딕",0,12));
			chartC.getLegend().setBorder(new BlockBorder(1,1,1,1,new Color(220,220,220)));
			////차트 백그라운 색깔
			//chartC.setBackgroundPaint(Color.WHITE);
			//chartC.setBorderPaint(new Color(225,225,225));
			//chartC.setBorderStroke(new BasicStroke(2));
			
			chartC.setTitle("");
			 
			 //ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			 CategoryPlot plot = chartC.getCategoryPlot(); 
			 
			 plot.setBackgroundAlpha(1);
			 plot.setForegroundAlpha(1f);
			 
			 //plot.setOutlinePaint(Color.BLACK);
			 
			 BarRenderer renderer = (BarRenderer) plot.getRenderer();
			 //renderer.setOutlinePaint(Color.BLACK);
			 //renderer.setOutlineStroke(new BasicStroke(2));
			 // 그래프 라인 색 속성
			 //빨강 : 229,56,24
			 //파랑 : 11,172,254
			 //노랑 : 251,253,77
			 //연두 : 168,233,112
			 //보라 : 209,111,255
			 GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, new Color(111,200,252), 0.0f, 0.0f, new Color(111,200,252));
			 GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, new Color(252, 111, 111), 0.0f, 0.0f, new Color(252, 111, 111));
			 GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, new Color(147,252,123),     0.0f, 0.0f, new Color(147,252,123));
			 GradientPaint gp3 = new GradientPaint(0.0f, 0.0f, new Color(251,253,77),     0.0f, 0.0f, new Color(251,253,77));
			 GradientPaint gp4 = new GradientPaint(0.0f, 0.0f, new Color(209,111,255),     0.0f, 0.0f, new Color(209,111,255));
			 GradientPaint gp5 = new GradientPaint(0.0f, 0.0f, Color.lightGray,     0.0f, 0.0f, new Color(230, 93, 246));
			 renderer.setSeriesPaint(0, gp0);
			 renderer.setSeriesPaint(1, gp1);
			 renderer.setSeriesPaint(2, gp2);
			 renderer.setSeriesPaint(3, gp3);
			 renderer.setSeriesPaint(4, gp4);
			 renderer.setSeriesPaint(5, gp5);
			
			
			////차트생성
			//ChartRenderingInfo infoC = new ChartRenderingInfo(new StandardEntityCollection());
			if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+chartName),chartC,620,100);
			pullPath = urlPath+chartName;
			
			chartC = null;
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			
		}
		
		return pullPath;
	}
	
	//3.우호도 (Pie Chart)
	public String getDailyPieChart(String[] chartData, String filePath, String urlPath, String chtName, String chtTitle, String legend)
	{
		String pullPath = "";
		String chartName = "";
		JFreeChart chartC = null;		
		DefaultPieDataset dataset = new DefaultPieDataset();		
		DateUtil du = new DateUtil();
		String[] title = legend.split(",");
		try{
			
			chartName = chtName+du.getCurrentDate("yyyyMMddHHmmssss")+".png";
			for (int i = 0; i < chartData.length; i++) {
				dataset.setValue(title[i],Double.parseDouble(chartData[i]));
			}
					 
			//datasetC.sortByValues(SortOrder.DESCENDING);
			chartC = ChartFactory.createPieChart3D("",dataset,true,true,false);						
			////////////차트 셋팅	///////////
			////제목
			chartC.setTitle(chtTitle);
			chartC.getTitle().setFont(new java.awt.Font("맑은 고딕",0,12));
			////Legend Font
			chartC.getLegend().setItemFont(new java.awt.Font("맑은 고딕",0,12));
			chartC.getLegend().setBorder(new BlockBorder(1,1,1,1,new Color(220,220,220)));
			////차트 백그라운 색깔
			chartC.setBackgroundPaint(Color.WHITE);
			chartC.setBorderPaint(new Color(225,225,225));
		    chartC.setBorderStroke(new BasicStroke(2));
		    
			////파이 속성 및 라벨
		    PiePlot3D  plotC = (PiePlot3D)chartC.getPlot();
		    plotC.setStartAngle(220);
		    plotC.setDirection(Rotation.CLOCKWISE);
	        
			plotC.setLabelOutlinePaint(Color.WHITE);						
			plotC.setShadowXOffset(0.0);
			plotC.setShadowYOffset(0.0);
			plotC.setBackgroundAlpha(0);
			//plotC.setSimpleLabels(true);
			plotC.setLabelLinkStyle(PieLabelLinkStyle.QUAD_CURVE);
			plotC.setLabelBackgroundPaint(Color.WHITE);
			plotC.setOutlinePaint(Color.WHITE);
			plotC.setLabelOutlinePaint(new Color(225,225,225));
			plotC.setForegroundAlpha(1f);
			plotC.setSimpleLabels(true);
			
			//plotC.setIgnoreZeroValues(true);
			//plotC.setSectionOutlinesVisible(true);
			//plotC.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);						
			plotC.setLabelFont(new java.awt.Font("돋움",0,12));
			plotC.setSectionPaint(0,gp7);
			plotC.setSectionPaint(1,gp8);
			plotC.setSectionPaint(2,gp9);
			plotC.setSectionPaint(3,gp3);
			plotC.setSectionPaint(4,gp4);
			plotC.setSectionPaint(5,gp5);
			plotC.setSectionPaint(6,gp6);
			plotC.setSectionPaint(7,gp1);
			plotC.setSectionPaint(8,gp0);
			plotC.setSectionPaint(9,gp2);
			
			
			//인터페이스 구현 클래스
			plotC.setLabelGenerator(msg);
									
			////차트생성
			//ChartRenderingInfo infoC = new ChartRenderingInfo(new StandardEntityCollection());
			if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+chartName),chartC,200,189);
			pullPath = urlPath+chartName;
			chartC = null;
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			
		}
		
		return pullPath;
	}
	
	//3.우호도 (Pie Chart)
	public String getDailyPieChart2(String[] chartData, String filePath, String urlPath, String chtName, String chtTitle, String legend)
	{
		String pullPath = "";
		String chartName = "";
		JFreeChart chartC = null;		
		DefaultPieDataset dataset = new DefaultPieDataset();		
		DateUtil du = new DateUtil();
		String[] title = legend.split(",");
		try{
			
			chartName = chtName+du.getCurrentDate("yyyyMMddHHmmssss")+".png";
			for (int i = 0; i < chartData.length; i++) {
				dataset.setValue(title[i],Double.parseDouble(chartData[i]));
			}
			
			//datasetC.sortByValues(SortOrder.DESCENDING);
			chartC = ChartFactory.createPieChart("",dataset,true,false,false);						
			////////////차트 셋팅	///////////
			////제목
			chartC.setTitle(chtTitle);
			chartC.getTitle().setFont(new java.awt.Font("맑은 고딕",0,12));
			////Legend Font
			chartC.getLegend().setItemFont(new java.awt.Font("맑은 고딕",0,11));
			chartC.getLegend().setBorder(new BlockBorder(1,1,1,1,new Color(220,220,220)));
			chartC.getLegend().setVisible(false);
			
			////차트 백그라운 색깔
			chartC.setBackgroundPaint(Color.WHITE);
			chartC.setBorderPaint(new Color(225,225,225));
			chartC.setBorderStroke(new BasicStroke(2));
			
			////파이 속성 및 라벨
			PiePlot plotC = (PiePlot)chartC.getPlot();
			
			plotC.setLabelOutlinePaint(Color.WHITE);						
			plotC.setShadowXOffset(0.0);
			plotC.setShadowYOffset(0.0);
			plotC.setBackgroundAlpha(0);

			//plotC.setLabelLinkStyle(PieLabelLinkStyle.QUAD_CURVE);
			plotC.setLabelBackgroundPaint(Color.WHITE);
			plotC.setOutlinePaint(Color.WHITE);
			plotC.setLabelOutlinePaint(new Color(225,225,225));
			plotC.setSimpleLabels(true);
			//plotC.setLabelGenerator(null);
			
			//plotC.setIgnoreZeroValues(true);
			//plotC.setSectionOutlinesVisible(true);
			//plotC.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);						
			plotC.setLabelFont(new java.awt.Font("돋움",0,11));
			plotC.setSectionPaint(0,gp7);
			plotC.setSectionPaint(1,gp8);
			plotC.setSectionPaint(2,gp9);
			plotC.setSectionPaint(3,gp3);
			plotC.setSectionPaint(4,gp4);
			plotC.setSectionPaint(5,gp5);
			plotC.setSectionPaint(6,gp6);
			plotC.setSectionPaint(7,gp1);
			plotC.setSectionPaint(8,gp0);
			plotC.setSectionPaint(9,gp2);
			
			//인터페이스 구현 클래스
			plotC.setLabelGenerator(msg);
			
			////차트생성
			//ChartRenderingInfo infoC = new ChartRenderingInfo(new StandardEntityCollection());
			if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+chartName),chartC,100,100);
			pullPath = urlPath+chartName;
			chartC = null;
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			
		}
		
		return pullPath;
	}
}
