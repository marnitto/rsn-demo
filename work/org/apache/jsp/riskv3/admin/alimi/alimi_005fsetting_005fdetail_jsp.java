package org.apache.jsp.riskv3.admin.alimi;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.ConfigUtil;
import java.util.ArrayList;
import risk.mobile.AlimiSettingBean;
import risk.mobile.AlimiSettingDao;
import risk.util.ParseRequest;
import risk.admin.site.SitegroupBean;
import risk.admin.site.SiteMng;
import risk.search.userEnvMgr;
import risk.search.keywordInfo;
import risk.admin.classification.classificationMgr;
import risk.admin.classification.clfBean;
import java.util.List;
import risk.search.userEnvMgr;
import risk.search.userEnvInfo;

public final class alimi_005fsetting_005fdetail_jsp extends org.apache.jasper.runtime.HttpJspBase
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
	AlimiSettingBean asBean = null;	
		
	
	String mode = pr.getString("mode","INSERT");
	String as_seq        = pr.getString("as_seq","");
	String ab_seq = null;
	String nowpage = pr.getString("nowpage","");
	
	String styleDisplay1 = "";
	String styleDisplay2 = "";
	String styleDisplay3 = "";
	String styleDisplay4 = "";
	
	
	ArrayList alimiSetList = new ArrayList();
	List xpList = null;
	List sgList = null;
	
	String[] smsTime = null;
	String smsTimeCheck1 =null;
	String smsTimeCheck2 =null;
	String smsTimeCheck3 =null;
	String smsTimeCheck4 =null;
	String smsTimeCheck5 =null;
	
	String[] mtType = null;
	String mtTypeCheck1 = null;
	String mtTypeCheck2 = null;
	
	//pr.printParams();
	String[] sdGsnList = null;
	String[] exSeqList = null;
	int sdGsnCnt = 0;
	int exSeqCnt = 0;
	
	if(mode.equals("UPDATE") && as_seq.length()>0)
	{
		ab_seq = asDao.getReceiverSeq(as_seq);
		//System.out.println("ab_seq:"+ab_seq);
		alimiSetList = asDao.getAlimiSetList(1,0,as_seq,"Y");
		asBean = (AlimiSettingBean)alimiSetList.get(0);
		
				
		if(asBean.getSd_gsns()!=null && !asBean.getSd_gsns().trim().equals(""))
		{
			sdGsnList = asBean.getSd_gsns().split(",");
			sdGsnCnt = sdGsnList.length;
		}
		
		if(asBean.getAs_infotype().equals("1"))
		{
			if(asBean.getAs_type().equals("1"))
			{
				styleDisplay1 = "";
				styleDisplay2 = "none";
				styleDisplay3 = "none";
				styleDisplay4 = "none";
				
			}else{
				styleDisplay1 = "none";
				styleDisplay2 = "";
				styleDisplay3 = "none";
				styleDisplay4 = "none";
			}
		}else{
			if(asBean.getAs_type().equals("1"))
			{
				styleDisplay1 = "none";
				styleDisplay2 = "none";
				styleDisplay3 = "";
				styleDisplay4 = "none";
				
			}else{
				styleDisplay1 = "none";
				styleDisplay2 = "none";
				styleDisplay3 = "none";
				styleDisplay4 = "";
			}
		}		
		
		if(asBean.getAs_sms_time()!=null && !asBean.getAs_sms_time().equals(""))
		{
			smsTime = asBean.getAs_sms_time().split(",");
			
  			if(smsTime!=null)
  			{
  				for(int i=0;i<smsTime.length;i++)
  				{
  					if(smsTime[i].equals("1"))smsTimeCheck1="checked";
  					if(smsTime[i].equals("2"))smsTimeCheck2="checked";
  					if(smsTime[i].equals("3"))smsTimeCheck3="checked";
  					if(smsTime[i].equals("4"))smsTimeCheck4="checked";
  					if(smsTime[i].equals("5"))smsTimeCheck5="checked";
  				}
  			}
		}
		
		if(asBean.getMt_types()!=null && !asBean.getMt_types().equals(""))
		{
			mtType = asBean.getMt_types().split(",");
			
  			if(mtType!=null)
  			{
  				for(int i=0;i<mtType.length;i++)
  				{
  					if(mtType[i].equals("1"))mtTypeCheck1="checked";
  					if(mtType[i].equals("2"))mtTypeCheck2="checked";  					
  				}
  			}
		}
		
	}
	
	userEnvMgr uemng = new userEnvMgr();
	userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");
	xpList = uemng.getKeywordGroup(uei.getMg_xp());	
	
	SiteMng sitemng = new SiteMng();
	sgList = sitemng.getSGList();
	
	System.out.println(mode.equals("UPDATE") +" - mode ");
	

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<title></title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(SS_URL);
      out.write("css/base.css\" />\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write(".sameOp{height: 20px;box-sizing: content-box;color: rgb(33, 33, 33);font-size: 11px;line-height: 18px;padding: 0px 0px 0px 5px;margin: 0px 4px;border-width: 1px;border-style: solid;border-color: rgb(199, 199, 199);border-image: initial;}\r\n");
      out.write("</style>\r\n");
      out.write("\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/jquery.js\"  type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/ajax.js\"  type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(SS_URL);
      out.write("js/common.js\"  type=\"text/javascript\"></script>\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write("\r\n");
      out.write("\t//$('document').ready(initMain);\r\n");
      out.write("\tfunction initMain()\r\n");
      out.write("\t{\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(\"[name=as_type]\").each(function(){\r\n");
      out.write("\t\t\tif ( $(this).attr(\"checked\") ){\r\n");
      out.write("\t\t\t\tif ( $(this).val() == 4 ){\r\n");
      out.write("\t\t\t\t\t$(\".ex_top\").css(\"display\",\"none\");\t\t\r\n");
      out.write("\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\tchangeTopKeywordGroup();\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction checkAsTypeValue(){\r\n");
      out.write("\t\tvar f =  document.alimi_detail;\r\n");
      out.write("\t\tvar asType;\r\n");
      out.write("\t\tfor( var i = 0; i<f.as_type.length; i++)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tif(f.as_type[i].checked)\r\n");
      out.write("\t\t\t{\t\t\t\r\n");
      out.write("\t\t\t\tasType =f.as_type[i].value;\t\t\t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\treturn asType;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction checkAsInfoTypeValue(){\r\n");
      out.write("\t\tvar f =  document.alimi_detail;\r\n");
      out.write("\t\tvar asInfoType;\r\n");
      out.write("\t\r\n");
      out.write("\t\tasInfoType = f.as_infotype.value;\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t\tfor( var i = 0; i<f.as_infotype.length; i++)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tif(f.as_infotype[i].checked)\r\n");
      out.write("\t\t\t{\t\t\t\r\n");
      out.write("\t\t\t\tasInfoType =f.as_infotype[i].value;\t\t\t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\treturn asInfoType;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction checkedIntervalValue(name)\r\n");
      out.write("\t{\t\r\n");
      out.write("\t\tvar obj = eval('document.alimi_detail.'+name);\r\n");
      out.write("\t\tvar intervalValue='';\r\n");
      out.write("\t\tif(obj.length)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tfor(var i =0 ; i<obj.length; i++)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif(obj[i].checked)\r\n");
      out.write("\t\t\t\t{\t\t\t\t\r\n");
      out.write("\t\t\t\t\tintervalValue = obj[i].value;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tif(obj.checked)\r\n");
      out.write("\t\t\t{\t\t\t\r\n");
      out.write("\t\t\t\tintervalValue = obj.value;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\t\r\n");
      out.write("\t\treturn intervalValue;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction checkAsIntervalValue(){\r\n");
      out.write("\t\tvar f =  document.alimi_detail;\r\n");
      out.write("\t\tvar asType = checkAsTypeValue();\r\n");
      out.write("\t\tvar asInfoType = checkAsInfoTypeValue();\r\n");
      out.write("\t\tvar asInterval;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(asInfoType=='1')\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tif(asType==1)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tasInterval = checkedIntervalValue('as_interval1');\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tasInterval = checkedIntervalValue('as_interval2');\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tif(asType=='1')\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tasInterval = checkedIntervalValue('as_interval3');\t\t\t\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tasInterval = checkedIntervalValue('as_interval4');\r\n");
      out.write("\t\t\t}\t\t\r\n");
      out.write("\t\t}\t\r\n");
      out.write("\t\treturn asInterval;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction checkedSmsTimeValue(name)\r\n");
      out.write("\t{\t\r\n");
      out.write("\t\tvar obj = eval('document.alimi_detail.'+name);\r\n");
      out.write("\t\tvar asSmsTime = '';\r\n");
      out.write("\t\t//alert(obj.length);\r\n");
      out.write("\t\tif(obj.length)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tfor(var i =0 ; i<obj.length; i++)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif(obj[i].checked)\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tif(asSmsTime=='')\r\n");
      out.write("\t\t\t\t\t{\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\tasSmsTime = obj[i].value;\r\n");
      out.write("\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\tasSmsTime +=',' + obj[i].value;\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tif(obj.checked)\r\n");
      out.write("\t\t\t{\t\t\t\r\n");
      out.write("\t\t\t\tasSmsTime = obj.value;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\t\r\n");
      out.write("\t\treturn asSmsTime;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction checkSmsTimelValue(){\r\n");
      out.write("\t\tvar f =  document.alimi_detail;\r\n");
      out.write("\t\tvar asType = checkAsTypeValue();\r\n");
      out.write("\t\tvar asInfoType = checkAsInfoTypeValue();\r\n");
      out.write("\t\tvar asSmsTime;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(asInfoType=='1')\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tif(asType==1)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tasSmsTime = '0';\t\t\t \r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tasSmsTime = checkedSmsTimeValue('as_sms_time1');\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tif(asType=='1')\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tasSmsTime = '0';\t\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tasSmsTime = checkedSmsTimeValue('as_sms_time2');\r\n");
      out.write("\t\t\t}\t\t\r\n");
      out.write("\t\t}\t\r\n");
      out.write("\t\treturn asSmsTime;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction checkAllKxp(chk) {\r\n");
      out.write("\t\tvar o=document.all.k_xp;\r\n");
      out.write("\t\tfor(i=0; i<o.length; i++) {\r\n");
      out.write("\t\t\to(i).checked = chk;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction checkXpValue(){\r\n");
      out.write("\t\tvar f =  document.alimi_detail;\r\n");
      out.write("\t\tvar check = false;\r\n");
      out.write("\t\tf.k_xps.value='';\r\n");
      out.write("\t\tif(f.k_xp.length){\r\n");
      out.write("\t\t\tfor( var i = 0; i<f.k_xp.length; i++)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\tif(f.k_xp[i].checked)\r\n");
      out.write("\t\t\t\t{\t\t\r\n");
      out.write("\t\t\t\t\tf.k_xps.value =f.k_xp[i].value;\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tf.k_xps.value = f.k_xp.value;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tif(f.k_xps.value.length>0)check =true;\r\n");
      out.write("\t\treturn check;\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction checkYpValue(){\r\n");
      out.write("\t\tvar f =  document.alimi_detail;\r\n");
      out.write("\t\tvar check = false;\r\n");
      out.write("\t\tf.k_yps.value='';\r\n");
      out.write("\t\tif(f.k_yp){\r\n");
      out.write("\t\t\tif(f.k_yp.length){\r\n");
      out.write("\t\t\t\tfor( var i = 0; i<f.k_yp.length; i++)\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\tif(f.k_yp[i].checked)\r\n");
      out.write("\t\t\t\t\t{\t\t\t\r\n");
      out.write("\t\t\t\t\t\tif(f.k_yps.value=='')\r\n");
      out.write("\t\t\t\t\t\t{\r\n");
      out.write("\t\t\t\t\t\t\tf.k_yps.value =f.k_yp[i].value;\r\n");
      out.write("\t\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\t\tf.k_yps.value = f.k_yps.value+','+ f.k_yp[i].value;\r\n");
      out.write("\t\t\t\t\t\t}\t\t\t\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tf.k_yps.value = f.k_yp.value;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t//alert(f.k_yps.value);\r\n");
      out.write("\t\tif(f.k_yps.value.length>0)check =true;\r\n");
      out.write("\t\treturn check;\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction checkAllSgSeq(chk) {\r\n");
      out.write("\t\t/* var o=document.all.sg_seq;\r\n");
      out.write("\t\tfor(i=0; i<o.length; i++) {\r\n");
      out.write("\t\t\to(i).checked = chk;\r\n");
      out.write("\t\t} */\r\n");
      out.write("\t\tif($(\"[name=sg_seqALL]\").is(\":checked\")){\r\n");
      out.write("\t\t\t$(\"[name=sg_seq]\").attr(\"checked\", \"checked\");\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t$(\"[name=sg_seq]\").attr(\"checked\", \"\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\tfunction checkSgSeqValue(){\r\n");
      out.write("\t\tvar f =  document.alimi_detail;\r\n");
      out.write("\t\tvar check = false;\r\n");
      out.write("\t\tf.sg_seqs.value = '';\r\n");
      out.write("\t\tfor( var i = 0; i<f.sg_seq.length; i++)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tif(f.sg_seq[i].checked)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif(f.sg_seqs.value=='')\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tf.sg_seqs.value =f.sg_seq[i].value;\r\n");
      out.write("\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\tf.sg_seqs.value = f.sg_seqs.value+','+ f.sg_seq[i].value;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tif(f.sg_seqs.value.length>0)check =true;\r\n");
      out.write("\t\treturn check;\t\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction checkMtTypeValue(){\r\n");
      out.write("\t\tvar f =  document.alimi_detail;\r\n");
      out.write("\t\tvar check = false;\r\n");
      out.write("\t\tf.mt_types.value = '';\r\n");
      out.write("\t\tfor( var i = 0; i<f.mt_type.length; i++)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tif(f.mt_type[i].checked)\r\n");
      out.write("\t\t\t{\t\t\t\r\n");
      out.write("\t\t\t\tif(f.mt_types.value=='')\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tf.mt_types.value =f.mt_type[i].value;\r\n");
      out.write("\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\tf.mt_types.value = f.mt_types.value+','+ f.mt_type[i].value;\r\n");
      out.write("\t\t\t\t}\t\t\t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tif(f.mt_types.value.length>0)check =true;\r\n");
      out.write("\t\treturn check;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction checkAbAeqValue(){\r\n");
      out.write("\t\tvar f =  frmReceiver.document.ifr_receiver;\r\n");
      out.write("\t\tvar pf = document.alimi_detail;\r\n");
      out.write("\t\r\n");
      out.write("\t\tpf.ab_seqs.value = '';\r\n");
      out.write("\t\r\n");
      out.write("\t\tif(f.chkNum)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tif(f.chkNum.length)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tfor( var i = 0; i<f.chkNum.length; i++)\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tif(f.chkNum[i].checked)\r\n");
      out.write("\t\t\t\t\t{\r\n");
      out.write("\t\t\t\t\t\tif(pf.ab_seqs.value=='')\r\n");
      out.write("\t\t\t\t\t\t{\r\n");
      out.write("\t\t\t\t\t\t\tpf.ab_seqs.value =f.chkNum[i].value;\r\n");
      out.write("\t\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\t\tpf.ab_seqs.value = pf.ab_seqs.value+','+ f.chkNum[i].value;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tif( f.chkNum.checked)\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tpf.ab_seqs.value = f.chkNum.value;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t//alert(pf.ab_seqs.value);\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction asTypeCheck(objValue)   // 발송매체 구분 (objValue) - 1: 이메일 3: R-rimi 발송 4: 포탈TOP 발송 6: 포탈댓글전용알리미\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar f = document.alimi_detail;\r\n");
      out.write("\t\tvar asType = objValue;\r\n");
      out.write("\t\tvar asInfoType = checkAsInfoTypeValue();\t// 1: 일반수집\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(asInfoType==1)\r\n");
      out.write("\t\t{\r\n");
      out.write("\r\n");
      out.write("\t\t\t$(\"#intervalFilter3\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t$(\"#intervalFilter4\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t$(\".ex_top\").css(\"display\", \"\");\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tif(asType==1) //이메일\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\t$(\"#sender_mail\").css(\"display\", \"\");\t//발신자 메일\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"#intervalFilter1\").css(\"display\", \"\");\r\n");
      out.write("\t\t\t\t$(\"#intervalFilter2\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"#smsTimeFilter1\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\t$(\"#smsTimeFilter2\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"tr_later\").style.display='none';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"tr_monitor_use\").style.display='none';\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t$(\"#as_same\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t}else if(asType==3){ //R-rimi 발송\r\n");
      out.write("\t\t\t\t$(\"#sender_mail\").css(\"display\", \"none\");\t//발신자 메일\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"#intervalFilter1\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\t$(\"#intervalFilter2\").css(\"display\", \"\");\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"#smsTimeFilter1\").css(\"display\", \"\");\r\n");
      out.write("\t\t\t\t$(\"#smsTimeFilter2\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"tr_later\").style.display='';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"tr_monitor_use\").style.display='';\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"#as_same\").css(\"display\", \"\");\r\n");
      out.write("\t\t\t\tvar op = $(\".sameOp\").val();\r\n");
      out.write("\t\t\t\tif(op==\"1\"){\r\n");
      out.write("\t\t\t\t\t$(\"#as_same_text\").text(\"%\");\r\n");
      out.write("\t\t\t\t}else if(op==\"2\"){\r\n");
      out.write("\t\t\t\t\t$(\"#as_same_text\").text(\"건\");\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t}else if(asType==4){ //포탈TOP 발송\r\n");
      out.write("\t\t\t\t$(\"#sender_mail\").css(\"display\", \"none\");\t//발신자 메일\r\n");
      out.write("\t\t\t\t$(\".ex_top\").css(\"display\", \"none\");\t//키워드 그룹, 사이트 그룹, 정보유형\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"#intervalFilter1\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\t$(\"#intervalFilter2\").css(\"display\", \"\");\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"#smsTimeFilter1\").css(\"display\", \"\");\r\n");
      out.write("\t\t\t\t$(\"#smsTimeFilter2\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"tr_later\").style.display='';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"tr_monitor_use\").style.display='';\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"#as_same\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t}else if(asType==6){ //포탈댓글전용알리미\r\n");
      out.write("\t\t\t\t$(\"#sender_mail\").css(\"display\", \"none\");\t//발신자 메일\r\n");
      out.write("\t\t\t\t$(\".ex_top\").css(\"display\", \"none\");\t//키워드 그룹, 사이트 그룹, 정보유형\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"#intervalFilter1\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\t$(\"#intervalFilter2\").css(\"display\", \"\");\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"#smsTimeFilter1\").css(\"display\", \"\");\r\n");
      out.write("\t\t\t\t$(\"#smsTimeFilter2\").css(\"display\", \"none\");\r\n");
      out.write("\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"tr_later\").style.display='';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"tr_monitor_use\").style.display='';\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"#as_same\").css(\"display\", \"\");\r\n");
      out.write("\t\t\t\tvar op = $(\".sameOp\").val();\r\n");
      out.write("\t\t\t\tif(op==\"1\"){\r\n");
      out.write("\t\t\t\t\t$(\"#as_same_text\").text(\"%\");\r\n");
      out.write("\t\t\t\t}else if(op==\"2\"){\r\n");
      out.write("\t\t\t\t\t$(\"#as_same_text\").text(\"건\");\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t$(\"#intervalFilter3\").css(\"display\", \"\");\r\n");
      out.write("\t\t\t$(\"#intervalFilter4\").css(\"display\", \"\");\r\n");
      out.write("\t\t\tif(asType==1)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"#intervalFilter1\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\t$(\"#intervalFilter2\").css(\"display\", \"\");\r\n");
      out.write("\t\t\t\t$(\"#intervalFilter3\").css(\"display\", \"\");\r\n");
      out.write("\t\t\t\t$(\"#intervalFilter4\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\t$(\"#smsTimeFilter1\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\t$(\"#smsTimeFilter2\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"#intervalFilter1\").css(\"display\", \"\");\r\n");
      out.write("\t\t\t\t$(\"#intervalFilter2\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\t$(\"#intervalFilter3\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\t$(\"#intervalFilter4\").css(\"display\", \"\");\r\n");
      out.write("\t\t\t\t$(\"#smsTimeFilter1\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\t$(\"#smsTimeFilter2\").css(\"display\", \"\");\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction asInfoTypeCheck(objValue)\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar f = document.alimi_detail;\r\n");
      out.write("\t\tvar asType = checkAsTypeValue();\r\n");
      out.write("\t\tvar asInfoType = objValue;\t\r\n");
      out.write("\t\tif(asInfoType==1)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tdocument.getElementById(\"smsFilter\").style.display='none';\t\t\r\n");
      out.write("\t\t\tdocument.getElementById(\"mailFilter\").style.display='';\t\t\r\n");
      out.write("\t\t\tif(asType==1)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"intervalFilter1\").style.display='';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"intervalFilter2\").style.display='none';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"intervalFilter3\").style.display='none';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"intervalFilter4\").style.display='none';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"smsTimeFilter1\").style.display='none';\t\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"smsTimeFilter2\").style.display='none';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"tr_later\").style.display='none';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"tr_monitor_use\").style.display='none';\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"intervalFilter1\").style.display='none';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"intervalFilter2\").style.display='';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"intervalFilter3\").style.display='none';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"intervalFilter4\").style.display='none';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"smsTimeFilter1\").style.display='';\t\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"smsTimeFilter2\").style.display='none';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"tr_later\").style.display='';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"tr_monitor_use\").style.display='';\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t//document.getElementById(\"smsTimeFilter\").style.display ='';\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tdocument.getElementById(\"mailFilter\").style.display='none';\t\r\n");
      out.write("\t\t\tdocument.getElementById(\"smsFilter\").style.display='';\r\n");
      out.write("\t\t\tif(asType==1)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"intervalFilter1\").style.display='none';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"intervalFilter2\").style.display='none';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"intervalFilter3\").style.display='';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"intervalFilter4\").style.display='none';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"smsTimeFilter1\").style.display='none';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"smsTimeFilter2\").style.display='none';\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"intervalFilter1\").style.display='none';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"intervalFilter2\").style.display='none';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"intervalFilter3\").style.display='none';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"intervalFilter4\").style.display='';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"smsTimeFilter1\").style.display='none';\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"smsTimeFilter2\").style.display='';\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t//document.getElementById(\"smsTimeFilter\").style.display ='';\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t//발송체크되면 발송관련  tr 보여줌\r\n");
      out.write("\tfunction monitorView(m){\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(m.value==\"Y\"){\r\n");
      out.write("\t\t\tdocument.getElementById(\"tr_monitor_cnt_info\").style.display='';\r\n");
      out.write("\t\t\tdocument.getElementById(\"tr_monitor_inspector\").style.display='';\r\n");
      out.write("\t\t\tdocument.getElementById(\"as_monitor_repeat_m00\").value=\"\";\r\n");
      out.write("\t\t\tdocument.getElementById(\"as_monitor_max_m00\").value=\"\";\r\n");
      out.write("\t\t\tdocument.getElementById(\"as_monitor_inspector00\").value=\"\";\r\n");
      out.write("\t\t}else if(m.value==\"N\"){\r\n");
      out.write("\t\t\tdocument.getElementById(\"tr_monitor_cnt_info\").style.display='none';\r\n");
      out.write("\t\t\tdocument.getElementById(\"tr_monitor_inspector\").style.display='none';\r\n");
      out.write("\t\t\tdocument.getElementById(\"as_monitor_repeat_m00\").value=\"\";\r\n");
      out.write("\t\t\tdocument.getElementById(\"as_monitor_max_m00\").value=\"\";\r\n");
      out.write("\t\t\tdocument.getElementById(\"as_monitor_inspector00\").value=\"\";\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\talert(\"오류발생\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("// \t숫자만입력받기함수들 시작\r\n");
      out.write("\tfunction onlyNumber(event,type){\r\n");
      out.write("\t\tevent = event || window.event;\r\n");
      out.write("\t\tvar keyID = (event.which) ? event.which : event.keyCode;\r\n");
      out.write("\t\tif(type==\"1\"){\r\n");
      out.write("\t\t\tif ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) {return;}\r\n");
      out.write("\t\t\telse{return false;}\r\n");
      out.write("\t\t}\t\r\n");
      out.write("\t\tif(type==\"2\"){\r\n");
      out.write("\t\t\tif ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 || keyID ==188){return;}\r\n");
      out.write("\t\t\telse{return false;}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\tfunction removeChar(event,type) {\r\n");
      out.write("\t\tevent = event || window.event;\r\n");
      out.write("\t\tvar keyID = (event.which) ? event.which : event.keyCode;\r\n");
      out.write("\t\tif(type==\"1\"){\r\n");
      out.write("\t\t\tif ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ){return;}\t\r\n");
      out.write("\t\t\telse{event.target.value = event.target.value.replace(/[^0-9,]/g, \"\");}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tif(type==\"2\"){\r\n");
      out.write("\t\t\tif ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 || keyID ==188){return;}\r\n");
      out.write("\t\t\telse{event.target.value = event.target.value.replace(/[^0-9,]/g, \"\");}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t//\t숫자만입력받기함수들 종료\r\n");
      out.write("\t\r\n");
      out.write("\t//추가부분 As_later_send\r\n");
      out.write("\tfunction checkAsLaterSendValue(){\r\n");
      out.write("\t\tvar obj = eval('document.alimi_detail.as_later_send1');\r\n");
      out.write("\t\tvar as_later_send='';\r\n");
      out.write("\t\tif(obj.length)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tfor(var i =0 ; i<obj.length; i++)\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tif(obj[i].checked)\r\n");
      out.write("\t\t\t\t{\t\t\t\t\r\n");
      out.write("\t\t\t\t\tas_later_send = obj[i].value;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}else{}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\treturn as_later_send;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction save()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar f = document.alimi_detail;\r\n");
      out.write("\t\tvar asInfoType = checkAsInfoTypeValue();\r\n");
      out.write("\t\tvar checkValue = false;\r\n");
      out.write("\t\t//alert(asType);\r\n");
      out.write("\t\tif(asInfoType==1)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tf.as_sms_time.value=checkSmsTimelValue();\t\t\t\t\r\n");
      out.write("\t\t\tf.as_interval.value=checkAsIntervalValue();\t\r\n");
      out.write("\t\t\tf.as_later_send.value =checkAsLaterSendValue();\r\n");
      out.write("\t\t\tif(f.as_title.value.length==0){alert('제목을 입력하여 주십시오.'); f.as_title.focus(); return;}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tvar checked_asType = \"\";\r\n");
      out.write("\t\t\t$(\"[name=as_type]\").each(function(){\r\n");
      out.write("\t\t\t\tif( $(this).attr(\"checked\") ){\r\n");
      out.write("\t\t\t\t\tchecked_asType = $(this).val();\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tconsole.log(checked_asType);\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tif(checked_asType != \"4\" && checked_asType != \"6\"){  // 포탈TOP, 포탈댓글전용 제외\r\n");
      out.write("\t\t\tcheckValue=checkXpValue();\r\n");
      out.write("\t\t\tcheckValue=checkYpValue();\r\n");
      out.write("\t\t\tif(checkValue==false){alert('키워드 그룹을 체크하여주십시오.'); return;}\r\n");
      out.write("\t\t\tcheckValue=checkSgSeqValue();\r\n");
      out.write("\t\t\tif(checkValue==false && f.sd_gsns.value.length==0){alert('하나의 사이트 그룹이나 사이트를 지정해주십시오.'); return;}\t\t\r\n");
      out.write("\t\t\tcheckValue=checkMtTypeValue();\r\n");
      out.write("\t\t\tif(checkValue==false){alert('정보유형을 그룹을 체크하여주십시오.'); return;}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t//정보수신자 체크\r\n");
      out.write("\t\t\tcheckAbAeqValue();\r\n");
      out.write("\t\t\t//f.as_sms_key.value='';\r\n");
      out.write("\t\t\t//f.as_sms_exkey.value='';\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tif($('input:radio[name=as_monitor_use1]:checked').val()==\"Y\"){\r\n");
      out.write("\t\t\t\tif($('#as_monitor_max_m00').val().length==0){alert('미발송대기시간항목을 입력하여 주십시오.'); $('#as_monitor_max_m00').focus(); return;}\r\n");
      out.write("\t\t\t\tif($('#as_monitor_repeat_m00').val().length==0){alert('미발송대기시간항목을 입력하여 주십시오.'); $('#as_monitor_repeat_m00').focus(); return;}\r\n");
      out.write("\t\t\t\tif($('#as_monitor_inspector00').val().length==0){alert('수신자를 작성해주세요.'); $('#as_monitor_inspector00').focus(); return;}\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tf.as_monitor_use.value\t= \"Y\";\r\n");
      out.write("\t\t\t\tf.as_monitor_max_m.value = $('#as_monitor_max_m00').val();\r\n");
      out.write("\t\t\t\tf.as_monitor_repeat_m.value\t= $('#as_monitor_repeat_m00').val();\r\n");
      out.write("\t\t\t\tf.as_monitor_inspector.value = $('#as_monitor_inspector00').val();\r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tf.as_monitor_use.value\t= \"N\";\r\n");
      out.write("\t\t\t\tf.as_monitor_max_m.value = '0';\r\n");
      out.write("\t\t\t\tf.as_monitor_repeat_m.value\t= '0';\r\n");
      out.write("\t\t\t\tf.as_monitor_inspector.value = '';\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tif(f.as_title.value.length==0){alert('제목을 입력하여 주십시오.'); f.as_title.focus(); return;}\t\t\t\r\n");
      out.write("\t\t\tf.as_sms_time.value=checkSmsTimelValue();\t\t\r\n");
      out.write("\t\t\tf.as_interval.value=checkAsIntervalValue();\r\n");
      out.write("\t\t\tcheckAbAeqValue();\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tf.target = '';\r\n");
      out.write("\t\tf.action ='alimi_setting_prc.jsp';\r\n");
      out.write("\t\tf.submit();\r\n");
      out.write("\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction goList()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar f = document.alimi_detail;\r\n");
      out.write("\t\tf.target='';\r\n");
      out.write("\t\tf.action = 'alimi_setting_list.jsp';\r\n");
      out.write("\t\tf.submit();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction popSiteList(as_seq)\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar f = document.alimi_detail;\r\n");
      out.write("\t\tif(f.sd_gsns.value.length>0)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\twindow.open(\"pop_sitelist.jsp?as_seq=\"+as_seq+\"&selectedSdGsn=\"+f.sd_gsns.value+\"&mode=\"+f.mode.value, \"siteList\", \"width=850,height=600,scrollbars=no\");\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\twindow.open(\"pop_sitelist.jsp\", \"siteList\", \"width=850,height=600,scrollbars=no\");\r\n");
      out.write("\t\t}\t\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\tfunction changeTopKeywordGroup()\r\n");
      out.write("\t{\r\n");
      out.write("\t    ajax.post('sub_keywordgroup_radio.jsp','alimi_detail','subKewyrodGroupView');\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\tfunction ApproveDecimal(keyCode){\r\n");
      out.write("\r\n");
      out.write("\t\tvar keyChar;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(keyCode){\r\n");
      out.write("\t\t\tkeyChar = keyCode = String.fromCharCode(keyCode);\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\treturn true;\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\tif((\"0123456789\").indexOf(keyChar) > -1){\t\t\r\n");
      out.write("\t\t\tevent.returnValue = true; \t\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tevent.returnValue = false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<form id=\"alimi_detail\" name=\"alimi_detail\" method=\"post\">\r\n");
      out.write("<input type=\"hidden\" name=\"mode\" value=\"");
      out.print(mode);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"nowpage\" value=\"");
      out.print(nowpage);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"as_seq\" value=\"");
      out.print(as_seq);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"ab_seqs\">\r\n");
      out.write("<input type=\"hidden\" name=\"as_sms_time\">\r\n");
      out.write("<input type=\"hidden\" name=\"as_interval\">\r\n");
      out.write("<input type=\"hidden\" name=\"k_xps\">\r\n");
      out.write("<input type=\"hidden\" name=\"k_yps\">\r\n");
      out.write("<input type=\"hidden\" name=\"as_sitetype\">\r\n");
      out.write("<input type=\"hidden\" name=\"sg_seqs\">\r\n");
      out.write("<input type=\"hidden\" name=\"mt_types\">\r\n");
      out.write("<input type=\"hidden\" name=\"as_later_send\">\r\n");
      out.write("<input type=\"hidden\" name=\"as_monitor_use\">\r\n");
      out.write("<input type=\"hidden\" name=\"as_monitor_max_m\">\r\n");
      out.write("<input type=\"hidden\" name=\"as_monitor_repeat_m\">\r\n");
      out.write("<input type=\"hidden\" name=\"as_monitor_inspector\">\r\n");

	if(mode.equals("INSERT")){

      out.write("\r\n");
      out.write("<input type=\"hidden\" name=\"sd_gsns\">\r\n");
      out.write("<input name=\"as_infotype\" type=\"hidden\" value=\"1\"/>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\t$(document).ready(function(){\r\n");
      out.write("\t\t$(\"#sender_mail\").css(\"display\", \"\");\r\n");
      out.write("\t\t$(\"#as_same\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(\".sameOp\").change(function(){\r\n");
      out.write("\t\t\tvar op = $(this).val();\r\n");
      out.write("\t\t\tif(op==\"1\"){\r\n");
      out.write("\t\t\t\t$(\"#as_same_text\").text(\"%\");\r\n");
      out.write("\t\t\t}else if(op==\"2\"){\r\n");
      out.write("\t\t\t\t$(\"#as_same_text\").text(\"건\");\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t});\r\n");
      out.write("</script>\r\n");
      out.write("<table style=\"width:850px\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"body_bg_top\" valign=\"top\">\r\n");
      out.write("\t\t<table align=\"center\" style=\"width:820px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t<!-- 타이틀 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"tit_bg\" style=\"height:37px;padding-top:0px;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/alimi/tit_icon.gif\" /><img src=\"../../../images/admin/alimi/tit_0509.gif\" /></td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">관리자</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">알리미 설정관리</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td height=\"15\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 타이틀 끝 -->\r\n");
      out.write("\t\t\t<!-- 게시판 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"height:30px;\"><span class=\"sub_tit\">기본 설정</span></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<table id=\"board_write\" border=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">제목</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td><input style=\"width:80%;\" name=\"as_title\" type=\"text\" class=\"textbox2\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">발송매체</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input name=\"as_type\" type=\"radio\" value=\"1\" onclick=\"asTypeCheck(this.value);\" checked>이메일</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<!-- <li style=\"padding-right:20px;\"><input name=\"as_type\" type=\"radio\" value=\"2\" onclick=\"asTypeCheck(this.value);\">긴급 SMS 발송</li> -->\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input name=\"as_type\" type=\"radio\" value=\"3\" onclick=\"asTypeCheck(this.value);\">R-rimi 발송</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input name=\"as_type\" type=\"radio\" value=\"4\" onclick=\"asTypeCheck(this.value);\">포탈TOP 발송</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<!-- <li style=\"padding-right:20px;\"><input name=\"as_type\" type=\"radio\" value=\"5\" onclick=\"asTypeCheck(this.value);\">트위터 전용 R-rimi 발송</li> -->\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input name=\"as_type\" type=\"radio\" value=\"6\" onclick=\"asTypeCheck(this.value);\">포탈댓글전용알리미</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t</ul>\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<!-- <tr style=\"display: none;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">발송정보</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input name=\"as_infotype\" type=\"radio\" value=\"1\" onclick=\"asInfoTypeCheck(this.value)\" checked>일반수집</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t</ul>\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr> -->\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">정보 발송 여부</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input id=\"input_radio_00\" name=\"as_chk\" type=\"radio\" value=\"1\" checked /><label for=\"input_radio_00\">발송</label></li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input id=\"input_radio_01\" name=\"as_chk\" type=\"radio\" value=\"2\"><label for=\"input_radio_01\">발송중지</label></li>\r\n");
      out.write("\t\t\t\t\t\t\t\t</ul>\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr id=\"sender_mail\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">발신자 메일</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<select name=\"sendMail\" >\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<option value=\"\">선택하세요</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t");

										String tmp[] = new String[3];
										ArrayList mail = new ArrayList(); 
										mail = asDao.getSendMailSetting();
										if(mail.size() > 0 ){
											for(int i=0; i <mail.size(); i++){
												tmp = (String[])mail.get(i);
										
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(tmp[0]);
      out.write('"');
      out.write('>');
      out.print(tmp[2]);
      out.write("</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t");
	}
										}
										
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"padding-top:15px;height:45px;\"><span class=\"sub_tit\">발송 조건</span></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<table id=\"board_write\" border=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr class=\"ex_top\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">키워드그룹</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
	for( int i=0 ; i < xpList.size() ; i++ ) {
		keywordInfo KGset = (keywordInfo)xpList.get(i);	
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div style=\"width:150px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;float:left\" title=\"");
      out.print(KGset.getK_Value());
      out.write("\"><input type=\"radio\" name=\"k_xp\" value=\"");
      out.print(KGset.getK_Xp());
      out.write("\" onclick=\"changeTopKeywordGroup();\">");
      out.print(KGset.getK_Value());
      out.write("</div>\r\n");
	}	
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr id=\"subKewyrodGroupView\">\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr  class=\"ex_top\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">사이트 그룹</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"sg_seqALL\"  value=\"0\" onclick=\"checkAllSgSeq(this.checked);\">전체\r\n");
	for( int i=0 ; i < sgList.size() ; i++ ) {
		SitegroupBean SGinfo = (SitegroupBean)sgList.get(i);
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"sg_seq\" value=\"");
      out.print(SGinfo.get_seq());
      out.write('"');
      out.write('>');
      out.print( SGinfo.get_name());
      out.write('\r');
      out.write('\n');
	} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr class=\"ex_top\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">사이트 리스트</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"normal\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td class=\"normal\"><img src=\"../../../images/admin/alimi/btn_alrimiadd.gif\" onclick=\"popSiteList('");
      out.print(as_seq);
      out.write("');\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td class=\"normal\" id=\"siteListCnt\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t</table></td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr class=\"ex_top\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">정보유형</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input type=\"checkbox\" name=\"mt_type\" value=\"1\">기사</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li><input type=\"checkbox\" name=\"mt_type\" value=\"2\">게시물</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t</ul> \r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr id=\"smsTimeFilter1\" style=\"display:none\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">발송시간</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input name=\"as_sms_time1\" type=\"checkbox\" value=\"1\" checked>평일 근무시간 ( 06:00 ~ 22:00 )<br>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input name=\"as_sms_time1\" type=\"checkbox\" value=\"2\">평일 근무외시간  ( 22:00 ~ 명일 06:00 )<br>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input name=\"as_sms_time1\" type=\"checkbox\" value=\"3\">주말 근무시간1 ( 06:00 ~ 24:00 )<br>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input name=\"as_sms_time1\" type=\"checkbox\" value=\"4\">주말 근무시간2 ( 09:00 ~ 21:00 )<br>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input name=\"as_sms_time1\" type=\"checkbox\" value=\"5\">주말 근무외시간 ( 22:00 ~ 명일 06:00 )<br>\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">발송간격</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<ul id=\"intervalFilter1\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input type=\"radio\" name=\"as_interval1\" value=\"0\" checked>건별</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input type=\"radio\" name=\"as_interval1\" value=\"30\">30분</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input type=\"radio\" name=\"as_interval1\" value=\"60\">1시간</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input type=\"radio\" name=\"as_interval1\" value=\"180\">3시간</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t</ul> \r\n");
      out.write("\t\t\t\t\t\t\t\t<ul id=\"intervalFilter2\" style=\"display:none\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input type=\"radio\" name=\"as_interval2\" value=\"0\" checked>건별</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t</ul> \r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr id=\"as_same\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\"><select name=\"as_same_op\" class=\"sameOp\"><option value=\"1\">유사율</option><option value=\"2\">유사건수</option></select></span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input type=\"text\" maxlength=\"3\" size=\"3\" name=\"as_same_value\" value=\"0\" onkeypress=\"ApproveDecimal(window.event.keyCode)\" style=\"text-align: right\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<span id=\"as_same_text\">%</span>\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr id=\"tr_later\" style=\"display:none\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">시간외 알람</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input name=\"as_later_send1\" type=\"radio\" value=\"Y\">몰아서 받기</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input name=\"as_later_send1\" type=\"radio\" value=\"N\" checked>받지않기</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</ul>\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr id=\"tr_monitor_use\" style=\"display:none\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">감시SMS발송</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input name=\"as_monitor_use1\" type=\"radio\" value=\"Y\" onclick=\"monitorView(this)\">발송</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input name=\"as_monitor_use1\" type=\"radio\" value=\"N\" onclick=\"monitorView(this)\" checked >미발송</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</ul>\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr id=\"tr_monitor_cnt_info\" style=\"display: none;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">미발송대기시간</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<input type=\"text\" style=\"text-align: right;\" maxlength=\"4\" size=\"5\" id=\"as_monitor_max_m00\" onkeydown=\"return onlyNumber(event,1)\" onkeyup=\"removeChar(event,1)\">분 이상부터\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<input type=\"text\" style=\"text-align: right;\" maxlength=\"4\" size=\"5\" id=\"as_monitor_repeat_m00\"  onkeydown=\"return onlyNumber(event,1)\" onkeyup=\"removeChar(event,1)\">분 마다발송\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr id=\"tr_monitor_inspector\" style=\"display: none;\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">감시SMS수신자</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<input style=\"width:60%;\" type=\"text\" style=\"text-align: right;\" id=\"as_monitor_inspector00\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<div style=\"margin-top: 5px; margin-bottom: 1px; font-size: 90%\" > * 다수일 경우 세미콜론(;)처리 ex) 010-1234-4567;010-4567-8912 </div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t<!-- <tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">제목 포함 필터</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input name=\"as_sms_key\" type=\"text\" class=\"txtbox\" size=\"50\" value=\"\">\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">제목 제외 필터</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input name=\"as_sms_exkey\" type=\"text\" class=\"txtbox\" size=\"50\" value=\"\">\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr> -->\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("<!-- <table id=\"smsFilter\" style=\"display:none\" width=\"630\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\">     \r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td width=\"120\" height=\"30\" align=\"center\" bgcolor=\"#F0F0F0\" class=\"table_top\" style=\"padding: 4px 0px 0px 0px;\"><strong>제목 포함 필터</strong></td>\r\n");
      out.write("\t\t<td width=\"510\" style=\"padding: 5px 0px 5px 10px;\"><input name=\"as_sms_key\" type=\"text\" class=\"txtbox\" size=\"50\"></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td colspan=\"2\"  bgcolor=\"#D3D3D3\" width=\"1\" height=\"1\"></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td width=\"120\" height=\"30\" align=\"center\" bgcolor=\"#F0F0F0\" class=\"table_top\" style=\"padding: 4px 0px 0px 0px;\"><strong>제목 제외 필터</strong></td>\r\n");
      out.write("\t\t<td width=\"510\" style=\"padding: 5px 0px 5px 10px;\"><input name=\"as_sms_exkey\" type=\"text\" class=\"txtbox\" size=\"50\"></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td colspan=\"2\"  bgcolor=\"#D3D3D3\" width=\"1\" height=\"1\"></td>\r\n");
      out.write("\t</tr>\t\t      \r\n");
      out.write("\t<tr id=\"smsTimeFilter2\" style=\"display:none\">\t\t\t      \r\n");
      out.write("\t\t<td width=\"120\" height=\"30\" align=\"center\" bgcolor=\"#F0F0F0\" class=\"table_top\" style=\"padding: 4px 0px 0px 0px;\"><strong>발송 시간</strong></td>\r\n");
      out.write("\t\t<td width=\"510\" style=\"padding: 5px 0px 5px 10px;\">\r\n");
      out.write("\t\t\t<input name=\"as_sms_time2\" type=\"checkbox\" value=\"1\" checked>평일 근무시간 ( 06:00 ~ 22:00 )<br>\r\n");
      out.write("\t\t\t<input name=\"as_sms_time2\" type=\"checkbox\" value=\"2\">평일 근무외시간  ( 22:00 ~ 명일 06:00 )<br>\r\n");
      out.write("\t\t\t<input name=\"as_sms_time2\" type=\"checkbox\" value=\"3\">주말 근무시간1 ( 06:00 ~ 24:00 )<br>\r\n");
      out.write("\t\t\t<input name=\"as_sms_time2\" type=\"checkbox\" value=\"4\">주말 근무시간2 ( 09:00 ~ 21:00 )<br>\r\n");
      out.write("\t\t\t<input name=\"as_sms_time2\" type=\"checkbox\" value=\"4\">주말 근무외시간 ( 22:00 ~ 명일 06:00 )\r\n");
      out.write("\t\t</td>\t\t\t\t  \t\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td colspan=\"2\"  bgcolor=\"#D3D3D3\" width=\"1\" height=\"1\"></td>\r\n");
      out.write("\t</tr>\t\t       \r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td width=\"120\" height=\"30\" align=\"center\" bgcolor=\"#F0F0F0\" class=\"table_top\" style=\"padding: 4px 0px 0px 0px;\"><strong>발송간격</strong></td>\r\n");
      out.write("\t\t<td width=\"510\" style=\"padding: 5px 0px 5px 10px;\">\t\t  \t\t \t\r\n");
      out.write("\t\t<table id=\"intervalFilter3\" width=\"510\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"display:none\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t<input type=\"radio\" name=\"as_interval3\" value=\"0\" checked>건별&nbsp;&nbsp;\r\n");
      out.write("\t\t\t\t  \t<input type=\"radio\" name=\"as_interval3\" value=\"30\">30분&nbsp;&nbsp;\r\n");
      out.write("\t\t\t\t  \t<input type=\"radio\" name=\"as_interval3\" value=\"60\">1시간&nbsp;&nbsp;\r\n");
      out.write("\t\t\t\t  \t<input type=\"radio\" name=\"as_interval3\" value=\"180\">3시간&nbsp;&nbsp;\t\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t<table id=\"intervalFilter4\" width=\"510\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"display:none\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t<input type=\"radio\" name=\"as_interval4\" value=\"0\" checked>건별&nbsp;&nbsp;\t\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>  -->\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t<iframe name=\"frmReceiver\" src=\"ifrm_receiver_list.jsp\" frameborder=\"0\" scrolling=\"no\" width=\"820\" height=\"200\"></iframe>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 끝 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"height:40px;\" align=\"center\"><img src=\"../../../images/admin/alimi/btn_save2.gif\" onclick=\"save();\" style=\"cursor:pointer;\"/><img src=\"../../../images/admin/alimi/btn_cancel.gif\" onclick=\"goList();\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");

	}else if(mode.equals("UPDATE")){
		if(asBean!=null){

      out.write("\r\n");
      out.write("<input type=\"hidden\" name=\"sd_gsns\" value=\"");
      out.print(asBean.getSd_gsns());
      out.write("\">\r\n");
      out.write("<input name=\"as_infotype\" type=\"hidden\" value=\"1\"/>\r\n");
      out.write("<input type=\"hidden\" name=\"as_last_num\" value=\"");
      out.print(asBean.getAs_last_num());
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"as_same_op\" value=\"");
      out.print(asBean.getAs_same_op());
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" id=\"as_same_value\" name=\"as_same_value\" value=\"\">\r\n");
      out.write("\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write("\t$('document').ready(function(){\r\n");
      out.write("\t\tinitMain();\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar asType = \"");
      out.print(asBean.getAs_type());
      out.write("\";\r\n");
      out.write("\t\tvar asSameOp = \"");
      out.print(asBean.getAs_same_op());
      out.write("\";\r\n");
      out.write("\t\t$(\"input[name='as_type']\").not(\"input[value='\" + asType + \"']\").parent().css(\"display\", \"none\");\r\n");
      out.write("\t\t$(\".ex_top\").css(\"display\", \"\");\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(asType==\"1\"){\r\n");
      out.write("\t\t\t$(\"#sender_mail\").css(\"display\", \"\");\r\n");
      out.write("\t\t\t$(\"#as_same\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}else if(asType==\"3\" || asType==\"6\"){\r\n");
      out.write("\t\t\t$(\"#sender_mail\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t$(\"#as_same\").css(\"display\", \"\");\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tif(asType==\"6\"){\r\n");
      out.write("\t\t\t\t$(\".ex_top\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\t$(\"#subKewyrodGroupView\").css(\"display\", \"none\");\t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tif(asSameOp==\"1\"){\r\n");
      out.write("\t\t\t\t$(\"#as_same > th > span > label\").text(\"유사율\");\r\n");
      out.write("\t\t\t\t$(\"#as_same > td > label\").text(\"");
      out.print(asBean.getAs_same_percent());
      out.write(" %\");\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"#as_same_value\").val(\"");
      out.print(asBean.getAs_same_percent());
      out.write("\");\r\n");
      out.write("\t\t\t}else if(asSameOp==\"2\"){\r\n");
      out.write("\t\t\t\t$(\"#as_same > th > span > label\").text(\"유사건수\");\r\n");
      out.write("\t\t\t\t$(\"#as_same > td > label\").text(\"");
      out.print(asBean.getAs_same_cnt());
      out.write(" 건\");\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$(\"#as_same_value\").val(\"");
      out.print(asBean.getAs_same_cnt());
      out.write("\");\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}else if(asType==\"4\"){\r\n");
      out.write("\t\t\t$(\"#sender_mail\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t$(\"#as_same\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t$(\".ex_top\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("</script>\r\n");
      out.write("<table style=\"width:850px;height:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td class=\"body_bg_top\" valign=\"top\">\r\n");
      out.write("\t\t<table align=\"center\" style=\"width:820px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t<!-- 타이틀 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td class=\"tit_bg\" style=\"height:37px;padding-top:0px;\">\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td><img src=\"../../../images/admin/alimi/tit_icon.gif\" /><img src=\"../../../images/admin/alimi/tit_0509.gif\" /></td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">\r\n");
      out.write("\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_home\">HOME</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow\">관리자</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"navi_arrow2\">알리미 설정관리</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td height=\"15\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 타이틀 끝 -->\r\n");
      out.write("\t\t\t<!-- 게시판 시작 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"height:30px;\"><span class=\"sub_tit\">기본 설정</span></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<table id=\"board_write\" border=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">제목</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td><input style=\"width:80%;\" name=\"as_title\" type=\"text\" class=\"textbox2\" value=\"");
      out.print(asBean.getAs_title() );
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">발송매체</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input name=\"as_type\" type=\"radio\" value=\"1\" onclick=\"asTypeCheck(this.value);\" ");
if(asBean.getAs_type().equals("1")){out.println("checked");}
      out.write(">이메일</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input name=\"as_type\" type=\"radio\" value=\"3\" onclick=\"asTypeCheck(this.value);\" ");
if(asBean.getAs_type().equals("3")){out.println("checked");}
      out.write(">R-rimi 발송</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input name=\"as_type\" type=\"radio\" value=\"4\" onclick=\"asTypeCheck(this.value);\"");
if(asBean.getAs_type().equals("4")){out.println("checked");}
      out.write(">포탈TOP 발송</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input name=\"as_type\" type=\"radio\" value=\"6\" onclick=\"asTypeCheck(this.value);\"");
if(asBean.getAs_type().equals("6")){out.println("checked");}
      out.write(">포탈댓글전용알리미</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t</ul>\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">정보 발송 여부</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input name=\"as_chk\" type=\"radio\" value=\"1\" ");
if(asBean.getAs_chk().equals("1")){out.println("checked");} 
      out.write(">발송</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input name=\"as_chk\" type=\"radio\" value=\"2\" ");
if(asBean.getAs_chk().equals("2")){out.println("checked");} 
      out.write(">발송중지</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t</ul>\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr id=\"sender_mail\" class=\"ex_top\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">발신자 메일</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<select name=\"sendMail\" >\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<option value=\"\">선택하세요</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t");

										String tmp[] = new String[3];
										ArrayList mail = new ArrayList(); 
										mail = asDao.getSendMailSetting();
										if(mail.size() > 0 ){
											for(int i=0; i <mail.size(); i++){
												tmp = (String[])mail.get(i);
										
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t <option value=\"");
      out.print(tmp[0]);
      out.write('"');
if(tmp[1].equals(asBean.getAs_seq())){out.println("selected");} 
      out.write('>');
      out.print(tmp[2]);
      out.write("</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t");
	}
										}
										
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td style=\"padding-top:15px;height:45px;\"><span class=\"sub_tit\">발송 조건</span></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<table id=\"board_write\" border=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr class=\"ex_top\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">키워드그룹</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");

	String[] xp = asBean.getKg_xps().split(",");
	for( int i=0 ; i < xpList.size() ; i++ ) {
		keywordInfo KGset = (keywordInfo)xpList.get(i);

      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div style=\"width:150px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;float:left\" title=\"");
      out.print(KGset.getK_Value());
      out.write("\"><input type=\"radio\" name=\"k_xp\" value=\"");
      out.print(KGset.getK_Xp());
      out.write("\" onclick=\"changeTopKeywordGroup();\" ");
for(int j=0; j < xp.length; j++){if( xp[j].equals(KGset.getK_Xp())){out.println("checked");}}
      out.write('>');
      out.print(KGset.getK_Value());
      out.write("</div>\r\n");
	}	
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr id=\"subKewyrodGroupView\">\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr class=\"ex_top\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">사이트 그룹</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t      <input type=\"checkbox\" name=\"sg_seqALL\"  value=\"0\" onclick=\"checkAllSgSeq(this.checked);\">전체\r\n");

	String[] site = asBean.getSg_seqs().split(",");
	for( int i=0 ; i < sgList.size() ; i++ ) {
		SitegroupBean SGinfo = (SitegroupBean)sgList.get(i);

      out.write("\t\t\r\n");
      out.write("\t\t\t\t\t\t\t      <input type=\"checkbox\" name=\"sg_seq\" value=\"");
      out.print(SGinfo.get_seq());
      out.write('"');
      out.write(' ');
for( int j=0; j < site.length; j++){if(site[j].equals(Integer.toString(SGinfo.get_seq()))){out.println("checked");}else{out.println("");}}
      out.write('>');
      out.print( SGinfo.get_name());
      out.write('\r');
      out.write('\n');
	}	
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr class=\"ex_top\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">사이트 리스트</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"normal\" ><img src=\"../../../images/admin/alimi/btn_alrimiadd.gif\" onclick=\"popSiteList('");
      out.print(as_seq);
      out.write("');\" style=\"cursor:pointer;\"/>\r\n");
      out.write("\t\t\t\t\t\t\t\t<span id=\"siteListCnt\" style=\"vertical-align: middle;\">");
      out.print(sdGsnCnt+"개의 사이트가 저장 되었습니다.");
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr class=\"ex_top\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">정보유형</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input type=\"checkbox\" name=\"mt_type\" value=\"1\" ");
      out.print(mtTypeCheck1);
      out.write(">기사</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li><input type=\"checkbox\" name=\"mt_type\" value=\"2\" ");
      out.print(mtTypeCheck2);
      out.write(">게시물</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t</ul> \r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr id=\"smsTimeFilter1\" style=\"display:");
if(asBean.getAs_type().equals("1")){out.println("none");}else{out.println("");}
      out.write("\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">발송시간</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input name=\"as_sms_time1\" type=\"checkbox\" value=\"1\" ");
      out.print(smsTimeCheck1);
      out.write(">평일 근무시간 ( 06:00 ~ 22:00 )<br>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input name=\"as_sms_time1\" type=\"checkbox\" value=\"2\" ");
      out.print(smsTimeCheck2);
      out.write(">평일 근무외시간  ( 22:00 ~ 명일 06:00 )<br>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input name=\"as_sms_time1\" type=\"checkbox\" value=\"3\" ");
      out.print(smsTimeCheck3);
      out.write(">주말 근무시간1 ( 06:00 ~ 24:00 )<br>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input name=\"as_sms_time1\" type=\"checkbox\" value=\"4\" ");
      out.print(smsTimeCheck4);
      out.write(">주말 근무시간2 ( 09:00 ~ 21:00 )<br>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input name=\"as_sms_time1\" type=\"checkbox\" value=\"5\" ");
      out.print(smsTimeCheck5);
      out.write(">주말 근무외시간 ( 22:00 ~ 명일 06:00 )<br>\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">발송간격</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<ul id=\"intervalFilter1\" style=\"display:");
      out.print(styleDisplay1);
      out.write("\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input type=\"radio\" name=\"as_interval1\" value=\"0\" ");
if(asBean.getAs_interval().equals("0")){out.println("checked");} 
      out.write(">건별</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input type=\"radio\" name=\"as_interval1\" value=\"30\" ");
if(asBean.getAs_interval().equals("30")){out.println("checked");} 
      out.write(">30분</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input type=\"radio\" name=\"as_interval1\" value=\"60\" ");
if(asBean.getAs_interval().equals("60")){out.println("checked");} 
      out.write(">1시간</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input type=\"radio\" name=\"as_interval1\" value=\"180\" ");
if(asBean.getAs_interval().equals("180")){out.println("checked");} 
      out.write(">3시간</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t</ul> \r\n");
      out.write("\t\t\t\t\t\t\t\t<ul id=\"intervalFilter2\" style=\"display:");
      out.print(styleDisplay2);
      out.write("\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input type=\"radio\" name=\"as_interval2\" value=\"0\" checked>건별</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t</ul> \r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr id=\"as_same\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\"><label></label></span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<label></label>\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr id=\"tr_later\" style=\"display: ");
if(asBean.getAs_type().equals("1")){out.println("none");}
      out.write(";\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">시간외 알람</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input name=\"as_later_send1\" type=\"radio\" value=\"Y\" ");
if(asBean.getAs_later_send().equals("Y")){out.println("checked");} 
      out.write(">몰아서받기</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input name=\"as_later_send1\" type=\"radio\" value=\"N\" ");
if(asBean.getAs_later_send().equals("N")){out.println("checked");} 
      out.write(">받지않기</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr id=\"tr_monitor_use\" style=\"display: ");
if(asBean.getAs_type().equals("1")){out.println("none");}
      out.write(";\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">감시SMS발송</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input name=\"as_monitor_use1\" type=\"radio\" value=\"Y\" onclick=\"monitorView(this)\" ");
if(asBean.getAs_monitor_use().equals("Y")){out.println("checked");} 
      out.write("checked>발송</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li style=\"padding-right:20px;\"><input name=\"as_monitor_use1\" type=\"radio\" value=\"N\" onclick=\"monitorView(this)\" ");
if(asBean.getAs_monitor_use().equals("N")){out.println("checked");} 
      out.write(">미발송</li>\r\n");
      out.write("\t\t\t\t\t\t\t\t</ul>\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr id=\"tr_monitor_cnt_info\" style=\"display: ");
if(!asBean.getAs_monitor_use().equals("Y")){out.println("none");} 
      out.write(";\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">미발송대기시간</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input type=\"text\" style=\"text-align: right;\" maxlength=\"4\" size=\"5\" id=\"as_monitor_max_m00\" onkeydown=\"return onlyNumber(event,1)\" onkeyup=\"removeChar(event,1)\" value=\"");
      out.print(asBean.getAs_monitor_max_m() );
      out.write("\">분 이상부터\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input type=\"text\" style=\"text-align: right;\" maxlength=\"4\" size=\"5\" id=\"as_monitor_repeat_m00\"  onkeydown=\"return onlyNumber(event,1)\" onkeyup=\"removeChar(event,1)\" value=\"");
      out.print(asBean.getAs_monitor_repeat_m() );
      out.write("\">분 마다발송\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr id=\"tr_monitor_inspector\" style=\"display: ");
if(!asBean.getAs_monitor_use().equals("Y")){out.println("none");} 
      out.write(";\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<th><span class=\"board_write_tit\">감시SMS수신자</span></th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input style=\"width:60%;\" type=\"text\" style=\"text-align: right;\" id=\"as_monitor_inspector00\" value=\"");
      out.print(asBean.getAs_monitor_inspector());
      out.write("\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div style=\"margin-top: 5px; margin-bottom: 1px; font-size: 90%\" > * 다수일 경우 세미콜론(;)처리 ex) 010-1234-4567;010-4567-8912</div>\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t<iframe name=\"frmReceiver\" src=\"ifrm_receiver_list.jsp?ab_seq=");
      out.print(ab_seq);
      out.write("\" frameborder=\"0\" scrolling=\"no\" width=\"820\" height=\"200\"></iframe>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<!-- 게시판 끝 -->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td style=\"height:40px;\" align=\"center\"><img src=\"../../../images/admin/alimi/btn_save2.gif\" onclick=\"save();\" style=\"cursor:pointer;\"/><img src=\"../../../images/admin/alimi/btn_cancel.gif\" onclick=\"goList();\" style=\"cursor:pointer;\"/></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");

		}
	} 

      out.write("\r\n");
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
