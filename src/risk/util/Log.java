/**=======================================================
주시스템    : RSN
서브시스템  : 공통 로그 관리
프로그램 ID : Log
프로그램 명 : 로그 기록
프로그램개요   : 로그화일에 로그를 기록한다.
작   성   자   : 윤석준
작   성   일   : 2006. 04. 12
==========================================================
수정자/수정일 :
수정사유/내역 :
========================================================*/
package risk.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;

import risk.util.ConfigUtil;

public class Log
{
    
    //기본 로그 경로 : 실제 로그 경로는 환경파일(risk.properties)에서 읽어 온다.
    static String LOGPATH = File.separatorChar + "topic02" + File.separatorChar + "ncs" + File.separatorChar;     // 로그파일 경로
    
    
    static int miLOGLEVEL = 3;       // 로그 남기는 래벨 0:에러 메시지를 생성하지 않는다 
                                     //                  1:Default 에러 메시지를 생성한다 
                                     //                  2:Detail 에러 메시지 생성한다 
                                     //                  3:가장 자세한 Print Stack Trace 에러 메시지를 생성한다
                                    
    static int miISLOGWRITE = 1;     // 로그 남기기 여부  0:생성하지 않는다, 1:생성한다
    static int miISDEBUGWRITE = 1;   // 디버그 로그 남기기 여부  0:생성하지 않는다, 1:생성한다
    
    
    
    static public void setConf() {
        ConfigUtil cu = new ConfigUtil();
        if(!cu.getConfig("LOGPATH").equals("") ) LOGPATH = cu.getConfig("LOGPATH");
    }
    
    /*
    로그화일을 남길때 sLogDir을 지정 안할시 기본으로 default폴더에 저장된다
    단 디버그일경우에는 debug폴더에 저장된다
    */
    
    /******************************************************************************************************/
    //디버그 로그 관련(디버그로그는 추후 로그로 안남길수 있음)
    /******************************************************************************************************/
    public static void debug(String sMsg) {
        debugWriter("DEBUGLOG", sMsg);
    }
    
    public static void crond(String sMsg) {
        debugWriter("CRONLOG", sMsg);
    }
    
    public static void crond(String sMsg, String Msg) {
        debugWriter(sMsg, Msg);
    }
    
    /******************************************************************************************************/
    //로그 관련
    /******************************************************************************************************/
    
    /******************************************************************************************************/
    //에러 로그 관련
    /******************************************************************************************************/
    /**
    문자를 로그로 생성한다
    **/
    public static void writeExpt(String sMsg) {
      writeExpt("DEFAULTLOG", sMsg);
    }
    
    /**
     * 지정된 경로에 문자를 바로 로그로 생성한다
     * @param sLogDir
     * @param sMsg
     */
    public static void writeExpt(String sLogDir, String sMsg) {
        logWriter(sLogDir, sMsg);
    }
    
    public static void writeExpt(SQLException ex,String sSqlstr) {
        writeExpt("DEFAULTLOG",ex, sSqlstr );
    }
    
    public static void writeExpt(String sLogDir, SQLException ex) {
        writeExpt(sLogDir, ex, "");
    }
    
    public static void writeExpt(String sLogDir, SQLException ex, String sSqlstr) {
        String sErrorMsg = getLevelExpt(ex);
    
        logWriter(sLogDir, " SQLException\n" +
                    "Oracle Error Code [" + ex.getErrorCode() + "]\n" +
                    "SQLState          [" + ex.getSQLState() + "]\n" +
                      "<<-----------------------------------------------------------------------\n" +
                      sSqlstr + "\n" +
                    "----------------------------------------------------------------------->>\n" +
                    getLevelExpt(ex));
                    //"Error Message : " + getLevelExpt(ex);
    }
    
    public static void writeExpt(IOException ex) {
        writeExpt("DEFAULTLOG", "", ex);
    }
    
    public static void writeExpt(Object obj, IOException ex) {
        writeExpt("DEFAULTLOG", obj.getClass().getName(), ex);
    }
    
    public static void writeExpt(String sLogDir, Object obj, IOException ex) {
        writeExpt(sLogDir, obj.getClass().getName(), ex);
    }
    
    public static void writeExpt(String sLogDir, String sObjNam, IOException ex) {
        String sErrorMsg = getLevelExpt(ex);
        logWriter(sLogDir, sObjNam + " IOException \nError Message :" + sErrorMsg);
    }
    
    public static void writeExpt(Exception ex) {
        writeExpt("DEFAULTLOG", "", ex);
    }
    
    public static void writeExpt(Object obj, Exception ex) {
        writeExpt("DEFAULTLOG", obj.getClass().getName(), ex);
    }
    
    public static void writeExpt(String sLogDir, Object obj, Exception ex) {
        writeExpt(sLogDir, obj.getClass().getName(), ex);
    }
    
    public static void writeExpt(String sLogDir, String sObjNam, Exception ex) {
        String sErrorMsg = getLevelExpt(ex);
        logWriter(sLogDir, sObjNam + " Exception \nError Message : " + sErrorMsg);
    }
    
