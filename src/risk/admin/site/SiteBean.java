package risk.admin.site;

public class SiteBean {
	private int gsn;
	private String name;
	private int l_seq;
	
	private String sg_seq;
	private String sg_name;
	private String sg_regdate;
	
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
	public String getSg_regdate() {
		return sg_regdate;
	}
	public void setSg_regdate(String sgRegdate) {
		sg_regdate = sgRegdate;
	}
	public int get_gsn() {
        return gsn;
    }
    public int getL_seq() {
		return l_seq;
	}
	public void setL_seq(int l_seq) {
		this.l_seq = l_seq;
	}
	public void set_gsn(int gsn) {
        this.gsn = gsn;
    }

	public String get_name() {
        return name;
    }
    public void set_name(String name) {
        this.name = name;
    }
}