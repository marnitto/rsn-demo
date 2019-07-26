<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>

<%@ include file="../inc/sessioncheck.jsp" %>

<%@ page import="risk.issue.IssueMgr,
				 risk.issue.IssueDataBean,
				 risk.issue.IssueCodeBean,
				 risk.issue.IssueCodeMgr,
				 risk.issue.IssueCommentBean,
				 risk.util.*,
                 risk.admin.member.MemberBean,
                 risk.admin.member.MemberDao,                 
                 java.util.*,
                 java.net.URLDecoder"
%>

<%


	ParseRequest pr	= new ParseRequest(request);

	pr.printParams();	

	DateUtil du	= new DateUtil();

	StringUtil	su 	= new StringUtil();

	IssueMgr issueMgr = new IssueMgr();


	String sDate = pr.getString("sDateFrom");

	String eDate = pr.getString("sDateTo");

	String ir_stime = pr.getString("ir_stime");

	String ir_etime = pr.getString("ir_etime");

	String typeCode = pr.getString("typeCode");

	String m_seq = pr.getString("m_seq",SS_M_NO);

	String searchType = pr.getString("searchType", "1");

	String searchKey = URLDecoder.decode(pr.getString("encodingSearchKey",""),"UTF-8");

	System.out.println("searchKey : "+searchKey);

	

	List list = issueMgr.issueExcelDownLoad(sDate, eDate, ir_stime, ir_etime, typeCode, searchType, searchKey, m_seq);

	

	String title[] = new String[]{"문서번호","일자","제목","게시판명","URL","사이트명","출처","성향","ID","닉네임","방문자/팔로워/회원수","보도자료","보도자료명"};

	

	Map map = null;

	map = new HashMap();

	//map.put("name", "issue_data");

	//map.put("title", title);

	//map.put("list", list);

	//new ExcelView().buildExcelDocument(map, request, response);

	

	response.setContentType("application/vnd.ms-excel; charset=EUC-KR") ;
    
    response.setHeader("Content-Disposition", "attachment;filename=Issue_Data_"+ du.getCurrentDate("yyyyMMdd") +".xls");
    
    response.setHeader("Content-Description", "JSP Generated Data");

	

%>

<table border="1" cellspacing="0" cellpadding="0">

<thead>

	<tr>

		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">문서번호</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">일자</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">제목</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">게시판명</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">URL</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">사이트명</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">출처</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">성향</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">ID</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">닉네임</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">방문자/팔로워/회원수</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">보도자료</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">보도자료명</th>

	</tr>

</thead>

<tbody>

<%

	

	if(list.size() > 0){

		for(int i=0; i <list.size(); i++){

			map = new HashMap();

			map = (HashMap)list.get(i);

%>

		<tr>

			<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;" ><%=map.get("ID_SEQ")%>			</td>
			<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;" ><%=map.get("MD_DATE")%>			</td>
			<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;" ><%=map.get("ID_TITLE")	%>		</td>
			<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;" ><%=map.get("MD_SITE_MENU")%>		</td>
			<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;" ><%=map.get("ID_URL")		%>	</td>
			<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;" ><%=map.get("MD_SITE_NAME")	%>	</td>
			<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;" ><%=map.get("CODE6")			%>	</td>
			<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;" ><%=map.get("CODE9")			%>	</td>
			<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;" ><%=map.get("USER_ID")		%>	</td>
			<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;" ><%=map.get("USER_NICK")		%>	</td>
			<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;" ><%=map.get("BLOG_VISIT_COUNT")%> /<%=map.get("CAFE_NAME")		%> / <%=map.get("CAFE_MEMBER_COUNT")%>	</td>
			<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;" ><%=map.get("CODE10")		%>	</td>
			<td align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;" ><%=map.get("P_NAME")		%>	</td>

		</tr>

<%              			

		}

	}

%>

</tbody>

</table>