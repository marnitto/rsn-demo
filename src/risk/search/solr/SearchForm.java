package risk.search.solr;


import java.util.ArrayList;


import risk.util.StringUtil;
	
	
public class SearchForm{ 
	
	private StringUtil su = new StringUtil();
	
	//RA_RISK_DATA_TEMP TABLE
	private int docid;
	private int gsn;
	private String html;
	private String img_name;
	private int isp;
	private String menu;
	private String name;
	private int pid;
	private String sdate;
	private int sgroup;
	private int sn;
	private String stime;
	private int subid;
	private String title;
	private int type;
	private String url;

	
	public int getDocid() {
		return docid;
	}
	public void setDocid(int docid) {
		this.docid = docid;
	}
	public int getGsn() {
		return gsn;
	}
	public void setGsn(int gsn) {
		this.gsn = gsn;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getImg_name() {
		return img_name;
	}
	public void setImg_name(String img_name) {
		this.img_name = img_name;
	}
	public int getIsp() {
		return isp;
	}
	public void setIsp(int isp) {
		this.isp = isp;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public int getSgroup() {
		return sgroup;
	}
	public void setSgroup(int sgroup) {
		this.sgroup = sgroup;
	}
	public int getSn() {
		return sn;
	}
	public void setSn(int sn) {
		this.sn = sn;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public int getSubid() {
		return subid;
	}
	public void setSubid(int subid) {
		this.subid = subid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	//?�워???�이?�이??
	public String getHighrightHtml(int length, String color, String key)
    {    	
    	return su.cutKey(su.toHtmlString(this.html),key, length, color);
    }		
	
	
	
}
