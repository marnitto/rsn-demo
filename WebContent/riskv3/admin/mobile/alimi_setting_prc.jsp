<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="	java.util.ArrayList
                 	,risk.mobile.AlimiSettingBean
					,risk.mobile.AlimiSettingDao
					,risk.util.ParseRequest
					,risk.admin.site.SitegroupBean
				 	,risk.admin.site.SiteMng
				 	,risk.search.userEnvMgr
				 	,risk.search.keywordInfo
				 	,risk.admin.classification.classificationMgr
				 	,risk.admin.classification.clfBean
				 	,java.util.List
					" 
%>
<%	
  
	ParseRequest pr = new ParseRequest(request);
	AlimiSettingBean asBean = new AlimiSettingBean();
	AlimiSettingDao asDao = new AlimiSettingDao();	
	
	int result = 0;
	String mode = null;
	String nowpage = null;
	String as_seq = null;
	String as_seqs = null;
	String as_title = null;
	String as_chk = null;
	String as_infotype = null;
	String as_type = null;
	String k_xps = null;
	String sg_seqs = null;
	String sd_gsns = null;
	String mt_types = null;
	String as_sms_key = null;
	String as_sms_exkey = null;
	String as_sms_time = null;
	String as_interval = null;
	String ab_seqs = null;	
	String as_sms_day = null;
	String as_sms_stime = null;
	String as_sms_etime = null;
	int as_same_cnt = 0;
	
	pr.printParams();
	mode = pr.getString("mode");
	nowpage = pr.getString("nowpage","1");
	as_seq = pr.getString("as_seq");
	as_seqs = pr.getString("as_seqs");
	as_title = pr.getString("as_title");
	as_chk = pr.getString("as_chk");
	as_type = pr.getString("as_type");
	as_infotype = pr.getString("as_infotype","1"); //현재 일반수집만 들어옴
	k_xps = pr.getString("k_xps");
	sg_seqs = pr.getString("sg_seqs");
	sd_gsns = pr.getString("sd_gsns");
	mt_types = pr.getString("mt_types");
	as_sms_key = pr.getString("as_sms_key");
	as_sms_exkey = pr.getString("as_sms_exkey");
	as_sms_time = pr.getString("as_sms_time");
	as_interval = pr.getString("as_interval","0");
	ab_seqs = pr.getString("ab_seqs");
	as_sms_day = pr.getString("as_smsday");
	as_sms_stime = pr.getString("as_sms_stime");
	as_sms_etime = pr.getString("as_sms_etime");


	asBean = new AlimiSettingBean();
	asBean.setAs_seq(as_seq);
	asBean.setAs_title(as_title);	
	asBean.setAs_chk(as_chk);
	asBean.setAs_type(as_type);
	asBean.setAs_infotype(as_infotype);
	asBean.setKg_xps(k_xps);
	asBean.setSg_seqs(sg_seqs);
	asBean.setSd_gsns(sd_gsns);
	asBean.setMt_types(mt_types);
	asBean.setAs_sms_key(as_sms_key);
	asBean.setAs_sms_exkey(as_sms_exkey);
	asBean.setAs_sms_time(as_sms_time);
	asBean.setAs_interval(as_interval);	
	asBean.setAs_sms_day(as_sms_day);
	asBean.setAs_sms_stime(as_sms_stime);
	asBean.setAs_sms_etime(as_sms_etime);
	asBean.setAs_same_cnt(as_same_cnt);
	
	
	if(mode.equals("INSERT"))
	{		
		result = asDao.insertAlimisSet(asBean , ab_seqs);
		
	}else if(mode.equals("UPDATE")){
		
		result = asDao.updateReportSet(asBean , ab_seqs);
	}else if(mode.equals("DELETE")){
		result = asDao.deleteReportSet(as_seqs);
	}
	
%>
<script language="javascript">
<!--
	parent.contentsFrame.location.href = 'alimi_setting_list.jsp?nowpage=<%=nowpage%>';
//-->
</script>