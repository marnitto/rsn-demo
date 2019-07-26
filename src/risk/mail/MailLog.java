package risk.mail;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;

import risk.util.*;

public class MailLog
{
	private RandomAccessFile out;
	ConfigUtil cu = new ConfigUtil();
//	private String path = "/WLS/lms/domains/applications/mydomain/DefaultWebApp/common/mail_log/";	//	UNIX
	private String path = cu.getConfig("MAILLOG");	//	WINDOWS
 
	/**
	*	기본 객체 생성자
	*/
	public MailLog()
	{
	}

	/**
	*	Log파일 위치를 지정하여 객체 생성
	*/
	public MailLog(String path)
	{
		this.path = path;
		open();
	}

	/**
	*	로그파일을 새로 만듦
	*
	*	@return	파일의 생성여부
	*/
	public boolean open()
	{
		try
		{
			out = new RandomAccessFile(path + "MAIL_" + getTime("yyyyMMddHHmmss") + ".txt", "rw");
			return true;
		}
		catch (IOException ie)
		{
			ie.printStackTrace();
			return false;
		}
	}

	/**
	*	파일에 내용을 기록
	*
	*	@param	content		기록할 내용
	*	@return	기록결과(true : 성공, false : 실패)
	*/
	public boolean write(String content) 
	{
		try
		{
			content = new String(content.getBytes(), "8859_1");
			out.writeBytes(content);
			return true;
		}
		catch (IOException ie)
		{
			ie.printStackTrace();
			return false;
		}
	}

	/**
	*	기록할 내용을 LOG기록형식으로 바꾸어 기록
	*
	*	@param	msg		기록할 내용
	*	@return	기록결과(true : 성공, false : 실패)
	*/
	public boolean writeLog(String msg) 
	{
		StringBuffer contents = new StringBuffer();

		contents.append(msg).append(",").append(getTime("yyyy.MM.dd HH:mm:ss")).append("\n");

		return write(contents.toString());
	}

	/**
	*	Log파일의 종료
	*/
	public void close()
	{
		try
		{
			out.writeBytes("\n\n[END]");
			out.close();			
		}
		catch (IOException i){}
	}

	/**
	*	특정 형식의 시간문자열을 작성
	*
	*	@param	format	시간 형식
	*	@return	완성된 문자열
	*/
	public String getTime(String format)
	{
		SimpleDateFormat dt = new SimpleDateFormat(format);

		return dt.format(new java.util.Date());
	}
}