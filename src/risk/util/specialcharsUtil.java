/**=======================================================
주시스템    : RSN
서브시스템  : 특수문자열 설정 관리
프로그램 ID : specialcharsUtil
프로그램 명 : 
프로그램개요   : 
작   성   자   : 이재희
작   성   일   : 2006. 05. 13
==========================================================
수정자/수정일 :
수정사유/내역 :
========================================================*/
package risk.util ; 

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

public class specialcharsUtil {

    private static String       sCfg_file_name = "risk.specialchars";     //환경화일명
    private static long         lCfg_last_modified = 0L;                //환경화일 최종 변경일
    private static URL          propUrl = null;                         //환경화일 위치
    private static Properties   propCfg = null;                         //환경변수 임시 저장

    // 생성자 함수
    public specialcharsUtil() {}

    /*****************************************************************
    주요기능            : 특수문자 설정 파일 위치를 찾는다.
    TABLE               :
    입력 파라미터       :
    출력 파라미터   :
    비고                : 특수문자 설정 파일은 classpath가 등록된 위치에 놓여져야 한다.
    ****************************************************************/
    private void getCharsFile()    {
        propUrl = Thread.currentThread().getContextClassLoader().getResource(sCfg_file_name);

        if ( propUrl != null ) {
            openCharsFile();
        } else {
            System.out.println(sCfg_file_name + " 특수문자 설정 파일을 찾을 수 없습니다.");
        }

    }

    /*****************************************************************
    주요기능            : 특수문자 설정 파일에서 변수 읽어 오기
    TABLE               :
    입력 파라미터   :
    출력 파라미터   :
    비고                : 특수문자 설정 파일은 classpath가 등록된 위치에 놓여져야 한다.
    ****************************************************************/
    private void openCharsFile() {
        File cfgFile = new File(propUrl.getPath());

        FileInputStream cfg_fileInp = null;
        if( cfgFile.canRead() ) {
            try {
                cfg_fileInp = new FileInputStream(cfgFile);
                propCfg = new Properties();
                propCfg.load(cfg_fileInp);
                lCfg_last_modified = cfgFile.lastModified();
            } catch (Exception e) {
                System.out.println(sCfg_file_name + " 특수문자 설정을 읽는 중 오류가 발생했습니다.");
                e.printStackTrace();
            } finally {
                try {
                    if ( cfg_fileInp != null ) cfg_fileInp.close();
                } catch (Exception closeE) { closeE.printStackTrace(); }
            }
        } else {
            System.out.println(sCfg_file_name + " 특수문자 설정 파일이 열리지 않습니다.");
        }
    }

    public void isModified() {
        if ( lCfg_last_modified == 0L ) getCharsFile(); //최초 로딩시에만
        if ( propUrl != null ) {
            File cfgFile = new File(propUrl.getPath());

            if ( lCfg_last_modified != cfgFile.lastModified() ) {
            	getCharsFile();
                System.out.println("특수문자 설정 파일이 바뀌었습니다.");
            }
        }
    }

    /*****************************************************************
    주요기능            : 특수문자 설정의 KEY 값에 대한 VALUE를 돌려준다.
    TABLE               :
    입력 파라미터   : 특수문자 설정 KEY
    출력 파라미터   : 특수문자 설정 VALUE
    비고                : 특수문자 설정의 KEY 값은 대소문자를 구별한다.
    ****************************************************************/
    public String[] getChars(String sKey) {
        String rtnConfig = "";
        String rtnArr[] = null;
        isModified();
        try {
            rtnConfig = propCfg.getProperty(sKey);
            if ( rtnConfig == null ) {
                System.out.println(sCfg_file_name + " 에 " +sKey + "변수가 없습니다");
                rtnConfig = "";
            }else{
            	rtnArr = rtnConfig.split(",");
            }
        } catch (Exception e ) {
            System.out.println(sKey + "환경 변수를 참조하던중 예외 상황이 발생했습니다");
            e.printStackTrace();
            rtnConfig = "";
        }
        return rtnArr;
    }
}