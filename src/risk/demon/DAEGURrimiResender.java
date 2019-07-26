package risk.demon;

import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import risk.DBconn.DBconn;
import risk.util.Log;
import risk.util.rrimiUtil;

public class DAEGURrimiResender {

	  static DBconn dbconn = null;

	  public static void main(String[] args) {

	    try{
	      Log.writeExpt("CRONLOG", "GG R-rimi Resender Start..");

	      dbconn = new DBconn();
	      dbconn.getSubDirectConnection();

	      // RRIMI_FAIL_DATA에 있는 모든 데이터를 가져온다.
	      //String[][] resendData = getAllData();
	      String[][] resendData = getFailedData();

	      if(resendData != null){
				// 모든 데이터 루프
				for (int i=0; i<resendData.length; i++) {
					
					if(Integer.parseInt(resendData[i][4]) < 3 ){
						// 실패 3회 미만 데이터
						
						// 한건씩 전송
						if(reSendRrimiMsg(resendData[i])){
							// 성공하면 삭제
							Log.writeExpt("CRONLOG", "R-rimi 발송 성공");
							rogUpdate(resendData[i][5], resendData[i][6], resendData[i][3]);
							deleteRrimiMsg(resendData[i][0]);
						}else{
							// 실패하면 Count+1
							Log.writeExpt("CRONLOG", "R-rimi 발송 실패 : "+resendData[i][0]);
							updateRrimiMsgRetryCount(resendData[i][0]);
						}
					}else{
						
						// 실패 3회 이상 데이터 SMS전송
						if(sendSms(resendData[i][2], resendData[i][7], resendData[i][5], resendData[i][6], resendData[i][3])){
							deleteRrimiMsg(resendData[i][0]);
							Log.writeExpt("CRONLOG", "R-rimi 발송 실패데이터  SMS로 전환 전송 : "+resendData[i][0]);	
						}else{
							// 실패하면 Count+1
							updateRrimiMsgRetryCount(resendData[i][0]);
							Log.writeExpt("CRONLOG", "SMS 발송 실패 : "+resendData[i][0]);
						}
					}
					
				}
			}

	      Log.writeExpt("CRONLOG", "GG R-rimi Resender End..");
	    }catch(Exception e){

	    }
	  }

	  public static String[][] getFailedData() {
			String[][] result = null;
			
			Statement stmt = null;
	        ResultSet rs = null;
	        StringBuffer sb = null;
	        
			try{
	        	sb = new StringBuffer();
	            
	            sb.append("	SELECT RFD_SEQ, RFD_MSG_JSON_DATA, RFD_MSG_SMS_DATA, RFD_DATA_TYPE, RFD_RETRY_CNT, MD_SEQ, AS_SEQ, RFD_NUMBERS FROM RRIMI_FAIL_DATA		\n");
	            
	            System.out.println(sb.toString());
	            stmt = dbconn.createStatement();
	            rs = stmt.executeQuery(sb.toString());
	            
	            int count = 0;
	            while(rs.next()){
	            	count++;
	            }
	            
	            result = new String[count][8];
	            count = 0;
	            rs.beforeFirst();
	            
	            while(rs.next()){
	            	
	            	result[count][0] = rs.getString("RFD_SEQ");
	            	result[count][1] = rs.getString("RFD_MSG_JSON_DATA");
	            	result[count][2] = rs.getString("RFD_MSG_SMS_DATA");
	            	result[count][3] = rs.getString("RFD_DATA_TYPE");
	            	result[count][4] = rs.getString("RFD_RETRY_CNT");
	            	result[count][5] = rs.getString("MD_SEQ");
	            	result[count][6] = rs.getString("AS_SEQ");
	            	result[count][7] = rs.getString("RFD_NUMBERS");
	            	count++;
	            }

			}catch(SQLException ex){
				ex.printStackTrace();
				Log.writeExpt("CRONLOG", ex.toString());
	        }catch(Exception ex){
	        	ex.printStackTrace();
	        	Log.writeExpt("CRONLOG", ex.toString());
	        }finally{
	            sb = null;
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	        }
			
			return result;
		}

