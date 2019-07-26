package risk.admin.keyword;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import risk.DBconn.DBconn;
import risk.admin.log.LogBean;
import risk.admin.log.LogConstants;
import risk.admin.log.LogMgr;
import risk.search.MetaMgr;
import risk.util.DateUtil;
import risk.util.Log;

public class KeywordMng {
	
	DateUtil du = new DateUtil();
	StringBuffer sb = new StringBuffer();
	DBconn  conn  = null;
    Statement stmt = null;
    ResultSet rs = null;
    String key = null;
    
    String baseURL = "";
	String baseTarget = "";
	String imagesURL = "";
    
    int selectedXP = 0;
	int selectedYP = 0;
	int selectedZP = 0;
    
	private int k_seq;
	private int k_xp;
	private int k_yp;
	private int k_zp;
	private int k_type;
	private int k_pos;
	
	public void setImagesURL(String imagesURL) {
		this.imagesURL = imagesURL;
	}
	
	public void setBaseURL(String url) {
		baseURL = url;
	}
	
	public void setBaseTarget(String target) {
		baseTarget = target;
	}
	
	public void setSelected(int xp, int yp, int zp){
		selectedXP = xp;
		selectedYP = yp;
		selectedZP = zp;
	}
	
	private static KeywordMng instance = new KeywordMng();
	
	public static KeywordMng getInstance() {
		return instance;
	}
	
