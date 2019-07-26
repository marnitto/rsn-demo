package org.apache.jsp.riskv3.search;

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

public final class pop_005fmulti_005fissueData_005fform_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/riskv3/search/../inc/sessioncheck.jsp");
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
	List arrAddGroupBean = new ArrayList();  //수신자그룹 어레이
	
	hotkeywordMgr hm = new hotkeywordMgr();
	hotkeywordBean hb = new hotkeywordBean();
	
	AddressBookGroupBean addGroupBean = new AddressBookGroupBean();	
	IssueDataBean idBean = new IssueDataBean();
	IssueCodeBean icBean = new IssueCodeBean();
	MetaBean metaBean = new MetaBean();			
			
	String selected = null;
	String mode = "multi";
	String md_seqs = pr.getString("md_seqs");
	/* String nowPage = pr.getString("nowPage");
	String subMode = pr.getString("subMode");
	String md_seq = pr.getString("md_seq"); */
	int ic_seq = 0;
	
	//분류체계코드 (0:분류항목 포함, 1:분류항목 제외)
	icm.init(0);
	
	//SMS대그룹  HOT ISSUE중그룹에 속하는 키워드
	ArrayList keywordInfo = new ArrayList();
	
	String k_xp = "";
	
	//이슈데이터 등록 관련
	IssueMgr isMgr = new IssueMgr();
	IssueBean isBean = new IssueBean();	   	   	
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	
	//정보그룹 관련
	/* ArrayList igArr = new ArrayList();
	InfoGroupMgr igMgr = new InfoGroupMgr();
	InfoGroupBean igBean = new InfoGroupBean();
	igArr = igMgr.getInfoGroup("Y");	   	 */
	
	//수신자 그룹
	/* arrAddGroupBean = abDao.getAdressBookGroup();
	Iterator it = arrAddGroupBean.iterator(); */
	
	//태그그룹
	//ArrayList tagList = new ArrayList();
	//tagList = issueMgr.getTagGroup();
	
	/* String tagScriptElement = "";
	if(tagList.size() > 0){
		for(int i = 0; i < tagList.size(); i++){
			IssueDataBean idbean = (IssueDataBean)tagList.get(i);
			if(tagScriptElement.equals("")){
				tagScriptElement = idbean.getItg_seq();
			}else{
				tagScriptElement += ","+idbean.getItg_seq();
			}
		}
	} */
	
	//hot keyword
	/* ArrayList dataList = new ArrayList();
	dataList = hm.getHotkeyword("", "Y"); */
	
	//get할려는 IC_TYPE값을 num에 세팅한다.
	int num = 0;
	String checked ="";
	String checkedValue="";
	
	//연관키워드 관련 
	ArrayList relationKey = null;
	ArrayList relationKeyAll = null;
	int key_seq = 0;
	
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
      out.write("<title>이슈등록</title>\r\n");
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
      out.write("var key_seq = ");
      out.print(key_seq);
      out.write(";\r\n");
      out.write("var streamKey = '");
      out.print(streamKey);
      out.write("';\r\n");
      out.write("\r\n");
      out.write("//death2인 코드 값들을 불러 온다.\r\n");
      out.write("//type : 부모 속성의 타입값 - IC_PTYPE\r\n");
      out.write("//code : 부모 속성의 코드값 - IC_PCODE\r\n");
      out.write("//name : 부모 속성의 이름 - IC_NAME \r\n");
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
      out.write("\t\ticon = \"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>\";\r\n");
      out.write("\t\ticPName = \"소분류\";\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tif(type == \"5\" || type == \"3\"){\r\n");
      out.write("\t\t$(\"#label_\"+icPtype+\"1\").html(icon+icPName+\"<strong>*</strong>\");\t\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\t$(\"#label_\"+icPtype+\"1\").html(icon+icPName+\"<strong></strong>\");\r\n");
      out.write("\t}\r\n");
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
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tif(num == \"51\"){\r\n");
      out.write("\t\t\t\t\tinnerSpan += \"<span class='comp'><input id='input_radio_01_\"+num+\"_\"+index+\"' class='validation_\"+num+\"' name='icType\"+num+\"' onclick='comboCheckManual(this);' value='\"+data.list[index].icType+\",\"+data.list[index].icCode+\"' type='radio' \"+checked+\" />\"\r\n");
      out.write("\t\t\t\t\t+\"<label for='input_radio_01_\"+num+\"_\"+index+\"'>\"+data.list[index].icName+\"</label></span>\"\r\n");
      out.write("\t\t\t\t}else{\t\t\t\t\r\n");
      out.write("\t\t\t\t\tinnerSpan += \"<span class='comp'><input id='input_radio_01_\"+num+\"_\"+index+\"' class='validation_\"+num+\"' name='icType\"+num+\"' onclick='comboCheck(this);' value='\"+data.list[index].icType+\",\"+data.list[index].icCode+\"' type='radio' \"+checked+\" />\"\r\n");
      out.write("\t\t\t\t\t+\"<label for='input_radio_01_\"+num+\"_\"+index+\"'>\"+data.list[index].icName+\"</label></span>\"\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\t$(\"#td_icType\"+icPtype).html(innerSpan);\r\n");
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
      out.write("\tvar radio_id = [1,2,5,3,9,21,51,31];\r\n");
      out.write("\t//선택박스 type값 세팅\r\n");
      out.write("\tvar select_id = [4,7];\r\n");
      out.write("\tvar form = document.fSend;\r\n");
      out.write("\r\n");
      out.write("\tform.typeCode.value = '';\r\n");
      out.write("\t\r\n");
      out.write("\tfor(var i=0; i < radio_id.length; i++){\r\n");
      out.write("\t\t$(\"[name=icType\"+radio_id[i]+\"]:radio\").each(function(){\r\n");
      out.write("\t\t\tif($(this).is(\":checked\")){\r\n");
      out.write("\t\t\t\tform.typeCode.value += form.typeCode.value=='' ? $(this).val() : '@'+$(this).val();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tvar selectedVal;\r\n");
      out.write("\tfor(var i=0; i < select_id.length; i++){\r\n");
      out.write("\t\tselectedVal =  $(\"[name=icType\"+select_id[i]+\"] option:selected\").val();\r\n");
      out.write("\t\tif(selectedVal != \"\"){\r\n");
      out.write("\t\t\tform.typeCode.value +='@'+selectedVal;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\treturn form.typeCode.value;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("var regChk = false;//이미 등록중인지 확인하기위한 값\r\n");
      out.write("//이슈관리 저장 함수\r\n");
      out.write("function save_issue(mode, event){\r\n");
      out.write("\t\r\n");
      out.write("\t//유효성 검사\r\n");
      out.write("\tvar flagVal = $(\"[name=icType1]:checked\").val();\r\n");
      out.write("\tvar check;\r\n");
      out.write("\tcheck = [1,5,51,3,31,9];\r\n");
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
      out.write("\t  // 연관키워드\r\n");
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
      out.write("     }\r\n");
      out.write("\t\r\n");
      out.write("\t//타입별 값 세팅\r\n");
      out.write("\tvar typeCode = settingTypeCode();\r\n");
      out.write("\t\r\n");
      out.write("\t//mode에 따라 form submit\r\n");
      out.write("\tvar f = document.fSend;\r\n");
      out.write("\t\r\n");
      out.write("\tif(!regChk){\r\n");
      out.write("\t\tregChk = true;\r\n");
      out.write("\t\tf.typeCode.value = typeCode;\t\r\n");
      out.write("\t\tf.mode.value = mode;\r\n");
      out.write("\t\t//f.target='if_samelist';\r\n");
      out.write("\t\t//f.action='issue_data_prc.jsp';\r\n");
      out.write("        //f.submit();\r\n");
      out.write("        \r\n");
      out.write("        /* \r\n");
      out.write("        * ajax통신 방법으로 이슈등록 프로세스 변경\r\n");
      out.write("        * 2014.12.31\r\n");
      out.write("        */\r\n");
      out.write("        var formData = $(\"#fSend\").serialize();\r\n");
      out.write("        var imgId = \"\"; \r\n");
      out.write("\t\t$.ajax({\r\n");
      out.write("\t\t\ttype : \"POST\"\r\n");
      out.write("\t\t\t,url: \"../issue/issue_data_prc.jsp\"\r\n");
      out.write("\t\t\t,timeout: 30000\r\n");
      out.write("\t\t\t,data : formData\r\n");
      out.write("\t\t\t,dataType : 'text'\r\n");
      out.write("\t\t\t,async: true\r\n");
      out.write("\t\t\t,success : function(data){\r\n");
      out.write("\t\t\t\t\t\tif(data != \"\"){\r\n");
      out.write("\t\t\t\t\t\t\t\talert(\"이슈가 등록 되었습니다.\");\r\n");
      out.write("\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\tvar md_seqs = $(\"[name=md_seqs]\").val();\r\n");
      out.write("\t\t\t\t\t\t\t\tvar temp = md_seqs.split(\",\");\r\n");
      out.write("\t\t\t\t\t\t\t\tfor(var i = 0; i < temp.length; i++){\r\n");
      out.write("\t\t\t\t\t\t\t\t\timgId = \"#issue_menu_icon\" + temp[i];\r\n");
      out.write("\t\t\t\t\t\t\t\t\t$(imgId, opener.document).attr(\"src\", \"../../images/search/btn_manage_on.gif\");\r\n");
      out.write("\t\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\t\twindow.close();\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t  }\r\n");
      out.write("\t\t\t,beforeSend : function(){}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\talert('등록중입니다.. 잠시만 기다려주세요.');\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("//체크해제\r\n");
      out.write("function comboCheckType2(obj,icType,icCode,icName){\r\n");
      out.write("\r\n");
      out.write("\t\tvar f = document.fSend;\r\n");
      out.write("\t\tfocusObj = eval('f.focus_'+ obj.name);\r\n");
      out.write("\r\n");
      out.write("\t\tif(focusObj.value == obj.value){\r\n");
      out.write("\t\t\tobj.checked = false;\r\n");
      out.write("\t\t\tfocusObj.value = '';\r\n");
      out.write("\t\t\tif(\"icType2\" == obj.name){\r\n");
      out.write("\t\t\t\t$(\"#tr_icType2\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\t$(\"[name=icType21]\").attr(\"checked\", false);\r\n");
      out.write("\t\t\t}else if(\"icType3\" == obj.value){\r\n");
      out.write("\t\t\t\t$(\"#tr_icType3\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\t$(\"[name=icType31]\").attr(\"checked\", false);\r\n");
      out.write("\t\t\t}else if(\"icType5\" == obj.value){\r\n");
      out.write("\t\t\t\t$(\"#tr_icType5\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\t$(\"[name=icType51]\").attr(\"checked\", false);\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tgetTypeCodeDeath2(icType,icCode,icName);\r\n");
      out.write("\t\t\tfocusObj.value = obj.value;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("//체크해제\r\n");
      out.write("function comboCheck(obj){\r\n");
      out.write("\tvar f = document.fSend;\r\n");
      out.write("\tfocusObj = eval('f.focus_'+ obj.name);\r\n");
      out.write("\r\n");
      out.write("\tif(focusObj.value == obj.value){\r\n");
      out.write("\t\tobj.checked = false;\r\n");
      out.write("\t\tfocusObj.value = '';\t\t\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\tif(obj.value.split(\",\")[0] == \"51\"){\t\t\t\r\n");
      out.write("\t\t\tcomboCheckManual();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfocusObj.value = obj.value;\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function comboCheckManual(){\r\n");
      out.write("\tvar type5 = $(\"[name=icType5]:checked\").val().split(\",\")[1];\r\n");
      out.write("\tvar type51 = $(\"[name=icType51]:checked\").val().split(\",\")[1];\r\n");
      out.write("\t\r\n");
      out.write("\tif(type51 != '77'){\r\n");
      out.write("\t$.ajax({\r\n");
      out.write("\t\ttype:'POST',\r\n");
      out.write("\t\turl:\"getJsonData.jsp\",\r\n");
      out.write("\t\tdataType:'json',\r\n");
      out.write("\t\tdata:{\"type5\":type5, \"type51\":type51, flag: \"manual\"},\r\n");
      out.write("\t\tsuccess:function(data){\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tvar type3 = \"input_radio_01_03_0\"+data.type3;\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t$(\"#label_3\").html(\"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/images/issue/icon.gif'>대분류<strong>*</strong>\");\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t$(\"#\"+type3).click();\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tsetTimeout(function() {\r\n");
      out.write("\t\t\t\tif(typeof data.type31 != \"undefined\"){\t\t\t\t\r\n");
      out.write("\t\t\t\t\tvar type31 = \"31,\"+data.type31;\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t$(\"[name=icType31]\").each(function(){\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\tif(this.value == type31){\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\tthis.checked = true;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t});\t\t\t\t\r\n");
      out.write("\t\t\t\t}\t\t\t\t \r\n");
      out.write("\t\t\t}, 50);\r\n");
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
      out.write("//연관키워드 추가\r\n");
      out.write("function addKeyword() {\r\n");
      out.write("    var f = document.fSend;\r\n");
      out.write("    var keyword = f.txt_relationKey.value.trim();\r\n");
      out.write("\r\n");
      out.write("    if(keyword != ''){\r\n");
      out.write("      key_seq++;\r\n");
      out.write("      \r\n");
      out.write("      var AddHtml = \"<li id='td_keyword_\" + key_seq +\"'><div class='item'><span>\" + keyword + \"</span><button type='button' onclick=\\\"delKeyword('\"+ key_seq +\"');\\\" class='btn_del' title='키워드 삭제'>삭제</button></div></li>\"\r\n");
      out.write("      $(\"#tb_keywordList\").append(AddHtml);\r\n");
      out.write("      \r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    f.txt_relationKey.value = '';\r\n");
      out.write("  }\r\n");
      out.write("//연관키워드 삭제\r\n");
      out.write("  function delKeyword(idx){\r\n");
      out.write("\t  $(\"#td_keyword_\"+idx).remove();\r\n");
      out.write("  } \r\n");
      out.write("\r\n");
      out.write("function settingPseq(){\r\n");
      out.write("\tvar pseq = $(\"[name=typeCode10_pseq] option:selected\").val();\r\n");
      out.write("\t$(\"#p_seq\").val(pseq);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("$(function(){\t\r\n");
      out.write("\t$('#txt_relationKey').autocomplete(streamKey.split(','));\r\n");
      out.write("\t\r\n");
      out.write("\t$(\"[name=icType10]\").click(function(){\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif( $(\"[name=focus_icType10]\").val() == \"10,1\"){\r\n");
      out.write("\t\t\t$(\"[name=typeCode10_pseq]\").css(\"display\", '');\r\n");
      out.write("\t\t\tvar pseq = $(\"[name=typeCode10_pseq] option:selected\").val();\r\n");
      out.write("\t\t\t$(\"#p_seq\").val(pseq);\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t$(\"[name=typeCode10_pseq]\").css(\"display\", 'none');\r\n");
      out.write("\t\t\t$(\"#p_seq\").val('');\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t$(\"[name=typeCode10_pseq]\").change(function(){\r\n");
      out.write("\t\tvar pseq = $(\"[name=typeCode10_pseq] option:selected\").val();\r\n");
      out.write("\t\t$(\"#p_seq\").val(pseq);\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("});\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<form name=\"fSend\" id=\"fSend\" action=\"issue_data_prc.jsp\" method=\"post\" onsubmit=\"return false;\">\r\n");
      out.write("<input name=\"mode\" id=\"mode\" type=\"hidden\" value=\"\">\r\n");
      out.write("<input type=\"hidden\" name=\"typeCode\" value=\"\">\r\n");
      out.write("<input type=\"hidden\" name=\"md_seqs\" value=\"");
      out.print(md_seqs );
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"tagGroupCode\" id=\"tagGroupCode\">\r\n");
      out.write("<input type=\"hidden\" name=\"regTagCode\" id=\"regTagCode\">\r\n");
      out.write("<input type=\"hidden\" id=\"keyNames\" name=\"keyNames\" value=\"\">\r\n");
      out.write("<input type=\"hidden\" id=\"p_seq\" name=\"p_seq\" value=\"\">\r\n");
      out.write("<div class=\"wrap_pop\">\r\n");
      out.write("\t<h2>이슈 멀티 등록</h2>\r\n");
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
      out.write("\t\t<th><span id=\"label_1\">구분<strong>*</strong></span></th><!-- 필수 속성인 경우 strong 태그 삽입 -->\r\n");
      out.write("\t\t<td>\r\n");
      out.write("\t\t\r\n");

	num = 1;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	String cssNone = "";   
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);

      out.write("\r\n");
      out.write("\t\t\t<span class=\"comp hongboPart\" id=\"typeCode2_");
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
      out.write("' type=\"radio\" onclick=\"comboCheck(this);\" ");
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
      out.write("\t\t<tr class=\"soboNone\">\r\n");
      out.write("\t\t<th><span id=\"label_2\">정보구분<strong></strong></span></th>\r\n");
      out.write("\t\t<td>\r\n");

	num = 2;
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
      out.write("' onclick=\"comboCheckType2(this,'");
      out.print(icBean.getIc_type());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(icBean.getIc_code());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(icBean.getIc_name());
      out.write("');\" type=\"radio\">\r\n");
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
      out.write("\t\t<!--  2death 정보속성 값 -->\r\n");
      out.write("\t\t<tr style=\"display: none;\" id=\"tr_icType");
      out.print(num);
      out.write("\">\r\n");
      out.write("\t\t<th><span id=\"label_21\"><strong>*</strong></span><input type='hidden' name='focus_icType21' value=\"\" /></th>\r\n");
      out.write("\t\t<td id=\"td_icType");
      out.print(num);
      out.write("\"></td>\r\n");
      out.write("\t\t</tr>\t\t\r\n");
      out.write("\t\t<!--  2death 정보속성 값 -->\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<tr class=\"soboNone\">\r\n");
      out.write("\t\t<th><span id=\"label_5\">실국<strong>*</strong></span></th>\r\n");
      out.write("\t\t<td>\r\n");

	num = 5;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		int i_ = i*100;

      out.write("  \r\n");
      out.write("\t\t\t<span class=\"comp\">\r\n");
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
      out.write("' onclick=\"comboCheckType2(this,'");
      out.print(icBean.getIc_type());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(icBean.getIc_code());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(icBean.getIc_name());
      out.write("');\" type=\"radio\">\r\n");
      out.write("\t\t\t<label for=\"input_radio_01_0");
      out.print(num);
      out.write('_');
      out.write('0');
      out.print(i_);
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
      out.write("\t\t<!--  2death 정보속성 값 -->\r\n");
      out.write("\t\t<tr style=\"display: none;\" id=\"tr_icType");
      out.print(num);
      out.write("\">\r\n");
      out.write("\t\t<th><span id=\"label_51\"><strong>*</strong></span><input type='hidden' name='focus_icType51' value=\"\" /></th>\r\n");
      out.write("\t\t<td id=\"td_icType");
      out.print(num);
      out.write("\"></td>\r\n");
      out.write("\t\t</tr>\t\t\r\n");
      out.write("\t\t<!--  2death 정보속성 값 -->\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<tr class=\"soboNone\">\r\n");
      out.write("\t\t<th><span id=\"label_3\">대분류<strong>*</strong></span></th>\r\n");
      out.write("\t\t<td>\r\n");

	num = 3;
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(num);
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		int i_ = i*100;

      out.write("  \r\n");
      out.write("\t\t\t<span class=\"comp\">\r\n");
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
      out.write("' onclick=\"comboCheckType2(this,'");
      out.print(icBean.getIc_type());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(icBean.getIc_code());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(icBean.getIc_name());
      out.write("');\" type=\"radio\">\r\n");
      out.write("\t\t\t<label for=\"input_radio_01_0");
      out.print(num);
      out.write('_');
      out.write('0');
      out.print(i_);
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
      out.write("\t\t<!--  2death 정보속성 값 -->\r\n");
      out.write("\t\t<tr style=\"display: none;\" id=\"tr_icType");
      out.print(num);
      out.write("\">\r\n");
      out.write("\t\t<th><span id=\"label_31\"><strong>*</strong></span><input type='hidden' name='focus_icType31' value=\"\" /></th>\r\n");
      out.write("\t\t<td id=\"td_icType");
      out.print(num);
      out.write("\"></td>\r\n");
      out.write("\t\t</tr>\t\t\r\n");
      out.write("\t\t<!--  2death 정보속성 값 -->\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("<tr>\r\n");
      out.write("\t\t<th><span id=\"label_9\">성향<strong>*</strong></span></th>\r\n");
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
      out.write('	');
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\t\t\t\r\n");

	num = 4;

      out.write("\r\n");
      out.write("\t\t<tr class=\"soboNone\">\r\n");
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
      out.write("\t\t\r\n");
      out.write("\t\t\t</select><label for=\"input_select_01_");
      out.print(num);
      out.write("\" class=\"invisible\">주요이슈</label>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");

	num = 7;

      out.write("\r\n");
      out.write("\t\t<tr class=\"soboNone\">\r\n");
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
      out.write("\t\t\r\n");
      out.write("\t\t\t</select><label for=\"input_select_01_");
      out.print(num);
      out.write("\" class=\"invisible\">주요이슈</label>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t\t</tr>\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<!-- 연관 키워드 -->\r\n");
      out.write("\t\t<tr id=\"tr_relationKey\">\r\n");
      out.write("\t\t\t<th><span id=\"label_keyword\">연관키워드</span></th>\r\n");
      out.write("\t\t\t<td>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<input id=\"txt_relationKey\" type=\"text\" class=\"h24\" style=\"width:200px\" onkeypress=\"javascript:if(event.keyCode == 13){addKeyword();}\"><label for=\"txt_relationKey\" class=\"invisible\">연관 키워드 입력</label>\r\n");
      out.write("\t\t\t\t<button id=\"btn_key_add\" type=\"button\" class=\"btn_add\" onclick=\"addKeyword();\"></button>\r\n");
      out.write("\t\t\t\t<ul class=\"keyword_list\" id=\"tb_keywordList\"></ul>\r\n");
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
