package risk.search.solr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class testSolr {


	public static void main(String args[]){
		
		String url = "http://lucyapi.realsn.com/ErrorAPI?cmd=check&i_type=xml";
		
		try {
			Element root = rootReturn(url, "");
			
			Element lst = root.getChild("lst");
			List lst_elm = lst.getChildren();
			
			if(lst_elm.size() > 0){
				for(int l=0; l< lst_elm.size(); l++){
					Element element = (Element)lst_elm.get(l);
					if(element.getAttributeValue("name").equals("code")){
						if(  element.getValue().toString().equals("1") ){
							System.out.println("1");
						}else{
							System.out.println("0");
						}
					}
				}
			}
			
			for(int l2=0; l2< lst_elm.size(); l2++){
				Element strElement = (Element)lst_elm.get(l2);
				
				if(strElement.getAttributeValue("name").equals("errorMessage")){
					String msg = strElement.getValue().toString();
					System.out.println( msg );
					
				}else if( strElement.getAttributeValue("name").equals("alertMessage") ){
					String alert = strElement.getValue().toString();
					System.out.println( alert );
					
				}
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static Element rootReturn(String queryUrl, String q)throws IOException ,JDOMException{
		Document xmlDoc = null;
		SAXBuilder sax = null;
		Element root=null;
		sax = new SAXBuilder();
		URL url = new URL(queryUrl);
		URLConnection conn;
		conn = url.openConnection();
		((HttpURLConnection)conn).setRequestMethod("POST");
		conn.setDoOutput(true);
	    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	    wr.write(q);
	    wr.flush();
		
	    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	    
	    Document tempXMLDoc = sax.build(rd);
		
		if (tempXMLDoc != null) {
			xmlDoc = tempXMLDoc;
			root=xmlDoc.getRootElement();
		}
		return root;
	}
	
}
