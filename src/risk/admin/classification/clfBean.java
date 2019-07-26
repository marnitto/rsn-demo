package risk.admin.classification;

public class clfBean {
	
	public clfBean(){}
	
	private int		 ic_seq;
	private String 	 ic_name;
	private int 	 ic_type;
	private int 	 ic_code;
	private int 	 ic_ptype;
	private int 	 ic_pcode;
	private int 	 ic_mgr;
	private String 	 ic_wdate;
	private String 	 ic_wtime;
	private int 	 m_seq;
	private String 	 ic_description;

	public int 		getIc_mgr() 	{	return ic_mgr;		}
	public String 	getIc_name() 	{	return ic_name;		}
	public int 		getIc_seq() 	{	return ic_seq;		}
	public String 	getIc_wdate() 	{	return ic_wdate;	}
	public String 	getIc_wtime() 	{	return ic_wtime;	}
	public int 		getIc_code()	{	return ic_code;		}
	public int 		getIc_pcode() 	{	return ic_pcode;	}
	public int 		getIc_ptype() 	{	return ic_ptype;	}
	public int 		getIc_type() 	{	return ic_type;		}
	public int 		getM_seq() 		{	return m_seq;		}
	public String getIc_description() {	return ic_description;	}
	
	public void setIc_wdate(String ic_wdate)	{	this.ic_wdate = ic_wdate;	}
	public void setIc_wtime(String ic_wtime) 	{	this.ic_wtime = ic_wtime;	}
	public void setIc_code(int ic_code) 		{	this.ic_code = ic_code;		}
	public void setIc_pcode(int ic_pcode) 		{	this.ic_pcode = ic_pcode;	}
	public void setIc_ptype(int ic_ptype) 		{	this.ic_ptype = ic_ptype;	}
	public void setIc_type(int ic_type)			{	this.ic_type = ic_type;		}
	public void setM_seq(int m_seq) 			{	this.m_seq = m_seq;			}
	public void setIc_mgr(int ic_mgr) 			{	this.ic_mgr = ic_mgr;		}
	public void setIc_name(String ic_name) 		{	this.ic_name = ic_name;		}
	public void setIc_seq(int ic_seq) 			{	this.ic_seq = ic_seq;		}
	public void setIc_description(String ic_description) {	this.ic_description = ic_description; }
	
	
	public clfBean(   int 		ic_seq,
					  String 	ic_name,
					  int 		ic_type,
					  int		ic_code,
					  int 		ic_ptype,
					  int 		ic_pcode,
					  int 		ic_mgr,
					  String 	ic_wdate,
					  String 	ic_wtime,
					  int 		m_seq,
					  String ic_description)
	{
			this.ic_wdate 	= ic_wdate;	
			this.ic_wtime 	= ic_wtime;	
			this.ic_code 	= ic_code;	
			this.ic_pcode 	= ic_pcode;
			this.ic_ptype 	= ic_ptype;
			this.ic_type 	= ic_type;
			this.ic_mgr 	= ic_mgr;	
			this.ic_name 	= ic_name;	
			this.ic_seq 	= ic_seq;
			this.m_seq 		= m_seq;
			this.ic_description = ic_description;
	}
	
	public clfBean(   String 	ic_name,
					  int 		ic_type,
					  int		ic_code
				  )
	{
		this.ic_code 	= ic_code;	
		this.ic_type 	= ic_type;	
		this.ic_name 	= ic_name;	
	}
}
