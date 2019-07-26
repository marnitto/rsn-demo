<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="risk.util.*
				 ,risk.admin.bookmark.*           
                 "
%>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
    BookMarkMgr bmMgr = new BookMarkMgr();
	BookMarkBean bmBean = new BookMarkBean();
	bmBean.setB_mark_seq(pr.getString("bookMarkSeq"));
	bmBean.setB_contents(pr.getString("bookMarkContents"));
	bmMgr.saveBookMark(bmBean);
%>
<script language="JavaScript" type="text/JavaScript">
alert('북마크 하였습니다.');
</script>
