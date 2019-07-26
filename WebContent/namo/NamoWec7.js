document.onreadystatechange=function()
{
 if (document.readyState == 'complete')
 {
      if (document.all['divShowInstall'])
        document.all['divShowInstall'].style.visibility = 'hidden';
  }
}

var strScripts ="<OBJECT ID='Wec' CLASSID='CLSID:CE67C33D-5A8C-4459-A596-4E5E0B1D5715' WIDTH='800' HEIGHT='750' CodeBase='/namo/NamoWec.cab#Version=7,0,3,7'>";
strScripts +="<PARAM NAME='UserLang' VALUE=kor>";
strScripts +="<PARAM NAME='InitFileURL' VALUE='/namo/UserSetting.xml'>";
strScripts +="<PARAM NAME='InitFileVer' VALUE='1.0'>";
strScripts +="<PARAM NAME='InitFileWaitTime' VALUE='3000'>";
strScripts +="<PARAM NAME='EditorAutoSaveInterval' VALUE='10'>";
strScripts +="<PARAM NAME='EditorBackupOnOff' VALUE='True'>";
strScripts +="<PARAM NAME='InstallSourceURL' VALUE='http://help.namo.co.kr/activesquare/techlist/help/AS7_update/'>";
strScripts +="</OBJECT>";

document.write(strScripts);