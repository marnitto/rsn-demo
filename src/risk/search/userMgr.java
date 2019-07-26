/**
========================================================
주 시 스 템 : RSN
서브 시스템 :
프로그램 ID : userMgr
프로그램 명 : 사용자 정보 조회
프로그램개요 : 사용자 아이디 비밀번호조회
작 성 자 : 윤석준
작 성 일 : 2006. 04. 11
========================================================
수정자/수정일:
수정사유/내역:
========================================================
*/
package risk.search;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;


//import risk.util.StringUtil;
import risk.DBconn.DBconn;
import risk.util.Log;

public class userMgr {

    DBconn dbconn   = null;
    Statement stmt  = null;
    ResultSet rs    = null;

    StringBuffer sb = null;
    String sQuery   = "";

    public String msUserID     = "";
    public String msUserPW     = "";
    public String msConnctDate = "";
    public String mSConnectIP  = "";

    public userMgr() {

    }

    public void searchUserInfo(String psUserID, String psUserPW,String psUserIP ) {

        //StringUtil su = new StringUtil();

        try {

            sb = new StringBuffer();

            //sb.append("SELECT M_ID,                                           \n");
            //sb.append("       M_PASS,                                         \n");
            //sb.append("       TO_CHAR(M_LASTDATE,'YYYY-MM-DD') AS M_LASTDATE, \n");
            //sb.append("       M_LASTIP                                        \n");
            //sb.append("  FROM MEMBER                                          \n");
            //sb.append(" WHERE M_ID = '" + psUserID + "'                       \n");

            sb.append("SELECT M_ID,                                           \n");
            sb.append("       M_PASS                                          \n");
            sb.append("  FROM MEMBER                                          \n");
            sb.append(" WHERE M_ID = '" + psUserID + "'                       \n");


            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());

            if ( rs.next() ) {

                msUserID        = rs.getString("M_ID")       == null ? "" : rs.getString("M_ID")      ;
                msUserPW        = rs.getString("M_PASS")     == null ? "" : rs.getString("M_PASS")    ;
                //msConnctDate    = rs.getString("M_LASTDATE") == null ? "" : rs.getString("M_LASTDATE");
                //mSConnectIP     = rs.getString("M_LASTIP")   == null ? "" : rs.getString("M_LASTIP")  ;
            }

            sb = null;
            rs.close();
            rs = null;



            //로그인 성공이면 접속일자와 아이피를 로그에 남긴다.
            if ( msUserID.equals( psUserID ) && msUserPW.equals(psUserPW) ) {

                rs = stmt.executeQuery("SELECT SEQ_ML_SEQ.NEXTVAL AS NEWSEQ FROM DUAL");

                String sNewSeq  = "";

                if ( rs.next() ) {
                    sNewSeq = rs.getString("NEWSEQ");
                }

                sb = new StringBuffer();

                sb.append("INSERT INTO MEMBER_LOG(ML_SEQ ,                                              \n");
                sb.append("                  M_SEQ  ,                                                   \n");
                sb.append("                  ML_IP  ,                                                   \n");
                sb.append("                  ML_DATE)                                                   \n");
                sb.append("         VALUES( " + sNewSeq + " ,                                           \n");
                sb.append("                 (SELECT M_SEQ FROM MEMBER WHERE M_ID = '" + psUserID + "'), \n");
                sb.append("                 '" + psUserIP + "' ,                                        \n");
                sb.append("                 SYSDATE                                                     \n");
                sb.append("                )                                                            \n");

                stmt.executeUpdate( sb.toString() );
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
    }

}