
package risk.admin.member;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;

public class MemberDao {	
	
	private DBconn dbconn   = null;
	private Statement stmt  = null;
    private ResultSet rs    = null;
    private StringBuffer sb = null;
    List memList = null;
    private MemberBean mBean = new MemberBean();
    DateUtil du = new DateUtil();    
   
    public int totalct = 0;	
		
    public MemberBean schID(String memberId, String memberPass) throws Exception{
    	return getMember( "M_ID", memberId, memberPass );
    }
    
    public MemberBean schID(String memberId) throws Exception{
    	return getMember( "M_ID", memberId);
    }
    
    public MemberBean schName(String memberName) throws Exception{
    	return getMember( "M_NAME", memberName );
    }

	public MemberBean schSeq(String memberSeq) throws Exception{
    	return getMember( "M_SEQ", memberSeq );
    }
		
	public boolean checkId(String id){
		boolean result = false;
		int intCnt = 0;
		try{
			dbconn  = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append("SELECT count(m_id) FROM MEMBER  \n");
			sb.append("WHERE M_ID = '"+id+"'   \n");       
			System.out.println(sb.toString());
			stmt = dbconn.createStatement( );
	        rs = stmt.executeQuery( sb.toString());
	        
	        if(rs.next())
	        	intCnt = rs.getInt(1);
	        	if(intCnt > 0 )
	        		result = true;
		
			} catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
				if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			return result;
	}	
	
