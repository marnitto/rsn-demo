package org.apache.jsp.dashboard.mrt;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.json.JSONObject;
import risk.dashboard.mrt.MrtMgr;
import risk.util.ParseRequest;

public final class mrtDao_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
 
ParseRequest pr = new ParseRequest(request);
pr.printParams();

JSONObject object = new JSONObject();
MrtMgr mMgr = new MrtMgr();

String scope = pr.getString("scope");
String keyword = pr.getString("keyword");
String sDate = pr.getString("sDate");
String eDate = pr.getString("eDate");
String issueCode = pr.getString("issueCode");
String source = pr.getString("source");
String limit = pr.getString("limit");
String senti = pr.getString("senti");

String op = pr.getString("op", "1");
int nowPage = pr.getInt("nowPage");


if("0".equals(pr.getString("section"))){
	object.put("initData", mMgr.getMediaList());
}else if("1".equals(pr.getString("section"))){
	object.put("sectionData1", mMgr.getMainIssueData(nowPage, 10, scope, keyword, sDate, eDate, op));
}else if("1_1".equals(pr.getString("section"))){
	object.put("sectionData1_1", mMgr.getMainIssueData_trend(scope, keyword, sDate, eDate, issueCode));
}else if("1_2".equals(pr.getString("section"))){
	object.put("sectionData1_2", mMgr.getMainIssueData_neg(scope, keyword, sDate, eDate, issueCode));
}else if("2".equals(pr.getString("section"))){
	object.put("sectionData2", mMgr.getWeekData(scope, keyword, sDate, eDate, issueCode));
}else if("3".equals(pr.getString("section"))){
	object.put("sectionData3", mMgr.getMediaData(scope, keyword, sDate, eDate, issueCode));
}else if("4".equals(pr.getString("section"))){
	object.put("sectionData4", mMgr.getMainMediaData(scope, keyword, sDate, eDate, issueCode, source, limit));
}else if("5".equals(pr.getString("section"))){
	object.put("sectionData5", mMgr.getRelationKeywordData(scope, keyword, sDate, eDate, issueCode, source, limit, senti));
}else if("6".equals(pr.getString("section"))){
	JSONObject object2 = new JSONObject();
	object2.put("pos", mMgr.getRelationKeywordSentiData(scope, keyword, sDate, eDate, issueCode, "1"));
	object2.put("neg", mMgr.getRelationKeywordSentiData(scope, keyword, sDate, eDate, issueCode, "2"));
	object2.put("neu", mMgr.getRelationKeywordSentiData(scope, keyword, sDate, eDate, issueCode, "3"));
	object.put("sectionData6", object2);
}else if("7".equals(pr.getString("section"))){
	object.put("sectionData7", mMgr.getRelaltionInfoList(nowPage, 10, scope, keyword, sDate, eDate, issueCode, source));
}
out.println(object);


    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else log(t.getMessage(), t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
