/**
========================================================
주 시 스 템 : RSN
서브 시스템 : 사용자 환경정보
프로그램 ID : userEnvInfo
프로그램 명 : 사용자 환경정보 Data Bean
프로그램개요 : 사용자 환경정보
작 성 자 : 윤석준
작 성 일 : 2006.04.01
========================================================
수정자/수정일: 임승철  2010.10.26
수정사유/내역:
========================================================
*/
package risk.search;
import java.io.Serializable;

public class userEnvInfo implements Serializable {
	
    private String st_seq = null;
    private String m_seq = null;
    private String st_list_cnt = null;
    private String st_interval_day = null;
    private String st_reload_time = null;
    private String st_menu = null;
    private String md_type = null;
    private String sg_seq = "0";
    private String sg_seq_al = "0";
    private String s_seq = "0";
    private String k_xp = "0";
    private String k_yp = "0";
    private String k_zp = "0";
    private String order = null;
    private String orderAlign = null;
    private String mg_seq = null;
    private String mg_xp = null;
    private String mg_site = null;
    private String mg_menu = null;
    private String searchMode = null;
    private String keyword = null;
    private String dateFrom = null;
    private String dateTo = null;
    private String delName = null; 
    private String lnaguage = "";
    //검색 시간을 8시~23시로 초기화
    private String stime = "8";
	private String etime = "23";
    
    
    
	private String siteValue = ""; // 포탈 초기면 관련 검색 대상
    private String pTodate = "";  // 포탈 초기면 관련 날짜
    private String pFromdate = ""; // 포탈 초기면 관련 날짜
    
	public String getSt_seq() {
		return st_seq;
	}
	public void setSt_seq(String stSeq) {
		st_seq = stSeq;
	}
	public String getM_seq() {
		return m_seq;
	}
	public void setM_seq(String mSeq) {
		m_seq = mSeq;
	}
	public String getSt_list_cnt() {
		return st_list_cnt;
	}
	public void setSt_list_cnt(String stListCnt) {
		st_list_cnt = stListCnt;
	}
	public String getSt_interval_day() {
		return st_interval_day;
	}
	public void setSt_interval_day(String stIntervalDay) {
		st_interval_day = stIntervalDay;
	}
	public String getSt_reload_time() {
		return st_reload_time;
	}
	public void setSt_reload_time(String stReloadTime) {
		st_reload_time = stReloadTime;
	}
	public String getSt_menu() {
		return st_menu;
	}
	public void setSt_menu(String stMenu) {
		st_menu = stMenu;
	}	
	public String getMd_type() {
		return md_type;
	}
	public void setMd_type(String mdType) {
		md_type = mdType;
	}
	public String getSg_seq() {
		return sg_seq;
	}
	public void setSg_seq(String sgSeq) {
		sg_seq = sgSeq;
	}
	
	public String getSg_seq_al() {
		return sg_seq_al;
	}
	public void setSg_seq_al(String sgSeqAl) {
		sg_seq_al = sgSeqAl;
	}
	
	public String getS_seq() {
		return s_seq;
	}
	public void setS_seq(String sSeq) {
		s_seq = sSeq;
	}
	public String getK_xp() {
		return k_xp;
	}
	public void setK_xp(String kXp) {
		k_xp = kXp;
	}
	public String getMg_seq() {
		return mg_seq;
	}
	public void setMg_seq(String mgSeq) {
		mg_seq = mgSeq;
	}
	public String getMg_xp() {
		return mg_xp;
	}
	public void setMg_xp(String mgXp) {
		mg_xp = mgXp;
	}
	public String getMg_site() {
		return mg_site;
	}
	public void setMg_site(String mgSite) {
		mg_site = mgSite;
	}
	public String getMg_menu() {
		return mg_menu;
	}
	public void setMg_menu(String mgMenu) {
		mg_menu = mgMenu;
	}
	public String getSearchMode() {
		return searchMode;
	}
	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
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
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getOrderAlign() {
		return orderAlign;
	}
	public void setOrderAlign(String orderAlign) {
		this.orderAlign = orderAlign;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public String getDelName() {
		return delName;
	}
	public void setDelName(String delName) {
		this.delName = delName;
	}
	public String getLnaguage() {
		return lnaguage;
	}
	public void setLnaguage(String lnaguage) {
		this.lnaguage = lnaguage;
	}
	
	
	public String getSiteValue() {
		return siteValue;
	}
	public void setSiteValue(String siteValue) {
		this.siteValue = siteValue;
	}
	public String getPTodate() {
		return pTodate;
	}
	public void setPTodate(String todate) {
		pTodate = todate;
	}
	public String getPFromdate() {
		return pFromdate;
	}
	public void setPFromdate(String fromdate) {
		pFromdate = fromdate;
	}
	
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	
	
	
	
}