	public MemberBean getMember(String SchField, String schval, String memberPass) throws  Exception {   
		MemberBean member = null ;
        try {
        	dbconn  = new DBconn();
        	dbconn.getDBCPConnection();
        	
        	
        	sb = new StringBuffer();        	
       	 	sb.append("SELECT M_SEQ, M_ID, M_PASS, M_NAME, M_DEPT, M_POSITION, \n");
       	 	sb.append("       M_MAIL, M_TEL, M_HP, MG_SEQ , M_REGDATE \n");
       	 	sb.append("FROM MEMBER \n");
       	 	sb.append("WHERE 1=1 \n");
			if( SchField.equals("M_SEQ") )
       	 	sb.append("   AND M_SEQ = '"+schval+"' \n");
       	 	if( SchField.equals("M_ID") )
       	 	sb.append("   AND M_ID = '"+schval+"' \n");
       	 	if( SchField.equals("M_NAME") )
       	 	sb.append("   AND M_NAME = '"+schval+"' \n");
       	 	if( SchField.equals("M_MAIL") )
       	 	sb.append("   AND M_MAIL = '"+schval+"' \n");
       		if( SchField.equals("M_TEL") )
       	 	sb.append("   AND M_TEL = '"+schval+"' \n");
       	
       		System.out.println( sb.toString() );
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            if (rs.next()) {
            	member = new MemberBean();
            	member.setM_seq(rs.getString("M_SEQ"));
            	member.setM_id(rs.getString("M_ID"));
            	member.setM_pass(rs.getString("M_PASS"));
            	member.setM_name(rs.getString("M_NAME"));
            	member.setM_dept(rs.getString("M_DEPT"));
            	member.setM_position(rs.getString("M_POSITION"));
            	member.setM_mail(rs.getString("M_MAIL"));
            	member.setM_tel(rs.getString("M_TEL"));
            	member.setM_hp(rs.getString("M_HP"));
            	member.setMg_seq(rs.getString("MG_SEQ"));				
            	member.setM_regdate(rs.getString("M_REGDATE"));				
            }
          
        } catch(SQLException ex) {
        	ex.printStackTrace();
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        
        System.out.println("-------5-------------");
        return member;
    }
	
	public MemberBean getMember(String SchField, String schval)	throws  Exception {
    	
        try {
        	dbconn  = new DBconn();
       	 	dbconn.getDBCPConnection();
       	 	
       	    sb = new StringBuffer(); 
       	 	sb.append("SELECT M_SEQ, M_ID, M_PASS, M_NAME, M_DEPT, M_POSITION, \n");
       	 	sb.append("       M_MAIL, M_TEL, M_HP, MG_SEQ  \n");
       	 	sb.append("FROM MEMBER \n");
       	 	sb.append("WHERE 1=1 \n");
			if( SchField.equals("M_SEQ") )
       	 	sb.append("   and M_SEQ = '"+schval+"' \n");
       	 	if( SchField.equals("M_ID") )
       	 	sb.append("   and M_ID = '"+schval+"' \n");
       	 	if( SchField.equals("M_NAME") )
       	 	sb.append("   and M_NAME = '"+schval+"' \n");
       	 	if( SchField.equals("M_MAIL") )
       	 	sb.append("   and M_MAIL = '"+schval+"' \n");
       		if( SchField.equals("M_TEL") )
       	 	sb.append("   and M_TEL = '"+schval+"' \n");
       	       	 
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            if (rs.next()) {
            	mBean = new MemberBean();
        		mBean.setM_seq(rs.getString("M_SEQ"));
        		mBean.setM_id(rs.getString("M_ID"));
        		mBean.setM_pass(rs.getString("M_PASS"));
        		mBean.setM_name(rs.getString("M_NAME"));
        		mBean.setM_dept(rs.getString("M_DEPT"));
        		mBean.setM_position(rs.getString("M_POSITION"));
        		mBean.setM_mail(rs.getString("M_MAIL"));
        		mBean.setM_tel(rs.getString("M_TEL"));
        		mBean.setM_hp(rs.getString("M_HP"));
        		mBean.setMg_seq(rs.getString("MG_SEQ"));				
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
        return mBean;
    }
	
    public List getList(String nowpage) throws ClassNotFoundException, Exception {
    	return getList( nowpage, "", "" );
    }

    public List getList(String nowpage, String field, String val) throws ClassNotFoundException, Exception {
    	
    	int Nowpage = Integer.parseInt(nowpage);
    	int StartNo=0;
    	int EndNo=0;
    	StartNo = (Nowpage-1) * 10;
    	EndNo = 10;
    	
    	try {
	        	dbconn  = new DBconn();
	       	 	dbconn.getDBCPConnection();
	       	 	sb = new StringBuffer();
				sb.append("  SELECT COUNT(1) CT \n");
				sb.append("          FROM MEMBER_GROUP MG, \n");
				sb.append("               (SELECT M_SEQ, MG_SEQ \n");
				sb.append("                FROM MEMBER \n");
				sb.append("                WHERE MG_SEQ NOT IN (SELECT MG_SEQ \n");
				sb.append("                                      FROM MEMBER_GROUP \n");
				sb.append("                                      WHERE MG_NAME = 'SYSTEM') \n");
				if( field.equals("m_id") )
				sb.append("                                       AND M_ID LIKE '%"+val+"%' \n");
				if( field.equals("m_name") )
				sb.append("                                       AND M_NAME LIKE '%"+val+"%' \n");
				if( field.equals("m_dept") )
				sb.append("                                       AND M_DEPT LIKE '%"+val+"%' \n");
				if( field.equals("m_mail") )
				sb.append("                                       AND M_MAIL LIKE '%"+val+"%' \n");
				if( field.equals("m_hp") )
				sb.append("                                       AND M_HP LIKE '%"+val+"%' \n");				
				sb.append("                ) M \n");
				sb.append("         WHERE MG.MG_SEQ = M.MG_SEQ \n");
				if( field.equals("mg_name") )
				sb.append("           AND MG_NAME LIKE '%"+val+"%' \n");
	       	 	
				System.out.print( sb.toString() );
	            stmt = dbconn.createStatement();
	            rs = stmt.executeQuery( sb.toString() );
	            if( rs.next() ) {
	            	totalct = rs.getInt("CT");
	            }
	            System.out.println(totalct + "전체 카운터 수 ");
	            
	       	 	sb = new StringBuffer();
				sb.append("  SELECT M.M_SEQ, M.M_ID, M.M_NAME, M.M_DEPT, M.M_MAIL, M.M_HP, MG.MG_NAME,M.MG_SEQ \n");
				sb.append("  FROM MEMBER_GROUP MG, \n");
				sb.append("       (SELECT   M_SEQ, M_ID, M_NAME, M_DEPT, M_MAIL, M_HP, MG_SEQ \n");
				sb.append("        														             \n");
				sb.append("        FROM MEMBER \n");
				sb.append("        WHERE MG_SEQ != (SELECT MG_SEQ \n");
				sb.append("                         FROM MEMBER_GROUP \n");
				sb.append("                         WHERE MG_NAME = 'SYSTEM') \n");
				if( field.equals("m_id") )
				sb.append("                               AND M_ID LIKE '%"+val+"%' \n");
				if( field.equals("m_name") )
				sb.append("                               AND M_NAME LIKE '%"+val+"%' \n");
				if( field.equals("m_dept") )
				sb.append("                               AND M_DEPT LIKE '%"+val+"%' \n");
				if( field.equals("m_mail") )
				sb.append("                               AND M_MAIL LIKE '%"+val+"%' \n");
				if( field.equals("m_hp") )
				sb.append("                               AND M_HP LIKE '%"+val+"%' \n");				
				sb.append("              			) M \n");
				sb.append("  WHERE MG.MG_SEQ = M.MG_SEQ \n");				
				if( field.equals("mg_name") )
				sb.append("        AND MG_NAME LIKE '%"+val+"%' \n"); 
				sb.append("  LIMIT "+StartNo+","+EndNo+" \n"); 
				
	       	 	
				System.out.print( sb.toString() );
	            stmt = dbconn.createStatement();
	            rs = stmt.executeQuery(sb.toString());

	            memList = new ArrayList();
	            while (rs.next()) {
	                MemberBean member = new MemberBean();

					member.setM_seq(rs.getString(1));
					member.setM_id(rs.getString(2));
				    member.setM_name(rs.getString(3));
				    member.setM_dept(rs.getString(4));
					member.setM_mail(rs.getString(5));
				    member.setM_hp(rs.getString(6));				   
					member.setMg_name(rs.getString(7));
					member.setMg_seq(rs.getString(8));

					memList.add(member);
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
		return memList;
    }
    
    
    public void insertMember(MemberBean MBean) throws  Exception {
        
		String Mseq = null;
		System.out.println("######################################");
		//String m_date = du.addDay(du.getCurrentDate("yyyy-MM-dd"),-1).replaceAll("-", "");
		//String m_time = du.getCurrentDate("HHmmss"); 
		
	        try {
        	dbconn  = new DBconn();
        	dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			String	hp = MBean.getM_hp();

			sb = new StringBuffer();
			sb.append("INSERT INTO MEMBER(M_ID,M_PASS,M_NAME,M_DEPT,M_POSITION,M_MAIL,M_TEL,M_HP,MG_SEQ,M_REGDATE)  \n");
			sb.append("VALUES ( \n");
			sb.append("        '"+MBean.getM_id()+"' \n");
			sb.append("        ,'"+MBean.getM_pass()+"' \n");
			sb.append("        ,'"+MBean.getM_name()+"' \n");
			sb.append("        ,'"+MBean.getM_dept()+"' \n");
			sb.append("        ,'"+MBean.getM_position()+"' \n");
			sb.append("        ,'"+MBean.getM_mail()+"' \n");
			sb.append("        ,'"+MBean.getM_tel()+"' \n");
			sb.append("        ,'"+MBean.getM_hp()+"' \n");
			sb.append("        ,'"+MBean.getMg_seq()+"' \n");			
			sb.append("        ,'"+MBean.getM_regdate()+"' \n");		
			sb.append("        ) \n");
			
			System.out.print( sb.toString() );
			stmt = dbconn.createStatement();
            stmt.executeUpdate(sb.toString());
                        
            sb = new StringBuffer();
            sb.append("SELECT M_SEQ FROM MEMBER WHERE M_ID='"+MBean.getM_id()+"' \n");
            
			//System.out.print( sb.toString() );
			stmt = dbconn.createStatement();
			rs =  stmt.executeQuery(sb.toString());
			if( rs.next() ) {
				Mseq = rs.getString("M_SEQ");					
				
	            sb = new StringBuffer();            
	            sb.append("INSERT INTO SETTING \n");
	            sb.append("            ( K_XP, ST_INTERVAL_DAY, MD_TYPE, SG_SEQ, ST_RELOAD_TIME, ST_LIST_CNT, ST_MENU, M_SEQ, ST_REGDATE, SG_SEQ_AL) \n");
	            sb.append("   (SELECT  S.K_XP, S.ST_INTERVAL_DAY, S.MD_TYPE, S.SG_SEQ, \n");
	            sb.append("           S.ST_RELOAD_TIME, S.ST_LIST_CNT, S.ST_MENU, "+Mseq+", '"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"', S.SG_SEQ_AL \n");
	            sb.append("    FROM SETTING S, (SELECT M_SEQ \n");
	            sb.append("                         FROM MEMBER \n");
	            sb.append("                         WHERE M_ID = '**NCS**') M \n");   
	            sb.append("    WHERE S.M_SEQ = M.M_SEQ) ");
	            
	            
				
				stmt = dbconn.createStatement();
	            stmt.executeUpdate(sb.toString());	            
	            sb = null;
	            sb = new StringBuffer();
	            
			}
            
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
    }

    public void updateMember(MemberBean MBean)
    throws ClassNotFoundException, Exception {
    	try {
        	dbconn  = new DBconn();
       	 	dbconn.getDBCPConnection();
       	 	
       	 	sb = new StringBuffer();
			sb.append("UPDATE MEMBER \n");			
			sb.append("    SET 				 \n");
			if( MBean.getM_name().length() > 0 ){
				sb.append("   M_NAME = '"+MBean.getM_name()+"' \n");
				sb.append("   , \n");
			}
			if( MBean.getM_pass().length() > 0 ){
				sb.append("   M_PASS = '"+MBean.getM_pass()+"' \n");
				sb.append("   , \n");
			}
			if( MBean.getM_dept().length() > 0 ){
				sb.append("   M_DEPT = '"+MBean.getM_dept()+"' \n");
				sb.append("   , \n");
			}
			if( MBean.getM_position().length() > 0 ){
				sb.append("   M_POSITION = '"+MBean.getM_position()+"' \n");
				sb.append("   , \n");
			}
			if( MBean.getM_mail().length() > 0 ){
				sb.append("   M_MAIL = '"+MBean.getM_mail()+"' \n");
				sb.append("   , \n");
			}			
			if( MBean.getM_tel().length() > 0 ){
				sb.append("   M_TEL = '"+MBean.getM_tel()+"' \n");
				sb.append("   , \n");
			}
			sb.append("   M_HP = '"+MBean.getM_hp()+"' \n");
			sb.append("   , \n");
			if( MBean.getMg_seq().length() > 0 ){
				sb.append("   MG_SEQ = '"+MBean.getMg_seq()+"' \n");
				sb.append("   , \n");
			}			
			if(MBean.getM_regdate().length() > 0 ){
				sb.append("   M_REGDATE = '"+MBean.getM_regdate()+"' \n");
				sb.append("   \n");
			}	
			sb.append(" WHERE M_SEQ = '"+MBean.getM_seq()+"' \n");

       	 	System.out.print( sb.toString() );
       	 	stmt = dbconn.createStatement( );
            stmt.executeUpdate( sb.toString());

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
    }    

    public void deleteMember(String Mseq)
    throws ClassNotFoundException, Exception {
    	try {
        	dbconn  = new DBconn();
       	 	dbconn.getDBCPConnection();
       	 	
       	 	sb = new StringBuffer();
       	 	sb.append(" DELETE FROM MEMBER WHERE M_SEQ IN ("+Mseq+") ");
			Log.writeExpt( sb.toString() );
            stmt = dbconn.createStatement();
            stmt.executeUpdate( sb.toString());
            
            sb = new StringBuffer();
			sb.append(" DELETE FROM SETTING WHERE M_SEQ IN ("+Mseq+") ");
			Log.writeExpt( sb.toString() );
			stmt = dbconn.createStatement();
	        stmt.executeUpdate( sb.toString());
            
	        sb = new StringBuffer();
            sb.append(" DELETE FROM MEMBER_IP WHERE M_SEQ IN ("+Mseq+") ");
			Log.writeExpt( sb.toString() );
			stmt = dbconn.createStatement();
	        stmt.executeUpdate( sb.toString());

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
    }

    //????????????? ?????´?.
    public List getMGList() throws ClassNotFoundException, Exception {
    	
	        try {
	        	dbconn  = new DBconn();
	       	 	dbconn.getDBCPConnection();
	       	 	sb = new StringBuffer();
				sb.append("SELECT MG_SEQ, MG_XP, MG_SITE, MG_MENU, MG_NAME FROM MEMBER_GROUP WHERE MG_NAME != 'SYSTEM' ");

				System.out.println( sb.toString() );
	            stmt = dbconn.createStatement();
	            rs = stmt.executeQuery(sb.toString());

	            memList = new ArrayList();
	            while (rs.next()) {
	                MemberGroupBean member = new MemberGroupBean();
					member.setMg_seq(rs.getString("MG_SEQ"));
					member.setMg_xp(rs.getString("MG_XP"));
				    member.setMg_site(rs.getString("MG_SITE"));
				    member.setMg_menu(rs.getString("MG_MENU"));
					member.setMg_name(rs.getString("MG_NAME"));

					memList.add(member);
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
		return memList;
    }
}