package risk.statistics;

public class StatisticsSuperBean {

	public class CodeNameBean{
		String date = "";
		String type = "";
		String code = "";
		String name = "";
		String cnt = "";
		
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCnt() {
			return cnt;
		}
		public void setCnt(String cnt) {
			this.cnt = cnt;
		}
	}
	
	public class twitterBean{
		String user = "";
		String cnt = "";
		
		String full_follower;
		String info;
		String md_title;
		
		public String getFull_follower() {
			return full_follower;
		}
		public void setFull_follower(String full_follower) {
			this.full_follower = full_follower;
		}
		public String getInfo() {
			return info;
		}
		public void setInfo(String info) {
			this.info = info;
		}
		public String getMd_title() {
			return md_title;
		}
		public void setMd_title(String md_title) {
			this.md_title = md_title;
		}
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public String getCnt() {
			return cnt;
		}
		public void setCnt(String cnt) {
			this.cnt = cnt;
		}

	}
	
	public class dailyChart{
		
		String category;
		int pcnt;
		String name1 = "긍정";
		int ncnt;
		String name2 = "부정";
		int ecnt;
		String name3 = "중립";
		
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public int getPcnt() {
			return pcnt;
		}
		public void setAddPcnt(int pcnt) {
			this.pcnt += pcnt;
		}
		public String getName1() {
			return name1;
		}
		public void setName1(String name1) {
			this.name1 = name1;
		}
		public int getNcnt() {
			return ncnt;
		}
		public void setAddNcnt(int ncnt) {
			this.ncnt += ncnt;
		}
		public String getName2() {
			return name2;
		}
		public void setName2(String name2) {
			this.name2 = name2;
		}
		public int getEcnt() {
			return ecnt;
		}
		public void setAddEcnt(int ecnt) {
			this.ecnt += ecnt;
		}
		public String getName3() {
			return name3;
		}
		public void setName3(String name3) {
			this.name3 = name3;
		}
		
		

	}
}
