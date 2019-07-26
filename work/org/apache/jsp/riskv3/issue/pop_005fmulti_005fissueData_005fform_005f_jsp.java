package org.apache.jsp.riskv3.issue;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import risk.util.ParseRequest;
import risk.util.DateUtil;
import risk.util.StringUtil;
import risk.admin.info.*;
import risk.admin.keyword.KeywordBean;
import risk.admin.hotkeyword.hotkeywordMgr;
import risk.admin.hotkeyword.hotkeywordBean;
import risk.search.MetaBean;
import risk.search.MetaMgr;
import risk.search.solr.*;
import risk.issue.IssueCodeMgr;
import risk.issue.IssueCodeBean;
import risk.issue.IssueBean;
import risk.issue.IssueMgr;
import risk.sms.AddressBookDao;
import risk.sms.AddressBookBean;
import risk.sms.AddressBookGroupBean;
import risk.issue.IssueDataBean;
import risk.util.ConfigUtil;

public final class pop_005fmulti_005fissueData_005fform_005f_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/issue/../inc/sessioncheck.jsp");
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
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
      out.write('\r');
      out.write('\n');

	ParseRequest pr = new ParseRequest(request);		
	DateUtil	 du = new DateUtil();
	StringUtil		su = new StringUtil();
	pr.printParams();
	
	MetaMgr  metaMgr = new MetaMgr();
	IssueCodeMgr 	icm = new IssueCodeMgr();		
	IssueMgr issueMgr = new IssueMgr();
	AddressBookDao abDao = new AddressBookDao();
	
	ArrayList arrIcBean = new ArrayList();	 //분류코드 어레이
	
	IssueDataBean idBean = new IssueDataBean();
	IssueCodeBean icBean = new IssueCodeBean();
	MetaBean metaBean = new MetaBean();			
			
	String selected = null;
	String mode = pr.getString("mode", "update_multi");
	String issue_m_seq = pr.getString("issue_m_seq");
	
	int ic_seq = 0;
	
	//분류체계코드 (0:분류항목 포함, 1:분류항목 제외)
	icm.init(0);
	
	
	//이슈데이터 등록 관련
	IssueMgr isMgr = new IssueMgr();
	IssueBean isBean = new IssueBean();	   	   	
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	
	//get할려는 IC_TYPE값을 num에 세팅한다.
	int num = 0;
	String checked ="";
	String checkedValue="";

		

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"ko\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta charset=\"utf-8\">\r\n");
      out.write("<meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\">\r\n");
      out.write("<meta http-equiv=\"Content-Style-Type\" content=\"text/css\">\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n");
      out.write("<title></title>\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write("\t*{margin:0;padding:0;font-family:돋움,Dotum,AppleGothic,Sans-serif,Tahoma;-webkit-text-size-adjust:none}\r\n");
      out.write("\theader,footer,section,article,aside,nav,hgroup,details,menu,figure,figcaption{display:block}\r\n");
      out.write("\thtml{width:100%;height:100%;padding:0;margin:0;border:0}\r\n");
      out.write("\tbody{width:100%;height:100%;color:#666666;font-size:12px;line-height:15px}\r\n");
      out.write("\timg,fieldset,iframe{border:0}\r\n");
      out.write("\t\r\n");
      out.write("\t.wrap{padding-left:350px}\r\n");
      out.write("\t.ori_txts{position:fixed;top:0;left:0;bottom:0;width:350px;padding:20px;border:1px solid #d5d5d5;box-sizing:border-box;;overflow-y:auto;vertical-align:top}\r\n");
      out.write("\t.ori_txts > .in_con{font-size:11px;line-height:18px}\r\n");
      out.write("\t\r\n");
      out.write("\t/* #wrap_pop{width:100%} */\r\n");
      out.write("\th2{height:20px;padding:13px 0 10px 10px;background:url(../../images/common/pop_tit_bg.gif);color:#fff;font-size:14px}\r\n");
      out.write("\th3{padding:10px 0 10px 15px;background:url(../../images/common/icon_subtit.gif) no-repeat left 11px;text-align:left;color:#363636;font-size:12px}\r\n");
      out.write("\ttable{border-collapse:separate;*border-collapse:collapse;border-spacing:0;table-layout:fixed}\r\n");
      out.write("\tinput[type=text]{padding:2px 5px;border:1px solid #d3d3d3}\r\n");
      out.write("\tinput[type=text].h24{height:22px;padding:0 5px;vertical-align:top}\r\n");
      out.write("\tselect{padding:2px 5px;border:1px solid #d3d3d3}\r\n");
      out.write("\ttextarea{display:block;padding:2px 5px;margin:0;border:1px solid #d3d3d3;resize:none}\r\n");
      out.write("\t.invisible{position:absolute;width:0;height:0;font-size:0;overflow:hidden;visibility:hidden}\r\n");
      out.write("\t.ui_input_table{padding:10px}\r\n");
      out.write("\t.ui_input_table > table > caption{display:none}\r\n");
      out.write("\t.ui_input_table > table{width:100%}\r\n");
      out.write("\t.ui_input_table > table > tbody > tr:first-child > th,.ui_input_table > table > tbody > tr:first-child > td{border-top:2px solid #d5d5d5}\r\n");
      out.write("\t.ui_input_table > table > tbody > tr > th,.ui_input_table > table > tbody > tr > td{height:24px;padding:5px 0;border-bottom:1px dotted #cccccc;text-align:left}\r\n");
      out.write("\t.ui_input_table > table > tbody > tr > th{background:#f7f7f7;color:#555555;font-weight:bold;text-indent:15px}\r\n");
      out.write("\t.ui_input_table > table > tbody > tr > th span{padding-left:6px;background:url(../../images/common/icon_dot.gif) no-repeat left 4px}\r\n");
      out.write("\t.ui_input_table > table > tbody > tr > th span strong{padding-left:6px;color:#519eff}\r\n");
      out.write("\t.ui_input_table > table > tbody > tr > td{padding-left:10px}\r\n");
      out.write("\tdiv.botBtns{padding:15px 0;text-align:center}\r\n");
      out.write("\tspan.comp{display:inline-block;padding-right:10px}\r\n");
      out.write("\tspan.comp input{position:relative;top:2px;border:none}\r\n");
      out.write("\tspan.comp input + label{padding-left:5px}\r\n");
      out.write("\tspan.comp:last-child{padding-right:0}\r\n");
      out.write("\t.btn_add{width:39px;height:24px;border:none;background:url(../../images/admin/member_group/btn_add.gif) no-repeat 0 0;vertical-align:top;cursor:pointer;outline:none}\r\n");
      out.write("\t.keyword_list{padding:10px 0 5px}\r\n");
      out.write("\t.keyword_list:empty{display:none;padding:0}\r\n");
      out.write("\t.keyword_list:after{display:block;clear:both;content:''}\r\n");
      out.write("\t.keyword_list li{float:left}\r\n");
      out.write("\t.keyword_list .item{padding:5px;margin-right:8px;border:1px solid #efefef;border-radius:2px;background:#f9f9f9;line-height:1}\r\n");
      out.write("\t.keyword_list .item:hover{border:1px solid #c7c7c7}\r\n");
      out.write("\t.keyword_list .item .btn_del{width:13px;height:13px;margin:1px 0 0 6px;border:none;background:url(../../images/search/delete_btn_01.gif) no-repeat 0 0;text-align:left;text-indent:-9999px;cursor:pointer;outline:none;vertical-align:top}\r\n");
      out.write("</style>\r\n");
      out.write("<link href=\"");
      out.print(SS_URL);
      out.write("css/jquery.autocomplete.css\" rel=\"stylesheet\"type=\"text/css\" />\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/jquery-1.8.3.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/ajax.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/common.js\"  type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/popup.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/jquery.autocomplete.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("function getTypeCodeDeath2(type, code, name){\r\n");
      out.write("\tvar icPtype = type;\r\n");
      out.write("\tvar icPcode = code;\r\n");
      out.write("\tvar icPName = name;\r\n");
      out.write("\t$(\"#tr_icType\"+icPtype).css(\"display\",\"\");\r\n");
      out.write("\t\r\n");
      out.write("\tvar icon = \"\";\r\n");
      out.write("\t\r\n");
      out.write("\tif(type == \"5\"){\r\n");
      out.write("\t\ticon = \"&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>\";\t\t\r\n");
      out.write("\t\ticPName = \"부서\";\r\n");
      out.write("\t}else if(type == \"3\"){\r\n");
      out.write("\t\ticon = \"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>\";\r\n");
      out.write("\t\ticPName = \"소분류\";\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t$(\"#label_\"+icPtype+\"1\").html(icon+icPName);\r\n");
      out.write("\t\r\n");
      out.write("\tvar icType1 = \"H\";\r\n");
      out.write("\tvar checked= \"\";\r\n");
      out.write("\t\r\n");
      out.write("\t$.ajax({\r\n");
      out.write("\t\ttype:'POST',\r\n");
      out.write("\t\turl:\"getJsonData.jsp\",\r\n");
      out.write("\t\tdataType:'json',\r\n");
      out.write("\t\tdata:{ic_Ptype:icPtype, ic_Pcode:icPcode, flag: \"2death\", icType1:icType1},\r\n");
      out.write("\t\tsuccess:function(data){\r\n");
      out.write("\t\t\tvar innerSpan = \"\";\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t$.each(data.list, function(index){\r\n");
      out.write("\t\t\t\tvar num = data.list[index].icType;\r\n");
      out.write("\t\t\t\tif(data.list.length==1){\r\n");
      out.write("\t\t\t\t\tchecked = \"checked\";\r\n");
      out.write("\t\t\t\t\t$(\"[name=focus_icType\"+num+\"]\").val(data.list[index].icType+\",\"+data.list[index].icCode);\r\n");
      out.write("\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\tchecked = \"\";\r\n");
      out.write("\t\t\t\t}\t\r\n");
      out.write("\t\t\t\t/* innerSpan += \"<span class='comp'><input id='input_radio_01_\"+num+\"_\"+index+\"' class='validation_\"+num+\"' name='icType\"+num+\"' onclick='comboCheck(this);' value='\"+data.list[index].icType+\",\"+data.list[index].icCode+\"' type='radio' \"+checked+\" />\"\r\n");
      out.write("\t\t\t\t+\"<label for='input_radio_01_\"+num+\"_\"+index+\"'>\"+data.list[index].icName+\"</label></span>\" */\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tif(num == \"51\"){\r\n");
      out.write("\t\t\t\t\tinnerSpan += \"<span class='comp'><input id='input_radio_01_\"+num+\"_\"+index+\"' class='validation_\"+num+\"' name='icType\"+num+\"' onclick='comboCheckManual(this);' value='\"+data.list[index].icType+\",\"+data.list[index].icCode+\"' type='radio' \"+checked+\" />\"\r\n");
      out.write("\t\t\t\t\t+\"<label for='input_radio_01_\"+num+\"_\"+index+\"'>\"+data.list[index].icName+\"</label></span>\"\r\n");
      out.write("\t\t\t\t}else{\t\t\t\t\r\n");
      out.write("\t\t\t\t\tinnerSpan += \"<span class='comp'><input id='input_radio_01_\"+num+\"_\"+index+\"' class='validation_\"+num+\"' name='icType\"+num+\"' onclick='comboCheck(this);' value='\"+data.list[index].icType+\",\"+data.list[index].icCode+\"' type='radio' \"+checked+\" />\"\r\n");
      out.write("\t\t\t\t\t+\"<label for='input_radio_01_\"+num+\"_\"+index+\"'>\"+data.list[index].icName+\"</label></span>\"\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\t$(\"#td_icType\"+icPtype).html(innerSpan);\r\n");
      out.write("\r\n");
      out.write("\t\t\tif(icPtype == \"5\"){\r\n");
      out.write("\t\t\t\tif($(\"[name=icType51]\").length == 1){\r\n");
      out.write("\t\t\t\t\t$(\"[name=icType51]\").click();\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t,error:function(){}\r\n");
      out.write("\t\r\n");
      out.write("\t});\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("//체크해제\r\n");
      out.write("function comboCheck(obj){\r\n");
      out.write("\r\n");
      out.write("\tvar f = document.fSend;\r\n");
      out.write("\tfocusObj = eval('f.focus_'+ obj.name);\r\n");
      out.write("\r\n");
      out.write("\tif(focusObj.value == obj.value){\r\n");
      out.write("\t\tobj.checked = false;\r\n");
      out.write("\t\tfocusObj.value = '';\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\tfocusObj.value = obj.value;\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("//체크해제\r\n");
      out.write("function comboCheckType2(obj,icType,icCode,icName){\r\n");
      out.write("\r\n");
      out.write("\tvar f = document.fSend;\r\n");
      out.write("\tfocusObj = eval('f.focus_'+ obj.name);\r\n");
      out.write("\r\n");
      out.write("\tif(focusObj.value == obj.value){\r\n");
      out.write("\t\tobj.checked = false;\r\n");
      out.write("\t\tfocusObj.value = '';\r\n");
      out.write("\t\tif(\"icType2\" == obj.name){\r\n");
      out.write("\t\t\t$(\"#tr_icType2\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t$(\"[name=icType21]\").attr(\"checked\", false);\r\n");
      out.write("\t\t}else if(\"icType3\" == obj.value){\r\n");
      out.write("\t\t\t$(\"#tr_icType3\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t$(\"[name=icType31]\").attr(\"checked\", false);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\tgetTypeCodeDeath2(icType,icCode,icName);\r\n");
      out.write("\t\tfocusObj.value = obj.value;\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("function comboCheckManual(){\r\n");
      out.write("\tvar type5 = $(\"[name=icType5]:checked\").val().split(\",\")[1];\r\n");
      out.write("\tvar type51 = $(\"[name=icType51]:checked\").val().split(\",\")[1];\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\tif(type51 != '77'){\r\n");
      out.write("\t\t$.ajax({\r\n");
      out.write("\t\t\ttype:'POST',\r\n");
      out.write("\t\t\turl:\"getJsonData.jsp\",\r\n");
      out.write("\t\t\tdataType:'json',\r\n");
      out.write("\t\t\tdata:{\"type5\":type5, \"type51\":type51, flag: \"manual\"},\r\n");
      out.write("\t\t\tsuccess:function(data){\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tvar type3 = \"input_radio_01_03_0\"+data.type3;\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"#label_3\").html(\"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>대분류\");\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"#\"+type3).click();\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tsetTimeout(function() {\r\n");
      out.write("\t\t\t\t\tif(typeof data.type31 != \"undefined\"){\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\tvar type31 = \"31,\"+data.type31;\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t$(\"[name=icType31]\").each(function(){\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\tif(this.value == type31){\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\tthis.checked = true;\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t});\t\t\t\t\r\n");
      out.write("\t\t\t\t\t}\t\t\t\t \r\n");
      out.write("\t\t\t\t}, 50);\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t,error:function(){}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t});\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\t$(\"[name=icType3]\").attr(\"checked\",false);\r\n");
      out.write("\t\t$(\"[name=icType31]\").attr(\"checked\",false);\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("var regChk = false;//이미 등록중인지 확인하기위한 값\r\n");
      out.write("function save_issue(mode){\r\n");
      out.write("\t\r\n");
      out.write("\t//타입별 값 세팅\r\n");
      out.write("\tvar typeCode = settingTypeCode();\r\n");
      out.write("\t\r\n");
      out.write("\tvar f = document.fSend;\r\n");
      out.write("\tif(!regChk){\r\n");
      out.write("\t\tregChk = true;\r\n");
      out.write("\t\tf.typeCode.value = typeCode;\r\n");
      out.write("\t\tf.mode.value = mode;\r\n");
      out.write("\t\t\r\n");
      out.write("        var formData = $(\"[name=fSend]\").serialize();\r\n");
      out.write("        \r\n");
      out.write("\t\t$.ajax({\r\n");
      out.write("\t\t\ttype : \"POST\"\r\n");
      out.write("\t\t\t,url: \"issue_data_prc.jsp\"\r\n");
      out.write("\t\t\t,timeout: 30000\r\n");
      out.write("\t\t\t,data : formData\r\n");
      out.write("\t\t\t,dataType : 'text'\r\n");
      out.write("\t\t\t,async: true\r\n");
      out.write("\t\t\t,success : function(data){\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tif(data != \"\"){\r\n");
      out.write("\t\t\t\t\tif(mode == \"update_multi\"){\r\n");
      out.write("\t\t\t\t\t\talert(\"이슈가 수정 되었습니다.\");\r\n");
      out.write("\t\t\t\t\t\twindow.close();\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t  }\r\n");
      out.write("\t\t\t,beforeSend : function(){}\t\t\t\r\n");
      out.write("\t\t\t});\t\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\talert('등록중입니다.. 잠시만 기다려주세요.');\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("//유효성 검사 선택된 값이 있으면 true를 리턴, 없으면 false를 리턴\r\n");
      out.write("function validation_check(id){\r\n");
      out.write("\tvar target = \"validation_\"+id;\r\n");
      out.write("\tvar chk = \"\";\r\n");
      out.write("\t\r\n");
      out.write("\t$(\".\"+target).each(function(index){\r\n");
      out.write("\t\tif($(this).is(\":checked\")){chk=\"true\";}\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\tif(chk.length > 1){return true;\r\n");
      out.write("\t}else{return false;}\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("//선택된 코드 값들을 수정하기 위한 값으로 세팅\r\n");
      out.write("function settingTypeCode(){\r\n");
      out.write("\t//라디오 버튼 type값 세팅\r\n");
      out.write("\tvar radio_id = null;\r\n");
      out.write("\t//선택박스 type값 세팅\r\n");
      out.write("\tvar select_id = null;\r\n");
      out.write("\t//유효성 검사\r\n");
      out.write("\t\r\n");
      out.write("\tradio_id = [1,2,3,9,21,31,5,51];\r\n");
      out.write("\tselect_id = [4,7];\r\n");
      out.write("\t\r\n");
      out.write("\tvar form = document.fSend;\r\n");
      out.write("\r\n");
      out.write("\tform.typeCode.value = '';\r\n");
      out.write("\tif(radio_id != null){\r\n");
      out.write("\t\tfor(var i=0; i < radio_id.length; i++){\r\n");
      out.write("\t\t\t$(\"[name=icType\"+radio_id[i]+\"]:radio\").each(function(){\r\n");
      out.write("\t\t\t\tif($(this).is(\":checked\")){\r\n");
      out.write("\t\t\t\t\tform.typeCode.value += form.typeCode.value=='' ? $(this).val() : '@'+$(this).val();\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\r\n");
      out.write("\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\tvar selectedVal;\r\n");
      out.write("\tif(select_id != null){\r\n");
      out.write("\t\tfor(var i=0; i < select_id.length; i++){\r\n");
      out.write("\t\t\tselectedVal =  $(\"[name=icType\"+select_id[i]+\"] option:selected\").val();\t\t\r\n");
      out.write("\t\t\tform.typeCode.value += form.typeCode.value=='' ? selectedVal : '@'+selectedVal;\r\n");
      out.write("\t\t}\t\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\treturn form.typeCode.value;\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<form name=\"fSend\" id=\"fSend\" action=\"issue_data_prc.jsp\" method=\"post\" onsubmit=\"return false;\">\r\n");
      out.write("<input name=\"mode\" id=\"mode\" type=\"hidden\" value=\"\">\r\n");
      out.write("<input name=\"id_seqs\" id=\"id_seqs\" type=\"hidden\" value=\"");
      out.print(issue_m_seq);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"typeCode\" value=\"\">\r\n");
      out.write("<input type=\"hidden\" name=\"chk_types\" value=\"\">\r\n");
      out.write("<input type=\"hidden\" id=\"p_seq\" name=\"p_seq\" value=\"\">\r\n");
      out.write("<div class=\"wrap_pop\">\r\n");
      out.write("\t<h2>이슈 멀티 수정</h2>\r\n");
      out.write("\t<a href=\"javascript:window.close();\" style=\"position:absolute;top:12px;right:15px\"><img src=\"/images/search/pop_tit_close.gif\" alt=\"창닫기\"></a>\r\n");
      out.write("\t<div class=\"ui_input_table\">\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"ui_input_table\">\r\n");
      out.write("\t\t<h3>정보분류 항목</h3>\r\n");
      out.write("\t\t<table summary=\"정보분류 항목\">\r\n");
      out.write("\t\t<caption>정보분류 항목</caption>\r\n");
      out.write("\t\t<colgroup>\r\n");
      out.write("\t\t<col style=\"width:130px\">\r\n");
      out.write("\t\t<col>\r\n");
      out.write("\t\t</colgroup>\r\n");
      out.write("\t\t<tbody>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t<th><span id=\"label_1\">구분</span></th><!-- 필수 속성인 경우 strong 태그 삽입 -->\r\n");
      out.write("\t\t<td>\r\n");

	num = 1;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	String cssNone = "";   
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);

      out.write("\r\n");
      out.write("\t\t\t<span class=\"comp\" id=\"typeCode1_");
      out.print(i);
      out.write("\" >\r\n");
      out.write("\t\t\t<input id=\"input_radio_01_0");
      out.print(num);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" class=\"validation_");
      out.print(num);
      out.write("\" name=\"icType");
      out.print(num);
      out.write("\" value='");
      out.print(icBean.getIc_type());
      out.write(',');
      out.print(icBean.getIc_code());
      out.write("' type=\"radio\" onclick=\"comboCheckType2(this,'");
      out.print(icBean.getIc_type());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(icBean.getIc_code());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(icBean.getIc_name());
      out.write("');\" ");
      out.print(checked);
      out.write(" />\r\n");
      out.write("\t\t\t<label for=\"input_radio_01_0");
      out.print(num);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write('"');
      out.write('>');
      out.print(icBean.getIc_name());
      out.write("</label>\r\n");
      out.write("\t\t\t</span>\t\r\n");
      out.write("\t\r\n");
}
      out.write("\r\n");
      out.write("\t\t\t<input type='hidden' name='focus_icType");
      out.print(num);
      out.write("' value=\"\" />\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t<th><span id=\"label_2\">정보구분</span></th><!-- 필수 속성인 경우 strong 태그 삽입 -->\r\n");
      out.write("\t\t<td>\r\n");

	num = 2;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	cssNone = "";   
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);

      out.write("\r\n");
      out.write("\t\t\t<span class=\"comp\" id=\"typeCode2_");
      out.print(i);
      out.write("\" >\r\n");
      out.write("\t\t\t<input id=\"input_radio_01_0");
      out.print(num);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" class=\"validation_");
      out.print(num);
      out.write("\" name=\"icType");
      out.print(num);
      out.write("\" value='");
      out.print(icBean.getIc_type());
      out.write(',');
      out.print(icBean.getIc_code());
      out.write("' type=\"radio\" onclick=\"comboCheckType2(this,'");
      out.print(icBean.getIc_type());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(icBean.getIc_code());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(icBean.getIc_name());
      out.write("');\" ");
      out.print(checked);
      out.write(" />\r\n");
      out.write("\t\t\t<label for=\"input_radio_01_0");
      out.print(num);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write('"');
      out.write('>');
      out.print(icBean.getIc_name());
      out.write("</label>\r\n");
      out.write("\t\t\t</span>\t\r\n");
      out.write("\t\r\n");
}
      out.write("\r\n");
      out.write("\t\t\t<input type='hidden' name='focus_icType");
      out.print(num);
      out.write("' value=\"\" />\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<!--  2death 정보속성 값 -->\r\n");
      out.write("\t\t<tr style=\"display: none;\" id=\"tr_icType");
      out.print(num);
      out.write("\">\r\n");
      out.write("\t\t<th><span id=\"label_21\"></span><input type='hidden' name='focus_icType21' value=\"\" /></th>\r\n");
      out.write("\t\t<td id=\"td_icType");
      out.print(num);
      out.write("\"></td>\r\n");
      out.write("\t\t</tr>\t\t\r\n");
      out.write("\t\t<!--  2death 정보속성 값 -->\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t<th><span id=\"label_5\">실국</span></th><!-- 필수 속성인 경우 strong 태그 삽입 -->\r\n");
      out.write("\t\t<td>\r\n");

	num = 5;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	cssNone = "";   
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		int i_ = i*100;

      out.write("\r\n");
      out.write("\t\t\t<span class=\"comp\" id=\"typeCode5_");
      out.print(i_);
      out.write("\" >\r\n");
      out.write("\t\t\t<input id=\"input_radio_01_0");
      out.print(num);
      out.write('_');
      out.write('0');
      out.print(i_);
      out.write("\" class=\"validation_");
      out.print(num);
      out.write("\" name=\"icType");
      out.print(num);
      out.write("\" value='");
      out.print(icBean.getIc_type());
      out.write(',');
      out.print(icBean.getIc_code());
      out.write("' type=\"radio\" onclick=\"comboCheckType2(this,'");
      out.print(icBean.getIc_type());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(icBean.getIc_code());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(icBean.getIc_name());
      out.write("');\" ");
      out.print(checked);
      out.write(" />\r\n");
      out.write("\t\t\t<label for=\"input_radio_01_0");
      out.print(num);
      out.write('_');
      out.write('0');
      out.print(i_);
      out.write('"');
      out.write('>');
      out.print(icBean.getIc_name());
      out.write("</label>\r\n");
      out.write("\t\t\t</span>\t\r\n");
      out.write("\t\r\n");
}
      out.write("\r\n");
      out.write("\t\t\t<input type='hidden' name='focus_icType");
      out.print(num);
      out.write("' value=\"\" />\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<!--  2death 정보속성 값 -->\r\n");
      out.write("\t\t<tr style=\"display: none;\" id=\"tr_icType");
      out.print(num);
      out.write("\">\r\n");
      out.write("\t\t<th><span id=\"label_51\"></span><input type='hidden' name='focus_icType51' value=\"\" /></th>\r\n");
      out.write("\t\t<td id=\"td_icType");
      out.print(num);
      out.write("\"></td>\r\n");
      out.write("\t\t</tr>\t\t\r\n");
      out.write("\t\t<!--  2death 정보속성 값 -->\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t<th><span id=\"label_3\">대분류</span></th><!-- 필수 속성인 경우 strong 태그 삽입 -->\r\n");
      out.write("\t\t<td>\r\n");

	num = 3;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	cssNone = "";   
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		int i_ = i*100;

      out.write("\r\n");
      out.write("\t\t\t<span class=\"comp\" id=\"typeCode3_");
      out.print(i_);
      out.write("\" >\r\n");
      out.write("\t\t\t<input id=\"input_radio_01_0");
      out.print(num);
      out.write('_');
      out.write('0');
      out.print(i_);
      out.write("\" class=\"validation_");
      out.print(num);
      out.write("\" name=\"icType");
      out.print(num);
      out.write("\" value='");
      out.print(icBean.getIc_type());
      out.write(',');
      out.print(icBean.getIc_code());
      out.write("' type=\"radio\" onclick=\"comboCheckType2(this,'");
      out.print(icBean.getIc_type());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(icBean.getIc_code());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(icBean.getIc_name());
      out.write("');\" ");
      out.print(checked);
      out.write(" />\r\n");
      out.write("\t\t\t<label for=\"input_radio_01_0");
      out.print(num);
      out.write('_');
      out.write('0');
      out.print(i_);
      out.write('"');
      out.write('>');
      out.print(icBean.getIc_name());
      out.write("</label>\r\n");
      out.write("\t\t\t</span>\t\r\n");
      out.write("\t\r\n");
}
      out.write("\r\n");
      out.write("\t\t\t<input type='hidden' name='focus_icType");
      out.print(num);
      out.write("' value=\"\" />\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<!--  2death 정보속성 값 -->\r\n");
      out.write("\t\t<tr style=\"display: none;\" id=\"tr_icType");
      out.print(num);
      out.write("\">\r\n");
      out.write("\t\t<th><span id=\"label_31\"></span><input type='hidden' name='focus_icType31' value=\"\" /></th>\r\n");
      out.write("\t\t<td id=\"td_icType");
      out.print(num);
      out.write("\"></td>\r\n");
      out.write("\t\t</tr>\t\t\r\n");
      out.write("\t\t<!--  2death 정보속성 값 -->\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t<th><span id=\"label_9\">성향</span></th>\r\n");
      out.write("\t\t<td>\r\n");

	num = 9;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);

      out.write("  \r\n");
      out.write("\t\t\t<span class=\"comp\">\r\n");
      out.write("\t\t\t<input id=\"input_radio_01_0");
      out.print(num);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write("\" class=\"validation_");
      out.print(num);
      out.write("\" name=\"icType");
      out.print(num);
      out.write("\" value='");
      out.print(icBean.getIc_type());
      out.write(',');
      out.print(icBean.getIc_code());
      out.write("' onclick=\"comboCheck(this);\" type=\"radio\">\r\n");
      out.write("\t\t\t<label for=\"input_radio_01_0");
      out.print(num);
      out.write('_');
      out.write('0');
      out.print(i);
      out.write('"');
      out.write('>');
      out.print(icBean.getIc_name());
      out.write("</label>\r\n");
      out.write("\t\t\t</span>\r\n");
}
      out.write("\r\n");
      out.write("\t\t\t<input type='hidden' name='focus_icType");
      out.print(num);
      out.write("' value=\"\" />\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t");
      out.write("\r\n");
      out.write("\t\t\r\n");

	num = 4;

      out.write("\t\t\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t<th><span id=\"label_4\">주요이슈</span></th>\r\n");
      out.write("\t\t<td>\r\n");
      out.write("\t\t\t<select id=\"input_select_01_");
      out.print(num);
      out.write("\" class=\"validation_");
      out.print(num);
      out.write("\" name=\"icType");
      out.print(num);
      out.write("\" style=\"min-width:180px\">\r\n");
      out.write("\t\t\t<option value='' >선택하세요</option>\r\n");


	List type4List = icm.getCodeList("4","","DESC");
	HashMap map = new HashMap();
	for (int i = 0; i < type4List.size(); i++) {
		map = (HashMap) type4List.get(i);
		if(!"0".equals(map.get("IC_CODE")+"") ){
		checkedValue = "";
		selected="";
		if("update".equals(mode)){
			checkedValue = icm.getTypeCodeVal(idBean.getArrCodeList(),4); //selectbox 
			if(checkedValue.equals(map.get("IC_TYPE")+","+map.get("IC_CODE"))){
				selected = "selected=selected";
			}else{
				selected = "";
			}
		}

      out.write("  \t\t\t\r\n");
      out.write("\t\t\t<option value='");
      out.print(map.get("IC_TYPE"));
      out.write(',');
      out.print(map.get("IC_CODE"));
      out.write('\'');
      out.write(' ');
      out.print(selected);
      out.write(' ');
      out.write('>');
      out.print(map.get("IC_NAME"));
      out.write("</option>\r\n");
}}
      out.write("\t\t\t\r\n");
      out.write("\t\t\t</select><label for=\"input_select_01_");
      out.print(num);
      out.write("\" class=\"invisible\">주요이슈</label>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t\r\n");

	num = 7;

      out.write("\t\t\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t<th><span id=\"label_7\">보도자료</span></th>\r\n");
      out.write("\t\t<td>\r\n");
      out.write("\t\t\t<select id=\"input_select_01_");
      out.print(num);
      out.write("\" class=\"validation_");
      out.print(num);
      out.write("\" name=\"icType");
      out.print(num);
      out.write("\" style=\"min-width:180px\">\r\n");
      out.write("\t\t\t<option value='' >선택하세요</option>\r\n");


	List type7List = icm.getCodeList("7","","DESC");
	map = new HashMap();
	for (int i = 0; i < type7List.size(); i++) {
		map = (HashMap) type7List.get(i);
		if(!"0".equals(map.get("IC_CODE")+"") ){
		checkedValue = "";
		selected="";
		if("update".equals(mode)){
			checkedValue = icm.getTypeCodeVal(idBean.getArrCodeList(),7); //selectbox 
			if(checkedValue.equals(map.get("IC_TYPE")+","+map.get("IC_CODE"))){
				selected = "selected=selected";
			}else{
				selected = "";
			}
		}

      out.write("  \t\t\t\r\n");
      out.write("\t\t\t<option value='");
      out.print(map.get("IC_TYPE"));
      out.write(',');
      out.print(map.get("IC_CODE"));
      out.write('\'');
      out.write(' ');
      out.print(selected);
      out.write(' ');
      out.write('>');
      out.write('(');
      out.print(map.get("IC_REGDATE"));
      out.write(") - ");
      out.print(map.get("IC_NAME"));
      out.write("</option>\r\n");
}}
      out.write("\t\t\t\r\n");
      out.write("\t\t\t</select><label for=\"input_select_01_");
      out.print(num);
      out.write("\" class=\"invisible\">주요이슈</label>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t\t</tr>\t\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t<!-- 연관 키워드 -->\r\n");
      out.write("\t\t<!-- <tr id=\"tr_relationKey\">\r\n");
      out.write("\t\t\t<th><span id=\"label_keyword\">연관키워드</span></th>\r\n");
      out.write("\t\t\t<td>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<input id=\"txt_relationKey\" type=\"text\" class=\"h24\" style=\"width:200px\" onkeypress=\"javascript:if(event.keyCode == 13){addKeyword();}\"><label for=\"txt_relationKey\" class=\"invisible\">연관 키워드 입력</label>\r\n");
      out.write("\t\t\t\t<button id=\"btn_key_add\" type=\"button\" class=\"btn_add\" onclick=\"addKeyword();\"></button>\r\n");
      out.write("\t\t\t\t<ul class=\"keyword_list\" id=\"tb_keywordList\"></ul>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr> -->\r\n");
      out.write("\t\t<!-- 연관 키워드 END -->\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t</tbody>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"botBtns\">\r\n");
      out.write("\t\t<a href=\"javascript:save_issue('");
      out.print(mode);
      out.write("');\"><img src=\"/images/admin/member/btn_save2.gif\" alt=\"저장\"></a>\r\n");
      out.write("\t\t<a href=\"javascript:window.close();\"><img src=\"/images/admin/member/btn_cancel.gif\" alt=\"취소\"></a>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("</form>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
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
