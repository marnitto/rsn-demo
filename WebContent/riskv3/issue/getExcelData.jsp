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

	

	String title[] = new String[]{"������ȣ","����","����","�Խ��Ǹ�","URL","����Ʈ��","��ó","����","ID","�г���","�湮��/�ȷο�/ȸ����","�����ڷ�","�����ڷ��"};

	

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

		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">������ȣ</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">����</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">����</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">�Խ��Ǹ�</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">URL</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">����Ʈ��</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">��ó</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">����</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">ID</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">�г���</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">�湮��/�ȷο�/ȸ����</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">�����ڷ�</th>
		<th align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;">�����ڷ��</th>

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