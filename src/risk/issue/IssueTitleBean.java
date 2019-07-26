package risk.issue;

import risk.util.DateUtil;
import risk.util.StringUtil;

public class IssueTitleBean {
	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();	
	private String it_seq;  
	private String it_title;
	private String i_seq;
	private String it_regdate;
	private String it_useyn;
	private String m_seq;
	private String m_name;
	
	public String getIt_seq() {
		return it_seq;
	}
	public void setIt_seq(String itSeq) {
		it_seq = itSeq;
	}	
	public String getIt_title() {
		return it_title;
	}
	public void setIt_title(String itTitle) {
		it_title = itTitle;
	}
	public String getI_seq() {
		return i_seq;
	}
	public void setI_seq(String iSeq) {
		i_seq = iSeq;
	}
	public String getIt_regdate() {
		return it_regdate;
	}
	//날짜 포멧
	public void setIt_regdate(String itRegdate) {
		it_regdate = itRegdate;
	}
	public String getFormatIt_date(String dateFormat) {
		return du.getDate(this.it_regdate, dateFormat);
	}
	public String getIt_useyn() {
		return it_useyn;
	}
	public void setIt_useyn(String itUseyn) {
		it_useyn = itUseyn;
	}
	public String getM_seq() {
		return m_seq;
	}
	public void setM_seq(String mSeq) {
		m_seq = mSeq;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String mName) {
		m_name = mName;
	}		
	
}
