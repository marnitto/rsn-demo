<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../riskv3/inc/sessioncheck.jsp" %>
<%@ page import="risk.util.ParseRequest
                 ,risk.util.DateUtil
                 ,risk.util.PageIndex
                 ,risk.util.StringUtil
                 ,risk.util.POIExcelAdd
                 ,risk.util.ConfigUtil
                 ,risk.dashboard.summary.SummaryMgr
                 ,risk.json.JSONArray
                 ,risk.json.JSONObject
                 ,java.util.*
                 ,java.net.*
                 ,java.text.SimpleDateFormat
                 "
%>
<%
ParseRequest pr = new ParseRequest(request);
DateUtil du = new DateUtil();
ConfigUtil cu = new ConfigUtil();
SummaryMgr sMgr = new SummaryMgr();
String doc_id = pr.getString("p_docid");
String p_date = pr.getString("p_date");
String r_trnd = pr.getString("r_trend","");
String r_relation_word = pr.getString("r_relation_word","");

POIExcelAdd poiAdd = new POIExcelAdd();
String filePath = cu.getConfig("FILEPATH");
String excelPath = filePath + "excel/" + SS_M_NO + "/";
String yyyy = du.getDate("yyyy");
String MMdd = du.getDate("MMdd");
String nowTime = du.getDate("HHmmss");
String fileName = pr.getString("fileName", "daegu_ReplyData_Excel" + yyyy + MMdd + nowTime + ".xls");
List excelList = new ArrayList();

int cnt = 100;

String subject = "";
String[] titleArr = null;
String tmp[] = null;
pr.printParams();

//JSONObject obj = new JSONObject();
JSONArray arr = new JSONArray();
ArrayList larr = new ArrayList();
String jData = null;

sMgr.getLucySwitch();

arr = sMgr.getReplyDataExcelList(cnt, p_date, doc_id, r_trnd, r_relation_word, SS_M_ID, true);


 for(int i=0 ; i<arr.length() ; i++){
	JSONObject obj = new  JSONObject();
	subject = "댓글 정보";
	titleArr = new String[] {"댓글의 수집일시","댓글본문","댓글의 작성일시","댓글의 좋아요수","댓글의 부정수","댓글의 작성자","댓글의 작성자 ID","사이트"};
	tmp = new String [titleArr.length];
	obj = (JSONObject)arr.get(i);	

	tmp[0] = obj.getString("r_datetime");
	tmp[1] = obj.getString("r_content");
	tmp[2] = obj.getString("r_writeDate");
	tmp[3] = obj.getString("r_interest_count");
	tmp[4] = obj.getString("r_badcnt");
	tmp[5] = obj.getString("r_name");
	tmp[6] = obj.getString("r_id");
	tmp[7] = obj.getString("p_sitename");
	
	excelList.add(tmp);
}

 poiAdd.addExcel(excelPath, fileName, subject, titleArr, excelList);
	
	String fullName = SS_URL + "dashboard/file/" + "excel/" + SS_M_NO + "/" + fileName;
	fullName = fullName.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\r", "");
	System.out.println("fullName=> " + fullName);

%>

<%=fullName%>

<%-- <table style="width:1200px; table-layout:fixed;" border="1" cellspacing="1" >
    <caption></caption>
	<colgroup>
		<col style="width:200px">
		<col style="width:400px">
		<col style="width:400px">
		<col style="width:100px">
		<col style="width:100px">
		<col>
	</colgroup>
	<thead>
		<!-- 공통 헤더 추가 대시보드 타이틀 및 검색기간-->
		<%
			int colspan = 8;
		%>
		<tr style="height:40px">
			<th scope="col" colspan="<%=colspan%>">댓글 정보</th>
		</tr>
		<tr>
		<th scope="col"><span>댓글의 수집일시</span></th>
		<th scope="col"><span>댓글본문</span></th>
		<th scope="col"><span>댓글의 작성일시</span></th>
		<th scope="col"><span>댓글의 좋아요수</span></th>
		<th scope="col"><span>댓글의 부정수</span></th>
		<th scope="col"><span>댓글의 작성자</span></th>
		<th scope="col"><span>댓글의 작성자 ID</span></th>
		<th scope="col"><span>사이트</span></th>
		</tr>
	</thead>
    <tbody>
    
    <%
    	for(int i=0; i<arr.length(); i++){
    		JSONObject obj = new  JSONObject();
			obj = (JSONObject)arr.get(i);	
			//java.util.Date time = new java.util.Date((long)obj.getInt("r_writeDate")*1000);
			//SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//String r_date = date.format(time);

			//String Jdata = obj.getString("r_jsondata");
    %>
	<tr>
		<td><span><%=obj.get("r_datetime")%></span></td>
		<td><span><%=obj.get("r_content")%></span></td>
		<td><span><%=obj.get("r_writeDate") %></span></td>
		<td><span><%=obj.get("r_interest_count")%></span></td>
		<td><span><%=obj.get("r_badcnt")%></span></td>
		<td><span><%=obj.get("r_name")%></span></td>
		<td><span><%=obj.get("r_id")%></span></td>
		<td><span><%=obj.get("p_sitename")%></span></td>
	</tr>
	<%
    	}
    %>
    </tbody>
</table> --%>
