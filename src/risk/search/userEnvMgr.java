package risk.search;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;
import risk.search.keywordInfo;

/**
 * @author neosj
 * 사용자 환경 관리 Manager
 */
public class userEnvMgr {

    DBconn dbconn   = null;
    Statement stmt  = null;
    ResultSet rs    = null;
    StringBuffer sb = null;
    String sQuery   = "";
    DateUtil du = new DateUtil();

    String msDefaultUser        = "**NCS**";
    userEnvInfo uei = null; 

    /**
     * 기본사용자 아이디를 설정한다.
     * @param psStr
     */
    public void setDefaultUser(String psStr ) {
        this.msDefaultUser = psStr;
    }

    /**
     * 사용자 환경 조회
     * @param psM_seq
     * @return userEnvInfo
     */
    public userEnvInfo getUserEnv( String psUserID ) {

    	uei = queryUserEnv( psUserID );

        if ( uei == null ) {
            //조회한 사용자 환경자료가 없으면
            //NCS 디폴트 유저의 설정을 가져온다.
            uei = queryUserEnv( msDefaultUser );
        }
        return uei;
    }

    /**
     * 사용자 환경 조회
     * @param psM_Seq
     * @return userEnvInfo
     */
    public userEnvInfo getUserEnv( String psM_Seq, String psMg_Seq ) {
      
        uei = queryUserEnv( psM_Seq,psMg_Seq );

        if ( uei == null ) {
            //조회한 사용자 환경자료가 없으면
            //NCS 디폴트 유저의 설정을 가져온다.
            uei = queryUserEnv( msDefaultUser );
        }
        return uei;
    }



    /**
     * 사용자 환경 조회 기본 메서드
     * @param psM_seq
     * @return userEnvInfo
     */
    public userEnvInfo queryUserEnv(String psUserID ) {
        try {
        	sb = new StringBuffer();
        	sb.append("SELECT ST_SEQ,                       \n");
            sb.append("       K_XP,                         \n");
            sb.append("       ST_INTERVAL_DAY,              \n");
            sb.append("       MD_TYPE,                      \n");
            sb.append("       SG_SEQ,                       \n");
            sb.append("       SG_SEQ_AL,                    \n");
            sb.append("       ST_RELOAD_TIME,               \n");
            sb.append("       ST_LIST_CNT,                  \n");
            sb.append("       ST_MENU,                      \n");
            sb.append("       M_SEQ,                      \n");
            sb.append("       MG_SEQ,                       \n");
            sb.append("       MG_XP,                        \n");
            sb.append("       MG_SITE,                      \n");
            sb.append("       MG_MENU                       \n");
            sb.append("  FROM SETTING A, MEMBER_GROUP B                     \n");
            sb.append(" WHERE A.M_SEQ IN (SELECT M_SEQ                       \n");
            sb.append("                   FROM MEMBER                       \n");
            sb.append("                   WHERE M_ID = '" + psUserID + "' )  \n");
            sb.append("       AND B.MG_SEQ IN (SELECT MG_SEQ                      \n");
            sb.append("                        FROM MEMBER                       \n");
            sb.append("                        WHERE M_ID = '" + psUserID + "' )  \n");


            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            Log.debug(sb.toString() );
            if ( rs.next() ) {
            	 uei = new userEnvInfo();
                 uei.setSt_seq(rs.getString("ST_SEQ"));
                 uei.setK_xp(rs.getString("K_XP"));
                 uei.setSg_seq(rs.getString("SG_SEQ"));
                 uei.setSg_seq_al(rs.getString("SG_SEQ_AL"));
                 uei.setSt_interval_day(rs.getString("ST_INTERVAL_DAY"));
                 uei.setSt_list_cnt(rs.getString("ST_LIST_CNT"));
                 uei.setSt_menu(rs.getString("ST_MENU"));
                 uei.setM_seq(rs.getString("M_SEQ"));
                 uei.setSt_reload_time(rs.getString("ST_RELOAD_TIME"));
                 uei.setMd_type(rs.getString("MD_TYPE"));
                 uei.setMg_seq(rs.getString("MG_SEQ"));
                 uei.setMg_xp(rs.getString("MG_XP"));
                 uei.setMg_site(rs.getString("MG_SITE"));
                 uei.setMg_menu(rs.getString("MG_MENU"));

            }

            sb = null;

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );
        } catch (Exception ex ) {
            Log.writeExpt(ex);
        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return uei;
    }


