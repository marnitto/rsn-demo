package risk.mail;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import risk.util.ConfigUtil;
import risk.util.Log;

public class MailUtil
{
	private String smtphost = "";		//	실제 SMTP서버 IP로 수정
	private Log log;

	private Properties props = new Properties();
	private Session session;
	private MimeMessage msg;
	
    public MailUtil()
	{
		log = new Log();
		ConfigUtil cu = new ConfigUtil();
		setSmtpHost(cu.getConfig("SMTP")); //210.104.107.5 // 211.174.63.123
    }

	/**
	*	SMTP 서버 IP주소 입력
	*
	*	@param	address		SMTP 서버 IP주소
	*/
	public void setSmtpHost(String address)
	{
		smtphost = address;
		props.put("mail.smtp.host", smtphost);
		props.put("mail.smtp.port", "25");
        session = Session.getDefaultInstance(props, null);
		msg = new MimeMessage(session);

		log.writeExpt("MAILLOG", "SMTP IP setting," + smtphost);   
	}

    /**
	*	메일을 발송한다.
	*
	*	@param		toaddr		받는사람 email주소
	*	@param		fromaddr	보내는사람 email주소
	*	@param		subject		제목
	*	@param		body		본문
	*	@param		ishtml		true : html, false : text
	*	@return		true - 성공, false - 실패
    */
    public boolean sendmessage(String toaddr, String fromaddr, String subject, String body, boolean ishtml)
	{

        try
		{
            InternetAddress from = new InternetAddress(fromaddr);
            msg.setFrom(from);

            InternetAddress[] to = InternetAddress.parse(toaddr);
            Address[] toAddress = to;
            msg.setRecipients(Message.RecipientType.TO, toAddress);
            
            msg.setSubject(subject, "euc-kr");

            if(ishtml)
                msg.setContent(body, "text/html;charset=euc-kr");
            else
                msg.setContent(body, "text/plain;charset=euc-kr");

            log.writeExpt("MAILLOG","mail send to," + toaddr);
			Transport.send(msg);

			
			
			log.writeExpt("MAILLOG","send OK," + toaddr);

			return true;
        }
        catch(MessagingException mex)
		{
            mex.printStackTrace();
            log.writeExpt("MAILLOG","Mail send fail," + mex.getMessage());

            return false;
        }
    }

	public String kor2asc(String txt)
	{                                
		try                      
		{                        
			txt = new String(txt.getBytes("8859_1"), "KSC5601");
		}                                                           
		catch (java.io.UnsupportedEncodingException ue)             
		{                                                           
			ue.printStackTrace();                               
		}                                                           
		finally                                        
		{                                                       
			return txt;                                         
		}                                                             
	}             

	public String euc2utf(String txt)
	{                                
		try                      
		{                        
			txt = new String(txt.getBytes("euc-kr"), "utf-8");
		}                                                           
		catch (java.io.UnsupportedEncodingException ue)             
		{                                                           
			ue.printStackTrace();                               
		}                                                           
		finally                                        
		{                                                       
			return txt;                                         
		}                                                             
	}    
	
	public String asc2kor(String txt)
	{                                
		try                      
		{                        
			txt = new String(txt.getBytes("KSC5601"), "8859_1");
		}                                                           
		catch (java.io.UnsupportedEncodingException ue)             
		{                                                           
			ue.printStackTrace();                               
		}                                                           
		finally                                        
		{                                                       
			return txt;                                         
		}                                                             
	}

	public int parseCode(String txt) throws Exception
	{
		return Integer.parseInt(txt.substring(0, 3));
	}
/**
	public int sendMemberMail(String mode) throws Exception
	{	
		int cnt = 0;

		NewsHtml contents = new NewsHtml();

		DBConnection conn = new DBConnection();
		PreparedStatement pstmt = conn.prepareStatement(" select USER_NAME, EMAIL "   +
														" from T_MEMBER where RCV_EMAIL = :val");
		pstmt.setString(1, mode);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next())
		{
			contents.setEmailName(rs.getString(2), rs.getString(1));

			if (sendmessage(rs.getString(2), "id10100@samsung.com", "ID10100 상상커뮤니티에서 보내는 상상뉴스", contents.makeHtml(), true)) cnt++;
		}

		rs.close();
		pstmt.close();
		if (conn != null) conn.returnConnection(); //커넥션을 돌려준다.

		return cnt;
	}
*/
}