	  public static void updateRrimiMsgRetryCount(String seq) {
			Statement stmt = null;
	        StringBuffer sb = null;
			try{
	            sb = new StringBuffer();
	            sb.append("	UPDATE RRIMI_FAIL_DATA SET RFD_RETRY_CNT = RFD_RETRY_CNT + 1 WHERE RFD_SEQ = "+seq+"	\n");
	            
	            System.out.println(sb.toString());
	            stmt = dbconn.createStatement();
	            stmt.executeUpdate(sb.toString());

			}catch(SQLException ex){
				ex.printStackTrace();
				Log.writeExpt("CRONLOG", ex.toString());
	        }catch(Exception ex){
	        	ex.printStackTrace();
	        	Log.writeExpt("CRONLOG", ex.toString());
	        }finally{
	            sb = null;
	            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	        }
		}

	  public static boolean reSendRrimiMsg(String[] data) {
			boolean result = false;
			rrimiUtil rUtil = new rrimiUtil();
			//	         			   json_data   sms_data   numbers   as_seq   data_type
			result = rUtil.resendRRIMI(data[1],    data[2],   data[7],   data[5], data[6], data[3],    dbconn);
			
			return result;
	}

	  /**
	   * sms발송시 저장 MAL_TYPE 성공여부저장 어떤 용도의 컬럼인지 몰라 성공여부로 사용 하겠음 : 0:실패 1:성공
	   * @param metaBean
	   * @param arBean
	   * @param mal_type
	   */
	  public static void RegAlimiLog(String sg_seq, String site_name, String site_menu, String datetime, String md_seq, String s_seq, String title, String url, String content, String k_value, String mtPno, String mal_type, String as_seq, String as_title){
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    StringBuffer sb = null;
	    try{
	      sb = new StringBuffer();

	      sb.append("INSERT INTO MALIMI_LOG\n");
	      sb.append("(SG_SEQ, SD_NAME, SD_MENU, MT_TITLE, MT_DATE\n");
	      sb.append(", MT_URL, MT_NO, MAL_SEND_DATE, MT_CONTENT, MAL_TYPE, S_SEQ, MT_PNO, AS_SEQ, AS_TITLE, SEND_MESSAGE,K_VALUES)\n");
	      sb.append("VALUES("+sg_seq+", '"+site_name+"', '"+site_menu+"', ?, '"+datetime+"'\n");
	      sb.append(", ?, "+md_seq+", NOW(), ?, '"+mal_type+"'" + "\n");
	      sb.append(", "+ s_seq +" , "+mtPno+", "+as_seq+", ? , ?, ? 	)\n");
	      pstmt = dbconn.createPStatement(sb.toString());
	      pstmt.setString(1, title);
	      pstmt.setString(2, url);
	      if(content.length() > 500){
	        pstmt.setString(3, content.substring(0, 500));
	      }else{
	        pstmt.setString(3, content);
	      }

	      pstmt.setString(4, as_title);
	      if(("["+site_name+"]"+title).length() > 64){
	        pstmt.setString(5, ("["+site_name+"]"+title).substring(0, 64));	
	      }else{
	        pstmt.setString(5, "["+site_name+"]"+title);
	      }
	      pstmt.setString(6, k_value);
	      pstmt.executeUpdate();

	    }catch(SQLException ex){
	      ex.printStackTrace();
	      Log.writeExpt("CRONLOG", ex.toString());
	    }catch(Exception ex){
	      ex.printStackTrace();
	      Log.writeExpt("CRONLOG", ex.toString());
	    }finally{
	      sb = null;
	      if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	      if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	    }
	  }
	  
