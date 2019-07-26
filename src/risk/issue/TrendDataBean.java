package risk.issue;

import java.util.*;
import risk.util.StringUtil;

public class TrendDataBean {
	
	StringUtil  su = new StringUtil();
	
    private String Cname ;	// data 계열의 명칭
    private ArrayList Cdatas = new ArrayList();	// data list
	
    /**
     * 클래스 생성자
     */
    public TrendDataBean(   String name    	,
    						ArrayList datas 
                        )
    {
        this.Cname	= su.nvl(name, "")	;
        this.Cdatas	= datas	;
    }

	
	public String getName() {
		return Cname;
	}
	
	
	public ArrayList getData() {
		return Cdatas;
	}

	public void setName(String name) {
		this.Cname = su.nvl(name, "");
	}

	
	public void setCount(ArrayList datas) {
		this.Cdatas = datas;
	}
	
	public void addData(String data) {
		this.Cdatas.add(new String(data));
	}
	public void addData(int data) {
		this.Cdatas.add(new Integer(data));
	}
	public void addData(float data) {
		this.Cdatas.add(new Float(data));
	}
	public void addData(Double data) {
		this.Cdatas.add(data);
	}
	public void addData(double data) {
		this.Cdatas.add(new Double (data) );
	}
	public void resetDatas() {
		this.Cdatas.clear();
		this.Cname = "";
	}
	
	
}

