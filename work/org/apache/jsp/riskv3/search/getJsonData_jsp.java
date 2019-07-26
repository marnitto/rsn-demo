package org.apache.jsp.riskv3.search;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import risk.json.JSONObject;
import risk.json.JSONArray;
import risk.util.ParseRequest;
import risk.issue.IssueCodeMgr;
import risk.issue.IssueCodeBean;

public final class getJsonData_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write(" \r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");


ParseRequest    pr = new ParseRequest(request);

IssueCodeMgr icMgr = new IssueCodeMgr();
IssueCodeBean icBean= new IssueCodeBean();
String icPtype = pr.getString("ic_Ptype");
String icPcode = pr.getString("ic_Pcode");
String flag = pr.getString("flag");
String kind = pr.getString("icType1");


if(flag.equals("2death")){
	
	JSONObject jObect = new JSONObject(); 
	JSONArray jArray = new JSONArray();
	ArrayList alDate  = new ArrayList();
	
	alDate =  icMgr.getArrayListForIssueCode(icPtype, icPcode);
	
	if(alDate.size() > 0){
		for(int i =0; i < alDate.size(); i++){
			icBean = (IssueCodeBean)alDate.get(i);
			
			JSONObject object = new JSONObject();
			object.put("icName",icBean.getIc_name());
			object.put("icType",icBean.getIc_type());
			object.put("icCode",icBean.getIc_code());
			jArray.put(object);
		}
	}
	jObect.put("list", jArray);
	out.println(jObect);
}else if("manual".equals(flag)){
	JSONObject obj = new JSONObject();	
	String type5 = pr.getString("type5");
	String type51 = pr.getString("type51");
	
	obj = icMgr.getArrayListForIssueCode_Manual(type5,type51);
	
	out.println(obj);
}


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
