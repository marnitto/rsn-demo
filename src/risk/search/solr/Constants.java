package risk.search.solr;

//import org.apache.log4j.Logger;
//import rsnf.RsnfConstants;

/**
 * 
 * @author Ryu Seung Wan
 */
public class Constants /*extends RsnfConstants*/ {

//	protected static Logger log = Logger.getLogger(Constants.class);
 
	public static String SYS_TITLE_ADMIN = " Information Analysis System ";
	public static String SYS_TITLE_USER = " Information Analysis System ";
	
	public static String SOLR_SERVER_URL="";
	public static String SOLR_SERVER_PORT="";
	public static int LOG4J_DELETE_IF_AGE_GREATER_THAN_DAYS	= 7;//�α����� ���� �Ⱓ
	public static String LOG4J_LOG_DIR 	= "";
	//���� ����
	public static  String EMAIL_SMTP = "58.180.17.2";
	 
	//�����»�� ���ϸ�
	public static String SENDER = "ias@realsn.com";
	
	//�����»�� 
	public static String SENDERNAME = "Information  Analysis  System";
	
	//�ý��� url
	public static String DOMAIN = "";
	

	public static int MAILLISTCNT = 0;
	public static int MAILGROUPCNT = 0;
	
	//���� ���
	public static  String NAMOPATH = "http://localhost/namo/";
//	public static  String NAMOPATH = "E:/project/workspace/chartimages/fusion//WebContent/risk/namo/";
	
	//íƮ������ �÷�
	public static  String[] COLOR = new String[]{"#92B0E5", "#DB9DCE", "#82C6D5", "#DBD289", "#B6ACD6", "#A5CA37","#DCB296", "#7BC68F","#a9a9a9","#DB9D9D"};
	
	//SearchMainPage�� 2d���� íƮ
	public static  String STICK_CHARTXML_TOP = "<graph  exportEnabled='1' exportAtClient='0' exportAction='download' exportFileName='MyFileName' exportHandler='http://search-k.kbs.co.kr/chartimages/fusion/FCExporter.save' showValues='0' decimals='0' formatNumberScale='0' baseFont='Dotum' BaseFontSize='12' outCnvBaseFontSize='12' animation='1'  bgColor='FFFFFF' canvasBorderAlpha='100'  canvasBorderColor='000000' borderThickness='1' canvasBorderThickness='1'>";
	
	//Trend���� ������ íƮ
	public static  String STICK_CHARTXML_TOP2 = "<graph exportCallback='myCallBackFunction'  exportEnabled='1' exportAtClient='0' exportAction='download' exportFileName='MyFileName' exportHandler='http://search-k.kbs.co.kr/chartimages/fusion/FCExporter.save'    chartTopMargin='20' chartRightMargin='20' numVDivLines='10' divLineAlpha='30'  labelPadding ='0' valuePosition='auto' yAxisValuesPadding ='0' showValues='0' rotateValues='1' showLegend='0' baseFont='Dotum' BaseFontSize='11' outCnvBaseFontSize='11' borderThickness='1' canvasBorderThickness='1' >";

	//Trend���� ������ íƮ
	public static  String STICK_CHARTXML_TOP3 = "<graph exportCallback='myCallBackFunction'   exportEnabled='1' exportAtClient='0' exportAction='download' exportFileName='MyFileName' exportHandler='http://search-k.kbs.co.kr/chartimages/fusion/FCExporter.save'   chartTopMargin='20'  bgColor='FFFFFF'  chartRightMargin='20' numVDivLines='10' divLineAlpha='30'   showValues='0' rotateValues='1' chartTopMargin='20' chartRightMargin='20' showLegend='0' baseFont='Dotum' BaseFontSize='11' outCnvBaseFontSize='11' borderThickness='1' canvasBorderThickness='1' drawAnchors='0'>";
	
	//����íƮ
	public static  String FIE_CHARTXML_TOP = "<graph exportCallback='myCallBackFunction'  exportEnabled='1' exportAtClient='0' exportAction='download' exportFileName='MyFileName' exportHandler='http://search-k.kbs.co.kr/chartimages/fusion/FCExporter.save' decimalPrecision='0' baseFont='Dotum' BaseFontSize='12' outCnvBaseFontSize='12' showPercentageValues='1' showNames='0' showValues='1' showPercentageInLabel='1' pieYScale='50' pieSliceDepth='15'  showBorder='1' borderAlpha ='80'  borderThickness='1' borderColor='999999'>";
	
	
	//��Ÿ���� GSN 
	public static String META_GSN_LIST ="";
}
