/**
========================================================
二�������: RSN
�쒕툕 �쒖뒪��: db 怨듯넻紐⑤뱢
�꾨줈洹몃옩 ID : DBConn.class
�꾨줈洹몃옩 紐�: DBConn
�꾨줈洹몃옩媛쒖슂 : �곗씠�곕쿋�댁뒪 而ㅻ꽖���곌껐,�몃옖��뀡 愿�━
               諛�Query �ㅽ뻾
������: �ㅼ꽍以�
������: 2006. 04. 11
========================================================
�섏젙���섏젙��
�섏젙�ъ쑀/�댁뿭:
========================================================
*/
package risk.DBconn;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import risk.util.Log;

public class DBconn {
	
	/*
	final private String CONST_MYSQL_MAINDB_URL       = "58.180.17.13";
//	final private String CONST_MYSQL_MAINDB_URL       = "localhost";
    final private String CONST_MYSQL_MAINDB_NAME      = "ASP_MAIN";
    final private String CONST_MYSQL_MAINDB_USER      = "posco_asp";
    final private String CONST_MYSQL_MAINDB_PASSWD    = "rsn601";
    */
	
	//final private String CONST_MYSQL_MAINDB_URL       = "210.107.78.180:3306";
    //final private String CONST_MYSQL_MAINDB_NAME      = "TRANS_COMMON";
    //final private String CONST_MYSQL_MAINDB_USER      = "common";
    //final private String CONST_MYSQL_MAINDB_PASSWD    = "common123";
	
    
	final private String CONST_MYSQL_MAINDB_URL       = "58.180.17.4";
    final private String CONST_MYSQL_MAINDB_NAME      = "TRANS_SEOUL";
    final private String CONST_MYSQL_MAINDB_USER      = "trans_seoul";
    final private String CONST_MYSQL_MAINDB_PASSWD    = "seoul#@!";
    
	
	//諛깆뾽�꾩슜
    /*
    final private String CONST_MYSQL_MAINDB_URL_T       = "58.180.17.15";
    final private String CONST_MYSQL_MAINDB_NAME_T      = "TRANS_BACKUP";
    final private String CONST_MYSQL_MAINDB_USER_T      = "trans_backup";
    final private String CONST_MYSQL_MAINDB_PASSWD_T    = "dkfdptmdps2025";
    */
    
	/*
    final private String CONST_MYSQL_MAINDB_URL       = "210.107.78.50";
    final private String CONST_MYSQL_MAINDB_NAME      = "TRANS_MAIN";
    final private String CONST_MYSQL_MAINDB_USER      = "foreign_asp";
    final private String CONST_MYSQL_MAINDB_PASSWD    = "rsn601";
    */
	
    final private String CONST_MYSQL_SUBDB_URL       = "58.180.13.45";
    //final private String CONST_MYSQL_SUBDB_URL       = "localhost";
    final private String CONST_MYSQL_SUBDB_NAME      = "DAEGU_ASP";
    final private String CONST_MYSQL_SUBDB_USER      = "daegu_asp";
    final private String CONST_MYSQL_SUBDB_PASSWD    = "rsn601";
    
    final private String CONST_MYSQL_SMSDB_URL       = "58.180.13.44";
    final private String CONST_MYSQL_SMSDB_NAME       = "SMS_ASP";
    final private String CONST_MYSQL_SMSDB_USER       = "sms_asp";
    final private String CONST_MYSQL_SMSDB_PASSWD     = "rsn601";
    
    public Connection   mConn = null;   
    public boolean      mbConnet = false;
    public boolean      mbTR = false;

    public boolean isConnected() {
        return mbConnet;
    }

    public boolean isTransaction() {
        return mbTR;
    }

    /**
     * DB tomcat Connection Pool 
     * @return
     * @throws SQLException
     * @throws Exception
     * @throws ClassNotFoundException
     */
    public void getDBCPConnection() throws SQLException, NamingException{
	       String poolName = "DAEGU";
	       Context initContext = null;
	       Context envContext = null;
	       DataSource ds = null;
	       Connection conn = null;
	       
	       initContext = new InitialContext();
	       envContext  = (Context)initContext.lookup("java:/comp/env");
	       ds = (DataSource)envContext.lookup(poolName);
	       mConn = ds.getConnection();	      
   }
    
