package risk.mobile;

public class AlimiReceiverBean {
	
	public String getAs_seq() {
		return as_seq;
	}
	public void setAs_seq(String as_seq) {
		this.as_seq = as_seq;
	}
	public String getAb_seq() {
		return ab_seq;
	}
	public void setAb_seq(String ab_seq) {
		this.ab_seq = ab_seq;
	}
	public String getAb_name() {
		return ab_name;
	}
	public void setAb_name(String ab_name) {
		this.ab_name = ab_name;
	}
	public String getAb_dept() {
		return ab_dept;
	}
	public void setAb_dept(String ab_dept) {
		this.ab_dept = ab_dept;
	}
	public String getAb_pos() {
		return ab_pos;
	}
	public void setAb_pos(String ab_pos) {
		this.ab_pos = ab_pos;
	}
	public String getAb_mobile() {
		return ab_mobile;
	}
	public void setAb_mobile(String ab_mobile) {
		this.ab_mobile = ab_mobile;
	}
	public String getAb_mail() {
		return ab_mail;
	}
	public void setAb_mail(String ab_mail) {
		this.ab_mail = ab_mail;
	}
	public String getK_xp() {
		return k_xp;
	}
	public void setK_xp(String k_xp) {
		this.k_xp = k_xp;
	}
	public String getSg_seq() {
		return sg_seq;
	}
	public void setSg_seq(String sg_seq) {
		this.sg_seq = sg_seq;
	}
	public String getMt_type() {
		return mt_type;
	}
	public void setMt_type(String mt_type) {
		this.mt_type = mt_type;
	}
	public String getAb_issue_dept() {
		return ab_issue_dept;
	}
	public void setAb_issue_dept(String ab_issue_dept) {
		this.ab_issue_dept = ab_issue_dept;
	}
	public String getAb_sms_receivechk() {
		return ab_sms_receivechk;
	}
	public void setAb_sms_receivechk(String ab_sms_receivechk) {
		this.ab_sms_receivechk = ab_sms_receivechk;
	}
	public String getAb_issue_receivechk() {
		return ab_issue_receivechk;
	}
	public void setAb_issue_receivechk(String ab_issue_receivechk) {
		this.ab_issue_receivechk = ab_issue_receivechk;
	}
	public String getAb_report_day_chk() {
		return ab_report_day_chk;
	}
	public void setAb_report_day_chk(String ab_report_day_chk) {
		this.ab_report_day_chk = ab_report_day_chk;
	}
	public String getAb_report_week_chk() {
		return ab_report_week_chk;
	}
	public void setAb_report_week_chk(String ab_report_week_chk) {
		this.ab_report_week_chk = ab_report_week_chk;
	}
	public String getAb_sms_limit() {
		return ab_sms_limit;
	}
	public void setAb_sms_limit(String ab_sms_limit) {
		this.ab_sms_limit = ab_sms_limit;
	}
	public String getAb_report_month_chk() {
		return ab_report_month_chk;
	}
	public void setAb_report_month_chk(String ab_report_month_chk) {
		this.ab_report_month_chk = ab_report_month_chk;
	}
	 private String as_seq = null;
	 private String ab_seq = null;
	 private String ab_name = null;//사용자이이름
	 private String ab_dept = null;//부서
	 private String ab_pos = null;//직급
	 private String ab_mobile = null;//핸드폰번호
	 private String ab_mail =null;//메일주소 
	 private String k_xp = null;//SMS 수신할 키워드 그룹
	 private String sg_seq = null;//SMS 수신할 사이트그룹
	 private String mt_type = null;//SMS 수신할 접수매체유형
	 private String ab_issue_dept = null;//Mail 수신할 부서
	 private String ab_sms_receivechk = null;//SMS 수신여부
	 private String ab_issue_receivechk =null;//Mail 수신여부
	 private String ab_report_day_chk =null;//일별 통계보고서 발송여부
	 private String ab_report_week_chk=null;//주별 통계보거서 발송여부
	 private String ab_sms_limit=null;//sms 발송 제한 설정
	 private String ab_report_month_chk = null;//월별 통계보고서 발송여부
}