    /**
    * 최초작성자   :  
    * String      psUsrMsg : Exception이외에 추가할 오류 메세지
    */
    public static void writeExpt(String sLogDir, String sObjNam, Exception ex, String psUsrMsg) {
      String sErrorMsg = getLevelExpt(ex);
      if(psUsrMsg.length() > 0) sErrorMsg = psUsrMsg + "\n"+ sErrorMsg;
      logWriter(sLogDir, sObjNam + " Exception \nError Message : " + sErrorMsg);
    }
    
    
    /**
     * 에러 로그를 생성한다
     * @param sLogDir
     * @param sMsg
     */
    private static void logWriter(String sLogDir, String sMsg) {
      try {
         setConf();
         java.text.SimpleDateFormat formatter1 = new java.text.SimpleDateFormat ("yyyyMMdd");
         java.text.SimpleDateFormat formatter2 = new java.text.SimpleDateFormat ("yyyy년MM월dd일 HH시mm분ss초");
         Calendar rightNow = Calendar.getInstance();
    
         String sLogdate = formatter1.format(rightNow.getTime());
         String sLogdate2 = formatter2.format(rightNow.getTime());
    
            System.out.println("[" + sLogdate2 + "] ## ERROR ## [" + sLogDir + "] " + "\n" + sMsg + "\n");
    
         if(miISLOGWRITE == 1) {
            if(!new File(LOGPATH + sLogDir).isDirectory() ) new File(LOGPATH + sLogDir).mkdirs();
    
            FileWriter fw = null;
            fw = new FileWriter(LOGPATH + sLogDir + File.separatorChar + sLogDir + sLogdate + ".txt", true);
            fw.write("[" + sLogdate2 + "] " + "\n" + sMsg + "\n");
            fw.close();
         }
      } catch(Exception e) {
            System.out.println("Log.java logWriter() error => " + e.toString());
      }
    }
    
    
    /**
     *  디버그 로그를 생성한다
     * @param sLogDir
     * @param sMsg
     */
    private static void debugWriter(String sLogDir, String sMsg) {
        try {
            setConf();
            if(miISDEBUGWRITE == 1) {
                java.text.SimpleDateFormat formatter1 = new java.text.SimpleDateFormat ("yyyyMMdd");
                java.text.SimpleDateFormat formatter2 = new java.text.SimpleDateFormat ("yyyy년MM월dd일 HH시mm분ss초");
                Calendar rightNow = Calendar.getInstance();
             
                String sLogdate = formatter1.format(rightNow.getTime());
                String sLogdate2 = formatter2.format(rightNow.getTime());
             
                //콘손에 로그를 뿌린다.
                System.out.println("[" + sLogdate2 + "] ## DEBUG ## [" + sLogDir + "] " + "\n" + sMsg + "\n");
             
                if(!new File(LOGPATH + sLogDir).isDirectory()) new File(LOGPATH + sLogDir).mkdirs();
             
                //파일로 로그를 뿌린다.
                FileWriter fw = null;
                fw = new FileWriter(LOGPATH + sLogDir + File.separatorChar + sLogDir + sLogdate + ".txt", true);
                fw.write("[" + sLogdate2 + "] " + "\n" + sMsg + "\n");
                fw.close();
            }
        } catch(Exception e) {
            System.out.println("Log.java logWriter() error => " + e.toString());
        }
    }
    
    /**
     * 에러 메시지를 가져온다
     * @param ex
     * @return
     */
    public static String getExptStr(SQLException ex) {
        String sRtnMsg = "";
        sRtnMsg = "Oracle Error Code [" + ex.getErrorCode() + "]\n" +
                  "SQLState          [" + ex.getSQLState() + "]\n" +
                    getLevelExpt(ex);
                      //"Error Message : " + getLevelExpt(ex);
        return sRtnMsg;
    }
    
    /**
     * 에러 메시지를 가져온다
     * @param ex
     * @return
     */
    public static String getExptStr(IOException ex) {
        String sRtnMsg = "";
        sRtnMsg = getLevelExpt(ex);
        return sRtnMsg;
    }
    
    /**
     * 에러 메시지를 가져온다
     * @param ex
     * @return
     */
    public static String getExptStr(Exception ex) {
        String sRtnMsg = "";
        sRtnMsg = getLevelExpt(ex);
        return sRtnMsg;
    }
    
    /**
     * 로그 레벨에 따른 메세지를 리턴 한다.
     * @param throwable
     * @return
     */
    private static String getLevelExpt(Throwable throwable) {
        String sErrorMsg = "";
        
        switch (miLOGLEVEL) {
           case 0 :
              sErrorMsg = "";
              break;
           case 1 :
              sErrorMsg = throwable.getMessage();
              break;
           case 2 :
              sErrorMsg = throwable.toString();
              break;
           case 3 :
              sErrorMsg = getStackTrace(throwable);
              break;
           default :
              sErrorMsg = getStackTrace(throwable);
              break;
        }
        return sErrorMsg;
    }
    
    /**
     * 에러메시지를 가져온다
     * @param throwable
     * @return
     */
    private static String getStackTrace(Throwable throwable) {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        PrintWriter printwriter = new PrintWriter(bytearrayoutputstream);
        throwable.printStackTrace(printwriter);
        printwriter.flush();
        return bytearrayoutputstream.toString();
    }
}