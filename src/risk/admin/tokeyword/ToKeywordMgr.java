package risk.admin.tokeyword;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import risk.DBconn.DBconn;
import risk.admin.aekeyword.ExceptionKeywordBean;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class ToKeywordMgr {
	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	
	public ToKeywordBean[] getKeywordList(String SearchKey, String Oredered )
	{
		DBconn  conn  = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        ToKeywordBean[] arrAEKeyword= null;
        ToKeywordBean AEKeyword = null;
        int rowCnt = 0;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	
       	 	sb = new StringBuffer();
       	 	
       		sb.append(" SELECT EK_SEQ, EK_VALUE, DATE_FORMAT(EK_DATE,'%Y.%m.%d') EK_DATE, IFNULL(DATE_FORMAT(EK_UPDATE,'%Y.%m.%d'),'없음') EK_UPDATE, \n");
    	 	sb.append(" 	   EK_FWRITTER,  IFNULL(EK_LWRITTER,'없음') EK_LWRITTER, EK_POSITION, EK_USEYN \n");
			sb.append(" FROM RELATION_KEYWORD \n");
			
       	 	if( SearchKey.length() > 0 )
       	 	sb.append(" WHERE EK_VALUE LIKE '%"+SearchKey+"%' \n");
       	 	if( !Oredered.equals("") )
			sb.append(" ORDER BY "+Oredered+" 			  \n");
       	 	
       	 	Log.debug(sb.toString());
       	 	
       	 	pstmt = conn.createPStatement( sb.toString() );
       	 	rs = pstmt.executeQuery();
       	 	
       	 	rs.last();
       	 	if(rs.getRow()>0) arrAEKeyword = new ToKeywordBean[rs.getRow()];
       	 	rs.beforeFirst();
            
            while (rs.next()) {
            	AEKeyword = new ToKeywordBean();
            	AEKeyword.setEkSeq(rs.getString("EK_SEQ"));
            	AEKeyword.setEkValue(rs.getString("EK_VALUE"));
            	AEKeyword.setEkFwriter(rs.getString("EK_FWRITTER"));
            	AEKeyword.setEkLwriter(rs.getString("EK_LWRITTER"));
            	AEKeyword.setEkDate(rs.getString("EK_DATE"));
            	AEKeyword.setEkUpdate(rs.getString("EK_UPDATE"));
            	AEKeyword.setEkPosition(rs.getString("EK_POSITION"));
            	AEKeyword.setEkUseyn(rs.getString("EK_USEYN"));
            	
            	arrAEKeyword[rowCnt] =	AEKeyword;    		
            	rowCnt++;
            }
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return arrAEKeyword;
    }
	
	public ToKeywordBean getKeyword( String ekSeq )
	{
		DBconn  conn  = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        ArrayList arrList = new ArrayList();
        
        ToKeywordBean AEKeyword = null;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	
       	 	sb = new StringBuffer();
       	 	
       	 	sb.append(" SELECT EK_SEQ, EK_VALUE, DATE_FORMAT(EK_DATE,'%Y.%m.%d') EK_DATE, IFNULL(DATE_FORMAT(EK_UPDATE,'%Y.%m.%d'),'없음') EK_UPDATE, \n");
       	 	sb.append(" 	   EK_FWRITTER,  IFNULL(EK_LWRITTER,'없음') EK_LWRITTER, EK_POSITION \n");
			sb.append(" FROM RELATION_KEYWORD EK \n");
			sb.append(" WHERE EK_SEQ="+ekSeq+" \n");       	 	
       	 	
       	 	//Log.debug(sb.toString());
       	 	
       	 	pstmt = conn.createPStatement( sb.toString() );
       	 	rs = pstmt.executeQuery();
            
            if (rs.next()) {
            	AEKeyword = new ToKeywordBean();
            	AEKeyword.setEkSeq(rs.getString("EK_SEQ"));
            	AEKeyword.setEkValue(rs.getString("EK_VALUE"));
            	AEKeyword.setEkFwriter(rs.getString("EK_FWRITTER"));
            	AEKeyword.setEkLwriter(rs.getString("EK_LWRITTER"));
            	AEKeyword.setEkDate(rs.getString("EK_DATE"));
            	AEKeyword.setEkUpdate(rs.getString("EK_UPDATE"));
            	AEKeyword.setEkPosition(rs.getString("EK_POSITION"));
            }
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return AEKeyword;
    }
	
	public boolean insertKeyword( String ekValue, String mName )
	{
		DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        boolean rebln = false;
        
        int chkcount = 0;
        int chkPosition = 0;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();
       	 	
       	 	sb = new StringBuffer();
       	 	sb.append("SELECT COUNT(1) FROM RELATION_KEYWORD WHERE EK_VALUE='"+ekValue+"' \n");
            
            rs = stmt.executeQuery(sb.toString());

            if (rs.next()) {
            	chkcount = rs.getInt(1);
            }
            
           
            if( chkcount > 0 ) {
            	rebln = false;
            }else {            	
            	sb = new StringBuffer();
            	rs = null;
            	sb = new StringBuffer();
           	 	sb.append("SELECT MAX(EK_POSITION) FROM RELATION_KEYWORD  \n");                
                rs = stmt.executeQuery(sb.toString());
                
                if (rs.next()) {
                	chkPosition = rs.getInt(1);
                	chkPosition++;
                }
            	
	       	 	sb = new StringBuffer();	        
				sb.append(" INSERT INTO RELATION_KEYWORD (EK_VALUE, EK_FWRITTER, EK_DATE, EK_POSITION) \n");
			 	sb.append(" VALUES ('"+ekValue+"', '"+mName+"', '"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"',"+chkPosition+") \n");
	   	 	
			 	//Log.debug(sb.toString());
			 	stmt.executeUpdate(sb.toString());
			 	rebln = true;
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
        return rebln;
	
	}
	
	public boolean updateKeyword( String ekSeq, String ekValue, String mName)
	{
		DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        boolean rebln = false;
        
        int chkcount = 0;
        String chkWord = null;
        String chkWeight = null;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();       	 	
       	 	
       	 	sb = new StringBuffer();
    	 	sb.append("SELECT COUNT(1) FROM RELATION_KEYWORD WHERE EK_VALUE='"+ekValue+"' \n");
	         
	        rs = stmt.executeQuery(sb.toString());
	
	        if (rs.next()) {
            	chkcount = rs.getInt(1);
            }
	        
	        System.out.println("mgr 1111111111");
            System.out.println("chkcount:"+chkcount);          
	         
	        if( chkcount > 0 ) {
            	rebln = false;
            }else {
            	
	        	sb = new StringBuffer();
	            
				sb.append(" UPDATE RELATION_KEYWORD	\n");
				sb.append(" SET EK_VALUE='"+ekValue+"', 		\n");
				sb.append(" 	EK_UPDATE= '"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"', 	\n");		
				sb.append(" 	EK_LWRITTER='"+mName+"' 	\n");			
			 	sb.append(" WHERE EK_SEQ="+ekSeq+" 			\n");
	   	 	
			 	Log.debug(sb.toString());
			 	stmt.executeUpdate(sb.toString());
			 	rebln = true;
	        }
	        System.out.println("rebln:"+rebln);
       	 	
            
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
			return false;
        } catch(Exception ex) {
			Log.writeExpt(ex);
			return false;
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return rebln;
	
	}
	
	public boolean delKeyword(String delList )
	{
		DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        String[] arrList = null;
        boolean rebln = false;
        
        try {       	      	
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();	      
        	
        	arrList = delList.split(",");
        	
        	if(delList.length()>1000)
        	{
        		rebln = false;
        	}else{
	        	sb = new StringBuffer();
	            
	        	sb.append("DELETE FROM RELATION_KEYWORD		\n");
			 	sb.append("WHERE EK_SEQ IN ("+delList+")    		\n");
	   	 	
			 	Log.debug(sb.toString());
			 	stmt.executeUpdate(sb.toString());
			 	rebln = true;
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
        return rebln;
	
	}
	
	public String updateUseYn(String ekSeq, String yn )
	{
		DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        String[] arrList = null;
        String rebln = "";
        
        try {       	      	
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();	      

        	sb = new StringBuffer();
            
        	sb.append("UPDATE RELATION_KEYWORD SET EK_USEYN = '"+yn+"' WHERE EK_SEQ = "+ekSeq+"	\n");
   	 	
		 	Log.debug(sb.toString());
		 	if(stmt.executeUpdate(sb.toString()) > 0){
		 		if(yn.equals("Y")){
		 			rebln = "on";
		 		}else{
		 			rebln = "off";
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
        return rebln;
	}

}
