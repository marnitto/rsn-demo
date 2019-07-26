package risk.issue;

import java.net.URLEncoder;
import java.util.ArrayList;

import risk.util.DateUtil;
import risk.util.StringUtil;

public class IssueDataBean {

	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();	
	private String id_seq;				//이슈데이터번호
	private String i_seq;				//이슈번호
	private String it_seq;				//이슈타이틀번호
	private String md_seq;				//기사번호
	private String s_seq;				//사이트번호
	private String sg_seq;				//사이트그룹번호
	private String md_site_menu;		//사이트 메뉴
	private String md_site_name;        //사이트이름
	private String md_date;				//수집일시
	private String id_title;			//제목
	private String id_url;				//사이트URL
	private String id_writter;          //글쓴이
	private String id_content;			//기사내용
	private String id_regdate;			//이슈등록일
	private String id_mailyn;			//메일여부
	private String id_useyn;			//이슈사용여부
	private String md_same_ct; 			//유사 개수
	private String m_seq;				//등록자번호
	private String m_name;				//등록자이름
	private ArrayList arrCodeList;		//이슈 코드리스트
	private ArrayList arrCommentList;	//이슈 코멘트리스트
	private String l_alpha;				//언어코드
	private String md_type;            	//정보유형
	private String md_pseq;            	//모기사 데이터 번호
	private String id_reportyn;        	//보고서 포함 여부
	private String sg_name;
	private String id_mobile;
	private String ic_code;
	private String ic_influence;       //영향력
	private String site_group_same_ct;			//사이트 그룹별 유사
	private String site_group_same_ct_check;	//사이트 그룹별 유사
	
	private String user_id;			//아이디
	private String user_nick;		//닉네임
	
	private String blog_visit_count;		//누적방문자수
	private String cafe_name;				//카페명
	private String cafe_member_count;		//카페회원수
	
	private String d_seq;		//문서번호
	
	private String k_xp;   			
	private String k_yp;
	private String media_info;
	private String f_news;
	
	private String h_seq;
	
	//hot keyword
	private String keywordInfo;
	
	private String md_same_ct_check; 			//최초 기사인지 확인
	
	private int p_cnt;
	private int n_cnt;
	private int g_cnt;
	private int[] cnt;
	
	// 임시 변수 
	private String temp1 ="";
	private String temp2 ="";
	private String temp3 ="";
	private String temp4 ="";
	
	//태그관련
	private String itg_seq;
	private String itg_name;
	
	private String itc_seq;
	private String itc_name;
	private int itc_cnt;
	
	private String relationkeys = ""; //연관키워드
	private String pressTitle = ""; //보도자료 제목
	private String media_name = ""; //매체 구분
	
	//보고서 원문 기사 링크 URL encode 처리
	public String getId_urlEncoding() {
	  String result = null;
		    
	  try {
		    result = URLEncoder.encode(id_url, "UTF-8");
		  } catch(Exception ex) {
		    ex.printStackTrace();
		  }
		    
	  return result;
	}

	
	
	
	public String getD_seq() {
		return d_seq;
	}




