package org.apache.jsp.riskv3.issue;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.json.JSONObject;
import risk.json.JSONArray;
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

public final class new_005fpop_005fissueData_005fform_jsp extends org.apache.jasper.runtime.HttpJspBase
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
	List arrAddGroupBean = new ArrayList();  //수신자그룹 어레이
	
	AddressBookGroupBean addGroupBean = new AddressBookGroupBean();	
	IssueDataBean idBean = new IssueDataBean();
	IssueCodeBean icBean = new IssueCodeBean();
	MetaBean metaBean = new MetaBean();			
			
	String checked = null;
	String checkedValue = null;
	String selected = null;
	String mode = pr.getString("mode");
	String nowPage = pr.getString("nowPage");
	String subMode = pr.getString("subMode");
	String md_seq = pr.getString("md_seq");
	String child = pr.getString("child");
	int ic_seq = 0;
	
	//분류체계코드 (0:분류항목 포함, 1:분류항목 제외)
	icm.init(0);
	
	//연관키워드 관련 
	ArrayList relationKey = null;
	ArrayList relationKeyAll = null;
	int key_seq = 0;
	int issue_seq = 0;
	
	String k_xp = "";
	String km_TypeCode = "";
	
	String pseqVal = "";
	//모드에 따른 분기
	if(mode.equals("insert")){
		metaBean = metaMgr.getMetaData(md_seq);
		ic_seq = icm.getSiteMapCode(metaBean.getSg_seq());  //사이트 이슈 코드 맵핑 정보 코드 시퀀스로 가져옴
		k_xp = metaMgr.getKeywordXP(md_seq);
		km_TypeCode = metaMgr.getKeyword_Map(md_seq);
		JSONArray sb_seq = metaMgr.getSb_seq_Map(); 
		JSONObject obj = null;
		for(int i=0 ; i<sb_seq.length() ; i++){
			obj = new JSONObject();
			obj = (JSONObject)sb_seq.get(i);
			if(obj.getString("SB_SEQ").equals(metaBean.getSb_seq())){
				km_TypeCode = "3,"+obj.getString("TYPE3")+"@"+"5,"+obj.getString("TYPE5")+"@"+"31,"+obj.getString("TYPE31")+"@"+"51,"+obj.getString("TYPE51");
			}
		}
		
		
	}else if(mode.equals("update")){
		//이슈 정보
		idBean = issueMgr.getIssueDataBean(md_seq);
		//연관 키워드 정보
	    relationKey = issueMgr.getRelationKey(idBean.getId_seq());
	    key_seq =  relationKey.size();
	    
	    pseqVal = issueMgr.getPressIssueData(idBean.getId_seq());
		
	}
	ArrayList arrCodeList = new ArrayList();
	arrCodeList = idBean.getArrCodeList();
	
	//이슈데이터 등록 관련
	IssueMgr isMgr = new IssueMgr();
	IssueBean isBean = new IssueBean();	   	   	
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	
	//수신자 그룹
	arrAddGroupBean = abDao.getAdressBookGroup();
	Iterator it = arrAddGroupBean.iterator();
	
	//get할려는 IC_TYPE값을 num에 세팅한다.
	int num = 0;
	String selectedValue="";
	
	//자동롤링용 연관키워드 한줄로 만들기
	  relationKeyAll = issueMgr.getRelationKey();
	  String streamKey = "";
	  for(int i =0; i < relationKeyAll.size(); i++){
	    if(streamKey.equals("")){
	      streamKey = (String)relationKeyAll.get(i);
	    }else{
	      streamKey += "," + (String)relationKeyAll.get(i);
	    }
	  }
	  
	  ArrayList IssueArrayList = null;
	  
	  String issueKey = "";
	  IssueArrayList = icm.getCodeList("4","","DESC");
	  for(int i =0; i < IssueArrayList.size(); i++){
		  Map map = new HashMap();
	      map = (HashMap)IssueArrayList.get(i);
	      if(issueKey.equals("")){	    	
	    	issueKey = map.get("IC_CODE")+"."+map.get("IC_NAME");
	      }else{
	    	issueKey += "," +map.get("IC_CODE")+"."+map.get("IC_NAME");
	      }
	  }
	  
	/* ArrayList pressList = new ArrayList();
	pressList = isMgr.getPressList(); */
	

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
      out.write("\t.ui_input_table > table > tbody > tr > th span{padding-left:6px;background:url(../../images/common/icon_dot.gif) no-repeat left 8px}\r\n");
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
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(SS_URL);
      out.write("css/design.css\">\r\n");
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
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(SS_URL);
      out.write("js/jquery.multi_selector.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(SS_URL);
      out.write("js/design.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/spin.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("var key_seq = ");
      out.print(key_seq);
      out.write(";\r\n");
      out.write("var streamKey = '");
      out.print(streamKey);
      out.write("'; \r\n");
      out.write("var issue_seq = ");
      out.print(issue_seq);
      out.write(";\r\n");
      out.write("var issueKey = '");
      out.print(issueKey);
      out.write("';\r\n");
      out.write("var km_TypeCode = '");
      out.print(km_TypeCode);
      out.write("';\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//death2인 코드 값들을 불러 온다.\r\n");
      out.write("//type : 부모 속성의 타입값 - IC_PTYPE\r\n");
      out.write("//code : 부모 속성의 코드값 - IC_PCODE\r\n");
      out.write("//name : 부모 속성의 이름 - IC_NAME \r\n");
      out.write("function getTypeCodeDeath2(type, code, name){\r\n");
      out.write("\tvar icPtype = type;\r\n");
      out.write("\tvar icPcode = code;\r\n");
      out.write("\tvar icPName = name;\r\n");
      out.write("\t\r\n");
      out.write("\t$(\"#tr_icType\"+icPtype+\"1\").css(\"display\",\"\");\r\n");
      out.write("\t\r\n");
      out.write("\tvar icon = \"\";\r\n");
      out.write("\t\r\n");
      out.write("\tif(type == \"5\"){\r\n");
      out.write("\t\ticon = \"&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>\";\t\t\r\n");
      out.write("\t\ticPName = \"부서\";\r\n");
      out.write("\t}else if(type == \"3\"){\r\n");
      out.write("\t\ticon = \"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>\";\r\n");
      out.write("\t\ticPName = \"소분류\";\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tif(type == \"5\" || type == \"3\"){\r\n");
      out.write("\t\t$(\"#label_\"+icPtype+\"1\").html(icon+icPName+\"<strong>*</strong>\");\t\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\t$(\"#label_\"+icPtype+\"1\").html(icon+icPName+\"<strong></strong>\");\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tvar checked= \"\"; \r\n");
      out.write("\t$.ajax({\r\n");
      out.write("\t\ttype:'POST',\r\n");
      out.write("\t\turl:\"getJsonData.jsp\",\r\n");
      out.write("\t\tdataType:'json',\r\n");
      out.write("\t\tdata:{ic_Ptype:icPtype, ic_Pcode:icPcode, flag: \"2death\"},\r\n");
      out.write("\t\tsuccess:function(data){\r\n");
      out.write("\t\t\tvar innerSpan = \"\";\r\n");
      out.write("\t\t\tvar num = \"\";\r\n");
      out.write("\t\t\t$.each(data.list, function(index){\r\n");
      out.write("\r\n");
      out.write("\t\t\t\tnum = data.list[index].icType;\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tif(data.list.length==1){\r\n");
      out.write("\t\t\t\t\tchecked = \"checked\";\r\n");
      out.write("\t\t\t\t\t$(\"[name=focus_icType\"+num+\"]\").val(data.list[index].icType+\",\"+data.list[index].icCode);\r\n");
      out.write("\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\tchecked = \"\";\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tif(num == \"51\"){\r\n");
      out.write("\t\t\t\t\tinnerSpan += \"<span class='comp'><input id='input_radio_01_\"+num+\"_\"+index+\"' class='validation_\"+num+\"' name='icType\"+num+\"' onclick='comboCheckManual(this);' value='\"+data.list[index].icType+\",\"+data.list[index].icCode+\"' type='radio' \"+checked+\" />\"\r\n");
      out.write("\t\t\t\t\t+\"<label for='input_radio_01_\"+num+\"_\"+index+\"'>\"+data.list[index].icName+\"</label></span>\"\r\n");
      out.write("\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\tinnerSpan += \"<span class='comp'><input id='input_radio_01_\"+num+\"_\"+index+\"' class='validation_\"+num+\"' name='icType\"+num+\"' onclick='comboCheck(this);' value='\"+data.list[index].icType+\",\"+data.list[index].icCode+\"' type='radio' \"+checked+\" />\"\r\n");
      out.write("\t\t\t\t\t+\"<label for='input_radio_01_\"+num+\"_\"+index+\"'>\"+data.list[index].icName+\"</label></span>\"\t\r\n");
      out.write("\t\t\t\t}\t\t\t\t\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\tinnerSpan += \"<input type='hidden' name='focus_icType\"+num+\"' value='' />\";\r\n");
      out.write("\t\t\t$(\"#td_icType\"+icPtype+\"1\").html(innerSpan);\r\n");
      out.write("\t\t\t\r\n");
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
      out.write("function getMapingTypeCodeDeath2(type, code, two_type , two_code , name){\r\n");
      out.write("\tvar icPtype = type;\r\n");
      out.write("\tvar icPcode = code;\r\n");
      out.write("\tvar icPName = name;\r\n");
      out.write("\t\r\n");
      out.write("\t$(\"#tr_icType\"+icPtype+\"1\").css(\"display\",\"\");\r\n");
      out.write("\t$(\"#label_\"+icPtype+\"1\").html(icPName+\"<strong>*</strong>\");\r\n");
      out.write("\t\r\n");
      out.write("\tvar icon = \"\";\r\n");
      out.write("\t\r\n");
      out.write("\tif(type == \"5\"){\r\n");
      out.write("\t\ticon = \"&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>\";\t\t\r\n");
      out.write("\t\ticPName = \"부서\";\r\n");
      out.write("\t}else if(type == \"3\"){\r\n");
      out.write("\t\ticon = \"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>\";\r\n");
      out.write("\t\ticPName = \"소분류\";\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tif(type == \"5\" || type == \"3\"){\r\n");
      out.write("\t\t$(\"#label_\"+icPtype+\"1\").html(icon+icPName+\"<strong>*</strong>\");\t\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\t$(\"#label_\"+icPtype+\"1\").html(icon+icPName+\"<strong></strong>\");\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tvar checked= \"\"; \r\n");
      out.write("\t$.ajax({\r\n");
      out.write("\t\ttype:'POST',\r\n");
      out.write("\t\turl:\"getJsonData.jsp\",\r\n");
      out.write("\t\tdataType:'json',\r\n");
      out.write("\t\tdata:{ic_Ptype:icPtype, ic_Pcode:icPcode, flag: \"2death\"},\r\n");
      out.write("\t\tsuccess:function(data){\r\n");
      out.write("\t\t\tvar innerSpan = \"\";\r\n");
      out.write("\t\t\tvar num = \"\";\r\n");
      out.write("\t\t\t$.each(data.list, function(index){\r\n");
      out.write("\r\n");
      out.write("\t\t\t\tnum = data.list[index].icType;\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tif(data.list[index].icCode == two_code){\r\n");
      out.write("\t\t\t\t\tchecked = \"checked\";\r\n");
      out.write("\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\tchecked = \"\";\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tinnerSpan += \"<span class='comp'><input id='input_radio_01_\"+num+\"_\"+index+\"' class='validation_\"+num+\"' name='icType\"+num+\"' onclick='comboCheckManual(this);' value='\"+data.list[index].icType+\",\"+data.list[index].icCode+\"' type='radio' \"+checked+\" />\"\r\n");
      out.write("\t\t\t\t+\"<label for='input_radio_01_\"+num+\"_\"+index+\"'>\"+data.list[index].icName+\"</label></span>\"\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\tinnerSpan += \"<input type='hidden' name='focus_icType\"+num+\"' value='' />\";\r\n");
      out.write("\t\t\t$(\"#td_icType\"+icPtype+\"1\").html(innerSpan);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t,error:function(){}\r\n");
      out.write("\t\r\n");
      out.write("\t});\r\n");
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
      out.write("//선택된 코드 값들을 insert하기 위한 값으로 세팅\r\n");
      out.write("function settingTypeCode(){\r\n");
      out.write("\t//라디오 버튼 type값 세팅\r\n");
      out.write("\tvar radio_id = null;\r\n");
      out.write("\t//선택박스 type값 세팅\r\n");
      out.write("\tvar select_id = null;\r\n");
      out.write("\t//유효성 검사\r\n");
      out.write("\t\r\n");
      out.write("\tradio_id = [1,2,5,51,3,31,9,6,11,21];\r\n");
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("var regChk = false;//이미 등록중인지 확인하기위한 값\r\n");
      out.write("//이슈관리 저장 함수\r\n");
      out.write("function save_issue(mode, event){\r\n");
      out.write("\t\r\n");
      out.write("\t//유효성 검사\r\n");
      out.write("\tvar check;\r\n");
      out.write("\t//유효성 검사\r\n");
      out.write("\tcheck = [1,5,51,3,31,9,6];\r\n");
      out.write("\t\r\n");
      out.write("\tvar targetClass = \"\";\r\n");
      out.write("\tfor(var i = 0; i < check.length; i++){\r\n");
      out.write("\t\tif(!validation_check(check[i])){\r\n");
      out.write("\t\t\tvar label = $(\"#label_\"+check[i]).text();\r\n");
      out.write("\t\t\talert(label.trim()+\"을(를) 선택하세요.\");\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tvar pseq = $(\"[name=typeCode10_pseq] option:selected\").val();\r\n");
      out.write("\tif(pseq != ''){\r\n");
      out.write("\t\t$(\"#p_seq\").val(pseq);\t\r\n");
      out.write("\t}\r\n");
      out.write("\t$(\"#p_seq\").val(pseq);\r\n");
      out.write("\t\r\n");
      out.write("\t //연관키워드\r\n");
      out.write("    if(document.getElementById(\"tr_relationKey\").style.display != 'none'){\r\n");
      out.write("     var obj_td = null;\r\n");
      out.write("     var names = '';\r\n");
      out.write("     var html = '';\r\n");
      out.write("     var nameIdx = -1;\r\n");
      out.write("     \r\n");
      out.write("     \r\n");
      out.write("     $(\"#tb_keywordList\").find(\"li\").each(function(){\r\n");
      out.write("    \t html = $(this).find(\"div\").find(\"span\").text();\r\n");
      out.write("    \tif(names == '') {\r\n");
      out.write("    \t\tnames =\thtml.trim();\r\n");
      out.write("    \t}else{\r\n");
      out.write("    \t\tnames += ',' + html.trim();\r\n");
      out.write("    \t}\r\n");
      out.write("     });\r\n");
      out.write("     \r\n");
      out.write("     /* if(!names || names == '' || names.length < 1) {\r\n");
      out.write("       alert(\"연관 키워드를 입력해 주세요.\");\r\n");
      out.write("       $(\"#txt_relationKey\").focus();\r\n");
      out.write("       \r\n");
      out.write("       return;\r\n");
      out.write("     } */\r\n");
      out.write("     \r\n");
      out.write("     $(\"[name=keyNames]\").val(names);\r\n");
      out.write("     \r\n");
      out.write("    }\r\n");
      out.write("\t\r\n");
      out.write("\t//타입별 값 세팅\r\n");
      out.write("\tvar typeCode = settingTypeCode();\r\n");
      out.write("\t//mode에 따라 form submit\r\n");
      out.write("\tvar f = document.fSend;\r\n");
      out.write("\tif(!regChk){\r\n");
      out.write("\t\tregChk = true;\r\n");
      out.write("\t\tf.typeCode.value = typeCode;\r\n");
      out.write("\t\tf.mode.value = mode;\r\n");
      out.write("\t\t\r\n");
      out.write("        var formData = $(\"[name=fSend]\").serialize();\r\n");
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
      out.write("\t\t\t\t\tif(mode == \"insert\"){\r\n");
      out.write("\t\t\t\t\t\tif(data.indexOf('a') > 0){\r\n");
      out.write("\t\t\t\t\t\t\talert(\"유사가 이미 등록 되었습니다. 자동 등록 될 예정입니다.\");\r\n");
      out.write("\t\t\t\t\t\t\twindow.close();\r\n");
      out.write("\t\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\t\talert(\"이슈가 등록 되었습니다.\");\r\n");
      out.write("\t\t\t\t\t\t\t//$(opener.document).find(\"#issue_menu_icon\"+data.trim()).attr(\"src\", \"../../images/search/btn_manage_on.gif\");\r\n");
      out.write("\t\t\t\t\t\t\t//$(opener.document).find(\"#issue_menu_icon\"+data.trim());\r\n");
      out.write("\t\t\t\t\t\t\t//imgId = \"#issue_menu_icon\" + data.trim();\r\n");
      out.write("\t\t\t\t\t\t\timgId = \"#issue_menu_icon\" + $(\"[name=md_seq]\").val();\r\n");
      out.write("\t\t\t\t\t\t\t$(imgId, opener.document).attr(\"src\", \"../../images/search/btn_manage_on.gif\");\r\n");
      out.write("\t\t\t\t\t\t\twindow.close();\r\n");
      out.write("\t\t\t\t\t\t}\t\r\n");
      out.write("\t\t\t\t\t}else if(mode == \"insert&mail\"){\r\n");
      out.write("\t\t\t\t\t\talert(\"이슈 등록 및 메일이 발송 되었습니다.\");\r\n");
      out.write("\t\t\t\t\t\twindow.close();\r\n");
      out.write("\t\t\t\t\t}else if(mode == \"update\"){\r\n");
      out.write("\t\t\t\t\t\talert(\"이슈가 수정 되었습니다.\");\r\n");
      out.write("\t\t\t\t\t\twindow.close();\r\n");
      out.write("\t\t\t\t\t}else if(mode == \"mail\"){\r\n");
      out.write("\t\t\t\t\t\talert(\"메일이 발송되었습니다.\");\r\n");
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
      out.write("\r\n");
      out.write("//체크해제\r\n");
      out.write("function comboCheckType2(obj,icType,icCode,icName){\r\n");
      out.write("\t\tvar icPtype = icType;\r\n");
      out.write("\t\tvar f = document.fSend;\r\n");
      out.write("\t\tfocusObj = eval('f.focus_'+ obj.name);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(focusObj.value == obj.value){\r\n");
      out.write("\t\t\tobj.checked = false;\r\n");
      out.write("\t\t\tfocusObj.value = '';\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t$(\"#tr_icType\"+icPtype+\"1\").css(\"display\", \"\");\r\n");
      out.write("\t\t\t$(\"[name=icType\"+icPtype+\"1]\").attr(\"checked\", false);\r\n");
      out.write("\t\t\t$(\"[name=focus_icType\"+icPtype+\"1]\").val('');\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tgetTypeCodeDeath2(icType,icCode,icName);\r\n");
      out.write("\t\t\tfocusObj.value = obj.value;\r\n");
      out.write("\t\t}\r\n");
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
      out.write("}\t\r\n");
      out.write("\r\n");
      out.write("function comboCheckManual(obj){\r\n");
      out.write("\tvar type5 = $(\"[name=icType5]:checked\").val().split(\",\")[1];\r\n");
      out.write("\tvar type51 = $(\"[name=icType51]:checked\").val().split(\",\")[1];\r\n");
      out.write("\t\r\n");
      out.write("\tvar f = document.fSend;\r\n");
      out.write("\t\r\n");
      out.write("\tif(type51 != '77'){\r\n");
      out.write("\t$.ajax({\r\n");
      out.write("\t\ttype:'POST',\r\n");
      out.write("\t\turl:\"getJsonData.jsp\",\r\n");
      out.write("\t\tdataType:'json',\r\n");
      out.write("\t\tdata:{\"type5\":type5, \"type51\":type51, flag: \"manual\"},\r\n");
      out.write("\t\tsuccess:function(data){\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tif(data != null){\r\n");
      out.write("\t\t\t\tvar type3 = \"input_radio_01_03_0\"+data.type3;\r\n");
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
      out.write("\t\t\t\t}, 100);\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\t$(\"[name=icType3]\").attr(\"checked\",false);\r\n");
      out.write("\t\t\t\t$(\"[name=icType31]\").attr(\"checked\",false);\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tif(f.mode.value == 'insert'){\r\n");
      out.write("\t\t\t\t$(\"#tr_icType3\").css(\"display\",\"\");\r\n");
      out.write("\t\t\t\t$(\"#tr_icType31\").css(\"display\",\"\");\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t,error:function(){}\r\n");
      out.write("\t\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\t$(\"[name=icType3]\").attr(\"checked\",false);\r\n");
      out.write("\t\t$(\"[name=icType31]\").attr(\"checked\",false);\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//연관키워드 추가\r\n");
      out.write("function addKeyword() {\r\n");
      out.write("  var f = document.fSend;\r\n");
      out.write("  var keyword = f.txt_relationKey.value.trim();\r\n");
      out.write("\r\n");
      out.write("  if(keyword != ''){\r\n");
      out.write("    key_seq++;\r\n");
      out.write("    \r\n");
      out.write("    var AddHtml = \"<li id='td_keyword_\" + key_seq +\"'><div class='item'><span>\" + keyword + \"</span><button type='button' onclick=\\\"delKeyword('\"+ key_seq +\"');\\\" class='btn_del' title='키워드 삭제'>삭제</button></div></li>\";\r\n");
      out.write("    $(\"#tb_keywordList\").append(AddHtml);\r\n");
      out.write("    \r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write("  f.txt_relationKey.value = '';\r\n");
      out.write("}\r\n");
      out.write("//연관키워드 삭제\r\n");
      out.write("function delKeyword(idx){\r\n");
      out.write("\t\r\n");
      out.write("\t$(\"#td_keyword_\"+idx).remove();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function settingPseq(){\r\n");
      out.write("\tvar pseq = $(\"[name=typeCode10_pseq] option:selected\").val();\r\n");
      out.write("\tif(pseq != ''){\r\n");
      out.write("\t\t$(\"#p_seq\").val(pseq);\t\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("$(function(){\r\n");
      out.write("\t\r\n");
      out.write("\tvar f = document.fSend;\r\n");
      out.write("\t\r\n");
      out.write("\tif(km_TypeCode != \"\"){\r\n");
      out.write("\t\tvar type3 = $(\"[name=icType3]:checked\").val();\r\n");
      out.write("\t\tvar type31 = $(\"[name=icType31]:checked\").val();\r\n");
      out.write("\t\tvar type5 = $(\"[name=icType5]:checked\").val();\r\n");
      out.write("\t\tvar type51 = $(\"[name=icType51]:checked\").val();\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(type3 != \"\" && type31 != \"\"){\r\n");
      out.write("\t\t\tgetMapingTypeCodeDeath2(type3.split(\",\")[0], type3.split(\",\")[1], type31.split(\",\")[0] , type31.split(\",\")[1] , \"소분류\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(type5 != \"\" && type51 != \"\"){\r\n");
      out.write("\t\t\tgetMapingTypeCodeDeath2(type5.split(\",\")[0], type5.split(\",\")[1], type51.split(\",\")[0] , type51.split(\",\")[1] , \"부서\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\tif(f.mode.value == 'insert'){\r\n");
      out.write("\t\t\t$(\"#tr_icType3\").css(\"display\",\"none\");\r\n");
      out.write("\t\t\t$(\"#tr_icType31\").css(\"display\",\"none\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\r\n");
      out.write("\t$('#txt_issueKey').autocomplete(issueKey.split(','), {matchContains: true});\r\n");
      out.write("\t\r\n");
      out.write("\t$('#txt_relationKey').autocomplete(streamKey.split(','));\r\n");
      out.write("\t\r\n");
      out.write("\t$(\"[name=icType1]\").click();\r\n");
      out.write("\t\r\n");
      out.write("\t/* $(\"[name=icType10]\").click(function(){\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif( $(\"[name=focus_icType10]\").val() == \"10,1\"){\r\n");
      out.write("\t\t\t$(\"[name=typeCode10_pseq]\").css(\"display\", '');\r\n");
      out.write("\t\t\tvar pseq = $(\"[name=typeCode10_pseq] option:selected\").val();\r\n");
      out.write("\t\t\tif(pseq != ''){\r\n");
      out.write("\t\t\t\t$(\"#p_seq\").val(pseq);\t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t$(\"[name=typeCode10_pseq]\").css(\"display\", 'none');\r\n");
      out.write("\t\t\t$(\"#p_seq\").val('');\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}); */\r\n");
      out.write("\t\r\n");
      out.write("\t/* var mode = $(\"[name=mode]\").val();\r\n");
      out.write("\tif(mode != \"update\"){\r\n");
      out.write("\t\tvar type = [10,12];\r\n");
      out.write("\t\tvar order = [1,1];\r\n");
      out.write("\t\tfor(var i=0; i < type.length; i++){\r\n");
      out.write("\t\t\t$(\"[name=icType\"+type[i]+\"]\").eq(order[i]).prop(\"checked\", true);\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t$(\"[name=focus_icType\"+type[i]+\"]\").val( $(\"[name=icType\"+type[i]+\"]\").eq(order[i]).val() );\r\n");
      out.write("\t\t}\r\n");
      out.write("\t} */\r\n");
      out.write("\t\r\n");
      out.write("});\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<form name=\"fSend\" id=\"fSend\" action=\"issue_data_prc.jsp\" method=\"post\" onsubmit=\"return false;\">\r\n");
      out.write("<input name=\"mode\" id=\"mode\" type=\"hidden\" value=\"");
      out.print(mode);
      out.write("\"><!-- 모드 -->\r\n");
      out.write("<input name=\"subMode\" id=\"subMode\" type=\"hidden\" value=");
      out.print(subMode);
      out.write(">\r\n");
      out.write("<input type=\"hidden\" name=\"nowPage\" value=\"");
      out.print(nowPage );
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"tagGroupCode\" id=\"tagGroupCode\">\r\n");
      out.write("<input type=\"hidden\" name=\"regTagCode\" id=\"regTagCode\">\r\n");
      out.write("<input type=\"hidden\" id=\"keyNames\" name=\"keyNames\" value=\"\">\r\n");
      out.write("<input type=\"hidden\" id=\"p_seq\" name=\"p_seq\" value=\"\">\r\n");
if(mode.equals("insert")){ 
      out.write("\r\n");
      out.write("<input name=\"md_seq\" id=\"md_seq\" type=\"hidden\" value=\"");
      out.print(metaBean.getMd_seq());
      out.write("\"><!-- 기사번호 -->\r\n");
      out.write("<input name=\"s_seq\" id=\"s_seq\" type=\"hidden\" value=\"");
      out.print(metaBean.getS_seq());
      out.write("\"><!-- 사이트번호 -->\r\n");
      out.write("<input name=\"sg_seq\" id=\"sg_seq\" type=\"hidden\" value=\"");
      out.print(metaBean.getSg_seq());
      out.write("\"><!-- 사이트 그룹 -->\r\n");
      out.write("<input name=\"md_date\" id=\"md_date\" type=\"hidden\" value=\"");
      out.print(metaBean.getMd_date());
      out.write("\"><!-- 수십 시간-->\r\n");
      out.write("<input name=\"md_site_menu\" id=\"md_site_menu\" type=\"hidden\" value=\"");
      out.print(metaBean.getMd_same_count());
      out.write("\"><!-- 사이트 메뉴 -->\r\n");
      out.write("<input name=\"md_same_ct\" id=\"md_same_ct\" type=\"hidden\" value=\"");
      out.print(metaBean.getMd_same_count());
      out.write("\"><!-- 유사개수 -->\r\n");
      out.write("<input name=\"typeCode\" id=\"typeCode\" type=\"hidden\" value=\"\"><!-- 타입코드 -->\r\n");
      out.write("<input name=\"mailreceiver\" id=\"mailreceiver\" type=\"hidden\" value=\"\"><!--선택된 메일 수신자 -->\r\n");
      out.write("<input name=\"selItseq\" id=\"selItseq\" type=\"hidden\" value=\"\">\r\n");
      out.write("<input name=\"md_pseq\" id=\"md_pseq\" type=\"hidden\" value=\"");
      out.print(metaBean.getMd_pseq());
      out.write("\">\r\n");
      out.write("<input name=\"k_xp\" id=\"k_xp\" type=\"hidden\" value=\"");
      out.print(k_xp);
      out.write("\">\r\n");
      out.write("<input name=\"user_id\" type=\"hidden\" value=\"");
      out.print(metaBean.getUser_id() );
      out.write("\" />\r\n");
      out.write("<input name=\"user_nick\" type=\"hidden\" value=\"");
      out.print(metaBean.getUser_nick() );
      out.write("\" />\r\n");
      out.write("\r\n");
      out.write("<input name=\"blog_visit_count\" type=\"hidden\" value=\"");
      out.print(metaBean.getBlog_visit_count() );
      out.write("\" />\r\n");
      out.write("<input name=\"cafe_name\" type=\"hidden\" value=\"");
      out.print(metaBean.getCafe_name() );
      out.write("\" />\r\n");
      out.write("<input name=\"cafe_member_count\" type=\"hidden\" value=\"");
      out.print(metaBean.getCafe_member_count() );
      out.write("\" />\r\n");
      out.write("\r\n");
      out.write("<input name=\"d_seq\" type=\"hidden\" value=\"");
      out.print(metaBean.getD_seq() );
      out.write("\" /><!-- 문서번호 -->\r\n");
      out.write("\r\n");
}else if(mode.equals("update")){
      out.write("\r\n");
      out.write("<input name=\"id_seq\" id=\"id_seq\" type=\"hidden\" value=\"");
      out.print(idBean.getId_seq());
      out.write("\"><!-- 기사번호 -->\r\n");
      out.write("<input name=\"md_seq\" id=\"md_seq\" type=\"hidden\" value=\"");
      out.print(idBean.getMd_seq());
      out.write("\"><!-- 기사번호 -->\r\n");
      out.write("<input name=\"s_seq\" id=\"s_seq\" type=\"hidden\" value=\"");
      out.print(idBean.getS_seq());
      out.write("\"><!-- 사이트번호 -->\r\n");
      out.write("<input name=\"sg_seq\" id=\"sg_seq\" type=\"hidden\" value=\"");
      out.print(idBean.getSg_seq());
      out.write("\"><!-- 사이트 그룹 -->\r\n");
      out.write("<input name=\"md_date\" id=\"md_date\" type=\"hidden\" value=\"");
      out.print(idBean.getMd_date());
      out.write("\"><!-- 수십 시간-->\r\n");
      out.write("<input name=\"md_site_menu\" id=\"md_site_menu\" type=\"hidden\" value=\"");
      out.print(idBean.getMd_site_menu());
      out.write("\"><!-- 사이트 메뉴 -->\r\n");
      out.write("<input name=\"id_mailyn\" id=\"id_mailyn\" type=\"hidden\" value=\"");
      out.print(idBean.getId_mailyn());
      out.write("\"><!-- 사이트 메뉴 -->\r\n");
      out.write("<input name=\"typeCode\" id=\"typeCode\" type=\"hidden\" value=\"\"><!-- 타입코드 -->\r\n");
      out.write("<input name=\"mailreceiver\" id=\"mailreceiver\" type=\"hidden\" value=\"\"><!--선택된 메일 수신자 -->\r\n");
      out.write("<input name=\"selItseq\" id=\"selItseq\" type=\"hidden\" value=\"");
      out.print(idBean.getIt_seq());
      out.write("\"><!--선택된 it_seq -->\r\n");
      out.write("<input name=\"child\" id=\"child\" type=\"hidden\" value=\"");
      out.print(child);
      out.write("\" />\r\n");
      out.write("<input name=\"user_id\" type=\"hidden\" value=\"");
      out.print(idBean.getUser_id() );
      out.write("\" />\r\n");
      out.write("<input name=\"user_nick\" type=\"hidden\" value=\"");
      out.print(idBean.getUser_nick() );
      out.write("\" />\r\n");
      out.write("<input name=\"blog_visit_count\" type=\"hidden\" value=\"");
      out.print(idBean.getBlog_visit_count() );
      out.write("\" />\r\n");
      out.write("<input name=\"cafe_name\" type=\"hidden\" value=\"");
      out.print(idBean.getCafe_name() );
      out.write("\" />\r\n");
      out.write("<input name=\"cafe_member_count\" type=\"hidden\" value=\"");
      out.print(idBean.getCafe_member_count() );
      out.write("\" />\r\n");
      out.write("\r\n");
      out.write("<input name=\"d_seq\" type=\"hidden\" value=\"");
      out.print(idBean.getD_seq() );
      out.write("\" /><!-- 문서번호 -->\r\n");
      out.write("\r\n");
} 
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    \r\n");
if(!"multi".equals(mode)){ 
      out.write("\r\n");
      out.write("<!-- <div class=\"wrap\" style=\"width: 50%\"> -->\r\n");
      out.write("\r\n");
      out.write("\t");

	if( SS_MG_NO.equals("3") ||  SS_MG_NO.equals("9") || SS_MG_NO.equals("4")){
	
	
      out.write("\r\n");
      out.write("    <div class=\"ori_txts\" style=\"float:left;width:35%;position: fixed;\">\r\n");
      out.write("        \t<div class=\"in_con\">\r\n");
      out.write("\t\t\t");

		//	String contents ="";
		//	if ("insert".equals(mode)) {
		//		contents = metaBean.getMd_content().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		//	} else if ("update".equals(mode)) {
		//		contents = idBean.getId_content().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		//	}
		//	contents = contents.replaceAll("<div", "");
			
      out.write("\r\n");
      out.write("\t\t\t");

			if("insert".equals(mode)){
				out.println(metaBean.changeKeyColor("blue"));				 
			}else{
				String contents = "";
				contents = idBean.getId_content().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
				contents = contents.replaceAll("<div", "");
				out.println(contents);
			}
			
      out.write("\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"wrap_pop\" style=\"float:right;width:65%;\">\r\n");
      out.write("\t");

	}else{
      out.write("\r\n");
      out.write("\t\t\t<div class=\"wrap_pop\" >\r\n");
      out.write("\t");

	}
	 
}else{
      out.write("\t<div class=\"wrap_pop\" >\r\n");
      out.write("\t");

}

      out.write("    \r\n");
      out.write("    \r\n");
      out.write("<div class=\"wrap_pop\">\r\n");
      out.write("\t<h2>이슈등록</h2>\r\n");
      out.write("\t<a href=\"javascript:window.close();\" style=\"position:absolute;top:12px;right:15px\"><img src=\"/images/search/pop_tit_close.gif\" alt=\"창닫기\"></a>\r\n");
      out.write("\t<div class=\"ui_input_table\">\r\n");
      out.write("\t\t<table summary=\"기본정보\">\r\n");
      out.write("\t\t<caption>기본정보</caption>\r\n");
      out.write("\t\t<colgroup>\r\n");
      out.write("\t\t<col style=\"width:130px\">\r\n");
      out.write("\t\t<col>\r\n");
      out.write("\t\t<col style=\"width:130px\">\r\n");
      out.write("\t\t<col>\r\n");
      out.write("\t\t</colgroup>\r\n");
      out.write("\t\t<tbody>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t<th><span>제목</span></th>\r\n");
      out.write("\t\t<td colspan=\"3\"><input id=\"ui_input_00_00\" name=\"id_title\" type=\"text\" style=\"width:90%\" value=\"");
if(mode.equals("insert")){out.print(su.ChangeString(metaBean.getMd_title()));}else{out.print(su.ChangeString(idBean.getId_title()));}
      out.write("\"><label for=\"ui_input_00_00\" class=\"invisible\">제목 입력</label></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t<th><span>URL</span></th>\r\n");
      out.write("\t\t<td colspan=\"3\"><input id=\"ui_input_00_01\" name=\"id_url\" type=\"text\" style=\"width:90%\" value=\"");
if(mode.equals("insert")){out.print(metaBean.getMd_url());}else{out.print(idBean.getId_url());}
      out.write("\"><label for=\"ui_input_00_01\" class=\"invisible\">URL 입력</label></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t<th><span>사이트이름</span></th>\r\n");
      out.write("\t\t<td><input id=\"ui_input_00_02\" name=\"md_site_name\" type=\"text\" style=\"width:70%\" value=\"");
if(mode.equals("insert")){out.print(metaBean.getMd_site_name());}else{out.print(idBean.getMd_site_name());}
      out.write("\"><label for=\"ui_input_00_02\" class=\"invisible\">사이트이름 입력</label></td>\r\n");
      out.write("\t\t<th><span>수집원명</span></th>\r\n");
      out.write("\t\t<td><input id=\"ui_input_00_04\" name=\"md_menu_name\" type=\"text\" style=\"width:70%\" value=\"");
if(mode.equals("insert")){out.print(metaBean.getMd_site_menu());}else{out.print(idBean.getMd_site_menu());}
      out.write("\"><label for=\"ui_input_00_04\" class=\"invisible\">수집원명 입력</label></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t<th><span>정보분류시간</span></th>\r\n");
      out.write("\t\t<td><input id=\"ui_input_00_03\" name=\"id_regdate\" type=\"text\" style=\"width:70%\" value=\"");
if(mode.equals("insert")){out.print(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));}else{out.print(idBean.getFormatId_regdate("yyyy-MM-dd HH:mm:ss"));}
      out.write("\" readonly=\"readonly\"><label for=\"ui_input_00_03\" class=\"invisible\">정보분류시간 입력</label></td>\r\n");
      out.write("\t\t<th><span>정보종류</span></th>\r\n");
      out.write("\t\t<td >\r\n");
      out.write("\t\t\t<select id=\"ui_select_00_00\" name=\"md_type\" style=\"width:100px\">\r\n");
      out.write("\t\t\t<option value=\"1\" ");
if(mode.equals("insert")){if(metaBean.getMd_type().equals("1")){out.print("selected");}}else{if(idBean.getMd_type().equals("1")){out.print("selected");}}
      out.write(">언론</option>\r\n");
      out.write("\t\t\t<option value=\"2\" ");
if(mode.equals("insert")){if(Integer.parseInt(metaBean.getMd_type()) > 1){out.print("selected");}}else{if(idBean.getMd_type().equals("2")){out.print("selected");}}
      out.write(">개인</option>\r\n");
      out.write("\t\t\t</select><label for=\"ui_select_00_00\" class=\"invisible\">정보종류 선택</label>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t<th><span>정보내용</span></th>\r\n");
      out.write("\t\t<td colspan=\"3\"><textarea id=\"ui_textarea_00_00\"name=\"id_content\" style=\"width:90%;height:100px\">");
if(mode.equals("insert")){out.print(su.cutKey(su.ChangeString(metaBean.getMd_content()),"",300,""));}else{out.print(su.ChangeString(idBean.getId_content()));}
      out.write("</textarea><label for=\"ui_textarea_00_00\" class=\"invisible\">정보내용 입력</label></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t</tbody>\r\n");
      out.write("\t\t</table>\r\n");
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
      out.write("\t\t");

		int[] codeArry = {1,2,21,5,51,3,31,9,6};	//전체 이슈코드
		int[] twoDepthCode = {2,3,5};			//2depth 코드의 부모 코드
		int[] hiddenCode;
		if("insert".equals(mode)){
			hiddenCode = new int[]{21,31,51};			//기본 숨김 설정 코드	
		}else{
			hiddenCode = new int[]{21,3,31,51};			//기본 숨김 설정 코드
		}
		
		
		for(int codeNum = 0; codeNum < codeArry.length; codeNum++){
			num = codeArry[codeNum];
			arrIcBean = new ArrayList();
			arrIcBean = icm.GetType(num);
			icBean = (IssueCodeBean)arrIcBean.get(0);
			String styleDisplay = "";
			String cobocheck = "";
			if("insert".equals(mode)){
				for(int number : hiddenCode){
					if(number == num){
						styleDisplay ="none;";	
						break;
					}else{
						styleDisplay ="";
					}
				}
			}
			
			String star="<strong>*</strong>";
			//필수 체크 해제
			if(num == 2 || num == 21){
				star="";
			}
			
			String title = icBean.getIc_name();
			int indx = 1;
			if(num==21){
				title = "정보구분 상세";
				indx = 0;
			}else if(num==31){
				title = "소분류";
				indx = 0;
			}else if(num==51){
				title = "부서";
				indx = 0;
			}
			
			String icon = "";
			if(num == 3){
				icon = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>";
			}
			
			if("update".equals(mode)){
				if(num == 51){
					icon = "&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>";
				}else if(num == 31){
					icon = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>";
				}
			}
			
			
		
      out.write("\r\n");
      out.write("\t\t<tr id=\"tr_icType");
      out.print(num);
      out.write("\" style=\"display: ");
      out.print(styleDisplay);
      out.write("\" >\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<th><span id=\"label_");
      out.print(num);
      out.write('"');
      out.write('>');
      out.print(icon);
      out.print(title);
      out.print(star);
      out.write("</span></th><!-- 필수 속성인 경우 strong 태그 * 삽입 -->\r\n");
      out.write("\t\t<td id=\"td_icType");
      out.print(num);
      out.write("\" style=\"height:24px;line-height:24px\">\r\n");
      out.write("\t\t");

			for (int i = indx; i < arrIcBean.size(); i++) {
				int i_ = i;
				icBean = (IssueCodeBean) arrIcBean.get(i);
				for(int number : twoDepthCode){
					if(number == num){
						cobocheck ="comboCheckType2(this,"+icBean.getIc_type()+","+icBean.getIc_code()+",'"+icBean.getIc_name()+"');";
						break;
					}else{
						if(num==31){
							cobocheck = "comboCheckManual(this)";
						}else{
							cobocheck ="comboCheck(this);";	
						}						
					}
				}
				
				checkedValue = "";
				selected="";
				/**
				*	출처 자동 선택 시작
				**/
				 
				if("insert".equals(mode) && codeArry[codeNum] == 6 || codeArry[codeNum] == 7){
					
					if(codeArry[codeNum] == 6){
						if(metaBean.getMd_type().equals("1")){	//정보류가 언론인 경우 무조건 언론으로 찍히도록
							if(icBean.getIc_code() == 1){
								selected = "checked=checked";
								checkedValue = icBean.getIc_type()+","+icBean.getIc_code();
							}else{
								checked = "";
							}
						}else{
							if(ic_seq > 0){
								selected = icm.getTypeCodeValByKey(arrIcBean , ic_seq);
							}
							if(selected.equals( icBean.getIc_type()+","+icBean.getIc_code() )){
								selected = "checked=checked";
								checkedValue = icBean.getIc_type()+","+icBean.getIc_code();
							}else{
								checked = "";
							}
						}
					}
				}
				/**
				*	출처 자동 선택 끝
				**/
				
				if(num==3 || num==31 || num==5 || num==51){
					i_ = i_*100;
				}
				
				if("update".equals(mode)){
					//System.out.println( icBean.getIc_type()+","+icBean.getIc_code() );
					checkedValue = icm.getTypeCodeVal(idBean.getArrCodeList(),num);
					if(checkedValue.equals(icBean.getIc_type()+","+icBean.getIc_code())){
						selected = "checked=checked";
						selectedValue = icBean.getIc_type()+","+icBean.getIc_code();
						//icPcode = icBean.getIc_code();
					}else{
						selected = "";
					}
				}
				
				if("update".equals(mode) && (num==21 || num==31 || num == 51)){				
					String chk = "";
					if(num==21){
						chk = icm.getTypeCodeVal(idBean.getArrCodeList(), 2);	
					}else if(num==31){
						chk = icm.getTypeCodeVal(idBean.getArrCodeList(), 3);
					}else if(num==51){
						chk = icm.getTypeCodeVal(idBean.getArrCodeList(), 5);
					}
					
					if(chk.equals(icBean.getIc_ptype()+","+icBean.getIc_pcode())){
		
      out.write("\r\n");
      out.write("\t\t\t\t\t<span class=\"comp\" ");
if(num==10){
      out.write(" style=\"line-height:24px\" ");
} 
      out.write(">\r\n");
      out.write("\t\t\t\t\t<input id=\"input_radio_01_0");
      out.print(num);
      out.write('_');
      out.write('0');
      out.print(i_);
      out.write("\" class=\"validation_");
      out.print(num);
      out.write("\" name=\"icType");
      out.print(num);
      out.write("\" style=\"top:7px\" value='");
      out.print(icBean.getIc_type());
      out.write(',');
      out.print(icBean.getIc_code());
      out.write("' type=\"radio\" onclick=\"");
      out.print(cobocheck);
      out.write('"');
      out.write(' ');
      out.print(selected);
      out.write(" />\r\n");
      out.write("\t\t\t\t\t<label for=\"input_radio_01_0");
      out.print(num);
      out.write('_');
      out.write('0');
      out.print(i_);
      out.write('"');
      out.write('>');
      out.print(icBean.getIc_name());
      out.write("</label>\r\n");
      out.write("\t\t\t\t\t</span>\r\n");
      out.write("\t\t");

					}
				}else{					
					if(!"".equals(km_TypeCode)){
					if(codeArry[codeNum] == 3 || codeArry[codeNum] == 31 || codeArry[codeNum] == 5 || codeArry[codeNum] == 51){
						String arr_kMap[] = km_TypeCode.split("@");
						String arr_TypeCode[] = null;
						for(int k=0 ; k<arr_kMap.length ; k++){
							arr_TypeCode = arr_kMap[k].split(",");
							if(icBean.getIc_type() == Integer.parseInt(arr_TypeCode[0]) && icBean.getIc_code() == Integer.parseInt(arr_TypeCode[1])){
								selected = "checked=checked";
							}
						}
					}
					}
					
		
      out.write("\r\n");
      out.write("\t\t\t\t<span class=\"comp\" ");
if(num==10){
      out.write(" style=\"line-height:24px\" ");
} 
      out.write(">\r\n");
      out.write("\t\t\t\t<input id=\"input_radio_01_0");
      out.print(num);
      out.write('_');
      out.write('0');
      out.print(i_);
      out.write("\" class=\"validation_");
      out.print(num);
      out.write("\" name=\"icType");
      out.print(num);
      out.write("\" style=\"top:7px\" value='");
      out.print(icBean.getIc_type());
      out.write(',');
      out.print(icBean.getIc_code());
      out.write("' type=\"radio\" onclick=\"");
      out.print(cobocheck);
      out.write('"');
      out.write(' ');
      out.print(selected);
      out.write(" />\r\n");
      out.write("\t\t\t\t<label for=\"input_radio_01_0");
      out.print(num);
      out.write('_');
      out.write('0');
      out.print(i_);
      out.write('"');
      out.write('>');
      out.print(icBean.getIc_name());
      out.write("</label>\r\n");
      out.write("\t\t\t\t</span>\r\n");
      out.write("\t\t");

				}
			}
		
      out.write("\r\n");
      out.write("\t\t");
      out.write("\r\n");
      out.write("\t\t\t<input type='hidden' name='focus_icType");
      out.print(num);
      out.write("' value=\"");
      out.print(selectedValue);
      out.write("\" />\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t");
}
      out.write("\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t<th><span id=\"label_4\">주요이슈</span></th>\r\n");
      out.write("\t\t<td>\r\n");

	num = 4;
	//arrIcBean = new ArrayList();
	//arrIcBean = icm.GetType(num);

      out.write("\t\t\r\n");
      out.write("\t\t\t<select id=\"input_select_01_");
      out.print(num);
      out.write("\" class=\"validation_");
      out.print(num);
      out.write("\" name=\"icType");
      out.print(num);
      out.write("\" style=\"min-width:180px\">\r\n");
      out.write("\t\t\t\t<option value=\"\">선택하세요</option>\r\n");


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
      out.write("<tr>\r\n");
      out.write("\t\t<th><span id=\"label_7\">보도자료</span></th>\r\n");
      out.write("\t\t<td>\r\n");

	num = 7;
	//arrIcBean = new ArrayList();
	//arrIcBean = icm.GetType(num);

      out.write("\t\t\r\n");
      out.write("\t\t\t<select id=\"input_select_01_");
      out.print(num);
      out.write("\" class=\"validation_");
      out.print(num);
      out.write("\" name=\"icType");
      out.print(num);
      out.write("\" style=\"min-width:180px\">\r\n");
      out.write("\t\t\t\t<option value=\"\">선택하세요</option>\r\n");


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
      out.write("\" class=\"invisible\">보도자료</label>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t\t</tr>\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<!-- <tr>\r\n");
      out.write("\t\t\t<th><span>주요이슈</span></th>\r\n");
      out.write("\t\t\t<td>\r\n");
      out.write("\t\t\t\t<input name=\"txt_issueKey\" id=\"txt_issueKey\" type=\"text\" class=\"h24\" style=\"width:200px\" /><label for=\"txt_issueKey\" class=\"invisible\">주요이슈</label>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr> -->\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<!-- 연관 키워드 -->\r\n");
      out.write("\t\t<tr id=\"tr_relationKey\" style=\"display:");
if(mode.equals("update_multi")){out.print("none");}
      out.write("\">\r\n");
      out.write("\t\t\t<th><span id=\"label_keyword\">연관키워드</span></th>\r\n");
      out.write("\t\t\t<td>\r\n");
      out.write("\t\t\t\t<input id=\"txt_relationKey\" type=\"text\" class=\"h24\" style=\"width:200px\" onkeypress=\"javascript:if(event.keyCode == 13){addKeyword();}\"><label for=\"txt_relationKey\" class=\"invisible\">연관 키워드 입력</label>\r\n");
      out.write("\t\t\t\t<button id=\"btn_key_add\" type=\"button\" class=\"btn_add\" onclick=\"addKeyword();\"></button>\r\n");
      out.write("\t\t\t\t<ul class=\"keyword_list\" id=\"tb_keywordList\">\r\n");
      out.write("\t\t\t\t");

                  if(mode.equals("update")) {
                    for(int i =0; i < relationKey.size(); i++) {
                      out.println("<li id='td_keyword_" + (i + 1) +"'><div class='item'><span>" + (String)relationKey.get(i) + "</span><button type='button' onclick=\"delKeyword('"+ (i+1) +"');\" class='btn_del' title='키워드 삭제'>삭제</button></div></li>");
                    }
                  }
                
      out.write("</ul>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<!-- \t<input style=\"width:150px;\" type=\"text\" class=\"textbox2\" name=\"txt_relationKey\" id=\"txt_relationKey\" value=\"\" onkeypress=\"javascript:if(event.keyCode == 13){addKeyword();}\">\r\n");
      out.write("\t\t\t\t<img src=\"/images/search/plus_btn.gif\" onclick=\"addKeyword();\" style=\"vertical-align: bottom;cursor: pointer;\">\r\n");
      out.write("\t\t\t\t<div id=\"tb_keywordList\">\t\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("                </div> -->\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
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
