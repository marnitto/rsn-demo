<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
	String k_yps = null;
	String sg_seqs = null;
	String sd_gsns = null;
	String mt_types = null;
	String as_sms_key = null;
	String as_sms_exkey = null;
	String as_sms_time = null;
	String as_interval = null;
	String ab_seqs = null;
	String as_last_num = null;
	int as_same_op = 0;
	int as_same_percent	 = 0;
	int as_same_cnt	 = 0;
	
	String as_later_send = "";
	String as_monitor_use= "";
	String as_monitor_max_m= "";
	String as_monitor_repeat_m= "";
	String as_monitor_inspector= "";
	int as_monitor_send_cnt = 0;
	
	pr.printParams();
	mode = pr.getString("mode");
	nowpage = pr.getString("nowpage","1");
	as_seq = pr.getString("as_seq");
	as_seqs = pr.getString("as_seqs");
	as_title = pr.getString("as_title");
	as_chk = pr.getString("as_chk");
	as_type = pr.getString("as_type");
	as_infotype = pr.getString("as_infotype");
	k_xps = pr.getString("k_xps");
	k_yps = pr.getString("k_yps");
	sg_seqs = pr.getString("sg_seqs");
	sd_gsns = pr.getString("sd_gsns");
	mt_types = pr.getString("mt_types");
	as_sms_key = pr.getString("as_sms_key");
	as_sms_exkey = pr.getString("as_sms_exkey");
	as_sms_time = pr.getString("as_sms_time");
	as_interval = pr.getString("as_interval");
	ab_seqs = pr.getString("ab_seqs");
	as_last_num = pr.getString("as_last_num");
	if("1".equals(as_type) || "4".equals(as_type)){
		as_same_op = 0;
	}else{
		as_same_op = pr.getInt("as_same_op");
		
		if(as_same_op==1){					// 유사율을 선택한 경우
			as_same_percent = pr.getInt("as_same_value");
		}else if(as_same_op==2){			// 유사건수를 선택한 경우
			as_same_cnt = pr.getInt("as_same_value");
		}
	}
	
	as_later_send = pr.getString("as_later_send");
	as_monitor_use = 		pr.getString("as_monitor_use");
	as_monitor_max_m = 		pr.getString("as_monitor_max_m");
	as_monitor_repeat_m = 		pr.getString("as_monitor_repeat_m");
	as_monitor_inspector = 		pr.getString("as_monitor_inspector");
	
	asBean = new AlimiSettingBean();
	asBean.setAs_seq(as_seq);
	asBean.setAs_title(as_title);	
	asBean.setAs_chk(as_chk);
	asBean.setAs_type(as_type);
	asBean.setAs_infotype(as_infotype);
	asBean.setKg_xps(k_xps);
	asBean.setKg_yps(k_yps);	
	asBean.setSg_seqs(sg_seqs);
	asBean.setSd_gsns(sd_gsns);
	asBean.setMt_types(mt_types);
	asBean.setAs_sms_key(as_sms_key);
	asBean.setAs_sms_exkey(as_sms_exkey);
	asBean.setAs_sms_time(as_sms_time);
	asBean.setAs_interval(as_interval);
	asBean.setAs_sms_day("");
	asBean.setAs_sms_stime("");
	asBean.setAs_sms_etime("");
	asBean.setAs_same_op(as_same_op);
	asBean.setAs_same_percent(as_same_percent);
	asBean.setAs_same_cnt(as_same_cnt);
	
	asBean.setAs_later_send(as_later_send);
	asBean.setAs_monitor_inspector(as_monitor_inspector);
	asBean.setAs_monitor_max_m(as_monitor_max_m);
	asBean.setAs_monitor_repeat_m(as_monitor_repeat_m);
	asBean.setAs_monitor_use(as_monitor_use);
	asBean.setAs_last_num(as_last_num);
	
		
	String sendMail = pr.getString("sendMail");
	
	if("Y".equals(as_monitor_use)){ 
		as_monitor_send_cnt = asDao.getSendCount(as_seq,as_monitor_max_m,as_monitor_repeat_m);
	}
	asBean.setAs_monitor_send_cnt(as_monitor_send_cnt);
	
	if(mode.equals("INSERT"))
	{		
		result = asDao.insertAlimisSet(asBean , ab_seqs, sendMail);
		
	}else if(mode.equals("UPDATE")){
		
		result = asDao.updateReportSet(asBean , ab_seqs);
		//result = asDao.updateSendMail(as_seq , sendMail);
		
	}else if(mode.equals("DELETE")){
		result = asDao.deleteReportSet(as_seqs);
	}
%>
<script type="text/javascript">

	parent.contentsFrame.location.href = 'alimi_setting_list.jsp?nowpage=<%=nowpage%>';

</script>