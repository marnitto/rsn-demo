package risk.issue;
public class IssueDataCodeBean {
	
	private int		 id_seq;                  //이슈코드번호
	private int 	 ic_type;                 //이슈타입
	private int 	 ic_code;				  //이슈코드
	private int 	 ic_ptype;				  //부모이슈타입
	private int 	 ic_pcode;				  //부모이슈코드
	
	public int getId_seq() {
		return id_seq;
	}
	public void setId_seq(int id_seq) {
		this.id_seq = id_seq;
	}
	public int getIc_type() {
		return ic_type;
	}
	public void setIc_type(int ic_type) {
		this.ic_type = ic_type;
	}
	public int getIc_code() {
		return ic_code;
	}
	public void setIc_code(int ic_code) {
		this.ic_code = ic_code;
	}
	public int getIc_ptype() {
		return ic_ptype;
	}
	public void setIc_ptype(int ic_ptype) {
		this.ic_ptype = ic_ptype;
	}
	public int getIc_pcode() {
		return ic_pcode;
	}
	public void setIc_pcode(int ic_pcode) {
		this.ic_pcode = ic_pcode;
	}
	
}

