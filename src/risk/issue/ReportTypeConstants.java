package risk.issue;
/**
 * 보고서 타입 및 이름
 * @return
 */
public class ReportTypeConstants {
	public static String emergencyVal ="E";
	public static String emergencyName ="긴급보고서";
	public static String dailyVal ="D1" ;
	public static String dailyName ="일일보고서";
	public static String weeklyVal ="W" ;
	public static String weeklyName ="주간보고서";
	public static String peoridVal = "P";
	public static String peoridName ="기간보고서";
	public static String issueVal = "I";
	public static String issueName ="일일보고서(주요이슈포함)";
	public static String monthlyVal = "M";
	public static String monthlyName ="월간보고서";
	
	public static String getEmergencyVal() {
		return emergencyVal;
	}
	public static String getEmergencyName() {
		return emergencyName;
	}
	public static String getDailyVal() {
		return dailyVal;
	}
	public static String getDailyName() {
		return dailyName;
	}
	public static String getWeeklyVal() {
		return weeklyVal;
	}
	public static String getWeeklyName() {
		return weeklyName;
	}
	public static String getPeoridVal() {
		return peoridVal;
	}
	public static String getPeoridName() {
		return peoridName;
	}
	public static String getIssueVal() {
		return issueVal;
	}	
	public static String getIssueName() {
		return issueName;
	}
	public static String getMonthlyVal() {
		return monthlyVal;
	}
	
	
	
	
	
	
}
