package risk.issue;

import java.util.ArrayList;
import risk.util.DateUtil;
import risk.util.StringUtil;

public class IssueBean {
	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();	
	private String i_seq;
	private String i_title;
	private String i_regdate;
	private String i_useyn;
	private String m_seq;
	private String m_name;
	private String i_count;
	private String md_date;
	private String id_url;
	
	public String getMd_date() {
		return md_date;
	}
	public void setMd_date(String mdDate) {
		md_date = mdDate;
	}
	public String getId_url() {
		return id_url;
	}
	public void setId_url(String idUrl) {
		id_url = idUrl;
	}
	public String getI_count() {
		return i_count;
	}
	public void setI_count(String i_count) {
		this.i_count = i_count;
	}
	private ArrayList arrITBean;
	
	public String getI_seq() {
		return i_seq;
	}
	public void setI_seq(String iSeq) {
		i_seq = iSeq;
	}
	public String getI_title() {
		return i_title;
	}
	public void setI_title(String iTitle) {
		i_title = iTitle;
	}
	public String getI_regdate() {
		return i_regdate;
	}
	public void setI_regdate(String iRegdate) {
		i_regdate = iRegdate;
	}
	//날짜 포멧
	public String getFormatI_date(String dateFormat) {
		return du.getDate(this.i_regdate, dateFormat);
	}
	public String getI_useyn() {
		return i_useyn;
	}
	public void setI_useyn(String iUseyn) {
		i_useyn = iUseyn;
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
	public ArrayList getArrITBean() {
		return arrITBean;
	}
	public void setArrITBean(ArrayList arrITBean) {
		this.arrITBean = arrITBean;
	}
}
