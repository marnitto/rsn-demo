package org.apache.jsp.riskv3.admin.alimi;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;
import java.util.ArrayList;
import risk.mobile.AlimiReceiverBean;
import risk.mobile.AlimiSettingDao;
import risk.util.ParseRequest;
import java.util.List;

public final class ifrm_005freceiver_005flist_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/admin/alimi/../../inc/sessioncheck.jsp");
  }

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
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');
//@ page contentType="text/html; charset=euc-kr"
      out.write("\r\n");
      out.write("\r\n");

    
	String SS_M_NO = (String)session.getAttribute("SS_M_NO")   == null ? "": (String)session.getAttribute("SS_M_NO")  ;
    String SS_M_ID = (String)session.getAttribute("SS_M_ID")   == null ? "": (String)session.getAttribute("SS_M_ID")  ;
    String SS_M_NAME = (String)session.getAttribute("SS_M_NAME") == null ? "": (String)session.getAttribute("SS_M_NAME");
    String SS_MG_NO = (String)session.getAttribute("SS_MG_NO")  == null ? "": (String)session.getAttribute("SS_MG_NO") ;
	String SS_TITLE = (String)session.getAttribute("SS_TITLE")  == null ? "": (String)session.getAttribute("SS_TITLE");
    String SS_URL =   (String)session.getAttribute("SS_URL")    == null ? "": (String)session.getAttribute("SS_URL") ;
    String SS_M_DEPT =   (String)session.getAttribute("SS_M_DEPT")    == null ? "": (String)session.getAttribute("SS_M_DEPT") ;    
    String SS_M_IP =   (String)session.getAttribute("SS_M_IP")    == null ? "": (String)session.getAttribute("SS_M_IP") ;    
    String SS_M_MAIL =   (String)session.getAttribute("SS_M_MAIL")    == null ? "": (String)session.getAttribute("SS_M_MAIL") ;    
    
	String SS_ML_URL = javax.servlet.http.HttpUtils.getRequestURL(request).toString();
	
	String SS_SEARCHDATE = (String)session.getAttribute("SS_SEARCHDATE")    == null ? "": (String)session.getAttribute("SS_SEARCHDATE") ;
	
	
    

	if ((SS_M_ID.equals("")) || !SS_M_IP.equals(request.getRemoteAddr()) ) {
		ConfigUtil cu = new ConfigUtil();
		out.print("<SCRIPT Language=JavaScript>");
		//out.print("window.setTimeout( \" top.document.location = "+cu.getConfig("URL")+"'riskv3/error/sessionerror.jsp' \") ");
		out.print("top.document.location = '"+cu.getConfig("URL")+"riskv3/error/sessionerror.jsp'");
        out.print("</SCRIPT>");
    }
	

      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");

	ParseRequest pr = new ParseRequest(request);
	AlimiSettingDao asDao = new AlimiSettingDao();
	ArrayList arrReceiverList =  new ArrayList();
	AlimiReceiverBean arBean = new AlimiReceiverBean();	
	
	
	String mode = pr.getString("mode","");
	String ab_seq = pr.getString("ab_seq","");
	String[] arrAb_seq = null;
	String checked = "";
	int abSeqCount = 0;
		
	if(ab_seq!=null && !ab_seq.equals(""))
	{
		arrAb_seq = ab_seq.split(",");
		abSeqCount = arrAb_seq.length;
	}
	
	
	String existsAb_seq = "";
	arrReceiverList = asDao.getAddressList("","","");	
	pr.printParams();
	
	

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>");
      out.print(SS_TITLE );
      out.write("</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../../../css/base.css\" type=\"text/css\">\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write("<!--\r\n");
      out.write("var listCheckCnt = 0;\r\n");
      out.write("function checkAll(check)\r\n");
      out.write("{\r\n");
      out.write("\tlistCheckCnt = 0;  \r\n");
      out.write("\tvar checked = check;\r\n");
      out.write("\tvar f = document.ifr_receiver;\t\t\r\n");
      out.write("    for (var i = 0; i < f.elements.length; i++) {        \t \r\n");
      out.write("        var e = f.elements[i];\r\n");
      out.write("        if ( e.type == \"checkbox\" ) {\r\n");
      out.write("            if(check)\r\n");
      out.write("            {\r\n");
      out.write("        \t\tlistCheckCnt++;\r\n");
      out.write("            }else{\r\n");
      out.write("            \tlistCheckCnt--;\r\n");
      out.write("            }\r\n");
      out.write("            e.checked = checked;\r\n");
      out.write("        }       \r\n");
      out.write("    }\r\n");
      out.write("    listCnt();    \r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function checkList()\r\n");
      out.write("{\r\n");
      out.write("\tvar f = document.ifr_receiver;\r\n");
      out.write("\tvar checked = false;\r\n");
      out.write("\tvar values = new Array();\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\tif(f.chkNum)\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif(f.chkNum.length)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tfor(var i =0;i<f.chkNum.length;i++)\r\n");
      out.write("\t\t\t{\t\t\t\t\r\n");
      out.write("\t\t\t\tif(f.chkNum[i].checked)\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tchecked = true;\r\n");
      out.write("\t\t\t\t\tvalues = f.chkNum[i].value.split(',');\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\tif(f.delSeq.value=='')\r\n");
      out.write("\t\t\t\t\t{\r\n");
      out.write("\t\t\t\t\t\tf.delSeq.value = values[0];\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\telse\r\n");
      out.write("\t\t\t\t\t{\r\n");
      out.write("\t\t\t\t\t\tf.delSeq.value +=','+ values[0];\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}else{\t\t\t\r\n");
      out.write("\t\t\tif(f.chkNum.checked)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tchecked = true;\r\n");
      out.write("\t\t\t\tvalues = f.chkNum.value.split(',');\r\n");
      out.write("\t\t\t\tf.delSeq.value = values[0];\t\t\t\t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\t\r\n");
      out.write("\treturn checked;\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function listCnt()\r\n");
      out.write("{\r\n");
      out.write("\tvar f = document.ifr_receiver;\r\n");
      out.write("\tvar chkCount = 0;\r\n");
      out.write("\r\n");
      out.write("\tif(f.chkNum)\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif(f.chkNum.length)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tfor(var i =0;i<f.chkNum.length;i++)\r\n");
      out.write("\t\t\t{\t\t\t\t\r\n");
      out.write("\t\t\t\tif(f.chkNum[i].checked)\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tchkCount ++;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\t\t\t\r\n");
      out.write("\t\t}else{\t\t\t\r\n");
      out.write("\t\t\tif(f.chkNum.checked)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tchkCount ++;\t\t\t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t//document.getElementById('cnt').innerHTML = chkCount;     \r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("function receiverDel(){\r\n");
      out.write("\t\r\n");
      out.write("\tvar f = document.ifr_receiver ;\t\r\n");
      out.write("\tif(f.existAb_seq.value.length>0)\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif(checkList())\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tf.target = '';\r\n");
      out.write("\t\t\tf.action = 'pop_receiver_del_prc.jsp';\r\n");
      out.write("\t\t\tf.submit();\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\talert(\"삭제할 수신자를 체크하여 주십시오.\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\talert(\"설정된 수신자가 없습니다.\");\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function receiverAdd(){ \t\t\r\n");
      out.write("\twindow.open(\"pop_receiver_detail.jsp\", \"receiverAdd\", \"width=650,height=220,scrollbars=yes\");\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function receiverCall(){\r\n");
      out.write("\tvar f = document.ifr_receiver ;\r\n");
      out.write("\tf.target = '';\r\n");
      out.write("\tf.action = 'pop_receiver_call_prc.jsp';\r\n");
      out.write("\tf.submit();\t\r\n");
      out.write("}\r\n");
      out.write("//-->\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<form name=\"ifr_receiver\" method=\"post\">\r\n");
      out.write("<input type=\"hidden\" name=\"delSeq\">\r\n");
      out.write("<input type=\"hidden\" name=\"existAb_seq\" value=\"");
      out.print(ab_seq);
      out.write("\">\r\n");
      out.write("<table width=\"820\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("<tr>\r\n");
      out.write("\t<td style=\"padding-top:15px;height:45px;\"><span class=\"sub_tit\">수신자 설정</span></td>\r\n");
      out.write("</tr>\r\n");
      out.write("<tr>\r\n");
      out.write("\t<td>\r\n");
      out.write("\t<table id=\"board_01\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"height:30px;table-layout:fixed;\">\r\n");
      out.write("\t<col width=\"5%\"><col width=\"15%\"><col width=\"15%\"><col width=\"65%\">\r\n");
      out.write("\t<tr>           \r\n");
      out.write("\t\t\t<th><input type=\"checkbox\" name=\"checkall\" id=\"ab_seqALL\" onclick=\"checkAll(this.checked);\"></th>\r\n");
      out.write("\t\t\t<th>성명</th>\r\n");
      out.write("\t\t\t<th>부서</th>\r\n");
      out.write("\t\t\t<th>메일주소</th>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t</table>\r\n");
      out.write("\t<div style=\"height:125px;overflow:auto\">\r\n");
      out.write("\t<table id=\"board_01\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed;\">\r\n");
      out.write("\t<col width=\"5%\"><col width=\"15%\"><col width=\"15%\"><col width=\"65%\">\r\n");

	if(arrReceiverList.size()>0){
		for(int i=0; i<arrReceiverList.size(); i++){
			arBean = new AlimiReceiverBean();
			arBean = (AlimiReceiverBean)arrReceiverList.get(i);
			
			if(arrAb_seq!=null){				
				checked ="";
				for(int j=0 ; j<arrAb_seq.length; j++){
					if(arrAb_seq[j].equals(arBean.getAb_seq())){
						System.out.println(arrAb_seq[j]);
						checked ="checked";	
					}
				}
			}

      out.write("\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td><input type=\"checkbox\" name=\"chkNum\" value=\"");
      out.print(arBean.getAb_seq());
      out.write("\" onclick=\"listCnt();\" ");
      out.print(checked);
      out.write("></td>\r\n");
      out.write("\t\t\t<td>");
      out.print(arBean.getAb_name() );
      out.write("</td>\r\n");
      out.write("\t\t\t<td>");
      out.print(arBean.getAb_dept());
      out.write("</td>\r\n");
      out.write("\t\t\t<td><span class=\"mail\">");
      out.print(arBean.getAb_mail());
      out.write("</span></td>\r\n");
      out.write("\t\t</tr>\r\n");

		}
	}else{

      out.write("\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td colspan=\"4\" style=\"font-weight:bold;height:40px\" align=\"center\">조건에 맞는 데이터가 없습니다.</td>\r\n");
      out.write("\t\t</tr>\r\n");

	}

      out.write("\r\n");
      out.write("\t</table>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t</td>\r\n");
      out.write("</tr>\r\n");
      out.write("</table>\r\n");
      out.write("</form>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
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
