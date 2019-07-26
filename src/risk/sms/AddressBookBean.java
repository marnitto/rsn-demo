package risk.sms;

import java.lang.String;

import risk.util.DateUtil;
import risk.util.StringUtil;

//회원정보를 가져오는 자바빈


public class AddressBookBean {
	
	public AddressBookBean(){
			
	}
	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	private int Mab_seq =0;//일련번호
	private String Mab_name = "";//사용자이이름
	private String Mag_seq = "";
	private String Mab_dept = "";//부서
	private String Mab_pos = "";//직급
	private String Mab_mobile = "";//핸드폰번호
	private String Mab_mail ="";//메일주소	
	private String Mk_xp = "";//SMS 수신할 키워드 그룹
	private String Msg_seq = "";//SMS 수신할 사이트그룹
	private String Mmt_type = "";//SMS 수신할 접수매체유형
	private String Mab_issue_dept = "";//Mail 수신할 부서
	private String Mab_sms_receivechk = "";//SMS 수신여부
	private String Mab_issue_receivechk ="";//Mail 수신여부
	private String Mab_report_day_chk ="";//일별 통계보고서 발송여부
	private String Mab_report_week_chk="";//주별 통계보거서 발송여부
	private String Mab_sms_limit="";//sms 발송 제한 설정
	private String Mab_send_date = ""; // 발송일
	private String Mab_app_chk = "";      // 모바일 앱 사용여부 (0:미사용, 1:사용)
	private String Mab_sms_chk = "";      // SMS 사용여부 (0:미사용, 1:사용)
	
	public String getMab_sms_chk() {
		return Mab_sms_chk;
	}
	public void setMab_sms_chk(String mab_sms_chk) {
		Mab_sms_chk = mab_sms_chk;
	}

	private String l_count = ""; 
	public String getL_count() {
		return l_count;
	}
	public void setL_count(String l_count) {
		this.l_count = l_count;
	}
	
	public String getMab_sms_limit() {	return Mab_sms_limit;	}
	public void setMab_sms_limit(String mab_sms_limit) {	Mab_sms_limit = mab_sms_limit;	}
	//Bean 속성값
	public String getMk_xp() {	return Mk_xp;	}
	public void setMk_xp(String Pk_xp) {	Mk_xp = Pk_xp;	}	
	public String getMab_mobile() {	return Mab_mobile;	}
	public void setMab_mobile(String Pab_mobile) {	Mab_mobile = Pab_mobile;	}	
	public String getMmt_type() {	return Mmt_type;	}
	public void setMmt_type(String Pmt_type) {	Mmt_type = Pmt_type;	}	
	public String getMab_name() {	return Mab_name;	}
	public void setMab_name(String Pab_name) {	Mab_name = Pab_name;	}			
	public String getMsg_seq() {	return Msg_seq;	}
	public void setMsg_seq(String Psg_seq) {	Msg_seq = Psg_seq;	}
	public int getMab_seq() {	return Mab_seq;	}
	public void setMab_seq(int Pab_seq) {	Mab_seq = Pab_seq;	}
	public String getMab_dept() {	return Mab_dept;	}
	public void setMab_dept(String Pab_dept) {	Mab_dept = Pab_dept;	}
	public String getMab_issue_dept() {	return Mab_issue_dept;	}
	public void setMab_issue_dept(String Pab_issue_dept) {	Mab_issue_dept = Pab_issue_dept;	}
	public String getMab_issue_receivechk() {	return Mab_issue_receivechk;	}
	public void setMab_issue_receivechk(String Pab_issue_receivechk) {	Mab_issue_receivechk = Pab_issue_receivechk;	}
	public String getMab_mail() {	return Mab_mail;	}
	public void setMab_mail(String Pab_mail) {	Mab_mail = Pab_mail;	}
	public String getMab_pos() {	return Mab_pos;	}
	public void setMab_pos(String Pab_pos) {	Mab_pos = Pab_pos;	}
	public String getMab_report_day_chk() {	return Mab_report_day_chk;	}
	public void setMab_report_day_chk(String Pab_report_day_chk) {	Mab_report_day_chk = Pab_report_day_chk;	}
	public String getMab_report_week_chk() {	return Mab_report_week_chk;	}
	public void setMab_report_week_chk(String Pab_report_week_chk) {	Mab_report_week_chk = Pab_report_week_chk;	}
	public String getMab_sms_receivechk() {	return Mab_sms_receivechk;	}
	public void setMab_sms_receivechk(String Pab_sms_receivechk) {	Mab_sms_receivechk = Pab_sms_receivechk;	}
	public String getMab_app_chk() {	return Mab_app_chk;	}
	public void setMab_app_chk(String Pab_app_chk) {	Mab_app_chk = Pab_app_chk;	}
	
	
	public String getMag_seq() {
		return Mag_seq;
	}
	public void setMag_seq(String magSeq) {
		Mag_seq = magSeq;
	}	
	public String getMab_send_date() {
		return Mab_send_date;
	}
	//날짜 포멧
	public String getFormatMab_send_date(String dateFormat) {
		return du.getDate(Mab_send_date, dateFormat);
	}
	public void setMab_send_date(String mabSendDate) {
		Mab_send_date = mabSendDate;
	}
	
	public AddressBookBean(int Pab_seq,
						   String Pag_seq,
			               String Pab_name,
			               String Pab_dept,
			               String Pab_pos,
			               String Pab_mobile,
			               String Pab_mail,
			               String Pk_xp,
			               String Psg_seq,			               
			               String Pmt_type,
			               String Pab_issue_dept,
			               String Pab_sms_receivechk,
			               String Pab_issue_receivechk,
			               String Pab_report_day_chk,
			               String Pab_report_week_chk,
			               String Pab_sms_limit
			               )		
	{
		this.Mab_seq = Pab_seq;
		this.Mag_seq = Pag_seq;
		this.Mab_name = Pab_name;
		this.Mab_dept = Pab_dept;
		this.Mab_pos = Pab_pos;
		this.Mab_mail = Pab_mail;
		this.Mab_mobile = Pab_mobile;
		this.Mk_xp = Pk_xp;
		this.Msg_seq = Psg_seq;
		this.Mmt_type = Pmt_type;
		this.Mab_issue_dept = Pab_issue_dept;
		this.Mab_sms_receivechk = Pab_sms_receivechk;
		this.Mab_issue_receivechk = Pab_issue_receivechk;
		this.Mab_report_day_chk = Pab_report_day_chk;
		this.Mab_report_week_chk = Pab_report_week_chk;
		this.Mab_sms_limit = Pab_sms_limit;
	}
	
	public AddressBookBean(
				            String Pab_name,
				            String Pab_dept,
				            String Pab_mail,
				            String Pab_report_day_chk
                       		)		
	{
		this.Mab_name = Pab_name;
		this.Mab_dept = Pab_dept;
		this.Mab_mail = Pab_mail;
		this.Mab_report_day_chk = Pab_report_day_chk;
	}	
}