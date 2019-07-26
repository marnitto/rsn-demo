package risk.mobile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

import risk.DBconn.DBconn;
import risk.util.ConfigUtil;
import risk.util.DateUtil;
import risk.util.Log;

//알리미 셋팅 정보빈
public class AlimiSettingDao {
	
	DBconn dbconn   = null;
    Statement stmt  = null;
    ResultSet rs    = null;
    StringBuffer sb = null;
    String sQuery   = "";
    
    private int listCnt = 0;
    
    public ArrayList getAlimiSetList(int nowPage, int rowCnt, String Pas_seq, String subInfoIn)
    {
    	ArrayList arrAlimiSetList = new ArrayList();
    	ArrayList arrReceiverList = new ArrayList();
    	AlimiSettingBean asBean = new AlimiSettingBean();
    	AlimiReceiverBean arBean = new AlimiReceiverBean();
    	String as_seq = null;
    	
    	int liststart = 0;
    	int listend = 0;
    	int whereCnt = 0;   	
    	
    	try
    	{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();   
			sb.append(" SELECT COUNT(*) AS TOTAL_CNT FROM ALIMI_SETTING \n");							
			if(!Pas_seq.equals(""))
			{
				if(whereCnt==0)
				{
					sb.append("WHERE ");
				}else{
					sb.append("AND ");
				}
				sb.append("AS_SEQ = "+Pas_seq+" \n");
				whereCnt++;
			}

			//Log.debug(sb.toString());                    
            rs = stmt.executeQuery(sb.toString());            
            if ( rs.next() ) {
            	listCnt  = rs.getInt("TOTAL_CNT");                     
            }
            
          
 		    liststart = (nowPage-1) * rowCnt;
 		    listend = rowCnt;
               
            rs = null;           
            sb = new StringBuffer();
            whereCnt =0;
            
            sb.append("SELECT AS_SEQ                                                      \n");                 
            sb.append("     , AS_TITLE                                                    \n");            
            sb.append("     , AS_CHK                                                      \n");       
            sb.append("     , AS_TYPE                                                     \n");  
            sb.append("     , AS_INFOTYPE                                                 \n"); 
            sb.append("     , K_XPS                                                       \n");
            sb.append("     , K_YPS                                                     \n"); 
            sb.append("     , SG_SEQS                                                     \n");      
            sb.append("     , SD_GSNS                                                     \n");
            sb.append("     , MT_TYPES                                                    \n");
            sb.append("     , AS_SMS_KEY                                                  \n");
            sb.append("     , AS_SMS_EXKEY                                                \n");
            sb.append("     , AS_SMS_TIME                                                 \n");
            sb.append("     , AS_INTERVAL                                                 \n");
            sb.append("     , AS_LAST_SENDTIME                                            \n");
            sb.append("     , AS_SMS_DAY                                                  \n");
            sb.append("     , AS_SMS_STIME                                                \n");
            sb.append("     , AS_SMS_ETIME                                                \n");
            sb.append("     , AS_SAME_CNT                                                 \n");
            sb.append("     , AS_SAME_PERCENT                                             \n");
            sb.append("     , AS_LATER_SEND                                         	  \n");
            sb.append("     , IFNULL(AS_MONITOR_USE,'N') AS AS_MONITOR_USE                \n");
            sb.append("     , AS_MONITOR_MAX_M                                         	  \n");
            sb.append("     , AS_MONITOR_REPEAT_M                                         \n");
            sb.append("     , AS_MONITOR_INSPECTOR                                        \n");
            sb.append("     , AS_SAME_OP                                                 \n");
            sb.append("     , AS_LAST_NUM                                         	  	  \n");
            sb.append("  FROM ALIMI_SETTING                                               \n");
           
            if(!Pas_seq.equals(""))
            sb.append(" WHERE AS_SEQ = "+Pas_seq+"                                        \n");
            
            if(rowCnt>0){
            sb.append(" ORDER BY AS_SEQ DESC                                              \n");
            sb.append(" LIMIT "+liststart+","+listend+"                                   \n");
            }
            
            //System.out.println(sb.toString());
			Log.debug(sb.toString() );			
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next())
			{
				asBean = new AlimiSettingBean();
				asBean.setAs_seq(rs.getString(1));
				asBean.setAs_title(rs.getString(2));
				asBean.setAs_chk(rs.getString(3));
				asBean.setAs_type(rs.getString(4));
				asBean.setAs_infotype(rs.getString(5));
				asBean.setKg_xps(rs.getString(6));
				asBean.setKg_yps(rs.getString(7));
				asBean.setSg_seqs(rs.getString(8));
				asBean.setSd_gsns(rs.getString(9));
				asBean.setMt_types(rs.getString(10));
				asBean.setAs_sms_key(rs.getString(11));
				asBean.setAs_sms_exkey(rs.getString(12));
				asBean.setAs_sms_time(rs.getString(13));
				asBean.setAs_interval(rs.getString(14));
				asBean.setAs_last_sendtime(rs.getString(15));
				asBean.setAs_sms_day(rs.getString(16));
				asBean.setAs_sms_stime(rs.getString(17));
				asBean.setAs_sms_etime(rs.getString(18));
				asBean.setAs_same_cnt(rs.getInt(19));
				asBean.setAs_same_percent(rs.getInt(20));
				asBean.setAs_later_send(rs.getString(21));
				asBean.setAs_monitor_use(rs.getString("AS_MONITOR_USE"));
				asBean.setAs_monitor_max_m(rs.getString("AS_MONITOR_MAX_M"));
				asBean.setAs_monitor_repeat_m(rs.getString("AS_MONITOR_REPEAT_M"));
				asBean.setAs_monitor_inspector(rs.getString("AS_MONITOR_INSPECTOR"));
				asBean.setAs_same_op(rs.getInt("AS_SAME_OP"));
				asBean.setAs_last_num(rs.getString("AS_LAST_NUM"));
				arrAlimiSetList.add(asBean);
			}
			
			if(subInfoIn.equals("Y"))
			{
				rs = null;           
	            sb = new StringBuffer();
	            whereCnt =1;
	            as_seq ="";
	            /*
	            sb.append("SELECT                                              \n");
	            sb.append("		A.AS_SEQ,	                                   \n");
	            sb.append("	    C.AB_SEQ,                                      \n");
	            sb.append("	    C.AB_NAME,                                     \n");
	            sb.append("	    C.AB_DEPT,                                     \n");
	            sb.append("	    C.AB_POS,                                      \n");
	            sb.append("	    C.AB_MOBILE,                                   \n");
	            sb.append("		C.AB_MAIL,                                     \n");
	            sb.append("		C.K_XP,                                        \n");
	            sb.append("		C.SG_SEQ,                                      \n");
	            sb.append("		C.MT_TYPE,                                     \n");
	            sb.append("		C.AB_ISSUE_DEPT,                               \n");
	            sb.append("		C.AB_ISSUE_RECEIVECHK,                         \n");
	            sb.append("		C.AB_REPORT_DAY_CHK,                           \n");
	            sb.append("		C.AB_REPORT_WEEK_CHK,                          \n");
	            sb.append("		C.AB_SMS_LIMIT                                \n");
	            sb.append("FROM IMS_ALIMI_SETTING A, IMS_ALIMI_RECEIVER B, IMS_ADDRESS_BOOK C \n");
	            sb.append("WHERE                                                  \n");
	            sb.append("	   A.AS_SEQ = B.AS_SEQ	                           \n");
	            sb.append("	   AND B.AB_SEQ = C.AB_SEQ	                       \n");
	            */
	            
	            
	            sb.append("SELECT A.AS_SEQ	                    \n");                
    			sb.append("     , C.AB_SEQ                      \n");                         
    			sb.append("     , C.AB_NAME                     \n");                         
    			sb.append("     , C.AB_DEPT                     \n");                         
    			sb.append("     , C.AB_POSITION                 \n");                              
    			sb.append("     , C.AB_MOBILE                   \n");                         
    			sb.append("     , C.AB_MAIL                     \n");                     
    			sb.append("     , NULL AS K_XP                  \n");                           
    			sb.append("     , NULL AS SG_SEQ                \n");                           
    			sb.append("     , NULL AS MT_TYPE               \n");                           
    			sb.append("     , NULL AS AB_ISSUE_DEPT         \n");                           
    			sb.append("     , 0 AS AB_ISSUE_RECEIVECHK      \n");                        
    			sb.append("     , C.AB_REPORT_DAY_CHK           \n");                     
    			sb.append("     , C.AB_REPORT_WEEK_CHK          \n");                     
    			sb.append("     , NULL AS AB_SMS_LIMIT          \n");                            
    			sb.append("  FROM ALIMI_SETTING  A              \n");
    			sb.append("     , ALIMI_RECEIVER B              \n");
    			sb.append("     , ADDRESS_BOOK   C              \n");
    			sb.append(" WHERE A.AS_SEQ = B.AS_SEQ	        \n");                       
    			sb.append("   AND B.AB_SEQ = C.AB_SEQ           \n");
    			
	            if(!Pas_seq.equals(""))
				{
					if(whereCnt==0)
					{
						sb.append("WHERE ");
					}else{
						sb.append("AND ");
					}
					sb.append("A.AS_SEQ = "+Pas_seq+" \n");
					whereCnt++;
				}
	            sb.append("ORDER BY A.AS_SEQ ASC	                          \n");
	            
	            
	            
	            //System.out.println(sb.toString());
				Log.debug(sb.toString() );				
				rs = stmt.executeQuery(sb.toString());
				
	            
	            while(rs.next())
				{
	            	if (!as_seq.equals(rs.getString(1))) {
	            		//System.out.println("arrReceiverList:"+arrReceiverList.size());
	            		arrAlimiSetList = addArrReceiber(arrAlimiSetList, as_seq, arrReceiverList);
	            		arrReceiverList = new ArrayList();
					}
	            	as_seq = rs.getString(1);
	            	arBean = new AlimiReceiverBean();
	            	arBean.setAb_name( rs.getString(3));
	            	arBean.setAb_dept( rs.getString(4));
	            	arBean.setAb_pos( rs.getString(5));
	            	arBean.setAb_mobile( rs.getString(6));
	            	arBean.setAb_mail( rs.getString(7));
	            	arBean.setK_xp( rs.getString(8));
	            	arBean.setSg_seq( rs.getString(9));
	            	arBean.setMt_type( rs.getString(10));
	            	arBean.setAb_issue_dept( rs.getString(11));
	            	arBean.setAb_issue_receivechk( rs.getString(12));
	            	arBean.setAb_report_day_chk( rs.getString(13));
	            	arBean.setAb_report_week_chk( rs.getString(14));
	            	arBean.setAb_sms_limit( rs.getString(15));           	
	            	arrReceiverList.add(arBean);
				}
	            //System.out.println("arrReceiverList:"+arrReceiverList.size());
	            arrAlimiSetList = addArrReceiber(arrAlimiSetList, as_seq, arrReceiverList);
			}
			
			//System.out.println("arrAlimiSetList:"+arrAlimiSetList.size());
			
			
    	 } catch (SQLException ex ) {
             Log.writeExpt(ex, ex.getMessage() );

         } catch (Exception ex ) {
             Log.writeExpt(ex.getMessage());

         } finally {
             try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
             try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
             try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
         }   
         
