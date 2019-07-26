<%@page import="risk.dashboard.search.SearchMgr"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="risk.json.JSONObject"%>
<%@page import="risk.json.JSONArray"%>
<%@ page import = "java.util.*" %>
<%@ page import = "risk.util.*" %>
<%@page import="risk.util.ParseRequest"%>
<%@page import="risk.util.DateUtil"%>
<%@include file="../../riskv3/inc/sessioncheck.jsp" %>

<%

ParseRequest pr = new ParseRequest(request);
pr.printParams();
DateUtil du = new DateUtil();
SearchMgr searchMgr = new SearchMgr();

//엑셀용 기본 세팅
	StringUtil su = new StringUtil();
	ConfigUtil cu = new ConfigUtil();
	POIExcelAdd poiAdd = new POIExcelAdd();
	String filePath = cu.getConfig("FILEPATH");
	String excelPath = filePath + "excel/" + SS_M_NO + "/";
	String yyyy = du.getDate("yyyy");
	String MMdd = du.getDate("MMdd");
	String nowTime = du.getDate("HHmmss");
	String fileName = pr.getString("fileName", "daeguExcel" + yyyy + MMdd + nowTime + ".xls");
	String subject = "";
	String[] titleArr = null;
	String tmp[] = null;
	List excelList = new ArrayList();
/**************************/

String section = pr.getString("section");
String i_sdate = pr.getString("i_sdate");
String i_edate = pr.getString("i_edate");
String i_sourcetype = pr.getString("i_sourcetype");				// 채널

/* 		첫번째 검색 키워드의 파라미터		 */ 
String i_and_text = pr.getString("i_and_text");					// AND 검색 키워드
String i_exact_text = pr.getString("i_exact_text");				// 구문 검색 키워드
String i_or_text = pr.getString("i_or_text");						// OR 검색 키워드
String i_exclude_text = pr.getString("i_exclude_text");			// 제외단어 키워드


/* 		두번째 검색 키워드의 파라미터		 */ 
String i_and_text2 = pr.getString("i_and_text2");				// AND 검색 키워드
String i_exact_text2 = pr.getString("i_exact_text2");				// 구문 검색 키워드
String i_or_text2 = pr.getString("i_or_text2");						// OR 검색 키워드
String i_exclude_text2 = pr.getString("i_exclude_text2");		// 제외단어 키워드

/* 		두번째 검색 키워드의 파라미터		 */ 
String i_and_text3 = pr.getString("i_and_text3");				// AND 검색 키워드
String i_exact_text3 = pr.getString("i_exact_text3");				// 구문 검색 키워드
String i_or_text3 = pr.getString("i_or_text3");						// OR 검색 키워드
String i_exclude_text3 = pr.getString("i_exclude_text3");		// 제외단어 키워드

String i_remove_rt = pr.getString("i_remove_rt");

String title = pr.getString("title");
String keyword = pr.getString("keyword");
String keyword1 = pr.getString("keyword1");
String keyword2 = pr.getString("keyword2");
String keyword3 = pr.getString("keyword3");
int limit = pr.getInt("limit");											// 연관키워드 개수
String tw_rt_chk = pr.getString("tw_rt_chk", "");
JSONArray jArray = new JSONArray();
JSONObject result = new JSONObject();


