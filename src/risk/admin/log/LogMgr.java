package risk.admin.log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;

public class LogMgr {
	
	private DateUtil du = new DateUtil();
	private StringBuffer sb = new StringBuffer();
	private DBconn  conn  = null;
	private Statement stmt = null;
    private ResultSet rs = null;
    private int result = 0;
    private int listCnt = 0;
    private int startNum = 0;
    private int endNum = 0;
    private ArrayList list = new ArrayList();
    private LogBean lBean = new LogBean();
    
    //리스트 총 갯수
    public int getListCnt() {
		return listCnt;
	}
    
    //로그 Kinds 반환
	public ArrayList getLogKindsList()
    {	
		try {
			list = new ArrayList();
			
			conn  = new DBconn();
		 	conn.getDBCPConnection();
		 	sb = new StringBuffer();
		 	
		 	sb.append(" SELECT LK_SEQ, LK_NAME FROM LOG_KINDS ORDER BY LK_SEQ ASC \n");		 	
		 	stmt = conn.createStatement();
	        rs = stmt.executeQuery(sb.toString());
	        System.out.println(sb.toString());	        
	        while(rs.next())
			{
				lBean = new LogBean();
				lBean.setL_kinds(rs.getString("LK_SEQ"));
				lBean.setL_kindsName(rs.getString("LK_NAME"));
				list.add(lBean);
			}
		        
		} catch(Exception ex) {
				Log.writeExpt(ex);
		} finally {
		     if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		     if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		     if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		return list;
    }
	
	//로그 type 반환
	public ArrayList getLogTypeList()
    {	
		try {
			list = new ArrayList();
			
			conn  = new DBconn();
		 	conn.getDBCPConnection();
		 	sb = new StringBuffer();
		 	
		 	sb.append(" SELECT LT_SEQ, LT_NAME FROM LOG_TYPE ORDER BY LT_SEQ ASC \n");		 	
		 	stmt = conn.createStatement();
	        rs = stmt.executeQuery(sb.toString());	        
	        System.out.println(sb.toString());
	        while(rs.next())
			{
	        	lBean = new LogBean();
	        	lBean.setL_type(rs.getString("LT_SEQ"));
				lBean.setL_typeName(rs.getString("LT_NAME"));
				list.add(lBean);
			}				
		        
		} catch(Exception ex) {
				Log.writeExpt(ex);
		} finally {
		     if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		     if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		     if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		return list;
    }
    
    
    //로그 리스트
	public ArrayList getLogList(
								int pageNum
								,int rowCnt
								,String l_kind
								,String l_type
								,String sch_mode
								,String sch_val
							   )
    {
		
		
		try {
			//지역변수 초기화
			startNum = 0;
			endNum = 0;
			listCnt = 0;
			list = new ArrayList();
			
			//리스트 시작, 끝번호 
			this.startNum = (pageNum-1)*rowCnt;
			this.endNum = rowCnt;
			
     		conn  = new DBconn();
    	 	conn.getDBCPConnection();
    	 	stmt = conn.createStatement();
    	 	
    	 	//리스트 총갯수
    	 	sb = new StringBuffer();
    	 	sb.append(" SELECT COUNT(*) CNT                  \n");    	
    		sb.append(" FROM LOG L INNER JOIN MEMBER M ON L.M_SEQ = M.M_SEQ         \n");
    		sb.append(" WHERE 1=1                            \n");
    		if(l_kind.length()>0) 
    		sb.append("       AND L.L_KINDS =   "+l_kind+"     \n");
    		if(l_type.length()>0) 
    		sb.append("       AND L.L_TYPE =    "+l_type+" 	 \n");
    		if(sch_val.length()>0)
    		{
    			if(sch_mode.equals("1"))
    			{
    				sb.append("       AND LOWER(M.M_ID) LIKE  '%"+sch_val.toLowerCase()+"%' 	 \n");
    			}else if(sch_mode.equals("2")){
        			sb.append("       AND LOWER(M.M_NAME) LIKE '%"+sch_val.toLowerCase()+"%' 	 \n");
    			}else if(sch_mode.equals("3")){
        			sb.append("       AND LOWER(L.L_IP) LIKE  '%"+sch_val.toLowerCase()+"%' 	 \n");
    			}else if(sch_mode.equals("")){
    				sb.append("  AND (LOWER(M.M_ID) LIKE  '%"+sch_val.toLowerCase()+"%' 	 \n");
	    			sb.append("       OR LOWER(M.M_NAME) LIKE '%"+sch_val.toLowerCase()+"%' 	 \n");
	    			sb.append("       OR LOWER(L.L_IP) LIKE  '%"+sch_val.toLowerCase()+"%') 	 \n");
    			}
    		}
    		    		
    		rs = stmt.executeQuery(sb.toString());
    		 System.out.println(sb.toString());
    		if(rs.next()){
    			this.listCnt =  rs.getInt("CNT");
    		}
    		
    		//리스트 파싱
    		rs.close();    		
    	 	sb = new StringBuffer();
    	 	sb.append(" SELECT L.L_SEQ                         \n");
    		sb.append("       ,L.L_KINDS                       \n");
    		sb.append("       ,(SELECT LK_NAME FROM LOG_KINDS  WHERE LK_SEQ = L.L_KINDS) AS KINDSNAME  \n");
    		sb.append("       ,L.L_TYPE                        \n");
    		sb.append("       ,(SELECT LT_NAME FROM LOG_TYPE  WHERE LT_SEQ = L.L_TYPE) AS TYPENAME  \n");
    		sb.append("       ,IFNULL(L.L_IP,'') AS L_IP         \n");
    		sb.append("       ,L.L_KEY                           \n");
    		sb.append("       ,L.L_REGDATE                        \n");
    		sb.append("       ,L.M_SEQ                         \n");
    		sb.append("       ,M.M_ID                         \n");
    		sb.append("       ,M.M_NAME                         \n");
    		sb.append(" FROM LOG L INNER JOIN MEMBER M ON L.M_SEQ = M.M_SEQ \n");
    		sb.append(" WHERE 1=1                            \n");
    		if(l_kind.length()>0) 
    		sb.append("       AND L_KINDS =   "+l_kind+"     \n");
    		if(l_type.length()>0) 
    		sb.append("       AND L_TYPE =    "+l_type+" 	 \n");
    		if(sch_val.length()>0)
    		{
    			if(sch_mode.equals("1"))
    			{
    				sb.append("       AND LOWER(M.M_ID) LIKE  '%"+sch_val.toLowerCase()+"%' 	 \n");
    			}else if(sch_mode.equals("2")){
        			sb.append("       AND LOWER(M.M_NAME) LIKE '%"+sch_val.toLowerCase()+"%' 	 \n");
    			}else if(sch_mode.equals("3")){
        			sb.append("       AND LOWER(L.L_IP) LIKE  '%"+sch_val.toLowerCase()+"%' 	 \n");
    			}else if(sch_mode.equals("")){
    				sb.append("  AND (LOWER(M.M_ID) LIKE  '%"+sch_val.toLowerCase()+"%' 	 \n");
	    			sb.append("       OR LOWER(M.M_NAME) LIKE '%"+sch_val.toLowerCase()+"%' 	 \n");
	    			sb.append("       OR LOWER(L.L_IP) LIKE  '%"+sch_val.toLowerCase()+"%') 	 \n");
    			}
    		}
    		sb.append(" ORDER BY L_REGDATE DESC \n");
    		sb.append(" LIMIT   "+startNum+","+endNum+"                           \n");   	 	
    	 	
            rs = stmt.executeQuery(sb.toString());
            System.out.println(sb.toString());
            while(rs.next()){
            	lBean = new LogBean();
            	lBean.setL_seq(rs.getString("L_SEQ"));
            	lBean.setL_kinds(rs.getString("L_KINDS"));
            	lBean.setL_kindsName(rs.getString("KINDSNAME"));
            	lBean.setL_type(rs.getString("L_TYPE"));
            	lBean.setL_typeName(rs.getString("TYPENAME"));
            	lBean.setL_ip(rs.getString("L_IP"));            	
            	lBean.setKey(rs.getString("L_KEY"));
            	lBean.setL_date(rs.getString("L_REGDATE").substring(0,19));
            	lBean.setM_seq(rs.getString("M_SEQ"));
            	lBean.setM_id(rs.getString("M_ID"));
            	lBean.setM_name(rs.getString("M_NAME"));
            	list.add(lBean);
    		}
            
     } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
     } catch(Exception ex) {
			Log.writeExpt(ex);
     } finally {
         if (rs != null) try { rs.close(); } catch(SQLException ex) {}
         if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
         if (conn != null) try { conn.close(); } catch(SQLException ex) {}
     }	
		return list;
    }	
	
    
	//로그를 저장하고 해당키를 반환
    public String insertLog(LogBean lBean)
    { 
    	String insertNum = null;
		try {
		 		conn  = new DBconn();
			 	conn.getDBCPConnection();
			 	sb = new StringBuffer();
			 			 	
			 	sb.append("INSERT INTO LOG(L_KINDS, L_TYPE, L_IP, L_KEY, L_REGDATE, M_SEQ) \n");
				sb.append("VALUES( \n");
				sb.append("       "+lBean.getL_kinds()+"\n");
				sb.append("       ,"+lBean.getL_type()+"\n");
				sb.append("       ,'"+lBean.getL_ip()+"' \n");						
				sb.append("       ,"+lBean.getKey()+"\n");				
				sb.append("       ,'"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"' \n");
				sb.append("       ,"+lBean.getM_seq()+"\n");
				sb.append("		  ) ");
			 	
			 	stmt = conn.createStatement();
		       
		        if(stmt.executeUpdate(sb.toString())>0) {
					sb = new StringBuffer();
					sb.append(" SELECT MAX(L_SEQ) L_SEQ FROM LOG                              \n");			
					rs = stmt.executeQuery(sb.toString());
					if(rs.next()){
						insertNum = rs.getString("L_SEQ");
					}					
				}			
		        
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
		     if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		     if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		     if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return insertNum;
    }
    
    //메일 로그 수신자 저장
    public boolean insertLogReceiver(String l_seq, String[] receiver)
    { 
    	boolean result = false;
    	String ab_seq;
		try {
		 		conn  = new DBconn();
			 	conn.getDBCPConnection();
			 	stmt = conn.createStatement();			 	
			 	
			 	if(receiver !=null){
				 	for(int i=0;i<receiver.length;i++)
				 	{
				 		ab_seq = receiver[i];
				 		sb = new StringBuffer();
					 	sb.append("INSERT INTO LOG_RECEIVER(L_SEQ,AB_SEQ,LR_REGDATE) \n");
						sb.append("VALUES( \n");
						sb.append("       "+l_seq+"\n");
						sb.append("       ,"+ab_seq+"\n");
						sb.append("       ,'"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"' \n");	
						sb.append("		  ) ");
						if(stmt.executeUpdate(sb.toString())>0) result = true;
				 	}	
			 	}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
		     if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		     if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		     if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return result;
    }
}