         return arrAlimiSetList;
    }
    
    
    
    public ArrayList getAlimiSetList(int nowPage 
    								 ,int rowCnt 
    								 ,String Pas_seq     //seq
    								 ,String pAsTitle    //제목
    								 ,String pAsChk      //사용 여부
    								 ,String pAsType     //1:메일, 2:SMS, 3:MMS, 4:수동 메일, 5:수동 SMS
    								 ,String pAsInfoType //1:일반 2:포털
    								 )
    {
    	ArrayList arrAlimiSetList = new ArrayList();
    	ArrayList arrReceiverList = new ArrayList();
    	AlimiSettingBean asBean = new AlimiSettingBean();
    	AlimiReceiverBean arBean = new AlimiReceiverBean();
    	String as_seq = null;
    	
    	int liststart = 0;
    	int listend = 0;
    	int whereCnt = 0;   	
    	
    	try
    	{
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		stmt = dbconn.createStatement();
    		
    		sb = new StringBuffer();   
    		sb.append(" SELECT COUNT(0) AS TOTAL_CNT FROM ALIMI_SETTING \n");							
    		sb.append(" WHERE AS_TITLE LIKE '%"+pAsTitle+"%' \n");							
    		if(Pas_seq.length()>0)
    		{
    			sb.append(" AND AS_SEQ = "+Pas_seq+" \n");
    		}
    		if(pAsChk.length()>0)
    		{
    			sb.append(" AND AS_CHK = "+pAsChk+" \n");
    		}
    		if(pAsType.length()>0)
    		{
    			sb.append(" AND AS_TYPE IN ("+pAsType+") \n");
    		}
    		if(pAsInfoType.length()>0)
    		{
    			sb.append(" AND AS_INFOTYPE = "+pAsInfoType+" \n");
    		}
    		
    		
    		
    		//Log.debug(sb.toString());
    		System.out.println(sb.toString());
    		rs = stmt.executeQuery(sb.toString());            
    		if ( rs.next() ) {
    			listCnt  = rs.getInt("TOTAL_CNT");                     
    		}
    		
    		
    		liststart = (nowPage-1) * rowCnt + 1;
    		listend = nowPage * rowCnt;
    		
    		rs = null;           
    		sb = new StringBuffer();
    		whereCnt =0;
    		
    		sb.append("SELECT  B.AS_SEQ                                                      \n");
    		sb.append("       ,B.AS_TITLE                                                    \n");
    		sb.append("       ,B.AS_CHK                                                      \n");
    		sb.append("       ,B.AS_TYPE                                                     \n");
    		sb.append("       ,B.AS_INFOTYPE                                                     \n");
    		sb.append("       ,B.K_XPS                                                       \n");
    		sb.append("       ,B.SG_SEQS                                                     \n");
    		sb.append("       ,B.SD_GSNS                                                     \n");
    		sb.append("       ,B.MT_TYPES                                                    \n");
    		sb.append("       ,B.AS_SMS_KEY                                                  \n");
    		sb.append("       ,B.AS_SMS_EXKEY                                                \n");
    		sb.append("       ,B.AS_SMS_TIME                                                 \n");
    		sb.append("       ,B.AS_INTERVAL                                                 \n");
    		sb.append("       ,B.AS_LAST_SENDTIME                                            \n");
    		sb.append("       ,B.AS_SMS_DAY                                            \n");
    		sb.append("       ,B.AS_SMS_STIME                                            \n");
    		sb.append("       ,B.AS_SMS_ETIME                                            \n");
    		sb.append("       ,B.AS_SAME_CNT                                            \n");
    		sb.append("FROM                                                                  \n");
    		sb.append("(                                                                     \n");
    		sb.append("	SELECT  A.* ,ROW_NUMBER() OVER(ORDER BY AS_SEQ DESC) AS NUM    \n");
    		sb.append("	FROM                                                              \n");
    		sb.append("	(                                                                 \n");
    		sb.append("		SELECT *                                                      \n");
    		sb.append("		FROM ALIMI_SETTING                                            \n");
    		sb.append(" 	WHERE AS_TITLE LIKE '%"+pAsTitle+"%' \n");							
    		if(Pas_seq.length()>0)
    		{
    			sb.append(" AND AS_SEQ = "+Pas_seq+" \n");
    		}
    		if(pAsChk.length()>0)
    		{
    			sb.append(" AND AS_CHK = "+pAsChk+" \n");
    		}
    		if(pAsType.length()>0)
    		{
    			sb.append(" AND AS_TYPE IN ( "+pAsType+" ) \n");
    		}
    		if(pAsInfoType.length()>0)
    		{
    			sb.append(" AND AS_INFOTYPE = "+pAsInfoType+" \n");
    		}
    		sb.append("	)A                                                                \n");
    		sb.append(")B                                                                    \n");
    		if(rowCnt>0)
    		{
    			sb.append("WHERE                                                                 \n");          
    			nowPage--;
    			sb.append("B.NUM BETWEEN "+liststart+" AND "+listend+"	 \n");
    		}
    		
    		
    		System.out.println(sb.toString());
    		//Log.debug(sb.toString() );			
    		rs = stmt.executeQuery(sb.toString());
    		
    		while(rs.next())
    		{
    			asBean = new AlimiSettingBean();
    			asBean.setAs_seq(rs.getString(1));
    			asBean.setAs_title(rs.getString(2));
    			asBean.setAs_chk(rs.getString(3));
    			asBean.setAs_type(rs.getString(4));
    			asBean.setAs_infotype(rs.getString(5));
    			asBean.setKg_xps(rs.getString(6));
    			asBean.setSg_seqs(rs.getString(7));
    			asBean.setSd_gsns(rs.getString(8));
    			asBean.setMt_types(rs.getString(9));
    			asBean.setAs_sms_key(rs.getString(10));
    			asBean.setAs_sms_exkey(rs.getString(11));
    			asBean.setAs_sms_time(rs.getString(12));
    			asBean.setAs_interval(rs.getString(13));
    			asBean.setAs_last_sendtime(rs.getString(14));
    			asBean.setAs_sms_day(rs.getString(15));
    			asBean.setAs_sms_stime(rs.getString(16));
    			asBean.setAs_sms_etime(rs.getString(17));
    			asBean.setAs_same_cnt(rs.getInt(18));
    			arrAlimiSetList.add(asBean);
    		}
    		
    		if(pAsChk.equals("1"))
    		{
    			rs = null;           
    			sb = new StringBuffer();
    			whereCnt =1;
    			as_seq ="";
    			
    			sb.append("SELECT                                              \n");
    			sb.append("		A.AS_SEQ,	                                   \n");
    			sb.append("	    C.AB_SEQ,                                      \n");
    			sb.append("	    C.AB_NAME,                                     \n");
    			sb.append("	    C.AB_DEPT,                                     \n");
    			sb.append("	    C.AB_POS,                                      \n");
    			sb.append("	    C.AB_MOBILE,                                   \n");
    			sb.append("		C.AB_MAIL,                                     \n");
    			sb.append("		C.K_XP,                                        \n");
    			sb.append("		C.SG_SEQ,                                      \n");
    			sb.append("		C.MT_TYPE,                                     \n");
    			sb.append("		C.AB_ISSUE_DEPT,                               \n");
    			sb.append("		C.AB_ISSUE_RECEIVECHK,                         \n");
    			sb.append("		C.AB_REPORT_DAY_CHK,                           \n");
    			sb.append("		C.AB_REPORT_WEEK_CHK,                          \n");
    			sb.append("		C.AB_SMS_LIMIT                                \n");
    			sb.append("FROM ALIMI_SETTING A, ALIMI_RECEIVER B, ADDRESS_BOOK C \n");
    			sb.append("WHERE                                                  \n");
    			sb.append("	   A.AS_SEQ = B.AS_SEQ	                           \n");
    			sb.append("	   AND B.AB_SEQ = C.AB_SEQ	                       \n");
    			sb.append("	   AND C.AB_ACTIVE = 1	                       \n");
    			
    			
    			if(!Pas_seq.equals(""))
    			{
    				if(whereCnt==0)
    				{
    					sb.append("WHERE ");
    				}else{
    					sb.append("AND ");
    				}
    				sb.append("A.AS_SEQ = "+Pas_seq+" \n");
    				whereCnt++;
    			}
    			sb.append("ORDER BY A.AS_SEQ ASC	                          \n");
    			
    			
    			
    			//System.out.println(sb.toString());
    			Log.debug(sb.toString() );				
    			rs = stmt.executeQuery(sb.toString());
    			
    			
    			while(rs.next())
    			{
    				if (!as_seq.equals(rs.getString(1))) {
    					//System.out.println("arrReceiverList:"+arrReceiverList.size());
    					arrAlimiSetList = addArrReceiber(arrAlimiSetList, as_seq, arrReceiverList);
    					arrReceiverList = new ArrayList();
    				}
    				as_seq = rs.getString(1);
    				arBean = new AlimiReceiverBean();
    				arBean.setAb_seq(rs.getString(2));
    				arBean.setAb_name( rs.getString(3));
    				arBean.setAb_dept( rs.getString(4));
    				arBean.setAb_pos( rs.getString(5));
    				arBean.setAb_mobile( rs.getString(6));
    				arBean.setAb_mail( rs.getString(7));
    				arBean.setK_xp( rs.getString(8));
    				arBean.setSg_seq( rs.getString(9));
    				arBean.setMt_type( rs.getString(10));
    				arBean.setAb_issue_dept( rs.getString(11));
    				//arBean.setAb_sms_receivechk( rs.getString(12));
    				arBean.setAb_issue_receivechk( rs.getString(12));
    				arBean.setAb_report_day_chk( rs.getString(13));
    				arBean.setAb_report_week_chk( rs.getString(14));
    				arBean.setAb_sms_limit( rs.getString(15));
    				//arBean.setAb_report_month_chk( rs.getString(17));	            	
    				arrReceiverList.add(arBean);
    			}
    			//System.out.println("arrReceiverList:"+arrReceiverList.size());
    			arrAlimiSetList = addArrReceiber(arrAlimiSetList, as_seq, arrReceiverList);
    		}
    		
    		//System.out.println("arrAlimiSetList:"+arrAlimiSetList.size());
    		
    		
    	} catch (SQLException ex ) {
    		Log.writeExpt(ex, ex.getMessage() );
    		
    	} catch (Exception ex ) {
    		Log.writeExpt(ex.getMessage());
    		
    	} finally {
    		try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
    		try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
    		try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
    	}   
    	
    	return arrAlimiSetList;
    }
    
    //리포트 셋 빈의 리시버 어레이 추가
	public ArrayList addArrReceiber(ArrayList paArrInsert, String as_seq, ArrayList paArrData){
		int i=0;
		AlimiSettingBean asBean = new AlimiSettingBean();
    	for(i=0; i<paArrInsert.size(); i++) {
    		asBean = new AlimiSettingBean();
    		asBean = (AlimiSettingBean) paArrInsert.get(i);
    		if (asBean.getAs_seq().equals(as_seq)) {
    			/*
    			System.out.println("i:"+i);
    			for(int j =0;j<paArrData.size();j++)
    			{
    				AlimiReceiverBean arBean = new AlimiReceiverBean();
    				arBean = (AlimiReceiverBean)paArrData.get(i);
    				System.out.println("name:"+arBean.getAb_name());
    			}
    			*/
    			asBean.setArrReceiver(paArrData);
    			paArrInsert.set(i, asBean);
    		}
    	}
    	return paArrInsert;
	}
    
    public int getListCnt()
    {
    	return this.listCnt;
    }
    public int insertAlimisSet(AlimiSettingBean asBean, String ab_seqs)
    {
    	return insertAlimisSet(asBean, ab_seqs , "");
    }
    //
    public int insertAlimisSet(AlimiSettingBean asBean, String ab_seqs, String gm_seq)
    {
    	int result = 0;
    	
    	ConfigUtil cu = new ConfigUtil();
    	
    	String dbTranferNo = null;
    	
    	if(asBean.getAs_infotype().equals("1"))
    	{
    		dbTranferNo = cu.getConfig("LocalDBTranferNo");
    	}else{
    		dbTranferNo = cu.getConfig("PORTALTRANNO");
    	}    	
    	
    	//dbTranferNo = "1"; //서버 1번밖에 없어서 하드 코딩(진솔)    	
    	
    	String startMtNo = "";
    	String as_seq = null;    	
    	ArrayList arrSetReceiver = new ArrayList();
    	ArrayList arrSetBrand = new ArrayList();
    	String[] arrAbSeq = null;
    	
    	if(ab_seqs!=null && !ab_seqs.equals(""))
    	{
    		arrAbSeq = ab_seqs.trim().split(",");
    	}        
    
    	try
    	{
    		
    		if("4".equals(asBean.getAs_type())){		//포탈 TOP일 경우
    			startMtNo = getMaxTopNo(dbTranferNo);
    		}else{
    			startMtNo = getMaxMtNo(dbTranferNo);
    		}
    		
    		
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append(" INSERT INTO ALIMI_SETTING      \n");
			sb.append(" 	(                          \n");
			sb.append(" 	AS_TITLE,                  \n");
			sb.append(" 	AS_CHK,                    \n");
			sb.append(" 	AS_TYPE,                   \n");
			sb.append(" 	AS_INFOTYPE,                   \n");
			sb.append(" 	K_XPS,                     \n");
			sb.append(" 	K_YPS,                   \n");
			sb.append(" 	SG_SEQS,                   \n");
			sb.append(" 	SD_GSNS,                   \n");
			sb.append(" 	MT_TYPES,                  \n");
			sb.append(" 	AS_SMS_KEY,                \n");
			sb.append(" 	AS_SMS_EXKEY,              \n");
			sb.append(" 	AS_SMS_TIME,              \n");
			sb.append(" 	AS_INTERVAL,               \n");	
			sb.append(" 	AS_LAST_SENDTIME,           \n");
			sb.append(" 	AS_LAST_NUM		           \n");
			sb.append(" 	,AS_SMS_DAY		           \n");
			sb.append(" 	,AS_SMS_STIME		           \n");
			sb.append(" 	,AS_SMS_ETIME		           \n");
			sb.append(" 	,AS_SAME_OP			           \n");
			sb.append(" 	,AS_SAME_CNT		           \n");
			sb.append(" 	,AS_SAME_PERCENT		           \n");
			sb.append(" 	,AS_LATER_SEND		       \n");
			sb.append(" 	,AS_MONITOR_USE		       \n");
			sb.append(" 	,AS_MONITOR_MAX_M		   \n");
			sb.append(" 	,AS_MONITOR_REPEAT_M	   \n");
			sb.append(" 	,AS_MONITOR_INSPECTOR	   \n");
			sb.append(" 	,AS_MONITOR_SEND_CNT	   \n");
			sb.append(" 	)                          \n");
			sb.append(" VALUES                         \n");
			sb.append(" 	(                          \n");
			sb.append(" 	'"+asBean.getAs_title()+"',                 \n");
			sb.append(" 	"+asBean.getAs_chk()+",                   \n");
			sb.append(" 	"+asBean.getAs_type()+",                  \n");
			sb.append(" 	"+asBean.getAs_infotype()+",                  \n");
			sb.append(" 	'"+asBean.getKg_xps()+"',                    \n");
			sb.append(" 	'"+asBean.getKg_yps()+"',                    \n");
			sb.append(" 	'"+asBean.getSg_seqs()+"',               \n");
			sb.append(" 	'"+asBean.getSd_gsns()+"',               \n");
			sb.append(" 	'"+asBean.getMt_types()+"',                  \n");
			sb.append(" 	'"+asBean.getAs_sms_key()+"',                 \n");			
			sb.append(" 	'"+asBean.getAs_sms_exkey()+"',             \n");
			sb.append(" 	'"+asBean.getAs_sms_time()+"',             \n");
			sb.append(" 	"+asBean.getAs_interval()+",              \n");
			sb.append(" 	NOW(),              \n");
			sb.append(" 	"+startMtNo+"              \n");
			sb.append(" 	,'"+asBean.getAs_sms_day()+"'             \n");
			sb.append(" 	,'"+asBean.getAs_sms_stime()+"'             \n");
			sb.append(" 	,'"+asBean.getAs_sms_etime()+"'             \n");
			sb.append(" 	,"+asBean.getAs_same_op()+"             \n");
			sb.append(" 	,"+asBean.getAs_same_cnt()+"             \n");
			sb.append(" 	,"+asBean.getAs_same_percent()+"             \n");
			sb.append(" 	,'"+asBean.getAs_later_send()+"'            \n");
			sb.append(" 	,'"+asBean.getAs_monitor_use()+"'           \n");
			sb.append(" 	,'"+asBean.getAs_monitor_max_m()+"'         \n");
			sb.append(" 	,'"+asBean.getAs_monitor_repeat_m()+"'      \n");
			sb.append(" 	,'"+asBean.getAs_monitor_inspector()+"'		\n");
			sb.append(" 	,'"+asBean.getAs_monitor_send_cnt()+"'      \n");
			sb.append(" 	)                          \n");			
			Log.debug(sb.toString() );
			
			result =  stmt.executeUpdate(sb.toString());			
			
			if(result==1)
			{
				
				sb = new StringBuffer();
				sb.append("SELECT MAX(AS_SEQ) AS AS_SEQ\n");
				sb.append("FROM ALIMI_SETTING \n");
				
				Log.debug(sb.toString() );
						
				rs = stmt.executeQuery(sb.toString());
				while(rs.next())
				{
					as_seq = rs.getString("AS_SEQ");
				}
				
				
				/*
				sb = new StringBuffer();
				sb.append("INSERT INTO ALIMI_FILTER(AS_SEQ, MT_NO) \n");
				sb.append("		  VALUES("+as_seq+","+startMtNo+") \n");
				
				Log.debug(sb.toString());
				result =  stmt.executeUpdate(sb.toString());
				*/
				
				if(arrAbSeq!=null)
		    	{
					for(int i=0;i<arrAbSeq.length;i++)
					{
						sb = new StringBuffer();
						sb.append("INSERT INTO ALIMI_RECEIVER(AS_SEQ,AB_SEQ) \n");
						sb.append("		  VALUES("+as_seq+","+arrAbSeq[i]+") \n");
						
						Log.debug(sb.toString() );
						result =  stmt.executeUpdate(sb.toString());
					}
				}
				
				//이메일 세팅
				if(!gm_seq.equals("")){
					sb = new StringBuffer();   
					sb.append("UPDATE GMAIL_GROUP                  \n");
					sb.append("SET  AS_SEQ = "+as_seq+"                               \n");
					sb.append("WHERE GM_SEQ = "+gm_seq+"                                \n");
					Log.debug(sb.toString() );			
					result = stmt.executeUpdate(sb.toString());
				}
				
			}
			
			
    	 } catch (SQLException ex ) {
             Log.writeExpt(ex, ex.getMessage() );

         } catch (Exception ex ) {
             Log.writeExpt(ex.getMessage());

         } finally {
        	 try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
     		try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
     		try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }            
         }
    	 
         return result;    
    }
    
    public String getMaxMtNo(String piNo)
	{	
	 
		String lastNo = "";	  
		try 
		{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			rs = null;
			sb = new StringBuffer();
			sb.append("SELECT MD_SEQ FROM META ORDER BY MD_SEQ DESC LIMIT 1 \n");
			
			Log.debug(sb.toString() );
			rs = stmt.executeQuery(sb.toString());
			if (rs.next()) {
				lastNo = rs.getString("MD_SEQ");
			}	
			   
		}catch (SQLException ex ) {
            Log.writeExpt(ex, ex.getMessage() );

        } catch (Exception ex ) {
            Log.writeExpt(ex.getMessage());

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
     		try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
     		try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }  
        } 
		return lastNo;
	}
    
    public String getMaxTopNo(String piNo)
	{	
	 
		String lastNo = "";	  
		try 
		{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			rs = null;
			sb = new StringBuffer();
			sb.append("SELECT T_SEQ FROM TOP ORDER BY T_SEQ DESC LIMIT 1 \n");
			
			Log.debug(sb.toString() );
			rs = stmt.executeQuery(sb.toString());
			if (rs.next()) {
				lastNo = rs.getString("T_SEQ");
			}	
			   
		}catch (SQLException ex ) {
            Log.writeExpt(ex, ex.getMessage() );

        } catch (Exception ex ) {
            Log.writeExpt(ex.getMessage());

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
     		try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
     		try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }  
        } 
		return lastNo;
	}
    
    public int updateReportSet(AlimiSettingBean asBean, String ab_seqs)
    {
    	int result = 0;
    	String as_seq = null;
    	ArrayList arrSetReceiver = new ArrayList();
    	ArrayList arrSetBrand = new ArrayList();
    	String[] arrAbSeq = null;
    	String startMtNo = "";
    	
    	ConfigUtil cu = new ConfigUtil();
    	String dbTranferNo = null;
    	
    	
    	//if(asBean.getAs_infotype().equals("1"))
    	//{
    	//	dbTranferNo = cu.getConfig("LocalDBTranferNo");
    	//}else{
    	//	dbTranferNo = cu.getConfig("PORTALTRANNO");
    	//}    	
    	
    	//dbTranferNo = "1"; //하드코딩 <table에 1row 밖에 없음. >
    	
    	if(ab_seqs!=null && !ab_seqs.equals(""))
    	{    		
    		arrAbSeq = ab_seqs.trim().split(",");
    	}        
    
    	try
    	{
    		//TODO 
    		//startMtNo = getMaxMtNo(dbTranferNo);
    		startMtNo = asBean.getAs_last_num();
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("UPDATE ALIMI_SETTING                                     \n");
			sb.append("SET                                                  \n");
			sb.append("	AS_TITLE = '"+asBean.getAs_title()+"',                                \n");
			sb.append("	AS_CHK = "+asBean.getAs_chk()+",                                    \n");
			sb.append("	AS_TYPE = "+asBean.getAs_type()+",                                  \n");
			sb.append("	AS_INFOTYPE = "+asBean.getAs_infotype()+",                                  \n");
			sb.append("	K_XPS = '"+asBean.getKg_xps()+"',                                      \n");
			sb.append("	K_YPS = '"+asBean.getKg_yps()+"',                                      \n");
			sb.append("	SG_SEQS = '"+asBean.getSg_seqs()+"',                                  \n");
			sb.append("	SD_GSNS = '"+asBean.getSd_gsns()+"',                                  \n");
			sb.append("	MT_TYPES = '"+asBean.getMt_types()+"',                                \n");
			sb.append("	AS_SMS_KEY = '"+asBean.getAs_sms_key()+"',                            \n");
			sb.append("	AS_SMS_EXKEY = '"+asBean.getAs_sms_exkey()+"',                        \n");
			sb.append("	AS_SMS_TIME = '"+asBean.getAs_sms_time()+"',                          \n");
			sb.append("	AS_INTERVAL = "+asBean.getAs_interval()+",                          \n");
			sb.append("	AS_LAST_SENDTIME = NOW(),                          \n");
			sb.append("	AS_LAST_NUM = "+startMtNo+"                          \n");
			sb.append("	,AS_SMS_DAY = '"+asBean.getAs_sms_day()+"'                          \n");
			sb.append("	,AS_SMS_STIME = '"+asBean.getAs_sms_stime()+"'                          \n");
			sb.append("	,AS_SMS_ETIME = '"+asBean.getAs_sms_etime()+"'                          \n");
			sb.append("	,AS_SAME_OP = "+asBean.getAs_same_op()+"                          \n");
			sb.append("	,AS_SAME_CNT = "+asBean.getAs_same_cnt()+"                          \n");
			sb.append("	,AS_SAME_PERCENT = "+asBean.getAs_same_percent()+"                          \n");
			sb.append("	,AS_LATER_SEND = '"+asBean.getAs_later_send()+"'                   \n");
			sb.append("	,AS_MONITOR_USE = '"+asBean.getAs_monitor_use()+"'                 \n");
			sb.append("	,AS_MONITOR_MAX_M = "+asBean.getAs_monitor_max_m()+"               \n");
			sb.append("	,AS_MONITOR_REPEAT_M = "+asBean.getAs_monitor_repeat_m()+"         \n");
			sb.append("	,AS_MONITOR_INSPECTOR = '"+asBean.getAs_monitor_inspector()+"'     \n");
			sb.append("	,AS_MONITOR_SEND_CNT = '"+asBean.getAs_monitor_send_cnt()+"'       \n");
			sb.append("WHERE AS_SEQ = "+asBean.getAs_seq()+"                               \n");	
			
			Log.debug(sb.toString() );
			
			result =  stmt.executeUpdate(sb.toString());
			
			
			/*
			sb = new StringBuffer();
			sb.append("UPDATE ALIMI_FILTER SET MT_NO = "+startMtNo+" \n");
			sb.append("WHERE AS_SEQ = "+asBean.getAs_seq()+" \n");
			
			Log.debug(sb.toString());
			result =  stmt.executeUpdate(sb.toString());
			*/
			
			if(result==1)
			{
				sb = new StringBuffer();
				sb.append("DELETE FROM ALIMI_RECEIVER \n");
				sb.append("WHERE AS_SEQ = "+asBean.getAs_seq()+" \n");
				
				Log.debug(sb.toString() );
				result =  stmt.executeUpdate(sb.toString());
				
				if(arrAbSeq!=null)
		    	{
					//System.out.println("1234");
					for(int i=0;i<arrAbSeq.length;i++)
					{
						sb = new StringBuffer();
						sb.append("INSERT INTO ALIMI_RECEIVER(AS_SEQ,AB_SEQ) \n");
						sb.append("		  VALUES("+asBean.getAs_seq()+","+arrAbSeq[i]+" ) \n");
						Log.debug(sb.toString() );
						
						result =  stmt.executeUpdate(sb.toString());
					}
		    	}
				
			}
			
    	 } catch (SQLException ex ) {
             Log.writeExpt(ex, ex.getMessage() );

         } catch (Exception ex ) {
             Log.writeExpt(ex.getMessage());

         } finally {
             try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
             try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
             try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
         }
    	 
         return result;    
    }
    
    public int deleteReportSet(String as_seqs)
    {
    	int result = 0;
    	String as_seq = null;
    	ArrayList arrSetReceiver = new ArrayList();
    	ArrayList arrSetBrand = new ArrayList();
    	String[] arrAbSeq = null;
    	    
    	try
    	{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			
			sb.append("DELETE FROM ALIMI_SETTING                                     \n");			
			sb.append("WHERE AS_SEQ IN ( "+as_seqs+" )                                  \n");	
			
			Log.debug(sb.toString() );			
			result =  stmt.executeUpdate(sb.toString());			
			
			if(result==1)
			{
				sb = new StringBuffer();
				sb.append("DELETE FROM ALIMI_RECEIVER \n");
				sb.append("WHERE AS_SEQ IN  ("+as_seqs+") \n");
				
				result =  stmt.executeUpdate(sb.toString());
				/*
				sb = new StringBuffer();
				sb.append("DELETE FROM ALIMI_FILTER \n");
				sb.append("WHERE AS_SEQ IN  ("+as_seqs+") \n");
				
				result =  stmt.executeUpdate(sb.toString());
				*/
			}
			
    	 } catch (SQLException ex ) {
             Log.writeExpt(ex, ex.getMessage() );

         } catch (Exception ex ) {
             Log.writeExpt(ex.getMessage());

         } finally {
             try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
             try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
             try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
         }
    	 
         return result;    
    }
    
    //설정된 수신자 번호 가져오기.
    public String getReceiverSeq(String as_seq)
    {
    	String result = "";
    	AlimiReceiverBean arBean = new AlimiReceiverBean();
    	
		try {			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();
			sb.append("SELECT A.AB_SEQ \n");		
			sb.append("FROM ALIMI_RECEIVER A , ADDRESS_BOOK B \n");
			sb.append("WHERE A.AB_SEQ = B.AB_SEQ\n");
			sb.append("      AND A.AS_SEQ = "+as_seq+"\n");
			
			Log.debug(sb.toString() );
			rs = stmt.executeQuery(sb.toString());			
						
	        while(rs.next())
	        {
	        	
	        	result += result.equals("") ?  rs.getString("AB_SEQ") : ","+ rs.getString("AB_SEQ");
	        }
		
		} catch (SQLException ex ) {
            Log.writeExpt(ex, ex.getMessage() );

        } catch (Exception ex ) {
            Log.writeExpt(ex.getMessage());

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
    	return result;
    }
    
    //주소록 리스트 가져오기
    public ArrayList getAddressList( String ab_seq,
    								 String ab_name,
    								 String ab_dept    								 
    								)
    {
    	ArrayList result = new ArrayList();
    	AlimiReceiverBean arBean = new AlimiReceiverBean();
    	
		try {
			String[] issue_Dept=null;
			
			String check = ab_seq+ab_name+ab_dept;
			String whereQuery = "";
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();
			
			/*
			sb.append("SELECT AB_SEQ, AB_NAME, AB_DEPT, AB_POS \n");
			sb.append("	      , AB_MOBILE, AB_MAIL \n");
			
			sb.append("FROM ADDRESS_BOOK \n");
			sb.append("WHERE AB_ACTIVE = 1 ");
			*/
			
			sb.append("SELECT AB_SEQ          \n");
			sb.append("     , AB_NAME         \n");
			sb.append("	    , AB_DEPT         \n");
			sb.append("	    , AB_POSITION     \n");
			sb.append("	    , AB_MOBILE       \n");
			sb.append("	    , AB_MAIL         \n");
			sb.append("  FROM ADDRESS_BOOK    \n");
			sb.append(" WHERE 1=1             \n");

			if( ab_seq.length() > 0 )
			whereQuery += " AND AB_SEQ IN ("+ab_seq+") ";
			
			if( ab_name.length() > 0 ) {
				if( whereQuery.length() > 0 ) whereQuery += " AND ";
				whereQuery += " AB_NAME LIKE '%"+ab_name+"%' ";
			}
			
			if( ab_dept.length() > 0 ) {
				if( whereQuery.length() > 0 ) whereQuery += " AND ";
				whereQuery += " AB_DEPT LIKE '%"+ab_dept+"%' ";
			}
			
			if(issue_Dept!=null && issue_Dept.length>0 ){
				if( whereQuery.length() > 0 ) whereQuery += " AND ";
				whereQuery += " ( ";
				for(int i=0; i < issue_Dept.length; i++){
					whereQuery += "AB_ISSUE_DEPT LIKE '%"+issue_Dept[i]+"%' ";
					
					if(issue_Dept.length -1 != i){
						whereQuery += " OR  ";
					}					
				}
				whereQuery += " ) ";
			}		
			sb.append(whereQuery);			
			
			rs = stmt.executeQuery(sb.toString());
			
			result = new ArrayList();
	        while(rs.next())
	        {
	        	//핸드폰번호 특수문자 제거
	        	String replaceString =  rs.getString(5);
	        	replaceString = replaceString.replaceAll("-", "");
	        	replaceString = replaceString.replaceAll("\\.", "");
	        	replaceString = replaceString.replaceAll(" ", "");
	        	replaceString = replaceString.replaceAll("_", "");
	        	replaceString = replaceString.replaceAll("\'", "");
	        	replaceString = replaceString.replaceAll("\"", "");
	        	
	        	arBean = new AlimiReceiverBean();
	        	arBean.setAb_seq(rs.getString(1));
	        	arBean.setAb_name(rs.getString(2));
	        	arBean.setAb_dept(rs.getString(3));
	        	arBean.setAb_pos(rs.getString(4));
	        	arBean.setAb_mobile(replaceString);
	        	arBean.setAb_mail(rs.getString(6));
	        	result.add(arBean);					
	        	
	        }
		
		} catch (SQLException ex ) {
            Log.writeExpt(ex, ex.getMessage() );

        } catch (Exception ex ) {
            Log.writeExpt(ex.getMessage());

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
    	return result;
    }    
   
    
    // 주소록 저장
	public String insertAddressBook(AlimiReceiverBean arBean){
		
		int result = 0;
		String ab_seq = "";
		try {
			
			dbconn = new DBconn();
			Statement stmt = null;
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			
			
				/*
			   sb.append(" INSERT INTO ADDRESS_BOOK (AB_SEQ,     \n"); 
			   sb.append("            				 AB_NAME,     \n");
			   sb.append("            				 AB_DEPT,     \n");
			   sb.append("           				 AB_POS,     \n");
			   sb.append("         				     AB_MOBILE,     \n");
			   sb.append("         				     AB_MAIL     \n");
			   sb.append("					 		 )     \n");
			   
			   sb.append("    VALUES (SEQ_AB_SEQ.NEXTVAL     \n");
			   sb.append("      	  ,'" + arBean.getAb_name() + "'    \n");
			   sb.append("      	  ,'" + arBean.getAb_dept() + "'    \n");
			   sb.append("            ,'" + arBean.getAb_pos() + "'    \n");
			   sb.append("            ,'" + arBean.getAb_mobile() + "'    \n");
			   sb.append("            ,'" + arBean.getAb_mail() + "'    \n");
			  
			   
			   sb.append("            )         \n");
			*/
			
			sb.append("INSERT INTO ADDRESS_BOOK ( AB_NAME                                \n");
			sb.append("                         , AB_DEPT                                \n"); 
			sb.append("                         , AB_POSITION                            \n"); 
			sb.append("                         , AB_MOBILE                              \n"); 
			sb.append("                         , AB_MAIL  )                             \n");
			sb.append("                  VALUES ( '"+ arBean.getAb_name() +"'            \n");
			sb.append("                         , '"+ arBean.getAb_dept() +"'            \n");
			sb.append("                         , '"+ arBean.getAb_pos() +"'             \n");
			sb.append("                         , '"+ arBean.getAb_mobile() +"'          \n");
			sb.append("                         , '"+ arBean.getAb_mail() +"')           \n");
			

			System.out.println(sb.toString());
			result = stmt.executeUpdate(sb.toString());
			if(result==1)
			{
				sb = new StringBuffer();
				
				sb.append(" SELECT AB_SEQ FROM ADDRESS_BOOK ORDER BY AB_SEQ DESC LIMIT 0,1 ");
				rs = stmt.executeQuery(sb.toString());
				if (rs.next() ) {
					ab_seq = rs.getString("AB_SEQ");
					//System.out.println("abSeq  :" +abSeq);
				}
			}

        } catch (SQLException ex ) {
            Log.writeExpt(ex, ex.getMessage() );

        } catch (Exception ex ) {
            Log.writeExpt(ex.getMessage());

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return ab_seq;
    }
    
	/*
	public PcAlimiSettingBean getPcAlimiSetList()
    {
		PcAlimiSettingBean pasBean = new PcAlimiSettingBean();
    	try
    	{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();   
            rs = null;           
            
            sb.append("SELECT  PA_SEQ                                                      \n");
            sb.append("       ,PA_ACTIVE                                                    \n");
            sb.append("       ,PA_INFO                                                     \n");
            sb.append("       ,K_XP                                                     \n");
            sb.append("       ,P_XP                                                     \n");
            sb.append("       ,SG_SEQ                                                       \n");
            sb.append("       ,MT_TYPE                                                     \n");
            sb.append("       ,PA_COUNT                                                     \n");
            sb.append("FROM   PC_ALIMI_SETTING                                                               \n");
			
            //System.out.println(sb.toString());
			Log.debug(sb.toString() );			
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next())
			{
				pasBean.setPa_active(rs.getInt("PA_ACTIVE"));
				pasBean.setPa_info(rs.getString("PA_INFO"));
				pasBean.setK_xp(rs.getString("K_XP"));
				pasBean.setP_xp(rs.getString("P_XP"));
				pasBean.setSg_seq(rs.getString("SG_SEQ"));
				pasBean.setMt_type(rs.getString("MT_TYPE"));
				pasBean.setPa_count(rs.getInt("PA_COUNT"));
			}
			
			
			
    	 } catch (SQLException ex ) {
             Log.writeExpt(ex, ex.getMessage() );

         } catch (Exception ex ) {
             Log.writeExpt(ex.getMessage());

         } finally {
             try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
             try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
             try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
         }   
         
         return pasBean;
    }
	
	public PcAlimiSettingBean getPcAlimiSetList(String type)
	{
		PcAlimiSettingBean pasBean = new PcAlimiSettingBean();
		try
		{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();   
			rs = null;           
			
			sb.append("SELECT  PA_SEQ                                                      \n");
			sb.append("       ,PA_ACTIVE                                                    \n");
			sb.append("       ,PA_INFO                                                     \n");
			sb.append("       ,K_XP                                                     \n");
			sb.append("       ,P_XP                                                     \n");
			sb.append("       ,SG_SEQ                                                       \n");
			sb.append("       ,MT_TYPE                                                     \n");
			sb.append("       ,PA_COUNT                                                     \n");
			sb.append("       ,P_SITE                                                     \n");
			sb.append("FROM   PC_ALIMI_SETTING                                                               \n");
			sb.append("WHERE  PA_TYPE = "+type+"                                                               \n");
			
			//System.out.println(sb.toString());
			Log.debug(sb.toString() );			
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next())
			{
				pasBean.setPa_active(rs.getInt("PA_ACTIVE"));
				pasBean.setPa_info(rs.getString("PA_INFO"));
				pasBean.setK_xp(rs.getString("K_XP"));
				pasBean.setP_xp(rs.getString("P_XP"));
				pasBean.setSg_seq(rs.getString("SG_SEQ"));
				pasBean.setMt_type(rs.getString("MT_TYPE"));
				pasBean.setPa_count(rs.getInt("PA_COUNT"));
				pasBean.setPSite(rs.getString("P_SITE"));
			}
			
			
			
		} catch (SQLException ex ) {
			Log.writeExpt(ex, ex.getMessage() );
			
		} catch (Exception ex ) {
			Log.writeExpt(ex.getMessage());
			
		} finally {
			try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
		}   
		
		return pasBean;
	}
	
	public int setPcAlimiSetList(PcAlimiSettingBean pasBean)
	{
		int result = 0;
		try
		{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();   
			rs = null;           
			
			sb.append("UPDATE PC_ALIMI_SETTING                                              \n");
			sb.append("       SET PA_ACTIVE = "+pasBean.getPa_active()+"                                                 \n");
			sb.append("       ,PA_INFO = '"+pasBean.getPa_info()+"'                                                   \n");
			sb.append("       ,K_XP = '"+pasBean.getK_xp()+"'                                                \n");
			sb.append("       ,P_XP = '"+pasBean.getP_xp()+"'                                                     \n");
			sb.append("       ,SG_SEQ = '"+pasBean.getSg_seq()+"'                                                       \n");
			sb.append("       ,MT_TYPE  = '"+pasBean.getMt_type()+"'                                                    \n");
			sb.append("       ,PA_COUNT = "+pasBean.getPa_count()+"                                                     \n");
			sb.append("       ,P_SITE = '"+pasBean.getPSite()+"'                                                     \n");
			sb.append("WHERE  PA_TYPE = "+pasBean.getPa_type()+"                                                     \n");
			
			//System.out.println(sb.toString());
			Log.debug(sb.toString() );			
			stmt.executeUpdate(sb.toString());
			
			
			
			
		} catch (SQLException ex ) {
			Log.writeExpt(ex, ex.getMessage() );
			
		} catch (Exception ex ) {
			Log.writeExpt(ex.getMessage());
			
		} finally {
			try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
		}   
		
		return result;
	}
	
	*/
	
	/**
	 * SMS 발송	
	 * @param reciever
	 * @param sender
	 * @param smsContent
	 * @return
	 */
	public boolean sendSms(
							 String reciever  //수신자 
							,String sender // 발송자		
							,String smsContent // SMS 내용
							)
	{
		boolean result = false;

		Statement stmt = null;
		StringBuffer sb = null;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
	    	stmt = dbconn.createStatement();
	    	
			// SMS 발송 모듈에 인서트
			sb = new StringBuffer();
			sb.append("INSERT INTO SMS_SEND																					\n");
			sb.append("(MSG_KEY, RECEIVER, SENDER, MESSAGE)	\n");
			sb.append("VALUES(SEQ_SMS_SEQ.NEXTVAL, '"+reciever+"', '"+sender+"', '"+smsContent+"')	\n");
			
			System.out.println( sb.toString() );
			int chk = stmt.executeUpdate( sb.toString() );
			System.out.println("실행결과값: "+chk);
			
			if(chk > 0){
				result = true;
			}else{
				result = false;
			}

		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            sb = null;
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        
		return result;
	}
	
	/**
	 * 알리미 발송 로그 작성
	 * @param recieverName
	 * @param reciever
	 * @param sender
	 * @param content
	 * @param title
	 * @param url
	 * @param reciverSeq
	 * @param mt_no
	 * @param type
	 * @return
	 */
	public boolean sendAlimLog(
			String recieverName  	//수신자 
			,String reciever  	//수신자 
			,String sender 		// 발송자		
			,String content 	// 내용
			,String title 		// 제목
			,String url 		//URL
			,String reciverSeq  //수신자 SEQ
			,String mt_no		//데이터 번호
			,String type		//1:SMS, 2:MMS, 4:수동 메일, 5:수동 SMS
	)
	{
		DateUtil du = new DateUtil();
		
		boolean result = false;
		Statement stmt = null;
		StringBuffer sb = null;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			// Alim LOg 작성
			sb = new StringBuffer();
			sb.append("INSERT INTO SMS_LOG								\n");
			sb.append("(SMSL_SEQ, MT_NO, SMSL_DATE, SMSL_TIME, SMS_TYPE, SMSL_TITLE, SMSL_HTML, SMSL_URL, AB_SEQ, AB_NAME, AB_TEL, SENDER_NAME)	\n");
			sb.append("VALUES	\n");
			sb.append("(SEQ_SMSL_SEQ.NEXTVAL, "+mt_no+", '"+du.getCurrentDate("yyyyMMdd")+"', '"+du.getCurrentDate("HHmmss")+"',"+type+" 	\n");
			sb.append(" , '"+title.replaceAll("'", "''")+"', '"+content+"', '"+url+"', "+reciverSeq+", '"+recieverName+"', '"+reciever+"', '"+sender+"')	\n");
			
			System.out.println( sb.toString() );
			int chk = stmt.executeUpdate( sb.toString() );
			System.out.println("실행결과값: "+chk);
			
			if(chk > 0){
				result = true;
			}else{
				result = false;
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			sb = null;
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		
		return result;
	}
	
	
	public Hashtable getAlimLog(
			String sNo  	//수신자 
			,String eNo  	//수신자 
			,String type		//1:SMS, 2:MMS, 4:수동 메일, 5:수동 SMS
	)
	{

		Hashtable result = new Hashtable();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			// Alim LOg 작성
			sb = new StringBuffer();
			sb.append("SELECT MT_NO, SMS_TYPE FROM SMS_LOG								\n");
			sb.append("WHERE MT_NO BETWEEN ? AND ?	\n");
			sb.append("AND SMS_TYPE = ?	\n");
			sb.append("GROUP BY MT_NO, SMS_TYPE	\n");
			
			pstmt = dbconn.createPStatement(sb.toString());
			//System.out.println( sb.toString() +"\n 	sNo:"+sNo+"/eNo"+eNo+"/type"+type);
			
			pstmt.clearParameters();
			pstmt.setString(1, sNo);
			pstmt.setString(2, eNo);
			pstmt.setInt(3, Integer.parseInt(type));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				//System.out.println("mt_no:"+rs.getString("MT_NO")+"/sms_type:"+rs.getString("SMS_TYPE"));
				result.put(rs.getString("MT_NO"), rs.getString("SMS_TYPE"));
			}
			
			
		} catch(SQLException ex) {
			ex.printStackTrace();
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			ex.printStackTrace();
			Log.writeExpt(ex);
		} finally {
			sb = null;
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		}
		
		return result;
	}
	
	public int addPcAlimiSiteGroup(int type, String sName, String sGroup, String m_seq)
	{
		int result = 0;
		try
		{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();   
			rs = null;    
			
			int pcsg_seq = 1;
			
			sb.append(" SELECT PCSG_SEQ FROM PC_ALIMI_SETTING_SITEGROUP WHERE ROWNUM =1 ORDER BY PCSG_SEQ DESC                                             \n");
			
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next()){
				if(rs.getInt("PCSG_SEQ")!=0){
					pcsg_seq = rs.getInt("PCSG_SEQ")+1;
				}
			}
			
			sb = new StringBuffer();
			
			sb.append("INSERT INTO PC_ALIMI_SETTING_SITEGROUP(PCSG_SEQ, PCSG_TYPE, PCSG_NAME, PCSG_SGSEQ, PCSG_ACTIVE, PCSG_MSEQ)                                              \n");
			sb.append("VALUES("+pcsg_seq+", "+type+", '"+sName+"', '"+sGroup+"', 1, "+m_seq+")                                              \n");
			
			Log.debug(sb.toString() );			
			stmt.executeUpdate(sb.toString());
			
			
		} catch (SQLException ex ) {
			Log.writeExpt(ex, ex.getMessage() );
			
		} catch (Exception ex ) {
			Log.writeExpt(ex.getMessage());
			
		} finally {
			try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
		}   
		
		return result;
	}
	
	public int delPcAlimiSiteGroup(int pcsgseq, String m_seq)
	{
		int result = 0;
		try
		{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();   
			rs = null;    
			
			sb.append("UPDATE PC_ALIMI_SETTING_SITEGROUP                               \n");
			sb.append(" SET PCSG_ACTIVE = 0                                \n");
			sb.append(" , PCSG_MSEQ = "+m_seq+"                                \n");
			sb.append("WHERE PCSG_SEQ = "+pcsgseq+"                                              \n");
			
			Log.debug(sb.toString() );			
			stmt.executeUpdate(sb.toString());
			
			
		} catch (SQLException ex ) {
			Log.writeExpt(ex, ex.getMessage() );
			
		} catch (Exception ex ) {
			Log.writeExpt(ex.getMessage());
			
		} finally {
			try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
		}   
		
		return result;
	}
	
	public ArrayList getPcAlimiSiteGroup(int pcsgseq, int type)
	{
		ArrayList arrResult = new ArrayList();
		try
		{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();   
			rs = null;    
			
			sb.append("SELECT PCSG_SEQ, PCSG_TYPE, PCSG_NAME, PCSG_SGSEQ                               \n");
			sb.append("FROM PC_ALIMI_SETTING_SITEGROUP                               \n");
			sb.append("WHERE PCSG_ACTIVE = 1                                \n");
			if(type!=0){
				sb.append("AND PCSG_TYPE = "+type+"                                \n");
			}
			if(pcsgseq!=0){
				sb.append("AND PCSG_SEQ = "+pcsgseq+"                                \n");
			}
			sb.append("ORDER BY PCSG_SEQ                              \n");
			
			Log.debug(sb.toString() );			
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				String[] tmp = new String[4];
				tmp[0] = rs.getString("PCSG_SEQ");
				tmp[1] = rs.getString("PCSG_TYPE");
				tmp[2] = rs.getString("PCSG_NAME");
				tmp[3] = rs.getString("PCSG_SGSEQ");
				
				arrResult.add(tmp);
			}
			
		} catch (SQLException ex ) {
			Log.writeExpt(ex, ex.getMessage() );
			
		} catch (Exception ex ) {
			Log.writeExpt(ex.getMessage());
			
		} finally {
			try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
		}   
		
		return arrResult;
	}
	
	
	public ArrayList getSendMailSetting()
	{
		ArrayList arrResult = new ArrayList();
		try
		{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();   
			rs = null;    
			
			sb.append("SELECT *                               \n");
			sb.append("FROM GMAIL_GROUP                               \n");
			sb.append("WHERE 1 = 1                                \n");
			Log.debug(sb.toString() );			
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				String[] tmp = new String[3];
				tmp[0] = rs.getString("GM_SEQ");
				tmp[1] = rs.getString("AS_SEQ");
				tmp[2] = rs.getString("GM_MAIL");
				arrResult.add(tmp);
			}
			
		} catch (SQLException ex ) {
			Log.writeExpt(ex, ex.getMessage() );
			
		} catch (Exception ex ) {
			Log.writeExpt(ex.getMessage());
			
		} finally {
			try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
		}   
		
		return arrResult;
	}

	public int updateSendMail(String as_seq , String gm_seq){
		int result = 0;
		try
		{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();   
			    
			sb.append("UPDATE GMAIL_GROUP                  \n");
			sb.append("SET  AS_SEQ = "+as_seq+"                               \n");
			sb.append("WHERE GM_SEQ = "+gm_seq+"                                \n");
			Log.debug(sb.toString() );			
			result = stmt.executeUpdate(sb.toString());
			
		} catch (SQLException ex ) {
			Log.writeExpt(ex, ex.getMessage() );
			
		} catch (Exception ex ) {
			Log.writeExpt(ex.getMessage());
			
		} finally {
			try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
		}
		
		return result;
		
	}
	
	public int updateSiteList(String as_seq , String site_seqs){
		int result = 0;
		try
		{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();   
			    
			sb.append("UPDATE ALIMI_SETTING                  \n");
			sb.append("SET  SD_GSNS = '"+site_seqs+"'                               \n");
			sb.append("WHERE AS_SEQ = "+as_seq+"                                \n");
			Log.debug(sb.toString() );			
			result = stmt.executeUpdate(sb.toString());
			
		} catch (SQLException ex ) {
			Log.writeExpt(ex, ex.getMessage() );
			
		} catch (Exception ex ) {
			Log.writeExpt(ex.getMessage());
			
		} finally {
			try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
		}
		return result;
	}
	
	public void insertSiteList(String as_seq , String site_seqs){
		try
		{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();   
			    
			sb.append("UPDATE ALIMI_SETTING                  \n");
			sb.append("SET  SD_GSNS = "+site_seqs+"                               \n");
			sb.append("WHERE AS_SEQ = "+as_seq+"                                \n");
			Log.debug(sb.toString() );			
			stmt.executeUpdate(sb.toString());
			
		} catch (SQLException ex ) {
			Log.writeExpt(ex, ex.getMessage() );
			
		} catch (Exception ex ) {
			Log.writeExpt(ex.getMessage());
			
		} finally {
			try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
		}	
	}
	
	public static int getSendCount(String pk, String as_monitor_max_m, String as_monitor_repeat_m) {
		if("".equals(pk)){
			return 0;
		}
		int result = 0;
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        DBconn dbconn1 = null;
        int monitor_max_m = Integer.parseInt(as_monitor_max_m);
        int monitor_repeat_m = Integer.parseInt(as_monitor_repeat_m);
        try{
        	dbconn1 = new DBconn(); //로컬디비
        	dbconn1.getSubDirectConnection();
        	stmt = dbconn1.createStatement();
        	sb = new StringBuffer();
		    
	    	sb.append("SELECT TIMESTAMPDIFF(MINUTE, MAL_SEND_DATE, SYSDATE()) AS DelayTime \n"); 
	    	sb.append("  FROM MALIMI_LOG                                                    \n");
	    	sb.append(" WHERE AS_SEQ = "+pk+"                                              \n");
	    	sb.append(" ORDER BY MAL_SEQ DESC                                              \n");
	    	sb.append(" LIMIT 1                                                            \n");
    		rs = stmt.executeQuery(sb.toString());
    		while(rs.next()){
    			result = rs.getInt("DelayTime");
    		}
    		if( 0 < result &&  monitor_max_m < result){
    			 return (result-monitor_max_m)/monitor_repeat_m;
    		}
    		
        } catch(Exception ex) {
        	Log.writeExpt(ex.getMessage());
        	ex.printStackTrace();
        	System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
            if (dbconn1 != null) try { dbconn1.close(); } catch(SQLException ex) {}
        }
		return result;
    }
}
