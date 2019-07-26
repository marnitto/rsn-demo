package risk.JfreeChart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategorySeriesLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.jfree.util.Rotation;

import risk.DBconn.DBconn;
import risk.util.ConfigUtil;
import risk.util.DateUtil;
import risk.util.Log;

public class ChartMgr {
	
	private MySectionLabelGenerator msg = new MySectionLabelGenerator();
	
	public ArrayList getChartDataset(String sdate, String edate){
		ArrayList result = new ArrayList();
		HashMap bean = new HashMap();
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			
			SimpleDateFormat fommatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdate = fommatter.format(new SimpleDateFormat("yyyyMMddHHmmss").parse(sdate));
			edate = fommatter.format(new SimpleDateFormat("yyyyMMddHHmmss").parse(edate));
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			sb.append("SELECT A.IC_TYPE, A.IC_CODE, A.IC_NAME, IFNULL(B.CNT, 0) AS CNT\n");
			sb.append("FROM\n");
			sb.append("(\n");
			sb.append(" 	SELECT IC_TYPE, IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 8 AND IC_CODE <> 0\n");
			sb.append(") A LEFT OUTER JOIN\n");
			sb.append("(\n");
			sb.append("		SELECT IC_TYPE, IC_CODE, COUNT(IDC.ID_SEQ) AS CNT\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON MD_DATE BETWEEN '"+sdate+"' AND '"+edate+"'\n");
			sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		AND IDC.IC_TYPE = 8\n");
			sb.append("		GROUP BY IC_TYPE, IC_CODE\n");
			sb.append(") B\n");
			sb.append("ON A.IC_TYPE = B.IC_TYPE AND A.IC_CODE = B.IC_CODE\n");
			sb.append("ORDER BY A.IC_TYPE, A.IC_CODE\n");
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				bean = new HashMap();
				bean.put("IC_TYPE", rs.getString("IC_TYPE"));
				bean.put("IC_CODE", rs.getString("IC_CODE"));
				bean.put("IC_NAME", rs.getString("IC_NAME"));
				bean.put("CNT", rs.getString("CNT"));
				result.add(bean);
			}
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);			
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return result;
	}
	
	public ArrayList getChartDataset1(String sdate, String edate){
		ArrayList result = new ArrayList();
		HashMap bean = new HashMap();
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			
			SimpleDateFormat fommatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdate = fommatter.format(new SimpleDateFormat("yyyyMMddHHmmss").parse(sdate));
			edate = fommatter.format(new SimpleDateFormat("yyyyMMddHHmmss").parse(edate));
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			sb.append("SELECT A.IC_TYPE, A.IC_CODE, A.IC_NAME, IFNULL(SUM(B.P_CNT), 0) AS P_CNT, IFNULL(SUM(B.N_CNT), 0) AS N_CNT\n");
			sb.append("FROM\n");
			sb.append("(\n");
			sb.append(" 	SELECT IC_TYPE, IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 6 AND IC_CODE <> 0\n");
			sb.append(") A LEFT OUTER JOIN\n");
			sb.append("(\n");
			sb.append("		SELECT IDC.IC_TYPE, IDC.IC_CODE\n");
			sb.append("		, IFNULL(CASE IDC1.IC_CODE WHEN 1 THEN SUM(1) END, 0) AS P_CNT\n");
			sb.append("		, IFNULL(CASE IDC1.IC_CODE WHEN 2 THEN SUM(1) END, 0) AS N_CNT\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON MD_DATE BETWEEN '"+sdate+"' AND '"+edate+"'\n");
			sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		AND IDC.IC_TYPE = 6\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC1\n");
			sb.append("		ON ID.ID_SEQ = IDC1.ID_SEQ\n");
			sb.append("		AND IDC1.IC_TYPE = 9\n");
			sb.append("		GROUP BY IDC.IC_TYPE, IDC.IC_CODE, IDC1.IC_CODE\n");
			sb.append(") B\n");
			sb.append("ON A.IC_TYPE = B.IC_TYPE AND A.IC_CODE = B.IC_CODE\n");
			sb.append("GROUP BY A.IC_TYPE, A.IC_CODE, A.IC_NAME\n");
			sb.append("ORDER BY A.IC_TYPE, A.IC_CODE\n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				bean = new HashMap();
				bean.put("IC_TYPE", rs.getString("IC_TYPE"));
				bean.put("IC_CODE", rs.getString("IC_CODE"));
				bean.put("IC_NAME", rs.getString("IC_NAME"));
				bean.put("P_CNT", rs.getString("P_CNT"));
				bean.put("N_CNT", rs.getString("N_CNT"));
				result.add(bean);
			}
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);			
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return result;
	}
	
	public String getChartImg(ArrayList dataset, int width, int height){
		String result = "";
		DateUtil du = new DateUtil();
		ConfigUtil cu = new ConfigUtil();
		
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        for(int i = 0; i < dataset.size(); i++){
        	HashMap bean = (HashMap)dataset.get(i);
        	defaultcategorydataset.addValue(Integer.parseInt(String.valueOf(bean.get("CNT"))), "", String.valueOf(bean.get("IC_NAME")));
        }
		try{
			Paint apaint[] = new Paint[5];
	        apaint[0] = new GradientPaint(0.0F, 0.0F, Color.red, 0.0F, 0.0F, Color.white);
	        apaint[1] = new GradientPaint(0.0F, 0.0F, Color.green, 0.0F, 0.0F, Color.white);
	        apaint[2] = new GradientPaint(0.0F, 0.0F, Color.blue, 0.0F, 0.0F, Color.white);
	        apaint[3] = new GradientPaint(0.0F, 0.0F, Color.orange, 0.0F, 0.0F, Color.white);
	        apaint[4] = new GradientPaint(0.0F, 0.0F, Color.magenta, 0.0F, 0.0F, Color.white);
			
			JFreeChart jfreechart = ChartFactory.createBarChart("", null, "", defaultcategorydataset, PlotOrientation.VERTICAL, false, true, false);
			
	        CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
	        categoryplot.setNoDataMessage("NO DATA!");
	        categoryplot.setBackgroundPaint(null);
	        categoryplot.setInsets(new RectangleInsets(10D, 5D, 5D, 5D));
	        categoryplot.setOutlinePaint(Color.black);
	        categoryplot.setRangeGridlinePaint(Color.gray);
	        categoryplot.setRangeGridlineStroke(new BasicStroke(1.0F));
	        
	        CustomBarRenderer custombarrenderer = new CustomBarRenderer(apaint);
	        custombarrenderer.setBarPainter(new StandardBarPainter());
	        custombarrenderer.setDrawBarOutline(true);
	        custombarrenderer.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.CENTER_HORIZONTAL));
	        categoryplot.setRenderer(custombarrenderer);
	        
	        NumberAxis numberaxis = (NumberAxis)categoryplot.getRangeAxis();
	        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	        //numberaxis.setRange(0.0D, 800D);
	        numberaxis.setTickMarkPaint(Color.black);
	        
            CategoryAxis CategoryDomainAxis = (CategoryAxis)categoryplot.getDomainAxis();
            CategoryDomainAxis.setTickLabelFont(new Font("굴림",java.awt.Font.PLAIN ,12));
            CategoryDomainAxis.setLabelAngle(9.0D);
	        
	        String filePath = cu.getConfig("CHARTPATH");
	        String urlPath= cu.getConfig("CHARTURL");
	        String chartName = "chart"+du.getCurrentDate("yyyyMMddHHmmss")+".png";
	        
	        if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+chartName), jfreechart, width, height);
			result = urlPath+chartName;
	        
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public String getChartImg1(ArrayList dataset, int width, int height){
		String result = "";
		DateUtil du = new DateUtil();
		ConfigUtil cu = new ConfigUtil();
		
		DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
		for(int i = 0; i < dataset.size(); i++){
			HashMap bean = (HashMap)dataset.get(i);
			defaultpiedataset.setValue(String.valueOf(bean.get("IC_NAME")), Integer.parseInt(String.valueOf(bean.get("CNT"))));
		}
		try{
	        JFreeChart jfreechart = ChartFactory.createPieChart3D("", defaultpiedataset, true, true, false);
	        jfreechart.getLegend().setItemFont(new java.awt.Font("굴림", 0, 11));
	        jfreechart.setBackgroundPaint(Color.WHITE);
	        
	        PiePlot3D pieplot3d = (PiePlot3D)jfreechart.getPlot();
	        pieplot3d.setStartAngle(290D);
	        pieplot3d.setDirection(Rotation.CLOCKWISE);
	        pieplot3d.setForegroundAlpha(0.5F);
	        pieplot3d.setNoDataMessage("No data to display");
	        pieplot3d.setLabelGenerator(msg);
	        pieplot3d.setBackgroundPaint(Color.WHITE);
	        pieplot3d.setLabelLinkStyle(PieLabelLinkStyle.QUAD_CURVE);
	        pieplot3d.setLabelBackgroundPaint(Color.WHITE);
	        pieplot3d.setOutlinePaint(Color.WHITE);
	        pieplot3d.setLabelOutlinePaint(new Color(225,225,225));
	        pieplot3d.setLabelFont(new Font("굴림",java.awt.Font.PLAIN ,12));
			
			String filePath = cu.getConfig("CHARTPATH");
			String urlPath= cu.getConfig("CHARTURL");
			String chartName = "chart1"+du.getCurrentDate("yyyyMMddHHmmss")+".png";
			
			if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+chartName), jfreechart, width, height);
			result = urlPath+chartName;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	public String getChartImg2(ArrayList dataset, int width, int height){
		String result = "";
		DateUtil du = new DateUtil();
		ConfigUtil cu = new ConfigUtil();
		
		
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        for(int i = 0; i < dataset.size(); i++){
        	HashMap bean = (HashMap)dataset.get(i);
        	defaultcategorydataset.addValue(Integer.parseInt(String.valueOf(bean.get("P_CNT"))), "긍정", String.valueOf(bean.get("IC_NAME")));
        	defaultcategorydataset.addValue(Integer.parseInt(String.valueOf(bean.get("N_CNT"))), "부정", String.valueOf(bean.get("IC_NAME")));
        }
		try{
	        JFreeChart jfreechart = ChartFactory.createBarChart("", "", "", defaultcategorydataset, PlotOrientation.HORIZONTAL, true, true, false);
	        jfreechart.getLegend().setItemFont(new java.awt.Font("굴림", 0, 11));
	        
	        CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
	        categoryplot.setDomainGridlinesVisible(true);
	        categoryplot.setRangeGridlinesVisible(true);
	        categoryplot.setRangeGridlinePaint(Color.BLACK);
	        
	        categoryplot.setBackgroundPaint(Color.WHITE);
	        
	        NumberAxis numberaxis = (NumberAxis)categoryplot.getRangeAxis();
	        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	        
	        BarRenderer barrenderer = (BarRenderer)categoryplot.getRenderer();
	        barrenderer.setDrawBarOutline(false);
	        
	        GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, Color.blue, 0.0F, 0.0F, new Color(0, 0, 64));
	        GradientPaint gradientpaint1 = new GradientPaint(0.0F, 0.0F, Color.red, 0.0F, 0.0F, new Color(64, 0, 0));
	        
	        barrenderer.setSeriesPaint(0, gradientpaint);
	        barrenderer.setSeriesPaint(1, gradientpaint1);
	        
	        barrenderer.setLegendItemToolTipGenerator(new StandardCategorySeriesLabelGenerator("Tooltip: {0}"));
	        
	        CategoryAxis categoryaxis = categoryplot.getDomainAxis();
	        categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.52359877559829882D));
	        
            CategoryAxis CategoryDomainAxis = (CategoryAxis)categoryplot.getDomainAxis();
            CategoryDomainAxis.setTickLabelFont(new Font("굴림",java.awt.Font.PLAIN ,12));
            CategoryDomainAxis.setLabelAngle(9.0D);
	        
	        String filePath = cu.getConfig("CHARTPATH");
	        String urlPath= cu.getConfig("CHARTURL");
	        String chartName = "chart2"+du.getCurrentDate("yyyyMMddHHmmss")+".png";
	        
	        if(!new File(filePath).isDirectory()) new File(filePath).mkdirs();
			ChartUtilities.saveChartAsPNG(new File(filePath+chartName), jfreechart, width, height);
			result = urlPath+chartName;
	        
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
    public class CustomBarRenderer extends BarRenderer
    {

        private Paint colors[];

        public Paint getItemPaint(int i, int j)
        {
            return colors[j % colors.length];
        }

        public CustomBarRenderer(Paint apaint[])
        {
            colors = apaint;
        }
    }
}