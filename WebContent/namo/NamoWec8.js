document.onreadystatechange=function()
{
 if (document.readyState == 'complete')
 {
      if (document.all['divShowInstall'])
        document.all['divShowInstall'].style.visibility = 'hidden';
  }
}

var strScripts ="<OBJECT ID='Wec' CLASSID='CLSID:E0E0AFB5-7BB7-43CD-A7E5-A4B17460EF99' WIDTH='715' HEIGHT='800' CodeBase='/namo/NamoWec.cab#Version=7,0,0,68'>";
strScripts +="<PARAM NAME='UserLang' VALUE=kor>";
strScripts +="<PARAM NAME='InitFileURL' VALUE='/namo/UserSetting.xml'>";
strScripts +="<PARAM NAME='InstallSourceURL' VALUE='http://help.namo.co.kr/activesquare/techlist/help/AS7_update'>";
strScripts +="</OBJECT>";

document.write(strScripts);