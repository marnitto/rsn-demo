package risk.sms;


import java.net.URLEncoder;
import risk.util.StringUtil;


public class SmsSend {
	
	  public Boolean SendSMS(String phone, String msg){
		  
		  Boolean result = false;
		  StringUtil su = new StringUtil();
		  
		  try{
			  String url = "http://www.munja114.co.kr/Remote/RemoteSms.html";
			  StringBuffer param = new StringBuffer();
			  param.append("remote_id=rsnrsn");  
			  param.append("&remote_pass=9219"); 
			  param.append("&remote_num=%s");    //문자메시지 전송할 개수
			  param.append("&remote_reserve=%d");//문자메시지 예약전송 체크 (0 : 즉시전송, 1: 예약전송)
			  param.append("&remote_phone=%s");  //문자메시지 수신번호 
			  param.append("&remote_callback=07076000339");
			  param.append("&remote_msg=%s");   //내용
			  
			  String data = String.format(param.toString(), "1", 0, phone.trim().replaceAll("-", ""), URLEncoder.encode(msg,"euc-kr"));
			  String rslt = su.GetHtmlPost(url, data);
			  
			  if(rslt.equals("0000")){
				  result = true;
			  }
			
		  }catch (Exception e) {
			  e.printStackTrace();
		  }
		  
		  return result;
	  }
}
