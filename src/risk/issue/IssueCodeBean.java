package risk.issue;
import java.util.ArrayList;
public class IssueCodeBean {
	
	private int		 ic_seq;                  //이슈코드번호
	private String 	 ic_name;				  //이슈코드명
	private int 	 ic_type;                 //이슈타입
	private int 	 ic_code;				  //이슈코드
	private int 	 ic_ptype;				  //부모이슈타입
	private int 	 ic_pcode;				  //부모이슈코드
	private String 	 ic_regdate;			  //이슈코드등록일
	private String 	 m_seq;					  //등록자번호
	private String   ic_description;		  //상세
	
	public int getIc_seq() {
		return ic_seq;
	}
	public void setIc_seq(int icSeq) {
		ic_seq = icSeq;
	}	
	public String getIc_name() {
		return ic_name;
	}
	public void setIc_name(String icName) {
		ic_name = icName;
	}
	public int getIc_type() {
		return ic_type;
	}
	public void setIc_type(int icType) {
		ic_type = icType;
	}
	public int getIc_code() {
		return ic_code;
	}
	public void setIc_code(int icCode) {
		ic_code = icCode;
	}
	public int getIc_ptype() {
		return ic_ptype;
	}
	public void setIc_ptype(int icPtype) {
		ic_ptype = icPtype;
	}
	public int getIc_pcode() {
		return ic_pcode;
	}
	public void setIc_pcode(int icPcode) {
		ic_pcode = icPcode;
	}
	public String getIc_regdate() {
		return ic_regdate;
	}
	public void setIc_regdate(String icRegdate) {
		ic_regdate = icRegdate;
	}
	public String getM_seq() {
		return m_seq;
	}
	public void setM_seq(String mSeq) {
		m_seq = mSeq;
	}
	public String getIc_description() {
		return ic_description;
	}
	public void setIc_description(String icDescription) {
		ic_description = icDescription;
	}
	
	
	
}

