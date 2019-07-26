package risk.admin.log;
import java.util.ArrayList;

import risk.util.DateUtil;
import risk.util.StringUtil;

public class LogBean {
	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();	
	private String l_seq = null;
	private String l_kinds = null;
	private String l_kindsName = null;
	private String l_type  = null;
	private String l_typeName = null;
	private String l_ip = null;
	private String key = null;
	private String l_date = null;
	private String m_seq = null;
	private String m_id = null;
	private String m_name = null;
	private ArrayList arrReceiver = null;
	
	public String getL_seq() {
		return l_seq;
	}
	public void setL_seq(String lSeq) {
		l_seq = lSeq;
	}
	public String getL_kinds() {
		return l_kinds;
	}
	public void setL_kinds(String lKinds) {
		l_kinds = lKinds;
	}
	public String getL_type() {
		return l_type;
	}
	public void setL_type(String lType) {
		l_type = lType;
	}
	public String getL_ip() {
		return l_ip;
	}
	public void setL_ip(String lIp) {
		l_ip = lIp;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getL_date() {
		return l_date;
	}
	public void setL_date(String lDate) {
		l_date = lDate;
	}
	//날짜 포멧
	public String getFormatL_date(String dateFormat) {
		return du.getDate(l_date, dateFormat);
	}
	public String getM_seq() {
		return m_seq;
	}
	public void setM_seq(String mSeq) {
		m_seq = mSeq;
	}
	public String getL_kindsName() {
		return l_kindsName;
	}
	public String getL_typeName() {
		return l_typeName;
	}
	public void setL_kindsName(String lKindsName) {
		l_kindsName = lKindsName;
	}
	public void setL_typeName(String lTypeName) {
		l_typeName = lTypeName;
	}
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String mId) {
		m_id = mId;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String mName) {
		m_name = mName;
	}
	public ArrayList getArrArrReceiver() {
		return arrReceiver;
	}
	public void setArrReceiver(ArrayList arrReceiver) {
		this.arrReceiver = arrReceiver;
	}
	
	
}
