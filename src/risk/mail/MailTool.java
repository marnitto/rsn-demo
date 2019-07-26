package risk.mail;

import risk.util.ConfigUtil;
import risk.util.Log;


public class  MailTool
{
	private MailUtil mail;	
	ConfigUtil cu = new ConfigUtil();

	public MailTool()
	{
		mail = new MailUtil();
	}	

 
	public boolean sendMail(String to_email, String subject, String message){
		boolean bResult=false;
		bResult=this.sendMail(to_email, subject, message,"");
		return bResult;
	}
	
	public boolean sendMail(String to_email, String subject, String message,String from_email)
	{
		boolean isSend = false;
		try
		{
			if(from_email.equals("")){
				from_email = cu.getConfig("SENDER");	// "dhpark@hanafos.com";
			}
			Log.writeExpt("MAILLOG"," 보내는사람( " + from_email + " ) : ");
			Log.writeExpt("MAILLOG"," 받는사람( " + to_email + " ) : ");

			if (mail.sendmessage(to_email, from_email, cu.getConfig("MAILTITLE")+subject, message, true))  {
				Log.writeExpt("MAILLOG",cu.getConfig("MAILTITLE")+subject);
				Log.writeExpt("MAILLOG","보냄");
				isSend = true;
			}else{ 
				Log.writeExpt("MAILLOG","메일을 보내지 못했습니다.");
				isSend = false;
			}

			return isSend;
		}
		catch (Exception ex)
		{
			Log.debug(ex.getMessage());
			return false;
		}		
	}
}
