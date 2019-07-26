package risk.taglib;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class AutoTagDao {
	
	private AutoTagBean atBean = null;
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs    = null;	
	private StringBuffer sb = null;	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	

    public ArrayList findByName(String qeuryId, String name)    
    {   	    	
    	ArrayList arrList = new ArrayList(); 
    	    	
    	try{ 
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		stmt = dbconn.createStatement();
    		sb = new StringBuffer();
    		
    		if(qeuryId.equals("EXAMPLE")){
    			
    		}else if(qeuryId.equals("WRITER")){
    			sb.append(" SELECT IC_NAME AS NAME, IC_TYPE AS VALUE1, IC_CODE AS VALUE2 FROM ISSUE_CODE WHERE IC_TYPE =7 AND IC_CODE <> 0 AND IC_NAME LIKE '%"+name+"%' AND IC_USEYN='Y'  GROUP BY IC_NAME \n");
    		}else if(qeuryId.equals("PROGRAM")){
    			
    		}			
			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				atBean = new AutoTagBean();
				if(qeuryId.equals("EXAMPLE")){
					
					atBean.setName(rs.getString("NAME"));
					atBean.setValue(rs.getString("VALUE"));
					
				}else if(qeuryId.equals("WRITER")){
					
					atBean.setName(rs.getString("NAME"));
					atBean.setValue(rs.getString("VALUE1"));
					atBean.setValue2(rs.getString("VALUE2"));
					
				}else if(qeuryId.equals("PROGRAM")){
					
					atBean.setSeq(rs.getString("SEQ"));
					atBean.setName(rs.getString("NAME"));
					atBean.setValue(rs.getString("VALUE"));
					atBean.setName2(rs.getString("NAME2"));
					atBean.setValue2(rs.getString("VALUE2"));
					atBean.setName3(rs.getString("NAME3"));
					atBean.setValue3(rs.getString("VALUE3"));
					
				}
				arrList.add(atBean);
			}												
		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		
	    return arrList;
	 }
	
}
