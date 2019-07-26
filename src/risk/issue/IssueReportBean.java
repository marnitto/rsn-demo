package risk.issue;

import risk.util.DateUtil;
import risk.util.StringUtil;

public class IssueReportBean {
	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();		
	private String ir_seq;
	private String ir_type;
	private String ir_title;
	private String ir_html;
	private String ir_memo;
	private String ir_regdate;
	private String ir_maildate;
	private String ir_mailyn;
	private String ir_mailcnt;
	
	public String getIr_seq() {
		return ir_seq;
	}
	public void setIr_seq(String irSeq) {
		ir_seq = irSeq;
	}
	public String getIr_type() {
		return ir_type;
	}
	public void setIr_type(String irType) {
		ir_type = irType;
	}
	public String getIr_title() {
		return ir_title;
	}
	public void setIr_title(String irTitle) {
		ir_title = irTitle;
	}
	public String getIr_html() {
		return ir_html;
	}
	public void setIr_html(String irHtml) {
		ir_html = irHtml;
	}
	public String getIr_memo() {
		return ir_memo;
	}
	public void setIr_memo(String irMemo) {
		ir_memo = irMemo;
	}
	public String getIr_regdate() {
		return ir_regdate;
	}
	//날짜 포멧
	public String getFormatIr_regdate(String dateFormat) {
		return du.getDate(this.ir_regdate, dateFormat);
	}
	public void setIr_regdate(String irRegdate) {
		ir_regdate = irRegdate;
	}	
	public String getIr_maildate() {
		return ir_maildate;
	}
	//날짜 포멧
	public String getFormatIr_maildate(String dateFormat) {
		return du.getDate(this.ir_maildate, dateFormat);
	}
	public void setIr_maildate(String irMaildate) {
		ir_maildate = irMaildate;
	}	
	public String getIr_mailyn() {
		return ir_mailyn;
	}
	public void setIr_mailyn(String irMailyn) {
		ir_mailyn = irMailyn;
	}
	public String getIr_mailcnt() {
		return ir_mailcnt;
	}
	public void setIr_mailcnt(String irMailcnt) {
		ir_mailcnt = irMailcnt;
	}	
	
}
