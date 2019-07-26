package risk.sms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.util.Random;

public class SmsSend_old {
	   protected Socket socket;

	   Random r = new Random();
	   // 요금제에 따라 주석을 풀어서 사용하세요.
	   //int port = r.nextInt(4) + 7192; //충전제 설정
	   int port = r.nextInt(4) + 7196; //정액제 설정
	   
	   String destination = "211.172.232.124";
	   DataOutputStream out;
	   DataInputStream in;


	   public SmsSend_old(){      
	         
	   }
	   
	   public void connect() throws Exception{
	       socket = new Socket(destination,port);
		   socket.setSoTimeout (5000);
		   out = new DataOutputStream(socket.getOutputStream()) ;
		   in = new DataInputStream(socket.getInputStream());
	    
	   }
	   
	   public void disconnect() throws Exception{
	       in.close();
		   out.close();
		   socket.close();

	    
	   }

	  //전화번호와 회신번호는 숫자만가능 
	   public String SendMsg(String callNo, String callBack,String caller, String msg) throws IOException{       
	       
	       String result = "",msgid = "";
	       int i = 0;
		  	  
		   out.writeBytes("01"); //Msg Type
		   out.writeBytes("0144"); //Msg Len	
		   out.writeBytes(fillSpace("rsnrsn",10));		//아이코드 ID
		   out.writeBytes(fillSpace("9219",10));		//아이코드 PASSWORD
		   out.writeBytes(fillSpace(callNo,11));		   
		   out.writeBytes(fillSpace(callBack,11));
	       out.writeBytes(fillSpace(caller,10));
		   out.writeBytes(fillSpace(" ",12)); //즉시전송시 날짜와 시간은 모두 space
		   //예약 전송시 12자리를 맞춰주시면 됩니다. 형식은 YYYYMMDDHH

	       out.writeBytes(fillSpace(msg,80)); // msg
	       out.flush();

		   boolean inputExist = true;
		   
		   do{
		       try{						
		    	   
			        byte buffer[] = new byte[2];
			        for(i=0 ; i < 2; i++)buffer[i] = in.readByte();
			        String msgType = new String(buffer);
	                byte temp[] = new byte[4];
			        for(i = 0; i < 4 ; i++) temp[i] = in.readByte();
	                String sLen = new String(temp);
					sLen = sLen.trim();

			        int nLen = Integer.valueOf(sLen).intValue();
	                buffer = new byte[nLen];

			        if(msgType.equals("02")){
			           inputExist = false;

				       for(i=0 ; i < nLen; i++)buffer[i] = in.readByte();
				          result = new String(buffer);			  
				          msgid = result;

			        }else if(msgType.equals("03")){
					   
	                   for(i=0 ; i < nLen; i++)buffer[i] = in.readByte();
				          result = new String(buffer);
				          
	                   out.writeBytes("04");
					   out.writeBytes("12  ");
					   out.writeBytes("00");
					   out.writeBytes(result.substring(2));
					   out.flush();
	                  
	     			}

				
			   }catch (EOFException e){
				 	inputExist = false;
			   
			   }catch (InterruptedIOException e){
				 	
			   }
	       }while(inputExist);        
		  	
	       return msgid.trim();   
	   
	   }
	   
	    
		public String fillSpace(String text, int size){
	    	int diff = size - text.length();
	    	if (diff > 0 ){
	    		for (int i = 0 ; i < diff ; i++)
	    			text += " ";
	    	}else{
			   StringBuffer sb = new StringBuffer(text);
			   sb.setLength(size);
			   text = sb.toString();
			}

			return text;


	    }
	   
	   
}
