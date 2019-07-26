/**
========================================================
주 시 스 템 : RISK
서브 시스템 : 키워드 정보 조회
프로그램 ID : keywordInfo
프로그램 명 : 키워드 정보 Data Bean
프로그램개요 : 키워드 정보
작 성 자 : 윤석준
작 성 일 : 2006. 04. 11
========================================================
수정자/수정일:
수정사유/내역:
========================================================
*/
package risk.search;

public class keywordInfo {

	public keywordInfo(){} 
	
    private String msK_Seq      = "";       //NUMBER(8)    일련번호
    private String msK_Xp       = "";       //NUMBER(4)    상위 키워드그룹 일련번호
    private String msK_Yp       = "";       //NUMBER(4)    키워드그룹 일련번호
    private String msK_Zp       = "";       //NUMBER(4)    키워드 일련번호
    private String msK_Type     = "";       //NUMBER(4)    분류정보(1그룹,2키워드,11~제외키워드)
    private String msK_Pos      = "";       //NUMBER(4)    키워드그룹 위치정보
    private String msK_Op       = "";       //NUMBER(1)    고유명사 처리정보
    private String msK_Value    = "";       //VARCHAR2(64) 키워드 및 그룹 이름

    public void showValue() {

    }


    /**
        getter Method
    */

    public String getK_Seq      () { return this.msK_Seq  ; }
    public String getK_Xp       () { return this.msK_Xp   ; }
    public String getK_Yp       () { return this.msK_Yp   ; }
    public String getK_Zp       () { return this.msK_Zp   ; }
    public String getK_Type     () { return this.msK_Type ; }
    public String getK_Pos      () { return this.msK_Pos  ; }
    public String getK_Op       () { return this.msK_Op   ; }
    public String getK_Value    () { return this.msK_Value; }

    /**
        setter Method
    */

    public void setK_Seq    ( String psK_Seq   ) { this.msK_Seq     = psK_Seq   ; }
    public void setK_Xp     ( String psK_Xp    ) { this.msK_Xp      = psK_Xp    ; }
    public void setK_Yp     ( String psK_Yp    ) { this.msK_Yp      = psK_Yp    ; }
    public void setK_Zp     ( String psK_Zp    ) { this.msK_Zp      = psK_Zp    ; }
    public void setK_Type   ( String psK_Type  ) { this.msK_Type    = psK_Type  ; }
    public void setK_Pos    ( String psK_Pos   ) { this.msK_Pos     = psK_Pos   ; }
    public void setK_Op     ( String psK_Op    ) { this.msK_Op      = psK_Op    ; }
    public void setK_Value  ( String psK_Value ) { this.msK_Value   = psK_Value ; }


    public keywordInfo( String psK_Seq      ,
                        String psK_Xp       ,
                        String psK_Yp       ,
                        String psK_Zp       ,
                        String psK_Type     ,
                        String psK_Pos      ,
                        String psK_Op       ,
                        String psK_Value    )
    {
        this.msK_Seq    = psK_Seq       ;
        this.msK_Xp     = psK_Xp        ;
        this.msK_Yp     = psK_Yp        ;
        this.msK_Zp     = psK_Zp        ;
        this.msK_Type   = psK_Type      ;
        this.msK_Pos    = psK_Pos       ;
        this.msK_Op     = psK_Op        ;
        this.msK_Value  = psK_Value     ;
    }
}