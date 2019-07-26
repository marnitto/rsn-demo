package risk.JfreeChart;

public class ChartBean {

	private String 	msxVal;
	private int 	miyVal;
	private int 	miVal;
	
	//jfreechart dataset
	private String  	data_seq;
	private String  	data_name;
	private String  	data_date;
	private String  	data_count;
	private String  	data_count2;
	
	public String getData_seq() {
		return data_seq;
	}

	public void setData_seq(String data_seq) {
		this.data_seq = data_seq;
	}

	public String getData_name() {
		return data_name;
	}

	public void setData_name(String data_name) {
		this.data_name = data_name;
	}

	public String getData_date() {
		return data_date;
	}

	public void setData_date(String data_date) {
		this.data_date = data_date;
	}

	public String getData_count() {
		return data_count;
	}

	public void setData_count(String data_count) {
		this.data_count = data_count;
	}

	public String getData_count2() {
		return data_count2;
	}

	public void setData_count2(String data_count2) {
		this.data_count2 = data_count2;
	}

	public ChartBean() {}
	
	public int getMiVal() {			return miVal;	}
	public int getMsyVal() {		return miyVal;	}
	public String getMsxVal() {		return msxVal;	}
	
	public void setMiVal(int miVal) {			this.miVal = miVal;	}
	public void setMsyVal(int miyVal) {		this.miyVal = miyVal;	}
	public void setMsxVal(String msxVal) {		this.msxVal = msxVal;	}

	public ChartBean(String msxVal, int miyVal, int miVal) {
		this.msxVal = msxVal;
		this.miyVal = miyVal;
		this.miVal = miVal;
	}
	
		public ChartBean(
				String data_seq, 
				String data_name, 
				String data_date, 
				String data_count2
				) 
	{
	this.data_seq = data_seq;
	this.data_name = data_name;
	this.data_date = data_date;
	this.data_count2 = data_count2;
	}
		
}