    /**
     * 사용자 환경 조회 기본 메서드
     * @param psM_seq
     * @return userEnvInfo
     */
    public userEnvInfo queryUserEnv(String psM_Seq, String psMg_Seq ) {
        try {

            sb = new StringBuffer();
            sb.append("SELECT ST_SEQ,                       \n");
            sb.append("       K_XP,                         \n");
            sb.append("       ST_INTERVAL_DAY,              \n");
            sb.append("       MD_TYPE,                      \n");
            sb.append("       SG_SEQ,                       \n");
            sb.append("       ST_RELOAD_TIME,               \n");
            sb.append("       ST_LIST_CNT,                  \n");
            sb.append("       ST_MENU,                      \n");
            sb.append("       MG_SEQ,                       \n");
            sb.append("       MG_XP,                        \n");
            sb.append("       MG_SITE,                      \n");
            sb.append("       MG_MENU                       \n");
            sb.append("  FROM SETTING A,                   	\n");
            sb.append("       MEMBER_GROUP B				\n");
            sb.append(" WHERE A.M_SEQ   = " + psM_Seq   + " \n");
            sb.append("       AND B.MG_SEQ   = " + psMg_Seq   + " \n");
           

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            //System.out.println(sb.toString());

            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            Log.debug(sb.toString() );
            if ( rs.next() ) {
                uei = new userEnvInfo();
                uei.setSt_seq(rs.getString("ST_SEQ"));
                uei.setK_xp(rs.getString("K_XP"));
                uei.setSg_seq(rs.getString("SG_SEQ"));
                uei.setSt_interval_day(rs.getString("ST_INTERVAL_DAY"));
                uei.setSt_list_cnt(rs.getString("ST_LIST_CNT"));
                uei.setSt_menu(rs.getString("ST_MENU"));
                uei.setSt_reload_time(rs.getString("ST_RELOAD_TIME"));
                uei.setMd_type(rs.getString("MD_TYPE"));
                uei.setMg_seq(rs.getString("MG_SEQ"));
                uei.setMg_xp(rs.getString("MG_XP"));
                uei.setMg_site(rs.getString("MG_SITE"));
                uei.setMg_menu(rs.getString("MG_MENU"));
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
        return uei;
    }




    /**
     * 사용자 환경 자료 삭제
     * @param psM_seq
     * @return boolean
     */
    public boolean deleteUserEnv(String psM_seq) {


        boolean bRtnValue = false ;

        try {
            sb = new StringBuffer();
            sb.append("DELETE FROM SETTING              \n");
            sb.append(" WHERE M_SEQ = " + psM_seq + "   \n");

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();

            if ( stmt.executeUpdate(sb.toString()) > 0  ) {
                bRtnValue = true;
            }
            sb = null;

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );
        } catch (Exception ex ) {
            Log.writeExpt(ex);
        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return bRtnValue;

    }

    /**
     * 사용자 환경자료 입력
     * @return boolean
     */
    public boolean saveUserEnv( String psK_Xp       ,
                                String psS_Period   ,
                                String psS_Type     ,
                                String psSg_Seq     ,
                                String psSg_Seq_Al  ,
                                String psS_Reflash  ,
                                String psS_Listset  ,
                                String psS_Menu     ,
                                String psM_Seq )
    {

        boolean bRtnValue = false ;

        try {

            sb = new StringBuffer();

            StringBuffer sbQuery = new StringBuffer();

            sb.append("SELECT 1                                                         \n");
            sb.append("  FROM SETTING                                                   \n");
            sb.append(" WHERE M_SEQ = " + psM_Seq + "                                   \n");

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();

            Log.debug(sb.toString() );

            //자료가 있으면 UPDATE
            if ( dbconn.exists(sb.toString()) ) {

                sbQuery.append("UPDATE SETTING                                     \n");
                sbQuery.append("   SET K_XP      = " +   psK_Xp      + "   ,       \n");
                sbQuery.append("       ST_INTERVAL_DAY  = " +   psS_Period  + "   ,       \n");
                sbQuery.append("       MD_TYPE    = " +   psS_Type    + "   ,       \n");
                sbQuery.append("       SG_SEQ    = '" +   psSg_Seq    + "'   ,       \n");
                sbQuery.append("       SG_SEQ_AL    = '" +   psSg_Seq_Al    + "'   ,       \n");
                sbQuery.append("       ST_RELOAD_TIME = " +   psS_Reflash + "   ,       \n");
                sbQuery.append("       ST_LIST_CNT = " +   psS_Listset + "   ,       \n");
                sbQuery.append("       ST_MENU    = " +   psS_Menu    + "           \n");
                sbQuery.append(" WHERE M_SEQ     = " +   psM_Seq     + "           \n");

            } else {         

                sbQuery.append("INSERT INTO SETTING (                                \n");            
                sbQuery.append("     K_XP        ,                                   \n");
                sbQuery.append("     ST_INTERVAL_DAY    ,                                   \n");
                sbQuery.append("     MD_TYPE      ,                                   \n");
                sbQuery.append("     SG_SEQ      ,                                   \n");
                sbQuery.append("     SG_SEQ_AL    ,                                   \n");
                sbQuery.append("     ST_RELOAD_TIME   ,                                   \n");
                sbQuery.append("     ST_LIST_CNT   ,                                   \n");
                sbQuery.append("     ST_MENU      ,                                   \n");
                sbQuery.append("     ST_REGDATE      ,                                   \n");
                sbQuery.append("     M_SEQ       )                                   \n");
                sbQuery.append("VALUES                                               \n");               
                sbQuery.append("(     " + psK_Xp          + ",                        \n");
                sbQuery.append("     " + psS_Period      + ",                        \n");
                sbQuery.append("     " + psS_Type        + ",                        \n");
                sbQuery.append("     '" + psSg_Seq        + "',                        \n");
                sbQuery.append("     '" + psSg_Seq_Al     + "',                        \n");
                sbQuery.append("     " + psS_Reflash     + ",                        \n");
                sbQuery.append("     " + psS_Listset     + ",                        \n");
                sbQuery.append("     " + psS_Menu        + ",                        \n");
                sbQuery.append("     '" + du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+ "',                        \n");
                sbQuery.append("     " + psM_Seq         + ")                        \n");
            }

            Log.debug(sbQuery.toString() );
            if ( stmt.executeUpdate(sbQuery.toString()) > 0 ) {
                bRtnValue = true;
            }

            sb = null;

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );
        } catch (Exception ex ) {
            Log.writeExpt(ex);
        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return bRtnValue;

    }


    /**
     * 키워드 그룹 조회
     */
    public ArrayList getKeywordGroup( ) {
        return getKeywordGroup( "" );
    }

    public ArrayList getKeywordGroup( String psMgXp ) {

        ArrayList arrList = new ArrayList();

        try {

            sb = new StringBuffer();

            sb.append("SELECT K_SEQ     ,               \n");
            sb.append("       K_XP      ,               \n");
            sb.append("       K_YP      ,               \n");
            sb.append("       K_ZP      ,               \n");
            sb.append("       K_TYPE    ,               \n");
            sb.append("       K_POS     ,               \n");
            sb.append("       K_OP      ,               \n");
            sb.append("       K_VALUE                   \n");
            sb.append("  FROM KEYWORD                   \n");
            sb.append(" WHERE K_YP = 0                  \n");
            sb.append("   AND K_TYPE = 1                \n");
            sb.append("   AND K_USEYN='Y'                \n");

            if ( !psMgXp.equals("") )
            sb.append("   AND K_XP IN (" + psMgXp + ")  \n");
            sb.append(" ORDER BY K_SEQ                  \n");

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            Log.debug(sb.toString() );

            while ( rs.next() ) {
            	arrList.add(
                  				new keywordInfo(
                                        rs.getString("K_SEQ"    ),
                                        rs.getString("K_XP"     ),
                                        rs.getString("K_YP"     ),
                                        rs.getString("K_ZP"     ),
                                        rs.getString("K_TYPE"   ),
                                        rs.getString("K_POS"    ),
                                        rs.getString("K_OP"     ),
                                        rs.getString("K_VALUE"  )
                                     )
                );

            }

            sb = null;

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );
        } catch (Exception ex ) {
            Log.writeExpt(ex);
        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return arrList;
    }
    
    public ArrayList getSubKeywordGroup( String psMgXp) {
    	return getSubKeywordGroup(psMgXp,"");
    }
    public ArrayList getSubKeywordGroup( String psMgXp, String psMgYp ) {

        ArrayList arrList = new ArrayList();

        try {

            sb = new StringBuffer();

            sb.append("SELECT K_SEQ     ,               \n");
            sb.append("       K_XP      ,               \n");
            sb.append("       K_YP      ,               \n");
            sb.append("       K_ZP      ,               \n");
            sb.append("       K_TYPE    ,               \n");
            sb.append("       K_POS     ,               \n");
            sb.append("       K_OP      ,               \n");
            sb.append("       K_VALUE                   \n");
            sb.append("  FROM KEYWORD                   \n");
            sb.append(" WHERE 1=1		                \n");
            if ( !psMgXp.equals("") && psMgYp.equals("")){
            sb.append("   AND K_XP IN (" + psMgXp + ")  \n");
            sb.append("   AND K_ZP = 0  \n");
            sb.append("   AND K_YP > 0  \n");
            sb.append("   AND K_TYPE = 1  \n");
            }else{
            	sb.append("   AND K_XP = "+ psMgXp +"  \n");
                sb.append("   AND K_YP = "+ psMgYp +"  \n");
                sb.append("   AND K_TYPE = 2  \n");
            }
            
            
            sb.append(" ORDER BY K_SEQ                  \n");

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            Log.debug(sb.toString() );

            while ( rs.next() ) {
            	arrList.add(
                  				new keywordInfo(
                                        rs.getString("K_SEQ"    ),
                                        rs.getString("K_XP"     ),
                                        rs.getString("K_YP"     ),
                                        rs.getString("K_ZP"     ),
                                        rs.getString("K_TYPE"   ),
                                        rs.getString("K_POS"    ),
                                        rs.getString("K_OP"     ),
                                        rs.getString("K_VALUE"  )
                                     )
                );

            }

            sb = null;

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );
        } catch (Exception ex ) {
            Log.writeExpt(ex);
        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return arrList;
    }

}
