package risk.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelView extends HttpServlet {
	
	XSSFWorkbook workbook = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ExcelView(){
		super();
	}

	//public void doGet(HttpServletRequest request, HttpServletResponse response){
	//	doPost(request, response);
	//}
	//
	//public void doPost(HttpServletRequest request, HttpServletResponse response){
	//	
	//	
	//	try {
	//		String sDate = request.getParameter("sDateFrom");
	//		String eDate = request.getParameter("sDateTo");
	//		String ir_stime = request.getParameter("ir_stime");
	//		String ir_etime = request.getParameter("ir_etime");
	//		String typeCode = request.getParameter("typeCode");
	//		String searchType = request.getParameter("searchType");
	//		String searchKey = URLDecoder.decode(request.getParameter("encodingSearchKey"),"UTF-8");
	//		String m_seq="";
	//		
	//		List list = issueMgr.issueExcelDownLoad(sDate, eDate, ir_stime, ir_etime, typeCode, searchType, searchKey, m_seq);
	//		
	//		String title[] = new String[]{"문서번호","일자","제목","게시판명","URL","출처","성향","ID","닉네임","방문자/팔로워/회원수","보도자료","보도자료명"};
	//		
	//		
	//	} catch (UnsupportedEncodingException e) {
	//		// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	}
	//	 
	//}

	public void doGet(Map<String, Object> dataMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		doPost(dataMap, request, response);
	}
	
	public void doPost(Map<String, Object> dataMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		buildExcelDocument(dataMap, request, response);
	}
	
	
	public void buildExcelDocument(Map<String, Object> dataMap, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			//logger.info("ExcelView start");
			System.out.println("ExcelView start");
			Calendar oCalendar = Calendar.getInstance( );
			String date =  oCalendar.get(Calendar.YEAR)+"."+(oCalendar.get(Calendar.MONTH) + 1)+"."+oCalendar.get(Calendar.DAY_OF_MONTH);
			
			String excelName = dataMap.get("name").toString();
			XSSFSheet worksheet = null;
	        workbook = new XSSFWorkbook();
	        
	        
			if("issue_data".equals(excelName)){
				
				excelName=URLEncoder.encode("이슈데이터","UTF-8");
				worksheet = workbook.createSheet(excelName);
				
				worksheet = setIssueDataExcel(worksheet, dataMap);
				
				System.out.println("엑셀 시트 완성");
			}

			  String userAgent = request.getHeader("User-Agent");
			  
			  if(userAgent.indexOf("MSIE") > -1){
				  excelName = URLEncoder.encode(excelName, "utf-8");
			  }else{
				  excelName = new String(excelName.getBytes("utf-8"), "iso-8859-1");
			  }
			
			 //response.setContentType("application/octet-stream");
			 //response.setHeader("Content-Disposition", "ATTachment; Filename="+date+".xls");
			 
			 //FileOutputStream out = getFileTarget("C:\\Users\\Alex\\Documents",date+".xlsx");
			 //workbook.write(out);
			 //out.close();

	}
	
	
	
	@SuppressWarnings("unused")
	private FileOutputStream getFileTarget(String path, String name)throws IOException{
		FileOutputStream out = new FileOutputStream(getFile(path, name));
		
		return out;
	}
	
	
	@SuppressWarnings("unused")
	private File getFile(String path, String name) throws IOException{
		return new File(path+File.separator+name);
	}
	
	/**
	 * 금감원 전용- 이슈데이터 엑셀 다운로드(영향력자 선정 관련)
	 * @param worksheet
	 * @param dataMap
	 * @return
	 */
	private XSSFSheet setIssueDataExcel(XSSFSheet worksheet, Map dataMap){
		
		XSSFRow row = null;
		row = worksheet.createRow(0);
		String title[] = (String[])dataMap.get("title");
		if(title.length > 0){
			for(int i=0; i < title.length; i++){
				row.createCell(i).setCellValue(title[i]);
			}
		}
        List<Map> list = (List<Map>)dataMap.get("list");
        if(list.size() > 0){
        	System.out.println("list.size() - "+list.size());
			for(int i=0; i < list.size(); i++){
				row = worksheet.createRow(i+1);
				System.out.print(list.get(i).get("ID_SEQ").toString()+","+list.get(i).get("MD_DATE").toString()+","+list.get(i).get("ID_TITLE").toString());
				System.out.print(list.get(i).get("MD_SITE_MENU").toString()+","+list.get(i).get("ID_URL").toString()+","+list.get(i).get("MD_SITE_NAME").toString());
				System.out.print(list.get(i).get("CODE9").toString()+","+list.get(i).get("USER_ID").toString()+","+list.get(i).get("USER_NICK").toString());
				System.out.print(list.get(i).get("BLOG_VISIT_COUNT").toString()+","+list.get(i).get("CAFE_NAME").toString()+","+list.get(i).get("CAFE_MEMBER_COUNT").toString());
				System.out.print(list.get(i).get("CODE10").toString()+","+list.get(i).get("P_NAME").toString());
				System.out.println("------------------------------------------------------------------------------------------------------------");
				row.createCell(0).setCellValue( list.get(i).get("ID_SEQ").toString() );
				row.createCell(1).setCellValue( list.get(i).get("MD_DATE").toString() );
				row.createCell(2).setCellValue( list.get(i).get("ID_TITLE").toString() );
				row.createCell(3).setCellValue( list.get(i).get("MD_SITE_MENU").toString() );
				row.createCell(4).setCellValue( list.get(i).get("ID_URL").toString() );
				row.createCell(5).setCellValue( list.get(i).get("MD_SITE_NAME").toString() );
				
				row.createCell(6).setCellValue( list.get(i).get("CODE9").toString() );
				row.createCell(7).setCellValue( list.get(i).get("USER_ID").toString() );
				row.createCell(8).setCellValue( list.get(i).get("USER_NICK").toString() );
				row.createCell(9).setCellValue( list.get(i).get("BLOG_VISIT_COUNT").toString() );
				row.createCell(10).setCellValue( list.get(i).get("CAFE_NAME").toString() );
				row.createCell(11).setCellValue( list.get(i).get("CAFE_MEMBER_COUNT").toString() );
				row.createCell(12).setCellValue( list.get(i).get("CODE10").toString() );
				row.createCell(13).setCellValue( list.get(i).get("P_NAME").toString() );
			}
		}
		
		return worksheet;
	}
	
	
	private HSSFRow setKeywordExcel(HSSFSheet worksheet, Map dataMap){
		HSSFRow row = null;
		row = worksheet.createRow(0);
		row.createCell(0).setCellValue("대분류");
        row.createCell(1).setCellValue("중분류");
        row.createCell(2).setCellValue("키워드");
		List<Map> list = (List<Map>)dataMap.get("list");
		if(list.size() > 0){
			for(int i=1; i < list.size()+1; i++){
				row = worksheet.createRow(i);
				System.out.println(list.get(i-1).get("KEYWORD1").toString());
				System.out.println(list.get(i-1).get("KEYWORD2").toString());
				System.out.println(list.get(i-1).get("KEYWORD3").toString());
				row.createCell(0).setCellValue( list.get(i-1).get("KEYWORD1").toString() );
				row.createCell(1).setCellValue( list.get(i-1).get("KEYWORD2").toString() );
				row.createCell(2).setCellValue( list.get(i-1).get("KEYWORD3").toString() );
			}
		}
		
		return row;
	}
}