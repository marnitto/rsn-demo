package risk.admin.blacksite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import risk.DBconn.DBconn;
import risk.admin.blacksite.BlackSiteBean;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class BlackSiteMgr {
	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	
	public BlackSiteBean[] getKeywordList(String SearchKey, String Oredered )
	{
		DBconn  conn  = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        BlackSiteBean[] arrAEKeyword= null;
        BlackSiteBean AEKeyword = null;
        int rowCnt = 0;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	
       	 	sb = new StringBuffer();
       	 	
       		sb.append(" SELECT BS_SEQ, BS_VALUE, DATE_FORMAT(BS_DATE,'%Y.%m.%d') BS_DATE, IFNULL(DATE_FORMAT(BS_UPDATE,'%Y.%m.%d'),'없음') BS_UPDATE, \n");
    	 	sb.append(" 	   BS_FWRITTER,  IFNULL(BS_LWRITTER,'없음') BS_LWRITTER, BS_POSITION \n");
			sb.append(" FROM BLACK_SITE \n");
			
       	 	if( SearchKey.length() > 0 )
       	 	sb.append(" WHERE BS_VALUE LIKE '%"+SearchKey+"%' \n");
       	 	if( !Oredered.equals("") )
			sb.append(" ORDER BY "+Oredered+" 			  \n");
       	 	
       	 	Log.debug(sb.toString());
       	 	
       	 	pstmt = conn.createPStatement( sb.toString() );
       	 	rs = pstmt.executeQuery();
       	 	
       	 	rs.last();
       	 	if(rs.getRow()>0) arrAEKeyword = new BlackSiteBean[rs.getRow()];
       	 	rs.beforeFirst();
            
            while (rs.next()) {
            	AEKeyword = new BlackSiteBean();
            	AEKeyword.setBsSeq(rs.getString("BS_SEQ"));
            	AEKeyword.setBsValue(rs.getString("BS_VALUE"));
            	AEKeyword.setBsFwriter(rs.getString("BS_FWRITTER"));
            	AEKeyword.setBsLwriter(rs.getString("BS_LWRITTER"));
            	AEKeyword.setBsDate(rs.getString("BS_DATE"));
            	AEKeyword.setBsUpdate(rs.getString("BS_UPDATE"));
            	AEKeyword.setBsPosition(rs.getString("BS_POSITION"));         	
            	
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
	
	public BlackSiteBean getKeyword( String ekSeq )
	{
		DBconn  conn  = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        ArrayList arrList = new ArrayList();
        
        BlackSiteBean AEKeyword = null;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	
       	 	sb = new StringBuffer();
       	 	
       	 	sb.append(" SELECT BS_SEQ, BS_VALUE, DATE_FORMAT(BS_DATE,'%Y.%m.%d') BS_DATE, IFNULL(DATE_FORMAT(BS_UPDATE,'%Y.%m.%d'),'없음') BS_UPDATE, \n");
       	 	sb.append(" 	   BS_FWRITTER,  IFNULL(BS_LWRITTER,'없음') BS_LWRITTER, BS_POSITION, IFNULL(K_SEQS,'') AS K_SEQS, IFNULL(S_SEQS,'') AS S_SEQS \n");
			sb.append(" FROM BLACK_SITE BS \n");
			sb.append(" WHERE BS_SEQ="+ekSeq+" \n");       	 	
       	 	
       	 	//Log.debug(sb.toString());
       	 	
       	 	pstmt = conn.createPStatement( sb.toString() );
       	 	rs = pstmt.executeQuery();
            
            if (rs.next()) {
            	AEKeyword = new BlackSiteBean();
            	AEKeyword.setBsSeq(rs.getString("BS_SEQ"));
            	AEKeyword.setBsValue(rs.getString("BS_VALUE"));
            	AEKeyword.setBsFwriter(rs.getString("BS_FWRITTER"));
            	AEKeyword.setBsLwriter(rs.getString("BS_LWRITTER"));
            	AEKeyword.setBsDate(rs.getString("BS_DATE"));
            	AEKeyword.setBsUpdate(rs.getString("BS_UPDATE"));
            	AEKeyword.setBsPosition(rs.getString("BS_POSITION"));
            	AEKeyword.setK_seqs(rs.getString("K_SEQS"));
            	AEKeyword.setS_seqs(rs.getString("S_SEQS"));
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
       	 	sb.append("SELECT COUNT(1) FROM BLACK_SITE WHERE BS_VALUE='"+ekValue+"' \n");
            
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
           	 	sb.append("SELECT MAX(BS_POSITION) FROM BLACK_SITE  \n");                
                rs = stmt.executeQuery(sb.toString());
                
                if (rs.next()) {
                	chkPosition = rs.getInt(1);
                	chkPosition++;
                }
            	
	       	 	sb = new StringBuffer();	        
				sb.append(" INSERT INTO BLACK_SITE (BS_VALUE, BS_FWRITTER, BS_DATE, BS_POSITION) \n");
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
    	 	sb.append("SELECT COUNT(1) FROM BLACK_SITE WHERE BS_VALUE='"+ekValue+"' \n");
	         
	        rs = stmt.executeQuery(sb.toString());
	
	        if (rs.next()) {
            	chkcount = rs.getInt(1);
            }
	        
	        if( chkcount > 0 ) {
            	rebln = false;
            }else {
            	
	        	sb = new StringBuffer();
	            
				sb.append(" UPDATE BLACK_SITE	\n");
				sb.append(" SET BS_VALUE='"+ekValue+"', 		\n");
				sb.append(" 	BS_UPDATE= '"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"', 	\n");		
				sb.append(" 	BS_LWRITTER='"+mName+"' 	\n");			
			 	sb.append(" WHERE BS_SEQ="+ekSeq+" 			\n");
	   	 	
			 	Log.debug(sb.toString());
			 	stmt.executeUpdate(sb.toString());
			 	rebln = true;
	        }
       	 	
            
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
	            
	        	sb.append("DELETE FROM BLACK_SITE		\n");
			 	sb.append("WHERE BS_SEQ IN ("+delList+")    		\n");
	   	 	
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
	
	public ArrayList getAllKeyword(String ek_seq, BlackSiteBean.Type type)
	{
		DBconn  conn  = null;
        Statement stmt = null;
        //PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        ArrayList arrList = new ArrayList();
        
        BlackSiteBean AEKeyword = null;
        
        ArrayList result = new ArrayList();
        
        String not  = "";
        
        if(type == BlackSiteBean.Type.LEFT){
        	not = "NOT";
        }else if(type == BlackSiteBean.Type.RIGHT){
        	not = "";
        }
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();

       	 	sb = new StringBuffer();
       	 	sb.append("SELECT IFNULL(K_SEQS,'') AS K_SEQS FROM BLACK_SITE WHERE BS_SEQ = "+ek_seq+"\n");
       	 	System.out.println(sb.toString());
       	 	rs = stmt.executeQuery(sb.toString());
       	 	String k_seqs = "";
       	 	if(rs.next()){
       	 		k_seqs = rs.getString("K_SEQS");
    	 	}
       	 
       	 	if(type == BlackSiteBean.Type.LEFT || !k_seqs.equals("")){
       	 		
       	 		sb = new StringBuffer();
				sb.append("SELECT A.K_SEQ, B.K_VALUE AS K_VALUE1, C.K_VALUE AS K_VALUE2, A.K_VALUE AS K_VALUE3 						\n");
				sb.append("  FROM (SELECT K_SEQ, K_XP, K_YP, K_ZP, K_VALUE FROM KEYWORD WHERE K_XP > 0 AND K_YP > 0 AND K_ZP > 0)A	\n");
				sb.append("     , (SELECT K_XP, K_VALUE FROM KEYWORD WHERE K_XP > 0 AND K_YP = 0 AND K_ZP = 0)B 					\n");
				sb.append("     , (SELECT K_XP, K_YP, K_VALUE FROM KEYWORD WHERE K_XP > 0 AND K_YP > 0 AND K_ZP = 0)C 				\n");
				sb.append(" WHERE A.K_XP = B.K_XP																					\n");
				sb.append("   AND A.K_XP = C.K_XP																					\n");
				sb.append("   AND A.K_YP = C.K_YP																					\n");
				if(!k_seqs.equals("")){
					sb.append("   AND A.K_SEQ "+not+" IN ("+k_seqs+")																\n");
				}
				sb.append(" ORDER BY A.K_XP, K_VALUE2, K_VALUE3																			\n");
				
				System.out.println(sb.toString());
	       	 	rs = stmt.executeQuery(sb.toString());
	       	 	
	       	 	while(rs.next()){
	       	 		AEKeyword = new BlackSiteBean();
	       	 		
	       	 		AEKeyword.setK_seq(rs.getString("K_SEQ"));
	       	 		AEKeyword.setK_value1(rs.getString("K_VALUE1"));
	       	 		AEKeyword.setK_value2(rs.getString("K_VALUE2"));
	       	 		AEKeyword.setK_value3(rs.getString("K_VALUE3"));
	       	 		result.add(AEKeyword);
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
	
	public ArrayList getAllSite(String ek_seq, BlackSiteBean.Type type)
	{
		DBconn  conn  = null;
        Statement stmt = null;
        //PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        ArrayList arrList = new ArrayList();
        
        BlackSiteBean AEKeyword = null;
        
        ArrayList result = new ArrayList();
        
        String not  = "";
        
        if(type == BlackSiteBean.Type.LEFT){
        	not = "NOT";
        }else if(type == BlackSiteBean.Type.RIGHT){
        	not = "";
        }
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();
       	 	
	       	sb = new StringBuffer();
    	 	sb.append("SELECT IFNULL(S_SEQS,'') AS S_SEQS FROM BLACK_SITE WHERE BS_SEQ = "+ek_seq+"\n");
    	 	System.out.println(sb.toString());
    	 	rs = stmt.executeQuery(sb.toString());
    	 	String s_seqs = "";
    	 	if(rs.next()){
    	 		s_seqs = rs.getString("S_SEQS");
	 	 	}
       	 
       	 	if(type == BlackSiteBean.Type.LEFT || !s_seqs.equals("")){
	       	 	sb = new StringBuffer();
				sb.append("SELECT A.S_SEQ, A.S_NAME, B.S_URL	\n");
				sb.append("  FROM SG_S_RELATION A				\n");
				sb.append("     , SITE B						\n");
				sb.append(" WHERE A.S_SEQ = B.S_SEQ				\n");
				if(!s_seqs.equals("")){
					sb.append("   AND A.S_SEQ "+not+" IN ("+s_seqs+")	\n");
				}
				sb.append(" ORDER BY A.S_NAME	\n");
				
				System.out.println(sb.toString());
	       	 	rs = stmt.executeQuery(sb.toString());
	       	 	
	       	 	while(rs.next()){
	       	 		AEKeyword = new BlackSiteBean();
	       	 		
	       	 		AEKeyword.setS_seq(rs.getString("S_SEQ"));
	       	 		AEKeyword.setS_value1(rs.getString("S_NAME"));
	       	 		AEKeyword.setS_url(rs.getString("S_URL"));
	       	 		result.add(AEKeyword);
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
	
	public ArrayList UpdateExkeyword(String ek_seq , String targetSeq, BlackSiteBean.Mode mode, BlackSiteBean.Type2 type2)
	{
		DBconn  conn  = null;
        Statement stmt = null;
        //PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        ArrayList arrList = new ArrayList();
        BlackSiteBean AEKeyword = null;
        
        ArrayList result = new ArrayList();
        ArrayList<String> newData = new ArrayList<String>();
        
        String not  = "";
        
        
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();
       	 
       	 	String col = "";
       	 	if(mode == BlackSiteBean.Mode.KEYWORD){
       	 		col = "K_SEQS";
       	 	}else if(mode == BlackSiteBean.Mode.SITE){
       	 		col = "S_SEQS";
       	 	}
       	 	
       	 	sb = new StringBuffer();
       	 	sb.append("SELECT IFNULL("+col+",'') AS SEQS FROM BLACK_SITE WHERE BS_SEQ = "+ek_seq+"  LIMIT 1	\n");
       	 	
			System.out.println(sb.toString());
       	 	rs = stmt.executeQuery(sb.toString());
       	 	
       	 	String[] ar_seqs = null;
       	 	if(rs.next()){
       	 		ar_seqs = rs.getString("SEQS").split(",");	
       	 	}
       	 	
       	 	//더하기
       	 	if(type2 == BlackSiteBean.Type2.ADD){
       	 		for(int i =0; i < ar_seqs.length; i++){
       	 			newData.add(ar_seqs[i]);
       	 		}
       	 		newData.add(targetSeq);
       	 	//빼기
       	 	}else if(type2 == BlackSiteBean.Type2.DEL){
	       	 	for(int i =0; i < ar_seqs.length; i++){
	   	 			if(!ar_seqs[i].trim().equals(targetSeq.trim())){
	   	 				newData.add(ar_seqs[i]);
	   	 			}
	   	 		}
       	 	}
       	 	
       	 	//배열 병합
       	 	String newSeqs = "";
       	 	for(int i =0; i < newData.size(); i ++){
       	 		if(newSeqs.equals("")){
       	 			newSeqs = newData.get(i);
       	 		}else{
       	 			newSeqs += "," + newData.get(i);
       	 		}
       	 	}
       	 	
       	 	sb = new StringBuffer();
    	 	sb.append("UPDATE BLACK_SITE SET "+col+" = '"+newSeqs+"' WHERE BS_SEQ = "+ek_seq+" \n");
    	 	System.out.println(sb.toString());
       	 	stmt.executeUpdate(sb.toString());
       	
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
