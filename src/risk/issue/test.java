package risk.issue;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;


public class test {
	
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private PreparedStatement pstmt = null;
	private StringBuffer sb = null;	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();

	
	public JSONArray selectCodeList(String ic_type)
	{	
		JSONArray result = null;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append("SELECT IC_TYPE, IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+ic_type+" AND IC_USEYN='Y' AND IC_CODE > 0 ORDER BY IC_CODE ASC ");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());			
			result = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("ic_type", rs.getString("IC_TYPE"));
				obj.put("ic_code", rs.getString("IC_CODE"));
				obj.put("ic_name", rs.getString("IC_NAME"));
				result.put(obj);
			}
																			
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
		    sb = null;
		    if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}            
		}        
		return result;
	}
	
	
	public JSONArray selectDataListCount(String sDate, String eDate, String typeCode, int pageIdx)
	{	
		JSONArray result = null;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" SELECT                                                                       												\n");
			sb.append("       COUNT(0) AS CNT                                                       										\n");
			sb.append("   FROM ISSUE_DATA A                                                         										\n");
			if(!"".equals(typeCode)){
				String temp[] = typeCode.split("@");
				for(int i=0; i < temp.length; i++){
			sb.append(" INNER JOIN ISSUE_DATA_CODE IC"+i+" ON A.ID_SEQ = IC"+i+".ID_SEQ AND IC"+i+".IC_TYPE = "+temp[i].split(",")[0]+" AND IC"+i+".IC_CODE = "+temp[i].split(",")[1]+" \n");
				}
			}
			sb.append("  WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'  			\n");
			sb.append("    AND A.MD_SEQ = A.MD_PSEQ                                                  								\n");
			sb.append("  ORDER BY A.MD_DATE DESC												\n");	
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());			
			result = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("totalCnt", rs.getString("CNT"));
				obj.put("pageIdx", pageIdx);
				result.put(obj);
			}
																			
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
		    sb = null;
		    if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}            
		}        
		return result;
	}
	
	public JSONArray selectDataList(String sDate, String eDate, String typeCode, int pageIndex, int rowCnt)
	{	
		int startNum = (pageIndex-1)*rowCnt;
		JSONArray result = null;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" SELECT                                                                       												\n");
			sb.append("        A.ID_TITLE                                                            											\n");
			sb.append("       ,A.ID_URL                                                              											\n");
			sb.append("       ,A.MD_SITE_NAME                                                        										\n");
			sb.append("       ,A.S_SEQ                                                       														\n");
			sb.append("       ,DATE_FORMAT(A.MD_DATE, '%Y-%m-%d') AS MD_DATE                         						\n");
			sb.append("       ,A.MD_SAME_CT                                                       										\n");
			sb.append("       ,FN_GET_ISSUE_CODE_NAME(A.ID_SEQ,9) AS SENTI                                                       										\n");
			sb.append("   FROM ISSUE_DATA A                                                         										\n");
			if(!"".equals(typeCode)){
				String temp[] = typeCode.split("@");
				for(int i=0; i < temp.length; i++){
			sb.append(" INNER JOIN ISSUE_DATA_CODE IC"+i+" ON A.ID_SEQ = IC"+i+".ID_SEQ AND IC"+i+".IC_TYPE = "+temp[i].split(",")[0]+" AND IC"+i+".IC_CODE = "+temp[i].split(",")[1]+" \n");
				}
			}
			sb.append("  WHERE A.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate + " 23:59:59'  			\n");
			sb.append("    AND A.MD_SEQ = A.MD_PSEQ                                                  								\n");
			sb.append("  ORDER BY A.MD_DATE DESC												\n");	
			sb.append("  LIMIT " + startNum + "," + rowCnt + "																			\n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());			
			result = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("title", rs.getString("ID_TITLE"));
				obj.put("url", rs.getString("ID_URL"));
				obj.put("origin", rs.getString("MD_SITE_NAME"));
				obj.put("site_no", rs.getString("S_SEQ"));
				obj.put("date", rs.getString("MD_DATE"));
				obj.put("same_cnt", rs.getString("MD_SAME_CT"));
				obj.put("senti", rs.getString("SENTI"));
				result.put(obj);
			}
																			
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
		    sb = null;
		    if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}            
		}        
		return result;
	}
	
}