	public void setD_seq(String d_seq) {
		this.d_seq = d_seq;
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




	public String getSite_group_same_ct() {
		return site_group_same_ct;
	}
	public void setSite_group_same_ct(String site_group_same_ct) {
		this.site_group_same_ct = site_group_same_ct;
	}
	public String getSite_group_same_ct_check() {
		return site_group_same_ct_check;
	}
	public void setSite_group_same_ct_check(String site_group_same_ct_check) {
		this.site_group_same_ct_check = site_group_same_ct_check;
	}
	public String getIc_influence() {
		return ic_influence;
	}
	public void setIc_influence(String ic_influence) {
		this.ic_influence = ic_influence;
	}
	public String getIc_code() {
		return ic_code;
	}
	public void setIc_code(String icCode) {
		ic_code = icCode;
	}
	public String getId_mobile() {
		return id_mobile;
	}
	public void setId_mobile(String idMobile) {
		id_mobile = idMobile;
	}
	public String getSg_name() {
		return sg_name;
	}
	public void setSg_name(String sgName) {
		sg_name = sgName;
	}
	public int getItc_cnt() {
		return itc_cnt;
	}
	public void setItc_cnt(int itcCnt) {
		itc_cnt = itcCnt;
	}
	public String getH_seq() {
		return h_seq;
	}
	public void setH_seq(String hSeq) {
		h_seq = hSeq;
	}
	public String getKeywordInfo() {
		return keywordInfo;
	}
	public void setKeywordInfo(String keywordInfo) {
		this.keywordInfo = keywordInfo;
	}
	public String getItg_seq() {
		return itg_seq;
	}
	public void setItg_seq(String itgSeq) {
		itg_seq = itgSeq;
	}
	public String getItg_name() {
		return itg_name;
	}
	public void setItg_name(String itgName) {
		itg_name = itgName;
	}
	public String getItc_seq() {
		return itc_seq;
	}
	public void setItc_seq(String itcSeq) {
		itc_seq = itcSeq;
	}
	public String getItc_name() {
		return itc_name;
	}
	public void setItc_name(String itcName) {
		itc_name = itcName;
	}
	public int[] getCnt() {
		return cnt;
	}
	public void setCnt(int[] cnt) {
		this.cnt = cnt;
	}
	public String getMd_type() {
		return md_type;
	}
	public void setMd_type(String mdType) {
		md_type = mdType;
	}
	public String getId_seq() {
		return id_seq;
	}
	public void setId_seq(String iSeq) {
		id_seq = iSeq;
	}	
	public String getI_seq() {
		return i_seq;
	}
	public void setI_seq(String iSeq) {
		i_seq = iSeq;
	}
	public String getIt_seq() {
		return it_seq;
	}
	public void setIt_seq(String itSeq) {
		it_seq = itSeq;
	}
	public String getMd_seq() {
		return md_seq;
	}
	public void setMd_seq(String mdSeq) {
		md_seq = mdSeq;
	}
	public String getS_seq() {
		return s_seq;
	}
	public void setS_seq(String sSeq) {
		s_seq = sSeq;
	}	
	public String getMd_site_menu() {
		return md_site_menu;
	}
	public void setMd_site_menu(String mdSiteMenu) {
		md_site_menu = mdSiteMenu;
	}
	public String getMd_site_name() {
		return md_site_name;
	}
	public void setMd_site_name(String mdSiteName) {
		md_site_name = mdSiteName;
	}
	public String getMd_date() {
		return md_date;
	}
	public void setMd_date(String mdDate) {
		md_date = mdDate;
	}
	//날짜 포멧
	public String getFormatMd_date(String dateFormat) {
		return du.getDate(this.md_date, dateFormat);
	}	
	public String getId_writter() {
		return id_writter;
	}
	public void setId_writter(String idWritter) {
		id_writter = idWritter;
	}
	public String getId_content() {
		return id_content;
	}
	public void setId_content(String iContent) {
		id_content = iContent;
	}	
	public String getId_regdate() {
		return id_regdate;
	}
	public void setId_regdate(String iRegdate) {
		id_regdate = iRegdate;
	}
	//날짜 포멧
	public String getFormatId_regdate(String dateFormat) {
		return du.getDate(this.id_regdate, dateFormat);
	}
	public String getId_mailyn() {
		return id_mailyn;
	}
	public void setId_mailyn(String iMailyn) {
		id_mailyn = iMailyn;
	}	
	public String getSg_seq() {
		return sg_seq;
	}
	public void setSg_seq(String sgSeq) {
		sg_seq = sgSeq;
	}
	public String getId_title() {
		return su.toHtmlString(id_title);
	}
	public void setId_title(String idTitle) {
		id_title = idTitle;
	}	
	public String getId_url() {
		return id_url;
	}
	public void setId_url(String iUrl) {
		id_url = iUrl;
	}
	public String getId_useyn() {
		return id_useyn;
	}
	public void setId_useyn(String iUseyn) {
		id_useyn = iUseyn;
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
	public ArrayList getArrCodeList() {
		return arrCodeList;
	}
	public void setArrCodeList(ArrayList arrCodeList) {
		this.arrCodeList = arrCodeList;
	}	
	public ArrayList getArrCommentList() {
		return arrCommentList;
	}
	public void setArrCommentList(ArrayList arrCommentList) {
		this.arrCommentList = arrCommentList;
	}
	public String getMd_same_ct() {
		return md_same_ct;
	}
	public void setMd_same_ct(String mdSameCt) {
		md_same_ct = mdSameCt;
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
	public int getG_cnt() {
		return g_cnt;
	}
	public void setG_cnt(int gCnt) {
		g_cnt = gCnt;
	}			
	public String getL_alpha() {
		return l_alpha;
	}
	public void setL_alpha(String l_alpha) {
		this.l_alpha = l_alpha;
	}
	public String getMd_pseq() {
		return md_pseq;
	}
	public void setMd_pseq(String md_pseq) {
		this.md_pseq = md_pseq;
	}
	public String getId_reportyn() {
		return id_reportyn;
	}
	public void setId_reportyn(String id_reportyn) {
		this.id_reportyn = id_reportyn;
	}
	
	public String getK_xp() {
		return k_xp;
	}
	public void setK_xp(String k_xp) {
		this.k_xp = k_xp;
	}
	public String getK_yp() {
		return k_yp;
	}
	public void setK_yp(String k_yp) {
		this.k_yp = k_yp;
	}
	public String getMedia_info() {
		return media_info;
	}
	public void setMedia_info(String media_info) {
		this.media_info = media_info;
	}
	public String getF_news() {
		return f_news;
	}
	public void setF_news(String f_news) {
		this.f_news = f_news;
	}
	public String getMd_same_ct_check() {
		return md_same_ct_check;
	}
	public void setMd_same_ct_check(String md_same_ct_check) {
		this.md_same_ct_check = md_same_ct_check;
	}
	
	public String getTemp1() {
		return temp1;
	}
	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}
	public String getTemp2() {
		return temp2;
	}
	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}
	public String getTemp3() {
		return temp3;
	}
	public void setTemp3(String temp3) {
		this.temp3 = temp3;
	}
	public String getTemp4() {
		return temp4;
	}
	public void setTemp4(String temp4) {
		this.temp4 = temp4;
	}
	public String getRelationkeys() {
		return relationkeys;
	}
	public void setRelationkeys(String relationkeys) {
		this.relationkeys = relationkeys;
	}
	public String getPressTitle() {
		return pressTitle;
	}
	public void setPressTitle(String pressTitle) {
		this.pressTitle = pressTitle;
	}
	public String getMedia_name() {
		return media_name;
	}
	public void setMedia_name(String media_name) {
		this.media_name = media_name;
	}
	
}
