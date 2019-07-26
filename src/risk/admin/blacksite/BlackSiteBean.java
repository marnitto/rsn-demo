package risk.admin.blacksite;

public class BlackSiteBean {

	private String bsSeq = null;
	private String bsValue = null;
	private String bsPosition = null;
	private String bsDate = null;	
	private String bsUpdate = null;
	private String bsFwriter = null;
	private String bsLwriter = null;
	
	private String k_seqs = "";
	private String k_seq = "";
	private String k_value1 = "";
	private String k_value2 = "";
	private String k_value3 = "";
	
	private String s_seqs = "";
	private String s_seq = "";
	private String s_value1 = "";
	private String s_url = "";
	
	
	public String getK_seqs() {
		return k_seqs;
	}
	public void setK_seqs(String k_seqs) {
		this.k_seqs = k_seqs;
	}
	public String getK_seq() {
		return k_seq;
	}
	public void setK_seq(String k_seq) {
		this.k_seq = k_seq;
	}
	public String getK_value1() {
		return k_value1;
	}
	public void setK_value1(String k_value1) {
		this.k_value1 = k_value1;
	}
	public String getK_value2() {
		return k_value2;
	}
	public void setK_value2(String k_value2) {
		this.k_value2 = k_value2;
	}
	public String getK_value3() {
		return k_value3;
	}
	public void setK_value3(String k_value3) {
		this.k_value3 = k_value3;
	}
	public String getBsSeq() {
		return bsSeq;
	}
	public void setBsSeq(String bsSeq) {
		this.bsSeq = bsSeq;
	}
	public String getBsValue() {
		return bsValue;
	}
	public void setBsValue(String bsValue) {
		this.bsValue = bsValue;
	}
	public String getBsPosition() {
		return bsPosition;
	}
	public void setBsPosition(String bsPosition) {
		this.bsPosition = bsPosition;
	}
	public String getBsDate() {
		return bsDate;
	}
	public void setBsDate(String bsDate) {
		this.bsDate = bsDate;
	}
	public String getBsUpdate() {
		return bsUpdate;
	}
	public void setBsUpdate(String bsUpdate) {
		this.bsUpdate = bsUpdate;
	}
	public String getBsFwriter() {
		return bsFwriter;
	}
	public void setBsFwriter(String bsFwriter) {
		this.bsFwriter = bsFwriter;
	}
	public String getBsLwriter() {
		return bsLwriter;
	}
	public void setBsLwriter(String bsLwriter) {
		this.bsLwriter = bsLwriter;
	}
	public String getS_seqs() {
		return s_seqs;
	}
	public void setS_seqs(String s_seqs) {
		this.s_seqs = s_seqs;
	}
	public String getS_seq() {
		return s_seq;
	}
	public void setS_seq(String s_seq) {
		this.s_seq = s_seq;
	}
	public String getS_value1() {
		return s_value1;
	}
	public void setS_value1(String s_value1) {
		this.s_value1 = s_value1;
	}
	public String getS_url() {
		return s_url;
	}
	public void setS_url(String s_url) {
		this.s_url = s_url;
	}
	
	
	public enum Type{LEFT,RIGHT};
	public enum Type2{ADD,DEL};
	public enum Mode{KEYWORD,SITE};
	
}