	//사이트 그룹 가져오기 
    public ArrayList getSgSeq(String sg_seqs) throws ClassNotFoundException, Exception {
    	
        sb = new StringBuffer();
        ArrayList result = new ArrayList();
        String[] k_value = null;
        
        if(sg_seqs == null || sg_seqs.trim().equals("")){
        	sg_seqs = "0";
        }
        
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();

       	 	sb.append("SELECT A.SG_SEQ																	\n");
       	 	sb.append("     , A.SG_NAME																	\n");
       	 	sb.append("     , IF(B.SG_NAME IS NULL , '','checked') AS CHK								\n"); 
       	 	sb.append("  FROM (SELECT SG_SEQ, SG_NAME FROM SITE_GROUP)A									\n");
       	 	sb.append("        LEFT OUTER JOIN 															\n");
       	 	sb.append("       (SELECT SG_SEQ, SG_NAME FROM SITE_GROUP WHERE SG_SEQ IN ("+sg_seqs+"))B	\n");
       	 	sb.append("    ON A.SG_SEQ = B.SG_SEQ														\n");
    
       	 	System.out.println(sb.toString());
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            
            while(rs.next()) {
            		k_value = new String[3];
					k_value[0] = rs.getString("SG_SEQ");
					k_value[1] = rs.getString("SG_NAME");
					k_value[2] = rs.getString("CHK");
					
					result.add(k_value);
					
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
    
    
	
	//대분류명 
    public String[] getKG(String k_xp)  {
    	
        sb = new StringBuffer();
        String[] k_value = new String[2];

        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();

       	 	sb.append(" SELECT K_VALUE, SG_SEQS FROM KEYWORD WHERE K_XP="+k_xp+" AND K_YP=0 AND K_ZP=0");
       	 	System.out.println(sb.toString());
            stmt = conn.createStatement();
                      rs = stmt.executeQuery(sb.toString());
            if (rs.next()) {
					k_value[0] = rs.getString("K_VALUE");
					k_value[1] = rs.getString("SG_SEQS");
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
        return k_value;
    }

	//중분류명
    public String getKG(String k_xp, String k_yp) throws ClassNotFoundException, Exception {
    	
        sb = new StringBuffer();
        String k_value = "";

        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
	       	sb = new StringBuffer();
	       	sb.append(" SELECT K_VALUE FROM KEYWORD WHERE K_XP="+k_xp+" AND K_YP="+k_yp+" AND K_ZP=0");
	       	stmt = conn.createStatement();
	        rs = stmt.executeQuery( sb.toString() );
            if (rs.next()) {
					k_value = rs.getString("K_VALUE");               
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
        return k_value;
    }
    
	//키워드명
    public String getKG(String k_xp, String k_yp, String k_zp) throws ClassNotFoundException, Exception {
    	      
        String k_value = "";

        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	sb = new StringBuffer();
       	 	sb.append(" SELECT K_VALUE FROM KEYWORD WHERE K_XP="+k_xp+" AND K_YP="+k_yp+" AND K_ZP="+k_zp+" AND K_TYPE=2 ORDER BY K_XP,K_YP,K_ZP ASC");
       	 	stmt = conn.createStatement();
       	
            rs = stmt.executeQuery(sb.toString());
            if (rs.next()) {
				k_value = rs.getString("K_VALUE");               
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
        return k_value;
    }
    
	//제외키워드명
    public List getEXKG(String k_xp, String k_yp, String k_zp) throws Exception {
    	
       
        List exkeylist = null;

        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	
       	 	System.out.println("\n\n k_xp : "+k_xp);
       	 	System.out.println(" k_yp : "+k_yp);
       	 	System.out.println(" k_yp : "+k_zp);
       	 	sb = new StringBuffer();
       	 	sb.append(" SELECT K_TYPE, K_VALUE FROM KEYWORD \n");
       	 	sb.append(" WHERE K_XP="+k_xp+" AND K_YP="+k_yp+" AND K_ZP="+k_zp+" AND K_TYPE>10 ORDER BY K_TYPE ASC");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            
            exkeylist = new ArrayList();
            if(rs != null) {
	            while(rs.next()) {
	            	KeywordBean keybean = new KeywordBean();
	            	keybean.setKGtype( rs.getString("K_TYPE") );
	            	keybean.setKGvalue( rs.getString("K_VALUE") );
	            	
	            	exkeylist.add(keybean);
	            }
            } else {
            	return null;
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
        return exkeylist;
    }
    
    //키워드 정보를 가져온다.
    public ArrayList getKeywordInfo(String k_xp, String k_yp, String k_type) throws Exception {
    	
       
    	ArrayList keywordList = new ArrayList();

        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 
       	 	sb = new StringBuffer();
       	 	if(k_type.equals("1")&&k_xp.equals("")){
	       	 	sb.append(" SELECT K_XP, K_YP, K_ZP ,K_TYPE, K_VALUE FROM KEYWORD \n");
	       	 	sb.append(" WHERE K_YP = 0 AND K_TYPE="+k_type+" ORDER BY K_TYPE ASC");
       	 	}else if(k_type.equals("1")&&!k_xp.equals("")){
	       	 	sb.append(" SELECT K_XP, K_YP, K_ZP ,K_TYPE, K_VALUE FROM KEYWORD \n");
	       	 	sb.append(" WHERE K_XP="+k_xp+" AND K_YP<>0 AND K_TYPE="+k_type+" ORDER BY K_TYPE ASC");
       	 	}else if(k_type.equals("2")){
	       	 	sb.append(" SELECT K_XP, K_YP, K_ZP ,K_TYPE, K_VALUE FROM KEYWORD \n");
	       	 	sb.append(" WHERE K_XP="+k_xp+" AND K_YP="+k_yp+" AND K_TYPE="+k_type+" ORDER BY K_TYPE ASC");
       	 	}
       	 	stmt = conn.createStatement();
       	 	System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            
           
            while(rs.next()) {
            	KeywordBean keybean = new KeywordBean();
            	keybean.setKGxp(rs.getString("K_XP") );
            	keybean.setKGyp(rs.getString("K_YP") );
            	keybean.setKGzp(rs.getString("K_ZP") );
            	keybean.setKGtype( rs.getString("K_TYPE") );
            	keybean.setKGvalue( rs.getString("K_VALUE") );
            	
            	keywordList.add(keybean);
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
        return keywordList;
    }
    
    
    
	//모든 키워드의 HTML을 리턴
    public String getAllKGHtml() throws Exception {
    	
        StringBuffer returnValue = new StringBuffer();
    	
    	DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        int k_xp = 0;
        int k_yp = 0;
        int k_zp = 0;


        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	sb = new StringBuffer();
       	 	
       	 	sb.append("   	 SELECT A3.K_XP, A3.K_YP, A3.K_ZP, A1.K_VALUE, A2.K_VALUE, A3.K_VALUE  \n");
       	 	//sb.append("	     		dbo.get_sumkey(A3.K_XP, A3.K_YP, A3.K_ZP, 11) AS EXKEY                       \n");
    		sb.append("    	 FROM              										                                                          \n");
    		sb.append(" (                           													     										  \n"); 
    		sb.append("		 SELECT K_XP, K_VALUE													                                  \n");
    		sb.append("  	 FROM KEYWORD													                                              \n");
    		sb.append("  			WHERE K_YP=0 AND K_ZP=0 AND K_TYPE=1 AND K_USEYN='Y'							                  \n");
    		sb.append(" 	  ) A1,  																	                                              \n");
    		sb.append(" (                  																											   \n");
    		sb.append(" 	SELECT K_XP, K_YP, K_VALUE            																 \n");
    		sb.append(" 		FROM KEYWORD                    																		 \n");
    		sb.append("  WHERE K_YP>0 AND K_ZP=0 AND K_TYPE=1  AND K_USEYN='Y'														\n");
    		sb.append(") A2,            																										   \n");
    		sb.append("(                   																										     \n");
    		sb.append("SELECT K_XP, K_YP, K_ZP, K_TYPE, K_VALUE														\n");
    		sb.append("FROM KEYWORD																									\n");
    		sb.append("WHERE K_YP>0 AND K_ZP>0 AND K_TYPE=2	AND K_USEYN='Y'															\n");
    		sb.append(") A3																																\n");
    		sb.append("WHERE A1.K_XP=A2.K_XP AND A1.K_XP=A3.K_XP AND A2.K_YP=A3.K_YP						\n");
    		sb.append("ORDER BY A3.K_XP, A3.K_YP, A3.K_ZP, A3.K_VALUE														\n");

    		Log.debug(sb.toString());
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sb.toString());

            
            returnValue.append("<table border='1'>		\n");
            returnValue.append("	<tr>		\n");
            returnValue.append("		<td bgcolor='CCCCCC'><strong>대분류</strong></td>		\n");
            returnValue.append("		<td bgcolor='CCCCCC'><strong>소분류</strong></td>		\n");
            returnValue.append("		<td bgcolor='CCCCCC'><strong>키워드</strong></td>		\n");
            //returnValue.append("		<td bgcolor='CCCCCC'><strong>제외단어</strong></td>		\n");
            returnValue.append("	</tr>		\n");
            
            while(rs.next())
            {
            	
                returnValue.append("<table border='1'>		\n");
                returnValue.append("	<tr>		\n");
                
                if( k_xp == rs.getInt("K_XP") )
                returnValue.append("		<td></td>		\n");
                else
                returnValue.append("		<td>"+rs.getString(4)+"</td>		\n");
                
                if( k_xp == rs.getInt("K_XP") && k_yp == rs.getInt("K_YP") )
                returnValue.append("		<td></td>		\n");
                else
                returnValue.append("		<td>"+rs.getString(5)+"</td>		\n");

                if( k_xp == rs.getInt("K_XP") && k_yp == rs.getInt("K_YP") && k_zp == rs.getInt("K_ZP") )
                returnValue.append("		<td bgcolor='CCCCCC'></td>		\n");
                else
                returnValue.append("		<td>"+rs.getString(6)+"</td>		\n");
                
               // returnValue.append("		<td>"+rs.getString("EXKEY")+"</td>		\n");
                returnValue.append("	</tr>		\n");
                
                k_xp = rs.getInt("K_XP");
                k_yp = rs.getInt("K_YP");
                k_zp = rs.getInt("K_ZP");
            }
            
            returnValue.append("</table>		\n");

            
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }

        return returnValue.toString();
    }  
    
    //상위 키워드그룹 추가 
	public void insert(String val, String m_seq) throws SQLException, ClassNotFoundException, Exception {
		
		try {
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuffer();
			sb.append("SELECT MAX(K_XP) MAX_XP FROM KEYWORD ");			
			rs = stmt.executeQuery(sb.toString());
			if( rs.next() ) {
				k_xp = rs.getInt("MAX_XP");
			} else {
				k_xp = 0;
			}
			
			sb = new StringBuffer();
			sb.append("SELECT MAX(K_POS) MAX_POS FROM KEYWORD WHERE K_YP=0 AND K_ZP=0 ");	
			rs = stmt.executeQuery(sb.toString());
			if( rs.next() ) {
				k_pos = rs.getInt("MAX_POS");
			} else {
				k_pos = 0;
			}
			
			k_xp++;
			k_pos++;
			
			//대그룹만 SG_SEQ넣기~(기본은 전부다~)
			sb = new StringBuffer();
			sb.append("SELECT SG_SEQ FROM SITE_GROUP");	
			rs = stmt.executeQuery(sb.toString());
			String sg_seqs = "";
			while(rs.next()){
				if(sg_seqs.equals("")){
					sg_seqs = rs.getString("SG_SEQ");
				}else{
					sg_seqs += "," + rs.getString("SG_SEQ");
				}
			}
			
			sb = new StringBuffer();
			sb.append("INSERT INTO KEYWORD(K_XP, K_YP, K_ZP, K_TYPE, K_POS, K_OP, K_VALUE, K_REGDATE, SG_SEQS) VALUES("+k_xp+",0,0,1,"+k_pos+",0,'"+val+"','"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"', '"+sg_seqs+"')");
									
			Log.debug(sb.toString()+"  상위 키워드그룹 추가 :["+m_seq+"]");
			stmt.execute(sb.toString());
			
			rs.close();
			sb = new StringBuffer();
			sb.append("SELECT MAX(K_SEQ)AS PKEY FROM KEYWORD");
									
			Log.debug(sb.toString()+"  상위 키워드그룹 추가 :["+m_seq+"]");
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				key = rs.getString("PKEY");
			}
			
			//로그 관련
			LogMgr logMgr = new LogMgr();
			LogBean logBean = new LogBean();		
			logBean.setKey(key);
			logBean.setL_kinds(LogConstants.keywordKindsVal);
			logBean.setL_type(LogConstants.insertTypeVal);
			logBean.setL_ip("");
			logBean.setM_seq(m_seq);
			
			//로그 저장
			logMgr.insertLog(logBean);
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
	}
	
	//키워드그룹 추가
    public void insert( int xp, String val, String m_seq )
    throws ClassNotFoundException, Exception {
    	DBconn  conn  = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		String nowDate = du.getCurrentDate("yyyyMMdd");
	    String nowTime = du.getCurrentDate("HHmmss");

        try {
        	conn  = new DBconn();
        	conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuffer();
			
			sb.append("INSERT INTO KEYWORD( K_XP, K_YP, K_ZP, K_TYPE, K_POS, K_OP, K_VALUE, K_REGDATE) \n");
			sb.append("   SELECT   K_XP, K_YP + 1, 0, 1, K_POS, 0, '"+val+"','"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"' \n");
			sb.append("   FROM (SELECT  K_XP, K_YP, K_POS \n");
			sb.append("         FROM KEYWORD \n");
			sb.append("         WHERE K_XP = "+xp+" \n");
			sb.append("         	  \n");
			sb.append("   ORDER BY K_YP DESC LIMIT 1)m \n");
					
			Log.debug(sb.toString()+"  키워드그룹 추가 :["+m_seq+"]");
			stmt.execute( sb.toString() );
			
			
			rs.close();
			sb = new StringBuffer();
			sb.append("SELECT MAX(K_SEQ)AS PKEY FROM KEYWORD");
									
			Log.debug(sb.toString()+"  키워드그룹 추가 :["+m_seq+"]");
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				key = rs.getString("PKEY");
			}
			
			//로그 관련
			LogMgr logMgr = new LogMgr();
			LogBean logBean = new LogBean();		
			logBean.setKey(key);
			logBean.setL_kinds(LogConstants.keywordKindsVal);
			logBean.setL_type(LogConstants.insertTypeVal);
			logBean.setL_ip("");
			logBean.setM_seq(m_seq);
			
			//로그 저장
			logMgr.insertLog(logBean);
			
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
    }
    
  //키워드 추가
    public void insertAnaTypeCode( int xp, int yp, String val, int op, String m_seq , String typeCode)
    throws ClassNotFoundException, Exception {
    	DBconn  conn  = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;

        try {
        	conn  = new DBconn();
        	conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuffer();

			sb.append("INSERT INTO KEYWORD( K_XP, K_YP, K_ZP, K_TYPE, K_POS, K_OP, K_VALUE, K_REGDATE) \n");
			sb.append("   SELECT K_XP, K_YP, K_ZP + 1, 2, K_POS, "+op+", '"+val+"','"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"' \n");
			sb.append("   FROM (SELECT  K_XP, K_YP, K_ZP, K_POS \n");
			sb.append("         FROM KEYWORD \n");
			sb.append("         WHERE K_XP = "+xp+" AND K_YP = "+yp+"  \n");
			sb.append("         ORDER BY K_ZP DESC LIMIT 1)m \n");
						
			Log.debug(sb.toString()+"  키워드 추가 :["+m_seq+"]");
			stmt.execute( sb.toString() );
			
			sb = new StringBuffer();
			sb.append("SELECT MAX(K_SEQ)AS PKEY FROM KEYWORD");
									
			Log.debug(sb.toString()+"  키워드 추가 :["+m_seq+"]");
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				key = rs.getString("PKEY");
			}
			
			String zp = "";
			sb = new StringBuffer();
			sb.append("SELECT K_ZP FROM KEYWORD WHERE K_SEQ = "+key+" ");
			rs = stmt.executeQuery(sb.toString());
			if(rs.next()){
				zp = rs.getString("K_ZP");
			}
			
			String tmp[] = typeCode.split("/");
			String tmp2[] = new String[2]; 
			if(!zp.equals("")){
				
				if(tmp.length > 0){
					for(int i=0; i < tmp.length; i++){
						tmp2 = tmp[i].split("@");
						sb = new StringBuffer();
						sb.append("INSERT INTO KEYWORD_MAP (K_XP, K_YP, K_ZP, IC_TYPE, IC_CODE) VALUES ("+xp+", "+yp+", "+zp+", "+tmp2[0]+", "+tmp2[1]+"); \n");
						stmt.executeUpdate(sb.toString());
					}	
				}
			}
			
			
			//로그 관련
			LogMgr logMgr = new LogMgr();
			LogBean logBean = new LogBean();		
			logBean.setKey(key);
			logBean.setL_kinds(LogConstants.keywordKindsVal);
			logBean.setL_type(LogConstants.insertTypeVal);
			logBean.setL_ip("");
			logBean.setM_seq(m_seq);
			
			//로그 저장
			logMgr.insertLog(logBean);
			
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
    } 
    
	//키워드 추가
    public void insert( int xp, int yp, String val, int op, String m_seq)
    throws ClassNotFoundException, Exception {
    	DBconn  conn  = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;

        try {
        	conn  = new DBconn();
        	conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuffer();

			sb.append("INSERT INTO KEYWORD( K_XP, K_YP, K_ZP, K_TYPE, K_POS, K_OP, K_VALUE, K_REGDATE) \n");
			sb.append("   SELECT K_XP, K_YP, K_ZP + 1, 2, K_POS, "+op+", '"+val+"','"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"' \n");
			sb.append("   FROM (SELECT  K_XP, K_YP, K_ZP, K_POS \n");
			sb.append("         FROM KEYWORD \n");
			sb.append("         WHERE K_XP = "+xp+" AND K_YP = "+yp+"  \n");
			sb.append("         ORDER BY K_ZP DESC LIMIT 1)m \n");
						
			Log.debug(sb.toString()+"  키워드 추가 :["+m_seq+"]");
			stmt.execute( sb.toString() );
			
			sb = new StringBuffer();
			sb.append("SELECT MAX(K_SEQ)AS PKEY FROM KEYWORD");
									
			Log.debug(sb.toString()+"  키워드 추가 :["+m_seq+"]");
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				key = rs.getString("PKEY");
			}
			
			//로그 관련
			LogMgr logMgr = new LogMgr();
			LogBean logBean = new LogBean();		
			logBean.setKey(key);
			logBean.setL_kinds(LogConstants.keywordKindsVal);
			logBean.setL_type(LogConstants.insertTypeVal);
			logBean.setL_ip("");
			logBean.setM_seq(m_seq);
			
			//로그 저장
			logMgr.insertLog(logBean);
			
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
    } 

	//제외키워드 추가
    public void insert( int xp, int yp, int zp, int op, String val, String m_seq )
    throws ClassNotFoundException, Exception {
    	
		int Ktype = 11;
		String k_pos = "";

        try {
        	conn  = new DBconn();
        	conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuffer();
			
			sb.append("SELECT MAX(K_TYPE)+1 AS NEXT_TYPE \n");
			sb.append("FROM KEYWORD \n");
			sb.append("WHERE K_XP="+xp+" AND K_YP="+yp+" AND K_ZP="+zp+" AND K_TYPE>10 \n");
			Log.debug(sb.toString());
			rs = stmt.executeQuery( sb.toString() );			
			if( rs.next() ) {
				Ktype = rs.getInt("NEXT_TYPE");
			}

			if( Ktype < 11 ) Ktype = 11;
			
			// k_pos 검색
			sb = new StringBuffer();			
			sb.append("SELECT MAX(K_POS) AS K_POS \n");
			sb.append("FROM KEYWORD \n");
			sb.append("WHERE K_XP="+xp+" \n");
			Log.debug(sb.toString());
			rs = stmt.executeQuery( sb.toString() );			
			if( rs.next() ) {
				k_pos = rs.getString("K_POS");
			}
						
			sb = new StringBuffer();			
			sb.append("INSERT INTO KEYWORD( K_XP, K_YP, K_ZP, K_TYPE, K_POS, K_OP, K_VALUE, K_REGDATE) \n");
			sb.append("VALUES("+xp+", "+yp+", "+zp+", "+Ktype+", "+k_pos+", "+op+", '"+val+"','"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"') \n");
			Log.debug(sb.toString()+"  제외 키워드 추가 :["+m_seq+"]");
			rs.close();
			stmt.execute( sb.toString() );
			
			rs.close();
			sb = new StringBuffer();
			sb.append("SELECT MAX(K_SEQ) AS PKEY FROM KEYWORD");
									
			Log.debug(sb.toString()+"  키워드 추가 :["+m_seq+"]");
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				key = rs.getString("PKEY");
			}
			
			//로그 관련
			LogMgr logMgr = new LogMgr();
			LogBean logBean = new LogBean();		
			logBean.setKey(key);
			logBean.setL_kinds(LogConstants.keywordKindsVal);
			logBean.setL_type(LogConstants.insertTypeVal);
			logBean.setL_ip("");
			logBean.setM_seq(m_seq);
			
			//로그 저장
			logMgr.insertLog(logBean);
	
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
    } 
    
    
	//대분류명 수정
	public void update( int xp, String val, String m_seq ) throws SQLException, ClassNotFoundException, Exception {
	
		try {
			
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuffer();	
			sb.append(" UPDATE KEYWORD SET K_VALUE='"+val+"' WHERE K_XP="+xp+" AND K_YP=0 AND K_ZP=0 ");			
			Log.debug(sb.toString()+"   대분류명 수정 :["+m_seq+"]");
			stmt.executeUpdate(sb.toString());
			
			rs.close();
			sb = new StringBuffer();
			sb.append("SELECT K_SEQ AS PKEY FROM KEYWORD WHERE K_XP="+xp+" AND K_YP=0 AND K_ZP=0");
									
			Log.debug(sb.toString()+"  대분류명 수정 :["+m_seq+"]");
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				key = rs.getString("PKEY");
			}
			
			//로그 관련
			LogMgr logMgr = new LogMgr();
			LogBean logBean = new LogBean();		
			logBean.setKey(key);
			logBean.setL_kinds(LogConstants.keywordKindsVal);
			logBean.setL_type(LogConstants.updateTypeVal);
			logBean.setL_ip("");
			logBean.setM_seq(m_seq);
			
			//로그 저장
			logMgr.insertLog(logBean);
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
	}
	
	//소분류명 수정
	public void update( int xp, int yp, String val, String m_seq ) throws SQLException, ClassNotFoundException, Exception {
		DBconn  conn  = null;     //DB 연결을 위한 Object 선언
		Statement stmt  = null;
		try {
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuffer();
			sb.append("UPDATE KEYWORD SET K_VALUE='"+val+"' WHERE K_XP="+xp+" AND K_YP="+yp+" AND K_ZP=0 ");
			Log.debug(sb.toString()+"   소분류명 수정 :["+m_seq+"]");
			stmt.executeUpdate(sb.toString());
			
			rs.close();
			sb = new StringBuffer();
			sb.append("SELECT K_SEQ AS PKEY FROM KEYWORD WHERE K_XP="+xp+" AND K_YP="+yp+" AND K_ZP=0 ");
									
			Log.debug(sb.toString()+"  소분류명 수정 :["+m_seq+"]");
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				key = rs.getString("PKEY");
			}
			
			//로그 관련
			LogMgr logMgr = new LogMgr();
			LogBean logBean = new LogBean();		
			logBean.setKey(key);
			logBean.setL_kinds(LogConstants.keywordKindsVal);
			logBean.setL_type(LogConstants.updateTypeVal);
			logBean.setL_ip("");
			logBean.setM_seq(m_seq);
			
			//로그 저장
			logMgr.insertLog(logBean);
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
	}
	
	//제외키워드  삭제
	public void delete( int xp, int yp, int zp, String type, String m_seq ) throws SQLException, ClassNotFoundException, Exception {
		
		try {
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();		
			
			sb = new StringBuffer();
			sb.append(" DELETE FROM KEYWORD WHERE K_XP="+xp+" AND K_YP="+yp+" AND K_ZP="+zp+" AND K_TYPE IN ("+type+") ");
			Log.debug(sb.toString()+"   제외키워드  삭제 :["+m_seq+"]");
			stmt.executeUpdate(sb.toString());
			
			
			rs.close();
			sb = new StringBuffer();
			sb.append("SELECT K_SEQ AS PKEY FROM KEYWORD WHERE K_XP="+xp+" AND K_YP="+yp+" AND K_ZP="+zp+" AND K_TYPE IN ("+type+") ");
									
			Log.debug(sb.toString()+"  제외키워드  삭제 :["+m_seq+"]");
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				key = rs.getString("PKEY");
			}
			
			//로그 관련
			LogMgr logMgr = new LogMgr();
			LogBean logBean = new LogBean();		
			logBean.setKey(key);
			logBean.setL_kinds(LogConstants.keywordKindsVal);
			logBean.setL_type(LogConstants.deleteTypeVal);
			logBean.setL_ip("");
			logBean.setM_seq(m_seq);
			
			//로그 저장
			logMgr.insertLog(logBean);
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
	}
	
	//키워드 삭제
	public void delete( int xp, int yp, int zp, String m_seq ) throws SQLException, ClassNotFoundException, Exception {
		
		try {
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
						
			sb = new StringBuffer();
			sb.append(" DELETE FROM KEYWORD WHERE K_XP="+xp+" AND K_YP="+yp+" AND K_ZP="+zp);
			Log.debug(sb.toString()+"   키워드  삭제 :["+m_seq+"]");
			stmt.execute(sb.toString());
			
			sb = new StringBuffer();
			sb.append(" DELETE FROM IDX WHERE K_XP="+xp+" AND K_YP="+yp+" AND K_ZP="+zp);			
			Log.debug(sb.toString()+"   키워드  삭제 :["+m_seq+"]");
			stmt.execute(sb.toString());
			
			rs.close();
			sb = new StringBuffer();
			sb.append("SELECT K_SEQ AS PKEY FROM KEYWORD WHERE K_XP="+xp+" AND K_YP="+yp+" AND K_ZP="+zp+" AND K_TYPE=2 ");
									
			Log.debug(sb.toString()+"  키워드 추가 :["+m_seq+"]");
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				key = rs.getString("PKEY");
			}
			
			//로그 관련
			LogMgr logMgr = new LogMgr();
			LogBean logBean = new LogBean();		
			logBean.setKey(key);
			logBean.setL_kinds(LogConstants.keywordKindsVal);
			logBean.setL_type(LogConstants.deleteTypeVal);
			logBean.setL_ip("");
			logBean.setM_seq(m_seq);
			
			//로그 저장
			logMgr.insertLog(logBean);
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}            
        }
	}
	
	//키워드그룹 삭제
	public void delete( int xp, int yp, String m_seq ) throws SQLException, ClassNotFoundException, Exception {
		
		try {
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();			
			
			sb = new StringBuffer();
			sb.append(" DELETE FROM KEYWORD WHERE K_XP="+xp+" AND K_YP="+yp);			
			Log.debug(sb.toString()+"   키워드그룹  삭제 :["+m_seq+"]");
			stmt.execute(sb.toString());
			
			sb = new StringBuffer();
			sb.append(" DELETE FROM IDX WHERE K_XP="+xp+" AND K_YP="+yp);			
			Log.debug(sb.toString()+"   키워드그룹  삭제 :["+m_seq+"]");
			stmt.execute(sb.toString());
			
			rs.close();
			sb = new StringBuffer();
			sb.append("SELECT K_SEQ AS PKEY FROM KEYWORD WHERE K_XP="+xp+" AND K_YP="+yp+" AND K_TYPE=0 ");
									
			Log.debug(sb.toString()+"  키워드그룹  삭제 :["+m_seq+"]");
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				key = rs.getString("PKEY");
			}
			
			//로그 관련
			LogMgr logMgr = new LogMgr();
			LogBean logBean = new LogBean();		
			logBean.setKey(key);
			logBean.setL_kinds(LogConstants.keywordKindsVal);
			logBean.setL_type(LogConstants.deleteTypeVal);
			logBean.setL_ip("");
			logBean.setM_seq(m_seq);
			
			//로그 저장
			logMgr.insertLog(logBean);
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,sb.toString());
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
	}
	
	//상위키워드그룹 삭제
	public void delete( int xp, String m_seq ) throws SQLException, ClassNotFoundException, Exception {

		try {
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();			
			
			sb = new StringBuffer();
			sb.append(" DELETE FROM KEYWORD WHERE K_XP="+xp) ;			
			Log.debug(sb.toString()+"   상위 키워드그룹  삭제 :["+m_seq+"]");
			stmt.execute(sb.toString());
			
			sb = new StringBuffer();
			sb.append(" DELETE FROM IDX WHERE K_XP="+xp) ;
			Log.debug(sb.toString()+"   상위 키워드그룹  삭제 :["+m_seq+"]");			
			stmt.execute(sb.toString());
			
			rs.close();
			sb = new StringBuffer();
			sb.append("SELECT K_SEQ AS PKEY FROM KEYWORD WHERE K_XP="+xp+" AND K_TYPE=0 ");
									
			Log.debug(sb.toString()+"  상위 키워드그룹  삭제 :["+m_seq+"]");
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				key = rs.getString("PKEY");
			}
			
			//로그 관련
			LogMgr logMgr = new LogMgr();
			LogBean logBean = new LogBean();		
			logBean.setKey(key);
			logBean.setL_kinds(LogConstants.keywordKindsVal);
			logBean.setL_type(LogConstants.deleteTypeVal);
			logBean.setL_ip("");
			logBean.setM_seq(m_seq);
			
			//로그 저장
			logMgr.insertLog(logBean);
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
	}
	
	
	//키워드 on/off	
    public int keywordOnOff( int xp, int yp, int zp, int mode) {
    	DBconn  conn  = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		int result = 0;
		
        try {
        	conn  = new DBconn();
        	conn.getDBCPConnection();        	
			sb = new StringBuffer();
			
			System.out.println("mode:"+mode+"/xp:"+xp+"/yp:"+yp+"/zp"+zp);
			
			if(mode == 1){
				sb.append("UPDATE KEYWORD SET K_USEYN ='Y' \n");			
				sb.append(" WHERE K_XP = ? AND K_YP = ? AND K_ZP = ? \n");
			}else if(mode == 2){
				sb.append("UPDATE KEYWORD SET K_USEYN ='N' \n");			
				sb.append(" WHERE K_XP = ? AND K_YP = ? AND K_ZP = ? \n");
			}
			System.out.println(sb.toString());
			
			pstmt = conn.createPStatement(sb.toString());					
			int idx = 0;
			pstmt.clearParameters();
			pstmt.setInt(++idx, xp);
			pstmt.setInt(++idx, yp);
			pstmt.setInt(++idx, zp);
			result = pstmt.executeUpdate();
									
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return result;
    }

	//상위키워드그룹 위로 이동
	public void moveUP( int xp, String m_name ) throws SQLException, ClassNotFoundException, Exception {
	   	DBconn  conn  = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		String pos = null;
		String tgxp = null;
		String tgpos = null;
		
        try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
				
			rs = stmt.executeQuery( "SELECT K_POS FROM KEYWORD WHERE K_XP="+xp+" AND K_YP=0 AND K_ZP=0 " );
			if (rs.next()) {
				pos = rs.getString("K_POS");				
				sb.append("SELECT K_XP, K_POS \n");
				sb.append("FROM (SELECT K_POS, K_XP  \n");
				sb.append("        FROM KEYWORD \n");
				sb.append("        WHERE K_YP = 0 AND K_ZP = 0 AND K_POS < "+pos+" AND K_USEYN='Y' \n");
				sb.append("ORDER BY K_POS DESC LIMIT 2)M \n");
				sb.append("LIMIT 1  \n");															
				Log.debug(sb.toString()+"   상위 키워드그룹  위로이동 :["+m_name+"]");
				rs = stmt.executeQuery( sb.toString() );
				if (rs.next()) {
					tgxp = rs.getString("K_XP");
					tgpos = rs.getString("K_POS");
					stmt.executeUpdate( "UPDATE KEYWORD SET K_POS="+pos+" WHERE K_XP="+tgxp);
					stmt.executeUpdate( "UPDATE KEYWORD SET K_POS="+tgpos+" WHERE K_XP="+xp );
				}
			}			
			
       } catch(SQLException ex) {
           System.out.println("SQLException"+ex);
       } finally {
       	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		   if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
           if (conn != null) try { conn.close(); } catch(SQLException ex) {}
       }
	}
	
	//상위키워드그룹 아래로 이동
	public void moveDOWN( int xp, String m_name ) throws SQLException, ClassNotFoundException, Exception {
	  	String pos = "0";
		String tgxp = "0";
		String tgpos = "0";
		
        try {
	       	conn  = new DBconn();
	       	conn.getDBCPConnection();
			stmt = conn.createStatement();			
			rs = stmt.executeQuery( "SELECT K_POS FROM KEYWORD WHERE K_XP="+xp+" AND K_YP=0 AND K_ZP=0 " );
			if (rs.next()) {
				pos = rs.getString("K_POS");
			sb = new StringBuffer();
			sb.append("SELECT K_XP, K_POS \n");
			sb.append("FROM (SELECT K_POS, K_XP \n");
			sb.append("            FROM KEYWORD \n");
			sb.append("           WHERE K_YP = 0 AND K_ZP = 0 AND K_POS > "+pos+" \n");
			sb.append("        ORDER BY K_POS ASC LIMIT 2 )m \n");
			sb.append("LIMIT 1 \n");
							
			Log.debug(sb.toString()+"   상위 키워드그룹  아래로이동 :["+m_name+"]");
			rs = stmt.executeQuery( sb.toString() );
			if (rs.next()) {
				tgxp = rs.getString("K_XP");
				tgpos = rs.getString("K_POS");
							
				stmt.executeUpdate( "UPDATE KEYWORD SET K_POS="+pos+" WHERE K_XP="+tgxp);
				stmt.executeUpdate( "UPDATE KEYWORD SET K_POS="+tgpos+" WHERE K_XP="+xp );						
				}
			}
			
       } catch(SQLException ex) {
           //throw new MemberException("query error", ex);
    	   System.out.println("SQLException"+ex);
       } finally {
       		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
       }
	}
	
	//사이트그룹 저장
	public void setSiteGroup( String xp, String sg_seqs ) throws SQLException, ClassNotFoundException, Exception {
	  	String pos = "0";
		String tgxp = "0";
		String tgpos = "0";
		
        try {
	       	conn  = new DBconn();
	       	conn.getDBCPConnection();
			stmt = conn.createStatement();			
			stmt.executeUpdate("UPDATE KEYWORD SET SG_SEQS = '"+sg_seqs+"' WHERE K_XP = "+xp+" AND K_YP = 0 AND K_ZP = 0" );
			
       } catch(SQLException ex) {
           //throw new MemberException("query error", ex);
    	   System.out.println("SQLException"+ex);
       } finally {
       		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
       }
	}
	
	//  키워드 트리
	public String GetHtml( String psDateFrom,String psDateTo,String MGxp, String showCount ) {
				
		StringBuffer sbhtml = null;
		String frontIcon    = "";
		String trID         = "";
		String spanID       = "";
		String imgID		= "";
		String trStyle      = "";
		String kgName       = "";
		String linkURL      = "";
		String imgAction    = "";
		String frontSpace   = "";
		String spanClass    = "";
		String sCount       = "";
		
        StringBuffer sb = null;

        String MinMtno      = "";
        String MaxMtno      = "";

		int xp=0;
		int yp=0;
		int zp=0;
		int onOffMode = 0;
		String onOffText = "";
		int prevxp = 0;
		int prevyp = 0;
		int prevzp = 0;			

		try {

		    MetaMgr metaMgr = new MetaMgr();
		    metaMgr.getMaxMinNo(psDateFrom, psDateTo);

            MinMtno= metaMgr.msMinNo;
            MaxMtno= metaMgr.msMaxNo;
            
            System.out.println("msMinNo = "+MinMtno);
            System.out.println("msMaxNo = "+MaxMtno);

            conn  = new DBconn();
            conn.getDBCPConnection();
            stmt    = conn.createStatement();

            sb = new StringBuffer();
            if( showCount.equals("false") ){
                
                sb.append(" SELECT K_XP, K_YP, K_ZP,K_OP, K_VALUE, K_OP, K_USEYN  	\n");
                sb.append(" FROM KEYWORD 						\n");
                sb.append(" WHERE K_TYPE < 3 					\n");
                if( MGxp.length() > 0 )
                sb.append("   AND K_XP IN ("+MGxp+") 			\n");               
                sb.append(" ORDER BY K_XP, K_YP, K_ZP 	\n");
            }else {   
            	String tempStr = null;
            	
                if( MGxp.length() > 0 ) tempStr = " AND B1.K_XP IN ("+MGxp+") ";
                else tempStr = "";
                sb.append(" SELECT B1.K_VALUE, B1.K_XP, B1.K_YP, B1.K_ZP, B1.K_USEYN, IFNULL(B2.CNT,0) AS CNT                                \n");
                sb.append(" FROM                                                                                                 \n");
                sb.append(" KEYWORD B1 LEFT OUTER JOIN                                                                           \n");
                sb.append(" (                                                                                                    \n");
                sb.append("     SELECT K_XP, K_YP, K_ZP, COUNT(DISTINCT MD_PSEQ) AS CNT                              \n");
                sb.append("     FROM META A1,                                                                                    \n");
                sb.append("       (                                                                                              \n");
                sb.append("         SELECT DISTINCT K_XP, 0 AS K_YP, 0 AS K_ZP, MD_SEQ                                           \n");
                sb.append("         FROM IDX                                                                                     \n");
                sb.append("         WHERE MD_SEQ BETWEEN  "+MinMtno+" AND "+MaxMtno+"                                                \n");
                sb.append("       ) A2                                                                                           \n");
                sb.append("     WHERE A1.MD_SEQ=A2.MD_SEQ                                                                        \n");
                sb.append("     GROUP BY K_XP                                                                                    \n");
                sb.append("     UNION                                                                                \n");
                sb.append(" 	SELECT K_XP, K_YP, K_ZP, COUNT(DISTINCT MD_PSEQ) AS CNT                              \n");
                sb.append("     FROM META A1,                                                                                    \n");
                sb.append("       (                                                                                              \n");
                sb.append("         SELECT DISTINCT K_XP, K_YP, 0 AS K_ZP, MD_SEQ                                                \n");
                sb.append("         FROM IDX                                                                                     \n");
                sb.append("         WHERE MD_SEQ BETWEEN  "+MinMtno+" AND "+MaxMtno+"                                                \n");
                sb.append("       ) A2                                                                                           \n");
                sb.append("     WHERE A1.MD_SEQ=A2.MD_SEQ                                                                        \n");
                sb.append("     GROUP BY K_XP, K_YP                                                                              \n");
                sb.append(" 	UNION                                                                                \n");
                sb.append(" 	SELECT K_XP, K_YP, K_ZP, COUNT(DISTINCT MD_PSEQ) AS CNT                              \n");
                sb.append("     FROM META A1,                                                                                    \n");
                sb.append("       (                                                                                              \n");
                sb.append("         SELECT DISTINCT K_XP, K_YP, K_ZP, MD_SEQ                                                     \n");
                sb.append("         FROM IDX                                                                                     \n");
                sb.append("         WHERE MD_SEQ BETWEEN  "+MinMtno+" AND "+MaxMtno+"                                                \n");
                sb.append("       ) A2                                                                                           \n");
                sb.append("     WHERE A1.MD_SEQ=A2.MD_SEQ                                                                        \n");
                sb.append("     GROUP BY K_XP, K_YP, K_ZP                                                                        \n");
                sb.append(" 	) B2 ON B1.K_XP=B2.K_XP AND B1.K_YP=B2.K_YP AND B1.K_ZP=B2.K_ZP                              \n");
                sb.append(" 	WHERE B1.K_TYPE<3  "+tempStr+"                              \n");
                sb.append(" ORDER BY B1.K_XP, B1.K_YP, B1.K_ZP ASC                                                     \n");
            }
            
            	

            Log.debug(sb.toString());
            System.out.println(sb.toString());
            rs = stmt.executeQuery( sb.toString() );
          
			spanID = "spanID_"+selectedXP+"_"+selectedYP+"_"+selectedZP;			
			
			sbhtml = new StringBuffer();
			sbhtml.append("<span id='tmpspan' style='display:none' class=''></span>");
			sbhtml.append("<input type=\"hidden\" name=\"kgmenu_id\" value=\""+spanID+"\">");
			sbhtml.append("<input type=\"hidden\" name=\"onoffmode\" value=\"\">");
									
			while(rs.next()){
				
            	kgName = rs.getString("k_value");            	
            	System.out.println();
            	xp = rs.getInt("k_xp");
            	yp = rs.getInt("k_yp");
            	zp = rs.getInt("k_zp");
            	
            	if(rs.getString("K_USEYN").equals("Y")){
            		onOffMode = 2;
            		onOffText = "<img src=\""+imagesURL+"on_btn.gif\" border=\"0\">";
            	}else{
            		onOffMode = 1;
            		onOffText = "<img src=\""+imagesURL+"off_btn.gif\" border=\"0\">";
            	}            	
            	
            	linkURL = baseTarget+".location.href='"+baseURL+"&xp="+xp+"&yp="+yp+"&zp="+zp+"'";
				spanID = "spanID_"+xp+"_"+yp+"_"+zp;
				imgID = "imgID_"+xp+"_"+yp+"_"+zp;
				
            	if (yp==0) {
					frontSpace="";
					frontIcon = "folder01_close.gif";
					if (selectedXP==xp) { trStyle = "display:"; frontIcon = "folder01_open.gif";}
					else  { trStyle = "display:none"; }
					trID = "trID_"+xp;
					imgAction = " toggleme('"+imgID+"','trID_"+xp+"'); ";
					kgName = "<b>" + kgName + "</b>";
				}
			
            	else if (zp==0) {
					frontSpace="&nbsp;&nbsp;";
					frontIcon = "folder02_close.gif";
					if (selectedXP==xp && selectedYP==yp) { trStyle = "display:"; frontIcon = "folder02_open.gif";}
					else  { trStyle = "display:none"; }
					trID = "trID_"+xp+"_"+yp;
					imgAction = " toggleme('"+imgID+"', 'trID_"+xp+"_"+yp+"'); ";
					kgName = "<b>" + kgName + "</b>";
				}			
            	else {
            		
            		if(rs.getString("K_OP").equals("1"))
            		{
            			frontIcon = "icon_a.jpg";
            		}else if(rs.getString("K_OP").equals("2")){
            			frontIcon = "icon_n.jpg";
            		}else if(rs.getString("K_OP").equals("3")){
            			frontIcon = "icon_p.jpg";
            		}else if(rs.getString("K_OP").equals("4")){
            			frontIcon = "icon_o.jpg";
            		}else if(rs.getString("K_OP").equals("5")){
            			frontIcon = "icon_k.gif";
            		}else if(rs.getString("K_OP").equals("6")){
            			frontIcon = "icon_e.jpg";
            		}
            		
            		
					frontSpace="&nbsp;&nbsp;&nbsp;&nbsp;";					
					trStyle = "";
					trID = "";
					imgAction = " ";																			
				}
				
				if (selectedXP==xp && selectedYP==yp && selectedZP==zp) {
					spanClass=" class=\"kgmenu_selected\" ";
				}
				else {
					spanClass=" class=\"kgmenu\" ";
				}
				
				if (prevyp!=0 && yp==0)
				{
					sbhtml.append("</div>\n");
				} 
				else if ( prevxp != 0 && prevxp != xp && prevyp==0 && yp==0)
				{
					sbhtml.append("</div>\n");
				}
				if (prevzp!=0 && zp==0)
				{
					sbhtml.append("  </div>\n");
				} 
				else if ( prevyp !=0 && prevyp != yp && prevzp==0 && zp==0)
				{
					sbhtml.append("  </div>\n");
				}

				if (showCount.equals("true")){
					
					sCount = "<span class=\"kgmenu_cnt\">[" + rs.getString("cnt") + "]</span>";
				}	
				
				if (zp==0) { 
					sbhtml.append("    "+frontSpace+"\n"+
						"      <img id=\""+imgID+"\" class=\"kgmenu_img\" src=\""+imagesURL+frontIcon+"\" onClick=\""+imgAction+"\" border=\"0\" align=absmiddle>"+
						" <span id=\""+spanID+"\" "+spanClass+" onclick=\""+linkURL+"; kg_click(this); "+imgAction+" \" onmouseover=\"kg_over(this);\""+
						"onmouseout=\"kg_out(this);\">" 
						+ kgName + " " + sCount +
						"</span></br>\n");
				}
				else { 
					sbhtml.append("    "+frontSpace+"\n"+
						"      <img id=\""+imgID+"\" class=\"kgmenu_img\" src=\""+imagesURL+frontIcon+"\" onClick=\""+imgAction+"\" border=\"0\" align=absmiddle>"+
						" <span id=\""+spanID+"\" "+spanClass+" onclick=\""+linkURL+"; kg_click(this); "+imgAction+" \" onmouseover=\"kg_over(this);\""+
						"onmouseout=\"kg_out(this);\">");
						
					if (showCount.equals("false")){
						sbhtml.append("<nobr style =\"text-overflow:ellipsis; overflow:hidden; width:110px; \" title=\" " +rs.getString("k_value").replaceAll("\"","")+ " \">");
					}else{ 
						sbhtml.append("<nobr style =\"text-overflow:ellipsis; overflow:hidden; width:70px; \" title=\" " +rs.getString("k_value").replaceAll("\"","")+ " \">");
					}
						
						sbhtml.append(kgName + "</nobr> " + sCount + "</span><a href=\"javascript:keywordOnOff("+xp+","+yp+","+zp+","+onOffMode+");\" style=\"cursor:hand;\"><span class=\"kgmenu_cnt\">"+onOffText+"</span></a></br>\n");					
				}
				
				// block start 
				if (yp==0)
				{
					sbhtml.append("<div id=\""+trID+"\" style=\""+trStyle+"\">\n");
				}
				else if (zp==0)
				{
					sbhtml.append("  <div id=\""+trID+"\" style=\""+trStyle+"\">\n");
				}
				
				prevxp=xp;
				prevyp=yp;
				prevzp=zp;
            }
		
		sbhtml.append("  </div>		\n");
		sbhtml.append("</div>		\n");
		
		
        } catch (SQLException ex) {
            Log.writeExpt(ex);
        } catch (Exception ex) {
            Log.writeExpt(ex);          
        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( conn != null) conn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
		return sbhtml.toString();
	}
	
	public String GetScript() {
		String kgScript = ""+
			"<SCRIPT LANGUAGE=\"JavaScript\">					\n"+
			"<!--												\n"+
			"function toggleme(obj, subo) {						\n"+
			"	var subObj = eval(\"document.all.\"+subo);		\n"+
			"	var targetObj = $('#'+obj).attr('src');			\n"+
			"	var changeObjImg;								\n"+
			"	var i = 0;										\n"+
			"	var subcnt = subObj.length;						\n"+
			"	if (targetObj.indexOf('folder01_close')>0) {	\n"+
			"		if (subObj)	{								\n"+
			"			changeObjImg = '"+imagesURL+"folder01_open.gif'; \n"+
			"			$('#'+obj).attr('src', changeObjImg);	  \n"+
			"			subObj.style.display='';				  \n"+
			"		}											  \n"+
			"	}else if(targetObj.indexOf('folder02_close')>0){										  \n"+
			"		if (subObj)	{								  \n"+
			"			changeObjImg = '"+imagesURL+"folder02_open.gif';  \n"+
			"			$('#'+obj).attr('src', changeObjImg);	  \n"+
			"			subObj.style.display='';			\n"+
			"		}											\n"+
			"	}else if(targetObj.indexOf('folder01_open')>0){										  \n"+
			"		if (subObj)	{								  \n"+
			"			changeObjImg = '"+imagesURL+"folder01_close.gif';  \n"+
			"			$('#'+obj).attr('src', changeObjImg);	  \n"+
			"			subObj.style.display='none';			\n"+
			"		}											\n"+
			"	}else if(targetObj.indexOf('folder02_open')>0){										  \n"+
			"		if (subObj)	{								  \n"+
			"			changeObjImg = '"+imagesURL+"folder02_close.gif';  \n"+
			"			$('#'+obj).attr('src', changeObjImg);	  \n"+
			"			subObj.style.display='none';			\n"+
			"		}											\n"+
			"	}												\n"+
			"}													\n"+
			"function kg_over(obj) {							\n"+
			"	tmpspan.className=obj.className;				\n"+
			"	obj.className='kgmenu_over';					\n"+
			"}													\n"+
			"function kg_out(obj){								\n"+
			"	obj.className=tmpspan.className;				\n"+
			"}													\n"+
			"function kg_click(obj){							\n"+
			"	var prvObj = eval('document.all.'+document.all.kgmenu_id.value);	\n"+
			"	if (prvObj)										\n"+
			"	{												\n"+
			"		prvObj.className = 'kgmenu';				\n"+
			"	}												\n"+
			"	document.all.kgmenu_id.value = obj.id;			\n"+
			"	obj.className='kgmenu_selected';				\n"+
			"	tmpspan.className=obj.className;				\n"+
			"}													\n"+
			"function keywordOnOff(xp,yp,zp,mode)        {    	\n"+
			"	document.leftmenu.xp.value = xp;				\n"+
			"	document.leftmenu.yp.value = yp;				\n"+
			"	document.leftmenu.zp.value = zp;			               \n"+
			"	document.leftmenu.onoffmode.value = mode;                  \n"+
			"	document.leftmenu.target = '';  \n"+
			"	document.leftmenu.action = 'admin_keyword_onoff_prc.jsp';   \n"+
			"	document.leftmenu.submit();							       \n"+
			"}													\n"+			
			"//-->												\n"+
			"</SCRIPT>											\n";		
		return kgScript;
	}
	
	public String GetStyle() {
		StringBuffer sb = new StringBuffer();
		sb.append("																														\n");
		sb.append("<style>																												\n");
		sb.append("																													\n");
		sb.append(".kgmenu { font-size:12px; height:18px; color:#333333; padding:3 3 1 3; cursor:hand; border:1px solid #EAF5F9; }		\n");
		sb.append(".kgmenu_cnt { FONT-SIZE: 10px; COLOR: navy; FONT-FAMILY: \"tahoma\"; padding:0 0 0 3 }								\n");
		sb.append(".kgmenu_selected { font-size:12px; height:18px; color:#7F7F7F; padding:3 3 1 3; border:1px solid #999999; 			\n");
		sb.append("                  background-color:#EAF5F9; cursor:hand;}																				\n");
		sb.append(".kgmenu_over { font-size:12px; height:18px; color:#333333; padding:3 3 1 3; cursor:hand; border:1px solid #999999; }	\n");
		sb.append(".kgmenu_img { cursor:hand; }																							\n");
		sb.append("																												\n");
		sb.append("</style>																												\n");
		return sb.toString();
	}
	
	
	public ArrayList getKeyword(String xp, String yp){
		ArrayList result = new ArrayList();
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("SELECT K_XP, K_YP, K_ZP, K_VALUE FROM KEYWORD WHERE K_XP = "+xp+" AND K_YP = "+yp+" ORDER BY K_XP , K_YP, K_ZP\n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				KeywordBean kb = new KeywordBean();
				kb.setK_xp(rs.getString("K_XP"));
				kb.setK_yp(rs.getString("K_YP"));
				kb.setK_zp(rs.getString("K_ZP"));
				kb.setK_value(rs.getString("K_VALUE"));
				result.add(kb);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if(dbconn != null)dbconn.close();}catch(Exception e){e.printStackTrace();};
			try{if(rs != null)rs.close();}catch(Exception e){e.printStackTrace();};
			try{if(pstmt != null)pstmt.close();}catch(Exception e){e.printStackTrace();};
		}
		return result;
	}
	
}
