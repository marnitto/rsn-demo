package risk.admin.site;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;

public class SiteMng {
	String query;
	
	StringBuffer sb = null;
	
	DateUtil du = new DateUtil();
	//String StrSiteExclude = " AND NOT (S_CODE1=1 AND S_CODE2=6) ";
	
	String StrSiteExclude = "";
	
	
	
	
	// 김태완 임시
	/*
	public List get46List( String key, int sg_seq , int l_seq) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;
		String search_key = null;
		
        search_key = key;
        StringBuffer sql = new StringBuffer();

		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    sql.append(" SELECT A.S_SEQ, A.S_NAME, A.l_seq FROM SITE A WHERE NOT EXISTS (SELECT 1 FROM SG_S_RELATION WHERE S_SEQ=A.S_SEQ)  ");
	    if(!search_key.equals("")){
	    	sql.append(" AND LOWER(S_NAME) LIKE '%"+search_key.toLowerCase()+"%' " );
	    }
	    if(sg_seq != 0){
	    	sql.append(" AND S_CODE1="+sg_seq );
	    }
	    
	    if(l_seq > 0 && l_seq != 99){
	    	sql.append(" AND A.l_seq="+l_seq );
	    }else if (l_seq == 99){
	    	sql.append(" AND A.l_seq > 2");
	    }
	    sql.append(" ORDER BY S_NAME ASC");
	    		
		rs = stmt.executeQuery(sql.toString());	    
	    List list = new java.util.ArrayList();

	    while( rs.next() ) {
	    	SiteBean site = new SiteBean();
	    	site.set_gsn(rs.getInt("S_SEQ"));
	    	site.set_name(rs.getString("S_NAME"));
	    	site.setL_seq(rs.getInt("l_seq"));
	    	list.add(site);
	    }
		dbconn.close();
		rs.close();
		stmt.close();
	    return list;
	}
	*/
	
	
	
	

	public List get46List(String key, int sg_seq, int language, String order){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null; 
	    ResultSet rs = null;	
	    List list = null;
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    System.out.println(key);
		    
		    sb = new StringBuffer();
		    
		    
		    sb.append("SELECT A.S_SEQ   \n");
		    //sb.append("    , IF(DATE_FORMAT(FROM_UNIXTIME(S_REG_TIMESTAMP),'%Y%m%d') >= DATE_FORMAT(DATE_ADD(NOW(), INTERVAL -7 DAY),'%Y%m%d'), CONCAT(A.S_NAME,'(최신)'), A.S_NAME) AS S_NAME	\n");
		    sb.append("    , A.S_NAME	\n");
		    sb.append(" FROM SITE A    \n");
		    sb.append("WHERE NOT EXISTS (SELECT 1 FROM SG_S_RELATION WHERE S_SEQ=A.S_SEQ)    \n");
		    if(!key.equals("")){
		    	sb.append("AND LOWER(S_NAME) LIKE '%"+key.toLowerCase()+"%'    \n");	
		    }
		    if(sg_seq > 0){
		    	sb.append("AND S_CODE1= "+sg_seq+"   \n");	
		    }
		    if(language > 0){
		    	if(language == 1){
		    		sb.append("AND S_LANG= 'KOR'   \n");	
		    	}else if(language == 2){
		    		sb.append("AND S_LANG= 'ENG'   \n");
		    	}	
		    }
		    if(order.equals("kor")){
		    	sb.append("ORDER BY A.S_NAME ASC    \n");
		    }else{
		    	sb.append("ORDER BY A.S_SEQ DESC    \n");
		    }
		    
		    
		    System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
		    
		    list = new java.util.ArrayList();
		    
		    while( rs.next() ) {
		    	SiteBean site = new SiteBean();
		    	site.set_gsn(rs.getInt("S_SEQ"));
		    	site.set_name(rs.getString("S_NAME"));
		    	list.add(site);
		    }
	    } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
	    
