package risk.util;

public class PageIndex {
	
	public static String getPageIndex(int nowpage, int totpage) {
        String rtnStr="";
        int idx = 0;
        int i = 0;
        int idxpage = 0;
        int tmp = 0;
        int tmp2 = 0;
        int tmp3 = 0;
        int nUpdown = 0;

        idx = ( nowpage - 1) / 10 + 1;
        for( i = 1 ; i <= 10 ; i++ )
        {
            idxpage = (idx - 1) * 10 + i;

            if( idxpage != 0 && idxpage <= totpage ) {
                if( idxpage == nowpage ) {
                    if ( i == 1 && idx != 1 ) {
                        tmp = idxpage - 1;
                        rtnStr = ""; //"<FONT size=2>";

                        rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "');\" ";
                        rtnStr += "><img src=\"./images/con_prev.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;&nbsp;";
                        rtnStr += "<B><font color=\"C6120E\">" +  idxpage + "</font></B> &nbsp; ";
                    } else if ( i == 10 ) {
                        tmp = idxpage + 1;
                        tmp2 = idxpage - 1;
                        tmp3 = totpage;
                        rtnStr += "";
                        rtnStr += "<B><font color=\"C6120E\">" +  idxpage + "</font></B> &nbsp; ";

                        if ( totpage >= tmp ) {
                            if ( idx != totpage / 10 ){
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "');\" ";
                            } else {
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "');\" ";
                            }
                           rtnStr += "><img src=\"./images/con_next.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                        }
                    } else {
                        rtnStr += "<B><font color=\"C6120E\">" +  idxpage + "</font></B> &nbsp; ";
                    }

                } else {
                    if ( i == 1 && idx != 1 ) {
                        tmp = idxpage - 1;
                        rtnStr += "";
                        //rtnStr += "<a HREF='" + gourl + "?nowpage=1" + add_tag + "'> ";
                        //rtnStr += "<img src=\"./images/next2.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                        rtnStr += "<a HREF=\"javascript:pageClick('"+ tmp +"');\" ";
                        rtnStr += "><img src=\"./images/con_prev.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;&nbsp;";
                        rtnStr += "<a HREF=\"javascript:pageClick('" + idxpage + "');\"";
                        rtnStr += ">" +  idxpage + "</A> &nbsp; ";
                    } else if ( i == 10 ) {
                        tmp = idxpage + 1;
                        tmp2 = totpage;
                        rtnStr += "";
                        rtnStr += "<a HREF=\"javascript:pageClick('" +  idxpage + "');\" ";
                        rtnStr += ">" +  idxpage + "</A> &nbsp; ";

                        if ( totpage >= tmp ) {
                            if ( idx != totpage / 10) {
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "');\"";
                            } else {
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "');\"";
                            }
                            rtnStr += "><img src=\"./images/con_next.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                            //rtnStr += "<a HREF='" + gourl + "?nowpage=" + tmp2 + add_tag + "&nUpdown=-1'> ";
                            //rtnStr += "<img src=\"./images/prev2.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";

                        }
                    } else {
                        rtnStr += "";
                        rtnStr += "<a HREF=\"javascript:pageClick('" + idxpage + "');\" ";
                        rtnStr += ">" +  idxpage + "</A> &nbsp; ";
                    }
                }
            }
        }

        return rtnStr;
    }
	
	public static String getPageIndex(int nowpage, int totpage,int funcType, String url) {
        String rtnStr="";
        int idx = 0;
        int i = 0;
        int idxpage = 0;
        int tmp = 0;
        int tmp2 = 0;
        int tmp3 = 0;
        int nUpdown = 0;

        idx = ( nowpage - 1) / 10 + 1;
        for( i = 1 ; i <= 10 ; i++ )
        {
            idxpage = (idx - 1) * 10 + i;

            if( idxpage != 0 && idxpage <= totpage ) {
                if( idxpage == nowpage ) {
                    if ( i == 1 && idx != 1 ) {
                        tmp = idxpage - 1;
                        rtnStr = ""; //"<FONT size=2>";

                        rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "','"+url+"',"+funcType+");\" ";
                        rtnStr += "><img src=\"./images/con_prev.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;&nbsp;";
                        rtnStr += "<B><font color=\"C6120E\">" +  idxpage + "</font></B> &nbsp; ";
                    } else if ( i == 10 ) {
                        tmp = idxpage + 1;
                        tmp2 = idxpage - 1;
                        tmp3 = totpage;
                        rtnStr += "";
                        rtnStr += "<B><font color=\"C6120E\">" +  idxpage + "</font></B> &nbsp; ";

                        if ( totpage >= tmp ) {
                            if ( idx != totpage / 10 ){
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "','"+url+"',"+funcType+");\" ";
                            } else {
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "','"+url+"',"+funcType+");\" ";
                            }
                           rtnStr += "><img src=\"./images/con_next.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                        }
                    } else {
                        rtnStr += "<B><font color=\"C6120E\">" +  idxpage + "</font></B> &nbsp; ";
                    }

                } else {
                    if ( i == 1 && idx != 1 ) {
                        tmp = idxpage - 1;
                        rtnStr += "";
                        //rtnStr += "<a HREF='" + gourl + "?nowpage=1" + add_tag + "'> ";
                        //rtnStr += "<img src=\"./images/next2.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                        rtnStr += "<a HREF=\"javascript:pageClick('"+ tmp +"','"+url+"',"+funcType+");\" ";
                        rtnStr += "><img src=\"./images/con_prev.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;&nbsp;";
                        rtnStr += "<a HREF=\"javascript:pageClick('" + idxpage + "','"+url+"',"+funcType+");\"";
                        rtnStr += ">" +  idxpage + "</A> &nbsp; ";
                    } else if ( i == 10 ) {
                        tmp = idxpage + 1;
                        tmp2 = totpage;
                        rtnStr += "";
                        rtnStr += "<a HREF=\"javascript:pageClick('" +  idxpage + "','"+url+"',"+funcType+");\" ";
                        rtnStr += ">" +  idxpage + "</A> &nbsp; ";

                        if ( totpage >= tmp ) {
                            if ( idx != totpage / 10) {
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "','"+url+"',"+funcType+");\"";
                            } else {
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "','"+url+"',"+funcType+");\"";
                            }
                            rtnStr += "><img src=\"./images/con_next.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                            //rtnStr += "<a HREF='" + gourl + "?nowpage=" + tmp2 + add_tag + "&nUpdown=-1'> ";
                            //rtnStr += "<img src=\"./images/prev2.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";

                        }
                    } else {
                        rtnStr += "";
                        rtnStr += "<a HREF=\"javascript:pageClick('" + idxpage + "','"+url+"',"+funcType+");\" ";
                        rtnStr += ">" +  idxpage + "</A> &nbsp; ";
                    }
                }
            }
        }

        return rtnStr;
    }
	
	
	public static String getPageIndex(int nowpage, int totpage, String gourl, String add_tag) {
		ConfigUtil cu = new ConfigUtil();
		String url =cu.getConfig("URL")+"/images/common/";
        String rtnStr="";
        int idx = 0;
        int i = 0;
        int idxpage = 0;
        int tmp = 0;
        int tmp2 = 0;
        int tmp3 = 0;
        int nUpdown = 0;

        if(add_tag == null){
        	add_tag = "";
        }
        idx = (nowpage - 1) / 10 + 1;
        for(i = 1 ; i <= 10 ; i++){
            idxpage = (idx - 1) * 10 + i;
            if(idxpage != 0 && idxpage <= totpage){
                if(idxpage == nowpage){
                    if(i == 1 && idx != 1){
                        tmp = idxpage - 1;
                        rtnStr = "";
                        rtnStr += "<td class=\"img\"><a HREF=\"javascript:pageClick('" + gourl + "?nowpage=" + tmp + add_tag + "&nUpdown=-1');\" ";
                        rtnStr += "><img src=\""+url+"btn_paging_prev.gif\" border=\"0\" align=\"absmiddle\"></a></td>";
                        rtnStr += "<th><B><font color=\"C6120E\">" +  idxpage + "</font></B></th>";
                    }else if( i == 10 ){
                        tmp = idxpage + 1;
                        tmp2 = idxpage - 1;
                        tmp3 = totpage;
                        rtnStr += "";
                        rtnStr += "<th><B><font color=\"C6120E\">" +  idxpage + "</font></B></th>";

                        if ( totpage >= tmp ) {
                            if ( idx != totpage / 10 ){
                                rtnStr += "<td class=\"img\"><a HREF=\"javascript:pageClick('" + gourl + "?nowpage=" + tmp + add_tag + "&nUpdown=1');\" ";
                            } else {
                                rtnStr += "<td class=\"img\"><a HREF=\"javascript:pageClick('" + gourl + "?nowpage=" + tmp + add_tag + "&nUpdown=-1');\" ";
                            }
                            rtnStr += "><img src=\""+url+"btn_paging_next.gif\" border=\"0\" align=\"absmiddle\"></a></td>";
                        }
                    }else{
                        rtnStr += "<th><B><font color=\"C6120E\">" +  idxpage + "</font></B></th>";
                    }
                }else{
                    if(i == 1 && idx != 1){
                        tmp = idxpage - 1;
                        rtnStr += "";
                        rtnStr += "<td class=\"img\"><a HREF=\"javascript:pageClick('" + gourl + "?nowpage=" + tmp + add_tag + "&nUpdown=-1');\" ";
                        rtnStr += "><img src=\""+url+"btn_paging_prev.gif\" border=\"0\" align=\"absmiddle\"></a></td>";
                        rtnStr += "<td><a HREF=\"javascript:pageClick('" + gourl + "?nowpage=" +  idxpage + add_tag + "');\"";
                        rtnStr += ">" +  idxpage + "</a></td>";
                    }else if( i == 10 ){
                        tmp = idxpage + 1;
                        tmp2 = totpage;
                        rtnStr += "";
                        rtnStr += "<td><a HREF=\"javascript:pageClick('" + gourl + "?nowpage=" +  idxpage + add_tag + "');\" ";
                        rtnStr += ">" +  idxpage + "</a></td>";
                        
                        if(totpage >= tmp){
                            if(idx != totpage / 10){
                                rtnStr += "<td class=\"img\"><a HREF=\"javascript:pageClick('" + gourl + "?nowpage=" + tmp + add_tag + "&nUpdown=1');\"";
                            } else {
                                rtnStr += "<td class=\"img\"><a HREF=\"javascript:pageClick('" + gourl + "?nowpage=" + tmp + add_tag + "&nUpdown=-1');\"";
                            }
                            rtnStr += "><img src=\""+url+"btn_paging_next.gif\" border=\"0\" align=\"absmiddle\"></a></td>";
                        }
                    }else{
                        rtnStr += "";
                        rtnStr += "<td><a HREF=\"javascript:pageClick('" + gourl + "?nowpage=" +  idxpage + add_tag + "');\" ";
                        rtnStr += ">" +  idxpage + "</a></td>";
                    }
                }
            }
        }
        return rtnStr;
    }
	
	public static String getPageIndex_DashBoard(int nowpage, int totpage) {
		ConfigUtil cu = new ConfigUtil();
		String url =cu.getConfig("URL")+"/images/dashboard_new/";
		
        String rtnStr="";
        int idx = 0;
        int i = 0;
        int idxpage = 0;
        int tmp = 0;
        int tmp2 = 0;
        int tmp3 = 0;
        int nUpdown = 0;

        idx = ( nowpage - 1) / 10 + 1;
        for( i = 1 ; i <= 10 ; i++ )
        {
            idxpage = (idx - 1) * 10 + i;

            if( idxpage != 0 && idxpage <= totpage ) {
                if( idxpage == nowpage ) {
                    if ( i == 1 && idx != 1 ) {
                        tmp = idxpage - 1;
                        rtnStr = ""; //"<FONT size=2>";

                        rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "');\" ";
                        rtnStr += "><img src=\""+url+"con_prev.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;&nbsp;";
                        rtnStr += "<B><font color=\"C6120E\">" +  idxpage + "</font></B> &nbsp; ";
                    } else if ( i == 10 ) {
                        tmp = idxpage + 1;
                        tmp2 = idxpage - 1;
                        tmp3 = totpage;
                        rtnStr += "";
                        rtnStr += "<B><font color=\"C6120E\">" +  idxpage + "</font></B> &nbsp; ";

                        if ( totpage >= tmp ) {
                            if ( idx != totpage / 10 ){
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "');\" ";
                            } else {
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "');\" ";
                            }
                           rtnStr += "><img src=\""+url+"con_next.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                        }
                    } else {
                        rtnStr += "<B><font color=\"C6120E\">" +  idxpage + "</font></B> &nbsp; ";
                    }

                } else {
                    if ( i == 1 && idx != 1 ) {
                        tmp = idxpage - 1;
                        rtnStr += "";
                        //rtnStr += "<a HREF='" + gourl + "?nowpage=1" + add_tag + "'> ";
                        //rtnStr += "<img src=\"./images/next2.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                        rtnStr += "<a HREF=\"javascript:pageClick('"+ tmp +"');\" ";
                        rtnStr += "><img src=\""+url+"con_prev.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;&nbsp;";
                        rtnStr += "<a HREF=\"javascript:pageClick('" + idxpage + "');\"";
                        rtnStr += ">" +  idxpage + "</A> &nbsp; ";
                    } else if ( i == 10 ) {
                        tmp = idxpage + 1;
                        tmp2 = totpage;
                        rtnStr += "";
                        rtnStr += "<a HREF=\"javascript:pageClick('" +  idxpage + "');\" ";
                        rtnStr += ">" +  idxpage + "</A> &nbsp; ";

                        if ( totpage >= tmp ) {
                            if ( idx != totpage / 10) {
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "');\"";
                            } else {
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "');\"";
                            }
                            rtnStr += "><img src=\""+url+"con_next.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                            //rtnStr += "<a HREF='" + gourl + "?nowpage=" + tmp2 + add_tag + "&nUpdown=-1'> ";
                            //rtnStr += "<img src=\"./images/prev2.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";

                        }
                    } else {
                        rtnStr += "";
                        rtnStr += "<a HREF=\"javascript:pageClick('" + idxpage + "');\" ";
                        rtnStr += ">" +  idxpage + "</A> &nbsp; ";
                    }
                }
            }
        }

        return rtnStr;
    }
	
	public static String getPagination(int nowpage, int totpage, String fn_name) {
		ConfigUtil cu = new ConfigUtil();
		String url =cu.getConfig("URL")+"dashboard/asset/img/";
        String rtnStr="";
        int idx = 0;
        int i = 0;
        int idxpage = 0;
        int tmp = 0;
        int tmp2 = 0;
        int tmp3 = 0;
        int nUpdown = 0;

        idx = (nowpage - 1) / 10 + 1;
        for(i = 1 ; i <= 10 ; i++){
            idxpage = (idx - 1) * 10 + i;
            if(idxpage != 0 && idxpage <= totpage){
                if(idxpage == nowpage){
                    if(i == 1 && idx != 1){
                        tmp = idxpage - 1;
                        rtnStr = "";
                        rtnStr += "<td width=\"18\" class=\"img\"><a HREF=\"javascript:pageClick"+fn_name+"("+ tmp + ");\" ";
                        rtnStr += "><img src='"+url+"num_arrow_L.gif' border=\"0\" align=\"absmiddle\"></a></td>";
                        rtnStr += "<td width=\"1\" bgcolor=\"#E2E2E2\"></td>";
                        rtnStr += "<td width=\"18\" align=\"center\"><B><font color=\"#518fd8\">" +  idxpage + "</font></B></td>";
                    }else if( i == 10 ){
                        tmp = idxpage + 1;
                        tmp2 = idxpage - 1;
                        tmp3 = totpage;
                        rtnStr += "";
                        rtnStr += "<td width=\"18\" align=\"center\"><B><font color=\"#518fd8\">" +  idxpage + "</font></B></td>";

                        if ( totpage >= tmp ) {
                        	rtnStr += "<td width=\"1\" bgcolor=\"#E2E2E2\"></td>";
                            if ( idx != totpage / 10 ){
                                rtnStr += "<td width=\"18\" class=\"img\"><a HREF=\"javascript:pageClick"+fn_name+"("+ tmp + ");\" ";
                            } else {
                                rtnStr += "<td width=\"18\" class=\"img\"><a HREF=\"javascript:pageClick"+fn_name+"("+ tmp + ");\" ";
                            }                            
                            rtnStr += "><img src='"+url+"num_arrow_R.gif' border=\"0\" align=\"absmiddle\"></a></td>";
                        }
                    }else{
                    	//rtnStr += "<td width=\"18\"><img src=\""+url+"num_arrow_L.gif\" alt=\"\" width=\"18\" height=\"15\" /></td>";
                    	rtnStr += "<td width=\"1\" bgcolor=\"#E2E2E2\"></td>";
                    	rtnStr += "<td width=\"18\" align=\"center\"><B><font color=\"#518fd8\">" +  idxpage + "</font></B></td>";
                    }
                }else{
                    if(i == 1 && idx != 1){
                        tmp = idxpage - 1;
                        rtnStr += "";
                        rtnStr += "<td width=\"18\" class=\"img\"><a HREF=\"javascript:pageClick"+fn_name+"("+ tmp + ");\" ";
                        rtnStr += "><img src='"+url+"num_arrow_L.gif' border=\"0\" align=\"absmiddle\"></a></td>";
                        rtnStr += "<td width=\"18\" align=\"center\"><a HREF=\"javascript:pageClick"+fn_name+"(" + idxpage + ");\"";
                        rtnStr += ">" +  idxpage + "</a></td>";
                    }else if( i == 10 ){
                        tmp = idxpage + 1;
                        tmp2 = totpage;
                        //rtnStr += "";
                        rtnStr += "<td width=\"1\" bgcolor=\"#E2E2E2\"></td>";
                        rtnStr += "<td width=\"18\" align=\"center\"><a HREF=\"javascript:pageClick"+fn_name+"(" + idxpage + ");\" ";
                        rtnStr += ">" +  idxpage + "</a></td>";
                        
                        if(totpage >= tmp){                        	
                        	rtnStr += "<td width=\"1\" bgcolor=\"#E2E2E2\"></td>";
                            if(idx != totpage / 10){
                                rtnStr += "<td width=\"18\" class=\"img\" align=\"center\"><a HREF=\"javascript:pageClick"+fn_name+"(" + idxpage + ");\"";
                                rtnStr += "><img src='"+url+"num_arrow_R.gif' border=\"0\" align=\"absmiddle\"></a></td>";
                            } else {
                                rtnStr += "<td width=\"18\" class=\"img\" align=\"center\"><a HREF=\"javascript:pageClick"+fn_name+"(" + idxpage + ");\"";
                                rtnStr += "><img src='"+url+"num_arrow_R.gif' border=\"0\" align=\"absmiddle\"></a></td>";
                            }
                            
                        }
                    }else{	
                        //rtnStr += "";
                        rtnStr += "<td width=\"1\" bgcolor=\"#E2E2E2\"></td>";
                        rtnStr += "<td width=\"18\" align=\"center\"><a HREF=\"javascript:pageClick"+fn_name+"(" + idxpage + ");\" ";
                        rtnStr += ">" +  idxpage + "</a></td>";
                    }
                }
            }
        }
        return rtnStr;
    }
    
}
