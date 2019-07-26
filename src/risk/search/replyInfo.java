/**
========================================================
주 시 스 템 : RSN
서브 시스템 : 정보검색
프로그램 ID : replyInfo
프로그램 명 : 뎃글 데이터 class
프로그램개요 : 뎃글 Data Beans
작 성 자 : 박정호
작 성 일 : 2006-05-18
========================================================
수정자/수정일:
수정사유/내역:
========================================================
*/
package risk.search;

public class replyInfo {

    public int      riMt_no          ;    //일련번호
    public int      riDb_seq         ;    //SITE_GROUP(SG_SEQ)
    public String   rsHtml           ;    //SITE_DATA(SD_GSN)



    /**
        getter Method
    */
    public int    getMt_no      (){ return this.riMt_no       ; }
    public int    getDb_seq     (){ return this.riDb_seq      ; }
    public String getHtml       (){ return this.rsHtml     ; }


    /**
        setter Method
    */
    public void setMt_no      ( int    piMt_no      ) { this.riMt_no       = piMt_no      ; }
    public void setDb_seq     ( int    piDb_seq     ) { this.riDb_seq      = piDb_seq     ; }
    public void setHtml       ( String psHtml       ) { this.rsHtml        = psHtml       ; }

    public replyInfo(   int    piMt_no      ,
                        int    piDb_seq     ,
                        String psHtml       
                    )
    {
        this.riMt_no       = piMt_no        ;
        this.riDb_seq      = piDb_seq       ;
        this.rsHtml        = psHtml         ;
    }
}