		return list;
	}

	
	//SITE A 테이블에서 SG_S_RELATION 테이블을 뺀 리스트에서 키워드에 의해 검색
	/*
	public List get46List( String key, int sg_seq ) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;
		String search_key = null;

//		search_key = new String( key.getBytes("8859_1"), "euc-kr" );
                search_key = key;

		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    query = " SELECT A.S_SEQ, A.S_NAME FROM SITE A WHERE NOT EXISTS (SELECT 1 FROM SG_S_RELATION WHERE S_SEQ=A.S_SEQ)  AND LOWER(S_NAME) LIKE '%"+search_key.toLowerCase()+"%' AND S_CODE1="+sg_seq+" " +
	    		StrSiteExclude +
	    		" ORDER BY S_NAME ASC";
		System.out.println(query+"\n");
	    rs = stmt.executeQuery(query);
	    
	    List list = new java.util.ArrayList();

	    while( rs.next() ) {
	    	SiteBean site = new SiteBean();
	    	site.set_gsn(rs.getInt("S_SEQ"));
	    	site.set_name(rs.getString("S_NAME"));
	    	list.add(site);
	    }
		dbconn.close();
		
//		2007.10.12
		rs.close();
		stmt.close();
		
	    return list;
	}
	*/

	//SITE A 테이블에서 SG_S_RELATION 테이블을 뺀 리스트에서 사이트그룹으로 검색
	/*
	public List get46List( int sg_seq ) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;		
		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    query = " SELECT A.S_SEQ, A.S_NAME FROM SITE A WHERE NOT EXISTS (SELECT 1 FROM SG_S_RELATION WHERE S_SEQ=A.S_SEQ)   AND S_CODE1="+sg_seq+" " +
	    		StrSiteExclude +
	    		" ORDER BY S_SEQ, S_NAME ";
		System.out.println(query+"\n");
	    rs = stmt.executeQuery(query);
	    
	    List list = new java.util.ArrayList();

	    while( rs.next() ) {
	    	SiteBean site = new SiteBean();
	    	site.set_gsn(rs.getInt("S_SEQ"));
	    	site.set_name(rs.getString("S_NAME"));
	    	list.add(site);
	    }
		dbconn.close();
		
//		2007.10.12
		rs.close();
		stmt.close();
		
		
	    return list;
	}	
	*/
	//SITE A 테이블에서 SG_S_RELATION 테이블을 뺀 리스트에서 키워드에 의해 검색
	/*
	public List get46List( String key ) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;
		String search_key = null;

//		search_key = new String( key.getBytes("8859_1"), "euc-kr" );
                search_key = key;

		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    query = " SELECT A.S_SEQ, A.S_NAME FROM SITE A WHERE NOT EXISTS (SELECT 1 FROM SG_S_RELATION WHERE S_SEQ=A.S_SEQ) AND LOWER(S_NAME) LIKE '%"+search_key.toLowerCase()+"%' " +
	    		StrSiteExclude +
	    		" ORDER BY S_NAME ";
		System.out.println(query+"\n");
	    rs = stmt.executeQuery(query);
	    
	    List list = new java.util.ArrayList();

	    while( rs.next() ) {
	    	SiteBean site = new SiteBean();
	    	site.set_gsn(rs.getInt("S_SEQ"));
	    	site.set_name(rs.getString("S_NAME"));
	    	list.add(site);
	    }
		dbconn.close();
		
//		2007.10.12
		rs.close();
		stmt.close();
		
		
	    return list;
	}
	*/
	
	
	
	
	public List getList(String key, int sg_seq, int language){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null; 
	    ResultSet rs = null;	
	    List list = null;
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    System.out.println(key);
		    
		    sb = new StringBuffer();
		    
		    
		    sb.append("SELECT A.S_SEQ										\n");
		    sb.append("     , A.S_NAME 										\n");
		    sb.append("  FROM SG_S_RELATION A								\n");
		    sb.append("     , SITE B										\n");
		    sb.append(" WHERE A.S_SEQ = B.S_SEQ 							\n");
		    if(!key.equals("")){
		    	sb.append("   AND LOWER(A.S_NAME) LIKE '%"+key.toLowerCase()+"%'	\n");
		    }
		    if(sg_seq > 0){
		    	sb.append("   AND A.SG_SEQ="+sg_seq+"							\n");
		    }
		    if(language > 0){
		    	if(language == 1){
		    		sb.append("AND B.S_LANG= 'KOR'   \n");	
		    	}else if(language == 2){
		    		sb.append("AND B.S_LANG= 'ENG'   \n");
		    	}	
		    }
		    sb.append(" ORDER BY S_NAME ASC									\n");
		    
		    
		    System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
		    
		    list = new java.util.ArrayList();
		    
		    while( rs.next() ) {
		    	SiteBean site = new SiteBean();
		    	site.set_gsn(rs.getInt("S_SEQ"));
		    	site.set_name(rs.getString("S_NAME"));
		    	list.add(site);
		    }
	    } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
	    
		return list;
	}
	
	
	
	
	
	
	//SG_S_RELATION 테이블 리스트
	public List getList() throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;		
		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    query = " SELECT S_SEQ,S_NAME FROM SG_S_RELATION ORDER BY S_NAME ";		
	    rs = stmt.executeQuery(query);
	    
	    List list = new java.util.ArrayList();

	    while( rs.next() ) {
	    	SiteBean site = new SiteBean();
	    	site.set_gsn(rs.getInt("S_SEQ"));
	    	site.set_name(rs.getString("S_NAME"));
	    	list.add(site);
	    }
		dbconn.close();
		
