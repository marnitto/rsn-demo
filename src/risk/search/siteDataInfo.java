/**
========================================================
주 시 스 템 : RSN
서브 시스템 : 자료검색
프로그램 ID : siteDataInfo
프로그램 명 : 사이트자료 데이터 class
프로그램개요 : 사이트자료 Data Beans
작 성 자 : 윤석준
작 성 일 : 2006. 04. 11
========================================================
수정자/수정일:
수정사유/내역:
========================================================
*/
package risk.search;

import risk.util.StringUtil;

public class siteDataInfo {

    StringUtil  su = new StringUtil();

    public int      miSd_gsn      ;
    public int      miSg_seq      ;
    public String   msSd_name = "";
    
    
    /**
        getter Method
    */
    public int    getSd_gsn ()  { return   this.miSd_gsn   ; }
    public int    getSg_seq ()  { return   this.miSg_seq   ; }
    public String getSd_name()  { return   this.msSd_name  ; }
    
    /**
        setter Method
    */
    public void setSd_gsn ( int     piSd_gsn  )  { this.miSd_gsn  = piSd_gsn  ; }
    public void setSg_seq ( int     piSg_seq  )  { this.miSg_seq  = piSg_seq  ; }
    public void setSd_name( String  psSd_name )  { this.msSd_name = psSd_name ; }
    
    public siteDataInfo() {
        
    }
    
    public siteDataInfo(  int       piSd_gsn ,
                          int       piSg_seq ,
                          String    psSd_name
                        )
    {
        
        this.miSd_gsn  = piSd_gsn  ;
        this.miSg_seq  = piSg_seq  ;
        this.msSd_name = psSd_name ;
        
    }
}