/**
========================================================
주 시 스 템 : RSN
서브 시스템 : 정보검색
프로그램 ID : metaInfo
프로그램 명 : 메타 데이터 class
프로그램개요 : 메타 Data Beans
작 성 자 : 윤석준
작 성 일 : 2006. 04. 11
========================================================
수정자/수정일:
수정사유/내역:
========================================================
*/
package risk.search;
import risk.util.DateUtil;
import risk.util.StringUtil;

public class PortalBean {
	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();


	
	private int no; 			 //시퀀스
	private String name;         //문서제목
	private String stime;        //시간
	private String url;          //문서링크
	private String title;        //문서제목
	private String html;         //주요내용
	private String exposeTime;		
	private String issue_check;
	private String s_seq;		
	private String t_board;
	
	
	public String getS_seq() {
		return s_seq;
	}
	public void setS_seq(String s_seq) {
		this.s_seq = s_seq;
	}
	public String getT_board() {
		return t_board;
	}
	public void setT_board(String t_board) {
		this.t_board = t_board;
	}
	public String getIssue_check() {
		return issue_check;
	}
	public void setIssue_check(String issue_check) {
		this.issue_check = issue_check;
	}
	public StringUtil getSu() {
		return su;
	}
	public void setSu(StringUtil su) {
		this.su = su;
	}
	public DateUtil getDu() {
		return du;
	}
	public void setDu(DateUtil du) {
		this.du = du;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}	
	//키워드 하이라이트
	public String getHighrightHtml(int length, String color, String k_value)
    {    	
		System.out.println("//////////////////////////////////////////////////");
		System.out.println( this.html );
    	return su.cutKey(su.toHtmlString(this.html), k_value, length, color);
    }
	//날짜 포멧
	public String getFormatMd_date(String dateFormat) {
		return du.getDate(this.stime, dateFormat);
	}
	public String getExposeTime() {
		return exposeTime;
	}
	public void setExposeTime(String exposeTime) {
		this.exposeTime = exposeTime;
	}  
	  
}