//		2007.10.12
		rs.close();
		stmt.close();
		
		
	    return list;
	}

	//SG_S_RELATION 테이블 리스트 검색
	public List getList(String sch, int sg_seq) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;
	    
        dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    query = " SELECT A.S_SEQ, A.S_NAME FROM SG_S_RELATION A WHERE LOWER(S_NAME) LIKE '%"+sch.toLowerCase()+"%' AND SG_SEQ="+sg_seq+" ORDER BY S_NAME ASC";
		
	    rs = stmt.executeQuery(query);
	    
	    List list = new java.util.ArrayList();

	    while( rs.next() ) {
	    	SiteBean site = new SiteBean();
	    	site.set_gsn(rs.getInt("S_SEQ"));
	    	site.set_name(rs.getString("S_NAME"));
	    	list.add(site);
	    }
		dbconn.close();
		
//		2007.10.12
		rs.close();
		stmt.close();
		
	    return list;
	}

	
	//SG_S_RELATION 테이블 리스트 검색
	public List getList(String sch) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;

		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    query = " SELECT A.S_SEQ, A.S_NAME FROM SG_S_RELATION A WHERE LOWER(S_NAME) LIKE '%"+sch.toLowerCase()+"%'ORDER BY S_NAME ASC";
		
	    rs = stmt.executeQuery(query);
	    
	    List list = new java.util.ArrayList();

	    while( rs.next() ) {
	    	SiteBean site = new SiteBean();
	    	site.set_gsn(rs.getInt("S_SEQ"));
	    	site.set_name(rs.getString("S_NAME"));
	    	list.add(site);
	    }
		dbconn.close();
		
//		2007.10.12
		rs.close();
		stmt.close();
		
		
	    return list;
	}
	
	//SG_S_RELATION 테이블 리스트 검색
	public List getSelectList(String sdGsns) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;

		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    query = " SELECT A.S_SEQ, A.S_NAME FROM SG_S_RELATION A WHERE S_SEQ IN ("+sdGsns+") ORDER BY S_NAME ASC";
		System.out.println(query);
	    rs = stmt.executeQuery(query);
	    
	    List list = new java.util.ArrayList();

	    while( rs.next() ) {
	    	SiteBean site = new SiteBean();
	    	site.set_gsn(rs.getInt("S_SEQ"));
	    	site.set_name(rs.getString("S_NAME"));
	    	list.add(site);
	    }
		dbconn.close();
		
//		2007.10.12
		rs.close();
		stmt.close();
		
		
	    return list;
	}
	
	//SG_S_RELATION 테이블  그룹리스트
	public List getList(int sg_seq) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;		
		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    query = " SELECT A.S_SEQ, A.S_NAME FROM SG_S_RELATION WHERE SG_SEQ="+sg_seq+" ORDER BY S_NAME ";
		
	    rs = stmt.executeQuery(query);
	    
	    List list = new java.util.ArrayList();

		while( rs.next() ) {
			SiteBean site = new SiteBean();
			site.set_gsn(rs.getInt("S_SEQ"));
			site.set_name(rs.getString("S_NAME"));
			list.add(site);
		}
		dbconn.close();
		
		//		2007.10.12
		rs.close();
		stmt.close();
		
	    return list;
	}
	
	//SITE_GROUP 그룹리스트
	public List getSGList() throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;		
		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    query = " SELECT SG_SEQ, SG_NAME FROM SITE_GROUP WHERE SG_SEQ!=48";	// 인스타그램 제외
	    rs = stmt.executeQuery(query);
	    
	    List list = new java.util.ArrayList();

	    while( rs.next() ) {
	    	SitegroupBean sitegroup = new SitegroupBean();
	    	sitegroup.set_seq(rs.getInt("SG_SEQ"));
	    	sitegroup.set_name(rs.getString("SG_NAME"));
	    	list.add(sitegroup);
	    }
		dbconn.close();
		
//		2007.10.12
		rs.close();
		stmt.close();
		
	    return list;
	}
	
	//사이트그룹 생성
	public void SGinsert(String sg_name) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    //ResultSet rs;
		String insert_name = null;