    /**
     * Mysql Direct Connection
     * @return
     * @throws SQLException
     * @throws Exception
     * @throws ClassNotFoundException
     */   
    public boolean getMainDirectConnection() throws SQLException,Exception, ClassNotFoundException {

        boolean bRtnValue = false;

        if ( !mbConnet )
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            mConn = DriverManager.getConnection("jdbc:mysql://" + CONST_MYSQL_MAINDB_URL + "/" + CONST_MYSQL_MAINDB_NAME
                                                                 + "?user=" + CONST_MYSQL_MAINDB_USER
                                                                 + "&password=" + CONST_MYSQL_MAINDB_PASSWD 
                                                                 + "&autoReconnect=true&connectTimeout=0&maxReconnects=3");
        }

        if ( mConn != null )
        {
            mbConnet = true;
        }

        bRtnValue = true;
        return bRtnValue;
    }
    /*
    public boolean getMainDirectConnection_t() throws SQLException,Exception, ClassNotFoundException {

        boolean bRtnValue = false;

        if ( !mbConnet )
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            mConn = DriverManager.getConnection("jdbc:mysql://" + CONST_MYSQL_MAINDB_URL_T + "/" + CONST_MYSQL_MAINDB_NAME_T
                                                                 + "?user=" + CONST_MYSQL_MAINDB_USER_T
                                                                 + "&password=" + CONST_MYSQL_MAINDB_PASSWD_T 
                                                                 + "&autoReconnect=true&connectTimeout=0&maxReconnects=3");
        }

        if ( mConn != null )
        {
            mbConnet = true;
        }

        bRtnValue = true;
        return bRtnValue;
    }
    */
    
    
    /**
     * Mysql Direct Connection
     * @return
     * @throws SQLException
     * @throws Exception
     * @throws ClassNotFoundException
     */   
    public boolean getSubDirectConnection() throws SQLException,Exception, ClassNotFoundException {
        boolean bRtnValue = false;
        System.out.println("========================= local DB ");
        if ( !mbConnet )
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            mConn = DriverManager.getConnection("jdbc:mysql://" + CONST_MYSQL_SUBDB_URL + "/" + CONST_MYSQL_SUBDB_NAME
                                                                 + "?user=" + CONST_MYSQL_SUBDB_USER
                                                                 + "&password=" + CONST_MYSQL_SUBDB_PASSWD 
                                                                 + "&autoReconnect=true&connectTimeout=0&maxReconnects=3");
        }

        if ( mConn != null )
        {
            mbConnet = true;
        }

        bRtnValue = true;
        return bRtnValue;
    }
    
    /**
     * Mysql Direct Connection
     * @return
     * @throws SQLException
     * @throws Exception
     * @throws ClassNotFoundException
     */   
    public boolean getSMSDirectConnection() throws SQLException,Exception, ClassNotFoundException {
        boolean bRtnValue = false;

        if ( !mbConnet )
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            mConn = DriverManager.getConnection("jdbc:mysql://" + CONST_MYSQL_SMSDB_URL + "/" + CONST_MYSQL_SMSDB_NAME
                                                                 + "?user=" + CONST_MYSQL_SMSDB_USER
                                                                 + "&password=" + CONST_MYSQL_SMSDB_PASSWD 
                                                                 + "&autoReconnect=true&connectTimeout=0&maxReconnects=3");
        }

        if ( mConn != null )
        {
            mbConnet = true;
        }

        bRtnValue = true;
        return bRtnValue;
    }

  
    public void close() throws SQLException {

        if ( mConn != null )
        {          
            mConn.close();
            mbConnet = false;
            mConn = null;           
        }
    }

    /**
     * �먮즺議댁옱�щ�瑜��뚯븙�섎뒗 荑쇰━ �ㅽ뻾���ъ슜
     * @param psQuery
     * @return
     * @throws SQLException
     */
    public boolean  exists( String psQuery ) throws SQLException {
        boolean bRtnValue = false;

        Statement stmt = null;
        ResultSet rs = null;

        stmt = mConn.createStatement();
        rs = stmt.executeQuery(psQuery);
        
        if ( rs.next() ) {
            bRtnValue = true;
        } else {
            bRtnValue = false;
        }

        stmt.close();
        rs.close();

        stmt    = null;
        rs  = null;
        return bRtnValue;

    }

    /**
     * �먮즺議댁옱�щ�瑜��뚯븙�섎뒗 荑쇰━ �ㅽ뻾���ъ슜
     * @param popstmt
     * @return
     * @throws SQLException
     */
    public boolean  exists( PreparedStatement popstmt ) throws SQLException {
        boolean bRtnValue = false;

        Statement stmt = null;
        ResultSet rs = null;

        rs = popstmt.executeQuery();

        if ( rs.next() ) {
            bRtnValue = true;
        } else {
            bRtnValue = false;
        }

        rs.close();
        rs  = null;
        return bRtnValue;

    }


    /**
     * �몃옖�앹뀡 �쒖옉
     * @throws SQLException
     */
    public void TransactionStart() throws SQLException {

        mConn.setAutoCommit( false );
        mbTR = true;   //Tranjaction Start

    }


    /**
     * �몃옖�앹뀡 而ㅻ컠
     * @throws SQLException
     */
    public void Commit() throws SQLException {
        mConn.commit();
        mConn.setAutoCommit( true );
        mbTR = false;  //Tranjaction End
    }

    /**
     *  �몃옖�앹뀡 醫낅즺
     * @throws SQLException
     */
    public void Rollback() throws SQLException {
        mConn.rollback();
        mConn.setAutoCommit( true );
        mbTR = false;  //Tranjaction End
    }


    /**
     *  Statement �앹꽦
     * @return
     * @throws SQLException
     */
    public Statement createStatement() throws SQLException {
        return mConn.createStatement();
    }

    /**
     *  TYPE_SCROLL_INSENSITIVE��Statement �앹꽦
     * @return
     * @throws SQLException
     */
    public Statement createScrollStatement() throws SQLException {
        return mConn.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
    }

    /**
     * PreparedStatement �앹꽦
     * @param psQuery
     * @return
     * @throws SQLException
     */
    public PreparedStatement createPStatement(String psQuery) throws SQLException {
        return mConn.prepareStatement( psQuery, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
    }


    /**
     * PreparedStatement濡�議고쉶�섏뿬 ResultSet  由ы꽩
     * @param pPstmt
     * @return
     * @throws SQLException
     */
    public ResultSet executeQuery( PreparedStatement pPstmt ) throws SQLException {

        ResultSet rs        = null;
        rs = pPstmt.executeQuery();
        return rs;
    }

    /**
     * IUD Query �ㅽ뻾
     * @param psQuery
     * @return
     * @throws SQLException
     */
    public int executeUpdate(String psQuery) throws SQLException {
        int irtnValue = 0;
        Statement stmt = null;
        stmt = mConn.createStatement();
        irtnValue = stmt.executeUpdate(psQuery);
        if (stmt != null) stmt.close();
        return irtnValue;
    }

    /**
     * IUD Query �ㅽ뻾
     * @param pPstmt
     * @return
     * @throws SQLException
     */
    public int executeUpdate(PreparedStatement pPstmt) throws SQLException {
        int irtnValue = 0;
        Statement stmt = null;

        irtnValue = pPstmt.executeUpdate();
        if (stmt != null) stmt.close();
        return irtnValue;
    }

    /**
     * Stored Procedure �ㅽ뻾
     * @param psQuery
     * @return
     * @throws SQLException
     */
    public CallableStatement createCallableStatement(String psQuery ) throws SQLException {
        return mConn.prepareCall(psQuery);
    }

    /**
     * Statement �곌껐�댁젣
     * @param poSt
     */
    public void realeseStatment( Statement poSt ) {
        try { if( poSt != null)  poSt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
    }

    /**
     * PreparedStatement �곌껐�댁젣
     * @param poPst
     */
    public void realesePStatment( PreparedStatement poPst ) {
        try { if( poPst != null)  poPst.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
    }

    /**
     * ResultSet �곌껐�댁젣
     * @param poRs
     */
    public void realeseResultSet( ResultSet poRs ) {
        try { if( poRs != null)  poRs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }

    }

}