if("5_1".equals(section) || "5_2".equals(section) || "5_3".equals(section)){
	JSONObject tmpObj = null;
	if("5_1".equals(section)){
		if("1".equals(tw_rt_chk)){
			//트위터 전용 검색 일 경우
			result = searchMgr.getRelationInfoDataForTw(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, 3000, 1, SS_M_ID);
		}else{
			result = searchMgr.getRelationInfoData_excel(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype,i_remove_rt, 3000, 1, SS_M_ID);
		}
		
		jArray = (JSONArray)result.get("data");
		
		subject = keyword+" 관련 정보";
		if("1".equals(tw_rt_chk)){
			titleArr = new String[] {"출처","제목","URL","RT","수집시간"};
		}else{
			titleArr = new String[] {"출처","제목","URL","수집시간"};
		}
		for(int i=0; i < jArray.length(); i++){
			tmp =new String[titleArr.length];
			JSONObject object = new JSONObject();
			object = (JSONObject)jArray.get(i);		
			tmp[0] = object.getString("sitename");
			tmp[1] = object.getString("title");
			tmp[2] = object.getString("url");
			if("1".equals(tw_rt_chk)){
				tmp[3] = object.getString("samecnt");
				tmp[4] = object.getString("date");
			}else{
				tmp[3] = object.getString("date");
			}			
			
			excelList.add(tmp);
		}
		
	}else if("5_2".equals(section)){
		if("1".equals(tw_rt_chk)){
			//트위터 전용 검색 일 경우
			result = searchMgr.getRelationInfoDataForTw(i_sdate, i_edate, i_and_text2, i_exact_text2, i_or_text2, i_exclude_text2, i_sourcetype,i_remove_rt, 3000, 1, SS_M_ID);
		}else{
			result = searchMgr.getRelationInfoData_excel(i_sdate, i_edate, i_and_text2, i_exact_text2, i_or_text2, i_exclude_text2, i_sourcetype,i_remove_rt, 3000, 1, SS_M_ID);
		}
		
		jArray = (JSONArray)result.get("data");
		
		subject = keyword+" 관련 정보";
		if("1".equals(tw_rt_chk)){
			titleArr = new String[] {"출처","제목","URL","RT","수집시간"};
		}else{
			titleArr = new String[] {"출처","제목","URL","수집시간"};
		}
		for(int i=0; i < jArray.length(); i++){
			tmp =new String[titleArr.length];
			JSONObject object = new JSONObject();
			object = (JSONObject)jArray.get(i);		
			tmp[0] = object.getString("sitename");
			tmp[1] = object.getString("title");
			tmp[2] = object.getString("url");
			if("1".equals(tw_rt_chk)){
				tmp[3] = object.getString("samecnt");
				tmp[4] = object.getString("date");
			}else{
				tmp[3] = object.getString("date");
			}			
			
			excelList.add(tmp);
		}
		
	}else if("5_3".equals(section)){
		if("1".equals(tw_rt_chk)){
			//트위터 전용 검색 일 경우
			result = searchMgr.getRelationInfoDataForTw(i_sdate, i_edate, i_and_text3, i_exact_text3, i_or_text3, i_exclude_text3, i_sourcetype,i_remove_rt, 3000, 1, SS_M_ID);
		}else{
			result = searchMgr.getRelationInfoData_excel(i_sdate, i_edate, i_and_text3, i_exact_text3, i_or_text3, i_exclude_text3, i_sourcetype,i_remove_rt, 3000, 1, SS_M_ID);
		}
		
		jArray = (JSONArray)result.get("data");
		
		subject = keyword+" 관련 정보";
		if("1".equals(tw_rt_chk)){
			titleArr = new String[] {"출처","제목","URL","RT","수집시간"};
		}else{
			titleArr = new String[] {"출처","제목","URL","수집시간"};
		}
		for(int i=0; i < jArray.length(); i++){
			tmp =new String[titleArr.length];
			JSONObject object = new JSONObject();
			object = (JSONObject)jArray.get(i);		
			tmp[0] = object.getString("sitename");
			tmp[1] = object.getString("title");
			tmp[2] = object.getString("url");
			if("1".equals(tw_rt_chk)){
				tmp[3] = object.getString("samecnt");
				tmp[4] = object.getString("date");
			}else{
				tmp[3] = object.getString("date");
			}			
			
			excelList.add(tmp);
		}		
	}
}

poiAdd.addExcel(excelPath, fileName, subject, titleArr, excelList);

String fullName = SS_URL + "dashboard/file/" + "excel/" + SS_M_NO + "/" + fileName;
fullName = fullName.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\r", "");
System.out.println("fullName=> " + fullName);

%>

<%=fullName%>