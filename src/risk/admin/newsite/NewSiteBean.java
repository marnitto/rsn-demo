package risk.admin.newsite;
/**
 * @param int nsNo
 * @param String nsName
 * @param String nsDate
 * @param String nsURL
 * @param String nsTitle
 */
public class NewSiteBean {
	private int nsNo;
	private String nsName;
	private String nsDate;
	private String nsURL;
	private String nsTitle;
	private String nsSendChk;
	private String nsSendDate;
	private String nsSendTime;
	
	
	
	public String getNsSendDate() {		return nsSendDate;	}
	public String getNsSendTime() {		return nsSendTime;	}
	public String getNsSendChk() {		return nsSendChk;	}
	public String getNsDate() {		return nsDate;	}
	public String getNsName() {		return nsName;	}
	public int getNsNo() {			return nsNo;	}
	public String getNsTitle() {	return nsTitle;	}
	public String getNsURL() {		return nsURL;	}
	
	public void setNsSendDate(String nsSendDate) {		this.nsSendDate = nsSendDate;	}
	public void setNsSendTime(String nsSendTime) {		this.nsSendTime = nsSendTime;	}
	public void setNsSendChk(String nsSendChk) {		this.nsSendChk = nsSendChk;	}
	public void setNsDate(String nsDate) {		this.nsDate = nsDate;	}
	public void setNsName(String nsName) {		this.nsName = nsName;	}
	public void setNsNo(int nsNo) {				this.nsNo = nsNo;	}
	public void setNsTitle(String nsTitle) {	this.nsTitle = nsTitle;	}
	public void setNsURL(String nsURL) {		this.nsURL = nsURL;	}
	
	
}