//		insert_name = new String( sg_name.getBytes("8859_1"), "euc-kr" );
                insert_name = sg_name;
		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    
	    //2007.10.12 수정자:이경환
	    //query = " INSERT INTO SITE_GROUP VALUES(SEQ_SG_SEQ.NEXTVAL, '"+sg_name+"') ";
	    
	    query = " INSERT INTO SITE_GROUP(SG_NAME,SG_REGDATE) VALUES('"+sg_name+"','"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"') ";
	    
	    
	    stmt.executeUpdate(query);
		dbconn.close();
		
		// 2007.10.12
		stmt.close();

	}
	
	//사이트그룹 변경
	public void SGupdate(int sg_seq, String sg_name) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;

		String insert_name = null;
//		insert_name = new String( sg_name.getBytes("8859_1"), "euc-kr" );
        insert_name = sg_name;

		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    query = " UPDATE SITE_GROUP SET SG_NAME='"+insert_name+"' WHERE SG_SEQ="+sg_seq;
	    stmt.executeUpdate(query);
		dbconn.close();
		
//		 2007.10.12
		stmt.close();
		
	}
	
	//사이트그룹 삭제
	public void SGdelete(int sg_seq) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    query = "DELETE FROM SG_S_RELATION WHERE SG_SEQ="+sg_seq;
	    stmt.executeUpdate(query);
	    
	    query = "DELETE FROM SITE_GROUP WHERE SG_SEQ="+sg_seq;
	    stmt.executeUpdate(query);
		dbconn.close();
		
