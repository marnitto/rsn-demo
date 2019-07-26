package risk.relationmap;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import risk.DBconn.DBconn;

public class relationMap {
	
	static Image image = null;
	static Image image1 = null;
	
	public static void main(String[] args) throws IOException {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("relationMap");
		shell.setLayout(new FillLayout());
		
		final int shellWidth = 559;
		final int shellHeight = 420;
		
		shell.setSize(shellWidth, shellHeight);
		final Rectangle rect = shell.getClientArea();

		URL url = null;
		Canvas canvas = new Canvas(shell, SWT.NONE);
		try {
			//url = new URL("http://ssdi2.devel.com/images/report/issuereport/bg_012.jpg");
			url = new URL("http://cuffee.realsn.co.kr/images/report/issuereport/bg_012.jpg");
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		}

		final ArrayList tagGroupList = getTagGroup(args[0], args[1]);
		final ArrayList tagList = getTagList(args[0], args[1]);
		
		canvas.setBackgroundImage(new Image(display, url.openStream()));
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				
				for(int i = 0; i < tagGroupList.size(); i++){
					HashMap bean = (HashMap)tagGroupList.get(i);
					String tempName = "";
					String tempCnt = "";	//작은원 크기(즉 카운트 비례)
					for(int j = 0; j < tagList.size(); j++){
						HashMap bean1 = (HashMap)tagList.get(j);
						if(String.valueOf(bean.get("ITG_SEQ")).equals(String.valueOf(bean1.get("ITG_SEQ")))){
							if(tempName.equals("")){
								tempName = String.valueOf(bean1.get("ITC_NAME"));
								tempCnt = String.valueOf(bean1.get("CNT"));
							}else{
								tempName += ","+String.valueOf(bean1.get("ITC_NAME"));
								tempCnt += ","+String.valueOf(bean1.get("CNT"));
							}
						}
					}
					if(!tempName.equals("")){
						drawChart(display, e, shell, shellWidth, shellHeight, String.valueOf(bean.get("ITG_NAME")), tempName.split(","), i, tempCnt.split(","));
					}
				}
				
			}
		});
		canvas.redraw();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
	
	public static void drawChart(Display display, PaintEvent e, Shell shell, int shellWidth, int shellHeight, String str, String[] eleStr, int index, String[] cnt){
		//실제 메인Circle이미지 지름
		int circleSize = 85;
		//하위Circle이미지 지름
		int childCircleSize = 40;
		
		int left = (shellWidth/4)-(circleSize/2);
		int top = (shellHeight/4)-(circleSize/2);
		
		if(index == 0){
			left = (shellWidth/4)-(circleSize/2);
			top = (shellHeight/4)-(circleSize/2);
		}else if(index == 1){
			left = ((shellWidth/4)+(shellWidth/4)+(shellWidth/4))-(circleSize/2);
			top = (shellHeight/4)-(circleSize/2);
		}else if(index == 2){
			left = (shellWidth/4)-(circleSize/2);
			top = ((shellHeight/4)+(shellHeight/4)+(shellHeight/4))-(circleSize/2);
		}else if(index == 3){
			left = ((shellWidth/4)+(shellWidth/4)+(shellWidth/4))-(circleSize/2);
			top = ((shellHeight/4)+(shellHeight/4)+(shellHeight/4))-(circleSize/2);
		}
		
		

		//라인길이
		int linewidth = 1;
		int[] lineWidthArr = {linewidth, linewidth, linewidth, linewidth, linewidth, linewidth, linewidth, linewidth};
//		int[] lineWidthArr = {45, 30, 70, 15, 20, 35, 10, 15};

		//하위Circle 크기++
		//int[] circleSizeArr = {7, 14, 20, 10, 38, 20, 29, 21};
		
		//라인굵기
//		int[] lineBold = {4, 7, 3, 4, 1, 6, 7, 12};
		int[] lineBold = {2, 2, 2, 2, 2, 2, 2, 2};
		
		String[] cirCleXY = null;
		
		//중심라인그리기
		cirCleXY = drawLine(e, left, top, circleSize, lineWidthArr, lineBold, eleStr.length);
		
		//중심 원 그리기
		try {
			URL url1 = new URL("http://cuffee.realsn.co.kr/images/report/issuereport/circle_blue.png");
			URL url2 = new URL("http://cuffee.realsn.co.kr/images/report/issuereport/circle_gray.png");
			URL url3 = new URL("http://cuffee.realsn.co.kr/images/report/issuereport/circle_green.png");
			URL url4 = new URL("http://cuffee.realsn.co.kr/images/report/issuereport/circle_orange.png");
			URL url5 = new URL("http://cuffee.realsn.co.kr/images/report/issuereport/circle_purple.png");
//			URL url1 = new URL("http://ssdi2.devel.com/images/report/issuereport/circle_blue.png");
//			URL url2 = new URL("http://ssdi2.devel.com/images/report/issuereport/circle_gray.png");
//			URL url3 = new URL("http://ssdi2.devel.com/images/report/issuereport/circle_green.png");
//			URL url4 = new URL("http://ssdi2.devel.com/images/report/issuereport/circle_orange.png");
//			URL url5 = new URL("http://ssdi2.devel.com/images/report/issuereport/circle_purple.png");
			if(index == 0){
				image = new Image(display, url1.openStream());
			}else if(index == 1){
				image = new Image(display, url3.openStream());
			}else if(index == 2){
				image = new Image(display, url4.openStream());
			}else if(index == 3){
				image = new Image(display, url5.openStream());
			}
			
			image1 = new Image(display, url2.openStream());
			if(circleSize == 0){
				e.gc.drawImage(image, left, top);
			}else{
				e.gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, left, top, circleSize, circleSize);
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//중심원 텍스트 그리기
		Font font = new Font(shell.getDisplay(), new FontData("Helvetica", (circleSize / 9), SWT.BOLD));
        e.gc.setFont(font);
        e.gc.drawText(str, left + (circleSize / str.length()) - 5, top + (circleSize / 2)-15, true);

        
		//라인 끝 원 그리기
		for(int i = 0; i < cirCleXY.length; i++){
			int eleCircleSize = childCircleSize + (Integer.parseInt(cnt[i])+10);
			int eleLeft = Integer.parseInt(cirCleXY[i].split(",")[0]) - (eleCircleSize / 2);
			int eleTop = Integer.parseInt(cirCleXY[i].split(",")[1]) - (eleCircleSize / 2);
			
//			if(i == 7){
//				drawCircleSmall(Integer.parseInt(cirCleXY[i].split(",")[0]), Integer.parseInt(cirCleXY[i].split(",")[1]), e, 100, 100);
//				
//			}else if(i == 2){
//				drawCircleSmall(Integer.parseInt(cirCleXY[i].split(",")[0]), Integer.parseInt(cirCleXY[i].split(",")[1]), e, 100, 100);
//				
//			}else{
//				eleLeft = Integer.parseInt(cirCleXY[i].split(",")[0]) - (eleCircleSize / 2);
//				eleTop = Integer.parseInt(cirCleXY[i].split(",")[1]) - (eleCircleSize / 2);
//				e.gc.drawImage(image1, 0, 0, image1.getBounds().width, image1.getBounds().height, eleLeft, eleTop, eleCircleSize, eleCircleSize);
//			}
			eleLeft = Integer.parseInt(cirCleXY[i].split(",")[0]) - (eleCircleSize / 2);
			eleTop = Integer.parseInt(cirCleXY[i].split(",")[1]) - (eleCircleSize / 2);
			e.gc.drawImage(image1, 0, 0, image1.getBounds().width, image1.getBounds().height, eleLeft, eleTop, eleCircleSize, eleCircleSize);
			
			//라인끝 텍스트 그리기
			font = new Font(shell.getDisplay(), new FontData("Helvetica", (eleCircleSize/5), SWT.BOLD));
	        e.gc.setFont(font);
	        e.gc.drawText(eleStr[i], eleLeft + (eleCircleSize/5), eleTop + (eleCircleSize / 2)-10, true);	
		}
	}
	
	public static String[] drawLine(PaintEvent e, int left, int top, int circleSize, int[] lineWidth, int[] lineBold, int len){
		String[] result = new String[len];
		int leftCenter = 0;
		int topCenter = 0;
		leftCenter = left + (circleSize / 2);
		topCenter = top + (circleSize / 2);
		
		int objLeft = 0;
		int objTop = 0;
		
		int min = -20;
		int max = 20;
		int side = 0;
		int center = 70;
		for(int i = 0; i < len; i++){
			side = (int)(Math.random() * (max - min + 1)) + min;
			if(i == 0){
				objLeft = leftCenter + side;
				objTop = topCenter - center - lineWidth[i];
			}else if(i == 1){
				objLeft = leftCenter + center + lineWidth[i] + side;
				objTop = topCenter - center - lineWidth[i];
			}else if(i == 2){
				objLeft = leftCenter + center + lineWidth[i];
				objTop = topCenter + side;
			}else if(i == 3){
				objLeft = leftCenter + center + lineWidth[i] + side;
				objTop = topCenter + center + lineWidth[i];
			}else if(i == 4){
				objLeft = leftCenter + side;
				objTop = topCenter + center + lineWidth[i];
			}else if(i == 5){
				objLeft = leftCenter - center - lineWidth[i] + side;
				objTop = topCenter + center + lineWidth[i];
			}else if(i == 6){
				objLeft = leftCenter - center - lineWidth[i];
				objTop = topCenter + side;
			}else if(i == 7){
				objLeft = leftCenter - center - lineWidth[i] + side;
				objTop = topCenter - center - lineWidth[i];
			}
			e.gc.setLineWidth(lineBold[i]);
			e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_GRAY));
			result[i] = objLeft+","+objTop;
			e.gc.drawLine(leftCenter, topCenter, objLeft, objTop);	//12
			e.gc.setLineWidth(1);
			e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_WHITE));
		}
		return result;
	}
	
	public static void drawCircleSmall(int eleLeft, int eleTop, PaintEvent e, int mainCircleSize, int eleCircleSize){
		
		eleLeft = eleLeft - (mainCircleSize / 2);
		eleTop = eleTop - (mainCircleSize / 2);
		
		//중심원 라인
		String[] cirCleXY1 = null;
		int[] lineWidthArr1 = {5, 75, 30, 15, 40, 95, 10, 50};
		int[] lineBold1 = {4, 5, 3, 7, 8, 6, 1, 10};
		cirCleXY1 = drawLine(e, eleLeft, eleTop, eleCircleSize, lineWidthArr1, lineBold1, 8);
		
		//중심 원
		e.gc.drawImage(image, eleLeft, eleTop);
		
		for(int j = 0; j < cirCleXY1.length; j++){
			int eleCircleSize1 = 50;
			int eleLeft1 = Integer.parseInt(cirCleXY1[j].split(",")[0]) - (eleCircleSize1 / 2);
			int eleTop1 = Integer.parseInt(cirCleXY1[j].split(",")[1]) - (eleCircleSize1 / 2);
			e.gc.drawImage(image1, 0, 0, image1.getBounds().width, image1.getBounds().height, eleLeft1, eleTop1, eleCircleSize1, eleCircleSize1);
		}
	}
	
	public static ArrayList getTagGroup(String sdate, String edate){
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
			dbconn.getSubDirectConnection();
			
			sb = new StringBuffer();
			
			sb.append("SELECT ITC.ITG_SEQ, ITG.ITG_NAME\n");
			sb.append("FROM ISSUE_DATA ID\n");
			sb.append("INNER JOIN ISSUE_TAG_DATA ITD\n");
			sb.append("ON ID.MD_DATE BETWEEN '"+sdate+"' AND '"+edate+"'\n");
			sb.append("AND ID.ID_SEQ = ITD.ID_SEQ\n");
			sb.append("INNER JOIN ISSUE_TAG_CODE ITC\n");
			sb.append("ON ITD.ITC_SEQ = ITC.ITC_SEQ\n");
			sb.append("INNER JOIN ISSUE_TAG_GROUP ITG\n");
			sb.append("ON ITC.ITG_SEQ = ITG.ITG_SEQ\n");
			sb.append("GROUP BY ITC.ITG_SEQ, ITG.ITG_NAME\n");
			sb.append("ORDER BY ITC.ITG_SEQ\n");
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				bean = new HashMap();
				bean.put("ITG_SEQ", rs.getString("ITG_SEQ"));
				bean.put("ITG_NAME", rs.getString("ITG_NAME"));
				result.add(bean);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return result;
	}
	
	public static ArrayList getTagList(String sdate, String edate){
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
			dbconn.getSubDirectConnection();
			
			sb = new StringBuffer();
			
			sb.append("SELECT ITC.ITG_SEQ, ITG.ITG_NAME, ITD.ITC_SEQ, ITC.ITC_NAME, COUNT(*) AS CNT\n");
			sb.append("FROM ISSUE_DATA ID\n");
			sb.append("INNER JOIN ISSUE_TAG_DATA ITD\n");
			sb.append("ON ID.MD_DATE BETWEEN '"+sdate+"' AND '"+edate+"'\n");
			sb.append("AND ID.ID_SEQ = ITD.ID_SEQ\n");
			sb.append("INNER JOIN ISSUE_TAG_CODE ITC\n");
			sb.append("ON ITD.ITC_SEQ = ITC.ITC_SEQ\n");
			sb.append("INNER JOIN ISSUE_TAG_GROUP ITG\n");
			sb.append("ON ITC.ITG_SEQ = ITG.ITG_SEQ\n");
			sb.append("GROUP BY ITC.ITG_SEQ, ITG.ITG_NAME, ITC_SEQ, ITC.ITC_NAME\n");
			//sb.append("ORDER BY ITC.ITG_SEQ, ITC_SEQ\n");
			sb.append("ORDER BY ITC.ITG_SEQ, CNT DESC\n");
			sb.append("LIMIT 8\n");
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				bean = new HashMap();
				bean.put("ITG_SEQ", rs.getString("ITG_SEQ"));
				bean.put("ITG_NAME", rs.getString("ITG_NAME"));
				bean.put("ITC_SEQ", rs.getString("ITC_SEQ"));
				bean.put("ITC_NAME", rs.getString("ITC_NAME"));
				bean.put("CNT", rs.getString("CNT"));
				result.add(bean);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return result;
	}
}