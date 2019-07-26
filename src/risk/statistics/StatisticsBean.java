package risk.statistics;

public class StatisticsBean {
	
	//사이트 관련
	private String sg_seq = "";	
	private String sg_name = "";
	private String s_name ="";
	private String s_seq = "";
	private String s_url = "";
	private int site_ct = 0;
	private int favor_ct = 0;
	private int total_ct = 0;
	
	private int scnt = 0;	//site cnt
	private int idcnt = 0;	//issue reg cnt
	private int mcnt = 0;	//meta join idx cnt
	private double avg = 0.0;
	
	private String s_cnt = "";
	
	
	//키워드 관련
	private String date = "";
	private String k_xp = "";
	private String k_yp = "";
	private String k_zp = "";
	private String k_seq = "";
	private String k_value = "";
	private String k_cnt = "";
	
	private int p_cnt;
	private int n_cnt;
	private int e_cnt;
	
	private int cnt1;
	private int cnt2;
	private int cnt3;
	
	public int getCnt1() {
		return cnt1;
	}
	public void setCnt1(int cnt1) {
		this.cnt1 = cnt1;
	}
	public int getCnt2() {
		return cnt2;
	}
	public void setCnt2(int cnt2) {
		this.cnt2 = cnt2;
	}
	public int getCnt3() {
		return cnt3;
	}
	public void setCnt3(int cnt3) {
		this.cnt3 = cnt3;
	}
	public int getP_cnt() {
		return p_cnt;
	}
	public void setP_cnt(int pCnt) {
		p_cnt = pCnt;
	}
	public int getN_cnt() {
		return n_cnt;
	}
	public void setN_cnt(int nCnt) {
		n_cnt = nCnt;
	}
	public int getE_cnt() {
		return e_cnt;
	}
	public void setE_cnt(int eCnt) {
		e_cnt = eCnt;
	}
	public int getScnt() {
		return scnt;
	}
	public void setScnt(int scnt) {
		this.scnt = scnt;
	}
	public int getIdcnt() {
		return idcnt;
	}
	public void setIdcnt(int idcnt) {
		this.idcnt = idcnt;
	}
	public int getMcnt() {
		return mcnt;
	}
	public void setMcnt(int mcnt) {
		this.mcnt = mcnt;
	}
	public String getSg_seq() {
		return sg_seq;
	}
	public void setSg_seq(String sgSeq) {
		sg_seq = sgSeq;
	}
	public String getSg_name() {
		return sg_name;
	}
	public void setSg_name(String sgName) {
		sg_name = sgName;
	}	
	public String getS_seq() {
		return s_seq;
	}
	public void setS_seq(String sSeq) {
		s_seq = sSeq;
	}
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String sName) {
		s_name = sName;
	}
	public String getS_url() {
		return s_url;
	}
	public void setS_url(String sUrl) {
		s_url = sUrl;
	}
	public int getSite_ct() {
		return site_ct;
	}
	public void setSite_ct(int siteCt) {
		site_ct = siteCt;
	}
	public int getFavor_ct() {
		return favor_ct;
	}
	public void setFavor_ct(int favorCt) {
		favor_ct = favorCt;
	}
	public int getTotal_ct() {
		return total_ct;
	}
	public void setTotal_ct(int totalCt) {
		total_ct = totalCt;
	}
	public double getAvg() {
		return avg;
	}
	public void setAvg(double avg) {
		this.avg = avg;
	}
	public String getK_seq() {
		return k_seq;
	}
	public void setK_seq(String kSeq) {
		k_seq = kSeq;
	}
	public String getK_value() {
		return k_value;
	}
	public void setK_value(String kValue) {
		k_value = kValue;
	}
	public String getK_cnt() {
		return k_cnt;
	}
	public void setK_cnt(String kCnt) {
		k_cnt = kCnt;
	}	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getK_xp() {
		return k_xp;
	}
	public void setK_xp(String kXp) {
		k_xp = kXp;
	}
	public String getK_yp() {
		return k_yp;
	}
	public void setK_yp(String kYp) {
		k_yp = kYp;
	}
	public String getK_zp() {
		return k_zp;
	}
	public void setK_zp(String kZp) {
		k_zp = kZp;
	}		
	public String getS_cnt() {
		return s_cnt;
	}
	public void setS_cnt(String s_cnt) {
		this.s_cnt = s_cnt;
	}
	
	
}