//		 2007.10.12
		stmt.close();
	}

    //사이트그룹을 가져온다.
    /**
     * @param SGseq
     * @return
     * @throws ClassNotFoundException
     * @throws Exception
     */
    public String getSG(String SGseq)
    throws ClassNotFoundException, Exception {
    	DBconn  conn  = null;
        PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		String SGval = "";

        try {
        	conn  = new DBconn();
        	conn.getDBCPConnection();
			stmt = conn.createStatement();

			sb.append("SELECT SG_NAME FROM SITE_GROUP WHERE SG_SEQ="+SGseq);
            
			pstmt = conn.createPStatement( sb.toString() );
			rs = conn.executeQuery(pstmt);
			if( rs.next() ) {
				SGval = rs.getString("SG_NAME");
            }
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return SGval;
    }
    
    public void insertGSN(String SGseq,String GSN)
    throws ClassNotFoundException, Exception {
    	DBconn  conn  = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();

        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();

       	 	//2007.10.15 수정 : 이경환
       	 	//sb.append(" INSERT INTO SG_S_RELATION SELECT A.S_SEQ, "+SGseq+", S_NAME FROM SITE A WHERE A.S_SEQ IN ("+GSN+") ");
       	 	sb.append(" INSERT INTO SG_S_RELATION(SG_SEQ,S_SEQ,S_NAME) SELECT "+SGseq+", A.S_SEQ,A.S_NAME FROM SITE A WHERE A.S_SEQ IN ("+GSN+") ");
            pstmt = conn.createPStatement(sb.toString());
            conn.executeUpdate(pstmt);

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
            
            
        }
    }
    
    public void deleteGSN(String GSN)
    throws ClassNotFoundException, Exception {
    	DBconn  conn  = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();

        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();

       	 	sb.append(" DELETE FROM SG_S_RELATION WHERE S_SEQ IN ("+GSN+") ");
       	 	
       	 	System.out.println(sb.toString());
            pstmt = conn.createPStatement( sb.toString());
            conn.executeUpdate(pstmt);

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
            
        }
    }
    
	//SITE_GROUP, SG_S_RELATION 테이블 의 데이터를 html 형태로 반환한다.
	public String getSiteHtml( ) {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs = null;
	    StringBuffer sb = null;//new StringBuffer();
	    StringBuffer sbHtml = null;
	    try {
	    	sb = new StringBuffer();
			dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    sb.append(" SELECT A.SG_SEQ, A.SG_NAME, B.S_NAME, B.S_SEQ, C.s_url, C.l_seq  \n");
		    sb.append(" FROM SITE_GROUP A, SG_S_RELATION B, SITE C   \n");
		    sb.append(" WHERE A.SG_SEQ=B.SG_SEQ AND B.s_seq = C.s_seq \n");		   
		    sb.append(" ORDER BY A.SG_SEQ ASC  \n");
		    
		    System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
		    
		    sbHtml = new StringBuffer();
            
            sbHtml.append("<table border='1'>		\n");
            sbHtml.append("	<tr>		\n");
            sbHtml.append("		<td align='center' bgcolor='CCCCCC'><strong>사이트 그룹</strong></td>		\n");
            sbHtml.append("		<td align='center' bgcolor='CCCCCC'><strong>사이트</strong></td>		\n");
            sbHtml.append("		<td align='center' bgcolor='CCCCCC'><strong>언어코드</strong></td>		\n");
            sbHtml.append("		<td align='center' bgcolor='CCCCCC'><strong>URL</strong></td>		\n");
            sbHtml.append("	</tr>		\n");
            
            int tempSgseq = 0;
            
			while( rs.next() ) {
				sbHtml.append("<tr>		\n");
				if( tempSgseq != rs.getInt("SG_SEQ") ) {
					sbHtml.append("	<td align='center'>"+rs.getString("SG_NAME")+"</td>		\n");
				}else {
					sbHtml.append("	<td align='center'>&nbsp;</td>		\n");
				}
				tempSgseq = rs.getInt("SG_SEQ");
    			sbHtml.append("	<td align='center'>"+rs.getString("S_NAME")+"</td>		\n");
    			sbHtml.append("	<td align='center'>"+rs.getString("l_seq")+"</td>		\n");
    			sbHtml.append("	<td align='left'>"+rs.getString("s_url")+"</td>		\n");
    			sbHtml.append("</tr>		\n");
			}
			 sbHtml.append("</table>		\n");
	    } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
            
        }
	    return sbHtml.toString();
	}
	
	//SITE_GROUP, SG_S_RELATION 테이블 의 데이터를 html 형태로 반환한다.
	public String getAllSiteHtml( ) {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs = null;
	    StringBuffer sb = null;//new StringBuffer();
	    StringBuffer sbHtml = null;
	    try {
	    	sb = new StringBuffer();
			dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    sb.append(" SELECT S_CODE1, A.S_SEQ, S_NAME, S_LANG, s_url  \n");
		    sb.append(" FROM SITE A  \n");
		    sb.append(" WHERE NOT EXISTS (SELECT 1 FROM SG_S_RELATION WHERE S_SEQ=A.S_SEQ) ORDER BY S_NAME \n");
		    
		    System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
		    
		    sbHtml = new StringBuffer();
            
            sbHtml.append("<table border='1'>		\n");
            sbHtml.append("	<tr>		\n");
            sbHtml.append("		<td align='center' bgcolor='CCCCCC'><strong>사이트 그룹</strong></td>		\n");
            sbHtml.append("		<td align='center' bgcolor='CCCCCC'><strong>사이트</strong></td>		\n");
            sbHtml.append("		<td align='center' bgcolor='CCCCCC'><strong>언어코드</strong></td>		\n");
            sbHtml.append("		<td align='center' bgcolor='CCCCCC'><strong>URL</strong></td>		\n");
            sbHtml.append("	</tr>		\n");
            
            int tempSgseq = 0;
            String SGNAME = "";
            
			while( rs.next() ) {
				switch( rs.getInt("S_CODE1") )
				{
					case 1: SGNAME = "언론사";     break;
					case 2: SGNAME = "정부/공공";  break;
					case 3: SGNAME = "금융";       break;
					case 4: SGNAME = "NGO";        break;
					case 5: SGNAME = "기업/단체";  break;
					case 6: SGNAME = "기타";       break;
					default : SGNAME = "전체";       break;
				}
				sbHtml.append("<tr>		\n");
				sbHtml.append("	<td align='center'>"+SGNAME+"</td>		\n");
    			sbHtml.append("	<td align='center'>"+rs.getString("S_NAME")+"</td>		\n");
    			sbHtml.append("	<td align='center'>"+rs.getString("S_LANG")+"</td>		\n");
    			sbHtml.append("	<td align='left'>"+rs.getString("s_url")+"</td>		\n");
    			sbHtml.append("</tr>		\n");
			}
			 sbHtml.append("</table>		\n");
	    } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
            
        }
	    return sbHtml.toString();
	}
	
	public ArrayList getSiteGroup(){
		ArrayList result = new ArrayList();
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("SELECT SG_SEQ, SG_NAME, SG_REGDATE\n");
			sb.append("FROM SITE_GROUP\n");
			sb.append("ORDER BY SG_SEQ\n");
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				SiteBean sbean = new SiteBean();
				sbean.setSg_seq(rs.getString("SG_SEQ"));
				sbean.setSg_name(rs.getString("SG_NAME"));
				sbean.setSg_regdate(rs.getString("SG_REGDATE"));
				result.add(sbean);
			}
		}catch(SQLException e){
			Log.writeExpt(e, e.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		return result;
	}	
}
