package risk.search;

import risk.util.StringUtil;

public class SearchPortalBean {
	
	private StringUtil su = new StringUtil();
	
	private String P_SEQ = "";
	private String SD_GSN = "";
	private String SD_NAME = "";
	private String P_STIME = "";
	private String P_URL = "";
	private String P_TITLE = "";
	private String P_ETIME = "";
	private String B_TIME = "";
	private String ISSUE_CHECK = "";
	private String MENU = "";
	
	
	public String getMENU() {
		return MENU;
	}
	public void setMENU(String menu) {
		MENU = menu;
	}
	public String getISSUE_CHECK() {
		return ISSUE_CHECK;
	}
	public void setISSUE_CHECK(String issue_check) {
		ISSUE_CHECK = issue_check;
	}
	public String getB_TIME() {
		return B_TIME;
	}
	public void setB_TIME(String b_time) {
		B_TIME = b_time;
	}
	public String getP_SEQ() {
		return P_SEQ;
	}
	public void setP_SEQ(String p_seq) {
		P_SEQ = p_seq;
	}
	public String getSD_GSN() {
		return SD_GSN;
	}
	public void setSD_GSN(String sd_gsn) {
		SD_GSN = sd_gsn;
	}
	public String getSD_NAME() {
		return SD_NAME;
	}
	public void setSD_NAME(String sd_name) {
		SD_NAME = sd_name;
	}
	public String getP_STIME() {
		return P_STIME;
	}
	public void setP_STIME(String p_stime) {
		P_STIME = p_stime;
	}
	public String getP_URL() {
		return P_URL;
	}
	public void setP_URL(String p_url) {
		P_URL = p_url;
	}
	public String getP_TITLE() {
		return P_TITLE;
	}
	public void setP_TITLE(String p_title) {
		P_TITLE = p_title;
	}
	public String getP_ETIME() {
		return P_ETIME;
	}
	public void setP_ETIME(String p_etime) {
		P_ETIME = p_etime;
	}
	
	public SearchPortalBean(){}
	
	public SearchPortalBean(String p_seq, String sd_gsn, String sd_name, String p_stime, String p_url, String p_title, String p_etime, String b_time, String issue_check, String menu){
		this.P_SEQ = p_seq;
		this.SD_GSN = sd_gsn;
		this.SD_NAME = sd_name;
		this.P_STIME = p_stime;
		this.P_URL = p_url;
		this.P_TITLE = p_title;
		this.P_ETIME = p_etime;
		this.B_TIME = b_time;
		this.ISSUE_CHECK = issue_check;
		this.MENU = menu;
	}
	
	public SearchPortalBean(String sd_gsn, String sd_name){
		this.SD_GSN = sd_gsn;
		this.SD_NAME = sd_name;
	}
	
	
}
