package risk.issue;

import risk.util.StringUtil;

public class IssueStaticBean {
	
	StringUtil  su = new StringUtil();
	
	private String 	msxVal;
	private int 	miyVal;
	private double 	miVal;
	
	// 그래프 용 
	private String 	codeName1="";
	private int 	code1=0;
	private String 	codeName2="";
	private int 	code2=0;
	private double 	codeValue=0.0;
	
	
	public String getCodeName1() 				{	return codeName1;	}
	public void setCodeName1(String codeName1)  {	this.codeName1 = su.nvl(codeName1, "");	}
	
	public int getCode1() 		    			{	return code1;	}
	public void setCode1(int code1) 			{	this.code1 = code1;	}

	public String getCodeName2() 				{	return codeName2;	}
	public void setCodeName2(String codeName2)  {	this.codeName2 = su.nvl(codeName2, "");	}
	
	public int getCode2() 						{	return code2;	}
	public void setCode2(int code2) 			{	this.code2 = code2;	}

	public double getCodeValue() 				{	return codeValue;	}
	public void setCodeValue(double codeValue)  {	this.codeValue = codeValue;	}

	public IssueStaticBean() {}
	
	public double getMiVal() {			return miVal;	}
	public int getMsyVal() {		return miyVal;	}
	public String getMsxVal() {		return msxVal;	}
	
	public void setMiVal(double miVal) {			this.miVal = miVal;	}
	public void setMsyVal(int miyVal) {		this.miyVal = miyVal;	}
	public void setMsxVal(String msxVal) {		this.msxVal = su.nvl(msxVal, "");	}

	public IssueStaticBean(String msxVal, int miyVal, double miVal) {
		this.msxVal = su.nvl(msxVal, "");
		this.miyVal = miyVal;
		this.miVal = miVal;
	}
	
	public IssueStaticBean(String codeName1, int code1, String codeName2, int code2, double codeValue) {
		this.codeName1 = su.nvl(codeName1, "");
		this.code1 = code1;
		this.codeName2 = su.nvl(codeName2, "");
		this.code2 = code2;
		this.codeValue = codeValue;
	}
}
