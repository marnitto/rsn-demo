<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="risk.dashboard.search.SearchMgr"%>
<%@page import="risk.json.JSONObject"%>
<%@page import="risk.util.ParseRequest"%>
<%@page import="risk.util.DateUtil"%>
<%
ParseRequest pr = new ParseRequest(request);
pr.printParams();

DateUtil du = new DateUtil();
JSONObject object = new JSONObject();
JSONObject object2 = new JSONObject();
SearchMgr searchMgr = new SearchMgr();

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

int nowPage = pr.getInt("nowPage");
int limit = pr.getInt("limit");											// 연관키워드 개수

//연관키워드 일때 최대 검색기간을 31일로 제한 한다.-lucy api 공통 사항

if("1".equals(section)){
	String naverBoardCode = "2305157";
	String daumBoardCode = "2305159";

	object2.put("naver", searchMgr.getRealTimeList(naverBoardCode));
	object2.put("daum", searchMgr.getRealTimeList(daumBoardCode));
	object.put("sectionData1", object2);
	
}else if("2".equals(section)){			// 정보검색 - 검색키워드1 & 검색키워드2
	object2.put("firstKeyword", searchMgr.getInfoData(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype, "1"));
	object2.put("secondKeyword", searchMgr.getInfoData(i_sdate, i_edate, i_and_text2, i_exact_text2, i_or_text2, i_exclude_text2, i_sourcetype, "2"));
	
	object.put("sectionData2", object2);
	
}else if("3_1".equals(section)){		// 감성현황 - 검색키워드1
	object2.put("firstKeyword_left", searchMgr.getSentiData01(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype));					// 왼쪽차트
	object2.put("firstKeyword_right", searchMgr.getSentiData02(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype));					// 오른쪽차트
	
	object.put("sectionData3", object2);
	
}else if("3_2".equals(section)){		// 감성현황 - 검색키워드2
	object2.put("secondKeyword_left", searchMgr.getSentiData01(i_sdate, i_edate, i_and_text2, i_exact_text2, i_or_text2, i_exclude_text2, i_sourcetype));			// 왼쪽차트
	object2.put("secondKeyword_right", searchMgr.getSentiData02(i_sdate, i_edate, i_and_text2, i_exact_text2, i_or_text2, i_exclude_text2, i_sourcetype));		// 오른쪽차트
	
	object.put("sectionData3", object2);
	
}else if("4_1".equals(section)){		// 연관키워드 - 검색키워드1

		long diffCnt = du.DateDiff("yyyyMMdd",i_edate,i_sdate);
		if(diffCnt > 31){
			i_sdate = du.addDay_v2(i_edate, -31).replaceAll("-", "");
		}

	
	object.put("firstKeyword", searchMgr.getRelationKeywordData(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype, limit));

}else if("4_2".equals(section)){		// 연관키워드 - 검색키워드2
	
	long diffCnt = du.DateDiff("yyyyMMdd",i_edate,i_sdate);
	if(diffCnt > 31){
		i_sdate = du.addDay_v2(i_edate, -31).replaceAll("-", "");
	}

	object.put("secondKeyword", searchMgr.getRelationKeywordData(i_sdate, i_edate, i_and_text2, i_exact_text2, i_or_text2, i_exclude_text2, i_sourcetype, limit));

}else if("5_1".equals(section)){		// 관련정보 - 검색키워드1
	object.put("sectionData5", searchMgr.getRelationInfoData(i_sdate, i_edate, i_and_text, i_exact_text, i_or_text, i_exclude_text, i_sourcetype, 10, nowPage));

}else if("5_2".equals(section)){		// 관련정보 - 검색키워드2
	object.put("sectionData5", searchMgr.getRelationInfoData(i_sdate, i_edate, i_and_text2, i_exact_text2, i_or_text2, i_exclude_text2, i_sourcetype, 10, nowPage));

}
out.println(object);

%>