	  public static void rogUpdate(String md_seq, String as_seq, String data_type) {
			Statement stmt = null;
	        StringBuffer sb = null;
			try{
	            sb = new StringBuffer();
	           
	        	sb.append("	UPDATE MALIMI_LOG SET MAL_TYPE = 1 WHERE  MT_NO = "+md_seq+" AND AS_SEQ = "+as_seq+"	\n");
	            
	            System.out.println(sb.toString());
	            stmt = dbconn.createStatement();
	            stmt.executeUpdate(sb.toString());

			}catch(SQLException ex){
				ex.printStackTrace();
				Log.writeExpt("CRONLOG", ex.toString());
	        }catch(Exception ex){
	        	ex.printStackTrace();
	        	Log.writeExpt("CRONLOG", ex.toString());
	        }finally{
	            sb = null;
	            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	        }
		}
	  
	  public static void deleteRrimiMsg(String seq) {
			Statement stmt = null;
	        StringBuffer sb = null;
			try{
	            sb = new StringBuffer();
	            sb.append("	DELETE FROM RRIMI_FAIL_DATA WHERE RFD_SEQ = "+seq+"	\n");
	            
	            System.out.println(sb.toString());
	            stmt = dbconn.createStatement();
	            stmt.executeUpdate(sb.toString());

			}catch(SQLException ex){
				ex.printStackTrace();
				Log.writeExpt("CRONLOG", ex.toString());
	        }catch(Exception ex){
	        	ex.printStackTrace();
	        	Log.writeExpt("CRONLOG", ex.toString());
	        }finally{
	            sb = null;
	            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	        }
		}
	  
	  public static void rogUpdateChangeSms(String md_seq, String as_seq, String data_type) {
			Statement stmt = null;
	        StringBuffer sb = null;
			try{
	            sb = new StringBuffer();
	            sb.append("	UPDATE MALIMI_LOG SET MAL_TYPE = 1, MAL_RESOURCE = 2 WHERE  MT_NO = "+md_seq+" AND AS_SEQ = "+as_seq+"	\n");
	            
	            System.out.println(sb.toString());
	            stmt = dbconn.createStatement();
	            stmt.executeUpdate(sb.toString());

			}catch(SQLException ex){
				ex.printStackTrace();
				Log.writeExpt("CRONLOG", ex.toString());
	        }catch(Exception ex){
	        	ex.printStackTrace();
	        	Log.writeExpt("CRONLOG", ex.toString());
	        }finally{
	            sb = null;
	            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	        }
		}
	  
	  
	  public static boolean sendSms(String sms_msg, String numbers, String md_seq, String as_seq, String data_type) {
			
			boolean result = false;
			
			rrimiUtil rUtil = new rrimiUtil();
			String [] reciever = numbers.split(",");
			
			for (int i=0; i<reciever.length; i++) {
				String url = "http://www.munja114.co.kr/Remote/RemoteSms.html";
			    StringBuffer param = new StringBuffer();
			    param.append("remote_id=rsnrsn");  
			    param.append("&remote_pass=9219"); 
			    param.append("&remote_num=%s");    //문자메시지 전송할 개수
			    param.append("&remote_reserve=%d");//문자메시지 예약전송 체크 (0 : 즉시전송, 1: 예약전송)
			    param.append("&remote_phone=%s");  //문자메시지 수신번호 
			    param.append("&remote_callback=07076000339"); //TODO 고객사 마다 발신자 번호 변경
			    param.append("&remote_msg=%s");   //내용
				
				try {
					String paramData = String.format(param.toString(), "1", 0, reciever[i], java.net.URLEncoder.encode(sms_msg,"euc-kr"));
					
					String rslt = "";
					rslt = rUtil.GetHtmlPost(url, paramData);
					
					System.out.println("rslt : "+rslt);
					if(rslt.equals("0000")){
						result = true;
						Log.writeExpt("CRONLOG", reciever[i]+"번호로 전송되었습니다.");
						rogUpdateChangeSms(md_seq, as_seq, data_type);
					}else{
						result = false;
						Log.writeExpt("CRONLOG", "전송실패");
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block 
					e.printStackTrace();
				}
			}
			
			return result;
		}
}
