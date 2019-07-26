package risk.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class POIExcelAdd {

	public void addExcel(String path, String fileName, String subject, String[] titleArray, List arData) {
		try {
			SXSSFWorkbook w_workbook = new SXSSFWorkbook(100); // xlsx 파일 생성
			Sheet w_sheet = w_workbook.createSheet();
			
			
			CellStyle headStyle = w_workbook.createCellStyle();
			headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);                     //스타일인스턴스의 속성 ?V팅           
			headStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());        //셀에 색깔 채우기   
			headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);   
			headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);              //테두리 설정   
			headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);   
			headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);   
			headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

			CellStyle bodyStyle = w_workbook.createCellStyle(); // 스타일
			bodyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);   
	        bodyStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);   
	        bodyStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);   
	        bodyStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);   
	        bodyStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);   
	        
			int widhtPixel = 3200;
			
			w_sheet.setColumnWidth(0, (widhtPixel * 3));
			w_sheet.setColumnWidth(1, (widhtPixel * 3));
			
			Row w_row = null;
			Cell w_cell = null;

			// 폰트
			Font font = w_workbook.createFont();
			font.setFontHeightInPoints((short) 10);
			bodyStyle.setFont(font);

			// 정렬
			bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
			bodyStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

			String[] arBean = null;
			String[] titleArr = titleArray;
			
			w_row = w_sheet.createRow(0);
			w_cell= w_row.createCell(0);
			w_cell.setCellValue(subject);

			w_row = w_sheet.createRow(1); // row 생성
			// cell 생성
			for (int j = 0; j < titleArr.length; j++) {
				w_cell = w_row.createCell(j);
				w_cell.setCellValue(titleArr[j]);
				w_cell.setCellStyle(headStyle);
			}
			for (int i = 0; i < arData.size(); i++) {
				arBean = new String[arData.size()];
				arBean = (String[]) arData.get(i);
				w_row = w_sheet.createRow(i + 2); // row 생성
				// cell 생성
				for (int j = 0; j < arBean.length; j++) {
					w_cell = w_row.createCell(j);
					w_cell.setCellValue(arBean[j]);
					w_cell.setCellStyle(bodyStyle);
				}
			}
			
			//셀병합
			//CellRangeAddress rangeAdd = new CellRangeAddress(0, 0, 0, 2); //제목 셀 병합
			//w_sheet.addMergedRegion( rangeAdd );
			
			FileOutputStream outStream;
			
			System.out.println("system excel path : "+path + fileName);

			try {
				if (!new File(path).isDirectory())
					new File(path).mkdirs();
				outStream = new FileOutputStream(path + fileName);
				
				w_workbook.write(outStream);
				outStream.close();
				w_workbook.dispose();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	public void addExcel(String path, String fileName, String subject, String[] titleArray, List arData1, List arData2, List arData3, List arData4) {
		try {
			SXSSFWorkbook w_workbook = new SXSSFWorkbook(100); // xlsx 파일 생성
			Sheet w_sheet = w_workbook.createSheet();
			
			
			CellStyle headStyle = w_workbook.createCellStyle();
			headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);                     //스타일인스턴스의 속성 ?V팅           
			headStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());        //셀에 색깔 채우기   
			headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);   
			headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);              //테두리 설정   
			headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);   
			headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);   
			headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

			CellStyle bodyStyle = w_workbook.createCellStyle(); // 스타일
			bodyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);   
	        bodyStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);   
	        bodyStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);   
	        bodyStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);   
	        bodyStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);   
	        
			int widhtPixel = 3200;
			
			w_sheet.setColumnWidth(0, (widhtPixel * 3));
			w_sheet.setColumnWidth(1, (widhtPixel * 3));
			
			Row w_row = null;
			Cell w_cell = null;

			// 폰트
			Font font = w_workbook.createFont();
			font.setFontHeightInPoints((short) 10);
			bodyStyle.setFont(font);

			// 정렬
			bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
			bodyStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

			String[] arBean1 = null;
			String[] arBean2 = null;
			String[] arBean3 = null;
			String[] arBean4 = null;
			
			String[] titleArr = titleArray;
			
			w_row = w_sheet.createRow(0);
			w_cell= w_row.createCell(0);
			w_cell.setCellValue(subject);

			w_row = w_sheet.createRow(1); // row 생성
			int ranking = 1;
			// cell 생성
			for (int j = 0; j < titleArr.length; j++) {
				w_cell = w_row.createCell(j);
				w_cell.setCellValue(titleArr[j]);
				w_cell.setCellStyle(headStyle);
			}
			for (int i = 0; i < 10; i++) {
				arBean1 = new String[2];
				arBean2 = new String[2];
				arBean3 = new String[2];
				arBean4 = new String[2];
				
				w_row = w_sheet.createRow(i + 2); // row 생성
				// cell 생성
				
				w_cell = w_row.createCell(0);
				w_cell.setCellValue(ranking);
				w_cell.setCellStyle(bodyStyle);
				
				if(arData1.size() > i){
					arBean1 = (String[]) arData1.get(i);
				}else{
					String [] temp =  new String[2];
					arBean1 = temp;
				}
				w_cell = w_row.createCell(1);
				w_cell.setCellValue(arBean1[0]);
				w_cell.setCellStyle(bodyStyle);
				w_cell = w_row.createCell(2);
				w_cell.setCellValue(arBean1[1]);
				w_cell.setCellStyle(bodyStyle);
				
				if(arData2.size() > i){
					arBean2 = (String[]) arData2.get(i);
					
					w_cell = w_row.createCell(3);
					w_cell.setCellValue(arBean2[0]);
					w_cell.setCellStyle(bodyStyle);
					w_cell = w_row.createCell(4);
					w_cell.setCellValue(arBean2[1]);
					w_cell.setCellStyle(bodyStyle);
					
				}else{
					String [] temp =  new String[2];
					arBean2 = temp;
				}
				
				
				if(arData3.size() > i){
					arBean3 = (String[]) arData3.get(i);
					
					w_cell = w_row.createCell(5);
					w_cell.setCellValue(arBean3[0]);
					w_cell.setCellStyle(bodyStyle);
					w_cell = w_row.createCell(6);
					w_cell.setCellValue(arBean3[1]);
					w_cell.setCellStyle(bodyStyle);
					
				}else{
					String [] temp =  new String[2];
					arBean3 = temp;
				}
				
				
				if(arData4.size() > i){
					arBean4 = (String[]) arData4.get(i);
					
					w_cell = w_row.createCell(7);
					w_cell.setCellValue(arBean4[0]);
					w_cell.setCellStyle(bodyStyle);
					w_cell = w_row.createCell(8);
					w_cell.setCellValue(arBean4[1]);
					w_cell.setCellStyle(bodyStyle);
				}else{
					String [] temp =  new String[2];
					arBean4 = temp;
				}
				
				
				ranking++;
			}
			
			//셀병합
			//CellRangeAddress rangeAdd = new CellRangeAddress(0, 0, 0, 2); //제목 셀 병합
			//w_sheet.addMergedRegion( rangeAdd );
			
			FileOutputStream outStream;

			try {
				System.out.println(path + fileName);
				if (!new File(path).isDirectory())
					new File(path).mkdirs();
				outStream = new FileOutputStream(path + fileName);

				w_workbook.write(outStream);
				outStream.close();
				w_workbook.dispose();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	public void DelExcelFile(String path, String fileName){
    	String reFileName = "";
    	File file = null;
    	
		file = new File(path + fileName);
		file.delete();
    }
	
	public void deleteExcelFile(String fileName){
		System.out.println("fileName : "+fileName);
		ConfigUtil cu = new ConfigUtil();
		String filePath = cu.getConfig("FILEPATH");
		String url = cu.getConfig("URL");
		fileName = fileName.trim();
		System.out.println("filePath : "+filePath);
		fileName = fileName.replaceAll("dashboard/file/", "");
		fileName = fileName.replaceAll(url, filePath);
		
		System.out.println("엑셀 파일 삭제 - "+ fileName);
    	File file = null;
    	
    	file = new File(fileName);
		if(file.delete()){
			System.out.println("엑셀 파일을 성공적으로 삭제하였습니다.");
		}else{
			System.out.println("엑셀 파일 삭제에 실패 했습니다.");
		}
    }

}
