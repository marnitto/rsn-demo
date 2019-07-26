/**
========================================================
주 시 스 템 : RSN
서브 시스템 : 
프로그램 ID : siteDataInfo
프로그램 명 : 사이트그룹 데이터 class
프로그램개요 : 사이트그룹 Data Beans
작 성 자 : 윤석준
작 성 일 : 2006. 04. 11
========================================================
수정자/수정일:
수정사유/내역:
========================================================
*/
package risk.search;

import risk.util.StringUtil;

public class siteGroupInfo {

    StringUtil  su = new StringUtil();

    public int      miSg_seq      ;
    public String   msSg_name = "";
    
    
    /**
        getter Method
    */
    public int    getSg_seq        ()  { return   this.miSg_seq   ; }
    public String getSd_name       ()  { return   this.msSg_name  ; }
    
    /**
        setter Method
    */
    public void setSg_seq ( int     piSg_seq  )  { this.miSg_seq  = piSg_seq  ; }
    public void setSg_name( String  psSg_name )  { this.msSg_name = psSg_name ; }
    
    public siteGroupInfo() {
        
    }
    
    public siteGroupInfo(  int       piSg_seq ,
                          String    psSg_name
                        )
    {
        
        this.miSg_seq  = piSg_seq  ;
        this.msSg_name = psSg_name ;
        
    }
}