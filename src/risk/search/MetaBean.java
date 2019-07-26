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

public class MetaBean {
	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	private String md_seq;                //일련번호
	private String sg_seq;                //SITE_GROUP(SG_SEQ)
	private String s_seq;                 //SITE_DATA(S_SEQ)
	private String sb_seq;                 //SITE_DATA(SB_SEQ)
	private String md_site_name ;         //사이트이름  
	private String md_site_menu;          //사이트메뉴의 이름
	private String md_type; 	             //기사유형 (1:기사,2:게시,3:공지)
	private String md_title;			     //기사 제목
	private String md_url;				 //기사 URL
	private String md_date;				 //기사 수집일
	private String md_content;            //기사 내용
	private String md_pseq;				 //기사 유사처리 부모값
	private String k_value; 				 //인덱싱된 키워드
	private String md_same_count;		 //기사 유사 처리수
	private String md_reply_count;		 //댓글 수
	private String issue_check;			 //이슈등록여부
	private String i_deldate; 			 //삭제일자
	private String m_seq;				 //삭제자번호
	private String m_name;				 //삭제자이름        
	private String md_img;               //이미지 여부
	private String l_alpha;               // 언어코드
	private String md_comfirm;               //이미 본 리스트인지 확인
	private String wtime;                 //작성일자
	private String d_seq;                 //문서번호
	
	private String sg_name;               //사이트그룹 명
	
	private String t_followers;               //팔로워수~~
  
	private String user_id;			//아이디
	private String user_nick;		//닉네임
	
	private String blog_visit_count;		//누적방문자수
	private String cafe_name;				//카페명
	private String cafe_member_count;		//카페회원수
	
	private String ex_keyword_name;			//제외키워드
	
	
	
	public String getSb_seq() {
		return sb_seq;
	}
	public void setSb_seq(String sb_seq) {
		this.sb_seq = sb_seq;
	}
	public String getD_seq() {
		return d_seq;
	}
	public void setD_seq(String d_seq) {
		this.d_seq = d_seq;
	}
	public String getEx_keyword_name() {
		return ex_keyword_name;
	}
	public void setEx_keyword_name(String ex_keyword_name) {
		this.ex_keyword_name = ex_keyword_name;
	}
	public String getBlog_visit_count() {
		return blog_visit_count;
	}
	public void setBlog_visit_count(String blog_visit_count) {
		this.blog_visit_count = blog_visit_count;
	}
	public String getCafe_name() {
		return cafe_name;
	}
	public void setCafe_name(String cafe_name) {
		this.cafe_name = cafe_name;
	}
	public String getCafe_member_count() {
		return cafe_member_count;
	}
	public void setCafe_member_count(String cafe_member_count) {
		this.cafe_member_count = cafe_member_count;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_nick() {
		return user_nick;
	}
	public void setUser_nick(String user_nick) {
		this.user_nick = user_nick;
	}
	public String getWtime() {
		return wtime;
	}
	public void setWtime(String wtime) {
		this.wtime = wtime;
	}
	public String getT_followers() {
		return t_followers;
	}
	public void setT_followers(String t_followers) {
		this.t_followers = t_followers;
	}
	public String getSg_name() {
		return sg_name;
	}
	public void setSg_name(String sg_name) {
		this.sg_name = sg_name;
	}
	public String getMd_seq() {
		return md_seq;
	}
	public void setMd_seq(String mdSeq) {
		md_seq = mdSeq;
	}
	public String getSg_seq() {
		return sg_seq;
	}
	public void setSg_seq(String sgSeq) {
		sg_seq = sgSeq;
	}
	public String getS_seq() {
		return s_seq;
	}
	public void setS_seq(String sSeq) {
		s_seq = sSeq;
	}
	public String getMd_site_name() {
		return md_site_name;
	}
	public void setMd_site_name(String mdSiteName) {
		md_site_name = mdSiteName;
	}
	public String getMd_site_menu() {
		return md_site_menu;
	}
	public void setMd_site_menu(String mdSiteMenu) {
		md_site_menu = mdSiteMenu;
	}
	public String getMd_type() {
		return md_type;
	}
	public void setMd_type(String mdType) {
		md_type = mdType;
	}
	public String getMd_title() {		
		return md_title;
	}
	public String getHtmlMd_title() {
		return su.toHtmlString(this.md_title);
	}
	public void setMd_title(String mdTitle) {
		md_title = mdTitle;
	}
	public String getMd_url() {
		return md_url;
	}
	public void setMd_url(String mdUrl) {
		md_url = mdUrl;
	}
	public String getMd_date() {
		return md_date;
	}
	//날짜 포멧
	public String getFormatMd_date(String dateFormat) {
		return du.getDate(this.md_date, dateFormat);
	}
	public void setMd_date(String mdDate) {
		md_date = mdDate;
	}
	public String getMd_content() {
		return su.toHtmlString(md_content);
	}
	public String getMd_contentNormal() {
		return this.md_content;
	}
	public void setMd_content(String mdContent) {
		this.md_content = mdContent;
	}
	//키워드 하이라이트
	public String getHighrightHtml(int length, String color)
    {    	
    	return su.cutKey(su.toHtmlString(this.md_content), this.k_value, length, color);
    }	
	public String changeKeyColor(String color)
    {
		String result = "";
		if(this.k_value == null){
			result = this.md_content;
		}else{
			
			result = su.changeKeyColor(this.md_content, this.k_value, color);
		}
    	return result;
    }	
	public String getMd_pseq() {
		return md_pseq;
	}
	public void setMd_pseq(String mdPseq) {
		md_pseq = mdPseq;
	}
	public String getK_value() {
		return k_value;
	}
	public void setK_value(String kValue) {
		k_value = kValue;
	}
	public String getMd_same_count() {
		return md_same_count;
	}
	public void setMd_same_count(String mdSameCount) {
		md_same_count = mdSameCount;
	}	
	public String getMd_reply_count() {
		return md_reply_count;
	}
	public void setMd_reply_count(String mdReplyCount) {
		md_reply_count = mdReplyCount;
	}
	public String getIssue_check() {
		return issue_check;
	}
	public void setIssue_check(String issueCheck) {
		issue_check = issueCheck;
	}
	public String getI_deldate() {
		return i_deldate;
	}
	public void setI_deldate(String iDeldate) {
		i_deldate = iDeldate;
	}
	//날짜 포멧
	public String getFormatI_deldate(String dateFormat) {
		return du.getDate(this.i_deldate, dateFormat);
	}
	public String getM_seq() {
		return m_seq;
	}
	public void setM_seq(String mSeq) {
		m_seq = mSeq;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String mName) {
		m_name = mName;
	}
	public String getMd_img() {
		return md_img;
	}
	public void setMd_img(String mdImg) {
		md_img = mdImg;
	}
	public String getMd_comfirm() {
		return md_comfirm;
	}
	public void setMd_comfirm(String md_comfirm) {
		this.md_comfirm = md_comfirm;
	}
	public String getL_alpha() {
		return l_alpha;
	}
	public void setL_alpha(String l_alpha) {
		this.l_alpha = l_alpha;
	}
  		        
}