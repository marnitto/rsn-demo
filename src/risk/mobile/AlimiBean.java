package risk.mobile;

import risk.util.DateUtil;

public class AlimiBean {

	
	private DateUtil du = new DateUtil();
	private String mal_Seq;              
	private String sd_Name;              
	private String sd_Menu;           
	private String mt_Title ;     
	private String mt_Date;       
	private String mt_Url; 	          
	private String mt_No;			 
	private String mal_Send_Date;		
	private String mt_Content;
  
	

	
	public String getMalSeq() {
		return mal_Seq;
	}
	public void setMalSeq(String malSeq) {
		mal_Seq = malSeq;
	}
	
	public String getSdName() {
		return sd_Name;
	}
	public void setSdName(String sdName) {
		sd_Name = sdName;
	}
	
	public String getSdMenu() {
		return sd_Menu;
	}
	public void setSdMenu(String sdMenu) {
		sd_Menu = sdMenu;
	}
	
	public String getMtTitle() {
		return mt_Title;
	}
	public void setMtTitle(String mtTitle) {
		mt_Title = mtTitle;
	}
	
	public String getMtDate() {
		return mt_Date;
	}
	
	//날짜 포멧
	public String getFormatMtDate(String dateFormat) {
		return du.getDate(this.mt_Date, dateFormat);
	}
	
	public void setMtDate(String mtDate) {
		mt_Date = mtDate;
	}
	
	public String getMtUrl() {
		return mt_Url;
	}
	public void setMtUrl(String mtUrl) {
		mt_Url = mtUrl;
	}
	
	public String getMtNo() {
		return mt_No;
	}
	public void setMtNo(String mtNo) {
		mt_No = mtNo;
	}
	
	public String getMalSendDate() {
		return mal_Send_Date;
	}
	public void setMalSendDate(String malSendDate) {
		mal_Send_Date = malSendDate;
	}
	
	public String getMtContent() {
		return mt_Content;
	}
	public void setMtContent(String mtContent) {
		mt_Content = mtContent;
	}
	
}
